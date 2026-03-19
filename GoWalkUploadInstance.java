package com.petkit.android.activities.go.utils;

import android.content.Intent;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.petkit.android.activities.go.ApiResponse.GoDailyDetailRsp;
import com.petkit.android.activities.go.model.GoDayData;
import com.petkit.android.activities.go.model.GoRecord;
import com.petkit.android.activities.go.model.GoWalkData;
import com.petkit.android.activities.home.event.RefreshPetDailyData;
import com.petkit.android.activities.statistics.mode.RefreshAllWalkData;
import com.petkit.android.api.PetkitCallback;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.AsyncHttpUtil;
import com.petkit.android.api.http.apiResponse.ErrorInfor;
import com.petkit.android.api.repository.WebModelRepository;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.LogcatStorageHelper;
import com.petkit.android.utils.PetkitLog;
import com.petkit.android.utils.UploadImagesUtils;
import cz.msebera.android.httpclient.Header;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import net.sourceforge.pinyin4j.ChineseToPinyinResource;
import org.simple.eventbus.EventBus;

/* JADX INFO: loaded from: classes3.dex */
public class GoWalkUploadInstance {
    public static final int RETRY_TIMES = 5;
    public static GoWalkUploadInstance mInstance;
    public boolean isUploading = false;
    public int mRetryTimes = 0;

    public static GoWalkUploadInstance getInstance() {
        if (mInstance == null) {
            mInstance = new GoWalkUploadInstance();
        }
        return mInstance;
    }

    public void start() {
        if (this.isUploading) {
            return;
        }
        GoWalkData nextUploadWalkData = getNextUploadWalkData();
        if (nextUploadWalkData != null) {
            this.isUploading = true;
            if (nextUploadWalkData.getImage() != null && nextUploadWalkData.getImage().startsWith("file://")) {
                uploadGoRoute(nextUploadWalkData);
                return;
            } else {
                uploadGoWalk(nextUploadWalkData);
                return;
            }
        }
        stop();
    }

    public final GoWalkData getNextUploadWalkData() {
        List<GoWalkData> goWalkDataWaitUploadList = GoDataUtils.getGoWalkDataWaitUploadList();
        if (goWalkDataWaitUploadList == null || goWalkDataWaitUploadList.size() <= 0) {
            return null;
        }
        return goWalkDataWaitUploadList.get(0);
    }

    public void stop() {
        this.isUploading = false;
        this.mRetryTimes = 0;
    }

    public final void failed() {
        this.mRetryTimes++;
        PetkitLog.d("upload GoWalkData failed, times: " + this.mRetryTimes);
        if (this.mRetryTimes < 5) {
            this.isUploading = false;
            start();
        } else {
            stop();
        }
    }

    public final void uploadGoRoute(final GoWalkData goWalkData) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put(goWalkData.getImage().substring(7), "");
        new UploadImagesUtils(UploadImagesUtils.NS_GO_ROUTE, linkedHashMap, new UploadImagesUtils.IUploadImagesListener() { // from class: com.petkit.android.activities.go.utils.GoWalkUploadInstance.1
            @Override // com.petkit.android.utils.UploadImagesUtils.IUploadImagesListener
            public void onUploadImageSuccess(LinkedHashMap<String, String> linkedHashMap2) {
                String str = linkedHashMap2.get(goWalkData.getImage().substring(7));
                GoWalkData goWalkDataById = GoDataUtils.getGoWalkDataById(goWalkData.getId().longValue());
                goWalkDataById.setImage(str);
                goWalkDataById.save();
                GoWalkUploadInstance.this.uploadGoWalk(GoDataUtils.getGoWalkDataById(goWalkDataById.getId().longValue()));
            }

            @Override // com.petkit.android.utils.UploadImagesUtils.IUploadImagesListener
            public void onUploadImageFailed() {
                LogcatStorageHelper.addLog("upload go route imag failed!");
                GoWalkUploadInstance.this.failed();
            }
        }, 2).start();
    }

    public final void uploadGoWalk(final GoWalkData goWalkData) {
        List<GoRecord> goRecordList = GoDataUtils.getGoRecordList();
        if (goRecordList == null || goRecordList.size() == 0) {
            return;
        }
        HashMap map = new HashMap();
        map.put("distance", Integer.valueOf(goWalkData.getDistance()));
        map.put("source", Integer.valueOf(goWalkData.getSource()));
        map.put("t1", goWalkData.getT1());
        map.put("t2", goWalkData.getT2());
        map.put("points", goWalkData.getPoints());
        map.put("poi", goWalkData.getPoi());
        if (goWalkData.getImage() != null) {
            map.put("routeImg", new JsonParser().parse(goWalkData.getImage()));
        }
        if (!CommonUtils.isEmpty(goWalkData.getPetId())) {
            map.put("pet", String.format("{\"id\":\"%s\"}", goWalkData.getPetId()));
        }
        if (goWalkData.getMarkers() != null) {
            map.put("marks", goWalkData.getMarkers());
        }
        HashMap map2 = new HashMap();
        map2.put("id", String.valueOf(goWalkData.getDeviceId()));
        map2.put("data", new Gson().toJson(map));
        map2.put("update", String.valueOf(goWalkData.getState() == 6));
        WebModelRepository.getInstance().saveGoData(map2, new PetkitCallback<String[]>() { // from class: com.petkit.android.activities.go.utils.GoWalkUploadInstance.2
            @Override // com.petkit.android.api.PetkitCallback
            public void onSuccess(String[] strArr) {
                goWalkData.setState(4);
                goWalkData.save();
                GoDataUtils.storeDefaultPetForGoWalkData(goWalkData);
                if (strArr != null && strArr.length > 0) {
                    for (String str : strArr) {
                        GoDayData goDayDataForDay = GoDataUtils.getGoDayDataForDay(goWalkData.getDeviceId(), Integer.valueOf(str).intValue());
                        if (goDayDataForDay != null) {
                            goDayDataForDay.setNeedUpdate(true);
                            goDayDataForDay.save();
                        }
                    }
                }
                EventBus.getDefault().post(new RefreshAllWalkData());
                EventBus.getDefault().post(new RefreshPetDailyData());
                GoWalkUploadInstance.this.getList(goWalkData, strArr);
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onFailure(ErrorInfor errorInfor) {
                GoWalkUploadInstance.this.failed();
            }
        });
    }

    public final void getList(final GoWalkData goWalkData, String[] strArr) {
        if (goWalkData == null || strArr == null || strArr.length == 0) {
            stop();
            start();
            return;
        }
        StringBuilder sb = new StringBuilder(strArr[0]);
        for (int i = 1; i < strArr.length; i++) {
            sb.append(ChineseToPinyinResource.Field.COMMA + strArr[i]);
        }
        final String string = sb.toString();
        HashMap map = new HashMap();
        map.put("id", String.valueOf(goWalkData.getDeviceId()));
        map.put("days", string);
        map.put("detailsLimit", String.valueOf(10));
        AsyncHttpUtil.post(ApiTools.SAMPLE_API_GO_DAILY_DATA, map, new AsyncHttpRespHandler() { // from class: com.petkit.android.activities.go.utils.GoWalkUploadInstance.3
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i2, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i2, headerArr, bArr);
                GoDailyDetailRsp goDailyDetailRsp = (GoDailyDetailRsp) this.gson.fromJson(this.responseResult, GoDailyDetailRsp.class);
                if (goDailyDetailRsp.getError() != null) {
                    GoWalkUploadInstance.this.failed();
                    return;
                }
                for (int i3 = 0; i3 < goDailyDetailRsp.getResult().size(); i3++) {
                    GoDailyDetailRsp.ResultBean resultBean = goDailyDetailRsp.getResult().get(i3);
                    GoDayData orCreateGoDayDataForDay = GoDataUtils.getOrCreateGoDayDataForDay(goWalkData.getDeviceId(), resultBean.getDay());
                    orCreateGoDayDataForDay.setDistance(resultBean.getDistance());
                    orCreateGoDayDataForDay.setDuration(resultBean.getDuration());
                    orCreateGoDayDataForDay.setTimes(resultBean.getTimes());
                    orCreateGoDayDataForDay.setGoal(resultBean.getGoal());
                    orCreateGoDayDataForDay.setNeedUpdate(false);
                    if (resultBean.getDetails() != null) {
                        orCreateGoDayDataForDay.setLastKey(resultBean.getDetails().getList().size() < 10 ? null : resultBean.getDetails().getLastKey());
                        for (int i4 = 0; i4 < resultBean.getDetails().getList().size(); i4++) {
                            GoDataUtils.storeGoWalkDataForDay(goWalkData.getDeviceId(), resultBean.getDay(), resultBean.getDetails().getList().get(i4));
                        }
                    }
                    orCreateGoDayDataForDay.save();
                }
                Intent intent = new Intent(GoDataUtils.BROADCAST_GO_WALK_UPDATE);
                intent.putExtra(GoDataUtils.EXTRA_GO_ID, goWalkData.getDeviceId());
                intent.putExtra(GoDataUtils.EXTRA_GO_DATA, string);
                LocalBroadcastManager.getInstance(CommonUtils.getAppContext()).sendBroadcast(intent);
                GoWalkUploadInstance.this.stop();
                GoWalkUploadInstance.this.start();
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFailure(int i2, Header[] headerArr, byte[] bArr, Throwable th) {
                super.onFailure(i2, headerArr, bArr, th);
                GoWalkUploadInstance.this.failed();
            }
        });
    }
}

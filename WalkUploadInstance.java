package com.petkit.android.activities.walkdog.utils;

import android.content.Intent;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.github.sunnysuperman.commons.utils.CollectionUtil;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.petkit.android.activities.home.event.RefreshPetDailyData;
import com.petkit.android.activities.statistics.mode.RefreshAllWalkData;
import com.petkit.android.activities.walkdog.ApiResponse.WalkDailyDetailRsp;
import com.petkit.android.activities.walkdog.model.WalkData;
import com.petkit.android.activities.walkdog.model.WalkDayData;
import com.petkit.android.api.PetkitCallback;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.AsyncHttpUtil;
import com.petkit.android.api.http.apiResponse.ErrorInfor;
import com.petkit.android.api.repository.WebModelRepository;
import com.petkit.android.model.UpdateWalkDataParam;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.LogcatStorageHelper;
import com.petkit.android.utils.PetkitLog;
import com.petkit.android.utils.UploadImagesUtils;
import cz.msebera.android.httpclient.Header;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import net.sourceforge.pinyin4j.ChineseToPinyinResource;
import org.simple.eventbus.EventBus;

/* JADX INFO: loaded from: classes6.dex */
public class WalkUploadInstance {
    public static final int RETRY_TIMES = 5;
    public static WalkUploadInstance mInstance;
    public boolean isAliOss;
    public boolean isUploading = false;
    public int mRetryTimes = 0;

    public static WalkUploadInstance getInstance() {
        if (mInstance == null) {
            mInstance = new WalkUploadInstance();
        }
        return mInstance;
    }

    public void start() {
        if (this.isUploading) {
            return;
        }
        WalkData nextUploadWalkData = getNextUploadWalkData();
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

    public final WalkData getNextUploadWalkData() {
        List<WalkData> walkDataWaitUploadList = WalkDataUtils.getWalkDataWaitUploadList();
        if (walkDataWaitUploadList == null || walkDataWaitUploadList.size() <= 0) {
            return null;
        }
        return walkDataWaitUploadList.get(0);
    }

    public void stop() {
        this.isUploading = false;
        this.mRetryTimes = 0;
    }

    public final void failed() {
        this.mRetryTimes++;
        PetkitLog.d("upload WalkData failed, times: " + this.mRetryTimes);
        if (this.mRetryTimes < 5) {
            this.isUploading = false;
            start();
        } else {
            stop();
        }
    }

    public void setIsAliOss(boolean z) {
        this.isAliOss = z;
    }

    public final void uploadGoRoute(final WalkData walkData) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put(walkData.getImage().substring(7), "");
        new UploadImagesUtils(UploadImagesUtils.NS_GO_ROUTE, linkedHashMap, new UploadImagesUtils.IUploadImagesListener() { // from class: com.petkit.android.activities.walkdog.utils.WalkUploadInstance.1
            @Override // com.petkit.android.utils.UploadImagesUtils.IUploadImagesListener
            public void onUploadImageSuccess(LinkedHashMap<String, String> linkedHashMap2) {
                String json;
                if (!WalkUploadInstance.this.isAliOss) {
                    json = linkedHashMap2.get(walkData.getImage().substring(7));
                } else {
                    json = new Gson().toJson(new UpdateWalkDataParam(linkedHashMap2.get(walkData.getImage().substring(7))));
                }
                WalkData walkDataById = WalkDataUtils.getWalkDataById(walkData.getId().longValue());
                walkDataById.setImage(json);
                walkDataById.save();
                WalkUploadInstance.this.uploadGoWalk(WalkDataUtils.getWalkDataById(walkData.getId().longValue()));
            }

            @Override // com.petkit.android.utils.UploadImagesUtils.IUploadImagesListener
            public void onUploadImageFailed() {
                LogcatStorageHelper.addLog("upload go route imag failed!");
                WalkUploadInstance.this.failed();
            }
        }, 2).start();
    }

    public final void uploadGoWalk(final WalkData walkData) {
        HashMap map = new HashMap();
        map.put("distance", Integer.valueOf(walkData.getDistance()));
        map.put("source", Integer.valueOf(walkData.getSource()));
        map.put("t1", walkData.getT1());
        map.put("t2", walkData.getT2());
        map.put("points", walkData.getPoints());
        map.put("poi", walkData.getPoi());
        if (walkData.getImage() != null) {
            map.put("routeImg", new JsonParser().parse(walkData.getImage()));
        }
        if (!CollectionUtil.isEmpty(walkData.getPetIds())) {
            ArrayList arrayList = new ArrayList();
            Iterator<String> it = walkData.getPetIds().iterator();
            while (it.hasNext()) {
                arrayList.add(String.format("{\"id\":\"%s\"}", it.next()));
            }
            map.put("pet", arrayList);
        }
        if (walkData.getMarkers() != null) {
            map.put("marks", walkData.getMarkers());
        }
        HashMap map2 = new HashMap();
        map2.put("data", new Gson().toJson(map));
        map2.put("update", String.valueOf(walkData.getState() == 6));
        WebModelRepository.getInstance().uploadGoWalk(map2, new PetkitCallback<String>() { // from class: com.petkit.android.activities.walkdog.utils.WalkUploadInstance.2
            @Override // com.petkit.android.api.PetkitCallback
            public void onSuccess(String str) {
                if (str == null) {
                    WalkUploadInstance.this.failed();
                } else {
                    walkData.setState(4);
                    walkData.save();
                    WalkDataUtils.storeDefaultPetForWalkData(walkData);
                    WalkDayData walkDayDataForDay = WalkDataUtils.getWalkDayDataForDay(Integer.valueOf(str).intValue());
                    if (walkDayDataForDay != null) {
                        walkDayDataForDay.setNeedUpdate(true);
                        walkDayDataForDay.save();
                    }
                    WalkUploadInstance.this.getList(walkData, new String[]{str});
                }
                EventBus.getDefault().post(new RefreshAllWalkData());
                EventBus.getDefault().post(new RefreshPetDailyData());
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onFailure(ErrorInfor errorInfor) {
                WalkUploadInstance.this.failed();
            }
        });
    }

    public final void getList(WalkData walkData, String[] strArr) {
        if (walkData == null || strArr == null || strArr.length == 0) {
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
        map.put("days", string);
        map.put("detailsLimit", String.valueOf(10));
        AsyncHttpUtil.post(ApiTools.SAMPLE_API_WALK_PET_DAILY_DATA, map, new AsyncHttpRespHandler() { // from class: com.petkit.android.activities.walkdog.utils.WalkUploadInstance.3
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i2, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i2, headerArr, bArr);
                WalkDailyDetailRsp walkDailyDetailRsp = (WalkDailyDetailRsp) this.gson.fromJson(this.responseResult, WalkDailyDetailRsp.class);
                if (walkDailyDetailRsp.getError() != null) {
                    WalkUploadInstance.this.failed();
                    return;
                }
                for (int i3 = 0; i3 < walkDailyDetailRsp.getResult().size(); i3++) {
                    WalkDailyDetailRsp.ResultBean resultBean = walkDailyDetailRsp.getResult().get(i3);
                    WalkDayData orCreateWalkDayDataForDay = WalkDataUtils.getOrCreateWalkDayDataForDay(resultBean.getDay());
                    orCreateWalkDayDataForDay.setDistance(resultBean.getDistance());
                    orCreateWalkDayDataForDay.setDuration(resultBean.getDuration());
                    orCreateWalkDayDataForDay.setTimes(resultBean.getTimes());
                    orCreateWalkDayDataForDay.setGoal(resultBean.getGoal());
                    orCreateWalkDayDataForDay.setNeedUpdate(false);
                    if (resultBean.getDetails() != null) {
                        orCreateWalkDayDataForDay.setLastKey(resultBean.getDetails().getList().size() < 10 ? null : resultBean.getDetails().getLastKey());
                        for (int i4 = 0; i4 < resultBean.getDetails().getList().size(); i4++) {
                            WalkDataUtils.storeWalkDataForDay(resultBean.getDay(), resultBean.getDetails().getList().get(i4));
                        }
                    }
                    orCreateWalkDayDataForDay.save();
                }
                Intent intent = new Intent(WalkDataUtils.BROADCAST_WALK_UPDATE);
                intent.putExtra(WalkDataUtils.EXTRA_WALK_DATA, string);
                LocalBroadcastManager.getInstance(CommonUtils.getAppContext()).sendBroadcast(intent);
                WalkUploadInstance.this.stop();
                WalkUploadInstance.this.start();
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFailure(int i2, Header[] headerArr, byte[] bArr, Throwable th) {
                super.onFailure(i2, headerArr, bArr, th);
                WalkUploadInstance.this.failed();
            }
        });
    }
}

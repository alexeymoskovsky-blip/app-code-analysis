package com.petkit.android.activities.appwidget.provider;

import android.annotation.SuppressLint;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.petkit.android.activities.appwidget.mode.AppWidgetBean;
import com.petkit.android.activities.appwidget.mode.RefreshHomeRsp;
import com.petkit.android.activities.appwidget.utils.AppWidgetUtils;
import com.petkit.android.activities.device.mode.DeviceInfos;
import com.petkit.android.activities.device.utils.DeviceUtils;
import com.petkit.android.activities.home.adapter.model.HomeDeviceData;
import com.petkit.android.activities.petkitBleDevice.download.utils.VideoDownloadManager;
import com.petkit.android.activities.petkitBleDevice.t7.utils.T7ConstUtils;
import com.petkit.android.activities.petkitBleDevice.utils.FamilyUtils;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.AsyncHttpUtil;
import com.petkit.android.utils.CommonUtil;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.DateUtil;
import com.petkit.android.utils.LogcatStorageHelper;
import com.petkit.android.utils.PetkitLog;
import cz.msebera.android.httpclient.Header;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/* JADX INFO: loaded from: classes3.dex */
public class ToiletAppWidgetProvider extends BaseAppWidgetProvider {
    public static final String normalWidgetKey = "normal_widget";
    public ComponentName componentName;
    public BroadcastReceiver.PendingResult pendingResult;

    @SuppressLint({"SimpleDateFormat"})
    public final SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtil.DATE_FORMAT_7);
    public final String tag = "LargeWidget";
    public final Map<String, List<AppWidgetBean>> refreshAppWidgetMap = new HashMap();
    public int requestCount = 0;
    public int exceptCount = 0;

    public static /* synthetic */ int access$012(ToiletAppWidgetProvider toiletAppWidgetProvider, int i) {
        int i2 = toiletAppWidgetProvider.requestCount + i;
        toiletAppWidgetProvider.requestCount = i2;
        return i2;
    }

    @Override // com.petkit.android.activities.appwidget.provider.BaseAppWidgetProvider, android.appwidget.AppWidgetProvider
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] iArr) {
        super.onUpdate(context, appWidgetManager, iArr);
        if (this.componentName == null) {
            this.componentName = new ComponentName(context.getPackageName(), ToiletAppWidgetProvider.class.getName());
        }
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(this.componentName);
        for (int i : appWidgetIds) {
        }
        getDataFromRemote(appWidgetIds, context);
    }

    @Override // android.appwidget.AppWidgetProvider, android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        PetkitLog.d("LargeWidget", "onReceive,action:" + intent.getAction());
        if (intent.getAction() == null) {
        }
        String action = intent.getAction();
        action.hashCode();
        switch (action) {
            case "android.appwidget.action.APPWIDGET_LARGE_REFRESH_DEVICE_DATA_FROM_LOCAL":
                long longExtra = intent.getLongExtra(Constants.EXTRA_DEVICE_ID, 0L);
                String stringExtra = intent.getStringExtra(AppWidgetUtils.EXTRA_APP_WIDGET_REFRESH_DATA);
                if (this.componentName == null) {
                    this.componentName = new ComponentName(context.getPackageName(), ToiletAppWidgetProvider.class.getName());
                }
                for (AppWidgetBean appWidgetBean : getAppWidgetBeansByDeviceId(context, this.componentName)) {
                    if (appWidgetBean.getDeviceId() == longExtra && stringExtra != null) {
                        refreshLargeWidgetData(context, (HomeDeviceData) new Gson().fromJson(stringExtra, HomeDeviceData.class), (int) appWidgetBean.getAppWidgetId());
                        break;
                    }
                }
                break;
            case "android.appwidget.action.APPWIDGET_LARGE_CHECK_DEVICE_IS_AVAILABLE":
                String stringExtra2 = intent.getStringExtra(AppWidgetUtils.EXTRA_APP_WIDGET_STATUS);
                List<AppWidgetBean> appWidgetBeans = AppWidgetUtils.getAppWidgetBeans();
                if (this.componentName == null) {
                    this.componentName = new ComponentName(context.getPackageName(), ToiletAppWidgetProvider.class.getName());
                }
                List<AppWidgetBean> listCheckLargeWidgetValid = checkLargeWidgetValid(context, this.componentName, appWidgetBeans);
                if (listCheckLargeWidgetValid != null && listCheckLargeWidgetValid.size() > 0) {
                    PetkitLog.d("LargeWidget", new Gson().toJson(listCheckLargeWidgetValid));
                    for (AppWidgetBean appWidgetBean2 : listCheckLargeWidgetValid) {
                        boolean z = FamilyUtils.getInstance().getFamilyInforThroughDevice(context, appWidgetBean2.getDeviceId(), CommonUtils.getDeviceTypeByString(appWidgetBean2.getType())) == null;
                        DeviceInfos deviceInfosFindDeviceInfo = DeviceUtils.findDeviceInfo(appWidgetBean2.getDeviceId(), CommonUtil.getDeviceType(appWidgetBean2.getType()));
                        boolean z2 = (deviceInfosFindDeviceInfo == null || deviceInfosFindDeviceInfo.getDeviceShared() == null) ? false : true;
                        PetkitLog.d("LargeWidget", "noFamily:" + z + "isShare:" + z2);
                        if (z) {
                            if (z2) {
                                if (stringExtra2 != null && stringExtra2.equals("loginOut")) {
                                    loginOutWidget(context, null, (int) appWidgetBean2.getAppWidgetId());
                                } else {
                                    getHomeCardDetail(appWidgetBean2, context, Integer.valueOf((int) appWidgetBean2.getAppWidgetId()));
                                }
                            } else {
                                refreshLargeWidgetData(context, null, (int) appWidgetBean2.getAppWidgetId());
                            }
                        } else if (stringExtra2 != null && stringExtra2.equals("loginOut")) {
                            loginOutWidget(context, null, (int) appWidgetBean2.getAppWidgetId());
                        } else {
                            getHomeCardDetail(appWidgetBean2, context, Integer.valueOf((int) appWidgetBean2.getAppWidgetId()));
                        }
                    }
                    break;
                }
                break;
            case "android.appwidget.action.APPWIDGET_LARGE_REFRESH":
                this.requestCount = 0;
                this.exceptCount = 1;
                this.pendingResult = goAsync();
                int intExtra = intent.getIntExtra(AppWidgetUtils.EXTRA_APP_WIDGET_ID, 0);
                AppWidgetBean appWidgetBeanById = AppWidgetUtils.getAppWidgetBeanById(intExtra);
                if (appWidgetBeanById != null) {
                    PetkitLog.d("appLargeWidgetId", "+++++++++++++:" + intExtra + "++++++deviceId：" + appWidgetBeanById.getDeviceId());
                    int deviceTypeByString = CommonUtils.getDeviceTypeByString(appWidgetBeanById.getType());
                    refreshLargeWidgetDataWithRotateAnim(intExtra, appWidgetBeanById.getType(), appWidgetBeanById.getDeviceId());
                    if (VideoDownloadManager.isNetworkAvailable(context)) {
                        if (AppWidgetUtils.isNeedLargeType(deviceTypeByString)) {
                            getHomeCardDetail(appWidgetBeanById, context, Integer.valueOf(intExtra));
                        } else {
                            this.pendingResult.finish();
                        }
                    } else {
                        this.pendingResult.finish();
                    }
                    break;
                }
                break;
        }
    }

    @Override // android.appwidget.AppWidgetProvider
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int i, Bundle bundle) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, i, bundle);
        PetkitLog.d("LargeWidget", "onAppWidgetOptionsChanged : " + i);
    }

    public synchronized void getDataFromRemote(int[] iArr, Context context) {
        try {
            boolean zCheckAppWidgetNumByIds = AppWidgetUtils.checkAppWidgetNumByIds(iArr);
            this.refreshAppWidgetMap.clear();
            if (zCheckAppWidgetNumByIds) {
                for (int i : iArr) {
                    AppWidgetBean appWidgetBeanById = AppWidgetUtils.getAppWidgetBeanById(i);
                    if (appWidgetBeanById != null) {
                        if (AppWidgetUtils.isNeedLargeType(CommonUtils.getDeviceTypeByString(appWidgetBeanById.getType()))) {
                            if (this.refreshAppWidgetMap.get(String.valueOf(appWidgetBeanById.getDeviceId())) == null) {
                                this.refreshAppWidgetMap.put(String.valueOf(appWidgetBeanById.getDeviceId()), new ArrayList());
                            }
                            List<AppWidgetBean> list = this.refreshAppWidgetMap.get(String.valueOf(appWidgetBeanById.getDeviceId()));
                            Objects.requireNonNull(list);
                            list.add(appWidgetBeanById);
                        } else {
                            if (this.refreshAppWidgetMap.get(normalWidgetKey) == null) {
                                this.refreshAppWidgetMap.put(normalWidgetKey, new ArrayList());
                            }
                            List<AppWidgetBean> list2 = this.refreshAppWidgetMap.get(normalWidgetKey);
                            Objects.requireNonNull(list2);
                            list2.add(appWidgetBeanById);
                        }
                    }
                }
                if (this.refreshAppWidgetMap.size() > 0) {
                    this.requestCount = 0;
                    this.exceptCount = this.refreshAppWidgetMap.size();
                    this.pendingResult = goAsync();
                    for (Map.Entry<String, List<AppWidgetBean>> entry : this.refreshAppWidgetMap.entrySet()) {
                        List<AppWidgetBean> list3 = this.refreshAppWidgetMap.get(entry.getKey());
                        Objects.requireNonNull(list3);
                        AppWidgetBean appWidgetBean = list3.get(0);
                        ArrayList arrayList = new ArrayList();
                        List<AppWidgetBean> list4 = this.refreshAppWidgetMap.get(entry.getKey());
                        Objects.requireNonNull(list4);
                        Iterator<AppWidgetBean> it = list4.iterator();
                        while (it.hasNext()) {
                            arrayList.add(Integer.valueOf((int) it.next().getAppWidgetId()));
                        }
                        Integer[] numArr = new Integer[arrayList.size()];
                        arrayList.toArray(numArr);
                        getHomeCardDetail(appWidgetBean, context, numArr);
                    }
                }
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    private void getHomeCardDetail(AppWidgetBean appWidgetBean, Context context, Integer... numArr) {
        int deviceTypeByString = CommonUtils.getDeviceTypeByString(appWidgetBean.getType());
        String str = ApiTools.SAMPLE_API_FIT_REFRESH_HOME;
        switch (deviceTypeByString) {
            case 2:
                str = ApiTools.SAMPLE_API_MATE_REFRESH_HOME;
                break;
            case 3:
                str = ApiTools.SAMPLE_API_GO_REFRESH_HOME;
                break;
            case 4:
                str = ApiTools.SAMPLE_API_FEEDER_REFRESH_HOME;
                break;
            case 5:
                str = ApiTools.SAMPLE_API_COZY_REFRESH_HOME;
                break;
            case 6:
                str = ApiTools.SAMPLE_API_D2_REFRESH_HOME;
                break;
            case 7:
                str = ApiTools.SAMPLE_API_T3_REFRESH_HOME;
                break;
            case 8:
                str = ApiTools.SAMPLE_API_K2_REFRESH_HOME;
                break;
            case 9:
                str = ApiTools.SAMPLE_API_D3_REFRESH_HOME;
                break;
            case 10:
                str = ApiTools.SAMPLE_API_AQ_REFRESH_HOME;
                break;
            case 11:
                str = ApiTools.SAMPLE_API_D4_REFRESH_HOME;
                break;
            case 12:
                str = ApiTools.SAMPLE_API_P3_REFRESH_HOME;
                break;
            case 13:
                str = ApiTools.SAMPLE_API_H2_REFRESH_HOME;
                break;
            case 14:
                str = ApiTools.SAMPLE_API_W5_REFRESH_HOME;
                break;
            case 15:
                str = ApiTools.SAMPLE_API_T4_REFRESH_HOME;
                break;
            case 16:
                str = ApiTools.SAMPLE_API_K3_REFRESH_HOME;
                break;
            case 17:
                str = ApiTools.SAMPLE_API_AQR_REFRESH_HOME;
                break;
            case 18:
                str = ApiTools.SAMPLE_API_R2_REFRESH_HOME;
                break;
            case 19:
                str = ApiTools.SAMPLE_API_AQH1_REFRESH_HOME;
                break;
            case 20:
                str = ApiTools.SAMPLE_API_D4S_REFRESH_HOME;
                break;
            case 21:
                str = ApiTools.SAMPLE_API_T5_REFRESH_HOME;
                break;
            case 22:
                str = ApiTools.SAMPLE_API_HG_REFRESH_HOME;
                break;
            case 24:
                str = ApiTools.SAMPLE_API_CTW3_REFRESH_HOME;
                break;
            case 25:
                str = ApiTools.SAMPLE_API_D4SH_REFRESH_HOME;
                break;
            case 26:
                str = ApiTools.SAMPLE_API_D4H_REFRESH_HOME;
                break;
            case 27:
                str = ApiTools.SAMPLE_API_T6_REFRESH_HOME;
                break;
            case 28:
                str = T7ConstUtils.SAMPLE_API_T7_REFRESH_HOME;
                break;
            case 29:
                str = ApiTools.SAMPLE_API_W7H_REFRESH_HOME;
                break;
        }
        PetkitLog.d("LargeWidget", "getHomeCardDetail type:" + deviceTypeByString + " id:" + appWidgetBean.getDeviceId());
        HashMap map = new HashMap();
        map.put("deviceId", String.valueOf(appWidgetBean.getDeviceId()));
        map.put("day", this.dateFormat.format(new Date()));
        if (!appWidgetBean.getPetId().isEmpty()) {
            map.put("petId", appWidgetBean.getPetId());
        }
        AsyncHttpUtil.post(str, (Map<String, String>) map, (AsyncHttpResponseHandler) new AsyncHttpRespHandler() { // from class: com.petkit.android.activities.appwidget.provider.ToiletAppWidgetProvider.1
            public final /* synthetic */ Integer[] val$appWidgetIds;
            public final /* synthetic */ Context val$context;

            public AnonymousClass1(Integer[] numArr2, Context context2) {
                numArr = numArr2;
                context = context2;
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                RefreshHomeRsp refreshHomeRsp = (RefreshHomeRsp) this.gson.fromJson(this.responseResult, RefreshHomeRsp.class);
                if (refreshHomeRsp.getError() != null) {
                    if (refreshHomeRsp.getError().getCode() == 705 || refreshHomeRsp.getError().getCode() == 1005) {
                        for (Integer num : numArr) {
                            ToiletAppWidgetProvider.this.refreshLargeWidgetData(context, null, num.intValue());
                        }
                    }
                    LogcatStorageHelper.addLog("getDeviceList error:" + refreshHomeRsp.getError().getMsg());
                    return;
                }
                List<HomeDeviceData> devices = refreshHomeRsp.getResult().getDevices();
                if (devices == null || devices.size() == 0 || devices.get(0) == null || devices.get(0).getType() == null) {
                    return;
                }
                for (Integer num2 : numArr) {
                    ToiletAppWidgetProvider.this.refreshLargeWidgetData(context, devices.get(0), num2.intValue());
                }
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFinish() {
                try {
                    ToiletAppWidgetProvider.access$012(ToiletAppWidgetProvider.this, 1);
                    if (ToiletAppWidgetProvider.this.requestCount == ToiletAppWidgetProvider.this.exceptCount && ToiletAppWidgetProvider.this.pendingResult != null) {
                        ToiletAppWidgetProvider.this.pendingResult.finish();
                    }
                    super.onFinish();
                } catch (Exception e) {
                    PetkitLog.d("pendingResult:" + e.getMessage());
                    LogcatStorageHelper.addLog("pendingResult:" + e.getMessage());
                }
            }
        }, false);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.appwidget.provider.ToiletAppWidgetProvider$1 */
    public class AnonymousClass1 extends AsyncHttpRespHandler {
        public final /* synthetic */ Integer[] val$appWidgetIds;
        public final /* synthetic */ Context val$context;

        public AnonymousClass1(Integer[] numArr2, Context context2) {
            numArr = numArr2;
            context = context2;
        }

        @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
        public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
            super.onSuccess(i, headerArr, bArr);
            RefreshHomeRsp refreshHomeRsp = (RefreshHomeRsp) this.gson.fromJson(this.responseResult, RefreshHomeRsp.class);
            if (refreshHomeRsp.getError() != null) {
                if (refreshHomeRsp.getError().getCode() == 705 || refreshHomeRsp.getError().getCode() == 1005) {
                    for (Integer num : numArr) {
                        ToiletAppWidgetProvider.this.refreshLargeWidgetData(context, null, num.intValue());
                    }
                }
                LogcatStorageHelper.addLog("getDeviceList error:" + refreshHomeRsp.getError().getMsg());
                return;
            }
            List<HomeDeviceData> devices = refreshHomeRsp.getResult().getDevices();
            if (devices == null || devices.size() == 0 || devices.get(0) == null || devices.get(0).getType() == null) {
                return;
            }
            for (Integer num2 : numArr) {
                ToiletAppWidgetProvider.this.refreshLargeWidgetData(context, devices.get(0), num2.intValue());
            }
        }

        @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
        public void onFinish() {
            try {
                ToiletAppWidgetProvider.access$012(ToiletAppWidgetProvider.this, 1);
                if (ToiletAppWidgetProvider.this.requestCount == ToiletAppWidgetProvider.this.exceptCount && ToiletAppWidgetProvider.this.pendingResult != null) {
                    ToiletAppWidgetProvider.this.pendingResult.finish();
                }
                super.onFinish();
            } catch (Exception e) {
                PetkitLog.d("pendingResult:" + e.getMessage());
                LogcatStorageHelper.addLog("pendingResult:" + e.getMessage());
            }
        }
    }
}

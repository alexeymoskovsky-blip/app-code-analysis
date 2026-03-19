package com.petkit.android.activities.appwidget.provider;

import android.annotation.SuppressLint;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.petkit.android.activities.appwidget.mode.AppWidgetBean;
import com.petkit.android.activities.appwidget.mode.RefreshHomeRsp;
import com.petkit.android.activities.appwidget.utils.AppWidgetUtils;
import com.petkit.android.activities.device.mode.DeviceInfos;
import com.petkit.android.activities.device.utils.DeviceUtils;
import com.petkit.android.activities.home.adapter.model.HomeDeviceData;
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
public class FeederAppWidgetProvider extends BaseAppWidgetProvider {
    public ComponentName componentName;
    public BroadcastReceiver.PendingResult pendingResult;

    @SuppressLint({"SimpleDateFormat"})
    public final SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtil.DATE_FORMAT_7);
    public final String tag = "SmallWidget";
    public final Map<String, List<AppWidgetBean>> refreshAppWidgetMap = new HashMap();
    public int requestCount = 0;
    public int exceptCount = 0;

    public static /* synthetic */ int access$012(FeederAppWidgetProvider feederAppWidgetProvider, int i) {
        int i2 = feederAppWidgetProvider.requestCount + i;
        feederAppWidgetProvider.requestCount = i2;
        return i2;
    }

    @Override // com.petkit.android.activities.appwidget.provider.BaseAppWidgetProvider, android.appwidget.AppWidgetProvider
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] iArr) {
        super.onUpdate(context, appWidgetManager, iArr);
        if (this.componentName == null) {
            this.componentName = new ComponentName(context.getPackageName(), FeederAppWidgetProvider.class.getName());
        }
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(this.componentName);
        for (int i : appWidgetIds) {
        }
        getDataFromRemote(appWidgetIds, context);
    }

    @Override // android.appwidget.AppWidgetProvider, android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        PetkitLog.d("SmallWidget", "onReceive,action:" + intent.getAction());
        if (intent.getAction() != null) {
            String action = intent.getAction();
            action.hashCode();
            switch (action) {
                case "android.appwidget.action.APPWIDGET_CHECK_DEVICE_IS_AVAILABLE":
                    String stringExtra = intent.getStringExtra(AppWidgetUtils.EXTRA_APP_WIDGET_STATUS);
                    List<AppWidgetBean> appWidgetBeans = AppWidgetUtils.getAppWidgetBeans();
                    if (this.componentName == null) {
                        this.componentName = new ComponentName(context.getPackageName(), FeederAppWidgetProvider.class.getName());
                    }
                    List<AppWidgetBean> listCheckWidgetValid = checkWidgetValid(context, this.componentName, appWidgetBeans);
                    if (listCheckWidgetValid != null && listCheckWidgetValid.size() > 0) {
                        PetkitLog.d("SmallWidget", new Gson().toJson(listCheckWidgetValid));
                        for (AppWidgetBean appWidgetBean : listCheckWidgetValid) {
                            boolean z = FamilyUtils.getInstance().getFamilyInforThroughDevice(context, appWidgetBean.getDeviceId(), CommonUtils.getDeviceTypeByString(appWidgetBean.getType())) == null;
                            DeviceInfos deviceInfosFindDeviceInfo = DeviceUtils.findDeviceInfo(appWidgetBean.getDeviceId(), CommonUtil.getDeviceType(appWidgetBean.getType()));
                            boolean z2 = (deviceInfosFindDeviceInfo == null || deviceInfosFindDeviceInfo.getDeviceShared() == null) ? false : true;
                            if (z) {
                                if (z2) {
                                    if (stringExtra != null && stringExtra.equals("loginOut")) {
                                        loginOutSmallWidget(context, (int) appWidgetBean.getAppWidgetId());
                                    } else {
                                        getHomeCardDetail(appWidgetBean, context, Integer.valueOf((int) appWidgetBean.getAppWidgetId()));
                                    }
                                } else {
                                    refreshWidgetData(context, null, (int) appWidgetBean.getAppWidgetId());
                                }
                            } else if (stringExtra != null && stringExtra.equals("loginOut")) {
                                loginOutSmallWidget(context, (int) appWidgetBean.getAppWidgetId());
                            } else {
                                getHomeCardDetail(appWidgetBean, context, Integer.valueOf((int) appWidgetBean.getAppWidgetId()));
                            }
                        }
                        break;
                    }
                    break;
                case "android.appwidget.action.APPWIDGET_REFRESH_DEVICE_DATA_FROM_LOCAL":
                    long longExtra = intent.getLongExtra(Constants.EXTRA_DEVICE_ID, 0L);
                    String stringExtra2 = intent.getStringExtra(AppWidgetUtils.EXTRA_APP_WIDGET_REFRESH_DATA);
                    if (this.componentName == null) {
                        this.componentName = new ComponentName(context.getPackageName(), FeederAppWidgetProvider.class.getName());
                    }
                    for (AppWidgetBean appWidgetBean2 : getAppWidgetBeansByDeviceId(context, this.componentName)) {
                        if (appWidgetBean2.getDeviceId() == longExtra && stringExtra2 != null) {
                            refreshWidgetData(context, (HomeDeviceData) new Gson().fromJson(stringExtra2, HomeDeviceData.class), (int) appWidgetBean2.getAppWidgetId());
                            break;
                        }
                    }
                    break;
                case "android.appwidget.action.APPWIDGET_REFRESH":
                    this.requestCount = 0;
                    this.exceptCount = 1;
                    this.pendingResult = goAsync();
                    int intExtra = intent.getIntExtra(AppWidgetUtils.EXTRA_APP_WIDGET_ID, 0);
                    AppWidgetBean appWidgetBeanById = AppWidgetUtils.getAppWidgetBeanById(intExtra);
                    if (appWidgetBeanById != null) {
                        PetkitLog.d("SmallWidget", "+++++++++++++:" + intExtra + "++++++deviceId：" + appWidgetBeanById.getDeviceId());
                        refreshWidgetDataWithRotateAnim(intExtra, appWidgetBeanById.getType(), appWidgetBeanById.getDeviceId());
                        getHomeCardDetail(appWidgetBeanById, context, Integer.valueOf(intExtra));
                        break;
                    }
                    break;
            }
        }
    }

    public synchronized void getDataFromRemote(int[] iArr, Context context) {
        try {
            boolean zCheckAppWidgetNumByIds = AppWidgetUtils.checkAppWidgetNumByIds(iArr);
            this.refreshAppWidgetMap.clear();
            if (zCheckAppWidgetNumByIds) {
                for (int i : iArr) {
                    AppWidgetBean appWidgetBeanById = AppWidgetUtils.getAppWidgetBeanById(i);
                    if (appWidgetBeanById != null) {
                        if (this.refreshAppWidgetMap.get(String.valueOf(appWidgetBeanById.getDeviceId())) == null) {
                            this.refreshAppWidgetMap.put(String.valueOf(appWidgetBeanById.getDeviceId()), new ArrayList());
                        }
                        List<AppWidgetBean> list = this.refreshAppWidgetMap.get(String.valueOf(appWidgetBeanById.getDeviceId()));
                        Objects.requireNonNull(list);
                        list.add(appWidgetBeanById);
                    }
                }
                if (this.refreshAppWidgetMap.size() > 0) {
                    this.requestCount = 0;
                    this.exceptCount = this.refreshAppWidgetMap.size();
                    this.pendingResult = goAsync();
                    for (Map.Entry<String, List<AppWidgetBean>> entry : this.refreshAppWidgetMap.entrySet()) {
                        List<AppWidgetBean> list2 = this.refreshAppWidgetMap.get(entry.getKey());
                        Objects.requireNonNull(list2);
                        AppWidgetBean appWidgetBean = list2.get(0);
                        ArrayList arrayList = new ArrayList();
                        List<AppWidgetBean> list3 = this.refreshAppWidgetMap.get(entry.getKey());
                        Objects.requireNonNull(list3);
                        Iterator<AppWidgetBean> it = list3.iterator();
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

    public final void getHomeCardDetail(AppWidgetBean appWidgetBean, Context context, Integer... numArr) {
        int deviceTypeByString = CommonUtils.getDeviceTypeByString(appWidgetBean.getType());
        PetkitLog.d("getHomeCardDetail type:" + deviceTypeByString + " id:" + appWidgetBean.getDeviceId());
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
            case 29:
                str = ApiTools.SAMPLE_API_W7H_REFRESH_HOME;
                break;
        }
        HashMap map = new HashMap();
        map.put("deviceId", String.valueOf(appWidgetBean.getDeviceId()));
        map.put("day", this.dateFormat.format(new Date()));
        if (!appWidgetBean.getPetId().isEmpty()) {
            map.put("petId", appWidgetBean.getPetId());
        }
        AsyncHttpUtil.post(str, (Map<String, String>) map, (AsyncHttpResponseHandler) new AsyncHttpRespHandler() { // from class: com.petkit.android.activities.appwidget.provider.FeederAppWidgetProvider.1
            public final /* synthetic */ Integer[] val$appWidgetIds;
            public final /* synthetic */ Context val$context;

            public AnonymousClass1(Integer[] numArr2, Context context2) {
                numArr = numArr2;
                context = context2;
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onStart() {
                super.onStart();
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                RefreshHomeRsp refreshHomeRsp = (RefreshHomeRsp) this.gson.fromJson(this.responseResult, RefreshHomeRsp.class);
                if (refreshHomeRsp.getError() != null) {
                    if (refreshHomeRsp.getError().getCode() == 705) {
                        for (Integer num : numArr) {
                            FeederAppWidgetProvider.this.refreshWidgetData(context, null, num.intValue());
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
                    FeederAppWidgetProvider.this.refreshWidgetData(context, devices.get(0), num2.intValue());
                }
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFinish() {
                FeederAppWidgetProvider.access$012(FeederAppWidgetProvider.this, 1);
                if (FeederAppWidgetProvider.this.requestCount == FeederAppWidgetProvider.this.exceptCount) {
                    try {
                        if (FeederAppWidgetProvider.this.pendingResult != null) {
                            FeederAppWidgetProvider.this.pendingResult.finish();
                        }
                    } catch (Exception e) {
                        PetkitLog.d("pendingResult:" + e.getMessage());
                        LogcatStorageHelper.addLog("pendingResult:" + e.getMessage());
                    }
                }
                super.onFinish();
            }
        }, false);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.appwidget.provider.FeederAppWidgetProvider$1 */
    public class AnonymousClass1 extends AsyncHttpRespHandler {
        public final /* synthetic */ Integer[] val$appWidgetIds;
        public final /* synthetic */ Context val$context;

        public AnonymousClass1(Integer[] numArr2, Context context2) {
            numArr = numArr2;
            context = context2;
        }

        @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
        public void onStart() {
            super.onStart();
        }

        @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
        public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
            super.onSuccess(i, headerArr, bArr);
            RefreshHomeRsp refreshHomeRsp = (RefreshHomeRsp) this.gson.fromJson(this.responseResult, RefreshHomeRsp.class);
            if (refreshHomeRsp.getError() != null) {
                if (refreshHomeRsp.getError().getCode() == 705) {
                    for (Integer num : numArr) {
                        FeederAppWidgetProvider.this.refreshWidgetData(context, null, num.intValue());
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
                FeederAppWidgetProvider.this.refreshWidgetData(context, devices.get(0), num2.intValue());
            }
        }

        @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
        public void onFinish() {
            FeederAppWidgetProvider.access$012(FeederAppWidgetProvider.this, 1);
            if (FeederAppWidgetProvider.this.requestCount == FeederAppWidgetProvider.this.exceptCount) {
                try {
                    if (FeederAppWidgetProvider.this.pendingResult != null) {
                        FeederAppWidgetProvider.this.pendingResult.finish();
                    }
                } catch (Exception e) {
                    PetkitLog.d("pendingResult:" + e.getMessage());
                    LogcatStorageHelper.addLog("pendingResult:" + e.getMessage());
                }
            }
            super.onFinish();
        }
    }
}

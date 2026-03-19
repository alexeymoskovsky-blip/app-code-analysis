package com.petkit.android.activities.go.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.location.LocationManager;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import androidx.browser.trusted.NotificationApiHelperForO$$ExternalSyntheticApiModelOutline5;
import androidx.core.app.NotificationChannelCompat$$ExternalSyntheticApiModelOutline26;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationCompatBuilder$$ExternalSyntheticApiModelOutline18;
import androidx.core.app.NotificationCompatBuilder$$ExternalSyntheticApiModelOutline19;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.facebook.login.widget.ToolTipPopup;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.petkit.android.activities.go.GoWalkingActivity;
import com.petkit.android.activities.go.model.GoRecord;
import com.petkit.android.activities.go.model.GoWalkData;
import com.petkit.android.activities.go.utils.GoDataUtils;
import com.petkit.android.activities.home.HomeActivity;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.AsyncHttpUtil;
import com.petkit.android.api.http.apiResponse.BaseRsp;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.LogcatStorageHelper;
import com.petkit.android.utils.PetkitLog;
import com.petkit.oversea.R;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.impl.auth.NTLMEngineImpl;
import io.agora.rtc2.internal.AudioRoutingController;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public class GoMainService extends Service {
    public static final int GO_WALKING_NOTIFICATION_ID = 2222;
    public BroadcastReceiver mBroadcastReceiver;
    public GoBackgroudThread mGoBackgroudThread;
    public NotificationManager mNotificationManager = null;
    public int mLastNotifyState = -1;
    public final ContentObserver mGpsMonitor = new ContentObserver(null) { // from class: com.petkit.android.activities.go.service.GoMainService.3
        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            super.onChange(z);
            int iIsProviderEnabled = ((LocationManager) GoMainService.this.getSystemService("location")).isProviderEnabled("gps");
            if (GoMainService.this.mGoBackgroudThread != null) {
                GoMainService.this.mGoBackgroudThread.writeData(0L, GoDataUtils.buildOpCodeBuffer(-40, iIsProviderEnabled));
            }
        }
    };

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        this.mNotificationManager = (NotificationManager) getSystemService("notification");
        int i = Build.VERSION.SDK_INT;
        if (i >= 26) {
            NotificationChannelCompat$$ExternalSyntheticApiModelOutline26.m();
            this.mNotificationManager.createNotificationChannel(NotificationApiHelperForO$$ExternalSyntheticApiModelOutline5.m(Constants.CHANNEL_NORMAL_ID, getString(R.string.Notification_Normal), 4));
            NotificationCompatBuilder$$ExternalSyntheticApiModelOutline19.m();
            Notification notificationBuild = NotificationCompatBuilder$$ExternalSyntheticApiModelOutline18.m(getApplicationContext(), Constants.CHANNEL_NORMAL_ID).build();
            try {
                if (i >= 34) {
                    startForeground(2222, notificationBuild, 8);
                } else {
                    startForeground(2222, notificationBuild);
                }
            } catch (Exception e) {
                LogcatStorageHelper.addLog("GoMainService :" + e.getMessage());
                PetkitLog.d("GoMainService:" + e.getMessage());
            }
        }
        startGPSMonitor();
        registerBoradcastReceiver();
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        stopGPSMonitor();
        unregisterBroadcastReceiver();
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) {
        int i3 = Build.VERSION.SDK_INT;
        if (i3 >= 26) {
            NotificationChannelCompat$$ExternalSyntheticApiModelOutline26.m();
            this.mNotificationManager.createNotificationChannel(NotificationApiHelperForO$$ExternalSyntheticApiModelOutline5.m(Constants.CHANNEL_NORMAL_ID, getString(R.string.Notification_Normal), 4));
            NotificationCompatBuilder$$ExternalSyntheticApiModelOutline19.m();
            Notification notificationBuild = NotificationCompatBuilder$$ExternalSyntheticApiModelOutline18.m(getApplicationContext(), Constants.CHANNEL_NORMAL_ID).build();
            if (i3 >= 34) {
                startForeground(2222, notificationBuild, 8);
            } else {
                startForeground(2222, notificationBuild);
            }
        }
        if (!getPackageManager().hasSystemFeature("android.hardware.bluetooth_le")) {
            return super.onStartCommand(intent, i, i2);
        }
        List<GoRecord> goRecordList = GoDataUtils.getGoRecordList();
        if (goRecordList != null && goRecordList.size() > 0) {
            GoBackgroudThread goBackgroudThread = this.mGoBackgroudThread;
            if (goBackgroudThread == null) {
                GoBackgroudThread goBackgroudThread2 = new GoBackgroudThread(this);
                this.mGoBackgroudThread = goBackgroudThread2;
                goBackgroudThread2.start();
            } else {
                goBackgroudThread.enableScan();
            }
            if (GoDataUtils.getGoWalkDataByState(1) != null) {
                startService(new Intent(this, (Class<?>) GoLocationService.class));
                notifyChanged(1);
            } else {
                notifyChanged(0);
            }
        } else {
            notifyChanged(0);
            stopSelf();
        }
        return super.onStartCommand(intent, i, i2);
    }

    public final void notifyChanged(int i) {
        notifyChanged(i, null);
    }

    public final void notifyChanged(int i, GoWalkData goWalkData) {
        PendingIntent activity;
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, Constants.CHANNEL_NORMAL_ID);
        if (i == 1) {
            Intent intent = new Intent();
            intent.setFlags(NTLMEngineImpl.FLAG_REQUEST_128BIT_KEY_EXCH);
            intent.setClass(this, GoWalkingActivity.class);
            if (Build.VERSION.SDK_INT >= 31) {
                activity = PendingIntent.getActivity(this, 1, intent, AudioRoutingController.DEVICE_OUT_USB_HEADSET);
            } else {
                activity = PendingIntent.getActivity(this, 1, intent, 134217728);
            }
            builder.setContentTitle(getString(R.string.Go_walking_doing)).setContentIntent(activity).setPriority(1).setAutoCancel(false).setOngoing(true).setDefaults(71).setSmallIcon(R.drawable.ic_launcher);
            if (goWalkData == null || goWalkData.getLowPowerStateWhileWalking() != 1) {
                i = 4;
            } else {
                builder.setContentText(getString(R.string.Go_walking_low_power_notice));
                i = 3;
            }
        } else if (i == 2) {
            List<GoWalkData> allGoWalkDataByState = GoDataUtils.getAllGoWalkDataByState(2);
            if (allGoWalkDataByState == null || allGoWalkDataByState.size() == 0) {
                i = 0;
            } else {
                builder.setContentTitle(getString(R.string.Go_walking_complete)).setContentText(getString(R.string.Go_walking_record_prompt_format, String.valueOf(allGoWalkDataByState.size()))).setContentIntent(getDefalutIntentByNumber()).setWhen(System.currentTimeMillis()).setPriority(1).setAutoCancel(true).setOngoing(false).setDefaults(7).setSmallIcon(R.drawable.ic_launcher);
            }
        }
        if (this.mLastNotifyState != i) {
            if (i == 0) {
                if (Build.VERSION.SDK_INT >= 26) {
                    stopForeground(1);
                } else {
                    this.mNotificationManager.cancel(2222);
                }
            } else {
                int i2 = Build.VERSION.SDK_INT;
                if (i2 < 26) {
                    this.mNotificationManager.notify(2222, builder.build());
                } else if (i2 >= 34) {
                    startForeground(2222, builder.build(), 8);
                } else {
                    startForeground(2222, builder.build());
                }
            }
            this.mLastNotifyState = i;
        }
    }

    public PendingIntent getDefalutIntentByNumber() {
        Intent intent = new Intent();
        intent.setFlags(NTLMEngineImpl.FLAG_REQUEST_128BIT_KEY_EXCH);
        List<GoWalkData> allGoWalkDataByState = GoDataUtils.getAllGoWalkDataByState(2);
        if (allGoWalkDataByState != null && allGoWalkDataByState.size() == 1) {
            intent.setClass(this, GoWalkingActivity.class);
            intent.putExtra(Constants.EXTRA_TAG_ID, allGoWalkDataByState.get(0).getId());
        } else {
            intent.setClass(this, HomeActivity.class);
        }
        if (Build.VERSION.SDK_INT >= 31) {
            return PendingIntent.getActivity(this, 1, intent, AudioRoutingController.DEVICE_OUT_USB_HEADSET);
        }
        return PendingIntent.getActivity(this, 1, intent, 134217728);
    }

    public final void registerBoradcastReceiver() {
        this.mBroadcastReceiver = new BroadcastReceiver() { // from class: com.petkit.android.activities.go.service.GoMainService.1
            /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
            /* JADX WARN: Removed duplicated region for block: B:4:0x001b  */
            @Override // android.content.BroadcastReceiver
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public void onReceive(android.content.Context r14, android.content.Intent r15) {
                /*
                    Method dump skipped, instruction units count: 620
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.petkit.android.activities.go.service.GoMainService.AnonymousClass1.onReceive(android.content.Context, android.content.Intent):void");
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(GoDataUtils.BROADCAST_GO_WRITE);
        intentFilter.addAction(GoDataUtils.BROADCAST_GO_DELETE);
        intentFilter.addAction(GoDataUtils.BROADCAST_GO_UPLOAD_CONFIG);
        intentFilter.addAction(GoDataUtils.BROADCAST_GO_START_SCAN);
        intentFilter.addAction(GoDataUtils.BROADCAST_GO_START_CALL);
        intentFilter.addAction(GoDataUtils.BROADCAST_GO_WALKING_STATE_CHANGED);
        intentFilter.addAction(Constants.BROADCAST_MSG_LOGOUT);
        intentFilter.addAction(GoDataUtils.BROADCAST_GO_BACKGROUND_THREAD_CANCELED);
        intentFilter.addAction(GoDataUtils.BROADCAST_GO_OTA_ID);
        LocalBroadcastManager.getInstance(this).registerReceiver(this.mBroadcastReceiver, intentFilter);
        if (Build.VERSION.SDK_INT >= 34) {
            registerReceiver(this.mBroadcastReceiver, intentFilter, 4);
        } else {
            registerReceiver(this.mBroadcastReceiver, intentFilter);
        }
    }

    public final void unregisterBroadcastReceiver() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(this.mBroadcastReceiver);
        unregisterReceiver(this.mBroadcastReceiver);
    }

    public void uploadGoConfig(GoRecord goRecord) {
        if (goRecord == null) {
            return;
        }
        HashMap map = new HashMap();
        map.put("id", "" + goRecord.getDeviceId());
        map.put("kv", goRecord.getCurrentKeyValue());
        AsyncHttpUtil.post(ApiTools.SAMPLE_API_GO_UPDATE, (Map<String, String>) map, (AsyncHttpResponseHandler) new AsyncHttpRespHandler() { // from class: com.petkit.android.activities.go.service.GoMainService.2
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                if (((BaseRsp) this.gson.fromJson(this.responseResult, BaseRsp.class)).getError() == null) {
                    LocalBroadcastManager.getInstance(GoMainService.this).sendBroadcast(new Intent(GoDataUtils.BROADCAST_GO_UPDATE));
                }
            }
        }, false);
    }

    public final boolean checkIfWalkingStartedJustNow(GoWalkData goWalkData) {
        return System.currentTimeMillis() - goWalkData.getTimeindex() <= ToolTipPopup.DEFAULT_POPUP_DISPLAY_TIME;
    }

    public void startGPSMonitor() {
        getContentResolver().registerContentObserver(Settings.Secure.getUriFor("location_providers_allowed"), false, this.mGpsMonitor);
    }

    public void stopGPSMonitor() {
        getContentResolver().unregisterContentObserver(this.mGpsMonitor);
    }
}

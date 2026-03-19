package com.petkit.android.activities.walkdog.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
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
import com.jess.arms.utils.DataHelper;
import com.petkit.android.activities.home.HomeActivity;
import com.petkit.android.activities.walkdog.WalkingActivity;
import com.petkit.android.activities.walkdog.model.WalkData;
import com.petkit.android.activities.walkdog.utils.WalkDataUtils;
import com.petkit.android.utils.Constants;
import com.petkit.oversea.R;
import cz.msebera.android.httpclient.impl.auth.NTLMEngineImpl;
import io.agora.rtc2.internal.AudioRoutingController;
import java.util.List;

/* JADX INFO: loaded from: classes6.dex */
public class WalkMainService extends Service {
    public static final int WALKING_NOTIFICATION_ID = 2222;
    public BroadcastReceiver mBroadcastReceiver;
    public NotificationManager mNotificationManager = null;
    public int mLastNotifyState = -1;
    public final ContentObserver mGpsMonitor = new ContentObserver(null) { // from class: com.petkit.android.activities.walkdog.service.WalkMainService.2
        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            super.onChange(z);
            ((LocationManager) WalkMainService.this.getSystemService("location")).isProviderEnabled("gps");
        }
    };

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    @SuppressLint({"WrongConstant"})
    public void onCreate() {
        super.onCreate();
        DataHelper.setStringSF(this, "finishTime", "");
        this.mNotificationManager = (NotificationManager) getSystemService("notification");
        int i = Build.VERSION.SDK_INT;
        if (i >= 26) {
            NotificationChannelCompat$$ExternalSyntheticApiModelOutline26.m();
            this.mNotificationManager.createNotificationChannel(NotificationApiHelperForO$$ExternalSyntheticApiModelOutline5.m(Constants.CHANNEL_NORMAL_ID, getString(R.string.Notification_Normal), 4));
            NotificationCompatBuilder$$ExternalSyntheticApiModelOutline19.m();
            Notification notificationBuild = NotificationCompatBuilder$$ExternalSyntheticApiModelOutline18.m(getApplicationContext(), Constants.CHANNEL_NORMAL_ID).build();
            if (i >= 34) {
                startForeground(2222, notificationBuild, 8);
            } else {
                startForeground(2222, notificationBuild);
            }
        }
        startGPSMonitor();
        registerBroadcastReceiver();
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        DataHelper.setStringSF(this, "finishTime", String.valueOf(System.currentTimeMillis()));
        stopGPSMonitor();
        unregisterBroadcastReceiver();
    }

    @Override // android.app.Service
    @SuppressLint({"WrongConstant"})
    public int onStartCommand(Intent intent, int i, int i2) {
        DataHelper.setStringSF(this, "finishTime", "");
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
        if (WalkDataUtils.getWalkDataByState(1) != null) {
            startService(new Intent(this, (Class<?>) LocationHelperService.class));
            startService(new Intent(this, (Class<?>) WalkLocationService.class));
            startService(new Intent(this, (Class<?>) LockViewService.class));
            notifyChanged(1);
        } else {
            notifyChanged(0);
        }
        return super.onStartCommand(intent, i, i2);
    }

    public final void notifyChanged(int i) {
        notifyChanged(i, null);
    }

    @SuppressLint({"WrongConstant"})
    public final void notifyChanged(int i, WalkData walkData) {
        PendingIntent activity;
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, Constants.CHANNEL_NORMAL_ID);
        if (i == 1) {
            Intent intent = new Intent();
            intent.setFlags(NTLMEngineImpl.FLAG_REQUEST_128BIT_KEY_EXCH);
            intent.setClass(this, WalkingActivity.class);
            if (Build.VERSION.SDK_INT >= 31) {
                activity = PendingIntent.getActivity(this, 1, intent, AudioRoutingController.DEVICE_OUT_USB_HEADSET);
            } else {
                activity = PendingIntent.getActivity(this, 1, intent, 134217728);
            }
            builder.setContentTitle(getString(R.string.Go_walking_doing)).setFullScreenIntent(activity, true).setPriority(1).setAutoCancel(false).setOngoing(true).setDefaults(71).setSmallIcon(R.drawable.ic_launcher);
            if (walkData == null || walkData.getLowPowerStateWhileWalking() != 1) {
                i = 4;
            } else {
                builder.setContentText(getString(R.string.Go_walking_low_power_notice));
                i = 3;
            }
        } else if (i == 2) {
            List<WalkData> allWalkDataByState = WalkDataUtils.getAllWalkDataByState(2);
            if (allWalkDataByState == null || allWalkDataByState.size() == 0) {
                i = 0;
            } else {
                builder.setContentTitle(getString(R.string.Go_walking_complete)).setContentText(getString(R.string.Go_walking_record_prompt_format, String.valueOf(allWalkDataByState.size()))).setContentIntent(getDefaultIntentByNumber()).setWhen(System.currentTimeMillis()).setPriority(1).setAutoCancel(true).setOngoing(false).setDefaults(7).setSmallIcon(R.drawable.ic_launcher);
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

    public PendingIntent getDefaultIntentByNumber() {
        Intent intent = new Intent();
        intent.setFlags(NTLMEngineImpl.FLAG_REQUEST_128BIT_KEY_EXCH);
        List<WalkData> allWalkDataByState = WalkDataUtils.getAllWalkDataByState(2);
        if (allWalkDataByState != null && allWalkDataByState.size() == 1) {
            intent.setClass(this, WalkingActivity.class);
            intent.putExtra(Constants.EXTRA_TAG_ID, allWalkDataByState.get(0).getId());
        } else {
            intent.setClass(this, HomeActivity.class);
        }
        if (Build.VERSION.SDK_INT >= 31) {
            return PendingIntent.getActivity(this, 1, intent, AudioRoutingController.DEVICE_OUT_USB_HEADSET);
        }
        return PendingIntent.getActivity(this, 1, intent, 134217728);
    }

    public final void registerBroadcastReceiver() {
        this.mBroadcastReceiver = new BroadcastReceiver() { // from class: com.petkit.android.activities.walkdog.service.WalkMainService.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                action.hashCode();
                if (action.equals(Constants.BROADCAST_MSG_LOGOUT)) {
                    WalkMainService.this.notifyChanged(0);
                    DataHelper.setStringSF(WalkMainService.this.getApplication(), "finishTime", String.valueOf(System.currentTimeMillis()));
                } else if (action.equals(WalkDataUtils.BROADCAST_WALKING_STATE_CHANGED) && intent.getIntExtra(WalkDataUtils.EXTRA_WALK_DATA, -1) == 4) {
                    List<WalkData> allWalkDataByState = WalkDataUtils.getAllWalkDataByState(1);
                    if (allWalkDataByState == null || allWalkDataByState.size() == 0) {
                        WalkMainService.this.notifyChanged(0);
                    } else {
                        WalkMainService.this.notifyChanged(1);
                    }
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.BROADCAST_MSG_LOGOUT);
        intentFilter.addAction(WalkDataUtils.BROADCAST_WALKING_STATE_CHANGED);
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

    public void startGPSMonitor() {
        getContentResolver().registerContentObserver(Settings.Secure.getUriFor("location_providers_allowed"), false, this.mGpsMonitor);
    }

    public void stopGPSMonitor() {
        getContentResolver().unregisterContentObserver(this.mGpsMonitor);
    }
}

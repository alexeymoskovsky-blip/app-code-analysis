package com.petkit.android.activities.petkitBleDevice.vlog;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.widget.RemoteViews;
import androidx.browser.trusted.NotificationApiHelperForO$$ExternalSyntheticApiModelOutline5;
import androidx.core.app.NotificationChannelCompat$$ExternalSyntheticApiModelOutline26;
import androidx.core.app.NotificationCompatBuilder$$ExternalSyntheticApiModelOutline18;
import androidx.core.app.NotificationCompatBuilder$$ExternalSyntheticApiModelOutline19;
import com.alibaba.fastjson.JSON;
import com.petkit.android.activities.petkitBleDevice.d4sh.D4shVlogActivity;
import com.petkit.android.activities.petkitBleDevice.d4sh.model.VlogStateChanged;
import com.petkit.android.activities.petkitBleDevice.d4sh.utils.VlogUtils;
import com.petkit.android.activities.petkitBleDevice.download.mode.MediaMsg;
import com.petkit.android.activities.petkitBleDevice.t6.T6VlogActivity;
import com.petkit.android.activities.petkitBleDevice.vlog.VlogGenerateThread;
import com.petkit.android.activities.petkitBleDevice.vlog.mode.VlogM3U8Mode;
import com.petkit.android.activities.petkitBleDevice.w7h.W7hVlogActivity;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.LogcatStorageHelper;
import com.petkit.android.utils.NotificationsUtils;
import com.petkit.android.utils.PetkitLog;
import com.petkit.oversea.R;
import io.agora.rtc2.internal.AudioRoutingController;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

/* JADX INFO: loaded from: classes5.dex */
public class VlogMakeService extends Service implements VlogGenerateThread.VlogGenerateListener {
    public static final int mNotificationId = 1147937;
    public Notification.Builder builder;
    public int cycle;
    public long deviceId;
    public int deviceType;
    public int id;
    public ProgressUpdateBinder mBinder;
    public Notification mNotification;
    public NotificationManager mNotificationManager;
    public HashMap<Integer, VlogGenerateThread> mVlogGenerateThreadHashMap;
    public int typeCode;

    @Override // com.petkit.android.activities.petkitBleDevice.vlog.VlogGenerateThread.VlogGenerateListener
    public void onStart(String str) {
    }

    @Override // android.app.Service
    @SuppressLint({"WrongConstant"})
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
        this.mBinder = new ProgressUpdateBinder(this);
        this.mVlogGenerateThreadHashMap = new HashMap<>();
        this.mNotificationManager = (NotificationManager) getSystemService("notification");
        int i = Build.VERSION.SDK_INT;
        if (i >= 26) {
            NotificationChannelCompat$$ExternalSyntheticApiModelOutline26.m();
            NotificationChannel notificationChannelM = NotificationApiHelperForO$$ExternalSyntheticApiModelOutline5.m(Constants.CHANNEL_VLOG_ID, getString(R.string.Notification_Upgrade), 4);
            notificationChannelM.setLockscreenVisibility(1);
            this.mNotificationManager.createNotificationChannel(notificationChannelM);
            NotificationCompatBuilder$$ExternalSyntheticApiModelOutline19.m();
            Notification.Builder smallIcon = NotificationCompatBuilder$$ExternalSyntheticApiModelOutline18.m(getApplicationContext(), Constants.CHANNEL_VLOG_ID).setAutoCancel(false).setOngoing(true).setCustomContentView(new RemoteViews(getApplication().getPackageName(), R.layout.notification_media_download)).setOnlyAlertOnce(true).setSmallIcon(R.drawable.ic_launcher);
            this.builder = smallIcon;
            this.mNotification = smallIcon.build();
            if (CommonUtils.isRunningForeground()) {
                if (i >= 29) {
                    startForeground(mNotificationId, this.mNotification, 1);
                    return;
                } else {
                    startForeground(mNotificationId, this.mNotification);
                    return;
                }
            }
            return;
        }
        Notification notification = new Notification();
        this.mNotification = notification;
        notification.icon = R.drawable.ic_launcher;
        notification.contentView = new RemoteViews(getApplication().getPackageName(), R.layout.notification_media_download);
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) {
        if (intent != null) {
            this.deviceId = intent.getLongExtra(Constants.EXTRA_DEVICE_ID, 0L);
            this.deviceType = intent.getIntExtra(Constants.EXTRA_DEVICE_TYPE, 0);
            this.id = intent.getIntExtra(Constants.EXTRA_VLOG_ID, 0);
            this.typeCode = intent.getIntExtra(Constants.EXTRA_DEVICE_TYPE_CODE, 0);
            this.cycle = intent.getIntExtra(Constants.EXTRA_VLOG_CYCLE, 0);
            startVlogGenerateThread(this.deviceId, this.deviceType, this.typeCode, this.id, JSON.parseArray(intent.getStringExtra(Constants.EXTRA_VLOG_M3U8_URL_LIST), VlogM3U8Mode.class), this.cycle);
        }
        return super.onStartCommand(intent, i, i2);
    }

    @SuppressLint({"WrongConstant"})
    public void setVlogMarkProgress(int i) {
        this.mNotification.contentView.setTextViewText(R.id.upgrade_name, getResources().getString(R.string.Vlog_is_being_made, i + "%"));
        this.mNotification.contentView.setProgressBar(R.id.app_upgrade_progressbar, 100, i, false);
        this.mNotificationManager.notify(mNotificationId, this.mNotification);
    }

    public void startVlogGenerateThread(long j, int i, int i2, int i3, List<VlogM3U8Mode> list, int i4) {
        Intent intentNewIntent;
        StringBuilder sb = new StringBuilder();
        sb.append("VlogMakeService startVlogGenerateThread deviceId:");
        sb.append(j);
        sb.append(" logId:");
        sb.append(i3);
        sb.append(" urlStrList size:");
        sb.append(list == null ? -1 : list.size());
        PetkitLog.d(sb.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append("VlogMakeService startVlogGenerateThread deviceId:");
        sb2.append(j);
        sb2.append(" logId:");
        sb2.append(i3);
        sb2.append(" urlStrList size:");
        sb2.append(list != null ? list.size() : -1);
        LogcatStorageHelper.addLog(sb2.toString());
        this.typeCode = i2;
        this.deviceType = i;
        this.mVlogGenerateThreadHashMap.put(Integer.valueOf(i3), new VlogGenerateThread(j, i, i2, String.valueOf(i3), i4, (ArrayList) list, this));
        VlogUtils.saveVlogMarkRecord(CommonUtils.getCurrentUserId(), i3, 4, 0);
        int iStartGenerateThread = startGenerateThread();
        PetkitLog.d("VlogMakeService startVlogGenerateThread startGenerateThread result:" + iStartGenerateThread);
        LogcatStorageHelper.addLog("VlogMakeService startVlogGenerateThread startGenerateThread result:" + iStartGenerateThread);
        if (iStartGenerateThread == 1 && this.mNotification.contentIntent == null) {
            switch (i) {
                case 25:
                    intentNewIntent = D4shVlogActivity.newIntent(getApplicationContext().getApplicationContext(), j, 0, 0);
                    break;
                case 26:
                    intentNewIntent = D4shVlogActivity.newIntent(getApplicationContext().getApplicationContext(), j, 1, 0);
                    break;
                case 27:
                    intentNewIntent = T6VlogActivity.newIntent(getApplicationContext().getApplicationContext(), j, i, 0);
                    break;
                case 28:
                default:
                    intentNewIntent = null;
                    break;
                case 29:
                    intentNewIntent = W7hVlogActivity.newIntent(getApplicationContext().getApplicationContext(), j, 0);
                    break;
            }
            int i5 = R.string.app_name;
            int i6 = Build.VERSION.SDK_INT;
            PendingIntent activity = PendingIntent.getActivity(this, i5, intentNewIntent, i6 >= 31 ? AudioRoutingController.DEVICE_OUT_USB_HEADSET : 134217728);
            Notification notification = this.mNotification;
            notification.contentIntent = activity;
            notification.contentView.setProgressBar(R.id.app_upgrade_progressbar, 100, 0, false);
            this.mNotification.contentView.setTextColor(R.id.app_upgrade_progresstext, CommonUtils.getColorById(R.color.black));
            this.mNotification.contentView.setTextColor(R.id.upgrade_name, CommonUtils.getColorById(R.color.black));
            this.mNotification.contentView.setTextViewText(R.id.upgrade_name, getResources().getString(R.string.Vlog_is_being_made, "0%"));
            if (i6 >= 34) {
                startForeground(mNotificationId, this.mNotification, 1);
            } else {
                startForeground(mNotificationId, this.mNotification);
            }
        }
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return this.mBinder;
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscriber
    public void downloadState(MediaMsg mediaMsg) {
        for (Map.Entry<Integer, VlogGenerateThread> entry : this.mVlogGenerateThreadHashMap.entrySet()) {
            if (entry.getValue() != null) {
                entry.getValue().downloadState(mediaMsg);
            }
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.vlog.VlogGenerateThread.VlogGenerateListener
    public void onComplete(String str, long j, String str2) {
        PetkitLog.d("onComplete id: " + str + ", url: " + str2);
        VlogMarkRecord vlogMarkRecord = VlogUtils.getVlogMarkRecord(CommonUtils.getCurrentUserId(), Integer.valueOf(str).intValue());
        if (vlogMarkRecord != null) {
            vlogMarkRecord.delete();
        }
        this.mVlogGenerateThreadHashMap.remove(Integer.valueOf(str));
        EventBus.getDefault().post(new VlogStateChanged(true));
        if (this.mVlogGenerateThreadHashMap.size() == 0) {
            stopForeground(true);
            stopSelf();
            switch (this.deviceType) {
                case 25:
                    NotificationsUtils.customNotify(this, mNotificationId, getString(R.string.Vlog_has_been_made), D4shVlogActivity.newIntent(this, j, 0, 0));
                    break;
                case 26:
                    NotificationsUtils.customNotify(this, mNotificationId, getString(R.string.Vlog_has_been_made), D4shVlogActivity.newIntent(this, j, 1, 0));
                    break;
                case 27:
                    NotificationsUtils.customNotify(this, mNotificationId, getString(R.string.Vlog_has_been_made), T6VlogActivity.newIntent(this, j, this.deviceType, 0));
                    break;
                case 29:
                    NotificationsUtils.customNotify(this, mNotificationId, getString(R.string.Vlog_has_been_made), W7hVlogActivity.newIntent(this, j, 0));
                    break;
            }
        }
        if (startGenerateThread() == -1) {
            stopForeground(true);
            stopSelf();
            switch (this.deviceType) {
                case 25:
                    NotificationsUtils.customNotify(this, mNotificationId, getString(R.string.Vlog_creation_failed), D4shVlogActivity.newIntent(this, j, 0, 0));
                    break;
                case 26:
                    NotificationsUtils.customNotify(this, mNotificationId, getString(R.string.Vlog_creation_failed), D4shVlogActivity.newIntent(this, j, 1, 0));
                    break;
                case 27:
                    NotificationsUtils.customNotify(this, mNotificationId, getString(R.string.Vlog_creation_failed), T6VlogActivity.newIntent(this, j, this.deviceType, 0));
                    break;
                case 29:
                    NotificationsUtils.customNotify(this, mNotificationId, getString(R.string.Vlog_creation_failed), W7hVlogActivity.newIntent(this, j, 0));
                    break;
            }
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.vlog.VlogGenerateThread.VlogGenerateListener
    public void onFailed(String str, long j, int i, String str2) {
        PetkitLog.d("onFailed id: " + str + ", error: " + str2);
        VlogMarkRecord vlogMarkRecord = VlogUtils.getVlogMarkRecord(CommonUtils.getCurrentUserId(), Integer.valueOf(str).intValue());
        if (vlogMarkRecord != null) {
            vlogMarkRecord.setStatus(2);
            vlogMarkRecord.save();
            EventBus.getDefault().post(new VlogStateChanged());
        }
        if (startGenerateThread() == -1) {
            stopForeground(true);
            stopSelf();
            switch (this.deviceType) {
                case 25:
                    NotificationsUtils.customNotify(this, mNotificationId, getString(R.string.Vlog_creation_failed), D4shVlogActivity.newIntent(this, j, 0, 0));
                    break;
                case 26:
                    NotificationsUtils.customNotify(this, mNotificationId, getString(R.string.Vlog_creation_failed), D4shVlogActivity.newIntent(this, j, 1, 0));
                    break;
                case 27:
                    NotificationsUtils.customNotify(this, mNotificationId, getString(R.string.Vlog_creation_failed), T6VlogActivity.newIntent(this, j, this.deviceType, 0));
                    break;
                case 29:
                    NotificationsUtils.customNotify(this, mNotificationId, getString(R.string.Vlog_creation_failed), W7hVlogActivity.newIntent(this, j, 0));
                    break;
            }
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.vlog.VlogGenerateThread.VlogGenerateListener
    public void onProgress(String str, int i, int i2) {
        VlogMarkRecord vlogMarkRecord = VlogUtils.getVlogMarkRecord(CommonUtils.getCurrentUserId(), Integer.valueOf(str).intValue());
        if (vlogMarkRecord != null) {
            vlogMarkRecord.setProgress(i2);
            vlogMarkRecord.save();
            EventBus.getDefault().post(new VlogStateChanged());
        }
        setVlogMarkProgress(i2);
    }

    public final int startGenerateThread() {
        boolean z;
        Iterator<Map.Entry<Integer, VlogGenerateThread>> it = this.mVlogGenerateThreadHashMap.entrySet().iterator();
        int iIntValue = 0;
        while (true) {
            if (!it.hasNext()) {
                z = false;
                break;
            }
            Map.Entry<Integer, VlogGenerateThread> next = it.next();
            VlogMarkRecord vlogMarkRecord = VlogUtils.getVlogMarkRecord(CommonUtils.getCurrentUserId(), next.getKey().intValue());
            if (vlogMarkRecord == null) {
                PetkitLog.d("VlogMakeService startGenerateThread VlogMarkRecord is null");
                LogcatStorageHelper.addLog("VlogMakeService startGenerateThread VlogMarkRecord is null");
            } else {
                PetkitLog.d("VlogMakeService startGenerateThread userId:" + vlogMarkRecord.getUserId() + " logId:" + vlogMarkRecord.getVlogId() + " status:" + vlogMarkRecord.getStatus());
                LogcatStorageHelper.addLog("VlogMakeService startGenerateThread userId:" + vlogMarkRecord.getUserId() + " logId:" + vlogMarkRecord.getVlogId() + " status:" + vlogMarkRecord.getStatus());
            }
            if (vlogMarkRecord != null && vlogMarkRecord.getStatus() == 1) {
                z = true;
                break;
            }
            if (vlogMarkRecord != null && vlogMarkRecord.getStatus() == 4) {
                iIntValue = next.getKey().intValue();
            }
        }
        PetkitLog.d("VlogMakeService startGenerateThread isGenerating:" + z);
        LogcatStorageHelper.addLog("VlogMakeService startGenerateThread isGenerating:" + z);
        if (z) {
            return 0;
        }
        if (iIntValue <= 0) {
            return -1;
        }
        new Thread(this.mVlogGenerateThreadHashMap.get(Integer.valueOf(iIntValue))).start();
        VlogMarkRecord vlogMarkRecord2 = VlogUtils.getVlogMarkRecord(CommonUtils.getCurrentUserId(), iIntValue);
        vlogMarkRecord2.setStatus(1);
        vlogMarkRecord2.save();
        PetkitLog.d("VlogMakeService startGenerateThread waitLogId:" + iIntValue);
        LogcatStorageHelper.addLog("VlogMakeService startGenerateThread waitLogId:" + iIntValue);
        return 1;
    }

    public static class ProgressUpdateBinder extends Binder {
        public WeakReference<VlogMakeService> vlogMarkServiceWeakReference;

        public ProgressUpdateBinder() {
        }

        public ProgressUpdateBinder(VlogMakeService vlogMakeService) {
            this.vlogMarkServiceWeakReference = new WeakReference<>(vlogMakeService);
        }

        public void setProgress(int i) {
            VlogMakeService vlogMakeService = this.vlogMarkServiceWeakReference.get();
            if (vlogMakeService != null) {
                vlogMakeService.setVlogMarkProgress(i);
            }
        }
    }
}

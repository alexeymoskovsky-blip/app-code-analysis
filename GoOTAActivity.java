package com.petkit.android.activities.go.setting;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.multidex.MultiDexExtractor;
import com.facebook.internal.ServerProtocol;
import com.google.gson.Gson;
import com.jess.arms.utils.Consts;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.petkit.android.activities.base.BaseActivity;
import com.petkit.android.activities.common.utils.DeviceCenterUtils;
import com.petkit.android.activities.go.model.GoRecord;
import com.petkit.android.activities.go.utils.GoDataUtils;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.AsyncHttpUtil;
import com.petkit.android.api.http.apiResponse.FirmwareCheckUpdateRsp;
import com.petkit.android.ble.BLEConsts;
import com.petkit.android.ble.service.AndroidBLEActionService;
import com.petkit.android.model.Device;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.FileUtils;
import com.petkit.android.utils.LogcatStorageHelper;
import com.petkit.android.utils.PetkitLog;
import com.petkit.oversea.R;
import cz.msebera.android.httpclient.Header;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public class GoOTAActivity extends BaseActivity {
    private ProgressBar downloadBar;
    private FirmwareCheckUpdateRsp firmwareCheckUpdateRsp;
    private AlertDialog mAlertDialog;
    private BroadcastReceiver mBroadcastReceiver;
    private Device mDevice;
    private GoRecord mGoRecord;
    private TextView mTopPromptTextView;
    private ImageView mobileImageView;
    private ImageView petkitImageView;
    private ImageView progressImageView;
    private TextView progressTextView;
    private Animation rotateAnimation;
    private ImageView serviceImageView;
    private Button startButton;
    private ProgressBar syncBar;
    private ImageView syncImageView;
    private ProgressBar updateBar;
    private int deviceConnectState = -1;
    private int mTotalSize = -1;

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        long longExtra;
        if (bundle == null) {
            longExtra = getIntent().getLongExtra(GoDataUtils.EXTRA_GO_ID, 0L);
        } else {
            longExtra = bundle.getLong(GoDataUtils.EXTRA_GO_ID);
        }
        this.mGoRecord = GoDataUtils.getGoRecordById(longExtra);
        super.onCreate(bundle);
        setContentView(R.layout.activity_go_update);
        getWindow().addFlags(128);
        registerBoradcastReceiver();
        sendGoOTAStateChangedBroadcast(true);
        if (this.mGoRecord == null) {
            finish();
        }
        List<GoRecord> goRecordListForWalking = GoDataUtils.getGoRecordListForWalking();
        if (goRecordListForWalking != null) {
            Iterator<GoRecord> it = goRecordListForWalking.iterator();
            while (it.hasNext()) {
                if (it.next().getDeviceId() == this.mGoRecord.getDeviceId()) {
                    showShortToast(R.string.Go_ota_failed_as_walking);
                    finish();
                    return;
                }
            }
        }
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        CommonUtils.addSysIntMap(this, Consts.SHARED_BLE_STATE, 1);
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        CommonUtils.addSysIntMap(this, Consts.SHARED_BLE_STATE, 0);
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putLong(GoDataUtils.EXTRA_GO_ID, this.mGoRecord.getDeviceId());
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        getWindow().clearFlags(128);
        Intent intent = new Intent(BLEConsts.BROADCAST_ACTION);
        intent.putExtra(BLEConsts.EXTRA_ACTION, 2);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        sendGoOTAStateChangedBroadcast(false);
        unregisterBroadcastReceiver();
    }

    @Override // com.petkit.android.activities.base.BaseActivity
    public void setupViews() {
        setTitle(R.string.OTA, R.color.white);
        setTitleLeftButton(R.drawable.btn_back_white);
        setTitleBackgroundColor(CommonUtils.getColorById(R.color.go_ota_bg));
        setDividerLineVisibility(8);
        this.syncBar = (ProgressBar) findViewById(R.id.ota_sync_progress);
        this.downloadBar = (ProgressBar) findViewById(R.id.ota_download_progress);
        this.updateBar = (ProgressBar) findViewById(R.id.ota_update_progress);
        this.syncImageView = (ImageView) findViewById(R.id.ota_sync_image);
        this.serviceImageView = (ImageView) findViewById(R.id.ota_service_image);
        this.mobileImageView = (ImageView) findViewById(R.id.ota_mobile_image);
        this.petkitImageView = (ImageView) findViewById(R.id.ota_petkit_image);
        this.progressTextView = (TextView) findViewById(R.id.ota_progress);
        this.progressImageView = (ImageView) findViewById(R.id.progress_img);
        this.rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.scan_rotate);
        this.rotateAnimation.setInterpolator(new LinearInterpolator());
        this.mTopPromptTextView = (TextView) findViewById(R.id.progress_top_prompt);
        Button button = (Button) findViewById(R.id.ota_start);
        this.startButton = button;
        button.setOnClickListener(this);
        changeStartButtonState(false);
    }

    @Override // com.petkit.android.activities.base.BaseActivity, android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() == R.id.ota_start) {
            int i = this.deviceConnectState;
            if (i == 3) {
                finish();
                return;
            }
            if (i != -1) {
                AlertDialog alertDialogCreate = new AlertDialog.Builder(this).setCancelable(true).setTitle(R.string.Prompt).setMessage(R.string.Confirm_stop_operation).setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() { // from class: com.petkit.android.activities.go.setting.GoOTAActivity.2
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i2) {
                        GoOTAActivity.this.finish();
                    }
                }).setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() { // from class: com.petkit.android.activities.go.setting.GoOTAActivity.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i2) {
                    }
                }).create();
                this.mAlertDialog = alertDialogCreate;
                alertDialogCreate.show();
            } else {
                changeDeviceConnectState(0);
                startOTA();
                changeStartButtonState(true);
            }
        }
    }

    @Override // androidx.core.app.ComponentActivity, android.app.Activity, android.view.Window.Callback
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == 4 && keyEvent.getAction() == 0 && this.deviceConnectState != -1) {
            return true;
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    @Override // com.petkit.android.activities.base.BaseActivity
    public void cancel(View view) {
        if (this.deviceConnectState == -1) {
            super.cancel(view);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeDeviceConnectState(int i) {
        this.deviceConnectState = i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopPetkitUpdate(boolean z) {
        if (isFinishing()) {
            return;
        }
        if (z && this.deviceConnectState == 3) {
            deviceUpdateSuccess();
            storeNewDeviceInfor();
            checkNotificationMessage();
            new HashMap().put("result", "success");
            this.startButton.setText(R.string.Done);
            this.progressImageView.clearAnimation();
            this.progressImageView.setImageResource(R.drawable.petkit_ota_progress_done);
            this.progressTextView.setText(R.string.BLEUI_OTA_completed);
            this.mTopPromptTextView.setText(R.string.Go_ota_notice_3);
            return;
        }
        this.mTopPromptTextView.setText(R.string.Go_ota_notice_1);
        changeDeviceConnectState(-1);
        changeStartButtonState(false);
        this.syncImageView.setBackgroundResource(R.drawable.ota_circle_normal_bg);
        this.serviceImageView.setBackgroundResource(R.drawable.ota_circle_normal_bg);
        this.mobileImageView.setBackgroundResource(R.drawable.ota_circle_normal_bg);
        this.petkitImageView.setBackgroundResource(R.drawable.ota_circle_normal_bg);
        this.progressTextView.setText("");
        this.syncBar.setProgress(0);
        this.downloadBar.setProgress(0);
        this.updateBar.setProgress(0);
        if (z) {
            AlertDialog alertDialog = this.mAlertDialog;
            if (alertDialog == null || !alertDialog.isShowing()) {
                new HashMap().put("result", "fail");
                LogcatStorageHelper.addLog(getString(R.string.BLEUI_OTA_notice_2));
                AlertDialog alertDialogCreate = new AlertDialog.Builder(this).setCancelable(true).setTitle(R.string.OTA).setMessage(R.string.BLEUI_OTA_error).setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() { // from class: com.petkit.android.activities.go.setting.GoOTAActivity.3
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).create();
                this.mAlertDialog = alertDialogCreate;
                alertDialogCreate.show();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkNotificationMessage() {
        if (DeviceCenterUtils.deleteGoOtaDevicesFlag(this.mGoRecord.getDeviceId())) {
            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(Constants.BROADCAST_MSG_UPDATE_DEVICE_UPDATE_NOTIFICATION));
        }
    }

    private void startOTA() {
        if (CommonUtils.getAndroidSDKVersion() >= 18) {
            if (getPackageManager().hasSystemFeature("android.hardware.bluetooth_le")) {
                Intent intent = new Intent(BLEConsts.BROADCAST_ACTION);
                intent.putExtra(BLEConsts.EXTRA_ACTION, 2);
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
                Intent intent2 = new Intent(this, (Class<?>) AndroidBLEActionService.class);
                intent2.putExtra(BLEConsts.EXTRA_ACTION, 12);
                intent2.putExtra(BLEConsts.EXTRA_DEVICE_ID, this.mGoRecord.getDeviceId());
                startService(intent2);
                this.mTopPromptTextView.setText(R.string.Go_ota_notice_2);
                return;
            }
            return;
        }
        showShortToast(R.string.Go_ble_not_supported, R.drawable.toast_failed);
    }

    private void changeStartButtonState(boolean z) {
        if (z) {
            this.startButton.setText(R.string.Cancel);
            this.startButton.setBackgroundResource(R.drawable.solid_black_bg);
            this.progressImageView.startAnimation(this.rotateAnimation);
            this.progressTextView.setText(R.string.BLEUI_OTA_checking);
            return;
        }
        this.startButton.setText(R.string.Start);
        this.startButton.setBackgroundResource(R.drawable.selector_solid_blue);
        this.progressImageView.clearAnimation();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopBLE() {
        Intent intent = new Intent(BLEConsts.BROADCAST_ACTION);
        intent.putExtra(BLEConsts.EXTRA_ACTION, 2);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private void checkPetkitUpdate() {
        HashMap map = new HashMap();
        map.put("id", "" + this.mGoRecord.getDeviceId());
        map.put("hardware", "" + this.mGoRecord.getHardware());
        map.put("firmware", "" + this.mGoRecord.getFirmware());
        post(ApiTools.SAMPLE_API_GO_UPGRADE_CHECK, map, new AsyncHttpRespHandler(this) { // from class: com.petkit.android.activities.go.setting.GoOTAActivity.4
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                FirmwareCheckUpdateRsp firmwareCheckUpdateRsp = (FirmwareCheckUpdateRsp) this.gson.fromJson(this.responseResult, FirmwareCheckUpdateRsp.class);
                if (firmwareCheckUpdateRsp.getError() != null) {
                    GoOTAActivity.this.showLongToast(firmwareCheckUpdateRsp.getError().getMsg(), R.drawable.toast_failed);
                    LogcatStorageHelper.addLog("check petkit device fail " + firmwareCheckUpdateRsp.getError().getMsg());
                    GoOTAActivity.this.stopPetkitUpdate(false);
                    GoOTAActivity.this.stopBLE();
                    new HashMap().put("result", "check update fail");
                    return;
                }
                if (firmwareCheckUpdateRsp.getResult() != null && firmwareCheckUpdateRsp.getResult().getFile() != null) {
                    GoOTAActivity.this.firmwareCheckUpdateRsp = firmwareCheckUpdateRsp;
                    GoOTAActivity.this.mTotalSize = firmwareCheckUpdateRsp.getResult().getFile().getSize();
                    LogcatStorageHelper.addLog("check petkit device success, start download new version.");
                    GoOTAActivity.this.download(firmwareCheckUpdateRsp.getResult().getFile().getUrl());
                    return;
                }
                LogcatStorageHelper.addLog("check petkit device success, no new version.");
                GoOTAActivity.this.stopPetkitUpdate(false);
                GoOTAActivity.this.checkNotificationMessage();
                new AlertDialog.Builder(GoOTAActivity.this).setCancelable(true).setTitle(R.string.Prompt).setMessage(R.string.Hint_no_update).setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() { // from class: com.petkit.android.activities.go.setting.GoOTAActivity.4.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i2) {
                        GoOTAActivity.this.finish();
                    }
                }).show();
                new HashMap().put("result", "no new version");
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFailure(int i, Header[] headerArr, byte[] bArr, Throwable th) {
                super.onFailure(i, headerArr, bArr, th);
                LogcatStorageHelper.addLog("check petkit device version fail!");
                GoOTAActivity.this.showLongToast(R.string.Hint_network_failed);
                GoOTAActivity.this.stopPetkitUpdate(false);
                new HashMap().put("result", "check update fail");
                GoOTAActivity.this.stopBLE();
            }
        }, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void download(String str) {
        AsyncHttpUtil.get(str, new AsyncHttpRespHandler(this) { // from class: com.petkit.android.activities.go.setting.GoOTAActivity.5
            @Override // com.loopj.android.http.AsyncHttpResponseHandler
            public void onProgress(long j, long j2) {
                super.onProgress(j, j2);
                if (GoOTAActivity.this.deviceConnectState == -1) {
                    return;
                }
                int i = (int) ((j * 100) / ((long) GoOTAActivity.this.mTotalSize));
                GoOTAActivity.this.downloadBar.setProgress(i);
                GoOTAActivity.this.progressTextView.setText(GoOTAActivity.this.getString(R.string.BLEUI_OTA_dowloading_from_server) + i + "%");
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                if (GoOTAActivity.this.deviceConnectState == -1 || GoOTAActivity.this.isFinishing()) {
                    return;
                }
                if (bArr == null || bArr.length < 10) {
                    LogcatStorageHelper.addLog("[ERROR] download new version file null");
                    GoOTAActivity.this.showLongToast(R.string.Hint_network_failed);
                    GoOTAActivity.this.stopBLE();
                    new HashMap().put("result", "download fail");
                    return;
                }
                LogcatStorageHelper.addLog("download new version success.");
                GoOTAActivity.this.mobileImageView.setBackgroundResource(R.drawable.ota_circle_hl_bg);
                ((BaseActivity) GoOTAActivity.this).localTempImageFileName = new Date().getTime() + MultiDexExtractor.EXTRACTED_SUFFIX;
                File file = new File(FileUtils.getAppCacheImageDirPath());
                if (!file.exists()) {
                    file.mkdirs();
                }
                FileUtils.writeStringToFile(FileUtils.getAppCacheImageDirPath() + ((BaseActivity) GoOTAActivity.this).localTempImageFileName, bArr);
                Intent intent = new Intent(BLEConsts.BROADCAST_ACTION);
                intent.putExtra(BLEConsts.EXTRA_ACTION, 1);
                intent.putExtra(BLEConsts.EXTRA_FILE_PATH, FileUtils.getAppCacheImageDirPath() + ((BaseActivity) GoOTAActivity.this).localTempImageFileName);
                LocalBroadcastManager.getInstance(GoOTAActivity.this).sendBroadcast(intent);
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFailure(int i, Header[] headerArr, byte[] bArr, Throwable th) {
                super.onFailure(i, headerArr, bArr, th);
                if (GoOTAActivity.this.deviceConnectState == -1) {
                    return;
                }
                LogcatStorageHelper.addLog("download new version fail.");
                GoOTAActivity.this.showLongToast(R.string.Hint_network_failed);
                GoOTAActivity.this.stopPetkitUpdate(false);
                new HashMap().put("result", "download fail");
                GoOTAActivity.this.stopBLE();
            }
        });
    }

    private void deviceUpdateSuccess() {
        if (this.firmwareCheckUpdateRsp.getResult() == null) {
            return;
        }
        HashMap map = new HashMap();
        map.put("deviceId", String.valueOf(this.mGoRecord.getDeviceId()));
        map.put("hardware", String.valueOf(this.firmwareCheckUpdateRsp.getResult().getHardware().getId()));
        map.put("firmware", String.valueOf(this.firmwareCheckUpdateRsp.getResult().getVersion()));
        map.put("oldFirmware", String.valueOf(this.mGoRecord.getFirmware()));
        map.put("success", ServerProtocol.DIALOG_RETURN_SCOPES_TRUE);
        LogcatStorageHelper.addLog("petkit device update success, notify service hardware: " + this.firmwareCheckUpdateRsp.getResult().getHardware().getId() + " version: " + this.firmwareCheckUpdateRsp.getResult().getVersion());
        AsyncHttpUtil.post(ApiTools.SAMPLE_API_GO_UPGRADE_REPORT, (Map<String, String>) map, (AsyncHttpResponseHandler) new AsyncHttpRespHandler(this) { // from class: com.petkit.android.activities.go.setting.GoOTAActivity.6
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
            }
        }, false);
    }

    private void registerBoradcastReceiver() {
        this.mBroadcastReceiver = new BroadcastReceiver() { // from class: com.petkit.android.activities.go.setting.GoOTAActivity.7
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(BLEConsts.BROADCAST_PROGRESS)) {
                    int intExtra = intent.getIntExtra(BLEConsts.EXTRA_PROGRESS, 0);
                    GoOTAActivity.this.updateBLESyncProgress(intExtra, intent.getStringExtra(BLEConsts.EXTRA_DATA));
                    if (intExtra == -6 && GoOTAActivity.this.updateBar.getProgress() == 100) {
                        GoOTAActivity.this.changeDeviceConnectState(3);
                        GoOTAActivity.this.petkitImageView.setBackgroundResource(R.drawable.ota_circle_hl_bg);
                        GoOTAActivity.this.stopPetkitUpdate(true);
                        return;
                    }
                    return;
                }
                if (intent.getAction().equals(BLEConsts.BROADCAST_ERROR)) {
                    intent.getIntExtra(BLEConsts.EXTRA_DATA, 0);
                    GoOTAActivity.this.changeDeviceConnectState(-1);
                    GoOTAActivity.this.stopPetkitUpdate(true);
                } else if (intent.getAction().equals(BLEConsts.BROADCAST_LOG)) {
                    PetkitLog.d(String.format("BROADCAST_LOG: data = %s", intent.getStringExtra(BLEConsts.EXTRA_LOG_MESSAGE)));
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BLEConsts.BROADCAST_LOG);
        intentFilter.addAction(BLEConsts.BROADCAST_PROGRESS);
        intentFilter.addAction(BLEConsts.BROADCAST_ERROR);
        LocalBroadcastManager.getInstance(this).registerReceiver(this.mBroadcastReceiver, intentFilter);
    }

    private void unregisterBroadcastReceiver() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(this.mBroadcastReceiver);
    }

    private void storeNewDeviceInfor() {
        this.mGoRecord.setHardware(this.firmwareCheckUpdateRsp.getResult().getHardware().getId());
        this.mGoRecord.setFirmware(this.firmwareCheckUpdateRsp.getResult().getVersion());
        this.mGoRecord.save();
        DeviceCenterUtils.deleteGoOtaDevicesFlag(this.mGoRecord.getDeviceId());
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(Constants.BROADCAST_MSG_DEVICE_UPDATE));
        Intent intent = new Intent(GoDataUtils.BROADCAST_GO_UPDATE);
        intent.putExtra(GoDataUtils.EXTRA_GO_ID, this.mGoRecord.getDeviceId());
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateBLESyncProgress(int i, String str) {
        PetkitLog.d("updateBLESyncProgress progress= " + i + "////data=" + str);
        if (i == -20) {
            changeDeviceConnectState(9);
        } else if (i == -18) {
            this.serviceImageView.setBackgroundResource(R.drawable.ota_circle_hl_bg);
            this.syncBar.setProgress(100);
            if (!isEmpty(str) && !Boolean.valueOf(str).booleanValue()) {
                GoRecord goRecord = this.mGoRecord;
                goRecord.setFirmware(goRecord.getFirmware() - 1);
            }
            checkPetkitUpdate();
            updateBLESyncProgress(-20, null);
        } else if (i != -11) {
            if (i == -5) {
                changeDeviceConnectState(-1);
            } else if (i == -1) {
                changeDeviceConnectState(1);
                this.progressTextView.setText(R.string.BLEUI_connecting);
                LogcatStorageHelper.addLog("find device, begin connect");
            } else if (i != -9) {
                if (i == -8) {
                    changeDeviceConnectState(0);
                    this.progressTextView.setText(R.string.BLEUI_scanning);
                    this.syncImageView.setBackgroundResource(R.drawable.ota_circle_hl_bg);
                } else {
                    switch (i) {
                        case -15:
                        case -14:
                            changeDeviceConnectState(-1);
                            new HashMap().put("result", "scan fail");
                            changeStartButtonState(false);
                            stopPetkitUpdate(false);
                            this.progressTextView.setText(R.string.BLEUI_scan_timeout);
                            break;
                        case -13:
                            break;
                        default:
                            int i2 = this.deviceConnectState;
                            if (-1 == i2) {
                                return;
                            }
                            if (i >= 0 && i <= 100) {
                                if (this.syncBar.getProgress() == 100) {
                                    this.updateBar.setProgress(i);
                                    if (i == 100) {
                                        this.progressTextView.setText(R.string.BLEUI_OTA_confirming);
                                        changeDeviceConnectState(11);
                                    } else {
                                        this.progressTextView.setText(getString(R.string.BLEUI_OTA_dowloading) + i + "%");
                                    }
                                } else {
                                    this.syncBar.setProgress(i);
                                    changeDeviceConnectState(2);
                                    if (i == 100) {
                                        this.progressTextView.setText(R.string.Synchronized);
                                    } else {
                                        this.progressTextView.setText(getString(R.string.Synchronizing) + i + "%");
                                    }
                                }
                            } else if (i > 100) {
                                if (i == 4096) {
                                    if (i2 == 11) {
                                        changeDeviceConnectState(3);
                                    } else if (i2 == 9 && this.mDevice.getHardware() == 2) {
                                        return;
                                    }
                                }
                                stopPetkitUpdate(true);
                            }
                            break;
                    }
                }
            } else if (isEmpty(str)) {
                return;
            } else {
                this.mDevice = (Device) new Gson().fromJson(str, Device.class);
            }
        } else if (isEmpty(str) || this.mDevice == null) {
            return;
        }
        PetkitLog.d(String.format("BROADCAST_PROGRESS: op = %s, data = %s", Integer.valueOf(i), str));
    }

    private void sendGoOTAStateChangedBroadcast(boolean z) {
        Intent intent = new Intent(GoDataUtils.BROADCAST_GO_OTA_ID);
        intent.putExtra(GoDataUtils.EXTRA_GO_ID, this.mGoRecord.getDeviceId());
        intent.putExtra(GoDataUtils.EXTRA_GO_DATA, z);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}

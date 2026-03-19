package com.petkit.android.activities.go.bind;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.google.gson.Gson;
import com.jess.arms.widget.LoadDialog;
import com.petkit.android.activities.base.BaseActivity;
import com.petkit.android.activities.go.ApiResponse.GoResultRsp;
import com.petkit.android.activities.go.model.Go;
import com.petkit.android.activities.go.model.GoRecord;
import com.petkit.android.activities.go.service.GoMainService;
import com.petkit.android.activities.go.setting.GoSettingTargetActivity;
import com.petkit.android.activities.go.utils.GoDataUtils;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.ble.BLEConsts;
import com.petkit.android.ble.DeviceInfo;
import com.petkit.android.model.Device;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.PetkitLog;
import com.petkit.oversea.R;
import cz.msebera.android.httpclient.Header;
import java.util.HashMap;

/* JADX INFO: loaded from: classes3.dex */
public class GoBindingActivity extends BaseActivity {
    private boolean isBindSuccess = false;
    private DeviceInfo mBleDeviceInfo;
    private BroadcastReceiver mBroadcastReceiver;
    private Go mDevice;

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        if (bundle == null) {
            this.mBleDeviceInfo = (DeviceInfo) getIntent().getSerializableExtra(BLEConsts.EXTRA_DEVICE_INFO);
        } else {
            this.mBleDeviceInfo = (DeviceInfo) bundle.getSerializable(BLEConsts.EXTRA_DEVICE_INFO);
        }
        super.onCreate(bundle);
        setContentView(R.layout.activity_go_binding);
        registerBoradcastReceiver();
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putSerializable(BLEConsts.EXTRA_DEVICE_INFO, this.mBleDeviceInfo);
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        dismissLoadDialog();
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(BLEConsts.BROADCAST_ACTION);
        intent.putExtra(BLEConsts.EXTRA_ACTION, 2);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        unregisterBroadcastReceiver();
    }

    @Override // com.petkit.android.activities.base.BaseActivity
    public void setupViews() {
        setTitle(R.string.Go_binding, R.color.white);
        setTitleStatus("(3/4)", 0, CommonUtils.getColorById(R.color.white), 0);
        setTitleLeftButton(R.drawable.icon_back_white_normal);
        setTitleBackgroundColor(Color.parseColor("#ff2e2e2e"));
        setDividerLineVisibility(8);
        findViewById(R.id.go_next).setOnClickListener(this);
    }

    @Override // com.petkit.android.activities.base.BaseActivity, android.view.View.OnClickListener
    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.go_next) {
            signupDevice();
        }
    }

    private void signupDevice() {
        this.isBindSuccess = false;
        if (this.mBleDeviceInfo == null) {
            return;
        }
        HashMap map = new HashMap();
        map.put("mac", this.mBleDeviceInfo.getMac().toLowerCase());
        if (this.mBleDeviceInfo.getDeviceId() != 0) {
            map.put("id", String.valueOf(this.mBleDeviceInfo.getDeviceId()));
        }
        showLoadDialog();
        post(ApiTools.SAMPLE_API_GO_SIGNUP, map, new AsyncHttpRespHandler(this) { // from class: com.petkit.android.activities.go.bind.GoBindingActivity.1
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFinish() {
                super.onFinish();
                if (GoBindingActivity.this.isBindSuccess) {
                    return;
                }
                LoadDialog.dismissDialog();
                Intent intent = new Intent(BLEConsts.BROADCAST_ACTION);
                intent.putExtra(BLEConsts.EXTRA_ACTION, 2);
                LocalBroadcastManager.getInstance(GoBindingActivity.this).sendBroadcast(intent);
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                GoResultRsp goResultRsp = (GoResultRsp) this.gson.fromJson(this.responseResult, GoResultRsp.class);
                if (goResultRsp.getError() != null) {
                    GoBindingActivity.this.showShortToast(goResultRsp.getError().getMsg(), R.drawable.toast_failed);
                    GoBindingActivity.this.finish();
                    return;
                }
                if (goResultRsp.getResult() != null) {
                    GoBindingActivity.this.mDevice = goResultRsp.getResult();
                    GoBindingActivity.this.isBindSuccess = true;
                    String strValueOf = String.valueOf(goResultRsp.getResult().getId());
                    int length = strValueOf.length();
                    if (length < 16) {
                        for (int i2 = 0; i2 < 16 - length; i2++) {
                            strValueOf = "0" + strValueOf;
                        }
                    }
                    Intent intent = new Intent(BLEConsts.BROADCAST_ACTION);
                    intent.putExtra(BLEConsts.EXTRA_ACTION, 1);
                    intent.putExtra(BLEConsts.EXTRA_SECRET, goResultRsp.getResult().getSecret());
                    intent.putExtra(BLEConsts.EXTRA_DEVICE_ID, strValueOf);
                    LocalBroadcastManager.getInstance(GoBindingActivity.this).sendBroadcast(intent);
                    return;
                }
                GoBindingActivity.this.showLongToast(R.string.BLEUI_bind_PETKIT_failed);
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFailure(int i, Header[] headerArr, byte[] bArr, Throwable th) {
                super.onFailure(i, headerArr, bArr, th);
                GoBindingActivity.this.showLongToast(R.string.BLEUI_bind_PETKIT_failed);
            }
        }, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void bindDevice() {
        HashMap map = new HashMap();
        map.put("id", this.mDevice.getId() + "");
        map.put("mac", this.mBleDeviceInfo.getMac().toLowerCase());
        map.put("secret", this.mDevice.getSecret());
        map.put("hardware", this.mDevice.getHardware() + "");
        map.put("firmware", this.mDevice.getFirmware() + "");
        map.put(Constants.HOME_TODO_CARD_DEVICE_ERR_BATTERY, this.mDevice.getBattery() + "");
        map.put("voltage", this.mDevice.getVoltage() + "");
        post(ApiTools.SAMPLE_API_GO_LINK, map, new AsyncHttpRespHandler() { // from class: com.petkit.android.activities.go.bind.GoBindingActivity.2
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFinish() {
                super.onFinish();
                LoadDialog.dismissDialog();
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                GoResultRsp goResultRsp = (GoResultRsp) this.gson.fromJson(this.responseResult, GoResultRsp.class);
                if (goResultRsp.getError() != null) {
                    GoBindingActivity.this.showLongToast(goResultRsp.getError().getMsg(), R.drawable.toast_failed);
                    GoBindingActivity.this.finish();
                    return;
                }
                new HashMap().put("type", "go");
                GoRecord orCreateGoRecord = GoDataUtils.getOrCreateGoRecord(goResultRsp.getResult());
                orCreateGoRecord.save();
                int i2 = Build.VERSION.SDK_INT;
                if (i2 >= 31) {
                    if (CommonUtils.isRunningForeground()) {
                        if (i2 >= 26) {
                            GoBindingActivity.this.startForegroundService(new Intent(GoBindingActivity.this, (Class<?>) GoMainService.class));
                        } else {
                            GoBindingActivity.this.startService(new Intent(GoBindingActivity.this, (Class<?>) GoMainService.class));
                        }
                        LocalBroadcastManager.getInstance(GoBindingActivity.this).sendBroadcast(new Intent(Constants.BROADCAST_MSG_DEVICE_UPDATE));
                    }
                } else {
                    if (i2 >= 26) {
                        GoBindingActivity.this.startForegroundService(new Intent(GoBindingActivity.this, (Class<?>) GoMainService.class));
                    } else {
                        GoBindingActivity.this.startService(new Intent(GoBindingActivity.this, (Class<?>) GoMainService.class));
                    }
                    LocalBroadcastManager.getInstance(GoBindingActivity.this).sendBroadcast(new Intent(Constants.BROADCAST_MSG_DEVICE_UPDATE));
                }
                Bundle bundle = new Bundle();
                bundle.putLong(GoDataUtils.EXTRA_GO_ID, orCreateGoRecord.getDeviceId());
                bundle.putInt(Constants.EXTRA_TYPE, 1);
                GoBindingActivity.this.startActivityWithData(GoSettingTargetActivity.class, bundle, false);
            }
        }, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void bindFailed(String str) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.EXTRA_STRING_ID, str);
        startActivityWithData(GoBindFailedActivity.class, bundle, true);
    }

    private void registerBoradcastReceiver() {
        this.mBroadcastReceiver = new BroadcastReceiver() { // from class: com.petkit.android.activities.go.bind.GoBindingActivity.3
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                PetkitLog.d("" + intent.getAction());
                if (intent.getAction().equals(BLEConsts.BROADCAST_PROGRESS)) {
                    int intExtra = intent.getIntExtra(BLEConsts.EXTRA_PROGRESS, 0);
                    String stringExtra = intent.getStringExtra(BLEConsts.EXTRA_DATA);
                    if (intExtra != -14) {
                        if (intExtra == -11) {
                            Device device = (Device) new Gson().fromJson(stringExtra, Device.class);
                            GoBindingActivity.this.mDevice.setBattery(device.getBattery());
                            GoBindingActivity.this.mDevice.setVoltage(device.getVoltage());
                            return;
                        } else {
                            if (intExtra == -9) {
                                Device device2 = (Device) new Gson().fromJson(stringExtra, Device.class);
                                GoBindingActivity.this.mDevice.setFirmware(device2.getFirmware());
                                GoBindingActivity.this.mDevice.setHardware(device2.getHardware());
                                GoBindingActivity.this.mDevice.setBattery(100);
                                return;
                            }
                            if (intExtra != 4096 && intExtra != 32769 && intExtra != 32937) {
                                if (intExtra == -6) {
                                    GoBindingActivity.this.bindDevice();
                                    return;
                                } else if (intExtra != -5) {
                                    return;
                                }
                            }
                        }
                    }
                    GoBindingActivity goBindingActivity = GoBindingActivity.this;
                    goBindingActivity.bindFailed(goBindingActivity.getString(R.string.Go_binding_failed));
                    return;
                }
                if (intent.getAction().equals(BLEConsts.BROADCAST_ERROR)) {
                    if (intent.getIntExtra(BLEConsts.EXTRA_DATA, 0) != 4097) {
                        GoBindingActivity goBindingActivity2 = GoBindingActivity.this;
                        goBindingActivity2.bindFailed(goBindingActivity2.getString(R.string.Go_binding_failed));
                        return;
                    }
                    return;
                }
                if (intent.getAction().equals(GoDataUtils.BROADCAST_GO_BIND_COMPLETE)) {
                    GoBindingActivity.this.finish();
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BLEConsts.BROADCAST_PROGRESS);
        intentFilter.addAction(BLEConsts.BROADCAST_ERROR);
        intentFilter.addAction(BLEConsts.BROADCAST_LOG);
        intentFilter.addAction(BLEConsts.BROADCAST_SCANED_DEVICE);
        intentFilter.addAction(GoDataUtils.BROADCAST_GO_BIND_COMPLETE);
        LocalBroadcastManager.getInstance(this).registerReceiver(this.mBroadcastReceiver, intentFilter);
    }

    private void unregisterBroadcastReceiver() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(this.mBroadcastReceiver);
    }
}

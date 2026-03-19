package com.petkit.android.activities.fit;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.facebook.devicerequests.internal.DeviceRequestsHelper;
import com.google.gson.Gson;
import com.jess.arms.widget.LoadDialog;
import com.petkit.android.activities.base.BaseActivity;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.apiResponse.BindDeviceRsp;
import com.petkit.android.api.http.apiResponse.LoginRsp;
import com.petkit.android.api.http.apiResponse.SignupDeviceRsp;
import com.petkit.android.ble.BLEConsts;
import com.petkit.android.ble.DeviceInfo;
import com.petkit.android.ble.service.AndroidBLEActionService;
import com.petkit.android.model.Device;
import com.petkit.android.model.Pet;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.PetkitLog;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.oversea.R;
import cz.msebera.android.httpclient.Header;
import java.util.HashMap;
import java.util.Iterator;

/* JADX INFO: loaded from: classes3.dex */
public class PetkitDeviceBindActivity extends BaseActivity {
    private Button bindButton;
    private Pet curPet;
    private TextView fitDevice;
    private ImageView light;
    private DeviceInfo mBleDeviceInfo;
    private BroadcastReceiver mBroadcastReceiver;
    private Device mDevice;
    private int count = 1;
    private long delayMillis = 600;
    private Handler handler = new Handler();
    private boolean isBindSuccess = false;

    public static /* synthetic */ int access$108(PetkitDeviceBindActivity petkitDeviceBindActivity) {
        int i = petkitDeviceBindActivity.count;
        petkitDeviceBindActivity.count = i + 1;
        return i;
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addFlags(128);
        if (bundle != null) {
            this.mBleDeviceInfo = (DeviceInfo) bundle.getSerializable(BLEConsts.EXTRA_DEVICE_INFO);
            this.curPet = (Pet) bundle.getSerializable(Constants.EXTRA_DOG);
        } else {
            this.mBleDeviceInfo = (DeviceInfo) getIntent().getSerializableExtra(BLEConsts.EXTRA_DEVICE_INFO);
            this.curPet = (Pet) getIntent().getSerializableExtra(Constants.EXTRA_DOG);
        }
        setContentView(R.layout.activity_petkit_bind);
        registerBoradcastReceiver();
        Intent intent = new Intent(BLEConsts.BROADCAST_ACTION);
        intent.putExtra(BLEConsts.EXTRA_ACTION, 2);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        Bundle bundle2 = new Bundle();
        bundle2.putInt(BLEConsts.EXTRA_ACTION, this.mBleDeviceInfo.getDeviceId() == 0 ? 5 : 6);
        bundle2.putSerializable(BLEConsts.EXTRA_DEVICE_INFO, this.mBleDeviceInfo);
        startBLEAction(bundle2);
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putSerializable(BLEConsts.EXTRA_DEVICE_INFO, this.mBleDeviceInfo);
        bundle.putSerializable(Constants.EXTRA_DOG, this.curPet);
    }

    @Override // com.petkit.android.activities.base.BaseActivity
    public void cancel(View view) {
        Intent intent = new Intent(BLEConsts.BROADCAST_ACTION);
        intent.putExtra(BLEConsts.EXTRA_ACTION, 2);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        super.cancel(view);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(BLEConsts.BROADCAST_ACTION);
        intent.putExtra(BLEConsts.EXTRA_ACTION, 2);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        unregisterBroadcastReceiver();
    }

    @Override // com.petkit.android.activities.base.BaseActivity
    public void setupViews() {
        setTitle(R.string.Device_add, R.color.white);
        setTitleLeftButton(R.drawable.btn_back_white);
        setTitleBackgroundColor(CommonUtils.getColorById(R.color.petkit_ota_bg));
        setDividerLineVisibility(8);
        this.fitDevice = (TextView) findViewById(R.id.fit_device);
        this.light = (ImageView) findViewById(R.id.light);
        if (this.mBleDeviceInfo.getName().equalsIgnoreCase(BLEConsts.PET_FIT2_DISPLAY_NAME)) {
            this.fitDevice.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.icon_bind_p2, 0, 0);
            this.light.setImageResource(R.drawable.icon_bind_light_p2);
        } else if (this.mBleDeviceInfo.getName().equalsIgnoreCase(BLEConsts.PET_FIT_DISPLAY_NAME)) {
            this.fitDevice.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.icon_bind_p1, 0, 0);
            this.light.setImageResource(R.drawable.icon_bind_light_p1);
        }
        Button button = (Button) findViewById(R.id.fit_bind);
        this.bindButton = button;
        button.setOnClickListener(this);
        this.bindButton.setClickable(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshDeviceBindView() {
        bindFlashAnimation();
        this.bindButton.setClickable(true);
        this.bindButton.setBackgroundResource(R.drawable.selector_solid_yellow);
    }

    @Override // com.petkit.android.activities.base.BaseActivity, android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() == R.id.fit_bind) {
            signupDevice();
        }
    }

    @Override // androidx.core.app.ComponentActivity, android.app.Activity, android.view.Window.Callback
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return super.dispatchKeyEvent(keyEvent);
    }

    private void bindFlashAnimation() {
        new Thread(new flashRunnable()).start();
    }

    public class flashRunnable implements Runnable {
        public flashRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (PetkitDeviceBindActivity.this.count % 2 == 0) {
                PetkitDeviceBindActivity.this.light.setVisibility(0);
            } else {
                PetkitDeviceBindActivity.this.light.setVisibility(4);
            }
            if (PetkitDeviceBindActivity.this.count >= 7) {
                PetkitDeviceBindActivity.this.handler.removeCallbacks(this);
            } else {
                PetkitDeviceBindActivity.this.handler.postDelayed(this, PetkitDeviceBindActivity.this.delayMillis);
            }
            PetkitDeviceBindActivity.access$108(PetkitDeviceBindActivity.this);
        }
    }

    private void startBLEAction(Bundle bundle) {
        if (CommonUtils.getAndroidSDKVersion() >= 18) {
            if (getPackageManager().hasSystemFeature("android.hardware.bluetooth_le")) {
                Intent intent = new Intent(this, (Class<?>) AndroidBLEActionService.class);
                intent.putExtras(bundle);
                startService(intent);
                return;
            }
            return;
        }
        showShortToast(R.string.BLEUI_not_supported);
    }

    private void signupDevice() {
        if (this.mBleDeviceInfo == null) {
            return;
        }
        HashMap map = new HashMap();
        map.put("mac", this.mBleDeviceInfo.getMac());
        if (this.mBleDeviceInfo.getDeviceId() != 0) {
            map.put("id", String.valueOf(this.mBleDeviceInfo.getDeviceId()));
        }
        showLoadDialog();
        post(ApiTools.SAMPLE_API_DEVICE_SIGNUP, map, new AsyncHttpRespHandler(this) { // from class: com.petkit.android.activities.fit.PetkitDeviceBindActivity.1
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFinish() {
                super.onFinish();
                if (PetkitDeviceBindActivity.this.isBindSuccess) {
                    return;
                }
                LoadDialog.dismissDialog();
                Intent intent = new Intent(BLEConsts.BROADCAST_ACTION);
                intent.putExtra(BLEConsts.EXTRA_ACTION, 2);
                LocalBroadcastManager.getInstance(PetkitDeviceBindActivity.this).sendBroadcast(intent);
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                SignupDeviceRsp signupDeviceRsp = (SignupDeviceRsp) this.gson.fromJson(this.responseResult, SignupDeviceRsp.class);
                if (signupDeviceRsp.getError() != null) {
                    PetkitDeviceBindActivity.this.showShortToast(signupDeviceRsp.getError().getMsg(), R.drawable.toast_failed);
                    return;
                }
                if (signupDeviceRsp.getResult() != null) {
                    PetkitDeviceBindActivity.this.mDevice = signupDeviceRsp.getResult();
                    PetkitDeviceBindActivity.this.isBindSuccess = true;
                    Intent intent = new Intent(BLEConsts.BROADCAST_ACTION);
                    intent.putExtra(BLEConsts.EXTRA_ACTION, 1);
                    intent.putExtra(BLEConsts.EXTRA_SECRET_KEY, signupDeviceRsp.getResult().getSecretKey());
                    intent.putExtra(BLEConsts.EXTRA_SECRET, signupDeviceRsp.getResult().getSecret());
                    intent.putExtra(BLEConsts.EXTRA_DEVICE_ID, String.valueOf(signupDeviceRsp.getResult().getId()));
                    LocalBroadcastManager.getInstance(PetkitDeviceBindActivity.this).sendBroadcast(intent);
                    return;
                }
                PetkitDeviceBindActivity.this.showLongToast(R.string.BLEUI_bind_PETKIT_failed);
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFailure(int i, Header[] headerArr, byte[] bArr, Throwable th) {
                super.onFailure(i, headerArr, bArr, th);
                PetkitDeviceBindActivity.this.showLongToast(R.string.BLEUI_bind_PETKIT_failed);
            }
        }, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void bindDevice(String str) {
        HashMap map = new HashMap();
        map.put("petId", this.curPet.getId());
        map.put(DeviceRequestsHelper.DEVICE_INFO_DEVICE, str);
        post(ApiTools.SAMPLE_API_PET_BINDDEVICE, map, new AsyncHttpRespHandler() { // from class: com.petkit.android.activities.fit.PetkitDeviceBindActivity.2
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFinish() {
                super.onFinish();
                LoadDialog.dismissDialog();
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                BindDeviceRsp bindDeviceRsp = (BindDeviceRsp) this.gson.fromJson(this.responseResult, BindDeviceRsp.class);
                if (bindDeviceRsp.getError() == null) {
                    PetkitDeviceBindActivity.this.curPet.setDevice(PetkitDeviceBindActivity.this.mDevice);
                    UserInforUtils.updateDogInformation(PetkitDeviceBindActivity.this.curPet, 3);
                    Intent intent = new Intent();
                    intent.setAction(Constants.BROADCAST_MSG_UPDATE_DOG);
                    intent.putExtra(Constants.EXTRA_DOG, PetkitDeviceBindActivity.this.curPet);
                    LocalBroadcastManager.getInstance(PetkitDeviceBindActivity.this).sendBroadcast(intent);
                    PetkitDeviceBindActivity.this.storeUserInfor();
                    PetkitDeviceBindActivity.this.showLongToast(R.string.Succeed, R.drawable.toast_succeed);
                    PetkitDeviceBindActivity.this.setResult(-1);
                    PetkitDeviceBindActivity.this.finish();
                    return;
                }
                PetkitDeviceBindActivity.this.showLongToast(bindDeviceRsp.getError().getMsg(), R.drawable.toast_failed);
            }
        }, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void storeUserInfor() {
        LoginRsp.LoginResult currentLoginResult = UserInforUtils.getCurrentLoginResult();
        if (currentLoginResult == null) {
            return;
        }
        if (currentLoginResult.getUser().getDogs() != null) {
            Iterator<Pet> it = currentLoginResult.getUser().getDogs().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Pet next = it.next();
                if (next.getId().equals(this.curPet.getId())) {
                    next.setDevice(this.curPet.getDevice());
                    break;
                }
            }
        }
        UserInforUtils.updateLoginResult(currentLoginResult);
    }

    private void registerBoradcastReceiver() {
        this.mBroadcastReceiver = new BroadcastReceiver() { // from class: com.petkit.android.activities.fit.PetkitDeviceBindActivity.3
            @Override // android.content.BroadcastReceiver
            @SuppressLint({"DefaultLocale"})
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(BLEConsts.BROADCAST_PROGRESS)) {
                    int intExtra = intent.getIntExtra(BLEConsts.EXTRA_PROGRESS, 0);
                    String stringExtra = intent.getStringExtra(BLEConsts.EXTRA_DATA);
                    if (intExtra == -16) {
                        LoadDialog.dismissDialog();
                        PetkitDeviceBindActivity.this.refreshDeviceBindView();
                    } else if (intExtra == -11) {
                        PetkitLog.d("battery: " + stringExtra);
                        if (PetkitDeviceBindActivity.this.isEmpty(stringExtra) || PetkitDeviceBindActivity.this.mDevice == null) {
                            return;
                        } else {
                            try {
                                PetkitDeviceBindActivity.this.mDevice.setBattery(Integer.valueOf(stringExtra).intValue());
                            } catch (Exception unused) {
                            }
                        }
                    } else if (intExtra != -9) {
                        if (intExtra == -6) {
                            PetkitDeviceBindActivity.this.bindDevice(new Gson().toJson(PetkitDeviceBindActivity.this.mDevice));
                        } else if (intExtra == -5) {
                            LoadDialog.dismissDialog();
                        }
                    } else {
                        if (PetkitDeviceBindActivity.this.isEmpty(stringExtra)) {
                            return;
                        }
                        Device device = (Device) new Gson().fromJson(stringExtra, Device.class);
                        PetkitDeviceBindActivity.this.mDevice.setFirmware(device.getFirmware());
                        PetkitDeviceBindActivity.this.mDevice.setHardware(device.getHardware());
                        PetkitDeviceBindActivity.this.mDevice.setFrequence(device.getFrequence());
                        PetkitDeviceBindActivity.this.mDevice.setExtra(device.getExtra());
                    }
                    PetkitLog.d(String.format("BROADCAST_PROGRESS: op = %s, data = %s", Integer.valueOf(intExtra), stringExtra));
                    return;
                }
                if (intent.getAction().equals(BLEConsts.BROADCAST_ERROR)) {
                    int intExtra2 = intent.getIntExtra(BLEConsts.EXTRA_DATA, 0);
                    PetkitLog.d(String.format("BROADCAST_ERROR: error = %d ", Integer.valueOf(intExtra2)));
                    if (intExtra2 == 4097) {
                        LoadDialog.dismissDialog();
                        return;
                    }
                    LoadDialog.dismissDialog();
                    PetkitDeviceBindActivity.this.showShortToast(R.string.BLEUI_connect_failed);
                    PetkitDeviceBindActivity.this.finish();
                    return;
                }
                if (intent.getAction().equals(BLEConsts.BROADCAST_LOG)) {
                    PetkitLog.d(String.format("BROADCAST_LOG: data = %s", intent.getStringExtra(BLEConsts.EXTRA_LOG_MESSAGE)));
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BLEConsts.BROADCAST_PROGRESS);
        intentFilter.addAction(BLEConsts.BROADCAST_ERROR);
        intentFilter.addAction(BLEConsts.BROADCAST_LOG);
        intentFilter.addAction(BLEConsts.BROADCAST_SCANED_DEVICE);
        LocalBroadcastManager.getInstance(this).registerReceiver(this.mBroadcastReceiver, intentFilter);
    }

    private void unregisterBroadcastReceiver() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(this.mBroadcastReceiver);
    }
}

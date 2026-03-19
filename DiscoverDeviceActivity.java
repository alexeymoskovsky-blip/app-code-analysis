package com.petkit.android.activities.device;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.view.LayoutInflaterCompat;
import com.jess.arms.base.PetKitTextFactory;
import com.jess.arms.utils.DeviceUtils;
import com.jess.arms.widget.LoadDialog;
import com.jess.arms.widget.PetkitToast;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.petkitBleDevice.BleCameraBindActivity;
import com.petkit.android.activities.petkitBleDevice.DeviceBindSetWifiActivity;
import com.petkit.android.activities.petkitBleDevice.t6.mode.NotifyCopyWritingGifVideoEvent;
import com.petkit.android.activities.petkitBleDevice.utils.FamilyUtils;
import com.petkit.android.ble.BLEConsts;
import com.petkit.android.ble.DeviceScanResult;
import com.petkit.android.ble.StopDeviceScanMsg;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.oversea.R;
import java.lang.ref.WeakReference;
import org.simple.eventbus.EventBus;

/* JADX INFO: loaded from: classes3.dex */
public class DiscoverDeviceActivity extends Activity implements View.OnClickListener {
    private DeviceScanResult deviceScanResult;
    private WeakReference<PetKitTextFactory> factoryWeakReference;
    private boolean isTopActivity;
    private ImageView ivDeviceCancel;
    private ImageView ivDeviceIcon;
    private LinearLayout llWindowPanel;
    private TextView tvDeviceConfirm;
    private TextView tvDeviceContent;

    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        DeviceUtils.switchLanguage(this, DeviceUtils.getLanguageLocale(this));
        setupLayoutInflaterFactory();
        if (bundle == null) {
            this.deviceScanResult = (DeviceScanResult) getIntent().getParcelableExtra(Constants.EXTRA_DEVICE_RESULT);
        } else {
            this.deviceScanResult = (DeviceScanResult) bundle.getParcelable(Constants.EXTRA_DEVICE_RESULT);
        }
        setContentView(R.layout.activity_discover_device);
        setFinishOnTouchOutside(false);
        if (this.deviceScanResult == null) {
            finish();
        } else {
            initView();
        }
    }

    @Override // android.app.Activity
    public void onStart() {
        super.onStart();
        this.isTopActivity = true;
    }

    @Override // android.app.Activity
    public void onResume() {
        super.onResume();
        this.isTopActivity = true;
    }

    @Override // android.app.Activity
    public void onPause() {
        super.onPause();
        this.isTopActivity = false;
    }

    @Override // android.app.Activity
    public void onStop() {
        super.onStop();
        LoadDialog.dismissDialog();
    }

    @Override // android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putParcelable(Constants.EXTRA_DEVICE_RESULT, this.deviceScanResult);
    }

    @Override // android.app.Activity
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_from_bottom, R.anim.slide_out_to_bottom);
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return (keyEvent.getKeyCode() == 4 && keyEvent.getAction() == 0) || super.dispatchKeyEvent(keyEvent);
    }

    private void initView() {
        this.llWindowPanel = (LinearLayout) findViewById(R.id.ll_window_panel);
        this.tvDeviceContent = (TextView) findViewById(R.id.tv_device_content);
        this.ivDeviceIcon = (ImageView) findViewById(R.id.iv_device_icon);
        this.ivDeviceCancel = (ImageView) findViewById(R.id.iv_device_cancel);
        TextView textView = (TextView) findViewById(R.id.tv_device_confirm);
        this.tvDeviceConfirm = textView;
        textView.setOnClickListener(this);
        this.tvDeviceConfirm.setText(getResources().getString(R.string.Connect_instantly) + ">");
        this.ivDeviceCancel.setOnClickListener(this);
        Window window = getWindow();
        window.setGravity(80);
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = (int) (((double) BaseApplication.displayMetrics.widthPixels) * 0.9d);
        window.setAttributes(attributes);
        window.getDecorView().setBackgroundColor(0);
        switch (this.deviceScanResult.getDeviceType()) {
            case 5:
                this.ivDeviceIcon.setImageResource(R.drawable.dev_home_discover_cozy);
                this.tvDeviceContent.setText(getString(R.string.Cozy));
                break;
            case 6:
                this.ivDeviceIcon.setImageResource(R.drawable.dev_home_discover_d2);
                this.tvDeviceContent.setText(getString(R.string.D2));
                break;
            case 7:
                this.ivDeviceIcon.setImageResource(R.drawable.dev_home_discover_t3);
                this.tvDeviceContent.setText(getString(R.string.Device_t3_name));
                break;
            case 8:
                this.ivDeviceIcon.setImageResource(R.drawable.dev_home_discover_k2);
                this.tvDeviceContent.setText(getString(R.string.Device_k2_name));
                break;
            case 9:
                this.ivDeviceIcon.setImageResource(R.drawable.dev_home_discover_d3);
                this.tvDeviceContent.setText(getString(R.string.Device_d3_name));
                break;
            case 10:
                if (BLEConsts.AQ_NAME.equals(this.deviceScanResult.getDeviceInfo().getName())) {
                    this.ivDeviceIcon.setImageResource(R.drawable.dev_home_discover_aq);
                    this.tvDeviceContent.setText(getString(R.string.Device_aq_name));
                }
                if (BLEConsts.AQ1S_NAME.equals(this.deviceScanResult.getDeviceInfo().getName())) {
                    this.ivDeviceIcon.setImageResource(R.drawable.dev_home_discover_aq1s);
                    this.tvDeviceContent.setText(getString(R.string.Device_aq1s_name));
                } else {
                    this.ivDeviceIcon.setImageResource(R.drawable.dev_home_discover_aq2);
                    this.tvDeviceContent.setText(getString(R.string.Device_aq2_name));
                }
                break;
            case 11:
                if (BLEConsts.D4_1_NAME.equals(this.deviceScanResult.getDeviceInfo().getName())) {
                    this.ivDeviceIcon.setImageResource(R.drawable.dev_home_discover_d4_1);
                    this.tvDeviceContent.setText(getString(R.string.Device_d4_1_name));
                } else if (BLEConsts.D4_2_NAME.equals(this.deviceScanResult.getDeviceInfo().getName())) {
                    this.ivDeviceIcon.setImageResource(R.drawable.dev_home_discover_d4_1);
                    this.tvDeviceContent.setText(getString(R.string.Device_d4_name));
                } else {
                    this.ivDeviceIcon.setImageResource(R.drawable.dev_home_discover_d4_1);
                    this.tvDeviceContent.setText(getString(R.string.Device_d4_name));
                }
                break;
            case 12:
                if (this.deviceScanResult.getDeviceInfo().getName().equals(BLEConsts.P3D_NAME)) {
                    this.ivDeviceIcon.setImageResource(R.drawable.dev_home_discover_p3d);
                } else {
                    this.ivDeviceIcon.setImageResource(R.drawable.dev_home_discover_p3d);
                }
                this.tvDeviceContent.setText(getString(R.string.Device_p3_name));
                break;
            case 14:
                if (BLEConsts.W5C_NAME.equals(this.deviceScanResult.getDeviceInfo().getName())) {
                    this.ivDeviceIcon.setImageResource(R.drawable.dev_home_discover_w5c);
                    this.tvDeviceContent.setText(getString(R.string.W5c_name_default));
                } else if (BLEConsts.W5_NAME.equals(this.deviceScanResult.getDeviceInfo().getName())) {
                    this.ivDeviceIcon.setImageResource(R.drawable.dev_home_discover_w5c);
                    this.tvDeviceContent.setText(getString(R.string.W5_name_default));
                } else if (BLEConsts.W4X_NAME.equals(this.deviceScanResult.getDeviceInfo().getName())) {
                    this.ivDeviceIcon.setImageResource(R.drawable.dev_home_discover_w4x);
                    this.tvDeviceContent.setText(getString(R.string.W4X_name_default));
                } else if (BLEConsts.W4X_UVC_NAME.equals(this.deviceScanResult.getDeviceInfo().getName())) {
                    this.ivDeviceIcon.setImageResource(R.drawable.dev_home_discover_w4x);
                    this.tvDeviceContent.setText(getString(R.string.W4X_UVC_name_default));
                } else if (BLEConsts.W5N_NAME.equals(this.deviceScanResult.getDeviceInfo().getName())) {
                    this.ivDeviceIcon.setImageResource(R.drawable.dev_home_discover_w5n);
                    this.tvDeviceContent.setText(getString(R.string.W5N_name_default));
                } else if (BLEConsts.CTW2_NAME.equals(this.deviceScanResult.getDeviceInfo().getName())) {
                    this.ivDeviceIcon.setImageResource(R.drawable.dev_home_discover_ctw2);
                    this.tvDeviceContent.setText(getString(R.string.CTW2_name_default));
                } else if (BLEConsts.W4X_UVC_NAME.equals(this.deviceScanResult.getDeviceInfo().getName())) {
                    this.ivDeviceIcon.setImageResource(R.drawable.dev_discover_ctw2);
                    this.tvDeviceContent.setText(getString(R.string.Discover_new_device_prompt, getString(R.string.CTW2_name_default)));
                }
                break;
            case 15:
                if (BLEConsts.T4_2_NAME.equals(this.deviceScanResult.getDeviceInfo().getName())) {
                    this.ivDeviceIcon.setImageResource(R.drawable.dev_home_discover_t4_2);
                    this.tvDeviceContent.setText(getString(R.string.Device_t4_2_name));
                } else {
                    this.ivDeviceIcon.setImageResource(R.drawable.dev_home_discover_t4);
                    this.tvDeviceContent.setText(getString(R.string.Device_t4_name));
                }
                break;
            case 16:
                this.ivDeviceIcon.setImageResource(R.drawable.dev_home_discover_k3);
                this.tvDeviceContent.setText(getString(R.string.Device_k3_name));
                break;
            case 17:
                this.ivDeviceIcon.setImageResource(R.drawable.dev_home_discover_aqr);
                this.tvDeviceContent.setText(getString(R.string.Device_aqr_name));
                break;
            case 18:
                this.ivDeviceIcon.setImageResource(R.drawable.dev_home_discover_r2);
                this.tvDeviceContent.setText(getString(R.string.Device_R2_name));
                break;
            case 19:
                if (BLEConsts.AQH1_L_NAME.equals(this.deviceScanResult.getDeviceInfo().getName()) || BLEConsts.AQH1_H_NAME.equals(this.deviceScanResult.getDeviceInfo().getName())) {
                    this.ivDeviceIcon.setImageResource(R.drawable.dev_home_discover_aqh1_l);
                }
                this.tvDeviceContent.setText(getString(R.string.Device_aqh1_name));
                break;
            case 20:
                this.ivDeviceIcon.setImageResource(R.drawable.dev_home_discover_d4s);
                this.tvDeviceContent.setText(getString(R.string.Device_d4s_name));
                break;
            case 21:
                if (BLEConsts.T5_2_NAME.equals(this.deviceScanResult.getDeviceInfo().getName())) {
                    this.ivDeviceIcon.setImageResource(R.drawable.dev_home_discover_t5_2);
                    this.tvDeviceContent.setText(getString(R.string.Device_t5_2_name));
                } else {
                    this.ivDeviceIcon.setImageResource(R.drawable.dev_home_discover_t5);
                    this.tvDeviceContent.setText(getString(R.string.Device_t5_name));
                }
                EventBus.getDefault().post(new NotifyCopyWritingGifVideoEvent(21));
                break;
            case 22:
                this.ivDeviceIcon.setImageResource(R.drawable.dev_home_discover_hg);
                this.tvDeviceContent.setText(getString(R.string.Device_hg_name));
                break;
            case 24:
                if (BLEConsts.CTW3_2_NAME.equals(this.deviceScanResult.getDeviceInfo().getName())) {
                    this.ivDeviceIcon.setImageResource(R.drawable.dev_home_discover_ctw3);
                    this.tvDeviceContent.setText(getString(R.string.Device_ctw3_name));
                } else if (BLEConsts.CTW3_NAME.equals(this.deviceScanResult.getDeviceInfo().getName())) {
                    this.ivDeviceIcon.setImageResource(R.drawable.dev_home_discover_ctw3);
                    this.tvDeviceContent.setText(getString(R.string.Device_ctw3_name));
                } else if (BLEConsts.CTW3_UV_NAME.equals(this.deviceScanResult.getDeviceInfo().getName())) {
                    this.ivDeviceIcon.setImageResource(R.drawable.dev_home_discover_ctw3);
                    this.tvDeviceContent.setText(getString(R.string.Device_ctw3_name));
                } else if (BLEConsts.CTW3_100_NAME.equals(this.deviceScanResult.getDeviceInfo().getName())) {
                    this.ivDeviceIcon.setImageResource(R.drawable.dev_home_discover_ctw3_100);
                    this.tvDeviceContent.setText(getString(R.string.Device_ctw3_100_name));
                } else if (BLEConsts.CTW3_UV_100_NAME.equals(this.deviceScanResult.getDeviceInfo().getName())) {
                    this.ivDeviceIcon.setImageResource(R.drawable.dev_home_discover_ctw3_100);
                    this.tvDeviceContent.setText(getString(R.string.Device_ctw3_100_name));
                }
                break;
            case 25:
                if ("Petkit_A_D4SH2".equals(this.deviceScanResult.getDeviceInfo().getName())) {
                    this.ivDeviceIcon.setImageResource(R.drawable.dev_home_discover_d4sh_2);
                    this.tvDeviceContent.setText(getString(R.string.Device_d4sh_name));
                } else if (BLEConsts.D4SH_3_NAME.equals(this.deviceScanResult.getDeviceInfo().getName()) || BLEConsts.D4SH_3_A_NAME.equals(this.deviceScanResult.getDeviceInfo().getName())) {
                    this.ivDeviceIcon.setImageResource(R.drawable.dev_home_discover_d4sh_3);
                    this.tvDeviceContent.setText(getString(R.string.Device_d4sh3_name));
                } else {
                    this.ivDeviceIcon.setImageResource(R.drawable.dev_home_discover_d4sh);
                    this.tvDeviceContent.setText(getString(R.string.Device_d4sh_name));
                }
                break;
            case 26:
                if ("Petkit_A_D4H2".equals(this.deviceScanResult.getDeviceInfo().getName())) {
                    this.ivDeviceIcon.setImageResource(R.drawable.dev_home_discover_d4h_2);
                    this.tvDeviceContent.setText(getString(R.string.Device_d4h_name));
                } else if (BLEConsts.D4H_3_NAME.equals(this.deviceScanResult.getDeviceInfo().getName()) || BLEConsts.D4H_3_A_NAME.equals(this.deviceScanResult.getDeviceInfo().getName())) {
                    this.ivDeviceIcon.setImageResource(R.drawable.dev_home_discover_d4h_3);
                    this.tvDeviceContent.setText(getString(R.string.Device_d4h3_name));
                } else {
                    this.ivDeviceIcon.setImageResource(R.drawable.dev_home_discover_d4h);
                    this.tvDeviceContent.setText(getString(R.string.Device_d4h_name));
                }
                break;
            case 27:
                this.ivDeviceIcon.setImageResource(R.drawable.dev_home_discover_t6);
                this.tvDeviceContent.setText(getString(R.string.Device_t6_name));
                EventBus.getDefault().post(new NotifyCopyWritingGifVideoEvent(27));
                break;
            case 28:
                this.ivDeviceIcon.setImageResource(R.drawable.dev_home_discover_t7);
                this.tvDeviceContent.setText(getString(R.string.Device_t7_name));
                EventBus.getDefault().post(new NotifyCopyWritingGifVideoEvent(28));
                break;
            case 29:
                this.ivDeviceIcon.setImageResource(R.drawable.dev_discover_w7h);
                this.tvDeviceContent.setText(getString(R.string.Discover_new_device_prompt, getString(R.string.W7h_name_default)));
                break;
        }
    }

    @Override // android.app.Activity
    public void onNewIntent(Intent intent) {
        setIntent(intent);
        this.deviceScanResult = (DeviceScanResult) intent.getParcelableExtra(Constants.EXTRA_DEVICE_RESULT);
        setFinishOnTouchOutside(false);
        if (this.deviceScanResult == null) {
            finish();
        } else {
            initView();
        }
    }

    public static Intent newIntent(Context context, DeviceScanResult deviceScanResult) {
        Intent intent = new Intent(context, (Class<?>) DiscoverDeviceActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.EXTRA_DEVICE_RESULT, deviceScanResult);
        intent.putExtras(bundle);
        return intent;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.iv_device_cancel) {
            finish();
            return;
        }
        if (id == R.id.tv_device_confirm) {
            EventBus.getDefault().post(new StopDeviceScanMsg());
            if (this.deviceScanResult != null) {
                if (Long.parseLong(UserInforUtils.getCurrentUserId(this)) != FamilyUtils.getInstance().getCurrentFamilyInfo(this).getOwner()) {
                    PetkitToast.showTopToast(this, getResources().getString(R.string.Bind_device_family_check_prompt), R.drawable.top_toast_warn_icon, 1);
                    return;
                }
                int deviceType = this.deviceScanResult.getDeviceType();
                if (deviceType == 10 || deviceType == 12 || deviceType == 14 || deviceType == 16 || deviceType == 17 || deviceType == 22 || deviceType == 18 || deviceType == 24) {
                    startActivity(BleDeviceScanAndConnectActivity.newIntent(this, this.deviceScanResult.getDeviceInfo().getDeviceId(), this.deviceScanResult.getDeviceType(), this.deviceScanResult));
                    finish();
                    return;
                }
                if (deviceType == 28 || deviceType == 27) {
                    startActivity(BleCameraBindActivity.newIntent(this, this.deviceScanResult.getDeviceInfo().getDeviceId(), this.deviceScanResult.getDeviceType(), "", this.deviceScanResult, true));
                    return;
                }
                if (deviceType == 21 || deviceType == 26 || deviceType == 25 || deviceType == 29 || deviceType == 19 || deviceType == 15 || deviceType == 11 || deviceType == 20 || deviceType == 9 || deviceType == 7 || deviceType == 8) {
                    startActivity(DeviceBindSetWifiActivity.newIntent((Context) this, this.deviceScanResult.getDeviceInfo().getDeviceId(), this.deviceScanResult.getDeviceType(), "", this.deviceScanResult, true));
                    finish();
                } else if (deviceType == 6) {
                    startActivity(WifiDeviceScanActivity.newIntent(this, 0L, 6, "", this.deviceScanResult, true));
                    finish();
                } else if (deviceType == 5) {
                    startActivity(WifiDeviceScanActivity.newIntent(this, 0L, 5, "", this.deviceScanResult, true));
                    finish();
                }
            }
        }
    }

    public void setupLayoutInflaterFactory() {
        try {
            LayoutInflater layoutInflater = getLayoutInflater();
            if (layoutInflater.getFactory2() == null) {
                PetKitTextFactory petKitTextFactory = new PetKitTextFactory();
                this.factoryWeakReference = new WeakReference<>(petKitTextFactory);
                LayoutInflaterCompat.setFactory2(layoutInflater, petKitTextFactory);
                Log.d("PetKitTextFactory", "LayoutInflater Factory2 set successfully");
            }
        } catch (Exception e) {
            Log.e("PetKitTextFactory", "Error setting LayoutInflater Factory2", e);
        }
    }

    @Override // android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        WeakReference<PetKitTextFactory> weakReference = this.factoryWeakReference;
        if (weakReference != null) {
            weakReference.clear();
        }
    }
}

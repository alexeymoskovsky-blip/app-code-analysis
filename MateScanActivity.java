package com.petkit.android.activities.mate.setting;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.jess.arms.utils.Consts;
import com.jess.arms.utils.DeviceUtils;
import com.jess.arms.widget.LoadDialog;
import com.petkit.android.activities.base.BaseActivity;
import com.petkit.android.activities.community.WebviewActivity;
import com.petkit.android.activities.fit.PetkitDetailActivity;
import com.petkit.android.activities.mate.utils.HsConsts;
import com.petkit.android.activities.petkitBleDevice.utils.BleDeviceBindUtils;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.apiResponse.DeviceBindStatusNewResult;
import com.petkit.android.api.http.apiResponse.DeviceBindStatusNewRsp;
import com.petkit.android.api.http.apiResponse.HsBindRsp;
import com.petkit.android.api.http.apiResponse.MateInfoRsp;
import com.petkit.android.api.http.apiResponse.SignupDeviceRsp;
import com.petkit.android.ble.BLEConsts;
import com.petkit.android.ble.DeviceInfo;
import com.petkit.android.ble.service.AndroidBLEActionService;
import com.petkit.android.model.MateDevice;
import com.petkit.android.utils.CommonUtil;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.PetkitLog;
import com.petkit.oversea.R;
import cz.msebera.android.httpclient.Header;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/* JADX INFO: loaded from: classes4.dex */
@TargetApi(18)
public class MateScanActivity extends BaseActivity {
    private static final int MATE_BIND_MODE = 1;
    private static final int MATE_DEFAULT_MODE = -1;
    private static final int MATE_ERROR_MODE = 2;
    private static final int MATE_GET_WIFI_MODE = 4;
    private static final int MATE_SCAN_MODE = 0;
    private static final int MATE_SUCCESS_MODE = 3;
    public static final String TAG = "HsScanActivity";
    private String curDeviceId;
    ImageView ledImageView;
    private DeviceListAdapter mAdapter;
    private DeviceInfo mBleDeviceInfo;
    private BroadcastReceiver mBroadcastReceiver;
    private MateDevice mExistDevice;
    Animation rotateAnimation;
    private FrameLayout scanFrameLayout;
    ImageView scanImageView;
    private TextView scanStateTextView;
    private String secret;
    private int mCurMode = -1;
    private List<DeviceInfo> mDeviceInfos = new ArrayList();
    private boolean scanState = false;
    private boolean mIsBindSuccess = false;
    private String deviceSN = null;
    private boolean isScanComplete = false;

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        if (bundle == null) {
            this.mExistDevice = (MateDevice) getIntent().getSerializableExtra(Constants.EXTRA_HS_DEVICE);
        } else {
            this.mExistDevice = (MateDevice) bundle.getSerializable(Constants.EXTRA_HS_DEVICE);
        }
        if (this.mExistDevice != null) {
            this.mIsBindSuccess = true;
            Intent intent = new Intent();
            intent.putExtra(Constants.EXTRA_HS_DEVICE_ID, this.mExistDevice.getId());
            intent.setAction(HsConsts.BROADCAST_FINISH_CALL);
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        }
        super.onCreate(bundle);
        if (PetkitDetailActivity.deviceConnectState != -1) {
            showLongToast(R.string.Error_ble_is_using);
            finish();
        } else {
            setContentView(R.layout.activity_hs_bt_connect);
            registerBoradcastReceiver();
        }
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        unregisterBroadcastReceiver();
        HsConsts.sendAbortActionBroadcast(this);
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        if (this.isScanComplete) {
            startWifiSelectActivity();
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.mate.setting.MateScanActivity$1 */
    public class AnonymousClass1 implements View.OnClickListener {
        public AnonymousClass1() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            MateScanActivity.this.onBackPressed();
        }
    }

    @Override // com.petkit.android.activities.base.BaseActivity
    public void setupViews() {
        setTitleLeftButton(R.drawable.btn_back_gray, new View.OnClickListener() { // from class: com.petkit.android.activities.mate.setting.MateScanActivity.1
            public AnonymousClass1() {
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MateScanActivity.this.onBackPressed();
            }
        });
        startBleProcess();
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        if (this.mExistDevice == null) {
            int i = this.mCurMode;
            if (i != 1 && i != 2) {
                if (i != 3) {
                    super.onBackPressed();
                    return;
                }
                return;
            } else {
                if (i == 1) {
                    HsConsts.sendAbortActionBroadcast(this);
                }
                updateMode(0, null);
                scanFinish();
                return;
            }
        }
        super.onBackPressed();
    }

    private void startBleProcess() {
        updateMode(0, null);
        MateDevice mateDevice = this.mExistDevice;
        if (mateDevice == null) {
            startScanDevice();
            return;
        }
        this.curDeviceId = mateDevice.getId();
        String secret = this.mExistDevice.getSecret();
        this.secret = secret;
        startSyncHSDevice(this.curDeviceId, secret, this.mExistDevice.getVersion());
        updateMode(4, null);
    }

    private void startScanDevice() {
        this.mDeviceInfos.clear();
        DeviceListAdapter deviceListAdapter = this.mAdapter;
        if (deviceListAdapter != null) {
            deviceListAdapter.notifyDataSetChanged();
        }
        if (this.scanFrameLayout.getChildCount() > 2) {
            FrameLayout frameLayout = this.scanFrameLayout;
            frameLayout.removeViewsInLayout(2, frameLayout.getChildCount() - 2);
        }
        this.scanState = true;
        Bundle bundle = new Bundle();
        bundle.putInt(BLEConsts.EXTRA_ACTION, 4);
        startBLEAction(bundle);
        this.scanStateTextView.setText(R.string.BLEUI_searching_PETKIT_nearby);
    }

    private void startBLEAction(Bundle bundle) {
        if (CommonUtils.getAndroidSDKVersion() >= 18) {
            if (!getPackageManager().hasSystemFeature("android.hardware.bluetooth_le")) {
                showShortToast(R.string.Mate_BLEUI_not_supported);
                finish();
                return;
            } else {
                Intent intent = new Intent(this, (Class<?>) AndroidBLEActionService.class);
                intent.putExtras(bundle);
                startService(intent);
                CommonUtils.addSysIntMap(this, Consts.SHARED_BLE_STATE, 1);
                return;
            }
        }
        showShortToast(R.string.Mate_BLEUI_not_supported);
        finish();
    }

    public void startInitDevice(DeviceInfo deviceInfo) {
        PetkitLog.d("startInitDevice");
        HsConsts.sendAbortActionBroadcast(this);
        String[] apiServers = BleDeviceBindUtils.getDeviceServerConfigsByType(2).getApiServers();
        Bundle bundle = new Bundle();
        bundle.putInt(BLEConsts.EXTRA_ACTION, deviceInfo.getDeviceId() == 0 ? 10 : 11);
        bundle.putSerializable(BLEConsts.EXTRA_DEVICE_INFO, deviceInfo);
        bundle.putString(BLEConsts.EXTRA_SECRET, this.secret);
        bundle.putString(BLEConsts.EXTRA_DATA, apiServers[0]);
        startBLEAction(bundle);
    }

    private void startSyncHSDevice(String str, String str2, String str3) {
        PetkitLog.d("startSyncHSDevice");
        HsConsts.sendAbortActionBroadcast(this);
        Bundle bundle = new Bundle();
        bundle.putInt(BLEConsts.EXTRA_ACTION, 9);
        bundle.putString(BLEConsts.EXTRA_DEVICE_ID, str);
        bundle.putString(BLEConsts.EXTRA_SECRET, str2);
        bundle.putString(BLEConsts.EXTRA_MATE_VERSION, str3);
        startBLEAction(bundle);
    }

    public void scanFinish() {
        this.scanState = false;
        this.scanImageView.clearAnimation();
        DeviceListAdapter deviceListAdapter = this.mAdapter;
        if (deviceListAdapter == null || deviceListAdapter.getCount() == 0) {
            this.scanStateTextView.setText(R.string.BLEUI_no_PETKIT_nearby);
        } else {
            this.scanStateTextView.setText(R.string.BLEUI_tap_radar_to_research);
        }
    }

    public void checkDeviceBindStatusNew(DeviceInfo deviceInfo) {
        if (deviceInfo == null) {
            return;
        }
        HashMap map = new HashMap();
        map.put("deviceIds", "" + deviceInfo.getDeviceId());
        post(ApiTools.SAMPLE_API_HS_BINDSTATUS, map, new AsyncHttpRespHandler(this) { // from class: com.petkit.android.activities.mate.setting.MateScanActivity.2
            public final /* synthetic */ DeviceInfo val$device;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass2(Activity this, DeviceInfo deviceInfo2) {
                super(this);
                deviceInfo = deviceInfo2;
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                DeviceBindStatusNewRsp deviceBindStatusNewRsp = (DeviceBindStatusNewRsp) this.gson.fromJson(this.responseResult, DeviceBindStatusNewRsp.class);
                if (deviceBindStatusNewRsp.getError() != null) {
                    if (deviceBindStatusNewRsp.getError().getCode() == 5 || deviceBindStatusNewRsp.getError().getCode() == 6) {
                        MateScanActivity.this.updateMode(2, deviceBindStatusNewRsp.getError().getMsg());
                        return;
                    }
                    return;
                }
                if (deviceBindStatusNewRsp.getResult() == null || deviceBindStatusNewRsp.getResult().size() <= 0) {
                    return;
                }
                DeviceBindStatusNewResult deviceBindStatusNewResult = deviceBindStatusNewRsp.getResult().get(0);
                if (deviceBindStatusNewResult.getId() == deviceInfo.getDeviceId()) {
                    deviceInfo.setOwner(deviceBindStatusNewResult.getOwner());
                    deviceInfo.setChecked(deviceBindStatusNewResult.getStatus() == 0);
                    MateScanActivity.this.addDevice(deviceInfo);
                }
            }
        }, false);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.mate.setting.MateScanActivity$2 */
    public class AnonymousClass2 extends AsyncHttpRespHandler {
        public final /* synthetic */ DeviceInfo val$device;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(Activity this, DeviceInfo deviceInfo2) {
            super(this);
            deviceInfo = deviceInfo2;
        }

        @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
        public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
            super.onSuccess(i, headerArr, bArr);
            DeviceBindStatusNewRsp deviceBindStatusNewRsp = (DeviceBindStatusNewRsp) this.gson.fromJson(this.responseResult, DeviceBindStatusNewRsp.class);
            if (deviceBindStatusNewRsp.getError() != null) {
                if (deviceBindStatusNewRsp.getError().getCode() == 5 || deviceBindStatusNewRsp.getError().getCode() == 6) {
                    MateScanActivity.this.updateMode(2, deviceBindStatusNewRsp.getError().getMsg());
                    return;
                }
                return;
            }
            if (deviceBindStatusNewRsp.getResult() == null || deviceBindStatusNewRsp.getResult().size() <= 0) {
                return;
            }
            DeviceBindStatusNewResult deviceBindStatusNewResult = deviceBindStatusNewRsp.getResult().get(0);
            if (deviceBindStatusNewResult.getId() == deviceInfo.getDeviceId()) {
                deviceInfo.setOwner(deviceBindStatusNewResult.getOwner());
                deviceInfo.setChecked(deviceBindStatusNewResult.getStatus() == 0);
                MateScanActivity.this.addDevice(deviceInfo);
            }
        }
    }

    public void addDevice(DeviceInfo deviceInfo) {
        float f;
        float fDpToPixel;
        Iterator<DeviceInfo> it = this.mDeviceInfos.iterator();
        while (it.hasNext()) {
            if (it.next().getAddress().equals(deviceInfo.getAddress())) {
                return;
            }
        }
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.mate_blue_point);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2);
        Random random = new Random();
        int iDpToPixel = (int) DeviceUtils.dpToPixel(this, 200.0f - (getResources().getDimension(R.dimen.author_avatar_width) / 2.0f));
        int iDpToPixel2 = (int) DeviceUtils.dpToPixel(this, getResources().getDimension(R.dimen.author_avatar_width) / 2.0f);
        int iNextInt = random.nextInt() % iDpToPixel;
        int iNextInt2 = random.nextInt() % iDpToPixel;
        if (iNextInt < 0) {
            iNextInt = -iNextInt;
        }
        if (iNextInt2 < 0) {
            iNextInt2 = -iNextInt2;
        }
        int iNextInt3 = random.nextInt() % 4;
        int i = iNextInt + iNextInt2;
        if (i < iDpToPixel2) {
            if (iNextInt < iNextInt2) {
                iNextInt += iDpToPixel2;
            } else {
                iNextInt2 += iDpToPixel2;
            }
        } else if (i > (iDpToPixel * 2) - iDpToPixel2) {
            if (iNextInt < iNextInt2) {
                iNextInt2 -= iDpToPixel2;
            } else {
                iNextInt -= iDpToPixel2;
            }
        }
        if (iNextInt3 == 2) {
            iNextInt = (int) (iNextInt + DeviceUtils.dpToPixel(this, 78.0f));
        } else {
            if (iNextInt3 == 3) {
                f = iNextInt2;
                fDpToPixel = DeviceUtils.dpToPixel(this, 78.0f);
            } else if (iNextInt3 != 1) {
                iNextInt = (int) (iNextInt + DeviceUtils.dpToPixel(this, 78.0f));
                f = iNextInt2;
                fDpToPixel = DeviceUtils.dpToPixel(this, 78.0f);
            }
            iNextInt2 = (int) (f + fDpToPixel);
        }
        layoutParams.leftMargin = iNextInt;
        layoutParams.topMargin = iNextInt2;
        PetkitLog.d("addDevice leftMargin: " + iNextInt + " topMargin: " + iNextInt2);
        this.scanFrameLayout.addView(imageView, layoutParams);
        this.mDeviceInfos.add(deviceInfo);
        DeviceListAdapter deviceListAdapter = this.mAdapter;
        if (deviceListAdapter != null && deviceListAdapter.getCount() > 0) {
            this.scanStateTextView.setText(R.string.BLEUI_found_PETKITs);
        }
        DeviceListAdapter deviceListAdapter2 = this.mAdapter;
        if (deviceListAdapter2 != null) {
            deviceListAdapter2.notifyDataSetChanged();
        }
    }

    private void signupDevice() {
        if (this.mBleDeviceInfo == null) {
            return;
        }
        if (isEmpty(this.deviceSN)) {
            updateMode(2, getString(R.string.Mate_bluetooth_sn_error));
            return;
        }
        HashMap map = new HashMap();
        map.put("mac", this.mBleDeviceInfo.getMac());
        if (this.mBleDeviceInfo.getDeviceId() != 0) {
            map.put("id", String.valueOf(this.mBleDeviceInfo.getDeviceId()));
        }
        map.put("sn", this.deviceSN);
        showLoadDialog();
        post(ApiTools.SAMPLE_API_HS_SIGNUP, map, new AsyncHttpRespHandler(this) { // from class: com.petkit.android.activities.mate.setting.MateScanActivity.3
            public AnonymousClass3(Activity this) {
                super(this);
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                SignupDeviceRsp signupDeviceRsp = (SignupDeviceRsp) this.gson.fromJson(this.responseResult, SignupDeviceRsp.class);
                if (signupDeviceRsp.getError() != null) {
                    MateScanActivity.this.updateMode(2, signupDeviceRsp.getError().getMsg());
                    return;
                }
                if (signupDeviceRsp.getResult() != null) {
                    MateScanActivity.this.mBleDeviceInfo.setDeviceId(signupDeviceRsp.getResult().getId());
                    MateScanActivity.this.mBleDeviceInfo.setMac(signupDeviceRsp.getResult().getMac());
                    MateScanActivity.this.secret = signupDeviceRsp.getResult().getSecret();
                    MateScanActivity.this.curDeviceId = "" + MateScanActivity.this.mBleDeviceInfo.getDeviceId();
                    String strValueOf = String.valueOf(signupDeviceRsp.getResult().getId());
                    int length = strValueOf.length();
                    if (length < 16) {
                        for (int i2 = 0; i2 < 16 - length; i2++) {
                            strValueOf = "0" + strValueOf;
                        }
                    }
                    Intent intent = new Intent(BLEConsts.BROADCAST_ACTION);
                    intent.putExtra(BLEConsts.EXTRA_ACTION, 1);
                    intent.putExtra(BLEConsts.EXTRA_SECRET_KEY, signupDeviceRsp.getResult().getSecretKey());
                    intent.putExtra(BLEConsts.EXTRA_SECRET, signupDeviceRsp.getResult().getSecret());
                    intent.putExtra(BLEConsts.EXTRA_DEVICE_ID, strValueOf);
                    LocalBroadcastManager.getInstance(MateScanActivity.this).sendBroadcast(intent);
                    return;
                }
                MateScanActivity.this.updateMode(2, null);
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFailure(int i, Header[] headerArr, byte[] bArr, Throwable th) {
                super.onFailure(i, headerArr, bArr, th);
                MateScanActivity.this.updateMode(2, null);
            }
        }, false);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.mate.setting.MateScanActivity$3 */
    public class AnonymousClass3 extends AsyncHttpRespHandler {
        public AnonymousClass3(Activity this) {
            super(this);
        }

        @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
        public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
            super.onSuccess(i, headerArr, bArr);
            SignupDeviceRsp signupDeviceRsp = (SignupDeviceRsp) this.gson.fromJson(this.responseResult, SignupDeviceRsp.class);
            if (signupDeviceRsp.getError() != null) {
                MateScanActivity.this.updateMode(2, signupDeviceRsp.getError().getMsg());
                return;
            }
            if (signupDeviceRsp.getResult() != null) {
                MateScanActivity.this.mBleDeviceInfo.setDeviceId(signupDeviceRsp.getResult().getId());
                MateScanActivity.this.mBleDeviceInfo.setMac(signupDeviceRsp.getResult().getMac());
                MateScanActivity.this.secret = signupDeviceRsp.getResult().getSecret();
                MateScanActivity.this.curDeviceId = "" + MateScanActivity.this.mBleDeviceInfo.getDeviceId();
                String strValueOf = String.valueOf(signupDeviceRsp.getResult().getId());
                int length = strValueOf.length();
                if (length < 16) {
                    for (int i2 = 0; i2 < 16 - length; i2++) {
                        strValueOf = "0" + strValueOf;
                    }
                }
                Intent intent = new Intent(BLEConsts.BROADCAST_ACTION);
                intent.putExtra(BLEConsts.EXTRA_ACTION, 1);
                intent.putExtra(BLEConsts.EXTRA_SECRET_KEY, signupDeviceRsp.getResult().getSecretKey());
                intent.putExtra(BLEConsts.EXTRA_SECRET, signupDeviceRsp.getResult().getSecret());
                intent.putExtra(BLEConsts.EXTRA_DEVICE_ID, strValueOf);
                LocalBroadcastManager.getInstance(MateScanActivity.this).sendBroadcast(intent);
                return;
            }
            MateScanActivity.this.updateMode(2, null);
        }

        @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
        public void onFailure(int i, Header[] headerArr, byte[] bArr, Throwable th) {
            super.onFailure(i, headerArr, bArr, th);
            MateScanActivity.this.updateMode(2, null);
        }
    }

    @Override // com.petkit.android.activities.base.BaseActivity, android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.mate_scan_img) {
            if (this.scanState) {
                return;
            }
            updateMode(0, null);
            startScanDevice();
            return;
        }
        if (id == R.id.device_connect) {
            DeviceInfo deviceInfo = (DeviceInfo) view.getTag();
            this.mBleDeviceInfo = deviceInfo;
            if (deviceInfo != null && deviceInfo.isChecked()) {
                showLoadDialog();
                if (this.mBleDeviceInfo.getDeviceId() == 0) {
                    startInitDevice(this.mBleDeviceInfo);
                    return;
                }
                trybind("" + this.mBleDeviceInfo.getDeviceId(), this.mBleDeviceInfo.getMac());
                return;
            }
            return;
        }
        if (id == R.id.mate_setting_retry_btn) {
            startBleProcess();
            return;
        }
        if (id == R.id.mate_setting_help_btn) {
            Bundle bundle = new Bundle();
            bundle.putString(Constants.EXTRA_LOAD_PATH, ApiTools.getWebUrlByKey("mate_usage"));
            bundle.putString(Constants.EXTRA_LOAD_TITLE, getString(R.string.Mate_usage));
            startActivityWithData(WebviewActivity.class, bundle, false);
            return;
        }
        if (id == R.id.mate_setting_bind_btn) {
            MateDevice mateDevice = this.mExistDevice;
            if (mateDevice != null) {
                startSyncHSDevice(mateDevice.getId(), this.mExistDevice.getSecret(), this.mExistDevice.getVersion());
                this.curDeviceId = this.mExistDevice.getId();
                this.secret = this.mExistDevice.getSecret();
                updateMode(4, null);
                return;
            }
            this.curDeviceId = "" + this.mBleDeviceInfo.getDeviceId();
            if (this.mBleDeviceInfo.getDeviceId() != 0) {
                bindDevice(Long.valueOf(this.mBleDeviceInfo.getDeviceId()), this.mBleDeviceInfo.getMac());
            } else {
                signupDevice();
            }
        }
    }

    public class DeviceListAdapter extends BaseAdapter {
        @Override // android.widget.Adapter
        public long getItemId(int i) {
            return 0L;
        }

        private DeviceListAdapter() {
        }

        public /* synthetic */ DeviceListAdapter(MateScanActivity mateScanActivity, AnonymousClass1 anonymousClass1) {
            this();
        }

        @Override // android.widget.Adapter
        public int getCount() {
            return MateScanActivity.this.mDeviceInfos.size();
        }

        @Override // android.widget.Adapter
        public DeviceInfo getItem(int i) {
            return (DeviceInfo) MateScanActivity.this.mDeviceInfos.get(i);
        }

        @Override // android.widget.Adapter
        @SuppressLint({"InflateParams"})
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            DeviceInfo item = getItem(i);
            if (view == null || !(view.getTag() instanceof ViewHolder)) {
                viewHolder = new ViewHolder();
                view = LayoutInflater.from(MateScanActivity.this).inflate(R.layout.adapter_scan_device_list, (ViewGroup) null);
                viewHolder.device_name = (TextView) view.findViewById(R.id.device_name);
                viewHolder.device_mac = (TextView) view.findViewById(R.id.device_mac);
                viewHolder.device_connect = (TextView) view.findViewById(R.id.device_connect);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            viewHolder.device_name.setText(item.getName());
            viewHolder.device_mac.setText(item.getMac());
            viewHolder.device_connect.setTag(item);
            viewHolder.device_connect.setOnClickListener(MateScanActivity.this);
            viewHolder.device_connect.setTextSize(14.0f);
            if (item.isChecked()) {
                viewHolder.device_connect.setTextColor(Color.parseColor("#ff1f9bdb"));
                viewHolder.device_connect.setText(R.string.Bind);
            } else {
                viewHolder.device_connect.setTextColor(CommonUtils.getColorById(R.color.gray));
                if (!MateScanActivity.this.isEmpty(item.getOwner())) {
                    viewHolder.device_connect.setText(MateScanActivity.this.getString(R.string.BLEUI_Bind_invalid_device_format, item.getOwner()));
                }
            }
            return view;
        }

        public class ViewHolder {
            public TextView device_connect;
            public TextView device_mac;
            public TextView device_name;

            public ViewHolder() {
            }

            public /* synthetic */ ViewHolder(DeviceListAdapter deviceListAdapter, AnonymousClass1 anonymousClass1) {
                this();
            }
        }
    }

    private void trybind(String str, String str2) {
        HashMap map = new HashMap();
        map.put("deviceId", str);
        map.put("mac", str2.toLowerCase());
        post(ApiTools.SAMPLE_API_HS_TRYBINDDEVICE, map, new AsyncHttpRespHandler() { // from class: com.petkit.android.activities.mate.setting.MateScanActivity.4
            public AnonymousClass4() {
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                MateInfoRsp mateInfoRsp = (MateInfoRsp) this.gson.fromJson(this.responseResult, MateInfoRsp.class);
                if (mateInfoRsp.getError() != null) {
                    LoadDialog.dismissDialog();
                    MateScanActivity.this.updateMode(2, mateInfoRsp.getError().getMsg());
                    return;
                }
                if (mateInfoRsp.getResult() != null) {
                    MateScanActivity.this.secret = mateInfoRsp.getResult().getSecret();
                    MateScanActivity mateScanActivity = MateScanActivity.this;
                    if (!mateScanActivity.isEmpty(mateScanActivity.secret)) {
                        MateScanActivity mateScanActivity2 = MateScanActivity.this;
                        mateScanActivity2.startInitDevice(mateScanActivity2.mBleDeviceInfo);
                    } else {
                        LoadDialog.dismissDialog();
                        MateScanActivity.this.updateMode(2, null);
                    }
                }
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFailure(int i, Header[] headerArr, byte[] bArr, Throwable th) {
                super.onFailure(i, headerArr, bArr, th);
                LoadDialog.dismissDialog();
            }
        }, false);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.mate.setting.MateScanActivity$4 */
    public class AnonymousClass4 extends AsyncHttpRespHandler {
        public AnonymousClass4() {
        }

        @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
        public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
            super.onSuccess(i, headerArr, bArr);
            MateInfoRsp mateInfoRsp = (MateInfoRsp) this.gson.fromJson(this.responseResult, MateInfoRsp.class);
            if (mateInfoRsp.getError() != null) {
                LoadDialog.dismissDialog();
                MateScanActivity.this.updateMode(2, mateInfoRsp.getError().getMsg());
                return;
            }
            if (mateInfoRsp.getResult() != null) {
                MateScanActivity.this.secret = mateInfoRsp.getResult().getSecret();
                MateScanActivity mateScanActivity = MateScanActivity.this;
                if (!mateScanActivity.isEmpty(mateScanActivity.secret)) {
                    MateScanActivity mateScanActivity2 = MateScanActivity.this;
                    mateScanActivity2.startInitDevice(mateScanActivity2.mBleDeviceInfo);
                } else {
                    LoadDialog.dismissDialog();
                    MateScanActivity.this.updateMode(2, null);
                }
            }
        }

        @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
        public void onFailure(int i, Header[] headerArr, byte[] bArr, Throwable th) {
            super.onFailure(i, headerArr, bArr, th);
            LoadDialog.dismissDialog();
        }
    }

    @SuppressLint({"DefaultLocale"})
    public void bindDevice(Long l, String str) {
        if (isEmpty(this.deviceSN)) {
            updateMode(2, getString(R.string.Mate_bluetooth_sn_error));
            return;
        }
        HashMap map = new HashMap();
        map.put("deviceId", "" + l);
        map.put("mac", str.toLowerCase());
        showLoadDialog();
        post(ApiTools.SAMPLE_API_HS_BINDDEVICE, map, new AsyncHttpRespHandler() { // from class: com.petkit.android.activities.mate.setting.MateScanActivity.5
            public AnonymousClass5() {
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFinish() {
                super.onFinish();
                LoadDialog.dismissDialog();
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                HsBindRsp hsBindRsp = (HsBindRsp) this.gson.fromJson(this.responseResult, HsBindRsp.class);
                if (hsBindRsp.getError() != null) {
                    MateScanActivity.this.showShortToast(hsBindRsp.getError().getMsg(), R.drawable.toast_failed);
                    MateScanActivity.this.updateMode(2, null);
                    return;
                }
                if (hsBindRsp.getResult() == null) {
                    MateScanActivity.this.updateMode(2, null);
                    return;
                }
                MateDevice mateDevice = new MateDevice();
                mateDevice.setId(hsBindRsp.getResult().getId());
                mateDevice.setMac(hsBindRsp.getResult().getMac());
                mateDevice.setSecret(hsBindRsp.getResult().getSecret());
                mateDevice.setWifiMac(hsBindRsp.getResult().getWifiMac());
                HsConsts.addDeviceList(MateScanActivity.this, mateDevice);
                MateScanActivity.this.curDeviceId = "" + hsBindRsp.getResult().getId();
                MateScanActivity.this.mIsBindSuccess = true;
                MateScanActivity.this.secret = hsBindRsp.getResult().getSecret();
                Intent intent = new Intent(BLEConsts.BROADCAST_ACTION);
                intent.putExtra(BLEConsts.EXTRA_ACTION, 1);
                intent.putExtra(BLEConsts.EXTRA_SECRET, hsBindRsp.getResult().getSecret());
                intent.putExtra(BLEConsts.EXTRA_DEVICE_ID, String.valueOf(hsBindRsp.getResult().getId()));
                intent.putExtra(BLEConsts.EXTRA_MATE_VERSION, hsBindRsp.getResult().getVersion());
                LocalBroadcastManager.getInstance(MateScanActivity.this).sendBroadcast(intent);
            }
        }, false);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.mate.setting.MateScanActivity$5 */
    public class AnonymousClass5 extends AsyncHttpRespHandler {
        public AnonymousClass5() {
        }

        @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
        public void onFinish() {
            super.onFinish();
            LoadDialog.dismissDialog();
        }

        @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
        public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
            super.onSuccess(i, headerArr, bArr);
            HsBindRsp hsBindRsp = (HsBindRsp) this.gson.fromJson(this.responseResult, HsBindRsp.class);
            if (hsBindRsp.getError() != null) {
                MateScanActivity.this.showShortToast(hsBindRsp.getError().getMsg(), R.drawable.toast_failed);
                MateScanActivity.this.updateMode(2, null);
                return;
            }
            if (hsBindRsp.getResult() == null) {
                MateScanActivity.this.updateMode(2, null);
                return;
            }
            MateDevice mateDevice = new MateDevice();
            mateDevice.setId(hsBindRsp.getResult().getId());
            mateDevice.setMac(hsBindRsp.getResult().getMac());
            mateDevice.setSecret(hsBindRsp.getResult().getSecret());
            mateDevice.setWifiMac(hsBindRsp.getResult().getWifiMac());
            HsConsts.addDeviceList(MateScanActivity.this, mateDevice);
            MateScanActivity.this.curDeviceId = "" + hsBindRsp.getResult().getId();
            MateScanActivity.this.mIsBindSuccess = true;
            MateScanActivity.this.secret = hsBindRsp.getResult().getSecret();
            Intent intent = new Intent(BLEConsts.BROADCAST_ACTION);
            intent.putExtra(BLEConsts.EXTRA_ACTION, 1);
            intent.putExtra(BLEConsts.EXTRA_SECRET, hsBindRsp.getResult().getSecret());
            intent.putExtra(BLEConsts.EXTRA_DEVICE_ID, String.valueOf(hsBindRsp.getResult().getId()));
            intent.putExtra(BLEConsts.EXTRA_MATE_VERSION, hsBindRsp.getResult().getVersion());
            LocalBroadcastManager.getInstance(MateScanActivity.this).sendBroadcast(intent);
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.mate.setting.MateScanActivity$6 */
    public class AnonymousClass6 extends BroadcastReceiver {
        public AnonymousClass6() {
        }

        @Override // android.content.BroadcastReceiver
        @SuppressLint({"DefaultLocale"})
        public void onReceive(Context context, Intent intent) {
            DeviceInfo deviceInfo;
            if (intent.getAction().equals(Constants.EXTRA_HS_WIFI_CONFIG_COMPLETE)) {
                MateScanActivity.this.finish();
                return;
            }
            if (intent.getAction().equals(BLEConsts.BROADCAST_PROGRESS)) {
                int intExtra = intent.getIntExtra(BLEConsts.EXTRA_PROGRESS, 0);
                PetkitLog.d("HsScanActivity progress: " + intExtra);
                if (MateScanActivity.this.isFinishing()) {
                    return;
                }
                if (intExtra == -28) {
                    MateScanActivity mateScanActivity = MateScanActivity.this;
                    mateScanActivity.bindDevice(Long.valueOf(mateScanActivity.mBleDeviceInfo.getDeviceId()), MateScanActivity.this.mBleDeviceInfo.getMac());
                    return;
                }
                if (intExtra != -27) {
                    if (intExtra != -5 && intExtra != 4096 && intExtra != 32769 && intExtra != 32937) {
                        switch (intExtra) {
                            case -16:
                                if (MateScanActivity.this.mExistDevice == null) {
                                    if (MateScanActivity.this.mIsBindSuccess) {
                                        MateScanActivity.this.updateMode(3, null);
                                    } else {
                                        MateScanActivity.this.updateMode(1, null);
                                    }
                                    break;
                                }
                                break;
                            case -15:
                                MateScanActivity.this.scanFinish();
                                break;
                            case -13:
                                MateScanActivity.this.deviceSN = intent.getStringExtra(BLEConsts.EXTRA_DATA);
                                break;
                        }
                        return;
                    }
                    MateScanActivity.this.updateMode(2, null);
                    return;
                }
                MateScanActivity.this.updateMode(3, null);
                return;
            }
            if (intent.getAction().equals(BLEConsts.BROADCAST_ERROR)) {
                int intExtra2 = intent.getIntExtra(BLEConsts.EXTRA_DATA, 0);
                PetkitLog.d("HsScanActivity error: " + intExtra2);
                if (MateScanActivity.this.mCurMode == -1 || intExtra2 == 4097) {
                    return;
                }
                MateScanActivity.this.updateMode(2, null);
                return;
            }
            if (!intent.getAction().equals(BLEConsts.BROADCAST_SCANED_DEVICE) || (deviceInfo = (DeviceInfo) intent.getSerializableExtra(BLEConsts.EXTRA_DEVICE_INFO)) == null) {
                return;
            }
            if (BLEConsts.PET_HOME.equals(deviceInfo.getName()) || BLEConsts.PET_MATE.equals(deviceInfo.getName())) {
                for (DeviceInfo deviceInfo2 : MateScanActivity.this.mDeviceInfos) {
                    if (deviceInfo.getMac() != null && deviceInfo2.getMac().equals(deviceInfo.getMac())) {
                        return;
                    }
                }
                if (MateScanActivity.this.mExistDevice == null) {
                    if (deviceInfo.isChecked()) {
                        MateScanActivity.this.addDevice(deviceInfo);
                    } else {
                        MateScanActivity.this.checkDeviceBindStatusNew(deviceInfo);
                    }
                }
            }
        }
    }

    private void registerBoradcastReceiver() {
        this.mBroadcastReceiver = new BroadcastReceiver() { // from class: com.petkit.android.activities.mate.setting.MateScanActivity.6
            public AnonymousClass6() {
            }

            @Override // android.content.BroadcastReceiver
            @SuppressLint({"DefaultLocale"})
            public void onReceive(Context context, Intent intent) {
                DeviceInfo deviceInfo;
                if (intent.getAction().equals(Constants.EXTRA_HS_WIFI_CONFIG_COMPLETE)) {
                    MateScanActivity.this.finish();
                    return;
                }
                if (intent.getAction().equals(BLEConsts.BROADCAST_PROGRESS)) {
                    int intExtra = intent.getIntExtra(BLEConsts.EXTRA_PROGRESS, 0);
                    PetkitLog.d("HsScanActivity progress: " + intExtra);
                    if (MateScanActivity.this.isFinishing()) {
                        return;
                    }
                    if (intExtra == -28) {
                        MateScanActivity mateScanActivity = MateScanActivity.this;
                        mateScanActivity.bindDevice(Long.valueOf(mateScanActivity.mBleDeviceInfo.getDeviceId()), MateScanActivity.this.mBleDeviceInfo.getMac());
                        return;
                    }
                    if (intExtra != -27) {
                        if (intExtra != -5 && intExtra != 4096 && intExtra != 32769 && intExtra != 32937) {
                            switch (intExtra) {
                                case -16:
                                    if (MateScanActivity.this.mExistDevice == null) {
                                        if (MateScanActivity.this.mIsBindSuccess) {
                                            MateScanActivity.this.updateMode(3, null);
                                        } else {
                                            MateScanActivity.this.updateMode(1, null);
                                        }
                                        break;
                                    }
                                    break;
                                case -15:
                                    MateScanActivity.this.scanFinish();
                                    break;
                                case -13:
                                    MateScanActivity.this.deviceSN = intent.getStringExtra(BLEConsts.EXTRA_DATA);
                                    break;
                            }
                            return;
                        }
                        MateScanActivity.this.updateMode(2, null);
                        return;
                    }
                    MateScanActivity.this.updateMode(3, null);
                    return;
                }
                if (intent.getAction().equals(BLEConsts.BROADCAST_ERROR)) {
                    int intExtra2 = intent.getIntExtra(BLEConsts.EXTRA_DATA, 0);
                    PetkitLog.d("HsScanActivity error: " + intExtra2);
                    if (MateScanActivity.this.mCurMode == -1 || intExtra2 == 4097) {
                        return;
                    }
                    MateScanActivity.this.updateMode(2, null);
                    return;
                }
                if (!intent.getAction().equals(BLEConsts.BROADCAST_SCANED_DEVICE) || (deviceInfo = (DeviceInfo) intent.getSerializableExtra(BLEConsts.EXTRA_DEVICE_INFO)) == null) {
                    return;
                }
                if (BLEConsts.PET_HOME.equals(deviceInfo.getName()) || BLEConsts.PET_MATE.equals(deviceInfo.getName())) {
                    for (DeviceInfo deviceInfo2 : MateScanActivity.this.mDeviceInfos) {
                        if (deviceInfo.getMac() != null && deviceInfo2.getMac().equals(deviceInfo.getMac())) {
                            return;
                        }
                    }
                    if (MateScanActivity.this.mExistDevice == null) {
                        if (deviceInfo.isChecked()) {
                            MateScanActivity.this.addDevice(deviceInfo);
                        } else {
                            MateScanActivity.this.checkDeviceBindStatusNew(deviceInfo);
                        }
                    }
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BLEConsts.BROADCAST_PROGRESS);
        intentFilter.addAction(BLEConsts.BROADCAST_ERROR);
        intentFilter.addAction(BLEConsts.BROADCAST_LOG);
        intentFilter.addAction(BLEConsts.BROADCAST_SCANED_DEVICE);
        intentFilter.addAction(Constants.EXTRA_HS_WIFI_CONFIG_COMPLETE);
        LocalBroadcastManager.getInstance(this).registerReceiver(this.mBroadcastReceiver, intentFilter);
    }

    private void unregisterBroadcastReceiver() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(this.mBroadcastReceiver);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.mate.setting.MateScanActivity$7 */
    public class AnonymousClass7 implements DialogInterface.OnCancelListener {
        public AnonymousClass7() {
        }

        @Override // android.content.DialogInterface.OnCancelListener
        public void onCancel(DialogInterface dialogInterface) {
            HsConsts.sendAbortActionBroadcast(MateScanActivity.this);
        }
    }

    @Override // com.petkit.android.activities.base.BaseActivity
    public void showLoadDialog() {
        this.mIsBindSuccess = false;
        LoadDialog.show(this, getString(R.string.Binding), false, new DialogInterface.OnCancelListener() { // from class: com.petkit.android.activities.mate.setting.MateScanActivity.7
            public AnonymousClass7() {
            }

            @Override // android.content.DialogInterface.OnCancelListener
            public void onCancel(DialogInterface dialogInterface) {
                HsConsts.sendAbortActionBroadcast(MateScanActivity.this);
            }
        });
    }

    @SuppressLint({"CutPasteId"})
    public void updateMode(int i, String str) {
        if (this.mCurMode != i || i == 0) {
            this.mCurMode = i;
            if (i == 0) {
                setTitle(R.string.Device_exam_scan);
                findViewById(R.id.mate_bt_connect_normal).setVisibility(0);
                findViewById(R.id.mate_bt_bind).setVisibility(8);
                findViewById(R.id.mate_bt_connect_failed).setVisibility(8);
                findViewById(R.id.mate_bt_bind_succeed).setVisibility(8);
                this.mAdapter = new DeviceListAdapter();
                ((ListView) findViewById(R.id.mate_bt_scan_list)).setAdapter((ListAdapter) this.mAdapter);
                ImageView imageView = (ImageView) findViewById(R.id.mate_scan_img);
                this.scanImageView = imageView;
                imageView.setOnClickListener(this);
                this.scanFrameLayout = (FrameLayout) findViewById(R.id.mate_scan_view);
                this.scanStateTextView = (TextView) findViewById(R.id.mate_bt_connect_txt);
                this.rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.scan_rotate);
                this.rotateAnimation.setInterpolator(new LinearInterpolator());
                this.scanImageView.startAnimation(this.rotateAnimation);
                return;
            }
            if (i == 1) {
                LoadDialog.dismissDialog();
                setTitle(R.string.Mate_bind_confirm);
                this.scanImageView.clearAnimation();
                findViewById(R.id.mate_bt_connect_normal).setVisibility(8);
                findViewById(R.id.mate_bt_bind).setVisibility(0);
                findViewById(R.id.mate_bt_connect_failed).setVisibility(8);
                findViewById(R.id.mate_bt_bind_succeed).setVisibility(8);
                this.ledImageView = (ImageView) findViewById(R.id.mate_bt_bind_led);
                AlphaAnimation alphaAnimation = new AlphaAnimation(0.1f, 1.0f);
                alphaAnimation.setDuration(1000L);
                alphaAnimation.setRepeatCount(-1);
                alphaAnimation.setRepeatMode(2);
                this.ledImageView.setAnimation(alphaAnimation);
                alphaAnimation.start();
                findViewById(R.id.mate_setting_bind_btn).setOnClickListener(this);
                return;
            }
            if (i == 2) {
                HsConsts.sendAbortActionBroadcast(this);
                LoadDialog.dismissDialog();
                setTitle(R.string.Mate_bind_error_title);
                this.scanImageView.clearAnimation();
                findViewById(R.id.mate_bt_connect_normal).setVisibility(8);
                findViewById(R.id.mate_bt_bind).setVisibility(8);
                findViewById(R.id.mate_bt_connect_failed).setVisibility(0);
                TextView textView = (TextView) findViewById(R.id.mate_setting_error);
                if (!isEmpty(str)) {
                    textView.setText(str);
                } else {
                    textView.setText(R.string.Mate_search_connect_device_error);
                }
                findViewById(R.id.mate_bt_bind_succeed).setVisibility(8);
                findViewById(R.id.mate_setting_retry_btn).setOnClickListener(this);
                findViewById(R.id.mate_setting_help_btn).setOnClickListener(this);
                return;
            }
            if (i != 3) {
                if (i != 4) {
                    return;
                }
                setTitle(R.string.Mate_bind_wait);
                this.scanImageView.clearAnimation();
                findViewById(R.id.mate_bt_connect_normal).setVisibility(8);
                findViewById(R.id.mate_bt_bind).setVisibility(8);
                findViewById(R.id.mate_bt_connect_failed).setVisibility(8);
                findViewById(R.id.mate_bt_bind_succeed).setVisibility(0);
                ((TextView) findViewById(R.id.mate_bt_bind_txt)).setText(R.string.Mate_bt_searching);
                return;
            }
            LoadDialog.dismissDialog();
            setTitle(R.string.Mate_bind_wait);
            this.scanImageView.clearAnimation();
            if (this.mExistDevice == null) {
                findViewById(R.id.mate_bt_connect_normal).setVisibility(8);
                findViewById(R.id.mate_bt_bind).setVisibility(8);
                findViewById(R.id.mate_bt_connect_failed).setVisibility(8);
                findViewById(R.id.mate_bt_bind_succeed).setVisibility(0);
                ((TextView) findViewById(R.id.mate_bt_bind_txt)).setText(R.string.Mate_bt_connecting_ready);
                LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(Constants.BROADCAST_MSG_DEVICE_UPDATE));
            }
            this.isScanComplete = true;
            new Handler().postDelayed(new Runnable() { // from class: com.petkit.android.activities.mate.setting.MateScanActivity.8
                public AnonymousClass8() {
                }

                @Override // java.lang.Runnable
                public void run() {
                    MateScanActivity mateScanActivity = MateScanActivity.this;
                    if (CommonUtil.isActivityForeground(mateScanActivity, mateScanActivity.getClass().getName())) {
                        MateScanActivity.this.startWifiSelectActivity();
                        MateScanActivity.this.isScanComplete = false;
                    }
                }
            }, 1000L);
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.mate.setting.MateScanActivity$8 */
    public class AnonymousClass8 implements Runnable {
        public AnonymousClass8() {
        }

        @Override // java.lang.Runnable
        public void run() {
            MateScanActivity mateScanActivity = MateScanActivity.this;
            if (CommonUtil.isActivityForeground(mateScanActivity, mateScanActivity.getClass().getName())) {
                MateScanActivity.this.startWifiSelectActivity();
                MateScanActivity.this.isScanComplete = false;
            }
        }
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 289) {
            if (i2 == -1) {
                setResult(-1);
            } else {
                setResult(0);
            }
            finish();
        }
    }

    public void startWifiSelectActivity() {
        if (isFinishing()) {
            return;
        }
        setResult(-1);
        Intent intent = new Intent(this, (Class<?>) MateWifiSelectActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(MateWifiSelectActivity.HS_DEVICE_ID, this.curDeviceId);
        bundle.putString(MateWifiSelectActivity.HS_DEVICE_SECRET, this.secret);
        intent.putExtras(bundle);
        startActivityForResult(intent, 289);
    }
}

package com.petkit.android.activities.fit;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.facebook.devicerequests.internal.DeviceRequestsHelper;
import com.google.gson.Gson;
import com.jess.arms.utils.Consts;
import com.jess.arms.utils.DeviceUtils;
import com.jess.arms.widget.LoadDialog;
import com.jess.arms.widget.imageloader.glide.GlideCircleTransform;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.base.BaseListActivity;
import com.petkit.android.activities.fit.adapter.FitScanListAdapter;
import com.petkit.android.activities.home.HomeActivity;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.apiResponse.BindDeviceRsp;
import com.petkit.android.api.http.apiResponse.DeviceBindStatusNewResult;
import com.petkit.android.api.http.apiResponse.DeviceBindStatusNewRsp;
import com.petkit.android.api.http.apiResponse.LoginRsp;
import com.petkit.android.api.http.apiResponse.SignupDeviceRsp;
import com.petkit.android.ble.BLEConsts;
import com.petkit.android.ble.DeviceInfo;
import com.petkit.android.ble.samsung.BaseBluetoothLeUtils;
import com.petkit.android.ble.samsung.ISamsungBLEListener;
import com.petkit.android.ble.samsung.SSBluetoothLeUtils;
import com.petkit.android.ble.service.AndroidBLEActionService;
import com.petkit.android.model.Device;
import com.petkit.android.model.Pet;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.PetkitLog;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.oversea.R;
import cz.msebera.android.httpclient.Header;
import in.srain.cube.views.ptr.PtrFrameLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

/* JADX INFO: loaded from: classes3.dex */
@TargetApi(18)
public class PetkitScanActivity extends BaseListActivity implements ISamsungBLEListener {
    public static final int PETKIT_CHANGE_DEVICE = 1;
    public static final int PETKIT_INIT_DEVICE = 2;
    private static final int SCAN_STATE_COMPLETE = 2;
    private static final int SCAN_STATE_FAIL = 3;
    private static final int SCAN_STATE_NONE = 0;
    private static final int SCAN_STATE_START = 1;
    private int actionType;
    private Pet curPet;
    private AlertDialog mAlertDialog;
    private DeviceInfo mBleDeviceInfo;
    private BaseBluetoothLeUtils mBluetoothLeUtils;
    private BroadcastReceiver mBroadcastReceiver;
    private Device mDevice;
    private Animation rotateAnimation;
    private FrameLayout scanFrameLayout;
    private ImageView scanImageView;
    private TextView scanStateTextView;
    private int scanState = 0;
    private boolean isBindSuccess = false;

    @Override // com.petkit.android.activities.base.BaseListActivity
    public void onLoadMoreBegin() {
    }

    @Override // com.petkit.android.activities.base.BaseActivity, com.petkit.android.activities.common.fragment.FailureFragment.FailureOnClickListener
    public void onRefresh(boolean z) {
    }

    @Override // in.srain.cube.views.ptr.PtrHandler
    public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
    }

    @Override // com.petkit.android.activities.base.BaseListActivity, com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        if (bundle != null) {
            this.actionType = bundle.getInt(Constants.EXTRA_DEVICE_ACTION_TYPE, 2);
            this.curPet = (Pet) bundle.getSerializable(Constants.EXTRA_DOG);
        } else {
            this.actionType = getIntent().getIntExtra(Constants.EXTRA_DEVICE_ACTION_TYPE, 2);
            this.curPet = (Pet) getIntent().getSerializableExtra(Constants.EXTRA_DOG);
        }
        super.onCreate(bundle);
        CommonUtils.addSysIntMap(this, Consts.SHARED_BLE_STATE, 1);
        registerBoradcastReceiver();
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt(Constants.EXTRA_DEVICE_ACTION_TYPE, this.actionType);
        bundle.putSerializable(Constants.EXTRA_DOG, this.curPet);
    }

    @Override // android.app.Activity
    public void finish() {
        setResult(-1);
        super.finish();
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        CommonUtils.addSysIntMap(this, Consts.SHARED_BLE_STATE, 0);
        unregisterBroadcastReceiver();
        Intent intent = new Intent(BLEConsts.BROADCAST_ACTION);
        intent.putExtra(BLEConsts.EXTRA_ACTION, 2);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        BaseBluetoothLeUtils baseBluetoothLeUtils = this.mBluetoothLeUtils;
        if (baseBluetoothLeUtils != null) {
            baseBluetoothLeUtils.stop();
        }
    }

    @Override // com.petkit.android.activities.base.BaseListActivity, com.petkit.android.activities.base.BaseActivity
    public void setupViews() {
        super.setupViews();
        if (PetkitDetailActivity.deviceConnectState != -1) {
            showLongToast(R.string.Error_ble_is_using);
            finish();
            return;
        }
        setTitle(R.string.Device_add, R.color.white);
        setTitleBackgroundColor(CommonUtils.getColorById(R.color.petkit_scan_bg));
        setTitleLeftButton(R.drawable.btn_back_white);
        setDividerLineVisibility(8);
        this.mPtrFrame.setMode(PtrFrameLayout.Mode.NONE);
        setTopView(R.layout.layout_init_petkit);
        ImageView imageView = (ImageView) findViewById(R.id.scan_img);
        this.scanImageView = imageView;
        imageView.setOnClickListener(this);
        this.scanFrameLayout = (FrameLayout) findViewById(R.id.scan_view);
        this.scanStateTextView = (TextView) findViewById(R.id.scan_state);
        ((BaseApplication) getApplication()).getAppComponent().imageLoader().loadImage(this, GlideImageConfig.builder().url(this.curPet.getAvatar()).imageView((ImageView) findViewById(R.id.dog_avatar)).errorPic(this.curPet.getType().getId() == 1 ? R.drawable.default_header_dog : R.drawable.default_header_cat).transformation(new GlideCircleTransform(this)).build());
        this.rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.scan_rotate);
        this.rotateAnimation.setInterpolator(new LinearInterpolator());
        setViewState(4);
        Bundle bundle = new Bundle();
        bundle.putInt(BLEConsts.EXTRA_ACTION, 4);
        bundle.putSerializable(BLEConsts.EXTRA_DOG, this.curPet);
        startBLEAction(bundle);
    }

    @Override // com.petkit.android.activities.base.BaseListActivity
    public void initAdapter() {
        FitScanListAdapter fitScanListAdapter = new FitScanListAdapter(this, null);
        this.mListAdapter = fitScanListAdapter;
        this.mListView.setAdapter((ListAdapter) fitScanListAdapter);
    }

    private void startBLEAction(Bundle bundle) {
        int androidSDKVersion = CommonUtils.getAndroidSDKVersion();
        if (CommonUtils.getAndroidSDKVersion() >= 18) {
            if (getPackageManager().hasSystemFeature("android.hardware.bluetooth_le")) {
                Intent intent = new Intent(this, (Class<?>) AndroidBLEActionService.class);
                intent.putExtras(bundle);
                startService(intent);
                return;
            }
            return;
        }
        if (androidSDKVersion == 17 && CommonUtils.isSamsungDevice(Build.MODEL)) {
            BaseBluetoothLeUtils baseBluetoothLeUtils = this.mBluetoothLeUtils;
            if (baseBluetoothLeUtils == null) {
                SSBluetoothLeUtils sSBluetoothLeUtils = new SSBluetoothLeUtils((Activity) this, (ISamsungBLEListener) this);
                this.mBluetoothLeUtils = sSBluetoothLeUtils;
                sSBluetoothLeUtils.start();
                return;
            }
            baseBluetoothLeUtils.startScan();
            return;
        }
        showShortToast(R.string.BLEUI_not_supported);
    }

    private void scanFinish() {
        this.scanImageView.clearAnimation();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeScanState(int i) {
        this.scanState = i;
        if (i == 1) {
            this.mListAdapter.clearList();
            setViewState(4);
            if (this.scanFrameLayout.getChildCount() > 2) {
                FrameLayout frameLayout = this.scanFrameLayout;
                frameLayout.removeViewsInLayout(2, frameLayout.getChildCount() - 2);
            }
            this.scanStateTextView.setText(R.string.BLEUI_searching_PETKIT_nearby);
            this.scanImageView.startAnimation(this.rotateAnimation);
            return;
        }
        if (i == 2) {
            scanFinish();
            if (this.mListAdapter.getCount() == 0) {
                this.scanStateTextView.setText(R.string.BLEUI_no_PETKIT_nearby);
                return;
            } else {
                this.scanStateTextView.setText(R.string.BLEUI_tap_radar_to_research);
                return;
            }
        }
        if (i != 3) {
            return;
        }
        scanFinish();
        if (this.mListAdapter.getCount() == 0) {
            this.scanStateTextView.setText(R.string.BLEUI_no_PETKIT_nearby);
            setViewState(2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showBindDialog() {
        AlertDialog alertDialog = this.mAlertDialog;
        if (alertDialog == null || !alertDialog.isShowing()) {
            AlertDialog alertDialog2 = this.mAlertDialog;
            if (alertDialog2 == null) {
                this.mAlertDialog = new AlertDialog.Builder(this).setCancelable(false).setTitle(R.string.BLEUI_connected_to_PETKIT).setMessage(R.string.BLEUI_connected_to_PETKIT_notice).setPositiveButton(R.string.Bind, new DialogInterface.OnClickListener() { // from class: com.petkit.android.activities.fit.PetkitScanActivity.2
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                        PetkitScanActivity.this.signupDevice();
                    }
                }).setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() { // from class: com.petkit.android.activities.fit.PetkitScanActivity.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (PetkitScanActivity.this.mBluetoothLeUtils != null) {
                            PetkitScanActivity.this.mBluetoothLeUtils.stopGatt();
                            LoadDialog.dismissDialog();
                        } else {
                            Intent intent = new Intent(BLEConsts.BROADCAST_ACTION);
                            intent.putExtra(BLEConsts.EXTRA_ACTION, 2);
                            LocalBroadcastManager.getInstance(PetkitScanActivity.this).sendBroadcast(intent);
                        }
                    }
                }).show();
            } else {
                alertDialog2.show();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkDeviceBindStatusNew(final DeviceInfo deviceInfo) {
        if (deviceInfo == null) {
            return;
        }
        HashMap map = new HashMap();
        map.put("deviceIds", String.valueOf(deviceInfo.getDeviceId()));
        post(ApiTools.SAMPLE_API_DEVICE_BINDSTATUS, map, new AsyncHttpRespHandler(this) { // from class: com.petkit.android.activities.fit.PetkitScanActivity.3
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                DeviceBindStatusNewRsp deviceBindStatusNewRsp = (DeviceBindStatusNewRsp) this.gson.fromJson(this.responseResult, DeviceBindStatusNewRsp.class);
                if (deviceBindStatusNewRsp.getError() != null) {
                    if (deviceBindStatusNewRsp.getError().getCode() == 5 || deviceBindStatusNewRsp.getError().getCode() == 6) {
                        Intent intent = new Intent(BLEConsts.BROADCAST_ACTION);
                        intent.putExtra(BLEConsts.EXTRA_ACTION, 2);
                        LocalBroadcastManager.getInstance(PetkitScanActivity.this).sendBroadcast(intent);
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
                    PetkitScanActivity.this.addDevice(deviceInfo);
                }
            }
        }, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addDevice(DeviceInfo deviceInfo) {
        float f;
        float fDpToPixel;
        setViewState(1);
        this.scanStateTextView.setText(R.string.BLEUI_found_PETKITs);
        if (this.mListAdapter.getList() != null) {
            Iterator it = this.mListAdapter.getList().iterator();
            while (it.hasNext()) {
                if (((DeviceInfo) it.next()).getAddress().equals(deviceInfo.getAddress())) {
                    return;
                }
            }
        }
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.icon_device_point);
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
        if (iNextInt3 != 1) {
            if (iNextInt3 == 2) {
                iNextInt = (int) (iNextInt + DeviceUtils.dpToPixel(this, 78.0f));
            } else {
                if (iNextInt3 == 3) {
                    f = iNextInt2;
                    fDpToPixel = DeviceUtils.dpToPixel(this, 78.0f);
                } else {
                    iNextInt = (int) (iNextInt + DeviceUtils.dpToPixel(this, 78.0f));
                    f = iNextInt2;
                    fDpToPixel = DeviceUtils.dpToPixel(this, 78.0f);
                }
                iNextInt2 = (int) (f + fDpToPixel);
            }
        }
        layoutParams.leftMargin = iNextInt;
        layoutParams.topMargin = iNextInt2;
        PetkitLog.d("addDevice leftMargin: " + iNextInt + " topMargin: " + iNextInt2);
        this.scanFrameLayout.addView(imageView, layoutParams);
        ArrayList arrayList = new ArrayList();
        arrayList.add(deviceInfo);
        this.mListAdapter.addList(arrayList);
        this.mListAdapter.notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void signupDevice() {
        if (this.mBleDeviceInfo == null) {
            return;
        }
        HashMap map = new HashMap();
        map.put("mac", this.mBleDeviceInfo.getMac());
        if (this.mBleDeviceInfo.getDeviceId() != 0) {
            map.put("id", String.valueOf(this.mBleDeviceInfo.getDeviceId()));
        }
        showLoadDialog();
        post(ApiTools.SAMPLE_API_DEVICE_SIGNUP, map, new AsyncHttpRespHandler(this) { // from class: com.petkit.android.activities.fit.PetkitScanActivity.4
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFinish() {
                super.onFinish();
                if (PetkitScanActivity.this.isBindSuccess) {
                    return;
                }
                LoadDialog.dismissDialog();
                Intent intent = new Intent(BLEConsts.BROADCAST_ACTION);
                intent.putExtra(BLEConsts.EXTRA_ACTION, 2);
                LocalBroadcastManager.getInstance(PetkitScanActivity.this).sendBroadcast(intent);
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                SignupDeviceRsp signupDeviceRsp = (SignupDeviceRsp) this.gson.fromJson(this.responseResult, SignupDeviceRsp.class);
                if (signupDeviceRsp.getError() != null) {
                    PetkitScanActivity.this.showShortToast(signupDeviceRsp.getError().getMsg(), R.drawable.toast_failed);
                    return;
                }
                if (signupDeviceRsp.getResult() != null) {
                    PetkitScanActivity.this.mDevice = signupDeviceRsp.getResult();
                    PetkitScanActivity.this.isBindSuccess = true;
                    if (PetkitScanActivity.this.mBluetoothLeUtils != null) {
                        if (PetkitScanActivity.this.mBleDeviceInfo.getDeviceId() != 0) {
                            PetkitScanActivity.this.mBluetoothLeUtils.changeDevice(signupDeviceRsp.getResult().getSecret(), PetkitScanActivity.this);
                            return;
                        } else {
                            PetkitScanActivity.this.mBluetoothLeUtils.initdevice(String.valueOf(signupDeviceRsp.getResult().getId()), signupDeviceRsp.getResult().getSecretKey(), signupDeviceRsp.getResult().getSecret(), PetkitScanActivity.this);
                            return;
                        }
                    }
                    Intent intent = new Intent(BLEConsts.BROADCAST_ACTION);
                    intent.putExtra(BLEConsts.EXTRA_ACTION, 1);
                    intent.putExtra(BLEConsts.EXTRA_SECRET_KEY, signupDeviceRsp.getResult().getSecretKey());
                    intent.putExtra(BLEConsts.EXTRA_SECRET, signupDeviceRsp.getResult().getSecret());
                    intent.putExtra(BLEConsts.EXTRA_DEVICE_ID, String.valueOf(signupDeviceRsp.getResult().getId()));
                    LocalBroadcastManager.getInstance(PetkitScanActivity.this).sendBroadcast(intent);
                    return;
                }
                PetkitScanActivity.this.showLongToast(R.string.BLEUI_bind_PETKIT_failed);
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFailure(int i, Header[] headerArr, byte[] bArr, Throwable th) {
                super.onFailure(i, headerArr, bArr, th);
                PetkitScanActivity.this.showLongToast(R.string.BLEUI_bind_PETKIT_failed);
            }
        }, false);
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        int headerViewsCount = i - this.mListView.getHeaderViewsCount();
        if (headerViewsCount < 0 || headerViewsCount >= this.mListAdapter.getCount()) {
            return;
        }
        DeviceInfo deviceInfo = (DeviceInfo) this.mListAdapter.getItem(headerViewsCount);
        this.mBleDeviceInfo = deviceInfo;
        if (deviceInfo.isChecked()) {
            showLoadDialog();
            if (this.mBluetoothLeUtils != null) {
                changeScanState(2);
                this.mBluetoothLeUtils.stopScan();
                if (this.mBluetoothLeUtils.onDeviceConnect(this.mBleDeviceInfo)) {
                    LoadDialog.show(this, getString(R.string.Binding));
                    return;
                }
                return;
            }
            Intent intent = new Intent(BLEConsts.BROADCAST_ACTION);
            intent.putExtra(BLEConsts.EXTRA_ACTION, 2);
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
            Bundle bundle = new Bundle();
            bundle.putInt(BLEConsts.EXTRA_ACTION, this.mBleDeviceInfo.getDeviceId() == 0 ? 5 : 6);
            bundle.putSerializable(BLEConsts.EXTRA_DEVICE_INFO, this.mBleDeviceInfo);
            startBLEAction(bundle);
        }
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == -1 && i == 33) {
            setResult(-1);
            finish();
        }
    }

    @Override // com.petkit.android.activities.base.BaseActivity, android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.scan_img) {
            if (this.scanState != 1) {
                Bundle bundle = new Bundle();
                bundle.putInt(BLEConsts.EXTRA_ACTION, 4);
                bundle.putSerializable(BLEConsts.EXTRA_DOG, this.curPet);
                startBLEAction(bundle);
                return;
            }
            return;
        }
        if (id == R.id.device_connect) {
            DeviceInfo deviceInfo = (DeviceInfo) view.getTag();
            this.mBleDeviceInfo = deviceInfo;
            if (deviceInfo.isChecked()) {
                showLoadDialog();
                if (this.mBluetoothLeUtils != null) {
                    changeScanState(2);
                    this.mBluetoothLeUtils.stopScan();
                    if (this.mBluetoothLeUtils.onDeviceConnect(this.mBleDeviceInfo)) {
                        LoadDialog.show(this, getString(R.string.Binding));
                        return;
                    }
                    return;
                }
                Bundle bundle2 = new Bundle();
                bundle2.putInt(BLEConsts.EXTRA_ACTION, this.mBleDeviceInfo.getDeviceId() == 0 ? 5 : 6);
                bundle2.putSerializable(BLEConsts.EXTRA_DEVICE_INFO, this.mBleDeviceInfo);
                startBLEAction(bundle2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void bindDevice(String str) {
        HashMap map = new HashMap();
        map.put("petId", this.curPet.getId());
        map.put(DeviceRequestsHelper.DEVICE_INFO_DEVICE, str);
        post(ApiTools.SAMPLE_API_PET_BINDDEVICE, map, new AsyncHttpRespHandler() { // from class: com.petkit.android.activities.fit.PetkitScanActivity.5
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
                    PetkitScanActivity.this.curPet.setDevice(PetkitScanActivity.this.mDevice);
                    UserInforUtils.updateDogInformation(PetkitScanActivity.this.curPet, 3);
                    new HashMap().put("type", PetkitScanActivity.this.mDevice.getHardware() == 1 ? "p1" : "p2");
                    Intent intent = new Intent();
                    intent.setAction(Constants.BROADCAST_MSG_UPDATE_DOG);
                    intent.putExtra(Constants.EXTRA_DOG, PetkitScanActivity.this.curPet);
                    LocalBroadcastManager.getInstance(PetkitScanActivity.this).sendBroadcast(intent);
                    PetkitScanActivity.this.storeUserInfor();
                    PetkitScanActivity.this.showLongToast(R.string.Succeed, R.drawable.toast_succeed);
                    LocalBroadcastManager.getInstance(PetkitScanActivity.this).sendBroadcast(new Intent(Constants.BROADCAST_MSG_DEVICE_UPDATE));
                    LocalBroadcastManager.getInstance(PetkitScanActivity.this).sendBroadcast(new Intent(Constants.BROADCAST_MSG_DEVICE_BIND_FIT));
                    PetkitScanActivity.this.startActivity(HomeActivity.class, false);
                    return;
                }
                PetkitScanActivity.this.showLongToast(bindDeviceRsp.getError().getMsg(), R.drawable.toast_failed);
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
        this.mBroadcastReceiver = new BroadcastReceiver() { // from class: com.petkit.android.activities.fit.PetkitScanActivity.6
            @Override // android.content.BroadcastReceiver
            @SuppressLint({"DefaultLocale"})
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(BLEConsts.BROADCAST_PROGRESS)) {
                    int intExtra = intent.getIntExtra(BLEConsts.EXTRA_PROGRESS, 0);
                    String stringExtra = intent.getStringExtra(BLEConsts.EXTRA_DATA);
                    if (intExtra == -16) {
                        LoadDialog.dismissDialog();
                        PetkitScanActivity.this.showBindDialog();
                    } else if (intExtra == -15) {
                        PetkitScanActivity.this.changeScanState(2);
                    } else if (intExtra == -11) {
                        PetkitLog.d("battery: " + stringExtra);
                        if (PetkitScanActivity.this.isEmpty(stringExtra) || PetkitScanActivity.this.mDevice == null) {
                            return;
                        } else {
                            try {
                                PetkitScanActivity.this.mDevice.setBattery(Integer.valueOf(stringExtra).intValue());
                            } catch (Exception unused) {
                            }
                        }
                    } else if (intExtra != -9) {
                        if (intExtra == -8) {
                            PetkitScanActivity.this.changeScanState(1);
                        } else if (intExtra == -6) {
                            LoadDialog.dismissDialog();
                            PetkitScanActivity.this.bindDevice(new Gson().toJson(PetkitScanActivity.this.mDevice));
                        } else if (intExtra == -5) {
                            LoadDialog.dismissDialog();
                            if (PetkitScanActivity.this.mAlertDialog != null && PetkitScanActivity.this.mAlertDialog.isShowing()) {
                                PetkitScanActivity.this.mAlertDialog.dismiss();
                            }
                        }
                    } else {
                        if (PetkitScanActivity.this.isEmpty(stringExtra)) {
                            return;
                        }
                        Device device = (Device) new Gson().fromJson(stringExtra, Device.class);
                        if (PetkitScanActivity.this.mDevice != null) {
                            PetkitScanActivity.this.mDevice.setFirmware(device.getFirmware());
                            PetkitScanActivity.this.mDevice.setHardware(device.getHardware());
                            PetkitScanActivity.this.mDevice.setFrequence(device.getFrequence());
                            PetkitScanActivity.this.mDevice.setExtra(device.getExtra());
                        }
                    }
                    PetkitLog.d(String.format("BROADCAST_PROGRESS: op = %s, data = %s", Integer.valueOf(intExtra), stringExtra));
                    return;
                }
                if (intent.getAction().equals(BLEConsts.BROADCAST_ERROR)) {
                    int intExtra2 = intent.getIntExtra(BLEConsts.EXTRA_DATA, 0);
                    PetkitLog.d(String.format("BROADCAST_ERROR: error = %d ", Integer.valueOf(intExtra2)));
                    if (intExtra2 == 4097) {
                        LoadDialog.dismissDialog();
                        if (PetkitScanActivity.this.mAlertDialog == null || !PetkitScanActivity.this.mAlertDialog.isShowing()) {
                            return;
                        }
                        PetkitScanActivity.this.mAlertDialog.dismiss();
                        return;
                    }
                    LoadDialog.dismissDialog();
                    if (PetkitScanActivity.this.mAlertDialog != null && PetkitScanActivity.this.mAlertDialog.isShowing()) {
                        PetkitScanActivity.this.mAlertDialog.dismiss();
                    }
                    PetkitScanActivity.this.showShortToast(R.string.BLEUI_connect_failed);
                    return;
                }
                if (intent.getAction().equals(BLEConsts.BROADCAST_LOG)) {
                    PetkitLog.d(String.format("BROADCAST_LOG: data = %s", intent.getStringExtra(BLEConsts.EXTRA_LOG_MESSAGE)));
                    return;
                }
                if (intent.getAction().equals(BLEConsts.BROADCAST_SCANED_DEVICE)) {
                    DeviceInfo deviceInfo = (DeviceInfo) intent.getSerializableExtra(BLEConsts.EXTRA_DEVICE_INFO);
                    if (deviceInfo.getType() == 1 || deviceInfo.getType() == 2) {
                        if (deviceInfo.isChecked()) {
                            PetkitScanActivity.this.addDevice(deviceInfo);
                            return;
                        } else {
                            PetkitScanActivity.this.checkDeviceBindStatusNew(deviceInfo);
                            return;
                        }
                    }
                    return;
                }
                if (Constants.BROADCAST_MSG_DEVICE_BIND_FIT.equals(intent.getAction())) {
                    new Handler().postDelayed(new Runnable() { // from class: com.petkit.android.activities.fit.PetkitScanActivity.6.1
                        @Override // java.lang.Runnable
                        public void run() {
                            PetkitScanActivity.this.finish();
                        }
                    }, 400L);
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BLEConsts.BROADCAST_PROGRESS);
        intentFilter.addAction(BLEConsts.BROADCAST_ERROR);
        intentFilter.addAction(BLEConsts.BROADCAST_LOG);
        intentFilter.addAction(BLEConsts.BROADCAST_SCANED_DEVICE);
        intentFilter.addAction(Constants.BROADCAST_MSG_DEVICE_BIND_FIT);
        LocalBroadcastManager.getInstance(this).registerReceiver(this.mBroadcastReceiver, intentFilter);
    }

    private void unregisterBroadcastReceiver() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(this.mBroadcastReceiver);
    }

    @Override // com.petkit.android.activities.base.BaseActivity
    public void showLoadDialog() {
        this.isBindSuccess = false;
        LoadDialog.show(this, getString(R.string.Binding), false, new DialogInterface.OnCancelListener() { // from class: com.petkit.android.activities.fit.PetkitScanActivity.7
            @Override // android.content.DialogInterface.OnCancelListener
            public void onCancel(DialogInterface dialogInterface) {
                Intent intent = new Intent(BLEConsts.BROADCAST_ACTION);
                intent.putExtra(BLEConsts.EXTRA_ACTION, 2);
                LocalBroadcastManager.getInstance(PetkitScanActivity.this).sendBroadcast(intent);
            }
        });
    }

    @Override // com.petkit.android.ble.samsung.ISamsungBLEListener
    public void onScanResultChange(DeviceInfo deviceInfo) {
        if (deviceInfo == null || deviceInfo.getName() == null) {
            return;
        }
        if (deviceInfo.getName().equalsIgnoreCase(BLEConsts.PET_FIT_DISPLAY_NAME) || deviceInfo.getName().equalsIgnoreCase(BLEConsts.PET_FIT2_DISPLAY_NAME)) {
            if (deviceInfo.isChecked()) {
                addDevice(deviceInfo);
            } else {
                checkDeviceBindStatusNew(deviceInfo);
            }
        }
    }

    @Override // com.petkit.android.ble.samsung.ISamsungBLEListener
    public void updateProgress(int i, String str) {
        Device device;
        if (i == -22) {
            if (CommonUtils.getSysIntMap(CommonUtils.getAppContext(), Consts.SHARED_SYSTEM_TIME_VALID_STATE) != 0) {
                showLongToast(R.string.BLEUI_ble_system_time_error);
                return;
            } else {
                changeScanState(1);
                this.mBluetoothLeUtils.startScan();
                return;
            }
        }
        if (i == -11) {
            PetkitLog.d("battery: " + str);
            if (isEmpty(str) || (device = this.mDevice) == null) {
                return;
            }
            try {
                device.setBattery(Integer.valueOf(str).intValue());
                return;
            } catch (Exception unused) {
                return;
            }
        }
        if (i == -6) {
            runOnUiThread(new Runnable() { // from class: com.petkit.android.activities.fit.PetkitScanActivity.8
                @Override // java.lang.Runnable
                public void run() {
                    LoadDialog.dismissDialog();
                    PetkitScanActivity.this.bindDevice(new Gson().toJson(PetkitScanActivity.this.mDevice));
                }
            });
            return;
        }
        if (i == -16) {
            showBindDialog();
            return;
        }
        if (i == -15) {
            runOnUiThread(new Runnable() { // from class: com.petkit.android.activities.fit.PetkitScanActivity.9
                @Override // java.lang.Runnable
                public void run() {
                    PetkitScanActivity.this.changeScanState(2);
                }
            });
            return;
        }
        if (i != -9) {
            if (i == -8) {
                changeScanState(1);
                return;
            } else {
                if (i > 100) {
                    stopGatt();
                    return;
                }
                return;
            }
        }
        if (isEmpty(str)) {
            return;
        }
        Device device2 = (Device) new Gson().fromJson(str, Device.class);
        this.mDevice.setFirmware(device2.getFirmware());
        this.mDevice.setHardware(device2.getHardware());
        this.mDevice.setFrequence(device2.getFrequence());
        this.mDevice.setExtra(device2.getExtra());
    }

    private void stopGatt() {
        LoadDialog.dismissDialog();
        BaseBluetoothLeUtils baseBluetoothLeUtils = this.mBluetoothLeUtils;
        if (baseBluetoothLeUtils != null) {
            baseBluetoothLeUtils.stopGatt();
        }
        AlertDialog alertDialog = this.mAlertDialog;
        if (alertDialog == null || !alertDialog.isShowing()) {
            return;
        }
        this.mAlertDialog.dismiss();
    }
}

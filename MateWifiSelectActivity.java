package com.petkit.android.activities.mate.setting;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.jess.arms.utils.Consts;
import com.jess.arms.widget.LoadDialog;
import com.petkit.android.activities.base.BaseListActivity;
import com.petkit.android.activities.base.adapter.NormalBaseAdapter;
import com.petkit.android.activities.mate.utils.HsConsts;
import com.petkit.android.ble.BLEConsts;
import com.petkit.android.ble.WifiInfo;
import com.petkit.android.ble.service.AndroidBLEActionService;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.LogcatStorageHelper;
import com.petkit.android.utils.PetkitLog;
import com.petkit.oversea.R;
import in.srain.cube.views.ptr.PtrFrameLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class MateWifiSelectActivity extends BaseListActivity {
    public static final String HS_DEVICE_ID = "HS_DEVICE";
    public static final String HS_DEVICE_SECRET = "HS_DEVICE_SECRET";
    public static final String HS_WIFI_BSSID = "HS_WIFI_BSSID";
    public static final String HS_WIFI_PSD = "HS_WIFI_PSD";
    public static final String HS_WIFI_SSID = "HS_WIFI_SSID";
    private static final int RESULT_COMPLETED_CODE = 274;
    private static final int RESULT_IN_SETTING_CODE = 275;
    private static final int RESULT_WIFI_PSD_CODE = 273;
    public static final String TAG = "HsWifiSelectActivity";
    private String deviceId;
    private BroadcastReceiver mBroadcastReceiver;
    private View mHeaderView;
    private WifiInfo mWifiInfo;
    private TextView myNetwork;
    private String secret;
    private boolean result = false;
    private boolean isBackGround = false;
    private WifiInfo myWifi = null;

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
        super.onCreate(bundle);
        if (bundle != null) {
            this.deviceId = bundle.getString(HS_DEVICE_ID);
            this.secret = bundle.getString(HS_DEVICE_SECRET);
        } else {
            this.deviceId = getIntent().getStringExtra(HS_DEVICE_ID);
            this.secret = getIntent().getStringExtra(HS_DEVICE_SECRET);
        }
        if (isEmpty(this.deviceId)) {
            finish();
        } else {
            registerBoradcastReceiver();
        }
    }

    @Override // com.petkit.android.activities.base.BaseListActivity, com.petkit.android.activities.base.BaseActivity
    public void setupViews() {
        super.setupViews();
        setTitle(R.string.Mate_select_network);
        setListBackgroundResource(R.color.white);
        setViewState(1);
        getWifiList();
    }

    @Override // com.petkit.android.activities.base.BaseListActivity
    public void initAdapter() {
        this.mListAdapter = new WifiListAdapter(this, new ArrayList());
        this.mListView.setDividerHeight(0);
        this.mListView.addHeaderView(initHeaderView());
        this.mListView.setBackgroundColor(getResources().getColor(R.color.mate_bg));
        this.mListView.setAdapter((ListAdapter) this.mListAdapter);
        this.mListView.setOnItemClickListener(this);
    }

    @SuppressLint({"InflateParams"})
    private View initHeaderView() {
        View viewInflate = LayoutInflater.from(this).inflate(R.layout.layout_hs_wifi_setting, (ViewGroup) null);
        this.mHeaderView = viewInflate;
        TextView textView = (TextView) viewInflate.findViewById(R.id.mate_wifi_ssid);
        this.myNetwork = textView;
        textView.setOnClickListener(this);
        return this.mHeaderView;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showAnimation() {
        Animation animationLoadAnimation = AnimationUtils.loadAnimation(this, R.anim.scan_rotate);
        animationLoadAnimation.setInterpolator(new LinearInterpolator());
        if (this.mHeaderView.findViewById(R.id.mate_wifi_loading).getVisibility() == 0) {
            this.mHeaderView.findViewById(R.id.mate_wifi_loading).startAnimation(animationLoadAnimation);
        }
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        this.mHeaderView.findViewById(R.id.mate_wifi_loading).clearAnimation();
    }

    private void setNetViewVisibility(boolean z) {
        if (z) {
            this.mHeaderView.findViewById(R.id.currect_network).setVisibility(0);
        } else {
            this.mHeaderView.findViewById(R.id.currect_network).setVisibility(8);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void filterNetworkView(WifiInfo wifiInfo) {
        if (wifiInfo != null) {
            setNetViewVisibility(true);
            this.myNetwork.setText(wifiInfo.getDisplayName());
            if (wifiInfo.getLevel() > 90) {
                this.myNetwork.setCompoundDrawablesWithIntrinsicBounds(R.drawable.mate_selected, 0, R.drawable.mate_stat_sys_wifi_signal_4, 0);
            } else if (wifiInfo.getLevel() > 70) {
                this.myNetwork.setCompoundDrawablesWithIntrinsicBounds(R.drawable.mate_selected, 0, R.drawable.mate_stat_sys_wifi_signal_3, 0);
            } else if (wifiInfo.getLevel() > 50) {
                this.myNetwork.setCompoundDrawablesWithIntrinsicBounds(R.drawable.mate_selected, 0, R.drawable.mate_stat_sys_wifi_signal_2, 0);
            } else {
                this.myNetwork.setCompoundDrawablesWithIntrinsicBounds(R.drawable.mate_selected, 0, R.drawable.mate_stat_sys_wifi_signal_1, 0);
            }
            setTitleRightButton(R.string.Continue, new View.OnClickListener() { // from class: com.petkit.android.activities.mate.setting.MateWifiSelectActivity.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    MateWifiSelectActivity.this.result = true;
                    MateWifiSelectActivity.this.isBackGround = true;
                    MateWifiSelectActivity.this.startActivity(MateCompletedActivity.class);
                }
            });
        }
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        showAnimation();
        this.isBackGround = false;
        this.mWifiInfo = null;
        NormalBaseAdapter normalBaseAdapter = this.mListAdapter;
        if (normalBaseAdapter != null) {
            normalBaseAdapter.notifyDataSetChanged();
        }
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        HsConsts.sendAbortActionBroadcast(this);
        unregisterBroadcastReceiver();
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putString(HS_DEVICE_ID, this.deviceId);
    }

    @Override // com.petkit.android.activities.base.BaseActivity, android.view.View.OnClickListener
    public void onClick(View view) {
        WifiInfo wifiInfo;
        if (view.getId() != R.id.mate_wifi_ssid || (wifiInfo = this.myWifi) == null) {
            return;
        }
        showShortToast(wifiInfo.getAddress());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startWifiPsdActivity(boolean z) {
        if (isFinishing()) {
            return;
        }
        Intent intent = new Intent(this, (Class<?>) MateWifiPsdActivity.class);
        intent.putExtra(HS_DEVICE_ID, this.deviceId);
        intent.putExtra(HS_DEVICE_SECRET, this.secret);
        if (this.mWifiInfo == null) {
            this.mWifiInfo = new WifiInfo();
        }
        intent.putExtra(BLEConsts.EXTRA_WIFI_INFO, this.mWifiInfo);
        this.isBackGround = true;
        startActivity(intent);
    }

    private void restartInitMateWifi() {
        Bundle bundle = new Bundle();
        bundle.putInt(BLEConsts.EXTRA_ACTION, 9);
        bundle.putString(BLEConsts.EXTRA_DEVICE_ID, this.deviceId);
        bundle.putString(BLEConsts.EXTRA_SECRET, this.secret);
        if (!getPackageManager().hasSystemFeature("android.hardware.bluetooth_le")) {
            showShortToast(R.string.BLEUI_not_supported);
            finish();
        } else {
            Intent intent = new Intent(this, (Class<?>) AndroidBLEActionService.class);
            intent.putExtras(bundle);
            startService(intent);
            CommonUtils.addSysIntMap(this, Consts.SHARED_BLE_STATE, 1);
        }
    }

    private void getWifiList() {
        PetkitLog.d("===== start get wifi list =========");
        Intent intent = new Intent(BLEConsts.BROADCAST_ACTION);
        intent.putExtra(BLEConsts.EXTRA_ACTION, 1);
        intent.putExtra(BLEConsts.EXTRA_SECRET, this.secret);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    public class WifiListAdapter extends NormalBaseAdapter {
        public WifiListAdapter(Activity activity, List list) {
            super(activity, list);
        }

        @Override // android.widget.Adapter
        @SuppressLint({"InflateParams"})
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            WifiInfo wifiInfo = (WifiInfo) getItem(i);
            if (view != null && (view.getTag() instanceof ViewHolder)) {
                viewHolder = (ViewHolder) view.getTag();
            } else if (wifiInfo != null) {
                view = LayoutInflater.from(MateWifiSelectActivity.this).inflate(R.layout.adapter_hs_wifi_list, (ViewGroup) null);
                viewHolder = new ViewHolder();
                viewHolder.textView = (TextView) view.findViewById(R.id.mate_wifi_ssid);
                viewHolder.imageView = (ImageView) view.findViewById(R.id.mate_wifi_image);
                viewHolder.status = (ImageView) view.findViewById(R.id.mate_wifi_status);
                viewHolder.lock = (ImageView) view.findViewById(R.id.mate_wifi_lock);
            } else {
                view = LayoutInflater.from(MateWifiSelectActivity.this).inflate(R.layout.layout_wifi_bottom, (ViewGroup) null);
                if (MateWifiSelectActivity.this.myWifi != null && !TextUtils.isEmpty(MateWifiSelectActivity.this.myWifi.getDeviceMac())) {
                    TextView textView = (TextView) view.findViewById(R.id.wifi_mac);
                    MateWifiSelectActivity mateWifiSelectActivity = MateWifiSelectActivity.this;
                    textView.setText(mateWifiSelectActivity.getString(R.string.Mate_wifi_mac_address_format, mateWifiSelectActivity.myWifi.getDeviceMac()));
                }
                view.findViewById(R.id.h1_wifi_other).setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.mate.setting.MateWifiSelectActivity.WifiListAdapter.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view2) {
                        MateWifiSelectActivity.this.startWifiPsdActivity(false);
                    }
                });
                viewHolder = null;
            }
            if (wifiInfo == null) {
                return view;
            }
            if (MateWifiSelectActivity.this.mWifiInfo != null && MateWifiSelectActivity.this.mWifiInfo.getBSSID() != null && MateWifiSelectActivity.this.mWifiInfo.getBSSID().equals(wifiInfo.getBSSID())) {
                viewHolder.status.setBackgroundResource(R.drawable.mate_connecting);
                Animation animationLoadAnimation = AnimationUtils.loadAnimation(MateWifiSelectActivity.this, R.anim.scan_rotate);
                animationLoadAnimation.setInterpolator(new LinearInterpolator());
                viewHolder.status.startAnimation(animationLoadAnimation);
            } else {
                viewHolder.status.clearAnimation();
            }
            viewHolder.textView.setText(wifiInfo.getDisplayName());
            MateWifiSelectActivity.this.updateWifiLevel(viewHolder.imageView, Math.abs(wifiInfo.getLevel()));
            if (wifiInfo.getPassword() == 1) {
                viewHolder.lock.setImageDrawable(ContextCompat.getDrawable(MateWifiSelectActivity.this, R.drawable.mate_locked));
            } else {
                viewHolder.lock.setImageDrawable(null);
            }
            return view;
        }

        public class ViewHolder {
            public ImageView imageView;
            public ImageView lock;
            public ImageView status;
            public TextView textView;

            public ViewHolder() {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateWifiLevel(ImageView imageView, int i) {
        if (i > 90) {
            imageView.setImageResource(R.drawable.mate_stat_sys_wifi_signal_4);
            return;
        }
        if (i > 70) {
            imageView.setImageResource(R.drawable.mate_stat_sys_wifi_signal_3);
        } else if (i > 50) {
            imageView.setImageResource(R.drawable.mate_stat_sys_wifi_signal_2);
        } else {
            imageView.setImageResource(R.drawable.mate_stat_sys_wifi_signal_1);
        }
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        int headerViewsCount = i - this.mListView.getHeaderViewsCount();
        if (headerViewsCount < 0 || this.mListAdapter.getItem(headerViewsCount) == null) {
            return;
        }
        WifiInfo wifiInfo = (WifiInfo) this.mListAdapter.getItem(headerViewsCount);
        this.mWifiInfo = wifiInfo;
        if (wifiInfo.getPassword() == 1) {
            startWifiPsdActivity(true);
        } else {
            showLoadDialog();
            sendWriteWifiBroadcast();
        }
        this.mListAdapter.notifyDataSetChanged();
    }

    @Override // com.petkit.android.activities.base.BaseActivity
    public void showLoadDialog() {
        LoadDialog.show(this, getString(R.string.Mate_configuring), true);
    }

    private void unregisterBroadcastReceiver() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(this.mBroadcastReceiver);
    }

    private void registerBoradcastReceiver() {
        this.mBroadcastReceiver = new BroadcastReceiver() { // from class: com.petkit.android.activities.mate.setting.MateWifiSelectActivity.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(Constants.EXTRA_HS_WIFI_CONFIG_COMPLETE)) {
                    int intExtra = intent.getIntExtra(Constants.EXTRA_HS_WIFI_CONFIG_FLAG, -1);
                    if (intExtra == 0) {
                        MateWifiSelectActivity.this.finish();
                        return;
                    } else {
                        if (intExtra == 1) {
                            if (MateWifiSelectActivity.this.mHeaderView != null) {
                                MateWifiSelectActivity.this.mHeaderView.findViewById(R.id.mate_wifi_loading).setVisibility(0);
                                new Handler().postDelayed(new Runnable() { // from class: com.petkit.android.activities.mate.setting.MateWifiSelectActivity.2.1
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        MateWifiSelectActivity.this.showAnimation();
                                    }
                                }, 800L);
                            }
                            MateWifiSelectActivity.this.initAndRestartWifi();
                            return;
                        }
                        return;
                    }
                }
                if (intent.getAction().equals(BLEConsts.BROADCAST_ERROR)) {
                    PetkitLog.d("HsWifiSelectActivity error: " + intent.getIntExtra(BLEConsts.EXTRA_DATA, 0));
                    MateWifiSelectActivity.this.startErrorActivity();
                    return;
                }
                if (intent.getAction().equals(BLEConsts.BROADCAST_PROGRESS)) {
                    int intExtra2 = intent.getIntExtra(BLEConsts.EXTRA_PROGRESS, 0);
                    String stringExtra = intent.getStringExtra(BLEConsts.EXTRA_DATA);
                    if (intExtra2 == -27) {
                        MateWifiSelectActivity.this.sendWriteWifiBroadcast();
                        return;
                    }
                    if (intExtra2 != -26) {
                        if (intExtra2 == -14 || intExtra2 == -5 || intExtra2 == 4096 || intExtra2 == 32769 || intExtra2 == 32937) {
                            MateWifiSelectActivity.this.startErrorActivity();
                            return;
                        }
                        return;
                    }
                    LoadDialog.dismissDialog();
                    int iIntValue = Integer.valueOf(stringExtra).intValue();
                    LogcatStorageHelper.addLog("[wifi set result] result : " + iIntValue);
                    if (iIntValue == 0 || iIntValue == 1 || iIntValue == 2) {
                        MateWifiSelectActivity mateWifiSelectActivity = MateWifiSelectActivity.this;
                        CommonUtils.addSysMap(mateWifiSelectActivity, mateWifiSelectActivity.deviceId, MateWifiSelectActivity.this.mWifiInfo.getDisplayName());
                        Intent intent2 = new Intent(HsConsts.MATE_UPDATE_WIFI_STATUS);
                        intent2.putExtra(HsConsts.MATE_UPDATE_WIFI_STATUS, MateWifiSelectActivity.this.mWifiInfo.getDisplayName());
                        LocalBroadcastManager.getInstance(MateWifiSelectActivity.this).sendBroadcast(intent2);
                        MateWifiSelectActivity.this.startWifiSetingActivity();
                        return;
                    }
                    if (iIntValue == 14) {
                        MateWifiSelectActivity.this.showLongToast(R.string.Password_error, R.drawable.toast_failed);
                        return;
                    }
                    if (iIntValue == 15) {
                        MateWifiSelectActivity.this.showLongToast(R.string.Mate_network_connect_timeout, R.drawable.toast_failed);
                        return;
                    }
                    MateWifiSelectActivity.this.showLongToast(R.string.Mate_network_ip_error, R.drawable.toast_failed);
                    if (iIntValue == 19 || (iIntValue >= 3 && iIntValue <= 7)) {
                        ((BaseListActivity) MateWifiSelectActivity.this).mListAdapter.getList().clear();
                        ((BaseListActivity) MateWifiSelectActivity.this).mListAdapter.getList().add(null);
                        ((BaseListActivity) MateWifiSelectActivity.this).mListAdapter.notifyDataSetChanged();
                        return;
                    }
                    return;
                }
                if (intent.getAction().equals(BLEConsts.BROADCAST_SCANED_WIFI)) {
                    WifiInfo wifiInfo = (WifiInfo) intent.getSerializableExtra(BLEConsts.EXTRA_WIFI_INFO);
                    if (!MateWifiSelectActivity.this.isEmpty(wifiInfo.getAddress())) {
                        ((BaseListActivity) MateWifiSelectActivity.this).mListAdapter.clearList();
                        if (MateWifiSelectActivity.this.myWifi != null) {
                            MateWifiSelectActivity mateWifiSelectActivity2 = MateWifiSelectActivity.this;
                            if (!mateWifiSelectActivity2.isEmpty(mateWifiSelectActivity2.myWifi.getDeviceMac())) {
                                wifiInfo.setDeviceMac(MateWifiSelectActivity.this.myWifi.getDeviceMac());
                            }
                        }
                        MateWifiSelectActivity.this.myWifi = wifiInfo;
                        MateWifiSelectActivity.this.filterNetworkView(wifiInfo);
                        MateWifiSelectActivity mateWifiSelectActivity3 = MateWifiSelectActivity.this;
                        CommonUtils.addSysMap(mateWifiSelectActivity3, mateWifiSelectActivity3.deviceId, wifiInfo.getDisplayName());
                        return;
                    }
                    if (!MateWifiSelectActivity.this.isEmpty(wifiInfo.getDisplayName()) && MateWifiSelectActivity.this.removeSameSSID(wifiInfo)) {
                        ((BaseListActivity) MateWifiSelectActivity.this).mListAdapter.getList().add(((BaseListActivity) MateWifiSelectActivity.this).mListAdapter.getCount(), wifiInfo);
                        ((BaseListActivity) MateWifiSelectActivity.this).mListAdapter.notifyDataSetChanged();
                    }
                    ((BaseListActivity) MateWifiSelectActivity.this).mListAdapter.notifyDataSetChanged();
                    return;
                }
                if (intent.getAction().equals(BLEConsts.BROADCAST_SCANED_WIFI_COMPLETED) && intent.getIntExtra(Consts.MATE_GET_WIFI_STEP_KEY, -1) == 2) {
                    ((BaseListActivity) MateWifiSelectActivity.this).mListAdapter.getList().add(null);
                    ((BaseListActivity) MateWifiSelectActivity.this).mListAdapter.notifyDataSetChanged();
                    MateWifiSelectActivity.this.mHeaderView.findViewById(R.id.mate_wifi_loading).setVisibility(8);
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BLEConsts.BROADCAST_PROGRESS);
        intentFilter.addAction(BLEConsts.BROADCAST_ERROR);
        intentFilter.addAction(BLEConsts.BROADCAST_SCANED_DEVICE);
        intentFilter.addAction(BLEConsts.BROADCAST_SCANED_WIFI);
        intentFilter.addAction(BLEConsts.BROADCAST_SCANED_WIFI_COMPLETED);
        intentFilter.addAction(Constants.EXTRA_HS_WIFI_CONFIG_COMPLETE);
        LocalBroadcastManager.getInstance(this).registerReceiver(this.mBroadcastReceiver, intentFilter);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean removeSameSSID(WifiInfo wifiInfo) {
        List list = this.mListAdapter.getList();
        Iterator it = list.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            WifiInfo wifiInfo2 = (WifiInfo) it.next();
            if (wifiInfo2 != null && wifiInfo2.getSSID().equals(wifiInfo.getSSID()) && isEmpty(wifiInfo2.getAddress())) {
                if (wifiInfo2.getLevel() >= wifiInfo.getLevel()) {
                    return false;
                }
                list.remove(wifiInfo2);
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startErrorActivity() {
        this.result = false;
        if (isFinishing()) {
            return;
        }
        Intent intent = new Intent(this, (Class<?>) MateCompletedActivity.class);
        intent.putExtra(MateCompletedActivity.HS_SETUP_RESULT, false);
        this.isBackGround = true;
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendWriteWifiBroadcast() {
        Intent intent = new Intent(BLEConsts.BROADCAST_ACTION);
        intent.putExtra(BLEConsts.EXTRA_ACTION, 1);
        WifiInfo wifiInfo = this.mWifiInfo;
        if (wifiInfo != null) {
            intent.putExtra(BLEConsts.EXTRA_DEVICE_ADDRESS, wifiInfo.getBSSID());
            intent.putExtra(BLEConsts.EXTRA_WIFI_SECRET_KEY, this.mWifiInfo.getSSID());
        }
        intent.putExtra(BLEConsts.EXTRA_SECRET, "");
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startWifiSetingActivity() {
        if (isFinishing()) {
            return;
        }
        Intent intent = new Intent(this, (Class<?>) MateWifiSetingActivity.class);
        intent.putExtra(HS_DEVICE_ID, this.deviceId);
        this.isBackGround = true;
        startActivity(intent);
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initAndRestartWifi() {
        this.myWifi = null;
        setNetViewVisibility(false);
        this.mListAdapter.clearList();
        restartInitMateWifi();
    }
}

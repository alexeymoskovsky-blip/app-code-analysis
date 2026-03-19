package com.petkit.android.activities.feeder.bind;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.BitmapDrawable;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.alibaba.fastjson.asm.Opcodes;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.jess.arms.utils.Consts;
import com.jess.arms.widget.LoadDialog;
import com.petkit.android.activities.base.BaseActivity;
import com.petkit.android.activities.community.WebviewActivity;
import com.petkit.android.activities.feeder.Utils.FeederUtils;
import com.petkit.android.activities.feeder.Utils.PetkitSocketInstance;
import com.petkit.android.activities.feeder.api.FeederDeviceStateRsp;
import com.petkit.android.activities.feeder.api.SignupRsp;
import com.petkit.android.activities.feeder.bind.utils.FeederBindUtils;
import com.petkit.android.activities.feeder.model.FeederRecord;
import com.petkit.android.activities.feeder.setting.esptouch.EspWifiAdminSimple;
import com.petkit.android.activities.feeder.widget.ReputPasswordWindow;
import com.petkit.android.activities.petkitBleDevice.utils.FamilyUtils;
import com.petkit.android.api.ErrorCode;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.apiResponse.BaseRsp;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.JSONUtils;
import com.petkit.android.utils.LogcatStorageHelper;
import com.petkit.android.utils.PetkitLog;
import com.petkit.android.utils.Rom;
import com.petkit.oversea.R;
import cz.msebera.android.httpclient.Header;
import java.util.HashMap;
import java.util.TimeZone;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class FeederBindConnectApActivity extends BaseActivity implements PetkitSocketInstance.IPetkitSocketListener {
    private static final int SCHEDULE_MAX_TIME = 60000;
    private static final int SCHEDULE_TIME = 5000;
    boolean isBind = false;
    boolean isChecked = false;
    private EspWifiAdminSimple mAdminSimple;
    private BroadcastReceiver mBroadcastReceiver;
    private int mCheckDeviceOnlineTimes;
    private String mDefaultSsid;
    private long mDeviceId;
    private FeederRecord mFeederRecord;
    private long mScheduleStartTime;
    private String password;
    private String ssid;

    @Override // com.petkit.android.activities.feeder.Utils.PetkitSocketInstance.IPetkitSocketListener
    public void onDisconnected() {
    }

    @Override // com.petkit.android.activities.feeder.Utils.PetkitSocketInstance.IPetkitSocketListener
    public void onFailed() {
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            this.ssid = bundle.getString("ssid");
            this.password = bundle.getString("password");
            this.mDeviceId = bundle.getLong(FeederUtils.EXTRA_FEEDER_ID);
        } else {
            this.ssid = getIntent().getStringExtra("ssid");
            this.password = getIntent().getStringExtra("password");
            this.mDeviceId = getIntent().getLongExtra(FeederUtils.EXTRA_FEEDER_ID, 0L);
        }
        setContentView(R.layout.activity_feeder_bind_connect_ap);
        this.mAdminSimple = new EspWifiAdminSimple(this);
        this.mDefaultSsid = getResources().getString(R.string.default_feeder_ap_name);
        LogcatStorageHelper.addLog("[Feeder Bind] start connect ap");
        registerBoradcastReceiver();
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        String wifiConnectedSsid = this.mAdminSimple.getWifiConnectedSsid();
        String currentApHostIp = this.mAdminSimple.getCurrentApHostIp();
        if (checkPermission()) {
            if (wifiConnectedSsid == null || !wifiConnectedSsid.startsWith(this.mDefaultSsid)) {
                return;
            }
            PetkitLog.d("remote ip: " + currentApHostIp);
            if (isEmpty(currentApHostIp)) {
                LogcatStorageHelper.addLog("[warning] can not get ap ip!");
                showShortToast(R.string.Failure);
                return;
            } else {
                LoadDialog.show(this, getString(R.string.Processing), true);
                PetkitSocketInstance.getInstance().setPetkitSocketListener(this);
                PetkitSocketInstance.getInstance().startConnect(currentApHostIp, 8001);
                return;
            }
        }
        if (isEmpty(currentApHostIp) || !currentApHostIp.contains("192.168.4.1")) {
            LogcatStorageHelper.addLog("[warning] can not get ap ip!");
            return;
        }
        LoadDialog.show(this, getString(R.string.Processing), true);
        PetkitSocketInstance.getInstance().setPetkitSocketListener(this);
        PetkitSocketInstance.getInstance().startConnect(currentApHostIp, 8001);
    }

    @Override // com.petkit.android.activities.base.BaseActivity
    public void setupViews() {
        setTitle(R.string.Title_feeder_bind_connect_ap);
        findViewById(R.id.feeder_bind_next).setOnClickListener(this);
        findViewById(R.id.feeder_bind_faq).setOnClickListener(this);
    }

    @Override // com.petkit.android.activities.base.BaseActivity, android.view.View.OnClickListener
    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.feeder_bind_next) {
            String wifiConnectedSsid = this.mAdminSimple.getWifiConnectedSsid();
            if (wifiConnectedSsid != null && wifiConnectedSsid.startsWith(this.mDefaultSsid)) {
                String currentApHostIp = this.mAdminSimple.getCurrentApHostIp();
                PetkitLog.d("remote ip: " + currentApHostIp);
                if (isEmpty(currentApHostIp)) {
                    LogcatStorageHelper.addLog("[warning] can not get ap ip!");
                    showShortToast(R.string.Failure);
                    return;
                } else {
                    LoadDialog.show(this, getString(R.string.Binding), true);
                    PetkitSocketInstance.getInstance().setPetkitSocketListener(this);
                    PetkitSocketInstance.getInstance().startConnect(currentApHostIp, 8001);
                    return;
                }
            }
            startActivity(new Intent("android.settings.WIFI_SETTINGS"));
            return;
        }
        if (view.getId() == R.id.feeder_bind_faq) {
            Bundle bundle = new Bundle();
            bundle.putString(Constants.EXTRA_LOAD_PATH, ApiTools.getWebUrlByKey("feeder_video"));
            startActivityWithData(WebviewActivity.class, bundle, false);
        }
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putString("ssid", this.ssid);
        bundle.putString("password", this.password);
        bundle.putLong(FeederUtils.EXTRA_FEEDER_ID, this.mDeviceId);
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        LoadDialog.dismissDialog();
        unregisterBroadcastReceiver();
    }

    private void registerBoradcastReceiver() {
        this.mBroadcastReceiver = new BroadcastReceiver() { // from class: com.petkit.android.activities.feeder.bind.FeederBindConnectApActivity.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                action.hashCode();
                if (action.equals(FeederUtils.BROADCAST_FEEDER_BIND_FAILED) || action.equals(FeederUtils.BROADCAST_FEEDER_BIND_COMPLETE)) {
                    FeederBindConnectApActivity.this.finish();
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(FeederUtils.BROADCAST_FEEDER_BIND_COMPLETE);
        intentFilter.addAction(FeederUtils.BROADCAST_FEEDER_BIND_FAILED);
        LocalBroadcastManager.getInstance(this).registerReceiver(this.mBroadcastReceiver, intentFilter);
    }

    private void unregisterBroadcastReceiver() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(this.mBroadcastReceiver);
    }

    @Override // com.petkit.android.activities.feeder.Utils.PetkitSocketInstance.IPetkitSocketListener
    public void onConnected() {
        PetkitSocketInstance.getInstance().sendString(FeederBindUtils.getDefaultRequestForKey(110));
    }

    @Override // com.petkit.android.activities.feeder.Utils.PetkitSocketInstance.IPetkitSocketListener
    public void onResponse(int i, String str) {
        if (i == 110) {
            try {
                JSONObject jSONObject = JSONUtils.getJSONObject(str);
                String string = !jSONObject.isNull("sn") ? jSONObject.getString("sn") : null;
                String string2 = !jSONObject.isNull("chipid") ? jSONObject.getString("chipid") : null;
                String string3 = !jSONObject.isNull("mac") ? jSONObject.getString("mac") : null;
                int i2 = jSONObject.isNull("hardware") ? 0 : jSONObject.getInt("hardware");
                String string4 = !jSONObject.isNull("software") ? jSONObject.getString("software") : null;
                String string5 = jSONObject.isNull("id") ? null : jSONObject.getString("id");
                if (this.mDeviceId > 0) {
                    if (string5 != null) {
                        long jLongValue = Long.valueOf(string5).longValue();
                        long j = this.mDeviceId;
                        if (jLongValue == j) {
                            this.mFeederRecord = FeederUtils.getFeederRecordByDeviceId(j);
                        }
                    }
                    processConfigFail(1, getString(R.string.Error_wifi_setting_not_mine), true);
                    PetkitSocketInstance.getInstance().disconnect();
                    return;
                }
                this.mFeederRecord = new FeederRecord();
                if (!isEmpty(string5)) {
                    this.mFeederRecord.setDeviceId(Long.valueOf(string5).longValue());
                }
                this.mFeederRecord.setSn(string);
                this.mFeederRecord.setMac(string3);
                this.mFeederRecord.setHardware(i2);
                this.mFeederRecord.setFirmware(string4);
                this.mFeederRecord.setChipid(string2);
                PetkitLog.d("sn: " + string + "  mac: " + string3);
                PetkitSocketInstance.getInstance().sendString(FeederBindUtils.getDefaultRequestForKey(111));
                return;
            } catch (JSONException e) {
                e.printStackTrace();
                return;
            }
        }
        if (i != 111) {
            if (i == 151) {
                PetkitSocketInstance.getInstance().sendString(FeederBindUtils.getDefaultRequestForKey(Opcodes.IFEQ));
                return;
            }
            if (i == 153) {
                PetkitSocketInstance.getInstance().disconnect();
                return;
            }
            if (i != 200) {
                if (i != 2457) {
                    return;
                }
                processConfigFail(0, getString(R.string.Hint_device_bind_error), true);
                return;
            } else {
                FeederRecord feederRecord = this.mFeederRecord;
                if (feederRecord != null) {
                    FeederBindUtils.saveFeederBindLog(feederRecord.getSn(), str);
                    return;
                }
                return;
            }
        }
        try {
            JSONObject jSONObject2 = JSONUtils.getJSONObject(str);
            if (!jSONObject2.isNull("status")) {
                try {
                    int i3 = jSONObject2.getInt("status");
                    FeederRecord feederRecord2 = this.mFeederRecord;
                    if (feederRecord2 != null) {
                        FeederBindUtils.saveFeederBindLog(feederRecord2.getSn(), str);
                    }
                    if (i3 == 1) {
                        LoadDialog.dismissDialog();
                        ReputPasswordWindow reputPasswordWindow = new ReputPasswordWindow(this, true, this.ssid);
                        reputPasswordWindow.setBackgroundDrawable(new BitmapDrawable());
                        reputPasswordWindow.setListener(new ReputPasswordWindow.IActionListener() { // from class: com.petkit.android.activities.feeder.bind.FeederBindConnectApActivity$$ExternalSyntheticLambda1
                            @Override // com.petkit.android.activities.feeder.widget.ReputPasswordWindow.IActionListener
                            public final void onPasswordResult(String str2) {
                                this.f$0.lambda$onResponse$0(str2);
                            }
                        });
                        reputPasswordWindow.showAtLocation(getWindow().getDecorView(), 17, 0, 0);
                        return;
                    }
                    if (i3 == 2) {
                        processConfigFail(0, getString(i3 == 1 ? R.string.Password_error : R.string.Error_ap_not_found), true);
                        LoadDialog.dismissDialog();
                        HashMap map = new HashMap();
                        map.put("mode", 0);
                        PetkitSocketInstance.getInstance().sendString(FeederBindUtils.getRequestForKeyAndPayload(ErrorCode.ERR_USER_PARTNER_ALREADY_BINDED, map));
                        return;
                    }
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }
        } catch (JSONException e3) {
            e3.printStackTrace();
        }
        HashMap map2 = new HashMap();
        map2.put("ssid", this.ssid);
        map2.put("pwd", this.password);
        map2.put("hide", 1);
        map2.put("server", ApiTools.getDeviceApiHTTPUri(4));
        map2.put("timezone", String.valueOf(TimeZone.getDefault().getRawOffset() / 3600000.0f));
        map2.put("locale", TimeZone.getDefault().getID());
        PetkitSocketInstance.getInstance().sendString(FeederBindUtils.getRequestForKeyAndPayload(151, map2));
        startScheduleTimer();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onResponse$0(String str) {
        this.password = str;
        LoadDialog.show(this, getString(R.string.Processing), true);
        HashMap map = new HashMap();
        map.put("ssid", this.ssid);
        map.put("pwd", this.password);
        map.put("hide", 1);
        map.put("server", ApiTools.getDeviceApiHTTPUri(4));
        map.put("timezone", String.valueOf(TimeZone.getDefault().getRawOffset() / 3600000.0f));
        map.put("locale", TimeZone.getDefault().getID());
        PetkitSocketInstance.getInstance().sendString(FeederBindUtils.getRequestForKeyAndPayload(151, map));
        startScheduleTimer();
    }

    private void startScheduleTimer() {
        this.mScheduleStartTime = System.currentTimeMillis();
        new Handler().postDelayed(new Runnable() { // from class: com.petkit.android.activities.feeder.bind.FeederBindConnectApActivity.2
            @Override // java.lang.Runnable
            public void run() {
                if (FeederBindConnectApActivity.this.mDeviceId > 0) {
                    FeederBindConnectApActivity.this.updateFeederState();
                } else {
                    FeederBindConnectApActivity.this.checkDeviceStatus();
                }
            }
        }, 5000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isScheduleTimeout() {
        boolean z = System.currentTimeMillis() - this.mScheduleStartTime > 60000;
        if (z) {
            processConfigFail(0, getString(R.string.Error_wifi_setting_failed), true);
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkDeviceStatus() {
        HashMap map = new HashMap();
        map.put("sn", this.mFeederRecord.getSn());
        map.put("mac", this.mFeederRecord.getMac());
        post(ApiTools.SAMPLET_API_FEEDER_SIGNUP_STATUS, map, new AsyncHttpRespHandler() { // from class: com.petkit.android.activities.feeder.bind.FeederBindConnectApActivity.3
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                if (FeederBindConnectApActivity.this.isChecked) {
                    return;
                }
                SignupRsp signupRsp = (SignupRsp) this.gson.fromJson(this.responseResult, SignupRsp.class);
                if (signupRsp.getError() != null) {
                    FeederBindConnectApActivity.this.processConfigFail(1, signupRsp.getError().getMsg(), true);
                    return;
                }
                if (signupRsp.getResult() == null || signupRsp.getResult().getId() <= 0) {
                    if (FeederBindConnectApActivity.this.isScheduleTimeout()) {
                        return;
                    }
                    new Handler().postDelayed(new Runnable() { // from class: com.petkit.android.activities.feeder.bind.FeederBindConnectApActivity.3.1
                        @Override // java.lang.Runnable
                        public void run() {
                            FeederBindConnectApActivity.this.checkDeviceStatus();
                        }
                    }, 5000L);
                } else {
                    if (signupRsp.getResult().getUser() != null) {
                        FeederBindConnectApActivity.this.processConfigFail(1, FeederBindConnectApActivity.this.getString(R.string.BLEUI_Bind_invalid_device_format, signupRsp.getResult().getUser().getNick()), true);
                        return;
                    }
                    FeederBindConnectApActivity.this.mFeederRecord.setSecret(signupRsp.getResult().getSecret());
                    FeederBindConnectApActivity.this.mFeederRecord.setDeviceId(signupRsp.getResult().getId());
                    FeederBindConnectApActivity.this.mFeederRecord.setMac(signupRsp.getResult().getMac());
                    FeederBindConnectApActivity.this.bindDevice();
                }
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFailure(int i, Header[] headerArr, byte[] bArr, Throwable th) {
                super.onFailure(i, headerArr, bArr, th);
                if (FeederBindConnectApActivity.this.isScheduleTimeout()) {
                    return;
                }
                new Handler().postDelayed(new Runnable() { // from class: com.petkit.android.activities.feeder.bind.FeederBindConnectApActivity.3.2
                    @Override // java.lang.Runnable
                    public void run() {
                        FeederBindConnectApActivity.this.checkDeviceStatus();
                    }
                }, 5000L);
            }
        }, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void bindDevice() {
        HashMap map = new HashMap();
        map.put("id", this.mFeederRecord.getDeviceId() + "");
        map.put("mac", this.mFeederRecord.getMac());
        map.put("secret", this.mFeederRecord.getSecret());
        map.put(Consts.PET_GROUP_ID, String.valueOf(FamilyUtils.getInstance().getCurrentFamilyId(this)));
        if (this.isBind) {
            return;
        }
        post(ApiTools.SAMPLET_API_FEEDER_LINK, map, new AsyncHttpRespHandler() { // from class: com.petkit.android.activities.feeder.bind.FeederBindConnectApActivity.4
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFailure(int i, Header[] headerArr, byte[] bArr, Throwable th) {
                super.onFailure(i, headerArr, bArr, th);
                FeederBindConnectApActivity.this.processConfigFail(0, FeederBindConnectApActivity.this.getString(R.string.Hint_network_failed), true);
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                BaseRsp baseRsp = (BaseRsp) this.gson.fromJson(this.responseResult, BaseRsp.class);
                if (baseRsp.getError() != null) {
                    FeederBindConnectApActivity.this.showLongToast(baseRsp.getError().getMsg(), R.drawable.toast_failed);
                    FeederBindConnectApActivity.this.processConfigFail(1, baseRsp.getError().getMsg(), true);
                    return;
                }
                try {
                    JSONObject jSONObject = JSONUtils.getJSONObject(this.responseResult).getJSONObject("result");
                    FeederBindConnectApActivity.this.mFeederRecord = FeederUtils.storeFeederRecordFromJson(jSONObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                FeederBindConnectApActivity feederBindConnectApActivity = FeederBindConnectApActivity.this;
                feederBindConnectApActivity.isBind = true;
                feederBindConnectApActivity.startCheckDeviceOnline();
                LocalBroadcastManager.getInstance(FeederBindConnectApActivity.this).sendBroadcast(new Intent(FeederUtils.BROADCAST_FEEDER_STATE_CHANGED));
            }
        }, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateFeederState() {
        HashMap map = new HashMap();
        map.put("id", String.valueOf(this.mFeederRecord.getDeviceId()));
        post(ApiTools.SAMPLET_API_FEEDER_DEVICE_STATE, map, new AsyncHttpRespHandler() { // from class: com.petkit.android.activities.feeder.bind.FeederBindConnectApActivity.5
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                FeederDeviceStateRsp feederDeviceStateRsp = (FeederDeviceStateRsp) this.gson.fromJson(this.responseResult, FeederDeviceStateRsp.class);
                if (feederDeviceStateRsp.getResult() == null) {
                    FeederBindConnectApActivity.this.processConfigFail(1, feederDeviceStateRsp.getError().getMsg(), true);
                    return;
                }
                if (feederDeviceStateRsp.getResult().getWifi() != null && feederDeviceStateRsp.getResult().getPim() == 1 && FeederBindConnectApActivity.this.ssid.equals(feederDeviceStateRsp.getResult().getWifi().getSsid())) {
                    FeederBindConnectApActivity.this.mFeederRecord.setState(feederDeviceStateRsp.getResult().getOverall());
                    FeederBindConnectApActivity.this.mFeederRecord.setDeviceErrMsg(feederDeviceStateRsp.getResult().getErrorMsg());
                    FeederBindConnectApActivity.this.mFeederRecord.setDeviceErrCode(feederDeviceStateRsp.getResult().getErrorCode());
                    FeederBindConnectApActivity.this.mFeederRecord.setDesiccantLeftDays(feederDeviceStateRsp.getResult().getDesiccantLeftDays());
                    FeederBindConnectApActivity.this.mFeederRecord.setWifiBssid(feederDeviceStateRsp.getResult().getWifi().getBssid());
                    FeederBindConnectApActivity.this.mFeederRecord.setWifiSsid(feederDeviceStateRsp.getResult().getWifi().getSsid());
                    FeederBindConnectApActivity.this.mFeederRecord.save();
                    Bundle bundle = new Bundle();
                    bundle.putLong(FeederUtils.EXTRA_FEEDER_ID, FeederBindConnectApActivity.this.mFeederRecord.getDeviceId());
                    FeederBindConnectApActivity.this.startActivityWithData(FeederBindCompleteActivity.class, bundle, false);
                    LocalBroadcastManager.getInstance(FeederBindConnectApActivity.this).sendBroadcast(new Intent(FeederUtils.BROADCAST_FEEDER_STATE_CHANGED));
                    return;
                }
                FeederBindConnectApActivity feederBindConnectApActivity = FeederBindConnectApActivity.this;
                feederBindConnectApActivity.processConfigFail(0, feederBindConnectApActivity.getString(R.string.Error_wifi_setting_failed), true);
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFailure(int i, Header[] headerArr, byte[] bArr, Throwable th) {
                super.onFailure(i, headerArr, bArr, th);
                new Handler().post(new Runnable() { // from class: com.petkit.android.activities.feeder.bind.FeederBindConnectApActivity.5.1
                    @Override // java.lang.Runnable
                    public void run() {
                        FeederBindConnectApActivity.this.updateFeederState();
                    }
                });
            }
        }, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processConfigFail(int i, String str, boolean z) {
        Bundle bundle = new Bundle();
        bundle.putString("reason", str);
        bundle.putInt(FirebaseAnalytics.Param.LEVEL, i);
        startActivityWithData(FeederBindFailedActivity.class, bundle, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processConfigSuccess() {
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(Constants.BROADCAST_MSG_DEVICE_UPDATE));
        Bundle bundle = new Bundle();
        bundle.putLong(FeederUtils.EXTRA_FEEDER_ID, this.mFeederRecord.getDeviceId());
        bundle.putBoolean(Constants.EXTRA_BOOLEAN, this.mDeviceId == 0);
        startActivityWithData(FeederBindCompleteActivity.class, bundle, false);
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startCheckDeviceOnline() {
        this.mCheckDeviceOnlineTimes = 15;
        new Handler().postDelayed(new Runnable() { // from class: com.petkit.android.activities.feeder.bind.FeederBindConnectApActivity.6
            @Override // java.lang.Runnable
            public void run() {
                FeederBindConnectApActivity.this.checkDeviceOnline();
            }
        }, 1000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkDeviceOnline() {
        int i = this.mCheckDeviceOnlineTimes;
        if (i <= 0) {
            processConfigSuccess();
            return;
        }
        this.mCheckDeviceOnlineTimes = i - 1;
        HashMap map = new HashMap();
        map.put("id", String.valueOf(this.mFeederRecord.getDeviceId()));
        post(ApiTools.SAMPLET_API_FEEDER_DEVICE_STATE, map, new AsyncHttpRespHandler(this) { // from class: com.petkit.android.activities.feeder.bind.FeederBindConnectApActivity.7
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i2, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i2, headerArr, bArr);
                FeederDeviceStateRsp feederDeviceStateRsp = (FeederDeviceStateRsp) this.gson.fromJson(this.responseResult, FeederDeviceStateRsp.class);
                if (feederDeviceStateRsp.getResult() != null && feederDeviceStateRsp.getResult().getPim() == 1) {
                    FeederBindConnectApActivity.this.processConfigSuccess();
                } else {
                    new Handler().postDelayed(new Runnable() { // from class: com.petkit.android.activities.feeder.bind.FeederBindConnectApActivity.7.1
                        @Override // java.lang.Runnable
                        public void run() {
                            FeederBindConnectApActivity.this.checkDeviceOnline();
                        }
                    }, 1000L);
                }
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFailure(int i2, Header[] headerArr, byte[] bArr, Throwable th) {
                super.onFailure(i2, headerArr, bArr, th);
                FeederBindConnectApActivity.this.processConfigSuccess();
            }
        });
    }

    private boolean checkPermission() {
        LocationManager locationManager = (LocationManager) getSystemService("location");
        int i = Build.VERSION.SDK_INT;
        if (i < 23 || checkSelfPermission("android.permission.ACCESS_FINE_LOCATION") == 0) {
            return (i >= 23 || !Rom.isMiui()) && locationManager.isProviderEnabled("gps");
        }
        return false;
    }
}

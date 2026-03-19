package com.petkit.android.activities.mate.setting;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.jess.arms.widget.LoadDialog;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.orm.SugarRecord;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.base.SpecialStatusBaseActivity;
import com.petkit.android.activities.mate.utils.HsConsts;
import com.petkit.android.api.PetkitCallback;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.apiResponse.BaseRsp;
import com.petkit.android.api.http.apiResponse.ErrorInfor;
import com.petkit.android.api.http.apiResponse.MateInfoNewRsp;
import com.petkit.android.api.http.apiResponse.OtaCheckResult;
import com.petkit.android.api.http.apiResponse.OtaCheckRsp;
import com.petkit.android.api.http.apiResponse.OtaStatusRsp;
import com.petkit.android.api.repository.WebModelRepository;
import com.petkit.android.ble.BLEConsts;
import com.petkit.android.ble.WifiInfo;
import com.petkit.android.model.ChatCenter;
import com.petkit.android.model.MateDevice;
import com.petkit.android.utils.ChatUtils;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.PetkitLog;
import com.petkit.android.utils.UploadImagesUtils;
import com.petkit.oversea.R;
import cz.msebera.android.httpclient.Header;
import java.util.HashMap;
import java.util.LinkedHashMap;
import net.sourceforge.pinyin4j.ChineseToPinyinResource;

/* JADX INFO: loaded from: classes4.dex */
public class MateDetailsActivity extends SpecialStatusBaseActivity {
    public static final String TAG = "HsDeviceDetailsActivity";
    private BroadcastReceiver mBroadcastReceiver;
    private MateDevice mateDevice;
    private boolean isSharedDevice = false;
    private boolean isFamliy = false;
    private String index = "";
    private final int MATE_OTA = 0;
    private final int MATE_SHARE = 2;

    @Override // com.petkit.android.activities.base.SpecialStatusBaseActivity, com.petkit.android.activities.base.SwipeBackBaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        this.isNeedPhotoController = true;
        super.onCreate(bundle);
        if (bundle != null) {
            this.mateDevice = (MateDevice) bundle.getSerializable(Constants.EXTRA_HS_DEVICE_DEATILS);
            this.isSharedDevice = bundle.getBoolean(Constants.EXTRA_BOOLEAN);
            this.isFamliy = bundle.getBoolean("famliy");
            this.index = bundle.getString(Constants.EXTRA_TAG_ID);
        } else {
            this.mateDevice = (MateDevice) getIntent().getSerializableExtra(Constants.EXTRA_HS_DEVICE_DEATILS);
            this.isSharedDevice = getIntent().getBooleanExtra(Constants.EXTRA_BOOLEAN, false);
            this.isFamliy = getIntent().getBooleanExtra("famliy", false);
            this.index = getIntent().getStringExtra(Constants.EXTRA_TAG_ID);
        }
        if (this.mateDevice == null) {
            finish();
        } else {
            setContentView(R.layout.activity_hs_device_details);
            registerBoradcastReceiver();
        }
    }

    @Override // android.app.Activity
    public void onSaveInstanceState(Bundle bundle, PersistableBundle persistableBundle) {
        super.onSaveInstanceState(bundle, persistableBundle);
        bundle.putSerializable(Constants.EXTRA_HS_DEVICE_DEATILS, this.mateDevice);
        bundle.putBoolean(Constants.EXTRA_BOOLEAN, this.isSharedDevice);
        bundle.putBoolean("famliy", this.isFamliy);
        bundle.putString(Constants.EXTRA_TAG_ID, this.index);
    }

    @Override // com.petkit.android.activities.base.SpecialStatusBaseActivity
    public void setupViews() {
        setSpecialTitleView(R.layout.layout_mate_device_detail_header);
        initView();
        if (this.isSharedDevice) {
            return;
        }
        otaCheck();
        MateDevice mateDevice = this.mateDevice;
        if (mateDevice != null) {
            getMateinfo(mateDevice.getId());
        }
    }

    @Override // com.petkit.android.activities.base.SpecialStatusBaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        if (this.isSharedDevice) {
            return;
        }
        unregisterBroadcastReceiver();
    }

    private void unregisterBroadcastReceiver() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(this.mBroadcastReceiver);
    }

    private void registerBoradcastReceiver() {
        this.mBroadcastReceiver = new BroadcastReceiver() { // from class: com.petkit.android.activities.mate.setting.MateDetailsActivity.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                WifiInfo wifiInfo;
                if (MateDetailsActivity.this.isFinishing()) {
                    return;
                }
                String action = intent.getAction();
                if (action.equals(HsConsts.MATE_UPDATE_STATUS)) {
                    int i = intent.getExtras().getInt(HsConsts.MATE_UPDATE_STATUS, 1);
                    MateDetailsActivity.this.updateStatus(i);
                    if (i == 4) {
                        MateDetailsActivity.this.refreshOtaView(2);
                        return;
                    }
                    return;
                }
                if (action.equals(HsConsts.MATE_UPDATE_OTA_RESULT)) {
                    TextView textView = (TextView) MateDetailsActivity.this.findViewById(R.id.mate_set_ota);
                    if (!intent.getExtras().getBoolean(HsConsts.MATE_UPDATE_OTA_RESULT)) {
                        MateDetailsActivity.this.refreshOtaView(0);
                    } else {
                        textView.setText(R.string.Hint_no_update);
                        textView.setTextColor(MateDetailsActivity.this.getResources().getColor(R.color.gray));
                    }
                    MateDetailsActivity mateDetailsActivity = MateDetailsActivity.this;
                    mateDetailsActivity.getMateinfo(mateDetailsActivity.mateDevice.getId());
                    return;
                }
                if (action.equals(HsConsts.MATE_UPDATE_WIFI_STATUS)) {
                    String stringExtra = intent.getStringExtra(HsConsts.MATE_UPDATE_WIFI_STATUS);
                    MateDetailsActivity.this.updateWifiStatus(stringExtra);
                    MateDetailsActivity mateDetailsActivity2 = MateDetailsActivity.this;
                    CommonUtils.addSysMap(mateDetailsActivity2, mateDetailsActivity2.mateDevice.getId(), stringExtra);
                    return;
                }
                if (action.equals(HsConsts.MATE_UPDATE_DOU_STATUS)) {
                    return;
                }
                if (action.equals(HsConsts.MATE_UPDATE_SYNC_STATUS)) {
                    MateDetailsActivity.this.updateSyncStatus(intent.getExtras().getBoolean(HsConsts.MATE_UPDATE_SYNC_STATUS, false));
                    return;
                }
                if (action.equals(HsConsts.MATE_UPDATE_SHARE_STATUS) || !action.equals(BLEConsts.BROADCAST_SCANED_WIFI) || (wifiInfo = (WifiInfo) intent.getSerializableExtra(BLEConsts.EXTRA_WIFI_INFO)) == null || wifiInfo.getAddress() == null) {
                    return;
                }
                MateDetailsActivity.this.updateWifiStatus(wifiInfo.getDisplayName());
                MateDetailsActivity mateDetailsActivity3 = MateDetailsActivity.this;
                CommonUtils.addSysMap(mateDetailsActivity3, mateDetailsActivity3.mateDevice.getId(), wifiInfo.getDisplayName());
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(HsConsts.MATE_UPDATE_OTA_RESULT);
        intentFilter.addAction(HsConsts.MATE_UPDATE_STATUS);
        intentFilter.addAction(HsConsts.MATE_UPDATE_WIFI_STATUS);
        intentFilter.addAction(HsConsts.MATE_UPDATE_SHARE_STATUS);
        intentFilter.addAction(BLEConsts.BROADCAST_SCANED_WIFI);
        LocalBroadcastManager.getInstance(this).registerReceiver(this.mBroadcastReceiver, intentFilter);
    }

    private void initView() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.mate_device_commom);
        Button button = (Button) findViewById(R.id.mate_unbind);
        button.setOnClickListener(this);
        if (this.isSharedDevice) {
            button.setText(R.string.Mate_cancel_share);
            linearLayout.setVisibility(8);
            findViewById(R.id.mate_detail_setting).setVisibility(8);
        } else {
            button.setText(R.string.Device_delete);
            linearLayout.setVisibility(0);
            findViewById(R.id.mate_details_ota).setOnClickListener(this);
            View viewFindViewById = findViewById(R.id.mate_details_wifi);
            viewFindViewById.setOnClickListener(this);
            viewFindViewById.setLongClickable(true);
            viewFindViewById.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.petkit.android.activities.mate.setting.MateDetailsActivity.2
                @Override // android.view.View.OnLongClickListener
                public boolean onLongClick(View view) {
                    Intent intent = new Intent(MateDetailsActivity.this, (Class<?>) MateWifiDeveloperModeActivity.class);
                    intent.putExtra(MateWifiDeveloperModeActivity.MATE_ID, MateDetailsActivity.this.mateDevice.getId());
                    MateDetailsActivity.this.startActivity(intent);
                    return true;
                }
            });
            findViewById(R.id.mate_details_share).setOnClickListener(this);
        }
        updateDisplay(this.mateDevice);
        if (this.mateDevice.getCallInfo() != null) {
            updateStatus(this.mateDevice.getCallInfo().getStatus());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateStatus(int i) {
        TextView textView = (TextView) findViewById(R.id.device_status);
        if (i == 1) {
            textView.setBackgroundResource(R.drawable.mate_offline_detail);
            textView.setText(R.string.Offline);
            return;
        }
        if (i == 2) {
            textView.setBackgroundResource(R.drawable.mate_online_detail);
            textView.setText(R.string.Online);
        } else if (i == 3) {
            textView.setBackgroundResource(R.drawable.mate_busy2_detail);
            textView.setText(R.string.Mate_using);
        } else if (i == 4) {
            textView.setBackgroundResource(R.drawable.mate_busy_detail);
            textView.setText(R.string.Mate_ota);
        } else {
            textView.setBackgroundResource(R.drawable.mate_offline_detail);
            textView.setText(R.string.Offline);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateDisplay(MateDevice mateDevice) {
        String str;
        if (mateDevice != null) {
            TextView textView = (TextView) findViewById(R.id.mate_details_text1);
            if (!this.isSharedDevice || mateDevice.getOwner() == null || isEmpty(mateDevice.getOwner().getNick())) {
                str = "";
            } else {
                str = mateDevice.getOwner().getNick() + "-";
                textView.setSingleLine(false);
            }
            if (!isEmpty(mateDevice.getName())) {
                textView.setText(str + mateDevice.getName());
            } else if (this.isSharedDevice) {
                textView.setText(str + mateDevice.getMac());
            } else if ("0".equals(this.index)) {
                textView.setText(R.string.Mate_default_name);
            } else {
                textView.setText(getString(R.string.Mate_default_name) + ChineseToPinyinResource.Field.LEFT_BRACKET + this.index + ChineseToPinyinResource.Field.RIGHT_BRACKET);
            }
            TextView textView2 = (TextView) findViewById(R.id.mate_details_text3);
            TextView textView3 = (TextView) findViewById(R.id.mate_details_text4);
            TextView textView4 = (TextView) findViewById(R.id.mate_details_text5);
            if (!isEmpty(mateDevice.getCover())) {
                ((BaseApplication) getApplication()).getAppComponent().imageLoader().loadImage(this, GlideImageConfig.builder().url(mateDevice.getCover()).imageView((ImageView) findViewById(R.id.mate_container)).errorPic(R.drawable.mate_detail_bg).build());
            }
            if (!this.isSharedDevice) {
                String sysMap = CommonUtils.getSysMap(this, mateDevice.getId());
                if (!isEmpty(sysMap)) {
                    ((TextView) findViewById(R.id.mate_set_wifi)).setText(sysMap);
                }
                TextView textView5 = (TextView) findViewById(R.id.mate_set_share);
                if (mateDevice.getShareStatus() != null && (mateDevice.getShareStatus().getOpen() | mateDevice.getShareStatus().getFamilyOpen()) == 1) {
                    textView5.setText(R.string.Mate_sharing);
                } else {
                    textView5.setText(R.string.Mate_share_closed);
                }
                findViewById(R.id.mate_nickname).setOnClickListener(this);
                findViewById(R.id.mate_cover).setOnClickListener(this);
                textView2.setVisibility(0);
                textView2.setText(getString(R.string.Mate_device_model, mateDevice.getStyle() + " - " + mateDevice.getSN()));
                textView4.setText(getString(R.string.Mate_wifi_mac_address_format, mateDevice.getMac()));
                if (isEmpty(mateDevice.getVersion())) {
                    return;
                }
                textView3.setText(getString(R.string.Firmware_version) + ":v" + mateDevice.getVersion());
                return;
            }
            textView2.setVisibility(4);
            textView4.setVisibility(4);
            if (this.isFamliy) {
                textView3.setText(R.string.Mate_share_not_limit);
                return;
            }
            textView3.setText(getString(R.string.Mate_friend_device_share_time_format, HsConsts.getWeek(this, mateDevice.getShareStatus().getWeek()), HsConsts.getOpenTime(mateDevice.getShareStatus().getStart()), HsConsts.getOpenTime(mateDevice.getShareStatus().getEnd()), mateDevice.getShareStatus().getLimit() + ""));
        }
    }

    @Override // com.petkit.android.activities.base.SpecialStatusBaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (-1 == i2) {
            MateDevice ownerDeviceByID = HsConsts.getOwnerDeviceByID(this, this.mateDevice.getId());
            this.mateDevice = ownerDeviceByID;
            if (ownerDeviceByID != null) {
                updateDisplay(ownerDeviceByID);
            } else {
                finish();
            }
        }
    }

    @Override // com.petkit.android.activities.base.SpecialStatusBaseActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putSerializable(Constants.EXTRA_HS_DEVICE_DEATILS, this.mateDevice);
        bundle.putBoolean(Constants.EXTRA_BOOLEAN, this.isSharedDevice);
        bundle.putBoolean("famliy", this.isFamliy);
        bundle.putString(Constants.EXTRA_TAG_ID, this.index);
    }

    @Override // com.petkit.android.activities.base.SpecialStatusBaseActivity, android.view.View.OnClickListener
    public void onClick(View view) {
        Bundle bundle = new Bundle();
        Intent intent = new Intent();
        int id = view.getId();
        if (id == R.id.mate_unbind) {
            MateDevice mateDevice = this.mateDevice;
            if (mateDevice != null) {
                showUnbindDeviceDialog(mateDevice.getId());
                return;
            }
            return;
        }
        if (id == R.id.mate_details_ota) {
            bundle.putString(Constants.EXTRA_HS_DEVICE_ID, this.mateDevice.getId());
            intent.setClass(this, MateOTAActivity.class);
            intent.putExtras(bundle);
            startActivityForResult(intent, 0);
            return;
        }
        if (id == R.id.mate_details_wifi) {
            MateDevice mateDevice2 = this.mateDevice;
            if (mateDevice2 != null) {
                bundle.putSerializable(Constants.EXTRA_HS_DEVICE, mateDevice2);
                bundle.putBoolean(Constants.EXTRA_HS_WIFI_MODIFY, true);
                startActivityWithData(MateScanActivity.class, bundle, false);
                HsConsts.mMateSettingAction = 2;
                return;
            }
            return;
        }
        if (id == R.id.mate_details_share) {
            bundle.putSerializable(Constants.EXTRA_HS_DEVICE, this.mateDevice);
            intent.setClass(this, MateShareActivity.class);
            intent.putExtras(bundle);
            startActivityForResult(intent, 2);
            return;
        }
        if (id == R.id.mate_nickname) {
            bundle.putString(Constants.EXTRA_HS_DEVICE_ID, this.mateDevice.getId());
            bundle.putString(Constants.EXTRA_TAG_ID, this.index);
            intent.setClass(this, MateUpdateNickActivity.class);
            intent.putExtras(bundle);
            startActivityForResult(intent, 1);
            return;
        }
        if (id == R.id.mate_cover) {
            setCrop(true);
            showCameraAndAblumMenu();
            return;
        }
        if (id == R.id.menu_1) {
            this.mPopupWindow.dismiss();
            getPhotoFromCamera();
        } else if (id == R.id.menu_2) {
            this.mPopupWindow.dismiss();
            getPhotoFromAlbum();
        } else if (id == R.id.menu_cancel) {
            this.mPopupWindow.dismiss();
        }
    }

    @Override // com.petkit.android.activities.base.SpecialStatusBaseActivity
    public void uploadHeadPic(String str) {
        this.imageFilePath = str;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put(this.imageFilePath, "");
        new UploadImagesUtils(UploadImagesUtils.NS_MATECOVER, linkedHashMap, new UploadImagesUtils.IUploadImagesListener() { // from class: com.petkit.android.activities.mate.setting.MateDetailsActivity.3
            @Override // com.petkit.android.utils.UploadImagesUtils.IUploadImagesListener
            public void onUploadImageSuccess(LinkedHashMap<String, String> linkedHashMap2) {
                String str2 = linkedHashMap2.get(((SpecialStatusBaseActivity) MateDetailsActivity.this).imageFilePath);
                PetkitLog.d("url = " + str2);
                MateDetailsActivity.this.updateAvatar(str2);
            }

            @Override // com.petkit.android.utils.UploadImagesUtils.IUploadImagesListener
            public void onUploadImageFailed() {
                LoadDialog.dismissDialog();
                MateDetailsActivity.this.showShortToast(R.string.Publish_post_failed);
            }
        }, 2).start();
        showLoadDialog();
    }

    @Override // com.petkit.android.activities.base.SpecialStatusBaseActivity
    public void refreshTitleView() {
        setTitleLeftButton(this.specialViewState == 0 ? R.drawable.btn_back_white : R.drawable.btn_back_gray);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateAvatar(final String str) {
        HashMap map = new HashMap();
        map.put("deviceId", this.mateDevice.getId());
        map.put("cover", str);
        WebModelRepository.getInstance().updateMateCover(map, new PetkitCallback<String>() { // from class: com.petkit.android.activities.mate.setting.MateDetailsActivity.4
            @Override // com.petkit.android.api.PetkitCallback
            public void onSuccess(String str2) {
                if (str2 != null) {
                    MateDetailsActivity.this.mateDevice.setCover(str);
                    MateDetailsActivity.this.setImageView();
                    MateDetailsActivity mateDetailsActivity = MateDetailsActivity.this;
                    HsConsts.updateDeviceList(mateDetailsActivity, mateDetailsActivity.mateDevice);
                    Intent intent = new Intent();
                    intent.putExtra(Constants.EXTRA_HS_DEVICE, MateDetailsActivity.this.mateDevice);
                    intent.setAction(Constants.BROADCAST_MSG_MATE_UPDATE_INFO);
                    LocalBroadcastManager.getInstance(MateDetailsActivity.this).sendBroadcast(intent);
                    return;
                }
                MateDetailsActivity.this.dismissLoadDialog();
            }

            @Override // com.petkit.android.api.PetkitCallback
            public void onFailure(ErrorInfor errorInfor) {
                MateDetailsActivity.this.dismissLoadDialog();
                MateDetailsActivity.this.showLongToast(errorInfor.getMsg(), R.drawable.toast_failed);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setImageView() {
        ((BaseApplication) getApplication()).getAppComponent().imageLoader().loadImage(this, GlideImageConfig.builder().url(this.imageFilePath).imageView((ImageView) findViewById(R.id.mate_container)).errorPic(R.drawable.mate_detail_bg).build());
    }

    private void showUnbindDeviceDialog(final String str) {
        new AlertDialog.Builder(this).setCancelable(false).setTitle(R.string.Prompt).setMessage(this.isSharedDevice ? R.string.Mate_confirm_cancel_share_device : R.string.Mate_confirm_unbind_device).setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() { // from class: com.petkit.android.activities.mate.setting.MateDetailsActivity.6
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                if (MateDetailsActivity.this.isSharedDevice) {
                    MateDetailsActivity.this.shareRemoveFromOther(str);
                } else {
                    MateDetailsActivity.this.unbindDevice(str);
                }
            }
        }).setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() { // from class: com.petkit.android.activities.mate.setting.MateDetailsActivity.5
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void unbindDevice(final String str) {
        HashMap map = new HashMap();
        map.put("deviceId", str);
        post(ApiTools.SAMPLE_API_HS_UNBINDDEVICE, map, new AsyncHttpRespHandler(this, true) { // from class: com.petkit.android.activities.mate.setting.MateDetailsActivity.7
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                BaseRsp baseRsp = (BaseRsp) this.gson.fromJson(this.responseResult, BaseRsp.class);
                if (baseRsp.getError() != null) {
                    MateDetailsActivity.this.showLongToast(baseRsp.getError().getMsg(), R.drawable.toast_failed);
                    return;
                }
                HsConsts.deleteOwnerDeviceByID(MateDetailsActivity.this, str);
                MateDetailsActivity.this.updateOtaDevice(str);
                MateDetailsActivity.this.sendEndCallBroadcast();
                MateDetailsActivity.this.sendDeviceUpdateBroadcast();
                MateDetailsActivity.this.setResult(-1);
                MateDetailsActivity.this.finish();
            }
        }, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void shareRemoveFromOther(final String str) {
        HashMap map = new HashMap();
        map.put("deviceId", str);
        post(ApiTools.SAMPLE_API_HS_SHAREREMOVEFROM, map, new AsyncHttpRespHandler(this, true) { // from class: com.petkit.android.activities.mate.setting.MateDetailsActivity.8
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                BaseRsp baseRsp = (BaseRsp) this.gson.fromJson(this.responseResult, BaseRsp.class);
                if (baseRsp != null) {
                    if (baseRsp.getError() != null) {
                        MateDetailsActivity.this.showLongToast(baseRsp.getError().getMsg(), R.drawable.toast_failed);
                        return;
                    }
                    HsConsts.deleteShareDeviceByID(MateDetailsActivity.this, str);
                    MateDetailsActivity.this.sendEndCallBroadcast();
                    MateDetailsActivity.this.sendDeviceUpdateBroadcast();
                    MateDetailsActivity.this.setResult(-1);
                    MateDetailsActivity.this.finish();
                    return;
                }
                MateDetailsActivity.this.showLongToast(MateDetailsActivity.this.getString(R.string.Delete) + MateDetailsActivity.this.getString(R.string.Failure) + "!");
            }
        }, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendEndCallBroadcast() {
        Intent intent = new Intent();
        intent.putExtra(Constants.EXTRA_HS_DEVICE_ID, this.mateDevice.getId());
        intent.setAction(HsConsts.BROADCAST_FINISH_CALL);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendDeviceUpdateBroadcast() {
        Intent intent = new Intent();
        intent.setAction(Constants.BROADCAST_MSG_DEVICE_UPDATE);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private void otaCheck() {
        if (isFinishing()) {
            return;
        }
        HashMap map = new HashMap();
        map.put("deviceId", this.mateDevice.getId());
        post(ApiTools.SAMPLE_API_HS_OTACHECK, map, new AsyncHttpRespHandler(this) { // from class: com.petkit.android.activities.mate.setting.MateDetailsActivity.9
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                OtaCheckRsp otaCheckRsp = (OtaCheckRsp) this.gson.fromJson(this.responseResult, OtaCheckRsp.class);
                if (otaCheckRsp != null) {
                    if (otaCheckRsp.getError() == null) {
                        MateDetailsActivity.this.updateOtaStatus(otaCheckRsp.getResult(), MateDetailsActivity.this.mateDevice);
                    } else {
                        MateDetailsActivity.this.showShortToast(otaCheckRsp.getError().getMsg());
                    }
                }
            }
        }, false);
    }

    private void otaStatus() {
        if (isFinishing()) {
            return;
        }
        HashMap map = new HashMap();
        map.put("deviceId", this.mateDevice.getId());
        post(ApiTools.SAMPLE_API_HS_OTASTATUS, map, new AsyncHttpRespHandler(this) { // from class: com.petkit.android.activities.mate.setting.MateDetailsActivity.10
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                OtaStatusRsp otaStatusRsp = (OtaStatusRsp) this.gson.fromJson(this.responseResult, OtaStatusRsp.class);
                if (otaStatusRsp != null) {
                    if (otaStatusRsp.getError() == null && otaStatusRsp.getResult() != null) {
                        MateDetailsActivity.this.refreshOtaView(otaStatusRsp.getResult().getStatus());
                    } else {
                        MateDetailsActivity.this.showLongToast(otaStatusRsp.getError().getMsg());
                    }
                }
            }
        }, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshOtaView(int i) {
        TextView textView = (TextView) findViewById(R.id.mate_set_ota);
        if (i == 1 || i == 2) {
            textView.setText(R.string.Mate_ota);
            textView.setTextColor(getResources().getColor(R.color.red));
        } else {
            textView.setText(R.string.Hint_new_update);
            textView.setTextColor(getResources().getColor(R.color.red));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateOtaStatus(OtaCheckResult otaCheckResult, MateDevice mateDevice) {
        TextView textView = (TextView) findViewById(R.id.mate_set_ota);
        if (otaCheckResult != null && !isEmpty(otaCheckResult.getVersion())) {
            otaStatus();
            return;
        }
        if (mateDevice.getCallInfo() != null && mateDevice.getCallInfo().getStatus() == 4) {
            textView.setText(R.string.Mate_ota);
            textView.setTextColor(getResources().getColor(R.color.red));
        } else {
            textView.setText(R.string.Hint_no_update);
            textView.setTextColor(getResources().getColor(R.color.gray));
            updateOtaDevice(mateDevice.getId());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateSyncStatus(boolean z) {
        TextView textView = (TextView) findViewById(R.id.mate_set_sync_p);
        if (z) {
            textView.setText(R.string.Mate_share_opened);
        } else {
            textView.setText(R.string.Mate_share_closed);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateWifiStatus(String str) {
        ((TextView) findViewById(R.id.mate_set_wifi)).setText(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getMateinfo(String str) {
        if (isFinishing()) {
            return;
        }
        HashMap map = new HashMap();
        map.put("deviceId", str);
        post(ApiTools.SAMPLE_API_HS_INFO, map, new AsyncHttpRespHandler(this) { // from class: com.petkit.android.activities.mate.setting.MateDetailsActivity.11
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                MateInfoNewRsp mateInfoNewRsp = (MateInfoNewRsp) this.gson.fromJson(this.responseResult, MateInfoNewRsp.class);
                if (mateInfoNewRsp != null) {
                    if (mateInfoNewRsp.getError() != null) {
                        MateDetailsActivity.this.showShortToast(mateInfoNewRsp.getError().getMsg());
                        return;
                    }
                    if (mateInfoNewRsp.getResult() == null || mateInfoNewRsp.getResult().getDevice() == null) {
                        return;
                    }
                    MateDetailsActivity.this.mateDevice = mateInfoNewRsp.getResult().getDevice();
                    MateDetailsActivity mateDetailsActivity = MateDetailsActivity.this;
                    mateDetailsActivity.updateDisplay(mateDetailsActivity.mateDevice);
                    MateDetailsActivity mateDetailsActivity2 = MateDetailsActivity.this;
                    HsConsts.updateDeviceList(mateDetailsActivity2, mateDetailsActivity2.mateDevice);
                    MateDetailsActivity mateDetailsActivity3 = MateDetailsActivity.this;
                    mateDetailsActivity3.updateStatus(mateDetailsActivity3.mateDevice.getCallInfo().getStatus());
                }
            }
        }, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateOtaDevice(String str) {
        ChatCenter chatCenter = ChatUtils.getChatCenter(CommonUtils.getCurrentUserId());
        String updatematedevices = chatCenter.getUpdatematedevices();
        if (isEmpty(updatematedevices)) {
            return;
        }
        if (updatematedevices.contains("&" + str)) {
            chatCenter.setUpdatematedevices(updatematedevices.replace("&" + str, ""));
            SugarRecord.save(chatCenter);
            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(Constants.BROADCAST_MSG_UPDATE_DEVICE_UPDATE_NOTIFICATION));
        }
    }
}

package com.petkit.android.activities.feeder.setting;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.google.gson.Gson;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.widget.PetkitToast;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.petkit.android.activities.base.BaseActivity;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.base.fragment.BaseTopicsListFragment;
import com.petkit.android.activities.common.utils.DeviceCenterUtils;
import com.petkit.android.activities.community.WebviewActivity;
import com.petkit.android.activities.device.DeviceChangePetActivity;
import com.petkit.android.activities.device.DeviceFeedPlansMainActivity;
import com.petkit.android.activities.feeder.FeederNewSettingShareActivity;
import com.petkit.android.activities.feeder.Utils.FeederUtils;
import com.petkit.android.activities.feeder.api.OtaCheckRsp;
import com.petkit.android.activities.feeder.bind.FeederBindStartActivity;
import com.petkit.android.activities.feeder.model.FeederRecord;
import com.petkit.android.activities.home.HomeActivity;
import com.petkit.android.activities.home.mode.DeviceStateUpdateEvent;
import com.petkit.android.activities.petkitBleDevice.d3.FeederConsumablesActivity;
import com.petkit.android.activities.petkitBleDevice.t4.mode.RelatedProductsInfor;
import com.petkit.android.activities.petkitBleDevice.utils.FamilyUtils;
import com.petkit.android.activities.petkitBleDevice.utils.TextUtil;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.AsyncHttpUtil;
import com.petkit.android.api.http.apiResponse.BaseRsp;
import com.petkit.android.api.http.apiResponse.CallInfoRsp;
import com.petkit.android.api.http.apiResponse.ResultIntRsp;
import com.petkit.android.api.http.apiResponse.ResultStringRsp;
import com.petkit.android.model.DeviceRelation;
import com.petkit.android.model.Pet;
import com.petkit.android.utils.CommonUtil;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.JSONUtils;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.android.widget.windows.SettingNameEditWindow;
import com.petkit.oversea.R;
import cz.msebera.android.httpclient.Header;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kotlinx.coroutines.DebugKt;
import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;

/* JADX INFO: loaded from: classes3.dex */
public class FeederSettingActivity extends BaseActivity implements SettingNameEditWindow.SettingEditSureListener {
    private ImageView editDeviceName;
    private ImageView feederSettingIcon;
    private LinearLayout llAdjustView;
    private LinearLayout llSettingOta;
    private LinearLayout llSettingWifi;
    private LinearLayout llShareView;
    private BroadcastReceiver mBroadcastReceiver;
    private FeederRecord mFeederRecord;
    private CheckBox mLightCheckBox;
    private CheckBox mManualLockCheckBox;
    private RelatedProductsInfor relatedProductsInfor;
    private TextView tvFeederDelete;
    private int tvFeederNameMaxWidth;
    private TextView tvFeederRemind;
    private View viewLineAdjust;
    private View viewLineShare;

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$showCancelShareDialog$5(DialogInterface dialogInterface, int i) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$showDeleteDialog$3(DialogInterface dialogInterface, int i) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$showResetDesiccantDialog$1(DialogInterface dialogInterface, int i) {
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        long longExtra;
        super.onCreate(bundle);
        if (bundle != null) {
            longExtra = bundle.getLong(FeederUtils.EXTRA_FEEDER_ID);
            this.relatedProductsInfor = (RelatedProductsInfor) bundle.getSerializable(Constants.EXTRA_RELATED_PRODUCTS_INFOR);
        } else {
            longExtra = getIntent().getLongExtra(FeederUtils.EXTRA_FEEDER_ID, 0L);
            this.relatedProductsInfor = (RelatedProductsInfor) getIntent().getSerializableExtra(Constants.EXTRA_RELATED_PRODUCTS_INFOR);
        }
        FeederRecord feederRecordByDeviceId = FeederUtils.getFeederRecordByDeviceId(longExtra);
        this.mFeederRecord = feederRecordByDeviceId;
        if (feederRecordByDeviceId == null) {
            finish();
            return;
        }
        if (this.relatedProductsInfor == null) {
            getRelatedProductsInfor();
        }
        setContentView(R.layout.activity_feeder_setting);
        initSize();
        otaCheck();
        registerBoradcastReceiver();
    }

    private void initSize() {
        ImageView imageView = (ImageView) findViewById(R.id.feeder_setting_icon);
        this.feederSettingIcon = imageView;
        final int i = BaseApplication.displayMetrics.widthPixels;
        imageView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.petkit.android.activities.feeder.setting.FeederSettingActivity.1
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                FeederSettingActivity.this.feederSettingIcon.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                FeederSettingActivity feederSettingActivity = FeederSettingActivity.this;
                feederSettingActivity.tvFeederNameMaxWidth = (i - ArmsUtils.dip2px(feederSettingActivity, 69.0f)) - FeederSettingActivity.this.feederSettingIcon.getMeasuredWidth();
                if (FeederSettingActivity.this.mFeederRecord != null) {
                    FeederSettingActivity.this.resizeTvWidth();
                }
            }
        });
    }

    public static Intent newIntent(Context context, long j) {
        Intent intent = new Intent(context, (Class<?>) FeederSettingActivity.class);
        intent.putExtra(FeederUtils.EXTRA_FEEDER_ID, j);
        return intent;
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putLong(FeederUtils.EXTRA_FEEDER_ID, this.mFeederRecord.getDeviceId());
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        unregisterBroadcastReceiver();
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        getDeviceDetail();
    }

    @Override // com.petkit.android.activities.base.BaseActivity
    public void setupViews() {
        setTitle(R.string.Settings);
        ((TextView) findViewById(R.id.feeder_share_state)).setText("");
        if (this.mFeederRecord == null) {
            return;
        }
        TextView textView = (TextView) findViewById(R.id.feeder_name);
        if (this.mFeederRecord.getDeviceShared() == null) {
            String name = this.mFeederRecord.getName();
            DeviceRelation relation = this.mFeederRecord.getRelation();
            List<String> petIds = relation != null ? relation.getPetIds() : null;
            if (!TextUtils.isEmpty(name)) {
                textView.setText(name);
            } else if (petIds != null && petIds.size() > 0 && UserInforUtils.getPetById(petIds.get(0)) != null) {
                textView.setText(getString(R.string.Feeder_name_format, UserInforUtils.getPetById(petIds.get(0)).getName()));
            } else {
                textView.setText(getString(R.string.Device_d1_name));
            }
        } else {
            String name2 = this.mFeederRecord.getName();
            List<Pet> pets = this.mFeederRecord.getDeviceShared().getPets();
            if (!TextUtils.isEmpty(name2)) {
                textView.setText(this.mFeederRecord.getDeviceShared().getOwnerNick() + "-" + name2);
            } else if (pets != null && pets.size() > 0 && !TextUtils.isEmpty(pets.get(0).getName())) {
                textView.setText(this.mFeederRecord.getDeviceShared().getOwnerNick() + "-" + getString(R.string.Feeder_name_format, pets.get(0).getName()));
            } else {
                textView.setText(this.mFeederRecord.getDeviceShared().getOwnerNick() + "-" + getString(R.string.Device_d1_name));
            }
        }
        ((TextView) findViewById(R.id.feeder_mac)).setText(getString(R.string.Mac_address_format, this.mFeederRecord.getMac()));
        ((TextView) findViewById(R.id.device_sn)).setText(getString(R.string.Sn_num, this.mFeederRecord.getSn()));
        ((TextView) findViewById(R.id.feeder_desiccant_left_time)).setText(CommonUtil.setSpannableStringIntegerSizeAndColor(getString(R.string.Feeder_desiccant_left_time_format, this.mFeederRecord.getDesiccantLeftDays() + "", getString(this.mFeederRecord.getDesiccantLeftDays() > 1 ? R.string.Unit_days : R.string.Unit_day)), 1.5f, CommonUtils.getColorById(R.color.black)));
        ((TextView) findViewById(R.id.feeder_wifi_name)).setText(this.mFeederRecord.getWifiSsid());
        ((TextView) findViewById(R.id.feeder_version)).setText(getString(R.string.Firmware_version) + ": v" + this.mFeederRecord.getFirmware());
        CheckBox checkBox = (CheckBox) findViewById(R.id.feeder_maunal_checkbox);
        this.mManualLockCheckBox = checkBox;
        checkBox.setChecked(this.mFeederRecord.getManuallock() == 1);
        this.mManualLockCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.petkit.android.activities.feeder.setting.FeederSettingActivity.2
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (z && FeederSettingActivity.this.mFeederRecord.getManuallock() == 1) {
                    return;
                }
                if (z || FeederSettingActivity.this.mFeederRecord.getManuallock() != 0) {
                    FeederSettingActivity.this.mFeederRecord.setManuallock(z ? 1 : 0);
                    FeederSettingActivity.this.updateManualLock();
                }
            }
        });
        CheckBox checkBox2 = (CheckBox) findViewById(R.id.feeder_light_checkbox);
        this.mLightCheckBox = checkBox2;
        checkBox2.setChecked(this.mFeederRecord.getLightMode() != 0);
        this.mLightCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.petkit.android.activities.feeder.setting.FeederSettingActivity.3
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (z && FeederSettingActivity.this.mFeederRecord.getLightMode() == 1) {
                    return;
                }
                if (z || FeederSettingActivity.this.mFeederRecord.getLightMode() != 0) {
                    FeederSettingActivity.this.mFeederRecord.setLightMode(z ? 1 : 0);
                    FeederSettingActivity.this.updateLightMode();
                }
            }
        });
        findViewById(R.id.feeder_plan).setOnClickListener(this);
        findViewById(R.id.feeder_remind).setOnClickListener(this);
        findViewById(R.id.feeder_setting_wifi).setOnClickListener(this);
        findViewById(R.id.feeder_setting_ota).setOnClickListener(this);
        findViewById(R.id.feeder_delete).setOnClickListener(this);
        findViewById(R.id.feeder_desiccant_left_time).setOnClickListener(this);
        findViewById(R.id.feeder_intro).setOnClickListener(this);
        findViewById(R.id.feeder_setting_light_time_view).setOnClickListener(this);
        findViewById(R.id.feeder_adjust_view).setOnClickListener(this);
        findViewById(R.id.feeder_share_view).setOnClickListener(this);
        findViewById(R.id.feeder_setting_pet).setOnClickListener(this);
        findViewById(R.id.device_mac_copy).setOnClickListener(this);
        findViewById(R.id.device_sn_copy).setOnClickListener(this);
        ImageView imageView = (ImageView) findViewById(R.id.feeder_name_edit);
        this.editDeviceName = imageView;
        imageView.setOnClickListener(this);
        refreshView();
    }

    public void refreshView() {
        refreshStateView();
        refreshDesiccant();
        refreshLightTimeView();
        updateManualLockView();
        otaCheck();
        ((TextView) findViewById(R.id.feeder_wifi_name)).setText(this.mFeederRecord.getWifiSsid());
        if (this.mFeederRecord.getDeviceShared() != null) {
            sharedRightLimit();
            return;
        }
        if (FamilyUtils.getInstance().checkIsSharedDevice(this, this.mFeederRecord.getDeviceId(), 4)) {
            this.tvFeederDelete = (TextView) findViewById(R.id.feeder_delete);
            this.llShareView = (LinearLayout) findViewById(R.id.feeder_share_view);
            this.viewLineShare = findViewById(R.id.line_share);
            this.tvFeederDelete.setVisibility(8);
            this.llShareView.setVisibility(8);
            this.viewLineShare.setVisibility(8);
        }
    }

    public void sharedRightLimit() {
        this.llAdjustView = (LinearLayout) findViewById(R.id.feeder_adjust_view);
        this.llShareView = (LinearLayout) findViewById(R.id.feeder_share_view);
        this.tvFeederDelete = (TextView) findViewById(R.id.feeder_delete);
        this.tvFeederRemind = (TextView) findViewById(R.id.feeder_remind);
        this.llSettingWifi = (LinearLayout) findViewById(R.id.feeder_setting_wifi);
        this.llSettingOta = (LinearLayout) findViewById(R.id.feeder_setting_ota);
        this.viewLineAdjust = findViewById(R.id.line_adjust);
        this.viewLineShare = findViewById(R.id.line_share);
        this.viewLineAdjust.setVisibility(8);
        this.viewLineShare.setVisibility(8);
        this.llAdjustView.setVisibility(8);
        this.llShareView.setVisibility(8);
        this.tvFeederRemind.setVisibility(8);
        this.llSettingWifi.setVisibility(8);
        this.llSettingOta.setVisibility(8);
        findViewById(R.id.feeder_setting_light_time_gap).setVisibility(8);
        findViewById(R.id.feeder_remind_gap).setVisibility(8);
        findViewById(R.id.feeder_setting_pet).setVisibility(8);
        this.tvFeederDelete.setText(getString(R.string.Mate_cancel_share));
        this.editDeviceName.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getDeviceDetail() {
        if (isFinishing()) {
            return;
        }
        HashMap map = new HashMap();
        map.put("id", String.valueOf(this.mFeederRecord.getDeviceId()));
        post(ApiTools.SAMPLET_API_FEEDER_DETAIL, map, new AsyncHttpRespHandler(this) { // from class: com.petkit.android.activities.feeder.setting.FeederSettingActivity.4
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                if (((BaseRsp) this.gson.fromJson(this.responseResult, BaseRsp.class)).getError() == null) {
                    try {
                        JSONObject jSONObject = JSONUtils.getJSONObject(this.responseResult).getJSONObject("result");
                        if (jSONObject != null) {
                            FeederUtils.storeFeederRecordFromJson(jSONObject);
                            FeederSettingActivity feederSettingActivity = FeederSettingActivity.this;
                            feederSettingActivity.mFeederRecord = FeederUtils.getFeederRecordByDeviceId(feederSettingActivity.mFeederRecord.getDeviceId());
                            FeederSettingActivity.this.refreshView();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override // com.petkit.android.activities.base.BaseActivity, android.view.View.OnClickListener
    public void onClick(View view) {
        super.onClick(view);
        int id = view.getId();
        if (id == R.id.device_mac_copy) {
            TextUtil.getInstance().copyToClipboard(this, this.mFeederRecord.getMac());
            return;
        }
        if (id == R.id.device_sn_copy) {
            TextUtil.getInstance().copyToClipboard(this, this.mFeederRecord.getSn());
            return;
        }
        if (id == R.id.feeder_plan) {
            startActivity(DeviceFeedPlansMainActivity.newIntent(this, this.mFeederRecord.getDeviceId(), 4, false));
            return;
        }
        if (id == R.id.feeder_remind) {
            Bundle bundle = new Bundle();
            bundle.putLong(FeederUtils.EXTRA_FEEDER_ID, this.mFeederRecord.getDeviceId());
            startActivityWithData(FeederSettingRemindActivity.class, bundle, false);
            return;
        }
        if (id == R.id.feeder_setting_wifi) {
            Bundle bundle2 = new Bundle();
            bundle2.putLong(FeederUtils.EXTRA_FEEDER_ID, this.mFeederRecord.getDeviceId());
            startActivityWithData(FeederBindStartActivity.class, bundle2, false);
            return;
        }
        if (id == R.id.feeder_setting_ota) {
            Bundle bundle3 = new Bundle();
            bundle3.putString(FeederUtils.EXTRA_FEEDER_ID, String.valueOf(this.mFeederRecord.getDeviceId()));
            startActivityWithData(FeederOtaActivity.class, bundle3, false);
            return;
        }
        if (id == R.id.feeder_delete) {
            if (this.mFeederRecord.getDeviceShared() != null) {
                showCancelShareDialog();
                return;
            } else {
                showDeleteDialog();
                return;
            }
        }
        if (id == R.id.feeder_desiccant_left_time) {
            startActivity(FeederConsumablesActivity.newIntent(this, this.mFeederRecord.getDeviceId(), this.relatedProductsInfor, 4));
            return;
        }
        if (id == R.id.feeder_intro) {
            startActivity(WebviewActivity.newIntent(this, "", ApiTools.getWebUrlByKey("freshE_instructions")));
            return;
        }
        if (id == R.id.feeder_setting_light_time_view) {
            Bundle bundle4 = new Bundle();
            bundle4.putLong(FeederUtils.EXTRA_FEEDER_ID, this.mFeederRecord.getDeviceId());
            startActivityWithData(FeederSettingLightTimeActivity.class, bundle4, false);
            return;
        }
        if (id == R.id.feeder_adjust_view) {
            Bundle bundle5 = new Bundle();
            bundle5.putLong(FeederUtils.EXTRA_FEEDER_ID, this.mFeederRecord.getDeviceId());
            startActivityWithData(FeederSettingAdjustActivity.class, bundle5, false);
        } else {
            if (id == R.id.feeder_share_view) {
                startActivity(FeederNewSettingShareActivity.newIntent(this, this.mFeederRecord.getDeviceId()));
                return;
            }
            if (id == R.id.feeder_name_edit) {
                SettingNameEditWindow settingNameEditWindow = new SettingNameEditWindow(this);
                settingNameEditWindow.setOriginalName(this.mFeederRecord.getName());
                settingNameEditWindow.setSettingEditSureListener(this);
                settingNameEditWindow.show(getWindow().getDecorView());
                return;
            }
            if (id == R.id.feeder_setting_pet) {
                startActivity(DeviceChangePetActivity.newIntent(this, this.mFeederRecord.getDeviceId(), 4, false, true));
            }
        }
    }

    private void refreshStateView() {
        FeederUtils.setFeederStateTextView((TextView) findViewById(R.id.feeder_state), this.mFeederRecord);
        TextView textView = (TextView) findViewById(R.id.feeder_update);
        if (this.mFeederRecord.getState() == 4) {
            textView.setText(R.string.Mate_ota);
            textView.setTextColor(CommonUtils.getColorById(R.color.red));
        } else {
            textView.setText(R.string.Hint_no_update);
            textView.setTextColor(CommonUtils.getColorById(R.color.gray));
        }
        ((TextView) findViewById(R.id.feeder_share_state)).setText(this.mFeederRecord.isShareOpen() ? R.string.Mate_sharing : R.string.Mate_share_closed);
    }

    public void resizeTvWidth() {
        if (this.mFeederRecord == null) {
            return;
        }
        final TextView textView = (TextView) findViewById(R.id.feeder_name);
        if (this.mFeederRecord.getDeviceShared() == null) {
            String name = this.mFeederRecord.getName();
            DeviceRelation relation = this.mFeederRecord.getRelation();
            List<String> petIds = relation != null ? relation.getPetIds() : null;
            if (!TextUtils.isEmpty(name)) {
                textView.setText(name);
            } else if (petIds != null && petIds.size() > 0 && UserInforUtils.getPetById(petIds.get(0)) != null) {
                textView.setText(getString(R.string.Feeder_name_format, UserInforUtils.getPetById(petIds.get(0)).getName()));
            } else {
                textView.setText(getString(R.string.Device_d1_name));
            }
            if (this.tvFeederNameMaxWidth > 0) {
                textView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.petkit.android.activities.feeder.setting.FeederSettingActivity.5
                    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                    public void onGlobalLayout() {
                        textView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        int measuredWidth = textView.getMeasuredWidth();
                        int measuredHeight = textView.getMeasuredHeight();
                        ViewGroup.LayoutParams layoutParams = textView.getLayoutParams();
                        if (measuredWidth > FeederSettingActivity.this.tvFeederNameMaxWidth) {
                            layoutParams.width = FeederSettingActivity.this.tvFeederNameMaxWidth;
                            layoutParams.height = measuredHeight;
                            textView.setLayoutParams(layoutParams);
                        } else {
                            layoutParams.width = -2;
                            textView.setLayoutParams(layoutParams);
                            if (measuredWidth == FeederSettingActivity.this.tvFeederNameMaxWidth) {
                                FeederSettingActivity.this.resizeTvWidth();
                            }
                        }
                    }
                });
            }
        }
    }

    private void showResetDesiccantDialog() {
        new AlertDialog.Builder(this).setCancelable(false).setTitle(R.string.Prompt).setMessage(R.string.Hint_reset_desiccant).setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() { // from class: com.petkit.android.activities.feeder.setting.FeederSettingActivity$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.lambda$showResetDesiccantDialog$0(dialogInterface, i);
            }
        }).setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() { // from class: com.petkit.android.activities.feeder.setting.FeederSettingActivity$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                FeederSettingActivity.lambda$showResetDesiccantDialog$1(dialogInterface, i);
            }
        }).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showResetDesiccantDialog$0(DialogInterface dialogInterface, int i) {
        resetDesiccant();
    }

    private void showDeleteDialog() {
        new AlertDialog.Builder(this).setCancelable(false).setTitle(R.string.Prompt).setMessage(R.string.Hint_delete_feeder).setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() { // from class: com.petkit.android.activities.feeder.setting.FeederSettingActivity$$ExternalSyntheticLambda6
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.lambda$showDeleteDialog$2(dialogInterface, i);
            }
        }).setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() { // from class: com.petkit.android.activities.feeder.setting.FeederSettingActivity$$ExternalSyntheticLambda7
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                FeederSettingActivity.lambda$showDeleteDialog$3(dialogInterface, i);
            }
        }).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showDeleteDialog$2(DialogInterface dialogInterface, int i) {
        deleteFeeder();
    }

    private void showCancelShareDialog() {
        new AlertDialog.Builder(this).setCancelable(false).setTitle(R.string.Prompt).setMessage(R.string.Hint_cancel_feeder_share).setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() { // from class: com.petkit.android.activities.feeder.setting.FeederSettingActivity$$ExternalSyntheticLambda4
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.lambda$showCancelShareDialog$4(dialogInterface, i);
            }
        }).setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() { // from class: com.petkit.android.activities.feeder.setting.FeederSettingActivity$$ExternalSyntheticLambda5
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                FeederSettingActivity.lambda$showCancelShareDialog$5(dialogInterface, i);
            }
        }).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showCancelShareDialog$4(DialogInterface dialogInterface, int i) {
        cancelFeederShare();
    }

    private void otaCheck() {
        if (isFinishing() || this.mFeederRecord.getDeviceShared() != null) {
            return;
        }
        HashMap map = new HashMap();
        map.put("deviceId", String.valueOf(this.mFeederRecord.getDeviceId()));
        post(ApiTools.SAMPLET_API_FEEDER_OTA_CHECK, map, new AsyncHttpRespHandler(this) { // from class: com.petkit.android.activities.feeder.setting.FeederSettingActivity.6
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                OtaCheckRsp otaCheckRsp = (OtaCheckRsp) this.gson.fromJson(this.responseResult, OtaCheckRsp.class);
                if (otaCheckRsp != null) {
                    if (otaCheckRsp.getResult() != null) {
                        if (!FeederSettingActivity.this.isEmpty(otaCheckRsp.getResult().getCurrentVersion()) && !otaCheckRsp.getResult().getCurrentVersion().equals(FeederSettingActivity.this.mFeederRecord.getFirmware())) {
                            FeederSettingActivity.this.mFeederRecord.setFirmware(otaCheckRsp.getResult().getCurrentVersion());
                            FeederSettingActivity.this.mFeederRecord.save();
                            ((TextView) FeederSettingActivity.this.findViewById(R.id.feeder_version)).setText(FeederSettingActivity.this.getString(R.string.Firmware_version) + ":v" + FeederSettingActivity.this.mFeederRecord.getFirmware());
                            LocalBroadcastManager.getInstance(FeederSettingActivity.this).sendBroadcast(new Intent(FeederUtils.BROADCAST_FEEDER_UPDATE));
                        }
                        if (FeederSettingActivity.this.mFeederRecord.getState() != 4) {
                            if (!FeederSettingActivity.this.isEmpty(otaCheckRsp.getResult().getVersion())) {
                                DeviceCenterUtils.addFeederOtaDevice(FeederSettingActivity.this.mFeederRecord.getDeviceId());
                                TextView textView = (TextView) FeederSettingActivity.this.findViewById(R.id.feeder_update);
                                textView.setText(R.string.Hint_new_update);
                                textView.setTextColor(CommonUtils.getColorById(R.color.red));
                                return;
                            }
                            DeviceCenterUtils.deleteFeederOtaDevicesFlag(FeederSettingActivity.this.mFeederRecord.getDeviceId());
                            return;
                        }
                        return;
                    }
                    FeederSettingActivity.this.showLongToast(otaCheckRsp.getError().getMsg());
                }
            }
        }, false);
    }

    private void cancelFeederShare() {
        HashMap map = new HashMap();
        map.put("deviceId", String.valueOf(this.mFeederRecord.getDeviceId()));
        post(ApiTools.SAMPLET_API_FEEDER_REMOVE_ME_FROM_SHARE, map, new AsyncHttpRespHandler(this, true) { // from class: com.petkit.android.activities.feeder.setting.FeederSettingActivity.7
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                ResultStringRsp resultStringRsp = (ResultStringRsp) this.gson.fromJson(this.responseResult, ResultStringRsp.class);
                if (resultStringRsp.getError() != null) {
                    FeederSettingActivity.this.showShortToast(resultStringRsp.getError().getMsg());
                    return;
                }
                FeederSettingActivity.this.showShortToast(R.string.Succeed);
                DeviceCenterUtils.deleteFeederOtaDevicesFlag(FeederSettingActivity.this.mFeederRecord.getDeviceId());
                FeederUtils.removeFeederDevice(FeederSettingActivity.this.mFeederRecord.getDeviceId());
                Intent intent = new Intent(FeederUtils.BROADCAST_FEEDER_DELETE);
                intent.putExtra(FeederUtils.EXTRA_FEEDER_ID, FeederSettingActivity.this.mFeederRecord.getDeviceId());
                LocalBroadcastManager.getInstance(FeederSettingActivity.this).sendBroadcast(intent);
                FeederSettingActivity.this.finish();
            }
        }, false);
    }

    private void deleteFeeder() {
        HashMap map = new HashMap();
        map.put("id", String.valueOf(this.mFeederRecord.getDeviceId()));
        post(ApiTools.SAMPLET_API_FEEDER_UNLINK, map, new AsyncHttpRespHandler(this, true) { // from class: com.petkit.android.activities.feeder.setting.FeederSettingActivity.8
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                ResultStringRsp resultStringRsp = (ResultStringRsp) this.gson.fromJson(this.responseResult, ResultStringRsp.class);
                if (resultStringRsp.getError() != null) {
                    FeederSettingActivity.this.showShortToast(resultStringRsp.getError().getMsg());
                    return;
                }
                FeederSettingActivity.this.showShortToast(R.string.Succeed);
                FeederUtils.removeFeederDevice(FeederSettingActivity.this.mFeederRecord.getDeviceId());
                Intent intent = new Intent(FeederUtils.BROADCAST_FEEDER_DELETE);
                intent.putExtra(FeederUtils.EXTRA_FEEDER_ID, FeederSettingActivity.this.mFeederRecord.getDeviceId());
                LocalBroadcastManager.getInstance(FeederSettingActivity.this).sendBroadcast(intent);
                FeederSettingActivity.this.finish();
            }
        }, false);
    }

    public void refreshFeederRecord(final Activity activity, String str) {
        HashMap map = new HashMap();
        map.put("id", str);
        AsyncHttpUtil.post(ApiTools.SAMPLET_API_FEEDER_DEVICE_DETAIL, (Map<String, String>) map, (AsyncHttpResponseHandler) new AsyncHttpRespHandler(activity, true) { // from class: com.petkit.android.activities.feeder.setting.FeederSettingActivity.9
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                BaseRsp baseRsp = (BaseRsp) this.gson.fromJson(this.responseResult, CallInfoRsp.class);
                if (baseRsp != null) {
                    if (baseRsp.getError() != null) {
                        PetkitToast.showShortToast(activity, baseRsp.getError().getMsg(), R.drawable.toast_failed);
                        return;
                    }
                    try {
                        FeederUtils.storeFeederRecordFromJson(JSONUtils.getJSONObject(this.responseResult).getJSONObject("result"));
                        FeederSettingActivity.this.setupViews();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        PetkitToast.showShortToast(activity, R.string.Hint_network_failed);
                    }
                }
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFailure(int i, Header[] headerArr, byte[] bArr, Throwable th) {
                super.onFailure(i, headerArr, bArr, th);
                PetkitToast.showShortToast(activity, R.string.Hint_network_failed);
            }
        }, false);
    }

    private void resetDesiccant() {
        HashMap map = new HashMap();
        map.put("deviceId", String.valueOf(this.mFeederRecord.getDeviceId()));
        post(ApiTools.SAMPLET_API_FEEDER_DESICCANT_RESET, map, new AsyncHttpRespHandler(this, true) { // from class: com.petkit.android.activities.feeder.setting.FeederSettingActivity.10
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                ResultIntRsp resultIntRsp = (ResultIntRsp) this.gson.fromJson(this.responseResult, ResultIntRsp.class);
                if (resultIntRsp.getError() != null) {
                    FeederSettingActivity.this.showShortToast(resultIntRsp.getError().getMsg());
                    return;
                }
                FeederSettingActivity.this.showShortToast(R.string.Succeed);
                FeederSettingActivity.this.mFeederRecord.setDesiccantLeftDays(resultIntRsp.getResult());
                FeederSettingActivity.this.mFeederRecord.save();
                FeederSettingActivity.this.refreshDesiccant();
                Intent intent = new Intent(FeederUtils.BROADCAST_FEEDER_UPDATE);
                intent.putExtra(FeederUtils.EXTRA_FEEDER_ID, FeederSettingActivity.this.mFeederRecord.getDeviceId());
                LocalBroadcastManager.getInstance(FeederSettingActivity.this).sendBroadcast(intent);
            }
        }, false);
    }

    private void showManualLockChangedDialog() {
        new AlertDialog.Builder(this).setCancelable(false).setTitle(R.string.Prompt).setMessage(this.mFeederRecord.getManuallock() == 1 ? R.string.Feeder_setting_manual_open_prompt : R.string.Feeder_setting_manual_close_prompt).setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() { // from class: com.petkit.android.activities.feeder.setting.FeederSettingActivity$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.lambda$showManualLockChangedDialog$6(dialogInterface, i);
            }
        }).setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() { // from class: com.petkit.android.activities.feeder.setting.FeederSettingActivity$$ExternalSyntheticLambda3
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.lambda$showManualLockChangedDialog$7(dialogInterface, i);
            }
        }).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showManualLockChangedDialog$6(DialogInterface dialogInterface, int i) {
        updateManualLock();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showManualLockChangedDialog$7(DialogInterface dialogInterface, int i) {
        FeederRecord feederRecord = this.mFeederRecord;
        feederRecord.setManuallock(feederRecord.getManuallock() == 0 ? 1 : 0);
        updateManualLockView();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateManualLockView() {
        this.mManualLockCheckBox.setChecked(this.mFeederRecord.getManuallock() == 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateManualLock() {
        HashMap map = new HashMap();
        map.put("id", String.valueOf(this.mFeederRecord.getDeviceId()));
        map.put("kv", this.mFeederRecord.getManualLockParamString());
        new HashMap().put("type", this.mFeederRecord.getManuallock() == 0 ? DebugKt.DEBUG_PROPERTY_VALUE_ON : DebugKt.DEBUG_PROPERTY_VALUE_OFF);
        post(ApiTools.SAMPLET_API_FEEDER_UPDATE, map, new AsyncHttpRespHandler(this, true) { // from class: com.petkit.android.activities.feeder.setting.FeederSettingActivity.11
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                BaseRsp baseRsp = (BaseRsp) this.gson.fromJson(this.responseResult, BaseRsp.class);
                if (baseRsp.getError() != null) {
                    FeederSettingActivity.this.mFeederRecord.setManuallock(FeederSettingActivity.this.mFeederRecord.getManuallock() == 0 ? 1 : 0);
                    FeederSettingActivity.this.updateManualLockView();
                    FeederSettingActivity.this.showShortToast(baseRsp.getError().getMsg());
                } else {
                    FeederSettingActivity.this.mFeederRecord.save();
                    FeederSettingActivity.this.updateManualLockView();
                    LocalBroadcastManager.getInstance(FeederSettingActivity.this).sendBroadcast(new Intent(FeederUtils.BROADCAST_FEEDER_UPDATE));
                }
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFailure(int i, Header[] headerArr, byte[] bArr, Throwable th) {
                super.onFailure(i, headerArr, bArr, th);
                FeederSettingActivity.this.mFeederRecord.setManuallock(FeederSettingActivity.this.mFeederRecord.getManuallock() == 0 ? 1 : 0);
                FeederSettingActivity.this.updateManualLockView();
                FeederSettingActivity.this.showShortToast(R.string.Hint_network_failed);
            }
        }, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateLightMode() {
        HashMap map = new HashMap();
        map.put("id", String.valueOf(this.mFeederRecord.getDeviceId()));
        map.put("kv", this.mFeederRecord.getLightModeParamString());
        new HashMap().put("type", this.mFeederRecord.getManuallock() == 0 ? DebugKt.DEBUG_PROPERTY_VALUE_ON : DebugKt.DEBUG_PROPERTY_VALUE_OFF);
        post(ApiTools.SAMPLET_API_FEEDER_UPDATE, map, new AsyncHttpRespHandler(this, true) { // from class: com.petkit.android.activities.feeder.setting.FeederSettingActivity.12
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                BaseRsp baseRsp = (BaseRsp) this.gson.fromJson(this.responseResult, BaseRsp.class);
                if (baseRsp.getError() != null) {
                    FeederSettingActivity.this.mFeederRecord.setLightMode(FeederSettingActivity.this.mFeederRecord.getLightMode() == 0 ? 1 : 0);
                    FeederSettingActivity.this.mLightCheckBox.setChecked(FeederSettingActivity.this.mFeederRecord.getLightMode() != 0);
                    FeederSettingActivity.this.showShortToast(baseRsp.getError().getMsg());
                } else {
                    FeederSettingActivity.this.mFeederRecord.save();
                    FeederSettingActivity.this.refreshLightTimeView();
                    LocalBroadcastManager.getInstance(FeederSettingActivity.this).sendBroadcast(new Intent(FeederUtils.BROADCAST_FEEDER_UPDATE));
                }
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFailure(int i, Header[] headerArr, byte[] bArr, Throwable th) {
                super.onFailure(i, headerArr, bArr, th);
                FeederSettingActivity.this.mFeederRecord.setLightMode(FeederSettingActivity.this.mFeederRecord.getLightMode() == 0 ? 1 : 0);
                FeederSettingActivity.this.mLightCheckBox.setChecked(FeederSettingActivity.this.mFeederRecord.getLightMode() != 0);
                FeederSettingActivity.this.showShortToast(R.string.Hint_network_failed);
            }
        }, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshLightTimeView() {
        this.mLightCheckBox.setChecked(this.mFeederRecord.getLightMode() != 0);
        if (this.mFeederRecord.getLightMode() == 1) {
            findViewById(R.id.feeder_light_time_gap_line).setVisibility(0);
            findViewById(R.id.feeder_setting_light_time_view).setVisibility(0);
            ((TextView) findViewById(R.id.feeder_setting_light_time)).setText(FeederUtils.formatLightTime(this, this.mFeederRecord.getLightTimeStart(), this.mFeederRecord.getLightTimeEnd()));
        } else {
            findViewById(R.id.feeder_light_time_gap_line).setVisibility(8);
            findViewById(R.id.feeder_setting_light_time_view).setVisibility(8);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshDesiccant() {
        ((TextView) findViewById(R.id.feeder_desiccant_left_time)).setText(CommonUtil.setSpannableStringIntegerSizeAndColor(getString(R.string.Feeder_desiccant_left_time_format, this.mFeederRecord.getDesiccantLeftDays() + "", getString(this.mFeederRecord.getDesiccantLeftDays() > 1 ? R.string.Unit_days : R.string.Unit_day)), 1.5f, CommonUtils.getColorById(R.color.black)));
    }

    private void registerBoradcastReceiver() {
        this.mBroadcastReceiver = new BroadcastReceiver() { // from class: com.petkit.android.activities.feeder.setting.FeederSettingActivity.13
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                int i;
                String action = intent.getAction();
                action.hashCode();
                switch (action) {
                    case "BROADCAST_FEEDER_UPDATE":
                        FeederSettingActivity feederSettingActivity = FeederSettingActivity.this;
                        feederSettingActivity.mFeederRecord = FeederUtils.getFeederRecordByDeviceId(feederSettingActivity.mFeederRecord.getDeviceId());
                        FeederSettingActivity.this.refreshView();
                        break;
                    case "BROADCAST_FEEDER_UPDATE_PIM":
                        FeederSettingActivity.this.getDeviceDetail();
                        break;
                    case "BROADCAST_FEEDER_OTA":
                        int intExtra = intent.getIntExtra(Constants.EXTRA_TYPE, 0);
                        TextView textView = (TextView) FeederSettingActivity.this.findViewById(R.id.feeder_update);
                        if (intExtra == 1) {
                            i = R.string.Mate_ota;
                        } else {
                            i = intExtra == 2 ? R.string.Hint_no_update : R.string.Hint_new_update;
                        }
                        textView.setText(i);
                        textView.setTextColor(CommonUtils.getColorById(intExtra == 2 ? R.color.gray : R.color.red));
                        break;
                    case "com.petkit.android.updateDog":
                        FeederSettingActivity feederSettingActivity2 = FeederSettingActivity.this;
                        feederSettingActivity2.mFeederRecord = FeederUtils.getFeederRecordByDeviceId(feederSettingActivity2.mFeederRecord.getDeviceId());
                        break;
                    case "BROADCAST_FEEDER_SHARE_CANCEL":
                        FeederSettingActivity.this.startActivity(HomeActivity.class);
                        break;
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(FeederUtils.BROADCAST_FEEDER_UPDATE_PIM);
        intentFilter.addAction(FeederUtils.BROADCAST_FEEDER_UPDATE);
        intentFilter.addAction(FeederUtils.BROADCAST_FEEDER_OTA);
        intentFilter.addAction(FeederUtils.BROADCAST_FEEDER_SHARE_CANCEL);
        LocalBroadcastManager.getInstance(this).registerReceiver(this.mBroadcastReceiver, intentFilter);
    }

    private void unregisterBroadcastReceiver() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(this.mBroadcastReceiver);
    }

    private void getRelatedProductsInfor() {
        HashMap map = new HashMap();
        map.put("deviceType", "D1");
        map.put(BaseTopicsListFragment.USERID, UserInforUtils.getCurrentUserId(this));
        post(ApiTools.SAMPLE_API_RELATED_PRODUCTS_INFOR, map, new AsyncHttpRespHandler(this, false) { // from class: com.petkit.android.activities.feeder.setting.FeederSettingActivity.14
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                BaseRsp baseRsp = (BaseRsp) this.gson.fromJson(this.responseResult, BaseRsp.class);
                if (baseRsp.getError() != null) {
                    PetkitToast.showShortToast(FeederSettingActivity.this, baseRsp.getError().getMsg(), R.drawable.toast_failed);
                    return;
                }
                try {
                    JSONObject jSONObject = JSONUtils.getJSONObject(JSONUtils.getJSONObject(this.responseResult), "result");
                    FeederSettingActivity.this.relatedProductsInfor = (RelatedProductsInfor) new Gson().fromJson(jSONObject.toString(), RelatedProductsInfor.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, false);
    }

    @Override // com.petkit.android.widget.windows.SettingNameEditWindow.SettingEditSureListener
    public void onSureClick(View view, String str) {
        updateDeviceName(str);
    }

    private void updateDeviceName(final String str) {
        HashMap map = new HashMap();
        map.put("deviceId", String.valueOf(this.mFeederRecord.getDeviceId()));
        map.put("deviceType", String.valueOf(4));
        map.put("name", str);
        post(ApiTools.SAMPLE_API_DEVICE_EDIT_NAME, map, new AsyncHttpRespHandler(this, true) { // from class: com.petkit.android.activities.feeder.setting.FeederSettingActivity.15
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                ResultStringRsp resultStringRsp = (ResultStringRsp) this.gson.fromJson(this.responseResult, ResultStringRsp.class);
                if (resultStringRsp.getError() == null) {
                    FeederSettingActivity.this.mFeederRecord.setName(str);
                    FeederSettingActivity.this.mFeederRecord.save();
                    FeederSettingActivity.this.resizeTvWidth();
                    FeederSettingActivity.this.setupViews();
                    EventBus.getDefault().post(new DeviceStateUpdateEvent(4, FeederSettingActivity.this.mFeederRecord.getDeviceId()));
                    return;
                }
                FeederSettingActivity.this.showShortToast(resultStringRsp.getError().getMsg());
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFailure(int i, Header[] headerArr, byte[] bArr, Throwable th) {
                super.onFailure(i, headerArr, bArr, th);
                FeederSettingActivity.this.showShortToast(th.getMessage());
            }
        }, false);
    }
}

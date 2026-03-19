package com.petkit.android.activities.remind;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.alibaba.sdk.android.oss.common.RequestParameters;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.petkit.android.activities.base.BaseActivity;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.device.mode.DeviceInfos;
import com.petkit.android.activities.device.utils.DeviceUtils;
import com.petkit.android.activities.petkitBleDevice.w5.AssociatedDeviceActivity;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.apiResponse.BaseRsp;
import com.petkit.android.api.http.apiResponse.RemindDetailRsp;
import com.petkit.android.model.Pet;
import com.petkit.android.model.RemindDetail;
import com.petkit.android.model.RemindType;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.ConvertTimeTypeUtil;
import com.petkit.android.utils.DateUtil;
import com.petkit.android.utils.LogcatStorageHelper;
import com.petkit.android.utils.PetUtils;
import com.petkit.android.utils.PetkitLog;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.android.widget.DateTimePickDialogUtil;
import com.petkit.android.widget.SelectContentDialog;
import com.petkit.oversea.R;
import cz.msebera.android.httpclient.Header;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import net.sourceforge.pinyin4j.ChineseToPinyinResource;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

/* JADX INFO: loaded from: classes5.dex */
public class AddRemindActivity extends BaseActivity implements View.OnClickListener {
    private TextView advance_date_tv;
    private TextView delete_remind_tv;
    private long deviceId;
    private int deviceType;
    private EditText etRemindName;
    private Context mContext;
    private RemindDetail mRemindDetail;
    private RemindType mRemindType;
    private String petId;
    private String planTime;
    private TextView plan_time_tv;
    private TextView relevance_pet_tv;
    private String remark;
    private EditText remark_et;
    private String remindName;
    String repeatString;
    private TextView repeat_remind_tv;
    private RelativeLayout rlCompleteRemind;
    private String typeId;
    int advance = 0;
    private int editType = 0;

    public static Intent newIntent(Context context, String str, RemindType remindType) {
        Intent intent = new Intent(context, (Class<?>) AddRemindActivity.class);
        intent.putExtra(Constants.EXTRA_PET_ID, str);
        intent.putExtra(Constants.EXTRA_REMINDTYPE, remindType);
        intent.putExtra(Constants.EXTRA_EDIT_TYPE, 0);
        return intent;
    }

    public static Intent newIntent(Context context, RemindDetail remindDetail, int i) {
        Intent intent = new Intent(context, (Class<?>) AddRemindActivity.class);
        intent.putExtra(Constants.EXTRA_REMINDDETAIL, remindDetail);
        intent.putExtra(Constants.EXTRA_EDIT_TYPE, i);
        return intent;
    }

    public static Intent newIntent(Context context, String str, RemindType remindType, long j, int i) {
        Intent intent = new Intent(context, (Class<?>) AddRemindActivity.class);
        intent.putExtra(Constants.EXTRA_PET_ID, str);
        intent.putExtra(Constants.EXTRA_REMINDTYPE, remindType);
        intent.putExtra(Constants.EXTRA_EDIT_TYPE, 0);
        intent.putExtra(Constants.EXTRA_DEVICE_ID, j);
        intent.putExtra(Constants.EXTRA_DEVICE_TYPE, i);
        return intent;
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mContext = this;
        if (bundle != null) {
            this.petId = bundle.getString(Constants.EXTRA_PET_ID);
            this.mRemindType = (RemindType) bundle.getSerializable(Constants.EXTRA_REMINDTYPE);
            this.mRemindDetail = (RemindDetail) bundle.getSerializable(Constants.EXTRA_REMINDDETAIL);
            this.editType = bundle.getInt(Constants.EXTRA_EDIT_TYPE, 0);
            this.deviceId = bundle.getLong(Constants.EXTRA_DEVICE_ID, 0L);
            this.deviceType = bundle.getInt(Constants.EXTRA_DEVICE_TYPE, 0);
        } else {
            this.mRemindType = (RemindType) getIntent().getSerializableExtra(Constants.EXTRA_REMINDTYPE);
            this.mRemindDetail = (RemindDetail) getIntent().getSerializableExtra(Constants.EXTRA_REMINDDETAIL);
            this.petId = getIntent().getStringExtra(Constants.EXTRA_PET_ID);
            this.editType = getIntent().getIntExtra(Constants.EXTRA_EDIT_TYPE, 0);
            this.deviceType = getIntent().getIntExtra(Constants.EXTRA_DEVICE_TYPE, 0);
            this.deviceId = getIntent().getLongExtra(Constants.EXTRA_DEVICE_ID, 0L);
        }
        ConvertTimeTypeUtil.initDateType(this.mContext);
        setContentView(R.layout.activity_add_remind);
        EventBus.getDefault().register(this);
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putSerializable(Constants.EXTRA_REMINDTYPE, this.mRemindType);
        bundle.putSerializable(Constants.EXTRA_REMINDDETAIL, this.mRemindDetail);
        bundle.putInt(Constants.EXTRA_EDIT_TYPE, this.editType);
    }

    @Override // com.petkit.android.activities.base.BaseActivity
    public void setupViews() {
        int i = this.editType;
        if (i == 0) {
            setTitle(R.string.Add_reminder);
            refreshWithRemindType();
            this.typeId = this.mRemindType.getId();
            setTitleRightButton(getString(R.string.Save), getResources().getColor(R.color.blue), this);
            return;
        }
        if (i == 1) {
            setTitle(R.string.Edit_reminder);
            refreshWithRemindDetail();
            this.typeId = this.mRemindDetail.getType().getId();
            this.deviceId = this.mRemindDetail.getDeviceId();
            this.deviceType = this.mRemindDetail.getDeviceType();
            setTitleRightButton(getString(R.string.Save), getResources().getColor(R.color.blue), this);
            this.delete_remind_tv.setVisibility(0);
            return;
        }
        if (i != 2) {
            if (i != 3) {
                return;
            }
            setTitle(R.string.Reminder_detail);
            refreshWithRemindDetail();
            this.typeId = this.mRemindDetail.getType().getId();
            this.deviceId = this.mRemindDetail.getDeviceId();
            this.deviceType = this.mRemindDetail.getDeviceType();
            this.delete_remind_tv.setVisibility(0);
            return;
        }
        setTitle(R.string.Reminder_detail);
        refreshWithRemindDetail();
        this.typeId = this.mRemindDetail.getType().getId();
        this.deviceId = this.mRemindDetail.getDeviceId();
        this.deviceType = this.mRemindDetail.getDeviceType();
        setTitleRightButton(getString(R.string.Edit), getResources().getColor(R.color.blue), new View.OnClickListener() { // from class: com.petkit.android.activities.remind.AddRemindActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$setupViews$0(view);
            }
        });
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.rl_complete_remind);
        this.rlCompleteRemind = relativeLayout;
        relativeLayout.setVisibility(0);
        this.rlCompleteRemind.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.remind.AddRemindActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$setupViews$1(view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setupViews$0(View view) {
        startActivityForResult(newIntent(this, this.mRemindDetail, 1), 2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setupViews$1(View view) {
        completeRemind();
    }

    private void refreshWithRemindDetail() {
        if ("-1".equals(this.mRemindDetail.getType().getWithDeviceType()) || TextUtils.isEmpty(this.mRemindDetail.getType().getWithDeviceType())) {
            findViewById(R.id.relevance_device_rl).setVisibility(8);
            findViewById(R.id.relevance_device_line).setVisibility(8);
        } else {
            findViewById(R.id.relevance_device_rl).setVisibility(0);
            findViewById(R.id.relevance_device_line).setVisibility(0);
            DeviceInfos deviceInfosFindDeviceInfo = DeviceUtils.findDeviceInfo(this.mRemindDetail.getDeviceId(), this.mRemindDetail.getDeviceType());
            if (deviceInfosFindDeviceInfo != null) {
                ((TextView) findViewById(R.id.relevance_device_tv)).setText(deviceInfosFindDeviceInfo.getName());
            } else {
                ((TextView) findViewById(R.id.relevance_device_tv)).setText(getResources().getString(R.string.Not_associated_device));
            }
        }
        ((BaseApplication) getApplication()).getAppComponent().imageLoader().loadImage(this, GlideImageConfig.builder().url(this.mRemindDetail.getType().getImg()).imageView((ImageView) findViewById(R.id.type_image)).errorPic(R.drawable.default_image_middle).build());
        if (this.mRemindDetail.getType().getId().equals(com.tencent.connect.common.Constants.VIA_SHARE_TYPE_MINI_PROGRAM) && this.editType == 1) {
            EditText editText = (EditText) findViewById(R.id.et_remind_name);
            this.etRemindName = editText;
            editText.setVisibility(0);
            findViewById(R.id.type_name_tv).setVisibility(8);
            this.etRemindName.setText(this.mRemindDetail.getName());
            if (!TextUtils.isEmpty(this.mRemindDetail.getName())) {
                this.etRemindName.setSelection(this.mRemindDetail.getName().length());
            }
        } else {
            TextView textView = (TextView) findViewById(R.id.type_name_tv);
            if (this.mRemindDetail.getType().getId().equals(com.tencent.connect.common.Constants.VIA_SHARE_TYPE_MINI_PROGRAM)) {
                textView.setText(this.mRemindDetail.getName());
            } else {
                textView.setText(this.mRemindDetail.getType().getName());
            }
        }
        this.plan_time_tv = (TextView) findViewById(R.id.plan_time_tv);
        String time = this.mRemindDetail.getTime();
        this.planTime = time;
        if (!TextUtils.isEmpty(time)) {
            this.plan_time_tv.setText(DateUtil.getFormatDateFromString(this.planTime));
            this.plan_time_tv.setTextColor(getResources().getColor(R.color.black));
        }
        this.advance_date_tv = (TextView) findViewById(R.id.advance_date_tv);
        if (this.mRemindDetail.getAlarmBefore() != 0) {
            this.advance_date_tv.setText(this.mRemindDetail.getAlarmBefore() + "");
        } else {
            this.advance_date_tv.setText(R.string.Not_advance);
        }
        this.advance_date_tv.setText(ConvertTimeTypeUtil.convertAdvanceTime(this.mRemindDetail.getAlarmBefore()));
        this.advance = this.mRemindDetail.getAlarmBefore();
        this.repeat_remind_tv = (TextView) findViewById(R.id.repeat_remind_tv);
        if (TextUtils.isEmpty(this.mRemindDetail.getRepeat())) {
            this.repeat_remind_tv.setText(ConvertTimeTypeUtil.repeatList.get(0));
        } else {
            this.repeat_remind_tv.setText(ConvertTimeTypeUtil.convertrepeatTime(this.mRemindDetail.getRepeat()));
            this.repeatString = this.mRemindDetail.getRepeat();
        }
        if (this.mRemindDetail.getType().getWithPet() == 1) {
            findViewById(R.id.relevance_pet_rl).setVisibility(0);
        } else {
            findViewById(R.id.relevance_pet_rl).setVisibility(8);
            findViewById(R.id.relevance_pet_line).setVisibility(8);
        }
        this.relevance_pet_tv = (TextView) findViewById(R.id.relevance_pet_tv);
        if (this.mRemindDetail.getPet() != null && !TextUtils.isEmpty(this.mRemindDetail.getPet().getName())) {
            this.petId = this.mRemindDetail.getPet().getId();
            this.relevance_pet_tv.setText(this.mRemindDetail.getPet().getName());
        } else {
            this.relevance_pet_tv.setText(getString(R.string.Not_assigned_pets));
        }
        this.remark_et = (EditText) findViewById(R.id.remark_et);
        if (!TextUtils.isEmpty(this.mRemindDetail.getNotes())) {
            this.remark_et.setText(this.mRemindDetail.getNotes());
            this.remark_et.setSelection(this.mRemindDetail.getNotes().length());
        }
        TextView textView2 = (TextView) findViewById(R.id.delete_remind_tv);
        this.delete_remind_tv = textView2;
        textView2.setOnClickListener(this);
        int i = this.editType;
        if (i == 2 || i == 3) {
            findViewById(R.id.plan_time_arrow).setVisibility(4);
            findViewById(R.id.relevance_pet_arrow).setVisibility(4);
            findViewById(R.id.relevance_device_arrow).setVisibility(4);
            findViewById(R.id.advance_remind_arrow).setVisibility(4);
            findViewById(R.id.repeat_remind_arrow).setVisibility(4);
            if (TextUtils.isEmpty(this.mRemindDetail.getNotes())) {
                this.remark_et.setHint(R.string.Pet_state_null);
            }
            this.remark_et.setFocusable(false);
            return;
        }
        findViewById(R.id.plan_time_rl).setOnClickListener(this);
        findViewById(R.id.relevance_pet_rl).setOnClickListener(this);
        findViewById(R.id.relevance_device_rl).setOnClickListener(this);
        findViewById(R.id.advance_remind_rl).setOnClickListener(this);
        findViewById(R.id.repeat_remind_rl).setOnClickListener(this);
    }

    private void refreshWithRemindType() {
        ((BaseApplication) getApplication()).getAppComponent().imageLoader().loadImage(this, GlideImageConfig.builder().url(this.mRemindType.getImg()).imageView((ImageView) findViewById(R.id.type_image)).errorPic(R.drawable.default_image_middle).build());
        if (!this.mRemindType.getId().equals(com.tencent.connect.common.Constants.VIA_SHARE_TYPE_MINI_PROGRAM)) {
            ((TextView) findViewById(R.id.type_name_tv)).setText(this.mRemindType.getName());
        } else {
            EditText editText = (EditText) findViewById(R.id.et_remind_name);
            this.etRemindName = editText;
            editText.setVisibility(0);
            findViewById(R.id.type_name_tv).setVisibility(8);
        }
        findViewById(R.id.plan_time_rl).setOnClickListener(this);
        this.plan_time_tv = (TextView) findViewById(R.id.plan_time_tv);
        findViewById(R.id.advance_remind_rl).setOnClickListener(this);
        TextView textView = (TextView) findViewById(R.id.advance_date_tv);
        this.advance_date_tv = textView;
        textView.setText(R.string.Not_advance);
        findViewById(R.id.repeat_remind_rl).setOnClickListener(this);
        TextView textView2 = (TextView) findViewById(R.id.repeat_remind_tv);
        this.repeat_remind_tv = textView2;
        textView2.setText(R.string.None);
        findViewById(R.id.relevance_device_rl).setOnClickListener(this);
        if (this.mRemindType.getWithPet() == 1) {
            findViewById(R.id.relevance_pet_rl).setVisibility(0);
        } else {
            findViewById(R.id.relevance_pet_rl).setVisibility(8);
            findViewById(R.id.relevance_pet_line).setVisibility(8);
        }
        try {
            if (!this.mRemindType.getWithDeviceType().contains(ChineseToPinyinResource.Field.COMMA) && Integer.parseInt(this.mRemindType.getWithDeviceType()) == -1) {
                findViewById(R.id.relevance_device_rl).setVisibility(8);
                findViewById(R.id.relevance_device_line).setVisibility(8);
            } else {
                findViewById(R.id.relevance_device_rl).setVisibility(0);
                long j = this.deviceId;
                if (j != 0 && DeviceUtils.findDeviceInfo(j, this.deviceType) != null) {
                    ((TextView) findViewById(R.id.relevance_device_tv)).setText(DeviceUtils.findDeviceInfo(this.deviceId, this.deviceType).getName());
                } else {
                    ((TextView) findViewById(R.id.relevance_device_tv)).setText(getResources().getString(R.string.Not_associated_device));
                }
            }
        } catch (Exception e) {
            PetkitLog.d(getClass().getSimpleName() + ":" + e.getMessage());
            LogcatStorageHelper.addLog(getClass().getSimpleName() + ":" + e.getMessage());
            findViewById(R.id.relevance_device_rl).setVisibility(8);
            findViewById(R.id.relevance_device_line).setVisibility(8);
        }
        findViewById(R.id.relevance_pet_rl).setOnClickListener(this);
        findViewById(R.id.relevance_device_rl).setOnClickListener(this);
        this.relevance_pet_tv = (TextView) findViewById(R.id.relevance_pet_tv);
        Pet petById = UserInforUtils.getPetById(this.petId);
        if (petById == null) {
            this.relevance_pet_tv.setText(getString(R.string.Not_assigned_pets));
        } else {
            this.relevance_pet_tv.setText(petById.getName());
        }
        this.remark_et = (EditText) findViewById(R.id.remark_et);
        findViewById(R.id.delete_remind_tv).setVisibility(8);
        if (this.mRemindType.getWithDeviceType().contains(com.tencent.connect.common.Constants.VIA_REPORT_TYPE_MAKE_FRIEND)) {
            this.repeat_remind_tv.setText(5 + getResources().getString(R.string.Unit_days));
            this.repeatString = ConvertTimeTypeUtil.repeatStrs[5];
            this.repeat_remind_tv.setTextColor(CommonUtils.getColorById(R.color.blue));
        }
    }

    @Override // com.petkit.android.activities.base.BaseActivity, android.view.View.OnClickListener
    public void onClick(View view) {
        Calendar calendar;
        if (view.getId() == R.id.title_right_btn) {
            if (TextUtils.isEmpty(this.planTime)) {
                showShortToast(this.mContext.getResources().getString(R.string.Hint_select_schedule_time));
                return;
            } else {
                this.remark = this.remark_et.getText().toString();
                saveRemind();
                return;
            }
        }
        if (view.getId() == R.id.plan_time_rl) {
            if (this.editType != 0) {
                calendar = null;
                try {
                    if (DateUtil.parseISO8601Date(this.mRemindDetail.getTime()).getTime() < new Date().getTime()) {
                        calendar = Calendar.getInstance();
                        calendar.setTime(new Date());
                    } else {
                        calendar = DateUtil.convertToCanlendar(this.planTime);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                calendar = Calendar.getInstance();
                calendar.setTime(new Date());
            }
            DateTimePickDialogUtil dateTimePickDialogUtil = new DateTimePickDialogUtil(this, calendar, new DateTimePickDialogUtil.OnDateTimePickChange() { // from class: com.petkit.android.activities.remind.AddRemindActivity$$ExternalSyntheticLambda2
                @Override // com.petkit.android.widget.DateTimePickDialogUtil.OnDateTimePickChange
                public final void onChange(boolean z, String str) {
                    this.f$0.lambda$onClick$2(z, str);
                }
            });
            dateTimePickDialogUtil.setNegativeColor(Integer.valueOf(R.color.blue));
            dateTimePickDialogUtil.setPositiveColor(Integer.valueOf(R.color.blue));
            dateTimePickDialogUtil.showDateTimePicKDialog();
            return;
        }
        if (view.getId() == R.id.advance_remind_rl) {
            new SelectContentDialog(this, "", ConvertTimeTypeUtil.advanceList, new SelectContentDialog.OnSureOnClickListener() { // from class: com.petkit.android.activities.remind.AddRemindActivity$$ExternalSyntheticLambda3
                @Override // com.petkit.android.widget.SelectContentDialog.OnSureOnClickListener
                public final void onSureListener(String str, int i) {
                    this.f$0.lambda$onClick$3(str, i);
                }
            }).show();
            return;
        }
        if (view.getId() == R.id.repeat_remind_rl) {
            new SelectContentDialog(this, "", ConvertTimeTypeUtil.repeatList, new SelectContentDialog.OnSureOnClickListener() { // from class: com.petkit.android.activities.remind.AddRemindActivity$$ExternalSyntheticLambda4
                @Override // com.petkit.android.widget.SelectContentDialog.OnSureOnClickListener
                public final void onSureListener(String str, int i) {
                    this.f$0.lambda$onClick$4(str, i);
                }
            }).show();
            return;
        }
        if (view.getId() == R.id.relevance_pet_rl) {
            startActivityForResult(SelectPetActivity.class, 1);
            return;
        }
        if (view.getId() == R.id.relevance_device_rl) {
            RemindType type = this.mRemindType;
            if (type == null) {
                type = this.mRemindDetail.getType();
            }
            startActivityForResult(AssociatedDeviceActivity.newIntent(this, type.getWithDeviceType()), 3);
            return;
        }
        if (view.getId() == R.id.delete_remind_tv) {
            deleteRemind();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$2(boolean z, String str) {
        if (z) {
            this.planTime = str;
            this.plan_time_tv.setText(DateUtil.getFormatDateFromString(str));
            this.plan_time_tv.setTextColor(getResources().getColor(R.color.black));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$3(String str, int i) {
        this.advance_date_tv.setText(str);
        this.advance = ConvertTimeTypeUtil.advances[i];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$4(String str, int i) {
        this.repeat_remind_tv.setText(str);
        this.repeatString = ConvertTimeTypeUtil.repeatStrs[i];
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == -1) {
            if (i == 1) {
                Pet pet = (Pet) intent.getSerializableExtra("pet");
                if (pet == null) {
                    this.relevance_pet_tv.setText(R.string.Not_assigned_pets);
                    this.petId = "";
                    return;
                } else {
                    this.relevance_pet_tv.setText(pet.getName());
                    this.petId = pet.getId();
                    return;
                }
            }
            if (i == 2) {
                if (intent != null) {
                    if (intent.getBooleanExtra(RequestParameters.SUBRESOURCE_DELETE, false)) {
                        finish();
                        return;
                    }
                    return;
                }
                getRemindDetail(this.mRemindDetail.getId());
                return;
            }
            if (i != 3 || intent == null) {
                return;
            }
            long longExtra = intent.getLongExtra(Constants.EXTRA_DEVICE_ID, 0L);
            this.deviceId = longExtra;
            if (longExtra != 0) {
                this.deviceType = intent.getIntExtra(Constants.EXTRA_DEVICE_TYPE, 0);
                ((TextView) findViewById(R.id.relevance_device_tv)).setText(intent.getStringExtra(Constants.EXTRA_STRING));
                return;
            }
            ((TextView) findViewById(R.id.relevance_device_tv)).setText(getResources().getString(R.string.Not_associated_device));
        }
    }

    private void saveRemind() {
        HashMap map = new HashMap();
        if (this.typeId.equals(com.tencent.connect.common.Constants.VIA_SHARE_TYPE_MINI_PROGRAM)) {
            if (!TextUtils.isEmpty(this.etRemindName.getText())) {
                map.put("name", this.etRemindName.getText().toString());
            } else {
                showShortToast(R.string.Please_add_remind_name);
                return;
            }
        }
        if (this.editType != 0) {
            map.put("id", this.mRemindDetail.getId());
        }
        map.put("time", this.planTime);
        if (!TextUtils.isEmpty(this.repeatString)) {
            map.put("repeat", this.repeatString);
        }
        if (this.advance != 0) {
            map.put("alarmBefore", this.advance + "");
        }
        map.put("typeId", this.typeId);
        if (!TextUtils.isEmpty(this.petId) && !this.petId.equals(PetUtils.ALL_DEVICE)) {
            map.put("petId", this.petId);
        }
        if (!TextUtils.isEmpty(this.remark)) {
            map.put("notes", this.remark);
        }
        long j = this.deviceId;
        if (j != 0) {
            map.put("deviceId", String.valueOf(j));
            map.put("deviceType", String.valueOf(this.deviceType));
        }
        post(ApiTools.SAMPLE_API_SCHEDULE_SAVE, map, new AsyncHttpRespHandler(this, true) { // from class: com.petkit.android.activities.remind.AddRemindActivity.1
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                BaseRsp baseRsp = (BaseRsp) this.gson.fromJson(this.responseResult, BaseRsp.class);
                if (baseRsp.getError() != null) {
                    AddRemindActivity.this.showLongToast(baseRsp.getError().getMsg(), R.drawable.toast_failed);
                    return;
                }
                AddRemindActivity.this.setResult(-1);
                EventBus.getDefault().post(new RemindUpdatedEvent(false));
                AddRemindActivity.this.finish();
            }
        }, false);
    }

    private void completeRemind() {
        HashMap map = new HashMap();
        map.put("id", this.mRemindDetail.getId());
        map.put("time", DateUtil.formatISO8601DateWithMills(new Date()));
        post(ApiTools.SAMPLE_API_SCHEDULE_COMPLETE, map, new AsyncHttpRespHandler(this, true) { // from class: com.petkit.android.activities.remind.AddRemindActivity.2
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                BaseRsp baseRsp = (BaseRsp) this.gson.fromJson(this.responseResult, BaseRsp.class);
                if (baseRsp.getError() != null) {
                    AddRemindActivity.this.showLongToast(baseRsp.getError().getMsg(), R.drawable.toast_failed);
                    return;
                }
                AddRemindActivity.this.setResult(-1);
                EventBus.getDefault().post(new RemindUpdatedEvent(false));
                AddRemindActivity.this.checkSchedule();
                AddRemindActivity.this.finish();
            }
        }, false);
    }

    private void getRemindDetail(String str) {
        HashMap map = new HashMap();
        map.put("id", str);
        post(ApiTools.SAMPLE_API_SCHEDULE_GET, map, new AsyncHttpRespHandler(this, true) { // from class: com.petkit.android.activities.remind.AddRemindActivity.3
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFailure(int i, Header[] headerArr, byte[] bArr, Throwable th) {
                super.onFailure(i, headerArr, bArr, th);
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFinish() {
                super.onFinish();
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                RemindDetailRsp remindDetailRsp = (RemindDetailRsp) this.gson.fromJson(this.responseResult, RemindDetailRsp.class);
                if (remindDetailRsp.getError() != null) {
                    if (remindDetailRsp.getError().getCode() == 1101) {
                        AddRemindActivity.this.showLongToast(remindDetailRsp.getError().getMsg());
                        return;
                    } else {
                        AddRemindActivity.this.showLongToast(remindDetailRsp.getError().getMsg(), R.drawable.toast_failed);
                        return;
                    }
                }
                if (remindDetailRsp.getResult() != null) {
                    AddRemindActivity.this.mRemindDetail = remindDetailRsp.getResult();
                    AddRemindActivity.this.setupViews();
                }
            }
        }, false);
    }

    private void deleteRemind() {
        HashMap map = new HashMap();
        map.put("id", this.mRemindDetail.getId());
        post(ApiTools.SAMPLE_API_SCHEDULE_REMOVE, map, new AsyncHttpRespHandler(this, true) { // from class: com.petkit.android.activities.remind.AddRemindActivity.4
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                BaseRsp baseRsp = (BaseRsp) this.gson.fromJson(this.responseResult, BaseRsp.class);
                if (baseRsp.getError() != null) {
                    AddRemindActivity.this.showLongToast(baseRsp.getError().getMsg(), R.drawable.toast_failed);
                } else {
                    EventBus.getDefault().post(new RemindUpdatedEvent(true));
                    AddRemindActivity.this.finish();
                }
            }
        }, false);
    }

    @Subscriber(mode = ThreadMode.MAIN)
    public void onRemindDeleted(RemindUpdatedEvent remindUpdatedEvent) {
        if (remindUpdatedEvent.isDeleted()) {
            finish();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:9:0x002e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void checkSchedule() {
        /*
            r8 = this;
            java.lang.String r0 = "system.api.schedule"
            com.petkit.android.model.RemindDetail r1 = r8.mRemindDetail
            java.lang.String r1 = r1.getTime()
            int r1 = com.petkit.android.utils.DateUtil.getOffsetDaysToTodayFromString(r1)
            r2 = 1
            if (r1 != 0) goto L2e
            com.petkit.android.model.RemindDetail r3 = r8.mRemindDetail     // Catch: java.lang.Exception -> L2c
            java.lang.String r3 = r3.getTime()     // Catch: java.lang.Exception -> L2c
            java.util.Date r3 = com.petkit.android.utils.DateUtil.parseISO8601Date(r3)     // Catch: java.lang.Exception -> L2c
            long r3 = r3.getTime()     // Catch: java.lang.Exception -> L2c
            java.util.Date r5 = new java.util.Date     // Catch: java.lang.Exception -> L2c
            r5.<init>()     // Catch: java.lang.Exception -> L2c
            long r5 = r5.getTime()     // Catch: java.lang.Exception -> L2c
            int r7 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r7 >= 0) goto L2e
            r3 = 1
            goto L2f
        L2c:
            r0 = move-exception
            goto L4c
        L2e:
            r3 = 0
        L2f:
            if (r1 < 0) goto L33
            if (r3 == 0) goto L4f
        L33:
            int r1 = com.petkit.android.utils.CommonUtils.getUnReadMsgCount(r0)     // Catch: java.lang.Exception -> L2c
            if (r1 <= 0) goto L4f
            int r1 = r1 - r2
            com.petkit.android.utils.CommonUtils.saveUnReadMsgCount(r0, r1)     // Catch: java.lang.Exception -> L2c
            android.content.Intent r0 = new android.content.Intent     // Catch: java.lang.Exception -> L2c
            java.lang.String r1 = "com.petkit.android.updateMsg"
            r0.<init>(r1)     // Catch: java.lang.Exception -> L2c
            androidx.localbroadcastmanager.content.LocalBroadcastManager r1 = androidx.localbroadcastmanager.content.LocalBroadcastManager.getInstance(r8)     // Catch: java.lang.Exception -> L2c
            r1.sendBroadcast(r0)     // Catch: java.lang.Exception -> L2c
            goto L4f
        L4c:
            r0.printStackTrace()
        L4f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.petkit.android.activities.remind.AddRemindActivity.checkSchedule():void");
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}

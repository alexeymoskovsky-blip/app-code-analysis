package com.petkit.android.activities.remind;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import com.petkit.android.activities.family.FamilySelectDeviceActivity;
import com.petkit.android.activities.family.FamilySelectPetActivity;
import com.petkit.android.activities.family.mode.FamilyInfor;
import com.petkit.android.activities.petkitBleDevice.ctw3.CTW3HomeActivity;
import com.petkit.android.activities.petkitBleDevice.ctw3.mode.CTW3BindCompleteEvent;
import com.petkit.android.activities.petkitBleDevice.hg.mode.HgKillActivity;
import com.petkit.android.activities.petkitBleDevice.utils.FamilyUtils;
import com.petkit.android.activities.petkitBleDevice.widget.frame.TipWindow;
import com.petkit.android.activities.remind.utils.ReminderUtils;
import com.petkit.android.activities.remind.widget.RemindRepeatWindow;
import com.petkit.android.activities.remind.widget.RepeatReminderWindow;
import com.petkit.android.activities.universalWindow.BaseBottomWindow;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.apiResponse.BaseRsp;
import com.petkit.android.api.http.apiResponse.RemindDetailRsp;
import com.petkit.android.model.Pet;
import com.petkit.android.model.RemindDetail;
import com.petkit.android.model.RemindType;
import com.petkit.android.utils.CommonUtil;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.DateUtil;
import com.petkit.android.utils.LogcatStorageHelper;
import com.petkit.android.utils.PetUtils;
import com.petkit.android.utils.PetkitLog;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.android.widget.DateTimePickDialogUtil;
import com.petkit.oversea.R;
import cz.msebera.android.httpclient.Header;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import net.sourceforge.pinyin4j.ChineseToPinyinResource;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

/* JADX INFO: loaded from: classes5.dex */
public class AddRemindActivity2_0 extends BaseActivity implements View.OnClickListener, BaseBottomWindow.OnClickListener, RemindRepeatWindow.RemindRepeatListener {
    private TextView bottomBtn;
    private long deviceId;
    private int deviceType;
    private boolean isBind;
    private Context mContext;
    private RemindDetail mRemindDetail;
    private RemindType mRemindType;
    private String petId;
    private String planTime;
    private TextView plan_time_tv;
    private TextView relevance_pet_tv;
    private String remark;
    private EditText remark_et;
    private TipWindow remindDeleteWindow;
    private String remindName;
    private RemindRepeatWindow remindRepeatWindow;
    String repeatString;
    private String repeatText;
    private TextView repeat_remind_tv;
    private RelativeLayout rlCompleteRemind;
    private String typeId;
    int advance = 0;
    private int editType = 0;
    private boolean modifyDeviceEnable = true;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @Override // com.petkit.android.activities.universalWindow.BaseBottomWindow.OnClickListener
    public void onTitleBtn() {
    }

    @Override // com.petkit.android.activities.universalWindow.BaseBottomWindow.OnClickListener
    public void onTopLeftBtn() {
    }

    @Override // com.petkit.android.activities.universalWindow.BaseBottomWindow.OnClickListener
    public void onTopRightBtn() {
    }

    public static Intent newIntent(Context context, String str, RemindType remindType) {
        Intent intent = new Intent(context, (Class<?>) AddRemindActivity2_0.class);
        intent.putExtra(Constants.EXTRA_PET_ID, str);
        intent.putExtra(Constants.EXTRA_REMINDTYPE, remindType);
        intent.putExtra(Constants.EXTRA_EDIT_TYPE, 0);
        return intent;
    }

    public static Intent newIntent(Context context, String str, RemindType remindType, String str2, String str3, int i, long j) {
        Intent intent = new Intent(context, (Class<?>) AddRemindActivity2_0.class);
        intent.putExtra(Constants.EXTRA_PET_ID, str);
        intent.putExtra(Constants.EXTRA_REMINDTYPE, remindType);
        intent.putExtra(Constants.EXTRA_EDIT_TYPE, 0);
        intent.putExtra(Constants.EXTRA_REMINDER_REPEAT, str2);
        intent.putExtra(Constants.EXTRA_REMINDER_REPEAT_TEXT, str3);
        intent.putExtra(Constants.EXTRA_DEVICE_TYPE, i);
        intent.putExtra(Constants.EXTRA_DEVICE_ID, j);
        return intent;
    }

    public static Intent newIntent(Context context, String str, RemindType remindType, String str2, String str3, int i, long j, boolean z) {
        Intent intent = new Intent(context, (Class<?>) AddRemindActivity2_0.class);
        intent.putExtra(Constants.EXTRA_PET_ID, str);
        intent.putExtra(Constants.EXTRA_REMINDTYPE, remindType);
        intent.putExtra(Constants.EXTRA_EDIT_TYPE, 0);
        intent.putExtra(Constants.EXTRA_REMINDER_REPEAT, str2);
        intent.putExtra(Constants.EXTRA_REMINDER_REPEAT_TEXT, str3);
        intent.putExtra(Constants.EXTRA_DEVICE_TYPE, i);
        intent.putExtra(Constants.EXTRA_DEVICE_ID, j);
        intent.putExtra(Constants.EXTRA_DEVICE_IS_BIND, z);
        return intent;
    }

    public static Intent newIntent(Context context, RemindDetail remindDetail, int i) {
        Intent intent = new Intent(context, (Class<?>) AddRemindActivity2_0.class);
        intent.putExtra(Constants.EXTRA_REMINDDETAIL, remindDetail);
        intent.putExtra(Constants.EXTRA_EDIT_TYPE, i);
        return intent;
    }

    public static Intent newIntent(Context context, RemindDetail remindDetail, String str, String str2, int i) {
        Intent intent = new Intent(context, (Class<?>) AddRemindActivity2_0.class);
        intent.putExtra(Constants.EXTRA_REMINDDETAIL, remindDetail);
        intent.putExtra(Constants.EXTRA_EDIT_TYPE, i);
        intent.putExtra(Constants.EXTRA_REMINDER_REPEAT, str);
        intent.putExtra(Constants.EXTRA_REMINDER_REPEAT_TEXT, str2);
        return intent;
    }

    public static Intent newIntent(Context context, String str, RemindType remindType, long j, int i) {
        Intent intent = new Intent(context, (Class<?>) AddRemindActivity2_0.class);
        intent.putExtra(Constants.EXTRA_PET_ID, str);
        intent.putExtra(Constants.EXTRA_REMINDTYPE, remindType);
        intent.putExtra(Constants.EXTRA_EDIT_TYPE, 0);
        intent.putExtra(Constants.EXTRA_DEVICE_ID, j);
        intent.putExtra(Constants.EXTRA_DEVICE_TYPE, i);
        return intent;
    }

    public static Intent newIntent(Context context, RemindDetail remindDetail, String str, String str2, int i, long j, int i2) {
        Intent intent = new Intent(context, (Class<?>) AddRemindActivity2_0.class);
        intent.putExtra(Constants.EXTRA_REMINDDETAIL, remindDetail);
        intent.putExtra(Constants.EXTRA_EDIT_TYPE, i);
        intent.putExtra(Constants.EXTRA_REMINDER_REPEAT, str);
        intent.putExtra(Constants.EXTRA_REMINDER_REPEAT_TEXT, str2);
        intent.putExtra(Constants.EXTRA_DEVICE_ID, j);
        intent.putExtra(Constants.EXTRA_DEVICE_TYPE, i2);
        return intent;
    }

    public static Intent newIntent(Context context, RemindDetail remindDetail, String str, String str2, int i, long j, int i2, boolean z) {
        Intent intent = new Intent(context, (Class<?>) AddRemindActivity2_0.class);
        intent.putExtra(Constants.EXTRA_REMINDDETAIL, remindDetail);
        intent.putExtra(Constants.EXTRA_EDIT_TYPE, i);
        intent.putExtra(Constants.EXTRA_REMINDER_REPEAT, str);
        intent.putExtra(Constants.EXTRA_REMINDER_REPEAT_TEXT, str2);
        intent.putExtra(Constants.EXTRA_DEVICE_ID, j);
        intent.putExtra(Constants.EXTRA_DEVICE_TYPE, i2);
        intent.putExtra(Constants.EXTRA_DEVICE_IS_BIND, z);
        return intent;
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mContext = this;
        boolean z = false;
        if (bundle != null) {
            this.petId = bundle.getString(Constants.EXTRA_PET_ID);
            this.mRemindType = (RemindType) bundle.getSerializable(Constants.EXTRA_REMINDTYPE);
            this.mRemindDetail = (RemindDetail) bundle.getSerializable(Constants.EXTRA_REMINDDETAIL);
            this.editType = bundle.getInt(Constants.EXTRA_EDIT_TYPE, 0);
            this.deviceId = bundle.getLong(Constants.EXTRA_DEVICE_ID, 0L);
            this.deviceType = bundle.getInt(Constants.EXTRA_DEVICE_TYPE, 0);
            this.repeatString = bundle.getString(Constants.EXTRA_REMINDER_REPEAT);
            this.repeatText = bundle.getString(Constants.EXTRA_REMINDER_REPEAT_TEXT);
            this.isBind = bundle.getBoolean(Constants.EXTRA_DEVICE_IS_BIND);
        } else {
            this.mRemindType = (RemindType) getIntent().getSerializableExtra(Constants.EXTRA_REMINDTYPE);
            this.mRemindDetail = (RemindDetail) getIntent().getSerializableExtra(Constants.EXTRA_REMINDDETAIL);
            this.petId = getIntent().getStringExtra(Constants.EXTRA_PET_ID);
            this.editType = getIntent().getIntExtra(Constants.EXTRA_EDIT_TYPE, 0);
            this.deviceType = getIntent().getIntExtra(Constants.EXTRA_DEVICE_TYPE, 0);
            this.deviceId = getIntent().getLongExtra(Constants.EXTRA_DEVICE_ID, 0L);
            this.repeatString = getIntent().getStringExtra(Constants.EXTRA_REMINDER_REPEAT);
            this.repeatText = getIntent().getStringExtra(Constants.EXTRA_REMINDER_REPEAT_TEXT);
            this.isBind = getIntent().getBooleanExtra(Constants.EXTRA_DEVICE_IS_BIND, false);
        }
        if (TextUtils.isEmpty(this.repeatString) && TextUtils.isEmpty(this.repeatText)) {
            z = true;
        }
        this.modifyDeviceEnable = z;
        RemindDetail remindDetail = this.mRemindDetail;
        if (remindDetail != null && this.mRemindType == null) {
            this.mRemindType = remindDetail.getType();
        }
        setContentView(R.layout.activity_add_remind2_0);
        setTitleLineVisibility(8);
        EventBus.getDefault().register(this);
        if (("2" + getString(R.string.Unit_weeks)).equals(this.repeatText)) {
            this.repeatString = "2w";
        }
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putSerializable(Constants.EXTRA_REMINDTYPE, this.mRemindType);
        bundle.putSerializable(Constants.EXTRA_REMINDDETAIL, this.mRemindDetail);
        bundle.putInt(Constants.EXTRA_EDIT_TYPE, this.editType);
        bundle.putString(Constants.EXTRA_REMINDER_REPEAT, this.repeatString);
        bundle.putString(Constants.EXTRA_REMINDER_REPEAT_TEXT, this.repeatText);
    }

    @Override // com.petkit.android.activities.base.BaseActivity
    public void setupViews() {
        RemindDetail remindDetail;
        int i = this.editType;
        if (i == 0) {
            setTitle(R.string.Add_reminder);
            if (this.mRemindType == null && (remindDetail = this.mRemindDetail) != null) {
                this.mRemindType = remindDetail.getType();
            }
            refreshWithRemindType();
            this.typeId = this.mRemindType.getId();
            return;
        }
        if (i == 1) {
            setTitle(R.string.Reminder_detail);
            refreshWithRemindDetail();
            this.typeId = this.mRemindDetail.getType().getId();
            this.deviceId = this.mRemindDetail.getDeviceId();
            this.deviceType = this.mRemindDetail.getDeviceType();
            setTitleRightButton(getString(R.string.Delete), getResources().getColor(R.color.remind_dark_black), new View.OnClickListener() { // from class: com.petkit.android.activities.remind.AddRemindActivity2_0$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$setupViews$0(view);
                }
            });
            return;
        }
        if (i == 2) {
            setTitle(R.string.Reminder_detail);
            refreshWithRemindDetail();
            this.typeId = this.mRemindDetail.getType().getId();
            this.deviceId = this.mRemindDetail.getDeviceId();
            this.deviceType = this.mRemindDetail.getDeviceType();
            setTitleRightButton(getString(R.string.Edit), getResources().getColor(R.color.remind_dark_black), new View.OnClickListener() { // from class: com.petkit.android.activities.remind.AddRemindActivity2_0$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$setupViews$1(view);
                }
            });
            return;
        }
        if (i != 3) {
            return;
        }
        setTitle(R.string.Reminder_detail);
        refreshWithRemindDetail();
        this.typeId = this.mRemindDetail.getType().getId();
        this.deviceId = this.mRemindDetail.getDeviceId();
        this.deviceType = this.mRemindDetail.getDeviceType();
        setTitleRightButton(getString(R.string.Delete), getResources().getColor(R.color.remind_dark_black), new View.OnClickListener() { // from class: com.petkit.android.activities.remind.AddRemindActivity2_0$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$setupViews$2(view);
            }
        });
    }

    public /* synthetic */ void lambda$setupViews$0(View view) {
        showRemindDeleteWindow();
    }

    public /* synthetic */ void lambda$setupViews$1(View view) {
        enterEditMode();
    }

    public /* synthetic */ void lambda$setupViews$2(View view) {
        showRemindDeleteWindow();
    }

    private void enterEditMode() {
        this.editType = 1;
        setupViews();
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
        String updateAt = this.mRemindDetail.getUpdateAt();
        if (updateAt.isEmpty()) {
            findViewById(R.id.complete_time_line).setVisibility(8);
            findViewById(R.id.complete_time_rl).setVisibility(8);
        } else {
            findViewById(R.id.complete_time_line).setVisibility(0);
            findViewById(R.id.complete_time_rl).setVisibility(0);
        }
        ((TextView) findViewById(R.id.complete_time_tv)).setText(DateUtil.getFormatDateFromString(DateUtil.formatISO8601DateWithMills(new Date(CommonUtil.getLong(updateAt)))));
        ((BaseApplication) getApplication()).getAppComponent().imageLoader().loadImage(this, GlideImageConfig.builder().url(this.mRemindDetail.getType().getImg()).imageView((ImageView) findViewById(R.id.type_image)).errorPic(R.drawable.default_image_middle).build());
        TextView textView = (TextView) findViewById(R.id.type_name_tv);
        if (this.mRemindDetail.getType().getId().equals(com.tencent.connect.common.Constants.VIA_SHARE_TYPE_MINI_PROGRAM)) {
            textView.setText(this.mRemindDetail.getName());
        } else {
            textView.setText(this.mRemindDetail.getType().getName());
        }
        this.plan_time_tv = (TextView) findViewById(R.id.plan_time_tv);
        String time = this.mRemindDetail.getTime();
        this.planTime = time;
        if (!TextUtils.isEmpty(time)) {
            this.plan_time_tv.setText(DateUtil.getFormatDateFromString(this.planTime));
        }
        this.repeat_remind_tv = (TextView) findViewById(R.id.repeat_remind_tv);
        String repeat = this.mRemindDetail.getRepeat();
        if (TextUtils.isEmpty(repeat)) {
            this.repeat_remind_tv.setText(getString(R.string.None));
        } else {
            this.repeatString = repeat;
            if (repeat.length() > 1) {
                String strSubstring = repeat.substring(0, repeat.length() - 1);
                String dateUnit = ReminderUtils.getDateUnit(this, repeat.substring(repeat.length() - 1), Integer.parseInt(strSubstring) > 1);
                this.repeat_remind_tv.setText(strSubstring + dateUnit);
            }
        }
        if (this.mRemindDetail.getType().getWithPet() == 1) {
            findViewById(R.id.relevance_pet_rl).setVisibility(0);
            findViewById(R.id.relevance_pet_line).setVisibility(0);
            this.relevance_pet_tv = (TextView) findViewById(R.id.relevance_pet_tv);
            if (this.mRemindDetail.getPet() != null && !TextUtils.isEmpty(this.mRemindDetail.getPet().getName())) {
                this.petId = this.mRemindDetail.getPet().getId();
                this.relevance_pet_tv.setText(this.mRemindDetail.getPet().getName());
            } else {
                this.relevance_pet_tv.setText(getString(R.string.Not_assigned_pets));
            }
        } else {
            findViewById(R.id.relevance_pet_rl).setVisibility(8);
            findViewById(R.id.relevance_pet_line).setVisibility(8);
        }
        this.remark_et = (EditText) findViewById(R.id.remark_et);
        if (!TextUtils.isEmpty(this.mRemindDetail.getNotes())) {
            this.remark_et.setText(this.mRemindDetail.getNotes());
            this.remark_et.setSelection(this.mRemindDetail.getNotes().length());
        }
        int i = this.editType;
        if (i == 1) {
            findViewById(R.id.plan_time_arrow).setVisibility(0);
            findViewById(R.id.relevance_pet_arrow).setVisibility(0);
            findViewById(R.id.relevance_device_arrow).setVisibility(0);
            findViewById(R.id.repeat_remind_arrow).setVisibility(0);
            findViewById(R.id.plan_time_rl).setOnClickListener(this);
            findViewById(R.id.relevance_pet_rl).setOnClickListener(this);
            findViewById(R.id.relevance_device_rl).setOnClickListener(this);
            findViewById(R.id.repeat_remind_rl).setOnClickListener(this);
            if (TextUtils.isEmpty(this.mRemindDetail.getNotes())) {
                this.remark_et.setHint(R.string.Make_note_prompt);
            }
            this.remark_et.setFocusable(true);
            this.remark_et.setFocusableInTouchMode(true);
            this.remark_et.requestFocus();
            this.remark_et.addTextChangedListener(new TextWatcher() { // from class: com.petkit.android.activities.remind.AddRemindActivity2_0.1
                @Override // android.text.TextWatcher
                public void afterTextChanged(Editable editable) {
                }

                @Override // android.text.TextWatcher
                public void beforeTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
                }

                public AnonymousClass1() {
                }

                @Override // android.text.TextWatcher
                public void onTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
                    if (charSequence.equals(AddRemindActivity2_0.this.mRemindDetail.getNotes())) {
                        return;
                    }
                    AddRemindActivity2_0.this.findViewById(R.id.bottom_btn).setVisibility(0);
                }
            });
        } else if (i == 2) {
            if (TextUtils.isEmpty(this.mRemindDetail.getNotes())) {
                this.remark_et.setHint(R.string.Make_note_prompt);
            }
            this.remark_et.setFocusable(false);
            this.remark_et.setFocusableInTouchMode(false);
            findViewById(R.id.plan_time_arrow).setVisibility(4);
            findViewById(R.id.relevance_pet_arrow).setVisibility(4);
            findViewById(R.id.relevance_device_arrow).setVisibility(4);
            findViewById(R.id.repeat_remind_arrow).setVisibility(4);
        } else {
            findViewById(R.id.plan_time_arrow).setVisibility(4);
            findViewById(R.id.relevance_pet_arrow).setVisibility(4);
            findViewById(R.id.relevance_device_arrow).setVisibility(4);
            findViewById(R.id.repeat_remind_arrow).setVisibility(4);
            if (TextUtils.isEmpty(this.mRemindDetail.getNotes())) {
                this.remark_et.setHint(R.string.Pet_state_null);
            }
            this.remark_et.setFocusable(false);
            this.remark_et.setFocusableInTouchMode(false);
        }
        int i2 = this.editType;
        if (i2 == 2) {
            TextView textView2 = (TextView) findViewById(R.id.bottom_btn);
            this.bottomBtn = textView2;
            textView2.setVisibility(0);
            this.bottomBtn.setText(getString(R.string.Make_completed));
            this.bottomBtn.setOnClickListener(this);
        } else if (i2 == 1) {
            TextView textView3 = (TextView) findViewById(R.id.bottom_btn);
            this.bottomBtn = textView3;
            textView3.setVisibility(8);
            this.bottomBtn.setText(getString(R.string.Save));
            this.bottomBtn.setOnClickListener(this);
        } else {
            TextView textView4 = (TextView) findViewById(R.id.bottom_btn);
            this.bottomBtn = textView4;
            textView4.setVisibility(8);
        }
        if (this.modifyDeviceEnable || this.editType == 2) {
            return;
        }
        findViewById(R.id.relevance_device_arrow).setVisibility(4);
        findViewById(R.id.relevance_device_rl).setOnClickListener(null);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.remind.AddRemindActivity2_0$1 */
    public class AnonymousClass1 implements TextWatcher {
        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable editable) {
        }

        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
        }

        public AnonymousClass1() {
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
            if (charSequence.equals(AddRemindActivity2_0.this.mRemindDetail.getNotes())) {
                return;
            }
            AddRemindActivity2_0.this.findViewById(R.id.bottom_btn).setVisibility(0);
        }
    }

    private void refreshWithRemindType() {
        String str;
        findViewById(R.id.complete_time_line).setVisibility(8);
        findViewById(R.id.complete_time_rl).setVisibility(8);
        ((BaseApplication) getApplication()).getAppComponent().imageLoader().loadImage(this, GlideImageConfig.builder().url(this.mRemindType.getImg()).imageView((ImageView) findViewById(R.id.type_image)).errorPic(R.drawable.default_image_middle).build());
        ((TextView) findViewById(R.id.type_name_tv)).setText(this.mRemindType.getName());
        findViewById(R.id.plan_time_rl).setOnClickListener(this);
        this.plan_time_tv = (TextView) findViewById(R.id.plan_time_tv);
        findViewById(R.id.repeat_remind_rl).setOnClickListener(this);
        this.repeat_remind_tv = (TextView) findViewById(R.id.repeat_remind_tv);
        RemindType remindType = this.mRemindType;
        if (remindType != null) {
            if (TextUtils.isEmpty(remindType.getRepeat())) {
                this.repeat_remind_tv.setText(R.string.None);
            } else {
                String repeat = this.mRemindType.getRepeat();
                String strSubstring = repeat.substring(0, repeat.length() - 1);
                String dateUnit = ReminderUtils.getDateUnit(this, repeat.substring(repeat.length() - 1), Integer.parseInt(strSubstring) > 1);
                this.repeatString = repeat;
                this.repeat_remind_tv.setText(strSubstring + dateUnit);
                if (this.mRemindType.getId().equals(com.tencent.connect.common.Constants.VIA_REPORT_TYPE_MAKE_FRIEND) || this.mRemindType.getId().equals(com.tencent.connect.common.Constants.VIA_REPORT_TYPE_WPA_STATE) || this.mRemindType.getId().equals(com.tencent.connect.common.Constants.VIA_REPORT_TYPE_SHARE_TO_QQ)) {
                    Calendar calendar = Calendar.getInstance();
                    int i = calendar.get(1);
                    int i2 = calendar.get(2);
                    int i3 = calendar.get(5);
                    if (getResources().getString(R.string.Unit_days).equals(dateUnit)) {
                        calendar.set(5, i3 + CommonUtil.getInt(strSubstring));
                    } else if (getResources().getString(R.string.Unit_weeks).equals(dateUnit)) {
                        calendar.set(5, i3 + (CommonUtil.getInt(strSubstring) * 7));
                    } else if (getResources().getString(R.string.Unit_months).equals(dateUnit)) {
                        calendar.set(2, i2 + CommonUtil.getInt(strSubstring));
                    } else if (getResources().getString(R.string.Unit_years).equals(dateUnit) || getResources().getString(R.string.Unit_year).equals(dateUnit)) {
                        calendar.set(1, i + CommonUtil.getInt(strSubstring));
                    }
                    String iSO8601DateWithMills = DateUtil.formatISO8601DateWithMills(calendar.getTime());
                    this.planTime = iSO8601DateWithMills;
                    this.plan_time_tv.setText(DateUtil.getFormatDateFromString(iSO8601DateWithMills));
                }
            }
        }
        if (this.repeatString != null && (str = this.repeatText) != null) {
            this.repeat_remind_tv.setText(str);
            this.repeatString = this.mRemindType.getRepeat();
        }
        if (this.mRemindType.getWithPet() == 1) {
            findViewById(R.id.relevance_pet_rl).setVisibility(0);
            this.relevance_pet_tv = (TextView) findViewById(R.id.relevance_pet_tv);
            Pet petById = UserInforUtils.getPetById(this.petId);
            if (petById == null) {
                this.relevance_pet_tv.setText(getString(R.string.None));
            } else {
                this.relevance_pet_tv.setText(petById.getName());
            }
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
                findViewById(R.id.relevance_device_line).setVisibility(0);
                long j = this.deviceId;
                if (j != 0 && DeviceUtils.findDeviceInfo(j, this.deviceType) != null) {
                    ((TextView) findViewById(R.id.relevance_device_tv)).setText(DeviceUtils.findDeviceInfo(this.deviceId, this.deviceType).getName());
                } else {
                    ((TextView) findViewById(R.id.relevance_device_tv)).setText(getResources().getString(R.string.None));
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
        this.remark_et = (EditText) findViewById(R.id.remark_et);
        TextView textView = (TextView) findViewById(R.id.bottom_btn);
        this.bottomBtn = textView;
        textView.setVisibility(0);
        String str2 = this.planTime;
        if (str2 != null && str2.length() > 0) {
            this.bottomBtn.setBackgroundResource(R.drawable.selector_solid_new_blue);
            this.bottomBtn.setTextColor(getResources().getColor(R.color.white));
            this.bottomBtn.setClickable(true);
        } else {
            this.bottomBtn.setBackgroundResource(R.drawable.solid_login_light_blue_bg);
            this.bottomBtn.setTextColor(getResources().getColor(R.color.slide_bar_hint_color));
            this.bottomBtn.setClickable(false);
        }
        this.bottomBtn.setText(getString(R.string.Save));
        this.bottomBtn.setOnClickListener(this);
        if (this.modifyDeviceEnable) {
            return;
        }
        findViewById(R.id.relevance_device_arrow).setVisibility(4);
        findViewById(R.id.relevance_device_rl).setOnClickListener(null);
    }

    @Override // com.petkit.android.activities.base.BaseActivity, android.view.View.OnClickListener
    public void onClick(View view) {
        Calendar calendar;
        if (view.getId() == R.id.bottom_btn) {
            if (this.editType == 2) {
                if (TextUtils.isEmpty(this.mRemindDetail.getRepeat())) {
                    completeRemind();
                    return;
                }
                String time = this.mRemindDetail.getTime();
                try {
                    Date iSO8601Date = DateUtil.parseISO8601Date(time);
                    Calendar calendar2 = Calendar.getInstance();
                    calendar2.setTime(iSO8601Date);
                    calendar2.set(11, 0);
                    calendar2.set(12, 0);
                    calendar2.set(13, 0);
                    calendar2.set(14, 0);
                    Calendar calendar3 = Calendar.getInstance();
                    calendar3.set(11, 0);
                    calendar3.set(12, 0);
                    calendar3.set(13, 0);
                    calendar3.set(14, 0);
                    if (calendar2.compareTo(calendar3) == 0) {
                        completeRemind();
                    } else {
                        showRemindWindow(calendar2.compareTo(calendar3));
                    }
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    LogcatStorageHelper.addLog(" completeRemind error:" + e.getMessage() + " time:" + time);
                    PetkitLog.d(" completeRemind error:" + e.getMessage() + " time:" + time);
                    return;
                }
            }
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
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            } else {
                calendar = Calendar.getInstance();
                calendar.setTime(new Date());
            }
            DateTimePickDialogUtil dateTimePickDialogUtil = new DateTimePickDialogUtil(this, calendar, new DateTimePickDialogUtil.OnDateTimePickChange() { // from class: com.petkit.android.activities.remind.AddRemindActivity2_0$$ExternalSyntheticLambda3
                @Override // com.petkit.android.widget.DateTimePickDialogUtil.OnDateTimePickChange
                public final void onChange(boolean z, String str) {
                    this.f$0.lambda$onClick$3(z, str);
                }
            });
            dateTimePickDialogUtil.setNegativeColor(Integer.valueOf(R.color.blue));
            dateTimePickDialogUtil.setPositiveColor(Integer.valueOf(R.color.blue));
            dateTimePickDialogUtil.showDateTimePicKDialog();
            return;
        }
        if (view.getId() == R.id.repeat_remind_rl) {
            showRepeatReminderWindow();
            return;
        }
        if (view.getId() == R.id.relevance_pet_rl) {
            startActivityForResult(FamilySelectPetActivity.newIntent(this, true, UserInforUtils.getPetById(this.petId)), 1);
            return;
        }
        if (view.getId() == R.id.relevance_device_rl) {
            long j = this.deviceId;
            int i = this.deviceType;
            RemindType type = this.mRemindType;
            if (type == null) {
                type = this.mRemindDetail.getType();
            }
            startActivityForResult(FamilySelectDeviceActivity.newIntent(this, true, j, i, type.getWithDeviceType()), 3);
        }
    }

    public /* synthetic */ void lambda$onClick$3(boolean z, String str) {
        if (z) {
            int i = this.editType;
            if (i == 0) {
                this.bottomBtn.setBackgroundResource(R.drawable.selector_solid_new_blue);
                this.bottomBtn.setTextColor(getResources().getColor(R.color.white));
                this.bottomBtn.setClickable(true);
            } else if (i == 1) {
                try {
                    if (!this.mRemindDetail.getTime().equals(str)) {
                        this.bottomBtn.setVisibility(0);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            this.planTime = str;
            this.plan_time_tv.setText(DateUtil.getFormatDateFromString(str));
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(12:106|59|(1:86)(2:64|(1:85)(2:69|(1:84)(3:74|(9:79|87|108|88|92|93|(1:95)(1:(4:97|(2:100|98)|110|101))|102|111)|82)))|83|87|108|88|92|93|(0)(0)|102|111) */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x0133, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x0134, code lost:
    
        r0.printStackTrace();
        r0 = null;
     */
    /* JADX WARN: Removed duplicated region for block: B:95:0x0147 A[Catch: Exception -> 0x00ae, TRY_ENTER, TryCatch #0 {Exception -> 0x00ae, blocks: (B:59:0x0034, B:61:0x0043, B:64:0x0053, B:66:0x0061, B:69:0x0071, B:71:0x007f, B:74:0x008e, B:76:0x009c, B:87:0x0124, B:92:0x0138, B:95:0x0147, B:102:0x0215, B:97:0x01a8, B:98:0x01da, B:100:0x01e0, B:101:0x01e4, B:91:0x0134, B:82:0x00b1, B:84:0x00cf, B:85:0x00ec, B:86:0x0109, B:88:0x0128), top: B:106:0x0034, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:96:0x01a6  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void showRemindWindow(int r17) {
        /*
            Method dump skipped, instruction units count: 681
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.petkit.android.activities.remind.AddRemindActivity2_0.showRemindWindow(int):void");
    }

    private void showRepeatReminderWindow() {
        RemindType remindType = this.mRemindType;
        if (remindType == null || !TextUtils.isEmpty(remindType.getRepeatOption())) {
            RemindType remindType2 = this.mRemindType;
            if (remindType2 != null) {
                if (!remindType2.getId().equals(com.tencent.connect.common.Constants.VIA_SHARE_TYPE_INFO) && !this.mRemindType.getId().equals(com.tencent.connect.common.Constants.VIA_SHARE_TYPE_PUBLISHVIDEO) && !this.mRemindType.getId().equals("2") && !this.mRemindType.getId().equals("7") && !this.mRemindType.getId().equals("1") && !this.mRemindType.getId().equals("3") && !this.mRemindType.getId().equals(com.tencent.connect.common.Constants.VIA_TO_TYPE_QZONE)) {
                    this.mRemindType.getId().equals(com.tencent.connect.common.Constants.VIA_REPORT_TYPE_SHARE_TO_QQ);
                }
            } else {
                RemindDetail remindDetail = this.mRemindDetail;
                if (remindDetail != null && remindDetail.getType() != null && !this.mRemindDetail.getType().getId().equals(com.tencent.connect.common.Constants.VIA_SHARE_TYPE_INFO) && !this.mRemindDetail.getType().getId().equals(com.tencent.connect.common.Constants.VIA_SHARE_TYPE_PUBLISHVIDEO) && !this.mRemindDetail.getType().getId().equals("2") && !this.mRemindDetail.getType().getId().equals("7") && !this.mRemindDetail.getType().getId().equals("1") && !this.mRemindDetail.getType().getId().equals("3") && !this.mRemindDetail.getType().getId().equals(com.tencent.connect.common.Constants.VIA_TO_TYPE_QZONE)) {
                    this.mRemindDetail.getType().getId().equals(com.tencent.connect.common.Constants.VIA_REPORT_TYPE_SHARE_TO_QQ);
                }
            }
            RepeatReminderWindow repeatReminderWindow = new RepeatReminderWindow(this, false, new RepeatReminderWindow.OnSaveListener() { // from class: com.petkit.android.activities.remind.AddRemindActivity2_0.2
                public AnonymousClass2() {
                }

                @Override // com.petkit.android.activities.remind.widget.RepeatReminderWindow.OnSaveListener
                public void onSaved(String str, String str2) {
                    if (AddRemindActivity2_0.this.editType == 1) {
                        if (!TextUtils.isEmpty(AddRemindActivity2_0.this.mRemindDetail.getRepeat()) || TextUtils.isEmpty(str2)) {
                            if (!TextUtils.isEmpty(AddRemindActivity2_0.this.mRemindDetail.getRepeat()) && !AddRemindActivity2_0.this.mRemindDetail.getRepeat().equals(str2)) {
                                AddRemindActivity2_0.this.bottomBtn.setVisibility(0);
                            }
                        } else {
                            AddRemindActivity2_0.this.bottomBtn.setVisibility(0);
                        }
                    }
                    AddRemindActivity2_0.this.repeat_remind_tv.setText(str);
                    AddRemindActivity2_0.this.repeatString = str2;
                }
            }, this.mRemindType.getRepeatOption());
            repeatReminderWindow.setOutsideTouchable(true);
            repeatReminderWindow.show(getWindow().getDecorView());
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.remind.AddRemindActivity2_0$2 */
    public class AnonymousClass2 implements RepeatReminderWindow.OnSaveListener {
        public AnonymousClass2() {
        }

        @Override // com.petkit.android.activities.remind.widget.RepeatReminderWindow.OnSaveListener
        public void onSaved(String str, String str2) {
            if (AddRemindActivity2_0.this.editType == 1) {
                if (!TextUtils.isEmpty(AddRemindActivity2_0.this.mRemindDetail.getRepeat()) || TextUtils.isEmpty(str2)) {
                    if (!TextUtils.isEmpty(AddRemindActivity2_0.this.mRemindDetail.getRepeat()) && !AddRemindActivity2_0.this.mRemindDetail.getRepeat().equals(str2)) {
                        AddRemindActivity2_0.this.bottomBtn.setVisibility(0);
                    }
                } else {
                    AddRemindActivity2_0.this.bottomBtn.setVisibility(0);
                }
            }
            AddRemindActivity2_0.this.repeat_remind_tv.setText(str);
            AddRemindActivity2_0.this.repeatString = str2;
        }
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        DeviceInfos deviceInfosFindDeviceInfo;
        super.onActivityResult(i, i2, intent);
        if (i2 == -1) {
            String str = "";
            if (i == 1) {
                Pet pet = (Pet) intent.getSerializableExtra("pet");
                if (this.editType == 1 && ((this.mRemindDetail.getPet() != null || pet != null) && (this.mRemindDetail.getPet() == null || pet == null || !this.mRemindDetail.getPet().getId().equals(pet.getId())))) {
                    this.bottomBtn.setVisibility(0);
                }
                if (pet == null) {
                    this.relevance_pet_tv.setText(R.string.Not_assigned_pets);
                    this.petId = "";
                    return;
                }
                FamilyInfor familyInfor = FamilyUtils.getInstance().getFamilyInfor(this, pet.getFamilyId());
                if (familyInfor == null || String.valueOf(familyInfor.getOwner()).equals(UserInforUtils.getUser().getId())) {
                    this.relevance_pet_tv.setText(pet.getName());
                } else {
                    this.relevance_pet_tv.setText(pet.getName() + ChineseToPinyinResource.Field.LEFT_BRACKET + getResources().getString(R.string.Mate_share_setting) + ChineseToPinyinResource.Field.RIGHT_BRACKET);
                }
                this.petId = pet.getId();
                return;
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
            this.deviceId = intent.getLongExtra(Constants.EXTRA_DEVICE_ID, 0L);
            this.deviceType = intent.getIntExtra(Constants.EXTRA_DEVICE_TYPE, 0);
            if (this.editType == 1 && !"-1".equals(this.mRemindDetail.getType().getWithDeviceType()) && (((deviceInfosFindDeviceInfo = DeviceUtils.findDeviceInfo(this.mRemindDetail.getDeviceId(), this.mRemindDetail.getDeviceType())) == null && this.deviceId > 0) || ((deviceInfosFindDeviceInfo != null && this.deviceId == 0) || (this.deviceId > 0 && deviceInfosFindDeviceInfo.getDeviceId() != this.deviceId)))) {
                this.bottomBtn.setVisibility(0);
            }
            if (this.deviceId != 0) {
                String stringExtra = intent.getStringExtra(Constants.EXTRA_STRING);
                boolean zCheckIsSharedDevice = FamilyUtils.getInstance().checkIsSharedDevice(this, this.deviceId, this.deviceType);
                TextView textView = (TextView) findViewById(R.id.relevance_device_tv);
                StringBuilder sb = new StringBuilder();
                sb.append(stringExtra);
                if (zCheckIsSharedDevice) {
                    str = ChineseToPinyinResource.Field.LEFT_BRACKET + getResources().getString(R.string.Mate_share_setting) + ChineseToPinyinResource.Field.RIGHT_BRACKET;
                }
                sb.append(str);
                textView.setText(sb.toString());
                return;
            }
            ((TextView) findViewById(R.id.relevance_device_tv)).setText(getResources().getString(R.string.Not_associated_device));
        }
    }

    private void saveRemind() {
        HashMap map = new HashMap();
        int i = this.editType;
        if (i == 0) {
            map.put("name", this.mRemindType.getName());
            if (this.mRemindType.getUserCustomId() != 0) {
                map.put("userCustomId", String.valueOf(this.mRemindType.getUserCustomId()));
            }
        } else if (i == 1) {
            if (this.typeId.equals(com.tencent.connect.common.Constants.VIA_SHARE_TYPE_MINI_PROGRAM)) {
                map.put("name", this.mRemindDetail.getName());
            } else {
                map.put("name", this.mRemindDetail.getType().getName());
            }
            if (this.mRemindDetail.getType() != null && this.mRemindDetail.getType().getUserCustomId() != 0) {
                map.put("userCustomId", String.valueOf(this.mRemindDetail.getType().getUserCustomId()));
            }
        }
        if (this.editType != 0) {
            map.put("id", this.mRemindDetail.getId());
        }
        map.put("time", this.planTime);
        if (!TextUtils.isEmpty(this.repeatString)) {
            map.put("repeat", this.repeatString);
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
        post(ApiTools.SAMPLE_API_SCHEDULE_SAVE, map, new AsyncHttpRespHandler(this, false) { // from class: com.petkit.android.activities.remind.AddRemindActivity2_0.3
            public AnonymousClass3(Activity this, boolean z) {
                super(this, z);
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i2, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i2, headerArr, bArr);
                BaseRsp baseRsp = (BaseRsp) this.gson.fromJson(this.responseResult, BaseRsp.class);
                if (baseRsp.getError() != null) {
                    AddRemindActivity2_0.this.showLongToast(baseRsp.getError().getMsg(), R.drawable.toast_failed);
                    return;
                }
                EventBus.getDefault().post(new RemindUpdatedEvent(false));
                if (!AddRemindActivity2_0.this.isBind) {
                    if (AddRemindActivity2_0.this.deviceType == 24 || AddRemindActivity2_0.this.deviceType == 14) {
                        Intent intent = new Intent();
                        intent.putExtra(Constants.EXTRA_BOOLEAN, true);
                        AddRemindActivity2_0.this.setResult(9, intent);
                    }
                    AddRemindActivity2_0.this.finish();
                    return;
                }
                AddRemindActivity2_0.this.setResult(-1);
                if (AddRemindActivity2_0.this.deviceType == 24) {
                    EventBus.getDefault().post(new CTW3BindCompleteEvent());
                    EventBus.getDefault().post(new HgKillActivity());
                    AddRemindActivity2_0 addRemindActivity2_0 = AddRemindActivity2_0.this;
                    addRemindActivity2_0.startActivity(CTW3HomeActivity.newIntent(addRemindActivity2_0, addRemindActivity2_0.deviceId));
                    AddRemindActivity2_0.this.finish();
                }
            }
        }, false);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.remind.AddRemindActivity2_0$3 */
    public class AnonymousClass3 extends AsyncHttpRespHandler {
        public AnonymousClass3(Activity this, boolean z) {
            super(this, z);
        }

        @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
        public void onSuccess(int i2, Header[] headerArr, byte[] bArr) {
            super.onSuccess(i2, headerArr, bArr);
            BaseRsp baseRsp = (BaseRsp) this.gson.fromJson(this.responseResult, BaseRsp.class);
            if (baseRsp.getError() != null) {
                AddRemindActivity2_0.this.showLongToast(baseRsp.getError().getMsg(), R.drawable.toast_failed);
                return;
            }
            EventBus.getDefault().post(new RemindUpdatedEvent(false));
            if (!AddRemindActivity2_0.this.isBind) {
                if (AddRemindActivity2_0.this.deviceType == 24 || AddRemindActivity2_0.this.deviceType == 14) {
                    Intent intent = new Intent();
                    intent.putExtra(Constants.EXTRA_BOOLEAN, true);
                    AddRemindActivity2_0.this.setResult(9, intent);
                }
                AddRemindActivity2_0.this.finish();
                return;
            }
            AddRemindActivity2_0.this.setResult(-1);
            if (AddRemindActivity2_0.this.deviceType == 24) {
                EventBus.getDefault().post(new CTW3BindCompleteEvent());
                EventBus.getDefault().post(new HgKillActivity());
                AddRemindActivity2_0 addRemindActivity2_0 = AddRemindActivity2_0.this;
                addRemindActivity2_0.startActivity(CTW3HomeActivity.newIntent(addRemindActivity2_0, addRemindActivity2_0.deviceId));
                AddRemindActivity2_0.this.finish();
            }
        }
    }

    private void completeRemind(Date date) {
        HashMap map = new HashMap();
        map.put("id", this.mRemindDetail.getId());
        map.put("time", DateUtil.formatISO8601DateWithMills(new Date()));
        map.put("newTime", DateUtil.formatISO8601DateWithMills(date));
        post(ApiTools.SAMPLE_API_SCHEDULE_COMPLETE, map, new AsyncHttpRespHandler(this, false) { // from class: com.petkit.android.activities.remind.AddRemindActivity2_0.4
            public AnonymousClass4(Activity this, boolean z) {
                super(this, z);
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                BaseRsp baseRsp = (BaseRsp) this.gson.fromJson(this.responseResult, BaseRsp.class);
                if (baseRsp.getError() != null) {
                    AddRemindActivity2_0.this.showLongToast(baseRsp.getError().getMsg(), R.drawable.toast_failed);
                    return;
                }
                AddRemindActivity2_0.this.setResult(-1);
                EventBus.getDefault().post(new RemindUpdatedEvent(false));
                AddRemindActivity2_0.this.checkSchedule();
                AddRemindActivity2_0.this.finish();
            }
        }, false);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.remind.AddRemindActivity2_0$4 */
    public class AnonymousClass4 extends AsyncHttpRespHandler {
        public AnonymousClass4(Activity this, boolean z) {
            super(this, z);
        }

        @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
        public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
            super.onSuccess(i, headerArr, bArr);
            BaseRsp baseRsp = (BaseRsp) this.gson.fromJson(this.responseResult, BaseRsp.class);
            if (baseRsp.getError() != null) {
                AddRemindActivity2_0.this.showLongToast(baseRsp.getError().getMsg(), R.drawable.toast_failed);
                return;
            }
            AddRemindActivity2_0.this.setResult(-1);
            EventBus.getDefault().post(new RemindUpdatedEvent(false));
            AddRemindActivity2_0.this.checkSchedule();
            AddRemindActivity2_0.this.finish();
        }
    }

    private void completeRemind() {
        HashMap map = new HashMap();
        map.put("id", this.mRemindDetail.getId());
        map.put("time", DateUtil.formatISO8601DateWithMills(new Date()));
        post(ApiTools.SAMPLE_API_SCHEDULE_COMPLETE, map, new AsyncHttpRespHandler(this, false) { // from class: com.petkit.android.activities.remind.AddRemindActivity2_0.5
            public AnonymousClass5(Activity this, boolean z) {
                super(this, z);
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                BaseRsp baseRsp = (BaseRsp) this.gson.fromJson(this.responseResult, BaseRsp.class);
                if (baseRsp.getError() != null) {
                    AddRemindActivity2_0.this.showLongToast(baseRsp.getError().getMsg(), R.drawable.toast_failed);
                    return;
                }
                AddRemindActivity2_0.this.setResult(-1);
                EventBus.getDefault().post(new RemindUpdatedEvent(false));
                AddRemindActivity2_0.this.checkSchedule();
                AddRemindActivity2_0.this.finish();
            }
        }, false);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.remind.AddRemindActivity2_0$5 */
    public class AnonymousClass5 extends AsyncHttpRespHandler {
        public AnonymousClass5(Activity this, boolean z) {
            super(this, z);
        }

        @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
        public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
            super.onSuccess(i, headerArr, bArr);
            BaseRsp baseRsp = (BaseRsp) this.gson.fromJson(this.responseResult, BaseRsp.class);
            if (baseRsp.getError() != null) {
                AddRemindActivity2_0.this.showLongToast(baseRsp.getError().getMsg(), R.drawable.toast_failed);
                return;
            }
            AddRemindActivity2_0.this.setResult(-1);
            EventBus.getDefault().post(new RemindUpdatedEvent(false));
            AddRemindActivity2_0.this.checkSchedule();
            AddRemindActivity2_0.this.finish();
        }
    }

    private void getRemindDetail(String str) {
        HashMap map = new HashMap();
        map.put("id", str);
        post(ApiTools.SAMPLE_API_SCHEDULE_GET, map, new AsyncHttpRespHandler(this, false) { // from class: com.petkit.android.activities.remind.AddRemindActivity2_0.6
            public AnonymousClass6(Activity this, boolean z) {
                super(this, z);
            }

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
                        AddRemindActivity2_0.this.showLongToast(remindDetailRsp.getError().getMsg());
                        return;
                    } else {
                        AddRemindActivity2_0.this.showLongToast(remindDetailRsp.getError().getMsg(), R.drawable.toast_failed);
                        return;
                    }
                }
                if (remindDetailRsp.getResult() != null) {
                    AddRemindActivity2_0.this.mRemindDetail = remindDetailRsp.getResult();
                    AddRemindActivity2_0.this.setupViews();
                }
            }
        }, false);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.remind.AddRemindActivity2_0$6 */
    public class AnonymousClass6 extends AsyncHttpRespHandler {
        public AnonymousClass6(Activity this, boolean z) {
            super(this, z);
        }

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
                    AddRemindActivity2_0.this.showLongToast(remindDetailRsp.getError().getMsg());
                    return;
                } else {
                    AddRemindActivity2_0.this.showLongToast(remindDetailRsp.getError().getMsg(), R.drawable.toast_failed);
                    return;
                }
            }
            if (remindDetailRsp.getResult() != null) {
                AddRemindActivity2_0.this.mRemindDetail = remindDetailRsp.getResult();
                AddRemindActivity2_0.this.setupViews();
            }
        }
    }

    public void deleteRemind() {
        HashMap map = new HashMap();
        map.put("id", this.mRemindDetail.getId());
        post(ApiTools.SAMPLE_API_SCHEDULE_REMOVE, map, new AsyncHttpRespHandler(this, false) { // from class: com.petkit.android.activities.remind.AddRemindActivity2_0.7
            public AnonymousClass7(Activity this, boolean z) {
                super(this, z);
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                BaseRsp baseRsp = (BaseRsp) this.gson.fromJson(this.responseResult, BaseRsp.class);
                if (baseRsp.getError() != null) {
                    AddRemindActivity2_0.this.showLongToast(baseRsp.getError().getMsg(), R.drawable.toast_failed);
                } else {
                    EventBus.getDefault().post(new RemindUpdatedEvent(true));
                    AddRemindActivity2_0.this.finish();
                }
            }
        }, false);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.remind.AddRemindActivity2_0$7 */
    public class AnonymousClass7 extends AsyncHttpRespHandler {
        public AnonymousClass7(Activity this, boolean z) {
            super(this, z);
        }

        @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
        public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
            super.onSuccess(i, headerArr, bArr);
            BaseRsp baseRsp = (BaseRsp) this.gson.fromJson(this.responseResult, BaseRsp.class);
            if (baseRsp.getError() != null) {
                AddRemindActivity2_0.this.showLongToast(baseRsp.getError().getMsg(), R.drawable.toast_failed);
            } else {
                EventBus.getDefault().post(new RemindUpdatedEvent(true));
                AddRemindActivity2_0.this.finish();
            }
        }
    }

    @Subscriber(mode = ThreadMode.MAIN)
    public void onRemindDeleted(RemindUpdatedEvent remindUpdatedEvent) {
        if (remindUpdatedEvent.isDeleted()) {
            finish();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x002e  */
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
        throw new UnsupportedOperationException("Method not decompiled: com.petkit.android.activities.remind.AddRemindActivity2_0.checkSchedule():void");
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.remind.AddRemindActivity2_0$8 */
    public class AnonymousClass8 implements TipWindow.TipClickListener {
        public AnonymousClass8() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.widget.frame.TipWindow.TipClickListener
        public void onCancelClick() {
            AddRemindActivity2_0.this.remindDeleteWindow.dismiss();
        }

        @Override // com.petkit.android.activities.petkitBleDevice.widget.frame.TipWindow.TipClickListener
        public void onConfirmClick() {
            AddRemindActivity2_0.this.remindDeleteWindow.dismiss();
            AddRemindActivity2_0.this.deleteRemind();
        }
    }

    private void showRemindDeleteWindow() {
        TipWindow tipWindow = this.remindDeleteWindow;
        if (tipWindow == null || !tipWindow.isShowing()) {
            TipWindow tipWindow2 = new TipWindow(this, getResources().getString(R.string.Delete_remind_confirm_tip), new TipWindow.TipClickListener() { // from class: com.petkit.android.activities.remind.AddRemindActivity2_0.8
                public AnonymousClass8() {
                }

                @Override // com.petkit.android.activities.petkitBleDevice.widget.frame.TipWindow.TipClickListener
                public void onCancelClick() {
                    AddRemindActivity2_0.this.remindDeleteWindow.dismiss();
                }

                @Override // com.petkit.android.activities.petkitBleDevice.widget.frame.TipWindow.TipClickListener
                public void onConfirmClick() {
                    AddRemindActivity2_0.this.remindDeleteWindow.dismiss();
                    AddRemindActivity2_0.this.deleteRemind();
                }
            });
            this.remindDeleteWindow = tipWindow2;
            tipWindow2.setConfirmColor(getResources().getColor(R.color.login_new_blue));
            this.remindDeleteWindow.setCancelColor(getResources().getColor(R.color.gray));
            this.remindDeleteWindow.show(getWindow().getDecorView());
        }
    }

    @Override // com.petkit.android.activities.universalWindow.BaseBottomWindow.OnClickListener
    public void onBtnLeft() {
        RemindRepeatWindow remindRepeatWindow = this.remindRepeatWindow;
        if (remindRepeatWindow != null) {
            remindRepeatWindow.dismiss();
        }
    }

    @Override // com.petkit.android.activities.universalWindow.BaseBottomWindow.OnClickListener
    public void onBtnRight() {
        RemindRepeatWindow remindRepeatWindow = this.remindRepeatWindow;
        if (remindRepeatWindow != null) {
            remindRepeatWindow.dismiss();
        }
    }

    @Override // com.petkit.android.activities.remind.widget.RemindRepeatWindow.RemindRepeatListener
    public void onOriginalClick(Date date, RemindDetail remindDetail, int i) {
        completeRemind(date);
    }

    @Override // com.petkit.android.activities.remind.widget.RemindRepeatWindow.RemindRepeatListener
    public void onTodayClick(Date date, RemindDetail remindDetail, int i) {
        completeRemind(date);
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}

package com.petkit.android.activities.feeder.setting;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.jess.arms.widget.imageloader.glide.GlideCircleTransform;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.petkit.android.activities.base.BaseActivity;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.feeder.Utils.FeederUtils;
import com.petkit.android.activities.feeder.api.FeederPlanRsp;
import com.petkit.android.activities.feeder.model.FeederPlan;
import com.petkit.android.activities.feeder.model.FeederRecord;
import com.petkit.android.activities.home.HomeActivity;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.apiResponse.ResultStringRsp;
import com.petkit.android.model.Pet;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.SpannableStringUtils;
import com.petkit.oversea.R;
import com.tencent.connect.common.Constants;
import cz.msebera.android.httpclient.Header;
import java.util.HashMap;
import kotlinx.coroutines.DebugKt;

/* JADX INFO: loaded from: classes3.dex */
public class FeederPlanActivity extends BaseActivity {
    private BroadcastReceiver mBroadcastReceiver;
    private FeederPlan mFeederPlan;
    private FeederRecord mFeederRecord;
    private LinearLayout petTitle;

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        long longExtra;
        super.onCreate(bundle);
        if (bundle == null) {
            longExtra = getIntent().getLongExtra(FeederUtils.EXTRA_FEEDER_ID, 0L);
        } else {
            longExtra = bundle.getLong(FeederUtils.EXTRA_FEEDER_ID);
        }
        FeederRecord feederRecordByDeviceId = FeederUtils.getFeederRecordByDeviceId(longExtra);
        this.mFeederRecord = feederRecordByDeviceId;
        if (feederRecordByDeviceId == null) {
            finish();
            return;
        }
        this.mFeederPlan = FeederUtils.getFeederPlanForDeviceId(longExtra);
        setContentView(R.layout.activity_feeder_plan);
        getFeederPlan();
        registerBoradcastReceiver();
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        unregisterBroadcastReceiver();
    }

    @Override // com.petkit.android.activities.base.BaseActivity
    public void setupViews() {
        setTitleBackgroundColor(CommonUtils.getColorById(R.color.transparent));
        setTitleLeftButton(R.drawable.btn_back_white);
        setTitleRightButton(getString(R.string.Edit), CommonUtils.getColorById(R.color.white), this);
        setDividerLineVisibility(8);
        setTitleStatus();
        refreshFeederPlanContent();
        findViewById(R.id.feeder_plan_suspend_icon).setOnClickListener(this);
    }

    @Override // com.petkit.android.activities.base.BaseActivity, android.view.View.OnClickListener
    public void onClick(View view) {
        super.onClick(view);
        int id = view.getId();
        if (id == R.id.title_right_btn) {
            Bundle bundle = new Bundle();
            bundle.putLong(FeederUtils.EXTRA_FEEDER_ID, this.mFeederRecord.getDeviceId());
            bundle.putSerializable(FeederUtils.EXTRA_FEEDER_PLAN, FeederUtils.getFeederPlanForDeviceId(this.mFeederRecord.getDeviceId()));
            startActivityWithData(FeederPlanEditActivity.class, bundle, false);
            return;
        }
        if (id == R.id.feeder_plan_suspend_icon) {
            if (this.mFeederPlan.getSuspended() == 0) {
                showSuspendPromptDialog();
            } else {
                changeSuspendState();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshFeederPlanContent() {
        if (this.mFeederPlan == null || this.mFeederRecord == null) {
            return;
        }
        TextView textView = (TextView) findViewById(R.id.feeder_plan_name);
        if (this.mFeederRecord.getDeviceShared() == null) {
            textView.setText(getString(R.string.Feeder_plan_name_format, this.mFeederRecord.getPet().getName()));
        }
        int colorById = CommonUtils.getColorById(this.mFeederPlan.getSuspended() == 0 ? R.color.black : R.color.gray);
        ((TextView) findViewById(R.id.feeder_plan_count)).setText(SpannableStringUtils.makeSpannableString(new SpannableStringUtils.SpanText(String.valueOf(this.mFeederPlan.getCount()), colorById, 3.5f), new SpannableStringUtils.SpanText(getString(this.mFeederPlan.getCount() > 1 ? R.string.Unit_times : R.string.Unit_time), colorById, 0.9f)));
        ((TextView) findViewById(R.id.feeder_plan_amount)).setText(SpannableStringUtils.makeSpannableString(new SpannableStringUtils.SpanText(FeederUtils.getAmountFormat(this.mFeederPlan.getTotalAmount()), colorById, 3.5f), new SpannableStringUtils.SpanText(getString(FeederUtils.getFeederAmountUnit(this.mFeederPlan.getTotalAmount())), colorById, 0.9f)));
        ((TextView) findViewById(R.id.feeder_plan_repeats)).setText(getFeederPlanRepeatsString());
        TextView textView2 = (TextView) findViewById(R.id.feeder_plan_state);
        ImageView imageView = (ImageView) findViewById(R.id.feeder_plan_suspend_icon);
        if (this.mFeederPlan.getSuspended() == 1) {
            textView2.setText(R.string.Feeder_plan_closed);
            textView2.setTextColor(CommonUtils.getColorById(R.color.gray));
            imageView.setImageResource(R.drawable.btn_feeder_plan_suspend_close);
            setBackgroundResourceId(R.drawable.feeder_plan_close_bg);
            return;
        }
        textView2.setText(R.string.Feeder_plan_opened);
        textView2.setTextColor(CommonUtils.getColorById(R.color.feeder_main_color));
        imageView.setImageResource(R.drawable.btn_feeder_plan_suspend_open);
        setBackgroundResourceId(R.drawable.feeder_plan_open_bg);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getFeederPlan() {
        HashMap map = new HashMap();
        map.put("deviceId", String.valueOf(this.mFeederRecord.getDeviceId()));
        post(ApiTools.SAMPLET_API_FEEDER_FEED, map, new AsyncHttpRespHandler(this, false) { // from class: com.petkit.android.activities.feeder.setting.FeederPlanActivity.1
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                FeederPlanRsp feederPlanRsp = (FeederPlanRsp) this.gson.fromJson(this.responseResult, FeederPlanRsp.class);
                if (feederPlanRsp.getError() == null) {
                    FeederPlanActivity.this.mFeederPlan = feederPlanRsp.getResult();
                    FeederUtils.saveFeederPlanForDeviceId(FeederPlanActivity.this.mFeederRecord.getDeviceId(), FeederPlanActivity.this.mFeederPlan);
                    Intent intent = new Intent(FeederUtils.BROADCAST_FEEDER_PLAN_CHANGED);
                    intent.putExtra(FeederUtils.EXTRA_FEEDER_ID, FeederPlanActivity.this.mFeederRecord.getDeviceId());
                    LocalBroadcastManager.getInstance(FeederPlanActivity.this).sendBroadcast(intent);
                    FeederPlanActivity.this.refreshFeederPlanContent();
                    return;
                }
                FeederPlanActivity.this.showShortToast(feederPlanRsp.getError().getMsg(), R.drawable.toast_failed);
            }
        }, false);
    }

    private void showSuspendPromptDialog() {
        new AlertDialog.Builder(this).setCancelable(false).setTitle(R.string.Prompt).setMessage(R.string.Hint_suspend_feeder_plan).setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() { // from class: com.petkit.android.activities.feeder.setting.FeederPlanActivity.3
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                FeederPlanActivity.this.changeSuspendState();
            }
        }).setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() { // from class: com.petkit.android.activities.feeder.setting.FeederPlanActivity.2
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeSuspendState() {
        HashMap map = new HashMap();
        map.put("deviceId", String.valueOf(this.mFeederRecord.getDeviceId()));
        post(this.mFeederPlan.getSuspended() == 0 ? ApiTools.SAMPLET_API_FEEDER_SUSPEND : ApiTools.SAMPLET_API_FEEDER_RESTORE, map, new AsyncHttpRespHandler(this, true) { // from class: com.petkit.android.activities.feeder.setting.FeederPlanActivity.4
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                ResultStringRsp resultStringRsp = (ResultStringRsp) this.gson.fromJson(this.responseResult, ResultStringRsp.class);
                if (resultStringRsp.getError() == null) {
                    new HashMap().put("type", FeederPlanActivity.this.mFeederPlan.getSuspended() == 0 ? DebugKt.DEBUG_PROPERTY_VALUE_ON : DebugKt.DEBUG_PROPERTY_VALUE_OFF);
                    FeederPlanActivity.this.mFeederPlan.setSuspended(FeederPlanActivity.this.mFeederPlan.getSuspended() == 0 ? 1 : 0);
                    FeederUtils.saveFeederPlanForDeviceId(FeederPlanActivity.this.mFeederRecord.getDeviceId(), FeederPlanActivity.this.mFeederPlan);
                    FeederPlanActivity.this.refreshFeederPlanContent();
                    Intent intent = new Intent(FeederUtils.BROADCAST_FEEDER_PLAN_CHANGED);
                    intent.putExtra(FeederUtils.EXTRA_FEEDER_ID, FeederPlanActivity.this.mFeederRecord.getDeviceId());
                    LocalBroadcastManager.getInstance(FeederPlanActivity.this).sendBroadcast(intent);
                    return;
                }
                FeederPlanActivity.this.showShortToast(resultStringRsp.getError().getMsg(), R.drawable.toast_failed);
            }
        }, false);
    }

    private SpannableStringBuilder getFeederPlanRepeatsString() {
        String string;
        String repeats = this.mFeederPlan.getRepeats();
        if (!getResources().getConfiguration().locale.getCountry().equals("CN")) {
            string = "";
        } else {
            string = getResources().getString(R.string.Unit_week);
        }
        return SpannableStringUtils.makeSpannableString(new SpannableStringUtils.SpanText(string + getString(R.string.Week_monday) + "、", CommonUtils.getColorById(repeats.contains("2") ? R.color.feeder_main_color : R.color.black), 1.0f), new SpannableStringUtils.SpanText(string + getString(R.string.Week_tuesday) + "、", CommonUtils.getColorById(repeats.contains("3") ? R.color.feeder_main_color : R.color.black), 1.0f), new SpannableStringUtils.SpanText(string + getString(R.string.Week_wednesday) + "、", CommonUtils.getColorById(repeats.contains(Constants.VIA_TO_TYPE_QZONE) ? R.color.feeder_main_color : R.color.black), 1.0f), new SpannableStringUtils.SpanText(string + getString(R.string.Week_thursday) + "、", CommonUtils.getColorById(repeats.contains("5") ? R.color.feeder_main_color : R.color.black), 1.0f), new SpannableStringUtils.SpanText(string + getString(R.string.Week_friday) + "、", CommonUtils.getColorById(repeats.contains(Constants.VIA_SHARE_TYPE_INFO) ? R.color.feeder_main_color : R.color.black), 1.0f), new SpannableStringUtils.SpanText(string + getString(R.string.Week_saturday) + "、", CommonUtils.getColorById(repeats.contains("7") ? R.color.feeder_main_color : R.color.black), 1.0f), new SpannableStringUtils.SpanText(string + getString(R.string.Week_sunday), CommonUtils.getColorById(repeats.contains("1") ? R.color.feeder_main_color : R.color.black), 1.0f));
    }

    private void setTitleStatus() {
        if (this.petTitle == null) {
            LinearLayout titleTab = getTitleTab();
            this.petTitle = titleTab;
            titleTab.setVisibility(0);
            this.petTitle.setOnClickListener(this);
        }
        FeederRecord feederRecord = this.mFeederRecord;
        if (feederRecord != null) {
            feederRecord.getDeviceShared();
            refreshPetTitleView(this.mFeederRecord.getPet());
        }
    }

    private void refreshPetTitleView(Pet pet) {
        this.petTitle.removeAllViews();
        if (pet == null || pet.getId() == null) {
            return;
        }
        View viewInflate = LayoutInflater.from(this).inflate(R.layout.layout_pet_title, (ViewGroup) null);
        ImageView imageView = (ImageView) viewInflate.findViewById(R.id.dog_avatar);
        TextView textView = (TextView) viewInflate.findViewById(R.id.dog_name);
        if (this.mFeederRecord.getDeviceShared() == null) {
            ((BaseApplication) getApplication()).getAppComponent().imageLoader().loadImage(this, GlideImageConfig.builder().url(pet.getAvatar()).imageView(imageView).errorPic(pet.getType().getId() == 1 ? R.drawable.default_header_dog : R.drawable.default_header_cat).transformation(new GlideCircleTransform(this)).build());
        } else {
            ((BaseApplication) getApplication()).getAppComponent().imageLoader().loadImage(this, GlideImageConfig.builder().url(pet.getAvatar()).imageView(imageView).transformation(new GlideCircleTransform(this)).build());
        }
        textView.setVisibility(8);
        this.petTitle.addView(viewInflate);
    }

    private void registerBoradcastReceiver() {
        this.mBroadcastReceiver = new BroadcastReceiver() { // from class: com.petkit.android.activities.feeder.setting.FeederPlanActivity.5
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                action.hashCode();
                switch (action) {
                    case "BROADCAST_FEEDER_PLAN_CHANGED_PIM":
                        if (intent.getLongExtra(FeederUtils.EXTRA_FEEDER_ID, 0L) == FeederPlanActivity.this.mFeederRecord.getDeviceId()) {
                            FeederPlanActivity.this.getFeederPlan();
                            break;
                        }
                        break;
                    case "BROADCAST_FEEDER_PLAN_CHANGED":
                        long longExtra = intent.getLongExtra(FeederUtils.EXTRA_FEEDER_ID, 0L);
                        if (longExtra == FeederPlanActivity.this.mFeederRecord.getDeviceId()) {
                            FeederPlanActivity.this.mFeederPlan = FeederUtils.getFeederPlanForDeviceId(longExtra);
                            FeederPlanActivity.this.refreshFeederPlanContent();
                            break;
                        }
                        break;
                    case "BROADCAST_FEEDER_SHARE_CANCEL":
                        FeederPlanActivity.this.startActivity(HomeActivity.class);
                        break;
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(FeederUtils.BROADCAST_FEEDER_PLAN_CHANGED_PIM);
        intentFilter.addAction(FeederUtils.BROADCAST_FEEDER_PLAN_CHANGED);
        intentFilter.addAction(FeederUtils.BROADCAST_FEEDER_SHARE_CANCEL);
        LocalBroadcastManager.getInstance(this).registerReceiver(this.mBroadcastReceiver, intentFilter);
    }

    private void unregisterBroadcastReceiver() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(this.mBroadcastReceiver);
    }
}

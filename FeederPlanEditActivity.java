package com.petkit.android.activities.feeder.setting;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.petkit.android.activities.base.BaseActivity;
import com.petkit.android.activities.feeder.Utils.FeederUtils;
import com.petkit.android.activities.feeder.model.FeederPlan;
import com.petkit.android.activities.feeder.model.FeederPlanItem;
import com.petkit.android.activities.feeder.model.FeederRecord;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.apiResponse.ResultStringRsp;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.SpannableStringUtils;
import com.petkit.android.widget.WeeksSelectView;
import com.petkit.oversea.R;
import cz.msebera.android.httpclient.Header;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;

/* JADX INFO: loaded from: classes3.dex */
public class FeederPlanEditActivity extends BaseActivity {
    private long deviceId;
    private boolean isFirstInit;
    private boolean isPlanEdited = false;
    private FeederPlan mFeederPlan;

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            this.deviceId = bundle.getLong(FeederUtils.EXTRA_FEEDER_ID);
            this.isFirstInit = bundle.getBoolean(Constants.EXTRA_BOOLEAN);
            this.mFeederPlan = (FeederPlan) bundle.getSerializable(FeederUtils.EXTRA_FEEDER_PLAN);
        } else {
            this.deviceId = getIntent().getLongExtra(FeederUtils.EXTRA_FEEDER_ID, -1L);
            this.isFirstInit = getIntent().getBooleanExtra(Constants.EXTRA_BOOLEAN, false);
            this.mFeederPlan = (FeederPlan) getIntent().getSerializableExtra(FeederUtils.EXTRA_FEEDER_PLAN);
        }
        setContentView(R.layout.activity_feeder_plan_edit);
        if (this.mFeederPlan == null) {
            finish();
        }
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putLong(FeederUtils.EXTRA_FEEDER_ID, this.deviceId);
        bundle.putSerializable(FeederUtils.EXTRA_FEEDER_PLAN, this.mFeederPlan);
        bundle.putBoolean(Constants.EXTRA_BOOLEAN, this.isFirstInit);
    }

    @Override // com.petkit.android.activities.base.BaseActivity
    public void setupViews() {
        setNoTitle();
        findViewById(R.id.toolbar_right_text).setOnClickListener(this);
        findViewById(R.id.toolbar_back).setOnClickListener(this);
        refreshFeederPlanItems();
        findViewById(R.id.feeder_plan_add).setOnClickListener(this);
    }

    @Override // com.petkit.android.activities.base.BaseActivity, android.view.View.OnClickListener
    public void onClick(View view) {
        super.onClick(view);
        int id = view.getId();
        if (id == R.id.feeder_plan_add) {
            Intent intent = new Intent(this, (Class<?>) FeederPlanItemEditActivity.class);
            intent.putExtra(FeederUtils.EXTRA_FEEDER_PLAN, this.mFeederPlan);
            intent.putExtra(FeederUtils.EXTRA_FEEDER_ID, this.deviceId);
            startActivityForResult(intent, 17);
            return;
        }
        if (id == R.id.feeder_plan_item_view) {
            FeederPlanItem feederPlanItem = (FeederPlanItem) view.getTag();
            Intent intent2 = new Intent(this, (Class<?>) FeederPlanItemEditActivity.class);
            intent2.putExtra(FeederUtils.EXTRA_FEEDER_PLAN_ITEM, feederPlanItem);
            intent2.putExtra(FeederUtils.EXTRA_FEEDER_PLAN, this.mFeederPlan);
            intent2.putExtra(FeederUtils.EXTRA_FEEDER_ID, this.deviceId);
            startActivityForResult(intent2, 17);
            return;
        }
        if (id == R.id.title_right_btn) {
            if (this.isPlanEdited || this.isFirstInit) {
                saveFeederPlan();
            } else {
                complete();
            }
        }
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == -1 && i == 17) {
            updateFeederPlanItem((FeederPlanItem) intent.getSerializableExtra(FeederUtils.EXTRA_FEEDER_PLAN_ITEM), intent.getBooleanExtra(Constants.EXTRA_BOOLEAN, false));
            refreshFeederPlanItems();
            this.isPlanEdited = true;
        }
    }

    private void refreshFeederPlanItems() {
        if (this.mFeederPlan == null) {
            return;
        }
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.rl_timezone);
        FeederRecord feederRecordByDeviceId = FeederUtils.getFeederRecordByDeviceId(this.deviceId);
        if (feederRecordByDeviceId == null || CommonUtils.isSameTimeZoneAsLocal(feederRecordByDeviceId.getLocale(), feederRecordByDeviceId.getTimezone())) {
            relativeLayout.setVisibility(8);
        } else {
            relativeLayout.setVisibility(0);
        }
        WeeksSelectView weeksSelectView = (WeeksSelectView) findViewById(R.id.weeks_select);
        weeksSelectView.setSelectWeeksString(this.mFeederPlan.getRepeats());
        weeksSelectView.setListener(new WeeksSelectView.onWeekChangedListener() { // from class: com.petkit.android.activities.feeder.setting.FeederPlanEditActivity.1
            @Override // com.petkit.android.widget.WeeksSelectView.onWeekChangedListener
            public void onWeekChanged(String str) {
                FeederPlanEditActivity.this.isPlanEdited = true;
                FeederPlanEditActivity.this.mFeederPlan.setRepeats(str);
            }
        });
        ((TextView) findViewById(R.id.feeder_total_number)).setText(SpannableStringUtils.makeSpannableString(new SpannableStringUtils.SpanText(getString(R.string.Feeder_plan_number), CommonUtils.getColorById(R.color.black), 1.0f), new SpannableStringUtils.SpanText("\n" + this.mFeederPlan.getCount(), CommonUtils.getColorById(R.color.black), 1.5f)));
        ((TextView) findViewById(R.id.feeder_total_amount)).setText(SpannableStringUtils.makeSpannableString(new SpannableStringUtils.SpanText(getString(R.string.Feeder_plan_total_amount), CommonUtils.getColorById(R.color.black), 1.0f), new SpannableStringUtils.SpanText("\n" + FeederUtils.getAmountFormat(this.mFeederPlan.getTotalAmount()), CommonUtils.getColorById(R.color.black), 1.5f), new SpannableStringUtils.SpanText(getString(R.string.Feeder_unit_short), CommonUtils.getColorById(R.color.black), 0.8f)));
        initFeederPlanItemsView();
    }

    private void initFeederPlanItemsView() {
        if (this.mFeederPlan.getItems() == null) {
            return;
        }
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.feeder_plan_items);
        linearLayout.removeAllViews();
        for (FeederPlanItem feederPlanItem : this.mFeederPlan.getItems()) {
            View viewInflate = LayoutInflater.from(this).inflate(R.layout.layout_feeder_plan_item, (ViewGroup) null, false);
            ((TextView) viewInflate.findViewById(R.id.feeder_plan_item_name)).setText(feederPlanItem.getName());
            ((TextView) viewInflate.findViewById(R.id.feeder_plan_item_time)).setText(String.format("%02d:%02d", Integer.valueOf(feederPlanItem.getTime() / 3600), Integer.valueOf((feederPlanItem.getTime() % 3600) / 60)));
            ((TextView) viewInflate.findViewById(R.id.feeder_plan_item_amount)).setText(FeederUtils.getAmountFormat(feederPlanItem.getAmount()) + getString(FeederUtils.getFeederAmountUnit(feederPlanItem.getAmount())));
            viewInflate.setTag(feederPlanItem);
            viewInflate.setOnClickListener(this);
            linearLayout.addView(viewInflate);
        }
    }

    private void updateFeederPlanItem(FeederPlanItem feederPlanItem, boolean z) {
        if (this.mFeederPlan.getItems() == null) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(feederPlanItem);
            this.mFeederPlan.setItems(arrayList);
        } else {
            Iterator<FeederPlanItem> it = this.mFeederPlan.getItems().iterator();
            while (true) {
                if (it.hasNext()) {
                    FeederPlanItem next = it.next();
                    if (feederPlanItem.getId() == next.getId()) {
                        this.mFeederPlan.getItems().remove(next);
                        if (!z) {
                            this.mFeederPlan.getItems().add(feederPlanItem);
                        }
                    }
                } else if (!z) {
                    this.mFeederPlan.getItems().add(feederPlanItem);
                }
            }
            Collections.sort(this.mFeederPlan.getItems(), new Comparator<FeederPlanItem>() { // from class: com.petkit.android.activities.feeder.setting.FeederPlanEditActivity.2
                @Override // java.util.Comparator
                public int compare(FeederPlanItem feederPlanItem2, FeederPlanItem feederPlanItem3) {
                    return feederPlanItem2.getTime() < feederPlanItem3.getTime() ? -1 : 1;
                }
            });
        }
        FeederPlan feederPlan = this.mFeederPlan;
        feederPlan.setCount(feederPlan.getItems().size());
        Iterator<FeederPlanItem> it2 = this.mFeederPlan.getItems().iterator();
        int amount = 0;
        while (it2.hasNext()) {
            amount += it2.next().getAmount();
        }
        this.mFeederPlan.setTotalAmount(amount);
    }

    private void saveFeederPlan() {
        HashMap map = new HashMap();
        map.put("deviceId", String.valueOf(this.deviceId));
        map.put(FirebaseAnalytics.Param.ITEMS, new Gson().toJson(this.mFeederPlan.getItems()));
        map.put("repeats", this.mFeederPlan.getRepeats());
        post(ApiTools.SAMPLET_API_FEEDER_SAVE_FEED, map, new AsyncHttpRespHandler(this, true) { // from class: com.petkit.android.activities.feeder.setting.FeederPlanEditActivity.3
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                ResultStringRsp resultStringRsp = (ResultStringRsp) this.gson.fromJson(this.responseResult, ResultStringRsp.class);
                if (resultStringRsp.getError() == null) {
                    FeederUtils.getFeederRecordByDeviceId(FeederPlanEditActivity.this.deviceId);
                    FeederUtils.saveFeederPlanForDeviceId(FeederPlanEditActivity.this.deviceId, FeederPlanEditActivity.this.mFeederPlan);
                    Intent intent = new Intent(FeederUtils.BROADCAST_FEEDER_PLAN_CHANGED);
                    intent.putExtra(FeederUtils.EXTRA_FEEDER_ID, FeederPlanEditActivity.this.deviceId);
                    LocalBroadcastManager.getInstance(FeederPlanEditActivity.this).sendBroadcast(intent);
                    FeederPlanEditActivity.this.complete();
                    return;
                }
                FeederPlanEditActivity.this.showShortToast(resultStringRsp.getError().getMsg(), R.drawable.toast_failed);
            }
        }, false);
    }

    @Override // com.petkit.android.activities.base.BaseActivity
    public void cancel(View view) {
        if (this.isPlanEdited) {
            showCancelDialog();
        } else {
            super.cancel(view);
        }
    }

    @Override // androidx.core.app.ComponentActivity, android.app.Activity, android.view.Window.Callback
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == 4 && keyEvent.getAction() == 0) {
            if (this.isPlanEdited) {
                showCancelDialog();
                return true;
            }
            if (this.isFirstInit) {
                return super.dispatchKeyEvent(keyEvent);
            }
            complete();
            return true;
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void complete() {
        if (this.isFirstInit) {
            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(FeederUtils.BROADCAST_FEEDER_BIND_COMPLETE));
        }
        finish();
    }

    private void showCancelDialog() {
        new AlertDialog.Builder(this).setCancelable(false).setTitle(R.string.Prompt).setMessage(R.string.Feeder_plan_has_edited_prompt).setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() { // from class: com.petkit.android.activities.feeder.setting.FeederPlanEditActivity.5
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                FeederPlanEditActivity.this.finish();
            }
        }).setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() { // from class: com.petkit.android.activities.feeder.setting.FeederPlanEditActivity.4
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).show();
    }
}

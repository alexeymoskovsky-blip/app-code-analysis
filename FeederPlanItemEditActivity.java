package com.petkit.android.activities.feeder.setting;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.petkit.android.activities.base.BaseActivity;
import com.petkit.android.activities.feed.widget.ScaleSelectGramWindow;
import com.petkit.android.activities.feeder.Utils.FeederUtils;
import com.petkit.android.activities.feeder.model.FeederPlan;
import com.petkit.android.activities.feeder.model.FeederPlanItem;
import com.petkit.android.activities.feeder.model.FeederRecord;
import com.petkit.android.activities.feeder.widget.FeederScaleSelectWindow;
import com.petkit.android.activities.petkitBleDevice.utils.BleDeviceUtils;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.android.widget.windows.TimePickerWindow;
import com.petkit.oversea.R;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes3.dex */
public class FeederPlanItemEditActivity extends BaseActivity implements TimePickerWindow.onTimeSelectListener, FeederScaleSelectWindow.onFeederScaleResultListener, ScaleSelectGramWindow.onScaleResultListener {
    FeederRecord feederRecord;
    private boolean isFirstInit;
    private long mDeviceId;
    private FeederPlan mFeederPlan;
    private FeederPlanItem mFeederPlanItem;

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            this.mFeederPlanItem = (FeederPlanItem) bundle.getSerializable(FeederUtils.EXTRA_FEEDER_PLAN_ITEM);
            this.mFeederPlan = (FeederPlan) bundle.getSerializable(FeederUtils.EXTRA_FEEDER_PLAN);
            this.isFirstInit = bundle.getBoolean(Constants.EXTRA_BOOLEAN);
            this.mDeviceId = bundle.getLong(FeederUtils.EXTRA_FEEDER_ID);
        } else {
            this.mFeederPlanItem = (FeederPlanItem) getIntent().getSerializableExtra(FeederUtils.EXTRA_FEEDER_PLAN_ITEM);
            this.mFeederPlan = (FeederPlan) getIntent().getSerializableExtra(FeederUtils.EXTRA_FEEDER_PLAN);
            this.isFirstInit = getIntent().getBooleanExtra(Constants.EXTRA_BOOLEAN, false);
            this.mDeviceId = getIntent().getLongExtra(FeederUtils.EXTRA_FEEDER_ID, 0L);
        }
        this.feederRecord = FeederUtils.getFeederRecordByDeviceId(this.mDeviceId);
        setContentView(R.layout.activity_feeder_plan_item_edit);
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putSerializable(FeederUtils.EXTRA_FEEDER_PLAN_ITEM, this.mFeederPlanItem);
        bundle.putSerializable(FeederUtils.EXTRA_FEEDER_PLAN, this.mFeederPlan);
        bundle.putBoolean(Constants.EXTRA_BOOLEAN, this.isFirstInit);
        bundle.putLong(FeederUtils.EXTRA_FEEDER_ID, this.mDeviceId);
    }

    @Override // com.petkit.android.activities.base.BaseActivity
    public void setupViews() {
        setNoTitle();
        ((TextView) findViewById(R.id.tv_title)).setText(this.mFeederPlanItem == null ? R.string.Feeder_plan_item_add : R.string.Feeder_plan_item_edit);
        findViewById(R.id.toolbar_right_text).setOnClickListener(this);
        findViewById(R.id.toolbar_back).setOnClickListener(this);
        if (this.mFeederPlanItem != null) {
            ((TextView) findViewById(R.id.feeder_plan_item_time)).setText(String.format("%02d:%02d", Integer.valueOf(this.mFeederPlanItem.getTime() / 3600), Integer.valueOf((this.mFeederPlanItem.getTime() % 3600) / 60)));
            EditText editText = (EditText) findViewById(R.id.feeder_plan_item_tag);
            editText.setText(this.mFeederPlanItem.getName());
            editText.setSelection(editText.getEditableText().toString().length());
            ((TextView) findViewById(R.id.feeder_plan_item_amount)).setText(FeederUtils.getAmountFormat(this.mFeederPlanItem.getAmount()) + getString(FeederUtils.getFeederAmountUnit(this.mFeederPlanItem.getAmount())));
            findViewById(R.id.feeder_plan_item_remove).setOnClickListener(this);
        } else {
            findViewById(R.id.feeder_plan_item_remove).setVisibility(8);
            FeederPlanItem feederPlanItem = new FeederPlanItem();
            this.mFeederPlanItem = feederPlanItem;
            feederPlanItem.setId(FeederUtils.generateFeederPlanItemId());
        }
        FeederRecord feederRecordByDeviceId = FeederUtils.getFeederRecordByDeviceId(this.mDeviceId);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.rl_timezone);
        if (feederRecordByDeviceId == null || CommonUtils.isSameTimeZoneAsLocal(feederRecordByDeviceId.getLocale(), feederRecordByDeviceId.getTimezone())) {
            relativeLayout.setVisibility(8);
        } else {
            relativeLayout.setVisibility(0);
        }
        findViewById(R.id.feeder_plan_item_time_view).setOnClickListener(this);
        findViewById(R.id.feeder_plan_item_amount_view).setOnClickListener(this);
        findViewById(R.id.feeder_plan_item_device_view).setOnClickListener(this);
    }

    @Override // com.petkit.android.activities.base.BaseActivity, android.view.View.OnClickListener
    public void onClick(View view) {
        super.onClick(view);
        int id = view.getId();
        if (id == R.id.feeder_plan_item_time_view) {
            TimePickerWindow timePickerWindow = new TimePickerWindow(this, true, this);
            timePickerWindow.setTime(this.mFeederPlanItem.getTime(), this.feederRecord.getActualTimeZone());
            timePickerWindow.show(getWindow().getDecorView());
            return;
        }
        if (id == R.id.feeder_plan_item_amount_view) {
            if (this.feederRecord.getDeviceId() != 0) {
                ScaleSelectGramWindow scaleSelectGramWindow = new ScaleSelectGramWindow(this, true, BleDeviceUtils.getDeviceColor(4), this);
                scaleSelectGramWindow.setDefaultScale(this.mFeederPlanItem.getAmount());
                scaleSelectGramWindow.show(getWindow().getDecorView());
                return;
            } else {
                FeederScaleSelectWindow feederScaleSelectWindow = new FeederScaleSelectWindow(this, true, BleDeviceUtils.getDeviceColor(4), this);
                feederScaleSelectWindow.setBackgroundDrawable(new BitmapDrawable());
                feederScaleSelectWindow.setDefaultScale(this.mFeederPlanItem.getAmount() / 20);
                feederScaleSelectWindow.show(getWindow().getDecorView());
                return;
            }
        }
        if (id == R.id.title_right_btn) {
            if (this.mFeederPlanItem.getAmount() <= 0) {
                showShortToast(R.string.Hint_set_feeder_plan_amount_null);
                return;
            }
            String string = ((EditText) findViewById(R.id.feeder_plan_item_tag)).getEditableText().toString();
            if (isEmpty(string)) {
                showShortToast(R.string.Hint_set_feeder_plan_tag_null);
                return;
            }
            this.mFeederPlanItem.setName(string);
            if (this.mFeederPlanItem.getTime() < 0) {
                showShortToast(R.string.Hint_set_feeder_plan_time_null);
                return;
            }
            if (!FeederUtils.checkFeederPlanItemValid(this.mFeederPlan, this.mFeederPlanItem)) {
                showShortToast(R.string.Hint_set_feeder_plan_time_invalid);
                return;
            }
            Intent intent = new Intent();
            intent.putExtra(FeederUtils.EXTRA_FEEDER_PLAN_ITEM, this.mFeederPlanItem);
            if (this.isFirstInit) {
                this.mFeederPlan.setItems(new ArrayList());
                this.mFeederPlan.getItems().add(this.mFeederPlanItem);
                this.mFeederPlan.setCount(1);
                this.mFeederPlan.setTotalAmount(this.mFeederPlanItem.getAmount());
                intent.putExtra(Constants.EXTRA_BOOLEAN, this.isFirstInit);
                intent.putExtra(FeederUtils.EXTRA_FEEDER_PLAN, this.mFeederPlan);
                intent.putExtra(FeederUtils.EXTRA_FEEDER_ID, this.mDeviceId);
                intent.setClass(this, FeederPlanEditActivity.class);
                startActivity(intent);
                finish();
                return;
            }
            setResult(-1, intent);
            finish();
            return;
        }
        if (id == R.id.feeder_plan_item_remove) {
            showDeleteDialog();
            return;
        }
        if (id == R.id.feeder_plan_item_device_view) {
            ArrayList arrayList = new ArrayList();
            arrayList.add("喂食器1");
            arrayList.add("喂食器2");
            arrayList.add("喂食器3");
            OptionsPickerView optionsPickerViewBuild = new OptionsPickerBuilder(this, new OnOptionsSelectListener() { // from class: com.petkit.android.activities.feeder.setting.FeederPlanItemEditActivity.1
                @Override // com.bigkoo.pickerview.listener.OnOptionsSelectListener
                public void onOptionsSelect(int i, int i2, int i3, View view2) {
                }
            }).setCancelText(getString(R.string.Cancel)).setSubmitText(getString(R.string.Confirm)).setTitleBgColor(getResources().getColor(R.color.white)).setCancelColor(getResources().getColor(R.color.feeder_main_color)).setSubmitColor(getResources().getColor(R.color.feeder_main_color)).build();
            optionsPickerViewBuild.setPicker(arrayList, null, null);
            optionsPickerViewBuild.show();
        }
    }

    @Override // com.petkit.android.widget.windows.TimePickerWindow.onTimeSelectListener
    public void onTimeSelect(int i) {
        ((TextView) findViewById(R.id.feeder_plan_item_time)).setText(String.format("%02d:%02d", Integer.valueOf(i / 3600), Integer.valueOf((i % 3600) / 60)));
        this.mFeederPlanItem.setTime(i);
        EditText editText = (EditText) findViewById(R.id.feeder_plan_item_tag);
        if (isEmpty(editText.getEditableText().toString())) {
            editText.setText(getDefaultFeederPlanItemNameByTime(i));
            editText.setSelection(editText.getEditableText().toString().length());
        }
    }

    @Override // com.petkit.android.activities.feeder.widget.FeederScaleSelectWindow.onFeederScaleResultListener, com.petkit.android.activities.petkitBleDevice.d4.widget.D4ScaleSelectWindow.onFeederScaleResultListener
    public void onFeederScaleResult(int i) {
        ((TextView) findViewById(R.id.feeder_plan_item_amount)).setText(FeederUtils.getAmountFormat(i) + getString(FeederUtils.getFeederAmountUnit(i)));
        this.mFeederPlanItem.setAmount(i);
    }

    private void showDeleteDialog() {
        new AlertDialog.Builder(this).setCancelable(false).setTitle(R.string.Prompt).setMessage(R.string.Hint_delete_feeder_plan_item).setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() { // from class: com.petkit.android.activities.feeder.setting.FeederPlanItemEditActivity.3
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent();
                intent.putExtra(Constants.EXTRA_BOOLEAN, true);
                intent.putExtra(FeederUtils.EXTRA_FEEDER_PLAN_ITEM, FeederPlanItemEditActivity.this.mFeederPlanItem);
                FeederPlanItemEditActivity.this.setResult(-1, intent);
                FeederPlanItemEditActivity.this.finish();
            }
        }).setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() { // from class: com.petkit.android.activities.feeder.setting.FeederPlanItemEditActivity.2
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).show();
    }

    private String getDefaultFeederPlanItemNameByTime(int i) {
        if (i > 0 && i <= 32400) {
            return getString(R.string.Breakfast);
        }
        if (i > 32400 && i <= 57600) {
            return getString(R.string.Lunch);
        }
        return getString(R.string.Dinner);
    }

    @Override // com.petkit.android.activities.feed.widget.ScaleSelectGramWindow.onScaleResultListener, com.petkit.android.activities.petkitBleDevice.d3.widget.D3ScaleSelectGramWindow.onScaleResultListener
    public void onScaleResult(int i) {
        ((TextView) findViewById(R.id.feeder_plan_item_amount)).setText(i + getResources().getString(R.string.Unit_g));
        this.mFeederPlanItem.setAmount(i);
    }
}

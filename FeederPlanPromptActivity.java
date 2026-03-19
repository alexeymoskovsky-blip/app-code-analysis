package com.petkit.android.activities.feeder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.petkit.android.activities.base.BaseActivity;
import com.petkit.android.activities.base.BaseFragment;
import com.petkit.android.activities.feeder.Utils.FeederUtils;
import com.petkit.android.activities.feeder.model.FeederRecord;
import com.petkit.android.activities.feeder.setting.FeederSettingActivity;
import com.petkit.android.utils.Constants;
import com.petkit.android.widget.HorizonalCircleProgress;
import com.petkit.oversea.R;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class FeederPlanPromptActivity extends BaseActivity implements IFeederPromptListener {
    private FragmentManager fragmentManager;
    private boolean isBind = false;
    private BroadcastReceiver mBroadcastReceiver;
    private FeederRecord mFeederRecord;
    private HorizonalCircleProgress progress;

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        long longExtra;
        super.onCreate(bundle);
        if (bundle != null) {
            longExtra = bundle.getLong(FeederUtils.EXTRA_FEEDER_ID);
            this.isBind = bundle.getBoolean(Constants.EXTRA_BOOLEAN);
        } else {
            longExtra = getIntent().getLongExtra(FeederUtils.EXTRA_FEEDER_ID, 0L);
            this.isBind = getIntent().getBooleanExtra(Constants.EXTRA_BOOLEAN, false);
        }
        this.mFeederRecord = FeederUtils.getFeederRecordByDeviceId(longExtra);
        setContentView(R.layout.activity_feeder_plan_prompt);
        registerBoradcastReceiver();
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        unregisterBroadcastReceiver();
    }

    @Override // com.petkit.android.activities.base.BaseActivity
    public void setupViews() {
        setTitleRightImageView(R.drawable.btn_setting, this);
        this.fragmentManager = getSupportFragmentManager();
        HorizonalCircleProgress horizonalCircleProgress = (HorizonalCircleProgress) findViewById(R.id.circle_line);
        this.progress = horizonalCircleProgress;
        horizonalCircleProgress.setTotalSteps(2);
        this.progress.setVisibility(FeederUtils.getFeederPlanForDeviceId(this.mFeederRecord.getDeviceId()) == null ? 0 : 8);
        setTitle(R.string.Feeder_plan);
        enterInFragment(new FeederPlanPromptFragment());
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putLong(FeederUtils.EXTRA_FEEDER_ID, this.mFeederRecord.getDeviceId());
        bundle.putBoolean(Constants.EXTRA_BOOLEAN, this.isBind);
    }

    @Override // com.petkit.android.activities.base.BaseActivity, android.view.View.OnClickListener
    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.title_right_image) {
            Bundle bundle = new Bundle();
            bundle.putLong(FeederUtils.EXTRA_FEEDER_ID, this.mFeederRecord.getDeviceId());
            startActivityWithData(FeederSettingActivity.class, bundle, false);
        }
    }

    private void processBackEvent() {
        this.fragmentManager.popBackStackImmediate();
        if (getCurrentFragment() == null) {
            finish();
        }
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        processBackEvent();
    }

    @Override // com.petkit.android.activities.base.BaseActivity
    public void cancel(View view) {
        processBackEvent();
    }

    private void enterInFragment(Fragment fragment) {
        Bundle bundle = new Bundle();
        bundle.putLong(FeederUtils.EXTRA_FEEDER_ID, this.mFeederRecord.getDeviceId());
        bundle.putBoolean(Constants.EXTRA_BOOLEAN, this.isBind);
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransactionBeginTransaction = this.fragmentManager.beginTransaction();
        fragmentTransactionBeginTransaction.setCustomAnimations(R.anim.push_right_in, R.anim.push_left_out);
        fragmentTransactionBeginTransaction.add(R.id.content_fragment, fragment, String.valueOf(1));
        fragmentTransactionBeginTransaction.addToBackStack(null);
        fragmentTransactionBeginTransaction.commit();
    }

    private BaseFragment getCurrentFragment() {
        List<Fragment> fragments = this.fragmentManager.getFragments();
        for (int size = fragments.size() - 1; size > 0; size--) {
            Fragment fragment = fragments.get(size);
            if (fragment != null && fragment.isVisible() && (fragment instanceof BaseFragment)) {
                return (BaseFragment) fragment;
            }
        }
        return null;
    }

    private void registerBoradcastReceiver() {
        this.mBroadcastReceiver = new BroadcastReceiver() { // from class: com.petkit.android.activities.feeder.FeederPlanPromptActivity.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                action.hashCode();
                if (action.equals(FeederUtils.BROADCAST_FEEDER_DELETE)) {
                    FeederPlanPromptActivity.this.finish();
                    return;
                }
                if (action.equals(FeederUtils.BROADCAST_FEEDER_BIND_COMPLETE)) {
                    if (FeederPlanPromptActivity.this.isBind) {
                        FeederPlanPromptActivity.this.finish();
                        return;
                    }
                    Bundle bundle = new Bundle();
                    bundle.putLong(FeederUtils.EXTRA_FEEDER_ID, FeederPlanPromptActivity.this.mFeederRecord.getDeviceId());
                    FeederPlanPromptActivity.this.startActivityWithData(FeederHomeActivity.class, bundle, true);
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(FeederUtils.BROADCAST_FEEDER_BIND_COMPLETE);
        intentFilter.addAction(FeederUtils.BROADCAST_FEEDER_DELETE);
        LocalBroadcastManager.getInstance(this).registerReceiver(this.mBroadcastReceiver, intentFilter);
    }

    private void unregisterBroadcastReceiver() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(this.mBroadcastReceiver);
    }

    @Override // com.petkit.android.activities.feeder.IFeederPromptListener
    public void onNext() {
        if (FeederUtils.getFeederPlanForDeviceId(this.mFeederRecord.getDeviceId()) == null) {
            setTitle(R.string.Feeder_plan);
            this.progress.setCurrentStep(2);
            enterInFragment(new FeederPlanPromptFragment());
        } else {
            if (this.isBind) {
                finish();
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putLong(FeederUtils.EXTRA_FEEDER_ID, this.mFeederRecord.getDeviceId());
            startActivityWithData(FeederHomeActivity.class, bundle, true);
        }
    }
}

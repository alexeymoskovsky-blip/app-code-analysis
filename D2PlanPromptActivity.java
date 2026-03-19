package com.petkit.android.activities.d2.food;

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
import com.petkit.android.activities.d2.D2HomeActivity;
import com.petkit.android.activities.d2.D2PlanPromptFragment;
import com.petkit.android.activities.d2.D2SettingActivity;
import com.petkit.android.activities.d2.mode.D2Record;
import com.petkit.android.activities.d2.utils.D2Utils;
import com.petkit.android.activities.feeder.IFeederPromptListener;
import com.petkit.android.activities.feeder.Utils.FeederUtils;
import com.petkit.android.utils.Constants;
import com.petkit.android.widget.HorizonalCircleProgress;
import com.petkit.oversea.R;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class D2PlanPromptActivity extends BaseActivity implements IFeederPromptListener {
    private long deviceId;
    private FragmentManager fragmentManager;
    private boolean isBind = false;
    private BroadcastReceiver mBroadcastReceiver;
    private D2Record mD2Record;
    private HorizonalCircleProgress progress;

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            this.deviceId = bundle.getLong(Constants.EXTRA_DEVICE_ID);
            this.isBind = bundle.getBoolean(Constants.EXTRA_BOOLEAN);
        } else {
            this.deviceId = getIntent().getLongExtra(Constants.EXTRA_DEVICE_ID, 0L);
            this.isBind = getIntent().getBooleanExtra(Constants.EXTRA_BOOLEAN, false);
        }
        this.mD2Record = D2Utils.getD2RecordByDeviceId(this.deviceId);
        setContentView(R.layout.activity_feeder_plan_prompt);
        registerBoradcastReceiver();
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        if (D2Utils.getD2PlanForDeviceId(this.deviceId) == null && this.mD2Record.getShared() == null) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.EXTRA_DEVICE_ID, Long.valueOf(this.deviceId));
        startActivityWithData(D2HomeActivity.class, bundle, false);
        finish();
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
        horizonalCircleProgress.setCircleDrawable(R.drawable.little_circle_green);
        this.progress.setTotalSteps(2);
        this.progress.setVisibility(D2Utils.getD2PlanForDeviceId(this.mD2Record.getDeviceId()) == null ? 0 : 8);
        setTitle(R.string.Pet_feed_food);
        enterInFragment(new D2PlanPromptFragment());
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putLong(Constants.EXTRA_DEVICE_ID, this.mD2Record.getDeviceId());
        bundle.putBoolean(Constants.EXTRA_BOOLEAN, this.isBind);
    }

    @Override // com.petkit.android.activities.base.BaseActivity, android.view.View.OnClickListener
    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.title_right_image) {
            Bundle bundle = new Bundle();
            bundle.putLong(Constants.EXTRA_DEVICE_ID, this.mD2Record.getDeviceId());
            startActivityWithData(D2SettingActivity.class, bundle, false);
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
        bundle.putLong(Constants.EXTRA_DEVICE_ID, this.mD2Record.getDeviceId());
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

    /* JADX INFO: renamed from: com.petkit.android.activities.d2.food.D2PlanPromptActivity$1 */
    public class AnonymousClass1 extends BroadcastReceiver {
        public AnonymousClass1() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            action.hashCode();
            if (!action.equals(D2Utils.BROADCAST_D2_BIND_COMPLETE)) {
                if (action.equals(D2Utils.BROADCAST_D2_DELETE)) {
                    D2PlanPromptActivity.this.finish();
                }
            } else {
                if (D2PlanPromptActivity.this.isBind) {
                    D2PlanPromptActivity.this.finish();
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putLong(Constants.EXTRA_DEVICE_ID, D2PlanPromptActivity.this.mD2Record.getDeviceId());
                D2PlanPromptActivity.this.startActivityWithData(D2HomeActivity.class, bundle, true);
            }
        }
    }

    private void registerBoradcastReceiver() {
        this.mBroadcastReceiver = new BroadcastReceiver() { // from class: com.petkit.android.activities.d2.food.D2PlanPromptActivity.1
            public AnonymousClass1() {
            }

            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                action.hashCode();
                if (!action.equals(D2Utils.BROADCAST_D2_BIND_COMPLETE)) {
                    if (action.equals(D2Utils.BROADCAST_D2_DELETE)) {
                        D2PlanPromptActivity.this.finish();
                    }
                } else {
                    if (D2PlanPromptActivity.this.isBind) {
                        D2PlanPromptActivity.this.finish();
                        return;
                    }
                    Bundle bundle = new Bundle();
                    bundle.putLong(Constants.EXTRA_DEVICE_ID, D2PlanPromptActivity.this.mD2Record.getDeviceId());
                    D2PlanPromptActivity.this.startActivityWithData(D2HomeActivity.class, bundle, true);
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(D2Utils.BROADCAST_D2_BIND_COMPLETE);
        intentFilter.addAction(D2Utils.BROADCAST_D2_DELETE);
        LocalBroadcastManager.getInstance(this).registerReceiver(this.mBroadcastReceiver, intentFilter);
    }

    private void unregisterBroadcastReceiver() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(this.mBroadcastReceiver);
    }

    @Override // com.petkit.android.activities.feeder.IFeederPromptListener
    public void onNext() {
        if (FeederUtils.getFeederPlanForDeviceId(this.mD2Record.getDeviceId()) == null) {
            setTitle(R.string.Feeder_plan);
            this.progress.setCurrentStep(2);
            enterInFragment(new D2PlanPromptFragment());
        } else {
            if (this.isBind) {
                finish();
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putLong(Constants.EXTRA_DEVICE_ID, this.mD2Record.getDeviceId());
            startActivityWithData(D2HomeActivity.class, bundle, true);
        }
    }
}

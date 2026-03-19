package com.petkit.android.activities.feeder.bind;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.petkit.android.activities.base.BaseActivity;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.cozy.event.BindResultEvent;
import com.petkit.android.activities.device.DeviceSetInfoActivity;
import com.petkit.android.activities.feeder.Utils.FeederUtils;
import com.petkit.android.activities.feeder.model.FeederRecord;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.oversea.R;
import org.simple.eventbus.EventBus;

/* JADX INFO: loaded from: classes3.dex */
public class FeederBindCompleteActivity extends BaseActivity {
    long id;
    private boolean isBindFeeder;
    private BroadcastReceiver mBroadcastReceiver;
    private FeederRecord mFeederRecord;

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            this.id = bundle.getLong(FeederUtils.EXTRA_FEEDER_ID);
            this.isBindFeeder = bundle.getBoolean(Constants.EXTRA_BOOLEAN);
        } else {
            this.id = getIntent().getLongExtra(FeederUtils.EXTRA_FEEDER_ID, 0L);
            this.isBindFeeder = getIntent().getBooleanExtra(Constants.EXTRA_BOOLEAN, false);
        }
        this.mFeederRecord = FeederUtils.getFeederRecordByDeviceId(this.id);
        setContentView(R.layout.activity_feeder_bind_complete);
        registerBoradcastReceiver();
    }

    @Override // com.petkit.android.activities.base.BaseActivity
    public void setupViews() {
        setTitle(R.string.Title_bind_complete);
        setNoTitleLeftButton();
        int statusHeight = (int) (((double) ((BaseApplication.displayMetrics.heightPixels - CommonUtils.getStatusHeight()) - (getResources().getDimension(R.dimen.base_titleheight) * 2.0f))) * 0.8d);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.feeder_bind_start_complete_parent);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) frameLayout.getLayoutParams();
        layoutParams.height = statusHeight;
        frameLayout.setLayoutParams(layoutParams);
        TextView textView = (TextView) findViewById(R.id.feeder_bind_complete);
        if ((this.isBindFeeder && (FeederUtils.getFeederPlanForDeviceId(this.mFeederRecord.getDeviceId()) == null || (this.mFeederRecord.getPet().getFood() == null && this.mFeederRecord.getPet().getPrivateFood() == null))) || (this.mFeederRecord.getContract() != null && (this.mFeederRecord.getContract().getActive() == -1 || this.mFeederRecord.getContract().getLinkedMini() == 0))) {
            textView.setText(R.string.Next);
        } else {
            textView.setText(R.string.Done);
        }
        findViewById(R.id.feeder_bind_complete).setOnClickListener(this);
        if (this.mFeederRecord != null) {
            EventBus.getDefault().post(new BindResultEvent(1, this.isBindFeeder ? this.mFeederRecord.getPetId() : null));
        } else {
            EventBus.getDefault().post(new BindResultEvent(1, null));
        }
    }

    @Override // com.petkit.android.activities.base.BaseActivity, android.view.View.OnClickListener
    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.feeder_bind_complete) {
            if (this.isBindFeeder) {
                startActivity(DeviceSetInfoActivity.newIntent(this, this.mFeederRecord.getDeviceId(), 4, true));
            }
            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(Constants.BROADCAST_MSG_DEVICE_UPDATE));
            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(FeederUtils.BROADCAST_FEEDER_BIND_COMPLETE));
            finish();
        }
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putLong(FeederUtils.EXTRA_FEEDER_ID, this.mFeederRecord.getDeviceId());
        bundle.putBoolean(Constants.EXTRA_BOOLEAN, this.isBindFeeder);
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        unregisterBroadcastReceiver();
    }

    private void registerBoradcastReceiver() {
        this.mBroadcastReceiver = new BroadcastReceiver() { // from class: com.petkit.android.activities.feeder.bind.FeederBindCompleteActivity.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                action.hashCode();
                if (action.equals(FeederUtils.BROADCAST_FEEDER_BIND_COMPLETE)) {
                    FeederBindCompleteActivity.this.finish();
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(FeederUtils.BROADCAST_FEEDER_BIND_COMPLETE);
        LocalBroadcastManager.getInstance(this).registerReceiver(this.mBroadcastReceiver, intentFilter);
    }

    private void unregisterBroadcastReceiver() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(this.mBroadcastReceiver);
    }
}

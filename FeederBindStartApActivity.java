package com.petkit.android.activities.feeder.bind;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.petkit.android.activities.base.BaseActivity;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.feeder.Utils.FeederUtils;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.LogcatStorageHelper;
import com.petkit.oversea.R;

/* JADX INFO: loaded from: classes3.dex */
public class FeederBindStartApActivity extends BaseActivity {
    private BroadcastReceiver mBroadcastReceiver;
    private long mDeviceId;
    private TextView mNextTextView;
    private int mWaitSecond;
    private String password;
    private String ssid;

    public static /* synthetic */ int access$010(FeederBindStartApActivity feederBindStartApActivity) {
        int i = feederBindStartApActivity.mWaitSecond;
        feederBindStartApActivity.mWaitSecond = i - 1;
        return i;
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            this.ssid = bundle.getString("ssid");
            this.password = bundle.getString("password");
            this.mDeviceId = bundle.getLong(FeederUtils.EXTRA_FEEDER_ID);
        } else {
            this.ssid = getIntent().getStringExtra("ssid");
            this.password = getIntent().getStringExtra("password");
            this.mDeviceId = getIntent().getLongExtra(FeederUtils.EXTRA_FEEDER_ID, 0L);
        }
        setContentView(R.layout.activity_feeder_bind_start_ap);
        LogcatStorageHelper.addLog("[Feeder Bind] start ap");
        registerBoradcastReceiver();
    }

    @Override // com.petkit.android.activities.base.BaseActivity
    public void setupViews() {
        setTitle(R.string.Title_feeder_bind_start_ap);
        int statusHeight = (int) (((double) ((BaseApplication.displayMetrics.heightPixels - CommonUtils.getStatusHeight()) - (getResources().getDimension(R.dimen.base_titleheight) * 2.0f))) * 0.8d);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.feeder_bind_start_ap_parent);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) frameLayout.getLayoutParams();
        layoutParams.height = statusHeight;
        frameLayout.setLayoutParams(layoutParams);
        TextView textView = (TextView) findViewById(R.id.feeder_bind_prompt);
        String string = getString(R.string.Double_click);
        String string2 = getString(R.string.Feeder_bind_start_ap_prompt_format, string);
        SpannableString spannableString = new SpannableString(string2);
        spannableString.setSpan(new ForegroundColorSpan(CommonUtils.getColorById(R.color.orange)), string2.indexOf(string), string2.indexOf(string) + string.length(), 33);
        textView.setText(spannableString);
        ImageView imageView = (ImageView) findViewById(R.id.feeder_bind_start_ap_anim);
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) imageView.getLayoutParams();
        layoutParams2.topMargin = (int) (((double) statusHeight) * 0.36d);
        layoutParams2.leftMargin = (int) (((double) BaseApplication.displayMetrics.widthPixels) * 0.5d);
        imageView.setLayoutParams(layoutParams2);
        imageView.setImageResource(R.drawable.feeder_bind_start_ap_anim);
        if (imageView.getDrawable() instanceof AnimationDrawable) {
            ((AnimationDrawable) imageView.getDrawable()).start();
        }
        TextView textView2 = (TextView) findViewById(R.id.feeder_bind_next);
        this.mNextTextView = textView2;
        textView2.setOnClickListener(this);
        this.mWaitSecond = 5;
        refreshNextTextView();
    }

    @Override // com.petkit.android.activities.base.BaseActivity, android.view.View.OnClickListener
    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.feeder_bind_next) {
            Bundle bundle = new Bundle();
            bundle.putString("ssid", this.ssid);
            bundle.putString("password", this.password);
            bundle.putLong(FeederUtils.EXTRA_FEEDER_ID, this.mDeviceId);
            startActivityWithData(FeederBindConnectApActivity.class, bundle, false);
        }
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putString("ssid", this.ssid);
        bundle.putString("password", this.password);
        bundle.putLong(FeederUtils.EXTRA_FEEDER_ID, this.mDeviceId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshNextTextView() {
        if (isFinishing()) {
            return;
        }
        if (this.mWaitSecond > 0) {
            this.mNextTextView.setText(getString(R.string.Feeder_bind_start_ap_next_prompt) + " " + this.mWaitSecond);
            this.mNextTextView.setBackgroundColor(CommonUtils.getColorById(R.color.gray));
            this.mNextTextView.setClickable(false);
            new Handler().postDelayed(new Runnable() { // from class: com.petkit.android.activities.feeder.bind.FeederBindStartApActivity.1
                @Override // java.lang.Runnable
                public void run() {
                    FeederBindStartApActivity.access$010(FeederBindStartApActivity.this);
                    FeederBindStartApActivity.this.refreshNextTextView();
                }
            }, 1000L);
            return;
        }
        this.mNextTextView.setText(getString(R.string.Feeder_bind_start_ap_next_prompt));
        this.mNextTextView.setBackgroundResource(R.drawable.selector_solid_feeder_main_color);
        this.mNextTextView.setClickable(true);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        super.onBackPressed();
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(FeederUtils.BROADCAST_FEEDER_BIND_FAILED));
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        unregisterBroadcastReceiver();
    }

    private void registerBoradcastReceiver() {
        this.mBroadcastReceiver = new BroadcastReceiver() { // from class: com.petkit.android.activities.feeder.bind.FeederBindStartApActivity.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                action.hashCode();
                if (action.equals(FeederUtils.BROADCAST_FEEDER_BIND_FAILED) || action.equals(FeederUtils.BROADCAST_FEEDER_BIND_COMPLETE)) {
                    FeederBindStartApActivity.this.finish();
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(FeederUtils.BROADCAST_FEEDER_BIND_COMPLETE);
        intentFilter.addAction(FeederUtils.BROADCAST_FEEDER_BIND_FAILED);
        LocalBroadcastManager.getInstance(this).registerReceiver(this.mBroadcastReceiver, intentFilter);
    }

    private void unregisterBroadcastReceiver() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(this.mBroadcastReceiver);
    }
}

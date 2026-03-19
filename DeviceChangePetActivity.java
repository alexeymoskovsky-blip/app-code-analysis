package com.petkit.android.activities.device;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.petkit.android.activities.base.BaseActivity;
import com.petkit.android.utils.Constants;
import com.petkit.oversea.R;

/* JADX INFO: loaded from: classes3.dex */
public class DeviceChangePetActivity extends BaseActivity {
    private DeviceBindPetFragment deviceFeedFragment;
    private long deviceId;
    private int deviceType;
    private boolean isBind = false;
    private boolean isSetting;

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            this.deviceId = bundle.getLong(Constants.EXTRA_DEVICE_ID);
            this.deviceType = bundle.getInt(Constants.EXTRA_DEVICE_TYPE);
            this.isBind = bundle.getBoolean(Constants.EXTRA_DEVICE_IS_BIND);
            this.isSetting = bundle.getBoolean(Constants.EXTRA_DEVICE_IS_SETTING);
        } else {
            this.deviceId = getIntent().getLongExtra(Constants.EXTRA_DEVICE_ID, 0L);
            this.deviceType = getIntent().getIntExtra(Constants.EXTRA_DEVICE_TYPE, 0);
            this.isBind = getIntent().getBooleanExtra(Constants.EXTRA_DEVICE_IS_BIND, false);
            this.isSetting = getIntent().getBooleanExtra(Constants.EXTRA_DEVICE_IS_SETTING, false);
        }
        setContentView(R.layout.activity_device_bind_pet);
    }

    public static Intent newIntent(Context context, long j, int i, boolean z, boolean z2) {
        Intent intent = new Intent(context, (Class<?>) DeviceChangePetActivity.class);
        intent.putExtra(Constants.EXTRA_DEVICE_ID, j);
        intent.putExtra(Constants.EXTRA_DEVICE_TYPE, i);
        intent.putExtra(Constants.EXTRA_DEVICE_IS_BIND, z);
        intent.putExtra(Constants.EXTRA_DEVICE_IS_SETTING, z2);
        return intent;
    }

    @Override // com.petkit.android.activities.base.BaseActivity
    public void setupViews() {
        if (this.isBind) {
            setTitle(R.string.Bind_pet);
        } else {
            setTitle(R.string.Change_pet);
        }
        setTitleLeftButton(R.drawable.btn_back_gray, new View.OnClickListener() { // from class: com.petkit.android.activities.device.DeviceChangePetActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DeviceChangePetActivity.this.onBackPressed();
            }
        });
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        this.deviceFeedFragment = new DeviceBindPetFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(Constants.EXTRA_DEVICE_ID, this.deviceId);
        bundle.putInt(Constants.EXTRA_DEVICE_TYPE, this.deviceType);
        bundle.putBoolean(Constants.EXTRA_DEVICE_IS_BIND, false);
        bundle.putBoolean(Constants.EXTRA_DEVICE_IS_SETTING, this.isSetting);
        this.deviceFeedFragment.setArguments(bundle);
        FragmentTransaction fragmentTransactionBeginTransaction = supportFragmentManager.beginTransaction();
        fragmentTransactionBeginTransaction.setCustomAnimations(R.anim.push_right_in, R.anim.push_left_out);
        fragmentTransactionBeginTransaction.add(R.id.content_fragment, this.deviceFeedFragment);
        fragmentTransactionBeginTransaction.commit();
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        DeviceBindPetFragment deviceBindPetFragment = this.deviceFeedFragment;
        if (deviceBindPetFragment != null) {
            deviceBindPetFragment.backPressed();
        } else {
            super.onBackPressed();
        }
    }
}

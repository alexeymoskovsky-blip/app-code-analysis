package com.petkit.android.activities.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.widget.imageloader.glide.GlideCircleTransform;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.petkit.android.activities.base.BaseActivity;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.cozy.CozyBindStartActivity;
import com.petkit.android.activities.cozy.utils.CozyUtils;
import com.petkit.android.activities.d2.D2BindStartActivity;
import com.petkit.android.activities.d2.utils.D2Utils;
import com.petkit.android.activities.feeder.Utils.FeederUtils;
import com.petkit.android.activities.feeder.bind.FeederBindStartActivity;
import com.petkit.android.activities.fit.PetkitScanActivity;
import com.petkit.android.activities.pet.PetCreateActivity;
import com.petkit.android.api.http.apiResponse.LoginRsp;
import com.petkit.android.model.Pet;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.oversea.R;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class DeviceAddPetSelectListActivity extends BaseActivity {
    private BroadcastReceiver mBroadcastReceiver;
    private int mDeviceType;
    private List<Pet> mDogs = new ArrayList();
    private List<Pet> mDogsWithoutDevice = new ArrayList();
    private int mSelectIndex = -1;
    private String petId;

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            this.petId = bundle.getString(Constants.EXTRA_PET_ID);
            this.mDeviceType = bundle.getInt(Constants.EXTRA_TYPE);
        } else {
            this.petId = getIntent().getStringExtra(Constants.EXTRA_PET_ID);
            this.mDeviceType = getIntent().getIntExtra(Constants.EXTRA_TYPE, 1);
        }
        setContentView(R.layout.activity_device_add_pets_select);
        registerBoradcastReceiver();
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt(Constants.EXTRA_TYPE, this.mDeviceType);
        bundle.putString(Constants.EXTRA_PET_ID, this.petId);
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        unregisterBroadcastReceiver();
    }

    @Override // com.petkit.android.activities.base.BaseActivity
    public void setupViews() {
        setTitle(this.mDeviceType == 1 ? R.string.Title_bind_fit : R.string.Title_bind_feeder);
        setTitleRightButton(R.string.Create, this);
        setTitleRightButtonTextColor(ArmsUtils.getColor(this, R.color.blue));
        setTitle(getTitleResIdByType());
        initDogs();
        initPetListView();
        findViewById(R.id.add_dog).setOnClickListener(this);
        findViewById(R.id.tv_next_step).setOnClickListener(this);
    }

    private int getTitleResIdByType() {
        int i = this.mDeviceType;
        if (i == 1) {
            return R.string.Title_bind_fit;
        }
        if (i == 4) {
            return R.string.Title_bind_feeder;
        }
        if (i == 5) {
            return R.string.Title_bind_cozy;
        }
        if (i == 6) {
            return R.string.Title_bind_d2;
        }
        throw new IllegalArgumentException("try to bind an unknown device type");
    }

    public void initDogs() {
        String petIdsHasD2;
        LoginRsp.LoginResult currentLoginResult = UserInforUtils.getCurrentLoginResult();
        if (currentLoginResult != null) {
            this.mDogsWithoutDevice.clear();
            this.mDogs.clear();
            int i = this.mDeviceType;
            if (i == 4) {
                petIdsHasD2 = FeederUtils.getPetIdsHasFeeder();
            } else if (i == 5) {
                petIdsHasD2 = CozyUtils.getPetIdsHasCozy();
            } else {
                petIdsHasD2 = i == 6 ? D2Utils.getPetIdsHasD2() : null;
            }
            for (Pet pet : currentLoginResult.getUser().getDogs(true)) {
                if ((this.mDeviceType == 1 && pet.getDevice() == null) || (petIdsHasD2 != null && !petIdsHasD2.contains(pet.getId()))) {
                    this.mDogsWithoutDevice.add(pet);
                } else {
                    this.mDogs.add(pet);
                }
            }
            if (this.mDogsWithoutDevice.size() > 0) {
                this.mSelectIndex = 0;
                for (int i2 = 0; i2 < this.mDogsWithoutDevice.size(); i2++) {
                    if (this.mDogsWithoutDevice.get(i2).getId().equals(this.petId)) {
                        this.mSelectIndex = i2;
                        return;
                    }
                }
            }
        }
    }

    public void initPetListView() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.bind_device_layout);
        LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.unbind_device_layout);
        LinearLayout linearLayout3 = (LinearLayout) findViewById(R.id.ll_no_pet);
        TextView textView = (TextView) findViewById(R.id.add_dog);
        TextView textView2 = (TextView) findViewById(R.id.tv_next_step);
        if (this.mSelectIndex == -1) {
            textView2.setVisibility(8);
        } else {
            textView2.setVisibility(0);
        }
        List<Pet> list = this.mDogs;
        if (list == null || list.size() == 0) {
            linearLayout.setVisibility(8);
            findViewById(R.id.bind_device_tv).setVisibility(8);
        } else {
            linearLayout.setVisibility(0);
            findViewById(R.id.bind_device_tv).setVisibility(0);
        }
        List<Pet> list2 = this.mDogsWithoutDevice;
        if (list2 == null || list2.size() == 0) {
            setTitleRightButtonVisibility(4);
            linearLayout3.setVisibility(0);
            textView.setVisibility(0);
            linearLayout2.setVisibility(8);
            findViewById(R.id.unbind_device_tv).setVisibility(8);
        } else {
            setTitleRightButtonVisibility(0);
            textView.setVisibility(8);
            linearLayout3.setVisibility(8);
            linearLayout2.setVisibility(0);
            findViewById(R.id.unbind_device_tv).setVisibility(0);
        }
        linearLayout.removeAllViews();
        for (int i = 0; i < this.mDogs.size(); i++) {
            linearLayout.addView(getPetCellView(this.mDogs.get(i), true, -2));
        }
        linearLayout2.removeAllViews();
        for (int i2 = 0; i2 < this.mDogsWithoutDevice.size(); i2++) {
            linearLayout2.addView(getPetCellView(this.mDogsWithoutDevice.get(i2), false, i2));
        }
    }

    private View getPetCellView(Pet pet, boolean z, int i) {
        View viewInflate = LayoutInflater.from(this).inflate(R.layout.layout_device_add_pet_cell, (ViewGroup) null);
        ImageView imageView = (ImageView) viewInflate.findViewById(R.id.dog_avatar);
        TextView textView = (TextView) viewInflate.findViewById(R.id.dog_name);
        ImageView imageView2 = (ImageView) viewInflate.findViewById(R.id.pet_select_state);
        ((BaseApplication) getApplication()).getAppComponent().imageLoader().loadImage(this, GlideImageConfig.builder().url(pet.getAvatar()).imageView(imageView).errorPic(pet.getType().getId() == 1 ? R.drawable.default_header_dog : R.drawable.default_header_cat).transformation(new GlideCircleTransform(this)).build());
        textView.setText(pet.getName());
        viewInflate.setBackgroundColor(CommonUtils.getColorById(z ? R.color.gray_pool : R.color.white));
        imageView2.setVisibility(this.mSelectIndex != i ? 4 : 0);
        if (!z) {
            viewInflate.setTag(Integer.valueOf(i));
            viewInflate.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.home.DeviceAddPetSelectListActivity$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$getPetCellView$0(view);
                }
            });
        }
        return viewInflate;
    }

    public /* synthetic */ void lambda$getPetCellView$0(View view) {
        int iIntValue = ((Integer) view.getTag()).intValue();
        if (this.mSelectIndex == iIntValue) {
            this.mSelectIndex = -1;
        } else {
            this.mSelectIndex = iIntValue;
        }
        initPetListView();
    }

    @Override // com.petkit.android.activities.base.BaseActivity, android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.title_right_btn || id == R.id.add_dog) {
            Bundle bundle = new Bundle();
            bundle.putBoolean(Constants.EXTRA_BOOLEAN, true);
            bundle.putInt(Constants.EXTRA_TYPE, this.mDeviceType);
            startActivityWithData(PetCreateActivity.class, bundle, false);
            return;
        }
        if (id != R.id.tv_next_step || this.mSelectIndex == -1) {
            return;
        }
        int i = this.mDeviceType;
        if (i == 1) {
            Bundle bundle2 = new Bundle();
            bundle2.putSerializable(Constants.EXTRA_DOG, this.mDogsWithoutDevice.get(this.mSelectIndex));
            startActivityWithData(PetkitScanActivity.class, bundle2, false);
            return;
        }
        if (i == 4) {
            Bundle bundle3 = new Bundle();
            bundle3.putSerializable(Constants.EXTRA_DOG, this.mDogsWithoutDevice.get(this.mSelectIndex));
            startActivityWithData(FeederBindStartActivity.class, bundle3, false);
        } else if (i == 5) {
            Bundle bundle4 = new Bundle();
            bundle4.putSerializable(Constants.EXTRA_DOG, this.mDogsWithoutDevice.get(this.mSelectIndex));
            startActivityWithData(CozyBindStartActivity.class, bundle4, false);
        } else {
            if (i != 6) {
                return;
            }
            Bundle bundle5 = new Bundle();
            bundle5.putSerializable(Constants.EXTRA_DOG, this.mDogsWithoutDevice.get(this.mSelectIndex));
            startActivityWithData(D2BindStartActivity.class, bundle5, false);
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.home.DeviceAddPetSelectListActivity$1 */
    public class AnonymousClass1 extends BroadcastReceiver {
        public AnonymousClass1() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            action.hashCode();
            switch (action) {
                case "BROADCAST_D2_BIND_COMPLETE":
                case "BROADCAST_FEEDER_BIND_COMPLETE":
                case "com.petkit.android.BROADCAST_MSG_DEVICE_BIND_FIT":
                    DeviceAddPetSelectListActivity.this.finish();
                    break;
                case "com.petkit.android.updateDog":
                    DeviceAddPetSelectListActivity.this.initDogs();
                    DeviceAddPetSelectListActivity.this.initPetListView();
                    break;
            }
        }
    }

    private void registerBoradcastReceiver() {
        this.mBroadcastReceiver = new BroadcastReceiver() { // from class: com.petkit.android.activities.home.DeviceAddPetSelectListActivity.1
            public AnonymousClass1() {
            }

            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                action.hashCode();
                switch (action) {
                    case "BROADCAST_D2_BIND_COMPLETE":
                    case "BROADCAST_FEEDER_BIND_COMPLETE":
                    case "com.petkit.android.BROADCAST_MSG_DEVICE_BIND_FIT":
                        DeviceAddPetSelectListActivity.this.finish();
                        break;
                    case "com.petkit.android.updateDog":
                        DeviceAddPetSelectListActivity.this.initDogs();
                        DeviceAddPetSelectListActivity.this.initPetListView();
                        break;
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.BROADCAST_MSG_UPDATE_DOG);
        intentFilter.addAction(Constants.BROADCAST_MSG_DEVICE_BIND_FIT);
        intentFilter.addAction(D2Utils.BROADCAST_D2_BIND_COMPLETE);
        intentFilter.addAction(FeederUtils.BROADCAST_FEEDER_BIND_COMPLETE);
        LocalBroadcastManager.getInstance(this).registerReceiver(this.mBroadcastReceiver, intentFilter);
    }

    private void unregisterBroadcastReceiver() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(this.mBroadcastReceiver);
    }
}

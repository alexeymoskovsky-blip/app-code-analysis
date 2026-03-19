package com.petkit.android.activities.pet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.google.gson.Gson;
import com.petkit.android.activities.base.BaseActivity;
import com.petkit.android.activities.base.fragment.BasePinnedHeaderListFragment;
import com.petkit.android.activities.base.fragment.BaseSwitchFragment;
import com.petkit.android.activities.cozy.CozyBindStartActivity;
import com.petkit.android.activities.d2.D2BindStartActivity;
import com.petkit.android.activities.feed.mode.RefreshFeedDataEvent;
import com.petkit.android.activities.feeder.bind.FeederBindStartActivity;
import com.petkit.android.activities.fit.PetkitScanActivity;
import com.petkit.android.activities.pet.PetCreateUtils;
import com.petkit.android.activities.pet.fragment.PetActivityFragment;
import com.petkit.android.activities.pet.fragment.PetFoodBrandFragment;
import com.petkit.android.activities.pet.fragment.PetFoodListFragment;
import com.petkit.android.activities.pet.fragment.PetGenderFragment;
import com.petkit.android.activities.pet.fragment.PetRegisterBirthDayFragment;
import com.petkit.android.activities.pet.fragment.PetRegisterCategoryListFragment;
import com.petkit.android.activities.pet.fragment.PetRegisterTypeFragment;
import com.petkit.android.activities.pet.fragment.PetRegisterWeightFragment;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.apiResponse.PetRsp;
import com.petkit.android.model.Pet;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.android.widget.HorizonalCircleProgress;
import com.petkit.android.widget.PetCompleteWindow;
import com.petkit.oversea.R;
import cz.msebera.android.httpclient.Header;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.simple.eventbus.EventBus;

/* JADX INFO: loaded from: classes4.dex */
public class PetRegisterFirstPartActivity extends BaseActivity implements BaseSwitchFragment.OnSwitchListner, BasePinnedHeaderListFragment.OnSwitchSearchModeListenr, PetRegisterWeightFragment.OnWeightListner, PetFoodListFragment.onPetFoodListSelectListener {
    private int ViewFrom;
    private Bundle bundle;
    private BaseSwitchFragment currentFragment;
    private FragmentManager fragmentManager;
    private int fragment_current;
    private BroadcastReceiver mBroadcastReceiver;
    private int mDeviceType;
    private Pet pet;
    private PetCompleteWindow petCompleteWindow;
    private int petType;
    private HorizonalCircleProgress progress;
    private boolean isModifyProp = true;
    private Map<String, Object> params = new HashMap();

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        this.isNeedPhotoController = true;
        super.onCreate(bundle);
        if (bundle == null) {
            this.bundle = getIntent().getExtras();
        } else {
            this.bundle = bundle.getBundle("Pet_Registe_bundle");
        }
        Bundle bundle2 = this.bundle;
        if (bundle2 != null) {
            Pet pet = (Pet) bundle2.getSerializable(Constants.EXTRA_DOG);
            this.pet = pet;
            if (pet != null && pet.getType() != null) {
                this.petType = this.pet.getType().getId();
            }
            this.ViewFrom = this.bundle.getInt(Constants.VIEW_FROM, -1);
            this.mDeviceType = this.bundle.getInt(Constants.EXTRA_TYPE);
            if (this.ViewFrom == 1) {
                setBottomView(R.layout.pet_register_ignore_view);
                this.mBottomView.findViewById(R.id.ignore_pet_register).setOnClickListener(this);
            }
        }
        setContentView(R.layout.activity_pet_register_base);
        EventBus.getDefault().register(this);
        registerBroadcastReceiver();
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        unregisterBroadcastReceiver();
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBundle("Pet_Registe_bundle", this.bundle);
    }

    @Override // com.petkit.android.activities.base.BaseActivity
    public void setupViews() {
        setTitleRightButton(R.string.Next, this);
        HorizonalCircleProgress horizonalCircleProgress = (HorizonalCircleProgress) findViewById(R.id.circle_line);
        this.progress = horizonalCircleProgress;
        horizonalCircleProgress.setTotalSteps(6);
        this.progress.setVisibility(8);
        initData();
    }

    private void initData() {
        this.fragment_current = -1;
        this.fragmentManager = getSupportFragmentManager();
        Bundle bundle = this.bundle;
        if (bundle == null || bundle.getInt(Constants.MODIFY_PET_PROPS, -1) < 0) {
            this.isModifyProp = false;
            this.progress.setVisibility(0);
            switchToFragment(1);
            return;
        }
        switchToFragment(this.bundle.getInt(Constants.MODIFY_PET_PROPS, 1));
    }

    @Override // com.petkit.android.activities.base.BaseActivity, android.view.View.OnClickListener
    public void onClick(View view) {
        super.onClick(view);
        int id = view.getId();
        if (id == R.id.title_right_btn) {
            this.currentFragment.goToNextStep();
            return;
        }
        if (id == R.id.menu_1) {
            this.mPopupWindow.dismiss();
            setCrop(true);
            getPhotoFromCamera();
            return;
        }
        if (id == R.id.menu_2) {
            this.mPopupWindow.dismiss();
            setCrop(true);
            getPhotoFromAlbum();
            return;
        }
        if (id == R.id.menu_cancel) {
            this.mPopupWindow.dismiss();
            return;
        }
        if (id == R.id.done) {
            this.petCompleteWindow.dismisswithOutAnima();
            if (this.ViewFrom == 1) {
                setResult(-1);
                finish();
                int i = R.anim.slide_none;
                overridePendingTransition(i, i);
                return;
            }
            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(Constants.BROADCAST_MSG_ADD_DOG_COMPLETE));
            finish();
            return;
        }
        if (id == R.id.complete_more) {
            this.petCompleteWindow.dismisswithOutAnima();
            this.params.clear();
            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(Constants.BROADCAST_MSG_ADD_DOG_COMPLETE));
            int i2 = this.petType;
            if (i2 == 1) {
                this.progress.setTotalSteps(2);
                switchToFragment(7);
                return;
            } else {
                if (i2 == 2) {
                    this.progress.setTotalSteps(1);
                    this.progress.setVisibility(8);
                    switchToFragment(10);
                    return;
                }
                return;
            }
        }
        if (id == R.id.ignore_pet_register) {
            if (this.ViewFrom == 1) {
                setResult(-1);
                finish();
                int i3 = R.anim.slide_none;
                overridePendingTransition(i3, i3);
                return;
            }
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

    private void processBackEvent() {
        if (this.fragment_current == 2) {
            BaseSwitchFragment baseSwitchFragment = this.currentFragment;
            if ((baseSwitchFragment instanceof PetRegisterTypeFragment) && this.mDeviceType != 5) {
                ((PetRegisterTypeFragment) baseSwitchFragment).onBackPressed();
                setCircleTitleState(this.currentFragment.getCurrentFragmentIndex());
                return;
            }
        }
        this.fragmentManager.popBackStackImmediate();
        BaseSwitchFragment currentFragment = getCurrentFragment();
        this.currentFragment = currentFragment;
        if (currentFragment == null) {
            if (this.ViewFrom == 1) {
                setResult(-1);
                finish();
                int i = R.anim.slide_none;
                overridePendingTransition(i, i);
                return;
            }
            finish();
            return;
        }
        currentFragment.onResume();
        setCircleTitleState(this.currentFragment.getCurrentFragmentIndex());
    }

    private BaseSwitchFragment getCurrentFragment() {
        List<Fragment> fragments = this.fragmentManager.getFragments();
        for (int size = fragments.size() - 1; size > 0; size--) {
            Fragment fragment = fragments.get(size);
            if (fragment != null && fragment.isVisible() && (fragment instanceof BaseSwitchFragment)) {
                return (BaseSwitchFragment) fragment;
            }
        }
        return null;
    }

    private void setCircleTitleState(int i) {
        setHasTitle();
        boolean z = this.isModifyProp;
        if (!z && i < 7) {
            int i2 = this.ViewFrom;
            if (i2 == 1 && i == 1) {
                this.mBottomView.setVisibility(0);
                setTitleLeftButtonVisibility(8);
            } else if (i2 == 1) {
                setTitleLeftButtonVisibility(0);
                this.mBottomView.setVisibility(8);
            }
            this.progress.setCurrentStep(i);
        } else if (!z && i >= 7) {
            if (i == 7) {
                this.progress.setCurrentStep(1);
            } else if (i == 8) {
                int i3 = this.petType;
                if (i3 == 1) {
                    this.progress.setCurrentStep(2);
                } else if (i3 == 2) {
                    this.progress.setVisibility(8);
                }
            } else if (i == 9) {
                int i4 = this.petType;
                if (i4 == 1) {
                    this.progress.setCurrentStep(2);
                } else if (i4 == 2) {
                    this.progress.setVisibility(8);
                }
            }
        }
        this.fragment_current = i;
        int titleStringId = this.currentFragment.getTitleStringId();
        String string = "";
        setTitle(titleStringId > 0 ? getString(titleStringId) : "");
        setTitleRightButtonVisibility(this.currentFragment.getTitleRightButtonVisibility(i));
        int titleRightButtonStringId = this.currentFragment.getTitleRightButtonStringId(this.isModifyProp, i);
        if (titleRightButtonStringId > 0) {
            string = getString(titleRightButtonStringId);
        }
        setTitleRightButton(string);
    }

    private void switchToFragment(int i) {
        hideSoftInput();
        BaseSwitchFragment baseSwitchFragment = this.currentFragment;
        if (baseSwitchFragment != null) {
            baseSwitchFragment.onPause();
        }
        switch (i) {
            case 1:
                PetRegisterTypeFragment petRegisterTypeFragment = new PetRegisterTypeFragment();
                this.currentFragment = petRegisterTypeFragment;
                petRegisterTypeFragment.setCurrentfragment(i);
                break;
            case 2:
                if (this.currentFragment == null) {
                    this.currentFragment = new PetRegisterTypeFragment();
                    this.bundle.putInt("PET_TYPE", 2);
                }
                this.currentFragment.setCurrentfragment(i);
                break;
            case 3:
                PetRegisterCategoryListFragment petRegisterCategoryListFragment = new PetRegisterCategoryListFragment();
                this.currentFragment = petRegisterCategoryListFragment;
                petRegisterCategoryListFragment.setOnSwitchSearchModeListenr(this);
                break;
            case 4:
                this.currentFragment = new PetRegisterBirthDayFragment();
                break;
            case 5:
                this.currentFragment = new PetGenderFragment();
                break;
            case 6:
                PetRegisterWeightFragment petRegisterWeightFragment = new PetRegisterWeightFragment();
                this.currentFragment = petRegisterWeightFragment;
                petRegisterWeightFragment.setOnWeightListner(this);
                break;
            case 7:
                this.currentFragment = new PetActivityFragment();
                break;
            case 8:
                PetFoodBrandFragment petFoodBrandFragment = new PetFoodBrandFragment();
                this.currentFragment = petFoodBrandFragment;
                petFoodBrandFragment.setOnSwitchSearchModeListenr(this);
                break;
            case 9:
                this.currentFragment = new PetFoodListFragment();
                break;
            default:
                throw new RuntimeException("[PetRegisterFirstPart] entry an undefined step");
        }
        BaseSwitchFragment baseSwitchFragment2 = this.currentFragment;
        if (baseSwitchFragment2 != null) {
            if (i != 2) {
                baseSwitchFragment2.setArguments(this.bundle);
                this.currentFragment.setOnSwitchListner(this);
                enterInFragment(this.currentFragment);
            }
            setCircleTitleState(i);
        }
    }

    private void enterInFragment(Fragment fragment) {
        FragmentTransaction fragmentTransactionBeginTransaction = this.fragmentManager.beginTransaction();
        fragmentTransactionBeginTransaction.setCustomAnimations(R.anim.push_right_in, R.anim.push_left_out);
        fragmentTransactionBeginTransaction.add(R.id.content_fragment, fragment, String.valueOf(this.fragment_current));
        fragmentTransactionBeginTransaction.addToBackStack(null);
        fragmentTransactionBeginTransaction.commit();
    }

    private void createPet() {
        if (this.params.size() <= 0) {
            return;
        }
        Set<String> setKeySet = this.params.keySet();
        HashMap map = new HashMap();
        for (String str : setKeySet) {
            this.params.get(str);
            map.put(str, this.params.get(str) + "");
        }
        PetCreateUtils.createPet(this, map, new PetCreateUtils.OnUploadListener() { // from class: com.petkit.android.activities.pet.PetRegisterFirstPartActivity.1
            @Override // com.petkit.android.activities.pet.PetCreateUtils.OnUploadListener
            public void onSuccess(Bundle bundle) {
                PetRegisterFirstPartActivity.this.pet = (Pet) bundle.getSerializable(Constants.EXTRA_DOG);
                if (PetRegisterFirstPartActivity.this.pet != null) {
                    PetRegisterFirstPartActivity petRegisterFirstPartActivity = PetRegisterFirstPartActivity.this;
                    petRegisterFirstPartActivity.petType = petRegisterFirstPartActivity.pet.getType().getId();
                }
                PetRegisterFirstPartActivity.this.bundle.putSerializable(Constants.EXTRA_DOG, PetRegisterFirstPartActivity.this.pet);
                int i = PetRegisterFirstPartActivity.this.mDeviceType;
                if (i == 1) {
                    Bundle bundle2 = new Bundle();
                    bundle2.putSerializable(Constants.EXTRA_DOG, PetRegisterFirstPartActivity.this.pet);
                    PetRegisterFirstPartActivity.this.startActivityWithData(PetkitScanActivity.class, bundle2, false);
                    PetRegisterFirstPartActivity.this.finish();
                    return;
                }
                if (i == 4) {
                    Bundle bundle3 = new Bundle();
                    bundle3.putSerializable(Constants.EXTRA_DOG, PetRegisterFirstPartActivity.this.pet);
                    PetRegisterFirstPartActivity.this.startActivityWithData(FeederBindStartActivity.class, bundle3, false);
                    PetRegisterFirstPartActivity.this.finish();
                    return;
                }
                if (i == 5) {
                    Bundle bundle4 = new Bundle();
                    bundle4.putSerializable(Constants.EXTRA_DOG, PetRegisterFirstPartActivity.this.pet);
                    PetRegisterFirstPartActivity.this.startActivityWithData(CozyBindStartActivity.class, bundle4, false);
                    PetRegisterFirstPartActivity.this.finish();
                    return;
                }
                if (i != 6) {
                    PetRegisterFirstPartActivity.this.showCompleteWindow();
                    return;
                }
                Bundle bundle5 = new Bundle();
                bundle5.putSerializable(Constants.EXTRA_DOG, PetRegisterFirstPartActivity.this.pet);
                PetRegisterFirstPartActivity.this.startActivityWithData(D2BindStartActivity.class, bundle5, false);
                PetRegisterFirstPartActivity.this.finish();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showCompleteWindow() {
        View viewInflate = getLayoutInflater().inflate(R.layout.layout_pet_register_success, (ViewGroup) null);
        viewInflate.findViewById(R.id.done).setOnClickListener(this);
        viewInflate.findViewById(R.id.complete_more).setOnClickListener(this);
        PetCompleteWindow petCompleteWindow = new PetCompleteWindow(this, viewInflate, true);
        this.petCompleteWindow = petCompleteWindow;
        petCompleteWindow.setBackgroundDrawable(new BitmapDrawable());
        if (isFinishing()) {
            return;
        }
        this.petCompleteWindow.showAtLocation(getWindow().getDecorView(), 17, 0, 0);
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public void onWindowFocusChanged(boolean z) {
        BaseSwitchFragment baseSwitchFragment = this.currentFragment;
        if (baseSwitchFragment == null || !(baseSwitchFragment instanceof PetRegisterTypeFragment)) {
            return;
        }
        ((PetRegisterTypeFragment) baseSwitchFragment).onWindowFocusChanged(z);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

    @Override // com.petkit.android.activities.base.BaseActivity
    public void uploadHeadPic(String str) {
        BaseSwitchFragment baseSwitchFragment = this.currentFragment;
        if (baseSwitchFragment == null || !(baseSwitchFragment instanceof PetRegisterTypeFragment)) {
            return;
        }
        ((PetRegisterTypeFragment) baseSwitchFragment).uploadHeadPic(str);
    }

    @Override // com.petkit.android.activities.base.fragment.BaseSwitchFragment.OnSwitchListner
    public void dataDone(Bundle bundle, HashMap<String, Object> map) {
        if (this.bundle == null) {
            this.bundle = new Bundle();
        }
        if (bundle != null) {
            this.bundle.putAll(bundle);
        }
        this.params.putAll(map);
        int i = this.fragment_current;
        if (i <= 7 && this.isModifyProp) {
            updatePetProps();
            return;
        }
        switch (i) {
            case 1:
                switchToFragment(2);
                break;
            case 2:
                switchToFragment(3);
                break;
            case 3:
                switchToFragment(4);
                break;
            case 4:
                switchToFragment(5);
                break;
            case 5:
                switchToFragment(6);
                break;
            case 6:
                createPet();
                break;
            case 7:
                switchToFragment(8);
                break;
            case 8:
                if (bundle == null || bundle.getSerializable(Constants.EXTRA_PRIVATE_FOOD) != null) {
                    updatePetProps();
                } else {
                    switchToFragment(9);
                }
                break;
            case 9:
                updatePetProps();
                break;
        }
    }

    private void updatePetProps() {
        HashMap map = new HashMap();
        map.put("petId", this.pet.getId());
        boolean z = true;
        if (this.params.size() <= 0) {
            if (this.ViewFrom == 1) {
                setResult(-1);
                finish();
                int i = R.anim.slide_none;
                overridePendingTransition(i, i);
                return;
            }
            finish();
            return;
        }
        map.put("kv", new Gson().toJson(this.params));
        post(ApiTools.SAMPLE_API_PET_UPDATE_PROP, map, new AsyncHttpRespHandler(this, z) { // from class: com.petkit.android.activities.pet.PetRegisterFirstPartActivity.2
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i2, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i2, headerArr, bArr);
                PetRsp petRsp = (PetRsp) this.gson.fromJson(this.responseResult, PetRsp.class);
                if (petRsp.getError() != null) {
                    PetRegisterFirstPartActivity.this.showLongToast(petRsp.getError().getMsg(), R.drawable.toast_failed);
                    return;
                }
                PetRegisterFirstPartActivity.this.pet = petRsp.getResult();
                if (PetRegisterFirstPartActivity.this.pet != null) {
                    UserInforUtils.updateDogInformation(PetRegisterFirstPartActivity.this.pet, 3);
                    PetRegisterFirstPartActivity.this.sendUpdateDogBroadcast();
                    PetRegisterFirstPartActivity.this.sendUpdatePetFoodBroadcast();
                    EventBus.getDefault().post(new RefreshFeedDataEvent());
                }
                if (PetRegisterFirstPartActivity.this.ViewFrom == 1) {
                    PetRegisterFirstPartActivity.this.setResult(-1);
                    PetRegisterFirstPartActivity.this.finish();
                    PetRegisterFirstPartActivity petRegisterFirstPartActivity = PetRegisterFirstPartActivity.this;
                    int i3 = R.anim.slide_none;
                    petRegisterFirstPartActivity.overridePendingTransition(i3, i3);
                    return;
                }
                PetRegisterFirstPartActivity.this.finish();
            }
        });
    }

    @Override // com.petkit.android.activities.base.fragment.BasePinnedHeaderListFragment.OnSwitchSearchModeListenr
    public void isInSearchMode(boolean z) {
        if (z) {
            setNoTitle();
            this.progress.setVisibility(8);
            return;
        }
        setHasTitle();
        if (!this.isModifyProp || this.ViewFrom == 1) {
            int i = this.fragment_current;
            if ((i == 8 || i == 9) && this.petType == 2) {
                this.progress.setVisibility(8);
            } else {
                this.progress.setVisibility(0);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendUpdateDogBroadcast() {
        Intent intent = new Intent();
        intent.putExtra(Constants.EXTRA_DOG, this.pet);
        intent.setAction(Constants.BROADCAST_MSG_UPDATE_DOG);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendUpdatePetFoodBroadcast() {
        Intent intent = new Intent();
        intent.putExtra(Constants.EXTRA_SELECT_FOOD, this.pet.getFood());
        intent.putExtra(Constants.EXTRA_PRIVATE_FOOD, this.pet.getPrivateFood());
        intent.setAction("com.petkit.android.updateDogFood");
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @Override // com.petkit.android.activities.base.BaseActivity, com.petkit.android.activities.common.fragment.FailureFragment.FailureOnClickListener
    public void onRefresh(boolean z) {
        super.onRefresh(z);
        BaseSwitchFragment baseSwitchFragment = this.currentFragment;
        if (baseSwitchFragment instanceof PetFoodBrandFragment) {
            baseSwitchFragment.onRefresh(z);
        }
    }

    @Override // com.petkit.android.activities.pet.fragment.PetRegisterWeightFragment.OnWeightListner
    public void isWeightTenable(boolean z) {
        if (z) {
            setTitleRightButton(getString(R.string.Done), CommonUtils.getColorById(R.color.black), this);
        } else {
            setTitleRightButton(getString(R.string.Done), CommonUtils.getColorById(R.color.hblack), null);
        }
    }

    @Override // com.petkit.android.activities.pet.fragment.PetFoodListFragment.onPetFoodListSelectListener
    public void onPetFoodSelect(boolean z) {
        setTitleRightButtonVisibility(z ? 0 : 4);
    }

    private void registerBroadcastReceiver() {
        this.mBroadcastReceiver = new BroadcastReceiver() { // from class: com.petkit.android.activities.pet.PetRegisterFirstPartActivity.3
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                action.hashCode();
                if (action.equals(Constants.BROADCAST_PERMISSION_FINISHED)) {
                    intent.getStringExtra(Constants.EXTRA_PERMISSION_CLASSNAME);
                    intent.getBooleanExtra(Constants.EXTRA_BOOLEAN, false);
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.BROADCAST_MSG_UPDATE_DOG);
        intentFilter.addAction(Constants.BROADCAST_PERMISSION_FINISHED);
        LocalBroadcastManager.getInstance(this).registerReceiver(this.mBroadcastReceiver, intentFilter);
    }

    private void unregisterBroadcastReceiver() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(this.mBroadcastReceiver);
    }
}

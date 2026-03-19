package com.petkit.android.activities.fit;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;
import com.jjoe64.graphview.IInterceptListener;
import com.petkit.android.activities.base.BaseTabActivity;
import com.petkit.android.activities.fit.fragment.CharacteristicActivityFragment;
import com.petkit.android.activities.fit.fragment.CharacteristicConsumptionFragment;
import com.petkit.android.activities.fit.fragment.CharacteristicRestFragment;
import com.petkit.android.model.DailyDetailItem;
import com.petkit.android.model.Food;
import com.petkit.android.model.Pet;
import com.petkit.android.model.PrivateFood;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.DailyDetailUtils;
import com.petkit.android.utils.DateUtil;
import com.petkit.oversea.R;
import com.viewpagerindicator.TabPageIndicator;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class CharacteristicDetailActivity extends BaseTabActivity implements IInterceptListener {
    private Pet curPet;
    private String day;
    private BroadcastReceiver mBroadcastReceiver;
    private DailyDetailItem mDailyDetailItem;
    private boolean pound = false;

    @Override // com.petkit.android.activities.base.BaseActivity, android.view.View.OnClickListener
    public void onClick(View view) {
    }

    @Override // com.petkit.android.activities.base.BaseTabActivity, com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        if (bundle != null) {
            this.day = bundle.getString(Constants.EXTRA_Characteristic_DAY);
            this.curPet = (Pet) bundle.getSerializable(Constants.EXTRA_DOG);
            this.defaultTabPage = bundle.getInt(Constants.EXTRA_STRING_ID);
            this.pound = bundle.getBoolean(Constants.EXTRA_BOOLEAN);
        } else {
            this.day = getIntent().getStringExtra(Constants.EXTRA_Characteristic_DAY);
            this.curPet = (Pet) getIntent().getSerializableExtra(Constants.EXTRA_DOG);
            this.defaultTabPage = getIntent().getIntExtra(Constants.EXTRA_STRING_ID, 0);
            this.pound = getIntent().getBooleanExtra(Constants.EXTRA_BOOLEAN, false);
        }
        registerBoradcastReceiver();
        this.mDailyDetailItem = DailyDetailUtils.getDailyDetailItem(this.curPet.getId(), String.valueOf(this.day));
        super.onCreate(bundle);
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putString(Constants.EXTRA_Characteristic_DAY, this.day);
        bundle.putSerializable(Constants.EXTRA_DOG, this.curPet);
        bundle.putInt(Constants.EXTRA_STRING_ID, this.defaultTabPage);
        bundle.putBoolean(Constants.EXTRA_BOOLEAN, this.pound);
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        unregisterBroadcastReceiver();
    }

    @Override // com.petkit.android.activities.base.BaseTabActivity, com.petkit.android.activities.base.BaseActivity
    public void setupViews() {
        super.setupViews();
    }

    @Override // com.petkit.android.activities.base.BaseTabActivity
    public void initAdapter() {
        this.pagerAdapter = new CharacteristicPagerAdapter(getSupportFragmentManager());
        this.mViewPager.setOffscreenPageLimit(2);
        setTitleLeftButton(R.drawable.btn_back_white);
        setTitle(R.string.Detail, R.color.white);
        setTitleRightButton(DateUtil.getDateFormatShortString(this.day), CommonUtils.getColorById(R.color.white), null);
        setDividerLineVisibility(8);
        this.indicator.setTabStyle(TabPageIndicator.STYLE_MATCH_CONTENT);
        this.indicator.setBackgroundColor(CommonUtils.getColorById(R.color.filter_middle));
        this.indicator.setTabColor(CommonUtils.getColorById(R.color.white), CommonUtils.getColorById(R.color.white));
        this.indicator.changeTabStyle();
        changeViewPager(0);
        this.indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.petkit.android.activities.fit.CharacteristicDetailActivity.1
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i, float f, int i2) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int i) {
                CharacteristicDetailActivity.this.changeViewPager(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeViewPager(int i) {
        if (i == 0) {
            setTitleBackgroundColor(CommonUtils.getColorById(R.color.calorie_detail_bg));
            this.indicator.setBackgroundColor(CommonUtils.getColorById(R.color.calorie_detail_bg));
        } else if (i == 1) {
            setTitleBackgroundColor(CommonUtils.getColorById(R.color.rest_detail_bg));
            this.indicator.setBackgroundColor(CommonUtils.getColorById(R.color.rest_detail_bg));
        } else {
            setTitleBackgroundColor(CommonUtils.getColorById(R.color.consume_detail_bg));
            this.indicator.setBackgroundColor(CommonUtils.getColorById(R.color.consume_detail_bg));
        }
    }

    public class CharacteristicPagerAdapter extends FragmentPagerAdapter {
        public List<Fragment> fragments;
        public int[] titles;

        public CharacteristicPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
            this.fragments = new ArrayList();
            this.titles = new int[]{R.string.Activity, R.string.Sleep, R.string.Consumption};
            init();
        }

        private void init() {
            CharacteristicActivityFragment characteristicActivityFragment = new CharacteristicActivityFragment();
            Bundle bundle = new Bundle();
            bundle.putString("day", CharacteristicDetailActivity.this.day);
            bundle.putSerializable("curPet", CharacteristicDetailActivity.this.curPet);
            bundle.putSerializable("dailyDetailItem", CharacteristicDetailActivity.this.mDailyDetailItem);
            characteristicActivityFragment.setArguments(bundle);
            this.fragments.add(0, characteristicActivityFragment);
            CharacteristicRestFragment characteristicRestFragment = new CharacteristicRestFragment();
            Bundle bundle2 = new Bundle();
            bundle2.putString("day", CharacteristicDetailActivity.this.day);
            bundle2.putSerializable("curPet", CharacteristicDetailActivity.this.curPet);
            bundle2.putSerializable("dailyDetailItem", CharacteristicDetailActivity.this.mDailyDetailItem);
            characteristicRestFragment.setArguments(bundle2);
            this.fragments.add(1, characteristicRestFragment);
            CharacteristicConsumptionFragment characteristicConsumptionFragment = new CharacteristicConsumptionFragment();
            Bundle bundle3 = new Bundle();
            bundle3.putString("day", CharacteristicDetailActivity.this.day);
            bundle3.putSerializable("curPet", CharacteristicDetailActivity.this.curPet);
            bundle3.putSerializable("dailyDetailItem", CharacteristicDetailActivity.this.mDailyDetailItem);
            bundle3.putBoolean(CharacteristicConsumptionFragment.POUND, CharacteristicDetailActivity.this.pound);
            characteristicConsumptionFragment.setArguments(bundle3);
            this.fragments.add(2, characteristicConsumptionFragment);
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public int getCount() {
            return this.fragments.size();
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public CharSequence getPageTitle(int i) {
            return CharacteristicDetailActivity.this.getString(this.titles[i]);
        }

        public void refreshConsuptionViewPetFood(String str) {
            ((CharacteristicConsumptionFragment) this.fragments.get(2)).refreshFood(str);
        }

        @Override // androidx.fragment.app.FragmentPagerAdapter
        public Fragment getItem(int i) {
            return this.fragments.get(i);
        }
    }

    @Override // com.jjoe64.graphview.IInterceptListener
    public void changeInterceptState(boolean z) {
        this.mViewPager.setPagingEnabled(!z);
    }

    private void registerBoradcastReceiver() {
        this.mBroadcastReceiver = new BroadcastReceiver() { // from class: com.petkit.android.activities.fit.CharacteristicDetailActivity.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals("com.petkit.android.updateDogFood")) {
                    Food food = (Food) intent.getSerializableExtra(Constants.EXTRA_SELECT_FOOD);
                    if (((PrivateFood) intent.getSerializableExtra(Constants.EXTRA_PRIVATE_FOOD)) != null) {
                        ((CharacteristicPagerAdapter) ((BaseTabActivity) CharacteristicDetailActivity.this).pagerAdapter).refreshConsuptionViewPetFood((food.getId() + "/") + food.getName());
                        return;
                    }
                    if (food != null) {
                        String str = food.getId() + "/";
                        if (food.getBrand() != null) {
                            str = str + food.getBrand().getName() + "/";
                        }
                        ((CharacteristicPagerAdapter) ((BaseTabActivity) CharacteristicDetailActivity.this).pagerAdapter).refreshConsuptionViewPetFood(str + food.getName());
                    }
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.petkit.android.updateDogFood");
        LocalBroadcastManager.getInstance(this).registerReceiver(this.mBroadcastReceiver, intentFilter);
    }

    private void unregisterBroadcastReceiver() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(this.mBroadcastReceiver);
    }
}

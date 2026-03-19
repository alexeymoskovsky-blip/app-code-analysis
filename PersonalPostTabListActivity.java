package com.petkit.android.activities.personal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.petkit.android.activities.base.BaseTabActivity;
import com.petkit.android.activities.base.fragment.BasePostClassifyListFragment;
import com.petkit.android.activities.base.fragment.BaseTopicsListFragment;
import com.petkit.android.activities.community.fragment.PostClassifyCollectListFragment;
import com.petkit.android.activities.community.fragment.PostClassifyPublishListFragment;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.ShareHelper;
import com.petkit.oversea.R;
import com.viewpagerindicator.TabPageIndicator;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class PersonalPostTabListActivity extends BaseTabActivity implements View.OnClickListener {
    public static final int TYPE_POST = 1;
    private int type;
    private String userId;
    private boolean isUserSelf = false;
    private int currentPage = 0;

    @Override // com.petkit.android.activities.base.BaseActivity, android.view.View.OnClickListener
    public void onClick(View view) {
    }

    @Override // com.petkit.android.activities.base.BaseTabActivity, com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        if (bundle != null) {
            this.userId = bundle.getString(Constants.EXTRA_USER_ID);
            this.type = bundle.getInt(Constants.EXTRA_TYPE, 1);
            this.isUserSelf = bundle.getBoolean(Constants.EXTRA_IS_USERSELF, false);
        } else {
            this.userId = getIntent().getStringExtra(Constants.EXTRA_USER_ID);
            this.type = getIntent().getIntExtra(Constants.EXTRA_TYPE, 1);
            this.isUserSelf = getIntent().getBooleanExtra(Constants.EXTRA_IS_USERSELF, false);
        }
        super.onCreate(bundle);
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt(Constants.EXTRA_TYPE, this.type);
        bundle.putBoolean(Constants.EXTRA_IS_USERSELF, this.isUserSelf);
        bundle.putString(Constants.EXTRA_USER_ID, this.userId);
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(Constants.BROADCAST_MSG_NOTIFYDATASETCHANGED));
    }

    @Override // com.petkit.android.activities.base.BaseTabActivity
    public void initAdapter() {
        this.pagerAdapter = new ClassifyPagerAdapter(getSupportFragmentManager());
        if (this.isUserSelf) {
            setTitle(R.string.My_pet_posts);
        } else {
            setTitle(R.string.My_pet_posts);
        }
        this.indicator.setOnTabReselectedListener(new TabPageIndicator.OnTabReselectedListener() { // from class: com.petkit.android.activities.personal.PersonalPostTabListActivity.1
            @Override // com.viewpagerindicator.TabPageIndicator.OnTabReselectedListener
            public void onTabReselected(int i) {
                ((ClassifyPagerAdapter) ((BaseTabActivity) PersonalPostTabListActivity.this).pagerAdapter).moveToTop(i);
            }
        });
        this.indicator.setOnPageChangeListener(new BaseTabActivity.OnPageChangeListenerAdapter() { // from class: com.petkit.android.activities.personal.PersonalPostTabListActivity.2
            @Override // com.petkit.android.activities.base.BaseTabActivity.OnPageChangeListenerAdapter, androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int i) {
                PersonalPostTabListActivity.this.currentPage = i;
                LocalBroadcastManager.getInstance(PersonalPostTabListActivity.this).sendBroadcast(new Intent(Constants.BROADCAST_MSG_NOTIFYDATASETCHANGED));
            }
        });
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        ShareHelper.dealWithOnActivityResult(this, i, i2, intent);
    }

    public class ClassifyPagerAdapter extends FragmentPagerAdapter {
        public List<BasePostClassifyListFragment> fragments;
        public int[] titles;

        public ClassifyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
            this.fragments = new ArrayList();
            this.titles = new int[]{R.string.My_publish, R.string.My_collects};
            init();
        }

        public void moveToTop(int i) {
            this.fragments.get(i).moveToTop();
        }

        public void refreshFragment(int i, boolean z) {
            this.fragments.get(i).onRefresh(z);
        }

        private void init() {
            PostClassifyPublishListFragment postClassifyPublishListFragment = new PostClassifyPublishListFragment();
            Bundle bundle = new Bundle();
            bundle.putString(BaseTopicsListFragment.USERID, PersonalPostTabListActivity.this.userId);
            postClassifyPublishListFragment.setArguments(bundle);
            this.fragments.add(postClassifyPublishListFragment);
            PostClassifyCollectListFragment postClassifyCollectListFragment = new PostClassifyCollectListFragment();
            postClassifyCollectListFragment.setArguments(bundle);
            this.fragments.add(postClassifyCollectListFragment);
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public CharSequence getPageTitle(int i) {
            return PersonalPostTabListActivity.this.getString(this.titles[i]);
        }

        @Override // androidx.fragment.app.FragmentPagerAdapter
        public Fragment getItem(int i) {
            return this.fragments.get(i);
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public int getCount() {
            return this.fragments.size();
        }
    }

    @Override // com.petkit.android.activities.base.BaseActivity, com.petkit.android.activities.common.fragment.FailureFragment.FailureOnClickListener
    public void onRefresh(boolean z) {
        super.onRefresh(z);
        ((ClassifyPagerAdapter) this.pagerAdapter).refreshFragment(this.currentPage, z);
    }
}

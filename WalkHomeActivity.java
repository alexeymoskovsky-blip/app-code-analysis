package com.petkit.android.activities.walkdog;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;
import com.github.sunnysuperman.commons.utils.CollectionUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.widget.InterceptViewPager;
import com.jess.arms.widget.PetkitToast;
import com.orm.query.Condition;
import com.petkit.android.activities.base.BaseTabActivity;
import com.petkit.android.activities.feeder.adapter.WeekPagerAdapter;
import com.petkit.android.activities.permission.PermissionDialogActivity;
import com.petkit.android.activities.statistics.WalkPetStatisticsActivity$$ExternalSyntheticLambda1;
import com.petkit.android.activities.walkdog.ApiResponse.WalkDailyDetailRsp;
import com.petkit.android.activities.walkdog.adapter.WalkHomeFragmentAdapter;
import com.petkit.android.activities.walkdog.fragment.WalkHomeFragment;
import com.petkit.android.activities.walkdog.model.WalkData;
import com.petkit.android.activities.walkdog.model.WalkDayData;
import com.petkit.android.activities.walkdog.service.WalkMainService;
import com.petkit.android.activities.walkdog.setting.WalkSettingTargetActivity;
import com.petkit.android.activities.walkdog.utils.WalkDataUtils;
import com.petkit.android.activities.walkdog.widget.WalkPetListView;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.model.Pet;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.DateUtil;
import com.petkit.android.utils.PetUtils;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.oversea.R;
import cz.msebera.android.httpclient.Header;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

/* JADX INFO: loaded from: classes6.dex */
public class WalkHomeActivity extends BaseTabActivity implements WalkHomeFragment.IDataRefreshListener, WeekPagerAdapter.IDaySelectListener {
    private BroadcastReceiver mBroadcastReceiver;
    private int mSelectPagerIndex;
    private int mSelectWeekPagerIndex;
    private WeekPagerAdapter mWeekPagerAdapter;
    private ViewPager mWeekViewPager;
    String petId;

    @Override // com.petkit.android.activities.base.BaseTabActivity, com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        this.indicatorType = 1;
        super.onCreate(bundle);
        registerBoradcastReceiver();
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        unregisterBroadcastReceiver();
    }

    @Override // com.petkit.android.activities.base.BaseTabActivity, com.petkit.android.activities.base.BaseActivity
    public void setupViews() {
        super.setupViews();
        this.petId = getIntent().getStringExtra(WalkDataUtils.EXTRA_WALK_PET_ID);
        setTitleLeftButton(R.drawable.icon_back_black_normal);
        setTitleRightImageView(R.drawable.edit_feed, new View.OnClickListener() { // from class: com.petkit.android.activities.walkdog.WalkHomeActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$setupViews$0(view);
            }
        });
        setDividerLineVisibility(8);
        setTopView(R.layout.layout_walk_top_view);
        setBackgroundResourceId(R.color.white);
        initTopView();
        setBottomView(R.layout.layout_walkdog_bottom_state);
        refreshBottomView();
        this.mSelectPagerIndex = this.pagerAdapter.getCount() - 1;
        this.mViewPager.setCurrentItem(this.pagerAdapter.getCount() - 1);
        this.mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.petkit.android.activities.walkdog.WalkHomeActivity.1
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i, float f, int i2) {
            }

            public AnonymousClass1() {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int i) {
                if (WalkHomeActivity.this.mSelectPagerIndex > i) {
                    if (WalkHomeActivity.this.mWeekPagerAdapter.getCurrentDayIndex() != 0) {
                        WalkHomeActivity.this.mWeekPagerAdapter.setCurrentDayIndex(WalkHomeActivity.this.mWeekPagerAdapter.getCurrentDayIndex() - 1);
                    } else {
                        WalkHomeActivity.this.mSelectWeekPagerIndex = r0.mWeekViewPager.getCurrentItem() - 1;
                        WalkHomeActivity.this.mWeekViewPager.setCurrentItem(WalkHomeActivity.this.mSelectWeekPagerIndex);
                        WalkHomeActivity.this.mWeekPagerAdapter.setCurrentDayIndex(6);
                    }
                } else if (WalkHomeActivity.this.mSelectPagerIndex < i) {
                    if (WalkHomeActivity.this.mWeekPagerAdapter.getCurrentDayIndex() != 6) {
                        WalkHomeActivity.this.mWeekPagerAdapter.setCurrentDayIndex(WalkHomeActivity.this.mWeekPagerAdapter.getCurrentDayIndex() + 1);
                    } else {
                        WalkHomeActivity walkHomeActivity = WalkHomeActivity.this;
                        walkHomeActivity.mSelectWeekPagerIndex = walkHomeActivity.mWeekViewPager.getCurrentItem() + 1;
                        WalkHomeActivity.this.mWeekViewPager.setCurrentItem(WalkHomeActivity.this.mSelectWeekPagerIndex);
                        WalkHomeActivity.this.mWeekPagerAdapter.setCurrentDayIndex(0);
                    }
                }
                WalkHomeActivity.this.mSelectPagerIndex = i;
            }
        });
        setTitleStatus();
    }

    public /* synthetic */ void lambda$setupViews$0(View view) {
        startActivity(new Intent(this, (Class<?>) WalkSettingTargetActivity.class));
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.walkdog.WalkHomeActivity$1 */
    public class AnonymousClass1 implements ViewPager.OnPageChangeListener {
        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrollStateChanged(int i) {
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrolled(int i, float f, int i2) {
        }

        public AnonymousClass1() {
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageSelected(int i) {
            if (WalkHomeActivity.this.mSelectPagerIndex > i) {
                if (WalkHomeActivity.this.mWeekPagerAdapter.getCurrentDayIndex() != 0) {
                    WalkHomeActivity.this.mWeekPagerAdapter.setCurrentDayIndex(WalkHomeActivity.this.mWeekPagerAdapter.getCurrentDayIndex() - 1);
                } else {
                    WalkHomeActivity.this.mSelectWeekPagerIndex = r0.mWeekViewPager.getCurrentItem() - 1;
                    WalkHomeActivity.this.mWeekViewPager.setCurrentItem(WalkHomeActivity.this.mSelectWeekPagerIndex);
                    WalkHomeActivity.this.mWeekPagerAdapter.setCurrentDayIndex(6);
                }
            } else if (WalkHomeActivity.this.mSelectPagerIndex < i) {
                if (WalkHomeActivity.this.mWeekPagerAdapter.getCurrentDayIndex() != 6) {
                    WalkHomeActivity.this.mWeekPagerAdapter.setCurrentDayIndex(WalkHomeActivity.this.mWeekPagerAdapter.getCurrentDayIndex() + 1);
                } else {
                    WalkHomeActivity walkHomeActivity = WalkHomeActivity.this;
                    walkHomeActivity.mSelectWeekPagerIndex = walkHomeActivity.mWeekViewPager.getCurrentItem() + 1;
                    WalkHomeActivity.this.mWeekViewPager.setCurrentItem(WalkHomeActivity.this.mSelectWeekPagerIndex);
                    WalkHomeActivity.this.mWeekPagerAdapter.setCurrentDayIndex(0);
                }
            }
            WalkHomeActivity.this.mSelectPagerIndex = i;
        }
    }

    public void refreshBottomView() {
        LinearLayout linearLayout = (LinearLayout) this.mBottomView.findViewById(R.id.ll_pets_view);
        linearLayout.removeAllViews();
        final WalkPetListView walkPetListView = new WalkPetListView(this, UserInforUtils.getUser().getDogs(true), findWalkPets());
        linearLayout.addView(walkPetListView, new LinearLayout.LayoutParams(-2, -2));
        new Handler().postDelayed(new WalkPetStatisticsActivity$$ExternalSyntheticLambda1(walkPetListView), 500L);
        ((TextView) this.mBottomView.findViewById(R.id.tv_start_walkdog)).setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.walkdog.WalkHomeActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$refreshBottomView$1(walkPetListView, view);
            }
        });
    }

    public /* synthetic */ void lambda$refreshBottomView$1(WalkPetListView walkPetListView, View view) {
        if (!CommonUtils.checkPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE")) {
            startActivity(PermissionDialogActivity.newIntent(this, getClass().getName(), "android.permission.WRITE_EXTERNAL_STORAGE"));
            return;
        }
        if (CollectionUtil.isEmpty(UserInforUtils.getUser().getDogs(true))) {
            PetkitToast.showToast(getString(R.string.Walk_add_pet));
            return;
        }
        if (CollectionUtil.isEmpty(walkPetListView.getSelectedPets())) {
            PetkitToast.showToast(getString(R.string.Walk_choose_pet));
            return;
        }
        WalkData walkDataByConditions = WalkDataUtils.getWalkDataByConditions(Condition.prop("state").eq(1));
        if (walkDataByConditions != null) {
            walkDataByConditions.delete();
        }
        WalkData walkData = new WalkData();
        long jCurrentTimeMillis = System.currentTimeMillis();
        walkData.setOwnerId(Long.valueOf(CommonUtils.getCurrentUserId()).longValue());
        walkData.setTimeindex(jCurrentTimeMillis);
        walkData.setT1(DateUtil.long2str(jCurrentTimeMillis, "yyyy-MM-dd'T'HH:mm:ss.SSSZ"));
        walkData.setState(1);
        List<Pet> selectedPets = walkPetListView.getSelectedPets();
        walkData.setmPets(selectedPets);
        DataHelper.setStringSF(this, WalkDataUtils.EXTRA_WALK_SELECTED_PETS, new Gson().toJson(selectedPets));
        walkData.save();
        if (Build.VERSION.SDK_INT >= 26) {
            startForegroundService(new Intent(this, (Class<?>) WalkMainService.class));
        } else {
            startService(new Intent(this, (Class<?>) WalkMainService.class));
        }
        startActivity(new Intent(this, (Class<?>) WalkingStartActivity.class));
        finish();
    }

    @Override // com.petkit.android.activities.base.BaseTabActivity
    public void initAdapter() {
        this.pagerAdapter = new WalkHomeFragmentAdapter(getSupportFragmentManager(), this);
    }

    public void refreshFragments() {
        if (isFinishing()) {
            return;
        }
        ((WalkHomeFragmentAdapter) this.pagerAdapter).setNeedRefresh(true);
    }

    public void setTitleStatus() {
        setTitle(getString(R.string.Walkdog));
    }

    private void getList(String str, int i) {
        HashMap map = new HashMap();
        map.put("since", CommonUtils.getDateStringByOffsetForDay(str, i));
        map.put("until", str);
        map.put("detailsLimit", String.valueOf(10));
        post(ApiTools.SAMPLE_API_WALK_PET_DAILY_DATA, map, new AsyncHttpRespHandler() { // from class: com.petkit.android.activities.walkdog.WalkHomeActivity.2
            public final /* synthetic */ int val$offset;
            public final /* synthetic */ String val$until;

            public AnonymousClass2(int i2, String str2) {
                i = i2;
                str = str2;
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i2, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i2, headerArr, bArr);
                WalkDailyDetailRsp walkDailyDetailRsp = (WalkDailyDetailRsp) this.gson.fromJson(this.responseResult, WalkDailyDetailRsp.class);
                if (walkDailyDetailRsp.getError() == null) {
                    for (int i3 = 0; i3 <= i; i3++) {
                        WalkDataUtils.getOrCreateWalkDayDataForDay(Integer.valueOf(CommonUtils.getDateStringByOffsetForDay(str, i3)).intValue()).save();
                    }
                    for (int i4 = 0; i4 < walkDailyDetailRsp.getResult().size(); i4++) {
                        WalkDailyDetailRsp.ResultBean resultBean = walkDailyDetailRsp.getResult().get(i4);
                        WalkDayData orCreateWalkDayDataForDay = WalkDataUtils.getOrCreateWalkDayDataForDay(resultBean.getDay());
                        orCreateWalkDayDataForDay.setDistance(resultBean.getDistance());
                        orCreateWalkDayDataForDay.setDuration(resultBean.getDuration());
                        orCreateWalkDayDataForDay.setTimes(resultBean.getTimes());
                        orCreateWalkDayDataForDay.setGoal(resultBean.getGoal());
                        orCreateWalkDayDataForDay.setNeedUpdate(false);
                        if (resultBean.getDetails() != null) {
                            orCreateWalkDayDataForDay.setLastKey(resultBean.getDetails().getList().size() < 10 ? null : resultBean.getDetails().getLastKey());
                            for (int i5 = 0; i5 < resultBean.getDetails().getList().size(); i5++) {
                                WalkDataUtils.storeWalkDataForDay(resultBean.getDay(), resultBean.getDetails().getList().get(i5));
                            }
                        }
                        orCreateWalkDayDataForDay.save();
                    }
                    WalkHomeActivity.this.refreshFragments();
                    return;
                }
                WalkHomeActivity.this.showShortToast(walkDailyDetailRsp.getError().getMsg(), R.drawable.toast_failed);
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.walkdog.WalkHomeActivity$2 */
    public class AnonymousClass2 extends AsyncHttpRespHandler {
        public final /* synthetic */ int val$offset;
        public final /* synthetic */ String val$until;

        public AnonymousClass2(int i2, String str2) {
            i = i2;
            str = str2;
        }

        @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
        public void onSuccess(int i2, Header[] headerArr, byte[] bArr) {
            super.onSuccess(i2, headerArr, bArr);
            WalkDailyDetailRsp walkDailyDetailRsp = (WalkDailyDetailRsp) this.gson.fromJson(this.responseResult, WalkDailyDetailRsp.class);
            if (walkDailyDetailRsp.getError() == null) {
                for (int i3 = 0; i3 <= i; i3++) {
                    WalkDataUtils.getOrCreateWalkDayDataForDay(Integer.valueOf(CommonUtils.getDateStringByOffsetForDay(str, i3)).intValue()).save();
                }
                for (int i4 = 0; i4 < walkDailyDetailRsp.getResult().size(); i4++) {
                    WalkDailyDetailRsp.ResultBean resultBean = walkDailyDetailRsp.getResult().get(i4);
                    WalkDayData orCreateWalkDayDataForDay = WalkDataUtils.getOrCreateWalkDayDataForDay(resultBean.getDay());
                    orCreateWalkDayDataForDay.setDistance(resultBean.getDistance());
                    orCreateWalkDayDataForDay.setDuration(resultBean.getDuration());
                    orCreateWalkDayDataForDay.setTimes(resultBean.getTimes());
                    orCreateWalkDayDataForDay.setGoal(resultBean.getGoal());
                    orCreateWalkDayDataForDay.setNeedUpdate(false);
                    if (resultBean.getDetails() != null) {
                        orCreateWalkDayDataForDay.setLastKey(resultBean.getDetails().getList().size() < 10 ? null : resultBean.getDetails().getLastKey());
                        for (int i5 = 0; i5 < resultBean.getDetails().getList().size(); i5++) {
                            WalkDataUtils.storeWalkDataForDay(resultBean.getDay(), resultBean.getDetails().getList().get(i5));
                        }
                    }
                    orCreateWalkDayDataForDay.save();
                }
                WalkHomeActivity.this.refreshFragments();
                return;
            }
            WalkHomeActivity.this.showShortToast(walkDailyDetailRsp.getError().getMsg(), R.drawable.toast_failed);
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.walkdog.WalkHomeActivity$3 */
    public class AnonymousClass3 extends BroadcastReceiver {
        public AnonymousClass3() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            action.hashCode();
            switch (action) {
                case "com.petkit.android.BROADCAST_WALK_STATE_UPDATE":
                    WalkHomeActivity.this.setTitleStatus();
                    break;
                case "com.petkit.android.BROADCAST_WALK_UPDATE":
                    WalkHomeActivity.this.setTitleStatus();
                    WalkHomeActivity.this.refreshFragments();
                    break;
                case "com.petkit.android.updateDog":
                    WalkHomeActivity.this.refreshBottomView();
                    break;
            }
        }
    }

    private void registerBoradcastReceiver() {
        this.mBroadcastReceiver = new BroadcastReceiver() { // from class: com.petkit.android.activities.walkdog.WalkHomeActivity.3
            public AnonymousClass3() {
            }

            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                action.hashCode();
                switch (action) {
                    case "com.petkit.android.BROADCAST_WALK_STATE_UPDATE":
                        WalkHomeActivity.this.setTitleStatus();
                        break;
                    case "com.petkit.android.BROADCAST_WALK_UPDATE":
                        WalkHomeActivity.this.setTitleStatus();
                        WalkHomeActivity.this.refreshFragments();
                        break;
                    case "com.petkit.android.updateDog":
                        WalkHomeActivity.this.refreshBottomView();
                        break;
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.BROADCAST_MSG_UPDATE_DOG);
        intentFilter.addAction(WalkDataUtils.BROADCAST_WALK_STATE_UPDATE);
        intentFilter.addAction(WalkDataUtils.BROADCAST_WALK_UPDATE);
        LocalBroadcastManager.getInstance(this).registerReceiver(this.mBroadcastReceiver, intentFilter);
    }

    private void unregisterBroadcastReceiver() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(this.mBroadcastReceiver);
    }

    private void initTopView() {
        this.mWeekViewPager = (ViewPager) findViewById(R.id.view_pager);
        try {
            WeekPagerAdapter weekPagerAdapter = new WeekPagerAdapter(this, DateUtil.parseISO8601Date("2019-01-01"), DateUtil.parseISO8601Date(CommonUtils.getDateStringByOffset(0, TimeZone.getDefault()), TimeZone.getDefault()), TimeZone.getDefault());
            this.mWeekPagerAdapter = weekPagerAdapter;
            weekPagerAdapter.setSelectDayBackground(R.drawable.solid_walkdog_color_circle_bg);
            this.mWeekPagerAdapter.setNoSelectDayBackground(R.drawable.solid_walkdog_color_circle_bg);
            this.mWeekViewPager.setAdapter(this.mWeekPagerAdapter);
            setWeekPagerToToday();
            this.mWeekViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.petkit.android.activities.walkdog.WalkHomeActivity.4
                @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                public void onPageScrollStateChanged(int i) {
                }

                @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                public void onPageScrolled(int i, float f, int i2) {
                }

                public AnonymousClass4() {
                }

                @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                public void onPageSelected(int i) {
                    if (WalkHomeActivity.this.mSelectWeekPagerIndex <= i) {
                        if (WalkHomeActivity.this.mSelectWeekPagerIndex < i) {
                            WalkHomeActivity.this.mSelectPagerIndex += 7;
                            if (WalkHomeActivity.this.mSelectPagerIndex >= ((BaseTabActivity) WalkHomeActivity.this).pagerAdapter.getCount()) {
                                WalkHomeActivity.this.mSelectPagerIndex = ((BaseTabActivity) r0).pagerAdapter.getCount() - 1;
                            }
                            ((BaseTabActivity) WalkHomeActivity.this).mViewPager.setCurrentItem(WalkHomeActivity.this.mSelectPagerIndex);
                        }
                    } else {
                        WalkHomeActivity walkHomeActivity = WalkHomeActivity.this;
                        walkHomeActivity.mSelectPagerIndex -= 7;
                        if (WalkHomeActivity.this.mSelectPagerIndex < 0) {
                            WalkHomeActivity.this.mSelectPagerIndex = 0;
                        }
                        ((BaseTabActivity) WalkHomeActivity.this).mViewPager.setCurrentItem(WalkHomeActivity.this.mSelectPagerIndex);
                    }
                    WalkHomeActivity.this.mSelectWeekPagerIndex = i;
                    WalkHomeActivity.this.mWeekPagerAdapter.updateCurrentIndex(i);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.walkdog.WalkHomeActivity$4 */
    public class AnonymousClass4 implements ViewPager.OnPageChangeListener {
        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrollStateChanged(int i) {
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrolled(int i, float f, int i2) {
        }

        public AnonymousClass4() {
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageSelected(int i) {
            if (WalkHomeActivity.this.mSelectWeekPagerIndex <= i) {
                if (WalkHomeActivity.this.mSelectWeekPagerIndex < i) {
                    WalkHomeActivity.this.mSelectPagerIndex += 7;
                    if (WalkHomeActivity.this.mSelectPagerIndex >= ((BaseTabActivity) WalkHomeActivity.this).pagerAdapter.getCount()) {
                        WalkHomeActivity.this.mSelectPagerIndex = ((BaseTabActivity) r0).pagerAdapter.getCount() - 1;
                    }
                    ((BaseTabActivity) WalkHomeActivity.this).mViewPager.setCurrentItem(WalkHomeActivity.this.mSelectPagerIndex);
                }
            } else {
                WalkHomeActivity walkHomeActivity = WalkHomeActivity.this;
                walkHomeActivity.mSelectPagerIndex -= 7;
                if (WalkHomeActivity.this.mSelectPagerIndex < 0) {
                    WalkHomeActivity.this.mSelectPagerIndex = 0;
                }
                ((BaseTabActivity) WalkHomeActivity.this).mViewPager.setCurrentItem(WalkHomeActivity.this.mSelectPagerIndex);
            }
            WalkHomeActivity.this.mSelectWeekPagerIndex = i;
            WalkHomeActivity.this.mWeekPagerAdapter.updateCurrentIndex(i);
        }
    }

    private void setWeekPagerToToday() {
        int i;
        Calendar calendar = Calendar.getInstance();
        if (calendar.getFirstDayOfWeek() == 1) {
            i = calendar.get(7) - 1;
        } else {
            i = calendar.get(7);
        }
        int count = this.mWeekPagerAdapter.getCount() - 1;
        this.mSelectWeekPagerIndex = count;
        this.mWeekViewPager.setCurrentItem(count);
        this.mWeekPagerAdapter.setCurrentDayIndex((i + 6) % 7);
    }

    @Override // com.petkit.android.activities.walkdog.fragment.WalkHomeFragment.IDataRefreshListener
    public void onRefresh(int i) {
        getList(String.valueOf(i), 0);
    }

    @Override // com.petkit.android.activities.walkdog.fragment.WalkHomeFragment.IDataRefreshListener
    public void onPageChanged(int i) {
        if (i == -1) {
            InterceptViewPager interceptViewPager = this.mViewPager;
            interceptViewPager.setCurrentItem(interceptViewPager.getCurrentItem() - 1);
        } else {
            if (i != 1) {
                return;
            }
            InterceptViewPager interceptViewPager2 = this.mViewPager;
            interceptViewPager2.setCurrentItem(interceptViewPager2.getCurrentItem() + 1);
        }
    }

    public List<Pet> findWalkPets() {
        Pet petById;
        List<Pet> arrayList = new ArrayList<>();
        if (!TextUtils.isEmpty(this.petId) && !this.petId.equals(PetUtils.ALL_DEVICE) && (petById = UserInforUtils.getPetById(this.petId)) != null) {
            arrayList.add(petById);
            return arrayList;
        }
        List<Pet> dogs = UserInforUtils.getCurrentLoginResult().getUser().getDogs(true);
        String stringSF = DataHelper.getStringSF(this, WalkDataUtils.EXTRA_WALK_SELECTED_PETS);
        if (!TextUtils.isEmpty(stringSF)) {
            arrayList = (List) new Gson().fromJson(stringSF, new TypeToken<List<Pet>>() { // from class: com.petkit.android.activities.walkdog.WalkHomeActivity.5
                public AnonymousClass5() {
                }
            }.getType());
            arrayList.retainAll(dogs);
            if (!CollectionUtil.isEmpty(arrayList)) {
                return arrayList;
            }
        }
        try {
            Date iSO8601Date = null;
            Date iSO8601Date2 = null;
            for (Pet pet : dogs) {
                if (pet.getType().getId() == 1) {
                    if (iSO8601Date2 == null || DateUtil.parseISO8601Date(pet.getCreatedAt()).before(iSO8601Date2)) {
                        this.petId = pet.getId();
                    }
                    iSO8601Date2 = DateUtil.parseISO8601Date(pet.getCreatedAt());
                }
            }
            if (TextUtils.isEmpty(this.petId) || this.petId.equals(PetUtils.ALL_DEVICE)) {
                for (Pet pet2 : dogs) {
                    if (iSO8601Date == null || DateUtil.parseISO8601Date(pet2.getCreatedAt()).before(iSO8601Date)) {
                        this.petId = pet2.getId();
                    }
                    iSO8601Date = DateUtil.parseISO8601Date(pet2.getCreatedAt());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        arrayList.add(UserInforUtils.getPetById(this.petId));
        return arrayList;
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.walkdog.WalkHomeActivity$5 */
    public class AnonymousClass5 extends TypeToken<List<Pet>> {
        public AnonymousClass5() {
        }
    }

    @Override // com.petkit.android.activities.feeder.adapter.WeekPagerAdapter.IDaySelectListener
    public void onDaySelect(String str) {
        try {
            int iDaysCountBetween = CommonUtils.daysCountBetween("2019-01-01", str, TimeZone.getDefault());
            this.mSelectPagerIndex = iDaysCountBetween;
            this.mViewPager.setCurrentItem(iDaysCountBetween);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

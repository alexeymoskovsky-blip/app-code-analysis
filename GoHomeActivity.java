package com.petkit.android.activities.go;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;
import com.jess.arms.widget.InterceptViewPager;
import com.petkit.android.activities.base.BaseTabActivity;
import com.petkit.android.activities.common.utils.DeviceCenterUtils;
import com.petkit.android.activities.feeder.adapter.WeekPagerAdapter;
import com.petkit.android.activities.go.ApiResponse.GoDailyDetailRsp;
import com.petkit.android.activities.go.adapter.GoHomeFragmentAdapter;
import com.petkit.android.activities.go.fragment.GoHomeFragment;
import com.petkit.android.activities.go.model.GoDayData;
import com.petkit.android.activities.go.model.GoRecord;
import com.petkit.android.activities.go.setting.GoSettingActivity;
import com.petkit.android.activities.go.utils.GoDataUtils;
import com.petkit.android.activities.go.widget.GoIntroDialog;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.DateUtil;
import com.petkit.oversea.R;
import cz.msebera.android.httpclient.Header;
import java.util.Calendar;
import java.util.HashMap;
import java.util.TimeZone;

/* JADX INFO: loaded from: classes3.dex */
public class GoHomeActivity extends BaseTabActivity implements GoHomeFragment.IDataRefreshListener, WeekPagerAdapter.IDaySelectListener {
    private BroadcastReceiver mBroadcastReceiver;
    private GoRecord mGoRecord;
    private int mSelectPagerIndex;
    private int mSelectWeekPagerIndex;
    private WeekPagerAdapter mWeekPagerAdapter;
    private ViewPager mWeekViewPager;

    @Override // com.petkit.android.activities.base.BaseTabActivity, com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        long longExtra;
        if (bundle == null) {
            longExtra = getIntent().getLongExtra(GoDataUtils.EXTRA_GO_ID, 0L);
        } else {
            longExtra = bundle.getLong(GoDataUtils.EXTRA_GO_ID);
        }
        this.mGoRecord = GoDataUtils.getGoRecordById(longExtra);
        this.indicatorType = 1;
        super.onCreate(bundle);
        if (DeviceCenterUtils.checkDisplayGoIntro()) {
            startActivityForResult(GoIntroActivity.class, 4657);
        }
        registerBoradcastReceiver();
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putLong(GoDataUtils.EXTRA_GO_ID, this.mGoRecord.getDeviceId());
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        unregisterBroadcastReceiver();
    }

    @Override // com.petkit.android.activities.base.BaseTabActivity, com.petkit.android.activities.base.BaseActivity
    public void setupViews() {
        if (this.mGoRecord == null) {
            finish();
            return;
        }
        super.setupViews();
        setTitleLeftButton(R.drawable.icon_back_black_normal);
        setDividerLineVisibility(8);
        setTopView(R.layout.layout_walk_top_view);
        setBackgroundResourceId(R.color.white);
        initTopView();
        this.mSelectPagerIndex = this.pagerAdapter.getCount() - 1;
        this.mViewPager.setCurrentItem(this.pagerAdapter.getCount() - 1);
        this.mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.petkit.android.activities.go.GoHomeActivity.1
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i, float f, int i2) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int i) {
                if (GoHomeActivity.this.mSelectPagerIndex > i) {
                    if (GoHomeActivity.this.mWeekPagerAdapter.getCurrentDayIndex() != 0) {
                        GoHomeActivity.this.mWeekPagerAdapter.setCurrentDayIndex(GoHomeActivity.this.mWeekPagerAdapter.getCurrentDayIndex() - 1);
                    } else {
                        GoHomeActivity.this.mSelectWeekPagerIndex = r0.mWeekViewPager.getCurrentItem() - 1;
                        GoHomeActivity.this.mWeekViewPager.setCurrentItem(GoHomeActivity.this.mSelectWeekPagerIndex);
                        GoHomeActivity.this.mWeekPagerAdapter.setCurrentDayIndex(6);
                    }
                } else if (GoHomeActivity.this.mSelectPagerIndex < i) {
                    if (GoHomeActivity.this.mWeekPagerAdapter.getCurrentDayIndex() != 6) {
                        GoHomeActivity.this.mWeekPagerAdapter.setCurrentDayIndex(GoHomeActivity.this.mWeekPagerAdapter.getCurrentDayIndex() + 1);
                    } else {
                        GoHomeActivity goHomeActivity = GoHomeActivity.this;
                        goHomeActivity.mSelectWeekPagerIndex = goHomeActivity.mWeekViewPager.getCurrentItem() + 1;
                        GoHomeActivity.this.mWeekViewPager.setCurrentItem(GoHomeActivity.this.mSelectWeekPagerIndex);
                        GoHomeActivity.this.mWeekPagerAdapter.setCurrentDayIndex(0);
                    }
                }
                GoHomeActivity.this.mSelectPagerIndex = i;
            }
        });
        setTitleStatus();
        this.mGoRecord.clearNumberUnread(this);
    }

    @Override // com.petkit.android.activities.base.BaseTabActivity
    public void initAdapter() {
        this.pagerAdapter = new GoHomeFragmentAdapter(getSupportFragmentManager(), this, this.mGoRecord.getDeviceId());
    }

    @Override // com.petkit.android.activities.base.BaseActivity, android.view.View.OnClickListener
    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.title_right_image) {
            Bundle bundle = new Bundle();
            bundle.putLong(GoDataUtils.EXTRA_GO_ID, this.mGoRecord.getDeviceId());
            startActivityWithData(GoSettingActivity.class, bundle, false);
        }
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == -1 && i == 4657) {
            new GoIntroDialog(this).show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshFragments() {
        if (isFinishing()) {
            return;
        }
        ((GoHomeFragmentAdapter) this.pagerAdapter).setNeedRefresh(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setTitleStatus() {
        setTitle(this.mGoRecord.getName(), CommonUtils.getColorById(R.color.black));
        setTitleRightImageView(DeviceCenterUtils.isGoNeedOtaById(this.mGoRecord.getDeviceId()) ? R.drawable.btn_setting_with_notify_flag : R.drawable.btn_setting, this);
    }

    private void getList(final String str, final int i) {
        if (this.mGoRecord == null) {
            return;
        }
        HashMap map = new HashMap();
        map.put("id", String.valueOf(this.mGoRecord.getDeviceId()));
        map.put("since", CommonUtils.getDateStringByOffsetForDay(str, i));
        map.put("until", str);
        map.put("detailsLimit", String.valueOf(10));
        post(ApiTools.SAMPLE_API_GO_DAILY_DATA, map, new AsyncHttpRespHandler() { // from class: com.petkit.android.activities.go.GoHomeActivity.2
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i2, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i2, headerArr, bArr);
                GoDailyDetailRsp goDailyDetailRsp = (GoDailyDetailRsp) this.gson.fromJson(this.responseResult, GoDailyDetailRsp.class);
                if (goDailyDetailRsp.getError() == null) {
                    for (int i3 = 0; i3 <= i; i3++) {
                        GoDataUtils.getOrCreateGoDayDataForDay(GoHomeActivity.this.mGoRecord.getDeviceId(), Integer.valueOf(CommonUtils.getDateStringByOffsetForDay(str, i3)).intValue()).save();
                    }
                    for (int i4 = 0; i4 < goDailyDetailRsp.getResult().size(); i4++) {
                        GoDailyDetailRsp.ResultBean resultBean = goDailyDetailRsp.getResult().get(i4);
                        GoDayData orCreateGoDayDataForDay = GoDataUtils.getOrCreateGoDayDataForDay(GoHomeActivity.this.mGoRecord.getDeviceId(), resultBean.getDay());
                        orCreateGoDayDataForDay.setDistance(resultBean.getDistance());
                        orCreateGoDayDataForDay.setDuration(resultBean.getDuration());
                        orCreateGoDayDataForDay.setTimes(resultBean.getTimes());
                        orCreateGoDayDataForDay.setGoal(resultBean.getGoal());
                        orCreateGoDayDataForDay.setNeedUpdate(false);
                        if (resultBean.getDetails() != null) {
                            orCreateGoDayDataForDay.setLastKey(resultBean.getDetails().getList().size() < 10 ? null : resultBean.getDetails().getLastKey());
                            for (int i5 = 0; i5 < resultBean.getDetails().getList().size(); i5++) {
                                GoDataUtils.storeGoWalkDataForDay(GoHomeActivity.this.mGoRecord.getDeviceId(), resultBean.getDay(), resultBean.getDetails().getList().get(i5));
                            }
                        }
                        orCreateGoDayDataForDay.save();
                    }
                    GoHomeActivity.this.refreshFragments();
                    return;
                }
                GoHomeActivity.this.showShortToast(goDailyDetailRsp.getError().getMsg(), R.drawable.toast_failed);
            }
        });
    }

    private void registerBoradcastReceiver() {
        this.mBroadcastReceiver = new BroadcastReceiver() { // from class: com.petkit.android.activities.go.GoHomeActivity.3
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                action.hashCode();
                switch (action) {
                    case "com.petkit.android.BROADCAST_GO_UPDATE":
                        GoHomeActivity goHomeActivity = GoHomeActivity.this;
                        goHomeActivity.mGoRecord = GoDataUtils.getGoRecordById(goHomeActivity.mGoRecord.getDeviceId());
                        GoHomeActivity.this.setTitleStatus();
                        GoHomeActivity.this.refreshFragments();
                        break;
                    case "com.petkit.android.BROADCAST_GO_STATE_UPDATE":
                        GoHomeActivity goHomeActivity2 = GoHomeActivity.this;
                        goHomeActivity2.mGoRecord = GoDataUtils.getGoRecordById(goHomeActivity2.mGoRecord.getDeviceId());
                        GoHomeActivity.this.setTitleStatus();
                        break;
                    case "com.petkit.android.BROADCAST_GO_DELETE":
                        if (intent.getLongExtra(GoDataUtils.EXTRA_GO_ID, 0L) == GoHomeActivity.this.mGoRecord.getDeviceId()) {
                            GoHomeActivity.this.finish();
                            break;
                        }
                        break;
                    case "com.petkit.android.BROADCAST_GO_WALKING_MAP_START":
                        GoHomeActivity.this.finish();
                        break;
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(GoDataUtils.BROADCAST_GO_STATE_UPDATE);
        intentFilter.addAction(GoDataUtils.BROADCAST_GO_DELETE);
        intentFilter.addAction(GoDataUtils.BROADCAST_GO_UPDATE);
        intentFilter.addAction(GoDataUtils.BROADCAST_GO_WALKING_MAP_START);
        LocalBroadcastManager.getInstance(this).registerReceiver(this.mBroadcastReceiver, intentFilter);
    }

    private void unregisterBroadcastReceiver() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(this.mBroadcastReceiver);
    }

    private void initTopView() {
        this.mWeekViewPager = (ViewPager) findViewById(R.id.view_pager);
        try {
            WeekPagerAdapter weekPagerAdapter = new WeekPagerAdapter(this, DateUtil.parseISO8601Date(this.mGoRecord.getCreatedAt()), DateUtil.parseISO8601Date(CommonUtils.getDateStringByOffset(0, TimeZone.getDefault()), TimeZone.getDefault()), TimeZone.getDefault());
            this.mWeekPagerAdapter = weekPagerAdapter;
            weekPagerAdapter.setSelectDayBackground(R.drawable.solid_walkdog_color_circle_bg);
            this.mWeekPagerAdapter.setNoSelectDayBackground(R.drawable.solid_walkdog_color_circle_bg);
            this.mWeekViewPager.setAdapter(this.mWeekPagerAdapter);
            setWeekPagerToToday();
            this.mWeekViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.petkit.android.activities.go.GoHomeActivity.4
                @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                public void onPageScrollStateChanged(int i) {
                }

                @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                public void onPageScrolled(int i, float f, int i2) {
                }

                @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                public void onPageSelected(int i) {
                    if (GoHomeActivity.this.mSelectWeekPagerIndex <= i) {
                        if (GoHomeActivity.this.mSelectWeekPagerIndex < i) {
                            GoHomeActivity.this.mSelectPagerIndex += 7;
                            if (GoHomeActivity.this.mSelectPagerIndex >= ((BaseTabActivity) GoHomeActivity.this).pagerAdapter.getCount()) {
                                GoHomeActivity.this.mSelectPagerIndex = ((BaseTabActivity) r0).pagerAdapter.getCount() - 1;
                            }
                            ((BaseTabActivity) GoHomeActivity.this).mViewPager.setCurrentItem(GoHomeActivity.this.mSelectPagerIndex);
                        }
                    } else {
                        GoHomeActivity goHomeActivity = GoHomeActivity.this;
                        goHomeActivity.mSelectPagerIndex -= 7;
                        if (GoHomeActivity.this.mSelectPagerIndex < 0) {
                            GoHomeActivity.this.mSelectPagerIndex = 0;
                        }
                        ((BaseTabActivity) GoHomeActivity.this).mViewPager.setCurrentItem(GoHomeActivity.this.mSelectPagerIndex);
                    }
                    GoHomeActivity.this.mSelectWeekPagerIndex = i;
                    GoHomeActivity.this.mWeekPagerAdapter.updateCurrentIndex(i);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
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

    @Override // com.petkit.android.activities.go.fragment.GoHomeFragment.IDataRefreshListener
    public void onRefresh(int i) {
        getList(String.valueOf(i), 0);
    }

    @Override // com.petkit.android.activities.go.fragment.GoHomeFragment.IDataRefreshListener
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

    @Override // com.petkit.android.activities.feeder.adapter.WeekPagerAdapter.IDaySelectListener
    public void onDaySelect(String str) {
        try {
            int iDaysCountBetween = CommonUtils.daysCountBetween(DateUtil.date2Str(DateUtil.parseISO8601Date(this.mGoRecord.getCreatedAt()), "yyyy-MM-dd"), str, TimeZone.getDefault());
            this.mSelectPagerIndex = iDaysCountBetween;
            this.mViewPager.setCurrentItem(iDaysCountBetween);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

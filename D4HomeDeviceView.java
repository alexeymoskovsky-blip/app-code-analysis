package com.petkit.android.activities.petkitBleDevice.d4.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.RelativeSizeSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.motion.widget.Key;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.binioter.guideview.Guide;
import com.binioter.guideview.GuideBuilder;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.Consts;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.utils.DeviceUtils;
import com.petkit.android.activities.feeder.model.DifferentFeedPlan;
import com.petkit.android.activities.home.utils.GuidePetTipAndBtnAndLineComponent;
import com.petkit.android.activities.home.utils.GuideTipAndBtnAndLineComponent;
import com.petkit.android.activities.mall.mode.PromoteData;
import com.petkit.android.activities.mall.widget.PromoteView;
import com.petkit.android.activities.petkitBleDevice.BleDeviceBindNetWorkActivity;
import com.petkit.android.activities.petkitBleDevice.BleDeviceWifiSettingActivity;
import com.petkit.android.activities.petkitBleDevice.d3.widget.BarChartView;
import com.petkit.android.activities.petkitBleDevice.d3.widget.BleFeederTimeView;
import com.petkit.android.activities.petkitBleDevice.d4.D4HomeActivity;
import com.petkit.android.activities.petkitBleDevice.d4.adapter.D4HomeRecordAdapter;
import com.petkit.android.activities.petkitBleDevice.d4.mode.D4DailyFeeds;
import com.petkit.android.activities.petkitBleDevice.d4.mode.D4DateFeedData;
import com.petkit.android.activities.petkitBleDevice.d4.mode.D4Record;
import com.petkit.android.activities.petkitBleDevice.d4.utils.D4Utils;
import com.petkit.android.activities.petkitBleDevice.d4.widget.D4Histogram;
import com.petkit.android.activities.petkitBleDevice.d4s.widget.D4sHomeProgressView;
import com.petkit.android.activities.petkitBleDevice.mode.BleMenuItem;
import com.petkit.android.activities.petkitBleDevice.t4.mode.RelatedProductsInfor;
import com.petkit.android.activities.petkitBleDevice.utils.BleDeviceUtils;
import com.petkit.android.activities.petkitBleDevice.w5.guide.ConfirmListener;
import com.petkit.android.activities.petkitBleDevice.w5.guide.GuideParam;
import com.petkit.android.activities.petkitBleDevice.widget.BleDeviceHomeMenuView;
import com.petkit.android.activities.petkitBleDevice.widget.BleDeviceHomeOfflineWarnWindow;
import com.petkit.android.activities.petkitBleDevice.widget.PetkitSlidingUpPanelLayout;
import com.petkit.android.activities.petkitBleDevice.widget.ScrollRecyclerView;
import com.petkit.android.activities.petkitBleDevice.widget.ScrollViewWithListener2;
import com.petkit.android.activities.universalWindow.NormalCenterTipWindow;
import com.petkit.android.model.ItemsBean;
import com.petkit.android.utils.CommonUtil;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.DateUtil;
import com.petkit.android.utils.PetkitLog;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.oversea.R;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.simple.eventbus.EventBus;

/* JADX INFO: loaded from: classes4.dex */
public class D4HomeDeviceView extends LinearLayout implements PetkitSlidingUpPanelLayout.PanelSlideListener, View.OnClickListener, BleDeviceHomeMenuView.OnMenuClickListener, D4HomeRecordAdapter.OnClickListener, BleDeviceHomeOfflineWarnWindow.OfflineClickListener, BarChartView.ChartOnClickListener {
    private int accessoryConsumables;
    private Activity activity;
    private float alpha;
    private List<AnimatorSet> animatorLeftSetList;
    private BarChartView barChartView;
    private BleDeviceHomeMenuView bleMenuView;
    private ChartOnClickListener chartOnClickListener;
    private int clickOuterIndex;
    private View contentView;
    private D4DailyFeeds d4DailyFeeds;
    private D4FoodWarnWindow d4FoodWarnWindow;
    private D4Histogram d4Histogram;
    private D4HomeRecordAdapter d4HomeRecordAdapter;
    private D4Record d4Record;
    private BleFeederTimeView d4TimeView;
    private ScrollViewWithListener2 d4ViewLayout;
    NormalCenterTipWindow d4WarnWindow;
    NormalCenterTipWindow d4sFoodWarnWindow;
    private SimpleDateFormat dateFormat;
    private int day;
    private Disposable disposable;
    ValueAnimator expandValueAnimator;
    private TextView feederInlet;
    private FrameLayout flTopPanel;
    private Guide guide1;
    private Guide guide2;
    private Guide guide3;
    private HorizontalScrollView hsvChart;
    boolean isAniStart;
    boolean isRunning;
    private boolean isShowGuide;
    private boolean isUnMask;
    boolean isUp;
    private ImageView ivAccessoryConsumables;
    private ImageView ivLeftArrow;
    private ImageView ivUpArrow;
    private ImageView ivWarnText;
    int lastValue;
    private LinearLayout llAccessoryConsumables;
    private LinearLayout llBottomMenuParentView;
    private LinearLayout llBtnAndWarnPanel;
    private LinearLayout llChartPanel;
    private LinearLayout llD4ViewBtnPanel;
    private LinearLayout llHistoryRecordPanel;
    private LinearLayout llManualFeedNum1;
    private LinearLayout llWarnPanel;
    private LinearLayout llWarnText;
    private Context mContext;
    private float mSlideOffset;
    private VelocityTracker mVelocityTracker;
    private MainHandler mainHandler;
    private int measuredWidth;
    private MenuOnClickListener menuOnClickListener;
    private OnHistogramPageChange onHistogramPageChange;
    private D4sHomeProgressView pbBucket1;
    private PromoteView promoteView;
    private int recordType;
    ValueAnimator reduceValueAnimator;
    private RelativeLayout rlBtnAndWarnPanel;
    private RelativeLayout rlD4ViewBtnPanel;
    private RelativeLayout rlDailyData;
    private RelativeLayout rlData1;
    private RelativeLayout rlRightTopWindow;
    private RelativeLayout rlTimePanel;
    private RelativeLayout rlTopView;
    private RelativeLayout rlViewD4DeviceCenter;
    private ScrollRecyclerView rvD4RecordView;
    private RelativeLayout scrollView;
    private float tempY;
    private TextView tvAccessoryConsumables;
    private TextView tvBucket1;
    private TextView tvChartEmpty;
    private TextView tvD4BottomLeft;
    private TextView tvD4BottomPlan;
    private TextView tvD4Left;
    private TextView tvD4LeftDes;
    private TextView tvD4Plan;
    private TextView tvD4PlanDes;
    private TextView tvD4PlanFeed;
    private TextView tvD4RealFeed;
    private TextView tvHasOut1;
    private TextView tvHistoryMore;
    private TextView tvHistoryRecord;
    private TextView tvHistoryStatisticDate;
    private TextView tvLittleWarnText;
    private TextView tvManualFeedNum1;
    private TextView tvPlanAmount;
    private TextView tvPlanHasFinishedNum1;
    private TextView tvPlanPrompt;
    private TextView tvSmallArcCenterText;
    private TextView tvStatisticsDate;
    private TextView tvStatisticsMonth;
    private TextView tvStatisticsTitle;
    private TextView tvStatisticsWeek;
    private TextView tvTitleStatus;
    private TextView tvTodayFoodContent;
    private TextView tvTodayFoodTitle;
    private TextView tvWarnText;
    private int tvWidth;
    private View viewCenter;

    public interface ChartOnClickListener {
        void onChartClick(int i, String str);
    }

    public interface MenuOnClickListener {
        void onBottomMenuClick(MenuType menuType);
    }

    public enum MenuType {
        FEED_NOW,
        STOP_FEED,
        FEED_PLAN,
        TYPE_MONTH,
        TYPE_WEEK,
        TYPE_DATE,
        FEEDER_INLET,
        WARN_LIST,
        DATE_PICKER,
        NO_MORE_REMIND
    }

    public interface OnHistogramPageChange {
        void pageChange(int i);
    }

    @Override // com.petkit.android.activities.petkitBleDevice.d3.widget.BarChartView.ChartOnClickListener
    public void onChartClick(int i) {
    }

    @Override // com.petkit.android.activities.petkitBleDevice.widget.PetkitSlidingUpPanelLayout.PanelSlideListener
    public void onPanelStateChanged(View view, PetkitSlidingUpPanelLayout.PanelState panelState, PetkitSlidingUpPanelLayout.PanelState panelState2) {
    }

    @Override // com.petkit.android.activities.petkitBleDevice.d4.adapter.D4HomeRecordAdapter.OnClickListener
    public void onViewClick(ItemsBean itemsBean, int i) {
    }

    public void setFeedingAmount(int i) {
    }

    @RequiresApi(api = 23)
    public D4HomeDeviceView(Context context) {
        super(context);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtil.DATE_FORMAT_7);
        this.dateFormat = simpleDateFormat;
        this.recordType = 3;
        this.day = Integer.parseInt(simpleDateFormat.format(Calendar.getInstance().getTime()));
        this.isShowGuide = false;
        this.isAniStart = false;
        this.lastValue = 0;
        this.mContext = context;
        this.activity = (Activity) context;
        initView();
    }

    @RequiresApi(api = 23)
    public D4HomeDeviceView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtil.DATE_FORMAT_7);
        this.dateFormat = simpleDateFormat;
        this.recordType = 3;
        this.day = Integer.parseInt(simpleDateFormat.format(Calendar.getInstance().getTime()));
        this.isShowGuide = false;
        this.isAniStart = false;
        this.lastValue = 0;
        this.mContext = context;
        this.activity = (Activity) context;
        initView();
    }

    @RequiresApi(api = 23)
    public D4HomeDeviceView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtil.DATE_FORMAT_7);
        this.dateFormat = simpleDateFormat;
        this.recordType = 3;
        this.day = Integer.parseInt(simpleDateFormat.format(Calendar.getInstance().getTime()));
        this.isShowGuide = false;
        this.isAniStart = false;
        this.lastValue = 0;
        this.mContext = context;
        this.activity = (Activity) context;
        initView();
    }

    @RequiresApi(api = 23)
    private void initView() {
        this.animatorLeftSetList = new ArrayList();
        this.mVelocityTracker = VelocityTracker.obtain();
        this.mainHandler = new MainHandler(this.mContext);
        View viewInflate = LayoutInflater.from(this.mContext).inflate(R.layout.layout_d4_home_device_view, (ViewGroup) null);
        this.contentView = viewInflate;
        addView(viewInflate, -1, -1);
        this.ivAccessoryConsumables = (ImageView) this.contentView.findViewById(R.id.iv_accessory_consumables);
        this.tvAccessoryConsumables = (TextView) this.contentView.findViewById(R.id.tv_accessory_consumables);
        this.llAccessoryConsumables = (LinearLayout) this.contentView.findViewById(R.id.ll_accessory_consumables);
        this.d4ViewLayout = (ScrollViewWithListener2) this.contentView.findViewById(R.id.d4_view_layout);
        this.llChartPanel = (LinearLayout) this.contentView.findViewById(R.id.ll_chart_panel);
        this.llD4ViewBtnPanel = (LinearLayout) this.contentView.findViewById(R.id.ll_d4_view_btn_panel);
        this.llWarnPanel = (LinearLayout) this.contentView.findViewById(R.id.ll_warn_panel);
        this.rlViewD4DeviceCenter = (RelativeLayout) this.contentView.findViewById(R.id.rl_view_d4_device_center);
        this.viewCenter = this.contentView.findViewById(R.id.view_center);
        this.tvD4Left = (TextView) this.contentView.findViewById(R.id.tv_d4_left);
        this.tvD4BottomLeft = (TextView) this.contentView.findViewById(R.id.tv_d4_bottom_left);
        this.tvD4LeftDes = (TextView) this.contentView.findViewById(R.id.tv_d4_left_des);
        this.tvD4Plan = (TextView) this.contentView.findViewById(R.id.tv_d4_plan);
        this.tvD4BottomPlan = (TextView) this.contentView.findViewById(R.id.tv_d4_bottom_plan);
        this.tvD4PlanDes = (TextView) this.contentView.findViewById(R.id.tv_d4_plan_des);
        this.rlD4ViewBtnPanel = (RelativeLayout) this.contentView.findViewById(R.id.rl_d4_view_btn_panel);
        this.llBtnAndWarnPanel = (LinearLayout) this.contentView.findViewById(R.id.ll_btn_and_warn_panel);
        this.tvWarnText = (TextView) this.contentView.findViewById(R.id.tv_warn_text);
        this.ivWarnText = (ImageView) this.contentView.findViewById(R.id.iv_warn_text);
        this.llWarnText = (LinearLayout) this.contentView.findViewById(R.id.ll_warn_text);
        this.tvLittleWarnText = (TextView) this.contentView.findViewById(R.id.tv_little_warn_text);
        this.rlBtnAndWarnPanel = (RelativeLayout) this.contentView.findViewById(R.id.rl_btn_and_warn_panel);
        this.tvPlanPrompt = (TextView) this.contentView.findViewById(R.id.tv_plan_prompt);
        this.ivUpArrow = (ImageView) this.contentView.findViewById(R.id.iv_up_arrow);
        this.rlTopView = (RelativeLayout) this.contentView.findViewById(R.id.rl_top_view);
        this.flTopPanel = (FrameLayout) this.contentView.findViewById(R.id.fl_top_panel);
        this.bleMenuView = (BleDeviceHomeMenuView) this.contentView.findViewById(R.id.ble_menu_view);
        this.tvTodayFoodTitle = (TextView) this.contentView.findViewById(R.id.tv_today_food_title);
        this.tvTodayFoodContent = (TextView) this.contentView.findViewById(R.id.tv_today_food_content);
        this.d4TimeView = (BleFeederTimeView) this.contentView.findViewById(R.id.d4_time_view);
        this.rlTimePanel = (RelativeLayout) this.contentView.findViewById(R.id.rl_time_panel);
        this.scrollView = (RelativeLayout) this.contentView.findViewById(R.id.rl_d4_view_layout);
        this.tvStatisticsTitle = (TextView) this.contentView.findViewById(R.id.tv_statistics_title);
        this.tvStatisticsDate = (TextView) this.contentView.findViewById(R.id.tv_statistics_date);
        this.tvStatisticsWeek = (TextView) this.contentView.findViewById(R.id.tv_statistics_week);
        this.tvStatisticsMonth = (TextView) this.contentView.findViewById(R.id.tv_statistics_month);
        this.tvChartEmpty = (TextView) this.contentView.findViewById(R.id.tv_chart_empty);
        this.barChartView = (BarChartView) this.contentView.findViewById(R.id.barChartView);
        this.hsvChart = (HorizontalScrollView) this.contentView.findViewById(R.id.hsv_chart);
        ScrollRecyclerView scrollRecyclerView = (ScrollRecyclerView) this.contentView.findViewById(R.id.rv_d4_recordView);
        this.rvD4RecordView = scrollRecyclerView;
        scrollRecyclerView.setLayoutManager(new LinearLayoutManager(this.mContext));
        this.llHistoryRecordPanel = (LinearLayout) this.contentView.findViewById(R.id.ll_history_record_panel);
        this.llBottomMenuParentView = (LinearLayout) this.contentView.findViewById(R.id.ll_bottom_menu_parent_view);
        this.tvManualFeedNum1 = (TextView) this.contentView.findViewById(R.id.tv_manual_feed_num1);
        this.tvPlanHasFinishedNum1 = (TextView) this.contentView.findViewById(R.id.tv_plan_has_been_finished_num1);
        this.rlData1 = (RelativeLayout) this.contentView.findViewById(R.id.rl_data1);
        this.llManualFeedNum1 = (LinearLayout) this.contentView.findViewById(R.id.ll_manual_feed_num1);
        this.tvSmallArcCenterText = (TextView) this.contentView.findViewById(R.id.tv_small_arc_center_text);
        this.tvHasOut1 = (TextView) this.contentView.findViewById(R.id.tv_has_out1);
        TextView textView = (TextView) this.contentView.findViewById(R.id.tv_bucket1);
        this.tvBucket1 = textView;
        textView.setVisibility(0);
        D4sHomeProgressView d4sHomeProgressView = (D4sHomeProgressView) this.contentView.findViewById(R.id.pb_bucket1);
        this.pbBucket1 = d4sHomeProgressView;
        d4sHomeProgressView.setProgressColor(getResources().getColor(R.color.d4_main_yellow), getResources().getColor(R.color.d4s_progress_gray));
        this.tvTitleStatus = (TextView) this.contentView.findViewById(R.id.tv_title_status);
        this.tvPlanAmount = (TextView) this.contentView.findViewById(R.id.tv_plan_amount1);
        this.promoteView = (PromoteView) this.contentView.findViewById(R.id.promoteView);
        this.feederInlet = (TextView) this.contentView.findViewById(R.id.feeder_inlet);
        this.rlDailyData = (RelativeLayout) this.contentView.findViewById(R.id.rl_daily_data);
        D4Histogram d4Histogram = (D4Histogram) this.contentView.findViewById(R.id.d4_histogram);
        this.d4Histogram = d4Histogram;
        d4Histogram.setOnPageChanged(new D4Histogram.OnPageChanged() { // from class: com.petkit.android.activities.petkitBleDevice.d4.widget.D4HomeDeviceView.1
            @Override // com.petkit.android.activities.petkitBleDevice.d4.widget.D4Histogram.OnPageChanged
            public void change(int i) {
                D4HomeDeviceView.this.onHistogramPageChange.pageChange(i);
                D4HomeDeviceView.this.stopLeftAni();
            }
        });
        this.tvHistoryMore = (TextView) this.contentView.findViewById(R.id.tv_history_more);
        TextView textView2 = (TextView) this.contentView.findViewById(R.id.tv_history_statistic_date);
        this.tvHistoryStatisticDate = textView2;
        textView2.setOnTouchListener(new View.OnTouchListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4.widget.D4HomeDeviceView.2
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == 1 && D4HomeDeviceView.this.tvHistoryStatisticDate.getCompoundDrawables()[2] != null && motionEvent.getX() > D4HomeDeviceView.this.tvHistoryStatisticDate.getWidth() - D4HomeDeviceView.this.tvHistoryStatisticDate.getCompoundDrawables()[2].getBounds().width()) {
                    D4HomeDeviceView.this.menuOnClickListener.onBottomMenuClick(MenuType.DATE_PICKER);
                }
                return true;
            }
        });
        this.tvHistoryRecord = (TextView) this.contentView.findViewById(R.id.tv_history_record);
        this.ivLeftArrow = (ImageView) this.contentView.findViewById(R.id.iv_left_arrow);
        this.rlRightTopWindow = (RelativeLayout) this.contentView.findViewById(R.id.rl_right_top_parent);
        this.tvD4RealFeed = (TextView) this.contentView.findViewById(R.id.tv_d4_real_feed);
        this.tvD4PlanFeed = (TextView) this.contentView.findViewById(R.id.tv_d4_plan_feed);
        this.bleMenuView.setDeviceType(11, 0);
        this.bleMenuView.setOnMenuClickListener(this);
        this.barChartView.setChartOnClickListener(this);
        this.tvStatisticsMonth.setOnClickListener(this);
        this.tvStatisticsDate.setOnClickListener(this);
        this.tvStatisticsWeek.setOnClickListener(this);
        this.tvWarnText.setOnClickListener(this);
        this.tvHistoryMore.setOnClickListener(this);
        this.feederInlet.setOnClickListener(this);
        this.tvSmallArcCenterText.setOnClickListener(this);
        this.tvTitleStatus.setOnClickListener(this);
        this.tvLittleWarnText.setOnClickListener(this);
        this.llAccessoryConsumables.setOnClickListener(this);
        this.tvAccessoryConsumables.setOnClickListener(this);
        this.ivAccessoryConsumables.setOnClickListener(this);
        this.mainHandler.postDelayed(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.d4.widget.D4HomeDeviceView.3
            @Override // java.lang.Runnable
            public void run() {
                D4HomeDeviceView.this.activity.getWindow().getDecorView().setSystemUiVisibility(0);
                D4HomeDeviceView.this.initViewSizeAndPosition();
            }
        }, 200L);
        this.d4ViewLayout.setScrollviewOnTouchListener(new ScrollViewWithListener2.ScrollviewOnTouchListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4.widget.D4HomeDeviceView.4
            @Override // com.petkit.android.activities.petkitBleDevice.widget.ScrollViewWithListener2.ScrollviewOnTouchListener
            public void finishScroll(boolean z) {
            }

            @Override // com.petkit.android.activities.petkitBleDevice.widget.ScrollViewWithListener2.ScrollviewOnTouchListener
            public void onScrolling(int i, int i2, int i3, int i4) {
                if (i2 - i4 > 5) {
                    Log.d("scrolling", "up");
                    D4HomeDeviceView d4HomeDeviceView = D4HomeDeviceView.this;
                    d4HomeDeviceView.isUp = true;
                    if (!d4HomeDeviceView.isAniStart && d4HomeDeviceView.bleMenuView.getVisibility() == 0) {
                        Animation animationLoadAnimation = AnimationUtils.loadAnimation(D4HomeDeviceView.this.activity, R.anim.ble_menu_slide_out_to_bottom);
                        animationLoadAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4.widget.D4HomeDeviceView.4.1
                            @Override // android.view.animation.Animation.AnimationListener
                            public void onAnimationRepeat(Animation animation) {
                            }

                            @Override // android.view.animation.Animation.AnimationListener
                            public void onAnimationStart(Animation animation) {
                            }

                            @Override // android.view.animation.Animation.AnimationListener
                            public void onAnimationEnd(Animation animation) {
                                D4HomeDeviceView d4HomeDeviceView2 = D4HomeDeviceView.this;
                                d4HomeDeviceView2.isAniStart = false;
                                d4HomeDeviceView2.bleMenuView.setVisibility(8);
                            }
                        });
                        D4HomeDeviceView.this.bleMenuView.startAnimation(animationLoadAnimation);
                        D4HomeDeviceView.this.isAniStart = true;
                    }
                } else if (i4 - i2 > 5) {
                    D4HomeDeviceView d4HomeDeviceView2 = D4HomeDeviceView.this;
                    d4HomeDeviceView2.isUp = false;
                    if (!d4HomeDeviceView2.isAniStart && d4HomeDeviceView2.bleMenuView.getVisibility() == 8) {
                        Animation animationLoadAnimation2 = AnimationUtils.loadAnimation(D4HomeDeviceView.this.activity, R.anim.panel_slide_in_from_bottom);
                        animationLoadAnimation2.setAnimationListener(new Animation.AnimationListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4.widget.D4HomeDeviceView.4.2
                            @Override // android.view.animation.Animation.AnimationListener
                            public void onAnimationRepeat(Animation animation) {
                            }

                            @Override // android.view.animation.Animation.AnimationListener
                            public void onAnimationStart(Animation animation) {
                            }

                            @Override // android.view.animation.Animation.AnimationListener
                            public void onAnimationEnd(Animation animation) {
                                D4HomeDeviceView d4HomeDeviceView3 = D4HomeDeviceView.this;
                                d4HomeDeviceView3.isAniStart = false;
                                d4HomeDeviceView3.bleMenuView.setVisibility(0);
                            }
                        });
                        D4HomeDeviceView.this.bleMenuView.startAnimation(animationLoadAnimation2);
                        D4HomeDeviceView.this.isAniStart = true;
                    }
                }
                if ((((((D4HomeDeviceView.this.llChartPanel.getHeight() + D4HomeDeviceView.this.llChartPanel.getTop()) + D4HomeDeviceView.this.llHistoryRecordPanel.getTop()) + D4HomeDeviceView.this.scrollView.getTop()) - D4HomeDeviceView.this.d4ViewLayout.getScrollY()) + ArmsUtils.dip2px(D4HomeDeviceView.this.getContext(), 50.0f)) - (D4HomeDeviceView.this.bleMenuView.getTop() - D4HomeDeviceView.this.contentView.getTop()) > 0 || DataHelper.getBooleanSF(D4HomeDeviceView.this.mContext, Consts.D4_CHART_IS_FIRST) || D4HomeDeviceView.this.isShowGuide) {
                    return;
                }
                D4HomeDeviceView.this.d4ViewLayout.fling(0);
                D4HomeDeviceView.this.d4ViewLayout.setOnTouchListener(new View.OnTouchListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4.widget.D4HomeDeviceView.4.3
                    @Override // android.view.View.OnTouchListener
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        return true;
                    }
                });
                D4HomeDeviceView.this.isShowGuide = true;
                D4HomeDeviceView d4HomeDeviceView3 = D4HomeDeviceView.this;
                d4HomeDeviceView3.showGuideView3(d4HomeDeviceView3.llChartPanel);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initViewSizeAndPosition() {
        this.hsvChart.post(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.d4.widget.D4HomeDeviceView.5
            @Override // java.lang.Runnable
            public void run() {
                D4HomeDeviceView.this.hsvChart.scrollTo(D4HomeDeviceView.this.barChartView.getWidth(), 0);
            }
        });
        this.rlTopView.setOnTouchListener(new View.OnTouchListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4.widget.D4HomeDeviceView.6
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                D4HomeDeviceView.this.mVelocityTracker.addMovement(motionEvent);
                int action = motionEvent.getAction();
                if (action == 0) {
                    D4HomeDeviceView.this.rlRightTopWindow.setVisibility(8);
                    D4HomeDeviceView.this.tempY = motionEvent.getY();
                    PetkitLog.d("resOffset", "ACTION_DOWN:tempY:" + D4HomeDeviceView.this.tempY);
                } else if (action == 1) {
                    PetkitLog.d("resOffset", "ACTION_UP");
                    D4HomeDeviceView.this.mVelocityTracker.computeCurrentVelocity(1000);
                    PetkitLog.d("resOffset", "xVelocity:" + D4HomeDeviceView.this.mVelocityTracker.getXVelocity() + " yVelocity:" + D4HomeDeviceView.this.mVelocityTracker.getYVelocity());
                } else if (action == 2) {
                    float y = motionEvent.getY();
                    float unused = D4HomeDeviceView.this.tempY;
                    D4HomeDeviceView.this.tempY = y;
                }
                return true;
            }
        });
        startProductAni();
    }

    @SuppressLint({"StringFormatInvalid"})
    public void setD4Record(D4Record d4Record) {
        Resources resources;
        int i;
        Resources resources2;
        int i2;
        this.d4Record = d4Record;
        this.tvHistoryMore.setVisibility(d4Record.getDeviceShared() == null ? 0 : 4);
        this.llAccessoryConsumables.setVisibility(TextUtils.isEmpty(BleDeviceUtils.getPurchaseEntranceConsumaUrl(11, new int[0])) ? 8 : 0);
        if (d4Record.getSettings() != null) {
            if (d4Record.getSettings().getColorSetting() == 1 || d4Record.getSettings().getColorSetting() == 0) {
                this.rlViewD4DeviceCenter.setBackground(getResources().getDrawable(R.drawable.d4_1_home_icon));
            } else if (d4Record.getSettings().getColorSetting() == 2) {
                this.rlViewD4DeviceCenter.setBackground(getResources().getDrawable(R.drawable.d4_home_icon));
            } else if (d4Record.getSettings().getColorSetting() == 3) {
                this.rlViewD4DeviceCenter.setBackground(getResources().getDrawable(R.drawable.d4_home_icon_black));
            } else if (d4Record.getSettings().getColorSetting() == 4) {
                this.rlViewD4DeviceCenter.setBackground(getResources().getDrawable(R.drawable.d4_home_icon_orange));
            }
        }
        if (this.d4HomeRecordAdapter == null) {
            D4HomeRecordAdapter d4HomeRecordAdapter = new D4HomeRecordAdapter(this.mContext, d4Record.getDeviceId(), this);
            this.d4HomeRecordAdapter = d4HomeRecordAdapter;
            this.rvD4RecordView.setAdapter(d4HomeRecordAdapter);
        }
        setupCenterArcView();
        this.ivWarnText.setVisibility(8);
        if (d4Record.getState().getPim() == 0) {
            this.feederInlet.setVisibility(0);
            if (d4Record.getState().getDesiccantLeftDays() > 0) {
                this.feederInlet.setBackground(getResources().getDrawable(R.drawable.shape_feeder_desiccant_orange_bg));
                TextView textView = this.feederInlet;
                StringBuilder sb = new StringBuilder();
                sb.append(getResources().getString(R.string.Desiccant_left));
                sb.append(":");
                sb.append(String.valueOf(d4Record.getState().getDesiccantLeftDays() >= 0 ? d4Record.getState().getDesiccantLeftDays() : 0));
                sb.append(getResources().getString(d4Record.getState().getDesiccantLeftDays() > 1 ? R.string.Unit_days : R.string.Unit_day));
                textView.setText(sb.toString());
                this.feederInlet.setTextColor(getResources().getColor(R.color.light_black));
            } else {
                this.feederInlet.setBackground(getResources().getDrawable(R.drawable.shape_feeder_desiccant_red_bg));
                TextView textView2 = this.feederInlet;
                StringBuilder sb2 = new StringBuilder();
                sb2.append(getResources().getString(R.string.Desiccant_left));
                sb2.append(":");
                sb2.append(String.valueOf(d4Record.getState().getDesiccantLeftDays() >= 0 ? d4Record.getState().getDesiccantLeftDays() : 0));
                sb2.append(getResources().getString(d4Record.getState().getDesiccantLeftDays() > 1 ? R.string.Unit_days : R.string.Unit_day));
                textView2.setText(sb2.toString());
                this.feederInlet.setTextColor(getResources().getColor(R.color.red));
            }
            this.isUnMask = false;
            this.bleMenuView.changeAllMask(true);
            this.tvD4Left.setText(String.valueOf(d4Record.getState().getFeedState().getAddAmountTotal() / d4Record.getSettings().getFactor()));
            this.tvD4BottomLeft.setText("--");
            this.tvD4BottomLeft.setTextColor(this.mContext.getResources().getColor(R.color.walk_text));
            this.tvD4Left.setText(D4Utils.getAmountFormat(d4Record.getState().getFeedState().getAddAmountTotal(), d4Record.getSettings().getFactor()));
            this.tvD4Left.setTextColor(this.mContext.getResources().getColor(R.color.feeder_plan_save_black));
            this.tvD4Plan.setTextColor(this.mContext.getResources().getColor(R.color.feeder_plan_save_black));
            this.tvD4BottomPlan.setText("--");
            this.tvD4BottomPlan.setTextColor(this.mContext.getResources().getColor(R.color.walk_text));
            this.llWarnText.setVisibility(0);
            this.tvWarnText.setText(this.mContext.getResources().getString(R.string.Device_off_line_tip) + " >");
        } else {
            this.feederInlet.setVisibility(0);
            if (d4Record.getState().getDesiccantLeftDays() > 0) {
                this.feederInlet.setBackground(getResources().getDrawable(R.drawable.shape_feeder_desiccant_orange_bg));
                TextView textView3 = this.feederInlet;
                StringBuilder sb3 = new StringBuilder();
                sb3.append(getResources().getString(R.string.Desiccant_left));
                sb3.append(":");
                sb3.append(String.valueOf(d4Record.getState().getDesiccantLeftDays() >= 0 ? d4Record.getState().getDesiccantLeftDays() : 0));
                sb3.append(getResources().getString(d4Record.getState().getDesiccantLeftDays() > 1 ? R.string.Unit_days : R.string.Unit_day));
                textView3.setText(sb3.toString());
                this.feederInlet.setTextColor(getResources().getColor(R.color.light_black));
            } else {
                this.feederInlet.setBackground(getResources().getDrawable(R.drawable.shape_feeder_desiccant_red_bg));
                TextView textView4 = this.feederInlet;
                StringBuilder sb4 = new StringBuilder();
                sb4.append(getResources().getString(R.string.Desiccant_left));
                sb4.append(":");
                sb4.append(String.valueOf(d4Record.getState().getDesiccantLeftDays() >= 0 ? d4Record.getState().getDesiccantLeftDays() : 0));
                sb4.append(getResources().getString(d4Record.getState().getDesiccantLeftDays() > 1 ? R.string.Unit_days : R.string.Unit_day));
                textView4.setText(sb4.toString());
                this.feederInlet.setTextColor(getResources().getColor(R.color.red));
            }
            this.llWarnText.setVisibility(8);
            this.isUnMask = true;
            this.bleMenuView.changeAllMask(false);
            this.tvD4Left.setText(D4Utils.getAmountFormat(d4Record.getState().getFeedState().getAddAmountTotal(), d4Record.getSettings().getFactor()));
            String amountFormat = D4Utils.getAmountFormat(d4Record.getState().getFeedState().getAddAmountTotal(), d4Record.getSettings().getFactor());
            if (d4Record.getState().getFeedState().getAddAmountTotal() > 100) {
                resources = getResources();
                i = R.string.Feeders_unit;
            } else {
                resources = getResources();
                i = R.string.Feeder_unit;
            }
            String string = resources.getString(i);
            SpannableString spannableString = new SpannableString(amountFormat + string);
            spannableString.setSpan(new RelativeSizeSpan(0.35f), spannableString.toString().indexOf(string), spannableString.toString().indexOf(string) + string.length(), 33);
            this.tvD4BottomLeft.setText(spannableString);
            this.tvD4Left.setTextColor(this.mContext.getResources().getColor(R.color.d4_light_green));
            this.tvD4BottomLeft.setTextColor(this.mContext.getResources().getColor(R.color.common_text));
            this.tvD4Plan.setTextColor(this.mContext.getResources().getColor(R.color.d4_main_yellow));
            this.tvD4BottomPlan.setTextColor(this.mContext.getResources().getColor(R.color.common_text));
            if (!TextUtils.isEmpty(d4Record.getState().getErrorCode()) && d4Record.getState().getErrorLevel() == 1) {
                this.llWarnText.setVisibility(0);
                this.tvWarnText.setText(d4Record.getState().getErrorMsg() + " >");
                this.isUnMask = false;
                this.bleMenuView.changeAllMask(true);
            } else {
                if (d4Record.getState().getOta() == 1) {
                    if (d4Record.getDeviceShared() != null) {
                        this.tvWarnText.setText(this.activity.getResources().getString(R.string.Device_ota_prompt));
                        this.tvWarnText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.warn_red_little_icon, 0, 0, 0);
                    } else {
                        this.tvWarnText.setText(this.activity.getResources().getString(R.string.Device_ota_prompt));
                        this.tvWarnText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.warn_red_little_icon, 0, R.drawable.arrow_right2, 0);
                    }
                    this.llWarnText.setVisibility(0);
                } else if (!TextUtils.isEmpty(d4Record.getState().getErrorCode())) {
                    this.llWarnText.setVisibility(0);
                    this.tvWarnText.setText(d4Record.getState().getErrorMsg());
                    this.tvWarnText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.warn_red_little_icon, 0, R.drawable.arrow_right2, 0);
                } else if (d4Record.getState().getFood() == 0 || d4Record.getState().getFood() == -1) {
                    this.llWarnText.setVisibility(0);
                    this.tvWarnText.setText(this.activity.getResources().getString(R.string.No_food_prompt));
                    this.tvWarnText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_right2, 0);
                    this.ivWarnText.setVisibility(0);
                    this.ivWarnText.setImageDrawable(getResources().getDrawable(R.drawable.d4s_warn_anim));
                    if (this.ivWarnText.getDrawable() instanceof AnimationDrawable) {
                        ((AnimationDrawable) this.ivWarnText.getDrawable()).start();
                    }
                } else if (d4Record.getState().getFood() == 2) {
                    this.llWarnText.setVisibility(0);
                    this.tvWarnText.setText(this.activity.getResources().getString(R.string.Surplus_grain_early_warning_prompt));
                    this.tvWarnText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_right2, 0);
                    this.ivWarnText.setVisibility(0);
                    this.ivWarnText.setImageDrawable(getResources().getDrawable(R.drawable.d4s_warn_anim));
                    if (this.ivWarnText.getDrawable() instanceof AnimationDrawable) {
                        ((AnimationDrawable) this.ivWarnText.getDrawable()).start();
                    }
                } else if (d4Record.getState().getBatteryStatus() == 2) {
                    this.llWarnText.setVisibility(0);
                    this.tvWarnText.setText(this.activity.getResources().getString(R.string.D3_low_power_prompt));
                    this.tvWarnText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.warn_red_little_icon, 0, 0, 0);
                }
                if (d4Record.getState().getOta() == 1) {
                    this.isUnMask = false;
                    this.bleMenuView.changeAllMask(true);
                }
            }
        }
        if (d4Record.getState().getFeedState() != null) {
            this.tvD4Plan.setText(D4Utils.getAmountFormat(d4Record.getState().getFeedState().getPlanAmountTotal(), d4Record.getSettings().getFactor()));
            String amountFormat2 = D4Utils.getAmountFormat(d4Record.getState().getFeedState().getPlanAmountTotal(), d4Record.getSettings().getFactor());
            if (d4Record.getState().getFeedState().getPlanAmountTotal() > 100) {
                resources2 = getResources();
                i2 = R.string.Feeders_unit;
            } else {
                resources2 = getResources();
                i2 = R.string.Feeder_unit;
            }
            String string2 = resources2.getString(i2);
            SpannableString spannableString2 = new SpannableString(amountFormat2 + string2);
            spannableString2.setSpan(new RelativeSizeSpan(0.35f), spannableString2.toString().indexOf(string2), spannableString2.toString().indexOf(string2) + string2.length(), 33);
            this.tvD4BottomPlan.setText(spannableString2);
            this.tvD4PlanFeed.setText(D4Utils.getAmountFormat(d4Record.getState().getFeedState().getPlanAmountTotal(), d4Record.getSettings().getFactor()) + string2);
        } else {
            this.tvD4PlanFeed.setText(0 + this.mContext.getResources().getString(R.string.Feeder_unit));
        }
        if (d4Record.getState().getPim() == 0 && (d4Record.getState().getFeedState() == null || d4Record.getState().getFeedState().getFeedTimes() == null || (d4Record.getState().getFeedState().getRealAmountTotal() == 0 && d4Record.getState().getFeedState().getTimes() == 0))) {
            String d4AmountTimesUnit = D4Utils.getD4AmountTimesUnit(this.mContext, 0);
            String decimalAmountUnit = D4Utils.getDecimalAmountUnit(0);
            SpannableString spannableString3 = new SpannableString("?" + d4AmountTimesUnit + "/?" + decimalAmountUnit);
            spannableString3.setSpan(new RelativeSizeSpan(0.7f), spannableString3.toString().indexOf(d4AmountTimesUnit), spannableString3.toString().indexOf(d4AmountTimesUnit) + d4AmountTimesUnit.length(), 33);
            spannableString3.setSpan(new RelativeSizeSpan(0.7f), spannableString3.toString().indexOf(decimalAmountUnit), spannableString3.toString().indexOf(decimalAmountUnit) + decimalAmountUnit.length(), 33);
            this.tvTodayFoodContent.setText(spannableString3);
        } else if (d4Record.getState().getFeedState() != null) {
            String d4AmountTimesUnit2 = D4Utils.getD4AmountTimesUnit(this.mContext, d4Record.getState().getFeedState().getTimes());
            String decimalAmountUnit2 = D4Utils.getDecimalAmountUnit(d4Record.getState().getFeedState().getRealAmountTotal());
            SpannableString spannableString4 = new SpannableString(d4Record.getState().getFeedState().getTimes() + d4AmountTimesUnit2 + "/" + D4Utils.getDecimalAmountFormat(d4Record.getState().getFeedState().getRealAmountTotal()));
            spannableString4.setSpan(new RelativeSizeSpan(0.7f), spannableString4.toString().indexOf(d4AmountTimesUnit2), spannableString4.toString().indexOf(d4AmountTimesUnit2) + d4AmountTimesUnit2.length(), 33);
            spannableString4.setSpan(new RelativeSizeSpan(0.7f), spannableString4.toString().indexOf(decimalAmountUnit2), spannableString4.toString().indexOf(decimalAmountUnit2) + decimalAmountUnit2.length(), 33);
            this.tvTodayFoodContent.setText(spannableString4);
            this.tvD4RealFeed.setText(D4Utils.getAmountFormat(d4Record.getState().getFeedState().getRealAmountTotal(), d4Record.getSettings().getFactor()) + decimalAmountUnit2);
        } else {
            String d4AmountTimesUnit3 = D4Utils.getD4AmountTimesUnit(this.mContext, 0);
            String decimalAmountUnit3 = D4Utils.getDecimalAmountUnit(0);
            SpannableString spannableString5 = new SpannableString(0 + d4AmountTimesUnit3 + "/0" + this.mContext.getResources().getString(R.string.Feeder_unit));
            spannableString5.setSpan(new RelativeSizeSpan(0.7f), spannableString5.toString().indexOf(d4AmountTimesUnit3), spannableString5.toString().indexOf(d4AmountTimesUnit3) + d4AmountTimesUnit3.length(), 33);
            spannableString5.setSpan(new RelativeSizeSpan(0.7f), spannableString5.toString().indexOf(decimalAmountUnit3), spannableString5.toString().indexOf(decimalAmountUnit3) + decimalAmountUnit3.length(), 33);
            this.tvTodayFoodContent.setText(spannableString5);
            this.tvD4RealFeed.setText(0 + this.mContext.getResources().getString(R.string.Feeder_unit));
        }
        this.d4TimeView.setTimeDataList(processFeedStateData());
        int i3 = this.recordType;
        if (i3 == 1) {
            this.tvStatisticsMonth.setSelected(true);
            this.tvStatisticsDate.setSelected(false);
            this.tvStatisticsWeek.setSelected(false);
            this.tvStatisticsMonth.setTextColor(-1);
            this.tvStatisticsWeek.setTextColor(getResources().getColor(R.color.feeder_plan_save_black));
            this.tvStatisticsDate.setTextColor(getResources().getColor(R.color.feeder_plan_save_black));
        } else if (i3 == 2) {
            this.tvStatisticsWeek.setSelected(true);
            this.tvStatisticsDate.setSelected(false);
            this.tvStatisticsMonth.setSelected(false);
            this.tvStatisticsWeek.setTextColor(-1);
            this.tvStatisticsMonth.setTextColor(getResources().getColor(R.color.feeder_plan_save_black));
            this.tvStatisticsDate.setTextColor(getResources().getColor(R.color.feeder_plan_save_black));
        } else if (i3 == 3) {
            this.tvStatisticsDate.setSelected(true);
            this.tvStatisticsWeek.setSelected(false);
            this.tvStatisticsMonth.setSelected(false);
            this.tvStatisticsDate.setTextColor(-1);
            this.tvStatisticsWeek.setTextColor(getResources().getColor(R.color.feeder_plan_save_black));
            this.tvStatisticsMonth.setTextColor(getResources().getColor(R.color.feeder_plan_save_black));
        }
        checkDeviceWarnState();
        if (d4Record.getState().getPim() != 0 && d4Record.getState().getFeeding() == 1) {
            this.bleMenuView.setFirstButtonText(getResources().getString(R.string.Stop_feed));
            this.bleMenuView.changeMenuBtnState(0);
            startFeedAni();
        } else {
            stopFeedAni();
            this.bleMenuView.setFirstButtonText(getResources().getString(R.string.Extra_feeding));
            this.bleMenuView.changeAllMenuBtnState(false);
        }
    }

    private List<BleFeederTimeView.BleFeederTimeViewData> processFeedStateData() {
        int i;
        ArrayList arrayList = new ArrayList();
        if (this.d4Record.getState().getFeedState() != null) {
            Map<String, Integer> feedTimes = this.d4Record.getState().getFeedState().getFeedTimes();
            arrayList.clear();
            if (feedTimes != null) {
                Object[] array = feedTimes.keySet().toArray();
                for (int i2 = 0; i2 < array.length; i2++) {
                    int iIntValue = feedTimes.get(array[i2]).intValue();
                    if (iIntValue == 1) {
                        i = R.drawable.d4_time_success_icon;
                    } else if (iIntValue == 3) {
                        i = R.drawable.d4_time_plan_icon;
                    } else {
                        i = R.drawable.d4_time_fail_icon;
                    }
                    try {
                        arrayList.add(new BleFeederTimeView.BleFeederTimeViewData(Integer.parseInt(String.valueOf(array[i2])) / 60, Integer.parseInt(String.valueOf(array[i2])) / 60, BitmapFactory.decodeResource(getResources(), i), BitmapFactory.decodeResource(getResources(), i), 2));
                    } catch (Exception e) {
                        e.printStackTrace();
                        PetkitLog.d(e.getMessage());
                    }
                }
            }
        }
        return arrayList;
    }

    private void checkDeviceWarnState() {
        int i = !CommonUtils.isSameTimeZoneAsLocal(this.d4Record.getLocale(), this.d4Record.getTimezone()) ? 1 : 0;
        if (this.d4Record.getState().getWifi().getRsq() < -70) {
            i++;
        }
        if (i == 0) {
            this.tvLittleWarnText.setVisibility(8);
        } else if (i == 1) {
            this.tvLittleWarnText.setVisibility(0);
            if (!CommonUtils.isSameTimeZoneAsLocal(this.d4Record.getLocale(), this.d4Record.getTimezone())) {
                this.tvLittleWarnText.setText(R.string.Time_zone_is_different);
            } else if (this.d4Record.getState().getWifi().getRsq() < -70) {
                this.tvLittleWarnText.setText(R.string.Wifi_signal_weak);
            }
        } else {
            this.tvLittleWarnText.setVisibility(0);
            this.tvLittleWarnText.setText(String.format(getResources().getString(R.string.Have_mistake_to_handle), String.valueOf(i)));
        }
        if (this.d4Record.getState().getPim() == 2) {
            this.tvTitleStatus.setVisibility(0);
            this.tvTitleStatus.setText(getResources().getString(R.string.D2_battery_mode));
            int batteryPower = this.d4Record.getState().getBatteryPower();
            if (batteryPower == 0) {
                this.tvTitleStatus.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, getResources().getDrawable(R.drawable.d4s_battery_0_icon), (Drawable) null);
            } else if (batteryPower != 1) {
                if (batteryPower == 2) {
                    this.tvTitleStatus.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, getResources().getDrawable(R.drawable.d4s_battery_25_icon), (Drawable) null);
                    return;
                }
                if (batteryPower == 3) {
                    this.tvTitleStatus.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, getResources().getDrawable(R.drawable.d4s_battery_75_icon), (Drawable) null);
                    return;
                } else if (batteryPower == 4) {
                    this.tvTitleStatus.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, getResources().getDrawable(R.drawable.d4s_battery_100_icon), (Drawable) null);
                    return;
                } else {
                    this.tvTitleStatus.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, getResources().getDrawable(R.drawable.d4s_battery_100_icon), (Drawable) null);
                    return;
                }
            }
            this.tvTitleStatus.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, getResources().getDrawable(R.drawable.d4s_battery_25_icon), (Drawable) null);
            return;
        }
        this.tvTitleStatus.setVisibility(8);
    }

    private void setupCenterArcView() {
        Resources resources;
        int i;
        Resources resources2;
        int i2;
        Resources resources3;
        int i3;
        Resources resources4;
        int i4;
        if (this.d4Record.getState().getFeedState() != null) {
            if (this.d4Record.getState().getFeedState().getPlanAmountTotal() == 0) {
                if (this.d4Record.getState().getFeedState().getRealAmountTotal() == 0) {
                    this.pbBucket1.setPercent(0.0f);
                } else {
                    this.pbBucket1.setPercent(100.0f);
                }
            } else {
                int i5 = CommonUtil.getInt(D4Utils.getAmountFormat(this.d4Record.getState().getFeedState().getPlanAmountTotal(), this.d4Record.getSettings().getFactor()));
                if (i5 != 0) {
                    this.pbBucket1.setPercent((CommonUtil.getInt(D4Utils.getAmountFormat(this.d4Record.getState().getFeedState().getRealAmountTotal(), this.d4Record.getSettings().getFactor())) * 100.0f) / i5);
                } else {
                    this.pbBucket1.setPercent(0.0f);
                }
            }
            this.tvPlanAmount.setText(getResources().getString(R.string.Plan_n_meals, D4Utils.getAmountFormat(this.d4Record.getState().getFeedState().getPlanAmountTotal(), this.d4Record.getSettings().getFactor())));
        }
        if (this.d4Record.getState().getPim() != 0 && this.d4Record.getState().getFeeding() == 1) {
            this.bleMenuView.setFirstButtonText(getResources().getString(R.string.Stop_feed));
            this.bleMenuView.changeMenuBtnState(0);
            this.tvSmallArcCenterText.setTextColor(getResources().getColor(R.color.common_text));
            this.tvSmallArcCenterText.setText(this.mContext.getResources().getString(R.string.Feeding));
            this.tvSmallArcCenterText.setTextSize(20.0f);
            startFeedAni();
            this.tvBucket1.setVisibility(4);
        } else {
            stopFeedAni();
            this.tvBucket1.setVisibility(0);
            this.bleMenuView.setFirstButtonText(getResources().getString(R.string.Extra_feeding));
            this.bleMenuView.changeAllMenuBtnState(false);
            this.tvSmallArcCenterText.setTextColor(getResources().getColor(R.color.common_text));
            this.tvSmallArcCenterText.setTextSize(20.0f);
            boolean zEqualsIgnoreCase = "zh_CN".equalsIgnoreCase(UserInforUtils.getLanguageLocaleString(CommonUtils.getAppContext()));
            String amountFormat = D4Utils.getAmountFormat(this.d4Record.getState().getFeedState().getRealAmountTotal(), this.d4Record.getSettings().getFactor());
            StringBuilder sb = new StringBuilder();
            if (this.d4Record.getState().getFeedState().getRealAmountTotal() / 100 > 1) {
                resources = getResources();
                i = R.string.Feeders_unit;
            } else {
                resources = getResources();
                i = R.string.Feeder_unit;
            }
            String string = resources.getString(i);
            sb.append(amountFormat);
            sb.append(zEqualsIgnoreCase ? "" : " ");
            sb.append(string);
            SpannableString spannableString = new SpannableString(sb.toString());
            int length = amountFormat.length();
            int length2 = sb.length();
            spannableString.setSpan(new AbsoluteSizeSpan(42, true), 0, length, 33);
            spannableString.setSpan(new AbsoluteSizeSpan(12, true), length, length2, 33);
            this.tvSmallArcCenterText.setText(spannableString);
        }
        String str = DeviceUtils.isZhCN(this.activity) ? "" : " ";
        TextView textView = this.tvHasOut1;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(getResources().getString(R.string.Feeder_amount_feeded));
        sb2.append(str);
        sb2.append(D4Utils.getAmountFormat(this.d4Record.getState().getFeedState().getRealAmountTotal(), this.d4Record.getSettings().getFactor()));
        sb2.append(str);
        if (this.d4Record.getState().getFeedState().getRealAmountTotal() / 100 > 1) {
            resources2 = getResources();
            i2 = R.string.Feeders_unit;
        } else {
            resources2 = getResources();
            i2 = R.string.Feeder_unit;
        }
        sb2.append(resources2.getString(i2));
        textView.setText(sb2.toString());
        TextView textView2 = this.tvPlanHasFinishedNum1;
        StringBuilder sb3 = new StringBuilder();
        sb3.append(D4Utils.getAmountFormat(this.d4Record.getState().getFeedState().getPlanRealAmountTotal(), this.d4Record.getSettings().getFactor()));
        if (this.d4Record.getState().getFeedState().getPlanRealAmountTotal() / 100 > 1) {
            resources3 = getResources();
            i3 = R.string.Feeders_unit;
        } else {
            resources3 = getResources();
            i3 = R.string.Feeder_unit;
        }
        sb3.append(resources3.getString(i3));
        textView2.setText(sb3.toString());
        TextView textView3 = this.tvManualFeedNum1;
        StringBuilder sb4 = new StringBuilder();
        sb4.append(D4Utils.getAmountFormat(this.d4Record.getState().getFeedState().getRealAmountTotal() - this.d4Record.getState().getFeedState().getPlanRealAmountTotal(), this.d4Record.getSettings().getFactor()));
        if ((this.d4Record.getState().getFeedState().getRealAmountTotal() - this.d4Record.getState().getFeedState().getPlanRealAmountTotal()) / 100 > 1) {
            resources4 = getResources();
            i4 = R.string.Feeders_unit;
        } else {
            resources4 = getResources();
            i4 = R.string.Feeder_unit;
        }
        sb4.append(resources4.getString(i4));
        textView3.setText(sb4.toString());
        if (this.d4Record.getState().getFeedState().getRealAmountTotal() - this.d4Record.getState().getFeedState().getPlanRealAmountTotal() == 0) {
            this.llManualFeedNum1.setVisibility(8);
        } else {
            this.llManualFeedNum1.setVisibility(0);
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.widget.PetkitSlidingUpPanelLayout.PanelSlideListener
    public void onPanelSlide(View view, float f) {
        this.mSlideOffset = f;
        if (f >= 0.0f) {
            float f2 = (1.0f - f) - 0.2f;
            this.alpha = f2;
            if (f2 < 0.0f) {
                this.alpha = 0.0f;
            }
            this.rlTopView.setAlpha(this.alpha);
        }
    }

    public void startUpArrowAnimation() {
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this.ivUpArrow, Key.TRANSLATION_Y, 0.0f, -16.0f);
        objectAnimatorOfFloat.setRepeatMode(1);
        objectAnimatorOfFloat.setRepeatCount(-1);
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(this.ivUpArrow, Key.ALPHA, 0.0f, 1.0f, 0.0f);
        objectAnimatorOfFloat2.setRepeatMode(1);
        objectAnimatorOfFloat2.setRepeatCount(5);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(3500L);
        animatorSet.playTogether(objectAnimatorOfFloat2, objectAnimatorOfFloat);
        animatorSet.start();
    }

    public void showGuideView3(View view) {
        if (this.guide3 != null) {
            return;
        }
        GuideBuilder guideBuilder = new GuideBuilder();
        guideBuilder.setTargetView(view).setAlpha(180).setHighTargetCorner(50).setHighTargetPaddingLeft(ArmsUtils.dip2px(this.mContext, 0.0f)).setHighTargetPaddingRight(ArmsUtils.dip2px(this.mContext, 0.0f)).setHighTargetPaddingBottom(ArmsUtils.dip2px(this.mContext, 0.0f)).setHighTargetPaddingTop(ArmsUtils.dip2px(this.mContext, 0.0f)).setOutsideTouchable(false).setAutoDismiss(false);
        guideBuilder.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4.widget.D4HomeDeviceView.7
            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onShown() {
            }

            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onDismiss() {
                DataHelper.setBooleanSF(D4HomeDeviceView.this.mContext, Consts.D4_CHART_IS_FIRST, Boolean.TRUE);
                D4HomeDeviceView.this.d4ViewLayout.fling(1);
                D4HomeDeviceView.this.d4ViewLayout.setOnTouchListener(new View.OnTouchListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4.widget.D4HomeDeviceView.7.1
                    @Override // android.view.View.OnTouchListener
                    public boolean onTouch(View view2, MotionEvent motionEvent) {
                        return false;
                    }
                });
            }
        });
        guideBuilder.addComponent(new GuidePetTipAndBtnAndLineComponent(new GuideParam(getResources().getString(R.string.Home_chart_guide_tips), "", 4, 32, 20, 10, getResources().getString(R.string.Done), R.layout.layout_feeder_guide_one), new ConfirmListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4.widget.D4HomeDeviceView.8
            @Override // com.petkit.android.activities.petkitBleDevice.w5.guide.ConfirmListener
            public void onConfirmListener() {
                if (D4HomeDeviceView.this.guide3 != null) {
                    D4HomeDeviceView.this.guide3.dismiss();
                }
            }
        }));
        this.guide3 = guideBuilder.createGuide();
        this.contentView.post(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.d4.widget.D4HomeDeviceView.9
            @Override // java.lang.Runnable
            public void run() {
                D4HomeDeviceView.this.guide3.show((Activity) D4HomeDeviceView.this.getContext());
            }
        });
    }

    public void showGuideView2(View view) {
        if (this.guide2 != null) {
            return;
        }
        GuideBuilder guideBuilder = new GuideBuilder();
        guideBuilder.setTargetView(view).setAlpha(180).setHighTargetCorner(50).setHighTargetPaddingLeft(ArmsUtils.dip2px(this.mContext, 0.0f)).setHighTargetPaddingRight(ArmsUtils.dip2px(this.mContext, 0.0f)).setHighTargetPaddingBottom(ArmsUtils.dip2px(this.mContext, 0.0f)).setHighTargetPaddingTop(ArmsUtils.dip2px(this.mContext, 0.0f)).setOutsideTouchable(false).setAutoDismiss(false);
        guideBuilder.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4.widget.D4HomeDeviceView.10
            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onShown() {
            }

            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onDismiss() {
                DataHelper.setBooleanSF(D4HomeDeviceView.this.mContext, Consts.D4_HOME_DATA_GUIDE, Boolean.TRUE);
            }
        });
        guideBuilder.addComponent(new GuideTipAndBtnAndLineComponent(new GuideParam(getResources().getString(R.string.Home_menu_guide_tips), "2/2", 2, 48, 0, -10, getResources().getString(R.string.Done), R.layout.layout_home_guide_mine), new ConfirmListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4.widget.D4HomeDeviceView.11
            @Override // com.petkit.android.activities.petkitBleDevice.w5.guide.ConfirmListener
            public void onConfirmListener() {
                if (D4HomeDeviceView.this.guide2 != null) {
                    D4HomeDeviceView.this.guide2.dismiss();
                }
            }
        }));
        this.guide2 = guideBuilder.createGuide();
        this.contentView.post(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.d4.widget.D4HomeDeviceView.12
            @Override // java.lang.Runnable
            public void run() {
                D4HomeDeviceView.this.guide2.show((Activity) D4HomeDeviceView.this.getContext());
            }
        });
    }

    public void showGuideView() {
        if (this.guide1 != null) {
            return;
        }
        GuideBuilder guideBuilder = new GuideBuilder();
        guideBuilder.setTargetView(this.rlDailyData).setAlpha(180).setHighTargetCorner(50).setHighTargetPaddingLeft(ArmsUtils.dip2px(this.mContext, 8.0f)).setHighTargetPaddingRight(ArmsUtils.dip2px(this.mContext, 8.0f)).setHighTargetPaddingBottom(ArmsUtils.dip2px(this.mContext, 8.0f)).setHighTargetPaddingTop(ArmsUtils.dip2px(this.mContext, 8.0f)).setOutsideTouchable(false).setAutoDismiss(false);
        guideBuilder.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4.widget.D4HomeDeviceView.13
            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onShown() {
            }

            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onDismiss() {
                if (D4HomeDeviceView.this.bleMenuView.getVisibility() != 8) {
                    D4HomeDeviceView d4HomeDeviceView = D4HomeDeviceView.this;
                    d4HomeDeviceView.showGuideView2(d4HomeDeviceView.bleMenuView);
                }
            }
        });
        guideBuilder.addComponent(new GuidePetTipAndBtnAndLineComponent(new GuideParam(getResources().getString(R.string.D3_top_guide_tips), "1/2", 4, 32, -50, 10, getResources().getString(R.string.Next_tip), R.layout.layout_feeder_guide_one), new ConfirmListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4.widget.D4HomeDeviceView.14
            @Override // com.petkit.android.activities.petkitBleDevice.w5.guide.ConfirmListener
            public void onConfirmListener() {
                if (D4HomeDeviceView.this.guide1 != null) {
                    D4HomeDeviceView.this.guide1.dismiss();
                }
            }
        }));
        this.guide1 = guideBuilder.createGuide();
        this.contentView.post(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.d4.widget.D4HomeDeviceView.15
            @Override // java.lang.Runnable
            public void run() {
                D4HomeDeviceView.this.guide1.show((Activity) D4HomeDeviceView.this.getContext());
            }
        });
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:74:0x02e6  */
    @Override // android.view.View.OnClickListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onClick(android.view.View r15) {
        /*
            Method dump skipped, instruction units count: 1130
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.petkit.android.activities.petkitBleDevice.d4.widget.D4HomeDeviceView.onClick(android.view.View):void");
    }

    @Override // com.petkit.android.activities.petkitBleDevice.widget.BleDeviceHomeMenuView.OnMenuClickListener
    public void onMenuClick(BleMenuItem bleMenuItem, int i) {
        MenuOnClickListener menuOnClickListener = this.menuOnClickListener;
        if (menuOnClickListener != null) {
            if (i != 0) {
                if (i != 1) {
                    return;
                }
                menuOnClickListener.onBottomMenuClick(MenuType.FEED_PLAN);
            } else if (this.d4Record.getState().getFeeding() == 1) {
                this.menuOnClickListener.onBottomMenuClick(MenuType.STOP_FEED);
            } else {
                this.menuOnClickListener.onBottomMenuClick(MenuType.FEED_NOW);
            }
        }
    }

    private SpannableString getAllFeedStr(int i, int i2) {
        String d4AmountTimesUnit = D4Utils.getD4AmountTimesUnit(this.mContext, i2);
        String decimalAmountUnit = D4Utils.getDecimalAmountUnit(i);
        SpannableString spannableString = new SpannableString(i2 + d4AmountTimesUnit + "/" + D4Utils.getDecimalAmountFormat(i));
        spannableString.setSpan(new RelativeSizeSpan(0.7f), spannableString.toString().indexOf(d4AmountTimesUnit), spannableString.toString().indexOf(d4AmountTimesUnit) + d4AmountTimesUnit.length(), 33);
        spannableString.setSpan(new RelativeSizeSpan(0.7f), spannableString.toString().indexOf(decimalAmountUnit), spannableString.toString().indexOf(decimalAmountUnit) + decimalAmountUnit.length(), 33);
        return spannableString;
    }

    public void refreshDateData(String str) {
        this.tvHistoryStatisticDate.setText(D4Utils.getDateByStatisticTime(str, this.activity));
        refreshBarChart();
        refreshDateData(this.d4DailyFeeds);
    }

    public void refreshHistoryRecord(int i) {
        this.tvHistoryRecord.setText(String.format("%s%s", D4Utils.getAmountFormat(i, this.d4Record.getSettings().getFactor()), D4Utils.getD4AmountUnit(this.mContext, i, this.d4Record.getSettings().getFactor())));
    }

    public void refreshBarChart() {
        this.d4Histogram.updateBarChartData(0);
    }

    public void refreshDateData(D4DailyFeeds d4DailyFeeds) {
        this.d4DailyFeeds = d4DailyFeeds;
        if (d4DailyFeeds != null) {
            this.d4HomeRecordAdapter = new D4HomeRecordAdapter(this.mContext, this.d4Record.getDeviceId(), this);
            this.d4HomeRecordAdapter.setD4DateFeedData(new D4DateFeedData(d4DailyFeeds.getFeed().get(0).getItems(), d4DailyFeeds.getFeed().get(0).getDay()));
            this.rvD4RecordView.setAdapter(this.d4HomeRecordAdapter);
            refreshHistoryRecord(d4DailyFeeds.getFeed().get(0).getRealAmount());
            this.rvD4RecordView.post(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.d4.widget.D4HomeDeviceView.17
                @Override // java.lang.Runnable
                public void run() {
                    if (D4HomeDeviceView.this.rvD4RecordView.getMeasuredHeight() >= ArmsUtils.dip2px(D4HomeDeviceView.this.mContext, 300.0f)) {
                        D4HomeDeviceView.this.rvD4RecordView.setPadding(0, 0, 0, ArmsUtils.dip2px(D4HomeDeviceView.this.mContext, 50.0f));
                    }
                }
            });
        }
    }

    public void refreshPlanView(DifferentFeedPlan differentFeedPlan) {
        Calendar.getInstance().get(7);
        if (differentFeedPlan == null || differentFeedPlan.getFeedDailyList() == null) {
            return;
        }
        for (int i = 0; i < 7; i++) {
            if (differentFeedPlan.getFeedDailyList().get(i).getSuspended() == 0) {
                this.bleMenuView.setIsFeederPlanIsOpen(true);
                this.bleMenuView.setDeviceType(11, 0);
                this.pbBucket1.setVisibility(0);
                this.tvPlanAmount.setVisibility(0);
                this.tvPlanPrompt.setVisibility(8);
                return;
            }
        }
        this.bleMenuView.setIsFeederPlanIsOpen(false);
        this.bleMenuView.setDeviceType(11, 0);
        this.pbBucket1.setVisibility(4);
        this.tvPlanAmount.setVisibility(8);
        this.tvPlanPrompt.setVisibility(0);
    }

    public void startFeedAni() {
        Observable.just("").delay(500L, TimeUnit.MILLISECONDS).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<String>() { // from class: com.petkit.android.activities.petkitBleDevice.d4.widget.D4HomeDeviceView.18
            @Override // io.reactivex.functions.Consumer
            public void accept(String str) throws Exception {
                D4HomeDeviceView.this.pbBucket1.setOpenAnimation(true);
            }
        });
    }

    public void stopFeedAni() {
        this.pbBucket1.setOpenAnimation(false);
    }

    public MenuOnClickListener getMenuOnClickListener() {
        return this.menuOnClickListener;
    }

    public void setMenuOnClickListener(MenuOnClickListener menuOnClickListener) {
        this.menuOnClickListener = menuOnClickListener;
    }

    @Override // com.petkit.android.activities.petkitBleDevice.widget.BleDeviceHomeOfflineWarnWindow.OfflineClickListener
    public void onClickSeeDetails() {
        Activity activity = this.activity;
        activity.startActivity(BleDeviceWifiSettingActivity.newIntent(activity, this.d4Record.getDeviceId(), 11, this.d4Record.getTypeCode()));
    }

    @Override // com.petkit.android.activities.petkitBleDevice.widget.BleDeviceHomeOfflineWarnWindow.OfflineClickListener
    public void onClickReset() {
        Activity activity = this.activity;
        activity.startActivity(BleDeviceBindNetWorkActivity.newIntent((Context) activity, this.d4Record.getDeviceId(), 11, this.d4Record.getBtMac(), false, this.d4Record.getTypeCode()));
    }

    public ChartOnClickListener getChartOnClickListener() {
        return this.chartOnClickListener;
    }

    public void setChartOnClickListener(ChartOnClickListener chartOnClickListener) {
        this.chartOnClickListener = chartOnClickListener;
    }

    @Override // com.petkit.android.activities.petkitBleDevice.d4.adapter.D4HomeRecordAdapter.OnClickListener
    public void onDeleteFeedRecordClick(int i, int i2) {
        ((D4HomeActivity) this.activity).openDeleteRecordWindow(i, i2);
    }

    public static class MainHandler extends Handler {
        public final WeakReference<Context> mContext;

        public MainHandler(Context context) {
            super(Looper.getMainLooper());
            this.mContext = new WeakReference<>(context);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        MainHandler mainHandler = this.mainHandler;
        if (mainHandler != null) {
            mainHandler.removeCallbacksAndMessages(null);
        }
        stopFeedAni();
        Disposable disposable = this.disposable;
        if (disposable != null && !disposable.isDisposed()) {
            this.disposable.dispose();
        }
        super.onDetachedFromWindow();
    }

    public void setOnHistogramPageChange(OnHistogramPageChange onHistogramPageChange) {
        this.onHistogramPageChange = onHistogramPageChange;
    }

    public void startLeftArrowAnimation() {
        this.ivLeftArrow.setVisibility(0);
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this.ivLeftArrow, Key.TRANSLATION_X, 0.0f, -16.0f);
        objectAnimatorOfFloat.setRepeatMode(1);
        objectAnimatorOfFloat.setRepeatCount(-1);
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(this.ivLeftArrow, Key.ALPHA, 0.0f, 1.0f, 0.0f);
        objectAnimatorOfFloat2.setRepeatMode(1);
        objectAnimatorOfFloat2.setRepeatCount(-1);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(3500L);
        animatorSet.playTogether(objectAnimatorOfFloat2, objectAnimatorOfFloat);
        animatorSet.start();
        this.animatorLeftSetList.add(animatorSet);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopLeftAni() {
        for (int i = 0; i < this.animatorLeftSetList.size(); i++) {
            this.animatorLeftSetList.get(i).cancel();
        }
        this.animatorLeftSetList.clear();
        this.ivLeftArrow.setVisibility(8);
    }

    public void setPromoteView(RelatedProductsInfor relatedProductsInfor) {
        ArrayList arrayList = new ArrayList();
        if (relatedProductsInfor != null && relatedProductsInfor.getPromote() != null) {
            arrayList.addAll(relatedProductsInfor.getPromote());
        }
        if (arrayList.size() == 0) {
            this.promoteView.setVisibility(8);
            return;
        }
        this.promoteView.setVisibility(0);
        this.promoteView.enableSwitchable("D4H");
        this.promoteView.refreshPromoteData(arrayList);
        this.promoteView.setPromoteViewOnItemListener(new PromoteView.PromoteViewOnItemListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4.widget.D4HomeDeviceView.19
            @Override // com.petkit.android.activities.mall.widget.PromoteView.PromoteViewOnItemListener
            public void onItemClick(PromoteData promoteData) {
                EventBus.getDefault().post(promoteData);
            }
        });
    }

    public void showFoodWarnDialog() {
        int i = R.drawable.d4_buckets_lack_of_food_icon;
        SpannableString spannableString = new SpannableString(this.activity.getResources().getString(R.string.No_food_prompt));
        String string = this.activity.getResources().getString(R.string.Surplus_grain_early_warning_tips);
        if (this.d4Record.getSettings().getFoodWarn() != 1 || (this.d4Record.getState() != null && this.d4Record.getState().getPim() == 2)) {
            string = "";
        }
        NormalCenterTipWindow normalCenterTipWindow = new NormalCenterTipWindow(this.activity, new NormalCenterTipWindow.OnClickOk() { // from class: com.petkit.android.activities.petkitBleDevice.d4.widget.D4HomeDeviceView.20
            @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
            public void onOkClick() {
                D4HomeDeviceView.this.d4sFoodWarnWindow.dismiss();
            }

            @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
            public void onCancelClick() {
                D4HomeDeviceView.this.menuOnClickListener.onBottomMenuClick(MenuType.NO_MORE_REMIND);
            }
        }, getResources().getString(R.string.Surplus_grain_early_warning), spannableString);
        this.d4sFoodWarnWindow = normalCenterTipWindow;
        normalCenterTipWindow.setTvTipGray(true, string);
        this.d4sFoodWarnWindow.setSelectText(getResources().getString(R.string.I_have_added_meal), getResources().getString(R.string.I_know));
        this.d4sFoodWarnWindow.setImage(i);
        this.d4sFoodWarnWindow.showAtLocation(this.activity.getWindow().getDecorView(), 17, 0, 0);
    }

    public void showFoodEmptyDialog() {
        int i = R.drawable.d4_buckets_lack_of_food_icon;
        SpannableString spannableString = new SpannableString(this.activity.getResources().getString(R.string.No_food_prompt));
        String string = this.activity.getResources().getString(R.string.Surplus_grain_early_warning_tips);
        if (this.d4Record.getSettings().getFoodWarn() != 1 || (this.d4Record.getState() != null && this.d4Record.getState().getPim() == 2)) {
            string = "";
        }
        NormalCenterTipWindow normalCenterTipWindow = new NormalCenterTipWindow(this.activity, new NormalCenterTipWindow.OnClickOk() { // from class: com.petkit.android.activities.petkitBleDevice.d4.widget.D4HomeDeviceView.21
            @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
            public void onOkClick() {
                D4HomeDeviceView.this.d4sFoodWarnWindow.dismiss();
            }

            @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
            public void onCancelClick() {
                D4HomeDeviceView.this.menuOnClickListener.onBottomMenuClick(MenuType.NO_MORE_REMIND);
            }
        }, getResources().getString(R.string.Surplus_grain_early_warning), spannableString);
        this.d4sFoodWarnWindow = normalCenterTipWindow;
        normalCenterTipWindow.setTvTipGray(true, string);
        this.d4sFoodWarnWindow.setSelectText(getResources().getString(R.string.I_have_added_meal), getResources().getString(R.string.I_know));
        this.d4sFoodWarnWindow.setImage(i);
        this.d4sFoodWarnWindow.showAtLocation(this.activity.getWindow().getDecorView(), 17, 0, 0);
    }

    public void setCurrentItem(int i) {
        this.d4Histogram.setCurrentItem(i);
    }

    private void startExpandAni(final int i, int i2) {
        if (this.isRunning) {
            return;
        }
        ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(i2, i);
        this.expandValueAnimator = valueAnimatorOfInt;
        valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4.widget.D4HomeDeviceView.22
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int iIntValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                D4HomeDeviceView d4HomeDeviceView = D4HomeDeviceView.this;
                if (d4HomeDeviceView.lastValue >= iIntValue || d4HomeDeviceView.llAccessoryConsumables == null) {
                    return;
                }
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) D4HomeDeviceView.this.llAccessoryConsumables.getLayoutParams();
                layoutParams.width = iIntValue;
                D4HomeDeviceView.this.llAccessoryConsumables.setLayoutParams(layoutParams);
                D4HomeDeviceView.this.lastValue = iIntValue;
            }
        });
        this.expandValueAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.petkit.android.activities.petkitBleDevice.d4.widget.D4HomeDeviceView.23
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                D4HomeDeviceView d4HomeDeviceView = D4HomeDeviceView.this;
                d4HomeDeviceView.isRunning = false;
                d4HomeDeviceView.accessoryConsumables = 0;
                D4HomeDeviceView d4HomeDeviceView2 = D4HomeDeviceView.this;
                int i3 = i;
                d4HomeDeviceView2.startReduceAni(i3, i3 - d4HomeDeviceView2.tvWidth);
            }
        });
        this.expandValueAnimator.setInterpolator(new LinearInterpolator());
        this.expandValueAnimator.setDuration(1000L);
        this.expandValueAnimator.setStartDelay(0L);
        this.expandValueAnimator.start();
        this.isRunning = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startReduceAni(int i, int i2) {
        if (this.isRunning) {
            return;
        }
        ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(i, i2);
        this.reduceValueAnimator = valueAnimatorOfInt;
        valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4.widget.D4HomeDeviceView.24
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int iIntValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                D4HomeDeviceView d4HomeDeviceView = D4HomeDeviceView.this;
                if (d4HomeDeviceView.lastValue <= iIntValue || d4HomeDeviceView.llAccessoryConsumables == null) {
                    return;
                }
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) D4HomeDeviceView.this.llAccessoryConsumables.getLayoutParams();
                layoutParams.width = iIntValue;
                D4HomeDeviceView.this.llAccessoryConsumables.setLayoutParams(layoutParams);
                D4HomeDeviceView.this.lastValue = iIntValue;
            }
        });
        this.reduceValueAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.petkit.android.activities.petkitBleDevice.d4.widget.D4HomeDeviceView.25
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                D4HomeDeviceView.this.accessoryConsumables = 1;
                D4HomeDeviceView.this.isRunning = false;
            }
        });
        this.reduceValueAnimator.setInterpolator(new LinearInterpolator());
        this.reduceValueAnimator.setDuration(1000L);
        this.reduceValueAnimator.setStartDelay(10000L);
        this.reduceValueAnimator.start();
        this.isRunning = true;
    }

    public boolean isUnMask() {
        return this.isUnMask;
    }

    private void startProductAni() {
        this.llAccessoryConsumables.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4.widget.D4HomeDeviceView.26
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                D4HomeDeviceView d4HomeDeviceView = D4HomeDeviceView.this;
                d4HomeDeviceView.measuredWidth = d4HomeDeviceView.llAccessoryConsumables.getMeasuredWidth();
                D4HomeDeviceView d4HomeDeviceView2 = D4HomeDeviceView.this;
                d4HomeDeviceView2.lastValue = d4HomeDeviceView2.measuredWidth;
                D4HomeDeviceView d4HomeDeviceView3 = D4HomeDeviceView.this;
                d4HomeDeviceView3.tvWidth = d4HomeDeviceView3.tvAccessoryConsumables.getMeasuredWidth();
                D4HomeDeviceView d4HomeDeviceView4 = D4HomeDeviceView.this;
                d4HomeDeviceView4.startReduceAni(d4HomeDeviceView4.measuredWidth, D4HomeDeviceView.this.measuredWidth - D4HomeDeviceView.this.tvWidth);
                D4HomeDeviceView.this.llAccessoryConsumables.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }
}

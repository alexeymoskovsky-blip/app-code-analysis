package com.petkit.android.activities.petkitBleDevice.d3.widget;

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
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
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
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
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
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.feeder.model.DifferentFeedPlan;
import com.petkit.android.activities.home.utils.GuidePetTipAndBtnAndLineComponent;
import com.petkit.android.activities.home.utils.GuideTipAndBtnAndLineComponent;
import com.petkit.android.activities.mall.mode.PromoteData;
import com.petkit.android.activities.mall.widget.PromoteView;
import com.petkit.android.activities.petkitBleDevice.BleDeviceBindNetWorkActivity;
import com.petkit.android.activities.petkitBleDevice.BleDeviceWifiSettingActivity;
import com.petkit.android.activities.petkitBleDevice.d3.adapter.D3HomeRecordAdapter;
import com.petkit.android.activities.petkitBleDevice.d3.guide.GuideD3TipAndBtnAndLineComponent;
import com.petkit.android.activities.petkitBleDevice.d3.mode.D3DailyFeeds;
import com.petkit.android.activities.petkitBleDevice.d3.mode.D3DateFeedData;
import com.petkit.android.activities.petkitBleDevice.d3.mode.D3Record;
import com.petkit.android.activities.petkitBleDevice.d3.mode.D3Statistic;
import com.petkit.android.activities.petkitBleDevice.d3.utils.D3Utils;
import com.petkit.android.activities.petkitBleDevice.d3.widget.BarChartView;
import com.petkit.android.activities.petkitBleDevice.d3.widget.BleFeederTimeView;
import com.petkit.android.activities.petkitBleDevice.d3.widget.D3Histogram;
import com.petkit.android.activities.petkitBleDevice.d4s.widget.D4sHomeProgressView;
import com.petkit.android.activities.petkitBleDevice.mode.BleMenuItem;
import com.petkit.android.activities.petkitBleDevice.t4.mode.RelatedProductsInfor;
import com.petkit.android.activities.petkitBleDevice.utils.BleDeviceUtils;
import com.petkit.android.activities.petkitBleDevice.w5.guide.ConfirmListener;
import com.petkit.android.activities.petkitBleDevice.w5.guide.GuideParam;
import com.petkit.android.activities.petkitBleDevice.widget.BleDeviceHomeMenuView;
import com.petkit.android.activities.petkitBleDevice.widget.BleDeviceHomeOfflineWarnWindow;
import com.petkit.android.activities.petkitBleDevice.widget.PetkitSlidingUpPanelLayout;
import com.petkit.android.activities.petkitBleDevice.widget.ScrollViewWithListener2;
import com.petkit.android.activities.petkitBleDevice.widget.frame.FrameSurfaceView;
import com.petkit.android.model.ItemsBean;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.DateUtil;
import com.petkit.android.utils.PetkitLog;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.android.widget.InterceptRecyclerView;
import com.petkit.oversea.R;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.simple.eventbus.EventBus;

/* JADX INFO: loaded from: classes4.dex */
public class D3HomeDeviceView extends LinearLayout implements PetkitSlidingUpPanelLayout.PanelSlideListener, View.OnClickListener, BleDeviceHomeMenuView.OnMenuClickListener, D3HomeRecordAdapter.OnClickListener, BleDeviceHomeOfflineWarnWindow.OfflineClickListener, BarChartView.ChartOnClickListener, D3HomeRecordAdapter.DeleteRecordViewClick, D3HomeRecordAdapter.GuidePageListener, D3Histogram.OnPageChanged {
    private int accessoryConsumables;
    private Activity activity;
    private float alpha;
    private List<AnimatorSet> animatorLeftSetList;
    private BarChartView barChartView;
    private List<Integer> bitmaps;
    private BleDeviceHomeMenuView bleMenuView;
    private BleFeederTimeView bleTimeView;
    private boolean bottomPanelAllVisible;
    private Guide centerProgressViewGuide;
    private GuideBuilder centerProgressViewGuideBuilder;
    private ChartOnClickListener chartOnClickListener;
    private int clickInnerIndex;
    private int clickOuterIndex;
    View contentView;
    private ImageView d3ArcBg;
    private LinearLayout d3BottomInfosLayout;
    private D3DailyFeeds d3DailyFeeds;
    private D3Histogram d3Histogram;
    private D3HomeRecordAdapter d3HomeRecordAdapter;
    private D3Record d3Record;
    private ImageView d3ViewStateIconBattery;
    private ImageView d3ViewStateIconFood;
    private ImageView d3ViewStateIconTimezone;
    private ImageView d3ViewStateIconWifi;
    private SimpleDateFormat dateFormat;
    private int day;
    ValueAnimator expandValueAnimator;
    private TextView feederInlet;
    private FrameSurfaceView fsvVoiceAni;
    private Guide guide1;
    private Guide guide2;
    private Guide guide3;
    private Integer guidePageIndex;
    private View homeViewCard;
    private HorizontalScrollView hsvChart;
    boolean isAniStart;
    private boolean isNeedShow;
    boolean isRunning;
    private boolean isShowGuide;
    private boolean isUnMask;
    boolean isUp;
    private Map<Integer, Integer> itemHeightMap;
    private ImageView ivAccessoryConsumables;
    private ImageView ivHelpIcon;
    private ImageView ivLeftArrow;
    private ImageView ivMenuHelpIcon;
    private ImageView ivUpArrow;
    int lastValue;
    private LinearLayout llAccessoryConsumables;
    private LinearLayout llChartPanel;
    private int llChartPanelHeight;
    private LinearLayout llD3Bottom;
    private LinearLayout llD3ViewBtnPanel;
    private LinearLayout llHistoryRecordPanel;
    private LinearLayout llManualFeedNum1;
    private Context mContext;
    private float mSlideOffset;
    private VelocityTracker mVelocityTracker;
    private MainHandler mainHandler;
    private int measuredWidth;
    private MenuOnClickListener menuOnClickListener;
    private OnCenterProgressViewMeasureComplete onCenterProgressViewMeasureComplete;
    private OnHistogramPageChange onHistogramPageChange;
    private int panelHeight;
    private D4sHomeProgressView pbBucket1;
    private PopupWindow popupWindow;
    private PromoteView promoteView;
    private int recordType;
    ValueAnimator reduceValueAnimator;
    private Guide removeRecordGuide;
    private GuideBuilder removeRecordGuideBuilder;
    private RelativeLayout rlD3RecordView;
    private RelativeLayout rlDailyData;
    private RelativeLayout rlData1;
    private RelativeLayout rlLeftBottomWindow;
    private RelativeLayout rlRightTopWindow;
    private RelativeLayout rlTimePanel;
    private RelativeLayout rlTopView;
    private RelativeLayout rlViewD3DeviceCenter;
    private RelativeLayout rlbtnAndWarnPanel;
    private InterceptRecyclerView rvRecordView;
    private int scrollHeight;
    private ScrollViewWithListener2 scrollLayout;
    private boolean showRemoveRecordGuide;
    private float tempY;
    private TextView tvAccessoryConsumables;
    private TextView tvBucket1;
    private TextView tvChartEmpty;
    private TextView tvD3Left;
    private TextView tvD3LeftDes;
    private TextView tvD3MaxWeight;
    private TextView tvD3Plan;
    private TextView tvD3PlanEat;
    private TextView tvD3RealEat;
    private TextView tvD3RemainingWeight;
    private TextView tvEatPrompt;
    private TextView tvHasEat;
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
    private TextView tvState;
    private TextView tvStatistics;
    private TextView tvStatisticsDate;
    private TextView tvStatisticsMonth;
    private TextView tvStatisticsWeek;
    private TextView tvTitleStatus;
    private TextView tvTodayFood;
    private TextView tvWarnText;
    private int tvWidth;
    private RelativeLayout viewCard;
    private int viewCardHeight;
    private boolean voiceStart;

    public interface ChartOnClickListener {
        void onChartClick(int i, String str);
    }

    public interface MenuOnClickListener {
        void onBottomMenuClick(MenuType menuType);
    }

    public enum MenuType {
        FEED_NOW,
        STOP_FEED,
        CALL_PET,
        SPEECH_SETTING,
        FEED_PLAN,
        TYPE_MONTH,
        TYPE_WEEK,
        TYPE_DATE,
        SHOW_TIPS,
        SURPLUS_GRAIN_CONTROL,
        SHOW_SURPLUS_GRAIN_CONTROL_TIP,
        FEEDER_INLET,
        WARN_LIST,
        DATE_PICKER
    }

    public interface OnCenterProgressViewMeasureComplete {
        void onComplete(int i, View view);
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

    @Override // com.petkit.android.activities.petkitBleDevice.d3.adapter.D3HomeRecordAdapter.OnClickListener
    public void onViewClick(ItemsBean itemsBean, int i) {
    }

    public void setFeedingAmount(int i) {
    }

    @RequiresApi(api = 23)
    public D3HomeDeviceView(Context context) {
        super(context);
        this.isShowGuide = false;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtil.DATE_FORMAT_7);
        this.dateFormat = simpleDateFormat;
        this.recordType = 3;
        this.day = Integer.parseInt(simpleDateFormat.format(Calendar.getInstance().getTime()));
        this.voiceStart = false;
        this.isAniStart = false;
        this.panelHeight = 0;
        this.isNeedShow = false;
        this.llChartPanelHeight = 0;
        this.viewCardHeight = 0;
        this.scrollHeight = 0;
        this.showRemoveRecordGuide = false;
        this.lastValue = 0;
        this.mContext = context;
        this.activity = (Activity) context;
        initView();
    }

    @RequiresApi(api = 23)
    public D3HomeDeviceView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.isShowGuide = false;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtil.DATE_FORMAT_7);
        this.dateFormat = simpleDateFormat;
        this.recordType = 3;
        this.day = Integer.parseInt(simpleDateFormat.format(Calendar.getInstance().getTime()));
        this.voiceStart = false;
        this.isAniStart = false;
        this.panelHeight = 0;
        this.isNeedShow = false;
        this.llChartPanelHeight = 0;
        this.viewCardHeight = 0;
        this.scrollHeight = 0;
        this.showRemoveRecordGuide = false;
        this.lastValue = 0;
        this.mContext = context;
        this.activity = (Activity) context;
        initView();
    }

    @RequiresApi(api = 23)
    public D3HomeDeviceView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.isShowGuide = false;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtil.DATE_FORMAT_7);
        this.dateFormat = simpleDateFormat;
        this.recordType = 3;
        this.day = Integer.parseInt(simpleDateFormat.format(Calendar.getInstance().getTime()));
        this.voiceStart = false;
        this.isAniStart = false;
        this.panelHeight = 0;
        this.isNeedShow = false;
        this.llChartPanelHeight = 0;
        this.viewCardHeight = 0;
        this.scrollHeight = 0;
        this.showRemoveRecordGuide = false;
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
        View viewInflate = LayoutInflater.from(this.mContext).inflate(R.layout.layout_d3_home_device_view, (ViewGroup) null);
        this.contentView = viewInflate;
        addView(viewInflate, -1, -1);
        this.ivAccessoryConsumables = (ImageView) this.contentView.findViewById(R.id.iv_accessory_consumables);
        this.tvAccessoryConsumables = (TextView) this.contentView.findViewById(R.id.tv_accessory_consumables);
        this.llAccessoryConsumables = (LinearLayout) this.contentView.findViewById(R.id.ll_accessory_consumables);
        this.tvEatPrompt = (TextView) this.contentView.findViewById(R.id.tv_eat_prompt);
        this.llD3Bottom = (LinearLayout) this.contentView.findViewById(R.id.ll_d3_bottom);
        this.rlD3RecordView = (RelativeLayout) this.contentView.findViewById(R.id.rl_d3_recordView);
        this.llChartPanel = (LinearLayout) this.contentView.findViewById(R.id.ll_chart_panel);
        this.llD3ViewBtnPanel = (LinearLayout) this.contentView.findViewById(R.id.ll_d3_view_btn_panel);
        this.tvState = (TextView) this.contentView.findViewById(R.id.tv_state);
        TextView textView = (TextView) this.contentView.findViewById(R.id.tv_d3_left_des_bottom);
        this.tvD3LeftDes = textView;
        textView.setText(this.mContext.getResources().getString(R.string.D3_remaining_in_the_current_bowl));
        this.ivMenuHelpIcon = (ImageView) this.contentView.findViewById(R.id.iv_menu_help_icon);
        this.ivHelpIcon = (ImageView) this.contentView.findViewById(R.id.iv_help_icon);
        this.rlViewD3DeviceCenter = (RelativeLayout) this.contentView.findViewById(R.id.rl_view_d3_device_center);
        this.tvChartEmpty = (TextView) this.contentView.findViewById(R.id.tv_chart_empty);
        this.hsvChart = (HorizontalScrollView) this.contentView.findViewById(R.id.hsv_chart);
        BarChartView barChartView = (BarChartView) this.contentView.findViewById(R.id.barCharView);
        this.barChartView = barChartView;
        barChartView.setChartOnClickListener(this);
        this.rlbtnAndWarnPanel = (RelativeLayout) this.contentView.findViewById(R.id.rl_btn_and_warn_panel);
        this.tvWarnText = (TextView) this.contentView.findViewById(R.id.tv_warn_text);
        InterceptRecyclerView interceptRecyclerView = (InterceptRecyclerView) this.contentView.findViewById(R.id.rv_d3_recordView);
        this.rvRecordView = interceptRecyclerView;
        interceptRecyclerView.setLayoutManager(new LinearLayoutManager(this.mContext));
        this.llHistoryRecordPanel = (LinearLayout) this.contentView.findViewById(R.id.ll_history_record_panel);
        this.llManualFeedNum1 = (LinearLayout) this.contentView.findViewById(R.id.ll_manual_feed_num1);
        this.tvManualFeedNum1 = (TextView) this.contentView.findViewById(R.id.tv_manual_feed_num1);
        this.tvPlanHasFinishedNum1 = (TextView) this.contentView.findViewById(R.id.tv_plan_has_been_finished_num1);
        this.rlData1 = (RelativeLayout) this.contentView.findViewById(R.id.rl_data1);
        this.tvHasOut1 = (TextView) this.contentView.findViewById(R.id.tv_has_out1);
        this.tvHasEat = (TextView) this.contentView.findViewById(R.id.tv_has_eat);
        this.tvStatisticsMonth = (TextView) this.contentView.findViewById(R.id.tv_statistics_month);
        this.tvStatisticsDate = (TextView) this.contentView.findViewById(R.id.tv_statistics_date);
        this.tvStatisticsWeek = (TextView) this.contentView.findViewById(R.id.tv_statistics_week);
        this.tvStatistics = (TextView) this.contentView.findViewById(R.id.tv_statistics_title);
        this.bleTimeView = (BleFeederTimeView) this.contentView.findViewById(R.id.d3_time_view);
        this.tvTodayFood = (TextView) this.contentView.findViewById(R.id.tv_today_food_content);
        this.tvLittleWarnText = (TextView) this.contentView.findViewById(R.id.tv_little_warn_text);
        this.tvD3Left = (TextView) this.contentView.findViewById(R.id.tv_d3_left_bottom);
        this.tvD3Plan = (TextView) this.contentView.findViewById(R.id.tv_d3_plan_bottom);
        this.tvSmallArcCenterText = (TextView) this.contentView.findViewById(R.id.tv_small_arc_center_text);
        TextView textView2 = (TextView) this.contentView.findViewById(R.id.tv_bucket1);
        this.tvBucket1 = textView2;
        textView2.setVisibility(0);
        D4sHomeProgressView d4sHomeProgressView = (D4sHomeProgressView) this.contentView.findViewById(R.id.pb_bucket1);
        this.pbBucket1 = d4sHomeProgressView;
        d4sHomeProgressView.setProgressColor(getResources().getColor(R.color.d3_main_green), getResources().getColor(R.color.d4s_progress_gray));
        this.tvTitleStatus = (TextView) this.contentView.findViewById(R.id.tv_title_status);
        this.tvPlanAmount = (TextView) this.contentView.findViewById(R.id.tv_plan_amount1);
        this.fsvVoiceAni = (FrameSurfaceView) this.contentView.findViewById(R.id.fsv_voice_ani);
        this.tvPlanPrompt = (TextView) this.contentView.findViewById(R.id.tv_plan_prompt);
        this.rlTopView = (RelativeLayout) this.contentView.findViewById(R.id.rl_top_view);
        this.ivUpArrow = (ImageView) this.contentView.findViewById(R.id.iv_up_arrow);
        this.rlTimePanel = (RelativeLayout) this.contentView.findViewById(R.id.rl_time_panel);
        BleDeviceHomeMenuView bleDeviceHomeMenuView = (BleDeviceHomeMenuView) this.contentView.findViewById(R.id.ble_menu_view);
        this.bleMenuView = bleDeviceHomeMenuView;
        bleDeviceHomeMenuView.setDeviceType(9, 0);
        this.rlDailyData = (RelativeLayout) this.contentView.findViewById(R.id.rl_daily_data);
        this.rlRightTopWindow = (RelativeLayout) this.contentView.findViewById(R.id.rl_right_top_parent);
        this.rlLeftBottomWindow = (RelativeLayout) this.contentView.findViewById(R.id.rl_left_bottom_parent);
        this.tvD3PlanEat = (TextView) this.contentView.findViewById(R.id.tv_d3_plan_eat);
        this.tvD3RealEat = (TextView) this.contentView.findViewById(R.id.tv_d3_real_eat);
        this.tvD3MaxWeight = (TextView) this.contentView.findViewById(R.id.tv_d3_max_weight);
        this.tvD3RemainingWeight = (TextView) this.contentView.findViewById(R.id.tv_d3_remaining_weight);
        this.scrollLayout = (ScrollViewWithListener2) this.contentView.findViewById(R.id.d3_view_layout);
        this.viewCard = (RelativeLayout) this.contentView.findViewById(R.id.view_card);
        this.d3Histogram = (D3Histogram) this.contentView.findViewById(R.id.d3_histogram);
        this.tvHistoryMore = (TextView) this.contentView.findViewById(R.id.tv_history_more);
        TextView textView3 = (TextView) this.contentView.findViewById(R.id.tv_history_statistic_date);
        this.tvHistoryStatisticDate = textView3;
        textView3.setOnTouchListener(new View.OnTouchListener() { // from class: com.petkit.android.activities.petkitBleDevice.d3.widget.D3HomeDeviceView.1
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == 1 && D3HomeDeviceView.this.tvHistoryStatisticDate.getCompoundDrawables()[2] != null && motionEvent.getX() > D3HomeDeviceView.this.tvHistoryStatisticDate.getWidth() - D3HomeDeviceView.this.tvHistoryStatisticDate.getCompoundDrawables()[2].getBounds().width()) {
                    D3HomeDeviceView.this.menuOnClickListener.onBottomMenuClick(MenuType.DATE_PICKER);
                }
                return true;
            }
        });
        this.tvHistoryRecord = (TextView) this.contentView.findViewById(R.id.tv_history_record);
        this.ivLeftArrow = (ImageView) this.contentView.findViewById(R.id.iv_left_arrow);
        this.d3BottomInfosLayout = (LinearLayout) this.contentView.findViewById(R.id.ll_d4_view_btn_panel);
        this.homeViewCard = this.contentView.findViewById(R.id.home_view_card);
        this.promoteView = (PromoteView) this.contentView.findViewById(R.id.promoteView);
        this.feederInlet = (TextView) this.contentView.findViewById(R.id.feeder_inlet);
        this.ivMenuHelpIcon.setOnClickListener(this);
        this.ivHelpIcon.setOnClickListener(this);
        this.bleMenuView.setOnMenuClickListener(this);
        this.tvStatisticsMonth.setOnClickListener(this);
        this.tvStatisticsDate.setOnClickListener(this);
        this.tvStatisticsWeek.setOnClickListener(this);
        this.tvWarnText.setOnClickListener(this);
        this.tvHistoryMore.setOnClickListener(this);
        this.d3Histogram.setOnPageChanged(this);
        this.feederInlet.setOnClickListener(this);
        this.tvTitleStatus.setOnClickListener(this);
        this.tvSmallArcCenterText.setOnClickListener(this);
        this.tvLittleWarnText.setOnClickListener(this);
        this.llAccessoryConsumables.setOnClickListener(this);
        this.tvAccessoryConsumables.setOnClickListener(this);
        this.ivAccessoryConsumables.setOnClickListener(this);
        initFrameAnimation();
        this.mainHandler.postDelayed(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.d3.widget.D3HomeDeviceView.2
            @Override // java.lang.Runnable
            public void run() {
                D3HomeDeviceView.this.activity.getWindow().getDecorView().setSystemUiVisibility(0);
                D3HomeDeviceView.this.initViewSizeAndPosition();
            }
        }, 200L);
        this.scrollLayout.setScrollviewOnTouchListener(new ScrollViewWithListener2.ScrollviewOnTouchListener() { // from class: com.petkit.android.activities.petkitBleDevice.d3.widget.D3HomeDeviceView.3
            @Override // com.petkit.android.activities.petkitBleDevice.widget.ScrollViewWithListener2.ScrollviewOnTouchListener
            public void finishScroll(boolean z) {
            }

            @Override // com.petkit.android.activities.petkitBleDevice.widget.ScrollViewWithListener2.ScrollviewOnTouchListener
            public void onScrolling(int i, int i2, int i3, int i4) {
                if (i2 - i4 > 5) {
                    Log.d("scrolling", "up");
                    D3HomeDeviceView d3HomeDeviceView = D3HomeDeviceView.this;
                    d3HomeDeviceView.isUp = true;
                    if (!d3HomeDeviceView.isAniStart && d3HomeDeviceView.bleMenuView.getVisibility() == 0) {
                        Animation animationLoadAnimation = AnimationUtils.loadAnimation(D3HomeDeviceView.this.activity, R.anim.ble_menu_slide_out_to_bottom);
                        animationLoadAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.petkit.android.activities.petkitBleDevice.d3.widget.D3HomeDeviceView.3.1
                            @Override // android.view.animation.Animation.AnimationListener
                            public void onAnimationRepeat(Animation animation) {
                            }

                            @Override // android.view.animation.Animation.AnimationListener
                            public void onAnimationStart(Animation animation) {
                            }

                            @Override // android.view.animation.Animation.AnimationListener
                            public void onAnimationEnd(Animation animation) {
                                D3HomeDeviceView d3HomeDeviceView2 = D3HomeDeviceView.this;
                                d3HomeDeviceView2.isAniStart = false;
                                d3HomeDeviceView2.bleMenuView.setVisibility(8);
                            }
                        });
                        D3HomeDeviceView.this.bleMenuView.startAnimation(animationLoadAnimation);
                        D3HomeDeviceView.this.isAniStart = true;
                    }
                } else if (i4 - i2 > 5) {
                    D3HomeDeviceView d3HomeDeviceView2 = D3HomeDeviceView.this;
                    d3HomeDeviceView2.isUp = false;
                    if (!d3HomeDeviceView2.isAniStart && d3HomeDeviceView2.bleMenuView.getVisibility() == 8) {
                        Animation animationLoadAnimation2 = AnimationUtils.loadAnimation(D3HomeDeviceView.this.activity, R.anim.panel_slide_in_from_bottom);
                        animationLoadAnimation2.setAnimationListener(new Animation.AnimationListener() { // from class: com.petkit.android.activities.petkitBleDevice.d3.widget.D3HomeDeviceView.3.2
                            @Override // android.view.animation.Animation.AnimationListener
                            public void onAnimationRepeat(Animation animation) {
                            }

                            @Override // android.view.animation.Animation.AnimationListener
                            public void onAnimationStart(Animation animation) {
                            }

                            @Override // android.view.animation.Animation.AnimationListener
                            public void onAnimationEnd(Animation animation) {
                                D3HomeDeviceView d3HomeDeviceView3 = D3HomeDeviceView.this;
                                d3HomeDeviceView3.isAniStart = false;
                                d3HomeDeviceView3.bleMenuView.setVisibility(0);
                            }
                        });
                        D3HomeDeviceView.this.bleMenuView.startAnimation(animationLoadAnimation2);
                        D3HomeDeviceView.this.isAniStart = true;
                    }
                }
                if ((((((D3HomeDeviceView.this.llChartPanel.getHeight() + D3HomeDeviceView.this.llChartPanel.getTop()) + D3HomeDeviceView.this.llHistoryRecordPanel.getTop()) + D3HomeDeviceView.this.llD3Bottom.getTop()) - D3HomeDeviceView.this.scrollLayout.getScrollY()) + ArmsUtils.dip2px(D3HomeDeviceView.this.getContext(), 50.0f)) - (D3HomeDeviceView.this.bleMenuView.getTop() - D3HomeDeviceView.this.contentView.getTop()) > 0 || DataHelper.getBooleanSF(D3HomeDeviceView.this.mContext, Consts.D3_CHART_IS_FIRST) || D3HomeDeviceView.this.isShowGuide) {
                    return;
                }
                D3HomeDeviceView.this.scrollLayout.fling(0);
                D3HomeDeviceView.this.scrollLayout.setOnTouchListener(new View.OnTouchListener() { // from class: com.petkit.android.activities.petkitBleDevice.d3.widget.D3HomeDeviceView.3.3
                    @Override // android.view.View.OnTouchListener
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        return true;
                    }
                });
                D3HomeDeviceView.this.isShowGuide = true;
                D3HomeDeviceView d3HomeDeviceView3 = D3HomeDeviceView.this;
                d3HomeDeviceView3.showGuideView3(d3HomeDeviceView3.llChartPanel);
            }
        });
        if (!DataHelper.getBooleanSF(this.mContext, Consts.D3_HOME_DATA_GUIDE)) {
            showGuideView(this.rlDailyData);
        }
        startProductAni();
    }

    public BleDeviceHomeMenuView getBleMenuView() {
        return this.bleMenuView;
    }

    public void setBleMenuView(BleDeviceHomeMenuView bleDeviceHomeMenuView) {
        this.bleMenuView = bleDeviceHomeMenuView;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initViewSizeAndPosition() {
        this.hsvChart.post(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.d3.widget.D3HomeDeviceView.4
            @Override // java.lang.Runnable
            public void run() {
                D3HomeDeviceView.this.hsvChart.scrollTo(D3HomeDeviceView.this.barChartView.getWidth(), 0);
            }
        });
        this.rlTopView.setOnTouchListener(new View.OnTouchListener() { // from class: com.petkit.android.activities.petkitBleDevice.d3.widget.D3HomeDeviceView.5
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                D3HomeDeviceView.this.mVelocityTracker.addMovement(motionEvent);
                int action = motionEvent.getAction();
                if (action == 0) {
                    D3HomeDeviceView.this.tempY = motionEvent.getY();
                    D3HomeDeviceView.this.rlRightTopWindow.setVisibility(8);
                    D3HomeDeviceView.this.rlLeftBottomWindow.setVisibility(8);
                    D3HomeDeviceView.this.clickOuterIndex = 0;
                    D3HomeDeviceView.this.clickInnerIndex = 0;
                    PetkitLog.d("resOffset", "ACTION_DOWN:tempY:" + D3HomeDeviceView.this.tempY);
                } else if (action == 1) {
                    PetkitLog.d("resOffset", "ACTION_UP");
                    D3HomeDeviceView.this.mVelocityTracker.computeCurrentVelocity(1000);
                    PetkitLog.d("resOffset", "xVelocity:" + D3HomeDeviceView.this.mVelocityTracker.getXVelocity() + " yVelocity:" + D3HomeDeviceView.this.mVelocityTracker.getYVelocity());
                } else if (action == 2) {
                    float y = motionEvent.getY();
                    float unused = D3HomeDeviceView.this.tempY;
                    D3HomeDeviceView.this.tempY = y;
                }
                return true;
            }
        });
        this.tvD3Left.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.petkit.android.activities.petkitBleDevice.d3.widget.D3HomeDeviceView.6
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                int height = D3HomeDeviceView.this.tvD3Left.getHeight();
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) D3HomeDeviceView.this.ivHelpIcon.getLayoutParams();
                layoutParams.setMargins(ArmsUtils.dip2px(D3HomeDeviceView.this.mContext, 2.0f), height, layoutParams.rightMargin, layoutParams.bottomMargin);
                D3HomeDeviceView.this.ivHelpIcon.setLayoutParams(layoutParams);
                D3HomeDeviceView.this.tvD3Left.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    private void initCenterWindowParams(int i) {
        int i2 = BaseApplication.displayMetrics.widthPixels;
        int iDip2px = (ArmsUtils.dip2px(this.activity, 14.0f) + i) - ArmsUtils.dip2px(this.activity, 28.0f);
        int i3 = i2 / 2;
        int iDip2px2 = ArmsUtils.dip2px(this.activity, 61.0f) + i3;
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.rlRightTopWindow.getLayoutParams();
        layoutParams.setMargins(iDip2px2, iDip2px, layoutParams.rightMargin, layoutParams.bottomMargin);
        this.rlRightTopWindow.setLayoutParams(layoutParams);
        int iDip2px3 = i + ArmsUtils.dip2px(this.activity, 14.0f) + ArmsUtils.dip2px(this.activity, 25.0f);
        int iDip2px4 = (i3 - ArmsUtils.dip2px(this.activity, 55.0f)) - ArmsUtils.dip2px(this.activity, 120.0f);
        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) this.rlLeftBottomWindow.getLayoutParams();
        layoutParams2.setMargins(iDip2px4, iDip2px3, layoutParams2.rightMargin, layoutParams2.bottomMargin);
        this.rlLeftBottomWindow.setLayoutParams(layoutParams2);
    }

    @SuppressLint({"StringFormatInvalid"})
    public void setD3Record(D3Record d3Record) {
        Resources resources;
        int i;
        Resources resources2;
        int i2;
        Resources resources3;
        int i3;
        this.d3Record = d3Record;
        this.tvHistoryMore.setVisibility(d3Record.getDeviceShared() == null ? 0 : 4);
        if (d3Record.getState().getFeedState().getEatAmountTotal() > 0) {
            this.tvEatPrompt.setText(getResources().getString(R.string.D3_amount_of_eat_food_today));
        } else {
            this.tvEatPrompt.setText(getResources().getString(R.string.Have_not_eat));
        }
        if (d3Record.getSettings().getSurplusControl() == 1) {
            this.bleMenuView.setD3SurplusGrainControlOpen(true);
            this.bleMenuView.setDeviceType(9, 0);
        } else {
            this.bleMenuView.setD3SurplusGrainControlOpen(false);
            this.bleMenuView.setDeviceType(9, 0);
        }
        this.llAccessoryConsumables.setVisibility(TextUtils.isEmpty(BleDeviceUtils.getPurchaseEntranceConsumaUrl(9, new int[0])) ? 8 : 0);
        if (this.d3HomeRecordAdapter == null) {
            D3HomeRecordAdapter d3HomeRecordAdapter = new D3HomeRecordAdapter(this.mContext, d3Record.getDeviceId(), this, this, this, d3Record.getDeviceShared() != null);
            this.d3HomeRecordAdapter = d3HomeRecordAdapter;
            this.rvRecordView.setAdapter(d3HomeRecordAdapter);
        }
        if (d3Record.getState().getPim() == 0) {
            this.feederInlet.setVisibility(0);
            if (d3Record.getState().getDesiccantLeftDays() > 0) {
                this.feederInlet.setBackground(getResources().getDrawable(R.drawable.shape_feeder_desiccant_green_bg));
                TextView textView = this.feederInlet;
                StringBuilder sb = new StringBuilder();
                sb.append(getResources().getString(R.string.Desiccant_left));
                sb.append(":");
                sb.append(String.valueOf(d3Record.getState().getDesiccantLeftDays() >= 0 ? d3Record.getState().getDesiccantLeftDays() : 0));
                sb.append(getResources().getString(d3Record.getState().getDesiccantLeftDays() > 1 ? R.string.Unit_days : R.string.Unit_day));
                textView.setText(sb.toString());
                this.feederInlet.setTextColor(getResources().getColor(R.color.light_black));
            } else {
                this.feederInlet.setBackground(getResources().getDrawable(R.drawable.shape_feeder_desiccant_red_bg));
                TextView textView2 = this.feederInlet;
                StringBuilder sb2 = new StringBuilder();
                sb2.append(getResources().getString(R.string.Desiccant_left));
                sb2.append(":");
                sb2.append(String.valueOf(d3Record.getState().getDesiccantLeftDays() >= 0 ? d3Record.getState().getDesiccantLeftDays() : 0));
                sb2.append(getResources().getString(d3Record.getState().getDesiccantLeftDays() > 1 ? R.string.Unit_days : R.string.Unit_day));
                textView2.setText(sb2.toString());
                this.feederInlet.setTextColor(getResources().getColor(R.color.red));
            }
            this.isUnMask = false;
            this.bleMenuView.changeAllMask(true);
            this.tvD3Left.setText("--");
            this.tvD3Left.setTextColor(this.mContext.getResources().getColor(R.color.common_text));
            this.tvD3Plan.setText("--");
            this.tvD3Plan.setTextColor(this.mContext.getResources().getColor(R.color.common_text));
            this.tvWarnText.setVisibility(0);
            this.tvWarnText.setText(this.mContext.getResources().getString(R.string.Device_off_line_tip));
            this.tvWarnText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.warn_red_little_icon, 0, R.drawable.arrow_right2, 0);
            this.ivMenuHelpIcon.setVisibility(8);
        } else {
            if (d3Record.getState().getPim() == 2) {
                this.tvTitleStatus.setVisibility(0);
                this.tvTitleStatus.setText(getResources().getString(R.string.D2_battery_mode));
                int batteryPower = d3Record.getState().getBatteryPower();
                if (batteryPower == 0) {
                    this.tvTitleStatus.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, getResources().getDrawable(R.drawable.d4s_battery_0_icon), (Drawable) null);
                } else if (batteryPower == 1 || batteryPower == 2) {
                    this.tvTitleStatus.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, getResources().getDrawable(R.drawable.d4s_battery_25_icon), (Drawable) null);
                } else if (batteryPower == 3) {
                    this.tvTitleStatus.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, getResources().getDrawable(R.drawable.d4s_battery_75_icon), (Drawable) null);
                } else if (batteryPower == 4) {
                    this.tvTitleStatus.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, getResources().getDrawable(R.drawable.d4s_battery_100_icon), (Drawable) null);
                } else {
                    this.tvTitleStatus.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, getResources().getDrawable(R.drawable.d4s_battery_100_icon), (Drawable) null);
                }
                this.tvTitleStatus.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, getResources().getDrawable(R.drawable.d4s_battery_25_icon), (Drawable) null);
            } else {
                this.tvTitleStatus.setVisibility(8);
            }
            this.feederInlet.setVisibility(0);
            if (d3Record.getState().getDesiccantLeftDays() > 0) {
                this.feederInlet.setBackground(getResources().getDrawable(R.drawable.shape_feeder_desiccant_green_bg));
                TextView textView3 = this.feederInlet;
                StringBuilder sb3 = new StringBuilder();
                sb3.append(getResources().getString(R.string.Desiccant_left));
                sb3.append(":");
                sb3.append(String.valueOf(d3Record.getState().getDesiccantLeftDays() >= 0 ? d3Record.getState().getDesiccantLeftDays() : 0));
                sb3.append(getResources().getString(d3Record.getState().getDesiccantLeftDays() > 1 ? R.string.Unit_days : R.string.Unit_day));
                textView3.setText(sb3.toString());
                this.feederInlet.setTextColor(getResources().getColor(R.color.light_black));
            } else {
                this.feederInlet.setBackground(getResources().getDrawable(R.drawable.shape_feeder_desiccant_red_bg));
                TextView textView4 = this.feederInlet;
                StringBuilder sb4 = new StringBuilder();
                sb4.append(getResources().getString(R.string.Desiccant_left));
                sb4.append(":");
                sb4.append(String.valueOf(d3Record.getState().getDesiccantLeftDays() >= 0 ? d3Record.getState().getDesiccantLeftDays() : 0));
                sb4.append(getResources().getString(d3Record.getState().getDesiccantLeftDays() > 1 ? R.string.Unit_days : R.string.Unit_day));
                textView4.setText(sb4.toString());
                this.feederInlet.setTextColor(getResources().getColor(R.color.red));
            }
            this.ivMenuHelpIcon.setVisibility(8);
            this.tvWarnText.setVisibility(8);
            this.isUnMask = true;
            this.bleMenuView.changeAllMask(false);
            this.tvD3Left.setTextColor(this.mContext.getResources().getColor(R.color.common_text));
            this.tvD3Plan.setTextColor(this.mContext.getResources().getColor(R.color.common_text));
            if (!TextUtils.isEmpty(d3Record.getState().getErrorCode()) && d3Record.getState().getErrorLevel() == 1) {
                this.tvWarnText.setVisibility(0);
                this.tvWarnText.setText(d3Record.getState().getErrorMsg());
                this.tvWarnText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.warn_red_little_icon, 0, R.drawable.arrow_right2, 0);
                this.isUnMask = false;
                this.bleMenuView.changeAllMask(true);
            } else {
                if (d3Record.getState().getOta() == 1) {
                    if (d3Record.getDeviceShared() != null) {
                        this.tvWarnText.setText(this.activity.getResources().getString(R.string.Device_ota_prompt));
                        this.tvWarnText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.warn_red_little_icon, 0, 0, 0);
                    } else {
                        this.tvWarnText.setText(this.activity.getResources().getString(R.string.Device_ota_prompt));
                        this.tvWarnText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.warn_red_little_icon, 0, R.drawable.arrow_right2, 0);
                    }
                    this.tvWarnText.setVisibility(0);
                } else if (!TextUtils.isEmpty(d3Record.getState().getErrorCode())) {
                    this.tvWarnText.setVisibility(0);
                    this.tvWarnText.setText(d3Record.getState().getErrorMsg());
                    this.tvWarnText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.warn_red_little_icon, 0, R.drawable.arrow_right2, 0);
                } else if (d3Record.getState().getEating() == 1) {
                    this.tvWarnText.setVisibility(0);
                    this.tvWarnText.setText(this.activity.getResources().getString(R.string.D3_pet_eating_prompt));
                    this.tvWarnText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                } else if (d3Record.getState().getFood() == 0 || d3Record.getState().getFood() == 1) {
                    this.tvWarnText.setVisibility(0);
                    this.tvWarnText.setText(this.activity.getResources().getString(R.string.Surplus_grain_early_warning_prompt));
                    this.tvWarnText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.warn_red_little_icon, 0, 0, 0);
                } else if (d3Record.getState().getBatteryStatus() == 2) {
                    this.tvWarnText.setVisibility(0);
                    this.tvWarnText.setText(this.activity.getResources().getString(R.string.D3_low_power_prompt));
                    this.tvWarnText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.warn_red_little_icon, 0, 0, 0);
                }
                if (d3Record.getState().getOta() == 1) {
                    this.isUnMask = false;
                    this.bleMenuView.changeAllMask(true);
                }
            }
        }
        if (d3Record.getState().getFeedState() != null && d3Record.getState().getPim() != 0) {
            String string = getResources().getString(R.string.Unit_g);
            SpannableString spannableString = new SpannableString(d3Record.getState().getWeight() + string);
            spannableString.setSpan(new RelativeSizeSpan(0.5f), spannableString.toString().indexOf(string), spannableString.toString().indexOf(string) + string.length(), 33);
            this.tvD3Left.setText(spannableString);
            if (d3Record.getState().getFeedState().getEatTimes() != null) {
                if (d3Record.getState().getFeedState().getEatTimes().size() > 1) {
                    resources3 = getResources();
                    i3 = R.string.Unit_times;
                } else {
                    resources3 = getResources();
                    i3 = R.string.Unit_time;
                }
                String string2 = resources3.getString(i3);
                SpannableString spannableString2 = new SpannableString(d3Record.getState().getFeedState().getEatTimes().size() + string2);
                spannableString2.setSpan(new RelativeSizeSpan(0.5f), spannableString2.toString().indexOf(string2), spannableString2.toString().indexOf(string2) + string2.length(), 33);
                this.tvD3Plan.setText(spannableString2);
            } else {
                String string3 = getResources().getString(R.string.Unit_time);
                SpannableString spannableString3 = new SpannableString("0" + string3);
                spannableString3.setSpan(new RelativeSizeSpan(0.35f), spannableString3.toString().indexOf(string3), spannableString3.toString().indexOf(string3) + string3.length(), 33);
                this.tvD3Plan.setText(spannableString3);
            }
        }
        setupCenterArcView();
        if (d3Record.getState().getFeedState() != null) {
            String string4 = this.mContext.getResources().getString(R.string.Unit_g);
            this.tvD3RealEat.setText(String.format("%s%s", Integer.valueOf(d3Record.getState().getFeedState().getEatAmountTotal()), string4));
            this.tvD3PlanEat.setText(String.format("%s%s", Integer.valueOf(d3Record.getState().getFeedState().getPlanAmountTotal()), string4));
            this.tvD3RemainingWeight.setText(String.format("%s%s", Integer.valueOf(d3Record.getState().getWeight()), string4));
            this.tvD3MaxWeight.setText(String.format("%s%s", "200", string4));
        } else {
            String string5 = this.mContext.getResources().getString(R.string.Unit_g);
            this.tvD3RealEat.setText(String.format("%s%s", "0", string5));
            this.tvD3PlanEat.setText(String.format("%s%s", "0", string5));
            this.tvD3RemainingWeight.setText(String.format("%s%s", "0", string5));
            this.tvD3MaxWeight.setText(String.format("%s%s", "200", string5));
        }
        if (d3Record.getState().getPim() == 0 && (d3Record.getState().getFeedState() == null || d3Record.getState().getFeedState().getEatTimes() == null || (d3Record.getState().getFeedState().getEatAmountTotal() == 0 && d3Record.getState().getFeedState().getEatTimes().size() == 0))) {
            String string6 = this.mContext.getResources().getString(R.string.Unit_time);
            String string7 = this.mContext.getResources().getString(R.string.Unit_g);
            SpannableString spannableString4 = new SpannableString("?" + string6 + "/?" + string7);
            spannableString4.setSpan(new RelativeSizeSpan(0.7f), spannableString4.toString().indexOf(string6), spannableString4.toString().indexOf(string6) + string6.length(), 33);
            spannableString4.setSpan(new RelativeSizeSpan(0.7f), spannableString4.toString().indexOf(string7), spannableString4.toString().indexOf(string7) + string7.length(), 33);
            this.tvTodayFood.setText(spannableString4);
        } else if (d3Record.getState().getFeedState() != null && d3Record.getState().getFeedState().getEatTimes() != null) {
            if (d3Record.getState().getFeedState().getEatTimes().size() > 1) {
                resources = this.mContext.getResources();
                i = R.string.Unit_time;
            } else {
                resources = this.mContext.getResources();
                i = R.string.Unit_times;
            }
            String string8 = resources.getString(i);
            if (d3Record.getState().getFeedState().getEatAmountTotal() >= 1000) {
                resources2 = this.mContext.getResources();
                i2 = R.string.Unit_kg;
            } else {
                resources2 = this.mContext.getResources();
                i2 = R.string.Unit_g;
            }
            String string9 = resources2.getString(i2);
            SpannableString spannableString5 = new SpannableString(d3Record.getState().getFeedState().getEatTimes().size() + string8 + "/" + D3Utils.calcD3AmountUnit(this.mContext, d3Record.getState().getFeedState().getEatAmountTotal()));
            spannableString5.setSpan(new RelativeSizeSpan(0.7f), spannableString5.toString().indexOf(string8), spannableString5.toString().indexOf(string8) + string8.length(), 33);
            int iIndexOf = spannableString5.toString().indexOf(string9);
            spannableString5.setSpan(new RelativeSizeSpan(0.7f), iIndexOf, string9.length() + iIndexOf, 33);
            this.tvTodayFood.setText(spannableString5);
        } else {
            String string10 = this.mContext.getResources().getString(R.string.Unit_time);
            String string11 = this.mContext.getResources().getString(R.string.Unit_g);
            SpannableString spannableString6 = new SpannableString(0 + string10 + "/" + D3Utils.calcD3AmountUnit(this.mContext, 0));
            spannableString6.setSpan(new RelativeSizeSpan(0.7f), spannableString6.toString().indexOf(string10), spannableString6.toString().indexOf(string10) + string10.length(), 33);
            spannableString6.setSpan(new RelativeSizeSpan(0.7f), spannableString6.toString().indexOf(string11), spannableString6.toString().indexOf(string11) + string11.length(), 33);
            this.tvTodayFood.setText(spannableString6);
        }
        this.bleTimeView.setTimeDataList(processFeedStateData());
        int i4 = this.recordType;
        if (i4 == 1) {
            this.tvStatisticsMonth.setSelected(true);
            this.tvStatisticsDate.setSelected(false);
            this.tvStatisticsWeek.setSelected(false);
            this.tvStatisticsMonth.setTextColor(-1);
            this.tvStatisticsWeek.setTextColor(getResources().getColor(R.color.feeder_plan_save_black));
            this.tvStatisticsDate.setTextColor(getResources().getColor(R.color.feeder_plan_save_black));
        } else if (i4 == 2) {
            this.tvStatisticsWeek.setSelected(true);
            this.tvStatisticsDate.setSelected(false);
            this.tvStatisticsMonth.setSelected(false);
            this.tvStatisticsWeek.setTextColor(-1);
            this.tvStatisticsMonth.setTextColor(getResources().getColor(R.color.feeder_plan_save_black));
            this.tvStatisticsDate.setTextColor(getResources().getColor(R.color.feeder_plan_save_black));
        } else if (i4 == 3) {
            this.tvStatisticsDate.setSelected(true);
            this.tvStatisticsWeek.setSelected(false);
            this.tvStatisticsMonth.setSelected(false);
            this.tvStatisticsDate.setTextColor(-1);
            this.tvStatisticsWeek.setTextColor(getResources().getColor(R.color.feeder_plan_save_black));
            this.tvStatisticsMonth.setTextColor(getResources().getColor(R.color.feeder_plan_save_black));
        }
        if (d3Record.getState().getPim() != 0 && d3Record.getState().getBroadcast() != null) {
            long start_time = d3Record.getState().getBroadcast().getStart_time() * 1000;
            long duration = d3Record.getState().getBroadcast().getDuration() * 1000;
            int i5 = (int) (duration / 2000);
            if (start_time + duration > System.currentTimeMillis()) {
                this.fsvVoiceAni.setRepeatTimes(i5 - 1);
                this.fsvVoiceAni.start();
                this.voiceStart = true;
                if (this.alpha > 0.5f) {
                    this.fsvVoiceAni.setVisibility(0);
                }
            } else {
                this.voiceStart = false;
                this.fsvVoiceAni.setVisibility(4);
                if (d3Record.getFeed() != null) {
                    d3Record.getFeed().getSuspended();
                }
            }
        } else if (d3Record.getFeed() != null) {
            d3Record.getFeed().getSuspended();
        }
        checkDeviceWarnState();
        this.fsvVoiceAni.post(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.d3.widget.D3HomeDeviceView.7
            @Override // java.lang.Runnable
            public void run() {
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) D3HomeDeviceView.this.fsvVoiceAni.getLayoutParams();
                layoutParams.setMargins(layoutParams.leftMargin, (ArmsUtils.dip2px(D3HomeDeviceView.this.mContext, 40.0f) + D3HomeDeviceView.this.fsvVoiceAni.getMeasuredHeight()) * (-1), layoutParams.rightMargin, layoutParams.bottomMargin);
                D3HomeDeviceView.this.fsvVoiceAni.setLayoutParams(layoutParams);
            }
        });
    }

    private List<BleFeederTimeView.BleFeederTimeViewData> processFeedStateData() {
        ArrayList arrayList = new ArrayList();
        if (this.d3Record.getState().getFeedState() != null) {
            List<Integer> eatTimes = this.d3Record.getState().getFeedState().getEatTimes();
            List<Integer> feedTimes = this.d3Record.getState().getFeedState().getFeedTimes();
            arrayList.clear();
            int i = R.drawable.d3_time_plan_icon;
            if (eatTimes != null) {
                for (int i2 = 0; i2 < eatTimes.size(); i2++) {
                    arrayList.add(new BleFeederTimeView.BleFeederTimeViewData(eatTimes.get(i2).intValue() / 60, eatTimes.get(i2).intValue() / 60, BitmapFactory.decodeResource(getResources(), D3Utils.checkD3Timeout(eatTimes.get(i2).intValue(), this.d3Record.getActualTimeZone()) ? R.drawable.d3_time_icon : i), BitmapFactory.decodeResource(getResources(), D3Utils.checkD3Timeout(eatTimes.get(i2).intValue(), this.d3Record.getActualTimeZone()) ? R.drawable.d3_time_icons : i), 1));
                }
            }
            if (feedTimes != null) {
                for (int i3 = 0; i3 < feedTimes.size(); i3++) {
                    arrayList.add(new BleFeederTimeView.BleFeederTimeViewData(feedTimes.get(i3).intValue() / 60, feedTimes.get(i3).intValue() / 60, BitmapFactory.decodeResource(getResources(), D3Utils.checkD3Timeout(feedTimes.get(i3).intValue(), this.d3Record.getActualTimeZone()) ? R.drawable.d3_time_icon : i), BitmapFactory.decodeResource(getResources(), D3Utils.checkD3Timeout(feedTimes.get(i3).intValue(), this.d3Record.getActualTimeZone()) ? R.drawable.d3_time_icons : i), 2));
                }
            }
            Collections.sort(arrayList);
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                if (D3Utils.checkD3Timeout(((BleFeederTimeView.BleFeederTimeViewData) arrayList.get(size)).getEndTime() * 60, this.d3Record.getActualTimeZone()) && ((BleFeederTimeView.BleFeederTimeViewData) arrayList.get(size)).getType() == 2) {
                    arrayList.remove(size);
                }
            }
        }
        return arrayList;
    }

    private void setupCenterArcView() {
        if (this.d3Record.getState().getFeedState().getPlanAmountTotal() == 0) {
            if (this.d3Record.getState().getFeedState().getEatAmountTotal() == 0) {
                this.pbBucket1.setPercent(0.0f);
            } else {
                this.pbBucket1.setPercent(100.0f);
            }
        } else {
            this.pbBucket1.setPercent((this.d3Record.getState().getFeedState().getEatAmountTotal() * 100.0f) / this.d3Record.getState().getFeedState().getPlanAmountTotal());
        }
        this.tvPlanAmount.setText(getResources().getString(R.string.Feeder_amount_plan_format, String.valueOf(this.d3Record.getState().getFeedState().getPlanAmountTotal()), this.mContext.getResources().getString(R.string.Unit_g)));
        if (this.d3Record.getState().getPim() != 0 && this.d3Record.getState().getFeeding() == 1) {
            this.bleMenuView.setFirstButtonText(getResources().getString(R.string.Stop_feed));
            this.bleMenuView.changeMenuBtnState(0);
            this.tvEatPrompt.setVisibility(4);
            this.tvSmallArcCenterText.setTextColor(getResources().getColor(R.color.common_text));
            this.tvSmallArcCenterText.setText(this.mContext.getResources().getString(R.string.Feeding));
            this.tvSmallArcCenterText.setTextSize(20.0f);
            this.tvBucket1.setVisibility(4);
            startFeedAni();
        } else {
            stopFeedAni();
            this.tvBucket1.setVisibility(0);
            this.bleMenuView.setFirstButtonText(getResources().getString(R.string.Extra_feeding));
            this.bleMenuView.changeAllMenuBtnState(false);
            this.tvEatPrompt.setVisibility(4);
            this.tvSmallArcCenterText.setTextColor(getResources().getColor(R.color.common_text));
            this.tvSmallArcCenterText.setTextSize(20.0f);
            boolean zEqualsIgnoreCase = "zh_CN".equalsIgnoreCase(UserInforUtils.getLanguageLocaleString(CommonUtils.getAppContext()));
            String strValueOf = String.valueOf(this.d3Record.getState().getFeedState().getEatAmountTotal());
            StringBuilder sb = new StringBuilder();
            Integer.parseInt(strValueOf);
            String string = getResources().getString(R.string.Unit_g);
            sb.append(strValueOf);
            sb.append(zEqualsIgnoreCase ? "" : " ");
            sb.append(string);
            SpannableString spannableString = new SpannableString(sb.toString());
            int length = strValueOf.length();
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
        sb2.append(this.d3Record.getState().getFeedState().getRealAmountTotal());
        sb2.append(str);
        this.d3Record.getState().getFeedState().getRealAmountTotal();
        sb2.append(getResources().getString(R.string.Unit_g));
        textView.setText(sb2.toString());
        TextView textView2 = this.tvHasEat;
        StringBuilder sb3 = new StringBuilder();
        sb3.append(getResources().getString(R.string.Has_eated));
        sb3.append(str);
        sb3.append(this.d3Record.getState().getFeedState().getEatAmountTotal());
        sb3.append(str);
        this.d3Record.getState().getFeedState().getEatAmountTotal();
        sb3.append(getResources().getString(R.string.Unit_g));
        textView2.setText(sb3.toString());
        TextView textView3 = this.tvPlanHasFinishedNum1;
        StringBuilder sb4 = new StringBuilder();
        sb4.append(this.d3Record.getState().getFeedState().getPlanRealAmountTotal());
        this.d3Record.getState().getFeedState().getPlanRealAmountTotal();
        sb4.append(getResources().getString(R.string.Unit_g));
        textView3.setText(sb4.toString());
        TextView textView4 = this.tvManualFeedNum1;
        StringBuilder sb5 = new StringBuilder();
        sb5.append(this.d3Record.getState().getFeedState().getRealAmountTotal() - this.d3Record.getState().getFeedState().getPlanRealAmountTotal());
        int realAmountTotal = this.d3Record.getState().getFeedState().getRealAmountTotal() - this.d3Record.getState().getFeedState().getPlanRealAmountTotal();
        sb5.append(getResources().getString(R.string.Unit_g));
        textView4.setText(sb5.toString());
        if (this.d3Record.getState().getFeedState().getRealAmountTotal() - this.d3Record.getState().getFeedState().getPlanRealAmountTotal() == 0) {
            this.llManualFeedNum1.setVisibility(8);
        } else {
            this.llManualFeedNum1.setVisibility(0);
        }
    }

    private void checkDeviceWarnState() {
        int i = !CommonUtils.isSameTimeZoneAsLocal(this.d3Record.getLocale(), this.d3Record.getTimezone()) ? 1 : 0;
        if (this.d3Record.getState().getWifi().getRsq() < -70) {
            i++;
        }
        if (i == 0) {
            this.tvLittleWarnText.setVisibility(8);
            return;
        }
        if (i == 1) {
            this.tvLittleWarnText.setVisibility(0);
            if (!CommonUtils.isSameTimeZoneAsLocal(this.d3Record.getLocale(), this.d3Record.getTimezone())) {
                this.tvLittleWarnText.setText(R.string.Time_zone_is_different);
                return;
            } else {
                if (this.d3Record.getState().getWifi().getRsq() < -70) {
                    this.tvLittleWarnText.setText(R.string.Wifi_signal_weak);
                    return;
                }
                return;
            }
        }
        this.tvLittleWarnText.setVisibility(0);
        this.tvLittleWarnText.setText(String.format(getResources().getString(R.string.Have_mistake_to_handle), String.valueOf(i)));
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
            if (this.alpha < 0.5f) {
                this.fsvVoiceAni.setVisibility(4);
            } else if (this.voiceStart) {
                this.fsvVoiceAni.setVisibility(0);
            }
        }
        if (f == 1.0f) {
            if (this.bottomPanelAllVisible) {
                return;
            }
            this.bottomPanelAllVisible = true;
            return;
        }
        this.bottomPanelAllVisible = false;
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

    private void initFrameAnimation() {
        List<Integer> listAsList = Arrays.asList(Integer.valueOf(R.drawable.d3_voice_ani_1), Integer.valueOf(R.drawable.d3_voice_ani_2), Integer.valueOf(R.drawable.d3_voice_ani_3), Integer.valueOf(R.drawable.d3_voice_ani_4), Integer.valueOf(R.drawable.d3_voice_ani_5), Integer.valueOf(R.drawable.d3_voice_ani_6), Integer.valueOf(R.drawable.d3_voice_ani_7), Integer.valueOf(R.drawable.d3_voice_ani_8), Integer.valueOf(R.drawable.d3_voice_ani_9), Integer.valueOf(R.drawable.d3_voice_ani_10), Integer.valueOf(R.drawable.d3_voice_ani_11), Integer.valueOf(R.drawable.d3_voice_ani_12), Integer.valueOf(R.drawable.d3_voice_ani_13), Integer.valueOf(R.drawable.d3_voice_ani_14), Integer.valueOf(R.drawable.d3_voice_ani_15));
        this.bitmaps = listAsList;
        this.fsvVoiceAni.setBitmapIds(listAsList);
        this.fsvVoiceAni.setDuration(2000);
        this.fsvVoiceAni.setRepeatTimes(3);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:82:0x0392  */
    @Override // android.view.View.OnClickListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onClick(android.view.View r15) {
        /*
            Method dump skipped, instruction units count: 1264
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.petkit.android.activities.petkitBleDevice.d3.widget.D3HomeDeviceView.onClick(android.view.View):void");
    }

    @Override // com.petkit.android.activities.petkitBleDevice.widget.BleDeviceHomeMenuView.OnMenuClickListener
    public void onMenuClick(BleMenuItem bleMenuItem, int i) {
        MenuOnClickListener menuOnClickListener = this.menuOnClickListener;
        if (menuOnClickListener != null) {
            if (i == 0) {
                if (this.d3Record.getState().getFeeding() == 1) {
                    this.menuOnClickListener.onBottomMenuClick(MenuType.STOP_FEED);
                    return;
                } else {
                    this.menuOnClickListener.onBottomMenuClick(MenuType.FEED_NOW);
                    return;
                }
            }
            if (i == 1) {
                menuOnClickListener.onBottomMenuClick(MenuType.CALL_PET);
            } else if (i == 2) {
                menuOnClickListener.onBottomMenuClick(MenuType.FEED_PLAN);
            } else {
                if (i != 3) {
                    return;
                }
                menuOnClickListener.onBottomMenuClick(MenuType.SURPLUS_GRAIN_CONTROL);
            }
        }
    }

    private SpannableString getAllFeedAndEatStr(int i, int i2) {
        Resources resources;
        int i3;
        String string = this.mContext.getString(R.string.Eating);
        this.mContext.getResources().getString(R.string.Out_of_food);
        String string2 = CommonUtils.getAppContext().getResources().getString(i2 >= 1000 ? R.string.Unit_kg : R.string.Unit_g);
        if (i >= 1000) {
            resources = CommonUtils.getAppContext().getResources();
            i3 = R.string.Unit_kg;
        } else {
            resources = CommonUtils.getAppContext().getResources();
            i3 = R.string.Unit_g;
        }
        resources.getString(i3);
        SpannableString spannableString = new SpannableString(string + D3Utils.calcD3AmountUnit(this.mContext, i2));
        spannableString.setSpan(new RelativeSizeSpan(0.7f), spannableString.toString().indexOf(string), spannableString.toString().indexOf(string) + string.length(), 33);
        if (spannableString.toString().indexOf(string2) != -1) {
            spannableString.setSpan(new RelativeSizeSpan(0.7f), spannableString.toString().indexOf(string2), spannableString.toString().indexOf(string2) + string2.length(), 33);
            spannableString.setSpan(new StyleSpan(1), spannableString.toString().indexOf(string2), spannableString.toString().indexOf(string2) + string2.length(), 33);
        }
        return spannableString;
    }

    public void refreshDateData(D3Statistic d3Statistic, int i) {
        this.tvHistoryStatisticDate.setText(D3Utils.getDateByStatisticTime("" + d3Statistic.getDay(), this.activity));
        String str = String.format("%s%s", getResources().getString(R.string.About), d3Statistic.getEatAmount() + "g");
        SpannableString spannableString = new SpannableString(str);
        int iIndexOf = str.indexOf(String.valueOf(d3Statistic.getEatAmount()));
        spannableString.setSpan(new AbsoluteSizeSpan(40, true), iIndexOf, String.valueOf(d3Statistic.getEatAmount()).length() + iIndexOf, 33);
        spannableString.setSpan(new ForegroundColorSpan(this.activity.getResources().getColor(R.color.w5_home_black)), iIndexOf, String.valueOf(d3Statistic.getEatAmount()).length() + iIndexOf, 33);
        spannableString.setSpan(new AbsoluteSizeSpan(14, true), 0, iIndexOf, 33);
        spannableString.setSpan(new ForegroundColorSpan(this.activity.getResources().getColor(R.color.gray)), 0, iIndexOf, 33);
        spannableString.setSpan(new AbsoluteSizeSpan(14, true), String.valueOf(d3Statistic.getEatAmount()).length() + iIndexOf, str.length(), 33);
        spannableString.setSpan(new ForegroundColorSpan(this.activity.getResources().getColor(R.color.gray)), iIndexOf + String.valueOf(d3Statistic.getEatAmount()).length(), str.length(), 33);
        this.tvHistoryRecord.setText(spannableString);
        refreshBarChart();
        refreshDateData(this.d3DailyFeeds);
    }

    private void refreshBarChart() {
        this.d3Histogram.updateBarChartData(0);
    }

    public void refreshDateData(D3DailyFeeds d3DailyFeeds) {
        this.d3DailyFeeds = d3DailyFeeds;
        if (d3DailyFeeds != null) {
            this.guidePageIndex = null;
            this.d3HomeRecordAdapter = new D3HomeRecordAdapter(this.mContext, this.d3Record.getDeviceId(), this, this, this, this.d3Record.getDeviceShared() != null);
            this.d3HomeRecordAdapter.setD3DateFeedData(new D3DateFeedData(d3DailyFeeds.getFeed().get(0).getItems(), d3DailyFeeds.getEat().get(0).getItems(), d3DailyFeeds.getFeed().get(0).getDay()));
            this.rvRecordView.setAdapter(this.d3HomeRecordAdapter);
            this.tvStatistics.setText(getAllFeedAndEatStr(d3DailyFeeds.getFeed().get(0).getRealAmount(), d3DailyFeeds.getEat().get(0).getEatAmount()));
            this.rvRecordView.post(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.d3.widget.D3HomeDeviceView.10
                @Override // java.lang.Runnable
                public void run() {
                    if (D3HomeDeviceView.this.rvRecordView.getMeasuredHeight() >= ArmsUtils.dip2px(D3HomeDeviceView.this.mContext, 300.0f)) {
                        D3HomeDeviceView.this.rvRecordView.setPadding(0, 0, 0, ArmsUtils.dip2px(D3HomeDeviceView.this.mContext, 50.0f));
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
                this.bleMenuView.setDeviceType(9, 0);
                this.pbBucket1.setVisibility(0);
                this.tvPlanAmount.setVisibility(0);
                this.tvPlanPrompt.setVisibility(8);
                return;
            }
        }
        this.bleMenuView.setIsFeederPlanIsOpen(false);
        this.bleMenuView.setDeviceType(9, 0);
        this.pbBucket1.setVisibility(4);
        this.tvPlanAmount.setVisibility(8);
        this.tvPlanPrompt.setVisibility(0);
    }

    public void startFeedAni() {
        Observable.just("").delay(500L, TimeUnit.MILLISECONDS).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<String>() { // from class: com.petkit.android.activities.petkitBleDevice.d3.widget.D3HomeDeviceView.11
            @Override // io.reactivex.functions.Consumer
            public void accept(String str) throws Exception {
                D3HomeDeviceView.this.pbBucket1.setOpenAnimation(true);
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
        activity.startActivity(BleDeviceWifiSettingActivity.newIntent(activity, this.d3Record.getDeviceId(), 9));
    }

    @Override // com.petkit.android.activities.petkitBleDevice.widget.BleDeviceHomeOfflineWarnWindow.OfflineClickListener
    public void onClickReset() {
        Activity activity = this.activity;
        activity.startActivity(BleDeviceBindNetWorkActivity.newIntent((Context) activity, this.d3Record.getDeviceId(), 9, this.d3Record.getBtMac(), false));
    }

    public ChartOnClickListener getChartOnClickListener() {
        return this.chartOnClickListener;
    }

    public void setChartOnClickListener(ChartOnClickListener chartOnClickListener) {
        this.chartOnClickListener = chartOnClickListener;
    }

    @Override // com.petkit.android.activities.petkitBleDevice.d3.adapter.D3HomeRecordAdapter.DeleteRecordViewClick
    public void onViewClick(int i, int i2, int i3, int i4) {
        ((D3HomeRecordAdapter.DeleteRecordViewClick) this.activity).onViewClick(i, i2, i3, i4);
    }

    @Override // com.petkit.android.activities.petkitBleDevice.d3.adapter.D3HomeRecordAdapter.DeleteRecordViewClick
    public void onDeleteFeedRecordClick(int i, int i2) {
        ((D3HomeRecordAdapter.DeleteRecordViewClick) this.activity).onDeleteFeedRecordClick(i, i2);
    }

    @Override // com.petkit.android.activities.petkitBleDevice.d3.adapter.D3HomeRecordAdapter.GuidePageListener
    public void onMeasureComplete(Map<Integer, Integer> map, Integer num) {
        PetkitLog.d("onMeasureComplete,position:" + num);
        D3Record d3Record = this.d3Record;
        if (d3Record == null || d3Record.getDeviceShared() == null) {
            this.itemHeightMap = map;
            this.guidePageIndex = num;
            if (num != null && map.containsKey(num)) {
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.viewCard.getLayoutParams();
                layoutParams.height = this.itemHeightMap.get(this.guidePageIndex).intValue();
                int iIntValue = 0;
                for (Map.Entry<Integer, Integer> entry : this.itemHeightMap.entrySet()) {
                    if (entry.getKey().intValue() < this.guidePageIndex.intValue()) {
                        iIntValue += entry.getValue().intValue();
                    }
                }
                layoutParams.setMargins(layoutParams.leftMargin, iIntValue, layoutParams.rightMargin, layoutParams.bottomMargin);
                this.viewCard.setLayoutParams(layoutParams);
            }
            this.viewCard.post(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.d3.widget.D3HomeDeviceView.12
                @Override // java.lang.Runnable
                public void run() {
                }
            });
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.d3.widget.D3Histogram.OnPageChanged
    public void change(int i) {
        this.onHistogramPageChange.pageChange(i);
        stopLeftAni();
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
        super.onDetachedFromWindow();
    }

    public void showRightTopWindow() {
        this.rlRightTopWindow.setVisibility(0);
    }

    public void hideRightTopWindow() {
        this.rlRightTopWindow.setVisibility(8);
    }

    public void showGuideView3(View view) {
        if (this.guide3 != null) {
            return;
        }
        GuideBuilder guideBuilder = new GuideBuilder();
        guideBuilder.setTargetView(view).setAlpha(180).setHighTargetCorner(50).setHighTargetPaddingLeft(ArmsUtils.dip2px(this.mContext, 0.0f)).setHighTargetPaddingRight(ArmsUtils.dip2px(this.mContext, 0.0f)).setHighTargetPaddingBottom(ArmsUtils.dip2px(this.mContext, 0.0f)).setHighTargetPaddingTop(ArmsUtils.dip2px(this.mContext, 0.0f)).setOutsideTouchable(false).setAutoDismiss(false);
        guideBuilder.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() { // from class: com.petkit.android.activities.petkitBleDevice.d3.widget.D3HomeDeviceView.13
            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onShown() {
            }

            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onDismiss() {
                DataHelper.setBooleanSF(D3HomeDeviceView.this.mContext, Consts.D3_CHART_IS_FIRST, Boolean.TRUE);
                D3HomeDeviceView.this.scrollLayout.fling(1);
                D3HomeDeviceView.this.scrollLayout.setOnTouchListener(new View.OnTouchListener() { // from class: com.petkit.android.activities.petkitBleDevice.d3.widget.D3HomeDeviceView.13.1
                    @Override // android.view.View.OnTouchListener
                    public boolean onTouch(View view2, MotionEvent motionEvent) {
                        return false;
                    }
                });
            }
        });
        guideBuilder.addComponent(new GuidePetTipAndBtnAndLineComponent(new GuideParam(getResources().getString(R.string.Home_chart_guide_tips), "", 4, 32, 20, 10, getResources().getString(R.string.Done), R.layout.layout_feeder_guide_one), new ConfirmListener() { // from class: com.petkit.android.activities.petkitBleDevice.d3.widget.D3HomeDeviceView.14
            @Override // com.petkit.android.activities.petkitBleDevice.w5.guide.ConfirmListener
            public void onConfirmListener() {
                if (D3HomeDeviceView.this.guide3 != null) {
                    D3HomeDeviceView.this.guide3.dismiss();
                }
            }
        }));
        this.guide3 = guideBuilder.createGuide();
        this.contentView.post(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.d3.widget.D3HomeDeviceView.15
            @Override // java.lang.Runnable
            public void run() {
                D3HomeDeviceView.this.guide3.show((Activity) D3HomeDeviceView.this.getContext());
            }
        });
    }

    public void showGuideView2(View view) {
        if (this.guide2 != null) {
            return;
        }
        GuideBuilder guideBuilder = new GuideBuilder();
        guideBuilder.setTargetView(view).setAlpha(180).setHighTargetCorner(50).setHighTargetPaddingLeft(ArmsUtils.dip2px(this.mContext, 0.0f)).setHighTargetPaddingRight(ArmsUtils.dip2px(this.mContext, 0.0f)).setHighTargetPaddingBottom(ArmsUtils.dip2px(this.mContext, 0.0f)).setHighTargetPaddingTop(ArmsUtils.dip2px(this.mContext, 0.0f)).setOutsideTouchable(false).setAutoDismiss(false);
        guideBuilder.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() { // from class: com.petkit.android.activities.petkitBleDevice.d3.widget.D3HomeDeviceView.16
            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onShown() {
            }

            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onDismiss() {
                DataHelper.setBooleanSF(D3HomeDeviceView.this.mContext, Consts.D3_HOME_DATA_GUIDE, Boolean.TRUE);
            }
        });
        guideBuilder.addComponent(new GuideTipAndBtnAndLineComponent(new GuideParam(getResources().getString(R.string.Home_menu_guide_tips), "2/2", 2, 48, 0, -10, getResources().getString(R.string.Done), R.layout.layout_home_guide_mine), new ConfirmListener() { // from class: com.petkit.android.activities.petkitBleDevice.d3.widget.D3HomeDeviceView.17
            @Override // com.petkit.android.activities.petkitBleDevice.w5.guide.ConfirmListener
            public void onConfirmListener() {
                if (D3HomeDeviceView.this.guide2 != null) {
                    D3HomeDeviceView.this.guide2.dismiss();
                }
            }
        }));
        this.guide2 = guideBuilder.createGuide();
        this.contentView.post(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.d3.widget.D3HomeDeviceView.18
            @Override // java.lang.Runnable
            public void run() {
                D3HomeDeviceView.this.guide2.show((Activity) D3HomeDeviceView.this.getContext());
            }
        });
    }

    public void showGuideView(View view) {
        if (this.guide1 != null) {
            return;
        }
        GuideBuilder guideBuilder = new GuideBuilder();
        guideBuilder.setTargetView(view).setAlpha(180).setHighTargetCorner(50).setHighTargetPaddingLeft(ArmsUtils.dip2px(this.mContext, 8.0f)).setHighTargetPaddingRight(ArmsUtils.dip2px(this.mContext, 8.0f)).setHighTargetPaddingBottom(ArmsUtils.dip2px(this.mContext, 8.0f)).setHighTargetPaddingTop(ArmsUtils.dip2px(this.mContext, 8.0f)).setOutsideTouchable(false).setAutoDismiss(false);
        guideBuilder.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() { // from class: com.petkit.android.activities.petkitBleDevice.d3.widget.D3HomeDeviceView.19
            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onShown() {
            }

            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onDismiss() {
                if (D3HomeDeviceView.this.bleMenuView.getVisibility() != 8) {
                    D3HomeDeviceView d3HomeDeviceView = D3HomeDeviceView.this;
                    d3HomeDeviceView.showGuideView2(d3HomeDeviceView.bleMenuView);
                }
            }
        });
        guideBuilder.addComponent(new GuidePetTipAndBtnAndLineComponent(new GuideParam(getResources().getString(R.string.D3_top_guide_tips), "1/2", 4, 32, -50, 10, getResources().getString(R.string.Next_tip), R.layout.layout_feeder_guide_one), new ConfirmListener() { // from class: com.petkit.android.activities.petkitBleDevice.d3.widget.D3HomeDeviceView.20
            @Override // com.petkit.android.activities.petkitBleDevice.w5.guide.ConfirmListener
            public void onConfirmListener() {
                if (D3HomeDeviceView.this.guide1 != null) {
                    D3HomeDeviceView.this.guide1.dismiss();
                }
            }
        }));
        this.guide1 = guideBuilder.createGuide();
        this.contentView.post(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.d3.widget.D3HomeDeviceView.21
            @Override // java.lang.Runnable
            public void run() {
                D3HomeDeviceView.this.guide1.show((Activity) D3HomeDeviceView.this.getContext());
            }
        });
    }

    public void showRemoveRecordGuideView() {
        this.viewCard.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.petkit.android.activities.petkitBleDevice.d3.widget.D3HomeDeviceView.22
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                D3HomeDeviceView.this.removeRecordGuideBuilder = new GuideBuilder();
                D3HomeDeviceView.this.removeRecordGuideBuilder.setTargetView(D3HomeDeviceView.this.viewCard).setAlpha(180).setHighTargetCorner(30).setOutsideTouchable(false).setAutoDismiss(false);
                D3HomeDeviceView.this.removeRecordGuideBuilder.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() { // from class: com.petkit.android.activities.petkitBleDevice.d3.widget.D3HomeDeviceView.22.1
                    @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
                    public void onShown() {
                    }

                    @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
                    public void onDismiss() {
                        D3HomeDeviceView.this.viewCard.setVisibility(8);
                        DataHelper.setBooleanSF(D3HomeDeviceView.this.activity, Consts.D3_REMOVE_RECORD_GUIDE_PAGE_SHOWED, Boolean.TRUE);
                    }
                });
                D3HomeDeviceView.this.removeRecordGuideBuilder.addComponent(new GuideD3TipAndBtnAndLineComponent(new GuideParam(D3HomeDeviceView.this.getResources().getString(R.string.Pet_feed_records_can_be_slip_deleted), "", 2, 32, 0, 0, "", R.layout.layout_d3_guide_remove_record), new ConfirmListener() { // from class: com.petkit.android.activities.petkitBleDevice.d3.widget.D3HomeDeviceView.22.2
                    @Override // com.petkit.android.activities.petkitBleDevice.w5.guide.ConfirmListener
                    public void onConfirmListener() {
                        if (D3HomeDeviceView.this.removeRecordGuide != null) {
                            D3HomeDeviceView.this.removeRecordGuide.dismiss();
                        }
                    }
                }));
                D3HomeDeviceView d3HomeDeviceView = D3HomeDeviceView.this;
                d3HomeDeviceView.removeRecordGuide = d3HomeDeviceView.removeRecordGuideBuilder.createGuide();
                D3HomeDeviceView.this.removeRecordGuide.show(D3HomeDeviceView.this.activity);
                D3HomeDeviceView.this.viewCard.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    public void showSurplusGrainWindow(View view, int i) {
        this.isNeedShow = false;
        View viewInflate = View.inflate(this.mContext, R.layout.layout_d3_pop_tip, null);
        ImageView imageView = (ImageView) viewInflate.findViewById(R.id.iv_bottom_arrow);
        TextView textView = (TextView) viewInflate.findViewById(R.id.tv_tip_content);
        LinearLayout linearLayout = (LinearLayout) viewInflate.findViewById(R.id.ll_root);
        textView.setText(this.mContext.getResources().getString(R.string.Surplus_grain_control_home_tip, this.d3Record.getState().getWeight() + this.mContext.getResources().getString(R.string.Unit_g)));
        int iDip2px = (int) ((((float) (BaseApplication.displayMetrics.widthPixels - ArmsUtils.dip2px(this.mContext, 0.0f))) / 4.0f) / 2.0f);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) imageView.getLayoutParams();
        layoutParams.setMargins(layoutParams.leftMargin, layoutParams.topMargin, iDip2px, i - ArmsUtils.dip2px(this.mContext, 23.0f));
        imageView.setLayoutParams(layoutParams);
        PopupWindow popupWindow = this.popupWindow;
        if (popupWindow == null || !popupWindow.isShowing()) {
            PopupWindow popupWindow2 = new PopupWindow(-1, -2);
            this.popupWindow = popupWindow2;
            popupWindow2.setContentView(viewInflate);
            this.popupWindow.setOutsideTouchable(true);
            this.popupWindow.setFocusable(true);
            this.popupWindow.showAtLocation(view, 80, 0, 0);
            linearLayout.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.d3.widget.D3HomeDeviceView.23
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    if (D3HomeDeviceView.this.popupWindow == null || !D3HomeDeviceView.this.popupWindow.isShowing()) {
                        return;
                    }
                    D3HomeDeviceView.this.popupWindow.dismiss();
                    D3HomeDeviceView.this.popupWindow = null;
                }
            });
        }
    }

    public void showSurplusGrainWindow() {
        if (this.panelHeight > 0) {
            showSurplusGrainWindow(this.activity.getWindow().getDecorView(), ArmsUtils.dip2px(this.mContext, 125.0f));
        } else {
            this.isNeedShow = true;
        }
        BleDeviceHomeMenuView bleDeviceHomeMenuView = this.bleMenuView;
        if (bleDeviceHomeMenuView != null) {
            bleDeviceHomeMenuView.changeMenuAniState(3, true);
        }
    }

    public void dismissSurplusGrainWindow() {
        PopupWindow popupWindow = this.popupWindow;
        if (popupWindow != null && popupWindow.isShowing()) {
            this.popupWindow.dismiss();
            this.popupWindow = null;
        }
        BleDeviceHomeMenuView bleDeviceHomeMenuView = this.bleMenuView;
        if (bleDeviceHomeMenuView != null) {
            bleDeviceHomeMenuView.changeMenuAniState(3, false);
        }
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

    private void stopLeftAni() {
        for (int i = 0; i < this.animatorLeftSetList.size(); i++) {
            this.animatorLeftSetList.get(i).cancel();
        }
        this.animatorLeftSetList.clear();
        this.ivLeftArrow.setVisibility(8);
    }

    public void setOnCenterProgressViewMeasureComplete(OnCenterProgressViewMeasureComplete onCenterProgressViewMeasureComplete) {
        this.onCenterProgressViewMeasureComplete = onCenterProgressViewMeasureComplete;
    }

    public void setPromoteView(RelatedProductsInfor relatedProductsInfor) {
        ArrayList arrayList = new ArrayList();
        if (relatedProductsInfor != null && relatedProductsInfor.getPromote() != null) {
            arrayList.addAll(relatedProductsInfor.getPromote());
        }
        if (arrayList.size() == 0) {
            this.promoteView.setVisibility(8);
        } else {
            this.promoteView.refreshPromoteData(arrayList);
            this.promoteView.setPromoteViewOnItemListener(new PromoteView.PromoteViewOnItemListener() { // from class: com.petkit.android.activities.petkitBleDevice.d3.widget.D3HomeDeviceView.24
                @Override // com.petkit.android.activities.mall.widget.PromoteView.PromoteViewOnItemListener
                public void onItemClick(PromoteData promoteData) {
                    EventBus.getDefault().post(promoteData);
                }
            });
        }
    }

    public void setCurrentItem(int i) {
        this.d3Histogram.setCurrentItem(i);
    }

    private void startExpandAni(final int i, int i2) {
        if (this.isRunning) {
            return;
        }
        ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(i2, i);
        this.expandValueAnimator = valueAnimatorOfInt;
        valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.petkit.android.activities.petkitBleDevice.d3.widget.D3HomeDeviceView.25
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int iIntValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                D3HomeDeviceView d3HomeDeviceView = D3HomeDeviceView.this;
                if (d3HomeDeviceView.lastValue >= iIntValue || d3HomeDeviceView.llAccessoryConsumables == null) {
                    return;
                }
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) D3HomeDeviceView.this.llAccessoryConsumables.getLayoutParams();
                layoutParams.width = iIntValue;
                D3HomeDeviceView.this.llAccessoryConsumables.setLayoutParams(layoutParams);
                D3HomeDeviceView.this.lastValue = iIntValue;
            }
        });
        this.expandValueAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.petkit.android.activities.petkitBleDevice.d3.widget.D3HomeDeviceView.26
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                D3HomeDeviceView d3HomeDeviceView = D3HomeDeviceView.this;
                d3HomeDeviceView.isRunning = false;
                d3HomeDeviceView.accessoryConsumables = 0;
                D3HomeDeviceView d3HomeDeviceView2 = D3HomeDeviceView.this;
                int i3 = i;
                d3HomeDeviceView2.startReduceAni(i3, i3 - d3HomeDeviceView2.tvWidth);
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
        valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.petkit.android.activities.petkitBleDevice.d3.widget.D3HomeDeviceView.27
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int iIntValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                D3HomeDeviceView d3HomeDeviceView = D3HomeDeviceView.this;
                if (d3HomeDeviceView.lastValue <= iIntValue || d3HomeDeviceView.llAccessoryConsumables == null) {
                    return;
                }
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) D3HomeDeviceView.this.llAccessoryConsumables.getLayoutParams();
                layoutParams.width = iIntValue;
                D3HomeDeviceView.this.llAccessoryConsumables.setLayoutParams(layoutParams);
                D3HomeDeviceView.this.lastValue = iIntValue;
            }
        });
        this.reduceValueAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.petkit.android.activities.petkitBleDevice.d3.widget.D3HomeDeviceView.28
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                D3HomeDeviceView.this.accessoryConsumables = 1;
                D3HomeDeviceView.this.isRunning = false;
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
        this.llAccessoryConsumables.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.petkit.android.activities.petkitBleDevice.d3.widget.D3HomeDeviceView.29
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                D3HomeDeviceView d3HomeDeviceView = D3HomeDeviceView.this;
                d3HomeDeviceView.measuredWidth = d3HomeDeviceView.llAccessoryConsumables.getMeasuredWidth();
                D3HomeDeviceView d3HomeDeviceView2 = D3HomeDeviceView.this;
                d3HomeDeviceView2.lastValue = d3HomeDeviceView2.measuredWidth;
                D3HomeDeviceView d3HomeDeviceView3 = D3HomeDeviceView.this;
                d3HomeDeviceView3.tvWidth = d3HomeDeviceView3.tvAccessoryConsumables.getMeasuredWidth();
                D3HomeDeviceView d3HomeDeviceView4 = D3HomeDeviceView.this;
                d3HomeDeviceView4.startReduceAni(d3HomeDeviceView4.measuredWidth, D3HomeDeviceView.this.measuredWidth - D3HomeDeviceView.this.tvWidth);
                D3HomeDeviceView.this.llAccessoryConsumables.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }
}

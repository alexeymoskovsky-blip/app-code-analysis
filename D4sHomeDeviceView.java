package com.petkit.android.activities.petkitBleDevice.d4s.widget;

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
import android.graphics.RectF;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Looper;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
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
import com.petkit.android.activities.device.widget.ThreeChoicesWindow;
import com.petkit.android.activities.feeder.model.DifferentFeedPlan;
import com.petkit.android.activities.home.utils.GuidePetTipAndBtnAndLineComponent;
import com.petkit.android.activities.home.utils.GuideTipAndBtnAndLineComponent;
import com.petkit.android.activities.mall.mode.PromoteData;
import com.petkit.android.activities.mall.widget.PromoteView;
import com.petkit.android.activities.petkitBleDevice.BleDeviceBindNetWorkActivity;
import com.petkit.android.activities.petkitBleDevice.BleDeviceWifiSettingActivity;
import com.petkit.android.activities.petkitBleDevice.d3.widget.BarChartView;
import com.petkit.android.activities.petkitBleDevice.d3.widget.BleFeederTimeView;
import com.petkit.android.activities.petkitBleDevice.d4s.D4sHomeActivity;
import com.petkit.android.activities.petkitBleDevice.d4s.adapter.D4sHomeRecordAdapter;
import com.petkit.android.activities.petkitBleDevice.d4s.mode.D4sCenterViewCallBack;
import com.petkit.android.activities.petkitBleDevice.d4s.mode.D4sDailyFeeds;
import com.petkit.android.activities.petkitBleDevice.d4s.mode.D4sDateFeedData;
import com.petkit.android.activities.petkitBleDevice.d4s.mode.D4sRecord;
import com.petkit.android.activities.petkitBleDevice.d4s.mode.D4sRemoveData;
import com.petkit.android.activities.petkitBleDevice.d4s.mode.D4sStatistic;
import com.petkit.android.activities.petkitBleDevice.d4s.utils.D4sUtils;
import com.petkit.android.activities.petkitBleDevice.d4s.widget.D4sHistogram;
import com.petkit.android.activities.petkitBleDevice.mode.BleMenuItem;
import com.petkit.android.activities.petkitBleDevice.r2.widget.NewIKnowWindow;
import com.petkit.android.activities.petkitBleDevice.t4.mode.RelatedProductsInfor;
import com.petkit.android.activities.petkitBleDevice.utils.BleDeviceUtils;
import com.petkit.android.activities.petkitBleDevice.utils.TextUtil;
import com.petkit.android.activities.petkitBleDevice.utils.TimeUtils;
import com.petkit.android.activities.petkitBleDevice.w5.guide.ConfirmListener;
import com.petkit.android.activities.petkitBleDevice.w5.guide.GuideParam;
import com.petkit.android.activities.petkitBleDevice.widget.BleDeviceHomeMenuView;
import com.petkit.android.activities.petkitBleDevice.widget.BleDeviceHomeOfflineWarnWindow;
import com.petkit.android.activities.petkitBleDevice.widget.PetkitSlidingUpPanelLayout;
import com.petkit.android.activities.petkitBleDevice.widget.ScrollRecyclerView;
import com.petkit.android.activities.petkitBleDevice.widget.ScrollViewWithListener2;
import com.petkit.android.activities.statistics.widget.VerticalScrollView;
import com.petkit.android.activities.universalWindow.NormalCenterTipWindow;
import com.petkit.android.model.ItemsBean;
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
public class D4sHomeDeviceView extends LinearLayout implements PetkitSlidingUpPanelLayout.PanelSlideListener, View.OnClickListener, BleDeviceHomeMenuView.OnMenuClickListener, D4sHomeRecordAdapter.OnClickListener, BleDeviceHomeOfflineWarnWindow.OfflineClickListener, BarChartView.ChartOnClickListener, D4sCenterViewCallBack, D4sHistogram.ChartOnClickListener {
    private int accessoryConsumables;
    private Activity activity;
    private float alpha;
    private Animation animation;
    private List<AnimatorSet> animatorLeftSetList;
    private BarChartView barChartView;
    private BleDeviceHomeMenuView bleMenuView;
    private ChartOnClickListener chartOnClickListener;
    private int clickOuterIndex;
    private View contentView;
    private D4sDailyFeeds d4DailyFeeds;
    private D4sHistogram d4Histogram;
    private D4sHomeRecordAdapter d4HomeRecordAdapter;
    private BleFeederTimeView d4TimeView;
    private ThreeChoicesWindow d4sFoodWarnWindow;
    private D4sRecord d4sRecord;
    private D4sRemoveData d4sRemoveData;
    private ScrollViewWithListener2 d4sViewLayout;
    private SimpleDateFormat dateFormat;
    private int day;
    private int deepEnergySavingStatus;
    private int deletePosition;
    private NormalCenterTipWindow deleteRecordWindow;
    private Disposable disposable;
    ValueAnimator expandValueAnimator;
    private TextView feederInlet;
    private FrameLayout flTopPanel;
    private Guide guide1;
    private Guide guide2;
    private Guide guide3;
    private Handler handler;
    private HorizontalScrollView hsvChart;
    private boolean isAniStart;
    boolean isRunning;
    private boolean isShowGuide;
    private boolean isUnMask;
    boolean isUp;
    private ImageView ivAccessoryConsumables;
    private ImageView ivCalendarIcon;
    private ImageView ivDeepEnergySavingRunning;
    private ImageView ivLeftArrow;
    private ImageView ivLeftArrows;
    private ImageView ivRightArrow;
    private ImageView ivUpArrow;
    private ImageView ivWarnText;
    int lastValue;
    private long leftTime;
    private LinearLayout llAccessoryConsumables;
    private LinearLayout llBottomMenuParentView;
    private LinearLayout llChartPanel;
    private LinearLayout llD4ViewBtnPanel;
    private LinearLayout llHistoryRecordPanel;
    private LinearLayout llManualFeedNum1;
    private LinearLayout llManualFeedNum2;
    private LinearLayout llRecordTitleBg;
    private LinearLayout llWarnPanel;
    private LinearLayout llWarnText;
    private LinearLayout llWorkRecord;
    private Context mContext;
    private float mSlideOffset;
    private VelocityTracker mVelocityTracker;
    private MainHandler mainHandler;
    private int measuredWidth;
    private MenuOnClickListener menuOnClickListener;
    private int offSet;
    private OnHistogramPageChange onHistogramPageChange;
    private D4sHomeProgressView pbBucket1;
    private D4sHomeProgressView pbBucket2;
    private PromoteView promoteView;
    private int recordType;
    ValueAnimator reduceValueAnimator;
    private RelativeLayout rlArc;
    private RelativeLayout rlBtnAndWarnPanel;
    private RelativeLayout rlDailyData;
    private RelativeLayout rlData1;
    private RelativeLayout rlData2;
    private RelativeLayout rlDeepEnergySaving;
    private RelativeLayout rlDeepEnergySavingInit;
    private RelativeLayout rlDeepEnergySavingRunning;
    private RelativeLayout rlPopStatisticsBg;
    private RelativeLayout rlRightTopWindow;
    private RelativeLayout rlTimePanel;
    private RelativeLayout rlTitleStatus;
    private RelativeLayout rlTopView;
    private RelativeLayout rlViewD4DeviceCenter;
    private ScrollRecyclerView rvD4RecordView;
    private RelativeLayout scrollView;
    private float tempY;
    private TextView tvAccessoryConsumables;
    private TextView tvBigArcCenterText;
    private TextView tvBucket1;
    private TextView tvBucket2;
    private TextView tvChartEmpty;
    private TextView tvD4BottomLeft;
    private TextView tvD4BottomPlan;
    private TextView tvD4PlanDes;
    private TextView tvD4PlanFeed;
    private TextView tvD4RealFeed;
    private TextView tvDeepEnergySavingInitCancle;
    private TextView tvDeepEnergySavingInitTime;
    private TextView tvHasOut1;
    private TextView tvHasOut2;
    private TextView tvHistoryMore;
    private TextView tvHistoryRecord;
    private TextView tvHistoryStatisticDate;
    private TextView tvLittleWarnText;
    private TextView tvManualFeedNum1;
    private TextView tvManualFeedNum2;
    private TextView tvPlanAmount1;
    private TextView tvPlanAmount2;
    private TextView tvPlanHasFinishedNum1;
    private TextView tvPlanHasFinishedNum2;
    private TextView tvPlanPrompt;
    private TextView tvPopContent;
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
    private VerticalScrollView verticalScrollView;
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
        BUCKET_NAME_1,
        BUCKET_NAME_2,
        REMOVE_RECORD,
        DEEP_ENERGY_SAVING,
        DEEP_ENERGY_SAVING_CANCLE,
        WARN_LIST,
        NO_MORE_REMIND,
        DATE_PICKER
    }

    public interface OnHistogramPageChange {
        void pageChange(int i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initCenterWindowParams(int i) {
    }

    @Override // com.petkit.android.activities.petkitBleDevice.d3.widget.BarChartView.ChartOnClickListener
    public void onChartClick(int i) {
    }

    @Override // com.petkit.android.activities.petkitBleDevice.d4s.widget.D4sHistogram.ChartOnClickListener
    public void onChartClick(int i, int i2, int i3) {
    }

    @Override // com.petkit.android.activities.petkitBleDevice.d4s.widget.D4sHistogram.ChartOnClickListener
    public void onChartClick(int i, List<RectF> list, D4sStatistic d4sStatistic) {
    }

    @Override // com.petkit.android.activities.petkitBleDevice.widget.PetkitSlidingUpPanelLayout.PanelSlideListener
    public void onPanelStateChanged(View view, PetkitSlidingUpPanelLayout.PanelState panelState, PetkitSlidingUpPanelLayout.PanelState panelState2) {
    }

    @Override // com.petkit.android.activities.petkitBleDevice.d4s.adapter.D4sHomeRecordAdapter.OnClickListener
    public void onViewClick(ItemsBean itemsBean, int i) {
    }

    public void setFeedingAmount(int i) {
    }

    @RequiresApi(api = 23)
    public D4sHomeDeviceView(Context context) {
        super(context);
        this.isShowGuide = false;
        this.guide1 = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtil.DATE_FORMAT_7);
        this.dateFormat = simpleDateFormat;
        this.recordType = 3;
        this.day = Integer.parseInt(simpleDateFormat.format(Calendar.getInstance().getTime()));
        this.d4sRemoveData = new D4sRemoveData();
        this.lastValue = 0;
        this.mContext = context;
        this.activity = (Activity) context;
        initView();
    }

    @RequiresApi(api = 23)
    public D4sHomeDeviceView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.isShowGuide = false;
        this.guide1 = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtil.DATE_FORMAT_7);
        this.dateFormat = simpleDateFormat;
        this.recordType = 3;
        this.day = Integer.parseInt(simpleDateFormat.format(Calendar.getInstance().getTime()));
        this.d4sRemoveData = new D4sRemoveData();
        this.lastValue = 0;
        this.mContext = context;
        this.activity = (Activity) context;
        initView();
    }

    @RequiresApi(api = 23)
    public D4sHomeDeviceView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.isShowGuide = false;
        this.guide1 = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtil.DATE_FORMAT_7);
        this.dateFormat = simpleDateFormat;
        this.recordType = 3;
        this.day = Integer.parseInt(simpleDateFormat.format(Calendar.getInstance().getTime()));
        this.d4sRemoveData = new D4sRemoveData();
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
        this.handler = new Handler(Looper.getMainLooper());
        View viewInflate = LayoutInflater.from(this.mContext).inflate(R.layout.layout_d4s_home_device_view, (ViewGroup) null);
        this.contentView = viewInflate;
        addView(viewInflate, -1, -1);
        this.llRecordTitleBg = (LinearLayout) this.contentView.findViewById(R.id.ll_record_title_bg);
        this.ivCalendarIcon = (ImageView) this.contentView.findViewById(R.id.iv_calendar_icon);
        this.ivAccessoryConsumables = (ImageView) this.contentView.findViewById(R.id.iv_accessory_consumables);
        this.tvAccessoryConsumables = (TextView) this.contentView.findViewById(R.id.tv_accessory_consumables);
        this.llAccessoryConsumables = (LinearLayout) this.contentView.findViewById(R.id.ll_accessory_consumables);
        this.scrollView = (RelativeLayout) this.contentView.findViewById(R.id.rl_d4s_view_layout);
        this.tvBigArcCenterText = (TextView) this.contentView.findViewById(R.id.tv_big_arc_center_text);
        this.tvSmallArcCenterText = (TextView) this.contentView.findViewById(R.id.tv_small_arc_center_text);
        this.tvPlanAmount1 = (TextView) this.contentView.findViewById(R.id.tv_plan_amount1);
        this.tvPlanAmount2 = (TextView) this.contentView.findViewById(R.id.tv_plan_amount2);
        this.tvBucket1 = (TextView) this.contentView.findViewById(R.id.tv_bucket1);
        this.tvBucket2 = (TextView) this.contentView.findViewById(R.id.tv_bucket2);
        D4sHomeProgressView d4sHomeProgressView = (D4sHomeProgressView) this.contentView.findViewById(R.id.pb_bucket1);
        this.pbBucket1 = d4sHomeProgressView;
        d4sHomeProgressView.setProgressColor(getResources().getColor(R.color.d3_main_green), getResources().getColor(R.color.d4s_progress_gray));
        D4sHomeProgressView d4sHomeProgressView2 = (D4sHomeProgressView) this.contentView.findViewById(R.id.pb_bucket2);
        this.pbBucket2 = d4sHomeProgressView2;
        d4sHomeProgressView2.setProgressColor(getResources().getColor(R.color.d4_main_yellow), getResources().getColor(R.color.d4s_progress_gray));
        this.rlArc = (RelativeLayout) this.contentView.findViewById(R.id.rl_arc);
        RelativeLayout relativeLayout = (RelativeLayout) this.contentView.findViewById(R.id.rl_pop_statistics_bg);
        this.rlPopStatisticsBg = relativeLayout;
        this.ivLeftArrows = (ImageView) relativeLayout.findViewById(R.id.iv_left_arrow);
        this.ivRightArrow = (ImageView) this.rlPopStatisticsBg.findViewById(R.id.iv_right_arrow);
        this.tvPopContent = (TextView) this.rlPopStatisticsBg.findViewById(R.id.tv_pop_content);
        this.rlTitleStatus = (RelativeLayout) this.contentView.findViewById(R.id.rl_title_status);
        this.tvTitleStatus = (TextView) this.contentView.findViewById(R.id.tv_title_status);
        this.rlDeepEnergySavingRunning = (RelativeLayout) this.contentView.findViewById(R.id.rl_deep_energy_saving_running);
        this.rlDeepEnergySaving = (RelativeLayout) this.contentView.findViewById(R.id.rl_deep_energy_saving);
        this.ivDeepEnergySavingRunning = (ImageView) this.contentView.findViewById(R.id.iv_deep_energy_saving_running);
        this.rlDeepEnergySavingInit = (RelativeLayout) this.contentView.findViewById(R.id.rl_deep_energy_saving_init);
        this.tvDeepEnergySavingInitTime = (TextView) this.contentView.findViewById(R.id.tv_deep_energy_saving_init_time);
        this.tvDeepEnergySavingInitCancle = (TextView) this.contentView.findViewById(R.id.tv_deep_energy_saving_init_cancle);
        this.tvManualFeedNum1 = (TextView) this.contentView.findViewById(R.id.tv_manual_feed_num1);
        this.tvManualFeedNum2 = (TextView) this.contentView.findViewById(R.id.tv_manual_feed_num2);
        this.tvPlanHasFinishedNum1 = (TextView) this.contentView.findViewById(R.id.tv_plan_has_been_finished_num1);
        this.tvPlanHasFinishedNum2 = (TextView) this.contentView.findViewById(R.id.tv_plan_has_been_finished_num2);
        this.rlData1 = (RelativeLayout) this.contentView.findViewById(R.id.rl_data1);
        this.rlData2 = (RelativeLayout) this.contentView.findViewById(R.id.rl_data2);
        this.tvHasOut1 = (TextView) this.contentView.findViewById(R.id.tv_has_out1);
        this.tvHasOut2 = (TextView) this.contentView.findViewById(R.id.tv_has_out2);
        this.llWorkRecord = (LinearLayout) this.contentView.findViewById(R.id.ll_work_record);
        this.d4sViewLayout = (ScrollViewWithListener2) this.contentView.findViewById(R.id.d4s_view_layout);
        this.llManualFeedNum1 = (LinearLayout) this.contentView.findViewById(R.id.ll_manual_feed_num1);
        this.llManualFeedNum2 = (LinearLayout) this.contentView.findViewById(R.id.ll_manual_feed_num2);
        this.llChartPanel = (LinearLayout) this.contentView.findViewById(R.id.ll_chart_panel);
        this.llD4ViewBtnPanel = (LinearLayout) this.contentView.findViewById(R.id.ll_d4_view_btn_panel);
        this.llWarnPanel = (LinearLayout) this.contentView.findViewById(R.id.ll_warn_panel);
        this.rlViewD4DeviceCenter = (RelativeLayout) this.contentView.findViewById(R.id.rl_view_d4_device_center);
        this.viewCenter = this.contentView.findViewById(R.id.view_center);
        this.tvD4BottomLeft = (TextView) this.contentView.findViewById(R.id.tv_d4_bottom_left);
        this.tvD4BottomPlan = (TextView) this.contentView.findViewById(R.id.tv_d4_bottom_plan);
        this.tvD4PlanDes = (TextView) this.contentView.findViewById(R.id.tv_d4_plan_des);
        this.tvWarnText = (TextView) this.contentView.findViewById(R.id.tv_warn_text);
        this.llWarnText = (LinearLayout) this.contentView.findViewById(R.id.ll_warn_text);
        this.ivWarnText = (ImageView) this.contentView.findViewById(R.id.iv_warn_text);
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
        this.rlDailyData = (RelativeLayout) this.contentView.findViewById(R.id.rl_daily_data);
        this.promoteView = (PromoteView) this.contentView.findViewById(R.id.promoteView);
        this.feederInlet = (TextView) this.contentView.findViewById(R.id.feeder_inlet);
        D4sHistogram d4sHistogram = (D4sHistogram) this.contentView.findViewById(R.id.d4_histogram);
        this.d4Histogram = d4sHistogram;
        d4sHistogram.setOnPageChanged(new D4sHistogram.OnPageChanged() { // from class: com.petkit.android.activities.petkitBleDevice.d4s.widget.D4sHomeDeviceView.1
            @Override // com.petkit.android.activities.petkitBleDevice.d4s.widget.D4sHistogram.OnPageChanged
            public void change(int i) {
                if (i != D4sHomeDeviceView.this.offSet && i != -1) {
                    D4sHomeDeviceView.this.stopLeftAni();
                }
                D4sHomeDeviceView.this.offSet = i;
                D4sHomeDeviceView.this.onHistogramPageChange.pageChange(i);
            }
        });
        this.d4Histogram.setChartOnClickListener(this);
        this.tvHistoryMore = (TextView) this.contentView.findViewById(R.id.tv_history_more);
        TextView textView = (TextView) this.contentView.findViewById(R.id.tv_history_statistic_date);
        this.tvHistoryStatisticDate = textView;
        textView.setOnTouchListener(new View.OnTouchListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4s.widget.D4sHomeDeviceView.2
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == 1 && D4sHomeDeviceView.this.tvHistoryStatisticDate.getCompoundDrawables()[2] != null && motionEvent.getX() > D4sHomeDeviceView.this.tvHistoryStatisticDate.getWidth() - D4sHomeDeviceView.this.tvHistoryStatisticDate.getCompoundDrawables()[2].getBounds().width()) {
                    D4sHomeDeviceView.this.menuOnClickListener.onBottomMenuClick(MenuType.DATE_PICKER);
                }
                return true;
            }
        });
        this.tvHistoryRecord = (TextView) this.contentView.findViewById(R.id.tv_history_record);
        this.ivLeftArrow = (ImageView) this.contentView.findViewById(R.id.iv_left_arrow);
        this.tvD4RealFeed = (TextView) this.contentView.findViewById(R.id.tv_d4_real_feed);
        this.tvD4PlanFeed = (TextView) this.contentView.findViewById(R.id.tv_d4_plan_feed);
        this.bleMenuView.setDeviceType(20, 0);
        this.bleMenuView.setOnMenuClickListener(this);
        this.barChartView.setChartOnClickListener(this);
        this.tvStatisticsMonth.setOnClickListener(this);
        this.tvStatisticsDate.setOnClickListener(this);
        this.tvStatisticsWeek.setOnClickListener(this);
        this.tvWarnText.setOnClickListener(this);
        this.ivCalendarIcon.setOnClickListener(this);
        this.tvLittleWarnText.setOnClickListener(this);
        this.tvHistoryMore.setOnClickListener(this);
        this.feederInlet.setOnClickListener(this);
        this.tvSmallArcCenterText.setOnClickListener(this);
        this.tvBigArcCenterText.setOnClickListener(this);
        this.llAccessoryConsumables.setOnClickListener(this);
        this.tvAccessoryConsumables.setOnClickListener(this);
        this.ivAccessoryConsumables.setOnClickListener(this);
        this.rlDeepEnergySavingRunning.setOnClickListener(this);
        this.tvDeepEnergySavingInitCancle.setOnClickListener(this);
        this.rlTitleStatus.setOnClickListener(this);
        this.d4sViewLayout.setScrollviewOnTouchListener(new ScrollViewWithListener2.ScrollviewOnTouchListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4s.widget.D4sHomeDeviceView.3
            @Override // com.petkit.android.activities.petkitBleDevice.widget.ScrollViewWithListener2.ScrollviewOnTouchListener
            public void finishScroll(boolean z) {
            }

            @Override // com.petkit.android.activities.petkitBleDevice.widget.ScrollViewWithListener2.ScrollviewOnTouchListener
            public void onScrolling(int i, int i2, int i3, int i4) {
                if (i2 - i4 > 5) {
                    D4sHomeDeviceView d4sHomeDeviceView = D4sHomeDeviceView.this;
                    d4sHomeDeviceView.isUp = true;
                    if (!d4sHomeDeviceView.isAniStart && D4sHomeDeviceView.this.bleMenuView.getVisibility() == 0) {
                        Animation animationLoadAnimation = AnimationUtils.loadAnimation(D4sHomeDeviceView.this.activity, R.anim.ble_menu_slide_out_to_bottom);
                        animationLoadAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4s.widget.D4sHomeDeviceView.3.1
                            @Override // android.view.animation.Animation.AnimationListener
                            public void onAnimationRepeat(Animation animation) {
                            }

                            @Override // android.view.animation.Animation.AnimationListener
                            public void onAnimationStart(Animation animation) {
                            }

                            @Override // android.view.animation.Animation.AnimationListener
                            public void onAnimationEnd(Animation animation) {
                                D4sHomeDeviceView.this.isAniStart = false;
                                D4sHomeDeviceView.this.bleMenuView.setVisibility(8);
                            }
                        });
                        D4sHomeDeviceView.this.bleMenuView.startAnimation(animationLoadAnimation);
                        D4sHomeDeviceView.this.isAniStart = true;
                    }
                } else if (i4 - i2 > 5) {
                    D4sHomeDeviceView d4sHomeDeviceView2 = D4sHomeDeviceView.this;
                    d4sHomeDeviceView2.isUp = false;
                    if (!d4sHomeDeviceView2.isAniStart && D4sHomeDeviceView.this.bleMenuView.getVisibility() == 8) {
                        Animation animationLoadAnimation2 = AnimationUtils.loadAnimation(D4sHomeDeviceView.this.activity, R.anim.panel_slide_in_from_bottom);
                        animationLoadAnimation2.setAnimationListener(new Animation.AnimationListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4s.widget.D4sHomeDeviceView.3.2
                            @Override // android.view.animation.Animation.AnimationListener
                            public void onAnimationRepeat(Animation animation) {
                            }

                            @Override // android.view.animation.Animation.AnimationListener
                            public void onAnimationStart(Animation animation) {
                            }

                            @Override // android.view.animation.Animation.AnimationListener
                            public void onAnimationEnd(Animation animation) {
                                D4sHomeDeviceView.this.isAniStart = false;
                                D4sHomeDeviceView.this.bleMenuView.setVisibility(0);
                            }
                        });
                        D4sHomeDeviceView.this.bleMenuView.startAnimation(animationLoadAnimation2);
                        D4sHomeDeviceView.this.isAniStart = true;
                    }
                }
                if ((((((D4sHomeDeviceView.this.llChartPanel.getHeight() + D4sHomeDeviceView.this.llChartPanel.getTop()) + D4sHomeDeviceView.this.llHistoryRecordPanel.getTop()) + D4sHomeDeviceView.this.scrollView.getTop()) - D4sHomeDeviceView.this.d4sViewLayout.getScrollY()) + ArmsUtils.dip2px(D4sHomeDeviceView.this.getContext(), 50.0f)) - (D4sHomeDeviceView.this.bleMenuView.getTop() - D4sHomeDeviceView.this.contentView.getTop()) > 0 || DataHelper.getBooleanSF(D4sHomeDeviceView.this.mContext, Consts.D4S_EAT_RECORD_IS_FIRST) || D4sHomeDeviceView.this.llWorkRecord.getVisibility() == 8 || D4sHomeDeviceView.this.isShowGuide) {
                    return;
                }
                D4sHomeDeviceView.this.d4sViewLayout.fling(0);
                D4sHomeDeviceView.this.d4sViewLayout.setOnTouchListener(new View.OnTouchListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4s.widget.D4sHomeDeviceView.3.3
                    @Override // android.view.View.OnTouchListener
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        return true;
                    }
                });
                D4sHomeDeviceView.this.isShowGuide = true;
                D4sHomeDeviceView d4sHomeDeviceView3 = D4sHomeDeviceView.this;
                d4sHomeDeviceView3.showGuideView3(d4sHomeDeviceView3.llChartPanel);
            }
        });
        this.activity.getWindow().getDecorView().setSystemUiVisibility(0);
        initViewSizeAndPosition();
        startProductAni();
    }

    private void initViewSizeAndPosition() {
        this.hsvChart.post(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.d4s.widget.D4sHomeDeviceView.4
            @Override // java.lang.Runnable
            public void run() {
                D4sHomeDeviceView.this.hsvChart.scrollTo(D4sHomeDeviceView.this.barChartView.getWidth(), 0);
            }
        });
        this.llD4ViewBtnPanel.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4s.widget.D4sHomeDeviceView.5
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                D4sHomeDeviceView.this.llD4ViewBtnPanel.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                D4sHomeDeviceView.this.mainHandler.postDelayed(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.d4s.widget.D4sHomeDeviceView.5.1
                    @Override // java.lang.Runnable
                    public void run() {
                        D4sHomeDeviceView.this.flTopPanel.getHeight();
                        D4sHomeDeviceView.this.contentView.getHeight();
                    }
                }, 200L);
                D4sHomeDeviceView d4sHomeDeviceView = D4sHomeDeviceView.this;
                d4sHomeDeviceView.initCenterWindowParams(ArmsUtils.dip2px(d4sHomeDeviceView.mContext, 30.0f));
            }
        });
        this.rlTopView.setOnTouchListener(new View.OnTouchListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4s.widget.D4sHomeDeviceView.6
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                D4sHomeDeviceView.this.mVelocityTracker.addMovement(motionEvent);
                int action = motionEvent.getAction();
                if (action == 0) {
                    D4sHomeDeviceView.this.tempY = motionEvent.getY();
                    PetkitLog.d("resOffset", "ACTION_DOWN:tempY:" + D4sHomeDeviceView.this.tempY);
                } else if (action == 1) {
                    PetkitLog.d("resOffset", "ACTION_UP");
                    D4sHomeDeviceView.this.mVelocityTracker.computeCurrentVelocity(1000);
                    PetkitLog.d("resOffset", "xVelocity:" + D4sHomeDeviceView.this.mVelocityTracker.getXVelocity() + " yVelocity:" + D4sHomeDeviceView.this.mVelocityTracker.getYVelocity());
                } else if (action == 2) {
                    float y = motionEvent.getY();
                    float unused = D4sHomeDeviceView.this.tempY;
                    D4sHomeDeviceView.this.tempY = y;
                }
                return true;
            }
        });
    }

    public void refreshPlanView(DifferentFeedPlan differentFeedPlan) {
        Calendar.getInstance().get(7);
        if (differentFeedPlan != null) {
            for (int i = 0; i < 7; i++) {
                if (differentFeedPlan.getFeedDailyList().get(i).getSuspended() == 0) {
                    this.bleMenuView.setIsFeederPlanIsOpen(true);
                    this.bleMenuView.setDeviceType(20, 0);
                    this.pbBucket1.setVisibility(0);
                    this.pbBucket2.setVisibility(0);
                    this.tvPlanAmount1.setVisibility(0);
                    this.tvPlanAmount2.setVisibility(0);
                    this.tvPlanPrompt.setVisibility(8);
                    return;
                }
            }
            this.bleMenuView.setIsFeederPlanIsOpen(false);
            this.bleMenuView.setDeviceType(20, 0);
            this.pbBucket1.setVisibility(4);
            this.pbBucket2.setVisibility(4);
            this.tvPlanAmount1.setVisibility(4);
            this.tvPlanAmount2.setVisibility(4);
            this.tvPlanPrompt.setVisibility(0);
        }
    }

    @SuppressLint({"StringFormatInvalid"})
    public void setD4Record(D4sRecord d4sRecord) {
        this.d4sRecord = d4sRecord;
        this.tvHistoryMore.setVisibility(d4sRecord.getDeviceShared() == null ? 0 : 4);
        if (this.d4HomeRecordAdapter == null) {
            D4sHomeRecordAdapter d4sHomeRecordAdapter = new D4sHomeRecordAdapter(this.mContext, d4sRecord.getDeviceId(), this);
            this.d4HomeRecordAdapter = d4sHomeRecordAdapter;
            this.rvD4RecordView.setAdapter(d4sHomeRecordAdapter);
        }
        this.llAccessoryConsumables.setVisibility(TextUtils.isEmpty(BleDeviceUtils.getPurchaseEntranceConsumaUrl(20, new int[0])) ? 8 : 0);
        checkDeepEnergySavingStatus();
        checkEatRecordState();
        this.ivWarnText.setVisibility(8);
        if (d4sRecord.getState().getPim() == 0) {
            this.feederInlet.setVisibility(0);
            if (d4sRecord.getState().getDesiccantLeftDays() > 0) {
                this.feederInlet.setBackground(getResources().getDrawable(R.drawable.shape_feeder_desiccant_orange_bg));
                TextView textView = this.feederInlet;
                StringBuilder sb = new StringBuilder();
                sb.append(getResources().getString(R.string.Desiccant_left));
                sb.append(":");
                sb.append(String.valueOf(d4sRecord.getState().getDesiccantLeftDays() >= 0 ? d4sRecord.getState().getDesiccantLeftDays() : 0));
                sb.append(getResources().getString(d4sRecord.getState().getDesiccantLeftDays() > 1 ? R.string.Unit_days : R.string.Unit_day));
                textView.setText(sb.toString());
                this.feederInlet.setTextColor(getResources().getColor(R.color.light_black));
            } else {
                this.feederInlet.setBackground(getResources().getDrawable(R.drawable.shape_feeder_desiccant_red_bg));
                TextView textView2 = this.feederInlet;
                StringBuilder sb2 = new StringBuilder();
                sb2.append(getResources().getString(R.string.Desiccant_left));
                sb2.append(":");
                sb2.append(String.valueOf(d4sRecord.getState().getDesiccantLeftDays() >= 0 ? d4sRecord.getState().getDesiccantLeftDays() : 0));
                sb2.append(getResources().getString(d4sRecord.getState().getDesiccantLeftDays() > 1 ? R.string.Unit_days : R.string.Unit_day));
                textView2.setText(sb2.toString());
                this.feederInlet.setTextColor(getResources().getColor(R.color.red));
            }
            this.isUnMask = false;
            this.bleMenuView.changeAllMask(true);
            this.tvD4BottomLeft.setText("--");
            this.tvD4BottomLeft.setTextColor(this.mContext.getResources().getColor(R.color.common_text));
            this.tvD4BottomPlan.setText("--");
            this.tvD4BottomPlan.setTextColor(this.mContext.getResources().getColor(R.color.common_text));
            this.llWarnText.setVisibility(0);
            this.tvWarnText.setText(this.mContext.getResources().getString(R.string.Device_off_line_tip));
            this.tvWarnText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.warn_red_little_icon, 0, R.drawable.arrow_right2, 0);
        } else {
            if (d4sRecord.getState().getPim() == 2) {
                this.bleMenuView.setIsD4sBatteryMode(true);
                this.bleMenuView.setDeviceType(20, 0);
            } else {
                this.bleMenuView.setIsD4sBatteryMode(false);
                this.bleMenuView.setDeviceType(20, 0);
            }
            this.feederInlet.setVisibility(0);
            if (d4sRecord.getState().getDesiccantLeftDays() > 0) {
                this.feederInlet.setBackground(getResources().getDrawable(R.drawable.shape_feeder_desiccant_orange_bg));
                TextView textView3 = this.feederInlet;
                StringBuilder sb3 = new StringBuilder();
                sb3.append(getResources().getString(R.string.Desiccant_left));
                sb3.append(":");
                sb3.append(String.valueOf(d4sRecord.getState().getDesiccantLeftDays() >= 0 ? d4sRecord.getState().getDesiccantLeftDays() : 0));
                sb3.append(getResources().getString(d4sRecord.getState().getDesiccantLeftDays() > 1 ? R.string.Unit_days : R.string.Unit_day));
                textView3.setText(sb3.toString());
                this.feederInlet.setTextColor(getResources().getColor(R.color.light_black));
            } else {
                this.feederInlet.setBackground(getResources().getDrawable(R.drawable.shape_feeder_desiccant_red_bg));
                TextView textView4 = this.feederInlet;
                StringBuilder sb4 = new StringBuilder();
                sb4.append(getResources().getString(R.string.Desiccant_left));
                sb4.append(":");
                sb4.append(String.valueOf(d4sRecord.getState().getDesiccantLeftDays() >= 0 ? d4sRecord.getState().getDesiccantLeftDays() : 0));
                sb4.append(getResources().getString(d4sRecord.getState().getDesiccantLeftDays() > 1 ? R.string.Unit_days : R.string.Unit_day));
                textView4.setText(sb4.toString());
                this.feederInlet.setTextColor(getResources().getColor(R.color.red));
            }
            this.llWarnText.setVisibility(8);
            this.isUnMask = true;
            this.bleMenuView.changeAllMask(false);
            this.tvD4BottomLeft.setTextColor(this.mContext.getResources().getColor(R.color.common_text));
            this.tvD4BottomPlan.setTextColor(this.mContext.getResources().getColor(R.color.common_text));
            if (!TextUtils.isEmpty(d4sRecord.getState().getErrorCode()) && d4sRecord.getState().getErrorLevel() == 1) {
                this.llWarnText.setVisibility(0);
                this.tvWarnText.setText(d4sRecord.getState().getErrorMsg());
                this.tvWarnText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.warn_red_little_icon, 0, R.drawable.arrow_right2, 0);
                this.isUnMask = false;
                this.bleMenuView.changeAllMask(true);
            } else if (d4sRecord.getState().getOta() == 1) {
                if (d4sRecord.getDeviceShared() != null) {
                    this.isUnMask = false;
                    this.bleMenuView.changeAllMask(true);
                    this.tvWarnText.setText(this.activity.getResources().getString(R.string.Device_ota_prompt));
                    this.tvWarnText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                } else {
                    this.tvWarnText.setText(this.activity.getResources().getString(R.string.Device_ota_prompt));
                    this.tvWarnText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                }
                this.llWarnText.setVisibility(0);
            } else if (!TextUtils.isEmpty(d4sRecord.getState().getErrorCode())) {
                this.llWarnText.setVisibility(0);
                this.tvWarnText.setText(d4sRecord.getState().getErrorMsg());
                this.tvWarnText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.warn_red_little_icon, 0, R.drawable.arrow_right2, 0);
            } else if (d4sRecord.getState().getEating() == 1) {
                this.llWarnText.setVisibility(0);
                this.tvWarnText.setText(this.activity.getResources().getString(R.string.D3_pet_eating_prompt));
                this.tvWarnText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            } else if (d4sRecord.getState().getFood1() == 0 || d4sRecord.getState().getFood2() == 0) {
                this.llWarnText.setVisibility(0);
                if (d4sRecord.getState().getFood1() == 0 && d4sRecord.getState().getFood2() == 0) {
                    this.tvWarnText.setText(this.activity.getResources().getString(R.string.D4s_buckets_lack_of_food_prompt));
                } else if (d4sRecord.getState().getFood1() == 0) {
                    SpannableString spannableString = new SpannableString(String.format(this.activity.getResources().getString(R.string.D4s_bucket1_lack_of_food_prompt), getResources().getString(R.string.Defalut_bucket1_name) + " "));
                    this.tvWarnText.setText(TextUtil.getInstance().makePartStringSpannableInTotalString(spannableString, String.format(this.activity.getResources().getString(R.string.D4s_bucket1_lack_of_food_prompt), getResources().getString(R.string.Defalut_bucket1_name) + " "), getResources().getString(R.string.Defalut_bucket1_name), getResources().getColor(R.color.d3_main_green), 16, true));
                } else if (d4sRecord.getState().getFood2() == 0) {
                    SpannableString spannableString2 = new SpannableString(String.format(this.activity.getResources().getString(R.string.D4s_bucket1_lack_of_food_prompt), getResources().getString(R.string.Defalut_bucket2_name) + " "));
                    this.tvWarnText.setText(TextUtil.getInstance().makePartStringSpannableInTotalString(spannableString2, String.format(this.activity.getResources().getString(R.string.D4s_bucket1_lack_of_food_prompt), getResources().getString(R.string.Defalut_bucket2_name) + " "), getResources().getString(R.string.Defalut_bucket2_name), getResources().getColor(R.color.d4s_orange_three), 16, true));
                }
                this.tvWarnText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_right2, 0);
                this.ivWarnText.setVisibility(0);
                this.ivWarnText.setImageDrawable(getResources().getDrawable(R.drawable.d4s_warn_anim));
                if (this.ivWarnText.getDrawable() instanceof AnimationDrawable) {
                    ((AnimationDrawable) this.ivWarnText.getDrawable()).start();
                }
            } else if (d4sRecord.getState().getBatteryStatus() == 2) {
                this.llWarnText.setVisibility(0);
                this.tvWarnText.setText(this.activity.getResources().getString(R.string.D3_low_power_prompt));
                this.tvWarnText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.warn_red_little_icon, 0, 0, 0);
            }
        }
        if (d4sRecord.getState().getFeedState() != null) {
            int size = d4sRecord.getState().getFeedState().getEatTimes() != null ? d4sRecord.getState().getFeedState().getEatTimes().size() : 0;
            String string = getResources().getString(size > 1 ? R.string.Unit_times : R.string.Unit_time);
            SpannableString spannableString3 = new SpannableString(size + string);
            spannableString3.setSpan(new RelativeSizeSpan(0.35f), spannableString3.toString().indexOf(string), spannableString3.toString().indexOf(string) + string.length(), 33);
            this.tvD4BottomLeft.setText(spannableString3);
            int eatAvg = d4sRecord.getState().getFeedState().getEatAvg();
            String string2 = getResources().getString(R.string.Unit_second_short);
            SpannableString spannableString4 = new SpannableString(eatAvg + string2);
            spannableString4.setSpan(new RelativeSizeSpan(0.35f), spannableString4.toString().indexOf(string2), spannableString4.toString().indexOf(string2) + string2.length(), 33);
            this.tvD4BottomPlan.setText(TimeUtils.getInstance().secondsToMinuteAndSecondsWithChangeSize(this.activity, (long) eatAvg, 0.35f));
        }
        setArcCenterView();
        this.d4TimeView.setTimeDataList(processFeedStateData());
        int i = this.recordType;
        if (i == 1) {
            this.tvStatisticsMonth.setSelected(true);
            this.tvStatisticsDate.setSelected(false);
            this.tvStatisticsWeek.setSelected(false);
            this.tvStatisticsMonth.setTextColor(-1);
            this.tvStatisticsWeek.setTextColor(getResources().getColor(R.color.feeder_plan_save_black));
            this.tvStatisticsDate.setTextColor(getResources().getColor(R.color.feeder_plan_save_black));
        } else if (i == 2) {
            this.tvStatisticsWeek.setSelected(true);
            this.tvStatisticsDate.setSelected(false);
            this.tvStatisticsMonth.setSelected(false);
            this.tvStatisticsWeek.setTextColor(-1);
            this.tvStatisticsMonth.setTextColor(getResources().getColor(R.color.feeder_plan_save_black));
            this.tvStatisticsDate.setTextColor(getResources().getColor(R.color.feeder_plan_save_black));
        } else if (i == 3) {
            this.tvStatisticsDate.setSelected(true);
            this.tvStatisticsWeek.setSelected(false);
            this.tvStatisticsMonth.setSelected(false);
            this.tvStatisticsDate.setTextColor(-1);
            this.tvStatisticsWeek.setTextColor(getResources().getColor(R.color.feeder_plan_save_black));
            this.tvStatisticsMonth.setTextColor(getResources().getColor(R.color.feeder_plan_save_black));
        }
        checkDeviceWarnState();
        if (d4sRecord.getState().getPim() != 0 && d4sRecord.getState().getFeeding() == 1) {
            this.bleMenuView.setFirstButtonText(getResources().getString(R.string.Stop_feed));
            this.bleMenuView.changeMenuBtnState(0);
            return;
        }
        if (d4sRecord.getState().getPim() != 0 && d4sRecord.getState().getFeeding() == 2) {
            this.bleMenuView.setFirstButtonText(getResources().getString(R.string.Stop_feed));
            this.bleMenuView.changeMenuBtnState(0);
        } else if (d4sRecord.getState().getPim() != 0 && d4sRecord.getState().getFeeding() == 3) {
            this.bleMenuView.setFirstButtonText(getResources().getString(R.string.Stop_feed));
            this.bleMenuView.changeMenuBtnState(0);
        } else {
            this.bleMenuView.setFirstButtonText(getResources().getString(R.string.Extra_feeding));
            this.bleMenuView.changeAllMenuBtnState(false);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:81:0x020f  */
    @Override // android.view.View.OnClickListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onClick(android.view.View r14) {
        /*
            Method dump skipped, instruction units count: 1124
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.petkit.android.activities.petkitBleDevice.d4s.widget.D4sHomeDeviceView.onClick(android.view.View):void");
    }

    public void openDeepEnergySavingIKnowWindow() {
        int iIndexOf = String.format(getResources().getString(R.string.Deep_energy_saving_tip_content), getResources().getString(R.string.Deep_energy_saving_tip_content1), getResources().getString(R.string.Deep_energy_saving_tip_content2)).indexOf(getResources().getString(R.string.Deep_energy_saving_tip_content1));
        int iIndexOf2 = String.format(getResources().getString(R.string.Deep_energy_saving_tip_content), getResources().getString(R.string.Deep_energy_saving_tip_content1), getResources().getString(R.string.Deep_energy_saving_tip_content2)).indexOf(getResources().getString(R.string.Deep_energy_saving_tip_content2));
        SpannableString spannableString = new SpannableString(String.format(getResources().getString(R.string.Deep_energy_saving_tip_content), getResources().getString(R.string.Deep_energy_saving_tip_content1), getResources().getString(R.string.Deep_energy_saving_tip_content2)));
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.manual_add_water_orange)), iIndexOf, getResources().getString(R.string.Deep_energy_saving_tip_content1).length() + iIndexOf, 33);
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.manual_add_water_orange)), iIndexOf2, getResources().getString(R.string.Deep_energy_saving_tip_content2).length() + iIndexOf2, 33);
        new NewIKnowWindow(this.activity, getResources().getString(R.string.Deep_energy_saving_tip_title), spannableString, (String) null).show(getRootView());
    }

    public void openBatteryModeIKnowWindow() {
        new NewIKnowWindow(this.activity, getResources().getString(R.string.Battery_mode_introduce), "\t\t\t\t" + getResources().getString(R.string.Battery_mode_introduce_detail_1) + "\t\t\t\t" + getResources().getString(R.string.Battery_mode_introduce_detail_2), (String) null).show(getRootView());
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x00eb  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0115  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void checkDeepEnergySavingStatus() {
        /*
            Method dump skipped, instruction units count: 458
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.petkit.android.activities.petkitBleDevice.d4s.widget.D4sHomeDeviceView.checkDeepEnergySavingStatus():void");
    }

    private void checkEatRecordState() {
        if (this.d4sRecord.getSettings().getEatRecord() == 0) {
            this.llD4ViewBtnPanel.setVisibility(8);
            this.llWorkRecord.setVisibility(8);
            this.ivCalendarIcon.setVisibility(0);
            this.llRecordTitleBg.setBackgroundResource(R.drawable.shape_d4_title_with_radius_bg);
            return;
        }
        this.ivCalendarIcon.setVisibility(8);
        this.llRecordTitleBg.setBackgroundResource(R.drawable.shape_d4_title_bg);
    }

    public void openDeepEnergySavingTimer(long j) {
        if (j < 0) {
            j = 0;
        }
        this.leftTime = j;
        if (this.disposable == null) {
            this.disposable = Observable.interval(1000L, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.petkit.android.activities.petkitBleDevice.d4s.widget.D4sHomeDeviceView$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f$0.lambda$openDeepEnergySavingTimer$0((Long) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$openDeepEnergySavingTimer$0(Long l) throws Exception {
        SpannableString spannableString = new SpannableString(getResources().getString(R.string.Deep_energy_saving_is_init, TimeUtils.getInstance().secondsToMinuteAndSeconds(this.leftTime)));
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.manual_add_water_orange)), spannableString.toString().indexOf(TimeUtils.getInstance().secondsToMinuteAndSeconds(this.leftTime)), spannableString.toString().indexOf(TimeUtils.getInstance().secondsToMinuteAndSeconds(this.leftTime)) + TimeUtils.getInstance().secondsToMinuteAndSeconds(this.leftTime).length(), 33);
        this.tvDeepEnergySavingInitTime.setText(spannableString);
        long j = this.leftTime - 1;
        this.leftTime = j;
        if (j < 0) {
            this.leftTime = 0L;
        }
    }

    public void closeDeepEnergySavingTimer() {
        Disposable disposable = this.disposable;
        if (disposable == null || disposable.isDisposed()) {
            return;
        }
        this.disposable.dispose();
        this.disposable = null;
    }

    public void refreshDateData(String str) {
        this.tvHistoryStatisticDate.setText(D4sUtils.getDateByStatisticTime(str, this.activity));
        refreshBarChart();
        refreshDateData(this.d4DailyFeeds);
    }

    public void refreshHistoryRecord(int i) {
        this.tvHistoryRecord.setText(String.format("%s%s%s", String.valueOf(this.d4DailyFeeds.getEat().get(0).getItems().size()), "zh_CN".equals(UserInforUtils.getLanguageLocaleString(CommonUtils.getAppContext())) ? "" : " ", this.mContext.getResources().getString(i > 1 ? R.string.Unit_times : R.string.Unit_time)));
    }

    private void refreshBarChart() {
        this.d4Histogram.updateBarChartData(0);
    }

    public void refreshDateData(D4sDailyFeeds d4sDailyFeeds) {
        this.d4DailyFeeds = d4sDailyFeeds;
        if (d4sDailyFeeds != null) {
            this.d4HomeRecordAdapter = new D4sHomeRecordAdapter(this.mContext, this.d4sRecord.getDeviceId(), this);
            this.d4HomeRecordAdapter.setD4DateFeedData(new D4sDateFeedData(d4sDailyFeeds.getFeed().get(0).getItems(), d4sDailyFeeds.getEat().get(0).getItems(), d4sDailyFeeds.getFeed().get(0).getDay()));
            this.rvD4RecordView.setAdapter(this.d4HomeRecordAdapter);
            refreshHistoryRecord(d4sDailyFeeds.getEat().get(0).getItems().size());
            this.rvD4RecordView.post(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.d4s.widget.D4sHomeDeviceView.8
                @Override // java.lang.Runnable
                public void run() {
                    if (D4sHomeDeviceView.this.rvD4RecordView.getMeasuredHeight() >= ArmsUtils.dip2px(D4sHomeDeviceView.this.mContext, 300.0f)) {
                        D4sHomeDeviceView.this.rvD4RecordView.setPadding(0, 0, 0, ArmsUtils.dip2px(D4sHomeDeviceView.this.mContext, 50.0f));
                    }
                }
            });
        }
    }

    public void setOnHistogramPageChange(OnHistogramPageChange onHistogramPageChange) {
        this.onHistogramPageChange = onHistogramPageChange;
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
            this.promoteView.setPromoteViewOnItemListener(new PromoteView.PromoteViewOnItemListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4s.widget.D4sHomeDeviceView.9
                @Override // com.petkit.android.activities.mall.widget.PromoteView.PromoteViewOnItemListener
                public void onItemClick(PromoteData promoteData) {
                    EventBus.getDefault().post(promoteData);
                }
            });
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.d4s.mode.D4sCenterViewCallBack
    public void initSizeWithParams(float f, int i, int i2, int i3) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.feederInlet.getLayoutParams();
        layoutParams.setMargins(Math.round(ArmsUtils.dip2px(this.mContext, 16.0f) * f), Math.round(ArmsUtils.dip2px(this.mContext, 13.0f) * f), layoutParams.rightMargin, layoutParams.bottomMargin);
        this.feederInlet.setLayoutParams(layoutParams);
    }

    private void setArcCenterView() {
        Resources resources;
        int i;
        Resources resources2;
        int i2;
        Resources resources3;
        int i3;
        Resources resources4;
        int i4;
        Resources resources5;
        int i5;
        Resources resources6;
        int i6;
        Resources resources7;
        int i7;
        Resources resources8;
        int i8;
        Resources resources9;
        int i9;
        Resources resources10;
        int i10;
        Resources resources11;
        int i11;
        Resources resources12;
        int i12;
        if (this.d4sRecord.getState().getPim() == 0) {
            this.tvBigArcCenterText.setTextColor(this.mContext.getResources().getColor(R.color.common_text));
            this.tvSmallArcCenterText.setTextColor(this.mContext.getResources().getColor(R.color.common_text));
        } else {
            this.tvBigArcCenterText.setTextColor(this.mContext.getResources().getColor(R.color.common_text));
            this.tvSmallArcCenterText.setTextColor(this.mContext.getResources().getColor(R.color.common_text));
        }
        if (this.d4sRecord.getState().getPim() != 0 && this.d4sRecord.getState().getFeeding() == 1) {
            this.pbBucket1.setOpenAnimation(true);
            this.pbBucket2.setOpenAnimation(false);
            this.tvSmallArcCenterText.setText(this.mContext.getResources().getString(R.string.Feeding));
            this.tvSmallArcCenterText.setTextSize(20.0f);
            this.tvBigArcCenterText.setTextSize(30.0f);
            String amountFormat = D4sUtils.getAmountFormat(this.d4sRecord.getState().getFeedState().getRealAmountTotal2(), this.d4sRecord.getSettings().getFactor2());
            StringBuilder sb = new StringBuilder();
            boolean zEqualsIgnoreCase = "zh_CN".equalsIgnoreCase(UserInforUtils.getLanguageLocaleString(CommonUtils.getAppContext()));
            if (Integer.parseInt(amountFormat) > 1) {
                resources12 = getResources();
                i12 = R.string.D4s_feeders_unit;
            } else {
                resources12 = getResources();
                i12 = R.string.D4s_feeder_unit;
            }
            String string = resources12.getString(i12);
            sb.append(amountFormat);
            sb.append(zEqualsIgnoreCase ? "" : " ");
            sb.append(string);
            SpannableString spannableString = new SpannableString(sb.toString());
            int length = amountFormat.length();
            int length2 = sb.length();
            spannableString.setSpan(new AbsoluteSizeSpan(42, true), 0, length, 33);
            spannableString.setSpan(new AbsoluteSizeSpan(12, true), length, length2, 33);
            this.tvBigArcCenterText.setText(spannableString);
            this.tvBucket1.setText("");
            if (this.d4sRecord.getState().getFeedState().getRealAmountTotal2() == 0) {
                this.tvBucket2.setText(String.format(getResources().getString(R.string.D4s_have_feeded), ""));
            } else {
                this.tvBucket2.setText(String.format(getResources().getString(R.string.D4s_have_feeded), ""));
            }
        } else if (this.d4sRecord.getState().getPim() != 0 && this.d4sRecord.getState().getFeeding() == 2) {
            this.pbBucket1.setOpenAnimation(false);
            this.pbBucket2.setOpenAnimation(true);
            this.tvBigArcCenterText.setText(this.mContext.getResources().getString(R.string.Feeding));
            this.tvBigArcCenterText.setTextSize(20.0f);
            String amountFormat2 = D4sUtils.getAmountFormat(this.d4sRecord.getState().getFeedState().getRealAmountTotal1(), this.d4sRecord.getSettings().getFactor1());
            StringBuilder sb2 = new StringBuilder();
            boolean zEqualsIgnoreCase2 = "zh_CN".equalsIgnoreCase(UserInforUtils.getLanguageLocaleString(CommonUtils.getAppContext()));
            if (Integer.parseInt(amountFormat2) > 1) {
                resources3 = getResources();
                i3 = R.string.D4s_feeders_unit;
            } else {
                resources3 = getResources();
                i3 = R.string.D4s_feeder_unit;
            }
            String string2 = resources3.getString(i3);
            sb2.append(amountFormat2);
            sb2.append(zEqualsIgnoreCase2 ? "" : " ");
            sb2.append(string2);
            SpannableString spannableString2 = new SpannableString(sb2.toString());
            int length3 = amountFormat2.length();
            int length4 = sb2.length();
            spannableString2.setSpan(new AbsoluteSizeSpan(42, true), 0, length3, 33);
            spannableString2.setSpan(new AbsoluteSizeSpan(12, true), length3, length4, 33);
            this.tvSmallArcCenterText.setText(spannableString2);
            this.tvBucket2.setText("");
            if (this.d4sRecord.getState().getFeedState().getRealAmountTotal1() == 0) {
                this.tvBucket1.setText(String.format(getResources().getString(R.string.D4s_have_feeded), ""));
            } else {
                this.tvBucket1.setText(String.format(getResources().getString(R.string.D4s_have_feeded), ""));
            }
        } else if (this.d4sRecord.getState().getPim() != 0 && this.d4sRecord.getState().getFeeding() == 3) {
            this.tvBigArcCenterText.setText(this.mContext.getResources().getString(R.string.Feeding));
            this.tvBigArcCenterText.setTextSize(20.0f);
            this.tvSmallArcCenterText.setText(this.mContext.getResources().getString(R.string.Feeding));
            this.tvSmallArcCenterText.setTextSize(20.0f);
            this.tvBucket2.setText("");
            this.tvBucket1.setText("");
            this.pbBucket1.setOpenAnimation(true);
            this.pbBucket2.setOpenAnimation(true);
        } else {
            this.pbBucket1.setOpenAnimation(false);
            this.pbBucket2.setOpenAnimation(false);
            this.tvBigArcCenterText.setTextSize(30.0f);
            String amountFormat3 = D4sUtils.getAmountFormat(this.d4sRecord.getState().getFeedState().getRealAmountTotal2(), this.d4sRecord.getSettings().getFactor2());
            StringBuilder sb3 = new StringBuilder();
            boolean zEqualsIgnoreCase3 = "zh_CN".equalsIgnoreCase(UserInforUtils.getLanguageLocaleString(CommonUtils.getAppContext()));
            if (Integer.parseInt(amountFormat3) > 1) {
                resources = getResources();
                i = R.string.D4s_feeders_unit;
            } else {
                resources = getResources();
                i = R.string.D4s_feeder_unit;
            }
            String string3 = resources.getString(i);
            sb3.append(amountFormat3);
            sb3.append(zEqualsIgnoreCase3 ? "" : " ");
            sb3.append(string3);
            SpannableString spannableString3 = new SpannableString(sb3.toString());
            int length5 = amountFormat3.length();
            int length6 = sb3.length();
            spannableString3.setSpan(new AbsoluteSizeSpan(42, true), 0, length5, 33);
            spannableString3.setSpan(new AbsoluteSizeSpan(12, true), length5, length6, 33);
            this.tvBigArcCenterText.setText(spannableString3);
            this.tvSmallArcCenterText.setTextSize(20.0f);
            String amountFormat4 = D4sUtils.getAmountFormat(this.d4sRecord.getState().getFeedState().getRealAmountTotal1(), this.d4sRecord.getSettings().getFactor1());
            StringBuilder sb4 = new StringBuilder();
            if (Integer.parseInt(amountFormat4) > 1) {
                resources2 = getResources();
                i2 = R.string.D4s_feeders_unit;
            } else {
                resources2 = getResources();
                i2 = R.string.D4s_feeder_unit;
            }
            String string4 = resources2.getString(i2);
            sb4.append(amountFormat4);
            sb4.append(zEqualsIgnoreCase3 ? "" : " ");
            sb4.append(string4);
            SpannableString spannableString4 = new SpannableString(sb4.toString());
            int length7 = amountFormat4.length();
            int length8 = sb4.length();
            spannableString4.setSpan(new AbsoluteSizeSpan(42, true), 0, length7, 33);
            spannableString4.setSpan(new AbsoluteSizeSpan(12, true), length7, length8, 33);
            this.tvSmallArcCenterText.setText(spannableString4);
            if (this.d4sRecord.getState().getFeedState().getRealAmountTotal1() == 0) {
                this.tvBucket1.setText(String.format(getResources().getString(R.string.D4s_have_feeded), ""));
            } else {
                this.tvBucket1.setText(String.format(getResources().getString(R.string.D4s_have_feeded), ""));
            }
            if (this.d4sRecord.getState().getFeedState().getRealAmountTotal2() == 0) {
                this.tvBucket2.setText(String.format(getResources().getString(R.string.D4s_have_feeded), ""));
            } else {
                this.tvBucket2.setText(String.format(getResources().getString(R.string.D4s_have_feeded), ""));
            }
        }
        String str = DeviceUtils.isZhCN(this.activity) ? "" : " ";
        TextView textView = this.tvManualFeedNum1;
        StringBuilder sb5 = new StringBuilder();
        sb5.append(this.d4sRecord.getState().getFeedState().getRealAmountTotal1() - this.d4sRecord.getState().getFeedState().getPlanRealAmountTotal1());
        if (this.d4sRecord.getState().getFeedState().getRealAmountTotal1() - this.d4sRecord.getState().getFeedState().getPlanRealAmountTotal1() > 1) {
            resources4 = getResources();
            i4 = R.string.D4s_feeders_unit;
        } else {
            resources4 = getResources();
            i4 = R.string.D4s_feeder_unit;
        }
        sb5.append(resources4.getString(i4));
        textView.setText(sb5.toString());
        TextView textView2 = this.tvManualFeedNum2;
        StringBuilder sb6 = new StringBuilder();
        sb6.append(this.d4sRecord.getState().getFeedState().getRealAmountTotal2() - this.d4sRecord.getState().getFeedState().getPlanRealAmountTotal2());
        if (this.d4sRecord.getState().getFeedState().getRealAmountTotal2() - this.d4sRecord.getState().getFeedState().getPlanRealAmountTotal2() > 1) {
            resources5 = getResources();
            i5 = R.string.D4s_feeders_unit;
        } else {
            resources5 = getResources();
            i5 = R.string.D4s_feeder_unit;
        }
        sb6.append(resources5.getString(i5));
        textView2.setText(sb6.toString());
        TextView textView3 = this.tvHasOut1;
        StringBuilder sb7 = new StringBuilder();
        sb7.append(getResources().getString(R.string.Feeder_amount_feeded));
        sb7.append(str);
        sb7.append(this.d4sRecord.getState().getFeedState().getRealAmountTotal1());
        sb7.append(str);
        if (this.d4sRecord.getState().getFeedState().getRealAmountTotal1() > 1) {
            resources6 = getResources();
            i6 = R.string.D4s_feeders_unit;
        } else {
            resources6 = getResources();
            i6 = R.string.D4s_feeder_unit;
        }
        sb7.append(resources6.getString(i6));
        textView3.setText(sb7.toString());
        TextView textView4 = this.tvHasOut2;
        StringBuilder sb8 = new StringBuilder();
        sb8.append(getResources().getString(R.string.Feeder_amount_feeded));
        sb8.append(str);
        sb8.append(this.d4sRecord.getState().getFeedState().getRealAmountTotal2());
        sb8.append(str);
        if (this.d4sRecord.getState().getFeedState().getRealAmountTotal2() > 1) {
            resources7 = getResources();
            i7 = R.string.D4s_feeders_unit;
        } else {
            resources7 = getResources();
            i7 = R.string.D4s_feeder_unit;
        }
        sb8.append(resources7.getString(i7));
        textView4.setText(sb8.toString());
        TextView textView5 = this.tvPlanHasFinishedNum1;
        StringBuilder sb9 = new StringBuilder();
        sb9.append(this.d4sRecord.getState().getFeedState().getPlanRealAmountTotal1());
        if (this.d4sRecord.getState().getFeedState().getPlanRealAmountTotal1() > 1) {
            resources8 = getResources();
            i8 = R.string.D4s_feeders_unit;
        } else {
            resources8 = getResources();
            i8 = R.string.D4s_feeder_unit;
        }
        sb9.append(resources8.getString(i8));
        textView5.setText(sb9.toString());
        TextView textView6 = this.tvPlanHasFinishedNum2;
        StringBuilder sb10 = new StringBuilder();
        sb10.append(this.d4sRecord.getState().getFeedState().getPlanRealAmountTotal2());
        if (this.d4sRecord.getState().getFeedState().getPlanRealAmountTotal2() > 1) {
            resources9 = getResources();
            i9 = R.string.D4s_feeders_unit;
        } else {
            resources9 = getResources();
            i9 = R.string.D4s_feeder_unit;
        }
        sb10.append(resources9.getString(i9));
        textView6.setText(sb10.toString());
        if (this.d4sRecord.getState().getFeedState().getRealAmountTotal1() - this.d4sRecord.getState().getFeedState().getPlanRealAmountTotal1() == 0) {
            this.llManualFeedNum1.setVisibility(8);
        } else {
            this.llManualFeedNum1.setVisibility(0);
        }
        if (this.d4sRecord.getState().getFeedState().getRealAmountTotal2() - this.d4sRecord.getState().getFeedState().getPlanRealAmountTotal2() == 0) {
            this.llManualFeedNum2.setVisibility(8);
        } else {
            this.llManualFeedNum2.setVisibility(0);
        }
        TextView textView7 = this.tvManualFeedNum1;
        StringBuilder sb11 = new StringBuilder();
        sb11.append(this.d4sRecord.getState().getFeedState().getRealAmountTotal1() - this.d4sRecord.getState().getFeedState().getPlanRealAmountTotal1());
        if (this.d4sRecord.getState().getFeedState().getRealAmountTotal1() - this.d4sRecord.getState().getFeedState().getPlanRealAmountTotal1() > 1) {
            resources10 = getResources();
            i10 = R.string.D4s_feeders_unit;
        } else {
            resources10 = getResources();
            i10 = R.string.D4s_feeder_unit;
        }
        sb11.append(resources10.getString(i10));
        textView7.setText(sb11.toString());
        TextView textView8 = this.tvManualFeedNum2;
        StringBuilder sb12 = new StringBuilder();
        sb12.append(this.d4sRecord.getState().getFeedState().getRealAmountTotal2() - this.d4sRecord.getState().getFeedState().getPlanRealAmountTotal2());
        if (this.d4sRecord.getState().getFeedState().getRealAmountTotal2() - this.d4sRecord.getState().getFeedState().getPlanRealAmountTotal2() > 1) {
            resources11 = getResources();
            i11 = R.string.D4s_feeders_unit;
        } else {
            resources11 = getResources();
            i11 = R.string.D4s_feeder_unit;
        }
        sb12.append(resources11.getString(i11));
        textView8.setText(sb12.toString());
        this.tvPlanAmount1.setText(String.format(getResources().getString(R.string.Plan_n_meals), D4sUtils.getAmountFormat(this.d4sRecord.getState().getFeedState().getPlanAmountTotal1(), this.d4sRecord.getSettings().getFactor1())));
        this.tvPlanAmount2.setText(String.format(getResources().getString(R.string.Plan_n_meals), D4sUtils.getAmountFormat(this.d4sRecord.getState().getFeedState().getPlanAmountTotal2(), this.d4sRecord.getSettings().getFactor2())));
        if (this.d4sRecord.getState().getFeedState().getPlanAmountTotal1() == 0) {
            if (this.d4sRecord.getState().getFeedState().getRealAmountTotal1() == 0) {
                this.pbBucket1.setPercent(0.0f);
            } else {
                this.pbBucket1.setPercent(100.0f);
            }
        } else {
            this.pbBucket1.setPercent((this.d4sRecord.getState().getFeedState().getRealAmountTotal1() * 100.0f) / this.d4sRecord.getState().getFeedState().getPlanAmountTotal1());
        }
        if (this.d4sRecord.getState().getFeedState().getPlanAmountTotal2() == 0) {
            if (this.d4sRecord.getState().getFeedState().getRealAmountTotal2() == 0) {
                this.pbBucket2.setPercent(0.0f);
            } else {
                this.pbBucket2.setPercent(100.0f);
            }
        } else {
            this.pbBucket2.setPercent((this.d4sRecord.getState().getFeedState().getRealAmountTotal2() * 100.0f) / this.d4sRecord.getState().getFeedState().getPlanAmountTotal2());
        }
        this.d4sRecord.getState().getFeedState().getRealAmountTotal2();
        this.d4sRecord.getState().getFeedState().getRealAmountTotal1();
        if (this.d4sRecord.getState().getPim() != 0) {
            this.d4sRecord.getState().getFeeding();
        }
    }

    public void removeRecord() {
        this.deleteRecordWindow.dismiss();
        if (this.d4HomeRecordAdapter != null) {
            this.onHistogramPageChange.pageChange(this.offSet);
            this.d4HomeRecordAdapter.removeItem(this.deletePosition);
        }
    }

    private void openDeleteRecordWindow() {
        this.deleteRecordWindow = new NormalCenterTipWindow(this.mContext, new NormalCenterTipWindow.OnClickOk() { // from class: com.petkit.android.activities.petkitBleDevice.d4s.widget.D4sHomeDeviceView.10
            @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
            public void onCancelClick() {
            }

            @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
            public void onOkClick() {
                EventBus.getDefault().post(D4sHomeDeviceView.this.d4sRemoveData);
            }
        }, (String) null, this.mContext.getResources().getString(R.string.D4s_delete_work_record_tip));
        if (this.d4sRemoveData.getDeleteType() == 1) {
            this.deleteRecordWindow.setTvTipGray(true, this.mContext.getResources().getString(R.string.D4s_delete_work_record_little_tip));
        } else {
            this.deleteRecordWindow.setTvTipGray(true, this.mContext.getResources().getString(R.string.D4s_delete_output_record_little_tip));
        }
        this.deleteRecordWindow.setSelectText(this.mContext.getResources().getString(R.string.Cancel), this.mContext.getResources().getString(R.string.Confirm));
        this.deleteRecordWindow.show(((D4sHomeActivity) this.mContext).getWindow().getDecorView());
    }

    public void showFoodWarnDialog() {
        ThreeChoicesWindow threeChoicesWindow = this.d4sFoodWarnWindow;
        if (threeChoicesWindow == null || !threeChoicesWindow.isShowing()) {
            int i = R.drawable.d4s_buckets_lack_of_food_icon;
            SpannableString spannableString = new SpannableString(this.activity.getResources().getString(R.string.D4s_buckets_lack_of_food_prompt));
            boolean z = this.d4sRecord.getState().getFood1() == 0;
            if (this.d4sRecord.getState().getFood2() != 0) {
                i = R.drawable.d4s_bucket1_lack_of_food_icon;
                String str = String.format(this.activity.getResources().getString(R.string.D4s_bucket1_lack_of_food_prompt), getResources().getString(R.string.Defalut_bucket1_name) + " ");
                spannableString = TextUtil.getInstance().makePartStringSpannableInTotalString(new SpannableString(str), str, this.mContext.getResources().getString(R.string.Defalut_bucket1_name), this.mContext.getResources().getColor(R.color.d3_main_green), 16, true);
            }
            if (!z) {
                i = R.drawable.d4s_bucket2_lack_of_food_icon;
                String str2 = String.format(this.activity.getResources().getString(R.string.D4s_bucket1_lack_of_food_prompt), getResources().getString(R.string.Defalut_bucket2_name) + " ");
                spannableString = TextUtil.getInstance().makePartStringSpannableInTotalString(new SpannableString(str2), str2, this.mContext.getResources().getString(R.string.Defalut_bucket2_name), this.mContext.getResources().getColor(R.color.d4s_orange_three), 16, true);
            }
            SpannableString spannableString2 = spannableString;
            String string = this.activity.getResources().getString(R.string.Surplus_grain_early_warning_tips);
            if (this.d4sRecord.getSettings().getFoodWarn() != 1 || (this.d4sRecord.getState() != null && this.d4sRecord.getState().getPim() == 2)) {
                string = "";
            }
            ThreeChoicesWindow threeChoicesWindow2 = new ThreeChoicesWindow(this.mContext, new ThreeChoicesWindow.OnClickThreeChoices() { // from class: com.petkit.android.activities.petkitBleDevice.d4s.widget.D4sHomeDeviceView.11
                @Override // com.petkit.android.activities.device.widget.ThreeChoicesWindow.OnClickThreeChoices
                public void onClickFirstChoice() {
                    D4sHomeDeviceView.this.d4sFoodWarnWindow.dismiss();
                }

                @Override // com.petkit.android.activities.device.widget.ThreeChoicesWindow.OnClickThreeChoices
                public void onClickSecondChoice() {
                    D4sHomeDeviceView.this.menuOnClickListener.onBottomMenuClick(MenuType.NO_MORE_REMIND);
                    D4sHomeDeviceView.this.d4sFoodWarnWindow.dismiss();
                }

                @Override // com.petkit.android.activities.device.widget.ThreeChoicesWindow.OnClickThreeChoices
                public void onClickThirdChoice() {
                    D4sHomeDeviceView.this.menuOnClickListener.onBottomMenuClick(MenuType.NO_MORE_REMIND);
                    D4sHomeDeviceView.this.d4sFoodWarnWindow.dismiss();
                }
            }, spannableString2, getResources().getString(R.string.I_know), getResources().getString(R.string.I_have_added_meal), getResources().getString(R.string.Not_remind));
            this.d4sFoodWarnWindow = threeChoicesWindow2;
            threeChoicesWindow2.setThreeChoicesTextColor(getResources().getColor(R.color.new_bind_blue), getResources().getColor(R.color.gray), getResources().getColor(R.color.gray));
            this.d4sFoodWarnWindow.setTitle(string);
            this.d4sFoodWarnWindow.setImage(i);
            this.d4sFoodWarnWindow.show(this.activity.getWindow().getDecorView());
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.d4s.widget.D4sHistogram.ChartOnClickListener
    public void clearSelection() {
        if (this.rlPopStatisticsBg.getVisibility() == 0) {
            this.rlPopStatisticsBg.setVisibility(4);
        }
    }

    private void showPopWindow() {
        Handler handler = this.handler;
        if (handler != null) {
            handler.postDelayed(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.d4s.widget.D4sHomeDeviceView.12
                @Override // java.lang.Runnable
                public void run() {
                    D4sHomeDeviceView.this.rlPopStatisticsBg.setVisibility(0);
                }
            }, 200L);
        } else {
            this.rlPopStatisticsBg.setVisibility(0);
        }
    }

    private String minutesToHour(int i) {
        String string;
        String string2;
        if (i < 0) {
            int i2 = i + 1440;
            string = Integer.toString(i2 / 60);
            string2 = Integer.toString(i2 % 60);
        } else if (i < 1440) {
            string = Integer.toString(i / 60);
            string2 = Integer.toString(i % 60);
        } else {
            int i3 = i - 1440;
            string = Integer.toString(i3 / 60);
            string2 = Integer.toString(i3 % 60);
        }
        if (string.length() < 2) {
            string = "0" + string;
        }
        if (string2.length() < 2) {
            string2 = "0" + string2;
        }
        return string + ":" + string2;
    }

    private List<BleFeederTimeView.BleFeederTimeViewData> processFeedStateData() {
        int i;
        ArrayList arrayList = new ArrayList();
        if (this.d4sRecord.getState().getFeedState() != null) {
            Map<String, Integer> feedTimes = this.d4sRecord.getState().getFeedState().getFeedTimes();
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
        int i = !CommonUtils.isSameTimeZoneAsLocal(this.d4sRecord.getLocale(), this.d4sRecord.getTimezone()) ? 1 : 0;
        if (this.d4sRecord.getState().getWifi().getRsq() < -70) {
            i++;
        }
        if (i == 0) {
            this.tvLittleWarnText.setVisibility(8);
            return;
        }
        if (i == 1) {
            this.tvLittleWarnText.setVisibility(0);
            if (!CommonUtils.isSameTimeZoneAsLocal(this.d4sRecord.getLocale(), this.d4sRecord.getTimezone())) {
                this.tvLittleWarnText.setText(R.string.Time_zone_is_different);
                return;
            } else {
                if (this.d4sRecord.getState().getWifi().getRsq() < -70) {
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
        }
    }

    private void startDeepEnergySavingLoading() {
        if (this.animation == null) {
            this.animation = new RotateAnimation(0.0f, 360.0f, 1, 0.5f, 1, 0.5f);
        }
        this.animation.setFillAfter(true);
        this.animation.setDuration(1000L);
        this.animation.setRepeatCount(-1);
        this.animation.setInterpolator(new LinearInterpolator());
        this.ivDeepEnergySavingRunning.startAnimation(this.animation);
    }

    private void stopDeepEnergySavingLoading() {
        Animation animation = this.animation;
        if (animation != null) {
            animation.cancel();
        }
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
        guideBuilder.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4s.widget.D4sHomeDeviceView.13
            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onShown() {
            }

            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onDismiss() {
                DataHelper.setBooleanSF(D4sHomeDeviceView.this.mContext, Consts.D4S_EAT_RECORD_IS_FIRST, Boolean.TRUE);
                D4sHomeDeviceView.this.d4sViewLayout.fling(1);
                D4sHomeDeviceView.this.d4sViewLayout.setOnTouchListener(new View.OnTouchListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4s.widget.D4sHomeDeviceView.13.1
                    @Override // android.view.View.OnTouchListener
                    public boolean onTouch(View view2, MotionEvent motionEvent) {
                        return false;
                    }
                });
            }
        });
        guideBuilder.addComponent(new GuidePetTipAndBtnAndLineComponent(new GuideParam(getResources().getString(R.string.Home_chart_guide_tips), "", 4, 32, 20, 10, getResources().getString(R.string.Done), R.layout.layout_feeder_guide_one), new ConfirmListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4s.widget.D4sHomeDeviceView.14
            @Override // com.petkit.android.activities.petkitBleDevice.w5.guide.ConfirmListener
            public void onConfirmListener() {
                if (D4sHomeDeviceView.this.guide3 != null) {
                    D4sHomeDeviceView.this.guide3.dismiss();
                }
            }
        }));
        this.guide3 = guideBuilder.createGuide();
        this.contentView.post(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.d4s.widget.D4sHomeDeviceView.15
            @Override // java.lang.Runnable
            public void run() {
                D4sHomeDeviceView.this.guide3.show((Activity) D4sHomeDeviceView.this.getContext());
            }
        });
    }

    public void showGuideView2(View view) {
        if (this.guide2 != null) {
            return;
        }
        GuideBuilder guideBuilder = new GuideBuilder();
        guideBuilder.setTargetView(view).setAlpha(180).setHighTargetCorner(50).setHighTargetPaddingLeft(ArmsUtils.dip2px(this.mContext, 0.0f)).setHighTargetPaddingRight(ArmsUtils.dip2px(this.mContext, 0.0f)).setHighTargetPaddingBottom(ArmsUtils.dip2px(this.mContext, 0.0f)).setHighTargetPaddingTop(ArmsUtils.dip2px(this.mContext, 0.0f)).setOutsideTouchable(false).setAutoDismiss(false);
        guideBuilder.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4s.widget.D4sHomeDeviceView.16
            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onShown() {
            }

            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onDismiss() {
                DataHelper.setBooleanSF(D4sHomeDeviceView.this.mContext, Consts.D4S_HOME_DATA_GUIDE, Boolean.TRUE);
            }
        });
        guideBuilder.addComponent(new GuideTipAndBtnAndLineComponent(new GuideParam(getResources().getString(R.string.Home_menu_guide_tips), "2/2", 2, 48, 0, -10, getResources().getString(R.string.Done), R.layout.layout_home_guide_mine), new ConfirmListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4s.widget.D4sHomeDeviceView.17
            @Override // com.petkit.android.activities.petkitBleDevice.w5.guide.ConfirmListener
            public void onConfirmListener() {
                if (D4sHomeDeviceView.this.guide2 != null) {
                    D4sHomeDeviceView.this.guide2.dismiss();
                }
            }
        }));
        this.guide2 = guideBuilder.createGuide();
        this.contentView.post(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.d4s.widget.D4sHomeDeviceView.18
            @Override // java.lang.Runnable
            public void run() {
                D4sHomeDeviceView.this.guide2.show((Activity) D4sHomeDeviceView.this.getContext());
            }
        });
    }

    public void showGuideView() {
        if (this.guide1 != null) {
            return;
        }
        GuideBuilder guideBuilder = new GuideBuilder();
        guideBuilder.setTargetView(this.rlDailyData).setAlpha(180).setHighTargetCorner(50).setHighTargetPaddingLeft(ArmsUtils.dip2px(this.mContext, 8.0f)).setHighTargetPaddingRight(ArmsUtils.dip2px(this.mContext, 8.0f)).setHighTargetPaddingBottom(ArmsUtils.dip2px(this.mContext, 8.0f)).setHighTargetPaddingTop(ArmsUtils.dip2px(this.mContext, 8.0f)).setOutsideTouchable(false).setAutoDismiss(false);
        guideBuilder.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4s.widget.D4sHomeDeviceView.19
            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onShown() {
            }

            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onDismiss() {
                if (D4sHomeDeviceView.this.bleMenuView.getVisibility() != 8) {
                    D4sHomeDeviceView d4sHomeDeviceView = D4sHomeDeviceView.this;
                    d4sHomeDeviceView.showGuideView2(d4sHomeDeviceView.bleMenuView);
                }
            }
        });
        guideBuilder.addComponent(new GuidePetTipAndBtnAndLineComponent(new GuideParam(getResources().getString(R.string.D3_top_guide_tips), "1/2", 4, 32, -50, 10, getResources().getString(R.string.Next_tip), R.layout.layout_feeder_guide_one), new ConfirmListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4s.widget.D4sHomeDeviceView.20
            @Override // com.petkit.android.activities.petkitBleDevice.w5.guide.ConfirmListener
            public void onConfirmListener() {
                if (D4sHomeDeviceView.this.guide1 != null) {
                    D4sHomeDeviceView.this.guide1.dismiss();
                }
            }
        }));
        this.guide1 = guideBuilder.createGuide();
        this.contentView.post(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.d4s.widget.D4sHomeDeviceView.21
            @Override // java.lang.Runnable
            public void run() {
                D4sHomeDeviceView.this.guide1.show((Activity) D4sHomeDeviceView.this.getContext());
            }
        });
    }

    @Override // com.petkit.android.activities.petkitBleDevice.widget.BleDeviceHomeMenuView.OnMenuClickListener
    public void onMenuClick(BleMenuItem bleMenuItem, int i) {
        MenuOnClickListener menuOnClickListener = this.menuOnClickListener;
        if (menuOnClickListener != null) {
            if (i != 0) {
                if (i == 1) {
                    menuOnClickListener.onBottomMenuClick(MenuType.FEED_PLAN);
                    return;
                } else {
                    if (i != 2) {
                        return;
                    }
                    menuOnClickListener.onBottomMenuClick(MenuType.DEEP_ENERGY_SAVING);
                    return;
                }
            }
            if (this.d4sRecord.getState().getFeeding() == 1 || this.d4sRecord.getState().getFeeding() == 2 || this.d4sRecord.getState().getFeeding() == 3) {
                this.menuOnClickListener.onBottomMenuClick(MenuType.STOP_FEED);
            } else {
                this.menuOnClickListener.onBottomMenuClick(MenuType.FEED_NOW);
            }
        }
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
        activity.startActivity(BleDeviceWifiSettingActivity.newIntent(activity, this.d4sRecord.getDeviceId(), 20));
    }

    @Override // com.petkit.android.activities.petkitBleDevice.widget.BleDeviceHomeOfflineWarnWindow.OfflineClickListener
    public void onClickReset() {
        Activity activity = this.activity;
        activity.startActivity(BleDeviceBindNetWorkActivity.newIntent((Context) activity, this.d4sRecord.getDeviceId(), 20, this.d4sRecord.getBtMac(), false));
    }

    public ChartOnClickListener getChartOnClickListener() {
        return this.chartOnClickListener;
    }

    public void setChartOnClickListener(ChartOnClickListener chartOnClickListener) {
        this.chartOnClickListener = chartOnClickListener;
    }

    @Override // com.petkit.android.activities.petkitBleDevice.d4s.adapter.D4sHomeRecordAdapter.OnClickListener
    public void onDelete(int i, int i2, int i3) {
        openDeleteRecordWindow();
        this.deletePosition = i3;
        this.d4sRemoveData.setEndTime(i);
        this.d4sRemoveData.setStartTime(i2);
        this.d4sRemoveData.setDeleteType(1);
    }

    @Override // com.petkit.android.activities.petkitBleDevice.d4s.adapter.D4sHomeRecordAdapter.OnClickListener
    public void onDeleteFeedRecord(int i, int i2) {
        openDeleteRecordWindow();
        this.deletePosition = i2;
        this.d4sRemoveData.setStartTime(i);
        this.d4sRemoveData.setDeleteType(2);
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
        Disposable disposable = this.disposable;
        if (disposable != null && !disposable.isDisposed()) {
            this.disposable.dispose();
        }
        super.onDetachedFromWindow();
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
        valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4s.widget.D4sHomeDeviceView.22
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int iIntValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                D4sHomeDeviceView d4sHomeDeviceView = D4sHomeDeviceView.this;
                if (d4sHomeDeviceView.lastValue >= iIntValue || d4sHomeDeviceView.llAccessoryConsumables == null) {
                    return;
                }
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) D4sHomeDeviceView.this.llAccessoryConsumables.getLayoutParams();
                layoutParams.width = iIntValue;
                D4sHomeDeviceView.this.llAccessoryConsumables.setLayoutParams(layoutParams);
                D4sHomeDeviceView.this.lastValue = iIntValue;
            }
        });
        this.expandValueAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.petkit.android.activities.petkitBleDevice.d4s.widget.D4sHomeDeviceView.23
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                D4sHomeDeviceView d4sHomeDeviceView = D4sHomeDeviceView.this;
                d4sHomeDeviceView.isRunning = false;
                d4sHomeDeviceView.accessoryConsumables = 0;
                D4sHomeDeviceView d4sHomeDeviceView2 = D4sHomeDeviceView.this;
                int i3 = i;
                d4sHomeDeviceView2.startReduceAni(i3, i3 - d4sHomeDeviceView2.tvWidth);
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
        valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4s.widget.D4sHomeDeviceView.24
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int iIntValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                D4sHomeDeviceView d4sHomeDeviceView = D4sHomeDeviceView.this;
                if (d4sHomeDeviceView.lastValue <= iIntValue || d4sHomeDeviceView.llAccessoryConsumables == null) {
                    return;
                }
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) D4sHomeDeviceView.this.llAccessoryConsumables.getLayoutParams();
                layoutParams.width = iIntValue;
                D4sHomeDeviceView.this.llAccessoryConsumables.setLayoutParams(layoutParams);
                D4sHomeDeviceView.this.lastValue = iIntValue;
            }
        });
        this.reduceValueAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.petkit.android.activities.petkitBleDevice.d4s.widget.D4sHomeDeviceView.25
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                D4sHomeDeviceView.this.accessoryConsumables = 1;
                D4sHomeDeviceView.this.isRunning = false;
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
        this.llAccessoryConsumables.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4s.widget.D4sHomeDeviceView.26
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                D4sHomeDeviceView d4sHomeDeviceView = D4sHomeDeviceView.this;
                d4sHomeDeviceView.measuredWidth = d4sHomeDeviceView.llAccessoryConsumables.getMeasuredWidth();
                D4sHomeDeviceView d4sHomeDeviceView2 = D4sHomeDeviceView.this;
                d4sHomeDeviceView2.lastValue = d4sHomeDeviceView2.measuredWidth;
                D4sHomeDeviceView d4sHomeDeviceView3 = D4sHomeDeviceView.this;
                d4sHomeDeviceView3.tvWidth = d4sHomeDeviceView3.tvAccessoryConsumables.getMeasuredWidth();
                D4sHomeDeviceView d4sHomeDeviceView4 = D4sHomeDeviceView.this;
                d4sHomeDeviceView4.startReduceAni(d4sHomeDeviceView4.measuredWidth, D4sHomeDeviceView.this.measuredWidth - D4sHomeDeviceView.this.tvWidth);
                D4sHomeDeviceView.this.llAccessoryConsumables.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }
}

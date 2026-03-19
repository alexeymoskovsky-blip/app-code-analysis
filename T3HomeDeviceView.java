package com.petkit.android.activities.petkitBleDevice.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.motion.widget.Key;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;
import com.binioter.guideview.Guide;
import com.binioter.guideview.GuideBuilder;
import com.facebook.login.widget.ToolTipPopup;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.Consts;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.widget.InterceptViewPager;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.community.WebviewActivity;
import com.petkit.android.activities.family.mode.FamilyInfor;
import com.petkit.android.activities.home.utils.GuidePetTipAndBtnAndLineComponent;
import com.petkit.android.activities.home.utils.GuideTipAndBtnAndLineComponent;
import com.petkit.android.activities.mall.mode.PromoteData;
import com.petkit.android.activities.mall.widget.PromoteView;
import com.petkit.android.activities.petkitBleDevice.BleDeviceBindNetWorkActivity;
import com.petkit.android.activities.petkitBleDevice.BleDeviceWifiSettingActivity;
import com.petkit.android.activities.petkitBleDevice.PetColorSettingActivity;
import com.petkit.android.activities.petkitBleDevice.adapter.T3HomeCenterAdapter;
import com.petkit.android.activities.petkitBleDevice.adapter.T3HomeHistoryRecordAdapter;
import com.petkit.android.activities.petkitBleDevice.mode.BleMenuItem;
import com.petkit.android.activities.petkitBleDevice.mode.T3DeviceRecord;
import com.petkit.android.activities.petkitBleDevice.mode.T3Record;
import com.petkit.android.activities.petkitBleDevice.mode.T3StatisticInfo;
import com.petkit.android.activities.petkitBleDevice.mode.T3StatisticResult;
import com.petkit.android.activities.petkitBleDevice.mode.TimeViewResult;
import com.petkit.android.activities.petkitBleDevice.mode.ToiletCompareResult;
import com.petkit.android.activities.petkitBleDevice.mode.UpdatePetColorMsg;
import com.petkit.android.activities.petkitBleDevice.t4.mode.RelatedProductsInfor;
import com.petkit.android.activities.petkitBleDevice.t4.utils.T4Utils;
import com.petkit.android.activities.petkitBleDevice.t4.widget.T4BarChartView;
import com.petkit.android.activities.petkitBleDevice.utils.BleDeviceUtils;
import com.petkit.android.activities.petkitBleDevice.utils.ColorUtils;
import com.petkit.android.activities.petkitBleDevice.utils.FamilyUtils;
import com.petkit.android.activities.petkitBleDevice.utils.TextUtil;
import com.petkit.android.activities.petkitBleDevice.utils.TimeUtils;
import com.petkit.android.activities.petkitBleDevice.utils.UnitUtils;
import com.petkit.android.activities.petkitBleDevice.w5.guide.ConfirmListener;
import com.petkit.android.activities.petkitBleDevice.w5.guide.GuideParam;
import com.petkit.android.activities.petkitBleDevice.widget.BleDeviceHomeMenuView;
import com.petkit.android.activities.petkitBleDevice.widget.BleDeviceHomeOfflineWarnWindow;
import com.petkit.android.activities.petkitBleDevice.widget.BleTimeView;
import com.petkit.android.activities.petkitBleDevice.widget.PetkitSlidingUpPanelLayout;
import com.petkit.android.activities.petkitBleDevice.widget.ScrollViewWithListener2;
import com.petkit.android.activities.petkitBleDevice.widget.T3Histogram;
import com.petkit.android.activities.universalWindow.BaseBottomWindow;
import com.petkit.android.activities.universalWindow.PetFilterWindow;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.model.Pet;
import com.petkit.android.utils.CommonUtil;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.LogcatStorageHelper;
import com.petkit.android.utils.PetkitLog;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.oversea.R;
import io.reactivex.disposables.Disposable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

/* JADX INFO: loaded from: classes5.dex */
public class T3HomeDeviceView extends LinearLayout implements BleDeviceHomeMenuView.OnMenuClickListener, View.OnClickListener, PetkitSlidingUpPanelLayout.PanelSlideListener, ViewPager.OnPageChangeListener, BleDeviceHomeOfflineWarnWindow.OfflineClickListener, T3HomeHistoryRecordAdapter.OnClickListener, BaseBottomWindow.OnClickListener {
    private int accessoryConsumables;
    private Activity activity;
    private AnimatorSet adAnimatorSet;
    private ObjectAnimator adXAnimator;
    private ObjectAnimator adYAnimator;
    private List<AnimatorSet> animatorLeftSetList;
    private List<AnimatorSet> animatorSetList;
    private BleDeviceHomeOfflineWarnWindow bleDeviceHomeOfflineWarnWindow;
    private BleDeviceHomeTroubleWarnWindow bleDeviceHomeTroubleWarnWindow;
    private BleDeviceHomeMenuView bleMenuView;
    private ObjectAnimator boxAlphaAnimator;
    private Button btnT3ContinueToRun;
    private Button btnT3PauseClean;
    private Button btnT3TerminateClean;
    private T4BarChartView.ChartOnClickListener chartOnClickListener;
    private ObjectAnimator clockwiseRotateAnimator;
    private View contentView;
    private ObjectAnimator counterclockwiseRotateAnimator;
    private int currentPageSelected;
    private ObjectAnimator deodorantAlphaAnimator;
    private ObjectAnimator deodorantRunningAlphaAnimator;
    private Disposable disposable;
    ValueAnimator expandValueAnimator;
    private FamilyInfor familyInfor;
    private Guide guide;
    private Guide guide1;
    private Guide guide2;
    private Guide guide3;
    private List<T3DeviceRecord> historyRecord;
    private ObjectAnimator horizontalAnimator;
    private AnimatorSet iconAnimatorSet;
    private ObjectAnimator iconRotateAnimator;
    private ObjectAnimator iconXAnimator;
    private ObjectAnimator iconYAnimator;
    private boolean isAllowedOpenGuide;
    boolean isAniStart;
    private boolean isFirst;
    private boolean isInDisturbTime;
    private boolean isNeedShowPop;
    private boolean isReady;
    boolean isRunning;
    private boolean isScroll;
    private boolean isShowGuide;
    private boolean isUnMask;
    boolean isUp;
    private ImageView ivAccessoryConsumables;
    private ImageView ivAni;
    private ImageView ivBoxConsumablesInlet;
    private ImageView ivK3ConsumablesInlet;
    private ImageView ivLackLiquidBg;
    private ImageView ivLackLitterBg;
    private ImageView ivLeftArrow;
    private ImageView ivLitterConsumablesInlet;
    private ImageView ivT3ErrorIcon;
    int lastValue;
    private ObjectAnimator liquideAlphaAnimator;
    private ObjectAnimator litterAlphaAnimator;
    private LinearLayout llAccessoryConsumables;
    private LinearLayout llBottomMenuParentView;
    private LinearLayout llBtnAndWarnPanel;
    private LinearLayout llPets;
    private LinearLayout llT3BtnPanel;
    private LinearLayout llTodayData;
    private LinearLayout llWarnPanel;
    private Context mContext;
    private float mSlideOffset;
    private VelocityTracker mVelocityTracker;
    private MainHandler mainHandler;
    private int measuredWidth;
    private MenuOnClickListener menuOnClickListener;
    private ScrollViewWithListener2 nestedScrollView;
    private OnHistogramPageChange onHistogramPageChange;
    private PetFilterWindow petFilterWindow;
    private View pointGuideViewBox;
    private View pointGuideViewLiquid;
    private View pointGuideViewLitter;
    private PopupWindow popupWindow;
    private PromoteView promoteView;
    String recordDate;
    private RecordOnClickListener recordOnClickListener;
    ValueAnimator reduceValueAnimator;
    private RelativeLayout rlBoxConsumablesInlet;
    private RelativeLayout rlBtnAndWarnPanel;
    private RelativeLayout rlChartGuide;
    private RelativeLayout rlDevice;
    private RelativeLayout rlK3ConsumablesInlet;
    private RelativeLayout rlLiquidNotInstall;
    private RelativeLayout rlLitterConsumablesInlet;
    private RelativeLayout rlPop;
    private RelativeLayout rlRecordPanel;
    private RelativeLayout rlT3CenterText;
    private RelativeLayout rlT3DeviceCenter;
    private RelativeLayout rlTimePanel;
    private RelativeLayout rlTopView;
    private ScrollRecyclerView rvT3RecordView;
    private AnimatorSet scanAnimatorSet;
    private List<String> selectedPetIds;
    private boolean showDataDesc;
    private int statisticType;
    private String symbol;
    private ImageView t3DeviceCenterMaskBg;
    private ImageView t3DeviceCenterProgress;
    private ImageView t3DeviceCenterWhiteBg;
    private T3DeviceRecord t3DeviceRecord;
    List<T3DeviceRecord> t3DeviceRecordList;
    private ImageView t3DeviceScan;
    private T3Histogram t3Histogram;
    private T3HomeCenterAdapter t3HomeCenterAdapter;
    private RelativeLayout t3HomeCenterPanel;
    private T3HomeHistoryRecordAdapter t3HomeHistoryRecordAdapter;
    private T3Record t3Record;
    private ImageView t3StateIconTimezone;
    private T3StatisticResult t3StatisticResult;
    private BleTimeView t3TimeView;
    private ImageView t3ViewDeviceDeodorant;
    private ImageView t3ViewDeviceDeodorantRunning;
    private ImageView t4ViewDeviceCenterBox;
    private float tempY;
    private List<Pet> thisDeviceFamilyPetList;
    private TipWindow tipWindow;
    private ToiletCompareResult toiletCompareResult;
    private TextView tvAccessoryConsumables;
    private TextView tvBoxConsumablesInlet;
    private TextView tvHistoryDate;
    private TextView tvHistoryStatisticDesc;
    private TextView tvHistoryTime;
    private TextView tvK3ConsumablesInlet;
    private TextView tvLegend;
    private TextView tvLitterConsumablesInlet;
    private TextView tvLittleWarnText;
    private TextView tvMore;
    private TextView tvNotInstalled;
    private TextView tvPetNameOne;
    private TextView tvPetNameTwo;
    private TextView tvPetWeight;
    private TextView tvPopContent;
    private TextView tvT3CenterBottom;
    private TextView tvT3CenterText;
    private TextView tvT3DeviceState;
    private TextView tvTimeContent;
    private TextView tvTodayAverage;
    private TextView tvTodayAverageComparedYesterday;
    private TextView tvTodayTime;
    private TextView tvTodayTimeComparedYesterday;
    private int tvWidth;
    private View viewPointOne;
    private View viewPointTwo;
    private InterceptViewPager vpT3Center;
    private float widthScale;

    public interface MenuOnClickListener {
        void onBottomMenuClick(MenuType menuType);
    }

    public enum MenuType {
        ON_OFF,
        CLEAN_UP,
        DEODORANT,
        SMART_SETTING,
        PAUSE_CLEAN,
        TERMINATE_CLEAN,
        CONTINUE_CLEAN,
        RESUME_RUN,
        CONSUMABLES_LITTER,
        CONSUMABLES_BOX,
        CONSUMABLES_LIQUID,
        UNDERWEIGHT,
        DATE_PICKER,
        DEODORIZATION_MODULE_NOT_INSTALLED,
        UNUSED_DIALOG
    }

    public interface OnHistogramPageChange {
        void pageChange(int i);

        void typeChange(int i);
    }

    public interface RecordOnClickListener {
        void onClickRecord(T3DeviceRecord t3DeviceRecord, String str);

        void onDelRecord(T3DeviceRecord t3DeviceRecord, String str);

        void onUnknownPetClick(String str, String str2, String str3, double d);
    }

    private void startAdAni() {
    }

    private void startBoxAnimation() {
    }

    private void stopAdAni() {
    }

    private void stopBoxAnimation() {
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageScrolled(int i, float f, int i2) {
    }

    @Override // com.petkit.android.activities.universalWindow.BaseBottomWindow.OnClickListener
    public void onTitleBtn() {
    }

    @Override // com.petkit.android.activities.universalWindow.BaseBottomWindow.OnClickListener
    public void onTopLeftBtn() {
    }

    @RequiresApi(api = 23)
    public T3HomeDeviceView(Context context) {
        super(context);
        this.historyRecord = new ArrayList();
        this.widthScale = 0.52f;
        this.currentPageSelected = 20000;
        this.isInDisturbTime = false;
        this.selectedPetIds = new ArrayList(Arrays.asList(ColorUtils.ALL_PET));
        this.thisDeviceFamilyPetList = new ArrayList();
        this.isReady = false;
        this.isAniStart = false;
        this.statisticType = 0;
        this.isFirst = true;
        this.isNeedShowPop = false;
        this.isAllowedOpenGuide = false;
        this.guide1 = null;
        this.guide2 = null;
        this.guide3 = null;
        this.lastValue = 0;
        this.mContext = context;
        this.activity = (Activity) context;
        this.animatorSetList = new ArrayList();
        this.animatorLeftSetList = new ArrayList();
        initView();
    }

    @RequiresApi(api = 23)
    public T3HomeDeviceView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.historyRecord = new ArrayList();
        this.widthScale = 0.52f;
        this.currentPageSelected = 20000;
        this.isInDisturbTime = false;
        this.selectedPetIds = new ArrayList(Arrays.asList(ColorUtils.ALL_PET));
        this.thisDeviceFamilyPetList = new ArrayList();
        this.isReady = false;
        this.isAniStart = false;
        this.statisticType = 0;
        this.isFirst = true;
        this.isNeedShowPop = false;
        this.isAllowedOpenGuide = false;
        this.guide1 = null;
        this.guide2 = null;
        this.guide3 = null;
        this.lastValue = 0;
        this.mContext = context;
        this.activity = (Activity) context;
        this.animatorSetList = new ArrayList();
        this.animatorLeftSetList = new ArrayList();
        initView();
    }

    @RequiresApi(api = 23)
    public T3HomeDeviceView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.historyRecord = new ArrayList();
        this.widthScale = 0.52f;
        this.currentPageSelected = 20000;
        this.isInDisturbTime = false;
        this.selectedPetIds = new ArrayList(Arrays.asList(ColorUtils.ALL_PET));
        this.thisDeviceFamilyPetList = new ArrayList();
        this.isReady = false;
        this.isAniStart = false;
        this.statisticType = 0;
        this.isFirst = true;
        this.isNeedShowPop = false;
        this.isAllowedOpenGuide = false;
        this.guide1 = null;
        this.guide2 = null;
        this.guide3 = null;
        this.lastValue = 0;
        this.mContext = context;
        this.activity = (Activity) context;
        this.animatorSetList = new ArrayList();
        this.animatorLeftSetList = new ArrayList();
        initView();
    }

    public OnHistogramPageChange getOnHistogramPageChange() {
        return this.onHistogramPageChange;
    }

    public void setOnHistogramPageChange(OnHistogramPageChange onHistogramPageChange) {
        this.onHistogramPageChange = onHistogramPageChange;
    }

    public RecordOnClickListener getRecordOnClickListener() {
        return this.recordOnClickListener;
    }

    public void setRecordOnClickListener(RecordOnClickListener recordOnClickListener) {
        this.recordOnClickListener = recordOnClickListener;
    }

    @RequiresApi(api = 23)
    private void initView() {
        this.mVelocityTracker = VelocityTracker.obtain();
        EventBus.getDefault().register(this);
        View viewInflate = LayoutInflater.from(this.mContext).inflate(R.layout.layout_t3_home_device_view, (ViewGroup) null);
        this.contentView = viewInflate;
        addView(viewInflate, -1, -1);
        TextView textView = (TextView) this.contentView.findViewById(R.id.tv_pop_content);
        this.tvPopContent = textView;
        textView.measure(0, 0);
        ImageView imageView = (ImageView) this.contentView.findViewById(R.id.iv_bottom_arrow);
        int measuredWidth = (this.tvPopContent.getMeasuredWidth() - ArmsUtils.dip2px(this.mContext, 10.0f)) - ArmsUtils.dip2px(this.mContext, 20.0f);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
        layoutParams.setMargins(measuredWidth, layoutParams.topMargin, layoutParams.rightMargin, layoutParams.bottomMargin);
        imageView.setLayoutParams(layoutParams);
        this.ivAccessoryConsumables = (ImageView) this.contentView.findViewById(R.id.iv_accessory_consumables);
        this.tvAccessoryConsumables = (TextView) this.contentView.findViewById(R.id.tv_accessory_consumables);
        this.llAccessoryConsumables = (LinearLayout) this.contentView.findViewById(R.id.ll_accessory_consumables);
        this.llPets = (LinearLayout) this.contentView.findViewById(R.id.ll_pets);
        this.rlRecordPanel = (RelativeLayout) this.contentView.findViewById(R.id.rl_record_panel);
        this.ivLeftArrow = (ImageView) this.contentView.findViewById(R.id.iv_left_arrow);
        this.llTodayData = (LinearLayout) this.contentView.findViewById(R.id.ll_today_data);
        this.tvMore = (TextView) this.contentView.findViewById(R.id.tv_history_more);
        this.llTodayData.setOnClickListener(this);
        this.tvMore.setOnClickListener(this);
        this.tvLegend = (TextView) this.contentView.findViewById(R.id.tv_legend);
        this.tvPetNameOne = (TextView) this.contentView.findViewById(R.id.tv_pet_name_one);
        this.tvPetNameTwo = (TextView) this.contentView.findViewById(R.id.tv_pet_name_two);
        this.viewPointTwo = this.contentView.findViewById(R.id.view_point_two);
        this.viewPointOne = this.contentView.findViewById(R.id.view_point_one);
        this.rlPop = (RelativeLayout) this.contentView.findViewById(R.id.rl_pop_parent);
        this.ivAni = (ImageView) this.contentView.findViewById(R.id.iv_ani);
        ScrollViewWithListener2 scrollViewWithListener2 = (ScrollViewWithListener2) this.contentView.findViewById(R.id.t3_view_layout);
        this.nestedScrollView = scrollViewWithListener2;
        scrollViewWithListener2.setScrollviewOnTouchListener(new ScrollViewWithListener2.ScrollviewOnTouchListener() { // from class: com.petkit.android.activities.petkitBleDevice.widget.T3HomeDeviceView.1
            @Override // com.petkit.android.activities.petkitBleDevice.widget.ScrollViewWithListener2.ScrollviewOnTouchListener
            public void finishScroll(boolean z) {
            }

            @Override // com.petkit.android.activities.petkitBleDevice.widget.ScrollViewWithListener2.ScrollviewOnTouchListener
            public void onScrolling(int i, int i2, int i3, int i4) {
                if (i2 - i4 > 5) {
                    Log.d("scrolling", "up");
                    T3HomeDeviceView t3HomeDeviceView = T3HomeDeviceView.this;
                    t3HomeDeviceView.isUp = true;
                    if (!t3HomeDeviceView.isAniStart && t3HomeDeviceView.bleMenuView.getVisibility() == 0) {
                        Animation animationLoadAnimation = AnimationUtils.loadAnimation(T3HomeDeviceView.this.activity, R.anim.ble_menu_slide_out_to_bottom);
                        animationLoadAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.petkit.android.activities.petkitBleDevice.widget.T3HomeDeviceView.1.1
                            @Override // android.view.animation.Animation.AnimationListener
                            public void onAnimationRepeat(Animation animation) {
                            }

                            @Override // android.view.animation.Animation.AnimationListener
                            public void onAnimationStart(Animation animation) {
                            }

                            @Override // android.view.animation.Animation.AnimationListener
                            public void onAnimationEnd(Animation animation) {
                                T3HomeDeviceView t3HomeDeviceView2 = T3HomeDeviceView.this;
                                t3HomeDeviceView2.isAniStart = false;
                                t3HomeDeviceView2.bleMenuView.setVisibility(8);
                            }
                        });
                        T3HomeDeviceView.this.bleMenuView.startAnimation(animationLoadAnimation);
                        T3HomeDeviceView.this.isAniStart = true;
                    }
                } else if (i4 - i2 > 5) {
                    T3HomeDeviceView t3HomeDeviceView2 = T3HomeDeviceView.this;
                    t3HomeDeviceView2.isUp = false;
                    if (!t3HomeDeviceView2.isAniStart && t3HomeDeviceView2.bleMenuView.getVisibility() == 8) {
                        Animation animationLoadAnimation2 = AnimationUtils.loadAnimation(T3HomeDeviceView.this.activity, R.anim.panel_slide_in_from_bottom);
                        animationLoadAnimation2.setAnimationListener(new Animation.AnimationListener() { // from class: com.petkit.android.activities.petkitBleDevice.widget.T3HomeDeviceView.1.2
                            @Override // android.view.animation.Animation.AnimationListener
                            public void onAnimationRepeat(Animation animation) {
                            }

                            @Override // android.view.animation.Animation.AnimationListener
                            public void onAnimationStart(Animation animation) {
                            }

                            @Override // android.view.animation.Animation.AnimationListener
                            public void onAnimationEnd(Animation animation) {
                                T3HomeDeviceView t3HomeDeviceView3 = T3HomeDeviceView.this;
                                t3HomeDeviceView3.isAniStart = false;
                                t3HomeDeviceView3.bleMenuView.setVisibility(0);
                            }
                        });
                        T3HomeDeviceView.this.bleMenuView.startAnimation(animationLoadAnimation2);
                        T3HomeDeviceView.this.isAniStart = true;
                    }
                }
                if (((((((T3HomeDeviceView.this.bleMenuView.getHeight() + T3HomeDeviceView.this.rlChartGuide.getHeight()) + T3HomeDeviceView.this.rlRecordPanel.getTop()) + T3HomeDeviceView.this.rlTimePanel.getTop()) + T3HomeDeviceView.this.llBottomMenuParentView.getTop()) - T3HomeDeviceView.this.nestedScrollView.getScrollY()) + ArmsUtils.dip2px(T3HomeDeviceView.this.getContext(), 60.0f)) - (T3HomeDeviceView.this.bleMenuView.getTop() - T3HomeDeviceView.this.contentView.getTop()) > 0 || DataHelper.getBooleanSF(T3HomeDeviceView.this.mContext, Consts.T3_CHART_IS_FIRST) || T3HomeDeviceView.this.isShowGuide) {
                    return;
                }
                T3HomeDeviceView.this.nestedScrollView.fling(0);
                T3HomeDeviceView.this.nestedScrollView.setOnTouchListener(new View.OnTouchListener() { // from class: com.petkit.android.activities.petkitBleDevice.widget.T3HomeDeviceView.1.3
                    @Override // android.view.View.OnTouchListener
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        return true;
                    }
                });
                T3HomeDeviceView.this.isShowGuide = true;
                T3HomeDeviceView t3HomeDeviceView3 = T3HomeDeviceView.this;
                t3HomeDeviceView3.showRecordGuideView(t3HomeDeviceView3.rlChartGuide);
            }
        });
        T3Histogram t3Histogram = (T3Histogram) this.contentView.findViewById(R.id.t3_histogram);
        this.t3Histogram = t3Histogram;
        t3Histogram.setOnPageChanged(new T3Histogram.OnPageChanged() { // from class: com.petkit.android.activities.petkitBleDevice.widget.T3HomeDeviceView.2
            @Override // com.petkit.android.activities.petkitBleDevice.widget.T3Histogram.OnPageChanged
            public void change(int i) {
                T3HomeDeviceView.this.onHistogramPageChange.pageChange(i);
                T3HomeDeviceView.this.stopLeftAni();
            }
        });
        TextView textView2 = (TextView) this.contentView.findViewById(R.id.tv_history_date);
        this.tvHistoryDate = textView2;
        textView2.setOnTouchListener(new View.OnTouchListener() { // from class: com.petkit.android.activities.petkitBleDevice.widget.T3HomeDeviceView.3
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == 1 && T3HomeDeviceView.this.tvHistoryDate.getCompoundDrawables()[2] != null && motionEvent.getX() > T3HomeDeviceView.this.tvHistoryDate.getWidth() - T3HomeDeviceView.this.tvHistoryDate.getCompoundDrawables()[2].getBounds().width()) {
                    T3HomeDeviceView.this.menuOnClickListener.onBottomMenuClick(MenuType.DATE_PICKER);
                }
                return true;
            }
        });
        this.tvHistoryTime = (TextView) this.contentView.findViewById(R.id.tv_history_time);
        this.tvHistoryStatisticDesc = (TextView) this.contentView.findViewById(R.id.tv_history_statistic_desc);
        this.tvTodayTime = (TextView) this.contentView.findViewById(R.id.tv_today_time);
        this.tvTodayAverage = (TextView) this.contentView.findViewById(R.id.tv_today_average_time);
        this.tvTodayTimeComparedYesterday = (TextView) this.contentView.findViewById(R.id.tv_today_time_compared_yesterday);
        this.tvTodayAverageComparedYesterday = (TextView) this.contentView.findViewById(R.id.tv_today_average_time_compared_yesterday);
        this.tvLittleWarnText = (TextView) this.contentView.findViewById(R.id.tv_little_warn_text);
        this.rlBtnAndWarnPanel = (RelativeLayout) this.contentView.findViewById(R.id.rl_btn_and_warn_panel);
        this.llBtnAndWarnPanel = (LinearLayout) this.contentView.findViewById(R.id.ll_btn_and_warn_panel);
        this.t3HomeCenterPanel = (RelativeLayout) this.contentView.findViewById(R.id.t3_home_center_panel);
        this.rlTimePanel = (RelativeLayout) this.contentView.findViewById(R.id.rl_time_panel);
        BleDeviceHomeMenuView bleDeviceHomeMenuView = (BleDeviceHomeMenuView) this.contentView.findViewById(R.id.ble_menu_view);
        this.bleMenuView = bleDeviceHomeMenuView;
        bleDeviceHomeMenuView.setDeviceType(7, 0);
        this.rlTopView = (RelativeLayout) this.contentView.findViewById(R.id.rl_top_view);
        this.llBottomMenuParentView = (LinearLayout) this.contentView.findViewById(R.id.ll_bottom_menu_parent_view);
        this.tvTimeContent = (TextView) this.contentView.findViewById(R.id.tv_time_content);
        this.t3ViewDeviceDeodorant = (ImageView) this.contentView.findViewById(R.id.t3_view_device_center_deodorant_bg);
        this.t3ViewDeviceDeodorantRunning = (ImageView) this.contentView.findViewById(R.id.t3_view_device_center_deodorant_running_bg);
        this.t3DeviceScan = (ImageView) this.contentView.findViewById(R.id.t3_view_device_scan);
        this.rlT3CenterText = (RelativeLayout) this.contentView.findViewById(R.id.rl_t3_center_text);
        this.ivT3ErrorIcon = (ImageView) this.contentView.findViewById(R.id.iv_t3_error_icon);
        this.tvT3CenterText = (TextView) this.contentView.findViewById(R.id.tv_t3_center_text);
        this.tvT3CenterBottom = (TextView) this.contentView.findViewById(R.id.tv_t3_center_bottom);
        this.llWarnPanel = (LinearLayout) this.contentView.findViewById(R.id.ll_warn_panel);
        this.rlChartGuide = (RelativeLayout) this.contentView.findViewById(R.id.rl_chart_guide);
        this.rlDevice = (RelativeLayout) this.contentView.findViewById(R.id.rl_device);
        BleTimeView bleTimeView = (BleTimeView) this.contentView.findViewById(R.id.t3_time_view);
        this.t3TimeView = bleTimeView;
        bleTimeView.setTimeColor(R.color.t3_main_blue);
        this.t3TimeView.setProgressHeight(ArmsUtils.dip2px(this.mContext, 5.0f));
        this.t3TimeView.setTextSize(ArmsUtils.dip2px(this.mContext, 7.0f));
        this.t3TimeView.setMode(BleTimeView.Mode.TIME_POINT);
        ScrollRecyclerView scrollRecyclerView = (ScrollRecyclerView) this.contentView.findViewById(R.id.rv_t3_recordView);
        this.rvT3RecordView = scrollRecyclerView;
        scrollRecyclerView.setLayoutManager(new LinearLayoutManager(this.mContext));
        this.tvNotInstalled = (TextView) this.contentView.findViewById(R.id.tv_liquid_not_installed);
        this.rlBoxConsumablesInlet = (RelativeLayout) this.contentView.findViewById(R.id.rl_box_state);
        this.rlK3ConsumablesInlet = (RelativeLayout) this.contentView.findViewById(R.id.rl_liquid_state);
        this.rlLitterConsumablesInlet = (RelativeLayout) this.contentView.findViewById(R.id.rl_litter_state);
        this.rlLiquidNotInstall = (RelativeLayout) this.contentView.findViewById(R.id.rl_liquid_not_installed);
        this.ivBoxConsumablesInlet = (ImageView) this.contentView.findViewById(R.id.iv_box_state);
        this.ivK3ConsumablesInlet = (ImageView) this.contentView.findViewById(R.id.iv_liquid_state);
        this.ivLitterConsumablesInlet = (ImageView) this.contentView.findViewById(R.id.iv_litter_state);
        this.tvBoxConsumablesInlet = (TextView) this.contentView.findViewById(R.id.tv_box_state);
        this.tvK3ConsumablesInlet = (TextView) this.contentView.findViewById(R.id.tv_liquid_state);
        this.tvLitterConsumablesInlet = (TextView) this.contentView.findViewById(R.id.tv_litter_state);
        this.t4ViewDeviceCenterBox = (ImageView) this.contentView.findViewById(R.id.t3_view_device_center_box);
        this.ivLackLitterBg = (ImageView) this.contentView.findViewById(R.id.t3_view_device_center_litter);
        this.ivLackLiquidBg = (ImageView) this.contentView.findViewById(R.id.t3_view_device_center_liquid);
        this.promoteView = (PromoteView) this.contentView.findViewById(R.id.promoteView);
        this.pointGuideViewLiquid = this.contentView.findViewById(R.id.point_guide_view_liquid);
        this.pointGuideViewLitter = this.contentView.findViewById(R.id.point_guide_view_litter);
        this.pointGuideViewBox = this.contentView.findViewById(R.id.point_guide_view_box);
        this.vpT3Center = (InterceptViewPager) this.contentView.findViewById(R.id.vp_t3_center);
        this.t3DeviceCenterMaskBg = (ImageView) this.contentView.findViewById(R.id.t3_view_device_center_mask_bg);
        this.t3DeviceCenterWhiteBg = (ImageView) this.contentView.findViewById(R.id.t3_view_device_center_white_bg);
        this.t3DeviceCenterProgress = (ImageView) this.contentView.findViewById(R.id.t3_view_device_center_progress);
        this.rlT3DeviceCenter = (RelativeLayout) this.contentView.findViewById(R.id.rl_view_t3_device_center);
        this.t3StateIconTimezone = (ImageView) this.contentView.findViewById(R.id.t3_view_state_icon_timezone);
        this.btnT3TerminateClean = (Button) this.contentView.findViewById(R.id.btn_t3_view_terminate_clean);
        this.btnT3ContinueToRun = (Button) this.contentView.findViewById(R.id.btn_t3_view_continue_to_run);
        this.tvPetWeight = (TextView) this.contentView.findViewById(R.id.tv_pet_weight);
        this.btnT3PauseClean = (Button) this.contentView.findViewById(R.id.btn_t3_view_pause_clean);
        this.llT3BtnPanel = (LinearLayout) this.contentView.findViewById(R.id.ll_t3_view_btn_panel);
        this.tvT3DeviceState = (TextView) this.contentView.findViewById(R.id.tv_warn_text);
        this.bleMenuView.setOnMenuClickListener(this);
        this.llPets.setOnClickListener(this);
        this.t3StateIconTimezone.setOnClickListener(this);
        this.tvPetWeight.setOnClickListener(this);
        this.btnT3TerminateClean.setOnClickListener(this);
        this.btnT3ContinueToRun.setOnClickListener(this);
        this.btnT3ContinueToRun.setOnClickListener(this);
        this.tvT3DeviceState.setOnClickListener(this);
        this.btnT3PauseClean.setOnClickListener(this);
        this.rlRecordPanel.setOnClickListener(this);
        this.llAccessoryConsumables.setOnClickListener(this);
        this.ivAccessoryConsumables.setOnClickListener(this);
        this.tvAccessoryConsumables.setOnClickListener(this);
        this.tvNotInstalled.setOnClickListener(this);
        this.vpT3Center.addOnPageChangeListener(this);
        this.tvK3ConsumablesInlet.setOnClickListener(this);
        this.tvBoxConsumablesInlet.setOnClickListener(this);
        this.tvLitterConsumablesInlet.setOnClickListener(this);
        this.ivK3ConsumablesInlet.setOnClickListener(this);
        this.ivBoxConsumablesInlet.setOnClickListener(this);
        this.ivLitterConsumablesInlet.setOnClickListener(this);
        this.pointGuideViewLiquid.setOnClickListener(this);
        this.pointGuideViewLitter.setOnClickListener(this);
        this.pointGuideViewBox.setOnClickListener(this);
        this.tvLittleWarnText.setOnClickListener(this);
        this.activity.getWindow().getDecorView().setSystemUiVisibility(0);
        initViewSizeAndEvent();
        MainHandler mainHandler = new MainHandler(this.mContext);
        this.mainHandler = mainHandler;
        mainHandler.postDelayed(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.widget.T3HomeDeviceView.4
            @Override // java.lang.Runnable
            public void run() {
                T3HomeDeviceView.this.activity.getWindow().getDecorView().setSystemUiVisibility(0);
                T3HomeDeviceView.this.initViewSizeAndEvent();
            }
        }, 200L);
        refreshView();
        startCenterTimer();
        this.symbol = "zh_CN".equalsIgnoreCase(UserInforUtils.getLanguageLocaleString(CommonUtils.getAppContext())) ? "" : " ";
        T4BarChartView.ChartOnClickListener chartOnClickListener = new T4BarChartView.ChartOnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.widget.T3HomeDeviceView.5
            @Override // com.petkit.android.activities.petkitBleDevice.t4.widget.T4BarChartView.ChartOnClickListener
            public void onChartClick(final int i, final int i2, int i3) {
                T3HomeDeviceView.this.showDataDesc = true;
                T3HomeDeviceView.this.tvHistoryDate.setVisibility(8);
                T3HomeDeviceView.this.tvHistoryStatisticDesc.setVisibility(0);
                final RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) T3HomeDeviceView.this.tvHistoryStatisticDesc.getLayoutParams();
                if ((T3HomeDeviceView.this.statisticType != 1 || i >= 4) && (T3HomeDeviceView.this.statisticType != 2 || i >= 15)) {
                    T3HomeDeviceView.this.tvHistoryStatisticDesc.setBackground(T3HomeDeviceView.this.activity.getResources().getDrawable(R.drawable.t4_statistic_pet_toilet_desc_bg_right));
                } else {
                    T3HomeDeviceView.this.tvHistoryStatisticDesc.setBackground(T3HomeDeviceView.this.activity.getResources().getDrawable(R.drawable.t4_statistic_pet_toilet_desc));
                }
                T3HomeDeviceView.this.tvHistoryStatisticDesc.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.petkit.android.activities.petkitBleDevice.widget.T3HomeDeviceView.5.1
                    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                    public void onGlobalLayout() {
                        if ((T3HomeDeviceView.this.statisticType != 1 || i < 4) && (T3HomeDeviceView.this.statisticType != 2 || i < 15)) {
                            layoutParams2.setMargins(i2 - ArmsUtils.dip2px(T3HomeDeviceView.this.activity, 8.0f), layoutParams2.topMargin, 0, 0);
                            T3HomeDeviceView.this.tvHistoryStatisticDesc.setLayoutParams(layoutParams2);
                        } else {
                            layoutParams2.setMargins((i2 + ArmsUtils.dip2px(T3HomeDeviceView.this.activity, 8.0f)) - T3HomeDeviceView.this.tvHistoryStatisticDesc.getWidth(), layoutParams2.topMargin, 0, 0);
                            T3HomeDeviceView.this.tvHistoryStatisticDesc.setLayoutParams(layoutParams2);
                        }
                    }
                });
                T3StatisticInfo dataByPosition = T3HomeDeviceView.this.t3Histogram.getDataByPosition(i);
                T3HomeDeviceView.this.tvHistoryStatisticDesc.setText(String.format("%s %s %s", T4Utils.getFormatYMDDateFromString(dataByPosition.getStatisticDate()), String.format("%s" + T3HomeDeviceView.this.symbol + "%s", Integer.valueOf(dataByPosition.getPetTimes()), dataByPosition.getPetTimes() > 1 ? T3HomeDeviceView.this.activity.getResources().getString(R.string.Unit_times) : T3HomeDeviceView.this.activity.getResources().getString(R.string.Unit_time)), T3HomeDeviceView.this.totalTimeToStr(dataByPosition.getPetTotalTime())));
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t4.widget.T4BarChartView.ChartOnClickListener
            public void clearSelection() {
                if (T3HomeDeviceView.this.showDataDesc) {
                    if (T3HomeDeviceView.this.statisticType != 0) {
                        T3HomeDeviceView.this.tvHistoryDate.setVisibility(0);
                    }
                    T3HomeDeviceView.this.tvHistoryStatisticDesc.setVisibility(8);
                    T3HomeDeviceView.this.showDataDesc = false;
                }
            }
        };
        this.chartOnClickListener = chartOnClickListener;
        this.t3Histogram.setChartOnClickListener(chartOnClickListener);
        startProductAni();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopLeftAni() {
        for (int i = 0; i < this.animatorLeftSetList.size(); i++) {
            this.animatorLeftSetList.get(i).cancel();
        }
        this.animatorLeftSetList.clear();
        this.ivLeftArrow.setVisibility(8);
    }

    private void startCenterTimer() {
        this.mainHandler.postDelayed(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.widget.T3HomeDeviceView.6
            @Override // java.lang.Runnable
            public void run() {
                T3HomeDeviceView.this.vpT3Center.setCurrentItem(T3HomeDeviceView.this.vpT3Center.getCurrentItem() + 1);
                T3HomeDeviceView.this.mainHandler.postDelayed(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.widget.T3HomeDeviceView.6.1
                    @Override // java.lang.Runnable
                    public void run() {
                        T3HomeDeviceView.this.vpT3Center.setCurrentItem(T3HomeDeviceView.this.vpT3Center.getCurrentItem() + 1);
                    }
                }, 5000L);
            }
        }, 5000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @RequiresApi(api = 23)
    public void initViewSizeAndEvent() {
        int i = BaseApplication.displayMetrics.widthPixels;
        ViewGroup.LayoutParams layoutParams = this.t3DeviceCenterWhiteBg.getLayoutParams();
        float f = i;
        int i2 = (int) (this.widthScale * f);
        layoutParams.width = i2;
        layoutParams.height = (int) (i2 / 0.78f);
        this.t3DeviceCenterWhiteBg.setLayoutParams(layoutParams);
        ViewGroup.LayoutParams layoutParams2 = this.t3DeviceCenterProgress.getLayoutParams();
        int i3 = (int) (this.widthScale * f);
        layoutParams2.width = i3;
        layoutParams2.height = (int) (i3 / 0.9466f);
        this.t3DeviceCenterProgress.setLayoutParams(layoutParams2);
        ViewGroup.LayoutParams layoutParams3 = this.t3ViewDeviceDeodorant.getLayoutParams();
        int i4 = (int) (this.widthScale * f);
        layoutParams3.width = i4;
        layoutParams3.height = (int) (i4 / 0.78f);
        this.t3ViewDeviceDeodorant.setLayoutParams(layoutParams3);
        ViewGroup.LayoutParams layoutParams4 = this.t3ViewDeviceDeodorantRunning.getLayoutParams();
        int i5 = (int) (this.widthScale * f);
        layoutParams4.width = i5;
        layoutParams4.height = (int) (i5 / 0.78f);
        this.t3ViewDeviceDeodorantRunning.setLayoutParams(layoutParams4);
        ViewGroup.LayoutParams layoutParams5 = this.t3DeviceCenterMaskBg.getLayoutParams();
        int i6 = (int) (this.widthScale * f);
        layoutParams5.width = i6;
        layoutParams5.height = (int) (i6 / 0.78f);
        this.t3DeviceCenterMaskBg.setLayoutParams(layoutParams5);
        this.ivLackLitterBg.setLayoutParams(layoutParams5);
        this.ivLackLiquidBg.setLayoutParams(layoutParams5);
        this.t4ViewDeviceCenterBox.setLayoutParams(layoutParams5);
        RelativeLayout.LayoutParams layoutParams6 = (RelativeLayout.LayoutParams) this.rlLiquidNotInstall.getLayoutParams();
        layoutParams6.leftMargin = (int) ((layoutParams5.width / 190.0f) * 141.0f);
        layoutParams6.topMargin = (int) ((layoutParams5.height / 250.0f) * 170.0f);
        this.rlLiquidNotInstall.setLayoutParams(layoutParams6);
        RelativeLayout.LayoutParams layoutParams7 = (RelativeLayout.LayoutParams) this.rlBoxConsumablesInlet.getLayoutParams();
        layoutParams7.leftMargin = (int) ((layoutParams5.width / 190.0f) * 60.0f);
        layoutParams7.topMargin = (int) ((layoutParams5.height / 250.0f) * 225.0f);
        this.rlBoxConsumablesInlet.setLayoutParams(layoutParams7);
        RelativeLayout.LayoutParams layoutParams8 = (RelativeLayout.LayoutParams) this.rlLitterConsumablesInlet.getLayoutParams();
        layoutParams8.leftMargin = (int) ((layoutParams5.width / 190.0f) * 95.0f);
        layoutParams8.topMargin = (int) ((layoutParams5.height / 250.0f) * 135.0f);
        this.rlLitterConsumablesInlet.setLayoutParams(layoutParams8);
        RelativeLayout.LayoutParams layoutParams9 = (RelativeLayout.LayoutParams) this.rlK3ConsumablesInlet.getLayoutParams();
        layoutParams9.leftMargin = (int) ((layoutParams5.width / 190.0f) * 150.0f);
        layoutParams9.topMargin = (int) ((layoutParams5.height / 250.0f) * 180.0f);
        this.rlK3ConsumablesInlet.setLayoutParams(layoutParams9);
        RelativeLayout.LayoutParams layoutParams10 = (RelativeLayout.LayoutParams) this.t3HomeCenterPanel.getLayoutParams();
        float f2 = this.widthScale;
        int i7 = (int) (f * f2 * 0.33846f);
        layoutParams10.width = i7;
        layoutParams10.height = i7;
        layoutParams10.setMargins(layoutParams10.leftMargin, (int) (((f2 * f) / 0.78f) * 0.256f), layoutParams10.rightMargin, layoutParams10.bottomMargin);
        this.t3HomeCenterPanel.setLayoutParams(layoutParams10);
        RelativeLayout.LayoutParams layoutParams11 = (RelativeLayout.LayoutParams) this.vpT3Center.getLayoutParams();
        float f3 = this.widthScale;
        int i8 = (int) (f * f3 * 0.33846f);
        layoutParams11.width = i8;
        layoutParams11.height = i8;
        layoutParams11.setMargins(layoutParams11.leftMargin, (int) (((f3 * f) / 0.78f) * 0.256f), layoutParams11.rightMargin, layoutParams11.bottomMargin);
        this.vpT3Center.setLayoutParams(layoutParams11);
        RelativeLayout.LayoutParams layoutParams12 = (RelativeLayout.LayoutParams) this.t3DeviceScan.getLayoutParams();
        float f4 = this.widthScale;
        layoutParams12.width = (int) (f * f4 * 0.4246f);
        layoutParams12.height = -2;
        layoutParams12.setMargins(layoutParams12.leftMargin, (int) (((f * f4) / 0.78f) * 0.148f), layoutParams12.rightMargin, layoutParams12.bottomMargin);
        this.t3DeviceScan.setLayoutParams(layoutParams12);
        RelativeLayout.LayoutParams layoutParams13 = (RelativeLayout.LayoutParams) this.ivT3ErrorIcon.getLayoutParams();
        layoutParams13.width = layoutParams10.width / 2;
        int i9 = layoutParams10.width;
        layoutParams13.height = i9 / 2;
        layoutParams13.setMargins(layoutParams13.leftMargin, i9 / 4, layoutParams13.rightMargin, layoutParams13.bottomMargin);
        this.ivT3ErrorIcon.setLayoutParams(layoutParams13);
        this.rlTimePanel.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.petkit.android.activities.petkitBleDevice.widget.T3HomeDeviceView.7
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                T3HomeDeviceView.this.mainHandler.postDelayed(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.widget.T3HomeDeviceView.7.1
                    @Override // java.lang.Runnable
                    public void run() {
                        T3HomeDeviceView.this.isReady = true;
                        if (T3HomeDeviceView.this.isNeedShowPop) {
                            T3HomeDeviceView.this.showPopWindow();
                        }
                    }
                }, 200L);
            }
        });
        this.rlTopView.setOnTouchListener(new View.OnTouchListener() { // from class: com.petkit.android.activities.petkitBleDevice.widget.T3HomeDeviceView.8
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                T3HomeDeviceView.this.mVelocityTracker.addMovement(motionEvent);
                int action = motionEvent.getAction();
                if (action == 0) {
                    T3HomeDeviceView.this.tempY = motionEvent.getY();
                    Log.d("resOffset", "ACTION_DOWN:tempY:" + T3HomeDeviceView.this.tempY);
                } else if (action == 1) {
                    Log.d("resOffset", "ACTION_UP");
                    T3HomeDeviceView.this.mVelocityTracker.computeCurrentVelocity(1000);
                    Log.d("resOffset", "xVelocity:" + T3HomeDeviceView.this.mVelocityTracker.getXVelocity() + " yVelocity:" + T3HomeDeviceView.this.mVelocityTracker.getYVelocity());
                } else if (action == 2) {
                    float y = motionEvent.getY();
                    float unused = T3HomeDeviceView.this.tempY;
                    T3HomeDeviceView.this.tempY = y;
                }
                return true;
            }
        });
    }

    public void setT3Record(T3Record t3Record) {
        this.t3Record = t3Record;
        initPetList(t3Record);
        this.familyInfor = FamilyUtils.getInstance().getFamilyInforThroughDevice(getContext(), t3Record.getDeviceId(), 7);
        refreshView();
    }

    private void initPetList(T3Record t3Record) {
        try {
            this.thisDeviceFamilyPetList = FamilyUtils.getInstance().getCatListByFamilyId(this.activity, FamilyUtils.getInstance().getFamilyInforThroughDevice(this.activity, t3Record.getDeviceId(), 7).getGroupId());
        } catch (Exception e) {
            PetkitLog.d("t3 getDevicePet error :" + e.getMessage());
            LogcatStorageHelper.addLog("t3 getDevicePet error :" + e.getMessage());
        }
        if (this.thisDeviceFamilyPetList == null) {
            this.thisDeviceFamilyPetList = new ArrayList();
        }
    }

    private void refreshView() {
        T3Record t3Record = this.t3Record;
        if (t3Record == null) {
            return;
        }
        if (t3Record.getDeviceShared() != null) {
            this.llPets.setVisibility(8);
        } else {
            this.llPets.setVisibility(0);
        }
        this.isInDisturbTime = false;
        this.llAccessoryConsumables.setVisibility(TextUtils.isEmpty(BleDeviceUtils.getPurchaseEntranceConsumaUrl(7, new int[0])) ? 8 : 0);
        this.tvPopContent.setText(getResources().getString(R.string.Check_cat_prompt, String.valueOf(this.t3Record.getPetInTipLimit())));
        Context context = this.mContext;
        StringBuilder sb = new StringBuilder();
        sb.append(Consts.T3_BOX_INLET_ISVISIBLE);
        sb.append(this.t3Record.getDeviceId());
        this.tvBoxConsumablesInlet.setVisibility(CommonUtils.getSysBoolMap(context, sb.toString(), true) ? 0 : 8);
        Context context2 = this.mContext;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(Consts.T3_LITTER_INLET_ISVISIBLE);
        sb2.append(this.t3Record.getDeviceId());
        this.tvLitterConsumablesInlet.setVisibility(CommonUtils.getSysBoolMap(context2, sb2.toString(), true) ? 0 : 8);
        Context context3 = this.mContext;
        StringBuilder sb3 = new StringBuilder();
        sb3.append(Consts.T3_LIQUID_INLET_ISVISIBLE);
        sb3.append(this.t3Record.getDeviceId());
        this.tvK3ConsumablesInlet.setVisibility(CommonUtils.getSysBoolMap(context3, sb3.toString(), true) ? 0 : 8);
        this.rlLiquidNotInstall.setVisibility(this.t3Record.getState().getSprayModule() == 1 ? 0 : 8);
        TextView textView = this.tvMore;
        this.t3Record.getDeviceShared();
        textView.setVisibility(8);
        if (this.t3Record.getState() != null && this.t3Record.getState().getOverall() == 2) {
            checkDeviceWarnState();
            setConsumablesInletGone();
            cancelAllAnimationAndTimer();
            this.llT3BtnPanel.setVisibility(4);
            this.rlT3CenterText.setVisibility(0);
            this.vpT3Center.setVisibility(8);
            this.ivT3ErrorIcon.setVisibility(8);
            this.tvT3DeviceState.setVisibility(0);
            this.llWarnPanel.setVisibility(0);
            this.tvT3DeviceState.setText(this.mContext.getString(R.string.Device_off_line_tip) + " >");
            this.tvT3DeviceState.setTextColor(this.mContext.getResources().getColor(R.color.feeder_plan_save_black));
            this.tvT3DeviceState.setBackgroundResource(R.drawable.selector_k2_white_with_radius_shadow);
            this.tvT3DeviceState.setPadding(ArmsUtils.dip2px(this.mContext, 14.0f), ArmsUtils.dip2px(this.mContext, 8.0f), ArmsUtils.dip2px(this.mContext, 14.0f), ArmsUtils.dip2px(this.mContext, 8.0f));
            this.isUnMask = false;
            this.bleMenuView.changeAllMask(true);
            this.bleMenuView.changeAllMenuBtnState(false);
            this.t3TimeView.setTimeViewColor(this.mContext.getResources().getColor(R.color.edit_text), this.mContext.getResources().getColor(R.color.t3_home_bg_gray), this.mContext.getResources().getColor(R.color.walk_text));
            this.tvTimeContent.setTextColor(this.mContext.getResources().getColor(R.color.walk_text));
            this.tvT3CenterText.setText("-");
            this.tvT3CenterText.setTextColor(this.mContext.getResources().getColor(R.color.walk_text));
            this.tvT3CenterBottom.setText(this.mContext.getResources().getString(R.string.Cat_litter));
            this.rlPop.setVisibility(8);
        } else if (this.t3Record.getState() != null && this.t3Record.getState().getOverall() == 4) {
            setConsumablesInletGone();
            cancelAllAnimationAndTimer();
            this.rlT3CenterText.setVisibility(8);
            this.llT3BtnPanel.setVisibility(4);
            this.vpT3Center.setVisibility(8);
            this.ivT3ErrorIcon.setVisibility(0);
            this.tvT3DeviceState.setVisibility(0);
            this.tvT3DeviceState.setText(this.t3Record.getState().getErrorMsg() + " >");
            this.tvT3DeviceState.setTextColor(this.mContext.getResources().getColor(R.color.feeder_plan_save_black));
            this.tvT3DeviceState.setBackgroundResource(R.drawable.selector_k2_white_with_radius_shadow);
            this.tvT3DeviceState.setPadding(ArmsUtils.dip2px(this.mContext, 14.0f), ArmsUtils.dip2px(this.mContext, 8.0f), ArmsUtils.dip2px(this.mContext, 14.0f), ArmsUtils.dip2px(this.mContext, 8.0f));
            this.t3TimeView.setTimeViewColor(this.mContext.getResources().getColor(R.color.t3_main_blue), this.mContext.getResources().getColor(R.color.t3_home_bg_gray), this.mContext.getResources().getColor(R.color.walk_text));
            this.tvTimeContent.setTextColor(this.mContext.getResources().getColor(R.color.t3_home_data_blue));
            if (this.t3Record.getState().getPower() == 1) {
                this.bleMenuView.changeAllMenuBtnState(true);
            } else {
                this.bleMenuView.changeMenuBtnState(0);
            }
            this.llWarnPanel.setVisibility(0);
            checkDeviceWarnState();
            if (this.t3Record.getState().getErrorLevel() == 1) {
                this.bleMenuView.changeSingleMaskVisible(0);
            } else {
                this.isUnMask = true;
                this.bleMenuView.changeAllMask(false);
            }
            if (this.t3Record.getState().isBoxFull()) {
                startBoxAnimation();
            }
            if (0 != this.t3Record.getState().getPetInTime()) {
                long jCurrentTimeMillis = (System.currentTimeMillis() - (this.t3Record.getState().getPetInTime() * 1000)) / 1000;
                int i = (int) (jCurrentTimeMillis / 3600);
                long j = jCurrentTimeMillis % 3600;
                int i2 = (int) (j / 60);
                int i3 = (int) (j % 60);
                if (i > 0) {
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append(i);
                    sb4.append(this.mContext.getResources().getString(R.string.Unit_hour));
                }
                if (i2 > 0) {
                    StringBuilder sb5 = new StringBuilder();
                    sb5.append(i2);
                    sb5.append(this.mContext.getResources().getString(R.string.Unit_minutes));
                }
                if (i3 > 0) {
                    StringBuilder sb6 = new StringBuilder();
                    sb6.append(i3);
                    sb6.append(this.mContext.getResources().getString(R.string.Unit_seconds));
                }
                this.tvT3DeviceState.setVisibility(0);
                if (this.t3Record.getState().isPetError()) {
                    String string = this.mContext.getResources().getString(R.string.No_cat);
                    SpannableString spannableString = new SpannableString(this.mContext.getResources().getString(R.string.Pet_into_prompt) + ",\n" + string);
                    spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.t4_main_blue)), spannableString.toString().indexOf(string), spannableString.toString().indexOf(string) + string.length(), 18);
                    spannableString.setSpan(new ClickableSpan() { // from class: com.petkit.android.activities.petkitBleDevice.widget.T3HomeDeviceView.9
                        @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
                        public void updateDrawState(TextPaint textPaint) {
                            super.updateDrawState(textPaint);
                            textPaint.setColor(T3HomeDeviceView.this.getResources().getColor(R.color.t4_main_blue));
                            textPaint.setUnderlineText(true);
                        }

                        @Override // android.text.style.ClickableSpan
                        public void onClick(View view) {
                            T3HomeDeviceView.this.showTipWindow();
                        }
                    }, spannableString.toString().indexOf(string), spannableString.toString().indexOf(string) + string.length(), 33);
                    this.tvT3DeviceState.setHighlightColor(0);
                    this.tvT3DeviceState.setText(spannableString);
                    this.tvT3DeviceState.setMovementMethod(LinkMovementMethod.getInstance());
                    showPopWindow();
                } else {
                    this.rlPop.setVisibility(8);
                    this.tvT3DeviceState.setText(this.mContext.getResources().getString(R.string.Pet_into_prompt));
                    PopupWindow popupWindow = this.popupWindow;
                    if (popupWindow != null) {
                        popupWindow.dismiss();
                        this.popupWindow = null;
                    }
                }
            } else {
                this.rlPop.setVisibility(8);
            }
        } else if (this.t3Record.getState() != null && this.t3Record.getState().getOverall() == 3) {
            setConsumablesInletVisible();
            cancelAllAnimationAndTimer();
            this.llT3BtnPanel.setVisibility(4);
            this.ivT3ErrorIcon.setVisibility(8);
            this.llWarnPanel.setVisibility(0);
            this.isUnMask = false;
            this.bleMenuView.changeAllMask(true);
            this.bleMenuView.changeAllMenuBtnState(true);
            this.rlPop.setVisibility(8);
            checkDeviceWarnState();
            if (this.t3Record.getLastOutTime() != null) {
                this.rlT3CenterText.setVisibility(0);
                this.vpT3Center.setVisibility(8);
                Integer lastOutTime = this.t3Record.getLastOutTime();
                lastOutTime.intValue();
                this.tvT3CenterText.setText(TimeUtils.getInstance().secondsToTimeStringWithUnit(this.mContext, lastOutTime.intValue() * 60));
                this.tvT3CenterText.setTextColor(getResources().getColor(R.color.t3_main_blue));
                if (!TimeUtils.getInstance().is24HourSystem()) {
                    this.tvT3CenterText.setTextSize(1, 18.0f);
                } else {
                    this.tvT3CenterText.setTextSize(1, 22.0f);
                }
                this.tvT3CenterBottom.setText(this.mContext.getResources().getString(R.string.Recent_time));
            } else {
                this.rlT3CenterText.setVisibility(8);
                this.vpT3Center.setVisibility(8);
            }
            if (this.t3Record.getState().isBoxFull()) {
                startBoxAnimation();
                this.tvT3DeviceState.setVisibility(0);
                this.tvT3DeviceState.setText(this.t3Record.getState().getErrorMsg());
            } else {
                stopBoxAnimation();
                this.tvT3DeviceState.setVisibility(8);
            }
            if (this.t3Record.getDeviceShared() != null) {
                this.tvT3DeviceState.setText(getResources().getString(R.string.Device_ota_prompt));
            } else {
                this.tvT3DeviceState.setText(getResources().getString(R.string.Device_ota_prompt) + " >");
            }
            this.tvT3DeviceState.setVisibility(0);
            if (!TextUtils.isEmpty(this.t3Record.getState().getErrorCode()) && !"full".equals(this.t3Record.getState().getErrorCode())) {
                this.tvT3DeviceState.setVisibility(0);
                this.tvT3DeviceState.setText(this.t3Record.getState().getErrorMsg() + " >");
            }
            this.tvT3DeviceState.setTextColor(this.mContext.getResources().getColor(R.color.feeder_plan_save_black));
            this.tvT3DeviceState.setBackgroundResource(R.drawable.selector_k2_white_with_radius_shadow);
            this.tvT3DeviceState.setPadding(ArmsUtils.dip2px(this.mContext, 14.0f), ArmsUtils.dip2px(this.mContext, 8.0f), ArmsUtils.dip2px(this.mContext, 14.0f), ArmsUtils.dip2px(this.mContext, 8.0f));
        } else if (this.t3Record.getState() != null && this.t3Record.getState().getPower() == 0) {
            checkDeviceWarnState();
            setConsumablesInletGone();
            stopDeodorantAnimation();
            stopCounterclockwiseRotateAnimation();
            stopClockwiseRotateAnimation();
            this.rlPop.setVisibility(8);
            startScanAnimation();
            this.llT3BtnPanel.setVisibility(4);
            this.rlT3CenterText.setVisibility(0);
            this.vpT3Center.setVisibility(8);
            this.ivT3ErrorIcon.setVisibility(8);
            this.tvT3DeviceState.setVisibility(8);
            this.t3TimeView.setTimeViewColor(this.mContext.getResources().getColor(R.color.edit_text), this.mContext.getResources().getColor(R.color.t3_home_bg_gray), this.mContext.getResources().getColor(R.color.walk_text));
            this.t3TimeView.setBgColor(R.color.transparent);
            this.tvTimeContent.setTextColor(this.mContext.getResources().getColor(R.color.walk_text));
            this.tvT3CenterBottom.setText(this.mContext.getResources().getString(R.string.Cat_litter));
            this.bleMenuView.changeSingleMaskVisible(0);
            this.bleMenuView.changeMenuBtnState(0);
            this.llWarnPanel.setVisibility(0);
            if (this.t3Record.getLastOutTime() != null) {
                this.rlT3CenterText.setVisibility(0);
                this.vpT3Center.setVisibility(8);
                Integer lastOutTime2 = this.t3Record.getLastOutTime();
                lastOutTime2.intValue();
                this.tvT3CenterText.setText(TimeUtils.getInstance().secondsToTimeStringWithUnit(this.mContext, lastOutTime2.intValue() * 60));
                this.tvT3CenterText.setTextColor(getResources().getColor(R.color.t3_main_blue));
                if (!TimeUtils.getInstance().is24HourSystem()) {
                    this.tvT3CenterText.setTextSize(1, 18.0f);
                } else {
                    this.tvT3CenterText.setTextSize(1, 22.0f);
                }
                this.tvT3CenterBottom.setText(this.mContext.getResources().getString(R.string.Recent_time));
            } else {
                this.rlT3CenterText.setVisibility(8);
                this.vpT3Center.setVisibility(8);
            }
            if (this.t3Record.getState().isBoxFull()) {
                startBoxAnimation();
                this.tvT3DeviceState.setVisibility(0);
                this.tvT3DeviceState.setText(this.t3Record.getState().getErrorMsg());
            } else {
                stopBoxAnimation();
                if (Double.parseDouble(this.t3Record.getFirmware()) >= 1.439d) {
                    if (this.t3Record.getSettings().getDisturbMode() == 1 && BleDeviceUtils.isInDisturbTimeNew(this.t3Record)) {
                        this.tvT3DeviceState.setVisibility(0);
                        this.tvT3DeviceState.setText(this.activity.getResources().getString(R.string.Disturb_mode_start_stop_clean));
                        this.isInDisturbTime = true;
                    } else {
                        this.tvT3DeviceState.setVisibility(8);
                    }
                } else if (this.t3Record.getSettings().getDisturbMode() == 1 && BleDeviceUtils.isInDisturbTime(this.t3Record)) {
                    this.tvT3DeviceState.setVisibility(0);
                    this.tvT3DeviceState.setText(this.activity.getResources().getString(R.string.Disturb_mode_start_stop_clean));
                    this.isInDisturbTime = true;
                } else {
                    this.tvT3DeviceState.setVisibility(8);
                }
            }
            if (!TextUtils.isEmpty(this.t3Record.getState().getErrorCode()) && !"full".equals(this.t3Record.getState().getErrorCode())) {
                this.tvT3DeviceState.setVisibility(0);
                this.tvT3DeviceState.setText(this.t3Record.getState().getErrorMsg() + " >");
            }
        } else if (this.t3Record.getState() != null && this.t3Record.getState().getWorkState() == null) {
            setConsumablesInletVisible();
            this.tvTimeContent.setTextColor(this.mContext.getResources().getColor(R.color.t3_home_data_blue));
            this.t3TimeView.setTimeViewColor(this.mContext.getResources().getColor(R.color.t3_main_blue), this.mContext.getResources().getColor(R.color.t3_home_bg_gray), this.mContext.getResources().getColor(R.color.walk_text));
            stopCounterclockwiseRotateAnimation();
            stopClockwiseRotateAnimation();
            startScanAnimation();
            this.ivT3ErrorIcon.setVisibility(8);
            this.tvT3DeviceState.setVisibility(8);
            this.isUnMask = true;
            this.bleMenuView.changeAllMask(false);
            this.bleMenuView.changeAllMenuBtnState(true);
            this.llWarnPanel.setVisibility(0);
            this.llT3BtnPanel.setVisibility(4);
            checkDeviceWarnState();
            if (this.t3Record.getLastOutTime() != null) {
                this.rlT3CenterText.setVisibility(0);
                Integer lastOutTime3 = this.t3Record.getLastOutTime();
                lastOutTime3.intValue();
                this.tvT3CenterText.setText(TimeUtils.getInstance().secondsToTimeStringWithUnit(this.mContext, lastOutTime3.intValue() * 60));
                this.tvT3CenterText.setTextColor(getResources().getColor(R.color.t3_main_blue));
                if (!TimeUtils.getInstance().is24HourSystem()) {
                    this.tvT3CenterText.setTextSize(1, 18.0f);
                } else {
                    this.tvT3CenterText.setTextSize(1, 22.0f);
                }
                this.tvT3CenterBottom.setText(this.mContext.getResources().getString(R.string.Recent_time));
            } else {
                this.rlT3CenterText.setVisibility(8);
            }
            if (this.t3Record.getState().getRefreshState() != null && 1 == this.t3Record.getState().getRefreshState().getWorkProcess()) {
                startDeodorantAnimation();
                this.tvT3DeviceState.setText(this.mContext.getResources().getString(R.string.Device_deodorizing_prompt));
                this.tvT3DeviceState.setVisibility(0);
                this.tvT3DeviceState.setBackgroundResource(R.drawable.selector_k2_white_with_radius_shadow);
                this.tvT3DeviceState.setTextColor(this.mContext.getResources().getColor(R.color.feeder_plan_save_black));
                this.tvT3DeviceState.setPadding(ArmsUtils.dip2px(this.mContext, 14.0f), ArmsUtils.dip2px(this.mContext, 8.0f), ArmsUtils.dip2px(this.mContext, 14.0f), ArmsUtils.dip2px(this.mContext, 8.0f));
            } else {
                stopDeodorantAnimation();
                this.tvT3DeviceState.setVisibility(8);
            }
            if (this.t3Record.getState().isBoxFull()) {
                startBoxAnimation();
                this.tvT3DeviceState.setVisibility(0);
                this.tvT3DeviceState.setText(this.t3Record.getState().getErrorMsg());
            } else {
                stopBoxAnimation();
                if (this.t3Record.getFirmware() != null && Double.parseDouble(this.t3Record.getFirmware()) >= 1.439d) {
                    if (this.t3Record.getSettings().getDisturbMode() == 1 && BleDeviceUtils.isInDisturbTimeNew(this.t3Record)) {
                        this.tvT3DeviceState.setVisibility(0);
                        this.tvT3DeviceState.setText(this.activity.getResources().getString(R.string.Disturb_mode_start_stop_clean));
                        this.isInDisturbTime = true;
                    } else if (this.t3Record.getState().getRefreshState() != null && 1 == this.t3Record.getState().getRefreshState().getWorkProcess()) {
                        this.tvT3DeviceState.setVisibility(0);
                    } else {
                        this.tvT3DeviceState.setVisibility(8);
                    }
                } else if (this.t3Record.getSettings().getDisturbMode() == 1 && BleDeviceUtils.isInDisturbTime(this.t3Record)) {
                    this.tvT3DeviceState.setVisibility(0);
                    this.tvT3DeviceState.setText(this.activity.getResources().getString(R.string.Disturb_mode_start_stop_clean));
                    this.isInDisturbTime = true;
                } else if (this.t3Record.getState().getRefreshState() != null && 1 == this.t3Record.getState().getRefreshState().getWorkProcess()) {
                    this.tvT3DeviceState.setVisibility(0);
                } else {
                    this.tvT3DeviceState.setVisibility(8);
                }
            }
            if (!TextUtils.isEmpty(this.t3Record.getState().getErrorCode()) && !"full".equals(this.t3Record.getState().getErrorCode())) {
                this.tvT3DeviceState.setVisibility(0);
                this.tvT3DeviceState.setText(this.t3Record.getState().getErrorMsg() + " >");
            }
            if (0 != this.t3Record.getState().getPetInTime()) {
                long jCurrentTimeMillis2 = (System.currentTimeMillis() - (this.t3Record.getState().getPetInTime() * 1000)) / 1000;
                int i4 = (int) (jCurrentTimeMillis2 / 3600);
                long j2 = jCurrentTimeMillis2 % 3600;
                int i5 = (int) (j2 / 60);
                int i6 = (int) (j2 % 60);
                if (i4 > 0) {
                    StringBuilder sb7 = new StringBuilder();
                    sb7.append(i4);
                    sb7.append(this.mContext.getResources().getString(R.string.Unit_hour));
                }
                if (i5 > 0) {
                    StringBuilder sb8 = new StringBuilder();
                    sb8.append(i5);
                    sb8.append(this.mContext.getResources().getString(R.string.Unit_minutes));
                }
                if (i6 > 0) {
                    StringBuilder sb9 = new StringBuilder();
                    sb9.append(i6);
                    sb9.append(this.mContext.getResources().getString(R.string.Unit_seconds));
                }
                this.tvT3DeviceState.setVisibility(0);
                if (this.t3Record.getState().isPetError()) {
                    String string2 = this.mContext.getResources().getString(R.string.No_cat);
                    SpannableString spannableString2 = new SpannableString(this.mContext.getResources().getString(R.string.Pet_into_prompt) + ",\n" + string2);
                    spannableString2.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.t4_main_blue)), spannableString2.toString().indexOf(string2), spannableString2.toString().indexOf(string2) + string2.length(), 18);
                    spannableString2.setSpan(new ClickableSpan() { // from class: com.petkit.android.activities.petkitBleDevice.widget.T3HomeDeviceView.10
                        @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
                        public void updateDrawState(TextPaint textPaint) {
                            super.updateDrawState(textPaint);
                            textPaint.setColor(T3HomeDeviceView.this.getResources().getColor(R.color.t4_main_blue));
                            textPaint.setUnderlineText(true);
                        }

                        @Override // android.text.style.ClickableSpan
                        public void onClick(View view) {
                            T3HomeDeviceView.this.showTipWindow();
                            if (T3HomeDeviceView.this.popupWindow != null) {
                                T3HomeDeviceView.this.popupWindow.dismiss();
                                T3HomeDeviceView.this.popupWindow = null;
                            }
                        }
                    }, spannableString2.toString().indexOf(string2), spannableString2.toString().indexOf(string2) + string2.length(), 33);
                    this.tvT3DeviceState.setHighlightColor(0);
                    this.tvT3DeviceState.setText(spannableString2);
                    this.tvT3DeviceState.setMovementMethod(LinkMovementMethod.getInstance());
                    showPopWindow();
                } else {
                    this.rlPop.setVisibility(8);
                    this.tvT3DeviceState.setText(this.mContext.getResources().getString(R.string.Pet_into_prompt));
                    PopupWindow popupWindow2 = this.popupWindow;
                    if (popupWindow2 != null) {
                        popupWindow2.dismiss();
                        this.popupWindow = null;
                    }
                }
            } else {
                this.rlPop.setVisibility(8);
            }
            this.tvT3DeviceState.setBackgroundResource(R.drawable.selector_k2_white_with_radius_shadow);
            this.tvT3DeviceState.setTextColor(this.mContext.getResources().getColor(R.color.feeder_plan_save_black));
            this.tvT3DeviceState.setPadding(ArmsUtils.dip2px(this.mContext, 14.0f), ArmsUtils.dip2px(this.mContext, 8.0f), ArmsUtils.dip2px(this.mContext, 14.0f), ArmsUtils.dip2px(this.mContext, 8.0f));
        } else if (this.t3Record.getState() != null && this.t3Record.getState().getWorkState() != null) {
            checkDeviceWarnState();
            setConsumablesInletGone();
            this.tvTimeContent.setTextColor(this.mContext.getResources().getColor(R.color.t3_home_data_blue));
            this.t3TimeView.setTimeViewColor(this.mContext.getResources().getColor(R.color.t3_main_blue), this.mContext.getResources().getColor(R.color.t3_home_bg_gray), this.mContext.getResources().getColor(R.color.walk_text));
            stopScanAnimation();
            this.ivT3ErrorIcon.setVisibility(8);
            this.llWarnPanel.setVisibility(0);
            if (this.t3Record.getLastOutTime() != null) {
                this.rlT3CenterText.setVisibility(0);
                this.vpT3Center.setVisibility(8);
                Integer lastOutTime4 = this.t3Record.getLastOutTime();
                lastOutTime4.intValue();
                this.tvT3CenterText.setText(TimeUtils.getInstance().secondsToTimeStringWithUnit(this.mContext, lastOutTime4.intValue() * 60));
                this.tvT3CenterText.setTextColor(getResources().getColor(R.color.t3_main_blue));
                if (!TimeUtils.getInstance().is24HourSystem()) {
                    this.tvT3CenterText.setTextSize(1, 18.0f);
                } else {
                    this.tvT3CenterText.setTextSize(1, 22.0f);
                }
                this.tvT3CenterBottom.setText(this.mContext.getResources().getString(R.string.Recent_time));
            } else {
                this.rlT3CenterText.setVisibility(8);
                this.vpT3Center.setVisibility(8);
            }
            this.bleMenuView.changeAllMenuBtnState(true);
            this.isUnMask = false;
            this.bleMenuView.changeAllMask(true);
            this.tvT3DeviceState.setVisibility(0);
            this.llT3BtnPanel.setVisibility(0);
            if (1 == this.t3Record.getState().getWorkState().getWorkProcess() / 10) {
                this.tvT3DeviceState.setBackgroundResource(R.drawable.selector_t3_blue_with_radius_shadow);
                this.btnT3TerminateClean.setVisibility(8);
                this.btnT3PauseClean.setVisibility(0);
                this.btnT3ContinueToRun.setVisibility(8);
                this.btnT3PauseClean.setTextColor(this.mContext.getResources().getColor(R.color.t3_prompt_gray));
                this.btnT3PauseClean.setBackgroundResource(R.drawable.solid_t3_home_gray);
                if (this.t3Record.getState().getWorkState().getWorkMode() == 1 || this.t3Record.getState().getWorkState().getWorkMode() == 5) {
                    if (this.clockwiseRotateAnimator == null) {
                        startClockwiseRotateAnimation(360.0f);
                    } else {
                        resumeClockwiseRotateAnimation();
                    }
                } else if (this.counterclockwiseRotateAnimator == null) {
                    startCounterclockwiseRotateAnimation(360.0f);
                } else {
                    resumeCounterclockwiseRotateAnimation();
                }
                int workMode = this.t3Record.getState().getWorkState().getWorkMode();
                if (workMode == 0) {
                    this.tvT3DeviceState.setText(this.mContext.getResources().getString(R.string.Cleaning_cat_litter));
                } else if (workMode == 1) {
                    this.tvT3DeviceState.setText(this.mContext.getResources().getString(R.string.Dumping_cat_litter));
                } else if (workMode == 3) {
                    this.tvT3DeviceState.setText(this.mContext.getResources().getString(R.string.Device_reset_prompt));
                } else if (workMode == 4) {
                    this.tvT3DeviceState.setText(this.mContext.getResources().getString(R.string.Smoothing_cat_litter));
                } else if (workMode == 5) {
                    this.tvT3DeviceState.setText(this.mContext.getResources().getString(R.string.Device_calibration_prompt));
                }
                this.tvT3DeviceState.setTextColor(this.mContext.getResources().getColor(R.color.t3_main_blue));
                this.tvT3DeviceState.setBackgroundResource(R.drawable.selector_t3_blue_with_radius_shadow);
                this.tvT3DeviceState.setPadding(ArmsUtils.dip2px(this.mContext, 14.0f), ArmsUtils.dip2px(this.mContext, 8.0f), ArmsUtils.dip2px(this.mContext, 14.0f), ArmsUtils.dip2px(this.mContext, 8.0f));
                this.rlPop.setVisibility(8);
            } else if (2 == this.t3Record.getState().getWorkState().getWorkProcess() / 10) {
                if (2 == this.t3Record.getState().getWorkState().getWorkProcess() % 10) {
                    this.btnT3PauseClean.setVisibility(8);
                    this.btnT3ContinueToRun.setVisibility(4);
                    this.btnT3TerminateClean.setVisibility(4);
                    int safeWarn = this.t3Record.getState().getWorkState().getSafeWarn();
                    if (safeWarn != 0) {
                        if (safeWarn == 1) {
                            this.rlPop.setVisibility(8);
                            this.tvT3DeviceState.setText(this.mContext.getResources().getString(R.string.Safe_warn_atp));
                        } else {
                            this.rlPop.setVisibility(8);
                            this.tvT3DeviceState.setText(this.mContext.getResources().getString(R.string.Safe_warn_general));
                        }
                    } else if (this.t3Record.getState().getPetInTime() == 0) {
                        this.tvT3DeviceState.setText(this.mContext.getResources().getString(R.string.Device_safety_testing));
                        this.rlPop.setVisibility(8);
                    } else if (this.t3Record.getState().isPetError()) {
                        String string3 = this.mContext.getResources().getString(R.string.No_cat);
                        SpannableString spannableString3 = new SpannableString(this.mContext.getResources().getString(R.string.Pet_go_into_device) + ",\n" + string3);
                        spannableString3.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.t4_main_blue)), spannableString3.toString().indexOf(string3), spannableString3.toString().indexOf(string3) + string3.length(), 18);
                        spannableString3.setSpan(new ClickableSpan() { // from class: com.petkit.android.activities.petkitBleDevice.widget.T3HomeDeviceView.11
                            @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
                            public void updateDrawState(TextPaint textPaint) {
                                super.updateDrawState(textPaint);
                                textPaint.setColor(T3HomeDeviceView.this.getResources().getColor(R.color.t4_main_blue));
                                textPaint.setUnderlineText(true);
                            }

                            @Override // android.text.style.ClickableSpan
                            public void onClick(View view) {
                                T3HomeDeviceView.this.showTipWindow();
                            }
                        }, spannableString3.toString().indexOf(string3), spannableString3.toString().indexOf(string3) + string3.length(), 33);
                        this.tvT3DeviceState.setHighlightColor(0);
                        this.tvT3DeviceState.setText(spannableString3);
                        this.tvT3DeviceState.setMovementMethod(LinkMovementMethod.getInstance());
                        showPopWindow();
                    } else {
                        this.rlPop.setVisibility(8);
                        this.tvT3DeviceState.setText(this.mContext.getResources().getString(R.string.Pet_go_into_device));
                        PopupWindow popupWindow3 = this.popupWindow;
                        if (popupWindow3 != null) {
                            popupWindow3.dismiss();
                            this.popupWindow = null;
                        }
                    }
                    if (this.t3Record.getState().getWorkState().getWorkMode() == 1 || this.t3Record.getState().getWorkState().getWorkMode() == 5) {
                        startClockwiseRotateAnimation(360.0f);
                        pauseClockwiseRotateAnimation();
                    } else {
                        startCounterclockwiseRotateAnimation(360.0f);
                        pauseCounterclockwiseRotateAnimation();
                    }
                    this.tvT3DeviceState.setTextColor(this.mContext.getResources().getColor(R.color.t3_main_blue));
                    this.tvT3DeviceState.setBackgroundResource(R.drawable.selector_t3_blue_with_radius_shadow);
                    this.tvT3DeviceState.setPadding(ArmsUtils.dip2px(this.mContext, 14.0f), ArmsUtils.dip2px(this.mContext, 8.0f), ArmsUtils.dip2px(this.mContext, 14.0f), ArmsUtils.dip2px(this.mContext, 8.0f));
                } else {
                    this.rlPop.setVisibility(8);
                    this.btnT3PauseClean.setVisibility(8);
                    this.btnT3ContinueToRun.setVisibility(0);
                    this.btnT3TerminateClean.setVisibility(0);
                    this.btnT3TerminateClean.setTextColor(this.mContext.getResources().getColor(R.color.t3_main_blue));
                    this.btnT3ContinueToRun.setTextColor(this.mContext.getResources().getColor(R.color.t3_main_blue));
                    this.btnT3ContinueToRun.setBackgroundResource(R.drawable.solid_t3_home_blue);
                    this.btnT3TerminateClean.setBackgroundResource(R.drawable.solid_t3_home_blue);
                    this.tvT3DeviceState.setText(this.mContext.getResources().getString(R.string.Device_pause_prompt, String.valueOf((this.t3Record.getState().getWorkState().getStopTime() > 0 ? this.t3Record.getState().getWorkState().getStopTime() : this.t3Record.getSettings().getStopTime()) / 60)));
                    if (this.t3Record.getState().getWorkState().getWorkMode() == 1 || this.t3Record.getState().getWorkState().getWorkMode() == 5) {
                        startClockwiseRotateAnimation(360.0f);
                        pauseClockwiseRotateAnimation();
                    } else {
                        startCounterclockwiseRotateAnimation(360.0f);
                        pauseCounterclockwiseRotateAnimation();
                    }
                    this.tvT3DeviceState.setTextColor(this.mContext.getResources().getColor(R.color.feeder_plan_save_black));
                    this.tvT3DeviceState.setBackgroundResource(R.drawable.selector_k2_white_with_radius_shadow);
                    this.tvT3DeviceState.setPadding(ArmsUtils.dip2px(this.mContext, 14.0f), ArmsUtils.dip2px(this.mContext, 8.0f), ArmsUtils.dip2px(this.mContext, 14.0f), ArmsUtils.dip2px(this.mContext, 8.0f));
                }
            } else if (3 == this.t3Record.getState().getWorkState().getWorkProcess() / 10) {
                this.btnT3TerminateClean.setVisibility(8);
                this.btnT3PauseClean.setVisibility(0);
                this.btnT3ContinueToRun.setVisibility(8);
                this.tvT3DeviceState.setText(this.mContext.getResources().getString(R.string.Device_reset_prompt));
                this.rlPop.setVisibility(8);
                if (this.t3Record.getState().getWorkState().getWorkMode() != 1 && this.t3Record.getState().getWorkState().getWorkMode() != 3 && this.t3Record.getState().getWorkState().getWorkMode() != 5) {
                    stopCounterclockwiseRotateAnimation();
                    if (this.clockwiseRotateAnimator == null) {
                        startClockwiseRotateAnimation(360.0f);
                    } else {
                        resumeClockwiseRotateAnimation();
                    }
                } else {
                    stopClockwiseRotateAnimation();
                    if (this.counterclockwiseRotateAnimator == null) {
                        startCounterclockwiseRotateAnimation(360.0f);
                    } else {
                        resumeCounterclockwiseRotateAnimation();
                    }
                }
                this.btnT3PauseClean.setTextColor(this.mContext.getResources().getColor(R.color.t3_prompt_gray));
                this.btnT3PauseClean.setBackgroundResource(R.drawable.solid_t3_home_gray);
                this.tvT3DeviceState.setTextColor(this.mContext.getResources().getColor(R.color.t3_main_blue));
                this.tvT3DeviceState.setBackgroundResource(R.drawable.selector_t3_blue_with_radius_shadow);
                this.tvT3DeviceState.setPadding(ArmsUtils.dip2px(this.mContext, 14.0f), ArmsUtils.dip2px(this.mContext, 8.0f), ArmsUtils.dip2px(this.mContext, 14.0f), ArmsUtils.dip2px(this.mContext, 8.0f));
            } else if (4 == this.t3Record.getState().getWorkState().getWorkProcess() / 10) {
                if (2 == this.t3Record.getState().getWorkState().getWorkProcess() % 10) {
                    this.btnT3PauseClean.setVisibility(4);
                    this.btnT3ContinueToRun.setVisibility(4);
                    this.btnT3TerminateClean.setVisibility(4);
                    int safeWarn2 = this.t3Record.getState().getWorkState().getSafeWarn();
                    if (safeWarn2 != 0) {
                        if (safeWarn2 == 1) {
                            this.rlPop.setVisibility(8);
                            this.tvT3DeviceState.setText(this.mContext.getResources().getString(R.string.Safe_warn_atp));
                        } else {
                            this.rlPop.setVisibility(8);
                            this.tvT3DeviceState.setText(this.mContext.getResources().getString(R.string.Safe_warn_general));
                        }
                    } else if (this.t3Record.getState().getPetInTime() == 0) {
                        this.tvT3DeviceState.setText(this.mContext.getResources().getString(R.string.Device_safety_testing));
                        this.rlPop.setVisibility(8);
                    } else if (this.t3Record.getState().isPetError()) {
                        String string4 = this.mContext.getResources().getString(R.string.No_cat);
                        SpannableString spannableString4 = new SpannableString(this.mContext.getResources().getString(R.string.Pet_go_into_device) + ",\n" + string4);
                        spannableString4.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.t4_main_blue)), spannableString4.toString().indexOf(string4), spannableString4.toString().indexOf(string4) + string4.length(), 18);
                        spannableString4.setSpan(new ClickableSpan() { // from class: com.petkit.android.activities.petkitBleDevice.widget.T3HomeDeviceView.12
                            @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
                            public void updateDrawState(TextPaint textPaint) {
                                super.updateDrawState(textPaint);
                                textPaint.setColor(T3HomeDeviceView.this.getResources().getColor(R.color.t4_main_blue));
                                textPaint.setUnderlineText(true);
                            }

                            @Override // android.text.style.ClickableSpan
                            public void onClick(View view) {
                                T3HomeDeviceView.this.showTipWindow();
                            }
                        }, spannableString4.toString().indexOf(string4), spannableString4.toString().indexOf(string4) + string4.length(), 33);
                        this.tvT3DeviceState.setHighlightColor(0);
                        this.tvT3DeviceState.setText(spannableString4);
                        this.tvT3DeviceState.setMovementMethod(LinkMovementMethod.getInstance());
                        showPopWindow();
                    } else {
                        this.rlPop.setVisibility(8);
                        this.tvT3DeviceState.setText(this.mContext.getResources().getString(R.string.Pet_go_into_device));
                        PopupWindow popupWindow4 = this.popupWindow;
                        if (popupWindow4 != null) {
                            popupWindow4.dismiss();
                            this.popupWindow = null;
                        }
                    }
                    if (this.t3Record.getState().getWorkState().getWorkMode() != 1 && this.t3Record.getState().getWorkState().getWorkMode() != 3 && this.t3Record.getState().getWorkState().getWorkMode() != 5) {
                        startClockwiseRotateAnimation(360.0f);
                        pauseClockwiseRotateAnimation();
                    } else {
                        startCounterclockwiseRotateAnimation(360.0f);
                        pauseCounterclockwiseRotateAnimation();
                    }
                    this.tvT3DeviceState.setTextColor(this.mContext.getResources().getColor(R.color.t3_main_blue));
                    this.tvT3DeviceState.setBackgroundResource(R.drawable.selector_t3_blue_with_radius_shadow);
                    this.tvT3DeviceState.setPadding(ArmsUtils.dip2px(this.mContext, 14.0f), ArmsUtils.dip2px(this.mContext, 8.0f), ArmsUtils.dip2px(this.mContext, 14.0f), ArmsUtils.dip2px(this.mContext, 8.0f));
                } else {
                    this.btnT3PauseClean.setVisibility(8);
                    this.btnT3ContinueToRun.setVisibility(0);
                    this.btnT3TerminateClean.setVisibility(8);
                    this.tvT3DeviceState.setText(this.mContext.getResources().getString(R.string.Device_pause_prompt, String.valueOf((this.t3Record.getState().getWorkState().getStopTime() > 0 ? this.t3Record.getState().getWorkState().getStopTime() : this.t3Record.getSettings().getStopTime()) / 60)));
                    this.rlPop.setVisibility(8);
                    if (this.t3Record.getState().getWorkState().getWorkMode() != 1 && this.t3Record.getState().getWorkState().getWorkMode() != 3 && this.t3Record.getState().getWorkState().getWorkMode() != 5) {
                        startClockwiseRotateAnimation(360.0f);
                        pauseClockwiseRotateAnimation();
                    } else {
                        startCounterclockwiseRotateAnimation(360.0f);
                        pauseCounterclockwiseRotateAnimation();
                    }
                    this.btnT3ContinueToRun.setTextColor(this.mContext.getResources().getColor(R.color.t3_main_blue));
                    this.btnT3ContinueToRun.setBackgroundResource(R.drawable.solid_t3_home_blue);
                    this.tvT3DeviceState.setTextColor(this.mContext.getResources().getColor(R.color.feeder_plan_save_black));
                    this.tvT3DeviceState.setBackgroundResource(R.drawable.selector_k2_white_with_radius_shadow);
                    this.tvT3DeviceState.setPadding(ArmsUtils.dip2px(this.mContext, 14.0f), ArmsUtils.dip2px(this.mContext, 8.0f), ArmsUtils.dip2px(this.mContext, 14.0f), ArmsUtils.dip2px(this.mContext, 8.0f));
                }
            }
            if (this.t3Record.getState().isBoxFull()) {
                startBoxAnimation();
            } else {
                stopBoxAnimation();
            }
            if (this.t3Record.getState().getRefreshState() != null && 1 == this.t3Record.getState().getRefreshState().getWorkProcess()) {
                startDeodorantAnimation();
            } else {
                stopDeodorantAnimation();
            }
        }
        if (this.t3Record.getState().getPower() == 1) {
            this.bleMenuView.setFirstButtonText(getResources().getString(R.string.Turn_off));
        } else {
            this.bleMenuView.setFirstButtonText(getResources().getString(R.string.Turn_on));
        }
        refreshTodayStatistics();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showTipWindow() {
        Activity activity = this.activity;
        activity.startActivity(WebviewActivity.newIntent(activity, getResources().getString(R.string.No_cat), ApiTools.getWebUrlByKey("t3_pet_reset")));
        this.rlPop.setVisibility(8);
    }

    private void refreshTodayStatistics() {
        Resources resources;
        int i;
        int totalTime = this.t3Record.getTotalTime();
        int i2 = totalTime / 3600;
        int i3 = totalTime % 3600;
        int i4 = i3 / 60;
        int i5 = i3 % 60;
        if (this.t3Record.getInTimes() > 1) {
            resources = this.mContext.getResources();
            i = R.string.Unit_times;
        } else {
            resources = this.mContext.getResources();
            i = R.string.Unit_time;
        }
        String string = resources.getString(i);
        String string2 = this.mContext.getResources().getString(i2 > 1 ? R.string.Unit_hours : R.string.Unit_hour);
        String string3 = this.mContext.getResources().getString(i4 > 1 ? R.string.Unit_minutes : R.string.Unit_minute);
        String string4 = this.mContext.getResources().getString(i5 > 1 ? R.string.Unit_seconds : R.string.Unit_second);
        StringBuilder sb = new StringBuilder(this.t3Record.getInTimes() + string);
        if (this.t3Record.getInTimes() > 0) {
            sb.append("/");
            if (i2 > 0) {
                sb.append(i2 + string2);
            }
            if (i4 > 0) {
                sb.append(i4 + string3);
            }
            if (i5 > 0) {
                sb.append(i5 + string4);
            }
        }
        SpannableString spannableString = new SpannableString(sb.toString());
        if (sb.toString().indexOf(string) != -1) {
            spannableString.setSpan(new RelativeSizeSpan(0.65f), sb.toString().indexOf(string), sb.toString().indexOf(string) + string.length(), 33);
        }
        if (sb.toString().indexOf(string2) != -1) {
            spannableString.setSpan(new RelativeSizeSpan(0.65f), sb.toString().indexOf(string2), sb.toString().indexOf(string2) + string2.length(), 33);
        }
        if (sb.toString().indexOf(string3) != -1) {
            spannableString.setSpan(new RelativeSizeSpan(0.65f), sb.toString().indexOf(string3), sb.toString().indexOf(string3) + string3.length(), 33);
        }
        if (sb.toString().indexOf(string4) != -1) {
            spannableString.setSpan(new RelativeSizeSpan(0.65f), sb.toString().indexOf(string4), sb.toString().indexOf(string4) + string4.length(), 33);
        }
        this.tvTimeContent.setText(spannableString);
        List<List<Integer>> petOutRecords = this.t3Record.getPetOutRecords();
        if (petOutRecords == null || petOutRecords.size() == 0) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (List<Integer> list : petOutRecords) {
            arrayList.add(new TimeViewResult(list.get(0).intValue(), list.get(1).intValue()));
        }
        this.t3TimeView.setTimeDataList(arrayList);
    }

    private void checkDeviceWarnState() {
        if (!CommonUtils.isSameTimeZoneAsLocal(this.t3Record.getLocale(), this.t3Record.getTimezone())) {
            this.tvLittleWarnText.setText(R.string.Time_zone_is_different);
            this.tvLittleWarnText.setVisibility(0);
        } else {
            this.tvLittleWarnText.setVisibility(8);
        }
        if (this.t3Record.getState().getSprayModule() == 0) {
            this.rlK3ConsumablesInlet.setVisibility(0);
            if (this.t3Record.getState().isLiquidLack()) {
                liquidStateEmpty();
            } else {
                liquidStateFull();
            }
        } else {
            this.rlK3ConsumablesInlet.setVisibility(8);
        }
        if (this.t3Record.getState().isSandLack()) {
            litterStateEmpty(this.t3Record);
        } else {
            litterStateFull(this.t3Record);
        }
        if (this.t3Record.getState().isBoxFull()) {
            boxStateFull();
        } else {
            boxStateNormal();
        }
        if (this.t3Record.getIsPetOutTips() == 1) {
            this.tvLittleWarnText.setText(R.string.T_Device_Have_Check_Toilet_Warning);
            this.tvLittleWarnText.setVisibility(0);
        }
    }

    private void cancelAllAnimationAndTimer() {
        stopBoxAnimation();
        stopDeodorantAnimation();
        stopCounterclockwiseRotateAnimation();
        stopClockwiseRotateAnimation();
        stopScanAnimation();
        stopAdAni();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:85:0x0252  */
    @Override // android.view.View.OnClickListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onClick(android.view.View r19) {
        /*
            Method dump skipped, instruction units count: 1738
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.petkit.android.activities.petkitBleDevice.widget.T3HomeDeviceView.onClick(android.view.View):void");
    }

    private void startDeodorantAnimation() {
        setConsumablesInletGone();
        if (this.deodorantAlphaAnimator == null) {
            this.t3ViewDeviceDeodorant.setVisibility(0);
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this.t3ViewDeviceDeodorant, Key.ALPHA, 0.0f, 1.0f);
            this.deodorantAlphaAnimator = objectAnimatorOfFloat;
            objectAnimatorOfFloat.setDuration(3000L);
            this.deodorantAlphaAnimator.setInterpolator(new LinearInterpolator());
            this.deodorantAlphaAnimator.setRepeatCount(0);
            this.deodorantAlphaAnimator.setRepeatMode(1);
            this.deodorantAlphaAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.petkit.android.activities.petkitBleDevice.widget.T3HomeDeviceView.13
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    if (T3HomeDeviceView.this.deodorantRunningAlphaAnimator == null) {
                        T3HomeDeviceView.this.t3ViewDeviceDeodorantRunning.setVisibility(0);
                        T3HomeDeviceView t3HomeDeviceView = T3HomeDeviceView.this;
                        t3HomeDeviceView.deodorantRunningAlphaAnimator = ObjectAnimator.ofFloat(t3HomeDeviceView.t3ViewDeviceDeodorantRunning, Key.ALPHA, 0.0f, 1.0f, 0.0f);
                        T3HomeDeviceView.this.deodorantRunningAlphaAnimator.setDuration(2000L);
                        T3HomeDeviceView.this.deodorantRunningAlphaAnimator.setInterpolator(new LinearInterpolator());
                        T3HomeDeviceView.this.deodorantRunningAlphaAnimator.setRepeatCount(-1);
                        T3HomeDeviceView.this.deodorantRunningAlphaAnimator.setRepeatMode(1);
                        T3HomeDeviceView.this.deodorantRunningAlphaAnimator.start();
                    }
                }
            });
            this.deodorantAlphaAnimator.start();
        }
    }

    public void startCounterclockwiseRotateAnimation(float f) {
        this.t3DeviceCenterProgress.setImageResource(R.drawable.t3_home_progress);
        ObjectAnimator objectAnimator = this.counterclockwiseRotateAnimator;
        if (objectAnimator == null || !objectAnimator.isRunning()) {
            this.t3DeviceCenterProgress.setVisibility(0);
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this.t3DeviceCenterProgress, "rotation", f, 0.0f);
            this.counterclockwiseRotateAnimator = objectAnimatorOfFloat;
            objectAnimatorOfFloat.setDuration(ToolTipPopup.DEFAULT_POPUP_DISPLAY_TIME);
            this.counterclockwiseRotateAnimator.setInterpolator(new LinearInterpolator());
            this.counterclockwiseRotateAnimator.setRepeatCount(-1);
            this.counterclockwiseRotateAnimator.setRepeatMode(1);
            this.counterclockwiseRotateAnimator.start();
        }
    }

    public void startClockwiseRotateAnimation(float f) {
        this.t3DeviceCenterProgress.setImageResource(R.drawable.t3_home_progress_clockwise);
        ObjectAnimator objectAnimator = this.clockwiseRotateAnimator;
        if (objectAnimator == null || !objectAnimator.isRunning()) {
            this.t3DeviceCenterProgress.setVisibility(0);
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this.t3DeviceCenterProgress, "rotation", 0.0f, f);
            this.clockwiseRotateAnimator = objectAnimatorOfFloat;
            objectAnimatorOfFloat.setDuration(ToolTipPopup.DEFAULT_POPUP_DISPLAY_TIME);
            this.clockwiseRotateAnimator.setInterpolator(new LinearInterpolator());
            this.clockwiseRotateAnimator.setRepeatCount(-1);
            this.clockwiseRotateAnimator.setRepeatMode(1);
            this.clockwiseRotateAnimator.start();
        }
    }

    public void pauseClockwiseRotateAnimation() {
        ObjectAnimator objectAnimator = this.clockwiseRotateAnimator;
        if (objectAnimator != null) {
            objectAnimator.pause();
        }
    }

    public void resumeClockwiseRotateAnimation() {
        this.t3DeviceCenterProgress.setVisibility(0);
        ObjectAnimator objectAnimator = this.clockwiseRotateAnimator;
        if (objectAnimator == null || !objectAnimator.isPaused()) {
            return;
        }
        this.clockwiseRotateAnimator.resume();
    }

    public void pauseCounterclockwiseRotateAnimation() {
        ObjectAnimator objectAnimator = this.counterclockwiseRotateAnimator;
        if (objectAnimator != null) {
            objectAnimator.pause();
        }
    }

    public void resumeCounterclockwiseRotateAnimation() {
        this.t3DeviceCenterProgress.setVisibility(0);
        ObjectAnimator objectAnimator = this.counterclockwiseRotateAnimator;
        if (objectAnimator == null || !objectAnimator.isPaused()) {
            return;
        }
        this.counterclockwiseRotateAnimator.resume();
    }

    public void stopCounterclockwiseRotateAnimation() {
        this.t3DeviceCenterProgress.setVisibility(8);
        ObjectAnimator objectAnimator = this.counterclockwiseRotateAnimator;
        if (objectAnimator != null) {
            objectAnimator.cancel();
            this.counterclockwiseRotateAnimator = null;
        }
    }

    public void stopClockwiseRotateAnimation() {
        this.t3DeviceCenterProgress.setVisibility(8);
        ObjectAnimator objectAnimator = this.clockwiseRotateAnimator;
        if (objectAnimator != null) {
            objectAnimator.cancel();
            this.clockwiseRotateAnimator = null;
        }
    }

    public void startScanAnimation() {
        AnimatorSet animatorSet = this.scanAnimatorSet;
        if (animatorSet == null || !animatorSet.isRunning()) {
            this.t3DeviceScan.setVisibility(0);
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this.t3DeviceScan, Key.ALPHA, 0.0f, 0.4f, 0.2f);
            objectAnimatorOfFloat.setRepeatMode(1);
            objectAnimatorOfFloat.setRepeatCount(-1);
            ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(this.t3DeviceScan, Key.TRANSLATION_Y, 0.0f, ArmsUtils.dip2px(this.mContext, 82.0f));
            objectAnimatorOfFloat2.setRepeatMode(1);
            objectAnimatorOfFloat2.setRepeatCount(-1);
            ObjectAnimator objectAnimatorOfFloat3 = ObjectAnimator.ofFloat(this.t3DeviceScan, Key.SCALE_X, 0.6f, 1.0f, 0.9f);
            objectAnimatorOfFloat3.setRepeatMode(1);
            objectAnimatorOfFloat3.setRepeatCount(-1);
            AnimatorSet animatorSet2 = new AnimatorSet();
            this.scanAnimatorSet = animatorSet2;
            animatorSet2.setDuration(Constants.H3_CLOUD_SERVICE_FREE_TIME_DURATION);
            this.scanAnimatorSet.playTogether(objectAnimatorOfFloat, objectAnimatorOfFloat3, objectAnimatorOfFloat2);
            this.scanAnimatorSet.start();
        }
    }

    public void stopScanAnimation() {
        this.t3DeviceScan.setVisibility(8);
        AnimatorSet animatorSet = this.scanAnimatorSet;
        if (animatorSet != null) {
            animatorSet.cancel();
            this.scanAnimatorSet = null;
        }
    }

    private void stopDeodorantAnimation() {
        this.t3ViewDeviceDeodorant.setVisibility(8);
        this.t3ViewDeviceDeodorantRunning.setVisibility(8);
        ObjectAnimator objectAnimator = this.deodorantAlphaAnimator;
        if (objectAnimator != null) {
            objectAnimator.cancel();
            this.deodorantAlphaAnimator = null;
        }
        ObjectAnimator objectAnimator2 = this.deodorantRunningAlphaAnimator;
        if (objectAnimator2 != null) {
            objectAnimator2.cancel();
            this.deodorantRunningAlphaAnimator = null;
        }
    }

    public MenuOnClickListener getMenuOnClickListener() {
        return this.menuOnClickListener;
    }

    public void setMenuOnClickListener(MenuOnClickListener menuOnClickListener) {
        this.menuOnClickListener = menuOnClickListener;
    }

    @Override // com.petkit.android.activities.petkitBleDevice.widget.PetkitSlidingUpPanelLayout.PanelSlideListener
    public void onPanelSlide(View view, float f) {
        this.mSlideOffset = f;
        if (f >= 0.0f) {
            this.rlTopView.setAlpha(1.0f - f);
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.widget.PetkitSlidingUpPanelLayout.PanelSlideListener
    public void onPanelStateChanged(View view, PetkitSlidingUpPanelLayout.PanelState panelState, PetkitSlidingUpPanelLayout.PanelState panelState2) {
        PetkitSlidingUpPanelLayout.PanelState.DRAGGING.equals(panelState2);
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

    @Override // com.petkit.android.activities.petkitBleDevice.widget.BleDeviceHomeMenuView.OnMenuClickListener
    public void onMenuClick(BleMenuItem bleMenuItem, int i) {
        MenuOnClickListener menuOnClickListener;
        if (i == 0) {
            MenuOnClickListener menuOnClickListener2 = this.menuOnClickListener;
            if (menuOnClickListener2 != null) {
                menuOnClickListener2.onBottomMenuClick(MenuType.ON_OFF);
                return;
            }
            return;
        }
        if (i == 1) {
            MenuOnClickListener menuOnClickListener3 = this.menuOnClickListener;
            if (menuOnClickListener3 != null) {
                menuOnClickListener3.onBottomMenuClick(MenuType.CLEAN_UP);
                return;
            }
            return;
        }
        if (i != 2) {
            if (i == 3 && (menuOnClickListener = this.menuOnClickListener) != null) {
                menuOnClickListener.onBottomMenuClick(MenuType.SMART_SETTING);
                return;
            }
            return;
        }
        MenuOnClickListener menuOnClickListener4 = this.menuOnClickListener;
        if (menuOnClickListener4 != null) {
            menuOnClickListener4.onBottomMenuClick(MenuType.DEODORANT);
        }
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageSelected(int i) {
        this.currentPageSelected = i;
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageScrollStateChanged(int i) {
        if (i == 1) {
            this.isScroll = true;
        } else if (i == 0) {
            this.isScroll = false;
        }
    }

    private void stopCenterTimer() {
        Disposable disposable = this.disposable;
        if (disposable == null || disposable.isDisposed()) {
            return;
        }
        this.disposable.dispose();
    }

    public void setDeviceRecord(List<T3DeviceRecord> list, String str) {
        this.t3DeviceRecordList = list;
        this.recordDate = str;
        if (list == null) {
            return;
        }
        this.historyRecord.clear();
        for (T3DeviceRecord t3DeviceRecord : list) {
            if (t3DeviceRecord != null) {
                t3DeviceRecord.setTimezone(this.t3Record.getTimezone());
                this.historyRecord.add(t3DeviceRecord);
            }
        }
        Collections.sort(this.historyRecord);
        BleDeviceUtils.filterT3DeviceRecord(this.historyRecord, this.selectedPetIds);
        try {
            if (this.t3HomeHistoryRecordAdapter == null) {
                T3HomeHistoryRecordAdapter t3HomeHistoryRecordAdapter = new T3HomeHistoryRecordAdapter(this.activity, this.t3Record.getDeviceShared() != null, this.t3Record.getDeviceId(), this.familyInfor);
                this.t3HomeHistoryRecordAdapter = t3HomeHistoryRecordAdapter;
                t3HomeHistoryRecordAdapter.setListener(this);
                this.t3HomeHistoryRecordAdapter.setData(this.historyRecord, this.t3Record.getActualTimeZone(), str);
                this.rvT3RecordView.setAdapter(this.t3HomeHistoryRecordAdapter);
            } else {
                T3HomeHistoryRecordAdapter t3HomeHistoryRecordAdapter2 = new T3HomeHistoryRecordAdapter(this.activity, this.t3Record.getDeviceShared() != null, this.t3Record.getDeviceId(), this.familyInfor);
                this.t3HomeHistoryRecordAdapter = t3HomeHistoryRecordAdapter2;
                t3HomeHistoryRecordAdapter2.setListener(this);
                this.t3HomeHistoryRecordAdapter.setData(this.historyRecord, this.t3Record.getActualTimeZone(), str);
                this.rvT3RecordView.setAdapter(this.t3HomeHistoryRecordAdapter);
            }
        } catch (Exception e) {
            PetkitLog.d(e.getMessage());
            LogcatStorageHelper.addLog("t3 add history adapter error:" + e.getMessage());
        }
        if (this.selectedPetIds.contains(ColorUtils.ALL_PET)) {
            this.tvPetWeight.setVisibility(8);
            this.viewPointOne.setVisibility(8);
            this.viewPointTwo.setVisibility(8);
            this.tvPetNameOne.setVisibility(0);
            this.tvPetNameTwo.setVisibility(8);
            this.tvPetNameOne.setText(R.string.All_cats);
            return;
        }
        if (this.selectedPetIds.size() >= 2) {
            this.tvPetWeight.setVisibility(8);
            this.viewPointOne.setVisibility(0);
            this.viewPointTwo.setVisibility(0);
            this.tvPetNameOne.setVisibility(0);
            this.tvPetNameTwo.setVisibility(0);
            String string = ColorUtils.UNKNOWN_PET.equals(this.selectedPetIds.get(0)) ? this.activity.getString(R.string.Not_matched) : UserInforUtils.getPetById(this.selectedPetIds.get(0)).getName();
            String string2 = ColorUtils.UNKNOWN_PET.equals(this.selectedPetIds.get(1)) ? this.activity.getString(R.string.Not_matched) : UserInforUtils.getPetById(this.selectedPetIds.get(1)).getName();
            this.viewPointOne.setBackgroundResource(ColorUtils.getPetColorCircleResById(this.selectedPetIds.get(1), string2));
            this.viewPointTwo.setBackgroundResource(ColorUtils.getPetColorCircleResById(this.selectedPetIds.get(0), string));
            this.tvPetNameOne.setText(string2);
            this.tvPetNameTwo.setText(string);
            return;
        }
        if (this.selectedPetIds.size() == 1) {
            if (!ColorUtils.UNKNOWN_PET.equals(this.selectedPetIds.get(0))) {
                this.tvPetWeight.setVisibility(0);
                Pet petById = UserInforUtils.getPetById(this.selectedPetIds.get(0));
                if (UserInforUtils.getCurrentLoginResult().getSettings().getUnit() == 1) {
                    if (petById.getWeight() != null) {
                        String strValueOf = String.valueOf(CommonUtil.doubleToDouble(CommonUtil.KgToLb(Double.valueOf(petById.getWeight()).doubleValue())));
                        String str2 = this.mContext.getResources().getString(R.string.Current_weight) + " " + strValueOf + " " + this.mContext.getResources().getString(R.string.Unit_lb) + " >";
                        this.tvPetWeight.setText(TextUtil.getInstance().makePartStringSpannableInTotalString(new SpannableString(str2), str2, strValueOf, getResources().getColor(R.color.light_black), 20, true));
                    } else {
                        this.tvPetWeight.setVisibility(8);
                    }
                } else if (petById.getWeight() != null) {
                    String strValueOf2 = String.valueOf(CommonUtil.doubleToDouble(Double.valueOf(petById.getWeight()).doubleValue()));
                    String str3 = this.mContext.getResources().getString(R.string.Current_weight) + " " + strValueOf2 + " " + this.mContext.getResources().getString(R.string.Unit_kg) + " >";
                    this.tvPetWeight.setText(TextUtil.getInstance().makePartStringSpannableInTotalString(new SpannableString(str3), str3, strValueOf2, getResources().getColor(R.color.light_black), 20, true));
                } else {
                    this.tvPetWeight.setVisibility(8);
                }
            } else {
                this.tvPetWeight.setVisibility(8);
            }
            this.viewPointOne.setVisibility(0);
            this.viewPointTwo.setVisibility(8);
            this.tvPetNameOne.setVisibility(0);
            this.tvPetNameTwo.setVisibility(8);
            String string3 = ColorUtils.UNKNOWN_PET.equals(this.selectedPetIds.get(0)) ? this.activity.getString(R.string.Not_matched) : UserInforUtils.getPetById(this.selectedPetIds.get(0)).getName();
            this.viewPointOne.setBackgroundResource(ColorUtils.getPetColorCircleResById(this.selectedPetIds.get(0), string3));
            this.tvPetNameOne.setText(string3);
        }
    }

    public void setToiletCompareResult(ToiletCompareResult toiletCompareResult) {
        String str;
        int color;
        this.toiletCompareResult = toiletCompareResult;
        if (toiletCompareResult != null) {
            this.tvTodayTime.setText(UnitUtils.getInstance().getNumAndUnitString(toiletCompareResult.getCount(), this.mContext.getResources().getString(R.string.Unit_time), this.mContext.getResources().getString(R.string.Unit_times)));
            this.tvTodayAverage.setText(TimeUtils.getInstance().secondsToMinuteAndSecondsShortText(this.mContext, toiletCompareResult.getTime()));
            int i = R.drawable.small_green_up_arrow;
            int color2 = 0;
            String str2 = "";
            if (toiletCompareResult.getCompareCount() >= 0) {
                i = R.drawable.small_green_up_arrow;
                color = this.mContext.getResources().getColor(R.color.compare_green);
                str = "+" + toiletCompareResult.getCompareCount();
            } else if (toiletCompareResult.getCompareCount() < 0) {
                i = R.drawable.small_down_orange_arrow;
                color = this.mContext.getResources().getColor(R.color.compare_orange);
                str = "" + toiletCompareResult.getCompareCount();
            } else {
                str = "";
                color = 0;
            }
            SpannableString spannableString = new SpannableString(this.mContext.getResources().getString(R.string.Compared_to_the_same_period_yesterday) + str);
            spannableString.setSpan(new ForegroundColorSpan(color), spannableString.toString().lastIndexOf(str), spannableString.toString().lastIndexOf(str) + str.length(), 33);
            spannableString.setSpan(new RelativeSizeSpan(1.2f), spannableString.toString().lastIndexOf(str), spannableString.toString().lastIndexOf(str) + str.length(), 33);
            this.tvTodayTimeComparedYesterday.setText(spannableString);
            if (toiletCompareResult.getCompareCount() != 0) {
                this.tvTodayTimeComparedYesterday.setCompoundDrawablesRelativeWithIntrinsicBounds((Drawable) null, (Drawable) null, getResources().getDrawable(i), (Drawable) null);
            } else {
                this.tvTodayTimeComparedYesterday.setCompoundDrawablesRelativeWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
            }
            int i2 = R.drawable.small_green_up_arrow;
            if (toiletCompareResult.getCompareTime() >= 0) {
                i2 = R.drawable.small_green_up_arrow;
                color2 = this.mContext.getResources().getColor(R.color.compare_green);
                str2 = "+" + TimeUtils.getInstance().secondsToMinuteAndSecondsShortText(this.mContext, Math.abs(toiletCompareResult.getCompareTime()));
            } else if (toiletCompareResult.getCompareTime() < 0) {
                i2 = R.drawable.small_down_orange_arrow;
                color2 = this.mContext.getResources().getColor(R.color.compare_orange);
                str2 = "-" + TimeUtils.getInstance().secondsToMinuteAndSecondsShortText(this.mContext, Math.abs(toiletCompareResult.getCompareTime()));
            }
            SpannableString spannableString2 = new SpannableString(this.mContext.getResources().getString(R.string.Compared_to_the_same_period_yesterday) + str2);
            spannableString2.setSpan(new ForegroundColorSpan(color2), spannableString2.toString().lastIndexOf(str2), spannableString2.toString().lastIndexOf(str2) + str2.length(), 33);
            spannableString2.setSpan(new RelativeSizeSpan(1.2f), spannableString2.toString().lastIndexOf(str2), spannableString2.toString().lastIndexOf(str2) + str2.length(), 33);
            this.tvTodayAverageComparedYesterday.setText(spannableString2);
            if (toiletCompareResult.getCompareTime() != 0) {
                this.tvTodayAverageComparedYesterday.setCompoundDrawablesRelativeWithIntrinsicBounds((Drawable) null, (Drawable) null, getResources().getDrawable(i2), (Drawable) null);
            } else {
                this.tvTodayAverageComparedYesterday.setCompoundDrawablesRelativeWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
            }
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.widget.BleDeviceHomeOfflineWarnWindow.OfflineClickListener
    public void onClickSeeDetails() {
        Activity activity = this.activity;
        activity.startActivity(BleDeviceWifiSettingActivity.newIntent(activity, this.t3Record.getDeviceId(), 7));
    }

    @Override // com.petkit.android.activities.petkitBleDevice.widget.BleDeviceHomeOfflineWarnWindow.OfflineClickListener
    public void onClickReset() {
        Activity activity = this.activity;
        activity.startActivity(BleDeviceBindNetWorkActivity.newIntent((Context) activity, this.t3Record.getDeviceId(), 7, this.t3Record.getBtMac(), false));
    }

    @Override // com.petkit.android.activities.petkitBleDevice.adapter.T3HomeHistoryRecordAdapter.OnClickListener
    public void onViewClick(T3DeviceRecord t3DeviceRecord, int i, String str) {
        RecordOnClickListener recordOnClickListener = this.recordOnClickListener;
        if (recordOnClickListener != null) {
            recordOnClickListener.onClickRecord(t3DeviceRecord, str);
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.adapter.T3HomeHistoryRecordAdapter.OnClickListener
    public void onDelClick(T3DeviceRecord t3DeviceRecord, int i, String str) {
        RecordOnClickListener recordOnClickListener = this.recordOnClickListener;
        if (recordOnClickListener != null) {
            recordOnClickListener.onDelRecord(t3DeviceRecord, str);
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.adapter.T3HomeHistoryRecordAdapter.OnClickListener
    public void onUnknownPetClick(String str, String str2, String str3, double d) {
        RecordOnClickListener recordOnClickListener = this.recordOnClickListener;
        if (recordOnClickListener != null) {
            recordOnClickListener.onUnknownPetClick(str, str2, str3, d);
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.adapter.T3HomeHistoryRecordAdapter.OnClickListener
    public void onUnderweightClick() {
        this.menuOnClickListener.onBottomMenuClick(MenuType.UNDERWEIGHT);
    }

    public void setStatistic(T3StatisticResult t3StatisticResult) {
        this.t3StatisticResult = t3StatisticResult;
        refreshStatistic();
    }

    private void refreshT3Histogram() {
        this.t3Histogram.updateBarChartData(this.statisticType, this.t3Record.getDeviceId(), this.selectedPetIds);
    }

    public void refreshStatistic() {
        refreshT3Histogram();
        if (this.statisticType == 0) {
            this.tvHistoryTime.setText(BleDeviceUtils.getNewHistoryRecordTitle(this.t3StatisticResult, this.mContext, this.selectedPetIds));
        }
        if (!this.showDataDesc) {
            this.tvHistoryDate.setVisibility(0);
        }
        if (this.statisticType != 0) {
            if (this.t3StatisticResult.getStatisticTime().contains("-")) {
                this.tvHistoryDate.setText(String.format("%s-%s", T4Utils.getFormatYMDDateFromString(this.t3StatisticResult.getStatisticTime().split("-")[0]), T4Utils.getFormatYMDDateFromString(this.t3StatisticResult.getStatisticTime().split("-")[1])));
            }
        } else {
            this.tvHistoryDate.setText(BleDeviceUtils.getDayChartDate(this.t3StatisticResult, this.mContext));
        }
        this.tvHistoryDate.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.petkit.android.activities.petkitBleDevice.widget.T3HomeDeviceView.14
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                int width = (T3HomeDeviceView.this.rlRecordPanel.getWidth() - ArmsUtils.dip2px(T3HomeDeviceView.this.activity, 20.0f)) - T3HomeDeviceView.this.tvHistoryDate.getWidth();
                int width2 = T3HomeDeviceView.this.llPets.getWidth();
                PetkitLog.d("llPetsWidth:" + width2 + " left:" + width);
                if (width2 > width) {
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) T3HomeDeviceView.this.llPets.getLayoutParams();
                    int i = layoutParams.leftMargin;
                    int iDip2px = ArmsUtils.dip2px(T3HomeDeviceView.this.activity, 50.0f);
                    int i2 = layoutParams.rightMargin;
                    layoutParams.setMargins(i, iDip2px, i2, i2);
                    T3HomeDeviceView.this.llPets.setLayoutParams(layoutParams);
                } else {
                    RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) T3HomeDeviceView.this.llPets.getLayoutParams();
                    int i3 = layoutParams2.leftMargin;
                    int iDip2px2 = ArmsUtils.dip2px(T3HomeDeviceView.this.activity, 16.0f);
                    int i4 = layoutParams2.rightMargin;
                    layoutParams2.setMargins(i3, iDip2px2, i4, i4);
                    T3HomeDeviceView.this.llPets.setLayoutParams(layoutParams2);
                }
                T3HomeDeviceView.this.tvHistoryDate.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String totalTimeToStr(int i) {
        Resources resources;
        int i2;
        int i3 = (int) (i / 60.0f);
        int i4 = i % 60;
        String string = this.mContext.getResources().getString(i3 > 1 ? R.string.Unit_minutes_short : R.string.Unit_minute_short);
        if (i4 > 1) {
            resources = this.mContext.getResources();
            i2 = R.string.Unit_seconds_short;
        } else {
            resources = this.mContext.getResources();
            i2 = R.string.Unit_second_short;
        }
        String string2 = resources.getString(i2);
        String str = "";
        String str2 = "zh_CN".equalsIgnoreCase(UserInforUtils.getLanguageLocaleString(CommonUtils.getAppContext())) ? "" : " ";
        StringBuilder sb = new StringBuilder();
        if (i3 > 0) {
            str = i3 + str2 + string;
        }
        sb.append(str);
        sb.append(str2);
        sb.append(i4);
        sb.append(str2);
        sb.append(string2);
        return sb.toString();
    }

    @Override // com.petkit.android.activities.universalWindow.BaseBottomWindow.OnClickListener
    public void onBtnLeft() {
        this.petFilterWindow.resetSelectedState();
        this.petFilterWindow.dismiss();
        this.selectedPetIds = this.petFilterWindow.getSelectedPetIds();
        PetkitLog.d("selectedPetIds onBtnRight:" + this.selectedPetIds.toString());
        refreshT3Histogram();
        refreshStatistic();
        setDeviceRecord(this.t3DeviceRecordList, this.recordDate);
    }

    @Override // com.petkit.android.activities.universalWindow.BaseBottomWindow.OnClickListener
    public void onBtnRight() {
        this.petFilterWindow.updateSelectedState();
        this.petFilterWindow.dismiss();
        this.selectedPetIds = this.petFilterWindow.getSelectedPetIds();
        PetkitLog.d("selectedPetIds onBtnRight:" + this.selectedPetIds.toString());
        refreshT3Histogram();
        refreshStatistic();
        setDeviceRecord(this.t3DeviceRecordList, this.recordDate);
    }

    @Override // com.petkit.android.activities.universalWindow.BaseBottomWindow.OnClickListener
    public void onTopRightBtn() {
        Activity activity = this.activity;
        activity.startActivity(PetColorSettingActivity.newIntent(activity, this.thisDeviceFamilyPetList));
    }

    public static class MainHandler extends Handler {
        public final WeakReference<Context> mContext;

        public MainHandler(Context context) {
            super(Looper.getMainLooper());
            this.mContext = new WeakReference<>(context);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showPopWindow() {
        if (this.isFirst) {
            if (this.isReady) {
                this.tvT3DeviceState.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.petkit.android.activities.petkitBleDevice.widget.T3HomeDeviceView.15
                    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                    public void onGlobalLayout() {
                        int top = T3HomeDeviceView.this.rlBtnAndWarnPanel.getTop();
                        int measuredWidth = T3HomeDeviceView.this.rlPop.getMeasuredWidth();
                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) T3HomeDeviceView.this.rlPop.getLayoutParams();
                        layoutParams.setMargins((int) ((BaseApplication.displayMetrics.widthPixels / 2.0f) - ((measuredWidth / 2.0f) * 1.0f)), top, layoutParams.rightMargin, layoutParams.bottomMargin);
                        T3HomeDeviceView.this.rlPop.setLayoutParams(layoutParams);
                        T3HomeDeviceView.this.mainHandler.postDelayed(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.widget.T3HomeDeviceView.15.1
                            @Override // java.lang.Runnable
                            public void run() {
                                T3HomeDeviceView.this.rlPop.setVisibility(0);
                                T3HomeDeviceView.this.isFirst = false;
                                T3HomeDeviceView.this.isNeedShowPop = false;
                            }
                        }, 1000L);
                        T3HomeDeviceView.this.tvT3DeviceState.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });
            } else {
                this.isNeedShowPop = true;
            }
        }
    }

    public void showLegendWindow(View view, LinkedHashMap<String, String> linkedHashMap, LinkedHashMap<String, String> linkedHashMap2) {
        int iIndexOf;
        View viewInflate = View.inflate(this.mContext, R.layout.layout_toilet_pause_pop_tip, null);
        ImageView imageView = (ImageView) viewInflate.findViewById(R.id.iv_top_arrow);
        LinearLayout linearLayout = (LinearLayout) viewInflate.findViewById(R.id.ll_pet_name);
        linearLayout.removeAllViews();
        if (linkedHashMap.get("-1") != null) {
            View viewInflate2 = View.inflate(this.mContext, R.layout.layout_pet_legend, null);
            View viewFindViewById = viewInflate2.findViewById(R.id.view_point);
            TextView textView = (TextView) viewInflate2.findViewById(R.id.tv_pet_name);
            viewFindViewById.setBackgroundResource(Constants.TOILET_CHART_COLOR_UNKNOWN_CIRCLE);
            textView.setText(this.mContext.getResources().getString(R.string.Not_matched));
            linearLayout.addView(viewInflate2);
        }
        for (int size = linkedHashMap.size() - 1; size >= 0; size--) {
            if (!"-1".equals(linkedHashMap.keySet().toArray()[size]) && (iIndexOf = Arrays.asList(Constants.TOILET_CHART_LIST_COLORS).indexOf(linkedHashMap.get(linkedHashMap.keySet().toArray()[size]))) >= 0) {
                View viewInflate3 = View.inflate(this.mContext, R.layout.layout_pet_legend, null);
                View viewFindViewById2 = viewInflate3.findViewById(R.id.view_point);
                TextView textView2 = (TextView) viewInflate3.findViewById(R.id.tv_pet_name);
                viewFindViewById2.setBackgroundResource(Constants.TOILET_CHART_LIST_COLOR_CIRCLE[iIndexOf]);
                textView2.setText(linkedHashMap2.get(linkedHashMap.keySet().toArray()[size]));
                linearLayout.addView(viewInflate3);
            }
        }
        viewInflate.measure(0, 0);
        int measuredWidth = (viewInflate.getMeasuredWidth() - ArmsUtils.dip2px(this.mContext, 10.0f)) - ArmsUtils.dip2px(this.mContext, 20.0f);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) imageView.getLayoutParams();
        layoutParams.setMargins(measuredWidth - ArmsUtils.dip2px(this.mContext, 16.0f), layoutParams.topMargin, layoutParams.rightMargin, layoutParams.bottomMargin);
        imageView.setLayoutParams(layoutParams);
        PopupWindow popupWindow = this.popupWindow;
        if (popupWindow == null || !popupWindow.isShowing()) {
            PopupWindow popupWindow2 = new PopupWindow(-2, -2);
            popupWindow2.setContentView(viewInflate);
            popupWindow2.setOutsideTouchable(true);
            popupWindow2.setFocusable(true);
            popupWindow2.showAsDropDown(view, 0, 10);
        }
    }

    private void setConsumablesInletVisible() {
        this.rlLitterConsumablesInlet.setVisibility(0);
        this.rlK3ConsumablesInlet.setVisibility(0);
        this.rlBoxConsumablesInlet.setVisibility(0);
    }

    public void setIsAllowedOpenGuide(boolean z) {
        this.isAllowedOpenGuide = z;
        if (z) {
            showGuideView(this.rlDevice);
        }
        refreshView();
    }

    private void setConsumablesInletGone() {
        this.rlLiquidNotInstall.setVisibility(8);
        this.rlLitterConsumablesInlet.setVisibility(8);
        this.rlK3ConsumablesInlet.setVisibility(8);
        this.rlBoxConsumablesInlet.setVisibility(8);
        this.t4ViewDeviceCenterBox.setVisibility(8);
        this.ivLackLitterBg.setVisibility(8);
        this.ivLackLiquidBg.setVisibility(8);
    }

    private void boxStateFull() {
        this.t4ViewDeviceCenterBox.setVisibility(0);
        this.tvBoxConsumablesInlet.setText(this.mContext.getResources().getString(R.string.Box_is_full));
        this.tvBoxConsumablesInlet.setBackground(this.mContext.getResources().getDrawable(R.drawable.shape_t4_state_red_bg));
        this.ivBoxConsumablesInlet.setImageResource(R.drawable.t4_state_red_icon);
        ObjectAnimator objectAnimator = this.boxAlphaAnimator;
        if (objectAnimator == null || !objectAnimator.isRunning()) {
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this.ivBoxConsumablesInlet, Key.ALPHA, 0.0f, 1.0f, 0.0f);
            this.boxAlphaAnimator = objectAnimatorOfFloat;
            objectAnimatorOfFloat.setDuration(2000L);
            this.boxAlphaAnimator.setInterpolator(new LinearInterpolator());
            this.boxAlphaAnimator.setRepeatCount(-1);
            this.boxAlphaAnimator.setRepeatMode(1);
            this.boxAlphaAnimator.start();
            this.mainHandler.postDelayed(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.widget.T3HomeDeviceView.16
                @Override // java.lang.Runnable
                public void run() {
                    if (T3HomeDeviceView.this.boxAlphaAnimator != null) {
                        T3HomeDeviceView.this.boxAlphaAnimator.cancel();
                    }
                }
            }, 7000L);
        }
    }

    private void boxStateNormal() {
        this.t4ViewDeviceCenterBox.setVisibility(8);
        this.tvBoxConsumablesInlet.setText(this.mContext.getResources().getString(R.string.T4_box_normal));
        this.tvBoxConsumablesInlet.setBackgroundResource(R.drawable.shape_t4_state_blue_bg);
        this.ivBoxConsumablesInlet.setImageResource(R.drawable.t4_state_blue_icon);
        ObjectAnimator objectAnimator = this.boxAlphaAnimator;
        if (objectAnimator != null) {
            objectAnimator.cancel();
            this.boxAlphaAnimator = null;
        }
    }

    private void litterStateEmpty(T3Record t3Record) {
        stopScanAnimation();
        this.ivLackLitterBg.setVisibility(0);
        this.tvLitterConsumablesInlet.setText(this.mContext.getResources().getString(R.string.Cat_litter) + ":" + t3Record.getState().getSandPercent() + "%");
        this.tvLitterConsumablesInlet.setBackgroundResource(R.drawable.shape_t4_state_red_bg);
        this.ivLitterConsumablesInlet.setImageResource(R.drawable.t4_state_red_icon);
        ObjectAnimator objectAnimator = this.litterAlphaAnimator;
        if (objectAnimator == null || !objectAnimator.isRunning()) {
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this.ivLitterConsumablesInlet, Key.ALPHA, 0.0f, 1.0f, 0.0f);
            this.litterAlphaAnimator = objectAnimatorOfFloat;
            objectAnimatorOfFloat.setDuration(2000L);
            this.litterAlphaAnimator.setInterpolator(new LinearInterpolator());
            this.litterAlphaAnimator.setRepeatCount(-1);
            this.litterAlphaAnimator.setRepeatMode(1);
            this.litterAlphaAnimator.start();
            this.mainHandler.postDelayed(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.widget.T3HomeDeviceView.17
                @Override // java.lang.Runnable
                public void run() {
                    if (T3HomeDeviceView.this.litterAlphaAnimator != null) {
                        T3HomeDeviceView.this.litterAlphaAnimator.cancel();
                    }
                }
            }, 7000L);
        }
    }

    private void litterStateFull(T3Record t3Record) {
        this.ivLackLitterBg.setVisibility(8);
        this.tvLitterConsumablesInlet.setText(this.mContext.getResources().getString(R.string.Cat_litter) + ":" + t3Record.getState().getSandPercent() + "%");
        this.tvLitterConsumablesInlet.setBackgroundResource(R.drawable.shape_t4_state_blue_bg);
        this.ivLitterConsumablesInlet.setImageResource(R.drawable.t4_state_blue_icon);
        ObjectAnimator objectAnimator = this.litterAlphaAnimator;
        if (objectAnimator != null) {
            objectAnimator.cancel();
            this.litterAlphaAnimator = null;
        }
    }

    private void liquidStateEmpty() {
        this.ivLackLiquidBg.setVisibility(0);
        this.tvK3ConsumablesInlet.setText(this.mContext.getResources().getString(R.string.K3_Liquid_lack_title));
        this.tvK3ConsumablesInlet.setBackgroundResource(R.drawable.shape_t4_state_red_bg);
        this.ivK3ConsumablesInlet.setImageResource(R.drawable.t4_state_red_icon);
        ObjectAnimator objectAnimator = this.liquideAlphaAnimator;
        if (objectAnimator == null || !objectAnimator.isRunning()) {
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this.ivK3ConsumablesInlet, Key.ALPHA, 0.0f, 1.0f, 0.0f);
            this.liquideAlphaAnimator = objectAnimatorOfFloat;
            objectAnimatorOfFloat.setDuration(2000L);
            this.liquideAlphaAnimator.setInterpolator(new LinearInterpolator());
            this.liquideAlphaAnimator.setRepeatCount(-1);
            this.liquideAlphaAnimator.setRepeatMode(1);
            this.liquideAlphaAnimator.start();
            this.mainHandler.postDelayed(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.widget.T3HomeDeviceView.18
                @Override // java.lang.Runnable
                public void run() {
                    if (T3HomeDeviceView.this.liquideAlphaAnimator != null) {
                        T3HomeDeviceView.this.liquideAlphaAnimator.cancel();
                    }
                }
            }, 7000L);
        }
    }

    private void liquidStateFull() {
        this.ivLackLiquidBg.setVisibility(8);
        this.tvK3ConsumablesInlet.setText(this.mContext.getResources().getString(R.string.K3_Liquid_adequate_title));
        this.tvK3ConsumablesInlet.setBackgroundResource(R.drawable.shape_t4_state_blue_bg);
        this.ivK3ConsumablesInlet.setImageResource(R.drawable.t4_state_blue_icon);
        ObjectAnimator objectAnimator = this.liquideAlphaAnimator;
        if (objectAnimator != null) {
            objectAnimator.cancel();
            this.liquideAlphaAnimator = null;
        }
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
            this.promoteView.setPromoteViewOnItemListener(new PromoteView.PromoteViewOnItemListener() { // from class: com.petkit.android.activities.petkitBleDevice.widget.T3HomeDeviceView.19
                @Override // com.petkit.android.activities.mall.widget.PromoteView.PromoteViewOnItemListener
                public void onItemClick(PromoteData promoteData) {
                    EventBus.getDefault().post(promoteData);
                }
            });
        }
    }

    public void showRecordGuideView(View view) {
        if (this.guide3 != null) {
            return;
        }
        GuideBuilder guideBuilder = new GuideBuilder();
        guideBuilder.setTargetView(view).setAlpha(180).setHighTargetCorner(50).setHighTargetPaddingLeft(ArmsUtils.dip2px(this.mContext, 0.0f)).setHighTargetPaddingRight(ArmsUtils.dip2px(this.mContext, 0.0f)).setHighTargetPaddingBottom(ArmsUtils.dip2px(this.mContext, 0.0f)).setHighTargetPaddingTop(ArmsUtils.dip2px(this.mContext, 0.0f)).setOutsideTouchable(false).setAutoDismiss(true);
        guideBuilder.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() { // from class: com.petkit.android.activities.petkitBleDevice.widget.T3HomeDeviceView.20
            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onShown() {
            }

            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onDismiss() {
                DataHelper.setBooleanSF(T3HomeDeviceView.this.mContext, Consts.T3_CHART_IS_FIRST, Boolean.TRUE);
                T3HomeDeviceView.this.nestedScrollView.fling(1);
                T3HomeDeviceView.this.nestedScrollView.setOnTouchListener(new View.OnTouchListener() { // from class: com.petkit.android.activities.petkitBleDevice.widget.T3HomeDeviceView.20.1
                    @Override // android.view.View.OnTouchListener
                    public boolean onTouch(View view2, MotionEvent motionEvent) {
                        return false;
                    }
                });
            }
        });
        guideBuilder.addComponent(new GuidePetTipAndBtnAndLineComponent(new GuideParam(getResources().getString(R.string.Home_chart_guide_tips), "", 4, 32, 20, 10, getResources().getString(R.string.Done), R.layout.layout_feeder_guide_one), new ConfirmListener() { // from class: com.petkit.android.activities.petkitBleDevice.widget.T3HomeDeviceView.21
            @Override // com.petkit.android.activities.petkitBleDevice.w5.guide.ConfirmListener
            public void onConfirmListener() {
                if (T3HomeDeviceView.this.guide3 != null) {
                    T3HomeDeviceView.this.guide3.dismiss();
                }
            }
        }));
        this.guide3 = guideBuilder.createGuide();
        this.contentView.post(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.widget.T3HomeDeviceView.22
            @Override // java.lang.Runnable
            public void run() {
                T3HomeDeviceView.this.guide3.show((Activity) T3HomeDeviceView.this.getContext());
            }
        });
    }

    public void showGuideView(View view) {
        if (!DataHelper.getBooleanSF(this.mContext, Consts.T3_HOME_DATA_GUIDE) && this.guide1 == null) {
            GuideBuilder guideBuilder = new GuideBuilder();
            guideBuilder.setTargetView(view).setAlpha(180).setHighTargetCorner(50).setHighTargetPaddingLeft(ArmsUtils.dip2px(this.mContext, 8.0f)).setHighTargetPaddingRight(ArmsUtils.dip2px(this.mContext, 8.0f)).setHighTargetPaddingBottom(ArmsUtils.dip2px(this.mContext, 8.0f)).setHighTargetPaddingTop(ArmsUtils.dip2px(this.mContext, 8.0f)).setOutsideTouchable(false).setAutoDismiss(true);
            guideBuilder.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() { // from class: com.petkit.android.activities.petkitBleDevice.widget.T3HomeDeviceView.23
                @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
                public void onShown() {
                }

                @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
                public void onDismiss() {
                    if (T3HomeDeviceView.this.bleMenuView.getVisibility() != 8) {
                        T3HomeDeviceView t3HomeDeviceView = T3HomeDeviceView.this;
                        t3HomeDeviceView.showGuideView2(t3HomeDeviceView.bleMenuView);
                    }
                }
            });
            guideBuilder.addComponent(new GuidePetTipAndBtnAndLineComponent(new GuideParam(getResources().getString(R.string.Home_device_guide_tips), "1/2", 4, 32, 0, 10, getResources().getString(R.string.Next_tip), R.layout.layout_feeder_guide_one), new ConfirmListener() { // from class: com.petkit.android.activities.petkitBleDevice.widget.T3HomeDeviceView.24
                @Override // com.petkit.android.activities.petkitBleDevice.w5.guide.ConfirmListener
                public void onConfirmListener() {
                    if (T3HomeDeviceView.this.guide1 != null) {
                        T3HomeDeviceView.this.guide1.dismiss();
                    }
                }
            }));
            this.guide1 = guideBuilder.createGuide();
            this.contentView.post(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.widget.T3HomeDeviceView.25
                @Override // java.lang.Runnable
                public void run() {
                    T3HomeDeviceView.this.guide1.show((Activity) T3HomeDeviceView.this.getContext());
                }
            });
        }
    }

    public void showGuideView2(View view) {
        if (this.guide2 != null) {
            return;
        }
        GuideBuilder guideBuilder = new GuideBuilder();
        guideBuilder.setTargetView(view).setAlpha(180).setHighTargetCorner(50).setHighTargetPaddingLeft(ArmsUtils.dip2px(this.mContext, 0.0f)).setHighTargetPaddingRight(ArmsUtils.dip2px(this.mContext, 0.0f)).setHighTargetPaddingBottom(ArmsUtils.dip2px(this.mContext, 0.0f)).setHighTargetPaddingTop(ArmsUtils.dip2px(this.mContext, 0.0f)).setOutsideTouchable(false).setAutoDismiss(true);
        guideBuilder.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() { // from class: com.petkit.android.activities.petkitBleDevice.widget.T3HomeDeviceView.26
            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onShown() {
            }

            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onDismiss() {
                DataHelper.setBooleanSF(T3HomeDeviceView.this.mContext, Consts.T3_HOME_DATA_GUIDE, Boolean.TRUE);
            }
        });
        guideBuilder.addComponent(new GuideTipAndBtnAndLineComponent(new GuideParam(getResources().getString(R.string.Home_menu_guide_tips), "2/2", 2, 48, 0, -10, getResources().getString(R.string.Done), R.layout.layout_home_guide_mine), new ConfirmListener() { // from class: com.petkit.android.activities.petkitBleDevice.widget.T3HomeDeviceView.27
            @Override // com.petkit.android.activities.petkitBleDevice.w5.guide.ConfirmListener
            public void onConfirmListener() {
                if (T3HomeDeviceView.this.guide2 != null) {
                    T3HomeDeviceView.this.guide2.dismiss();
                }
            }
        }));
        this.guide2 = guideBuilder.createGuide();
        this.contentView.post(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.widget.T3HomeDeviceView.28
            @Override // java.lang.Runnable
            public void run() {
                T3HomeDeviceView.this.guide2.show((Activity) T3HomeDeviceView.this.getContext());
            }
        });
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        MainHandler mainHandler = this.mainHandler;
        if (mainHandler != null) {
            mainHandler.removeCallbacksAndMessages(null);
        }
        stopCenterTimer();
        cancelAllAnimationAndTimer();
        super.onDetachedFromWindow();
    }

    public void setCurrentItem(int i) {
        this.t3Histogram.setCurrentItem(i);
    }

    @Subscriber
    public void refreshPetColor(UpdatePetColorMsg updatePetColorMsg) {
        PetkitLog.d("refreshPetColor");
        refreshT3Histogram();
        setDeviceRecord(this.t3DeviceRecordList, this.recordDate);
        this.petFilterWindow.refreshColor();
    }

    private void startExpandAni(final int i, int i2) {
        if (this.isRunning) {
            return;
        }
        ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(i2, i);
        this.expandValueAnimator = valueAnimatorOfInt;
        valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.petkit.android.activities.petkitBleDevice.widget.T3HomeDeviceView.29
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int iIntValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                T3HomeDeviceView t3HomeDeviceView = T3HomeDeviceView.this;
                if (t3HomeDeviceView.lastValue >= iIntValue || t3HomeDeviceView.llAccessoryConsumables == null) {
                    return;
                }
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) T3HomeDeviceView.this.llAccessoryConsumables.getLayoutParams();
                layoutParams.width = iIntValue;
                T3HomeDeviceView.this.llAccessoryConsumables.setLayoutParams(layoutParams);
                T3HomeDeviceView.this.lastValue = iIntValue;
            }
        });
        this.expandValueAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.petkit.android.activities.petkitBleDevice.widget.T3HomeDeviceView.30
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                T3HomeDeviceView t3HomeDeviceView = T3HomeDeviceView.this;
                t3HomeDeviceView.isRunning = false;
                t3HomeDeviceView.accessoryConsumables = 0;
                T3HomeDeviceView t3HomeDeviceView2 = T3HomeDeviceView.this;
                int i3 = i;
                t3HomeDeviceView2.startReduceAni(i3, i3 - t3HomeDeviceView2.tvWidth);
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
        valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.petkit.android.activities.petkitBleDevice.widget.T3HomeDeviceView.31
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int iIntValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                T3HomeDeviceView t3HomeDeviceView = T3HomeDeviceView.this;
                if (t3HomeDeviceView.lastValue <= iIntValue || t3HomeDeviceView.llAccessoryConsumables == null) {
                    return;
                }
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) T3HomeDeviceView.this.llAccessoryConsumables.getLayoutParams();
                layoutParams.width = iIntValue;
                T3HomeDeviceView.this.llAccessoryConsumables.setLayoutParams(layoutParams);
                T3HomeDeviceView.this.lastValue = iIntValue;
            }
        });
        this.reduceValueAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.petkit.android.activities.petkitBleDevice.widget.T3HomeDeviceView.32
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                T3HomeDeviceView.this.accessoryConsumables = 1;
                T3HomeDeviceView.this.isRunning = false;
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
        this.llAccessoryConsumables.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.petkit.android.activities.petkitBleDevice.widget.T3HomeDeviceView.33
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                T3HomeDeviceView t3HomeDeviceView = T3HomeDeviceView.this;
                t3HomeDeviceView.measuredWidth = t3HomeDeviceView.llAccessoryConsumables.getMeasuredWidth();
                T3HomeDeviceView t3HomeDeviceView2 = T3HomeDeviceView.this;
                t3HomeDeviceView2.lastValue = t3HomeDeviceView2.measuredWidth;
                T3HomeDeviceView t3HomeDeviceView3 = T3HomeDeviceView.this;
                t3HomeDeviceView3.tvWidth = t3HomeDeviceView3.tvAccessoryConsumables.getMeasuredWidth();
                T3HomeDeviceView t3HomeDeviceView4 = T3HomeDeviceView.this;
                t3HomeDeviceView4.startReduceAni(t3HomeDeviceView4.measuredWidth, T3HomeDeviceView.this.measuredWidth - T3HomeDeviceView.this.tvWidth);
                T3HomeDeviceView.this.llAccessoryConsumables.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }
}

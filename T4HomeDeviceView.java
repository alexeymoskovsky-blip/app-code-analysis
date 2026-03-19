package com.petkit.android.activities.petkitBleDevice.t4.widget;

import android.animation.Animator;
import android.animation.AnimatorInflater;
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
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.motion.widget.Key;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;
import com.binioter.guideview.Guide;
import com.binioter.guideview.GuideBuilder;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.Consts;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.community.WebviewActivity;
import com.petkit.android.activities.cozy.widget.EasyPopupWindow;
import com.petkit.android.activities.family.mode.FamilyInfor;
import com.petkit.android.activities.home.utils.GuidePetTipAndBtnAndLineComponent;
import com.petkit.android.activities.home.utils.GuideTipAndBtnAndLineComponent;
import com.petkit.android.activities.mall.mode.PromoteData;
import com.petkit.android.activities.mall.widget.PromoteView;
import com.petkit.android.activities.petkitBleDevice.BleDeviceBindNetWorkActivity;
import com.petkit.android.activities.petkitBleDevice.BleDeviceWifiSettingActivity;
import com.petkit.android.activities.petkitBleDevice.PetColorSettingActivity;
import com.petkit.android.activities.petkitBleDevice.k3.utils.K3Utils;
import com.petkit.android.activities.petkitBleDevice.k3.widget.K3BatteryWindow;
import com.petkit.android.activities.petkitBleDevice.k3.widget.K3LiquidWindow;
import com.petkit.android.activities.petkitBleDevice.mode.BleMenuItem;
import com.petkit.android.activities.petkitBleDevice.mode.TimeViewResult;
import com.petkit.android.activities.petkitBleDevice.mode.ToiletCompareResult;
import com.petkit.android.activities.petkitBleDevice.mode.UpdatePetColorMsg;
import com.petkit.android.activities.petkitBleDevice.t4.adapter.T4HomeHistoryRecordNewAdapter;
import com.petkit.android.activities.petkitBleDevice.t4.mode.RelatedProductsInfor;
import com.petkit.android.activities.petkitBleDevice.t4.mode.T4DeviceRecord;
import com.petkit.android.activities.petkitBleDevice.t4.mode.T4HistroyRecord;
import com.petkit.android.activities.petkitBleDevice.t4.mode.T4Record;
import com.petkit.android.activities.petkitBleDevice.t4.mode.T4StatisticInfo;
import com.petkit.android.activities.petkitBleDevice.t4.mode.T4StatisticResult;
import com.petkit.android.activities.petkitBleDevice.t4.utils.T4Utils;
import com.petkit.android.activities.petkitBleDevice.t4.widget.T4BarChartView;
import com.petkit.android.activities.petkitBleDevice.t4.widget.T4Histogram;
import com.petkit.android.activities.petkitBleDevice.utils.ColorUtils;
import com.petkit.android.activities.petkitBleDevice.utils.FamilyUtils;
import com.petkit.android.activities.petkitBleDevice.utils.TextUtil;
import com.petkit.android.activities.petkitBleDevice.utils.TimeUtils;
import com.petkit.android.activities.petkitBleDevice.utils.UnitUtils;
import com.petkit.android.activities.petkitBleDevice.w5.guide.ConfirmListener;
import com.petkit.android.activities.petkitBleDevice.w5.guide.GuideParam;
import com.petkit.android.activities.petkitBleDevice.widget.BleDeviceHomeMenuView;
import com.petkit.android.activities.petkitBleDevice.widget.BleDeviceHomeOfflineWarnWindow;
import com.petkit.android.activities.petkitBleDevice.widget.BleDeviceHomeTroubleWarnWindow;
import com.petkit.android.activities.petkitBleDevice.widget.GridMenuView;
import com.petkit.android.activities.petkitBleDevice.widget.ScrollViewWithListener2;
import com.petkit.android.activities.petkitBleDevice.widget.T4PetkitSlidingUpPanelLayout;
import com.petkit.android.activities.petkitBleDevice.widget.TipWindow;
import com.petkit.android.activities.universalWindow.BaseBottomWindow;
import com.petkit.android.activities.universalWindow.PetFilterWindow;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.model.Pet;
import com.petkit.android.model.RemindDetail;
import com.petkit.android.utils.CommonUtil;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.DateUtil;
import com.petkit.android.utils.LogcatStorageHelper;
import com.petkit.android.utils.PetkitLog;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.oversea.R;
import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

/* JADX INFO: loaded from: classes5.dex */
public class T4HomeDeviceView extends LinearLayout implements View.OnClickListener, T4PetkitSlidingUpPanelLayout.PanelSlideListener, ViewPager.OnPageChangeListener, T4HomeHistoryRecordNewAdapter.OnClickListener, BleDeviceHomeOfflineWarnWindow.OfflineClickListener, K3BatteryWindow.TipClickListener, BleDeviceHomeMenuView.OnMenuClickListener, GridMenuView.OnMenuClickListener, BaseBottomWindow.OnClickListener {
    private static final long MAINTENANCE_MODE_CYCLE = 15000;
    private int accessoryConsumables;
    private Activity activity;
    private AnimatorSet adAnimatorSet;
    private ObjectAnimator adXAnimator;
    private ObjectAnimator adYAnimator;
    private List<AnimatorSet> animatorLeftSetList;
    private List<AnimatorSet> animatorSetList;
    private BleDeviceHomeOfflineWarnWindow bleDeviceHomeOfflineWarnWindow;
    private BleDeviceHomeTroubleWarnWindow bleDeviceHomeTroubleWarnWindow;
    private ObjectAnimator bottomPanelDismissAnimator;
    private ObjectAnimator bottomPanelShowAnimator;
    private ObjectAnimator boxAlphaAnimator;
    private Button btnMaintenanceModeContinueToRun;
    private Button btnMaintenanceModePauseClean;
    private Button btnMaintenanceModeTerminateClean;
    private Button btnT4ContinueToRun;
    private Button btnT4PauseClean;
    private Button btnT4TerminateClean;
    private T4BarChartView.ChartOnClickListener chartOnClickListener;
    private boolean collapseFlag;
    private View contentView;
    private int currentPageSelected;
    private SimpleDateFormat dateFormat;
    private ObjectAnimator deodorantAlphaAnimator;
    private ObjectAnimator deodorantRunningAlphaAnimator;
    private ObjectAnimator deodorizationBoxAlphaAnimator;
    private ObjectAnimator deviceAlphaAnimator;
    Runnable enterMaintenance;
    ValueAnimator expandValueAnimator;
    private FamilyInfor familyInfor;
    private GridMenuView gridMenuView;
    private int gridMenuViewHeight;
    private Guide guide;
    private Guide guide1;
    private Guide guide2;
    private Guide guide3;
    private List<T4DeviceRecord> historyRecord;
    private ObjectAnimator horizontalAnimator;
    private AnimatorSet iconAnimatorSet;
    private ObjectAnimator iconXAnimator;
    private ObjectAnimator iconYAnimator;
    boolean isAniStart;
    private boolean isBoxConsumablesInletOpen;
    private boolean isDeodorizationBoxConsumablesInletOpen;
    private boolean isFirst;
    private boolean isInDisturbTime;
    private boolean isK3ConsumablesInletOpen;
    private boolean isLitterConsumablesInletOpen;
    private boolean isNeedShowPop;
    private boolean isReady;
    boolean isRunning;
    private boolean isScroll;
    private boolean isShowGuide;
    private boolean isTurnOnLight;
    private boolean isUnMask;
    boolean isUp;
    private ImageView ivAccessoryConsumables;
    private ImageView ivAni;
    private ImageView ivBoxConsumablesInlet;
    private ImageView ivCircleProgress;
    private ImageView ivClose;
    private ImageView ivDeodorizationBoxConsumablesInlet;
    private ImageView ivGif;
    private ImageView ivK3ConsumablesInlet;
    private ImageView ivLackLiquidBg;
    private ImageView ivLackLitterBg;
    private ImageView ivLeftArrow;
    private ImageView ivLitterConsumablesInlet;
    private ImageView ivMaintenanceDoubt;
    private ImageView ivMaintenanceLoading;
    private K3BatteryWindow k3BatteryWindow;
    private K3LiquidWindow k3LiquidWindow;
    private int lastMaintenanceType;
    int lastValue;
    private long leftStartCycle;
    private ObjectAnimator lightRotateAnimator;
    private ObjectAnimator lightTurnOffAnimator;
    private ObjectAnimator lightTurnOnAnimator;
    private ObjectAnimator liquideAlphaAnimator;
    private ObjectAnimator litterAlphaAnimator;
    private LinearLayout llAccessoryConsumables;
    private LinearLayout llBottomMenuParentView;
    private LinearLayout llBtnAndWarnPanel;
    private LinearLayout llMaintenanceMode;
    private LinearLayout llMaintenanceModeStatus;
    private LinearLayout llMaintenanceModeTop;
    private LinearLayout llNextAddLitterTime;
    private LinearLayout llOutside;
    private LinearLayout llPets;
    private LinearLayout llT4RecordView;
    private LinearLayout llTodayData;
    private LinearLayout llTodayDataPanel;
    private LinearLayout llWarnPanel;
    private LinearLayout llt4BtnPanel;
    private Context mContext;
    private float mSlideOffset;
    private VelocityTracker mVelocityTracker;
    private MainHandler mainHandler;
    private Animator maintenanceAnimator;
    private Guide maintenanceModeGuide;
    Runnable maintenanceRunTimer;
    private long maintenanceRunTimes;
    private int measuredWidth;
    private MenuOnClickListener menuOnClickListener;
    private NestedScrollView nsvRoot;
    private OnHistogramPageChange onHistogramPageChange;
    private OnLoadingDismiss onLoadingDismiss;
    private int panelHeight;
    private PetFilterWindow petFilterWindow;
    private View pointGuideView;
    private View pointGuideViewBlock;
    private View pointGuideViewBox;
    private View pointGuideViewLitter;
    private PopupWindow popupWindow;
    private PromoteView promoteView;
    String recordDate;
    private RecordOnClickListener recordOnClickListener;
    ValueAnimator reduceValueAnimator;
    private String refreshViewType;
    RelatedProductsInfor relatedProductsInfor;
    private RelativeLayout rlBoxConsumablesInlet;
    private RelativeLayout rlCenterTextPanel;
    private RelativeLayout rlChartGuide;
    private RelativeLayout rlDeodorizationBoxConsumablesInlet;
    private RelativeLayout rlDevice;
    private RelativeLayout rlK3ConsumablesInlet;
    private RelativeLayout rlLitterConsumablesInlet;
    private RelativeLayout rlMaintenanceModeBottom;
    private RelativeLayout rlMiddlePanel;
    private RelativeLayout rlParentPanel;
    private RelativeLayout rlPop;
    private RelativeLayout rlT4DeviceCenter;
    private RelativeLayout rlT4Light;
    private RelativeLayout rlText;
    private RelativeLayout rlTimePanel;
    private RelativeLayout rlTopPanel;
    private RelativeLayout rlTopView;
    private RelativeLayout rlWorkPanel;
    private ObjectAnimator rotateAnimator;
    private T4ScrollRecyclerView rvT4RecordView;
    private AnimatorSet scanAnimatorSet;
    private List<String> selectedPetIds;
    private boolean showDataDesc;
    private long startMaintenanceTimeStamp;
    private int statisticType;
    private NestedScrollView svMaintenanceModeIntroduces;
    private String symbol;
    private T4BarChartView t4BarChartView;
    private ImageView t4DeviceCenterWhiteBg;
    List<T4DeviceRecord> t4DeviceRecordList;
    private T4Histogram t4Histogram;
    private List<T4HistroyRecord> t4HistroyRecords;
    private T4HomeHistoryRecordNewAdapter t4HomeHistoryRecordAdapter;
    private T4Record t4Record;
    private EasyPopupWindow t4SandWindow;
    private T4StatisticResult t4StatisticResult;
    private ImageView t4ViewDeviceCenterBox;
    private ImageView t4ViewDeviceCenterK3;
    private ImageView t4ViewDeviceCenterK3Running;
    private ImageView t4ViewDeviceCenterMask;
    private ImageView t4ViewDeviceCenterScanBlueBg;
    private ImageView t4ViewDeviceCenterWhiteBg;
    private ScrollViewWithListener2 t4ViewLayout;
    private ImageView t4ViewStateIconLight;
    private ImageView t4ViewStateIconLightProgress;
    private ImageView t4ViewStateIconSoftMode;
    private float tempY;
    private List<Pet> thisDeviceFamilyPetList;
    private TipWindow tipWindow;
    private ToiletCompareResult toiletCompareResult;
    private TextView tvAccessoryConsumables;
    private TextView tvBoxConsumablesInlet;
    private TextView tvContent;
    private TextView tvContentPrompt;
    private TextView tvDeodorizationBoxConsumablesInlet;
    private TextView tvHistoryRecord;
    private TextView tvHistoryStatisticDate;
    private TextView tvHistoryStatisticDesc;
    private View tvHistoryStatisticDescLine;
    private TextView tvK3ConsumablesInlet;
    private TextView tvLegend;
    private TextView tvLitterConsumablesInlet;
    private TextView tvLittleWarnText;
    private TextView tvMaintenanceDesc;
    private TextView tvMaintenanceStatus;
    private TextView tvMore;
    private TextView tvNextAddLitterTime;
    private TextView tvPetNameOne;
    private TextView tvPetNameTwo;
    private TextView tvPetWeight;
    private TextView tvPopContent;
    private TextView tvSoftModePrompt;
    private TextView tvT4DeviceState;
    private TextView tvT4LightPrompt;
    private TextView tvT4ToiletTimeDate;
    private TextView tvT4ToiletTimeMonth;
    private TextView tvT4ToiletTimeWeek;
    private TextView tvTime;
    private TextView tvTimes;
    private TextView tvTitle;
    private TextView tvTodayAverage;
    private TextView tvTodayAverageComparedYesterday;
    private TextView tvTodayTime;
    private TextView tvTodayTimeComparedYesterday;
    private int tvWidth;
    private TextView tvWorkState;
    private String url;
    private View viewPointOne;
    private View viewPointTwo;
    private float widthScale;

    public interface MenuOnClickListener {
        void onBottomMenuClick(MenuType menuType);
    }

    public enum MenuType {
        DATE_PICKER,
        ON_OFF,
        CLEAN_UP,
        WARN_LIST,
        DEODORANT,
        SMART_SETTING,
        PAUSE_CLEAN,
        TERMINATE_CLEAN,
        CONTINUE_CLEAN,
        SOFT_MODE,
        LIGHT,
        RESET,
        BIND_K3,
        LIQUID_RESET,
        RESUME_RUN,
        CONSUMABLES_LITTER,
        CONSUMABLES_BOX,
        CONSUMABLES_LIQUID,
        BOX_ERROR,
        MAINTENANCE_MODE,
        UNUSED_DIALOG,
        UNUSED_DIALOG_2,
        PAUSE_MAINTENANCE,
        TERMINATE_MAINTENANCE,
        CONTINUE_MAINTENANCE,
        MAINTENANCE_MODE_INTRODUCE,
        CONSUMABLES_DEODORIZATION_BLOCK,
        EMPTY_CAT_LITTER,
        UNDERWEIGHT,
        NO_ASSOCIATED_K3,
        NEXT_ADD_REMIND
    }

    public interface OnHistogramPageChange {
        void pageChange(int i);

        void typeChange(int i);
    }

    public interface OnLoadingDismiss {
        void dismiss(MenuType menuType);
    }

    public interface RecordOnClickListener {
        void onClickRecord(T4DeviceRecord t4DeviceRecord, String str);

        void onDelRecord(T4DeviceRecord t4DeviceRecord, String str);

        void onUnknownPetClick(String str, String str2, String str3, double d);
    }

    private void lightTurnOffAnimation() {
    }

    private void lightTurnOnAnimation() {
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageScrolled(int i, float f, int i2) {
    }

    @Override // com.petkit.android.activities.petkitBleDevice.widget.T4PetkitSlidingUpPanelLayout.PanelSlideListener
    public void onPanelStateChanged(View view, T4PetkitSlidingUpPanelLayout.PanelState panelState, T4PetkitSlidingUpPanelLayout.PanelState panelState2) {
    }

    @Override // com.petkit.android.activities.universalWindow.BaseBottomWindow.OnClickListener
    public void onTitleBtn() {
    }

    @Override // com.petkit.android.activities.universalWindow.BaseBottomWindow.OnClickListener
    public void onTopLeftBtn() {
    }

    public void setT4HistoryRecordList(T4StatisticResult t4StatisticResult) {
    }

    public static /* synthetic */ long access$6714(T4HomeDeviceView t4HomeDeviceView, long j) {
        long j2 = t4HomeDeviceView.maintenanceRunTimes + j;
        t4HomeDeviceView.maintenanceRunTimes = j2;
        return j2;
    }

    public static /* synthetic */ long access$6738(T4HomeDeviceView t4HomeDeviceView, long j) {
        long j2 = t4HomeDeviceView.maintenanceRunTimes / j;
        t4HomeDeviceView.maintenanceRunTimes = j2;
        return j2;
    }

    public void setOnLoadingDismiss(OnLoadingDismiss onLoadingDismiss) {
        this.onLoadingDismiss = onLoadingDismiss;
    }

    public void setOnHistogramPageChange(OnHistogramPageChange onHistogramPageChange) {
        this.onHistogramPageChange = onHistogramPageChange;
    }

    @RequiresApi(api = 23)
    public T4HomeDeviceView(Context context) {
        super(context);
        this.historyRecord = new ArrayList();
        this.t4HistroyRecords = new ArrayList();
        this.widthScale = 0.84f;
        this.currentPageSelected = 20000;
        this.statisticType = 0;
        this.isTurnOnLight = false;
        this.isInDisturbTime = false;
        this.url = "";
        this.guide1 = null;
        this.guide2 = null;
        this.dateFormat = new SimpleDateFormat(DateUtil.DATE_FORMAT_7);
        this.isDeodorizationBoxConsumablesInletOpen = true;
        this.isBoxConsumablesInletOpen = true;
        this.isLitterConsumablesInletOpen = true;
        this.isK3ConsumablesInletOpen = true;
        this.selectedPetIds = new ArrayList(Arrays.asList(ColorUtils.ALL_PET));
        this.thisDeviceFamilyPetList = new ArrayList();
        this.isAniStart = false;
        this.isReady = false;
        this.panelHeight = 0;
        this.isFirst = true;
        this.isNeedShowPop = false;
        this.enterMaintenance = new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.t4.widget.T4HomeDeviceView.37
            @Override // java.lang.Runnable
            public void run() {
                T4HomeDeviceView.this.stopRotateAnimation();
                T4HomeDeviceView.this.rlWorkPanel.setVisibility(8);
                if (T4HomeDeviceView.this.t4Record.getTypeCode() == 2) {
                    T4HomeDeviceView.this.t4DeviceCenterWhiteBg.setImageResource(R.drawable.t4_home_device_maintenance_icon);
                } else {
                    T4HomeDeviceView.this.t4DeviceCenterWhiteBg.setImageResource(R.drawable.t4_2_home_device_maintenance_icon);
                }
                T4HomeDeviceView.this.t4ViewDeviceCenterMask.setVisibility(8);
                T4HomeDeviceView.this.startMaintenanceLoading();
                T4HomeDeviceView.this.llMaintenanceModeStatus.setGravity(3);
                T4HomeDeviceView.this.tvMaintenanceStatus.setPadding(0, 0, 0, 0);
                T4HomeDeviceView.this.tvMaintenanceDesc.setVisibility(0);
                T4HomeDeviceView.this.maintenanceRunTimes = System.currentTimeMillis() - (((long) T4HomeDeviceView.this.t4Record.getMaintenanceTime()) * 1000);
                T4HomeDeviceView.access$6738(T4HomeDeviceView.this, 1000L);
                PetkitLog.d("enterMaintenance,maintenanceTime:" + T4HomeDeviceView.this.t4Record.getMaintenanceTime() + ",maintenanceRunTimes:" + T4HomeDeviceView.this.maintenanceRunTimes);
                T4HomeDeviceView.this.mainHandler.removeCallbacks(T4HomeDeviceView.this.maintenanceRunTimer);
                T4HomeDeviceView.this.mainHandler.postDelayed(T4HomeDeviceView.this.maintenanceRunTimer, 0L);
                T4HomeDeviceView.this.clearMaintenanceTime("start");
            }
        };
        this.maintenanceRunTimer = new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.t4.widget.T4HomeDeviceView.38
            @Override // java.lang.Runnable
            public void run() {
                PetkitLog.d("maintenanceRunTimer,maintenanceRunTimes:" + T4HomeDeviceView.this.maintenanceRunTimes);
                T4HomeDeviceView.this.tvMaintenanceStatus.setText(T4HomeDeviceView.this.activity.getString(R.string.Maintenance_mode_time_consume, String.format("%02d:%02d", Long.valueOf(T4HomeDeviceView.this.maintenanceRunTimes / 60), Long.valueOf(T4HomeDeviceView.this.maintenanceRunTimes % 60))));
                T4HomeDeviceView.access$6714(T4HomeDeviceView.this, 1L);
                T4HomeDeviceView.this.mainHandler.postDelayed(this, 1000L);
            }
        };
        this.lastValue = 0;
        this.mContext = context;
        this.activity = (Activity) context;
        this.animatorSetList = new ArrayList();
        this.animatorLeftSetList = new ArrayList();
        initView();
    }

    @RequiresApi(api = 23)
    public T4HomeDeviceView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.historyRecord = new ArrayList();
        this.t4HistroyRecords = new ArrayList();
        this.widthScale = 0.84f;
        this.currentPageSelected = 20000;
        this.statisticType = 0;
        this.isTurnOnLight = false;
        this.isInDisturbTime = false;
        this.url = "";
        this.guide1 = null;
        this.guide2 = null;
        this.dateFormat = new SimpleDateFormat(DateUtil.DATE_FORMAT_7);
        this.isDeodorizationBoxConsumablesInletOpen = true;
        this.isBoxConsumablesInletOpen = true;
        this.isLitterConsumablesInletOpen = true;
        this.isK3ConsumablesInletOpen = true;
        this.selectedPetIds = new ArrayList(Arrays.asList(ColorUtils.ALL_PET));
        this.thisDeviceFamilyPetList = new ArrayList();
        this.isAniStart = false;
        this.isReady = false;
        this.panelHeight = 0;
        this.isFirst = true;
        this.isNeedShowPop = false;
        this.enterMaintenance = new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.t4.widget.T4HomeDeviceView.37
            @Override // java.lang.Runnable
            public void run() {
                T4HomeDeviceView.this.stopRotateAnimation();
                T4HomeDeviceView.this.rlWorkPanel.setVisibility(8);
                if (T4HomeDeviceView.this.t4Record.getTypeCode() == 2) {
                    T4HomeDeviceView.this.t4DeviceCenterWhiteBg.setImageResource(R.drawable.t4_home_device_maintenance_icon);
                } else {
                    T4HomeDeviceView.this.t4DeviceCenterWhiteBg.setImageResource(R.drawable.t4_2_home_device_maintenance_icon);
                }
                T4HomeDeviceView.this.t4ViewDeviceCenterMask.setVisibility(8);
                T4HomeDeviceView.this.startMaintenanceLoading();
                T4HomeDeviceView.this.llMaintenanceModeStatus.setGravity(3);
                T4HomeDeviceView.this.tvMaintenanceStatus.setPadding(0, 0, 0, 0);
                T4HomeDeviceView.this.tvMaintenanceDesc.setVisibility(0);
                T4HomeDeviceView.this.maintenanceRunTimes = System.currentTimeMillis() - (((long) T4HomeDeviceView.this.t4Record.getMaintenanceTime()) * 1000);
                T4HomeDeviceView.access$6738(T4HomeDeviceView.this, 1000L);
                PetkitLog.d("enterMaintenance,maintenanceTime:" + T4HomeDeviceView.this.t4Record.getMaintenanceTime() + ",maintenanceRunTimes:" + T4HomeDeviceView.this.maintenanceRunTimes);
                T4HomeDeviceView.this.mainHandler.removeCallbacks(T4HomeDeviceView.this.maintenanceRunTimer);
                T4HomeDeviceView.this.mainHandler.postDelayed(T4HomeDeviceView.this.maintenanceRunTimer, 0L);
                T4HomeDeviceView.this.clearMaintenanceTime("start");
            }
        };
        this.maintenanceRunTimer = new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.t4.widget.T4HomeDeviceView.38
            @Override // java.lang.Runnable
            public void run() {
                PetkitLog.d("maintenanceRunTimer,maintenanceRunTimes:" + T4HomeDeviceView.this.maintenanceRunTimes);
                T4HomeDeviceView.this.tvMaintenanceStatus.setText(T4HomeDeviceView.this.activity.getString(R.string.Maintenance_mode_time_consume, String.format("%02d:%02d", Long.valueOf(T4HomeDeviceView.this.maintenanceRunTimes / 60), Long.valueOf(T4HomeDeviceView.this.maintenanceRunTimes % 60))));
                T4HomeDeviceView.access$6714(T4HomeDeviceView.this, 1L);
                T4HomeDeviceView.this.mainHandler.postDelayed(this, 1000L);
            }
        };
        this.lastValue = 0;
        this.mContext = context;
        this.activity = (Activity) context;
        this.animatorSetList = new ArrayList();
        this.animatorLeftSetList = new ArrayList();
        initView();
    }

    @RequiresApi(api = 23)
    public T4HomeDeviceView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.historyRecord = new ArrayList();
        this.t4HistroyRecords = new ArrayList();
        this.widthScale = 0.84f;
        this.currentPageSelected = 20000;
        this.statisticType = 0;
        this.isTurnOnLight = false;
        this.isInDisturbTime = false;
        this.url = "";
        this.guide1 = null;
        this.guide2 = null;
        this.dateFormat = new SimpleDateFormat(DateUtil.DATE_FORMAT_7);
        this.isDeodorizationBoxConsumablesInletOpen = true;
        this.isBoxConsumablesInletOpen = true;
        this.isLitterConsumablesInletOpen = true;
        this.isK3ConsumablesInletOpen = true;
        this.selectedPetIds = new ArrayList(Arrays.asList(ColorUtils.ALL_PET));
        this.thisDeviceFamilyPetList = new ArrayList();
        this.isAniStart = false;
        this.isReady = false;
        this.panelHeight = 0;
        this.isFirst = true;
        this.isNeedShowPop = false;
        this.enterMaintenance = new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.t4.widget.T4HomeDeviceView.37
            @Override // java.lang.Runnable
            public void run() {
                T4HomeDeviceView.this.stopRotateAnimation();
                T4HomeDeviceView.this.rlWorkPanel.setVisibility(8);
                if (T4HomeDeviceView.this.t4Record.getTypeCode() == 2) {
                    T4HomeDeviceView.this.t4DeviceCenterWhiteBg.setImageResource(R.drawable.t4_home_device_maintenance_icon);
                } else {
                    T4HomeDeviceView.this.t4DeviceCenterWhiteBg.setImageResource(R.drawable.t4_2_home_device_maintenance_icon);
                }
                T4HomeDeviceView.this.t4ViewDeviceCenterMask.setVisibility(8);
                T4HomeDeviceView.this.startMaintenanceLoading();
                T4HomeDeviceView.this.llMaintenanceModeStatus.setGravity(3);
                T4HomeDeviceView.this.tvMaintenanceStatus.setPadding(0, 0, 0, 0);
                T4HomeDeviceView.this.tvMaintenanceDesc.setVisibility(0);
                T4HomeDeviceView.this.maintenanceRunTimes = System.currentTimeMillis() - (((long) T4HomeDeviceView.this.t4Record.getMaintenanceTime()) * 1000);
                T4HomeDeviceView.access$6738(T4HomeDeviceView.this, 1000L);
                PetkitLog.d("enterMaintenance,maintenanceTime:" + T4HomeDeviceView.this.t4Record.getMaintenanceTime() + ",maintenanceRunTimes:" + T4HomeDeviceView.this.maintenanceRunTimes);
                T4HomeDeviceView.this.mainHandler.removeCallbacks(T4HomeDeviceView.this.maintenanceRunTimer);
                T4HomeDeviceView.this.mainHandler.postDelayed(T4HomeDeviceView.this.maintenanceRunTimer, 0L);
                T4HomeDeviceView.this.clearMaintenanceTime("start");
            }
        };
        this.maintenanceRunTimer = new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.t4.widget.T4HomeDeviceView.38
            @Override // java.lang.Runnable
            public void run() {
                PetkitLog.d("maintenanceRunTimer,maintenanceRunTimes:" + T4HomeDeviceView.this.maintenanceRunTimes);
                T4HomeDeviceView.this.tvMaintenanceStatus.setText(T4HomeDeviceView.this.activity.getString(R.string.Maintenance_mode_time_consume, String.format("%02d:%02d", Long.valueOf(T4HomeDeviceView.this.maintenanceRunTimes / 60), Long.valueOf(T4HomeDeviceView.this.maintenanceRunTimes % 60))));
                T4HomeDeviceView.access$6714(T4HomeDeviceView.this, 1L);
                T4HomeDeviceView.this.mainHandler.postDelayed(this, 1000L);
            }
        };
        this.lastValue = 0;
        this.mContext = context;
        this.activity = (Activity) context;
        this.animatorSetList = new ArrayList();
        this.animatorLeftSetList = new ArrayList();
        initView();
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String str) {
        this.url = str;
    }

    @RequiresApi(api = 23)
    private void initView() {
        EventBus.getDefault().register(this);
        this.mainHandler = new MainHandler(this.mContext);
        this.mVelocityTracker = VelocityTracker.obtain();
        View viewInflate = LayoutInflater.from(this.mContext).inflate(R.layout.layout_t4_home_device_view, (ViewGroup) null);
        this.contentView = viewInflate;
        addView(viewInflate, -1, -1);
        this.ivLeftArrow = (ImageView) this.contentView.findViewById(R.id.iv_left_arrow);
        this.ivAccessoryConsumables = (ImageView) this.contentView.findViewById(R.id.iv_accessory_consumables);
        this.tvAccessoryConsumables = (TextView) this.contentView.findViewById(R.id.tv_accessory_consumables);
        this.llAccessoryConsumables = (LinearLayout) this.contentView.findViewById(R.id.ll_accessory_consumables);
        this.llPets = (LinearLayout) this.contentView.findViewById(R.id.ll_pets);
        this.llMaintenanceMode = (LinearLayout) this.contentView.findViewById(R.id.ll_maintenance_mode);
        this.llMaintenanceModeStatus = (LinearLayout) this.contentView.findViewById(R.id.ll_maintenance_mode_status);
        this.tvMaintenanceStatus = (TextView) this.contentView.findViewById(R.id.tv_maintenance_mode_status);
        this.tvMaintenanceDesc = (TextView) this.contentView.findViewById(R.id.tv_maintenance_mode_desc);
        this.ivMaintenanceLoading = (ImageView) this.contentView.findViewById(R.id.iv_maintenance_mode_loading);
        this.ivMaintenanceDoubt = (ImageView) this.contentView.findViewById(R.id.iv_maintenance_mode_doubt);
        this.btnMaintenanceModeTerminateClean = (Button) this.contentView.findViewById(R.id.btn_maintenance_mode_terminate_clean);
        this.btnMaintenanceModeContinueToRun = (Button) this.contentView.findViewById(R.id.btn_maintenance_mode_continue_to_run);
        this.btnMaintenanceModePauseClean = (Button) this.contentView.findViewById(R.id.btn_maintenance_mode_pause_clean);
        this.llMaintenanceModeTop = (LinearLayout) this.contentView.findViewById(R.id.ll_maintenance_mode_top);
        this.rlMaintenanceModeBottom = (RelativeLayout) this.contentView.findViewById(R.id.rl_maintenance_mode_bottom);
        this.svMaintenanceModeIntroduces = (NestedScrollView) this.contentView.findViewById(R.id.sv_maintenance_mode_introduces);
        this.btnMaintenanceModeTerminateClean.setOnClickListener(this);
        this.btnMaintenanceModeContinueToRun.setOnClickListener(this);
        this.btnMaintenanceModePauseClean.setOnClickListener(this);
        this.ivMaintenanceDoubt.setOnClickListener(this);
        this.llPets.setOnClickListener(this);
        GridMenuView gridMenuView = (GridMenuView) this.contentView.findViewById(R.id.grid_menu_view);
        this.gridMenuView = gridMenuView;
        gridMenuView.setDeviceType(15);
        this.gridMenuView.setOnMenuClickListener(this);
        initGridMenuView();
        RelativeLayout relativeLayout = (RelativeLayout) this.contentView.findViewById(R.id.rl_top_panel);
        this.rlTopPanel = relativeLayout;
        relativeLayout.setOnClickListener(this);
        this.llTodayData = (LinearLayout) this.contentView.findViewById(R.id.ll_today_data);
        this.tvMore = (TextView) this.contentView.findViewById(R.id.tv_history_more);
        TextView textView = (TextView) this.contentView.findViewById(R.id.tv_pop_content);
        this.tvPopContent = textView;
        textView.measure(0, 0);
        ImageView imageView = (ImageView) this.contentView.findViewById(R.id.iv_bottom_arrow);
        int measuredWidth = (this.tvPopContent.getMeasuredWidth() - ArmsUtils.dip2px(this.mContext, 10.0f)) - ArmsUtils.dip2px(this.mContext, 20.0f);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
        layoutParams.setMargins(measuredWidth, layoutParams.topMargin, layoutParams.rightMargin, layoutParams.bottomMargin);
        imageView.setLayoutParams(layoutParams);
        this.tvLegend = (TextView) this.contentView.findViewById(R.id.tv_legend);
        this.tvPetNameOne = (TextView) this.contentView.findViewById(R.id.tv_pet_name_one);
        this.tvPetNameTwo = (TextView) this.contentView.findViewById(R.id.tv_pet_name_two);
        this.viewPointTwo = this.contentView.findViewById(R.id.view_point_two);
        this.viewPointOne = this.contentView.findViewById(R.id.view_point_one);
        this.rlPop = (RelativeLayout) this.contentView.findViewById(R.id.rl_pop_parent);
        this.rlMiddlePanel = (RelativeLayout) this.contentView.findViewById(R.id.rl_middle_panel);
        this.rlText = (RelativeLayout) this.contentView.findViewById(R.id.rl_text);
        this.rlChartGuide = (RelativeLayout) this.contentView.findViewById(R.id.rl_chart_guide);
        ScrollViewWithListener2 scrollViewWithListener2 = (ScrollViewWithListener2) this.contentView.findViewById(R.id.t4_view_layout);
        this.t4ViewLayout = scrollViewWithListener2;
        scrollViewWithListener2.setScrollviewOnTouchListener(new ScrollViewWithListener2.ScrollviewOnTouchListener() { // from class: com.petkit.android.activities.petkitBleDevice.t4.widget.T4HomeDeviceView.1
            @Override // com.petkit.android.activities.petkitBleDevice.widget.ScrollViewWithListener2.ScrollviewOnTouchListener
            public void finishScroll(boolean z) {
            }

            @Override // com.petkit.android.activities.petkitBleDevice.widget.ScrollViewWithListener2.ScrollviewOnTouchListener
            public void onScrolling(int i, int i2, int i3, int i4) {
                if (i2 - i4 > 5) {
                    Log.d("scrolling", "up");
                    T4HomeDeviceView t4HomeDeviceView = T4HomeDeviceView.this;
                    t4HomeDeviceView.isUp = true;
                    if (!t4HomeDeviceView.isAniStart && t4HomeDeviceView.gridMenuView.getTranslationY() != T4HomeDeviceView.this.gridMenuViewHeight) {
                        T4HomeDeviceView t4HomeDeviceView2 = T4HomeDeviceView.this;
                        t4HomeDeviceView2.isAniStart = true;
                        t4HomeDeviceView2.startBottomPanelDismissAnimator();
                    }
                } else if (i4 - i2 > 5) {
                    T4HomeDeviceView t4HomeDeviceView3 = T4HomeDeviceView.this;
                    t4HomeDeviceView3.isUp = false;
                    if (!t4HomeDeviceView3.isAniStart && t4HomeDeviceView3.gridMenuView.getTranslationY() == T4HomeDeviceView.this.gridMenuViewHeight) {
                        T4HomeDeviceView t4HomeDeviceView4 = T4HomeDeviceView.this;
                        t4HomeDeviceView4.isAniStart = true;
                        t4HomeDeviceView4.startBottomPanelShowAnimator();
                    }
                }
                if ((((((T4HomeDeviceView.this.rlTimePanel.getHeight() + T4HomeDeviceView.this.rlTimePanel.getTop()) + T4HomeDeviceView.this.llBottomMenuParentView.getTop()) - T4HomeDeviceView.this.llT4RecordView.getHeight()) - T4HomeDeviceView.this.t4ViewLayout.getScrollY()) + ArmsUtils.dip2px(T4HomeDeviceView.this.getContext(), 30.0f)) - (T4HomeDeviceView.this.gridMenuView.getTop() - T4HomeDeviceView.this.contentView.getTop()) > 0 || DataHelper.getBooleanSF(T4HomeDeviceView.this.mContext, Consts.T4_CHART_IS_FIRST) || T4HomeDeviceView.this.isShowGuide) {
                    return;
                }
                T4HomeDeviceView.this.t4ViewLayout.fling(0);
                T4HomeDeviceView.this.t4ViewLayout.setOnTouchListener(new View.OnTouchListener() { // from class: com.petkit.android.activities.petkitBleDevice.t4.widget.T4HomeDeviceView.1.1
                    @Override // android.view.View.OnTouchListener
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        return true;
                    }
                });
                T4HomeDeviceView.this.isShowGuide = true;
                T4HomeDeviceView t4HomeDeviceView5 = T4HomeDeviceView.this;
                t4HomeDeviceView5.showRecordGuideView(t4HomeDeviceView5.rlChartGuide);
            }
        });
        this.ivAni = (ImageView) this.contentView.findViewById(R.id.iv_ani);
        this.tvTodayTime = (TextView) this.contentView.findViewById(R.id.tv_today_time);
        this.tvTodayAverage = (TextView) this.contentView.findViewById(R.id.tv_today_average_time);
        this.tvTodayTimeComparedYesterday = (TextView) this.contentView.findViewById(R.id.tv_today_time_compared_yesterday);
        this.tvTodayAverageComparedYesterday = (TextView) this.contentView.findViewById(R.id.tv_today_average_time_compared_yesterday);
        this.ivCircleProgress = (ImageView) this.contentView.findViewById(R.id.iv_circle_progress);
        this.tvWorkState = (TextView) this.contentView.findViewById(R.id.tv_working_state);
        this.rlWorkPanel = (RelativeLayout) this.contentView.findViewById(R.id.rl_work_panel);
        this.tvSoftModePrompt = (TextView) this.contentView.findViewById(R.id.tv_soft_mode_prompt);
        this.tvTime = (TextView) this.contentView.findViewById(R.id.tv_time);
        this.tvTimes = (TextView) this.contentView.findViewById(R.id.tv_times);
        this.rlCenterTextPanel = (RelativeLayout) this.contentView.findViewById(R.id.rl_center_text_panel);
        this.t4ViewStateIconLightProgress = (ImageView) this.contentView.findViewById(R.id.t4_view_state_icon_light_progress);
        this.rlT4Light = (RelativeLayout) this.contentView.findViewById(R.id.rl_t4_light);
        this.t4ViewStateIconLight = (ImageView) this.contentView.findViewById(R.id.t4_view_state_icon_light);
        this.t4ViewStateIconSoftMode = (ImageView) this.contentView.findViewById(R.id.t4_view_state_icon_soft_mode);
        this.t4ViewDeviceCenterMask = (ImageView) this.contentView.findViewById(R.id.t4_view_device_center_mask);
        this.t4ViewDeviceCenterScanBlueBg = (ImageView) this.contentView.findViewById(R.id.t4_view_device_center_scan_blue_bg);
        this.t4ViewDeviceCenterBox = (ImageView) this.contentView.findViewById(R.id.t4_view_device_center_box);
        this.t4ViewDeviceCenterK3 = (ImageView) this.contentView.findViewById(R.id.t4_view_device_center_k3);
        this.t4ViewDeviceCenterK3Running = (ImageView) this.contentView.findViewById(R.id.t4_view_device_center_k3_running);
        this.promoteView = (PromoteView) this.contentView.findViewById(R.id.promoteView);
        this.llTodayDataPanel = (LinearLayout) this.contentView.findViewById(R.id.ll_today_data_panel);
        this.llBtnAndWarnPanel = (LinearLayout) this.contentView.findViewById(R.id.ll_btn_and_warn_panel);
        this.rlTimePanel = (RelativeLayout) this.contentView.findViewById(R.id.rl_time_panel);
        this.rlTopView = (RelativeLayout) this.contentView.findViewById(R.id.rl_top_view);
        this.llWarnPanel = (LinearLayout) this.contentView.findViewById(R.id.ll_warn_panel);
        this.t4DeviceCenterWhiteBg = (ImageView) this.contentView.findViewById(R.id.t4_view_device_center_white_bg);
        this.rlT4DeviceCenter = (RelativeLayout) this.contentView.findViewById(R.id.rl_view_t4_device_center);
        this.btnT4TerminateClean = (Button) this.contentView.findViewById(R.id.btn_t4_view_terminate_clean);
        this.btnT4ContinueToRun = (Button) this.contentView.findViewById(R.id.btn_t4_view_continue_to_run);
        this.btnT4PauseClean = (Button) this.contentView.findViewById(R.id.btn_t4_view_pause_clean);
        this.llt4BtnPanel = (LinearLayout) this.contentView.findViewById(R.id.ll_t4_view_btn_panel);
        this.tvT4DeviceState = (TextView) this.contentView.findViewById(R.id.tv_warn_text);
        this.tvLittleWarnText = (TextView) this.contentView.findViewById(R.id.tv_little_warn_text);
        this.tvT4ToiletTimeDate = (TextView) this.contentView.findViewById(R.id.tv_t4_toilet_time_date);
        this.tvT4ToiletTimeWeek = (TextView) this.contentView.findViewById(R.id.tv_t4_toilet_time_week);
        this.tvT4ToiletTimeMonth = (TextView) this.contentView.findViewById(R.id.tv_t4_toilet_time_month);
        TextView textView2 = (TextView) this.contentView.findViewById(R.id.tv_history_statistic_date);
        this.tvHistoryStatisticDate = textView2;
        textView2.setOnTouchListener(new View.OnTouchListener() { // from class: com.petkit.android.activities.petkitBleDevice.t4.widget.T4HomeDeviceView.2
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == 1 && T4HomeDeviceView.this.tvHistoryStatisticDate.getCompoundDrawables()[2] != null && motionEvent.getX() > T4HomeDeviceView.this.tvHistoryStatisticDate.getWidth() - T4HomeDeviceView.this.tvHistoryStatisticDate.getCompoundDrawables()[2].getBounds().width()) {
                    T4HomeDeviceView.this.menuOnClickListener.onBottomMenuClick(MenuType.DATE_PICKER);
                }
                return true;
            }
        });
        this.tvHistoryStatisticDesc = (TextView) this.contentView.findViewById(R.id.tv_history_statistic_desc);
        this.tvHistoryStatisticDescLine = this.contentView.findViewById(R.id.history_statistic_guide_line);
        this.tvPetWeight = (TextView) this.contentView.findViewById(R.id.tv_pet_weight);
        this.ivLackLitterBg = (ImageView) this.contentView.findViewById(R.id.t4_view_device_center_litter);
        this.ivLackLiquidBg = (ImageView) this.contentView.findViewById(R.id.t4_view_device_center_liquid);
        this.rlDeodorizationBoxConsumablesInlet = (RelativeLayout) this.contentView.findViewById(R.id.rl_deodorization_box_state);
        this.rlBoxConsumablesInlet = (RelativeLayout) this.contentView.findViewById(R.id.rl_box_state);
        this.rlK3ConsumablesInlet = (RelativeLayout) this.contentView.findViewById(R.id.rl_liquid_state);
        this.rlLitterConsumablesInlet = (RelativeLayout) this.contentView.findViewById(R.id.rl_litter_state);
        this.ivDeodorizationBoxConsumablesInlet = (ImageView) this.contentView.findViewById(R.id.iv_deodorization_box_state);
        this.ivBoxConsumablesInlet = (ImageView) this.contentView.findViewById(R.id.iv_box_state);
        this.ivK3ConsumablesInlet = (ImageView) this.contentView.findViewById(R.id.iv_liquid_state);
        this.ivLitterConsumablesInlet = (ImageView) this.contentView.findViewById(R.id.iv_litter_state);
        this.tvDeodorizationBoxConsumablesInlet = (TextView) this.contentView.findViewById(R.id.tv_deodorization_box_state);
        this.tvBoxConsumablesInlet = (TextView) this.contentView.findViewById(R.id.tv_box_state);
        this.tvK3ConsumablesInlet = (TextView) this.contentView.findViewById(R.id.tv_liquid_state);
        this.tvLitterConsumablesInlet = (TextView) this.contentView.findViewById(R.id.tv_litter_state);
        this.pointGuideView = this.contentView.findViewById(R.id.point_guide_view);
        this.pointGuideViewLitter = this.contentView.findViewById(R.id.point_guide_view_litter);
        this.pointGuideViewBox = this.contentView.findViewById(R.id.point_guide_view_box);
        this.pointGuideViewBlock = this.contentView.findViewById(R.id.point_guide_view_block);
        this.tvT4LightPrompt = (TextView) this.contentView.findViewById(R.id.tv_t4_light_prompt);
        this.t4BarChartView = (T4BarChartView) this.contentView.findViewById(R.id.t4_bar_chart);
        T4Histogram t4Histogram = (T4Histogram) this.contentView.findViewById(R.id.t4_histogram);
        this.t4Histogram = t4Histogram;
        t4Histogram.setOnPageChanged(new T4Histogram.OnPageChanged() { // from class: com.petkit.android.activities.petkitBleDevice.t4.widget.T4HomeDeviceView.3
            @Override // com.petkit.android.activities.petkitBleDevice.t4.widget.T4Histogram.OnPageChanged
            public void change(int i) {
                T4HomeDeviceView.this.onHistogramPageChange.pageChange(i);
                T4HomeDeviceView.this.stopLeftAni();
            }
        });
        this.t4BarChartView.setXTextSize(ArmsUtils.dip2px(this.activity, 10.0f));
        this.symbol = "zh_CN".equalsIgnoreCase(UserInforUtils.getLanguageLocaleString(CommonUtils.getAppContext())) ? "" : " ";
        T4BarChartView.ChartOnClickListener chartOnClickListener = new T4BarChartView.ChartOnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t4.widget.T4HomeDeviceView.4
            @Override // com.petkit.android.activities.petkitBleDevice.t4.widget.T4BarChartView.ChartOnClickListener
            public void onChartClick(final int i, final int i2, final int i3) {
                T4HomeDeviceView.this.showDataDesc = true;
                T4HomeDeviceView.this.tvHistoryStatisticDate.setVisibility(8);
                T4HomeDeviceView.this.tvHistoryStatisticDesc.setVisibility(0);
                T4HomeDeviceView.this.tvHistoryStatisticDescLine.setVisibility(0);
                final RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) T4HomeDeviceView.this.tvHistoryStatisticDesc.getLayoutParams();
                if ((T4HomeDeviceView.this.statisticType != 1 || i >= 4) && (T4HomeDeviceView.this.statisticType != 2 || i >= 15)) {
                    T4HomeDeviceView.this.tvHistoryStatisticDesc.setBackground(T4HomeDeviceView.this.activity.getResources().getDrawable(R.drawable.t4_statistic_pet_toilet_desc_bg_right));
                } else {
                    T4HomeDeviceView.this.tvHistoryStatisticDesc.setBackground(T4HomeDeviceView.this.activity.getResources().getDrawable(R.drawable.t4_statistic_pet_toilet_desc));
                }
                T4HomeDeviceView.this.tvHistoryStatisticDesc.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.petkit.android.activities.petkitBleDevice.t4.widget.T4HomeDeviceView.4.1
                    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                    public void onGlobalLayout() {
                        if ((T4HomeDeviceView.this.statisticType != 1 || i < 4) && (T4HomeDeviceView.this.statisticType != 2 || i < 15)) {
                            layoutParams2.setMargins(i2 - ArmsUtils.dip2px(T4HomeDeviceView.this.activity, 8.0f), layoutParams2.topMargin, 0, 0);
                            T4HomeDeviceView.this.tvHistoryStatisticDesc.setLayoutParams(layoutParams2);
                        } else {
                            layoutParams2.setMargins((i2 + ArmsUtils.dip2px(T4HomeDeviceView.this.activity, 8.0f)) - T4HomeDeviceView.this.tvHistoryStatisticDesc.getWidth(), layoutParams2.topMargin, 0, 0);
                            T4HomeDeviceView.this.tvHistoryStatisticDesc.setLayoutParams(layoutParams2);
                        }
                        int height = (((RelativeLayout.LayoutParams) T4HomeDeviceView.this.t4BarChartView.getLayoutParams()).topMargin - (T4HomeDeviceView.this.tvHistoryStatisticDesc.getHeight() - ArmsUtils.dip2px(T4HomeDeviceView.this.activity, 8.0f))) + i3;
                        RelativeLayout.LayoutParams layoutParams3 = (RelativeLayout.LayoutParams) T4HomeDeviceView.this.tvHistoryStatisticDescLine.getLayoutParams();
                        layoutParams3.setMargins(i2, (ArmsUtils.dip2px(T4HomeDeviceView.this.activity, 2.0f) + T4HomeDeviceView.this.tvHistoryStatisticDesc.getHeight()) - ArmsUtils.dip2px(T4HomeDeviceView.this.activity, 8.0f), 0, 0);
                        layoutParams3.height = height;
                        T4HomeDeviceView.this.tvHistoryStatisticDescLine.setLayoutParams(layoutParams3);
                    }
                });
                T4StatisticInfo dataByPosition = T4HomeDeviceView.this.t4Histogram.getDataByPosition(i);
                T4HomeDeviceView.this.tvHistoryStatisticDesc.setText(String.format("%s %s %s", T4Utils.getFormatYMDDateFromString(dataByPosition.getStatisticDate()), String.format("%s" + T4HomeDeviceView.this.symbol + "%s", Integer.valueOf(dataByPosition.getPetTimes()), dataByPosition.getPetTimes() > 1 ? T4HomeDeviceView.this.activity.getResources().getString(R.string.Unit_times) : T4HomeDeviceView.this.activity.getResources().getString(R.string.Unit_time)), T4HomeDeviceView.this.totalTimeToStr(dataByPosition.getPetTotalTime())));
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t4.widget.T4BarChartView.ChartOnClickListener
            public void clearSelection() {
                if (T4HomeDeviceView.this.showDataDesc) {
                    if (T4HomeDeviceView.this.statisticType != 0) {
                        T4HomeDeviceView.this.tvHistoryStatisticDate.setVisibility(0);
                    }
                    T4HomeDeviceView.this.tvHistoryStatisticDesc.setVisibility(8);
                    T4HomeDeviceView.this.tvHistoryStatisticDescLine.setVisibility(8);
                    T4HomeDeviceView.this.t4BarChartView.setSelectPosition(0);
                    T4HomeDeviceView.this.showDataDesc = false;
                }
            }
        };
        this.chartOnClickListener = chartOnClickListener;
        this.t4BarChartView.setChartOnClickListener(chartOnClickListener);
        this.t4Histogram.setChartOnClickListener(this.chartOnClickListener);
        this.tvHistoryRecord = (TextView) this.contentView.findViewById(R.id.tv_history_record);
        this.tvNextAddLitterTime = (TextView) this.contentView.findViewById(R.id.tv_next_add_litter_time);
        this.llNextAddLitterTime = (LinearLayout) this.contentView.findViewById(R.id.ll_next_add_litter_time);
        this.rvT4RecordView = (T4ScrollRecyclerView) this.contentView.findViewById(R.id.rv_t4_recordView);
        this.llT4RecordView = (LinearLayout) this.contentView.findViewById(R.id.ll_t4_recordView);
        this.llBottomMenuParentView = (LinearLayout) this.contentView.findViewById(R.id.ll_bottom_menu_parent_view);
        this.rlDevice = (RelativeLayout) this.contentView.findViewById(R.id.rl_device);
        this.btnT4TerminateClean.setOnClickListener(this);
        this.btnT4ContinueToRun.setOnClickListener(this);
        this.btnT4ContinueToRun.setOnClickListener(this);
        this.tvT4DeviceState.setOnClickListener(this);
        this.btnT4PauseClean.setOnClickListener(this);
        this.tvT4ToiletTimeDate.setOnClickListener(this);
        this.tvT4ToiletTimeWeek.setOnClickListener(this);
        this.tvPetWeight.setOnClickListener(this);
        this.tvT4ToiletTimeMonth.setOnClickListener(this);
        this.t4ViewStateIconLight.setOnClickListener(this);
        this.t4ViewStateIconSoftMode.setOnClickListener(this);
        this.tvLittleWarnText.setOnClickListener(this);
        this.llNextAddLitterTime.setOnClickListener(this);
        this.llTodayData.setOnClickListener(this);
        this.tvMore.setOnClickListener(this);
        this.tvDeodorizationBoxConsumablesInlet.setOnClickListener(this);
        this.tvK3ConsumablesInlet.setOnClickListener(this);
        this.tvBoxConsumablesInlet.setOnClickListener(this);
        this.tvLitterConsumablesInlet.setOnClickListener(this);
        this.ivDeodorizationBoxConsumablesInlet.setOnClickListener(this);
        this.ivK3ConsumablesInlet.setOnClickListener(this);
        this.ivBoxConsumablesInlet.setOnClickListener(this);
        this.ivLitterConsumablesInlet.setOnClickListener(this);
        this.pointGuideViewBlock.setOnClickListener(this);
        this.pointGuideView.setOnClickListener(this);
        this.pointGuideViewLitter.setOnClickListener(this);
        this.pointGuideViewBox.setOnClickListener(this);
        this.llAccessoryConsumables.setOnClickListener(this);
        this.tvAccessoryConsumables.setOnClickListener(this);
        this.ivAccessoryConsumables.setOnClickListener(this);
        this.llNextAddLitterTime.setOnClickListener(this);
        this.activity.getWindow().getDecorView().setSystemUiVisibility(0);
        initViewSizeAndEvent();
        refreshView();
        if (!DataHelper.getBooleanSF(this.mContext, Constants.T4_MENU_STATUS)) {
            this.gridMenuView.setArrowIcon(R.drawable.t4_menu_arrow_down);
            this.gridMenuView.startDownArrowAnimation();
        } else {
            this.gridMenuView.setArrowIcon(R.drawable.t4_menu_arrow_up);
            this.gridMenuView.startUpArrowAnimation();
        }
        startProductAni();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startBottomPanelDismissAnimator() {
        ObjectAnimator objectAnimator = this.bottomPanelShowAnimator;
        if (objectAnimator != null && objectAnimator.isRunning()) {
            this.bottomPanelShowAnimator.cancel();
        }
        ObjectAnimator objectAnimator2 = this.bottomPanelDismissAnimator;
        if (objectAnimator2 != null && objectAnimator2.isRunning()) {
            this.bottomPanelDismissAnimator.cancel();
        }
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this.gridMenuView, Key.TRANSLATION_Y, r0.getLastTranslationY(), this.gridMenuViewHeight);
        this.bottomPanelDismissAnimator = objectAnimatorOfFloat;
        objectAnimatorOfFloat.setDuration(400L);
        this.bottomPanelDismissAnimator.setInterpolator(new LinearInterpolator());
        this.bottomPanelDismissAnimator.addListener(new Animator.AnimatorListener() { // from class: com.petkit.android.activities.petkitBleDevice.t4.widget.T4HomeDeviceView.5
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                T4HomeDeviceView.this.isAniStart = false;
            }
        });
        this.bottomPanelDismissAnimator.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startBottomPanelShowAnimator() {
        ObjectAnimator objectAnimator = this.bottomPanelShowAnimator;
        if (objectAnimator != null && objectAnimator.isRunning()) {
            this.bottomPanelShowAnimator.cancel();
        }
        ObjectAnimator objectAnimator2 = this.bottomPanelDismissAnimator;
        if (objectAnimator2 != null && objectAnimator2.isRunning()) {
            this.bottomPanelDismissAnimator.cancel();
        }
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this.gridMenuView, Key.TRANSLATION_Y, this.gridMenuViewHeight, r0.getLastTranslationY());
        this.bottomPanelShowAnimator = objectAnimatorOfFloat;
        objectAnimatorOfFloat.setDuration(400L);
        this.bottomPanelShowAnimator.setInterpolator(new LinearInterpolator());
        this.bottomPanelShowAnimator.addListener(new Animator.AnimatorListener() { // from class: com.petkit.android.activities.petkitBleDevice.t4.widget.T4HomeDeviceView.6
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                T4HomeDeviceView.this.isAniStart = false;
            }
        });
        this.bottomPanelShowAnimator.start();
    }

    private void initGridMenuView() {
        this.gridMenuViewHeight = ArmsUtils.dip2px(this.activity, 251.5f);
    }

    public void collapseMenuView() {
        if (this.collapseFlag) {
            return;
        }
        this.collapseFlag = true;
        this.gridMenuView.post(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.t4.widget.T4HomeDeviceView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$collapseMenuView$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$collapseMenuView$0() {
        if (this.gridMenuView.getTranslationY() == 0.0f) {
            this.gridMenuView.adsorbAnimator(true, 10);
        }
    }

    private void initViewSizeAndEvent() {
        int i = BaseApplication.displayMetrics.widthPixels;
        final ViewGroup.LayoutParams layoutParams = this.t4DeviceCenterWhiteBg.getLayoutParams();
        int i2 = (int) (i * this.widthScale);
        layoutParams.width = i2;
        layoutParams.height = (int) (i2 * 0.92063f);
        this.t4DeviceCenterWhiteBg.setLayoutParams(layoutParams);
        this.t4ViewDeviceCenterScanBlueBg.setLayoutParams(layoutParams);
        this.t4ViewDeviceCenterBox.setLayoutParams(layoutParams);
        this.t4ViewDeviceCenterK3.setLayoutParams(layoutParams);
        this.t4ViewDeviceCenterK3Running.setLayoutParams(layoutParams);
        this.t4ViewDeviceCenterMask.setLayoutParams(layoutParams);
        this.ivLackLitterBg.setLayoutParams(layoutParams);
        this.ivLackLiquidBg.setLayoutParams(layoutParams);
        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) this.rlBoxConsumablesInlet.getLayoutParams();
        layoutParams2.leftMargin = (int) ((layoutParams.width / 316.0f) * 222.0f);
        layoutParams2.topMargin = (int) ((layoutParams.height / 291.0f) * 236.0f);
        this.rlBoxConsumablesInlet.setLayoutParams(layoutParams2);
        RelativeLayout.LayoutParams layoutParams3 = (RelativeLayout.LayoutParams) this.rlLitterConsumablesInlet.getLayoutParams();
        layoutParams3.rightMargin = (int) ((layoutParams.width / 316.0f) * 215.0f);
        layoutParams3.topMargin = (int) ((layoutParams.height / 291.0f) * 158.0f);
        this.rlLitterConsumablesInlet.setLayoutParams(layoutParams3);
        RelativeLayout.LayoutParams layoutParams4 = (RelativeLayout.LayoutParams) this.rlK3ConsumablesInlet.getLayoutParams();
        layoutParams4.rightMargin = (int) ((layoutParams.width / 316.0f) * 215.0f);
        layoutParams4.topMargin = (int) ((layoutParams.height / 291.0f) * 46.0f);
        this.rlK3ConsumablesInlet.setLayoutParams(layoutParams4);
        RelativeLayout.LayoutParams layoutParams5 = (RelativeLayout.LayoutParams) this.rlDeodorizationBoxConsumablesInlet.getLayoutParams();
        layoutParams5.leftMargin = (int) ((layoutParams.width / 316.0f) * 222.0f);
        layoutParams5.topMargin = (int) ((layoutParams.height / 291.0f) * 176.0f);
        this.rlDeodorizationBoxConsumablesInlet.setLayoutParams(layoutParams5);
        this.llTodayDataPanel.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.petkit.android.activities.petkitBleDevice.t4.widget.T4HomeDeviceView.7
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                T4HomeDeviceView.this.mainHandler.postDelayed(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.t4.widget.T4HomeDeviceView.7.1
                    @Override // java.lang.Runnable
                    public void run() {
                        T4HomeDeviceView.this.refreshView();
                        T4HomeDeviceView.this.isReady = true;
                        if (T4HomeDeviceView.this.isNeedShowPop) {
                            T4HomeDeviceView.this.showPopWindow();
                        }
                    }
                }, 200L);
                int left = T4HomeDeviceView.this.t4DeviceCenterWhiteBg.getLeft();
                RelativeLayout.LayoutParams layoutParams6 = (RelativeLayout.LayoutParams) T4HomeDeviceView.this.rlCenterTextPanel.getLayoutParams();
                ViewGroup.LayoutParams layoutParams7 = layoutParams;
                layoutParams6.width = (int) ((layoutParams7.width / 315.0f) * 86.0f);
                layoutParams6.height = (int) ((layoutParams7.height / 290.0f) * 110.0f);
                layoutParams6.setMargins(left + ((int) ((layoutParams7.width / 315.0f) * 77.0f)), (int) ((layoutParams7.height / 290.0f) * 74.0f), 0, 0);
                layoutParams6.addRule(15);
                T4HomeDeviceView.this.rlCenterTextPanel.setLayoutParams(layoutParams6);
                T4HomeDeviceView.this.llTodayDataPanel.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        this.rlTopView.setOnTouchListener(new View.OnTouchListener() { // from class: com.petkit.android.activities.petkitBleDevice.t4.widget.T4HomeDeviceView.8
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (T4HomeDeviceView.this.llMaintenanceMode.getVisibility() == 0) {
                    return true;
                }
                T4HomeDeviceView.this.mVelocityTracker.addMovement(motionEvent);
                int action = motionEvent.getAction();
                if (action == 0) {
                    T4HomeDeviceView.this.tempY = motionEvent.getY();
                    Log.d("resOffset", "ACTION_DOWN:tempY:" + T4HomeDeviceView.this.tempY);
                } else if (action == 1) {
                    Log.d("resOffset", "ACTION_UP");
                    T4HomeDeviceView.this.mVelocityTracker.computeCurrentVelocity(1000);
                    Log.d("resOffset", "xVelocity:" + T4HomeDeviceView.this.mVelocityTracker.getXVelocity() + " yVelocity:" + T4HomeDeviceView.this.mVelocityTracker.getYVelocity());
                } else if (action == 2) {
                    float y = motionEvent.getY();
                    float unused = T4HomeDeviceView.this.tempY;
                    T4HomeDeviceView.this.tempY = y;
                }
                return true;
            }
        });
        this.llMaintenanceModeTop.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.petkit.android.activities.petkitBleDevice.t4.widget.T4HomeDeviceView.9
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                T4HomeDeviceView.this.llMaintenanceModeTop.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    public void setT4Record(T4Record t4Record) {
        this.t4Record = t4Record;
        initPetList(t4Record);
        this.familyInfor = FamilyUtils.getInstance().getFamilyInforThroughDevice(getContext(), t4Record.getDeviceId(), 15);
        refreshView();
    }

    private void initPetList(T4Record t4Record) {
        try {
            this.thisDeviceFamilyPetList = FamilyUtils.getInstance().getCatListByFamilyId(this.activity, FamilyUtils.getInstance().getFamilyInforThroughDevice(this.activity, t4Record.getDeviceId(), 15).getGroupId());
        } catch (Exception e) {
            PetkitLog.d("t4 getDevicePet error :" + e.getMessage());
            LogcatStorageHelper.addLog("t4 getDevicePet error :" + e.getMessage());
        }
        if (this.thisDeviceFamilyPetList == null) {
            this.thisDeviceFamilyPetList = new ArrayList();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:336:0x0e72  */
    /* JADX WARN: Removed duplicated region for block: B:337:0x0e8b  */
    /* JADX WARN: Removed duplicated region for block: B:342:0x0ea1  */
    /* JADX WARN: Removed duplicated region for block: B:343:0x0ea4  */
    /* JADX WARN: Removed duplicated region for block: B:348:0x0eb9  */
    /* JADX WARN: Removed duplicated region for block: B:352:0x0f1d  */
    /* JADX WARN: Removed duplicated region for block: B:360:0x0f91  */
    /* JADX WARN: Removed duplicated region for block: B:374:0x10d1  */
    /* JADX WARN: Removed duplicated region for block: B:380:0x111d  */
    /* JADX WARN: Removed duplicated region for block: B:382:0x1129  */
    /* JADX WARN: Removed duplicated region for block: B:383:0x112d  */
    /* JADX WARN: Removed duplicated region for block: B:502:0x193b  */
    /* JADX WARN: Removed duplicated region for block: B:504:0x1947  */
    /* JADX WARN: Removed duplicated region for block: B:505:0x194b  */
    /* JADX WARN: Removed duplicated region for block: B:508:0x195a  */
    /* JADX WARN: Removed duplicated region for block: B:512:0x1979  */
    /* JADX WARN: Removed duplicated region for block: B:516:0x19a7  */
    /* JADX WARN: Removed duplicated region for block: B:521:0x19c6  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void refreshView() {
        /*
            Method dump skipped, instruction units count: 6632
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.petkit.android.activities.petkitBleDevice.t4.widget.T4HomeDeviceView.refreshView():void");
    }

    public void refreshAddLitterTime(RemindDetail remindDetail) {
        if (remindDetail != null && remindDetail.getId() != null) {
            try {
                Date iSO8601Date = DateUtil.parseISO8601Date(remindDetail.getTime());
                if (iSO8601Date.compareTo(new Date()) < 0) {
                    this.tvNextAddLitterTime.setTextColor(getResources().getColor(R.color.remind_warn_red));
                } else {
                    this.tvNextAddLitterTime.setTextColor(getResources().getColor(R.color.light_black));
                }
                this.tvNextAddLitterTime.setText(new SimpleDateFormat("MM/dd").format(iSO8601Date));
                return;
            } catch (Exception e) {
                this.tvNextAddLitterTime.setTextColor(getResources().getColor(R.color.t4_text_gray));
                this.tvNextAddLitterTime.setText(this.mContext.getResources().getString(R.string.Not_set));
                e.printStackTrace();
                return;
            }
        }
        this.tvNextAddLitterTime.setTextColor(getResources().getColor(R.color.t4_text_gray));
        this.tvNextAddLitterTime.setText(this.mContext.getResources().getString(R.string.Not_set));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showPopWindow() {
        if (this.isFirst) {
            if (this.isReady) {
                this.tvT4DeviceState.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.petkit.android.activities.petkitBleDevice.t4.widget.T4HomeDeviceView.15
                    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                    public void onGlobalLayout() {
                        int top = T4HomeDeviceView.this.llBtnAndWarnPanel.getTop();
                        int top2 = T4HomeDeviceView.this.rlMiddlePanel.getTop();
                        int top3 = T4HomeDeviceView.this.rlText.getTop();
                        int top4 = T4HomeDeviceView.this.tvT4DeviceState.getTop();
                        int right = T4HomeDeviceView.this.tvT4DeviceState.getRight();
                        int measuredHeight = T4HomeDeviceView.this.rlPop.getMeasuredHeight();
                        int measuredWidth = T4HomeDeviceView.this.rlPop.getMeasuredWidth();
                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) T4HomeDeviceView.this.rlPop.getLayoutParams();
                        layoutParams.setMargins(right - measuredWidth, (((top + top2) + top3) + top4) - measuredHeight, layoutParams.rightMargin, layoutParams.bottomMargin);
                        T4HomeDeviceView.this.rlPop.setLayoutParams(layoutParams);
                        T4HomeDeviceView.this.mainHandler.postDelayed(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.t4.widget.T4HomeDeviceView.15.1
                            @Override // java.lang.Runnable
                            public void run() {
                                T4HomeDeviceView.this.rlPop.setVisibility(0);
                                T4HomeDeviceView.this.isFirst = false;
                                T4HomeDeviceView.this.isNeedShowPop = false;
                            }
                        }, 300L);
                        T4HomeDeviceView.this.tvT4DeviceState.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });
            } else {
                this.isNeedShowPop = true;
            }
        }
    }

    private void stopAdAni() {
        AnimatorSet animatorSet = this.adAnimatorSet;
        if (animatorSet != null) {
            animatorSet.cancel();
            this.adXAnimator = null;
            this.adYAnimator = null;
            this.adAnimatorSet = null;
        }
        AnimatorSet animatorSet2 = this.iconAnimatorSet;
        if (animatorSet2 != null) {
            animatorSet2.cancel();
            this.iconXAnimator = null;
            this.iconYAnimator = null;
            this.iconAnimatorSet = null;
        }
    }

    private void changeAllMenuState(boolean z) {
        this.isUnMask = z;
        this.gridMenuView.changeAllMask(!z);
    }

    private void resumeNormalMode() {
        if (this.gridMenuView.getVisibility() == 8) {
            this.gridMenuView.setVisibility(0);
            this.rlMiddlePanel.setVisibility(0);
            this.llMaintenanceMode.setVisibility(8);
            resetPanelHeight();
            if (this.t4Record.getTypeCode() == 2) {
                this.t4DeviceCenterWhiteBg.setImageResource(R.drawable.t4_2_home_device_icon);
            } else {
                this.t4DeviceCenterWhiteBg.setImageResource(R.drawable.t4_home_device_icon);
            }
            this.t4ViewDeviceCenterMask.setVisibility(0);
            this.mainHandler.removeCallbacks(this.enterMaintenance);
            this.mainHandler.removeCallbacks(this.maintenanceRunTimer);
            this.lastMaintenanceType = 0;
        }
    }

    public boolean isUnMask() {
        return this.isUnMask;
    }

    private void refreshTodayStatistics() {
        Resources resources;
        int i;
        int totalTime = this.t4Record.getTotalTime();
        int i2 = totalTime / 3600;
        int i3 = totalTime % 3600;
        int i4 = i3 / 60;
        int i5 = i3 % 60;
        if (this.t4Record.getInTimes() > 1) {
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
        StringBuilder sb = new StringBuilder(this.t4Record.getInTimes() + string);
        if (this.t4Record.getInTimes() > 0) {
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
        List<List<Integer>> petOutRecords = this.t4Record.getPetOutRecords();
        if (petOutRecords == null || petOutRecords.size() == 0) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (List<Integer> list : petOutRecords) {
            arrayList.add(new TimeViewResult(list.get(0).intValue(), list.get(1).intValue()));
        }
    }

    private void cancelAllAnimationAndTimer() {
        boxStateNormal();
        stopDeodorantAnimation();
        stopRotateAnimation();
        stopScanAnimation();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:194:0x05d1  */
    @Override // android.view.View.OnClickListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onClick(android.view.View r20) {
        /*
            Method dump skipped, instruction units count: 2554
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.petkit.android.activities.petkitBleDevice.t4.widget.T4HomeDeviceView.onClick(android.view.View):void");
    }

    private void showT4SandWindow() {
        EasyPopupWindow easyPopupWindow = this.t4SandWindow;
        if (easyPopupWindow == null || !easyPopupWindow.isShowing()) {
            String string = "";
            SpannableString spannableString = new SpannableString("");
            SpannableString spannableString2 = new SpannableString("");
            String string2 = this.mContext.getResources().getString(R.string.T4_sand_calibration);
            if (this.t4Record.getState().getSandStatus() == 1) {
                string = this.mContext.getResources().getString(R.string.T4_sand_adequate_title);
                String string3 = this.mContext.getResources().getString(R.string.T4_sand_inaccurate_prompt, this.mContext.getResources().getString(R.string.T4_sand_calibration));
                SpannableString spannableString3 = new SpannableString(string3);
                spannableString3.setSpan(new ClickableSpan() { // from class: com.petkit.android.activities.petkitBleDevice.t4.widget.T4HomeDeviceView.17
                    @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
                    public void updateDrawState(@NonNull TextPaint textPaint) {
                        textPaint.setUnderlineText(false);
                        textPaint.setColor(T4HomeDeviceView.this.mContext.getResources().getColor(R.color.t4_main_blue));
                    }

                    @Override // android.text.style.ClickableSpan
                    public void onClick(@NonNull View view) {
                        if (T4HomeDeviceView.this.menuOnClickListener != null) {
                            T4HomeDeviceView.this.menuOnClickListener.onBottomMenuClick(MenuType.RESET);
                        }
                        if (T4HomeDeviceView.this.t4SandWindow != null) {
                            T4HomeDeviceView.this.t4SandWindow.dismiss();
                            T4HomeDeviceView.this.t4SandWindow = null;
                        }
                    }
                }, string3.indexOf(string2), string3.indexOf(string2) + string2.length(), 17);
                spannableString = spannableString3;
            } else if (this.t4Record.getState().getSandStatus() == 2) {
                string = this.mContext.getResources().getString(R.string.T4_sand_fewer_title);
            } else if (this.t4Record.getState().getSandStatus() == 3) {
                string = this.mContext.getResources().getString(R.string.T4_sand_lack_title);
            }
            if (this.t4Record.getState().getSandStatus() == 2 || this.t4Record.getState().getSandStatus() == 3) {
                if (this.t4Record.getState().getSandStatus() == 2) {
                    spannableString = new SpannableString(this.mContext.getResources().getString(R.string.T4_sand_fewer_prompt));
                } else {
                    spannableString = new SpannableString(this.mContext.getResources().getString(R.string.T4_sand_lack_prompt));
                }
                String string4 = this.mContext.getResources().getString(R.string.T4_sand_manual_calibration_prompt, this.mContext.getResources().getString(R.string.T4_sand_calibration));
                SpannableString spannableString4 = new SpannableString(string4);
                spannableString4.setSpan(new ClickableSpan() { // from class: com.petkit.android.activities.petkitBleDevice.t4.widget.T4HomeDeviceView.18
                    @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
                    public void updateDrawState(@NonNull TextPaint textPaint) {
                        textPaint.setUnderlineText(false);
                        textPaint.setColor(T4HomeDeviceView.this.mContext.getResources().getColor(R.color.t4_main_blue));
                    }

                    @Override // android.text.style.ClickableSpan
                    public void onClick(@NonNull View view) {
                        if (T4HomeDeviceView.this.menuOnClickListener != null) {
                            T4HomeDeviceView.this.menuOnClickListener.onBottomMenuClick(MenuType.RESET);
                        }
                        if (T4HomeDeviceView.this.t4SandWindow != null) {
                            T4HomeDeviceView.this.t4SandWindow.dismiss();
                            T4HomeDeviceView.this.t4SandWindow = null;
                        }
                    }
                }, string4.indexOf(string2), string4.indexOf(string2) + string2.length(), 17);
                spannableString2 = spannableString4;
            }
            View viewInflate = LayoutInflater.from(this.mContext).inflate(R.layout.pop_t4_sand_prompt, (ViewGroup) null);
            this.llOutside = (LinearLayout) viewInflate.findViewById(R.id.ll_outside);
            this.tvTitle = (TextView) viewInflate.findViewById(R.id.tv_title);
            this.ivClose = (ImageView) viewInflate.findViewById(R.id.iv_close);
            this.ivGif = (ImageView) viewInflate.findViewById(R.id.iv_gif);
            this.tvContent = (TextView) viewInflate.findViewById(R.id.tv_content);
            this.tvContentPrompt = (TextView) viewInflate.findViewById(R.id.tv_content_prompt);
            this.rlParentPanel = (RelativeLayout) viewInflate.findViewById(R.id.rl_parent_panel);
            EasyPopupWindow easyPopupWindow2 = new EasyPopupWindow(this.mContext);
            this.t4SandWindow = easyPopupWindow2;
            easyPopupWindow2.setOutsideTouchable(false);
            this.ivClose.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t4.widget.T4HomeDeviceView$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$showT4SandWindow$1(view);
                }
            });
            ((BaseApplication) CommonUtils.getAppContext()).getAppComponent().imageLoader().loadImage(CommonUtils.getAppContext(), GlideImageConfig.builder().url(this.url).isGif(true).imageView(this.ivGif).build());
            this.tvTitle.setText(string);
            this.tvContent.setText(spannableString);
            this.tvContent.setMovementMethod(LinkMovementMethod.getInstance());
            this.tvContent.setHighlightColor(0);
            this.tvContentPrompt.setText(spannableString2);
            this.tvContentPrompt.setMovementMethod(LinkMovementMethod.getInstance());
            this.tvContentPrompt.setHighlightColor(0);
            if (this.t4Record.getState().getSandStatus() != 1) {
                this.tvContentPrompt.setVisibility(0);
            } else {
                this.tvContentPrompt.setVisibility(8);
            }
            RelativeLayout relativeLayout = (RelativeLayout) viewInflate.findViewById(R.id.rl_parent_panel);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) relativeLayout.getLayoutParams();
            layoutParams.setMargins(0, 0, 0, (int) (BaseApplication.displayMetrics.widthPixels * 0.05f));
            relativeLayout.setLayoutParams(layoutParams);
            this.t4SandWindow.setmShowAlpha(0.5f);
            this.t4SandWindow.setContentView(viewInflate);
            this.t4SandWindow.setHeight(-1);
            this.t4SandWindow.setWidth((int) (BaseApplication.displayMetrics.widthPixels * 0.9f));
            this.t4SandWindow.showAtLocation(this.activity.getWindow().getDecorView(), 17, 0, 0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showT4SandWindow$1(View view) {
        EasyPopupWindow easyPopupWindow = this.t4SandWindow;
        if (easyPopupWindow != null) {
            easyPopupWindow.dismiss();
            this.t4SandWindow = null;
        }
    }

    public void setStatistic(T4StatisticResult t4StatisticResult) {
        this.t4StatisticResult = t4StatisticResult;
        refreshStatistic();
    }

    private void refreshT4Histogram() {
        this.t4Histogram.updateBarChartData(this.statisticType, this.selectedPetIds);
    }

    public void refreshStatistic() {
        refreshT4Histogram();
        if (this.statisticType == 0) {
            this.tvHistoryRecord.setText(T4Utils.getNewHistoryRecordTitle(this.t4StatisticResult, this.mContext, this.selectedPetIds));
        } else {
            String str = totalTimeToStr(this.t4StatisticResult.getAvgTime());
            this.tvHistoryRecord.setText(T4Utils.getT4HistoryRecordTitle(this.activity.getResources().getString(R.string.Average_toilet, str), str));
        }
        if (!this.showDataDesc) {
            this.tvHistoryStatisticDate.setVisibility(0);
        }
        if (this.statisticType != 0) {
            if (this.t4StatisticResult.getStatisticTime().contains("-")) {
                this.tvHistoryStatisticDate.setText(String.format("%s-%s", T4Utils.getFormatYMDDateFromString(this.t4StatisticResult.getStatisticTime().split("-")[0]), T4Utils.getFormatYMDDateFromString(this.t4StatisticResult.getStatisticTime().split("-")[1])));
            }
        } else {
            this.tvHistoryStatisticDate.setText(T4Utils.getDayChartDate(this.t4StatisticResult, this.mContext));
        }
        this.tvHistoryStatisticDate.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.petkit.android.activities.petkitBleDevice.t4.widget.T4HomeDeviceView.19
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                int width = (T4HomeDeviceView.this.rlTopPanel.getWidth() - ArmsUtils.dip2px(T4HomeDeviceView.this.activity, 20.0f)) - T4HomeDeviceView.this.tvHistoryStatisticDate.getWidth();
                int width2 = T4HomeDeviceView.this.llPets.getWidth();
                PetkitLog.d("llPetsWidth:" + width2 + " left:" + width);
                if (width2 > width) {
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) T4HomeDeviceView.this.llPets.getLayoutParams();
                    int i = layoutParams.leftMargin;
                    int iDip2px = ArmsUtils.dip2px(T4HomeDeviceView.this.activity, 50.0f);
                    int i2 = layoutParams.rightMargin;
                    layoutParams.setMargins(i, iDip2px, i2, i2);
                    T4HomeDeviceView.this.llPets.setLayoutParams(layoutParams);
                } else {
                    RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) T4HomeDeviceView.this.llPets.getLayoutParams();
                    int i3 = layoutParams2.leftMargin;
                    int iDip2px2 = ArmsUtils.dip2px(T4HomeDeviceView.this.activity, 16.0f);
                    int i4 = layoutParams2.rightMargin;
                    layoutParams2.setMargins(i3, iDip2px2, i4, i4);
                    T4HomeDeviceView.this.llPets.setLayoutParams(layoutParams2);
                }
                T4HomeDeviceView.this.tvHistoryStatisticDate.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    public void setDeviceRecord(List<T4DeviceRecord> list, String str) {
        this.t4DeviceRecordList = list;
        this.recordDate = str;
        if (list == null) {
            return;
        }
        this.historyRecord.clear();
        for (T4DeviceRecord t4DeviceRecord : list) {
            if (t4DeviceRecord != null) {
                t4DeviceRecord.setTimezone(this.t4Record.getTimezone());
                this.historyRecord.add(t4DeviceRecord);
            }
        }
        Collections.sort(this.historyRecord);
        T4Utils.filterT4DeviceRecord(this.historyRecord, this.selectedPetIds);
        try {
            if (this.t4HomeHistoryRecordAdapter == null) {
                T4HomeHistoryRecordNewAdapter t4HomeHistoryRecordNewAdapter = new T4HomeHistoryRecordNewAdapter(this.activity, this.t4Record.getDeviceShared() != null, this.t4Record.getDeviceId(), this.familyInfor);
                this.t4HomeHistoryRecordAdapter = t4HomeHistoryRecordNewAdapter;
                t4HomeHistoryRecordNewAdapter.setListener(this);
                this.t4HomeHistoryRecordAdapter.setData(this.historyRecord, this.t4Record.getActualTimeZone(), str);
                this.rvT4RecordView.setAdapter(this.t4HomeHistoryRecordAdapter);
            } else {
                T4HomeHistoryRecordNewAdapter t4HomeHistoryRecordNewAdapter2 = new T4HomeHistoryRecordNewAdapter(this.activity, this.t4Record.getDeviceShared() != null, this.t4Record.getDeviceId(), this.familyInfor);
                this.t4HomeHistoryRecordAdapter = t4HomeHistoryRecordNewAdapter2;
                t4HomeHistoryRecordNewAdapter2.setListener(this);
                this.t4HomeHistoryRecordAdapter.setData(this.historyRecord, this.t4Record.getActualTimeZone(), str);
                this.rvT4RecordView.setAdapter(this.t4HomeHistoryRecordAdapter);
            }
        } catch (Exception e) {
            PetkitLog.d(e.getMessage());
            LogcatStorageHelper.addLog("t4 add history adapter error:" + e.getMessage());
        }
        this.rvT4RecordView.setLayoutManager(new LinearLayoutManager(this.mContext));
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        for (int i = 0; i < this.t4HomeHistoryRecordAdapter.getDataList().size(); i++) {
            if (this.t4HomeHistoryRecordAdapter.getDataList().get(i).getEventType() == 10) {
                String petId = this.t4HomeHistoryRecordAdapter.getDataList().get(i).getPetId();
                String petName = this.t4HomeHistoryRecordAdapter.getDataList().get(i).getPetName();
                if (TextUtils.isEmpty(petId) || Integer.parseInt(petId) <= 0) {
                    linkedHashMap2.put("-1", ColorUtils.getPetColorById(petId, petName));
                } else {
                    linkedHashMap2.put(petId, ColorUtils.getPetColorById(petId, petName));
                    linkedHashMap.put(petId, petName);
                }
            }
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
        FamilyInfor familyInfor;
        this.rlLitterConsumablesInlet.setVisibility(8);
        this.rlK3ConsumablesInlet.setVisibility(0);
        this.rlBoxConsumablesInlet.setVisibility(0);
        this.rlDeodorizationBoxConsumablesInlet.setVisibility(0);
        if (this.t4Record.getK3Id() == 0) {
            boolean sysBoolMap = CommonUtils.getSysBoolMap(this.mContext, Consts.D4_TAG + this.t4Record.getDeviceId(), true);
            if (K3Utils.getK3Number(getContext(), this.familyInfor) > 0) {
                if (this.t4Record.getDeviceShared() != null || this.familyInfor == null) {
                    this.rlK3ConsumablesInlet.setVisibility(8);
                    this.gridMenuView.hideLightingAndDeodorization();
                } else if (FamilyUtils.getInstance().isAppointFamilyOwner(getContext(), this.familyInfor.getGroupId())) {
                    this.rlK3ConsumablesInlet.setVisibility(sysBoolMap ? 0 : 8);
                } else {
                    this.rlK3ConsumablesInlet.setVisibility(sysBoolMap ? 0 : 8);
                }
            } else if (this.t4Record.getDeviceShared() == null && (familyInfor = this.familyInfor) != null && familyInfor != null && FamilyUtils.getInstance().isAppointFamilyOwner(getContext(), this.familyInfor.getGroupId())) {
                this.rlK3ConsumablesInlet.setVisibility(sysBoolMap ? 0 : 8);
            } else {
                this.rlK3ConsumablesInlet.setVisibility(8);
                this.gridMenuView.hideLightingAndDeodorization();
            }
        }
        Context context = this.mContext;
        StringBuilder sb = new StringBuilder();
        sb.append(Consts.T4_DEODORIZATION_TAG);
        sb.append(this.t4Record.getDeviceId());
        this.rlDeodorizationBoxConsumablesInlet.setVisibility(CommonUtils.getSysBoolMap(context, sb.toString(), true) ? 0 : 8);
    }

    private void setConsumablesInletGone() {
        this.rlLitterConsumablesInlet.setVisibility(8);
        this.rlK3ConsumablesInlet.setVisibility(8);
        this.rlBoxConsumablesInlet.setVisibility(8);
        this.rlDeodorizationBoxConsumablesInlet.setVisibility(8);
        this.t4ViewDeviceCenterBox.setVisibility(8);
        this.ivLackLitterBg.setVisibility(8);
        this.ivLackLiquidBg.setVisibility(8);
    }

    private void startDeodorantAnimation() {
        if (this.deviceAlphaAnimator == null) {
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this.rlT4DeviceCenter, Key.ALPHA, 1.0f, 0.5f);
            this.deviceAlphaAnimator = objectAnimatorOfFloat;
            objectAnimatorOfFloat.setDuration(1000L);
            this.deviceAlphaAnimator.setInterpolator(new LinearInterpolator());
            this.deviceAlphaAnimator.setRepeatCount(0);
            this.deviceAlphaAnimator.setRepeatMode(1);
            this.deviceAlphaAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.petkit.android.activities.petkitBleDevice.t4.widget.T4HomeDeviceView.20
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator) {
                    super.onAnimationStart(animator);
                    if (T4HomeDeviceView.this.onLoadingDismiss != null) {
                        T4HomeDeviceView.this.onLoadingDismiss.dismiss(MenuType.DEODORANT);
                    }
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    if (T4HomeDeviceView.this.deodorantAlphaAnimator == null) {
                        T4HomeDeviceView.this.t4ViewDeviceCenterK3.setVisibility(0);
                        T4HomeDeviceView t4HomeDeviceView = T4HomeDeviceView.this;
                        t4HomeDeviceView.deodorantAlphaAnimator = ObjectAnimator.ofFloat(t4HomeDeviceView.t4ViewDeviceCenterK3, Key.ALPHA, 0.0f, 1.0f);
                        T4HomeDeviceView.this.deodorantAlphaAnimator.setDuration(2000L);
                        T4HomeDeviceView.this.deodorantAlphaAnimator.setInterpolator(new LinearInterpolator());
                        T4HomeDeviceView.this.deodorantAlphaAnimator.setRepeatCount(0);
                        T4HomeDeviceView.this.deodorantAlphaAnimator.setRepeatMode(1);
                        T4HomeDeviceView.this.deodorantAlphaAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.petkit.android.activities.petkitBleDevice.t4.widget.T4HomeDeviceView.20.1
                            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                            public void onAnimationEnd(Animator animator2) {
                                super.onAnimationEnd(animator2);
                                if (T4HomeDeviceView.this.deodorantRunningAlphaAnimator == null) {
                                    T4HomeDeviceView.this.t4ViewDeviceCenterK3.setVisibility(0);
                                    T4HomeDeviceView.this.t4ViewDeviceCenterK3Running.setVisibility(0);
                                    T4HomeDeviceView t4HomeDeviceView2 = T4HomeDeviceView.this;
                                    t4HomeDeviceView2.deodorantRunningAlphaAnimator = ObjectAnimator.ofFloat(t4HomeDeviceView2.t4ViewDeviceCenterK3Running, Key.ALPHA, 0.0f, 1.0f, 0.0f);
                                    T4HomeDeviceView.this.deodorantRunningAlphaAnimator.setDuration(2000L);
                                    T4HomeDeviceView.this.deodorantRunningAlphaAnimator.setInterpolator(new LinearInterpolator());
                                    T4HomeDeviceView.this.deodorantRunningAlphaAnimator.setRepeatCount(-1);
                                    T4HomeDeviceView.this.deodorantRunningAlphaAnimator.setRepeatMode(1);
                                    T4HomeDeviceView.this.deodorantRunningAlphaAnimator.start();
                                }
                            }
                        });
                        T4HomeDeviceView.this.deodorantAlphaAnimator.start();
                    }
                }
            });
            this.deviceAlphaAnimator.start();
        }
    }

    private void deodorizationBoxStateFull() {
        TextView textView = this.tvDeodorizationBoxConsumablesInlet;
        StringBuilder sb = new StringBuilder();
        sb.append(this.mContext.getResources().getString(R.string.Deodoration_block));
        sb.append(": ");
        sb.append(this.mContext.getString(R.string.Something_left, UnitUtils.getInstance().getNumAndUnitWithXString(this.t4Record.getState().getDeodorantLeftDays() < 0 ? 0 : this.t4Record.getState().getDeodorantLeftDays(), this.mContext.getString(R.string.Unit_some_day), this.mContext.getString(R.string.Unit_some_days))));
        textView.setText(sb.toString());
        this.tvDeodorizationBoxConsumablesInlet.setBackground(this.mContext.getResources().getDrawable(R.drawable.shape_t4_state_red_bg));
        this.ivDeodorizationBoxConsumablesInlet.setImageResource(R.drawable.t4_state_red_icon);
        ObjectAnimator objectAnimator = this.deodorizationBoxAlphaAnimator;
        if (objectAnimator == null || !objectAnimator.isRunning()) {
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this.ivDeodorizationBoxConsumablesInlet, Key.ALPHA, 0.0f, 1.0f, 0.0f);
            this.deodorizationBoxAlphaAnimator = objectAnimatorOfFloat;
            objectAnimatorOfFloat.setDuration(2000L);
            this.deodorizationBoxAlphaAnimator.setInterpolator(new LinearInterpolator());
            this.deodorizationBoxAlphaAnimator.setRepeatCount(-1);
            this.deodorizationBoxAlphaAnimator.setRepeatMode(1);
            this.deodorizationBoxAlphaAnimator.start();
            this.mainHandler.postDelayed(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.t4.widget.T4HomeDeviceView.21
                @Override // java.lang.Runnable
                public void run() {
                    if (T4HomeDeviceView.this.deodorizationBoxAlphaAnimator != null) {
                        T4HomeDeviceView.this.deodorizationBoxAlphaAnimator.cancel();
                    }
                }
            }, 7000L);
        }
    }

    private void deodorizationBoxStateNormal() {
        TextView textView = this.tvDeodorizationBoxConsumablesInlet;
        StringBuilder sb = new StringBuilder();
        sb.append(this.mContext.getResources().getString(R.string.Deodoration_block));
        sb.append(": ");
        sb.append(this.mContext.getString(R.string.Something_left, UnitUtils.getInstance().getNumAndUnitWithXString(this.t4Record.getState().getDeodorantLeftDays() < 0 ? 0 : this.t4Record.getState().getDeodorantLeftDays(), this.mContext.getString(R.string.Unit_some_day), this.mContext.getString(R.string.Unit_some_days))));
        textView.setText(sb.toString());
        this.tvDeodorizationBoxConsumablesInlet.setBackground(this.mContext.getResources().getDrawable(R.drawable.shape_t4_state_blue_bg));
        this.ivDeodorizationBoxConsumablesInlet.setImageResource(R.drawable.t4_state_blue_icon);
        ObjectAnimator objectAnimator = this.boxAlphaAnimator;
        if (objectAnimator != null) {
            objectAnimator.cancel();
            this.boxAlphaAnimator = null;
        }
    }

    private void boxStateFull() {
        this.t4ViewDeviceCenterBox.setVisibility(0);
        changeAllMenuState(false);
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
            this.mainHandler.postDelayed(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.t4.widget.T4HomeDeviceView.22
                @Override // java.lang.Runnable
                public void run() {
                    if (T4HomeDeviceView.this.boxAlphaAnimator != null) {
                        T4HomeDeviceView.this.boxAlphaAnimator.cancel();
                    }
                }
            }, 7000L);
        }
    }

    private void boxStateUnsinstall() {
        this.t4ViewDeviceCenterBox.setVisibility(0);
        changeAllMenuState(false);
        this.tvBoxConsumablesInlet.setText(this.mContext.getResources().getString(R.string.T4_box_not_install));
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
            this.mainHandler.postDelayed(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.t4.widget.T4HomeDeviceView.23
                @Override // java.lang.Runnable
                public void run() {
                    if (T4HomeDeviceView.this.boxAlphaAnimator != null) {
                        T4HomeDeviceView.this.boxAlphaAnimator.cancel();
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

    private void litterStateEmpty() {
        stopScanAnimation();
        this.ivLackLitterBg.setVisibility(0);
        this.tvLitterConsumablesInlet.setText(this.mContext.getResources().getString(R.string.T4_litter_empty));
        this.tvLitterConsumablesInlet.setBackground(this.mContext.getResources().getDrawable(R.drawable.shape_t4_state_red_bg));
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
            this.mainHandler.postDelayed(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.t4.widget.T4HomeDeviceView.24
                @Override // java.lang.Runnable
                public void run() {
                    if (T4HomeDeviceView.this.litterAlphaAnimator != null) {
                        T4HomeDeviceView.this.litterAlphaAnimator.cancel();
                        T4HomeDeviceView.this.litterAlphaAnimator = null;
                    }
                }
            }, 7000L);
        }
    }

    private void litterStateLess() {
        stopScanAnimation();
        this.ivLackLitterBg.setVisibility(0);
        this.tvLitterConsumablesInlet.setText(this.mContext.getResources().getString(R.string.T4_litter_less));
        this.tvLitterConsumablesInlet.setBackground(this.mContext.getResources().getDrawable(R.drawable.shape_t4_state_red_bg));
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
            this.mainHandler.postDelayed(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.t4.widget.T4HomeDeviceView.25
                @Override // java.lang.Runnable
                public void run() {
                    if (T4HomeDeviceView.this.litterAlphaAnimator != null) {
                        T4HomeDeviceView.this.litterAlphaAnimator.cancel();
                        T4HomeDeviceView.this.litterAlphaAnimator = null;
                    }
                }
            }, 7000L);
        }
    }

    private void litterStateFull() {
        this.ivLackLitterBg.setVisibility(8);
        this.tvLitterConsumablesInlet.setText(this.mContext.getResources().getString(R.string.T4_litter_full));
        this.tvLitterConsumablesInlet.setBackground(this.mContext.getResources().getDrawable(R.drawable.shape_t4_state_blue_bg));
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
        this.tvK3ConsumablesInlet.setBackground(this.mContext.getResources().getDrawable(R.drawable.shape_t4_state_red_bg));
        this.tvK3ConsumablesInlet.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, this.mContext.getResources().getDrawable(R.drawable.right_arrow_black_small_icon), (Drawable) null);
        this.tvK3ConsumablesInlet.setTextColor(this.mContext.getResources().getColor(R.color.light_black));
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
            this.mainHandler.postDelayed(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.t4.widget.T4HomeDeviceView.26
                @Override // java.lang.Runnable
                public void run() {
                    if (T4HomeDeviceView.this.liquideAlphaAnimator != null) {
                        T4HomeDeviceView.this.liquideAlphaAnimator.cancel();
                        T4HomeDeviceView.this.liquideAlphaAnimator = null;
                    }
                }
            }, 7000L);
        }
    }

    private void liquidStateFull() {
        this.ivLackLiquidBg.setVisibility(8);
        this.tvK3ConsumablesInlet.setText(this.mContext.getResources().getString(R.string.K3_Liquid_adequate_title));
        this.tvK3ConsumablesInlet.setTextColor(this.mContext.getResources().getColor(R.color.light_black));
        this.tvK3ConsumablesInlet.setBackground(this.mContext.getResources().getDrawable(R.drawable.shape_t4_state_blue_bg));
        this.tvK3ConsumablesInlet.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, this.mContext.getResources().getDrawable(R.drawable.right_arrow_black_small_icon), (Drawable) null);
        this.ivK3ConsumablesInlet.setImageResource(R.drawable.t4_state_blue_icon);
        ObjectAnimator objectAnimator = this.liquideAlphaAnimator;
        if (objectAnimator != null) {
            objectAnimator.cancel();
            this.liquideAlphaAnimator = null;
        }
    }

    private void liquidStateUnbind() {
        this.ivLackLiquidBg.setVisibility(8);
        this.tvK3ConsumablesInlet.setText(this.mContext.getResources().getString(R.string.T4_unbind_k3));
        this.tvK3ConsumablesInlet.setTextColor(this.mContext.getResources().getColor(R.color.white));
        this.tvK3ConsumablesInlet.setCompoundDrawablesWithIntrinsicBounds(this.mContext.getResources().getDrawable(R.drawable.k3_unbind_icon), (Drawable) null, this.mContext.getResources().getDrawable(R.drawable.right_arrow_white_small_icon), (Drawable) null);
        this.tvK3ConsumablesInlet.setBackground(this.mContext.getResources().getDrawable(R.drawable.shape_t4_state_gray_bg));
        this.ivK3ConsumablesInlet.setImageResource(R.drawable.t4_state_gray_icon);
        ObjectAnimator objectAnimator = this.liquideAlphaAnimator;
        if (objectAnimator != null) {
            objectAnimator.cancel();
            this.liquideAlphaAnimator = null;
        }
    }

    private void liquidStateUnlink() {
        this.ivLackLiquidBg.setVisibility(8);
        this.tvK3ConsumablesInlet.setText(this.mContext.getResources().getString(R.string.T4_unlink_k3));
        this.tvK3ConsumablesInlet.setBackground(this.mContext.getResources().getDrawable(R.drawable.shape_t4_state_gray_bg));
        this.tvK3ConsumablesInlet.setTextColor(this.mContext.getResources().getColor(R.color.white));
        this.tvK3ConsumablesInlet.setCompoundDrawablesWithIntrinsicBounds(this.mContext.getResources().getDrawable(R.drawable.k3_unbind_icon), (Drawable) null, this.mContext.getResources().getDrawable(R.drawable.right_arrow_white_small_icon), (Drawable) null);
        this.ivK3ConsumablesInlet.setImageResource(R.drawable.t4_state_gray_icon);
        ObjectAnimator objectAnimator = this.liquideAlphaAnimator;
        if (objectAnimator != null) {
            objectAnimator.cancel();
            this.liquideAlphaAnimator = null;
        }
    }

    private void checkDeviceWarnState() {
        if (this.t4Record.getK3Id() != 0) {
            if (this.t4Record.getState().isLiquidLack()) {
                liquidStateEmpty();
            } else {
                liquidStateFull();
            }
        }
        int i = !CommonUtils.isSameTimeZoneAsLocal(this.t4Record.getLocale(), this.t4Record.getTimezone()) ? 1 : 0;
        if (this.t4Record.getState().isLowPower() && this.t4Record.getK3Record() != null) {
            i++;
        }
        if (this.t4Record.getIsPetOutTips() == 1) {
            i++;
        }
        if (i == 0) {
            this.tvLittleWarnText.setVisibility(8);
            return;
        }
        if (i == 1) {
            this.tvLittleWarnText.setVisibility(0);
            if (!CommonUtils.isSameTimeZoneAsLocal(this.t4Record.getLocale(), this.t4Record.getTimezone())) {
                this.tvLittleWarnText.setText(R.string.Time_zone_is_different);
                return;
            }
            if (this.t4Record.getState().isLowPower() && this.t4Record.getK3Record() != null) {
                this.tvLittleWarnText.setText(R.string.K3_battery_low_warn);
                return;
            } else {
                if (this.t4Record.getIsPetOutTips() == 1) {
                    this.tvLittleWarnText.setText(R.string.T_Device_Have_Check_Toilet_Warning);
                    return;
                }
                return;
            }
        }
        this.tvLittleWarnText.setVisibility(0);
        this.tvLittleWarnText.setText(String.format(getResources().getString(R.string.Have_mistake_to_handle), String.valueOf(i)));
    }

    private void startLightRotateAnimation() {
        this.t4ViewStateIconLightProgress.setVisibility(0);
        ObjectAnimator objectAnimator = this.lightRotateAnimator;
        if (objectAnimator == null || !objectAnimator.isRunning()) {
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this.t4ViewStateIconLightProgress, "rotation", 360.0f, 0.0f);
            this.lightRotateAnimator = objectAnimatorOfFloat;
            objectAnimatorOfFloat.setDuration(3000L);
            this.lightRotateAnimator.setRepeatCount(-1);
            this.lightRotateAnimator.setRepeatMode(1);
            this.lightRotateAnimator.setInterpolator(new LinearInterpolator());
            this.lightRotateAnimator.start();
        }
    }

    private void stopLightRotateAnimation() {
        this.t4ViewStateIconLightProgress.setVisibility(8);
        ObjectAnimator objectAnimator = this.lightRotateAnimator;
        if (objectAnimator != null) {
            objectAnimator.cancel();
            this.lightRotateAnimator = null;
        }
    }

    private void startRotateAnimation() {
        this.ivCircleProgress.setVisibility(0);
        ObjectAnimator objectAnimator = this.rotateAnimator;
        if (objectAnimator == null || !objectAnimator.isRunning()) {
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this.ivCircleProgress, "rotation", 0.0f, 360.0f);
            this.rotateAnimator = objectAnimatorOfFloat;
            objectAnimatorOfFloat.setDuration(1000L);
            this.rotateAnimator.setRepeatCount(-1);
            this.rotateAnimator.setRepeatMode(1);
            this.rotateAnimator.setInterpolator(new LinearInterpolator());
            this.rotateAnimator.start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopRotateAnimation() {
        this.ivCircleProgress.setVisibility(8);
        ObjectAnimator objectAnimator = this.rotateAnimator;
        if (objectAnimator != null) {
            objectAnimator.cancel();
            this.rotateAnimator = null;
        }
    }

    public void startScanAnimation() {
        AnimatorSet animatorSet = this.scanAnimatorSet;
        if (animatorSet == null || !animatorSet.isRunning()) {
            this.t4ViewDeviceCenterScanBlueBg.setVisibility(0);
            ImageView imageView = this.t4ViewDeviceCenterScanBlueBg;
            imageView.setPivotX(BaseApplication.displayMetrics.widthPixels * this.widthScale * 0.38095f);
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(imageView, Key.ALPHA, 0.2f, 0.8f, 0.8f, 0.8f, 0.8f, 0.8f, 0.8f, 0.8f, 0.8f, 0.8f, 0.8f, 0.8f, 0.8f, 0.8f, 0.8f, 0.2f);
            objectAnimatorOfFloat.setRepeatMode(1);
            objectAnimatorOfFloat.setRepeatCount(-1);
            int i = BaseApplication.displayMetrics.widthPixels;
            float f = this.widthScale;
            ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(imageView, Key.TRANSLATION_Y, (((-i) * f) / 315.0f) * 20.0f, ((i * f) / 315.0f) * 90.0f);
            objectAnimatorOfFloat2.setRepeatMode(1);
            objectAnimatorOfFloat2.setRepeatCount(-1);
            ObjectAnimator objectAnimatorOfFloat3 = ObjectAnimator.ofFloat(imageView, Key.SCALE_X, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f);
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
        this.t4ViewDeviceCenterScanBlueBg.setVisibility(8);
        AnimatorSet animatorSet = this.scanAnimatorSet;
        if (animatorSet != null) {
            animatorSet.cancel();
            this.scanAnimatorSet = null;
        }
    }

    private void stopDeodorantAnimation() {
        this.rlT4DeviceCenter.setAlpha(1.0f);
        this.t4ViewDeviceCenterK3.setVisibility(8);
        this.t4ViewDeviceCenterK3Running.setVisibility(8);
        if (this.deviceAlphaAnimator != null) {
            OnLoadingDismiss onLoadingDismiss = this.onLoadingDismiss;
            if (onLoadingDismiss != null) {
                onLoadingDismiss.dismiss(MenuType.DEODORANT);
            }
            this.deviceAlphaAnimator.cancel();
            this.deviceAlphaAnimator = null;
        }
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

    @Override // com.petkit.android.activities.petkitBleDevice.widget.T4PetkitSlidingUpPanelLayout.PanelSlideListener
    public void onPanelSlide(View view, float f) {
        this.mSlideOffset = f;
        if (f >= 0.0f) {
            this.rlTopView.setAlpha(1.0f - f);
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

    @Override // com.petkit.android.activities.petkitBleDevice.widget.BleDeviceHomeOfflineWarnWindow.OfflineClickListener
    public void onClickSeeDetails() {
        Activity activity = this.activity;
        activity.startActivity(BleDeviceWifiSettingActivity.newIntent(activity, this.t4Record.getDeviceId(), 15, this.t4Record.getTypeCode()));
    }

    @Override // com.petkit.android.activities.petkitBleDevice.widget.BleDeviceHomeOfflineWarnWindow.OfflineClickListener
    public void onClickReset() {
        Activity activity = this.activity;
        activity.startActivity(BleDeviceBindNetWorkActivity.newIntent((Context) activity, this.t4Record.getDeviceId(), 15, this.t4Record.getBtMac(), false, this.t4Record.getTypeCode()));
    }

    @Override // com.petkit.android.activities.petkitBleDevice.k3.widget.K3BatteryWindow.TipClickListener
    public void onConfirmClick() {
        K3BatteryWindow k3BatteryWindow = this.k3BatteryWindow;
        if (k3BatteryWindow != null) {
            k3BatteryWindow.dismiss();
        }
    }

    public RecordOnClickListener getRecordOnClickListener() {
        return this.recordOnClickListener;
    }

    public void setRecordOnClickListener(RecordOnClickListener recordOnClickListener) {
        this.recordOnClickListener = recordOnClickListener;
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t4.adapter.T4HomeHistoryRecordNewAdapter.OnClickListener
    public void onViewClick(T4DeviceRecord t4DeviceRecord, int i, String str) {
        RecordOnClickListener recordOnClickListener = this.recordOnClickListener;
        if (recordOnClickListener != null) {
            recordOnClickListener.onClickRecord(t4DeviceRecord, str);
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t4.adapter.T4HomeHistoryRecordNewAdapter.OnClickListener
    public void onDelClick(T4DeviceRecord t4DeviceRecord, int i, String str) {
        RecordOnClickListener recordOnClickListener = this.recordOnClickListener;
        if (recordOnClickListener != null) {
            recordOnClickListener.onDelRecord(t4DeviceRecord, str);
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t4.adapter.T4HomeHistoryRecordNewAdapter.OnClickListener
    public void onUnknownPetClick(String str, String str2, String str3, double d, int i) {
        RecordOnClickListener recordOnClickListener = this.recordOnClickListener;
        if (recordOnClickListener != null) {
            recordOnClickListener.onUnknownPetClick(str, str2, str3, d);
        }
    }

    @Override // com.petkit.android.activities.universalWindow.BaseBottomWindow.OnClickListener
    public void onBtnLeft() {
        this.petFilterWindow.resetSelectedState();
        this.petFilterWindow.dismiss();
        this.selectedPetIds = this.petFilterWindow.getSelectedPetIds();
        PetkitLog.d("selectedPetIds onBtnRight:" + this.selectedPetIds.toString());
        refreshT4Histogram();
        refreshStatistic();
        setDeviceRecord(this.t4DeviceRecordList, this.recordDate);
    }

    @Override // com.petkit.android.activities.universalWindow.BaseBottomWindow.OnClickListener
    public void onBtnRight() {
        this.petFilterWindow.updateSelectedState();
        this.petFilterWindow.dismiss();
        this.selectedPetIds = this.petFilterWindow.getSelectedPetIds();
        PetkitLog.d("selectedPetIds onBtnRight:" + this.selectedPetIds.toString());
        refreshT4Histogram();
        refreshStatistic();
        setDeviceRecord(this.t4DeviceRecordList, this.recordDate);
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

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        MainHandler mainHandler = this.mainHandler;
        if (mainHandler != null) {
            mainHandler.removeCallbacksAndMessages(null);
        }
        cancelAllAnimationAndTimer();
        super.onDetachedFromWindow();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String totalTimeToStr(int i) {
        String string;
        Resources resources;
        int i2;
        String string2;
        Resources resources2;
        int i3;
        int i4 = (int) (i / 60.0f);
        int i5 = i % 60;
        if ("zh_TW".equalsIgnoreCase(UserInforUtils.getLanguageLocaleString(CommonUtils.getAppContext()))) {
            string = this.mContext.getResources().getString(i4 > 1 ? R.string.Unit_minutes : R.string.Unit_minute);
            if (i5 > 1) {
                resources2 = this.mContext.getResources();
                i3 = R.string.Unit_seconds;
            } else {
                resources2 = this.mContext.getResources();
                i3 = R.string.Unit_second;
            }
            string2 = resources2.getString(i3);
        } else {
            string = this.mContext.getResources().getString(i4 > 1 ? R.string.Unit_minutes_short : R.string.Unit_minute_short);
            if (i5 > 1) {
                resources = this.mContext.getResources();
                i2 = R.string.Unit_seconds_short;
            } else {
                resources = this.mContext.getResources();
                i2 = R.string.Unit_second_short;
            }
            string2 = resources.getString(i2);
        }
        String str = "";
        String str2 = ("zh_CN".equalsIgnoreCase(UserInforUtils.getLanguageLocaleString(CommonUtils.getAppContext())) || "zh_TW".equalsIgnoreCase(UserInforUtils.getLanguageLocaleString(CommonUtils.getAppContext()))) ? "" : " ";
        StringBuilder sb = new StringBuilder();
        if (i4 > 0) {
            str = i4 + str2 + string;
        }
        sb.append(str);
        sb.append(str2);
        sb.append(i5);
        sb.append(str2);
        sb.append(string2);
        return sb.toString();
    }

    private String formatTime(String str) {
        try {
            return new SimpleDateFormat(DateUtil.DATE_FORMAT_8).format(new SimpleDateFormat(DateUtil.DATE_FORMAT_7).parse(str));
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    private long formatDateToTimeStamp(String str) {
        try {
            return new SimpleDateFormat(DateUtil.DATE_FORMAT_7).parse(str).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0L;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showTipWindow() {
        Activity activity = this.activity;
        activity.startActivity(WebviewActivity.newIntent(activity, getResources().getString(R.string.No_cat), ApiTools.getWebUrlByKey("t4_pet_reset")));
        this.rlPop.setVisibility(4);
    }

    public void setToiletCompareResult(ToiletCompareResult toiletCompareResult) {
        int color;
        String strValueOf;
        int i;
        int color2;
        String strSecondsToMinuteAndSecondsShortText;
        this.toiletCompareResult = toiletCompareResult;
        if (toiletCompareResult != null) {
            this.tvTodayTime.setText(UnitUtils.getInstance().getNumAndUnitString(toiletCompareResult.getCount(), this.mContext.getResources().getString(R.string.Unit_time), this.mContext.getResources().getString(R.string.Unit_times)));
            this.tvTodayAverage.setText(TimeUtils.getInstance().secondsToMinuteAndSecondsShortText(this.mContext, toiletCompareResult.getTime()));
            int i2 = 0;
            if (toiletCompareResult.getCompareCount() > 0) {
                i = R.drawable.small_green_up_arrow;
                color = this.mContext.getResources().getColor(R.color.compare_green);
                strValueOf = "+" + toiletCompareResult.getCompareCount();
            } else if (toiletCompareResult.getCompareCount() < 0) {
                i = R.drawable.small_down_orange_arrow;
                color = this.mContext.getResources().getColor(R.color.compare_orange);
                strValueOf = String.valueOf(toiletCompareResult.getCompareCount());
            } else {
                color = this.mContext.getResources().getColor(R.color.toilet_chart_color_one);
                strValueOf = "+" + toiletCompareResult.getCompareCount();
                i = 0;
            }
            SpannableString spannableString = new SpannableString(this.mContext.getResources().getString(R.string.Compared_to_the_same_period_yesterday) + strValueOf);
            spannableString.setSpan(new ForegroundColorSpan(color), spannableString.toString().lastIndexOf(strValueOf), spannableString.toString().lastIndexOf(strValueOf) + strValueOf.length(), 33);
            spannableString.setSpan(new RelativeSizeSpan(1.2f), spannableString.toString().lastIndexOf(strValueOf), spannableString.toString().lastIndexOf(strValueOf) + strValueOf.length(), 33);
            this.tvTodayTimeComparedYesterday.setText(spannableString);
            if (toiletCompareResult.getCompareCount() != 0) {
                this.tvTodayTimeComparedYesterday.setCompoundDrawablesRelativeWithIntrinsicBounds((Drawable) null, (Drawable) null, getResources().getDrawable(i), (Drawable) null);
            } else {
                this.tvTodayTimeComparedYesterday.setCompoundDrawablesRelativeWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
            }
            if (toiletCompareResult.getCompareTime() > 0) {
                i2 = R.drawable.small_green_up_arrow;
                color2 = this.mContext.getResources().getColor(R.color.compare_green);
                strSecondsToMinuteAndSecondsShortText = "+" + TimeUtils.getInstance().secondsToMinuteAndSecondsShortText(this.mContext, toiletCompareResult.getCompareTime());
            } else if (toiletCompareResult.getCompareTime() < 0) {
                i2 = R.drawable.small_down_orange_arrow;
                color2 = this.mContext.getResources().getColor(R.color.compare_orange);
                strSecondsToMinuteAndSecondsShortText = TimeUtils.getInstance().secondsToMinuteAndSecondsShortText(this.mContext, toiletCompareResult.getCompareTime());
            } else {
                color2 = this.mContext.getResources().getColor(R.color.toilet_chart_color_one);
                strSecondsToMinuteAndSecondsShortText = "+" + TimeUtils.getInstance().secondsToMinuteAndSecondsShortText(this.mContext, toiletCompareResult.getCompareTime());
            }
            SpannableString spannableString2 = new SpannableString(this.mContext.getResources().getString(R.string.Compared_to_the_same_period_yesterday) + strSecondsToMinuteAndSecondsShortText);
            spannableString2.setSpan(new ForegroundColorSpan(color2), spannableString2.toString().lastIndexOf(strSecondsToMinuteAndSecondsShortText), spannableString2.toString().lastIndexOf(strSecondsToMinuteAndSecondsShortText) + strSecondsToMinuteAndSecondsShortText.length(), 33);
            spannableString2.setSpan(new RelativeSizeSpan(1.2f), spannableString2.toString().lastIndexOf(strSecondsToMinuteAndSecondsShortText), spannableString2.toString().lastIndexOf(strSecondsToMinuteAndSecondsShortText) + strSecondsToMinuteAndSecondsShortText.length(), 33);
            this.tvTodayAverageComparedYesterday.setText(spannableString2);
            if (toiletCompareResult.getCompareTime() != 0) {
                this.tvTodayAverageComparedYesterday.setCompoundDrawablesRelativeWithIntrinsicBounds((Drawable) null, (Drawable) null, getResources().getDrawable(i2), (Drawable) null);
            } else {
                this.tvTodayAverageComparedYesterday.setCompoundDrawablesRelativeWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
            }
        }
    }

    public void showRecordGuideView(View view) {
        if (this.guide3 != null) {
            return;
        }
        GuideBuilder guideBuilder = new GuideBuilder();
        guideBuilder.setTargetView(view).setAlpha(180).setHighTargetCorner(50).setHighTargetPaddingLeft(ArmsUtils.dip2px(this.mContext, 0.0f)).setHighTargetPaddingRight(ArmsUtils.dip2px(this.mContext, 0.0f)).setHighTargetPaddingBottom(ArmsUtils.dip2px(this.mContext, 0.0f)).setHighTargetPaddingTop(ArmsUtils.dip2px(this.mContext, 0.0f)).setOutsideTouchable(false).setAutoDismiss(true);
        guideBuilder.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() { // from class: com.petkit.android.activities.petkitBleDevice.t4.widget.T4HomeDeviceView.27
            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onShown() {
            }

            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onDismiss() {
                DataHelper.setBooleanSF(T4HomeDeviceView.this.mContext, Consts.T4_CHART_IS_FIRST, Boolean.TRUE);
                T4HomeDeviceView.this.t4ViewLayout.fling(1);
                T4HomeDeviceView.this.t4ViewLayout.setOnTouchListener(new View.OnTouchListener() { // from class: com.petkit.android.activities.petkitBleDevice.t4.widget.T4HomeDeviceView.27.1
                    @Override // android.view.View.OnTouchListener
                    public boolean onTouch(View view2, MotionEvent motionEvent) {
                        return false;
                    }
                });
            }
        });
        guideBuilder.addComponent(new GuidePetTipAndBtnAndLineComponent(new GuideParam(getResources().getString(R.string.Home_chart_guide_tips), "", 4, 32, 20, 10, getResources().getString(R.string.Done), R.layout.layout_feeder_guide_one), new ConfirmListener() { // from class: com.petkit.android.activities.petkitBleDevice.t4.widget.T4HomeDeviceView.28
            @Override // com.petkit.android.activities.petkitBleDevice.w5.guide.ConfirmListener
            public void onConfirmListener() {
                if (T4HomeDeviceView.this.guide3 != null) {
                    T4HomeDeviceView.this.guide3.dismiss();
                }
            }
        }));
        this.guide3 = guideBuilder.createGuide();
        this.contentView.post(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.t4.widget.T4HomeDeviceView.29
            @Override // java.lang.Runnable
            public void run() {
                T4HomeDeviceView.this.guide3.show((Activity) T4HomeDeviceView.this.getContext());
            }
        });
    }

    public void showGuideView(View view) {
        if (DataHelper.getBooleanSF(this.mContext, Consts.T4_HOME_DATA_GUIDE)) {
            if (this.t4Record.getState().getOverall() != 2) {
                long currentFamilyId = FamilyUtils.getInstance().getCurrentFamilyId(this.mContext);
                if (currentFamilyId < 1) {
                    return;
                }
                String str = "HOME_TOILET_WINDOW" + currentFamilyId + this.dateFormat.format(new Date());
                String stringSF = DataHelper.getStringSF(this.mContext, str);
                if (this.t4Record.getIsPetOutTips() == 1 && TextUtils.isEmpty(stringSF)) {
                    this.menuOnClickListener.onBottomMenuClick(MenuType.UNUSED_DIALOG);
                    DataHelper.setStringSF(this.mContext, str, str);
                    return;
                }
                return;
            }
            return;
        }
        if (this.guide1 != null) {
            return;
        }
        GuideBuilder guideBuilder = new GuideBuilder();
        guideBuilder.setTargetView(view).setAlpha(180).setHighTargetCorner(50).setHighTargetPaddingLeft(ArmsUtils.dip2px(this.mContext, 8.0f)).setHighTargetPaddingRight(ArmsUtils.dip2px(this.mContext, 8.0f)).setHighTargetPaddingBottom(ArmsUtils.dip2px(this.mContext, 8.0f)).setHighTargetPaddingTop(ArmsUtils.dip2px(this.mContext, 8.0f)).setOutsideTouchable(false).setAutoDismiss(true);
        guideBuilder.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() { // from class: com.petkit.android.activities.petkitBleDevice.t4.widget.T4HomeDeviceView.30
            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onShown() {
            }

            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onDismiss() {
                if (T4HomeDeviceView.this.gridMenuView.getVisibility() != 8) {
                    T4HomeDeviceView t4HomeDeviceView = T4HomeDeviceView.this;
                    t4HomeDeviceView.showGuideView2(t4HomeDeviceView.gridMenuView);
                }
            }
        });
        guideBuilder.addComponent(new GuidePetTipAndBtnAndLineComponent(new GuideParam(getResources().getString(R.string.Home_device_guide_tips), "1/2", 4, 32, 0, 10, getResources().getString(R.string.Next_tip), R.layout.layout_feeder_guide_one), new ConfirmListener() { // from class: com.petkit.android.activities.petkitBleDevice.t4.widget.T4HomeDeviceView.31
            @Override // com.petkit.android.activities.petkitBleDevice.w5.guide.ConfirmListener
            public void onConfirmListener() {
                if (T4HomeDeviceView.this.guide1 != null) {
                    T4HomeDeviceView.this.guide1.dismiss();
                }
            }
        }));
        this.guide1 = guideBuilder.createGuide();
        this.contentView.post(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.t4.widget.T4HomeDeviceView.32
            @Override // java.lang.Runnable
            public void run() {
                T4HomeDeviceView.this.guide1.show((Activity) T4HomeDeviceView.this.getContext());
            }
        });
    }

    public void showGuideView2(View view) {
        if (this.guide2 != null) {
            return;
        }
        GuideBuilder guideBuilder = new GuideBuilder();
        guideBuilder.setTargetView(view).setAlpha(180).setHighTargetCorner(50).setHighTargetPaddingLeft(ArmsUtils.dip2px(this.mContext, 0.0f)).setHighTargetPaddingRight(ArmsUtils.dip2px(this.mContext, 0.0f)).setHighTargetPaddingBottom(ArmsUtils.dip2px(this.mContext, 0.0f)).setHighTargetPaddingTop(ArmsUtils.dip2px(this.mContext, 0.0f)).setOutsideTouchable(false).setAutoDismiss(true);
        guideBuilder.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() { // from class: com.petkit.android.activities.petkitBleDevice.t4.widget.T4HomeDeviceView.33
            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onShown() {
            }

            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onDismiss() {
                DataHelper.setBooleanSF(T4HomeDeviceView.this.mContext, Consts.T4_HOME_DATA_GUIDE, Boolean.TRUE);
            }
        });
        guideBuilder.addComponent(new GuideTipAndBtnAndLineComponent(new GuideParam(getResources().getString(R.string.Home_menu_guide_tips), "2/2", 2, 48, 0, -10, getResources().getString(R.string.Done), R.layout.layout_home_guide_mine), new ConfirmListener() { // from class: com.petkit.android.activities.petkitBleDevice.t4.widget.T4HomeDeviceView.34
            @Override // com.petkit.android.activities.petkitBleDevice.w5.guide.ConfirmListener
            public void onConfirmListener() {
                if (T4HomeDeviceView.this.guide2 != null) {
                    T4HomeDeviceView.this.guide2.dismiss();
                }
            }
        }));
        this.guide2 = guideBuilder.createGuide();
        this.contentView.post(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.t4.widget.T4HomeDeviceView.35
            @Override // java.lang.Runnable
            public void run() {
                T4HomeDeviceView.this.guide2.show((Activity) T4HomeDeviceView.this.getContext());
            }
        });
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
        this.relatedProductsInfor = relatedProductsInfor;
        ArrayList arrayList = new ArrayList();
        if (relatedProductsInfor != null && relatedProductsInfor.getPromote() != null) {
            arrayList.addAll(relatedProductsInfor.getPromote());
        }
        if (arrayList.size() == 0) {
            this.promoteView.setVisibility(8);
        } else {
            this.promoteView.refreshPromoteData(arrayList);
            this.promoteView.setPromoteViewOnItemListener(new PromoteView.PromoteViewOnItemListener() { // from class: com.petkit.android.activities.petkitBleDevice.t4.widget.T4HomeDeviceView.36
                @Override // com.petkit.android.activities.mall.widget.PromoteView.PromoteViewOnItemListener
                public void onItemClick(PromoteData promoteData) {
                    EventBus.getDefault().post(promoteData);
                }
            });
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.widget.BleDeviceHomeMenuView.OnMenuClickListener
    public void onMenuClick(BleMenuItem bleMenuItem, int i) {
        MenuOnClickListener menuOnClickListener = this.menuOnClickListener;
        if (menuOnClickListener != null) {
            if (i == 0) {
                menuOnClickListener.onBottomMenuClick(MenuType.CLEAN_UP);
                return;
            }
            if (i == 1) {
                menuOnClickListener.onBottomMenuClick(MenuType.MAINTENANCE_MODE);
                return;
            }
            if (i == 2) {
                if (this.t4Record.getSettings().getDumpSwitch() == 0) {
                    this.menuOnClickListener.onBottomMenuClick(MenuType.SMART_SETTING);
                    return;
                } else {
                    new HashMap().put("type", "t4");
                    this.menuOnClickListener.onBottomMenuClick(MenuType.EMPTY_CAT_LITTER);
                    return;
                }
            }
            if (i == 3) {
                if (this.t4Record.getSettings().getDumpSwitch() == 0) {
                    if (this.t4Record.getK3Id() == 0) {
                        this.menuOnClickListener.onBottomMenuClick(MenuType.NO_ASSOCIATED_K3);
                        return;
                    } else {
                        this.menuOnClickListener.onBottomMenuClick(MenuType.DEODORANT);
                        return;
                    }
                }
                this.menuOnClickListener.onBottomMenuClick(MenuType.SMART_SETTING);
                return;
            }
            if (i != 4) {
                if (i != 5) {
                    return;
                }
                if (this.t4Record.getK3Id() == 0) {
                    this.menuOnClickListener.onBottomMenuClick(MenuType.NO_ASSOCIATED_K3);
                    return;
                } else {
                    this.menuOnClickListener.onBottomMenuClick(MenuType.LIGHT);
                    return;
                }
            }
            if (this.t4Record.getSettings().getDumpSwitch() == 0) {
                if (this.t4Record.getK3Id() == 0) {
                    this.menuOnClickListener.onBottomMenuClick(MenuType.NO_ASSOCIATED_K3);
                    return;
                } else {
                    this.menuOnClickListener.onBottomMenuClick(MenuType.LIGHT);
                    return;
                }
            }
            if (this.t4Record.getK3Id() == 0) {
                this.menuOnClickListener.onBottomMenuClick(MenuType.NO_ASSOCIATED_K3);
            } else {
                this.menuOnClickListener.onBottomMenuClick(MenuType.DEODORANT);
            }
        }
    }

    public void showMaintenanceGuide() {
        if (this.t4Record == null || this.gridMenuView.getVisibility() == 8) {
            return;
        }
        showGuideView(this.rlDevice);
    }

    private void setupMaintenanceModeView() {
        stopScanAnimation();
        this.t4ViewDeviceCenterBox.setVisibility(8);
        this.t4ViewDeviceCenterK3.setVisibility(8);
        this.t4ViewDeviceCenterK3Running.setVisibility(8);
        this.llWarnPanel.setVisibility(8);
        this.gridMenuView.setVisibility(8);
        if (this.t4ViewLayout.getScrollY() != 0) {
            this.t4ViewLayout.smoothScrollTo(0, 0);
        }
        this.rlMiddlePanel.setVisibility(8);
        this.llMaintenanceMode.setVisibility(0);
        setConsumablesInletGone();
        if (1 == this.t4Record.getState().getWorkState().getWorkProcess() / 10) {
            if (this.lastMaintenanceType == 1) {
                DataHelper.setBooleanSF(this.activity, Constants.T4_START_MAINTENANCE_MODE_NORMAL_FLAG, Boolean.FALSE);
                return;
            }
            boolean booleanSF = DataHelper.getBooleanSF(this.activity, Constants.T4_START_MAINTENANCE_MODE_NORMAL_FLAG);
            this.t4Record = T4Utils.getT4RecordByDeviceId(this.t4Record.getDeviceId());
            StringBuilder sb = new StringBuilder();
            sb.append("setupMaintenanceModeView,startMaintenance type:");
            sb.append(booleanSF ? "normal" : "error");
            PetkitLog.d(sb.toString());
            StringBuilder sb2 = new StringBuilder();
            sb2.append("setupMaintenanceModeView,startMaintenance type:");
            sb2.append(booleanSF ? "normal" : "error");
            LogcatStorageHelper.addLog(sb2.toString());
            if (!booleanSF) {
                this.t4Record.setMaintenanceTime((int) (System.currentTimeMillis() / 1000));
                this.t4Record.save();
            }
            DataHelper.setBooleanSF(this.activity, Constants.T4_START_MAINTENANCE_MODE_NORMAL_FLAG, Boolean.FALSE);
            this.rlWorkPanel.setVisibility(0);
            if (this.t4Record.getTypeCode() == 2) {
                this.t4DeviceCenterWhiteBg.setImageResource(R.drawable.t4_2_home_device_icon);
            } else {
                this.t4DeviceCenterWhiteBg.setImageResource(R.drawable.t4_home_device_icon);
            }
            this.t4ViewDeviceCenterMask.setVisibility(0);
            this.btnMaintenanceModeContinueToRun.setVisibility(8);
            this.btnMaintenanceModePauseClean.setVisibility(8);
            this.btnMaintenanceModeTerminateClean.setVisibility(0);
            this.btnMaintenanceModeTerminateClean.setTextColor(this.mContext.getResources().getColor(R.color.t4_text_gray));
            this.btnMaintenanceModeTerminateClean.setBackgroundResource(R.drawable.solid_t4_home_gray);
            startRotateAnimation();
            this.tvWorkState.setText(this.mContext.getResources().getString(R.string.Starting));
            stopMaintenanceLoading();
            this.llMaintenanceModeStatus.setGravity(17);
            this.tvMaintenanceStatus.setPadding(0, 0, 0, 0);
            this.tvMaintenanceStatus.setText(this.activity.getString(R.string.Sand_warehouse_in_operation));
            this.tvMaintenanceDesc.setVisibility(8);
            timerForStartMaintenance();
            this.lastMaintenanceType = 1;
            return;
        }
        if (2 == this.t4Record.getState().getWorkState().getWorkProcess() / 10 && 2 == this.t4Record.getState().getWorkState().getWorkProcess() % 10) {
            this.rlWorkPanel.setVisibility(0);
            this.mainHandler.removeCallbacks(this.enterMaintenance);
            this.mainHandler.removeCallbacks(this.maintenanceRunTimer);
            if (this.t4Record.getTypeCode() == 2) {
                this.t4DeviceCenterWhiteBg.setImageResource(R.drawable.t4_2_home_device_icon);
            } else {
                this.t4DeviceCenterWhiteBg.setImageResource(R.drawable.t4_home_device_icon);
            }
            this.t4ViewDeviceCenterMask.setVisibility(0);
            stopRotateAnimation();
            this.tvWorkState.setText(this.mContext.getResources().getString(R.string.Paused));
            this.btnMaintenanceModeContinueToRun.setVisibility(4);
            this.btnMaintenanceModePauseClean.setVisibility(8);
            this.btnMaintenanceModeTerminateClean.setVisibility(8);
            stopMaintenanceLoading();
            this.llMaintenanceModeStatus.setGravity(17);
            this.tvMaintenanceStatus.setPadding(0, 0, 0, 0);
            this.tvMaintenanceDesc.setVisibility(8);
            int safeWarn = this.t4Record.getState().getWorkState().getSafeWarn();
            if (safeWarn != 0) {
                if (safeWarn == 1) {
                    this.tvMaintenanceStatus.setText(this.activity.getString(R.string.Safe_warn_atp));
                } else if (safeWarn == 3) {
                    this.tvMaintenanceStatus.setText(this.activity.getString(R.string.T4_hallt_error));
                } else {
                    this.tvMaintenanceStatus.setText(this.activity.getString(R.string.Safe_warn_general));
                }
            } else if (this.t4Record.getState().getPetInTime() == 0) {
                this.tvMaintenanceStatus.setText(this.activity.getString(R.string.T4_device_safety_testing));
            } else {
                this.tvMaintenanceStatus.setText(this.activity.getString(R.string.T4_pet_go_into_device));
            }
            this.lastMaintenanceType = 2;
            return;
        }
        if (3 == this.t4Record.getState().getWorkState().getWorkProcess() / 10) {
            this.rlWorkPanel.setVisibility(0);
            this.mainHandler.removeCallbacks(this.enterMaintenance);
            this.mainHandler.removeCallbacks(this.maintenanceRunTimer);
            if (this.t4Record.getTypeCode() == 2) {
                this.t4DeviceCenterWhiteBg.setImageResource(R.drawable.t4_2_home_device_icon);
            } else {
                this.t4DeviceCenterWhiteBg.setImageResource(R.drawable.t4_home_device_icon);
            }
            this.t4ViewDeviceCenterMask.setVisibility(0);
            this.btnMaintenanceModeTerminateClean.setVisibility(8);
            this.btnMaintenanceModePauseClean.setVisibility(0);
            this.btnMaintenanceModeContinueToRun.setVisibility(8);
            this.tvWorkState.setText(this.mContext.getResources().getString(R.string.Resetting));
            startRotateAnimation();
            stopMaintenanceLoading();
            this.llMaintenanceModeStatus.setGravity(17);
            this.tvMaintenanceStatus.setPadding(0, 0, 0, 0);
            this.tvMaintenanceStatus.setText(this.activity.getString(R.string.Sand_warehouse_is_being_reset));
            this.tvMaintenanceDesc.setVisibility(8);
            this.lastMaintenanceType = 3;
            return;
        }
        if (4 == this.t4Record.getState().getWorkState().getWorkProcess() / 10) {
            this.rlWorkPanel.setVisibility(0);
            if (this.t4Record.getTypeCode() == 2) {
                this.t4DeviceCenterWhiteBg.setImageResource(R.drawable.t4_2_home_device_icon);
            } else {
                this.t4DeviceCenterWhiteBg.setImageResource(R.drawable.t4_home_device_icon);
            }
            this.t4ViewDeviceCenterMask.setVisibility(0);
            this.tvWorkState.setText(this.mContext.getResources().getString(R.string.Paused));
            stopRotateAnimation();
            if (2 == this.t4Record.getState().getWorkState().getWorkProcess() % 10) {
                this.btnMaintenanceModeContinueToRun.setVisibility(4);
                this.btnMaintenanceModePauseClean.setVisibility(8);
                this.btnMaintenanceModeTerminateClean.setVisibility(8);
                stopMaintenanceLoading();
                this.tvMaintenanceStatus.setPadding(ArmsUtils.dip2px(this.activity, 40.0f), 0, ArmsUtils.dip2px(this.activity, 40.0f), 0);
                this.llMaintenanceModeStatus.setGravity(17);
                this.tvMaintenanceDesc.setVisibility(8);
                int safeWarn2 = this.t4Record.getState().getWorkState().getSafeWarn();
                if (safeWarn2 != 0) {
                    if (safeWarn2 == 1) {
                        this.tvMaintenanceStatus.setText(this.activity.getString(R.string.Safe_warn_atp));
                    } else if (safeWarn2 == 3) {
                        this.tvMaintenanceStatus.setText(this.activity.getString(R.string.T4_hallt_error));
                    } else {
                        this.tvMaintenanceStatus.setText(this.activity.getString(R.string.Safe_warn_general));
                    }
                } else if (this.t4Record.getState().getPetInTime() == 0) {
                    this.tvMaintenanceStatus.setText(this.activity.getString(R.string.T4_device_safety_testing));
                } else {
                    this.tvMaintenanceStatus.setText(this.activity.getString(R.string.T4_pet_go_into_device));
                }
            } else {
                this.btnMaintenanceModeTerminateClean.setVisibility(8);
                this.btnMaintenanceModeContinueToRun.setVisibility(0);
                this.btnMaintenanceModePauseClean.setVisibility(8);
                stopMaintenanceLoading();
                this.llMaintenanceModeStatus.setGravity(17);
                this.tvMaintenanceStatus.setPadding(ArmsUtils.dip2px(this.activity, 40.0f), 0, ArmsUtils.dip2px(this.activity, 40.0f), 0);
                this.tvMaintenanceDesc.setVisibility(8);
                this.tvMaintenanceStatus.setText(this.mContext.getResources().getString(R.string.Device_pause_prompt, String.valueOf((this.t4Record.getState().getWorkState().getStopTime() > 0 ? this.t4Record.getState().getWorkState().getStopTime() : this.t4Record.getSettings().getStopTime()) / 60)));
            }
            this.lastMaintenanceType = 4;
        }
    }

    private void timerForStartMaintenance() {
        int maintenanceTime = this.t4Record.getMaintenanceTime();
        long jCurrentTimeMillis = System.currentTimeMillis() - (((long) maintenanceTime) * 1000);
        PetkitLog.d("timerForStartMaintenance,maintenanceTime:" + maintenanceTime + ",xTime:" + jCurrentTimeMillis);
        LogcatStorageHelper.addLog("timerForStartMaintenance,maintenanceTime:" + maintenanceTime + ",xTime:" + jCurrentTimeMillis);
        if (jCurrentTimeMillis >= 15000) {
            this.maintenanceRunTimes = jCurrentTimeMillis / 1000;
            this.mainHandler.removeCallbacks(this.enterMaintenance);
            this.mainHandler.postDelayed(this.enterMaintenance, 0L);
        } else {
            this.mainHandler.removeCallbacks(this.enterMaintenance);
            this.mainHandler.postDelayed(this.enterMaintenance, 15000 - jCurrentTimeMillis);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearMaintenanceTime(String str) {
        if (str.equals("start")) {
            this.startMaintenanceTimeStamp = 0L;
            this.leftStartCycle = 0L;
        } else {
            str.equals("end");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startMaintenanceLoading() {
        if (this.maintenanceAnimator == null) {
            Animator animatorLoadAnimator = AnimatorInflater.loadAnimator(this.activity, R.animator.maintenance_mode_loading_infinite);
            this.maintenanceAnimator = animatorLoadAnimator;
            animatorLoadAnimator.setTarget(this.ivMaintenanceLoading);
        }
        if (this.maintenanceAnimator.isRunning()) {
            return;
        }
        this.ivMaintenanceLoading.setVisibility(0);
        this.maintenanceAnimator.start();
    }

    private void stopMaintenanceLoading() {
        Animator animator = this.maintenanceAnimator;
        if (animator == null || !animator.isRunning()) {
            return;
        }
        this.maintenanceAnimator.cancel();
        this.ivMaintenanceLoading.setVisibility(8);
    }

    private void resetPanelHeight() {
        this.rlTopView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.petkit.android.activities.petkitBleDevice.t4.widget.T4HomeDeviceView.39
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                T4HomeDeviceView.this.rlTopView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                T4HomeDeviceView.this.mainHandler.postDelayed(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.t4.widget.T4HomeDeviceView.39.1
                    @Override // java.lang.Runnable
                    public void run() {
                        T4HomeDeviceView.this.panelHeight = T4HomeDeviceView.this.contentView.getHeight() - T4HomeDeviceView.this.rlTopView.getHeight();
                        PetkitLog.d("T4HomeDeviceView", "resetPanelHeight:" + T4HomeDeviceView.this.panelHeight);
                        LogcatStorageHelper.addLog("T4HomeDeviceView,resetPanelHeight:" + T4HomeDeviceView.this.panelHeight);
                        int unused = T4HomeDeviceView.this.panelHeight;
                    }
                }, 200L);
            }
        });
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t4.adapter.T4HomeHistoryRecordNewAdapter.OnClickListener
    public void onUnderweightClick() {
        this.menuOnClickListener.onBottomMenuClick(MenuType.UNDERWEIGHT);
    }

    @Override // com.petkit.android.activities.petkitBleDevice.widget.GridMenuView.OnMenuClickListener
    public void onHeightChange() {
        Activity activity;
        float f;
        LinearLayout linearLayout = this.llBottomMenuParentView;
        if (this.gridMenuView.getLastTranslationY() == 0) {
            activity = this.activity;
            f = 252.0f;
        } else {
            activity = this.activity;
            f = 137.0f;
        }
        linearLayout.setPadding(0, 0, 0, ArmsUtils.dip2px(activity, f));
    }

    public void setCurrentItem(int i) {
        this.t4Histogram.setCurrentItem(i);
    }

    @Subscriber
    public void refreshPetColor(UpdatePetColorMsg updatePetColorMsg) {
        PetkitLog.d("refreshPetColor");
        refreshT4Histogram();
        setDeviceRecord(this.t4DeviceRecordList, this.recordDate);
        this.petFilterWindow.refreshColor();
    }

    private void startExpandAni(final int i, int i2) {
        if (this.isRunning) {
            return;
        }
        ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(i2, i);
        this.expandValueAnimator = valueAnimatorOfInt;
        valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.petkit.android.activities.petkitBleDevice.t4.widget.T4HomeDeviceView.40
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int iIntValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                T4HomeDeviceView t4HomeDeviceView = T4HomeDeviceView.this;
                if (t4HomeDeviceView.lastValue >= iIntValue || t4HomeDeviceView.llAccessoryConsumables == null) {
                    return;
                }
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) T4HomeDeviceView.this.llAccessoryConsumables.getLayoutParams();
                layoutParams.width = iIntValue;
                T4HomeDeviceView.this.llAccessoryConsumables.setLayoutParams(layoutParams);
                T4HomeDeviceView.this.lastValue = iIntValue;
            }
        });
        this.expandValueAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.petkit.android.activities.petkitBleDevice.t4.widget.T4HomeDeviceView.41
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                T4HomeDeviceView t4HomeDeviceView = T4HomeDeviceView.this;
                t4HomeDeviceView.isRunning = false;
                t4HomeDeviceView.accessoryConsumables = 0;
                T4HomeDeviceView t4HomeDeviceView2 = T4HomeDeviceView.this;
                int i3 = i;
                t4HomeDeviceView2.startReduceAni(i3, i3 - t4HomeDeviceView2.tvWidth);
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
        valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.petkit.android.activities.petkitBleDevice.t4.widget.T4HomeDeviceView.42
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int iIntValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                T4HomeDeviceView t4HomeDeviceView = T4HomeDeviceView.this;
                if (t4HomeDeviceView.lastValue <= iIntValue || t4HomeDeviceView.llAccessoryConsumables == null) {
                    return;
                }
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) T4HomeDeviceView.this.llAccessoryConsumables.getLayoutParams();
                layoutParams.width = iIntValue;
                T4HomeDeviceView.this.llAccessoryConsumables.setLayoutParams(layoutParams);
                T4HomeDeviceView.this.lastValue = iIntValue;
            }
        });
        this.reduceValueAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.petkit.android.activities.petkitBleDevice.t4.widget.T4HomeDeviceView.43
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                T4HomeDeviceView.this.accessoryConsumables = 1;
                T4HomeDeviceView.this.isRunning = false;
            }
        });
        this.reduceValueAnimator.setInterpolator(new LinearInterpolator());
        this.reduceValueAnimator.setDuration(1000L);
        this.reduceValueAnimator.setStartDelay(10000L);
        this.reduceValueAnimator.start();
        this.isRunning = true;
    }

    private void startProductAni() {
        this.llAccessoryConsumables.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.petkit.android.activities.petkitBleDevice.t4.widget.T4HomeDeviceView.44
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                T4HomeDeviceView t4HomeDeviceView = T4HomeDeviceView.this;
                t4HomeDeviceView.measuredWidth = t4HomeDeviceView.llAccessoryConsumables.getMeasuredWidth();
                T4HomeDeviceView t4HomeDeviceView2 = T4HomeDeviceView.this;
                t4HomeDeviceView2.lastValue = t4HomeDeviceView2.measuredWidth;
                T4HomeDeviceView t4HomeDeviceView3 = T4HomeDeviceView.this;
                t4HomeDeviceView3.tvWidth = t4HomeDeviceView3.tvAccessoryConsumables.getMeasuredWidth();
                T4HomeDeviceView t4HomeDeviceView4 = T4HomeDeviceView.this;
                t4HomeDeviceView4.startReduceAni(t4HomeDeviceView4.measuredWidth, T4HomeDeviceView.this.measuredWidth - T4HomeDeviceView.this.tvWidth);
                T4HomeDeviceView.this.llAccessoryConsumables.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }
}

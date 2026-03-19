package com.petkit.android.activities.petkitBleDevice.d4sh.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Vibrator;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.TextAppearanceSpan;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.motion.widget.Key;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.binioter.guideview.Guide;
import com.binioter.guideview.GuideBuilder;
import com.bumptech.glide.Glide;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.gson.Gson;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.Consts;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.utils.DeviceUtils;
import com.jess.arms.utils.ImageUtils;
import com.jess.arms.widget.InterceptViewPager;
import com.jess.arms.widget.PetkitToast;
import com.jess.arms.widget.imageloader.ImageCacheSimple;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.card.mode.PurchaseEligibilityInfo;
import com.petkit.android.activities.cloudservice.widget.CloudServiceView;
import com.petkit.android.activities.common.utils.DeviceCenterUtils;
import com.petkit.android.activities.device.AiActivity;
import com.petkit.android.activities.device.DeviceFeedPlansMainActivity;
import com.petkit.android.activities.device.mode.AiInfo;
import com.petkit.android.activities.device.mode.MaterialEventInfo;
import com.petkit.android.activities.device.mode.MaterialUploadRes;
import com.petkit.android.activities.device.utils.AiDataUtil;
import com.petkit.android.activities.device.widget.ThreeChoicesWindow;
import com.petkit.android.activities.feeder.model.DifferentFeedPlan;
import com.petkit.android.activities.home.utils.BlurUtils;
import com.petkit.android.activities.home.utils.GuideD4shBottomRightTipAndBtnAndLineComponent;
import com.petkit.android.activities.home.utils.GuideToiletBtnAndLineComponent;
import com.petkit.android.activities.mall.mode.PromoteData;
import com.petkit.android.activities.mall.widget.PromoteView;
import com.petkit.android.activities.permission.PermissionDialogActivity;
import com.petkit.android.activities.petkitBleDevice.BleDeviceBindNetWorkActivity;
import com.petkit.android.activities.petkitBleDevice.BleDeviceWifiSettingActivity;
import com.petkit.android.activities.petkitBleDevice.PetWeightActivity;
import com.petkit.android.activities.petkitBleDevice.adapter.BasePetkitHighlightAdapter;
import com.petkit.android.activities.petkitBleDevice.d3.widget.BarChartView;
import com.petkit.android.activities.petkitBleDevice.d3.widget.BleFeederTimeView;
import com.petkit.android.activities.petkitBleDevice.d4.utils.D4Utils;
import com.petkit.android.activities.petkitBleDevice.d4s.mode.D4sStatistic;
import com.petkit.android.activities.petkitBleDevice.d4s.mode.D4shDateFeedData;
import com.petkit.android.activities.petkitBleDevice.d4s.utils.D4sUtils;
import com.petkit.android.activities.petkitBleDevice.d4s.widget.D4sHistogram;
import com.petkit.android.activities.petkitBleDevice.d4sh.ControlFoodActivity;
import com.petkit.android.activities.petkitBleDevice.d4sh.D4shCameraSettingActivity;
import com.petkit.android.activities.petkitBleDevice.d4sh.D4shHomeActivity;
import com.petkit.android.activities.petkitBleDevice.d4sh.D4shPersonalizedDressingActivity;
import com.petkit.android.activities.petkitBleDevice.d4sh.D4shSettingActivity;
import com.petkit.android.activities.petkitBleDevice.d4sh.D4shVideoFeedbackActivity;
import com.petkit.android.activities.petkitBleDevice.d4sh.D4shVlogActivity;
import com.petkit.android.activities.petkitBleDevice.d4sh.adapter.D4hHomeRecordAdapter;
import com.petkit.android.activities.petkitBleDevice.d4sh.adapter.D4shAbstractHomeRecordAdapter;
import com.petkit.android.activities.petkitBleDevice.d4sh.adapter.D4shHomeBannerPageAdapter;
import com.petkit.android.activities.petkitBleDevice.d4sh.adapter.D4shHomeRecordAdapter;
import com.petkit.android.activities.petkitBleDevice.d4sh.adapter.D4shLiveViewPagerAdapter;
import com.petkit.android.activities.petkitBleDevice.d4sh.adapter.D4shVlogRecordAdapter;
import com.petkit.android.activities.petkitBleDevice.d4sh.adapter.RecordOnClickListener;
import com.petkit.android.activities.petkitBleDevice.d4sh.listener.D4shHomeDeviceViewListener;
import com.petkit.android.activities.petkitBleDevice.d4sh.mode.AttireListResult;
import com.petkit.android.activities.petkitBleDevice.d4sh.mode.D4shBannerData;
import com.petkit.android.activities.petkitBleDevice.d4sh.mode.D4shChartInfo;
import com.petkit.android.activities.petkitBleDevice.d4sh.mode.D4shDailyFeeds;
import com.petkit.android.activities.petkitBleDevice.d4sh.mode.D4shDayItem;
import com.petkit.android.activities.petkitBleDevice.d4sh.mode.D4shRecord;
import com.petkit.android.activities.petkitBleDevice.d4sh.mode.D4shRemoveData;
import com.petkit.android.activities.petkitBleDevice.d4sh.mode.D4shVideoRecord;
import com.petkit.android.activities.petkitBleDevice.d4sh.mode.D4shVideoRecordFeedbackMsg;
import com.petkit.android.activities.petkitBleDevice.d4sh.utils.D4shUtils;
import com.petkit.android.activities.petkitBleDevice.d4sh.utils.VlogUtils;
import com.petkit.android.activities.petkitBleDevice.download.mode.MediaMsg;
import com.petkit.android.activities.petkitBleDevice.download.utils.FileUtil;
import com.petkit.android.activities.petkitBleDevice.download.utils.VideoDownloadManager;
import com.petkit.android.activities.petkitBleDevice.hg.widget.NewRoundImageview;
import com.petkit.android.activities.petkitBleDevice.listener.AiClickListener;
import com.petkit.android.activities.petkitBleDevice.mode.BleMenuItem;
import com.petkit.android.activities.petkitBleDevice.mode.HighlightRecord;
import com.petkit.android.activities.petkitBleDevice.mode.PetkitVideoSegment;
import com.petkit.android.activities.petkitBleDevice.r2.widget.NewIKnowWindow;
import com.petkit.android.activities.petkitBleDevice.t4.mode.RelatedProductsInfor;
import com.petkit.android.activities.petkitBleDevice.t6.mode.UpdatePetEvent;
import com.petkit.android.activities.petkitBleDevice.t6.utils.T6Utils;
import com.petkit.android.activities.petkitBleDevice.t6.widget.MyChartViewPager;
import com.petkit.android.activities.petkitBleDevice.utils.ColorUtils;
import com.petkit.android.activities.petkitBleDevice.utils.PlayerUtils;
import com.petkit.android.activities.petkitBleDevice.utils.TextUtil;
import com.petkit.android.activities.petkitBleDevice.utils.TimeUtils;
import com.petkit.android.activities.petkitBleDevice.utils.UnitUtils;
import com.petkit.android.activities.petkitBleDevice.w5.guide.ConfirmListener;
import com.petkit.android.activities.petkitBleDevice.w5.guide.GuideParam;
import com.petkit.android.activities.petkitBleDevice.widget.BleDeviceHomeMenuView;
import com.petkit.android.activities.petkitBleDevice.widget.BleDeviceHomeOfflineWarnWindow;
import com.petkit.android.activities.petkitBleDevice.widget.BleDeviceHomeTroubleWarnWindow;
import com.petkit.android.activities.petkitBleDevice.widget.CloudServiceFreeTrialDialog;
import com.petkit.android.activities.petkitBleDevice.widget.D4shHomeRecyclerView;
import com.petkit.android.activities.petkitBleDevice.widget.MultipleImageView;
import com.petkit.android.activities.petkitBleDevice.widget.PetkitDeviceVideoRecordListViewGroup;
import com.petkit.android.activities.petkitBleDevice.widget.PetkitLogoLoadingView;
import com.petkit.android.activities.petkitBleDevice.widget.PetkitScrollViewWithListener;
import com.petkit.android.activities.petkitBleDevice.widget.PetkitSlidingUpPanelLayout;
import com.petkit.android.activities.petkitBleDevice.widget.datepicker.BasePetkitDeviceDatePickerView;
import com.petkit.android.activities.petkitBleDevice.widget.datepicker.DatePickerWindow;
import com.petkit.android.activities.petkitBleDevice.widget.petkit.PetkitPetEatingWindow;
import com.petkit.android.activities.registe.utils.AppUtils;
import com.petkit.android.activities.statistics.EatStatisticsActivity;
import com.petkit.android.activities.statistics.FeedStatisticsActivity;
import com.petkit.android.activities.statistics.widget.VerticalScrollView;
import com.petkit.android.activities.universalWindow.FunctionDialog;
import com.petkit.android.activities.universalWindow.NormalCenterTipWindow;
import com.petkit.android.activities.universalWindow.SpannableStringColorsPicPromptWindow;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.media.video.player.BasePetkitPlayerLandscapeViewClickListener;
import com.petkit.android.media.video.player.BasePetkitPlayerPortraitViewClickListener;
import com.petkit.android.media.video.player.ijkplayer.H3TextureView;
import com.petkit.android.media.video.player.ijkplayer.PetkitFeederDeviceRecordVideoPlayerInstance;
import com.petkit.android.media.video.player.ijkplayer.VideoPlayerView;
import com.petkit.android.media.video.player.listener.BasePetkitPlayerListener;
import com.petkit.android.model.ItemsBean;
import com.petkit.android.model.Pet;
import com.petkit.android.utils.AppVersionStateUtils;
import com.petkit.android.utils.CommonUtil;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.DateUtil;
import com.petkit.android.utils.FileUtils;
import com.petkit.android.utils.LogcatStorageHelper;
import com.petkit.android.utils.PetkitLog;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.android.widget.CirclePageIndicator;
import com.petkit.android.widget.SmallCircleDotView;
import com.petkit.oversea.R;
import com.tencent.connect.common.Constants;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.io.File;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import net.sourceforge.pinyin4j.ChineseToPinyinResource;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

/* JADX INFO: loaded from: classes4.dex */
public class D4shHomeDeviceView extends LinearLayout implements PetkitSlidingUpPanelLayout.PanelSlideListener, View.OnClickListener, BleDeviceHomeMenuView.OnMenuClickListener, RecordOnClickListener, BleDeviceHomeOfflineWarnWindow.OfflineClickListener, BarChartView.ChartOnClickListener, D4sHistogram.ChartOnClickListener, BleDeviceHomeMenuView.OnD4shDeviceHomeMenuTouchListener, BasePetkitPlayerListener, BasePetkitPlayerPortraitViewClickListener, BasePetkitPlayerLandscapeViewClickListener, BleDeviceHomeMenuView.BatteryModeClickListener {
    private static final int HIDE_CAT_FACE = 9;
    private Activity activity;
    private FunctionDialog aiDialog;
    AiInfo aiNewInfo;
    private float alpha;
    private Animation animation;
    private ValueAnimator animator;
    private List<AnimatorSet> animatorLeftSetList;
    private int appBarOffset;
    private BleDeviceHomeMenuView bleMenuView;
    private long calendarTime;
    private RelativeLayout chartView;
    private int clickOuterIndex;
    CloudServiceFreeTrialDialog cloudServiceFreeTrialDialog;
    private View contentView;
    private int current;
    private D4shDailyFeeds d4DailyFeeds;
    private NormalCenterTipWindow d4sFoodWarnWindow;
    private D4shRemoveData d4sRemoveData;
    private PetkitScrollViewWithListener d4sViewLayout;
    private D4shDailyFeeds.D4shDailyEat d4shDailyEat;
    D4shDayItem d4shDayItem;
    private D4shDeviceDataCardViewGroup d4shDeviceDataCardViewGroup;
    private String d4shDeviceRecordDateStr;
    private String d4shDeviceRecordDateText;
    private D4shDeviceRecordTagViewGroup d4shDeviceRecordTagViewGroup;
    private int d4shDeviceRecordTagViewGroupToTopFixedDistance;
    private D4shDeviceRecordTagViewGroup d4shDeviceRecordTagViewGroupTopFixed;
    private D4shDeviceWarnMessageViewGroup d4shDeviceWarnMessageViewGroup;
    private ThreeChoicesWindow d4shFoodWarnWindow;
    private D4shHomeBannerPageAdapter d4shHomeBannerPageAdapter;
    private LinearLayout d4shHomeDeviceDataView;
    private D4shHomeDeviceViewListener d4shHomeDeviceViewListener;
    private D4shAbstractHomeRecordAdapter d4shHomeRecordAdapter;
    private LinearLayout d4shLandscapePlayerContainerView;
    private D4shLiveViewPagerAdapter d4shLiveViewPagerAdapter;
    private int d4shPlayerStatus;
    private D4shRecord d4shRecord;
    D4shVideoRecord d4shVideoRecord;
    private D4shVlogRecordAdapter d4shVlogRecordAdapter;
    D4shVlogRecordAdapterListener d4shVlogRecordAdapterListener;
    private D4shVlogTipWindow d4shVlogTipWindow;
    private List<D4shBannerData.BannerData> dataList;
    private SimpleDateFormat dateFormat;
    private DatePickerWindow datePickerWindow;
    private int deepEnergySavingStatus;
    private int deletePosition;
    private NormalCenterTipWindow deleteRecordWindow;
    DifferentFeedPlan differentFeedPlan;
    private int displayPreviewVideoItemIndex;
    private Disposable disposable;
    Guide dressingGuide;
    private float eatVideoTimesSpeed;
    private PetkitPetEatingWindow eatingWindow;
    Guide firstGuide;
    private FrameLayout flHomeBanner;
    private FrameLayout flTopPanel;
    private boolean flag;
    private boolean fullVideoExpireOrLoss;
    private Guide guide1;
    private Handler handler;
    private ImageView headerLoadingView;
    private ViewPager imageViewPager;
    private ImageView imgD4Plan;
    private ImageView imgD4Setting;
    CirclePageIndicator indicator;
    private boolean invisible;
    private boolean isAniStart;
    boolean isEnable;
    private boolean isHidePromote;
    private boolean isInit;
    private int isNeedUploadVideo;
    private boolean isNotFirstLoad;
    private boolean isShowGuide;
    private boolean isUnMask;
    boolean isUp;
    private ImageView ivArrow;
    private ImageView ivBackground;
    private ImageView ivChartPlay;
    private ImageView ivChartShow;
    private ImageView ivClose;
    private ImageView ivDeepEnergySavingRunning;
    private ImageView ivLastEvent;
    private ImageView ivLeftArrow;
    private ImageView ivLeftEar;
    private ImageView ivLeftEarClickView;
    private ImageView ivMealtimeDate;
    private ImageView ivNextEvent;
    private ImageView ivPagerClose;
    private NewRoundImageview ivPopImage;
    private ImageView ivRightEar;
    private ImageView ivRightEarClickView;
    private ImageView ivTrialRemainingTimeClose;
    private ImageView ivUpArrow;
    private ImageView ivVlogTipClose;
    private ImageView ivWarnText;
    private Guide lastGuide;
    float lastX;
    float lastY;
    private long leftTime;
    private RelativeLayout live;
    private CirclePageIndicator liveIndicator;
    private LinearLayout llBottomMenuParentView;
    private LinearLayout llChartPanel;
    private LinearLayout llD4ViewBtnPanel;
    private LinearLayout llDay;
    private LinearLayout llHistoryRecordPanel;
    private LinearLayout llManualFeedNum1;
    private LinearLayout llManualFeedNum2;
    private LinearLayout llPets;
    private LinearLayout llTopView;
    private LinearLayout llTrialRemainingTime;
    private LinearLayout llVerticalDataView;
    private LinearLayout llWarnPanel;
    private LinearLayout llWarnText;
    private LinearLayout llWorkRecord;
    private Runnable loadFullVideoRunnable;
    private Context mContext;
    private float mSlideOffset;
    private VelocityTracker mVelocityTracker;
    private MainHandler mainHandler;
    private boolean manualCloseTrialRemainingTime;
    private MaterialEventInfo materialEventInfo;
    private MaterialUploadRes materialInfo;
    private MenuOnClickListener menuOnClickListener;
    private long moreLickTime;
    private MultipleImageView.MultipleImageAdapter multipleImageAdapter;
    private String newPath;
    private int offSet;
    private BasePetkitDeviceDatePickerView.OnCalendarChangeListener onCalendarChangeListener;
    public CloudServiceView.OnPurchaseBtnClickListener onPurchaseBtnClickListener;
    private LinearLayout petEatingView;
    private boolean playVideoInPrivacyMode;
    private D4shMultipleVideoPlayer player;
    private RelativeLayout playerContainer;
    private D4shHomePlayerLandscapeView playerLandscapeView;
    private D4shPlayerPortraitView playerPortraitView;
    private D4shPlayerSoundWaveView playerSoundWaveView;
    private int popCount;
    private PrivacyListener privacyListener;
    private PromoteView promoteView;
    private boolean pullFlag;
    private boolean ready;
    private int recordStartTime;
    private int recordType;
    private RelatedProductsInfor relatedProductsInfor;
    private RelativeLayout rlBtnAndWarnPanel;
    private RelativeLayout rlChart;
    private RelativeLayout rlD4shRecordView;
    private RelativeLayout rlData1;
    private RelativeLayout rlData2;
    private RelativeLayout rlDeepEnergySaving;
    private RelativeLayout rlDeepEnergySavingInit;
    private RelativeLayout rlDeepEnergySavingRunning;
    private RelativeLayout rlMask;
    private RelativeLayout rlPop;
    private RelativeLayout rlPopContent;
    private int rlPopHeight;
    private RelativeLayout rlPopImage;
    private int rlPopWidth;
    private RelativeLayout rlPurchaseEligibility;
    private RelativeLayout rlRightTopWindow;
    private RelativeLayout rlTipPanel;
    private RelativeLayout rlTitleStatus;
    private RelativeLayout rlTopView;
    private RelativeLayout rlViewD4DeviceCenter;
    private RelativeLayout rlVlogOpenTip;
    private RelativeLayout rlVlogRvPanel;
    private RelativeLayout rl_live_panel;
    private D4shHomeRecyclerView rvD4RecordView;
    private RecyclerView rvHighlights;
    private View scPoint;
    private View scPoint2;
    private int screenWidth;
    int scrollDistance;
    private RelativeLayout scrollView;
    private SmallCircleDotView sdPet;
    private Guide secondGuide;
    public List<String> selectedPetIds;
    private boolean serviceStatusMaybeChanged;
    private boolean showGuide;
    private List<D4shChartInfo> sortEventList;
    private boolean startCollAnim;
    private float startDragY;
    private boolean startExpandAnim;
    private float startMoveY;
    private float tempY;
    private Guide thirdGuide;
    private Toolbar toolbar;
    private ImageView toolbarBack;
    private View toolbarBackground;
    private TextView toolbarTitle;
    private TextView tvD4BottomPlan;
    private TextView tvD4PlanDes;
    private TextView tvD4PlanFeed;
    private TextView tvD4RealFeed;
    private TextView tvDeepEnergySavingInitCancle;
    private TextView tvDeepEnergySavingInitTime;
    private TextView tvDeviceTag;
    private TextView tvEatTime;
    private TextView tvHasOut1;
    private TextView tvHasOut2;
    private TextView tvImmediateRenewal;
    private TextView tvManualFeedNum1;
    private TextView tvManualFeedNum2;
    private TextView tvMealtimeDate;
    private TextView tvMore;
    private TextView tvName;
    private TextView tvPetName;
    private TextView tvPetName2;
    private TextView tvPetWeight;
    private TextView tvPlanHasFinishedNum1;
    private TextView tvPlanHasFinishedNum2;
    private TextView tvPopPetName;
    private TextView tvPopTime;
    private TextView tvPurchaseEligibility;
    private TextView tvTime;
    private TextView tvTitleStatus;
    private TextView tvTodayEatStr;
    private TextView tvTrialRemainingTime;
    private TextView tvVlogOpen;
    private TextView tvWarnText;
    private TextView tvY1;
    private TextView tvY2;
    private TextView tvY3;
    private int verticalLiveViewHeight;
    private VerticalScrollView verticalScrollView;
    private boolean videoExpireOrLoss;
    private View viewCenter;
    private ViewPager viewPager;
    private Disposable viewPagerDisposable;
    private MyChartViewPager vp;
    private InterceptViewPager vpLive;
    private NewWifiWeakWindow wifiWeakWindow;
    private SpannableStringColorsPicPromptWindow window;

    public interface ChartOnClickListener {
        void onChartClick(int i, String str);
    }

    public interface D4shVlogRecordAdapterListener {
        void onMarkVlogClick(HighlightRecord highlightRecord);

        void onPlayBtnClick(HighlightRecord highlightRecord);
    }

    public interface MenuOnClickListener {
        void onBottomMenuClick(MenuType menuType);
    }

    public enum MenuType {
        FEED_NOW,
        STOP_FEED,
        FEED_PLAN,
        TYPE_MONTH,
        CHART_SELECT_PET,
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
        DATE_PICKER,
        FOOD_CONTROL,
        OPEN_HIGHLIGHT,
        CONSUMABLE_DESICCANT_STATE,
        CONSUMABLE_DESICCANT_REMIND,
        PURCHASE_ELIGIBILITY_CLICK
    }

    public interface OnHistogramPageChange {
        void pageChange(int i);
    }

    public interface PrivacyListener {
        void agreePrivacy();

        void jumpToWeb(String str, String str2);

        void onVideoItemClick(String str);
    }

    private void initCenterWindowParams(int i) {
    }

    private void initTodayTime() {
    }

    private void landscapeVisibleCheck() {
    }

    private void setFeedDataCardViewTitleFeedingStatus() {
    }

    private void showPopWindow() {
    }

    private void visibleCheck() {
    }

    @Override // com.petkit.android.activities.petkitBleDevice.d4s.widget.D4sHistogram.ChartOnClickListener
    public void clearSelection() {
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

    @Override // com.petkit.android.media.video.player.BasePetkitPlayerLandscapeViewClickListener
    public void onEventDelete() {
    }

    @Override // com.petkit.android.media.video.player.listener.BasePetkitPlayerListener
    public void onFastBackwardResult(boolean z) {
    }

    @Override // com.petkit.android.media.video.player.listener.BasePetkitPlayerListener
    public void onFastForwardResult(boolean z) {
    }

    @Override // com.petkit.android.media.video.player.BasePetkitPlayerPortraitViewClickListener
    public void onFullScreenBtnClick() {
    }

    @Override // com.petkit.android.media.video.player.BasePetkitPlayerLandscapeViewClickListener
    public void onFullVideoDownload() {
    }

    @Override // com.petkit.android.media.video.player.listener.BasePetkitPlayerListener
    public void onInitSuccess() {
    }

    @Override // com.petkit.android.media.video.player.BasePetkitPlayerLandscapeViewClickListener
    public void onLandscapeCcvlBtnClick(boolean z, int i) {
    }

    @Override // com.petkit.android.media.video.player.BasePetkitPlayerLandscapeViewClickListener
    public void onLandscapeTripodHeadBtnClick() {
    }

    @Override // com.petkit.android.media.video.player.BasePetkitPlayerLandscapeViewClickListener
    public void onMultipleVideoItemClick(int i) {
    }

    @Override // com.petkit.android.media.video.player.listener.BasePetkitPlayerListener
    public void onMultipleVideosScrolled(int i) {
    }

    @Override // com.petkit.android.media.video.player.listener.BasePetkitPlayerListener
    public void onPageScrolled(int i) {
    }

    @Override // com.petkit.android.activities.petkitBleDevice.widget.PetkitSlidingUpPanelLayout.PanelSlideListener
    public void onPanelStateChanged(View view, PetkitSlidingUpPanelLayout.PanelState panelState, PetkitSlidingUpPanelLayout.PanelState panelState2) {
    }

    @Override // com.petkit.android.media.video.player.listener.BasePetkitPlayerListener
    public void onPausePlay() {
    }

    @Override // com.petkit.android.media.video.player.BasePetkitPlayerPortraitViewClickListener
    public void onPlayBtnClick() {
    }

    @Override // com.petkit.android.media.video.player.BasePetkitPlayerLandscapeViewClickListener
    public void onPreVideoRecordClick(String str, String str2) {
    }

    @Override // com.petkit.android.media.video.player.BasePetkitPlayerLandscapeViewClickListener
    public void onPreviewVideoDownload() {
    }

    @Override // com.petkit.android.media.video.player.BasePetkitPlayerPortraitViewClickListener
    public void onQualityOrTimeSpeedBtnClick() {
    }

    @Override // com.petkit.android.media.video.player.BasePetkitPlayerLandscapeViewClickListener
    public void onRecordAudit() {
    }

    @Override // com.petkit.android.media.video.player.listener.BasePetkitPlayerListener
    public void onRestart() {
    }

    @Override // com.petkit.android.media.video.player.listener.BasePetkitPlayerListener
    public void onSeekCompleted() {
    }

    @Override // com.petkit.android.media.video.player.listener.BasePetkitPlayerListener
    public void onStartPlay() {
    }

    @Override // com.petkit.android.activities.petkitBleDevice.d4sh.adapter.RecordOnClickListener
    public void onViewClick(ItemsBean itemsBean, int i) {
    }

    @Override // com.petkit.android.media.video.player.BasePetkitPlayerLandscapeViewClickListener
    public void onVirtualMoreClick() {
    }

    public void setCurrentItem(int i) {
    }

    public void setFeedingAmount(int i) {
    }

    public D4shHomeDeviceView(Context context) {
        this(context, null);
    }

    public D4shHomeDeviceView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public D4shHomeDeviceView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.isShowGuide = false;
        this.guide1 = null;
        this.dateFormat = new SimpleDateFormat(DateUtil.DATE_FORMAT_13);
        this.recordType = 3;
        this.d4sRemoveData = new D4shRemoveData();
        this.selectedPetIds = new ArrayList();
        this.invisible = false;
        this.d4shPlayerStatus = 0;
        this.displayPreviewVideoItemIndex = -1;
        this.eatVideoTimesSpeed = 1.0f;
        this.isInit = false;
        this.flag = false;
        this.pullFlag = false;
        this.startMoveY = 0.0f;
        this.ready = false;
        this.lastX = 0.0f;
        this.lastY = 0.0f;
        this.isEnable = false;
        this.scrollDistance = 0;
        this.dataList = new ArrayList();
        this.current = 0;
        this.loadFullVideoRunnable = new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView.52
            @Override // java.lang.Runnable
            public void run() {
                D4shHomeDeviceView.this.playerLandscapeView.refreshFullVideoStatus();
            }
        };
        this.isNeedUploadVideo = 1;
        this.mContext = context;
        this.activity = (Activity) context;
        initView();
    }

    public void setD4shHomeDeviceViewListener(D4shHomeDeviceViewListener d4shHomeDeviceViewListener) {
        this.d4shHomeDeviceViewListener = d4shHomeDeviceViewListener;
    }

    private void initView() {
        this.contentView = ((ViewGroup) LayoutInflater.from(this.mContext).inflate(R.layout.layout_d4sh_home_device_view, this)).getChildAt(0);
        EventBus.getDefault().register(this);
        initPlayer();
        initDeviceWarnMessageViewGroup();
        initDeviceDataCardViewGroup();
        initDeviceRecordTagViewGroup();
        initToolbarView();
        initPetEatingView();
        initOtherView();
        initScrollView();
        initTodayTime();
        initViewPager();
        setChartData();
    }

    private void setChartData() {
        D4shRecord d4shRecord = this.d4shRecord;
        if (d4shRecord != null && d4shRecord.getDeviceShared() != null) {
            this.llPets.setVisibility(4);
        }
        this.selectedPetIds.add(ColorUtils.ALL_PET);
        final RelativeLayout relativeLayout = (RelativeLayout) LayoutInflater.from(this.mContext).inflate(R.layout.chart_view_pager, (ViewGroup) null);
        TextView textView = (TextView) relativeLayout.findViewById(R.id.tv0);
        TextView textView2 = (TextView) relativeLayout.findViewById(R.id.tv6);
        TextView textView3 = (TextView) relativeLayout.findViewById(R.id.tv12);
        TextView textView4 = (TextView) relativeLayout.findViewById(R.id.tv18);
        TextView textView5 = (TextView) relativeLayout.findViewById(R.id.tv24);
        String str = "0" + getResources().getString(R.string.Unit_hour_short);
        String str2 = Constants.VIA_SHARE_TYPE_INFO + getResources().getString(R.string.Unit_hour_short);
        String str3 = "12" + getResources().getString(R.string.Unit_hour_short);
        String str4 = "18" + getResources().getString(R.string.Unit_hour_short);
        String str5 = Constants.VIA_REPORT_TYPE_CHAT_AIO + getResources().getString(R.string.Unit_hour_short);
        textView.setText(str);
        textView2.setText(str2);
        textView3.setText(str3);
        textView4.setText(str4);
        textView5.setText(str5);
        this.chartView = (RelativeLayout) relativeLayout.getChildAt(0);
        this.vp.setAdapter(new PagerAdapter() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView.1
            @Override // androidx.viewpager.widget.PagerAdapter
            public void destroyItem(@NonNull ViewGroup viewGroup, int i, @NonNull Object obj) {
            }

            @Override // androidx.viewpager.widget.PagerAdapter
            public int getCount() {
                return 1;
            }

            @Override // androidx.viewpager.widget.PagerAdapter
            public boolean isViewFromObject(@NonNull View view, @NonNull Object obj) {
                return view == obj;
            }

            @Override // androidx.viewpager.widget.PagerAdapter
            @NonNull
            public Object instantiateItem(@NonNull ViewGroup viewGroup, int i) {
                viewGroup.addView(relativeLayout);
                return relativeLayout;
            }
        });
        this.vp.setDragListener(new MyChartViewPager.DragListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView.2
            @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.MyChartViewPager.DragListener
            public void dragLeft() {
                D4shHomeDeviceView.this.dragChart(-1);
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.MyChartViewPager.DragListener
            public void dragRight() {
                if (D4shHomeDeviceView.this.calendarTime == 0) {
                    D4shHomeDeviceView d4shHomeDeviceView = D4shHomeDeviceView.this;
                    d4shHomeDeviceView.calendarTime = D4shUtils.getCurrentZoneTime(d4shHomeDeviceView.d4shRecord, D4shHomeDeviceView.this.d4shRecord.getDeviceId(), System.currentTimeMillis(), D4shHomeDeviceView.this.d4shRecord.getTypeCode() == 1 ? 26 : 25);
                }
                if (DateUtil.isSameDay(D4shHomeDeviceView.this.calendarTime - 86400000, D4shHomeDeviceView.this.d4shRecord.getActualTimeZone())) {
                    return;
                }
                D4shHomeDeviceView.this.dragChart(1);
            }
        });
        dragChart(0);
    }

    public void refreshPurchaseEligibility(PurchaseEligibilityInfo.PurchaseEligibility purchaseEligibility) {
        if (purchaseEligibility != null) {
            this.rlPurchaseEligibility.setVisibility(0);
            this.tvPurchaseEligibility.setText(purchaseEligibility.getNoticeBannerMsg());
        } else {
            this.rlPurchaseEligibility.setVisibility(8);
        }
    }

    private void initViewPager() {
        this.indicator = (CirclePageIndicator) this.contentView.findViewById(R.id.indicator);
        this.ivPagerClose = (ImageView) this.contentView.findViewById(R.id.iv_pager_close);
        this.viewPager = (ViewPager) this.contentView.findViewById(R.id.vp_home_banner);
        FrameLayout frameLayout = (FrameLayout) this.contentView.findViewById(R.id.fl_home_banner);
        this.flHomeBanner = frameLayout;
        ViewGroup.LayoutParams layoutParams = frameLayout.getLayoutParams();
        layoutParams.height = ((BaseApplication.displayMetrics.widthPixels - ArmsUtils.dip2px(this.mContext, 16.0f)) / 343) * 64;
        this.flHomeBanner.setLayoutParams(layoutParams);
        this.flHomeBanner.setVisibility(8);
        this.indicator.setVisibility(8);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x002c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void refreshHomeBannerData(com.petkit.android.activities.petkitBleDevice.d4sh.mode.D4shBannerData r12) {
        /*
            Method dump skipped, instruction units count: 691
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView.refreshHomeBannerData(com.petkit.android.activities.petkitBleDevice.d4sh.mode.D4shBannerData):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$refreshHomeBannerData$0(Long l) throws Exception {
        ViewPager viewPager = this.viewPager;
        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
        this.current = this.viewPager.getCurrentItem() % (this.d4shHomeBannerPageAdapter.getDataList().size() != 0 ? this.d4shHomeBannerPageAdapter.getDataList().size() : 1);
    }

    private void showFreeActivity(D4shBannerData d4shBannerData) {
        D4shBannerData.FreeActivity freeActivity = d4shBannerData.getFreeActivity();
        boolean zEquals = "zh_CN".equals(UserInforUtils.getLanguageLocaleString(CommonUtils.getAppContext()));
        if (CommonUtils.getSysBoolMap(this.activity, com.petkit.android.utils.Constants.D4SH_D4H_FREE_ACTIVITY + this.d4shRecord.getDeviceId() + "&" + this.d4shRecord.getTypeCode(), false)) {
            return;
        }
        CloudServiceFreeTrialDialog cloudServiceFreeTrialDialog = this.cloudServiceFreeTrialDialog;
        if ((cloudServiceFreeTrialDialog == null || !cloudServiceFreeTrialDialog.isShowing()) && freeActivity.getImageRet() != null) {
            Activity activity = this.activity;
            D4shBannerData.ImageRet imageRet = freeActivity.getImageRet();
            CloudServiceFreeTrialDialog cloudServiceFreeTrialDialog2 = new CloudServiceFreeTrialDialog(activity, zEquals ? imageRet.getPopChineseImage() : imageRet.getPopEnglishImage(), new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView.6
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    CloudServiceView.OnPurchaseBtnClickListener onPurchaseBtnClickListener;
                    String strCreatePurchasePageUrl = D4shUtils.createPurchasePageUrl(D4shHomeDeviceView.this.activity, D4shHomeDeviceView.this.d4shRecord.getTypeCode() == 0 ? 25 : 26, D4shHomeDeviceView.this.d4shRecord.getDeviceId());
                    if (D4shHomeDeviceView.this.d4shRecord.getRealDeviceShared() != null) {
                        PetkitToast.showTopToast(D4shHomeDeviceView.this.activity, D4shHomeDeviceView.this.activity.getString(R.string.D4sh_no_cloud_free_tips), R.drawable.top_toast_warn_icon, 1);
                        return;
                    }
                    CommonUtils.addSysBoolMap(D4shHomeDeviceView.this.activity, com.petkit.android.utils.Constants.D4SH_D4H_FREE_ACTIVITY + D4shHomeDeviceView.this.d4shRecord.getDeviceId() + "&" + D4shHomeDeviceView.this.d4shRecord.getTypeCode(), true);
                    if (!TextUtils.isEmpty(strCreatePurchasePageUrl) && (onPurchaseBtnClickListener = D4shHomeDeviceView.this.onPurchaseBtnClickListener) != null) {
                        onPurchaseBtnClickListener.onRedirectToH5(strCreatePurchasePageUrl);
                    }
                    D4shHomeDeviceView.this.cloudServiceFreeTrialDialog.dismiss();
                }
            });
            this.cloudServiceFreeTrialDialog = cloudServiceFreeTrialDialog2;
            cloudServiceFreeTrialDialog2.getContentView().findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView.7
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    CommonUtils.addSysBoolMap(D4shHomeDeviceView.this.activity, com.petkit.android.utils.Constants.D4SH_D4H_FREE_ACTIVITY + D4shHomeDeviceView.this.d4shRecord.getDeviceId() + "&" + D4shHomeDeviceView.this.d4shRecord.getTypeCode(), true);
                    D4shHomeDeviceView.this.cloudServiceFreeTrialDialog.dismiss();
                }
            });
            this.cloudServiceFreeTrialDialog.setBackgroundDrawable(new BitmapDrawable());
            this.cloudServiceFreeTrialDialog.setOutsideTouchable(true);
            this.cloudServiceFreeTrialDialog.setTouchable(true);
            this.cloudServiceFreeTrialDialog.setAnimationStyle(R.style.PopupWindow_Default_Appearance_Animation);
            this.cloudServiceFreeTrialDialog.showAtLocation(this.activity.getWindow().getDecorView(), 17, 0, 0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dragChart(int i) {
        this.chartView.removeAllViews();
        this.calendarTime += ((long) i) * 86400000;
        D4shHomeDeviceViewListener d4shHomeDeviceViewListener = this.d4shHomeDeviceViewListener;
        if (d4shHomeDeviceViewListener != null) {
            d4shHomeDeviceViewListener.dragChart(i);
        }
        if (i != 0) {
            this.ivLeftArrow.setVisibility(8);
        }
    }

    public void initGuide() {
        boolean booleanSF = DataHelper.getBooleanSF(this.mContext, com.petkit.android.utils.Constants.D4SH_HOME_GUIDE);
        DataHelper.getBooleanSF(this.mContext, com.petkit.android.utils.Constants.D4SH_HOME_DRESSING_GUIDE);
        if (!booleanSF && this.d4shRecord.getDeviceShared() == null) {
            this.mainHandler.postDelayed(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView.8
                @Override // java.lang.Runnable
                public void run() {
                    if (D4shHomeDeviceView.this.dressingGuide != null) {
                        return;
                    }
                    GuideBuilder guideBuilder = new GuideBuilder();
                    guideBuilder.setTargetView(D4shHomeDeviceView.this.rlMask).setAlpha(180).setHighTargetCorner(50).setHighTargetPaddingLeft(ArmsUtils.dip2px(D4shHomeDeviceView.this.mContext, 0.0f)).setHighTargetPaddingRight(ArmsUtils.dip2px(D4shHomeDeviceView.this.mContext, 0.0f)).setHighTargetPaddingBottom(((int) ((Math.round((D4shHomeDeviceView.this.player.getWidth() * 10.0f) / 16.0f) / 214.0f) * 139.0f)) * (-1)).setHighTargetPaddingTop(ArmsUtils.dip2px(D4shHomeDeviceView.this.mContext, -50.0f)).setOutsideTouchable(false).setAutoDismiss(true);
                    guideBuilder.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView.8.1
                        @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
                        public void onShown() {
                        }

                        @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
                        public void onDismiss() {
                            DataHelper.setBooleanSF(D4shHomeDeviceView.this.mContext, com.petkit.android.utils.Constants.D4SH_HOME_GUIDE, Boolean.TRUE);
                            D4shHomeDeviceView.this.showFirstGuide();
                        }
                    });
                    guideBuilder.addComponent(new GuideD4shBottomRightTipAndBtnAndLineComponent(new GuideParam(D4shHomeDeviceView.this.getResources().getString(R.string.Set_Personal_Dress_Tips_Home), "1/5", 4, 48, "zh_CN".equals(UserInforUtils.getLanguageLocaleString(CommonUtils.getAppContext())) ? ArmsUtils.dip2px(D4shHomeDeviceView.this.mContext, -30.0f) : ArmsUtils.dip2px(D4shHomeDeviceView.this.mContext, 0.0f), 0, D4shHomeDeviceView.this.getResources().getString(R.string.Next_tip), R.layout.layout_d4sh_guide_bottom_right), new ConfirmListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView.8.2
                        @Override // com.petkit.android.activities.petkitBleDevice.w5.guide.ConfirmListener
                        public void onConfirmListener() {
                            Guide guide = D4shHomeDeviceView.this.dressingGuide;
                            if (guide != null) {
                                guide.dismiss();
                            }
                        }
                    }));
                    D4shHomeDeviceView.this.dressingGuide = guideBuilder.createGuide();
                    D4shHomeDeviceView d4shHomeDeviceView = D4shHomeDeviceView.this;
                    d4shHomeDeviceView.dressingGuide.show((Activity) d4shHomeDeviceView.getContext());
                    D4shHomeDeviceView.this.showGuide = true;
                }
            }, 500L);
            return;
        }
        if (!booleanSF) {
            showFirstGuide();
            return;
        }
        D4shHomeDeviceViewListener d4shHomeDeviceViewListener = this.d4shHomeDeviceViewListener;
        if (d4shHomeDeviceViewListener != null) {
            d4shHomeDeviceViewListener.guideShowed();
        }
        if (!DataHelper.getBooleanSF(this.mContext, com.petkit.android.utils.Constants.D4SH_PET_TRACKING_HOME_GUIDE) && this.d4shRecord.getSettings().getSmartFrame() == 0 && this.d4shRecord.getSettings().getPetDetection() == 0) {
            openPetTrackingTipWindow();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showFirstGuide() {
        int iDip2px;
        if (this.firstGuide != null) {
            return;
        }
        GuideBuilder guideBuilder = new GuideBuilder();
        guideBuilder.setTargetView(this.player).setAlpha(180).setHighTargetCorner(50).setHighTargetPaddingLeft(ArmsUtils.dip2px(this.mContext, 0.0f)).setHighTargetPaddingRight(ArmsUtils.dip2px(this.mContext, 0.0f)).setHighTargetPaddingBottom(ArmsUtils.dip2px(this.mContext, 0.0f)).setHighTargetPaddingTop(ArmsUtils.dip2px(this.mContext, 0.0f)).setOutsideTouchable(false).setAutoDismiss(true);
        guideBuilder.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView.9
            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onShown() {
            }

            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onDismiss() {
                D4shHomeDeviceView.this.showSecondGuide();
            }
        });
        if ("zh_CN".equals(UserInforUtils.getLanguageLocaleString(CommonUtils.getAppContext()))) {
            iDip2px = ArmsUtils.dip2px(this.mContext, -30.0f);
        } else {
            iDip2px = ArmsUtils.dip2px(this.mContext, 0.0f);
        }
        int i = iDip2px;
        if (this.d4shRecord.getDeviceShared() == null) {
            guideBuilder.addComponent(new GuideD4shBottomRightTipAndBtnAndLineComponent(new GuideParam(getResources().getString(R.string.D4sh_home_guide_prompt_one), "2/5", 4, 48, i, 0, getResources().getString(R.string.Next_tip), R.layout.layout_d4sh_guide_bottom_right), new ConfirmListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView.10
                @Override // com.petkit.android.activities.petkitBleDevice.w5.guide.ConfirmListener
                public void onConfirmListener() {
                    Guide guide = D4shHomeDeviceView.this.firstGuide;
                    if (guide != null) {
                        guide.dismiss();
                    }
                }
            }));
        } else {
            guideBuilder.addComponent(new GuideD4shBottomRightTipAndBtnAndLineComponent(new GuideParam(getResources().getString(R.string.D4sh_home_guide_prompt_one), "1/4", 4, 48, i, 0, getResources().getString(R.string.Next_tip), R.layout.layout_d4sh_guide_bottom_right), new ConfirmListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView.11
                @Override // com.petkit.android.activities.petkitBleDevice.w5.guide.ConfirmListener
                public void onConfirmListener() {
                    Guide guide = D4shHomeDeviceView.this.firstGuide;
                    if (guide != null) {
                        guide.dismiss();
                    }
                }
            }));
        }
        Guide guideCreateGuide = guideBuilder.createGuide();
        this.firstGuide = guideCreateGuide;
        guideCreateGuide.show((Activity) getContext());
        this.showGuide = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showSecondGuide() {
        int iDip2px;
        if (this.secondGuide != null) {
            return;
        }
        GuideBuilder guideBuilder = new GuideBuilder();
        guideBuilder.setTargetView(this.d4shDeviceDataCardViewGroup).setAlpha(180).setHighTargetCorner(50).setHighTargetPaddingLeft(ArmsUtils.dip2px(this.mContext, 0.0f)).setHighTargetPaddingRight(ArmsUtils.dip2px(this.mContext, 0.0f)).setHighTargetPaddingBottom(ArmsUtils.dip2px(this.mContext, 0.0f)).setHighTargetPaddingTop(ArmsUtils.dip2px(this.mContext, 0.0f)).setOutsideTouchable(false).setAutoDismiss(true);
        guideBuilder.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView.12
            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onShown() {
            }

            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onDismiss() {
                D4shHomeDeviceView.this.showThirdGuide();
            }
        });
        if ("zh_CN".equals(UserInforUtils.getLanguageLocaleString(CommonUtils.getAppContext()))) {
            iDip2px = ArmsUtils.dip2px(this.mContext, -30.0f);
        } else {
            iDip2px = ArmsUtils.dip2px(this.mContext, 0.0f);
        }
        int i = iDip2px;
        if (this.d4shRecord.getDeviceShared() == null) {
            guideBuilder.addComponent(new GuideD4shBottomRightTipAndBtnAndLineComponent(new GuideParam(getResources().getString(R.string.D4sh_home_guide_prompt_two), "3/5", 4, 48, i, 0, getResources().getString(R.string.Next_tip), R.layout.layout_d4sh_guide_bottom_right), new ConfirmListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView.13
                @Override // com.petkit.android.activities.petkitBleDevice.w5.guide.ConfirmListener
                public void onConfirmListener() {
                    if (D4shHomeDeviceView.this.secondGuide != null) {
                        D4shHomeDeviceView.this.secondGuide.dismiss();
                    }
                }
            }));
        } else {
            guideBuilder.addComponent(new GuideD4shBottomRightTipAndBtnAndLineComponent(new GuideParam(getResources().getString(R.string.D4sh_home_guide_prompt_two), "2/4", 4, 48, i, 0, getResources().getString(R.string.Next_tip), R.layout.layout_d4sh_guide_bottom_right), new ConfirmListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView.14
                @Override // com.petkit.android.activities.petkitBleDevice.w5.guide.ConfirmListener
                public void onConfirmListener() {
                    if (D4shHomeDeviceView.this.secondGuide != null) {
                        D4shHomeDeviceView.this.secondGuide.dismiss();
                    }
                }
            }));
        }
        Guide guideCreateGuide = guideBuilder.createGuide();
        this.secondGuide = guideCreateGuide;
        guideCreateGuide.show((Activity) getContext());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showThirdGuide() {
        if (this.thirdGuide != null) {
            return;
        }
        Rect rect = new Rect();
        this.activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        int i = rect.top;
        int[] iArr = new int[2];
        findViewById(R.id.iv_mealtime_date).getLocationOnScreen(iArr);
        if ((iArr[1] - i) + findViewById(R.id.iv_mealtime_date).getHeight() > this.bleMenuView.getTop()) {
            this.d4sViewLayout.scrollBy(0, ArmsUtils.dip2px(this.mContext, 100.0f));
        }
        GuideBuilder guideBuilder = new GuideBuilder();
        guideBuilder.setTargetView(findViewById(R.id.iv_mealtime_date)).setAlpha(180).setHighTargetCorner(50).setHighTargetPaddingLeft(ArmsUtils.dip2px(this.mContext, 3.0f)).setHighTargetPaddingRight(ArmsUtils.dip2px(this.mContext, 3.0f)).setHighTargetPaddingBottom(ArmsUtils.dip2px(this.mContext, 3.0f)).setHighTargetPaddingTop(ArmsUtils.dip2px(this.mContext, 3.0f)).setOutsideTouchable(false).setAutoDismiss(true);
        guideBuilder.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView.15
            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onShown() {
            }

            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onDismiss() {
                D4shHomeDeviceView.this.showLastGuide();
            }
        });
        if ("zh_CN".equals(UserInforUtils.getLanguageLocaleString(CommonUtils.getAppContext()))) {
            ArmsUtils.dip2px(this.mContext, -30.0f);
        } else {
            ArmsUtils.dip2px(this.mContext, 0.0f);
        }
        if (this.d4shRecord.getDeviceShared() == null) {
            guideBuilder.addComponent(new GuideD4shBottomRightTipAndBtnAndLineComponent(new GuideParam(getResources().getString(R.string.D4sh_home_guide_prompt_three), "4/5", 4, 48, ArmsUtils.dip2px(this.mContext, 0.0f), 0, getResources().getString(R.string.Next_tip), R.layout.layout_d4sh_guide_bottom_left), new ConfirmListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView.16
                @Override // com.petkit.android.activities.petkitBleDevice.w5.guide.ConfirmListener
                public void onConfirmListener() {
                    if (D4shHomeDeviceView.this.thirdGuide != null) {
                        D4shHomeDeviceView.this.thirdGuide.dismiss();
                    }
                }
            }));
        } else {
            guideBuilder.addComponent(new GuideD4shBottomRightTipAndBtnAndLineComponent(new GuideParam(getResources().getString(R.string.D4sh_home_guide_prompt_three), "3/4", 4, 48, ArmsUtils.dip2px(this.mContext, 0.0f), 0, getResources().getString(R.string.Next_tip), R.layout.layout_d4sh_guide_bottom_left), new ConfirmListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView.17
                @Override // com.petkit.android.activities.petkitBleDevice.w5.guide.ConfirmListener
                public void onConfirmListener() {
                    if (D4shHomeDeviceView.this.thirdGuide != null) {
                        D4shHomeDeviceView.this.thirdGuide.dismiss();
                    }
                }
            }));
        }
        Guide guideCreateGuide = guideBuilder.createGuide();
        this.thirdGuide = guideCreateGuide;
        guideCreateGuide.show((Activity) getContext());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showLastGuide() {
        if (this.lastGuide != null) {
            return;
        }
        GuideBuilder guideBuilder = new GuideBuilder();
        guideBuilder.setTargetView(this.bleMenuView).setAlpha(180).setHighTargetCorner(50).setHighTargetPaddingLeft(ArmsUtils.dip2px(this.mContext, 3.0f)).setHighTargetPaddingRight(ArmsUtils.dip2px(this.mContext, 3.0f)).setHighTargetPaddingBottom(ArmsUtils.dip2px(this.mContext, 3.0f)).setHighTargetPaddingTop(ArmsUtils.dip2px(this.mContext, 3.0f)).setOutsideTouchable(false).setAutoDismiss(true);
        guideBuilder.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView.18
            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onShown() {
            }

            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onDismiss() {
                DataHelper.setBooleanSF(D4shHomeDeviceView.this.mContext, com.petkit.android.utils.Constants.D4SH_HOME_GUIDE, Boolean.TRUE);
                D4shHomeDeviceView.this.showGuide = false;
                if (D4shHomeDeviceView.this.d4shHomeDeviceViewListener != null) {
                    D4shHomeDeviceView.this.d4shHomeDeviceViewListener.guideShowed();
                }
                if (DataHelper.getBooleanSF(D4shHomeDeviceView.this.mContext, com.petkit.android.utils.Constants.D4SH_PET_TRACKING_HOME_GUIDE) || D4shHomeDeviceView.this.d4shRecord.getSettings().getSmartFrame() != 0) {
                    return;
                }
                D4shHomeDeviceView.this.openPetTrackingTipWindow();
            }
        });
        if (this.d4shRecord.getDeviceShared() == null) {
            guideBuilder.addComponent(new GuideD4shBottomRightTipAndBtnAndLineComponent(new GuideParam(getResources().getString(R.string.D4sh_home_guide_prompt_four), "5/5", 2, 16, ArmsUtils.dip2px(this.mContext, 16.0f), 0, getResources().getString(R.string.Know), R.layout.layout_d4sh_guide_top_right), new ConfirmListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView.19
                @Override // com.petkit.android.activities.petkitBleDevice.w5.guide.ConfirmListener
                public void onConfirmListener() {
                    if (D4shHomeDeviceView.this.lastGuide != null) {
                        D4shHomeDeviceView.this.lastGuide.dismiss();
                    }
                }
            }));
        } else {
            guideBuilder.addComponent(new GuideD4shBottomRightTipAndBtnAndLineComponent(new GuideParam(getResources().getString(R.string.D4sh_home_guide_prompt_four), "4/4", 2, 16, ArmsUtils.dip2px(this.mContext, 16.0f), 0, getResources().getString(R.string.Know), R.layout.layout_d4sh_guide_top_right), new ConfirmListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView.20
                @Override // com.petkit.android.activities.petkitBleDevice.w5.guide.ConfirmListener
                public void onConfirmListener() {
                    if (D4shHomeDeviceView.this.lastGuide != null) {
                        D4shHomeDeviceView.this.lastGuide.dismiss();
                    }
                }
            }));
        }
        Guide guideCreateGuide = guideBuilder.createGuide();
        this.lastGuide = guideCreateGuide;
        guideCreateGuide.show((Activity) getContext());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void openPetTrackingTipWindow() {
        int i;
        if (this.d4shRecord.getState().getPim() == 2) {
            return;
        }
        DataHelper.setBooleanSF(this.mContext, com.petkit.android.utils.Constants.D4SH_PET_TRACKING_HOME_GUIDE, Boolean.TRUE);
        if (this.d4shRecord.getTypeCode() == 0) {
            if (DeviceUtils.getLanguageLocale(BaseApplication.context) != null && DeviceUtils.getLanguageLocale(BaseApplication.context).getLanguage().equals("zh")) {
                i = R.drawable.d4h_pet_tracking_icon_cn;
            } else {
                i = R.drawable.d4h_pet_tracking_icon_en;
            }
        } else if (DeviceUtils.getLanguageLocale(BaseApplication.context) != null && DeviceUtils.getLanguageLocale(BaseApplication.context).getLanguage().equals("zh")) {
            i = R.drawable.d4h_pet_tracking_icon_cn;
        } else {
            i = R.drawable.d4h_pet_tracking_icon_en;
        }
        if (this.aiDialog == null) {
            this.aiDialog = new FunctionDialog.ToiletBuilder().setTitle(getContext().getResources().getString(R.string.Smart_Capture_Cat_Title)).setCenterImageRes(i).setCenterTipOne(getContext().getResources().getString(R.string.Smart_Capture_Cat_Title_Desc)).setCenterTipGravity(1).setBottomLeftStr(getContext().getResources().getString(R.string.Cancel)).setBottomLeftColor(getContext().getResources().getColor(R.color.t4_text_gray)).setBottomRightStr(getContext().getResources().getString(R.string.To_open_it)).setBottomRightColor(getContext().getResources().getColor(R.color.new_bind_blue)).setRightListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$openPetTrackingTipWindow$1(view);
                }
            }).builder((AppCompatActivity) this.activity);
        }
        if (this.aiDialog.isShowing()) {
            return;
        }
        this.aiDialog.show(this.activity.getWindow().getDecorView());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$openPetTrackingTipWindow$1(View view) {
        this.activity.startActivity(D4shCameraSettingActivity.newIntent(this.mContext, this.d4shRecord.getDeviceId(), this.d4shRecord.getTypeCode()));
    }

    private void initOtherView() {
        this.d4shHomeDeviceDataView = (LinearLayout) findViewById(R.id.d4sh_home_device_data_view);
        this.d4shLandscapePlayerContainerView = (LinearLayout) findViewById(R.id.d4sh_landscape_player_container_view);
        this.scrollView = (RelativeLayout) findViewById(R.id.rl_d4s_view_layout);
        TextView textView = (TextView) findViewById(R.id.tv_more);
        this.tvMore = textView;
        textView.setText(getResources().getString(R.string.More));
        this.rvHighlights = (RecyclerView) findViewById(R.id.rv_highlights);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.mContext);
        linearLayoutManager.setOrientation(0);
        this.rvHighlights.setLayoutManager(linearLayoutManager);
        this.rlTipPanel = (RelativeLayout) findViewById(R.id.rl_tip_panel);
        this.tvName = (TextView) findViewById(R.id.tv_name);
        this.tvTime = (TextView) findViewById(R.id.tv_time);
        this.tvImmediateRenewal = (TextView) findViewById(R.id.tv_immediate_renewal);
        this.ivClose = (ImageView) findViewById(R.id.iv_close);
        this.vp = (MyChartViewPager) findViewById(R.id.vp);
        this.rlPurchaseEligibility = (RelativeLayout) findViewById(R.id.rl_purchase_eligibility);
        this.tvPurchaseEligibility = (TextView) findViewById(R.id.tv_purchase_eligibility);
        this.rlVlogOpenTip = (RelativeLayout) findViewById(R.id.rl_vlog_open_tip);
        this.rlVlogRvPanel = (RelativeLayout) findViewById(R.id.rl_vlog_rv_panel);
        this.tvVlogOpen = (TextView) findViewById(R.id.tv_open_vlog);
        this.ivVlogTipClose = (ImageView) findViewById(R.id.iv_vlog_tip_close);
        this.promoteView = (PromoteView) this.contentView.findViewById(R.id.promoteView);
        this.rlTitleStatus = (RelativeLayout) findViewById(R.id.rl_title_status);
        this.tvTitleStatus = (TextView) findViewById(R.id.tv_title_status);
        this.rlDeepEnergySavingRunning = (RelativeLayout) findViewById(R.id.rl_deep_energy_saving_running);
        this.rlD4shRecordView = (RelativeLayout) findViewById(R.id.d4sh_record_view);
        this.rlDeepEnergySaving = (RelativeLayout) findViewById(R.id.rl_deep_energy_saving);
        this.ivDeepEnergySavingRunning = (ImageView) findViewById(R.id.iv_deep_energy_saving_running);
        this.rlDeepEnergySavingInit = (RelativeLayout) findViewById(R.id.rl_deep_energy_saving_init);
        this.tvDeepEnergySavingInitTime = (TextView) findViewById(R.id.tv_deep_energy_saving_init_time);
        this.tvDeepEnergySavingInitCancle = (TextView) findViewById(R.id.tv_deep_energy_saving_init_cancle);
        this.tvManualFeedNum1 = (TextView) findViewById(R.id.tv_manual_feed_num1);
        this.tvManualFeedNum2 = (TextView) findViewById(R.id.tv_manual_feed_num2);
        this.llPets = (LinearLayout) findViewById(R.id.ll_pets);
        this.ivChartShow = (ImageView) findViewById(R.id.iv_chart_show);
        this.tvEatTime = (TextView) findViewById(R.id.tv_today_eat_amount);
        this.scPoint = findViewById(R.id.sc_point);
        this.scPoint2 = findViewById(R.id.sc_point2);
        this.tvPetName = (TextView) findViewById(R.id.tv_pet_name);
        this.tvPetName2 = (TextView) findViewById(R.id.tv_pet_name2);
        this.tvPetWeight = (TextView) findViewById(R.id.tv_pet_weight);
        this.tvY1 = (TextView) findViewById(R.id.tv_y1);
        this.tvY2 = (TextView) findViewById(R.id.tv_y2);
        this.tvY3 = (TextView) findViewById(R.id.tv_y3);
        this.rlPopContent = (RelativeLayout) findViewById(R.id.rl_pop_content);
        this.rlPop = (RelativeLayout) findViewById(R.id.rl_pop);
        this.llDay = (LinearLayout) findViewById(R.id.ll_day);
        this.ivArrow = (ImageView) findViewById(R.id.iv_arrow);
        this.tvPopPetName = (TextView) findViewById(R.id.tv_pop_pet_name);
        this.tvPopTime = (TextView) findViewById(R.id.tv_pop_time);
        this.tvTodayEatStr = (TextView) findViewById(R.id.tv_today_toilet_str);
        this.sdPet = (SmallCircleDotView) findViewById(R.id.sd_pet);
        this.ivLastEvent = (ImageView) findViewById(R.id.iv_last_event);
        this.ivNextEvent = (ImageView) findViewById(R.id.iv_next_event);
        this.rlPopImage = (RelativeLayout) findViewById(R.id.rl_pop_image);
        this.ivChartPlay = (ImageView) findViewById(R.id.iv_chart_play);
        this.ivPopImage = (NewRoundImageview) findViewById(R.id.iv_pop_image);
        this.rlChart = (RelativeLayout) findViewById(R.id.rl_chart);
        this.tvPlanHasFinishedNum1 = (TextView) findViewById(R.id.tv_plan_has_been_finished_num1);
        this.tvPlanHasFinishedNum2 = (TextView) findViewById(R.id.tv_plan_has_been_finished_num2);
        this.rlData1 = (RelativeLayout) findViewById(R.id.rl_data1);
        this.rlData2 = (RelativeLayout) findViewById(R.id.rl_data2);
        this.tvHasOut1 = (TextView) findViewById(R.id.tv_has_out1);
        this.tvHasOut2 = (TextView) findViewById(R.id.tv_has_out2);
        this.d4sViewLayout = (PetkitScrollViewWithListener) findViewById(R.id.d4s_view_layout);
        this.llManualFeedNum1 = (LinearLayout) findViewById(R.id.ll_manual_feed_num1);
        this.llManualFeedNum2 = (LinearLayout) findViewById(R.id.ll_manual_feed_num2);
        this.tvMealtimeDate = (TextView) findViewById(R.id.tv_mealtime_date);
        this.ivMealtimeDate = (ImageView) findViewById(R.id.iv_mealtime_date);
        this.llChartPanel = (LinearLayout) findViewById(R.id.ll_chart_panel);
        this.tvDeviceTag = (TextView) findViewById(R.id.tv_device_tag);
        this.llWarnPanel = (LinearLayout) findViewById(R.id.ll_warn_panel);
        this.rlViewD4DeviceCenter = (RelativeLayout) findViewById(R.id.rl_view_d4_device_center);
        this.viewCenter = findViewById(R.id.view_center);
        this.tvD4PlanDes = (TextView) findViewById(R.id.tv_d4_plan_des);
        this.ivWarnText = (ImageView) findViewById(R.id.iv_warn_text);
        this.rlBtnAndWarnPanel = (RelativeLayout) findViewById(R.id.rl_btn_and_warn_panel);
        this.ivUpArrow = (ImageView) findViewById(R.id.iv_up_arrow);
        this.llVerticalDataView = (LinearLayout) findViewById(R.id.ll_vertiacal_data_view);
        this.flTopPanel = (FrameLayout) findViewById(R.id.fl_top_panel);
        this.bleMenuView = (BleDeviceHomeMenuView) findViewById(R.id.ble_menu_view);
        this.rvD4RecordView = (D4shHomeRecyclerView) findViewById(R.id.rv_d4_recordView);
        new LinearLayoutManager(this.mContext);
        this.rvD4RecordView.setLayoutManager(new LinearLayoutManager(this.mContext));
        this.llHistoryRecordPanel = (LinearLayout) findViewById(R.id.ll_history_record_panel);
        this.llBottomMenuParentView = (LinearLayout) findViewById(R.id.ll_bottom_menu_parent_view);
        this.animatorLeftSetList = new ArrayList();
        this.mVelocityTracker = VelocityTracker.obtain();
        this.mainHandler = new MainHandler(this.mContext);
        this.handler = new Handler(Looper.myLooper()) { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView.21
            @Override // android.os.Handler
            public void handleMessage(@NonNull Message message) {
                if (message.what == 9) {
                    D4shHomeDeviceView.this.hideAdapterCatFace();
                }
            }
        };
        this.ivLeftArrow = (ImageView) findViewById(R.id.iv_left_arrow);
        this.tvD4RealFeed = (TextView) findViewById(R.id.tv_d4_real_feed);
        this.tvD4PlanFeed = (TextView) findViewById(R.id.tv_d4_plan_feed);
        this.llTrialRemainingTime = (LinearLayout) findViewById(R.id.ll_trial_remaining_time);
        this.tvTrialRemainingTime = (TextView) findViewById(R.id.tv_trial_remaining_time);
        this.ivTrialRemainingTimeClose = (ImageView) findViewById(R.id.iv_trial_remaining_time_close);
        this.bleMenuView.setDeviceType(25, 0);
        this.bleMenuView.setOnMenuClickListener(this);
        this.bleMenuView.setBatteryModeClickListener(this);
        this.bleMenuView.setOnD4shDeviceHomeMenuTouchListener(this);
        this.tvMore.setOnClickListener(this);
        this.llPets.setOnClickListener(this);
        this.tvVlogOpen.setOnClickListener(this);
        this.ivVlogTipClose.setOnClickListener(this);
        this.rlDeepEnergySavingRunning.setOnClickListener(this);
        this.ivMealtimeDate.setOnClickListener(this);
        this.tvDeepEnergySavingInitCancle.setOnClickListener(this);
        this.ivChartShow.setOnClickListener(this);
        this.rlTitleStatus.setOnClickListener(this);
        this.ivLastEvent.setOnClickListener(this);
        this.ivNextEvent.setOnClickListener(this);
        this.rlPurchaseEligibility.setOnClickListener(this);
        this.d4sViewLayout.setScrollviewOnTouchListener(new PetkitScrollViewWithListener.ScrollviewOnTouchListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView.22
            @Override // com.petkit.android.activities.petkitBleDevice.widget.PetkitScrollViewWithListener.ScrollviewOnTouchListener
            public void onScrolling(int i, int i2, int i3, int i4) {
                int i5;
                if (D4shHomeDeviceView.this.showGuide) {
                    return;
                }
                if (D4shHomeDeviceView.this.d4sViewLayout.getScrollY() < D4shHomeDeviceView.this.toolbar.getHeight()) {
                    D4shHomeDeviceView.this.toolbarBackground.setAlpha((D4shHomeDeviceView.this.d4sViewLayout.getScrollY() * 1.0f) / D4shHomeDeviceView.this.toolbar.getHeight());
                } else {
                    D4shHomeDeviceView.this.toolbarBackground.setAlpha(1.0f);
                }
                D4shHomeDeviceView.this.hideCatFacePop();
                D4shHomeDeviceView.this.rlPop.setVisibility(8);
                D4shHomeDeviceView.this.setD4shDeviceRecordTagViewGroupTopFixedVisible();
                if (i2 - i4 > 5) {
                    Log.d("scrolling", "up");
                    D4shHomeDeviceView d4shHomeDeviceView = D4shHomeDeviceView.this;
                    d4shHomeDeviceView.isUp = true;
                    if (!d4shHomeDeviceView.startExpandAnim && !D4shHomeDeviceView.this.startCollAnim && !D4shHomeDeviceView.this.isHidePromote) {
                        D4shHomeDeviceView.this.startPromoteViewAnim();
                    }
                    if (!D4shHomeDeviceView.this.isAniStart && D4shHomeDeviceView.this.bleMenuView.getVisibility() == 0) {
                        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(D4shHomeDeviceView.this.bleMenuView, Key.TRANSLATION_Y, 0.0f, D4shHomeDeviceView.this.bleMenuView.getMeasuredHeight());
                        objectAnimatorOfFloat.setDuration(400L);
                        objectAnimatorOfFloat.addListener(new Animator.AnimatorListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView.22.1
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
                                D4shHomeDeviceView.this.isAniStart = false;
                                D4shHomeDeviceView.this.bleMenuView.setVisibility(4);
                            }
                        });
                        objectAnimatorOfFloat.start();
                        D4shHomeDeviceView.this.isAniStart = true;
                    }
                } else {
                    D4shHomeDeviceView.this.isUp = false;
                }
                if (D4shHomeDeviceView.this.d4shHomeRecordAdapter.getDateDataList() != null) {
                    int i6 = 0;
                    while (true) {
                        if (i6 >= D4shHomeDeviceView.this.d4shHomeRecordAdapter.getDateDataList().size()) {
                            i5 = 0;
                            break;
                        } else {
                            if (D4shHomeDeviceView.this.d4shHomeRecordAdapter.getDateDataList().get(i6).getType() == 2) {
                                i5 = i6 + 1;
                                break;
                            }
                            i6++;
                        }
                    }
                    if (i5 == 0) {
                        return;
                    }
                    View viewFindViewByPosition = D4shHomeDeviceView.this.rvD4RecordView.getLayoutManager().findViewByPosition(i5 - 1);
                    D4shHomeDeviceView.this.d4sViewLayout.setScrollParams(i5, D4shHomeDeviceView.this.d4sViewLayout.getScrollY(), D4shHomeDeviceView.this.d4shDeviceRecordTagViewGroupTopFixed.getHeight(), D4shHomeDeviceView.this.bleMenuView.getTop() - D4shHomeDeviceView.this.contentView.getTop(), D4shHomeDeviceView.this.d4shRecord.getDeviceShared() != null);
                    if (viewFindViewByPosition == null || ((((viewFindViewByPosition.getTop() + viewFindViewByPosition.getHeight()) + D4shHomeDeviceView.this.rvD4RecordView.getTop()) + ((RelativeLayout) D4shHomeDeviceView.this.rvD4RecordView.getParent()).getTop()) - D4shHomeDeviceView.this.d4sViewLayout.getScrollY()) - D4shHomeDeviceView.this.rvD4RecordView.computeVerticalScrollOffset() >= D4shHomeDeviceView.this.bleMenuView.getTop() - D4shHomeDeviceView.this.contentView.getTop() || DataHelper.getBooleanSF(D4shHomeDeviceView.this.mContext, Consts.D4S_EAT_RECORD_IS_FIRST) || D4shHomeDeviceView.this.d4shRecord == null || D4shHomeDeviceView.this.d4shRecord.getDeviceShared() != null || D4shHomeDeviceView.this.isShowGuide) {
                        return;
                    }
                    D4shHomeDeviceView.this.d4sViewLayout.fling(0);
                    D4shHomeDeviceView.this.rvD4RecordView.stopScroll();
                    D4shHomeDeviceView.this.d4sViewLayout.setOnTouchListener(new View.OnTouchListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView.22.2
                        @Override // android.view.View.OnTouchListener
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            return true;
                        }
                    });
                    D4shHomeDeviceView.this.isShowGuide = true;
                    D4shHomeDeviceView.this.showGuideView(viewFindViewByPosition);
                }
            }

            @Override // com.petkit.android.activities.petkitBleDevice.widget.PetkitScrollViewWithListener.ScrollviewOnTouchListener
            public void finishScroll(boolean z) {
                Log.d("scrolling", z + ",isLandscape:" + D4shHomeDeviceView.this.isLandscape());
                if (D4shHomeDeviceView.this.isLandscape()) {
                    return;
                }
                if (!D4shHomeDeviceView.this.startExpandAnim && D4shHomeDeviceView.this.isHidePromote && z) {
                    D4shHomeDeviceView.this.endPromoteViewAnim();
                }
                D4shHomeDeviceView.this.setD4shDeviceRecordTagViewGroupTopFixedVisible();
                D4shHomeDeviceView.this.setD4shDeviceRecordAdapterTopVideoItemInScreen();
                if (D4shHomeDeviceView.this.isAniStart || D4shHomeDeviceView.this.bleMenuView.getVisibility() == 4) {
                    ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(D4shHomeDeviceView.this.bleMenuView, Key.TRANSLATION_Y, D4shHomeDeviceView.this.bleMenuView.getMeasuredHeight(), 0.0f);
                    objectAnimatorOfFloat.setDuration(400L);
                    objectAnimatorOfFloat.addListener(new Animator.AnimatorListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView.22.3
                        @Override // android.animation.Animator.AnimatorListener
                        public void onAnimationCancel(Animator animator) {
                        }

                        @Override // android.animation.Animator.AnimatorListener
                        public void onAnimationRepeat(Animator animator) {
                        }

                        @Override // android.animation.Animator.AnimatorListener
                        public void onAnimationStart(Animator animator) {
                            if (D4shHomeDeviceView.this.isLandscape()) {
                                return;
                            }
                            D4shHomeDeviceView.this.bleMenuView.setVisibility(0);
                        }

                        @Override // android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator animator) {
                            D4shHomeDeviceView.this.isAniStart = false;
                        }
                    });
                    if (D4shHomeDeviceView.this.isLandscape()) {
                        objectAnimatorOfFloat.start();
                    } else if (z) {
                        objectAnimatorOfFloat.start();
                    }
                }
            }
        });
        this.bleMenuView.setAiListener(new AiClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView.23
            @Override // com.petkit.android.activities.petkitBleDevice.listener.AiClickListener
            public void AiAdd() {
                if (D4shHomeDeviceView.this.d4shRecord == null) {
                    return;
                }
                int i = D4shHomeDeviceView.this.d4shRecord.getTypeCode() == 0 ? 25 : 26;
                AiDataUtil.getInstance().saveClickedTask(D4shHomeDeviceView.this.activity, D4shHomeDeviceView.this.aiNewInfo.getInfo().getId(), D4shHomeDeviceView.this.d4shRecord.getDeviceId(), i);
                D4shHomeDeviceView.this.activity.startActivity(AiActivity.newIntent(D4shHomeDeviceView.this.activity, 0, i, D4shHomeDeviceView.this.d4shRecord.getDeviceId(), ""));
            }

            @Override // com.petkit.android.activities.petkitBleDevice.listener.AiClickListener
            public void AiPet() {
                D4shHomeDeviceView.this.activity.startActivity(PetWeightActivity.newIntent(D4shHomeDeviceView.this.activity, D4shHomeDeviceView.this.d4shRecord.getDeviceId(), D4shHomeDeviceView.this.d4shRecord.getTypeCode() == 0 ? 25 : 26));
            }

            @Override // com.petkit.android.activities.petkitBleDevice.listener.AiClickListener
            public void surplusGrainControl() {
                if (D4shHomeDeviceView.this.d4shRecord == null) {
                    return;
                }
                D4shHomeDeviceView.this.activity.startActivity(ControlFoodActivity.newIntent(D4shHomeDeviceView.this.activity, D4shHomeDeviceView.this.d4shRecord.getDeviceId(), D4shHomeDeviceView.this.d4shRecord.getTypeCode(), D4shHomeDeviceView.this.relatedProductsInfor));
            }
        });
        this.activity.getWindow().getDecorView().setSystemUiVisibility(0);
    }

    public void hideCatFacePop() {
        D4shAbstractHomeRecordAdapter d4shAbstractHomeRecordAdapter = this.d4shHomeRecordAdapter;
        if (d4shAbstractHomeRecordAdapter == null || !d4shAbstractHomeRecordAdapter.isShowingCatPop()) {
            return;
        }
        this.handler.sendEmptyMessageDelayed(9, 150L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideAdapterCatFace() {
        D4shAbstractHomeRecordAdapter d4shAbstractHomeRecordAdapter = this.d4shHomeRecordAdapter;
        if (d4shAbstractHomeRecordAdapter != null) {
            d4shAbstractHomeRecordAdapter.setHideCatPop(true);
        }
    }

    private void initScrollView() {
        this.headerLoadingView = (ImageView) findViewById(R.id.header_loading_view);
        final SpringAnimation springAnimation = new SpringAnimation(this.d4sViewLayout, DynamicAnimation.TRANSLATION_Y, 0.0f);
        springAnimation.getSpring().setStiffness(800.0f);
        springAnimation.getSpring().setDampingRatio(0.5f);
        this.d4sViewLayout.setOnTouchListener(new View.OnTouchListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView.24
            /* JADX WARN: Removed duplicated region for block: B:54:0x01a2  */
            @Override // android.view.View.OnTouchListener
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public boolean onTouch(android.view.View r6, android.view.MotionEvent r7) {
                /*
                    Method dump skipped, instruction units count: 524
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView.AnonymousClass24.onTouch(android.view.View, android.view.MotionEvent):boolean");
            }
        });
    }

    public void refreshPetChart(D4shDailyFeeds.D4shDailyEat d4shDailyEat) {
        if (d4shDailyEat == null) {
            return;
        }
        this.d4shDailyEat = d4shDailyEat;
        if (this.selectedPetIds.contains(ColorUtils.ALL_PET)) {
            updateStatisticList(d4shDailyEat.getItems());
        } else {
            ArrayList arrayList = new ArrayList();
            for (D4shDailyFeeds.D4shDailyEat.ItemsBean itemsBean : d4shDailyEat.getItems()) {
                if (hasSelectPet(itemsBean.getPetId())) {
                    arrayList.add(itemsBean);
                }
            }
            updateStatisticList(arrayList);
        }
        if (this.isNotFirstLoad) {
            return;
        }
        this.llDay.setVisibility(8);
        this.isNotFirstLoad = true;
    }

    public void updateStatisticList(List<D4shDailyFeeds.D4shDailyEat.ItemsBean> list) {
        if (list.size() > 0) {
            getChartData(this.chartView, list);
            this.tvEatTime.setText(String.valueOf(list.size()));
        } else {
            this.chartView.removeAllViews();
            this.tvEatTime.setText("0");
        }
    }

    @SuppressLint({"ClickableViewAccessibility"})
    private void getChartData(RelativeLayout relativeLayout, List<D4shDailyFeeds.D4shDailyEat.ItemsBean> list) {
        String petName;
        if (relativeLayout == null) {
            return;
        }
        relativeLayout.removeAllViews();
        int yUi = setYUi(getMaxTime(list));
        int measuredWidth = relativeLayout.getMeasuredWidth();
        int measuredHeight = relativeLayout.getMeasuredHeight() - AppUtils.dp2px(this.mContext, 18.0f);
        final ArrayList arrayList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            D4shDailyFeeds.D4shDailyEat.ItemsBean itemsBean = list.get(i);
            RelativeLayout relativeLayout2 = (RelativeLayout) LayoutInflater.from(this.mContext).inflate(R.layout.chart_view_data, (ViewGroup) null);
            ImageView imageView = (ImageView) relativeLayout2.getChildAt(1);
            Pet petById = UserInforUtils.getPetById(itemsBean.getPetId());
            if (petById != null) {
                petName = petById.getName();
            } else {
                petName = itemsBean.getPetName();
            }
            imageView.setImageResource(getWaterRes(itemsBean.getPetId(), petName));
            RelativeLayout relativeLayout3 = (RelativeLayout) relativeLayout2.getChildAt(0);
            ((TextView) relativeLayout3.getChildAt(0)).setBackgroundColor(Color.parseColor(getRecColor(itemsBean.getPetId(), petName)));
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) relativeLayout3.getLayoutParams();
            layoutParams.height = (int) ((((itemsBean.getEndTime() - itemsBean.getStartTime()) * 1.0f) / yUi) * measuredHeight);
            relativeLayout3.setLayoutParams(layoutParams);
            relativeLayout.addView(relativeLayout2);
            final RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) relativeLayout2.getLayoutParams();
            layoutParams2.leftMargin = Math.max(T6Utils.getEventTimeOffset(itemsBean.getEatStartTime(), measuredWidth - AppUtils.dp2px(this.mContext, 16.0f)), 0);
            layoutParams2.addRule(12);
            relativeLayout2.setLayoutParams(layoutParams2);
            final D4shChartInfo d4shChartInfo = new D4shChartInfo();
            d4shChartInfo.setInfo(itemsBean);
            d4shChartInfo.setClickView(relativeLayout2);
            arrayList.add(d4shChartInfo);
            relativeLayout2.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView$$ExternalSyntheticLambda4
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$getChartData$2(d4shChartInfo, arrayList, layoutParams2, view);
                }
            });
            relativeLayout2.setOnTouchListener(new View.OnTouchListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView$$ExternalSyntheticLambda5
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    return this.f$0.lambda$getChartData$3(d4shChartInfo, arrayList, layoutParams2, view, motionEvent);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getChartData$2(D4shChartInfo d4shChartInfo, List list, RelativeLayout.LayoutParams layoutParams, View view) {
        int top = ((((view.getTop() + this.rlChart.getTop()) + this.llDay.getTop()) - this.d4sViewLayout.getScrollY()) + this.llTopView.getMeasuredHeight()) - Math.abs(this.appBarOffset);
        Log.d("getChartData", "getChartData:totalH: " + top);
        this.sortEventList = getSortList(d4shChartInfo, list);
        showArrowPop(layoutParams, top, d4shChartInfo);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$getChartData$3(D4shChartInfo d4shChartInfo, List list, RelativeLayout.LayoutParams layoutParams, View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() != 0) {
            return false;
        }
        int top = ((((view.getTop() + this.rlChart.getTop()) + this.llDay.getTop()) - this.d4sViewLayout.getScrollY()) + this.llTopView.getMeasuredHeight()) - Math.abs(this.appBarOffset);
        this.sortEventList = getSortList(d4shChartInfo, list);
        showArrowPop(layoutParams, top, d4shChartInfo);
        return true;
    }

    private List<D4shChartInfo> getSortList(D4shChartInfo d4shChartInfo, List<D4shChartInfo> list) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(d4shChartInfo);
        this.popCount = 0;
        for (int size = list.size() - 1; size >= 0; size--) {
            if (list.get(size).getInfo().getStartTime() < d4shChartInfo.getInfo().getStartTime()) {
                arrayList.add(0, list.get(size));
                this.popCount++;
            } else if (list.get(size).getInfo().getStartTime() > d4shChartInfo.getInfo().getStartTime()) {
                if (this.popCount < arrayList.size() - 1) {
                    arrayList.add(this.popCount + 1, list.get(size));
                } else {
                    arrayList.add(list.get(size));
                }
            }
        }
        return arrayList;
    }

    private void showArrowPop(final RelativeLayout.LayoutParams layoutParams, final int i, D4shChartInfo d4shChartInfo) {
        setPopEventInfo(d4shChartInfo.getInfo());
        startPopAnim();
        this.rlPopContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView.25
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                D4shHomeDeviceView.this.rlPopContent.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                D4shHomeDeviceView.this.rlPopContent.measure(0, 0);
                D4shHomeDeviceView d4shHomeDeviceView = D4shHomeDeviceView.this;
                d4shHomeDeviceView.rlPopWidth = d4shHomeDeviceView.rlPopContent.getMeasuredWidth();
                D4shHomeDeviceView d4shHomeDeviceView2 = D4shHomeDeviceView.this;
                d4shHomeDeviceView2.rlPopHeight = d4shHomeDeviceView2.rlPopContent.getMeasuredHeight();
                RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) D4shHomeDeviceView.this.ivArrow.getLayoutParams();
                layoutParams2.leftMargin = layoutParams.leftMargin + AppUtils.dp2px(D4shHomeDeviceView.this.mContext, 68.0f);
                layoutParams2.topMargin = (i - AppUtils.dp2px(D4shHomeDeviceView.this.mContext, 2.0f)) + 1;
                D4shHomeDeviceView.this.ivArrow.setLayoutParams(layoutParams2);
                RelativeLayout.LayoutParams layoutParams3 = (RelativeLayout.LayoutParams) D4shHomeDeviceView.this.rlPopContent.getLayoutParams();
                int widthPixels = layoutParams.leftMargin;
                if (AppUtils.dp2px(D4shHomeDeviceView.this.mContext, 68.0f) + widthPixels < D4shHomeDeviceView.this.rlPopWidth / 2) {
                    widthPixels = AppUtils.dp2px(D4shHomeDeviceView.this.mContext, 18.0f);
                } else if (D4shHomeDeviceView.this.rlPopWidth + widthPixels > CommonUtil.getWidthPixels(D4shHomeDeviceView.this.mContext) - AppUtils.dp2px(D4shHomeDeviceView.this.mContext, 18.0f)) {
                    widthPixels = (CommonUtil.getWidthPixels(D4shHomeDeviceView.this.mContext) - AppUtils.dp2px(D4shHomeDeviceView.this.mContext, 18.0f)) - D4shHomeDeviceView.this.rlPopWidth;
                }
                layoutParams3.leftMargin = widthPixels;
                layoutParams3.topMargin = (i - D4shHomeDeviceView.this.rlPopHeight) + AppUtils.dp2px(D4shHomeDeviceView.this.mContext, 6.0f);
                D4shHomeDeviceView.this.rlPopContent.setLayoutParams(layoutParams3);
            }
        });
    }

    private void setPopEventInfo(final D4shDailyFeeds.D4shDailyEat.ItemsBean itemsBean) {
        String name;
        this.tvPopPetName.setText(getPetName(itemsBean.getPetId(), itemsBean.getPetName()) + "：" + getEventDurationStr(itemsBean.getEndTime() - itemsBean.getStartTime()));
        TextView textView = this.tvPopTime;
        TimeUtils timeUtils = TimeUtils.getInstance();
        Context context = this.mContext;
        long eatStartTime = itemsBean.getEatStartTime();
        D4shRecord d4shRecord = this.d4shRecord;
        textView.setText(timeUtils.timeStampToTimeStringWithUnit(context, eatStartTime, d4shRecord == null ? null : d4shRecord.getActualTimeZone()));
        Pet petById = UserInforUtils.getPetById(itemsBean.getPetId());
        if (petById == null) {
            name = "";
        } else {
            name = petById.getName();
        }
        this.sdPet.setBgColor(getRecColor(itemsBean.getPetId(), name));
        if (this.popCount == 0) {
            this.ivLastEvent.setImageResource(R.drawable.icon_t6_last_event2);
        } else {
            this.ivLastEvent.setImageResource(R.drawable.icon_t6_last_event);
        }
        if (this.popCount < this.sortEventList.size() - 1) {
            this.ivNextEvent.setImageResource(R.drawable.icon_t6_next_event);
        } else {
            this.ivNextEvent.setImageResource(R.drawable.icon_t6_next_event2);
        }
        if (System.currentTimeMillis() / 1000 > itemsBean.getExpire()) {
            this.rlPopImage.setVisibility(8);
            return;
        }
        if (TextUtils.isEmpty(itemsBean.getPreview())) {
            this.rlPopImage.setVisibility(8);
            return;
        }
        this.rlPopImage.setVisibility(0);
        this.rlPopImage.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView$$ExternalSyntheticLambda10
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$setPopEventInfo$4(itemsBean, view);
            }
        });
        if (isExpireService(itemsBean.getEatStartTime())) {
            this.ivChartPlay.setVisibility(0);
        } else {
            this.ivChartPlay.setVisibility(8);
        }
        new ImageCacheSimple(CommonUtils.getAppContext()).getImageCachePath(CommonUtil.httpToHttps(itemsBean.getPreview()), new ImageCacheSimple.IGetImageCacheListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView$$ExternalSyntheticLambda11
            @Override // com.jess.arms.widget.imageloader.ImageCacheSimple.IGetImageCacheListener
            public final void onCachePath(String str) {
                this.f$0.lambda$setPopEventInfo$5(itemsBean, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setPopEventInfo$4(D4shDailyFeeds.D4shDailyEat.ItemsBean itemsBean, View view) {
        this.d4shHomeDeviceViewListener.jumpToEventDetail(itemsBean);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setPopEventInfo$5(D4shDailyFeeds.D4shDailyEat.ItemsBean itemsBean, String str) {
        if (str != null) {
            try {
                File fileDecryptImageFile = ImageUtils.decryptImageFile(new File(str), itemsBean.getAesKey());
                if (fileDecryptImageFile != null) {
                    this.ivPopImage.setImageBitmap(CommonUtil.bimapSquareRound(60, BitmapFactory.decodeFile(fileDecryptImageFile.getAbsolutePath())));
                } else {
                    this.ivPopImage.setImageResource(R.drawable.default_image);
                }
                return;
            } catch (Exception unused) {
                this.ivPopImage.setImageResource(R.drawable.default_image);
                return;
            }
        }
        this.ivPopImage.setImageResource(R.drawable.default_image);
    }

    private boolean isExpireService(long j) {
        boolean z = false;
        boolean z2 = this.d4shRecord.getCloudProduct() == null || (this.d4shRecord.getCloudProduct().getWorkIndate() != null && j > Long.parseLong(this.d4shRecord.getCloudProduct().getWorkIndate()));
        if (this.d4shRecord.getServiceStatus() == 0 || (this.d4shRecord.getServiceStatus() == 2 && z2)) {
            z = true;
        }
        return !z;
    }

    private String getPetName(String str, String str2) {
        Pet petById = UserInforUtils.getPetById(str);
        String name = petById != null ? petById.getName() : null;
        return (name == null || name.equals("")) ? TextUtils.isEmpty(str2) ? getResources().getString(R.string.Pet) : str2 : name;
    }

    private String getEventDurationStr(int i) {
        StringBuilder sb;
        String str;
        if (i <= 0) {
            return "";
        }
        int i2 = i / 60;
        int i3 = i % 60;
        StringBuilder sb2 = new StringBuilder();
        if (i2 > 9) {
            sb = new StringBuilder();
            sb.append(i2);
            sb.append("");
        } else {
            sb = new StringBuilder();
            sb.append("0");
            sb.append(i2);
        }
        sb2.append(sb.toString());
        sb2.append("′");
        if (i3 > 9) {
            str = i3 + "";
        } else {
            str = "0" + i3;
        }
        sb2.append(str);
        sb2.append("″");
        return sb2.toString();
    }

    private void startPopAnim() {
        if (this.rlPop.getVisibility() == 0) {
            this.rlPopContent.requestLayout();
            return;
        }
        this.rlPop.setVisibility(0);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(300L);
        alphaAnimation.setInterpolator(new AccelerateInterpolator());
        this.rlPop.setAnimation(alphaAnimation);
        alphaAnimation.start();
    }

    private int getMaxTime(List<D4shDailyFeeds.D4shDailyEat.ItemsBean> list) {
        int endTime = 0;
        if (list.size() < 1) {
            return 0;
        }
        for (D4shDailyFeeds.D4shDailyEat.ItemsBean itemsBean : list) {
            if (itemsBean.getEndTime() - itemsBean.getStartTime() > endTime) {
                endTime = itemsBean.getEndTime() - itemsBean.getStartTime();
            }
        }
        return endTime;
    }

    private int setYUi(int i) {
        int i2 = 0;
        if (i == 0) {
            return 0;
        }
        int i3 = 4;
        while (i3 > 3) {
            i2 += 10;
            i3 = i % i2 == 0 ? i / i2 : (i / i2) + 1;
        }
        this.tvY1.setText(getTimeUnit(i2));
        this.tvY2.setText(getTimeUnit(i2 * 2));
        int i4 = i2 * 3;
        this.tvY3.setText(getTimeUnit(i4));
        return i4;
    }

    private String getTimeUnit(int i) {
        StringBuilder sb;
        StringBuilder sb2;
        int i2 = i / 60;
        int i3 = i % 60;
        StringBuilder sb3 = new StringBuilder();
        if (i2 < 10) {
            sb = new StringBuilder();
            sb.append("0");
            sb.append(i2);
        } else {
            sb = new StringBuilder();
            sb.append(i2);
            sb.append("");
        }
        sb3.append(sb.toString());
        sb3.append("′");
        if (i3 < 10) {
            sb2 = new StringBuilder();
            sb2.append("0");
            sb2.append(i3);
        } else {
            sb2 = new StringBuilder();
            sb2.append(i3);
            sb2.append("");
        }
        sb3.append(sb2.toString());
        sb3.append("”");
        return sb3.toString();
    }

    private boolean hasSelectPet(String str) {
        for (String str2 : this.selectedPetIds) {
            if (str2.equals(ColorUtils.UNKNOWN_PET)) {
                if (CommonUtil.getInt(str) < 1) {
                    return true;
                }
            } else if (str2.equals(str)) {
                return true;
            }
        }
        return false;
    }

    private int getWaterRes(String str, String str2) {
        return ColorUtils.getPetWaterColorById(str, str2);
    }

    private String getRecColor(String str, String str2) {
        return ColorUtils.getPetColorById(str, str2);
    }

    public void onRefreshStart(int i) {
        PetkitLog.d("onRefreshStart", "dy:" + i);
        ViewGroup.LayoutParams layoutParams = this.headerLoadingView.getLayoutParams();
        layoutParams.height = Math.min(layoutParams.height + (i / 2), ArmsUtils.dip2px(getContext(), 50.0f));
        this.headerLoadingView.setLayoutParams(layoutParams);
    }

    public void onRefreshFinish() {
        ViewGroup.LayoutParams layoutParams = this.headerLoadingView.getLayoutParams();
        if (layoutParams.height == ArmsUtils.dip2px(getContext(), 50.0f)) {
            this.headerLoadingView.setImageResource(R.drawable.loadding_anim);
            D4shHomeDeviceViewListener d4shHomeDeviceViewListener = this.d4shHomeDeviceViewListener;
            if (d4shHomeDeviceViewListener != null) {
                d4shHomeDeviceViewListener.refreshHome();
                return;
            }
            return;
        }
        startLoadingAnimation(layoutParams.height);
    }

    public void startLoad() {
        this.headerLoadingView.setImageResource(R.drawable.loadding_anim);
        ((AnimationDrawable) this.headerLoadingView.getDrawable()).start();
    }

    public void stopLoad() {
        ((AnimationDrawable) this.headerLoadingView.getDrawable()).stop();
        ViewGroup.LayoutParams layoutParams = this.headerLoadingView.getLayoutParams();
        layoutParams.height = 0;
        this.headerLoadingView.setLayoutParams(layoutParams);
    }

    private void startLoadingAnimation(final int i) {
        ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(100);
        this.animator = valueAnimatorOfInt;
        valueAnimatorOfInt.setDuration(400L);
        this.animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView.26
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int iIntValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                ViewGroup.LayoutParams layoutParams = D4shHomeDeviceView.this.headerLoadingView.getLayoutParams();
                int i2 = i;
                layoutParams.height = i2 - ((iIntValue * i2) / 100);
                D4shHomeDeviceView.this.headerLoadingView.setLayoutParams(layoutParams);
            }
        });
        this.animator.addListener(new Animator.AnimatorListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView.27
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
            }
        });
        this.animator.start();
    }

    private void initPlayer() {
        this.live = (RelativeLayout) View.inflate(this.mContext, R.layout.layout_d4sh_live_center_view, null);
        this.ivBackground = (ImageView) findViewById(R.id.iv_background);
        this.liveIndicator = (CirclePageIndicator) findViewById(R.id.live_indicator);
        this.llTopView = (LinearLayout) findViewById(R.id.top_view);
        this.vpLive = (InterceptViewPager) findViewById(R.id.vp_live_panel);
        this.ivLeftEar = (ImageView) this.live.findViewById(R.id.iv_left_ear);
        this.ivRightEar = (ImageView) this.live.findViewById(R.id.iv_right_ear);
        this.ivLeftEarClickView = (ImageView) this.live.findViewById(R.id.iv_left_ear_click_view);
        this.ivRightEarClickView = (ImageView) this.live.findViewById(R.id.iv_right_ear_click_view);
        this.ivLeftEarClickView.setOnClickListener(this);
        this.ivRightEarClickView.setOnClickListener(this);
        this.rlMask = (RelativeLayout) this.live.findViewById(R.id.rl_mask);
        this.player = (D4shMultipleVideoPlayer) this.live.findViewById(R.id.d4sh_player);
        int i = getResources().getDisplayMetrics().widthPixels;
        this.screenWidth = i;
        this.player.setScreenWidth(i);
        this.player.setPlayerListener(this);
        this.player.post(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$initPlayer$6();
            }
        });
        this.rlMask.post(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$initPlayer$7();
            }
        });
        PetkitFeederDeviceRecordVideoPlayerInstance.getInstance().initPlayer(this.activity);
        D4shPlayerPortraitView d4shPlayerPortraitView = new D4shPlayerPortraitView(this.mContext);
        this.playerPortraitView = d4shPlayerPortraitView;
        d4shPlayerPortraitView.setViewClickListener(this);
        this.player.addPortraitView(this.playerPortraitView);
        D4shHomePlayerLandscapeView d4shHomePlayerLandscapeView = new D4shHomePlayerLandscapeView(this.mContext);
        this.playerLandscapeView = d4shHomePlayerLandscapeView;
        d4shHomePlayerLandscapeView.setViewClickListener(this);
        this.player.addLandscapeView(this.playerLandscapeView);
        D4shPlayerSoundWaveView d4shPlayerSoundWaveView = (D4shPlayerSoundWaveView) findViewById(R.id.d4sh_home_player_sound_wave_view);
        this.playerSoundWaveView = d4shPlayerSoundWaveView;
        d4shPlayerSoundWaveView.setVisibility(4);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.playerSoundWaveView.getLayoutParams();
        layoutParams.width = (int) (BaseApplication.displayMetrics.widthPixels * 0.3f);
        this.playerSoundWaveView.setLayoutParams(layoutParams);
        this.playerContainer = (RelativeLayout) this.live.findViewById(R.id.d4sh_player_container);
        this.rl_live_panel = (RelativeLayout) this.live.findViewById(R.id.rl_live_panel);
        D4shLiveViewPagerAdapter d4shLiveViewPagerAdapter = new D4shLiveViewPagerAdapter(this.mContext, this.live);
        this.d4shLiveViewPagerAdapter = d4shLiveViewPagerAdapter;
        d4shLiveViewPagerAdapter.setOnClickListener(new D4shLiveViewPagerAdapter.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView.28
            @Override // com.petkit.android.activities.petkitBleDevice.d4sh.adapter.D4shLiveViewPagerAdapter.OnClickListener
            public void onFilterClick() {
                if (D4shHomeDeviceView.this.menuOnClickListener != null) {
                    D4shHomeDeviceView.this.menuOnClickListener.onBottomMenuClick(MenuType.CONSUMABLE_DESICCANT_STATE);
                }
            }

            @Override // com.petkit.android.activities.petkitBleDevice.d4sh.adapter.D4shLiveViewPagerAdapter.OnClickListener
            public void onFoodOneClick() {
                D4shHomeDeviceView.this.showFoodWarnDialog();
            }

            @Override // com.petkit.android.activities.petkitBleDevice.d4sh.adapter.D4shLiveViewPagerAdapter.OnClickListener
            public void onFoodTwoClick() {
                D4shHomeDeviceView.this.showFoodWarnDialog();
            }
        });
        this.vpLive.setAdapter(this.d4shLiveViewPagerAdapter);
        this.vpLive.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView.29
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i2) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i2, float f, int i3) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int i2) {
                if (i2 == 1) {
                    D4shHomeDeviceView.this.d4shLiveViewPagerAdapter.setCurrentPosition(i2);
                    D4shHomeDeviceView.this.d4shLiveViewPagerAdapter.refreshData();
                }
                CommonUtils.addSysIntMap(com.petkit.android.utils.Constants.D4SH_HOME_PAGE_SELECT + D4shHomeDeviceView.this.d4shRecord.getDeviceId(), i2);
            }
        });
        this.vpLive.setCurrentItem(0);
        this.liveIndicator.setViewPager(this.vpLive, 0, 2);
        this.liveIndicator.setPageColor(CommonUtils.getColorById(R.color.color_D2C5BC));
        this.liveIndicator.setFillColor(CommonUtils.getColorById(R.color.light_black));
        this.liveIndicator.setSnap(true);
        this.liveIndicator.setIndicatorStyle(3);
        this.liveIndicator.setRadius(ArmsUtils.dip2px(this.mContext, 9.0f));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initPlayer$6() {
        int iRound = Math.round((this.player.getWidth() * 10.0f) / 16.0f);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.player.getLayoutParams();
        layoutParams.height = iRound;
        this.player.setLayoutParams(layoutParams);
        this.player.initTextureViewSize();
        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) this.vpLive.getLayoutParams();
        layoutParams2.height = ArmsUtils.dip2px(this.mContext, 56.0f) + iRound;
        this.verticalLiveViewHeight = ArmsUtils.dip2px(this.mContext, 56.0f) + iRound;
        this.vpLive.setLayoutParams(layoutParams2);
        RelativeLayout.LayoutParams layoutParams3 = (RelativeLayout.LayoutParams) this.ivLeftEar.getLayoutParams();
        int width = (int) ((this.player.getWidth() / 343.0f) * 100.0f);
        layoutParams3.width = width;
        layoutParams3.height = (int) ((width / 100.0f) * 120.0f);
        int iDip2px = ArmsUtils.dip2px(this.mContext, 50.0f) + iRound;
        int i = (int) ((iRound / 214.0f) * 139.0f);
        int i2 = iDip2px - i;
        if (i2 > layoutParams3.height) {
            layoutParams3.addRule(12);
            layoutParams3.setMargins(layoutParams3.leftMargin, layoutParams3.topMargin, layoutParams3.rightMargin, ArmsUtils.dip2px(this.mContext, 0.5f) + i);
        } else {
            layoutParams3.addRule(10);
            layoutParams3.setMargins(layoutParams3.leftMargin, (i2 - layoutParams3.height) - ArmsUtils.dip2px(this.mContext, 4.0f), layoutParams3.rightMargin, layoutParams3.bottomMargin);
        }
        this.ivLeftEar.setLayoutParams(layoutParams3);
        RelativeLayout.LayoutParams layoutParams4 = (RelativeLayout.LayoutParams) this.ivLeftEarClickView.getLayoutParams();
        int width2 = (int) ((this.player.getWidth() / 343.0f) * 100.0f);
        layoutParams4.width = width2;
        int iDip2px2 = ((int) ((width2 / 100.0f) * 120.0f)) - ArmsUtils.dip2px(this.mContext, 50.0f);
        layoutParams4.height = iDip2px2;
        if (i2 > iDip2px2 + ArmsUtils.dip2px(this.mContext, 50.0f)) {
            layoutParams4.addRule(12);
            layoutParams4.setMargins(layoutParams4.leftMargin, layoutParams4.topMargin, layoutParams4.rightMargin, ArmsUtils.dip2px(this.mContext, 0.5f) + i);
        } else {
            layoutParams4.addRule(10);
            layoutParams4.setMargins(layoutParams4.leftMargin, (i2 - layoutParams4.height) - ArmsUtils.dip2px(this.mContext, 4.0f), layoutParams4.rightMargin, layoutParams4.bottomMargin);
        }
        this.ivLeftEarClickView.setLayoutParams(layoutParams4);
        RelativeLayout.LayoutParams layoutParams5 = (RelativeLayout.LayoutParams) this.ivRightEar.getLayoutParams();
        int width3 = (int) ((this.player.getWidth() / 343.0f) * 100.0f);
        layoutParams5.width = width3;
        int i3 = (int) ((width3 / 100.0f) * 120.0f);
        layoutParams5.height = i3;
        if (i2 > i3) {
            layoutParams5.addRule(12);
            layoutParams5.setMargins(layoutParams5.leftMargin, layoutParams5.topMargin, layoutParams5.rightMargin, ArmsUtils.dip2px(this.mContext, 0.5f) + i);
        } else {
            layoutParams5.addRule(10);
            layoutParams5.setMargins(layoutParams5.leftMargin, (i2 - layoutParams5.height) - ArmsUtils.dip2px(this.mContext, 4.0f), layoutParams5.rightMargin, layoutParams5.bottomMargin);
        }
        this.ivRightEar.setLayoutParams(layoutParams5);
        RelativeLayout.LayoutParams layoutParams6 = (RelativeLayout.LayoutParams) this.ivRightEarClickView.getLayoutParams();
        int width4 = (int) ((this.player.getWidth() / 343.0f) * 100.0f);
        layoutParams6.width = width4;
        int iDip2px3 = ((int) ((width4 / 100.0f) * 120.0f)) - ArmsUtils.dip2px(this.mContext, 50.0f);
        layoutParams6.height = iDip2px3;
        if (i2 > iDip2px3 + ArmsUtils.dip2px(this.mContext, 50.0f)) {
            layoutParams6.addRule(12);
            layoutParams6.setMargins(layoutParams6.leftMargin, layoutParams6.topMargin, layoutParams6.rightMargin, i + ArmsUtils.dip2px(this.mContext, 0.5f));
        } else {
            layoutParams6.addRule(10);
            layoutParams6.setMargins(layoutParams6.leftMargin, (i2 - layoutParams6.height) - ArmsUtils.dip2px(this.mContext, 4.0f), layoutParams6.rightMargin, layoutParams6.bottomMargin);
        }
        this.ivRightEarClickView.setLayoutParams(layoutParams6);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initPlayer$7() {
        int iRound = Math.round((this.rlMask.getWidth() * 10.0f) / 16.0f);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.rlMask.getLayoutParams();
        layoutParams.height = iRound + ArmsUtils.dip2px(this.mContext, 50.0f);
        this.rlMask.setLayoutParams(layoutParams);
    }

    private void initDeviceWarnMessageViewGroup() {
        D4shDeviceWarnMessageViewGroup d4shDeviceWarnMessageViewGroup = (D4shDeviceWarnMessageViewGroup) findViewById(R.id.d4sh_device_warn_message_view_group);
        this.d4shDeviceWarnMessageViewGroup = d4shDeviceWarnMessageViewGroup;
        d4shDeviceWarnMessageViewGroup.setWarnMessageViewGroupClickListener(this, this);
    }

    private void initDeviceDataCardViewGroup() {
        D4shDeviceDataCardViewGroup d4shDeviceDataCardViewGroup = (D4shDeviceDataCardViewGroup) findViewById(R.id.d4sh_device_data_card_view_group);
        this.d4shDeviceDataCardViewGroup = d4shDeviceDataCardViewGroup;
        d4shDeviceDataCardViewGroup.setFeedDeviceDataCardViewClickListener(this);
        this.d4shDeviceDataCardViewGroup.setEatDeviceDataCardViewClickListener(this);
        this.d4shDeviceDataCardViewGroup.setDesiccantDeviceDataCardViewClickListener(this);
    }

    private void initDeviceRecordTagViewGroup() {
        D4shDeviceRecordTagViewGroup d4shDeviceRecordTagViewGroup = (D4shDeviceRecordTagViewGroup) findViewById(R.id.d4sh_device_record_tag_view_group);
        this.d4shDeviceRecordTagViewGroup = d4shDeviceRecordTagViewGroup;
        d4shDeviceRecordTagViewGroup.setD4shDeviceRecordTagViewGroupClickListener(this, this);
        D4shDeviceRecordTagViewGroup d4shDeviceRecordTagViewGroup2 = (D4shDeviceRecordTagViewGroup) findViewById(R.id.d4sh_device_record_tag_view_group_top_fixed);
        this.d4shDeviceRecordTagViewGroupTopFixed = d4shDeviceRecordTagViewGroup2;
        d4shDeviceRecordTagViewGroup2.setCalendarImageViewVisibility(0);
        this.d4shDeviceRecordTagViewGroupTopFixed.setDateTitleViewVisibility(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initDeviceRecordTagViewGroup$8(view);
            }
        }, new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initDeviceRecordTagViewGroup$9(view);
            }
        });
        this.d4shDeviceRecordTagViewGroupTopFixed.setD4shDeviceRecordTagViewGroupClickListener(this, this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initDeviceRecordTagViewGroup$8(View view) {
        ((BaseActivity) this.activity).killMyself();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initDeviceRecordTagViewGroup$9(View view) {
        Context context = this.mContext;
        context.startActivity(D4shSettingActivity.newIntent(context, this.d4shRecord.getDeviceId(), this.d4shRecord.getTypeCode(), this.relatedProductsInfor));
    }

    private void initToolbarView() {
        this.imgD4Setting = (ImageView) findViewById(R.id.img_d4sh_setting);
        this.toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        this.toolbarBack = (ImageView) findViewById(R.id.toolbar_d4sh_back);
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        View viewFindViewById = findViewById(R.id.toolbar_background);
        this.toolbarBackground = viewFindViewById;
        viewFindViewById.setAlpha(0.0f);
    }

    public void refreshEarState(File file, File file2, File file3, AttireListResult attireListResult) {
        Context context = this.mContext;
        if (context == null) {
            return;
        }
        if ((context instanceof Activity) && (((Activity) context).isDestroyed() || ((Activity) this.mContext).isFinishing())) {
            return;
        }
        if (file == null) {
            this.ivLeftEar.setVisibility(4);
        } else {
            this.ivLeftEar.setVisibility(0);
            Glide.with(this.mContext).load(file.getAbsolutePath()).into(this.ivLeftEar);
        }
        if (file2 == null) {
            this.ivRightEar.setVisibility(4);
        } else {
            this.ivRightEar.setVisibility(0);
            Glide.with(this.mContext).load(file2.getAbsolutePath()).into(this.ivRightEar);
        }
        if (file3 == null) {
            this.ivBackground.setVisibility(4);
        } else {
            this.ivBackground.setVisibility(0);
            Glide.with(this.mContext).load(file3.getAbsolutePath()).into(this.ivBackground);
        }
    }

    private void initPetEatingView() {
        this.petEatingView = (LinearLayout) findViewById(R.id.pet_eating_view);
    }

    public void setPlayerStatus(D4shRecord d4shRecord) {
        if (isLandscape() && this.playerLandscapeView.getVideoType() == 2) {
            return;
        }
        try {
            if (Double.parseDouble(this.d4shRecord.getFirmware()) >= 200.0d) {
                if (d4shRecord.getState().getPim() == 0) {
                    setPlayerStatus(d4shRecord, 5);
                    return;
                }
                if (d4shRecord.getState().getPim() == 2) {
                    setPlayerStatus(null, 6);
                    return;
                }
                if (d4shRecord.getState().getCameraStatus() == 0) {
                    setPlayerStatus(this.d4shRecord, 4);
                    return;
                }
                if (d4shRecord.getSettings().getPreLive() == 0 && !this.playVideoInPrivacyMode) {
                    setPlayerStatus(this.d4shRecord, 7);
                    return;
                }
                int i = this.d4shPlayerStatus;
                if (i == 0 || i == 4 || i == 5 || i == 6 || i == 7) {
                    if (i == 7) {
                        this.player.clearCoverView(2);
                        setPlayerStatus(null, 1);
                        return;
                    } else {
                        setPlayerStatus(null, 1);
                        return;
                    }
                }
                return;
            }
            if (d4shRecord.getState().getPim() == 0) {
                setPlayerStatus(d4shRecord, 5);
                return;
            }
            if (d4shRecord.getState().getPim() == 2) {
                setPlayerStatus(null, 6);
                return;
            }
            if (d4shRecord.getSettings().getCamera() == 0) {
                setPlayerStatus(this.d4shRecord, 4);
                return;
            }
            if (d4shRecord.getSettings().getPreLive() == 0 && !this.playVideoInPrivacyMode) {
                setPlayerStatus(this.d4shRecord, 7);
                return;
            }
            int i2 = this.d4shPlayerStatus;
            if (i2 == 0 || i2 == 4 || i2 == 5 || i2 == 6 || i2 == 7) {
                if (i2 == 7) {
                    this.player.clearCoverView(2);
                    setPlayerStatus(null, 1);
                } else {
                    this.player.clearCoverView();
                    setPlayerStatus(null, 1);
                }
            }
        } catch (Exception e) {
            PetkitLog.d(e.getMessage());
            LogcatStorageHelper.addLog(e.getMessage());
        }
    }

    public void unCameraTimeToStopRecording() {
        if (!this.player.isRecording() || this.d4shHomeDeviceViewListener == null) {
            return;
        }
        Activity activity = this.activity;
        if (activity instanceof D4shHomeActivity) {
            ((D4shHomeActivity) activity).showCameraOutToast = true;
        }
        PetkitLog.i("d4sh debug = unCameraTimeToStopRecording");
        this.d4shHomeDeviceViewListener.onReDeviceResourcesDisconnected(true);
    }

    public void setPlayerStatus(D4shRecord d4shRecord, int i) {
        if (i == 4 || i == 1 || this.d4shPlayerStatus != i) {
            this.d4shPlayerStatus = i;
            D4shPlayerPortraitView d4shPlayerPortraitView = this.playerPortraitView;
            Boolean bool = Boolean.FALSE;
            d4shPlayerPortraitView.setVolumeImageVisible(bool);
            this.playerPortraitView.setPrivacyModePlayImageVisible(bool);
            this.playerLandscapeView.setLiveButtonVisibleStatus(bool);
            this.player.setViewBlack(8);
            showLive(false, i);
            PetkitLog.i("d4sh debug = d4shPlayerStatus = " + i);
            if (i == 5 || i == 4 || i == 3 || i == 6 || i == 7) {
                unCameraTimeToStopRecording();
            }
            if (i == 5) {
                hideLoadingView();
                View viewAddCoverView = this.player.addCoverView(Integer.valueOf(R.layout.d4sh_player_offline_cover_view));
                if (viewAddCoverView != null) {
                    ((TextView) viewAddCoverView.findViewById(R.id.offline_time_text_view)).setText(this.mContext.getString(R.string.Offline_time, DateUtil.getDateFormatShortTimeShortString(DateUtil.long2str(d4shRecord.getState().getOfflineTime(), "yyyy-MM-dd'T'HH:mm:ss.SSSZ"))));
                    return;
                }
                return;
            }
            if (i == 4) {
                hideLoadingView();
                View viewAddCoverView2 = this.player.addCoverView(Integer.valueOf(R.layout.d4sh_player_turn_off_cover_view));
                if (viewAddCoverView2 != null) {
                    TextView textView = (TextView) viewAddCoverView2.findViewById(R.id.turn_on_camera_text_view);
                    TextView textView2 = (TextView) viewAddCoverView2.findViewById(R.id.tv_camera_title);
                    TextView textView3 = (TextView) viewAddCoverView2.findViewById(R.id.tv_camera_prompt);
                    textView.setOnClickListener(this);
                    if (Double.parseDouble(d4shRecord.getFirmware()) < 200.0d || d4shRecord.getSettings().getCamera() == 0) {
                        textView2.setText(R.string.Camera_is_off);
                        textView3.setVisibility(0);
                        textView.setText(R.string.Turn_on_camera);
                    } else {
                        textView2.setText(R.string.Camera_off);
                        textView3.setVisibility(0);
                        textView.setText(R.string.Turn_on_camera_out_worktime);
                    }
                }
                this.player.pausePlay();
                this.invisible = true;
                if (isLandscape()) {
                    landscapeVisibleCheck();
                    return;
                } else {
                    visibleCheck();
                    return;
                }
            }
            if (i == 1) {
                showLoadingView();
                this.playerLandscapeView.setShowLandscapeVideoQualityTextView(true);
                this.player.clearCoverView();
                if (isLandscape() && this.playerLandscapeView.getVideoType() == 2) {
                    D4shHomeDeviceViewListener d4shHomeDeviceViewListener = this.d4shHomeDeviceViewListener;
                    if (d4shHomeDeviceViewListener != null) {
                        d4shHomeDeviceViewListener.onPlayerStartLoadVideo();
                    }
                    this.player.reStartPlay();
                    return;
                }
                this.d4shHomeDeviceViewListener.onCameraAvailable();
                return;
            }
            if (i == 3) {
                hideLoadingView();
                this.playerLandscapeView.setShowLandscapeVideoQualityTextView(true);
                View viewAddCoverView3 = this.player.addCoverView(Integer.valueOf(R.layout.d4sh_player_time_out_cover_view));
                if (viewAddCoverView3 != null) {
                    ((TextView) viewAddCoverView3.findViewById(R.id.retry_text_view)).setOnClickListener(this);
                    return;
                }
                return;
            }
            if (i == 6) {
                hideLoadingView();
                this.player.addCoverView(Integer.valueOf(R.layout.d4sh_player_battery_mode_cover_view));
                this.player.findViewById(R.id.camera_unavailable_prompt_view).setOnClickListener(this);
                return;
            }
            if (i == 7) {
                hideLoadingView();
                this.playerPortraitView.setPrivacyModePlayImageVisible(Boolean.TRUE);
                this.player.addCoverView(Integer.valueOf(R.layout.d4sh_player_privacy_mode_cover_view), 2);
                final ImageView imageView = (ImageView) this.player.findViewById(R.id.event_blur_image_view);
                imageView.setOnClickListener(this);
                String eventPreview = D4shUtils.getEventPreview(this.mContext, this.d4shRecord.getDeviceId(), this.d4shRecord.getTypeCode());
                if (eventPreview != null) {
                    new ImageCacheSimple(CommonUtils.getAppContext()).getImageCachePath(eventPreview, new ImageCacheSimple.IGetImageCacheListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView$$ExternalSyntheticLambda12
                        @Override // com.jess.arms.widget.imageloader.ImageCacheSimple.IGetImageCacheListener
                        public final void onCachePath(String str) {
                            this.f$0.lambda$setPlayerStatus$10(imageView, str);
                        }
                    });
                    return;
                } else {
                    imageView.setImageResource(R.drawable.solid_dark_black_16);
                    return;
                }
            }
            this.player.removePlayerBlackBackground();
            D4shPlayerPortraitView d4shPlayerPortraitView2 = this.playerPortraitView;
            Boolean bool2 = Boolean.TRUE;
            d4shPlayerPortraitView2.setVolumeImageVisible(bool2);
            this.playerLandscapeView.setLiveButtonVisibleStatus(bool2);
            showLive(true, i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setPlayerStatus$10(ImageView imageView, String str) {
        if (str != null) {
            imageView.setImageBitmap(BlurUtils.blur(this.mContext, BitmapFactory.decodeFile(ImageUtils.decryptImageFile(new File(str), D4shUtils.getEventPreviewAesKey(this.mContext, this.d4shRecord.getDeviceId(), this.d4shRecord.getTypeCode())).getAbsolutePath()), 10, 0.125f));
        }
    }

    public void showLive(boolean z, int i) {
        PetkitLog.d("d4shVideo", "当前的状态：" + i);
        this.playerPortraitView.setVideoTagLayoutVisible(z);
    }

    private void showLoadingView() {
        this.player.showLoadingView();
    }

    private void hideLoadingView() {
        this.player.hideLoadingView();
    }

    public VideoPlayerView getPlayerView() {
        return this.player.getPlayerView();
    }

    public H3TextureView getLiveView() {
        return this.player.getLiveView();
    }

    public void changeToLiveView() {
        this.player.changeToLive();
    }

    public void refreshPlanView(DifferentFeedPlan differentFeedPlan) {
        this.differentFeedPlan = differentFeedPlan;
        int i = Calendar.getInstance().get(7) - 1;
        if (differentFeedPlan != null) {
            if (differentFeedPlan.getFeedDailyList().get(i).getSuspended() == 0) {
                this.bleMenuView.setIsFeederPlanIsOpen(true);
                this.bleMenuView.setDeviceType(this.d4shRecord.getTypeCode() == 0 ? 25 : 26, this.d4shRecord.getModelCode());
            } else {
                this.bleMenuView.setIsFeederPlanIsOpen(false);
                this.bleMenuView.setDeviceType(this.d4shRecord.getTypeCode() == 0 ? 25 : 26, this.d4shRecord.getModelCode());
            }
        }
        setD4Record(this.d4shRecord);
        checkDeviceWarnState();
    }

    public void refreshFreeTrailTip() {
        if (AppVersionStateUtils.getCurrentAppVersionCheckState()) {
            return;
        }
        this.playerLandscapeView.setTipState(this.d4shRecord);
        showPurchaseEntry();
    }

    public void refreshVlogTipStatus(D4shRecord d4shRecord) {
        if (d4shRecord.getSettings().getHighlight() == 1) {
            DataHelper.setIntergerSF(getContext(), com.petkit.android.utils.Constants.D4H_D4SH_VLOG_TIP + d4shRecord.getDeviceId(), 1);
            this.rlVlogRvPanel.setVisibility(0);
        } else {
            this.rlVlogRvPanel.setVisibility(8);
        }
        if (d4shRecord.getSettings().getHighlight() == 0) {
            if (DataHelper.getIntergerSF(getContext(), com.petkit.android.utils.Constants.D4H_D4SH_VLOG_TIP + d4shRecord.getDeviceId()) == 1 && d4shRecord.getDeviceShared() == null) {
                this.rlVlogOpenTip.setVisibility(0);
                return;
            }
        }
        this.rlVlogOpenTip.setVisibility(8);
    }

    @SuppressLint({"StringFormatInvalid"})
    public void setD4Record(final D4shRecord d4shRecord) {
        if (d4shRecord == null) {
            return;
        }
        this.d4shRecord = d4shRecord;
        this.bleMenuView.isSingleDevice(d4shRecord.getDeviceShared() != null);
        if (d4shRecord.getModelCode() == 2) {
            this.ivChartShow.setVisibility(0);
        } else {
            this.ivChartShow.setVisibility(8);
            this.llDay.setVisibility(8);
        }
        if (d4shRecord.getServiceStatus() == 0) {
            this.tvDeviceTag.setVisibility(8);
        } else if (d4shRecord.getServiceStatus() == 1) {
            this.tvDeviceTag.setVisibility(0);
            this.tvDeviceTag.setTextColor(CommonUtils.getColorById(R.color.receive_for_free));
            this.tvDeviceTag.setBackgroundResource(R.drawable.gradient_gold_corners_petkitcare_tag_bg);
        } else if (d4shRecord.getServiceStatus() == 2) {
            this.tvDeviceTag.setTextColor(CommonUtils.getColorById(R.color.home_feeder_bg_gray));
            this.tvDeviceTag.setBackgroundResource(R.drawable.gradient_gray_corners_petkitcare_tag_bg);
            this.tvDeviceTag.setVisibility(0);
        }
        if (this.d4shLiveViewPagerAdapter != null && this.vpLive.getCurrentItem() == 1) {
            this.d4shLiveViewPagerAdapter.updateData(this.d4shRecord);
            this.d4shLiveViewPagerAdapter.setCurrentPosition(this.vpLive.getCurrentItem());
            this.d4shLiveViewPagerAdapter.refreshData();
        } else {
            this.d4shLiveViewPagerAdapter.updateData(this.d4shRecord);
        }
        setToolbarData(d4shRecord);
        this.playerLandscapeView.setDeviceInfo(d4shRecord.getTypeCode() == 0 ? 25 : 26, d4shRecord.getDeviceId());
        D4shDeviceDataCardViewGroup d4shDeviceDataCardViewGroup = this.d4shDeviceDataCardViewGroup;
        if (d4shDeviceDataCardViewGroup != null) {
            d4shDeviceDataCardViewGroup.setDeviceTypeCodeAndRefreshView(d4shRecord.getTypeCode());
        }
        D4shHomeBannerPageAdapter d4shHomeBannerPageAdapter = new D4shHomeBannerPageAdapter(this.mContext, this.dataList, d4shRecord, new D4shHomeBannerPageAdapter.OnItemClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView.30
            @Override // com.petkit.android.activities.petkitBleDevice.d4sh.adapter.D4shHomeBannerPageAdapter.OnItemClickListener
            public void onBannerItemClick(int i) {
                D4shHomeDeviceView.this.redirectToPurchasePage(d4shRecord.getTypeCode() == 0 ? 25 : 26, d4shRecord.getDeviceId());
            }

            @Override // com.petkit.android.activities.petkitBleDevice.d4sh.adapter.D4shHomeBannerPageAdapter.OnItemClickListener
            public void redirectToPurchase(int i, long j) {
                D4shHomeDeviceView.this.redirectToPurchasePage(d4shRecord.getTypeCode() == 0 ? 25 : 26, d4shRecord.getDeviceId());
            }
        });
        this.d4shHomeBannerPageAdapter = d4shHomeBannerPageAdapter;
        this.viewPager.setAdapter(d4shHomeBannerPageAdapter);
        refreshVlogTipStatus(d4shRecord);
        if (this.d4shHomeRecordAdapter == null) {
            if (d4shRecord.getTypeCode() == 0) {
                this.d4shHomeRecordAdapter = new D4shHomeRecordAdapter(this.mContext, d4shRecord.getDeviceId(), d4shRecord.getTypeCode(), 0, this);
            } else {
                this.d4shHomeRecordAdapter = new D4hHomeRecordAdapter(this.mContext, d4shRecord.getDeviceId(), d4shRecord.getTypeCode(), 0, this);
            }
            this.rvD4RecordView.setAdapter(this.d4shHomeRecordAdapter);
        }
        if (!AppVersionStateUtils.getCurrentAppVersionCheckState()) {
            this.playerLandscapeView.setTipState(d4shRecord);
            showPurchaseEntry();
        }
        this.playerLandscapeView.setServiceUnavailable(d4shRecord.getServiceStatus() != 1);
        checkDeepEnergySavingStatus();
        if (d4shRecord.getState().getPim() == 0) {
            setDesiccantDataCardViewData(d4shRecord.getState().getDesiccantLeftDays());
            this.isUnMask = false;
            this.bleMenuView.changeAllMask(true);
            D4shHomeDeviceViewListener d4shHomeDeviceViewListener = this.d4shHomeDeviceViewListener;
            if (d4shHomeDeviceViewListener != null) {
                d4shHomeDeviceViewListener.onCancelRecord();
            }
            if (d4shRecord.getState().getFeedState() != null) {
                setEatDataCardViewData(d4shRecord.getState().getFeedState().getEatCount());
            } else {
                setEatDataCardViewData(0);
            }
            setArcCenterView();
            return;
        }
        if (d4shRecord.getState().getPim() == 2) {
            this.bleMenuView.setIsD4sBatteryMode(true);
            this.bleMenuView.setDeviceType(d4shRecord.getTypeCode() == 0 ? 25 : 26, d4shRecord.getModelCode());
        } else {
            this.bleMenuView.setIsD4sBatteryMode(false);
            this.bleMenuView.setDeviceType(d4shRecord.getTypeCode() == 0 ? 25 : 26, d4shRecord.getModelCode());
        }
        setDesiccantDataCardViewData(d4shRecord.getState().getDesiccantLeftDays());
        this.isUnMask = true;
        this.bleMenuView.changeAllMask(false);
        if (FileUtils.checkAgoraFile() == 2) {
            if (!TextUtils.isEmpty(d4shRecord.getState().getErrorCode()) && d4shRecord.getState().getErrorLevel() == 1) {
                this.isUnMask = false;
                this.d4shDeviceWarnMessageViewGroup.showMainWarnMessageView(d4shRecord.getState().getErrorMsg());
                this.bleMenuView.changeAllMask(true);
            } else if (d4shRecord.getState().getOta() == 1) {
                if (d4shRecord.getDeviceShared() != null) {
                    this.isUnMask = false;
                    this.bleMenuView.changeAllMask(true);
                }
                this.d4shDeviceWarnMessageViewGroup.setWarnMessageViewGroupStatus(null, Boolean.TRUE, this.activity.getResources().getString(R.string.Device_ota_prompt), null, Boolean.valueOf(d4shRecord.getDeviceShared() == null), null, null);
                this.d4shDeviceWarnMessageViewGroup.setWarnMessageShowRightArrowIcon(Boolean.valueOf(d4shRecord.getDeviceShared() == null));
            } else if (!TextUtils.isEmpty(d4shRecord.getState().getErrorCode())) {
                this.d4shDeviceWarnMessageViewGroup.showMainWarnMessageView(d4shRecord.getState().getErrorMsg());
            }
        }
        if (d4shRecord.getState().getFeedState() != null) {
            setEatDataCardViewData(d4shRecord.getState().getFeedState().getEatCount());
        } else {
            setEatDataCardViewData(0);
        }
        setArcCenterView();
        processFeedStateData();
        checkDeviceWarnState();
        if (d4shRecord.getState().getPim() != 0 && d4shRecord.getState().getFeeding() == 1) {
            this.bleMenuView.setFirstButtonText(getResources().getString(R.string.Stop_feed));
            this.bleMenuView.changeMenuBtnState(0);
            this.playerLandscapeView.setExtraMealBtnImageResource(Integer.valueOf(R.drawable.d4sh_palyer_landscape_pause_extra_meal_icon), Integer.valueOf(R.drawable.solid_transparent_oval));
        } else if (d4shRecord.getState().getPim() != 0 && d4shRecord.getState().getFeeding() == 2) {
            this.bleMenuView.setFirstButtonText(getResources().getString(R.string.Stop_feed));
            this.bleMenuView.changeMenuBtnState(0);
            this.playerLandscapeView.setExtraMealBtnImageResource(Integer.valueOf(R.drawable.d4sh_palyer_landscape_pause_extra_meal_icon), Integer.valueOf(R.drawable.solid_transparent_oval));
        } else if (d4shRecord.getState().getPim() != 0 && d4shRecord.getState().getFeeding() == 3) {
            this.bleMenuView.setFirstButtonText(getResources().getString(R.string.Stop_feed));
            this.bleMenuView.changeMenuBtnState(0);
            this.playerLandscapeView.setExtraMealBtnImageResource(Integer.valueOf(R.drawable.d4sh_palyer_landscape_pause_extra_meal_icon), Integer.valueOf(R.drawable.solid_transparent_oval));
        } else {
            this.bleMenuView.setFirstButtonText(getResources().getString(R.string.Extra_feeding));
            this.bleMenuView.changeAllMenuBtnState(false);
            this.playerLandscapeView.setExtraMealBtnImageResource(Integer.valueOf(R.drawable.d4sh_player_landscape_extra_meal), Integer.valueOf(R.drawable.solid_half_transparent_black_oval));
        }
        if (d4shRecord.getState().getOta() == 1) {
            this.d4shDeviceWarnMessageViewGroup.setWarnMessageShowRightArrowIcon(Boolean.valueOf(d4shRecord.getDeviceShared() == null));
        }
    }

    private void showPurchaseEntry() {
        Activity activity;
        int i;
        Activity activity2;
        int i2;
        boolean z = this.d4shRecord.getDeviceShared() != null;
        if (this.d4shRecord.getCloudProduct() != null) {
            Context context = getContext();
            StringBuilder sb = new StringBuilder();
            sb.append(this.d4shRecord.getDeviceId());
            sb.append("_");
            sb.append(this.d4shRecord.getTypeCode() == 0 ? 25 : 26);
            sb.append("_");
            sb.append(this.d4shRecord.getCloudProduct().getServiceId());
            TextUtils.isEmpty(DataHelper.getStringSF(context, sb.toString()));
        }
        Activity activity3 = this.activity;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(com.petkit.android.utils.Constants.D4H_D4SH_DEVICE_HOME_FREE_TRIAL_TIP);
        sb2.append(this.d4shRecord.getTypeCode() == 0 ? 25 : 26);
        sb2.append("_");
        sb2.append(this.d4shRecord.getDeviceId());
        DataHelper.getBooleanSF(activity3, sb2.toString());
        final int i3 = this.d4shRecord.getTypeCode() == 0 ? 25 : 26;
        final int intergerSF = DataHelper.getIntergerSF(this.activity, com.petkit.android.utils.Constants.D4H_D4SH_FREE_TRIAL_LEFT_TIME_PROMPT_COUNT + i3 + "_" + this.d4shRecord.getDeviceId(), 2);
        this.llTrialRemainingTime.setVisibility(8);
        int serviceStatus = this.d4shRecord.getServiceStatus();
        if (serviceStatus != 0) {
            if (serviceStatus == 1) {
                this.playerLandscapeView.setServiceStatus(z, Boolean.FALSE, this.d4shDayItem);
                if (this.d4shRecord.getMoreService() == 1 || this.d4shRecord.getCloudProduct().getSubscribe() == 1) {
                    this.rlTipPanel.setVisibility(8);
                    return;
                }
                if ("FREE".equalsIgnoreCase(this.d4shRecord.getCloudProduct().getChargeType())) {
                    final boolean z2 = (Long.parseLong(this.d4shRecord.getCloudProduct().getWorkIndate()) * 1000) - System.currentTimeMillis() < 259200000;
                    if (intergerSF == 2 || (intergerSF == 1 && z2)) {
                        this.llTrialRemainingTime.setVisibility(0);
                        int i4 = (int) (((Long.parseLong(this.d4shRecord.getCloudProduct().getWorkIndate()) * 1000) - System.currentTimeMillis()) / 86400000);
                        String str = "zh_CN".equalsIgnoreCase(UserInforUtils.getLanguageLocaleString(CommonUtils.getAppContext())) ? "" : " ";
                        if (i4 > 0) {
                            TextView textView = this.tvTrialRemainingTime;
                            StringBuilder sb3 = new StringBuilder();
                            sb3.append(this.activity.getString(R.string.Trial_remaining_time));
                            sb3.append("：");
                            sb3.append(String.valueOf(i4));
                            sb3.append(str);
                            if (i4 > 1) {
                                activity2 = this.activity;
                                i2 = R.string.Unit_days;
                            } else {
                                activity2 = this.activity;
                                i2 = R.string.Unit_day;
                            }
                            sb3.append(activity2.getString(i2));
                            textView.setText(sb3.toString());
                        } else if (i4 == 0) {
                            float f = ((Long.parseLong(this.d4shRecord.getCloudProduct().getWorkIndate()) * 1000.0f) - System.currentTimeMillis()) / 3600000.0f;
                            if (f <= 0.0f) {
                                this.llTrialRemainingTime.setVisibility(8);
                            } else if (f < 1.0f) {
                                this.tvTrialRemainingTime.setText(this.activity.getString(R.string.Trial_remaining_time) + "：" + String.valueOf(1) + str + this.activity.getString(R.string.Unit_hour));
                            } else {
                                int i5 = (int) f;
                                TextView textView2 = this.tvTrialRemainingTime;
                                StringBuilder sb4 = new StringBuilder();
                                sb4.append(this.activity.getString(R.string.Trial_remaining_time));
                                sb4.append("：");
                                sb4.append(String.valueOf(i5));
                                sb4.append(str);
                                if (i5 > 1) {
                                    activity = this.activity;
                                    i = R.string.Unit_hours;
                                } else {
                                    activity = this.activity;
                                    i = R.string.Unit_hour;
                                }
                                sb4.append(activity.getString(i));
                                textView2.setText(sb4.toString());
                            }
                        }
                        this.ivTrialRemainingTimeClose.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView.31
                            @Override // android.view.View.OnClickListener
                            public void onClick(View view) {
                                D4shHomeDeviceView.this.llTrialRemainingTime.setVisibility(8);
                                DataHelper.setIntergerSF(D4shHomeDeviceView.this.activity, com.petkit.android.utils.Constants.D4H_D4SH_FREE_TRIAL_LEFT_TIME_PROMPT_COUNT + i3 + "_" + D4shHomeDeviceView.this.d4shRecord.getDeviceId(), z2 ? 0 : intergerSF - 1);
                            }
                        });
                    }
                    this.rlTipPanel.setVisibility(8);
                    return;
                }
                return;
            }
            if (serviceStatus != 2) {
                return;
            }
        }
        this.playerLandscapeView.setServiceStatus(z, Boolean.TRUE, this.d4shDayItem);
    }

    private void showPetEatingWindow() {
        PetkitPetEatingWindow petkitPetEatingWindow = this.eatingWindow;
        if (petkitPetEatingWindow == null || !petkitPetEatingWindow.isShowing()) {
            PetkitPetEatingWindow petkitPetEatingWindow2 = new PetkitPetEatingWindow(this.mContext);
            this.eatingWindow = petkitPetEatingWindow2;
            petkitPetEatingWindow2.showAsDropDown(this.player, 0, ArmsUtils.dip2px(this.mContext, 6.0f));
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.widget.BleDeviceHomeMenuView.OnD4shDeviceHomeMenuTouchListener
    public void onD4shDeviceHomeMenuMicroPhoneViewTouchDown() {
        int i = this.d4shPlayerStatus;
        if (i == 7) {
            PetkitToast.showTopToast(getContext(), getContext().getString(R.string.Turn_on_camera_and_try_again), 0, 1);
            return;
        }
        if (i == 4) {
            PetkitToast.showTopToast(getContext(), getContext().getString(R.string.Turn_on_camera_and_try_again), 0, 1);
            return;
        }
        if (i == 1) {
            PetkitToast.showTopToast(getContext(), getContext().getString(R.string.Turn_on_camera_and_try_again), 0, 1);
            return;
        }
        if (i == 3) {
            PetkitToast.showTopToast(getContext(), getContext().getString(R.string.Turn_on_camera_and_try_again), 0, 1);
            return;
        }
        D4shHomeDeviceViewListener d4shHomeDeviceViewListener = this.d4shHomeDeviceViewListener;
        if (d4shHomeDeviceViewListener != null) {
            d4shHomeDeviceViewListener.onD4shDeviceHomeMenuMicroPhoneViewTouchDown();
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.widget.BleDeviceHomeMenuView.OnD4shDeviceHomeMenuTouchListener
    public void onD4shDeviceHomeMenuMicroPhoneViewTouchUp() {
        D4shHomeDeviceViewListener d4shHomeDeviceViewListener = this.d4shHomeDeviceViewListener;
        if (d4shHomeDeviceViewListener != null) {
            d4shHomeDeviceViewListener.onD4shDeviceHomeMenuMicroPhoneViewTouchUp();
        }
    }

    @Override // com.petkit.android.media.video.player.listener.BasePetkitPlayerListener
    public void onCompleted() {
        if (isLandscape()) {
            this.player.reStartPlay();
        }
    }

    @Override // com.petkit.android.media.video.player.listener.BasePetkitPlayerListener
    public void playing(String str, long j) {
        PetkitLog.d(getClass().getSimpleName(), "playing:" + str + ChineseToPinyinResource.Field.COMMA + j);
        int position = this.player.getPosition();
        if (position >= 0) {
            this.playerLandscapeView.setVideoListProgressState(position, (int) j, ((Integer.parseInt(str.split("&")[1].split(":")[0]) * 3600) + (Integer.parseInt(str.split("&")[1].split(":")[1]) * 60) + Integer.parseInt(str.split("&")[1].split(":")[2])) * 1000);
        } else {
            this.playerLandscapeView.setSeekbarStatus(Integer.valueOf((int) j), null, str);
        }
    }

    @Override // com.petkit.android.media.video.player.listener.BasePetkitPlayerListener
    public void onVideoClick() {
        if (isLandscape()) {
            this.playerLandscapeView.viewClick();
        } else if (this.d4shPlayerStatus == 7) {
            Context context = this.mContext;
            PetkitToast.showTopToast(context, context.getString(R.string.Turn_on_camera_and_try_again), 0, 1);
        } else {
            this.player.switchFullOrWindowMode(50, false);
        }
    }

    @Override // com.petkit.android.media.video.player.listener.BasePetkitPlayerListener
    public void preparedVideo(String str, int i, int i2) {
        int position = this.player.getPosition();
        if (position >= 0) {
            this.playerLandscapeView.setVideoListProgressState(position, i, i2);
        } else {
            this.playerLandscapeView.setSeekbarStatus(Integer.valueOf(i), Integer.valueOf(i2), str);
        }
    }

    @Override // com.petkit.android.media.video.player.listener.BasePetkitPlayerListener
    /* JADX INFO: renamed from: onPrepared */
    public void lambda$initP2PSuccess$2() {
        setPlayerStatus(null, 2);
        hideLoadingView();
        this.playerLandscapeView.setRecordButtonVisibleStatus(Boolean.TRUE);
        this.playerLandscapeView.setPlayerSwitchImageViewResource(Integer.valueOf(R.drawable.petkit_player_landscape_record_view_pause_icon));
        D4shHomeDeviceViewListener d4shHomeDeviceViewListener = this.d4shHomeDeviceViewListener;
        if (d4shHomeDeviceViewListener != null) {
            d4shHomeDeviceViewListener.onPlayerLoadVideoSuccess();
        }
        if (this.player.isLive()) {
            PetkitLog.d("TENCENT_P2P", "live_success");
            LogcatStorageHelper.addLog("TENCENT_P2P, live_success");
        }
    }

    @Override // com.petkit.android.media.video.player.listener.BasePetkitPlayerListener
    public void continueVideo() {
        this.playerLandscapeView.setPlayerSwitchImageViewResource(Integer.valueOf(R.drawable.petkit_player_landscape_record_view_pause_icon));
    }

    @Override // com.petkit.android.media.video.player.listener.BasePetkitPlayerListener
    public void onVideoLongClick() {
        float f = this.eatVideoTimesSpeed;
        if (f == 4.0f || f == 2.0f || !isLandscape()) {
            return;
        }
        if (!(this.playerLandscapeView.getVideoType() == 2 && this.playerLandscapeView.getEatVideoType() == 1) && this.playerLandscapeView.getVideoType() == 2 && this.playerLandscapeView.getEatVideoType() == 2 && this.player.isPlayerPlayingState()) {
            ((Vibrator) this.mContext.getSystemService("vibrator")).vibrate(100L);
            this.playerLandscapeView.showTimeSpeedTip();
            this.player.setTimesSpeed(2.0f);
        }
    }

    @Override // com.petkit.android.media.video.player.listener.BasePetkitPlayerListener
    public void onVideoLongClickEnd() {
        float f = this.eatVideoTimesSpeed;
        if (f != 4.0f && f != 2.0f && isLandscape() && this.playerLandscapeView.getVideoType() == 2 && this.playerLandscapeView.getEatVideoType() == 2) {
            this.playerLandscapeView.hideTimeSpeedTip();
            this.player.setTimesSpeed(this.eatVideoTimesSpeed);
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.widget.BleDeviceHomeMenuView.BatteryModeClickListener
    public void onBatteryModeClick() {
        openCameraUnavailableBatteryModeWindow();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.iv_next_event) {
            if (this.popCount < this.sortEventList.size() - 1) {
                int i = this.popCount + 1;
                this.popCount = i;
                this.sortEventList.get(i).getClickView().performClick();
                return;
            }
            return;
        }
        if (id == R.id.iv_last_event) {
            int i2 = this.popCount;
            if (i2 > 0) {
                int i3 = i2 - 1;
                this.popCount = i3;
                this.sortEventList.get(i3).getClickView().performClick();
                return;
            }
            return;
        }
        if (id == R.id.rl_purchase_eligibility) {
            MenuOnClickListener menuOnClickListener = this.menuOnClickListener;
            if (menuOnClickListener != null) {
                menuOnClickListener.onBottomMenuClick(MenuType.PURCHASE_ELIGIBILITY_CLICK);
                return;
            }
            return;
        }
        if (id == R.id.iv_chart_show) {
            if (this.llDay.getVisibility() == 0) {
                this.llDay.setVisibility(8);
                this.rlPop.setVisibility(8);
                this.ivChartShow.setImageResource(R.drawable.icon_t6_chart_gray);
                return;
            } else {
                this.llDay.setVisibility(0);
                this.ivChartShow.setImageResource(R.drawable.icon_d4sh_chart_orange);
                return;
            }
        }
        if (id == R.id.iv_vlog_tip_close) {
            DataHelper.setIntergerSF(getContext(), com.petkit.android.utils.Constants.D4H_D4SH_VLOG_TIP + this.d4shRecord.getDeviceId(), 0);
            refreshVlogTipStatus(this.d4shRecord);
            return;
        }
        if (id == R.id.ll_pets) {
            if (this.rlPop.getVisibility() == 0) {
                this.rlPop.setVisibility(8);
            }
            MenuOnClickListener menuOnClickListener2 = this.menuOnClickListener;
            if (menuOnClickListener2 != null) {
                menuOnClickListener2.onBottomMenuClick(MenuType.CHART_SELECT_PET);
                return;
            }
            return;
        }
        if (id == R.id.tv_open_vlog) {
            MenuOnClickListener menuOnClickListener3 = this.menuOnClickListener;
            if (menuOnClickListener3 != null) {
                menuOnClickListener3.onBottomMenuClick(MenuType.OPEN_HIGHLIGHT);
                return;
            }
            return;
        }
        if (id == R.id.tv_more) {
            Context context = this.mContext;
            context.startActivity(D4shVlogActivity.newIntent(context, this.d4shRecord.getDeviceId(), this.d4shRecord.getTypeCode(), this.d4shRecord.getCloudProduct() != null ? this.d4shRecord.getCloudProduct().getServiceId() : 0));
            return;
        }
        if (id == R.id.iv_left_ear_click_view || id == R.id.iv_right_ear_click_view) {
            if (this.d4shRecord.getDeviceShared() == null && this.d4shRecord.getSettings().getAttireSwitch() == 1 && this.d4shRecord.getState().getPim() != 2) {
                Activity activity = this.activity;
                activity.startActivity(D4shPersonalizedDressingActivity.newIntent(activity, this.d4shRecord.getDeviceId(), this.d4shRecord.getTypeCode()));
                return;
            }
            return;
        }
        if (id == R.id.open_immediately) {
            this.serviceStatusMaybeChanged = true;
            redirectToPurchasePage(this.d4shRecord.getTypeCode() == 0 ? 25 : 26, this.d4shRecord.getDeviceId());
            return;
        }
        if (id == R.id.event_blur_image_view) {
            Context context2 = this.mContext;
            PetkitToast.showTopToast(context2, context2.getString(R.string.Turn_on_camera_and_try_again), 0, 1);
            return;
        }
        if (id == R.id.eat_data_card_view) {
            D4shHomeDeviceViewListener d4shHomeDeviceViewListener = this.d4shHomeDeviceViewListener;
            if (d4shHomeDeviceViewListener != null) {
                d4shHomeDeviceViewListener.onEatDataCardViewClick();
                return;
            }
            return;
        }
        if (id == R.id.desiccant_data_card_view) {
            D4shHomeDeviceViewListener d4shHomeDeviceViewListener2 = this.d4shHomeDeviceViewListener;
            if (d4shHomeDeviceViewListener2 != null) {
                d4shHomeDeviceViewListener2.onDesiccantDataCardViewClick();
                return;
            }
            return;
        }
        if (id == R.id.camera_unavailable_prompt_view) {
            openCameraUnavailableBatteryModeWindow();
            return;
        }
        if (id == R.id.turn_on_camera_text_view) {
            D4shHomeDeviceViewListener d4shHomeDeviceViewListener3 = this.d4shHomeDeviceViewListener;
            if (d4shHomeDeviceViewListener3 != null) {
                d4shHomeDeviceViewListener3.onTurnOnCameraBtnClick();
                return;
            }
            return;
        }
        if (id == R.id.retry_text_view) {
            this.player.clearCoverView();
            setPlayerStatus(null, 1);
            return;
        }
        if (id == R.id.d4sh_device_record_all_type_number_view) {
            resetTagViewGroupSelectionType(0);
            this.d4shHomeRecordAdapter.setFilterRecordType(0);
            return;
        }
        if (id == R.id.d4sh_device_record_pet_type_number_view) {
            resetTagViewGroupSelectionType(1);
            this.d4shHomeRecordAdapter.setFilterRecordType(1);
            return;
        }
        if (id == R.id.d4sh_device_record_eat_type_number_view) {
            resetTagViewGroupSelectionType(2);
            this.d4shHomeRecordAdapter.setFilterRecordType(2);
            return;
        }
        if (id == R.id.d4sh_device_record_activity_type_number_view) {
            resetTagViewGroupSelectionType(3);
            this.d4shHomeRecordAdapter.setFilterRecordType(3);
            return;
        }
        if (id == R.id.d4sh_device_record_work_type_number_view) {
            resetTagViewGroupSelectionType(4);
            this.d4shHomeRecordAdapter.setFilterRecordType(4);
            return;
        }
        if (id == R.id.iv_mealtime_date || id == R.id.d4sh_device_record_date_image_view || id == R.id.calendar_image_view) {
            D4shHomeDeviceViewListener d4shHomeDeviceViewListener4 = this.d4shHomeDeviceViewListener;
            if (d4shHomeDeviceViewListener4 != null) {
                d4shHomeDeviceViewListener4.onDeviceRecordDateImageViewClick();
                return;
            }
            return;
        }
        if (id == R.id.see_more_feed_data_text_view) {
            D4shHomeDeviceViewListener d4shHomeDeviceViewListener5 = this.d4shHomeDeviceViewListener;
            if (d4shHomeDeviceViewListener5 != null) {
                d4shHomeDeviceViewListener5.onSeeMoreFeedDataTextViewClick();
                return;
            }
            return;
        }
        if (id == R.id.feed_data_card_view) {
            if (this.d4shRecord.getDeviceShared() == null) {
                Activity activity2 = this.activity;
                activity2.startActivity(FeedStatisticsActivity.newIntent(activity2, this.d4shRecord.getTypeCode() == 0 ? 1 : 0));
                return;
            }
            return;
        }
        if (id == R.id.tv_deep_energy_saving_init_cancle) {
            if (this.deepEnergySavingStatus == 1) {
                this.tvDeepEnergySavingInitTime.setText(getResources().getString(R.string.Deep_energy_saving_is_init, "    "));
                this.menuOnClickListener.onBottomMenuClick(MenuType.DEEP_ENERGY_SAVING_CANCLE);
                return;
            }
            return;
        }
        if (id == R.id.rl_title_status) {
            if (this.deepEnergySavingStatus == 2) {
                openDeepEnergySavingIKnowWindow();
                return;
            } else {
                openCameraUnavailableBatteryModeWindow();
                return;
            }
        }
        if (id == R.id.rl_deep_energy_saving_running) {
            if (this.deepEnergySavingStatus == 2) {
                openDeepEnergySavingIKnowWindow();
                return;
            }
            return;
        }
        if (id == R.id.tv_small_arc_center_text) {
            if (this.rlData1.getVisibility() == 8) {
                this.rlData1.setVisibility(0);
                return;
            } else {
                this.rlData1.setVisibility(8);
                return;
            }
        }
        if (id == R.id.tv_big_arc_center_text) {
            if (this.rlData2.getVisibility() == 8) {
                this.rlData2.setVisibility(0);
                return;
            } else {
                this.rlData2.setVisibility(8);
                return;
            }
        }
        if (id == R.id.feeder_inlet) {
            MenuOnClickListener menuOnClickListener4 = this.menuOnClickListener;
            if (menuOnClickListener4 != null) {
                menuOnClickListener4.onBottomMenuClick(MenuType.FEEDER_INLET);
                return;
            }
            return;
        }
        if (id == R.id.tv_history_more) {
            if (this.d4shRecord.getDeviceShared() == null) {
                Activity activity3 = this.activity;
                activity3.startActivity(EatStatisticsActivity.newIntent(activity3));
                return;
            }
            return;
        }
        if (id == R.id.main_warn_message_view) {
            showFirstWarnWindow();
            return;
        }
        if (id == R.id.other_warn_message_view) {
            if (calcWarnNum() == 1) {
                showSecondWarnWindow();
                return;
            }
            MenuOnClickListener menuOnClickListener5 = this.menuOnClickListener;
            if (menuOnClickListener5 != null) {
                menuOnClickListener5.onBottomMenuClick(MenuType.WARN_LIST);
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0082  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void showFirstWarnWindow() {
        /*
            Method dump skipped, instruction units count: 948
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView.showFirstWarnWindow():void");
    }

    public void showSecondWarnWindow() {
        int i = this.d4shRecord.getState().getPim() == 0 ? 1 : 0;
        if (!TextUtils.isEmpty(this.d4shRecord.getState().getErrorCode()) && (i = i + 1) == 2) {
            String errorCode = this.d4shRecord.getState().getErrorCode();
            errorCode.hashCode();
            switch (errorCode) {
                case "DC":
                    new BleDeviceHomeTroubleWarnWindow(this.activity, this.d4shRecord.getTypeCode() == 0 ? 25 : 26, this.mContext.getString(R.string.Failure_remind), this.d4shRecord.getState().getErrorDetail(), this.d4shRecord.getTypeCode() == 0 ? R.drawable.d4sh_dc_error : R.drawable.d4h_dc_error).showAtLocation(this.activity.getWindow().getDecorView(), 17, 0, 0);
                    break;
                case "sys":
                case "grat":
                case "moto":
                case "rtc_c":
                    new BleDeviceHomeTroubleWarnWindow(this.activity, this.d4shRecord.getTypeCode() == 0 ? 25 : 26, this.mContext.getString(R.string.Failure_remind), this.d4shRecord.getState().getErrorDetail(), this.d4shRecord.getTypeCode() == 0 ? R.drawable.d4sh_system_error : R.drawable.d4h_system_error).showAtLocation(this.activity.getWindow().getDecorView(), 17, 0, 0);
                    break;
                case "blk_d":
                    new BleDeviceHomeTroubleWarnWindow(this.activity, this.d4shRecord.getTypeCode() == 0 ? 25 : 26, this.mContext.getString(R.string.Prompt), this.d4shRecord.getState().getErrorDetail(), this.d4shRecord.getTypeCode() == 0 ? R.drawable.d4sh_door_error : R.drawable.d4h_door_error).showAtLocation(this.activity.getWindow().getDecorView(), 17, 0, 0);
                    break;
                case "blk_f":
                    new BleDeviceHomeTroubleWarnWindow(this.activity, this.d4shRecord.getTypeCode() == 0 ? 25 : 26, this.mContext.getString(R.string.Failure_remind), this.d4shRecord.getState().getErrorDetail(), this.d4shRecord.getTypeCode() == 0 ? R.drawable.d4sh_food_error : R.drawable.d4h_food_error).showAtLocation(this.activity.getWindow().getDecorView(), 17, 0, 0);
                    break;
            }
        }
        if (this.d4shRecord.getTypeCode() == 0 && ((this.d4shRecord.getState().getFood1() == 0 || this.d4shRecord.getState().getFood2() == 0 || this.d4shRecord.getState().getFood1() == 1 || this.d4shRecord.getState().getFood2() == 1) && this.d4shRecord.getSettings().getNoRemind() == 0 && (i = i + 1) == 2)) {
            showFoodWarnDialog();
        }
        if (this.d4shRecord.getTypeCode() == 1 && ((this.d4shRecord.getState().getFood() == 0 || this.d4shRecord.getState().getFood() == 1) && this.d4shRecord.getSettings().getNoRemind() == 0 && (i = i + 1) == 2)) {
            showFoodWarnDialog();
        }
        if (this.d4shRecord.getState().getOta() == 1 && (i = i + 1) == 2 && this.d4shRecord.getDeviceShared() == null) {
            if (this.deepEnergySavingStatus == 2) {
                openDeepEnergySavingIKnowWindow();
            } else {
                D4shHomeDeviceViewListener d4shHomeDeviceViewListener = this.d4shHomeDeviceViewListener;
                if (d4shHomeDeviceViewListener != null) {
                    d4shHomeDeviceViewListener.goToOtaActivity();
                }
            }
        }
        if (this.d4shRecord.getState().getBatteryStatus() == 2) {
            i++;
        }
        if (!CommonUtils.isSameTimeZoneAsLocal(this.d4shRecord.getLocale(), this.d4shRecord.getTimezone()) && (i = i + 1) == 2) {
            new NewIKnowWindow(this.activity, this.mContext.getString(R.string.About_device_time), "\t\t\t\t" + this.mContext.getString(R.string.About_device_time_introduce), (String) null).showAtLocation(this.activity.getWindow().getDecorView(), 17, 0, 0);
        }
        if (this.d4shRecord.getState().getDesiccantLeftDays() == 0) {
            if (!CommonUtils.getSysBoolMap(this.mContext, Consts.D4SH_IS_IGNORE_DESICCANT_TIP + this.d4shRecord.getDeviceId(), false)) {
                i++;
            }
        }
        if (this.d4shRecord.getState().getWifi().getRsq() <= -65 && this.d4shRecord.getState().getWifi().getRsq() > -75) {
            if (TimeUtils.getInstance().getCurrentSeconds() - CommonUtils.getSysLongMap(this.activity, Consts.D4SH_WIFI_SIGINAL_WINDOW + this.d4shRecord.getDeviceId()) > 2592000 && (i = i + 1) == 2) {
                showWeakWifiWindow();
            }
        } else if (this.d4shRecord.getState().getWifi().getRsq() < -75 && (i = i + 1) == 2) {
            showWeakWifiWindow();
        }
        if (this.differentFeedPlan == null || i + 1 != 2) {
            return;
        }
        if (this.differentFeedPlan.getFeedDailyList().get(Calendar.getInstance().get(7) - 1).getSuspended() == 0) {
            return;
        }
        Activity activity = this.activity;
        activity.startActivity(DeviceFeedPlansMainActivity.newIntent(activity, this.d4shRecord.getDeviceId(), this.d4shRecord.getTypeCode() == 0 ? 25 : 26, false));
    }

    public void showSelectPet() {
        if (this.selectedPetIds.contains(ColorUtils.ALL_PET)) {
            this.tvPetWeight.setVisibility(8);
            this.scPoint.setVisibility(8);
            this.scPoint2.setVisibility(8);
            this.tvPetName.setVisibility(0);
            this.tvPetName2.setVisibility(8);
            this.tvPetName.setText(R.string.All_cats);
            return;
        }
        if (this.selectedPetIds.size() > 1) {
            this.tvPetWeight.setVisibility(8);
            this.scPoint.setVisibility(0);
            this.scPoint2.setVisibility(0);
            this.tvPetName.setVisibility(0);
            this.tvPetName2.setVisibility(0);
            String string = ColorUtils.UNKNOWN_PET.equals(this.selectedPetIds.get(0)) ? this.mContext.getString(R.string.Not_matched) : UserInforUtils.getPetNameById(this.selectedPetIds.get(0));
            String string2 = ColorUtils.UNKNOWN_PET.equals(this.selectedPetIds.get(1)) ? this.mContext.getString(R.string.Not_matched) : UserInforUtils.getPetNameById(this.selectedPetIds.get(1));
            this.scPoint.setBackgroundResource(ColorUtils.getPetColorCircleResById(this.selectedPetIds.get(0), string));
            this.scPoint2.setBackgroundResource(ColorUtils.getPetColorCircleResById(this.selectedPetIds.get(1), string2));
            this.tvPetName.setText(string);
            this.tvPetName2.setText(string2);
            return;
        }
        if (this.selectedPetIds.size() == 1) {
            if (!ColorUtils.UNKNOWN_PET.equals(this.selectedPetIds.get(0))) {
                this.tvPetWeight.setVisibility(8);
                Pet petById = UserInforUtils.getPetById(this.selectedPetIds.get(0));
                if (UserInforUtils.getCurrentLoginResult().getSettings().getUnit() == 1) {
                    if (petById.getWeight() != null) {
                        String strValueOf = String.valueOf(CommonUtil.doubleToDouble(CommonUtil.KgToLb(Double.valueOf(petById.getWeight()).doubleValue())));
                        String str = this.mContext.getString(R.string.Current_weight) + " " + strValueOf + " " + this.mContext.getString(R.string.Unit_lb) + " >";
                        this.tvPetWeight.setText(TextUtil.getInstance().makePartStringSpannableInTotalString(new SpannableString(str), str, strValueOf, getResources().getColor(R.color.light_black), 20, true));
                    } else {
                        this.tvPetWeight.setVisibility(8);
                    }
                } else if (petById.getWeight() != null) {
                    String strValueOf2 = String.valueOf(CommonUtil.doubleToDouble(Double.valueOf(petById.getWeight()).doubleValue()));
                    String str2 = this.mContext.getString(R.string.Current_weight) + " " + strValueOf2 + " " + this.mContext.getString(R.string.Unit_kg) + " >";
                    this.tvPetWeight.setText(TextUtil.getInstance().makePartStringSpannableInTotalString(new SpannableString(str2), str2, strValueOf2, getResources().getColor(R.color.light_black), 20, true));
                } else {
                    this.tvPetWeight.setVisibility(8);
                }
            } else {
                this.tvPetWeight.setVisibility(8);
            }
            this.scPoint.setVisibility(0);
            this.scPoint2.setVisibility(8);
            this.tvPetName.setVisibility(0);
            this.tvPetName2.setVisibility(8);
            String string3 = ColorUtils.UNKNOWN_PET.equals(this.selectedPetIds.get(0)) ? this.mContext.getString(R.string.Not_matched) : UserInforUtils.getPetNameById(this.selectedPetIds.get(0));
            this.scPoint.setBackgroundResource(ColorUtils.getPetColorCircleResById(this.selectedPetIds.get(0), string3));
            this.tvPetName.setText(string3);
        }
    }

    private void showWeakWifiWindow() {
        if (this.wifiWeakWindow == null) {
            this.wifiWeakWindow = new NewWifiWeakWindow(this.activity, this.d4shRecord.getTypeCode(), this.d4shRecord.getModelCode());
        }
        if (this.d4shRecord.getState().getWifi().getRsq() <= -65 && this.d4shRecord.getState().getWifi().getRsq() > -75) {
            if (TimeUtils.getInstance().getCurrentSeconds() - CommonUtils.getSysLongMap(this.activity, Consts.D4SH_WIFI_SIGINAL_WINDOW + this.d4shRecord.getDeviceId()) > 2592000) {
                this.wifiWeakWindow.setNoRemindVisibility(0);
                this.wifiWeakWindow.setNoRemindClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView.32
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        D4shHomeDeviceView.this.wifiWeakWindow.dismiss();
                        D4shHomeDeviceView.this.checkDeviceWarnState();
                        CommonUtils.addSysLongMap(D4shHomeDeviceView.this.activity, Consts.D4SH_WIFI_SIGINAL_WINDOW + D4shHomeDeviceView.this.d4shRecord.getDeviceId(), TimeUtils.getInstance().getCurrentSeconds());
                    }
                });
                this.wifiWeakWindow.showAtLocation(this.activity.getWindow().getDecorView(), 17, 0, 0);
                return;
            }
            return;
        }
        if (this.d4shRecord.getState().getWifi().getRsq() < -75) {
            this.wifiWeakWindow.setNoRemindVisibility(8);
            CommonUtils.addSysLongMap(this.activity, Consts.D4SH_WIFI_SIGINAL_WINDOW + this.d4shRecord.getDeviceId(), 0L);
            this.wifiWeakWindow.showAtLocation(this.activity.getWindow().getDecorView(), 17, 0, 0);
        }
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

    public void openCameraUnavailableBatteryModeWindow() {
        String string = getResources().getString(R.string.Battery_mode_camera_unavailable);
        String string2 = getResources().getString(R.string.Battery_mode_open_prompt, string);
        SpannableString spannableString = new SpannableString(string2);
        int iIndexOf = string2.indexOf(string);
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.d4_desiccant_warn_red)), iIndexOf, string.length() + iIndexOf, 33);
        new NewIKnowWindow(this.activity, getResources().getString(R.string.Battery_mode_introduce), spannableString, (String) null).show(getRootView());
    }

    public void checkDeepEnergySavingStatus() {
        stopDeepEnergySavingLoading();
        if (this.d4shRecord.getState().getPim() == 0) {
            if (!isLandscape()) {
                this.bleMenuView.setVisibility(0);
            }
            closeDeepEnergySavingTimer();
            this.deepEnergySavingStatus = 0;
            this.rlTitleStatus.setVisibility(8);
            this.rlDeepEnergySaving.setVisibility(8);
            this.bleMenuView.setIsD4sBatteryMode(false);
            this.bleMenuView.setDeviceType(this.d4shRecord.getTypeCode() == 0 ? 25 : 26, this.d4shRecord.getModelCode());
            return;
        }
        if (this.d4shRecord.getState().getPim() == 2) {
            this.rlTitleStatus.setVisibility(0);
            this.tvTitleStatus.setText(getResources().getString(R.string.D2_battery_mode));
            this.bleMenuView.setIsD4sBatteryMode(true);
            this.bleMenuView.setDeviceType(this.d4shRecord.getTypeCode() == 0 ? 25 : 26, this.d4shRecord.getModelCode());
            int batteryPower = this.d4shRecord.getState().getBatteryPower();
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
            if (this.d4shRecord.getSettings().getConservation() == 0) {
                closeDeepEnergySavingTimer();
                this.tvTitleStatus.setText(getResources().getString(R.string.D2_battery_mode));
                this.deepEnergySavingStatus = 0;
                this.rlDeepEnergySaving.setVisibility(8);
                if (isLandscape()) {
                    return;
                }
                this.bleMenuView.setVisibility(0);
                return;
            }
            this.rlDeepEnergySaving.setVisibility(0);
            this.bleMenuView.setVisibility(4);
            if (this.d4shRecord.getState().getConservationStatus() == 0 || this.d4shRecord.getState().getConservationStatus() == -1) {
                this.tvTitleStatus.setText(getResources().getString(R.string.D2_battery_mode));
                this.deepEnergySavingStatus = 1;
                openDeepEnergySavingTimer(this.d4shRecord.getSettings().getCTime() - TimeUtils.getInstance().getCurrentSeconds());
                this.rlDeepEnergySavingRunning.setVisibility(8);
                this.rlDeepEnergySavingInit.setVisibility(0);
                return;
            }
            closeDeepEnergySavingTimer();
            this.tvTitleStatus.setText(getResources().getString(R.string.Deep_energy_saving_mode));
            this.deepEnergySavingStatus = 2;
            this.rlDeepEnergySavingRunning.setVisibility(0);
            this.rlDeepEnergySavingInit.setVisibility(8);
            startDeepEnergySavingLoading();
            return;
        }
        closeDeepEnergySavingTimer();
        this.deepEnergySavingStatus = 0;
        if (!isLandscape()) {
            this.bleMenuView.setVisibility(0);
        }
        this.rlDeepEnergySaving.setVisibility(8);
        this.rlTitleStatus.setVisibility(8);
        this.bleMenuView.setIsD4sBatteryMode(true);
        this.bleMenuView.setDeviceType(this.d4shRecord.getTypeCode() == 0 ? 25 : 26, this.d4shRecord.getModelCode());
    }

    public void openDeepEnergySavingTimer(long j) {
        if (j < 0) {
            j = 0;
        }
        this.leftTime = j;
        if (this.disposable == null) {
            this.disposable = Observable.interval(1000L, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView$$ExternalSyntheticLambda8
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f$0.lambda$openDeepEnergySavingTimer$11((Long) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$openDeepEnergySavingTimer$11(Long l) throws Exception {
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
        refreshDateData(this.d4DailyFeeds);
    }

    @Subscriber
    public void updateD4shVideoRecordFeedback(D4shVideoRecordFeedbackMsg d4shVideoRecordFeedbackMsg) {
        this.isNeedUploadVideo = 0;
        this.playerLandscapeView.setFeedbackImageResource(getResources().getDrawable(R.drawable.feedback_gray_icon));
        int recordType = d4shVideoRecordFeedbackMsg.getD4shVideoRecord().getRecordType();
        if (recordType == 1) {
            List<D4shDailyFeeds.D4shDailyEat> eat = this.d4DailyFeeds.getEat();
            for (int i = 0; i < eat.get(0).getItems().size(); i++) {
                if (eat.get(0).getItems().get(i).getEventId().equals(d4shVideoRecordFeedbackMsg.getD4shVideoRecord().getEventId())) {
                    eat.get(0).getItems().get(i).setIsNeedUploadVideo(0);
                }
            }
        } else if (recordType == 2) {
            List<D4shDailyFeeds.D4shDailyFeed> feed = this.d4DailyFeeds.getFeed();
            for (int i2 = 0; i2 < feed.get(0).getItems().size(); i2++) {
                if (feed.get(0).getItems().get(i2).getEventId().equals(d4shVideoRecordFeedbackMsg.getD4shVideoRecord().getEventId())) {
                    feed.get(0).getItems().get(i2).setIsNeedUploadVideo(0);
                }
            }
        } else if (recordType == 3) {
            List<D4shDailyFeeds.PetBean> pet = this.d4DailyFeeds.getPet();
            for (int i3 = 0; i3 < pet.get(0).getItems().size(); i3++) {
                if (pet.get(0).getItems().get(i3).getEventId().equals(d4shVideoRecordFeedbackMsg.getD4shVideoRecord().getEventId())) {
                    pet.get(0).getItems().get(i3).setIsNeedUploadVideo(0);
                }
            }
        } else if (recordType == 4) {
            List<D4shDailyFeeds.MoveBean> move = this.d4DailyFeeds.getMove();
            for (int i4 = 0; i4 < move.get(0).getItems().size(); i4++) {
                if (move.get(0).getItems().get(i4).getEventId().equals(d4shVideoRecordFeedbackMsg.getD4shVideoRecord().getEventId())) {
                    move.get(0).getItems().get(i4).setIsNeedUploadVideo(0);
                }
            }
        }
        refreshDateData(this.d4DailyFeeds);
        D4shAbstractHomeRecordAdapter d4shAbstractHomeRecordAdapter = this.d4shHomeRecordAdapter;
        if (d4shAbstractHomeRecordAdapter == null || d4shAbstractHomeRecordAdapter.getDateDataList() == null) {
            return;
        }
        String eventId = d4shVideoRecordFeedbackMsg.getD4shVideoRecord().getEventId();
        int recordType2 = d4shVideoRecordFeedbackMsg.getD4shVideoRecord().getRecordType();
        for (int i5 = 0; i5 < this.d4shHomeRecordAdapter.getDateDataList().size(); i5++) {
            D4shDayItem d4shDayItem = this.d4shHomeRecordAdapter.getDateDataList().get(i5);
            int type = d4shDayItem.getType();
            if (type == 1) {
                D4shDailyFeeds.D4shDailyEat.ItemsBean itemsBean = (D4shDailyFeeds.D4shDailyEat.ItemsBean) d4shDayItem.getBean();
                if (recordType2 == d4shDayItem.getType() && itemsBean.getEventId().equals(eventId)) {
                    itemsBean.setIsNeedUploadVideo(0);
                    this.d4shHomeRecordAdapter.notifyDataSetChanged();
                    return;
                }
            } else if (type == 2) {
                D4shDailyFeeds.D4shDailyFeed.ItemsBean itemsBean2 = (D4shDailyFeeds.D4shDailyFeed.ItemsBean) d4shDayItem.getBean();
                if (recordType2 == d4shDayItem.getType() && itemsBean2.getEventId().equals(eventId)) {
                    itemsBean2.setIsNeedUploadVideo(0);
                    this.d4shHomeRecordAdapter.notifyDataSetChanged();
                    return;
                }
            } else if (type == 3) {
                D4shDailyFeeds.PetBean.ItemsBean itemsBean3 = (D4shDailyFeeds.PetBean.ItemsBean) d4shDayItem.getBean();
                if (recordType2 == d4shDayItem.getType() && itemsBean3.getEventId().equals(eventId)) {
                    itemsBean3.setIsNeedUploadVideo(0);
                    this.d4shHomeRecordAdapter.notifyDataSetChanged();
                    return;
                }
            } else if (type != 4) {
                continue;
            } else {
                D4shDailyFeeds.MoveBean.ItemsBeanX itemsBeanX = (D4shDailyFeeds.MoveBean.ItemsBeanX) d4shDayItem.getBean();
                if (recordType2 == d4shDayItem.getType() && itemsBeanX.getEventId().equals(eventId)) {
                    itemsBeanX.setIsNeedUploadVideo(0);
                    this.d4shHomeRecordAdapter.notifyDataSetChanged();
                    return;
                }
            }
        }
    }

    public void refreshDateData(D4shDailyFeeds d4shDailyFeeds) {
        this.d4DailyFeeds = d4shDailyFeeds;
        int i = 0;
        if (d4shDailyFeeds != null && d4shDailyFeeds.getFeed() != null) {
            if (this.d4shRecord.getTypeCode() == 0) {
                this.d4shHomeRecordAdapter = new D4shHomeRecordAdapter(this.mContext, this.d4shRecord.getDeviceId(), this.d4shRecord.getTypeCode(), this.d4shHomeRecordAdapter.getFilterRecordType(), this);
            } else {
                this.d4shHomeRecordAdapter = new D4hHomeRecordAdapter(this.mContext, this.d4shRecord.getDeviceId(), this.d4shRecord.getTypeCode(), this.d4shHomeRecordAdapter.getFilterRecordType(), this);
            }
            this.d4shHomeRecordAdapter.setD4DateFeedData(new D4shDateFeedData(d4shDailyFeeds.getFeed().get(0).getItems(), (d4shDailyFeeds.getEat() == null || d4shDailyFeeds.getEat().size() == 0) ? new ArrayList<>() : d4shDailyFeeds.getEat().get(0).getItems(), (d4shDailyFeeds.getPet() == null || d4shDailyFeeds.getPet().size() == 0) ? new ArrayList<>() : d4shDailyFeeds.getPet().get(0).getItems(), (d4shDailyFeeds.getMove() == null || d4shDailyFeeds.getMove().size() == 0) ? new ArrayList<>() : d4shDailyFeeds.getMove().get(0).getItems(), (d4shDailyFeeds.getCompare() == null || d4shDailyFeeds.getCompare().size() == 0) ? new ArrayList<>() : d4shDailyFeeds.getCompare().get(0).getItems(), d4shDailyFeeds.getFeed().get(0).getDay()));
            this.rvD4RecordView.setAdapter(this.d4shHomeRecordAdapter);
            while (true) {
                if (i < this.d4shHomeRecordAdapter.getDateDataList().size()) {
                    if (this.d4shHomeRecordAdapter.getDateDataList().get(i).getType() == 2) {
                        this.d4sViewLayout.setEatRecordPosition(i + 1);
                        break;
                    }
                    i++;
                } else {
                    this.d4sViewLayout.setEatRecordPosition(-1);
                    break;
                }
            }
            setD4shDeviceRecordTagTypeNumber(d4shDailyFeeds);
            this.rvD4RecordView.post(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView.33
                @Override // java.lang.Runnable
                public void run() {
                }
            });
            if (isLandscape()) {
                this.playerLandscapeView.refreshEatVideoDeviceRecord(d4shDailyFeeds, this.d4shRecord);
                return;
            }
            return;
        }
        if (this.d4shRecord.getTypeCode() == 0) {
            this.d4shHomeRecordAdapter = new D4shHomeRecordAdapter(this.mContext, this.d4shRecord.getDeviceId(), this.d4shRecord.getTypeCode(), this.d4shHomeRecordAdapter.getFilterRecordType(), this);
        } else {
            this.d4shHomeRecordAdapter = new D4hHomeRecordAdapter(this.mContext, this.d4shRecord.getDeviceId(), this.d4shRecord.getTypeCode(), this.d4shHomeRecordAdapter.getFilterRecordType(), this);
        }
        this.d4shHomeRecordAdapter.setD4DateFeedData(null);
        this.rvD4RecordView.setAdapter(this.d4shHomeRecordAdapter);
        this.d4shDeviceRecordTagViewGroup.setD4shDeviceRecordTypeNumber(0, 0, 0, 0, 0);
        this.d4shDeviceRecordTagViewGroupTopFixed.setD4shDeviceRecordTypeNumber(0, 0, 0, 0, 0);
    }

    public void setPromoteView(RelatedProductsInfor relatedProductsInfor) {
        this.relatedProductsInfor = relatedProductsInfor;
        ArrayList arrayList = new ArrayList();
        if (relatedProductsInfor != null && relatedProductsInfor.getPromote() != null) {
            arrayList.addAll(relatedProductsInfor.getPromote());
        }
        if (arrayList.size() == 0) {
            this.promoteView.setVisibility(8);
            return;
        }
        this.promoteView.setVisibility(0);
        this.promoteView.enableSwitchable("D4SH");
        this.promoteView.refreshPromoteData(arrayList);
        this.promoteView.setPromoteViewOnItemListener(new PromoteView.PromoteViewOnItemListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView.34
            @Override // com.petkit.android.activities.mall.widget.PromoteView.PromoteViewOnItemListener
            public void onItemClick(PromoteData promoteData) {
                EventBus.getDefault().post(promoteData);
            }
        });
    }

    private void setArcCenterView() {
        if (this.d4shRecord.getTypeCode() == 0) {
            setFeedDataCardViewData();
        } else {
            setD4hFeedDataCardViewData();
        }
        if (this.d4shRecord.getState().getPim() != 0 && this.d4shRecord.getState().getFeeding() == 1) {
            setFeedDataCardViewTitleFeedingStatus();
        } else if (this.d4shRecord.getState().getPim() != 0 && this.d4shRecord.getState().getFeeding() == 2) {
            setFeedDataCardViewTitleFeedingStatus();
        } else if (this.d4shRecord.getState().getPim() != 0 && this.d4shRecord.getState().getFeeding() == 3) {
            setFeedDataCardViewTitleFeedingStatus();
        }
        DeviceUtils.isZhCN(this.activity);
    }

    public void removeRecord() {
        this.deleteRecordWindow.dismiss();
        if (isLandscape()) {
            Context context = this.mContext;
            PetkitToast.showTopToast(context, context.getString(R.string.Delete_success), 0, 1);
            this.playerLandscapeView.deleteRecordSuccess();
        } else {
            D4shAbstractHomeRecordAdapter d4shAbstractHomeRecordAdapter = this.d4shHomeRecordAdapter;
            if (d4shAbstractHomeRecordAdapter != null) {
                d4shAbstractHomeRecordAdapter.removeItem(this.deletePosition);
            }
        }
    }

    private void openDeleteRecordWindow(String str) {
        if (getResources().getConfiguration().orientation == 2) {
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            DisplayMetrics displayMetrics = BaseApplication.displayMetrics;
            layoutParams.width = Math.min(displayMetrics.widthPixels, displayMetrics.heightPixels);
            layoutParams.height = -1;
            this.deleteRecordWindow = new NormalCenterTipWindow(this.mContext, new NormalCenterTipWindow.OnClickOk() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView.35
                @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
                public void onCancelClick() {
                }

                @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
                public void onOkClick() {
                    EventBus.getDefault().post(D4shHomeDeviceView.this.d4sRemoveData);
                }
            }, null, new SpannableString(str), layoutParams);
        } else {
            this.deleteRecordWindow = new NormalCenterTipWindow(this.mContext, new NormalCenterTipWindow.OnClickOk() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView.36
                @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
                public void onCancelClick() {
                }

                @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
                public void onOkClick() {
                    EventBus.getDefault().post(D4shHomeDeviceView.this.d4sRemoveData);
                }
            }, (String) null, str);
        }
        if (this.d4sRemoveData.getDeleteType() == 1) {
            this.deleteRecordWindow.setTvTipGray(true, this.mContext.getResources().getString(R.string.D4s_delete_work_record_little_tip));
        } else if (this.d4sRemoveData.getDeleteType() == 5) {
            this.deleteRecordWindow.setTvTipGray(true, this.mContext.getResources().getString(R.string.D4sh_del_compare_image_tips));
        } else if (this.d4sRemoveData.getDeleteType() == 3) {
            this.deleteRecordWindow.setTvTipGray(true, this.mContext.getResources().getString(R.string.D4sh_device_record_remove_video_event_prompt));
        } else {
            this.deleteRecordWindow.setTvTipGray(true, this.mContext.getResources().getString(R.string.D4s_delete_output_record_little_tip));
        }
        this.deleteRecordWindow.setSelectText(this.mContext.getResources().getString(R.string.Cancel), this.mContext.getResources().getString(R.string.Confirm));
        this.deleteRecordWindow.show(((D4shHomeActivity) this.mContext).getWindow().getDecorView());
    }

    public void showFoodWarnDialog() {
        int i;
        int i2;
        if (isLandscape()) {
            return;
        }
        String str = "";
        if (this.d4shRecord.getTypeCode() == 0) {
            ThreeChoicesWindow threeChoicesWindow = this.d4shFoodWarnWindow;
            if (threeChoicesWindow == null || !threeChoicesWindow.isShowing()) {
                int i3 = R.drawable.d4sh_buckets_lack_of_food_icon;
                SpannableString spannableString = new SpannableString(this.activity.getResources().getString(R.string.D4sh_buckets_insufficient_surplus_grain));
                if (this.d4shRecord.getState().getFood1() == 0 && this.d4shRecord.getState().getFood2() == 0) {
                    if (this.d4shRecord.getModelCode() == 2) {
                        i2 = R.drawable.d4sh_3_buckets_lack_of_food_icon;
                    } else if (this.d4shRecord.getModelCode() == 1) {
                        i2 = R.drawable.d4sh_2_buckets_lack_of_food_icon;
                    } else {
                        i2 = R.drawable.d4sh_buckets_lack_of_food_icon;
                    }
                    i3 = i2;
                    spannableString = new SpannableString(this.activity.getResources().getString(R.string.D4sh_buckets_lack_of_food_prompt));
                } else if (this.d4shRecord.getState().getFood1() == 0) {
                    if (this.d4shRecord.getModelCode() == 2) {
                        i3 = R.drawable.d4sh_3_bucket1_lack_of_food_icon;
                    } else if (this.d4shRecord.getModelCode() == 1) {
                        i3 = R.drawable.d4sh_2_bucket1_lack_of_food_icon;
                    } else {
                        i3 = R.drawable.d4sh_bucket1_lack_of_food_icon;
                    }
                    String str2 = String.format(this.activity.getResources().getString(R.string.D4sh_bucket1_lack_of_food_prompt), getResources().getString(R.string.Defalut_bucket1_name) + " ");
                    spannableString = TextUtil.getInstance().makePartStringSpannableInTotalString(new SpannableString(str2), str2, this.mContext.getResources().getString(R.string.Defalut_bucket1_name), this.mContext.getResources().getColor(R.color.d3_main_green), 16, true);
                } else if (this.d4shRecord.getState().getFood2() == 0) {
                    if (this.d4shRecord.getModelCode() == 2) {
                        i3 = R.drawable.d4sh_3_bucket2_lack_of_food_icon;
                    } else if (this.d4shRecord.getModelCode() == 1) {
                        i3 = R.drawable.d4sh_2_bucket2_lack_of_food_icon;
                    } else {
                        i3 = R.drawable.d4sh_bucket2_lack_of_food_icon;
                    }
                    String str3 = String.format(this.activity.getResources().getString(R.string.D4sh_bucket1_lack_of_food_prompt), getResources().getString(R.string.Defalut_bucket2_name) + " ");
                    spannableString = TextUtil.getInstance().makePartStringSpannableInTotalString(new SpannableString(str3), str3, this.mContext.getResources().getString(R.string.Defalut_bucket2_name), this.mContext.getResources().getColor(R.color.d4s_orange_three), 16, true);
                }
                SpannableString spannableString2 = spannableString;
                String string = this.activity.getResources().getString(R.string.Surplus_grain_early_warning_tips);
                if (this.d4shRecord.getSettings().getFoodWarn() == 1 && (this.d4shRecord.getState() == null || this.d4shRecord.getState().getPim() != 2)) {
                    str = string;
                }
                ThreeChoicesWindow threeChoicesWindow2 = new ThreeChoicesWindow(this.mContext, new ThreeChoicesWindow.OnClickThreeChoices() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView.37
                    @Override // com.petkit.android.activities.device.widget.ThreeChoicesWindow.OnClickThreeChoices
                    public void onClickFirstChoice() {
                        D4shHomeDeviceView.this.d4shFoodWarnWindow.dismiss();
                    }

                    @Override // com.petkit.android.activities.device.widget.ThreeChoicesWindow.OnClickThreeChoices
                    public void onClickSecondChoice() {
                        D4shHomeDeviceView.this.menuOnClickListener.onBottomMenuClick(MenuType.NO_MORE_REMIND);
                        D4shHomeDeviceView.this.d4shFoodWarnWindow.dismiss();
                    }

                    @Override // com.petkit.android.activities.device.widget.ThreeChoicesWindow.OnClickThreeChoices
                    public void onClickThirdChoice() {
                        D4shHomeDeviceView.this.menuOnClickListener.onBottomMenuClick(MenuType.NO_MORE_REMIND);
                        D4shHomeDeviceView.this.d4shFoodWarnWindow.dismiss();
                    }
                }, spannableString2, getResources().getString(R.string.I_know), getResources().getString(R.string.I_have_added_meal), getResources().getString(R.string.Not_remind));
                this.d4shFoodWarnWindow = threeChoicesWindow2;
                threeChoicesWindow2.setThreeChoicesTextColor(getResources().getColor(R.color.new_bind_blue), getResources().getColor(R.color.gray), getResources().getColor(R.color.gray));
                this.d4shFoodWarnWindow.setTitle(str);
                this.d4shFoodWarnWindow.setImage(i3);
                this.d4shFoodWarnWindow.show(this.activity.getWindow().getDecorView());
                return;
            }
            return;
        }
        if (this.d4shRecord.getTypeCode() == 1) {
            NormalCenterTipWindow normalCenterTipWindow = this.d4sFoodWarnWindow;
            if (normalCenterTipWindow == null || !normalCenterTipWindow.isShowing()) {
                if (this.d4shRecord.getModelCode() == 2) {
                    i = R.drawable.img_d4h3_empty_food;
                } else if (this.d4shRecord.getModelCode() == 1) {
                    i = R.drawable.d4h_2_buckets_lack_of_food_icon;
                } else {
                    i = R.drawable.d4h_buckets_lack_of_food_icon;
                }
                SpannableString spannableString3 = new SpannableString(this.activity.getResources().getString(R.string.No_food_prompt));
                String string2 = this.activity.getResources().getString(R.string.Surplus_grain_early_warning_tips);
                if (this.d4shRecord.getSettings().getFoodWarn() == 1 && (this.d4shRecord.getState() == null || this.d4shRecord.getState().getPim() != 2)) {
                    str = string2;
                }
                NormalCenterTipWindow normalCenterTipWindow2 = new NormalCenterTipWindow(this.activity, new NormalCenterTipWindow.OnClickOk() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView.38
                    @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
                    public void onOkClick() {
                        D4shHomeDeviceView.this.d4sFoodWarnWindow.dismiss();
                    }

                    @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
                    public void onCancelClick() {
                        D4shHomeDeviceView.this.menuOnClickListener.onBottomMenuClick(MenuType.NO_MORE_REMIND);
                    }
                }, getResources().getString(R.string.Surplus_grain_early_warning), spannableString3);
                this.d4sFoodWarnWindow = normalCenterTipWindow2;
                normalCenterTipWindow2.setTvTipGray(true, str);
                this.d4sFoodWarnWindow.setSelectText(getResources().getString(R.string.I_have_added_meal), getResources().getString(R.string.Feeder_i_know));
                this.d4sFoodWarnWindow.setImage(i);
                this.d4sFoodWarnWindow.showAtLocation(this.activity.getWindow().getDecorView(), 17, 0, 0);
            }
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
        if (this.d4shRecord.getState().getFeedState() != null) {
            Map<String, Integer> feedTimes = this.d4shRecord.getState().getFeedState().getFeedTimes();
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

    public void checkDeviceWarnState() {
        int iCalcWarnNum = calcWarnNum();
        if (iCalcWarnNum == 0) {
            this.d4shDeviceWarnMessageViewGroup.hideMainWarnMessageView();
            this.d4shDeviceWarnMessageViewGroup.hideOtherWarnMessageView();
        } else if (iCalcWarnNum == 1) {
            this.d4shDeviceWarnMessageViewGroup.hideOtherWarnMessageView();
            showFirstWarnTip();
        } else {
            this.d4shDeviceWarnMessageViewGroup.setOtherWarnMessageViewTwoButtonVisible(false);
            this.d4shDeviceWarnMessageViewGroup.setOtherWarnMessageIconVisible(true, true);
            this.d4shDeviceWarnMessageViewGroup.showOtherWarnMessageView(String.format(getResources().getString(R.string.Have_mistake_to_handle), String.valueOf(iCalcWarnNum - 1)));
            showFirstWarnTip();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:49:0x0139  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int calcWarnNum() {
        /*
            Method dump skipped, instruction units count: 351
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView.calcWarnNum():int");
    }

    private void showFirstWarnTip() {
        this.d4shDeviceWarnMessageViewGroup.setMainWarnMessageViewTwoButtonVisible(false);
        if (this.d4shRecord.getState().getPim() == 0) {
            this.d4shDeviceWarnMessageViewGroup.showMainWarnMessageView(this.mContext.getResources().getString(R.string.Device_off_line_tip));
            return;
        }
        if (!TextUtils.isEmpty(this.d4shRecord.getState().getErrorCode()) && this.d4shRecord.getState().getErrorLevel() == 1) {
            this.d4shDeviceWarnMessageViewGroup.showMainWarnMessageView(this.d4shRecord.getState().getErrorMsg());
            this.d4shDeviceWarnMessageViewGroup.setMainWarnMessageIconVisible(true, true);
            return;
        }
        if (!TextUtils.isEmpty(this.d4shRecord.getState().getErrorCode()) && this.d4shRecord.getState().getErrorLevel() != 1) {
            this.d4shDeviceWarnMessageViewGroup.showMainWarnMessageView(this.d4shRecord.getState().getErrorMsg());
            this.d4shDeviceWarnMessageViewGroup.setMainWarnMessageIconVisible(true, true);
            return;
        }
        if (this.d4shRecord.getTypeCode() == 1 && this.d4shRecord.getState().getFood() == 0) {
            this.d4shDeviceWarnMessageViewGroup.showMainWarnMessageView(this.activity.getResources().getString(R.string.No_food_prompt));
            this.d4shDeviceWarnMessageViewGroup.setMainWarnMessageIconVisible(true, true);
            return;
        }
        if (this.d4shRecord.getTypeCode() == 0 && ((this.d4shRecord.getState().getFood1() == 0 || this.d4shRecord.getState().getFood2() == 0 || this.d4shRecord.getState().getFood1() == 1 || this.d4shRecord.getState().getFood2() == 1) && this.d4shRecord.getSettings().getNoRemind() == 0)) {
            if (this.d4shRecord.getState().getFood1() == 1 && this.d4shRecord.getState().getFood2() == 1) {
                this.d4shDeviceWarnMessageViewGroup.showMainWarnMessageView(this.activity.getResources().getString(R.string.D4sh_buckets_insufficient_surplus_grain));
            } else if (this.d4shRecord.getState().getFood1() == 0 && this.d4shRecord.getState().getFood2() == 0) {
                this.d4shDeviceWarnMessageViewGroup.showMainWarnMessageView(this.activity.getResources().getString(R.string.D4sh_buckets_lack_of_food_prompt));
            } else if (this.d4shRecord.getState().getFood1() == 1 && this.d4shRecord.getState().getFood2() == 0) {
                SpannableString spannableString = new SpannableString(String.format(this.activity.getResources().getString(R.string.D4sh_buckets_lack_of_food_and_empty_prompt_one), getResources().getString(R.string.Defalut_bucket1_name) + " ", getResources().getString(R.string.Defalut_bucket2_name) + " "));
                SpannableString spannableStringMakePartStringSpannableInTotalString = TextUtil.getInstance().makePartStringSpannableInTotalString(spannableString, String.format(this.activity.getResources().getString(R.string.D4sh_buckets_lack_of_food_and_empty_prompt_one), getResources().getString(R.string.Defalut_bucket1_name) + " ", getResources().getString(R.string.Defalut_bucket2_name) + " "), getResources().getString(R.string.Defalut_bucket1_name), getResources().getColor(R.color.d3_main_green), 14, true);
                this.d4shDeviceWarnMessageViewGroup.showMainWarnMessageView(TextUtil.getInstance().makePartStringSpannableInTotalString(spannableStringMakePartStringSpannableInTotalString, String.format(this.activity.getResources().getString(R.string.D4sh_buckets_lack_of_food_and_empty_prompt_one), getResources().getString(R.string.Defalut_bucket1_name) + " ", getResources().getString(R.string.Defalut_bucket2_name) + " "), getResources().getString(R.string.Defalut_bucket2_name), getResources().getColor(R.color.d4s_orange_three), 14, true).toString());
            } else if (this.d4shRecord.getState().getFood1() == 0 && this.d4shRecord.getState().getFood2() == 1) {
                SpannableString spannableString2 = new SpannableString(String.format(this.activity.getResources().getString(R.string.D4sh_buckets_lack_of_food_and_empty_prompt_two), getResources().getString(R.string.Defalut_bucket1_name) + " ", getResources().getString(R.string.Defalut_bucket2_name) + " "));
                SpannableString spannableStringMakePartStringSpannableInTotalString2 = TextUtil.getInstance().makePartStringSpannableInTotalString(spannableString2, String.format(this.activity.getResources().getString(R.string.D4sh_buckets_lack_of_food_and_empty_prompt_two), getResources().getString(R.string.Defalut_bucket1_name) + " ", getResources().getString(R.string.Defalut_bucket2_name) + " "), getResources().getString(R.string.Defalut_bucket1_name), getResources().getColor(R.color.d3_main_green), 14, true);
                this.d4shDeviceWarnMessageViewGroup.showMainWarnMessageView(TextUtil.getInstance().makePartStringSpannableInTotalString(spannableStringMakePartStringSpannableInTotalString2, String.format(this.activity.getResources().getString(R.string.D4sh_buckets_lack_of_food_and_empty_prompt_two), getResources().getString(R.string.Defalut_bucket1_name) + " ", getResources().getString(R.string.Defalut_bucket2_name) + " "), getResources().getString(R.string.Defalut_bucket2_name), getResources().getColor(R.color.d4s_orange_three), 14, true).toString());
            } else if (this.d4shRecord.getState().getFood1() == 0) {
                SpannableString spannableString3 = new SpannableString(String.format(this.activity.getResources().getString(R.string.D4sh_bucket1_lack_of_food_prompt), getResources().getString(R.string.Defalut_bucket1_name) + " "));
                this.d4shDeviceWarnMessageViewGroup.showMainWarnMessageView(TextUtil.getInstance().makePartStringSpannableInTotalString(spannableString3, String.format(this.activity.getResources().getString(R.string.D4sh_bucket1_lack_of_food_prompt), getResources().getString(R.string.Defalut_bucket1_name) + " "), getResources().getString(R.string.Defalut_bucket1_name), getResources().getColor(R.color.d3_main_green), 14, true).toString());
            } else if (this.d4shRecord.getState().getFood2() == 0) {
                SpannableString spannableString4 = new SpannableString(String.format(this.activity.getResources().getString(R.string.D4sh_bucket1_lack_of_food_prompt), getResources().getString(R.string.Defalut_bucket2_name) + " "));
                this.d4shDeviceWarnMessageViewGroup.showMainWarnMessageView(TextUtil.getInstance().makePartStringSpannableInTotalString(spannableString4, String.format(this.activity.getResources().getString(R.string.D4sh_bucket1_lack_of_food_prompt), getResources().getString(R.string.Defalut_bucket2_name) + " "), getResources().getString(R.string.Defalut_bucket2_name), getResources().getColor(R.color.d4s_orange_three), 14, true).toString());
            } else if (this.d4shRecord.getState().getFood1() == 1) {
                SpannableString spannableString5 = new SpannableString(String.format(this.activity.getResources().getString(R.string.D4sh_bucket1_insufficient_surplus_grain), getResources().getString(R.string.Defalut_bucket1_name) + " "));
                this.d4shDeviceWarnMessageViewGroup.showMainWarnMessageView(TextUtil.getInstance().makePartStringSpannableInTotalString(spannableString5, String.format(this.activity.getResources().getString(R.string.D4s_bucket1_lack_of_food_prompt), getResources().getString(R.string.Defalut_bucket1_name) + " "), getResources().getString(R.string.Defalut_bucket1_name) + " ", getResources().getColor(R.color.d3_main_green), 14, true).toString());
            } else if (this.d4shRecord.getState().getFood2() == 1) {
                SpannableString spannableString6 = new SpannableString(String.format(this.activity.getResources().getString(R.string.D4sh_bucket1_insufficient_surplus_grain), getResources().getString(R.string.Defalut_bucket2_name) + " "));
                this.d4shDeviceWarnMessageViewGroup.showMainWarnMessageView(TextUtil.getInstance().makePartStringSpannableInTotalString(spannableString6, String.format(this.activity.getResources().getString(R.string.D4s_bucket1_lack_of_food_prompt), getResources().getString(R.string.Defalut_bucket2_name) + " "), getResources().getString(R.string.Defalut_bucket2_name) + " ", getResources().getColor(R.color.d4s_orange_three), 14, true).toString());
            }
            this.d4shDeviceWarnMessageViewGroup.setMainWarnMessageIconVisible(true, true);
            return;
        }
        if (this.d4shRecord.getState().getOta() == 1) {
            this.d4shDeviceWarnMessageViewGroup.setWarnMessageViewGroupStatus(null, Boolean.TRUE, this.activity.getResources().getString(R.string.Device_ota_prompt), null, Boolean.valueOf(this.d4shRecord.getDeviceShared() == null), null, null);
            this.d4shDeviceWarnMessageViewGroup.setWarnTextGravityLeft();
            this.d4shDeviceWarnMessageViewGroup.setMainWarnMessageIconVisible(false, false);
            return;
        }
        if (this.d4shRecord.getState().getBatteryStatus() == 2) {
            this.d4shDeviceWarnMessageViewGroup.showMainWarnMessageView(this.activity.getResources().getString(R.string.D3_low_power_prompt));
            this.d4shDeviceWarnMessageViewGroup.setMainWarnMessageIconVisible(true, false);
            return;
        }
        if (!CommonUtils.isSameTimeZoneAsLocal(this.d4shRecord.getLocale(), this.d4shRecord.getTimezone())) {
            this.d4shDeviceWarnMessageViewGroup.showMainWarnMessageView(this.activity.getResources().getString(R.string.Time_zone_is_different));
            this.d4shDeviceWarnMessageViewGroup.setMainWarnMessageIconVisible(true, true);
            return;
        }
        if (this.d4shRecord.getState().getDesiccantLeftDays() == 0) {
            if (!CommonUtils.getSysBoolMap(this.mContext, Consts.D4SH_IS_IGNORE_DESICCANT_TIP + this.d4shRecord.getDeviceId(), false)) {
                this.d4shDeviceWarnMessageViewGroup.showMainWarnMessageView(this.activity.getResources().getString(R.string.Desiccant_use_over_30_days));
                this.d4shDeviceWarnMessageViewGroup.setMainWarnMessageViewTwoButton(this.activity.getResources().getString(R.string.Invent_ignore), this.activity.getResources().getString(R.string.Reset), new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView.39
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        CommonUtils.addSysBoolMap(D4shHomeDeviceView.this.mContext, Consts.D4SH_IS_IGNORE_DESICCANT_TIP + D4shHomeDeviceView.this.d4shRecord.getDeviceId(), true);
                        D4shHomeDeviceView.this.checkDeviceWarnState();
                    }
                }, new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView.40
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        if (D4shHomeDeviceView.this.menuOnClickListener != null) {
                            D4shHomeDeviceView.this.menuOnClickListener.onBottomMenuClick(MenuType.CONSUMABLE_DESICCANT_REMIND);
                        }
                    }
                });
                this.d4shDeviceWarnMessageViewGroup.setMainWarnMessageViewTwoButtonVisible(true);
                this.d4shDeviceWarnMessageViewGroup.setMainWarnMessageIconVisible(false, false);
                return;
            }
        }
        if (this.d4shRecord.getState().getWifi().getRsq() <= -65 && this.d4shRecord.getState().getWifi().getRsq() > -75) {
            if (TimeUtils.getInstance().getCurrentSeconds() - CommonUtils.getSysLongMap(this.mContext, Consts.D4SH_WIFI_SIGINAL_WINDOW + this.d4shRecord.getDeviceId()) > 2592000) {
                this.d4shDeviceWarnMessageViewGroup.showMainWarnMessageView(this.activity.getResources().getString(R.string.Wifi_signal_weak));
                this.d4shDeviceWarnMessageViewGroup.setMainWarnMessageIconVisible(true, true);
                return;
            }
            return;
        }
        if (this.d4shRecord.getState().getWifi().getRsq() < -75) {
            this.d4shDeviceWarnMessageViewGroup.showMainWarnMessageView(this.activity.getResources().getString(R.string.Wifi_signal_weak));
            CommonUtils.addSysLongMap(this.mContext, Consts.D4SH_WIFI_SIGINAL_WINDOW + this.d4shRecord.getDeviceId(), 0L);
            this.d4shDeviceWarnMessageViewGroup.setMainWarnMessageIconVisible(true, true);
            return;
        }
        if (this.differentFeedPlan == null || this.differentFeedPlan.getFeedDailyList().get(Calendar.getInstance().get(7) - 1).getSuspended() == 0) {
            return;
        }
        D4shDeviceWarnMessageViewGroup d4shDeviceWarnMessageViewGroup = this.d4shDeviceWarnMessageViewGroup;
        Boolean bool = Boolean.TRUE;
        d4shDeviceWarnMessageViewGroup.setWarnMessageViewGroupStatus(bool, bool, null, null, null, null, null);
    }

    private void showSecondWarnTip() {
        this.d4shDeviceWarnMessageViewGroup.setOtherWarnMessageViewTwoButtonVisible(false);
        this.d4shDeviceWarnMessageViewGroup.setOtherWarnMessageIconVisible(true, true);
        int i = this.d4shRecord.getState().getPim() == 0 ? 1 : 0;
        if (!TextUtils.isEmpty(this.d4shRecord.getState().getErrorCode()) && this.d4shRecord.getState().getErrorLevel() == 1 && (i = i + 1) == 2) {
            this.d4shDeviceWarnMessageViewGroup.showOtherWarnMessageView(this.d4shRecord.getState().getErrorMsg());
        }
        if (!TextUtils.isEmpty(this.d4shRecord.getState().getErrorCode()) && this.d4shRecord.getState().getErrorLevel() != 1 && (i = i + 1) == 2) {
            this.d4shDeviceWarnMessageViewGroup.showOtherWarnMessageView(this.d4shRecord.getState().getErrorMsg());
        }
        if (this.d4shRecord.getTypeCode() == 1 && this.d4shRecord.getState().getFood() == 0 && (i = i + 1) == 2) {
            this.d4shDeviceWarnMessageViewGroup.showOtherWarnMessageView(this.activity.getResources().getString(R.string.No_food_prompt));
        }
        if (this.d4shRecord.getTypeCode() == 0 && ((this.d4shRecord.getState().getFood1() == 0 || this.d4shRecord.getState().getFood2() == 0 || this.d4shRecord.getState().getFood1() == 1 || this.d4shRecord.getState().getFood2() == 1) && this.d4shRecord.getSettings().getNoRemind() == 0)) {
            if (this.d4shRecord.getState().getFood1() == 1 && this.d4shRecord.getState().getFood2() == 1) {
                i++;
                if (i == 2) {
                    this.d4shDeviceWarnMessageViewGroup.showOtherWarnMessageView(this.activity.getResources().getString(R.string.D4sh_buckets_insufficient_surplus_grain));
                }
            } else if (this.d4shRecord.getState().getFood1() == 0 && this.d4shRecord.getState().getFood2() == 0) {
                i++;
                if (i == 2) {
                    this.d4shDeviceWarnMessageViewGroup.showOtherWarnMessageView(this.activity.getResources().getString(R.string.D4sh_buckets_lack_of_food_prompt));
                }
            } else if (this.d4shRecord.getState().getFood1() == 1 && this.d4shRecord.getState().getFood2() == 0) {
                i++;
                if (i == 2) {
                    SpannableString spannableString = new SpannableString(String.format(this.activity.getResources().getString(R.string.D4sh_buckets_lack_of_food_and_empty_prompt_one), getResources().getString(R.string.Defalut_bucket1_name) + " ", getResources().getString(R.string.Defalut_bucket2_name) + " "));
                    SpannableString spannableStringMakePartStringSpannableInTotalString = TextUtil.getInstance().makePartStringSpannableInTotalString(spannableString, String.format(this.activity.getResources().getString(R.string.D4sh_buckets_lack_of_food_and_empty_prompt_one), getResources().getString(R.string.Defalut_bucket1_name) + " ", getResources().getString(R.string.Defalut_bucket2_name) + " "), getResources().getString(R.string.Defalut_bucket1_name), getResources().getColor(R.color.d3_main_green), 14, true);
                    this.d4shDeviceWarnMessageViewGroup.showOtherWarnMessageView(TextUtil.getInstance().makePartStringSpannableInTotalString(spannableStringMakePartStringSpannableInTotalString, String.format(this.activity.getResources().getString(R.string.D4sh_buckets_lack_of_food_and_empty_prompt_one), getResources().getString(R.string.Defalut_bucket1_name) + " ", getResources().getString(R.string.Defalut_bucket2_name) + " "), getResources().getString(R.string.Defalut_bucket2_name), getResources().getColor(R.color.d4s_orange_three), 14, true).toString());
                }
            } else if (this.d4shRecord.getState().getFood1() == 0 && this.d4shRecord.getState().getFood2() == 1) {
                i++;
                if (i == 2) {
                    SpannableString spannableString2 = new SpannableString(String.format(this.activity.getResources().getString(R.string.D4sh_buckets_lack_of_food_and_empty_prompt_two), getResources().getString(R.string.Defalut_bucket1_name) + " ", getResources().getString(R.string.Defalut_bucket2_name) + " "));
                    SpannableString spannableStringMakePartStringSpannableInTotalString2 = TextUtil.getInstance().makePartStringSpannableInTotalString(spannableString2, String.format(this.activity.getResources().getString(R.string.D4sh_buckets_lack_of_food_and_empty_prompt_two), getResources().getString(R.string.Defalut_bucket1_name) + " ", getResources().getString(R.string.Defalut_bucket2_name) + " "), getResources().getString(R.string.Defalut_bucket1_name), getResources().getColor(R.color.d3_main_green), 14, true);
                    this.d4shDeviceWarnMessageViewGroup.showOtherWarnMessageView(TextUtil.getInstance().makePartStringSpannableInTotalString(spannableStringMakePartStringSpannableInTotalString2, String.format(this.activity.getResources().getString(R.string.D4sh_buckets_lack_of_food_and_empty_prompt_two), getResources().getString(R.string.Defalut_bucket1_name) + " ", getResources().getString(R.string.Defalut_bucket2_name) + " "), getResources().getString(R.string.Defalut_bucket2_name), getResources().getColor(R.color.d4s_orange_three), 14, true).toString());
                }
            } else if (this.d4shRecord.getState().getFood1() == 0) {
                i++;
                if (i == 2) {
                    SpannableString spannableString3 = new SpannableString(String.format(this.activity.getResources().getString(R.string.D4sh_bucket1_lack_of_food_prompt), getResources().getString(R.string.Defalut_bucket1_name) + " "));
                    this.d4shDeviceWarnMessageViewGroup.showOtherWarnMessageView(TextUtil.getInstance().makePartStringSpannableInTotalString(spannableString3, String.format(this.activity.getResources().getString(R.string.D4sh_bucket1_lack_of_food_prompt), getResources().getString(R.string.Defalut_bucket1_name) + " "), getResources().getString(R.string.Defalut_bucket1_name), getResources().getColor(R.color.d3_main_green), 14, true).toString());
                }
            } else if (this.d4shRecord.getState().getFood2() == 0) {
                i++;
                if (i == 2) {
                    SpannableString spannableString4 = new SpannableString(String.format(this.activity.getResources().getString(R.string.D4sh_bucket1_lack_of_food_prompt), getResources().getString(R.string.Defalut_bucket2_name) + " "));
                    this.d4shDeviceWarnMessageViewGroup.showOtherWarnMessageView(TextUtil.getInstance().makePartStringSpannableInTotalString(spannableString4, String.format(this.activity.getResources().getString(R.string.D4sh_bucket1_lack_of_food_prompt), getResources().getString(R.string.Defalut_bucket2_name) + " "), getResources().getString(R.string.Defalut_bucket2_name), getResources().getColor(R.color.d4s_orange_three), 14, true).toString());
                }
            } else if (this.d4shRecord.getState().getFood1() == 1) {
                i++;
                if (i == 2) {
                    SpannableString spannableString5 = new SpannableString(String.format(this.activity.getResources().getString(R.string.D4sh_bucket1_insufficient_surplus_grain), getResources().getString(R.string.Defalut_bucket1_name) + " "));
                    this.d4shDeviceWarnMessageViewGroup.showOtherWarnMessageView(TextUtil.getInstance().makePartStringSpannableInTotalString(spannableString5, String.format(this.activity.getResources().getString(R.string.D4s_bucket1_lack_of_food_prompt), getResources().getString(R.string.Defalut_bucket1_name) + " "), getResources().getString(R.string.Defalut_bucket1_name) + " ", getResources().getColor(R.color.d3_main_green), 14, true).toString());
                }
            } else if (this.d4shRecord.getState().getFood2() == 1 && (i = i + 1) == 2) {
                SpannableString spannableString6 = new SpannableString(String.format(this.activity.getResources().getString(R.string.D4sh_bucket1_insufficient_surplus_grain), getResources().getString(R.string.Defalut_bucket2_name) + " "));
                this.d4shDeviceWarnMessageViewGroup.showOtherWarnMessageView(TextUtil.getInstance().makePartStringSpannableInTotalString(spannableString6, String.format(this.activity.getResources().getString(R.string.D4s_bucket1_lack_of_food_prompt), getResources().getString(R.string.Defalut_bucket2_name) + " "), getResources().getString(R.string.Defalut_bucket2_name) + " ", getResources().getColor(R.color.d4s_orange_three), 14, true).toString());
            }
        }
        if (this.d4shRecord.getState().getOta() == 1 && (i = i + 1) == 2) {
            this.d4shDeviceWarnMessageViewGroup.showOtherWarnMessageView(this.activity.getResources().getString(R.string.Device_ota_prompt));
        }
        if (this.d4shRecord.getState().getBatteryStatus() == 2 && (i = i + 1) == 2) {
            this.d4shDeviceWarnMessageViewGroup.showOtherWarnMessageView(this.activity.getResources().getString(R.string.D3_low_power_prompt));
            this.d4shDeviceWarnMessageViewGroup.setOtherWarnMessageIconVisible(true, false);
        }
        if (!CommonUtils.isSameTimeZoneAsLocal(this.d4shRecord.getLocale(), this.d4shRecord.getTimezone()) && (i = i + 1) == 2) {
            this.d4shDeviceWarnMessageViewGroup.showOtherWarnMessageView(this.activity.getResources().getString(R.string.Time_zone_is_different));
        }
        if (this.d4shRecord.getState().getDesiccantLeftDays() == 0) {
            if (!CommonUtils.getSysBoolMap(this.mContext, Consts.D4SH_IS_IGNORE_DESICCANT_TIP + this.d4shRecord.getDeviceId(), false) && (i = i + 1) == 2) {
                this.d4shDeviceWarnMessageViewGroup.showOtherWarnMessageView(this.activity.getResources().getString(R.string.Desiccant_use_over_30_days));
                this.d4shDeviceWarnMessageViewGroup.setOtherWarnMessageViewTwoButton(this.activity.getResources().getString(R.string.Invent_ignore), this.activity.getResources().getString(R.string.Reset), new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView.41
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        CommonUtils.addSysBoolMap(D4shHomeDeviceView.this.mContext, Consts.D4SH_IS_IGNORE_DESICCANT_TIP + D4shHomeDeviceView.this.d4shRecord.getDeviceId(), true);
                        D4shHomeDeviceView.this.checkDeviceWarnState();
                    }
                }, new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView.42
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        if (D4shHomeDeviceView.this.menuOnClickListener != null) {
                            D4shHomeDeviceView.this.menuOnClickListener.onBottomMenuClick(MenuType.CONSUMABLE_DESICCANT_REMIND);
                        }
                    }
                });
                this.d4shDeviceWarnMessageViewGroup.setOtherWarnMessageViewTwoButtonVisible(true);
                this.d4shDeviceWarnMessageViewGroup.setOtherWarnMessageIconVisible(false, false);
            }
        }
        if (this.d4shRecord.getState().getWifi().getRsq() <= -65 && this.d4shRecord.getState().getWifi().getRsq() > -75) {
            if (TimeUtils.getInstance().getCurrentSeconds() - CommonUtils.getSysLongMap(this.mContext, Consts.D4SH_WIFI_SIGINAL_WINDOW + this.d4shRecord.getDeviceId()) > 2592000 && (i = i + 1) == 2) {
                this.d4shDeviceWarnMessageViewGroup.showOtherWarnMessageView(this.activity.getResources().getString(R.string.Wifi_signal_weak));
            }
        } else if (this.d4shRecord.getState().getWifi().getRsq() < -75) {
            i++;
            if (i == 2) {
                this.d4shDeviceWarnMessageViewGroup.showOtherWarnMessageView(this.activity.getResources().getString(R.string.Wifi_signal_weak));
            }
            CommonUtils.addSysLongMap(this.mContext, Consts.D4SH_WIFI_SIGINAL_WINDOW + this.d4shRecord.getDeviceId(), 0L);
        }
        if (this.differentFeedPlan == null || i + 1 != 2 || this.differentFeedPlan.getFeedDailyList().get(Calendar.getInstance().get(7) - 1).getSuspended() == 0) {
            return;
        }
        D4shDeviceWarnMessageViewGroup d4shDeviceWarnMessageViewGroup = this.d4shDeviceWarnMessageViewGroup;
        Boolean bool = Boolean.TRUE;
        d4shDeviceWarnMessageViewGroup.showOtherFeedPlanWarnMessageView(bool, bool, null, null, null, null, null);
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

    private void stopLeftAni() {
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

    public void showGuideView(View view) {
        if (this.guide1 != null) {
            return;
        }
        GuideBuilder guideBuilder = new GuideBuilder();
        guideBuilder.setTargetView(view).setAlpha(180).setHighTargetCorner(50).setHighTargetPaddingLeft(ArmsUtils.dip2px(this.mContext, 0.0f)).setHighTargetPaddingRight(ArmsUtils.dip2px(this.mContext, 0.0f)).setHighTargetPaddingBottom(ArmsUtils.dip2px(this.mContext, 0.0f)).setHighTargetPaddingTop(ArmsUtils.dip2px(this.mContext, 0.0f)).setOutsideTouchable(false).setAutoDismiss(true);
        guideBuilder.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView.43
            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onShown() {
            }

            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onDismiss() {
                DataHelper.setBooleanSF(D4shHomeDeviceView.this.mContext, Consts.D4S_EAT_RECORD_IS_FIRST, Boolean.TRUE);
                D4shHomeDeviceView.this.d4sViewLayout.fling(1);
                D4shHomeDeviceView.this.d4sViewLayout.setOnTouchListener(new View.OnTouchListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView.43.1
                    @Override // android.view.View.OnTouchListener
                    public boolean onTouch(View view2, MotionEvent motionEvent) {
                        return false;
                    }
                });
            }
        });
        guideBuilder.addComponent(new GuideToiletBtnAndLineComponent(new GuideParam(getResources().getString(R.string.Pet_feed_records_can_be_slip_deleted), 2, 32, 0, 10, this.mContext.getResources().getString(R.string.I_know), 0, R.layout.layout_toilet_guide), new ConfirmListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView.44
            @Override // com.petkit.android.activities.petkitBleDevice.w5.guide.ConfirmListener
            public void onConfirmListener() {
                if (D4shHomeDeviceView.this.guide1 != null) {
                    D4shHomeDeviceView.this.guide1.dismiss();
                }
            }
        }));
        this.guide1 = guideBuilder.createGuide();
        this.contentView.post(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView.45
            @Override // java.lang.Runnable
            public void run() {
                D4shHomeDeviceView.this.guide1.show((Activity) D4shHomeDeviceView.this.getContext());
            }
        });
    }

    @Override // com.petkit.android.activities.petkitBleDevice.widget.BleDeviceHomeMenuView.OnMenuClickListener
    public void onMenuClick(BleMenuItem bleMenuItem, int i) {
        if (System.currentTimeMillis() - this.moreLickTime < 500) {
            return;
        }
        this.moreLickTime = System.currentTimeMillis();
        MenuOnClickListener menuOnClickListener = this.menuOnClickListener;
        if (menuOnClickListener != null) {
            if (i == 0) {
                this.bleMenuView.showAiMenu();
                return;
            }
            if (i == 1) {
                if (this.d4shRecord.getState().getFeeding() == 1 || this.d4shRecord.getState().getFeeding() == 2 || this.d4shRecord.getState().getFeeding() == 3) {
                    this.menuOnClickListener.onBottomMenuClick(MenuType.STOP_FEED);
                    return;
                } else {
                    this.menuOnClickListener.onBottomMenuClick(MenuType.FEED_NOW);
                    return;
                }
            }
            if (i == 2) {
                D4shHomeDeviceViewListener d4shHomeDeviceViewListener = this.d4shHomeDeviceViewListener;
                if (d4shHomeDeviceViewListener != null) {
                    d4shHomeDeviceViewListener.onD4shDeviceHomeMenuMicroPhoneViewClick();
                    return;
                }
                return;
            }
            if (i == 3) {
                menuOnClickListener.onBottomMenuClick(MenuType.FEED_PLAN);
            } else {
                if (i != 4) {
                    return;
                }
                menuOnClickListener.onBottomMenuClick(MenuType.DEEP_ENERGY_SAVING);
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
        activity.startActivity(BleDeviceWifiSettingActivity.newIntent(activity, this.d4shRecord.getDeviceId(), this.d4shRecord.getTypeCode() == 0 ? 25 : 26, this.d4shRecord.getModelCode()));
    }

    @Override // com.petkit.android.activities.petkitBleDevice.widget.BleDeviceHomeOfflineWarnWindow.OfflineClickListener
    public void onClickReset() {
        Activity activity = this.activity;
        activity.startActivity(BleDeviceBindNetWorkActivity.newIntent((Context) activity, this.d4shRecord.getDeviceId(), this.d4shRecord.getTypeCode() == 0 ? 25 : 26, this.d4shRecord.getBtMac(), false, this.d4shRecord.getModelCode()));
    }

    @Override // com.petkit.android.activities.petkitBleDevice.d4sh.adapter.RecordOnClickListener
    public void onDelete(int i, int i2, int i3) {
        this.deletePosition = i3;
        this.d4sRemoveData.setEndTime(i);
        this.d4sRemoveData.setStartTime(i2);
        this.d4sRemoveData.setDeleteType(1);
        openDeleteRecordWindow(this.mContext.getResources().getString(R.string.D4s_delete_work_record_tip));
    }

    @Override // com.petkit.android.activities.petkitBleDevice.d4sh.adapter.RecordOnClickListener
    public void onDeleteCompareImage(int i, int i2, int i3) {
        this.deletePosition = i3;
        this.d4sRemoveData.setEndTime(i);
        this.d4sRemoveData.setStartTime(i2);
        this.d4sRemoveData.setDeleteType(5);
        openDeleteRecordWindow(this.mContext.getResources().getString(R.string.D4s_delete_work_record_tip));
    }

    @Override // com.petkit.android.activities.petkitBleDevice.d4sh.adapter.RecordOnClickListener
    public void onDeleteFeedRecord(int i, int i2) {
        this.deletePosition = i2;
        this.d4sRemoveData.setStartTime(i);
        this.d4sRemoveData.setDeleteType(2);
        openDeleteRecordWindow(this.mContext.getResources().getString(R.string.D4s_delete_work_record_tip));
    }

    @Override // com.petkit.android.activities.petkitBleDevice.d4sh.adapter.RecordOnClickListener
    public void onD4shHomeRecordAdapterDeleteVideoEvent(String str, int i) {
        this.deletePosition = i;
        this.d4sRemoveData.setEventId(str);
        this.d4sRemoveData.setDeleteType(3);
        openDeleteRecordWindow(String.format("%s", this.mContext.getString(R.string.D4sh_device_record_remove_video_event)));
    }

    @Override // com.petkit.android.activities.petkitBleDevice.d4sh.adapter.RecordOnClickListener
    public void onItemClick(D4shVideoRecord d4shVideoRecord) {
        D4shHomeDeviceViewListener d4shHomeDeviceViewListener = this.d4shHomeDeviceViewListener;
        if (d4shHomeDeviceViewListener != null) {
            d4shHomeDeviceViewListener.onD4shDeviceVideoRecordClick(d4shVideoRecord, this.d4shHomeRecordAdapter.getRecordList());
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.d4sh.adapter.RecordOnClickListener
    public void onTitleClick(D4shDayItem d4shDayItem) {
        D4shHomeDeviceViewListener d4shHomeDeviceViewListener = this.d4shHomeDeviceViewListener;
        if (d4shHomeDeviceViewListener != null) {
            d4shHomeDeviceViewListener.clickRecordPetName(d4shDayItem);
        }
    }

    public void changePet(UpdatePetEvent updatePetEvent) {
        if (updatePetEvent == null) {
            return;
        }
        D4shDailyFeeds.D4shDailyEat d4shDailyEat = this.d4shDailyEat;
        if (d4shDailyEat != null) {
            Iterator<D4shDailyFeeds.D4shDailyEat.ItemsBean> it = d4shDailyEat.getItems().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                D4shDailyFeeds.D4shDailyEat.ItemsBean next = it.next();
                if (updatePetEvent.getType() == 2) {
                    if (next.getEventId().equals(updatePetEvent.getEventId()) && updatePetEvent.getOldPetId().equals(next.getPetId())) {
                        next.setPetId(updatePetEvent.getPetId());
                    }
                }
            }
            refreshPetChart(this.d4shDailyEat);
        }
        D4shAbstractHomeRecordAdapter d4shAbstractHomeRecordAdapter = this.d4shHomeRecordAdapter;
        if (d4shAbstractHomeRecordAdapter != null) {
            d4shAbstractHomeRecordAdapter.updateOnePet(updatePetEvent.getOldPetId(), updatePetEvent.getPetId(), updatePetEvent.getEventId(), updatePetEvent.getType());
        }
        D4shHomePlayerLandscapeView d4shHomePlayerLandscapeView = this.playerLandscapeView;
        if (d4shHomePlayerLandscapeView != null) {
            d4shHomePlayerLandscapeView.updateOnePet(updatePetEvent.getOldPetId(), updatePetEvent.getPetId(), updatePetEvent.getEventId(), updatePetEvent.getType());
        }
    }

    public void refreshPetColor() {
        D4shAbstractHomeRecordAdapter d4shAbstractHomeRecordAdapter = this.d4shHomeRecordAdapter;
        if (d4shAbstractHomeRecordAdapter != null) {
            d4shAbstractHomeRecordAdapter.notifyDataSetChanged();
        }
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
        EventBus.getDefault().unregister(this);
        MainHandler mainHandler = this.mainHandler;
        if (mainHandler != null) {
            mainHandler.removeCallbacksAndMessages(null);
        }
        Disposable disposable = this.disposable;
        if (disposable != null && !disposable.isDisposed()) {
            this.disposable.dispose();
            this.disposable = null;
        }
        Disposable disposable2 = this.viewPagerDisposable;
        if (disposable2 != null && !disposable2.isDisposed()) {
            this.viewPagerDisposable.dispose();
            this.viewPagerDisposable = null;
        }
        super.onDetachedFromWindow();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isLandscape() {
        return getResources().getConfiguration().orientation == 2;
    }

    private void setD4hFeedDataCardViewData() {
        String amountFormat = D4Utils.getAmountFormat(this.d4shRecord.getState().getFeedState() != null ? this.d4shRecord.getState().getFeedState().getRealAmountTotal() : 0, this.d4shRecord.getSettings().getFactor());
        StringBuilder sb = new StringBuilder();
        "zh_CN".equalsIgnoreCase(UserInforUtils.getLanguageLocaleString(CommonUtils.getAppContext()));
        sb.append(amountFormat);
        this.d4shDeviceDataCardViewGroup.setDeviceDataCardViewGroupContent(new SpannableString(sb.toString()), null, null, null, null, Integer.valueOf(this.d4shRecord.getDeviceShared() != null ? 4 : 0));
    }

    private void setFeedDataCardViewData() {
        int realAmountTotal1;
        int realAmountTotal2;
        if (this.d4shRecord.getState().getFeedState() != null) {
            realAmountTotal1 = this.d4shRecord.getState().getFeedState().getRealAmountTotal1();
            realAmountTotal2 = this.d4shRecord.getState().getFeedState().getRealAmountTotal2();
        } else {
            realAmountTotal1 = 0;
            realAmountTotal2 = 0;
        }
        String allAmountFormat = D4sUtils.getAllAmountFormat(realAmountTotal1, this.d4shRecord.getSettings().getFactor1(), realAmountTotal2, this.d4shRecord.getSettings().getFactor2());
        StringBuilder sb = new StringBuilder();
        "zh_CN".equalsIgnoreCase(UserInforUtils.getLanguageLocaleString(CommonUtils.getAppContext()));
        sb.append(allAmountFormat);
        SpannableString spannableString = new SpannableString(sb.toString());
        allAmountFormat.length();
        sb.length();
        this.d4shDeviceDataCardViewGroup.setDeviceDataCardViewGroupContent(spannableString, null, null, null, null, Integer.valueOf(this.d4shRecord.getDeviceShared() != null ? 4 : 0));
    }

    private void setEatDataCardViewData(int i) {
        this.d4shDeviceDataCardViewGroup.setDeviceDataCardViewGroupContent(null, null, null, new SpannableString("" + i), null, Integer.valueOf(this.d4shRecord.getDeviceShared() != null ? 4 : 0));
    }

    private void setDesiccantDataCardViewData(int i) {
        if (i < 0) {
            i = 0;
        }
        String str = String.valueOf(i) + " /30 " + UnitUtils.getInstance().getUnitThroughNum(i, this.mContext.getString(R.string.Unit_day), this.mContext.getString(R.string.Unit_days));
        SpannableString spannableString = new SpannableString(str);
        int length = String.valueOf(i).length();
        int length2 = (String.valueOf(i) + " /30").length();
        int length3 = str.length();
        if (i > 0) {
            spannableString.setSpan(new TextAppearanceSpan(this.mContext, R.style.New_Style_Title_22_Light_Black_With_Bold), 0, length, 33);
            spannableString.setSpan(new TextAppearanceSpan(this.mContext, R.style.New_Style_Content_10_Light_Black), length, length2, 33);
            spannableString.setSpan(new TextAppearanceSpan(this.mContext, R.style.New_Style_Content_12_light_Gray), length2, length3, 33);
        } else {
            spannableString.setSpan(new TextAppearanceSpan(this.mContext, R.style.New_Style_Title_22_Warn_Red_With_Bold), 0, length, 33);
            spannableString.setSpan(new TextAppearanceSpan(this.mContext, R.style.New_Style_Content_10_Light_Black), length, length2, 33);
            spannableString.setSpan(new TextAppearanceSpan(this.mContext, R.style.New_Style_Content_12_light_Gray), length2, length3, 33);
        }
        this.d4shDeviceDataCardViewGroup.setDeviceDataCardViewGroupContent(null, null, null, null, spannableString, Integer.valueOf(this.d4shRecord.getDeviceShared() != null ? 4 : 0));
    }

    private void showFeedDataDetailInfoWindow() {
        if ((this.d4shDeviceDataCardViewGroup.getTop() + this.d4shDeviceDataCardViewGroup.getHeight()) - this.d4sViewLayout.getScrollY() > 0) {
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.width = -1;
            layoutParams.height = -2;
            if (this.d4shRecord.getTypeCode() == 0) {
                new D4shFeedDataDetailInfoWindow(this.mContext, this.d4shRecord, layoutParams, this).showAsDropDown(this.playerContainer, 0, ArmsUtils.dip2px(this.mContext, 4.0f));
            } else {
                new D4hFeedDataDetailInfoWindow(this.mContext, this.d4shRecord, layoutParams, this).showAsDropDown(this.playerContainer, 0, ArmsUtils.dip2px(this.mContext, 4.0f));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showVlogTipWindow(View view) {
        if (this.d4shRecord.getSettings().getAutoProduct() == 1 || (this.rvHighlights.getTop() + this.rvHighlights.getHeight()) - this.d4sViewLayout.getScrollY() <= 0 || view == null) {
            return;
        }
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.width = -1;
        layoutParams.height = -2;
        D4shVlogTipWindow d4shVlogTipWindow = this.d4shVlogTipWindow;
        if (d4shVlogTipWindow == null || !d4shVlogTipWindow.isShowing()) {
            D4shVlogTipWindow d4shVlogTipWindow2 = new D4shVlogTipWindow(this.mContext, layoutParams);
            this.d4shVlogTipWindow = d4shVlogTipWindow2;
            d4shVlogTipWindow2.setBackgroundDrawable(new BitmapDrawable());
            this.d4shVlogTipWindow.setOutsideTouchable(true);
            this.d4shVlogTipWindow.setFocusable(true);
            Activity activity = this.activity;
            StringBuilder sb = new StringBuilder();
            sb.append(com.petkit.android.utils.Constants.D4H_D4SH_VLOG_MAKE_TIP);
            sb.append(this.d4shRecord.getTypeCode() == 0 ? 25 : 26);
            sb.append("_");
            sb.append(this.d4shRecord.getDeviceId());
            sb.append("_");
            sb.append(D4shUtils.getTodayYYYYMMddFormatStr());
            DataHelper.setBooleanSF(activity, sb.toString(), Boolean.TRUE);
            this.mainHandler.postDelayed(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView.46
                @Override // java.lang.Runnable
                public void run() {
                    if (D4shHomeDeviceView.this.d4shVlogTipWindow == null || !D4shHomeDeviceView.this.d4shVlogTipWindow.isShowing()) {
                        return;
                    }
                    D4shHomeDeviceView.this.d4shVlogTipWindow.dismiss();
                }
            }, 3000L);
            this.d4shVlogTipWindow.showAsDropDown(view, 0, ArmsUtils.dip2px(this.mContext, 6.0f));
        }
    }

    public void setD4shDeviceRecordDateTextViewText(String str, String str2, String str3) {
        this.d4shDeviceRecordDateStr = str;
        this.d4shDeviceRecordDateText = str2;
        this.d4shDeviceRecordTagViewGroup.setD4shDeviceRecordDateTextViewText(str2);
        this.tvMealtimeDate.setText(str2);
        this.tvTodayEatStr.setText(str3);
        this.d4shDeviceRecordTagViewGroupTopFixed.setD4shDeviceRecordDateTextViewText(str2);
    }

    public void setToolbarData(final D4shRecord d4shRecord) {
        if (d4shRecord.getDeviceShared() == null) {
            String name = d4shRecord.getName();
            if (!TextUtils.isEmpty(name)) {
                this.toolbarTitle.setText(name);
            } else if (d4shRecord.getTypeCode() == 0) {
                if (d4shRecord.getModelCode() == 2) {
                    this.toolbarTitle.setText(this.mContext.getString(R.string.D4SH3_name_default));
                } else {
                    this.toolbarTitle.setText(this.mContext.getString(R.string.D4SH_name_default));
                }
            } else if (d4shRecord.getModelCode() == 2) {
                this.toolbarTitle.setText(this.mContext.getString(R.string.D4H3_name_default));
            } else {
                this.toolbarTitle.setText(this.mContext.getString(R.string.D4H_name_default));
            }
        } else {
            String name2 = d4shRecord.getName();
            if (!TextUtils.isEmpty(name2)) {
                this.toolbarTitle.setText(d4shRecord.getDeviceShared().getOwnerNick() + "-" + name2);
            } else {
                this.toolbarTitle.setText(d4shRecord.getDeviceShared().getOwnerNick() + "-" + this.mContext.getString(R.string.D4SH_name_default));
            }
        }
        this.imgD4Setting.setImageResource(DeviceCenterUtils.isD4shNeedOtaById(this.d4shRecord.getDeviceId()) ? R.drawable.petkit_device_home_new_style_setting_red_point_icon : R.drawable.petkit_device_home_new_style_setting_icon);
        this.imgD4Setting.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView.47
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                D4shHomeDeviceView.this.mContext.startActivity(D4shSettingActivity.newIntent(D4shHomeDeviceView.this.mContext, d4shRecord.getDeviceId(), d4shRecord.getTypeCode(), D4shHomeDeviceView.this.relatedProductsInfor));
            }
        });
        this.toolbarBack.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView.48
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((BaseActivity) D4shHomeDeviceView.this.activity).killMyself();
            }
        });
    }

    public void showHowlingWindow() {
        if (isLandscape()) {
            return;
        }
        SpannableStringColorsPicPromptWindow spannableStringColorsPicPromptWindow = this.window;
        if (spannableStringColorsPicPromptWindow == null || !spannableStringColorsPicPromptWindow.isShowing()) {
            int i = R.drawable.d4sh_howling_tip;
            if (this.d4shRecord.getTypeCode() == 0) {
                if (this.d4shRecord.getModelCode() == 0) {
                    i = R.drawable.d4sh_howling_tip;
                } else if (this.d4shRecord.getModelCode() == 1) {
                    i = R.drawable.d4sh_2_howling_tip;
                } else if (this.d4shRecord.getModelCode() == 2) {
                    i = R.drawable.d4sh_3_howling_tip;
                }
            } else if (this.d4shRecord.getTypeCode() == 1) {
                if (this.d4shRecord.getModelCode() == 0) {
                    i = R.drawable.d4h_howling_tip;
                } else if (this.d4shRecord.getModelCode() == 1) {
                    i = R.drawable.d4h_2_howling_tip;
                } else if (this.d4shRecord.getModelCode() == 2) {
                    i = R.drawable.d4h_3_howling_tip;
                }
            }
            SpannableStringColorsPicPromptWindow spannableStringColorsPicPromptWindow2 = new SpannableStringColorsPicPromptWindow(this.mContext, getResources().getString(R.string.Prompt), getResources().getString(R.string.D4sh_tip_prompt_two), getResources().getString(R.string.D4sh_tip_prompt_one), getResources().getString(R.string.Feeder_i_know), i, new SpannableStringColorsPicPromptWindow.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView.49
                @Override // com.petkit.android.activities.universalWindow.SpannableStringColorsPicPromptWindow.OnClickListener
                public void onCancel() {
                }

                @Override // com.petkit.android.activities.universalWindow.SpannableStringColorsPicPromptWindow.OnClickListener
                public void onConfirm() {
                }
            }, new String[0]);
            this.window = spannableStringColorsPicPromptWindow2;
            spannableStringColorsPicPromptWindow2.show(this.activity.getWindow().getDecorView());
            DataHelper.setBooleanSF(this.activity, com.petkit.android.utils.Constants.D4SH_HOWLING_FLAG + this.d4shRecord.getDeviceId() + this.d4shRecord.getTypeCode(), Boolean.TRUE);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setD4shDeviceRecordTagViewGroupTopFixedVisible() {
        this.d4shDeviceRecordTagViewGroup.post(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$setD4shDeviceRecordTagViewGroupTopFixedVisible$12();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setD4shDeviceRecordTagViewGroupTopFixedVisible$12() {
        int[] iArr = new int[2];
        this.d4shDeviceRecordTagViewGroupTopFixed.getLocationOnScreen(iArr);
        int[] iArr2 = new int[2];
        this.d4shDeviceRecordTagViewGroup.getLocationOnScreen(iArr2);
        if (iArr2[1] < iArr[1]) {
            this.d4shDeviceRecordTagViewGroupTopFixed.setVisibility(0);
        } else {
            this.d4shDeviceRecordTagViewGroupTopFixed.setVisibility(8);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setD4shDeviceRecordAdapterTopVideoItemInScreen() {
        if (this.d4shHomeRecordAdapter.getItemCount() <= 0 || this.rvD4RecordView.getLayoutManager() == null) {
            return;
        }
        int iFindFirstVisibleItemPosition = ((LinearLayoutManager) this.rvD4RecordView.getLayoutManager()).findFirstVisibleItemPosition();
        PetkitLog.d(getClass().getSimpleName(), "D4shRecordList firstVisibleItemPosition:" + iFindFirstVisibleItemPosition);
        Rect rect = new Rect();
        this.activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        int i = rect.top;
        while (iFindFirstVisibleItemPosition <= this.d4shHomeRecordAdapter.getItemCount() - 1) {
            View viewFindViewByPosition = this.rvD4RecordView.getLayoutManager().findViewByPosition(iFindFirstVisibleItemPosition);
            if (viewFindViewByPosition != null) {
                int[] iArr = new int[2];
                viewFindViewByPosition.getLocationOnScreen(iArr);
                int i2 = iArr[1] - i;
                if (i2 >= this.d4shDeviceRecordTagViewGroupTopFixed.getHeight() && i2 + viewFindViewByPosition.getHeight() <= this.d4sViewLayout.getHeight() - ArmsUtils.dip2px(this.mContext, 100.0f)) {
                    this.d4shHomeRecordAdapter.setD4shDeviceRecordTopVideoInScreenIndex(iFindFirstVisibleItemPosition);
                    return;
                }
            }
            iFindFirstVisibleItemPosition++;
        }
    }

    public void setConfiguration(@NonNull Configuration configuration) {
        this.mainHandler.removeCallbacks(this.loadFullVideoRunnable);
        this.player.setConfiguration(configuration, this.verticalLiveViewHeight - ArmsUtils.dip2px(this.mContext, 56.0f));
        setPortraitScreenViewVisible(Boolean.valueOf(configuration.orientation == 1));
        if (configuration.orientation == 2) {
            this.rl_live_panel.setBackgroundResource(R.color.dark_black);
            this.vpLive.setPagingEnabled(false);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.vpLive.getLayoutParams();
            layoutParams.height = this.screenWidth;
            this.vpLive.setLayoutParams(layoutParams);
            RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) this.playerContainer.getLayoutParams();
            layoutParams2.setMargins(0, 0, 0, 0);
            int i = getResources().getDisplayMetrics().widthPixels;
            layoutParams2.height = this.screenWidth;
            layoutParams2.width = i;
            this.playerContainer.setLayoutParams(layoutParams2);
            this.rlMask.setVisibility(8);
            this.d4shDeviceDataCardViewGroup.setVisibility(8);
            this.liveIndicator.setVisibility(8);
            this.rlD4shRecordView.setVisibility(8);
            this.llVerticalDataView.setVisibility(8);
            this.player.cancelCorners();
            this.playerPortraitView.hideOneself();
            this.playerLandscapeView.showOneself(null, null);
            landscapeVisibleCheck();
            this.promoteView.setVisibility(8);
            return;
        }
        this.rl_live_panel.setBackgroundResource(R.color.transparent);
        this.vpLive.setPagingEnabled(true);
        RelativeLayout.LayoutParams layoutParams3 = (RelativeLayout.LayoutParams) this.vpLive.getLayoutParams();
        layoutParams3.height = this.verticalLiveViewHeight;
        this.vpLive.setLayoutParams(layoutParams3);
        RelativeLayout.LayoutParams layoutParams4 = (RelativeLayout.LayoutParams) this.playerContainer.getLayoutParams();
        layoutParams4.setMargins(ArmsUtils.dip2px(this.activity, 16.0f), ArmsUtils.dip2px(this.activity, 56.0f), ArmsUtils.dip2px(this.activity, 16.0f), 0);
        this.playerContainer.setLayoutParams(layoutParams4);
        this.d4shDeviceDataCardViewGroup.setVisibility(0);
        this.liveIndicator.setVisibility(0);
        this.rlD4shRecordView.setVisibility(0);
        this.llVerticalDataView.setVisibility(0);
        this.rlMask.setVisibility(0);
        this.player.initCorners();
        this.playerPortraitView.showOneself(null);
        this.playerLandscapeView.hideOneself();
        visibleCheck();
        this.promoteView.setVisibility(0);
    }

    private void setPortraitScreenViewVisible(final Boolean bool) {
        if (bool != null) {
            this.toolbar.setVisibility(bool.booleanValue() ? 0 : 8);
            this.bleMenuView.setVisibility(bool.booleanValue() ? 0 : 4);
            this.d4sViewLayout.post(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$setPortraitScreenViewVisible$13(bool);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setPortraitScreenViewVisible$13(Boolean bool) {
        this.d4sViewLayout.smoothScrollTo(0, 0);
        this.d4sViewLayout.setDisableScroll(!bool.booleanValue());
    }

    public void setVolumeIcon(boolean z) {
        this.playerPortraitView.setMute(z);
        if (!z) {
            this.playerLandscapeView.setVolumeImageResource(R.drawable.d4sh_volume_icon);
        } else {
            this.playerLandscapeView.setVolumeImageResource(R.drawable.d4sh_mute_icon);
        }
    }

    public void setRecordTimeText(String str) {
        this.playerLandscapeView.setRecordTimeText(null, str);
    }

    public void deleteRecordingFile() {
        if (!TextUtils.isEmpty(this.player.getRecordFilePath())) {
            File file = new File(this.player.getRecordFilePath());
            if (file.exists()) {
                file.delete();
            }
        }
        if (TextUtils.isEmpty(this.newPath)) {
            return;
        }
        File file2 = new File(this.newPath);
        if (file2.exists()) {
            file2.delete();
        }
    }

    public void setDatePickerViewDateChangeListener(BasePetkitDeviceDatePickerView.OnCalendarChangeListener onCalendarChangeListener) {
        this.onCalendarChangeListener = onCalendarChangeListener;
    }

    public void showDatePickerWindow(String str) {
        if (isLandscape()) {
            this.playerLandscapeView.showDatePickerWindow(this.d4shRecord.getCreatedAt(), str, this.d4shRecord.getTypeCode() == 0 ? 25 : 26, this.d4shRecord.getDeviceId(), this.onCalendarChangeListener);
            return;
        }
        DatePickerWindow datePickerWindow = this.datePickerWindow;
        if (datePickerWindow == null || !datePickerWindow.isShowing()) {
            DatePickerWindow datePickerWindow2 = new DatePickerWindow(this.mContext, this.d4shRecord.getCreatedAt(), str, this.d4shRecord.getTypeCode() == 0 ? 25 : 26, this.d4shRecord.getDeviceId(), this.onCalendarChangeListener, true);
            this.datePickerWindow = datePickerWindow2;
            datePickerWindow2.showAtLocation(this.activity.getWindow().getDecorView(), 80, 0, 0);
        }
    }

    public void refreshDatePickerViewSelectDate() {
        if (isLandscape()) {
            this.playerLandscapeView.refreshDatePickerSelectDate();
            return;
        }
        DatePickerWindow datePickerWindow = this.datePickerWindow;
        if (datePickerWindow == null || !datePickerWindow.isShowing()) {
            return;
        }
        this.datePickerWindow.refreshData();
    }

    public void dismissDatePickerWindow() {
        if (isLandscape()) {
            this.playerLandscapeView.dismissDatePickerWindowAfterSelectDate();
            this.playerLandscapeView.showEatVideoWindow(this.d4DailyFeeds, this.d4shDeviceRecordDateStr, this.d4shDeviceRecordDateText, this.d4shRecord);
            return;
        }
        DatePickerWindow datePickerWindow = this.datePickerWindow;
        if (datePickerWindow == null || !datePickerWindow.isShowing()) {
            return;
        }
        this.datePickerWindow.dismiss();
    }

    public void setPlayerSoundWaveViewVisible(Boolean bool) {
        if (bool != null) {
            if (isLandscape()) {
                this.playerLandscapeView.setPlayerSoundWaveViewStatus(bool, null);
            } else {
                this.playerSoundWaveView.setVisibility(bool.booleanValue() ? 0 : 4);
                setGrayCoverWhenShowSoundWaveView(bool);
            }
        }
    }

    public void setAllPlayerSoundWaveViewVisible(Boolean bool) {
        if (bool != null) {
            this.playerLandscapeView.setPlayerSoundWaveViewStatus(bool, null);
            this.playerSoundWaveView.setVisibility(bool.booleanValue() ? 0 : 4);
            setGrayCoverWhenShowSoundWaveView(bool);
        }
    }

    public void setVoiceLineVolume(Integer num) {
        if (num != null) {
            if (isLandscape()) {
                this.playerLandscapeView.setPlayerSoundWaveViewStatus(null, num);
            } else {
                this.playerSoundWaveView.setVoiceLineVolume(num.intValue());
            }
        }
    }

    public void setGrayCoverWhenShowSoundWaveView(Boolean bool) {
        if (bool != null) {
            this.d4shDeviceWarnMessageViewGroup.setAlpha(1.0f);
            this.d4shDeviceDataCardViewGroup.setAlpha(1.0f);
            this.d4shDeviceRecordTagViewGroup.setAlpha(1.0f);
            this.d4shDeviceRecordTagViewGroupTopFixed.setAlpha(1.0f);
            this.rvD4RecordView.setAlpha(1.0f);
        }
    }

    private void resetTagViewGroupSelectionType(int i) {
        if (this.d4shDeviceRecordTagViewGroupTopFixed.getVisibility() == 8) {
            this.d4shDeviceRecordTagViewGroupTopFixed.setD4shDeviceRecordTypeNumberViewHighLightStatus(i);
        } else {
            this.d4shDeviceRecordTagViewGroup.setD4shDeviceRecordTypeNumberViewHighLightStatus(i);
        }
    }

    public void startRecord(String str) {
        this.player.startRecord(str);
        D4shHomePlayerLandscapeView d4shHomePlayerLandscapeView = this.playerLandscapeView;
        Boolean bool = Boolean.TRUE;
        d4shHomePlayerLandscapeView.setRecordBtnImageResource(bool, R.drawable.recording_icon);
        this.playerLandscapeView.setRecordTimeText(bool, null);
    }

    public void stopRecord() {
        String strStopRecord = this.player.stopRecord();
        if (TextUtils.isEmpty(strStopRecord)) {
            return;
        }
        String strSaveFileToExternalStorage = FileUtil.saveFileToExternalStorage(this.activity, new File(strStopRecord), VideoDownloadManager.getLocalVideoPath());
        this.newPath = strSaveFileToExternalStorage;
        MediaScannerConnection.scanFile(this.mContext, new String[]{strSaveFileToExternalStorage}, null, new MediaScannerConnection.OnScanCompletedListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView.50
            @Override // android.media.MediaScannerConnection.OnScanCompletedListener
            public void onScanCompleted(String str, Uri uri) {
            }
        });
        D4shHomePlayerLandscapeView d4shHomePlayerLandscapeView = this.playerLandscapeView;
        Boolean bool = Boolean.FALSE;
        d4shHomePlayerLandscapeView.setRecordBtnImageResource(bool, R.drawable.d4sh_player_landscape_record_icon);
        this.playerLandscapeView.setRecordTimeText(bool, null);
    }

    public void downloadShortVideoSuccess(MediaMsg mediaMsg) {
        this.playerLandscapeView.downloadShortVideoSuccess(mediaMsg);
    }

    public void exitFullScreenMode() {
        this.mainHandler.removeCallbacks(this.loadFullVideoRunnable);
        this.player.switchFullOrWindowMode(50, false);
    }

    public boolean playLiveVideo() {
        return (isLandscape() && this.playerLandscapeView.getVideoType() == 2) ? false : true;
    }

    public void setPlayerStatus(int i) {
        this.d4shPlayerStatus = i;
    }

    public int getPlayerStatus() {
        return this.d4shPlayerStatus;
    }

    public void pausePlay() {
        this.player.pausePlay();
    }

    public void startVideo(String str, int i) {
        this.videoExpireOrLoss = false;
        this.player.startVideo(str, true, this.eatVideoTimesSpeed, i);
    }

    public void startMultipleVideo(List<PetkitVideoSegment> list) {
        this.videoExpireOrLoss = false;
        this.player.startMultipleVideo(list, this.eatVideoTimesSpeed);
    }

    public void setVideoSegmentList(List<PetkitVideoSegment> list) {
        int iMax;
        if (!list.isEmpty()) {
            this.playerLandscapeView.setVideoSegmentListSize(list.size());
            if (list.size() > 1) {
                this.playerLandscapeView.initTotalBarCount(list.size(), new PetkitDeviceVideoRecordListViewGroup.VideoRecordListViewListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView.51
                    @Override // com.petkit.android.activities.petkitBleDevice.widget.PetkitDeviceVideoRecordListViewGroup.VideoRecordListViewListener
                    public void onClickItem(int i) {
                        D4shHomeDeviceView.this.player.videoChange(i);
                    }
                });
                startMultipleVideo(list);
                return;
            }
            if (System.currentTimeMillis() / 1000 > list.get(0).getExpire()) {
                D4shHomeDeviceViewListener d4shHomeDeviceViewListener = this.d4shHomeDeviceViewListener;
                if (d4shHomeDeviceViewListener != null) {
                    d4shHomeDeviceViewListener.cancelLiveLoadTimeoutMonitoring();
                    this.d4shHomeDeviceViewListener.cancelVideoLoadTimeoutMonitoring();
                    this.d4shHomeDeviceViewListener.cancelFetchVideoSegmentDisposable();
                }
                this.videoExpireOrLoss = true;
                hideLoadingView();
                this.player.addCoverWithBackgroundView(Integer.valueOf(R.layout.petkit_live_player_full_video_expired_cover_view), Integer.valueOf(R.layout.petkit_player_gray_black_background_cover_view));
                this.player.findViewById(R.id.camera_unavailable_prompt_view).setBackground(this.mContext.getResources().getDrawable(R.color.dark_black));
                return;
            }
            this.player.clearCoverView();
            if (this.recordStartTime > list.get(0).getStartTime()) {
                iMax = Math.max(0, (((int) (((long) this.recordStartTime) - list.get(0).getStartTime())) * 1000) - 2000);
                PetkitLog.d(getClass().getSimpleName(), "video start time:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(list.get(0).getStartTime() * 1000)) + ",record start time:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(this.recordStartTime * 1000)) + ",progress:" + iMax);
                LogcatStorageHelper.addLog("video start time:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(list.get(0).getStartTime() * 1000)) + ",record start time:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date((long) (this.recordStartTime * 1000))) + ",progress:" + iMax);
            } else {
                iMax = 0;
            }
            startVideo(list.get(0).getMediaApi(), iMax);
            return;
        }
        D4shHomeDeviceViewListener d4shHomeDeviceViewListener2 = this.d4shHomeDeviceViewListener;
        if (d4shHomeDeviceViewListener2 != null) {
            d4shHomeDeviceViewListener2.cancelLiveLoadTimeoutMonitoring();
            this.d4shHomeDeviceViewListener.cancelVideoLoadTimeoutMonitoring();
            this.d4shHomeDeviceViewListener.cancelFetchVideoSegmentDisposable();
        }
        this.videoExpireOrLoss = true;
        hideLoadingView();
        this.player.addCoverWithBackgroundView(Integer.valueOf(R.layout.petkit_player_video_loss_cover_view), Integer.valueOf(R.layout.petkit_player_gray_black_background_cover_view));
        this.player.findViewById(R.id.camera_unavailable_prompt_view).setBackground(this.mContext.getResources().getDrawable(R.color.dark_black));
    }

    public boolean getVideoExpireOrLoss() {
        return this.videoExpireOrLoss;
    }

    public boolean getServiceStatusMaybeChanged() {
        return this.serviceStatusMaybeChanged;
    }

    public void refreshFullVideoStatus() {
        this.playerLandscapeView.refreshFullVideoStatus();
        this.serviceStatusMaybeChanged = false;
    }

    public int getVideoType() {
        return this.playerLandscapeView.getVideoType();
    }

    public void setCloudVideoTimesSpeedType(int i) {
        this.playerLandscapeView.setCloudVideoTimesSpeedType(i);
    }

    public int getEatVideoTimesSpeed() {
        return (int) this.eatVideoTimesSpeed;
    }

    @Override // com.petkit.android.media.video.player.BasePetkitPlayerPortraitViewClickListener
    public void onPlayerPortraitViewClick() {
        if (this.d4shPlayerStatus == 7) {
            Context context = this.mContext;
            PetkitToast.showTopToast(context, context.getString(R.string.Turn_on_camera_and_try_again), 0, 1);
        } else {
            this.player.switchFullOrWindowMode(50, false);
        }
    }

    @Override // com.petkit.android.media.video.player.BasePetkitPlayerPortraitViewClickListener
    public void onVolumeBtnClick() {
        D4shHomeDeviceViewListener d4shHomeDeviceViewListener = this.d4shHomeDeviceViewListener;
        if (d4shHomeDeviceViewListener != null) {
            d4shHomeDeviceViewListener.onD4shDeviceVolumeBtnClick();
        }
    }

    @Override // com.petkit.android.media.video.player.BasePetkitPlayerPortraitViewClickListener
    public void onPrivacyModePlayBtnClick() {
        this.playVideoInPrivacyMode = true;
        this.player.clearCoverView(2);
        setPlayerStatus(null, 1);
    }

    @Override // com.petkit.android.media.video.player.BasePetkitPlayerLandscapeViewClickListener
    public void onExitFullScreenBtnClick() {
        exitFullScreenMode();
    }

    @Override // com.petkit.android.media.video.player.BasePetkitPlayerLandscapeViewClickListener
    public void onLandscapeQualityBtnClick(int i) {
        D4shHomeDeviceViewListener d4shHomeDeviceViewListener = this.d4shHomeDeviceViewListener;
        if (d4shHomeDeviceViewListener != null) {
            d4shHomeDeviceViewListener.onVideoQualityChange(i);
        }
    }

    @Override // com.petkit.android.media.video.player.BasePetkitPlayerLandscapeViewClickListener
    public void onLandscapeTimeSpeedBtnClick(int i) {
        float f = i;
        this.eatVideoTimesSpeed = f;
        this.player.setTimesSpeed(f);
    }

    @Override // com.petkit.android.media.video.player.BasePetkitPlayerLandscapeViewClickListener
    public void onLandscapeVolumeBtnClick() {
        PetkitLog.d("onLandscapeVolumeBtnClick", "isLandscape:" + isLandscape() + " playerLandscapeView.getVideoType:" + this.playerLandscapeView.getVideoType() + " player.getPosition:" + this.player.getPosition());
        if (isLandscape() && this.playerLandscapeView.getVideoType() == 2 && this.player.getPosition() >= 0) {
            this.player.switchMuteVolume();
            setVolumeIcon(this.player.isMute());
        } else if (isLandscape() && this.playerLandscapeView.getVideoType() == 2) {
            this.player.switchMuteVolume();
            setVolumeIcon(this.player.isMute());
        } else {
            D4shHomeDeviceViewListener d4shHomeDeviceViewListener = this.d4shHomeDeviceViewListener;
            if (d4shHomeDeviceViewListener != null) {
                d4shHomeDeviceViewListener.onD4shDeviceVolumeBtnClick();
            }
        }
    }

    @Override // com.petkit.android.media.video.player.BasePetkitPlayerLandscapeViewClickListener
    public void onLandscapePlayBtnClick() {
        if (this.player.isPlayerPlayingState()) {
            this.player.pausePlay();
            this.playerLandscapeView.setPlayerSwitchImageViewResource(Integer.valueOf(R.drawable.petkit_player_landscape_record_view_play_icon));
        } else if (this.player.isPlayerPauseState() || this.player.isPlayerCompleteState()) {
            this.player.continuePlay();
            this.playerLandscapeView.setPlayerSwitchImageViewResource(Integer.valueOf(R.drawable.petkit_player_landscape_record_view_pause_icon));
        }
    }

    @Override // com.petkit.android.media.video.player.BasePetkitPlayerLandscapeViewClickListener
    public void onLandscapeScreenShotBtnClick() {
        D4shHomeDeviceViewListener d4shHomeDeviceViewListener = this.d4shHomeDeviceViewListener;
        if (d4shHomeDeviceViewListener != null) {
            d4shHomeDeviceViewListener.onPlayerScreenShotBtnClick(Boolean.TRUE, null);
        }
    }

    @Override // com.petkit.android.media.video.player.BasePetkitPlayerLandscapeViewClickListener
    public void onLandscapeRecordBtnClick() {
        if (this.d4shHomeDeviceViewListener != null) {
            if (!this.player.isRecording()) {
                this.d4shHomeDeviceViewListener.onVideoRecordBtnClick(Boolean.TRUE);
            } else {
                this.d4shHomeDeviceViewListener.onVideoRecordBtnClick(Boolean.FALSE);
            }
        }
    }

    @Override // com.petkit.android.media.video.player.BasePetkitPlayerLandscapeViewClickListener
    public void onCancelRecord() {
        D4shHomeDeviceViewListener d4shHomeDeviceViewListener = this.d4shHomeDeviceViewListener;
        if (d4shHomeDeviceViewListener != null) {
            d4shHomeDeviceViewListener.onCancelRecord();
        }
    }

    @Override // com.petkit.android.media.video.player.BasePetkitPlayerLandscapeViewClickListener
    public void onLandscapeIntercomBtnTouchDown() {
        int i = this.d4shPlayerStatus;
        if (i == 7) {
            PetkitToast.showTopToast(getContext(), getContext().getString(R.string.Privacy_on), 0, 1);
            return;
        }
        if (i == 4) {
            PetkitToast.showTopToast(getContext(), getContext().getString(R.string.Device_shutdown), 0, 1);
            return;
        }
        if (i == 1) {
            PetkitToast.showTopToast(getContext(), getContext().getString(R.string.Loading), 0, 1);
            return;
        }
        if (i == 3) {
            PetkitToast.showTopToast(getContext(), getContext().getString(R.string.Load_timeout), 0, 1);
            return;
        }
        D4shHomeDeviceViewListener d4shHomeDeviceViewListener = this.d4shHomeDeviceViewListener;
        if (d4shHomeDeviceViewListener != null) {
            d4shHomeDeviceViewListener.onD4shDeviceHomeMenuMicroPhoneViewTouchDown();
        }
    }

    @Override // com.petkit.android.media.video.player.BasePetkitPlayerLandscapeViewClickListener
    public void onLandscapeIntercomBtnTouchUp() {
        D4shHomeDeviceViewListener d4shHomeDeviceViewListener = this.d4shHomeDeviceViewListener;
        if (d4shHomeDeviceViewListener != null) {
            d4shHomeDeviceViewListener.onD4shDeviceHomeMenuMicroPhoneViewTouchUp();
        }
    }

    @Override // com.petkit.android.media.video.player.BasePetkitPlayerLandscapeViewClickListener
    public void onLandscapeExtraMealBtnClick() {
        if (this.d4shRecord.getState().getPim() != 0 && (this.d4shRecord.getState().getFeeding() == 1 || this.d4shRecord.getState().getFeeding() == 2 || this.d4shRecord.getState().getFeeding() == 3)) {
            this.menuOnClickListener.onBottomMenuClick(MenuType.STOP_FEED);
        } else {
            this.playerLandscapeView.showExtraMealWindow(this.d4shRecord.getDeviceId(), this.d4shRecord.getTypeCode());
        }
    }

    @Override // com.petkit.android.media.video.player.BasePetkitPlayerLandscapeViewClickListener
    public void onLandscapeEatVideoBtnClick() {
        this.playerLandscapeView.showEatVideoWindow(this.d4DailyFeeds, this.d4shDeviceRecordDateStr, this.d4shDeviceRecordDateText, this.d4shRecord);
    }

    @Override // com.petkit.android.media.video.player.BasePetkitPlayerLandscapeViewClickListener
    public void onLandscapeDatePickerBtnClick() {
        D4shHomeDeviceViewListener d4shHomeDeviceViewListener = this.d4shHomeDeviceViewListener;
        if (d4shHomeDeviceViewListener != null) {
            d4shHomeDeviceViewListener.onDeviceRecordDateImageViewClick();
        }
    }

    @Override // com.petkit.android.media.video.player.BasePetkitPlayerLandscapeViewClickListener
    public void onVideoRecordClick(Integer num, String str, String str2, Float f, Long l, Long l2, int i, String str3, String str4, D4shDayItem d4shDayItem, long j) {
        int eatVideo;
        Resources resources;
        int i2;
        Resources resources2;
        int i3;
        Resources resources3;
        int i4;
        Resources resources4;
        int i5;
        this.d4shDayItem = d4shDayItem;
        this.mainHandler.removeCallbacks(this.loadFullVideoRunnable);
        showPurchaseEntry();
        if (d4shDayItem.getBean() instanceof D4shDailyFeeds.D4shDailyEat.ItemsBean) {
            D4shDailyFeeds.D4shDailyEat.ItemsBean itemsBean = (D4shDailyFeeds.D4shDailyEat.ItemsBean) d4shDayItem.getBean();
            new HashMap().put("status", "eat");
            this.d4shVideoRecord = D4shVideoRecord.createByEatRecord(itemsBean.getIsNeedUploadVideo(), this.d4shRecord.getDeviceId(), itemsBean.getPreview(), itemsBean.getAesKey(), itemsBean.getMediaApi(), Integer.valueOf(itemsBean.getStartTime()), Integer.valueOf(itemsBean.getEndTime()), Long.valueOf(itemsBean.getEatStartTime()), itemsBean.getEventId(), 0, "", TimeUtils.getInstance().secondsToTimeStringWithUnit(getContext(), TimeUtils.getInstance().timeStampToSeconds(itemsBean.getEatStartTime(), this.d4shRecord.getActualTimeZone())), 1, itemsBean.getExpire(), itemsBean.getDuration(), itemsBean.getStorageSpace(), itemsBean.getEatEndTime(), itemsBean.getMark());
            D4shHomePlayerLandscapeView d4shHomePlayerLandscapeView = this.playerLandscapeView;
            if (itemsBean.getIsNeedUploadVideo() == 1) {
                resources4 = getResources();
                i5 = R.drawable.feedback_icon;
            } else {
                resources4 = getResources();
                i5 = R.drawable.feedback_gray_icon;
            }
            d4shHomePlayerLandscapeView.setFeedbackImageResource(resources4.getDrawable(i5));
            this.isNeedUploadVideo = itemsBean.getIsNeedUploadVideo();
            int eatVideo2 = itemsBean.getEatVideo();
            PrivacyListener privacyListener = this.privacyListener;
            if (privacyListener != null) {
                privacyListener.onVideoItemClick(itemsBean.getEventId());
            }
            eatVideo = eatVideo2;
        } else if (d4shDayItem.getBean() instanceof D4shDailyFeeds.D4shDailyFeed.ItemsBean) {
            new HashMap().put("status", "feed");
            D4shDailyFeeds.D4shDailyFeed.ItemsBean itemsBean2 = (D4shDailyFeeds.D4shDailyFeed.ItemsBean) d4shDayItem.getBean();
            this.d4shVideoRecord = D4shVideoRecord.createByEatRecord(itemsBean2.getIsNeedUploadVideo(), this.d4shRecord.getDeviceId(), itemsBean2.getPreview(), itemsBean2.getAesKey(), itemsBean2.getMediaApi(), Integer.valueOf(itemsBean2.getTime()), (Integer) (-1), Long.valueOf(itemsBean2.getStartTime()), (String) null, 0, "", TimeUtils.getInstance().secondsToTimeStringWithUnit(getContext(), itemsBean2.getTime()), 2, itemsBean2.getExpire(), itemsBean2.getDuration(), itemsBean2.getStorageSpace(), -1L, itemsBean2.getMark());
            D4shHomePlayerLandscapeView d4shHomePlayerLandscapeView2 = this.playerLandscapeView;
            if (itemsBean2.getIsNeedUploadVideo() == 1) {
                resources3 = getResources();
                i4 = R.drawable.feedback_icon;
            } else {
                resources3 = getResources();
                i4 = R.drawable.feedback_gray_icon;
            }
            d4shHomePlayerLandscapeView2.setFeedbackImageResource(resources3.getDrawable(i4));
            this.isNeedUploadVideo = itemsBean2.getIsNeedUploadVideo();
            this.playerLandscapeView.setLanFeedbackBtnVisible(Boolean.FALSE, new int[0]);
            eatVideo = itemsBean2.getEatVideo();
            PrivacyListener privacyListener2 = this.privacyListener;
            if (privacyListener2 != null) {
                privacyListener2.onVideoItemClick(itemsBean2.getEventId());
            }
        } else if (d4shDayItem.getBean() instanceof D4shDailyFeeds.MoveBean.ItemsBeanX) {
            D4shDailyFeeds.MoveBean.ItemsBeanX itemsBeanX = (D4shDailyFeeds.MoveBean.ItemsBeanX) d4shDayItem.getBean();
            new HashMap().put("status", AppMeasurementSdk.ConditionalUserProperty.ACTIVE);
            this.d4shVideoRecord = D4shVideoRecord.createByEatRecord(itemsBeanX.getIsNeedUploadVideo(), this.d4shRecord.getDeviceId(), itemsBeanX.getPreview(), itemsBeanX.getAesKey(), itemsBeanX.getMediaApi(), Integer.valueOf((int) itemsBeanX.getTimestamp()), (Integer) (-1), Long.valueOf(itemsBeanX.getTimestamp()), itemsBeanX.getEventId(), 0, "", TimeUtils.getInstance().secondsToTimeStringWithUnit(getContext(), TimeUtils.getInstance().timeStampToSeconds(itemsBeanX.getTimestamp(), this.d4shRecord.getActualTimeZone())), 3, itemsBeanX.getExpire(), itemsBeanX.getDuration(), itemsBeanX.getStorageSpace(), -1L, itemsBeanX.getMark());
            D4shHomePlayerLandscapeView d4shHomePlayerLandscapeView3 = this.playerLandscapeView;
            if (itemsBeanX.getIsNeedUploadVideo() == 1) {
                resources2 = getResources();
                i3 = R.drawable.feedback_icon;
            } else {
                resources2 = getResources();
                i3 = R.drawable.feedback_gray_icon;
            }
            d4shHomePlayerLandscapeView3.setFeedbackImageResource(resources2.getDrawable(i3));
            this.isNeedUploadVideo = itemsBeanX.getIsNeedUploadVideo();
            int eatVideo3 = itemsBeanX.getEatVideo();
            PrivacyListener privacyListener3 = this.privacyListener;
            if (privacyListener3 != null) {
                privacyListener3.onVideoItemClick(itemsBeanX.getEventId());
            }
            eatVideo = eatVideo3;
        } else if (d4shDayItem.getBean() instanceof D4shDailyFeeds.PetBean.ItemsBean) {
            D4shDailyFeeds.PetBean.ItemsBean itemsBean3 = (D4shDailyFeeds.PetBean.ItemsBean) d4shDayItem.getBean();
            new HashMap().put("status", "pet");
            this.d4shVideoRecord = D4shVideoRecord.createByEatRecord(itemsBean3.getIsNeedUploadVideo(), this.d4shRecord.getDeviceId(), itemsBean3.getPreview(), itemsBean3.getAesKey(), itemsBean3.getMediaApi(), Integer.valueOf((int) itemsBean3.getTimestamp()), (Integer) (-1), Long.valueOf(itemsBean3.getTimestamp()), itemsBean3.getEventId(), 0, "", TimeUtils.getInstance().secondsToTimeStringWithUnit(getContext(), TimeUtils.getInstance().timeStampToSeconds(itemsBean3.getTimestamp(), this.d4shRecord.getActualTimeZone())), 3, itemsBean3.getExpire(), itemsBean3.getDuration(), itemsBean3.getStorageSpace(), -1L, itemsBean3.getMark());
            D4shHomePlayerLandscapeView d4shHomePlayerLandscapeView4 = this.playerLandscapeView;
            if (itemsBean3.getIsNeedUploadVideo() == 1) {
                resources = getResources();
                i2 = R.drawable.feedback_icon;
            } else {
                resources = getResources();
                i2 = R.drawable.feedback_gray_icon;
            }
            d4shHomePlayerLandscapeView4.setFeedbackImageResource(resources.getDrawable(i2));
            this.isNeedUploadVideo = itemsBean3.getIsNeedUploadVideo();
            int eatVideo4 = itemsBean3.getEatVideo();
            PrivacyListener privacyListener4 = this.privacyListener;
            if (privacyListener4 != null) {
                privacyListener4.onVideoItemClick(itemsBean3.getEventId());
            }
            eatVideo = eatVideo4;
        } else {
            eatVideo = 1;
        }
        D4shHomeDeviceViewListener d4shHomeDeviceViewListener = this.d4shHomeDeviceViewListener;
        if (d4shHomeDeviceViewListener != null) {
            d4shHomeDeviceViewListener.cancelLiveLoadTimeoutMonitoring();
            this.d4shHomeDeviceViewListener.cancelVideoLoadTimeoutMonitoring();
            this.d4shHomeDeviceViewListener.cancelFetchVideoSegmentDisposable();
        }
        this.videoExpireOrLoss = false;
        this.eatVideoTimesSpeed = f.floatValue();
        this.recordStartTime = i;
        this.player.pausePlay();
        this.player.setLiveViewVisible(false);
        this.playerLandscapeView.resetRecordViewStatus();
        boolean z = this.d4shRecord.getCloudProduct() == null || this.d4shVideoRecord.getTimestamp().longValue() > Long.parseLong(this.d4shRecord.getCloudProduct().getWorkIndate());
        D4shHomePlayerLandscapeView d4shHomePlayerLandscapeView5 = this.playerLandscapeView;
        Boolean bool = Boolean.FALSE;
        d4shHomePlayerLandscapeView5.setCompareTosvVisible(bool);
        boolean z2 = this.d4shRecord.getServiceStatus() == 0 || System.currentTimeMillis() / 1000 > this.d4shVideoRecord.getExpire() || (this.d4shRecord.getServiceStatus() == 2 && z);
        this.player.getPlayerView().setIntercept(z2);
        if (this.d4shDeviceRecordDateStr != null) {
            this.mContext.getResources().getString(R.string.Today).equals(D4shUtils.getVideoRecordDateStr(getContext(), this.d4shDeviceRecordDateStr));
        }
        this.playerLandscapeView.setShowTosvVisible(Boolean.TRUE);
        if (str == null) {
            this.videoExpireOrLoss = true;
            hideLoadingView();
            long j2 = ((long) i) * 1000;
            if (System.currentTimeMillis() - j2 < 120000 && num != null && num.intValue() == 2) {
                this.player.addCoverWithBackgroundView(Integer.valueOf(R.layout.petkit_player_video_loading_cover_view), Integer.valueOf(R.layout.petkit_player_gray_black_background_cover_view));
                ((PetkitLogoLoadingView) this.player.findViewById(R.id.pllv_cover)).startLoadingAnimation();
                this.mainHandler.postDelayed(this.loadFullVideoRunnable, 120000 - (System.currentTimeMillis() - j2));
            } else if (!TextUtils.isEmpty(str3)) {
                String sysMap = CommonUtils.getSysMap(this.mContext, "D4SH_D4H_BANNER_DATA:" + this.d4shRecord.getDeviceId() + ":" + this.d4shRecord.getTypeCode(), "");
                int serviceStatus = this.d4shRecord.getServiceStatus();
                if (serviceStatus != 0) {
                    if (serviceStatus == 2) {
                        if (TextUtils.isEmpty(sysMap)) {
                            if (System.currentTimeMillis() > l.longValue() * 1000 || this.d4shVideoRecord.getTimestamp().longValue() * 1000 <= Long.parseLong(this.d4shRecord.getCloudProduct().getWorkIndate())) {
                                return;
                            }
                            this.player.addCoverWithBackgroundView(Integer.valueOf(PlayerUtils.getRenewalPurchasePackageCoverView()), Integer.valueOf(R.layout.petkit_player_no_service_preview_background_cover_view));
                            return;
                        }
                        D4shBannerData d4shBannerData = (D4shBannerData) new Gson().fromJson(sysMap, D4shBannerData.class);
                        if (d4shBannerData == null || d4shBannerData.getFreeActivity() == null) {
                            if (System.currentTimeMillis() <= l.longValue() * 1000 && this.d4shVideoRecord.getTimestamp().longValue() * 1000 > Long.parseLong(this.d4shRecord.getCloudProduct().getWorkIndate())) {
                                this.player.addCoverWithBackgroundView(Integer.valueOf(PlayerUtils.getRenewalPurchasePackageCoverView()), Integer.valueOf(R.layout.petkit_player_no_service_preview_background_cover_view));
                            }
                        } else if (System.currentTimeMillis() <= l.longValue() * 1000 && this.d4shVideoRecord.getTimestamp().longValue() * 1000 > Long.parseLong(this.d4shRecord.getCloudProduct().getWorkIndate())) {
                            this.player.addCoverWithBackgroundView(Integer.valueOf(PlayerUtils.getFreePurchasePackageCoverView()), Integer.valueOf(R.layout.petkit_player_no_service_preview_background_cover_view));
                        }
                    }
                } else if (TextUtils.isEmpty(sysMap)) {
                    if (System.currentTimeMillis() <= l.longValue() * 1000) {
                        this.player.addCoverWithBackgroundView(Integer.valueOf(PlayerUtils.getNoServicePurchasePackageCoverView()), Integer.valueOf(R.layout.petkit_player_no_service_preview_background_cover_view));
                        return;
                    }
                    return;
                } else {
                    D4shBannerData d4shBannerData2 = (D4shBannerData) new Gson().fromJson(sysMap, D4shBannerData.class);
                    if (d4shBannerData2 == null || d4shBannerData2.getFreeActivity() == null) {
                        if (System.currentTimeMillis() <= l.longValue() * 1000) {
                            this.player.addCoverWithBackgroundView(Integer.valueOf(PlayerUtils.getNoServicePurchasePackageCoverView()), Integer.valueOf(R.layout.petkit_player_no_service_preview_background_cover_view));
                        }
                    } else if (System.currentTimeMillis() <= l.longValue() * 1000) {
                        this.player.addCoverWithBackgroundView(Integer.valueOf(PlayerUtils.getFreePurchasePackageCoverView()), Integer.valueOf(R.layout.petkit_player_no_service_preview_background_cover_view));
                    }
                }
                D4shUtils.setImageViewUrl((ImageView) this.player.findViewById(R.id.preview_image_view), str3, str4);
            } else if (eatVideo == 0) {
                if (this.d4shRecord.getServiceStatus() != 1) {
                    this.player.addCoverWithBackgroundView(Integer.valueOf(R.layout.petkit_player_photo_loss_cover_view), Integer.valueOf(R.layout.petkit_player_gray_black_background_cover_view));
                } else {
                    this.player.addCoverWithBackgroundView(Integer.valueOf(R.layout.petkit_player_eat_video_loss_cover_view), Integer.valueOf(R.layout.petkit_player_gray_black_background_cover_view));
                }
            } else if (this.d4shRecord.getServiceStatus() != 1) {
                this.player.addCoverWithBackgroundView(Integer.valueOf(R.layout.petkit_player_picture_loss_cover_view), Integer.valueOf(R.layout.petkit_player_gray_black_background_cover_view));
                if (((D4shDailyFeeds.MoveBean.ItemsBeanX) d4shDayItem.getBean()).getEatVideo() == 1) {
                    ((TextView) this.player.findViewById(R.id.tv_no_video_tip)).setText(R.string.No_video_prompt_one);
                } else {
                    ((TextView) this.player.findViewById(R.id.tv_no_video_tip)).setText(R.string.No_video_prompt_two);
                }
            } else {
                this.player.addCoverWithBackgroundView(Integer.valueOf(R.layout.petkit_player_video_loss_cover_view), Integer.valueOf(R.layout.petkit_player_gray_black_background_cover_view));
                if (((D4shDailyFeeds.MoveBean.ItemsBeanX) d4shDayItem.getBean()).getEatVideo() == 1) {
                    ((TextView) this.player.findViewById(R.id.tv_no_video_tip)).setText(R.string.No_video_prompt_one);
                } else {
                    ((TextView) this.player.findViewById(R.id.tv_no_video_tip)).setText(R.string.No_video_prompt_two);
                }
            }
            if (this.player.findViewById(R.id.camera_unavailable_prompt_view) != null) {
                this.player.findViewById(R.id.camera_unavailable_prompt_view).setBackgroundColor(getResources().getColor(R.color.dark_black));
            }
            this.playerLandscapeView.setLanFeedbackBtnVisible(bool, new int[0]);
            return;
        }
        if (l != null && System.currentTimeMillis() / 1000 > l.longValue()) {
            this.playerLandscapeView.setLanFeedbackBtnVisible(bool, new int[0]);
            this.videoExpireOrLoss = true;
            hideLoadingView();
            this.player.addCoverWithBackgroundView(Integer.valueOf(PlayerUtils.getNoServiceVideoExpiredCoverView()), Integer.valueOf(R.layout.petkit_player_gray_black_background_cover_view));
            this.player.findViewById(R.id.camera_unavailable_prompt_view).setBackground(this.mContext.getResources().getDrawable(R.color.dark_black));
            return;
        }
        if (z2) {
            this.videoExpireOrLoss = true;
            hideLoadingView();
            if (l != null && System.currentTimeMillis() / 1000 > l.longValue()) {
                this.player.addCoverWithBackgroundView(Integer.valueOf(PlayerUtils.getNoServiceVideoExpiredCoverView()), Integer.valueOf(R.layout.petkit_player_gray_black_background_cover_view));
            } else {
                String sysMap2 = CommonUtils.getSysMap(this.mContext, "D4SH_D4H_BANNER_DATA:" + this.d4shRecord.getDeviceId() + ":" + this.d4shRecord.getTypeCode(), "");
                int serviceStatus2 = this.d4shRecord.getServiceStatus();
                if (serviceStatus2 != 0) {
                    if (serviceStatus2 == 2) {
                        D4shBannerData d4shBannerData3 = (D4shBannerData) new Gson().fromJson(sysMap2, D4shBannerData.class);
                        if (d4shBannerData3 == null || d4shBannerData3.getFreeActivity() == null) {
                            if (System.currentTimeMillis() <= l.longValue() * 1000 && this.d4shVideoRecord.getTimestamp().longValue() * 1000 > Long.parseLong(this.d4shRecord.getCloudProduct().getWorkIndate())) {
                                this.player.addCoverWithBackgroundView(Integer.valueOf(PlayerUtils.getRenewalPurchasePackageCoverView()), Integer.valueOf(R.layout.petkit_player_no_service_preview_background_cover_view));
                            }
                        } else if (System.currentTimeMillis() <= l.longValue() * 1000 && this.d4shVideoRecord.getTimestamp().longValue() * 1000 > Long.parseLong(this.d4shRecord.getCloudProduct().getWorkIndate())) {
                            this.player.addCoverWithBackgroundView(Integer.valueOf(PlayerUtils.getFreePurchasePackageCoverView()), Integer.valueOf(R.layout.petkit_player_no_service_preview_background_cover_view));
                        }
                    }
                } else if (((D4shBannerData) new Gson().fromJson(sysMap2, D4shBannerData.class)) != null) {
                    if (System.currentTimeMillis() <= l.longValue() * 1000) {
                        this.player.addCoverWithBackgroundView(Integer.valueOf(PlayerUtils.getFreePurchasePackageCoverView()), Integer.valueOf(R.layout.petkit_player_no_service_preview_background_cover_view));
                    }
                } else if (System.currentTimeMillis() <= l.longValue() * 1000) {
                    this.player.addCoverWithBackgroundView(Integer.valueOf(PlayerUtils.getNoServicePurchasePackageCoverView()), Integer.valueOf(R.layout.petkit_player_no_service_preview_background_cover_view));
                }
                D4shUtils.setImageViewUrl((ImageView) this.player.findViewById(R.id.preview_image_view), str3, str4);
            }
            this.player.findViewById(R.id.camera_unavailable_prompt_view).setBackground(this.mContext.getResources().getDrawable(R.color.dark_black));
            if (this.player.findViewById(R.id.open_immediately) != null) {
                this.player.findViewById(R.id.open_immediately).setOnClickListener(this);
                this.player.findViewById(R.id.open_immediately).setVisibility(8);
            }
            this.playerLandscapeView.setLanFeedbackBtnVisible(bool, new int[0]);
            return;
        }
        this.player.clearCoverView();
        D4shHomeDeviceViewListener d4shHomeDeviceViewListener2 = this.d4shHomeDeviceViewListener;
        if (d4shHomeDeviceViewListener2 != null) {
            d4shHomeDeviceViewListener2.onPlayerStartLoadVideo();
        }
        String eventIdFromD4shDayItem = D4shUtils.getEventIdFromD4shDayItem(d4shDayItem);
        long markFromD4shDayItem = D4shUtils.getMarkFromD4shDayItem(d4shDayItem);
        long startTimeFromD4shDayItem = D4shUtils.getStartTimeFromD4shDayItem(d4shDayItem);
        MaterialEventInfo materialEventInfo = new MaterialEventInfo();
        this.materialEventInfo = materialEventInfo;
        materialEventInfo.setEventId(eventIdFromD4shDayItem);
        this.materialEventInfo.setMark(markFromD4shDayItem);
        this.materialEventInfo.setStartTime(startTimeFromD4shDayItem);
        if (num != null && num.intValue() == 2) {
            this.player.showLoadingView();
            D4shHomeDeviceViewListener d4shHomeDeviceViewListener3 = this.d4shHomeDeviceViewListener;
            if (d4shHomeDeviceViewListener3 != null) {
                d4shHomeDeviceViewListener3.onFetchVideoSegment(markFromD4shDayItem, startTimeFromD4shDayItem, l2);
                return;
            }
            return;
        }
        if (num == null || num.intValue() != 1) {
            return;
        }
        this.player.showLoadingView();
        this.player.startVideo(str, true);
    }

    @Override // com.petkit.android.media.video.player.BasePetkitPlayerLandscapeViewClickListener
    public void onCompareImageClick(D4shDayItem d4shDayItem) {
        this.d4shDayItem = d4shDayItem;
        this.playerLandscapeView.setCompareTosvVisible(Boolean.TRUE);
        this.mainHandler.removeCallbacks(this.loadFullVideoRunnable);
        D4shDailyFeeds.CompareBean.ItemsBeanX itemsBeanX = (D4shDailyFeeds.CompareBean.ItemsBeanX) d4shDayItem.getBean();
        new HashMap().put("status", "eat");
        this.d4shVideoRecord = D4shVideoRecord.createByEatCompareRecord(this.d4shRecord.getDeviceId(), itemsBeanX, 0, 5);
        D4shHomePlayerLandscapeView d4shHomePlayerLandscapeView = this.playerLandscapeView;
        Boolean bool = Boolean.FALSE;
        d4shHomePlayerLandscapeView.setLanFeedbackBtnVisible(bool, new int[0]);
        D4shHomeDeviceViewListener d4shHomeDeviceViewListener = this.d4shHomeDeviceViewListener;
        if (d4shHomeDeviceViewListener != null) {
            d4shHomeDeviceViewListener.cancelLiveLoadTimeoutMonitoring();
            this.d4shHomeDeviceViewListener.cancelVideoLoadTimeoutMonitoring();
            this.d4shHomeDeviceViewListener.cancelFetchVideoSegmentDisposable();
        }
        this.player.pausePlay();
        this.player.setLiveViewVisible(false);
        this.d4shHomeDeviceViewListener.stopLive();
        this.playerLandscapeView.initOptionWindowThroughD4shDayItem(d4shDayItem, this.d4shRecord.getDeviceShared() != null);
        this.playerLandscapeView.setShowTosvVisible(bool);
        this.playerLandscapeView.resetRecordViewStatus();
        this.videoExpireOrLoss = true;
        hideLoadingView();
        this.player.addCoverWithBackgroundView(null, Integer.valueOf(R.layout.petkit_player_no_service_preview_background_cover_view));
        this.imageViewPager = (ViewPager) this.player.findViewById(R.id.image_view_pager);
        this.player.findViewById(R.id.preview_image_view).setVisibility(8);
        this.imageViewPager.setVisibility(0);
        if (itemsBeanX != null && (System.currentTimeMillis() / 1000 > itemsBeanX.getExpire1() || System.currentTimeMillis() / 1000 > itemsBeanX.getExpire2())) {
            this.playerLandscapeView.setLanFeedbackBtnVisible(bool, new int[0]);
            this.videoExpireOrLoss = true;
            hideLoadingView();
            this.player.addCoverWithBackgroundView(Integer.valueOf(PlayerUtils.getImageExpiredCoverView()), Integer.valueOf(R.layout.petkit_player_gray_black_background_cover_view));
            this.player.findViewById(R.id.camera_unavailable_prompt_view).setBackground(this.mContext.getResources().getDrawable(R.color.dark_black));
            return;
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        if (d4shDayItem.getChoosePosition() == 0) {
            arrayList.add(itemsBeanX.getPreview1());
            arrayList2.add(itemsBeanX.getAesKey1());
            this.playerLandscapeView.setRecordContentTextView(this.activity.getString(R.string.D4sh_eat_compare_title) + ChineseToPinyinResource.Field.LEFT_BRACKET + this.activity.getString(R.string.D4sh_before_eating) + ChineseToPinyinResource.Field.RIGHT_BRACKET);
        } else {
            arrayList.add(itemsBeanX.getPreview2());
            arrayList2.add(itemsBeanX.getAesKey2());
            this.playerLandscapeView.setRecordContentTextView(this.activity.getString(R.string.D4sh_eat_compare_title) + ChineseToPinyinResource.Field.LEFT_BRACKET + this.activity.getString(R.string.D4sh_after_eating) + ChineseToPinyinResource.Field.RIGHT_BRACKET);
        }
        MultipleImageView.MultipleImageAdapter multipleImageAdapter = new MultipleImageView.MultipleImageAdapter(arrayList, arrayList2, getContext());
        this.multipleImageAdapter = multipleImageAdapter;
        this.imageViewPager.setAdapter(multipleImageAdapter);
        this.imageViewPager.setCurrentItem(0);
        this.imageViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView.53
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i, float f, int i2) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int i) {
                if (i == 0) {
                    D4shHomeDeviceView.this.playerLandscapeView.setRecordContentTextView(D4shHomeDeviceView.this.activity.getString(R.string.D4sh_eat_compare_title) + ChineseToPinyinResource.Field.LEFT_BRACKET + D4shHomeDeviceView.this.activity.getString(R.string.D4sh_before_eating) + ChineseToPinyinResource.Field.RIGHT_BRACKET);
                    return;
                }
                if (i == 1) {
                    D4shHomeDeviceView.this.playerLandscapeView.setRecordContentTextView(D4shHomeDeviceView.this.activity.getString(R.string.D4sh_eat_compare_title) + ChineseToPinyinResource.Field.LEFT_BRACKET + D4shHomeDeviceView.this.activity.getString(R.string.D4sh_after_eating) + ChineseToPinyinResource.Field.RIGHT_BRACKET);
                }
            }
        });
    }

    @Override // com.petkit.android.media.video.player.BasePetkitPlayerLandscapeViewClickListener
    public void onBackLiveBtnClick() {
        D4shHomeDeviceViewListener d4shHomeDeviceViewListener = this.d4shHomeDeviceViewListener;
        if (d4shHomeDeviceViewListener != null) {
            d4shHomeDeviceViewListener.cancelLiveLoadTimeoutMonitoring();
            this.d4shHomeDeviceViewListener.cancelVideoLoadTimeoutMonitoring();
            this.d4shHomeDeviceViewListener.cancelFetchVideoSegmentDisposable();
        }
        this.mainHandler.removeCallbacks(this.loadFullVideoRunnable);
        this.player.changeToLive();
        this.videoExpireOrLoss = false;
        showLoadingView();
        this.player.clearCoverView();
        D4shHomeDeviceViewListener d4shHomeDeviceViewListener2 = this.d4shHomeDeviceViewListener;
        if (d4shHomeDeviceViewListener2 != null) {
            d4shHomeDeviceViewListener2.onBackLiveBtnClick();
        }
    }

    @Override // com.petkit.android.media.video.player.BasePetkitPlayerLandscapeViewClickListener
    public void onDeleteEatVideoBtnClick(String str, int i, int i2, int i3) {
        if (str != null) {
            this.deletePosition = i3;
            this.d4sRemoveData.setEventId(str);
            this.d4sRemoveData.setDeleteType(3);
            openDeleteRecordWindow(String.format("%s", this.mContext.getString(R.string.D4sh_device_record_remove_video_event)));
            return;
        }
        if (i != 0 && i2 != 0) {
            this.deletePosition = i3;
            this.d4sRemoveData.setEndTime(i);
            this.d4sRemoveData.setStartTime(i2);
            this.d4sRemoveData.setDeleteType(1);
            openDeleteRecordWindow(this.mContext.getResources().getString(R.string.D4s_delete_work_record_tip));
            return;
        }
        if (i != 0 || i2 == 0) {
            return;
        }
        this.deletePosition = i3;
        this.d4sRemoveData.setStartTime(i2);
        this.d4sRemoveData.setDeleteType(2);
        openDeleteRecordWindow(this.mContext.getResources().getString(R.string.D4s_delete_work_record_tip));
    }

    @Override // com.petkit.android.media.video.player.BasePetkitPlayerLandscapeViewClickListener
    public void onDeleteEatVideoBtnClick(int i, String str, long j, long j2, int i2) {
        if (i == 1) {
            this.deletePosition = i2;
            this.d4sRemoveData.setEventId(str);
            this.d4sRemoveData.setDeleteType(2);
            this.d4sRemoveData.setStartTime((int) j2);
            openDeleteRecordWindow(String.format("%s", this.mContext.getString(R.string.D4sh_device_record_remove_video_event)));
            return;
        }
        if (str != null) {
            this.deletePosition = i2;
            this.d4sRemoveData.setEventId(str);
            this.d4sRemoveData.setDeleteType(3);
            openDeleteRecordWindow(String.format("%s", this.mContext.getString(R.string.D4sh_device_record_remove_video_event)));
            return;
        }
        if (j == 0 || j2 == 0) {
            return;
        }
        this.deletePosition = i2;
        this.d4sRemoveData.setEndTime((int) j);
        this.d4sRemoveData.setStartTime((int) j2);
        this.d4sRemoveData.setDeleteType(1);
        openDeleteRecordWindow(this.mContext.getResources().getString(R.string.D4s_delete_work_record_tip));
    }

    @Override // com.petkit.android.media.video.player.BasePetkitPlayerLandscapeViewClickListener
    public void onDownloadFullVideo(Long l, Long l2, Long l3, Long l4) {
        if (this.d4shRecord.getServiceStatus() != 1 || System.currentTimeMillis() / 1000 > l4.longValue()) {
            Context context = this.mContext;
            PetkitToast.showTopToast(context, context.getString(R.string.Expired_prompt), R.drawable.top_toast_warn_icon, 1);
        } else {
            D4shHomeDeviceViewListener d4shHomeDeviceViewListener = this.d4shHomeDeviceViewListener;
            if (d4shHomeDeviceViewListener != null) {
                d4shHomeDeviceViewListener.onPlayerDownloadFullVideo(l, l2, l3);
            }
        }
    }

    @Override // com.petkit.android.media.video.player.BasePetkitPlayerLandscapeViewClickListener
    public void onSeekbarProgressChanged(int i) {
        this.player.seekCompletePlay(i, true);
        this.player.continuePlay();
    }

    @Override // com.petkit.android.media.video.player.BasePetkitPlayerLandscapeViewClickListener
    public void onSeekbarStartMoving() {
        this.player.pausePlay();
    }

    @Override // com.petkit.android.media.video.player.BasePetkitPlayerLandscapeViewClickListener
    public void onRecordFeedbackClick() {
        if (this.isNeedUploadVideo == 1) {
            Activity activity = this.activity;
            activity.startActivity(D4shVideoFeedbackActivity.newIntent(activity, this.d4shRecord.getDeviceId(), this.d4shRecord.getTypeCode() == 0 ? 25 : 26, this.d4shVideoRecord));
        } else {
            showTipWindow();
        }
    }

    private void showTipWindow() {
        new NewIKnowWindow(this.activity, (String) null, getResources().getString(R.string.Video_Have_FeedBack), (String) null, getResources().getString(R.string.Okay)).show(this.activity.getWindow().getDecorView());
    }

    @Override // com.petkit.android.media.video.player.BasePetkitPlayerLandscapeViewClickListener
    public void onSaveImage() {
        if (this.d4shVideoRecord.getRecordType() == 5) {
            if (!CommonUtils.checkPermission(this.activity, "android.permission.WRITE_EXTERNAL_STORAGE")) {
                Activity activity = this.activity;
                activity.startActivity(PermissionDialogActivity.newIntent(activity, activity.getClass().getName(), "android.permission.WRITE_EXTERNAL_STORAGE"));
                return;
            } else if (System.currentTimeMillis() / 1000 > this.d4shVideoRecord.getExpire1() || System.currentTimeMillis() / 1000 > this.d4shVideoRecord.getExpire2()) {
                PetkitToast.showTopToast(this.activity, getResources().getString(R.string.Expired_prompt), 0, 0);
                return;
            } else {
                VideoDownloadManager.saveVideoImage(this.activity, this.d4shVideoRecord.getPreview1(), this.mContext.getString(R.string.Picture_has_been_saved), this.mContext.getString(R.string.Save_fail_prompt), this.d4shVideoRecord.getAesKey1(), this.d4shRecord.getTypeCode() == 0 ? 25 : 26);
                VideoDownloadManager.saveVideoImage(this.activity, this.d4shVideoRecord.getPreview2(), this.mContext.getString(R.string.Picture_has_been_saved), this.mContext.getString(R.string.Save_fail_prompt), this.d4shVideoRecord.getAesKey2(), this.d4shRecord.getTypeCode() == 0 ? 25 : 26);
                return;
            }
        }
        if (!CommonUtils.checkPermission(this.activity, "android.permission.WRITE_EXTERNAL_STORAGE")) {
            Activity activity2 = this.activity;
            activity2.startActivity(PermissionDialogActivity.newIntent(activity2, activity2.getClass().getName(), "android.permission.WRITE_EXTERNAL_STORAGE"));
        } else if (System.currentTimeMillis() / 1000 > this.d4shVideoRecord.getExpire()) {
            PetkitToast.showTopToast(this.activity, getResources().getString(R.string.Expired_prompt), 0, 0);
        } else {
            VideoDownloadManager.saveVideoImage(this.activity, this.d4shVideoRecord.getPreview(), this.mContext.getString(R.string.Picture_has_been_saved), this.mContext.getString(R.string.Save_fail_prompt), this.d4shVideoRecord.getAesKey(), this.d4shRecord.getTypeCode() == 0 ? 25 : 26);
        }
    }

    private void setD4shDeviceRecordTagTypeNumber(D4shDailyFeeds d4shDailyFeeds) {
        List<Integer> d4shRecordTypeNumberList = D4shUtils.getD4shRecordTypeNumberList(d4shDailyFeeds);
        this.d4shDeviceRecordTagViewGroup.setD4shDeviceRecordTypeNumber(d4shRecordTypeNumberList.get(0), d4shRecordTypeNumberList.get(1), d4shRecordTypeNumberList.get(2), d4shRecordTypeNumberList.get(3), d4shRecordTypeNumberList.get(4));
        this.d4shDeviceRecordTagViewGroupTopFixed.setD4shDeviceRecordTypeNumber(d4shRecordTypeNumberList.get(0), d4shRecordTypeNumberList.get(1), d4shRecordTypeNumberList.get(2), d4shRecordTypeNumberList.get(3), d4shRecordTypeNumberList.get(4));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void redirectToPurchasePage(int i, long j) {
        CloudServiceView.OnPurchaseBtnClickListener onPurchaseBtnClickListener;
        String strCreatePurchasePageUrl = D4shUtils.createPurchasePageUrl(this.mContext, i, j);
        if (TextUtils.isEmpty(strCreatePurchasePageUrl) || (onPurchaseBtnClickListener = this.onPurchaseBtnClickListener) == null) {
            return;
        }
        onPurchaseBtnClickListener.onRedirectToH5(strCreatePurchasePageUrl);
    }

    public void setOnPurchaseBtnClickListener(CloudServiceView.OnPurchaseBtnClickListener onPurchaseBtnClickListener) {
        this.onPurchaseBtnClickListener = onPurchaseBtnClickListener;
    }

    public int getScollYDistance() {
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) this.rvD4RecordView.getLayoutManager();
        int iFindFirstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
        View viewFindViewByPosition = linearLayoutManager.findViewByPosition(iFindFirstVisibleItemPosition);
        return (iFindFirstVisibleItemPosition * viewFindViewByPosition.getHeight()) - viewFindViewByPosition.getTop();
    }

    public void refreshHighlightsData(List<HighlightRecord> list) {
        D4shVlogRecordAdapter d4shVlogRecordAdapter = new D4shVlogRecordAdapter((Activity) this.mContext, this.d4shRecord);
        this.d4shVlogRecordAdapter = d4shVlogRecordAdapter;
        d4shVlogRecordAdapter.setTypeCode(this.d4shRecord.getTypeCode());
        this.d4shVlogRecordAdapter.append((List) list);
        this.rvHighlights.setAdapter(this.d4shVlogRecordAdapter);
        TextView textView = this.tvMore;
        if (list != null) {
            list.size();
        }
        textView.setVisibility(0);
        this.d4shVlogRecordAdapter.setOnDailyHighlightItemClickListener(new BasePetkitHighlightAdapter.OnDailyHighlightItemClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView.54
            @Override // com.petkit.android.activities.petkitBleDevice.adapter.BasePetkitHighlightAdapter.OnDailyHighlightItemClickListener
            public void onEmptyClick() {
            }

            @Override // com.petkit.android.activities.petkitBleDevice.adapter.BasePetkitHighlightAdapter.OnDailyHighlightItemClickListener
            public void onPlayBtnClick(HighlightRecord highlightRecord) {
                D4shVlogRecordAdapterListener d4shVlogRecordAdapterListener = D4shHomeDeviceView.this.d4shVlogRecordAdapterListener;
                if (d4shVlogRecordAdapterListener != null) {
                    d4shVlogRecordAdapterListener.onPlayBtnClick(highlightRecord);
                }
            }

            @Override // com.petkit.android.activities.petkitBleDevice.adapter.BasePetkitHighlightAdapter.OnDailyHighlightItemClickListener
            public void onMarkVlogClick(HighlightRecord highlightRecord) {
                D4shHomeDeviceView d4shHomeDeviceView = D4shHomeDeviceView.this;
                if (d4shHomeDeviceView.d4shVlogRecordAdapterListener != null) {
                    d4shHomeDeviceView.d4shVlogRecordAdapter.setRefreshLoadingStatus(true);
                    D4shHomeDeviceView.this.d4shVlogRecordAdapterListener.onMarkVlogClick(highlightRecord);
                }
            }
        });
        if (CommonUtils.checkPermission(this.mContext, "android.permission.WRITE_EXTERNAL_STORAGE") && this.d4shRecord.getSettings().getHighlight() == 1 && this.d4shRecord.getSettings().getAutoProduct() == 1 && list.size() > 0) {
            HighlightRecord highlightRecord = list.get(0);
            if (highlightRecord.getDisabled() != 1 && highlightRecord.getDisabled() != 2 && TextUtils.isEmpty(highlightRecord.getVideoUrl()) && VlogUtils.getVlogMarkRecord(CommonUtils.getCurrentUserId(), highlightRecord.getId()) == null && ((System.currentTimeMillis() <= ((long) highlightRecord.getExpired()) * 1000 || highlightRecord.getExpired() == 0) && highlightRecord.getId() != 0 && this.d4shVlogRecordAdapterListener != null)) {
                this.d4shVlogRecordAdapter.setRefreshLoadingStatus(true);
                this.d4shVlogRecordAdapterListener.onMarkVlogClick(highlightRecord);
            }
        }
        Activity activity = this.activity;
        StringBuilder sb = new StringBuilder();
        sb.append(com.petkit.android.utils.Constants.D4H_D4SH_VLOG_MAKE_TIP);
        sb.append(this.d4shRecord.getTypeCode() == 0 ? 25 : 26);
        sb.append("_");
        sb.append(this.d4shRecord.getDeviceId());
        sb.append("_");
        sb.append(D4shUtils.getTodayYYYYMMddFormatStr());
        if (DataHelper.getBooleanSF(activity, sb.toString())) {
            return;
        }
        Calendar calendar = Calendar.getInstance();
        if (list == null || list.size() < 1) {
            return;
        }
        HighlightRecord highlightRecord2 = list.get(0);
        if (VlogUtils.getVlogMarkRecord(CommonUtils.getCurrentUserId(), highlightRecord2.getId()) == null) {
            if ((System.currentTimeMillis() <= ((long) highlightRecord2.getExpired()) * 1000 || highlightRecord2.getExpired() == 0) && highlightRecord2.getId() != 0 && TextUtils.isEmpty(highlightRecord2.getVideoUrl()) && calendar.get(11) >= 8) {
                this.mainHandler.post(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView.55
                    @Override // java.lang.Runnable
                    public void run() {
                        D4shHomeDeviceView d4shHomeDeviceView = D4shHomeDeviceView.this;
                        d4shHomeDeviceView.showVlogTipWindow(d4shHomeDeviceView.rvHighlights.getChildAt(0));
                    }
                });
            }
        }
    }

    public void refreshHighlightsDataFail() {
        ArrayList arrayList = new ArrayList();
        if (this.d4shVlogRecordAdapter == null) {
            D4shVlogRecordAdapter d4shVlogRecordAdapter = new D4shVlogRecordAdapter((Activity) this.mContext, this.d4shRecord);
            this.d4shVlogRecordAdapter = d4shVlogRecordAdapter;
            d4shVlogRecordAdapter.setTypeCode(this.d4shRecord.getTypeCode());
            this.d4shVlogRecordAdapter.append((List) arrayList);
            this.rvHighlights.setAdapter(this.d4shVlogRecordAdapter);
        }
    }

    public void removeHighlightRecord() {
        D4shVlogRecordAdapter d4shVlogRecordAdapter = this.d4shVlogRecordAdapter;
        if (d4shVlogRecordAdapter != null) {
            d4shVlogRecordAdapter.removeAll();
        }
    }

    public void refreshVlogList() {
        D4shVlogRecordAdapter d4shVlogRecordAdapter = this.d4shVlogRecordAdapter;
        if (d4shVlogRecordAdapter != null) {
            d4shVlogRecordAdapter.setRefreshLoadingStatus(false);
            this.d4shVlogRecordAdapter.notifyDataSetChanged();
        }
    }

    public boolean isUnMask() {
        return this.isUnMask;
    }

    public void setD4shVlogRecordAdapterListener(D4shVlogRecordAdapterListener d4shVlogRecordAdapterListener) {
        this.d4shVlogRecordAdapterListener = d4shVlogRecordAdapterListener;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startPromoteViewAnim() {
        PromoteView promoteView = this.promoteView;
        if (promoteView == null || promoteView.getVisibility() == 8) {
            return;
        }
        this.startCollAnim = true;
        TranslateAnimation translateAnimation = new TranslateAnimation(1, 0.0f, 1, 0.7f, 0, 0.0f, 0, 0.0f);
        translateAnimation.setFillAfter(true);
        translateAnimation.setDuration(400L);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView.56
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                D4shHomeDeviceView.this.isHidePromote = true;
            }
        });
        this.promoteView.startAnimation(translateAnimation);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void endPromoteViewAnim() {
        PromoteView promoteView = this.promoteView;
        if (promoteView == null || promoteView.getVisibility() == 8) {
            return;
        }
        this.startExpandAnim = true;
        TranslateAnimation translateAnimation = new TranslateAnimation(1, 0.7f, 1, 0.0f, 0, 0.0f, 0, 0.0f);
        translateAnimation.setFillAfter(true);
        translateAnimation.setDuration(400L);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView.57
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                D4shHomeDeviceView.this.startCollAnim = false;
                D4shHomeDeviceView.this.startExpandAnim = false;
                D4shHomeDeviceView.this.isHidePromote = false;
            }
        });
        this.promoteView.startAnimation(translateAnimation);
    }

    public void setMaterialInfo(MaterialUploadRes materialUploadRes) {
        this.materialInfo = materialUploadRes;
        StringBuilder sb = new StringBuilder();
        sb.append("是否有值：");
        sb.append(this.materialInfo != null);
        PetkitLog.d("materialInfo", sb.toString());
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0061  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void showAi(boolean r7, com.petkit.android.activities.device.mode.AiInfo r8, long r9, int r11) {
        /*
            r6 = this;
            r6.aiNewInfo = r8
            com.petkit.android.activities.device.utils.AiDataUtil r0 = com.petkit.android.activities.device.utils.AiDataUtil.getInstance()
            android.content.Context r1 = r6.getContext()
            r2 = r8
            r3 = r9
            r5 = r11
            boolean r8 = r0.isDeviceHaveNewAiActivity(r1, r2, r3, r5)
            r0 = 1
            r1 = 0
            if (r8 == 0) goto L2f
            com.petkit.android.activities.petkitBleDevice.utils.FamilyUtils r8 = com.petkit.android.activities.petkitBleDevice.utils.FamilyUtils.getInstance()
            android.content.Context r2 = r6.getContext()
            boolean r8 = r8.checkIsSharedDevice(r2, r9, r11)
            if (r8 != 0) goto L2f
            com.petkit.android.activities.petkitBleDevice.d4sh.mode.D4shRecord r8 = r6.d4shRecord
            com.petkit.android.model.DeviceShared r8 = r8.getDeviceShared()
            if (r8 != 0) goto L2f
            r6.setIsMenuAiShowRedPoint(r0)
            goto L32
        L2f:
            r6.setIsMenuAiShowRedPoint(r1)
        L32:
            com.petkit.android.activities.petkitBleDevice.widget.BleDeviceHomeMenuView r8 = r6.bleMenuView
            if (r8 != 0) goto L37
            return
        L37:
            if (r7 == 0) goto L66
            com.petkit.android.activities.petkitBleDevice.d4sh.mode.D4shRecord r7 = r6.d4shRecord
            com.petkit.android.model.DeviceShared r7 = r7.getRealDeviceShared()
            if (r7 != 0) goto L61
            com.petkit.android.activities.petkitBleDevice.utils.FamilyUtils r7 = com.petkit.android.activities.petkitBleDevice.utils.FamilyUtils.getInstance()
            android.app.Activity r9 = r6.activity
            com.petkit.android.activities.petkitBleDevice.d4sh.mode.D4shRecord r10 = r6.d4shRecord
            long r10 = r10.getDeviceId()
            com.petkit.android.activities.petkitBleDevice.d4sh.mode.D4shRecord r2 = r6.d4shRecord
            int r2 = r2.getTypeCode()
            if (r2 != 0) goto L58
            r2 = 25
            goto L5a
        L58:
            r2 = 26
        L5a:
            boolean r7 = r7.checkIsSharedDevice(r9, r10, r2)
            if (r7 != 0) goto L61
            goto L62
        L61:
            r0 = 0
        L62:
            r8.showAiEnter(r0)
            goto L69
        L66:
            r8.showAiEnter(r1)
        L69:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView.showAi(boolean, com.petkit.android.activities.device.mode.AiInfo, long, int):void");
    }

    public void setIsMenuAiShowRedPoint(boolean z) {
        BleDeviceHomeMenuView bleDeviceHomeMenuView = this.bleMenuView;
        if (bleDeviceHomeMenuView == null) {
            return;
        }
        bleDeviceHomeMenuView.setIsAiShowRedPoint(z);
    }

    public void jumpToAi(int i, long j) {
        Activity activity = this.activity;
        activity.startActivity(AiActivity.newIntent(activity, 0, i, j, ""));
    }

    public void jumpToAudit(int i) {
        Activity activity = this.activity;
        activity.startActivity(AiActivity.newIntent(activity, 1, i, this.d4shRecord.getDeviceId(), new Gson().toJson(this.materialEventInfo)));
    }

    private void showAddDialog() {
        String string = getResources().getString(R.string.PetKit_privacy_text);
        final String string2 = getResources().getString(R.string.Co_creation_privacy_title);
        String string3 = getResources().getString(R.string.Co_creation_privacy_greement_content, string2);
        SpannableString spannableString = new SpannableString(string3);
        int iIndexOf = string3.indexOf(string2);
        int length = string2.length() + iIndexOf;
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.new_bind_blue)), iIndexOf, length, 18);
        spannableString.setSpan(new ClickableSpan() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView.58
            @Override // android.text.style.ClickableSpan
            public void onClick(@NonNull View view) {
                if (D4shHomeDeviceView.this.privacyListener != null) {
                    D4shHomeDeviceView.this.privacyListener.jumpToWeb(ApiTools.getWebUrlByKey("Co_Creation_Privacy_Protocol"), string2);
                }
            }

            @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
            public void updateDrawState(@NonNull TextPaint textPaint) {
                super.updateDrawState(textPaint);
                textPaint.setUnderlineText(false);
                textPaint.setColor(D4shHomeDeviceView.this.getResources().getColor(R.color.new_bind_blue));
            }
        }, iIndexOf, length, 18);
        NormalCenterTipWindow normalCenterTipWindow = new NormalCenterTipWindow(this.activity, new NormalCenterTipWindow.OnClickOk() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomeDeviceView.59
            @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
            public void onCancelClick() {
            }

            @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
            public void onOkClick() {
                if (D4shHomeDeviceView.this.privacyListener != null) {
                    D4shHomeDeviceView.this.privacyListener.agreePrivacy();
                }
            }
        }, string, spannableString);
        normalCenterTipWindow.setCancelButtonText(getResources().getString(R.string.Not_agree), getResources().getColor(R.color.common_text));
        normalCenterTipWindow.setOkButtonText(getResources().getString(R.string.Agree_continue));
        normalCenterTipWindow.show(this.activity.getWindow().getDecorView());
    }

    public void agreePrivacy() {
        jumpToAudit(this.d4shRecord.getTypeCode() == 0 ? 25 : 26);
    }

    public void setPrivacyListener(PrivacyListener privacyListener) {
        this.privacyListener = privacyListener;
    }
}

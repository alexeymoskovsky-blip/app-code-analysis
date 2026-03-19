package com.petkit.android.activities.virtual.d4sh.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Vibrator;
import android.text.SpannableString;
import android.text.TextUtils;
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
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.motion.widget.Key;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.binioter.guideview.Guide;
import com.binioter.guideview.GuideBuilder;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.Consts;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.utils.DeviceUtils;
import com.jess.arms.utils.ImageUtils;
import com.jess.arms.widget.PetkitToast;
import com.jess.arms.widget.imageloader.ImageCacheSimple;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.cloudservice.widget.CloudServiceView;
import com.petkit.android.activities.common.utils.DeviceCenterUtils;
import com.petkit.android.activities.feeder.model.DifferentFeedPlan;
import com.petkit.android.activities.home.utils.BlurUtils;
import com.petkit.android.activities.home.utils.GuideD4shBottomRightTipAndBtnAndLineComponent;
import com.petkit.android.activities.home.utils.GuideToiletBtnAndLineComponent;
import com.petkit.android.activities.mall.widget.PromoteView;
import com.petkit.android.activities.petkitBleDevice.BleDeviceBindNetWorkActivity;
import com.petkit.android.activities.petkitBleDevice.BleDeviceWifiSettingActivity;
import com.petkit.android.activities.petkitBleDevice.adapter.BasePetkitHighlightAdapter;
import com.petkit.android.activities.petkitBleDevice.d3.widget.BarChartView;
import com.petkit.android.activities.petkitBleDevice.d3.widget.BleFeederTimeView;
import com.petkit.android.activities.petkitBleDevice.d4.utils.D4Utils;
import com.petkit.android.activities.petkitBleDevice.d4s.mode.D4sStatistic;
import com.petkit.android.activities.petkitBleDevice.d4s.mode.D4shDateFeedData;
import com.petkit.android.activities.petkitBleDevice.d4s.utils.D4sUtils;
import com.petkit.android.activities.petkitBleDevice.d4s.widget.D4sHistogram;
import com.petkit.android.activities.petkitBleDevice.d4sh.D4shHomeActivity;
import com.petkit.android.activities.petkitBleDevice.d4sh.adapter.D4shAbstractHomeRecordAdapter;
import com.petkit.android.activities.petkitBleDevice.d4sh.adapter.RecordOnClickListener;
import com.petkit.android.activities.petkitBleDevice.d4sh.adapter.VirtualD4shVlogRecordAdapter;
import com.petkit.android.activities.petkitBleDevice.d4sh.listener.D4shHomeDeviceViewListener;
import com.petkit.android.activities.petkitBleDevice.d4sh.mode.AttireListResult;
import com.petkit.android.activities.petkitBleDevice.d4sh.mode.D4shDailyFeeds;
import com.petkit.android.activities.petkitBleDevice.d4sh.mode.D4shDayItem;
import com.petkit.android.activities.petkitBleDevice.d4sh.mode.D4shRecord;
import com.petkit.android.activities.petkitBleDevice.d4sh.mode.D4shRemoveData;
import com.petkit.android.activities.petkitBleDevice.d4sh.mode.D4shVideoRecord;
import com.petkit.android.activities.petkitBleDevice.d4sh.utils.D4shUtils;
import com.petkit.android.activities.petkitBleDevice.d4sh.utils.VlogUtils;
import com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4hFeedDataDetailInfoWindow;
import com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shDeviceDataCardViewGroup;
import com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shDeviceRecordTagViewGroup;
import com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shDeviceWarnMessageViewGroup;
import com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shFeedDataDetailInfoWindow;
import com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shMultipleVideoPlayer;
import com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shPlayerPortraitView;
import com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shPlayerSoundWaveView;
import com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shVlogTipWindow;
import com.petkit.android.activities.petkitBleDevice.download.mode.MediaMsg;
import com.petkit.android.activities.petkitBleDevice.download.utils.FileUtil;
import com.petkit.android.activities.petkitBleDevice.download.utils.VideoDownloadManager;
import com.petkit.android.activities.petkitBleDevice.mode.BleMenuItem;
import com.petkit.android.activities.petkitBleDevice.mode.HighlightRecord;
import com.petkit.android.activities.petkitBleDevice.mode.PetkitVideoSegment;
import com.petkit.android.activities.petkitBleDevice.r2.widget.NewIKnowWindow;
import com.petkit.android.activities.petkitBleDevice.t4.mode.RelatedProductsInfor;
import com.petkit.android.activities.petkitBleDevice.utils.FamilyUtils;
import com.petkit.android.activities.petkitBleDevice.utils.PlayerUtils;
import com.petkit.android.activities.petkitBleDevice.utils.TextUtil;
import com.petkit.android.activities.petkitBleDevice.utils.TimeUtils;
import com.petkit.android.activities.petkitBleDevice.w5.guide.ConfirmListener;
import com.petkit.android.activities.petkitBleDevice.w5.guide.GuideParam;
import com.petkit.android.activities.petkitBleDevice.widget.BleDeviceHomeOfflineWarnWindow;
import com.petkit.android.activities.petkitBleDevice.widget.D4shHomeRecyclerView;
import com.petkit.android.activities.petkitBleDevice.widget.PetkitLogoLoadingView;
import com.petkit.android.activities.petkitBleDevice.widget.PetkitScrollViewWithListener;
import com.petkit.android.activities.petkitBleDevice.widget.PetkitSlidingUpPanelLayout;
import com.petkit.android.activities.petkitBleDevice.widget.VirtualBleDeviceHomeMenuView;
import com.petkit.android.activities.petkitBleDevice.widget.datepicker.BasePetkitDeviceDatePickerView;
import com.petkit.android.activities.petkitBleDevice.widget.datepicker.DatePickerWindow;
import com.petkit.android.activities.petkitBleDevice.widget.petkit.PetkitPetEatingWindow;
import com.petkit.android.activities.statistics.widget.VerticalScrollView;
import com.petkit.android.activities.universalWindow.NormalCenterTipWindow;
import com.petkit.android.activities.virtual.d4sh.adapter.VirtualD4hHomeRecordAdapter;
import com.petkit.android.activities.virtual.d4sh.adapter.VirtualD4shHomeRecordAdapter;
import com.petkit.android.activities.virtual.widget.ExperienceMoreWindow;
import com.petkit.android.media.video.player.BasePetkitPlayerLandscapeViewClickListener;
import com.petkit.android.media.video.player.BasePetkitPlayerPortraitViewClickListener;
import com.petkit.android.media.video.player.ijkplayer.H3TextureView;
import com.petkit.android.media.video.player.ijkplayer.PetkitFeederDeviceRecordVideoPlayerInstance;
import com.petkit.android.media.video.player.ijkplayer.VideoPlayerView;
import com.petkit.android.media.video.player.listener.BasePetkitPlayerListener;
import com.petkit.android.model.ItemsBean;
import com.petkit.android.utils.AppVersionStateUtils;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.DateUtil;
import com.petkit.android.utils.LogcatStorageHelper;
import com.petkit.android.utils.PetkitLog;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.oversea.R;
import com.shopify.sample.util.MallUtils;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.io.File;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import net.sourceforge.pinyin4j.ChineseToPinyinResource;
import org.simple.eventbus.EventBus;

/* JADX INFO: loaded from: classes6.dex */
public class VirtualD4shHomeDeviceView extends LinearLayout implements PetkitSlidingUpPanelLayout.PanelSlideListener, View.OnClickListener, VirtualBleDeviceHomeMenuView.OnMenuClickListener, RecordOnClickListener, BleDeviceHomeOfflineWarnWindow.OfflineClickListener, BarChartView.ChartOnClickListener, D4sHistogram.ChartOnClickListener, VirtualBleDeviceHomeMenuView.OnD4shDeviceHomeMenuTouchListener, BasePetkitPlayerListener, BasePetkitPlayerPortraitViewClickListener, BasePetkitPlayerLandscapeViewClickListener, VirtualBleDeviceHomeMenuView.BatteryModeClickListener {
    private Activity activity;
    private float alpha;
    private Animation animation;
    private ValueAnimator animator;
    private List<AnimatorSet> animatorLeftSetList;
    private VirtualBleDeviceHomeMenuView bleMenuView;
    private int clickOuterIndex;
    private View contentView;
    private D4shDailyFeeds d4DailyFeeds;
    private NormalCenterTipWindow d4sFoodWarnWindow;
    private D4shRemoveData d4sRemoveData;
    private PetkitScrollViewWithListener d4sViewLayout;
    private D4shDeviceDataCardViewGroup d4shDeviceDataCardViewGroup;
    private String d4shDeviceRecordDateStr;
    private String d4shDeviceRecordDateText;
    private D4shDeviceRecordTagViewGroup d4shDeviceRecordTagViewGroup;
    private int d4shDeviceRecordTagViewGroupToTopFixedDistance;
    private D4shDeviceRecordTagViewGroup d4shDeviceRecordTagViewGroupTopFixed;
    private D4shDeviceWarnMessageViewGroup d4shDeviceWarnMessageViewGroup;
    private LinearLayout d4shHomeDeviceDataView;
    private D4shHomeDeviceViewListener d4shHomeDeviceViewListener;
    private D4shAbstractHomeRecordAdapter d4shHomeRecordAdapter;
    private LinearLayout d4shLandscapePlayerContainerView;
    private int d4shPlayerStatus;
    private D4shRecord d4shRecord;
    private VirtualD4shVlogRecordAdapter d4shVlogRecordAdapter;
    D4shVlogRecordAdapterListener d4shVlogRecordAdapterListener;
    private D4shVlogTipWindow d4shVlogTipWindow;
    private SimpleDateFormat dateFormat;
    private DatePickerWindow datePickerWindow;
    private int deepEnergySavingStatus;
    private int deletePosition;
    private NormalCenterTipWindow deleteRecordWindow;
    private String deviceUrl;
    private int displayPreviewVideoItemIndex;
    private Disposable disposable;
    private float eatVideoTimesSpeed;
    private PetkitPetEatingWindow eatingWindow;
    Guide firstGuide;
    private FrameLayout flTopPanel;
    private boolean flag;
    private boolean fullVideoExpireOrLoss;
    private Guide guide1;
    private Handler handler;
    private ImageView headerLoadingView;
    private ImageView imgD4Setting;
    private boolean invisible;
    private boolean isAniStart;
    boolean isEnable;
    private boolean isInit;
    private boolean isShowGuide;
    boolean isUp;
    private ImageView ivBackground;
    private ImageView ivClose;
    private ImageView ivDeepEnergySavingRunning;
    private ImageView ivLeftArrow;
    private ImageView ivLeftEar;
    private ImageView ivLeftEarClickView;
    private ImageView ivMealtimeDate;
    private ImageView ivProductIcon;
    private ImageView ivRightEar;
    private ImageView ivRightEarClickView;
    private ImageView ivTrialRemainingTimeClose;
    private ImageView ivUpArrow;
    private ImageView ivWarnText;
    private Guide lastGuide;
    float lastX;
    float lastY;
    private long leftTime;
    private LinearLayout llBottomMenuParentView;
    private LinearLayout llChartPanel;
    private LinearLayout llD4ViewBtnPanel;
    private LinearLayout llHistoryRecordPanel;
    private LinearLayout llManualFeedNum1;
    private LinearLayout llManualFeedNum2;
    private LinearLayout llTrialRemainingTime;
    private LinearLayout llWarnPanel;
    private LinearLayout llWarnText;
    private LinearLayout llWorkRecord;
    private Runnable loadFullVideoRunnable;
    private Context mContext;
    private float mSlideOffset;
    private VelocityTracker mVelocityTracker;
    private MainHandler mainHandler;
    private boolean manualCloseTrialRemainingTime;
    private MenuOnClickListener menuOnClickListener;
    private int offSet;
    private BasePetkitDeviceDatePickerView.OnCalendarChangeListener onCalendarChangeListener;
    public CloudServiceView.OnPurchaseBtnClickListener onPurchaseBtnClickListener;
    private LinearLayout petEatingView;
    private boolean playVideoInPrivacyMode;
    private D4shMultipleVideoPlayer player;
    private RelativeLayout playerContainer;
    private VirtualD4shHomePlayerLandscapeView playerLandscapeView;
    private D4shPlayerPortraitView playerPortraitView;
    private D4shPlayerSoundWaveView playerSoundWaveView;
    private PromoteView promoteView;
    private boolean pullFlag;
    private boolean ready;
    private int recordStartTime;
    private int recordType;
    private RelatedProductsInfor relatedProductsInfor;
    private RelativeLayout rlBtnAndWarnPanel;
    private RelativeLayout rlData1;
    private RelativeLayout rlData2;
    private RelativeLayout rlDeepEnergySaving;
    private RelativeLayout rlDeepEnergySavingInit;
    private RelativeLayout rlDeepEnergySavingRunning;
    private RelativeLayout rlMask;
    private RelativeLayout rlRightTopWindow;
    private RelativeLayout rlTipPanel;
    private RelativeLayout rlTitleStatus;
    private RelativeLayout rlTopView;
    private RelativeLayout rlViewD4DeviceCenter;
    private LinearLayout rlViewProduct;
    private D4shHomeRecyclerView rvD4RecordView;
    private RecyclerView rvHighlights;
    int scrollDistance;
    private RelativeLayout scrollView;
    private Guide secondGuide;
    private boolean serviceStatusMaybeChanged;
    private boolean showGuide;
    private float startDragY;
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
    private TextView tvHasOut1;
    private TextView tvHasOut2;
    private TextView tvImmediateRenewal;
    private TextView tvManualFeedNum1;
    private TextView tvManualFeedNum2;
    private TextView tvMealtimeDate;
    private TextView tvMore;
    private TextView tvName;
    private TextView tvPlanHasFinishedNum1;
    private TextView tvPlanHasFinishedNum2;
    private TextView tvProduct;
    private TextView tvTime;
    private TextView tvTitleStatus;
    private TextView tvTrialRemainingTime;
    private TextView tvWarnText;
    private VerticalScrollView verticalScrollView;
    private boolean videoExpireOrLoss;
    private View viewCenter;

    public interface ChartOnClickListener {
        void onChartClick(int i, String str);
    }

    public interface D4shVlogRecordAdapterListener {
        void onClickMore();

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
        FOOD_CONTROL
    }

    public interface OnHistogramPageChange {
        void pageChange(int i);
    }

    private void initCenterWindowParams(int i) {
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
    public void onCompareImageClick(D4shDayItem d4shDayItem) {
    }

    @Override // com.petkit.android.activities.petkitBleDevice.d4sh.adapter.RecordOnClickListener
    public void onDeleteCompareImage(int i, int i2, int i3) {
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
    public /* synthetic */ void onRecordAudit() {
        BasePetkitPlayerLandscapeViewClickListener.CC.$default$onRecordAudit(this);
    }

    @Override // com.petkit.android.media.video.player.listener.BasePetkitPlayerListener
    public void onRestart() {
    }

    @Override // com.petkit.android.media.video.player.BasePetkitPlayerLandscapeViewClickListener
    public void onSaveImage() {
    }

    @Override // com.petkit.android.media.video.player.listener.BasePetkitPlayerListener
    public void onSeekCompleted() {
    }

    @Override // com.petkit.android.media.video.player.listener.BasePetkitPlayerListener
    public void onStartPlay() {
    }

    @Override // com.petkit.android.activities.petkitBleDevice.d4sh.adapter.RecordOnClickListener
    public void onTitleClick(D4shDayItem d4shDayItem) {
    }

    @Override // com.petkit.android.activities.petkitBleDevice.d4sh.adapter.RecordOnClickListener
    public void onViewClick(ItemsBean itemsBean, int i) {
    }

    public void setCurrentItem(int i) {
    }

    public void setFeedingAmount(int i) {
    }

    public VirtualD4shHomeDeviceView(Context context) {
        this(context, null);
    }

    public VirtualD4shHomeDeviceView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public VirtualD4shHomeDeviceView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.isShowGuide = false;
        this.guide1 = null;
        this.dateFormat = new SimpleDateFormat(DateUtil.DATE_FORMAT_13);
        this.recordType = 3;
        this.d4sRemoveData = new D4shRemoveData();
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
        this.loadFullVideoRunnable = new Runnable() { // from class: com.petkit.android.activities.virtual.d4sh.widget.VirtualD4shHomeDeviceView.30
            @Override // java.lang.Runnable
            public void run() {
                VirtualD4shHomeDeviceView.this.playerLandscapeView.refreshFullVideoStatus();
            }
        };
        this.mContext = context;
        this.activity = (Activity) context;
        initView();
    }

    public void setd4shHomeDeviceViewListener(D4shHomeDeviceViewListener d4shHomeDeviceViewListener) {
        this.d4shHomeDeviceViewListener = d4shHomeDeviceViewListener;
    }

    private void initView() {
        this.contentView = ((ViewGroup) LayoutInflater.from(this.mContext).inflate(R.layout.layout_d4sh_home_device_virtual_view, this)).getChildAt(0);
        initPlayer();
        initDeviceWarnMessageViewGroup();
        initDeviceDataCardViewGroup();
        initDeviceRecordTagViewGroup();
        initToolbarView();
        initPetEatingView();
        initOtherView();
        initScrollView();
        initProductView();
        findViewById(R.id.d4sh_device_record_date_image_view).setVisibility(8);
        findViewById(R.id.calendar_image_view).setVisibility(8);
    }

    private void initProductView() {
        this.rlViewProduct = (LinearLayout) findViewById(R.id.rl_view_product);
        this.ivProductIcon = (ImageView) findViewById(R.id.iv_product_icon);
        this.tvProduct = (TextView) findViewById(R.id.tv_product);
        if (TextUtils.isEmpty(this.deviceUrl)) {
            this.rlViewProduct.setVisibility(8);
        } else {
            this.rlViewProduct.setVisibility(0);
        }
        this.rlViewProduct.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.virtual.d4sh.widget.VirtualD4shHomeDeviceView$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initProductView$0(view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initProductView$0(View view) {
        MallUtils.goToWebOrProductDetail(this.mContext, this.deviceUrl, 2);
    }

    public void initGuide() {
        if (!DataHelper.getBooleanSF(this.mContext, Constants.D4SH_HOME_GUIDE)) {
            this.mainHandler.postDelayed(new Runnable() { // from class: com.petkit.android.activities.virtual.d4sh.widget.VirtualD4shHomeDeviceView.1
                @Override // java.lang.Runnable
                public void run() {
                    if (VirtualD4shHomeDeviceView.this.firstGuide != null) {
                        return;
                    }
                    GuideBuilder guideBuilder = new GuideBuilder();
                    guideBuilder.setTargetView(VirtualD4shHomeDeviceView.this.player).setAlpha(180).setHighTargetCorner(50).setHighTargetPaddingLeft(ArmsUtils.dip2px(VirtualD4shHomeDeviceView.this.mContext, 0.0f)).setHighTargetPaddingRight(ArmsUtils.dip2px(VirtualD4shHomeDeviceView.this.mContext, 0.0f)).setHighTargetPaddingBottom(ArmsUtils.dip2px(VirtualD4shHomeDeviceView.this.mContext, 0.0f)).setHighTargetPaddingTop(ArmsUtils.dip2px(VirtualD4shHomeDeviceView.this.mContext, 0.0f)).setOutsideTouchable(false).setAutoDismiss(true);
                    guideBuilder.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() { // from class: com.petkit.android.activities.virtual.d4sh.widget.VirtualD4shHomeDeviceView.1.1
                        @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
                        public void onShown() {
                        }

                        @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
                        public void onDismiss() {
                            VirtualD4shHomeDeviceView.this.showSecondGuide();
                        }
                    });
                    guideBuilder.addComponent(new GuideD4shBottomRightTipAndBtnAndLineComponent(new GuideParam(VirtualD4shHomeDeviceView.this.getResources().getString(R.string.D4sh_home_guide_prompt_one), "1/4", 4, 48, ArmsUtils.dip2px(VirtualD4shHomeDeviceView.this.mContext, -30.0f), 0, VirtualD4shHomeDeviceView.this.getResources().getString(R.string.Next_tip), R.layout.layout_d4sh_guide_bottom_right), new ConfirmListener() { // from class: com.petkit.android.activities.virtual.d4sh.widget.VirtualD4shHomeDeviceView.1.2
                        @Override // com.petkit.android.activities.petkitBleDevice.w5.guide.ConfirmListener
                        public void onConfirmListener() {
                            Guide guide = VirtualD4shHomeDeviceView.this.firstGuide;
                            if (guide != null) {
                                guide.dismiss();
                            }
                        }
                    }));
                    VirtualD4shHomeDeviceView.this.firstGuide = guideBuilder.createGuide();
                    VirtualD4shHomeDeviceView virtualD4shHomeDeviceView = VirtualD4shHomeDeviceView.this;
                    virtualD4shHomeDeviceView.firstGuide.show((Activity) virtualD4shHomeDeviceView.getContext());
                    VirtualD4shHomeDeviceView.this.showGuide = true;
                }
            }, 500L);
            return;
        }
        D4shHomeDeviceViewListener d4shHomeDeviceViewListener = this.d4shHomeDeviceViewListener;
        if (d4shHomeDeviceViewListener != null) {
            d4shHomeDeviceViewListener.guideShowed();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showSecondGuide() {
        if (this.secondGuide != null) {
            return;
        }
        GuideBuilder guideBuilder = new GuideBuilder();
        guideBuilder.setTargetView(this.d4shDeviceDataCardViewGroup).setAlpha(180).setHighTargetCorner(50).setHighTargetPaddingLeft(ArmsUtils.dip2px(this.mContext, 0.0f)).setHighTargetPaddingRight(ArmsUtils.dip2px(this.mContext, 0.0f)).setHighTargetPaddingBottom(ArmsUtils.dip2px(this.mContext, 0.0f)).setHighTargetPaddingTop(ArmsUtils.dip2px(this.mContext, 0.0f)).setOutsideTouchable(false).setAutoDismiss(true);
        guideBuilder.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() { // from class: com.petkit.android.activities.virtual.d4sh.widget.VirtualD4shHomeDeviceView.2
            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onShown() {
            }

            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onDismiss() {
                VirtualD4shHomeDeviceView.this.showThirdGuide();
            }
        });
        guideBuilder.addComponent(new GuideD4shBottomRightTipAndBtnAndLineComponent(new GuideParam(getResources().getString(R.string.D4sh_home_guide_prompt_two), "2/4", 4, 48, ArmsUtils.dip2px(this.mContext, -30.0f), 0, getResources().getString(R.string.Next_tip), R.layout.layout_d4sh_guide_bottom_right), new ConfirmListener() { // from class: com.petkit.android.activities.virtual.d4sh.widget.VirtualD4shHomeDeviceView.3
            @Override // com.petkit.android.activities.petkitBleDevice.w5.guide.ConfirmListener
            public void onConfirmListener() {
                if (VirtualD4shHomeDeviceView.this.secondGuide != null) {
                    VirtualD4shHomeDeviceView.this.secondGuide.dismiss();
                }
            }
        }));
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
        findViewById(R.id.d4sh_device_record_date_image_view).getLocationOnScreen(iArr);
        if ((iArr[1] - i) + findViewById(R.id.d4sh_device_record_date_image_view).getHeight() > this.bleMenuView.getTop()) {
            this.d4sViewLayout.scrollBy(0, ArmsUtils.dip2px(this.mContext, 100.0f));
        }
        GuideBuilder guideBuilder = new GuideBuilder();
        guideBuilder.setTargetView(findViewById(R.id.d4sh_device_record_date_image_view)).setAlpha(180).setHighTargetCorner(50).setHighTargetPaddingLeft(ArmsUtils.dip2px(this.mContext, 3.0f)).setHighTargetPaddingRight(ArmsUtils.dip2px(this.mContext, 3.0f)).setHighTargetPaddingBottom(ArmsUtils.dip2px(this.mContext, 3.0f)).setHighTargetPaddingTop(ArmsUtils.dip2px(this.mContext, 3.0f)).setOutsideTouchable(false).setAutoDismiss(true);
        guideBuilder.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() { // from class: com.petkit.android.activities.virtual.d4sh.widget.VirtualD4shHomeDeviceView.4
            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onShown() {
            }

            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onDismiss() {
                VirtualD4shHomeDeviceView.this.showLastGuide();
            }
        });
        guideBuilder.addComponent(new GuideD4shBottomRightTipAndBtnAndLineComponent(new GuideParam(getResources().getString(R.string.D4sh_home_guide_prompt_three), "3/4", 4, 48, ArmsUtils.dip2px(this.mContext, 0.0f), 0, getResources().getString(R.string.Next_tip), R.layout.layout_d4sh_guide_bottom_left), new ConfirmListener() { // from class: com.petkit.android.activities.virtual.d4sh.widget.VirtualD4shHomeDeviceView.5
            @Override // com.petkit.android.activities.petkitBleDevice.w5.guide.ConfirmListener
            public void onConfirmListener() {
                if (VirtualD4shHomeDeviceView.this.thirdGuide != null) {
                    VirtualD4shHomeDeviceView.this.thirdGuide.dismiss();
                }
            }
        }));
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
        guideBuilder.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() { // from class: com.petkit.android.activities.virtual.d4sh.widget.VirtualD4shHomeDeviceView.6
            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onShown() {
            }

            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onDismiss() {
                DataHelper.setBooleanSF(VirtualD4shHomeDeviceView.this.mContext, Constants.D4SH_HOME_GUIDE, Boolean.TRUE);
                VirtualD4shHomeDeviceView.this.showGuide = false;
                if (VirtualD4shHomeDeviceView.this.d4shHomeDeviceViewListener != null) {
                    VirtualD4shHomeDeviceView.this.d4shHomeDeviceViewListener.guideShowed();
                }
            }
        });
        guideBuilder.addComponent(new GuideD4shBottomRightTipAndBtnAndLineComponent(new GuideParam(getResources().getString(R.string.D4sh_home_guide_prompt_four), "4/4", 2, 16, ArmsUtils.dip2px(this.mContext, 16.0f), 0, getResources().getString(R.string.Know), R.layout.layout_d4sh_guide_top_right), new ConfirmListener() { // from class: com.petkit.android.activities.virtual.d4sh.widget.VirtualD4shHomeDeviceView.7
            @Override // com.petkit.android.activities.petkitBleDevice.w5.guide.ConfirmListener
            public void onConfirmListener() {
                if (VirtualD4shHomeDeviceView.this.lastGuide != null) {
                    VirtualD4shHomeDeviceView.this.lastGuide.dismiss();
                }
            }
        }));
        Guide guideCreateGuide = guideBuilder.createGuide();
        this.lastGuide = guideCreateGuide;
        guideCreateGuide.show((Activity) getContext());
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
        this.promoteView = (PromoteView) this.contentView.findViewById(R.id.promoteView);
        this.rlTitleStatus = (RelativeLayout) findViewById(R.id.rl_title_status);
        this.tvTitleStatus = (TextView) findViewById(R.id.tv_title_status);
        this.rlDeepEnergySavingRunning = (RelativeLayout) findViewById(R.id.rl_deep_energy_saving_running);
        this.rlDeepEnergySaving = (RelativeLayout) findViewById(R.id.rl_deep_energy_saving);
        this.ivDeepEnergySavingRunning = (ImageView) findViewById(R.id.iv_deep_energy_saving_running);
        this.rlDeepEnergySavingInit = (RelativeLayout) findViewById(R.id.rl_deep_energy_saving_init);
        this.tvDeepEnergySavingInitTime = (TextView) findViewById(R.id.tv_deep_energy_saving_init_time);
        this.tvDeepEnergySavingInitCancle = (TextView) findViewById(R.id.tv_deep_energy_saving_init_cancle);
        this.tvManualFeedNum1 = (TextView) findViewById(R.id.tv_manual_feed_num1);
        this.tvManualFeedNum2 = (TextView) findViewById(R.id.tv_manual_feed_num2);
        this.tvPlanHasFinishedNum1 = (TextView) findViewById(R.id.tv_plan_has_been_finished_num1);
        this.tvPlanHasFinishedNum2 = (TextView) findViewById(R.id.tv_plan_has_been_finished_num2);
        this.rlData1 = (RelativeLayout) findViewById(R.id.rl_data1);
        this.rlData2 = (RelativeLayout) findViewById(R.id.rl_data2);
        this.tvHasOut1 = (TextView) findViewById(R.id.tv_has_out1);
        this.tvHasOut2 = (TextView) findViewById(R.id.tv_has_out2);
        this.d4sViewLayout = (PetkitScrollViewWithListener) findViewById(R.id.d4s_view_layout);
        this.llManualFeedNum1 = (LinearLayout) findViewById(R.id.ll_manual_feed_num1);
        this.llManualFeedNum2 = (LinearLayout) findViewById(R.id.ll_manual_feed_num2);
        this.llChartPanel = (LinearLayout) findViewById(R.id.ll_chart_panel);
        this.llWarnPanel = (LinearLayout) findViewById(R.id.ll_warn_panel);
        this.rlViewD4DeviceCenter = (RelativeLayout) findViewById(R.id.rl_view_d4_device_center);
        this.viewCenter = findViewById(R.id.view_center);
        this.tvD4PlanDes = (TextView) findViewById(R.id.tv_d4_plan_des);
        this.ivWarnText = (ImageView) findViewById(R.id.iv_warn_text);
        this.rlBtnAndWarnPanel = (RelativeLayout) findViewById(R.id.rl_btn_and_warn_panel);
        this.ivUpArrow = (ImageView) findViewById(R.id.iv_up_arrow);
        this.flTopPanel = (FrameLayout) findViewById(R.id.fl_top_panel);
        VirtualBleDeviceHomeMenuView virtualBleDeviceHomeMenuView = (VirtualBleDeviceHomeMenuView) findViewById(R.id.ble_menu_view);
        this.bleMenuView = virtualBleDeviceHomeMenuView;
        virtualBleDeviceHomeMenuView.setIsVirtual(true);
        D4shHomeRecyclerView d4shHomeRecyclerView = (D4shHomeRecyclerView) findViewById(R.id.rv_d4_recordView);
        this.rvD4RecordView = d4shHomeRecyclerView;
        d4shHomeRecyclerView.setLayoutManager(new LinearLayoutManager(this.mContext));
        this.rvD4RecordView.getLayoutManager().setAutoMeasureEnabled(false);
        this.llHistoryRecordPanel = (LinearLayout) findViewById(R.id.ll_history_record_panel);
        this.llBottomMenuParentView = (LinearLayout) findViewById(R.id.ll_bottom_menu_parent_view);
        this.tvMealtimeDate = (TextView) findViewById(R.id.tv_mealtime_date);
        this.ivMealtimeDate = (ImageView) findViewById(R.id.iv_mealtime_date);
        this.animatorLeftSetList = new ArrayList();
        this.mVelocityTracker = VelocityTracker.obtain();
        this.mainHandler = new MainHandler(this.mContext);
        this.handler = new Handler(Looper.getMainLooper());
        this.ivBackground = (ImageView) findViewById(R.id.iv_background);
        this.ivLeftEar = (ImageView) findViewById(R.id.iv_left_ear);
        this.ivRightEar = (ImageView) findViewById(R.id.iv_right_ear);
        this.ivLeftEarClickView = (ImageView) findViewById(R.id.iv_left_ear_click_view);
        this.ivRightEarClickView = (ImageView) findViewById(R.id.iv_right_ear_click_view);
        this.ivLeftArrow = (ImageView) findViewById(R.id.iv_left_arrow);
        this.tvD4RealFeed = (TextView) findViewById(R.id.tv_d4_real_feed);
        this.tvD4PlanFeed = (TextView) findViewById(R.id.tv_d4_plan_feed);
        this.llTrialRemainingTime = (LinearLayout) findViewById(R.id.ll_trial_remaining_time);
        this.tvTrialRemainingTime = (TextView) findViewById(R.id.tv_trial_remaining_time);
        this.ivTrialRemainingTimeClose = (ImageView) findViewById(R.id.iv_trial_remaining_time_close);
        this.bleMenuView.setDeviceType(25);
        this.bleMenuView.setOnMenuClickListener(this);
        this.bleMenuView.setBatteryModeClickListener(this);
        this.bleMenuView.setOnD4shDeviceHomeMenuTouchListener(this);
        this.ivMealtimeDate.setOnClickListener(this);
        this.tvMore.setOnClickListener(this);
        this.rlDeepEnergySavingRunning.setOnClickListener(this);
        this.tvDeepEnergySavingInitCancle.setOnClickListener(this);
        this.rlTitleStatus.setOnClickListener(this);
        this.d4sViewLayout.setScrollviewOnTouchListener(new PetkitScrollViewWithListener.ScrollviewOnTouchListener() { // from class: com.petkit.android.activities.virtual.d4sh.widget.VirtualD4shHomeDeviceView.8
            @Override // com.petkit.android.activities.petkitBleDevice.widget.PetkitScrollViewWithListener.ScrollviewOnTouchListener
            public void onScrolling(int i, int i2, int i3, int i4) {
                int i5;
                if (VirtualD4shHomeDeviceView.this.showGuide) {
                    return;
                }
                VirtualD4shHomeDeviceView.this.setD4shDeviceRecordTagViewGroupTopFixedVisible();
                if (i2 - i4 > 5) {
                    Log.d("scrolling", "up");
                    VirtualD4shHomeDeviceView virtualD4shHomeDeviceView = VirtualD4shHomeDeviceView.this;
                    virtualD4shHomeDeviceView.isUp = true;
                    if (!virtualD4shHomeDeviceView.isAniStart && VirtualD4shHomeDeviceView.this.bleMenuView.getVisibility() == 0) {
                        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(VirtualD4shHomeDeviceView.this.bleMenuView, Key.TRANSLATION_Y, 0.0f, VirtualD4shHomeDeviceView.this.bleMenuView.getMeasuredHeight());
                        objectAnimatorOfFloat.setDuration(400L);
                        objectAnimatorOfFloat.addListener(new Animator.AnimatorListener() { // from class: com.petkit.android.activities.virtual.d4sh.widget.VirtualD4shHomeDeviceView.8.1
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
                                VirtualD4shHomeDeviceView.this.isAniStart = false;
                                VirtualD4shHomeDeviceView.this.bleMenuView.setVisibility(8);
                                VirtualD4shHomeDeviceView.this.rlViewProduct.setVisibility(8);
                            }
                        });
                        objectAnimatorOfFloat.start();
                        VirtualD4shHomeDeviceView.this.isAniStart = true;
                    }
                } else {
                    VirtualD4shHomeDeviceView.this.isUp = false;
                }
                if (VirtualD4shHomeDeviceView.this.d4shHomeRecordAdapter.getDateDataList() != null) {
                    int i6 = 0;
                    while (true) {
                        if (i6 >= VirtualD4shHomeDeviceView.this.d4shHomeRecordAdapter.getDateDataList().size()) {
                            i5 = 0;
                            break;
                        } else {
                            if (VirtualD4shHomeDeviceView.this.d4shHomeRecordAdapter.getDateDataList().get(i6).getType() == 2) {
                                i5 = i6 + 1;
                                break;
                            }
                            i6++;
                        }
                    }
                    if (i5 == 0) {
                        return;
                    }
                    View viewFindViewByPosition = VirtualD4shHomeDeviceView.this.rvD4RecordView.getLayoutManager().findViewByPosition(i5 - 1);
                    VirtualD4shHomeDeviceView.this.d4sViewLayout.setScrollParams(i5, VirtualD4shHomeDeviceView.this.d4sViewLayout.getScrollY(), VirtualD4shHomeDeviceView.this.d4shDeviceRecordTagViewGroupTopFixed.getHeight(), VirtualD4shHomeDeviceView.this.bleMenuView.getTop() - VirtualD4shHomeDeviceView.this.contentView.getTop(), VirtualD4shHomeDeviceView.this.d4shRecord.getDeviceShared() != null);
                    if (viewFindViewByPosition == null || ((((viewFindViewByPosition.getTop() + viewFindViewByPosition.getHeight()) + VirtualD4shHomeDeviceView.this.rvD4RecordView.getTop()) + ((RelativeLayout) VirtualD4shHomeDeviceView.this.rvD4RecordView.getParent()).getTop()) - VirtualD4shHomeDeviceView.this.d4sViewLayout.getScrollY()) - VirtualD4shHomeDeviceView.this.rvD4RecordView.computeVerticalScrollOffset() >= VirtualD4shHomeDeviceView.this.bleMenuView.getTop() - VirtualD4shHomeDeviceView.this.contentView.getTop() || DataHelper.getBooleanSF(VirtualD4shHomeDeviceView.this.mContext, Consts.D4S_EAT_RECORD_IS_FIRST) || VirtualD4shHomeDeviceView.this.d4shRecord == null || VirtualD4shHomeDeviceView.this.d4shRecord.getDeviceShared() != null || VirtualD4shHomeDeviceView.this.isShowGuide) {
                        return;
                    }
                    VirtualD4shHomeDeviceView.this.d4sViewLayout.fling(0);
                    VirtualD4shHomeDeviceView.this.rvD4RecordView.stopScroll();
                    VirtualD4shHomeDeviceView.this.d4sViewLayout.setOnTouchListener(new View.OnTouchListener() { // from class: com.petkit.android.activities.virtual.d4sh.widget.VirtualD4shHomeDeviceView.8.2
                        @Override // android.view.View.OnTouchListener
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            return true;
                        }
                    });
                    VirtualD4shHomeDeviceView.this.isShowGuide = true;
                    VirtualD4shHomeDeviceView.this.showGuideView(viewFindViewByPosition);
                }
            }

            @Override // com.petkit.android.activities.petkitBleDevice.widget.PetkitScrollViewWithListener.ScrollviewOnTouchListener
            public void finishScroll(boolean z) {
                Log.d("scrolling", z + ",isLandscape:" + VirtualD4shHomeDeviceView.this.isLandscape());
                if (VirtualD4shHomeDeviceView.this.isLandscape()) {
                    return;
                }
                VirtualD4shHomeDeviceView.this.setD4shDeviceRecordTagViewGroupTopFixedVisible();
                VirtualD4shHomeDeviceView.this.setD4shDeviceRecordAdapterTopVideoItemInScreen();
                if (VirtualD4shHomeDeviceView.this.isAniStart || VirtualD4shHomeDeviceView.this.bleMenuView.getVisibility() == 8) {
                    ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(VirtualD4shHomeDeviceView.this.bleMenuView, Key.TRANSLATION_Y, VirtualD4shHomeDeviceView.this.bleMenuView.getMeasuredHeight(), 0.0f);
                    objectAnimatorOfFloat.setDuration(400L);
                    objectAnimatorOfFloat.addListener(new Animator.AnimatorListener() { // from class: com.petkit.android.activities.virtual.d4sh.widget.VirtualD4shHomeDeviceView.8.3
                        @Override // android.animation.Animator.AnimatorListener
                        public void onAnimationCancel(Animator animator) {
                        }

                        @Override // android.animation.Animator.AnimatorListener
                        public void onAnimationRepeat(Animator animator) {
                        }

                        @Override // android.animation.Animator.AnimatorListener
                        public void onAnimationStart(Animator animator) {
                            if (VirtualD4shHomeDeviceView.this.isLandscape()) {
                                return;
                            }
                            VirtualD4shHomeDeviceView.this.bleMenuView.setVisibility(0);
                            if (TextUtils.isEmpty(VirtualD4shHomeDeviceView.this.deviceUrl)) {
                                VirtualD4shHomeDeviceView.this.rlViewProduct.setVisibility(8);
                            } else {
                                VirtualD4shHomeDeviceView.this.rlViewProduct.setVisibility(0);
                            }
                        }

                        @Override // android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator animator) {
                            VirtualD4shHomeDeviceView.this.isAniStart = false;
                        }
                    });
                    if (VirtualD4shHomeDeviceView.this.isLandscape()) {
                        objectAnimatorOfFloat.start();
                    } else if (z) {
                        objectAnimatorOfFloat.start();
                    }
                }
            }
        });
        this.activity.getWindow().getDecorView().setSystemUiVisibility(0);
    }

    private void initScrollView() {
        this.headerLoadingView = (ImageView) findViewById(R.id.header_loading_view);
        final SpringAnimation springAnimation = new SpringAnimation(this.d4sViewLayout, DynamicAnimation.TRANSLATION_Y, 0.0f);
        springAnimation.getSpring().setStiffness(800.0f);
        springAnimation.getSpring().setDampingRatio(0.5f);
        this.d4sViewLayout.setOnTouchListener(new View.OnTouchListener() { // from class: com.petkit.android.activities.virtual.d4sh.widget.VirtualD4shHomeDeviceView.9
            /* JADX WARN: Removed duplicated region for block: B:54:0x01a3  */
            @Override // android.view.View.OnTouchListener
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public boolean onTouch(android.view.View r6, android.view.MotionEvent r7) {
                /*
                    Method dump skipped, instruction units count: 509
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.petkit.android.activities.virtual.d4sh.widget.VirtualD4shHomeDeviceView.AnonymousClass9.onTouch(android.view.View, android.view.MotionEvent):boolean");
            }
        });
    }

    public void onRefreshStart(int i) {
        PetkitLog.d("onRefreshStart", "dy:" + i);
        ViewGroup.LayoutParams layoutParams = this.headerLoadingView.getLayoutParams();
        int i2 = i / 2;
        layoutParams.height = layoutParams.height + i2 > ArmsUtils.dip2px(getContext(), 50.0f) ? ArmsUtils.dip2px(getContext(), 50.0f) : i2 + layoutParams.height;
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
        this.animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.petkit.android.activities.virtual.d4sh.widget.VirtualD4shHomeDeviceView.10
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int iIntValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                ViewGroup.LayoutParams layoutParams = VirtualD4shHomeDeviceView.this.headerLoadingView.getLayoutParams();
                int i2 = i;
                layoutParams.height = i2 - ((iIntValue * i2) / 100);
                VirtualD4shHomeDeviceView.this.headerLoadingView.setLayoutParams(layoutParams);
            }
        });
        this.animator.addListener(new Animator.AnimatorListener() { // from class: com.petkit.android.activities.virtual.d4sh.widget.VirtualD4shHomeDeviceView.11
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

    @SuppressLint({"WrongViewCast"})
    private void initPlayer() {
        this.rlMask = (RelativeLayout) findViewById(R.id.rl_mask);
        D4shMultipleVideoPlayer d4shMultipleVideoPlayer = (D4shMultipleVideoPlayer) findViewById(R.id.d4sh_player);
        this.player = d4shMultipleVideoPlayer;
        d4shMultipleVideoPlayer.setPlayerListener(this);
        this.player.post(new Runnable() { // from class: com.petkit.android.activities.virtual.d4sh.widget.VirtualD4shHomeDeviceView$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$initPlayer$1();
            }
        });
        this.rlMask.post(new Runnable() { // from class: com.petkit.android.activities.virtual.d4sh.widget.VirtualD4shHomeDeviceView$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$initPlayer$2();
            }
        });
        PetkitFeederDeviceRecordVideoPlayerInstance.getInstance().initPlayer(this.activity);
        D4shPlayerPortraitView d4shPlayerPortraitView = new D4shPlayerPortraitView(this.mContext);
        this.playerPortraitView = d4shPlayerPortraitView;
        d4shPlayerPortraitView.setViewClickListener(this);
        this.player.addPortraitView(this.playerPortraitView);
        VirtualD4shHomePlayerLandscapeView virtualD4shHomePlayerLandscapeView = new VirtualD4shHomePlayerLandscapeView(this.mContext);
        this.playerLandscapeView = virtualD4shHomePlayerLandscapeView;
        virtualD4shHomePlayerLandscapeView.setViewClickListener(this);
        this.player.addLandscapeView(this.playerLandscapeView);
        D4shPlayerSoundWaveView d4shPlayerSoundWaveView = (D4shPlayerSoundWaveView) findViewById(R.id.d4sh_home_player_sound_wave_view);
        this.playerSoundWaveView = d4shPlayerSoundWaveView;
        d4shPlayerSoundWaveView.setVisibility(4);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.playerSoundWaveView.getLayoutParams();
        layoutParams.width = (int) (BaseApplication.displayMetrics.widthPixels * 0.3f);
        this.playerSoundWaveView.setLayoutParams(layoutParams);
        this.playerContainer = (RelativeLayout) findViewById(R.id.d4sh_player_container);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initPlayer$1() {
        int iRound = Math.round((this.player.getWidth() * 10.0f) / 16.0f);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.player.getLayoutParams();
        layoutParams.height = iRound;
        this.player.setLayoutParams(layoutParams);
        this.player.initTextureViewSize();
        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) this.ivLeftEar.getLayoutParams();
        int width = (int) ((this.player.getWidth() / 343.0f) * 100.0f);
        layoutParams2.width = width;
        layoutParams2.height = (int) ((width / 100.0f) * 120.0f);
        int iDip2px = ArmsUtils.dip2px(this.mContext, 50.0f) + iRound;
        int i = (int) ((iRound / 214.0f) * 139.0f);
        int i2 = iDip2px - i;
        if (i2 > layoutParams2.height) {
            layoutParams2.addRule(12);
            layoutParams2.setMargins(layoutParams2.leftMargin, layoutParams2.topMargin, layoutParams2.rightMargin, ArmsUtils.dip2px(this.mContext, 0.5f) + i);
        } else {
            layoutParams2.addRule(10);
            layoutParams2.setMargins(layoutParams2.leftMargin, (i2 - layoutParams2.height) - ArmsUtils.dip2px(this.mContext, 4.0f), layoutParams2.rightMargin, layoutParams2.bottomMargin);
        }
        this.ivLeftEar.setLayoutParams(layoutParams2);
        RelativeLayout.LayoutParams layoutParams3 = (RelativeLayout.LayoutParams) this.ivLeftEarClickView.getLayoutParams();
        int width2 = (int) ((this.player.getWidth() / 343.0f) * 100.0f);
        layoutParams3.width = width2;
        int iDip2px2 = ((int) ((width2 / 100.0f) * 120.0f)) - ArmsUtils.dip2px(this.mContext, 50.0f);
        layoutParams3.height = iDip2px2;
        if (i2 > iDip2px2 + ArmsUtils.dip2px(this.mContext, 50.0f)) {
            layoutParams3.addRule(12);
            layoutParams3.setMargins(layoutParams3.leftMargin, layoutParams3.topMargin, layoutParams3.rightMargin, ArmsUtils.dip2px(this.mContext, 0.5f) + i);
        } else {
            layoutParams3.addRule(10);
            layoutParams3.setMargins(layoutParams3.leftMargin, (i2 - layoutParams3.height) - ArmsUtils.dip2px(this.mContext, 4.0f), layoutParams3.rightMargin, layoutParams3.bottomMargin);
        }
        this.ivLeftEarClickView.setLayoutParams(layoutParams3);
        RelativeLayout.LayoutParams layoutParams4 = (RelativeLayout.LayoutParams) this.ivRightEar.getLayoutParams();
        int width3 = (int) ((this.player.getWidth() / 343.0f) * 100.0f);
        layoutParams4.width = width3;
        int i3 = (int) ((width3 / 100.0f) * 120.0f);
        layoutParams4.height = i3;
        if (i2 > i3) {
            layoutParams4.addRule(12);
            layoutParams4.setMargins(layoutParams4.leftMargin, layoutParams4.topMargin, layoutParams4.rightMargin, ArmsUtils.dip2px(this.mContext, 0.5f) + i);
        } else {
            layoutParams4.addRule(10);
            layoutParams4.setMargins(layoutParams4.leftMargin, (i2 - layoutParams4.height) - ArmsUtils.dip2px(this.mContext, 4.0f), layoutParams4.rightMargin, layoutParams4.bottomMargin);
        }
        this.ivRightEar.setLayoutParams(layoutParams4);
        RelativeLayout.LayoutParams layoutParams5 = (RelativeLayout.LayoutParams) this.ivRightEarClickView.getLayoutParams();
        int width4 = (int) ((this.player.getWidth() / 343.0f) * 100.0f);
        layoutParams5.width = width4;
        int iDip2px3 = ((int) ((width4 / 100.0f) * 120.0f)) - ArmsUtils.dip2px(this.mContext, 50.0f);
        layoutParams5.height = iDip2px3;
        if (i2 > iDip2px3 + ArmsUtils.dip2px(this.mContext, 50.0f)) {
            layoutParams5.addRule(12);
            layoutParams5.setMargins(layoutParams5.leftMargin, layoutParams5.topMargin, layoutParams5.rightMargin, i + ArmsUtils.dip2px(this.mContext, 0.5f));
        } else {
            layoutParams5.addRule(10);
            layoutParams5.setMargins(layoutParams5.leftMargin, (i2 - layoutParams5.height) - ArmsUtils.dip2px(this.mContext, 4.0f), layoutParams5.rightMargin, layoutParams5.bottomMargin);
        }
        this.ivRightEarClickView.setLayoutParams(layoutParams5);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initPlayer$2() {
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
        d4shDeviceRecordTagViewGroup2.setCalendarImageViewVisibility(8);
        this.d4shDeviceRecordTagViewGroupTopFixed.setDateTitleViewVisibility(new View.OnClickListener() { // from class: com.petkit.android.activities.virtual.d4sh.widget.VirtualD4shHomeDeviceView$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initDeviceRecordTagViewGroup$3(view);
            }
        }, new View.OnClickListener() { // from class: com.petkit.android.activities.virtual.d4sh.widget.VirtualD4shHomeDeviceView$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initDeviceRecordTagViewGroup$4(view);
            }
        });
        this.d4shDeviceRecordTagViewGroupTopFixed.setD4shDeviceRecordTagViewGroupClickListener(this, this);
        this.d4shDeviceRecordTagViewGroupTopFixed.setDeviceRecordDateImageViewVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initDeviceRecordTagViewGroup$3(View view) {
        ((BaseActivity) this.activity).killMyself();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initDeviceRecordTagViewGroup$4(View view) {
        showMoreWindow();
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
        if (file == null) {
            this.ivLeftEar.setVisibility(4);
        } else {
            this.ivLeftEar.setVisibility(0);
            ((BaseApplication) CommonUtils.getApplication()).getAppComponent().imageLoader().autoLoadImage(CommonUtils.getAppContext(), GlideImageConfig.builder().url(file.getAbsolutePath()).newListener(new RequestListener<String, GlideDrawable>() { // from class: com.petkit.android.activities.virtual.d4sh.widget.VirtualD4shHomeDeviceView.12
                @Override // com.bumptech.glide.request.RequestListener
                public boolean onException(Exception exc, String str, Target<GlideDrawable> target, boolean z) {
                    return false;
                }

                @Override // com.bumptech.glide.request.RequestListener
                public boolean onResourceReady(GlideDrawable glideDrawable, String str, Target<GlideDrawable> target, boolean z, boolean z2) {
                    return false;
                }
            }).imageView(this.ivLeftEar).build());
        }
        if (file2 == null) {
            this.ivRightEar.setVisibility(4);
        } else {
            this.ivRightEar.setVisibility(0);
            ((BaseApplication) CommonUtils.getApplication()).getAppComponent().imageLoader().autoLoadImage(CommonUtils.getAppContext(), GlideImageConfig.builder().url(file2.getAbsolutePath()).newListener(new RequestListener<String, GlideDrawable>() { // from class: com.petkit.android.activities.virtual.d4sh.widget.VirtualD4shHomeDeviceView.13
                @Override // com.bumptech.glide.request.RequestListener
                public boolean onException(Exception exc, String str, Target<GlideDrawable> target, boolean z) {
                    return false;
                }

                @Override // com.bumptech.glide.request.RequestListener
                public boolean onResourceReady(GlideDrawable glideDrawable, String str, Target<GlideDrawable> target, boolean z, boolean z2) {
                    return false;
                }
            }).imageView(this.ivRightEar).build());
        }
        if (file3 == null) {
            this.ivBackground.setVisibility(4);
        } else {
            this.ivBackground.setVisibility(0);
            ((BaseApplication) CommonUtils.getApplication()).getAppComponent().imageLoader().autoLoadImage(CommonUtils.getAppContext(), GlideImageConfig.builder().url(file3.getAbsolutePath()).newListener(new RequestListener<String, GlideDrawable>() { // from class: com.petkit.android.activities.virtual.d4sh.widget.VirtualD4shHomeDeviceView.14
                @Override // com.bumptech.glide.request.RequestListener
                public boolean onException(Exception exc, String str, Target<GlideDrawable> target, boolean z) {
                    return false;
                }

                @Override // com.bumptech.glide.request.RequestListener
                public boolean onResourceReady(GlideDrawable glideDrawable, String str, Target<GlideDrawable> target, boolean z, boolean z2) {
                    return false;
                }
            }).imageView(this.ivBackground).build());
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
                        this.player.clearCoverView();
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

    public void setPlayerStatus(D4shRecord d4shRecord, int i) {
        if (i == 4 || i == 1 || this.d4shPlayerStatus != i) {
            this.d4shPlayerStatus = i;
            D4shPlayerPortraitView d4shPlayerPortraitView = this.playerPortraitView;
            Boolean bool = Boolean.FALSE;
            d4shPlayerPortraitView.setVolumeImageVisible(bool);
            this.playerPortraitView.setPrivacyModePlayImageVisible(bool);
            this.playerLandscapeView.setLiveButtonVisibleStatus(bool);
            this.player.setViewBlack(8);
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
                    try {
                        if (Double.parseDouble(d4shRecord.getFirmware()) < 200.0d || d4shRecord.getSettings().getCamera() == 0) {
                            textView2.setText(R.string.Camera_is_off);
                            textView3.setVisibility(0);
                            textView.setText(R.string.Turn_on_camera);
                            this.player.setViewBlack(0);
                        } else {
                            textView2.setText(R.string.Camera_off);
                            textView3.setVisibility(0);
                            textView.setText(R.string.Turn_on_camera_out_worktime);
                            this.player.setViewBlack(0);
                        }
                    } catch (Exception e) {
                        PetkitLog.d(e.getMessage());
                        LogcatStorageHelper.addLog(e.getMessage());
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
                    new ImageCacheSimple(CommonUtils.getAppContext()).getImageCachePath(eventPreview, new ImageCacheSimple.IGetImageCacheListener() { // from class: com.petkit.android.activities.virtual.d4sh.widget.VirtualD4shHomeDeviceView$$ExternalSyntheticLambda6
                        @Override // com.jess.arms.widget.imageloader.ImageCacheSimple.IGetImageCacheListener
                        public final void onCachePath(String str) {
                            this.f$0.lambda$setPlayerStatus$5(imageView, str);
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
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setPlayerStatus$5(ImageView imageView, String str) {
        if (str != null) {
            imageView.setImageBitmap(BlurUtils.blur(CommonUtils.getAppContext(), BitmapFactory.decodeFile(ImageUtils.decryptImageFile(new File(str), D4shUtils.getEventPreviewAesKey(this.mContext, this.d4shRecord.getDeviceId(), this.d4shRecord.getTypeCode())).getAbsolutePath()), 10, 0.125f));
        }
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

    public void setLiveUrl(String str, boolean z) {
        this.player.startVideo(str);
        setVolumeIcon(z);
    }

    public void refreshPlanView(DifferentFeedPlan differentFeedPlan) {
        Calendar.getInstance().get(7);
        if (differentFeedPlan != null) {
            for (int i = 0; i < 7 && differentFeedPlan.getFeedDailyList().get(i).getSuspended() != 0; i++) {
            }
            this.bleMenuView.setIsFeederPlanIsOpen(true);
            this.bleMenuView.setDeviceType(25);
            this.d4shDeviceWarnMessageViewGroup.setWarnMessageViewGroupStatus(Boolean.FALSE, null, null, null, null, null, null);
        }
        setD4Record(this.d4shRecord);
    }

    public void refreshFreeTrailTip() {
        if (AppVersionStateUtils.getCurrentAppVersionCheckState()) {
            return;
        }
        this.playerLandscapeView.setTipState(this.d4shRecord);
        showPurchaseEntry();
    }

    @SuppressLint({"StringFormatInvalid"})
    public void setD4Record(D4shRecord d4shRecord) {
        this.d4shRecord = d4shRecord;
        setToolbarData(d4shRecord);
        this.playerLandscapeView.setDeviceInfo(d4shRecord.getTypeCode() == 0 ? 25 : 26, d4shRecord.getDeviceId());
        D4shDeviceDataCardViewGroup d4shDeviceDataCardViewGroup = this.d4shDeviceDataCardViewGroup;
        if (d4shDeviceDataCardViewGroup != null) {
            d4shDeviceDataCardViewGroup.setDeviceTypeCodeAndRefreshView(d4shRecord.getTypeCode());
        }
        if (this.d4shHomeRecordAdapter == null) {
            if (d4shRecord.getTypeCode() == 0) {
                this.d4shHomeRecordAdapter = new VirtualD4shHomeRecordAdapter(this.mContext, d4shRecord.getDeviceId(), d4shRecord.getTypeCode(), 0, this);
            } else {
                this.d4shHomeRecordAdapter = new VirtualD4hHomeRecordAdapter(this.mContext, d4shRecord.getDeviceId(), d4shRecord.getTypeCode(), 0, this);
            }
            this.rvD4RecordView.setAdapter(this.d4shHomeRecordAdapter);
        }
        if (!AppVersionStateUtils.getCurrentAppVersionCheckState()) {
            this.playerLandscapeView.setTipState(d4shRecord);
            showPurchaseEntry();
        }
        this.playerLandscapeView.setServiceUnavailable(d4shRecord.getCloudProduct() == null || !(d4shRecord.getMoreService() == 1 || d4shRecord.getCloudProduct().getSubscribe() == 1 || System.currentTimeMillis() / 1000 <= Long.parseLong(d4shRecord.getCloudProduct().getWorkIndate())));
        checkDeepEnergySavingStatus();
        if (d4shRecord.getState().getPim() == 0) {
            setDesiccantDataCardViewData(d4shRecord.getState().getDesiccantLeftDays());
            this.bleMenuView.changeAllMask(true);
            this.d4shDeviceWarnMessageViewGroup.showMainWarnMessageView(this.mContext.getResources().getString(R.string.Device_off_line_tip));
        } else {
            if (d4shRecord.getState().getPim() == 2) {
                this.bleMenuView.setIsD4sBatteryMode(true);
                this.bleMenuView.setDeviceType(d4shRecord.getTypeCode() == 0 ? 25 : 26);
            } else {
                this.bleMenuView.setIsD4sBatteryMode(false);
                this.bleMenuView.setDeviceType(d4shRecord.getTypeCode() == 0 ? 25 : 26);
            }
            setDesiccantDataCardViewData(d4shRecord.getState().getDesiccantLeftDays());
            this.d4shDeviceWarnMessageViewGroup.hideMainWarnMessageView();
            this.bleMenuView.changeAllMask(false);
            if (!TextUtils.isEmpty(d4shRecord.getState().getErrorCode()) && d4shRecord.getState().getErrorLevel() == 1) {
                this.d4shDeviceWarnMessageViewGroup.showMainWarnMessageView(d4shRecord.getState().getErrorMsg());
                this.bleMenuView.changeAllMask(true);
            } else if (d4shRecord.getState().getOta() == 1) {
                if (d4shRecord.getDeviceShared() != null) {
                    this.bleMenuView.changeAllMask(true);
                }
                this.d4shDeviceWarnMessageViewGroup.setWarnMessageViewGroupStatus(null, Boolean.TRUE, this.activity.getResources().getString(R.string.Device_ota_prompt), null, Boolean.valueOf(d4shRecord.getDeviceShared() == null), null, null);
                this.d4shDeviceWarnMessageViewGroup.setWarnMessageShowRightArrowIcon(Boolean.valueOf(d4shRecord.getDeviceShared() == null));
            } else if (!TextUtils.isEmpty(d4shRecord.getState().getErrorCode())) {
                this.d4shDeviceWarnMessageViewGroup.showMainWarnMessageView(d4shRecord.getState().getErrorMsg());
            } else if (d4shRecord.getTypeCode() == 0 && ((d4shRecord.getState().getFood1() == 0 || d4shRecord.getState().getFood2() == 0 || d4shRecord.getState().getFood1() == 1 || d4shRecord.getState().getFood2() == 1) && d4shRecord.getSettings().getNoRemind() == 0)) {
                if (d4shRecord.getState().getFood1() == 1 && d4shRecord.getState().getFood2() == 1) {
                    this.d4shDeviceWarnMessageViewGroup.showMainWarnMessageView(this.activity.getResources().getString(R.string.D4sh_buckets_insufficient_surplus_grain));
                } else if (d4shRecord.getState().getFood1() == 0 && d4shRecord.getState().getFood2() == 0) {
                    this.d4shDeviceWarnMessageViewGroup.showMainWarnMessageView(this.activity.getResources().getString(R.string.D4sh_buckets_lack_of_food_prompt));
                } else if (d4shRecord.getState().getFood1() == 1 && d4shRecord.getState().getFood2() == 0) {
                    SpannableString spannableString = new SpannableString(String.format(this.activity.getResources().getString(R.string.D4sh_buckets_lack_of_food_and_empty_prompt_one), getResources().getString(R.string.Defalut_bucket1_name) + " ", getResources().getString(R.string.Defalut_bucket2_name) + " "));
                    SpannableString spannableStringMakePartStringSpannableInTotalString = TextUtil.getInstance().makePartStringSpannableInTotalString(spannableString, String.format(this.activity.getResources().getString(R.string.D4sh_buckets_lack_of_food_and_empty_prompt_one), getResources().getString(R.string.Defalut_bucket1_name) + " ", getResources().getString(R.string.Defalut_bucket2_name) + " "), getResources().getString(R.string.Defalut_bucket1_name), getResources().getColor(R.color.d3_main_green), 14, true);
                    this.d4shDeviceWarnMessageViewGroup.showMainWarnMessageView(TextUtil.getInstance().makePartStringSpannableInTotalString(spannableStringMakePartStringSpannableInTotalString, String.format(this.activity.getResources().getString(R.string.D4sh_buckets_lack_of_food_and_empty_prompt_one), getResources().getString(R.string.Defalut_bucket1_name) + " ", getResources().getString(R.string.Defalut_bucket2_name) + " "), getResources().getString(R.string.Defalut_bucket2_name), getResources().getColor(R.color.d4s_orange_three), 14, true));
                } else if (d4shRecord.getState().getFood1() == 0 && d4shRecord.getState().getFood2() == 1) {
                    SpannableString spannableString2 = new SpannableString(String.format(this.activity.getResources().getString(R.string.D4sh_buckets_lack_of_food_and_empty_prompt_two), getResources().getString(R.string.Defalut_bucket1_name) + " ", getResources().getString(R.string.Defalut_bucket2_name) + " "));
                    SpannableString spannableStringMakePartStringSpannableInTotalString2 = TextUtil.getInstance().makePartStringSpannableInTotalString(spannableString2, String.format(this.activity.getResources().getString(R.string.D4sh_buckets_lack_of_food_and_empty_prompt_two), getResources().getString(R.string.Defalut_bucket1_name) + " ", getResources().getString(R.string.Defalut_bucket2_name) + " "), getResources().getString(R.string.Defalut_bucket1_name), getResources().getColor(R.color.d3_main_green), 14, true);
                    this.d4shDeviceWarnMessageViewGroup.showMainWarnMessageView(TextUtil.getInstance().makePartStringSpannableInTotalString(spannableStringMakePartStringSpannableInTotalString2, String.format(this.activity.getResources().getString(R.string.D4sh_buckets_lack_of_food_and_empty_prompt_two), getResources().getString(R.string.Defalut_bucket1_name) + " ", getResources().getString(R.string.Defalut_bucket2_name) + " "), getResources().getString(R.string.Defalut_bucket2_name), getResources().getColor(R.color.d4s_orange_three), 14, true));
                } else if (d4shRecord.getState().getFood1() == 0) {
                    SpannableString spannableString3 = new SpannableString(String.format(this.activity.getResources().getString(R.string.D4sh_bucket1_lack_of_food_prompt), getResources().getString(R.string.Defalut_bucket1_name) + " "));
                    this.d4shDeviceWarnMessageViewGroup.showMainWarnMessageView(TextUtil.getInstance().makePartStringSpannableInTotalString(spannableString3, String.format(this.activity.getResources().getString(R.string.D4sh_bucket1_lack_of_food_prompt), getResources().getString(R.string.Defalut_bucket1_name) + " "), getResources().getString(R.string.Defalut_bucket1_name), getResources().getColor(R.color.d3_main_green), 14, true));
                } else if (d4shRecord.getState().getFood2() == 0) {
                    SpannableString spannableString4 = new SpannableString(String.format(this.activity.getResources().getString(R.string.D4sh_bucket1_lack_of_food_prompt), getResources().getString(R.string.Defalut_bucket2_name) + " "));
                    this.d4shDeviceWarnMessageViewGroup.showMainWarnMessageView(TextUtil.getInstance().makePartStringSpannableInTotalString(spannableString4, String.format(this.activity.getResources().getString(R.string.D4sh_bucket1_lack_of_food_prompt), getResources().getString(R.string.Defalut_bucket2_name) + " "), getResources().getString(R.string.Defalut_bucket2_name), getResources().getColor(R.color.d4s_orange_three), 14, true));
                } else if (d4shRecord.getState().getFood1() == 1) {
                    SpannableString spannableString5 = new SpannableString(String.format(this.activity.getResources().getString(R.string.D4sh_bucket1_insufficient_surplus_grain), getResources().getString(R.string.Defalut_bucket1_name) + " "));
                    this.d4shDeviceWarnMessageViewGroup.showMainWarnMessageView(TextUtil.getInstance().makePartStringSpannableInTotalString(spannableString5, String.format(this.activity.getResources().getString(R.string.D4s_bucket1_lack_of_food_prompt), getResources().getString(R.string.Defalut_bucket1_name) + " "), getResources().getString(R.string.Defalut_bucket1_name) + " ", getResources().getColor(R.color.d3_main_green), 14, true));
                } else if (d4shRecord.getState().getFood2() == 1) {
                    SpannableString spannableString6 = new SpannableString(String.format(this.activity.getResources().getString(R.string.D4sh_bucket1_insufficient_surplus_grain), getResources().getString(R.string.Defalut_bucket2_name) + " "));
                    this.d4shDeviceWarnMessageViewGroup.showMainWarnMessageView(TextUtil.getInstance().makePartStringSpannableInTotalString(spannableString6, String.format(this.activity.getResources().getString(R.string.D4s_bucket1_lack_of_food_prompt), getResources().getString(R.string.Defalut_bucket2_name) + " "), getResources().getString(R.string.Defalut_bucket2_name) + " ", getResources().getColor(R.color.d4s_orange_three), 14, true));
                }
            } else if (d4shRecord.getTypeCode() == 1 && ((d4shRecord.getState().getFood() == 0 || d4shRecord.getState().getFood() == 1) && d4shRecord.getSettings().getNoRemind() == 0)) {
                if (d4shRecord.getState().getFood() == 1) {
                    this.d4shDeviceWarnMessageViewGroup.showMainWarnMessageView(this.activity.getResources().getString(R.string.D4sh_bucket1_insufficient_surplus_grain, ""));
                } else if (d4shRecord.getState().getFood() == 0) {
                    this.d4shDeviceWarnMessageViewGroup.showMainWarnMessageView(this.activity.getResources().getString(R.string.D4h_buckets_lack_of_food_and_empty_prompt));
                }
            } else if (d4shRecord.getState().getBatteryStatus() == 2) {
                this.d4shDeviceWarnMessageViewGroup.setWarnMessageViewGroupStatus(null, Boolean.TRUE, this.activity.getResources().getString(R.string.D3_low_power_prompt), null, null, Boolean.FALSE, null, null);
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

    /* JADX WARN: Removed duplicated region for block: B:86:0x0387  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void showPurchaseEntry() {
        /*
            Method dump skipped, instruction units count: 945
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.petkit.android.activities.virtual.d4sh.widget.VirtualD4shHomeDeviceView.showPurchaseEntry():void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showPurchaseEntry$6(View view) {
        if (this.d4shRecord.getRealDeviceShared() != null) {
            PetkitToast.showTopToast(CommonUtils.getAppContext(), this.mContext.getResources().getString(R.string.D4sh_no_cloud_free_tips), R.drawable.top_toast_warn_icon, 1);
        } else {
            redirectToPurchasePage(this.d4shRecord.getTypeCode() == 0 ? 25 : 26, this.d4shRecord.getDeviceId());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showPurchaseEntry$7(View view) {
        if (this.d4shRecord.getRealDeviceShared() != null) {
            Context context = this.mContext;
            PetkitToast.showTopToast(context, context.getResources().getString(R.string.D4sh_no_cloud_buy_tips), R.drawable.top_toast_warn_icon, 1);
        } else {
            redirectToPurchasePage(this.d4shRecord.getTypeCode() == 0 ? 25 : 26, this.d4shRecord.getDeviceId());
        }
    }

    private void showPetEatingWindow() {
        PetkitPetEatingWindow petkitPetEatingWindow = this.eatingWindow;
        if (petkitPetEatingWindow == null || !petkitPetEatingWindow.isShowing()) {
            PetkitPetEatingWindow petkitPetEatingWindow2 = new PetkitPetEatingWindow(this.mContext);
            this.eatingWindow = petkitPetEatingWindow2;
            petkitPetEatingWindow2.showAsDropDown(this.player, 0, ArmsUtils.dip2px(this.mContext, 6.0f));
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.widget.VirtualBleDeviceHomeMenuView.OnD4shDeviceHomeMenuTouchListener
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
        new HashMap().put("type", "Devicehome");
        D4shHomeDeviceViewListener d4shHomeDeviceViewListener = this.d4shHomeDeviceViewListener;
        if (d4shHomeDeviceViewListener != null) {
            d4shHomeDeviceViewListener.onD4shDeviceHomeMenuMicroPhoneViewTouchDown();
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.widget.VirtualBleDeviceHomeMenuView.OnD4shDeviceHomeMenuTouchListener
    public void onD4shDeviceHomeMenuMicroPhoneViewTouchUp() {
        D4shHomeDeviceViewListener d4shHomeDeviceViewListener = this.d4shHomeDeviceViewListener;
        if (d4shHomeDeviceViewListener != null) {
            d4shHomeDeviceViewListener.onD4shDeviceHomeMenuMicroPhoneViewTouchUp();
        }
    }

    @Override // com.petkit.android.media.video.player.listener.BasePetkitPlayerListener
    public void onCompleted() {
        this.player.reStartPlay();
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
            new HashMap().put("status", "live_success");
            PetkitLog.d("TENCENT_P2P", "live_success");
            LogcatStorageHelper.addLog("TENCENT_P2P, live_success");
        }
    }

    @Override // com.petkit.android.media.video.player.listener.BasePetkitPlayerListener
    public void continueVideo() {
        this.playerLandscapeView.setPlayerSwitchImageViewResource(Integer.valueOf(R.drawable.petkit_player_landscape_record_view_pause_icon));
    }

    @Override // com.petkit.android.media.video.player.listener.BasePetkitPlayerListener
    @SuppressLint({"MissingPermission"})
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

    @Override // com.petkit.android.activities.petkitBleDevice.widget.VirtualBleDeviceHomeMenuView.BatteryModeClickListener
    public void onBatteryModeClick() {
        openCameraUnavailableBatteryModeWindow();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:133:0x023e  */
    @Override // android.view.View.OnClickListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onClick(android.view.View r19) {
        /*
            Method dump skipped, instruction units count: 1338
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.petkit.android.activities.virtual.d4sh.widget.VirtualD4shHomeDeviceView.onClick(android.view.View):void");
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
            if (isLandscape() || TextUtils.isEmpty(this.deviceUrl)) {
                this.rlViewProduct.setVisibility(8);
            } else {
                this.rlViewProduct.setVisibility(0);
            }
            closeDeepEnergySavingTimer();
            this.deepEnergySavingStatus = 0;
            this.rlTitleStatus.setVisibility(8);
            this.rlDeepEnergySaving.setVisibility(8);
            this.bleMenuView.setIsD4sBatteryMode(false);
            this.bleMenuView.setDeviceType(this.d4shRecord.getTypeCode() == 0 ? 25 : 26);
            return;
        }
        if (this.d4shRecord.getState().getPim() == 2) {
            this.rlTitleStatus.setVisibility(0);
            this.tvTitleStatus.setText(getResources().getString(R.string.D2_battery_mode));
            this.bleMenuView.setIsD4sBatteryMode(true);
            this.bleMenuView.setDeviceType(this.d4shRecord.getTypeCode() == 0 ? 25 : 26);
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
                if (!isLandscape()) {
                    this.bleMenuView.setVisibility(0);
                    return;
                } else {
                    this.rlViewProduct.setVisibility(8);
                    return;
                }
            }
            this.rlDeepEnergySaving.setVisibility(0);
            this.bleMenuView.setVisibility(8);
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
        } else {
            this.rlViewProduct.setVisibility(8);
        }
        this.rlDeepEnergySaving.setVisibility(8);
        this.rlTitleStatus.setVisibility(8);
        this.bleMenuView.setIsD4sBatteryMode(true);
        this.bleMenuView.setDeviceType(this.d4shRecord.getTypeCode() == 0 ? 25 : 26);
    }

    public void openDeepEnergySavingTimer(long j) {
        if (j < 0) {
            j = 0;
        }
        this.leftTime = j;
        if (this.disposable == null) {
            this.disposable = Observable.interval(1000L, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.petkit.android.activities.virtual.d4sh.widget.VirtualD4shHomeDeviceView$$ExternalSyntheticLambda5
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f$0.lambda$openDeepEnergySavingTimer$8((Long) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$openDeepEnergySavingTimer$8(Long l) throws Exception {
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

    public void refreshDateData(D4shDailyFeeds d4shDailyFeeds) {
        this.d4DailyFeeds = d4shDailyFeeds;
        if (d4shDailyFeeds != null) {
            if (this.d4shRecord.getTypeCode() == 0) {
                this.d4shHomeRecordAdapter = new VirtualD4shHomeRecordAdapter(this.mContext, this.d4shRecord.getDeviceId(), this.d4shRecord.getTypeCode(), this.d4shHomeRecordAdapter.getFilterRecordType(), this);
            } else {
                this.d4shHomeRecordAdapter = new VirtualD4hHomeRecordAdapter(this.mContext, this.d4shRecord.getDeviceId(), this.d4shRecord.getTypeCode(), this.d4shHomeRecordAdapter.getFilterRecordType(), this);
            }
            int i = 0;
            this.d4shHomeRecordAdapter.setD4DateFeedData(new D4shDateFeedData(d4shDailyFeeds.getFeed().get(0).getItems(), (d4shDailyFeeds.getEat() == null || d4shDailyFeeds.getEat().size() == 0) ? new ArrayList<>() : d4shDailyFeeds.getEat().get(0).getItems(), (d4shDailyFeeds.getPet() == null || d4shDailyFeeds.getPet().size() == 0) ? new ArrayList<>() : d4shDailyFeeds.getPet().get(0).getItems(), (d4shDailyFeeds.getMove() == null || d4shDailyFeeds.getMove().size() == 0) ? new ArrayList<>() : d4shDailyFeeds.getMove().get(0).getItems(), d4shDailyFeeds.getFeed().get(0).getDay()));
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
            this.rvD4RecordView.post(new Runnable() { // from class: com.petkit.android.activities.virtual.d4sh.widget.VirtualD4shHomeDeviceView.19
                @Override // java.lang.Runnable
                public void run() {
                }
            });
            if (isLandscape()) {
                this.playerLandscapeView.refreshEatVideoDeviceRecord(d4shDailyFeeds, this.d4shRecord);
            }
        }
    }

    public void setPromoteView(RelatedProductsInfor relatedProductsInfor) {
        this.relatedProductsInfor = relatedProductsInfor;
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
            this.deleteRecordWindow = new NormalCenterTipWindow(this.mContext, new NormalCenterTipWindow.OnClickOk() { // from class: com.petkit.android.activities.virtual.d4sh.widget.VirtualD4shHomeDeviceView.20
                @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
                public void onCancelClick() {
                }

                @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
                public void onOkClick() {
                    EventBus.getDefault().post(VirtualD4shHomeDeviceView.this.d4sRemoveData);
                }
            }, null, new SpannableString(str), layoutParams);
        } else {
            this.deleteRecordWindow = new NormalCenterTipWindow(this.mContext, new NormalCenterTipWindow.OnClickOk() { // from class: com.petkit.android.activities.virtual.d4sh.widget.VirtualD4shHomeDeviceView.21
                @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
                public void onCancelClick() {
                }

                @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
                public void onOkClick() {
                    EventBus.getDefault().post(VirtualD4shHomeDeviceView.this.d4sRemoveData);
                }
            }, (String) null, str);
        }
        if (this.d4sRemoveData.getDeleteType() == 1) {
            this.deleteRecordWindow.setTvTipGray(true, this.mContext.getResources().getString(R.string.D4s_delete_work_record_little_tip));
        } else if (this.d4sRemoveData.getDeleteType() == 3) {
            this.deleteRecordWindow.setTvTipGray(true, this.mContext.getResources().getString(R.string.D4sh_device_record_remove_video_event_prompt));
        } else {
            this.deleteRecordWindow.setTvTipGray(true, this.mContext.getResources().getString(R.string.D4s_delete_output_record_little_tip));
        }
        this.deleteRecordWindow.setSelectText(this.mContext.getResources().getString(R.string.Cancel), this.mContext.getResources().getString(R.string.Confirm));
        this.deleteRecordWindow.show(((D4shHomeActivity) this.mContext).getWindow().getDecorView());
    }

    public void showFoodWarnDialog() {
        if (isLandscape()) {
            return;
        }
        NormalCenterTipWindow normalCenterTipWindow = this.d4sFoodWarnWindow;
        if (normalCenterTipWindow == null || !normalCenterTipWindow.isShowing()) {
            String str = "";
            if (this.d4shRecord.getTypeCode() == 0) {
                int i = R.drawable.d4sh_buckets_lack_of_food_icon;
                SpannableString spannableString = new SpannableString(this.activity.getResources().getString(R.string.D4sh_buckets_insufficient_surplus_grain));
                if (this.d4shRecord.getState().getFood1() == 0 && this.d4shRecord.getState().getFood2() == 0) {
                    i = R.drawable.d4sh_buckets_lack_of_food_icon;
                    spannableString = new SpannableString(this.activity.getResources().getString(R.string.D4sh_buckets_lack_of_food_prompt));
                } else if (this.d4shRecord.getState().getFood1() == 0) {
                    i = R.drawable.d4sh_bucket1_lack_of_food_icon;
                    String str2 = String.format(this.activity.getResources().getString(R.string.D4sh_bucket1_lack_of_food_prompt), getResources().getString(R.string.Defalut_bucket1_name) + " ");
                    spannableString = TextUtil.getInstance().makePartStringSpannableInTotalString(new SpannableString(str2), str2, this.mContext.getResources().getString(R.string.Defalut_bucket1_name), this.mContext.getResources().getColor(R.color.d3_main_green), 16, true);
                } else if (this.d4shRecord.getState().getFood2() == 0) {
                    i = R.drawable.d4sh_bucket2_lack_of_food_icon;
                    String str3 = String.format(this.activity.getResources().getString(R.string.D4sh_bucket1_lack_of_food_prompt), getResources().getString(R.string.Defalut_bucket2_name) + " ");
                    spannableString = TextUtil.getInstance().makePartStringSpannableInTotalString(new SpannableString(str3), str3, this.mContext.getResources().getString(R.string.Defalut_bucket2_name), this.mContext.getResources().getColor(R.color.d4s_orange_three), 16, true);
                }
                String string = this.activity.getResources().getString(R.string.Surplus_grain_early_warning_tips);
                if (this.d4shRecord.getSettings().getFoodWarn() == 1 && (this.d4shRecord.getState() == null || this.d4shRecord.getState().getPim() != 2)) {
                    str = string;
                }
                NormalCenterTipWindow normalCenterTipWindow2 = new NormalCenterTipWindow(this.activity, new NormalCenterTipWindow.OnClickOk() { // from class: com.petkit.android.activities.virtual.d4sh.widget.VirtualD4shHomeDeviceView.22
                    @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
                    public void onOkClick() {
                        VirtualD4shHomeDeviceView.this.d4sFoodWarnWindow.dismiss();
                    }

                    @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
                    public void onCancelClick() {
                        VirtualD4shHomeDeviceView.this.menuOnClickListener.onBottomMenuClick(MenuType.NO_MORE_REMIND);
                    }
                }, getResources().getString(R.string.Surplus_grain_early_warning), spannableString);
                this.d4sFoodWarnWindow = normalCenterTipWindow2;
                normalCenterTipWindow2.setTvTipGray(true, str);
                this.d4sFoodWarnWindow.setSelectText(getResources().getString(R.string.I_have_added_meal), getResources().getString(R.string.I_know));
                this.d4sFoodWarnWindow.setImage(i);
                this.d4sFoodWarnWindow.showAtLocation(this.activity.getWindow().getDecorView(), 17, 0, 0);
                return;
            }
            if (this.d4shRecord.getTypeCode() == 1) {
                int i2 = R.drawable.d4h_buckets_lack_of_food_icon;
                SpannableString spannableString2 = new SpannableString(this.activity.getResources().getString(R.string.No_food_prompt));
                String string2 = this.activity.getResources().getString(R.string.Surplus_grain_early_warning_tips);
                if (this.d4shRecord.getSettings().getFoodWarn() == 1 && (this.d4shRecord.getState() == null || this.d4shRecord.getState().getPim() != 2)) {
                    str = string2;
                }
                NormalCenterTipWindow normalCenterTipWindow3 = new NormalCenterTipWindow(this.activity, new NormalCenterTipWindow.OnClickOk() { // from class: com.petkit.android.activities.virtual.d4sh.widget.VirtualD4shHomeDeviceView.23
                    @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
                    public void onOkClick() {
                        VirtualD4shHomeDeviceView.this.d4sFoodWarnWindow.dismiss();
                    }

                    @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
                    public void onCancelClick() {
                        VirtualD4shHomeDeviceView.this.menuOnClickListener.onBottomMenuClick(MenuType.NO_MORE_REMIND);
                    }
                }, getResources().getString(R.string.Surplus_grain_early_warning), spannableString2);
                this.d4sFoodWarnWindow = normalCenterTipWindow3;
                normalCenterTipWindow3.setTvTipGray(true, str);
                this.d4sFoodWarnWindow.setSelectText(getResources().getString(R.string.I_have_added_meal), getResources().getString(R.string.I_know));
                this.d4sFoodWarnWindow.setImage(i2);
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

    private void checkDeviceWarnState() {
        int i = !CommonUtils.isSameTimeZoneAsLocal(this.d4shRecord.getLocale(), this.d4shRecord.getTimezone()) ? 1 : 0;
        if (this.d4shRecord.getState().getWifi().getRsq() < -70) {
            i++;
        }
        if (i == 0) {
            this.d4shDeviceWarnMessageViewGroup.hideOtherWarnMessageView();
            return;
        }
        if (i == 1) {
            if (!CommonUtils.isSameTimeZoneAsLocal(this.d4shRecord.getLocale(), this.d4shRecord.getTimezone())) {
                this.d4shDeviceWarnMessageViewGroup.showOtherWarnMessageView(this.activity.getResources().getString(R.string.Time_zone_is_different));
                return;
            } else {
                if (this.d4shRecord.getState().getWifi().getRsq() < -70) {
                    this.d4shDeviceWarnMessageViewGroup.showOtherWarnMessageView(this.activity.getResources().getString(R.string.Wifi_signal_weak));
                    return;
                }
                return;
            }
        }
        this.d4shDeviceWarnMessageViewGroup.showOtherWarnMessageView(String.format(getResources().getString(R.string.Have_mistake_to_handle), String.valueOf(i)));
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
        guideBuilder.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() { // from class: com.petkit.android.activities.virtual.d4sh.widget.VirtualD4shHomeDeviceView.24
            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onShown() {
            }

            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onDismiss() {
                DataHelper.setBooleanSF(VirtualD4shHomeDeviceView.this.mContext, Consts.D4S_EAT_RECORD_IS_FIRST, Boolean.TRUE);
                VirtualD4shHomeDeviceView.this.d4sViewLayout.fling(1);
                VirtualD4shHomeDeviceView.this.d4sViewLayout.setOnTouchListener(new View.OnTouchListener() { // from class: com.petkit.android.activities.virtual.d4sh.widget.VirtualD4shHomeDeviceView.24.1
                    @Override // android.view.View.OnTouchListener
                    public boolean onTouch(View view2, MotionEvent motionEvent) {
                        return false;
                    }
                });
            }
        });
        guideBuilder.addComponent(new GuideToiletBtnAndLineComponent(new GuideParam(getResources().getString(R.string.Pet_feed_records_can_be_slip_deleted), 2, 32, 0, 10, this.mContext.getResources().getString(R.string.I_know), 0, R.layout.layout_toilet_guide), new ConfirmListener() { // from class: com.petkit.android.activities.virtual.d4sh.widget.VirtualD4shHomeDeviceView.25
            @Override // com.petkit.android.activities.petkitBleDevice.w5.guide.ConfirmListener
            public void onConfirmListener() {
                if (VirtualD4shHomeDeviceView.this.guide1 != null) {
                    VirtualD4shHomeDeviceView.this.guide1.dismiss();
                }
            }
        }));
        this.guide1 = guideBuilder.createGuide();
        this.contentView.post(new Runnable() { // from class: com.petkit.android.activities.virtual.d4sh.widget.VirtualD4shHomeDeviceView.26
            @Override // java.lang.Runnable
            public void run() {
                VirtualD4shHomeDeviceView.this.guide1.show((Activity) VirtualD4shHomeDeviceView.this.getContext());
            }
        });
    }

    @Override // com.petkit.android.activities.petkitBleDevice.widget.VirtualBleDeviceHomeMenuView.OnMenuClickListener
    public void onMenuClick(BleMenuItem bleMenuItem, int i) {
        MenuOnClickListener menuOnClickListener = this.menuOnClickListener;
        if (menuOnClickListener != null) {
            if (i == 0) {
                if (this.d4shRecord.getState().getFeeding() == 1 || this.d4shRecord.getState().getFeeding() == 2 || this.d4shRecord.getState().getFeeding() == 3) {
                    this.menuOnClickListener.onBottomMenuClick(MenuType.STOP_FEED);
                    return;
                } else {
                    this.menuOnClickListener.onBottomMenuClick(MenuType.FEED_NOW);
                    return;
                }
            }
            if (i == 1) {
                D4shHomeDeviceViewListener d4shHomeDeviceViewListener = this.d4shHomeDeviceViewListener;
                if (d4shHomeDeviceViewListener != null) {
                    d4shHomeDeviceViewListener.onD4shDeviceHomeMenuMicroPhoneViewClick();
                    return;
                }
                return;
            }
            if (i == 2) {
                menuOnClickListener.onBottomMenuClick(MenuType.FEED_PLAN);
            } else if (i == 3) {
                menuOnClickListener.onBottomMenuClick(MenuType.FOOD_CONTROL);
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
        activity.startActivity(BleDeviceWifiSettingActivity.newIntent(activity, this.d4shRecord.getDeviceId(), this.d4shRecord.getTypeCode() == 0 ? 25 : 26));
    }

    @Override // com.petkit.android.activities.petkitBleDevice.widget.BleDeviceHomeOfflineWarnWindow.OfflineClickListener
    public void onClickReset() {
        Activity activity = this.activity;
        activity.startActivity(BleDeviceBindNetWorkActivity.newIntent((Context) activity, this.d4shRecord.getDeviceId(), this.d4shRecord.getTypeCode() == 0 ? 25 : 26, this.d4shRecord.getBtMac(), false));
    }

    @Override // com.petkit.android.activities.petkitBleDevice.d4sh.adapter.RecordOnClickListener
    public void onDelete(int i, int i2, int i3) {
        showMoreWindow();
    }

    @Override // com.petkit.android.activities.petkitBleDevice.d4sh.adapter.RecordOnClickListener
    public void onDeleteFeedRecord(int i, int i2) {
        showMoreWindow();
    }

    @Override // com.petkit.android.activities.petkitBleDevice.d4sh.adapter.RecordOnClickListener
    public void onD4shHomeRecordAdapterDeleteVideoEvent(String str, int i) {
        showMoreWindow();
    }

    @Override // com.petkit.android.activities.petkitBleDevice.d4sh.adapter.RecordOnClickListener
    public void onItemClick(D4shVideoRecord d4shVideoRecord) {
        D4shHomeDeviceViewListener d4shHomeDeviceViewListener = this.d4shHomeDeviceViewListener;
        if (d4shHomeDeviceViewListener != null) {
            d4shHomeDeviceViewListener.onD4shDeviceVideoRecordClick(d4shVideoRecord, new ArrayList());
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

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isLandscape() {
        return getResources().getConfiguration().orientation == 2;
    }

    private void setD4hFeedDataCardViewData() {
        String amountFormat = D4Utils.getAmountFormat(this.d4shRecord.getState().getFeedState() != null ? this.d4shRecord.getState().getFeedState().getRealAmountTotal() : 0, this.d4shRecord.getSettings().getFactor());
        StringBuilder sb = new StringBuilder();
        "zh_CN".equalsIgnoreCase(UserInforUtils.getLanguageLocaleString(CommonUtils.getAppContext()));
        sb.append(amountFormat);
        this.d4shDeviceDataCardViewGroup.setDeviceDataCardViewGroupContent(new SpannableString(sb.toString()), null, null, null, null, 4);
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
        Context context;
        int i2;
        if (i < 0) {
            i = 0;
        }
        if (i > 1) {
            context = this.mContext;
            i2 = R.string.Unit_days;
        } else {
            context = this.mContext;
            i2 = R.string.Unit_day;
        }
        String str = String.valueOf(i) + " /30 " + context.getString(i2);
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
        this.d4shDeviceDataCardViewGroup.setDeviceDataCardViewGroupContent(null, null, null, null, spannableString, 4);
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
        if ((this.rvHighlights.getTop() + this.rvHighlights.getHeight()) - this.d4sViewLayout.getScrollY() <= 0 || view == null) {
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
            sb.append(Constants.D4H_D4SH_VLOG_MAKE_TIP);
            sb.append(this.d4shRecord.getTypeCode() == 0 ? 25 : 26);
            sb.append("_");
            sb.append(this.d4shRecord.getDeviceId());
            sb.append("_");
            sb.append(D4shUtils.getTodayYYYYMMddFormatStr());
            DataHelper.setBooleanSF(activity, sb.toString(), Boolean.TRUE);
            Observable.timer(3L, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).compose(((LifecycleProvider) this.activity).bindUntilEvent(ActivityEvent.DESTROY)).subscribe(new Consumer<Long>() { // from class: com.petkit.android.activities.virtual.d4sh.widget.VirtualD4shHomeDeviceView.27
                @Override // io.reactivex.functions.Consumer
                public void accept(Long l) throws Exception {
                    if (VirtualD4shHomeDeviceView.this.d4shVlogTipWindow == null || !VirtualD4shHomeDeviceView.this.d4shVlogTipWindow.isShowing()) {
                        return;
                    }
                    VirtualD4shHomeDeviceView.this.d4shVlogTipWindow.dismiss();
                }
            });
            this.d4shVlogTipWindow.showAsDropDown(view, 0, ArmsUtils.dip2px(this.mContext, 6.0f));
        }
    }

    public void setD4shDeviceRecordDateTextViewText(String str, String str2) {
        this.d4shDeviceRecordDateStr = str;
        this.d4shDeviceRecordDateText = str2;
        this.d4shDeviceRecordTagViewGroup.setD4shDeviceRecordDateTextViewText(str2);
        this.tvMealtimeDate.setText(str2);
        this.d4shDeviceRecordTagViewGroupTopFixed.setD4shDeviceRecordDateTextViewText(str2);
    }

    public void setToolbarData(D4shRecord d4shRecord) {
        Activity activity;
        int i;
        TextView textView = this.toolbarTitle;
        if (d4shRecord.getTypeCode() == 0) {
            activity = this.activity;
            i = R.string.D4SH_name_default;
        } else {
            activity = this.activity;
            i = R.string.D4H_name_default;
        }
        textView.setText(activity.getString(i));
        this.imgD4Setting.setImageResource(DeviceCenterUtils.isD4shNeedOtaById(this.d4shRecord.getDeviceId()) ? R.drawable.petkit_device_home_new_style_setting_red_point_icon : R.drawable.petkit_device_home_new_style_setting_icon);
        this.imgD4Setting.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.virtual.d4sh.widget.VirtualD4shHomeDeviceView$$ExternalSyntheticLambda11
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$setToolbarData$9(view);
            }
        });
        this.toolbarBack.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.virtual.d4sh.widget.VirtualD4shHomeDeviceView.28
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((BaseActivity) VirtualD4shHomeDeviceView.this.activity).killMyself();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setToolbarData$9(View view) {
        showMoreWindow();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setD4shDeviceRecordTagViewGroupTopFixedVisible() {
        this.d4shDeviceRecordTagViewGroup.post(new Runnable() { // from class: com.petkit.android.activities.virtual.d4sh.widget.VirtualD4shHomeDeviceView$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$setD4shDeviceRecordTagViewGroupTopFixedVisible$10();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setD4shDeviceRecordTagViewGroupTopFixedVisible$10() {
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
        this.player.setConfiguration(configuration, 0);
        setPortraitScreenViewVisible(Boolean.valueOf(configuration.orientation == 1));
        if (configuration.orientation == 2) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.playerContainer.getLayoutParams();
            layoutParams.setMargins(0, 0, 0, 0);
            this.playerContainer.setLayoutParams(layoutParams);
            this.rlMask.setVisibility(8);
            this.player.cancelCorners();
            this.playerPortraitView.hideOneself();
            this.playerLandscapeView.showOneself(null, null);
            landscapeVisibleCheck();
            this.rlViewProduct.setVisibility(8);
            return;
        }
        if (this.bleMenuView.getVisibility() == 0) {
            if (TextUtils.isEmpty(this.deviceUrl)) {
                this.rlViewProduct.setVisibility(8);
            } else {
                this.rlViewProduct.setVisibility(0);
            }
        }
        this.rlMask.setVisibility(0);
        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) this.playerContainer.getLayoutParams();
        layoutParams2.setMargins(ArmsUtils.dip2px(this.activity, 16.0f), ArmsUtils.dip2px(this.activity, 56.0f), ArmsUtils.dip2px(this.activity, 16.0f), 0);
        this.playerContainer.setLayoutParams(layoutParams2);
        this.player.initCorners();
        this.playerPortraitView.showOneself(null);
        this.playerLandscapeView.hideOneself();
        visibleCheck();
    }

    private void setPortraitScreenViewVisible(final Boolean bool) {
        if (bool != null) {
            this.toolbar.setVisibility(bool.booleanValue() ? 0 : 8);
            this.bleMenuView.setVisibility(bool.booleanValue() ? 0 : 8);
            this.d4sViewLayout.post(new Runnable() { // from class: com.petkit.android.activities.virtual.d4sh.widget.VirtualD4shHomeDeviceView$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$setPortraitScreenViewVisible$11(bool);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setPortraitScreenViewVisible$11(Boolean bool) {
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
        if (TextUtils.isEmpty(this.player.getRecordFilePath())) {
            return;
        }
        File file = new File(this.player.getRecordFilePath());
        if (file.exists()) {
            file.delete();
        }
    }

    public void setDatePickerViewDateChangeListener(BasePetkitDeviceDatePickerView.OnCalendarChangeListener onCalendarChangeListener) {
        this.onCalendarChangeListener = onCalendarChangeListener;
    }

    public void showDatePickerWindow(String str) {
        if (isLandscape()) {
            this.playerLandscapeView.showDatePickerWindow(this.d4shRecord.getCreatedAt(), str, this.d4shRecord.getDeviceId(), this.onCalendarChangeListener);
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
            this.d4shDeviceWarnMessageViewGroup.setAlpha(bool.booleanValue() ? 0.1f : 1.0f);
            this.d4shDeviceDataCardViewGroup.setAlpha(bool.booleanValue() ? 0.1f : 1.0f);
            this.d4shDeviceRecordTagViewGroup.setAlpha(bool.booleanValue() ? 0.1f : 1.0f);
            this.d4shDeviceRecordTagViewGroupTopFixed.setAlpha(bool.booleanValue() ? 0.1f : 1.0f);
            this.rvD4RecordView.setAlpha(bool.booleanValue() ? 0.1f : 1.0f);
        }
    }

    private void resetTagViewGroupSelectionType(int i) {
        if (this.d4shDeviceRecordTagViewGroupTopFixed.getVisibility() == 8) {
            this.d4shDeviceRecordTagViewGroupTopFixed.setD4shDeviceRecordTypeNumberViewHighLightStatus(i);
        } else {
            this.d4shDeviceRecordTagViewGroup.setD4shDeviceRecordTypeNumberViewHighLightStatus(i);
        }
    }

    public void startRecord() {
        this.player.startRecord(PlayerUtils.getVideoRecordFileName());
        VirtualD4shHomePlayerLandscapeView virtualD4shHomePlayerLandscapeView = this.playerLandscapeView;
        Boolean bool = Boolean.TRUE;
        virtualD4shHomePlayerLandscapeView.setRecordBtnImageResource(bool, R.drawable.recording_icon);
        this.playerLandscapeView.setRecordTimeText(bool, null);
    }

    public void startRecord(String str) {
        this.player.startRecord(str);
        VirtualD4shHomePlayerLandscapeView virtualD4shHomePlayerLandscapeView = this.playerLandscapeView;
        Boolean bool = Boolean.TRUE;
        virtualD4shHomePlayerLandscapeView.setRecordBtnImageResource(bool, R.drawable.recording_icon);
        this.playerLandscapeView.setRecordTimeText(bool, null);
    }

    public void stopRecord() {
        MediaScannerConnection.scanFile(this.mContext, new String[]{FileUtil.saveFileToExternalStorage(this.activity, new File(this.player.stopRecord()), VideoDownloadManager.getLocalVideoPath())}, null, new MediaScannerConnection.OnScanCompletedListener() { // from class: com.petkit.android.activities.virtual.d4sh.widget.VirtualD4shHomeDeviceView.29
            @Override // android.media.MediaScannerConnection.OnScanCompletedListener
            public void onScanCompleted(String str, Uri uri) {
            }
        });
        VirtualD4shHomePlayerLandscapeView virtualD4shHomePlayerLandscapeView = this.playerLandscapeView;
        Boolean bool = Boolean.FALSE;
        virtualD4shHomePlayerLandscapeView.setRecordBtnImageResource(bool, R.drawable.d4sh_player_landscape_record_icon);
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

    public void continuePlay() {
        this.player.continuePlay();
    }

    public void startVideo(String str, int i) {
        this.videoExpireOrLoss = false;
        this.player.startVideo(str, true, this.eatVideoTimesSpeed, i);
    }

    public void startMultipleVideo(List<PetkitVideoSegment> list) {
        this.videoExpireOrLoss = false;
        this.player.startMultipleVideo(list, this.eatVideoTimesSpeed);
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
        if (isLandscape() && this.playerLandscapeView.getVideoType() == 2 && this.player.getPosition() >= 0) {
            this.player.switchMuteVolume();
            setVolumeIcon(this.player.isMute());
        } else {
            D4shHomeDeviceViewListener d4shHomeDeviceViewListener = this.d4shHomeDeviceViewListener;
            if (d4shHomeDeviceViewListener != null) {
                d4shHomeDeviceViewListener.onD4shDeviceVolumeBtnClick();
            }
        }
    }

    public void switchMuteVolume() {
        this.player.switchMuteVolume();
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
            d4shHomeDeviceViewListener.onPlayerScreenShotBtnClick(Boolean.valueOf(this.player.isPlayerAvailableState()), this.player.getCurrentBitmap());
        }
    }

    @Override // com.petkit.android.media.video.player.BasePetkitPlayerLandscapeViewClickListener
    public void onLandscapeRecordBtnClick() {
        if (this.d4shHomeDeviceViewListener != null) {
            if (!this.player.isRecording()) {
                if (this.player.isPlayerPlayingState()) {
                    this.d4shHomeDeviceViewListener.onVideoRecordBtnClick(Boolean.TRUE);
                    return;
                }
                return;
            }
            this.d4shHomeDeviceViewListener.onVideoRecordBtnClick(Boolean.FALSE);
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
        new HashMap().put("type", "Livefullscreen");
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
        this.mainHandler.removeCallbacks(this.loadFullVideoRunnable);
        if (d4shDayItem.getBean() instanceof D4shDailyFeeds.D4shDailyEat.ItemsBean) {
            new HashMap().put("status", "eat");
        } else if (d4shDayItem.getBean() instanceof D4shDailyFeeds.D4shDailyFeed.ItemsBean) {
            new HashMap().put("status", "feed");
        } else if (d4shDayItem.getBean() instanceof D4shDailyFeeds.MoveBean.ItemsBeanX) {
            new HashMap().put("status", AppMeasurementSdk.ConditionalUserProperty.ACTIVE);
        } else if (d4shDayItem.getBean() instanceof D4shDailyFeeds.PetBean.ItemsBean) {
            new HashMap().put("status", "pet");
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
        this.d4shHomeDeviceViewListener.stopLive();
        this.playerLandscapeView.resetRecordViewStatus();
        boolean z = this.d4shRecord.getCloudProduct() == null || !(this.d4shRecord.getMoreService() == 1 || this.d4shRecord.getCloudProduct().getSubscribe() == 1 || System.currentTimeMillis() / 1000 <= Long.parseLong(this.d4shRecord.getCloudProduct().getWorkIndate()));
        if (this.d4shDeviceRecordDateStr != null) {
            getResources().getString(R.string.Today).equals(D4shUtils.getVideoRecordDateStr(getContext(), this.d4shDeviceRecordDateStr));
        }
        if (str == null) {
            this.videoExpireOrLoss = true;
            hideLoadingView();
            long j2 = ((long) i) * 1000;
            if (System.currentTimeMillis() - j2 < 120000 && num != null && num.intValue() == 2) {
                this.player.addCoverWithBackgroundView(Integer.valueOf(R.layout.petkit_player_video_loading_cover_view), Integer.valueOf(R.layout.petkit_player_gray_black_background_cover_view));
                ((PetkitLogoLoadingView) this.player.findViewById(R.id.pllv_cover)).startLoadingAnimation();
                this.mainHandler.postDelayed(this.loadFullVideoRunnable, 120000 - (System.currentTimeMillis() - j2));
            } else {
                this.player.addCoverWithBackgroundView(Integer.valueOf(R.layout.petkit_player_video_loss_cover_view), Integer.valueOf(R.layout.petkit_player_gray_black_background_cover_view));
                if (d4shDayItem.getBean() instanceof D4shDailyFeeds.D4shDailyEat.ItemsBean) {
                    if (((D4shDailyFeeds.D4shDailyEat.ItemsBean) d4shDayItem.getBean()).getEatVideo() == 1) {
                        ((TextView) this.player.findViewById(R.id.tv_no_video_tip)).setText(R.string.No_video_prompt_one);
                    } else {
                        ((TextView) this.player.findViewById(R.id.tv_no_video_tip)).setText(R.string.No_video_prompt_two);
                    }
                } else if (d4shDayItem.getBean() instanceof D4shDailyFeeds.D4shDailyFeed.ItemsBean) {
                    if (((D4shDailyFeeds.D4shDailyFeed.ItemsBean) d4shDayItem.getBean()).getEatVideo() == 1) {
                        ((TextView) this.player.findViewById(R.id.tv_no_video_tip)).setText(R.string.No_video_prompt_one);
                    } else {
                        ((TextView) this.player.findViewById(R.id.tv_no_video_tip)).setText(R.string.No_video_prompt_two);
                    }
                } else if (d4shDayItem.getBean() instanceof D4shDailyFeeds.MoveBean.ItemsBeanX) {
                    if (((D4shDailyFeeds.MoveBean.ItemsBeanX) d4shDayItem.getBean()).getEatVideo() == 1) {
                        ((TextView) this.player.findViewById(R.id.tv_no_video_tip)).setText(R.string.No_video_prompt_one);
                    } else {
                        ((TextView) this.player.findViewById(R.id.tv_no_video_tip)).setText(R.string.No_video_prompt_two);
                    }
                } else if (d4shDayItem.getBean() instanceof D4shDailyFeeds.PetBean.ItemsBean) {
                    if (((D4shDailyFeeds.PetBean.ItemsBean) d4shDayItem.getBean()).getEatVideo() == 1) {
                        ((TextView) this.player.findViewById(R.id.tv_no_video_tip)).setText(R.string.No_video_prompt_one);
                    } else {
                        ((TextView) this.player.findViewById(R.id.tv_no_video_tip)).setText(R.string.No_video_prompt_two);
                    }
                }
            }
            this.player.findViewById(R.id.camera_unavailable_prompt_view).setBackground(this.mContext.getResources().getDrawable(R.color.dark_black));
            return;
        }
        if (l != null && System.currentTimeMillis() / 1000 > l.longValue()) {
            this.videoExpireOrLoss = true;
            hideLoadingView();
            this.player.addCoverWithBackgroundView(Integer.valueOf(PlayerUtils.getNoServiceVideoExpiredCoverView()), Integer.valueOf(R.layout.petkit_player_gray_black_background_cover_view));
            this.player.findViewById(R.id.camera_unavailable_prompt_view).setBackground(this.mContext.getResources().getDrawable(R.color.dark_black));
            return;
        }
        if (z) {
            this.videoExpireOrLoss = true;
            hideLoadingView();
            if (l != null && System.currentTimeMillis() / 1000 > l.longValue()) {
                this.player.addCoverWithBackgroundView(Integer.valueOf(PlayerUtils.getNoServiceVideoExpiredCoverView()), Integer.valueOf(R.layout.petkit_player_gray_black_background_cover_view));
            } else {
                this.player.addCoverWithBackgroundView(Integer.valueOf(PlayerUtils.getNoServicePurchasePackageCoverView()), Integer.valueOf(R.layout.petkit_player_no_service_preview_background_cover_view));
                D4shUtils.setImageViewUrl((ImageView) this.player.findViewById(R.id.preview_image_view), str3, str4);
            }
            this.player.findViewById(R.id.camera_unavailable_prompt_view).setBackground(this.mContext.getResources().getDrawable(R.color.dark_black));
            if (this.player.findViewById(R.id.open_immediately) != null) {
                this.player.findViewById(R.id.open_immediately).setOnClickListener(this);
                this.player.findViewById(R.id.open_immediately).setVisibility(this.d4shRecord.getCloudProduct() != null ? 8 : 0);
                return;
            }
            return;
        }
        this.player.clearCoverView();
        D4shHomeDeviceViewListener d4shHomeDeviceViewListener2 = this.d4shHomeDeviceViewListener;
        if (d4shHomeDeviceViewListener2 != null) {
            d4shHomeDeviceViewListener2.onPlayerStartLoadVideo();
        }
        if (num != null && num.intValue() == 2) {
            this.player.showLoadingView();
            this.player.startVideo(str2, true);
        } else {
            if (num == null || num.intValue() != 1) {
                return;
            }
            this.player.showLoadingView();
            this.player.startVideo(str, true);
        }
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
        showMoreWindow();
    }

    @Override // com.petkit.android.media.video.player.BasePetkitPlayerLandscapeViewClickListener
    public void onDeleteEatVideoBtnClick(int i, String str, long j, long j2, int i2) {
        showMoreWindow();
    }

    @Override // com.petkit.android.media.video.player.BasePetkitPlayerLandscapeViewClickListener
    public void onDownloadFullVideo(Long l, Long l2, Long l3, Long l4) {
        if (this.d4shRecord.getServiceStatus() != 1 || System.currentTimeMillis() / 1000 > l4.longValue()) {
            PetkitToast.showToast(getResources().getString(R.string.Expired_prompt));
            return;
        }
        D4shHomeDeviceViewListener d4shHomeDeviceViewListener = this.d4shHomeDeviceViewListener;
        if (d4shHomeDeviceViewListener != null) {
            d4shHomeDeviceViewListener.onPlayerDownloadFullVideo(l, l2, l3);
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
        showMoreWindow();
    }

    @Override // com.petkit.android.media.video.player.BasePetkitPlayerLandscapeViewClickListener
    public void onVirtualMoreClick() {
        showMoreWindow();
    }

    private void setD4shDeviceRecordTagTypeNumber(D4shDailyFeeds d4shDailyFeeds) {
        List<Integer> d4shRecordTypeNumberList = D4shUtils.getD4shRecordTypeNumberList(d4shDailyFeeds);
        this.d4shDeviceRecordTagViewGroup.setD4shDeviceRecordTypeNumber(d4shRecordTypeNumberList.get(0), d4shRecordTypeNumberList.get(1), d4shRecordTypeNumberList.get(2), d4shRecordTypeNumberList.get(3), d4shRecordTypeNumberList.get(4));
        this.d4shDeviceRecordTagViewGroupTopFixed.setD4shDeviceRecordTypeNumber(d4shRecordTypeNumberList.get(0), d4shRecordTypeNumberList.get(1), d4shRecordTypeNumberList.get(2), d4shRecordTypeNumberList.get(3), d4shRecordTypeNumberList.get(4));
    }

    private void redirectToPurchasePage(int i, long j) {
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
        VirtualD4shVlogRecordAdapter virtualD4shVlogRecordAdapter = new VirtualD4shVlogRecordAdapter((Activity) this.mContext, this.d4shRecord);
        this.d4shVlogRecordAdapter = virtualD4shVlogRecordAdapter;
        virtualD4shVlogRecordAdapter.setTypeCode(this.d4shRecord.getTypeCode());
        this.d4shVlogRecordAdapter.append((List) list);
        this.rvHighlights.setAdapter(this.d4shVlogRecordAdapter);
        this.tvMore.setVisibility((list != null && list.size() > 0) ? 0 : 8);
        this.d4shVlogRecordAdapter.setOnDailyHighlightItemClickListener(new BasePetkitHighlightAdapter.OnDailyHighlightItemClickListener() { // from class: com.petkit.android.activities.virtual.d4sh.widget.VirtualD4shHomeDeviceView.31
            @Override // com.petkit.android.activities.petkitBleDevice.adapter.BasePetkitHighlightAdapter.OnDailyHighlightItemClickListener
            public void onEmptyClick() {
            }

            @Override // com.petkit.android.activities.petkitBleDevice.adapter.BasePetkitHighlightAdapter.OnDailyHighlightItemClickListener
            public void onPlayBtnClick(HighlightRecord highlightRecord) {
                D4shVlogRecordAdapterListener d4shVlogRecordAdapterListener = VirtualD4shHomeDeviceView.this.d4shVlogRecordAdapterListener;
                if (d4shVlogRecordAdapterListener != null) {
                    d4shVlogRecordAdapterListener.onPlayBtnClick(highlightRecord);
                }
            }

            @Override // com.petkit.android.activities.petkitBleDevice.adapter.BasePetkitHighlightAdapter.OnDailyHighlightItemClickListener
            public void onMarkVlogClick(HighlightRecord highlightRecord) {
                VirtualD4shHomeDeviceView virtualD4shHomeDeviceView = VirtualD4shHomeDeviceView.this;
                if (virtualD4shHomeDeviceView.d4shVlogRecordAdapterListener != null) {
                    virtualD4shHomeDeviceView.d4shVlogRecordAdapter.setRefreshLoadingStatus(true);
                    VirtualD4shHomeDeviceView.this.d4shVlogRecordAdapterListener.onMarkVlogClick(highlightRecord);
                }
            }
        });
        Activity activity = this.activity;
        StringBuilder sb = new StringBuilder();
        sb.append(Constants.D4H_D4SH_VLOG_MAKE_TIP);
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
        HighlightRecord highlightRecord = list.get(0);
        if (VlogUtils.getVlogMarkRecord(CommonUtils.getCurrentUserId(), highlightRecord.getId()) == null) {
            if ((System.currentTimeMillis() <= ((long) highlightRecord.getExpired()) * 1000 || highlightRecord.getExpired() == 0) && highlightRecord.getId() != 0 && TextUtils.isEmpty(highlightRecord.getVideoUrl()) && calendar.get(11) >= 8) {
                this.mainHandler.post(new Runnable() { // from class: com.petkit.android.activities.virtual.d4sh.widget.VirtualD4shHomeDeviceView.32
                    @Override // java.lang.Runnable
                    public void run() {
                        VirtualD4shHomeDeviceView virtualD4shHomeDeviceView = VirtualD4shHomeDeviceView.this;
                        virtualD4shHomeDeviceView.showVlogTipWindow(virtualD4shHomeDeviceView.rvHighlights.getChildAt(0));
                    }
                });
            }
        }
    }

    public void removeHighlightRecord() {
        VirtualD4shVlogRecordAdapter virtualD4shVlogRecordAdapter = this.d4shVlogRecordAdapter;
        if (virtualD4shVlogRecordAdapter != null) {
            virtualD4shVlogRecordAdapter.removeAll();
        }
    }

    public void refreshVlogList() {
        VirtualD4shVlogRecordAdapter virtualD4shVlogRecordAdapter = this.d4shVlogRecordAdapter;
        if (virtualD4shVlogRecordAdapter != null) {
            virtualD4shVlogRecordAdapter.setRefreshLoadingStatus(false);
            this.d4shVlogRecordAdapter.notifyDataSetChanged();
        }
    }

    public H3TextureView getLiveView() {
        return this.player.getLiveView();
    }

    public void setD4shVlogRecordAdapterListener(D4shVlogRecordAdapterListener d4shVlogRecordAdapterListener) {
        this.d4shVlogRecordAdapterListener = d4shVlogRecordAdapterListener;
    }

    public void setDeviceUrl(String str) {
        this.deviceUrl = str;
        initProductView();
    }

    public void showMoreWindow() {
        new ExperienceMoreWindow(this.activity, new ExperienceMoreWindow.OnClickListener() { // from class: com.petkit.android.activities.virtual.d4sh.widget.VirtualD4shHomeDeviceView.33
            @Override // com.petkit.android.activities.virtual.widget.ExperienceMoreWindow.OnClickListener
            public void onClickFirstChoice() {
                MallUtils.goToWebOrProductDetail(VirtualD4shHomeDeviceView.this.mContext, VirtualD4shHomeDeviceView.this.deviceUrl, 2);
            }

            @Override // com.petkit.android.activities.virtual.widget.ExperienceMoreWindow.OnClickListener
            public void onClickSecondChoice() {
                if (Long.parseLong(UserInforUtils.getCurrentUserId(VirtualD4shHomeDeviceView.this.activity)) != FamilyUtils.getInstance().getCurrentFamilyInfo(VirtualD4shHomeDeviceView.this.activity).getOwner()) {
                    PetkitToast.showTopToast(VirtualD4shHomeDeviceView.this.activity, VirtualD4shHomeDeviceView.this.getResources().getString(R.string.Bind_device_family_check_prompt), R.drawable.top_toast_warn_icon, 1);
                } else {
                    VirtualD4shHomeDeviceView.this.activity.startActivity(BleDeviceBindNetWorkActivity.newIntent(VirtualD4shHomeDeviceView.this.mContext, -1L, VirtualD4shHomeDeviceView.this.d4shRecord.getTypeCode() == 0 ? 25 : 26, "", true));
                }
            }
        }, this.mContext.getResources().getString(R.string.Prompt), this.mContext.getResources().getString(R.string.Experience_prompt), this.mContext.getResources().getString(R.string.Buy_immediately), this.mContext.getResources().getString(R.string.Device_add), !TextUtils.isEmpty(this.deviceUrl)).show(this.activity.getWindow().getDecorView());
    }
}

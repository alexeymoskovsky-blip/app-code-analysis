package com.petkit.android.activities.petkitBleDevice.t6;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.view.GravityCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.airbnb.lottie.LottieAnimationView;
import com.alibaba.fastjson.JSON;
import com.binioter.guideview.Guide;
import com.binioter.guideview.GuideBuilder;
import com.google.android.material.appbar.AppBarLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.Consts;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.utils.DeviceUtils;
import com.jess.arms.utils.ImageUtils;
import com.jess.arms.widget.InterceptViewPager;
import com.jess.arms.widget.PetkitToast;
import com.jess.arms.widget.autolayout.AutoToolbar;
import com.jess.arms.widget.imageloader.ImageCacheSimple;
import com.jess.arms.widget.imageloader.glide.GlideCircleTransform;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.petkit.android.activities.appwidget.mode.large.WidgetDataInfo;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.card.mode.PurchaseEligibilityInfo;
import com.petkit.android.activities.cloudservice.CloudServiceFreeTrialActivity;
import com.petkit.android.activities.cloudservice.CloudServiceWebViewActivity;
import com.petkit.android.activities.cloudservice.ServiceManagementActivity;
import com.petkit.android.activities.cloudservice.mode.CloudServiceUpdateMsg;
import com.petkit.android.activities.cloudservice.mode.ExitCloudServiceWebViewMsg;
import com.petkit.android.activities.cloudservice.mode.RefreshSdEvent;
import com.petkit.android.activities.cloudservice.mode.ServiceUpdateEvent;
import com.petkit.android.activities.cloudservice.utils.CloudServiceUtils;
import com.petkit.android.activities.common.utils.DeviceCenterUtils;
import com.petkit.android.activities.community.WebviewActivity;
import com.petkit.android.activities.cozy.widget.EasyPopupWindow;
import com.petkit.android.activities.device.AiActivity;
import com.petkit.android.activities.device.DeviceSetInfoActivity;
import com.petkit.android.activities.device.mode.AiInfo;
import com.petkit.android.activities.device.utils.AiDataUtil;
import com.petkit.android.activities.device.widget.ThreeChoicesWindow;
import com.petkit.android.activities.doctor.mode.MedicalConversionItem;
import com.petkit.android.activities.doctor.mode.PetErrorInfo;
import com.petkit.android.activities.doctor.utils.DoctorUtils;
import com.petkit.android.activities.family.mode.FamilyInfor;
import com.petkit.android.activities.home.mode.CloudServiceDelayTimeResult;
import com.petkit.android.activities.home.utils.GuideD4shBottomRightTipAndBtnAndLineComponent;
import com.petkit.android.activities.home.utils.GuideTipAndBtnAndLineComponent;
import com.petkit.android.activities.home.widget.MyHeader;
import com.petkit.android.activities.mall.mode.PromoteData;
import com.petkit.android.activities.mall.widget.PromoteView;
import com.petkit.android.activities.petkitBleDevice.BleDeviceWifiSettingActivity;
import com.petkit.android.activities.petkitBleDevice.PetColorSettingActivity;
import com.petkit.android.activities.petkitBleDevice.PetWeightActivity;
import com.petkit.android.activities.petkitBleDevice.adapter.BasePetkitHighlightAdapter;
import com.petkit.android.activities.petkitBleDevice.d4sh.mode.BannerStateCache;
import com.petkit.android.activities.petkitBleDevice.d4sh.mode.D4shBannerData;
import com.petkit.android.activities.petkitBleDevice.d4sh.mode.D4shVlogDeleteMsg;
import com.petkit.android.activities.petkitBleDevice.d4sh.model.VlogStateChanged;
import com.petkit.android.activities.petkitBleDevice.d4sh.utils.D4shUtils;
import com.petkit.android.activities.petkitBleDevice.d4sh.widget.NewWifiWeakWindow;
import com.petkit.android.activities.petkitBleDevice.download.MediaDisplayActivity;
import com.petkit.android.activities.petkitBleDevice.download.mode.AgoraDownloadMsg;
import com.petkit.android.activities.petkitBleDevice.download.mode.AgoraStartDownloadMsg;
import com.petkit.android.activities.petkitBleDevice.download.widget.DownloadingWindow;
import com.petkit.android.activities.petkitBleDevice.hg.widget.NewRoundImageview;
import com.petkit.android.activities.petkitBleDevice.mode.HighLightRecordRsp;
import com.petkit.android.activities.petkitBleDevice.mode.HighlightRecord;
import com.petkit.android.activities.petkitBleDevice.mode.OtaResult;
import com.petkit.android.activities.petkitBleDevice.mode.UpdatePetColorMsg;
import com.petkit.android.activities.petkitBleDevice.r2.widget.NewIKnowWindow;
import com.petkit.android.activities.petkitBleDevice.t4.T4EmptyCatLitterPromptActivity;
import com.petkit.android.activities.petkitBleDevice.t4.T4MaintenanceModeIntroduceActivity;
import com.petkit.android.activities.petkitBleDevice.t4.mode.PetOutTip;
import com.petkit.android.activities.petkitBleDevice.t4.mode.RelatedProductsInfor;
import com.petkit.android.activities.petkitBleDevice.t4.widget.RoundImageview;
import com.petkit.android.activities.petkitBleDevice.t6.T6EventListFragment;
import com.petkit.android.activities.petkitBleDevice.t6.adapter.MyViewPagerAdapter;
import com.petkit.android.activities.petkitBleDevice.t6.adapter.T5LiveViewPagerAdapter;
import com.petkit.android.activities.petkitBleDevice.t6.adapter.T6HomeBannerPageAdapter;
import com.petkit.android.activities.petkitBleDevice.t6.adapter.T6VlogRecordAdapter;
import com.petkit.android.activities.petkitBleDevice.t6.component.DaggerT5HomeComponent;
import com.petkit.android.activities.petkitBleDevice.t6.contract.T5HomeContract;
import com.petkit.android.activities.petkitBleDevice.t6.mode.DeleteEvent;
import com.petkit.android.activities.petkitBleDevice.t6.mode.DeviceError;
import com.petkit.android.activities.petkitBleDevice.t6.mode.T5CopyWritingGifVideoResult;
import com.petkit.android.activities.petkitBleDevice.t6.mode.T6ConsumeEvent;
import com.petkit.android.activities.petkitBleDevice.t6.mode.T6ContentInfo;
import com.petkit.android.activities.petkitBleDevice.t6.mode.T6EventInfo;
import com.petkit.android.activities.petkitBleDevice.t6.mode.T6EventStaticInfo;
import com.petkit.android.activities.petkitBleDevice.t6.mode.T6Record;
import com.petkit.android.activities.petkitBleDevice.t6.mode.T6StaticInfo;
import com.petkit.android.activities.petkitBleDevice.t6.mode.T6StatisticResInfo;
import com.petkit.android.activities.petkitBleDevice.t6.mode.TimingResult;
import com.petkit.android.activities.petkitBleDevice.t6.mode.UpdatePetCleanEvent;
import com.petkit.android.activities.petkitBleDevice.t6.mode.UpdatePetEvent;
import com.petkit.android.activities.petkitBleDevice.t6.presenter.T5HomePresenter;
import com.petkit.android.activities.petkitBleDevice.t6.utils.T6AnimUtil;
import com.petkit.android.activities.petkitBleDevice.t6.utils.T6Utils;
import com.petkit.android.activities.petkitBleDevice.t6.widget.CircleLayout;
import com.petkit.android.activities.petkitBleDevice.t6.widget.MyChartViewPager;
import com.petkit.android.activities.petkitBleDevice.t6.widget.MySmartRefreshView;
import com.petkit.android.activities.petkitBleDevice.t6.widget.PetkitVideoPlayerView;
import com.petkit.android.activities.petkitBleDevice.t6.widget.SandDumpSuccessDialog;
import com.petkit.android.activities.petkitBleDevice.t6.widget.T5BottomView;
import com.petkit.android.activities.petkitBleDevice.t6.widget.T5LiveVideoView;
import com.petkit.android.activities.petkitBleDevice.t6.widget.T6KittenProtectionDialog;
import com.petkit.android.activities.petkitBleDevice.t6.widget.VideoPlayerViewListener;
import com.petkit.android.activities.petkitBleDevice.utils.BleDeviceUtils;
import com.petkit.android.activities.petkitBleDevice.utils.ColorUtils;
import com.petkit.android.activities.petkitBleDevice.utils.FamilyUtils;
import com.petkit.android.activities.petkitBleDevice.utils.TextUtil;
import com.petkit.android.activities.petkitBleDevice.utils.TimeUtils;
import com.petkit.android.activities.petkitBleDevice.vlog.VlogMakeService;
import com.petkit.android.activities.petkitBleDevice.vlog.mode.VlogM3U8Mode;
import com.petkit.android.activities.petkitBleDevice.w5.guide.ConfirmListener;
import com.petkit.android.activities.petkitBleDevice.w5.guide.GuideParam;
import com.petkit.android.activities.petkitBleDevice.widget.BleDeviceHomeOfflineWarnWindow;
import com.petkit.android.activities.petkitBleDevice.widget.CloudServiceFreeTrialDialog;
import com.petkit.android.activities.petkitBleDevice.widget.DeviceErrorWarnWindow;
import com.petkit.android.activities.petkitBleDevice.widget.KittenProtectionWindow;
import com.petkit.android.activities.petkitBleDevice.widget.OtaPromptWindow;
import com.petkit.android.activities.petkitBleDevice.widget.datepicker.BasePetkitDeviceDatePickerView;
import com.petkit.android.activities.petkitBleDevice.widget.datepicker.CountBean;
import com.petkit.android.activities.petkitBleDevice.widget.datepicker.DatePickerWindow;
import com.petkit.android.activities.petkitBleDevice.widget.frame.TipWindow;
import com.petkit.android.activities.registe.utils.AppUtils;
import com.petkit.android.activities.statistics.ToiletStatisticsActivity;
import com.petkit.android.activities.statistics.WeightRecordActivity;
import com.petkit.android.activities.statistics.WeightStatisticsActivity;
import com.petkit.android.activities.universalWindow.BaseBottomWindow;
import com.petkit.android.activities.universalWindow.NormalCenterTipWindow;
import com.petkit.android.activities.universalWindow.NormalListChoiceCenterWindow;
import com.petkit.android.activities.universalWindow.PetFilterWindow;
import com.petkit.android.activities.universalWindow.SpannableStringColorsPicPromptWindow;
import com.petkit.android.activities.universalWindow.SpannableStringColorsWindow;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.media.video.rtmUtil.RtcService;
import com.petkit.android.media.video.rtmUtil.RtmManager;
import com.petkit.android.model.Pet;
import com.petkit.android.utils.AppVersionStateUtils;
import com.petkit.android.utils.CommonUtil;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.DateUtil;
import com.petkit.android.utils.FileUtils;
import com.petkit.android.utils.LogcatStorageHelper;
import com.petkit.android.utils.PetkitLog;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.android.utils.ZipUtil;
import com.petkit.android.widget.CirclePageIndicator;
import com.petkit.android.widget.SmallCircleDotView;
import com.petkit.oversea.R;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.shopify.sample.util.MallUtils;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

/* JADX INFO: loaded from: classes5.dex */
@SuppressLint({"NonConstantResourceId"})
public class T5HomeActivity extends BaseActivity<T5HomePresenter> implements View.OnClickListener, T5HomeContract.View, BleDeviceHomeOfflineWarnWindow.OfflineClickListener, T5LiveVideoView.T6LiveVideoViewStateListener, VideoPlayerViewListener {
    private static final long MAINTENANCE_MODE_CYCLE = 15000;
    AiInfo aiNewInfo;
    private boolean aiNotification;
    AppBarLayout appBar;
    private int appBarOffset;
    private T6HomeBannerPageAdapter bannerPageAdapter;
    private NormalCenterTipWindow bindServiceWindow;
    private BleDeviceHomeOfflineWarnWindow bleDeviceHomeOfflineWarnWindow;
    TextView btnContinue;
    TextView btnIgnoreOne;
    TextView btnKnowMoreOne;
    Button btnMaintenanceModeContinueToRun;
    Button btnMaintenanceModePauseClean;
    Button btnMaintenanceModeTerminateClean;
    TextView btnPause;
    TextView btnStop;
    private long calendarTime;
    private RelativeLayout chartView;
    CircleLayout clDevice;
    CircleLayout clEyeAnimBackground;
    CircleLayout clLoading;
    int clickActionType;
    private boolean clickIntoFullscreen;
    CloudServiceFreeTrialDialog cloudServiceFreeTrialDialog;
    private int current;
    D4shBannerData d4shBannerData;
    private DatePickerWindow datePickerWindow;
    TextView deviceErrorOneText;
    TextView deviceErrorTwoText;
    private long deviceId;
    private String deviceName;
    TextView deviceStateText;
    private int deviceType;
    TextView deviceWorkStateText;
    private Disposable disposable;
    private DownloadingWindow downloadingWindow;
    private SandDumpSuccessDialog dumpSuccessDialog;
    private String eventId;
    private FamilyInfor familyInfor;
    Guide firstGuide;
    private T6EventListFragment fragmentAll;
    private T6EventListFragment fragmentPet;
    private T6EventListFragment fragmentToilet;
    private MyHandler handler;
    Guide homeModeGUide;
    ImageButton ibSetting;
    CirclePageIndicator indicator;
    private boolean isForeground;
    private boolean isHidePromote;
    boolean isNeedShowPetError;
    private boolean isNotFirstLoad;
    private boolean isShowHealthRemindWindow;
    private boolean isShowPhErrorDialog;
    private boolean isShowToiletUnusedDialog;
    MyHeader ivAnim;
    ImageView ivArrow;
    RoundImageview ivBg;
    ImageView ivCameraAnim;
    ImageView ivChartPlay;
    ImageView ivChartShow;
    ImageView ivCircleProgress;
    ImageView ivDeviceClose;
    ImageView ivDeviceErrorOneIcon;
    ImageView ivDeviceErrorOneRightArrow;
    ImageView ivDeviceErrorTwoIcon;
    ImageView ivDeviceErrorTwoRightArrow;
    ImageView ivDeviceOpen;
    ImageView ivDeviceStateIcon;
    ImageView ivEventAnim;
    ImageView ivEyeAnim;
    ImageView ivFox;
    ImageView ivLastEvent;
    ImageView ivLeftArrow;
    ImageView ivMaintain;
    ImageView ivMaintenanceLoading;
    ImageView ivMuteIcon;
    ImageView ivNextEvent;
    ImageView ivPagerClose;
    NewRoundImageview ivPopImage;
    SmallCircleDotView ivSmallAll;
    SmallCircleDotView ivSmallAll2;
    ImageView ivTodayAvgArrow;
    ImageView ivTodayEvent;
    ImageView ivTodayEvent2;
    ImageView ivTodayTimesArrow;
    ImageView ivTrialRemainingTimeClose;
    ImageView ivWarnRight;
    private KittenProtectionWindow kittenProtectionWindow;
    private int lastMaintenanceType;
    Guide liveGuide;
    CirclePageIndicator liveIndicator;
    LinearLayout llDay;
    LinearLayout llHighlights;
    LinearLayout llKnowMoreOne;
    LinearLayout llKnowMoreTwo;
    LinearLayout llMaintenanceMode;
    LinearLayout llMaintenanceModeStatus;
    LinearLayout llPets;
    LinearLayout llTime;
    LinearLayout llTrialRemainingTime;
    LottieAnimationView ltLoading;
    private Animator maintenanceAnimator;
    private TipWindow maintenanceModeWindow;
    private long maintenanceRunTimes;
    private int monthOffset;
    private int newClearEventNum;
    private int newPetEventNum;
    private int newToiletErrorEventNum;
    private int newToiletEventNum;
    private Disposable otaDisposable;
    private OtaPromptWindow otaPromptWindow;
    private Disposable paoPaoTimer;
    NormalCenterTipWindow petErrorNoRemindWindow;
    private PetFilterWindow petFilterWindow;
    private SpannableStringColorsWindow petOutWindow;
    private NormalListChoiceCenterWindow phErrorWindow;
    private int popCount;
    PromoteView promoteView;
    private SpannableStringColorsPicPromptWindow promptWindow;
    PurchaseEligibilityInfo.PurchaseEligibility purchaseEligibility;
    private boolean refreshSd;
    private RelatedProductsInfor relatedProductsInfor;
    private ActivityResultLauncher<Intent> resultLauncher;
    RelativeLayout rlChart;
    RelativeLayout rlDeviceErrorOne;
    RelativeLayout rlDeviceErrorTwo;
    RelativeLayout rlDeviceState;
    RelativeLayout rlHomeBanner;
    HorizontalScrollView rlList;
    RelativeLayout rlLivePanel;
    RelativeLayout rlParentLive;
    RelativeLayout rlPop;
    RelativeLayout rlPopContent;
    private int rlPopHeight;
    RelativeLayout rlPopImage;
    RelativeLayout rlPopParent;
    private int rlPopWidth;
    RelativeLayout rlPurchaseEligibility;
    RelativeLayout rlRoot;
    HorizontalScrollView rlTopTab;
    LinearLayout rlWorkPanel;
    RelativeLayout rlWorkState;
    private ObjectAnimator rotateAnimator;
    Runnable runnable;
    RecyclerView rvHighlights;
    View scPoint;
    View scPoint2;
    SmallCircleDotView scdView;
    SmallCircleDotView sdPet;
    Guide secondGuide;
    private boolean showCare;
    private boolean showPromoteView;
    private List<T6StaticInfo> sortEventList;
    MySmartRefreshView srl;
    private boolean startCollAnim;
    private boolean startExpandAnim;
    private List<T6StatisticResInfo> statisticList;
    T5BottomView t5BottomView;
    private T5LiveVideoView t5LiveVideoView;
    private T5LiveViewPagerAdapter t5LiveViewPagerAdapter;
    private T6Record t5Record;
    private T6AnimUtil t6AnimUtil;
    private T6KittenProtectionDialog t6KittenProtectionDialog;
    private T6VlogRecordAdapter t6VlogRecordAdapter;
    Guide thirdGuide;
    private ThreeChoicesWindow threeChoicesWindow;
    private NewIKnowWindow timezoneWindow;
    private String title;
    private int toiletErrorEventNum;
    private T6EventListFragment toiletErrorFragment;
    AutoToolbar toolbar;
    TextView toolbarTitle;
    TextView toolbarTitleStatusOne;
    TextView toolbarTitleStatusTwo;
    TextView tvAllTab;
    TextView tvAllTab2;
    TextView tvDeviceTag;
    TextView tvHighlights;
    TextView tvMaintenanceDesc;
    TextView tvMaintenanceStatus;
    TextView tvPetName;
    TextView tvPetName2;
    TextView tvPetTab;
    TextView tvPetTab2;
    TextView tvPetWeight;
    TextView tvPopContent;
    TextView tvPopPetName;
    TextView tvPopTime;
    TextView tvPurchaseEligibility;
    TextView tvSetting;
    TextView tvTodayAvgDuration;
    TextView tvTodayEvent;
    TextView tvTodayTimes;
    TextView tvTodayToiletAmount;
    TextView tvTodayToiletStr;
    TextView tvToiletTab;
    TextView tvToiletTab2;
    TextView tvToiletTabError;
    TextView tvToiletTabError2;
    TextView tvTopPaoPao;
    TextView tvTrialRemainingTime;
    TextView tvWorkState;
    TextView tvY1;
    TextView tvY2;
    TextView tvY3;
    TextView vCameraBottom;
    View vEnd;
    View vStart;
    TextView videoTag;
    RelativeLayout videoTagLayout;
    private Disposable viewPagerDisposable;
    MyChartViewPager vp;
    ViewPager vpHomeBanner;
    InterceptViewPager vpList;
    ViewPager vpLive;
    private WidgetDataInfo widgetDataInfo;
    private NewWifiWeakWindow wifiWeakWindow;
    private SpannableStringColorsPicPromptWindow window;
    private boolean workInMaintenance;
    private int currentPosition = 0;
    private List<T6StatisticResInfo> resStatisticList = new ArrayList();
    private List<Pet> petList = new ArrayList();
    private List<String> selectedPetIds = new ArrayList();
    private boolean isNeedTurnOnDeviceAni = true;
    private AnimationDrawable animationDrawable = null;
    private NormalCenterTipWindow smoothWindow = null;
    private NormalCenterTipWindow recoverWindow = null;
    private boolean firstRemoteVideoFrame = false;
    private boolean isAnimationRunning = false;
    private boolean isFirstInto = true;
    private boolean isFirst = true;
    private boolean isShowRecommendUpgradeWindow = false;
    boolean isCN = "zh_CN".equals(UserInforUtils.getLanguageLocaleString(CommonUtils.getAppContext()));
    private List<D4shBannerData.BannerData> dataList = new ArrayList();
    private boolean serviceMaybeChanged = false;
    private final int Handler_Pao_Pao = 10;
    private final int Handler_Action_Up = 13;
    private final int Handler_Action_Down = 14;
    private final int Handler_Rtm_Login = 21;
    private final int Handler_Rtm_Token_Expired = 22;
    private final int RTM_LOGIN_TIME = 5;
    Runnable enterMaintenance = new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.55
        public AnonymousClass55() {
        }

        @Override // java.lang.Runnable
        public void run() {
            T5HomeActivity.this.stopRotateAnimation();
            T5HomeActivity.this.rlWorkPanel.setVisibility(8);
            if (T5HomeActivity.this.t5Record.getModelCode() == 2) {
                T5HomeActivity.this.ivMaintain.setImageResource(R.drawable.t5_2_maintain_normal);
            } else {
                T5HomeActivity.this.ivMaintain.setImageResource(R.drawable.t5_maintain_normal);
            }
            T5HomeActivity.this.startMaintenanceLoading();
            T5HomeActivity.this.llMaintenanceModeStatus.setGravity(3);
            T5HomeActivity.this.tvMaintenanceStatus.setPadding(0, 0, 0, 0);
            T5HomeActivity.this.tvMaintenanceDesc.setVisibility(0);
            T5HomeActivity.this.maintenanceRunTimes = System.currentTimeMillis() - (((long) T5HomeActivity.this.t5Record.getMaintenanceTime()) * 1000);
            T5HomeActivity.access$8738(T5HomeActivity.this, 1000L);
            PetkitLog.d("T5-enterMaintenance,maintenanceTime:" + T5HomeActivity.this.t5Record.getMaintenanceTime() + ",maintenanceRunTimes:" + T5HomeActivity.this.maintenanceRunTimes);
            T5HomeActivity.this.handler.removeCallbacks(T5HomeActivity.this.maintenanceRunTimer);
            T5HomeActivity.this.handler.postDelayed(T5HomeActivity.this.maintenanceRunTimer, 0L);
        }
    };
    Runnable maintenanceRunTimer = new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.56
        public AnonymousClass56() {
        }

        @Override // java.lang.Runnable
        @SuppressLint({"DefaultLocale"})
        public void run() {
            PetkitLog.d("maintenanceRunTimer,maintenanceRunTimes:" + T5HomeActivity.this.maintenanceRunTimes);
            T5HomeActivity t5HomeActivity = T5HomeActivity.this;
            t5HomeActivity.tvMaintenanceStatus.setText(t5HomeActivity.getResources().getString(R.string.Maintenance_mode_time_consume, String.format("%02d:%02d", Long.valueOf(T5HomeActivity.this.maintenanceRunTimes / 60), Long.valueOf(T5HomeActivity.this.maintenanceRunTimes % 60))));
            T5HomeActivity.access$8714(T5HomeActivity.this, 1L);
            T5HomeActivity.this.handler.postDelayed(this, 1000L);
        }
    };

    private void setListEmptyText() {
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.VideoPlayerViewListener
    public void continueVideo(int i) {
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T5HomeContract.View
    public void maintenanceEnd() {
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.VideoPlayerViewListener
    public void onBuffering(int i) {
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.VideoPlayerViewListener
    public void onCompleted() {
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.VideoPlayerViewListener
    public void onError(String str) {
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.VideoPlayerViewListener
    public void onFullScreen() {
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.VideoPlayerViewListener
    public void onInitSuccess() {
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.VideoPlayerViewListener
    public void onNormalScreen() {
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.VideoPlayerViewListener
    public void onPausePlay() {
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.VideoPlayerViewListener
    public void onPlaying() {
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.VideoPlayerViewListener
    public void onPrepared() {
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.VideoPlayerViewListener
    public void onReStart() {
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.VideoPlayerViewListener
    public void onReset() {
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.VideoPlayerViewListener
    public void onSeekCompleted() {
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.VideoPlayerViewListener
    public void onStartPlay() {
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.VideoPlayerViewListener
    public void onVideoLongClick() {
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.VideoPlayerViewListener
    public void onVideoLongClickEnd() {
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.VideoPlayerViewListener
    public void playing(String str, long j) {
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.VideoPlayerViewListener
    public void preparedVideo(String str, int i, int i2) {
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T5HomeContract.View
    public void showSandSuccess() {
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.VideoPlayerViewListener
    public void speed(String str) {
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T5HomeContract.View
    public void stopLoad() {
    }

    public static /* synthetic */ long access$8714(T5HomeActivity t5HomeActivity, long j) {
        long j2 = t5HomeActivity.maintenanceRunTimes + j;
        t5HomeActivity.maintenanceRunTimes = j2;
        return j2;
    }

    public static /* synthetic */ long access$8738(T5HomeActivity t5HomeActivity, long j) {
        long j2 = t5HomeActivity.maintenanceRunTimes / j;
        t5HomeActivity.maintenanceRunTimes = j2;
        return j2;
    }

    @Override // com.jess.arms.base.delegate.IActivity
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerT5HomeComponent.builder().appComponent(appComponent).view(this).build().inject(this);
    }

    @Override // com.jess.arms.base.delegate.IActivity
    public int initView(Bundle bundle) {
        return R.layout.activity_t5_home;
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(@androidx.annotation.NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putLong(Constants.EXTRA_DEVICE_ID, this.deviceId);
        bundle.putInt(Constants.EXTRA_DEVICE_TYPE, this.deviceType);
    }

    private void initViews() {
        this.rlRoot = (RelativeLayout) findViewById(R.id.rl_root);
        this.ibSetting = (ImageButton) findViewById(R.id.ib_setting);
        this.deviceStateText = (TextView) findViewById(R.id.device_state_text);
        this.toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        this.toolbar = (AutoToolbar) findViewById(R.id.toolbar);
        this.tvHighlights = (TextView) findViewById(R.id.tv_highlights);
        this.rvHighlights = (RecyclerView) findViewById(R.id.rv_highlights);
        this.llHighlights = (LinearLayout) findViewById(R.id.ll_highlights);
        this.ivAnim = (MyHeader) findViewById(R.id.iv_anim);
        this.srl = (MySmartRefreshView) findViewById(R.id.srl);
        this.rlList = (HorizontalScrollView) findViewById(R.id.rl_list);
        this.rlTopTab = (HorizontalScrollView) findViewById(R.id.rl_top_tab);
        this.tvAllTab = (TextView) findViewById(R.id.tv_t5_tab_all);
        this.tvToiletTab = (TextView) findViewById(R.id.tv_t5_tab_toilet);
        this.tvPetTab = (TextView) findViewById(R.id.tv_t5_tab_other);
        this.tvToiletTabError = (TextView) findViewById(R.id.tv_tab_toilet_error);
        this.tvAllTab2 = (TextView) findViewById(R.id.tv_t5_tab_all2);
        this.tvToiletTab2 = (TextView) findViewById(R.id.tv_tab_toilet2);
        this.tvPetTab2 = (TextView) findViewById(R.id.tv_tab_other2);
        this.tvToiletTabError2 = (TextView) findViewById(R.id.tv_tab_toilet_error2);
        this.rlPurchaseEligibility = (RelativeLayout) findViewById(R.id.rl_purchase_eligibility);
        this.tvPurchaseEligibility = (TextView) findViewById(R.id.tv_purchase_eligibility);
        this.t5BottomView = (T5BottomView) findViewById(R.id.bottom_view);
        this.tvTodayTimes = (TextView) findViewById(R.id.tv_today_times);
        this.ivTodayTimesArrow = (ImageView) findViewById(R.id.iv_today_times_arrow);
        this.tvTodayAvgDuration = (TextView) findViewById(R.id.tv_today_avg_duration);
        this.ivTodayAvgArrow = (ImageView) findViewById(R.id.iv_today_avg_arrow);
        this.tvSetting = (TextView) findViewById(R.id.tv_setting);
        this.ivDeviceStateIcon = (ImageView) findViewById(R.id.iv_device_state_icon);
        this.rlDeviceState = (RelativeLayout) findViewById(R.id.rl_device_state);
        this.deviceWorkStateText = (TextView) findViewById(R.id.device_work_state_text);
        this.ivWarnRight = (ImageView) findViewById(R.id.iv_warn_right);
        this.btnPause = (TextView) findViewById(R.id.btn_pause);
        this.btnContinue = (TextView) findViewById(R.id.btn_continue);
        this.btnStop = (TextView) findViewById(R.id.btn_stop);
        this.rlWorkState = (RelativeLayout) findViewById(R.id.rl_work_state);
        this.tvTodayEvent = (TextView) findViewById(R.id.tv_today_event);
        this.ivTodayEvent = (ImageView) findViewById(R.id.iv_today_event);
        this.ivTodayEvent2 = (ImageView) findViewById(R.id.iv_today_event2);
        this.ivChartShow = (ImageView) findViewById(R.id.iv_chart_show);
        this.tvTodayToiletStr = (TextView) findViewById(R.id.tv_today_toilet_str);
        this.tvTodayToiletAmount = (TextView) findViewById(R.id.tv_today_toilet_amount);
        this.vStart = findViewById(R.id.v_start);
        this.vEnd = findViewById(R.id.v_end);
        this.llTime = (LinearLayout) findViewById(R.id.ll_time);
        this.vp = (MyChartViewPager) findViewById(R.id.vp);
        this.ivEventAnim = (ImageView) findViewById(R.id.iv_event_anim);
        this.llPets = (LinearLayout) findViewById(R.id.ll_pets);
        this.rlChart = (RelativeLayout) findViewById(R.id.rl_chart);
        this.llDay = (LinearLayout) findViewById(R.id.ll_day);
        this.tvPopPetName = (TextView) findViewById(R.id.tv_pop_pet_name);
        this.tvPopTime = (TextView) findViewById(R.id.tv_pop_time);
        this.ivLastEvent = (ImageView) findViewById(R.id.iv_last_event);
        this.ivNextEvent = (ImageView) findViewById(R.id.iv_next_event);
        this.sdPet = (SmallCircleDotView) findViewById(R.id.sd_pet);
        this.ivPopImage = (NewRoundImageview) findViewById(R.id.iv_pop_image);
        this.ivChartPlay = (ImageView) findViewById(R.id.iv_chart_play);
        this.rlPopImage = (RelativeLayout) findViewById(R.id.rl_pop_image);
        this.tvY1 = (TextView) findViewById(R.id.tv_y1);
        this.tvY2 = (TextView) findViewById(R.id.tv_y2);
        this.tvY3 = (TextView) findViewById(R.id.tv_y3);
        this.rlPopContent = (RelativeLayout) findViewById(R.id.rl_pop_content);
        this.ivArrow = (ImageView) findViewById(R.id.iv_arrow);
        this.rlPop = (RelativeLayout) findViewById(R.id.rl_pop);
        this.ivLeftArrow = (ImageView) findViewById(R.id.iv_left_arrow);
        this.scPoint = findViewById(R.id.sc_point);
        this.tvPetName = (TextView) findViewById(R.id.tv_pet_name);
        this.scPoint2 = findViewById(R.id.sc_point2);
        this.tvPetName2 = (TextView) findViewById(R.id.tv_pet_name2);
        this.appBar = (AppBarLayout) findViewById(R.id.app_bar);
        this.vpList = (InterceptViewPager) findViewById(R.id.vp_list);
        this.ivSmallAll = (SmallCircleDotView) findViewById(R.id.iv_t5_small_all);
        this.ivSmallAll2 = (SmallCircleDotView) findViewById(R.id.iv_t5_small_all2);
        this.tvTopPaoPao = (TextView) findViewById(R.id.tv_top_pao_pao);
        this.ivDeviceErrorOneIcon = (ImageView) findViewById(R.id.iv_device_error_one_icon);
        this.deviceErrorOneText = (TextView) findViewById(R.id.device_error_one_text);
        this.ivDeviceErrorOneRightArrow = (ImageView) findViewById(R.id.iv_device_error_one_right_arrow);
        this.rlDeviceErrorOne = (RelativeLayout) findViewById(R.id.rl_device_error_one);
        this.ivDeviceErrorTwoIcon = (ImageView) findViewById(R.id.iv_device_error_two_icon);
        this.deviceErrorTwoText = (TextView) findViewById(R.id.device_error_two_text);
        this.ivDeviceErrorTwoRightArrow = (ImageView) findViewById(R.id.iv_device_error_two_right_arrow);
        this.rlDeviceErrorTwo = (RelativeLayout) findViewById(R.id.rl_device_error_two);
        this.vpHomeBanner = (ViewPager) findViewById(R.id.vp_home_banner);
        this.ivPagerClose = (ImageView) findViewById(R.id.iv_pager_close);
        this.rlHomeBanner = (RelativeLayout) findViewById(R.id.rl_home_banner);
        this.indicator = (CirclePageIndicator) findViewById(R.id.indicator);
        this.tvTrialRemainingTime = (TextView) findViewById(R.id.tv_trial_remaining_time);
        this.ivTrialRemainingTimeClose = (ImageView) findViewById(R.id.iv_trial_remaining_time_close);
        this.llTrialRemainingTime = (LinearLayout) findViewById(R.id.ll_trial_remaining_time);
        this.promoteView = (PromoteView) findViewById(R.id.promoteView);
        this.rlPopParent = (RelativeLayout) findViewById(R.id.rl_pop_parent);
        this.tvPopContent = (TextView) findViewById(R.id.tv_pop_content);
        this.vpLive = (ViewPager) findViewById(R.id.vp_live_panel);
        this.liveIndicator = (CirclePageIndicator) findViewById(R.id.live_indicator);
        this.tvDeviceTag = (TextView) findViewById(R.id.tv_device_tag);
        this.toolbarTitleStatusOne = (TextView) findViewById(R.id.toolbar_title_status_one);
        this.toolbarTitleStatusTwo = (TextView) findViewById(R.id.toolbar_title_status_two);
        this.llKnowMoreOne = (LinearLayout) findViewById(R.id.ll_know_more_one);
        this.llKnowMoreTwo = (LinearLayout) findViewById(R.id.ll_know_more_two);
        this.llMaintenanceMode = (LinearLayout) findViewById(R.id.ll_maintenance_mode);
        this.ivMaintain = (ImageView) findViewById(R.id.iv_maintain);
        this.rlWorkPanel = (LinearLayout) findViewById(R.id.rl_work_panel);
        this.btnMaintenanceModeTerminateClean = (Button) findViewById(R.id.btn_maintenance_mode_terminate_clean);
        this.btnMaintenanceModeContinueToRun = (Button) findViewById(R.id.btn_maintenance_mode_continue_to_run);
        this.btnMaintenanceModePauseClean = (Button) findViewById(R.id.btn_maintenance_mode_pause_clean);
        this.tvWorkState = (TextView) findViewById(R.id.tv_working_state);
        this.ivCircleProgress = (ImageView) findViewById(R.id.iv_circle_progress);
        this.llMaintenanceModeStatus = (LinearLayout) findViewById(R.id.ll_maintenance_mode_status);
        this.tvMaintenanceStatus = (TextView) findViewById(R.id.tv_maintenance_mode_status);
        this.tvMaintenanceDesc = (TextView) findViewById(R.id.tv_maintenance_mode_desc);
        this.ivMaintenanceLoading = (ImageView) findViewById(R.id.iv_maintenance_mode_loading);
        this.tvPetWeight = (TextView) findViewById(R.id.tv_pet_weight);
        this.btnKnowMoreOne = (TextView) findViewById(R.id.btn_know_more_one);
        this.btnIgnoreOne = (TextView) findViewById(R.id.btn_ignore_one);
        findViewById(R.id.device_work_state_text).setOnClickListener(this);
        findViewById(R.id.tv_pet_weight).setOnClickListener(this);
        findViewById(R.id.tv_tab_toilet_error).setOnClickListener(this);
        findViewById(R.id.tv_tab_toilet_error2).setOnClickListener(this);
        findViewById(R.id.ib_setting).setOnClickListener(this);
        findViewById(R.id.ib_back).setOnClickListener(this);
        findViewById(R.id.tv_title_status).setOnClickListener(this);
        findViewById(R.id.tv_more_vlog).setOnClickListener(this);
        findViewById(R.id.btn_ignore_one).setOnClickListener(this);
        findViewById(R.id.btn_ignore_two).setOnClickListener(this);
        findViewById(R.id.btn_know_more_one).setOnClickListener(this);
        findViewById(R.id.btn_know_more_two).setOnClickListener(this);
        findViewById(R.id.iv_today_event).setOnClickListener(this);
        findViewById(R.id.rl_pop_content).setOnClickListener(this);
        findViewById(R.id.iv_last_event).setOnClickListener(this);
        findViewById(R.id.iv_next_event).setOnClickListener(this);
        findViewById(R.id.tv_t5_tab_other).setOnClickListener(this);
        findViewById(R.id.tv_tab_other2).setOnClickListener(this);
        findViewById(R.id.ll_pets).setOnClickListener(this);
        findViewById(R.id.iv_chart_show).setOnClickListener(this);
        findViewById(R.id.tv_t5_tab_all).setOnClickListener(this);
        findViewById(R.id.tv_t5_tab_toilet).setOnClickListener(this);
        findViewById(R.id.tv_tab_toilet2).setOnClickListener(this);
        findViewById(R.id.rl_device_state).setOnClickListener(this);
        findViewById(R.id.rl_work_state).setOnClickListener(this);
        findViewById(R.id.btn_pause).setOnClickListener(this);
        findViewById(R.id.btn_continue).setOnClickListener(this);
        findViewById(R.id.btn_stop).setOnClickListener(this);
        findViewById(R.id.rl_device_error_one).setOnClickListener(this);
        findViewById(R.id.tv_top_pao_pao).setOnClickListener(this);
        findViewById(R.id.iv_today_event2).setOnClickListener(this);
        findViewById(R.id.rl_device_error_two).setOnClickListener(this);
        findViewById(R.id.ll_today_times_prompt).setOnClickListener(this);
        findViewById(R.id.ll_today_avg_duration_prompt).setOnClickListener(this);
        findViewById(R.id.tv_t5_tab_all2).setOnClickListener(this);
        findViewById(R.id.btn_maintenance_mode_terminate_clean).setOnClickListener(this);
        findViewById(R.id.btn_maintenance_mode_continue_to_run).setOnClickListener(this);
        findViewById(R.id.btn_maintenance_mode_pause_clean).setOnClickListener(this);
        findViewById(R.id.iv_maintenance_mode_doubt).setOnClickListener(this);
        findViewById(R.id.rl_purchase_eligibility).setOnClickListener(this);
    }

    @Override // com.jess.arms.base.delegate.IActivity
    @SuppressLint({"ClickableViewAccessibility"})
    public void initData(Bundle bundle) {
        String stringExtra;
        initViews();
        if (bundle != null) {
            this.deviceId = bundle.getLong(Constants.EXTRA_DEVICE_ID);
            this.deviceType = bundle.getInt(Constants.EXTRA_DEVICE_TYPE);
            this.eventId = bundle.getString(Constants.EXTRA_EVENT_ID);
            this.aiNotification = bundle.getBoolean(Constants.EXTRA_DEVICE_AI);
            stringExtra = bundle.getString(Consts.APP_WIDGET_EXTRA_DATA);
        } else {
            this.deviceId = getIntent().getLongExtra(Constants.EXTRA_DEVICE_ID, 0L);
            this.deviceType = getIntent().getIntExtra(Constants.EXTRA_DEVICE_TYPE, 0);
            this.eventId = getIntent().getStringExtra(Constants.EXTRA_EVENT_ID);
            this.aiNotification = getIntent().getBooleanExtra(Constants.EXTRA_DEVICE_AI, false);
            stringExtra = getIntent().getStringExtra(Consts.APP_WIDGET_EXTRA_DATA);
        }
        if (stringExtra != null) {
            this.widgetDataInfo = (WidgetDataInfo) new Gson().fromJson(stringExtra, WidgetDataInfo.class);
        }
        this.deviceType = 21;
        FamilyInfor familyInforThroughDevice = FamilyUtils.getInstance().getFamilyInforThroughDevice(this, this.deviceId, 21);
        this.familyInfor = familyInforThroughDevice;
        if (familyInforThroughDevice == null) {
            this.familyInfor = FamilyUtils.getInstance().getCurrentFamilyInfo(this);
        }
        this.handler = new MyHandler(getMainLooper(), this);
        if (FileUtils.checkAgoraFile() != 2 && FileUtils.checkAgoraFile() != 1) {
            openDownloadingWindow();
            EventBus.getDefault().post(new AgoraStartDownloadMsg());
        }
        findViewById(R.id.ib_back).setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.1
            public AnonymousClass1() {
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                T5HomeActivity.this.finish();
            }
        });
        initLiveView();
        this.selectedPetIds.add(ColorUtils.ALL_PET);
        initTodayTime();
        initAnimation();
        initView();
        initBottomView();
        initFragment();
        ((T5HomePresenter) this.mPresenter).initParams(this.deviceId, this.deviceType);
        this.rlRoot.setOnTouchListener(new View.OnTouchListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$$ExternalSyntheticLambda5
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return this.f$0.lambda$initData$0(view, motionEvent);
            }
        });
        setChartData();
        initSmartView();
        ((T5HomePresenter) this.mPresenter).getListNum(this.calendarTime);
        this.toolbarTitle.setTypeface(null, 1);
        this.appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$$ExternalSyntheticLambda6
            @Override // com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener, com.google.android.material.appbar.AppBarLayout.BaseOnOffsetChangedListener
            public final void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                this.f$0.lambda$initData$1(appBarLayout, i);
            }
        });
        this.resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$$ExternalSyntheticLambda7
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                this.f$0.lambda$initData$2((ActivityResult) obj);
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$1 */
    public class AnonymousClass1 implements View.OnClickListener {
        public AnonymousClass1() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            T5HomeActivity.this.finish();
        }
    }

    public /* synthetic */ boolean lambda$initData$0(View view, MotionEvent motionEvent) {
        if (this.rlPop.getVisibility() != 0 && this.ivArrow.getVisibility() != 0) {
            return false;
        }
        this.rlPop.setVisibility(8);
        return false;
    }

    public /* synthetic */ void lambda$initData$1(AppBarLayout appBarLayout, int i) {
        MyHandler myHandler;
        if (this.appBar == null) {
            return;
        }
        int i2 = this.appBarOffset;
        if (i2 - i > 0) {
            MyHandler myHandler2 = this.handler;
            if (myHandler2 != null) {
                myHandler2.sendEmptyMessage(13);
            }
        } else if (i2 - i < 0 && (myHandler = this.handler) != null) {
            myHandler.sendEmptyMessage(14);
        }
        if (Math.abs(i) > this.appBar.getTotalScrollRange() - ArmsUtils.dip2px(this, 16.0f)) {
            if (this.rlTopTab.getVisibility() == 8) {
                this.toolbar.setBackgroundColor(getResources().getColor(R.color.white));
                this.rlTopTab.setVisibility(0);
                this.ivTodayEvent2.setVisibility(0);
                this.toolbarTitle.setMaxWidth(ArmsUtils.dip2px(this, 160.0f));
                this.showCare = this.tvDeviceTag.getVisibility() == 0;
                this.toolbarTitle.setText(this.tvTodayToiletStr.getText().toString());
                this.tvDeviceTag.setVisibility(8);
            }
        } else if (this.rlTopTab.getVisibility() == 0) {
            this.toolbar.setBackgroundColor(getResources().getColor(R.color.transparent));
            this.rlTopTab.setVisibility(8);
            this.ivTodayEvent2.setVisibility(8);
            this.toolbarTitle.setMaxWidth(ArmsUtils.dip2px(this, 190.0f));
            this.toolbarTitle.setText(this.deviceName);
            this.tvDeviceTag.setVisibility(this.showCare ? 0 : 8);
        }
        if (this.rlDeviceState.getTop() > 0 && Math.abs(i) > this.rlDeviceState.getTop() && this.isNeedShowPetError) {
            showPopWindow();
        }
        this.appBarOffset = i;
    }

    public /* synthetic */ void lambda$initData$2(ActivityResult activityResult) {
        if (activityResult.getResultCode() == 104) {
            ((T5HomePresenter) this.mPresenter).T5Dump();
        } else if (activityResult.getResultCode() == 101) {
            ((T5HomePresenter) this.mPresenter).startMaintenance(this.deviceId);
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T5HomeContract.View
    public void refreshPurchaseEligibility() {
        PurchaseEligibilityInfo.PurchaseEligibility purchaseEligibilityWithDevice = CloudServiceUtils.getPurchaseEligibilityWithDevice(this, this.deviceId, this.deviceType);
        this.purchaseEligibility = purchaseEligibilityWithDevice;
        if (purchaseEligibilityWithDevice != null) {
            this.rlPurchaseEligibility.setVisibility(0);
            this.tvPurchaseEligibility.setText(this.purchaseEligibility.getNoticeBannerMsg());
        } else {
            this.rlPurchaseEligibility.setVisibility(8);
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T5HomeContract.View
    public void showPurchaseEligibility() {
        if (this.purchaseEligibility == null) {
            return;
        }
        CommonUtils.addSysBoolMap(this, "HOME_PURCHASE_ELIGIBILITY" + this.deviceType + this.deviceId, true);
        NormalListChoiceCenterWindow normalListChoiceCenterWindow = new NormalListChoiceCenterWindow(this, new NormalListChoiceCenterWindow.OnClickThreeChoices() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.2
            @Override // com.petkit.android.activities.universalWindow.NormalListChoiceCenterWindow.OnClickThreeChoices
            public void onClickThirdChoice() {
            }

            public AnonymousClass2() {
            }

            @Override // com.petkit.android.activities.universalWindow.NormalListChoiceCenterWindow.OnClickThreeChoices
            public void onClickFirstChoice() {
                T5HomeActivity t5HomeActivity = T5HomeActivity.this;
                t5HomeActivity.launchActivity(CloudServiceWebViewActivity.newIntent(t5HomeActivity, "", CloudServiceUtils.getPurchaseEligibilityUrl(t5HomeActivity, t5HomeActivity.purchaseEligibility)));
            }

            /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$2$1 */
            public class AnonymousClass1 implements NormalCenterTipWindow.OnClickOk {
                @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
                public void onOkClick() {
                }

                public AnonymousClass1() {
                }

                @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
                public void onCancelClick() {
                    ((T5HomePresenter) ((BaseActivity) T5HomeActivity.this).mPresenter).purchaseEligibilityNoRemind(T5HomeActivity.this.purchaseEligibility);
                }
            }

            @Override // com.petkit.android.activities.universalWindow.NormalListChoiceCenterWindow.OnClickThreeChoices
            public void onClickSecondChoice() {
                NormalCenterTipWindow normalCenterTipWindow = new NormalCenterTipWindow(T5HomeActivity.this, new NormalCenterTipWindow.OnClickOk() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.2.1
                    @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
                    public void onOkClick() {
                    }

                    public AnonymousClass1() {
                    }

                    @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
                    public void onCancelClick() {
                        ((T5HomePresenter) ((BaseActivity) T5HomeActivity.this).mPresenter).purchaseEligibilityNoRemind(T5HomeActivity.this.purchaseEligibility);
                    }
                }, (String) null, T5HomeActivity.this.getString(R.string.Claim_close_confirm));
                normalCenterTipWindow.setCancelButtonText(T5HomeActivity.this.getString(R.string.Affirm_Close));
                normalCenterTipWindow.setOkButtonText(T5HomeActivity.this.getString(R.string.Cancel));
                normalCenterTipWindow.show(T5HomeActivity.this.getWindow().getDecorView());
            }
        }, this.purchaseEligibility.getReminderMsgTitle(), this.purchaseEligibility.getReminderMsg(), getString(R.string.Claim_now), getString(R.string.Do_not_remind_again), null);
        normalListChoiceCenterWindow.setThreeChoicesTextColor(getResources().getColor(R.color.toilet_chart_color_nine), -1, -1);
        normalListChoiceCenterWindow.setTipGravity(GravityCompat.START);
        normalListChoiceCenterWindow.setWindowMargin(20, 20);
        normalListChoiceCenterWindow.setCloseIcon(true);
        normalListChoiceCenterWindow.show(getWindow().getDecorView());
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$2 */
    public class AnonymousClass2 implements NormalListChoiceCenterWindow.OnClickThreeChoices {
        @Override // com.petkit.android.activities.universalWindow.NormalListChoiceCenterWindow.OnClickThreeChoices
        public void onClickThirdChoice() {
        }

        public AnonymousClass2() {
        }

        @Override // com.petkit.android.activities.universalWindow.NormalListChoiceCenterWindow.OnClickThreeChoices
        public void onClickFirstChoice() {
            T5HomeActivity t5HomeActivity = T5HomeActivity.this;
            t5HomeActivity.launchActivity(CloudServiceWebViewActivity.newIntent(t5HomeActivity, "", CloudServiceUtils.getPurchaseEligibilityUrl(t5HomeActivity, t5HomeActivity.purchaseEligibility)));
        }

        /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$2$1 */
        public class AnonymousClass1 implements NormalCenterTipWindow.OnClickOk {
            @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
            public void onOkClick() {
            }

            public AnonymousClass1() {
            }

            @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
            public void onCancelClick() {
                ((T5HomePresenter) ((BaseActivity) T5HomeActivity.this).mPresenter).purchaseEligibilityNoRemind(T5HomeActivity.this.purchaseEligibility);
            }
        }

        @Override // com.petkit.android.activities.universalWindow.NormalListChoiceCenterWindow.OnClickThreeChoices
        public void onClickSecondChoice() {
            NormalCenterTipWindow normalCenterTipWindow = new NormalCenterTipWindow(T5HomeActivity.this, new NormalCenterTipWindow.OnClickOk() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.2.1
                @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
                public void onOkClick() {
                }

                public AnonymousClass1() {
                }

                @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
                public void onCancelClick() {
                    ((T5HomePresenter) ((BaseActivity) T5HomeActivity.this).mPresenter).purchaseEligibilityNoRemind(T5HomeActivity.this.purchaseEligibility);
                }
            }, (String) null, T5HomeActivity.this.getString(R.string.Claim_close_confirm));
            normalCenterTipWindow.setCancelButtonText(T5HomeActivity.this.getString(R.string.Affirm_Close));
            normalCenterTipWindow.setOkButtonText(T5HomeActivity.this.getString(R.string.Cancel));
            normalCenterTipWindow.show(T5HomeActivity.this.getWindow().getDecorView());
        }
    }

    private void initLiveView() {
        RelativeLayout relativeLayout = (RelativeLayout) View.inflate(this, R.layout.layout_t5_live_center_view, null);
        this.rlLivePanel = (RelativeLayout) relativeLayout.findViewById(R.id.rl_live_panel);
        this.ivBg = (RoundImageview) relativeLayout.findViewById(R.id.iv_bg);
        this.ivFox = (ImageView) relativeLayout.findViewById(R.id.iv_fox);
        this.videoTagLayout = (RelativeLayout) relativeLayout.findViewById(R.id.video_tag_layout);
        this.videoTag = (TextView) relativeLayout.findViewById(R.id.video_tag);
        this.scdView = (SmallCircleDotView) relativeLayout.findViewById(R.id.scd_view);
        this.rlParentLive = (RelativeLayout) relativeLayout.findViewById(R.id.rl_parent_live);
        this.vCameraBottom = (TextView) relativeLayout.findViewById(R.id.tv_camera_bottom);
        this.ivCameraAnim = (ImageView) relativeLayout.findViewById(R.id.iv_camera_anim);
        this.clDevice = (CircleLayout) relativeLayout.findViewById(R.id.cl_device);
        this.ivDeviceClose = (ImageView) relativeLayout.findViewById(R.id.iv_device_close);
        this.ivDeviceOpen = (ImageView) relativeLayout.findViewById(R.id.iv_device_open);
        this.ivEyeAnim = (ImageView) relativeLayout.findViewById(R.id.iv_eye_anim);
        this.clLoading = (CircleLayout) relativeLayout.findViewById(R.id.cl_loading);
        this.clEyeAnimBackground = (CircleLayout) relativeLayout.findViewById(R.id.cl_eye_anim_background);
        this.clLoading = (CircleLayout) relativeLayout.findViewById(R.id.cl_loading);
        this.ltLoading = (LottieAnimationView) relativeLayout.findViewById(R.id.lt_device_loading);
        this.ivMuteIcon = (ImageView) relativeLayout.findViewById(R.id.iv_mute_icon);
        T5LiveViewPagerAdapter t5LiveViewPagerAdapter = new T5LiveViewPagerAdapter(this, relativeLayout, this.handler);
        this.t5LiveViewPagerAdapter = t5LiveViewPagerAdapter;
        t5LiveViewPagerAdapter.setOnClickListener(new T5LiveViewPagerAdapter.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.3
            public AnonymousClass3() {
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.adapter.T5LiveViewPagerAdapter.OnClickListener
            public void onCatLitterClick() {
                T5HomeActivity t5HomeActivity = T5HomeActivity.this;
                t5HomeActivity.launchActivity(T6ConsumablesActivity.newIntent(t5HomeActivity, t5HomeActivity.deviceId, 21, 1, T5HomeActivity.this.relatedProductsInfor));
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.adapter.T5LiveViewPagerAdapter.OnClickListener
            public void onN60Click(boolean z) {
                T5HomeActivity t5HomeActivity = T5HomeActivity.this;
                t5HomeActivity.launchActivity(T6ConsumablesActivity.newIntent(t5HomeActivity, t5HomeActivity.deviceId, 21, 6, T5HomeActivity.this.relatedProductsInfor));
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.adapter.T5LiveViewPagerAdapter.OnClickListener
            public void onN50Click() {
                T5HomeActivity t5HomeActivity = T5HomeActivity.this;
                t5HomeActivity.launchActivity(T6ConsumablesActivity.newIntent(t5HomeActivity, t5HomeActivity.deviceId, 21, 5, T5HomeActivity.this.relatedProductsInfor));
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.adapter.T5LiveViewPagerAdapter.OnClickListener
            public void onDumpClick() {
                T5HomeActivity t5HomeActivity = T5HomeActivity.this;
                t5HomeActivity.launchActivity(T6ConsumablesActivity.newIntent(t5HomeActivity, t5HomeActivity.deviceId, 21, 2, T5HomeActivity.this.relatedProductsInfor));
            }
        });
        this.vpLive.setAdapter(this.t5LiveViewPagerAdapter);
        this.vpLive.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.4
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
                if (i == 1) {
                    T5HomeActivity.this.t5LiveViewPagerAdapter.setCurrentPosition(i);
                    T5HomeActivity.this.t5LiveViewPagerAdapter.refreshData();
                }
                CommonUtils.addSysIntMap(Constants.T6_HOME_PAGE_SELECT + T5HomeActivity.this.deviceId, i);
            }
        });
        this.liveIndicator.setViewPager(this.vpLive, 0, 2);
        this.liveIndicator.setPageColor(CommonUtils.getColorById(R.color.color_D2C5BC));
        this.liveIndicator.setFillColor(CommonUtils.getColorById(R.color.light_black));
        this.liveIndicator.setSnap(true);
        this.liveIndicator.setIndicatorStyle(3);
        this.liveIndicator.setRadius(ArmsUtils.dip2px(CommonUtils.getAppContext(), 9.0f));
        this.liveIndicator.setClickChange(true);
        this.liveIndicator.setRecRadius(9.0f, true);
        this.ivMuteIcon.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$$ExternalSyntheticLambda53
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initLiveView$3(view);
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$3 */
    public class AnonymousClass3 implements T5LiveViewPagerAdapter.OnClickListener {
        public AnonymousClass3() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t6.adapter.T5LiveViewPagerAdapter.OnClickListener
        public void onCatLitterClick() {
            T5HomeActivity t5HomeActivity = T5HomeActivity.this;
            t5HomeActivity.launchActivity(T6ConsumablesActivity.newIntent(t5HomeActivity, t5HomeActivity.deviceId, 21, 1, T5HomeActivity.this.relatedProductsInfor));
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t6.adapter.T5LiveViewPagerAdapter.OnClickListener
        public void onN60Click(boolean z) {
            T5HomeActivity t5HomeActivity = T5HomeActivity.this;
            t5HomeActivity.launchActivity(T6ConsumablesActivity.newIntent(t5HomeActivity, t5HomeActivity.deviceId, 21, 6, T5HomeActivity.this.relatedProductsInfor));
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t6.adapter.T5LiveViewPagerAdapter.OnClickListener
        public void onN50Click() {
            T5HomeActivity t5HomeActivity = T5HomeActivity.this;
            t5HomeActivity.launchActivity(T6ConsumablesActivity.newIntent(t5HomeActivity, t5HomeActivity.deviceId, 21, 5, T5HomeActivity.this.relatedProductsInfor));
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t6.adapter.T5LiveViewPagerAdapter.OnClickListener
        public void onDumpClick() {
            T5HomeActivity t5HomeActivity = T5HomeActivity.this;
            t5HomeActivity.launchActivity(T6ConsumablesActivity.newIntent(t5HomeActivity, t5HomeActivity.deviceId, 21, 2, T5HomeActivity.this.relatedProductsInfor));
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$4 */
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
            if (i == 1) {
                T5HomeActivity.this.t5LiveViewPagerAdapter.setCurrentPosition(i);
                T5HomeActivity.this.t5LiveViewPagerAdapter.refreshData();
            }
            CommonUtils.addSysIntMap(Constants.T6_HOME_PAGE_SELECT + T5HomeActivity.this.deviceId, i);
        }
    }

    public /* synthetic */ void lambda$initLiveView$3(View view) {
        if (T6Utils.checkDeviceState(this, this.deviceId, this.deviceType, ((T5HomePresenter) this.mPresenter).getLiveService())) {
            if (this.t5Record.getSettings().getMicrophone() == 0) {
                PetkitToast.showTopToast(this, getResources().getString(R.string.Can_not_control_voice_tip), 0, 0);
                return;
            }
            if (((T5HomePresenter) this.mPresenter).intercomBtnPushing) {
                PetkitToast.showTopToast(this, getResources().getString(R.string.Intercom_cannot_operate), 0, 0);
            } else if (T6Utils.getVolumeState()) {
                ((T5HomePresenter) this.mPresenter).openVolume();
                this.ivMuteIcon.setImageResource(R.drawable.t6_home_mute_off_icon);
            } else {
                ((T5HomePresenter) this.mPresenter).closeVolume();
                this.ivMuteIcon.setImageResource(R.drawable.t6_home_mute_icon);
            }
        }
    }

    private void startCleanPrepareAnimation() {
        this.ivFox.setImageResource(R.drawable.t5_animation_drawable_one);
        AnimationDrawable animationDrawable = (AnimationDrawable) this.ivFox.getDrawable();
        this.animationDrawable = animationDrawable;
        animationDrawable.start();
    }

    public void startCleanAnimation() {
        this.ivFox.setImageResource(R.drawable.t5_animation_drawable_two);
        AnimationDrawable animationDrawable = (AnimationDrawable) this.ivFox.getDrawable();
        this.animationDrawable = animationDrawable;
        animationDrawable.start();
    }

    private void stopFoxAnimation() {
        AnimationDrawable animationDrawable = this.animationDrawable;
        if (animationDrawable != null) {
            animationDrawable.stop();
            this.ivFox.setImageResource(R.drawable.fox2_00001);
        }
    }

    private void initView() {
        T5LiveVideoView t5LiveVideoView = new T5LiveVideoView(this);
        this.t5LiveVideoView = t5LiveVideoView;
        t5LiveVideoView.setPlayerType(PetkitVideoPlayerView.PlayerType.CIRCLE);
        this.t5LiveVideoView.setTouchListener();
        this.rlParentLive.addView(this.t5LiveVideoView);
        this.toolbar.setBackgroundColor(getResources().getColor(R.color.transparent));
        this.ibSetting.setVisibility(0);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(0);
        this.rvHighlights.setLayoutManager(linearLayoutManager);
        this.t5LiveVideoView.setT6LiveVideoViewListener(this);
        this.t5LiveVideoView.setListener(this);
        ViewGroup.LayoutParams layoutParams = this.rlHomeBanner.getLayoutParams();
        layoutParams.height = ((BaseApplication.displayMetrics.widthPixels - ArmsUtils.dip2px(CommonUtils.getAppContext(), 16.0f)) / 343) * 64;
        this.rlHomeBanner.setLayoutParams(layoutParams);
        this.ivBg.setAllRound();
        this.rlLivePanel.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.5
            public AnonymousClass5() {
            }

            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                int height = T5HomeActivity.this.rlLivePanel.getHeight();
                ViewGroup.LayoutParams layoutParams2 = T5HomeActivity.this.ivBg.getLayoutParams();
                layoutParams2.height = height;
                T5HomeActivity.this.ivBg.setLayoutParams(layoutParams2);
                T5HomeActivity.this.rlLivePanel.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$5 */
    public class AnonymousClass5 implements ViewTreeObserver.OnGlobalLayoutListener {
        public AnonymousClass5() {
        }

        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public void onGlobalLayout() {
            int height = T5HomeActivity.this.rlLivePanel.getHeight();
            ViewGroup.LayoutParams layoutParams2 = T5HomeActivity.this.ivBg.getLayoutParams();
            layoutParams2.height = height;
            T5HomeActivity.this.ivBg.setLayoutParams(layoutParams2);
            T5HomeActivity.this.rlLivePanel.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }
    }

    private void initTodayTime() {
        this.calendarTime = T6Utils.getCurrentZoneTime(this.t5Record, this.deviceId, System.currentTimeMillis(), this.deviceType);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$6 */
    public class AnonymousClass6 implements OnRefreshLoadMoreListener {
        @Override // com.scwang.smart.refresh.layout.listener.OnLoadMoreListener
        public void onLoadMore(@androidx.annotation.NonNull RefreshLayout refreshLayout) {
        }

        public AnonymousClass6() {
        }

        @Override // com.scwang.smart.refresh.layout.listener.OnRefreshListener
        public void onRefresh(@androidx.annotation.NonNull RefreshLayout refreshLayout) {
            T5HomeActivity.this.refreshHeader();
        }
    }

    private void initSmartView() {
        this.srl.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.6
            @Override // com.scwang.smart.refresh.layout.listener.OnLoadMoreListener
            public void onLoadMore(@androidx.annotation.NonNull RefreshLayout refreshLayout) {
            }

            public AnonymousClass6() {
            }

            @Override // com.scwang.smart.refresh.layout.listener.OnRefreshListener
            public void onRefresh(@androidx.annotation.NonNull RefreshLayout refreshLayout) {
                T5HomeActivity.this.refreshHeader();
            }
        });
        this.srl.setDispatchListener(new MySmartRefreshView.DispatchListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$$ExternalSyntheticLambda18
            @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.MySmartRefreshView.DispatchListener
            public final void onActionDown() {
                this.f$0.lambda$initSmartView$4();
            }
        });
    }

    public /* synthetic */ void lambda$initSmartView$4() {
        RelativeLayout relativeLayout = this.rlPop;
        if (relativeLayout != null && relativeLayout.getVisibility() == 0) {
            this.rlPop.setVisibility(8);
        }
        RelativeLayout relativeLayout2 = this.rlPopParent;
        if (relativeLayout2 == null || relativeLayout2.getVisibility() != 0) {
            return;
        }
        this.rlPopParent.setVisibility(8);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$7 */
    public class AnonymousClass7 implements T6AnimUtil.AnimationListener {
        @Override // com.petkit.android.activities.petkitBleDevice.t6.utils.T6AnimUtil.AnimationListener
        public void scaleAnimationStar() {
        }

        public AnonymousClass7() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t6.utils.T6AnimUtil.AnimationListener
        public void alphaAnimationStar() {
            T5HomeActivity.this.ivDeviceOpen.setVisibility(0);
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t6.utils.T6AnimUtil.AnimationListener
        public void alphaAnimationEnd() {
            T5HomeActivity.this.t6AnimUtil.startScaleAnim(T5HomeActivity.this.ivDeviceOpen);
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t6.utils.T6AnimUtil.AnimationListener
        public void scaleAnimationEnd() {
            T5HomeActivity.this.clDevice.setVisibility(8);
            T5HomeActivity.this.t5LiveVideoView.setVisibility(0);
            T5HomeActivity.this.isNeedTurnOnDeviceAni = true;
            T5HomeActivity.this.isAnimationRunning = false;
            T5HomeActivity t5HomeActivity = T5HomeActivity.this;
            t5HomeActivity.setupView(t5HomeActivity.t5Record);
            T5HomeActivity.this.t6AnimUtil.resetScaleAnim(T5HomeActivity.this.ivDeviceOpen);
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t6.utils.T6AnimUtil.AnimationListener
        public void cameraAnimationStar() {
            T5HomeActivity.this.vCameraBottom.setVisibility(8);
            T5HomeActivity.this.t5LiveVideoView.setVisibility(0);
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t6.utils.T6AnimUtil.AnimationListener
        public void cameraAnimationEnd() {
            T5HomeActivity.this.isNeedTurnOnDeviceAni = true;
            T5HomeActivity.this.isAnimationRunning = false;
            T5HomeActivity t5HomeActivity = T5HomeActivity.this;
            t5HomeActivity.setupView(t5HomeActivity.t5Record);
        }
    }

    private void initAnimation() {
        T6AnimUtil t6AnimUtil = new T6AnimUtil();
        this.t6AnimUtil = t6AnimUtil;
        t6AnimUtil.setAnimationListener(new T6AnimUtil.AnimationListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.7
            @Override // com.petkit.android.activities.petkitBleDevice.t6.utils.T6AnimUtil.AnimationListener
            public void scaleAnimationStar() {
            }

            public AnonymousClass7() {
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.utils.T6AnimUtil.AnimationListener
            public void alphaAnimationStar() {
                T5HomeActivity.this.ivDeviceOpen.setVisibility(0);
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.utils.T6AnimUtil.AnimationListener
            public void alphaAnimationEnd() {
                T5HomeActivity.this.t6AnimUtil.startScaleAnim(T5HomeActivity.this.ivDeviceOpen);
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.utils.T6AnimUtil.AnimationListener
            public void scaleAnimationEnd() {
                T5HomeActivity.this.clDevice.setVisibility(8);
                T5HomeActivity.this.t5LiveVideoView.setVisibility(0);
                T5HomeActivity.this.isNeedTurnOnDeviceAni = true;
                T5HomeActivity.this.isAnimationRunning = false;
                T5HomeActivity t5HomeActivity = T5HomeActivity.this;
                t5HomeActivity.setupView(t5HomeActivity.t5Record);
                T5HomeActivity.this.t6AnimUtil.resetScaleAnim(T5HomeActivity.this.ivDeviceOpen);
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.utils.T6AnimUtil.AnimationListener
            public void cameraAnimationStar() {
                T5HomeActivity.this.vCameraBottom.setVisibility(8);
                T5HomeActivity.this.t5LiveVideoView.setVisibility(0);
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.utils.T6AnimUtil.AnimationListener
            public void cameraAnimationEnd() {
                T5HomeActivity.this.isNeedTurnOnDeviceAni = true;
                T5HomeActivity.this.isAnimationRunning = false;
                T5HomeActivity t5HomeActivity = T5HomeActivity.this;
                t5HomeActivity.setupView(t5HomeActivity.t5Record);
            }
        });
        this.t6AnimUtil.startLeftArrowAnimation(this.ivLeftArrow);
    }

    private void initFragment() {
        ArrayList arrayList = new ArrayList();
        this.fragmentToilet = getFragment(0);
        this.fragmentPet = getFragment(1);
        this.fragmentAll = getFragment(2);
        this.toiletErrorFragment = getFragment(3);
        arrayList.add(this.fragmentAll);
        arrayList.add(this.fragmentToilet);
        arrayList.add(this.fragmentPet);
        arrayList.add(this.toiletErrorFragment);
        this.vpList.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager(), arrayList));
        this.vpList.setPagingEnabled(false);
        this.vpList.setOffscreenPageLimit(3);
        this.vpList.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.8
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i, float f, int i2) {
            }

            public AnonymousClass8() {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int i) {
                T5HomeActivity t5HomeActivity = T5HomeActivity.this;
                t5HomeActivity.setTextUi(i, t5HomeActivity.tvAllTab, t5HomeActivity.tvToiletTab, t5HomeActivity.tvPetTab, t5HomeActivity.tvToiletTabError);
                T5HomeActivity t5HomeActivity2 = T5HomeActivity.this;
                t5HomeActivity2.setTopTextUi(i, t5HomeActivity2.tvAllTab2, t5HomeActivity2.tvToiletTab2, t5HomeActivity2.tvPetTab2, t5HomeActivity2.tvToiletTabError2);
                T5HomeActivity.this.currentPosition = i;
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$8 */
    public class AnonymousClass8 implements ViewPager.OnPageChangeListener {
        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrollStateChanged(int i) {
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrolled(int i, float f, int i2) {
        }

        public AnonymousClass8() {
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageSelected(int i) {
            T5HomeActivity t5HomeActivity = T5HomeActivity.this;
            t5HomeActivity.setTextUi(i, t5HomeActivity.tvAllTab, t5HomeActivity.tvToiletTab, t5HomeActivity.tvPetTab, t5HomeActivity.tvToiletTabError);
            T5HomeActivity t5HomeActivity2 = T5HomeActivity.this;
            t5HomeActivity2.setTopTextUi(i, t5HomeActivity2.tvAllTab2, t5HomeActivity2.tvToiletTab2, t5HomeActivity2.tvPetTab2, t5HomeActivity2.tvToiletTabError2);
            T5HomeActivity.this.currentPosition = i;
        }
    }

    @SuppressLint({"UseCompatLoadingForDrawables"})
    private void showRedToilet(boolean z) {
        if (z) {
            Drawable drawable = getDrawable(R.drawable.icon_t7_toilet_error);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            this.tvToiletTabError.setCompoundDrawables(null, null, drawable, null);
            this.tvToiletTabError2.setCompoundDrawables(null, null, drawable, null);
            return;
        }
        this.tvToiletTabError.setCompoundDrawables(null, null, null, null);
        this.tvToiletTabError2.setCompoundDrawables(null, null, null, null);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$9 */
    public class AnonymousClass9 implements T6EventListFragment.FragmentListener {
        public AnonymousClass9() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t6.T6EventListFragment.FragmentListener
        public void recyclerViewScroll(int i) {
            if (i > 0) {
                if (T5HomeActivity.this.handler != null) {
                    T5HomeActivity.this.handler.sendEmptyMessage(13);
                }
            } else {
                if (i >= 0 || T5HomeActivity.this.handler == null) {
                    return;
                }
                T5HomeActivity.this.handler.sendEmptyMessage(14);
            }
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t6.T6EventListFragment.FragmentListener
        public void loadFirstPageData(int i) {
            if (i == 0) {
                T5HomeActivity.this.newToiletEventNum = 0;
                T5HomeActivity.this.newClearEventNum = 0;
            } else if (i == 1) {
                T5HomeActivity.this.newPetEventNum = 0;
            } else if (i != 3) {
                T5HomeActivity.this.hideAllRedPoint();
            } else {
                T5HomeActivity.this.newToiletErrorEventNum = 0;
            }
        }
    }

    private T6EventListFragment getFragment(int i) {
        T6EventListFragment t6EventListFragment = new T6EventListFragment();
        t6EventListFragment.setListener(new T6EventListFragment.FragmentListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.9
            public AnonymousClass9() {
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.T6EventListFragment.FragmentListener
            public void recyclerViewScroll(int i2) {
                if (i2 > 0) {
                    if (T5HomeActivity.this.handler != null) {
                        T5HomeActivity.this.handler.sendEmptyMessage(13);
                    }
                } else {
                    if (i2 >= 0 || T5HomeActivity.this.handler == null) {
                        return;
                    }
                    T5HomeActivity.this.handler.sendEmptyMessage(14);
                }
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.T6EventListFragment.FragmentListener
            public void loadFirstPageData(int i2) {
                if (i2 == 0) {
                    T5HomeActivity.this.newToiletEventNum = 0;
                    T5HomeActivity.this.newClearEventNum = 0;
                } else if (i2 == 1) {
                    T5HomeActivity.this.newPetEventNum = 0;
                } else if (i2 != 3) {
                    T5HomeActivity.this.hideAllRedPoint();
                } else {
                    T5HomeActivity.this.newToiletErrorEventNum = 0;
                }
            }
        });
        t6EventListFragment.setArguments(getBundle(i));
        return t6EventListFragment;
    }

    private Bundle getBundle(int i) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.T6_EVENT_LOAD_TYPE, i);
        bundle.putLong(Constants.EXTRA_DEVICE_ID, this.deviceId);
        bundle.putInt(Constants.EXTRA_DEVICE_TYPE, 21);
        bundle.putLong("EXTRA_TIME", this.calendarTime);
        return bundle;
    }

    private void selectedFragment(int i) {
        InterceptViewPager interceptViewPager = this.vpList;
        if (interceptViewPager != null) {
            interceptViewPager.setCurrentItem(i, false);
        }
    }

    public void setTextUi(int i, TextView... textViewArr) {
        for (int i2 = 0; i2 < textViewArr.length; i2++) {
            TextView textView = textViewArr[i2];
            if (i2 != i) {
                textView.setTextColor(getResources().getColor(R.color.t4_text_gray));
                textView.setBackgroundResource(R.drawable.circle_light_gray_bg_12);
            } else if (i == 3) {
                if (this.toiletErrorEventNum == 0) {
                    textView.setTextColor(getResources().getColor(R.color.new_bind_blue));
                    textView.setBackgroundResource(R.drawable.circle_light_blue_bg_12);
                } else {
                    textView.setTextColor(getResources().getColor(R.color.color_FF2B2B));
                    textView.setBackgroundResource(R.drawable.circle_red_bg_12);
                }
            } else {
                textView.setTextColor(getResources().getColor(R.color.new_bind_blue));
                textView.setBackgroundResource(R.drawable.circle_light_blue_bg_12);
            }
        }
    }

    public void setTopTextUi(int i, TextView... textViewArr) {
        for (int i2 = 0; i2 < textViewArr.length; i2++) {
            TextView textView = textViewArr[i2];
            if (i2 != i) {
                textView.setTextColor(getResources().getColor(R.color.t4_text_gray));
            } else if (i == 3) {
                textView.setTextColor(getResources().getColor(R.color.color_FF2B2B));
            } else {
                textView.setTextColor(getResources().getColor(R.color.new_bind_blue));
            }
        }
    }

    public void refreshHeader() {
        if (this.ivSmallAll.getVisibility() == 0 || this.ivSmallAll2.getVisibility() == 0) {
            initTodayTime();
        }
        hideAllRedPoint();
        this.ivEventAnim.setVisibility(0);
        this.t6AnimUtil.startRotateAnim(this.ivEventAnim);
        ((T5HomePresenter) this.mPresenter).getStatistic(this.calendarTime, 0);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$10 */
    public class AnonymousClass10 implements T5BottomView.BottomListener {
        public AnonymousClass10() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.T5BottomView.BottomListener
        public void Maintenance() {
            T5HomeActivity.this.maintenance();
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.T5BottomView.BottomListener
        public void smooth() {
            T5HomeActivity.this.showSmoothWindow();
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.T5BottomView.BottomListener
        public void dump() {
            Intent intent = new Intent(T5HomeActivity.this, (Class<?>) T4EmptyCatLitterPromptActivity.class);
            intent.putExtra(Constants.EXTRA_DEVICE_ID, T5HomeActivity.this.deviceId);
            intent.putExtra(Constants.EXTRA_DEVICE_TYPE, 21);
            intent.putExtra(Constants.EXTRA_RELATED_PRODUCTS_INFOR, T5HomeActivity.this.relatedProductsInfor);
            T5HomeActivity.this.resultLauncher.launch(intent);
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.T5BottomView.BottomListener
        public void recover() {
            T5HomeActivity.this.showRecoverWindow();
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.T5BottomView.BottomListener
        public void baseSet() {
            T5HomeActivity t5HomeActivity = T5HomeActivity.this;
            t5HomeActivity.launchActivity(T6SmartSettingActivity.newIntent(t5HomeActivity, t5HomeActivity.deviceId, T5HomeActivity.this.deviceType));
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.T5BottomView.BottomListener
        public void clean() {
            T5HomeActivity.this.showCleanUpWindow();
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.T5BottomView.BottomListener
        public void odor() {
            if (T5HomeActivity.this.t5Record.getState().getSprayState() == 1) {
                T5HomeActivity.this.removeOdor();
            } else {
                T5HomeActivity t5HomeActivity = T5HomeActivity.this;
                t5HomeActivity.unInstallN60(((T5HomePresenter) ((BaseActivity) t5HomeActivity).mPresenter).getCopyWritingGifVideo());
            }
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.T5BottomView.BottomListener
        public void light() {
            ((T5HomePresenter) ((BaseActivity) T5HomeActivity.this).mPresenter).switchLightState();
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.T5BottomView.BottomListener
        public void Ai(boolean z) {
            if (z) {
                return;
            }
            T5HomeActivity t5HomeActivity = T5HomeActivity.this;
            PetkitToast.showTopToast(t5HomeActivity, t5HomeActivity.getResources().getString(R.string.Petkit_ai_tips), 0, 0);
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.T5BottomView.BottomListener
        public void AiVoice() {
            T5HomeActivity t5HomeActivity = T5HomeActivity.this;
            t5HomeActivity.launchActivity(AiVoiceActivity.newIntent(t5HomeActivity, t5HomeActivity.deviceId, T5HomeActivity.this.t5Record.getTypeCode()));
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.T5BottomView.BottomListener
        public void AiPh() {
            T5HomeActivity t5HomeActivity = T5HomeActivity.this;
            t5HomeActivity.launchActivity(AiPhActivity.newIntent(t5HomeActivity, t5HomeActivity.deviceId, T5HomeActivity.this.t5Record.getTypeCode(), T5HomeActivity.this.relatedProductsInfor));
        }
    }

    private void initBottomView() {
        this.t5BottomView.setBottomListener(new T5BottomView.BottomListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.10
            public AnonymousClass10() {
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.T5BottomView.BottomListener
            public void Maintenance() {
                T5HomeActivity.this.maintenance();
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.T5BottomView.BottomListener
            public void smooth() {
                T5HomeActivity.this.showSmoothWindow();
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.T5BottomView.BottomListener
            public void dump() {
                Intent intent = new Intent(T5HomeActivity.this, (Class<?>) T4EmptyCatLitterPromptActivity.class);
                intent.putExtra(Constants.EXTRA_DEVICE_ID, T5HomeActivity.this.deviceId);
                intent.putExtra(Constants.EXTRA_DEVICE_TYPE, 21);
                intent.putExtra(Constants.EXTRA_RELATED_PRODUCTS_INFOR, T5HomeActivity.this.relatedProductsInfor);
                T5HomeActivity.this.resultLauncher.launch(intent);
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.T5BottomView.BottomListener
            public void recover() {
                T5HomeActivity.this.showRecoverWindow();
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.T5BottomView.BottomListener
            public void baseSet() {
                T5HomeActivity t5HomeActivity = T5HomeActivity.this;
                t5HomeActivity.launchActivity(T6SmartSettingActivity.newIntent(t5HomeActivity, t5HomeActivity.deviceId, T5HomeActivity.this.deviceType));
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.T5BottomView.BottomListener
            public void clean() {
                T5HomeActivity.this.showCleanUpWindow();
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.T5BottomView.BottomListener
            public void odor() {
                if (T5HomeActivity.this.t5Record.getState().getSprayState() == 1) {
                    T5HomeActivity.this.removeOdor();
                } else {
                    T5HomeActivity t5HomeActivity = T5HomeActivity.this;
                    t5HomeActivity.unInstallN60(((T5HomePresenter) ((BaseActivity) t5HomeActivity).mPresenter).getCopyWritingGifVideo());
                }
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.T5BottomView.BottomListener
            public void light() {
                ((T5HomePresenter) ((BaseActivity) T5HomeActivity.this).mPresenter).switchLightState();
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.T5BottomView.BottomListener
            public void Ai(boolean z) {
                if (z) {
                    return;
                }
                T5HomeActivity t5HomeActivity = T5HomeActivity.this;
                PetkitToast.showTopToast(t5HomeActivity, t5HomeActivity.getResources().getString(R.string.Petkit_ai_tips), 0, 0);
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.T5BottomView.BottomListener
            public void AiVoice() {
                T5HomeActivity t5HomeActivity = T5HomeActivity.this;
                t5HomeActivity.launchActivity(AiVoiceActivity.newIntent(t5HomeActivity, t5HomeActivity.deviceId, T5HomeActivity.this.t5Record.getTypeCode()));
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.T5BottomView.BottomListener
            public void AiPh() {
                T5HomeActivity t5HomeActivity = T5HomeActivity.this;
                t5HomeActivity.launchActivity(AiPhActivity.newIntent(t5HomeActivity, t5HomeActivity.deviceId, T5HomeActivity.this.t5Record.getTypeCode(), T5HomeActivity.this.relatedProductsInfor));
            }
        });
        this.t5BottomView.findViewById(R.id.ll_ai_cat).setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$$ExternalSyntheticLambda46
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initBottomView$5(view);
            }
        });
        this.t5BottomView.findViewById(R.id.ll_ai_add).setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$$ExternalSyntheticLambda47
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initBottomView$6(view);
            }
        });
        this.t5BottomView.showAiEnter(false);
    }

    public /* synthetic */ void lambda$initBottomView$5(View view) {
        launchActivity(PetWeightActivity.newIntent(this, this.deviceId, this.deviceType));
    }

    public /* synthetic */ void lambda$initBottomView$6(View view) {
        AiDataUtil.getInstance().saveClickedTask(this, this.aiNewInfo.getInfo().getId(), this.deviceId, this.deviceType);
        launchActivity(AiActivity.newIntent(this, 0, this.deviceType, this.deviceId, ""));
    }

    public void maintenance() {
        if (!DataHelper.getBooleanSF(this, Consts.T4_MAINTENANCE_MODE_USED_FLAG)) {
            this.resultLauncher.launch(T4MaintenanceModeIntroduceActivity.newIntent(this, false, this.deviceId, 21));
            DataHelper.setBooleanSF(this, Consts.T4_MAINTENANCE_MODE_USED_FLAG, Boolean.TRUE);
        } else {
            showMaintenanceModeWindow();
        }
    }

    public void removeOdor() {
        ((T5HomePresenter) this.mPresenter).beginDeodorant(this.deviceId);
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T5HomeContract.View
    public void showAutoWeight(String str, double d, double d2, double d3) {
        showAutoUpdatePetWeightWindow(str, d, d2, d3);
    }

    private void showAutoUpdatePetWeightWindow(final String str, double d, double d2, double d3) {
        Resources resources;
        int i;
        View view;
        ImageView imageView;
        Button button;
        Button button2;
        String string;
        View viewInflate = LayoutInflater.from(this).inflate(R.layout.pop_toilet_pet_weight_auto_update, (ViewGroup) null);
        ImageView imageView2 = (ImageView) viewInflate.findViewById(R.id.iv_pet_avatar);
        TextView textView = (TextView) viewInflate.findViewById(R.id.tv_content);
        LinearLayout linearLayout = (LinearLayout) viewInflate.findViewById(R.id.ll_prompt);
        final CheckBox checkBox = (CheckBox) viewInflate.findViewById(R.id.cb_todo);
        TextView textView2 = (TextView) viewInflate.findViewById(R.id.tv_prompt);
        Button button3 = (Button) viewInflate.findViewById(R.id.cancel_btn);
        Button button4 = (Button) viewInflate.findViewById(R.id.confirm_btn);
        Pet petById = UserInforUtils.getPetById(str);
        if (UserInforUtils.getCurrentLoginResult().getSettings().getUnit() == 1) {
            resources = getResources();
            i = R.string.Unit_lb;
        } else {
            resources = getResources();
            i = R.string.Unit_kg;
        }
        String string2 = resources.getString(i);
        double dKgToLb = UserInforUtils.getCurrentLoginResult().getSettings().getUnit() == 1 ? CommonUtil.KgToLb(d) : d;
        double dKgToLb2 = UserInforUtils.getCurrentLoginResult().getSettings().getUnit() == 1 ? CommonUtil.KgToLb(d2) : d2;
        if (d3 > 0.0d) {
            Resources resources2 = getResources();
            button2 = button4;
            int i2 = R.string.Auto_update_weight_window_content_two;
            button = button3;
            StringBuilder sb = new StringBuilder();
            view = viewInflate;
            imageView = imageView2;
            sb.append(new Formatter().format("%.2f", Double.valueOf(dKgToLb2)).toString());
            sb.append(string2);
            string = resources2.getString(i2, sb.toString());
        } else {
            view = viewInflate;
            imageView = imageView2;
            button = button3;
            button2 = button4;
            string = getResources().getString(R.string.Auto_update_weight_window_content_three, new Formatter().format("%.2f", Double.valueOf(dKgToLb2)).toString() + string2);
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(getResources().getString(R.string.Auto_update_weight_window_content_one, petById.getName(), dKgToLb + string2));
        sb2.append(string);
        SpannableString spannableString = new SpannableString(sb2.toString());
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.h2_ota_blue)), spannableString.toString().indexOf(petById.getName()), spannableString.toString().indexOf(petById.getName()) + petById.getName().length(), 33);
        textView.setText(spannableString);
        textView2.setText(getResources().getString(R.string.Auto_update_weight_window_prompt));
        linearLayout.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$$ExternalSyntheticLambda49
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                T5HomeActivity.lambda$showAutoUpdatePetWeightWindow$7(checkBox, view2);
            }
        });
        ((BaseApplication) getApplication()).getAppComponent().imageLoader().loadImage(this, GlideImageConfig.builder().url(petById.getAvatar()).imageView(imageView).errorPic(petById.getType().getId() == 1 ? R.drawable.default_header_dog : R.drawable.default_header_cat).transformation(new GlideCircleTransform(this)).build());
        final EasyPopupWindow easyPopupWindow = new EasyPopupWindow(this);
        easyPopupWindow.setOutsideTouchable(false);
        easyPopupWindow.setmShowAlpha(0.5f);
        easyPopupWindow.setContentView(view);
        easyPopupWindow.setHeight(-1);
        easyPopupWindow.setWidth(BaseApplication.displayMetrics.widthPixels);
        button.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$$ExternalSyntheticLambda50
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.lambda$showAutoUpdatePetWeightWindow$8(checkBox, easyPopupWindow, view2);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$$ExternalSyntheticLambda51
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.lambda$showAutoUpdatePetWeightWindow$9(checkBox, str, easyPopupWindow, view2);
            }
        });
        easyPopupWindow.showAtLocation(getWindow().getDecorView(), 17, 0, 0);
    }

    public static /* synthetic */ void lambda$showAutoUpdatePetWeightWindow$7(CheckBox checkBox, View view) {
        checkBox.setChecked(!checkBox.isChecked());
    }

    public /* synthetic */ void lambda$showAutoUpdatePetWeightWindow$8(CheckBox checkBox, EasyPopupWindow easyPopupWindow, View view) {
        if (checkBox.isChecked()) {
            ((T5HomePresenter) this.mPresenter).updateSetting("weightPopup", 0);
        }
        easyPopupWindow.dismiss();
    }

    public /* synthetic */ void lambda$showAutoUpdatePetWeightWindow$9(CheckBox checkBox, String str, EasyPopupWindow easyPopupWindow, View view) {
        if (checkBox.isChecked()) {
            ((T5HomePresenter) this.mPresenter).updateSetting("weightPopup", 0);
        }
        launchActivity(WeightRecordActivity.newIntent(this, str));
        easyPopupWindow.dismiss();
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$11 */
    public class AnonymousClass11 implements NormalCenterTipWindow.OnClickOk {
        @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
        public void onCancelClick() {
        }

        public AnonymousClass11() {
        }

        @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
        public void onOkClick() {
            if (((BaseActivity) T5HomeActivity.this).mPresenter != null) {
                ((T5HomePresenter) ((BaseActivity) T5HomeActivity.this).mPresenter).optionSmooth();
            }
            if (T5HomeActivity.this.smoothWindow != null) {
                T5HomeActivity.this.smoothWindow.dismiss();
            }
        }
    }

    public void showSmoothWindow() {
        NormalCenterTipWindow normalCenterTipWindow = new NormalCenterTipWindow(getApplicationContext(), new NormalCenterTipWindow.OnClickOk() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.11
            @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
            public void onCancelClick() {
            }

            public AnonymousClass11() {
            }

            @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
            public void onOkClick() {
                if (((BaseActivity) T5HomeActivity.this).mPresenter != null) {
                    ((T5HomePresenter) ((BaseActivity) T5HomeActivity.this).mPresenter).optionSmooth();
                }
                if (T5HomeActivity.this.smoothWindow != null) {
                    T5HomeActivity.this.smoothWindow.dismiss();
                }
            }
        }, (String) null, getResources().getString(R.string.T6_level_off_tips));
        this.smoothWindow = normalCenterTipWindow;
        normalCenterTipWindow.setOkText(getString(R.string.Confirm));
        T5BottomView t5BottomView = this.t5BottomView;
        if (t5BottomView != null) {
            t5BottomView.hideTopView();
        }
        this.smoothWindow.show(getWindow().getDecorView());
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$12 */
    public class AnonymousClass12 implements NormalCenterTipWindow.OnClickOk {
        @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
        public void onCancelClick() {
        }

        public AnonymousClass12() {
        }

        @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
        public void onOkClick() {
            if (((BaseActivity) T5HomeActivity.this).mPresenter != null) {
                ((T5HomePresenter) ((BaseActivity) T5HomeActivity.this).mPresenter).optionRecover();
            }
            if (T5HomeActivity.this.recoverWindow != null) {
                T5HomeActivity.this.recoverWindow.dismiss();
            }
        }
    }

    public void showRecoverWindow() {
        NormalCenterTipWindow normalCenterTipWindow = new NormalCenterTipWindow(getApplicationContext(), new NormalCenterTipWindow.OnClickOk() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.12
            @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
            public void onCancelClick() {
            }

            public AnonymousClass12() {
            }

            @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
            public void onOkClick() {
                if (((BaseActivity) T5HomeActivity.this).mPresenter != null) {
                    ((T5HomePresenter) ((BaseActivity) T5HomeActivity.this).mPresenter).optionRecover();
                }
                if (T5HomeActivity.this.recoverWindow != null) {
                    T5HomeActivity.this.recoverWindow.dismiss();
                }
            }
        }, (String) null, getResources().getString(R.string.T6_reset_tips));
        this.recoverWindow = normalCenterTipWindow;
        normalCenterTipWindow.setOkText(getString(R.string.Confirm));
        T5BottomView t5BottomView = this.t5BottomView;
        if (t5BottomView != null) {
            t5BottomView.hideTopView();
        }
        this.recoverWindow.show(getWindow().getDecorView());
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$13 */
    public class AnonymousClass13 implements SpannableStringColorsPicPromptWindow.OnClickListener {
        @Override // com.petkit.android.activities.universalWindow.SpannableStringColorsPicPromptWindow.OnClickListener
        public void onCancel() {
        }

        @Override // com.petkit.android.activities.universalWindow.SpannableStringColorsPicPromptWindow.OnClickListener
        public void onConfirm() {
        }

        public AnonymousClass13() {
        }
    }

    public void showHowlingWindow() {
        SpannableStringColorsPicPromptWindow spannableStringColorsPicPromptWindow = this.window;
        if (spannableStringColorsPicPromptWindow == null || !spannableStringColorsPicPromptWindow.isShowing()) {
            SpannableStringColorsPicPromptWindow spannableStringColorsPicPromptWindow2 = new SpannableStringColorsPicPromptWindow(this, getResources().getString(R.string.Prompt), getResources().getString(R.string.D4sh_tip_prompt_two), getResources().getString(R.string.D4sh_tip_prompt_one), getResources().getString(R.string.Feeder_i_know), R.drawable.t6_howling_tip, new SpannableStringColorsPicPromptWindow.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.13
                @Override // com.petkit.android.activities.universalWindow.SpannableStringColorsPicPromptWindow.OnClickListener
                public void onCancel() {
                }

                @Override // com.petkit.android.activities.universalWindow.SpannableStringColorsPicPromptWindow.OnClickListener
                public void onConfirm() {
                }

                public AnonymousClass13() {
                }
            }, new String[0]);
            this.window = spannableStringColorsPicPromptWindow2;
            spannableStringColorsPicPromptWindow2.show(getWindow().getDecorView());
            DataHelper.setBooleanSF(this, Constants.T6_HOWLING_FLAG + this.t5Record.getDeviceId(), Boolean.TRUE);
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T5HomeContract.View
    public void stopLive() {
        this.videoTagLayout.setVisibility(8);
        T5LiveViewPagerAdapter t5LiveViewPagerAdapter = this.t5LiveViewPagerAdapter;
        if (t5LiveViewPagerAdapter != null) {
            t5LiveViewPagerAdapter.setLive(false);
            if (this.vpLive.getCurrentItem() == 1) {
                this.t5LiveViewPagerAdapter.setCurrentPosition(this.vpLive.getCurrentItem());
                this.t5LiveViewPagerAdapter.refreshData();
            }
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T5HomeContract.View
    public void bindServicePrompt(int i) {
        if (this.t5Record.getServiceStatus() == 1 || this.t5Record.getServiceStatus() == 2) {
            DataHelper.setBooleanSF(this, Constants.T6_BIND_SERVICE_PROMPT + this.deviceId, Boolean.TRUE);
            ((T5HomePresenter) this.mPresenter).checkInitWindow(6);
            return;
        }
        if (i > 0) {
            openBindServiceWindow(getResources().getString(R.string.Bind_cloud_service_prompt));
            return;
        }
        DataHelper.setBooleanSF(this, Constants.T6_BIND_SERVICE_PROMPT + this.deviceId, Boolean.FALSE);
        ((T5HomePresenter) this.mPresenter).checkInitWindow(6);
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T5HomeContract.View
    public void initDeviceDetailFinish() {
        if (TextUtils.isEmpty(this.eventId) || this.eventId.equals("null") || this.eventId.equals("Null") || this.eventId.equals("NULL")) {
            return;
        }
        ((T5HomePresenter) this.mPresenter).getEventMediaInfo(this.deviceId, this.eventId);
        this.eventId = null;
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T5HomeContract.View
    public void redirectToVideoRecordActivity(T6StatisticResInfo t6StatisticResInfo) {
        if (t6StatisticResInfo.getEventType() == 5 && t6StatisticResInfo.getContent() != null && (t6StatisticResInfo.getContent().getResult().intValue() == 1 || t6StatisticResInfo.getContent().getResult().intValue() == 0)) {
            return;
        }
        T6EventInfo t6EventInfo = new T6EventInfo();
        t6EventInfo.setDeviceId(this.deviceId);
        t6EventInfo.setPetId(t6StatisticResInfo.getPetId());
        t6EventInfo.setPreview(t6StatisticResInfo.getPreview());
        t6EventInfo.setMediaApi(t6StatisticResInfo.getMediaApi());
        t6EventInfo.setAesKey(t6StatisticResInfo.getAesKey());
        t6EventInfo.setDuration(t6StatisticResInfo.getDuration());
        t6EventInfo.setExpire(t6StatisticResInfo.getExpire());
        t6EventInfo.setEventId(t6StatisticResInfo.getEventId());
        t6EventInfo.setMark(t6StatisticResInfo.getMark());
        t6EventInfo.setTimestamp(t6StatisticResInfo.getStartTime());
        T6ContentInfo t6ContentInfo = new T6ContentInfo();
        t6ContentInfo.setStartTime(t6StatisticResInfo.getStartTime());
        if (t6StatisticResInfo.getContent() == null) {
            t6ContentInfo.setTimeIn(t6StatisticResInfo.getStartTime());
        } else {
            t6ContentInfo.setTimeIn(t6StatisticResInfo.getContent().getTimeIn());
            t6ContentInfo.setTimeOut(t6StatisticResInfo.getContent().getTimeOut());
            t6ContentInfo.setPetWeight(t6StatisticResInfo.getContent().getPetWeight());
        }
        t6EventInfo.setContent(t6ContentInfo);
        t6EventInfo.setIsNeedUploadVideo(t6StatisticResInfo.getIsNeedUploadVideo());
        t6EventInfo.setStorageSpace(t6StatisticResInfo.getStorageSpace());
        t6EventInfo.setEventType(t6StatisticResInfo.getEventType());
        ArrayList arrayList = new ArrayList();
        arrayList.add(t6EventInfo);
        launchActivity(T6RecordVIdeoPlayActivity.newIntent(this, this.deviceId, this.deviceType, -1, arrayList, t6EventInfo, false, "", false));
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T5HomeContract.View
    public void getAiInfoSuccess(AiInfo aiInfo) {
        this.aiNewInfo = aiInfo;
        if (AiDataUtil.getInstance().isDeviceHaveNewAiActivity(this, aiInfo, this.deviceId, 21) && !FamilyUtils.getInstance().checkIsSharedDevice(this, this.deviceId, this.deviceType) && this.t5Record.getDeviceShared() == null) {
            this.t5BottomView.setAiIconHaveNewMessage(true);
        } else {
            this.t5BottomView.setAiIconHaveNewMessage(false);
        }
        AiDataUtil.getInstance().saveAiData(this, aiInfo, this.deviceId, 21);
        if (aiInfo == null || aiInfo.getInfo() == null || this.t5Record != null) {
            this.t5BottomView.showAiEnter(false);
        } else {
            this.t5BottomView.showAiEnter(false);
        }
        if (this.aiNotification) {
            if (aiInfo != null && (aiInfo.getInfo() == null || (aiInfo.getInfo() != null && aiInfo.getInfo().getId() < 1))) {
                showAiEndActWindow();
            } else {
                new Handler().postDelayed(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$$ExternalSyntheticLambda8
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$getAiInfoSuccess$10();
                    }
                }, 800L);
            }
        }
    }

    public /* synthetic */ void lambda$getAiInfoSuccess$10() {
        if (isFinishing()) {
            return;
        }
        launchActivity(AiActivity.newIntent(this, 0, this.deviceType, this.deviceId, ""));
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T5HomeContract.View
    public void setFoxAnimation(int i) {
        if (this.t5Record.getSettings().getPreLive() == 0 && !this.t5LiveVideoView.isPlayed) {
            i = 3;
        }
        if (i == 0) {
            this.ivFox.setVisibility(0);
            startCleanPrepareAnimation();
            AnonymousClass14 anonymousClass14 = new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.14
                public AnonymousClass14() {
                }

                @Override // java.lang.Runnable
                public void run() {
                    T5HomeActivity.this.startCleanAnimation();
                }
            };
            this.runnable = anonymousClass14;
            this.handler.postDelayed(anonymousClass14, 3000L);
            return;
        }
        if (i == 1) {
            this.ivFox.setVisibility(0);
            this.handler.removeCallbacks(this.runnable);
            startCleanAnimation();
        } else if (i == 2) {
            this.ivFox.setVisibility(0);
            this.handler.removeCallbacks(this.runnable);
            stopFoxAnimation();
        } else {
            if (i != 3) {
                return;
            }
            this.handler.removeCallbacks(this.runnable);
            stopFoxAnimation();
            this.ivFox.setVisibility(4);
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$14 */
    public class AnonymousClass14 implements Runnable {
        public AnonymousClass14() {
        }

        @Override // java.lang.Runnable
        public void run() {
            T5HomeActivity.this.startCleanAnimation();
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T5HomeContract.View
    public void setActionType(int i) {
        this.clickActionType = i;
    }

    private void showAiEndActWindow() {
        NewIKnowWindow newIKnowWindow = new NewIKnowWindow(this, (String) null, getResources().getString(R.string.Co_creation_activity_end), (String) null, (String) null);
        if (newIKnowWindow.isShowing()) {
            return;
        }
        newIKnowWindow.show(getWindow().getDecorView());
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T5HomeContract.View
    public void rtcTokenExpired() {
        Message message = new Message();
        message.what = 22;
        message.arg1 = 0;
        this.handler.sendMessageDelayed(message, 500L);
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T5HomeContract.View
    public void showFreeTrialDialog() {
        this.serviceMaybeChanged = true;
        Intent intent = new Intent(T6Utils.BROADCAST_T5_STATE_MSG);
        intent.putExtra(Constants.EXTRA_DEVICE_ID, this.deviceId);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        startActivity(CloudServiceFreeTrialActivity.newIntent(this, this.deviceId, this.deviceType, this.d4shBannerData.getFreeActivity().getId()));
    }

    public void openBindServiceWindow(String str) {
        SpannableString spannableString = new SpannableString(str);
        NormalCenterTipWindow normalCenterTipWindow = this.bindServiceWindow;
        if (normalCenterTipWindow == null || !normalCenterTipWindow.isShowing()) {
            NormalCenterTipWindow normalCenterTipWindow2 = new NormalCenterTipWindow(this, new NormalCenterTipWindow.OnClickOk() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.15
                public AnonymousClass15() {
                }

                @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
                public void onOkClick() {
                    DataHelper.setBooleanSF(T5HomeActivity.this, Constants.T6_BIND_SERVICE_PROMPT + T5HomeActivity.this.deviceId, Boolean.FALSE);
                    T5HomeActivity.this.launchActivity(new Intent(T5HomeActivity.this, (Class<?>) ServiceManagementActivity.class));
                }

                @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
                public void onCancelClick() {
                    DataHelper.setBooleanSF(T5HomeActivity.this, Constants.T6_BIND_SERVICE_PROMPT + T5HomeActivity.this.deviceId, Boolean.FALSE);
                }
            }, (String) null, spannableString);
            this.bindServiceWindow = normalCenterTipWindow2;
            ((TextView) normalCenterTipWindow2.getContentView().findViewById(R.id.tv_cancel)).setText(getString(R.string.Cancel));
            ((TextView) this.bindServiceWindow.getContentView().findViewById(R.id.tv_ok)).setText(getString(R.string.Decorrelation));
            this.bindServiceWindow.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$$ExternalSyntheticLambda22
                @Override // android.widget.PopupWindow.OnDismissListener
                public final void onDismiss() {
                    this.f$0.lambda$openBindServiceWindow$11();
                }
            });
            this.bindServiceWindow.show(getWindow().getDecorView());
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$15 */
    public class AnonymousClass15 implements NormalCenterTipWindow.OnClickOk {
        public AnonymousClass15() {
        }

        @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
        public void onOkClick() {
            DataHelper.setBooleanSF(T5HomeActivity.this, Constants.T6_BIND_SERVICE_PROMPT + T5HomeActivity.this.deviceId, Boolean.FALSE);
            T5HomeActivity.this.launchActivity(new Intent(T5HomeActivity.this, (Class<?>) ServiceManagementActivity.class));
        }

        @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
        public void onCancelClick() {
            DataHelper.setBooleanSF(T5HomeActivity.this, Constants.T6_BIND_SERVICE_PROMPT + T5HomeActivity.this.deviceId, Boolean.FALSE);
        }
    }

    public /* synthetic */ void lambda$openBindServiceWindow$11() {
        ((T5HomePresenter) this.mPresenter).checkInitWindow(6);
    }

    public void showCleanUpWindow() {
        View viewInflate = LayoutInflater.from(this).inflate(R.layout.pop_t3_clean_layout, (ViewGroup) null);
        final EasyPopupWindow easyPopupWindow = new EasyPopupWindow(this);
        easyPopupWindow.setOutsideTouchable(false);
        TextView textView = (TextView) viewInflate.findViewById(R.id.tv_cancel);
        textView.setTextColor(getResources().getColor(R.color.new_bind_blue));
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$$ExternalSyntheticLambda20
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                easyPopupWindow.dismiss();
            }
        });
        TextView textView2 = (TextView) viewInflate.findViewById(R.id.tv_confirm);
        textView2.setTextColor(getResources().getColor(R.color.new_bind_blue));
        textView2.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$$ExternalSyntheticLambda21
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$showCleanUpWindow$13(easyPopupWindow, view);
            }
        });
        TextView textView3 = (TextView) viewInflate.findViewById(R.id.tv_content);
        if (this.t5Record.getSettings().getKitten() == 1) {
            textView3.setText(getString(R.string.Kitten_clean_prompt));
        } else {
            textView3.setText(getString(R.string.Clean_litter_prompt));
        }
        easyPopupWindow.setContentView(viewInflate);
        easyPopupWindow.setHeight(-1);
        easyPopupWindow.setWidth((int) (DeviceUtils.getDisplayMetrics(this).widthPixels * 0.8f));
        T5BottomView t5BottomView = this.t5BottomView;
        if (t5BottomView != null) {
            t5BottomView.hideTopView();
        }
        easyPopupWindow.showAtLocation(getWindow().getDecorView(), 17, 0, 0);
    }

    public /* synthetic */ void lambda$showCleanUpWindow$13(EasyPopupWindow easyPopupWindow, View view) {
        easyPopupWindow.dismiss();
        ((T5HomePresenter) this.mPresenter).cleanup(this.deviceId);
    }

    private void refreshCameraStatus(T6Record t6Record) {
        PetkitLog.d("refreshCameraStatus:" + t6Record.getSettings().getCamera() + " status:" + t6Record.getState().getCameraStatus());
        if (t6Record.getSettings().getCamera() == 0) {
            setFoxAnimation(3);
            this.ivMuteIcon.setVisibility(8);
        } else if (t6Record.getState().getCameraStatus() == 0) {
            setFoxAnimation(3);
            this.ivMuteIcon.setVisibility(8);
        } else {
            this.ivMuteIcon.setVisibility(0);
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        this.clickIntoFullscreen = false;
        if (this.refreshSd) {
            startActivity(T6LiveFullscreenActivity.newIntent(this, this.deviceId, this.deviceType, true));
            overridePendingTransition(0, 0);
            return;
        }
        ((T5HomePresenter) this.mPresenter).onResume(this.t5LiveVideoView.getVideoPlayerView());
        if (AiDataUtil.getInstance().getIsDeviceNeedShowRedPoint(this, this.deviceId, 21) && !FamilyUtils.getInstance().checkIsSharedDevice(this, this.deviceId, this.deviceType) && this.t5Record.getDeviceShared() == null) {
            this.t5BottomView.setAiIconHaveNewMessage(true);
        } else {
            this.t5BottomView.setAiIconHaveNewMessage(false);
        }
        if (this.serviceMaybeChanged) {
            ((T5HomePresenter) this.mPresenter).initT5DeviceDetail(false);
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T5HomeContract.View
    public void handleWidget() {
        WidgetDataInfo widgetDataInfo = this.widgetDataInfo;
        if (widgetDataInfo != null) {
            if (widgetDataInfo.getClear() == 1) {
                showCleanUpWindow();
            } else if (this.widgetDataInfo.getCameraSwitch() == 1) {
                launchActivity(T6SettingActivity.newIntent(this, this.deviceId, this.deviceType, this.relatedProductsInfor, true));
            }
            this.widgetDataInfo = null;
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        if (this.t5Record == null || CommonUtil.getLong(r0.getFirmware()) >= 600.0d || this.clickIntoFullscreen) {
            return;
        }
        ((T5HomePresenter) this.mPresenter).stopLiveVideo();
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T5HomeContract.View
    public void passRelatedProductsInfor(RelatedProductsInfor relatedProductsInfor) {
        this.relatedProductsInfor = relatedProductsInfor;
        ArrayList arrayList = new ArrayList();
        if (relatedProductsInfor != null && relatedProductsInfor.getPromote() != null) {
            arrayList.addAll(relatedProductsInfor.getPromote());
        }
        boolean z = arrayList.size() > 0;
        this.showPromoteView = z;
        if (!z) {
            this.promoteView.setVisibility(8);
            return;
        }
        this.promoteView.enableSwitchable("T5");
        this.promoteView.refreshPromoteData(arrayList);
        this.promoteView.setPromoteViewOnItemListener(new PromoteView.PromoteViewOnItemListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$$ExternalSyntheticLambda23
            @Override // com.petkit.android.activities.mall.widget.PromoteView.PromoteViewOnItemListener
            public final void onItemClick(PromoteData promoteData) {
                this.f$0.lambda$passRelatedProductsInfor$14(promoteData);
            }
        });
        this.promoteView.setVisibility(0);
    }

    public /* synthetic */ void lambda$passRelatedProductsInfor$14(PromoteData promoteData) {
        new HashMap().put("contentId", "" + promoteData.getContentId());
        MallUtils.goToWebOrProductDetail(this, promoteData.getShareUrl(), promoteData.getUrlType());
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T5HomeContract.View
    public void getFixTimeSettingSuccess(List<TimingResult> list) {
        launchActivity(T6RegularCleanSettingActivity.newIntent(this, this.deviceId, this.deviceType, (ArrayList) list));
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T5HomeContract.View
    public void onFirstRemoteVideoFrame() {
        PetkitLog.d("onFirstRemoteVideoFrame");
        this.firstRemoteVideoFrame = true;
        runOnUiThread(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$$ExternalSyntheticLambda52
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onFirstRemoteVideoFrame$15();
            }
        });
    }

    public /* synthetic */ void lambda$onFirstRemoteVideoFrame$15() {
        if (this.handler == null) {
            return;
        }
        if (this.t5Record.getSettings().getCamera() == 0 || this.t5Record.getState().getCameraStatus() == 0) {
            this.videoTagLayout.setVisibility(8);
        } else {
            this.videoTagLayout.setVisibility(0);
        }
        T5LiveViewPagerAdapter t5LiveViewPagerAdapter = this.t5LiveViewPagerAdapter;
        if (t5LiveViewPagerAdapter != null) {
            t5LiveViewPagerAdapter.setLive(true);
            ViewPager viewPager = this.vpLive;
            if (viewPager != null && viewPager.getCurrentItem() == 1) {
                this.t5LiveViewPagerAdapter.setCurrentPosition(this.vpLive.getCurrentItem());
                this.t5LiveViewPagerAdapter.refreshData();
            }
        }
        endLoadingAnim();
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T5HomeContract.View
    public void updateEvent(int i) {
        if (i == 31 || i == 32 || i == 33 || i == 35 || i == 38) {
            if (DateUtil.isSameDay(this.calendarTime, this.t5Record.getActualTimeZone())) {
                int i2 = this.currentPosition;
                if (i2 == 0) {
                    if (this.rlTopTab.getVisibility() != 0) {
                        if (this.fragmentAll.isAdded()) {
                            this.fragmentAll.refreshEvent(this.calendarTime);
                        }
                    } else {
                        this.ivSmallAll.setVisibility(0);
                        this.ivSmallAll2.setVisibility(0);
                    }
                } else if (i2 == 1) {
                    this.ivSmallAll.setVisibility(0);
                    this.ivSmallAll2.setVisibility(0);
                    if ((i == 31 || i == 32) && this.rlTopTab.getVisibility() != 0 && this.fragmentToilet.isAdded()) {
                        this.fragmentToilet.refreshEvent(this.calendarTime);
                    }
                } else if (i2 == 2) {
                    this.ivSmallAll.setVisibility(0);
                    this.ivSmallAll2.setVisibility(0);
                    if ((i == 33 || i == 35) && this.rlTopTab.getVisibility() != 0 && this.fragmentPet.isAdded()) {
                        this.fragmentPet.refreshEvent(this.calendarTime);
                    }
                } else if (i2 == 3) {
                    this.ivSmallAll.setVisibility(0);
                    this.ivSmallAll2.setVisibility(0);
                    if (i == 38 && this.rlTopTab.getVisibility() != 0 && this.toiletErrorFragment.isAdded()) {
                        this.toiletErrorFragment.refreshEvent(this.calendarTime);
                    }
                }
                if (i == 31) {
                    this.newToiletEventNum++;
                } else if (i == 32) {
                    this.newClearEventNum++;
                } else if (i == 38) {
                    this.newToiletErrorEventNum++;
                    showRedToilet(true);
                } else {
                    this.newPetEventNum++;
                }
            }
            if (this.rlTopTab.getVisibility() == 0) {
                if (i == 33) {
                    this.tvTopPaoPao.setText(getResources().getString(R.string.T6_pet_event_coming));
                    showTopPaoPao();
                    return;
                } else {
                    if (i != 35) {
                        return;
                    }
                    this.tvTopPaoPao.setText(getResources().getString(R.string.T6_pet_detect_event_coming));
                    showTopPaoPao();
                    return;
                }
            }
            return;
        }
        if (i == 36 && this.rlTopTab.getVisibility() == 0) {
            this.tvTopPaoPao.setText(getResources().getString(R.string.T6_toilet_event_coming));
            showTopPaoPao();
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T5HomeContract.View
    public void showGuideView() {
        showLiveGuide();
    }

    private void showLiveGuide() {
        int iDip2px;
        if (this.liveGuide != null) {
            return;
        }
        GuideBuilder guideBuilder = new GuideBuilder();
        guideBuilder.setTargetView(findViewById(R.id.video_player_view)).setAlpha(180).setHighTargetCorner(600).setHighTargetPaddingLeft(ArmsUtils.dip2px(this, 0.0f)).setHighTargetPaddingRight(ArmsUtils.dip2px(this, 0.0f)).setHighTargetPaddingBottom(ArmsUtils.dip2px(this, 0.0f)).setHighTargetPaddingTop(ArmsUtils.dip2px(this, 0.0f)).setOutsideTouchable(false).setAutoDismiss(true);
        guideBuilder.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.16
            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onShown() {
            }

            public AnonymousClass16() {
            }

            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onDismiss() {
                T5HomeActivity.this.showFirstGuide();
            }
        });
        if ("zh_CN".equals(UserInforUtils.getLanguageLocaleString(CommonUtils.getAppContext()))) {
            iDip2px = ArmsUtils.dip2px(this, 20.0f);
        } else {
            iDip2px = ArmsUtils.dip2px(this, 20.0f);
        }
        guideBuilder.addComponent(new GuideD4shBottomRightTipAndBtnAndLineComponent(new GuideParam(getResources().getString(R.string.T6_home_live_guide_tips), "1/4", 4, 48, iDip2px, ArmsUtils.dip2px(this, -40.0f), getResources().getString(R.string.Next_tip), R.layout.layout_t6_live_guide), new ConfirmListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.17
            public AnonymousClass17() {
            }

            @Override // com.petkit.android.activities.petkitBleDevice.w5.guide.ConfirmListener
            public void onConfirmListener() {
                Guide guide = T5HomeActivity.this.liveGuide;
                if (guide != null) {
                    guide.dismiss();
                }
            }
        }));
        Guide guideCreateGuide = guideBuilder.createGuide();
        this.liveGuide = guideCreateGuide;
        guideCreateGuide.show(this);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$16 */
    public class AnonymousClass16 implements GuideBuilder.OnVisibilityChangedListener {
        @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
        public void onShown() {
        }

        public AnonymousClass16() {
        }

        @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
        public void onDismiss() {
            T5HomeActivity.this.showFirstGuide();
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$17 */
    public class AnonymousClass17 implements ConfirmListener {
        public AnonymousClass17() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.w5.guide.ConfirmListener
        public void onConfirmListener() {
            Guide guide = T5HomeActivity.this.liveGuide;
            if (guide != null) {
                guide.dismiss();
            }
        }
    }

    public void showFirstGuide() {
        int iDip2px;
        if (this.firstGuide != null) {
            return;
        }
        GuideBuilder guideBuilder = new GuideBuilder();
        guideBuilder.setTargetView(this.liveIndicator).setAlpha(180).setHighTargetCorner(50).setHighTargetPaddingLeft(ArmsUtils.dip2px(this, 5.0f)).setHighTargetPaddingRight(ArmsUtils.dip2px(this, -5.0f)).setHighTargetPaddingBottom(ArmsUtils.dip2px(this, 0.0f)).setHighTargetPaddingTop(ArmsUtils.dip2px(this, 0.0f)).setOutsideTouchable(false).setAutoDismiss(true);
        guideBuilder.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.18
            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onShown() {
            }

            public AnonymousClass18() {
            }

            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onDismiss() {
                int[] iArr = new int[2];
                DisplayMetrics displayMetrics = new DisplayMetrics();
                T5HomeActivity.this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int i = displayMetrics.heightPixels;
                T5HomeActivity.this.ivChartShow.getLocationOnScreen(iArr);
                int iDip2px2 = (iArr[1] - i) + ArmsUtils.dip2px(T5HomeActivity.this, 70.0f);
                AppBarLayout appBarLayout = T5HomeActivity.this.appBar;
                if (iDip2px2 < 0) {
                    iDip2px2 = 0;
                }
                appBarLayout.scrollTo(0, iDip2px2);
                T5HomeActivity.this.showSecondGuide();
            }
        });
        if ("zh_CN".equals(UserInforUtils.getLanguageLocaleString(CommonUtils.getAppContext()))) {
            iDip2px = ArmsUtils.dip2px(this, 20.0f);
        } else {
            iDip2px = ArmsUtils.dip2px(this, 20.0f);
        }
        guideBuilder.addComponent(new GuideTipAndBtnAndLineComponent(new GuideParam(getResources().getString(R.string.T6_home_guide_tips1), "2/4", 2, 48, iDip2px, 0, getResources().getString(R.string.Next_tip), R.layout.layout_t6_top_guide), new ConfirmListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.19
            public AnonymousClass19() {
            }

            @Override // com.petkit.android.activities.petkitBleDevice.w5.guide.ConfirmListener
            public void onConfirmListener() {
                Guide guide = T5HomeActivity.this.firstGuide;
                if (guide != null) {
                    guide.dismiss();
                }
            }
        }));
        Guide guideCreateGuide = guideBuilder.createGuide();
        this.firstGuide = guideCreateGuide;
        guideCreateGuide.show(this);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$18 */
    public class AnonymousClass18 implements GuideBuilder.OnVisibilityChangedListener {
        @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
        public void onShown() {
        }

        public AnonymousClass18() {
        }

        @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
        public void onDismiss() {
            int[] iArr = new int[2];
            DisplayMetrics displayMetrics = new DisplayMetrics();
            T5HomeActivity.this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int i = displayMetrics.heightPixels;
            T5HomeActivity.this.ivChartShow.getLocationOnScreen(iArr);
            int iDip2px2 = (iArr[1] - i) + ArmsUtils.dip2px(T5HomeActivity.this, 70.0f);
            AppBarLayout appBarLayout = T5HomeActivity.this.appBar;
            if (iDip2px2 < 0) {
                iDip2px2 = 0;
            }
            appBarLayout.scrollTo(0, iDip2px2);
            T5HomeActivity.this.showSecondGuide();
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$19 */
    public class AnonymousClass19 implements ConfirmListener {
        public AnonymousClass19() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.w5.guide.ConfirmListener
        public void onConfirmListener() {
            Guide guide = T5HomeActivity.this.firstGuide;
            if (guide != null) {
                guide.dismiss();
            }
        }
    }

    public void showSecondGuide() {
        int iDip2px;
        int i;
        if (this.secondGuide != null) {
            return;
        }
        GuideBuilder guideBuilder = new GuideBuilder();
        guideBuilder.setTargetView(this.ivTodayEvent).setAlpha(180).setHighTargetCorner(50).setHighTargetPaddingLeft(ArmsUtils.dip2px(this, 0.0f)).setHighTargetPaddingRight(ArmsUtils.dip2px(this, 0.0f)).setHighTargetPaddingBottom(ArmsUtils.dip2px(this, 0.0f)).setHighTargetPaddingTop(ArmsUtils.dip2px(this, 0.0f)).setOutsideTouchable(false).setAutoDismiss(true);
        guideBuilder.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.20
            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onShown() {
            }

            public AnonymousClass20() {
            }

            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onDismiss() {
                T5HomeActivity.this.appBar.scrollTo(0, 0);
                T5HomeActivity.this.showThirdGuide();
            }
        });
        if ("zh_CN".equals(UserInforUtils.getLanguageLocaleString(CommonUtils.getAppContext()))) {
            iDip2px = ArmsUtils.dip2px(this, 30.0f);
            i = 32;
        } else {
            iDip2px = ArmsUtils.dip2px(this, 90.0f);
            i = 48;
        }
        guideBuilder.addComponent(new GuideD4shBottomRightTipAndBtnAndLineComponent(new GuideParam(getResources().getString(R.string.T6_home_guide_tips2), "3/4", 4, i, iDip2px, 0, getResources().getString(R.string.Next_tip), R.layout.layout_d4sh_guide_bottom_right), new ConfirmListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.21
            public AnonymousClass21() {
            }

            @Override // com.petkit.android.activities.petkitBleDevice.w5.guide.ConfirmListener
            public void onConfirmListener() {
                Guide guide = T5HomeActivity.this.secondGuide;
                if (guide != null) {
                    guide.dismiss();
                }
            }
        }));
        Guide guideCreateGuide = guideBuilder.createGuide();
        this.secondGuide = guideCreateGuide;
        guideCreateGuide.show(this);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$20 */
    public class AnonymousClass20 implements GuideBuilder.OnVisibilityChangedListener {
        @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
        public void onShown() {
        }

        public AnonymousClass20() {
        }

        @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
        public void onDismiss() {
            T5HomeActivity.this.appBar.scrollTo(0, 0);
            T5HomeActivity.this.showThirdGuide();
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$21 */
    public class AnonymousClass21 implements ConfirmListener {
        public AnonymousClass21() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.w5.guide.ConfirmListener
        public void onConfirmListener() {
            Guide guide = T5HomeActivity.this.secondGuide;
            if (guide != null) {
                guide.dismiss();
            }
        }
    }

    public void showThirdGuide() {
        if (this.thirdGuide != null) {
            return;
        }
        GuideBuilder guideBuilder = new GuideBuilder();
        guideBuilder.setTargetView(this.t5BottomView.findViewById(R.id.ll_more)).setAlpha(180).setHighTargetCorner(50).setHighTargetPaddingLeft(ArmsUtils.dip2px(this, 3.0f)).setHighTargetPaddingRight(ArmsUtils.dip2px(this, 3.0f)).setHighTargetPaddingBottom(ArmsUtils.dip2px(this, 3.0f)).setHighTargetPaddingTop(ArmsUtils.dip2px(this, 3.0f)).setOutsideTouchable(false).setAutoDismiss(true);
        guideBuilder.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.22
            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onShown() {
            }

            public AnonymousClass22() {
            }

            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onDismiss() {
                DataHelper.setBooleanSF(T5HomeActivity.this.getApplicationContext(), Constants.T5_HOME_GUIDE, Boolean.TRUE);
            }
        });
        String stringSF = DataHelper.getStringSF(this, Consts.SHARED_SETTING_LANGUAGE);
        if (TextUtils.isEmpty(stringSF)) {
            stringSF = "zh_CN";
        }
        stringSF.equals("zh_CN");
        guideBuilder.addComponent(new GuideTipAndBtnAndLineComponent(new GuideParam(getResources().getString(R.string.T6_home_guide_tips3), "4/4", 2, 48, ArmsUtils.dip2px(this, 0.0f), 0, getResources().getString(R.string.Know), R.layout.layout_t6_top_guide), new ConfirmListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.23
            public AnonymousClass23() {
            }

            @Override // com.petkit.android.activities.petkitBleDevice.w5.guide.ConfirmListener
            public void onConfirmListener() {
                Guide guide = T5HomeActivity.this.thirdGuide;
                if (guide != null) {
                    guide.dismiss();
                }
            }
        }));
        Guide guideCreateGuide = guideBuilder.createGuide();
        this.thirdGuide = guideCreateGuide;
        guideCreateGuide.show(this);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$22 */
    public class AnonymousClass22 implements GuideBuilder.OnVisibilityChangedListener {
        @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
        public void onShown() {
        }

        public AnonymousClass22() {
        }

        @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
        public void onDismiss() {
            DataHelper.setBooleanSF(T5HomeActivity.this.getApplicationContext(), Constants.T5_HOME_GUIDE, Boolean.TRUE);
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$23 */
    public class AnonymousClass23 implements ConfirmListener {
        public AnonymousClass23() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.w5.guide.ConfirmListener
        public void onConfirmListener() {
            Guide guide = T5HomeActivity.this.thirdGuide;
            if (guide != null) {
                guide.dismiss();
            }
        }
    }

    public void hideAllRedPoint() {
        this.ivSmallAll.setVisibility(8);
        this.ivSmallAll2.setVisibility(8);
    }

    private void showTopPaoPao() {
        this.tvTopPaoPao.setVisibility(0);
        Disposable disposable = this.paoPaoTimer;
        if (disposable != null) {
            disposable.dispose();
            this.paoPaoTimer = null;
        }
        this.paoPaoTimer = Observable.timer(3L, TimeUnit.SECONDS).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.lambda$showTopPaoPao$16((Long) obj);
            }
        });
    }

    public /* synthetic */ void lambda$showTopPaoPao$16(Long l) throws Exception {
        MyHandler myHandler = this.handler;
        if (myHandler != null) {
            myHandler.sendEmptyMessage(10);
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() == R.id.device_work_state_text) {
            return;
        }
        if (view.getId() == R.id.tv_pet_weight) {
            startActivity(WeightStatisticsActivity.newIntent(this, this.selectedPetIds.get(0)));
            return;
        }
        if (view.getId() == R.id.btn_know_more_one || view.getId() == R.id.btn_know_more_two) {
            launchActivity(T6ConsumablesActivity.newIntent(this, this.deviceId, 21, 3, this.relatedProductsInfor));
            return;
        }
        if (view.getId() == R.id.btn_ignore_one || view.getId() == R.id.btn_ignore_two) {
            ((T5HomePresenter) this.mPresenter).ignoreRemainingBags();
            refreshDeviceStateView();
            return;
        }
        if (view.getId() == R.id.rl_device_error_two) {
            List<DeviceError> listCalcDeviceError = ((T5HomePresenter) this.mPresenter).calcDeviceError();
            if (listCalcDeviceError == null || listCalcDeviceError.size() <= 1) {
                return;
            }
            launchActivity(T6WarnsListActivity.newIntent(this, this.deviceId, this.deviceType, (ArrayList) listCalcDeviceError, this.relatedProductsInfor));
            return;
        }
        if (view.getId() == R.id.rl_device_error_one) {
            List<DeviceError> listCalcDeviceError2 = ((T5HomePresenter) this.mPresenter).calcDeviceError();
            if (listCalcDeviceError2 == null || listCalcDeviceError2.size() <= 0) {
                return;
            }
            showErrorWindow(listCalcDeviceError2.get(0));
            return;
        }
        if (view.getId() == R.id.btn_stop) {
            ((T5HomePresenter) this.mPresenter).terminateClean(this.deviceId);
            return;
        }
        if (view.getId() == R.id.btn_continue) {
            ((T5HomePresenter) this.mPresenter).continueToClean(this.deviceId);
            return;
        }
        if (view.getId() == R.id.btn_pause) {
            ((T5HomePresenter) this.mPresenter).pauseClean(this.deviceId);
            return;
        }
        if (view.getId() == R.id.tv_more_vlog) {
            launchActivity(T6VlogActivity.newIntent(this, this.deviceId, this.deviceType, 0));
            return;
        }
        if (view.getId() == R.id.tv_title_status) {
            return;
        }
        if (view.getId() == R.id.ib_setting) {
            startActivity(T6SettingActivity.newIntent(this, this.deviceId, this.deviceType, null));
            return;
        }
        if (view.getId() == R.id.ib_back) {
            killMyself();
            return;
        }
        if (view.getId() == R.id.iv_today_event || view.getId() == R.id.iv_today_event2) {
            ((T5HomePresenter) this.mPresenter).getCalendarData(this.calendarTime);
            return;
        }
        if (view.getId() == R.id.iv_chart_show) {
            if (this.llDay.getVisibility() == 0) {
                this.llDay.setVisibility(8);
                ((ImageView) view).setImageResource(R.drawable.icon_t6_chart_gray);
                return;
            } else {
                this.llDay.setVisibility(0);
                ((ImageView) view).setImageResource(R.drawable.icon_t6_chart);
                return;
            }
        }
        if (view.getId() == R.id.rl_pop_content) {
            return;
        }
        if (view.getId() == R.id.iv_last_event) {
            int i = this.popCount;
            if (i > 0) {
                int i2 = i - 1;
                this.popCount = i2;
                this.sortEventList.get(i2).getClickView().performClick();
                return;
            }
            return;
        }
        if (view.getId() == R.id.iv_next_event) {
            if (this.popCount < this.sortEventList.size() - 1) {
                int i3 = this.popCount + 1;
                this.popCount = i3;
                this.sortEventList.get(i3).getClickView().performClick();
                return;
            }
            return;
        }
        if (view.getId() == R.id.tv_t5_tab_all) {
            clickAllTab(false);
            return;
        }
        if (view.getId() == R.id.tv_t5_tab_all2) {
            clickAllTab(true);
            return;
        }
        if (view.getId() == R.id.tv_t5_tab_toilet) {
            clickToiletTab(false);
            return;
        }
        if (view.getId() == R.id.tv_tab_toilet2) {
            clickToiletTab(true);
            return;
        }
        if (view.getId() == R.id.tv_t5_tab_other) {
            clickPetTab(false);
            return;
        }
        if (view.getId() == R.id.tv_tab_other2) {
            clickPetTab(true);
            return;
        }
        if (view.getId() == R.id.tv_tab_toilet_error) {
            clickToiletErrorTab(false);
            return;
        }
        if (view.getId() == R.id.tv_tab_toilet_error2) {
            clickToiletErrorTab(true);
            return;
        }
        if (view.getId() == R.id.ll_pets) {
            T6Record t6Record = this.t5Record;
            if (t6Record == null || t6Record.getDeviceShared() == null) {
                showSelectPetsDialog();
                return;
            }
            return;
        }
        if (view.getId() == R.id.rl_device_state) {
            if (this.t5Record.getState().getOverall() == 2) {
                showOfflineWindow();
                return;
            } else {
                if (this.t5Record.getState().getOta() == 1) {
                    launchActivity(T6SettingOtaActivity.newIntent(this, this.deviceId, this.deviceType));
                    return;
                }
                return;
            }
        }
        if (view.getId() == R.id.rl_work_state) {
            return;
        }
        if (view.getId() == R.id.tv_top_pao_pao) {
            scrollToTop();
            if (DateUtil.isSameDay(this.calendarTime, this.t5Record.getActualTimeZone())) {
                refreshHeader();
                return;
            }
            return;
        }
        if (view.getId() == R.id.ll_today_times_prompt || view.getId() == R.id.ll_today_avg_duration_prompt) {
            if (this.t5Record.getDeviceShared() == null) {
                launchActivity(ToiletStatisticsActivity.newIntent(this));
                return;
            }
            return;
        }
        if (view.getId() == R.id.btn_maintenance_mode_terminate_clean) {
            P p = this.mPresenter;
            if (p != 0) {
                ((T5HomePresenter) p).endMaintenance(this.deviceId);
                return;
            }
            return;
        }
        if (view.getId() == R.id.btn_maintenance_mode_pause_clean) {
            P p2 = this.mPresenter;
            if (p2 != 0) {
                ((T5HomePresenter) p2).stopMaintenance(this.deviceId);
                return;
            }
            return;
        }
        if (view.getId() == R.id.rl_purchase_eligibility) {
            showPurchaseEligibility();
            return;
        }
        if (view.getId() == R.id.btn_maintenance_mode_continue_to_run) {
            P p3 = this.mPresenter;
            if (p3 != 0) {
                ((T5HomePresenter) p3).reEndMaintenance(this.deviceId);
                return;
            }
            return;
        }
        if (view.getId() == R.id.iv_maintenance_mode_doubt) {
            launchActivity(T4MaintenanceModeIntroduceActivity.newIntent(this, true, this.deviceId, 21));
        }
    }

    private void clickAllTab(boolean z) {
        if (DateUtil.isSameDay(this.calendarTime, this.t5Record.getActualTimeZone())) {
            if (this.ivSmallAll.getVisibility() == 0 || this.ivSmallAll2.getVisibility() == 0) {
                int i = this.newToiletEventNum;
                if ((i > 0 || this.newClearEventNum > 0) && this.newPetEventNum > 0) {
                    P p = this.mPresenter;
                    if (p != 0) {
                        ((T5HomePresenter) p).getStatistic(this.calendarTime, 0);
                    }
                } else if (i > 0 || this.newClearEventNum > 0) {
                    if (i > 0) {
                        P p2 = this.mPresenter;
                        if (p2 != 0) {
                            ((T5HomePresenter) p2).getStatistic(this.calendarTime, 1);
                        }
                    } else {
                        if (this.fragmentAll.isAdded()) {
                            this.fragmentAll.refreshEvent(this.calendarTime);
                        }
                        if (this.fragmentToilet.isAdded()) {
                            this.fragmentToilet.refreshEvent(this.calendarTime);
                        }
                        P p3 = this.mPresenter;
                        if (p3 != 0) {
                            ((T5HomePresenter) p3).getListNum(this.calendarTime);
                        }
                    }
                }
                if (this.currentPosition != 0) {
                    if (!z && this.fragmentAll.isAdded() && this.rlTopTab.getVisibility() != 0) {
                        this.fragmentAll.scrollToTop();
                    }
                    selectedFragment(0);
                }
                hideAllRedPoint();
                return;
            }
            if (this.currentPosition == 0) {
                return;
            }
            if (!z && this.fragmentAll.isAdded() && this.rlTopTab.getVisibility() != 0) {
                this.fragmentAll.scrollToTop();
            }
            selectedFragment(0);
            return;
        }
        if (this.currentPosition == 0) {
            return;
        }
        if (!z && this.fragmentAll.isAdded() && this.rlTopTab.getVisibility() != 0) {
            this.fragmentAll.scrollToTop();
        }
        selectedFragment(0);
    }

    private void clickToiletTab(boolean z) {
        if (DateUtil.isSameDay(this.calendarTime, this.t5Record.getActualTimeZone())) {
            int i = this.newToiletEventNum;
            if (i > 0 || this.newClearEventNum > 0) {
                if (i > 0) {
                    P p = this.mPresenter;
                    if (p != 0) {
                        ((T5HomePresenter) p).getStatistic(this.calendarTime, 2);
                    }
                } else {
                    if (this.fragmentToilet.isAdded()) {
                        this.fragmentToilet.refreshEvent(this.calendarTime);
                    }
                    P p2 = this.mPresenter;
                    if (p2 != 0) {
                        ((T5HomePresenter) p2).getListNum(this.calendarTime);
                    }
                }
                if (this.currentPosition != 1) {
                    if (!z && this.fragmentToilet.isAdded() && this.rlTopTab.getVisibility() != 0) {
                        this.fragmentToilet.scrollToTop();
                    }
                    selectedFragment(1);
                    return;
                }
                return;
            }
            if (this.currentPosition == 1) {
                return;
            }
            if (!z && this.fragmentToilet.isAdded() && this.rlTopTab.getVisibility() != 0) {
                this.fragmentToilet.scrollToTop();
            }
            selectedFragment(1);
            return;
        }
        if (this.currentPosition == 1) {
            return;
        }
        if (!z && this.fragmentToilet.isAdded() && this.rlTopTab.getVisibility() != 0) {
            this.fragmentToilet.scrollToTop();
        }
        selectedFragment(1);
    }

    private void clickPetTab(boolean z) {
        if (DateUtil.isSameDay(this.calendarTime, this.t5Record.getActualTimeZone())) {
            if (this.newPetEventNum > 0) {
                if (this.fragmentPet.isAdded()) {
                    this.fragmentPet.refreshEvent(this.calendarTime);
                }
                P p = this.mPresenter;
                if (p != 0) {
                    ((T5HomePresenter) p).getListNum(this.calendarTime);
                }
                if (this.currentPosition != 2) {
                    if (!z && this.fragmentPet.isAdded() && this.rlTopTab.getVisibility() != 0) {
                        this.fragmentPet.scrollToTop();
                    }
                    selectedFragment(2);
                    return;
                }
                return;
            }
            if (this.currentPosition == 2) {
                return;
            }
            if (!z && this.fragmentPet.isAdded() && this.rlTopTab.getVisibility() != 0) {
                this.fragmentPet.scrollToTop();
            }
            selectedFragment(2);
            return;
        }
        if (this.currentPosition == 2) {
            return;
        }
        if (!z && this.fragmentPet.isAdded() && this.rlTopTab.getVisibility() != 0) {
            this.fragmentPet.scrollToTop();
        }
        selectedFragment(2);
    }

    private void clickToiletErrorTab(boolean z) {
        if (DateUtil.isSameDay(this.calendarTime, this.t5Record.getActualTimeZone())) {
            showRedToilet(false);
            if (this.newToiletErrorEventNum > 0) {
                P p = this.mPresenter;
                if (p != 0) {
                    ((T5HomePresenter) p).getStatistic(this.calendarTime, 3);
                }
                if (this.currentPosition != 3) {
                    if (!z && this.toiletErrorFragment.isAdded() && this.rlTopTab.getVisibility() != 0) {
                        this.toiletErrorFragment.scrollToTop();
                    }
                    selectedFragment(3);
                    return;
                }
                return;
            }
            if (this.currentPosition == 3) {
                return;
            }
            if (!z && this.toiletErrorFragment.isAdded() && this.rlTopTab.getVisibility() != 0) {
                this.toiletErrorFragment.scrollToTop();
            }
            selectedFragment(3);
            return;
        }
        if (this.currentPosition == 3) {
            return;
        }
        if (!z && this.toiletErrorFragment.isAdded() && this.rlTopTab.getVisibility() != 0) {
            this.toiletErrorFragment.scrollToTop();
        }
        selectedFragment(3);
    }

    private void scrollToTop() {
        T6EventListFragment t6EventListFragment = this.fragmentToilet;
        if (t6EventListFragment != null) {
            t6EventListFragment.scrollToTop();
        }
        T6EventListFragment t6EventListFragment2 = this.fragmentPet;
        if (t6EventListFragment2 != null) {
            t6EventListFragment2.scrollToTop();
        }
        T6EventListFragment t6EventListFragment3 = this.toiletErrorFragment;
        if (t6EventListFragment3 != null) {
            t6EventListFragment3.scrollToTop();
        }
        AppBarLayout appBarLayout = this.appBar;
        if (appBarLayout != null) {
            appBarLayout.setExpanded(true, true);
        }
    }

    private void setBottomViewInError(String str) {
        T5BottomView t5BottomView;
        if (str == null) {
            str = "";
        }
        switch (str) {
            case "camera":
            case "moto_D":
            case "moto_P":
            case "hallC":
            case "hallO":
                if (hasUnDisableError() && (t5BottomView = this.t5BottomView) != null) {
                    t5BottomView.unDisableAll();
                    break;
                }
                break;
            case "moto_R":
            case "scaleD":
            case "NFC":
            case "PROX":
            case "hallB":
            case "radar":
            case "scale":
                T5BottomView t5BottomView2 = this.t5BottomView;
                if (t5BottomView2 != null) {
                    t5BottomView2.disableClean();
                    this.t5BottomView.disableDump();
                    this.t5BottomView.disableRecover();
                    this.t5BottomView.disableSmooth();
                    this.t5BottomView.disableMaintenance();
                    break;
                }
                break;
            case "hallT":
                T5BottomView t5BottomView3 = this.t5BottomView;
                if (t5BottomView3 != null) {
                    t5BottomView3.disableClean();
                    this.t5BottomView.disableDump();
                    this.t5BottomView.disableRecover();
                    this.t5BottomView.disableSmooth();
                    this.t5BottomView.disableMaintenance();
                    this.t5BottomView.disableOdor();
                    break;
                }
                break;
            default:
                T5BottomView t5BottomView4 = this.t5BottomView;
                if (t5BottomView4 != null) {
                    t5BottomView4.disableAll();
                    break;
                }
                break;
        }
    }

    private boolean hasUnDisableError() {
        List<DeviceError> listCalcDeviceError = ((T5HomePresenter) this.mPresenter).calcDeviceError();
        if (listCalcDeviceError.size() == 1) {
            String errorType = listCalcDeviceError.get(0).getErrorType();
            return errorType.equals("moto_D") || errorType.equals("hallO") || errorType.equals("hallC") || errorType.equals("camera") || errorType.equals(Constants.T6_ERROR_CODE_MOTO_P);
        }
        if (listCalcDeviceError.size() != 2) {
            return false;
        }
        String errorType2 = listCalcDeviceError.get(0).getErrorType();
        String errorType3 = listCalcDeviceError.get(1).getErrorType();
        return (errorType2.equals("moto_D") || errorType2.equals("hallO") || errorType2.equals("hallC") || errorType2.equals("camera") || errorType2.equals(Constants.T6_ERROR_CODE_MOTO_P)) && (errorType3.equals("moto_D") || errorType3.equals("hallO") || errorType3.equals("hallC") || errorType3.equals("camera") || errorType3.equals(Constants.T6_ERROR_CODE_MOTO_P));
    }

    private void setBottomViewInWorkN60() {
        T6Record t6Record = this.t5Record;
        if (t6Record == null || t6Record.getState() == null) {
            return;
        }
        if (0 != this.t5Record.getState().getWanderTime() || 0 != this.t5Record.getState().getPetInTime()) {
            T5BottomView t5BottomView = this.t5BottomView;
            if (t5BottomView != null) {
                t5BottomView.disableClean();
                this.t5BottomView.disableDump();
                this.t5BottomView.disableRecover();
                this.t5BottomView.disableSmooth();
                this.t5BottomView.disableMaintenance();
                this.t5BottomView.disableOdor();
            }
            return;
        }
        String errorCode = getErrorCode();
        errorCode.hashCode();
        switch (errorCode) {
            case "box_full":
                T5BottomView t5BottomView2 = this.t5BottomView;
                if (t5BottomView2 != null) {
                    t5BottomView2.disableClean();
                    this.t5BottomView.disableDump();
                    this.t5BottomView.disableOdor();
                    break;
                }
                break;
            case "t6_error_roll_weight":
                T5BottomView t5BottomView3 = this.t5BottomView;
                if (t5BottomView3 != null) {
                    t5BottomView3.disableClean();
                    this.t5BottomView.disableDump();
                    this.t5BottomView.disableRecover();
                    this.t5BottomView.disableSmooth();
                    this.t5BottomView.disableMaintenance();
                    this.t5BottomView.disableOdor();
                    break;
                }
                break;
            case "box_state_uninstall":
                T5BottomView t5BottomView4 = this.t5BottomView;
                if (t5BottomView4 != null) {
                    t5BottomView4.disableClean();
                    this.t5BottomView.disableDump();
                    this.t5BottomView.disableSmooth();
                    this.t5BottomView.disableRecover();
                    this.t5BottomView.disableOdor();
                    break;
                }
                break;
            default:
                T5BottomView t5BottomView5 = this.t5BottomView;
                if (t5BottomView5 != null) {
                    t5BottomView5.disableOdor();
                    break;
                }
                break;
        }
    }

    private void setBottomViewInWork() {
        T6Record t6Record = this.t5Record;
        if (t6Record == null || t6Record.getState() == null) {
            return;
        }
        if (0 != this.t5Record.getState().getWanderTime() || 0 != this.t5Record.getState().getPetInTime()) {
            T5BottomView t5BottomView = this.t5BottomView;
            if (t5BottomView != null) {
                t5BottomView.disableClean();
                this.t5BottomView.disableDump();
                this.t5BottomView.disableRecover();
                this.t5BottomView.disableSmooth();
                this.t5BottomView.disableOdor();
                this.t5BottomView.disableMaintenance();
            }
            return;
        }
        String errorCode = getErrorCode();
        errorCode.hashCode();
        switch (errorCode) {
            case "box_full":
                T5BottomView t5BottomView2 = this.t5BottomView;
                if (t5BottomView2 != null) {
                    t5BottomView2.disableClean();
                    this.t5BottomView.disableDump();
                    break;
                }
                break;
            case "t6_error_roll_weight":
                T5BottomView t5BottomView3 = this.t5BottomView;
                if (t5BottomView3 != null) {
                    t5BottomView3.disableClean();
                    this.t5BottomView.disableDump();
                    this.t5BottomView.disableRecover();
                    this.t5BottomView.disableSmooth();
                    this.t5BottomView.disableMaintenance();
                    break;
                }
                break;
            case "box_state_uninstall":
                T5BottomView t5BottomView4 = this.t5BottomView;
                if (t5BottomView4 != null) {
                    t5BottomView4.disableClean();
                    this.t5BottomView.disableDump();
                    this.t5BottomView.disableSmooth();
                    this.t5BottomView.disableRecover();
                    break;
                }
                break;
            default:
                T5BottomView t5BottomView5 = this.t5BottomView;
                if (t5BottomView5 != null) {
                    t5BottomView5.unDisableAll();
                    break;
                }
                break;
        }
    }

    private String getErrorCode() {
        if (this.t5Record.getState().isBoxFull()) {
            return Constants.T6_ERROR_CODE_BOX_FULL;
        }
        if (this.t5Record.getState().getBoxState() != 1) {
            return Constants.T5_ERROR_CODE_BOX_UNINSTALL;
        }
        if (this.t5Record.getState().getWeightState() == 1) {
            return Constants.T6_ERROR_ROLL_WEIGHT;
        }
        if (this.t5Record.getState().isSandLack()) {
            return Constants.T6_ERROR_CODE_SAND_LACK;
        }
        if (this.t5Record.getState().getDeodorantLeftDays() < 1) {
            return Constants.T5_ERROR_CODE_N50_EMPTY;
        }
        if (this.t5Record.getState().getSprayLeftDays() <= 0) {
            return Constants.T5_ERROR_CODE_N60_EMPTY;
        }
        if (this.t5Record.getState().getPackageState() != null && this.t5Record.getState().getPackageState().intValue() == 1 && this.t5Record.getPackageIgnoreState() == 0) {
            return Constants.T6_ERROR_CODE_PACKBOX;
        }
        return "";
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:243:0x0140  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void showErrorWindow(com.petkit.android.activities.petkitBleDevice.t6.mode.DeviceError r14) {
        /*
            Method dump skipped, instruction units count: 1314
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.showErrorWindow(com.petkit.android.activities.petkitBleDevice.t6.mode.DeviceError):void");
    }

    public /* synthetic */ void lambda$showErrorWindow$17() {
        launchActivity(T6LearnMoreActivity.newIntent(this, ApiTools.getWebUrlByKey("close_sensor"), getResources().getString(R.string.H5_navTitle_closer_sensor)));
    }

    public /* synthetic */ void lambda$showErrorWindow$18() {
        launchActivity(T6LearnMoreActivity.newIntent(this, ApiTools.getWebUrlByKey("magnet_lid_fault"), getResources().getString(R.string.T5_magnet)));
    }

    public /* synthetic */ void lambda$showErrorWindow$19() {
        launchActivity(T6LearnMoreActivity.newIntent(this, ApiTools.getWebUrlByKey("sealed-door-exception"), getResources().getString(R.string.H5_navTitle_sealed_door_exception)));
    }

    public /* synthetic */ void lambda$showErrorWindow$20() {
        RelatedProductsInfor relatedProductsInfor = this.relatedProductsInfor;
        if (relatedProductsInfor == null || relatedProductsInfor.getStandard() == null || this.relatedProductsInfor.getStandard().getT5() == null) {
            return;
        }
        MallUtils.goToWebOrProductDetail(this, this.relatedProductsInfor.getStandard().getT5().getUrinal().getShareUrl(), this.relatedProductsInfor.getStandard().getT5().getUrinal().getUrlType());
    }

    public void unInstallN60(T5CopyWritingGifVideoResult t5CopyWritingGifVideoResult) {
        if (t5CopyWritingGifVideoResult == null) {
            return;
        }
        if (this.relatedProductsInfor.getStandard().getT5().getK4() != null && !TextUtils.isEmpty(this.relatedProductsInfor.getStandard().getT5().getK4().getShareUrl())) {
            DeviceErrorWarnWindow deviceErrorWarnWindow = new DeviceErrorWarnWindow(this, this.deviceType, t5CopyWritingGifVideoResult.getSprayUnInstall().get(0).getStepName(), t5CopyWritingGifVideoResult.getSprayUnInstall().get(0).getStepDesc(), t5CopyWritingGifVideoResult.getSprayUnInstall().get(0).getStepUrl(), "", getString(R.string.Go_to_buy));
            deviceErrorWarnWindow.setOnClickListener(new DeviceErrorWarnWindow.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.24
                @Override // com.petkit.android.activities.petkitBleDevice.widget.DeviceErrorWarnWindow.OnClickListener
                public /* synthetic */ void onClickText() {
                    DeviceErrorWarnWindow.OnClickListener.CC.$default$onClickText(this);
                }

                public AnonymousClass24() {
                }

                @Override // com.petkit.android.activities.petkitBleDevice.widget.DeviceErrorWarnWindow.OnClickListener
                public void onClickLearnMore() {
                    T5HomeActivity t5HomeActivity = T5HomeActivity.this;
                    MallUtils.goToWebOrProductDetail(t5HomeActivity, t5HomeActivity.relatedProductsInfor.getStandard().getT5().getK4().getShareUrl(), T5HomeActivity.this.relatedProductsInfor.getStandard().getT5().getK4().getUrlType());
                }
            });
            deviceErrorWarnWindow.showAtLocation(getWindow().getDecorView(), 17, 0, 0);
            return;
        }
        new DeviceErrorWarnWindow(this, this.deviceType, t5CopyWritingGifVideoResult.getSprayUnInstall().get(0).getStepName(), t5CopyWritingGifVideoResult.getSprayUnInstall().get(0).getStepDesc(), t5CopyWritingGifVideoResult.getSprayUnInstall().get(0).getStepUrl(), "", getString(R.string.I_already_know)).showAtLocation(getWindow().getDecorView(), 17, 0, 0);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$24 */
    public class AnonymousClass24 implements DeviceErrorWarnWindow.OnClickListener {
        @Override // com.petkit.android.activities.petkitBleDevice.widget.DeviceErrorWarnWindow.OnClickListener
        public /* synthetic */ void onClickText() {
            DeviceErrorWarnWindow.OnClickListener.CC.$default$onClickText(this);
        }

        public AnonymousClass24() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.widget.DeviceErrorWarnWindow.OnClickListener
        public void onClickLearnMore() {
            T5HomeActivity t5HomeActivity = T5HomeActivity.this;
            MallUtils.goToWebOrProductDetail(t5HomeActivity, t5HomeActivity.relatedProductsInfor.getStandard().getT5().getK4().getShareUrl(), T5HomeActivity.this.relatedProductsInfor.getStandard().getT5().getK4().getUrlType());
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$25 */
    public class AnonymousClass25 implements NormalCenterTipWindow.OnClickOk {
        public final /* synthetic */ long val$deviceId;

        public AnonymousClass25(long j) {
            j = j;
        }

        @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
        public void onOkClick() {
            T5HomeActivity t5HomeActivity = T5HomeActivity.this;
            t5HomeActivity.launchActivity(T6SmartSettingActivity.newIntent(t5HomeActivity, j, t5HomeActivity.deviceType));
        }

        @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
        public void onCancelClick() {
            CommonUtils.addSysBoolMap(T5HomeActivity.this, "LIGHT_ASSIST_IN_TAKING_PHOTO_WINDOW_WHILE_PH_OPEN" + T5HomeActivity.this.deviceType + j, true);
        }
    }

    private void showPhLightTipWindow(long j) {
        NormalCenterTipWindow normalCenterTipWindow = new NormalCenterTipWindow(this, new NormalCenterTipWindow.OnClickOk() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.25
            public final /* synthetic */ long val$deviceId;

            public AnonymousClass25(long j2) {
                j = j2;
            }

            @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
            public void onOkClick() {
                T5HomeActivity t5HomeActivity = T5HomeActivity.this;
                t5HomeActivity.launchActivity(T6SmartSettingActivity.newIntent(t5HomeActivity, j, t5HomeActivity.deviceType));
            }

            @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
            public void onCancelClick() {
                CommonUtils.addSysBoolMap(T5HomeActivity.this, "LIGHT_ASSIST_IN_TAKING_PHOTO_WINDOW_WHILE_PH_OPEN" + T5HomeActivity.this.deviceType + j, true);
            }
        }, "", getString(R.string.Prompt_light_assistant_ph_detection_title));
        normalCenterTipWindow.setSelectText(getResources().getString(R.string.Not_remind), getResources().getString(R.string.Wifi_set));
        normalCenterTipWindow.show(getWindow().getDecorView());
    }

    private void showWeakWifiWindow() {
        if (this.wifiWeakWindow == null) {
            if (this.t5Record.getModelCode() == 2) {
                this.wifiWeakWindow = new NewWifiWeakWindow(R.drawable.img_t5_2_wifi_weak, this);
            } else {
                this.wifiWeakWindow = new NewWifiWeakWindow(R.drawable.img_t5_wifi_weak, this);
            }
        }
        if (this.t5Record.getState().getWifi().getRsq() <= -65 && this.t5Record.getState().getWifi().getRsq() > -75) {
            if (TimeUtils.getInstance().getCurrentSeconds() - CommonUtils.getSysLongMap(this, Consts.T6_WIFI_SIGINAL_WINDOW + this.t5Record.getDeviceId()) > 2592000) {
                this.wifiWeakWindow.setNoRemindVisibility(0);
                this.wifiWeakWindow.setNoRemindClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.26
                    public AnonymousClass26() {
                    }

                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        T5HomeActivity.this.wifiWeakWindow.dismiss();
                        CommonUtils.addSysLongMap(T5HomeActivity.this, Consts.T6_WIFI_SIGINAL_WINDOW + T5HomeActivity.this.t5Record.getDeviceId(), TimeUtils.getInstance().getCurrentSeconds());
                        T5HomeActivity t5HomeActivity = T5HomeActivity.this;
                        t5HomeActivity.setupView(t5HomeActivity.t5Record);
                    }
                });
                this.wifiWeakWindow.showAtLocation(getWindow().getDecorView(), 17, 0, 0);
                return;
            }
            return;
        }
        if (this.t5Record.getState().getWifi().getRsq() < -75) {
            this.wifiWeakWindow.setNoRemindVisibility(8);
            CommonUtils.addSysLongMap(this, Consts.T6_WIFI_SIGINAL_WINDOW + this.t5Record.getDeviceId(), 0L);
            this.wifiWeakWindow.showAtLocation(getWindow().getDecorView(), 17, 0, 0);
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$26 */
    public class AnonymousClass26 implements View.OnClickListener {
        public AnonymousClass26() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            T5HomeActivity.this.wifiWeakWindow.dismiss();
            CommonUtils.addSysLongMap(T5HomeActivity.this, Consts.T6_WIFI_SIGINAL_WINDOW + T5HomeActivity.this.t5Record.getDeviceId(), TimeUtils.getInstance().getCurrentSeconds());
            T5HomeActivity t5HomeActivity = T5HomeActivity.this;
            t5HomeActivity.setupView(t5HomeActivity.t5Record);
        }
    }

    public static Intent newIntent(Context context, long j, int i) {
        Intent intent = new Intent(context, (Class<?>) T5HomeActivity.class);
        intent.putExtra(Constants.EXTRA_DEVICE_ID, j);
        intent.putExtra(Constants.EXTRA_DEVICE_TYPE, i);
        return intent;
    }

    public static Intent newIntent(Context context, long j, String str, int i) {
        Intent intent = new Intent(context, (Class<?>) T5HomeActivity.class);
        intent.putExtra(Constants.EXTRA_DEVICE_ID, j);
        intent.putExtra(Constants.EXTRA_DEVICE_TYPE, i);
        intent.putExtra(Constants.EXTRA_EVENT_ID, str);
        return intent;
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T5HomeContract.View
    public void showOfflineWindow() {
        this.disposable = Observable.timer(500L, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$$ExternalSyntheticLambda9
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.lambda$showOfflineWindow$21((Long) obj);
            }
        });
    }

    public /* synthetic */ void lambda$showOfflineWindow$21(Long l) throws Exception {
        BleDeviceHomeOfflineWarnWindow bleDeviceHomeOfflineWarnWindow = this.bleDeviceHomeOfflineWarnWindow;
        if (bleDeviceHomeOfflineWarnWindow == null || !bleDeviceHomeOfflineWarnWindow.isShowing()) {
            BleDeviceHomeOfflineWarnWindow bleDeviceHomeOfflineWarnWindow2 = new BleDeviceHomeOfflineWarnWindow(this, 21, getResources().getString(R.string.K2_offline_title), R.drawable.t5_remind_offline_icon, this);
            this.bleDeviceHomeOfflineWarnWindow = bleDeviceHomeOfflineWarnWindow2;
            bleDeviceHomeOfflineWarnWindow2.showAtLocation(getWindow().getDecorView(), 17, 0, 0);
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T5HomeContract.View
    public void setupView(T6Record t6Record) {
        this.t5Record = t6Record;
        this.t5BottomView.showAiEnter(false);
        if (t6Record == null || this.isAnimationRunning) {
            return;
        }
        this.workInMaintenance = false;
        refreshPurchaseEligibility();
        if (t6Record.getRealDeviceShared() != null) {
            this.ivTodayTimesArrow.setVisibility(8);
            this.ivTodayAvgArrow.setVisibility(8);
        } else {
            this.ivTodayTimesArrow.setVisibility(0);
            this.ivTodayAvgArrow.setVisibility(0);
        }
        if (this.t5LiveViewPagerAdapter != null && this.vpLive.getCurrentItem() == 1) {
            this.t5LiveViewPagerAdapter.updateData(this.t5Record);
            this.t5LiveViewPagerAdapter.setCurrentPosition(this.vpLive.getCurrentItem());
            this.t5LiveViewPagerAdapter.refreshData();
        } else {
            T5LiveViewPagerAdapter t5LiveViewPagerAdapter = this.t5LiveViewPagerAdapter;
            if (t5LiveViewPagerAdapter != null) {
                t5LiveViewPagerAdapter.updateData(this.t5Record);
            }
        }
        if (this.isFirstInto) {
            this.handler.postDelayed(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$$ExternalSyntheticLambda54
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$setupView$22();
                }
            }, 300L);
            this.isFirstInto = false;
        }
        this.ibSetting.setImageResource(DeviceCenterUtils.isT6NeedOtaById(t6Record.getDeviceId()) ? R.drawable.black_setting_with_notify_flag : R.drawable.black_setting_icon);
        setDeviceStatus(this.t5Record);
        if (t6Record.getServiceStatus() == 0) {
            this.tvDeviceTag.setVisibility(4);
        } else if (t6Record.getServiceStatus() == 1) {
            if (this.rlTopTab.getVisibility() == 0) {
                this.tvDeviceTag.setVisibility(8);
            } else {
                this.tvDeviceTag.setVisibility(0);
            }
            this.tvDeviceTag.setTextColor(CommonUtils.getColorById(R.color.receive_for_free));
            this.tvDeviceTag.setBackgroundResource(R.drawable.gradient_gold_corners_petkitcare_tag_bg);
        } else if (t6Record.getServiceStatus() == 2) {
            this.tvDeviceTag.setTextColor(CommonUtils.getColorById(R.color.home_feeder_bg_gray));
            this.tvDeviceTag.setBackgroundResource(R.drawable.gradient_gray_corners_petkitcare_tag_bg);
            if (this.rlTopTab.getVisibility() == 0) {
                this.tvDeviceTag.setVisibility(8);
            } else {
                this.tvDeviceTag.setVisibility(0);
            }
        }
        this.t5LiveVideoView.setT5Record(t6Record);
        if (this.t6VlogRecordAdapter == null) {
            T6VlogRecordAdapter t6VlogRecordAdapter = new T6VlogRecordAdapter(this, t6Record, this.deviceType);
            this.t6VlogRecordAdapter = t6VlogRecordAdapter;
            t6VlogRecordAdapter.append((List) new ArrayList());
            this.rvHighlights.setAdapter(this.t6VlogRecordAdapter);
        }
        String name = t6Record.getName();
        this.deviceName = name;
        if (TextUtils.isEmpty(name)) {
            if (t6Record.getModelCode() == 2) {
                this.deviceName = getResources().getString(R.string.T5_2_name_default);
            } else {
                this.deviceName = getResources().getString(R.string.T5_name_default);
            }
        }
        if (t6Record.getDeviceShared() != null) {
            this.deviceName = String.format("%s-%s", t6Record.getDeviceShared().getOwnerNick(), this.deviceName);
            this.t5BottomView.showCatBody(false);
        } else {
            this.t5BottomView.showCatBody(true);
        }
        HorizontalScrollView horizontalScrollView = this.rlTopTab;
        if (horizontalScrollView != null && horizontalScrollView.getVisibility() == 0) {
            TextView textView = this.toolbarTitle;
            String str = this.title;
            if (str == null) {
                str = this.deviceName;
            }
            textView.setText(str);
        } else {
            this.toolbarTitle.setText(this.deviceName);
        }
        this.tvPopContent.setText(getResources().getString(R.string.Check_cat_prompt, String.valueOf(t6Record.getPetInTipLimit())));
        this.ivMuteIcon.setImageResource(T6Utils.getVolumeState() ? R.drawable.t6_home_mute_icon : R.drawable.t6_home_mute_off_icon);
        this.tvTodayTimes.setText(T6Utils.getT6HomeTimesString(t6Record.getInTimes()));
        this.tvTodayAvgDuration.setText(T6Utils.getT6HomeString(t6Record.getTotalTime() / (t6Record.getInTimes() == 0 ? 1 : t6Record.getInTimes())));
        this.t5BottomView.setLightIcon(t6Record.getState().getLightState() == null);
        this.rlWorkState.setVisibility(8);
        refreshDeviceStateView();
        if (t6Record.getState() != null && t6Record.getState().getOverall() == 2) {
            setFoxAnimation(3);
            this.t5BottomView.disableAll();
            this.t5LiveVideoView.setVisibility(0);
            this.videoTagLayout.setVisibility(8);
            this.ivMuteIcon.setVisibility(8);
            this.rlLivePanel.setBackgroundResource(R.drawable.t6_device_gray_bg);
        } else if (t6Record.getState() != null && t6Record.getState().getOverall() == 3) {
            setFoxAnimation(3);
            this.t5BottomView.disableAll();
            this.t5LiveVideoView.setVisibility(0);
            this.videoTagLayout.setVisibility(8);
            this.ivMuteIcon.setVisibility(0);
            this.rlLivePanel.setBackgroundResource(R.drawable.t6_device_bg);
        } else if (t6Record.getState() != null && t6Record.getState().getPower() == 0) {
            setFoxAnimation(3);
            this.t5BottomView.disableAll();
            this.t5LiveVideoView.setVisibility(0);
            this.videoTagLayout.setVisibility(8);
            this.ivMuteIcon.setVisibility(8);
            this.rlLivePanel.setBackgroundResource(R.drawable.t6_device_gray_bg);
        } else if (t6Record.getState() != null && !TextUtils.isEmpty(t6Record.getState().getErrorCode())) {
            setFoxAnimation(3);
            this.t5LiveVideoView.setVisibility(0);
            setBottomViewInError(t6Record.getState().getErrorCode());
            refreshCameraStatus(t6Record);
            this.ivMuteIcon.setVisibility(0);
            this.rlLivePanel.setBackgroundResource(R.drawable.t6_device_bg);
            if (t6Record.getState().getErrorCode().equals("hallB") && t6Record.getState() != null && t6Record.getState().getWorkState() != null && t6Record.getState().getWorkState().getWorkMode() == 9 && t6Record.getState().getWeightState() != 1) {
                this.workInMaintenance = true;
            }
        } else if (t6Record.getState() != null && t6Record.getState().getWeightState() == 1) {
            setFoxAnimation(3);
            this.t5LiveVideoView.setVisibility(0);
            refreshCameraStatus(t6Record);
            this.ivMuteIcon.setVisibility(0);
            this.rlLivePanel.setBackgroundResource(R.drawable.t6_device_bg);
            if (t6Record.getState().getRefreshState() != null && 1 == t6Record.getState().getRefreshState().getWorkProcess()) {
                setBottomViewInWorkN60();
            } else {
                setBottomViewInWork();
            }
        } else {
            this.ivMuteIcon.setVisibility(0);
            this.rlLivePanel.setBackgroundResource(R.drawable.t6_device_bg);
            if (t6Record.getState() != null && t6Record.getState().getWorkState() == null) {
                setFoxAnimation(3);
                this.t5LiveVideoView.setVisibility(0);
                if (t6Record.getState().getRefreshState() != null && 1 == t6Record.getState().getRefreshState().getWorkProcess()) {
                    this.deviceWorkStateText.setText(getResources().getString(R.string.T5_deodorizing));
                    this.btnPause.setVisibility(8);
                    this.btnStop.setVisibility(8);
                    this.btnContinue.setVisibility(8);
                    this.rlDeviceErrorOne.setVisibility(8);
                    this.rlDeviceErrorTwo.setVisibility(8);
                    this.rlWorkState.setVisibility(0);
                    this.deviceWorkStateText.setGravity(17);
                    this.ivWarnRight.setVisibility(8);
                    setBottomViewInWorkN60();
                } else {
                    setBottomViewInWork();
                }
            } else if (t6Record.getState() != null && t6Record.getState().getWorkState() != null && t6Record.getState().getWorkState().getWorkMode() == 9) {
                setFoxAnimation(3);
                this.workInMaintenance = true;
            } else if (t6Record.getState() != null && t6Record.getState().getWorkState() != null && t6Record.getState().getWorkState().getWorkMode() != 11) {
                this.rlDeviceErrorOne.setVisibility(8);
                this.rlDeviceErrorTwo.setVisibility(8);
                int workMode = t6Record.getState().getWorkState().getWorkMode();
                if (workMode == 0) {
                    T5BottomView t5BottomView = this.t5BottomView;
                    if (t5BottomView != null) {
                        t5BottomView.workInClear();
                    }
                } else if (workMode == 1) {
                    T5BottomView t5BottomView2 = this.t5BottomView;
                    if (t5BottomView2 != null) {
                        t5BottomView2.workInDump();
                    }
                } else if (workMode == 3) {
                    T5BottomView t5BottomView3 = this.t5BottomView;
                    if (t5BottomView3 != null) {
                        t5BottomView3.workInRecover();
                    }
                } else if (workMode == 4) {
                    T5BottomView t5BottomView4 = this.t5BottomView;
                    if (t5BottomView4 != null) {
                        t5BottomView4.workInSmooth();
                    }
                } else if (workMode == 9) {
                    T5BottomView t5BottomView5 = this.t5BottomView;
                    if (t5BottomView5 != null) {
                        t5BottomView5.workInMaintenance();
                    }
                } else {
                    T5BottomView t5BottomView6 = this.t5BottomView;
                    if (t5BottomView6 != null) {
                        t5BottomView6.disableAll();
                    }
                }
                this.t5LiveVideoView.setVisibility(0);
                this.rlWorkState.setVisibility(0);
                this.deviceWorkStateText.setGravity(17);
                this.ivWarnRight.setVisibility(8);
                findViewById(R.id.iv_right_arrow).setVisibility(0);
                if (1 == t6Record.getState().getWorkState().getWorkProcess() / 10) {
                    this.btnPause.setVisibility(0);
                    int workMode2 = t6Record.getState().getWorkState().getWorkMode();
                    if (workMode2 == 0) {
                        this.deviceWorkStateText.setGravity(17);
                        this.deviceWorkStateText.setText(getResources().getString(R.string.Cleaning_cat_litter));
                        AnimationDrawable animationDrawable = this.animationDrawable;
                        if (animationDrawable == null || !animationDrawable.isRunning()) {
                            if (this.clickActionType == 1) {
                                setFoxAnimation(0);
                            } else {
                                setFoxAnimation(1);
                            }
                        }
                    } else if (workMode2 == 1) {
                        this.deviceWorkStateText.setGravity(17);
                        this.deviceWorkStateText.setText(getResources().getString(R.string.Dumping_cat_litter));
                        AnimationDrawable animationDrawable2 = this.animationDrawable;
                        if (animationDrawable2 == null || !animationDrawable2.isRunning()) {
                            if (this.clickActionType == 1) {
                                setFoxAnimation(0);
                            } else {
                                setFoxAnimation(1);
                            }
                        }
                    } else if (workMode2 == 3) {
                        this.deviceWorkStateText.setGravity(17);
                        this.deviceWorkStateText.setText(getResources().getString(R.string.Device_reset_prompt));
                        AnimationDrawable animationDrawable3 = this.animationDrawable;
                        if (animationDrawable3 == null || !animationDrawable3.isRunning()) {
                            if (this.clickActionType == 1) {
                                setFoxAnimation(0);
                            } else {
                                setFoxAnimation(1);
                            }
                        }
                    } else if (workMode2 == 4) {
                        this.deviceWorkStateText.setGravity(17);
                        this.deviceWorkStateText.setText(getResources().getString(R.string.Smoothing_cat_litter));
                        AnimationDrawable animationDrawable4 = this.animationDrawable;
                        if (animationDrawable4 == null || !animationDrawable4.isRunning()) {
                            if (this.clickActionType == 1) {
                                setFoxAnimation(0);
                            } else {
                                setFoxAnimation(1);
                            }
                        }
                    } else if (workMode2 == 5) {
                        this.deviceWorkStateText.setGravity(17);
                        this.deviceWorkStateText.setText(getResources().getString(R.string.Device_calibration_prompt));
                    } else if (workMode2 == 8) {
                        this.btnPause.setVisibility(8);
                        this.deviceWorkStateText.setGravity(GravityCompat.START);
                        this.ivWarnRight.setVisibility(0);
                        this.deviceWorkStateText.setText(getResources().getString(R.string.T6_packing_tips));
                    } else if (workMode2 == 9) {
                        this.workInMaintenance = true;
                    }
                    this.btnContinue.setVisibility(8);
                    this.btnStop.setVisibility(8);
                } else if (2 == t6Record.getState().getWorkState().getWorkProcess() / 10) {
                    if (2 == t6Record.getState().getWorkState().getWorkProcess() % 10) {
                        if (t6Record.getState().getWorkState().getWorkMode() != 8) {
                            this.deviceWorkStateText.setGravity(17);
                            int safeWarn = t6Record.getState().getWorkState().getSafeWarn();
                            if (safeWarn != 0) {
                                if (safeWarn == 1) {
                                    this.deviceWorkStateText.setText(getResources().getString(R.string.Safe_warn_atp));
                                } else {
                                    this.deviceWorkStateText.setText(getResources().getString(R.string.Safe_warn_general));
                                }
                            } else if (t6Record.getState().getPetInTime() == 0) {
                                this.deviceWorkStateText.setText(getResources().getString(R.string.Device_safety_testing));
                                findViewById(R.id.iv_right_arrow).setVisibility(4);
                            } else if (t6Record.getState().isPetError()) {
                                String string = getResources().getString(R.string.Check_cat_prompt, String.valueOf(t6Record.getPetInTipLimit()));
                                SpannableString spannableString = new SpannableString(getResources().getString(R.string.Pet_go_into_device) + "\n" + string);
                                spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.t4_main_blue)), spannableString.toString().indexOf(string), spannableString.toString().indexOf(string) + string.length(), 18);
                                spannableString.setSpan(new ClickableSpan() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.27
                                    public AnonymousClass27() {
                                    }

                                    @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
                                    public void updateDrawState(TextPaint textPaint) {
                                        super.updateDrawState(textPaint);
                                        textPaint.setColor(T5HomeActivity.this.getResources().getColor(R.color.t4_main_blue));
                                        textPaint.setUnderlineText(false);
                                    }

                                    @Override // android.text.style.ClickableSpan
                                    public void onClick(View view) {
                                        T5HomeActivity t5HomeActivity = T5HomeActivity.this;
                                        t5HomeActivity.launchActivity(WebviewActivity.newIntent(t5HomeActivity, t5HomeActivity.getResources().getString(R.string.No_cat), ApiTools.getWebUrlByKey("t5_pet_reset")));
                                    }
                                }, spannableString.toString().indexOf(string), spannableString.toString().indexOf(string) + string.length(), 33);
                                this.deviceStateText.setHighlightColor(0);
                                this.deviceStateText.setText(spannableString);
                                this.ivDeviceStateIcon.setVisibility(8);
                                this.deviceStateText.setMovementMethod(LinkMovementMethod.getInstance());
                                this.deviceStateText.setVisibility(0);
                                this.rlDeviceState.setVisibility(0);
                                this.rlWorkState.setVisibility(8);
                                findViewById(R.id.iv_right_arrow).setVisibility(4);
                            } else {
                                this.rlWorkState.setVisibility(0);
                                this.deviceWorkStateText.setText(getResources().getString(R.string.Pet_go_into_device));
                                findViewById(R.id.iv_right_arrow).setVisibility(4);
                            }
                        } else if (0 != t6Record.getState().getPetInTime()) {
                            this.deviceWorkStateText.setText(getResources().getString(R.string.T6_pet_toilet_pack_pause));
                        } else {
                            this.deviceWorkStateText.setText(getResources().getString(R.string.T6_pet_close_pack_pause));
                        }
                        this.btnPause.setVisibility(8);
                        this.btnContinue.setVisibility(8);
                        this.btnStop.setVisibility(8);
                    } else {
                        this.deviceWorkStateText.setGravity(17);
                        this.deviceWorkStateText.setText(getResources().getString(R.string.Device_pause_prompt, String.valueOf((t6Record.getState().getWorkState().getStopTime() > 0 ? t6Record.getState().getWorkState().getStopTime() : t6Record.getSettings().getStopTime()) / 60)));
                        this.btnPause.setVisibility(8);
                        this.btnContinue.setVisibility(0);
                        this.btnStop.setVisibility(0);
                        findViewById(R.id.iv_right_arrow).setVisibility(4);
                    }
                    int workMode3 = t6Record.getState().getWorkState().getWorkMode();
                    if (workMode3 == 0 || workMode3 == 1 || workMode3 == 3 || workMode3 == 4) {
                        setFoxAnimation(2);
                    }
                } else if (3 == t6Record.getState().getWorkState().getWorkProcess() / 10) {
                    this.btnPause.setVisibility(0);
                    this.btnContinue.setVisibility(8);
                    this.btnStop.setVisibility(8);
                    this.deviceWorkStateText.setText(getResources().getString(R.string.Device_reset_prompt));
                    this.deviceWorkStateText.setGravity(17);
                    AnimationDrawable animationDrawable5 = this.animationDrawable;
                    if (animationDrawable5 == null || !animationDrawable5.isRunning()) {
                        setFoxAnimation(1);
                    }
                } else if (4 == t6Record.getState().getWorkState().getWorkProcess() / 10) {
                    int workMode4 = t6Record.getState().getWorkState().getWorkMode();
                    if (workMode4 == 0 || workMode4 == 1 || workMode4 == 3 || workMode4 == 4) {
                        setFoxAnimation(2);
                    }
                    this.btnPause.setVisibility(8);
                    this.btnContinue.setVisibility(0);
                    this.btnStop.setVisibility(8);
                    this.deviceWorkStateText.setGravity(17);
                    if (2 == t6Record.getState().getWorkState().getWorkProcess() % 10) {
                        int safeWarn2 = t6Record.getState().getWorkState().getSafeWarn();
                        if (safeWarn2 != 0) {
                            if (safeWarn2 == 1) {
                                this.rlPopParent.setVisibility(4);
                                this.deviceWorkStateText.setText(getResources().getString(R.string.Safe_warn_atp));
                            } else {
                                this.rlPopParent.setVisibility(4);
                                this.deviceWorkStateText.setText(getResources().getString(R.string.Safe_warn_general));
                            }
                        } else if (t6Record.getState().getPetInTime() == 0) {
                            this.rlPopParent.setVisibility(4);
                            this.deviceWorkStateText.setText(getResources().getString(R.string.Device_safety_testing));
                        } else if (t6Record.getState().isPetError()) {
                            this.isNeedShowPetError = true;
                            String str2 = getResources().getString(R.string.No_cat) + ">";
                            SpannableString spannableString2 = new SpannableString(getResources().getString(R.string.Pet_go_into_device) + ",\n" + str2);
                            spannableString2.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.t4_main_blue)), spannableString2.toString().indexOf(str2), spannableString2.toString().indexOf(str2) + str2.length(), 18);
                            spannableString2.setSpan(new ClickableSpan() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.28
                                public AnonymousClass28() {
                                }

                                @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
                                public void updateDrawState(TextPaint textPaint) {
                                    super.updateDrawState(textPaint);
                                    textPaint.setColor(T5HomeActivity.this.getResources().getColor(R.color.t4_main_blue));
                                    textPaint.setUnderlineText(false);
                                }

                                @Override // android.text.style.ClickableSpan
                                public void onClick(View view) {
                                    T5HomeActivity.this.showTipWindow();
                                }
                            }, spannableString2.toString().indexOf(str2), spannableString2.toString().indexOf(str2) + str2.length(), 33);
                            this.deviceStateText.setHighlightColor(0);
                            this.deviceStateText.setText(spannableString2);
                            this.ivDeviceStateIcon.setVisibility(8);
                            this.deviceStateText.setMovementMethod(LinkMovementMethod.getInstance());
                            this.deviceStateText.setVisibility(0);
                            this.rlDeviceState.setVisibility(0);
                            this.rlWorkState.setVisibility(8);
                            findViewById(R.id.iv_right_arrow).setVisibility(4);
                        } else {
                            this.rlWorkState.setVisibility(0);
                            this.rlPopParent.setVisibility(4);
                            this.deviceWorkStateText.setText(getResources().getString(R.string.Pet_go_into_device));
                        }
                    } else {
                        this.rlPopParent.setVisibility(4);
                        this.deviceWorkStateText.setGravity(17);
                        this.deviceWorkStateText.setText(getResources().getString(R.string.Device_pause_prompt, String.valueOf((t6Record.getState().getWorkState().getStopTime() > 0 ? t6Record.getState().getWorkState().getStopTime() : t6Record.getSettings().getStopTime()) / 60)));
                    }
                }
            }
            refreshCameraStatus(t6Record);
        }
        if (this.workInMaintenance) {
            setWorkInMaintenance();
        } else {
            setWorkInNormal();
        }
        if (!AppVersionStateUtils.getCurrentAppVersionCheckState()) {
            showPurchaseEntry();
        }
        if (this.isNeedShowPetError || this.rlPopParent.getVisibility() != 0) {
            return;
        }
        this.rlPopParent.setVisibility(4);
    }

    public /* synthetic */ void lambda$setupView$22() {
        this.vpLive.setCurrentItem(CommonUtils.getSysIntMap(Constants.T6_HOME_PAGE_SELECT + this.deviceId));
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$27 */
    public class AnonymousClass27 extends ClickableSpan {
        public AnonymousClass27() {
        }

        @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
        public void updateDrawState(TextPaint textPaint) {
            super.updateDrawState(textPaint);
            textPaint.setColor(T5HomeActivity.this.getResources().getColor(R.color.t4_main_blue));
            textPaint.setUnderlineText(false);
        }

        @Override // android.text.style.ClickableSpan
        public void onClick(View view) {
            T5HomeActivity t5HomeActivity = T5HomeActivity.this;
            t5HomeActivity.launchActivity(WebviewActivity.newIntent(t5HomeActivity, t5HomeActivity.getResources().getString(R.string.No_cat), ApiTools.getWebUrlByKey("t5_pet_reset")));
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$28 */
    public class AnonymousClass28 extends ClickableSpan {
        public AnonymousClass28() {
        }

        @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
        public void updateDrawState(TextPaint textPaint) {
            super.updateDrawState(textPaint);
            textPaint.setColor(T5HomeActivity.this.getResources().getColor(R.color.t4_main_blue));
            textPaint.setUnderlineText(false);
        }

        @Override // android.text.style.ClickableSpan
        public void onClick(View view) {
            T5HomeActivity.this.showTipWindow();
        }
    }

    private void petInTimeState(T6Record t6Record) {
        if (0 != t6Record.getState().getPetInTime()) {
            this.deviceStateText.setVisibility(0);
            this.deviceStateText.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
            if (t6Record.getState().isPetError()) {
                this.deviceStateText.setLineSpacing(ArmsUtils.dip2px(this, 9.0f), 1.0f);
                this.isNeedShowPetError = true;
                String str = getResources().getString(R.string.No_cat) + " >";
                SpannableString spannableString = new SpannableString(getResources().getString(R.string.Pet_into_prompt) + "\n" + str);
                spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.t4_main_blue)), spannableString.toString().indexOf(str), spannableString.toString().indexOf(str) + str.length(), 18);
                spannableString.setSpan(new ClickableSpan() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.29
                    public AnonymousClass29() {
                    }

                    @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
                    public void updateDrawState(TextPaint textPaint) {
                        super.updateDrawState(textPaint);
                        textPaint.setColor(T5HomeActivity.this.getResources().getColor(R.color.t4_main_blue));
                        textPaint.setUnderlineText(false);
                    }

                    @Override // android.text.style.ClickableSpan
                    public void onClick(View view) {
                        T5HomeActivity.this.showTipWindow();
                    }
                }, spannableString.toString().indexOf(str), spannableString.toString().indexOf(str) + str.length(), 33);
                this.deviceStateText.setHighlightColor(0);
                this.deviceStateText.setText(spannableString);
                this.deviceStateText.setMovementMethod(LinkMovementMethod.getInstance());
            } else {
                this.rlPopParent.setVisibility(4);
                this.deviceStateText.setText(getResources().getString(R.string.Pet_into_prompt));
            }
        } else {
            this.rlPopParent.setVisibility(4);
        }
        this.deviceStateText.setVisibility(0);
        this.rlDeviceState.setVisibility(0);
        findViewById(R.id.iv_right_arrow).setVisibility(4);
        this.ivDeviceStateIcon.setImageResource(0);
        this.ivDeviceStateIcon.setVisibility(0);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$29 */
    public class AnonymousClass29 extends ClickableSpan {
        public AnonymousClass29() {
        }

        @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
        public void updateDrawState(TextPaint textPaint) {
            super.updateDrawState(textPaint);
            textPaint.setColor(T5HomeActivity.this.getResources().getColor(R.color.t4_main_blue));
            textPaint.setUnderlineText(false);
        }

        @Override // android.text.style.ClickableSpan
        public void onClick(View view) {
            T5HomeActivity.this.showTipWindow();
        }
    }

    private void showPopWindow() {
        if (this.isFirst) {
            this.isNeedShowPetError = false;
            this.deviceStateText.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.30
                public AnonymousClass30() {
                }

                @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                public void onGlobalLayout() {
                    int[] iArr = new int[2];
                    T5HomeActivity.this.deviceStateText.getLocationOnScreen(iArr);
                    int i = iArr[0];
                    int i2 = iArr[1];
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) T5HomeActivity.this.rlPop.getLayoutParams();
                    layoutParams.setMargins(i, i2, layoutParams.rightMargin, layoutParams.bottomMargin);
                    T5HomeActivity.this.rlPopParent.setLayoutParams(layoutParams);
                    T5HomeActivity.this.handler.postDelayed(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.30.1
                        public AnonymousClass1() {
                        }

                        @Override // java.lang.Runnable
                        public void run() {
                            T5HomeActivity.this.rlPopParent.setVisibility(0);
                            T5HomeActivity.this.isFirst = false;
                        }
                    }, 300L);
                    T5HomeActivity.this.deviceStateText.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }

                /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$30$1 */
                public class AnonymousClass1 implements Runnable {
                    public AnonymousClass1() {
                    }

                    @Override // java.lang.Runnable
                    public void run() {
                        T5HomeActivity.this.rlPopParent.setVisibility(0);
                        T5HomeActivity.this.isFirst = false;
                    }
                }
            });
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$30 */
    public class AnonymousClass30 implements ViewTreeObserver.OnGlobalLayoutListener {
        public AnonymousClass30() {
        }

        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public void onGlobalLayout() {
            int[] iArr = new int[2];
            T5HomeActivity.this.deviceStateText.getLocationOnScreen(iArr);
            int i = iArr[0];
            int i2 = iArr[1];
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) T5HomeActivity.this.rlPop.getLayoutParams();
            layoutParams.setMargins(i, i2, layoutParams.rightMargin, layoutParams.bottomMargin);
            T5HomeActivity.this.rlPopParent.setLayoutParams(layoutParams);
            T5HomeActivity.this.handler.postDelayed(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.30.1
                public AnonymousClass1() {
                }

                @Override // java.lang.Runnable
                public void run() {
                    T5HomeActivity.this.rlPopParent.setVisibility(0);
                    T5HomeActivity.this.isFirst = false;
                }
            }, 300L);
            T5HomeActivity.this.deviceStateText.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }

        /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$30$1 */
        public class AnonymousClass1 implements Runnable {
            public AnonymousClass1() {
            }

            @Override // java.lang.Runnable
            public void run() {
                T5HomeActivity.this.rlPopParent.setVisibility(0);
                T5HomeActivity.this.isFirst = false;
            }
        }
    }

    private void showPurchaseEntry() {
        this.t5Record.getDeviceShared();
        if (this.t5Record.getCloudProduct() != null) {
            TextUtils.isEmpty(DataHelper.getStringSF(this, this.t5Record.getDeviceId() + "_" + this.deviceType + "_" + this.t5Record.getCloudProduct().getServiceId()));
        }
        DataHelper.getBooleanSF(this, Constants.T6_DEVICE_HOME_FREE_TRIAL_TIP + this.deviceType + "_" + this.t5Record.getDeviceId());
        final int intergerSF = DataHelper.getIntergerSF(this, Constants.T6_FREE_TRIAL_LEFT_TIME_PROMPT_COUNT + this.deviceType + "_" + this.t5Record.getDeviceId(), 2);
        this.llTrialRemainingTime.setVisibility(8);
        if (this.t5Record.getServiceStatus() == 1 && this.t5Record.getMoreService() != 1 && this.t5Record.getCloudProduct().getSubscribe() != 1 && "FREE".equalsIgnoreCase(this.t5Record.getCloudProduct().getChargeType())) {
            final boolean z = (Long.parseLong(this.t5Record.getCloudProduct().getWorkIndate()) * 1000) - System.currentTimeMillis() < 259200000;
            if (intergerSF == 2 || (intergerSF == 1 && z)) {
                this.llTrialRemainingTime.setVisibility(0);
                int i = (int) (((Long.parseLong(this.t5Record.getCloudProduct().getWorkIndate()) * 1000) - System.currentTimeMillis()) / 86400000);
                String str = "zh_CN".equalsIgnoreCase(UserInforUtils.getLanguageLocaleString(CommonUtils.getAppContext())) ? "" : " ";
                if (i > 0) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(getResources().getString(R.string.Trial_remaining_time));
                    sb.append("：");
                    sb.append(i);
                    sb.append(str);
                    sb.append(getString(i > 1 ? R.string.Unit_days : R.string.Unit_day));
                    this.tvTrialRemainingTime.setText(sb.toString());
                } else if (i == 0) {
                    float f = ((Long.parseLong(this.t5Record.getCloudProduct().getWorkIndate()) * 1000.0f) - System.currentTimeMillis()) / 3600000.0f;
                    if (f <= 0.0f) {
                        this.llTrialRemainingTime.setVisibility(8);
                    } else if (f < 1.0f) {
                        this.tvTrialRemainingTime.setText(getString(R.string.Trial_remaining_time) + "：1" + str + getResources().getString(R.string.Unit_hour));
                    } else {
                        int i2 = (int) f;
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(getString(R.string.Trial_remaining_time));
                        sb2.append("：");
                        sb2.append(i2);
                        sb2.append(str);
                        sb2.append(getString(i2 > 1 ? R.string.Unit_hours : R.string.Unit_hour));
                        this.tvTrialRemainingTime.setText(sb2.toString());
                    }
                }
                this.ivTrialRemainingTimeClose.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$$ExternalSyntheticLambda44
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$showPurchaseEntry$23(z, intergerSF, view);
                    }
                });
            }
        }
    }

    public /* synthetic */ void lambda$showPurchaseEntry$23(boolean z, int i, View view) {
        this.llTrialRemainingTime.setVisibility(8);
        DataHelper.setIntergerSF(this, Constants.T6_FREE_TRIAL_LEFT_TIME_PROMPT_COUNT + this.deviceType + "_" + this.t5Record.getDeviceId(), z ? 0 : i - 1);
    }

    public void showTipWindow() {
        startActivity(WebviewActivity.newIntent(this, getResources().getString(R.string.No_cat), ApiTools.getWebUrlByKey("t5_pet_reset")));
        this.rlPopParent.setVisibility(4);
    }

    private void refreshDeviceStateView() {
        this.rlDeviceState.setVisibility(8);
        this.rlDeviceErrorOne.setVisibility(8);
        this.rlDeviceErrorTwo.setVisibility(8);
        if (this.t5Record.getState().getOverall() == 2) {
            this.rlDeviceState.setVisibility(0);
            this.deviceStateText.setText(R.string.Device_off_line_tip);
            this.ivDeviceStateIcon.setImageResource(R.drawable.d4sh_main_warn_message_warn_icon);
            this.ivDeviceStateIcon.setVisibility(0);
            findViewById(R.id.iv_right_arrow).setVisibility(0);
            return;
        }
        if (this.t5Record.getState().getOta() == 1) {
            this.rlDeviceState.setVisibility(0);
            this.deviceStateText.setText(R.string.D4sh_update);
            this.ivDeviceStateIcon.setImageResource(R.drawable.d4sh_main_warn_message_warn_icon);
            this.ivDeviceStateIcon.setVisibility(0);
            findViewById(R.id.iv_right_arrow).setVisibility(0);
            return;
        }
        if (this.t5Record.getState() != null && this.t5Record.getState().getWeightState() != 0) {
            showCalc();
            return;
        }
        if (this.t5Record.getState().getWorkState() == null && this.t5Record.getState().getPetInTime() != 0) {
            petInTimeState(this.t5Record);
        } else if (this.t5Record.getState().getWorkState() != null && this.t5Record.getState().getPetInTime() != 0) {
            this.rlDeviceState.setVisibility(8);
        } else {
            showCalc();
        }
    }

    private void showCalc() {
        int i;
        int i2;
        final List<DeviceError> listCalcDeviceError = ((T5HomePresenter) this.mPresenter).calcDeviceError();
        this.ivDeviceErrorOneRightArrow.setVisibility(0);
        this.ivDeviceErrorTwoRightArrow.setVisibility(0);
        findViewById(R.id.iv_device_error_one_right_arrow).setVisibility(0);
        findViewById(R.id.iv_device_error_two_right_arrow).setVisibility(0);
        if (listCalcDeviceError.size() > 1) {
            this.rlDeviceErrorOne.setVisibility(0);
            this.ivDeviceErrorOneIcon.setImageResource(R.drawable.d4sh_main_warn_message_warn_icon);
            this.deviceErrorOneText.setText(listCalcDeviceError.get(0).getErrorMsg());
            this.rlDeviceErrorTwo.setVisibility(0);
            this.ivDeviceErrorTwoIcon.setImageResource(R.drawable.d4sh_main_warn_message_warn_icon);
            this.deviceErrorTwoText.setText(getResources().getString(R.string.Have_mistake_to_handle, String.valueOf(listCalcDeviceError.size() - 1)));
            String errorType = listCalcDeviceError.get(0).getErrorType();
            if (errorType.equals(Constants.T5_ERROR_CODE_N50_EMPTY)) {
                this.llKnowMoreOne.setVisibility(0);
                this.ivDeviceErrorOneIcon.setImageResource(0);
                this.rlDeviceErrorOne.setGravity(17);
                this.deviceErrorOneText.setGravity(17);
                this.ivDeviceErrorOneRightArrow.setVisibility(4);
                this.btnIgnoreOne.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$$ExternalSyntheticLambda28
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$showCalc$24(view);
                    }
                });
                this.btnKnowMoreOne.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$$ExternalSyntheticLambda35
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$showCalc$25(view);
                    }
                });
            } else if (errorType.equals(Constants.T5_ERROR_CODE_N60_EMPTY)) {
                this.llKnowMoreOne.setVisibility(0);
                this.ivDeviceErrorOneIcon.setImageResource(0);
                this.rlDeviceErrorOne.setGravity(17);
                this.deviceErrorOneText.setGravity(17);
                this.ivDeviceErrorOneRightArrow.setVisibility(4);
                this.btnIgnoreOne.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$$ExternalSyntheticLambda36
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$showCalc$26(view);
                    }
                });
                this.btnKnowMoreOne.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$$ExternalSyntheticLambda37
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$showCalc$27(view);
                    }
                });
            } else if (errorType.equals(Constants.T6_OPEN_LIGHT_ASSIST_WHILE_PH_OPEN)) {
                this.ivDeviceErrorOneIcon.setVisibility(8);
                this.btnKnowMoreOne.setVisibility(8);
                this.btnIgnoreOne.setVisibility(8);
                this.ivDeviceErrorOneRightArrow.setVisibility(0);
            } else if (errorType.equals(Constants.T6_ERROR_CODE_PET_OUT_TIPS)) {
                this.llKnowMoreOne.setVisibility(8);
                this.rlDeviceErrorOne.setGravity(GravityCompat.START);
                this.deviceErrorOneText.setGravity(GravityCompat.START);
                this.ivDeviceErrorOneIcon.setImageResource(0);
            } else if (errorType.equals(Constants.PET_ERROR_TOILET_TOO_LESS)) {
                this.llKnowMoreOne.setVisibility(0);
                this.btnIgnoreOne.setText(getResources().getString(R.string.This_round_not_remind));
                this.btnKnowMoreOne.setText(getResources().getString(R.string.Consult_urology_specialist));
                if (DoctorUtils.getInstance().isTestUser(this) && listCalcDeviceError.get(0).getPetErrorInfo() != null) {
                    this.btnKnowMoreOne.setVisibility(0);
                } else {
                    this.btnKnowMoreOne.setVisibility(8);
                }
                this.btnIgnoreOne.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$$ExternalSyntheticLambda38
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$showCalc$28(listCalcDeviceError, view);
                    }
                });
                this.btnKnowMoreOne.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$$ExternalSyntheticLambda39
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$showCalc$29(listCalcDeviceError, view);
                    }
                });
                this.rlDeviceErrorOne.setGravity(GravityCompat.START);
                this.deviceErrorOneText.setGravity(GravityCompat.START);
                this.ivDeviceErrorOneIcon.setImageResource(R.drawable.d4sh_main_warn_message_warn_icon);
                findViewById(R.id.iv_device_error_one_right_arrow).setVisibility(4);
            } else if (errorType.equals(Constants.T6_ERROR_CODE_HP_ERROR_TIPS)) {
                this.llKnowMoreOne.setVisibility(0);
                this.btnIgnoreOne.setText(getResources().getString(R.string.This_round_not_remind));
                this.btnKnowMoreOne.setText(getResources().getString(R.string.Consult_urology_specialist));
                if (DoctorUtils.getInstance().isTestUser(this)) {
                    this.btnKnowMoreOne.setVisibility(0);
                } else {
                    this.btnKnowMoreOne.setVisibility(8);
                }
                this.btnIgnoreOne.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$$ExternalSyntheticLambda40
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$showCalc$30(view);
                    }
                });
                this.btnKnowMoreOne.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$$ExternalSyntheticLambda41
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$showCalc$31(view);
                    }
                });
                this.rlDeviceErrorOne.setGravity(GravityCompat.START);
                this.deviceErrorOneText.setGravity(GravityCompat.START);
                this.ivDeviceErrorOneIcon.setImageResource(R.drawable.d4sh_main_warn_message_warn_icon);
                findViewById(R.id.iv_device_error_one_right_arrow).setVisibility(4);
            } else {
                if (errorType.equals(Constants.T6_ERROR_CODE_SAND_LACK)) {
                    i = 8;
                    this.llKnowMoreOne.setVisibility(8);
                    RelativeLayout relativeLayout = this.rlDeviceErrorOne;
                    i2 = GravityCompat.START;
                    relativeLayout.setGravity(GravityCompat.START);
                    this.deviceErrorOneText.setGravity(GravityCompat.START);
                    findViewById(R.id.iv_device_error_one_right_arrow).setVisibility(4);
                } else {
                    i = 8;
                    i2 = GravityCompat.START;
                    this.llKnowMoreOne.setVisibility(8);
                    this.rlDeviceErrorOne.setGravity(GravityCompat.START);
                    this.deviceErrorOneText.setGravity(GravityCompat.START);
                    findViewById(R.id.iv_device_error_one_right_arrow).setVisibility(0);
                }
                this.llKnowMoreTwo.setVisibility(i);
                this.rlDeviceErrorTwo.setGravity(i2);
                this.deviceErrorTwoText.setGravity(i2);
                return;
            }
            i = 8;
            i2 = GravityCompat.START;
            this.llKnowMoreTwo.setVisibility(i);
            this.rlDeviceErrorTwo.setGravity(i2);
            this.deviceErrorTwoText.setGravity(i2);
            return;
        }
        if (listCalcDeviceError.size() == 1) {
            this.rlDeviceErrorTwo.setVisibility(8);
            this.rlDeviceErrorOne.setVisibility(0);
            this.ivDeviceErrorOneIcon.setImageResource(R.drawable.d4sh_main_warn_message_warn_icon);
            this.deviceErrorOneText.setText(listCalcDeviceError.get(0).getErrorMsg());
            String errorType2 = listCalcDeviceError.get(0).getErrorType();
            if (errorType2.equals(Constants.T5_ERROR_CODE_N50_EMPTY)) {
                this.llKnowMoreOne.setVisibility(0);
                this.ivDeviceErrorOneIcon.setImageResource(0);
                this.rlDeviceErrorOne.setGravity(17);
                this.deviceErrorOneText.setGravity(17);
                this.ivDeviceErrorOneRightArrow.setVisibility(4);
                TextView textView = this.btnIgnoreOne;
                if (textView != null) {
                    textView.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$$ExternalSyntheticLambda42
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            this.f$0.lambda$showCalc$32(view);
                        }
                    });
                }
                TextView textView2 = this.btnKnowMoreOne;
                if (textView2 != null) {
                    textView2.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$$ExternalSyntheticLambda43
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            this.f$0.lambda$showCalc$33(view);
                        }
                    });
                    return;
                }
                return;
            }
            if (errorType2.equals(Constants.T5_ERROR_CODE_N60_EMPTY)) {
                this.llKnowMoreOne.setVisibility(0);
                this.ivDeviceErrorOneIcon.setImageResource(0);
                this.rlDeviceErrorOne.setGravity(17);
                this.deviceErrorOneText.setGravity(17);
                this.ivDeviceErrorOneRightArrow.setVisibility(4);
                this.btnIgnoreOne.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$$ExternalSyntheticLambda29
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$showCalc$34(view);
                    }
                });
                this.btnKnowMoreOne.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$$ExternalSyntheticLambda30
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$showCalc$35(view);
                    }
                });
                return;
            }
            if (errorType2.equals(Constants.T6_OPEN_LIGHT_ASSIST_WHILE_PH_OPEN)) {
                this.ivDeviceErrorOneIcon.setVisibility(8);
                this.btnKnowMoreOne.setVisibility(8);
                this.btnIgnoreOne.setVisibility(8);
                this.ivDeviceErrorOneRightArrow.setVisibility(0);
                this.rlDeviceErrorTwo.setVisibility(8);
                return;
            }
            if (errorType2.equals(Constants.T6_ERROR_CODE_PET_OUT_TIPS)) {
                this.llKnowMoreOne.setVisibility(8);
                this.rlDeviceErrorOne.setGravity(GravityCompat.START);
                this.deviceErrorOneText.setGravity(GravityCompat.START);
                this.ivDeviceErrorOneIcon.setImageResource(0);
                return;
            }
            if (errorType2.equals(Constants.PET_ERROR_TOILET_TOO_LESS)) {
                this.llKnowMoreOne.setVisibility(0);
                this.btnIgnoreOne.setText(getResources().getString(R.string.This_round_not_remind));
                this.btnKnowMoreOne.setText(getResources().getString(R.string.Consult_urology_specialist));
                if (DoctorUtils.getInstance().isTestUser(this) && listCalcDeviceError.get(0).getPetErrorInfo() != null) {
                    this.btnKnowMoreOne.setVisibility(0);
                } else {
                    this.btnKnowMoreOne.setVisibility(8);
                }
                this.btnIgnoreOne.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$$ExternalSyntheticLambda31
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$showCalc$36(listCalcDeviceError, view);
                    }
                });
                this.btnKnowMoreOne.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$$ExternalSyntheticLambda32
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$showCalc$37(listCalcDeviceError, view);
                    }
                });
                this.rlDeviceErrorOne.setGravity(GravityCompat.START);
                this.deviceErrorOneText.setGravity(GravityCompat.START);
                this.ivDeviceErrorOneIcon.setImageResource(R.drawable.d4sh_main_warn_message_warn_icon);
                findViewById(R.id.iv_device_error_one_right_arrow).setVisibility(4);
                return;
            }
            if (errorType2.equals(Constants.T6_ERROR_CODE_HP_ERROR_TIPS)) {
                this.llKnowMoreOne.setVisibility(0);
                this.btnIgnoreOne.setText(getResources().getString(R.string.This_round_not_remind));
                this.btnKnowMoreOne.setText(getResources().getString(R.string.Consult_urology_specialist));
                if (DoctorUtils.getInstance().isTestUser(this)) {
                    this.btnKnowMoreOne.setVisibility(0);
                } else {
                    this.btnKnowMoreOne.setVisibility(8);
                }
                this.btnIgnoreOne.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$$ExternalSyntheticLambda33
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$showCalc$38(view);
                    }
                });
                this.btnKnowMoreOne.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$$ExternalSyntheticLambda34
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$showCalc$39(view);
                    }
                });
                this.rlDeviceErrorOne.setGravity(GravityCompat.START);
                this.deviceErrorOneText.setGravity(GravityCompat.START);
                this.ivDeviceErrorOneIcon.setImageResource(R.drawable.d4sh_main_warn_message_warn_icon);
                findViewById(R.id.iv_device_error_one_right_arrow).setVisibility(4);
                return;
            }
            if (errorType2.equals(Constants.T6_ERROR_CODE_SAND_LACK)) {
                this.llKnowMoreOne.setVisibility(8);
                this.rlDeviceErrorOne.setGravity(GravityCompat.START);
                this.deviceErrorOneText.setGravity(GravityCompat.START);
                findViewById(R.id.iv_device_error_one_right_arrow).setVisibility(4);
                return;
            }
            this.llKnowMoreOne.setVisibility(8);
            this.rlDeviceErrorOne.setGravity(GravityCompat.START);
            this.deviceErrorOneText.setGravity(GravityCompat.START);
            findViewById(R.id.iv_device_error_one_right_arrow).setVisibility(0);
        }
    }

    public /* synthetic */ void lambda$showCalc$24(View view) {
        P p = this.mPresenter;
        if (p != 0) {
            ((T5HomePresenter) p).ignoreN50State();
        }
    }

    public /* synthetic */ void lambda$showCalc$25(View view) {
        launchActivity(T6ConsumablesActivity.newIntent(this, this.deviceId, 21, 5, this.relatedProductsInfor));
    }

    public /* synthetic */ void lambda$showCalc$26(View view) {
        P p = this.mPresenter;
        if (p != 0) {
            ((T5HomePresenter) p).ignoreN60State();
        }
    }

    public /* synthetic */ void lambda$showCalc$27(View view) {
        launchActivity(T6ConsumablesActivity.newIntent(this, this.deviceId, 21, 6, this.relatedProductsInfor));
    }

    public /* synthetic */ void lambda$showCalc$28(List list, View view) {
        ((T5HomePresenter) this.mPresenter).ignorePetError(((DeviceError) list.get(0)).getPetErrorInfo(), 2);
    }

    public /* synthetic */ void lambda$showCalc$29(List list, View view) {
        if (this.t5Record.getDeviceShared() == null) {
            MedicalConversionItem medicalConversionItem = new MedicalConversionItem();
            medicalConversionItem.setContent(((DeviceError) list.get(0)).getPetErrorInfo().getDesc());
            medicalConversionItem.setPetId(((DeviceError) list.get(0)).getPetErrorInfo().getPetId());
            medicalConversionItem.setPetName(((DeviceError) list.get(0)).getPetErrorInfo().getPetName());
            DoctorUtils.getInstance().askDoctor(this, medicalConversionItem, null, 0L, 0);
            return;
        }
        PetkitToast.showTopToast(this, getResources().getString(R.string.Shared_device_is_limited_tip), 0, 0);
    }

    public /* synthetic */ void lambda$showCalc$30(View view) {
        ((T5HomePresenter) this.mPresenter).ignorePh();
    }

    public /* synthetic */ void lambda$showCalc$31(View view) {
        if (this.t5Record.getDeviceShared() == null) {
            MedicalConversionItem medicalConversionItem = new MedicalConversionItem();
            medicalConversionItem.setContent(getString(R.string.T6_timeline_recognize_urine_ph_tip));
            DoctorUtils.getInstance().askDoctor(this, medicalConversionItem, null, this.deviceId, this.deviceType);
            return;
        }
        PetkitToast.showTopToast(this, getResources().getString(R.string.Shared_device_is_limited_tip), 0, 0);
    }

    public /* synthetic */ void lambda$showCalc$32(View view) {
        P p = this.mPresenter;
        if (p != 0) {
            ((T5HomePresenter) p).ignoreN50State();
        }
    }

    public /* synthetic */ void lambda$showCalc$33(View view) {
        launchActivity(T6ConsumablesActivity.newIntent(this, this.deviceId, 21, 5, this.relatedProductsInfor));
    }

    public /* synthetic */ void lambda$showCalc$34(View view) {
        P p = this.mPresenter;
        if (p != 0) {
            ((T5HomePresenter) p).ignoreN60State();
        }
    }

    public /* synthetic */ void lambda$showCalc$35(View view) {
        launchActivity(T6ConsumablesActivity.newIntent(this, this.deviceId, 21, 6, this.relatedProductsInfor));
    }

    public /* synthetic */ void lambda$showCalc$36(List list, View view) {
        showNoRemindConfirmWindow(((DeviceError) list.get(0)).getPetErrorInfo(), 2);
    }

    public /* synthetic */ void lambda$showCalc$37(List list, View view) {
        if (this.t5Record.getDeviceShared() == null) {
            MedicalConversionItem medicalConversionItem = new MedicalConversionItem();
            medicalConversionItem.setContent(((DeviceError) list.get(0)).getPetErrorInfo().getDesc());
            medicalConversionItem.setPetId(((DeviceError) list.get(0)).getPetErrorInfo().getPetId());
            medicalConversionItem.setPetName(((DeviceError) list.get(0)).getPetErrorInfo().getPetName());
            DoctorUtils.getInstance().askDoctor(this, medicalConversionItem, null, 0L, 0);
            return;
        }
        PetkitToast.showTopToast(this, getResources().getString(R.string.Shared_device_is_limited_tip), 0, 0);
    }

    public /* synthetic */ void lambda$showCalc$38(View view) {
        ((T5HomePresenter) this.mPresenter).ignorePh();
    }

    public /* synthetic */ void lambda$showCalc$39(View view) {
        if (this.t5Record.getDeviceShared() == null) {
            MedicalConversionItem medicalConversionItem = new MedicalConversionItem();
            medicalConversionItem.setContent(getString(R.string.T6_timeline_recognize_urine_ph_tip));
            DoctorUtils.getInstance().askDoctor(this, medicalConversionItem, null, this.deviceId, this.deviceType);
            return;
        }
        PetkitToast.showTopToast(this, getResources().getString(R.string.Shared_device_is_limited_tip), 0, 0);
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T5HomeContract.View
    public void showForceUpgradeWindow() {
        this.otaDisposable = Observable.timer(500L, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$$ExternalSyntheticLambda27
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.lambda$showForceUpgradeWindow$40((Long) obj);
            }
        });
    }

    public /* synthetic */ void lambda$showForceUpgradeWindow$40(Long l) throws Exception {
        if (this.t5Record.getDeviceShared() != null) {
            OtaPromptWindow otaPromptWindow = this.otaPromptWindow;
            if (otaPromptWindow == null || !otaPromptWindow.isShowing()) {
                OtaPromptWindow otaPromptWindow2 = new OtaPromptWindow(this, getResources().getString(R.string.Ota_prompt_share), getResources().getString(R.string.I_know), new OtaPromptWindow.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.31
                    public AnonymousClass31() {
                    }

                    @Override // com.petkit.android.activities.petkitBleDevice.widget.OtaPromptWindow.OnClickListener
                    public void onCancel() {
                        T5HomeActivity.this.killMyself();
                    }

                    @Override // com.petkit.android.activities.petkitBleDevice.widget.OtaPromptWindow.OnClickListener
                    public void onConfirm() {
                        T5HomeActivity.this.killMyself();
                    }

                    @Override // com.petkit.android.activities.petkitBleDevice.widget.OtaPromptWindow.OnClickListener
                    public void onBack() {
                        T5HomeActivity.this.killMyself();
                    }
                }, true);
                this.otaPromptWindow = otaPromptWindow2;
                otaPromptWindow2.setTouchOutsideAble(false);
                this.otaPromptWindow.setCheckboxVisibility(8);
                this.otaPromptWindow.show(getWindow().getDecorView());
                return;
            }
            return;
        }
        OtaPromptWindow otaPromptWindow3 = this.otaPromptWindow;
        if (otaPromptWindow3 == null || !otaPromptWindow3.isShowing()) {
            OtaPromptWindow otaPromptWindow4 = new OtaPromptWindow(this, getResources().getString(R.string.Ota_prompt), getResources().getString(R.string.Update_right_now), getResources().getString(R.string.Close), new OtaPromptWindow.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.32
                public AnonymousClass32() {
                }

                @Override // com.petkit.android.activities.petkitBleDevice.widget.OtaPromptWindow.OnClickListener
                public void onCancel() {
                    T5HomeActivity.this.killMyself();
                }

                @Override // com.petkit.android.activities.petkitBleDevice.widget.OtaPromptWindow.OnClickListener
                public void onConfirm() {
                    T5HomeActivity t5HomeActivity = T5HomeActivity.this;
                    t5HomeActivity.launchActivity(T6SettingOtaActivity.newIntent(t5HomeActivity, t5HomeActivity.deviceId, T5HomeActivity.this.deviceType));
                    T5HomeActivity.this.killMyself();
                }

                @Override // com.petkit.android.activities.petkitBleDevice.widget.OtaPromptWindow.OnClickListener
                public void onBack() {
                    T5HomeActivity.this.killMyself();
                }
            }, true);
            this.otaPromptWindow = otaPromptWindow4;
            otaPromptWindow4.setTouchOutsideAble(false);
            this.otaPromptWindow.setCheckboxVisibility(8);
            this.otaPromptWindow.show(getWindow().getDecorView());
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$31 */
    public class AnonymousClass31 implements OtaPromptWindow.OnClickListener {
        public AnonymousClass31() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.widget.OtaPromptWindow.OnClickListener
        public void onCancel() {
            T5HomeActivity.this.killMyself();
        }

        @Override // com.petkit.android.activities.petkitBleDevice.widget.OtaPromptWindow.OnClickListener
        public void onConfirm() {
            T5HomeActivity.this.killMyself();
        }

        @Override // com.petkit.android.activities.petkitBleDevice.widget.OtaPromptWindow.OnClickListener
        public void onBack() {
            T5HomeActivity.this.killMyself();
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$32 */
    public class AnonymousClass32 implements OtaPromptWindow.OnClickListener {
        public AnonymousClass32() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.widget.OtaPromptWindow.OnClickListener
        public void onCancel() {
            T5HomeActivity.this.killMyself();
        }

        @Override // com.petkit.android.activities.petkitBleDevice.widget.OtaPromptWindow.OnClickListener
        public void onConfirm() {
            T5HomeActivity t5HomeActivity = T5HomeActivity.this;
            t5HomeActivity.launchActivity(T6SettingOtaActivity.newIntent(t5HomeActivity, t5HomeActivity.deviceId, T5HomeActivity.this.deviceType));
            T5HomeActivity.this.killMyself();
        }

        @Override // com.petkit.android.activities.petkitBleDevice.widget.OtaPromptWindow.OnClickListener
        public void onBack() {
            T5HomeActivity.this.killMyself();
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$33 */
    public class AnonymousClass33 implements NormalCenterTipWindow.OnClickOk {
        public final /* synthetic */ PetErrorInfo val$info;
        public final /* synthetic */ int val$type;

        @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
        public void onCancelClick() {
        }

        public AnonymousClass33(PetErrorInfo petErrorInfo, int i) {
            petErrorInfo = petErrorInfo;
            i = i;
        }

        @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
        public void onOkClick() {
            ((T5HomePresenter) ((BaseActivity) T5HomeActivity.this).mPresenter).ignorePetError(petErrorInfo, i);
        }
    }

    public void showNoRemindConfirmWindow(PetErrorInfo petErrorInfo, int i) {
        NormalCenterTipWindow normalCenterTipWindow = new NormalCenterTipWindow(this, new NormalCenterTipWindow.OnClickOk() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.33
            public final /* synthetic */ PetErrorInfo val$info;
            public final /* synthetic */ int val$type;

            @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
            public void onCancelClick() {
            }

            public AnonymousClass33(PetErrorInfo petErrorInfo2, int i2) {
                petErrorInfo = petErrorInfo2;
                i = i2;
            }

            @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
            public void onOkClick() {
                ((T5HomePresenter) ((BaseActivity) T5HomeActivity.this).mPresenter).ignorePetError(petErrorInfo, i);
            }
        }, (String) null, petErrorInfo2.getAlertTip());
        this.petErrorNoRemindWindow = normalCenterTipWindow;
        if (normalCenterTipWindow.isShowing()) {
            return;
        }
        this.petErrorNoRemindWindow.show(getWindow().getDecorView());
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T5HomeContract.View
    @SuppressLint({"CheckResult"})
    public void showRecommendUpgradeWindow(final OtaResult otaResult) {
        T6Record t6Record;
        T6Record t6Record2;
        if (DataHelper.getBooleanSF(this, (this.deviceId + 21) + otaResult.getVersion())) {
            if (DataHelper.getBooleanSF(this, 21 + String.valueOf(this.deviceId) + Constants.DEVICE_BIND_COMPLETE) || ((t6Record2 = this.t5Record) != null && t6Record2.getSettings().getSandType() == 0)) {
                launchActivity(DeviceSetInfoActivity.newIntent(this, this.deviceId, 21, true));
                killMyself();
                return;
            } else {
                ((T5HomePresenter) this.mPresenter).checkInitWindow(2);
                return;
            }
        }
        if (!this.isShowRecommendUpgradeWindow) {
            this.isShowRecommendUpgradeWindow = true;
            Observable.timer(500L, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f$0.lambda$showRecommendUpgradeWindow$41(otaResult, (Long) obj);
                }
            });
            return;
        }
        if (DataHelper.getBooleanSF(this, 21 + String.valueOf(this.deviceId) + Constants.DEVICE_BIND_COMPLETE) || ((t6Record = this.t5Record) != null && t6Record.getSettings().getSandType() == 0)) {
            launchActivity(DeviceSetInfoActivity.newIntent(this, this.deviceId, 21, true));
            killMyself();
        } else {
            ((T5HomePresenter) this.mPresenter).checkInitWindow(2);
        }
    }

    public /* synthetic */ void lambda$showRecommendUpgradeWindow$41(OtaResult otaResult, Long l) throws Exception {
        if (this.t5Record.getDeviceShared() == null) {
            OtaPromptWindow otaPromptWindow = this.otaPromptWindow;
            if (otaPromptWindow == null || !otaPromptWindow.isShowing()) {
                OtaPromptWindow otaPromptWindow2 = new OtaPromptWindow(this, getResources().getString(R.string.Ota_prompt), getResources().getString(R.string.Update_right_now), getResources().getString(R.string.Cancel), new OtaPromptWindow.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.34
                    public final /* synthetic */ OtaResult val$otaCheckResult;

                    @Override // com.petkit.android.activities.petkitBleDevice.widget.OtaPromptWindow.OnClickListener
                    public void onBack() {
                    }

                    public AnonymousClass34(OtaResult otaResult2) {
                        otaResult = otaResult2;
                    }

                    @Override // com.petkit.android.activities.petkitBleDevice.widget.OtaPromptWindow.OnClickListener
                    public void onCancel() {
                        if (T5HomeActivity.this.otaPromptWindow.isChecked()) {
                            DataHelper.setBooleanSF(T5HomeActivity.this, (T5HomeActivity.this.deviceId + 21) + otaResult.getVersion(), Boolean.TRUE);
                        }
                        if (!DataHelper.getBooleanSF(T5HomeActivity.this, 21 + String.valueOf(T5HomeActivity.this.deviceId) + Constants.DEVICE_BIND_COMPLETE) && (T5HomeActivity.this.t5Record == null || T5HomeActivity.this.t5Record.getSettings().getSandType() != 0)) {
                            ((T5HomePresenter) ((BaseActivity) T5HomeActivity.this).mPresenter).checkInitWindow(2);
                            return;
                        }
                        T5HomeActivity t5HomeActivity = T5HomeActivity.this;
                        t5HomeActivity.launchActivity(DeviceSetInfoActivity.newIntent(t5HomeActivity, t5HomeActivity.deviceId, 21, true));
                        T5HomeActivity.this.killMyself();
                    }

                    @Override // com.petkit.android.activities.petkitBleDevice.widget.OtaPromptWindow.OnClickListener
                    public void onConfirm() {
                        if (T5HomeActivity.this.otaPromptWindow.isChecked()) {
                            DataHelper.setBooleanSF(T5HomeActivity.this, (T5HomeActivity.this.deviceId + 21) + otaResult.getVersion(), Boolean.TRUE);
                        }
                        T5HomeActivity t5HomeActivity = T5HomeActivity.this;
                        t5HomeActivity.launchActivity(T6SettingOtaActivity.newIntent(t5HomeActivity, t5HomeActivity.deviceId, T5HomeActivity.this.deviceType));
                    }
                }, true);
                this.otaPromptWindow = otaPromptWindow2;
                otaPromptWindow2.setTouchOutsideAble(true);
                this.otaPromptWindow.setCheckboxVisibility(0);
                this.otaPromptWindow.show(getWindow().getDecorView());
            }
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$34 */
    public class AnonymousClass34 implements OtaPromptWindow.OnClickListener {
        public final /* synthetic */ OtaResult val$otaCheckResult;

        @Override // com.petkit.android.activities.petkitBleDevice.widget.OtaPromptWindow.OnClickListener
        public void onBack() {
        }

        public AnonymousClass34(OtaResult otaResult2) {
            otaResult = otaResult2;
        }

        @Override // com.petkit.android.activities.petkitBleDevice.widget.OtaPromptWindow.OnClickListener
        public void onCancel() {
            if (T5HomeActivity.this.otaPromptWindow.isChecked()) {
                DataHelper.setBooleanSF(T5HomeActivity.this, (T5HomeActivity.this.deviceId + 21) + otaResult.getVersion(), Boolean.TRUE);
            }
            if (!DataHelper.getBooleanSF(T5HomeActivity.this, 21 + String.valueOf(T5HomeActivity.this.deviceId) + Constants.DEVICE_BIND_COMPLETE) && (T5HomeActivity.this.t5Record == null || T5HomeActivity.this.t5Record.getSettings().getSandType() != 0)) {
                ((T5HomePresenter) ((BaseActivity) T5HomeActivity.this).mPresenter).checkInitWindow(2);
                return;
            }
            T5HomeActivity t5HomeActivity = T5HomeActivity.this;
            t5HomeActivity.launchActivity(DeviceSetInfoActivity.newIntent(t5HomeActivity, t5HomeActivity.deviceId, 21, true));
            T5HomeActivity.this.killMyself();
        }

        @Override // com.petkit.android.activities.petkitBleDevice.widget.OtaPromptWindow.OnClickListener
        public void onConfirm() {
            if (T5HomeActivity.this.otaPromptWindow.isChecked()) {
                DataHelper.setBooleanSF(T5HomeActivity.this, (T5HomeActivity.this.deviceId + 21) + otaResult.getVersion(), Boolean.TRUE);
            }
            T5HomeActivity t5HomeActivity = T5HomeActivity.this;
            t5HomeActivity.launchActivity(T6SettingOtaActivity.newIntent(t5HomeActivity, t5HomeActivity.deviceId, T5HomeActivity.this.deviceType));
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T5HomeContract.View
    public void noUpgrade() {
        T6Record t6Record;
        if (DataHelper.getBooleanSF(this, 21 + String.valueOf(this.deviceId) + Constants.DEVICE_BIND_COMPLETE) || ((t6Record = this.t5Record) != null && t6Record.getSettings().getSandType() == 0)) {
            launchActivity(DeviceSetInfoActivity.newIntent(this, this.deviceId, 21, true));
            killMyself();
        } else {
            ((T5HomePresenter) this.mPresenter).checkInitWindow(2);
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T5HomeContract.View
    public void setTitleStatus(T6Record t6Record) {
        this.ibSetting.setImageResource(DeviceCenterUtils.isT6NeedOtaById(t6Record.getDeviceId()) ? R.drawable.black_setting_with_notify_flag : R.drawable.black_setting_icon);
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T5HomeContract.View
    public void refreshHighLight(HighLightRecordRsp highLightRecordRsp) {
        T6VlogRecordAdapter t6VlogRecordAdapter = new T6VlogRecordAdapter(this, this.t5Record, this.deviceType);
        this.t6VlogRecordAdapter = t6VlogRecordAdapter;
        t6VlogRecordAdapter.append((List) highLightRecordRsp.getItems());
        this.rvHighlights.setAdapter(this.t6VlogRecordAdapter);
        this.t6VlogRecordAdapter.setOnDailyHighlightItemClickListener(new BasePetkitHighlightAdapter.OnDailyHighlightItemClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.35
            @Override // com.petkit.android.activities.petkitBleDevice.adapter.BasePetkitHighlightAdapter.OnDailyHighlightItemClickListener
            public void onEmptyClick() {
            }

            public AnonymousClass35() {
            }

            @Override // com.petkit.android.activities.petkitBleDevice.adapter.BasePetkitHighlightAdapter.OnDailyHighlightItemClickListener
            public void onPlayBtnClick(HighlightRecord highlightRecord) {
                T5HomeActivity t5HomeActivity = T5HomeActivity.this;
                t5HomeActivity.startActivityForResult(MediaDisplayActivity.newIntent(t5HomeActivity, highlightRecord, 3, t5HomeActivity.deviceId, T5HomeActivity.this.deviceType), 5);
            }

            @Override // com.petkit.android.activities.petkitBleDevice.adapter.BasePetkitHighlightAdapter.OnDailyHighlightItemClickListener
            public void onMarkVlogClick(HighlightRecord highlightRecord) {
                ((T5HomePresenter) ((BaseActivity) T5HomeActivity.this).mPresenter).getHighlightM3U8(highlightRecord.getId(), T5HomeActivity.this.deviceType);
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$35 */
    public class AnonymousClass35 implements BasePetkitHighlightAdapter.OnDailyHighlightItemClickListener {
        @Override // com.petkit.android.activities.petkitBleDevice.adapter.BasePetkitHighlightAdapter.OnDailyHighlightItemClickListener
        public void onEmptyClick() {
        }

        public AnonymousClass35() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.adapter.BasePetkitHighlightAdapter.OnDailyHighlightItemClickListener
        public void onPlayBtnClick(HighlightRecord highlightRecord) {
            T5HomeActivity t5HomeActivity = T5HomeActivity.this;
            t5HomeActivity.startActivityForResult(MediaDisplayActivity.newIntent(t5HomeActivity, highlightRecord, 3, t5HomeActivity.deviceId, T5HomeActivity.this.deviceType), 5);
        }

        @Override // com.petkit.android.activities.petkitBleDevice.adapter.BasePetkitHighlightAdapter.OnDailyHighlightItemClickListener
        public void onMarkVlogClick(HighlightRecord highlightRecord) {
            ((T5HomePresenter) ((BaseActivity) T5HomeActivity.this).mPresenter).getHighlightM3U8(highlightRecord.getId(), T5HomeActivity.this.deviceType);
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T5HomeContract.View
    public void refreshHighLightFail() {
        T6VlogRecordAdapter t6VlogRecordAdapter = new T6VlogRecordAdapter(this, this.t5Record, this.deviceType);
        this.t6VlogRecordAdapter = t6VlogRecordAdapter;
        t6VlogRecordAdapter.append((List) new ArrayList());
        this.rvHighlights.setAdapter(this.t6VlogRecordAdapter);
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T5HomeContract.View
    public void loadVideoTimeout() {
        this.t5LiveVideoView.refreshTimeoutState();
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T5HomeContract.View
    public void resetVideoTimeoutView() {
        this.t5LiveVideoView.resetTimeoutView();
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T5HomeContract.View
    public void statisticResult(List<T6StatisticResInfo> list, int i) {
        this.statisticList = list;
        this.resStatisticList = list;
        MySmartRefreshView mySmartRefreshView = this.srl;
        if (mySmartRefreshView != null) {
            mySmartRefreshView.finishRefresh();
        }
        refreshPetChart();
        this.handler.postDelayed(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$$ExternalSyntheticLambda48
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$statisticResult$42();
            }
        }, 300L);
        updateEventTime();
        if (this.isNotFirstLoad) {
            ((T5HomePresenter) this.mPresenter).getListNum(this.calendarTime);
            updateEventList(i);
        } else {
            this.llDay.setVisibility(8);
            this.isNotFirstLoad = true;
        }
    }

    public /* synthetic */ void lambda$statisticResult$42() {
        this.ivEventAnim.setVisibility(8);
        this.ivEventAnim.clearAnimation();
    }

    private void updateEventList(int i) {
        if (i == 2) {
            if (this.fragmentToilet.isAdded()) {
                this.fragmentToilet.selectDate(this.calendarTime, 0);
                return;
            }
            return;
        }
        if (i == 1) {
            if (this.fragmentAll.isAdded()) {
                this.fragmentAll.selectDate(this.calendarTime, 2);
            }
            if (this.fragmentToilet.isAdded()) {
                this.fragmentToilet.selectDate(this.calendarTime, 0);
            }
            if (this.toiletErrorFragment.isAdded()) {
                this.toiletErrorFragment.selectDate(this.calendarTime, 3);
                return;
            }
            return;
        }
        if (i == 3) {
            if (this.fragmentAll.isAdded()) {
                this.fragmentAll.selectDate(this.calendarTime, 2);
            }
            if (this.toiletErrorFragment.isAdded()) {
                this.toiletErrorFragment.selectDate(this.calendarTime, 3);
            }
            if (this.fragmentToilet.isAdded()) {
                this.fragmentToilet.selectDate(this.calendarTime, 0);
                return;
            }
            return;
        }
        if (this.fragmentAll.isAdded()) {
            this.fragmentAll.selectDate(this.calendarTime, 2);
        }
        if (this.fragmentToilet.isAdded()) {
            this.fragmentToilet.selectDate(this.calendarTime, 0);
        }
        if (this.fragmentPet.isAdded()) {
            this.fragmentPet.selectDate(this.calendarTime, 1);
        }
        if (this.toiletErrorFragment.isAdded()) {
            this.toiletErrorFragment.selectDate(this.calendarTime, 3);
        }
    }

    public void dateSelected(String str) {
        long millisecondByDateString = DateUtil.getMillisecondByDateString(str);
        this.calendarTime = T6Utils.getCurrentZoneTime(this.t5Record, this.deviceId, millisecondByDateString, this.deviceType);
        PetkitLog.d("t6Time", millisecondByDateString + "  c  " + this.calendarTime);
        ((T5HomePresenter) this.mPresenter).getStatistic(this.calendarTime, 0);
        updateEventTime();
        ((T5HomePresenter) this.mPresenter).getListNum(this.calendarTime);
        updateEventList(0);
        DatePickerWindow datePickerWindow = this.datePickerWindow;
        if (datePickerWindow != null) {
            datePickerWindow.dismiss();
        }
    }

    private void updateEventTime() {
        if (DateUtil.isSameDay(this.calendarTime, this.t5Record.getActualTimeZone())) {
            this.tvTodayEvent.setText(getResources().getString(R.string.Today_Events_records));
            this.tvTodayToiletStr.setText(getResources().getString(R.string.Today_Events_records));
            this.title = getResources().getString(R.string.Today_Events_records);
            if (this.rlTopTab.getVisibility() == 0) {
                this.toolbarTitle.setText(this.title);
            } else {
                this.toolbarTitle.setText(this.deviceName);
            }
            hideAllRedPoint();
            return;
        }
        this.tvTodayEvent.setText(DateUtil.long2str(this.calendarTime, "yyyy-MM-dd"));
        this.tvTodayToiletStr.setText(DateUtil.long2str(this.calendarTime, "yyyy-MM-dd"));
        this.title = DateUtil.long2str(this.calendarTime, "yyyy-MM-dd");
        if (this.rlTopTab.getVisibility() == 0) {
            this.toolbarTitle.setText(this.title);
        } else {
            this.toolbarTitle.setText(this.deviceName);
        }
        hideAllRedPoint();
        showRedToilet(false);
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T5HomeContract.View
    public void showCalendarDialog(String str, List<CountBean> list) {
        DatePickerWindow datePickerWindow = this.datePickerWindow;
        if (datePickerWindow != null && datePickerWindow.isShowing()) {
            this.datePickerWindow.getDatePicker().refreshT6Data(this.t5Record.getCreatedAt(), this.deviceType, T6Utils.getStartAndEndDateStr2(this.calendarTime)[0], this.deviceId, list);
        } else {
            DatePickerWindow datePickerWindow2 = new DatePickerWindow(this, this.t5Record.getCreatedAt(), str, 21, this.deviceId, new BasePetkitDeviceDatePickerView.OnCalendarChangeListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$$ExternalSyntheticLambda24
                @Override // com.petkit.android.activities.petkitBleDevice.widget.datepicker.BasePetkitDeviceDatePickerView.OnCalendarChangeListener
                public final void pageChange(int i) {
                    this.f$0.lambda$showCalendarDialog$43(i);
                }
            }, true, list, new BasePetkitDeviceDatePickerView.OnCalendarSelectListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$$ExternalSyntheticLambda25
                @Override // com.petkit.android.activities.petkitBleDevice.widget.datepicker.BasePetkitDeviceDatePickerView.OnCalendarSelectListener
                public final void dateSelect(String str2) {
                    this.f$0.dateSelected(str2);
                }
            });
            this.datePickerWindow = datePickerWindow2;
            datePickerWindow2.setTitleText(getString(R.string.All_cats_data));
            this.datePickerWindow.showAtLocation(getWindow().getDecorView(), 80, 0, 0);
        }
    }

    public /* synthetic */ void lambda$showCalendarDialog$43(int i) {
        this.monthOffset = i;
        ((T5HomePresenter) this.mPresenter).getDataPicker(i);
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T5HomeContract.View
    public void showEventListNum(T6EventStaticInfo t6EventStaticInfo) {
        if (t6EventStaticInfo == null) {
            return;
        }
        this.tvAllTab.setText(getListNumStr(getResources().getString(R.string.Whole), t6EventStaticInfo.getTotal()));
        this.tvToiletTab.setText(getListNumStr(getResources().getString(R.string.Go_to_the_toilet), t6EventStaticInfo.getToilet()));
        this.tvPetTab.setText(getListNumStr(getResources().getString(R.string.Pet), t6EventStaticInfo.getPet()));
        this.toiletErrorEventNum = t6EventStaticInfo.getUnusualToilet();
        this.tvToiletTabError.setText(getListNumStr(getResources().getString(R.string.Unusual_event_title), t6EventStaticInfo.getUnusualToilet()));
        this.tvAllTab2.setText(getListNumStr(getResources().getString(R.string.Whole), t6EventStaticInfo.getTotal()));
        this.tvToiletTab2.setText(getListNumStr(getResources().getString(R.string.Go_to_the_toilet), t6EventStaticInfo.getToilet()));
        this.tvPetTab2.setText(getListNumStr(getResources().getString(R.string.Pet), t6EventStaticInfo.getPet()));
        this.tvToiletTabError2.setText(getListNumStr(getResources().getString(R.string.Unusual_event_title), t6EventStaticInfo.getUnusualToilet()));
        setTextUi(this.currentPosition, this.tvAllTab, this.tvToiletTab, this.tvPetTab, this.tvToiletTabError);
        setTopTextUi(this.currentPosition, this.tvAllTab2, this.tvToiletTab2, this.tvPetTab2, this.tvToiletTabError2);
    }

    private SpannableString getListNumStr(String str, int i) {
        if (i == 0) {
            return new SpannableString(str);
        }
        String str2 = str + " " + i;
        SpannableString spannableString = new SpannableString(str2);
        spannableString.setSpan(new AbsoluteSizeSpan(11, true), str2.length() - String.valueOf(i).length(), str2.length(), 33);
        return spannableString;
    }

    @Subscriber
    public void cloudServiceUpdate(CloudServiceUpdateMsg cloudServiceUpdateMsg) {
        if (cloudServiceUpdateMsg.getDeviceType() == this.deviceType) {
            ((T5HomePresenter) this.mPresenter).initT5DeviceDetail();
        }
    }

    @Subscriber
    public void deleteEvent(DeleteEvent deleteEvent) {
        if (deleteEvent == null || deleteEvent.getEventId().equals("")) {
            return;
        }
        ((T5HomePresenter) this.mPresenter).getListNum(this.calendarTime);
        if (this.fragmentAll.isAdded()) {
            if (deleteEvent.isFullScreen()) {
                this.fragmentAll.refreshEvent(this.calendarTime);
            } else {
                this.fragmentAll.refreshAdapter(deleteEvent.getEventId(), deleteEvent.getEventType());
            }
        }
        if (this.fragmentToilet.isAdded()) {
            if (deleteEvent.isFullScreen()) {
                this.fragmentToilet.refreshEvent(this.calendarTime);
            } else {
                this.fragmentToilet.refreshAdapter(deleteEvent.getEventId(), deleteEvent.getEventType());
            }
        }
        if (this.fragmentPet.isAdded()) {
            if (deleteEvent.isFullScreen()) {
                this.fragmentPet.refreshEvent(this.calendarTime);
            } else {
                this.fragmentPet.refreshAdapter(deleteEvent.getEventId(), deleteEvent.getEventType());
            }
        }
        if (this.toiletErrorFragment.isAdded()) {
            if (deleteEvent.isFullScreen()) {
                this.toiletErrorFragment.refreshEvent(this.calendarTime);
            } else {
                this.toiletErrorFragment.refreshAdapter(deleteEvent.getEventId(), deleteEvent.getEventType());
            }
        }
        if (deleteEvent.getEventType() == 10) {
            ((T5HomePresenter) this.mPresenter).getStatistic(this.calendarTime, 1);
        }
    }

    @Subscriber
    public void changePet(UpdatePetEvent updatePetEvent) {
        if (updatePetEvent == null) {
            return;
        }
        if (updatePetEvent.getBatch().equals("1")) {
            if (this.fragmentToilet.isAdded()) {
                this.fragmentToilet.refreshEvent(this.calendarTime);
            }
            if (this.fragmentAll.isAdded()) {
                this.fragmentAll.refreshEvent(this.calendarTime);
            }
            if (this.fragmentPet.isAdded() && updatePetEvent.isAppear()) {
                this.fragmentPet.refreshEvent(this.calendarTime);
                return;
            }
            return;
        }
        if (this.fragmentToilet.isAdded()) {
            this.fragmentToilet.updateOnePet(updatePetEvent.getOldPetId(), updatePetEvent.getPetId(), updatePetEvent.getEventId());
        }
        if (this.fragmentAll.isAdded()) {
            this.fragmentAll.updateOnePet(updatePetEvent.getOldPetId(), updatePetEvent.getPetId(), updatePetEvent.getEventId());
        }
        if (this.fragmentPet.isAdded() && updatePetEvent.isAppear()) {
            this.fragmentPet.updateOnePet(updatePetEvent.getOldPetId(), updatePetEvent.getPetId(), updatePetEvent.getEventId());
        }
    }

    @Subscriber
    public void updatePetSubEvent(UpdatePetCleanEvent updatePetCleanEvent) {
        if (updatePetCleanEvent != null) {
            if (this.fragmentToilet.isAdded()) {
                this.fragmentToilet.updateOnePetByClean(updatePetCleanEvent);
            }
            if (this.fragmentAll.isAdded()) {
                this.fragmentAll.updateOnePetByClean(updatePetCleanEvent);
            }
        }
    }

    @Override // com.jess.arms.base.BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        P p = this.mPresenter;
        if (p != 0 && ((T5HomePresenter) p).getLiveService() != null) {
            if (this.t5Record != null && CommonUtil.getLong(r0.getFirmware()) < 600.0d) {
                ((T5HomePresenter) this.mPresenter).getLiveService().stopP2PService();
            } else {
                ((RtcService) ((T5HomePresenter) this.mPresenter).getLiveService()).destroy();
            }
        }
        T5LiveViewPagerAdapter t5LiveViewPagerAdapter = this.t5LiveViewPagerAdapter;
        if (t5LiveViewPagerAdapter != null) {
            t5LiveViewPagerAdapter.isNeedShowAni = false;
        }
        Disposable disposable = this.disposable;
        if (disposable != null && !disposable.isDisposed()) {
            this.disposable.dispose();
            this.disposable = null;
        }
        Disposable disposable2 = this.otaDisposable;
        if (disposable2 != null && !disposable2.isDisposed()) {
            this.otaDisposable.dispose();
            this.otaDisposable = null;
        }
        Disposable disposable3 = this.viewPagerDisposable;
        if (disposable3 != null && !disposable3.isDisposed()) {
            this.viewPagerDisposable.dispose();
            this.viewPagerDisposable = null;
        }
        Disposable disposable4 = this.paoPaoTimer;
        if (disposable4 != null) {
            disposable4.dispose();
            this.paoPaoTimer = null;
        }
        MyHandler myHandler = this.handler;
        if (myHandler != null) {
            myHandler.removeCallbacksAndMessages(null);
            this.handler = null;
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.widget.BleDeviceHomeOfflineWarnWindow.OfflineClickListener
    public void onClickSeeDetails() {
        launchActivity(BleDeviceWifiSettingActivity.newIntent(this, this.t5Record.getDeviceId(), 21));
    }

    @Override // com.petkit.android.activities.petkitBleDevice.widget.BleDeviceHomeOfflineWarnWindow.OfflineClickListener
    public void onClickReset() {
        this.serviceMaybeChanged = true;
        launchActivity(BleDeviceWifiSettingActivity.newIntent(this, this.deviceId, this.deviceType));
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.T5LiveVideoView.T6LiveVideoViewStateListener
    public void openDevice() {
        ((T5HomePresenter) this.mPresenter).turnOnOrOffDevice(true);
        this.t5LiveVideoView.setVisibility(0);
        this.isNeedTurnOnDeviceAni = true;
        this.isAnimationRunning = false;
        this.t6AnimUtil.resetScaleAnim(this.ivDeviceOpen);
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.T5LiveVideoView.T6LiveVideoViewStateListener
    public void turnOnCamera() {
        if (this.t5Record.getSettings().getCamera() == 0) {
            ((T5HomePresenter) this.mPresenter).turnOnCamera();
        } else if (this.t5Record.getState().getCameraStatus() == 0) {
            showTurnOnCameraWindow();
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.T5LiveVideoView.T6LiveVideoViewStateListener
    public void turnOnCamera5Minutes() {
        ((T5HomePresenter) this.mPresenter).temporaryOpenCamera();
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.T5LiveVideoView.T6LiveVideoViewStateListener
    public void startLiveVideo() {
        this.clEyeAnimBackground.setVisibility(0);
        ((T5HomePresenter) this.mPresenter).startLiveVideo(this.t5LiveVideoView.getVideoPlayerView());
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.T5LiveVideoView.T6LiveVideoViewStateListener
    public void liveRetry() {
        ((T5HomePresenter) this.mPresenter).startLiveVideo(this.t5LiveVideoView.getVideoPlayerView());
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.T5LiveVideoView.T6LiveVideoViewStateListener
    public void stopLiveVideo() {
        ((T5HomePresenter) this.mPresenter).stopLiveVideo();
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.T5LiveVideoView.T6LiveVideoViewStateListener
    public void liveViewOnClick() {
        this.clickIntoFullscreen = true;
        startActivity(T6LiveFullscreenActivity.newIntent(this, this.deviceId, this.deviceType));
        overridePendingTransition(0, 0);
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.T5LiveVideoView.T6LiveVideoViewStateListener
    public void normalLive() {
        if (CommonUtil.getLong(this.t5Record.getFirmware()) < 600.0d) {
            if (CommonUtil.isActivityForeground(this, getClass().getName())) {
                ((T5HomePresenter) this.mPresenter).startLiveVideo(this.t5LiveVideoView.getVideoPlayerView());
                return;
            }
            return;
        }
        ((T5HomePresenter) this.mPresenter).firstConnectRtc(this.t5LiveVideoView.getVideoPlayerView());
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.VideoPlayerViewListener
    public void onVideoClick() {
        T6Record t6Record = this.t5Record;
        if (t6Record != null && t6Record.getState().getPower() == 1 && this.firstRemoteVideoFrame) {
            this.clickIntoFullscreen = true;
            startActivity(T6LiveFullscreenActivity.newIntent(this, this.deviceId, this.deviceType));
            overridePendingTransition(0, 0);
        }
    }

    private void startPromoteViewAnim() {
        PromoteView promoteView = this.promoteView;
        if (promoteView == null || promoteView.getVisibility() == 8) {
            return;
        }
        this.startCollAnim = true;
        TranslateAnimation translateAnimation = new TranslateAnimation(1, 0.0f, 1, 0.7f, 0, 0.0f, 0, 0.0f);
        translateAnimation.setFillAfter(true);
        translateAnimation.setDuration(400L);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.36
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
            }

            public AnonymousClass36() {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                T5HomeActivity.this.isHidePromote = true;
            }
        });
        this.promoteView.startAnimation(translateAnimation);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$36 */
    public class AnonymousClass36 implements Animation.AnimationListener {
        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationRepeat(Animation animation) {
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationStart(Animation animation) {
        }

        public AnonymousClass36() {
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationEnd(Animation animation) {
            T5HomeActivity.this.isHidePromote = true;
        }
    }

    private void endPromoteViewAnim() {
        PromoteView promoteView = this.promoteView;
        if (promoteView == null || promoteView.getVisibility() == 8) {
            return;
        }
        this.startExpandAnim = true;
        TranslateAnimation translateAnimation = new TranslateAnimation(1, 0.7f, 1, 0.0f, 0, 0.0f, 0, 0.0f);
        translateAnimation.setFillAfter(true);
        translateAnimation.setDuration(400L);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.37
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
            }

            public AnonymousClass37() {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                T5HomeActivity.this.startCollAnim = false;
                T5HomeActivity.this.startExpandAnim = false;
                T5HomeActivity.this.isHidePromote = false;
            }
        });
        this.promoteView.startAnimation(translateAnimation);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$37 */
    public class AnonymousClass37 implements Animation.AnimationListener {
        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationRepeat(Animation animation) {
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationStart(Animation animation) {
        }

        public AnonymousClass37() {
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationEnd(Animation animation) {
            T5HomeActivity.this.startCollAnim = false;
            T5HomeActivity.this.startExpandAnim = false;
            T5HomeActivity.this.isHidePromote = false;
        }
    }

    private void setChartData() {
        T6Record t6Record = this.t5Record;
        if (t6Record != null && t6Record.getDeviceShared() != null) {
            this.llPets.setVisibility(4);
        }
        RelativeLayout relativeLayout = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.chart_view_pager, (ViewGroup) null);
        TextView textView = (TextView) relativeLayout.findViewById(R.id.tv0);
        TextView textView2 = (TextView) relativeLayout.findViewById(R.id.tv6);
        TextView textView3 = (TextView) relativeLayout.findViewById(R.id.tv12);
        TextView textView4 = (TextView) relativeLayout.findViewById(R.id.tv18);
        TextView textView5 = (TextView) relativeLayout.findViewById(R.id.tv24);
        String str = "0" + getResources().getString(R.string.Unit_hour_short);
        String str2 = com.tencent.connect.common.Constants.VIA_SHARE_TYPE_INFO + getResources().getString(R.string.Unit_hour_short);
        String str3 = "12" + getResources().getString(R.string.Unit_hour_short);
        String str4 = "18" + getResources().getString(R.string.Unit_hour_short);
        String str5 = com.tencent.connect.common.Constants.VIA_REPORT_TYPE_CHAT_AIO + getResources().getString(R.string.Unit_hour_short);
        textView.setText(str);
        textView2.setText(str2);
        textView3.setText(str3);
        textView4.setText(str4);
        textView5.setText(str5);
        this.chartView = (RelativeLayout) relativeLayout.getChildAt(0);
        this.vp.setAdapter(new PagerAdapter() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.38
            public final /* synthetic */ RelativeLayout val$v;

            @Override // androidx.viewpager.widget.PagerAdapter
            public void destroyItem(@androidx.annotation.NonNull ViewGroup viewGroup, int i, @androidx.annotation.NonNull Object obj) {
            }

            @Override // androidx.viewpager.widget.PagerAdapter
            public int getCount() {
                return 1;
            }

            @Override // androidx.viewpager.widget.PagerAdapter
            public boolean isViewFromObject(@androidx.annotation.NonNull View view, @androidx.annotation.NonNull Object obj) {
                return view == obj;
            }

            public AnonymousClass38(RelativeLayout relativeLayout2) {
                relativeLayout = relativeLayout2;
            }

            @Override // androidx.viewpager.widget.PagerAdapter
            @androidx.annotation.NonNull
            public Object instantiateItem(@androidx.annotation.NonNull ViewGroup viewGroup, int i) {
                viewGroup.addView(relativeLayout);
                return relativeLayout;
            }
        });
        this.vp.setDragListener(new MyChartViewPager.DragListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.39
            public AnonymousClass39() {
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.MyChartViewPager.DragListener
            public void dragLeft() {
                T5HomeActivity.this.dragChart(-1);
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.MyChartViewPager.DragListener
            public void dragRight() {
                if (DateUtil.isSameDay(T5HomeActivity.this.calendarTime, T5HomeActivity.this.t5Record.getActualTimeZone())) {
                    return;
                }
                T5HomeActivity.this.dragChart(1);
            }
        });
        dragChart(0);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$38 */
    public class AnonymousClass38 extends PagerAdapter {
        public final /* synthetic */ RelativeLayout val$v;

        @Override // androidx.viewpager.widget.PagerAdapter
        public void destroyItem(@androidx.annotation.NonNull ViewGroup viewGroup, int i, @androidx.annotation.NonNull Object obj) {
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public int getCount() {
            return 1;
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public boolean isViewFromObject(@androidx.annotation.NonNull View view, @androidx.annotation.NonNull Object obj) {
            return view == obj;
        }

        public AnonymousClass38(RelativeLayout relativeLayout2) {
            relativeLayout = relativeLayout2;
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        @androidx.annotation.NonNull
        public Object instantiateItem(@androidx.annotation.NonNull ViewGroup viewGroup, int i) {
            viewGroup.addView(relativeLayout);
            return relativeLayout;
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$39 */
    public class AnonymousClass39 implements MyChartViewPager.DragListener {
        public AnonymousClass39() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.MyChartViewPager.DragListener
        public void dragLeft() {
            T5HomeActivity.this.dragChart(-1);
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.MyChartViewPager.DragListener
        public void dragRight() {
            if (DateUtil.isSameDay(T5HomeActivity.this.calendarTime, T5HomeActivity.this.t5Record.getActualTimeZone())) {
                return;
            }
            T5HomeActivity.this.dragChart(1);
        }
    }

    public void dragChart(int i) {
        this.chartView.removeAllViews();
        Calendar calendar = Calendar.getInstance(this.t5Record.getActualTimeZone());
        calendar.setTimeInMillis(this.calendarTime);
        calendar.add(5, i);
        this.calendarTime = calendar.getTimeInMillis();
        this.ivEventAnim.setVisibility(0);
        this.t6AnimUtil.startRotateAnim(this.ivEventAnim);
        ((T5HomePresenter) this.mPresenter).getStatistic(this.calendarTime, 1);
        if (i != 0) {
            this.ivLeftArrow.setVisibility(8);
        }
    }

    @SuppressLint({"ClickableViewAccessibility"})
    private void getChartData(RelativeLayout relativeLayout, List<T6StatisticResInfo> list) {
        String name;
        if (relativeLayout == null) {
            return;
        }
        relativeLayout.removeAllViews();
        int yUi = setYUi(getMaxTime(list));
        int measuredWidth = relativeLayout.getMeasuredWidth();
        int measuredHeight = relativeLayout.getMeasuredHeight() - AppUtils.dp2px(this, 18.0f);
        final ArrayList arrayList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            T6StatisticResInfo t6StatisticResInfo = list.get(i);
            RelativeLayout relativeLayout2 = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.chart_view_data, (ViewGroup) null);
            ImageView imageView = (ImageView) relativeLayout2.getChildAt(1);
            Pet petById = UserInforUtils.getPetById(t6StatisticResInfo.getPetId());
            if (petById == null) {
                name = "";
            } else {
                name = petById.getName();
            }
            imageView.setImageResource(getWaterRes(t6StatisticResInfo.getPetId(), name));
            RelativeLayout relativeLayout3 = (RelativeLayout) relativeLayout2.getChildAt(0);
            ((TextView) relativeLayout3.getChildAt(0)).setBackgroundColor(Color.parseColor(getRecColor(t6StatisticResInfo.getPetId(), name)));
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) relativeLayout3.getLayoutParams();
            layoutParams.height = (int) (((t6StatisticResInfo.getToiletTime() * 1.0f) / yUi) * measuredHeight);
            relativeLayout3.setLayoutParams(layoutParams);
            relativeLayout.addView(relativeLayout2);
            final RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) relativeLayout2.getLayoutParams();
            layoutParams2.leftMargin = Math.max(T6Utils.getEventTimeOffset(t6StatisticResInfo.getTime(), measuredWidth - AppUtils.dp2px(this, 16.0f)), 0);
            layoutParams2.addRule(12);
            relativeLayout2.setLayoutParams(layoutParams2);
            final T6StaticInfo t6StaticInfo = new T6StaticInfo();
            t6StaticInfo.setInfo(t6StatisticResInfo);
            t6StaticInfo.setClickView(relativeLayout2);
            arrayList.add(t6StaticInfo);
            relativeLayout2.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$$ExternalSyntheticLambda3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$getChartData$44(t6StaticInfo, arrayList, layoutParams2, view);
                }
            });
            relativeLayout2.setOnTouchListener(new View.OnTouchListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$$ExternalSyntheticLambda4
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    return this.f$0.lambda$getChartData$45(t6StaticInfo, arrayList, layoutParams2, view, motionEvent);
                }
            });
        }
    }

    public /* synthetic */ void lambda$getChartData$44(T6StaticInfo t6StaticInfo, List list, RelativeLayout.LayoutParams layoutParams, View view) {
        int top = (((view.getTop() + this.rlChart.getTop()) + this.llDay.getTop()) - Math.abs(this.appBarOffset)) + AppUtils.dp2px(this, 68.0f);
        this.sortEventList = getSortList(t6StaticInfo, list);
        showArrowPop(layoutParams, top, t6StaticInfo);
    }

    public /* synthetic */ boolean lambda$getChartData$45(T6StaticInfo t6StaticInfo, List list, RelativeLayout.LayoutParams layoutParams, View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() != 0) {
            return false;
        }
        int top = (((view.getTop() + this.rlChart.getTop()) + this.llDay.getTop()) - Math.abs(this.appBarOffset)) + AppUtils.dp2px(this, 68.0f);
        this.sortEventList = getSortList(t6StaticInfo, list);
        showArrowPop(layoutParams, top, t6StaticInfo);
        return true;
    }

    private void showArrowPop(RelativeLayout.LayoutParams layoutParams, int i, T6StaticInfo t6StaticInfo) {
        setPopEventInfo(t6StaticInfo.getInfo());
        startPopAnim();
        this.rlPopContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.40
            final /* synthetic */ RelativeLayout.LayoutParams val$p;
            final /* synthetic */ int val$top;

            public AnonymousClass40(RelativeLayout.LayoutParams layoutParams2, int i2) {
                layoutParams = layoutParams2;
                i = i2;
            }

            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                T5HomeActivity.this.rlPopContent.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                T5HomeActivity.this.rlPopContent.measure(0, 0);
                T5HomeActivity t5HomeActivity = T5HomeActivity.this;
                t5HomeActivity.rlPopWidth = t5HomeActivity.rlPopContent.getMeasuredWidth();
                T5HomeActivity t5HomeActivity2 = T5HomeActivity.this;
                t5HomeActivity2.rlPopHeight = t5HomeActivity2.rlPopContent.getMeasuredHeight();
                RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) T5HomeActivity.this.ivArrow.getLayoutParams();
                layoutParams2.leftMargin = layoutParams.leftMargin + AppUtils.dp2px(T5HomeActivity.this, 68.0f);
                layoutParams2.topMargin = (i - AppUtils.dp2px(T5HomeActivity.this, 2.0f)) + 1;
                T5HomeActivity.this.ivArrow.setLayoutParams(layoutParams2);
                RelativeLayout.LayoutParams layoutParams3 = (RelativeLayout.LayoutParams) T5HomeActivity.this.rlPopContent.getLayoutParams();
                int widthPixels = layoutParams.leftMargin;
                if (AppUtils.dp2px(T5HomeActivity.this, 68.0f) + widthPixels >= T5HomeActivity.this.rlPopWidth / 2) {
                    if (T5HomeActivity.this.rlPopWidth + widthPixels > CommonUtil.getWidthPixels(T5HomeActivity.this) - AppUtils.dp2px(T5HomeActivity.this, 18.0f)) {
                        widthPixels = (CommonUtil.getWidthPixels(T5HomeActivity.this) - AppUtils.dp2px(T5HomeActivity.this, 18.0f)) - T5HomeActivity.this.rlPopWidth;
                    }
                } else {
                    widthPixels = AppUtils.dp2px(T5HomeActivity.this, 18.0f);
                }
                layoutParams3.leftMargin = widthPixels;
                layoutParams3.topMargin = (i - T5HomeActivity.this.rlPopHeight) + AppUtils.dp2px(T5HomeActivity.this, 6.0f);
                T5HomeActivity.this.rlPopContent.setLayoutParams(layoutParams3);
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$40 */
    public class AnonymousClass40 implements ViewTreeObserver.OnGlobalLayoutListener {
        final /* synthetic */ RelativeLayout.LayoutParams val$p;
        final /* synthetic */ int val$top;

        public AnonymousClass40(RelativeLayout.LayoutParams layoutParams2, int i2) {
            layoutParams = layoutParams2;
            i = i2;
        }

        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public void onGlobalLayout() {
            T5HomeActivity.this.rlPopContent.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            T5HomeActivity.this.rlPopContent.measure(0, 0);
            T5HomeActivity t5HomeActivity = T5HomeActivity.this;
            t5HomeActivity.rlPopWidth = t5HomeActivity.rlPopContent.getMeasuredWidth();
            T5HomeActivity t5HomeActivity2 = T5HomeActivity.this;
            t5HomeActivity2.rlPopHeight = t5HomeActivity2.rlPopContent.getMeasuredHeight();
            RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) T5HomeActivity.this.ivArrow.getLayoutParams();
            layoutParams2.leftMargin = layoutParams.leftMargin + AppUtils.dp2px(T5HomeActivity.this, 68.0f);
            layoutParams2.topMargin = (i - AppUtils.dp2px(T5HomeActivity.this, 2.0f)) + 1;
            T5HomeActivity.this.ivArrow.setLayoutParams(layoutParams2);
            RelativeLayout.LayoutParams layoutParams3 = (RelativeLayout.LayoutParams) T5HomeActivity.this.rlPopContent.getLayoutParams();
            int widthPixels = layoutParams.leftMargin;
            if (AppUtils.dp2px(T5HomeActivity.this, 68.0f) + widthPixels >= T5HomeActivity.this.rlPopWidth / 2) {
                if (T5HomeActivity.this.rlPopWidth + widthPixels > CommonUtil.getWidthPixels(T5HomeActivity.this) - AppUtils.dp2px(T5HomeActivity.this, 18.0f)) {
                    widthPixels = (CommonUtil.getWidthPixels(T5HomeActivity.this) - AppUtils.dp2px(T5HomeActivity.this, 18.0f)) - T5HomeActivity.this.rlPopWidth;
                }
            } else {
                widthPixels = AppUtils.dp2px(T5HomeActivity.this, 18.0f);
            }
            layoutParams3.leftMargin = widthPixels;
            layoutParams3.topMargin = (i - T5HomeActivity.this.rlPopHeight) + AppUtils.dp2px(T5HomeActivity.this, 6.0f);
            T5HomeActivity.this.rlPopContent.setLayoutParams(layoutParams3);
        }
    }

    private int getMaxTime(List<T6StatisticResInfo> list) {
        int toiletTime = 0;
        if (list.size() < 1) {
            return 0;
        }
        for (T6StatisticResInfo t6StatisticResInfo : list) {
            if (t6StatisticResInfo.getToiletTime() > toiletTime) {
                toiletTime = t6StatisticResInfo.getToiletTime();
            }
        }
        return toiletTime;
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

    private int getWaterRes(String str, String str2) {
        return ColorUtils.getPetWaterColorById(str, str2);
    }

    private String getRecColor(String str, String str2) {
        return ColorUtils.getPetColorById(str, str2);
    }

    private void setPopEventInfo(final T6StatisticResInfo t6StatisticResInfo) {
        String name;
        this.tvPopPetName.setText(getPetName(t6StatisticResInfo.getPetId()) + "：" + getEventDurationStr(t6StatisticResInfo.getToiletTime()));
        long timeIn = t6StatisticResInfo.getContent() != null ? t6StatisticResInfo.getContent().getTimeIn() : 0L;
        TextView textView = this.tvPopTime;
        TimeUtils timeUtils = TimeUtils.getInstance();
        T6Record t6Record = this.t5Record;
        textView.setText(timeUtils.timeStampToTimeStringWithUnit(this, timeIn, t6Record == null ? null : t6Record.getActualTimeZone()));
        Pet petById = UserInforUtils.getPetById(t6StatisticResInfo.getPetId());
        if (petById == null) {
            name = "";
        } else {
            name = petById.getName();
        }
        this.sdPet.setBgColor(getRecColor(t6StatisticResInfo.getPetId(), name));
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
        if (System.currentTimeMillis() / 1000 > t6StatisticResInfo.getExpire()) {
            this.rlPopImage.setVisibility(8);
            return;
        }
        if (t6StatisticResInfo.getPreview().isEmpty()) {
            this.rlPopImage.setVisibility(8);
            return;
        }
        this.rlPopImage.setVisibility(0);
        this.rlPopImage.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$$ExternalSyntheticLambda16
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$setPopEventInfo$46(t6StatisticResInfo, view);
            }
        });
        if (isExpireService(t6StatisticResInfo.getTimestamp())) {
            this.ivChartPlay.setVisibility(0);
        } else if (t6StatisticResInfo.getContent() != null && DateUtil.isSameDay(t6StatisticResInfo.getContent().getStartTime() * 1000, this.t5Record.getActualTimeZone())) {
            this.ivChartPlay.setVisibility(0);
        } else {
            this.ivChartPlay.setVisibility(8);
        }
        new ImageCacheSimple(CommonUtils.getAppContext()).getImageCachePath(CommonUtil.httpToHttps(t6StatisticResInfo.getPreview()), new ImageCacheSimple.IGetImageCacheListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$$ExternalSyntheticLambda17
            @Override // com.jess.arms.widget.imageloader.ImageCacheSimple.IGetImageCacheListener
            public final void onCachePath(String str) {
                this.f$0.lambda$setPopEventInfo$47(t6StatisticResInfo, str);
            }
        });
    }

    public /* synthetic */ void lambda$setPopEventInfo$46(T6StatisticResInfo t6StatisticResInfo, View view) {
        jumpToPlayerDetail(t6StatisticResInfo);
    }

    public /* synthetic */ void lambda$setPopEventInfo$47(T6StatisticResInfo t6StatisticResInfo, String str) {
        if (str != null) {
            try {
                File fileDecryptImageFile = ImageUtils.decryptImageFile(new File(str), t6StatisticResInfo.getAesKey());
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

    private String getPetName(String str) {
        Pet petById = UserInforUtils.getPetById(str);
        String name = petById != null ? petById.getName() : null;
        return (name == null || name.equals("")) ? getResources().getString(R.string.Pet) : name;
    }

    private boolean isExpireService(long j) {
        boolean z = false;
        boolean z2 = this.t5Record.getCloudProduct() == null || (this.t5Record.getCloudProduct().getWorkIndate() != null && j > Long.parseLong(this.t5Record.getCloudProduct().getWorkIndate()));
        if (this.t5Record.getServiceStatus() == 0 || (this.t5Record.getServiceStatus() == 2 && z2)) {
            z = true;
        }
        return !z;
    }

    private void jumpToPlayerDetail(T6StatisticResInfo t6StatisticResInfo) {
        T6EventInfo t6EventInfo = new T6EventInfo();
        t6EventInfo.setPetId(t6StatisticResInfo.getPetId());
        t6EventInfo.setDeviceId(this.deviceId);
        t6EventInfo.setPreview(t6StatisticResInfo.getPreview());
        t6EventInfo.setMediaApi(t6StatisticResInfo.getMediaApi());
        t6EventInfo.setAesKey(t6StatisticResInfo.getAesKey());
        t6EventInfo.setDuration(t6StatisticResInfo.getDuration());
        t6EventInfo.setExpire(t6StatisticResInfo.getExpire());
        t6EventInfo.setEventId(t6StatisticResInfo.getEventId());
        if (t6StatisticResInfo.getContent() != null) {
            t6EventInfo.setMark(t6StatisticResInfo.getContent().getMark());
            t6StatisticResInfo.getContent().setStartTime(t6StatisticResInfo.getContent().getTimeIn());
            t6StatisticResInfo.getContent().setPetWeight(t6StatisticResInfo.getContent().getPetWeight());
        }
        t6EventInfo.setContent(t6StatisticResInfo.getContent());
        t6EventInfo.setIsNeedUploadVideo(t6StatisticResInfo.getIsNeedUploadVideo());
        t6EventInfo.setStorageSpace(t6StatisticResInfo.getStorageSpace());
        t6EventInfo.setTimestamp(t6StatisticResInfo.getTime());
        t6EventInfo.setEventType(10);
        ArrayList arrayList = new ArrayList();
        arrayList.add(t6EventInfo);
        launchActivity(T6RecordVIdeoPlayActivity.newIntent(this, this.deviceId, this.deviceType, -1, arrayList, t6EventInfo, false, "", false));
    }

    private String getEventDurationStr(int i) {
        StringBuilder sb;
        StringBuilder sb2;
        int i2 = i / 60;
        int i3 = i % 60;
        StringBuilder sb3 = new StringBuilder();
        if (i2 > 9) {
            sb = new StringBuilder();
            sb.append(i2);
            sb.append("");
        } else {
            sb = new StringBuilder();
            sb.append("0");
            sb.append(i2);
        }
        sb3.append(sb.toString());
        sb3.append("′");
        if (i3 > 9) {
            sb2 = new StringBuilder();
            sb2.append(i3);
            sb2.append("");
        } else {
            sb2 = new StringBuilder();
            sb2.append("0");
            sb2.append(i3);
        }
        sb3.append(sb2.toString());
        sb3.append("″");
        return sb3.toString();
    }

    private List<T6StaticInfo> getSortList(T6StaticInfo t6StaticInfo, List<T6StaticInfo> list) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(t6StaticInfo);
        this.popCount = 0;
        for (int size = list.size() - 1; size >= 0; size--) {
            if (list.get(size).getInfo().getTime() < t6StaticInfo.getInfo().getTime()) {
                arrayList.add(0, list.get(size));
                this.popCount++;
            } else if (list.get(size).getInfo().getTime() > t6StaticInfo.getInfo().getTime()) {
                if (this.popCount < arrayList.size() - 1) {
                    arrayList.add(this.popCount + 1, list.get(size));
                } else {
                    arrayList.add(list.get(size));
                }
            }
        }
        return arrayList;
    }

    private void updateStatisticList(List<T6StatisticResInfo> list) {
        if (list.size() > 0) {
            getChartData(this.chartView, list);
        } else {
            this.chartView.removeAllViews();
        }
        this.tvTodayToiletAmount.setText(String.valueOf(list.size()));
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

    private void showSelectPetsDialog() {
        try {
            this.petList = FamilyUtils.getInstance().getCatListByFamilyId(this, FamilyUtils.getInstance().getFamilyInforThroughDevice(this, this.deviceId, 21).getGroupId());
        } catch (Exception e) {
            this.petList = new ArrayList();
            LogcatStorageHelper.addLog("t6 getDevicePet error :" + e.getMessage());
        }
        PetFilterWindow petFilterWindow = this.petFilterWindow;
        if (petFilterWindow != null) {
            petFilterWindow.setPetList(this.petList);
            this.petFilterWindow.refreshColor();
            this.petFilterWindow.show(getWindow().getDecorView());
        } else {
            PetFilterWindow petFilterWindow2 = new PetFilterWindow(this, getResources().getString(R.string.Cancel), getResources().getString(R.string.OK), "", this.petList.size() == 0 ? "" : getResources().getString(R.string.Change_color), getResources().getString(R.string.Choose_pets), this.petList, new BaseBottomWindow.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.41
                @Override // com.petkit.android.activities.universalWindow.BaseBottomWindow.OnClickListener
                public void onTitleBtn() {
                }

                @Override // com.petkit.android.activities.universalWindow.BaseBottomWindow.OnClickListener
                public void onTopLeftBtn() {
                }

                public AnonymousClass41() {
                }

                @Override // com.petkit.android.activities.universalWindow.BaseBottomWindow.OnClickListener
                public void onBtnLeft() {
                    T5HomeActivity.this.petFilterWindow.resetSelectedState();
                    T5HomeActivity.this.petFilterWindow.dismiss();
                    T5HomeActivity t5HomeActivity = T5HomeActivity.this;
                    t5HomeActivity.selectedPetIds = t5HomeActivity.petFilterWindow.getSelectedPetIds();
                }

                @Override // com.petkit.android.activities.universalWindow.BaseBottomWindow.OnClickListener
                public void onBtnRight() {
                    T5HomeActivity.this.petFilterWindow.updateSelectedState();
                    T5HomeActivity.this.petFilterWindow.dismiss();
                    T5HomeActivity t5HomeActivity = T5HomeActivity.this;
                    t5HomeActivity.selectedPetIds = t5HomeActivity.petFilterWindow.getSelectedPetIds();
                    T5HomeActivity.this.showSelectPet();
                    T5HomeActivity.this.refreshPetChart();
                }

                @Override // com.petkit.android.activities.universalWindow.BaseBottomWindow.OnClickListener
                public void onTopRightBtn() {
                    T5HomeActivity t5HomeActivity = T5HomeActivity.this;
                    t5HomeActivity.startActivity(PetColorSettingActivity.newIntent(t5HomeActivity, t5HomeActivity.petList));
                }
            });
            this.petFilterWindow = petFilterWindow2;
            petFilterWindow2.show(getWindow().getDecorView());
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$41 */
    public class AnonymousClass41 implements BaseBottomWindow.OnClickListener {
        @Override // com.petkit.android.activities.universalWindow.BaseBottomWindow.OnClickListener
        public void onTitleBtn() {
        }

        @Override // com.petkit.android.activities.universalWindow.BaseBottomWindow.OnClickListener
        public void onTopLeftBtn() {
        }

        public AnonymousClass41() {
        }

        @Override // com.petkit.android.activities.universalWindow.BaseBottomWindow.OnClickListener
        public void onBtnLeft() {
            T5HomeActivity.this.petFilterWindow.resetSelectedState();
            T5HomeActivity.this.petFilterWindow.dismiss();
            T5HomeActivity t5HomeActivity = T5HomeActivity.this;
            t5HomeActivity.selectedPetIds = t5HomeActivity.petFilterWindow.getSelectedPetIds();
        }

        @Override // com.petkit.android.activities.universalWindow.BaseBottomWindow.OnClickListener
        public void onBtnRight() {
            T5HomeActivity.this.petFilterWindow.updateSelectedState();
            T5HomeActivity.this.petFilterWindow.dismiss();
            T5HomeActivity t5HomeActivity = T5HomeActivity.this;
            t5HomeActivity.selectedPetIds = t5HomeActivity.petFilterWindow.getSelectedPetIds();
            T5HomeActivity.this.showSelectPet();
            T5HomeActivity.this.refreshPetChart();
        }

        @Override // com.petkit.android.activities.universalWindow.BaseBottomWindow.OnClickListener
        public void onTopRightBtn() {
            T5HomeActivity t5HomeActivity = T5HomeActivity.this;
            t5HomeActivity.startActivity(PetColorSettingActivity.newIntent(t5HomeActivity, t5HomeActivity.petList));
        }
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
            String string = ColorUtils.UNKNOWN_PET.equals(this.selectedPetIds.get(0)) ? getString(R.string.Not_matched) : UserInforUtils.getPetNameById(this.selectedPetIds.get(0));
            String string2 = ColorUtils.UNKNOWN_PET.equals(this.selectedPetIds.get(1)) ? getString(R.string.Not_matched) : UserInforUtils.getPetNameById(this.selectedPetIds.get(1));
            this.scPoint.setBackgroundResource(ColorUtils.getPetColorCircleResById(this.selectedPetIds.get(0), string));
            this.scPoint2.setBackgroundResource(ColorUtils.getPetColorCircleResById(this.selectedPetIds.get(1), string2));
            this.tvPetName.setText(string);
            this.tvPetName2.setText(string2);
            return;
        }
        if (this.selectedPetIds.size() == 1) {
            if (!ColorUtils.UNKNOWN_PET.equals(this.selectedPetIds.get(0))) {
                this.tvPetWeight.setVisibility(0);
                Pet petById = UserInforUtils.getPetById(this.selectedPetIds.get(0));
                if (UserInforUtils.getCurrentLoginResult().getSettings().getUnit() == 1) {
                    if (petById.getWeight() != null) {
                        String strValueOf = String.valueOf(CommonUtil.doubleToDouble(CommonUtil.KgToLb(Double.valueOf(petById.getWeight()).doubleValue())));
                        String str = getString(R.string.Current_weight) + " " + strValueOf + " " + getString(R.string.Unit_lb) + " >";
                        this.tvPetWeight.setText(TextUtil.getInstance().makePartStringSpannableInTotalString(new SpannableString(str), str, strValueOf, getResources().getColor(R.color.light_black), 20, true));
                    } else {
                        this.tvPetWeight.setVisibility(8);
                    }
                } else if (petById.getWeight() != null) {
                    String strValueOf2 = String.valueOf(CommonUtil.doubleToDouble(Double.valueOf(petById.getWeight()).doubleValue()));
                    String str2 = getString(R.string.Current_weight) + " " + strValueOf2 + " " + getString(R.string.Unit_kg) + " >";
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
            String string3 = ColorUtils.UNKNOWN_PET.equals(this.selectedPetIds.get(0)) ? getString(R.string.Not_matched) : UserInforUtils.getPetNameById(this.selectedPetIds.get(0));
            this.scPoint.setBackgroundResource(ColorUtils.getPetColorCircleResById(this.selectedPetIds.get(0), string3));
            this.tvPetName.setText(string3);
        }
    }

    public void refreshPetChart() {
        if (this.selectedPetIds.contains(ColorUtils.ALL_PET)) {
            updateStatisticList(this.resStatisticList);
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (T6StatisticResInfo t6StatisticResInfo : this.resStatisticList) {
            if (hasSelectPet(t6StatisticResInfo.getPetId())) {
                arrayList.add(t6StatisticResInfo);
            }
        }
        updateStatisticList(arrayList);
    }

    private boolean hasSelectPet(String str) {
        if (str != null && !str.equals("")) {
            for (String str2 : this.selectedPetIds) {
                if (str2.equals(ColorUtils.UNKNOWN_PET)) {
                    if (CommonUtil.getInt(str) < 1) {
                        return true;
                    }
                } else if (str2.equals(str)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Subscriber
    public void refreshPetColor(UpdatePetColorMsg updatePetColorMsg) {
        PetFilterWindow petFilterWindow = this.petFilterWindow;
        if (petFilterWindow != null) {
            petFilterWindow.refreshColor();
        }
        showSelectPet();
        refreshPetChart();
        if (this.fragmentToilet.isAdded()) {
            this.fragmentToilet.refreshPetColor();
        }
    }

    private void showKittenProtectDialog() {
        List<Pet> kittens;
        T6Record t6Record = this.t5Record;
        if (t6Record == null) {
            return;
        }
        if (t6Record.getState().getOverall() == 2 || this.t5Record.getState().getErrorLevel() == 1 || this.t5Record.getDeviceShared() != null) {
            if (!DataHelper.getBooleanSF(this, Constants.T6_BIND_SERVICE_PROMPT + this.deviceId) || this.t5Record.getRealDeviceShared() != null || FamilyUtils.getInstance().checkIsSharedDevice(this, this.t5Record.getDeviceId(), this.deviceType)) {
                ((T5HomePresenter) this.mPresenter).checkInitWindow(6);
                return;
            } else {
                ((T5HomePresenter) this.mPresenter).checkInitWindow(5);
                return;
            }
        }
        boolean booleanSF = DataHelper.getBooleanSF(this, this.familyInfor.getGroupId() + Constants.T5_KITTEN_PROTECTION_NEED_GUIDE_FLAG);
        LogcatStorageHelper.addLog("t6ProtectFlag:" + booleanSF);
        if (!booleanSF) {
            if (this.t5Record.getSettings().getKitten() == 1) {
                DataHelper.setBooleanSF(this, this.familyInfor.getGroupId() + Constants.T5_KITTEN_PROTECTION_NEED_GUIDE_FLAG, Boolean.TRUE);
            } else {
                List<Pet> kittens2 = BleDeviceUtils.getKittens(FamilyUtils.getInstance().getFamilyInforThroughDevice(this, this.deviceId, this.deviceType));
                if (kittens2 != null && kittens2.size() > 0) {
                    showKittenDialog(kittens2);
                    return;
                }
            }
        } else {
            boolean booleanSF2 = DataHelper.getBooleanSF(this, Constants.T5_KITTEN_PROTECTION_NEED_GUIDE_SECOND_FLAG);
            boolean zSevenDaysBetween = BleDeviceUtils.sevenDaysBetween(this.t5Record.getSettings().getKittenTipsTime());
            LogcatStorageHelper.addLog("t6,reshowFlag:" + booleanSF2 + ",sevenDaysBetween:" + zSevenDaysBetween);
            if (booleanSF2 && zSevenDaysBetween && this.t5Record.getSettings().getKitten() != 1 && (kittens = BleDeviceUtils.getKittens(FamilyUtils.getInstance().getFamilyInforThroughDevice(this, this.deviceId, this.deviceType))) != null && kittens.size() > 0) {
                showKittenDialog(kittens);
                return;
            }
        }
        if (!DataHelper.getBooleanSF(this, Constants.T6_BIND_SERVICE_PROMPT + this.deviceId) || this.t5Record.getRealDeviceShared() != null || FamilyUtils.getInstance().checkIsSharedDevice(this, this.t5Record.getDeviceId(), this.deviceType)) {
            ((T5HomePresenter) this.mPresenter).checkInitWindow(6);
        } else {
            ((T5HomePresenter) this.mPresenter).checkInitWindow(5);
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$42 */
    public class AnonymousClass42 implements T6KittenProtectionDialog.OnOpenKittenProtectionListener {
        public AnonymousClass42() {
        }

        /* JADX WARN: Removed duplicated region for block: B:23:0x0060  */
        @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.T6KittenProtectionDialog.OnOpenKittenProtectionListener
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void onOpen() {
            /*
                r5 = this;
                com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity r0 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.this
                com.petkit.android.activities.petkitBleDevice.t6.widget.T6KittenProtectionDialog r0 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.access$5600(r0)
                r0.dismiss()
                com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity r0 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.this
                java.lang.StringBuilder r1 = new java.lang.StringBuilder
                r1.<init>()
                java.lang.String r2 = "com.petkit.android.T6_BIND_SERVICE_PROMPT_"
                r1.append(r2)
                com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity r2 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.this
                long r2 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.access$100(r2)
                r1.append(r2)
                java.lang.String r1 = r1.toString()
                boolean r0 = com.jess.arms.utils.DataHelper.getBooleanSF(r0, r1)
                if (r0 == 0) goto L60
                com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity r0 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.this
                com.petkit.android.activities.petkitBleDevice.t6.mode.T6Record r0 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.access$900(r0)
                com.petkit.android.model.DeviceShared r0 = r0.getRealDeviceShared()
                if (r0 != 0) goto L60
                com.petkit.android.activities.petkitBleDevice.utils.FamilyUtils r0 = com.petkit.android.activities.petkitBleDevice.utils.FamilyUtils.getInstance()
                com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity r1 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.this
                com.petkit.android.activities.petkitBleDevice.t6.mode.T6Record r2 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.access$900(r1)
                long r2 = r2.getDeviceId()
                com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity r4 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.this
                int r4 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.access$2300(r4)
                boolean r0 = r0.checkIsSharedDevice(r1, r2, r4)
                if (r0 == 0) goto L4f
                goto L60
            L4f:
                com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity r0 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.this
                com.jess.arms.mvp.IPresenter r0 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.access$5800(r0)
                com.petkit.android.activities.petkitBleDevice.t6.presenter.T5HomePresenter r0 = (com.petkit.android.activities.petkitBleDevice.t6.presenter.T5HomePresenter) r0
                r1 = 5
                int[] r1 = new int[]{r1}
                r0.checkInitWindow(r1)
                goto L70
            L60:
                com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity r0 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.this
                com.jess.arms.mvp.IPresenter r0 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.access$5700(r0)
                com.petkit.android.activities.petkitBleDevice.t6.presenter.T5HomePresenter r0 = (com.petkit.android.activities.petkitBleDevice.t6.presenter.T5HomePresenter) r0
                r1 = 6
                int[] r1 = new int[]{r1}
                r0.checkInitWindow(r1)
            L70:
                com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity r0 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.this
                java.lang.StringBuilder r1 = new java.lang.StringBuilder
                r1.<init>()
                com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity r2 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.this
                com.petkit.android.activities.family.mode.FamilyInfor r2 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.access$5900(r2)
                long r2 = r2.getGroupId()
                r1.append(r2)
                java.lang.String r2 = "T5_KITTEN_PROTECTION_NEED_GUIDE_FLAG"
                r1.append(r2)
                java.lang.String r1 = r1.toString()
                java.lang.Boolean r2 = java.lang.Boolean.TRUE
                com.jess.arms.utils.DataHelper.setBooleanSF(r0, r1, r2)
                com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity r0 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.this
                com.jess.arms.mvp.IPresenter r0 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.access$6000(r0)
                com.petkit.android.activities.petkitBleDevice.t6.presenter.T5HomePresenter r0 = (com.petkit.android.activities.petkitBleDevice.t6.presenter.T5HomePresenter) r0
                r0.enableKittenProtection()
                com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity r0 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.this
                java.lang.Boolean r1 = java.lang.Boolean.FALSE
                java.lang.String r2 = "T5_KITTEN_PROTECTION_NEED_GUIDE_SECOND_FLAG"
                com.jess.arms.utils.DataHelper.setBooleanSF(r0, r2, r1)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.AnonymousClass42.onOpen():void");
        }

        /* JADX WARN: Removed duplicated region for block: B:23:0x0060  */
        @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.T6KittenProtectionDialog.OnOpenKittenProtectionListener
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void onCancel() {
            /*
                r5 = this;
                com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity r0 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.this
                com.petkit.android.activities.petkitBleDevice.t6.widget.T6KittenProtectionDialog r0 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.access$5600(r0)
                r0.dismiss()
                com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity r0 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.this
                java.lang.StringBuilder r1 = new java.lang.StringBuilder
                r1.<init>()
                java.lang.String r2 = "com.petkit.android.T6_BIND_SERVICE_PROMPT_"
                r1.append(r2)
                com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity r2 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.this
                long r2 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.access$100(r2)
                r1.append(r2)
                java.lang.String r1 = r1.toString()
                boolean r0 = com.jess.arms.utils.DataHelper.getBooleanSF(r0, r1)
                if (r0 == 0) goto L60
                com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity r0 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.this
                com.petkit.android.activities.petkitBleDevice.t6.mode.T6Record r0 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.access$900(r0)
                com.petkit.android.model.DeviceShared r0 = r0.getRealDeviceShared()
                if (r0 != 0) goto L60
                com.petkit.android.activities.petkitBleDevice.utils.FamilyUtils r0 = com.petkit.android.activities.petkitBleDevice.utils.FamilyUtils.getInstance()
                com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity r1 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.this
                com.petkit.android.activities.petkitBleDevice.t6.mode.T6Record r2 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.access$900(r1)
                long r2 = r2.getDeviceId()
                com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity r4 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.this
                int r4 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.access$2300(r4)
                boolean r0 = r0.checkIsSharedDevice(r1, r2, r4)
                if (r0 == 0) goto L4f
                goto L60
            L4f:
                com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity r0 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.this
                com.jess.arms.mvp.IPresenter r0 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.access$6200(r0)
                com.petkit.android.activities.petkitBleDevice.t6.presenter.T5HomePresenter r0 = (com.petkit.android.activities.petkitBleDevice.t6.presenter.T5HomePresenter) r0
                r1 = 5
                int[] r1 = new int[]{r1}
                r0.checkInitWindow(r1)
                goto L70
            L60:
                com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity r0 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.this
                com.jess.arms.mvp.IPresenter r0 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.access$6100(r0)
                com.petkit.android.activities.petkitBleDevice.t6.presenter.T5HomePresenter r0 = (com.petkit.android.activities.petkitBleDevice.t6.presenter.T5HomePresenter) r0
                r1 = 6
                int[] r1 = new int[]{r1}
                r0.checkInitWindow(r1)
            L70:
                com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity r0 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.this
                java.lang.StringBuilder r1 = new java.lang.StringBuilder
                r1.<init>()
                com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity r2 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.this
                com.petkit.android.activities.family.mode.FamilyInfor r2 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.access$5900(r2)
                long r2 = r2.getGroupId()
                r1.append(r2)
                java.lang.String r2 = "T5_KITTEN_PROTECTION_NEED_GUIDE_FLAG"
                r1.append(r2)
                java.lang.String r1 = r1.toString()
                java.lang.Boolean r2 = java.lang.Boolean.TRUE
                com.jess.arms.utils.DataHelper.setBooleanSF(r0, r1, r2)
                com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity r0 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.this
                com.jess.arms.mvp.IPresenter r0 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.access$6300(r0)
                com.petkit.android.activities.petkitBleDevice.t6.presenter.T5HomePresenter r0 = (com.petkit.android.activities.petkitBleDevice.t6.presenter.T5HomePresenter) r0
                r0.updateKittenTipsTime()
                com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity r0 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.this
                java.lang.String r1 = "T5_KITTEN_PROTECTION_NEED_GUIDE_SECOND_FLAG"
                com.jess.arms.utils.DataHelper.setBooleanSF(r0, r1, r2)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.AnonymousClass42.onCancel():void");
        }
    }

    private void showKittenDialog(List<Pet> list) {
        if (this.t6KittenProtectionDialog == null) {
            this.t6KittenProtectionDialog = new T6KittenProtectionDialog(this, list, new T6KittenProtectionDialog.OnOpenKittenProtectionListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.42
                public AnonymousClass42() {
                }

                @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.T6KittenProtectionDialog.OnOpenKittenProtectionListener
                public void onOpen() {
                    /*
                        this = this;
                        com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity r0 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.this
                        com.petkit.android.activities.petkitBleDevice.t6.widget.T6KittenProtectionDialog r0 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.access$5600(r0)
                        r0.dismiss()
                        com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity r0 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.this
                        java.lang.StringBuilder r1 = new java.lang.StringBuilder
                        r1.<init>()
                        java.lang.String r2 = "com.petkit.android.T6_BIND_SERVICE_PROMPT_"
                        r1.append(r2)
                        com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity r2 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.this
                        long r2 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.access$100(r2)
                        r1.append(r2)
                        java.lang.String r1 = r1.toString()
                        boolean r0 = com.jess.arms.utils.DataHelper.getBooleanSF(r0, r1)
                        if (r0 == 0) goto L60
                        com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity r0 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.this
                        com.petkit.android.activities.petkitBleDevice.t6.mode.T6Record r0 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.access$900(r0)
                        com.petkit.android.model.DeviceShared r0 = r0.getRealDeviceShared()
                        if (r0 != 0) goto L60
                        com.petkit.android.activities.petkitBleDevice.utils.FamilyUtils r0 = com.petkit.android.activities.petkitBleDevice.utils.FamilyUtils.getInstance()
                        com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity r1 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.this
                        com.petkit.android.activities.petkitBleDevice.t6.mode.T6Record r2 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.access$900(r1)
                        long r2 = r2.getDeviceId()
                        com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity r4 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.this
                        int r4 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.access$2300(r4)
                        boolean r0 = r0.checkIsSharedDevice(r1, r2, r4)
                        if (r0 == 0) goto L4f
                        goto L60
                    L4f:
                        com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity r0 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.this
                        com.jess.arms.mvp.IPresenter r0 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.access$5800(r0)
                        com.petkit.android.activities.petkitBleDevice.t6.presenter.T5HomePresenter r0 = (com.petkit.android.activities.petkitBleDevice.t6.presenter.T5HomePresenter) r0
                        r1 = 5
                        int[] r1 = new int[]{r1}
                        r0.checkInitWindow(r1)
                        goto L70
                    L60:
                        com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity r0 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.this
                        com.jess.arms.mvp.IPresenter r0 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.access$5700(r0)
                        com.petkit.android.activities.petkitBleDevice.t6.presenter.T5HomePresenter r0 = (com.petkit.android.activities.petkitBleDevice.t6.presenter.T5HomePresenter) r0
                        r1 = 6
                        int[] r1 = new int[]{r1}
                        r0.checkInitWindow(r1)
                    L70:
                        com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity r0 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.this
                        java.lang.StringBuilder r1 = new java.lang.StringBuilder
                        r1.<init>()
                        com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity r2 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.this
                        com.petkit.android.activities.family.mode.FamilyInfor r2 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.access$5900(r2)
                        long r2 = r2.getGroupId()
                        r1.append(r2)
                        java.lang.String r2 = "T5_KITTEN_PROTECTION_NEED_GUIDE_FLAG"
                        r1.append(r2)
                        java.lang.String r1 = r1.toString()
                        java.lang.Boolean r2 = java.lang.Boolean.TRUE
                        com.jess.arms.utils.DataHelper.setBooleanSF(r0, r1, r2)
                        com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity r0 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.this
                        com.jess.arms.mvp.IPresenter r0 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.access$6000(r0)
                        com.petkit.android.activities.petkitBleDevice.t6.presenter.T5HomePresenter r0 = (com.petkit.android.activities.petkitBleDevice.t6.presenter.T5HomePresenter) r0
                        r0.enableKittenProtection()
                        com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity r0 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.this
                        java.lang.Boolean r1 = java.lang.Boolean.FALSE
                        java.lang.String r2 = "T5_KITTEN_PROTECTION_NEED_GUIDE_SECOND_FLAG"
                        com.jess.arms.utils.DataHelper.setBooleanSF(r0, r2, r1)
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.AnonymousClass42.onOpen():void");
                }

                @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.T6KittenProtectionDialog.OnOpenKittenProtectionListener
                public void onCancel() {
                    /*
                        this = this;
                        com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity r0 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.this
                        com.petkit.android.activities.petkitBleDevice.t6.widget.T6KittenProtectionDialog r0 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.access$5600(r0)
                        r0.dismiss()
                        com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity r0 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.this
                        java.lang.StringBuilder r1 = new java.lang.StringBuilder
                        r1.<init>()
                        java.lang.String r2 = "com.petkit.android.T6_BIND_SERVICE_PROMPT_"
                        r1.append(r2)
                        com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity r2 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.this
                        long r2 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.access$100(r2)
                        r1.append(r2)
                        java.lang.String r1 = r1.toString()
                        boolean r0 = com.jess.arms.utils.DataHelper.getBooleanSF(r0, r1)
                        if (r0 == 0) goto L60
                        com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity r0 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.this
                        com.petkit.android.activities.petkitBleDevice.t6.mode.T6Record r0 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.access$900(r0)
                        com.petkit.android.model.DeviceShared r0 = r0.getRealDeviceShared()
                        if (r0 != 0) goto L60
                        com.petkit.android.activities.petkitBleDevice.utils.FamilyUtils r0 = com.petkit.android.activities.petkitBleDevice.utils.FamilyUtils.getInstance()
                        com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity r1 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.this
                        com.petkit.android.activities.petkitBleDevice.t6.mode.T6Record r2 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.access$900(r1)
                        long r2 = r2.getDeviceId()
                        com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity r4 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.this
                        int r4 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.access$2300(r4)
                        boolean r0 = r0.checkIsSharedDevice(r1, r2, r4)
                        if (r0 == 0) goto L4f
                        goto L60
                    L4f:
                        com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity r0 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.this
                        com.jess.arms.mvp.IPresenter r0 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.access$6200(r0)
                        com.petkit.android.activities.petkitBleDevice.t6.presenter.T5HomePresenter r0 = (com.petkit.android.activities.petkitBleDevice.t6.presenter.T5HomePresenter) r0
                        r1 = 5
                        int[] r1 = new int[]{r1}
                        r0.checkInitWindow(r1)
                        goto L70
                    L60:
                        com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity r0 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.this
                        com.jess.arms.mvp.IPresenter r0 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.access$6100(r0)
                        com.petkit.android.activities.petkitBleDevice.t6.presenter.T5HomePresenter r0 = (com.petkit.android.activities.petkitBleDevice.t6.presenter.T5HomePresenter) r0
                        r1 = 6
                        int[] r1 = new int[]{r1}
                        r0.checkInitWindow(r1)
                    L70:
                        com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity r0 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.this
                        java.lang.StringBuilder r1 = new java.lang.StringBuilder
                        r1.<init>()
                        com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity r2 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.this
                        com.petkit.android.activities.family.mode.FamilyInfor r2 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.access$5900(r2)
                        long r2 = r2.getGroupId()
                        r1.append(r2)
                        java.lang.String r2 = "T5_KITTEN_PROTECTION_NEED_GUIDE_FLAG"
                        r1.append(r2)
                        java.lang.String r1 = r1.toString()
                        java.lang.Boolean r2 = java.lang.Boolean.TRUE
                        com.jess.arms.utils.DataHelper.setBooleanSF(r0, r1, r2)
                        com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity r0 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.this
                        com.jess.arms.mvp.IPresenter r0 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.access$6300(r0)
                        com.petkit.android.activities.petkitBleDevice.t6.presenter.T5HomePresenter r0 = (com.petkit.android.activities.petkitBleDevice.t6.presenter.T5HomePresenter) r0
                        r0.updateKittenTipsTime()
                        com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity r0 = com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.this
                        java.lang.String r1 = "T5_KITTEN_PROTECTION_NEED_GUIDE_SECOND_FLAG"
                        com.jess.arms.utils.DataHelper.setBooleanSF(r0, r1, r2)
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.AnonymousClass42.onCancel():void");
                }
            }, true, 21);
        }
        if (this.t6KittenProtectionDialog.isShowing()) {
            return;
        }
        this.t6KittenProtectionDialog.showAtLocation(getWindow().getDecorView(), 17, 0, 0);
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T5HomeContract.View
    public void enableKittenProtectionSuccess() {
        PetkitToast.showTopToast(this, getResources().getString(R.string.Kitten_protection_is_enabled), R.drawable.top_toast_success_icon, 500);
    }

    private void endLoadingAnim() {
        this.ivEyeAnim.setVisibility(0);
        this.clEyeAnimBackground.setVisibility(8);
        this.t6AnimUtil.endLoadingAnim(this.clLoading, this.ltLoading);
        ((AnimationDrawable) this.ivEyeAnim.getDrawable()).start();
        MyHandler myHandler = this.handler;
        if (myHandler != null) {
            myHandler.postDelayed(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$$ExternalSyntheticLambda45
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$endLoadingAnim$48();
                }
            }, 1000L);
        }
    }

    public /* synthetic */ void lambda$endLoadingAnim$48() {
        ImageView imageView = this.ivEyeAnim;
        if (imageView != null) {
            imageView.setVisibility(8);
        }
    }

    private void startCameraAnim() {
        this.t6AnimUtil.startCameraAnim(this.ivCameraAnim);
        this.isAnimationRunning = true;
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T5HomeContract.View
    public void showPetGuide() {
        T6Record t6Record = this.t5Record;
        if (t6Record != null && t6Record.getDeviceShared() != null) {
            ((T5HomePresenter) this.mPresenter).checkInitWindow(7);
            return;
        }
        if (!CommonUtils.getSysBoolMap(this, "CAT_FACE_GUIDE_" + this.familyInfor.getGroupId(), false)) {
            boolean currentFamilyIsUploadCatPhoto = FamilyUtils.getInstance().getCurrentFamilyIsUploadCatPhoto(this);
            boolean zHasOneCat = FamilyUtils.getInstance().hasOneCat(this, this.deviceId, this.deviceType);
            List<Pet> currentFamilyPetListExceptBlocket = FamilyUtils.getInstance().getCurrentFamilyPetListExceptBlocket(this);
            if ((!currentFamilyIsUploadCatPhoto && zHasOneCat) || currentFamilyPetListExceptBlocket == null || currentFamilyPetListExceptBlocket.isEmpty()) {
                showUploadCatPhotoPop();
                CommonUtils.addSysBoolMap(this, "CAT_FACE_GUIDE_" + this.familyInfor.getGroupId(), true);
                CommonUtils.addSysLongMap(this, "CAT_FACE_NOT_UPLOAD_POP_TIME_" + this.familyInfor.getGroupId(), System.currentTimeMillis());
                return;
            }
            ((T5HomePresenter) this.mPresenter).checkInitWindow(7);
            return;
        }
        ((T5HomePresenter) this.mPresenter).checkInitWindow(7);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$43 */
    public class AnonymousClass43 implements NormalCenterTipWindow.OnClickOk {
        public AnonymousClass43() {
        }

        @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
        public void onOkClick() {
            T5HomeActivity t5HomeActivity = T5HomeActivity.this;
            t5HomeActivity.launchActivity(PetWeightActivity.newIntent(t5HomeActivity, t5HomeActivity.deviceId, T5HomeActivity.this.deviceType));
        }

        @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
        public void onCancelClick() {
            ((T5HomePresenter) ((BaseActivity) T5HomeActivity.this).mPresenter).checkInitWindow(7);
        }
    }

    private void showUploadCatPhotoPop() {
        NormalCenterTipWindow normalCenterTipWindow = new NormalCenterTipWindow(this, new NormalCenterTipWindow.OnClickOk() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.43
            public AnonymousClass43() {
            }

            @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
            public void onOkClick() {
                T5HomeActivity t5HomeActivity = T5HomeActivity.this;
                t5HomeActivity.launchActivity(PetWeightActivity.newIntent(t5HomeActivity, t5HomeActivity.deviceId, T5HomeActivity.this.deviceType));
            }

            @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
            public void onCancelClick() {
                ((T5HomePresenter) ((BaseActivity) T5HomeActivity.this).mPresenter).checkInitWindow(7);
            }
        }, (String) null, getString(R.string.Upload_face_photo_content));
        normalCenterTipWindow.setOkButtonText(getString(R.string.Upload_face_photo));
        normalCenterTipWindow.setCancelButtonText(getString(R.string.Not_upload_now));
        normalCenterTipWindow.show(getWindow().getDecorView());
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T5HomeContract.View
    public void refreshBannerList(D4shBannerData d4shBannerData) {
        T6Record t6Record;
        this.d4shBannerData = d4shBannerData;
        refreshHomeBannerData(d4shBannerData);
        if (TextUtils.isEmpty(CommonUtils.getSysMap(Constants.CLOUD_SERVICE_DELAY_TIME)) || TextUtils.isEmpty(this.t5Record.getCreatedAt())) {
            return;
        }
        try {
            if ((System.currentTimeMillis() - DateUtil.parseISO8601Date(this.t5Record.getCreatedAt()).getTime()) / 1000 <= ((CloudServiceDelayTimeResult) new Gson().fromJson(r0, CloudServiceDelayTimeResult.class)).getDelayTime() || !FamilyUtils.getInstance().isDeviceBelongToMyself(this, this.deviceId).booleanValue() || (t6Record = this.t5Record) == null || t6Record.getRealDeviceShared() != null || this.t5Record.getServiceStatus() != 0 || d4shBannerData.getFreeActivity() == null || d4shBannerData.getFreeActivity().getImageRet() == null) {
                return;
            }
            ((T5HomePresenter) this.mPresenter).getFreePackage(this.deviceId, this.t5Record.getTypeCode() == 0 ? "T6" : "T5");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subscriber
    public void changeState(ExitCloudServiceWebViewMsg exitCloudServiceWebViewMsg) {
        this.serviceMaybeChanged = true;
    }

    public void refreshHomeBannerData(D4shBannerData d4shBannerData) {
        boolean z;
        BannerStateCache bannerStateCache;
        boolean z2 = this.t5Record.getRealDeviceShared() == null && !FamilyUtils.getInstance().checkIsSharedDevice(this, this.t5Record.getDeviceId(), 21);
        this.dataList = new ArrayList();
        T6Record t6RecordByDeviceId = T6Utils.getT6RecordByDeviceId(this.t5Record.getDeviceId(), 1);
        this.t5Record = t6RecordByDeviceId;
        if (t6RecordByDeviceId == null) {
            return;
        }
        PetkitLog.d("getServiceStatus", "获取到的设备status:" + this.t5Record.getServiceStatus());
        int serviceStatus = this.t5Record.getServiceStatus();
        if (serviceStatus != 0) {
            if (serviceStatus != 1) {
                if (serviceStatus == 2) {
                    if (d4shBannerData == null || d4shBannerData.getFreeActivity() == null) {
                        this.dataList.add(new D4shBannerData.ServiceStatusData(3));
                    } else if (z2) {
                        this.dataList.add(d4shBannerData.getFreeActivity());
                    }
                }
            } else if (this.t5Record.getMoreService() != 1 && this.t5Record.getCloudProduct().getSubscribe() != 1 && (Long.parseLong(this.t5Record.getCloudProduct().getWorkIndate()) * 1000) - System.currentTimeMillis() <= 259200000 && !"FREE".equalsIgnoreCase(this.t5Record.getCloudProduct().getChargeType())) {
                this.dataList.add(new D4shBannerData.ServiceStatusData(2));
            }
        } else if (d4shBannerData == null || d4shBannerData.getFreeActivity() == null) {
            this.dataList.add(new D4shBannerData.ServiceStatusData(1));
        } else if (z2) {
            this.dataList.add(d4shBannerData.getFreeActivity());
        }
        if (d4shBannerData != null && z2) {
            this.dataList.addAll(d4shBannerData.getPromotionList());
        }
        if (this.dataList.size() > 0) {
            String sysMap = CommonUtils.getSysMap(Constants.T6_PROMOTE_BANNER + UserInforUtils.getCurrentUserId(this) + this.t5Record.getDeviceId() + (this.t5Record.getCloudProduct() == null ? 0 : this.t5Record.getCloudProduct().getServiceId()));
            if (!TextUtils.isEmpty(sysMap)) {
                List list = (List) new Gson().fromJson(sysMap, new TypeToken<List<BannerStateCache>>() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.44
                    public AnonymousClass44() {
                    }
                }.getType());
                boolean z3 = false;
                for (int i = 0; i < this.dataList.size(); i++) {
                    D4shBannerData.BannerData bannerData = this.dataList.get(i);
                    if (bannerData instanceof D4shBannerData.ServiceStatusData) {
                        bannerStateCache = new BannerStateCache(3, Integer.valueOf(((D4shBannerData.ServiceStatusData) bannerData).getStatus()));
                    } else if (bannerData instanceof D4shBannerData.FreeActivity) {
                        D4shBannerData.FreeActivity freeActivity = (D4shBannerData.FreeActivity) bannerData;
                        bannerStateCache = new BannerStateCache(1, freeActivity.getUpdateDate(), Integer.valueOf(freeActivity.getId()));
                    } else if (bannerData instanceof D4shBannerData.PromotionData) {
                        D4shBannerData.PromotionData promotionData = (D4shBannerData.PromotionData) bannerData;
                        bannerStateCache = new BannerStateCache(2, promotionData.getUpdateDate(), Integer.valueOf(promotionData.getId()));
                    } else {
                        bannerStateCache = null;
                    }
                    if (!list.contains(bannerStateCache)) {
                        z3 = true;
                    }
                }
                if (z3) {
                    this.rlHomeBanner.setVisibility(0);
                    this.indicator.setVisibility(0);
                } else {
                    this.rlHomeBanner.setVisibility(8);
                    this.indicator.setVisibility(8);
                }
                z = !z3;
            } else {
                this.rlHomeBanner.setVisibility(0);
                this.indicator.setVisibility(0);
                z = true;
            }
            if (AppVersionStateUtils.getCurrentAppVersionCheckState()) {
                this.rlHomeBanner.setVisibility(8);
                this.indicator.setVisibility(8);
            }
            T6HomeBannerPageAdapter t6HomeBannerPageAdapter = new T6HomeBannerPageAdapter(this, this.dataList, this.t5Record, this.deviceType, new T6HomeBannerPageAdapter.OnItemClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.45
                public AnonymousClass45() {
                }

                @Override // com.petkit.android.activities.petkitBleDevice.t6.adapter.T6HomeBannerPageAdapter.OnItemClickListener
                public void onBannerItemClick(int i2) {
                    T5HomeActivity t5HomeActivity = T5HomeActivity.this;
                    t5HomeActivity.redirectToPurchasePage(t5HomeActivity.deviceType, T5HomeActivity.this.t5Record.getDeviceId());
                }

                @Override // com.petkit.android.activities.petkitBleDevice.t6.adapter.T6HomeBannerPageAdapter.OnItemClickListener
                public void redirectToPurchase(int i2, long j) {
                    T5HomeActivity t5HomeActivity = T5HomeActivity.this;
                    t5HomeActivity.redirectToPurchasePage(t5HomeActivity.deviceType, T5HomeActivity.this.t5Record.getDeviceId());
                }
            });
            this.bannerPageAdapter = t6HomeBannerPageAdapter;
            this.vpHomeBanner.setAdapter(t6HomeBannerPageAdapter);
            this.vpHomeBanner.setOffscreenPageLimit(0);
            this.indicator.setViewPager(this.vpHomeBanner, 0, this.dataList.size());
            this.indicator.setPageColor(CommonUtils.getColorById(R.color.color_D2C5BC));
            this.indicator.setFillColor(CommonUtils.getColorById(R.color.color_BD8356));
            this.indicator.setSnap(true);
            this.indicator.setIndicatorStyle(3);
            this.indicator.setRadius(ArmsUtils.dip2px(CommonUtils.getAppContext(), 3.0f));
            if (this.bannerPageAdapter.getDataList().size() > 1) {
                if (!z) {
                    this.indicator.setVisibility(0);
                }
                Disposable disposable = this.viewPagerDisposable;
                if (disposable != null && !disposable.isDisposed()) {
                    this.viewPagerDisposable.dispose();
                    this.viewPagerDisposable = null;
                }
                this.viewPagerDisposable = Observable.interval(5000L, 5000L, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$$ExternalSyntheticLambda19
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) throws Exception {
                        this.f$0.lambda$refreshHomeBannerData$49((Long) obj);
                    }
                });
            } else {
                this.indicator.setVisibility(8);
                Disposable disposable2 = this.viewPagerDisposable;
                if (disposable2 != null && !disposable2.isDisposed()) {
                    this.viewPagerDisposable.dispose();
                    this.viewPagerDisposable = null;
                }
            }
            this.ivPagerClose.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.46
                public AnonymousClass46() {
                }

                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    List<D4shBannerData.BannerData> dataList = T5HomeActivity.this.bannerPageAdapter.getDataList();
                    ArrayList arrayList = new ArrayList();
                    for (int i2 = 0; i2 < dataList.size(); i2++) {
                        D4shBannerData.BannerData bannerData2 = dataList.get(i2);
                        if (bannerData2 instanceof D4shBannerData.ServiceStatusData) {
                            arrayList.add(new BannerStateCache(3, Integer.valueOf(((D4shBannerData.ServiceStatusData) bannerData2).getStatus())));
                        } else if (bannerData2 instanceof D4shBannerData.FreeActivity) {
                            D4shBannerData.FreeActivity freeActivity2 = (D4shBannerData.FreeActivity) bannerData2;
                            arrayList.add(new BannerStateCache(1, freeActivity2.getUpdateDate(), Integer.valueOf(freeActivity2.getId())));
                        } else if (bannerData2 instanceof D4shBannerData.PromotionData) {
                            D4shBannerData.PromotionData promotionData2 = (D4shBannerData.PromotionData) bannerData2;
                            arrayList.add(new BannerStateCache(2, promotionData2.getUpdateDate(), Integer.valueOf(promotionData2.getId())));
                        }
                    }
                    T5HomeActivity.this.rlHomeBanner.setVisibility(8);
                    T5HomeActivity.this.indicator.setVisibility(8);
                    CommonUtils.addSysMap(Constants.T6_PROMOTE_BANNER + UserInforUtils.getCurrentUserId(T5HomeActivity.this) + T5HomeActivity.this.t5Record.getDeviceId() + (T5HomeActivity.this.t5Record.getCloudProduct() != null ? T5HomeActivity.this.t5Record.getCloudProduct().getServiceId() : 0), new Gson().toJson(arrayList, new TypeToken<List<BannerStateCache>>() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.46.1
                        public AnonymousClass1() {
                        }
                    }.getType()));
                }

                /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$46$1 */
                public class AnonymousClass1 extends TypeToken<List<BannerStateCache>> {
                    public AnonymousClass1() {
                    }
                }
            });
            return;
        }
        this.rlHomeBanner.setVisibility(8);
        this.indicator.setVisibility(8);
        Disposable disposable3 = this.viewPagerDisposable;
        if (disposable3 == null || disposable3.isDisposed()) {
            return;
        }
        this.viewPagerDisposable.dispose();
        this.viewPagerDisposable = null;
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$44 */
    public class AnonymousClass44 extends TypeToken<List<BannerStateCache>> {
        public AnonymousClass44() {
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$45 */
    public class AnonymousClass45 implements T6HomeBannerPageAdapter.OnItemClickListener {
        public AnonymousClass45() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t6.adapter.T6HomeBannerPageAdapter.OnItemClickListener
        public void onBannerItemClick(int i2) {
            T5HomeActivity t5HomeActivity = T5HomeActivity.this;
            t5HomeActivity.redirectToPurchasePage(t5HomeActivity.deviceType, T5HomeActivity.this.t5Record.getDeviceId());
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t6.adapter.T6HomeBannerPageAdapter.OnItemClickListener
        public void redirectToPurchase(int i2, long j) {
            T5HomeActivity t5HomeActivity = T5HomeActivity.this;
            t5HomeActivity.redirectToPurchasePage(t5HomeActivity.deviceType, T5HomeActivity.this.t5Record.getDeviceId());
        }
    }

    public /* synthetic */ void lambda$refreshHomeBannerData$49(Long l) throws Exception {
        ViewPager viewPager = this.vpHomeBanner;
        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
        this.current = this.vpHomeBanner.getCurrentItem() % (this.bannerPageAdapter.getDataList().size() != 0 ? this.bannerPageAdapter.getDataList().size() : 1);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$46 */
    public class AnonymousClass46 implements View.OnClickListener {
        public AnonymousClass46() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            List<D4shBannerData.BannerData> dataList = T5HomeActivity.this.bannerPageAdapter.getDataList();
            ArrayList arrayList = new ArrayList();
            for (int i2 = 0; i2 < dataList.size(); i2++) {
                D4shBannerData.BannerData bannerData2 = dataList.get(i2);
                if (bannerData2 instanceof D4shBannerData.ServiceStatusData) {
                    arrayList.add(new BannerStateCache(3, Integer.valueOf(((D4shBannerData.ServiceStatusData) bannerData2).getStatus())));
                } else if (bannerData2 instanceof D4shBannerData.FreeActivity) {
                    D4shBannerData.FreeActivity freeActivity2 = (D4shBannerData.FreeActivity) bannerData2;
                    arrayList.add(new BannerStateCache(1, freeActivity2.getUpdateDate(), Integer.valueOf(freeActivity2.getId())));
                } else if (bannerData2 instanceof D4shBannerData.PromotionData) {
                    D4shBannerData.PromotionData promotionData2 = (D4shBannerData.PromotionData) bannerData2;
                    arrayList.add(new BannerStateCache(2, promotionData2.getUpdateDate(), Integer.valueOf(promotionData2.getId())));
                }
            }
            T5HomeActivity.this.rlHomeBanner.setVisibility(8);
            T5HomeActivity.this.indicator.setVisibility(8);
            CommonUtils.addSysMap(Constants.T6_PROMOTE_BANNER + UserInforUtils.getCurrentUserId(T5HomeActivity.this) + T5HomeActivity.this.t5Record.getDeviceId() + (T5HomeActivity.this.t5Record.getCloudProduct() != null ? T5HomeActivity.this.t5Record.getCloudProduct().getServiceId() : 0), new Gson().toJson(arrayList, new TypeToken<List<BannerStateCache>>() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.46.1
                public AnonymousClass1() {
                }
            }.getType()));
        }

        /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$46$1 */
        public class AnonymousClass1 extends TypeToken<List<BannerStateCache>> {
            public AnonymousClass1() {
            }
        }
    }

    public void redirectToPurchasePage(int i, long j) {
        String strCreatePurchasePageUrl = D4shUtils.createPurchasePageUrl(this, i, j);
        if (TextUtils.isEmpty(strCreatePurchasePageUrl)) {
            return;
        }
        onRedirectToH5(strCreatePurchasePageUrl);
    }

    private void showFreeActivity(final D4shBannerData d4shBannerData) {
        this.handler.postDelayed(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$$ExternalSyntheticLambda55
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$showFreeActivity$50(d4shBannerData);
            }
        }, 500L);
    }

    public /* synthetic */ void lambda$showFreeActivity$50(D4shBannerData d4shBannerData) {
        D4shBannerData.FreeActivity freeActivity = d4shBannerData.getFreeActivity();
        boolean zEquals = "zh_CN".equals(UserInforUtils.getLanguageLocaleString(CommonUtils.getAppContext()));
        if (CommonUtils.getSysBoolMap(this, Constants.DEVICE_FREE_ACTIVITY + this.t5Record.getDeviceId() + "&" + this.deviceType, false)) {
            return;
        }
        CloudServiceFreeTrialDialog cloudServiceFreeTrialDialog = this.cloudServiceFreeTrialDialog;
        if (cloudServiceFreeTrialDialog == null || !cloudServiceFreeTrialDialog.isShowing()) {
            D4shBannerData.ImageRet imageRet = freeActivity.getImageRet();
            CloudServiceFreeTrialDialog cloudServiceFreeTrialDialog2 = new CloudServiceFreeTrialDialog(this, zEquals ? imageRet.getPopChineseImage() : imageRet.getPopEnglishImage(), new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.47
                public AnonymousClass47() {
                }

                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    T5HomeActivity t5HomeActivity = T5HomeActivity.this;
                    String strCreatePurchasePageUrl = D4shUtils.createPurchasePageUrl(t5HomeActivity, t5HomeActivity.deviceType, T5HomeActivity.this.t5Record.getDeviceId());
                    if (T5HomeActivity.this.t5Record.getRealDeviceShared() == null) {
                        FamilyUtils familyUtils = FamilyUtils.getInstance();
                        T5HomeActivity t5HomeActivity2 = T5HomeActivity.this;
                        if (!familyUtils.checkIsSharedDevice(t5HomeActivity2, t5HomeActivity2.deviceId, 21)) {
                            CommonUtils.addSysBoolMap(T5HomeActivity.this, Constants.DEVICE_FREE_ACTIVITY + T5HomeActivity.this.t5Record.getDeviceId() + "&" + T5HomeActivity.this.deviceType, true);
                            if (!TextUtils.isEmpty(strCreatePurchasePageUrl)) {
                                T5HomeActivity.this.onRedirectToH5(strCreatePurchasePageUrl);
                            }
                            T5HomeActivity.this.cloudServiceFreeTrialDialog.dismiss();
                            return;
                        }
                    }
                    T5HomeActivity t5HomeActivity3 = T5HomeActivity.this;
                    PetkitToast.showTopToast(t5HomeActivity3, t5HomeActivity3.getResources().getString(R.string.D4sh_no_cloud_free_tips), R.drawable.top_toast_warn_icon, 1);
                }
            });
            this.cloudServiceFreeTrialDialog = cloudServiceFreeTrialDialog2;
            cloudServiceFreeTrialDialog2.getContentView().findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.48
                public AnonymousClass48() {
                }

                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    CommonUtils.addSysBoolMap(T5HomeActivity.this, Constants.DEVICE_FREE_ACTIVITY + T5HomeActivity.this.t5Record.getDeviceId() + "&" + T5HomeActivity.this.deviceType, true);
                    T5HomeActivity.this.cloudServiceFreeTrialDialog.dismiss();
                }
            });
            this.cloudServiceFreeTrialDialog.setBackgroundDrawable(new BitmapDrawable());
            this.cloudServiceFreeTrialDialog.setOutsideTouchable(true);
            this.cloudServiceFreeTrialDialog.setTouchable(true);
            this.cloudServiceFreeTrialDialog.setAnimationStyle(R.style.PopupWindow_Default_Appearance_Animation);
            this.cloudServiceFreeTrialDialog.showAtLocation(getWindow().getDecorView(), 17, 0, 0);
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$47 */
    public class AnonymousClass47 implements View.OnClickListener {
        public AnonymousClass47() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            T5HomeActivity t5HomeActivity = T5HomeActivity.this;
            String strCreatePurchasePageUrl = D4shUtils.createPurchasePageUrl(t5HomeActivity, t5HomeActivity.deviceType, T5HomeActivity.this.t5Record.getDeviceId());
            if (T5HomeActivity.this.t5Record.getRealDeviceShared() == null) {
                FamilyUtils familyUtils = FamilyUtils.getInstance();
                T5HomeActivity t5HomeActivity2 = T5HomeActivity.this;
                if (!familyUtils.checkIsSharedDevice(t5HomeActivity2, t5HomeActivity2.deviceId, 21)) {
                    CommonUtils.addSysBoolMap(T5HomeActivity.this, Constants.DEVICE_FREE_ACTIVITY + T5HomeActivity.this.t5Record.getDeviceId() + "&" + T5HomeActivity.this.deviceType, true);
                    if (!TextUtils.isEmpty(strCreatePurchasePageUrl)) {
                        T5HomeActivity.this.onRedirectToH5(strCreatePurchasePageUrl);
                    }
                    T5HomeActivity.this.cloudServiceFreeTrialDialog.dismiss();
                    return;
                }
            }
            T5HomeActivity t5HomeActivity3 = T5HomeActivity.this;
            PetkitToast.showTopToast(t5HomeActivity3, t5HomeActivity3.getResources().getString(R.string.D4sh_no_cloud_free_tips), R.drawable.top_toast_warn_icon, 1);
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$48 */
    public class AnonymousClass48 implements View.OnClickListener {
        public AnonymousClass48() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            CommonUtils.addSysBoolMap(T5HomeActivity.this, Constants.DEVICE_FREE_ACTIVITY + T5HomeActivity.this.t5Record.getDeviceId() + "&" + T5HomeActivity.this.deviceType, true);
            T5HomeActivity.this.cloudServiceFreeTrialDialog.dismiss();
        }
    }

    public void onRedirectToH5(String str) {
        if (this.t5Record.getRealDeviceShared() != null || FamilyUtils.getInstance().checkIsSharedDevice(this, this.deviceId, 21)) {
            PetkitToast.showTopToast(this, getString(R.string.D4sh_no_cloud_buy_tips), R.drawable.top_toast_warn_icon, 1);
        } else {
            this.serviceMaybeChanged = true;
            redirectToH5(str);
        }
    }

    private void redirectToH5(String str) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.EXTRA_LOAD_PATH, str);
        Intent intent = new Intent(this, (Class<?>) CloudServiceWebViewActivity.class);
        intent.putExtras(bundle);
        launchActivity(intent);
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T5HomeContract.View
    public void setHighlightM3u8UrlList(int i, ArrayList<VlogM3U8Mode> arrayList) {
        Intent intent = new Intent(this, (Class<?>) VlogMakeService.class);
        intent.putExtra(Constants.EXTRA_DEVICE_ID, this.deviceId);
        intent.putExtra(Constants.EXTRA_DEVICE_TYPE, 21);
        intent.putExtra(Constants.EXTRA_VLOG_M3U8_URL_LIST, JSON.toJSONString(arrayList));
        intent.putExtra(Constants.EXTRA_VLOG_ID, i);
        intent.putExtra(Constants.EXTRA_DEVICE_TYPE_CODE, this.deviceType);
        try {
            if (Build.VERSION.SDK_INT >= 26) {
                startForegroundService(intent);
            } else {
                startService(intent);
            }
        } catch (Exception e) {
            PetkitLog.e(getClass().getSimpleName(), e.getMessage());
            LogcatStorageHelper.addLog(getClass().getSimpleName() + " error: " + e.getMessage());
        }
    }

    public void updateDragImage(Message message) {
        int i = message.what;
        if (i == 10) {
            TextView textView = this.tvTopPaoPao;
            if (textView != null) {
                textView.setVisibility(8);
                return;
            }
            return;
        }
        if (i == 13) {
            this.handler.removeMessages(13);
            this.t5BottomView.startColl();
            if (this.startCollAnim || this.isHidePromote) {
                return;
            }
            startPromoteViewAnim();
            return;
        }
        if (i == 14) {
            this.handler.removeMessages(14);
            this.t5BottomView.endColl();
            if (this.startExpandAnim || !this.isHidePromote) {
                return;
            }
            endPromoteViewAnim();
            return;
        }
        if (i == 21) {
            this.handler.removeMessages(22);
            if (RtmManager.getInstance().isLoginRtm()) {
                this.handler.removeMessages(21);
                ((T5HomePresenter) this.mPresenter).startLiveVideo(this.t5LiveVideoView.getVideoPlayerView());
                return;
            }
            int i2 = message.arg1;
            if (i2 == 5) {
                ((T5HomePresenter) this.mPresenter).startLiveVideo(this.t5LiveVideoView.getVideoPlayerView());
                return;
            }
            Message message2 = new Message();
            message2.what = 21;
            message2.arg1 = i2 + 1;
            this.handler.sendMessageDelayed(message2, 500L);
            return;
        }
        if (i == 22) {
            int i3 = message.arg1;
            if (i3 == 0) {
                RtmManager.getInstance().getRtmListInfo();
            }
            if (RtmManager.getInstance().isLoginRtm()) {
                this.handler.removeMessages(22);
                ((T5HomePresenter) this.mPresenter).startLiveVideo(this.t5LiveVideoView.getVideoPlayerView());
            } else {
                if (i3 == 5) {
                    ((T5HomePresenter) this.mPresenter).startLiveVideo(this.t5LiveVideoView.getVideoPlayerView());
                    return;
                }
                Message message3 = new Message();
                message3.what = 22;
                message3.arg1 = i3 + 1;
                this.handler.sendMessageDelayed(message3, 500L);
            }
        }
    }

    public static class MyHandler extends Handler {
        public final WeakReference<T5HomeActivity> mActivity;

        public MyHandler(Looper looper, T5HomeActivity t5HomeActivity) {
            super(looper);
            this.mActivity = new WeakReference<>(t5HomeActivity);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            T5HomeActivity t5HomeActivity = this.mActivity.get();
            if (t5HomeActivity != null) {
                t5HomeActivity.updateDragImage(message);
            }
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T5HomeContract.View
    public void showToiletUnusedDialog() {
        Resources resources;
        int i;
        if (this.isShowToiletUnusedDialog || this.t5Record.getDeviceShared() != null) {
            P p = this.mPresenter;
            if (p != 0) {
                ((T5HomePresenter) p).checkInitWindow(3);
                return;
            }
            return;
        }
        T6Record t6RecordByDeviceId = T6Utils.getT6RecordByDeviceId(this.deviceId, 1);
        this.t5Record = t6RecordByDeviceId;
        if (t6RecordByDeviceId == null || t6RecordByDeviceId.getPetOutTips() == null || this.t5Record.getPetOutTips().size() == 0 || this.t5Record.getPetOutTips() == null || this.t5Record.getPetOutTips().size() <= 0) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < this.t5Record.getPetOutTips().size(); i2++) {
            PetOutTip petOutTip = this.t5Record.getPetOutTips().get(i2);
            if (petOutTip.getOutDay() > 1) {
                resources = getResources();
                i = R.string.Unit_days;
            } else {
                resources = getResources();
                i = R.string.Unit_day;
            }
            String string = resources.getString(i);
            sb.append(getResources().getString(R.string.T_Pet_No_Toilet_Tips, petOutTip.getPetName(), petOutTip.getOutDay() + string));
            arrayList.add(petOutTip.getPetName());
            arrayList.add(petOutTip.getOutDay() + string);
        }
        String[] strArr = (String[]) arrayList.toArray(new String[arrayList.size()]);
        SpannableStringColorsWindow spannableStringColorsWindow = this.petOutWindow;
        if (spannableStringColorsWindow == null || !spannableStringColorsWindow.isShowing()) {
            SpannableStringColorsWindow spannableStringColorsWindow2 = new SpannableStringColorsWindow(this, new SpannableStringColorsWindow.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.49
                public AnonymousClass49() {
                }

                @Override // com.petkit.android.activities.universalWindow.SpannableStringColorsWindow.OnClickListener
                public void onCancel() {
                    ((T5HomePresenter) ((BaseActivity) T5HomeActivity.this).mPresenter).recordUserAction(2);
                    if (((BaseActivity) T5HomeActivity.this).mPresenter != null) {
                        ((T5HomePresenter) ((BaseActivity) T5HomeActivity.this).mPresenter).checkInitWindow(3);
                    }
                }

                @Override // com.petkit.android.activities.universalWindow.SpannableStringColorsWindow.OnClickListener
                public void onConfirm() {
                    if (((BaseActivity) T5HomeActivity.this).mPresenter != null) {
                        ((T5HomePresenter) ((BaseActivity) T5HomeActivity.this).mPresenter).checkInitWindow(3);
                    }
                }
            }, getResources().getString(R.string.T_Toilet_Error_Tips), sb.toString() + getResources().getString(R.string.T_Pet_No_Toilet_Desc), getResources().getString(R.string.I_already_know), getResources().getString(R.string.Not_remind), R.color.new_bind_blue, strArr);
            this.petOutWindow = spannableStringColorsWindow2;
            spannableStringColorsWindow2.show();
            this.isShowToiletUnusedDialog = true;
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$49 */
    public class AnonymousClass49 implements SpannableStringColorsWindow.OnClickListener {
        public AnonymousClass49() {
        }

        @Override // com.petkit.android.activities.universalWindow.SpannableStringColorsWindow.OnClickListener
        public void onCancel() {
            ((T5HomePresenter) ((BaseActivity) T5HomeActivity.this).mPresenter).recordUserAction(2);
            if (((BaseActivity) T5HomeActivity.this).mPresenter != null) {
                ((T5HomePresenter) ((BaseActivity) T5HomeActivity.this).mPresenter).checkInitWindow(3);
            }
        }

        @Override // com.petkit.android.activities.universalWindow.SpannableStringColorsWindow.OnClickListener
        public void onConfirm() {
            if (((BaseActivity) T5HomeActivity.this).mPresenter != null) {
                ((T5HomePresenter) ((BaseActivity) T5HomeActivity.this).mPresenter).checkInitWindow(3);
            }
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$50 */
    public class AnonymousClass50 implements NormalListChoiceCenterWindow.OnClickThreeChoices {
        @Override // com.petkit.android.activities.universalWindow.NormalListChoiceCenterWindow.OnClickThreeChoices
        public void onClickFirstChoice() {
        }

        @Override // com.petkit.android.activities.universalWindow.NormalListChoiceCenterWindow.OnClickThreeChoices
        public void onClickThirdChoice() {
        }

        public AnonymousClass50() {
        }

        @Override // com.petkit.android.activities.universalWindow.NormalListChoiceCenterWindow.OnClickThreeChoices
        public void onClickSecondChoice() {
            ((T5HomePresenter) ((BaseActivity) T5HomeActivity.this).mPresenter).ignorePh();
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T5HomeContract.View
    public void showPhErrorDialog() {
        if (this.phErrorWindow == null) {
            this.phErrorWindow = new NormalListChoiceCenterWindow(this, new NormalListChoiceCenterWindow.OnClickThreeChoices() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.50
                @Override // com.petkit.android.activities.universalWindow.NormalListChoiceCenterWindow.OnClickThreeChoices
                public void onClickFirstChoice() {
                }

                @Override // com.petkit.android.activities.universalWindow.NormalListChoiceCenterWindow.OnClickThreeChoices
                public void onClickThirdChoice() {
                }

                public AnonymousClass50() {
                }

                @Override // com.petkit.android.activities.universalWindow.NormalListChoiceCenterWindow.OnClickThreeChoices
                public void onClickSecondChoice() {
                    ((T5HomePresenter) ((BaseActivity) T5HomeActivity.this).mPresenter).ignorePh();
                }
            }, getString(R.string.T_toilet_ph_error_tips), getString(R.string.T_toilet_ph_error_tips_promp), getString(R.string.I_know), getString(R.string.This_round_not_remind), null);
        }
        this.phErrorWindow.setThreeChoicesTextColor(getResources().getColor(R.color.new_bind_blue), -1, -1);
        if (this.phErrorWindow.isShowing()) {
            return;
        }
        this.phErrorWindow.show(getWindow().getDecorView());
    }

    public void manualShowToiletUnusedDialog() {
        Resources resources;
        int i;
        T6Record t6RecordByDeviceId = T6Utils.getT6RecordByDeviceId(this.deviceId, 1);
        this.t5Record = t6RecordByDeviceId;
        if (t6RecordByDeviceId == null || t6RecordByDeviceId.getPetOutTips() == null || this.t5Record.getPetOutTips().size() == 0 || this.t5Record.getPetOutTips() == null || this.t5Record.getPetOutTips().size() <= 0) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < this.t5Record.getPetOutTips().size(); i2++) {
            PetOutTip petOutTip = this.t5Record.getPetOutTips().get(i2);
            if (petOutTip.getOutDay() > 1) {
                resources = getResources();
                i = R.string.Unit_days;
            } else {
                resources = getResources();
                i = R.string.Unit_day;
            }
            String string = resources.getString(i);
            sb.append(getResources().getString(R.string.T_Pet_No_Toilet_Tips, petOutTip.getPetName(), petOutTip.getOutDay() + string));
            arrayList.add(petOutTip.getPetName());
            arrayList.add(petOutTip.getOutDay() + string);
        }
        String[] strArr = (String[]) arrayList.toArray(new String[arrayList.size()]);
        SpannableStringColorsWindow spannableStringColorsWindow = this.petOutWindow;
        if (spannableStringColorsWindow == null || !spannableStringColorsWindow.isShowing()) {
            SpannableStringColorsWindow spannableStringColorsWindow2 = new SpannableStringColorsWindow(this, new SpannableStringColorsWindow.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.51
                @Override // com.petkit.android.activities.universalWindow.SpannableStringColorsWindow.OnClickListener
                public void onConfirm() {
                }

                public AnonymousClass51() {
                }

                @Override // com.petkit.android.activities.universalWindow.SpannableStringColorsWindow.OnClickListener
                public void onCancel() {
                    ((T5HomePresenter) ((BaseActivity) T5HomeActivity.this).mPresenter).recordUserAction(2);
                }
            }, getResources().getString(R.string.T_Toilet_Error_Tips), sb.toString() + getResources().getString(R.string.T_Pet_No_Toilet_Desc), getResources().getString(R.string.I_already_know), getResources().getString(R.string.Not_remind), R.color.new_bind_blue, strArr);
            this.petOutWindow = spannableStringColorsWindow2;
            spannableStringColorsWindow2.show();
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$51 */
    public class AnonymousClass51 implements SpannableStringColorsWindow.OnClickListener {
        @Override // com.petkit.android.activities.universalWindow.SpannableStringColorsWindow.OnClickListener
        public void onConfirm() {
        }

        public AnonymousClass51() {
        }

        @Override // com.petkit.android.activities.universalWindow.SpannableStringColorsWindow.OnClickListener
        public void onCancel() {
            ((T5HomePresenter) ((BaseActivity) T5HomeActivity.this).mPresenter).recordUserAction(2);
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T5HomeContract.View
    public void showHealthRemindWindow() {
        if (this.isShowHealthRemindWindow || this.t5Record.getDeviceShared() != null) {
            P p = this.mPresenter;
            if (p != 0) {
                ((T5HomePresenter) p).checkInitWindow(4);
                return;
            }
            return;
        }
        String string = getResources().getString(R.string.Toilet_health_remind_prompt_two);
        String string2 = getResources().getString(R.string.Toilet_health_remind_prompt_three);
        String string3 = getResources().getString(R.string.Toilet_health_remind_prompt_one, string, string2);
        SpannableStringColorsPicPromptWindow spannableStringColorsPicPromptWindow = this.promptWindow;
        if (spannableStringColorsPicPromptWindow == null || !spannableStringColorsPicPromptWindow.isShowing()) {
            RelatedProductsInfor relatedProductsInfor = this.relatedProductsInfor;
            if (relatedProductsInfor != null && relatedProductsInfor.getStandard().getT6().getShelf() != null && !TextUtils.isEmpty(this.relatedProductsInfor.getStandard().getT6().getShelf().getShareUrl())) {
                this.promptWindow = new SpannableStringColorsPicPromptWindow(this, getResources().getString(R.string.Toilet_health_remind_title), R.drawable.toilet_health_remind_t5, new SpannableStringColorsPicPromptWindow.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.52
                    public AnonymousClass52() {
                    }

                    @Override // com.petkit.android.activities.universalWindow.SpannableStringColorsPicPromptWindow.OnClickListener
                    public void onCancel() {
                        ((T5HomePresenter) ((BaseActivity) T5HomeActivity.this).mPresenter).recordUserAction(1);
                    }

                    @Override // com.petkit.android.activities.universalWindow.SpannableStringColorsPicPromptWindow.OnClickListener
                    public void onConfirm() {
                        ((T5HomePresenter) ((BaseActivity) T5HomeActivity.this).mPresenter).recordUserAction(1);
                        if (T5HomeActivity.this.relatedProductsInfor.getStandard().getT6().getShelf() != null) {
                            T5HomeActivity t5HomeActivity = T5HomeActivity.this;
                            t5HomeActivity.launchActivity(WebviewActivity.newIntent(t5HomeActivity, "", t5HomeActivity.relatedProductsInfor.getStandard().getT6().getShelf().getShareUrl()));
                        }
                    }
                }, string3, getResources().getString(R.string.Go_to_buy), getResources().getString(R.string.Think_again), string, string2);
            } else {
                this.promptWindow = new SpannableStringColorsPicPromptWindow(this, getResources().getString(R.string.Toilet_health_remind_title), string3, getResources().getString(R.string.Feeder_i_know), R.drawable.toilet_health_remind, new SpannableStringColorsPicPromptWindow.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.53
                    public AnonymousClass53() {
                    }

                    @Override // com.petkit.android.activities.universalWindow.SpannableStringColorsPicPromptWindow.OnClickListener
                    public void onCancel() {
                        if (((BaseActivity) T5HomeActivity.this).mPresenter != null) {
                            ((T5HomePresenter) ((BaseActivity) T5HomeActivity.this).mPresenter).recordUserAction(1);
                            ((T5HomePresenter) ((BaseActivity) T5HomeActivity.this).mPresenter).checkInitWindow(4);
                        }
                    }

                    @Override // com.petkit.android.activities.universalWindow.SpannableStringColorsPicPromptWindow.OnClickListener
                    public void onConfirm() {
                        if (((BaseActivity) T5HomeActivity.this).mPresenter != null) {
                            ((T5HomePresenter) ((BaseActivity) T5HomeActivity.this).mPresenter).recordUserAction(1);
                            ((T5HomePresenter) ((BaseActivity) T5HomeActivity.this).mPresenter).checkInitWindow(4);
                        }
                    }
                }, string, string2);
            }
            this.promptWindow.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$$ExternalSyntheticLambda26
                @Override // android.widget.PopupWindow.OnDismissListener
                public final void onDismiss() {
                    this.f$0.lambda$showHealthRemindWindow$51();
                }
            });
            this.promptWindow.show(getWindow().getDecorView());
            this.isShowHealthRemindWindow = true;
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$52 */
    public class AnonymousClass52 implements SpannableStringColorsPicPromptWindow.OnClickListener {
        public AnonymousClass52() {
        }

        @Override // com.petkit.android.activities.universalWindow.SpannableStringColorsPicPromptWindow.OnClickListener
        public void onCancel() {
            ((T5HomePresenter) ((BaseActivity) T5HomeActivity.this).mPresenter).recordUserAction(1);
        }

        @Override // com.petkit.android.activities.universalWindow.SpannableStringColorsPicPromptWindow.OnClickListener
        public void onConfirm() {
            ((T5HomePresenter) ((BaseActivity) T5HomeActivity.this).mPresenter).recordUserAction(1);
            if (T5HomeActivity.this.relatedProductsInfor.getStandard().getT6().getShelf() != null) {
                T5HomeActivity t5HomeActivity = T5HomeActivity.this;
                t5HomeActivity.launchActivity(WebviewActivity.newIntent(t5HomeActivity, "", t5HomeActivity.relatedProductsInfor.getStandard().getT6().getShelf().getShareUrl()));
            }
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$53 */
    public class AnonymousClass53 implements SpannableStringColorsPicPromptWindow.OnClickListener {
        public AnonymousClass53() {
        }

        @Override // com.petkit.android.activities.universalWindow.SpannableStringColorsPicPromptWindow.OnClickListener
        public void onCancel() {
            if (((BaseActivity) T5HomeActivity.this).mPresenter != null) {
                ((T5HomePresenter) ((BaseActivity) T5HomeActivity.this).mPresenter).recordUserAction(1);
                ((T5HomePresenter) ((BaseActivity) T5HomeActivity.this).mPresenter).checkInitWindow(4);
            }
        }

        @Override // com.petkit.android.activities.universalWindow.SpannableStringColorsPicPromptWindow.OnClickListener
        public void onConfirm() {
            if (((BaseActivity) T5HomeActivity.this).mPresenter != null) {
                ((T5HomePresenter) ((BaseActivity) T5HomeActivity.this).mPresenter).recordUserAction(1);
                ((T5HomePresenter) ((BaseActivity) T5HomeActivity.this).mPresenter).checkInitWindow(4);
            }
        }
    }

    public /* synthetic */ void lambda$showHealthRemindWindow$51() {
        ((T5HomePresenter) this.mPresenter).checkInitWindow(4);
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T5HomeContract.View
    public void showKittenProtectWindow() {
        showKittenProtectDialog();
    }

    @Subscriber
    public void d4shVlogDelete(D4shVlogDeleteMsg d4shVlogDeleteMsg) {
        T6VlogRecordAdapter t6VlogRecordAdapter = this.t6VlogRecordAdapter;
        if (t6VlogRecordAdapter != null) {
            t6VlogRecordAdapter.removeAll();
        }
    }

    @Subscriber
    public void vlogStateChanged(VlogStateChanged vlogStateChanged) {
        if (vlogStateChanged.isRefreshRemoteData()) {
            T6VlogRecordAdapter t6VlogRecordAdapter = this.t6VlogRecordAdapter;
            if (t6VlogRecordAdapter != null) {
                t6VlogRecordAdapter.removeAll();
                return;
            }
            return;
        }
        T6VlogRecordAdapter t6VlogRecordAdapter2 = this.t6VlogRecordAdapter;
        if (t6VlogRecordAdapter2 != null) {
            t6VlogRecordAdapter2.setRefreshLoadingStatus(false);
            this.t6VlogRecordAdapter.notifyDataSetChanged();
        }
    }

    public void setDeviceStatus(T6Record t6Record) {
        if (t6Record == null || t6Record.getState() == null) {
            return;
        }
        int overall = t6Record.getState().getOverall();
        if (overall == 2) {
            this.toolbarTitleStatusOne.setText(getString(R.string.Offline));
            this.toolbarTitleStatusOne.setBackgroundResource(R.drawable.solid_t4_status_gray);
            this.toolbarTitleStatusOne.setVisibility(0);
            this.toolbarTitleStatusTwo.setVisibility(8);
            this.toolbarTitleStatusOne.setOnClickListener(null);
            return;
        }
        if (overall == 3) {
            this.toolbarTitleStatusOne.setText(getString(R.string.Mate_ota));
            this.toolbarTitleStatusOne.setBackgroundResource(R.drawable.solid_t4_status_green);
            this.toolbarTitleStatusOne.setVisibility(0);
            this.toolbarTitleStatusTwo.setVisibility(8);
            this.toolbarTitleStatusOne.setOnClickListener(null);
            return;
        }
        if (overall == 4) {
            this.toolbarTitleStatusOne.setText(getString(R.string.State_error));
            this.toolbarTitleStatusOne.setBackgroundResource(R.drawable.solid_t4_status_gray);
            this.toolbarTitleStatusOne.setVisibility(0);
            this.toolbarTitleStatusTwo.setVisibility(8);
            this.toolbarTitleStatusOne.setOnClickListener(null);
            return;
        }
        if (t6Record.getState().getPower() == 0) {
            this.toolbarTitleStatusOne.setText(getString(R.string.Power_off));
            this.toolbarTitleStatusOne.setBackgroundResource(R.drawable.solid_t4_status_gray);
            this.toolbarTitleStatusOne.setVisibility(0);
            this.toolbarTitleStatusTwo.setVisibility(8);
            this.toolbarTitleStatusOne.setOnClickListener(null);
            return;
        }
        int i = -65;
        int i2 = 5;
        if (t6Record.getState().getSprayState() == 1) {
            if (t6Record.getSettings().getAutoWork() == 0 && t6Record.getSettings().getFixedTimeClear() == 0 && t6Record.getSettings().getFixedTimeSpray() == 0 && t6Record.getSettings().getAutoSpray() == 0) {
                this.toolbarTitleStatusOne.setText(getString(R.string.Smart_set_disable));
                this.toolbarTitleStatusOne.setBackgroundResource(R.drawable.solid_t4_status_green);
                this.toolbarTitleStatusOne.setVisibility(0);
                this.toolbarTitleStatusTwo.setVisibility(8);
                this.toolbarTitleStatusOne.setOnClickListener(null);
                return;
            }
            ArrayList arrayList = new ArrayList();
            int i3 = 0;
            for (int i4 = 6; i3 < i4 && arrayList.size() < 2; i4 = 6) {
                if (i3 != 0) {
                    if (i3 != 1) {
                        if (i3 != 2) {
                            if (i3 != 3) {
                                if (i3 != 4) {
                                    if (i3 == i2) {
                                        if (t6Record.getState().getWifi().getRsq() <= i && t6Record.getState().getWifi().getRsq() > -75) {
                                            if (TimeUtils.getInstance().getCurrentSeconds() - CommonUtils.getSysLongMap(this, Consts.T6_WIFI_SIGINAL_WINDOW + t6Record.getDeviceId()) > 2592000) {
                                                arrayList.add(getString(R.string.Signal_weak));
                                                if (arrayList.size() == 1) {
                                                    this.toolbarTitleStatusOne.setBackgroundResource(R.drawable.solid_t4_status_green);
                                                } else {
                                                    this.toolbarTitleStatusTwo.setBackgroundResource(R.drawable.solid_t4_status_green);
                                                }
                                            }
                                        } else if (t6Record.getState().getWifi().getRsq() < -75) {
                                            arrayList.add(getString(R.string.Signal_weak));
                                            if (arrayList.size() == 1) {
                                                this.toolbarTitleStatusOne.setBackgroundResource(R.drawable.solid_t4_status_green);
                                            } else {
                                                this.toolbarTitleStatusTwo.setBackgroundResource(R.drawable.solid_t4_status_green);
                                            }
                                        }
                                    }
                                } else if (t6Record.getSettings().getFixedTimeSpray() == 1) {
                                    arrayList.add(getString(R.string.T5_regular_deodorant));
                                    if (arrayList.size() == 1) {
                                        this.toolbarTitleStatusOne.setBackgroundResource(R.drawable.solid_t4_status_blue);
                                    } else {
                                        this.toolbarTitleStatusTwo.setBackgroundResource(R.drawable.solid_t4_status_blue);
                                    }
                                }
                            } else if (t6Record.getSettings().getKitten() == 0 && t6Record.getSettings().getFixedTimeClear() == 1) {
                                arrayList.add(getString(R.string.Regular_clean));
                                if (arrayList.size() == 1) {
                                    this.toolbarTitleStatusOne.setBackgroundResource(R.drawable.solid_t4_status_red);
                                } else {
                                    this.toolbarTitleStatusTwo.setBackgroundResource(R.drawable.solid_t4_status_red);
                                }
                            }
                        } else if (t6Record.getSettings().getAutoSpray() == 1) {
                            arrayList.add(getString(R.string.T5_auto_deodorant));
                            if (arrayList.size() == 1) {
                                this.toolbarTitleStatusOne.setBackgroundResource(R.drawable.solid_t4_status_blue);
                            } else {
                                this.toolbarTitleStatusTwo.setBackgroundResource(R.drawable.solid_t4_status_blue);
                            }
                        }
                    } else if (t6Record.getSettings().getKitten() == 0 && t6Record.getSettings().getAutoWork() == 1) {
                        arrayList.add(getString(R.string.Auto_clean));
                        this.toolbarTitleStatusOne.setBackgroundResource(R.drawable.solid_t4_status_red);
                    }
                } else if (t6Record.getSettings().getKitten() == 1) {
                    arrayList.add(getString(R.string.Kitten_protection));
                    this.toolbarTitleStatusOne.setBackgroundResource(R.drawable.solid_t4_status_dark_green);
                    this.toolbarTitleStatusOne.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$$ExternalSyntheticLambda10
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            this.f$0.lambda$setDeviceStatus$52(view);
                        }
                    });
                }
                i3++;
                i = -65;
                i2 = 5;
            }
            if (arrayList.size() == 1) {
                this.toolbarTitleStatusOne.setVisibility(0);
                this.toolbarTitleStatusTwo.setVisibility(8);
                this.toolbarTitleStatusOne.setText((CharSequence) arrayList.get(0));
                return;
            } else {
                if (arrayList.size() == 2) {
                    this.toolbarTitleStatusOne.setVisibility(0);
                    this.toolbarTitleStatusTwo.setVisibility(0);
                    this.toolbarTitleStatusOne.setText((CharSequence) arrayList.get(0));
                    this.toolbarTitleStatusTwo.setText((CharSequence) arrayList.get(1));
                    return;
                }
                if (arrayList.size() == 0) {
                    this.toolbarTitleStatusOne.setVisibility(8);
                    this.toolbarTitleStatusTwo.setVisibility(8);
                    return;
                }
                return;
            }
        }
        if (t6Record.getSettings().getAutoWork() == 0 && t6Record.getSettings().getFixedTimeClear() == 0 && t6Record.getSettings().getFixedTimeSpray() == 0 && t6Record.getSettings().getAutoSpray() == 0) {
            this.toolbarTitleStatusOne.setText(getString(R.string.Smart_set_disable));
            this.toolbarTitleStatusOne.setBackgroundResource(R.drawable.solid_t4_status_green);
            this.toolbarTitleStatusOne.setVisibility(0);
            this.toolbarTitleStatusTwo.setVisibility(8);
            this.toolbarTitleStatusOne.setOnClickListener(null);
            return;
        }
        ArrayList arrayList2 = new ArrayList();
        int i5 = 6;
        int i6 = 0;
        for (int i7 = 2; i6 < i5 && arrayList2.size() < i7; i7 = 2) {
            if (i6 != 0) {
                if (i6 != 1) {
                    if (i6 != 3) {
                        if (i6 == 5) {
                            if (t6Record.getState().getWifi().getRsq() <= -65 && t6Record.getState().getWifi().getRsq() > -75) {
                                if (TimeUtils.getInstance().getCurrentSeconds() - CommonUtils.getSysLongMap(this, Consts.T6_WIFI_SIGINAL_WINDOW + t6Record.getDeviceId()) > 2592000) {
                                    arrayList2.add(getString(R.string.Signal_weak));
                                    if (arrayList2.size() == 1) {
                                        this.toolbarTitleStatusOne.setBackgroundResource(R.drawable.solid_t4_status_green);
                                    } else {
                                        this.toolbarTitleStatusTwo.setBackgroundResource(R.drawable.solid_t4_status_green);
                                    }
                                }
                            } else if (t6Record.getState().getWifi().getRsq() < -75) {
                                arrayList2.add(getString(R.string.Signal_weak));
                                if (arrayList2.size() == 1) {
                                    this.toolbarTitleStatusOne.setBackgroundResource(R.drawable.solid_t4_status_green);
                                } else {
                                    this.toolbarTitleStatusTwo.setBackgroundResource(R.drawable.solid_t4_status_green);
                                }
                            }
                        }
                    } else if (t6Record.getSettings().getKitten() == 0 && t6Record.getSettings().getFixedTimeClear() == 1) {
                        arrayList2.add(getString(R.string.Regular_clean));
                        if (arrayList2.size() == 1) {
                            this.toolbarTitleStatusOne.setBackgroundResource(R.drawable.solid_t4_status_red);
                        } else {
                            this.toolbarTitleStatusTwo.setBackgroundResource(R.drawable.solid_t4_status_red);
                        }
                    }
                } else if (t6Record.getSettings().getKitten() == 0 && t6Record.getSettings().getAutoWork() == 1) {
                    arrayList2.add(getString(R.string.Auto_clean));
                    this.toolbarTitleStatusOne.setBackgroundResource(R.drawable.solid_t4_status_red);
                }
            } else if (t6Record.getSettings().getKitten() == 1) {
                arrayList2.add(getString(R.string.Kitten_protection));
                this.toolbarTitleStatusOne.setBackgroundResource(R.drawable.solid_t4_status_dark_green);
                this.toolbarTitleStatusOne.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$$ExternalSyntheticLambda11
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$setDeviceStatus$53(view);
                    }
                });
            }
            i6++;
            i5 = 6;
        }
        if (arrayList2.size() == 1) {
            this.toolbarTitleStatusOne.setVisibility(0);
            this.toolbarTitleStatusTwo.setVisibility(8);
            this.toolbarTitleStatusOne.setText((CharSequence) arrayList2.get(0));
        } else {
            if (arrayList2.size() == 2) {
                this.toolbarTitleStatusOne.setVisibility(0);
                this.toolbarTitleStatusTwo.setVisibility(0);
                this.toolbarTitleStatusOne.setText((CharSequence) arrayList2.get(0));
                this.toolbarTitleStatusTwo.setText((CharSequence) arrayList2.get(1));
                return;
            }
            if (arrayList2.size() == 0) {
                this.toolbarTitleStatusOne.setText(getString(R.string.Smart_set_disable));
                this.toolbarTitleStatusOne.setBackgroundResource(R.drawable.solid_t4_status_green);
                this.toolbarTitleStatusOne.setVisibility(0);
                this.toolbarTitleStatusTwo.setVisibility(8);
            }
        }
    }

    public /* synthetic */ void lambda$setDeviceStatus$52(View view) {
        showKittenProtectionWindow();
    }

    public /* synthetic */ void lambda$setDeviceStatus$53(View view) {
        showKittenProtectionWindow();
    }

    public void showKittenProtectionWindow() {
        KittenProtectionWindow kittenProtectionWindow = this.kittenProtectionWindow;
        if (kittenProtectionWindow == null || !kittenProtectionWindow.isShowing()) {
            KittenProtectionWindow kittenProtectionWindow2 = new KittenProtectionWindow(this, 21, false, R.color.login_new_blue, true);
            this.kittenProtectionWindow = kittenProtectionWindow2;
            kittenProtectionWindow2.showAtLocation(getWindow().getDecorView(), 17, 0, 0);
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$54 */
    public class AnonymousClass54 implements ThreeChoicesWindow.OnClickThreeChoices {
        public AnonymousClass54() {
        }

        @Override // com.petkit.android.activities.device.widget.ThreeChoicesWindow.OnClickThreeChoices
        public void onClickFirstChoice() {
            T5HomeActivity t5HomeActivity = T5HomeActivity.this;
            t5HomeActivity.launchActivity(T6WorkingTimeActivity.newIntent(t5HomeActivity, t5HomeActivity.deviceId, T5HomeActivity.this.deviceType));
            T5HomeActivity.this.threeChoicesWindow.dismiss();
        }

        @Override // com.petkit.android.activities.device.widget.ThreeChoicesWindow.OnClickThreeChoices
        public void onClickSecondChoice() {
            ((T5HomePresenter) ((BaseActivity) T5HomeActivity.this).mPresenter).temporaryOpenCamera();
            T5HomeActivity.this.show5MinWindowTips();
            T5HomeActivity.this.threeChoicesWindow.dismiss();
        }

        @Override // com.petkit.android.activities.device.widget.ThreeChoicesWindow.OnClickThreeChoices
        public void onClickThirdChoice() {
            T5HomeActivity.this.threeChoicesWindow.dismiss();
        }
    }

    public void showTurnOnCameraWindow() {
        if (this.threeChoicesWindow == null) {
            ThreeChoicesWindow threeChoicesWindow = new ThreeChoicesWindow(this, new ThreeChoicesWindow.OnClickThreeChoices() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.54
                public AnonymousClass54() {
                }

                @Override // com.petkit.android.activities.device.widget.ThreeChoicesWindow.OnClickThreeChoices
                public void onClickFirstChoice() {
                    T5HomeActivity t5HomeActivity = T5HomeActivity.this;
                    t5HomeActivity.launchActivity(T6WorkingTimeActivity.newIntent(t5HomeActivity, t5HomeActivity.deviceId, T5HomeActivity.this.deviceType));
                    T5HomeActivity.this.threeChoicesWindow.dismiss();
                }

                @Override // com.petkit.android.activities.device.widget.ThreeChoicesWindow.OnClickThreeChoices
                public void onClickSecondChoice() {
                    ((T5HomePresenter) ((BaseActivity) T5HomeActivity.this).mPresenter).temporaryOpenCamera();
                    T5HomeActivity.this.show5MinWindowTips();
                    T5HomeActivity.this.threeChoicesWindow.dismiss();
                }

                @Override // com.petkit.android.activities.device.widget.ThreeChoicesWindow.OnClickThreeChoices
                public void onClickThirdChoice() {
                    T5HomeActivity.this.threeChoicesWindow.dismiss();
                }
            }, getResources().getString(R.string.T6_open_camera_tip), getResources().getString(R.string.Open_whole_day_camera), getResources().getString(R.string.Camera_open_five_minute), getResources().getString(R.string.Cancel));
            this.threeChoicesWindow = threeChoicesWindow;
            threeChoicesWindow.setThreeChoicesTextColor(getResources().getColor(R.color.new_bind_blue), getResources().getColor(R.color.new_bind_blue), getResources().getColor(R.color.gray));
        }
        this.threeChoicesWindow.show(getWindow().getDecorView());
    }

    public void show5MinWindowTips() {
        new NewIKnowWindow(this, "", getResources().getString(R.string.Camera_open_five_minute_tips), "").show(getWindow().getDecorView());
    }

    private void setWorkInMaintenance() {
        setViewVisibleOrGone(this.llMaintenanceMode, 0);
        setViewVisibleOrGone(this.srl, 8);
        PromoteView promoteView = this.promoteView;
        if (promoteView != null) {
            promoteView.clearAnimation();
        }
        setViewVisibleOrGone(this.promoteView, 8);
        setViewVisibleOrGone(this.t5BottomView, 8);
        setViewVisibleOrGone(this.rlPop, 8);
        if (1 == this.t5Record.getState().getWorkState().getWorkProcess() / 10) {
            if (this.lastMaintenanceType == 1) {
                DataHelper.setBooleanSF(this, Constants.T5_START_MAINTENANCE_MODE_NORMAL_FLAG, Boolean.FALSE);
                return;
            }
            boolean booleanSF = DataHelper.getBooleanSF(this, Constants.T5_START_MAINTENANCE_MODE_NORMAL_FLAG);
            this.t5Record = T6Utils.getT6RecordByDeviceId(this.t5Record.getDeviceId(), 1);
            StringBuilder sb = new StringBuilder();
            sb.append("setupMaintenanceModeView,startMaintenance type:");
            sb.append(booleanSF ? "normal" : "error");
            PetkitLog.d(sb.toString());
            StringBuilder sb2 = new StringBuilder();
            sb2.append("setupMaintenanceModeView,startMaintenance type:");
            sb2.append(booleanSF ? "normal" : "error");
            LogcatStorageHelper.addLog(sb2.toString());
            if (!booleanSF) {
                this.t5Record.setMaintenanceTime((int) (System.currentTimeMillis() / 1000));
                this.t5Record.save();
            }
            DataHelper.setBooleanSF(this, Constants.T5_START_MAINTENANCE_MODE_NORMAL_FLAG, Boolean.FALSE);
            this.rlWorkPanel.setVisibility(0);
            if (this.t5Record.getModelCode() == 2) {
                this.ivMaintain.setImageResource(R.drawable.t5_2_maintain_loading);
            } else {
                this.ivMaintain.setImageResource(R.drawable.t5_maintain_loading);
            }
            this.btnMaintenanceModeContinueToRun.setVisibility(8);
            this.btnMaintenanceModePauseClean.setVisibility(8);
            this.btnMaintenanceModeTerminateClean.setVisibility(0);
            this.btnMaintenanceModeTerminateClean.setTextColor(getResources().getColor(R.color.t4_text_gray));
            this.btnMaintenanceModeTerminateClean.setBackgroundResource(R.drawable.solid_t4_home_gray);
            startRotateAnimation();
            this.tvWorkState.setText(getResources().getString(R.string.Starting));
            stopMaintenanceLoading();
            this.llMaintenanceModeStatus.setGravity(17);
            this.tvMaintenanceStatus.setPadding(0, 0, 0, 0);
            this.tvMaintenanceStatus.setText(getResources().getString(R.string.Sand_warehouse_in_operation));
            this.tvMaintenanceDesc.setVisibility(8);
            timerForStartMaintenance();
            this.lastMaintenanceType = 1;
            return;
        }
        if (2 == this.t5Record.getState().getWorkState().getWorkProcess() / 10 && 2 == this.t5Record.getState().getWorkState().getWorkProcess() % 10) {
            this.rlWorkPanel.setVisibility(0);
            this.handler.removeCallbacks(this.enterMaintenance);
            this.handler.removeCallbacks(this.maintenanceRunTimer);
            if (this.t5Record.getModelCode() == 2) {
                this.ivMaintain.setImageResource(R.drawable.t5_2_maintain_loading);
            } else {
                this.ivMaintain.setImageResource(R.drawable.t5_maintain_loading);
            }
            stopRotateAnimation();
            this.tvWorkState.setText(getResources().getString(R.string.Paused));
            this.btnMaintenanceModeContinueToRun.setVisibility(4);
            this.btnMaintenanceModePauseClean.setVisibility(8);
            this.btnMaintenanceModeTerminateClean.setVisibility(8);
            stopMaintenanceLoading();
            this.llMaintenanceModeStatus.setGravity(17);
            this.tvMaintenanceStatus.setPadding(0, 0, 0, 0);
            this.tvMaintenanceDesc.setVisibility(8);
            int safeWarn = this.t5Record.getState().getWorkState().getSafeWarn();
            if (safeWarn != 0) {
                if (safeWarn == 1) {
                    this.tvMaintenanceStatus.setText(getResources().getString(R.string.Safe_warn_atp));
                } else if (safeWarn == 3) {
                    this.tvMaintenanceStatus.setText(getResources().getString(R.string.T5_magnet_uninstall_pause));
                } else {
                    this.tvMaintenanceStatus.setText(getResources().getString(R.string.Safe_warn_general));
                }
            } else if (this.t5Record.getState().getPetInTime() == 0) {
                this.tvMaintenanceStatus.setText(getResources().getString(R.string.Device_safety_testing));
            } else {
                this.tvMaintenanceStatus.setText(getResources().getString(R.string.T4_pet_go_into_device));
            }
            this.lastMaintenanceType = 2;
            return;
        }
        if (3 == this.t5Record.getState().getWorkState().getWorkProcess() / 10) {
            this.rlWorkPanel.setVisibility(0);
            this.handler.removeCallbacks(this.enterMaintenance);
            this.handler.removeCallbacks(this.maintenanceRunTimer);
            if (this.t5Record.getModelCode() == 2) {
                this.ivMaintain.setImageResource(R.drawable.t5_2_maintain_loading);
            } else {
                this.ivMaintain.setImageResource(R.drawable.t5_maintain_loading);
            }
            this.btnMaintenanceModeTerminateClean.setVisibility(8);
            this.btnMaintenanceModePauseClean.setVisibility(0);
            this.btnMaintenanceModeContinueToRun.setVisibility(8);
            this.tvWorkState.setText(getResources().getString(R.string.Resetting));
            startRotateAnimation();
            stopMaintenanceLoading();
            this.llMaintenanceModeStatus.setGravity(17);
            this.tvMaintenanceStatus.setPadding(0, 0, 0, 0);
            this.tvMaintenanceStatus.setText(getResources().getString(R.string.Sand_warehouse_is_being_reset));
            this.tvMaintenanceDesc.setVisibility(8);
            this.lastMaintenanceType = 3;
            return;
        }
        if (4 == this.t5Record.getState().getWorkState().getWorkProcess() / 10) {
            this.rlWorkPanel.setVisibility(0);
            if (this.t5Record.getModelCode() == 2) {
                this.ivMaintain.setImageResource(R.drawable.t5_2_maintain_loading);
            } else {
                this.ivMaintain.setImageResource(R.drawable.t5_maintain_loading);
            }
            this.tvWorkState.setText(getResources().getString(R.string.Paused));
            stopRotateAnimation();
            if (2 == this.t5Record.getState().getWorkState().getWorkProcess() % 10) {
                this.btnMaintenanceModeContinueToRun.setVisibility(4);
                this.btnMaintenanceModePauseClean.setVisibility(8);
                this.btnMaintenanceModeTerminateClean.setVisibility(8);
                stopMaintenanceLoading();
                this.tvMaintenanceStatus.setPadding(ArmsUtils.dip2px(this, 40.0f), 0, ArmsUtils.dip2px(this, 40.0f), 0);
                this.llMaintenanceModeStatus.setGravity(17);
                this.tvMaintenanceDesc.setVisibility(8);
                int safeWarn2 = this.t5Record.getState().getWorkState().getSafeWarn();
                if (safeWarn2 != 0) {
                    if (safeWarn2 == 1) {
                        this.tvMaintenanceStatus.setText(getResources().getString(R.string.Safe_warn_atp));
                    } else if (safeWarn2 == 3) {
                        this.tvMaintenanceStatus.setText(getString(R.string.T4_hallt_error));
                    } else {
                        this.tvMaintenanceStatus.setText(getResources().getString(R.string.Safe_warn_general));
                    }
                } else if (this.t5Record.getState().getPetInTime() == 0) {
                    this.tvMaintenanceStatus.setText(getResources().getString(R.string.Device_safety_testing));
                } else {
                    this.tvMaintenanceStatus.setText(getResources().getString(R.string.T4_pet_go_into_device));
                }
            } else {
                this.btnMaintenanceModeTerminateClean.setVisibility(8);
                this.btnMaintenanceModeContinueToRun.setVisibility(0);
                this.btnMaintenanceModePauseClean.setVisibility(8);
                stopMaintenanceLoading();
                this.llMaintenanceModeStatus.setGravity(17);
                this.tvMaintenanceStatus.setPadding(ArmsUtils.dip2px(this, 40.0f), 0, ArmsUtils.dip2px(this, 40.0f), 0);
                this.tvMaintenanceDesc.setVisibility(8);
                this.tvMaintenanceStatus.setText(getResources().getString(R.string.Device_pause_prompt, String.valueOf((this.t5Record.getState().getWorkState().getStopTime() > 0 ? this.t5Record.getState().getWorkState().getStopTime() : this.t5Record.getSettings().getStopTime()) / 60)));
            }
            this.lastMaintenanceType = 4;
        }
    }

    private void setWorkInNormal() {
        MySmartRefreshView mySmartRefreshView = this.srl;
        if (mySmartRefreshView == null || mySmartRefreshView.getVisibility() != 0) {
            setViewVisibleOrGone(this.srl, 0);
            setViewVisibleOrGone(this.t5BottomView, 0);
            if (this.showPromoteView) {
                setViewVisibleOrGone(this.promoteView, 0);
            }
            setViewVisibleOrGone(this.llMaintenanceMode, 8);
            this.lastMaintenanceType = 0;
        }
    }

    private void setViewVisibleOrGone(View view, int i) {
        if (view != null) {
            view.setVisibility(i);
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$55 */
    public class AnonymousClass55 implements Runnable {
        public AnonymousClass55() {
        }

        @Override // java.lang.Runnable
        public void run() {
            T5HomeActivity.this.stopRotateAnimation();
            T5HomeActivity.this.rlWorkPanel.setVisibility(8);
            if (T5HomeActivity.this.t5Record.getModelCode() == 2) {
                T5HomeActivity.this.ivMaintain.setImageResource(R.drawable.t5_2_maintain_normal);
            } else {
                T5HomeActivity.this.ivMaintain.setImageResource(R.drawable.t5_maintain_normal);
            }
            T5HomeActivity.this.startMaintenanceLoading();
            T5HomeActivity.this.llMaintenanceModeStatus.setGravity(3);
            T5HomeActivity.this.tvMaintenanceStatus.setPadding(0, 0, 0, 0);
            T5HomeActivity.this.tvMaintenanceDesc.setVisibility(0);
            T5HomeActivity.this.maintenanceRunTimes = System.currentTimeMillis() - (((long) T5HomeActivity.this.t5Record.getMaintenanceTime()) * 1000);
            T5HomeActivity.access$8738(T5HomeActivity.this, 1000L);
            PetkitLog.d("T5-enterMaintenance,maintenanceTime:" + T5HomeActivity.this.t5Record.getMaintenanceTime() + ",maintenanceRunTimes:" + T5HomeActivity.this.maintenanceRunTimes);
            T5HomeActivity.this.handler.removeCallbacks(T5HomeActivity.this.maintenanceRunTimer);
            T5HomeActivity.this.handler.postDelayed(T5HomeActivity.this.maintenanceRunTimer, 0L);
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$56 */
    public class AnonymousClass56 implements Runnable {
        public AnonymousClass56() {
        }

        @Override // java.lang.Runnable
        @SuppressLint({"DefaultLocale"})
        public void run() {
            PetkitLog.d("maintenanceRunTimer,maintenanceRunTimes:" + T5HomeActivity.this.maintenanceRunTimes);
            T5HomeActivity t5HomeActivity = T5HomeActivity.this;
            t5HomeActivity.tvMaintenanceStatus.setText(t5HomeActivity.getResources().getString(R.string.Maintenance_mode_time_consume, String.format("%02d:%02d", Long.valueOf(T5HomeActivity.this.maintenanceRunTimes / 60), Long.valueOf(T5HomeActivity.this.maintenanceRunTimes % 60))));
            T5HomeActivity.access$8714(T5HomeActivity.this, 1L);
            T5HomeActivity.this.handler.postDelayed(this, 1000L);
        }
    }

    public void startMaintenanceLoading() {
        if (this.maintenanceAnimator == null) {
            Animator animatorLoadAnimator = AnimatorInflater.loadAnimator(this, R.animator.maintenance_mode_loading_infinite);
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

    public void stopRotateAnimation() {
        this.ivCircleProgress.setVisibility(8);
        ObjectAnimator objectAnimator = this.rotateAnimator;
        if (objectAnimator != null) {
            objectAnimator.cancel();
            this.rotateAnimator = null;
        }
    }

    private void timerForStartMaintenance() {
        int maintenanceTime = this.t5Record.getMaintenanceTime();
        long jCurrentTimeMillis = System.currentTimeMillis() - (((long) maintenanceTime) * 1000);
        PetkitLog.d("timerForStartMaintenance,maintenanceTime:" + maintenanceTime + ",xTime:" + jCurrentTimeMillis);
        LogcatStorageHelper.addLog("timerForStartMaintenance,maintenanceTime:" + maintenanceTime + ",xTime:" + jCurrentTimeMillis);
        if (jCurrentTimeMillis >= 15000) {
            this.maintenanceRunTimes = jCurrentTimeMillis / 1000;
            this.handler.removeCallbacks(this.enterMaintenance);
            this.handler.postDelayed(this.enterMaintenance, 0L);
        } else {
            this.handler.removeCallbacks(this.enterMaintenance);
            this.handler.postDelayed(this.enterMaintenance, 15000 - jCurrentTimeMillis);
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$57 */
    public class AnonymousClass57 implements TipWindow.TipClickListener {
        public AnonymousClass57() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.widget.frame.TipWindow.TipClickListener
        public void onCancelClick() {
            T5HomeActivity.this.maintenanceModeWindow.dismiss();
        }

        @Override // com.petkit.android.activities.petkitBleDevice.widget.frame.TipWindow.TipClickListener
        public void onConfirmClick() {
            T5HomeActivity.this.maintenanceModeWindow.dismiss();
            ((T5HomePresenter) ((BaseActivity) T5HomeActivity.this).mPresenter).startMaintenance(T5HomeActivity.this.deviceId);
        }
    }

    private void showMaintenanceModeWindow() {
        TipWindow tipWindow = this.maintenanceModeWindow;
        if (tipWindow == null || !tipWindow.isShowing()) {
            TipWindow tipWindow2 = new TipWindow(this, getResources().getString(R.string.Maintenance_mode_confirm_words), new TipWindow.TipClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.57
                public AnonymousClass57() {
                }

                @Override // com.petkit.android.activities.petkitBleDevice.widget.frame.TipWindow.TipClickListener
                public void onCancelClick() {
                    T5HomeActivity.this.maintenanceModeWindow.dismiss();
                }

                @Override // com.petkit.android.activities.petkitBleDevice.widget.frame.TipWindow.TipClickListener
                public void onConfirmClick() {
                    T5HomeActivity.this.maintenanceModeWindow.dismiss();
                    ((T5HomePresenter) ((BaseActivity) T5HomeActivity.this).mPresenter).startMaintenance(T5HomeActivity.this.deviceId);
                }
            });
            this.maintenanceModeWindow = tipWindow2;
            tipWindow2.setConfirmColor(getResources().getColor(R.color.login_new_blue));
            this.maintenanceModeWindow.setCancelColor(getResources().getColor(R.color.gray));
            this.maintenanceModeWindow.show(getWindow().getDecorView());
        }
    }

    @Subscriber
    public void serviceUpdate(ServiceUpdateEvent serviceUpdateEvent) {
        if (isFinishing() || this.mPresenter == 0) {
            return;
        }
        Intent intent = new Intent(T6Utils.BROADCAST_T5_STATE_MSG);
        intent.putExtra(Constants.EXTRA_DEVICE_ID, this.deviceId);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        ((T5HomePresenter) this.mPresenter).initT5DeviceDetail();
    }

    @Override // android.app.Activity
    public void onRestart() {
        P p;
        super.onRestart();
        if ((this.t5Record != null && CommonUtil.getLong(r0.getFirmware()) < 600.0d) || this.refreshSd || (p = this.mPresenter) == 0 || ((T5HomePresenter) p).getLiveService() == null) {
            return;
        }
        if (this.isForeground) {
            this.isForeground = false;
            resetLiveVideoView();
            Message message = new Message();
            message.arg1 = 0;
            message.what = 21;
            this.handler.sendMessageDelayed(message, 500L);
            return;
        }
        if (((T5HomePresenter) this.mPresenter).getLiveService() instanceof RtcService) {
            ((RtcService) ((T5HomePresenter) this.mPresenter).getLiveService()).onRestart(this.t5LiveVideoView.getVideoPlayerView().getPlayerView().getTextureView());
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStop() {
        super.onStop();
        if (BaseApplication.isForeground()) {
            this.isForeground = true;
        }
        if ((this.t5Record == null || CommonUtil.getLong(r0.getFirmware()) >= 600.0d) && !this.clickIntoFullscreen) {
            if (this.refreshSd) {
                this.refreshSd = false;
                return;
            }
            P p = this.mPresenter;
            if (p == 0 || ((T5HomePresenter) p).getLiveService() == null || !(((T5HomePresenter) this.mPresenter).getLiveService() instanceof RtcService)) {
                return;
            }
            ((RtcService) ((T5HomePresenter) this.mPresenter).getLiveService()).stop();
        }
    }

    private void resetLiveVideoView() {
        this.rlParentLive.removeAllViews();
        T5LiveVideoView t5LiveVideoView = new T5LiveVideoView(this);
        this.t5LiveVideoView = t5LiveVideoView;
        t5LiveVideoView.setPlayerType(PetkitVideoPlayerView.PlayerType.CIRCLE);
        this.t5LiveVideoView.setTouchListener();
        this.rlParentLive.addView(this.t5LiveVideoView);
        this.t5LiveVideoView.getVideoPlayerView().showLoadingView();
        this.t5LiveVideoView.setT6LiveVideoViewListener(this);
        this.t5LiveVideoView.setListener(this);
    }

    @Subscriber
    public void refreshSD(RefreshSdEvent refreshSdEvent) {
        if (isFinishing() || refreshSdEvent == null) {
            return;
        }
        this.refreshSd = true;
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$58 */
    public class AnonymousClass58 implements DownloadingWindow.OnClick {
        @Override // com.petkit.android.activities.petkitBleDevice.download.widget.DownloadingWindow.OnClick
        public void onBackgroundClick() {
        }

        public AnonymousClass58() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.download.widget.DownloadingWindow.OnClick
        public void onDismissClick() {
            T5HomeActivity.this.finish();
        }

        @Override // com.petkit.android.activities.petkitBleDevice.download.widget.DownloadingWindow.OnClick
        public boolean isAllowDismiss() {
            return FileUtils.checkAgoraFile() == 2;
        }

        @Override // com.petkit.android.activities.petkitBleDevice.download.widget.DownloadingWindow.OnClick
        public void onCloseClick() {
            T5HomeActivity.this.killMyself();
        }
    }

    public void openDownloadingWindow() {
        if (this.downloadingWindow == null) {
            this.downloadingWindow = new DownloadingWindow(this, getString(R.string.BT_initing) + " 0%...", getString(R.string.D4sh_download_agora_tip), new DownloadingWindow.OnClick() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.58
                @Override // com.petkit.android.activities.petkitBleDevice.download.widget.DownloadingWindow.OnClick
                public void onBackgroundClick() {
                }

                public AnonymousClass58() {
                }

                @Override // com.petkit.android.activities.petkitBleDevice.download.widget.DownloadingWindow.OnClick
                public void onDismissClick() {
                    T5HomeActivity.this.finish();
                }

                @Override // com.petkit.android.activities.petkitBleDevice.download.widget.DownloadingWindow.OnClick
                public boolean isAllowDismiss() {
                    return FileUtils.checkAgoraFile() == 2;
                }

                @Override // com.petkit.android.activities.petkitBleDevice.download.widget.DownloadingWindow.OnClick
                public void onCloseClick() {
                    T5HomeActivity.this.killMyself();
                }
            });
        }
        if (this.downloadingWindow.isShowing()) {
            return;
        }
        this.handler.postDelayed(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.59
            public AnonymousClass59() {
            }

            @Override // java.lang.Runnable
            public void run() {
                T5HomeActivity.this.downloadingWindow.show(T5HomeActivity.this.getWindow().getDecorView());
            }
        }, 600L);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$59 */
    public class AnonymousClass59 implements Runnable {
        public AnonymousClass59() {
        }

        @Override // java.lang.Runnable
        public void run() {
            T5HomeActivity.this.downloadingWindow.show(T5HomeActivity.this.getWindow().getDecorView());
        }
    }

    @Subscriber
    public void downloadState(AgoraDownloadMsg agoraDownloadMsg) {
        DownloadingWindow downloadingWindow;
        if (agoraDownloadMsg.getState() == 4) {
            finish();
            return;
        }
        if (agoraDownloadMsg.getState() == 3) {
            try {
                ZipUtil.unzip(FileUtils.getAppAgoraZipPath(), FileUtils.getAppAgoraDirPath());
                this.handler.postDelayed(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity.60
                    public AnonymousClass60() {
                    }

                    @Override // java.lang.Runnable
                    public void run() {
                        if (FileUtils.checkAgoraFile() != 2 || T5HomeActivity.this.downloadingWindow == null) {
                            return;
                        }
                        T5HomeActivity.this.downloadingWindow.dismiss();
                    }
                }, 2000L);
                return;
            } catch (Exception e) {
                PetkitLog.e(this.TAG, "zip error: " + e.getMessage());
                return;
            }
        }
        if (agoraDownloadMsg.getState() != 2 || (downloadingWindow = this.downloadingWindow) == null) {
            return;
        }
        downloadingWindow.setContent(getString(R.string.BT_initing) + " " + agoraDownloadMsg.getPercent() + "%...");
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity$60 */
    public class AnonymousClass60 implements Runnable {
        public AnonymousClass60() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (FileUtils.checkAgoraFile() != 2 || T5HomeActivity.this.downloadingWindow == null) {
                return;
            }
            T5HomeActivity.this.downloadingWindow.dismiss();
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(@androidx.annotation.NonNull Configuration configuration) {
        super.onConfigurationChanged(configuration);
        setChartData();
        if (this.statisticList != null) {
            RelativeLayout relativeLayout = this.rlPop;
            if (relativeLayout != null) {
                relativeLayout.setVisibility(8);
            }
            updateStatisticList(this.statisticList);
        }
    }

    @Override // com.jess.arms.base.BaseActivity, com.jess.arms.mvp.IView
    public void killMyself() {
        super.killMyself();
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0) {
            int i = this.currentPosition;
            if (i == 0) {
                this.fragmentAll.hideCatFacePop();
            } else if (i == 1) {
                this.fragmentToilet.hideCatFacePop();
            } else if (i == 2) {
                this.fragmentPet.hideCatFacePop();
            }
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    @Subscriber
    public void resetComplete(T6ConsumeEvent t6ConsumeEvent) {
        if (t6ConsumeEvent == null) {
            return;
        }
        setupView(T6Utils.getT6RecordByDeviceId(this.deviceId, 1));
    }
}

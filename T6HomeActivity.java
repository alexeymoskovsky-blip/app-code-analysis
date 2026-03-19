package com.petkit.android.activities.petkitBleDevice.t6;

import android.animation.Animator;
import android.animation.AnimatorInflater;
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
import android.os.Vibrator;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.view.GravityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.airbnb.lottie.LottieAnimationView;
import com.alibaba.fastjson.JSON;
import com.binioter.guideview.Guide;
import com.binioter.guideview.GuideBuilder;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
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
import com.petkit.android.activities.home.utils.GuidePetTipAndBtnAndLineComponent;
import com.petkit.android.activities.home.utils.GuideTipAndBtnAndLineComponent;
import com.petkit.android.activities.home.widget.MyHeader;
import com.petkit.android.activities.home.widget.TipWindow;
import com.petkit.android.activities.mall.mode.PromoteData;
import com.petkit.android.activities.mall.widget.PromoteView;
import com.petkit.android.activities.petkitBleDevice.BleDeviceBindNetWorkActivity;
import com.petkit.android.activities.petkitBleDevice.BleDeviceWifiSettingActivity;
import com.petkit.android.activities.petkitBleDevice.PetColorSettingActivity;
import com.petkit.android.activities.petkitBleDevice.PetWeightActivity;
import com.petkit.android.activities.petkitBleDevice.adapter.BasePetkitHighlightAdapter;
import com.petkit.android.activities.petkitBleDevice.d4sh.mode.BannerStateCache;
import com.petkit.android.activities.petkitBleDevice.d4sh.mode.D4shBannerData;
import com.petkit.android.activities.petkitBleDevice.d4sh.mode.D4shVlogDeleteMsg;
import com.petkit.android.activities.petkitBleDevice.d4sh.model.VlogStateChanged;
import com.petkit.android.activities.petkitBleDevice.d4sh.utils.D4shUtils;
import com.petkit.android.activities.petkitBleDevice.d4sh.utils.VlogUtils;
import com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shPlayerSoundWaveView;
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
import com.petkit.android.activities.petkitBleDevice.t4.mode.PetOutTip;
import com.petkit.android.activities.petkitBleDevice.t4.mode.RelatedProductsInfor;
import com.petkit.android.activities.petkitBleDevice.t4.widget.RoundImageview;
import com.petkit.android.activities.petkitBleDevice.t6.T6EventListFragment;
import com.petkit.android.activities.petkitBleDevice.t6.adapter.MyViewPagerAdapter;
import com.petkit.android.activities.petkitBleDevice.t6.adapter.T6HomeBannerPageAdapter;
import com.petkit.android.activities.petkitBleDevice.t6.adapter.T6LiveViewPagerAdapter;
import com.petkit.android.activities.petkitBleDevice.t6.adapter.T6VlogRecordAdapter;
import com.petkit.android.activities.petkitBleDevice.t6.component.DaggerT6HomeComponent;
import com.petkit.android.activities.petkitBleDevice.t6.contract.T6HomeContract;
import com.petkit.android.activities.petkitBleDevice.t6.mode.DeleteEvent;
import com.petkit.android.activities.petkitBleDevice.t6.mode.DeviceError;
import com.petkit.android.activities.petkitBleDevice.t6.mode.T6ConsumeEvent;
import com.petkit.android.activities.petkitBleDevice.t6.mode.T6ContentInfo;
import com.petkit.android.activities.petkitBleDevice.t6.mode.T6EventInfo;
import com.petkit.android.activities.petkitBleDevice.t6.mode.T6EventStaticInfo;
import com.petkit.android.activities.petkitBleDevice.t6.mode.T6FinishDevicePageMsg;
import com.petkit.android.activities.petkitBleDevice.t6.mode.T6NewUpdateSettingEvent;
import com.petkit.android.activities.petkitBleDevice.t6.mode.T6Record;
import com.petkit.android.activities.petkitBleDevice.t6.mode.T6StaticInfo;
import com.petkit.android.activities.petkitBleDevice.t6.mode.T6StatisticResInfo;
import com.petkit.android.activities.petkitBleDevice.t6.mode.UpdatePetCleanEvent;
import com.petkit.android.activities.petkitBleDevice.t6.mode.UpdatePetEvent;
import com.petkit.android.activities.petkitBleDevice.t6.presenter.T6HomePresenter;
import com.petkit.android.activities.petkitBleDevice.t6.utils.SwitchCameraCall;
import com.petkit.android.activities.petkitBleDevice.t6.utils.T6AnimUtil;
import com.petkit.android.activities.petkitBleDevice.t6.utils.T6Utils;
import com.petkit.android.activities.petkitBleDevice.t6.widget.CircleLayout;
import com.petkit.android.activities.petkitBleDevice.t6.widget.MyChartViewPager;
import com.petkit.android.activities.petkitBleDevice.t6.widget.MySmartRefreshView;
import com.petkit.android.activities.petkitBleDevice.t6.widget.PetkitVideoPlayerView;
import com.petkit.android.activities.petkitBleDevice.t6.widget.SandDumpSuccessDialog;
import com.petkit.android.activities.petkitBleDevice.t6.widget.T6BottomView;
import com.petkit.android.activities.petkitBleDevice.t6.widget.T6KittenProtectionDialog;
import com.petkit.android.activities.petkitBleDevice.t6.widget.T6LiveVideoView;
import com.petkit.android.activities.petkitBleDevice.t6.widget.VideoPlayerViewListener;
import com.petkit.android.activities.petkitBleDevice.t7.utils.T7ConstUtils;
import com.petkit.android.activities.petkitBleDevice.utils.BleDeviceUtils;
import com.petkit.android.activities.petkitBleDevice.utils.ColorUtils;
import com.petkit.android.activities.petkitBleDevice.utils.DeviceVolumeUtils;
import com.petkit.android.activities.petkitBleDevice.utils.FamilyUtils;
import com.petkit.android.activities.petkitBleDevice.utils.TextUtil;
import com.petkit.android.activities.petkitBleDevice.utils.TimeUtils;
import com.petkit.android.activities.petkitBleDevice.vlog.VlogMakeService;
import com.petkit.android.activities.petkitBleDevice.vlog.mode.VlogM3U8Mode;
import com.petkit.android.activities.petkitBleDevice.w5.guide.ConfirmListener;
import com.petkit.android.activities.petkitBleDevice.w5.guide.GuideParam;
import com.petkit.android.activities.petkitBleDevice.widget.BleDeviceHomeOfflineWarnWindow;
import com.petkit.android.activities.petkitBleDevice.widget.BleDeviceHomeTroubleWarnWindow;
import com.petkit.android.activities.petkitBleDevice.widget.CloudServiceFreeTrialDialog;
import com.petkit.android.activities.petkitBleDevice.widget.DeviceErrorWarnWindow;
import com.petkit.android.activities.petkitBleDevice.widget.KittenProtectionWindow;
import com.petkit.android.activities.petkitBleDevice.widget.OtaPromptWindow;
import com.petkit.android.activities.petkitBleDevice.widget.SimpleTipWindow;
import com.petkit.android.activities.petkitBleDevice.widget.datepicker.BasePetkitDeviceDatePickerView;
import com.petkit.android.activities.petkitBleDevice.widget.datepicker.CountBean;
import com.petkit.android.activities.petkitBleDevice.widget.datepicker.DatePickerWindow;
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
import com.petkit.android.media.video.p2p.agora.AgoraLiveService;
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
import com.wode369.videocroplibrary.utils.ToastUtil;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

/* JADX INFO: loaded from: classes5.dex */
@SuppressLint({"NonConstantResourceId"})
public class T6HomeActivity extends BaseActivity<T6HomePresenter> implements View.OnClickListener, T6HomeContract.View, BleDeviceHomeOfflineWarnWindow.OfflineClickListener, T6LiveVideoView.T6LiveVideoViewStateListener, VideoPlayerViewListener {
    AiInfo aiNewInfo;
    private boolean aiNotification;
    private T6EventListFragment allFragment;
    AppBarLayout appBar;
    private int appBarOffset;
    private T6HomeBannerPageAdapter bannerPageAdapter;
    private NormalCenterTipWindow bindServiceWindow;
    private BleDeviceHomeOfflineWarnWindow bleDeviceHomeOfflineWarnWindow;
    private DeviceErrorWarnWindow bleDeviceHomeTroubleWarnWindow;
    TextView btnContinue;
    TextView btnIgnoreOne;
    TextView btnKnowMoreOne;
    TextView btnPause;
    TextView btnStop;
    private long calendarTime;
    private RelativeLayout chartView;
    CircleLayout clCamera;
    CircleLayout clDevice;
    CircleLayout clEyeAnimBackground;
    CircleLayout clLoading;
    int clickActionType;
    private boolean clickIntoFullscreen;
    CloudServiceFreeTrialDialog cloudServiceFreeTrialDialog;
    private int current;
    private String currentRotate;
    D4shBannerData d4shBannerData;
    private DatePickerWindow datePickerWindow;
    TextView deviceErrorOneText;
    TextView deviceErrorTwoText;
    private long deviceId;
    private String deviceName;
    TextView deviceStateText;
    private int deviceType;
    TextView deviceWorkStateText;
    private String direction;
    private SimpleTipWindow disableCleanUpWindow;
    private Disposable disposable;
    private DownloadingWindow downloadingWindow;
    private SandDumpSuccessDialog dumpSuccessDialog;
    private TipWindow enableAutoCleanWindow;
    private String eventId;
    private FamilyInfor familyInfor;
    Guide firstGuide;
    FrameLayout flHomeBanner;
    private MyHandler handler;
    Guide homeModeGUide;
    private NewIKnowWindow homeModeWindow;
    private NewIKnowWindow homeModeWindowWithListner;
    ImageButton ibBack;
    ImageButton ibSetting;
    CirclePageIndicator indicator;
    boolean isBackHomeModeClickable;
    private boolean isForeground;
    private boolean isHidePromote;
    boolean isNeedShowPetError;
    private boolean isNotFirstLoad;
    private boolean isShowHealthRemindWindow;
    private boolean isShowPhErrorDialog;
    private boolean isShowToiletUnusedDialog;
    SmallCircleDotView ivAll;
    SmallCircleDotView ivAll2;
    MyHeader ivAnim;
    ImageView ivArrow;
    ImageView ivBackHomeMode;
    RoundImageview ivBg;
    ImageView ivCameraAnim;
    ImageView ivCameraSwitchLoading;
    ImageView ivChartPlay;
    ImageView ivChartShow;
    ImageView ivControlLeft;
    ImageView ivControlRight;
    ImageView ivDeviceClose;
    ImageView ivDeviceErrorOneIcon;
    ImageView ivDeviceErrorOneRightArrow;
    ImageView ivDeviceErrorTwoIcon;
    ImageView ivDeviceErrorTwoRightArrow;
    ImageView ivDeviceModeIconOne;
    ImageView ivDeviceModeIconTwo;
    ImageView ivDeviceOpen;
    ImageView ivDeviceStateIcon;
    ImageView ivEventAnim;
    ImageView ivEyeAnim;
    ImageView ivFox;
    ImageView ivLastEvent;
    ImageView ivLeftArrow;
    ImageView ivMuteIcon;
    ImageView ivNextEvent;
    ImageView ivPagerClose;
    NewRoundImageview ivPopImage;
    ImageView ivTodayAvgDurationPrompt;
    ImageView ivTodayEvent;
    ImageView ivTodayEvent2;
    ImageView ivTodayTimesPrompt;
    ImageView ivTrialRemainingTimeClose;
    ImageView ivWarnRight;
    private boolean jump;
    private KittenProtectionWindow kittenProtectionWindow;
    private RelativeLayout live;
    Guide liveGuide;
    CirclePageIndicator liveIndicator;
    LinearLayout llControl;
    LinearLayout llDay;
    LinearLayout llDeviceMode;
    LinearLayout llHighlights;
    LinearLayout llKnowMoreOne;
    LinearLayout llKnowMoreTwo;
    LinearLayout llPets;
    LinearLayout llTime;
    LinearLayout llTrialRemainingTime;
    private boolean longClick;
    LottieAnimationView ltLoading;
    private Animator maintenanceAnimator;
    private int monthOffset;
    private int newClearEventNum;
    private int newPetEventNum;
    private int newToiletErrorEventNum;
    private int newToiletEventNum;
    private long optionTime;
    private Disposable otaDisposable;
    private OtaPromptWindow otaPromptWindow;
    private NewIKnowWindow packageStateErrorWindow;
    private Disposable paoPaoTimer;
    NormalCenterTipWindow petErrorNoRemindWindow;
    private PetFilterWindow petFilterWindow;
    private T6EventListFragment petFragment;
    private SpannableStringColorsWindow petOutWindow;
    private NormalListChoiceCenterWindow phErrorWindow;
    private int popCount;
    PromoteView promoteView;
    private SpannableStringColorsPicPromptWindow promptWindow;
    PurchaseEligibilityInfo.PurchaseEligibility purchaseEligibility;
    private boolean refreshSd;
    RelatedProductsInfor relatedProductsInfor;
    RelativeLayout rlCameraTip;
    RelativeLayout rlChart;
    RelativeLayout rlDeviceErrorOne;
    RelativeLayout rlDeviceErrorTwo;
    LinearLayout rlDeviceState;
    RelativeLayout rlHighlightsTip;
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
    RelativeLayout rlWorkState;
    Runnable runnable;
    RecyclerView rvHighlights;
    View scPoint;
    View scPoint2;
    SmallCircleDotView scdView;
    NestedScrollView scrollView;
    SmallCircleDotView sdPet;
    Guide secondGuide;
    private boolean showCare;
    private long showErrorTime;
    private boolean showTurnCamera;
    private List<T6StaticInfo> sortEventList;
    D4shPlayerSoundWaveView soundWaveView;
    MySmartRefreshView srl;
    private boolean startCollAnim;
    private boolean startExpandAnim;
    private List<T6StatisticResInfo> statisticList;
    private T6AnimUtil t6AnimUtil;
    T6BottomView t6BottomView;
    private T6KittenProtectionDialog t6KittenProtectionDialog;
    private T6LiveVideoView t6LiveVideoView;
    private T6LiveViewPagerAdapter t6LiveViewPagerAdapter;
    private T6Record t6Record;
    private T6VlogRecordAdapter t6VlogRecordAdapter;
    Guide thirdGuide;
    private ThreeChoicesWindow threeChoicesWindow;
    private NewIKnowWindow timezoneWindow;
    private String title;
    private int toiletErrorEventNum;
    private T6EventListFragment toiletErrorFragment;
    private T6EventListFragment toiletFragment;
    AutoToolbar toolbar;
    TextView toolbarTitle;
    TextView toolbarTitleStatusOne;
    TextView toolbarTitleStatusTwo;
    TextView tvDeviceTag;
    TextView tvHighlights;
    TextView tvOtherTab;
    TextView tvOtherTab2;
    TextView tvPetName;
    TextView tvPetName2;
    TextView tvPetWeight;
    TextView tvPopContent;
    TextView tvPopPetName;
    TextView tvPopTime;
    TextView tvPurchaseEligibility;
    TextView tvSetting;
    TextView tvTabAll;
    TextView tvTabAll2;
    TextView tvTitleStatus;
    TextView tvTodayAvgDuration;
    TextView tvTodayAvgDurationPrompt;
    TextView tvTodayEvent;
    TextView tvTodayTimes;
    TextView tvTodayTimesPrompt;
    TextView tvTodayToiletAmount;
    TextView tvTodayToiletStr;
    TextView tvToiletTab;
    TextView tvToiletTab2;
    TextView tvToiletTabError;
    TextView tvToiletTabError2;
    TextView tvTopPaoPao;
    TextView tvTrialRemainingTime;
    TextView tvTurnOn;
    TextView tvY1;
    TextView tvY2;
    TextView tvY3;
    TextView vCameraBottom;
    View vEnd;
    View vStart;
    private Vibrator vibrator;
    TextView videoTag;
    RelativeLayout videoTagLayout;
    private Disposable viewPagerDisposable;
    MyChartViewPager vp;
    ViewPager vpHomeBanner;
    InterceptViewPager vpList;
    ViewPager vpLive;
    private BleDeviceHomeTroubleWarnWindow warnWindow;
    private WidgetDataInfo widgetDataInfo;
    private NewWifiWeakWindow wifiWeakWindow;
    private SpannableStringColorsPicPromptWindow window;
    private int currentPosition = 0;
    private List<T6StatisticResInfo> resStatisticList = new ArrayList();
    private List<Pet> petList = new ArrayList();
    private List<String> selectedPetIds = new ArrayList();
    private boolean isNeedTurnOnDeviceAni = false;
    private final int Handler_Ret_Code2 = 9;
    private final int Handler_Pao_Pao = 10;
    private final int Handler_Ret_Code3 = 11;
    private final int Handler_Long_Click = 12;
    private final int Handler_Action_Up = 13;
    private final int Handler_Action_Down = 14;
    private final int Handler_Ret_Code4 = 15;
    private final int Handler_Ret_Code5 = 16;
    private final int Handler_Ret_Code_Null = 17;
    private final int Handler_Ret_Code6 = 18;
    private final int Handler_Bar_Scroll = 19;
    private final int Handler_Bar_Stop = 20;
    private final int Handler_Rtm_Login = 21;
    private final int Handler_Rtm_Token_Expired = 22;
    private final int Handler_Ret_Code7 = 23;
    private final int RTM_LOGIN_TIME = 5;
    private AnimationDrawable animationDrawable = null;
    private NormalCenterTipWindow doorWindow = null;
    private NormalCenterTipWindow smoothWindow = null;
    private NormalCenterTipWindow recoverWindow = null;
    private boolean isActivityInBackgrouond = false;
    private boolean firstRemoteVideoFrame = false;
    private boolean isAnimationRunning = false;
    private boolean isFirstInto = true;
    private boolean isFirst = true;
    private boolean isShowRecommendUpgradeWindow = false;
    boolean isCN = "zh_CN".equals(UserInforUtils.getLanguageLocaleString(CommonUtils.getAppContext()));
    private List<D4shBannerData.BannerData> dataList = new ArrayList();
    private boolean serviceMaybeChanged = false;

    private void setListEmptyText() {
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.VideoPlayerViewListener
    public void continueVideo(int i) {
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

    @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.VideoPlayerViewListener
    public void speed(String str) {
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T6HomeContract.View
    public void stopLoad() {
    }

    @Override // com.jess.arms.base.delegate.IActivity
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerT6HomeComponent.builder().appComponent(appComponent).view(this).build().inject(this);
    }

    @Override // com.jess.arms.base.delegate.IActivity
    public int initView(Bundle bundle) {
        return R.layout.activity_t6_home;
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(@androidx.annotation.NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putLong(Constants.EXTRA_DEVICE_ID, this.deviceId);
        bundle.putInt(Constants.EXTRA_DEVICE_TYPE, this.deviceType);
    }

    private void initViews() {
        this.rlRoot = (RelativeLayout) findViewById(R.id.rl_root);
        this.ibBack = (ImageButton) findViewById(R.id.ib_back);
        this.ibSetting = (ImageButton) findViewById(R.id.ib_setting);
        this.toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        this.toolbar = (AutoToolbar) findViewById(R.id.toolbar);
        this.tvTitleStatus = (TextView) findViewById(R.id.tv_title_status);
        this.rlParentLive = (RelativeLayout) findViewById(R.id.rl_parent_live);
        this.tvHighlights = (TextView) findViewById(R.id.tv_highlights);
        this.rvHighlights = (RecyclerView) findViewById(R.id.rv_highlights);
        this.llHighlights = (LinearLayout) findViewById(R.id.ll_highlights);
        this.ivAnim = (MyHeader) findViewById(R.id.iv_anim);
        this.rlPurchaseEligibility = (RelativeLayout) findViewById(R.id.rl_purchase_eligibility);
        this.tvPurchaseEligibility = (TextView) findViewById(R.id.tv_purchase_eligibility);
        this.srl = (MySmartRefreshView) findViewById(R.id.srl);
        this.rlList = (HorizontalScrollView) findViewById(R.id.rl_list);
        this.rlTopTab = (HorizontalScrollView) findViewById(R.id.rl_top_tab);
        this.tvTabAll = (TextView) findViewById(R.id.tv_tab_all);
        this.tvToiletTab = (TextView) findViewById(R.id.tv_tab_toilet);
        this.tvOtherTab = (TextView) findViewById(R.id.tv_tab_other);
        this.tvToiletTabError = (TextView) findViewById(R.id.tv_tab_toilet_error);
        this.tvTabAll2 = (TextView) findViewById(R.id.tv_tab_all2);
        this.tvToiletTab2 = (TextView) findViewById(R.id.tv_tab_toilet2);
        this.tvOtherTab2 = (TextView) findViewById(R.id.tv_tab_other2);
        this.tvToiletTabError2 = (TextView) findViewById(R.id.tv_tab_toilet_error2);
        this.t6BottomView = (T6BottomView) findViewById(R.id.bottom_view);
        this.soundWaveView = (D4shPlayerSoundWaveView) findViewById(R.id.sound_wave_view);
        this.rlCameraTip = (RelativeLayout) findViewById(R.id.rl_camera_tip);
        this.rlHighlightsTip = (RelativeLayout) findViewById(R.id.rl_highlights_tip);
        this.tvSetting = (TextView) findViewById(R.id.tv_setting);
        this.ivDeviceStateIcon = (ImageView) findViewById(R.id.iv_device_state_icon);
        this.deviceStateText = (TextView) findViewById(R.id.device_state_text);
        this.rlDeviceState = (LinearLayout) findViewById(R.id.rl_device_state);
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
        this.ivAll = (SmallCircleDotView) findViewById(R.id.iv_all);
        this.ivAll2 = (SmallCircleDotView) findViewById(R.id.iv_all2);
        this.tvTopPaoPao = (TextView) findViewById(R.id.tv_top_pao_pao);
        this.ivDeviceErrorOneIcon = (ImageView) findViewById(R.id.iv_device_error_one_icon);
        this.deviceErrorOneText = (TextView) findViewById(R.id.device_error_one_text);
        this.ivDeviceErrorOneRightArrow = (ImageView) findViewById(R.id.iv_device_error_one_right_arrow);
        this.rlDeviceErrorOne = (RelativeLayout) findViewById(R.id.rl_device_error_one);
        this.ivDeviceErrorTwoIcon = (ImageView) findViewById(R.id.iv_device_error_two_icon);
        this.deviceErrorTwoText = (TextView) findViewById(R.id.device_error_two_text);
        this.ivDeviceErrorTwoRightArrow = (ImageView) findViewById(R.id.iv_device_error_two_right_arrow);
        this.rlDeviceErrorTwo = (RelativeLayout) findViewById(R.id.rl_device_error_two);
        this.ivBg = (RoundImageview) findViewById(R.id.iv_bg);
        this.vpHomeBanner = (ViewPager) findViewById(R.id.vp_home_banner);
        this.ivPagerClose = (ImageView) findViewById(R.id.iv_pager_close);
        this.flHomeBanner = (FrameLayout) findViewById(R.id.fl_home_banner);
        this.indicator = (CirclePageIndicator) findViewById(R.id.indicator);
        this.tvTrialRemainingTime = (TextView) findViewById(R.id.tv_trial_remaining_time);
        this.ivTrialRemainingTimeClose = (ImageView) findViewById(R.id.iv_trial_remaining_time_close);
        this.llTrialRemainingTime = (LinearLayout) findViewById(R.id.ll_trial_remaining_time);
        this.tvTurnOn = (TextView) findViewById(R.id.tv_turn_on);
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
        this.llDeviceMode = (LinearLayout) findViewById(R.id.ll_device_mode);
        this.ivDeviceModeIconOne = (ImageView) findViewById(R.id.iv_device_mode_icon_one);
        this.ivDeviceModeIconTwo = (ImageView) findViewById(R.id.iv_device_mode_icon_two);
        this.scrollView = (NestedScrollView) findViewById(R.id.scrollview);
        this.tvPetWeight = (TextView) findViewById(R.id.tv_pet_weight);
        this.btnKnowMoreOne = (TextView) findViewById(R.id.btn_know_more_one);
        this.btnIgnoreOne = (TextView) findViewById(R.id.btn_ignore_one);
        this.deviceWorkStateText.setOnClickListener(this);
        this.tvPetWeight.setOnClickListener(this);
        this.ibSetting.setOnClickListener(this);
        this.tvToiletTabError.setOnClickListener(this);
        this.tvToiletTabError2.setOnClickListener(this);
        this.ibBack.setOnClickListener(this);
        this.tvTitleStatus.setOnClickListener(this);
        this.ivTodayEvent.setOnClickListener(this);
        this.rlPopContent.setOnClickListener(this);
        this.ivLastEvent.setOnClickListener(this);
        this.ivNextEvent.setOnClickListener(this);
        this.tvOtherTab.setOnClickListener(this);
        this.tvOtherTab2.setOnClickListener(this);
        this.llPets.setOnClickListener(this);
        this.ivChartShow.setOnClickListener(this);
        this.tvToiletTab.setOnClickListener(this);
        this.tvToiletTab2.setOnClickListener(this);
        this.rlDeviceState.setOnClickListener(this);
        this.rlWorkState.setOnClickListener(this);
        this.btnPause.setOnClickListener(this);
        this.btnContinue.setOnClickListener(this);
        this.btnStop.setOnClickListener(this);
        this.rlDeviceErrorOne.setOnClickListener(this);
        this.tvTopPaoPao.setOnClickListener(this);
        this.ivTodayEvent2.setOnClickListener(this);
        this.rlDeviceErrorTwo.setOnClickListener(this);
        this.tvTabAll.setOnClickListener(this);
        this.tvTabAll2.setOnClickListener(this);
        this.rlPurchaseEligibility.setOnClickListener(this);
        findViewById(R.id.tv_highlights_open).setOnClickListener(this);
        findViewById(R.id.iv_tip_close).setOnClickListener(this);
        findViewById(R.id.tv_more_vlog).setOnClickListener(this);
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
        FamilyInfor familyInforThroughDevice = FamilyUtils.getInstance().getFamilyInforThroughDevice(this, this.deviceId, 27);
        this.familyInfor = familyInforThroughDevice;
        if (familyInforThroughDevice == null) {
            this.familyInfor = FamilyUtils.getInstance().getCurrentFamilyInfo(this);
        }
        this.handler = new MyHandler(getMainLooper(), this);
        this.soundWaveView.setColor(R.color.w5_main_blue);
        if (FileUtils.checkAgoraFile() != 2 && FileUtils.checkAgoraFile() != 1) {
            openDownloadingWindow();
            EventBus.getDefault().post(new AgoraStartDownloadMsg());
        }
        initLiveView();
        this.selectedPetIds.add(ColorUtils.ALL_PET);
        initTodayTime();
        initAnimation();
        initPlayerView();
        initControlView();
        initFragment();
        initBottomView();
        this.deviceType = 27;
        ((T6HomePresenter) this.mPresenter).initParams(this.deviceId, 27);
        this.rlRoot.setOnTouchListener(new View.OnTouchListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$$ExternalSyntheticLambda11
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return this.f$0.lambda$initData$0(view, motionEvent);
            }
        });
        setChartData();
        initSmartView();
        this.vibrator = (Vibrator) CommonUtils.getAppContext().getSystemService("vibrator");
        ((T6HomePresenter) this.mPresenter).getListNum(this.calendarTime);
        this.toolbarTitle.setTypeface(null, 1);
        this.appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$$ExternalSyntheticLambda12
            @Override // com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener, com.google.android.material.appbar.AppBarLayout.BaseOnOffsetChangedListener
            public final void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                this.f$0.lambda$initData$1(appBarLayout, i);
            }
        });
        boolean booleanSF = DataHelper.getBooleanSF(this, Constants.T6_CAMERA_BACK_TIP + UserInforUtils.getCurrentUserId(this) + this.deviceId);
        RelativeLayout relativeLayout = this.rlCameraTip;
        if (relativeLayout != null) {
            if (booleanSF) {
                relativeLayout.setVisibility(8);
            } else {
                relativeLayout.setVisibility(0);
            }
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
        int i2 = this.appBarOffset;
        if (i2 - i > 0) {
            MyHandler myHandler2 = this.handler;
            if (myHandler2 != null) {
                myHandler2.sendEmptyMessage(13);
            }
        } else if (i2 - i < 0 && (myHandler = this.handler) != null) {
            myHandler.sendEmptyMessage(14);
        }
        if (this.appBar != null && Math.abs(i) > this.appBar.getTotalScrollRange() - ArmsUtils.dip2px(this, 16.0f)) {
            HorizontalScrollView horizontalScrollView = this.rlTopTab;
            if (horizontalScrollView != null && horizontalScrollView.getVisibility() == 8) {
                this.toolbar.setBackgroundColor(getResources().getColor(R.color.white));
                this.rlTopTab.setVisibility(0);
                this.ivTodayEvent2.setVisibility(0);
                this.toolbarTitle.setMaxWidth(ArmsUtils.dip2px(this, 160.0f));
                this.showCare = this.tvDeviceTag.getVisibility() == 0;
                this.toolbarTitle.setText(this.tvTodayToiletStr.getText().toString());
                this.tvDeviceTag.setVisibility(8);
            }
        } else {
            HorizontalScrollView horizontalScrollView2 = this.rlTopTab;
            if (horizontalScrollView2 != null && horizontalScrollView2.getVisibility() == 0) {
                if (this.appBar != null && Math.abs(i) < this.appBar.getTotalScrollRange() - ArmsUtils.dip2px(this, 30.0f) && this.appBarOffset == i) {
                    this.appBar.setExpanded(false, false);
                }
                this.toolbar.setBackgroundColor(getResources().getColor(R.color.transparent));
                this.rlTopTab.setVisibility(8);
                this.ivTodayEvent2.setVisibility(8);
                this.toolbarTitle.setMaxWidth(ArmsUtils.dip2px(this, 190.0f));
                this.toolbarTitle.setText(this.deviceName);
                this.tvDeviceTag.setVisibility(this.showCare ? 0 : 8);
            }
        }
        if (this.appBar == null) {
            return;
        }
        if (i == 0 || Math.abs(i) == this.appBar.getMeasuredHeight()) {
            MyHandler myHandler3 = this.handler;
            if (myHandler3 != null) {
                myHandler3.sendEmptyMessage(20);
            }
        } else {
            startAppBarScroll();
        }
        this.appBarOffset = i;
    }

    private void initLiveView() {
        RelativeLayout relativeLayout = (RelativeLayout) View.inflate(this, R.layout.layout_t6_live_center_view, null);
        this.live = relativeLayout;
        this.rlLivePanel = (RelativeLayout) relativeLayout.findViewById(R.id.rl_live_panel);
        this.ivBg = (RoundImageview) this.live.findViewById(R.id.iv_bg);
        this.videoTagLayout = (RelativeLayout) this.live.findViewById(R.id.video_tag_layout);
        this.videoTag = (TextView) this.live.findViewById(R.id.video_tag);
        this.scdView = (SmallCircleDotView) this.live.findViewById(R.id.scd_view);
        this.ivFox = (ImageView) this.live.findViewById(R.id.iv_fox);
        this.rlParentLive = (RelativeLayout) this.live.findViewById(R.id.rl_parent_live);
        this.clCamera = (CircleLayout) this.live.findViewById(R.id.cl_camera);
        this.vCameraBottom = (TextView) this.live.findViewById(R.id.tv_camera_bottom);
        this.ivCameraAnim = (ImageView) this.live.findViewById(R.id.iv_camera_anim);
        this.clDevice = (CircleLayout) this.live.findViewById(R.id.cl_device);
        this.ivDeviceClose = (ImageView) this.live.findViewById(R.id.iv_device_close);
        this.ivDeviceOpen = (ImageView) this.live.findViewById(R.id.iv_device_open);
        this.ivEyeAnim = (ImageView) this.live.findViewById(R.id.iv_eye_anim);
        this.clLoading = (CircleLayout) this.live.findViewById(R.id.cl_loading);
        this.clEyeAnimBackground = (CircleLayout) this.live.findViewById(R.id.cl_eye_anim_background);
        this.ltLoading = (LottieAnimationView) this.live.findViewById(R.id.lt_device_loading);
        this.ivMuteIcon = (ImageView) this.live.findViewById(R.id.iv_mute_icon);
        this.tvTodayTimes = (TextView) this.live.findViewById(R.id.tv_today_times);
        this.tvTodayTimesPrompt = (TextView) this.live.findViewById(R.id.tv_today_times_prompt);
        this.ivTodayTimesPrompt = (ImageView) this.live.findViewById(R.id.iv_today_times_prompt);
        this.tvTodayAvgDuration = (TextView) this.live.findViewById(R.id.tv_today_avg_duration);
        this.tvTodayAvgDurationPrompt = (TextView) this.live.findViewById(R.id.tv_today_avg_duration_prompt);
        this.ivTodayAvgDurationPrompt = (ImageView) this.live.findViewById(R.id.iv_today_avg_duration_prompt);
        this.ivBackHomeMode = (ImageView) this.live.findViewById(R.id.iv_back_home_mode);
        this.ivCameraSwitchLoading = (ImageView) this.live.findViewById(R.id.iv_camera_switch_loading);
        this.tvTurnOn = (TextView) this.live.findViewById(R.id.tv_turn_on);
        this.llControl = (LinearLayout) this.live.findViewById(R.id.ll_control);
        this.ivControlLeft = (ImageView) this.live.findViewById(R.id.iv_control_left);
        this.ivControlRight = (ImageView) this.live.findViewById(R.id.iv_control_right);
        this.clCamera.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$$ExternalSyntheticLambda55
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initLiveView$2(view);
            }
        });
        T6LiveViewPagerAdapter t6LiveViewPagerAdapter = new T6LiveViewPagerAdapter(this, this.live, this.handler);
        this.t6LiveViewPagerAdapter = t6LiveViewPagerAdapter;
        t6LiveViewPagerAdapter.setOnClickListener(new T6LiveViewPagerAdapter.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.1
            public AnonymousClass1() {
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.adapter.T6LiveViewPagerAdapter.OnClickListener
            public void onBoxClick() {
                T6HomeActivity t6HomeActivity = T6HomeActivity.this;
                t6HomeActivity.launchActivity(T6ConsumablesActivity.newIntent(t6HomeActivity, t6HomeActivity.deviceId, 27, 2, T6HomeActivity.this.relatedProductsInfor));
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.adapter.T6LiveViewPagerAdapter.OnClickListener
            public void onCatLitterClick() {
                T6HomeActivity t6HomeActivity = T6HomeActivity.this;
                t6HomeActivity.launchActivity(T6ConsumablesActivity.newIntent(t6HomeActivity, t6HomeActivity.deviceId, 27, 1, T6HomeActivity.this.relatedProductsInfor));
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.adapter.T6LiveViewPagerAdapter.OnClickListener
            public void onPackageClick() {
                T6HomeActivity t6HomeActivity = T6HomeActivity.this;
                t6HomeActivity.launchActivity(T6ConsumablesActivity.newIntent(t6HomeActivity, t6HomeActivity.deviceId, 27, 3, T6HomeActivity.this.relatedProductsInfor));
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.adapter.T6LiveViewPagerAdapter.OnClickListener
            public void onBlockClick() {
                T6HomeActivity t6HomeActivity = T6HomeActivity.this;
                t6HomeActivity.launchActivity(T6ConsumablesActivity.newIntent(t6HomeActivity, t6HomeActivity.deviceId, 27, 4, T6HomeActivity.this.relatedProductsInfor));
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.adapter.T6LiveViewPagerAdapter.OnClickListener
            public void updateDeviceSettings(String str, boolean z) {
                ((T6HomePresenter) ((BaseActivity) T6HomeActivity.this).mPresenter).updateDeviceSettings(str, z);
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.adapter.T6LiveViewPagerAdapter.OnClickListener
            public void jumpToSmartSetting() {
                T6HomeActivity t6HomeActivity = T6HomeActivity.this;
                t6HomeActivity.launchActivity(T6SmartSettingActivity.newIntent(t6HomeActivity, t6HomeActivity.deviceId, T6HomeActivity.this.deviceType));
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.adapter.T6LiveViewPagerAdapter.OnClickListener
            public void jumpToRegularClean() {
                if (T6HomeActivity.this.t6Record.getSettings().getKitten() == 1) {
                    T6HomeActivity.this.showDisableCleanUpWindow();
                } else {
                    T6HomeActivity t6HomeActivity = T6HomeActivity.this;
                    t6HomeActivity.launchActivity(T6RegularCleanSettingActivity.newIntent(t6HomeActivity, t6HomeActivity.deviceId, T6HomeActivity.this.deviceType, new ArrayList()));
                }
            }
        });
        this.vpLive.setAdapter(this.t6LiveViewPagerAdapter);
        this.vpLive.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.2
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i, float f, int i2) {
            }

            public AnonymousClass2() {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int i) {
                if (i == 1) {
                    T6HomeActivity.this.t6LiveViewPagerAdapter.setCurrentPosition(i);
                    T6HomeActivity.this.t6LiveViewPagerAdapter.refreshData();
                }
                CommonUtils.addSysIntMap(Constants.T6_HOME_PAGE_SELECT + T6HomeActivity.this.deviceId, i);
            }
        });
        this.liveIndicator.setViewPager(this.vpLive, 0, 2);
        this.liveIndicator.setPageColor(CommonUtils.getColorById(R.color.color_D2C5BC));
        this.liveIndicator.setFillColor(CommonUtils.getColorById(R.color.light_black));
        this.liveIndicator.setSnap(true);
        this.liveIndicator.setIndicatorStyle(3);
        this.liveIndicator.setRadius(ArmsUtils.dip2px(CommonUtils.getAppContext(), 9.0f));
        this.liveIndicator.setRecRadius(9.0f, true);
        this.tvTurnOn.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$$ExternalSyntheticLambda56
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initLiveView$3(view);
            }
        });
        this.ivBackHomeMode.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$$ExternalSyntheticLambda57
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initLiveView$4(view);
            }
        });
        this.ivMuteIcon.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$$ExternalSyntheticLambda58
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initLiveView$5(view);
            }
        });
        this.tvTodayTimesPrompt.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$$ExternalSyntheticLambda59
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initLiveView$6(view);
            }
        });
        this.tvTodayTimes.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$$ExternalSyntheticLambda60
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initLiveView$7(view);
            }
        });
        this.tvTodayAvgDuration.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$$ExternalSyntheticLambda61
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initLiveView$8(view);
            }
        });
        this.tvTodayAvgDurationPrompt.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$$ExternalSyntheticLambda62
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initLiveView$9(view);
            }
        });
    }

    public /* synthetic */ void lambda$initLiveView$2(View view) {
        onVideoClick();
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$1 */
    public class AnonymousClass1 implements T6LiveViewPagerAdapter.OnClickListener {
        public AnonymousClass1() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t6.adapter.T6LiveViewPagerAdapter.OnClickListener
        public void onBoxClick() {
            T6HomeActivity t6HomeActivity = T6HomeActivity.this;
            t6HomeActivity.launchActivity(T6ConsumablesActivity.newIntent(t6HomeActivity, t6HomeActivity.deviceId, 27, 2, T6HomeActivity.this.relatedProductsInfor));
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t6.adapter.T6LiveViewPagerAdapter.OnClickListener
        public void onCatLitterClick() {
            T6HomeActivity t6HomeActivity = T6HomeActivity.this;
            t6HomeActivity.launchActivity(T6ConsumablesActivity.newIntent(t6HomeActivity, t6HomeActivity.deviceId, 27, 1, T6HomeActivity.this.relatedProductsInfor));
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t6.adapter.T6LiveViewPagerAdapter.OnClickListener
        public void onPackageClick() {
            T6HomeActivity t6HomeActivity = T6HomeActivity.this;
            t6HomeActivity.launchActivity(T6ConsumablesActivity.newIntent(t6HomeActivity, t6HomeActivity.deviceId, 27, 3, T6HomeActivity.this.relatedProductsInfor));
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t6.adapter.T6LiveViewPagerAdapter.OnClickListener
        public void onBlockClick() {
            T6HomeActivity t6HomeActivity = T6HomeActivity.this;
            t6HomeActivity.launchActivity(T6ConsumablesActivity.newIntent(t6HomeActivity, t6HomeActivity.deviceId, 27, 4, T6HomeActivity.this.relatedProductsInfor));
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t6.adapter.T6LiveViewPagerAdapter.OnClickListener
        public void updateDeviceSettings(String str, boolean z) {
            ((T6HomePresenter) ((BaseActivity) T6HomeActivity.this).mPresenter).updateDeviceSettings(str, z);
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t6.adapter.T6LiveViewPagerAdapter.OnClickListener
        public void jumpToSmartSetting() {
            T6HomeActivity t6HomeActivity = T6HomeActivity.this;
            t6HomeActivity.launchActivity(T6SmartSettingActivity.newIntent(t6HomeActivity, t6HomeActivity.deviceId, T6HomeActivity.this.deviceType));
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t6.adapter.T6LiveViewPagerAdapter.OnClickListener
        public void jumpToRegularClean() {
            if (T6HomeActivity.this.t6Record.getSettings().getKitten() == 1) {
                T6HomeActivity.this.showDisableCleanUpWindow();
            } else {
                T6HomeActivity t6HomeActivity = T6HomeActivity.this;
                t6HomeActivity.launchActivity(T6RegularCleanSettingActivity.newIntent(t6HomeActivity, t6HomeActivity.deviceId, T6HomeActivity.this.deviceType, new ArrayList()));
            }
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$2 */
    public class AnonymousClass2 implements ViewPager.OnPageChangeListener {
        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrollStateChanged(int i) {
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrolled(int i, float f, int i2) {
        }

        public AnonymousClass2() {
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageSelected(int i) {
            if (i == 1) {
                T6HomeActivity.this.t6LiveViewPagerAdapter.setCurrentPosition(i);
                T6HomeActivity.this.t6LiveViewPagerAdapter.refreshData();
            }
            CommonUtils.addSysIntMap(Constants.T6_HOME_PAGE_SELECT + T6HomeActivity.this.deviceId, i);
        }
    }

    public /* synthetic */ void lambda$initLiveView$3(View view) {
        if (this.t6Record.getState().getPower() != 1) {
            ((T6HomePresenter) this.mPresenter).turnOnOrOffDevice(true);
            this.t6LiveVideoView.setVisibility(0);
            this.isNeedTurnOnDeviceAni = true;
            this.isAnimationRunning = false;
            this.t6AnimUtil.resetScaleAnim(this.ivDeviceOpen);
            return;
        }
        if (this.t6Record.getSettings().getHomeMode() == 1 && this.t6Record.getSettings().getCameraOff() == 1) {
            openHomeModeTipWindow(getString(R.string.Home_mode_can_not_open_camera_tip));
        } else if (this.t6Record.getSettings().getCamera() == 0) {
            ((T6HomePresenter) this.mPresenter).turnOnCamera();
        } else if (this.t6Record.getState().getCameraStatus() == 0) {
            showTurnOnCameraWindow();
        }
    }

    public /* synthetic */ void lambda$initLiveView$4(View view) {
        if (canHomeModeEdit()) {
            if (this.t6Record.getSettings().getCameraInward() == 0 && this.t6Record.getSettings().getCameraOff() == 0 && this.t6Record.getSettings().getNoSound() == 0) {
                if (this.t6Record.getDeviceShared() == null) {
                    launchActivity(T6InitHomeModeActivity.newIntent(this, this.deviceId, this.t6Record.getTypeCode()));
                    return;
                } else {
                    openHomeModeTipWindow(getString(R.string.Shared_device_is_limited_tip));
                    return;
                }
            }
            if (this.t6Record.getDeviceShared() == null) {
                if (this.t6Record.getSettings().getHomeMode() == 1) {
                    showLoading();
                    ((T6HomePresenter) this.mPresenter).updateSetting("homeMode", 0);
                    return;
                } else if (this.t6Record.getSettings().getCameraConfig() != 1 && this.t6Record.getSettings().getCameraOff() == 1) {
                    openHomeModeTipWindowWithListner(getString(R.string.Home_mode_in_working_time_tip), new NewIKnowWindow.onClickIKnowListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.3
                        public AnonymousClass3() {
                        }

                        @Override // com.petkit.android.activities.petkitBleDevice.r2.widget.NewIKnowWindow.onClickIKnowListener
                        public void onClickIKnow() {
                            T6HomeActivity.this.showLoading();
                            ((T6HomePresenter) ((BaseActivity) T6HomeActivity.this).mPresenter).updateSetting("homeMode", 1);
                        }
                    });
                    return;
                } else {
                    showLoading();
                    ((T6HomePresenter) this.mPresenter).updateSetting("homeMode", 1);
                    return;
                }
            }
            openHomeModeTipWindow(getString(R.string.Shared_device_is_limited_tip));
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$3 */
    public class AnonymousClass3 implements NewIKnowWindow.onClickIKnowListener {
        public AnonymousClass3() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.r2.widget.NewIKnowWindow.onClickIKnowListener
        public void onClickIKnow() {
            T6HomeActivity.this.showLoading();
            ((T6HomePresenter) ((BaseActivity) T6HomeActivity.this).mPresenter).updateSetting("homeMode", 1);
        }
    }

    public /* synthetic */ void lambda$initLiveView$5(View view) {
        if (T6Utils.checkDeviceState(this, this.deviceId, this.deviceType, ((T6HomePresenter) this.mPresenter).getLiveService())) {
            if (this.t6Record.getSettings().getHomeMode() == 1 && this.t6Record.getSettings().getNoSound() == 1) {
                openHomeModeTipWindow(getString(R.string.Home_mode_can_not_control_voice_tip));
                return;
            }
            if (this.t6Record.getSettings().getMicrophone() == 0) {
                PetkitToast.showTopToast(this, getResources().getString(R.string.Can_not_control_voice_tip), 0, 0);
                return;
            }
            if (((T6HomePresenter) this.mPresenter).intercomBtnPushing) {
                PetkitToast.showTopToast(this, getResources().getString(R.string.Intercom_cannot_operate), 0, 0);
            } else if (T6Utils.getVolumeState()) {
                ((T6HomePresenter) this.mPresenter).openVolume();
                this.ivMuteIcon.setImageResource(R.drawable.t6_home_mute_off_icon);
            } else {
                ((T6HomePresenter) this.mPresenter).closeVolume();
                this.ivMuteIcon.setImageResource(R.drawable.t6_home_mute_icon);
            }
        }
    }

    public /* synthetic */ void lambda$initLiveView$6(View view) {
        if (this.t6Record.getDeviceShared() == null) {
            launchActivity(ToiletStatisticsActivity.newIntent(this));
        }
    }

    public /* synthetic */ void lambda$initLiveView$7(View view) {
        if (this.t6Record.getDeviceShared() == null) {
            launchActivity(ToiletStatisticsActivity.newIntent(this));
        }
    }

    public /* synthetic */ void lambda$initLiveView$8(View view) {
        if (this.t6Record.getDeviceShared() == null) {
            launchActivity(ToiletStatisticsActivity.newIntent(this));
        }
    }

    public /* synthetic */ void lambda$initLiveView$9(View view) {
        if (this.t6Record.getDeviceShared() == null) {
            launchActivity(ToiletStatisticsActivity.newIntent(this));
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T6HomeContract.View
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

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T6HomeContract.View
    public void showPurchaseEligibility() {
        if (this.purchaseEligibility == null) {
            return;
        }
        CommonUtils.addSysBoolMap(this, "HOME_PURCHASE_ELIGIBILITY" + this.deviceType + this.deviceId, true);
        NormalListChoiceCenterWindow normalListChoiceCenterWindow = new NormalListChoiceCenterWindow(this, new NormalListChoiceCenterWindow.OnClickThreeChoices() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.4
            @Override // com.petkit.android.activities.universalWindow.NormalListChoiceCenterWindow.OnClickThreeChoices
            public void onClickThirdChoice() {
            }

            public AnonymousClass4() {
            }

            @Override // com.petkit.android.activities.universalWindow.NormalListChoiceCenterWindow.OnClickThreeChoices
            public void onClickFirstChoice() {
                T6HomeActivity t6HomeActivity = T6HomeActivity.this;
                t6HomeActivity.launchActivity(CloudServiceWebViewActivity.newIntent(t6HomeActivity, "", CloudServiceUtils.getPurchaseEligibilityUrl(t6HomeActivity, t6HomeActivity.purchaseEligibility)));
            }

            /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$4$1 */
            public class AnonymousClass1 implements NormalCenterTipWindow.OnClickOk {
                @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
                public void onOkClick() {
                }

                public AnonymousClass1() {
                }

                @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
                public void onCancelClick() {
                    ((T6HomePresenter) ((BaseActivity) T6HomeActivity.this).mPresenter).purchaseEligibilityNoRemind(T6HomeActivity.this.purchaseEligibility);
                }
            }

            @Override // com.petkit.android.activities.universalWindow.NormalListChoiceCenterWindow.OnClickThreeChoices
            public void onClickSecondChoice() {
                NormalCenterTipWindow normalCenterTipWindow = new NormalCenterTipWindow(T6HomeActivity.this, new NormalCenterTipWindow.OnClickOk() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.4.1
                    @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
                    public void onOkClick() {
                    }

                    public AnonymousClass1() {
                    }

                    @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
                    public void onCancelClick() {
                        ((T6HomePresenter) ((BaseActivity) T6HomeActivity.this).mPresenter).purchaseEligibilityNoRemind(T6HomeActivity.this.purchaseEligibility);
                    }
                }, (String) null, T6HomeActivity.this.getString(R.string.Claim_close_confirm));
                normalCenterTipWindow.setCancelButtonText(T6HomeActivity.this.getString(R.string.Affirm_Close));
                normalCenterTipWindow.setOkButtonText(T6HomeActivity.this.getString(R.string.Cancel));
                normalCenterTipWindow.show(T6HomeActivity.this.getWindow().getDecorView());
            }
        }, this.purchaseEligibility.getReminderMsgTitle(), this.purchaseEligibility.getReminderMsg(), getString(R.string.Claim_now), getString(R.string.Do_not_remind_again), null);
        normalListChoiceCenterWindow.setThreeChoicesTextColor(getResources().getColor(R.color.toilet_chart_color_nine), -1, -1);
        normalListChoiceCenterWindow.setTipGravity(GravityCompat.START);
        normalListChoiceCenterWindow.setWindowMargin(20, 20);
        normalListChoiceCenterWindow.setCloseIcon(true);
        normalListChoiceCenterWindow.show(getWindow().getDecorView());
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$4 */
    public class AnonymousClass4 implements NormalListChoiceCenterWindow.OnClickThreeChoices {
        @Override // com.petkit.android.activities.universalWindow.NormalListChoiceCenterWindow.OnClickThreeChoices
        public void onClickThirdChoice() {
        }

        public AnonymousClass4() {
        }

        @Override // com.petkit.android.activities.universalWindow.NormalListChoiceCenterWindow.OnClickThreeChoices
        public void onClickFirstChoice() {
            T6HomeActivity t6HomeActivity = T6HomeActivity.this;
            t6HomeActivity.launchActivity(CloudServiceWebViewActivity.newIntent(t6HomeActivity, "", CloudServiceUtils.getPurchaseEligibilityUrl(t6HomeActivity, t6HomeActivity.purchaseEligibility)));
        }

        /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$4$1 */
        public class AnonymousClass1 implements NormalCenterTipWindow.OnClickOk {
            @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
            public void onOkClick() {
            }

            public AnonymousClass1() {
            }

            @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
            public void onCancelClick() {
                ((T6HomePresenter) ((BaseActivity) T6HomeActivity.this).mPresenter).purchaseEligibilityNoRemind(T6HomeActivity.this.purchaseEligibility);
            }
        }

        @Override // com.petkit.android.activities.universalWindow.NormalListChoiceCenterWindow.OnClickThreeChoices
        public void onClickSecondChoice() {
            NormalCenterTipWindow normalCenterTipWindow = new NormalCenterTipWindow(T6HomeActivity.this, new NormalCenterTipWindow.OnClickOk() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.4.1
                @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
                public void onOkClick() {
                }

                public AnonymousClass1() {
                }

                @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
                public void onCancelClick() {
                    ((T6HomePresenter) ((BaseActivity) T6HomeActivity.this).mPresenter).purchaseEligibilityNoRemind(T6HomeActivity.this.purchaseEligibility);
                }
            }, (String) null, T6HomeActivity.this.getString(R.string.Claim_close_confirm));
            normalCenterTipWindow.setCancelButtonText(T6HomeActivity.this.getString(R.string.Affirm_Close));
            normalCenterTipWindow.setOkButtonText(T6HomeActivity.this.getString(R.string.Cancel));
            normalCenterTipWindow.show(T6HomeActivity.this.getWindow().getDecorView());
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

    public void setFoxAnimation(int i) {
        if (this.t6Record.getSettings().getPreLive() == 0 && !this.t6LiveVideoView.isPlayed) {
            i = 3;
        }
        if (i == 0) {
            this.ivFox.setVisibility(0);
            startCleanPrepareAnimation();
            AnonymousClass5 anonymousClass5 = new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.5
                public AnonymousClass5() {
                }

                @Override // java.lang.Runnable
                public void run() {
                    T6HomeActivity.this.startCleanAnimation();
                }
            };
            this.runnable = anonymousClass5;
            this.handler.postDelayed(anonymousClass5, 3000L);
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

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$5 */
    public class AnonymousClass5 implements Runnable {
        public AnonymousClass5() {
        }

        @Override // java.lang.Runnable
        public void run() {
            T6HomeActivity.this.startCleanAnimation();
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T6HomeContract.View
    public void setActionType(int i) {
        this.clickActionType = i;
    }

    @Subscriber
    public void refreshT6Setting(T6NewUpdateSettingEvent t6NewUpdateSettingEvent) {
        Log.d(this.TAG, "T6NewUpdateSettingEvent: ");
        if (t6NewUpdateSettingEvent.getType() == 1) {
            Log.d(this.TAG, "T6NewUpdateSettingEvent: " + t6NewUpdateSettingEvent.getType());
            ((T6HomePresenter) this.mPresenter).closeVolume();
            this.ivMuteIcon.setImageResource(R.drawable.t6_home_mute_icon);
        }
    }

    private void initPlayerView() {
        T6LiveVideoView t6LiveVideoView = new T6LiveVideoView(this);
        this.t6LiveVideoView = t6LiveVideoView;
        t6LiveVideoView.setPlayerType(PetkitVideoPlayerView.PlayerType.CIRCLE);
        this.t6LiveVideoView.setTouchListener();
        this.rlParentLive.addView(this.t6LiveVideoView);
        this.t6LiveVideoView.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$$ExternalSyntheticLambda8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initPlayerView$10(view);
            }
        });
        this.t6LiveVideoView.getVideoPlayerView().showLoadingView();
        this.t6LiveVideoView.setT6LiveVideoViewListener(this);
        this.t6LiveVideoView.setListener(this);
        this.toolbar.setBackgroundColor(getResources().getColor(R.color.transparent));
        this.ibSetting.setVisibility(0);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(0);
        this.rvHighlights.setLayoutManager(linearLayoutManager);
        ViewGroup.LayoutParams layoutParams = this.flHomeBanner.getLayoutParams();
        layoutParams.height = ((BaseApplication.displayMetrics.widthPixels - ArmsUtils.dip2px(CommonUtils.getAppContext(), 16.0f)) / 343) * 64;
        this.flHomeBanner.setLayoutParams(layoutParams);
        this.ivBg.setAllRound();
        this.rlLivePanel.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.6
            public AnonymousClass6() {
            }

            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                int height = T6HomeActivity.this.rlLivePanel.getHeight();
                ViewGroup.LayoutParams layoutParams2 = T6HomeActivity.this.ivBg.getLayoutParams();
                layoutParams2.height = height;
                T6HomeActivity.this.ivBg.setLayoutParams(layoutParams2);
                T6HomeActivity.this.rlLivePanel.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    public /* synthetic */ void lambda$initPlayerView$10(View view) {
        onVideoClick();
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$6 */
    public class AnonymousClass6 implements ViewTreeObserver.OnGlobalLayoutListener {
        public AnonymousClass6() {
        }

        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public void onGlobalLayout() {
            int height = T6HomeActivity.this.rlLivePanel.getHeight();
            ViewGroup.LayoutParams layoutParams2 = T6HomeActivity.this.ivBg.getLayoutParams();
            layoutParams2.height = height;
            T6HomeActivity.this.ivBg.setLayoutParams(layoutParams2);
            T6HomeActivity.this.rlLivePanel.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }
    }

    private void initTodayTime() {
        this.calendarTime = T6Utils.getCurrentZoneTime(this.t6Record, this.deviceId, System.currentTimeMillis(), this.deviceType);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$7 */
    public class AnonymousClass7 implements OnRefreshLoadMoreListener {
        @Override // com.scwang.smart.refresh.layout.listener.OnLoadMoreListener
        public void onLoadMore(@androidx.annotation.NonNull RefreshLayout refreshLayout) {
        }

        public AnonymousClass7() {
        }

        @Override // com.scwang.smart.refresh.layout.listener.OnRefreshListener
        public void onRefresh(@androidx.annotation.NonNull RefreshLayout refreshLayout) {
            T6HomeActivity.this.refreshHeader();
        }
    }

    private void initSmartView() {
        this.srl.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.7
            @Override // com.scwang.smart.refresh.layout.listener.OnLoadMoreListener
            public void onLoadMore(@androidx.annotation.NonNull RefreshLayout refreshLayout) {
            }

            public AnonymousClass7() {
            }

            @Override // com.scwang.smart.refresh.layout.listener.OnRefreshListener
            public void onRefresh(@androidx.annotation.NonNull RefreshLayout refreshLayout) {
                T6HomeActivity.this.refreshHeader();
            }
        });
        this.srl.setDispatchListener(new MySmartRefreshView.DispatchListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$$ExternalSyntheticLambda17
            @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.MySmartRefreshView.DispatchListener
            public final void onActionDown() {
                this.f$0.lambda$initSmartView$11();
            }
        });
    }

    public /* synthetic */ void lambda$initSmartView$11() {
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

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$8 */
    public class AnonymousClass8 implements T6AnimUtil.AnimationListener {
        @Override // com.petkit.android.activities.petkitBleDevice.t6.utils.T6AnimUtil.AnimationListener
        public void scaleAnimationStar() {
        }

        public AnonymousClass8() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t6.utils.T6AnimUtil.AnimationListener
        public void alphaAnimationStar() {
            T6HomeActivity.this.ivDeviceOpen.setVisibility(0);
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t6.utils.T6AnimUtil.AnimationListener
        public void alphaAnimationEnd() {
            T6HomeActivity.this.t6AnimUtil.startScaleAnim(T6HomeActivity.this.ivDeviceOpen);
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t6.utils.T6AnimUtil.AnimationListener
        public void scaleAnimationEnd() {
            T6HomeActivity.this.clDevice.setVisibility(8);
            T6HomeActivity.this.t6LiveVideoView.setVisibility(0);
            T6HomeActivity.this.isNeedTurnOnDeviceAni = true;
            T6HomeActivity.this.isAnimationRunning = false;
            T6HomeActivity t6HomeActivity = T6HomeActivity.this;
            t6HomeActivity.setupView(t6HomeActivity.t6Record);
            T6HomeActivity.this.t6AnimUtil.resetScaleAnim(T6HomeActivity.this.ivDeviceOpen);
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t6.utils.T6AnimUtil.AnimationListener
        public void cameraAnimationStar() {
            T6HomeActivity.this.vCameraBottom.setVisibility(8);
            T6HomeActivity.this.t6LiveVideoView.setVisibility(0);
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t6.utils.T6AnimUtil.AnimationListener
        public void cameraAnimationEnd() {
            T6HomeActivity.this.isNeedTurnOnDeviceAni = true;
            T6HomeActivity.this.clCamera.setVisibility(8);
            T6HomeActivity.this.isAnimationRunning = false;
            T6HomeActivity t6HomeActivity = T6HomeActivity.this;
            t6HomeActivity.setupView(t6HomeActivity.t6Record);
        }
    }

    private void initAnimation() {
        T6AnimUtil t6AnimUtil = new T6AnimUtil();
        this.t6AnimUtil = t6AnimUtil;
        t6AnimUtil.setAnimationListener(new T6AnimUtil.AnimationListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.8
            @Override // com.petkit.android.activities.petkitBleDevice.t6.utils.T6AnimUtil.AnimationListener
            public void scaleAnimationStar() {
            }

            public AnonymousClass8() {
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.utils.T6AnimUtil.AnimationListener
            public void alphaAnimationStar() {
                T6HomeActivity.this.ivDeviceOpen.setVisibility(0);
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.utils.T6AnimUtil.AnimationListener
            public void alphaAnimationEnd() {
                T6HomeActivity.this.t6AnimUtil.startScaleAnim(T6HomeActivity.this.ivDeviceOpen);
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.utils.T6AnimUtil.AnimationListener
            public void scaleAnimationEnd() {
                T6HomeActivity.this.clDevice.setVisibility(8);
                T6HomeActivity.this.t6LiveVideoView.setVisibility(0);
                T6HomeActivity.this.isNeedTurnOnDeviceAni = true;
                T6HomeActivity.this.isAnimationRunning = false;
                T6HomeActivity t6HomeActivity = T6HomeActivity.this;
                t6HomeActivity.setupView(t6HomeActivity.t6Record);
                T6HomeActivity.this.t6AnimUtil.resetScaleAnim(T6HomeActivity.this.ivDeviceOpen);
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.utils.T6AnimUtil.AnimationListener
            public void cameraAnimationStar() {
                T6HomeActivity.this.vCameraBottom.setVisibility(8);
                T6HomeActivity.this.t6LiveVideoView.setVisibility(0);
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.utils.T6AnimUtil.AnimationListener
            public void cameraAnimationEnd() {
                T6HomeActivity.this.isNeedTurnOnDeviceAni = true;
                T6HomeActivity.this.clCamera.setVisibility(8);
                T6HomeActivity.this.isAnimationRunning = false;
                T6HomeActivity t6HomeActivity = T6HomeActivity.this;
                t6HomeActivity.setupView(t6HomeActivity.t6Record);
            }
        });
        this.t6AnimUtil.startLeftArrowAnimation(this.ivLeftArrow);
    }

    private void initFragment() {
        ArrayList arrayList = new ArrayList();
        this.toiletFragment = getFragment(0);
        this.petFragment = getFragment(1);
        this.allFragment = getFragment(2);
        this.toiletErrorFragment = getFragment(3);
        arrayList.add(this.allFragment);
        arrayList.add(this.toiletFragment);
        arrayList.add(this.petFragment);
        arrayList.add(this.toiletErrorFragment);
        this.vpList.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager(), arrayList));
        this.vpList.setPagingEnabled(false);
        this.vpList.setOffscreenPageLimit(3);
        this.vpList.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.9
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i, float f, int i2) {
            }

            public AnonymousClass9() {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int i) {
                T6HomeActivity t6HomeActivity = T6HomeActivity.this;
                t6HomeActivity.setTextUi(i, t6HomeActivity.tvTabAll, t6HomeActivity.tvToiletTab, t6HomeActivity.tvOtherTab, t6HomeActivity.tvToiletTabError);
                T6HomeActivity t6HomeActivity2 = T6HomeActivity.this;
                t6HomeActivity2.setTopTextUi(i, t6HomeActivity2.tvTabAll2, t6HomeActivity2.tvToiletTab2, t6HomeActivity2.tvOtherTab2, t6HomeActivity2.tvToiletTabError2);
                T6HomeActivity.this.currentPosition = i;
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$9 */
    public class AnonymousClass9 implements ViewPager.OnPageChangeListener {
        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrollStateChanged(int i) {
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrolled(int i, float f, int i2) {
        }

        public AnonymousClass9() {
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageSelected(int i) {
            T6HomeActivity t6HomeActivity = T6HomeActivity.this;
            t6HomeActivity.setTextUi(i, t6HomeActivity.tvTabAll, t6HomeActivity.tvToiletTab, t6HomeActivity.tvOtherTab, t6HomeActivity.tvToiletTabError);
            T6HomeActivity t6HomeActivity2 = T6HomeActivity.this;
            t6HomeActivity2.setTopTextUi(i, t6HomeActivity2.tvTabAll2, t6HomeActivity2.tvToiletTab2, t6HomeActivity2.tvOtherTab2, t6HomeActivity2.tvToiletTabError2);
            T6HomeActivity.this.currentPosition = i;
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

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$10 */
    public class AnonymousClass10 implements T6EventListFragment.FragmentListener {
        public AnonymousClass10() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t6.T6EventListFragment.FragmentListener
        public void recyclerViewScroll(int i) {
            if (i > 0) {
                if (T6HomeActivity.this.handler != null) {
                    T6HomeActivity.this.handler.sendEmptyMessage(13);
                }
            } else {
                if (i >= 0 || T6HomeActivity.this.handler == null) {
                    return;
                }
                T6HomeActivity.this.handler.sendEmptyMessage(14);
            }
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t6.T6EventListFragment.FragmentListener
        public void loadFirstPageData(int i) {
            if (i == 0) {
                T6HomeActivity.this.newToiletEventNum = 0;
                T6HomeActivity.this.newClearEventNum = 0;
            } else if (i == 1) {
                T6HomeActivity.this.newPetEventNum = 0;
            } else if (i != 3) {
                T6HomeActivity.this.hideAllRedPoint();
            } else {
                T6HomeActivity.this.newToiletErrorEventNum = 0;
            }
        }
    }

    private T6EventListFragment getFragment(int i) {
        T6EventListFragment t6EventListFragment = new T6EventListFragment();
        t6EventListFragment.setListener(new T6EventListFragment.FragmentListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.10
            public AnonymousClass10() {
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.T6EventListFragment.FragmentListener
            public void recyclerViewScroll(int i2) {
                if (i2 > 0) {
                    if (T6HomeActivity.this.handler != null) {
                        T6HomeActivity.this.handler.sendEmptyMessage(13);
                    }
                } else {
                    if (i2 >= 0 || T6HomeActivity.this.handler == null) {
                        return;
                    }
                    T6HomeActivity.this.handler.sendEmptyMessage(14);
                }
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.T6EventListFragment.FragmentListener
            public void loadFirstPageData(int i2) {
                if (i2 == 0) {
                    T6HomeActivity.this.newToiletEventNum = 0;
                    T6HomeActivity.this.newClearEventNum = 0;
                } else if (i2 == 1) {
                    T6HomeActivity.this.newPetEventNum = 0;
                } else if (i2 != 3) {
                    T6HomeActivity.this.hideAllRedPoint();
                } else {
                    T6HomeActivity.this.newToiletErrorEventNum = 0;
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
        bundle.putInt(Constants.EXTRA_DEVICE_TYPE, 27);
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
        if (this.ivAll.getVisibility() == 0 || this.ivAll2.getVisibility() == 0) {
            initTodayTime();
        }
        hideAllRedPoint();
        this.ivEventAnim.setVisibility(0);
        this.t6AnimUtil.startRotateAnim(this.ivEventAnim);
        ((T6HomePresenter) this.mPresenter).getStatistic(this.calendarTime, 0);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$11 */
    public class AnonymousClass11 implements T6BottomView.BottomListener {
        public AnonymousClass11() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.T6BottomView.BottomListener
        public void packed() {
            T6HomeActivity t6HomeActivity = T6HomeActivity.this;
            t6HomeActivity.launchActivity(T6PackGuideActivity.newIntent(t6HomeActivity, t6HomeActivity.deviceId));
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.T6BottomView.BottomListener
        public void door() {
            T6HomeActivity.this.showDoorWindow(T6HomeActivity.this.t6Record.getState().getSealDoorState() == 1);
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.T6BottomView.BottomListener
        public void smooth() {
            T6HomeActivity.this.showSmoothWindow();
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.T6BottomView.BottomListener
        public void dump() {
            T6HomeActivity t6HomeActivity = T6HomeActivity.this;
            t6HomeActivity.launchActivity(T6SandGuideActivity.newIntent(t6HomeActivity, t6HomeActivity.deviceId));
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.T6BottomView.BottomListener
        public void recover() {
            T6HomeActivity.this.showRecoverWindow();
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.T6BottomView.BottomListener
        public void baseSet() {
            T6HomeActivity t6HomeActivity = T6HomeActivity.this;
            t6HomeActivity.launchActivity(T6SmartSettingActivity.newIntent(t6HomeActivity, t6HomeActivity.deviceId, T6HomeActivity.this.deviceType));
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.T6BottomView.BottomListener
        public void clean() {
            T6HomeActivity.this.showCleanUpWindow();
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.T6BottomView.BottomListener
        public void talkDown() {
            if (T6HomeActivity.this.t6Record.getState().getOverall() == 2) {
                T6HomeActivity t6HomeActivity = T6HomeActivity.this;
                PetkitToast.showTopToast(t6HomeActivity, t6HomeActivity.getResources().getString(R.string.Mate_connect_offline), 0, 0);
                return;
            }
            if (T6HomeActivity.this.videoTagLayout.getVisibility() != 0) {
                T6HomeActivity t6HomeActivity2 = T6HomeActivity.this;
                PetkitToast.showTopToast(t6HomeActivity2, t6HomeActivity2.getResources().getString(R.string.Turn_on_camera_and_try_again), 0, 0);
                return;
            }
            if (DataHelper.getBooleanSF(T6HomeActivity.this, Constants.T6_HOWLING_FLAG + T6HomeActivity.this.t6Record.getDeviceId())) {
                ((T6HomePresenter) ((BaseActivity) T6HomeActivity.this).mPresenter).startRecordAudioDown();
                T6HomeActivity.this.soundWaveView.setVisibility(0);
                if (T6HomeActivity.this.t6Record.getSettings().getHomeMode() == 1 || T6HomeActivity.this.t6Record.getSettings().getNoSound() == 1) {
                    T6HomeActivity.this.ivMuteIcon.setImageResource(R.drawable.t6_home_mute_icon);
                    return;
                } else {
                    T6HomeActivity.this.ivMuteIcon.setImageResource(R.drawable.t6_home_mute_off_icon);
                    return;
                }
            }
            T6HomeActivity.this.showHowlingWindow();
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.T6BottomView.BottomListener
        public void talkUp() {
            ((T6HomePresenter) ((BaseActivity) T6HomeActivity.this).mPresenter).startRecordAudioUp();
            T6HomeActivity.this.soundWaveView.setVisibility(4);
            if (DeviceVolumeUtils.getInstance().isLiveMuteFlag(T6HomeActivity.this.deviceType)) {
                T6HomeActivity.this.ivMuteIcon.setImageResource(R.drawable.t6_home_mute_icon);
            } else {
                T6HomeActivity.this.ivMuteIcon.setImageResource(R.drawable.t6_home_mute_off_icon);
            }
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.T6BottomView.BottomListener
        public void light() {
            ((T6HomePresenter) ((BaseActivity) T6HomeActivity.this).mPresenter).switchLightState();
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.T6BottomView.BottomListener
        public void Ai(boolean z) {
            if (z) {
                return;
            }
            T6HomeActivity t6HomeActivity = T6HomeActivity.this;
            PetkitToast.showTopToast(t6HomeActivity, t6HomeActivity.getResources().getString(R.string.Petkit_ai_tips), 0, 0);
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.T6BottomView.BottomListener
        public void AiVoice() {
            T6HomeActivity t6HomeActivity = T6HomeActivity.this;
            t6HomeActivity.startActivity(AiVoiceActivity.newIntent(t6HomeActivity, t6HomeActivity.deviceId, T6HomeActivity.this.t6Record.getTypeCode()));
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.T6BottomView.BottomListener
        public void AiPh() {
            T6HomeActivity t6HomeActivity = T6HomeActivity.this;
            t6HomeActivity.startActivity(AiPhActivity.newIntent(t6HomeActivity, t6HomeActivity.deviceId, T6HomeActivity.this.t6Record.getTypeCode(), T6HomeActivity.this.relatedProductsInfor));
        }
    }

    private void initBottomView() {
        this.t6BottomView.setBottomListener(new T6BottomView.BottomListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.11
            public AnonymousClass11() {
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.T6BottomView.BottomListener
            public void packed() {
                T6HomeActivity t6HomeActivity = T6HomeActivity.this;
                t6HomeActivity.launchActivity(T6PackGuideActivity.newIntent(t6HomeActivity, t6HomeActivity.deviceId));
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.T6BottomView.BottomListener
            public void door() {
                T6HomeActivity.this.showDoorWindow(T6HomeActivity.this.t6Record.getState().getSealDoorState() == 1);
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.T6BottomView.BottomListener
            public void smooth() {
                T6HomeActivity.this.showSmoothWindow();
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.T6BottomView.BottomListener
            public void dump() {
                T6HomeActivity t6HomeActivity = T6HomeActivity.this;
                t6HomeActivity.launchActivity(T6SandGuideActivity.newIntent(t6HomeActivity, t6HomeActivity.deviceId));
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.T6BottomView.BottomListener
            public void recover() {
                T6HomeActivity.this.showRecoverWindow();
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.T6BottomView.BottomListener
            public void baseSet() {
                T6HomeActivity t6HomeActivity = T6HomeActivity.this;
                t6HomeActivity.launchActivity(T6SmartSettingActivity.newIntent(t6HomeActivity, t6HomeActivity.deviceId, T6HomeActivity.this.deviceType));
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.T6BottomView.BottomListener
            public void clean() {
                T6HomeActivity.this.showCleanUpWindow();
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.T6BottomView.BottomListener
            public void talkDown() {
                if (T6HomeActivity.this.t6Record.getState().getOverall() == 2) {
                    T6HomeActivity t6HomeActivity = T6HomeActivity.this;
                    PetkitToast.showTopToast(t6HomeActivity, t6HomeActivity.getResources().getString(R.string.Mate_connect_offline), 0, 0);
                    return;
                }
                if (T6HomeActivity.this.videoTagLayout.getVisibility() != 0) {
                    T6HomeActivity t6HomeActivity2 = T6HomeActivity.this;
                    PetkitToast.showTopToast(t6HomeActivity2, t6HomeActivity2.getResources().getString(R.string.Turn_on_camera_and_try_again), 0, 0);
                    return;
                }
                if (DataHelper.getBooleanSF(T6HomeActivity.this, Constants.T6_HOWLING_FLAG + T6HomeActivity.this.t6Record.getDeviceId())) {
                    ((T6HomePresenter) ((BaseActivity) T6HomeActivity.this).mPresenter).startRecordAudioDown();
                    T6HomeActivity.this.soundWaveView.setVisibility(0);
                    if (T6HomeActivity.this.t6Record.getSettings().getHomeMode() == 1 || T6HomeActivity.this.t6Record.getSettings().getNoSound() == 1) {
                        T6HomeActivity.this.ivMuteIcon.setImageResource(R.drawable.t6_home_mute_icon);
                        return;
                    } else {
                        T6HomeActivity.this.ivMuteIcon.setImageResource(R.drawable.t6_home_mute_off_icon);
                        return;
                    }
                }
                T6HomeActivity.this.showHowlingWindow();
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.T6BottomView.BottomListener
            public void talkUp() {
                ((T6HomePresenter) ((BaseActivity) T6HomeActivity.this).mPresenter).startRecordAudioUp();
                T6HomeActivity.this.soundWaveView.setVisibility(4);
                if (DeviceVolumeUtils.getInstance().isLiveMuteFlag(T6HomeActivity.this.deviceType)) {
                    T6HomeActivity.this.ivMuteIcon.setImageResource(R.drawable.t6_home_mute_icon);
                } else {
                    T6HomeActivity.this.ivMuteIcon.setImageResource(R.drawable.t6_home_mute_off_icon);
                }
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.T6BottomView.BottomListener
            public void light() {
                ((T6HomePresenter) ((BaseActivity) T6HomeActivity.this).mPresenter).switchLightState();
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.T6BottomView.BottomListener
            public void Ai(boolean z) {
                if (z) {
                    return;
                }
                T6HomeActivity t6HomeActivity = T6HomeActivity.this;
                PetkitToast.showTopToast(t6HomeActivity, t6HomeActivity.getResources().getString(R.string.Petkit_ai_tips), 0, 0);
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.T6BottomView.BottomListener
            public void AiVoice() {
                T6HomeActivity t6HomeActivity = T6HomeActivity.this;
                t6HomeActivity.startActivity(AiVoiceActivity.newIntent(t6HomeActivity, t6HomeActivity.deviceId, T6HomeActivity.this.t6Record.getTypeCode()));
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.T6BottomView.BottomListener
            public void AiPh() {
                T6HomeActivity t6HomeActivity = T6HomeActivity.this;
                t6HomeActivity.startActivity(AiPhActivity.newIntent(t6HomeActivity, t6HomeActivity.deviceId, T6HomeActivity.this.t6Record.getTypeCode(), T6HomeActivity.this.relatedProductsInfor));
            }
        });
        this.t6BottomView.findViewById(R.id.ll_ai_cat).setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$$ExternalSyntheticLambda19
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initBottomView$12(view);
            }
        });
        this.t6BottomView.findViewById(R.id.ll_ai_add).setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$$ExternalSyntheticLambda20
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initBottomView$13(view);
            }
        });
        this.t6BottomView.showAiEnter(false);
    }

    public /* synthetic */ void lambda$initBottomView$12(View view) {
        launchActivity(PetWeightActivity.newIntent(this, this.deviceId, this.deviceType));
    }

    public /* synthetic */ void lambda$initBottomView$13(View view) {
        AiDataUtil.getInstance().saveClickedTask(this, this.aiNewInfo.getInfo().getId(), this.deviceId, this.deviceType);
        launchActivity(AiActivity.newIntent(this, 0, this.deviceType, this.deviceId, ""));
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$12 */
    public class AnonymousClass12 implements NormalCenterTipWindow.OnClickOk {
        public final /* synthetic */ boolean val$open;

        @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
        public void onCancelClick() {
        }

        public AnonymousClass12(boolean z) {
            z = z;
        }

        @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
        public void onOkClick() {
            if (((BaseActivity) T6HomeActivity.this).mPresenter != null) {
                ((T6HomePresenter) ((BaseActivity) T6HomeActivity.this).mPresenter).optionDoor(z);
            }
            if (T6HomeActivity.this.doorWindow != null) {
                T6HomeActivity.this.doorWindow.dismiss();
            }
        }
    }

    public void showDoorWindow(boolean z) {
        NormalCenterTipWindow normalCenterTipWindow = new NormalCenterTipWindow(getApplicationContext(), new NormalCenterTipWindow.OnClickOk() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.12
            public final /* synthetic */ boolean val$open;

            @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
            public void onCancelClick() {
            }

            public AnonymousClass12(boolean z2) {
                z = z2;
            }

            @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
            public void onOkClick() {
                if (((BaseActivity) T6HomeActivity.this).mPresenter != null) {
                    ((T6HomePresenter) ((BaseActivity) T6HomeActivity.this).mPresenter).optionDoor(z);
                }
                if (T6HomeActivity.this.doorWindow != null) {
                    T6HomeActivity.this.doorWindow.dismiss();
                }
            }
        }, (String) null, getResources().getString(z2 ? R.string.T6_close_sealed_doors_tips : R.string.T6_open_sealed_doors_tips));
        this.doorWindow = normalCenterTipWindow;
        normalCenterTipWindow.setOkText(getString(R.string.Confirm));
        T6BottomView t6BottomView = this.t6BottomView;
        if (t6BottomView != null) {
            t6BottomView.hideTopView();
        }
        this.doorWindow.show(getWindow().getDecorView());
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$13 */
    public class AnonymousClass13 implements NormalCenterTipWindow.OnClickOk {
        @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
        public void onCancelClick() {
        }

        public AnonymousClass13() {
        }

        @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
        public void onOkClick() {
            if (((BaseActivity) T6HomeActivity.this).mPresenter != null) {
                ((T6HomePresenter) ((BaseActivity) T6HomeActivity.this).mPresenter).optionSmooth();
            }
            if (T6HomeActivity.this.smoothWindow != null) {
                T6HomeActivity.this.smoothWindow.dismiss();
            }
        }
    }

    public void showSmoothWindow() {
        NormalCenterTipWindow normalCenterTipWindow = new NormalCenterTipWindow(getApplicationContext(), new NormalCenterTipWindow.OnClickOk() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.13
            @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
            public void onCancelClick() {
            }

            public AnonymousClass13() {
            }

            @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
            public void onOkClick() {
                if (((BaseActivity) T6HomeActivity.this).mPresenter != null) {
                    ((T6HomePresenter) ((BaseActivity) T6HomeActivity.this).mPresenter).optionSmooth();
                }
                if (T6HomeActivity.this.smoothWindow != null) {
                    T6HomeActivity.this.smoothWindow.dismiss();
                }
            }
        }, (String) null, getResources().getString(R.string.T6_level_off_tips));
        this.smoothWindow = normalCenterTipWindow;
        normalCenterTipWindow.setOkText(getString(R.string.Confirm));
        T6BottomView t6BottomView = this.t6BottomView;
        if (t6BottomView != null) {
            t6BottomView.hideTopView();
        }
        this.smoothWindow.show(getWindow().getDecorView());
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$14 */
    public class AnonymousClass14 implements NormalCenterTipWindow.OnClickOk {
        @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
        public void onCancelClick() {
        }

        public AnonymousClass14() {
        }

        @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
        public void onOkClick() {
            if (((BaseActivity) T6HomeActivity.this).mPresenter != null) {
                ((T6HomePresenter) ((BaseActivity) T6HomeActivity.this).mPresenter).optionRecover();
            }
            if (T6HomeActivity.this.recoverWindow != null) {
                T6HomeActivity.this.recoverWindow.dismiss();
            }
        }
    }

    public void showRecoverWindow() {
        NormalCenterTipWindow normalCenterTipWindow = new NormalCenterTipWindow(getApplicationContext(), new NormalCenterTipWindow.OnClickOk() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.14
            @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
            public void onCancelClick() {
            }

            public AnonymousClass14() {
            }

            @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
            public void onOkClick() {
                if (((BaseActivity) T6HomeActivity.this).mPresenter != null) {
                    ((T6HomePresenter) ((BaseActivity) T6HomeActivity.this).mPresenter).optionRecover();
                }
                if (T6HomeActivity.this.recoverWindow != null) {
                    T6HomeActivity.this.recoverWindow.dismiss();
                }
            }
        }, (String) null, getResources().getString(R.string.T6_reset_tips));
        this.recoverWindow = normalCenterTipWindow;
        normalCenterTipWindow.setOkText(getString(R.string.Confirm));
        T6BottomView t6BottomView = this.t6BottomView;
        if (t6BottomView != null) {
            t6BottomView.hideTopView();
        }
        this.recoverWindow.show(getWindow().getDecorView());
    }

    public void openHomeModeTipWindow(String str) {
        NewIKnowWindow newIKnowWindow = new NewIKnowWindow(this, (String) null, str, (String) null);
        this.homeModeWindow = newIKnowWindow;
        newIKnowWindow.show(getWindow().getDecorView());
    }

    public void openHomeModeTipWindowWithListner(String str, NewIKnowWindow.onClickIKnowListener onclickiknowlistener) {
        NewIKnowWindow newIKnowWindow = new NewIKnowWindow(this, (String) null, str, (String) null);
        this.homeModeWindowWithListner = newIKnowWindow;
        newIKnowWindow.setOnClickIKnowListener(onclickiknowlistener);
        this.homeModeWindowWithListner.show(getWindow().getDecorView());
    }

    private boolean canOption() {
        if (this.t6Record.getState().getOverall() == 2) {
            PetkitToast.showTopToast(this, getResources().getString(R.string.Mate_connect_offline), 0, 0);
            return false;
        }
        if (this.videoTagLayout.getVisibility() != 0) {
            PetkitToast.showTopToast(this, getResources().getString(R.string.Turn_on_camera_and_try_again), 0, 0);
            return false;
        }
        if (this.t6Record.getState() != null && this.t6Record.getState().getPetInTime() != 0) {
            PetkitToast.showTopToast(this, getResources().getString(R.string.T6_control_dev_toast4), 0, 1);
            return false;
        }
        if (this.t6Record.getState() != null && this.t6Record.getState().getWeightState() != 0) {
            PetkitToast.showTopToast(this, getResources().getString(R.string.Camera_rotate_not_operable), 0, 1);
            return false;
        }
        if (this.t6Record.getState().getWorkState() != null) {
            PetkitToast.showTopToast(this, getResources().getString(R.string.T6_control_dev_toast1), 0, 1);
            return false;
        }
        if (this.t6Record.getSettings().getHomeMode() != 1 || this.t6Record.getSettings().getCameraInward() != 1) {
            return true;
        }
        openHomeModeTipWindow(getString(R.string.Home_mode_can_not_move_camera_tip));
        return false;
    }

    public boolean canHomeModeEdit() {
        if (this.t6Record.getState() != null && this.t6Record.getState().getPetInTime() != 0) {
            return true;
        }
        if (this.t6Record.getState() != null && this.t6Record.getState().getOverall() == 2) {
            PetkitToast.showTopToast(this, getString(R.string.Cant_controll_now_offline), 0, 0);
            return false;
        }
        if (this.t6Record.getState() != null && this.t6Record.getState().getOverall() == 3) {
            PetkitToast.showTopToast(this, getString(R.string.Cant_controll_now_updating), 0, 0);
            return false;
        }
        if (this.t6Record.getState() != null && this.t6Record.getState().getPower() == 0) {
            PetkitToast.showTopToast(this, getString(R.string.Cant_controll_now_power_off), 0, 0);
            return false;
        }
        if (this.t6Record.getState() != null && !TextUtils.isEmpty(this.t6Record.getState().getErrorCode())) {
            PetkitToast.showTopToast(this, getString(R.string.Cant_controll_now_err), 0, 0);
            return false;
        }
        if (this.t6Record.getState() != null && this.t6Record.getState().getWeightState() == 1) {
            PetkitToast.showTopToast(this, getString(R.string.Cant_controll_now_err), 0, 0);
            return false;
        }
        if (this.t6Record.getState() == null || this.t6Record.getState().getWorkState() == null || this.t6Record.getState().getWorkState().getWorkMode() == 11) {
            return true;
        }
        PetkitToast.showTopToast(this, getString(R.string.Cant_controll_now_working), 0, 0);
        return false;
    }

    private void jumpToSandDumping() {
        if (this.jump) {
            return;
        }
        this.jump = true;
        launchActivity(T6SandDumpingActivity.newIntent(this, this.deviceId, this.deviceType));
    }

    private void jumpToPackError(int i) {
        String str;
        String str2;
        String errorCode;
        String errorMsg;
        if (this.jump) {
            return;
        }
        this.jump = true;
        if (this.t6Record.getState() == null) {
            str = "";
            str2 = str;
        } else {
            if (breakdown()) {
                errorCode = this.t6Record.getState().getErrorCode();
                errorMsg = this.t6Record.getState().getErrorMsg();
            } else {
                errorCode = getErrorCode();
                errorMsg = getErrorMsg(errorCode);
            }
            str2 = errorCode;
            str = errorMsg;
        }
        launchActivity(T6PackErrorActivity.newIntent(this, this.deviceId, str, i, str2));
    }

    private String getErrorMsg(String str) {
        if (this.t6Record == null) {
            return "";
        }
        str.hashCode();
        switch (str) {
            case "t6_error_roll_weight":
                return getResources().getString(R.string.Error_Weight_Increased_Overtime);
            case "hallLC":
            case "hallLO":
            case "hallSC":
            case "hallSO":
            case "heater":
            case "moto_F":
            case "moto_L":
            case "moto_S":
            case "hallSLC":
            case "hallSLO":
            case "moto_SL":
                return this.t6Record.getState().getErrorMsg();
            case "box_state_un_bagging":
            case "T6_garbage_bag_not_in_place":
                return getResources().getString(R.string.T6_garbage_bag_not_in_place);
            case "trunk_F":
                return getResources().getString(R.string.Main_engine_tail_box_matter);
            case "box_state_destroy":
                return getResources().getString(R.string.Garbage_bag_breakage);
            case "hallP":
                return getResources().getString(R.string.T6_error_bagBoxTopIns_not_installed);
            case "hallT":
                return getResources().getString(R.string.Main_engine_tail_box_not_installed);
            case "packbox_E":
                return getResources().getString(R.string.T6_error_bags_have_run_out);
            case "packbox_I":
                return getResources().getString(R.string.T6_error_bagBox_invalid);
            case "packbox_U":
                return getResources().getString(R.string.T6_error_bagBox_not_installed);
            default:
                return "";
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T6HomeContract.View
    public void onRecording(int i) {
        D4shPlayerSoundWaveView d4shPlayerSoundWaveView = this.soundWaveView;
        if (d4shPlayerSoundWaveView != null) {
            d4shPlayerSoundWaveView.setVoiceLineVolume(i);
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$15 */
    public class AnonymousClass15 implements SpannableStringColorsPicPromptWindow.OnClickListener {
        @Override // com.petkit.android.activities.universalWindow.SpannableStringColorsPicPromptWindow.OnClickListener
        public void onCancel() {
        }

        @Override // com.petkit.android.activities.universalWindow.SpannableStringColorsPicPromptWindow.OnClickListener
        public void onConfirm() {
        }

        public AnonymousClass15() {
        }
    }

    public void showHowlingWindow() {
        SpannableStringColorsPicPromptWindow spannableStringColorsPicPromptWindow = this.window;
        if (spannableStringColorsPicPromptWindow == null || !spannableStringColorsPicPromptWindow.isShowing()) {
            SpannableStringColorsPicPromptWindow spannableStringColorsPicPromptWindow2 = new SpannableStringColorsPicPromptWindow(this, getResources().getString(R.string.Prompt), getResources().getString(R.string.D4sh_tip_prompt_two), getResources().getString(R.string.D4sh_tip_prompt_one), getResources().getString(R.string.Feeder_i_know), R.drawable.t6_howling_tip, new SpannableStringColorsPicPromptWindow.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.15
                @Override // com.petkit.android.activities.universalWindow.SpannableStringColorsPicPromptWindow.OnClickListener
                public void onCancel() {
                }

                @Override // com.petkit.android.activities.universalWindow.SpannableStringColorsPicPromptWindow.OnClickListener
                public void onConfirm() {
                }

                public AnonymousClass15() {
                }
            }, new String[0]);
            this.window = spannableStringColorsPicPromptWindow2;
            spannableStringColorsPicPromptWindow2.show(getWindow().getDecorView());
            DataHelper.setBooleanSF(this, Constants.T6_HOWLING_FLAG + this.t6Record.getDeviceId(), Boolean.TRUE);
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$16 */
    public class AnonymousClass16 implements Runnable {
        public AnonymousClass16() {
        }

        @Override // java.lang.Runnable
        public void run() {
            T6HomeActivity.this.videoTagLayout.setVisibility(8);
            if (T6HomeActivity.this.t6LiveViewPagerAdapter != null) {
                T6HomeActivity.this.t6LiveViewPagerAdapter.setLive(false);
                if (T6HomeActivity.this.vpLive.getCurrentItem() == 1) {
                    T6HomeActivity.this.t6LiveViewPagerAdapter.setCurrentPosition(T6HomeActivity.this.vpLive.getCurrentItem());
                    T6HomeActivity.this.t6LiveViewPagerAdapter.refreshData();
                }
            }
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T6HomeContract.View
    public void stopLive() {
        runOnUiThread(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.16
            public AnonymousClass16() {
            }

            @Override // java.lang.Runnable
            public void run() {
                T6HomeActivity.this.videoTagLayout.setVisibility(8);
                if (T6HomeActivity.this.t6LiveViewPagerAdapter != null) {
                    T6HomeActivity.this.t6LiveViewPagerAdapter.setLive(false);
                    if (T6HomeActivity.this.vpLive.getCurrentItem() == 1) {
                        T6HomeActivity.this.t6LiveViewPagerAdapter.setCurrentPosition(T6HomeActivity.this.vpLive.getCurrentItem());
                        T6HomeActivity.this.t6LiveViewPagerAdapter.refreshData();
                    }
                }
            }
        });
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T6HomeContract.View
    public void bindServicePrompt(int i) {
        if (this.t6Record.getServiceStatus() == 1 || this.t6Record.getServiceStatus() == 2) {
            DataHelper.setBooleanSF(this, Constants.T6_BIND_SERVICE_PROMPT + this.deviceId, Boolean.TRUE);
            ((T6HomePresenter) this.mPresenter).checkInitWindow(6);
            return;
        }
        if (i > 0) {
            openBindServiceWindow(getResources().getString(R.string.Bind_cloud_service_prompt));
            return;
        }
        DataHelper.setBooleanSF(this, Constants.T6_BIND_SERVICE_PROMPT + this.deviceId, Boolean.FALSE);
        ((T6HomePresenter) this.mPresenter).checkInitWindow(6);
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T6HomeContract.View
    public void initDeviceDetailFinish() {
        if (TextUtils.isEmpty(this.eventId) || this.eventId.equals("null") || this.eventId.equals("Null") || this.eventId.equals("NULL")) {
            return;
        }
        ((T6HomePresenter) this.mPresenter).getEventMediaInfo(this.deviceId, this.eventId);
        this.eventId = null;
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T6HomeContract.View
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

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T6HomeContract.View
    public void showSandSuccess() {
        if (this.jump) {
            return;
        }
        if (this.dumpSuccessDialog == null) {
            this.dumpSuccessDialog = new SandDumpSuccessDialog(this);
        }
        if (this.dumpSuccessDialog.isShowing()) {
            return;
        }
        this.dumpSuccessDialog.show();
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T6HomeContract.View
    public void onCameraTurning() {
        showFlipCameraAnim(false);
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T6HomeContract.View
    public void rtcTokenExpired() {
        Message message = new Message();
        message.what = 22;
        message.arg1 = 0;
        this.handler.sendMessageDelayed(message, 500L);
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T6HomeContract.View
    public void openSealDoor(boolean z) {
        Resources resources;
        int i;
        if (this.isActivityInBackgrouond) {
            return;
        }
        if (z) {
            resources = getResources();
            i = R.string.Open_sealed_doors_success;
        } else {
            resources = getResources();
            i = R.string.Close_sealed_doors_success;
        }
        PetkitToast.showTopToast(this, resources.getString(i), R.drawable.top_toast_success_icon, 0);
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T6HomeContract.View
    public void getAiInfoSuccess(AiInfo aiInfo) {
        this.aiNewInfo = aiInfo;
        if (AiDataUtil.getInstance().isDeviceHaveNewAiActivity(this, aiInfo, this.deviceId, 27) && !FamilyUtils.getInstance().checkIsSharedDevice(this, this.deviceId, this.deviceType) && this.t6Record.getDeviceShared() == null) {
            this.t6BottomView.setAiIconHaveNewMessage(true);
        } else {
            this.t6BottomView.setAiIconHaveNewMessage(false);
        }
        AiDataUtil.getInstance().saveAiData(this, aiInfo, this.deviceId, 27);
        if (aiInfo == null || aiInfo.getInfo() == null || this.t6Record != null) {
            this.t6BottomView.showAiEnter(false);
        } else {
            this.t6BottomView.showAiEnter(false);
        }
        if (this.aiNotification) {
            if (aiInfo != null && (aiInfo.getInfo() == null || (aiInfo.getInfo() != null && aiInfo.getInfo().getId() < 1))) {
                showAiEndActWindow();
            } else {
                new Handler().postDelayed(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$$ExternalSyntheticLambda44
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$getAiInfoSuccess$14();
                    }
                }, 800L);
            }
        }
    }

    public /* synthetic */ void lambda$getAiInfoSuccess$14() {
        if (isFinishing()) {
            return;
        }
        launchActivity(AiActivity.newIntent(this, 0, this.deviceType, this.deviceId, ""));
    }

    private void showAiEndActWindow() {
        NewIKnowWindow newIKnowWindow = new NewIKnowWindow(this, (String) null, getResources().getString(R.string.Co_creation_activity_end), (String) null, (String) null);
        if (newIKnowWindow.isShowing()) {
            return;
        }
        newIKnowWindow.show(getWindow().getDecorView());
    }

    public void openBindServiceWindow(String str) {
        SpannableString spannableString = new SpannableString(str);
        NormalCenterTipWindow normalCenterTipWindow = this.bindServiceWindow;
        if (normalCenterTipWindow == null || !normalCenterTipWindow.isShowing()) {
            NormalCenterTipWindow normalCenterTipWindow2 = new NormalCenterTipWindow(this, new NormalCenterTipWindow.OnClickOk() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.17
                public AnonymousClass17() {
                }

                @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
                public void onOkClick() {
                    DataHelper.setBooleanSF(T6HomeActivity.this, Constants.T6_BIND_SERVICE_PROMPT + T6HomeActivity.this.deviceId, Boolean.FALSE);
                    T6HomeActivity.this.launchActivity(new Intent(T6HomeActivity.this, (Class<?>) ServiceManagementActivity.class));
                }

                @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
                public void onCancelClick() {
                    DataHelper.setBooleanSF(T6HomeActivity.this, Constants.T6_BIND_SERVICE_PROMPT + T6HomeActivity.this.deviceId, Boolean.FALSE);
                }
            }, (String) null, spannableString);
            this.bindServiceWindow = normalCenterTipWindow2;
            ((TextView) normalCenterTipWindow2.getContentView().findViewById(R.id.tv_cancel)).setText(getString(R.string.Cancel));
            ((TextView) this.bindServiceWindow.getContentView().findViewById(R.id.tv_ok)).setText(getString(R.string.Decorrelation));
            this.bindServiceWindow.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.18
                public AnonymousClass18() {
                }

                @Override // android.widget.PopupWindow.OnDismissListener
                public void onDismiss() {
                    ((T6HomePresenter) ((BaseActivity) T6HomeActivity.this).mPresenter).checkInitWindow(6);
                }
            });
            this.bindServiceWindow.show(getWindow().getDecorView());
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$17 */
    public class AnonymousClass17 implements NormalCenterTipWindow.OnClickOk {
        public AnonymousClass17() {
        }

        @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
        public void onOkClick() {
            DataHelper.setBooleanSF(T6HomeActivity.this, Constants.T6_BIND_SERVICE_PROMPT + T6HomeActivity.this.deviceId, Boolean.FALSE);
            T6HomeActivity.this.launchActivity(new Intent(T6HomeActivity.this, (Class<?>) ServiceManagementActivity.class));
        }

        @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
        public void onCancelClick() {
            DataHelper.setBooleanSF(T6HomeActivity.this, Constants.T6_BIND_SERVICE_PROMPT + T6HomeActivity.this.deviceId, Boolean.FALSE);
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$18 */
    public class AnonymousClass18 implements PopupWindow.OnDismissListener {
        public AnonymousClass18() {
        }

        @Override // android.widget.PopupWindow.OnDismissListener
        public void onDismiss() {
            ((T6HomePresenter) ((BaseActivity) T6HomeActivity.this).mPresenter).checkInitWindow(6);
        }
    }

    public void showCleanUpWindow() {
        View viewInflate = LayoutInflater.from(this).inflate(R.layout.pop_t3_clean_layout, (ViewGroup) null);
        final EasyPopupWindow easyPopupWindow = new EasyPopupWindow(this);
        easyPopupWindow.setOutsideTouchable(false);
        TextView textView = (TextView) viewInflate.findViewById(R.id.tv_cancel);
        textView.setTextColor(getResources().getColor(R.color.new_bind_blue));
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$$ExternalSyntheticLambda9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                easyPopupWindow.dismiss();
            }
        });
        TextView textView2 = (TextView) viewInflate.findViewById(R.id.tv_confirm);
        textView2.setTextColor(getResources().getColor(R.color.new_bind_blue));
        textView2.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$$ExternalSyntheticLambda10
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$showCleanUpWindow$16(easyPopupWindow, view);
            }
        });
        TextView textView3 = (TextView) viewInflate.findViewById(R.id.tv_content);
        if (this.t6Record.getSettings().getKitten() == 1) {
            textView3.setText(getString(R.string.Kitten_clean_prompt));
        } else {
            textView3.setText(getString(R.string.Clean_litter_prompt));
        }
        easyPopupWindow.setContentView(viewInflate);
        easyPopupWindow.setHeight(-1);
        easyPopupWindow.setWidth((int) (DeviceUtils.getDisplayMetrics(this).widthPixels * 0.8f));
        T6BottomView t6BottomView = this.t6BottomView;
        if (t6BottomView != null) {
            t6BottomView.hideTopView();
        }
        easyPopupWindow.showAtLocation(getWindow().getDecorView(), 17, 0, 0);
    }

    public /* synthetic */ void lambda$showCleanUpWindow$16(EasyPopupWindow easyPopupWindow, View view) {
        easyPopupWindow.dismiss();
        ((T6HomePresenter) this.mPresenter).cleanup(this.deviceId);
    }

    private void refreshCameraStatus(T6Record t6Record) {
        PetkitLog.d("refreshCameraStatus:" + t6Record.getSettings().getCamera() + " status:" + t6Record.getState().getCameraStatus());
        if (t6Record.getSettings().getCamera() == 0) {
            setFoxAnimation(3);
            unOpenCamera();
            this.tvTurnOn.setVisibility(0);
            this.tvTurnOn.setText(R.string.Turn_on_camera);
            this.llControl.setVisibility(8);
            this.ivMuteIcon.setVisibility(8);
            return;
        }
        if (t6Record.getSettings().getHomeMode() == 1 && t6Record.getSettings().getCameraOff() == 1) {
            setFoxAnimation(3);
            unOpenCamera();
            this.tvTurnOn.setVisibility(0);
            this.tvTurnOn.setText(R.string.Turn_on_camera);
            this.llControl.setVisibility(8);
            this.ivMuteIcon.setVisibility(8);
            return;
        }
        if (t6Record.getState().getCameraStatus() == 0) {
            setFoxAnimation(3);
            unOpenCamera();
            this.tvTurnOn.setVisibility(0);
            this.tvTurnOn.setText(R.string.Turn_on_camera_out_worktime);
            this.llControl.setVisibility(8);
            this.ivMuteIcon.setVisibility(8);
            return;
        }
        openCamera();
        this.tvTurnOn.setVisibility(8);
        this.llControl.setVisibility(0);
        this.ivMuteIcon.setVisibility(0);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        P p;
        super.onResume();
        this.isActivityInBackgrouond = false;
        this.jump = false;
        if (this.clickIntoFullscreen && (p = this.mPresenter) != 0) {
            ((T6HomePresenter) p).reCallBackRtm();
        }
        this.clickIntoFullscreen = false;
        if (this.refreshSd) {
            startActivity(T6LiveFullscreenActivity.newIntent(this, this.deviceId, this.deviceType, true));
            overridePendingTransition(0, 0);
            return;
        }
        this.t6Record = T6Utils.getT6RecordByDeviceId(this.deviceId, this.deviceType == 27 ? 0 : 1);
        if (AiDataUtil.getInstance().getIsDeviceNeedShowRedPoint(this, this.deviceId, 27) && !FamilyUtils.getInstance().checkIsSharedDevice(this, this.deviceId, this.deviceType) && this.t6Record.getDeviceShared() == null) {
            this.t6BottomView.setAiIconHaveNewMessage(true);
        } else {
            this.t6BottomView.setAiIconHaveNewMessage(false);
        }
        ((T6HomePresenter) this.mPresenter).onResume(this.t6LiveVideoView.getVideoPlayerView());
        if (this.serviceMaybeChanged) {
            ((T6HomePresenter) this.mPresenter).initT6DeviceDetail(false);
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        ((T6HomePresenter) this.mPresenter).onPause();
        if (this.t6Record != null && CommonUtil.getLong(r0.getFirmware()) < 600.0d && !this.clickIntoFullscreen) {
            ((T6HomePresenter) this.mPresenter).stopLiveVideo();
        }
        this.isActivityInBackgrouond = true;
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T6HomeContract.View
    public void passRelatedProductsInfor(RelatedProductsInfor relatedProductsInfor) {
        this.relatedProductsInfor = relatedProductsInfor;
        ArrayList arrayList = new ArrayList();
        if (relatedProductsInfor != null && relatedProductsInfor.getPromote() != null) {
            arrayList.addAll(relatedProductsInfor.getPromote());
        }
        if (arrayList.size() == 0) {
            this.promoteView.setVisibility(8);
            return;
        }
        this.promoteView.enableSwitchable("T6");
        this.promoteView.refreshPromoteData(arrayList);
        this.promoteView.setPromoteViewOnItemListener(new PromoteView.PromoteViewOnItemListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.19
            public AnonymousClass19() {
            }

            @Override // com.petkit.android.activities.mall.widget.PromoteView.PromoteViewOnItemListener
            public void onItemClick(PromoteData promoteData) {
                new HashMap().put("contentId", "" + promoteData.getContentId());
                MallUtils.goToWebOrProductDetail(T6HomeActivity.this, promoteData.getShareUrl(), promoteData.getUrlType());
            }
        });
        this.promoteView.setVisibility(0);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$19 */
    public class AnonymousClass19 implements PromoteView.PromoteViewOnItemListener {
        public AnonymousClass19() {
        }

        @Override // com.petkit.android.activities.mall.widget.PromoteView.PromoteViewOnItemListener
        public void onItemClick(PromoteData promoteData) {
            new HashMap().put("contentId", "" + promoteData.getContentId());
            MallUtils.goToWebOrProductDetail(T6HomeActivity.this, promoteData.getShareUrl(), promoteData.getUrlType());
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T6HomeContract.View
    public void dragOutSide(String str) {
        if (str == null || str.equals("") || str.equals("-1")) {
            this.handler.sendEmptyMessage(16);
            return;
        }
        switch (str) {
            case "2":
                MyHandler myHandler = this.handler;
                if (myHandler != null) {
                    myHandler.sendEmptyMessage(9);
                    break;
                }
                break;
            case "3":
                MyHandler myHandler2 = this.handler;
                if (myHandler2 != null) {
                    myHandler2.sendEmptyMessage(11);
                    break;
                }
                break;
            case "4":
                MyHandler myHandler3 = this.handler;
                if (myHandler3 != null) {
                    myHandler3.sendEmptyMessage(15);
                    break;
                }
                break;
            case "5":
                MyHandler myHandler4 = this.handler;
                if (myHandler4 != null) {
                    myHandler4.sendEmptyMessage(16);
                    break;
                }
                break;
            case "6":
                MyHandler myHandler5 = this.handler;
                if (myHandler5 != null) {
                    myHandler5.sendEmptyMessage(18);
                    break;
                }
                break;
            case "7":
                MyHandler myHandler6 = this.handler;
                if (myHandler6 != null) {
                    myHandler6.sendEmptyMessage(23);
                    break;
                }
                break;
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T6HomeContract.View
    public void reversal(String str) {
        MyHandler myHandler;
        if (str == null || str.equals("") || str.equals("-1")) {
            this.handler.sendEmptyMessage(16);
            return;
        }
        if (str.equals("1")) {
            MyHandler myHandler2 = this.handler;
            if (myHandler2 != null) {
                myHandler2.sendEmptyMessage(18);
                return;
            }
            return;
        }
        if (str.equals("2") && (myHandler = this.handler) != null) {
            myHandler.sendEmptyMessage(16);
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T6HomeContract.View
    public void dragCameraError(int i) {
        if (i == 2) {
            hideFlipCameraAnim();
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (jCurrentTimeMillis - this.showErrorTime < 2000) {
            return;
        }
        this.showErrorTime = jCurrentTimeMillis;
        ToastUtil.show(this, getResources().getString(R.string.T6_Rtm_Unconnected));
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T6HomeContract.View
    public void onFirstRemoteVideoFrame() {
        PetkitLog.d("onFirstRemoteVideoFrame");
        this.firstRemoteVideoFrame = true;
        runOnUiThread(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.20
            public AnonymousClass20() {
            }

            @Override // java.lang.Runnable
            public void run() {
                if (T6HomeActivity.this.t6Record.getSettings().getCamera() == 0 || T6HomeActivity.this.t6Record.getState().getCameraStatus() == 0) {
                    T6HomeActivity.this.videoTagLayout.setVisibility(8);
                } else {
                    T6HomeActivity.this.videoTagLayout.setVisibility(0);
                }
                if (T6HomeActivity.this.t6LiveViewPagerAdapter != null) {
                    T6HomeActivity.this.t6LiveViewPagerAdapter.setLive(true);
                    ViewPager viewPager = T6HomeActivity.this.vpLive;
                    if (viewPager != null && viewPager.getCurrentItem() == 1) {
                        T6HomeActivity.this.t6LiveViewPagerAdapter.setCurrentPosition(T6HomeActivity.this.vpLive.getCurrentItem());
                        T6HomeActivity.this.t6LiveViewPagerAdapter.refreshData();
                    }
                }
                if (T6HomeActivity.this.handler != null) {
                    T6HomeActivity.this.endLoadingAnim();
                }
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$20 */
    public class AnonymousClass20 implements Runnable {
        public AnonymousClass20() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (T6HomeActivity.this.t6Record.getSettings().getCamera() == 0 || T6HomeActivity.this.t6Record.getState().getCameraStatus() == 0) {
                T6HomeActivity.this.videoTagLayout.setVisibility(8);
            } else {
                T6HomeActivity.this.videoTagLayout.setVisibility(0);
            }
            if (T6HomeActivity.this.t6LiveViewPagerAdapter != null) {
                T6HomeActivity.this.t6LiveViewPagerAdapter.setLive(true);
                ViewPager viewPager = T6HomeActivity.this.vpLive;
                if (viewPager != null && viewPager.getCurrentItem() == 1) {
                    T6HomeActivity.this.t6LiveViewPagerAdapter.setCurrentPosition(T6HomeActivity.this.vpLive.getCurrentItem());
                    T6HomeActivity.this.t6LiveViewPagerAdapter.refreshData();
                }
            }
            if (T6HomeActivity.this.handler != null) {
                T6HomeActivity.this.endLoadingAnim();
            }
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T6HomeContract.View
    public void updateEvent(int i) {
        if (i == 31 || i == 32 || i == 33 || i == 35 || i == 38) {
            if (DateUtil.isSameDay(this.calendarTime, this.t6Record.getActualTimeZone())) {
                int i2 = this.currentPosition;
                if (i2 == 0) {
                    if (this.rlTopTab.getVisibility() != 0) {
                        if (this.allFragment.isAdded()) {
                            this.allFragment.refreshEvent(this.calendarTime);
                        }
                    } else {
                        this.ivAll.setVisibility(0);
                        this.ivAll2.setVisibility(0);
                    }
                } else if (i2 == 1) {
                    this.ivAll.setVisibility(0);
                    this.ivAll2.setVisibility(0);
                    if ((i == 31 || i == 32) && this.rlTopTab.getVisibility() != 0 && this.toiletFragment.isAdded()) {
                        this.toiletFragment.refreshEvent(this.calendarTime);
                    }
                } else if (i2 == 2) {
                    this.ivAll.setVisibility(0);
                    this.ivAll2.setVisibility(0);
                    if ((i == 33 || i == 35) && this.rlTopTab.getVisibility() != 0 && this.petFragment.isAdded()) {
                        this.petFragment.refreshEvent(this.calendarTime);
                    }
                } else if (i2 == 3) {
                    this.ivAll.setVisibility(0);
                    this.ivAll2.setVisibility(0);
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

    public void hideAllRedPoint() {
        this.ivAll.setVisibility(8);
        this.ivAll2.setVisibility(8);
    }

    private void showTopPaoPao() {
        this.tvTopPaoPao.setVisibility(0);
        Disposable disposable = this.paoPaoTimer;
        if (disposable != null) {
            disposable.dispose();
            this.paoPaoTimer = null;
        }
        this.paoPaoTimer = Observable.timer(3L, TimeUnit.SECONDS).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$$ExternalSyntheticLambda7
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.lambda$showTopPaoPao$17((Long) obj);
            }
        });
    }

    public /* synthetic */ void lambda$showTopPaoPao$17(Long l) throws Exception {
        MyHandler myHandler = this.handler;
        if (myHandler != null) {
            myHandler.sendEmptyMessage(10);
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        P p;
        if (view.getId() == R.id.device_work_state_text) {
            return;
        }
        if (view.getId() == R.id.tv_pet_weight) {
            startActivity(WeightStatisticsActivity.newIntent(this, this.selectedPetIds.get(0)));
            return;
        }
        if (view.getId() == R.id.btn_know_more_two) {
            launchActivity(T6ConsumablesActivity.newIntent(this, this.deviceId, 27, 3, this.relatedProductsInfor));
            return;
        }
        if (view.getId() == R.id.btn_ignore_two) {
            ((T6HomePresenter) this.mPresenter).ignoreRemainingBags();
            refreshDeviceStateView();
            return;
        }
        if (view.getId() == R.id.rl_device_error_two) {
            List<DeviceError> listCalcDeviceError = ((T6HomePresenter) this.mPresenter).calcDeviceError();
            if (listCalcDeviceError == null || listCalcDeviceError.size() <= 1) {
                return;
            }
            launchActivity(T6WarnsListActivity.newIntent(this, this.deviceId, this.deviceType, (ArrayList) listCalcDeviceError, this.relatedProductsInfor));
            return;
        }
        if (view.getId() == R.id.rl_device_error_one) {
            List<DeviceError> listCalcDeviceError2 = ((T6HomePresenter) this.mPresenter).calcDeviceError();
            if (listCalcDeviceError2 == null || listCalcDeviceError2.size() <= 0) {
                return;
            }
            showErrorWindow(listCalcDeviceError2.get(0));
            return;
        }
        if (view.getId() == R.id.btn_stop) {
            ((T6HomePresenter) this.mPresenter).terminateClean(this.deviceId);
            return;
        }
        if (view.getId() == R.id.btn_continue) {
            ((T6HomePresenter) this.mPresenter).continueToClean(this.deviceId);
            return;
        }
        if (view.getId() == R.id.btn_pause) {
            ((T6HomePresenter) this.mPresenter).pauseClean(this.deviceId);
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
            ((T6HomePresenter) this.mPresenter).getCalendarData(this.calendarTime);
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
        if (view.getId() == R.id.tv_tab_all) {
            clickAllTab(false);
            return;
        }
        if (view.getId() == R.id.tv_tab_all2) {
            clickAllTab(true);
            return;
        }
        if (view.getId() == R.id.tv_tab_toilet) {
            clickToiletTab(false);
            return;
        }
        if (view.getId() == R.id.tv_tab_toilet2) {
            clickToiletTab(true);
            return;
        }
        if (view.getId() == R.id.tv_tab_other) {
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
        if (view.getId() == R.id.rl_purchase_eligibility) {
            showPurchaseEligibility();
            return;
        }
        if (view.getId() == R.id.ll_pets) {
            T6Record t6Record = this.t6Record;
            if (t6Record == null || t6Record.getDeviceShared() == null) {
                showSelectPetsDialog();
                return;
            }
            return;
        }
        if (view.getId() == R.id.rl_device_state) {
            if (this.t6Record.getState().getOverall() == 2) {
                showOfflineWindow();
                return;
            } else {
                if (this.t6Record.getState().getOta() == 1) {
                    launchActivity(T6SettingOtaActivity.newIntent(this, this.deviceId, this.deviceType));
                    return;
                }
                return;
            }
        }
        if (view.getId() == R.id.rl_work_state) {
            if (this.t6Record.getState().getWorkState() != null) {
                if (1 == this.t6Record.getState().getWorkState().getWorkProcess() / 10) {
                    int workMode = this.t6Record.getState().getWorkState().getWorkMode();
                    if (workMode == 8 || workMode == 9) {
                        launchActivity(T6PackActivity.newIntent(this, this.deviceId));
                        return;
                    } else {
                        if (workMode == 1) {
                            jumpToSandDumping();
                            return;
                        }
                        return;
                    }
                }
                if (2 == this.t6Record.getState().getWorkState().getWorkProcess() / 10) {
                    if (this.t6Record.getState().getWorkState().getWorkMode() == 1) {
                        jumpToSandDumping();
                        return;
                    } else {
                        if (this.t6Record.getState().getWorkState().getWorkMode() != 8 || 0 == this.t6Record.getState().getPetInTime()) {
                            return;
                        }
                        launchActivity(T6PackActivity.newIntent(this, this.deviceId));
                        return;
                    }
                }
                if (3 == this.t6Record.getState().getWorkState().getWorkProcess() / 10) {
                    if (this.t6Record.getState().getWorkState().getWorkMode() == 1) {
                        jumpToSandDumping();
                        return;
                    }
                    return;
                } else {
                    if (4 == this.t6Record.getState().getWorkState().getWorkProcess() / 10 && this.t6Record.getState().getWorkState().getWorkMode() == 1) {
                        jumpToSandDumping();
                        return;
                    }
                    return;
                }
            }
            return;
        }
        if (view.getId() == R.id.tv_top_pao_pao) {
            scrollToTop();
            if (DateUtil.isSameDay(this.calendarTime, this.t6Record.getActualTimeZone())) {
                refreshHeader();
                return;
            }
            return;
        }
        if (view.getId() == R.id.iv_tip_close) {
            RelativeLayout relativeLayout = this.rlCameraTip;
            if (relativeLayout != null) {
                relativeLayout.setVisibility(8);
                DataHelper.setBooleanSF(this, Constants.T6_CAMERA_BACK_TIP + UserInforUtils.getCurrentUserId(this) + this.deviceId, Boolean.TRUE);
                return;
            }
            return;
        }
        if (view.getId() == R.id.iv_highlights_close) {
            RelativeLayout relativeLayout2 = this.rlHighlightsTip;
            if (relativeLayout2 != null) {
                relativeLayout2.setVisibility(8);
            }
            DataHelper.setBooleanSF(this, Constants.T6_HIGHLIGHT_TIP, Boolean.TRUE);
            return;
        }
        if (view.getId() != R.id.tv_highlights_open || (p = this.mPresenter) == 0) {
            return;
        }
        ((T6HomePresenter) p).openHighLight("highlight", true);
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T6HomeContract.View
    public void showAutoWeight(String str, double d, double d2, double d3) {
        showAutoUpdatePetWeightWindow(str, d, d2, d3);
    }

    private void clickAllTab(boolean z) {
        if (DateUtil.isSameDay(this.calendarTime, this.t6Record.getActualTimeZone())) {
            if (this.ivAll.getVisibility() == 0 || this.ivAll2.getVisibility() == 0) {
                int i = this.newToiletEventNum;
                if ((i > 0 || this.newClearEventNum > 0) && this.newPetEventNum > 0) {
                    P p = this.mPresenter;
                    if (p != 0) {
                        ((T6HomePresenter) p).getStatistic(this.calendarTime, 0);
                    }
                } else if (i > 0 || this.newClearEventNum > 0) {
                    if (i > 0) {
                        P p2 = this.mPresenter;
                        if (p2 != 0) {
                            ((T6HomePresenter) p2).getStatistic(this.calendarTime, 1);
                        }
                    } else {
                        if (this.allFragment.isAdded()) {
                            this.allFragment.refreshEvent(this.calendarTime);
                        }
                        if (this.toiletFragment.isAdded()) {
                            this.toiletFragment.refreshEvent(this.calendarTime);
                        }
                        P p3 = this.mPresenter;
                        if (p3 != 0) {
                            ((T6HomePresenter) p3).getListNum(this.calendarTime);
                        }
                    }
                }
                if (this.currentPosition != 0) {
                    if (!z && this.allFragment.isAdded() && this.rlTopTab.getVisibility() != 0) {
                        this.allFragment.scrollToTop();
                    }
                    selectedFragment(0);
                }
                hideAllRedPoint();
                return;
            }
            if (this.currentPosition == 0) {
                return;
            }
            if (!z && this.allFragment.isAdded() && this.rlTopTab.getVisibility() != 0) {
                this.allFragment.scrollToTop();
            }
            selectedFragment(0);
            return;
        }
        if (this.currentPosition == 0) {
            return;
        }
        if (!z && this.allFragment.isAdded() && this.rlTopTab.getVisibility() != 0) {
            this.allFragment.scrollToTop();
        }
        selectedFragment(0);
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
        linearLayout.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$$ExternalSyntheticLambda13
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                T6HomeActivity.lambda$showAutoUpdatePetWeightWindow$18(checkBox, view2);
            }
        });
        ((BaseApplication) getApplication()).getAppComponent().imageLoader().loadImage(this, GlideImageConfig.builder().url(petById.getAvatar()).imageView(imageView).errorPic(petById.getType().getId() == 1 ? R.drawable.default_header_dog : R.drawable.default_header_cat).transformation(new GlideCircleTransform(this)).build());
        final EasyPopupWindow easyPopupWindow = new EasyPopupWindow(this);
        easyPopupWindow.setOutsideTouchable(false);
        easyPopupWindow.setmShowAlpha(0.5f);
        easyPopupWindow.setContentView(view);
        easyPopupWindow.setHeight(-1);
        easyPopupWindow.setWidth(BaseApplication.displayMetrics.widthPixels);
        button.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$$ExternalSyntheticLambda14
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.lambda$showAutoUpdatePetWeightWindow$19(checkBox, easyPopupWindow, view2);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$$ExternalSyntheticLambda15
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.lambda$showAutoUpdatePetWeightWindow$20(checkBox, str, easyPopupWindow, view2);
            }
        });
        easyPopupWindow.showAtLocation(getWindow().getDecorView(), 17, 0, 0);
    }

    public static /* synthetic */ void lambda$showAutoUpdatePetWeightWindow$18(CheckBox checkBox, View view) {
        checkBox.setChecked(!checkBox.isChecked());
    }

    public /* synthetic */ void lambda$showAutoUpdatePetWeightWindow$19(CheckBox checkBox, EasyPopupWindow easyPopupWindow, View view) {
        if (checkBox.isChecked()) {
            ((T6HomePresenter) this.mPresenter).updateDeviceSettings("weightPopup", false);
        }
        easyPopupWindow.dismiss();
    }

    public /* synthetic */ void lambda$showAutoUpdatePetWeightWindow$20(CheckBox checkBox, String str, EasyPopupWindow easyPopupWindow, View view) {
        if (checkBox.isChecked()) {
            ((T6HomePresenter) this.mPresenter).updateDeviceSettings("weightPopup", false);
        }
        launchActivity(WeightRecordActivity.newIntent(this, str));
        easyPopupWindow.dismiss();
    }

    private void clickToiletTab(boolean z) {
        if (DateUtil.isSameDay(this.calendarTime, this.t6Record.getActualTimeZone())) {
            int i = this.newToiletEventNum;
            if (i > 0 || this.newClearEventNum > 0) {
                if (i > 0) {
                    P p = this.mPresenter;
                    if (p != 0) {
                        ((T6HomePresenter) p).getStatistic(this.calendarTime, 2);
                    }
                } else {
                    if (this.toiletFragment.isAdded()) {
                        this.toiletFragment.refreshEvent(this.calendarTime);
                    }
                    P p2 = this.mPresenter;
                    if (p2 != 0) {
                        ((T6HomePresenter) p2).getListNum(this.calendarTime);
                    }
                }
                if (this.currentPosition != 1) {
                    if (!z && this.toiletFragment.isAdded() && this.rlTopTab.getVisibility() != 0) {
                        this.toiletFragment.scrollToTop();
                    }
                    selectedFragment(1);
                    return;
                }
                return;
            }
            if (this.currentPosition == 1) {
                return;
            }
            if (!z && this.toiletFragment.isAdded() && this.rlTopTab.getVisibility() != 0) {
                this.toiletFragment.scrollToTop();
            }
            selectedFragment(1);
            return;
        }
        if (this.currentPosition == 1) {
            return;
        }
        if (!z && this.toiletFragment.isAdded() && this.rlTopTab.getVisibility() != 0) {
            this.toiletFragment.scrollToTop();
        }
        selectedFragment(1);
    }

    private void clickPetTab(boolean z) {
        if (DateUtil.isSameDay(this.calendarTime, this.t6Record.getActualTimeZone())) {
            if (this.newPetEventNum > 0) {
                if (this.petFragment.isAdded()) {
                    this.petFragment.refreshEvent(this.calendarTime);
                }
                P p = this.mPresenter;
                if (p != 0) {
                    ((T6HomePresenter) p).getListNum(this.calendarTime);
                }
                if (this.currentPosition != 2) {
                    if (!z && this.petFragment.isAdded() && this.rlTopTab.getVisibility() != 0) {
                        this.petFragment.scrollToTop();
                    }
                    selectedFragment(2);
                    return;
                }
                return;
            }
            if (this.currentPosition == 2) {
                return;
            }
            if (!z && this.petFragment.isAdded() && this.rlTopTab.getVisibility() != 0) {
                this.petFragment.scrollToTop();
            }
            selectedFragment(2);
            return;
        }
        if (this.currentPosition == 2) {
            return;
        }
        if (!z && this.petFragment.isAdded() && this.rlTopTab.getVisibility() != 0) {
            this.petFragment.scrollToTop();
        }
        selectedFragment(2);
    }

    private void clickToiletErrorTab(boolean z) {
        if (DateUtil.isSameDay(this.calendarTime, this.t6Record.getActualTimeZone())) {
            showRedToilet(false);
            if (this.newToiletErrorEventNum > 0) {
                P p = this.mPresenter;
                if (p != 0) {
                    ((T6HomePresenter) p).getStatistic(this.calendarTime, 3);
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
        T6EventListFragment t6EventListFragment = this.allFragment;
        if (t6EventListFragment != null) {
            t6EventListFragment.scrollToTop();
        }
        T6EventListFragment t6EventListFragment2 = this.toiletFragment;
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
        T6BottomView t6BottomView;
        if (str == null) {
            str = "";
        }
        switch (str) {
            case "camera":
            case "moto_P":
                if (hasUnDisableError() && (t6BottomView = this.t6BottomView) != null) {
                    t6BottomView.unDisableAll();
                    break;
                }
                break;
            case "moto_R":
                T6BottomView t6BottomView2 = this.t6BottomView;
                if (t6BottomView2 != null) {
                    t6BottomView2.disableClean();
                    this.t6BottomView.disableDump();
                    this.t6BottomView.disableRecover();
                    this.t6BottomView.disableSmooth();
                    this.t6BottomView.disableDoor();
                    break;
                }
                break;
            case "scaleD":
            case "NFC":
            case "PROX":
            case "radar":
            case "scale":
                T6BottomView t6BottomView3 = this.t6BottomView;
                if (t6BottomView3 != null) {
                    t6BottomView3.disableClean();
                    this.t6BottomView.disablePacked();
                    this.t6BottomView.disableDump();
                    this.t6BottomView.disableRecover();
                    this.t6BottomView.disableSmooth();
                    this.t6BottomView.disableDoor();
                    break;
                }
                break;
            case "packModule_C":
            case "packModule_O":
                T6BottomView t6BottomView4 = this.t6BottomView;
                if (t6BottomView4 != null) {
                    t6BottomView4.disableClean();
                    this.t6BottomView.disablePacked();
                    this.t6BottomView.disableSmooth();
                    this.t6BottomView.disableDump();
                    this.t6BottomView.disableRecover();
                    break;
                }
                break;
            default:
                T6BottomView t6BottomView5 = this.t6BottomView;
                if (t6BottomView5 != null) {
                    t6BottomView5.disableAll();
                    break;
                }
                break;
        }
    }

    private boolean hasUnDisableError() {
        List<DeviceError> listCalcDeviceError = ((T6HomePresenter) this.mPresenter).calcDeviceError();
        if (listCalcDeviceError.size() == 1) {
            String errorType = listCalcDeviceError.get(0).getErrorType();
            return errorType.equals(Constants.T6_ERROR_CODE_MOTO_P) || errorType.equals("camera");
        }
        if (listCalcDeviceError.size() != 2) {
            return false;
        }
        String errorType2 = listCalcDeviceError.get(0).getErrorType();
        String errorType3 = listCalcDeviceError.get(1).getErrorType();
        return (errorType2.equals(Constants.T6_ERROR_CODE_MOTO_P) || errorType2.equals("camera")) && (errorType3.equals(Constants.T6_ERROR_CODE_MOTO_P) || errorType3.equals("camera"));
    }

    private void setBottomViewInWork() {
        T6Record t6Record = this.t6Record;
        if (t6Record == null || t6Record.getState() == null) {
            return;
        }
        if (0 != this.t6Record.getState().getWanderTime() || 0 != this.t6Record.getState().getPetInTime()) {
            T6BottomView t6BottomView = this.t6BottomView;
            if (t6BottomView != null) {
                t6BottomView.disableClean();
                this.t6BottomView.disablePacked();
                this.t6BottomView.disableDump();
                this.t6BottomView.disableRecover();
                this.t6BottomView.disableSmooth();
                this.t6BottomView.disableDoor();
            }
            return;
        }
        String errorCode = getErrorCode();
        errorCode.hashCode();
        switch (errorCode) {
            case "box_full":
                break;
            case "t6_error_roll_weight":
            case "trunk_F":
                T6BottomView t6BottomView2 = this.t6BottomView;
                if (t6BottomView2 != null) {
                    t6BottomView2.disableClean();
                    this.t6BottomView.disablePacked();
                    this.t6BottomView.disableDump();
                    this.t6BottomView.disableRecover();
                    this.t6BottomView.disableSmooth();
                    this.t6BottomView.disableDoor();
                    break;
                }
                break;
            case "box_state_un_bagging":
            case "box_state_destroy":
            case "hallP":
            case "hallT":
            case "package_U":
            case "packbox_E":
            case "packbox_I":
            case "packbox_U":
                T6BottomView t6BottomView3 = this.t6BottomView;
                if (t6BottomView3 != null) {
                    t6BottomView3.disableClean();
                    this.t6BottomView.disablePacked();
                    this.t6BottomView.disableDump();
                    this.t6BottomView.disableRecover();
                    this.t6BottomView.disableSmooth();
                    break;
                }
                break;
            case "packERR":
            case "pack_box_u":
                T6BottomView t6BottomView4 = this.t6BottomView;
                if (t6BottomView4 != null) {
                    t6BottomView4.disableClean();
                    this.t6BottomView.disableDump();
                    this.t6BottomView.disableRecover();
                    this.t6BottomView.disableSmooth();
                    break;
                }
                break;
            default:
                T6BottomView t6BottomView5 = this.t6BottomView;
                if (t6BottomView5 != null) {
                    t6BottomView5.unDisableAll();
                    break;
                }
                break;
        }
    }

    private String getErrorCode() {
        if (this.t6Record.getState().getTopIns() == 1) {
            return "hallT";
        }
        if (this.t6Record.getState().getTrunkState() == 1) {
            return Constants.T6_ERROR_CODE_TRUNK_F;
        }
        if (this.t6Record.getState().getWeightState() == 1) {
            return Constants.T6_ERROR_ROLL_WEIGHT;
        }
        if (this.t6Record.getState().getPackageState() != null && this.t6Record.getState().getPackageState().intValue() == 5) {
            return Constants.T6_ERROR_CODE_PACKBOX_U;
        }
        if (this.t6Record.getState().getPackageState() == null || this.t6Record.getState().getPackageState().intValue() != 4) {
            if (this.t6Record.getState().getPiIns() == 1) {
                return "hallP";
            }
            if (this.t6Record.getState().getPackageState() != null && this.t6Record.getState().getPackageState().intValue() == 2) {
                return Constants.T6_ERROR_CODE_PACKBOX_E;
            }
            if (this.t6Record.getState().getPackageState() == null || this.t6Record.getState().getPackageState().intValue() != 3) {
                if (this.t6Record.getState().getBaggingState() != null && this.t6Record.getState().getBaggingState().intValue() == 0) {
                    return Constants.T6_ERROR_CODE_PACKAGE_U;
                }
                if (this.t6Record.getState().getPackState() != null && this.t6Record.getState().getPackState().intValue() == 0) {
                    return Constants.T6_ERROR_CODE_PACK_ERR;
                }
                if (this.t6Record.getState().getPackState() != null && this.t6Record.getState().getBoxStoreState() == 1) {
                    return Constants.T6_ERROR_CODE_BOX_DESTROY;
                }
                if (this.t6Record.getState().getPackState() != null && this.t6Record.getState().getBoxStoreState() == 2) {
                    return Constants.T6_ERROR_CODE_BOX_UN_BAGGING;
                }
                if (this.t6Record.getState().getBaggingState() != null && this.t6Record.getState().getBaggingState().intValue() == -1 && this.t6Record.getState().getPackState() != null && this.t6Record.getState().getPackState().intValue() == 1 && this.t6Record.getState().getBoxStoreState() != 1 && this.t6Record.getState().getBoxStoreState() != 2) {
                    return Constants.T6_ERROR_CODE_PACK_BOX_U;
                }
                if (this.t6Record.getState().isBoxFull()) {
                    return Constants.T6_ERROR_CODE_BOX_FULL;
                }
                if (this.t6Record.getState().isSandLack()) {
                    return Constants.T6_ERROR_CODE_SAND_LACK;
                }
                if (this.t6Record.getState().getPackageState() != null && this.t6Record.getState().getPackageState().intValue() == 1 && this.t6Record.getPackageIgnoreState() == 0) {
                    return Constants.T6_ERROR_CODE_PACKBOX;
                }
                return "";
            }
        }
        return Constants.T6_ERROR_CODE_PACKBOX_I;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:353:0x020c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void showErrorWindow(com.petkit.android.activities.petkitBleDevice.t6.mode.DeviceError r14) {
        /*
            Method dump skipped, instruction units count: 2432
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.showErrorWindow(com.petkit.android.activities.petkitBleDevice.t6.mode.DeviceError):void");
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$21 */
    public class AnonymousClass21 implements DeviceErrorWarnWindow.OnClickListener {
        @Override // com.petkit.android.activities.petkitBleDevice.widget.DeviceErrorWarnWindow.OnClickListener
        public /* synthetic */ void onClickText() {
            DeviceErrorWarnWindow.OnClickListener.CC.$default$onClickText(this);
        }

        public AnonymousClass21() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.widget.DeviceErrorWarnWindow.OnClickListener
        public void onClickLearnMore() {
            T6HomeActivity t6HomeActivity = T6HomeActivity.this;
            t6HomeActivity.launchActivity(T6LearnMoreActivity.newIntent(t6HomeActivity, ApiTools.getWebUrlByKey("t6_autopack_err"), T6HomeActivity.this.getResources().getString(R.string.Automatic_packaging_mode)));
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$22 */
    public class AnonymousClass22 implements DeviceErrorWarnWindow.OnClickListener {
        @Override // com.petkit.android.activities.petkitBleDevice.widget.DeviceErrorWarnWindow.OnClickListener
        public /* synthetic */ void onClickText() {
            DeviceErrorWarnWindow.OnClickListener.CC.$default$onClickText(this);
        }

        public AnonymousClass22() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.widget.DeviceErrorWarnWindow.OnClickListener
        public void onClickLearnMore() {
            T6HomeActivity t6HomeActivity = T6HomeActivity.this;
            t6HomeActivity.launchActivity(T6LearnMoreActivity.newIntent(t6HomeActivity, ApiTools.getWebUrlByKey("t6_roller_err"), T6HomeActivity.this.getResources().getString(R.string.H5_navTitle_roller_err)));
        }
    }

    public /* synthetic */ void lambda$showErrorWindow$21() {
        launchActivity(T6LearnMoreActivity.newIntent(this, ApiTools.getWebUrlByKey("clean_waste_bin"), getResources().getString(R.string.H5_navTitle_clean_waste_bin)));
    }

    public /* synthetic */ void lambda$showErrorWindow$22() {
        P p = this.mPresenter;
        if (p != 0) {
            ((T6HomePresenter) p).startBagging(this.deviceId);
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$23 */
    public class AnonymousClass23 implements DeviceErrorWarnWindow.OnClickListener {
        @Override // com.petkit.android.activities.petkitBleDevice.widget.DeviceErrorWarnWindow.OnClickListener
        public /* synthetic */ void onClickText() {
            DeviceErrorWarnWindow.OnClickListener.CC.$default$onClickText(this);
        }

        public AnonymousClass23() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.widget.DeviceErrorWarnWindow.OnClickListener
        public void onClickLearnMore() {
            T6HomeActivity t6HomeActivity = T6HomeActivity.this;
            t6HomeActivity.launchActivity(T6LearnMoreActivity.newIntent(t6HomeActivity, ApiTools.getWebUrlByKey("t6_piIns_uninstall"), T6HomeActivity.this.getResources().getString(R.string.Main_engine_tail_box)));
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$24 */
    public class AnonymousClass24 implements DeviceErrorWarnWindow.OnClickListener {
        @Override // com.petkit.android.activities.petkitBleDevice.widget.DeviceErrorWarnWindow.OnClickListener
        public /* synthetic */ void onClickText() {
            DeviceErrorWarnWindow.OnClickListener.CC.$default$onClickText(this);
        }

        public AnonymousClass24() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.widget.DeviceErrorWarnWindow.OnClickListener
        public void onClickLearnMore() {
            T6HomeActivity t6HomeActivity = T6HomeActivity.this;
            t6HomeActivity.launchActivity(T6LearnMoreActivity.newIntent(t6HomeActivity, ApiTools.getWebUrlByKey("t6_rubbish_in_trunk"), T6HomeActivity.this.getResources().getString(R.string.T6_have_foreign_body_title)));
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$25 */
    public class AnonymousClass25 implements DeviceErrorWarnWindow.OnClickListener {
        @Override // com.petkit.android.activities.petkitBleDevice.widget.DeviceErrorWarnWindow.OnClickListener
        public /* synthetic */ void onClickText() {
            DeviceErrorWarnWindow.OnClickListener.CC.$default$onClickText(this);
        }

        public AnonymousClass25() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.widget.DeviceErrorWarnWindow.OnClickListener
        public void onClickLearnMore() {
            if (TextUtils.isEmpty(T6HomeActivity.this.relatedProductsInfor.getStandard().getT6().getGarbageBagBox().getShareUrl())) {
                return;
            }
            T6HomeActivity t6HomeActivity = T6HomeActivity.this;
            MallUtils.goToWebOrProductDetail(t6HomeActivity, t6HomeActivity.relatedProductsInfor.getStandard().getT6().getGarbageBagBox().getShareUrl(), T6HomeActivity.this.relatedProductsInfor.getStandard().getT6().getGarbageBagBox().getUrlType());
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$26 */
    public class AnonymousClass26 implements DeviceErrorWarnWindow.OnClickListener {
        @Override // com.petkit.android.activities.petkitBleDevice.widget.DeviceErrorWarnWindow.OnClickListener
        public /* synthetic */ void onClickText() {
            DeviceErrorWarnWindow.OnClickListener.CC.$default$onClickText(this);
        }

        public AnonymousClass26() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.widget.DeviceErrorWarnWindow.OnClickListener
        public void onClickLearnMore() {
            T6HomeActivity t6HomeActivity = T6HomeActivity.this;
            t6HomeActivity.launchActivity(T6LearnMoreActivity.newIntent(t6HomeActivity, ApiTools.getWebUrlByKey("t6_topIns_uninstall"), T6HomeActivity.this.getResources().getString(R.string.H5_navTitle_topIns_uninstall)));
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$27 */
    public class AnonymousClass27 implements DeviceErrorWarnWindow.OnClickListener {
        @Override // com.petkit.android.activities.petkitBleDevice.widget.DeviceErrorWarnWindow.OnClickListener
        public /* synthetic */ void onClickText() {
            DeviceErrorWarnWindow.OnClickListener.CC.$default$onClickText(this);
        }

        public AnonymousClass27() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.widget.DeviceErrorWarnWindow.OnClickListener
        public void onClickLearnMore() {
            if (TextUtils.isEmpty(T6HomeActivity.this.relatedProductsInfor.getStandard().getT6().getGarbageBagBox().getShareUrl())) {
                return;
            }
            T6HomeActivity t6HomeActivity = T6HomeActivity.this;
            MallUtils.goToWebOrProductDetail(t6HomeActivity, t6HomeActivity.relatedProductsInfor.getStandard().getT6().getGarbageBagBox().getShareUrl(), T6HomeActivity.this.relatedProductsInfor.getStandard().getT6().getGarbageBagBox().getUrlType());
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$28 */
    public class AnonymousClass28 implements NormalCenterTipWindow.OnClickOk {
        public final /* synthetic */ long val$deviceId;

        public AnonymousClass28(long j) {
            j = j;
        }

        @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
        public void onOkClick() {
            T6HomeActivity t6HomeActivity = T6HomeActivity.this;
            t6HomeActivity.launchActivity(T6SmartSettingActivity.newIntent(t6HomeActivity, j, t6HomeActivity.deviceType));
        }

        @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
        public void onCancelClick() {
            CommonUtils.addSysBoolMap(T6HomeActivity.this, "LIGHT_ASSIST_IN_TAKING_PHOTO_WINDOW_WHILE_PH_OPEN" + T6HomeActivity.this.deviceType + j, true);
        }
    }

    private void showPhLightTipWindow(long j) {
        NormalCenterTipWindow normalCenterTipWindow = new NormalCenterTipWindow(this, new NormalCenterTipWindow.OnClickOk() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.28
            public final /* synthetic */ long val$deviceId;

            public AnonymousClass28(long j2) {
                j = j2;
            }

            @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
            public void onOkClick() {
                T6HomeActivity t6HomeActivity = T6HomeActivity.this;
                t6HomeActivity.launchActivity(T6SmartSettingActivity.newIntent(t6HomeActivity, j, t6HomeActivity.deviceType));
            }

            @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
            public void onCancelClick() {
                CommonUtils.addSysBoolMap(T6HomeActivity.this, "LIGHT_ASSIST_IN_TAKING_PHOTO_WINDOW_WHILE_PH_OPEN" + T6HomeActivity.this.deviceType + j, true);
            }
        }, "", getString(R.string.Prompt_light_assistant_ph_detection_title));
        normalCenterTipWindow.setSelectText(getResources().getString(R.string.Not_remind), getResources().getString(R.string.Wifi_set));
        normalCenterTipWindow.show(getWindow().getDecorView());
    }

    private void showWeakWifiWindow() {
        if (this.wifiWeakWindow == null) {
            this.wifiWeakWindow = new NewWifiWeakWindow(R.drawable.img_t6_wifi_weak, this);
        }
        if (this.t6Record.getState().getWifi().getRsq() <= -65 && this.t6Record.getState().getWifi().getRsq() > -75) {
            if (TimeUtils.getInstance().getCurrentSeconds() - CommonUtils.getSysLongMap(this, Consts.T6_WIFI_SIGINAL_WINDOW + this.t6Record.getDeviceId()) > 2592000) {
                this.wifiWeakWindow.setNoRemindVisibility(0);
                this.wifiWeakWindow.setNoRemindClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.29
                    public AnonymousClass29() {
                    }

                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        T6HomeActivity.this.wifiWeakWindow.dismiss();
                        CommonUtils.addSysLongMap(T6HomeActivity.this, Consts.T6_WIFI_SIGINAL_WINDOW + T6HomeActivity.this.t6Record.getDeviceId(), TimeUtils.getInstance().getCurrentSeconds());
                        T6HomeActivity t6HomeActivity = T6HomeActivity.this;
                        t6HomeActivity.setupView(t6HomeActivity.t6Record);
                    }
                });
                this.wifiWeakWindow.showAtLocation(getWindow().getDecorView(), 17, 0, 0);
                return;
            }
            return;
        }
        if (this.t6Record.getState().getWifi().getRsq() < -75) {
            this.wifiWeakWindow.setNoRemindVisibility(8);
            CommonUtils.addSysLongMap(this, Consts.T6_WIFI_SIGINAL_WINDOW + this.t6Record.getDeviceId(), 0L);
            this.wifiWeakWindow.showAtLocation(getWindow().getDecorView(), 17, 0, 0);
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$29 */
    public class AnonymousClass29 implements View.OnClickListener {
        public AnonymousClass29() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            T6HomeActivity.this.wifiWeakWindow.dismiss();
            CommonUtils.addSysLongMap(T6HomeActivity.this, Consts.T6_WIFI_SIGINAL_WINDOW + T6HomeActivity.this.t6Record.getDeviceId(), TimeUtils.getInstance().getCurrentSeconds());
            T6HomeActivity t6HomeActivity = T6HomeActivity.this;
            t6HomeActivity.setupView(t6HomeActivity.t6Record);
        }
    }

    public static Intent newIntent(Context context, long j, int i) {
        Intent intent = new Intent(context, (Class<?>) T6HomeActivity.class);
        intent.putExtra(Constants.EXTRA_DEVICE_ID, j);
        intent.putExtra(Constants.EXTRA_DEVICE_TYPE, i);
        return intent;
    }

    public static Intent newIntent(Context context, long j, int i, String str) {
        Intent intent = new Intent(context, (Class<?>) T6HomeActivity.class);
        intent.putExtra(Constants.EXTRA_DEVICE_ID, j);
        intent.putExtra(Constants.EXTRA_DEVICE_TYPE, i);
        intent.putExtra(Constants.EXTRA_EVENT_ID, str);
        return intent;
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$30 */
    public class AnonymousClass30 implements Consumer<Long> {
        public AnonymousClass30() {
        }

        @Override // io.reactivex.functions.Consumer
        public void accept(Long l) {
            if (T6HomeActivity.this.bleDeviceHomeOfflineWarnWindow == null || !T6HomeActivity.this.bleDeviceHomeOfflineWarnWindow.isShowing()) {
                T6HomeActivity t6HomeActivity = T6HomeActivity.this;
                T6HomeActivity t6HomeActivity2 = T6HomeActivity.this;
                t6HomeActivity.bleDeviceHomeOfflineWarnWindow = new BleDeviceHomeOfflineWarnWindow(t6HomeActivity2, 27, t6HomeActivity2.getResources().getString(R.string.K2_offline_title), R.drawable.t6_remind_offline_icon, T6HomeActivity.this);
                T6HomeActivity.this.bleDeviceHomeOfflineWarnWindow.showAtLocation(T6HomeActivity.this.getWindow().getDecorView(), 17, 0, 0);
            }
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T6HomeContract.View
    public void showOfflineWindow() {
        this.disposable = Observable.timer(500L, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.30
            public AnonymousClass30() {
            }

            @Override // io.reactivex.functions.Consumer
            public void accept(Long l) {
                if (T6HomeActivity.this.bleDeviceHomeOfflineWarnWindow == null || !T6HomeActivity.this.bleDeviceHomeOfflineWarnWindow.isShowing()) {
                    T6HomeActivity t6HomeActivity = T6HomeActivity.this;
                    T6HomeActivity t6HomeActivity2 = T6HomeActivity.this;
                    t6HomeActivity.bleDeviceHomeOfflineWarnWindow = new BleDeviceHomeOfflineWarnWindow(t6HomeActivity2, 27, t6HomeActivity2.getResources().getString(R.string.K2_offline_title), R.drawable.t6_remind_offline_icon, T6HomeActivity.this);
                    T6HomeActivity.this.bleDeviceHomeOfflineWarnWindow.showAtLocation(T6HomeActivity.this.getWindow().getDecorView(), 17, 0, 0);
                }
            }
        });
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T6HomeContract.View
    public void setupView(T6Record t6Record) {
        this.t6Record = t6Record;
        this.t6BottomView.showAiEnter(false);
        if (t6Record == null || this.isAnimationRunning) {
            return;
        }
        refreshPurchaseEligibility();
        String rotateAngle = t6Record.getState().getRotateAngle();
        this.currentRotate = rotateAngle;
        rotateAngle.hashCode();
        switch (rotateAngle) {
            case "RA090":
                this.ivPopImage.setRotation(90.0f);
                this.t6LiveVideoView.findViewById(R.id.video_player_view).setRotation(90.0f);
                break;
            case "RA180":
                this.ivPopImage.setRotation(180.0f);
                this.t6LiveVideoView.findViewById(R.id.video_player_view).setRotation(180.0f);
                break;
            case "RA270":
                this.ivPopImage.setRotation(-90.0f);
                this.t6LiveVideoView.findViewById(R.id.video_player_view).setRotation(-90.0f);
                break;
        }
        if (t6Record.getRealDeviceShared() != null) {
            this.ivTodayTimesPrompt.setVisibility(8);
            this.ivTodayAvgDurationPrompt.setVisibility(8);
        } else {
            this.ivTodayTimesPrompt.setVisibility(0);
            this.ivTodayAvgDurationPrompt.setVisibility(0);
        }
        boolean booleanSF = DataHelper.getBooleanSF(this, Constants.T6_HIGHLIGHT_TIP);
        if (t6Record.getSettings().getHighlight() == 1) {
            this.rlHighlightsTip.setVisibility(8);
            this.llHighlights.setVisibility(0);
            DataHelper.setBooleanSF(this, Constants.T6_HIGHLIGHT_TIP, Boolean.FALSE);
        } else {
            this.rlHighlightsTip.setVisibility(booleanSF ? 8 : 0);
            this.llHighlights.setVisibility(8);
        }
        if (this.t6LiveViewPagerAdapter != null && this.vpLive.getCurrentItem() == 1) {
            this.t6LiveViewPagerAdapter.updateData(this.t6Record);
            this.t6LiveViewPagerAdapter.setCurrentPosition(this.vpLive.getCurrentItem());
            this.t6LiveViewPagerAdapter.refreshData();
        } else {
            this.t6LiveViewPagerAdapter.updateData(this.t6Record);
        }
        if (this.isFirstInto) {
            this.handler.postDelayed(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.31
                public AnonymousClass31() {
                }

                @Override // java.lang.Runnable
                public void run() {
                    T6HomeActivity.this.vpLive.setCurrentItem(CommonUtils.getSysIntMap(Constants.T6_HOME_PAGE_SELECT + T6HomeActivity.this.deviceId));
                }
            }, 300L);
            this.isFirstInto = false;
        }
        this.ibSetting.setImageResource(DeviceCenterUtils.isT6NeedOtaById(t6Record.getDeviceId()) ? R.drawable.black_setting_with_notify_flag : R.drawable.black_setting_icon);
        setDeviceStatus(this.t6Record);
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
        this.t6LiveVideoView.setT6Record(t6Record);
        if (this.t6VlogRecordAdapter == null) {
            T6VlogRecordAdapter t6VlogRecordAdapter = new T6VlogRecordAdapter(this, t6Record, this.deviceType);
            this.t6VlogRecordAdapter = t6VlogRecordAdapter;
            t6VlogRecordAdapter.append((List) new ArrayList());
            this.rvHighlights.setAdapter(this.t6VlogRecordAdapter);
        }
        String name = t6Record.getName();
        this.deviceName = name;
        if (TextUtils.isEmpty(name)) {
            this.deviceName = getResources().getString(R.string.T6_name_default);
        }
        if (t6Record.getDeviceShared() != null) {
            this.deviceName = String.format("%s-%s", t6Record.getDeviceShared().getOwnerNick(), this.deviceName);
            this.t6BottomView.showCatBody(false);
        } else {
            this.t6BottomView.showCatBody(true);
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
        if (t6Record.getSettings().getHomeMode() == 1 && t6Record.getSettings().getNoSound() == 1) {
            this.ivMuteIcon.setImageResource(R.drawable.t6_home_mute_icon);
        } else {
            this.ivMuteIcon.setImageResource(T6Utils.getVolumeState() ? R.drawable.t6_home_mute_icon : R.drawable.t6_home_mute_off_icon);
        }
        this.tvTodayTimes.setText(T6Utils.getT6HomeTimesString(t6Record.getInTimes()));
        this.tvTodayAvgDuration.setText(T6Utils.getT6HomeString(t6Record.getTotalTime() / (t6Record.getInTimes() == 0 ? 1 : t6Record.getInTimes())));
        this.t6BottomView.setLightIcon(t6Record.getState().getLightState() == null);
        this.t6BottomView.setDoorIcon(t6Record.getState().getSealDoorState() == 1);
        this.rlWorkState.setVisibility(8);
        this.ivBackHomeMode.setVisibility(0);
        refreshDeviceStateView();
        if (t6Record.getState() != null && t6Record.getState().getOverall() == 2) {
            setFoxAnimation(3);
            this.llControl.setVisibility(8);
            this.ivMuteIcon.setVisibility(8);
            this.ivBackHomeMode.setVisibility(8);
            this.isBackHomeModeClickable = false;
            this.t6BottomView.disableAll();
            this.t6LiveVideoView.setVisibility(0);
            this.videoTagLayout.setVisibility(8);
            this.rlLivePanel.setBackgroundResource(R.drawable.t6_device_gray_bg);
            openCamera();
        } else if (t6Record.getState() != null && t6Record.getState().getOverall() == 3) {
            setFoxAnimation(3);
            this.t6BottomView.disableAll();
            this.ivBackHomeMode.setImageResource(R.drawable.device_home_back_home_mode_gray);
            this.isBackHomeModeClickable = false;
            this.t6LiveVideoView.setVisibility(0);
            this.videoTagLayout.setVisibility(8);
            this.rlLivePanel.setBackgroundResource(R.drawable.t6_device_bg);
            openCamera();
        } else if (t6Record.getState() != null && t6Record.getState().getPower() == 0) {
            setFoxAnimation(3);
            this.llControl.setVisibility(8);
            this.tvTurnOn.setVisibility(0);
            this.tvTurnOn.setText(R.string.Turn_on_toilet);
            this.ivMuteIcon.setVisibility(8);
            this.ivBackHomeMode.setVisibility(8);
            this.ivBackHomeMode.setImageResource(R.drawable.device_home_back_home_mode_gray);
            this.isBackHomeModeClickable = false;
            this.t6BottomView.disableAll();
            this.t6LiveVideoView.setVisibility(0);
            this.videoTagLayout.setVisibility(8);
            this.rlLivePanel.setBackgroundResource(R.drawable.t6_device_gray_bg);
            openCamera();
        } else if (t6Record.getState() != null && !TextUtils.isEmpty(t6Record.getState().getErrorCode())) {
            setFoxAnimation(3);
            this.t6LiveVideoView.setVisibility(0);
            this.ivBackHomeMode.setImageResource(R.drawable.device_home_back_home_mode_gray);
            this.isBackHomeModeClickable = false;
            setBottomViewInError(t6Record.getState().getErrorCode());
            refreshCameraStatus(t6Record);
            this.rlLivePanel.setBackgroundResource(R.drawable.t6_device_bg);
        } else if (t6Record.getState() != null && t6Record.getState().getWeightState() == 1) {
            setFoxAnimation(3);
            this.t6LiveVideoView.setVisibility(0);
            refreshCameraStatus(t6Record);
            this.rlLivePanel.setBackgroundResource(R.drawable.t6_device_bg);
            setBottomViewInWork();
            this.ivBackHomeMode.setImageResource(R.drawable.device_home_back_home_mode_gray);
            this.isBackHomeModeClickable = false;
        } else {
            if (t6Record.getState() != null && t6Record.getState().getWorkState() == null) {
                setFoxAnimation(3);
                this.t6LiveVideoView.setVisibility(0);
                this.ivMuteIcon.setVisibility(0);
                this.isBackHomeModeClickable = true;
                if (t6Record.getState() != null && t6Record.getState().getPetInTime() != 0) {
                    this.ivBackHomeMode.setImageResource(R.drawable.device_home_back_home_mode_gray);
                } else if (t6Record.getSettings().getHomeMode() == 1) {
                    this.ivBackHomeMode.setImageResource(R.drawable.device_home_back_home_mode_blue);
                } else {
                    this.ivBackHomeMode.setImageResource(R.drawable.device_home_back_home_mode);
                }
                setBottomViewInWork();
                if (0 != t6Record.getState().getWanderTime() && 0 == t6Record.getState().getPetInTime()) {
                    this.rlWorkState.setVisibility(8);
                    this.btnPause.setVisibility(8);
                    this.btnContinue.setVisibility(8);
                    this.btnStop.setVisibility(8);
                } else {
                    this.rlWorkState.setVisibility(8);
                }
            } else if (t6Record.getState() != null && t6Record.getState().getWorkState() != null && t6Record.getState().getWorkState().getWorkMode() != 11) {
                this.isBackHomeModeClickable = false;
                this.ivBackHomeMode.setImageResource(R.drawable.device_home_back_home_mode_gray);
                this.rlDeviceErrorOne.setVisibility(8);
                this.rlDeviceErrorTwo.setVisibility(8);
                this.t6LiveVideoView.setVisibility(0);
                this.rlWorkState.setVisibility(0);
                this.deviceWorkStateText.setGravity(17);
                this.ivWarnRight.setVisibility(8);
                findViewById(R.id.iv_t6_right_arrow).setVisibility(0);
                int workMode = t6Record.getState().getWorkState().getWorkMode();
                if (workMode == 0) {
                    T6BottomView t6BottomView = this.t6BottomView;
                    if (t6BottomView != null) {
                        t6BottomView.workInClear();
                    }
                } else if (workMode == 1) {
                    T6BottomView t6BottomView2 = this.t6BottomView;
                    if (t6BottomView2 != null) {
                        t6BottomView2.workInDump();
                    }
                } else if (workMode == 3) {
                    T6BottomView t6BottomView3 = this.t6BottomView;
                    if (t6BottomView3 != null) {
                        t6BottomView3.workInRecover();
                    }
                } else if (workMode == 4) {
                    T6BottomView t6BottomView4 = this.t6BottomView;
                    if (t6BottomView4 != null) {
                        t6BottomView4.workInSmooth();
                    }
                } else if (workMode == 8) {
                    T6BottomView t6BottomView5 = this.t6BottomView;
                    if (t6BottomView5 != null) {
                        t6BottomView5.workInPack();
                    }
                } else {
                    T6BottomView t6BottomView6 = this.t6BottomView;
                    if (t6BottomView6 != null) {
                        t6BottomView6.disableAll();
                    }
                }
                if (1 == t6Record.getState().getWorkState().getWorkProcess() / 10) {
                    this.btnPause.setVisibility(0);
                    int workMode2 = t6Record.getState().getWorkState().getWorkMode();
                    if (workMode2 == 0) {
                        this.deviceWorkStateText.setGravity(17);
                        this.deviceWorkStateText.setText(getResources().getString(R.string.Cleaning_cat_litter));
                        T6BottomView t6BottomView7 = this.t6BottomView;
                        if (t6BottomView7 != null) {
                            t6BottomView7.workInClear();
                        }
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
                        T6BottomView t6BottomView8 = this.t6BottomView;
                        if (t6BottomView8 != null) {
                            t6BottomView8.workInDump();
                        }
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
                        T6BottomView t6BottomView9 = this.t6BottomView;
                        if (t6BottomView9 != null) {
                            t6BottomView9.workInSmooth();
                        }
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
                        AnimationDrawable animationDrawable5 = this.animationDrawable;
                        if (animationDrawable5 == null || !animationDrawable5.isRunning()) {
                            if (this.clickActionType == 1) {
                                setFoxAnimation(0);
                            } else {
                                setFoxAnimation(1);
                            }
                        }
                    } else if (workMode2 == 8) {
                        this.btnPause.setVisibility(8);
                        this.deviceWorkStateText.setGravity(GravityCompat.START);
                        this.ivWarnRight.setVisibility(0);
                        this.deviceWorkStateText.setText(getResources().getString(R.string.T6_packing_tips));
                        T6BottomView t6BottomView10 = this.t6BottomView;
                        if (t6BottomView10 != null) {
                            t6BottomView10.workInPack();
                        }
                    } else if (workMode2 == 9) {
                        this.btnPause.setVisibility(8);
                        this.deviceWorkStateText.setGravity(GravityCompat.START);
                        this.ivWarnRight.setVisibility(0);
                        this.deviceWorkStateText.setText(getResources().getString(R.string.T6_auto_bagging_tips));
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
                                findViewById(R.id.iv_t6_right_arrow).setVisibility(4);
                            } else if (t6Record.getState().getWeightState() == 1) {
                                if (((T6HomePresenter) this.mPresenter).calcDeviceError().size() > 1) {
                                    this.rlDeviceErrorOne.setVisibility(0);
                                    this.rlDeviceErrorTwo.setVisibility(0);
                                    this.rlWorkState.setVisibility(8);
                                } else if (((T6HomePresenter) this.mPresenter).calcDeviceError().size() == 1) {
                                    this.rlDeviceErrorOne.setVisibility(0);
                                    this.rlDeviceErrorTwo.setVisibility(8);
                                    this.rlWorkState.setVisibility(8);
                                }
                            } else if (t6Record.getState().isPetError()) {
                                String string = getResources().getString(R.string.Check_cat_prompt, String.valueOf(t6Record.getPetInTipLimit()));
                                SpannableString spannableString = new SpannableString(getResources().getString(R.string.Pet_go_into_device) + "\n" + string);
                                spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.t4_main_blue)), spannableString.toString().indexOf(string), spannableString.toString().indexOf(string) + string.length(), 18);
                                spannableString.setSpan(new ClickableSpan() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.32
                                    public AnonymousClass32() {
                                    }

                                    @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
                                    public void updateDrawState(TextPaint textPaint) {
                                        super.updateDrawState(textPaint);
                                        textPaint.setColor(T6HomeActivity.this.getResources().getColor(R.color.t4_main_blue));
                                        textPaint.setUnderlineText(false);
                                    }

                                    @Override // android.text.style.ClickableSpan
                                    public void onClick(View view) {
                                        T6HomeActivity t6HomeActivity = T6HomeActivity.this;
                                        t6HomeActivity.launchActivity(WebviewActivity.newIntent(t6HomeActivity, t6HomeActivity.getResources().getString(R.string.No_cat), ApiTools.getWebUrlByKey("t6_pet_reset")));
                                    }
                                }, spannableString.toString().indexOf(string), spannableString.toString().indexOf(string) + string.length(), 33);
                                this.deviceStateText.setHighlightColor(0);
                                this.deviceStateText.setText(spannableString);
                                this.deviceStateText.setMovementMethod(LinkMovementMethod.getInstance());
                                this.deviceStateText.setVisibility(0);
                                this.ivDeviceStateIcon.setVisibility(8);
                                this.rlDeviceState.setVisibility(0);
                                this.rlWorkState.setVisibility(8);
                                findViewById(R.id.iv_t6_right_arrow).setVisibility(4);
                            } else {
                                this.rlWorkState.setVisibility(0);
                                this.deviceWorkStateText.setText(getResources().getString(R.string.Pet_go_into_device));
                                findViewById(R.id.iv_t6_right_arrow).setVisibility(4);
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
                        if (t6Record.getState().getWorkState().getWorkMode() == 3) {
                            this.btnStop.setVisibility(8);
                        } else {
                            this.btnStop.setVisibility(0);
                        }
                        findViewById(R.id.iv_t6_right_arrow).setVisibility(4);
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
                    AnimationDrawable animationDrawable6 = this.animationDrawable;
                    if (animationDrawable6 == null || !animationDrawable6.isRunning()) {
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
                        } else if (t6Record.getState().getWeightState() == 1) {
                            if (((T6HomePresenter) this.mPresenter).calcDeviceError().size() > 1) {
                                this.rlDeviceErrorOne.setVisibility(0);
                                this.rlDeviceErrorTwo.setVisibility(0);
                                this.rlWorkState.setVisibility(8);
                            } else if (((T6HomePresenter) this.mPresenter).calcDeviceError().size() == 1) {
                                this.rlDeviceErrorOne.setVisibility(0);
                                this.rlDeviceErrorTwo.setVisibility(8);
                                this.rlWorkState.setVisibility(8);
                            }
                        } else if (t6Record.getState().isPetError()) {
                            this.isNeedShowPetError = true;
                            String str2 = getResources().getString(R.string.No_cat) + ">";
                            SpannableString spannableString2 = new SpannableString(getResources().getString(R.string.Pet_go_into_device) + ",\n" + str2);
                            spannableString2.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.t4_main_blue)), spannableString2.toString().indexOf(str2), spannableString2.toString().indexOf(str2) + str2.length(), 18);
                            spannableString2.setSpan(new ClickableSpan() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.33
                                public AnonymousClass33() {
                                }

                                @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
                                public void updateDrawState(TextPaint textPaint) {
                                    super.updateDrawState(textPaint);
                                    textPaint.setColor(T6HomeActivity.this.getResources().getColor(R.color.t4_main_blue));
                                    textPaint.setUnderlineText(false);
                                }

                                @Override // android.text.style.ClickableSpan
                                public void onClick(View view) {
                                    T6HomeActivity.this.showTipWindow();
                                }
                            }, spannableString2.toString().indexOf(str2), spannableString2.toString().indexOf(str2) + str2.length(), 33);
                            this.deviceStateText.setHighlightColor(0);
                            this.deviceStateText.setText(spannableString2);
                            this.deviceStateText.setMovementMethod(LinkMovementMethod.getInstance());
                            this.deviceStateText.setVisibility(0);
                            this.ivDeviceStateIcon.setVisibility(8);
                            this.rlDeviceState.setVisibility(0);
                            this.rlWorkState.setVisibility(8);
                            findViewById(R.id.iv_t6_right_arrow).setVisibility(4);
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
            this.rlLivePanel.setBackgroundResource(R.drawable.t6_device_bg);
        }
        if (!AppVersionStateUtils.getCurrentAppVersionCheckState()) {
            showPurchaseEntry();
        }
        if (this.isNeedShowPetError || this.rlPopParent.getVisibility() != 0) {
            return;
        }
        this.rlPopParent.setVisibility(4);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$31 */
    public class AnonymousClass31 implements Runnable {
        public AnonymousClass31() {
        }

        @Override // java.lang.Runnable
        public void run() {
            T6HomeActivity.this.vpLive.setCurrentItem(CommonUtils.getSysIntMap(Constants.T6_HOME_PAGE_SELECT + T6HomeActivity.this.deviceId));
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$32 */
    public class AnonymousClass32 extends ClickableSpan {
        public AnonymousClass32() {
        }

        @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
        public void updateDrawState(TextPaint textPaint) {
            super.updateDrawState(textPaint);
            textPaint.setColor(T6HomeActivity.this.getResources().getColor(R.color.t4_main_blue));
            textPaint.setUnderlineText(false);
        }

        @Override // android.text.style.ClickableSpan
        public void onClick(View view) {
            T6HomeActivity t6HomeActivity = T6HomeActivity.this;
            t6HomeActivity.launchActivity(WebviewActivity.newIntent(t6HomeActivity, t6HomeActivity.getResources().getString(R.string.No_cat), ApiTools.getWebUrlByKey("t6_pet_reset")));
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$33 */
    public class AnonymousClass33 extends ClickableSpan {
        public AnonymousClass33() {
        }

        @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
        public void updateDrawState(TextPaint textPaint) {
            super.updateDrawState(textPaint);
            textPaint.setColor(T6HomeActivity.this.getResources().getColor(R.color.t4_main_blue));
            textPaint.setUnderlineText(false);
        }

        @Override // android.text.style.ClickableSpan
        public void onClick(View view) {
            T6HomeActivity.this.showTipWindow();
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
                spannableString.setSpan(new ClickableSpan() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.34
                    public AnonymousClass34() {
                    }

                    @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
                    public void updateDrawState(TextPaint textPaint) {
                        super.updateDrawState(textPaint);
                        textPaint.setColor(T6HomeActivity.this.getResources().getColor(R.color.t4_main_blue));
                        textPaint.setUnderlineText(false);
                    }

                    @Override // android.text.style.ClickableSpan
                    public void onClick(View view) {
                        T6HomeActivity.this.showTipWindow();
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
        findViewById(R.id.iv_t6_right_arrow).setVisibility(4);
        this.ivDeviceStateIcon.setImageResource(0);
        this.ivDeviceStateIcon.setVisibility(0);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$34 */
    public class AnonymousClass34 extends ClickableSpan {
        public AnonymousClass34() {
        }

        @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
        public void updateDrawState(TextPaint textPaint) {
            super.updateDrawState(textPaint);
            textPaint.setColor(T6HomeActivity.this.getResources().getColor(R.color.t4_main_blue));
            textPaint.setUnderlineText(false);
        }

        @Override // android.text.style.ClickableSpan
        public void onClick(View view) {
            T6HomeActivity.this.showTipWindow();
        }
    }

    private void showPopWindow() {
        if (this.isFirst) {
            this.isNeedShowPetError = false;
            TextView textView = this.deviceStateText;
            if (textView != null) {
                int[] iArr = new int[2];
                textView.getLocationOnScreen(iArr);
                int i = iArr[0];
                int i2 = iArr[1];
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.rlPop.getLayoutParams();
                layoutParams.setMargins(i, i2, layoutParams.rightMargin, layoutParams.bottomMargin);
                this.rlPopParent.setLayoutParams(layoutParams);
                this.handler.postDelayed(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$$ExternalSyntheticLambda46
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$showPopWindow$23();
                    }
                }, 300L);
            }
        }
    }

    public /* synthetic */ void lambda$showPopWindow$23() {
        this.rlPopParent.setVisibility(0);
        this.isFirst = false;
    }

    private void showPurchaseEntry() {
        this.t6Record.getDeviceShared();
        if (this.t6Record.getCloudProduct() != null) {
            TextUtils.isEmpty(DataHelper.getStringSF(this, this.t6Record.getDeviceId() + "_" + this.deviceType + "_" + this.t6Record.getCloudProduct().getServiceId()));
        }
        DataHelper.getBooleanSF(this, Constants.T6_DEVICE_HOME_FREE_TRIAL_TIP + this.deviceType + "_" + this.t6Record.getDeviceId());
        int intergerSF = DataHelper.getIntergerSF(this, Constants.T6_FREE_TRIAL_LEFT_TIME_PROMPT_COUNT + this.deviceType + "_" + this.t6Record.getDeviceId(), 2);
        this.llTrialRemainingTime.setVisibility(8);
        if (this.t6Record.getServiceStatus() == 1 && this.t6Record.getMoreService() != 1 && this.t6Record.getCloudProduct().getSubscribe() != 1 && "FREE".equalsIgnoreCase(this.t6Record.getCloudProduct().getChargeType())) {
            boolean z = (Long.parseLong(this.t6Record.getCloudProduct().getWorkIndate()) * 1000) - System.currentTimeMillis() < 259200000;
            if (intergerSF == 2 || (intergerSF == 1 && z)) {
                this.llTrialRemainingTime.setVisibility(0);
                int i = (int) (((Long.parseLong(this.t6Record.getCloudProduct().getWorkIndate()) * 1000) - System.currentTimeMillis()) / 86400000);
                String str = "zh_CN".equalsIgnoreCase(UserInforUtils.getLanguageLocaleString(CommonUtils.getAppContext())) ? "" : " ";
                if (i > 0) {
                    TextView textView = this.tvTrialRemainingTime;
                    StringBuilder sb = new StringBuilder();
                    sb.append(getString(R.string.Trial_remaining_time));
                    sb.append("：");
                    sb.append(String.valueOf(i));
                    sb.append(str);
                    sb.append(getString(i > 1 ? R.string.Unit_days : R.string.Unit_day));
                    textView.setText(sb.toString());
                } else if (i == 0) {
                    float f = ((Long.parseLong(this.t6Record.getCloudProduct().getWorkIndate()) * 1000.0f) - System.currentTimeMillis()) / 3600000.0f;
                    if (f <= 0.0f) {
                        this.llTrialRemainingTime.setVisibility(8);
                    } else if (f < 1.0f) {
                        this.tvTrialRemainingTime.setText(getString(R.string.Trial_remaining_time) + "：" + String.valueOf(1) + str + getString(R.string.Unit_hour));
                    } else {
                        int i2 = (int) f;
                        TextView textView2 = this.tvTrialRemainingTime;
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(getString(R.string.Trial_remaining_time));
                        sb2.append("：");
                        sb2.append(String.valueOf(i2));
                        sb2.append(str);
                        sb2.append(getString(i2 > 1 ? R.string.Unit_hours : R.string.Unit_hour));
                        textView2.setText(sb2.toString());
                    }
                }
                this.ivTrialRemainingTimeClose.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.35
                    final /* synthetic */ int val$count;
                    final /* synthetic */ boolean val$threeDays;

                    public AnonymousClass35(boolean z2, int intergerSF2) {
                        z = z2;
                        i = intergerSF2;
                    }

                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        T6HomeActivity.this.llTrialRemainingTime.setVisibility(8);
                        DataHelper.setIntergerSF(T6HomeActivity.this, Constants.T6_FREE_TRIAL_LEFT_TIME_PROMPT_COUNT + T6HomeActivity.this.deviceType + "_" + T6HomeActivity.this.t6Record.getDeviceId(), z ? 0 : i - 1);
                    }
                });
            }
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$35 */
    public class AnonymousClass35 implements View.OnClickListener {
        final /* synthetic */ int val$count;
        final /* synthetic */ boolean val$threeDays;

        public AnonymousClass35(boolean z2, int intergerSF2) {
            z = z2;
            i = intergerSF2;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            T6HomeActivity.this.llTrialRemainingTime.setVisibility(8);
            DataHelper.setIntergerSF(T6HomeActivity.this, Constants.T6_FREE_TRIAL_LEFT_TIME_PROMPT_COUNT + T6HomeActivity.this.deviceType + "_" + T6HomeActivity.this.t6Record.getDeviceId(), z ? 0 : i - 1);
        }
    }

    public void showTipWindow() {
        startActivity(WebviewActivity.newIntent(this, getResources().getString(R.string.No_cat), ApiTools.getWebUrlByKey("t6_pet_reset")));
        this.rlPopParent.setVisibility(4);
    }

    private void refreshDeviceStateView() {
        this.rlDeviceState.setVisibility(8);
        this.rlDeviceErrorOne.setVisibility(8);
        this.rlDeviceErrorTwo.setVisibility(8);
        if (this.t6Record.getState().getOverall() == 2) {
            this.rlDeviceState.setVisibility(0);
            this.deviceStateText.setText(R.string.Device_off_line_tip);
            this.ivDeviceStateIcon.setImageResource(R.drawable.d4sh_main_warn_message_warn_icon);
            this.ivDeviceStateIcon.setVisibility(0);
            findViewById(R.id.iv_t6_right_arrow).setVisibility(0);
            return;
        }
        if (this.t6Record.getState().getOta() == 1) {
            this.rlDeviceState.setVisibility(0);
            this.deviceStateText.setText(R.string.D4sh_update);
            this.ivDeviceStateIcon.setImageResource(R.drawable.d4sh_main_warn_message_warn_icon);
            this.ivDeviceStateIcon.setVisibility(0);
            findViewById(R.id.iv_t6_right_arrow).setVisibility(0);
            return;
        }
        if (this.t6Record.getState().getWeightState() == 1) {
            showCalc();
            return;
        }
        if (this.t6Record.getState().getWorkState() == null && this.t6Record.getState().getPetInTime() != 0) {
            petInTimeState(this.t6Record);
            this.rlDeviceErrorOne.setVisibility(8);
            this.rlDeviceErrorTwo.setVisibility(8);
        } else if (this.t6Record.getState().getWorkState() != null && this.t6Record.getState().getPetInTime() != 0) {
            this.rlDeviceState.setVisibility(8);
        } else {
            showCalc();
        }
    }

    private void showCalc() {
        int i;
        int i2;
        final List<DeviceError> listCalcDeviceError = ((T6HomePresenter) this.mPresenter).calcDeviceError();
        this.ivDeviceErrorOneRightArrow.setVisibility(0);
        this.ivDeviceErrorTwoRightArrow.setVisibility(0);
        findViewById(R.id.iv_device_error_one_right_arrow).setVisibility(0);
        findViewById(R.id.iv_device_error_two_right_arrow).setVisibility(0);
        this.deviceErrorOneText.setGravity(3);
        this.deviceErrorTwoText.setGravity(3);
        if (listCalcDeviceError.size() > 1) {
            this.rlDeviceErrorOne.setVisibility(0);
            this.ivDeviceErrorOneIcon.setImageResource(R.drawable.d4sh_main_warn_message_warn_icon);
            this.deviceErrorOneText.setText(listCalcDeviceError.get(0).getErrorMsg());
            this.rlDeviceErrorTwo.setVisibility(0);
            this.ivDeviceErrorOneIcon.setVisibility(0);
            this.ivDeviceErrorTwoIcon.setImageResource(R.drawable.d4sh_main_warn_message_warn_icon);
            this.deviceErrorTwoText.setText(getResources().getString(R.string.Have_mistake_to_handle, String.valueOf(listCalcDeviceError.size() - 1)));
            String errorType = listCalcDeviceError.get(0).getErrorType();
            if (errorType.equals(Constants.T6_ERROR_CODE_PACKBOX)) {
                this.llKnowMoreOne.setVisibility(0);
                this.ivDeviceErrorOneIcon.setImageResource(0);
                this.rlDeviceErrorOne.setGravity(17);
                this.deviceErrorOneText.setGravity(17);
                this.ivDeviceErrorOneRightArrow.setVisibility(4);
                this.btnIgnoreOne.setVisibility(0);
                this.btnKnowMoreOne.setVisibility(8);
                this.btnIgnoreOne.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$$ExternalSyntheticLambda30
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$showCalc$24(view);
                    }
                });
            } else if (errorType.equals(Constants.T6_PET_CLOSE)) {
                this.ivDeviceErrorOneIcon.setVisibility(8);
                this.btnKnowMoreOne.setVisibility(8);
                this.btnIgnoreOne.setVisibility(8);
                this.ivDeviceErrorOneRightArrow.setVisibility(8);
                this.rlDeviceErrorTwo.setVisibility(8);
            } else if (errorType.equals(Constants.T6_OPEN_LIGHT_ASSIST_WHILE_PH_OPEN)) {
                this.ivDeviceErrorOneIcon.setVisibility(8);
                this.btnKnowMoreOne.setVisibility(8);
                this.btnIgnoreOne.setVisibility(8);
                this.ivDeviceErrorOneRightArrow.setVisibility(0);
            } else if (errorType.equals(Constants.T5_ERROR_CODE_N60_EMPTY)) {
                this.llKnowMoreOne.setVisibility(0);
                this.ivDeviceErrorOneIcon.setImageResource(0);
                this.rlDeviceErrorOne.setGravity(17);
                this.deviceErrorOneText.setGravity(17);
                this.ivDeviceErrorOneRightArrow.setVisibility(4);
                this.btnKnowMoreOne.setText(getResources().getString(R.string.To_reset));
                this.btnKnowMoreOne.setVisibility(0);
                this.btnIgnoreOne.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$$ExternalSyntheticLambda35
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$showCalc$25(view);
                    }
                });
                this.btnKnowMoreOne.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$$ExternalSyntheticLambda36
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$showCalc$26(view);
                    }
                });
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
                this.btnIgnoreOne.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$$ExternalSyntheticLambda37
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$showCalc$27(listCalcDeviceError, view);
                    }
                });
                this.btnKnowMoreOne.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$$ExternalSyntheticLambda38
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$showCalc$28(listCalcDeviceError, view);
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
                this.btnIgnoreOne.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$$ExternalSyntheticLambda39
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$showCalc$29(view);
                    }
                });
                this.btnKnowMoreOne.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$$ExternalSyntheticLambda40
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$showCalc$30(view);
                    }
                });
                this.rlDeviceErrorOne.setGravity(GravityCompat.START);
                this.deviceErrorOneText.setGravity(GravityCompat.START);
                this.ivDeviceErrorOneIcon.setImageResource(R.drawable.d4sh_main_warn_message_warn_icon);
                findViewById(R.id.iv_device_error_one_right_arrow).setVisibility(4);
            } else if (errorType.equals(Constants.T6_ERROR_CODE_SAND_LACK)) {
                this.llKnowMoreOne.setVisibility(8);
                this.rlDeviceErrorOne.setGravity(GravityCompat.START);
                this.deviceErrorOneText.setGravity(GravityCompat.START);
                findViewById(R.id.iv_device_error_one_right_arrow).setVisibility(4);
            } else {
                if (sealDoorOpen(errorType)) {
                    this.ivDeviceErrorOneIcon.setImageResource(0);
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
        if (((T6HomePresenter) this.mPresenter).calcDeviceError().size() == 1) {
            this.rlDeviceErrorTwo.setVisibility(8);
            this.rlDeviceErrorOne.setVisibility(0);
            this.ivDeviceErrorOneIcon.setVisibility(0);
            this.ivDeviceErrorOneIcon.setImageResource(R.drawable.d4sh_main_warn_message_warn_icon);
            this.deviceErrorOneText.setText(listCalcDeviceError.get(0).getErrorMsg());
            String errorType2 = listCalcDeviceError.get(0).getErrorType();
            if (sealDoorOpen(errorType2)) {
                this.ivDeviceErrorOneIcon.setImageResource(0);
                this.llKnowMoreOne.setVisibility(8);
                this.rlDeviceErrorOne.setGravity(GravityCompat.START);
                this.deviceErrorOneText.setGravity(GravityCompat.START);
                findViewById(R.id.iv_device_error_one_right_arrow).setVisibility(4);
                return;
            }
            if (errorType2.equals(Constants.T6_PET_CLOSE)) {
                this.ivDeviceErrorOneIcon.setVisibility(8);
                this.ivDeviceErrorOneIcon.setVisibility(8);
                this.btnKnowMoreOne.setVisibility(8);
                this.btnIgnoreOne.setVisibility(8);
                this.ivDeviceErrorOneRightArrow.setVisibility(8);
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
            if (errorType2.equals(Constants.T6_ERROR_CODE_PACKBOX)) {
                this.llKnowMoreOne.setVisibility(0);
                this.ivDeviceErrorOneIcon.setImageResource(0);
                this.rlDeviceErrorOne.setGravity(17);
                this.deviceErrorOneText.setGravity(17);
                this.ivDeviceErrorOneRightArrow.setVisibility(4);
                this.btnKnowMoreOne.setVisibility(8);
                this.btnIgnoreOne.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$$ExternalSyntheticLambda41
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$showCalc$31(view);
                    }
                });
                return;
            }
            if (errorType2.equals(Constants.T5_ERROR_CODE_N60_EMPTY)) {
                this.llKnowMoreOne.setVisibility(0);
                this.ivDeviceErrorOneIcon.setImageResource(0);
                this.rlDeviceErrorOne.setGravity(17);
                this.deviceErrorOneText.setGravity(17);
                this.ivDeviceErrorOneRightArrow.setVisibility(4);
                this.btnKnowMoreOne.setText(getResources().getString(R.string.To_reset));
                this.btnKnowMoreOne.setVisibility(0);
                this.btnIgnoreOne.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$$ExternalSyntheticLambda42
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$showCalc$32(view);
                    }
                });
                this.btnKnowMoreOne.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$$ExternalSyntheticLambda43
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$showCalc$33(view);
                    }
                });
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
                this.btnIgnoreOne.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$$ExternalSyntheticLambda31
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$showCalc$34(listCalcDeviceError, view);
                    }
                });
                this.btnKnowMoreOne.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$$ExternalSyntheticLambda32
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$showCalc$35(listCalcDeviceError, view);
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
                this.btnIgnoreOne.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$$ExternalSyntheticLambda33
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$showCalc$36(view);
                    }
                });
                this.btnKnowMoreOne.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$$ExternalSyntheticLambda34
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$showCalc$37(view);
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
        }
    }

    public /* synthetic */ void lambda$showCalc$24(View view) {
        P p = this.mPresenter;
        if (p != 0) {
            ((T6HomePresenter) p).ignoreRemainingBags();
        }
        refreshDeviceStateView();
    }

    public /* synthetic */ void lambda$showCalc$25(View view) {
        P p = this.mPresenter;
        if (p != 0) {
            ((T6HomePresenter) p).ignoreN60State();
        }
    }

    public /* synthetic */ void lambda$showCalc$26(View view) {
        launchActivity(T6ConsumablesActivity.newIntent(this, this.deviceId, 27, 4, this.relatedProductsInfor));
    }

    public /* synthetic */ void lambda$showCalc$27(List list, View view) {
        ((T6HomePresenter) this.mPresenter).ignorePetError(((DeviceError) list.get(0)).getPetErrorInfo(), 2);
    }

    public /* synthetic */ void lambda$showCalc$28(List list, View view) {
        if (this.t6Record.getDeviceShared() == null) {
            MedicalConversionItem medicalConversionItem = new MedicalConversionItem();
            medicalConversionItem.setContent(((DeviceError) list.get(0)).getPetErrorInfo().getDesc());
            medicalConversionItem.setPetId(((DeviceError) list.get(0)).getPetErrorInfo().getPetId());
            medicalConversionItem.setPetName(((DeviceError) list.get(0)).getPetErrorInfo().getPetName());
            DoctorUtils.getInstance().askDoctor(this, medicalConversionItem, null, 0L, 0);
            return;
        }
        PetkitToast.showTopToast(this, getResources().getString(R.string.Shared_device_is_limited_tip), 0, 0);
    }

    public /* synthetic */ void lambda$showCalc$29(View view) {
        ((T6HomePresenter) this.mPresenter).ignorePh();
    }

    public /* synthetic */ void lambda$showCalc$30(View view) {
        if (this.t6Record.getDeviceShared() == null) {
            MedicalConversionItem medicalConversionItem = new MedicalConversionItem();
            medicalConversionItem.setContent(getString(R.string.T6_timeline_recognize_urine_ph_tip));
            DoctorUtils.getInstance().askDoctor(this, medicalConversionItem, null, this.deviceId, this.deviceType);
            return;
        }
        PetkitToast.showTopToast(this, getResources().getString(R.string.Shared_device_is_limited_tip), 0, 0);
    }

    public /* synthetic */ void lambda$showCalc$31(View view) {
        P p = this.mPresenter;
        if (p != 0) {
            ((T6HomePresenter) p).ignoreRemainingBags();
        }
        refreshDeviceStateView();
    }

    public /* synthetic */ void lambda$showCalc$32(View view) {
        P p = this.mPresenter;
        if (p != 0) {
            ((T6HomePresenter) p).ignoreN60State();
        }
    }

    public /* synthetic */ void lambda$showCalc$33(View view) {
        launchActivity(T6ConsumablesActivity.newIntent(this, this.deviceId, 27, 4, this.relatedProductsInfor));
    }

    public /* synthetic */ void lambda$showCalc$34(List list, View view) {
        showNoRemindConfirmWindow(((DeviceError) list.get(0)).getPetErrorInfo(), 2);
    }

    public /* synthetic */ void lambda$showCalc$35(List list, View view) {
        if (this.t6Record.getDeviceShared() == null) {
            MedicalConversionItem medicalConversionItem = new MedicalConversionItem();
            medicalConversionItem.setContent(((DeviceError) list.get(0)).getPetErrorInfo().getDesc());
            medicalConversionItem.setPetId(((DeviceError) list.get(0)).getPetErrorInfo().getPetId());
            medicalConversionItem.setPetName(((DeviceError) list.get(0)).getPetErrorInfo().getPetName());
            DoctorUtils.getInstance().askDoctor(this, medicalConversionItem, null, 0L, 0);
            return;
        }
        PetkitToast.showTopToast(this, getResources().getString(R.string.Shared_device_is_limited_tip), 0, 0);
    }

    public /* synthetic */ void lambda$showCalc$36(View view) {
        ((T6HomePresenter) this.mPresenter).ignorePh();
    }

    public /* synthetic */ void lambda$showCalc$37(View view) {
        if (this.t6Record.getDeviceShared() == null) {
            MedicalConversionItem medicalConversionItem = new MedicalConversionItem();
            medicalConversionItem.setContent(getString(R.string.T6_timeline_recognize_urine_ph_tip));
            DoctorUtils.getInstance().askDoctor(this, medicalConversionItem, null, this.deviceId, this.deviceType);
            return;
        }
        PetkitToast.showTopToast(this, getResources().getString(R.string.Shared_device_is_limited_tip), 0, 0);
    }

    private boolean sealDoorOpen(String str) {
        return Constants.T6_ERROR_CODE_T6_HALL_SO.equals(str);
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T6HomeContract.View
    public void showForceUpgradeWindow() {
        this.otaDisposable = Observable.timer(500L, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.lambda$showForceUpgradeWindow$38((Long) obj);
            }
        });
    }

    public /* synthetic */ void lambda$showForceUpgradeWindow$38(Long l) throws Exception {
        if (this.t6Record.getDeviceShared() != null) {
            OtaPromptWindow otaPromptWindow = this.otaPromptWindow;
            if (otaPromptWindow == null || !otaPromptWindow.isShowing()) {
                OtaPromptWindow otaPromptWindow2 = new OtaPromptWindow(this, getResources().getString(R.string.Ota_prompt_share), getResources().getString(R.string.I_know), new OtaPromptWindow.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.36
                    public AnonymousClass36() {
                    }

                    @Override // com.petkit.android.activities.petkitBleDevice.widget.OtaPromptWindow.OnClickListener
                    public void onCancel() {
                        T6HomeActivity.this.killMyself();
                    }

                    @Override // com.petkit.android.activities.petkitBleDevice.widget.OtaPromptWindow.OnClickListener
                    public void onConfirm() {
                        T6HomeActivity.this.killMyself();
                    }

                    @Override // com.petkit.android.activities.petkitBleDevice.widget.OtaPromptWindow.OnClickListener
                    public void onBack() {
                        T6HomeActivity.this.killMyself();
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
            OtaPromptWindow otaPromptWindow4 = new OtaPromptWindow(this, getResources().getString(R.string.Ota_prompt), getResources().getString(R.string.Update_right_now), getResources().getString(R.string.Close), new OtaPromptWindow.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.37
                public AnonymousClass37() {
                }

                @Override // com.petkit.android.activities.petkitBleDevice.widget.OtaPromptWindow.OnClickListener
                public void onCancel() {
                    T6HomeActivity.this.killMyself();
                }

                @Override // com.petkit.android.activities.petkitBleDevice.widget.OtaPromptWindow.OnClickListener
                public void onConfirm() {
                    T6HomeActivity t6HomeActivity = T6HomeActivity.this;
                    t6HomeActivity.launchActivity(T6SettingOtaActivity.newIntent(t6HomeActivity, t6HomeActivity.deviceId, T6HomeActivity.this.deviceType));
                    T6HomeActivity.this.killMyself();
                }

                @Override // com.petkit.android.activities.petkitBleDevice.widget.OtaPromptWindow.OnClickListener
                public void onBack() {
                    T6HomeActivity.this.killMyself();
                }
            }, true);
            this.otaPromptWindow = otaPromptWindow4;
            otaPromptWindow4.setTouchOutsideAble(false);
            this.otaPromptWindow.setCheckboxVisibility(8);
            this.otaPromptWindow.show(getWindow().getDecorView());
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$36 */
    public class AnonymousClass36 implements OtaPromptWindow.OnClickListener {
        public AnonymousClass36() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.widget.OtaPromptWindow.OnClickListener
        public void onCancel() {
            T6HomeActivity.this.killMyself();
        }

        @Override // com.petkit.android.activities.petkitBleDevice.widget.OtaPromptWindow.OnClickListener
        public void onConfirm() {
            T6HomeActivity.this.killMyself();
        }

        @Override // com.petkit.android.activities.petkitBleDevice.widget.OtaPromptWindow.OnClickListener
        public void onBack() {
            T6HomeActivity.this.killMyself();
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$37 */
    public class AnonymousClass37 implements OtaPromptWindow.OnClickListener {
        public AnonymousClass37() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.widget.OtaPromptWindow.OnClickListener
        public void onCancel() {
            T6HomeActivity.this.killMyself();
        }

        @Override // com.petkit.android.activities.petkitBleDevice.widget.OtaPromptWindow.OnClickListener
        public void onConfirm() {
            T6HomeActivity t6HomeActivity = T6HomeActivity.this;
            t6HomeActivity.launchActivity(T6SettingOtaActivity.newIntent(t6HomeActivity, t6HomeActivity.deviceId, T6HomeActivity.this.deviceType));
            T6HomeActivity.this.killMyself();
        }

        @Override // com.petkit.android.activities.petkitBleDevice.widget.OtaPromptWindow.OnClickListener
        public void onBack() {
            T6HomeActivity.this.killMyself();
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$38 */
    public class AnonymousClass38 implements NormalCenterTipWindow.OnClickOk {
        public final /* synthetic */ PetErrorInfo val$info;
        public final /* synthetic */ int val$type;

        @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
        public void onCancelClick() {
        }

        public AnonymousClass38(PetErrorInfo petErrorInfo, int i) {
            petErrorInfo = petErrorInfo;
            i = i;
        }

        @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
        public void onOkClick() {
            ((T6HomePresenter) ((BaseActivity) T6HomeActivity.this).mPresenter).ignorePetError(petErrorInfo, i);
        }
    }

    public void showNoRemindConfirmWindow(PetErrorInfo petErrorInfo, int i) {
        NormalCenterTipWindow normalCenterTipWindow = new NormalCenterTipWindow(this, new NormalCenterTipWindow.OnClickOk() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.38
            public final /* synthetic */ PetErrorInfo val$info;
            public final /* synthetic */ int val$type;

            @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
            public void onCancelClick() {
            }

            public AnonymousClass38(PetErrorInfo petErrorInfo2, int i2) {
                petErrorInfo = petErrorInfo2;
                i = i2;
            }

            @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
            public void onOkClick() {
                ((T6HomePresenter) ((BaseActivity) T6HomeActivity.this).mPresenter).ignorePetError(petErrorInfo, i);
            }
        }, (String) null, petErrorInfo2.getAlertTip());
        this.petErrorNoRemindWindow = normalCenterTipWindow;
        if (normalCenterTipWindow.isShowing()) {
            return;
        }
        this.petErrorNoRemindWindow.show(getWindow().getDecorView());
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T6HomeContract.View
    @SuppressLint({"CheckResult"})
    public void showRecommendUpgradeWindow(final OtaResult otaResult) {
        T6Record t6Record;
        T6Record t6Record2;
        if (DataHelper.getBooleanSF(this, (this.deviceId + 27) + otaResult.getVersion())) {
            if (DataHelper.getBooleanSF(this, 27 + String.valueOf(this.deviceId) + Constants.DEVICE_BIND_COMPLETE) || ((t6Record2 = this.t6Record) != null && t6Record2.getSettings().getSandType() == 0)) {
                launchActivity(DeviceSetInfoActivity.newIntent(this, this.deviceId, 27, true));
                killMyself();
                return;
            } else {
                ((T6HomePresenter) this.mPresenter).checkInitWindow(2);
                return;
            }
        }
        if (!this.isShowRecommendUpgradeWindow) {
            this.isShowRecommendUpgradeWindow = true;
            Observable.timer(500L, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$$ExternalSyntheticLambda50
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f$0.lambda$showRecommendUpgradeWindow$39(otaResult, (Long) obj);
                }
            });
            return;
        }
        if (DataHelper.getBooleanSF(this, 27 + String.valueOf(this.deviceId) + Constants.DEVICE_BIND_COMPLETE) || ((t6Record = this.t6Record) != null && t6Record.getSettings().getSandType() == 0)) {
            launchActivity(DeviceSetInfoActivity.newIntent(this, this.deviceId, 27, true));
            killMyself();
        } else {
            ((T6HomePresenter) this.mPresenter).checkInitWindow(2);
        }
    }

    public /* synthetic */ void lambda$showRecommendUpgradeWindow$39(OtaResult otaResult, Long l) throws Exception {
        if (this.t6Record.getDeviceShared() == null) {
            OtaPromptWindow otaPromptWindow = this.otaPromptWindow;
            if (otaPromptWindow == null || !otaPromptWindow.isShowing()) {
                OtaPromptWindow otaPromptWindow2 = new OtaPromptWindow(this, getResources().getString(R.string.Ota_prompt), getResources().getString(R.string.Update_right_now), getResources().getString(R.string.Cancel), new OtaPromptWindow.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.39
                    public final /* synthetic */ OtaResult val$otaCheckResult;

                    @Override // com.petkit.android.activities.petkitBleDevice.widget.OtaPromptWindow.OnClickListener
                    public void onBack() {
                    }

                    public AnonymousClass39(OtaResult otaResult2) {
                        otaResult = otaResult2;
                    }

                    @Override // com.petkit.android.activities.petkitBleDevice.widget.OtaPromptWindow.OnClickListener
                    public void onCancel() {
                        if (T6HomeActivity.this.otaPromptWindow.isChecked()) {
                            DataHelper.setBooleanSF(T6HomeActivity.this, (T6HomeActivity.this.deviceId + 27) + otaResult.getVersion(), Boolean.TRUE);
                        }
                        if (!DataHelper.getBooleanSF(T6HomeActivity.this, 27 + String.valueOf(T6HomeActivity.this.deviceId) + Constants.DEVICE_BIND_COMPLETE) && (T6HomeActivity.this.t6Record == null || T6HomeActivity.this.t6Record.getSettings().getSandType() != 0)) {
                            ((T6HomePresenter) ((BaseActivity) T6HomeActivity.this).mPresenter).checkInitWindow(2);
                            return;
                        }
                        T6HomeActivity t6HomeActivity = T6HomeActivity.this;
                        t6HomeActivity.launchActivity(DeviceSetInfoActivity.newIntent(t6HomeActivity, t6HomeActivity.deviceId, 27, true));
                        T6HomeActivity.this.killMyself();
                    }

                    @Override // com.petkit.android.activities.petkitBleDevice.widget.OtaPromptWindow.OnClickListener
                    public void onConfirm() {
                        if (T6HomeActivity.this.otaPromptWindow.isChecked()) {
                            DataHelper.setBooleanSF(T6HomeActivity.this, (T6HomeActivity.this.deviceId + 27) + otaResult.getVersion(), Boolean.TRUE);
                        }
                        T6HomeActivity t6HomeActivity = T6HomeActivity.this;
                        t6HomeActivity.launchActivity(T6SettingOtaActivity.newIntent(t6HomeActivity, t6HomeActivity.deviceId, T6HomeActivity.this.deviceType));
                    }
                }, true);
                this.otaPromptWindow = otaPromptWindow2;
                otaPromptWindow2.setTouchOutsideAble(true);
                this.otaPromptWindow.setCheckboxVisibility(0);
                this.otaPromptWindow.show(getWindow().getDecorView());
            }
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$39 */
    public class AnonymousClass39 implements OtaPromptWindow.OnClickListener {
        public final /* synthetic */ OtaResult val$otaCheckResult;

        @Override // com.petkit.android.activities.petkitBleDevice.widget.OtaPromptWindow.OnClickListener
        public void onBack() {
        }

        public AnonymousClass39(OtaResult otaResult2) {
            otaResult = otaResult2;
        }

        @Override // com.petkit.android.activities.petkitBleDevice.widget.OtaPromptWindow.OnClickListener
        public void onCancel() {
            if (T6HomeActivity.this.otaPromptWindow.isChecked()) {
                DataHelper.setBooleanSF(T6HomeActivity.this, (T6HomeActivity.this.deviceId + 27) + otaResult.getVersion(), Boolean.TRUE);
            }
            if (!DataHelper.getBooleanSF(T6HomeActivity.this, 27 + String.valueOf(T6HomeActivity.this.deviceId) + Constants.DEVICE_BIND_COMPLETE) && (T6HomeActivity.this.t6Record == null || T6HomeActivity.this.t6Record.getSettings().getSandType() != 0)) {
                ((T6HomePresenter) ((BaseActivity) T6HomeActivity.this).mPresenter).checkInitWindow(2);
                return;
            }
            T6HomeActivity t6HomeActivity = T6HomeActivity.this;
            t6HomeActivity.launchActivity(DeviceSetInfoActivity.newIntent(t6HomeActivity, t6HomeActivity.deviceId, 27, true));
            T6HomeActivity.this.killMyself();
        }

        @Override // com.petkit.android.activities.petkitBleDevice.widget.OtaPromptWindow.OnClickListener
        public void onConfirm() {
            if (T6HomeActivity.this.otaPromptWindow.isChecked()) {
                DataHelper.setBooleanSF(T6HomeActivity.this, (T6HomeActivity.this.deviceId + 27) + otaResult.getVersion(), Boolean.TRUE);
            }
            T6HomeActivity t6HomeActivity = T6HomeActivity.this;
            t6HomeActivity.launchActivity(T6SettingOtaActivity.newIntent(t6HomeActivity, t6HomeActivity.deviceId, T6HomeActivity.this.deviceType));
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T6HomeContract.View
    public void noUpgrade() {
        T6Record t6Record;
        if (DataHelper.getBooleanSF(this, 27 + String.valueOf(this.deviceId) + Constants.DEVICE_BIND_COMPLETE) || ((t6Record = this.t6Record) != null && t6Record.getSettings().getSandType() == 0)) {
            launchActivity(DeviceSetInfoActivity.newIntent(this, this.deviceId, 27, true));
            killMyself();
        } else {
            ((T6HomePresenter) this.mPresenter).checkInitWindow(2);
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T6HomeContract.View
    public void setTitleStatus(T6Record t6Record) {
        this.ibSetting.setImageResource(DeviceCenterUtils.isT6NeedOtaById(t6Record.getDeviceId()) ? R.drawable.black_setting_with_notify_flag : R.drawable.black_setting_icon);
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T6HomeContract.View
    public void refreshHighLight(HighLightRecordRsp highLightRecordRsp) {
        T6VlogRecordAdapter t6VlogRecordAdapter = new T6VlogRecordAdapter(this, this.t6Record, this.deviceType);
        this.t6VlogRecordAdapter = t6VlogRecordAdapter;
        t6VlogRecordAdapter.append((List) highLightRecordRsp.getItems());
        this.rvHighlights.setAdapter(this.t6VlogRecordAdapter);
        this.t6VlogRecordAdapter.setOnDailyHighlightItemClickListener(new BasePetkitHighlightAdapter.OnDailyHighlightItemClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.40
            @Override // com.petkit.android.activities.petkitBleDevice.adapter.BasePetkitHighlightAdapter.OnDailyHighlightItemClickListener
            public void onEmptyClick() {
            }

            public AnonymousClass40() {
            }

            @Override // com.petkit.android.activities.petkitBleDevice.adapter.BasePetkitHighlightAdapter.OnDailyHighlightItemClickListener
            public void onPlayBtnClick(HighlightRecord highlightRecord) {
                T6HomeActivity t6HomeActivity = T6HomeActivity.this;
                t6HomeActivity.startActivityForResult(MediaDisplayActivity.newIntent(t6HomeActivity, highlightRecord, 3, t6HomeActivity.deviceId, T6HomeActivity.this.deviceType), 5);
            }

            @Override // com.petkit.android.activities.petkitBleDevice.adapter.BasePetkitHighlightAdapter.OnDailyHighlightItemClickListener
            public void onMarkVlogClick(HighlightRecord highlightRecord) {
                ((T6HomePresenter) ((BaseActivity) T6HomeActivity.this).mPresenter).getHighlightM3U8(highlightRecord.getId(), T6HomeActivity.this.deviceType, highlightRecord.getCycle());
            }
        });
        if (CommonUtils.checkPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") && this.t6Record.getSettings().getHighlight() == 1 && this.t6Record.getSettings().getAutoProduct() == 1 && highLightRecordRsp.getItems().size() > 0) {
            HighlightRecord highlightRecord = highLightRecordRsp.getItems().get(0);
            if (highlightRecord.getDisabled() != 1 && highlightRecord.getDisabled() != 2 && TextUtils.isEmpty(highlightRecord.getVideoUrl()) && VlogUtils.getVlogMarkRecord(CommonUtils.getCurrentUserId(), highlightRecord.getId()) == null) {
                if ((System.currentTimeMillis() <= ((long) highlightRecord.getExpired()) * 1000 || highlightRecord.getExpired() == 0) && highlightRecord.getId() != 0) {
                    new Handler().postDelayed(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.41
                        public final /* synthetic */ HighlightRecord val$itemsBean;

                        public AnonymousClass41(HighlightRecord highlightRecord2) {
                            highlightRecord = highlightRecord2;
                        }

                        @Override // java.lang.Runnable
                        public void run() {
                            if (T6HomeActivity.this.t6VlogRecordAdapter != null) {
                                T6HomeActivity.this.t6VlogRecordAdapter.setRefreshLoadingStatus(true);
                                ((T6HomePresenter) ((BaseActivity) T6HomeActivity.this).mPresenter).getHighlightM3U8(highlightRecord.getId(), T6HomeActivity.this.deviceType, highlightRecord.getCycle());
                            }
                        }
                    }, 200L);
                }
            }
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$40 */
    public class AnonymousClass40 implements BasePetkitHighlightAdapter.OnDailyHighlightItemClickListener {
        @Override // com.petkit.android.activities.petkitBleDevice.adapter.BasePetkitHighlightAdapter.OnDailyHighlightItemClickListener
        public void onEmptyClick() {
        }

        public AnonymousClass40() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.adapter.BasePetkitHighlightAdapter.OnDailyHighlightItemClickListener
        public void onPlayBtnClick(HighlightRecord highlightRecord) {
            T6HomeActivity t6HomeActivity = T6HomeActivity.this;
            t6HomeActivity.startActivityForResult(MediaDisplayActivity.newIntent(t6HomeActivity, highlightRecord, 3, t6HomeActivity.deviceId, T6HomeActivity.this.deviceType), 5);
        }

        @Override // com.petkit.android.activities.petkitBleDevice.adapter.BasePetkitHighlightAdapter.OnDailyHighlightItemClickListener
        public void onMarkVlogClick(HighlightRecord highlightRecord) {
            ((T6HomePresenter) ((BaseActivity) T6HomeActivity.this).mPresenter).getHighlightM3U8(highlightRecord.getId(), T6HomeActivity.this.deviceType, highlightRecord.getCycle());
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$41 */
    public class AnonymousClass41 implements Runnable {
        public final /* synthetic */ HighlightRecord val$itemsBean;

        public AnonymousClass41(HighlightRecord highlightRecord2) {
            highlightRecord = highlightRecord2;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (T6HomeActivity.this.t6VlogRecordAdapter != null) {
                T6HomeActivity.this.t6VlogRecordAdapter.setRefreshLoadingStatus(true);
                ((T6HomePresenter) ((BaseActivity) T6HomeActivity.this).mPresenter).getHighlightM3U8(highlightRecord.getId(), T6HomeActivity.this.deviceType, highlightRecord.getCycle());
            }
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T6HomeContract.View
    public void refreshHighLightFail() {
        T6VlogRecordAdapter t6VlogRecordAdapter = new T6VlogRecordAdapter(this, this.t6Record, this.deviceType);
        this.t6VlogRecordAdapter = t6VlogRecordAdapter;
        t6VlogRecordAdapter.append((List) new ArrayList());
        this.rvHighlights.setAdapter(this.t6VlogRecordAdapter);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$42 */
    public class AnonymousClass42 implements Runnable {
        public AnonymousClass42() {
        }

        @Override // java.lang.Runnable
        public void run() {
            T6HomeActivity.this.t6LiveVideoView.refreshTimeoutState();
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T6HomeContract.View
    public void loadVideoTimeout() {
        runOnUiThread(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.42
            public AnonymousClass42() {
            }

            @Override // java.lang.Runnable
            public void run() {
                T6HomeActivity.this.t6LiveVideoView.refreshTimeoutState();
            }
        });
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T6HomeContract.View
    public void resetVideoTimeoutView() {
        this.t6LiveVideoView.resetTimeoutView();
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T6HomeContract.View
    public void statisticResult(List<T6StatisticResInfo> list, int i) {
        this.statisticList = list;
        this.resStatisticList = list;
        MySmartRefreshView mySmartRefreshView = this.srl;
        if (mySmartRefreshView != null) {
            mySmartRefreshView.finishRefresh();
        }
        refreshPetChart();
        this.handler.postDelayed(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$$ExternalSyntheticLambda29
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$statisticResult$40();
            }
        }, 300L);
        updateEventTime();
        if (this.isNotFirstLoad) {
            ((T6HomePresenter) this.mPresenter).getListNum(this.calendarTime);
            updateEventList(i);
        } else {
            this.llDay.setVisibility(8);
            this.isNotFirstLoad = true;
        }
    }

    public /* synthetic */ void lambda$statisticResult$40() {
        this.ivEventAnim.setVisibility(8);
        this.ivEventAnim.clearAnimation();
    }

    private void updateEventList(int i) {
        if (i == 2) {
            if (this.toiletFragment.isAdded()) {
                this.toiletFragment.selectDate(this.calendarTime, 0);
                return;
            }
            return;
        }
        if (i == 1) {
            if (this.allFragment.isAdded()) {
                this.allFragment.selectDate(this.calendarTime, 2);
            }
            if (this.toiletFragment.isAdded()) {
                this.toiletFragment.selectDate(this.calendarTime, 0);
            }
            if (this.toiletErrorFragment.isAdded()) {
                this.toiletErrorFragment.selectDate(this.calendarTime, 3);
                return;
            }
            return;
        }
        if (i == 3) {
            if (this.allFragment.isAdded()) {
                this.allFragment.selectDate(this.calendarTime, 2);
            }
            if (this.toiletErrorFragment.isAdded()) {
                this.toiletErrorFragment.selectDate(this.calendarTime, 3);
            }
            if (this.toiletFragment.isAdded()) {
                this.toiletFragment.selectDate(this.calendarTime, 0);
                return;
            }
            return;
        }
        if (this.allFragment.isAdded()) {
            this.allFragment.selectDate(this.calendarTime, 2);
        }
        if (this.toiletFragment.isAdded()) {
            this.toiletFragment.selectDate(this.calendarTime, 0);
        }
        if (this.petFragment.isAdded()) {
            this.petFragment.selectDate(this.calendarTime, 1);
        }
        if (this.toiletErrorFragment.isAdded()) {
            this.toiletErrorFragment.selectDate(this.calendarTime, 3);
        }
    }

    public void dateSelected(String str) {
        long millisecondByDateString = DateUtil.getMillisecondByDateString(str);
        this.calendarTime = T6Utils.getCurrentZoneTime(this.t6Record, this.deviceId, millisecondByDateString, this.deviceType);
        PetkitLog.d("t6Time", millisecondByDateString + "  c  " + this.calendarTime);
        ((T6HomePresenter) this.mPresenter).getStatistic(this.calendarTime, 0);
        updateEventTime();
        ((T6HomePresenter) this.mPresenter).getListNum(this.calendarTime);
        updateEventList(0);
        DatePickerWindow datePickerWindow = this.datePickerWindow;
        if (datePickerWindow != null) {
            datePickerWindow.dismiss();
        }
    }

    private void updateEventTime() {
        if (DateUtil.isSameDay(this.calendarTime, this.t6Record.getActualTimeZone())) {
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

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T6HomeContract.View
    public void showCalendarDialog(String str, List<CountBean> list) {
        DatePickerWindow datePickerWindow = this.datePickerWindow;
        if (datePickerWindow != null && datePickerWindow.isShowing()) {
            this.datePickerWindow.getDatePicker().refreshT6Data(this.t6Record.getCreatedAt(), this.deviceType, T6Utils.getStartAndEndDateStr2(this.calendarTime)[0], this.deviceId, list);
        } else {
            DatePickerWindow datePickerWindow2 = new DatePickerWindow(this, this.t6Record.getCreatedAt(), str, 27, this.deviceId, new BasePetkitDeviceDatePickerView.OnCalendarChangeListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$$ExternalSyntheticLambda47
                @Override // com.petkit.android.activities.petkitBleDevice.widget.datepicker.BasePetkitDeviceDatePickerView.OnCalendarChangeListener
                public final void pageChange(int i) {
                    this.f$0.lambda$showCalendarDialog$41(i);
                }
            }, true, list, new BasePetkitDeviceDatePickerView.OnCalendarSelectListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$$ExternalSyntheticLambda48
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

    public /* synthetic */ void lambda$showCalendarDialog$41(int i) {
        this.monthOffset = i;
        ((T6HomePresenter) this.mPresenter).getDataPicker(i);
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T6HomeContract.View
    public void showEventListNum(T6EventStaticInfo t6EventStaticInfo) {
        if (t6EventStaticInfo == null) {
            return;
        }
        this.tvTabAll.setText(getListNumStr(getResources().getString(R.string.Whole), t6EventStaticInfo.getTotal()));
        this.tvToiletTab.setText(getListNumStr(getResources().getString(R.string.Go_to_the_toilet), t6EventStaticInfo.getToilet()));
        this.tvOtherTab.setText(getListNumStr(getResources().getString(R.string.Pet), t6EventStaticInfo.getPet()));
        this.toiletErrorEventNum = t6EventStaticInfo.getUnusualToilet();
        this.tvToiletTabError.setText(getListNumStr(getResources().getString(R.string.Unusual_event_title), t6EventStaticInfo.getUnusualToilet()));
        this.tvTabAll2.setText(getListNumStr(getResources().getString(R.string.Whole), t6EventStaticInfo.getTotal()));
        this.tvToiletTab2.setText(getListNumStr(getResources().getString(R.string.Go_to_the_toilet), t6EventStaticInfo.getToilet()));
        this.tvOtherTab2.setText(getListNumStr(getResources().getString(R.string.Pet), t6EventStaticInfo.getPet()));
        this.tvToiletTabError2.setText(getListNumStr(getResources().getString(R.string.Unusual_event_title), t6EventStaticInfo.getUnusualToilet()));
        setTextUi(this.currentPosition, this.tvTabAll, this.tvToiletTab, this.tvOtherTab, this.tvToiletTabError);
        setTopTextUi(this.currentPosition, this.tvTabAll2, this.tvToiletTab2, this.tvOtherTab2, this.tvToiletTabError2);
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
            ((T6HomePresenter) this.mPresenter).initT6DeviceDetail();
        }
    }

    @Subscriber
    public void deleteEvent(DeleteEvent deleteEvent) {
        if (deleteEvent == null || deleteEvent.getEventId().equals("")) {
            return;
        }
        ((T6HomePresenter) this.mPresenter).getListNum(this.calendarTime);
        if (this.allFragment.isAdded()) {
            this.allFragment.refreshAdapter(deleteEvent.getEventId(), deleteEvent.getEventType());
        }
        if (this.toiletFragment.isAdded()) {
            this.toiletFragment.refreshAdapter(deleteEvent.getEventId(), deleteEvent.getEventType());
        }
        if (this.petFragment.isAdded()) {
            this.petFragment.refreshAdapter(deleteEvent.getEventId(), deleteEvent.getEventType());
        }
        if (this.petFragment.isAdded()) {
            this.toiletErrorFragment.refreshAdapter(deleteEvent.getEventId(), deleteEvent.getEventType());
        }
        if (deleteEvent.getEventType() == 10) {
            ((T6HomePresenter) this.mPresenter).getStatistic(this.calendarTime, 1);
        }
    }

    @Subscriber
    public void changePet(UpdatePetEvent updatePetEvent) {
        if (updatePetEvent == null) {
            return;
        }
        if (updatePetEvent.getBatch().equals("1")) {
            if (this.allFragment.isAdded()) {
                this.allFragment.refreshEvent(this.calendarTime);
            }
            if (this.toiletFragment.isAdded()) {
                this.toiletFragment.refreshEvent(this.calendarTime);
            }
            if (this.petFragment.isAdded() && updatePetEvent.isAppear()) {
                this.petFragment.refreshEvent(this.calendarTime);
                return;
            }
            return;
        }
        if (this.allFragment.isAdded()) {
            this.allFragment.updateOnePet(updatePetEvent.getOldPetId(), updatePetEvent.getPetId(), updatePetEvent.getEventId());
        }
        if (this.toiletFragment.isAdded()) {
            this.toiletFragment.updateOnePet(updatePetEvent.getOldPetId(), updatePetEvent.getPetId(), updatePetEvent.getEventId());
        }
        if (this.petFragment.isAdded() && updatePetEvent.isAppear()) {
            this.petFragment.updateOnePet(updatePetEvent.getOldPetId(), updatePetEvent.getPetId(), updatePetEvent.getEventId());
        }
    }

    @Subscriber
    public void updatePetSubEvent(UpdatePetCleanEvent updatePetCleanEvent) {
        if (updatePetCleanEvent != null) {
            if (this.toiletFragment.isAdded()) {
                this.toiletFragment.updateOnePetByClean(updatePetCleanEvent);
            }
            if (this.allFragment.isAdded()) {
                this.allFragment.updateOnePetByClean(updatePetCleanEvent);
            }
        }
    }

    @Override // android.app.Activity
    public void onRestart() {
        P p;
        super.onRestart();
        if (CommonUtil.getLong(this.t6Record.getFirmware()) < 600.0d || this.refreshSd || (p = this.mPresenter) == 0 || ((T6HomePresenter) p).getLiveService() == null) {
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
        if (((T6HomePresenter) this.mPresenter).getLiveService() instanceof RtcService) {
            ((RtcService) ((T6HomePresenter) this.mPresenter).getLiveService()).onRestart(this.t6LiveVideoView.getVideoPlayerView().getPlayerView().getTextureView());
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStop() {
        super.onStop();
        if (BaseApplication.isForeground()) {
            this.isForeground = true;
        }
        if (CommonUtil.getLong(this.t6Record.getFirmware()) >= 600.0d && !this.clickIntoFullscreen) {
            if (this.refreshSd) {
                this.refreshSd = false;
                return;
            }
            P p = this.mPresenter;
            if (p == 0 || ((T6HomePresenter) p).getLiveService() == null || !(((T6HomePresenter) this.mPresenter).getLiveService() instanceof RtcService)) {
                return;
            }
            ((RtcService) ((T6HomePresenter) this.mPresenter).getLiveService()).stop();
        }
    }

    @Override // com.jess.arms.base.BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        P p = this.mPresenter;
        if (p != 0 && ((T6HomePresenter) p).getLiveService() != null) {
            if (CommonUtil.getLong(this.t6Record.getFirmware()) < 600.0d) {
                ((T6HomePresenter) this.mPresenter).getLiveService().stopP2PService();
            } else {
                ((RtcService) ((T6HomePresenter) this.mPresenter).getLiveService()).destroy();
            }
        }
        T6LiveViewPagerAdapter t6LiveViewPagerAdapter = this.t6LiveViewPagerAdapter;
        if (t6LiveViewPagerAdapter != null) {
            t6LiveViewPagerAdapter.isNeedShowAni = false;
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
        launchActivity(BleDeviceWifiSettingActivity.newIntent(this, this.t6Record.getDeviceId(), 27));
    }

    @Override // com.petkit.android.activities.petkitBleDevice.widget.BleDeviceHomeOfflineWarnWindow.OfflineClickListener
    public void onClickReset() {
        launchActivity(BleDeviceBindNetWorkActivity.newIntent((Context) this, this.t6Record.getDeviceId(), 27, this.t6Record.getBtMac(), false));
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.T6LiveVideoView.T6LiveVideoViewStateListener
    public void turnOnCamera() {
        if (this.t6Record.getSettings().getHomeMode() == 1 && this.t6Record.getSettings().getCameraOff() == 1) {
            openHomeModeTipWindow(getString(R.string.Home_mode_can_not_open_camera_tip));
        } else {
            ((T6HomePresenter) this.mPresenter).turnOnCamera();
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.T6LiveVideoView.T6LiveVideoViewStateListener
    public void turnOnCamera5Minutes() {
        if (this.t6Record.getSettings().getHomeMode() == 1 && this.t6Record.getSettings().getCameraOff() == 1) {
            openHomeModeTipWindow(getString(R.string.Home_mode_can_not_open_camera_tip));
        } else {
            ((T6HomePresenter) this.mPresenter).temporaryOpenCamera();
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.T6LiveVideoView.T6LiveVideoViewStateListener
    public void startLiveVideo() {
        this.clEyeAnimBackground.setVisibility(0);
        ((T6HomePresenter) this.mPresenter).startLiveVideo(this.t6LiveVideoView.getVideoPlayerView());
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.T6LiveVideoView.T6LiveVideoViewStateListener
    public void normalLive() {
        if (CommonUtil.getLong(this.t6Record.getFirmware()) < 600.0d) {
            if (CommonUtil.isActivityForeground(this, getClass().getName())) {
                ((T6HomePresenter) this.mPresenter).startLiveVideo(this.t6LiveVideoView.getVideoPlayerView());
                return;
            }
            return;
        }
        ((T6HomePresenter) this.mPresenter).firstConnectRtc(this.t6LiveVideoView.getVideoPlayerView());
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.T6LiveVideoView.T6LiveVideoViewStateListener
    public void liveRetry() {
        this.t6LiveVideoView.getVideoPlayerView().showLoadingView();
        ((T6HomePresenter) this.mPresenter).startLiveVideo(this.t6LiveVideoView.getVideoPlayerView());
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.T6LiveVideoView.T6LiveVideoViewStateListener
    public void stopLiveVideo() {
        ((T6HomePresenter) this.mPresenter).stopLiveVideo();
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.T6LiveVideoView.T6LiveVideoViewStateListener
    public void offlineOnClick() {
        onVideoClick();
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.VideoPlayerViewListener
    public void onVideoClick() {
        if (this.t6Record != null) {
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
        translateAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.43
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
            }

            public AnonymousClass43() {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                T6HomeActivity.this.isHidePromote = true;
            }
        });
        this.promoteView.startAnimation(translateAnimation);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$43 */
    public class AnonymousClass43 implements Animation.AnimationListener {
        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationRepeat(Animation animation) {
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationStart(Animation animation) {
        }

        public AnonymousClass43() {
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationEnd(Animation animation) {
            T6HomeActivity.this.isHidePromote = true;
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
        translateAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.44
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
            }

            public AnonymousClass44() {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                T6HomeActivity.this.startCollAnim = false;
                T6HomeActivity.this.startExpandAnim = false;
                T6HomeActivity.this.isHidePromote = false;
            }
        });
        this.promoteView.startAnimation(translateAnimation);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$44 */
    public class AnonymousClass44 implements Animation.AnimationListener {
        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationRepeat(Animation animation) {
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationStart(Animation animation) {
        }

        public AnonymousClass44() {
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationEnd(Animation animation) {
            T6HomeActivity.this.startCollAnim = false;
            T6HomeActivity.this.startExpandAnim = false;
            T6HomeActivity.this.isHidePromote = false;
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$45 */
    public class AnonymousClass45 implements TipWindow.TipClickListener {
        public AnonymousClass45() {
        }

        @Override // com.petkit.android.activities.home.widget.TipWindow.TipClickListener
        public void onCancelClick() {
            T6HomeActivity.this.enableAutoCleanWindow.dismiss();
        }

        @Override // com.petkit.android.activities.home.widget.TipWindow.TipClickListener
        public void onConfirmClick() {
            T6HomeActivity.this.enableAutoCleanWindow.dismiss();
            ((T6HomePresenter) ((BaseActivity) T6HomeActivity.this).mPresenter).updateDeviceSettings("autoWork", true);
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T6HomeContract.View
    public void showEnableAutoCleanWindow() {
        TipWindow tipWindow = this.enableAutoCleanWindow;
        if (tipWindow == null || !tipWindow.isShowing()) {
            TipWindow tipWindow2 = new TipWindow(this, getResources().getString(R.string.Enable_auto_clean_prompt), new TipWindow.TipClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.45
                public AnonymousClass45() {
                }

                @Override // com.petkit.android.activities.home.widget.TipWindow.TipClickListener
                public void onCancelClick() {
                    T6HomeActivity.this.enableAutoCleanWindow.dismiss();
                }

                @Override // com.petkit.android.activities.home.widget.TipWindow.TipClickListener
                public void onConfirmClick() {
                    T6HomeActivity.this.enableAutoCleanWindow.dismiss();
                    ((T6HomePresenter) ((BaseActivity) T6HomeActivity.this).mPresenter).updateDeviceSettings("autoWork", true);
                }
            });
            this.enableAutoCleanWindow = tipWindow2;
            tipWindow2.setConfirmColor(getResources().getColor(R.color.login_new_blue));
            this.enableAutoCleanWindow.setCancelColor(getResources().getColor(R.color.gray));
            this.enableAutoCleanWindow.show(getWindow().getDecorView());
        }
    }

    private void setChartData() {
        T6Record t6Record = this.t6Record;
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
        this.vp.setAdapter(new PagerAdapter() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.46
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

            public AnonymousClass46(RelativeLayout relativeLayout2) {
                relativeLayout = relativeLayout2;
            }

            @Override // androidx.viewpager.widget.PagerAdapter
            @androidx.annotation.NonNull
            public Object instantiateItem(@androidx.annotation.NonNull ViewGroup viewGroup, int i) {
                viewGroup.addView(relativeLayout);
                return relativeLayout;
            }
        });
        this.vp.setDragListener(new MyChartViewPager.DragListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.47
            public AnonymousClass47() {
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.MyChartViewPager.DragListener
            public void dragLeft() {
                T6HomeActivity.this.dragChart(-1);
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.MyChartViewPager.DragListener
            public void dragRight() {
                if (DateUtil.isSameDay(T6HomeActivity.this.calendarTime, T6HomeActivity.this.t6Record.getActualTimeZone())) {
                    return;
                }
                T6HomeActivity.this.dragChart(1);
            }
        });
        dragChart(0);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$46 */
    public class AnonymousClass46 extends PagerAdapter {
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

        public AnonymousClass46(RelativeLayout relativeLayout2) {
            relativeLayout = relativeLayout2;
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        @androidx.annotation.NonNull
        public Object instantiateItem(@androidx.annotation.NonNull ViewGroup viewGroup, int i) {
            viewGroup.addView(relativeLayout);
            return relativeLayout;
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$47 */
    public class AnonymousClass47 implements MyChartViewPager.DragListener {
        public AnonymousClass47() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.MyChartViewPager.DragListener
        public void dragLeft() {
            T6HomeActivity.this.dragChart(-1);
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.MyChartViewPager.DragListener
        public void dragRight() {
            if (DateUtil.isSameDay(T6HomeActivity.this.calendarTime, T6HomeActivity.this.t6Record.getActualTimeZone())) {
                return;
            }
            T6HomeActivity.this.dragChart(1);
        }
    }

    public void dragChart(int i) {
        this.chartView.removeAllViews();
        this.calendarTime = DateUtil.setOffSetDaysTime(this.calendarTime, i, this.t6Record.getActualTimeZone());
        this.ivEventAnim.setVisibility(0);
        this.t6AnimUtil.startRotateAnim(this.ivEventAnim);
        ((T6HomePresenter) this.mPresenter).getStatistic(this.calendarTime, 1);
        if (i != 0) {
            this.ivLeftArrow.setVisibility(8);
        }
    }

    @SuppressLint({"ClickableViewAccessibility"})
    private void getChartData(RelativeLayout relativeLayout, List<T6StatisticResInfo> list) {
        String petName;
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
            if (petById != null) {
                petName = petById.getName();
            } else {
                petName = t6StatisticResInfo.getPetName();
            }
            imageView.setImageResource(getWaterRes(t6StatisticResInfo.getPetId(), petName));
            RelativeLayout relativeLayout3 = (RelativeLayout) relativeLayout2.getChildAt(0);
            ((TextView) relativeLayout3.getChildAt(0)).setBackgroundColor(Color.parseColor(getRecColor(t6StatisticResInfo.getPetId(), petName)));
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
            relativeLayout2.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$$ExternalSyntheticLambda51
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$getChartData$42(t6StaticInfo, arrayList, layoutParams2, view);
                }
            });
            relativeLayout2.setOnTouchListener(new View.OnTouchListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$$ExternalSyntheticLambda52
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    return this.f$0.lambda$getChartData$43(t6StaticInfo, arrayList, layoutParams2, view, motionEvent);
                }
            });
        }
    }

    public /* synthetic */ void lambda$getChartData$42(T6StaticInfo t6StaticInfo, List list, RelativeLayout.LayoutParams layoutParams, View view) {
        int top = (((view.getTop() + this.rlChart.getTop()) + this.llDay.getTop()) - Math.abs(this.appBarOffset)) + AppUtils.dp2px(this, 66.0f);
        this.sortEventList = getSortList(t6StaticInfo, list);
        showArrowPop(layoutParams, top, t6StaticInfo);
    }

    public /* synthetic */ boolean lambda$getChartData$43(T6StaticInfo t6StaticInfo, List list, RelativeLayout.LayoutParams layoutParams, View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() != 0) {
            return false;
        }
        int top = (((view.getTop() + this.rlChart.getTop()) + this.llDay.getTop()) - Math.abs(this.appBarOffset)) + AppUtils.dp2px(this, 66.0f);
        this.sortEventList = getSortList(t6StaticInfo, list);
        showArrowPop(layoutParams, top, t6StaticInfo);
        return true;
    }

    private void showArrowPop(RelativeLayout.LayoutParams layoutParams, int i, T6StaticInfo t6StaticInfo) {
        setPopEventInfo(t6StaticInfo.getInfo());
        startPopAnim();
        this.rlPopContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.48
            final /* synthetic */ RelativeLayout.LayoutParams val$p;
            final /* synthetic */ int val$top;

            public AnonymousClass48(RelativeLayout.LayoutParams layoutParams2, int i2) {
                layoutParams = layoutParams2;
                i = i2;
            }

            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                T6HomeActivity.this.rlPopContent.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                T6HomeActivity.this.rlPopContent.measure(0, 0);
                T6HomeActivity t6HomeActivity = T6HomeActivity.this;
                t6HomeActivity.rlPopWidth = t6HomeActivity.rlPopContent.getMeasuredWidth();
                T6HomeActivity t6HomeActivity2 = T6HomeActivity.this;
                t6HomeActivity2.rlPopHeight = t6HomeActivity2.rlPopContent.getMeasuredHeight();
                RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) T6HomeActivity.this.ivArrow.getLayoutParams();
                layoutParams2.leftMargin = layoutParams.leftMargin + AppUtils.dp2px(T6HomeActivity.this, 68.0f);
                layoutParams2.topMargin = (i - AppUtils.dp2px(T6HomeActivity.this, 2.0f)) + 1;
                T6HomeActivity.this.ivArrow.setLayoutParams(layoutParams2);
                RelativeLayout.LayoutParams layoutParams3 = (RelativeLayout.LayoutParams) T6HomeActivity.this.rlPopContent.getLayoutParams();
                int widthPixels = layoutParams.leftMargin;
                if (AppUtils.dp2px(T6HomeActivity.this, 68.0f) + widthPixels >= T6HomeActivity.this.rlPopWidth / 2) {
                    if (T6HomeActivity.this.rlPopWidth + widthPixels > CommonUtil.getWidthPixels(T6HomeActivity.this) - AppUtils.dp2px(T6HomeActivity.this, 18.0f)) {
                        widthPixels = (CommonUtil.getWidthPixels(T6HomeActivity.this) - AppUtils.dp2px(T6HomeActivity.this, 18.0f)) - T6HomeActivity.this.rlPopWidth;
                    }
                } else {
                    widthPixels = AppUtils.dp2px(T6HomeActivity.this, 18.0f);
                }
                layoutParams3.leftMargin = widthPixels;
                layoutParams3.topMargin = (i - T6HomeActivity.this.rlPopHeight) + AppUtils.dp2px(T6HomeActivity.this, 6.0f);
                T6HomeActivity.this.rlPopContent.setLayoutParams(layoutParams3);
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$48 */
    public class AnonymousClass48 implements ViewTreeObserver.OnGlobalLayoutListener {
        final /* synthetic */ RelativeLayout.LayoutParams val$p;
        final /* synthetic */ int val$top;

        public AnonymousClass48(RelativeLayout.LayoutParams layoutParams2, int i2) {
            layoutParams = layoutParams2;
            i = i2;
        }

        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public void onGlobalLayout() {
            T6HomeActivity.this.rlPopContent.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            T6HomeActivity.this.rlPopContent.measure(0, 0);
            T6HomeActivity t6HomeActivity = T6HomeActivity.this;
            t6HomeActivity.rlPopWidth = t6HomeActivity.rlPopContent.getMeasuredWidth();
            T6HomeActivity t6HomeActivity2 = T6HomeActivity.this;
            t6HomeActivity2.rlPopHeight = t6HomeActivity2.rlPopContent.getMeasuredHeight();
            RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) T6HomeActivity.this.ivArrow.getLayoutParams();
            layoutParams2.leftMargin = layoutParams.leftMargin + AppUtils.dp2px(T6HomeActivity.this, 68.0f);
            layoutParams2.topMargin = (i - AppUtils.dp2px(T6HomeActivity.this, 2.0f)) + 1;
            T6HomeActivity.this.ivArrow.setLayoutParams(layoutParams2);
            RelativeLayout.LayoutParams layoutParams3 = (RelativeLayout.LayoutParams) T6HomeActivity.this.rlPopContent.getLayoutParams();
            int widthPixels = layoutParams.leftMargin;
            if (AppUtils.dp2px(T6HomeActivity.this, 68.0f) + widthPixels >= T6HomeActivity.this.rlPopWidth / 2) {
                if (T6HomeActivity.this.rlPopWidth + widthPixels > CommonUtil.getWidthPixels(T6HomeActivity.this) - AppUtils.dp2px(T6HomeActivity.this, 18.0f)) {
                    widthPixels = (CommonUtil.getWidthPixels(T6HomeActivity.this) - AppUtils.dp2px(T6HomeActivity.this, 18.0f)) - T6HomeActivity.this.rlPopWidth;
                }
            } else {
                widthPixels = AppUtils.dp2px(T6HomeActivity.this, 18.0f);
            }
            layoutParams3.leftMargin = widthPixels;
            layoutParams3.topMargin = (i - T6HomeActivity.this.rlPopHeight) + AppUtils.dp2px(T6HomeActivity.this, 6.0f);
            T6HomeActivity.this.rlPopContent.setLayoutParams(layoutParams3);
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
        T6Record t6Record = this.t6Record;
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
        this.rlPopImage.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$$ExternalSyntheticLambda53
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$setPopEventInfo$44(t6StatisticResInfo, view);
            }
        });
        if (isExpireService(t6StatisticResInfo.getTimestamp())) {
            this.ivChartPlay.setVisibility(0);
        } else if (t6StatisticResInfo.getContent() != null && DateUtil.isSameDay(t6StatisticResInfo.getContent().getStartTime() * 1000, this.t6Record.getActualTimeZone())) {
            this.ivChartPlay.setVisibility(0);
        } else {
            this.ivChartPlay.setVisibility(8);
        }
        new ImageCacheSimple(CommonUtils.getAppContext()).getImageCachePath(CommonUtil.httpToHttps(t6StatisticResInfo.getPreview()), new ImageCacheSimple.IGetImageCacheListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$$ExternalSyntheticLambda54
            @Override // com.jess.arms.widget.imageloader.ImageCacheSimple.IGetImageCacheListener
            public final void onCachePath(String str) {
                this.f$0.lambda$setPopEventInfo$45(t6StatisticResInfo, str);
            }
        });
    }

    public /* synthetic */ void lambda$setPopEventInfo$44(T6StatisticResInfo t6StatisticResInfo, View view) {
        jumpToPlayerDetail(t6StatisticResInfo);
    }

    public /* synthetic */ void lambda$setPopEventInfo$45(T6StatisticResInfo t6StatisticResInfo, String str) {
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
        boolean z2 = this.t6Record.getCloudProduct() == null || (this.t6Record.getCloudProduct().getWorkIndate() != null && j > Long.parseLong(this.t6Record.getCloudProduct().getWorkIndate()));
        if (this.t6Record.getServiceStatus() == 0 || (this.t6Record.getServiceStatus() == 2 && z2)) {
            z = true;
        }
        return !z;
    }

    private void jumpToPlayerDetail(T6StatisticResInfo t6StatisticResInfo) {
        T6EventInfo t6EventInfo = new T6EventInfo();
        t6EventInfo.setDeviceId(this.deviceId);
        t6EventInfo.setPetId(t6StatisticResInfo.getPetId());
        t6EventInfo.setPreview(t6StatisticResInfo.getPreview());
        t6EventInfo.setMediaApi(t6StatisticResInfo.getMediaApi());
        t6EventInfo.setAesKey(t6StatisticResInfo.getAesKey());
        t6EventInfo.setDuration(t6StatisticResInfo.getDuration());
        t6EventInfo.setExpire(t6StatisticResInfo.getExpire());
        t6EventInfo.setEventId(t6StatisticResInfo.getEventId());
        if (t6StatisticResInfo.getContent() != null) {
            t6EventInfo.setMark(t6StatisticResInfo.getContent().getMark());
            t6StatisticResInfo.getContent().setStartTime(t6StatisticResInfo.getContent().getTimeIn());
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
            this.petList = FamilyUtils.getInstance().getCatListByFamilyId(this, FamilyUtils.getInstance().getFamilyInforThroughDevice(this, this.deviceId, 27).getGroupId());
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
            PetFilterWindow petFilterWindow2 = new PetFilterWindow(this, getResources().getString(R.string.Cancel), getResources().getString(R.string.OK), "", this.petList.size() == 0 ? "" : getResources().getString(R.string.Change_color), getResources().getString(R.string.Choose_pets), this.petList, new BaseBottomWindow.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.49
                @Override // com.petkit.android.activities.universalWindow.BaseBottomWindow.OnClickListener
                public void onTitleBtn() {
                }

                @Override // com.petkit.android.activities.universalWindow.BaseBottomWindow.OnClickListener
                public void onTopLeftBtn() {
                }

                public AnonymousClass49() {
                }

                @Override // com.petkit.android.activities.universalWindow.BaseBottomWindow.OnClickListener
                public void onBtnLeft() {
                    T6HomeActivity.this.petFilterWindow.resetSelectedState();
                    T6HomeActivity.this.petFilterWindow.dismiss();
                    T6HomeActivity t6HomeActivity = T6HomeActivity.this;
                    t6HomeActivity.selectedPetIds = t6HomeActivity.petFilterWindow.getSelectedPetIds();
                }

                @Override // com.petkit.android.activities.universalWindow.BaseBottomWindow.OnClickListener
                public void onBtnRight() {
                    T6HomeActivity.this.petFilterWindow.updateSelectedState();
                    T6HomeActivity.this.petFilterWindow.dismiss();
                    T6HomeActivity t6HomeActivity = T6HomeActivity.this;
                    t6HomeActivity.selectedPetIds = t6HomeActivity.petFilterWindow.getSelectedPetIds();
                    T6HomeActivity.this.showSelectPet();
                    T6HomeActivity.this.refreshPetChart();
                }

                @Override // com.petkit.android.activities.universalWindow.BaseBottomWindow.OnClickListener
                public void onTopRightBtn() {
                    T6HomeActivity t6HomeActivity = T6HomeActivity.this;
                    t6HomeActivity.startActivity(PetColorSettingActivity.newIntent(t6HomeActivity, t6HomeActivity.petList));
                }
            });
            this.petFilterWindow = petFilterWindow2;
            petFilterWindow2.show(getWindow().getDecorView());
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$49 */
    public class AnonymousClass49 implements BaseBottomWindow.OnClickListener {
        @Override // com.petkit.android.activities.universalWindow.BaseBottomWindow.OnClickListener
        public void onTitleBtn() {
        }

        @Override // com.petkit.android.activities.universalWindow.BaseBottomWindow.OnClickListener
        public void onTopLeftBtn() {
        }

        public AnonymousClass49() {
        }

        @Override // com.petkit.android.activities.universalWindow.BaseBottomWindow.OnClickListener
        public void onBtnLeft() {
            T6HomeActivity.this.petFilterWindow.resetSelectedState();
            T6HomeActivity.this.petFilterWindow.dismiss();
            T6HomeActivity t6HomeActivity = T6HomeActivity.this;
            t6HomeActivity.selectedPetIds = t6HomeActivity.petFilterWindow.getSelectedPetIds();
        }

        @Override // com.petkit.android.activities.universalWindow.BaseBottomWindow.OnClickListener
        public void onBtnRight() {
            T6HomeActivity.this.petFilterWindow.updateSelectedState();
            T6HomeActivity.this.petFilterWindow.dismiss();
            T6HomeActivity t6HomeActivity = T6HomeActivity.this;
            t6HomeActivity.selectedPetIds = t6HomeActivity.petFilterWindow.getSelectedPetIds();
            T6HomeActivity.this.showSelectPet();
            T6HomeActivity.this.refreshPetChart();
        }

        @Override // com.petkit.android.activities.universalWindow.BaseBottomWindow.OnClickListener
        public void onTopRightBtn() {
            T6HomeActivity t6HomeActivity = T6HomeActivity.this;
            t6HomeActivity.startActivity(PetColorSettingActivity.newIntent(t6HomeActivity, t6HomeActivity.petList));
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
        if (this.allFragment.isAdded()) {
            this.allFragment.refreshPetColor();
        }
    }

    private void showKittenProtectDialog() {
        List<Pet> kittens;
        if (this.t6Record.getState().getOverall() == 2 || this.t6Record.getState().getErrorLevel() == 1 || this.t6Record.getDeviceShared() != null) {
            if (!DataHelper.getBooleanSF(this, Constants.T6_BIND_SERVICE_PROMPT + this.deviceId) || this.t6Record.getRealDeviceShared() != null || FamilyUtils.getInstance().checkIsSharedDevice(this, this.t6Record.getDeviceId(), this.deviceType)) {
                ((T6HomePresenter) this.mPresenter).checkInitWindow(6);
                return;
            } else {
                ((T6HomePresenter) this.mPresenter).checkInitWindow(5);
                return;
            }
        }
        boolean booleanSF = DataHelper.getBooleanSF(this, this.familyInfor.getGroupId() + Constants.T6_KITTEN_PROTECTION_NEED_GUIDE_FLAG);
        LogcatStorageHelper.addLog("t6ProtectFlag:" + booleanSF);
        if (!booleanSF) {
            if (this.t6Record.getSettings().getKitten() == 1) {
                DataHelper.setBooleanSF(this, this.familyInfor.getGroupId() + Constants.T6_KITTEN_PROTECTION_NEED_GUIDE_FLAG, Boolean.TRUE);
            } else {
                List<Pet> kittens2 = BleDeviceUtils.getKittens(FamilyUtils.getInstance().getFamilyInforThroughDevice(this, this.deviceId, this.deviceType));
                if (kittens2 != null && kittens2.size() > 0) {
                    showKittenDialog(kittens2);
                    return;
                }
            }
        } else {
            boolean booleanSF2 = DataHelper.getBooleanSF(this, Constants.T6_KITTEN_PROTECTION_NEED_GUIDE_SECOND_FLAG);
            boolean zSevenDaysBetween = BleDeviceUtils.sevenDaysBetween(this.t6Record.getSettings().getKittenTipsTime());
            LogcatStorageHelper.addLog("t6,reshowFlag:" + booleanSF2 + ",sevenDaysBetween:" + zSevenDaysBetween);
            if (booleanSF2 && zSevenDaysBetween && this.t6Record.getSettings().getKitten() != 1 && (kittens = BleDeviceUtils.getKittens(FamilyUtils.getInstance().getFamilyInforThroughDevice(this, this.deviceId, this.deviceType))) != null && kittens.size() > 0) {
                showKittenDialog(kittens);
                return;
            }
        }
        if (!DataHelper.getBooleanSF(this, Constants.T6_BIND_SERVICE_PROMPT + this.deviceId) || this.t6Record.getRealDeviceShared() != null || FamilyUtils.getInstance().checkIsSharedDevice(this, this.t6Record.getDeviceId(), this.deviceType)) {
            ((T6HomePresenter) this.mPresenter).checkInitWindow(6);
        } else {
            ((T6HomePresenter) this.mPresenter).checkInitWindow(5);
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$50 */
    public class AnonymousClass50 implements T6KittenProtectionDialog.OnOpenKittenProtectionListener {
        public AnonymousClass50() {
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
                com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity r0 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.this
                com.petkit.android.activities.petkitBleDevice.t6.widget.T6KittenProtectionDialog r0 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.access$6100(r0)
                r0.dismiss()
                com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity r0 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.this
                java.lang.StringBuilder r1 = new java.lang.StringBuilder
                r1.<init>()
                java.lang.String r2 = "com.petkit.android.T6_BIND_SERVICE_PROMPT_"
                r1.append(r2)
                com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity r2 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.this
                long r2 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.access$000(r2)
                r1.append(r2)
                java.lang.String r1 = r1.toString()
                boolean r0 = com.jess.arms.utils.DataHelper.getBooleanSF(r0, r1)
                if (r0 == 0) goto L60
                com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity r0 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.this
                com.petkit.android.activities.petkitBleDevice.t6.mode.T6Record r0 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.access$300(r0)
                com.petkit.android.model.DeviceShared r0 = r0.getRealDeviceShared()
                if (r0 != 0) goto L60
                com.petkit.android.activities.petkitBleDevice.utils.FamilyUtils r0 = com.petkit.android.activities.petkitBleDevice.utils.FamilyUtils.getInstance()
                com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity r1 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.this
                com.petkit.android.activities.petkitBleDevice.t6.mode.T6Record r2 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.access$300(r1)
                long r2 = r2.getDeviceId()
                com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity r4 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.this
                int r4 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.access$200(r4)
                boolean r0 = r0.checkIsSharedDevice(r1, r2, r4)
                if (r0 == 0) goto L4f
                goto L60
            L4f:
                com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity r0 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.this
                com.jess.arms.mvp.IPresenter r0 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.access$6300(r0)
                com.petkit.android.activities.petkitBleDevice.t6.presenter.T6HomePresenter r0 = (com.petkit.android.activities.petkitBleDevice.t6.presenter.T6HomePresenter) r0
                r1 = 5
                int[] r1 = new int[]{r1}
                r0.checkInitWindow(r1)
                goto L70
            L60:
                com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity r0 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.this
                com.jess.arms.mvp.IPresenter r0 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.access$6200(r0)
                com.petkit.android.activities.petkitBleDevice.t6.presenter.T6HomePresenter r0 = (com.petkit.android.activities.petkitBleDevice.t6.presenter.T6HomePresenter) r0
                r1 = 6
                int[] r1 = new int[]{r1}
                r0.checkInitWindow(r1)
            L70:
                com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity r0 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.this
                java.lang.StringBuilder r1 = new java.lang.StringBuilder
                r1.<init>()
                com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity r2 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.this
                com.petkit.android.activities.family.mode.FamilyInfor r2 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.access$6400(r2)
                long r2 = r2.getGroupId()
                r1.append(r2)
                java.lang.String r2 = "T6_KITTEN_PROTECTION_NEED_GUIDE_FLAG"
                r1.append(r2)
                java.lang.String r1 = r1.toString()
                java.lang.Boolean r2 = java.lang.Boolean.TRUE
                com.jess.arms.utils.DataHelper.setBooleanSF(r0, r1, r2)
                com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity r0 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.this
                com.jess.arms.mvp.IPresenter r0 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.access$6500(r0)
                com.petkit.android.activities.petkitBleDevice.t6.presenter.T6HomePresenter r0 = (com.petkit.android.activities.petkitBleDevice.t6.presenter.T6HomePresenter) r0
                r0.enableKittenProtection()
                com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity r0 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.this
                java.lang.Boolean r1 = java.lang.Boolean.FALSE
                java.lang.String r2 = "T6_KITTEN_PROTECTION_NEED_GUIDE_SECOND_FLAG"
                com.jess.arms.utils.DataHelper.setBooleanSF(r0, r2, r1)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.AnonymousClass50.onOpen():void");
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
                com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity r0 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.this
                com.petkit.android.activities.petkitBleDevice.t6.widget.T6KittenProtectionDialog r0 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.access$6100(r0)
                r0.dismiss()
                com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity r0 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.this
                java.lang.StringBuilder r1 = new java.lang.StringBuilder
                r1.<init>()
                java.lang.String r2 = "com.petkit.android.T6_BIND_SERVICE_PROMPT_"
                r1.append(r2)
                com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity r2 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.this
                long r2 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.access$000(r2)
                r1.append(r2)
                java.lang.String r1 = r1.toString()
                boolean r0 = com.jess.arms.utils.DataHelper.getBooleanSF(r0, r1)
                if (r0 == 0) goto L60
                com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity r0 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.this
                com.petkit.android.activities.petkitBleDevice.t6.mode.T6Record r0 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.access$300(r0)
                com.petkit.android.model.DeviceShared r0 = r0.getRealDeviceShared()
                if (r0 != 0) goto L60
                com.petkit.android.activities.petkitBleDevice.utils.FamilyUtils r0 = com.petkit.android.activities.petkitBleDevice.utils.FamilyUtils.getInstance()
                com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity r1 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.this
                com.petkit.android.activities.petkitBleDevice.t6.mode.T6Record r2 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.access$300(r1)
                long r2 = r2.getDeviceId()
                com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity r4 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.this
                int r4 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.access$200(r4)
                boolean r0 = r0.checkIsSharedDevice(r1, r2, r4)
                if (r0 == 0) goto L4f
                goto L60
            L4f:
                com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity r0 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.this
                com.jess.arms.mvp.IPresenter r0 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.access$6700(r0)
                com.petkit.android.activities.petkitBleDevice.t6.presenter.T6HomePresenter r0 = (com.petkit.android.activities.petkitBleDevice.t6.presenter.T6HomePresenter) r0
                r1 = 5
                int[] r1 = new int[]{r1}
                r0.checkInitWindow(r1)
                goto L70
            L60:
                com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity r0 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.this
                com.jess.arms.mvp.IPresenter r0 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.access$6600(r0)
                com.petkit.android.activities.petkitBleDevice.t6.presenter.T6HomePresenter r0 = (com.petkit.android.activities.petkitBleDevice.t6.presenter.T6HomePresenter) r0
                r1 = 6
                int[] r1 = new int[]{r1}
                r0.checkInitWindow(r1)
            L70:
                com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity r0 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.this
                java.lang.StringBuilder r1 = new java.lang.StringBuilder
                r1.<init>()
                com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity r2 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.this
                com.petkit.android.activities.family.mode.FamilyInfor r2 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.access$6400(r2)
                long r2 = r2.getGroupId()
                r1.append(r2)
                java.lang.String r2 = "T6_KITTEN_PROTECTION_NEED_GUIDE_FLAG"
                r1.append(r2)
                java.lang.String r1 = r1.toString()
                java.lang.Boolean r2 = java.lang.Boolean.TRUE
                com.jess.arms.utils.DataHelper.setBooleanSF(r0, r1, r2)
                com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity r0 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.this
                com.jess.arms.mvp.IPresenter r0 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.access$6800(r0)
                com.petkit.android.activities.petkitBleDevice.t6.presenter.T6HomePresenter r0 = (com.petkit.android.activities.petkitBleDevice.t6.presenter.T6HomePresenter) r0
                r0.updateKittenTipsTime()
                com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity r0 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.this
                java.lang.String r1 = "T6_KITTEN_PROTECTION_NEED_GUIDE_SECOND_FLAG"
                com.jess.arms.utils.DataHelper.setBooleanSF(r0, r1, r2)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.AnonymousClass50.onCancel():void");
        }
    }

    private void showKittenDialog(List<Pet> list) {
        if (this.t6KittenProtectionDialog == null) {
            this.t6KittenProtectionDialog = new T6KittenProtectionDialog(this, list, new T6KittenProtectionDialog.OnOpenKittenProtectionListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.50
                public AnonymousClass50() {
                }

                @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.T6KittenProtectionDialog.OnOpenKittenProtectionListener
                public void onOpen() {
                    /*
                        this = this;
                        com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity r0 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.this
                        com.petkit.android.activities.petkitBleDevice.t6.widget.T6KittenProtectionDialog r0 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.access$6100(r0)
                        r0.dismiss()
                        com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity r0 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.this
                        java.lang.StringBuilder r1 = new java.lang.StringBuilder
                        r1.<init>()
                        java.lang.String r2 = "com.petkit.android.T6_BIND_SERVICE_PROMPT_"
                        r1.append(r2)
                        com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity r2 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.this
                        long r2 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.access$000(r2)
                        r1.append(r2)
                        java.lang.String r1 = r1.toString()
                        boolean r0 = com.jess.arms.utils.DataHelper.getBooleanSF(r0, r1)
                        if (r0 == 0) goto L60
                        com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity r0 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.this
                        com.petkit.android.activities.petkitBleDevice.t6.mode.T6Record r0 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.access$300(r0)
                        com.petkit.android.model.DeviceShared r0 = r0.getRealDeviceShared()
                        if (r0 != 0) goto L60
                        com.petkit.android.activities.petkitBleDevice.utils.FamilyUtils r0 = com.petkit.android.activities.petkitBleDevice.utils.FamilyUtils.getInstance()
                        com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity r1 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.this
                        com.petkit.android.activities.petkitBleDevice.t6.mode.T6Record r2 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.access$300(r1)
                        long r2 = r2.getDeviceId()
                        com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity r4 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.this
                        int r4 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.access$200(r4)
                        boolean r0 = r0.checkIsSharedDevice(r1, r2, r4)
                        if (r0 == 0) goto L4f
                        goto L60
                    L4f:
                        com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity r0 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.this
                        com.jess.arms.mvp.IPresenter r0 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.access$6300(r0)
                        com.petkit.android.activities.petkitBleDevice.t6.presenter.T6HomePresenter r0 = (com.petkit.android.activities.petkitBleDevice.t6.presenter.T6HomePresenter) r0
                        r1 = 5
                        int[] r1 = new int[]{r1}
                        r0.checkInitWindow(r1)
                        goto L70
                    L60:
                        com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity r0 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.this
                        com.jess.arms.mvp.IPresenter r0 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.access$6200(r0)
                        com.petkit.android.activities.petkitBleDevice.t6.presenter.T6HomePresenter r0 = (com.petkit.android.activities.petkitBleDevice.t6.presenter.T6HomePresenter) r0
                        r1 = 6
                        int[] r1 = new int[]{r1}
                        r0.checkInitWindow(r1)
                    L70:
                        com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity r0 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.this
                        java.lang.StringBuilder r1 = new java.lang.StringBuilder
                        r1.<init>()
                        com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity r2 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.this
                        com.petkit.android.activities.family.mode.FamilyInfor r2 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.access$6400(r2)
                        long r2 = r2.getGroupId()
                        r1.append(r2)
                        java.lang.String r2 = "T6_KITTEN_PROTECTION_NEED_GUIDE_FLAG"
                        r1.append(r2)
                        java.lang.String r1 = r1.toString()
                        java.lang.Boolean r2 = java.lang.Boolean.TRUE
                        com.jess.arms.utils.DataHelper.setBooleanSF(r0, r1, r2)
                        com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity r0 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.this
                        com.jess.arms.mvp.IPresenter r0 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.access$6500(r0)
                        com.petkit.android.activities.petkitBleDevice.t6.presenter.T6HomePresenter r0 = (com.petkit.android.activities.petkitBleDevice.t6.presenter.T6HomePresenter) r0
                        r0.enableKittenProtection()
                        com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity r0 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.this
                        java.lang.Boolean r1 = java.lang.Boolean.FALSE
                        java.lang.String r2 = "T6_KITTEN_PROTECTION_NEED_GUIDE_SECOND_FLAG"
                        com.jess.arms.utils.DataHelper.setBooleanSF(r0, r2, r1)
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.AnonymousClass50.onOpen():void");
                }

                @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.T6KittenProtectionDialog.OnOpenKittenProtectionListener
                public void onCancel() {
                    /*
                        this = this;
                        com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity r0 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.this
                        com.petkit.android.activities.petkitBleDevice.t6.widget.T6KittenProtectionDialog r0 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.access$6100(r0)
                        r0.dismiss()
                        com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity r0 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.this
                        java.lang.StringBuilder r1 = new java.lang.StringBuilder
                        r1.<init>()
                        java.lang.String r2 = "com.petkit.android.T6_BIND_SERVICE_PROMPT_"
                        r1.append(r2)
                        com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity r2 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.this
                        long r2 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.access$000(r2)
                        r1.append(r2)
                        java.lang.String r1 = r1.toString()
                        boolean r0 = com.jess.arms.utils.DataHelper.getBooleanSF(r0, r1)
                        if (r0 == 0) goto L60
                        com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity r0 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.this
                        com.petkit.android.activities.petkitBleDevice.t6.mode.T6Record r0 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.access$300(r0)
                        com.petkit.android.model.DeviceShared r0 = r0.getRealDeviceShared()
                        if (r0 != 0) goto L60
                        com.petkit.android.activities.petkitBleDevice.utils.FamilyUtils r0 = com.petkit.android.activities.petkitBleDevice.utils.FamilyUtils.getInstance()
                        com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity r1 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.this
                        com.petkit.android.activities.petkitBleDevice.t6.mode.T6Record r2 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.access$300(r1)
                        long r2 = r2.getDeviceId()
                        com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity r4 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.this
                        int r4 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.access$200(r4)
                        boolean r0 = r0.checkIsSharedDevice(r1, r2, r4)
                        if (r0 == 0) goto L4f
                        goto L60
                    L4f:
                        com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity r0 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.this
                        com.jess.arms.mvp.IPresenter r0 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.access$6700(r0)
                        com.petkit.android.activities.petkitBleDevice.t6.presenter.T6HomePresenter r0 = (com.petkit.android.activities.petkitBleDevice.t6.presenter.T6HomePresenter) r0
                        r1 = 5
                        int[] r1 = new int[]{r1}
                        r0.checkInitWindow(r1)
                        goto L70
                    L60:
                        com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity r0 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.this
                        com.jess.arms.mvp.IPresenter r0 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.access$6600(r0)
                        com.petkit.android.activities.petkitBleDevice.t6.presenter.T6HomePresenter r0 = (com.petkit.android.activities.petkitBleDevice.t6.presenter.T6HomePresenter) r0
                        r1 = 6
                        int[] r1 = new int[]{r1}
                        r0.checkInitWindow(r1)
                    L70:
                        com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity r0 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.this
                        java.lang.StringBuilder r1 = new java.lang.StringBuilder
                        r1.<init>()
                        com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity r2 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.this
                        com.petkit.android.activities.family.mode.FamilyInfor r2 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.access$6400(r2)
                        long r2 = r2.getGroupId()
                        r1.append(r2)
                        java.lang.String r2 = "T6_KITTEN_PROTECTION_NEED_GUIDE_FLAG"
                        r1.append(r2)
                        java.lang.String r1 = r1.toString()
                        java.lang.Boolean r2 = java.lang.Boolean.TRUE
                        com.jess.arms.utils.DataHelper.setBooleanSF(r0, r1, r2)
                        com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity r0 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.this
                        com.jess.arms.mvp.IPresenter r0 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.access$6800(r0)
                        com.petkit.android.activities.petkitBleDevice.t6.presenter.T6HomePresenter r0 = (com.petkit.android.activities.petkitBleDevice.t6.presenter.T6HomePresenter) r0
                        r0.updateKittenTipsTime()
                        com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity r0 = com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.this
                        java.lang.String r1 = "T6_KITTEN_PROTECTION_NEED_GUIDE_SECOND_FLAG"
                        com.jess.arms.utils.DataHelper.setBooleanSF(r0, r1, r2)
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.AnonymousClass50.onCancel():void");
                }
            }, true, 27);
        }
        if (this.t6KittenProtectionDialog.isShowing()) {
            return;
        }
        this.t6KittenProtectionDialog.showAtLocation(getWindow().getDecorView(), 17, 0, 0);
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T6HomeContract.View
    public void enableKittenProtectionSuccess() {
        PetkitToast.showTopToast(this, getResources().getString(R.string.Kitten_protection_is_enabled), R.drawable.top_toast_success_icon, 500);
    }

    private void deviceClose() {
        this.clDevice.setVisibility(0);
    }

    private void deviceOpen() {
        this.clDevice.setVisibility(8);
    }

    private void startDeviceAnim() {
        this.ivDeviceOpen.setVisibility(0);
        this.t6AnimUtil.startAlphaAnim(this.ivDeviceOpen);
        this.isAnimationRunning = true;
    }

    public void endLoadingAnim() {
        this.ivEyeAnim.setVisibility(0);
        this.clEyeAnimBackground.setVisibility(8);
        this.t6AnimUtil.endLoadingAnim(this.clLoading, this.ltLoading);
        ((AnimationDrawable) this.ivEyeAnim.getDrawable()).start();
        this.handler.postDelayed(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$$ExternalSyntheticLambda16
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$endLoadingAnim$46();
            }
        }, 1000L);
    }

    public /* synthetic */ void lambda$endLoadingAnim$46() {
        ImageView imageView;
        if (this.handler == null || (imageView = this.ivEyeAnim) == null) {
            return;
        }
        imageView.setVisibility(8);
    }

    private void unOpenCamera() {
        this.clCamera.setVisibility(0);
    }

    private void openCamera() {
        this.clCamera.setVisibility(8);
    }

    public void startCameraAnim() {
        this.t6AnimUtil.startCameraAnim(this.ivCameraAnim);
        this.isAnimationRunning = true;
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T6HomeContract.View
    public void showPetGuide() {
        T6Record t6Record = this.t6Record;
        if (t6Record != null && t6Record.getDeviceShared() != null) {
            ((T6HomePresenter) this.mPresenter).checkInitWindow(7);
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
            ((T6HomePresenter) this.mPresenter).checkInitWindow(7);
            return;
        }
        ((T6HomePresenter) this.mPresenter).checkInitWindow(7);
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T6HomeContract.View
    public void handleWidget() {
        WidgetDataInfo widgetDataInfo = this.widgetDataInfo;
        if (widgetDataInfo != null) {
            if (widgetDataInfo.getClear() == 1) {
                showCleanUpWindow();
            } else if (this.widgetDataInfo.getCameraSwitch() == 1) {
                launchActivity(T6SettingActivity.newIntent(this, this.deviceId, this.deviceType, this.relatedProductsInfor, true));
            } else if (this.widgetDataInfo.getHomeMode() == 1 && canHomeModeEdit()) {
                if (this.t6Record.getSettings().getCameraInward() == 0 && this.t6Record.getSettings().getCameraOff() == 0 && this.t6Record.getSettings().getNoSound() == 0) {
                    if (this.t6Record.getDeviceShared() == null) {
                        launchActivity(T6InitHomeModeActivity.newIntent(this, this.deviceId, this.t6Record.getTypeCode()));
                    } else {
                        openHomeModeTipWindow(getString(R.string.Shared_device_is_limited_tip));
                    }
                } else if (this.t6Record.getDeviceShared() == null) {
                    if (this.t6Record.getSettings().getHomeMode() == 1) {
                        showLoading();
                        ((T6HomePresenter) this.mPresenter).updateSetting("homeMode", 0);
                    } else if (this.t6Record.getSettings().getCameraConfig() != 1 && this.t6Record.getSettings().getCameraOff() == 1) {
                        openHomeModeTipWindowWithListner(getString(R.string.Home_mode_in_working_time_tip), new NewIKnowWindow.onClickIKnowListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.51
                            public AnonymousClass51() {
                            }

                            @Override // com.petkit.android.activities.petkitBleDevice.r2.widget.NewIKnowWindow.onClickIKnowListener
                            public void onClickIKnow() {
                                T6HomeActivity.this.showLoading();
                                ((T6HomePresenter) ((BaseActivity) T6HomeActivity.this).mPresenter).updateSetting("homeMode", 1);
                            }
                        });
                    } else {
                        showLoading();
                        ((T6HomePresenter) this.mPresenter).updateSetting("homeMode", 1);
                    }
                } else {
                    openHomeModeTipWindow(getString(R.string.Shared_device_is_limited_tip));
                }
            }
            this.widgetDataInfo = null;
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$51 */
    public class AnonymousClass51 implements NewIKnowWindow.onClickIKnowListener {
        public AnonymousClass51() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.r2.widget.NewIKnowWindow.onClickIKnowListener
        public void onClickIKnow() {
            T6HomeActivity.this.showLoading();
            ((T6HomePresenter) ((BaseActivity) T6HomeActivity.this).mPresenter).updateSetting("homeMode", 1);
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$52 */
    public class AnonymousClass52 implements NormalCenterTipWindow.OnClickOk {
        public AnonymousClass52() {
        }

        @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
        public void onOkClick() {
            T6HomeActivity t6HomeActivity = T6HomeActivity.this;
            t6HomeActivity.launchActivity(PetWeightActivity.newIntent(t6HomeActivity, t6HomeActivity.deviceId, T6HomeActivity.this.deviceType));
        }

        @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
        public void onCancelClick() {
            ((T6HomePresenter) ((BaseActivity) T6HomeActivity.this).mPresenter).checkInitWindow(7);
        }
    }

    private void showUploadCatPhotoPop() {
        NormalCenterTipWindow normalCenterTipWindow = new NormalCenterTipWindow(this, new NormalCenterTipWindow.OnClickOk() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.52
            public AnonymousClass52() {
            }

            @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
            public void onOkClick() {
                T6HomeActivity t6HomeActivity = T6HomeActivity.this;
                t6HomeActivity.launchActivity(PetWeightActivity.newIntent(t6HomeActivity, t6HomeActivity.deviceId, T6HomeActivity.this.deviceType));
            }

            @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
            public void onCancelClick() {
                ((T6HomePresenter) ((BaseActivity) T6HomeActivity.this).mPresenter).checkInitWindow(7);
            }
        }, (String) null, getString(R.string.Upload_face_photo_content));
        normalCenterTipWindow.setOkButtonText(getString(R.string.Upload_face_photo));
        normalCenterTipWindow.setCancelButtonText(getString(R.string.Not_upload_now));
        normalCenterTipWindow.show(getWindow().getDecorView());
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T6HomeContract.View
    public void refreshBannerList(D4shBannerData d4shBannerData) {
        T6Record t6Record;
        this.d4shBannerData = d4shBannerData;
        refreshHomeBannerData(d4shBannerData);
        if (TextUtils.isEmpty(CommonUtils.getSysMap(Constants.CLOUD_SERVICE_DELAY_TIME)) || TextUtils.isEmpty(this.t6Record.getCreatedAt())) {
            return;
        }
        try {
            if ((System.currentTimeMillis() - DateUtil.parseISO8601Date(this.t6Record.getCreatedAt()).getTime()) / 1000 <= ((CloudServiceDelayTimeResult) new Gson().fromJson(r0, CloudServiceDelayTimeResult.class)).getDelayTime() || !FamilyUtils.getInstance().isDeviceBelongToMyself(this, this.deviceId).booleanValue() || (t6Record = this.t6Record) == null || t6Record.getRealDeviceShared() != null || this.t6Record.getServiceStatus() != 0 || d4shBannerData.getFreeActivity() == null || d4shBannerData.getFreeActivity().getImageRet() == null) {
                return;
            }
            ((T6HomePresenter) this.mPresenter).getFreePackage(this.deviceId, this.t6Record.getTypeCode() == 0 ? "T6" : "T5");
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
        boolean z2 = this.t6Record.getRealDeviceShared() == null && !FamilyUtils.getInstance().checkIsSharedDevice(this, this.t6Record.getDeviceId(), 27);
        this.dataList = new ArrayList();
        T6Record t6RecordByDeviceId = T6Utils.getT6RecordByDeviceId(this.t6Record.getDeviceId(), this.deviceType == 27 ? 0 : 1);
        this.t6Record = t6RecordByDeviceId;
        int serviceStatus = t6RecordByDeviceId.getServiceStatus();
        if (serviceStatus != 0) {
            if (serviceStatus != 1) {
                if (serviceStatus == 2) {
                    if (d4shBannerData == null || d4shBannerData.getFreeActivity() == null) {
                        this.dataList.add(new D4shBannerData.ServiceStatusData(3));
                    } else if (z2) {
                        this.dataList.add(d4shBannerData.getFreeActivity());
                    }
                }
            } else if (this.t6Record.getMoreService() != 1 && this.t6Record.getCloudProduct().getSubscribe() != 1 && (Long.parseLong(this.t6Record.getCloudProduct().getWorkIndate()) * 1000) - System.currentTimeMillis() <= 259200000 && !"FREE".equalsIgnoreCase(this.t6Record.getCloudProduct().getChargeType())) {
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
            String sysMap = CommonUtils.getSysMap(Constants.T6_PROMOTE_BANNER + UserInforUtils.getCurrentUserId(this) + this.t6Record.getDeviceId() + (this.t6Record.getCloudProduct() == null ? 0 : this.t6Record.getCloudProduct().getServiceId()));
            if (!TextUtils.isEmpty(sysMap)) {
                List list = (List) new Gson().fromJson(sysMap, new TypeToken<List<BannerStateCache>>() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.53
                    public AnonymousClass53() {
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
                    this.flHomeBanner.setVisibility(0);
                    this.indicator.setVisibility(0);
                } else {
                    this.flHomeBanner.setVisibility(8);
                    this.indicator.setVisibility(8);
                }
                z = !z3;
            } else {
                this.flHomeBanner.setVisibility(0);
                this.indicator.setVisibility(0);
                z = true;
            }
            if (AppVersionStateUtils.getCurrentAppVersionCheckState()) {
                this.flHomeBanner.setVisibility(8);
                this.indicator.setVisibility(8);
            }
            T6HomeBannerPageAdapter t6HomeBannerPageAdapter = new T6HomeBannerPageAdapter(this, this.dataList, this.t6Record, this.deviceType, new T6HomeBannerPageAdapter.OnItemClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.54
                public AnonymousClass54() {
                }

                @Override // com.petkit.android.activities.petkitBleDevice.t6.adapter.T6HomeBannerPageAdapter.OnItemClickListener
                public void onBannerItemClick(int i2) {
                    T6HomeActivity t6HomeActivity = T6HomeActivity.this;
                    t6HomeActivity.redirectToPurchasePage(t6HomeActivity.deviceType, T6HomeActivity.this.t6Record.getDeviceId());
                }

                @Override // com.petkit.android.activities.petkitBleDevice.t6.adapter.T6HomeBannerPageAdapter.OnItemClickListener
                public void redirectToPurchase(int i2, long j) {
                    T6HomeActivity t6HomeActivity = T6HomeActivity.this;
                    t6HomeActivity.redirectToPurchasePage(t6HomeActivity.deviceType, T6HomeActivity.this.t6Record.getDeviceId());
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
                this.viewPagerDisposable = Observable.interval(5000L, 5000L, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$$ExternalSyntheticLambda18
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) throws Exception {
                        this.f$0.lambda$refreshHomeBannerData$47((Long) obj);
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
            this.ivPagerClose.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.55
                public AnonymousClass55() {
                }

                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    List<D4shBannerData.BannerData> dataList = T6HomeActivity.this.bannerPageAdapter.getDataList();
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
                    T6HomeActivity.this.flHomeBanner.setVisibility(8);
                    T6HomeActivity.this.indicator.setVisibility(8);
                    CommonUtils.addSysMap(Constants.T6_PROMOTE_BANNER + UserInforUtils.getCurrentUserId(T6HomeActivity.this) + T6HomeActivity.this.t6Record.getDeviceId() + (T6HomeActivity.this.t6Record.getCloudProduct() != null ? T6HomeActivity.this.t6Record.getCloudProduct().getServiceId() : 0), new Gson().toJson(arrayList, new TypeToken<List<BannerStateCache>>() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.55.1
                        public AnonymousClass1() {
                        }
                    }.getType()));
                }

                /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$55$1 */
                public class AnonymousClass1 extends TypeToken<List<BannerStateCache>> {
                    public AnonymousClass1() {
                    }
                }
            });
            return;
        }
        this.flHomeBanner.setVisibility(8);
        this.indicator.setVisibility(8);
        Disposable disposable3 = this.viewPagerDisposable;
        if (disposable3 == null || disposable3.isDisposed()) {
            return;
        }
        this.viewPagerDisposable.dispose();
        this.viewPagerDisposable = null;
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$53 */
    public class AnonymousClass53 extends TypeToken<List<BannerStateCache>> {
        public AnonymousClass53() {
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$54 */
    public class AnonymousClass54 implements T6HomeBannerPageAdapter.OnItemClickListener {
        public AnonymousClass54() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t6.adapter.T6HomeBannerPageAdapter.OnItemClickListener
        public void onBannerItemClick(int i2) {
            T6HomeActivity t6HomeActivity = T6HomeActivity.this;
            t6HomeActivity.redirectToPurchasePage(t6HomeActivity.deviceType, T6HomeActivity.this.t6Record.getDeviceId());
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t6.adapter.T6HomeBannerPageAdapter.OnItemClickListener
        public void redirectToPurchase(int i2, long j) {
            T6HomeActivity t6HomeActivity = T6HomeActivity.this;
            t6HomeActivity.redirectToPurchasePage(t6HomeActivity.deviceType, T6HomeActivity.this.t6Record.getDeviceId());
        }
    }

    public /* synthetic */ void lambda$refreshHomeBannerData$47(Long l) throws Exception {
        ViewPager viewPager = this.vpHomeBanner;
        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
        this.current = this.vpHomeBanner.getCurrentItem() % (this.bannerPageAdapter.getDataList().size() != 0 ? this.bannerPageAdapter.getDataList().size() : 1);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$55 */
    public class AnonymousClass55 implements View.OnClickListener {
        public AnonymousClass55() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            List<D4shBannerData.BannerData> dataList = T6HomeActivity.this.bannerPageAdapter.getDataList();
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
            T6HomeActivity.this.flHomeBanner.setVisibility(8);
            T6HomeActivity.this.indicator.setVisibility(8);
            CommonUtils.addSysMap(Constants.T6_PROMOTE_BANNER + UserInforUtils.getCurrentUserId(T6HomeActivity.this) + T6HomeActivity.this.t6Record.getDeviceId() + (T6HomeActivity.this.t6Record.getCloudProduct() != null ? T6HomeActivity.this.t6Record.getCloudProduct().getServiceId() : 0), new Gson().toJson(arrayList, new TypeToken<List<BannerStateCache>>() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.55.1
                public AnonymousClass1() {
                }
            }.getType()));
        }

        /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$55$1 */
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

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$56 */
    public class AnonymousClass56 implements Runnable {
        public final /* synthetic */ D4shBannerData val$d4shBannerData;

        public AnonymousClass56(D4shBannerData d4shBannerData) {
            d4shBannerData = d4shBannerData;
        }

        @Override // java.lang.Runnable
        public void run() {
            D4shBannerData.FreeActivity freeActivity = d4shBannerData.getFreeActivity();
            boolean zEquals = "zh_CN".equals(UserInforUtils.getLanguageLocaleString(CommonUtils.getAppContext()));
            if (CommonUtils.getSysBoolMap(T6HomeActivity.this, Constants.DEVICE_FREE_ACTIVITY + T6HomeActivity.this.t6Record.getDeviceId() + "&" + T6HomeActivity.this.deviceType, false)) {
                return;
            }
            CloudServiceFreeTrialDialog cloudServiceFreeTrialDialog = T6HomeActivity.this.cloudServiceFreeTrialDialog;
            if (cloudServiceFreeTrialDialog == null || !cloudServiceFreeTrialDialog.isShowing()) {
                T6HomeActivity t6HomeActivity = T6HomeActivity.this;
                T6HomeActivity t6HomeActivity2 = T6HomeActivity.this;
                D4shBannerData.ImageRet imageRet = freeActivity.getImageRet();
                t6HomeActivity.cloudServiceFreeTrialDialog = new CloudServiceFreeTrialDialog(t6HomeActivity2, zEquals ? imageRet.getPopChineseImage() : imageRet.getPopEnglishImage(), new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.56.1
                    public AnonymousClass1() {
                    }

                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        T6HomeActivity t6HomeActivity3 = T6HomeActivity.this;
                        String strCreatePurchasePageUrl = D4shUtils.createPurchasePageUrl(t6HomeActivity3, t6HomeActivity3.deviceType, T6HomeActivity.this.t6Record.getDeviceId());
                        if (T6HomeActivity.this.t6Record.getRealDeviceShared() == null) {
                            FamilyUtils familyUtils = FamilyUtils.getInstance();
                            T6HomeActivity t6HomeActivity4 = T6HomeActivity.this;
                            if (!familyUtils.checkIsSharedDevice(t6HomeActivity4, t6HomeActivity4.deviceId, 27)) {
                                CommonUtils.addSysBoolMap(T6HomeActivity.this, Constants.DEVICE_FREE_ACTIVITY + T6HomeActivity.this.t6Record.getDeviceId() + "&" + T6HomeActivity.this.deviceType, true);
                                if (!TextUtils.isEmpty(strCreatePurchasePageUrl)) {
                                    T6HomeActivity.this.onRedirectToH5(strCreatePurchasePageUrl);
                                }
                                T6HomeActivity.this.cloudServiceFreeTrialDialog.dismiss();
                                return;
                            }
                        }
                        T6HomeActivity t6HomeActivity5 = T6HomeActivity.this;
                        PetkitToast.showTopToast(t6HomeActivity5, t6HomeActivity5.getResources().getString(R.string.D4sh_no_cloud_free_tips), R.drawable.top_toast_warn_icon, 1);
                    }
                });
                T6HomeActivity.this.cloudServiceFreeTrialDialog.getContentView().findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.56.2
                    public AnonymousClass2() {
                    }

                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        CommonUtils.addSysBoolMap(T6HomeActivity.this, Constants.DEVICE_FREE_ACTIVITY + T6HomeActivity.this.t6Record.getDeviceId() + "&" + T6HomeActivity.this.deviceType, true);
                        T6HomeActivity.this.cloudServiceFreeTrialDialog.dismiss();
                    }
                });
                T6HomeActivity.this.cloudServiceFreeTrialDialog.setBackgroundDrawable(new BitmapDrawable());
                T6HomeActivity.this.cloudServiceFreeTrialDialog.setOutsideTouchable(true);
                T6HomeActivity.this.cloudServiceFreeTrialDialog.setTouchable(true);
                T6HomeActivity.this.cloudServiceFreeTrialDialog.setAnimationStyle(R.style.PopupWindow_Default_Appearance_Animation);
                T6HomeActivity t6HomeActivity3 = T6HomeActivity.this;
                t6HomeActivity3.cloudServiceFreeTrialDialog.showAtLocation(t6HomeActivity3.getWindow().getDecorView(), 17, 0, 0);
            }
        }

        /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$56$1 */
        public class AnonymousClass1 implements View.OnClickListener {
            public AnonymousClass1() {
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                T6HomeActivity t6HomeActivity3 = T6HomeActivity.this;
                String strCreatePurchasePageUrl = D4shUtils.createPurchasePageUrl(t6HomeActivity3, t6HomeActivity3.deviceType, T6HomeActivity.this.t6Record.getDeviceId());
                if (T6HomeActivity.this.t6Record.getRealDeviceShared() == null) {
                    FamilyUtils familyUtils = FamilyUtils.getInstance();
                    T6HomeActivity t6HomeActivity4 = T6HomeActivity.this;
                    if (!familyUtils.checkIsSharedDevice(t6HomeActivity4, t6HomeActivity4.deviceId, 27)) {
                        CommonUtils.addSysBoolMap(T6HomeActivity.this, Constants.DEVICE_FREE_ACTIVITY + T6HomeActivity.this.t6Record.getDeviceId() + "&" + T6HomeActivity.this.deviceType, true);
                        if (!TextUtils.isEmpty(strCreatePurchasePageUrl)) {
                            T6HomeActivity.this.onRedirectToH5(strCreatePurchasePageUrl);
                        }
                        T6HomeActivity.this.cloudServiceFreeTrialDialog.dismiss();
                        return;
                    }
                }
                T6HomeActivity t6HomeActivity5 = T6HomeActivity.this;
                PetkitToast.showTopToast(t6HomeActivity5, t6HomeActivity5.getResources().getString(R.string.D4sh_no_cloud_free_tips), R.drawable.top_toast_warn_icon, 1);
            }
        }

        /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$56$2 */
        public class AnonymousClass2 implements View.OnClickListener {
            public AnonymousClass2() {
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CommonUtils.addSysBoolMap(T6HomeActivity.this, Constants.DEVICE_FREE_ACTIVITY + T6HomeActivity.this.t6Record.getDeviceId() + "&" + T6HomeActivity.this.deviceType, true);
                T6HomeActivity.this.cloudServiceFreeTrialDialog.dismiss();
            }
        }
    }

    private void showFreeActivity(D4shBannerData d4shBannerData) {
        this.handler.postDelayed(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.56
            public final /* synthetic */ D4shBannerData val$d4shBannerData;

            public AnonymousClass56(D4shBannerData d4shBannerData2) {
                d4shBannerData = d4shBannerData2;
            }

            @Override // java.lang.Runnable
            public void run() {
                D4shBannerData.FreeActivity freeActivity = d4shBannerData.getFreeActivity();
                boolean zEquals = "zh_CN".equals(UserInforUtils.getLanguageLocaleString(CommonUtils.getAppContext()));
                if (CommonUtils.getSysBoolMap(T6HomeActivity.this, Constants.DEVICE_FREE_ACTIVITY + T6HomeActivity.this.t6Record.getDeviceId() + "&" + T6HomeActivity.this.deviceType, false)) {
                    return;
                }
                CloudServiceFreeTrialDialog cloudServiceFreeTrialDialog = T6HomeActivity.this.cloudServiceFreeTrialDialog;
                if (cloudServiceFreeTrialDialog == null || !cloudServiceFreeTrialDialog.isShowing()) {
                    T6HomeActivity t6HomeActivity = T6HomeActivity.this;
                    T6HomeActivity t6HomeActivity2 = T6HomeActivity.this;
                    D4shBannerData.ImageRet imageRet = freeActivity.getImageRet();
                    t6HomeActivity.cloudServiceFreeTrialDialog = new CloudServiceFreeTrialDialog(t6HomeActivity2, zEquals ? imageRet.getPopChineseImage() : imageRet.getPopEnglishImage(), new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.56.1
                        public AnonymousClass1() {
                        }

                        @Override // android.view.View.OnClickListener
                        public void onClick(View view) {
                            T6HomeActivity t6HomeActivity3 = T6HomeActivity.this;
                            String strCreatePurchasePageUrl = D4shUtils.createPurchasePageUrl(t6HomeActivity3, t6HomeActivity3.deviceType, T6HomeActivity.this.t6Record.getDeviceId());
                            if (T6HomeActivity.this.t6Record.getRealDeviceShared() == null) {
                                FamilyUtils familyUtils = FamilyUtils.getInstance();
                                T6HomeActivity t6HomeActivity4 = T6HomeActivity.this;
                                if (!familyUtils.checkIsSharedDevice(t6HomeActivity4, t6HomeActivity4.deviceId, 27)) {
                                    CommonUtils.addSysBoolMap(T6HomeActivity.this, Constants.DEVICE_FREE_ACTIVITY + T6HomeActivity.this.t6Record.getDeviceId() + "&" + T6HomeActivity.this.deviceType, true);
                                    if (!TextUtils.isEmpty(strCreatePurchasePageUrl)) {
                                        T6HomeActivity.this.onRedirectToH5(strCreatePurchasePageUrl);
                                    }
                                    T6HomeActivity.this.cloudServiceFreeTrialDialog.dismiss();
                                    return;
                                }
                            }
                            T6HomeActivity t6HomeActivity5 = T6HomeActivity.this;
                            PetkitToast.showTopToast(t6HomeActivity5, t6HomeActivity5.getResources().getString(R.string.D4sh_no_cloud_free_tips), R.drawable.top_toast_warn_icon, 1);
                        }
                    });
                    T6HomeActivity.this.cloudServiceFreeTrialDialog.getContentView().findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.56.2
                        public AnonymousClass2() {
                        }

                        @Override // android.view.View.OnClickListener
                        public void onClick(View view) {
                            CommonUtils.addSysBoolMap(T6HomeActivity.this, Constants.DEVICE_FREE_ACTIVITY + T6HomeActivity.this.t6Record.getDeviceId() + "&" + T6HomeActivity.this.deviceType, true);
                            T6HomeActivity.this.cloudServiceFreeTrialDialog.dismiss();
                        }
                    });
                    T6HomeActivity.this.cloudServiceFreeTrialDialog.setBackgroundDrawable(new BitmapDrawable());
                    T6HomeActivity.this.cloudServiceFreeTrialDialog.setOutsideTouchable(true);
                    T6HomeActivity.this.cloudServiceFreeTrialDialog.setTouchable(true);
                    T6HomeActivity.this.cloudServiceFreeTrialDialog.setAnimationStyle(R.style.PopupWindow_Default_Appearance_Animation);
                    T6HomeActivity t6HomeActivity3 = T6HomeActivity.this;
                    t6HomeActivity3.cloudServiceFreeTrialDialog.showAtLocation(t6HomeActivity3.getWindow().getDecorView(), 17, 0, 0);
                }
            }

            /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$56$1 */
            public class AnonymousClass1 implements View.OnClickListener {
                public AnonymousClass1() {
                }

                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    T6HomeActivity t6HomeActivity3 = T6HomeActivity.this;
                    String strCreatePurchasePageUrl = D4shUtils.createPurchasePageUrl(t6HomeActivity3, t6HomeActivity3.deviceType, T6HomeActivity.this.t6Record.getDeviceId());
                    if (T6HomeActivity.this.t6Record.getRealDeviceShared() == null) {
                        FamilyUtils familyUtils = FamilyUtils.getInstance();
                        T6HomeActivity t6HomeActivity4 = T6HomeActivity.this;
                        if (!familyUtils.checkIsSharedDevice(t6HomeActivity4, t6HomeActivity4.deviceId, 27)) {
                            CommonUtils.addSysBoolMap(T6HomeActivity.this, Constants.DEVICE_FREE_ACTIVITY + T6HomeActivity.this.t6Record.getDeviceId() + "&" + T6HomeActivity.this.deviceType, true);
                            if (!TextUtils.isEmpty(strCreatePurchasePageUrl)) {
                                T6HomeActivity.this.onRedirectToH5(strCreatePurchasePageUrl);
                            }
                            T6HomeActivity.this.cloudServiceFreeTrialDialog.dismiss();
                            return;
                        }
                    }
                    T6HomeActivity t6HomeActivity5 = T6HomeActivity.this;
                    PetkitToast.showTopToast(t6HomeActivity5, t6HomeActivity5.getResources().getString(R.string.D4sh_no_cloud_free_tips), R.drawable.top_toast_warn_icon, 1);
                }
            }

            /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$56$2 */
            public class AnonymousClass2 implements View.OnClickListener {
                public AnonymousClass2() {
                }

                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    CommonUtils.addSysBoolMap(T6HomeActivity.this, Constants.DEVICE_FREE_ACTIVITY + T6HomeActivity.this.t6Record.getDeviceId() + "&" + T6HomeActivity.this.deviceType, true);
                    T6HomeActivity.this.cloudServiceFreeTrialDialog.dismiss();
                }
            }
        }, 500L);
    }

    public void onRedirectToH5(String str) {
        if (this.t6Record.getRealDeviceShared() != null || FamilyUtils.getInstance().checkIsSharedDevice(this, this.deviceId, 27)) {
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

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T6HomeContract.View
    public void setHighlightM3u8UrlList(int i, ArrayList<VlogM3U8Mode> arrayList, int i2) {
        Intent intent = new Intent(this, (Class<?>) VlogMakeService.class);
        intent.putExtra(Constants.EXTRA_DEVICE_ID, this.deviceId);
        intent.putExtra(Constants.EXTRA_DEVICE_TYPE, 27);
        intent.putExtra(Constants.EXTRA_VLOG_M3U8_URL_LIST, JSON.toJSONString(arrayList));
        intent.putExtra(Constants.EXTRA_VLOG_ID, i);
        intent.putExtra(Constants.EXTRA_VLOG_CYCLE, i2);
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

    @SuppressLint({"ClickableViewAccessibility"})
    private void initControlView() {
        this.ivControlLeft.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$$ExternalSyntheticLambda21
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initControlView$48(view);
            }
        });
        this.ivControlRight.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$$ExternalSyntheticLambda22
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initControlView$49(view);
            }
        });
        this.ivControlLeft.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$$ExternalSyntheticLambda23
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                return this.f$0.lambda$initControlView$50(view);
            }
        });
        this.ivControlRight.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$$ExternalSyntheticLambda24
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                return this.f$0.lambda$initControlView$51(view);
            }
        });
        this.ivControlLeft.setImageResource(R.drawable.t6_control_left_gray);
        this.ivControlLeft.setOnTouchListener(new View.OnTouchListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$$ExternalSyntheticLambda25
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return this.f$0.lambda$initControlView$52(view, motionEvent);
            }
        });
        this.ivControlRight.setImageResource(R.drawable.t6_control_right_gray);
        this.ivControlRight.setOnTouchListener(new View.OnTouchListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$$ExternalSyntheticLambda26
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return this.f$0.lambda$initControlView$53(view, motionEvent);
            }
        });
    }

    public /* synthetic */ void lambda$initControlView$48(View view) {
        this.direction = "left";
        sendSwitchMsg(0);
    }

    public /* synthetic */ void lambda$initControlView$49(View view) {
        this.direction = TtmlNode.RIGHT;
        sendSwitchMsg(0);
    }

    public /* synthetic */ boolean lambda$initControlView$50(View view) {
        this.direction = "left";
        this.longClick = true;
        if (this.t6Record.getSettings().getHomeMode() != 1 || this.t6Record.getSettings().getCameraInward() != 1) {
            sendSwitchMsg(1);
        }
        return true;
    }

    public /* synthetic */ boolean lambda$initControlView$51(View view) {
        this.direction = TtmlNode.RIGHT;
        this.longClick = true;
        if (this.t6Record.getSettings().getHomeMode() != 1 || this.t6Record.getSettings().getCameraInward() != 1) {
            sendSwitchMsg(1);
        }
        return true;
    }

    public /* synthetic */ boolean lambda$initControlView$52(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == 3 || motionEvent.getAction() == 1) {
            this.ivControlLeft.clearAnimation();
            this.ivControlLeft.setImageResource(R.drawable.t6_control_left_gray);
            if (this.longClick) {
                this.handler.removeMessages(12);
                longClickUp();
                this.longClick = false;
            }
        } else if (motionEvent.getAction() == 0) {
            this.ivControlLeft.setImageResource(R.drawable.t6_control_left);
            Animation animationLoadAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.t6_control_click);
            animationLoadAnimation.setFillAfter(true);
            this.ivControlLeft.startAnimation(animationLoadAnimation);
        }
        return false;
    }

    public /* synthetic */ boolean lambda$initControlView$53(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == 3 || motionEvent.getAction() == 1) {
            this.ivControlRight.clearAnimation();
            this.ivControlRight.setImageResource(R.drawable.t6_control_right_gray);
            if (this.longClick) {
                this.handler.removeMessages(12);
                longClickUp();
                this.longClick = false;
            }
        } else if (motionEvent.getAction() == 0) {
            this.ivControlRight.setImageResource(R.drawable.t6_control_right);
            Animation animationLoadAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.t6_control_click);
            animationLoadAnimation.setFillAfter(true);
            this.ivControlRight.startAnimation(animationLoadAnimation);
        }
        return false;
    }

    public void updateDragImage(Message message) {
        int i = message.what;
        if (i == 9) {
            Vibrator vibrator = this.vibrator;
            if (vibrator != null) {
                vibrator.vibrate(100L);
            }
            PetkitToast.showTopToast(this, getResources().getString(R.string.Camera_turns_side), 0, 0);
            return;
        }
        if (i == 10) {
            TextView textView = this.tvTopPaoPao;
            if (textView != null) {
                textView.setVisibility(8);
                return;
            }
            return;
        }
        if (i == 11) {
            PetkitToast.showTopToast(this, getResources().getString(R.string.Camera_busy), 0, 0);
            return;
        }
        if (i == 12) {
            sendSwitchMsg(1);
            return;
        }
        if (i == 13) {
            this.handler.removeMessages(13);
            this.t6BottomView.startColl();
            if (this.startCollAnim || this.isHidePromote) {
                return;
            }
            startPromoteViewAnim();
            return;
        }
        if (i == 14) {
            this.handler.removeMessages(14);
            this.t6BottomView.endColl();
            if (this.startExpandAnim || !this.isHidePromote) {
                return;
            }
            endPromoteViewAnim();
            return;
        }
        if (i == 15) {
            P p = this.mPresenter;
            if (p != 0 && ((T6HomePresenter) p).getLiveService() != null) {
                if (CommonUtil.getLong(this.t6Record.getFirmware()) < 600.0d) {
                    ((AgoraLiveService) ((T6HomePresenter) this.mPresenter).getLiveService()).setTurning(true);
                } else {
                    ((RtcService) ((T6HomePresenter) this.mPresenter).getLiveService()).setTurning(true);
                }
            }
            if (this.showTurnCamera) {
                return;
            }
            showFlipCameraAnim(false);
            return;
        }
        if (i == 16) {
            hideFlipCameraAnim();
            return;
        }
        if (i == 17) {
            hideFlipCameraAnim();
            return;
        }
        if (i == 18) {
            if (this.showTurnCamera) {
                return;
            }
            P p2 = this.mPresenter;
            if (p2 != 0 && ((T6HomePresenter) p2).getLiveService() != null) {
                if (CommonUtil.getLong(this.t6Record.getFirmware()) < 600.0d) {
                    ((AgoraLiveService) ((T6HomePresenter) this.mPresenter).getLiveService()).setTurning(true);
                } else {
                    ((RtcService) ((T6HomePresenter) this.mPresenter).getLiveService()).setTurning(true);
                }
            }
            showFlipCameraAnim(true);
            return;
        }
        if (i == 23) {
            openHomeModeTipWindow(getString(R.string.Home_mode_can_not_move_camera_tip));
            return;
        }
        if (i == 19) {
            MyHandler myHandler = this.handler;
            if (myHandler != null) {
                myHandler.sendEmptyMessageDelayed(20, 50L);
                return;
            }
            return;
        }
        if (i == 20) {
            this.handler.removeMessages(19);
            if (this.rlDeviceState.getTop() <= 0 || Math.abs(this.appBarOffset) <= this.rlDeviceState.getTop() - CommonUtil.dip2px(this, T7ConstUtils.T7_PET_APPEAR_VERSION) || Math.abs(this.appBarOffset) >= this.rlDeviceState.getBottom() || !this.isNeedShowPetError) {
                return;
            }
            showPopWindow();
            return;
        }
        if (i == 21) {
            this.handler.removeMessages(22);
            if (RtmManager.getInstance().isLoginRtm()) {
                this.handler.removeMessages(21);
                ((T6HomePresenter) this.mPresenter).startLiveVideo(this.t6LiveVideoView.getVideoPlayerView());
                return;
            }
            int i2 = message.arg1;
            if (i2 == 5) {
                ((T6HomePresenter) this.mPresenter).startLiveVideo(this.t6LiveVideoView.getVideoPlayerView());
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
                ((T6HomePresenter) this.mPresenter).startLiveVideo(this.t6LiveVideoView.getVideoPlayerView());
            } else {
                if (i3 == 5) {
                    ((T6HomePresenter) this.mPresenter).startLiveVideo(this.t6LiveVideoView.getVideoPlayerView());
                    return;
                }
                Message message3 = new Message();
                message3.what = 22;
                message3.arg1 = i3 + 1;
                this.handler.sendMessageDelayed(message3, 500L);
            }
        }
    }

    private void resetLiveVideoView() {
        this.rlParentLive.removeAllViews();
        T6LiveVideoView t6LiveVideoView = new T6LiveVideoView(this);
        this.t6LiveVideoView = t6LiveVideoView;
        t6LiveVideoView.setPlayerType(PetkitVideoPlayerView.PlayerType.CIRCLE);
        this.t6LiveVideoView.setTouchListener();
        this.rlParentLive.addView(this.t6LiveVideoView);
        this.t6LiveVideoView.getVideoPlayerView().showLoadingView();
        this.t6LiveVideoView.setT6LiveVideoViewListener(this);
        this.t6LiveVideoView.setListener(this);
    }

    private void sendSwitchMsg(int i) {
        MyHandler myHandler;
        new HashMap().put("type", "Devicehome");
        if (canOption()) {
            if (i == 2) {
                showFlipCameraAnim(false);
                MyHandler myHandler2 = this.handler;
                if (myHandler2 != null) {
                    myHandler2.sendEmptyMessageDelayed(17, 10000L);
                }
            } else if (i == 1 && (myHandler = this.handler) != null) {
                myHandler.sendEmptyMessageDelayed(12, 1000L);
            }
            ((T6HomePresenter) this.mPresenter).switchCameraDirection(i, this.direction, new SwitchCameraCall() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$$ExternalSyntheticLambda1
                @Override // com.petkit.android.activities.petkitBleDevice.t6.utils.SwitchCameraCall
                public final void callError(int i2) {
                    this.f$0.lambda$sendSwitchMsg$54(i2);
                }
            });
        }
    }

    public /* synthetic */ void lambda$sendSwitchMsg$54(int i) {
        showUnOption(getResources().getString(R.string.T6_Rtm_Unconnected));
    }

    public /* synthetic */ void lambda$longClickUp$55(int i) {
        showUnOption(getResources().getString(R.string.T6_Rtm_Unconnected));
    }

    private void longClickUp() {
        ((T6HomePresenter) this.mPresenter).switchCameraDirection(1, "", new SwitchCameraCall() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$$ExternalSyntheticLambda49
            @Override // com.petkit.android.activities.petkitBleDevice.t6.utils.SwitchCameraCall
            public final void callError(int i) {
                this.f$0.lambda$longClickUp$55(i);
            }
        });
    }

    private void showUnOption(String str) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (jCurrentTimeMillis - this.optionTime < 3000) {
            return;
        }
        this.optionTime = jCurrentTimeMillis;
        PetkitToast.showTopToast(this, str, 0, 1);
    }

    public static class MyHandler extends Handler {
        public final WeakReference<T6HomeActivity> mActivity;

        public MyHandler(Looper looper, T6HomeActivity t6HomeActivity) {
            super(looper);
            this.mActivity = new WeakReference<>(t6HomeActivity);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            T6HomeActivity t6HomeActivity = this.mActivity.get();
            if (t6HomeActivity != null) {
                t6HomeActivity.updateDragImage(message);
            }
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T6HomeContract.View
    public void showToiletUnusedDialog() {
        Resources resources;
        int i;
        if (this.isShowToiletUnusedDialog || this.t6Record.getDeviceShared() != null) {
            P p = this.mPresenter;
            if (p != 0) {
                ((T6HomePresenter) p).checkInitWindow(3);
                return;
            }
            return;
        }
        T6Record t6RecordByDeviceId = T6Utils.getT6RecordByDeviceId(this.deviceId, this.deviceType == 27 ? 0 : 1);
        this.t6Record = t6RecordByDeviceId;
        if (t6RecordByDeviceId == null || t6RecordByDeviceId.getPetOutTips() == null || this.t6Record.getPetOutTips().size() == 0 || this.t6Record.getPetOutTips() == null || this.t6Record.getPetOutTips().size() <= 0) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < this.t6Record.getPetOutTips().size(); i2++) {
            PetOutTip petOutTip = this.t6Record.getPetOutTips().get(i2);
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
            SpannableStringColorsWindow spannableStringColorsWindow2 = new SpannableStringColorsWindow(this, new SpannableStringColorsWindow.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.57
                public AnonymousClass57() {
                }

                @Override // com.petkit.android.activities.universalWindow.SpannableStringColorsWindow.OnClickListener
                public void onCancel() {
                    ((T6HomePresenter) ((BaseActivity) T6HomeActivity.this).mPresenter).recordUserAction(2);
                    if (((BaseActivity) T6HomeActivity.this).mPresenter != null) {
                        ((T6HomePresenter) ((BaseActivity) T6HomeActivity.this).mPresenter).checkInitWindow(3);
                    }
                }

                @Override // com.petkit.android.activities.universalWindow.SpannableStringColorsWindow.OnClickListener
                public void onConfirm() {
                    if (((BaseActivity) T6HomeActivity.this).mPresenter != null) {
                        ((T6HomePresenter) ((BaseActivity) T6HomeActivity.this).mPresenter).checkInitWindow(3);
                    }
                }
            }, getResources().getString(R.string.T_Toilet_Error_Tips), sb.toString() + getResources().getString(R.string.T_Pet_No_Toilet_Desc), getResources().getString(R.string.I_already_know), getResources().getString(R.string.Not_remind), R.color.new_bind_blue, strArr);
            this.petOutWindow = spannableStringColorsWindow2;
            spannableStringColorsWindow2.show();
            this.isShowToiletUnusedDialog = true;
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$57 */
    public class AnonymousClass57 implements SpannableStringColorsWindow.OnClickListener {
        public AnonymousClass57() {
        }

        @Override // com.petkit.android.activities.universalWindow.SpannableStringColorsWindow.OnClickListener
        public void onCancel() {
            ((T6HomePresenter) ((BaseActivity) T6HomeActivity.this).mPresenter).recordUserAction(2);
            if (((BaseActivity) T6HomeActivity.this).mPresenter != null) {
                ((T6HomePresenter) ((BaseActivity) T6HomeActivity.this).mPresenter).checkInitWindow(3);
            }
        }

        @Override // com.petkit.android.activities.universalWindow.SpannableStringColorsWindow.OnClickListener
        public void onConfirm() {
            if (((BaseActivity) T6HomeActivity.this).mPresenter != null) {
                ((T6HomePresenter) ((BaseActivity) T6HomeActivity.this).mPresenter).checkInitWindow(3);
            }
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$58 */
    public class AnonymousClass58 implements NormalListChoiceCenterWindow.OnClickThreeChoices {
        @Override // com.petkit.android.activities.universalWindow.NormalListChoiceCenterWindow.OnClickThreeChoices
        public void onClickFirstChoice() {
        }

        @Override // com.petkit.android.activities.universalWindow.NormalListChoiceCenterWindow.OnClickThreeChoices
        public void onClickThirdChoice() {
        }

        public AnonymousClass58() {
        }

        @Override // com.petkit.android.activities.universalWindow.NormalListChoiceCenterWindow.OnClickThreeChoices
        public void onClickSecondChoice() {
            ((T6HomePresenter) ((BaseActivity) T6HomeActivity.this).mPresenter).ignorePh();
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T6HomeContract.View
    public void showPhErrorDialog() {
        if (this.phErrorWindow == null) {
            this.phErrorWindow = new NormalListChoiceCenterWindow(this, new NormalListChoiceCenterWindow.OnClickThreeChoices() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.58
                @Override // com.petkit.android.activities.universalWindow.NormalListChoiceCenterWindow.OnClickThreeChoices
                public void onClickFirstChoice() {
                }

                @Override // com.petkit.android.activities.universalWindow.NormalListChoiceCenterWindow.OnClickThreeChoices
                public void onClickThirdChoice() {
                }

                public AnonymousClass58() {
                }

                @Override // com.petkit.android.activities.universalWindow.NormalListChoiceCenterWindow.OnClickThreeChoices
                public void onClickSecondChoice() {
                    ((T6HomePresenter) ((BaseActivity) T6HomeActivity.this).mPresenter).ignorePh();
                }
            }, getString(R.string.T_toilet_ph_error_tips), getString(R.string.T_toilet_ph_error_tips_promp), getString(R.string.I_know), getString(R.string.This_round_not_remind), null);
        }
        this.phErrorWindow.setThreeChoicesTextColor(getResources().getColor(R.color.new_bind_blue), -1, -1);
        if (this.phErrorWindow.isShowing()) {
            return;
        }
        this.phErrorWindow.show(getWindow().getDecorView());
    }

    public void showPackageState4Window() {
        String string;
        if (((T6HomePresenter) this.mPresenter).getCopyWritingGifVideo().getBoxUnserviceable() == null || ((T6HomePresenter) this.mPresenter).getCopyWritingGifVideo().getBoxUnserviceable().size() <= 0) {
            return;
        }
        DeviceErrorWarnWindow deviceErrorWarnWindow = new DeviceErrorWarnWindow(this, this.deviceType, ((T6HomePresenter) this.mPresenter).getCopyWritingGifVideo().getBoxUnserviceable().get(0).getStepName(), ((T6HomePresenter) this.mPresenter).getCopyWritingGifVideo().getBoxUnserviceable().get(0).getStepDesc(), ((T6HomePresenter) this.mPresenter).getCopyWritingGifVideo().getBoxUnserviceable().get(0).getStepUrl(), "");
        this.bleDeviceHomeTroubleWarnWindow = deviceErrorWarnWindow;
        deviceErrorWarnWindow.setOnClickListener(new DeviceErrorWarnWindow.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.59
            @Override // com.petkit.android.activities.petkitBleDevice.widget.DeviceErrorWarnWindow.OnClickListener
            public /* synthetic */ void onClickText() {
                DeviceErrorWarnWindow.OnClickListener.CC.$default$onClickText(this);
            }

            public AnonymousClass59() {
            }

            @Override // com.petkit.android.activities.petkitBleDevice.widget.DeviceErrorWarnWindow.OnClickListener
            public void onClickLearnMore() {
                if (TextUtils.isEmpty(T6HomeActivity.this.relatedProductsInfor.getStandard().getT6().getGarbageBagBox().getShareUrl())) {
                    return;
                }
                T6HomeActivity t6HomeActivity = T6HomeActivity.this;
                MallUtils.goToWebOrProductDetail(t6HomeActivity, t6HomeActivity.relatedProductsInfor.getStandard().getT6().getGarbageBagBox().getShareUrl(), T6HomeActivity.this.relatedProductsInfor.getStandard().getT6().getGarbageBagBox().getUrlType());
            }
        });
        DeviceErrorWarnWindow deviceErrorWarnWindow2 = this.bleDeviceHomeTroubleWarnWindow;
        if (TextUtils.isEmpty(this.relatedProductsInfor.getStandard().getT6().getGarbageBagBox().getShareUrl())) {
            string = getResources().getString(R.string.I_already_know);
        } else {
            string = getResources().getString(R.string.T6_buy_bagBox) + " >";
        }
        deviceErrorWarnWindow2.setBtnText(string);
        this.bleDeviceHomeTroubleWarnWindow.showAtLocation(getWindow().getDecorView(), 17, 0, 0);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$59 */
    public class AnonymousClass59 implements DeviceErrorWarnWindow.OnClickListener {
        @Override // com.petkit.android.activities.petkitBleDevice.widget.DeviceErrorWarnWindow.OnClickListener
        public /* synthetic */ void onClickText() {
            DeviceErrorWarnWindow.OnClickListener.CC.$default$onClickText(this);
        }

        public AnonymousClass59() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.widget.DeviceErrorWarnWindow.OnClickListener
        public void onClickLearnMore() {
            if (TextUtils.isEmpty(T6HomeActivity.this.relatedProductsInfor.getStandard().getT6().getGarbageBagBox().getShareUrl())) {
                return;
            }
            T6HomeActivity t6HomeActivity = T6HomeActivity.this;
            MallUtils.goToWebOrProductDetail(t6HomeActivity, t6HomeActivity.relatedProductsInfor.getStandard().getT6().getGarbageBagBox().getShareUrl(), T6HomeActivity.this.relatedProductsInfor.getStandard().getT6().getGarbageBagBox().getUrlType());
        }
    }

    public void manualShowToiletUnusedDialog() {
        Resources resources;
        int i;
        T6Record t6RecordByDeviceId = T6Utils.getT6RecordByDeviceId(this.deviceId, this.deviceType == 27 ? 0 : 1);
        this.t6Record = t6RecordByDeviceId;
        if (t6RecordByDeviceId == null || t6RecordByDeviceId.getPetOutTips() == null || this.t6Record.getPetOutTips().size() == 0 || this.t6Record.getPetOutTips() == null || this.t6Record.getPetOutTips().size() <= 0) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < this.t6Record.getPetOutTips().size(); i2++) {
            PetOutTip petOutTip = this.t6Record.getPetOutTips().get(i2);
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
            SpannableStringColorsWindow spannableStringColorsWindow2 = new SpannableStringColorsWindow(this, new SpannableStringColorsWindow.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.60
                @Override // com.petkit.android.activities.universalWindow.SpannableStringColorsWindow.OnClickListener
                public void onConfirm() {
                }

                public AnonymousClass60() {
                }

                @Override // com.petkit.android.activities.universalWindow.SpannableStringColorsWindow.OnClickListener
                public void onCancel() {
                    ((T6HomePresenter) ((BaseActivity) T6HomeActivity.this).mPresenter).recordUserAction(2);
                }
            }, getResources().getString(R.string.T_Toilet_Error_Tips), sb.toString() + getResources().getString(R.string.T_Pet_No_Toilet_Desc), getResources().getString(R.string.I_already_know), getResources().getString(R.string.Not_remind), R.color.new_bind_blue, strArr);
            this.petOutWindow = spannableStringColorsWindow2;
            spannableStringColorsWindow2.show();
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$60 */
    public class AnonymousClass60 implements SpannableStringColorsWindow.OnClickListener {
        @Override // com.petkit.android.activities.universalWindow.SpannableStringColorsWindow.OnClickListener
        public void onConfirm() {
        }

        public AnonymousClass60() {
        }

        @Override // com.petkit.android.activities.universalWindow.SpannableStringColorsWindow.OnClickListener
        public void onCancel() {
            ((T6HomePresenter) ((BaseActivity) T6HomeActivity.this).mPresenter).recordUserAction(2);
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T6HomeContract.View
    public void showHealthRemindWindow() {
        if (this.isShowHealthRemindWindow || this.t6Record.getDeviceShared() != null) {
            P p = this.mPresenter;
            if (p != 0) {
                ((T6HomePresenter) p).checkInitWindow(4);
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
                this.promptWindow = new SpannableStringColorsPicPromptWindow(this, getResources().getString(R.string.Toilet_health_remind_title), R.drawable.toilet_health_remind, new SpannableStringColorsPicPromptWindow.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.61
                    public AnonymousClass61() {
                    }

                    @Override // com.petkit.android.activities.universalWindow.SpannableStringColorsPicPromptWindow.OnClickListener
                    public void onCancel() {
                        ((T6HomePresenter) ((BaseActivity) T6HomeActivity.this).mPresenter).recordUserAction(1);
                    }

                    @Override // com.petkit.android.activities.universalWindow.SpannableStringColorsPicPromptWindow.OnClickListener
                    public void onConfirm() {
                        ((T6HomePresenter) ((BaseActivity) T6HomeActivity.this).mPresenter).recordUserAction(1);
                        if (T6HomeActivity.this.relatedProductsInfor.getStandard().getT6().getShelf() != null) {
                            T6HomeActivity t6HomeActivity = T6HomeActivity.this;
                            t6HomeActivity.launchActivity(WebviewActivity.newIntent(t6HomeActivity, "", t6HomeActivity.relatedProductsInfor.getStandard().getT6().getShelf().getShareUrl()));
                        }
                    }
                }, string3, getResources().getString(R.string.Go_to_buy), getResources().getString(R.string.Think_again), string, string2);
            } else {
                this.promptWindow = new SpannableStringColorsPicPromptWindow(this, getResources().getString(R.string.Toilet_health_remind_title), string3, getResources().getString(R.string.Feeder_i_know), R.drawable.toilet_health_remind, new SpannableStringColorsPicPromptWindow.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.62
                    public AnonymousClass62() {
                    }

                    @Override // com.petkit.android.activities.universalWindow.SpannableStringColorsPicPromptWindow.OnClickListener
                    public void onCancel() {
                        if (((BaseActivity) T6HomeActivity.this).mPresenter != null) {
                            ((T6HomePresenter) ((BaseActivity) T6HomeActivity.this).mPresenter).recordUserAction(1);
                            ((T6HomePresenter) ((BaseActivity) T6HomeActivity.this).mPresenter).checkInitWindow(4);
                        }
                    }

                    @Override // com.petkit.android.activities.universalWindow.SpannableStringColorsPicPromptWindow.OnClickListener
                    public void onConfirm() {
                        if (((BaseActivity) T6HomeActivity.this).mPresenter != null) {
                            ((T6HomePresenter) ((BaseActivity) T6HomeActivity.this).mPresenter).recordUserAction(1);
                            ((T6HomePresenter) ((BaseActivity) T6HomeActivity.this).mPresenter).checkInitWindow(4);
                        }
                    }
                }, string, string2);
            }
            this.promptWindow.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$$ExternalSyntheticLambda45
                @Override // android.widget.PopupWindow.OnDismissListener
                public final void onDismiss() {
                    this.f$0.lambda$showHealthRemindWindow$56();
                }
            });
            this.promptWindow.show(getWindow().getDecorView());
            this.isShowHealthRemindWindow = true;
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$61 */
    public class AnonymousClass61 implements SpannableStringColorsPicPromptWindow.OnClickListener {
        public AnonymousClass61() {
        }

        @Override // com.petkit.android.activities.universalWindow.SpannableStringColorsPicPromptWindow.OnClickListener
        public void onCancel() {
            ((T6HomePresenter) ((BaseActivity) T6HomeActivity.this).mPresenter).recordUserAction(1);
        }

        @Override // com.petkit.android.activities.universalWindow.SpannableStringColorsPicPromptWindow.OnClickListener
        public void onConfirm() {
            ((T6HomePresenter) ((BaseActivity) T6HomeActivity.this).mPresenter).recordUserAction(1);
            if (T6HomeActivity.this.relatedProductsInfor.getStandard().getT6().getShelf() != null) {
                T6HomeActivity t6HomeActivity = T6HomeActivity.this;
                t6HomeActivity.launchActivity(WebviewActivity.newIntent(t6HomeActivity, "", t6HomeActivity.relatedProductsInfor.getStandard().getT6().getShelf().getShareUrl()));
            }
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$62 */
    public class AnonymousClass62 implements SpannableStringColorsPicPromptWindow.OnClickListener {
        public AnonymousClass62() {
        }

        @Override // com.petkit.android.activities.universalWindow.SpannableStringColorsPicPromptWindow.OnClickListener
        public void onCancel() {
            if (((BaseActivity) T6HomeActivity.this).mPresenter != null) {
                ((T6HomePresenter) ((BaseActivity) T6HomeActivity.this).mPresenter).recordUserAction(1);
                ((T6HomePresenter) ((BaseActivity) T6HomeActivity.this).mPresenter).checkInitWindow(4);
            }
        }

        @Override // com.petkit.android.activities.universalWindow.SpannableStringColorsPicPromptWindow.OnClickListener
        public void onConfirm() {
            if (((BaseActivity) T6HomeActivity.this).mPresenter != null) {
                ((T6HomePresenter) ((BaseActivity) T6HomeActivity.this).mPresenter).recordUserAction(1);
                ((T6HomePresenter) ((BaseActivity) T6HomeActivity.this).mPresenter).checkInitWindow(4);
            }
        }
    }

    public /* synthetic */ void lambda$showHealthRemindWindow$56() {
        ((T6HomePresenter) this.mPresenter).checkInitWindow(4);
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T6HomeContract.View
    public void showKittenProtectWindow() {
        showKittenProtectDialog();
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T6HomeContract.View
    public void showGuideView() {
        showLiveGuide();
    }

    private void showLiveGuide() {
        int iDip2px;
        if (this.liveGuide != null) {
            return;
        }
        GuideBuilder guideBuilder = new GuideBuilder();
        guideBuilder.setTargetView(this.t6LiveVideoView).setAlpha(180).setHighTargetCorner(600).setOutsideTouchable(false).setAutoDismiss(true);
        guideBuilder.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.63
            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onShown() {
            }

            public AnonymousClass63() {
            }

            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onDismiss() {
                T6HomeActivity.this.showHomeModeGuide();
            }
        });
        if ("zh_CN".equals(UserInforUtils.getLanguageLocaleString(CommonUtils.getAppContext()))) {
            iDip2px = ArmsUtils.dip2px(this, 20.0f);
        } else {
            iDip2px = ArmsUtils.dip2px(this, 20.0f);
        }
        guideBuilder.addComponent(new GuideD4shBottomRightTipAndBtnAndLineComponent(new GuideParam(getResources().getString(R.string.T6_home_live_guide_tips), "1/5", 4, 48, iDip2px, ArmsUtils.dip2px(this, -40.0f), getResources().getString(R.string.Next_tip), R.layout.layout_t6_live_guide), new ConfirmListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.64
            public AnonymousClass64() {
            }

            @Override // com.petkit.android.activities.petkitBleDevice.w5.guide.ConfirmListener
            public void onConfirmListener() {
                Guide guide = T6HomeActivity.this.liveGuide;
                if (guide != null) {
                    guide.dismiss();
                }
            }
        }));
        Guide guideCreateGuide = guideBuilder.createGuide();
        this.liveGuide = guideCreateGuide;
        guideCreateGuide.show(this);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$63 */
    public class AnonymousClass63 implements GuideBuilder.OnVisibilityChangedListener {
        @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
        public void onShown() {
        }

        public AnonymousClass63() {
        }

        @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
        public void onDismiss() {
            T6HomeActivity.this.showHomeModeGuide();
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$64 */
    public class AnonymousClass64 implements ConfirmListener {
        public AnonymousClass64() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.w5.guide.ConfirmListener
        public void onConfirmListener() {
            Guide guide = T6HomeActivity.this.liveGuide;
            if (guide != null) {
                guide.dismiss();
            }
        }
    }

    public void showHomeModeGuide() {
        int iDip2px;
        if (this.homeModeGUide != null) {
            return;
        }
        GuideBuilder guideBuilder = new GuideBuilder();
        guideBuilder.setTargetView(this.ivBackHomeMode).setAlpha(180).setHighTargetCorner(50).setHighTargetPaddingLeft(ArmsUtils.dip2px(this, 10.0f)).setHighTargetPaddingRight(ArmsUtils.dip2px(this, 10.0f)).setHighTargetPaddingBottom(ArmsUtils.dip2px(this, 5.0f)).setHighTargetPaddingTop(ArmsUtils.dip2px(this, 5.0f)).setOutsideTouchable(false).setAutoDismiss(true);
        guideBuilder.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.65
            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onShown() {
            }

            public AnonymousClass65() {
            }

            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onDismiss() {
                T6HomeActivity.this.showFirstGuide();
            }
        });
        if ("zh_CN".equals(UserInforUtils.getLanguageLocaleString(CommonUtils.getAppContext()))) {
            iDip2px = ArmsUtils.dip2px(this, -20.0f);
        } else {
            iDip2px = ArmsUtils.dip2px(this, -40.0f);
        }
        guideBuilder.addComponent(new GuidePetTipAndBtnAndLineComponent(new GuideParam(getResources().getString(R.string.T6_home_home_mode_guide_tips), "2/5", 4, 32, iDip2px, 10, getResources().getString(R.string.Next_tip), R.layout.layout_feeder_guide_one), new ConfirmListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.66
            public AnonymousClass66() {
            }

            @Override // com.petkit.android.activities.petkitBleDevice.w5.guide.ConfirmListener
            public void onConfirmListener() {
                Guide guide = T6HomeActivity.this.homeModeGUide;
                if (guide != null) {
                    guide.dismiss();
                }
            }
        }));
        Guide guideCreateGuide = guideBuilder.createGuide();
        this.homeModeGUide = guideCreateGuide;
        guideCreateGuide.show(this);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$65 */
    public class AnonymousClass65 implements GuideBuilder.OnVisibilityChangedListener {
        @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
        public void onShown() {
        }

        public AnonymousClass65() {
        }

        @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
        public void onDismiss() {
            T6HomeActivity.this.showFirstGuide();
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$66 */
    public class AnonymousClass66 implements ConfirmListener {
        public AnonymousClass66() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.w5.guide.ConfirmListener
        public void onConfirmListener() {
            Guide guide = T6HomeActivity.this.homeModeGUide;
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
        guideBuilder.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.67
            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onShown() {
            }

            public AnonymousClass67() {
            }

            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onDismiss() {
                int[] iArr = new int[2];
                DisplayMetrics displayMetrics = new DisplayMetrics();
                T6HomeActivity.this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int i = displayMetrics.heightPixels;
                T6HomeActivity.this.ivChartShow.getLocationOnScreen(iArr);
                int iDip2px2 = (iArr[1] - i) + ArmsUtils.dip2px(T6HomeActivity.this, 170.0f);
                AppBarLayout appBarLayout = T6HomeActivity.this.appBar;
                if (iDip2px2 < 0) {
                    iDip2px2 = 0;
                }
                appBarLayout.scrollTo(0, iDip2px2);
                T6HomeActivity.this.showSecondGuide();
            }
        });
        if ("zh_CN".equals(UserInforUtils.getLanguageLocaleString(CommonUtils.getAppContext()))) {
            iDip2px = ArmsUtils.dip2px(this, 20.0f);
        } else {
            iDip2px = ArmsUtils.dip2px(this, 20.0f);
        }
        guideBuilder.addComponent(new GuideTipAndBtnAndLineComponent(new GuideParam(getResources().getString(R.string.T6_home_guide_tips1), "3/5", 2, 48, iDip2px, 0, getResources().getString(R.string.Next_tip), R.layout.layout_t6_top_guide), new ConfirmListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.68
            public AnonymousClass68() {
            }

            @Override // com.petkit.android.activities.petkitBleDevice.w5.guide.ConfirmListener
            public void onConfirmListener() {
                Guide guide = T6HomeActivity.this.firstGuide;
                if (guide != null) {
                    guide.dismiss();
                }
            }
        }));
        Guide guideCreateGuide = guideBuilder.createGuide();
        this.firstGuide = guideCreateGuide;
        guideCreateGuide.show(this);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$67 */
    public class AnonymousClass67 implements GuideBuilder.OnVisibilityChangedListener {
        @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
        public void onShown() {
        }

        public AnonymousClass67() {
        }

        @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
        public void onDismiss() {
            int[] iArr = new int[2];
            DisplayMetrics displayMetrics = new DisplayMetrics();
            T6HomeActivity.this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int i = displayMetrics.heightPixels;
            T6HomeActivity.this.ivChartShow.getLocationOnScreen(iArr);
            int iDip2px2 = (iArr[1] - i) + ArmsUtils.dip2px(T6HomeActivity.this, 170.0f);
            AppBarLayout appBarLayout = T6HomeActivity.this.appBar;
            if (iDip2px2 < 0) {
                iDip2px2 = 0;
            }
            appBarLayout.scrollTo(0, iDip2px2);
            T6HomeActivity.this.showSecondGuide();
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$68 */
    public class AnonymousClass68 implements ConfirmListener {
        public AnonymousClass68() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.w5.guide.ConfirmListener
        public void onConfirmListener() {
            Guide guide = T6HomeActivity.this.firstGuide;
            if (guide != null) {
                guide.dismiss();
            }
        }
    }

    public void showSecondGuide() {
        int iDip2px;
        if (this.secondGuide != null) {
            return;
        }
        GuideBuilder guideBuilder = new GuideBuilder();
        guideBuilder.setTargetView(this.ivTodayEvent).setAlpha(180).setHighTargetCorner(50).setHighTargetPaddingLeft(ArmsUtils.dip2px(this, 3.0f)).setHighTargetPaddingRight(ArmsUtils.dip2px(this, 3.0f)).setHighTargetPaddingBottom(ArmsUtils.dip2px(this, 3.0f)).setHighTargetPaddingTop(ArmsUtils.dip2px(this, 3.0f)).setOutsideTouchable(false).setAutoDismiss(true);
        guideBuilder.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.69
            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onShown() {
            }

            public AnonymousClass69() {
            }

            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onDismiss() {
                T6HomeActivity.this.appBar.scrollTo(0, 0);
                T6HomeActivity.this.showThirdGuide();
            }
        });
        if ("zh_CN".equals(UserInforUtils.getLanguageLocaleString(CommonUtils.getAppContext()))) {
            iDip2px = ArmsUtils.dip2px(this, 30.0f);
        } else {
            iDip2px = ArmsUtils.dip2px(this, 40.0f);
        }
        guideBuilder.addComponent(new GuideD4shBottomRightTipAndBtnAndLineComponent(new GuideParam(getResources().getString(R.string.T6_home_guide_tips2), "4/5", 4, 32, iDip2px, 0, getResources().getString(R.string.Next_tip), R.layout.layout_d4sh_guide_bottom_right), new ConfirmListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.70
            public AnonymousClass70() {
            }

            @Override // com.petkit.android.activities.petkitBleDevice.w5.guide.ConfirmListener
            public void onConfirmListener() {
                Guide guide = T6HomeActivity.this.secondGuide;
                if (guide != null) {
                    guide.dismiss();
                }
            }
        }));
        Guide guideCreateGuide = guideBuilder.createGuide();
        this.secondGuide = guideCreateGuide;
        guideCreateGuide.show(this);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$69 */
    public class AnonymousClass69 implements GuideBuilder.OnVisibilityChangedListener {
        @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
        public void onShown() {
        }

        public AnonymousClass69() {
        }

        @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
        public void onDismiss() {
            T6HomeActivity.this.appBar.scrollTo(0, 0);
            T6HomeActivity.this.showThirdGuide();
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$70 */
    public class AnonymousClass70 implements ConfirmListener {
        public AnonymousClass70() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.w5.guide.ConfirmListener
        public void onConfirmListener() {
            Guide guide = T6HomeActivity.this.secondGuide;
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
        guideBuilder.setTargetView(this.t6BottomView.findViewById(R.id.ll_more)).setAlpha(180).setHighTargetCorner(50).setHighTargetPaddingLeft(ArmsUtils.dip2px(this, 3.0f)).setHighTargetPaddingRight(ArmsUtils.dip2px(this, 3.0f)).setHighTargetPaddingBottom(ArmsUtils.dip2px(this, 3.0f)).setHighTargetPaddingTop(ArmsUtils.dip2px(this, 3.0f)).setOutsideTouchable(false).setAutoDismiss(true);
        guideBuilder.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.71
            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onShown() {
            }

            public AnonymousClass71() {
            }

            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onDismiss() {
                DataHelper.setBooleanSF(T6HomeActivity.this.getApplicationContext(), Constants.T6_HOME_GUIDE, Boolean.TRUE);
            }
        });
        TextUtils.isEmpty(DataHelper.getStringSF(this, Consts.SHARED_SETTING_LANGUAGE));
        guideBuilder.addComponent(new GuideTipAndBtnAndLineComponent(new GuideParam(getResources().getString(R.string.T6_home_guide_tips3), "5/5", 2, 48, ArmsUtils.dip2px(this, 0.0f), 0, getResources().getString(R.string.Know), R.layout.layout_t6_top_guide), new ConfirmListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.72
            public AnonymousClass72() {
            }

            @Override // com.petkit.android.activities.petkitBleDevice.w5.guide.ConfirmListener
            public void onConfirmListener() {
                Guide guide = T6HomeActivity.this.thirdGuide;
                if (guide != null) {
                    guide.dismiss();
                }
            }
        }));
        Guide guideCreateGuide = guideBuilder.createGuide();
        this.thirdGuide = guideCreateGuide;
        guideCreateGuide.show(this);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$71 */
    public class AnonymousClass71 implements GuideBuilder.OnVisibilityChangedListener {
        @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
        public void onShown() {
        }

        public AnonymousClass71() {
        }

        @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
        public void onDismiss() {
            DataHelper.setBooleanSF(T6HomeActivity.this.getApplicationContext(), Constants.T6_HOME_GUIDE, Boolean.TRUE);
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$72 */
    public class AnonymousClass72 implements ConfirmListener {
        public AnonymousClass72() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.w5.guide.ConfirmListener
        public void onConfirmListener() {
            Guide guide = T6HomeActivity.this.thirdGuide;
            if (guide != null) {
                guide.dismiss();
            }
        }
    }

    @Subscriber
    public void d4shVlogDelete(D4shVlogDeleteMsg d4shVlogDeleteMsg) {
        T6VlogRecordAdapter t6VlogRecordAdapter = this.t6VlogRecordAdapter;
        if (t6VlogRecordAdapter != null) {
            t6VlogRecordAdapter.removeAll();
        }
        ((T6HomePresenter) this.mPresenter).getHighLightRecord(this.deviceId, this.deviceType, false);
    }

    @Subscriber
    public void vlogStateChanged(VlogStateChanged vlogStateChanged) {
        if (vlogStateChanged.isRefreshRemoteData()) {
            T6VlogRecordAdapter t6VlogRecordAdapter = this.t6VlogRecordAdapter;
            if (t6VlogRecordAdapter != null) {
                t6VlogRecordAdapter.removeAll();
            }
            ((T6HomePresenter) this.mPresenter).getHighLightRecord(this.deviceId, this.deviceType, false);
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
            this.llDeviceMode.setVisibility(8);
            return;
        }
        if (overall == 3) {
            this.toolbarTitleStatusOne.setText(getString(R.string.Mate_ota));
            this.toolbarTitleStatusOne.setBackgroundResource(R.drawable.solid_t4_status_green);
            this.toolbarTitleStatusOne.setVisibility(0);
            this.toolbarTitleStatusTwo.setVisibility(8);
            this.toolbarTitleStatusOne.setOnClickListener(null);
            this.llDeviceMode.setVisibility(8);
            return;
        }
        if (overall == 4) {
            this.toolbarTitleStatusOne.setText(getString(R.string.State_error));
            this.toolbarTitleStatusOne.setBackgroundResource(R.drawable.solid_t4_status_gray);
            this.toolbarTitleStatusOne.setVisibility(0);
            this.toolbarTitleStatusTwo.setVisibility(8);
            this.toolbarTitleStatusOne.setOnClickListener(null);
            this.llDeviceMode.setVisibility(8);
            return;
        }
        if (t6Record.getState().getPower() == 0) {
            this.toolbarTitleStatusOne.setText(getString(R.string.Power_off));
            this.toolbarTitleStatusOne.setBackgroundResource(R.drawable.solid_t4_status_gray);
            this.toolbarTitleStatusOne.setVisibility(0);
            this.toolbarTitleStatusTwo.setVisibility(8);
            this.toolbarTitleStatusOne.setOnClickListener(null);
            this.llDeviceMode.setVisibility(8);
            return;
        }
        ArrayList arrayList = new ArrayList();
        String string = getString(R.string.Kitten_protection);
        String string2 = getString(R.string.Auto_clean);
        String string3 = getString(R.string.Regular_clean);
        for (int i = 0; i < 6 && arrayList.size() < 2; i++) {
            if (i != 0) {
                if (i == 1) {
                    if (t6Record.getSettings().getKitten() == 0 && t6Record.getSettings().getAutoWork() == 1) {
                        arrayList.add(string2);
                        this.toolbarTitleStatusOne.setBackgroundResource(R.drawable.solid_t4_status_red);
                    }
                } else if (i == 3 && t6Record.getSettings().getKitten() == 0 && t6Record.getSettings().getFixedTimeClear() == 1) {
                    arrayList.add(string3);
                    if (arrayList.size() == 1) {
                        this.toolbarTitleStatusOne.setBackgroundResource(R.drawable.solid_t4_status_red);
                    } else {
                        this.toolbarTitleStatusTwo.setBackgroundResource(R.drawable.solid_t4_status_red);
                    }
                }
            } else if (t6Record.getSettings().getKitten() == 1) {
                arrayList.add(string);
                this.toolbarTitleStatusOne.setBackgroundResource(R.drawable.solid_t4_status_dark_green);
                this.toolbarTitleStatusOne.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$$ExternalSyntheticLambda3
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$setDeviceStatus$57(view);
                    }
                });
            }
        }
        this.toolbarTitleStatusOne.setVisibility(8);
        this.toolbarTitleStatusTwo.setVisibility(8);
        this.llDeviceMode.setVisibility(0);
        if (arrayList.size() == 1) {
            this.ivDeviceModeIconOne.setVisibility(0);
            this.ivDeviceModeIconTwo.setVisibility(8);
            if (string.equals(arrayList.get(0))) {
                this.ivDeviceModeIconOne.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$$ExternalSyntheticLambda4
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$setDeviceStatus$58(view);
                    }
                });
                this.ivDeviceModeIconOne.setImageResource(R.drawable.toilet_kitten_protection_icon);
                return;
            } else if (string2.equals(arrayList.get(0))) {
                this.ivDeviceModeIconOne.setOnClickListener(null);
                this.ivDeviceModeIconOne.setImageResource(R.drawable.toilet_auto_clean_icon);
                return;
            } else {
                if (string3.equals(arrayList.get(0))) {
                    this.ivDeviceModeIconOne.setOnClickListener(null);
                    this.ivDeviceModeIconOne.setImageResource(R.drawable.toilet_regular_clean_icon);
                    return;
                }
                return;
            }
        }
        if (arrayList.size() == 2) {
            this.ivDeviceModeIconOne.setVisibility(0);
            this.ivDeviceModeIconTwo.setVisibility(0);
            if (string.equals(arrayList.get(0))) {
                this.ivDeviceModeIconOne.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$$ExternalSyntheticLambda5
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$setDeviceStatus$59(view);
                    }
                });
                this.ivDeviceModeIconOne.setImageResource(R.drawable.toilet_kitten_protection_icon);
            } else if (string2.equals(arrayList.get(0))) {
                this.ivDeviceModeIconOne.setOnClickListener(null);
                this.ivDeviceModeIconOne.setImageResource(R.drawable.toilet_auto_clean_icon);
            } else if (string3.equals(arrayList.get(0))) {
                this.ivDeviceModeIconOne.setOnClickListener(null);
                this.ivDeviceModeIconOne.setImageResource(R.drawable.toilet_regular_clean_icon);
            }
            if (string.equals(arrayList.get(1))) {
                this.ivDeviceModeIconTwo.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$$ExternalSyntheticLambda6
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$setDeviceStatus$60(view);
                    }
                });
                this.ivDeviceModeIconTwo.setImageResource(R.drawable.toilet_kitten_protection_icon);
                return;
            } else if (string2.equals(arrayList.get(1))) {
                this.ivDeviceModeIconTwo.setOnClickListener(null);
                this.ivDeviceModeIconTwo.setImageResource(R.drawable.toilet_auto_clean_icon);
                return;
            } else {
                if (string3.equals(arrayList.get(1))) {
                    this.ivDeviceModeIconTwo.setOnClickListener(null);
                    this.ivDeviceModeIconTwo.setImageResource(R.drawable.toilet_regular_clean_icon);
                    return;
                }
                return;
            }
        }
        this.ivDeviceModeIconOne.setVisibility(8);
        this.ivDeviceModeIconTwo.setVisibility(8);
    }

    public /* synthetic */ void lambda$setDeviceStatus$57(View view) {
        showKittenProtectionWindow();
    }

    public /* synthetic */ void lambda$setDeviceStatus$58(View view) {
        showKittenProtectionWindow();
    }

    public /* synthetic */ void lambda$setDeviceStatus$59(View view) {
        showKittenProtectionWindow();
    }

    public /* synthetic */ void lambda$setDeviceStatus$60(View view) {
        showKittenProtectionWindow();
    }

    public void showKittenProtectionWindow() {
        KittenProtectionWindow kittenProtectionWindow = this.kittenProtectionWindow;
        if (kittenProtectionWindow == null || !kittenProtectionWindow.isShowing()) {
            KittenProtectionWindow kittenProtectionWindow2 = new KittenProtectionWindow(this, 27, false, R.color.login_new_blue, true);
            this.kittenProtectionWindow = kittenProtectionWindow2;
            kittenProtectionWindow2.showAtLocation(getWindow().getDecorView(), 17, 0, 0);
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$73 */
    public class AnonymousClass73 implements ThreeChoicesWindow.OnClickThreeChoices {
        public AnonymousClass73() {
        }

        @Override // com.petkit.android.activities.device.widget.ThreeChoicesWindow.OnClickThreeChoices
        public void onClickFirstChoice() {
            T6HomeActivity t6HomeActivity = T6HomeActivity.this;
            t6HomeActivity.launchActivity(T6WorkingTimeActivity.newIntent(t6HomeActivity, t6HomeActivity.deviceId, T6HomeActivity.this.deviceType));
            T6HomeActivity.this.threeChoicesWindow.dismiss();
        }

        @Override // com.petkit.android.activities.device.widget.ThreeChoicesWindow.OnClickThreeChoices
        public void onClickSecondChoice() {
            T6HomeActivity.this.startCameraAnim();
            ((T6HomePresenter) ((BaseActivity) T6HomeActivity.this).mPresenter).temporaryOpenCamera();
            T6HomeActivity.this.show5MinWindowTips();
            T6HomeActivity.this.threeChoicesWindow.dismiss();
        }

        @Override // com.petkit.android.activities.device.widget.ThreeChoicesWindow.OnClickThreeChoices
        public void onClickThirdChoice() {
            T6HomeActivity.this.threeChoicesWindow.dismiss();
        }
    }

    public void showTurnOnCameraWindow() {
        if (this.threeChoicesWindow == null) {
            ThreeChoicesWindow threeChoicesWindow = new ThreeChoicesWindow(this, new ThreeChoicesWindow.OnClickThreeChoices() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.73
                public AnonymousClass73() {
                }

                @Override // com.petkit.android.activities.device.widget.ThreeChoicesWindow.OnClickThreeChoices
                public void onClickFirstChoice() {
                    T6HomeActivity t6HomeActivity = T6HomeActivity.this;
                    t6HomeActivity.launchActivity(T6WorkingTimeActivity.newIntent(t6HomeActivity, t6HomeActivity.deviceId, T6HomeActivity.this.deviceType));
                    T6HomeActivity.this.threeChoicesWindow.dismiss();
                }

                @Override // com.petkit.android.activities.device.widget.ThreeChoicesWindow.OnClickThreeChoices
                public void onClickSecondChoice() {
                    T6HomeActivity.this.startCameraAnim();
                    ((T6HomePresenter) ((BaseActivity) T6HomeActivity.this).mPresenter).temporaryOpenCamera();
                    T6HomeActivity.this.show5MinWindowTips();
                    T6HomeActivity.this.threeChoicesWindow.dismiss();
                }

                @Override // com.petkit.android.activities.device.widget.ThreeChoicesWindow.OnClickThreeChoices
                public void onClickThirdChoice() {
                    T6HomeActivity.this.threeChoicesWindow.dismiss();
                }
            }, getResources().getString(R.string.T6_open_camera_tip), getResources().getString(R.string.Open_whole_day_camera), getResources().getString(R.string.Camera_open_five_minute), getResources().getString(R.string.Cancel));
            this.threeChoicesWindow = threeChoicesWindow;
            threeChoicesWindow.setThreeChoicesTextColor(getResources().getColor(R.color.new_bind_blue), getResources().getColor(R.color.new_bind_blue), getResources().getColor(R.color.gray));
        }
        this.threeChoicesWindow.show(getWindow().getDecorView());
    }

    public void show5MinWindowTips() {
        new NewIKnowWindow(this, "", getResources().getString(R.string.Open_temp_five_mins_desc), "").show(getWindow().getDecorView());
    }

    private void showFlipCameraAnim(boolean z) {
        if (this.showTurnCamera) {
            return;
        }
        this.showTurnCamera = true;
        if (this.maintenanceAnimator == null) {
            Animator animatorLoadAnimator = AnimatorInflater.loadAnimator(this, R.animator.maintenance_mode_loading_infinite);
            this.maintenanceAnimator = animatorLoadAnimator;
            animatorLoadAnimator.setTarget(this.ivCameraSwitchLoading);
        }
        if (this.maintenanceAnimator.isRunning()) {
            return;
        }
        this.ivCameraSwitchLoading.setVisibility(0);
        this.maintenanceAnimator.start();
    }

    private void hideFlipCameraAnim() {
        P p = this.mPresenter;
        if (p != 0 && ((T6HomePresenter) p).getLiveService() != null) {
            if (CommonUtil.getLong(this.t6Record.getFirmware()) < 600.0d) {
                ((AgoraLiveService) ((T6HomePresenter) this.mPresenter).getLiveService()).setTurning(false);
            } else {
                ((RtcService) ((T6HomePresenter) this.mPresenter).getLiveService()).setTurning(false);
            }
        }
        MyHandler myHandler = this.handler;
        if (myHandler != null) {
            myHandler.removeMessages(17);
            this.handler.removeMessages(18);
        }
        this.showTurnCamera = false;
        Animator animator = this.maintenanceAnimator;
        if (animator != null) {
            animator.cancel();
        }
        this.ivCameraSwitchLoading.clearAnimation();
        this.ivCameraSwitchLoading.setVisibility(8);
    }

    private void startAppBarScroll() {
        MyHandler myHandler = this.handler;
        if (myHandler != null) {
            myHandler.removeMessages(20);
        }
        MyHandler myHandler2 = this.handler;
        if (myHandler2 != null) {
            myHandler2.sendEmptyMessage(19);
        }
    }

    @Subscriber
    public void finishHomePage(T6FinishDevicePageMsg t6FinishDevicePageMsg) {
        killMyself();
    }

    @Subscriber
    public void serviceUpdate(ServiceUpdateEvent serviceUpdateEvent) {
        P p;
        if (isFinishing() || (p = this.mPresenter) == 0) {
            return;
        }
        ((T6HomePresenter) p).getT6DeviceDetail(this.deviceId);
        Intent intent = new Intent(T6Utils.BROADCAST_T6_STATE_MSG);
        intent.putExtra(Constants.EXTRA_DEVICE_ID, this.deviceId);
        intent.putExtra(Constants.EXTRA_BOOLEAN, true);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        ((T6HomePresenter) this.mPresenter).initT6DeviceDetail();
    }

    private boolean breakdown() {
        return (this.t6Record.getState() == null || TextUtils.isEmpty(this.t6Record.getState().getErrorCode())) ? false : true;
    }

    public void showDisableCleanUpWindow() {
        SimpleTipWindow simpleTipWindow = this.disableCleanUpWindow;
        if (simpleTipWindow == null || !simpleTipWindow.isShowing()) {
            SimpleTipWindow simpleTipWindow2 = new SimpleTipWindow(this, getString(R.string.Disable_auto_clean_when_kitten_protection), true, R.color.login_new_blue, true);
            this.disableCleanUpWindow = simpleTipWindow2;
            simpleTipWindow2.showAtLocation(getWindow().getDecorView(), 17, 0, 0);
        }
    }

    @Subscriber
    public void refreshSD(RefreshSdEvent refreshSdEvent) {
        if (isFinishing() || refreshSdEvent == null) {
            return;
        }
        this.refreshSd = true;
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$74 */
    public class AnonymousClass74 implements DownloadingWindow.OnClick {
        @Override // com.petkit.android.activities.petkitBleDevice.download.widget.DownloadingWindow.OnClick
        public void onBackgroundClick() {
        }

        public AnonymousClass74() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.download.widget.DownloadingWindow.OnClick
        public void onDismissClick() {
            T6HomeActivity.this.finish();
        }

        @Override // com.petkit.android.activities.petkitBleDevice.download.widget.DownloadingWindow.OnClick
        public boolean isAllowDismiss() {
            return FileUtils.checkAgoraFile() == 2;
        }

        @Override // com.petkit.android.activities.petkitBleDevice.download.widget.DownloadingWindow.OnClick
        public void onCloseClick() {
            T6HomeActivity.this.killMyself();
        }
    }

    public void openDownloadingWindow() {
        if (this.downloadingWindow == null) {
            this.downloadingWindow = new DownloadingWindow(this, getString(R.string.BT_initing) + " 0%...", getString(R.string.D4sh_download_agora_tip), new DownloadingWindow.OnClick() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.74
                @Override // com.petkit.android.activities.petkitBleDevice.download.widget.DownloadingWindow.OnClick
                public void onBackgroundClick() {
                }

                public AnonymousClass74() {
                }

                @Override // com.petkit.android.activities.petkitBleDevice.download.widget.DownloadingWindow.OnClick
                public void onDismissClick() {
                    T6HomeActivity.this.finish();
                }

                @Override // com.petkit.android.activities.petkitBleDevice.download.widget.DownloadingWindow.OnClick
                public boolean isAllowDismiss() {
                    return FileUtils.checkAgoraFile() == 2;
                }

                @Override // com.petkit.android.activities.petkitBleDevice.download.widget.DownloadingWindow.OnClick
                public void onCloseClick() {
                    T6HomeActivity.this.killMyself();
                }
            });
        }
        if (this.downloadingWindow.isShowing()) {
            return;
        }
        this.handler.postDelayed(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.75
            public AnonymousClass75() {
            }

            @Override // java.lang.Runnable
            public void run() {
                T6HomeActivity.this.downloadingWindow.show(T6HomeActivity.this.getWindow().getDecorView());
            }
        }, 600L);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$75 */
    public class AnonymousClass75 implements Runnable {
        public AnonymousClass75() {
        }

        @Override // java.lang.Runnable
        public void run() {
            T6HomeActivity.this.downloadingWindow.show(T6HomeActivity.this.getWindow().getDecorView());
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
                this.handler.postDelayed(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity.76
                    public AnonymousClass76() {
                    }

                    @Override // java.lang.Runnable
                    public void run() {
                        if (FileUtils.checkAgoraFile() != 2 || T6HomeActivity.this.downloadingWindow == null) {
                            return;
                        }
                        T6HomeActivity.this.downloadingWindow.dismiss();
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

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity$76 */
    public class AnonymousClass76 implements Runnable {
        public AnonymousClass76() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (FileUtils.checkAgoraFile() != 2 || T6HomeActivity.this.downloadingWindow == null) {
                return;
            }
            T6HomeActivity.this.downloadingWindow.dismiss();
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.contract.T6HomeContract.View
    public void showFreeTrialDialog() {
        this.serviceMaybeChanged = true;
        Intent intent = new Intent(T6Utils.BROADCAST_T6_STATE_MSG);
        intent.putExtra(Constants.EXTRA_DEVICE_ID, this.deviceId);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        startActivity(CloudServiceFreeTrialActivity.newIntent(this, this.deviceId, this.deviceType, this.d4shBannerData.getFreeActivity().getId()));
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0) {
            int i = this.currentPosition;
            if (i == 0) {
                this.allFragment.hideCatFacePop();
            } else if (i == 1) {
                this.toiletFragment.hideCatFacePop();
            } else if (i == 2) {
                this.petFragment.hideCatFacePop();
            }
        }
        return super.dispatchTouchEvent(motionEvent);
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

    @Subscriber
    public void resetComplete(T6ConsumeEvent t6ConsumeEvent) {
        if (t6ConsumeEvent == null) {
            return;
        }
        setupView(T6Utils.getT6RecordByDeviceId(this.deviceId, 0));
    }
}

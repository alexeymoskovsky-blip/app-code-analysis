package com.petkit.android.activities.petkitBleDevice.w7h;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Layout;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.Group;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;
import com.alibaba.fastjson.JSON;
import com.binioter.guideview.Guide;
import com.binioter.guideview.GuideBuilder;
import com.github.sunnysuperman.commons.utils.CollectionUtil;
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
import com.jess.arms.widget.PetkitToast;
import com.jess.arms.widget.imageloader.ImageCacheSimple;
import com.petkit.android.activities.adapter.HorizontalLiveViewPagerAdapter;
import com.petkit.android.activities.appwidget.mode.large.WidgetDataInfo;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.cloudservice.CloudServiceFreeTrialActivity;
import com.petkit.android.activities.cloudservice.ServiceManagementActivity;
import com.petkit.android.activities.cloudservice.mode.ServiceUpdateEvent;
import com.petkit.android.activities.common.utils.DeviceCenterUtils;
import com.petkit.android.activities.device.DeviceSetInfoActivity;
import com.petkit.android.activities.device.widget.ThreeChoicesWindow;
import com.petkit.android.activities.family.mode.FamilyInfor;
import com.petkit.android.activities.home.mode.CloudServiceDelayTimeResult;
import com.petkit.android.activities.home.utils.GuideD4shBottomRightTipAndBtnAndLineComponent;
import com.petkit.android.activities.mall.mode.PromoteData;
import com.petkit.android.activities.mall.widget.PromoteView;
import com.petkit.android.activities.permission.PermissionDialogActivity;
import com.petkit.android.activities.petkitBleDevice.BleDeviceBindNetWorkActivity;
import com.petkit.android.activities.petkitBleDevice.BleDeviceWifiSettingActivity;
import com.petkit.android.activities.petkitBleDevice.PetColorSettingActivity;
import com.petkit.android.activities.petkitBleDevice.PetWeightActivity;
import com.petkit.android.activities.petkitBleDevice.ctw3.mode.DistributionDiagram;
import com.petkit.android.activities.petkitBleDevice.ctw3.widget.DrinkWaterWindow;
import com.petkit.android.activities.petkitBleDevice.d4sh.mode.BannerStateCache;
import com.petkit.android.activities.petkitBleDevice.d4sh.mode.D4shBannerData;
import com.petkit.android.activities.petkitBleDevice.d4sh.mode.D4shVlogDeleteMsg;
import com.petkit.android.activities.petkitBleDevice.d4sh.model.VlogStateChanged;
import com.petkit.android.activities.petkitBleDevice.d4sh.utils.D4shUtils;
import com.petkit.android.activities.petkitBleDevice.d4sh.utils.VlogUtils;
import com.petkit.android.activities.petkitBleDevice.d4sh.widget.NewWifiWeakWindow;
import com.petkit.android.activities.petkitBleDevice.download.LocalAlbumActivity;
import com.petkit.android.activities.petkitBleDevice.download.MediaDisplayActivity;
import com.petkit.android.activities.petkitBleDevice.download.mode.AgoraDownloadMsg;
import com.petkit.android.activities.petkitBleDevice.download.mode.AgoraStartDownloadMsg;
import com.petkit.android.activities.petkitBleDevice.download.utils.FileUtil;
import com.petkit.android.activities.petkitBleDevice.download.utils.VideoDownloadManager;
import com.petkit.android.activities.petkitBleDevice.download.widget.DownloadingWindow;
import com.petkit.android.activities.petkitBleDevice.mode.FeederPlayerLandscapeSelectorWindowBuilder;
import com.petkit.android.activities.petkitBleDevice.mode.HighLightRecordRsp;
import com.petkit.android.activities.petkitBleDevice.mode.HighlightRecord;
import com.petkit.android.activities.petkitBleDevice.mode.OtaResult;
import com.petkit.android.activities.petkitBleDevice.mode.UpdatePetColorMsg;
import com.petkit.android.activities.petkitBleDevice.r2.widget.NewIKnowWindow;
import com.petkit.android.activities.petkitBleDevice.t4.mode.RelatedProductsInfor;
import com.petkit.android.activities.petkitBleDevice.t6.T6EventListFragment;
import com.petkit.android.activities.petkitBleDevice.t6.adapter.T6VideoPlayFragmentAdapter;
import com.petkit.android.activities.petkitBleDevice.t6.mode.DeleteEvent;
import com.petkit.android.activities.petkitBleDevice.t6.mode.DeviceError;
import com.petkit.android.activities.petkitBleDevice.t6.mode.T6GuideInfo;
import com.petkit.android.activities.petkitBleDevice.t6.mode.T6RecordFragmentInfor;
import com.petkit.android.activities.petkitBleDevice.t6.mode.UpdatePetEvent;
import com.petkit.android.activities.petkitBleDevice.t6.utils.T6AnimUtil;
import com.petkit.android.activities.petkitBleDevice.t6.utils.T6Utils;
import com.petkit.android.activities.petkitBleDevice.t6.widget.MyChartViewPager;
import com.petkit.android.activities.petkitBleDevice.t6.widget.MySmartRefreshView;
import com.petkit.android.activities.petkitBleDevice.t6.widget.PetkitVideoPlayerView;
import com.petkit.android.activities.petkitBleDevice.t7.utils.T7DataUtils;
import com.petkit.android.activities.petkitBleDevice.t7.utils.T7Utils;
import com.petkit.android.activities.petkitBleDevice.utils.ColorUtils;
import com.petkit.android.activities.petkitBleDevice.utils.DeviceVolumeUtils;
import com.petkit.android.activities.petkitBleDevice.utils.FamilyUtils;
import com.petkit.android.activities.petkitBleDevice.utils.PlayerUtils;
import com.petkit.android.activities.petkitBleDevice.utils.TextUtil;
import com.petkit.android.activities.petkitBleDevice.utils.TimeUtils;
import com.petkit.android.activities.petkitBleDevice.utils.WebUtils;
import com.petkit.android.activities.petkitBleDevice.vlog.VlogMakeService;
import com.petkit.android.activities.petkitBleDevice.vlog.mode.VlogM3U8Mode;
import com.petkit.android.activities.petkitBleDevice.w5.guide.ConfirmListener;
import com.petkit.android.activities.petkitBleDevice.w5.guide.GuideParam;
import com.petkit.android.activities.petkitBleDevice.w7h.W7hFullLiveListFragment;
import com.petkit.android.activities.petkitBleDevice.w7h.W7hVideoPlayFragment;
import com.petkit.android.activities.petkitBleDevice.w7h.adapter.EventViewPagerAdapter;
import com.petkit.android.activities.petkitBleDevice.w7h.adapter.W7hHomeBannerPageAdapter;
import com.petkit.android.activities.petkitBleDevice.w7h.adapter.W7hVlogRecordAdapter;
import com.petkit.android.activities.petkitBleDevice.w7h.component.DaggerW7hHomeComponent;
import com.petkit.android.activities.petkitBleDevice.w7h.contract.W7hHomeContract;
import com.petkit.android.activities.petkitBleDevice.w7h.mode.W7hChartInfo;
import com.petkit.android.activities.petkitBleDevice.w7h.mode.W7hContentInfo;
import com.petkit.android.activities.petkitBleDevice.w7h.mode.W7hEventInfo;
import com.petkit.android.activities.petkitBleDevice.w7h.mode.W7hEventTotalVO;
import com.petkit.android.activities.petkitBleDevice.w7h.mode.W7hPetDrinkTipVO;
import com.petkit.android.activities.petkitBleDevice.w7h.mode.W7hRecord;
import com.petkit.android.activities.petkitBleDevice.w7h.mode.W7hStatisticResInfo;
import com.petkit.android.activities.petkitBleDevice.w7h.presenter.W7hHomePresenter;
import com.petkit.android.activities.petkitBleDevice.w7h.utils.W7hDataUtils;
import com.petkit.android.activities.petkitBleDevice.w7h.utils.W7hUtils;
import com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hBannerServiceView;
import com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hBottomView;
import com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hLiveVideoView;
import com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hPlayerSoundWaveView;
import com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hVlogTipWindow;
import com.petkit.android.activities.petkitBleDevice.w7h.widget.WebViewControllerActivity;
import com.petkit.android.activities.petkitBleDevice.widget.BleDeviceHomeOfflineWarnWindow;
import com.petkit.android.activities.petkitBleDevice.widget.BleDeviceHomeTroubleWarnWindow;
import com.petkit.android.activities.petkitBleDevice.widget.CloudServiceFreeTrialDialog;
import com.petkit.android.activities.petkitBleDevice.widget.DeviceErrorWarnWindow;
import com.petkit.android.activities.petkitBleDevice.widget.LoadingWindow;
import com.petkit.android.activities.petkitBleDevice.widget.OtaPromptWindow;
import com.petkit.android.activities.petkitBleDevice.widget.SimpleTipWindow;
import com.petkit.android.activities.petkitBleDevice.widget.datepicker.BasePetkitDeviceDatePickerView;
import com.petkit.android.activities.petkitBleDevice.widget.datepicker.CountBean;
import com.petkit.android.activities.petkitBleDevice.widget.datepicker.DatePickerWindow;
import com.petkit.android.activities.petkitBleDevice.widget.datepicker.PetkitPlayerLandscapeDatePickerView;
import com.petkit.android.activities.registe.utils.AppUtils;
import com.petkit.android.activities.statistics.DrinkStatisticsActivity;
import com.petkit.android.activities.universalWindow.BaseBottomWindow;
import com.petkit.android.activities.universalWindow.NormalCenterTipWindow;
import com.petkit.android.activities.universalWindow.PetFilterWindow;
import com.petkit.android.activities.universalWindow.SpannableStringColorsPicPromptWindow;
import com.petkit.android.activities.universalWindow.SpannableStringColorsWindow;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.media.video.rtmUtil.PetKitQualityUtil;
import com.petkit.android.media.video.rtmUtil.RtmManager;
import com.petkit.android.model.Pet;
import com.petkit.android.utils.AgoraConstants;
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
import com.petkit.oversea.R;
import com.petkit.oversea.databinding.ActivityW7hHomeBinding;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

/* JADX INFO: loaded from: classes5.dex */
public class W7hHomeActivity extends BaseActivity<W7hHomePresenter> implements W7hHomeContract.View, View.OnClickListener {
    public static final int W7H_HEATING_STOP_PET = 101;
    public static final int W7H_SAFE_WARN_PET = 5;

    @Deprecated
    public static final int W7H_SAFE_WARN_REMOVE_WASTEWATER_TRAY = 14;
    public static final int W7H_SAFE_WARN_REMOVE_WATER_TRAY = 9;
    private W7hEventListFragment allFragment;
    private T6AnimUtil animUtil;
    private int appBarOffset;
    private Disposable bannerDisposable;
    private W7hBannerServiceView bannerServiceView;
    private BleDeviceHomeOfflineWarnWindow bleDeviceHomeOfflineWarnWindow;
    private long calendarTime;
    private RelativeLayout chartView;
    private MyChartViewPager chartViewPager;
    private CloudServiceFreeTrialDialog cloudServiceFreeTrialDialog;
    private PetkitPlayerLandscapeDatePickerView datePickerView;
    private DatePickerWindow datePickerWindow;
    private long deviceId;
    private String deviceName;
    private int deviceType;
    private Disposable disposable;
    private DownloadingWindow downloadingWindow;
    private W7hEventListFragment drankFragment;
    private String eventId;
    private FamilyInfor familyInfor;
    private String filePath;
    private Guide firstGuide;
    private AnimationDrawable flamesAnim;
    private T6VideoPlayFragmentAdapter fragmentStateAdapter;
    private DeviceErrorWarnWindow gitWindow;
    private Group groupDate;
    private Group groupList;
    private MyHandler handler;
    private HorizontalLiveViewPagerAdapter horizontalLiveViewPagerAdapter;
    private ImageButton ibSetting;
    private boolean isForeground;
    private boolean isHidePromote;
    public boolean isInLandscape;
    boolean isNeedShowPetError;
    private boolean isNotFirstLoad;
    private boolean isShowRecommendUpgradeWindow;
    private boolean isStartAnim;
    public boolean isStartRecord;
    private ImageView ivHeatStatus;
    private ImageView ivLeftArrow;
    private long landscapeCalendarTime;
    FeederPlayerLandscapeSelectorWindowBuilder landscapeLiveQualitWindowBuilder;
    private Guide lastGuide;
    private View llToolbarStatus;
    private LoadingWindow loadingWindow;
    private W7hFullLiveListFragment lsFragmentAll;
    private W7hFullLiveListFragment lsFragmentDrink;
    private W7hFullLiveListFragment lsFragmentPet;
    private ActivityW7hHomeBinding mBinding;
    private int monthOffset;
    private int newDrankEventNum;
    private int newPetEventNum;
    private Disposable otaDisposable;
    private OtaPromptWindow otaPromptWindow;
    private Disposable paoPaoTimer;
    private PetFilterWindow petFilterWindow;
    private W7hEventListFragment petFragment;
    private int popCount;
    private Disposable recordTimerDisposable;
    private long recordingStartTime;
    private RelatedProductsInfor relatedProductsInfor;
    private RelativeLayout rlChart;
    private int rlPopHeight;
    private int rlPopWidth;
    private Guide secondGuide;
    private int selectedItemPosition;
    public boolean showCameraOutToast;
    private boolean showCare;
    private DeviceErrorWarnWindow showHeatModuleFirstInstallWindow;
    private boolean showList;
    private List<W7hChartInfo> sortEventList;
    private SpannableStringColorsPicPromptWindow speakingWindow;
    private boolean startCollAnim;
    private boolean startExpandAnim;
    private View statusBar;
    private Guide thirdGuide;
    private ThreeChoicesWindow threeChoicesWindow;
    private NewIKnowWindow timezoneWindow;
    private String title;
    private View toolbarRoot;
    private TextView toolbarTitle;
    private TextView tvDeviceTag;
    private TextView tvLandscapeTabAll;
    private TextView tvLandscapeTabDrink;
    private TextView tvLandscapeTabPet;
    private TextView tvLandscapeTodayEvent;
    private TextView tvToolbarStatus;
    private TextView tvY1;
    private TextView tvY2;
    private TextView tvY3;
    private W7hEventInfo w7hEventInfo;
    W7hLiveVideoView w7hLiveVideoView;
    private W7hRecord w7hRecord;
    W7hVlogRecordAdapter w7hVlogRecordAdapter;
    private W7hVlogTipWindow w7hVlogTipWindow;
    private BleDeviceHomeTroubleWarnWindow warnWindow;
    private WidgetDataInfo widgetDataInfo;
    private NewWifiWeakWindow wifiWeakWindow;
    private final int Handler_Action_Up = 13;
    private final int Handler_Action_Down = 14;
    private final int Handler_Bar_Stop = 20;
    private final int Handler_Bar_Scroll = 19;
    private final int Handler_Pao_Pao = 10;
    private final int Handler_Rtm_Token_Expired = 22;
    private final int Handler_Rtm_Login = 21;
    private final int RTM_LOGIN_TIME = 5;
    int topMargin = 50;
    private int currentPosition = 0;
    private int landscapeCurrentEventPosition = -1;
    private int landscapeCurrentPosition = 0;
    List<W7hStatisticResInfo> catChartList = new ArrayList();
    private List<Pet> petList = new ArrayList();
    private List<String> selectedPetIds = new ArrayList();
    private boolean flamesAnimStop = false;
    private BleDeviceHomeOfflineWarnWindow.OfflineClickListener offlineClickListener = new BleDeviceHomeOfflineWarnWindow.OfflineClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity.45
        public AnonymousClass45() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.widget.BleDeviceHomeOfflineWarnWindow.OfflineClickListener
        public void onClickSeeDetails() {
            W7hHomeActivity w7hHomeActivity = W7hHomeActivity.this;
            w7hHomeActivity.launchActivity(BleDeviceWifiSettingActivity.newIntent(w7hHomeActivity, w7hHomeActivity.w7hRecord.getDeviceId(), 29));
        }

        @Override // com.petkit.android.activities.petkitBleDevice.widget.BleDeviceHomeOfflineWarnWindow.OfflineClickListener
        public void onClickReset() {
            W7hHomeActivity w7hHomeActivity = W7hHomeActivity.this;
            w7hHomeActivity.launchActivity(BleDeviceBindNetWorkActivity.newIntent((Context) w7hHomeActivity, w7hHomeActivity.w7hRecord.getDeviceId(), 29, W7hHomeActivity.this.w7hRecord.getBtMac(), false));
        }
    };

    public static /* synthetic */ boolean lambda$checkAndShowPrompts$41(boolean z) {
        return z;
    }

    public static /* synthetic */ boolean lambda$checkAndShowPrompts$42(boolean z) {
        return z;
    }

    public static /* synthetic */ boolean lambda$checkAndShowPrompts$44(boolean z) {
        return z;
    }

    public static /* synthetic */ boolean lambda$checkAndShowPrompts$46(boolean z) {
        return z;
    }

    public static /* synthetic */ boolean lambda$checkAndShowPrompts$48(boolean z) {
        return z;
    }

    public static /* synthetic */ boolean lambda$checkAndShowPrompts$49(boolean z) {
        return z;
    }

    public static /* synthetic */ boolean lambda$checkAndShowPrompts$50(boolean z) {
        return z;
    }

    public static /* synthetic */ void lambda$showHeatModuleFirstInstallWindow$52() {
    }

    public Activity getActivity() {
        return this;
    }

    @Override // com.petkit.android.activities.petkitBleDevice.w7h.contract.W7hHomeContract.View
    public void showToast(int i) {
    }

    @Override // com.jess.arms.base.delegate.IActivity
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerW7hHomeComponent.builder().appComponent(appComponent).view(this).build().inject(this);
    }

    public static Intent newIntent(Context context, long j) {
        Intent intent = new Intent(context, (Class<?>) W7hHomeActivity.class);
        intent.putExtra(Constants.EXTRA_DEVICE_ID, j);
        return intent;
    }

    public static Intent newIntent(Context context, long j, String... strArr) {
        Intent intent = new Intent(context, (Class<?>) W7hHomeActivity.class);
        intent.putExtra(Constants.EXTRA_DEVICE_ID, j);
        if (strArr.length > 0) {
            intent.putExtra(Constants.EXTRA_EVENT_ID, strArr[0]);
        }
        return intent;
    }

    @Override // com.jess.arms.base.delegate.IActivity
    public int initView(Bundle bundle) {
        this.mBinding = (ActivityW7hHomeBinding) DataBindingUtil.setContentView(getActivity(), R.layout.activity_w7h_home);
        return 0;
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBoolean("isLandscape", this.isInLandscape);
    }

    @Override // com.jess.arms.base.delegate.IActivity
    public void initData(Bundle bundle) {
        String stringExtra;
        if (bundle != null) {
            this.deviceId = bundle.getLong(Constants.EXTRA_DEVICE_ID);
            this.deviceType = bundle.getInt(Constants.EXTRA_DEVICE_TYPE, 29);
            this.eventId = bundle.getString(Constants.EXTRA_EVENT_ID);
            stringExtra = bundle.getString(Consts.APP_WIDGET_EXTRA_DATA);
            this.isInLandscape = bundle.getBoolean("isLandscape");
        } else if (getIntent() != null) {
            this.deviceId = getIntent().getLongExtra(Constants.EXTRA_DEVICE_ID, 0L);
            this.deviceType = getIntent().getIntExtra(Constants.EXTRA_DEVICE_TYPE, 29);
            this.eventId = getIntent().getStringExtra(Constants.EXTRA_EVENT_ID);
            this.isInLandscape = getIntent().getBooleanExtra("isLandscape", false);
            stringExtra = getIntent().getStringExtra(Consts.APP_WIDGET_EXTRA_DATA);
        } else {
            stringExtra = null;
        }
        if (stringExtra != null) {
            this.widgetDataInfo = (WidgetDataInfo) new Gson().fromJson(stringExtra, WidgetDataInfo.class);
        }
        ((W7hHomePresenter) this.mPresenter).initPresenter(this.deviceId, 29, this);
        FamilyInfor familyInforThroughDevice = FamilyUtils.getInstance().getFamilyInforThroughDevice(this, this.deviceId, 27);
        this.familyInfor = familyInforThroughDevice;
        if (familyInforThroughDevice == null) {
            this.familyInfor = FamilyUtils.getInstance().getCurrentFamilyInfo(this);
        }
        this.handler = new MyHandler(getMainLooper(), this);
        initViews();
        initBannerParams();
        initTodayTime();
        initAnim();
        this.selectedPetIds.add(ColorUtils.ALL_PET);
        initClickListener();
        initLandscapeGroupView();
        initLandscapeLiveView();
        initPlayer();
        initFragment();
        initBottomView();
        setChartData();
        initSmartView();
        appBarChangedListener();
        ((W7hHomePresenter) this.mPresenter).getEventTotal(this.calendarTime);
        this.toolbarTitle.setTypeface(null, 1);
        initVlogView();
        initFullScreen();
        agoraStartDownload();
    }

    private void agoraStartDownload() {
        if (FileUtils.checkAgoraFile() == 2) {
            setLog("W7h声网.so齐全");
        } else if (FileUtils.checkAgoraFile() == 1) {
            setLog("W7h声网有解压包，但未解压");
        } else {
            openDownloadingWindow();
            EventBus.getDefault().post(new AgoraStartDownloadMsg());
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
                this.handler.postDelayed(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$$ExternalSyntheticLambda46
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$downloadState$0();
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

    public /* synthetic */ void lambda$downloadState$0() {
        DownloadingWindow downloadingWindow;
        if (isFinishing() || FileUtils.checkAgoraFile() != 2 || (downloadingWindow = this.downloadingWindow) == null) {
            return;
        }
        downloadingWindow.dismiss();
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$1 */
    public class AnonymousClass1 implements DownloadingWindow.OnClick {
        @Override // com.petkit.android.activities.petkitBleDevice.download.widget.DownloadingWindow.OnClick
        public void onBackgroundClick() {
        }

        public AnonymousClass1() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.download.widget.DownloadingWindow.OnClick
        public void onDismissClick() {
            W7hHomeActivity.this.finish();
        }

        @Override // com.petkit.android.activities.petkitBleDevice.download.widget.DownloadingWindow.OnClick
        public boolean isAllowDismiss() {
            return FileUtils.checkAgoraFile() == 2;
        }

        @Override // com.petkit.android.activities.petkitBleDevice.download.widget.DownloadingWindow.OnClick
        public void onCloseClick() {
            W7hHomeActivity.this.killMyself();
        }
    }

    public void openDownloadingWindow() {
        if (this.downloadingWindow == null) {
            this.downloadingWindow = new DownloadingWindow(this, getString(R.string.BT_initing) + " 0%...", getString(R.string.D4sh_download_agora_tip), new DownloadingWindow.OnClick() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity.1
                @Override // com.petkit.android.activities.petkitBleDevice.download.widget.DownloadingWindow.OnClick
                public void onBackgroundClick() {
                }

                public AnonymousClass1() {
                }

                @Override // com.petkit.android.activities.petkitBleDevice.download.widget.DownloadingWindow.OnClick
                public void onDismissClick() {
                    W7hHomeActivity.this.finish();
                }

                @Override // com.petkit.android.activities.petkitBleDevice.download.widget.DownloadingWindow.OnClick
                public boolean isAllowDismiss() {
                    return FileUtils.checkAgoraFile() == 2;
                }

                @Override // com.petkit.android.activities.petkitBleDevice.download.widget.DownloadingWindow.OnClick
                public void onCloseClick() {
                    W7hHomeActivity.this.killMyself();
                }
            });
        }
        if (this.downloadingWindow.isShowing()) {
            return;
        }
        this.handler.postDelayed(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$$ExternalSyntheticLambda20
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$openDownloadingWindow$1();
            }
        }, 600L);
    }

    public /* synthetic */ void lambda$openDownloadingWindow$1() {
        if (isFinishing()) {
            return;
        }
        this.downloadingWindow.show(getWindow().getDecorView());
    }

    private void setLog(String str) {
        PetkitLog.d("W7hHomeLive", str);
        LogcatStorageHelper.addLog("W7hHome：" + str);
    }

    @Override // com.jess.arms.base.BaseActivity
    public void initFullScreen() {
        StringBuilder sb = new StringBuilder();
        sb.append("w7h home Build.VERSION.SDK_INT = ");
        int i = Build.VERSION.SDK_INT;
        sb.append(i);
        PetkitLog.i(sb.toString());
        if (i >= 35) {
            this.statusBar.setVisibility(0);
            WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView()).setAppearanceLightNavigationBars(true);
            getWindow().setBackgroundDrawableResource(android.R.color.white);
            ViewCompat.setOnApplyWindowInsetsListener(getWindow().getDecorView(), new OnApplyWindowInsetsListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$$ExternalSyntheticLambda35
                @Override // androidx.core.view.OnApplyWindowInsetsListener
                public final WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                    return this.f$0.lambda$initFullScreen$2(view, windowInsetsCompat);
                }
            });
            WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView()).setAppearanceLightStatusBars(true);
            return;
        }
        this.statusBar.setVisibility(8);
    }

    public /* synthetic */ WindowInsetsCompat lambda$initFullScreen$2(View view, WindowInsetsCompat windowInsetsCompat) {
        Insets insets = windowInsetsCompat.getInsets(WindowInsetsCompat.Type.systemBars());
        int i = insets.top;
        int i2 = insets.bottom;
        PetkitLog.d("initFullScreen statusBarHeight= " + i + "");
        PetkitLog.d("initFullScreen navBarHeight= " + i2 + "");
        PetkitLog.d("initFullScreen getScreenBarHeight= " + getScreenBarHeight() + "");
        view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), i2);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.statusBar.getLayoutParams();
        layoutParams.height = i;
        this.statusBar.setLayoutParams(layoutParams);
        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) this.mBinding.rlTopTab.getLayoutParams();
        layoutParams2.topMargin = ArmsUtils.dip2px(getActivity(), 40.0f) + i;
        this.mBinding.rlTopTab.setLayoutParams(layoutParams2);
        RelativeLayout.LayoutParams layoutParams3 = (RelativeLayout.LayoutParams) this.mBinding.ivTodayEvent2.getLayoutParams();
        layoutParams3.topMargin = ArmsUtils.dip2px(getActivity(), 12.0f) + i;
        this.mBinding.ivTodayEvent2.setLayoutParams(layoutParams3);
        return windowInsetsCompat;
    }

    public int getScreenBarHeight() {
        WindowInsets rootWindowInsets;
        if (getActivity() == null || getActivity().getWindow() == null || Build.VERSION.SDK_INT < 35 || (rootWindowInsets = getActivity().getWindow().getDecorView().getRootWindowInsets()) == null || rootWindowInsets.getDisplayCutout() == null) {
            return 0;
        }
        return rootWindowInsets.getDisplayCutout().getSafeInsetTop();
    }

    @Override // com.petkit.android.activities.petkitBleDevice.w7h.contract.W7hHomeContract.View
    public void sendStopFireAnimation() {
        MyHandler myHandler = this.handler;
        if (myHandler != null) {
            myHandler.sendEmptyMessageDelayed(101, 5000L);
        }
    }

    private void initBannerParams() {
        ViewGroup.LayoutParams layoutParams = this.mBinding.flHomeBanner.getLayoutParams();
        layoutParams.height = ((BaseApplication.displayMetrics.widthPixels - ArmsUtils.dip2px(CommonUtils.getAppContext(), 16.0f)) / 343) * 64;
        this.mBinding.flHomeBanner.setLayoutParams(layoutParams);
    }

    @Override // com.petkit.android.activities.petkitBleDevice.w7h.contract.W7hHomeContract.View
    public void initEventPush() {
        if (TextUtils.isEmpty(this.eventId) || this.eventId.equals("null") || this.eventId.equals("Null") || this.eventId.equals("NULL")) {
            return;
        }
        ((W7hHomePresenter) this.mPresenter).getEventMediaInfo(this.deviceId, this.eventId);
        this.eventId = null;
    }

    @Override // com.petkit.android.activities.petkitBleDevice.w7h.contract.W7hHomeContract.View
    public void unAgree() {
        killMyself();
    }

    private void initViews() {
        this.chartViewPager = (MyChartViewPager) findViewById(R.id.vp);
        this.ivLeftArrow = (ImageView) findViewById(R.id.iv_left_arrow);
        this.rlChart = (RelativeLayout) findViewById(R.id.rl_chart);
        this.tvY1 = (TextView) findViewById(R.id.tv_y1);
        this.tvY2 = (TextView) findViewById(R.id.tv_y2);
        this.tvY3 = (TextView) findViewById(R.id.tv_y3);
        this.datePickerView = (PetkitPlayerLandscapeDatePickerView) findViewById(R.id.w7h_date_picker);
        this.tvDeviceTag = (TextView) findViewById(R.id.tv_device_tag);
        this.toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        this.ibSetting = (ImageButton) findViewById(R.id.ib_setting);
        this.toolbarRoot = findViewById(R.id.toolbarRoot);
        this.statusBar = findViewById(R.id.status_bar);
        this.ivHeatStatus = (ImageView) findViewById(R.id.iv_heat_status);
        this.tvToolbarStatus = (TextView) findViewById(R.id.tv_toolbar_status);
        this.llToolbarStatus = findViewById(R.id.ll_toolbar_status);
        this.toolbarRoot.setBackgroundColor(getResources().getColor(R.color.transparent));
        this.statusBar.setBackgroundColor(getResources().getColor(R.color.transparent));
    }

    @SuppressLint({"ClickableViewAccessibility"})
    private void initClickListener() {
        this.mBinding.rlRoot.setOnTouchListener(new View.OnTouchListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$$ExternalSyntheticLambda47
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return this.f$0.lambda$initClickListener$3(view, motionEvent);
            }
        });
        this.mBinding.ivTodayEvent.setOnClickListener(this);
        this.mBinding.ivChartShow.setOnClickListener(this);
        this.mBinding.tvTabAll.setOnClickListener(this);
        this.mBinding.tvTabPet.setOnClickListener(this);
        this.mBinding.tvTabDrank.setOnClickListener(this);
        this.mBinding.tvTabAllTop.setOnClickListener(this);
        this.mBinding.tvTabPetTop.setOnClickListener(this);
        this.mBinding.tvTabDrankTop.setOnClickListener(this);
        this.mBinding.llPets.setOnClickListener(this);
        this.mBinding.tvTopPaoPao.setOnClickListener(this);
        this.mBinding.ivLastEvent.setOnClickListener(this);
        this.mBinding.ivNextEvent.setOnClickListener(this);
        this.mBinding.ivTodayEvent2.setOnClickListener(this);
        this.mBinding.tvMoreVlog.setOnClickListener(this);
        this.toolbarRoot.findViewById(R.id.ib_back).setOnClickListener(this);
        this.ibSetting.setOnClickListener(this);
        this.mBinding.rlDeviceErrorTwo.setOnClickListener(this);
        this.mBinding.rlDeviceErrorOne.setOnClickListener(this);
        this.mBinding.rlDeviceState.setOnClickListener(this);
        this.mBinding.tvHighlightsOpen.setOnClickListener(this);
        this.mBinding.tvTodayDrink.setOnClickListener(this);
        this.mBinding.tvAvgDuration.setOnClickListener(this);
        this.mBinding.ivHighlightsClose.setOnClickListener(this);
    }

    public /* synthetic */ boolean lambda$initClickListener$3(View view, MotionEvent motionEvent) {
        if (this.mBinding.rlPop.getVisibility() != 0 && this.mBinding.ivArrow.getVisibility() != 0) {
            return false;
        }
        this.mBinding.rlPop.setVisibility(8);
        return false;
    }

    public void initLandscapeGroupView() {
        this.tvLandscapeTabAll = (TextView) findViewById(R.id.tv_tab_landscape_all);
        this.tvLandscapeTabPet = (TextView) findViewById(R.id.tv_tab_landscape_pet);
        this.tvLandscapeTabDrink = (TextView) findViewById(R.id.tv_tab_landscape_drink);
        this.groupList = (Group) findViewById(R.id.group_list);
        this.groupDate = (Group) findViewById(R.id.group_date);
        this.tvLandscapeTodayEvent = (TextView) findViewById(R.id.tv_landscape_today_event);
        this.bannerServiceView = (W7hBannerServiceView) findViewById(R.id.ll_cloud_service);
        initLandscapeRecordList();
    }

    private void initLandscapeRecordList() {
        FragmentTransaction fragmentTransactionBeginTransaction = getSupportFragmentManager().beginTransaction();
        W7hFullLiveListFragment w7hFullLiveListFragment = new W7hFullLiveListFragment();
        this.lsFragmentAll = w7hFullLiveListFragment;
        w7hFullLiveListFragment.setArguments(getBundle(0));
        this.lsFragmentAll.setEventListListener(new W7hFullLiveListFragment.EventListListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity.2
            @Override // com.petkit.android.activities.petkitBleDevice.w7h.W7hFullLiveListFragment.EventListListener
            public void cancelRedPoint() {
            }

            public AnonymousClass2() {
            }

            @Override // com.petkit.android.activities.petkitBleDevice.w7h.W7hFullLiveListFragment.EventListListener
            public void startPlayVideo(W7hEventInfo w7hEventInfo, int i, List<W7hEventInfo> list, String str) {
                W7hHomeActivity.this.landscapeCurrentEventPosition = -1;
                W7hHomeActivity.this.w7hEventInfo = w7hEventInfo;
                W7hHomeActivity.this.setupLandscapeRecordPlayView(list);
                W7hHomeActivity.this.showLandscapeRecordPlayview();
                W7hHomeActivity.this.hideRightList();
            }
        });
        W7hFullLiveListFragment w7hFullLiveListFragment2 = new W7hFullLiveListFragment();
        this.lsFragmentPet = w7hFullLiveListFragment2;
        w7hFullLiveListFragment2.setEventListListener(new W7hFullLiveListFragment.EventListListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity.3
            public AnonymousClass3() {
            }

            @Override // com.petkit.android.activities.petkitBleDevice.w7h.W7hFullLiveListFragment.EventListListener
            public void startPlayVideo(W7hEventInfo w7hEventInfo, int i, List<W7hEventInfo> list, String str) {
                W7hHomeActivity.this.w7hEventInfo = w7hEventInfo;
                W7hHomeActivity.this.setupLandscapeRecordPlayView(list);
                W7hHomeActivity.this.showLandscapeRecordPlayview();
                W7hHomeActivity.this.hideRightList();
            }

            @Override // com.petkit.android.activities.petkitBleDevice.w7h.W7hFullLiveListFragment.EventListListener
            public void cancelRedPoint() {
                W7hHomeActivity.this.newPetEventNum = 0;
            }
        });
        W7hFullLiveListFragment w7hFullLiveListFragment3 = new W7hFullLiveListFragment();
        this.lsFragmentDrink = w7hFullLiveListFragment3;
        w7hFullLiveListFragment3.setEventListListener(new W7hFullLiveListFragment.EventListListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity.4
            public AnonymousClass4() {
            }

            @Override // com.petkit.android.activities.petkitBleDevice.w7h.W7hFullLiveListFragment.EventListListener
            public void startPlayVideo(W7hEventInfo w7hEventInfo, int i, List<W7hEventInfo> list, String str) {
                W7hHomeActivity.this.w7hEventInfo = w7hEventInfo;
                W7hHomeActivity.this.setupLandscapeRecordPlayView(list);
                W7hHomeActivity.this.showLandscapeRecordPlayview();
                W7hHomeActivity.this.hideRightList();
            }

            @Override // com.petkit.android.activities.petkitBleDevice.w7h.W7hFullLiveListFragment.EventListListener
            public void cancelRedPoint() {
                W7hHomeActivity.this.newDrankEventNum = 0;
            }
        });
        fragmentTransactionBeginTransaction.add(R.id.rl_list_content, this.lsFragmentAll);
        fragmentTransactionBeginTransaction.commit();
        selectedLandscapeFragment(0);
        this.tvLandscapeTabAll.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$$ExternalSyntheticLambda38
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initLandscapeRecordList$4(view);
            }
        });
        this.tvLandscapeTabPet.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$$ExternalSyntheticLambda39
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initLandscapeRecordList$5(view);
            }
        });
        this.tvLandscapeTabDrink.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$$ExternalSyntheticLambda40
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initLandscapeRecordList$6(view);
            }
        });
        findViewById(R.id.iv_landscape_today_event).setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$$ExternalSyntheticLambda41
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initLandscapeRecordList$7(view);
            }
        });
        findViewById(R.id.iv_date_back).setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$$ExternalSyntheticLambda42
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initLandscapeRecordList$8(view);
            }
        });
        this.datePickerView.setListener(new BasePetkitDeviceDatePickerView.OnCalendarChangeListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$$ExternalSyntheticLambda43
            @Override // com.petkit.android.activities.petkitBleDevice.widget.datepicker.BasePetkitDeviceDatePickerView.OnCalendarChangeListener
            public final void pageChange(int i) {
                this.f$0.lambda$initLandscapeRecordList$9(i);
            }
        });
        this.datePickerView.setSelectListener(new BasePetkitDeviceDatePickerView.OnCalendarSelectListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$$ExternalSyntheticLambda44
            @Override // com.petkit.android.activities.petkitBleDevice.widget.datepicker.BasePetkitDeviceDatePickerView.OnCalendarSelectListener
            public final void dateSelect(String str) {
                this.f$0.landscapeDateSelected(str);
            }
        });
        ((W7hHomePresenter) this.mPresenter).getLandscapeEventTotal(this.landscapeCalendarTime);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$2 */
    public class AnonymousClass2 implements W7hFullLiveListFragment.EventListListener {
        @Override // com.petkit.android.activities.petkitBleDevice.w7h.W7hFullLiveListFragment.EventListListener
        public void cancelRedPoint() {
        }

        public AnonymousClass2() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.w7h.W7hFullLiveListFragment.EventListListener
        public void startPlayVideo(W7hEventInfo w7hEventInfo, int i, List<W7hEventInfo> list, String str) {
            W7hHomeActivity.this.landscapeCurrentEventPosition = -1;
            W7hHomeActivity.this.w7hEventInfo = w7hEventInfo;
            W7hHomeActivity.this.setupLandscapeRecordPlayView(list);
            W7hHomeActivity.this.showLandscapeRecordPlayview();
            W7hHomeActivity.this.hideRightList();
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$3 */
    public class AnonymousClass3 implements W7hFullLiveListFragment.EventListListener {
        public AnonymousClass3() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.w7h.W7hFullLiveListFragment.EventListListener
        public void startPlayVideo(W7hEventInfo w7hEventInfo, int i, List<W7hEventInfo> list, String str) {
            W7hHomeActivity.this.w7hEventInfo = w7hEventInfo;
            W7hHomeActivity.this.setupLandscapeRecordPlayView(list);
            W7hHomeActivity.this.showLandscapeRecordPlayview();
            W7hHomeActivity.this.hideRightList();
        }

        @Override // com.petkit.android.activities.petkitBleDevice.w7h.W7hFullLiveListFragment.EventListListener
        public void cancelRedPoint() {
            W7hHomeActivity.this.newPetEventNum = 0;
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$4 */
    public class AnonymousClass4 implements W7hFullLiveListFragment.EventListListener {
        public AnonymousClass4() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.w7h.W7hFullLiveListFragment.EventListListener
        public void startPlayVideo(W7hEventInfo w7hEventInfo, int i, List<W7hEventInfo> list, String str) {
            W7hHomeActivity.this.w7hEventInfo = w7hEventInfo;
            W7hHomeActivity.this.setupLandscapeRecordPlayView(list);
            W7hHomeActivity.this.showLandscapeRecordPlayview();
            W7hHomeActivity.this.hideRightList();
        }

        @Override // com.petkit.android.activities.petkitBleDevice.w7h.W7hFullLiveListFragment.EventListListener
        public void cancelRedPoint() {
            W7hHomeActivity.this.newDrankEventNum = 0;
        }
    }

    public /* synthetic */ void lambda$initLandscapeRecordList$4(View view) {
        clickLandscapeAllTab();
    }

    public /* synthetic */ void lambda$initLandscapeRecordList$5(View view) {
        clickLandscapePetTab();
    }

    public /* synthetic */ void lambda$initLandscapeRecordList$6(View view) {
        clickLandscapeDrinkTab();
    }

    public /* synthetic */ void lambda$initLandscapeRecordList$7(View view) {
        this.groupList.setVisibility(8);
        this.groupDate.setVisibility(0);
        this.datePickerView.setVisibility(0);
        ((W7hHomePresenter) this.mPresenter).getCalendarData(this.landscapeCalendarTime);
    }

    public /* synthetic */ void lambda$initLandscapeRecordList$8(View view) {
        this.groupList.setVisibility(0);
        this.groupDate.setVisibility(8);
        this.datePickerView.setVisibility(8);
    }

    public /* synthetic */ void lambda$initLandscapeRecordList$9(int i) {
        ((W7hHomePresenter) this.mPresenter).getDataPicker(i);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.ib_setting) {
            startActivity(W7hSettingActivity.newIntent(getActivity(), this.deviceId, this.deviceType));
            return;
        }
        if (id == R.id.tv_tab_all) {
            clickAllTab(false);
            return;
        }
        if (id == R.id.tv_today_drink) {
            if (isSingleDevice(this.w7hRecord)) {
                return;
            }
            launchActivity(DrinkStatisticsActivity.newIntent(this));
            return;
        }
        if (id == R.id.tv_avg_duration) {
            if (isSingleDevice(this.w7hRecord)) {
                return;
            }
            launchActivity(DrinkStatisticsActivity.newIntent(this));
            return;
        }
        if (id == R.id.tv_more_vlog) {
            launchActivity(W7hVlogActivity.newIntent(this, this.deviceId, 0));
            return;
        }
        if (id == R.id.tv_tab_pet) {
            clickPetTab(false);
            return;
        }
        if (id == R.id.tv_tab_drank) {
            clickDrankTab(false);
            return;
        }
        if (id == R.id.tv_tab_all_top) {
            clickAllTab(true);
            return;
        }
        if (id == R.id.tv_tab_pet_top) {
            clickPetTab(true);
            return;
        }
        if (id == R.id.tv_tab_drank_top) {
            clickDrankTab(true);
            return;
        }
        if (id == R.id.iv_today_event || view.getId() == R.id.iv_today_event2) {
            ((W7hHomePresenter) this.mPresenter).getDateByDay(this.calendarTime);
            return;
        }
        if (id == R.id.iv_chart_show) {
            if (this.mBinding.llDay.getVisibility() == 0) {
                this.mBinding.llDay.setVisibility(8);
                ((ImageView) view).setImageResource(R.drawable.icon_t6_chart_gray);
                return;
            } else {
                this.mBinding.llDay.setVisibility(0);
                ((ImageView) view).setImageResource(R.drawable.icon_t6_chart);
                return;
            }
        }
        if (id == R.id.ll_pets) {
            showSelectPetsDialog();
            return;
        }
        if (id == R.id.tv_top_pao_pao) {
            scrollToTop();
            if (DateUtil.isSameDay(this.calendarTime, this.w7hRecord.getActualTimeZone())) {
                refreshHeader();
                return;
            }
            return;
        }
        if (id == R.id.iv_last_event) {
            int i = this.popCount;
            if (i > 0) {
                int i2 = i - 1;
                this.popCount = i2;
                this.sortEventList.get(i2).getClickView().performClick();
                return;
            }
            return;
        }
        if (id == R.id.iv_next_event) {
            if (this.popCount < this.sortEventList.size() - 1) {
                int i3 = this.popCount + 1;
                this.popCount = i3;
                this.sortEventList.get(i3).getClickView().performClick();
                return;
            }
            return;
        }
        if (id == R.id.ib_back) {
            killMyself();
            return;
        }
        if (id == R.id.rl_device_error_two) {
            List<DeviceError> abnormal = W7hUtils.getInstance().getAbnormal(getActivity(), ((W7hHomePresenter) this.mPresenter).getW7hRecord());
            if (abnormal == null || abnormal.isEmpty()) {
                return;
            }
            if (W7hUtils.getInstance().getFault(((W7hHomePresenter) this.mPresenter).getW7hRecord()).isEmpty()) {
                abnormal.remove(0);
            }
            launchActivity(W7hAbnormalListActivity.newIntent(this, this.deviceId, (ArrayList) abnormal));
            return;
        }
        if (id == R.id.rl_device_error_one) {
            List<DeviceError> listCalcDeviceError = W7hUtils.getInstance().calcDeviceError(getActivity(), ((W7hHomePresenter) this.mPresenter).getW7hRecord());
            if (listCalcDeviceError == null || listCalcDeviceError.isEmpty() || listCalcDeviceError.get(0).getErrorType().equals(W7hUtils.ERROR_CODE_TANK_DF) || listCalcDeviceError.get(0).getErrorType().equals(W7hUtils.ERROR_CODE_PET_LIFT_VALUE_RESET) || listCalcDeviceError.get(0).getErrorType().equals(W7hUtils.ERROR_CODE_FILTER_TIP)) {
                return;
            }
            showErrorWindow(listCalcDeviceError.get(0));
            return;
        }
        if (id == R.id.rl_device_state) {
            if (this.w7hRecord.getState().getOverall() == 2) {
                showOfflineWindow();
                return;
            } else {
                if (this.w7hRecord.getState().getOta() == 1) {
                    launchActivity(W7hOtaSettingActivity.newIntent(this, this.deviceId, this.deviceType));
                    return;
                }
                return;
            }
        }
        if (view.getId() == R.id.tv_highlights_open) {
            ((W7hHomePresenter) this.mPresenter).updateSettings("highlight", 1);
        } else if (view.getId() == R.id.iv_highlights_close) {
            this.mBinding.rlHighlightsTip.setVisibility(8);
            DataHelper.setBooleanSF(this, Constants.W7H_HIGHLIGHT_TIP, Boolean.TRUE);
        }
    }

    private boolean isSingleDevice(W7hRecord w7hRecord) {
        return (w7hRecord == null || w7hRecord.getDeviceShared() == null) ? false : true;
    }

    @Override // com.petkit.android.activities.petkitBleDevice.w7h.contract.W7hHomeContract.View
    public void setupViews(W7hRecord w7hRecord) {
        this.w7hRecord = w7hRecord;
        if (w7hRecord == null) {
            return;
        }
        if (isSingleDevice(w7hRecord)) {
            this.mBinding.llPets.setVisibility(4);
        }
        setupLiveView(w7hRecord);
        setupCardData(w7hRecord);
        HorizontalLiveViewPagerAdapter horizontalLiveViewPagerAdapter = this.horizontalLiveViewPagerAdapter;
        if (horizontalLiveViewPagerAdapter != null) {
            horizontalLiveViewPagerAdapter.refreshW7hState(w7hRecord);
        }
        refreshErrorStateView(w7hRecord);
        if (w7hRecord.getState().getOverall() != 2 && w7hRecord.getState().getOta() != 1) {
            refreshWorkingStateView(w7hRecord);
        }
        updateServiceState();
        refreshDeviceBottomStateView(w7hRecord);
        this.mBinding.bottomView.singleDevice(isSingleDevice(w7hRecord));
        this.mBinding.bottomView.updateBottomLayout();
        setupTopBar(w7hRecord);
        boolean booleanSF = DataHelper.getBooleanSF(this, Constants.W7H_HIGHLIGHT_TIP);
        if (w7hRecord.getSettings().getHighlight() == 1) {
            this.mBinding.rlHighlightsTip.setVisibility(8);
            this.mBinding.llHighlights.setVisibility(0);
            DataHelper.setBooleanSF(this, Constants.W7H_HIGHLIGHT_TIP, Boolean.FALSE);
        } else {
            this.mBinding.rlHighlightsTip.setVisibility(booleanSF ? 8 : 0);
            this.mBinding.llHighlights.setVisibility(8);
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$5 */
    public class AnonymousClass5 implements ViewTreeObserver.OnGlobalLayoutListener {
        public AnonymousClass5() {
        }

        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public void onGlobalLayout() {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) W7hHomeActivity.this.mBinding.vpLivePanel.getLayoutParams();
            layoutParams.height = (int) (W7hHomeActivity.this.mBinding.vpLivePanel.getWidth() * W7hUtils.getInstance().getPlayerRatio());
            W7hHomeActivity.this.mBinding.vpLivePanel.setLayoutParams(layoutParams);
            W7hHomeActivity.this.mBinding.vpLivePanel.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }
    }

    private void initPlayer() {
        this.mBinding.vpLivePanel.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity.5
            public AnonymousClass5() {
            }

            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) W7hHomeActivity.this.mBinding.vpLivePanel.getLayoutParams();
                layoutParams.height = (int) (W7hHomeActivity.this.mBinding.vpLivePanel.getWidth() * W7hUtils.getInstance().getPlayerRatio());
                W7hHomeActivity.this.mBinding.vpLivePanel.setLayoutParams(layoutParams);
                W7hHomeActivity.this.mBinding.vpLivePanel.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        W7hLiveVideoView w7hLiveVideoView = new W7hLiveVideoView(this);
        this.w7hLiveVideoView = w7hLiveVideoView;
        w7hLiveVideoView.setPlayerType(PetkitVideoPlayerView.PlayerType.RECTANGLE);
        this.w7hLiveVideoView.setLiveVideoViewStateListener(new W7hLiveVideoView.LiveVideoViewStateListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity.6
            @Override // com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hLiveVideoView.LiveVideoViewStateListener
            public void offlineOnClick() {
            }

            public AnonymousClass6() {
            }

            @Override // com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hLiveVideoView.LiveVideoViewStateListener
            public void turnOnCamera() {
                ((W7hHomePresenter) ((BaseActivity) W7hHomeActivity.this).mPresenter).updateSettings("camera", 1);
            }

            @Override // com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hLiveVideoView.LiveVideoViewStateListener
            public void switchLandscape() {
                W7hHomeActivity.this.switchLandScape();
            }

            @Override // com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hLiveVideoView.LiveVideoViewStateListener
            public void backToPortual() {
                W7hHomeActivity.this.switchPortual();
                W7hHomeActivity.this.hideRightList();
                W7hHomeActivity w7hHomeActivity = W7hHomeActivity.this;
                if (w7hHomeActivity.isStartRecord) {
                    w7hHomeActivity.stopRecording();
                }
            }

            @Override // com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hLiveVideoView.LiveVideoViewStateListener
            public void turnOnCamera5Minutes() {
                W7hHomeActivity.this.showTurnOnCameraWindow();
            }

            @Override // com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hLiveVideoView.LiveVideoViewStateListener
            public void startLiveVideo() {
                W7hHomeActivity.this.startLive();
            }

            @Override // com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hLiveVideoView.LiveVideoViewStateListener
            public void liveRetry() {
                Log.d("w7htest", "liveRetry: ");
                if (((W7hHomePresenter) ((BaseActivity) W7hHomeActivity.this).mPresenter).getLiveService() != null && ((W7hHomePresenter) ((BaseActivity) W7hHomeActivity.this).mPresenter).getLiveService().isLive) {
                    W7hHomeActivity.this.startLive();
                } else {
                    W7hHomeActivity.this.w7hLiveVideoView.getVideoPlayerView().showLoadingView();
                    ((W7hHomePresenter) ((BaseActivity) W7hHomeActivity.this).mPresenter).firstConnectRtc(W7hHomeActivity.this.w7hLiveVideoView.getVideoPlayerView());
                }
            }

            @Override // com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hLiveVideoView.LiveVideoViewStateListener
            public void stopLiveVideo() {
                Log.d("w7htest", "stopLiveVideo: ");
                W7hHomeActivity.this.stopLive();
                W7hHomeActivity.this.w7hLiveVideoView.getVideoPlayerView().hideLoadingView();
            }

            @Override // com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hLiveVideoView.LiveVideoViewStateListener
            public void normalLive() {
                Log.d("w7htest", "normalLive: ");
                if (((W7hHomePresenter) ((BaseActivity) W7hHomeActivity.this).mPresenter).getLiveService() != null && ((W7hHomePresenter) ((BaseActivity) W7hHomeActivity.this).mPresenter).getLiveService().isLive) {
                    liveRetry();
                } else {
                    W7hHomeActivity.this.w7hLiveVideoView.getVideoPlayerView().showLoadingView();
                    ((W7hHomePresenter) ((BaseActivity) W7hHomeActivity.this).mPresenter).firstConnectRtc(W7hHomeActivity.this.w7hLiveVideoView.getVideoPlayerView());
                }
            }

            @Override // com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hLiveVideoView.LiveVideoViewStateListener
            public void muteIconOnClick() {
                if (W7hHomeActivity.this.w7hRecord.getSettings().getMicrophone() == 0) {
                    PetkitToast.showTopToast(W7hHomeActivity.this.getActivity(), W7hHomeActivity.this.getResources().getString(R.string.Can_not_control_voice_tip), 0, 0);
                } else if (DeviceVolumeUtils.getInstance().isLiveMuteFlag(29)) {
                    ((W7hHomePresenter) ((BaseActivity) W7hHomeActivity.this).mPresenter).openVolume();
                } else {
                    ((W7hHomePresenter) ((BaseActivity) W7hHomeActivity.this).mPresenter).closeVolume();
                }
            }

            @Override // com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hLiveVideoView.LiveVideoViewStateListener
            public void refillClick() {
                ((W7hHomePresenter) ((BaseActivity) W7hHomeActivity.this).mPresenter).controlAddWater();
            }

            @Override // com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hLiveVideoView.LiveVideoViewStateListener
            public void talkDown() {
                if (W7hHomeActivity.this.w7hRecord.getState().getOverall() == 2) {
                    W7hHomeActivity w7hHomeActivity = W7hHomeActivity.this;
                    PetkitToast.showTopToast(w7hHomeActivity, w7hHomeActivity.getResources().getString(R.string.Mate_connect_offline), 0, 0);
                    return;
                }
                if (DataHelper.getBooleanSF(W7hHomeActivity.this, Constants.W7H_HOWLING_FLAG + W7hHomeActivity.this.w7hRecord.getDeviceId())) {
                    ((W7hHomePresenter) ((BaseActivity) W7hHomeActivity.this).mPresenter).startRecordAudioDown();
                    W7hHomeActivity.this.w7hLiveVideoView.setLandscapeSoundWaveViewIsVisible(true);
                } else {
                    W7hHomeActivity.this.showHowlingWindow();
                }
            }

            @Override // com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hLiveVideoView.LiveVideoViewStateListener
            public void talkUp() {
                ((W7hHomePresenter) ((BaseActivity) W7hHomeActivity.this).mPresenter).startRecordAudioUp();
                W7hHomeActivity.this.w7hLiveVideoView.setLandscapeSoundWaveViewIsVisible(false);
                DeviceVolumeUtils.getInstance().isLiveMuteFlag(W7hHomeActivity.this.deviceType);
            }

            @Override // com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hLiveVideoView.LiveVideoViewStateListener
            public void recordOnClick() {
                if (CommonUtils.checkPermission(W7hHomeActivity.this, "android.permission.WRITE_EXTERNAL_STORAGE")) {
                    W7hHomeActivity w7hHomeActivity = W7hHomeActivity.this;
                    if (w7hHomeActivity.isStartRecord) {
                        w7hHomeActivity.stopRecording();
                        return;
                    } else {
                        W7hHomeActivity.this.startRecord(PlayerUtils.getVideoRecordFileName(w7hHomeActivity.deviceType));
                        return;
                    }
                }
                W7hHomeActivity w7hHomeActivity2 = W7hHomeActivity.this;
                w7hHomeActivity2.startActivity(PermissionDialogActivity.newIntent(w7hHomeActivity2, W7hHomeActivity.class.getName(), "android.permission.WRITE_EXTERNAL_STORAGE"));
            }

            @Override // com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hLiveVideoView.LiveVideoViewStateListener
            public void autoStopRecording() {
                W7hHomeActivity.this.unCameraTimeToStopRecording();
            }

            @Override // com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hLiveVideoView.LiveVideoViewStateListener
            public void cameraOnClick() {
                ((W7hHomePresenter) ((BaseActivity) W7hHomeActivity.this).mPresenter).getLiveService().screenshot();
            }

            @Override // com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hLiveVideoView.LiveVideoViewStateListener
            public void liveQualityOnClick() {
                W7hHomeActivity w7hHomeActivity = W7hHomeActivity.this;
                if (!w7hHomeActivity.isStartRecord) {
                    if (((W7hHomePresenter) ((BaseActivity) w7hHomeActivity).mPresenter).getLiveService() != null && !((W7hHomePresenter) ((BaseActivity) W7hHomeActivity.this).mPresenter).getLiveService().isInChannel()) {
                        W7hHomeActivity w7hHomeActivity2 = W7hHomeActivity.this;
                        PetkitToast.showTopToast(w7hHomeActivity2, w7hHomeActivity2.getString(R.string.Live_loading), 0, 1);
                        return;
                    } else {
                        W7hHomeActivity.this.showLandscapeLiveQualityWindow();
                        return;
                    }
                }
                PetkitToast.showTopToast(w7hHomeActivity, w7hHomeActivity.getString(R.string.Unable_switch_between_recording_videos), 0, 1);
            }

            @Override // com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hLiveVideoView.LiveVideoViewStateListener
            public void eventOnClick() {
                W7hHomeActivity.this.showBottomList();
            }

            @Override // com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hLiveVideoView.LiveVideoViewStateListener
            public void eventCloseOnClick() {
                W7hHomeActivity.this.hideRightList();
            }
        });
        HorizontalLiveViewPagerAdapter horizontalLiveViewPagerAdapter = new HorizontalLiveViewPagerAdapter(this, 29, this.w7hLiveVideoView);
        this.horizontalLiveViewPagerAdapter = horizontalLiveViewPagerAdapter;
        horizontalLiveViewPagerAdapter.setW7hOnClickListener(new HorizontalLiveViewPagerAdapter.W7hOnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity.7
            public AnonymousClass7() {
            }

            @Override // com.petkit.android.activities.adapter.HorizontalLiveViewPagerAdapter.W7hOnClickListener
            public void onCleanBoxClick() {
                W7hHomeActivity w7hHomeActivity = W7hHomeActivity.this;
                w7hHomeActivity.launchActivity(W7hConsumablesActivity.newIntent(w7hHomeActivity, w7hHomeActivity.deviceId, 1));
            }

            @Override // com.petkit.android.activities.adapter.HorizontalLiveViewPagerAdapter.W7hOnClickListener
            public void onDirtyBoxClick() {
                W7hHomeActivity w7hHomeActivity = W7hHomeActivity.this;
                w7hHomeActivity.launchActivity(W7hConsumablesActivity.newIntent(w7hHomeActivity, w7hHomeActivity.deviceId, 2));
            }

            @Override // com.petkit.android.activities.adapter.HorizontalLiveViewPagerAdapter.W7hOnClickListener
            public void onFilterClick() {
                W7hHomeActivity w7hHomeActivity = W7hHomeActivity.this;
                w7hHomeActivity.launchActivity(W7hConsumablesActivity.newIntent(w7hHomeActivity, w7hHomeActivity.deviceId, 3));
            }

            @Override // com.petkit.android.activities.adapter.HorizontalLiveViewPagerAdapter.W7hOnClickListener
            public void onTemperatureClick() {
                if (W7hHomeActivity.this.w7hRecord.getState().getHeatInstall() == 0) {
                    W7hHomeActivity w7hHomeActivity = W7hHomeActivity.this;
                    w7hHomeActivity.launchActivity(HeatDisinfectActivity.newIntent(w7hHomeActivity.getActivity(), W7hHomeActivity.this.deviceId, W7hHomeActivity.this.deviceType, 1));
                } else {
                    W7hHomeActivity w7hHomeActivity2 = W7hHomeActivity.this;
                    w7hHomeActivity2.launchActivity(W7hHeatModuleActivity.newIntent(w7hHomeActivity2.getActivity(), W7hHomeActivity.this.deviceId, W7hHomeActivity.this.deviceType));
                }
            }
        });
        this.mBinding.vpLivePanel.setAdapter(this.horizontalLiveViewPagerAdapter);
        this.mBinding.vpLivePanel.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity.8
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
                if (i == 1) {
                    ((W7hHomePresenter) ((BaseActivity) W7hHomeActivity.this).mPresenter).tempReport();
                }
            }
        });
        this.mBinding.vpLivePanel.setCurrentItem(0);
        ActivityW7hHomeBinding activityW7hHomeBinding = this.mBinding;
        activityW7hHomeBinding.liveIndicator.setViewPager(activityW7hHomeBinding.vpLivePanel, 0, 2);
        this.mBinding.liveIndicator.setPageColor(CommonUtils.getColorById(R.color.color_D2C5BC));
        this.mBinding.liveIndicator.setFillColor(CommonUtils.getColorById(R.color.light_black));
        this.mBinding.liveIndicator.setSnap(true);
        this.mBinding.liveIndicator.setIndicatorStyle(3);
        this.mBinding.liveIndicator.setRadius(ArmsUtils.dip2px(CommonUtils.getAppContext(), 9.0f));
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$6 */
    public class AnonymousClass6 implements W7hLiveVideoView.LiveVideoViewStateListener {
        @Override // com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hLiveVideoView.LiveVideoViewStateListener
        public void offlineOnClick() {
        }

        public AnonymousClass6() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hLiveVideoView.LiveVideoViewStateListener
        public void turnOnCamera() {
            ((W7hHomePresenter) ((BaseActivity) W7hHomeActivity.this).mPresenter).updateSettings("camera", 1);
        }

        @Override // com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hLiveVideoView.LiveVideoViewStateListener
        public void switchLandscape() {
            W7hHomeActivity.this.switchLandScape();
        }

        @Override // com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hLiveVideoView.LiveVideoViewStateListener
        public void backToPortual() {
            W7hHomeActivity.this.switchPortual();
            W7hHomeActivity.this.hideRightList();
            W7hHomeActivity w7hHomeActivity = W7hHomeActivity.this;
            if (w7hHomeActivity.isStartRecord) {
                w7hHomeActivity.stopRecording();
            }
        }

        @Override // com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hLiveVideoView.LiveVideoViewStateListener
        public void turnOnCamera5Minutes() {
            W7hHomeActivity.this.showTurnOnCameraWindow();
        }

        @Override // com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hLiveVideoView.LiveVideoViewStateListener
        public void startLiveVideo() {
            W7hHomeActivity.this.startLive();
        }

        @Override // com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hLiveVideoView.LiveVideoViewStateListener
        public void liveRetry() {
            Log.d("w7htest", "liveRetry: ");
            if (((W7hHomePresenter) ((BaseActivity) W7hHomeActivity.this).mPresenter).getLiveService() != null && ((W7hHomePresenter) ((BaseActivity) W7hHomeActivity.this).mPresenter).getLiveService().isLive) {
                W7hHomeActivity.this.startLive();
            } else {
                W7hHomeActivity.this.w7hLiveVideoView.getVideoPlayerView().showLoadingView();
                ((W7hHomePresenter) ((BaseActivity) W7hHomeActivity.this).mPresenter).firstConnectRtc(W7hHomeActivity.this.w7hLiveVideoView.getVideoPlayerView());
            }
        }

        @Override // com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hLiveVideoView.LiveVideoViewStateListener
        public void stopLiveVideo() {
            Log.d("w7htest", "stopLiveVideo: ");
            W7hHomeActivity.this.stopLive();
            W7hHomeActivity.this.w7hLiveVideoView.getVideoPlayerView().hideLoadingView();
        }

        @Override // com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hLiveVideoView.LiveVideoViewStateListener
        public void normalLive() {
            Log.d("w7htest", "normalLive: ");
            if (((W7hHomePresenter) ((BaseActivity) W7hHomeActivity.this).mPresenter).getLiveService() != null && ((W7hHomePresenter) ((BaseActivity) W7hHomeActivity.this).mPresenter).getLiveService().isLive) {
                liveRetry();
            } else {
                W7hHomeActivity.this.w7hLiveVideoView.getVideoPlayerView().showLoadingView();
                ((W7hHomePresenter) ((BaseActivity) W7hHomeActivity.this).mPresenter).firstConnectRtc(W7hHomeActivity.this.w7hLiveVideoView.getVideoPlayerView());
            }
        }

        @Override // com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hLiveVideoView.LiveVideoViewStateListener
        public void muteIconOnClick() {
            if (W7hHomeActivity.this.w7hRecord.getSettings().getMicrophone() == 0) {
                PetkitToast.showTopToast(W7hHomeActivity.this.getActivity(), W7hHomeActivity.this.getResources().getString(R.string.Can_not_control_voice_tip), 0, 0);
            } else if (DeviceVolumeUtils.getInstance().isLiveMuteFlag(29)) {
                ((W7hHomePresenter) ((BaseActivity) W7hHomeActivity.this).mPresenter).openVolume();
            } else {
                ((W7hHomePresenter) ((BaseActivity) W7hHomeActivity.this).mPresenter).closeVolume();
            }
        }

        @Override // com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hLiveVideoView.LiveVideoViewStateListener
        public void refillClick() {
            ((W7hHomePresenter) ((BaseActivity) W7hHomeActivity.this).mPresenter).controlAddWater();
        }

        @Override // com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hLiveVideoView.LiveVideoViewStateListener
        public void talkDown() {
            if (W7hHomeActivity.this.w7hRecord.getState().getOverall() == 2) {
                W7hHomeActivity w7hHomeActivity = W7hHomeActivity.this;
                PetkitToast.showTopToast(w7hHomeActivity, w7hHomeActivity.getResources().getString(R.string.Mate_connect_offline), 0, 0);
                return;
            }
            if (DataHelper.getBooleanSF(W7hHomeActivity.this, Constants.W7H_HOWLING_FLAG + W7hHomeActivity.this.w7hRecord.getDeviceId())) {
                ((W7hHomePresenter) ((BaseActivity) W7hHomeActivity.this).mPresenter).startRecordAudioDown();
                W7hHomeActivity.this.w7hLiveVideoView.setLandscapeSoundWaveViewIsVisible(true);
            } else {
                W7hHomeActivity.this.showHowlingWindow();
            }
        }

        @Override // com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hLiveVideoView.LiveVideoViewStateListener
        public void talkUp() {
            ((W7hHomePresenter) ((BaseActivity) W7hHomeActivity.this).mPresenter).startRecordAudioUp();
            W7hHomeActivity.this.w7hLiveVideoView.setLandscapeSoundWaveViewIsVisible(false);
            DeviceVolumeUtils.getInstance().isLiveMuteFlag(W7hHomeActivity.this.deviceType);
        }

        @Override // com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hLiveVideoView.LiveVideoViewStateListener
        public void recordOnClick() {
            if (CommonUtils.checkPermission(W7hHomeActivity.this, "android.permission.WRITE_EXTERNAL_STORAGE")) {
                W7hHomeActivity w7hHomeActivity = W7hHomeActivity.this;
                if (w7hHomeActivity.isStartRecord) {
                    w7hHomeActivity.stopRecording();
                    return;
                } else {
                    W7hHomeActivity.this.startRecord(PlayerUtils.getVideoRecordFileName(w7hHomeActivity.deviceType));
                    return;
                }
            }
            W7hHomeActivity w7hHomeActivity2 = W7hHomeActivity.this;
            w7hHomeActivity2.startActivity(PermissionDialogActivity.newIntent(w7hHomeActivity2, W7hHomeActivity.class.getName(), "android.permission.WRITE_EXTERNAL_STORAGE"));
        }

        @Override // com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hLiveVideoView.LiveVideoViewStateListener
        public void autoStopRecording() {
            W7hHomeActivity.this.unCameraTimeToStopRecording();
        }

        @Override // com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hLiveVideoView.LiveVideoViewStateListener
        public void cameraOnClick() {
            ((W7hHomePresenter) ((BaseActivity) W7hHomeActivity.this).mPresenter).getLiveService().screenshot();
        }

        @Override // com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hLiveVideoView.LiveVideoViewStateListener
        public void liveQualityOnClick() {
            W7hHomeActivity w7hHomeActivity = W7hHomeActivity.this;
            if (!w7hHomeActivity.isStartRecord) {
                if (((W7hHomePresenter) ((BaseActivity) w7hHomeActivity).mPresenter).getLiveService() != null && !((W7hHomePresenter) ((BaseActivity) W7hHomeActivity.this).mPresenter).getLiveService().isInChannel()) {
                    W7hHomeActivity w7hHomeActivity2 = W7hHomeActivity.this;
                    PetkitToast.showTopToast(w7hHomeActivity2, w7hHomeActivity2.getString(R.string.Live_loading), 0, 1);
                    return;
                } else {
                    W7hHomeActivity.this.showLandscapeLiveQualityWindow();
                    return;
                }
            }
            PetkitToast.showTopToast(w7hHomeActivity, w7hHomeActivity.getString(R.string.Unable_switch_between_recording_videos), 0, 1);
        }

        @Override // com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hLiveVideoView.LiveVideoViewStateListener
        public void eventOnClick() {
            W7hHomeActivity.this.showBottomList();
        }

        @Override // com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hLiveVideoView.LiveVideoViewStateListener
        public void eventCloseOnClick() {
            W7hHomeActivity.this.hideRightList();
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$7 */
    public class AnonymousClass7 implements HorizontalLiveViewPagerAdapter.W7hOnClickListener {
        public AnonymousClass7() {
        }

        @Override // com.petkit.android.activities.adapter.HorizontalLiveViewPagerAdapter.W7hOnClickListener
        public void onCleanBoxClick() {
            W7hHomeActivity w7hHomeActivity = W7hHomeActivity.this;
            w7hHomeActivity.launchActivity(W7hConsumablesActivity.newIntent(w7hHomeActivity, w7hHomeActivity.deviceId, 1));
        }

        @Override // com.petkit.android.activities.adapter.HorizontalLiveViewPagerAdapter.W7hOnClickListener
        public void onDirtyBoxClick() {
            W7hHomeActivity w7hHomeActivity = W7hHomeActivity.this;
            w7hHomeActivity.launchActivity(W7hConsumablesActivity.newIntent(w7hHomeActivity, w7hHomeActivity.deviceId, 2));
        }

        @Override // com.petkit.android.activities.adapter.HorizontalLiveViewPagerAdapter.W7hOnClickListener
        public void onFilterClick() {
            W7hHomeActivity w7hHomeActivity = W7hHomeActivity.this;
            w7hHomeActivity.launchActivity(W7hConsumablesActivity.newIntent(w7hHomeActivity, w7hHomeActivity.deviceId, 3));
        }

        @Override // com.petkit.android.activities.adapter.HorizontalLiveViewPagerAdapter.W7hOnClickListener
        public void onTemperatureClick() {
            if (W7hHomeActivity.this.w7hRecord.getState().getHeatInstall() == 0) {
                W7hHomeActivity w7hHomeActivity = W7hHomeActivity.this;
                w7hHomeActivity.launchActivity(HeatDisinfectActivity.newIntent(w7hHomeActivity.getActivity(), W7hHomeActivity.this.deviceId, W7hHomeActivity.this.deviceType, 1));
            } else {
                W7hHomeActivity w7hHomeActivity2 = W7hHomeActivity.this;
                w7hHomeActivity2.launchActivity(W7hHeatModuleActivity.newIntent(w7hHomeActivity2.getActivity(), W7hHomeActivity.this.deviceId, W7hHomeActivity.this.deviceType));
            }
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$8 */
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
            if (i == 1) {
                ((W7hHomePresenter) ((BaseActivity) W7hHomeActivity.this).mPresenter).tempReport();
            }
        }
    }

    public void showHowlingWindow() {
        SpannableStringColorsPicPromptWindow spannableStringColorsPicPromptWindow = this.speakingWindow;
        if (spannableStringColorsPicPromptWindow == null || !spannableStringColorsPicPromptWindow.isShowing()) {
            SpannableStringColorsPicPromptWindow spannableStringColorsPicPromptWindow2 = new SpannableStringColorsPicPromptWindow(this, getResources().getString(R.string.Prompt), getResources().getString(R.string.D4sh_tip_prompt_two), getResources().getString(R.string.D4sh_tip_prompt_one), getResources().getString(R.string.Feeder_i_know), R.drawable.w7h_howling_tip, (SpannableStringColorsPicPromptWindow.OnClickListener) null, new String[0]);
            this.speakingWindow = spannableStringColorsPicPromptWindow2;
            spannableStringColorsPicPromptWindow2.show(getWindow().getDecorView());
            DataHelper.setBooleanSF(this, Constants.W7H_HOWLING_FLAG + this.deviceId, Boolean.TRUE);
        }
    }

    public void startLive() {
        ((W7hHomePresenter) this.mPresenter).startLiveVideo(this.w7hLiveVideoView.getVideoPlayerView());
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$9 */
    public class AnonymousClass9 implements ThreeChoicesWindow.OnClickThreeChoices {
        public AnonymousClass9() {
        }

        @Override // com.petkit.android.activities.device.widget.ThreeChoicesWindow.OnClickThreeChoices
        public void onClickFirstChoice() {
            W7hHomeActivity w7hHomeActivity = W7hHomeActivity.this;
            w7hHomeActivity.launchActivity(W7hCameraPeriodActivity.newIntent(w7hHomeActivity, w7hHomeActivity.deviceId, W7hHomeActivity.this.deviceType));
            W7hHomeActivity.this.threeChoicesWindow.dismiss();
        }

        @Override // com.petkit.android.activities.device.widget.ThreeChoicesWindow.OnClickThreeChoices
        public void onClickSecondChoice() {
            ((W7hHomePresenter) ((BaseActivity) W7hHomeActivity.this).mPresenter).temporaryOpenCamera();
            W7hHomeActivity.this.show5MinWindowTips();
            W7hHomeActivity.this.threeChoicesWindow.dismiss();
        }

        @Override // com.petkit.android.activities.device.widget.ThreeChoicesWindow.OnClickThreeChoices
        public void onClickThirdChoice() {
            W7hHomeActivity.this.threeChoicesWindow.dismiss();
        }
    }

    public void showTurnOnCameraWindow() {
        if (this.threeChoicesWindow == null) {
            ThreeChoicesWindow threeChoicesWindow = new ThreeChoicesWindow(this, new ThreeChoicesWindow.OnClickThreeChoices() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity.9
                public AnonymousClass9() {
                }

                @Override // com.petkit.android.activities.device.widget.ThreeChoicesWindow.OnClickThreeChoices
                public void onClickFirstChoice() {
                    W7hHomeActivity w7hHomeActivity = W7hHomeActivity.this;
                    w7hHomeActivity.launchActivity(W7hCameraPeriodActivity.newIntent(w7hHomeActivity, w7hHomeActivity.deviceId, W7hHomeActivity.this.deviceType));
                    W7hHomeActivity.this.threeChoicesWindow.dismiss();
                }

                @Override // com.petkit.android.activities.device.widget.ThreeChoicesWindow.OnClickThreeChoices
                public void onClickSecondChoice() {
                    ((W7hHomePresenter) ((BaseActivity) W7hHomeActivity.this).mPresenter).temporaryOpenCamera();
                    W7hHomeActivity.this.show5MinWindowTips();
                    W7hHomeActivity.this.threeChoicesWindow.dismiss();
                }

                @Override // com.petkit.android.activities.device.widget.ThreeChoicesWindow.OnClickThreeChoices
                public void onClickThirdChoice() {
                    W7hHomeActivity.this.threeChoicesWindow.dismiss();
                }
            }, getResources().getString(R.string.T6_open_camera_tip), getResources().getString(R.string.Camera_open_all_day), getResources().getString(R.string.Camera_open_five_minute), getResources().getString(R.string.Cancel));
            this.threeChoicesWindow = threeChoicesWindow;
            threeChoicesWindow.setThreeChoicesTextColor(getResources().getColor(R.color.new_bind_blue), getResources().getColor(R.color.new_bind_blue), getResources().getColor(R.color.gray));
        }
        this.threeChoicesWindow.show(getWindow().getDecorView());
    }

    public void show5MinWindowTips() {
        new NewIKnowWindow(this, "", getResources().getString(R.string.Camera_open_five_minute_tips), "").show(getWindow().getDecorView());
    }

    @Override // com.petkit.android.activities.petkitBleDevice.w7h.contract.W7hHomeContract.View
    public void unCameraTimeToStopRecording() {
        if (this.isStartRecord) {
            this.showCameraOutToast = true;
            stopRecording();
        }
    }

    public void stopRecording() {
        ((W7hHomePresenter) this.mPresenter).stopRecord();
        this.w7hLiveVideoView.stopRecord();
        stopRecordingTimer();
        this.isStartRecord = false;
    }

    public void startRecord(String str) {
        if (((W7hHomePresenter) this.mPresenter).getLiveService() != null) {
            this.filePath = str;
            this.recordingStartTime = System.currentTimeMillis();
            ((W7hHomePresenter) this.mPresenter).startRecord(this.filePath);
            this.w7hLiveVideoView.startRecord();
            startRecordingTimer();
            this.isStartRecord = true;
        }
    }

    public void stopRecordingTimer() {
        Disposable disposable = this.recordTimerDisposable;
        if (disposable == null || disposable.isDisposed()) {
            return;
        }
        this.recordTimerDisposable.dispose();
        this.recordTimerDisposable = null;
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$10 */
    public class AnonymousClass10 implements Observer<Long> {
        @Override // io.reactivex.Observer
        public void onComplete() {
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
        }

        public AnonymousClass10() {
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            W7hHomeActivity.this.recordTimerDisposable = disposable;
        }

        @Override // io.reactivex.Observer
        public void onNext(Long l) {
            Object objValueOf;
            Object objValueOf2;
            Object objValueOf3;
            int iLongValue = (int) (l.longValue() / 3600);
            int iLongValue2 = (int) ((l.longValue() % 3600) / 60);
            int iLongValue3 = (int) (l.longValue() % 60);
            W7hHomeActivity w7hHomeActivity = W7hHomeActivity.this;
            StringBuilder sb = new StringBuilder();
            if (iLongValue < 10) {
                objValueOf = "0" + iLongValue;
            } else {
                objValueOf = Integer.valueOf(iLongValue);
            }
            sb.append(objValueOf);
            sb.append(":");
            if (iLongValue2 < 10) {
                objValueOf2 = "0" + iLongValue2;
            } else {
                objValueOf2 = Integer.valueOf(iLongValue2);
            }
            sb.append(objValueOf2);
            sb.append(":");
            if (iLongValue3 < 10) {
                objValueOf3 = "0" + iLongValue3;
            } else {
                objValueOf3 = Integer.valueOf(iLongValue3);
            }
            sb.append(objValueOf3);
            w7hHomeActivity.refreshRecordTime(sb.toString());
            if (l.longValue() >= AgoraConstants.maxRecordDuration / 1000) {
                ((W7hHomePresenter) ((BaseActivity) W7hHomeActivity.this).mPresenter).stopRecord();
                PetkitToast.showTopToast(W7hHomeActivity.this, W7hHomeActivity.this.getString(R.string.Record_max_duration_end) + " >", 0, 1, new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$10$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$onNext$0(view);
                    }
                });
                W7hHomeActivity.this.stopRecordingTimer();
            }
        }

        public /* synthetic */ void lambda$onNext$0(View view) {
            if (CommonUtils.checkPermission(W7hHomeActivity.this, "android.permission.WRITE_EXTERNAL_STORAGE")) {
                W7hHomeActivity w7hHomeActivity = W7hHomeActivity.this;
                w7hHomeActivity.launchActivity(LocalAlbumActivity.getDeviceJumpIntent(w7hHomeActivity, 1));
            } else {
                W7hHomeActivity w7hHomeActivity2 = W7hHomeActivity.this;
                w7hHomeActivity2.launchActivity(PermissionDialogActivity.newIntent(w7hHomeActivity2, getClass().getName(), "android.permission.WRITE_EXTERNAL_STORAGE"));
            }
        }
    }

    public void startRecordingTimer() {
        Observable.interval(0L, 1000L, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new AnonymousClass10());
    }

    public void refreshRecordTime(String str) {
        this.w7hLiveVideoView.refreshRecordTime(str);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$11 */
    public class AnonymousClass11 implements PopupWindow.OnDismissListener {
        public AnonymousClass11() {
        }

        @Override // android.widget.PopupWindow.OnDismissListener
        public void onDismiss() {
            W7hHomeActivity.this.hideSystemUI();
        }
    }

    public void showLandscapeLiveQualityWindow() {
        this.landscapeLiveQualitWindowBuilder = new FeederPlayerLandscapeSelectorWindowBuilder(this).addAction(getString(R.string.Cozy_auto), new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$$ExternalSyntheticLambda48
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$showLandscapeLiveQualityWindow$10();
            }
        }, true).addAction(getString(R.string.Standard_definition), new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$$ExternalSyntheticLambda49
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$showLandscapeLiveQualityWindow$11();
            }
        }, true).addAction(getString(R.string.Quality_hd), new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$$ExternalSyntheticLambda50
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$showLandscapeLiveQualityWindow$12();
            }
        }, true).setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity.11
            public AnonymousClass11() {
            }

            @Override // android.widget.PopupWindow.OnDismissListener
            public void onDismiss() {
                W7hHomeActivity.this.hideSystemUI();
            }
        });
        this.landscapeLiveQualitWindowBuilder.show(getWindow().getDecorView(), PetKitQualityUtil.getInstance().getQualitySelf(this, this.deviceId, this.deviceType) ? -1 : ((W7hHomePresenter) this.mPresenter).getLiveService().getQualityType() + 1);
    }

    public /* synthetic */ void lambda$showLandscapeLiveQualityWindow$10() {
        switchLiveQuality(-1);
        this.landscapeLiveQualitWindowBuilder.dismiss();
    }

    public /* synthetic */ void lambda$showLandscapeLiveQualityWindow$11() {
        switchLiveQuality(1);
        this.landscapeLiveQualitWindowBuilder.dismiss();
    }

    public /* synthetic */ void lambda$showLandscapeLiveQualityWindow$12() {
        switchLiveQuality(2);
        this.landscapeLiveQualitWindowBuilder.dismiss();
    }

    private void switchLiveQuality(int i) {
        if (i == 1) {
            PetKitQualityUtil.getInstance().setQualitySelf(this, this.deviceId, this.deviceType, false);
            ((W7hHomePresenter) this.mPresenter).switchLiveQuality(i);
            return;
        }
        if (i == 2) {
            PetKitQualityUtil.getInstance().cancelQualityStillTime(CommonUtils.getAppContext(), this.deviceId, this.deviceType);
            PetKitQualityUtil.getInstance().setQualitySelf(this, this.deviceId, this.deviceType, false);
            ((W7hHomePresenter) this.mPresenter).switchLiveQuality(i);
            return;
        }
        PetKitQualityUtil.getInstance().setQualitySelf(this, this.deviceId, this.deviceType, true);
        if (DataHelper.getIntergerSF(this, Constants.VIDEO_DEFINITION + this.deviceType + "_" + this.deviceId, 2) != 2) {
            ((W7hHomePresenter) this.mPresenter).switchLiveQuality(2);
            return;
        }
        W7hLiveVideoView w7hLiveVideoView = this.w7hLiveVideoView;
        if (w7hLiveVideoView != null) {
            w7hLiveVideoView.setupLiveQuality();
        }
    }

    public void switchLandScape() {
        Log.d("w7htest", "switchLandscape");
        if (this.isInLandscape) {
            return;
        }
        this.isInLandscape = true;
        if (this.w7hLiveVideoView.getParent() != null) {
            ((ViewGroup) this.w7hLiveVideoView.getParent()).removeView(this.w7hLiveVideoView);
        }
        this.mBinding.rlLandscapeVideo.addView(this.w7hLiveVideoView, 0);
        this.bannerServiceView.refreshBannerService(this.w7hRecord, this.deviceId);
        this.mBinding.rlLandscape.setVisibility(0);
        setRequestedOrientation(0);
        hideSystemUI();
        Log.d("w7htest", "switchLandscape1");
    }

    public void switchPortual() {
        if (this.isInLandscape) {
            this.isInLandscape = false;
            this.mBinding.lsViewPager.setAdapter(null);
            this.mBinding.rlLandscape.setVisibility(8);
            hideRightList();
            this.mBinding.lsSrl.setVisibility(8);
            this.mBinding.rlLandscapeVideo.setVisibility(0);
            W7hLiveVideoView w7hLiveVideoView = this.w7hLiveVideoView;
            if (w7hLiveVideoView != null) {
                w7hLiveVideoView.setLandscape(false);
            }
            if (getActivity() != null) {
                setRequestedOrientation(1);
            }
            showSystemUI();
            if (this.w7hLiveVideoView.getParent() != null) {
                ((ViewGroup) this.w7hLiveVideoView.getParent()).removeView(this.w7hLiveVideoView);
            }
            this.mBinding.vpLivePanel.addView(this.w7hLiveVideoView, 0);
            setupViews(this.w7hRecord);
        }
    }

    public void hideSystemUI() {
        getActivity().getWindow().getDecorView().setSystemUiVisibility(5126);
    }

    private void showSystemUI() {
        getActivity().getWindow().getDecorView().setSystemUiVisibility(0);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
    }

    @Override // android.app.Activity
    public void onRestart() {
        super.onRestart();
        if (((W7hHomePresenter) this.mPresenter).getLiveService() != null) {
            if (this.isForeground) {
                this.isForeground = false;
                Message message = new Message();
                message.arg1 = 0;
                message.what = 21;
                MyHandler myHandler = this.handler;
                if (myHandler != null) {
                    myHandler.sendMessageDelayed(message, 500L);
                }
            } else {
                W7hRecord w7hRecord = this.w7hRecord;
                if (w7hRecord != null) {
                    setupViews(w7hRecord);
                }
            }
            if (DeviceVolumeUtils.getInstance().isLiveMuteFlag(29)) {
                ((W7hHomePresenter) this.mPresenter).closeVolume();
                return;
            } else {
                ((W7hHomePresenter) this.mPresenter).openVolume();
                return;
            }
        }
        W7hRecord w7hRecord2 = this.w7hRecord;
        if (w7hRecord2 != null) {
            setupViews(w7hRecord2);
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStop() {
        super.onStop();
        if (BaseApplication.isForeground()) {
            this.isForeground = true;
        }
        if (this.isStartRecord) {
            stopRecording();
        }
        if (((W7hHomePresenter) this.mPresenter).getLiveService() != null) {
            ((W7hHomePresenter) this.mPresenter).getLiveService().stop();
        }
    }

    @Override // com.jess.arms.base.BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        this.mBinding.bottomView.onDestroy();
        this.mBinding.unbind();
        Disposable disposable = this.disposable;
        if (disposable != null && !disposable.isDisposed()) {
            this.disposable.dispose();
            this.disposable = null;
        }
        Disposable disposable2 = this.paoPaoTimer;
        if (disposable2 != null) {
            disposable2.dispose();
            this.paoPaoTimer = null;
        }
        Disposable disposable3 = this.otaDisposable;
        if (disposable3 != null) {
            disposable3.dispose();
            this.otaDisposable = null;
        }
        Disposable disposable4 = this.bannerDisposable;
        if (disposable4 != null && !disposable4.isDisposed()) {
            this.bannerDisposable.dispose();
            this.bannerDisposable = null;
        }
        T7DataUtils.getInstance().setOpenPreview(false);
        ((W7hHomePresenter) this.mPresenter).destroyLiveService();
        stopLive();
        MyHandler myHandler = this.handler;
        if (myHandler != null) {
            myHandler.removeCallbacksAndMessages(null);
            this.handler = null;
        }
        this.deviceId = 0L;
        this.eventId = null;
        super.onDestroy();
    }

    @Override // com.petkit.android.activities.petkitBleDevice.w7h.contract.W7hHomeContract.View
    public void stopLive() {
        ((W7hHomePresenter) this.mPresenter).stopLiveVideo();
        this.w7hLiveVideoView.setIsInlive(false);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$12 */
    public class AnonymousClass12 implements Runnable {
        public AnonymousClass12() {
        }

        @Override // java.lang.Runnable
        public void run() {
            W7hLiveVideoView w7hLiveVideoView = W7hHomeActivity.this.w7hLiveVideoView;
            if (w7hLiveVideoView != null) {
                w7hLiveVideoView.refreshTimeoutState();
            }
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.w7h.contract.W7hHomeContract.View
    public void loadVideoTimeout() {
        runOnUiThread(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity.12
            public AnonymousClass12() {
            }

            @Override // java.lang.Runnable
            public void run() {
                W7hLiveVideoView w7hLiveVideoView = W7hHomeActivity.this.w7hLiveVideoView;
                if (w7hLiveVideoView != null) {
                    w7hLiveVideoView.refreshTimeoutState();
                }
            }
        });
    }

    @Override // com.petkit.android.activities.petkitBleDevice.w7h.contract.W7hHomeContract.View
    public void onRecording(int i) {
        W7hPlayerSoundWaveView w7hPlayerSoundWaveView = this.mBinding.soundWaveView;
        if (w7hPlayerSoundWaveView != null) {
            w7hPlayerSoundWaveView.setVoiceLineVolume(i);
        }
        W7hLiveVideoView w7hLiveVideoView = this.w7hLiveVideoView;
        if (w7hLiveVideoView != null) {
            w7hLiveVideoView.onSpeaking(i);
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$13 */
    public class AnonymousClass13 implements Runnable {
        public AnonymousClass13() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (System.currentTimeMillis() - W7hHomeActivity.this.recordingStartTime < 5000) {
                W7hHomeActivity w7hHomeActivity = W7hHomeActivity.this;
                PetkitToast.showTopToast(w7hHomeActivity, w7hHomeActivity.getString(R.string.Recording_time_too_short), 0, 0);
                File file = new File(W7hHomeActivity.this.filePath);
                if (file.exists()) {
                    file.delete();
                    return;
                }
                return;
            }
            W7hHomeActivity w7hHomeActivity2 = W7hHomeActivity.this;
            w7hHomeActivity2.copyVideo(w7hHomeActivity2.filePath);
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.w7h.contract.W7hHomeContract.View
    public void onRecordVideoEnd() {
        if (TextUtils.isEmpty(this.filePath)) {
            return;
        }
        runOnUiThread(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity.13
            public AnonymousClass13() {
            }

            @Override // java.lang.Runnable
            public void run() {
                if (System.currentTimeMillis() - W7hHomeActivity.this.recordingStartTime < 5000) {
                    W7hHomeActivity w7hHomeActivity = W7hHomeActivity.this;
                    PetkitToast.showTopToast(w7hHomeActivity, w7hHomeActivity.getString(R.string.Recording_time_too_short), 0, 0);
                    File file = new File(W7hHomeActivity.this.filePath);
                    if (file.exists()) {
                        file.delete();
                        return;
                    }
                    return;
                }
                W7hHomeActivity w7hHomeActivity2 = W7hHomeActivity.this;
                w7hHomeActivity2.copyVideo(w7hHomeActivity2.filePath);
            }
        });
    }

    public void copyVideo(String str) {
        File file = new File(str);
        if (file.exists()) {
            MediaScannerConnection.scanFile(this, new String[]{FileUtil.saveFileToExternalStorage(this, file, VideoDownloadManager.getLocalVideoPath())}, null, new MediaScannerConnection.OnScanCompletedListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$$ExternalSyntheticLambda45
                @Override // android.media.MediaScannerConnection.OnScanCompletedListener
                public final void onScanCompleted(String str2, Uri uri) {
                    this.f$0.lambda$copyVideo$13(str2, uri);
                }
            });
        }
    }

    public /* synthetic */ void lambda$copyVideo$13(String str, Uri uri) {
        jumpToLookFile();
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$14 */
    public class AnonymousClass14 implements Runnable {
        public AnonymousClass14() {
        }

        @Override // java.lang.Runnable
        public void run() {
            String str;
            try {
                if (W7hHomeActivity.this.showCameraOutToast) {
                    str = W7hHomeActivity.this.getString(R.string.Video_recording_has_stopped_automatically) + " >";
                } else {
                    str = W7hHomeActivity.this.getString(R.string.Saved_to_album_click_to_view) + " >";
                }
                PetkitToast.showTopToast(W7hHomeActivity.this, str, 0, 1, new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$14$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$run$0(view);
                    }
                });
            } catch (Exception unused) {
                PetkitLog.d("去查看文件失败：jumpToLookFile");
            }
            W7hHomeActivity.this.showCameraOutToast = false;
        }

        public final /* synthetic */ void lambda$run$0(View view) {
            if (CommonUtils.checkPermission(W7hHomeActivity.this, "android.permission.WRITE_EXTERNAL_STORAGE")) {
                W7hHomeActivity w7hHomeActivity = W7hHomeActivity.this;
                w7hHomeActivity.startActivity(LocalAlbumActivity.getDeviceJumpIntent(w7hHomeActivity, 1));
            } else {
                W7hHomeActivity w7hHomeActivity2 = W7hHomeActivity.this;
                w7hHomeActivity2.startActivity(PermissionDialogActivity.newIntent(w7hHomeActivity2, getClass().getName(), "android.permission.WRITE_EXTERNAL_STORAGE"));
            }
        }
    }

    private void jumpToLookFile() {
        runOnUiThread(new AnonymousClass14());
    }

    @Override // com.petkit.android.activities.petkitBleDevice.w7h.contract.W7hHomeContract.View
    public void onFirstRemoteVideoFrame() {
        PetkitLog.d("onFirstRemoteVideoFrame");
        runOnUiThread(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity.15
            public AnonymousClass15() {
            }

            @Override // java.lang.Runnable
            public void run() {
                if (W7hHomeActivity.this.w7hRecord.getSettings().getCamera() == 0) {
                    W7hHomeActivity.this.w7hLiveVideoView.setIsInlive(false);
                    return;
                }
                if (W7hHomeActivity.this.w7hRecord.getState().getCameraStatus() == 0) {
                    W7hHomeActivity.this.w7hLiveVideoView.setIsInlive(false);
                    return;
                }
                W7hHomeActivity.this.w7hLiveVideoView.setIsInlive(true);
                if (((BaseActivity) W7hHomeActivity.this).mPresenter != null) {
                    W7hHomeActivity w7hHomeActivity = W7hHomeActivity.this;
                    w7hHomeActivity.refreshDeviceBottomStateView(w7hHomeActivity.w7hRecord);
                }
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$15 */
    public class AnonymousClass15 implements Runnable {
        public AnonymousClass15() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (W7hHomeActivity.this.w7hRecord.getSettings().getCamera() == 0) {
                W7hHomeActivity.this.w7hLiveVideoView.setIsInlive(false);
                return;
            }
            if (W7hHomeActivity.this.w7hRecord.getState().getCameraStatus() == 0) {
                W7hHomeActivity.this.w7hLiveVideoView.setIsInlive(false);
                return;
            }
            W7hHomeActivity.this.w7hLiveVideoView.setIsInlive(true);
            if (((BaseActivity) W7hHomeActivity.this).mPresenter != null) {
                W7hHomeActivity w7hHomeActivity = W7hHomeActivity.this;
                w7hHomeActivity.refreshDeviceBottomStateView(w7hHomeActivity.w7hRecord);
            }
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.w7h.contract.W7hHomeContract.View
    public void onSavePhotoSuccess(final String str) {
        runOnUiThread(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onSavePhotoSuccess$15(str);
            }
        });
    }

    public /* synthetic */ void lambda$onSavePhotoSuccess$15(String str) {
        sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.parse("file://" + str)));
        PetkitToast.showTopToast(this, getString(R.string.Saved_to_album_click_to_view) + " >", 0, 0, new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$$ExternalSyntheticLambda36
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$onSavePhotoSuccess$14(view);
            }
        });
    }

    public /* synthetic */ void lambda$onSavePhotoSuccess$14(View view) {
        if (!CommonUtils.checkPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE")) {
            launchActivity(PermissionDialogActivity.newIntent(this, getClass().getName(), "android.permission.WRITE_EXTERNAL_STORAGE"));
        } else {
            launchActivity(LocalAlbumActivity.newIntent(this));
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.w7h.contract.W7hHomeContract.View
    public void resetVideoTimeoutView() {
        this.w7hLiveVideoView.resetTimeoutView();
    }

    @Override // com.petkit.android.activities.petkitBleDevice.w7h.contract.W7hHomeContract.View
    public void rtcTokenExpired() {
        Message message = new Message();
        message.what = 22;
        message.arg1 = 0;
        this.handler.sendMessageDelayed(message, 500L);
    }

    @Override // com.petkit.android.activities.petkitBleDevice.w7h.contract.W7hHomeContract.View
    public void onUidChanged() {
        W7hLiveVideoView w7hLiveVideoView = this.w7hLiveVideoView;
        if (w7hLiveVideoView != null) {
            w7hLiveVideoView.setupLiveQuality();
            Log.d(this.TAG, "onUidChanged: ");
            ((W7hHomePresenter) this.mPresenter).getLiveService().stop();
            W7hRecord w7hRecord = this.w7hRecord;
            if (w7hRecord != null) {
                setupViews(w7hRecord);
            }
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.w7h.contract.W7hHomeContract.View
    public void setupLiveView(W7hRecord w7hRecord) {
        this.w7hLiveVideoView.setupRecord(w7hRecord);
    }

    @Override // com.petkit.android.activities.petkitBleDevice.w7h.contract.W7hHomeContract.View
    public void setupLiveViewVoiceIcon() {
        this.w7hLiveVideoView.setupVoiceIcon();
    }

    public void setupCardData(W7hRecord w7hRecord) {
        String str = getString(R.string.Today_drink) + " " + w7hRecord.getDrinkCount() + " " + getString(R.string.Unit_times);
        this.mBinding.tvTodayDrinkTools.setText(TextUtil.getInstance().makePartStringSpannableInTotalString(new SpannableString(str), str, String.valueOf(w7hRecord.getDrinkCount()), getResources().getColor(R.color.light_black), 22, true));
        this.mBinding.tvTodayDrinkTools.post(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity.16
            public final /* synthetic */ W7hRecord val$w7hRecord;

            public AnonymousClass16(W7hRecord w7hRecord2) {
                w7hRecord = w7hRecord2;
            }

            @Override // java.lang.Runnable
            public void run() {
                String str2;
                Layout layout = W7hHomeActivity.this.mBinding.tvTodayDrinkTools.getLayout();
                if (layout == null) {
                    return;
                }
                if (layout.getLineCount() > 1) {
                    str2 = W7hHomeActivity.this.getString(R.string.Today_drink) + "\n" + w7hRecord.getDrinkCount() + " " + W7hHomeActivity.this.getString(R.string.Unit_times);
                } else {
                    str2 = W7hHomeActivity.this.getString(R.string.Today_drink) + " " + w7hRecord.getDrinkCount() + " " + W7hHomeActivity.this.getString(R.string.Unit_times);
                }
                String str3 = str2;
                W7hHomeActivity.this.mBinding.tvTodayDrink.setText(TextUtil.getInstance().makePartStringSpannableInTotalString(new SpannableString(str3), str3, String.valueOf(w7hRecord.getDrinkCount()), W7hHomeActivity.this.getResources().getColor(R.color.light_black), 22, true));
            }
        });
        String string = getString(R.string.Average_drink_duration);
        String str2 = string + " " + ((Object) T6Utils.getT6HomeCardString(w7hRecord2.getDrinkTimeAvg()));
        this.mBinding.tvAvgDurationTools.setText(TextUtil.getInstance().makePartStringSpannableInTotalString(new SpannableString(str2), str2, W7hUtils.getInstance().getTimeString(w7hRecord2.getDrinkTimeAvg()), getResources().getColor(R.color.light_black), 22, true));
        this.mBinding.tvAvgDurationTools.post(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity.17
            public final /* synthetic */ String val$avgDurationString;
            public final /* synthetic */ String val$normalText2;
            public final /* synthetic */ W7hRecord val$w7hRecord;

            public AnonymousClass17(String string2, W7hRecord w7hRecord2, String str22) {
                str = string2;
                w7hRecord = w7hRecord2;
                str = str22;
            }

            @Override // java.lang.Runnable
            public void run() {
                String str3;
                Layout layout = W7hHomeActivity.this.mBinding.tvAvgDurationTools.getLayout();
                if (layout == null) {
                    return;
                }
                if (layout.getLineCount() > 1) {
                    str3 = str + "\n" + ((Object) W7hUtils.getInstance().getHomeCardString(W7hHomeActivity.this.getActivity(), w7hRecord.getDrinkTimeAvg()));
                } else {
                    str3 = str;
                }
                String str4 = str3;
                W7hHomeActivity.this.mBinding.tvAvgDuration.setText(TextUtil.getInstance().makePartStringSpannableInTotalString(new SpannableString(str4), str4, W7hUtils.getInstance().getTimeString(w7hRecord.getDrinkTimeAvg()), W7hHomeActivity.this.getResources().getColor(R.color.light_black), 22, true));
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$16 */
    public class AnonymousClass16 implements Runnable {
        public final /* synthetic */ W7hRecord val$w7hRecord;

        public AnonymousClass16(W7hRecord w7hRecord2) {
            w7hRecord = w7hRecord2;
        }

        @Override // java.lang.Runnable
        public void run() {
            String str2;
            Layout layout = W7hHomeActivity.this.mBinding.tvTodayDrinkTools.getLayout();
            if (layout == null) {
                return;
            }
            if (layout.getLineCount() > 1) {
                str2 = W7hHomeActivity.this.getString(R.string.Today_drink) + "\n" + w7hRecord.getDrinkCount() + " " + W7hHomeActivity.this.getString(R.string.Unit_times);
            } else {
                str2 = W7hHomeActivity.this.getString(R.string.Today_drink) + " " + w7hRecord.getDrinkCount() + " " + W7hHomeActivity.this.getString(R.string.Unit_times);
            }
            String str3 = str2;
            W7hHomeActivity.this.mBinding.tvTodayDrink.setText(TextUtil.getInstance().makePartStringSpannableInTotalString(new SpannableString(str3), str3, String.valueOf(w7hRecord.getDrinkCount()), W7hHomeActivity.this.getResources().getColor(R.color.light_black), 22, true));
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$17 */
    public class AnonymousClass17 implements Runnable {
        public final /* synthetic */ String val$avgDurationString;
        public final /* synthetic */ String val$normalText2;
        public final /* synthetic */ W7hRecord val$w7hRecord;

        public AnonymousClass17(String string2, W7hRecord w7hRecord2, String str22) {
            str = string2;
            w7hRecord = w7hRecord2;
            str = str22;
        }

        @Override // java.lang.Runnable
        public void run() {
            String str3;
            Layout layout = W7hHomeActivity.this.mBinding.tvAvgDurationTools.getLayout();
            if (layout == null) {
                return;
            }
            if (layout.getLineCount() > 1) {
                str3 = str + "\n" + ((Object) W7hUtils.getInstance().getHomeCardString(W7hHomeActivity.this.getActivity(), w7hRecord.getDrinkTimeAvg()));
            } else {
                str3 = str;
            }
            String str4 = str3;
            W7hHomeActivity.this.mBinding.tvAvgDuration.setText(TextUtil.getInstance().makePartStringSpannableInTotalString(new SpannableString(str4), str4, W7hUtils.getInstance().getTimeString(w7hRecord.getDrinkTimeAvg()), W7hHomeActivity.this.getResources().getColor(R.color.light_black), 22, true));
        }
    }

    public void initLandscapeLiveView() {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mBinding.rlLandscapeVideo.getLayoutParams();
        int i = getResources().getDisplayMetrics().widthPixels;
        int i2 = getResources().getDisplayMetrics().heightPixels;
        layoutParams.width = (i * 16) / 9;
        this.mBinding.rlLandscapeVideo.setLayoutParams(layoutParams);
        if (this.isInLandscape) {
            switchLandScape();
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$18 */
    public class AnonymousClass18 implements W7hVideoPlayFragment.FromLiveOnclickListener {
        public AnonymousClass18() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.w7h.W7hVideoPlayFragment.FromLiveOnclickListener
        public void backLive() {
            W7hHomeActivity.this.backLive();
            W7hHomeActivity w7hHomeActivity = W7hHomeActivity.this;
            if (w7hHomeActivity.safeCallFragment(w7hHomeActivity.lsFragmentAll)) {
                W7hHomeActivity.this.lsFragmentAll.clearPlayerState();
            }
            W7hHomeActivity w7hHomeActivity2 = W7hHomeActivity.this;
            if (w7hHomeActivity2.safeCallFragment(w7hHomeActivity2.lsFragmentPet)) {
                W7hHomeActivity.this.lsFragmentPet.clearPlayerState();
            }
            W7hHomeActivity w7hHomeActivity3 = W7hHomeActivity.this;
            if (w7hHomeActivity3.safeCallFragment(w7hHomeActivity3.lsFragmentDrink)) {
                W7hHomeActivity.this.lsFragmentDrink.clearPlayerState();
            }
        }

        @Override // com.petkit.android.activities.petkitBleDevice.w7h.W7hVideoPlayFragment.FromLiveOnclickListener
        public void onEventClick() {
            W7hHomeActivity.this.showBottomList();
        }

        @Override // com.petkit.android.activities.petkitBleDevice.w7h.W7hVideoPlayFragment.FromLiveOnclickListener
        public void onBackClick() {
            W7hHomeActivity.this.switchPortual();
            backLive();
        }

        @Override // com.petkit.android.activities.petkitBleDevice.w7h.W7hVideoPlayFragment.FromLiveOnclickListener
        public void closeRightList() {
            W7hHomeActivity.this.hideRightList();
        }
    }

    public void setupLandscapeRecordPlayView(List<W7hEventInfo> list) {
        T6VideoPlayFragmentAdapter t6VideoPlayFragmentAdapter = new T6VideoPlayFragmentAdapter(this, this.deviceId, this.deviceType, handleEventListToFragment(list), true);
        this.fragmentStateAdapter = t6VideoPlayFragmentAdapter;
        t6VideoPlayFragmentAdapter.setFromLiveListener(new W7hVideoPlayFragment.FromLiveOnclickListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity.18
            public AnonymousClass18() {
            }

            @Override // com.petkit.android.activities.petkitBleDevice.w7h.W7hVideoPlayFragment.FromLiveOnclickListener
            public void backLive() {
                W7hHomeActivity.this.backLive();
                W7hHomeActivity w7hHomeActivity = W7hHomeActivity.this;
                if (w7hHomeActivity.safeCallFragment(w7hHomeActivity.lsFragmentAll)) {
                    W7hHomeActivity.this.lsFragmentAll.clearPlayerState();
                }
                W7hHomeActivity w7hHomeActivity2 = W7hHomeActivity.this;
                if (w7hHomeActivity2.safeCallFragment(w7hHomeActivity2.lsFragmentPet)) {
                    W7hHomeActivity.this.lsFragmentPet.clearPlayerState();
                }
                W7hHomeActivity w7hHomeActivity3 = W7hHomeActivity.this;
                if (w7hHomeActivity3.safeCallFragment(w7hHomeActivity3.lsFragmentDrink)) {
                    W7hHomeActivity.this.lsFragmentDrink.clearPlayerState();
                }
            }

            @Override // com.petkit.android.activities.petkitBleDevice.w7h.W7hVideoPlayFragment.FromLiveOnclickListener
            public void onEventClick() {
                W7hHomeActivity.this.showBottomList();
            }

            @Override // com.petkit.android.activities.petkitBleDevice.w7h.W7hVideoPlayFragment.FromLiveOnclickListener
            public void onBackClick() {
                W7hHomeActivity.this.switchPortual();
                backLive();
            }

            @Override // com.petkit.android.activities.petkitBleDevice.w7h.W7hVideoPlayFragment.FromLiveOnclickListener
            public void closeRightList() {
                W7hHomeActivity.this.hideRightList();
            }
        });
        this.mBinding.lsViewPager.setOffscreenPageLimit(1);
        this.mBinding.lsViewPager.setAdapter(this.fragmentStateAdapter);
        this.mBinding.lsViewPager.setOrientation(1);
        this.mBinding.lsViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity.19
            public final /* synthetic */ List val$list;

            public AnonymousClass19(List list2) {
                list = list2;
            }

            @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
            public void onPageScrolled(int i, float f, int i2) {
                super.onPageScrolled(i, f, i2);
            }

            @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
            public void onPageSelected(int i) {
                super.onPageSelected(i);
                W7hHomeActivity.this.landscapeCurrentEventPosition = i;
                int i2 = W7hHomeActivity.this.landscapeCurrentPosition;
                if (i2 == 0) {
                    W7hHomeActivity w7hHomeActivity = W7hHomeActivity.this;
                    if (w7hHomeActivity.safeCallFragment(w7hHomeActivity.lsFragmentAll)) {
                        W7hHomeActivity.this.lsFragmentAll.updateItemPlayerState(i);
                        return;
                    }
                    return;
                }
                if (i2 == 1) {
                    W7hHomeActivity w7hHomeActivity2 = W7hHomeActivity.this;
                    if (w7hHomeActivity2.safeCallFragment(w7hHomeActivity2.lsFragmentPet)) {
                        W7hHomeActivity.this.lsFragmentPet.updateItemPlayerState(i);
                        return;
                    }
                    return;
                }
                if (i2 != 2) {
                    return;
                }
                W7hHomeActivity w7hHomeActivity3 = W7hHomeActivity.this;
                if (w7hHomeActivity3.safeCallFragment(w7hHomeActivity3.lsFragmentDrink)) {
                    W7hHomeActivity.this.lsFragmentDrink.updateItemPlayerState(i);
                }
            }

            @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
            public void onPageScrollStateChanged(int i) {
                List list2;
                super.onPageScrollStateChanged(i);
                if (i != 0 || (list2 = list) == null || list2.isEmpty()) {
                    return;
                }
                int iMin = Math.min(list.size() - 1, W7hHomeActivity.this.landscapeCurrentEventPosition + 1);
                for (int iMax = Math.max(0, W7hHomeActivity.this.landscapeCurrentEventPosition - 1); iMax <= iMin; iMax++) {
                    if (iMax != W7hHomeActivity.this.landscapeCurrentEventPosition) {
                        Fragment fragmentFindFragmentByTag = W7hHomeActivity.this.getSupportFragmentManager().findFragmentByTag("f" + iMax);
                        if (fragmentFindFragmentByTag instanceof W7hVideoPlayFragment) {
                            ((W7hVideoPlayFragment) fragmentFindFragmentByTag).removePlayer();
                        }
                    }
                }
                Fragment fragmentFindFragmentByTag2 = W7hHomeActivity.this.getSupportFragmentManager().findFragmentByTag("f" + W7hHomeActivity.this.landscapeCurrentEventPosition);
                if (fragmentFindFragmentByTag2 instanceof W7hVideoPlayFragment) {
                    Log.d(((BaseActivity) W7hHomeActivity.this).TAG, "onPageScrollStateChanged: SCROLL_STATE_IDLE");
                    ((W7hVideoPlayFragment) fragmentFindFragmentByTag2).fragmentIsVisible();
                }
                for (int i2 = 0; i2 < W7hHomeActivity.this.fragmentStateAdapter.getItemCount(); i2++) {
                    Fragment fragmentFindFragmentByTag3 = W7hHomeActivity.this.getSupportFragmentManager().findFragmentByTag("f" + i2);
                    if (fragmentFindFragmentByTag3 instanceof W7hVideoPlayFragment) {
                        ((W7hVideoPlayFragment) fragmentFindFragmentByTag3).switchLandscape();
                    }
                }
            }
        });
        this.mBinding.lsViewPager.setCurrentItem(this.selectedItemPosition, false);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$19 */
    public class AnonymousClass19 extends ViewPager2.OnPageChangeCallback {
        public final /* synthetic */ List val$list;

        public AnonymousClass19(List list2) {
            list = list2;
        }

        @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
        public void onPageScrolled(int i, float f, int i2) {
            super.onPageScrolled(i, f, i2);
        }

        @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
        public void onPageSelected(int i) {
            super.onPageSelected(i);
            W7hHomeActivity.this.landscapeCurrentEventPosition = i;
            int i2 = W7hHomeActivity.this.landscapeCurrentPosition;
            if (i2 == 0) {
                W7hHomeActivity w7hHomeActivity = W7hHomeActivity.this;
                if (w7hHomeActivity.safeCallFragment(w7hHomeActivity.lsFragmentAll)) {
                    W7hHomeActivity.this.lsFragmentAll.updateItemPlayerState(i);
                    return;
                }
                return;
            }
            if (i2 == 1) {
                W7hHomeActivity w7hHomeActivity2 = W7hHomeActivity.this;
                if (w7hHomeActivity2.safeCallFragment(w7hHomeActivity2.lsFragmentPet)) {
                    W7hHomeActivity.this.lsFragmentPet.updateItemPlayerState(i);
                    return;
                }
                return;
            }
            if (i2 != 2) {
                return;
            }
            W7hHomeActivity w7hHomeActivity3 = W7hHomeActivity.this;
            if (w7hHomeActivity3.safeCallFragment(w7hHomeActivity3.lsFragmentDrink)) {
                W7hHomeActivity.this.lsFragmentDrink.updateItemPlayerState(i);
            }
        }

        @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
        public void onPageScrollStateChanged(int i) {
            List list2;
            super.onPageScrollStateChanged(i);
            if (i != 0 || (list2 = list) == null || list2.isEmpty()) {
                return;
            }
            int iMin = Math.min(list.size() - 1, W7hHomeActivity.this.landscapeCurrentEventPosition + 1);
            for (int iMax = Math.max(0, W7hHomeActivity.this.landscapeCurrentEventPosition - 1); iMax <= iMin; iMax++) {
                if (iMax != W7hHomeActivity.this.landscapeCurrentEventPosition) {
                    Fragment fragmentFindFragmentByTag = W7hHomeActivity.this.getSupportFragmentManager().findFragmentByTag("f" + iMax);
                    if (fragmentFindFragmentByTag instanceof W7hVideoPlayFragment) {
                        ((W7hVideoPlayFragment) fragmentFindFragmentByTag).removePlayer();
                    }
                }
            }
            Fragment fragmentFindFragmentByTag2 = W7hHomeActivity.this.getSupportFragmentManager().findFragmentByTag("f" + W7hHomeActivity.this.landscapeCurrentEventPosition);
            if (fragmentFindFragmentByTag2 instanceof W7hVideoPlayFragment) {
                Log.d(((BaseActivity) W7hHomeActivity.this).TAG, "onPageScrollStateChanged: SCROLL_STATE_IDLE");
                ((W7hVideoPlayFragment) fragmentFindFragmentByTag2).fragmentIsVisible();
            }
            for (int i2 = 0; i2 < W7hHomeActivity.this.fragmentStateAdapter.getItemCount(); i2++) {
                Fragment fragmentFindFragmentByTag3 = W7hHomeActivity.this.getSupportFragmentManager().findFragmentByTag("f" + i2);
                if (fragmentFindFragmentByTag3 instanceof W7hVideoPlayFragment) {
                    ((W7hVideoPlayFragment) fragmentFindFragmentByTag3).switchLandscape();
                }
            }
        }
    }

    public void showLandscapeRecordPlayview() {
        this.mBinding.lsSrl.setVisibility(0);
        this.mBinding.rlLandscapeVideo.setVisibility(8);
        if (this.isStartRecord) {
            stopRecording();
        }
        ((W7hHomePresenter) this.mPresenter).closeVolume();
        stopLive();
    }

    public void backLive() {
        this.mBinding.lsSrl.setVisibility(8);
        this.mBinding.rlLandscapeVideo.setVisibility(0);
        this.mBinding.lsViewPager.setAdapter(null);
        if (((W7hHomePresenter) this.mPresenter).getLiveService() != null) {
            if (this.isForeground) {
                this.isForeground = false;
                Message message = new Message();
                message.arg1 = 0;
                message.what = 21;
                MyHandler myHandler = this.handler;
                if (myHandler != null) {
                    myHandler.sendMessageDelayed(message, 500L);
                }
            } else {
                W7hRecord w7hRecord = this.w7hRecord;
                if (w7hRecord != null) {
                    setupViews(w7hRecord);
                }
            }
            if (DeviceVolumeUtils.getInstance().isLiveMuteFlag(29)) {
                ((W7hHomePresenter) this.mPresenter).closeVolume();
                return;
            } else {
                ((W7hHomePresenter) this.mPresenter).openVolume();
                return;
            }
        }
        W7hRecord w7hRecord2 = this.w7hRecord;
        if (w7hRecord2 != null) {
            setupViews(w7hRecord2);
        }
    }

    public List<T6RecordFragmentInfor> handleEventListToFragment(List<W7hEventInfo> list) {
        ArrayList arrayList = new ArrayList();
        this.selectedItemPosition = 0;
        if (list != null && list.size() > 0) {
            boolean z = false;
            for (int i = 0; i < list.size(); i++) {
                W7hEventInfo w7hEventInfo = list.get(i);
                if (System.currentTimeMillis() / 1000 <= w7hEventInfo.getExpire() && (!TextUtils.isEmpty(w7hEventInfo.getPreview()) || !TextUtils.isEmpty(w7hEventInfo.getMediaApi()))) {
                    if (this.w7hEventInfo.getEventId().equals(w7hEventInfo.getEventId())) {
                        z = true;
                    } else if (!z) {
                        this.selectedItemPosition++;
                    }
                    arrayList.add(new T6RecordFragmentInfor(1, w7hEventInfo));
                }
            }
        }
        if (this.landscapeCurrentEventPosition == -1) {
            this.landscapeCurrentEventPosition = this.selectedItemPosition;
        }
        return arrayList;
    }

    public void refreshDeviceBottomStateView(W7hRecord w7hRecord) {
        this.mBinding.bottomView.unDisableAll();
        if (W7hUtils.getInstance().checkAllState(w7hRecord)) {
            this.mBinding.bottomView.disableAll();
            return;
        }
        boolean zCheckDisinfectState = W7hUtils.getInstance().checkDisinfectState(w7hRecord);
        boolean zCheckFlowState = W7hUtils.getInstance().checkFlowState(w7hRecord);
        boolean zCheckRefillState = W7hUtils.getInstance().checkRefillState(w7hRecord);
        boolean zCheckDrainState = W7hUtils.getInstance().checkDrainState(w7hRecord);
        boolean zCheckHeatingState = W7hUtils.getInstance().checkHeatingState(w7hRecord);
        boolean zCheckWashingState = W7hUtils.getInstance().checkWashingState(w7hRecord);
        boolean zCheckSettingState = W7hUtils.getInstance().checkSettingState(w7hRecord);
        boolean zCheckVoiceState = W7hUtils.getInstance().checkVoiceState(w7hRecord, (((W7hHomePresenter) this.mPresenter).getLiveService() == null || !((W7hHomePresenter) this.mPresenter).getLiveService().isInChannel() || this.w7hLiveVideoView.liveIsBlack()) ? false : true);
        if (zCheckHeatingState) {
            this.mBinding.bottomView.disableHeating();
        }
        if (zCheckWashingState) {
            this.mBinding.bottomView.disableWashing();
        }
        if (zCheckSettingState) {
            this.mBinding.bottomView.disableSetting();
        }
        if (zCheckVoiceState) {
            this.mBinding.bottomView.disableVoice();
        }
        if (zCheckRefillState) {
            this.mBinding.bottomView.disableRefill();
        }
        if (zCheckDrainState) {
            this.mBinding.bottomView.disableDrain();
        }
        if (zCheckDisinfectState) {
            this.mBinding.bottomView.disableDisinfect();
        }
        if (zCheckFlowState) {
            this.mBinding.bottomView.disableFlow();
        }
    }

    private void workingState(boolean z) {
        this.mBinding.deviceWorkStateText.setGravity(GravityCompat.START);
        this.mBinding.rlWorkState.setVisibility(z ? 0 : 8);
        this.mBinding.rlWorkState.setOnClickListener(null);
        this.mBinding.ivWarnRight.setVisibility(8);
        if (!z) {
            List<DeviceError> listCalcDeviceError = W7hUtils.getInstance().calcDeviceError(getActivity(), ((W7hHomePresenter) this.mPresenter).getW7hRecord());
            if (listCalcDeviceError.size() == 1) {
                this.mBinding.rlDeviceErrorOne.setVisibility(0);
            } else if (listCalcDeviceError.size() >= 2) {
                this.mBinding.rlDeviceErrorOne.setVisibility(0);
                this.mBinding.rlDeviceErrorTwo.setVisibility(0);
            } else {
                this.mBinding.rlDeviceErrorOne.setVisibility(8);
                this.mBinding.rlDeviceErrorTwo.setVisibility(8);
            }
            ((W7hHomePresenter) this.mPresenter).stopDisinfect();
            return;
        }
        this.mBinding.rlDeviceErrorOne.setVisibility(8);
        this.mBinding.rlDeviceErrorTwo.setVisibility(8);
    }

    @Override // com.petkit.android.activities.petkitBleDevice.w7h.contract.W7hHomeContract.View
    public void disinfectFinished() {
        ((W7hHomePresenter) this.mPresenter).stopDisinfect();
    }

    private void refreshWorkingStateView(W7hRecord w7hRecord) {
        workingState(w7hRecord.getState().getWorkState() != null);
        if (w7hRecord.getState().getLiftValveState() == 4 && !W7hUtils.getInstance().isBlockDrainWater(w7hRecord)) {
            this.mBinding.deviceWorkStateText.setText(getResources().getString(R.string.Drain_valve_raised));
            workingState(true);
            return;
        }
        if (w7hRecord.getState().getWorkState() != null && 1 == w7hRecord.getState().getWorkState().getWorkProcess() / 10) {
            int workMode = w7hRecord.getState().getWorkState().getWorkMode();
            if ((w7hRecord.getState().getFlushState() == 1 || workMode == 1) && !W7hUtils.getInstance().isBlockFlush(w7hRecord)) {
                this.mBinding.deviceWorkStateText.setText(getResources().getString(R.string.W7h_Rinsing_status));
                return;
            }
            if ((w7hRecord.getState().getAddWaterState() == 1 || workMode == 2) && !W7hUtils.getInstance().isBlockAddWater(w7hRecord)) {
                this.mBinding.deviceWorkStateText.setText(getResources().getString(R.string.W7h_Add_Water_status));
                return;
            }
            if (workMode == 4 && !W7hUtils.getInstance().isBlockDisinfect(w7hRecord)) {
                this.mBinding.ivWarnRight.setVisibility(0);
                this.mBinding.rlWorkState.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$$ExternalSyntheticLambda62
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$refreshWorkingStateView$16(view);
                    }
                });
                if (w7hRecord.getState().getDisinfectState() == 2) {
                    this.mBinding.ivWarnRight.setVisibility(0);
                    this.mBinding.deviceWorkStateText.setText(getString(R.string.Add_boiling_water));
                    return;
                } else if (w7hRecord.getState().getDisinfectState() == 3) {
                    ((W7hHomePresenter) this.mPresenter).startDisinfect(w7hRecord);
                    this.mBinding.deviceWorkStateText.setText(getResources().getString(R.string.Boiling_sterilizing_remaining, ((W7hHomePresenter) this.mPresenter).getTimeRemaining()));
                    return;
                } else {
                    workingState(false);
                    return;
                }
            }
            if (workMode == 5 && !W7hUtils.getInstance().isBlockReplaceWater(w7hRecord)) {
                this.mBinding.deviceWorkStateText.setText(getResources().getString(R.string.Drain_Refilling));
                return;
            } else {
                workingState(false);
                return;
            }
        }
        if (w7hRecord.getState().getWorkState() != null && 2 == w7hRecord.getState().getWorkState().getWorkProcess() / 10) {
            int workMode2 = w7hRecord.getState().getWorkState().getWorkMode();
            int safeWarn = w7hRecord.getState().getWorkState().getSafeWarn();
            if (safeWarn != 5) {
                if (safeWarn != 9) {
                    this.mBinding.deviceWorkStateText.setText(getResources().getString(R.string.Safe_warn_general));
                    return;
                }
                if (workMode2 == 1) {
                    this.mBinding.deviceWorkStateText.setText(R.string.W7H_storage_water_box_not_install_in_working);
                    return;
                } else if (workMode2 == 5) {
                    this.mBinding.deviceWorkStateText.setText(R.string.Water_tray_removed_Drain_Refill_paused);
                    return;
                } else {
                    this.mBinding.deviceWorkStateText.setText(R.string.Water_tray_removed);
                    return;
                }
            }
            if (w7hRecord.getState().getDrinkTime() <= 0) {
                this.mBinding.deviceWorkStateText.setText(getResources().getString(R.string.Safe_warn_general));
                return;
            }
            if (workMode2 == 1) {
                this.mBinding.deviceWorkStateText.setText(getResources().getString(R.string.W7h_Pet_appear_pause_status));
                return;
            } else if (workMode2 == 3) {
                this.mBinding.deviceWorkStateText.setText(R.string.Reset_paused_because_pet_nearby);
                return;
            } else {
                this.mBinding.deviceWorkStateText.setText(getResources().getString(R.string.Safe_warn_general));
                return;
            }
        }
        workingState(false);
    }

    public /* synthetic */ void lambda$refreshWorkingStateView$16(View view) {
        launchActivity(W7hSterilizationActivity.newIntent(getActivity(), this.deviceId, this.deviceType));
    }

    @Override // com.petkit.android.activities.petkitBleDevice.w7h.contract.W7hHomeContract.View
    public void refreshTimeRemaining(String str) {
        if (((W7hHomePresenter) this.mPresenter).getW7hRecord().getState().getWorkState() != null) {
            refreshWorkingStateView(((W7hHomePresenter) this.mPresenter).getW7hRecord());
        }
    }

    private void refreshErrorStateView(W7hRecord w7hRecord) {
        this.mBinding.rlDeviceState.setVisibility(8);
        this.mBinding.rlWorkState.setVisibility(8);
        this.mBinding.rlDeviceErrorOne.setVisibility(8);
        this.mBinding.rlDeviceErrorTwo.setVisibility(8);
        if (w7hRecord.getState().getOverall() == 2) {
            this.mBinding.rlDeviceState.setVisibility(0);
            this.mBinding.deviceStateText.setText(R.string.Device_off_line_tip);
            this.mBinding.ivDeviceStateIcon.setImageResource(R.drawable.d4sh_main_warn_message_warn_icon);
            this.mBinding.ivDeviceStateIcon.setVisibility(0);
            this.mBinding.ivRightArrow.setVisibility(0);
            return;
        }
        if (w7hRecord.getState().getOta() == 1) {
            this.mBinding.rlDeviceState.setVisibility(0);
            this.mBinding.deviceStateText.setText(R.string.D4sh_update);
            this.mBinding.ivDeviceStateIcon.setVisibility(8);
            this.mBinding.ivRightArrow.setVisibility(4);
            return;
        }
        filterErrorStatus();
    }

    private void filterErrorStatus() {
        List<DeviceError> listCalcDeviceError = W7hUtils.getInstance().calcDeviceError(getActivity(), ((W7hHomePresenter) this.mPresenter).getW7hRecord());
        this.mBinding.ivDeviceErrorOneRightArrow.setVisibility(0);
        this.mBinding.ivDeviceErrorTwoRightArrow.setVisibility(0);
        this.mBinding.llKnowMoreOne.setVisibility(8);
        this.mBinding.btnIgnoreOne.setVisibility(0);
        this.mBinding.btnKnowMoreOne.setVisibility(0);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mBinding.deviceErrorOneText.getLayoutParams();
        layoutParams.addRule(15, -1);
        if (!listCalcDeviceError.isEmpty()) {
            String errorType = listCalcDeviceError.get(0).getErrorType();
            if (W7hUtils.ERROR_CODE_TANK_DF.equals(errorType) || W7hUtils.ERROR_CODE_PET_LIFT_VALUE_RESET.equals(errorType) || W7hUtils.ERROR_CODE_FILTER_TIP.equals(errorType)) {
                layoutParams.addRule(15, 0);
            }
        }
        this.mBinding.deviceErrorOneText.setLayoutParams(layoutParams);
        if (listCalcDeviceError.size() > 1) {
            final DeviceError deviceError = listCalcDeviceError.get(0);
            this.mBinding.rlDeviceErrorOne.setVisibility(0);
            this.mBinding.ivDeviceErrorOneIcon.setImageResource(R.drawable.d4sh_main_warn_message_warn_icon);
            this.mBinding.deviceErrorOneText.setText(deviceError.getErrorMsg());
            this.mBinding.deviceErrorOneText.setGravity(GravityCompat.START);
            this.mBinding.rlDeviceErrorTwo.setVisibility(0);
            this.mBinding.ivDeviceErrorOneIcon.setVisibility(0);
            this.mBinding.ivDeviceErrorTwoRightArrow.setVisibility(0);
            this.mBinding.llKnowMoreTwo.setVisibility(8);
            this.mBinding.ivDeviceErrorTwoIcon.setImageResource(R.drawable.d4sh_main_warn_message_warn_icon);
            this.mBinding.deviceErrorTwoText.setText(getResources().getString(R.string.Have_mistake_to_handle, String.valueOf(listCalcDeviceError.size() - 1)));
            if (deviceError.getErrorType().equals(W7hUtils.ERROR_CODE_TANK_DF)) {
                this.mBinding.rlDeviceErrorOne.setVisibility(0);
                this.mBinding.ivDeviceErrorOneIcon.setVisibility(4);
                this.mBinding.ivDeviceErrorOneRightArrow.setVisibility(4);
                this.mBinding.llKnowMoreOne.setVisibility(0);
                this.mBinding.btnIgnoreOne.setVisibility(0);
                this.mBinding.btnKnowMoreOne.setVisibility(0);
                this.mBinding.btnIgnoreOne.setText(getString(R.string.No_more_reminders_for_3_days));
                this.mBinding.btnKnowMoreOne.setText(getString(R.string.Know_more));
                this.mBinding.deviceErrorOneText.setGravity(17);
                this.mBinding.btnIgnoreOne.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$$ExternalSyntheticLambda52
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$filterErrorStatus$17(view);
                    }
                });
                this.mBinding.btnKnowMoreOne.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$$ExternalSyntheticLambda53
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$filterErrorStatus$18(deviceError, view);
                    }
                });
                return;
            }
            if (deviceError.getErrorType().equals(W7hUtils.ERROR_CODE_PET_LIFT_VALUE_RESET)) {
                this.mBinding.rlDeviceErrorOne.setVisibility(0);
                this.mBinding.ivDeviceErrorOneIcon.setVisibility(8);
                this.mBinding.ivDeviceErrorOneRightArrow.setVisibility(8);
                this.mBinding.llKnowMoreOne.setVisibility(0);
                this.mBinding.btnIgnoreOne.setVisibility(0);
                this.mBinding.btnKnowMoreOne.setVisibility(8);
                this.mBinding.deviceErrorOneText.setGravity(17);
                this.mBinding.btnIgnoreOne.setText(getString(R.string.Reset));
                this.mBinding.btnIgnoreOne.setBackgroundResource(R.drawable.solid_t6_work_blue_bg);
                this.mBinding.btnIgnoreOne.setTextColor(getResources().getColor(R.color.new_bind_blue));
                this.mBinding.btnIgnoreOne.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$$ExternalSyntheticLambda54
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$filterErrorStatus$19(view);
                    }
                });
                return;
            }
            if (deviceError.getErrorType().equals(W7hUtils.ERROR_CODE_FILTER_TIP)) {
                this.mBinding.rlDeviceErrorOne.setVisibility(0);
                this.mBinding.ivDeviceErrorOneIcon.setVisibility(4);
                this.mBinding.ivDeviceErrorOneRightArrow.setVisibility(4);
                this.mBinding.llKnowMoreOne.setVisibility(0);
                this.mBinding.btnIgnoreOne.setVisibility(0);
                this.mBinding.btnKnowMoreOne.setVisibility(0);
                this.mBinding.btnIgnoreOne.setBackgroundResource(R.drawable.solid_t6_work_blue_bg);
                this.mBinding.btnIgnoreOne.setText(getString(R.string.Invent_ignore));
                this.mBinding.btnKnowMoreOne.setText(getString(R.string.Reset));
                this.mBinding.deviceErrorOneText.setGravity(17);
                this.mBinding.btnIgnoreOne.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$$ExternalSyntheticLambda55
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$filterErrorStatus$20(view);
                    }
                });
                this.mBinding.btnKnowMoreOne.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$$ExternalSyntheticLambda56
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$filterErrorStatus$21(view);
                    }
                });
                return;
            }
            return;
        }
        if (listCalcDeviceError.size() == 1) {
            final DeviceError deviceError2 = listCalcDeviceError.get(0);
            this.mBinding.rlDeviceErrorTwo.setVisibility(8);
            this.mBinding.rlDeviceErrorOne.setVisibility(0);
            this.mBinding.ivDeviceErrorOneIcon.setVisibility(0);
            this.mBinding.ivDeviceErrorOneRightArrow.setVisibility(0);
            this.mBinding.ivDeviceErrorOneIcon.setImageResource(R.drawable.d4sh_main_warn_message_warn_icon);
            this.mBinding.deviceErrorOneText.setText(deviceError2.getErrorMsg());
            this.mBinding.deviceErrorOneText.setGravity(GravityCompat.START);
            if (deviceError2.getErrorType().equals(W7hUtils.ERROR_CODE_TANK_DF)) {
                this.mBinding.rlDeviceErrorOne.setVisibility(0);
                this.mBinding.ivDeviceErrorOneIcon.setVisibility(4);
                this.mBinding.ivDeviceErrorOneRightArrow.setVisibility(4);
                this.mBinding.llKnowMoreOne.setVisibility(0);
                this.mBinding.btnIgnoreOne.setVisibility(0);
                this.mBinding.btnKnowMoreOne.setVisibility(0);
                this.mBinding.btnIgnoreOne.setText(getString(R.string.No_more_reminders_for_3_days));
                this.mBinding.btnKnowMoreOne.setText(getString(R.string.Know_more));
                this.mBinding.deviceErrorOneText.setGravity(17);
                this.mBinding.btnIgnoreOne.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$$ExternalSyntheticLambda57
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$filterErrorStatus$22(view);
                    }
                });
                this.mBinding.btnKnowMoreOne.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$$ExternalSyntheticLambda58
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$filterErrorStatus$23(deviceError2, view);
                    }
                });
                return;
            }
            if (deviceError2.getErrorType().equals(W7hUtils.ERROR_CODE_PET_LIFT_VALUE_RESET)) {
                this.mBinding.rlDeviceErrorOne.setVisibility(0);
                this.mBinding.ivDeviceErrorOneIcon.setVisibility(8);
                this.mBinding.ivDeviceErrorOneRightArrow.setVisibility(8);
                this.mBinding.llKnowMoreOne.setVisibility(0);
                this.mBinding.btnIgnoreOne.setVisibility(0);
                this.mBinding.btnKnowMoreOne.setVisibility(8);
                this.mBinding.deviceErrorOneText.setGravity(17);
                this.mBinding.btnIgnoreOne.setText(getString(R.string.Reset));
                this.mBinding.btnIgnoreOne.setBackgroundResource(R.drawable.solid_t6_work_blue_bg);
                this.mBinding.btnIgnoreOne.setTextColor(getResources().getColor(R.color.new_bind_blue));
                this.mBinding.btnIgnoreOne.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$$ExternalSyntheticLambda59
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$filterErrorStatus$24(view);
                    }
                });
                return;
            }
            if (deviceError2.getErrorType().equals(W7hUtils.ERROR_CODE_FILTER_TIP)) {
                this.mBinding.rlDeviceErrorOne.setVisibility(0);
                this.mBinding.ivDeviceErrorOneIcon.setVisibility(4);
                this.mBinding.ivDeviceErrorOneRightArrow.setVisibility(4);
                this.mBinding.llKnowMoreOne.setVisibility(0);
                this.mBinding.btnIgnoreOne.setVisibility(0);
                this.mBinding.btnKnowMoreOne.setVisibility(0);
                this.mBinding.btnIgnoreOne.setText(getString(R.string.Invent_ignore));
                this.mBinding.btnKnowMoreOne.setText(getString(R.string.Reset));
                this.mBinding.btnKnowMoreOne.setTextColor(getResources().getColor(R.color.new_bind_blue));
                this.mBinding.btnKnowMoreOne.setBackgroundResource(R.drawable.solid_t6_work_blue_bg);
                this.mBinding.deviceErrorOneText.setGravity(17);
                this.mBinding.btnIgnoreOne.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$$ExternalSyntheticLambda60
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$filterErrorStatus$25(view);
                    }
                });
                this.mBinding.btnKnowMoreOne.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$$ExternalSyntheticLambda61
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$filterErrorStatus$26(view);
                    }
                });
            }
        }
    }

    public /* synthetic */ void lambda$filterErrorStatus$17(View view) {
        ((W7hHomePresenter) this.mPresenter).recordUserClickAction(2);
    }

    public /* synthetic */ void lambda$filterErrorStatus$18(DeviceError deviceError, View view) {
        showErrorWindow(deviceError);
    }

    public /* synthetic */ void lambda$filterErrorStatus$19(View view) {
        ((W7hHomePresenter) this.mPresenter).resetLiftValve();
    }

    public /* synthetic */ void lambda$filterErrorStatus$20(View view) {
        CommonUtils.addSysBoolMap(getActivity(), W7hUtils.W7H_IS_IGNORE_FILTER_TIP + this.deviceId, true);
        setupViews(this.w7hRecord);
    }

    public /* synthetic */ void lambda$filterErrorStatus$21(View view) {
        launchActivity(W7hConsumablesActivity.newIntent(getActivity(), this.deviceId, 3));
    }

    public /* synthetic */ void lambda$filterErrorStatus$22(View view) {
        ((W7hHomePresenter) this.mPresenter).recordUserClickAction(2);
    }

    public /* synthetic */ void lambda$filterErrorStatus$23(DeviceError deviceError, View view) {
        showErrorWindow(deviceError);
    }

    public /* synthetic */ void lambda$filterErrorStatus$24(View view) {
        ((W7hHomePresenter) this.mPresenter).resetLiftValve();
    }

    public /* synthetic */ void lambda$filterErrorStatus$25(View view) {
        CommonUtils.addSysBoolMap(getActivity(), W7hUtils.W7H_IS_IGNORE_FILTER_TIP + this.deviceId, true);
        setupViews(this.w7hRecord);
    }

    public /* synthetic */ void lambda$filterErrorStatus$26(View view) {
        launchActivity(W7hConsumablesActivity.newIntent(getActivity(), this.deviceId, 3));
    }

    private void showErrorWindow(DeviceError deviceError) {
        int faultResource = W7hUtils.getInstance().getFaultResource(deviceError.getErrorType());
        T6GuideInfo abnormalResource = W7hUtils.getInstance().getAbnormalResource(deviceError.getErrorType());
        String errorType = deviceError.getErrorType();
        errorType.hashCode();
        switch (errorType) {
            case "wifi_signal_weak":
                showWeakWifiWindow();
                break;
            case "ERROR_CODE_PET_DRINK":
                showDrinkAbnormalDialog(null);
                break;
            case "ERROR_CODE_PET_ADD_WATER_FAIL":
                startActivity(WebViewControllerActivity.newIntent(this, getString(R.string.How_to_handle_add_water_fail), ApiTools.getWebUrlByKey("w7h_add_water_fail"), "ADD_WATER_TYPE", this.deviceId));
                break;
            case "timezone_is_different":
                showZoneNotEqual();
                break;
        }
        if (abnormalResource == null) {
            if (faultResource != 0) {
                BleDeviceHomeTroubleWarnWindow bleDeviceHomeTroubleWarnWindow = new BleDeviceHomeTroubleWarnWindow(this, this.deviceType, getResources().getString(R.string.Failure_remind), this.w7hRecord.getState().getErrorDetail(), faultResource);
                this.warnWindow = bleDeviceHomeTroubleWarnWindow;
                bleDeviceHomeTroubleWarnWindow.showAtLocation(getWindow().getDecorView(), 17, 0, 0);
                return;
            }
            PetkitLog.d("showErrorWindow: error");
            return;
        }
        this.gitWindow = new DeviceErrorWarnWindow(this, this.deviceType, abnormalResource.getStepName(), abnormalResource.getStepDesc(), abnormalResource.getStepUrl());
        if (deviceError.getErrorType().equals(W7hUtils.ERROR_CODE_PET_CYCLE_WATER_PUMP)) {
            this.gitWindow.setBtnText(getString(R.string.I_Have_Cleaned_spring));
        } else if (deviceError.getErrorType().equals(W7hUtils.ERROR_CODE_PET_ADD_WATER_PUMP)) {
            this.gitWindow.setBtnText(getString(R.string.I_Have_Cleaned_add_water));
        } else if (deviceError.getErrorType().equals(W7hUtils.ERROR_CODE_PET_LIFT_VALUE)) {
            this.gitWindow.setResetListener();
            this.gitWindow.setBtnText(getString(R.string.I_know));
        } else if (deviceError.getErrorType().equals(W7hUtils.ERROR_CODE_TANK_LESS)) {
            this.gitWindow.setBtnText(getString(R.string.No_more_reminders_for_time));
        } else {
            this.gitWindow.setBtnText(getString(R.string.I_know));
            this.gitWindow.setCloseVisibility(8);
        }
        this.gitWindow.showAtLocation(getWindow().getDecorView(), 17, 0, 0);
        this.gitWindow.setOnClickListener(new DeviceErrorWarnWindow.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity.20
            public final /* synthetic */ DeviceError val$deviceError;

            public AnonymousClass20(DeviceError deviceError2) {
                deviceError = deviceError2;
            }

            @Override // com.petkit.android.activities.petkitBleDevice.widget.DeviceErrorWarnWindow.OnClickListener
            public void onClickLearnMore() {
                if (deviceError.getErrorType().equals(W7hUtils.ERROR_CODE_PET_CYCLE_WATER_PUMP)) {
                    ((W7hHomePresenter) ((BaseActivity) W7hHomeActivity.this).mPresenter).resetCyclePump();
                    return;
                }
                if (deviceError.getErrorType().equals(W7hUtils.ERROR_CODE_PET_ADD_WATER_PUMP)) {
                    ((W7hHomePresenter) ((BaseActivity) W7hHomeActivity.this).mPresenter).controlAddWater();
                } else if (deviceError.getErrorType().equals(W7hUtils.ERROR_CODE_TANK_LESS)) {
                    ((W7hHomePresenter) ((BaseActivity) W7hHomeActivity.this).mPresenter).recordUserClickAction(3);
                    ((W7hHomePresenter) ((BaseActivity) W7hHomeActivity.this).mPresenter).cwtLightWarnClear();
                }
            }

            @Override // com.petkit.android.activities.petkitBleDevice.widget.DeviceErrorWarnWindow.OnClickListener
            public void onClickText() {
                ((W7hHomePresenter) ((BaseActivity) W7hHomeActivity.this).mPresenter).resetLiftValve();
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$20 */
    public class AnonymousClass20 implements DeviceErrorWarnWindow.OnClickListener {
        public final /* synthetic */ DeviceError val$deviceError;

        public AnonymousClass20(DeviceError deviceError2) {
            deviceError = deviceError2;
        }

        @Override // com.petkit.android.activities.petkitBleDevice.widget.DeviceErrorWarnWindow.OnClickListener
        public void onClickLearnMore() {
            if (deviceError.getErrorType().equals(W7hUtils.ERROR_CODE_PET_CYCLE_WATER_PUMP)) {
                ((W7hHomePresenter) ((BaseActivity) W7hHomeActivity.this).mPresenter).resetCyclePump();
                return;
            }
            if (deviceError.getErrorType().equals(W7hUtils.ERROR_CODE_PET_ADD_WATER_PUMP)) {
                ((W7hHomePresenter) ((BaseActivity) W7hHomeActivity.this).mPresenter).controlAddWater();
            } else if (deviceError.getErrorType().equals(W7hUtils.ERROR_CODE_TANK_LESS)) {
                ((W7hHomePresenter) ((BaseActivity) W7hHomeActivity.this).mPresenter).recordUserClickAction(3);
                ((W7hHomePresenter) ((BaseActivity) W7hHomeActivity.this).mPresenter).cwtLightWarnClear();
            }
        }

        @Override // com.petkit.android.activities.petkitBleDevice.widget.DeviceErrorWarnWindow.OnClickListener
        public void onClickText() {
            ((W7hHomePresenter) ((BaseActivity) W7hHomeActivity.this).mPresenter).resetLiftValve();
        }
    }

    public void showDrinkAbnormalDialog(Runnable runnable) {
        Resources resources;
        int i;
        List<W7hPetDrinkTipVO> petDrinkTips = this.w7hRecord.getPetDrinkTips();
        if (CollectionUtil.isNotEmpty(petDrinkTips)) {
            ArrayList arrayList = new ArrayList();
            StringBuilder sb = new StringBuilder();
            for (int i2 = 0; i2 < petDrinkTips.size(); i2++) {
                W7hPetDrinkTipVO w7hPetDrinkTipVO = petDrinkTips.get(i2);
                if (w7hPetDrinkTipVO.getOutDay() > 1) {
                    resources = getResources();
                    i = R.string.Unit_days;
                } else {
                    resources = getResources();
                    i = R.string.Unit_day;
                }
                String string = resources.getString(i);
                sb.append(getResources().getString(R.string.Pet_not_drink_Water_tip, w7hPetDrinkTipVO.getPetName(), w7hPetDrinkTipVO.getOutDay() + string));
                arrayList.add(w7hPetDrinkTipVO.getPetName());
                arrayList.add(w7hPetDrinkTipVO.getOutDay() + string);
            }
            String[] strArr = (String[]) arrayList.toArray(new String[arrayList.size()]);
            AnonymousClass21 anonymousClass21 = new SpannableStringColorsWindow.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity.21
                public final /* synthetic */ Runnable val$next;
                public final /* synthetic */ String val$todayValue;

                public AnonymousClass21(Runnable runnable2, String str) {
                    runnable = runnable2;
                    str = str;
                }

                @Override // com.petkit.android.activities.universalWindow.SpannableStringColorsWindow.OnClickListener
                public void onCancel() {
                    ((W7hHomePresenter) ((BaseActivity) W7hHomeActivity.this).mPresenter).recordUserClickAction(1);
                    if (runnable != null) {
                        CommonUtils.addSysMap(Constants.W7H_DRINK_ERROR + str, str);
                        runnable.run();
                    }
                }

                @Override // com.petkit.android.activities.universalWindow.SpannableStringColorsWindow.OnClickListener
                public void onConfirm() {
                    if (runnable != null) {
                        CommonUtils.addSysMap(Constants.W7H_DRINK_ERROR + str, str);
                        runnable.run();
                    }
                }
            };
            String string2 = getResources().getString(R.string.Drink_abnormal_remind);
            StringBuilder sb2 = new StringBuilder();
            sb2.append((Object) sb);
            sb2.append(getResources().getString(R.string.T_Pet_No_Toilet_Desc));
            new SpannableStringColorsWindow(this, anonymousClass21, string2, sb2.toString(), getResources().getString(R.string.I_already_know), getResources().getString(R.string.Not_remind), R.color.new_bind_blue, strArr).show();
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$21 */
    public class AnonymousClass21 implements SpannableStringColorsWindow.OnClickListener {
        public final /* synthetic */ Runnable val$next;
        public final /* synthetic */ String val$todayValue;

        public AnonymousClass21(Runnable runnable2, String str) {
            runnable = runnable2;
            str = str;
        }

        @Override // com.petkit.android.activities.universalWindow.SpannableStringColorsWindow.OnClickListener
        public void onCancel() {
            ((W7hHomePresenter) ((BaseActivity) W7hHomeActivity.this).mPresenter).recordUserClickAction(1);
            if (runnable != null) {
                CommonUtils.addSysMap(Constants.W7H_DRINK_ERROR + str, str);
                runnable.run();
            }
        }

        @Override // com.petkit.android.activities.universalWindow.SpannableStringColorsWindow.OnClickListener
        public void onConfirm() {
            if (runnable != null) {
                CommonUtils.addSysMap(Constants.W7H_DRINK_ERROR + str, str);
                runnable.run();
            }
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$22 */
    public class AnonymousClass22 implements NormalCenterTipWindow.OnClickOk {
        public AnonymousClass22() {
        }

        @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
        public void onOkClick() {
            W7hHomeActivity w7hHomeActivity = W7hHomeActivity.this;
            w7hHomeActivity.setupViews(((W7hHomePresenter) ((BaseActivity) w7hHomeActivity).mPresenter).getW7hRecord());
        }

        @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
        public void onCancelClick() {
            W7hHomeActivity w7hHomeActivity = W7hHomeActivity.this;
            w7hHomeActivity.setupViews(((W7hHomePresenter) ((BaseActivity) w7hHomeActivity).mPresenter).getW7hRecord());
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.w7h.contract.W7hHomeContract.View
    public void showConfirmTipDialog() {
        NormalCenterTipWindow gravity = new NormalCenterTipWindow(getActivity(), new NormalCenterTipWindow.OnClickOk() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity.22
            public AnonymousClass22() {
            }

            @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
            public void onOkClick() {
                W7hHomeActivity w7hHomeActivity = W7hHomeActivity.this;
                w7hHomeActivity.setupViews(((W7hHomePresenter) ((BaseActivity) w7hHomeActivity).mPresenter).getW7hRecord());
            }

            @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
            public void onCancelClick() {
                W7hHomeActivity w7hHomeActivity = W7hHomeActivity.this;
                w7hHomeActivity.setupViews(((W7hHomePresenter) ((BaseActivity) w7hHomeActivity).mPresenter).getW7hRecord());
            }
        }, (String) null, getString(R.string.W7H_drink_pet_abnormal_completion_tips)).setGravity(17);
        gravity.setSelectText(null, getString(R.string.I_know));
        if (gravity.isShowing()) {
            return;
        }
        gravity.show(getWindow().getDecorView());
    }

    private void showZoneNotEqual() {
        NewIKnowWindow newIKnowWindow = this.timezoneWindow;
        if (newIKnowWindow == null || !newIKnowWindow.isShowing()) {
            NewIKnowWindow newIKnowWindow2 = new NewIKnowWindow(this, getString(R.string.About_device_time), "\t\t\t\t" + getString(R.string.About_device_time_introduce), (String) null);
            this.timezoneWindow = newIKnowWindow2;
            newIKnowWindow2.showAtLocation(getWindow().getDecorView(), 17, 0, 0);
        }
    }

    private void showWeakWifiWindow() {
        if (this.wifiWeakWindow == null) {
            this.wifiWeakWindow = new NewWifiWeakWindow(R.drawable.w7h_wifi_weak_signal_image, this);
        }
        if (this.w7hRecord.getState().getWifi().getRsq() <= -65 && this.w7hRecord.getState().getWifi().getRsq() > -75) {
            if (TimeUtils.getInstance().getCurrentSeconds() - CommonUtils.getSysLongMap(this, W7hDataUtils.FINALLY_WIFI_NO_REMIND_TIME + this.w7hRecord.getDeviceId()) > 2592000) {
                this.wifiWeakWindow.setNoRemindVisibility(0);
                this.wifiWeakWindow.setNoRemindClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity.23
                    public AnonymousClass23() {
                    }

                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        W7hHomeActivity.this.wifiWeakWindow.dismiss();
                        CommonUtils.addSysLongMap(W7hHomeActivity.this, W7hDataUtils.FINALLY_WIFI_NO_REMIND_TIME + W7hHomeActivity.this.w7hRecord.getDeviceId(), TimeUtils.getInstance().getCurrentSeconds());
                        W7hHomeActivity w7hHomeActivity = W7hHomeActivity.this;
                        w7hHomeActivity.setupViews(w7hHomeActivity.w7hRecord);
                    }
                });
                this.wifiWeakWindow.showAtLocation(getWindow().getDecorView(), 17, 0, 0);
                return;
            }
            return;
        }
        if (this.w7hRecord.getState().getWifi().getRsq() < -75) {
            this.wifiWeakWindow.setNoRemindVisibility(8);
            CommonUtils.addSysLongMap(this, W7hDataUtils.FINALLY_WIFI_NO_REMIND_TIME + this.w7hRecord.getDeviceId(), 0L);
            this.wifiWeakWindow.showAtLocation(getWindow().getDecorView(), 17, 0, 0);
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$23 */
    public class AnonymousClass23 implements View.OnClickListener {
        public AnonymousClass23() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            W7hHomeActivity.this.wifiWeakWindow.dismiss();
            CommonUtils.addSysLongMap(W7hHomeActivity.this, W7hDataUtils.FINALLY_WIFI_NO_REMIND_TIME + W7hHomeActivity.this.w7hRecord.getDeviceId(), TimeUtils.getInstance().getCurrentSeconds());
            W7hHomeActivity w7hHomeActivity = W7hHomeActivity.this;
            w7hHomeActivity.setupViews(w7hHomeActivity.w7hRecord);
        }
    }

    private void setupTopBar(W7hRecord w7hRecord) {
        this.ibSetting.setImageResource(DeviceCenterUtils.isW7hNeedOtaById(w7hRecord.getDeviceId()) ? R.drawable.black_setting_with_notify_flag : R.drawable.black_setting_icon);
        if (w7hRecord.getServiceStatus() == 0) {
            this.tvDeviceTag.setVisibility(4);
        } else if (w7hRecord.getServiceStatus() == 1) {
            this.tvDeviceTag.setVisibility(0);
            this.tvDeviceTag.setTextColor(CommonUtils.getColorById(R.color.receive_for_free));
            this.tvDeviceTag.setBackgroundResource(R.drawable.gradient_gold_corners_petkitcare_tag_bg);
        } else if (w7hRecord.getServiceStatus() == 2) {
            this.tvDeviceTag.setTextColor(CommonUtils.getColorById(R.color.home_feeder_bg_gray));
            this.tvDeviceTag.setBackgroundResource(R.drawable.gradient_gray_corners_petkitcare_tag_bg);
            this.tvDeviceTag.setVisibility(0);
        }
        String name = w7hRecord.getName();
        this.deviceName = name;
        if (TextUtils.isEmpty(name)) {
            this.deviceName = getResources().getString(R.string.W7h_name_default);
        }
        if (isSingleDevice(w7hRecord)) {
            this.deviceName = String.format("%s-%s", w7hRecord.getDeviceShared().getOwnerNick(), this.deviceName);
        }
        if (this.mBinding.rlTopTab.getVisibility() == 0) {
            TextView textView = this.toolbarTitle;
            String str = this.title;
            if (str == null) {
                str = this.deviceName;
            }
            textView.setText(str);
        } else {
            this.toolbarTitle.setText(this.deviceName);
        }
        this.ivHeatStatus.setVisibility(8);
        this.llToolbarStatus.setVisibility(0);
        if (w7hRecord.getState().getOverall() == 2) {
            this.tvToolbarStatus.setText(getString(R.string.Offline));
            this.llToolbarStatus.setBackgroundResource(R.drawable.solid_t4_status_gray);
        } else if (w7hRecord.getState().getOverall() == 3) {
            this.tvToolbarStatus.setText(getString(R.string.Mate_ota));
            this.llToolbarStatus.setBackgroundResource(R.drawable.solid_t4_status_green);
        } else {
            if (W7hUtils.getInstance().getHeatSwitch() && w7hRecord.getState().getHeatInstall() == 1 && !W7hUtils.getInstance().heatModuleFault(w7hRecord)) {
                if (w7hRecord.getState().getHeatState() == 1) {
                    this.ivHeatStatus.setVisibility(0);
                    this.tvToolbarStatus.setText(getString(R.string.Cozy_temperature_hoting));
                    this.llToolbarStatus.setBackgroundResource(R.drawable.solid_radius12_heat_warming);
                    this.ivHeatStatus.setImageResource(R.drawable.w7h_heating_animation);
                } else if (w7hRecord.getState().getHeatState() == 2) {
                    this.ivHeatStatus.setVisibility(8);
                    this.tvToolbarStatus.setText(getString(R.string.Heating_Paused));
                    this.llToolbarStatus.setBackgroundResource(R.drawable.solid_radius12_heat_warming);
                } else if (w7hRecord.getState().getHeatState() == 3) {
                    this.ivHeatStatus.setVisibility(0);
                    this.tvToolbarStatus.setText(getString(R.string.Heating_at_max_power));
                    this.llToolbarStatus.setBackgroundResource(R.drawable.solid_radius12_heat_warming);
                    this.ivHeatStatus.setImageResource(R.drawable.w7h_heating_animation);
                } else if (w7hRecord.getState().getHeatState() == 4) {
                    this.ivHeatStatus.setVisibility(0);
                    this.tvToolbarStatus.setText(getString(R.string.Constant_Temp));
                    this.llToolbarStatus.setBackgroundResource(R.drawable.solid_radius12_heat_constant_temp);
                    this.ivHeatStatus.setImageResource(R.drawable.w7h_constant_temp_animation);
                } else if (w7hRecord.getState().getHeatState() == 5) {
                    this.ivHeatStatus.setVisibility(8);
                    this.llToolbarStatus.setBackgroundResource(R.drawable.solid_radius12_heat_cooling_down);
                    this.tvToolbarStatus.setText(getString(R.string.Cooling_down));
                } else {
                    this.llToolbarStatus.setVisibility(8);
                }
                if (this.ivHeatStatus.getVisibility() == 0) {
                    this.ivHeatStatus.post(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$$ExternalSyntheticLambda22
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$setupTopBar$27();
                        }
                    });
                }
            } else {
                this.llToolbarStatus.setVisibility(8);
            }
            if (this.llToolbarStatus.getVisibility() == 0) {
                this.topMargin = 66;
            } else {
                this.topMargin = 50;
            }
        }
        if (this.llToolbarStatus.getVisibility() == 0) {
            this.topMargin = 66;
        } else {
            this.topMargin = 50;
        }
    }

    public /* synthetic */ void lambda$setupTopBar$27() {
        AnimationDrawable animationDrawable = (AnimationDrawable) this.ivHeatStatus.getDrawable();
        this.flamesAnim = animationDrawable;
        if (this.flamesAnimStop || animationDrawable.isRunning()) {
            return;
        }
        this.flamesAnim.start();
        sendStopFireAnimation();
    }

    @Override // com.petkit.android.activities.petkitBleDevice.w7h.contract.W7hHomeContract.View
    public void initDeviceDetailFinish() {
        if (TextUtils.isEmpty(this.eventId) || this.eventId.equals("null") || this.eventId.equals("Null") || this.eventId.equals("NULL")) {
            return;
        }
        ((W7hHomePresenter) this.mPresenter).getEventMediaInfo(this.deviceId, this.eventId);
        this.eventId = null;
    }

    private void initTodayTime() {
        this.calendarTime = W7hDataUtils.getCurrentZoneTime(this.w7hRecord, this.deviceId, System.currentTimeMillis(), this.deviceType);
        this.landscapeCalendarTime = W7hDataUtils.getCurrentZoneTime(this.w7hRecord, this.deviceId, System.currentTimeMillis(), this.deviceType);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$24 */
    public class AnonymousClass24 implements W7hBottomView.BottomListener {
        @Override // com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hBottomView.BottomListener
        public void Ai(boolean z) {
        }

        public AnonymousClass24() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hBottomView.BottomListener
        public void refillClick() {
            if (((W7hHomeActivity.this.w7hRecord.getState().getLiftValveState() != 4 && W7hHomeActivity.this.w7hRecord.getState().getLiftValveState() != 3) || W7hHomeActivity.this.w7hRecord.getSettings().getCamera() != 0) && W7hHomeActivity.this.w7hRecord.getState().getCameraStatus() != 0) {
                ((W7hHomePresenter) ((BaseActivity) W7hHomeActivity.this).mPresenter).controlAddWater();
            } else {
                W7hHomeActivity.this.w7hOpenCameraAndRefillWindow();
            }
        }

        @Override // com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hBottomView.BottomListener
        public void drainClick() {
            W7hHomeActivity.this.controlDrainingDialog();
        }

        @Override // com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hBottomView.BottomListener
        public void flowClick() {
            W7hHomeActivity w7hHomeActivity = W7hHomeActivity.this;
            w7hHomeActivity.launchActivity(W7hFlowModeActivity.newIntent(w7hHomeActivity.getActivity(), W7hHomeActivity.this.deviceId, W7hHomeActivity.this.deviceType));
        }

        @Override // com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hBottomView.BottomListener
        public void heatClick() {
            if (W7hHomeActivity.this.w7hRecord.getState().getHeatInstall() == 0) {
                W7hHomeActivity w7hHomeActivity = W7hHomeActivity.this;
                w7hHomeActivity.launchActivity(HeatDisinfectActivity.newIntent(w7hHomeActivity.getActivity(), W7hHomeActivity.this.deviceId, W7hHomeActivity.this.deviceType, 1));
            } else {
                W7hHomeActivity w7hHomeActivity2 = W7hHomeActivity.this;
                w7hHomeActivity2.launchActivity(W7hHeatModuleActivity.newIntent(w7hHomeActivity2.getActivity(), W7hHomeActivity.this.deviceId, W7hHomeActivity.this.deviceType));
            }
        }

        @Override // com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hBottomView.BottomListener
        public void refillSettingsClick() {
            W7hHomeActivity w7hHomeActivity = W7hHomeActivity.this;
            w7hHomeActivity.startActivity(W7hAddWaterSettingActivity.newIntent(w7hHomeActivity, w7hHomeActivity.deviceId, W7hHomeActivity.this.deviceType));
        }

        @Override // com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hBottomView.BottomListener
        public void disinfectClick() {
            W7hHomeActivity w7hHomeActivity = W7hHomeActivity.this;
            w7hHomeActivity.launchActivity(HeatDisinfectActivity.newIntent(w7hHomeActivity.getActivity(), W7hHomeActivity.this.deviceId, W7hHomeActivity.this.deviceType, 2));
        }

        @Override // com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hBottomView.BottomListener
        public void washingClick() {
            if (W7hHomeActivity.this.w7hRecord.getSettings().getCamera() == 0 || W7hHomeActivity.this.w7hRecord.getState().getCameraStatus() == 0) {
                W7hHomeActivity.this.w7hOpenCameraAndWashingWindow();
            } else {
                W7hHomeActivity.this.controlLandscapeWashingDialog();
            }
        }

        @Override // com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hBottomView.BottomListener
        public void talkDown() {
            if (W7hHomeActivity.this.w7hRecord.getState().getOverall() == 2) {
                W7hHomeActivity w7hHomeActivity = W7hHomeActivity.this;
                PetkitToast.showTopToast(w7hHomeActivity, w7hHomeActivity.getResources().getString(R.string.Mate_connect_offline), 0, 0);
                return;
            }
            if (DataHelper.getBooleanSF(W7hHomeActivity.this, Constants.W7H_HOWLING_FLAG + W7hHomeActivity.this.w7hRecord.getDeviceId())) {
                ((W7hHomePresenter) ((BaseActivity) W7hHomeActivity.this).mPresenter).startRecordAudioDown();
                W7hHomeActivity.this.mBinding.soundWaveView.setVisibility(0);
            } else {
                W7hHomeActivity.this.showHowlingWindow();
            }
        }

        @Override // com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hBottomView.BottomListener
        public void talkUp() {
            ((W7hHomePresenter) ((BaseActivity) W7hHomeActivity.this).mPresenter).startRecordAudioUp();
            W7hHomeActivity.this.mBinding.soundWaveView.setVisibility(4);
            DeviceVolumeUtils.getInstance().isLiveMuteFlag(W7hHomeActivity.this.deviceType);
        }
    }

    private void initBottomView() {
        this.mBinding.bottomView.setBottomListener(new W7hBottomView.BottomListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity.24
            @Override // com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hBottomView.BottomListener
            public void Ai(boolean z) {
            }

            public AnonymousClass24() {
            }

            @Override // com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hBottomView.BottomListener
            public void refillClick() {
                if (((W7hHomeActivity.this.w7hRecord.getState().getLiftValveState() != 4 && W7hHomeActivity.this.w7hRecord.getState().getLiftValveState() != 3) || W7hHomeActivity.this.w7hRecord.getSettings().getCamera() != 0) && W7hHomeActivity.this.w7hRecord.getState().getCameraStatus() != 0) {
                    ((W7hHomePresenter) ((BaseActivity) W7hHomeActivity.this).mPresenter).controlAddWater();
                } else {
                    W7hHomeActivity.this.w7hOpenCameraAndRefillWindow();
                }
            }

            @Override // com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hBottomView.BottomListener
            public void drainClick() {
                W7hHomeActivity.this.controlDrainingDialog();
            }

            @Override // com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hBottomView.BottomListener
            public void flowClick() {
                W7hHomeActivity w7hHomeActivity = W7hHomeActivity.this;
                w7hHomeActivity.launchActivity(W7hFlowModeActivity.newIntent(w7hHomeActivity.getActivity(), W7hHomeActivity.this.deviceId, W7hHomeActivity.this.deviceType));
            }

            @Override // com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hBottomView.BottomListener
            public void heatClick() {
                if (W7hHomeActivity.this.w7hRecord.getState().getHeatInstall() == 0) {
                    W7hHomeActivity w7hHomeActivity = W7hHomeActivity.this;
                    w7hHomeActivity.launchActivity(HeatDisinfectActivity.newIntent(w7hHomeActivity.getActivity(), W7hHomeActivity.this.deviceId, W7hHomeActivity.this.deviceType, 1));
                } else {
                    W7hHomeActivity w7hHomeActivity2 = W7hHomeActivity.this;
                    w7hHomeActivity2.launchActivity(W7hHeatModuleActivity.newIntent(w7hHomeActivity2.getActivity(), W7hHomeActivity.this.deviceId, W7hHomeActivity.this.deviceType));
                }
            }

            @Override // com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hBottomView.BottomListener
            public void refillSettingsClick() {
                W7hHomeActivity w7hHomeActivity = W7hHomeActivity.this;
                w7hHomeActivity.startActivity(W7hAddWaterSettingActivity.newIntent(w7hHomeActivity, w7hHomeActivity.deviceId, W7hHomeActivity.this.deviceType));
            }

            @Override // com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hBottomView.BottomListener
            public void disinfectClick() {
                W7hHomeActivity w7hHomeActivity = W7hHomeActivity.this;
                w7hHomeActivity.launchActivity(HeatDisinfectActivity.newIntent(w7hHomeActivity.getActivity(), W7hHomeActivity.this.deviceId, W7hHomeActivity.this.deviceType, 2));
            }

            @Override // com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hBottomView.BottomListener
            public void washingClick() {
                if (W7hHomeActivity.this.w7hRecord.getSettings().getCamera() == 0 || W7hHomeActivity.this.w7hRecord.getState().getCameraStatus() == 0) {
                    W7hHomeActivity.this.w7hOpenCameraAndWashingWindow();
                } else {
                    W7hHomeActivity.this.controlLandscapeWashingDialog();
                }
            }

            @Override // com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hBottomView.BottomListener
            public void talkDown() {
                if (W7hHomeActivity.this.w7hRecord.getState().getOverall() == 2) {
                    W7hHomeActivity w7hHomeActivity = W7hHomeActivity.this;
                    PetkitToast.showTopToast(w7hHomeActivity, w7hHomeActivity.getResources().getString(R.string.Mate_connect_offline), 0, 0);
                    return;
                }
                if (DataHelper.getBooleanSF(W7hHomeActivity.this, Constants.W7H_HOWLING_FLAG + W7hHomeActivity.this.w7hRecord.getDeviceId())) {
                    ((W7hHomePresenter) ((BaseActivity) W7hHomeActivity.this).mPresenter).startRecordAudioDown();
                    W7hHomeActivity.this.mBinding.soundWaveView.setVisibility(0);
                } else {
                    W7hHomeActivity.this.showHowlingWindow();
                }
            }

            @Override // com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hBottomView.BottomListener
            public void talkUp() {
                ((W7hHomePresenter) ((BaseActivity) W7hHomeActivity.this).mPresenter).startRecordAudioUp();
                W7hHomeActivity.this.mBinding.soundWaveView.setVisibility(4);
                DeviceVolumeUtils.getInstance().isLiveMuteFlag(W7hHomeActivity.this.deviceType);
            }
        });
        this.mBinding.bottomView.findViewById(R.id.ll_ai_cat).setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$$ExternalSyntheticLambda31
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initBottomView$28(view);
            }
        });
    }

    public /* synthetic */ void lambda$initBottomView$28(View view) {
        launchActivity(PetWeightActivity.newIntent(this, this.deviceId, this.deviceType));
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$25 */
    public class AnonymousClass25 implements NormalCenterTipWindow.OnClickOk {
        @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
        public void onCancelClick() {
        }

        public AnonymousClass25() {
        }

        @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
        public void onOkClick() {
            ((W7hHomePresenter) ((BaseActivity) W7hHomeActivity.this).mPresenter).controlWashing();
        }
    }

    public void controlWashingDialog() {
        NormalCenterTipWindow normalCenterTipWindow = new NormalCenterTipWindow(getActivity(), new NormalCenterTipWindow.OnClickOk() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity.25
            @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
            public void onCancelClick() {
            }

            public AnonymousClass25() {
            }

            @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
            public void onOkClick() {
                ((W7hHomePresenter) ((BaseActivity) W7hHomeActivity.this).mPresenter).controlWashing();
            }
        }, (String) null, new SpannableString(getString(R.string.Flush_prompt)));
        ((TextView) normalCenterTipWindow.getContentView().findViewById(R.id.tv_cancel)).setText(getString(R.string.Cancel));
        ((TextView) normalCenterTipWindow.getContentView().findViewById(R.id.tv_cancel)).setTextColor(getResources().getColor(R.color.gray));
        ((TextView) normalCenterTipWindow.getContentView().findViewById(R.id.tv_ok)).setText(getString(R.string.Confirm));
        ((TextView) normalCenterTipWindow.getContentView().findViewById(R.id.tv_ok)).setTextColor(getResources().getColor(R.color.new_bind_blue));
        normalCenterTipWindow.show(getWindow().getDecorView());
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$26 */
    public class AnonymousClass26 implements NormalCenterTipWindow.OnClickOk {
        @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
        public void onCancelClick() {
        }

        public AnonymousClass26() {
        }

        @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
        public void onOkClick() {
            ((W7hHomePresenter) ((BaseActivity) W7hHomeActivity.this).mPresenter).controlDraining();
        }
    }

    public void controlDrainingDialog() {
        NormalCenterTipWindow normalCenterTipWindow = new NormalCenterTipWindow(getActivity(), new NormalCenterTipWindow.OnClickOk() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity.26
            @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
            public void onCancelClick() {
            }

            public AnonymousClass26() {
            }

            @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
            public void onOkClick() {
                ((W7hHomePresenter) ((BaseActivity) W7hHomeActivity.this).mPresenter).controlDraining();
            }
        }, (String) null, new SpannableString(getString(R.string.Start_Draining_tip)));
        ((TextView) normalCenterTipWindow.getContentView().findViewById(R.id.tv_cancel)).setText(getString(R.string.Cancel));
        ((TextView) normalCenterTipWindow.getContentView().findViewById(R.id.tv_cancel)).setTextColor(getResources().getColor(R.color.gray));
        ((TextView) normalCenterTipWindow.getContentView().findViewById(R.id.tv_ok)).setText(getString(R.string.Confirm));
        ((TextView) normalCenterTipWindow.getContentView().findViewById(R.id.tv_ok)).setTextColor(getResources().getColor(R.color.new_bind_blue));
        normalCenterTipWindow.setWindowWidth((int) DeviceUtils.dpToPixel(this, 270.0f));
        normalCenterTipWindow.show(getWindow().getDecorView());
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$27 */
    public class AnonymousClass27 implements NormalCenterTipWindow.OnClickOk {
        @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
        public void onCancelClick() {
        }

        public AnonymousClass27() {
        }

        @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
        public void onOkClick() {
            ((W7hHomePresenter) ((BaseActivity) W7hHomeActivity.this).mPresenter).controlWashing();
        }
    }

    public void controlLandscapeWashingDialog() {
        NormalCenterTipWindow normalCenterTipWindow = new NormalCenterTipWindow(getActivity(), new NormalCenterTipWindow.OnClickOk() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity.27
            @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
            public void onCancelClick() {
            }

            public AnonymousClass27() {
            }

            @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
            public void onOkClick() {
                ((W7hHomePresenter) ((BaseActivity) W7hHomeActivity.this).mPresenter).controlWashing();
            }
        }, (String) null, new SpannableString(getString(R.string.Flush_prompt)));
        ((TextView) normalCenterTipWindow.getContentView().findViewById(R.id.tv_cancel)).setText(getString(R.string.Cancel));
        ((TextView) normalCenterTipWindow.getContentView().findViewById(R.id.tv_cancel)).setTextColor(getResources().getColor(R.color.gray));
        ((TextView) normalCenterTipWindow.getContentView().findViewById(R.id.tv_ok)).setText(getString(R.string.Confirm));
        ((TextView) normalCenterTipWindow.getContentView().findViewById(R.id.tv_ok)).setTextColor(getResources().getColor(R.color.new_bind_blue));
        normalCenterTipWindow.setWindowWidth((int) DeviceUtils.dpToPixel(this, 270.0f));
        normalCenterTipWindow.show(getWindow().getDecorView());
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$28 */
    public class AnonymousClass28 implements NormalCenterTipWindow.OnClickOk {
        @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
        public void onCancelClick() {
        }

        public AnonymousClass28() {
        }

        @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
        public void onOkClick() {
            ((W7hHomePresenter) ((BaseActivity) W7hHomeActivity.this).mPresenter).w7hTemporaryOpenCameraAW();
        }
    }

    public void w7hOpenCameraAndRefillWindow() {
        NormalCenterTipWindow normalCenterTipWindow = new NormalCenterTipWindow(getActivity(), new NormalCenterTipWindow.OnClickOk() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity.28
            @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
            public void onCancelClick() {
            }

            public AnonymousClass28() {
            }

            @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
            public void onOkClick() {
                ((W7hHomePresenter) ((BaseActivity) W7hHomeActivity.this).mPresenter).w7hTemporaryOpenCameraAW();
            }
        }, (String) null, new SpannableString(getString(R.string.Temp_refill_remind)));
        ((TextView) normalCenterTipWindow.getContentView().findViewById(R.id.tv_cancel)).setText(getString(R.string.Skip_adding));
        ((TextView) normalCenterTipWindow.getContentView().findViewById(R.id.tv_cancel)).setTextColor(getResources().getColor(R.color.new_bind_blue));
        ((TextView) normalCenterTipWindow.getContentView().findViewById(R.id.tv_ok)).setText(getString(R.string.Enable_Camera_Add_Water));
        ((TextView) normalCenterTipWindow.getContentView().findViewById(R.id.tv_ok)).setTextColor(getResources().getColor(R.color.new_bind_blue));
        normalCenterTipWindow.show(getActivity().getWindow().getDecorView());
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$29 */
    public class AnonymousClass29 implements NormalCenterTipWindow.OnClickOk {
        @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
        public void onCancelClick() {
        }

        public AnonymousClass29() {
        }

        @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
        public void onOkClick() {
            ((W7hHomePresenter) ((BaseActivity) W7hHomeActivity.this).mPresenter).temporaryOpenCameraFlush();
        }
    }

    public void w7hOpenCameraAndWashingWindow() {
        NormalCenterTipWindow normalCenterTipWindow = new NormalCenterTipWindow(getActivity(), new NormalCenterTipWindow.OnClickOk() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity.29
            @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
            public void onCancelClick() {
            }

            public AnonymousClass29() {
            }

            @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
            public void onOkClick() {
                ((W7hHomePresenter) ((BaseActivity) W7hHomeActivity.this).mPresenter).temporaryOpenCameraFlush();
            }
        }, (String) null, new SpannableString(getString(R.string.Temp_flush_remind)));
        ((TextView) normalCenterTipWindow.getContentView().findViewById(R.id.tv_cancel)).setText(getString(R.string.Not_flush_temp));
        ((TextView) normalCenterTipWindow.getContentView().findViewById(R.id.tv_cancel)).setTextColor(getResources().getColor(R.color.new_bind_blue));
        ((TextView) normalCenterTipWindow.getContentView().findViewById(R.id.tv_ok)).setText(getString(R.string.Open_camera_and_flush));
        ((TextView) normalCenterTipWindow.getContentView().findViewById(R.id.tv_ok)).setTextColor(getResources().getColor(R.color.new_bind_blue));
        normalCenterTipWindow.show(getWindow().getDecorView());
    }

    @Override // com.petkit.android.activities.petkitBleDevice.w7h.contract.W7hHomeContract.View
    public void controlAddWater(long j) {
        ((W7hHomePresenter) this.mPresenter).controlAddWater(true);
    }

    @Override // com.petkit.android.activities.petkitBleDevice.w7h.contract.W7hHomeContract.View
    public void showAddWaterTip() {
        new SimpleTipWindow(getActivity(), getString(R.string.Prompt), getString(R.string.After_water_refilling_complete), false).showAtLocation(getActivity().getWindow().getDecorView(), 17, 0, 0);
    }

    @Override // com.petkit.android.activities.petkitBleDevice.w7h.contract.W7hHomeContract.View
    public void controlFlush() {
        ((W7hHomePresenter) this.mPresenter).controlWashing(true);
    }

    @Override // com.petkit.android.activities.petkitBleDevice.w7h.contract.W7hHomeContract.View
    public void showWashingTip() {
        new SimpleTipWindow(this, getString(R.string.Prompt), getString(R.string.Temp_flush_tip), false).showAtLocation(getWindow().getDecorView(), 17, 0, 0);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$30 */
    public class AnonymousClass30 implements OnRefreshLoadMoreListener {
        @Override // com.scwang.smart.refresh.layout.listener.OnLoadMoreListener
        public void onLoadMore(@androidx.annotation.NonNull RefreshLayout refreshLayout) {
        }

        public AnonymousClass30() {
        }

        @Override // com.scwang.smart.refresh.layout.listener.OnRefreshListener
        public void onRefresh(@androidx.annotation.NonNull RefreshLayout refreshLayout) {
            W7hHomeActivity.this.refreshHeader();
            ((W7hHomePresenter) ((BaseActivity) W7hHomeActivity.this).mPresenter).getDeviceDetail(false);
            ((W7hHomePresenter) ((BaseActivity) W7hHomeActivity.this).mPresenter).getHighLightRecord(W7hHomeActivity.this.deviceId, W7hHomeActivity.this.deviceType, false);
            ((W7hHomePresenter) ((BaseActivity) W7hHomeActivity.this).mPresenter).bsBannerList(null);
        }
    }

    private void initSmartView() {
        this.mBinding.srl.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity.30
            @Override // com.scwang.smart.refresh.layout.listener.OnLoadMoreListener
            public void onLoadMore(@androidx.annotation.NonNull RefreshLayout refreshLayout) {
            }

            public AnonymousClass30() {
            }

            @Override // com.scwang.smart.refresh.layout.listener.OnRefreshListener
            public void onRefresh(@androidx.annotation.NonNull RefreshLayout refreshLayout) {
                W7hHomeActivity.this.refreshHeader();
                ((W7hHomePresenter) ((BaseActivity) W7hHomeActivity.this).mPresenter).getDeviceDetail(false);
                ((W7hHomePresenter) ((BaseActivity) W7hHomeActivity.this).mPresenter).getHighLightRecord(W7hHomeActivity.this.deviceId, W7hHomeActivity.this.deviceType, false);
                ((W7hHomePresenter) ((BaseActivity) W7hHomeActivity.this).mPresenter).bsBannerList(null);
            }
        });
        this.mBinding.srl.setDispatchListener(new MySmartRefreshView.DispatchListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$$ExternalSyntheticLambda27
            @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.MySmartRefreshView.DispatchListener
            public final void onActionDown() {
                this.f$0.lambda$initSmartView$29();
            }
        });
    }

    public /* synthetic */ void lambda$initSmartView$29() {
        if (this.mBinding.rlPop.getVisibility() == 0) {
            this.mBinding.rlPop.setVisibility(8);
        }
    }

    public void refreshHeader() {
        if (this.mBinding.ivAllDotTop.getVisibility() == 0 || this.mBinding.ivAllDot.getVisibility() == 0) {
            initTodayTime();
        }
        hideAllRedPoint();
        this.mBinding.ivEventAnim.setVisibility(0);
        this.animUtil.startRotateAnim(this.mBinding.ivEventAnim);
        ((W7hHomePresenter) this.mPresenter).getPetDrinkGraph(this.calendarTime, 0);
    }

    private void appBarChangedListener() {
        this.mBinding.appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$$ExternalSyntheticLambda37
            @Override // com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener, com.google.android.material.appbar.AppBarLayout.BaseOnOffsetChangedListener
            public final void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                this.f$0.lambda$appBarChangedListener$30(appBarLayout, i);
            }
        });
    }

    public /* synthetic */ void lambda$appBarChangedListener$30(AppBarLayout appBarLayout, int i) {
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
        if (Math.abs(i) > this.mBinding.appBar.getTotalScrollRange() - ArmsUtils.dip2px(this, 16.0f)) {
            if (this.mBinding.rlTopTab.getVisibility() == 8) {
                this.toolbarRoot.setBackgroundColor(getResources().getColor(R.color.white));
                this.statusBar.setBackgroundColor(getResources().getColor(R.color.white));
                this.mBinding.rlTopTab.setVisibility(0);
                this.mBinding.ivTodayEvent2.setVisibility(0);
                this.toolbarTitle.setMaxWidth(ArmsUtils.dip2px(this, 160.0f));
                this.showCare = this.tvDeviceTag.getVisibility() == 0;
                this.toolbarTitle.setText(this.mBinding.tvTodayEvent.getText().toString());
                this.tvDeviceTag.setVisibility(8);
            }
        } else if (this.mBinding.rlTopTab.getVisibility() == 0) {
            if (Math.abs(i) < this.mBinding.appBar.getTotalScrollRange() - ArmsUtils.dip2px(this, 30.0f) && this.appBarOffset == i) {
                this.mBinding.appBar.setExpanded(false, false);
            }
            this.toolbarRoot.setBackgroundColor(getResources().getColor(R.color.transparent));
            this.statusBar.setBackgroundColor(getResources().getColor(R.color.transparent));
            this.mBinding.rlTopTab.setVisibility(8);
            this.mBinding.ivTodayEvent2.setVisibility(8);
            this.toolbarTitle.setMaxWidth(ArmsUtils.dip2px(this, 190.0f));
            this.toolbarTitle.setText(this.deviceName);
            this.tvDeviceTag.setVisibility(this.showCare ? 0 : 8);
        }
        if (i == 0 || Math.abs(i) == this.mBinding.appBar.getMeasuredHeight()) {
            MyHandler myHandler3 = this.handler;
            if (myHandler3 != null) {
                myHandler3.sendEmptyMessage(20);
            }
        } else {
            startAppBarScroll();
        }
        this.appBarOffset = i;
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

    private void scrollToTop() {
        W7hEventListFragment w7hEventListFragment = this.allFragment;
        if (w7hEventListFragment != null) {
            w7hEventListFragment.scrollToTop();
        }
        W7hEventListFragment w7hEventListFragment2 = this.petFragment;
        if (w7hEventListFragment2 != null) {
            w7hEventListFragment2.scrollToTop();
        }
        W7hEventListFragment w7hEventListFragment3 = this.drankFragment;
        if (w7hEventListFragment3 != null) {
            w7hEventListFragment3.scrollToTop();
        }
        this.mBinding.appBar.setExpanded(true, true);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$31 */
    public class AnonymousClass31 implements T6AnimUtil.AnimationListener {
        @Override // com.petkit.android.activities.petkitBleDevice.t6.utils.T6AnimUtil.AnimationListener
        public void alphaAnimationEnd() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t6.utils.T6AnimUtil.AnimationListener
        public void alphaAnimationStar() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t6.utils.T6AnimUtil.AnimationListener
        public void cameraAnimationEnd() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t6.utils.T6AnimUtil.AnimationListener
        public void cameraAnimationStar() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t6.utils.T6AnimUtil.AnimationListener
        public void scaleAnimationEnd() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t6.utils.T6AnimUtil.AnimationListener
        public void scaleAnimationStar() {
        }

        public AnonymousClass31() {
        }
    }

    private void initAnim() {
        T6AnimUtil t6AnimUtil = new T6AnimUtil();
        this.animUtil = t6AnimUtil;
        t6AnimUtil.setAnimationListener(new T6AnimUtil.AnimationListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity.31
            @Override // com.petkit.android.activities.petkitBleDevice.t6.utils.T6AnimUtil.AnimationListener
            public void alphaAnimationEnd() {
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.utils.T6AnimUtil.AnimationListener
            public void alphaAnimationStar() {
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.utils.T6AnimUtil.AnimationListener
            public void cameraAnimationEnd() {
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.utils.T6AnimUtil.AnimationListener
            public void cameraAnimationStar() {
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.utils.T6AnimUtil.AnimationListener
            public void scaleAnimationEnd() {
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.utils.T6AnimUtil.AnimationListener
            public void scaleAnimationStar() {
            }

            public AnonymousClass31() {
            }
        });
        this.animUtil.startLeftArrowAnimation(this.ivLeftArrow);
    }

    public static class MyHandler extends Handler {
        public final WeakReference<W7hHomeActivity> mActivity;

        public MyHandler(Looper looper, W7hHomeActivity w7hHomeActivity) {
            super(looper);
            this.mActivity = new WeakReference<>(w7hHomeActivity);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            W7hHomeActivity w7hHomeActivity = this.mActivity.get();
            if (w7hHomeActivity != null) {
                w7hHomeActivity.updateMessage(message);
            }
        }
    }

    public void updateMessage(Message message) {
        AnimationDrawable animationDrawable;
        int i = message.what;
        if (i == 13) {
            this.handler.removeMessages(13);
            this.mBinding.bottomView.startColl();
            if (this.startCollAnim || this.isHidePromote) {
                return;
            }
            startPromoteViewAnim();
            return;
        }
        if (i == 14) {
            this.handler.removeMessages(14);
            this.mBinding.bottomView.endColl();
            if (this.startExpandAnim || !this.isHidePromote) {
                return;
            }
            endPromoteViewAnim();
            return;
        }
        if (i == 10) {
            this.mBinding.tvTopPaoPao.setVisibility(8);
            return;
        }
        if (i == 20) {
            return;
        }
        if (i == 22) {
            int i2 = message.arg1;
            if (i2 == 0) {
                RtmManager.getInstance().getRtmListInfo();
            }
            if (RtmManager.getInstance().isLoginRtm()) {
                this.handler.removeMessages(22);
                startLive();
                return;
            } else {
                if (i2 == 5) {
                    startLive();
                    return;
                }
                Message message2 = new Message();
                message2.what = 22;
                message2.arg1 = i2 + 1;
                this.handler.sendMessageDelayed(message2, 500L);
                return;
            }
        }
        if (i != 21) {
            if (i == 101 && (animationDrawable = this.flamesAnim) != null && animationDrawable.isRunning()) {
                this.flamesAnimStop = true;
                this.flamesAnim.stop();
                return;
            }
            return;
        }
        MyHandler myHandler = this.handler;
        if (myHandler != null) {
            myHandler.removeMessages(22);
        }
        if (RtmManager.getInstance().isLoginRtm()) {
            MyHandler myHandler2 = this.handler;
            if (myHandler2 != null) {
                myHandler2.removeMessages(21);
            }
            startLive();
            return;
        }
        int i3 = message.arg1;
        if (i3 == 5) {
            startLive();
            return;
        }
        Message message3 = new Message();
        message3.what = 21;
        message3.arg1 = i3 + 1;
        MyHandler myHandler3 = this.handler;
        if (myHandler3 != null) {
            myHandler3.sendMessageDelayed(message3, 500L);
        }
    }

    private void startPromoteViewAnim() {
        PromoteView promoteView = this.mBinding.promoteView;
        if (promoteView == null || promoteView.getVisibility() == 8) {
            return;
        }
        this.startCollAnim = true;
        TranslateAnimation translateAnimation = new TranslateAnimation(1, 0.0f, 1, 0.7f, 0, 0.0f, 0, 0.0f);
        translateAnimation.setFillAfter(true);
        translateAnimation.setDuration(400L);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity.32
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
            }

            public AnonymousClass32() {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                W7hHomeActivity.this.isHidePromote = true;
            }
        });
        this.mBinding.promoteView.startAnimation(translateAnimation);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$32 */
    public class AnonymousClass32 implements Animation.AnimationListener {
        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationRepeat(Animation animation) {
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationStart(Animation animation) {
        }

        public AnonymousClass32() {
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationEnd(Animation animation) {
            W7hHomeActivity.this.isHidePromote = true;
        }
    }

    private void endPromoteViewAnim() {
        PromoteView promoteView = this.mBinding.promoteView;
        if (promoteView == null || promoteView.getVisibility() == 8) {
            return;
        }
        this.startExpandAnim = true;
        TranslateAnimation translateAnimation = new TranslateAnimation(1, 0.7f, 1, 0.0f, 0, 0.0f, 0, 0.0f);
        translateAnimation.setFillAfter(true);
        translateAnimation.setDuration(400L);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity.33
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
            }

            public AnonymousClass33() {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                W7hHomeActivity.this.startCollAnim = false;
                W7hHomeActivity.this.startExpandAnim = false;
                W7hHomeActivity.this.isHidePromote = false;
            }
        });
        this.mBinding.promoteView.startAnimation(translateAnimation);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$33 */
    public class AnonymousClass33 implements Animation.AnimationListener {
        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationRepeat(Animation animation) {
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationStart(Animation animation) {
        }

        public AnonymousClass33() {
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationEnd(Animation animation) {
            W7hHomeActivity.this.startCollAnim = false;
            W7hHomeActivity.this.startExpandAnim = false;
            W7hHomeActivity.this.isHidePromote = false;
        }
    }

    @Override // com.jess.arms.base.BaseActivity, com.jess.arms.mvp.IView
    public void showLoading() {
        super.showLoading();
    }

    @Override // com.jess.arms.base.BaseActivity, com.jess.arms.mvp.IView
    public void hideLoading() {
        super.hideLoading();
    }

    @Override // com.petkit.android.activities.petkitBleDevice.w7h.contract.W7hHomeContract.View
    public void showAnimationLoading() {
        if (this.loadingWindow == null) {
            this.loadingWindow = new LoadingWindow(this);
        }
        this.loadingWindow.showLoading(getWindow().getDecorView());
    }

    @Override // com.petkit.android.activities.petkitBleDevice.w7h.contract.W7hHomeContract.View
    public void hideAnimationLoading() {
        LoadingWindow loadingWindow = this.loadingWindow;
        if (loadingWindow != null) {
            loadingWindow.hideLoading();
        }
    }

    @Override // com.jess.arms.base.BaseActivity, com.jess.arms.mvp.IView
    public void showMessage(@NonNull String str) {
        super.showMessage(str);
    }

    @Override // com.jess.arms.base.BaseActivity, com.jess.arms.mvp.IView
    public void launchActivity(@NonNull Intent intent) {
        super.launchActivity(intent);
    }

    @Override // com.jess.arms.base.BaseActivity, com.jess.arms.mvp.IView
    public void killMyself() {
        super.killMyself();
    }

    @Override // com.petkit.android.activities.petkitBleDevice.w7h.contract.W7hHomeContract.View
    public void passRelatedProductsInfor(RelatedProductsInfor relatedProductsInfor) {
        this.relatedProductsInfor = relatedProductsInfor;
        ArrayList arrayList = new ArrayList();
        if (relatedProductsInfor != null && relatedProductsInfor.getPromote() != null) {
            arrayList.addAll(relatedProductsInfor.getPromote());
        }
        if (arrayList.isEmpty()) {
            this.mBinding.promoteView.setVisibility(8);
            return;
        }
        this.mBinding.promoteView.enableSwitchable("W7h");
        this.mBinding.promoteView.refreshPromoteData(arrayList);
        this.mBinding.promoteView.setPromoteViewOnItemListener(new PromoteView.PromoteViewOnItemListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity.34
            public AnonymousClass34() {
            }

            @Override // com.petkit.android.activities.mall.widget.PromoteView.PromoteViewOnItemListener
            public void onItemClick(PromoteData promoteData) {
                new HashMap().put("contentId", "" + promoteData.getContentId());
                W7hHomeActivity w7hHomeActivity = W7hHomeActivity.this;
                w7hHomeActivity.launchActivity(WebUtils.getWebViewIntent(w7hHomeActivity, promoteData.getShareUrl(), W7hHomeActivity.this.getResources().getString(R.string.Title_PETKIT_store), promoteData.isYzUrl()));
            }
        });
        this.mBinding.promoteView.setVisibility(0);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$34 */
    public class AnonymousClass34 implements PromoteView.PromoteViewOnItemListener {
        public AnonymousClass34() {
        }

        @Override // com.petkit.android.activities.mall.widget.PromoteView.PromoteViewOnItemListener
        public void onItemClick(PromoteData promoteData) {
            new HashMap().put("contentId", "" + promoteData.getContentId());
            W7hHomeActivity w7hHomeActivity = W7hHomeActivity.this;
            w7hHomeActivity.launchActivity(WebUtils.getWebViewIntent(w7hHomeActivity, promoteData.getShareUrl(), W7hHomeActivity.this.getResources().getString(R.string.Title_PETKIT_store), promoteData.isYzUrl()));
        }
    }

    private void initFragment() {
        ArrayList arrayList = new ArrayList();
        this.allFragment = getFragment(0);
        this.petFragment = getFragment(1);
        this.drankFragment = getFragment(2);
        arrayList.add(this.allFragment);
        arrayList.add(this.petFragment);
        arrayList.add(this.drankFragment);
        this.mBinding.vpList.setAdapter(new EventViewPagerAdapter(getSupportFragmentManager(), arrayList));
        this.mBinding.vpList.setPagingEnabled(false);
        this.mBinding.vpList.setOffscreenPageLimit(2);
        this.mBinding.vpList.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity.35
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i, float f, int i2) {
            }

            public AnonymousClass35() {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int i) {
                W7hHomeActivity w7hHomeActivity = W7hHomeActivity.this;
                w7hHomeActivity.setTextUi(i, w7hHomeActivity.mBinding.tvTabAll, W7hHomeActivity.this.mBinding.tvTabPet, W7hHomeActivity.this.mBinding.tvTabDrank);
                W7hHomeActivity w7hHomeActivity2 = W7hHomeActivity.this;
                w7hHomeActivity2.setTopTextUi(i, w7hHomeActivity2.mBinding.tvTabAllTop, W7hHomeActivity.this.mBinding.tvTabPetTop, W7hHomeActivity.this.mBinding.tvTabDrankTop);
                W7hHomeActivity.this.currentPosition = i;
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$35 */
    public class AnonymousClass35 implements ViewPager.OnPageChangeListener {
        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrollStateChanged(int i) {
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrolled(int i, float f, int i2) {
        }

        public AnonymousClass35() {
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageSelected(int i) {
            W7hHomeActivity w7hHomeActivity = W7hHomeActivity.this;
            w7hHomeActivity.setTextUi(i, w7hHomeActivity.mBinding.tvTabAll, W7hHomeActivity.this.mBinding.tvTabPet, W7hHomeActivity.this.mBinding.tvTabDrank);
            W7hHomeActivity w7hHomeActivity2 = W7hHomeActivity.this;
            w7hHomeActivity2.setTopTextUi(i, w7hHomeActivity2.mBinding.tvTabAllTop, W7hHomeActivity.this.mBinding.tvTabPetTop, W7hHomeActivity.this.mBinding.tvTabDrankTop);
            W7hHomeActivity.this.currentPosition = i;
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$36 */
    public class AnonymousClass36 implements T6EventListFragment.FragmentListener {
        public AnonymousClass36() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t6.T6EventListFragment.FragmentListener
        public void recyclerViewScroll(int i) {
            if (i > 0) {
                if (W7hHomeActivity.this.handler != null) {
                    W7hHomeActivity.this.handler.sendEmptyMessage(13);
                }
            } else {
                if (i >= 0 || W7hHomeActivity.this.handler == null) {
                    return;
                }
                W7hHomeActivity.this.handler.sendEmptyMessage(14);
            }
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t6.T6EventListFragment.FragmentListener
        public void loadFirstPageData(int i) {
            if (i == 1) {
                W7hHomeActivity.this.newPetEventNum = 0;
            } else if (i != 2) {
                W7hHomeActivity.this.hideAllRedPoint();
            } else {
                W7hHomeActivity.this.newDrankEventNum = 0;
            }
        }
    }

    private W7hEventListFragment getFragment(int i) {
        W7hEventListFragment w7hEventListFragment = new W7hEventListFragment();
        w7hEventListFragment.setListener(new T6EventListFragment.FragmentListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity.36
            public AnonymousClass36() {
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.T6EventListFragment.FragmentListener
            public void recyclerViewScroll(int i2) {
                if (i2 > 0) {
                    if (W7hHomeActivity.this.handler != null) {
                        W7hHomeActivity.this.handler.sendEmptyMessage(13);
                    }
                } else {
                    if (i2 >= 0 || W7hHomeActivity.this.handler == null) {
                        return;
                    }
                    W7hHomeActivity.this.handler.sendEmptyMessage(14);
                }
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.T6EventListFragment.FragmentListener
            public void loadFirstPageData(int i2) {
                if (i2 == 1) {
                    W7hHomeActivity.this.newPetEventNum = 0;
                } else if (i2 != 2) {
                    W7hHomeActivity.this.hideAllRedPoint();
                } else {
                    W7hHomeActivity.this.newDrankEventNum = 0;
                }
            }
        });
        w7hEventListFragment.setArguments(getBundle(i));
        return w7hEventListFragment;
    }

    private Bundle getBundle(int i) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.W7H_EVENT_LOAD_TYPE, i);
        bundle.putLong(Constants.EXTRA_DEVICE_ID, this.deviceId);
        bundle.putInt(Constants.EXTRA_DEVICE_TYPE, 29);
        bundle.putLong("EXTRA_TIME", this.calendarTime);
        return bundle;
    }

    private void selectedFragment(int i) {
        this.mBinding.vpList.setCurrentItem(i, false);
    }

    private void selectedLandscapeFragment(int i) {
        setLandscapeTextUi(i, this.tvLandscapeTabAll, this.tvLandscapeTabPet, this.tvLandscapeTabDrink);
        this.landscapeCurrentPosition = i;
        FragmentTransaction fragmentTransactionBeginTransaction = getSupportFragmentManager().beginTransaction();
        if (i == 0) {
            fragmentTransactionBeginTransaction.show(this.lsFragmentAll);
            if (this.lsFragmentPet.isAdded()) {
                fragmentTransactionBeginTransaction.hide(this.lsFragmentPet);
            }
            if (this.lsFragmentDrink.isAdded()) {
                fragmentTransactionBeginTransaction.hide(this.lsFragmentDrink);
            }
        } else if (i == 1) {
            fragmentTransactionBeginTransaction.hide(this.lsFragmentAll);
            if (this.lsFragmentDrink.isAdded()) {
                fragmentTransactionBeginTransaction.hide(this.lsFragmentDrink);
            }
            if (!this.lsFragmentPet.isAdded()) {
                this.lsFragmentPet.setArguments(getBundle(1));
                fragmentTransactionBeginTransaction.add(R.id.rl_list_content, this.lsFragmentPet);
            }
            fragmentTransactionBeginTransaction.show(this.lsFragmentPet);
        } else if (i == 2) {
            fragmentTransactionBeginTransaction.hide(this.lsFragmentAll);
            if (this.lsFragmentPet.isAdded()) {
                fragmentTransactionBeginTransaction.hide(this.lsFragmentPet);
            }
            if (!this.lsFragmentDrink.isAdded()) {
                this.lsFragmentDrink.setArguments(getBundle(2));
                fragmentTransactionBeginTransaction.add(R.id.rl_list_content, this.lsFragmentDrink);
            }
            fragmentTransactionBeginTransaction.show(this.lsFragmentDrink);
        }
        fragmentTransactionBeginTransaction.commit();
    }

    public void hideAllRedPoint() {
        this.mBinding.ivAllDot.setVisibility(8);
        this.mBinding.ivAllDotTop.setVisibility(8);
    }

    public void setTextUi(int i, TextView... textViewArr) {
        for (int i2 = 0; i2 < textViewArr.length; i2++) {
            TextView textView = textViewArr[i2];
            if (i2 == i) {
                textView.setTextColor(getResources().getColor(R.color.color_w7h_home));
                textView.setBackgroundResource(R.drawable.circle_light_blue_bg_12);
                textView.setTypeface(null, 1);
            } else {
                textView.setTextColor(getResources().getColor(R.color.t4_text_gray));
                textView.setBackgroundResource(R.drawable.circle_light_gray_bg_12);
                textView.setTypeface(null, 0);
            }
        }
    }

    private void setLandscapeTextUi(int i, TextView... textViewArr) {
        for (int i2 = 0; i2 < textViewArr.length; i2++) {
            TextView textView = textViewArr[i2];
            if (i2 == i) {
                textView.setTextColor(getResources().getColor(R.color.color_w7h_home));
                textView.setTypeface(null, 1);
            } else {
                textView.setTextColor(getResources().getColor(R.color.t4_text_gray));
                textView.setTypeface(null, 0);
            }
        }
    }

    public void setTopTextUi(int i, TextView... textViewArr) {
        for (int i2 = 0; i2 < textViewArr.length; i2++) {
            TextView textView = textViewArr[i2];
            if (i2 == i) {
                textView.setTextColor(getResources().getColor(R.color.color_w7h_home));
                textView.setTypeface(null, 1);
            } else {
                textView.setTextColor(getResources().getColor(R.color.t4_text_gray));
                textView.setTypeface(null, 0);
            }
        }
    }

    private void clickAllTab(boolean z) {
        if (DateUtil.isSameDay(this.calendarTime, this.w7hRecord.getActualTimeZone())) {
            if (this.mBinding.ivAllDot.getVisibility() == 0 || this.mBinding.ivAllDotTop.getVisibility() == 0) {
                int i = this.newDrankEventNum;
                if (i > 0 && this.newPetEventNum > 0) {
                    P p = this.mPresenter;
                    if (p != 0) {
                        ((W7hHomePresenter) p).getPetDrinkGraph(this.calendarTime, 0);
                    }
                } else if (i > 0) {
                    P p2 = this.mPresenter;
                    if (p2 != 0) {
                        ((W7hHomePresenter) p2).getPetDrinkGraph(this.calendarTime, 2);
                    }
                } else {
                    if (this.allFragment.isAdded()) {
                        this.allFragment.refreshEvent(this.calendarTime);
                    }
                    if (this.drankFragment.isAdded()) {
                        this.drankFragment.refreshEvent(this.calendarTime);
                    }
                    P p3 = this.mPresenter;
                    if (p3 != 0) {
                        ((W7hHomePresenter) p3).getEventTotal(this.calendarTime);
                    }
                }
                if (this.currentPosition != 0) {
                    if (!z && this.allFragment.isAdded() && this.mBinding.rlTopTab.getVisibility() != 0) {
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
            if (!z && this.allFragment.isAdded() && this.mBinding.rlTopTab.getVisibility() != 0) {
                this.allFragment.scrollToTop();
            }
            selectedFragment(0);
            return;
        }
        if (this.currentPosition == 0) {
            return;
        }
        if (!z && this.allFragment.isAdded() && this.mBinding.rlTopTab.getVisibility() != 0) {
            this.allFragment.scrollToTop();
        }
        selectedFragment(0);
    }

    private void clickPetTab(boolean z) {
        if (DateUtil.isSameDay(this.calendarTime, this.w7hRecord.getActualTimeZone())) {
            if (this.newPetEventNum > 0) {
                if (this.petFragment.isAdded()) {
                    this.petFragment.refreshEvent(this.calendarTime);
                }
                P p = this.mPresenter;
                if (p != 0) {
                    ((W7hHomePresenter) p).getEventTotal(this.calendarTime);
                }
                if (this.currentPosition != 1) {
                    if (!z && this.petFragment.isAdded() && this.mBinding.rlTopTab.getVisibility() != 0) {
                        this.petFragment.scrollToTop();
                    }
                    selectedFragment(1);
                    return;
                }
                return;
            }
            if (this.currentPosition == 1) {
                return;
            }
            if (!z && this.petFragment.isAdded() && this.mBinding.rlTopTab.getVisibility() != 0) {
                this.petFragment.scrollToTop();
            }
            selectedFragment(1);
            return;
        }
        if (this.currentPosition == 1) {
            return;
        }
        if (!z && this.petFragment.isAdded() && this.mBinding.rlTopTab.getVisibility() != 0) {
            this.petFragment.scrollToTop();
        }
        selectedFragment(1);
    }

    private void clickDrankTab(boolean z) {
        if (DateUtil.isSameDay(this.calendarTime, this.w7hRecord.getActualTimeZone())) {
            if (this.newDrankEventNum > 0) {
                if (this.drankFragment.isAdded()) {
                    this.drankFragment.refreshEvent(this.calendarTime);
                }
                P p = this.mPresenter;
                if (p != 0) {
                    ((W7hHomePresenter) p).getEventTotal(this.calendarTime);
                }
                if (this.currentPosition != 2) {
                    if (!z && this.drankFragment.isAdded() && this.mBinding.rlTopTab.getVisibility() != 0) {
                        this.drankFragment.scrollToTop();
                    }
                    selectedFragment(2);
                    return;
                }
                return;
            }
            if (this.currentPosition == 2) {
                return;
            }
            if (!z && this.drankFragment.isAdded() && this.mBinding.rlTopTab.getVisibility() != 0) {
                this.drankFragment.scrollToTop();
            }
            selectedFragment(2);
            return;
        }
        if (this.currentPosition == 2) {
            return;
        }
        if (!z && this.drankFragment.isAdded() && this.mBinding.rlTopTab.getVisibility() != 0) {
            this.drankFragment.scrollToTop();
        }
        selectedFragment(2);
    }

    private void clickLandscapeAllTab() {
        DateUtil.isSameDay(this.calendarTime, this.w7hRecord.getActualTimeZone());
        if (this.landscapeCurrentPosition == 0) {
            return;
        }
        selectedLandscapeFragment(0);
    }

    private void clickLandscapePetTab() {
        if (DateUtil.isSameDay(this.calendarTime, this.w7hRecord.getActualTimeZone()) && this.newPetEventNum > 0 && this.lsFragmentPet.isAdded()) {
            this.lsFragmentPet.refreshEvent(this.calendarTime);
        }
        if (this.landscapeCurrentPosition == 1) {
            return;
        }
        selectedLandscapeFragment(1);
    }

    private void clickLandscapeDrinkTab() {
        if (DateUtil.isSameDay(this.calendarTime, this.w7hRecord.getActualTimeZone()) && this.newDrankEventNum > 0 && this.lsFragmentPet.isAdded()) {
            this.lsFragmentPet.refreshEvent(this.calendarTime);
        }
        if (this.landscapeCurrentPosition == 2) {
            return;
        }
        selectedLandscapeFragment(2);
    }

    @Override // com.petkit.android.activities.petkitBleDevice.w7h.contract.W7hHomeContract.View
    public void updateEvent(int i) {
        if (DateUtil.isSameDay(this.calendarTime, this.w7hRecord.getActualTimeZone())) {
            int i2 = this.currentPosition;
            if (i2 == 0) {
                if (this.mBinding.rlTopTab.getVisibility() != 0) {
                    if (this.allFragment.isAdded()) {
                        this.allFragment.refreshEvent(this.calendarTime);
                    }
                } else {
                    this.mBinding.ivAllDot.setVisibility(0);
                    this.mBinding.ivAllDotTop.setVisibility(0);
                }
            } else if (i2 == 1) {
                this.mBinding.ivAllDot.setVisibility(0);
                this.mBinding.ivAllDotTop.setVisibility(0);
                if ((i == 33 || i == 34) && this.mBinding.rlTopTab.getVisibility() != 0 && this.petFragment.isAdded()) {
                    this.petFragment.refreshEvent(this.calendarTime);
                }
            } else if (i2 == 2) {
                this.mBinding.ivAllDot.setVisibility(0);
                this.mBinding.ivAllDotTop.setVisibility(0);
                if (i == 35 && this.mBinding.rlTopTab.getVisibility() != 0 && this.drankFragment.isAdded()) {
                    this.drankFragment.refreshEvent(this.calendarTime);
                }
            }
            if (i == 33 || i == 34) {
                this.newPetEventNum++;
            } else if (i == 35) {
                this.newDrankEventNum++;
            }
        }
        if (this.mBinding.rlTopTab.getVisibility() == 0) {
            switch (i) {
                case 33:
                    this.mBinding.tvTopPaoPao.setText(getResources().getString(R.string.T6_pet_event_coming));
                    showTopPaoPao();
                    break;
                case 34:
                    this.mBinding.tvTopPaoPao.setText(getResources().getString(R.string.T6_pet_detect_event_coming));
                    showTopPaoPao();
                    break;
                case 35:
                    this.mBinding.tvTopPaoPao.setText(getResources().getString(R.string.W7h_pet_have_a_drink_of_water, getString(R.string.Pet)));
                    showTopPaoPao();
                    break;
            }
        }
        int i3 = this.currentPosition;
        if (i3 == 0 || ((this.newPetEventNum > 0 && i3 == 1) || (this.newDrankEventNum > 0 && i3 == 2))) {
            ((W7hHomePresenter) this.mPresenter).getDeviceDetail(true);
        } else {
            ((W7hHomePresenter) this.mPresenter).getDeviceDetail(false);
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.w7h.contract.W7hHomeContract.View
    public void updateEventApi(W7hRecord w7hRecord) {
        ((W7hHomePresenter) this.mPresenter).getPetDrinkGraph(this.calendarTime, this.currentPosition);
    }

    @Override // com.petkit.android.activities.petkitBleDevice.w7h.contract.W7hHomeContract.View
    public void tempOpenVoice() {
        W7hLiveVideoView w7hLiveVideoView = this.w7hLiveVideoView;
        if (w7hLiveVideoView != null) {
            w7hLiveVideoView.setTempVoiceIcon(false);
        }
    }

    private void showTopPaoPao() {
        this.mBinding.tvTopPaoPao.setVisibility(0);
        Disposable disposable = this.paoPaoTimer;
        if (disposable != null) {
            disposable.dispose();
            this.paoPaoTimer = null;
        }
        this.paoPaoTimer = Observable.timer(3L, TimeUnit.SECONDS).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$$ExternalSyntheticLambda33
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.lambda$showTopPaoPao$31((Long) obj);
            }
        });
    }

    public /* synthetic */ void lambda$showTopPaoPao$31(Long l) throws Exception {
        MyHandler myHandler = this.handler;
        if (myHandler != null) {
            myHandler.sendEmptyMessage(10);
        }
    }

    private void updateEventList(int i) {
        if (i == 1) {
            if (this.allFragment.isAdded()) {
                this.allFragment.selectDate(this.calendarTime, 0);
            }
            if (this.petFragment.isAdded()) {
                this.petFragment.selectDate(this.calendarTime, 1);
                return;
            }
            return;
        }
        if (i == 2) {
            if (this.allFragment.isAdded()) {
                this.allFragment.selectDate(this.calendarTime, 0);
            }
            if (this.drankFragment.isAdded()) {
                this.drankFragment.selectDate(this.calendarTime, 2);
                return;
            }
            return;
        }
        if (this.allFragment.isAdded()) {
            this.allFragment.selectDate(this.calendarTime, 0);
        }
        if (this.petFragment.isAdded()) {
            this.petFragment.selectDate(this.calendarTime, 1);
        }
        if (this.drankFragment.isAdded()) {
            this.drankFragment.selectDate(this.calendarTime, 2);
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.w7h.contract.W7hHomeContract.View
    public void showEventTotal(W7hEventTotalVO w7hEventTotalVO) {
        if (w7hEventTotalVO == null) {
            return;
        }
        this.mBinding.tvTabAll.setText(getListNumStr(getResources().getString(R.string.Whole), w7hEventTotalVO.getTotal()));
        this.mBinding.tvTabPet.setText(getListNumStr(getResources().getString(R.string.Pet), w7hEventTotalVO.getPet()));
        this.mBinding.tvTabDrank.setText(getListNumStr(getResources().getString(R.string.Drink), w7hEventTotalVO.getDrink()));
        this.mBinding.tvTabAllTop.setText(getListNumStr(getResources().getString(R.string.Whole), w7hEventTotalVO.getTotal()));
        this.mBinding.tvTabPetTop.setText(getListNumStr(getResources().getString(R.string.Pet), w7hEventTotalVO.getPet()));
        this.mBinding.tvTabDrankTop.setText(getListNumStr(getResources().getString(R.string.Drink), w7hEventTotalVO.getDrink()));
        int i = this.currentPosition;
        ActivityW7hHomeBinding activityW7hHomeBinding = this.mBinding;
        setTextUi(i, activityW7hHomeBinding.tvTabAll, activityW7hHomeBinding.tvTabPet, activityW7hHomeBinding.tvTabDrank);
        int i2 = this.currentPosition;
        ActivityW7hHomeBinding activityW7hHomeBinding2 = this.mBinding;
        setTopTextUi(i2, activityW7hHomeBinding2.tvTabAllTop, activityW7hHomeBinding2.tvTabPetTop, activityW7hHomeBinding2.tvTabDrankTop);
    }

    @Override // com.petkit.android.activities.petkitBleDevice.w7h.contract.W7hHomeContract.View
    public void showLandscapeEventTotal(W7hEventTotalVO w7hEventTotalVO) {
        if (w7hEventTotalVO == null) {
            return;
        }
        this.tvLandscapeTabPet.setText(getListNumStr(getResources().getString(R.string.Pet), w7hEventTotalVO.getPet()));
        this.tvLandscapeTabDrink.setText(getListNumStr(getResources().getString(R.string.Drink), w7hEventTotalVO.getDrink()));
        this.tvLandscapeTabAll.setText(getListNumStr(getResources().getString(R.string.Whole), w7hEventTotalVO.getPet() + w7hEventTotalVO.getDrink()));
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

    @Override // com.petkit.android.activities.petkitBleDevice.w7h.contract.W7hHomeContract.View
    public void showCalendarDialog(String str, List<CountBean> list) {
        DatePickerWindow datePickerWindow = this.datePickerWindow;
        if (datePickerWindow != null && datePickerWindow.isShowing()) {
            this.datePickerWindow.getDatePicker().refreshT6Data(this.w7hRecord.getCreatedAt(), this.deviceType, T6Utils.getStartAndEndDateStr2(this.calendarTime)[0], this.deviceId, list);
        } else {
            DatePickerWindow datePickerWindow2 = new DatePickerWindow(this, this.w7hRecord.getCreatedAt(), str, 29, this.deviceId, new BasePetkitDeviceDatePickerView.OnCalendarChangeListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$$ExternalSyntheticLambda23
                @Override // com.petkit.android.activities.petkitBleDevice.widget.datepicker.BasePetkitDeviceDatePickerView.OnCalendarChangeListener
                public final void pageChange(int i) {
                    this.f$0.lambda$showCalendarDialog$32(i);
                }
            }, true, list, new BasePetkitDeviceDatePickerView.OnCalendarSelectListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$$ExternalSyntheticLambda24
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

    public /* synthetic */ void lambda$showCalendarDialog$32(int i) {
        this.monthOffset = i;
        ((W7hHomePresenter) this.mPresenter).getDateByMonth(i);
    }

    @Override // com.petkit.android.activities.petkitBleDevice.w7h.contract.W7hHomeContract.View
    public void updateCalendarView(String str, List<CountBean> list) {
        if (this.datePickerView != null) {
            this.datePickerView.refreshT6Data(this.w7hRecord.getCreatedAt(), this.deviceType, T7Utils.getStartAndEndDateStr2(this.landscapeCalendarTime)[0], this.deviceId, list);
        }
    }

    public void dateSelected(String str) {
        long millisecondByDateString = DateUtil.getMillisecondByDateString(str);
        this.calendarTime = W7hDataUtils.getCurrentZoneTime(this.w7hRecord, this.deviceId, millisecondByDateString, this.deviceType);
        PetkitLog.d("W7hTime", millisecondByDateString + "  c  " + this.calendarTime);
        ((W7hHomePresenter) this.mPresenter).getPetDrinkGraph(this.calendarTime, 0);
        updateEventTime();
        ((W7hHomePresenter) this.mPresenter).getEventTotal(this.calendarTime);
        updateEventList(0);
        DatePickerWindow datePickerWindow = this.datePickerWindow;
        if (datePickerWindow != null) {
            datePickerWindow.dismiss();
        }
    }

    public void landscapeDateSelected(String str) {
        String strLong2str;
        long currentZoneTime = W7hUtils.getInstance().getCurrentZoneTime(this.w7hRecord, this.deviceId, DateUtil.getMillisecondByDateString(str));
        this.landscapeCalendarTime = currentZoneTime;
        if (DateUtil.isSameDay(currentZoneTime, this.w7hRecord.getActualTimeZone())) {
            strLong2str = getResources().getString(R.string.Today_Events_records);
        } else {
            strLong2str = DateUtil.long2str(this.landscapeCalendarTime, "yyyy-MM-dd");
        }
        this.tvLandscapeTodayEvent.setText(strLong2str);
        this.groupDate.setVisibility(8);
        this.groupList.setVisibility(0);
        this.datePickerView.setVisibility(8);
        if (this.lsFragmentAll.isAdded()) {
            this.lsFragmentAll.selectDate(this.landscapeCalendarTime, 0);
        }
        if (this.lsFragmentPet.isAdded()) {
            this.lsFragmentPet.selectDate(this.landscapeCalendarTime, 1);
        }
        if (this.lsFragmentDrink.isAdded()) {
            this.lsFragmentDrink.selectDate(this.landscapeCalendarTime, 2);
        }
        ((W7hHomePresenter) this.mPresenter).getLandscapeEventTotal(this.landscapeCalendarTime);
    }

    private void updateEventTime() {
        if (DateUtil.isSameDay(this.calendarTime, this.w7hRecord.getActualTimeZone())) {
            this.mBinding.tvTodayEvent.setText(getResources().getString(R.string.Today_Events_records));
            this.mBinding.tvTodayEventChart.setText(getResources().getString(R.string.Today_drink));
            this.title = getResources().getString(R.string.Today_Events_records);
            if (this.mBinding.rlTopTab.getVisibility() == 0) {
                this.toolbarTitle.setText(this.title);
            } else {
                this.toolbarTitle.setText(this.deviceName);
            }
            hideAllRedPoint();
            return;
        }
        this.mBinding.tvTodayEvent.setText(DateUtil.long2str(this.calendarTime, "yyyy-MM-dd"));
        this.mBinding.tvTodayEventChart.setText(DateUtil.long2str(this.calendarTime, "yyyy-MM-dd"));
        this.title = DateUtil.long2str(this.calendarTime, "yyyy-MM-dd");
        if (this.mBinding.rlTopTab.getVisibility() == 0) {
            this.toolbarTitle.setText(this.title);
        } else {
            this.toolbarTitle.setText(this.deviceName);
        }
        hideAllRedPoint();
    }

    private void showSelectPetsDialog() {
        try {
            this.petList = FamilyUtils.getInstance().getPetListByFamilyId(this, FamilyUtils.getInstance().getFamilyInforThroughDevice(this, this.deviceId, 29).getGroupId());
        } catch (Exception e) {
            this.petList = new ArrayList();
            LogcatStorageHelper.addLog("W7h getDevicePet error :" + e.getMessage());
        }
        PetFilterWindow petFilterWindow = this.petFilterWindow;
        if (petFilterWindow != null) {
            petFilterWindow.setPetList(this.petList);
            this.petFilterWindow.refreshColor();
            this.petFilterWindow.show(getWindow().getDecorView());
        } else {
            PetFilterWindow petFilterWindow2 = new PetFilterWindow(this, getResources().getString(R.string.Cancel), getResources().getString(R.string.OK), "", this.petList.size() == 0 || this.w7hRecord.getDeviceShared() != null ? "" : getResources().getString(R.string.Change_color), getResources().getString(R.string.Choose_pets), this.petList, new BaseBottomWindow.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity.37
                @Override // com.petkit.android.activities.universalWindow.BaseBottomWindow.OnClickListener
                public void onTitleBtn() {
                }

                @Override // com.petkit.android.activities.universalWindow.BaseBottomWindow.OnClickListener
                public void onTopLeftBtn() {
                }

                public AnonymousClass37() {
                }

                @Override // com.petkit.android.activities.universalWindow.BaseBottomWindow.OnClickListener
                public void onBtnLeft() {
                    W7hHomeActivity.this.petFilterWindow.resetSelectedState();
                    W7hHomeActivity.this.petFilterWindow.dismiss();
                    W7hHomeActivity w7hHomeActivity = W7hHomeActivity.this;
                    w7hHomeActivity.selectedPetIds = w7hHomeActivity.petFilterWindow.getSelectedPetIds();
                }

                @Override // com.petkit.android.activities.universalWindow.BaseBottomWindow.OnClickListener
                public void onBtnRight() {
                    W7hHomeActivity.this.petFilterWindow.updateSelectedState();
                    W7hHomeActivity.this.petFilterWindow.dismiss();
                    W7hHomeActivity w7hHomeActivity = W7hHomeActivity.this;
                    w7hHomeActivity.selectedPetIds = w7hHomeActivity.petFilterWindow.getSelectedPetIds();
                    W7hHomeActivity.this.showSelectPet();
                    W7hHomeActivity.this.refreshPetChart();
                }

                @Override // com.petkit.android.activities.universalWindow.BaseBottomWindow.OnClickListener
                public void onTopRightBtn() {
                    W7hHomeActivity w7hHomeActivity = W7hHomeActivity.this;
                    w7hHomeActivity.startActivity(PetColorSettingActivity.newIntent(w7hHomeActivity, w7hHomeActivity.petList));
                }
            });
            this.petFilterWindow = petFilterWindow2;
            petFilterWindow2.show(getWindow().getDecorView());
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$37 */
    public class AnonymousClass37 implements BaseBottomWindow.OnClickListener {
        @Override // com.petkit.android.activities.universalWindow.BaseBottomWindow.OnClickListener
        public void onTitleBtn() {
        }

        @Override // com.petkit.android.activities.universalWindow.BaseBottomWindow.OnClickListener
        public void onTopLeftBtn() {
        }

        public AnonymousClass37() {
        }

        @Override // com.petkit.android.activities.universalWindow.BaseBottomWindow.OnClickListener
        public void onBtnLeft() {
            W7hHomeActivity.this.petFilterWindow.resetSelectedState();
            W7hHomeActivity.this.petFilterWindow.dismiss();
            W7hHomeActivity w7hHomeActivity = W7hHomeActivity.this;
            w7hHomeActivity.selectedPetIds = w7hHomeActivity.petFilterWindow.getSelectedPetIds();
        }

        @Override // com.petkit.android.activities.universalWindow.BaseBottomWindow.OnClickListener
        public void onBtnRight() {
            W7hHomeActivity.this.petFilterWindow.updateSelectedState();
            W7hHomeActivity.this.petFilterWindow.dismiss();
            W7hHomeActivity w7hHomeActivity = W7hHomeActivity.this;
            w7hHomeActivity.selectedPetIds = w7hHomeActivity.petFilterWindow.getSelectedPetIds();
            W7hHomeActivity.this.showSelectPet();
            W7hHomeActivity.this.refreshPetChart();
        }

        @Override // com.petkit.android.activities.universalWindow.BaseBottomWindow.OnClickListener
        public void onTopRightBtn() {
            W7hHomeActivity w7hHomeActivity = W7hHomeActivity.this;
            w7hHomeActivity.startActivity(PetColorSettingActivity.newIntent(w7hHomeActivity, w7hHomeActivity.petList));
        }
    }

    public void showSelectPet() {
        if (this.selectedPetIds.contains(ColorUtils.ALL_PET)) {
            this.mBinding.scPoint.setVisibility(8);
            this.mBinding.scPoint2.setVisibility(8);
            this.mBinding.tvPetName.setVisibility(0);
            this.mBinding.tvPetName2.setVisibility(8);
            this.mBinding.tvPetName.setText(R.string.All_cats);
            return;
        }
        if (this.selectedPetIds.size() > 1) {
            this.mBinding.scPoint.setVisibility(0);
            this.mBinding.scPoint2.setVisibility(0);
            this.mBinding.tvPetName.setVisibility(0);
            this.mBinding.tvPetName2.setVisibility(0);
            String string = ColorUtils.UNKNOWN_PET.equals(this.selectedPetIds.get(0)) ? getString(R.string.Not_matched) : UserInforUtils.getPetNameById(this.selectedPetIds.get(0));
            String string2 = ColorUtils.UNKNOWN_PET.equals(this.selectedPetIds.get(1)) ? getString(R.string.Not_matched) : UserInforUtils.getPetNameById(this.selectedPetIds.get(1));
            this.mBinding.scPoint.setBackgroundResource(ColorUtils.getPetColorCircleResById(this.selectedPetIds.get(0), string));
            this.mBinding.scPoint2.setBackgroundResource(ColorUtils.getPetColorCircleResById(this.selectedPetIds.get(1), string2));
            this.mBinding.tvPetName.setText(string);
            this.mBinding.tvPetName2.setText(string2);
            return;
        }
        if (this.selectedPetIds.size() == 1) {
            this.mBinding.scPoint.setVisibility(0);
            this.mBinding.scPoint2.setVisibility(8);
            this.mBinding.tvPetName.setVisibility(0);
            this.mBinding.tvPetName2.setVisibility(8);
            String string3 = ColorUtils.UNKNOWN_PET.equals(this.selectedPetIds.get(0)) ? getString(R.string.Not_matched) : UserInforUtils.getPetNameById(this.selectedPetIds.get(0));
            this.mBinding.scPoint.setBackgroundResource(ColorUtils.getPetColorCircleResById(this.selectedPetIds.get(0), string3));
            this.mBinding.tvPetName.setText(string3);
        }
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

    private void setChartData() {
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
        this.chartViewPager.setAdapter(new PagerAdapter() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity.38
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
        this.chartViewPager.setDragListener(new MyChartViewPager.DragListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity.39
            public AnonymousClass39() {
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.MyChartViewPager.DragListener
            public void dragLeft() {
                W7hHomeActivity.this.dragChart(-1);
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.MyChartViewPager.DragListener
            public void dragRight() {
                if (DateUtil.isSameDay(W7hHomeActivity.this.calendarTime, W7hHomeActivity.this.w7hRecord.getActualTimeZone())) {
                    return;
                }
                W7hHomeActivity.this.dragChart(1);
            }
        });
        dragChart(0);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$38 */
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

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$39 */
    public class AnonymousClass39 implements MyChartViewPager.DragListener {
        public AnonymousClass39() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.MyChartViewPager.DragListener
        public void dragLeft() {
            W7hHomeActivity.this.dragChart(-1);
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.MyChartViewPager.DragListener
        public void dragRight() {
            if (DateUtil.isSameDay(W7hHomeActivity.this.calendarTime, W7hHomeActivity.this.w7hRecord.getActualTimeZone())) {
                return;
            }
            W7hHomeActivity.this.dragChart(1);
        }
    }

    public void dragChart(int i) {
        this.chartView.removeAllViews();
        this.calendarTime += ((long) i) * 86400000;
        this.mBinding.ivEventAnim.setVisibility(0);
        this.animUtil.startRotateAnim(this.mBinding.ivEventAnim);
        ((W7hHomePresenter) this.mPresenter).getPetDrinkGraph(this.calendarTime, 0);
        if (i != 0) {
            this.ivLeftArrow.setVisibility(8);
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.w7h.contract.W7hHomeContract.View
    public void chartResult(List<W7hStatisticResInfo> list, int i) {
        this.catChartList = list;
        this.mBinding.srl.finishRefresh();
        refreshPetChart();
        this.handler.postDelayed(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$$ExternalSyntheticLambda21
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$chartResult$33();
            }
        }, 300L);
        updateEventTime();
        if (this.isNotFirstLoad) {
            ((W7hHomePresenter) this.mPresenter).getEventTotal(this.calendarTime);
            updateEventList(i);
        } else {
            this.mBinding.llDay.setVisibility(8);
            this.isNotFirstLoad = true;
        }
    }

    public /* synthetic */ void lambda$chartResult$33() {
        this.mBinding.ivEventAnim.setVisibility(8);
        this.mBinding.ivEventAnim.clearAnimation();
    }

    public void refreshPetChart() {
        if (this.selectedPetIds.contains(ColorUtils.ALL_PET)) {
            updateStatisticList(this.catChartList);
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (W7hStatisticResInfo w7hStatisticResInfo : this.catChartList) {
            if (hasSelectPet(w7hStatisticResInfo.getPetId())) {
                arrayList.add(w7hStatisticResInfo);
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

    private void updateStatisticList(List<W7hStatisticResInfo> list) {
        if (list.size() > 0) {
            getChartData(this.chartView, list);
        } else {
            this.chartView.removeAllViews();
        }
        this.mBinding.tvTodayDrankAmount.setText(String.valueOf(list.size()));
    }

    @SuppressLint({"ClickableViewAccessibility"})
    private void getChartData(RelativeLayout relativeLayout, List<W7hStatisticResInfo> list) {
        String petName;
        W7hHomeActivity w7hHomeActivity = this;
        if (relativeLayout == null) {
            return;
        }
        relativeLayout.removeAllViews();
        int yUi = w7hHomeActivity.setYUi(w7hHomeActivity.getMaxTime(list));
        int measuredWidth = relativeLayout.getMeasuredWidth();
        int measuredHeight = relativeLayout.getMeasuredHeight() - AppUtils.dp2px(w7hHomeActivity, 18.0f);
        final ArrayList arrayList = new ArrayList();
        int i = 0;
        int i2 = 0;
        while (i2 < list.size()) {
            W7hStatisticResInfo w7hStatisticResInfo = list.get(i2);
            final RelativeLayout relativeLayout2 = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.chart_view_data, (ViewGroup) null);
            ImageView imageView = (ImageView) relativeLayout2.getChildAt(1);
            Pet petById = UserInforUtils.getPetById(w7hStatisticResInfo.getPetId());
            if (petById != null) {
                petName = petById.getName();
            } else {
                petName = w7hStatisticResInfo.getPetName();
            }
            imageView.setImageResource(ColorUtils.getPetWaterColorById(w7hStatisticResInfo.getPetId(), petName));
            RelativeLayout relativeLayout3 = (RelativeLayout) relativeLayout2.getChildAt(i);
            ((TextView) relativeLayout3.getChildAt(i)).setBackgroundColor(Color.parseColor(ColorUtils.getPetColorById(w7hStatisticResInfo.getPetId(), petName)));
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) relativeLayout3.getLayoutParams();
            layoutParams.height = (int) (((w7hStatisticResInfo.getDrinkTime() * 1.0f) / yUi) * measuredHeight);
            relativeLayout3.setLayoutParams(layoutParams);
            relativeLayout.addView(relativeLayout2);
            final RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) relativeLayout2.getLayoutParams();
            layoutParams2.leftMargin = Math.max(T6Utils.getEventTimeOffset(w7hStatisticResInfo.getTime(), measuredWidth - AppUtils.dp2px(w7hHomeActivity, 16.0f)), i);
            layoutParams2.addRule(12);
            relativeLayout2.setLayoutParams(layoutParams2);
            final W7hChartInfo w7hChartInfo = new W7hChartInfo();
            w7hChartInfo.setInfo(w7hStatisticResInfo);
            w7hChartInfo.setClickView(relativeLayout2);
            arrayList.add(w7hChartInfo);
            relativeLayout2.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$$ExternalSyntheticLambda28
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$getChartData$34(relativeLayout2, w7hChartInfo, arrayList, layoutParams2, view);
                }
            });
            final float[] fArr = new float[1];
            relativeLayout2.setOnTouchListener(new View.OnTouchListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$$ExternalSyntheticLambda29
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    return this.f$0.lambda$getChartData$35(fArr, relativeLayout2, w7hChartInfo, arrayList, layoutParams2, view, motionEvent);
                }
            });
            i2++;
            i = 0;
            w7hHomeActivity = this;
        }
    }

    public /* synthetic */ void lambda$getChartData$34(RelativeLayout relativeLayout, W7hChartInfo w7hChartInfo, List list, RelativeLayout.LayoutParams layoutParams, View view) {
        PetkitLog.e("HeightDebug", "getCorrectTopPosition() = " + getCorrectTopPosition());
        int top = (((relativeLayout.getTop() + this.rlChart.getTop()) + getCorrectTopPosition()) - Math.abs(this.appBarOffset)) + AppUtils.dp2px(this, (float) this.topMargin);
        this.sortEventList = getSortList(w7hChartInfo, list);
        showArrowPop(layoutParams, top, w7hChartInfo);
    }

    public /* synthetic */ boolean lambda$getChartData$35(float[] fArr, RelativeLayout relativeLayout, W7hChartInfo w7hChartInfo, List list, RelativeLayout.LayoutParams layoutParams, View view, MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 0) {
            fArr[0] = motionEvent.getRawY();
            PetkitLog.d("getChartData", "ACTION_DOWN : " + motionEvent.getRawY());
            view.getParent().requestDisallowInterceptTouchEvent(true);
            int top = (((relativeLayout.getTop() + this.rlChart.getTop()) + getCorrectTopPosition()) - Math.abs(this.appBarOffset)) + AppUtils.dp2px(this, (float) this.topMargin);
            this.sortEventList = getSortList(w7hChartInfo, list);
            showArrowPop(layoutParams, top, w7hChartInfo);
            return true;
        }
        if (action != 1) {
            if (action == 2) {
                float fAbs = Math.abs(motionEvent.getRawY() - fArr[0]);
                PetkitLog.d("getChartData", "ACTION_MOVE : " + motionEvent.getRawY());
                if (fAbs > ViewConfiguration.get(view.getContext()).getScaledTouchSlop() && this.mBinding.rlPop.getVisibility() == 0) {
                    this.mBinding.rlPop.setVisibility(8);
                }
            } else if (action != 3) {
                return false;
            }
        }
        return true;
    }

    private int getCorrectTopPosition() {
        int[] iArr = new int[2];
        this.mBinding.scrollview.getLocationInWindow(iArr);
        int[] iArr2 = new int[2];
        this.mBinding.llDay.getLocationInWindow(iArr2);
        return iArr2[1] - iArr[1];
    }

    private List<W7hChartInfo> getSortList(W7hChartInfo w7hChartInfo, List<W7hChartInfo> list) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(w7hChartInfo);
        this.popCount = 0;
        for (int size = list.size() - 1; size >= 0; size--) {
            if (list.get(size).getInfo().getTime() < w7hChartInfo.getInfo().getTime()) {
                arrayList.add(0, list.get(size));
                this.popCount++;
            } else if (list.get(size).getInfo().getTime() > w7hChartInfo.getInfo().getTime()) {
                if (this.popCount < arrayList.size() - 1) {
                    arrayList.add(this.popCount + 1, list.get(size));
                } else {
                    arrayList.add(list.get(size));
                }
            }
        }
        return arrayList;
    }

    private void showArrowPop(RelativeLayout.LayoutParams layoutParams, int i, W7hChartInfo w7hChartInfo) {
        setPopEventInfo(w7hChartInfo.getInfo());
        startPopAnim();
        this.mBinding.rlPopContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity.40
            final /* synthetic */ RelativeLayout.LayoutParams val$p;
            final /* synthetic */ int val$top;

            public AnonymousClass40(RelativeLayout.LayoutParams layoutParams2, int i2) {
                layoutParams = layoutParams2;
                i = i2;
            }

            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                W7hHomeActivity.this.mBinding.rlPopContent.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                W7hHomeActivity.this.mBinding.rlPopContent.measure(0, 0);
                W7hHomeActivity w7hHomeActivity = W7hHomeActivity.this;
                w7hHomeActivity.rlPopWidth = w7hHomeActivity.mBinding.rlPopContent.getMeasuredWidth();
                W7hHomeActivity w7hHomeActivity2 = W7hHomeActivity.this;
                w7hHomeActivity2.rlPopHeight = w7hHomeActivity2.mBinding.rlPopContent.getMeasuredHeight();
                RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) W7hHomeActivity.this.mBinding.ivArrow.getLayoutParams();
                layoutParams2.leftMargin = layoutParams.leftMargin + AppUtils.dp2px(W7hHomeActivity.this, 68.0f);
                layoutParams2.topMargin = (i - AppUtils.dp2px(W7hHomeActivity.this, 2.0f)) + 1;
                W7hHomeActivity.this.mBinding.ivArrow.setLayoutParams(layoutParams2);
                RelativeLayout.LayoutParams layoutParams3 = (RelativeLayout.LayoutParams) W7hHomeActivity.this.mBinding.rlPopContent.getLayoutParams();
                int widthPixels = layoutParams.leftMargin;
                if (AppUtils.dp2px(W7hHomeActivity.this, 68.0f) + widthPixels >= W7hHomeActivity.this.rlPopWidth / 2) {
                    if (W7hHomeActivity.this.rlPopWidth + widthPixels > CommonUtil.getWidthPixels(W7hHomeActivity.this) - AppUtils.dp2px(W7hHomeActivity.this, 18.0f)) {
                        widthPixels = (CommonUtil.getWidthPixels(W7hHomeActivity.this) - AppUtils.dp2px(W7hHomeActivity.this, 18.0f)) - W7hHomeActivity.this.rlPopWidth;
                    }
                } else {
                    widthPixels = AppUtils.dp2px(W7hHomeActivity.this, 18.0f);
                }
                layoutParams3.leftMargin = widthPixels;
                layoutParams3.topMargin = (i - W7hHomeActivity.this.rlPopHeight) + AppUtils.dp2px(W7hHomeActivity.this, 6.0f);
                W7hHomeActivity.this.mBinding.rlPopContent.setLayoutParams(layoutParams3);
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$40 */
    public class AnonymousClass40 implements ViewTreeObserver.OnGlobalLayoutListener {
        final /* synthetic */ RelativeLayout.LayoutParams val$p;
        final /* synthetic */ int val$top;

        public AnonymousClass40(RelativeLayout.LayoutParams layoutParams2, int i2) {
            layoutParams = layoutParams2;
            i = i2;
        }

        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public void onGlobalLayout() {
            W7hHomeActivity.this.mBinding.rlPopContent.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            W7hHomeActivity.this.mBinding.rlPopContent.measure(0, 0);
            W7hHomeActivity w7hHomeActivity = W7hHomeActivity.this;
            w7hHomeActivity.rlPopWidth = w7hHomeActivity.mBinding.rlPopContent.getMeasuredWidth();
            W7hHomeActivity w7hHomeActivity2 = W7hHomeActivity.this;
            w7hHomeActivity2.rlPopHeight = w7hHomeActivity2.mBinding.rlPopContent.getMeasuredHeight();
            RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) W7hHomeActivity.this.mBinding.ivArrow.getLayoutParams();
            layoutParams2.leftMargin = layoutParams.leftMargin + AppUtils.dp2px(W7hHomeActivity.this, 68.0f);
            layoutParams2.topMargin = (i - AppUtils.dp2px(W7hHomeActivity.this, 2.0f)) + 1;
            W7hHomeActivity.this.mBinding.ivArrow.setLayoutParams(layoutParams2);
            RelativeLayout.LayoutParams layoutParams3 = (RelativeLayout.LayoutParams) W7hHomeActivity.this.mBinding.rlPopContent.getLayoutParams();
            int widthPixels = layoutParams.leftMargin;
            if (AppUtils.dp2px(W7hHomeActivity.this, 68.0f) + widthPixels >= W7hHomeActivity.this.rlPopWidth / 2) {
                if (W7hHomeActivity.this.rlPopWidth + widthPixels > CommonUtil.getWidthPixels(W7hHomeActivity.this) - AppUtils.dp2px(W7hHomeActivity.this, 18.0f)) {
                    widthPixels = (CommonUtil.getWidthPixels(W7hHomeActivity.this) - AppUtils.dp2px(W7hHomeActivity.this, 18.0f)) - W7hHomeActivity.this.rlPopWidth;
                }
            } else {
                widthPixels = AppUtils.dp2px(W7hHomeActivity.this, 18.0f);
            }
            layoutParams3.leftMargin = widthPixels;
            layoutParams3.topMargin = (i - W7hHomeActivity.this.rlPopHeight) + AppUtils.dp2px(W7hHomeActivity.this, 6.0f);
            W7hHomeActivity.this.mBinding.rlPopContent.setLayoutParams(layoutParams3);
        }
    }

    private void setPopEventInfo(final W7hStatisticResInfo w7hStatisticResInfo) {
        String name;
        this.mBinding.tvPopPetName.setText(getPetName(w7hStatisticResInfo.getPetId()) + "：" + getEventDurationStr(w7hStatisticResInfo.getDrinkTime()));
        long time = w7hStatisticResInfo.getContent() != null ? w7hStatisticResInfo.getTime() : 0L;
        TextView textView = this.mBinding.tvPopTime;
        TimeUtils timeUtils = TimeUtils.getInstance();
        W7hRecord w7hRecord = this.w7hRecord;
        textView.setText(timeUtils.timeStampToTimeStringWithUnit(this, time, w7hRecord == null ? null : w7hRecord.getActualTimeZone()));
        Pet petById = UserInforUtils.getPetById(w7hStatisticResInfo.getPetId());
        if (petById == null) {
            name = "";
        } else {
            name = petById.getName();
        }
        this.mBinding.sdPet.setBgColor(getRecColor(w7hStatisticResInfo.getPetId(), name));
        if (this.popCount == 0) {
            this.mBinding.ivLastEvent.setImageResource(R.drawable.icon_t6_last_event2);
        } else {
            this.mBinding.ivLastEvent.setImageResource(R.drawable.icon_t6_last_event);
        }
        if (this.popCount < this.sortEventList.size() - 1) {
            this.mBinding.ivNextEvent.setImageResource(R.drawable.icon_t6_next_event);
        } else {
            this.mBinding.ivNextEvent.setImageResource(R.drawable.icon_t6_next_event2);
        }
        if (System.currentTimeMillis() / 1000 > w7hStatisticResInfo.getExpire()) {
            this.mBinding.rlPopImage.setVisibility(8);
            return;
        }
        if (w7hStatisticResInfo.getPreview().isEmpty()) {
            this.mBinding.rlPopImage.setVisibility(8);
            return;
        }
        this.mBinding.rlPopImage.setVisibility(0);
        this.mBinding.rlPopImage.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$$ExternalSyntheticLambda25
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$setPopEventInfo$36(w7hStatisticResInfo, view);
            }
        });
        if (isExpireService(w7hStatisticResInfo.getTimestamp())) {
            this.mBinding.ivChartPlay.setVisibility(0);
        } else {
            this.mBinding.ivChartPlay.setVisibility(8);
        }
        new ImageCacheSimple(CommonUtils.getAppContext()).getImageCachePath(CommonUtil.httpToHttps(w7hStatisticResInfo.getPreview()), new ImageCacheSimple.IGetImageCacheListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$$ExternalSyntheticLambda26
            @Override // com.jess.arms.widget.imageloader.ImageCacheSimple.IGetImageCacheListener
            public final void onCachePath(String str) {
                this.f$0.lambda$setPopEventInfo$37(w7hStatisticResInfo, str);
            }
        });
    }

    public /* synthetic */ void lambda$setPopEventInfo$36(W7hStatisticResInfo w7hStatisticResInfo, View view) {
        jumpToPlayerDetail(w7hStatisticResInfo);
    }

    public /* synthetic */ void lambda$setPopEventInfo$37(W7hStatisticResInfo w7hStatisticResInfo, String str) {
        if (str != null) {
            try {
                File fileDecryptImageFile = ImageUtils.decryptImageFile(new File(str), w7hStatisticResInfo.getAesKey());
                if (fileDecryptImageFile != null) {
                    this.mBinding.ivPopImage.setImageBitmap(CommonUtil.bimapSquareRound(60, BitmapFactory.decodeFile(fileDecryptImageFile.getAbsolutePath())));
                } else {
                    this.mBinding.ivPopImage.setImageResource(R.drawable.default_image);
                }
                return;
            } catch (Exception unused) {
                this.mBinding.ivPopImage.setImageResource(R.drawable.default_image);
                return;
            }
        }
        this.mBinding.ivPopImage.setImageResource(R.drawable.default_image);
    }

    private void jumpToPlayerDetail(W7hStatisticResInfo w7hStatisticResInfo) {
        W7hEventInfo w7hEventInfo = new W7hEventInfo();
        w7hEventInfo.setPetId(w7hStatisticResInfo.getPetId());
        if (w7hStatisticResInfo.getContent() != null) {
            w7hEventInfo.setMark(w7hStatisticResInfo.getContent().getMark());
        } else {
            w7hEventInfo.setMark(w7hStatisticResInfo.getMark());
            w7hStatisticResInfo.setContent(new W7hContentInfo());
        }
        w7hEventInfo.setContent(w7hStatisticResInfo.getContent());
        w7hEventInfo.setPreview(w7hStatisticResInfo.getPreview());
        w7hEventInfo.setMediaApi(w7hStatisticResInfo.getMediaApi());
        w7hEventInfo.setMedia(w7hStatisticResInfo.getMedia());
        w7hEventInfo.setUpload(w7hStatisticResInfo.getUpload());
        w7hEventInfo.setAesKey(w7hStatisticResInfo.getAesKey());
        w7hEventInfo.setDuration(w7hStatisticResInfo.getDuration());
        w7hEventInfo.setExpire(w7hStatisticResInfo.getExpire());
        w7hEventInfo.setEventId(w7hStatisticResInfo.getEventId());
        w7hEventInfo.setIsNeedUploadVideo(w7hStatisticResInfo.getIsNeedUploadVideo());
        w7hEventInfo.setStorageSpace(w7hStatisticResInfo.getStorageSpace());
        w7hEventInfo.setTimestamp(w7hStatisticResInfo.getTime());
        w7hEventInfo.setEventType(6);
        ArrayList arrayList = new ArrayList();
        arrayList.add(w7hEventInfo);
        launchActivity(W7hRecordVideoPlayActivity.newIntent(this, this.deviceId, this.deviceType, -1, arrayList, w7hEventInfo, ""));
    }

    private boolean isExpireService(long j) {
        boolean z = false;
        boolean z2 = this.w7hRecord.getCloudProduct() == null || (this.w7hRecord.getCloudProduct().getWorkIndate() != null && j > Long.parseLong(this.w7hRecord.getCloudProduct().getWorkIndate()));
        if (this.w7hRecord.getServiceStatus() == 0 || (this.w7hRecord.getServiceStatus() == 2 && z2)) {
            z = true;
        }
        return !z;
    }

    private int getWaterRes(String str, String str2) {
        return ColorUtils.getPetWaterColorById(str, str2);
    }

    private String getRecColor(String str, String str2) {
        return ColorUtils.getPetColorById(str, str2);
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

    private String getPetName(String str) {
        Pet petById = UserInforUtils.getPetById(str);
        String name = petById != null ? petById.getName() : null;
        return (name == null || name.equals("")) ? getResources().getString(R.string.Pet) : name;
    }

    private void startPopAnim() {
        if (this.mBinding.rlPop.getVisibility() == 0) {
            this.mBinding.rlPopContent.requestLayout();
            return;
        }
        this.mBinding.rlPop.setVisibility(0);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(300L);
        alphaAnimation.setInterpolator(new AccelerateInterpolator());
        this.mBinding.rlPop.setAnimation(alphaAnimation);
        alphaAnimation.start();
    }

    private int getMaxTime(List<W7hStatisticResInfo> list) {
        int drinkTime = 0;
        if (list.size() < 1) {
            return 0;
        }
        for (W7hStatisticResInfo w7hStatisticResInfo : list) {
            if (w7hStatisticResInfo.getDrinkTime() > drinkTime) {
                drinkTime = w7hStatisticResInfo.getDrinkTime();
            }
        }
        return drinkTime;
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

    @Subscriber
    public void deleteEvent(DeleteEvent deleteEvent) {
        if (deleteEvent == null || deleteEvent.getEventId().isEmpty()) {
            return;
        }
        ((W7hHomePresenter) this.mPresenter).getEventTotal(this.calendarTime);
        ((W7hHomePresenter) this.mPresenter).getLandscapeEventTotal(this.landscapeCalendarTime);
        if (this.allFragment.isAdded()) {
            this.allFragment.refreshAdapter(deleteEvent.getEventId(), deleteEvent.getEventType());
        }
        if (this.petFragment.isAdded()) {
            this.petFragment.refreshAdapter(deleteEvent.getEventId(), deleteEvent.getEventType());
        }
        if (this.drankFragment.isAdded()) {
            this.drankFragment.refreshAdapter(deleteEvent.getEventId(), deleteEvent.getEventType());
        }
        if (this.isInLandscape) {
            hideRightList();
            backLive();
        }
        if (this.lsFragmentAll.isAdded()) {
            this.lsFragmentAll.selectDate(this.landscapeCalendarTime, 0);
        }
        if (this.lsFragmentPet.isAdded()) {
            this.lsFragmentPet.selectDate(this.landscapeCalendarTime, 1);
        }
        if (this.lsFragmentDrink.isAdded()) {
            this.lsFragmentDrink.selectDate(this.landscapeCalendarTime, 2);
        }
    }

    public boolean safeCallFragment(Fragment fragment) {
        if (fragment == null) {
            PetkitLog.e(this.TAG, "Fragment is null");
            return false;
        }
        if (fragment.isAdded()) {
            return true;
        }
        PetkitLog.e(this.TAG, "Fragment is not added to Activity");
        return false;
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0) {
            int i = this.currentPosition;
            if (i == 0) {
                this.allFragment.hideCatFacePop();
            } else if (i == 1) {
                this.petFragment.hideCatFacePop();
            } else if (i == 2) {
                this.drankFragment.hideCatFacePop();
            }
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    @Subscriber
    public void changePet(UpdatePetEvent updatePetEvent) {
        if (updatePetEvent == null) {
            return;
        }
        updateEventApi(null);
        if (this.allFragment.isAdded()) {
            this.allFragment.updateOnePet(updatePetEvent.getOldPetId(), updatePetEvent.getPetId(), updatePetEvent.getEventId());
        }
        if (this.drankFragment.isAdded()) {
            this.drankFragment.updateOnePet(updatePetEvent.getOldPetId(), updatePetEvent.getPetId(), updatePetEvent.getEventId());
        }
        if (this.petFragment.isAdded() && updatePetEvent.isAppear()) {
            this.petFragment.updateOnePet(updatePetEvent.getOldPetId(), updatePetEvent.getPetId(), updatePetEvent.getEventId());
        }
        if (this.lsFragmentAll.isAdded()) {
            this.lsFragmentAll.updateOnePet(updatePetEvent.getOldPetId(), updatePetEvent.getPetId(), updatePetEvent.getEventId());
        }
        if (this.lsFragmentDrink.isAdded()) {
            this.lsFragmentDrink.updateOnePet(updatePetEvent.getOldPetId(), updatePetEvent.getPetId(), updatePetEvent.getEventId());
        }
        if (this.lsFragmentPet.isAdded() && updatePetEvent.isAppear()) {
            this.lsFragmentPet.updateOnePet(updatePetEvent.getOldPetId(), updatePetEvent.getPetId(), updatePetEvent.getEventId());
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$41 */
    public class AnonymousClass41 implements Consumer<Long> {
        public AnonymousClass41() {
        }

        @Override // io.reactivex.functions.Consumer
        public void accept(Long l) {
            if (W7hHomeActivity.this.bleDeviceHomeOfflineWarnWindow == null || !W7hHomeActivity.this.bleDeviceHomeOfflineWarnWindow.isShowing()) {
                W7hHomeActivity w7hHomeActivity = W7hHomeActivity.this;
                W7hHomeActivity w7hHomeActivity2 = W7hHomeActivity.this;
                w7hHomeActivity.bleDeviceHomeOfflineWarnWindow = new BleDeviceHomeOfflineWarnWindow(w7hHomeActivity2, 29, w7hHomeActivity2.getResources().getString(R.string.K2_offline_title), R.drawable.w7h_remind_offline_icon, W7hHomeActivity.this.offlineClickListener);
                W7hHomeActivity.this.bleDeviceHomeOfflineWarnWindow.showAtLocation(W7hHomeActivity.this.getWindow().getDecorView(), 17, 0, 0);
            }
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.w7h.contract.W7hHomeContract.View
    public void showOfflineWindow() {
        this.disposable = Observable.timer(500L, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity.41
            public AnonymousClass41() {
            }

            @Override // io.reactivex.functions.Consumer
            public void accept(Long l) {
                if (W7hHomeActivity.this.bleDeviceHomeOfflineWarnWindow == null || !W7hHomeActivity.this.bleDeviceHomeOfflineWarnWindow.isShowing()) {
                    W7hHomeActivity w7hHomeActivity = W7hHomeActivity.this;
                    W7hHomeActivity w7hHomeActivity2 = W7hHomeActivity.this;
                    w7hHomeActivity.bleDeviceHomeOfflineWarnWindow = new BleDeviceHomeOfflineWarnWindow(w7hHomeActivity2, 29, w7hHomeActivity2.getResources().getString(R.string.K2_offline_title), R.drawable.w7h_remind_offline_icon, W7hHomeActivity.this.offlineClickListener);
                    W7hHomeActivity.this.bleDeviceHomeOfflineWarnWindow.showAtLocation(W7hHomeActivity.this.getWindow().getDecorView(), 17, 0, 0);
                }
            }
        });
    }

    @Override // com.petkit.android.activities.petkitBleDevice.w7h.contract.W7hHomeContract.View
    public void showForceUpgradeWindow() {
        this.otaDisposable = Observable.timer(500L, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$$ExternalSyntheticLambda34
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.lambda$showForceUpgradeWindow$38((Long) obj);
            }
        });
    }

    public /* synthetic */ void lambda$showForceUpgradeWindow$38(Long l) throws Exception {
        if (this.w7hRecord.getDeviceShared() != null) {
            OtaPromptWindow otaPromptWindow = this.otaPromptWindow;
            if (otaPromptWindow == null || !otaPromptWindow.isShowing()) {
                OtaPromptWindow otaPromptWindow2 = new OtaPromptWindow(getActivity(), getResources().getString(R.string.Ota_prompt_share), getResources().getString(R.string.I_know), new OtaPromptWindow.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity.42
                    public AnonymousClass42() {
                    }

                    @Override // com.petkit.android.activities.petkitBleDevice.widget.OtaPromptWindow.OnClickListener
                    public void onCancel() {
                        W7hHomeActivity.this.killMyself();
                    }

                    @Override // com.petkit.android.activities.petkitBleDevice.widget.OtaPromptWindow.OnClickListener
                    public void onConfirm() {
                        W7hHomeActivity.this.killMyself();
                    }

                    @Override // com.petkit.android.activities.petkitBleDevice.widget.OtaPromptWindow.OnClickListener
                    public void onBack() {
                        W7hHomeActivity.this.killMyself();
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
            OtaPromptWindow otaPromptWindow4 = new OtaPromptWindow(getActivity(), getResources().getString(R.string.Ota_prompt), getResources().getString(R.string.Update_right_now), getResources().getString(R.string.Close), new OtaPromptWindow.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity.43
                public AnonymousClass43() {
                }

                @Override // com.petkit.android.activities.petkitBleDevice.widget.OtaPromptWindow.OnClickListener
                public void onCancel() {
                    W7hHomeActivity.this.killMyself();
                }

                @Override // com.petkit.android.activities.petkitBleDevice.widget.OtaPromptWindow.OnClickListener
                public void onConfirm() {
                    W7hHomeActivity w7hHomeActivity = W7hHomeActivity.this;
                    w7hHomeActivity.launchActivity(W7hOtaSettingActivity.newIntent(w7hHomeActivity.getActivity(), W7hHomeActivity.this.deviceId, W7hHomeActivity.this.deviceType));
                    W7hHomeActivity.this.killMyself();
                }

                @Override // com.petkit.android.activities.petkitBleDevice.widget.OtaPromptWindow.OnClickListener
                public void onBack() {
                    W7hHomeActivity.this.killMyself();
                }
            }, true);
            this.otaPromptWindow = otaPromptWindow4;
            otaPromptWindow4.setTouchOutsideAble(false);
            this.otaPromptWindow.setCheckboxVisibility(8);
            this.otaPromptWindow.show(getWindow().getDecorView());
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$42 */
    public class AnonymousClass42 implements OtaPromptWindow.OnClickListener {
        public AnonymousClass42() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.widget.OtaPromptWindow.OnClickListener
        public void onCancel() {
            W7hHomeActivity.this.killMyself();
        }

        @Override // com.petkit.android.activities.petkitBleDevice.widget.OtaPromptWindow.OnClickListener
        public void onConfirm() {
            W7hHomeActivity.this.killMyself();
        }

        @Override // com.petkit.android.activities.petkitBleDevice.widget.OtaPromptWindow.OnClickListener
        public void onBack() {
            W7hHomeActivity.this.killMyself();
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$43 */
    public class AnonymousClass43 implements OtaPromptWindow.OnClickListener {
        public AnonymousClass43() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.widget.OtaPromptWindow.OnClickListener
        public void onCancel() {
            W7hHomeActivity.this.killMyself();
        }

        @Override // com.petkit.android.activities.petkitBleDevice.widget.OtaPromptWindow.OnClickListener
        public void onConfirm() {
            W7hHomeActivity w7hHomeActivity = W7hHomeActivity.this;
            w7hHomeActivity.launchActivity(W7hOtaSettingActivity.newIntent(w7hHomeActivity.getActivity(), W7hHomeActivity.this.deviceId, W7hHomeActivity.this.deviceType));
            W7hHomeActivity.this.killMyself();
        }

        @Override // com.petkit.android.activities.petkitBleDevice.widget.OtaPromptWindow.OnClickListener
        public void onBack() {
            W7hHomeActivity.this.killMyself();
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.w7h.contract.W7hHomeContract.View
    @SuppressLint({"CheckResult"})
    public void showRecommendUpgradeWindow(final OtaResult otaResult) {
        if (DataHelper.getBooleanSF(getActivity(), (this.deviceId + 29) + otaResult.getVersion())) {
            if (DataHelper.getBooleanSF(this, 29 + String.valueOf(this.deviceId) + Constants.DEVICE_BIND_COMPLETE)) {
                launchActivity(DeviceSetInfoActivity.newIntent(this, this.deviceId, 29, true));
                DataHelper.setBooleanSF(getActivity(), 29 + String.valueOf(this.deviceId) + Constants.DEVICE_BIND_COMPLETE, Boolean.FALSE);
                return;
            }
            noUpgrade();
            return;
        }
        if (!this.isShowRecommendUpgradeWindow) {
            this.isShowRecommendUpgradeWindow = true;
            Observable.timer(500L, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$$ExternalSyntheticLambda30
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f$0.lambda$showRecommendUpgradeWindow$39(otaResult, (Long) obj);
                }
            });
            return;
        }
        if (DataHelper.getBooleanSF(getActivity(), 29 + String.valueOf(this.deviceId) + Constants.DEVICE_BIND_COMPLETE)) {
            launchActivity(DeviceSetInfoActivity.newIntent(getActivity(), this.deviceId, this.deviceType, true));
            DataHelper.setBooleanSF(getActivity(), 29 + String.valueOf(this.deviceId) + Constants.DEVICE_BIND_COMPLETE, Boolean.FALSE);
            return;
        }
        noUpgrade();
    }

    public /* synthetic */ void lambda$showRecommendUpgradeWindow$39(OtaResult otaResult, Long l) throws Exception {
        if (this.w7hRecord.getDeviceShared() == null) {
            OtaPromptWindow otaPromptWindow = this.otaPromptWindow;
            if (otaPromptWindow == null || !otaPromptWindow.isShowing()) {
                OtaPromptWindow otaPromptWindow2 = new OtaPromptWindow(getActivity(), getResources().getString(R.string.Ota_prompt), getResources().getString(R.string.Update_right_now), getResources().getString(R.string.Cancel), new OtaPromptWindow.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity.44
                    public final /* synthetic */ OtaResult val$otaCheckResult;

                    @Override // com.petkit.android.activities.petkitBleDevice.widget.OtaPromptWindow.OnClickListener
                    public void onBack() {
                    }

                    public AnonymousClass44(OtaResult otaResult2) {
                        otaResult = otaResult2;
                    }

                    @Override // com.petkit.android.activities.petkitBleDevice.widget.OtaPromptWindow.OnClickListener
                    public void onCancel() {
                        if (W7hHomeActivity.this.otaPromptWindow.isChecked()) {
                            DataHelper.setBooleanSF(W7hHomeActivity.this.getActivity(), (W7hHomeActivity.this.deviceId + 29) + otaResult.getVersion(), Boolean.TRUE);
                        }
                        if (DataHelper.getBooleanSF(W7hHomeActivity.this.getActivity(), 29 + String.valueOf(W7hHomeActivity.this.deviceId) + Constants.DEVICE_BIND_COMPLETE)) {
                            W7hHomeActivity w7hHomeActivity = W7hHomeActivity.this;
                            w7hHomeActivity.launchActivity(DeviceSetInfoActivity.newIntent(w7hHomeActivity.getActivity(), W7hHomeActivity.this.deviceId, W7hHomeActivity.this.deviceType, true));
                            DataHelper.setBooleanSF(W7hHomeActivity.this.getActivity(), 29 + String.valueOf(W7hHomeActivity.this.deviceId) + Constants.DEVICE_BIND_COMPLETE, Boolean.FALSE);
                            return;
                        }
                        W7hHomeActivity.this.noUpgrade();
                    }

                    @Override // com.petkit.android.activities.petkitBleDevice.widget.OtaPromptWindow.OnClickListener
                    public void onConfirm() {
                        if (W7hHomeActivity.this.otaPromptWindow.isChecked()) {
                            DataHelper.setBooleanSF(W7hHomeActivity.this.getActivity(), (W7hHomeActivity.this.deviceId + 29) + otaResult.getVersion(), Boolean.TRUE);
                        }
                        W7hHomeActivity w7hHomeActivity = W7hHomeActivity.this;
                        w7hHomeActivity.launchActivity(W7hOtaSettingActivity.newIntent(w7hHomeActivity.getActivity(), W7hHomeActivity.this.deviceId, W7hHomeActivity.this.deviceType));
                    }
                }, true);
                this.otaPromptWindow = otaPromptWindow2;
                otaPromptWindow2.setTouchOutsideAble(true);
                this.otaPromptWindow.setCheckboxVisibility(0);
                this.otaPromptWindow.show(getWindow().getDecorView());
            }
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$44 */
    public class AnonymousClass44 implements OtaPromptWindow.OnClickListener {
        public final /* synthetic */ OtaResult val$otaCheckResult;

        @Override // com.petkit.android.activities.petkitBleDevice.widget.OtaPromptWindow.OnClickListener
        public void onBack() {
        }

        public AnonymousClass44(OtaResult otaResult2) {
            otaResult = otaResult2;
        }

        @Override // com.petkit.android.activities.petkitBleDevice.widget.OtaPromptWindow.OnClickListener
        public void onCancel() {
            if (W7hHomeActivity.this.otaPromptWindow.isChecked()) {
                DataHelper.setBooleanSF(W7hHomeActivity.this.getActivity(), (W7hHomeActivity.this.deviceId + 29) + otaResult.getVersion(), Boolean.TRUE);
            }
            if (DataHelper.getBooleanSF(W7hHomeActivity.this.getActivity(), 29 + String.valueOf(W7hHomeActivity.this.deviceId) + Constants.DEVICE_BIND_COMPLETE)) {
                W7hHomeActivity w7hHomeActivity = W7hHomeActivity.this;
                w7hHomeActivity.launchActivity(DeviceSetInfoActivity.newIntent(w7hHomeActivity.getActivity(), W7hHomeActivity.this.deviceId, W7hHomeActivity.this.deviceType, true));
                DataHelper.setBooleanSF(W7hHomeActivity.this.getActivity(), 29 + String.valueOf(W7hHomeActivity.this.deviceId) + Constants.DEVICE_BIND_COMPLETE, Boolean.FALSE);
                return;
            }
            W7hHomeActivity.this.noUpgrade();
        }

        @Override // com.petkit.android.activities.petkitBleDevice.widget.OtaPromptWindow.OnClickListener
        public void onConfirm() {
            if (W7hHomeActivity.this.otaPromptWindow.isChecked()) {
                DataHelper.setBooleanSF(W7hHomeActivity.this.getActivity(), (W7hHomeActivity.this.deviceId + 29) + otaResult.getVersion(), Boolean.TRUE);
            }
            W7hHomeActivity w7hHomeActivity = W7hHomeActivity.this;
            w7hHomeActivity.launchActivity(W7hOtaSettingActivity.newIntent(w7hHomeActivity.getActivity(), W7hHomeActivity.this.deviceId, W7hHomeActivity.this.deviceType));
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.w7h.contract.W7hHomeContract.View
    public void noUpgrade() {
        checkAndShowPrompts();
    }

    @Override // com.petkit.android.activities.petkitBleDevice.w7h.contract.W7hHomeContract.View
    public void setTitleStatus(W7hRecord w7hRecord) {
        setupTopBar(w7hRecord);
    }

    @Override // com.petkit.android.activities.petkitBleDevice.w7h.contract.W7hHomeContract.View
    public void refreshHighLightFail() {
        if (this.w7hVlogRecordAdapter == null) {
            this.w7hVlogRecordAdapter = new W7hVlogRecordAdapter(this);
        }
        this.w7hVlogRecordAdapter.replace(new ArrayList());
        this.mBinding.rvHighlights.setAdapter(this.w7hVlogRecordAdapter);
    }

    @Override // com.petkit.android.activities.petkitBleDevice.w7h.contract.W7hHomeContract.View
    public void redirectToVideoRecordActivity(W7hStatisticResInfo w7hStatisticResInfo) {
        jumpToPlayerDetail(w7hStatisticResInfo);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$45 */
    public class AnonymousClass45 implements BleDeviceHomeOfflineWarnWindow.OfflineClickListener {
        public AnonymousClass45() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.widget.BleDeviceHomeOfflineWarnWindow.OfflineClickListener
        public void onClickSeeDetails() {
            W7hHomeActivity w7hHomeActivity = W7hHomeActivity.this;
            w7hHomeActivity.launchActivity(BleDeviceWifiSettingActivity.newIntent(w7hHomeActivity, w7hHomeActivity.w7hRecord.getDeviceId(), 29));
        }

        @Override // com.petkit.android.activities.petkitBleDevice.widget.BleDeviceHomeOfflineWarnWindow.OfflineClickListener
        public void onClickReset() {
            W7hHomeActivity w7hHomeActivity = W7hHomeActivity.this;
            w7hHomeActivity.launchActivity(BleDeviceBindNetWorkActivity.newIntent((Context) w7hHomeActivity, w7hHomeActivity.w7hRecord.getDeviceId(), 29, W7hHomeActivity.this.w7hRecord.getBtMac(), false));
        }
    }

    public void handleWidget(Runnable runnable) {
        WidgetDataInfo widgetDataInfo = this.widgetDataInfo;
        if (widgetDataInfo == null) {
            if (runnable != null) {
                runnable.run();
                return;
            }
            return;
        }
        if (widgetDataInfo.getWash() == 1) {
            W7hRecord w7hRecord = this.w7hRecord;
            if (w7hRecord == null || w7hRecord.getState() == null) {
                return;
            }
            if (((this.w7hRecord.getState().getLiftValveState() == 4 || this.w7hRecord.getState().getLiftValveState() == 3) && this.w7hRecord.getSettings().getCamera() == 0) || this.w7hRecord.getState().getCameraStatus() == 0) {
                w7hOpenCameraAndRefillWindow();
            } else {
                ((W7hHomePresenter) this.mPresenter).controlAddWater();
            }
        } else if (this.widgetDataInfo.getCameraSwitch() == 1) {
            launchActivity(W7hSettingActivity.newIntent(this, this.deviceId, this.deviceType));
        }
        this.widgetDataInfo = null;
    }

    /* JADX WARN: Removed duplicated region for block: B:75:0x009f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void checkAndShowPrompts() {
        /*
            Method dump skipped, instruction units count: 280
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity.checkAndShowPrompts():void");
    }

    public /* synthetic */ boolean lambda$checkAndShowPrompts$40() {
        return this.widgetDataInfo != null;
    }

    public /* synthetic */ void lambda$checkAndShowPrompts$43(Runnable runnable) {
        String str = UserInforUtils.getCurrentUserId(getActivity()) + W7hHomePresenter.simpleDateFormat.format(new Date()) + 29 + this.deviceId + FamilyUtils.getInstance().getCurrentFamilyId(getActivity());
        if (!str.equals(CommonUtils.getSysMap(getActivity(), Constants.W7H_DRINK_ERROR + str))) {
            showDrinkAbnormalDialog(runnable);
        } else {
            runnable.run();
        }
    }

    public /* synthetic */ void lambda$checkAndShowPrompts$45(Runnable runnable) {
        ((W7hHomePresenter) this.mPresenter).bsBannerList(runnable);
    }

    public /* synthetic */ void lambda$checkAndShowPrompts$47(Runnable runnable) {
        ((W7hHomePresenter) this.mPresenter).checkLinkService(runnable);
    }

    public /* synthetic */ void lambda$checkAndShowPrompts$51(Runnable runnable) {
        ((W7hHomePresenter) this.mPresenter).getDistributionDiagram(runnable);
    }

    public void showHeatModuleFirstInstallWindow(Runnable runnable) {
        DeviceErrorWarnWindow deviceErrorWarnWindow = this.showHeatModuleFirstInstallWindow;
        if (deviceErrorWarnWindow == null || !deviceErrorWarnWindow.isShowing()) {
            this.mBinding.bottomView.setMoreUpResource(true);
            T6GuideInfo t6GuideInfo = !CollectionUtil.isEmpty(W7hDataUtils.getInstance().getW7hAllGif().getHeatSwitch()) ? W7hDataUtils.getInstance().getW7hAllGif().getHeatSwitch().get(0) : null;
            if (t6GuideInfo == null) {
                if (runnable != null) {
                    runnable.run();
                    return;
                }
                return;
            }
            DeviceErrorWarnWindow deviceErrorWarnWindow2 = new DeviceErrorWarnWindow(this, this.deviceType, t6GuideInfo.getStepName(), t6GuideInfo.getStepDesc(), t6GuideInfo.getStepUrl());
            this.showHeatModuleFirstInstallWindow = deviceErrorWarnWindow2;
            deviceErrorWarnWindow2.setBtnText(getString(R.string.I_know));
            this.showHeatModuleFirstInstallWindow.showAtLocation(getWindow().getDecorView(), 17, 0, 0);
            this.showHeatModuleFirstInstallWindow.setOnClickListener(new DeviceErrorWarnWindow.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$$ExternalSyntheticLambda51
                @Override // com.petkit.android.activities.petkitBleDevice.widget.DeviceErrorWarnWindow.OnClickListener
                public final void onClickLearnMore() {
                    W7hHomeActivity.lambda$showHeatModuleFirstInstallWindow$52();
                }

                @Override // com.petkit.android.activities.petkitBleDevice.widget.DeviceErrorWarnWindow.OnClickListener
                public /* synthetic */ void onClickText() {
                    DeviceErrorWarnWindow.OnClickListener.CC.$default$onClickText(this);
                }
            });
            this.showHeatModuleFirstInstallWindow.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity.46
                final /* synthetic */ Runnable val$next;

                public AnonymousClass46(Runnable runnable2) {
                    runnable = runnable2;
                }

                @Override // android.widget.PopupWindow.OnDismissListener
                public void onDismiss() {
                    if (W7hHomeActivity.this.w7hRecord.getSettings().getHeaterSwitch() == 0) {
                        ((W7hHomePresenter) ((BaseActivity) W7hHomeActivity.this).mPresenter).updateSettings("heaterSwitch", 1);
                    }
                    DataHelper.setBooleanSF(W7hHomeActivity.this.getActivity(), W7hUtils.W7H_HEAT_MODULE_FIRST_INSTALL_FLAG + W7hHomeActivity.this.deviceId, Boolean.TRUE);
                    Runnable runnable2 = runnable;
                    if (runnable2 != null) {
                        runnable2.run();
                    }
                }
            });
            this.showHeatModuleFirstInstallWindow.showAtLocation(getWindow().getDecorView(), 17, 0, 0);
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$46 */
    public class AnonymousClass46 implements PopupWindow.OnDismissListener {
        final /* synthetic */ Runnable val$next;

        public AnonymousClass46(Runnable runnable2) {
            runnable = runnable2;
        }

        @Override // android.widget.PopupWindow.OnDismissListener
        public void onDismiss() {
            if (W7hHomeActivity.this.w7hRecord.getSettings().getHeaterSwitch() == 0) {
                ((W7hHomePresenter) ((BaseActivity) W7hHomeActivity.this).mPresenter).updateSettings("heaterSwitch", 1);
            }
            DataHelper.setBooleanSF(W7hHomeActivity.this.getActivity(), W7hUtils.W7H_HEAT_MODULE_FIRST_INSTALL_FLAG + W7hHomeActivity.this.deviceId, Boolean.TRUE);
            Runnable runnable2 = runnable;
            if (runnable2 != null) {
                runnable2.run();
            }
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.w7h.contract.W7hHomeContract.View
    public void showDistributionDiagram(DistributionDiagram distributionDiagram, Runnable runnable) {
        DrinkWaterWindow drinkWaterWindow = new DrinkWaterWindow(this, distributionDiagram.getDesc(), distributionDiagram.getMorning(), distributionDiagram.getAfternoon(), distributionDiagram.getNight());
        drinkWaterWindow.show(getWindow().getDecorView());
        drinkWaterWindow.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity.47
            final /* synthetic */ Runnable val$next;

            public AnonymousClass47(Runnable runnable2) {
                runnable = runnable2;
            }

            @Override // android.widget.PopupWindow.OnDismissListener
            public void onDismiss() {
                Runnable runnable2 = runnable;
                if (runnable2 != null) {
                    runnable2.run();
                }
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$47 */
    public class AnonymousClass47 implements PopupWindow.OnDismissListener {
        final /* synthetic */ Runnable val$next;

        public AnonymousClass47(Runnable runnable2) {
            runnable = runnable2;
        }

        @Override // android.widget.PopupWindow.OnDismissListener
        public void onDismiss() {
            Runnable runnable2 = runnable;
            if (runnable2 != null) {
                runnable2.run();
            }
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$48 */
    public class AnonymousClass48 implements NormalCenterTipWindow.OnClickOk {
        public AnonymousClass48() {
        }

        @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
        public void onOkClick() {
            DataHelper.setBooleanSF(W7hHomeActivity.this.getActivity(), Constants.W7H_BIND_SERVICE_PROMPT + W7hHomeActivity.this.deviceId, Boolean.FALSE);
            W7hHomeActivity.this.launchActivity(new Intent(W7hHomeActivity.this.getActivity(), (Class<?>) ServiceManagementActivity.class));
        }

        @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
        public void onCancelClick() {
            DataHelper.setBooleanSF(W7hHomeActivity.this.getActivity(), Constants.W7H_BIND_SERVICE_PROMPT + W7hHomeActivity.this.deviceId, Boolean.FALSE);
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.w7h.contract.W7hHomeContract.View
    public void bindServicePrompt(int i, Runnable runnable) {
        if (i > 0) {
            NormalCenterTipWindow normalCenterTipWindow = new NormalCenterTipWindow(this, new NormalCenterTipWindow.OnClickOk() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity.48
                public AnonymousClass48() {
                }

                @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
                public void onOkClick() {
                    DataHelper.setBooleanSF(W7hHomeActivity.this.getActivity(), Constants.W7H_BIND_SERVICE_PROMPT + W7hHomeActivity.this.deviceId, Boolean.FALSE);
                    W7hHomeActivity.this.launchActivity(new Intent(W7hHomeActivity.this.getActivity(), (Class<?>) ServiceManagementActivity.class));
                }

                @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
                public void onCancelClick() {
                    DataHelper.setBooleanSF(W7hHomeActivity.this.getActivity(), Constants.W7H_BIND_SERVICE_PROMPT + W7hHomeActivity.this.deviceId, Boolean.FALSE);
                }
            }, (String) null, new SpannableString(getActivity().getString(R.string.Bind_cloud_service_prompt)));
            ((TextView) normalCenterTipWindow.getContentView().findViewById(R.id.tv_cancel)).setText(getString(R.string.Cancel));
            ((TextView) normalCenterTipWindow.getContentView().findViewById(R.id.tv_ok)).setText(getString(R.string.Decorrelation));
            Objects.requireNonNull(runnable);
            normalCenterTipWindow.setOnDismissListener(new W7hHomeActivity$$ExternalSyntheticLambda1(runnable));
            normalCenterTipWindow.show(getWindow().getDecorView());
            return;
        }
        DataHelper.setBooleanSF(getActivity(), Constants.W7H_BIND_SERVICE_PROMPT + this.deviceId, Boolean.FALSE);
        if (runnable != null) {
            runnable.run();
        }
    }

    public void showPetGuide(Runnable runnable) {
        if (CommonUtils.getSysBoolMap(this, "CAT_FACE_GUIDE_" + this.familyInfor.getGroupId(), false)) {
            if (runnable != null) {
                runnable.run();
                return;
            }
            return;
        }
        boolean currentFamilyIsUploadCatAndDogPhoto = FamilyUtils.getInstance().getCurrentFamilyIsUploadCatAndDogPhoto(getActivity());
        boolean zHasOnePet = FamilyUtils.getInstance().hasOnePet(getActivity(), this.deviceId, this.deviceType);
        List<Pet> currentFamilyPetListExceptBlocket = FamilyUtils.getInstance().getCurrentFamilyPetListExceptBlocket(getActivity());
        if ((currentFamilyIsUploadCatAndDogPhoto || !zHasOnePet) && currentFamilyPetListExceptBlocket != null && !currentFamilyPetListExceptBlocket.isEmpty()) {
            if (runnable != null) {
                runnable.run();
                return;
            }
            return;
        }
        NormalCenterTipWindow normalCenterTipWindow = new NormalCenterTipWindow(this, new NormalCenterTipWindow.OnClickOk() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity.49
            @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
            public void onCancelClick() {
            }

            public AnonymousClass49() {
            }

            @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
            public void onOkClick() {
                W7hHomeActivity w7hHomeActivity = W7hHomeActivity.this;
                w7hHomeActivity.launchActivity(PetWeightActivity.newIntent(w7hHomeActivity.getActivity(), W7hHomeActivity.this.deviceId, W7hHomeActivity.this.deviceType));
            }
        }, (String) null, getString(R.string.Upload_face_photo_content));
        normalCenterTipWindow.setOkButtonText(getString(R.string.Upload_face_photo));
        normalCenterTipWindow.setCancelButtonText(getString(R.string.Not_upload_now));
        normalCenterTipWindow.show(getWindow().getDecorView());
        Objects.requireNonNull(runnable);
        normalCenterTipWindow.setOnDismissListener(new W7hHomeActivity$$ExternalSyntheticLambda1(runnable));
        CommonUtils.addSysBoolMap(this, "CAT_FACE_GUIDE_" + this.familyInfor.getGroupId(), true);
        CommonUtils.addSysLongMap(this, "CAT_FACE_NOT_UPLOAD_POP_TIME_" + this.familyInfor.getGroupId(), System.currentTimeMillis());
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$49 */
    public class AnonymousClass49 implements NormalCenterTipWindow.OnClickOk {
        @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
        public void onCancelClick() {
        }

        public AnonymousClass49() {
        }

        @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
        public void onOkClick() {
            W7hHomeActivity w7hHomeActivity = W7hHomeActivity.this;
            w7hHomeActivity.launchActivity(PetWeightActivity.newIntent(w7hHomeActivity.getActivity(), W7hHomeActivity.this.deviceId, W7hHomeActivity.this.deviceType));
        }
    }

    public void showFirstGuide(Runnable runnable) {
        if (this.firstGuide != null) {
            return;
        }
        GuideBuilder guideBuilder = new GuideBuilder();
        guideBuilder.setTargetView(this.mBinding.vpLivePanel).setAlpha(180).setHighTargetCorner(50).setHighTargetPaddingLeft(ArmsUtils.dip2px(getActivity(), 0.0f)).setHighTargetPaddingRight(ArmsUtils.dip2px(getActivity(), 0.0f)).setHighTargetPaddingBottom(ArmsUtils.dip2px(getActivity(), 0.0f)).setHighTargetPaddingTop(ArmsUtils.dip2px(getActivity(), 0.0f)).setOutsideTouchable(false).setAutoDismiss(true);
        guideBuilder.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity.50
            public final /* synthetic */ Runnable val$next;

            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onShown() {
            }

            public AnonymousClass50(Runnable runnable2) {
                runnable = runnable2;
            }

            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onDismiss() {
                W7hHomeActivity.this.showSecondGuide(runnable);
            }
        });
        guideBuilder.addComponent(new GuideD4shBottomRightTipAndBtnAndLineComponent(new GuideParam(getResources().getString(R.string.D4sh_home_guide_prompt_one), "1/4", 4, 32, ArmsUtils.dip2px(getActivity(), 0.0f), 0, getResources().getString(R.string.Next_tip), R.layout.layout_w7h_bottom_guide), new ConfirmListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity.51
            public AnonymousClass51() {
            }

            @Override // com.petkit.android.activities.petkitBleDevice.w5.guide.ConfirmListener
            public void onConfirmListener() {
                if (W7hHomeActivity.this.firstGuide != null) {
                    W7hHomeActivity.this.firstGuide.dismiss();
                }
            }
        }));
        Guide guideCreateGuide = guideBuilder.createGuide();
        this.firstGuide = guideCreateGuide;
        guideCreateGuide.show(getActivity());
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$50 */
    public class AnonymousClass50 implements GuideBuilder.OnVisibilityChangedListener {
        public final /* synthetic */ Runnable val$next;

        @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
        public void onShown() {
        }

        public AnonymousClass50(Runnable runnable2) {
            runnable = runnable2;
        }

        @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
        public void onDismiss() {
            W7hHomeActivity.this.showSecondGuide(runnable);
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$51 */
    public class AnonymousClass51 implements ConfirmListener {
        public AnonymousClass51() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.w5.guide.ConfirmListener
        public void onConfirmListener() {
            if (W7hHomeActivity.this.firstGuide != null) {
                W7hHomeActivity.this.firstGuide.dismiss();
            }
        }
    }

    public void showSecondGuide(Runnable runnable) {
        if (this.secondGuide != null) {
            return;
        }
        GuideBuilder guideBuilder = new GuideBuilder();
        guideBuilder.setTargetView(this.mBinding.llDataStats).setAlpha(180).setHighTargetCorner(50).setHighTargetPaddingLeft(ArmsUtils.dip2px(getActivity(), 0.0f)).setHighTargetPaddingRight(ArmsUtils.dip2px(getActivity(), 0.0f)).setHighTargetPaddingBottom(ArmsUtils.dip2px(getActivity(), 0.0f)).setHighTargetPaddingTop(ArmsUtils.dip2px(getActivity(), 0.0f)).setOutsideTouchable(false).setAutoDismiss(true);
        guideBuilder.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity.52
            public final /* synthetic */ Runnable val$next;

            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onShown() {
            }

            public AnonymousClass52(Runnable runnable2) {
                runnable = runnable2;
            }

            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onDismiss() {
                int[] iArr = new int[2];
                DisplayMetrics displayMetrics = new DisplayMetrics();
                W7hHomeActivity.this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int i = displayMetrics.heightPixels;
                W7hHomeActivity.this.mBinding.ivChartShow.getLocationOnScreen(iArr);
                int iDip2px = (iArr[1] - i) + ArmsUtils.dip2px(W7hHomeActivity.this.getActivity(), 150.0f);
                AppBarLayout appBarLayout = W7hHomeActivity.this.mBinding.appBar;
                if (iDip2px < 0) {
                    iDip2px = 0;
                }
                appBarLayout.scrollTo(0, iDip2px);
                W7hHomeActivity.this.showThirdGuide(runnable);
            }
        });
        guideBuilder.addComponent(new GuideD4shBottomRightTipAndBtnAndLineComponent(new GuideParam(getResources().getString(R.string.D4sh_home_guide_prompt_two), "2/4", 4, 32, ArmsUtils.dip2px(getActivity(), 0.0f), 0, getResources().getString(R.string.Next_tip), R.layout.layout_w7h_bottom_guide), new ConfirmListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity.53
            public AnonymousClass53() {
            }

            @Override // com.petkit.android.activities.petkitBleDevice.w5.guide.ConfirmListener
            public void onConfirmListener() {
                if (W7hHomeActivity.this.secondGuide != null) {
                    W7hHomeActivity.this.secondGuide.dismiss();
                }
            }
        }));
        Guide guideCreateGuide = guideBuilder.createGuide();
        this.secondGuide = guideCreateGuide;
        guideCreateGuide.show(getActivity());
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$52 */
    public class AnonymousClass52 implements GuideBuilder.OnVisibilityChangedListener {
        public final /* synthetic */ Runnable val$next;

        @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
        public void onShown() {
        }

        public AnonymousClass52(Runnable runnable2) {
            runnable = runnable2;
        }

        @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
        public void onDismiss() {
            int[] iArr = new int[2];
            DisplayMetrics displayMetrics = new DisplayMetrics();
            W7hHomeActivity.this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int i = displayMetrics.heightPixels;
            W7hHomeActivity.this.mBinding.ivChartShow.getLocationOnScreen(iArr);
            int iDip2px = (iArr[1] - i) + ArmsUtils.dip2px(W7hHomeActivity.this.getActivity(), 150.0f);
            AppBarLayout appBarLayout = W7hHomeActivity.this.mBinding.appBar;
            if (iDip2px < 0) {
                iDip2px = 0;
            }
            appBarLayout.scrollTo(0, iDip2px);
            W7hHomeActivity.this.showThirdGuide(runnable);
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$53 */
    public class AnonymousClass53 implements ConfirmListener {
        public AnonymousClass53() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.w5.guide.ConfirmListener
        public void onConfirmListener() {
            if (W7hHomeActivity.this.secondGuide != null) {
                W7hHomeActivity.this.secondGuide.dismiss();
            }
        }
    }

    public void showThirdGuide(Runnable runnable) {
        if (this.thirdGuide != null) {
            return;
        }
        GuideBuilder guideBuilder = new GuideBuilder();
        guideBuilder.setTargetView(this.mBinding.ivTodayEvent).setAlpha(180).setHighTargetCorner(50).setHighTargetPaddingLeft(ArmsUtils.dip2px(getActivity(), 3.0f)).setHighTargetPaddingRight(ArmsUtils.dip2px(getActivity(), 3.0f)).setHighTargetPaddingBottom(ArmsUtils.dip2px(getActivity(), 3.0f)).setHighTargetPaddingTop(ArmsUtils.dip2px(getActivity(), 3.0f)).setOutsideTouchable(false).setAutoDismiss(true);
        guideBuilder.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity.54
            public final /* synthetic */ Runnable val$next;

            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onShown() {
            }

            public AnonymousClass54(Runnable runnable2) {
                runnable = runnable2;
            }

            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onDismiss() {
                W7hHomeActivity.this.mBinding.appBar.scrollTo(0, 0);
                W7hHomeActivity.this.showLastGuide(runnable);
            }
        });
        guideBuilder.addComponent(new GuideD4shBottomRightTipAndBtnAndLineComponent(new GuideParam(getResources().getString(R.string.D4sh_home_guide_prompt_three), "3/4", 2, 32, ArmsUtils.dip2px(getActivity(), 22.0f), 0, getResources().getString(R.string.Next_tip), R.layout.layout_w7h_top_guide), new ConfirmListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity.55
            public AnonymousClass55() {
            }

            @Override // com.petkit.android.activities.petkitBleDevice.w5.guide.ConfirmListener
            public void onConfirmListener() {
                if (W7hHomeActivity.this.thirdGuide != null) {
                    W7hHomeActivity.this.thirdGuide.dismiss();
                }
            }
        }));
        Guide guideCreateGuide = guideBuilder.createGuide();
        this.thirdGuide = guideCreateGuide;
        guideCreateGuide.show(getActivity());
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$54 */
    public class AnonymousClass54 implements GuideBuilder.OnVisibilityChangedListener {
        public final /* synthetic */ Runnable val$next;

        @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
        public void onShown() {
        }

        public AnonymousClass54(Runnable runnable2) {
            runnable = runnable2;
        }

        @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
        public void onDismiss() {
            W7hHomeActivity.this.mBinding.appBar.scrollTo(0, 0);
            W7hHomeActivity.this.showLastGuide(runnable);
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$55 */
    public class AnonymousClass55 implements ConfirmListener {
        public AnonymousClass55() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.w5.guide.ConfirmListener
        public void onConfirmListener() {
            if (W7hHomeActivity.this.thirdGuide != null) {
                W7hHomeActivity.this.thirdGuide.dismiss();
            }
        }
    }

    public void showLastGuide(Runnable runnable) {
        int iDip2px;
        if (this.lastGuide != null) {
            return;
        }
        GuideBuilder guideBuilder = new GuideBuilder();
        guideBuilder.setTargetView(this.mBinding.bottomView.findViewById(R.id.ll_coll)).setAlpha(180).setHighTargetCorner(50).setHighTargetPaddingLeft(ArmsUtils.dip2px(getActivity(), 3.0f)).setHighTargetPaddingRight(ArmsUtils.dip2px(getActivity(), 3.0f)).setHighTargetPaddingBottom(ArmsUtils.dip2px(getActivity(), 3.0f)).setHighTargetPaddingTop(ArmsUtils.dip2px(getActivity(), 3.0f)).setOutsideTouchable(false).setAutoDismiss(true);
        guideBuilder.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity.56
            public final /* synthetic */ Runnable val$next;

            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onShown() {
            }

            public AnonymousClass56(Runnable runnable2) {
                runnable = runnable2;
            }

            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onDismiss() {
                DataHelper.setBooleanSF(W7hHomeActivity.this.getActivity(), Constants.W7H_HOME_GUIDE, Boolean.TRUE);
                Runnable runnable2 = runnable;
                if (runnable2 != null) {
                    runnable2.run();
                }
            }
        });
        if ("zh_CN".equals(UserInforUtils.getLanguageLocaleString(CommonUtils.getAppContext()))) {
            iDip2px = ArmsUtils.dip2px(getActivity(), -30.0f);
        } else {
            iDip2px = ArmsUtils.dip2px(getActivity(), 0.0f);
        }
        guideBuilder.addComponent(new GuideD4shBottomRightTipAndBtnAndLineComponent(new GuideParam(getResources().getString(R.string.D4sh_home_guide_prompt_four), "4/4", 2, 32, iDip2px, 0, getResources().getString(R.string.Know), R.layout.layout_w7h_top_guide), new ConfirmListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity.57
            public AnonymousClass57() {
            }

            @Override // com.petkit.android.activities.petkitBleDevice.w5.guide.ConfirmListener
            public void onConfirmListener() {
                if (W7hHomeActivity.this.lastGuide != null) {
                    W7hHomeActivity.this.lastGuide.dismiss();
                }
            }
        }));
        Guide guideCreateGuide = guideBuilder.createGuide();
        this.lastGuide = guideCreateGuide;
        guideCreateGuide.show(getActivity());
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$56 */
    public class AnonymousClass56 implements GuideBuilder.OnVisibilityChangedListener {
        public final /* synthetic */ Runnable val$next;

        @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
        public void onShown() {
        }

        public AnonymousClass56(Runnable runnable2) {
            runnable = runnable2;
        }

        @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
        public void onDismiss() {
            DataHelper.setBooleanSF(W7hHomeActivity.this.getActivity(), Constants.W7H_HOME_GUIDE, Boolean.TRUE);
            Runnable runnable2 = runnable;
            if (runnable2 != null) {
                runnable2.run();
            }
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$57 */
    public class AnonymousClass57 implements ConfirmListener {
        public AnonymousClass57() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.w5.guide.ConfirmListener
        public void onConfirmListener() {
            if (W7hHomeActivity.this.lastGuide != null) {
                W7hHomeActivity.this.lastGuide.dismiss();
            }
        }
    }

    @Subscriber
    public void refreshVlogByDelete(D4shVlogDeleteMsg d4shVlogDeleteMsg) {
        ((W7hHomePresenter) this.mPresenter).getHighLightRecord(this.deviceId, this.deviceType, false);
    }

    @Subscriber
    public void vlogStateChanged(VlogStateChanged vlogStateChanged) {
        if (vlogStateChanged.isRefreshRemoteData()) {
            W7hVlogRecordAdapter w7hVlogRecordAdapter = this.w7hVlogRecordAdapter;
            if (w7hVlogRecordAdapter != null) {
                w7hVlogRecordAdapter.removeAll();
            }
            ((W7hHomePresenter) this.mPresenter).getHighLightRecord(this.deviceId, this.deviceType, false);
            return;
        }
        W7hVlogRecordAdapter w7hVlogRecordAdapter2 = this.w7hVlogRecordAdapter;
        if (w7hVlogRecordAdapter2 != null) {
            w7hVlogRecordAdapter2.notifyDataSetChanged();
        }
    }

    private void initVlogView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(0);
        this.mBinding.rvHighlights.setLayoutManager(linearLayoutManager);
        W7hVlogRecordAdapter w7hVlogRecordAdapter = new W7hVlogRecordAdapter(this);
        this.w7hVlogRecordAdapter = w7hVlogRecordAdapter;
        this.mBinding.rvHighlights.setAdapter(w7hVlogRecordAdapter);
        this.w7hVlogRecordAdapter.setDeviceId(this.deviceId);
        this.w7hVlogRecordAdapter.setonItemClickListener(new W7hVlogRecordAdapter.OnItemClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity.58
            public AnonymousClass58() {
            }

            @Override // com.petkit.android.activities.petkitBleDevice.w7h.adapter.W7hVlogRecordAdapter.OnItemClickListener
            public void onPlayBtnClick(HighlightRecord highlightRecord) {
                W7hHomeActivity w7hHomeActivity = W7hHomeActivity.this;
                w7hHomeActivity.startActivityForResult(MediaDisplayActivity.newIntent(w7hHomeActivity, highlightRecord, 3, w7hHomeActivity.deviceId, W7hHomeActivity.this.deviceType), 5);
            }

            @Override // com.petkit.android.activities.petkitBleDevice.w7h.adapter.W7hVlogRecordAdapter.OnItemClickListener
            public void onMarkVlogClick(HighlightRecord highlightRecord) {
                ((W7hHomePresenter) ((BaseActivity) W7hHomeActivity.this).mPresenter).getHighlightM3U8(highlightRecord.getId());
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$58 */
    public class AnonymousClass58 implements W7hVlogRecordAdapter.OnItemClickListener {
        public AnonymousClass58() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.w7h.adapter.W7hVlogRecordAdapter.OnItemClickListener
        public void onPlayBtnClick(HighlightRecord highlightRecord) {
            W7hHomeActivity w7hHomeActivity = W7hHomeActivity.this;
            w7hHomeActivity.startActivityForResult(MediaDisplayActivity.newIntent(w7hHomeActivity, highlightRecord, 3, w7hHomeActivity.deviceId, W7hHomeActivity.this.deviceType), 5);
        }

        @Override // com.petkit.android.activities.petkitBleDevice.w7h.adapter.W7hVlogRecordAdapter.OnItemClickListener
        public void onMarkVlogClick(HighlightRecord highlightRecord) {
            ((W7hHomePresenter) ((BaseActivity) W7hHomeActivity.this).mPresenter).getHighlightM3U8(highlightRecord.getId());
        }
    }

    private void showVlogTipWindow(View view) {
        if (this.w7hRecord.getSettings().getAutoProduct() == 1) {
            return;
        }
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.width = -1;
        layoutParams.height = -2;
        W7hVlogTipWindow w7hVlogTipWindow = this.w7hVlogTipWindow;
        if (w7hVlogTipWindow == null || !w7hVlogTipWindow.isShowing()) {
            W7hVlogTipWindow w7hVlogTipWindow2 = new W7hVlogTipWindow(getActivity(), layoutParams);
            this.w7hVlogTipWindow = w7hVlogTipWindow2;
            w7hVlogTipWindow2.setBackgroundDrawable(new BitmapDrawable());
            this.w7hVlogTipWindow.setOutsideTouchable(true);
            this.w7hVlogTipWindow.setFocusable(true);
            DataHelper.setBooleanSF(getActivity(), "com.petkit.android.W7H_VLOG_MAKE_TIP_29_" + this.w7hRecord.getDeviceId() + "_" + D4shUtils.getTodayYYYYMMddFormatStr(), Boolean.TRUE);
            this.handler.postDelayed(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity.59
                public AnonymousClass59() {
                }

                @Override // java.lang.Runnable
                public void run() {
                    if (W7hHomeActivity.this.w7hVlogTipWindow == null || !W7hHomeActivity.this.w7hVlogTipWindow.isShowing()) {
                        return;
                    }
                    W7hHomeActivity.this.w7hVlogTipWindow.dismiss();
                }
            }, 3000L);
            int[] iArr = new int[2];
            view.getLocationOnScreen(iArr);
            this.w7hVlogTipWindow.getContentView().measure(0, 0);
            this.w7hVlogTipWindow.showAtLocation(view, 0, iArr[0], iArr[1] - this.w7hVlogTipWindow.getContentView().getMeasuredHeight());
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$59 */
    public class AnonymousClass59 implements Runnable {
        public AnonymousClass59() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (W7hHomeActivity.this.w7hVlogTipWindow == null || !W7hHomeActivity.this.w7hVlogTipWindow.isShowing()) {
                return;
            }
            W7hHomeActivity.this.w7hVlogTipWindow.dismiss();
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.w7h.contract.W7hHomeContract.View
    public void refreshHighLight(HighLightRecordRsp highLightRecordRsp) {
        W7hVlogRecordAdapter w7hVlogRecordAdapter = this.w7hVlogRecordAdapter;
        if (w7hVlogRecordAdapter != null) {
            w7hVlogRecordAdapter.replace(highLightRecordRsp.getItems());
            if (highLightRecordRsp.getItems().isEmpty()) {
                return;
            }
            HighlightRecord highlightRecord = highLightRecordRsp.getItems().get(0);
            if (VlogUtils.getVlogMarkRecord(CommonUtils.getCurrentUserId(), highlightRecord.getId()) == null) {
                if ((System.currentTimeMillis() <= ((long) highlightRecord.getExpired()) * 1000 || highlightRecord.getExpired() == 0) && highlightRecord.getId() != 0 && TextUtils.isEmpty(highlightRecord.getVideoUrl())) {
                    if (DataHelper.getBooleanSF(getActivity(), "com.petkit.android.W7H_VLOG_MAKE_TIP_29_" + this.w7hRecord.getDeviceId() + "_" + D4shUtils.getTodayYYYYMMddFormatStr()) || Calendar.getInstance().get(11) < 8) {
                        return;
                    }
                    showVlogTipWindow(this.mBinding.rvHighlights.getChildAt(0));
                }
            }
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.w7h.contract.W7hHomeContract.View
    public void setHighlightM3u8UrlList(int i, ArrayList<VlogM3U8Mode> arrayList) {
        Intent intent = new Intent(this, (Class<?>) VlogMakeService.class);
        intent.putExtra(Constants.EXTRA_DEVICE_ID, this.deviceId);
        intent.putExtra(Constants.EXTRA_DEVICE_TYPE, 29);
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

    private void updateServiceState() {
        if (AppVersionStateUtils.getCurrentAppVersionCheckState()) {
            return;
        }
        showPurchaseEntry();
    }

    private void showPurchaseEntry() {
        final int intergerSF = DataHelper.getIntergerSF(this, Constants.W7H_FREE_TRIAL_LEFT_TIME_PROMPT_COUNT + this.deviceType + "_" + this.w7hRecord.getDeviceId(), 2);
        this.mBinding.llTrialRemainingTime.setVisibility(8);
        if (this.w7hRecord.getServiceStatus() == 1 && this.w7hRecord.getMoreService() != 1 && this.w7hRecord.getCloudProduct().getSubscribe() != 1 && "FREE".equalsIgnoreCase(this.w7hRecord.getCloudProduct().getChargeType())) {
            final boolean z = (Long.parseLong(this.w7hRecord.getCloudProduct().getWorkIndate()) * 1000) - System.currentTimeMillis() < 259200000;
            if (intergerSF == 2 || (intergerSF == 1 && z)) {
                this.mBinding.llTrialRemainingTime.setVisibility(0);
                int i = (int) (((Long.parseLong(this.w7hRecord.getCloudProduct().getWorkIndate()) * 1000) - System.currentTimeMillis()) / 86400000);
                String str = "zh_CN".equalsIgnoreCase(UserInforUtils.getLanguageLocaleString(CommonUtils.getAppContext())) ? "" : " ";
                if (i > 0) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(getResources().getString(R.string.Trial_remaining_time));
                    sb.append("：");
                    sb.append(i);
                    sb.append(str);
                    sb.append(getString(i > 1 ? R.string.Unit_days : R.string.Unit_day));
                    this.mBinding.tvTrialRemainingTime.setText(sb.toString());
                } else if (i == 0) {
                    float f = ((Long.parseLong(this.w7hRecord.getCloudProduct().getWorkIndate()) * 1000.0f) - System.currentTimeMillis()) / 3600000.0f;
                    if (f <= 0.0f) {
                        this.mBinding.llTrialRemainingTime.setVisibility(8);
                    } else if (f < 1.0f) {
                        this.mBinding.tvTrialRemainingTime.setText(getString(R.string.Trial_remaining_time) + "：1" + str + getResources().getString(R.string.Unit_hour));
                    } else {
                        int i2 = (int) f;
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(getString(R.string.Trial_remaining_time));
                        sb2.append("：");
                        sb2.append(i2);
                        sb2.append(str);
                        sb2.append(getString(i2 > 1 ? R.string.Unit_hours : R.string.Unit_hour));
                        this.mBinding.tvTrialRemainingTime.setText(sb2.toString());
                    }
                }
                this.mBinding.ivTrialRemainingTimeClose.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$$ExternalSyntheticLambda2
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$showPurchaseEntry$53(z, intergerSF, view);
                    }
                });
            }
        }
    }

    public /* synthetic */ void lambda$showPurchaseEntry$53(boolean z, int i, View view) {
        this.mBinding.llTrialRemainingTime.setVisibility(8);
        DataHelper.setIntergerSF(this, Constants.W7H_FREE_TRIAL_LEFT_TIME_PROMPT_COUNT + this.deviceType + "_" + this.w7hRecord.getDeviceId(), z ? 0 : i - 1);
    }

    @Override // com.petkit.android.activities.petkitBleDevice.w7h.contract.W7hHomeContract.View
    public void getFreePackage(D4shBannerData d4shBannerData) {
        W7hRecord w7hRecord;
        if (TextUtils.isEmpty(CommonUtils.getSysMap(Constants.CLOUD_SERVICE_DELAY_TIME)) || TextUtils.isEmpty(this.w7hRecord.getCreatedAt())) {
            return;
        }
        try {
            if ((System.currentTimeMillis() - DateUtil.parseISO8601Date(this.w7hRecord.getCreatedAt()).getTime()) / 1000 <= ((CloudServiceDelayTimeResult) new Gson().fromJson(r0, CloudServiceDelayTimeResult.class)).getDelayTime() || d4shBannerData == null || !FamilyUtils.getInstance().isDeviceBelongToMyself(this, this.deviceId).booleanValue() || (w7hRecord = this.w7hRecord) == null || w7hRecord.getDeviceShared() != null || this.w7hRecord.getServiceStatus() != 0 || d4shBannerData.getFreeActivity() == null || d4shBannerData.getFreeActivity().getImageRet() == null) {
                return;
            }
            ((W7hHomePresenter) this.mPresenter).getFreePackage(d4shBannerData.getFreeActivity().getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.w7h.contract.W7hHomeContract.View
    public void showFreeTrialDialog(int i) {
        Intent intent = new Intent(T6Utils.BROADCAST_T6_STATE_MSG);
        intent.putExtra(Constants.EXTRA_DEVICE_ID, this.deviceId);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        startActivity(CloudServiceFreeTrialActivity.newIntent(this, this.deviceId, this.deviceType, i));
    }

    @Override // com.petkit.android.activities.petkitBleDevice.w7h.contract.W7hHomeContract.View
    public void refreshBannerList(D4shBannerData d4shBannerData, Runnable runnable) {
        boolean z;
        BannerStateCache bannerStateCache;
        W7hRecord w7hRecordByDeviceId = W7hDataUtils.getInstance().getW7hRecordByDeviceId(this.deviceId);
        this.w7hRecord = w7hRecordByDeviceId;
        boolean z2 = w7hRecordByDeviceId.getRealDeviceShared() == null && !FamilyUtils.getInstance().checkIsSharedDevice(this, this.w7hRecord.getDeviceId(), 29);
        ArrayList arrayList = new ArrayList();
        int serviceStatus = this.w7hRecord.getServiceStatus();
        if (serviceStatus != 0) {
            if (serviceStatus == 1) {
                if (this.w7hRecord.getMoreService() != 1 && this.w7hRecord.getCloudProduct().getSubscribe() != 1 && (Long.parseLong(this.w7hRecord.getCloudProduct().getWorkIndate()) * 1000) - System.currentTimeMillis() <= 259200000 && !"FREE".equalsIgnoreCase(this.w7hRecord.getCloudProduct().getChargeType())) {
                    arrayList.add(new D4shBannerData.ServiceStatusData(2));
                }
                if (runnable != null) {
                    runnable.run();
                }
            } else if (serviceStatus == 2) {
                if (d4shBannerData == null || d4shBannerData.getFreeActivity() == null) {
                    arrayList.add(new D4shBannerData.ServiceStatusData(3));
                    if (runnable != null) {
                        runnable.run();
                    }
                } else if (z2) {
                    arrayList.add(d4shBannerData.getFreeActivity());
                    showFreeActivity(d4shBannerData, runnable);
                } else if (runnable != null) {
                    runnable.run();
                }
            }
        } else if (d4shBannerData == null || d4shBannerData.getFreeActivity() == null) {
            arrayList.add(new D4shBannerData.ServiceStatusData(1));
            if (runnable != null) {
                runnable.run();
            }
        } else if (z2) {
            arrayList.add(d4shBannerData.getFreeActivity());
            showFreeActivity(d4shBannerData, runnable);
        } else if (runnable != null) {
            runnable.run();
        }
        if (d4shBannerData != null && z2) {
            arrayList.addAll(d4shBannerData.getPromotionList());
        }
        if (!arrayList.isEmpty()) {
            String sysMap = CommonUtils.getSysMap(Constants.W7H_PROMOTE_BANNER + UserInforUtils.getCurrentUserId(this) + this.w7hRecord.getDeviceId() + (this.w7hRecord.getCloudProduct() == null ? 0 : this.w7hRecord.getCloudProduct().getServiceId()));
            if (!TextUtils.isEmpty(sysMap)) {
                List list = (List) new Gson().fromJson(sysMap, new TypeToken<List<BannerStateCache>>() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity.60
                    public AnonymousClass60() {
                    }
                }.getType());
                boolean z3 = false;
                for (int i = 0; i < arrayList.size(); i++) {
                    D4shBannerData.BannerData bannerData = (D4shBannerData.BannerData) arrayList.get(i);
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
                    this.mBinding.flHomeBanner.setVisibility(0);
                    this.mBinding.indicator.setVisibility(0);
                } else {
                    this.mBinding.flHomeBanner.setVisibility(8);
                    this.mBinding.indicator.setVisibility(8);
                }
                z = !z3;
            } else {
                this.mBinding.flHomeBanner.setVisibility(0);
                this.mBinding.indicator.setVisibility(0);
                z = true;
            }
            if (AppVersionStateUtils.getCurrentAppVersionCheckState()) {
                this.mBinding.flHomeBanner.setVisibility(8);
                this.mBinding.indicator.setVisibility(8);
            }
            W7hHomeBannerPageAdapter w7hHomeBannerPageAdapter = new W7hHomeBannerPageAdapter(this, arrayList, this.w7hRecord, this.deviceType, new W7hHomeBannerPageAdapter.OnItemClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity.61
                public AnonymousClass61() {
                }

                @Override // com.petkit.android.activities.petkitBleDevice.w7h.adapter.W7hHomeBannerPageAdapter.OnItemClickListener
                public void onBannerItemClick(int i2) {
                    W7hUtils.getInstance().redirectToPurchasePage(W7hHomeActivity.this.getActivity(), W7hHomeActivity.this.w7hRecord, 29, W7hHomeActivity.this.deviceId);
                }

                @Override // com.petkit.android.activities.petkitBleDevice.w7h.adapter.W7hHomeBannerPageAdapter.OnItemClickListener
                public void redirectToPurchase(int i2, long j) {
                    W7hUtils.getInstance().redirectToPurchasePage(W7hHomeActivity.this.getActivity(), W7hHomeActivity.this.w7hRecord, 29, W7hHomeActivity.this.deviceId);
                }
            });
            this.mBinding.vpHomeBanner.setAdapter(w7hHomeBannerPageAdapter);
            this.mBinding.vpHomeBanner.setOffscreenPageLimit(0);
            ActivityW7hHomeBinding activityW7hHomeBinding = this.mBinding;
            activityW7hHomeBinding.indicator.setViewPager(activityW7hHomeBinding.vpHomeBanner, 0, arrayList.size());
            this.mBinding.indicator.setPageColor(CommonUtils.getColorById(R.color.color_D2C5BC));
            this.mBinding.indicator.setFillColor(CommonUtils.getColorById(R.color.color_BD8356));
            this.mBinding.indicator.setSnap(true);
            this.mBinding.indicator.setIndicatorStyle(3);
            this.mBinding.indicator.setRadius(ArmsUtils.dip2px(CommonUtils.getAppContext(), 3.0f));
            if (w7hHomeBannerPageAdapter.getDataList().size() > 1) {
                if (!z) {
                    this.mBinding.indicator.setVisibility(0);
                }
                Disposable disposable = this.bannerDisposable;
                if (disposable != null && !disposable.isDisposed()) {
                    this.bannerDisposable.dispose();
                    this.bannerDisposable = null;
                }
                this.bannerDisposable = Observable.interval(5000L, 5000L, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$$ExternalSyntheticLambda32
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) throws Exception {
                        this.f$0.lambda$refreshBannerList$54((Long) obj);
                    }
                });
            } else {
                this.mBinding.indicator.setVisibility(8);
                Disposable disposable2 = this.bannerDisposable;
                if (disposable2 != null && !disposable2.isDisposed()) {
                    this.bannerDisposable.dispose();
                    this.bannerDisposable = null;
                }
            }
            this.mBinding.ivPagerClose.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity.62
                final /* synthetic */ W7hHomeBannerPageAdapter val$bannerPageAdapter;

                public AnonymousClass62(W7hHomeBannerPageAdapter w7hHomeBannerPageAdapter2) {
                    w7hHomeBannerPageAdapter = w7hHomeBannerPageAdapter2;
                }

                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    List<D4shBannerData.BannerData> dataList = w7hHomeBannerPageAdapter.getDataList();
                    ArrayList arrayList2 = new ArrayList();
                    for (int i2 = 0; i2 < dataList.size(); i2++) {
                        D4shBannerData.BannerData bannerData2 = dataList.get(i2);
                        if (bannerData2 instanceof D4shBannerData.ServiceStatusData) {
                            arrayList2.add(new BannerStateCache(3, Integer.valueOf(((D4shBannerData.ServiceStatusData) bannerData2).getStatus())));
                        } else if (bannerData2 instanceof D4shBannerData.FreeActivity) {
                            D4shBannerData.FreeActivity freeActivity2 = (D4shBannerData.FreeActivity) bannerData2;
                            arrayList2.add(new BannerStateCache(1, freeActivity2.getUpdateDate(), Integer.valueOf(freeActivity2.getId())));
                        } else if (bannerData2 instanceof D4shBannerData.PromotionData) {
                            D4shBannerData.PromotionData promotionData2 = (D4shBannerData.PromotionData) bannerData2;
                            arrayList2.add(new BannerStateCache(2, promotionData2.getUpdateDate(), Integer.valueOf(promotionData2.getId())));
                        }
                    }
                    W7hHomeActivity.this.mBinding.flHomeBanner.setVisibility(8);
                    W7hHomeActivity.this.mBinding.indicator.setVisibility(8);
                    CommonUtils.addSysMap(Constants.W7H_PROMOTE_BANNER + UserInforUtils.getCurrentUserId(W7hHomeActivity.this.getActivity()) + W7hHomeActivity.this.w7hRecord.getDeviceId() + (W7hHomeActivity.this.w7hRecord.getCloudProduct() != null ? W7hHomeActivity.this.w7hRecord.getCloudProduct().getServiceId() : 0), new Gson().toJson(arrayList2, new TypeToken<List<BannerStateCache>>() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity.62.1
                        public AnonymousClass1() {
                        }
                    }.getType()));
                }

                /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$62$1 */
                public class AnonymousClass1 extends TypeToken<List<BannerStateCache>> {
                    public AnonymousClass1() {
                    }
                }
            });
            return;
        }
        this.mBinding.flHomeBanner.setVisibility(8);
        this.mBinding.indicator.setVisibility(8);
        Disposable disposable3 = this.bannerDisposable;
        if (disposable3 == null || disposable3.isDisposed()) {
            return;
        }
        this.bannerDisposable.dispose();
        this.bannerDisposable = null;
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$60 */
    public class AnonymousClass60 extends TypeToken<List<BannerStateCache>> {
        public AnonymousClass60() {
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$61 */
    public class AnonymousClass61 implements W7hHomeBannerPageAdapter.OnItemClickListener {
        public AnonymousClass61() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.w7h.adapter.W7hHomeBannerPageAdapter.OnItemClickListener
        public void onBannerItemClick(int i2) {
            W7hUtils.getInstance().redirectToPurchasePage(W7hHomeActivity.this.getActivity(), W7hHomeActivity.this.w7hRecord, 29, W7hHomeActivity.this.deviceId);
        }

        @Override // com.petkit.android.activities.petkitBleDevice.w7h.adapter.W7hHomeBannerPageAdapter.OnItemClickListener
        public void redirectToPurchase(int i2, long j) {
            W7hUtils.getInstance().redirectToPurchasePage(W7hHomeActivity.this.getActivity(), W7hHomeActivity.this.w7hRecord, 29, W7hHomeActivity.this.deviceId);
        }
    }

    public /* synthetic */ void lambda$refreshBannerList$54(Long l) throws Exception {
        ViewPager viewPager = this.mBinding.vpHomeBanner;
        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$62 */
    public class AnonymousClass62 implements View.OnClickListener {
        final /* synthetic */ W7hHomeBannerPageAdapter val$bannerPageAdapter;

        public AnonymousClass62(W7hHomeBannerPageAdapter w7hHomeBannerPageAdapter2) {
            w7hHomeBannerPageAdapter = w7hHomeBannerPageAdapter2;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            List<D4shBannerData.BannerData> dataList = w7hHomeBannerPageAdapter.getDataList();
            ArrayList arrayList2 = new ArrayList();
            for (int i2 = 0; i2 < dataList.size(); i2++) {
                D4shBannerData.BannerData bannerData2 = dataList.get(i2);
                if (bannerData2 instanceof D4shBannerData.ServiceStatusData) {
                    arrayList2.add(new BannerStateCache(3, Integer.valueOf(((D4shBannerData.ServiceStatusData) bannerData2).getStatus())));
                } else if (bannerData2 instanceof D4shBannerData.FreeActivity) {
                    D4shBannerData.FreeActivity freeActivity2 = (D4shBannerData.FreeActivity) bannerData2;
                    arrayList2.add(new BannerStateCache(1, freeActivity2.getUpdateDate(), Integer.valueOf(freeActivity2.getId())));
                } else if (bannerData2 instanceof D4shBannerData.PromotionData) {
                    D4shBannerData.PromotionData promotionData2 = (D4shBannerData.PromotionData) bannerData2;
                    arrayList2.add(new BannerStateCache(2, promotionData2.getUpdateDate(), Integer.valueOf(promotionData2.getId())));
                }
            }
            W7hHomeActivity.this.mBinding.flHomeBanner.setVisibility(8);
            W7hHomeActivity.this.mBinding.indicator.setVisibility(8);
            CommonUtils.addSysMap(Constants.W7H_PROMOTE_BANNER + UserInforUtils.getCurrentUserId(W7hHomeActivity.this.getActivity()) + W7hHomeActivity.this.w7hRecord.getDeviceId() + (W7hHomeActivity.this.w7hRecord.getCloudProduct() != null ? W7hHomeActivity.this.w7hRecord.getCloudProduct().getServiceId() : 0), new Gson().toJson(arrayList2, new TypeToken<List<BannerStateCache>>() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity.62.1
                public AnonymousClass1() {
                }
            }.getType()));
        }

        /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$62$1 */
        public class AnonymousClass1 extends TypeToken<List<BannerStateCache>> {
            public AnonymousClass1() {
            }
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$63 */
    public class AnonymousClass63 implements Runnable {
        public final /* synthetic */ D4shBannerData val$d4shBannerData;
        public final /* synthetic */ Runnable val$next;

        public AnonymousClass63(D4shBannerData d4shBannerData, Runnable runnable) {
            d4shBannerData = d4shBannerData;
            runnable = runnable;
        }

        @Override // java.lang.Runnable
        public void run() {
            D4shBannerData.FreeActivity freeActivity = d4shBannerData.getFreeActivity();
            boolean zEquals = "zh_CN".equals(UserInforUtils.getLanguageLocaleString(CommonUtils.getAppContext()));
            if (!CommonUtils.getSysBoolMap(W7hHomeActivity.this.getActivity(), Constants.DEVICE_FREE_ACTIVITY + W7hHomeActivity.this.w7hRecord.getDeviceId() + "&" + W7hHomeActivity.this.deviceType, false)) {
                if (W7hHomeActivity.this.cloudServiceFreeTrialDialog == null || !W7hHomeActivity.this.cloudServiceFreeTrialDialog.isShowing()) {
                    W7hHomeActivity w7hHomeActivity = W7hHomeActivity.this;
                    Activity activity = W7hHomeActivity.this.getActivity();
                    D4shBannerData.ImageRet imageRet = freeActivity.getImageRet();
                    w7hHomeActivity.cloudServiceFreeTrialDialog = new CloudServiceFreeTrialDialog(activity, zEquals ? imageRet.getPopChineseImage() : imageRet.getPopEnglishImage(), new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity.63.1
                        public AnonymousClass1() {
                        }

                        @Override // android.view.View.OnClickListener
                        public void onClick(View view) {
                            if (W7hHomeActivity.this.w7hRecord.getRealDeviceShared() != null || FamilyUtils.getInstance().checkIsSharedDevice(W7hHomeActivity.this.getActivity(), W7hHomeActivity.this.deviceId, 29)) {
                                PetkitToast.showTopToast(W7hHomeActivity.this.getActivity(), W7hHomeActivity.this.getResources().getString(R.string.D4sh_no_cloud_free_tips), R.drawable.top_toast_warn_icon, 1);
                                return;
                            }
                            CommonUtils.addSysBoolMap(W7hHomeActivity.this.getActivity(), Constants.DEVICE_FREE_ACTIVITY + W7hHomeActivity.this.w7hRecord.getDeviceId() + "&" + W7hHomeActivity.this.deviceType, true);
                            W7hUtils.getInstance().redirectToPurchasePage(W7hHomeActivity.this.getActivity(), W7hHomeActivity.this.w7hRecord, 29, W7hHomeActivity.this.deviceId);
                            W7hHomeActivity.this.cloudServiceFreeTrialDialog.dismiss();
                            runnable.run();
                        }
                    });
                    W7hHomeActivity.this.cloudServiceFreeTrialDialog.getContentView().findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity.63.2
                        public AnonymousClass2() {
                        }

                        @Override // android.view.View.OnClickListener
                        public void onClick(View view) {
                            CommonUtils.addSysBoolMap(W7hHomeActivity.this.getActivity(), Constants.DEVICE_FREE_ACTIVITY + W7hHomeActivity.this.w7hRecord.getDeviceId() + "&" + W7hHomeActivity.this.deviceType, true);
                            W7hHomeActivity.this.cloudServiceFreeTrialDialog.dismiss();
                            runnable.run();
                        }
                    });
                    W7hHomeActivity.this.cloudServiceFreeTrialDialog.setBackgroundDrawable(new BitmapDrawable());
                    W7hHomeActivity.this.cloudServiceFreeTrialDialog.setOutsideTouchable(true);
                    W7hHomeActivity.this.cloudServiceFreeTrialDialog.setTouchable(true);
                    W7hHomeActivity.this.cloudServiceFreeTrialDialog.setAnimationStyle(R.style.PopupWindow_Default_Appearance_Animation);
                    W7hHomeActivity.this.cloudServiceFreeTrialDialog.showAtLocation(W7hHomeActivity.this.getActivity().getWindow().getDecorView(), 17, 0, 0);
                    return;
                }
                return;
            }
            runnable.run();
        }

        /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$63$1 */
        public class AnonymousClass1 implements View.OnClickListener {
            public AnonymousClass1() {
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (W7hHomeActivity.this.w7hRecord.getRealDeviceShared() != null || FamilyUtils.getInstance().checkIsSharedDevice(W7hHomeActivity.this.getActivity(), W7hHomeActivity.this.deviceId, 29)) {
                    PetkitToast.showTopToast(W7hHomeActivity.this.getActivity(), W7hHomeActivity.this.getResources().getString(R.string.D4sh_no_cloud_free_tips), R.drawable.top_toast_warn_icon, 1);
                    return;
                }
                CommonUtils.addSysBoolMap(W7hHomeActivity.this.getActivity(), Constants.DEVICE_FREE_ACTIVITY + W7hHomeActivity.this.w7hRecord.getDeviceId() + "&" + W7hHomeActivity.this.deviceType, true);
                W7hUtils.getInstance().redirectToPurchasePage(W7hHomeActivity.this.getActivity(), W7hHomeActivity.this.w7hRecord, 29, W7hHomeActivity.this.deviceId);
                W7hHomeActivity.this.cloudServiceFreeTrialDialog.dismiss();
                runnable.run();
            }
        }

        /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$63$2 */
        public class AnonymousClass2 implements View.OnClickListener {
            public AnonymousClass2() {
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CommonUtils.addSysBoolMap(W7hHomeActivity.this.getActivity(), Constants.DEVICE_FREE_ACTIVITY + W7hHomeActivity.this.w7hRecord.getDeviceId() + "&" + W7hHomeActivity.this.deviceType, true);
                W7hHomeActivity.this.cloudServiceFreeTrialDialog.dismiss();
                runnable.run();
            }
        }
    }

    private void showFreeActivity(D4shBannerData d4shBannerData, Runnable runnable) {
        if (runnable == null) {
            return;
        }
        this.handler.postDelayed(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity.63
            public final /* synthetic */ D4shBannerData val$d4shBannerData;
            public final /* synthetic */ Runnable val$next;

            public AnonymousClass63(D4shBannerData d4shBannerData2, Runnable runnable2) {
                d4shBannerData = d4shBannerData2;
                runnable = runnable2;
            }

            @Override // java.lang.Runnable
            public void run() {
                D4shBannerData.FreeActivity freeActivity = d4shBannerData.getFreeActivity();
                boolean zEquals = "zh_CN".equals(UserInforUtils.getLanguageLocaleString(CommonUtils.getAppContext()));
                if (!CommonUtils.getSysBoolMap(W7hHomeActivity.this.getActivity(), Constants.DEVICE_FREE_ACTIVITY + W7hHomeActivity.this.w7hRecord.getDeviceId() + "&" + W7hHomeActivity.this.deviceType, false)) {
                    if (W7hHomeActivity.this.cloudServiceFreeTrialDialog == null || !W7hHomeActivity.this.cloudServiceFreeTrialDialog.isShowing()) {
                        W7hHomeActivity w7hHomeActivity = W7hHomeActivity.this;
                        Activity activity = W7hHomeActivity.this.getActivity();
                        D4shBannerData.ImageRet imageRet = freeActivity.getImageRet();
                        w7hHomeActivity.cloudServiceFreeTrialDialog = new CloudServiceFreeTrialDialog(activity, zEquals ? imageRet.getPopChineseImage() : imageRet.getPopEnglishImage(), new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity.63.1
                            public AnonymousClass1() {
                            }

                            @Override // android.view.View.OnClickListener
                            public void onClick(View view) {
                                if (W7hHomeActivity.this.w7hRecord.getRealDeviceShared() != null || FamilyUtils.getInstance().checkIsSharedDevice(W7hHomeActivity.this.getActivity(), W7hHomeActivity.this.deviceId, 29)) {
                                    PetkitToast.showTopToast(W7hHomeActivity.this.getActivity(), W7hHomeActivity.this.getResources().getString(R.string.D4sh_no_cloud_free_tips), R.drawable.top_toast_warn_icon, 1);
                                    return;
                                }
                                CommonUtils.addSysBoolMap(W7hHomeActivity.this.getActivity(), Constants.DEVICE_FREE_ACTIVITY + W7hHomeActivity.this.w7hRecord.getDeviceId() + "&" + W7hHomeActivity.this.deviceType, true);
                                W7hUtils.getInstance().redirectToPurchasePage(W7hHomeActivity.this.getActivity(), W7hHomeActivity.this.w7hRecord, 29, W7hHomeActivity.this.deviceId);
                                W7hHomeActivity.this.cloudServiceFreeTrialDialog.dismiss();
                                runnable.run();
                            }
                        });
                        W7hHomeActivity.this.cloudServiceFreeTrialDialog.getContentView().findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity.63.2
                            public AnonymousClass2() {
                            }

                            @Override // android.view.View.OnClickListener
                            public void onClick(View view) {
                                CommonUtils.addSysBoolMap(W7hHomeActivity.this.getActivity(), Constants.DEVICE_FREE_ACTIVITY + W7hHomeActivity.this.w7hRecord.getDeviceId() + "&" + W7hHomeActivity.this.deviceType, true);
                                W7hHomeActivity.this.cloudServiceFreeTrialDialog.dismiss();
                                runnable.run();
                            }
                        });
                        W7hHomeActivity.this.cloudServiceFreeTrialDialog.setBackgroundDrawable(new BitmapDrawable());
                        W7hHomeActivity.this.cloudServiceFreeTrialDialog.setOutsideTouchable(true);
                        W7hHomeActivity.this.cloudServiceFreeTrialDialog.setTouchable(true);
                        W7hHomeActivity.this.cloudServiceFreeTrialDialog.setAnimationStyle(R.style.PopupWindow_Default_Appearance_Animation);
                        W7hHomeActivity.this.cloudServiceFreeTrialDialog.showAtLocation(W7hHomeActivity.this.getActivity().getWindow().getDecorView(), 17, 0, 0);
                        return;
                    }
                    return;
                }
                runnable.run();
            }

            /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$63$1 */
            public class AnonymousClass1 implements View.OnClickListener {
                public AnonymousClass1() {
                }

                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (W7hHomeActivity.this.w7hRecord.getRealDeviceShared() != null || FamilyUtils.getInstance().checkIsSharedDevice(W7hHomeActivity.this.getActivity(), W7hHomeActivity.this.deviceId, 29)) {
                        PetkitToast.showTopToast(W7hHomeActivity.this.getActivity(), W7hHomeActivity.this.getResources().getString(R.string.D4sh_no_cloud_free_tips), R.drawable.top_toast_warn_icon, 1);
                        return;
                    }
                    CommonUtils.addSysBoolMap(W7hHomeActivity.this.getActivity(), Constants.DEVICE_FREE_ACTIVITY + W7hHomeActivity.this.w7hRecord.getDeviceId() + "&" + W7hHomeActivity.this.deviceType, true);
                    W7hUtils.getInstance().redirectToPurchasePage(W7hHomeActivity.this.getActivity(), W7hHomeActivity.this.w7hRecord, 29, W7hHomeActivity.this.deviceId);
                    W7hHomeActivity.this.cloudServiceFreeTrialDialog.dismiss();
                    runnable.run();
                }
            }

            /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$63$2 */
            public class AnonymousClass2 implements View.OnClickListener {
                public AnonymousClass2() {
                }

                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    CommonUtils.addSysBoolMap(W7hHomeActivity.this.getActivity(), Constants.DEVICE_FREE_ACTIVITY + W7hHomeActivity.this.w7hRecord.getDeviceId() + "&" + W7hHomeActivity.this.deviceType, true);
                    W7hHomeActivity.this.cloudServiceFreeTrialDialog.dismiss();
                    runnable.run();
                }
            }
        }, 500L);
    }

    public void hideRightList() {
        if (this.isStartAnim || this.mBinding.rlMyLayout.getVisibility() == 8) {
            return;
        }
        startAnim(false);
        this.showList = false;
    }

    private void startAnim(boolean z) {
        Animation animationLoadAnimation;
        if (z) {
            animationLoadAnimation = AnimationUtils.loadAnimation(this, R.anim.menu_slide_in_from_right);
        } else {
            animationLoadAnimation = AnimationUtils.loadAnimation(this, R.anim.menu_slide_out_to_right);
        }
        animationLoadAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity.64
            final /* synthetic */ boolean val$start;

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }

            public AnonymousClass64(boolean z2) {
                z = z2;
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
                W7hHomeActivity.this.isStartAnim = true;
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                W7hHomeActivity.this.isStartAnim = false;
                if (z) {
                    return;
                }
                W7hHomeActivity.this.mBinding.rlMyLayout.clearAnimation();
                W7hHomeActivity.this.mBinding.rlMyLayout.setVisibility(8);
            }
        });
        this.mBinding.rlMyLayout.startAnimation(animationLoadAnimation);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity$64 */
    public class AnonymousClass64 implements Animation.AnimationListener {
        final /* synthetic */ boolean val$start;

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationRepeat(Animation animation) {
        }

        public AnonymousClass64(boolean z2) {
            z = z2;
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationStart(Animation animation) {
            W7hHomeActivity.this.isStartAnim = true;
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationEnd(Animation animation) {
            W7hHomeActivity.this.isStartAnim = false;
            if (z) {
                return;
            }
            W7hHomeActivity.this.mBinding.rlMyLayout.clearAnimation();
            W7hHomeActivity.this.mBinding.rlMyLayout.setVisibility(8);
        }
    }

    public void showBottomList() {
        if (this.isStartAnim || this.mBinding.rlMyLayout.getVisibility() == 0) {
            return;
        }
        this.mBinding.rlMyLayout.setVisibility(0);
        startAnim(true);
        this.showList = true;
    }

    @Subscriber
    public void serviceUpdate(ServiceUpdateEvent serviceUpdateEvent) {
        if (isFinishing() || this.mPresenter == 0) {
            return;
        }
        Intent intent = new Intent(W7hUtils.BROADCAST_W7H_STATE_MSG);
        intent.putExtra(Constants.EXTRA_DEVICE_ID, this.deviceId);
        intent.putExtra(Constants.EXTRA_BOOLEAN, true);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        ((W7hHomePresenter) this.mPresenter).initDeviceDetail();
    }
}

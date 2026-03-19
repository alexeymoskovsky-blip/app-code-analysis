package com.petkit.android.activities.home;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.core.view.DisplayCompat;
import androidx.core.view.GravityCompat;
import androidx.exifinterface.media.ExifInterface;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.alibaba.fastjson.JSON;
import com.binioter.guideview.Guide;
import com.binioter.guideview.GuideBuilder;
import com.facebook.stetho.dumpapp.Framer;
import com.google.android.material.appbar.AppBarLayout;
import com.google.common.base.Ascii;
import com.google.gson.Gson;
import com.gyf.immersionbar.ImmersionBar;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.Consts;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.utils.DeviceUtils;
import com.jess.arms.widget.InterceptViewPager;
import com.jess.arms.widget.LoadDialog;
import com.jess.arms.widget.PetkitToast;
import com.orm.SugarRecord;
import com.orm.query.Condition;
import com.orm.query.Select;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.card.mode.PurchaseEligibilityInfo;
import com.petkit.android.activities.cloudservice.CloudServiceWebViewActivity;
import com.petkit.android.activities.cloudservice.utils.CloudServiceUtils;
import com.petkit.android.activities.common.utils.DeviceCenterUtils;
import com.petkit.android.activities.community.WebviewActivity;
import com.petkit.android.activities.cozy.CozyHomeActivity;
import com.petkit.android.activities.cozy.mode.CozyRecord;
import com.petkit.android.activities.cozy.utils.CozyUtils;
import com.petkit.android.activities.cozy.widget.EasyPopupWindow;
import com.petkit.android.activities.d2.D2HomeActivity;
import com.petkit.android.activities.d2.mode.D2Record;
import com.petkit.android.activities.d2.utils.D2Utils;
import com.petkit.android.activities.device.DeviceFeedPlansMainActivity;
import com.petkit.android.activities.device.DeviceSetInfoActivity;
import com.petkit.android.activities.device.mode.DeviceInfos;
import com.petkit.android.activities.device.widget.ThreeChoicesWindow;
import com.petkit.android.activities.doctor.PetErrorListActivity;
import com.petkit.android.activities.doctor.mode.MedicalConversionItem;
import com.petkit.android.activities.doctor.mode.PetErrorInfo;
import com.petkit.android.activities.doctor.utils.DoctorUtils;
import com.petkit.android.activities.family.FamilyListActivity;
import com.petkit.android.activities.family.mode.FamilyInfor;
import com.petkit.android.activities.family.mode.WallPagerEvent;
import com.petkit.android.activities.family.mode.WallPagerInfo;
import com.petkit.android.activities.feed.FeedCalculatorActivity;
import com.petkit.android.activities.feed.HealthyFeedActivity;
import com.petkit.android.activities.feed.PetsFeedListActivity;
import com.petkit.android.activities.feeder.FeederHomeActivity;
import com.petkit.android.activities.feeder.FeederPlanPromptActivity;
import com.petkit.android.activities.feeder.Utils.FeederUtils;
import com.petkit.android.activities.feeder.model.FeederRecord;
import com.petkit.android.activities.fit.PetkitDetailActivity;
import com.petkit.android.activities.go.GoHomeActivity;
import com.petkit.android.activities.go.GoWalkingActivity;
import com.petkit.android.activities.go.model.GoRecord;
import com.petkit.android.activities.go.model.GoWalkData;
import com.petkit.android.activities.go.setting.GoSettingTargetActivity;
import com.petkit.android.activities.go.utils.GoDataUtils;
import com.petkit.android.activities.home.adapter.HomeBannerPageAdapter;
import com.petkit.android.activities.home.adapter.HomePageDeviceAdapter;
import com.petkit.android.activities.home.adapter.HomePageLargeDeviceAdapter;
import com.petkit.android.activities.home.adapter.HomePageToolAdapter;
import com.petkit.android.activities.home.adapter.HomeTopCardPageAdapter;
import com.petkit.android.activities.home.adapter.PetErrorItemAdapter;
import com.petkit.android.activities.home.adapter.PurchaseEligibilityItemAdapter;
import com.petkit.android.activities.home.adapter.model.HomeDeviceData;
import com.petkit.android.activities.home.adapter.model.NewHomeCardData;
import com.petkit.android.activities.home.adapter.model.ToDoBean;
import com.petkit.android.activities.home.component.DaggerHomePageComponent;
import com.petkit.android.activities.home.contract.HomePageContract;
import com.petkit.android.activities.home.mode.CardRankResult;
import com.petkit.android.activities.home.mode.HomeCardData;
import com.petkit.android.activities.home.mode.ScrollEvent;
import com.petkit.android.activities.home.mode.YzNewInforEvent;
import com.petkit.android.activities.home.module.HomePageModule;
import com.petkit.android.activities.home.presenter.HomePagePresenter;
import com.petkit.android.activities.home.utils.BlurUtils;
import com.petkit.android.activities.home.utils.DeleteItemComponent;
import com.petkit.android.activities.home.utils.GuidePetTipAndBtnAndLineComponent;
import com.petkit.android.activities.home.utils.GuideTipAndBtnAndLineComponent;
import com.petkit.android.activities.home.utils.HomeCardDataUtils;
import com.petkit.android.activities.home.utils.HomeGuideUtils;
import com.petkit.android.activities.home.widget.MyRefreshView;
import com.petkit.android.activities.mall.mode.CTW3ProductsInfor;
import com.petkit.android.activities.mate.MateDownloadActivity;
import com.petkit.android.activities.mate.utils.HsConsts;
import com.petkit.android.activities.permission.PermissionDialogActivity;
import com.petkit.android.activities.pet.PetItemTouchHelperCallback;
import com.petkit.android.activities.petkitBleDevice.BleDeviceBindCompleteActivity;
import com.petkit.android.activities.petkitBleDevice.K2HomeActivity;
import com.petkit.android.activities.petkitBleDevice.T3HomeActivity;
import com.petkit.android.activities.petkitBleDevice.aq.AqHomeActivity;
import com.petkit.android.activities.petkitBleDevice.aq.mode.AqRecord;
import com.petkit.android.activities.petkitBleDevice.aq.utils.AqUtils;
import com.petkit.android.activities.petkitBleDevice.aq1s.Aq1sHomeActivity;
import com.petkit.android.activities.petkitBleDevice.aqh1.Aqh1HomeActivity;
import com.petkit.android.activities.petkitBleDevice.aqh1.utils.Aqh1Utils;
import com.petkit.android.activities.petkitBleDevice.aqr.AqrHomeActivity;
import com.petkit.android.activities.petkitBleDevice.aqr.utils.AqrUtils;
import com.petkit.android.activities.petkitBleDevice.bind.NearByDeviceActivity;
import com.petkit.android.activities.petkitBleDevice.ble.PetkitBleDeviceManager;
import com.petkit.android.activities.petkitBleDevice.ble.mode.HgDataConvertor;
import com.petkit.android.activities.petkitBleDevice.ctw3.CTW3FilterRemainingActivity;
import com.petkit.android.activities.petkitBleDevice.ctw3.CTW3HomeActivity;
import com.petkit.android.activities.petkitBleDevice.ctw3.mode.CTW3Record;
import com.petkit.android.activities.petkitBleDevice.ctw3.utils.CTW3Utils;
import com.petkit.android.activities.petkitBleDevice.d3.D3HomeActivity;
import com.petkit.android.activities.petkitBleDevice.d3.FeederConsumablesActivity;
import com.petkit.android.activities.petkitBleDevice.d3.mode.D3Record;
import com.petkit.android.activities.petkitBleDevice.d3.utils.D3Utils;
import com.petkit.android.activities.petkitBleDevice.d3.widget.D3ManualWindow;
import com.petkit.android.activities.petkitBleDevice.d4.D4HomeActivity;
import com.petkit.android.activities.petkitBleDevice.d4.mode.D4Record;
import com.petkit.android.activities.petkitBleDevice.d4.utils.D4Utils;
import com.petkit.android.activities.petkitBleDevice.d4.widget.D4ManualWindow;
import com.petkit.android.activities.petkitBleDevice.d4s.D4sHomeActivity;
import com.petkit.android.activities.petkitBleDevice.d4s.mode.D4sRecord;
import com.petkit.android.activities.petkitBleDevice.d4s.utils.D4sUtils;
import com.petkit.android.activities.petkitBleDevice.d4s.widget.D4sManualWindow;
import com.petkit.android.activities.petkitBleDevice.d4sh.D4shHomeActivity;
import com.petkit.android.activities.petkitBleDevice.d4sh.D4shWorkingTimeActivity;
import com.petkit.android.activities.petkitBleDevice.d4sh.mode.D4shRecord;
import com.petkit.android.activities.petkitBleDevice.d4sh.utils.D4shUtils;
import com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4hManualWindow;
import com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shManualWindow;
import com.petkit.android.activities.petkitBleDevice.hg.HgHomeActivity;
import com.petkit.android.activities.petkitBleDevice.hg.mode.HgRecord;
import com.petkit.android.activities.petkitBleDevice.hg.utils.HgUtils;
import com.petkit.android.activities.petkitBleDevice.k3.K3HomeActivity;
import com.petkit.android.activities.petkitBleDevice.k3.utils.K3Utils;
import com.petkit.android.activities.petkitBleDevice.mode.T3Record;
import com.petkit.android.activities.petkitBleDevice.mode.TempUnitChangeEvent;
import com.petkit.android.activities.petkitBleDevice.p3.P3MainActivity;
import com.petkit.android.activities.petkitBleDevice.p3.utils.P3Utils;
import com.petkit.android.activities.petkitBleDevice.r2.R2HomeActivity;
import com.petkit.android.activities.petkitBleDevice.r2.utils.R2Utils;
import com.petkit.android.activities.petkitBleDevice.r2.widget.NewIKnowWindow;
import com.petkit.android.activities.petkitBleDevice.t4.T4ConsumablesActivity;
import com.petkit.android.activities.petkitBleDevice.t4.T4HomeActivity;
import com.petkit.android.activities.petkitBleDevice.t4.mode.RelatedProductsInfor;
import com.petkit.android.activities.petkitBleDevice.t4.mode.T4Record;
import com.petkit.android.activities.petkitBleDevice.t4.utils.T4Utils;
import com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity;
import com.petkit.android.activities.petkitBleDevice.t6.T6ConsumablesActivity;
import com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity;
import com.petkit.android.activities.petkitBleDevice.t6.T6InitHomeModeActivity;
import com.petkit.android.activities.petkitBleDevice.t6.T6WorkingTimeActivity;
import com.petkit.android.activities.petkitBleDevice.t6.mode.T6Record;
import com.petkit.android.activities.petkitBleDevice.t6.utils.T6Utils;
import com.petkit.android.activities.petkitBleDevice.t7.T7ConsumableActivity;
import com.petkit.android.activities.petkitBleDevice.t7.T7HomeActivity;
import com.petkit.android.activities.petkitBleDevice.t7.T7WorkingTimeActivity;
import com.petkit.android.activities.petkitBleDevice.t7.mode.T7Record;
import com.petkit.android.activities.petkitBleDevice.t7.utils.T7DataUtils;
import com.petkit.android.activities.petkitBleDevice.t7.widget.FunctionDialog;
import com.petkit.android.activities.petkitBleDevice.utils.ActivityUtils;
import com.petkit.android.activities.petkitBleDevice.utils.BleDeviceUtils;
import com.petkit.android.activities.petkitBleDevice.utils.FamilyUtils;
import com.petkit.android.activities.petkitBleDevice.utils.PetKitClickUtils;
import com.petkit.android.activities.petkitBleDevice.w5.W5FilterRemainingActivity;
import com.petkit.android.activities.petkitBleDevice.w5.W5HomeActivity;
import com.petkit.android.activities.petkitBleDevice.w5.guide.ConfirmListener;
import com.petkit.android.activities.petkitBleDevice.w5.guide.GuideParam;
import com.petkit.android.activities.petkitBleDevice.w5.guide.HomeQuickActionListener;
import com.petkit.android.activities.petkitBleDevice.w5.mode.W5Record;
import com.petkit.android.activities.petkitBleDevice.w5.utils.W5Utils;
import com.petkit.android.activities.petkitBleDevice.w7h.W7hCameraPeriodActivity;
import com.petkit.android.activities.petkitBleDevice.w7h.W7hConsumablesActivity;
import com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity;
import com.petkit.android.activities.petkitBleDevice.w7h.mode.W7hRecord;
import com.petkit.android.activities.petkitBleDevice.w7h.utils.W7hDataUtils;
import com.petkit.android.activities.petkitBleDevice.widget.SimpleTipWindow;
import com.petkit.android.activities.remind.HealthRemindActivity2_0;
import com.petkit.android.activities.statistics.ToiletStatisticsActivity;
import com.petkit.android.activities.statistics.WeightStatisticsActivity;
import com.petkit.android.activities.universalWindow.FamilySwitchWindow;
import com.petkit.android.activities.universalWindow.NormalCenterTipWindow;
import com.petkit.android.activities.universalWindow.NormalListChoiceCenterWindow;
import com.petkit.android.activities.virtual.ctw3.VirtualCTW3HomeActivity;
import com.petkit.android.activities.virtual.d3.VirtualD3HomeActivity;
import com.petkit.android.activities.virtual.d4sh.VirtualD4shHomeActivity;
import com.petkit.android.activities.virtual.hg.VirtualHgHomeActivity;
import com.petkit.android.activities.virtual.mode.DeviceRegularDataResult;
import com.petkit.android.activities.virtual.t4.VirtualT4HomeActivity;
import com.petkit.android.activities.virtual.t6.VirtualT6HomeActivity;
import com.petkit.android.activities.virtual.w5.VirtualW5HomeActivity;
import com.petkit.android.activities.walkdog.WalkHomeActivity;
import com.petkit.android.activities.walkdog.WalkingActivity;
import com.petkit.android.activities.walkdog.model.WalkData;
import com.petkit.android.activities.walkdog.setting.WalkSettingTargetActivity;
import com.petkit.android.activities.walkdog.utils.WalkDataUtils;
import com.petkit.android.api.PetkitCallback;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.apiResponse.AppBanner;
import com.petkit.android.api.http.apiResponse.ErrorInfor;
import com.petkit.android.api.http.apiResponse.HomeDeviceListRsp;
import com.petkit.android.api.http.apiResponse.UnReadStatusRsp;
import com.petkit.android.ble.StopDeviceScanMsg;
import com.petkit.android.model.ChatCenter;
import com.petkit.android.model.MateDevice;
import com.petkit.android.model.Pet;
import com.petkit.android.utils.ChatUtils;
import com.petkit.android.utils.CommonUtil;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.DateUtil;
import com.petkit.android.utils.GrayLevelUtil;
import com.petkit.android.utils.LogcatStorageHelper;
import com.petkit.android.utils.MyGlideTask;
import com.petkit.android.utils.PetUtils;
import com.petkit.android.utils.PetkitLog;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.android.widget.CirclePageIndicator;
import com.petkit.oversea.R;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.shopify.sample.util.MallUtils;
import io.agora.rtc2.internal.AudioDeviceInventoryLowerThanM;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import kotlinx.coroutines.internal.LockFreeTaskQueueCore;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

/* JADX INFO: loaded from: classes4.dex */
@SuppressLint({"NonConstantResourceId"})
public class HomePageFragment extends BaseFragment<HomePagePresenter> implements View.OnClickListener, HomePageContract.View, HomePageToolAdapter.OnClickListener, HomeTopCardPageAdapter.OnItemClickListener, NewCardOnClickListener, HomeBannerPageAdapter.OnItemClickListener, FamilySwitchWindow.FamilySwitchOnClickListener {
    private AppBarLayout appBar;
    private Bitmap bitmap;
    private int cardMode;
    private View consView;
    private Disposable d;
    private D3ManualWindow d3ManualFeedWindow;
    private D4ManualWindow d4ManualFeedWindow;
    private D4hManualWindow d4hManualFeedWindow;
    private D4sManualWindow d4sManualFeedWindow;
    private D4shManualWindow d4shManualFeedWindow;
    private List<NewHomeCardData> deviceDataListLarge;
    private InterceptViewPager discoverPager;
    private Disposable disposable;
    private long familyId;
    private GridLayoutManager gridLayoutManager;
    private boolean guideLarge;
    private int halfHeight;
    private int height;
    private List<NewHomeCardData> homeCardDataList;
    private NewIKnowWindow homeModeWindowWithListner;
    private HomePageDeviceAdapter homePageDeviceAdapter;
    private HomePageLargeDeviceAdapter homePageLargeDeviceAdapter;
    private HomePageListener homePageListener;
    NewIKnowWindow iKnowWindow;
    private ImageButton ibAddDevice;
    private ImageButton ibHistoryMsg;
    private ImageButton ibTopTitleAddDevice;
    private ImageButton ibTopTitleHistoryMsg;
    private CirclePageIndicator indicator;
    private boolean isAutoRefresh;
    private boolean isChangePosition;
    private boolean isDrag;
    private boolean isTop;
    private ImageView ivAddDevice;
    private ImageView ivBannerClose;
    private ImageView ivBlurBg;
    private ImageView ivHomeWallPager;
    private ImageView ivSwiftCardMode;
    private GridLayoutManager largeGridLayoutManager;
    private View lineToReset;
    private LinearLayout llControl;
    private LinearLayout llEmptyPanel;
    private LinearLayout llMemberEmptyView;
    private List<NewHomeCardData> newHomeCardDataList;
    private Pet pet;
    PetErrorItemAdapter petErrorItemAdapter;
    NormalCenterTipWindow petErrorNoRemindWindow;
    PurchaseEligibilityItemAdapter purchaseEligibilityItemAdapter;
    private HomeDeviceListRsp.HomeDeviceListResult result;
    private RelativeLayout rlAdPanel;
    private RelativeLayout rlAddDevice;
    private RelativeLayout rlMask;
    private RelativeLayout rlParentPanel;
    private RelativeLayout rlPetError;
    private RelativeLayout rlTitlePanel;
    private RelativeLayout rlTodoItem;
    private RelativeLayout rlTopTitlePanel;
    private FrameLayout rootPanel;
    private RecyclerView rvDevice;
    private RecyclerView rvPetError;
    private RecyclerView rvPurchaseEligibility;
    private int sX;
    private int sY;
    private float scale;
    private boolean showFamilyName;
    private long showTime;
    private MyRefreshView srl;
    private ThreeChoicesWindow threeChoicesWindow;
    private View transparentMaskView;
    private TextView tvContent;
    private TextView tvNotRemind;
    private TextView tvNotRemind3Days;
    private TextView tvPetErrorTip;
    private TextView tvPrompt;
    private TextView tvRemainTime;
    private TextView tvTitle;
    private TextView tvTitleShare;
    private TextView tvToReset;
    private TextView tvTopTitle;
    private TextView tvTopTitleShare;
    private int width;
    private boolean isRefresh = false;
    private String familyName = "";
    private String homeWallPager = "";
    private boolean isShowOneGuide = false;
    private int current = 0;
    private Guide guide = null;
    private Guide thirdGuide = null;
    private Guide fourthGuide = null;
    private Guide fourthGuideLarge = null;
    boolean deviceListReady = false;
    long lastTime = 0;
    boolean isSwitchCardMode = true;
    private long clickToDeviceHomeTime = 0;
    private Guide deleteGuide = null;

    public interface HomePageListener {
        void changeTheme(int i, long j);

        void dismiss(boolean z);

        void redPointDismiss();
    }

    public static /* synthetic */ void lambda$showCancelD3FeedingDialog$29(DialogInterface dialogInterface, int i) {
    }

    public static /* synthetic */ void lambda$showCancelD4FeedingDialog$25(DialogInterface dialogInterface, int i) {
    }

    public static /* synthetic */ void lambda$showCancelD4hFeedingDialog$31(DialogInterface dialogInterface, int i) {
    }

    public static /* synthetic */ void lambda$showCancelD4sFeedingDialog$21(DialogInterface dialogInterface, int i) {
    }

    public static /* synthetic */ void lambda$showCancelD4shFeedingDialog$27(DialogInterface dialogInterface, int i) {
    }

    public static /* synthetic */ void lambda$showD3ManualFeedWindow$17(int i, int i2, int i3) {
    }

    public static /* synthetic */ void lambda$showD4ManualFeedWindow$18(int i, int i2, int i3) {
    }

    public static /* synthetic */ void lambda$showD4hManualFeedWindow$23(int i, int i2, int i3, int i4) {
    }

    public static /* synthetic */ void lambda$showD4sManualFeedWindow$19(int i, int i2, int i3, int i4) {
    }

    public static /* synthetic */ void lambda$showD4shManualFeedWindow$22(int i, int i2, int i3, int i4) {
    }

    private void showCameraWindow(HomeDeviceData homeDeviceData) {
    }

    @Override // com.petkit.android.activities.universalWindow.FamilySwitchWindow.FamilySwitchOnClickListener
    public void onAllFamily() {
    }

    @Override // com.petkit.android.activities.home.contract.HomePageContract.View
    public void passYzMessageNum(int i) {
    }

    @Override // com.petkit.android.activities.home.contract.HomePageContract.View
    public void refreshFamilyView() {
    }

    @Override // com.petkit.android.activities.home.contract.HomePageContract.View
    public void refreshTopCard(HomeCardData homeCardData) {
    }

    @Subscriber
    public void refreshYzNewInfor(YzNewInforEvent yzNewInforEvent) {
    }

    @Override // com.petkit.android.activities.home.contract.HomePageContract.View
    public void resetGuideFlag() {
    }

    @Override // com.jess.arms.base.delegate.IFragment
    public void setData(Object obj) {
    }

    @Override // com.petkit.android.activities.home.contract.HomePageContract.View
    public void startLoad() {
    }

    @Override // com.jess.arms.base.BaseFragment, androidx.fragment.app.Fragment
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        ((HomePagePresenter) this.mPresenter).getUnReadStatus();
        checkMsgCount();
    }

    public static HomePageFragment newInstance() {
        return new HomePageFragment();
    }

    @Override // com.jess.arms.base.delegate.IFragment
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerHomePageComponent.builder().appComponent(appComponent).homePageModule(new HomePageModule(this)).build().inject(this);
    }

    @Override // com.jess.arms.base.delegate.IFragment
    public View initView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.cloneInContext(createThemedContext()).inflate(R.layout.fragment_home_page1, viewGroup, false);
        initViews(viewInflate);
        return viewInflate;
    }

    @Override // com.jess.arms.base.delegate.IFragment
    @RequiresApi(api = 23)
    @SuppressLint({"ClickableViewAccessibility"})
    public void initData(Bundle bundle) {
        this.halfHeight = getResources().getDisplayMetrics().heightPixels / 3;
        this.cardMode = CommonUtils.getSysIntMap(getActivity(), "HOME_CARD_MODE");
        this.guideLarge = CommonUtils.getSysBoolMap(CommonUtils.getAppContext(), Consts.GUIDE_HOME_NEW_CARD, false);
        if (getActivity() != null) {
            View viewFindViewById = getActivity().getWindow().getDecorView().findViewById(android.R.id.content);
            this.consView = viewFindViewById;
            viewFindViewById.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.petkit.android.activities.home.HomePageFragment.1
                public AnonymousClass1() {
                }

                @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                public void onGlobalLayout() {
                    HomePageFragment.this.consView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    HomePageFragment homePageFragment = HomePageFragment.this;
                    homePageFragment.width = homePageFragment.consView.getMeasuredWidth();
                    HomePageFragment homePageFragment2 = HomePageFragment.this;
                    homePageFragment2.height = homePageFragment2.consView.getMeasuredHeight();
                }
            });
        }
        this.pet = UserInforUtils.getPetById(PetUtils.ALL_DEVICE);
        refreshUserInfo();
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.rlAddDevice.getLayoutParams();
        layoutParams.width = (int) ((BaseApplication.displayMetrics.widthPixels - ArmsUtils.dip2px(getActivity(), 48.0f)) / 2.0f);
        this.rlAddDevice.setLayoutParams(layoutParams);
        ((HomePagePresenter) this.mPresenter).initParams(getContext());
        initSmartView();
        TextView textView = new TextView(getActivity());
        textView.setTextAppearance(getActivity(), R.style.New_Style_Title_28_Dark_Black);
        textView.setText(getResources().getString(R.string.Hello, ""));
        textView.setMaxLines(1);
        textView.measure(View.MeasureSpec.makeMeasureSpec(LockFreeTaskQueueCore.MAX_CAPACITY_MASK, Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(LockFreeTaskQueueCore.MAX_CAPACITY_MASK, Integer.MIN_VALUE));
        int measuredHeight = ((textView.getMeasuredHeight() / 2) - (ArmsUtils.dip2px(getActivity(), 26.0f) / 2)) + ArmsUtils.dip2px(getActivity(), 8.0f);
        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) this.ibAddDevice.getLayoutParams();
        layoutParams2.topMargin = measuredHeight;
        this.ibAddDevice.setLayoutParams(layoutParams2);
        RelativeLayout.LayoutParams layoutParams3 = (RelativeLayout.LayoutParams) this.ibHistoryMsg.getLayoutParams();
        layoutParams3.topMargin = measuredHeight;
        this.ibHistoryMsg.setLayoutParams(layoutParams3);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.home.HomePageFragment$1 */
    public class AnonymousClass1 implements ViewTreeObserver.OnGlobalLayoutListener {
        public AnonymousClass1() {
        }

        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public void onGlobalLayout() {
            HomePageFragment.this.consView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            HomePageFragment homePageFragment = HomePageFragment.this;
            homePageFragment.width = homePageFragment.consView.getMeasuredWidth();
            HomePageFragment homePageFragment2 = HomePageFragment.this;
            homePageFragment2.height = homePageFragment2.consView.getMeasuredHeight();
        }
    }

    @Override // com.petkit.android.activities.home.contract.HomePageContract.View
    public void refreshPurchaseEligibility(PurchaseEligibilityInfo purchaseEligibilityInfo) {
        if (!CommonUtils.getSysBoolMap(getActivity(), "HOME_PURCHASE_ELIGIBILITY", false) && purchaseEligibilityInfo != null && purchaseEligibilityInfo.getPurchaseEligibility() != null && purchaseEligibilityInfo.getPurchaseEligibility().size() == 1) {
            CommonUtils.addSysBoolMap(getActivity(), "HOME_PURCHASE_ELIGIBILITY", true);
            showPurchaseEligibilityWindow(purchaseEligibilityInfo.getPurchaseEligibility().get(0));
        }
        if (purchaseEligibilityInfo != null && purchaseEligibilityInfo.getActive().booleanValue() && purchaseEligibilityInfo.getPurchaseEligibility() != null && !purchaseEligibilityInfo.getPurchaseEligibility().isEmpty()) {
            this.rvPurchaseEligibility.setVisibility(0);
            this.rvPurchaseEligibility.setLayoutManager(new LinearLayoutManager(getActivity()));
            PurchaseEligibilityItemAdapter purchaseEligibilityItemAdapter = this.purchaseEligibilityItemAdapter;
            if (purchaseEligibilityItemAdapter == null) {
                PurchaseEligibilityItemAdapter purchaseEligibilityItemAdapter2 = new PurchaseEligibilityItemAdapter(getActivity(), purchaseEligibilityInfo.getPurchaseEligibility(), new PurchaseEligibilityItemAdapter.OnClickListener() { // from class: com.petkit.android.activities.home.HomePageFragment.2
                    public AnonymousClass2() {
                    }

                    @Override // com.petkit.android.activities.home.adapter.PurchaseEligibilityItemAdapter.OnClickListener
                    public void onSelect(PurchaseEligibilityInfo.PurchaseEligibility purchaseEligibility) {
                        HomePageFragment.this.showPurchaseEligibilityWindow(purchaseEligibility);
                    }
                });
                this.purchaseEligibilityItemAdapter = purchaseEligibilityItemAdapter2;
                this.rvPurchaseEligibility.setAdapter(purchaseEligibilityItemAdapter2);
                return;
            }
            purchaseEligibilityItemAdapter.setData(purchaseEligibilityInfo.getPurchaseEligibility());
            return;
        }
        this.rvPurchaseEligibility.setVisibility(8);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.home.HomePageFragment$2 */
    public class AnonymousClass2 implements PurchaseEligibilityItemAdapter.OnClickListener {
        public AnonymousClass2() {
        }

        @Override // com.petkit.android.activities.home.adapter.PurchaseEligibilityItemAdapter.OnClickListener
        public void onSelect(PurchaseEligibilityInfo.PurchaseEligibility purchaseEligibility) {
            HomePageFragment.this.showPurchaseEligibilityWindow(purchaseEligibility);
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.home.HomePageFragment$3 */
    public class AnonymousClass3 implements NormalListChoiceCenterWindow.OnClickThreeChoices {
        public final /* synthetic */ PurchaseEligibilityInfo.PurchaseEligibility val$item;

        @Override // com.petkit.android.activities.universalWindow.NormalListChoiceCenterWindow.OnClickThreeChoices
        public void onClickThirdChoice() {
        }

        public AnonymousClass3(PurchaseEligibilityInfo.PurchaseEligibility purchaseEligibility) {
            purchaseEligibility = purchaseEligibility;
        }

        @Override // com.petkit.android.activities.universalWindow.NormalListChoiceCenterWindow.OnClickThreeChoices
        public void onClickFirstChoice() {
            HomePageFragment homePageFragment = HomePageFragment.this;
            homePageFragment.launchActivity(CloudServiceWebViewActivity.newIntent(homePageFragment.getActivity(), "", CloudServiceUtils.getPurchaseEligibilityUrl(HomePageFragment.this.getActivity(), purchaseEligibility)));
        }

        /* JADX INFO: renamed from: com.petkit.android.activities.home.HomePageFragment$3$1 */
        public class AnonymousClass1 implements NormalCenterTipWindow.OnClickOk {
            @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
            public void onOkClick() {
            }

            public AnonymousClass1() {
            }

            @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
            public void onCancelClick() {
                ((HomePagePresenter) ((BaseFragment) HomePageFragment.this).mPresenter).purchaseEligibilityNoRemind(purchaseEligibility);
            }
        }

        @Override // com.petkit.android.activities.universalWindow.NormalListChoiceCenterWindow.OnClickThreeChoices
        public void onClickSecondChoice() {
            NormalCenterTipWindow normalCenterTipWindow = new NormalCenterTipWindow(HomePageFragment.this.getActivity(), new NormalCenterTipWindow.OnClickOk() { // from class: com.petkit.android.activities.home.HomePageFragment.3.1
                @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
                public void onOkClick() {
                }

                public AnonymousClass1() {
                }

                @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
                public void onCancelClick() {
                    ((HomePagePresenter) ((BaseFragment) HomePageFragment.this).mPresenter).purchaseEligibilityNoRemind(purchaseEligibility);
                }
            }, (String) null, HomePageFragment.this.getString(R.string.Claim_close_confirm));
            normalCenterTipWindow.setCancelButtonText(HomePageFragment.this.getString(R.string.Affirm_Close));
            normalCenterTipWindow.setOkButtonText(HomePageFragment.this.getString(R.string.Cancel));
            normalCenterTipWindow.show(HomePageFragment.this.getActivity().getWindow().getDecorView());
        }
    }

    public void showPurchaseEligibilityWindow(PurchaseEligibilityInfo.PurchaseEligibility purchaseEligibility) {
        NormalListChoiceCenterWindow normalListChoiceCenterWindow = new NormalListChoiceCenterWindow(getActivity(), new NormalListChoiceCenterWindow.OnClickThreeChoices() { // from class: com.petkit.android.activities.home.HomePageFragment.3
            public final /* synthetic */ PurchaseEligibilityInfo.PurchaseEligibility val$item;

            @Override // com.petkit.android.activities.universalWindow.NormalListChoiceCenterWindow.OnClickThreeChoices
            public void onClickThirdChoice() {
            }

            public AnonymousClass3(PurchaseEligibilityInfo.PurchaseEligibility purchaseEligibility2) {
                purchaseEligibility = purchaseEligibility2;
            }

            @Override // com.petkit.android.activities.universalWindow.NormalListChoiceCenterWindow.OnClickThreeChoices
            public void onClickFirstChoice() {
                HomePageFragment homePageFragment = HomePageFragment.this;
                homePageFragment.launchActivity(CloudServiceWebViewActivity.newIntent(homePageFragment.getActivity(), "", CloudServiceUtils.getPurchaseEligibilityUrl(HomePageFragment.this.getActivity(), purchaseEligibility)));
            }

            /* JADX INFO: renamed from: com.petkit.android.activities.home.HomePageFragment$3$1 */
            public class AnonymousClass1 implements NormalCenterTipWindow.OnClickOk {
                @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
                public void onOkClick() {
                }

                public AnonymousClass1() {
                }

                @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
                public void onCancelClick() {
                    ((HomePagePresenter) ((BaseFragment) HomePageFragment.this).mPresenter).purchaseEligibilityNoRemind(purchaseEligibility);
                }
            }

            @Override // com.petkit.android.activities.universalWindow.NormalListChoiceCenterWindow.OnClickThreeChoices
            public void onClickSecondChoice() {
                NormalCenterTipWindow normalCenterTipWindow = new NormalCenterTipWindow(HomePageFragment.this.getActivity(), new NormalCenterTipWindow.OnClickOk() { // from class: com.petkit.android.activities.home.HomePageFragment.3.1
                    @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
                    public void onOkClick() {
                    }

                    public AnonymousClass1() {
                    }

                    @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
                    public void onCancelClick() {
                        ((HomePagePresenter) ((BaseFragment) HomePageFragment.this).mPresenter).purchaseEligibilityNoRemind(purchaseEligibility);
                    }
                }, (String) null, HomePageFragment.this.getString(R.string.Claim_close_confirm));
                normalCenterTipWindow.setCancelButtonText(HomePageFragment.this.getString(R.string.Affirm_Close));
                normalCenterTipWindow.setOkButtonText(HomePageFragment.this.getString(R.string.Cancel));
                normalCenterTipWindow.show(HomePageFragment.this.getActivity().getWindow().getDecorView());
            }
        }, purchaseEligibility2.getReminderMsgTitle(), purchaseEligibility2.getReminderMsg(), getString(R.string.Claim_now), getString(R.string.Do_not_remind_again), null);
        normalListChoiceCenterWindow.setThreeChoicesTextColor(getResources().getColor(R.color.toilet_chart_color_nine), -1, -1);
        normalListChoiceCenterWindow.setTipGravity(GravityCompat.START);
        normalListChoiceCenterWindow.setWindowMargin(20, 20);
        normalListChoiceCenterWindow.setCloseIcon(true);
        normalListChoiceCenterWindow.show(getActivity().getWindow().getDecorView());
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        ((HomePagePresenter) this.mPresenter).onPause();
    }

    private void initSmartView() {
        this.appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() { // from class: com.petkit.android.activities.home.HomePageFragment$$ExternalSyntheticLambda69
            @Override // com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener, com.google.android.material.appbar.AppBarLayout.BaseOnOffsetChangedListener
            public final void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                this.f$0.lambda$initSmartView$1(appBarLayout, i);
            }
        });
        this.srl.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() { // from class: com.petkit.android.activities.home.HomePageFragment.4
            @Override // com.scwang.smart.refresh.layout.listener.OnLoadMoreListener
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
            }

            public AnonymousClass4() {
            }

            @Override // com.scwang.smart.refresh.layout.listener.OnRefreshListener
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                HomeCardDataUtils.getInstance().setIsNeedRefreshDeviceRoaster(HomePageFragment.this.getContext(), FamilyUtils.getInstance().getCurrentFamilyId(HomePageFragment.this.getContext()), true);
                HomePageFragment.this.isRefresh = true;
                if (((BaseFragment) HomePageFragment.this).mPresenter != null) {
                    ((HomePagePresenter) ((BaseFragment) HomePageFragment.this).mPresenter).getHomeDeviceList();
                    ((HomePagePresenter) ((BaseFragment) HomePageFragment.this).mPresenter).getForeignHeatSwitch();
                }
            }
        });
        initRecyclerViewEvent();
    }

    public /* synthetic */ void lambda$initSmartView$1(AppBarLayout appBarLayout, int i) {
        boolean z = this.appBar.getMeasuredHeight() + i <= 0;
        if (getActivity() != null) {
            ((HomeActivity) getActivity()).setTop(z);
        }
        boolean z2 = i == 0;
        this.isTop = z2;
        this.srl.setEnableRefresh(z2);
        if (this.isTop && this.isAutoRefresh) {
            this.isAutoRefresh = false;
            new Handler().postDelayed(new Runnable() { // from class: com.petkit.android.activities.home.HomePageFragment$$ExternalSyntheticLambda59
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$initSmartView$0();
                }
            }, 300L);
        }
        float f = (-i) / 250.0f;
        if (f < 0.0f) {
            f = 0.0f;
        }
        if (f > 0.9d) {
            f = 1.0f;
        }
        onScrollChange(f);
    }

    public /* synthetic */ void lambda$initSmartView$0() {
        this.srl.setStatusNone();
        this.srl.autoRefresh();
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.home.HomePageFragment$4 */
    public class AnonymousClass4 implements OnRefreshLoadMoreListener {
        @Override // com.scwang.smart.refresh.layout.listener.OnLoadMoreListener
        public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        }

        public AnonymousClass4() {
        }

        @Override // com.scwang.smart.refresh.layout.listener.OnRefreshListener
        public void onRefresh(@NonNull RefreshLayout refreshLayout) {
            HomeCardDataUtils.getInstance().setIsNeedRefreshDeviceRoaster(HomePageFragment.this.getContext(), FamilyUtils.getInstance().getCurrentFamilyId(HomePageFragment.this.getContext()), true);
            HomePageFragment.this.isRefresh = true;
            if (((BaseFragment) HomePageFragment.this).mPresenter != null) {
                ((HomePagePresenter) ((BaseFragment) HomePageFragment.this).mPresenter).getHomeDeviceList();
                ((HomePagePresenter) ((BaseFragment) HomePageFragment.this).mPresenter).getForeignHeatSwitch();
            }
        }
    }

    private void initViews(View view) {
        this.srl = (MyRefreshView) view.findViewById(R.id.srl);
        this.appBar = (AppBarLayout) view.findViewById(R.id.appBar);
        this.rootPanel = (FrameLayout) view.findViewById(R.id.root_panel);
        this.rvPurchaseEligibility = (RecyclerView) view.findViewById(R.id.rv_purchase_eligibility);
        this.tvTitle = (TextView) view.findViewById(R.id.tv_title);
        this.ibAddDevice = (ImageButton) view.findViewById(R.id.ib_add_device);
        this.rlTitlePanel = (RelativeLayout) view.findViewById(R.id.rl_title_panel);
        this.rvDevice = (RecyclerView) view.findViewById(R.id.rv_device);
        this.rvPetError = (RecyclerView) view.findViewById(R.id.rv_pet_error);
        this.llEmptyPanel = (LinearLayout) view.findViewById(R.id.ll_empty_panel);
        this.llMemberEmptyView = (LinearLayout) view.findViewById(R.id.ll_member_empty_view);
        this.discoverPager = (InterceptViewPager) view.findViewById(R.id.discover_pager);
        this.indicator = (CirclePageIndicator) view.findViewById(R.id.indicator);
        this.rlParentPanel = (RelativeLayout) view.findViewById(R.id.rl_parent_panel);
        this.rlPetError = (RelativeLayout) view.findViewById(R.id.rl_pet_error);
        this.tvPetErrorTip = (TextView) view.findViewById(R.id.tv_pet_error_tip);
        this.ivSwiftCardMode = (ImageView) view.findViewById(R.id.iv_swift_card_mode);
        this.ivBannerClose = (ImageView) view.findViewById(R.id.iv_banner_close);
        this.rlAdPanel = (RelativeLayout) view.findViewById(R.id.rl_ad_panel);
        this.ivAddDevice = (ImageView) view.findViewById(R.id.iv_add_device);
        this.tvTopTitle = (TextView) view.findViewById(R.id.tv_top_title);
        this.ibTopTitleAddDevice = (ImageButton) view.findViewById(R.id.ib_top_title_add_device);
        this.ibTopTitleHistoryMsg = (ImageButton) view.findViewById(R.id.ib_top_title_history_msg);
        this.rlTopTitlePanel = (RelativeLayout) view.findViewById(R.id.rl_top_title_panel);
        this.rlAddDevice = (RelativeLayout) view.findViewById(R.id.rl_add_device);
        this.ibHistoryMsg = (ImageButton) view.findViewById(R.id.ib_history_msg);
        this.ivHomeWallPager = (ImageView) view.findViewById(R.id.iv_home_wall_pager);
        this.llControl = (LinearLayout) view.findViewById(R.id.ll_control);
        this.tvPrompt = (TextView) view.findViewById(R.id.tv_prompt);
        this.tvToReset = (TextView) view.findViewById(R.id.tv_to_reset);
        this.lineToReset = view.findViewById(R.id.line_to_reset);
        this.tvNotRemind3Days = (TextView) view.findViewById(R.id.tv_not_remind_3_days);
        this.tvNotRemind = (TextView) view.findViewById(R.id.tv_not_remind);
        this.ivBlurBg = (ImageView) view.findViewById(R.id.iv_blur_bg);
        this.transparentMaskView = view.findViewById(R.id.transparent_mask);
        this.rlMask = (RelativeLayout) view.findViewById(R.id.rl_mask);
        this.rlTodoItem = (RelativeLayout) view.findViewById(R.id.rl_todo_item);
        this.tvContent = (TextView) view.findViewById(R.id.tv_content);
        this.tvRemainTime = (TextView) view.findViewById(R.id.tv_remain_time);
        this.tvTopTitleShare = (TextView) view.findViewById(R.id.tv_top_title_share);
        this.tvTitleShare = (TextView) view.findViewById(R.id.tv_title_share);
        this.ivSwiftCardMode.setOnClickListener(this);
        this.tvTopTitle.setOnClickListener(this);
        this.tvTitle.setOnClickListener(this);
        this.ibAddDevice.setOnClickListener(this);
        this.rlAddDevice.setOnClickListener(this);
        this.ibTopTitleAddDevice.setOnClickListener(this);
        this.ibHistoryMsg.setOnClickListener(this);
        this.ibTopTitleHistoryMsg.setOnClickListener(this);
        this.rlPetError.setOnClickListener(this);
        this.rlMask.setOnClickListener(this);
        this.tvTopTitleShare.setOnClickListener(this);
        this.tvTitleShare.setOnClickListener(this);
    }

    @SuppressLint({"ClickableViewAccessibility"})
    private void initRecyclerViewEvent() {
        this.rvDevice.setOnTouchListener(new View.OnTouchListener() { // from class: com.petkit.android.activities.home.HomePageFragment$$ExternalSyntheticLambda7
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return this.f$0.lambda$initRecyclerViewEvent$2(view, motionEvent);
            }
        });
        this.rvDevice.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.petkit.android.activities.home.HomePageFragment.5
            public AnonymousClass5() {
            }

            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(@NonNull RecyclerView recyclerView, int i, int i2) {
                super.onScrolled(recyclerView, i, i2);
            }

            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int i) {
                Log.d(((BaseFragment) HomePageFragment.this).TAG, "onScrollStateChanged: +" + i);
                super.onScrollStateChanged(recyclerView, i);
                if (i == 0) {
                    if (HomePageFragment.this.cardMode != 0) {
                        if (HomePageFragment.this.cardMode == 1) {
                            int iFindFirstCompletelyVisibleItemPosition = ((GridLayoutManager) recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
                            int iFindLastCompletelyVisibleItemPosition = ((GridLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                            Log.d(((BaseFragment) HomePageFragment.this).TAG, "onScrollStateChanged: firstVisibleItem " + iFindFirstCompletelyVisibleItemPosition + "lastVisibleItem： " + iFindLastCompletelyVisibleItemPosition);
                            if (HomePageFragment.this.homePageLargeDeviceAdapter != null) {
                                while (iFindFirstCompletelyVisibleItemPosition <= iFindLastCompletelyVisibleItemPosition) {
                                    if (iFindFirstCompletelyVisibleItemPosition >= 0 && iFindFirstCompletelyVisibleItemPosition < HomePageFragment.this.homePageLargeDeviceAdapter.getDeviceDataList().size() && HomePageFragment.this.homePageLargeDeviceAdapter.getDeviceDataList().get(iFindFirstCompletelyVisibleItemPosition).getType() == Constants.HOME_CARD_MAIN_DEVICE) {
                                        HomeDeviceData homeDeviceData = HomePageFragment.this.homePageLargeDeviceAdapter.getDeviceDataList().get(iFindFirstCompletelyVisibleItemPosition).getHomeDeviceData();
                                        if (HomeCardDataUtils.getInstance().getIsDeviceNeedRefreshHome(HomePageFragment.this.getContext(), homeDeviceData.getId(), homeDeviceData.getDeviceTypeId())) {
                                            ((HomePagePresenter) ((BaseFragment) HomePageFragment.this).mPresenter).getHomeCardDetail(homeDeviceData.getDeviceTypeId(), homeDeviceData.getId(), "");
                                        }
                                    }
                                    iFindFirstCompletelyVisibleItemPosition++;
                                }
                                return;
                            }
                            return;
                        }
                        return;
                    }
                    int iFindFirstCompletelyVisibleItemPosition2 = ((GridLayoutManager) recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
                    int iFindLastCompletelyVisibleItemPosition2 = ((GridLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                    Log.d(((BaseFragment) HomePageFragment.this).TAG, "onScrollStateChanged: firstVisibleItem " + iFindFirstCompletelyVisibleItemPosition2 + "lastVisibleItem： " + iFindLastCompletelyVisibleItemPosition2);
                    if (HomePageFragment.this.homePageDeviceAdapter != null) {
                        while (iFindFirstCompletelyVisibleItemPosition2 <= iFindLastCompletelyVisibleItemPosition2) {
                            if (iFindFirstCompletelyVisibleItemPosition2 >= 0 && iFindFirstCompletelyVisibleItemPosition2 < HomePageFragment.this.homePageDeviceAdapter.getDeviceDataList().size() && HomePageFragment.this.homePageDeviceAdapter.getDeviceDataList().get(iFindFirstCompletelyVisibleItemPosition2).getType() == Constants.HOME_CARD_MAIN_DEVICE) {
                                HomeDeviceData homeDeviceData2 = HomePageFragment.this.homePageDeviceAdapter.getDeviceDataList().get(iFindFirstCompletelyVisibleItemPosition2).getHomeDeviceData();
                                if (HomeCardDataUtils.getInstance().getIsDeviceNeedRefreshHome(HomePageFragment.this.getContext(), homeDeviceData2.getId(), homeDeviceData2.getDeviceTypeId())) {
                                    ((HomePagePresenter) ((BaseFragment) HomePageFragment.this).mPresenter).getHomeCardDetail(homeDeviceData2.getDeviceTypeId(), homeDeviceData2.getId(), "");
                                }
                            }
                            iFindFirstCompletelyVisibleItemPosition2++;
                        }
                    }
                }
            }
        });
    }

    /* JADX WARN: Removed duplicated region for block: B:50:0x004f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ boolean lambda$initRecyclerViewEvent$2(android.view.View r3, android.view.MotionEvent r4) {
        /*
            r2 = this;
            int r3 = r4.getAction()
            r0 = 1
            r1 = 0
            if (r3 == r0) goto L4f
            r0 = 2
            if (r3 == r0) goto Lf
            r4 = 3
            if (r3 == r4) goto L4f
            goto L55
        Lf:
            boolean r3 = r2.isDrag
            if (r3 == 0) goto L55
            int r3 = r2.sY
            if (r3 == 0) goto L40
            int r3 = r2.sX
            if (r3 != 0) goto L1c
            goto L40
        L1c:
            float r0 = r4.getRawX()
            int r0 = (int) r0
            int r3 = r3 - r0
            int r3 = java.lang.Math.abs(r3)
            r0 = 40
            if (r3 > r0) goto L38
            int r3 = r2.sY
            float r4 = r4.getRawY()
            int r4 = (int) r4
            int r3 = r3 - r4
            int r3 = java.lang.Math.abs(r3)
            if (r3 <= r0) goto L55
        L38:
            com.binioter.guideview.Guide r3 = r2.deleteGuide
            if (r3 == 0) goto L55
            r3.dismiss()
            goto L55
        L40:
            float r3 = r4.getRawX()
            int r3 = (int) r3
            r2.sX = r3
            float r3 = r4.getRawY()
            int r3 = (int) r3
            r2.sY = r3
            goto L55
        L4f:
            r2.isDrag = r1
            r2.sY = r1
            r2.sX = r1
        L55:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.petkit.android.activities.home.HomePageFragment.lambda$initRecyclerViewEvent$2(android.view.View, android.view.MotionEvent):boolean");
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.home.HomePageFragment$5 */
    public class AnonymousClass5 extends RecyclerView.OnScrollListener {
        public AnonymousClass5() {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
        public void onScrolled(@NonNull RecyclerView recyclerView, int i, int i2) {
            super.onScrolled(recyclerView, i, i2);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int i) {
            Log.d(((BaseFragment) HomePageFragment.this).TAG, "onScrollStateChanged: +" + i);
            super.onScrollStateChanged(recyclerView, i);
            if (i == 0) {
                if (HomePageFragment.this.cardMode != 0) {
                    if (HomePageFragment.this.cardMode == 1) {
                        int iFindFirstCompletelyVisibleItemPosition = ((GridLayoutManager) recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
                        int iFindLastCompletelyVisibleItemPosition = ((GridLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                        Log.d(((BaseFragment) HomePageFragment.this).TAG, "onScrollStateChanged: firstVisibleItem " + iFindFirstCompletelyVisibleItemPosition + "lastVisibleItem： " + iFindLastCompletelyVisibleItemPosition);
                        if (HomePageFragment.this.homePageLargeDeviceAdapter != null) {
                            while (iFindFirstCompletelyVisibleItemPosition <= iFindLastCompletelyVisibleItemPosition) {
                                if (iFindFirstCompletelyVisibleItemPosition >= 0 && iFindFirstCompletelyVisibleItemPosition < HomePageFragment.this.homePageLargeDeviceAdapter.getDeviceDataList().size() && HomePageFragment.this.homePageLargeDeviceAdapter.getDeviceDataList().get(iFindFirstCompletelyVisibleItemPosition).getType() == Constants.HOME_CARD_MAIN_DEVICE) {
                                    HomeDeviceData homeDeviceData = HomePageFragment.this.homePageLargeDeviceAdapter.getDeviceDataList().get(iFindFirstCompletelyVisibleItemPosition).getHomeDeviceData();
                                    if (HomeCardDataUtils.getInstance().getIsDeviceNeedRefreshHome(HomePageFragment.this.getContext(), homeDeviceData.getId(), homeDeviceData.getDeviceTypeId())) {
                                        ((HomePagePresenter) ((BaseFragment) HomePageFragment.this).mPresenter).getHomeCardDetail(homeDeviceData.getDeviceTypeId(), homeDeviceData.getId(), "");
                                    }
                                }
                                iFindFirstCompletelyVisibleItemPosition++;
                            }
                            return;
                        }
                        return;
                    }
                    return;
                }
                int iFindFirstCompletelyVisibleItemPosition2 = ((GridLayoutManager) recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
                int iFindLastCompletelyVisibleItemPosition2 = ((GridLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                Log.d(((BaseFragment) HomePageFragment.this).TAG, "onScrollStateChanged: firstVisibleItem " + iFindFirstCompletelyVisibleItemPosition2 + "lastVisibleItem： " + iFindLastCompletelyVisibleItemPosition2);
                if (HomePageFragment.this.homePageDeviceAdapter != null) {
                    while (iFindFirstCompletelyVisibleItemPosition2 <= iFindLastCompletelyVisibleItemPosition2) {
                        if (iFindFirstCompletelyVisibleItemPosition2 >= 0 && iFindFirstCompletelyVisibleItemPosition2 < HomePageFragment.this.homePageDeviceAdapter.getDeviceDataList().size() && HomePageFragment.this.homePageDeviceAdapter.getDeviceDataList().get(iFindFirstCompletelyVisibleItemPosition2).getType() == Constants.HOME_CARD_MAIN_DEVICE) {
                            HomeDeviceData homeDeviceData2 = HomePageFragment.this.homePageDeviceAdapter.getDeviceDataList().get(iFindFirstCompletelyVisibleItemPosition2).getHomeDeviceData();
                            if (HomeCardDataUtils.getInstance().getIsDeviceNeedRefreshHome(HomePageFragment.this.getContext(), homeDeviceData2.getId(), homeDeviceData2.getDeviceTypeId())) {
                                ((HomePagePresenter) ((BaseFragment) HomePageFragment.this).mPresenter).getHomeCardDetail(homeDeviceData2.getDeviceTypeId(), homeDeviceData2.getId(), "");
                            }
                        }
                        iFindFirstCompletelyVisibleItemPosition2++;
                    }
                }
            }
        }
    }

    public void getFamilyList() {
        ((HomePagePresenter) this.mPresenter).getFamilyList();
    }

    @Override // com.petkit.android.activities.home.contract.HomePageContract.View
    public void onFunctionIconClick(String str) {
        str.hashCode();
        switch (str) {
            case "HOME_PAGE_FUNCTION_TYPE_1":
                launchActivity(FeedCalculatorActivity.newIntent(getContext()));
                break;
            case "HOME_PAGE_FUNCTION_TYPE_2":
                Intent intent = new Intent(getActivity(), (Class<?>) HealthRemindActivity2_0.class);
                intent.putExtra(Constants.EXTRA_PET_ID, PetUtils.ALL_DEVICE);
                startActivity(intent);
                break;
            case "HOME_PAGE_FUNCTION_TYPE_3":
                if (CommonUtils.checkPermission(requireActivity(), "android.permission.ACCESS_FINE_LOCATION") && CommonUtils.checkPermission(getActivity(), "android.permission.WRITE_EXTERNAL_STORAGE")) {
                    DataHelper.setBooleanSF(getContext(), "hasWalkdog", Boolean.TRUE);
                    GoWalkData goWalkDataByState = GoDataUtils.getGoWalkDataByState(1);
                    if (goWalkDataByState != null) {
                        Bundle bundle = new Bundle();
                        bundle.putLong(Constants.EXTRA_TAG_ID, goWalkDataByState.getDeviceId());
                        Intent intent2 = new Intent(getActivity(), (Class<?>) GoWalkingActivity.class);
                        intent2.putExtras(bundle);
                        launchActivity(intent2);
                    } else if (WalkDataUtils.getWalkRecord().getGoal() <= 0) {
                        Intent intent3 = new Intent(getContext(), (Class<?>) WalkSettingTargetActivity.class);
                        intent3.putExtra(WalkDataUtils.EXTRA_WALK_PET_ID, PetUtils.ALL_DEVICE);
                        startActivity(intent3);
                    } else if (Select.from(WalkData.class).where(Condition.prop("ownerId").eq(Long.valueOf(CommonUtils.getCurrentUserId()))).where(Condition.prop("state").eq(1)).count() > 0) {
                        launchActivity(new Intent(getActivity(), (Class<?>) WalkingActivity.class));
                    } else {
                        Intent intent4 = new Intent(getContext(), (Class<?>) WalkHomeActivity.class);
                        intent4.putExtra(WalkDataUtils.EXTRA_WALK_PET_ID, PetUtils.ALL_DEVICE);
                        startActivity(intent4);
                    }
                    break;
                } else {
                    startActivity(PermissionDialogActivity.newIntent(getActivity(), HomePageFragment.class.getName(), "android.permission.ACCESS_FINE_LOCATION", "android.permission.WRITE_EXTERNAL_STORAGE"));
                    break;
                }
                break;
            case "HOME_PAGE_FUNCTION_TYPE_4":
                if (UserInforUtils.getAccount().getRegion().equals("US") || UserInforUtils.getAccount().getRegion().equals("UM")) {
                    launchActivity(WeightStatisticsActivity.newIntent(getActivity()));
                    break;
                } else {
                    launchActivity(WeightStatisticsActivity.newIntent(getActivity()));
                    break;
                }
                break;
        }
    }

    public void scrollTop() {
        this.rvDevice.scrollToPosition(0);
        this.appBar.setExpanded(true, true);
    }

    private void onScrollChange(float f) {
        this.rlTopTitlePanel.setAlpha(f);
    }

    @Override // com.petkit.android.activities.home.contract.HomePageContract.View
    public void checkMsgCount() {
        if (this.ibHistoryMsg == null) {
            return;
        }
        if (((HomePagePresenter) this.mPresenter).checkHistoryMsgUnReadCount()) {
            this.ibHistoryMsg.setImageResource(R.drawable.white_icon_history_msg_red_point);
            this.ibTopTitleHistoryMsg.setImageResource(R.drawable.white_icon_history_msg_red_point);
        } else {
            this.ibHistoryMsg.setImageResource(R.drawable.icon_history_msg);
            this.ibTopTitleHistoryMsg.setImageResource(R.drawable.icon_history_msg);
        }
        LogcatStorageHelper.addLog("HomePageFragment,schedule count > 0：" + ((HomePagePresenter) this.mPresenter).checkScheduleMsgUnReadCount());
    }

    @Override // com.petkit.android.activities.home.contract.HomePageContract.View
    public void refreshUserInfo() {
        String string;
        FamilyInfor familyInfor = FamilyUtils.getInstance().getFamilyInfor(getActivity(), FamilyUtils.getInstance().getCurrentFamilyId(getActivity()));
        if (familyInfor != null) {
            boolean z = CommonUtil.getLong(UserInforUtils.getCurrentUserId(requireActivity())) == familyInfor.getOwner();
            if (z) {
                this.tvTopTitleShare.setText("");
                this.tvTitleShare.setText("");
            } else {
                String string2 = getResources().getString(R.string.Home_shared_text);
                this.tvTopTitleShare.setText(string2);
                this.tvTitleShare.setText(string2);
            }
            if (!familyInfor.getName().isEmpty() && !this.familyName.equals(familyInfor.getName())) {
                this.familyName = familyInfor.getName();
                if (z) {
                    string = getResources().getString(R.string.Current_owner_family);
                } else {
                    string = getResources().getString(R.string.Current_shared_family, this.familyName);
                }
                if (this.showFamilyName) {
                    showChangeFamilyToast(string);
                }
            }
            this.familyId = familyInfor.getGroupId();
            this.tvTitle.setText(this.familyName);
            this.tvTopTitle.setText(this.familyName);
            String currentFamilyPic = FamilyUtils.getInstance().getCurrentFamilyPic(getActivity(), familyInfor.getGroupId());
            if (!TextUtils.isEmpty(currentFamilyPic) && !currentFamilyPic.equals(this.homeWallPager)) {
                new MyGlideTask((Context) getActivity(), new MyGlideTask.GlideSourceListener() { // from class: com.petkit.android.activities.home.HomePageFragment$$ExternalSyntheticLambda26
                    @Override // com.petkit.android.utils.MyGlideTask.GlideSourceListener
                    public final void onPostExecute(Bitmap bitmap) {
                        this.f$0.lambda$refreshUserInfo$3(bitmap);
                    }

                    @Override // com.petkit.android.utils.MyGlideTask.GlideSourceListener
                    public /* synthetic */ void onPreExecute() {
                        MyGlideTask.GlideSourceListener.CC.$default$onPreExecute(this);
                    }

                    @Override // com.petkit.android.utils.MyGlideTask.GlideSourceListener
                    public /* synthetic */ void onProgressUpdate(Integer... numArr) {
                        MyGlideTask.GlideSourceListener.CC.$default$onProgressUpdate(this, numArr);
                    }
                }, true).execute(currentFamilyPic);
                this.homeWallPager = currentFamilyPic;
            }
        } else {
            ((HomePagePresenter) this.mPresenter).getFamilyList();
        }
        this.tvTitle.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.petkit.android.activities.home.HomePageFragment.6
            public AnonymousClass6() {
            }

            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                HomePageFragment.this.tvTitle.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int familyNameLength = CommonUtils.getFamilyNameLength(HomePageFragment.this.familyName);
                if (familyNameLength < 7) {
                    HomePageFragment.this.tvTitle.setTextSize(1, 28.0f);
                } else if (familyNameLength < 11) {
                    HomePageFragment.this.tvTitle.setTextSize(1, 26.0f);
                } else {
                    HomePageFragment.this.tvTitle.setTextSize(1, 24.0f);
                }
            }
        });
        this.tvTopTitle.requestLayout();
        this.tvTopTitle.invalidate();
    }

    public /* synthetic */ void lambda$refreshUserInfo$3(Bitmap bitmap) {
        if (bitmap == null) {
            ImageView imageView = this.ivHomeWallPager;
            if (imageView != null) {
                imageView.setImageResource(R.drawable.img_wall_pager_default);
                changeHomeTheme(false);
                return;
            }
            return;
        }
        ImageView imageView2 = this.ivHomeWallPager;
        if (imageView2 != null) {
            imageView2.setImageBitmap(bitmap);
            changeHomeTheme(GrayLevelUtil.whiteTheme(bitmap));
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.home.HomePageFragment$6 */
    public class AnonymousClass6 implements ViewTreeObserver.OnGlobalLayoutListener {
        public AnonymousClass6() {
        }

        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public void onGlobalLayout() {
            HomePageFragment.this.tvTitle.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            int familyNameLength = CommonUtils.getFamilyNameLength(HomePageFragment.this.familyName);
            if (familyNameLength < 7) {
                HomePageFragment.this.tvTitle.setTextSize(1, 28.0f);
            } else if (familyNameLength < 11) {
                HomePageFragment.this.tvTitle.setTextSize(1, 26.0f);
            } else {
                HomePageFragment.this.tvTitle.setTextSize(1, 24.0f);
            }
        }
    }

    @Override // com.petkit.android.activities.home.contract.HomePageContract.View
    public void refreshPetError(List<PetErrorInfo> list) {
        if (list != null && list.size() > 0) {
            if (list.size() == 1) {
                this.rvPetError.setVisibility(0);
                this.rlPetError.setVisibility(8);
                this.rvPetError.setLayoutManager(new LinearLayoutManager(getActivity()));
                PetErrorItemAdapter petErrorItemAdapter = this.petErrorItemAdapter;
                if (petErrorItemAdapter == null) {
                    PetErrorItemAdapter petErrorItemAdapter2 = new PetErrorItemAdapter(getActivity(), list, new PetErrorItemAdapter.OnClickListener() { // from class: com.petkit.android.activities.home.HomePageFragment.7
                        public AnonymousClass7() {
                        }

                        @Override // com.petkit.android.activities.home.adapter.PetErrorItemAdapter.OnClickListener
                        public void onSelectLeft(PetErrorInfo petErrorInfo) {
                            MedicalConversionItem medicalConversionItem = new MedicalConversionItem();
                            medicalConversionItem.setContent(petErrorInfo.getDesc());
                            medicalConversionItem.setPetId(petErrorInfo.getPetId());
                            medicalConversionItem.setPetName(petErrorInfo.getPetName());
                            medicalConversionItem.setPetAvatar(petErrorInfo.getAvatar());
                            DoctorUtils.getInstance().askDoctor(HomePageFragment.this.getActivity(), medicalConversionItem, null, 0L, 0);
                        }

                        @Override // com.petkit.android.activities.home.adapter.PetErrorItemAdapter.OnClickListener
                        public void onSelectTip(PetErrorInfo petErrorInfo) {
                            HomePageFragment homePageFragment = HomePageFragment.this;
                            homePageFragment.launchActivity(ToiletStatisticsActivity.newIntent(homePageFragment.getActivity(), String.valueOf(petErrorInfo.getPetId())));
                        }

                        @Override // com.petkit.android.activities.home.adapter.PetErrorItemAdapter.OnClickListener
                        public void onSelectRight(PetErrorInfo petErrorInfo) {
                            HomePageFragment.this.showNoRemindConfirmWindow(petErrorInfo, 2);
                        }

                        @Override // com.petkit.android.activities.home.adapter.PetErrorItemAdapter.OnClickListener
                        public void onSelectClose(PetErrorInfo petErrorInfo) {
                            ((HomePagePresenter) ((BaseFragment) HomePageFragment.this).mPresenter).ignorePetError(petErrorInfo, 1);
                        }
                    });
                    this.petErrorItemAdapter = petErrorItemAdapter2;
                    this.rvPetError.setAdapter(petErrorItemAdapter2);
                    return;
                }
                petErrorItemAdapter.setData(list);
                return;
            }
            this.rvPetError.setVisibility(8);
            this.rlPetError.setVisibility(0);
            this.tvPetErrorTip.setText(getActivity().getString(R.string.Pet_error_have_more_pet_health_errors, String.valueOf(list.size())));
            return;
        }
        this.rvPetError.setVisibility(8);
        this.rlPetError.setVisibility(8);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.home.HomePageFragment$7 */
    public class AnonymousClass7 implements PetErrorItemAdapter.OnClickListener {
        public AnonymousClass7() {
        }

        @Override // com.petkit.android.activities.home.adapter.PetErrorItemAdapter.OnClickListener
        public void onSelectLeft(PetErrorInfo petErrorInfo) {
            MedicalConversionItem medicalConversionItem = new MedicalConversionItem();
            medicalConversionItem.setContent(petErrorInfo.getDesc());
            medicalConversionItem.setPetId(petErrorInfo.getPetId());
            medicalConversionItem.setPetName(petErrorInfo.getPetName());
            medicalConversionItem.setPetAvatar(petErrorInfo.getAvatar());
            DoctorUtils.getInstance().askDoctor(HomePageFragment.this.getActivity(), medicalConversionItem, null, 0L, 0);
        }

        @Override // com.petkit.android.activities.home.adapter.PetErrorItemAdapter.OnClickListener
        public void onSelectTip(PetErrorInfo petErrorInfo) {
            HomePageFragment homePageFragment = HomePageFragment.this;
            homePageFragment.launchActivity(ToiletStatisticsActivity.newIntent(homePageFragment.getActivity(), String.valueOf(petErrorInfo.getPetId())));
        }

        @Override // com.petkit.android.activities.home.adapter.PetErrorItemAdapter.OnClickListener
        public void onSelectRight(PetErrorInfo petErrorInfo) {
            HomePageFragment.this.showNoRemindConfirmWindow(petErrorInfo, 2);
        }

        @Override // com.petkit.android.activities.home.adapter.PetErrorItemAdapter.OnClickListener
        public void onSelectClose(PetErrorInfo petErrorInfo) {
            ((HomePagePresenter) ((BaseFragment) HomePageFragment.this).mPresenter).ignorePetError(petErrorInfo, 1);
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.home.HomePageFragment$8 */
    public class AnonymousClass8 implements NormalCenterTipWindow.OnClickOk {
        public final /* synthetic */ PetErrorInfo val$info;
        public final /* synthetic */ int val$type;

        @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
        public void onCancelClick() {
        }

        public AnonymousClass8(PetErrorInfo petErrorInfo, int i) {
            petErrorInfo = petErrorInfo;
            i = i;
        }

        @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
        public void onOkClick() {
            ((HomePagePresenter) ((BaseFragment) HomePageFragment.this).mPresenter).ignorePetError(petErrorInfo, i);
        }
    }

    public void showNoRemindConfirmWindow(PetErrorInfo petErrorInfo, int i) {
        NormalCenterTipWindow normalCenterTipWindow = new NormalCenterTipWindow(getActivity(), new NormalCenterTipWindow.OnClickOk() { // from class: com.petkit.android.activities.home.HomePageFragment.8
            public final /* synthetic */ PetErrorInfo val$info;
            public final /* synthetic */ int val$type;

            @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
            public void onCancelClick() {
            }

            public AnonymousClass8(PetErrorInfo petErrorInfo2, int i2) {
                petErrorInfo = petErrorInfo2;
                i = i2;
            }

            @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
            public void onOkClick() {
                ((HomePagePresenter) ((BaseFragment) HomePageFragment.this).mPresenter).ignorePetError(petErrorInfo, i);
            }
        }, (String) null, petErrorInfo2.getAlertTip());
        this.petErrorNoRemindWindow = normalCenterTipWindow;
        if (normalCenterTipWindow.isShowing()) {
            return;
        }
        this.petErrorNoRemindWindow.show(getActivity().getWindow().getDecorView());
    }

    public void refreshCardList(List<NewHomeCardData> list) {
        this.homeCardDataList = list;
        if (list.size() == 0) {
            this.llEmptyPanel.setVisibility(0);
            if (FamilyUtils.getInstance().isCurrentFamilyOwner(getContext())) {
                this.llMemberEmptyView.setVisibility(8);
                this.rlAddDevice.setVisibility(0);
            } else {
                this.llMemberEmptyView.setVisibility(0);
                this.rlAddDevice.setVisibility(8);
            }
            this.ivSwiftCardMode.setVisibility(8);
            this.rvDevice.setVisibility(8);
            return;
        }
        if (list.get(0).getType() == Constants.HOME_CARD_VIRTUAL_AREA) {
            this.llEmptyPanel.setVisibility(0);
            if (FamilyUtils.getInstance().isCurrentFamilyOwner(getContext())) {
                this.llMemberEmptyView.setVisibility(8);
                this.rlAddDevice.setVisibility(0);
            } else {
                this.llMemberEmptyView.setVisibility(0);
                this.rlAddDevice.setVisibility(8);
            }
            this.ivSwiftCardMode.setVisibility(8);
        } else {
            this.llEmptyPanel.setVisibility(8);
            this.ivSwiftCardMode.setVisibility(0);
        }
        this.rvDevice.setVisibility(0);
        int i = this.cardMode;
        if (i == 0) {
            HomePageDeviceAdapter homePageDeviceAdapter = this.homePageDeviceAdapter;
            if (homePageDeviceAdapter == null || this.isSwitchCardMode || homePageDeviceAdapter.getDeviceDataList().size() != list.size()) {
                this.isRefresh = false;
                this.homePageDeviceAdapter = new HomePageDeviceAdapter(getContext(), this, list);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
                this.gridLayoutManager = gridLayoutManager;
                gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() { // from class: com.petkit.android.activities.home.HomePageFragment.9
                    public AnonymousClass9() {
                    }

                    @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
                    public int getSpanSize(int i2) {
                        if (i2 >= HomePageFragment.this.homeCardDataList.size()) {
                            return 0;
                        }
                        return ((NewHomeCardData) HomePageFragment.this.homeCardDataList.get(i2)).getType() == Constants.HOME_CARD_VIRTUAL_AREA ? 2 : 1;
                    }
                });
                this.rvDevice.setLayoutManager(this.gridLayoutManager);
                this.rvDevice.setAdapter(this.homePageDeviceAdapter);
                this.rvDevice.setItemAnimator(null);
                PetItemTouchHelperCallback petItemTouchHelperCallback = new PetItemTouchHelperCallback(this.homePageDeviceAdapter);
                petItemTouchHelperCallback.setDragListener(new PetItemTouchHelperCallback.IsDragListener() { // from class: com.petkit.android.activities.home.HomePageFragment$$ExternalSyntheticLambda55
                    @Override // com.petkit.android.activities.pet.PetItemTouchHelperCallback.IsDragListener
                    public final void isDrag(boolean z, RecyclerView.ViewHolder viewHolder) {
                        this.f$0.lambda$refreshCardList$4(z, viewHolder);
                    }
                });
                ItemTouchHelper itemTouchHelper = new ItemTouchHelper(petItemTouchHelperCallback);
                this.homePageDeviceAdapter.setOnItemDragListener(new HomePageFragment$$ExternalSyntheticLambda56(itemTouchHelper));
                itemTouchHelper.attachToRecyclerView(this.rvDevice);
                this.isSwitchCardMode = false;
                return;
            }
            GridLayoutManager gridLayoutManager2 = this.gridLayoutManager;
            if (gridLayoutManager2 != null) {
                this.rvDevice.setLayoutManager(gridLayoutManager2);
            }
            this.homePageDeviceAdapter.updateAdapter(list);
            this.rvDevice.setAdapter(this.homePageDeviceAdapter);
            return;
        }
        if (i == 1) {
            HomePageLargeDeviceAdapter homePageLargeDeviceAdapter = this.homePageLargeDeviceAdapter;
            if (homePageLargeDeviceAdapter == null || this.isSwitchCardMode || homePageLargeDeviceAdapter.getDeviceDataList().size() != list.size()) {
                this.isRefresh = false;
                this.homePageLargeDeviceAdapter = new HomePageLargeDeviceAdapter(getContext(), this, list);
                GridLayoutManager gridLayoutManager3 = new GridLayoutManager(getContext(), 2);
                this.largeGridLayoutManager = gridLayoutManager3;
                gridLayoutManager3.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() { // from class: com.petkit.android.activities.home.HomePageFragment.10
                    @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
                    public int getSpanSize(int i2) {
                        return 2;
                    }

                    public AnonymousClass10() {
                    }
                });
                this.rvDevice.setLayoutManager(this.largeGridLayoutManager);
                this.rvDevice.setAdapter(this.homePageLargeDeviceAdapter);
                this.rvDevice.setItemAnimator(null);
                PetItemTouchHelperCallback petItemTouchHelperCallback2 = new PetItemTouchHelperCallback(this.homePageLargeDeviceAdapter);
                petItemTouchHelperCallback2.setDragListener(new PetItemTouchHelperCallback.IsDragListener() { // from class: com.petkit.android.activities.home.HomePageFragment$$ExternalSyntheticLambda57
                    @Override // com.petkit.android.activities.pet.PetItemTouchHelperCallback.IsDragListener
                    public final void isDrag(boolean z, RecyclerView.ViewHolder viewHolder) {
                        this.f$0.lambda$refreshCardList$5(z, viewHolder);
                    }
                });
                ItemTouchHelper itemTouchHelper2 = new ItemTouchHelper(petItemTouchHelperCallback2);
                this.homePageLargeDeviceAdapter.setOnItemDragListener(new HomePageFragment$$ExternalSyntheticLambda56(itemTouchHelper2));
                itemTouchHelper2.attachToRecyclerView(this.rvDevice);
                this.isSwitchCardMode = false;
                return;
            }
            this.rvDevice.setLayoutManager(this.largeGridLayoutManager);
            this.homePageLargeDeviceAdapter.updateAdapter(list);
            this.rvDevice.setAdapter(this.homePageLargeDeviceAdapter);
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.home.HomePageFragment$9 */
    public class AnonymousClass9 extends GridLayoutManager.SpanSizeLookup {
        public AnonymousClass9() {
        }

        @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
        public int getSpanSize(int i2) {
            if (i2 >= HomePageFragment.this.homeCardDataList.size()) {
                return 0;
            }
            return ((NewHomeCardData) HomePageFragment.this.homeCardDataList.get(i2)).getType() == Constants.HOME_CARD_VIRTUAL_AREA ? 2 : 1;
        }
    }

    public /* synthetic */ void lambda$refreshCardList$4(boolean z, RecyclerView.ViewHolder viewHolder) {
        this.isDrag = z;
        if (!z || viewHolder == null) {
            return;
        }
        int layoutPosition = viewHolder.getLayoutPosition();
        if (this.homePageDeviceAdapter.getDeviceDataList().get(layoutPosition).getType() == Constants.HOME_CARD_MAIN_DEVICE) {
            getLocation(viewHolder.itemView, this.homePageDeviceAdapter.getDeviceDataList().get(layoutPosition).getHomeDeviceData(), false);
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.home.HomePageFragment$10 */
    public class AnonymousClass10 extends GridLayoutManager.SpanSizeLookup {
        @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
        public int getSpanSize(int i2) {
            return 2;
        }

        public AnonymousClass10() {
        }
    }

    public /* synthetic */ void lambda$refreshCardList$5(boolean z, RecyclerView.ViewHolder viewHolder) {
        this.isDrag = z;
        if (!z || viewHolder == null) {
            return;
        }
        int layoutPosition = viewHolder.getLayoutPosition();
        if (this.homePageLargeDeviceAdapter.getDeviceDataList().get(layoutPosition).getType() == Constants.HOME_CARD_MAIN_DEVICE) {
            getLocation(viewHolder.itemView, this.homePageLargeDeviceAdapter.getDeviceDataList().get(layoutPosition).getHomeDeviceData(), true);
        }
    }

    @Override // com.petkit.android.activities.home.contract.HomePageContract.View
    public void refreshDeviceList(HomeDeviceListRsp.HomeDeviceListResult homeDeviceListResult) {
        this.result = homeDeviceListResult;
        this.deviceListReady = true;
        this.isSwitchCardMode = true;
        swiftCardMode();
        initAdView(DeviceCenterUtils.getAppBannerList());
        refreshCardList(((HomePagePresenter) this.mPresenter).getNewLocalCardList());
        this.rlTitlePanel.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.petkit.android.activities.home.HomePageFragment.11
            public AnonymousClass11() {
            }

            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                StringBuilder sb = new StringBuilder();
                sb.append("onGlobalLayout：");
                sb.append(HomePageFragment.this.deviceListReady);
                sb.append("  mPresenter：");
                sb.append(((BaseFragment) HomePageFragment.this).mPresenter == null);
                PetkitLog.d("changeTheme", sb.toString());
                HomePageFragment homePageFragment = HomePageFragment.this;
                if (homePageFragment.deviceListReady) {
                    if (((BaseFragment) homePageFragment).mPresenter == null) {
                        return;
                    }
                    if (((HomePagePresenter) ((BaseFragment) HomePageFragment.this).mPresenter).getNewLocalCardList().isEmpty() || ((HomePagePresenter) ((BaseFragment) HomePageFragment.this).mPresenter).getNewLocalCardList().get(0).getType().equals(Constants.HOME_CARD_VIRTUAL_AREA)) {
                        HomePageFragment.this.isShowOneGuide = true;
                    }
                    HomePageFragment.this.showHomeGuide();
                }
                HomePageFragment.this.rlTitlePanel.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.home.HomePageFragment$11 */
    public class AnonymousClass11 implements ViewTreeObserver.OnGlobalLayoutListener {
        public AnonymousClass11() {
        }

        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public void onGlobalLayout() {
            StringBuilder sb = new StringBuilder();
            sb.append("onGlobalLayout：");
            sb.append(HomePageFragment.this.deviceListReady);
            sb.append("  mPresenter：");
            sb.append(((BaseFragment) HomePageFragment.this).mPresenter == null);
            PetkitLog.d("changeTheme", sb.toString());
            HomePageFragment homePageFragment = HomePageFragment.this;
            if (homePageFragment.deviceListReady) {
                if (((BaseFragment) homePageFragment).mPresenter == null) {
                    return;
                }
                if (((HomePagePresenter) ((BaseFragment) HomePageFragment.this).mPresenter).getNewLocalCardList().isEmpty() || ((HomePagePresenter) ((BaseFragment) HomePageFragment.this).mPresenter).getNewLocalCardList().get(0).getType().equals(Constants.HOME_CARD_VIRTUAL_AREA)) {
                    HomePageFragment.this.isShowOneGuide = true;
                }
                HomePageFragment.this.showHomeGuide();
            }
            HomePageFragment.this.rlTitlePanel.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }
    }

    private void initAdView(List<AppBanner> list) {
        if (list == null || list.size() == 0) {
            this.rlAdPanel.setVisibility(8);
            return;
        }
        final List<AppBanner> notCloseList = UserInforUtils.getNotCloseList(getActivity(), list);
        if (notCloseList.size() == 0) {
            this.rlAdPanel.setVisibility(8);
            return;
        }
        this.rlAdPanel.setVisibility(0);
        this.discoverPager.setAdapter(new HomeBannerPageAdapter(CommonUtils.getAppContext(), notCloseList, this));
        this.indicator.setViewPager(this.discoverPager, 0, notCloseList.size());
        this.indicator.setPageColor(CommonUtils.getColorById(R.color.cozy_circle_color));
        this.indicator.setFillColor(CommonUtils.getColorById(R.color.white));
        this.indicator.setSnap(true);
        this.indicator.setIndicatorStyle(1);
        this.indicator.setRadius(ArmsUtils.dip2px(CommonUtils.getAppContext(), 3.0f));
        ViewGroup.LayoutParams layoutParams = this.rlParentPanel.getLayoutParams();
        layoutParams.height = ((BaseApplication.displayMetrics.widthPixels - ArmsUtils.dip2px(CommonUtils.getAppContext(), 16.0f)) / 343) * 90;
        this.rlParentPanel.setLayoutParams(layoutParams);
        if (notCloseList.size() > 1) {
            this.indicator.setVisibility(0);
            if (this.disposable == null) {
                this.disposable = Observable.interval(5000L, 5000L, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.petkit.android.activities.home.HomePageFragment$$ExternalSyntheticLambda61
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) throws Exception {
                        this.f$0.lambda$initAdView$6(notCloseList, (Long) obj);
                    }
                });
            }
        } else {
            this.indicator.setVisibility(8);
            Disposable disposable = this.disposable;
            if (disposable != null && !disposable.isDisposed()) {
                this.disposable.dispose();
                this.disposable = null;
            }
        }
        this.ivBannerClose.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.home.HomePageFragment$$ExternalSyntheticLambda62
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initAdView$7(notCloseList, view);
            }
        });
    }

    public /* synthetic */ void lambda$initAdView$6(List list, Long l) throws Exception {
        if (this.disposable != null) {
            InterceptViewPager interceptViewPager = this.discoverPager;
            interceptViewPager.setCurrentItem(interceptViewPager.getCurrentItem() + 1);
            this.current = this.discoverPager.getCurrentItem() % (list.size() != 0 ? list.size() : 1);
        }
    }

    public /* synthetic */ void lambda$initAdView$7(List list, View view) {
        new HashMap().put("type", String.valueOf(((AppBanner) list.get(this.current)).getId()));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            UserInforUtils.closeBanner(getActivity(), ((AppBanner) it.next()).getId());
        }
        this.rlAdPanel.setVisibility(8);
    }

    public void showHomeGuide() {
        if (HomeGuideUtils.getInstance().canShowGuide1()) {
            showFirstGuide();
            return;
        }
        if (HomeGuideUtils.getInstance().canShowGuide2() && this.ivSwiftCardMode.getVisibility() == 0) {
            showSecondGuide();
            return;
        }
        if (this.cardMode == 0) {
            if (HomeGuideUtils.getInstance().canShowGuideSmall()) {
                showFourthGuide();
            }
        } else if (HomeGuideUtils.getInstance().canShowGuideLarge()) {
            showFourthGuideLarge();
        }
    }

    private void swiftCardMode() {
        if (this.cardMode == 0) {
            if (this.isChangePosition) {
                HomeCardDataUtils.getInstance().setIsNeedRefreshDeviceRoaster(getContext(), FamilyUtils.getInstance().getCurrentFamilyId(getContext()), true);
                ((HomePagePresenter) this.mPresenter).getHomeDeviceList();
                this.isChangePosition = false;
            }
            this.rvDevice.setVisibility(0);
            refreshCardList(((HomePagePresenter) this.mPresenter).getNewLocalCardList());
            this.ivSwiftCardMode.setBackgroundResource(R.drawable.swift_large_card_icon);
            return;
        }
        if (this.isChangePosition) {
            HomeCardDataUtils.getInstance().setIsNeedRefreshDeviceRoaster(getContext(), FamilyUtils.getInstance().getCurrentFamilyId(getContext()), true);
            ((HomePagePresenter) this.mPresenter).getHomeDeviceList();
            this.isChangePosition = false;
        }
        this.rvDevice.setVisibility(0);
        refreshCardList(((HomePagePresenter) this.mPresenter).getNewLocalCardList());
        this.ivSwiftCardMode.setBackgroundResource(R.drawable.swift_small_card_icon);
    }

    public void showFirstGuide() {
        TextView textView;
        if (!isAdded() || getContext() == null || (textView = this.tvTitle) == null) {
            return;
        }
        if (textView.getWidth() == 0 || this.tvTitle.getHeight() == 0) {
            this.tvTitle.postDelayed(new Runnable() { // from class: com.petkit.android.activities.home.HomePageFragment.12
                public AnonymousClass12() {
                }

                @Override // java.lang.Runnable
                public void run() {
                    HomePageFragment.this.showFirstGuide();
                }
            }, 200L);
            return;
        }
        if (this.guide != null) {
            return;
        }
        GuideBuilder guideBuilder = new GuideBuilder();
        guideBuilder.setTargetView(this.tvTitle).setAlpha(180).setHighTargetCorner(30).setHighTargetPaddingLeft(ArmsUtils.dip2px(CommonUtils.getAppContext(), 3.0f)).setHighTargetPaddingRight(ArmsUtils.dip2px(CommonUtils.getAppContext(), 3.0f)).setHighTargetPaddingBottom(ArmsUtils.dip2px(CommonUtils.getAppContext(), 6.0f)).setHighTargetPaddingTop(ArmsUtils.dip2px(CommonUtils.getAppContext(), 6.0f)).setOutsideTouchable(false).setAutoDismiss(false);
        guideBuilder.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() { // from class: com.petkit.android.activities.home.HomePageFragment.13
            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onDismiss() {
            }

            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onShown() {
            }

            public AnonymousClass13() {
            }
        });
        guideBuilder.addComponent(new GuidePetTipAndBtnAndLineComponent(new GuideParam(getResources().getString(R.string.Home_guide_one), "1/3", 4, 32, 100, 10, getResources().getString(R.string.Next_tip), R.layout.layout_home_guide_pet_one), new ConfirmListener() { // from class: com.petkit.android.activities.home.HomePageFragment.14
            public AnonymousClass14() {
            }

            @Override // com.petkit.android.activities.petkitBleDevice.w5.guide.ConfirmListener
            public void onConfirmListener() {
                if (HomePageFragment.this.guide != null) {
                    HomePageFragment.this.guide.dismiss();
                }
                CommonUtils.addSysBoolMap(CommonUtils.getAppContext(), Consts.GUIDE_HOME_FIRST, true);
                if (HomePageFragment.this.ivSwiftCardMode != null && HomePageFragment.this.ivSwiftCardMode.getVisibility() == 0 && HomeGuideUtils.getInstance().canShowGuide2()) {
                    HomePageFragment.this.showSecondGuide();
                }
            }
        }));
        this.guide = guideBuilder.createGuide();
        HomeGuideUtils.getInstance().setAlreadyShowGuide(true);
        PetkitLog.d("showHomeGuide", "第1步star");
        this.guide.show(getActivity());
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.home.HomePageFragment$12 */
    public class AnonymousClass12 implements Runnable {
        public AnonymousClass12() {
        }

        @Override // java.lang.Runnable
        public void run() {
            HomePageFragment.this.showFirstGuide();
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.home.HomePageFragment$13 */
    public class AnonymousClass13 implements GuideBuilder.OnVisibilityChangedListener {
        @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
        public void onDismiss() {
        }

        @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
        public void onShown() {
        }

        public AnonymousClass13() {
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.home.HomePageFragment$14 */
    public class AnonymousClass14 implements ConfirmListener {
        public AnonymousClass14() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.w5.guide.ConfirmListener
        public void onConfirmListener() {
            if (HomePageFragment.this.guide != null) {
                HomePageFragment.this.guide.dismiss();
            }
            CommonUtils.addSysBoolMap(CommonUtils.getAppContext(), Consts.GUIDE_HOME_FIRST, true);
            if (HomePageFragment.this.ivSwiftCardMode != null && HomePageFragment.this.ivSwiftCardMode.getVisibility() == 0 && HomeGuideUtils.getInstance().canShowGuide2()) {
                HomePageFragment.this.showSecondGuide();
            }
        }
    }

    public void showSecondGuide() {
        if (isAdded() && getContext() != null && this.thirdGuide == null) {
            GuideBuilder guideBuilder = new GuideBuilder();
            guideBuilder.setTargetView(this.ivSwiftCardMode).setAlpha(180).setHighTargetCorner(5).setHighTargetPaddingLeft(ArmsUtils.dip2px(CommonUtils.getAppContext(), 1.0f)).setHighTargetPaddingRight(ArmsUtils.dip2px(CommonUtils.getAppContext(), 1.0f)).setHighTargetPaddingBottom(ArmsUtils.dip2px(CommonUtils.getAppContext(), 1.0f)).setHighTargetPaddingTop(ArmsUtils.dip2px(CommonUtils.getAppContext(), 1.0f)).setOutsideTouchable(false).setAutoDismiss(false);
            guideBuilder.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() { // from class: com.petkit.android.activities.home.HomePageFragment.15
                @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
                public void onDismiss() {
                }

                @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
                public void onShown() {
                }

                public AnonymousClass15() {
                }
            });
            guideBuilder.addComponent(new GuidePetTipAndBtnAndLineComponent(new GuideParam(getResources().getString(R.string.Home_card_type_guide), "2/3", 4, 48, 15, 10, getResources().getString(R.string.Next_tip), R.layout.layout_home_guide_pet_two), new ConfirmListener() { // from class: com.petkit.android.activities.home.HomePageFragment$$ExternalSyntheticLambda60
                @Override // com.petkit.android.activities.petkitBleDevice.w5.guide.ConfirmListener
                public final void onConfirmListener() {
                    this.f$0.lambda$showSecondGuide$8();
                }
            }));
            this.thirdGuide = guideBuilder.createGuide();
            PetkitLog.d("showHomeGuide", "第2步star");
            this.thirdGuide.show(getActivity());
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.home.HomePageFragment$15 */
    public class AnonymousClass15 implements GuideBuilder.OnVisibilityChangedListener {
        @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
        public void onDismiss() {
        }

        @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
        public void onShown() {
        }

        public AnonymousClass15() {
        }
    }

    public /* synthetic */ void lambda$showSecondGuide$8() {
        Guide guide = this.thirdGuide;
        if (guide != null) {
            guide.dismiss();
        }
        CommonUtils.addSysBoolMap(CommonUtils.getAppContext(), Consts.GUIDE_HOME_THIRD, true);
        HomeGuideUtils.getInstance().setAlreadyShowGuide2(true);
        if (this.cardMode == 0) {
            if (HomeGuideUtils.getInstance().canShowGuideSmall()) {
                showFourthGuide();
            }
        } else if (HomeGuideUtils.getInstance().canShowGuideLarge()) {
            showFourthGuideLarge();
        }
    }

    private void showFourthGuide() {
        GridLayoutManager gridLayoutManager;
        View viewFindViewByPosition;
        if (!isAdded() || getContext() == null || this.fourthGuide != null || (gridLayoutManager = this.gridLayoutManager) == null || (viewFindViewByPosition = gridLayoutManager.findViewByPosition(0)) == null) {
            return;
        }
        GuideBuilder guideBuilder = new GuideBuilder();
        guideBuilder.setTargetView(viewFindViewByPosition).setAlpha(180).setHighTargetCorner(ArmsUtils.dip2px(CommonUtils.getAppContext(), 16.0f)).setHighTargetPaddingTop(ArmsUtils.dip2px(CommonUtils.getAppContext(), 1.0f)).setHighTargetPaddingLeft(ArmsUtils.dip2px(CommonUtils.getAppContext(), -7.0f)).setHighTargetPaddingRight(ArmsUtils.dip2px(CommonUtils.getAppContext(), -7.0f)).setHighTargetPaddingBottom(ArmsUtils.dip2px(CommonUtils.getAppContext(), -19.0f)).setOutsideTouchable(false).setAutoDismiss(true);
        guideBuilder.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() { // from class: com.petkit.android.activities.home.HomePageFragment.16
            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onShown() {
            }

            public AnonymousClass16() {
            }

            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onDismiss() {
                HomeGuideUtils.getInstance().setAlreadyShowSmall(true);
                CommonUtils.addSysBoolMap(CommonUtils.getAppContext(), Consts.GUIDE_HOME_CARD_SMALL, true);
            }
        });
        guideBuilder.addComponent(new GuideTipAndBtnAndLineComponent(new GuideParam(getResources().getString(R.string.Home_guide_three), "3/3", 2, 48, 200, -10, getResources().getString(R.string.Know), R.layout.layout_home_guide_mine), new ConfirmListener() { // from class: com.petkit.android.activities.home.HomePageFragment$$ExternalSyntheticLambda0
            @Override // com.petkit.android.activities.petkitBleDevice.w5.guide.ConfirmListener
            public final void onConfirmListener() {
                this.f$0.lambda$showFourthGuide$9();
            }
        }));
        this.fourthGuide = guideBuilder.createGuide();
        PetkitLog.d("showHomeGuide", "第3步小卡片star");
        new Handler().postDelayed(new Runnable() { // from class: com.petkit.android.activities.home.HomePageFragment.17
            public AnonymousClass17() {
            }

            @Override // java.lang.Runnable
            public void run() {
                HomePageFragment.this.fourthGuide.show(HomePageFragment.this.getActivity());
            }
        }, 100L);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.home.HomePageFragment$16 */
    public class AnonymousClass16 implements GuideBuilder.OnVisibilityChangedListener {
        @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
        public void onShown() {
        }

        public AnonymousClass16() {
        }

        @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
        public void onDismiss() {
            HomeGuideUtils.getInstance().setAlreadyShowSmall(true);
            CommonUtils.addSysBoolMap(CommonUtils.getAppContext(), Consts.GUIDE_HOME_CARD_SMALL, true);
        }
    }

    public /* synthetic */ void lambda$showFourthGuide$9() {
        Guide guide = this.fourthGuide;
        if (guide != null) {
            guide.dismiss();
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.home.HomePageFragment$17 */
    public class AnonymousClass17 implements Runnable {
        public AnonymousClass17() {
        }

        @Override // java.lang.Runnable
        public void run() {
            HomePageFragment.this.fourthGuide.show(HomePageFragment.this.getActivity());
        }
    }

    private void showFourthGuideLarge() {
        GridLayoutManager gridLayoutManager;
        View viewFindViewByPosition;
        if (!isAdded() || getContext() == null || this.fourthGuideLarge != null || (gridLayoutManager = this.largeGridLayoutManager) == null || (viewFindViewByPosition = gridLayoutManager.findViewByPosition(0)) == null) {
            return;
        }
        GuideBuilder guideBuilder = new GuideBuilder();
        guideBuilder.setTargetView(viewFindViewByPosition).setAlpha(180).setHighTargetCorner(ArmsUtils.dip2px(CommonUtils.getAppContext(), 28.0f)).setHighTargetPaddingTop(ArmsUtils.dip2px(CommonUtils.getAppContext(), 1.0f)).setHighTargetPaddingLeft(ArmsUtils.dip2px(CommonUtils.getAppContext(), -7.0f)).setHighTargetPaddingRight(ArmsUtils.dip2px(CommonUtils.getAppContext(), -7.0f)).setHighTargetPaddingBottom(ArmsUtils.dip2px(CommonUtils.getAppContext(), -19.0f)).setOutsideTouchable(false).setAutoDismiss(true);
        guideBuilder.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() { // from class: com.petkit.android.activities.home.HomePageFragment.18
            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onShown() {
            }

            public AnonymousClass18() {
            }

            @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
            public void onDismiss() {
                HomeGuideUtils.getInstance().setAlreadyShowLarge(true);
                CommonUtils.addSysBoolMap(CommonUtils.getAppContext(), Consts.GUIDE_HOME_CARD_LARGE, true);
            }
        });
        guideBuilder.addComponent(new GuideTipAndBtnAndLineComponent(new GuideParam(getResources().getString(R.string.Home_guide_three), "3/3", 2, 48, 0, -10, getResources().getString(R.string.Know), R.layout.layout_home_guide_mine), new ConfirmListener() { // from class: com.petkit.android.activities.home.HomePageFragment$$ExternalSyntheticLambda6
            @Override // com.petkit.android.activities.petkitBleDevice.w5.guide.ConfirmListener
            public final void onConfirmListener() {
                this.f$0.lambda$showFourthGuideLarge$10();
            }
        }));
        this.fourthGuideLarge = guideBuilder.createGuide();
        PetkitLog.d("showHomeGuide", "第4步小卡片star");
        this.fourthGuideLarge.show(getActivity());
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.home.HomePageFragment$18 */
    public class AnonymousClass18 implements GuideBuilder.OnVisibilityChangedListener {
        @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
        public void onShown() {
        }

        public AnonymousClass18() {
        }

        @Override // com.binioter.guideview.GuideBuilder.OnVisibilityChangedListener
        public void onDismiss() {
            HomeGuideUtils.getInstance().setAlreadyShowLarge(true);
            CommonUtils.addSysBoolMap(CommonUtils.getAppContext(), Consts.GUIDE_HOME_CARD_LARGE, true);
        }
    }

    public /* synthetic */ void lambda$showFourthGuideLarge$10() {
        Guide guide = this.fourthGuideLarge;
        if (guide != null) {
            guide.dismiss();
        }
    }

    @Override // com.petkit.android.activities.home.contract.HomePageContract.View
    public void updateDeviceData(HomeDeviceData homeDeviceData) {
        int indexByDeviceTypeAndId;
        int indexByDeviceTypeAndId2;
        if (TextUtils.isEmpty(homeDeviceData.getType())) {
            return;
        }
        long id = homeDeviceData.getId();
        if (id == 0 && homeDeviceData.getData() != null) {
            id = homeDeviceData.getData().getId();
        }
        HomePageDeviceAdapter homePageDeviceAdapter = this.homePageDeviceAdapter;
        if (homePageDeviceAdapter != null) {
            indexByDeviceTypeAndId = homePageDeviceAdapter.getIndexByDeviceTypeAndId(CommonUtils.getDeviceTypeByString(homeDeviceData.getType()), id);
            if (indexByDeviceTypeAndId != -1) {
                homeDeviceData.getData().setLoading(false);
                NewHomeCardData newHomeCardData = this.homePageDeviceAdapter.getDeviceDataList().get(indexByDeviceTypeAndId);
                newHomeCardData.setHomeDeviceData(homeDeviceData);
                this.homePageDeviceAdapter.getDeviceDataList().set(indexByDeviceTypeAndId, newHomeCardData);
                this.homePageDeviceAdapter.notifyItemChanged(indexByDeviceTypeAndId);
            }
        } else {
            indexByDeviceTypeAndId = -1;
        }
        HomePageLargeDeviceAdapter homePageLargeDeviceAdapter = this.homePageLargeDeviceAdapter;
        if (homePageLargeDeviceAdapter != null) {
            indexByDeviceTypeAndId2 = homePageLargeDeviceAdapter.getIndexByDeviceTypeAndId(CommonUtils.getDeviceTypeByString(homeDeviceData.getType()), id);
            if (indexByDeviceTypeAndId2 != -1) {
                homeDeviceData.getData().setLoading(false);
                NewHomeCardData newHomeCardData2 = this.homePageLargeDeviceAdapter.getDeviceDataList().get(indexByDeviceTypeAndId2);
                newHomeCardData2.setHomeDeviceData(homeDeviceData);
                this.homePageLargeDeviceAdapter.getDeviceDataList().set(indexByDeviceTypeAndId2, newHomeCardData2);
                this.homePageLargeDeviceAdapter.notifyItemChanged(indexByDeviceTypeAndId2);
            }
        } else {
            indexByDeviceTypeAndId2 = -1;
        }
        if (indexByDeviceTypeAndId == -1 && indexByDeviceTypeAndId2 == -1) {
            return;
        }
        DeviceCenterUtils.updateDeviceCard(homeDeviceData);
    }

    @Override // com.petkit.android.activities.home.contract.HomePageContract.View
    public void stopLoad() {
        this.srl.finishRefresh();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        int id = view.getId();
        if (id == R.id.tv_title || id == R.id.tv_top_title || id == R.id.tv_top_title_share || id == R.id.tv_title_share) {
            if (PetKitClickUtils.isFastClick()) {
                return;
            }
            changeFamily();
            return;
        }
        if (id == R.id.iv_swift_card_mode) {
            if (jCurrentTimeMillis - this.lastTime < 500) {
                return;
            }
            this.lastTime = jCurrentTimeMillis;
            if (this.cardMode == 0) {
                this.isRefresh = false;
                this.isSwitchCardMode = true;
                this.cardMode = 1;
            } else {
                this.isRefresh = false;
                this.isSwitchCardMode = true;
                this.cardMode = 0;
            }
            swiftCardMode();
            ((HomePagePresenter) this.mPresenter).saveCardModeSetting(this.cardMode);
            if (this.cardMode == 1) {
                new Handler().postDelayed(new Runnable() { // from class: com.petkit.android.activities.home.HomePageFragment$$ExternalSyntheticLambda37
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onClick$11();
                    }
                }, 300L);
                return;
            } else {
                new Handler().postDelayed(new Runnable() { // from class: com.petkit.android.activities.home.HomePageFragment$$ExternalSyntheticLambda38
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onClick$12();
                    }
                }, 300L);
                return;
            }
        }
        if (id == R.id.rl_mask) {
            this.rlMask.setVisibility(8);
            this.ivBlurBg.setVisibility(8);
            this.transparentMaskView.setVisibility(8);
            HomePageListener homePageListener = this.homePageListener;
            if (homePageListener != null) {
                homePageListener.dismiss(false);
                return;
            }
            return;
        }
        if (id == R.id.rl_pet_error) {
            startActivity(new Intent(getContext(), (Class<?>) PetErrorListActivity.class));
            return;
        }
        if (id == R.id.ib_top_title_add_device || id == R.id.rl_add_device || id == R.id.ib_add_device) {
            if (jCurrentTimeMillis - this.lastTime <= 500 || FamilyUtils.getInstance().getCurrentFamilyInfo(getActivity()) == null) {
                return;
            }
            if (Long.parseLong(UserInforUtils.getCurrentUserId(getActivity())) != FamilyUtils.getInstance().getCurrentFamilyInfo(getActivity()).getOwner()) {
                PetkitToast.showTopToast(requireActivity(), getResources().getString(R.string.Bind_device_family_check_prompt), R.drawable.top_toast_warn_icon, 1);
                return;
            }
            this.lastTime = jCurrentTimeMillis;
            EventBus.getDefault().post(new StopDeviceScanMsg());
            Intent intent = new Intent(getContext(), (Class<?>) NearByDeviceActivity.class);
            intent.putExtra(Constants.EXTRA_PET_ID, PetUtils.ALL_DEVICE);
            startActivity(intent);
            return;
        }
        if (id == R.id.ib_history_msg || id == R.id.ib_top_title_history_msg) {
            startActivity(HistoryMsgActivity.newIntent(getActivity()));
        }
    }

    public /* synthetic */ void lambda$onClick$11() {
        if (!isDetached() && HomeGuideUtils.getInstance().canShowGuideLarge()) {
            showFourthGuideLarge();
        }
    }

    public /* synthetic */ void lambda$onClick$12() {
        if (!isDetached() && HomeGuideUtils.getInstance().canShowGuideSmall()) {
            showFourthGuide();
        }
    }

    private void changeFamily() {
        FamilySwitchWindow familySwitchWindow = new FamilySwitchWindow(getActivity(), this.tvTitle.getBottom() + 100 + ImmersionBar.getStatusBarHeight(requireActivity()));
        familySwitchWindow.setFamilySwitchOnClickListener(this);
        familySwitchWindow.setNeedAllFamily(false);
        familySwitchWindow.setNeedFamilyManager(true);
        familySwitchWindow.setOutsideTouchable(true);
        familySwitchWindow.setCurrentFamilyId(FamilyUtils.getInstance().getCurrentFamilyId(getContext()));
        familySwitchWindow.setClippingEnabled(false);
        if (familySwitchWindow.isShowing()) {
            return;
        }
        familySwitchWindow.showAtLocation(requireActivity().getWindow().getDecorView(), 17, 0, 0);
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == -1) {
            ChatCenter chatCenter = ChatUtils.getChatCenter(CommonUtils.getCurrentUserId());
            if (i == 4656) {
                chatCenter.setChatlastcommenttime(System.currentTimeMillis());
                SugarRecord.save(chatCenter);
                return;
            }
            if (i == 4657) {
                chatCenter.setChatlastfavortime(System.currentTimeMillis());
                SugarRecord.save(chatCenter);
            } else if (i == 4658) {
                chatCenter.setChatlastnotifytime(System.currentTimeMillis());
                SugarRecord.save(chatCenter);
            } else if (i == 4659) {
                chatCenter.setChatlastattime(System.currentTimeMillis());
                SugarRecord.save(chatCenter);
            }
        }
    }

    public void cameraDeviceEnterDeviceView(long j, int i) {
        if (i != 21) {
            switch (i) {
                case 25:
                    if (D4shUtils.getD4shRecordByDeviceId(j, 0) == null) {
                        ((HomePagePresenter) this.mPresenter).startActivityForSharedDeviceId(getActivity(), D4shHomeActivity.class, j, "D4sh", new String[0]);
                    } else {
                        Bundle bundle = new Bundle();
                        bundle.putLong(Constants.EXTRA_DEVICE_ID, j);
                        bundle.putInt(Constants.EXTRA_DEVICE_TYPE_CODE, 0);
                        Intent intent = new Intent(getActivity(), (Class<?>) D4shHomeActivity.class);
                        intent.putExtras(bundle);
                        launchActivity(intent);
                    }
                    break;
                case 26:
                    if (D4shUtils.getD4shRecordByDeviceId(j, 1) == null) {
                        ((HomePagePresenter) this.mPresenter).startActivityForSharedDeviceId(getActivity(), D4shHomeActivity.class, j, "D4h", new String[0]);
                    } else {
                        Bundle bundle2 = new Bundle();
                        bundle2.putLong(Constants.EXTRA_DEVICE_ID, j);
                        bundle2.putInt(Constants.EXTRA_DEVICE_TYPE_CODE, 1);
                        Intent intent2 = new Intent(getActivity(), (Class<?>) D4shHomeActivity.class);
                        intent2.putExtras(bundle2);
                        launchActivity(intent2);
                    }
                    break;
                case 27:
                    if (T6Utils.getT6RecordByDeviceId(j, 0) == null) {
                        ((HomePagePresenter) this.mPresenter).startActivityForSharedDeviceId(getActivity(), T6HomeActivity.class, j, "T6", new String[0]);
                    } else {
                        Bundle bundle3 = new Bundle();
                        bundle3.putLong(Constants.EXTRA_DEVICE_ID, j);
                        bundle3.putInt(Constants.EXTRA_DEVICE_TYPE, 27);
                        Intent intent3 = new Intent(getActivity(), (Class<?>) T6HomeActivity.class);
                        intent3.putExtras(bundle3);
                        launchActivity(intent3);
                    }
                    break;
                case 28:
                    if (T7DataUtils.getInstance().getT7RecordById(j) == null) {
                        ((HomePagePresenter) this.mPresenter).startActivityForSharedDeviceId(getActivity(), T7HomeActivity.class, j, "T7", new String[0]);
                    } else {
                        Bundle bundle4 = new Bundle();
                        bundle4.putLong(Constants.EXTRA_DEVICE_ID, j);
                        bundle4.putInt(Constants.EXTRA_DEVICE_TYPE, 27);
                        Intent intent4 = new Intent(getActivity(), (Class<?>) T7HomeActivity.class);
                        intent4.putExtras(bundle4);
                        launchActivity(intent4);
                    }
                    break;
                case 29:
                    if (W7hDataUtils.getInstance().getW7hRecordByDeviceId(j) == null) {
                        ((HomePagePresenter) this.mPresenter).startActivityForSharedDeviceId(getActivity(), W7hHomeActivity.class, j, "W7H", new String[0]);
                    } else {
                        Bundle bundle5 = new Bundle();
                        bundle5.putLong(Constants.EXTRA_DEVICE_ID, j);
                        bundle5.putInt(Constants.EXTRA_DEVICE_TYPE, 29);
                        Intent intent5 = new Intent(getActivity(), (Class<?>) W7hHomeActivity.class);
                        intent5.putExtras(bundle5);
                        launchActivity(intent5);
                    }
                    break;
            }
        }
        if (T6Utils.getT6RecordByDeviceId(j, 1) == null) {
            ((HomePagePresenter) this.mPresenter).startActivityForSharedDeviceId(getActivity(), T5HomeActivity.class, j, "T5", new String[0]);
            return;
        }
        Bundle bundle6 = new Bundle();
        bundle6.putLong(Constants.EXTRA_DEVICE_ID, j);
        bundle6.putInt(Constants.EXTRA_DEVICE_TYPE, 21);
        Intent intent6 = new Intent(getActivity(), (Class<?>) T5HomeActivity.class);
        intent6.putExtras(bundle6);
        launchActivity(intent6);
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // com.petkit.android.activities.home.adapter.HomePageToolAdapter.OnClickListener, com.petkit.android.activities.home.NewCardOnClickListener
    public void onClick(HomeDeviceData homeDeviceData, String... strArr) {
        String str;
        String str2;
        if (this.isDrag) {
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (jCurrentTimeMillis - this.clickToDeviceHomeTime < 2000) {
            return;
        }
        this.clickToDeviceHomeTime = jCurrentTimeMillis;
        new HashMap().put("type", homeDeviceData.getType());
        if (!Constants.TODO_TYPE_REMIND.equals(homeDeviceData.getType()) && !"walkpet".equals(homeDeviceData.getType())) {
            "weight".equals(homeDeviceData.getType());
        }
        long id = homeDeviceData.getId();
        if (id == 0) {
            id = homeDeviceData.getData().getId();
        }
        String type = homeDeviceData.getType();
        type.hashCode();
        byte b = -1;
        switch (type.hashCode()) {
            case -1549854238:
                if (type.equals("FeederMini")) {
                    b = 0;
                }
                break;
            case -934616827:
                if (type.equals(Constants.TODO_TYPE_REMIND)) {
                    b = 1;
                }
                break;
            case -791592328:
                if (type.equals("weight")) {
                    b = 2;
                }
                break;
            case 2128:
                if (type.equals("Aq")) {
                    b = 3;
                }
                break;
            case 2159:
                if (type.equals("D3")) {
                    b = 4;
                }
                break;
            case DisplayCompat.DISPLAY_SIZE_4K_HEIGHT /* 2160 */:
                if (type.equals("D4")) {
                    b = 5;
                }
                break;
            case 2280:
                if (type.equals("GO")) {
                    b = 6;
                }
                break;
            case 2303:
                if (type.equals("HG")) {
                    b = 7;
                }
                break;
            case 2312:
                if (type.equals("Go")) {
                    b = 8;
                }
                break;
            case 2335:
                if (type.equals("Hg")) {
                    b = 9;
                }
                break;
            case 2375:
                if (type.equals("K2")) {
                    b = 10;
                }
                break;
            case 2376:
                if (type.equals("K3")) {
                    b = 11;
                }
                break;
            case 2531:
                if (type.equals("P3")) {
                    b = 12;
                }
                break;
            case 2592:
                if (type.equals("R2")) {
                    b = 13;
                }
                break;
            case 2655:
                if (type.equals("T3")) {
                    b = 14;
                }
                break;
            case 2656:
                if (type.equals("T4")) {
                    b = 15;
                }
                break;
            case 2657:
                if (type.equals("T5")) {
                    b = 16;
                }
                break;
            case 2658:
                if (type.equals("T6")) {
                    b = 17;
                }
                break;
            case 2659:
                if (type.equals("T7")) {
                    b = 18;
                }
                break;
            case 2750:
                if (type.equals("W5")) {
                    b = 19;
                }
                break;
            case 66082:
                if (type.equals("Aqr")) {
                    b = 20;
                }
                break;
            case 67032:
                if (type.equals("D4H")) {
                    b = 21;
                }
                break;
            case 67064:
                if (type.equals("D4h")) {
                    b = 22;
                }
                break;
            case 67075:
                if (type.equals("D4s")) {
                    b = 23;
                }
                break;
            case 69617:
                if (type.equals("FIT")) {
                    b = Ascii.CAN;
                }
                break;
            case 70641:
                if (type.equals("Fit")) {
                    b = Ascii.EM;
                }
                break;
            case 85384:
                if (type.equals("W7H")) {
                    b = Ascii.SUB;
                }
                break;
            case 85416:
                if (type.equals("W7h")) {
                    b = Ascii.ESC;
                }
                break;
            case 2047289:
                if (type.equals("AqH1")) {
                    b = Ascii.FS;
                }
                break;
            case 2074795:
                if (type.equals("COZY")) {
                    b = Ascii.GS;
                }
                break;
            case 2079429:
                if (type.equals("D4sh")) {
                    b = Ascii.RS;
                }
                break;
            case 2079469:
                if (type.equals("CTW3")) {
                    b = Ascii.US;
                }
                break;
            case 2106571:
                if (type.equals("Cozy")) {
                    b = 32;
                }
                break;
            case 2111213:
                if (type.equals("Ctw3")) {
                    b = Framer.ENTER_FRAME_PREFIX;
                }
                break;
            case 2359045:
                if (type.equals("MATE")) {
                    b = 34;
                }
                break;
            case 2390821:
                if (type.equals("Mate")) {
                    b = 35;
                }
                break;
            case 3064525:
                if (type.equals("ctw3")) {
                    b = 36;
                }
                break;
            case 1118822070:
                if (type.equals("walkpet")) {
                    b = 37;
                }
                break;
            case 1237693858:
                if (type.equals("FEEDERMINI")) {
                    b = 38;
                }
                break;
            case 1982491468:
                if (type.equals("Banner")) {
                    b = 39;
                }
                break;
            case 2069886667:
                if (type.equals("FEEDER")) {
                    b = 40;
                }
                break;
            case 2100424427:
                if (type.equals("Feeder")) {
                    b = 41;
                }
                break;
        }
        switch (b) {
            case 0:
            case 38:
                long j = id;
                D2Record d2RecordByDeviceId = D2Utils.getD2RecordByDeviceId(j);
                if (d2RecordByDeviceId == null) {
                    D2Utils.startActivityForD2SharedDeviceid(getActivity(), D2HomeActivity.class, j);
                } else if (d2RecordByDeviceId.getDeviceShared() == null && TextUtils.isEmpty(d2RecordByDeviceId.getName())) {
                    startActivity(DeviceSetInfoActivity.newIntent(getContext(), j, 6, false));
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putLong(Constants.EXTRA_DEVICE_ID, j);
                    Intent intent = new Intent(getActivity(), (Class<?>) D2HomeActivity.class);
                    intent.putExtras(bundle);
                    launchActivity(intent);
                }
                break;
            case 1:
                Intent intent2 = new Intent(getActivity(), (Class<?>) HealthRemindActivity2_0.class);
                intent2.putExtra(Constants.EXTRA_PET_ID, PetUtils.ALL_DEVICE);
                startActivity(intent2);
                break;
            case 2:
                if (this.pet == null) {
                    launchActivity(new Intent(getActivity(), (Class<?>) PetsFeedListActivity.class));
                } else {
                    startActivity(HealthyFeedActivity.newIntent(getContext(), this.pet.getId(), false));
                }
                break;
            case 3:
                long j2 = id;
                AqRecord aqRecordByDeviceId = AqUtils.getAqRecordByDeviceId(j2);
                EventBus.getDefault().post(new StopDeviceScanMsg());
                if (aqRecordByDeviceId == null) {
                    if (homeDeviceData.getData().getTypeCode() == 1) {
                        ((HomePagePresenter) this.mPresenter).startActivityForSharedDeviceId(getActivity(), AqHomeActivity.class, j2, homeDeviceData.getType(), new String[0]);
                    } else if (homeDeviceData.getData().getTypeCode() == 2) {
                        ((HomePagePresenter) this.mPresenter).startActivityForSharedDeviceId(getActivity(), Aq1sHomeActivity.class, j2, homeDeviceData.getType(), new String[0]);
                    } else if (homeDeviceData.getData().getTypeCode() == 3) {
                        ((HomePagePresenter) this.mPresenter).startActivityForSharedDeviceId(getActivity(), Aq1sHomeActivity.class, j2, homeDeviceData.getType(), new String[0]);
                    }
                } else {
                    Bundle bundle2 = new Bundle();
                    bundle2.putLong(Constants.EXTRA_DEVICE_ID, j2);
                    if (homeDeviceData.getData().getTypeCode() == 1) {
                        Intent intent3 = new Intent(getActivity(), (Class<?>) AqHomeActivity.class);
                        intent3.putExtras(bundle2);
                        launchActivity(intent3);
                    } else if (homeDeviceData.getData().getTypeCode() == 2) {
                        Intent intent4 = new Intent(getActivity(), (Class<?>) Aq1sHomeActivity.class);
                        bundle2.putLong(Constants.EXTRA_TYPE_CODE, homeDeviceData.getData().getTypeCode());
                        intent4.putExtras(bundle2);
                        launchActivity(intent4);
                    } else if (homeDeviceData.getData().getTypeCode() == 3) {
                        Intent intent5 = new Intent(getActivity(), (Class<?>) Aq1sHomeActivity.class);
                        bundle2.putLong(Constants.EXTRA_TYPE_CODE, homeDeviceData.getData().getTypeCode());
                        intent5.putExtras(bundle2);
                        launchActivity(intent5);
                    }
                }
                break;
            case 4:
                long j3 = id;
                if (j3 < 0) {
                    D3Record deviceDetail = ((DeviceRegularDataResult) JSON.parseObject(DataHelper.getStringSF(getActivity(), Constants.VIRTUAL_DEVICE_DATA), DeviceRegularDataResult.class)).getResult().getD3().getDeviceDetail();
                    deviceDetail.save();
                    launchActivity(VirtualD3HomeActivity.newIntent(getActivity(), deviceDetail.getDeviceId()));
                } else if (D3Utils.getD3RecordByDeviceId(j3) == null) {
                    ((HomePagePresenter) this.mPresenter).startActivityForSharedDeviceId(getActivity(), D3HomeActivity.class, j3, homeDeviceData.getType(), strArr);
                } else {
                    Bundle bundle3 = new Bundle();
                    bundle3.putLong(Constants.EXTRA_DEVICE_ID, j3);
                    if (strArr != null && strArr.length > 0) {
                        bundle3.putString(Consts.APP_WIDGET_EXTRA_DATA, strArr[0]);
                    }
                    Intent intent6 = new Intent(getActivity(), (Class<?>) D3HomeActivity.class);
                    intent6.putExtras(bundle3);
                    launchActivity(intent6);
                }
                break;
            case 5:
                long j4 = id;
                if (D4Utils.getD4RecordByDeviceId(j4) == null) {
                    ((HomePagePresenter) this.mPresenter).startActivityForSharedDeviceId(getActivity(), D4HomeActivity.class, j4, homeDeviceData.getType(), strArr);
                } else {
                    Bundle bundle4 = new Bundle();
                    bundle4.putLong(Constants.EXTRA_DEVICE_ID, j4);
                    if (strArr != null && strArr.length > 0) {
                        bundle4.putString(Consts.APP_WIDGET_EXTRA_DATA, strArr[0]);
                    }
                    Intent intent7 = new Intent(getActivity(), (Class<?>) D4HomeActivity.class);
                    intent7.putExtras(bundle4);
                    launchActivity(intent7);
                }
                break;
            case 6:
            case 8:
                if (Build.VERSION.SDK_INT >= 31) {
                    if (CommonUtils.checkPermission(getActivity(), "android.permission.ACCESS_FINE_LOCATION") && CommonUtils.checkPermission(getActivity(), "android.permission.WRITE_EXTERNAL_STORAGE") && CommonUtils.checkPermission(getActivity(), "android.permission.BLUETOOTH_SCAN") && CommonUtils.checkPermission(getActivity(), AudioDeviceInventoryLowerThanM.PERMISSION_BLUETOOTH_CONNECT) && CommonUtils.checkPermission(getActivity(), "android.permission.BLUETOOTH_ADVERTISE")) {
                        if (WalkDataUtils.getWalkDataByState(1) != null) {
                            launchActivity(new Intent(getActivity(), (Class<?>) WalkingActivity.class));
                            break;
                        } else {
                            long id2 = homeDeviceData.getId();
                            GoRecord goRecordById = GoDataUtils.getGoRecordById(id2);
                            if (goRecordById != null) {
                                if (goRecordById.getGoal() <= 0) {
                                    Bundle bundle5 = new Bundle();
                                    bundle5.putLong(GoDataUtils.EXTRA_GO_ID, id2);
                                    bundle5.putInt(Constants.EXTRA_TYPE, 2);
                                    Intent intent8 = new Intent(getActivity(), (Class<?>) GoSettingTargetActivity.class);
                                    intent8.putExtras(bundle5);
                                    launchActivity(intent8);
                                } else if (Select.from(GoWalkData.class).where(Condition.prop("ownerId").eq(Long.valueOf(CommonUtils.getCurrentUserId()))).where(Condition.prop("state").eq(1)).where(Condition.prop("deviceId").eq(Long.valueOf(id2))).count() > 0) {
                                    Bundle bundle6 = new Bundle();
                                    bundle6.putLong(Constants.EXTRA_TAG_ID, id2);
                                    Intent intent9 = new Intent(getActivity(), (Class<?>) GoWalkingActivity.class);
                                    intent9.putExtras(bundle6);
                                    launchActivity(intent9);
                                } else {
                                    Bundle bundle7 = new Bundle();
                                    bundle7.putLong(GoDataUtils.EXTRA_GO_ID, id2);
                                    bundle7.putInt(Constants.EXTRA_TYPE, 1);
                                    Intent intent10 = new Intent(getActivity(), (Class<?>) GoHomeActivity.class);
                                    intent10.putExtras(bundle7);
                                    launchActivity(intent10);
                                }
                                break;
                            }
                        }
                    } else {
                        startActivity(PermissionDialogActivity.newIntent(getActivity(), HomePageFragment.class.getName(), "android.permission.ACCESS_FINE_LOCATION", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.BLUETOOTH_SCAN", AudioDeviceInventoryLowerThanM.PERMISSION_BLUETOOTH_CONNECT, "android.permission.BLUETOOTH_ADVERTISE"));
                        break;
                    }
                } else if (CommonUtils.checkPermission(getActivity(), "android.permission.ACCESS_FINE_LOCATION") && CommonUtils.checkPermission(getActivity(), "android.permission.WRITE_EXTERNAL_STORAGE")) {
                    if (WalkDataUtils.getWalkDataByState(1) != null) {
                        launchActivity(new Intent(getActivity(), (Class<?>) WalkingActivity.class));
                        break;
                    } else {
                        long id3 = homeDeviceData.getId();
                        GoRecord goRecordById2 = GoDataUtils.getGoRecordById(id3);
                        if (goRecordById2 != null) {
                            if (goRecordById2.getGoal() <= 0) {
                                Bundle bundle8 = new Bundle();
                                bundle8.putLong(GoDataUtils.EXTRA_GO_ID, id3);
                                bundle8.putInt(Constants.EXTRA_TYPE, 2);
                                Intent intent11 = new Intent(getActivity(), (Class<?>) GoSettingTargetActivity.class);
                                intent11.putExtras(bundle8);
                                launchActivity(intent11);
                            } else if (Select.from(GoWalkData.class).where(Condition.prop("ownerId").eq(Long.valueOf(CommonUtils.getCurrentUserId()))).where(Condition.prop("state").eq(1)).where(Condition.prop("deviceId").eq(Long.valueOf(id3))).count() > 0) {
                                Bundle bundle9 = new Bundle();
                                bundle9.putLong(Constants.EXTRA_TAG_ID, id3);
                                Intent intent12 = new Intent(getActivity(), (Class<?>) GoWalkingActivity.class);
                                intent12.putExtras(bundle9);
                                launchActivity(intent12);
                            } else {
                                Bundle bundle10 = new Bundle();
                                bundle10.putLong(GoDataUtils.EXTRA_GO_ID, id3);
                                bundle10.putInt(Constants.EXTRA_TYPE, 1);
                                Intent intent13 = new Intent(getActivity(), (Class<?>) GoHomeActivity.class);
                                intent13.putExtras(bundle10);
                                launchActivity(intent13);
                            }
                            break;
                        }
                    }
                } else {
                    startActivity(PermissionDialogActivity.newIntent(getActivity(), HomePageFragment.class.getName(), "android.permission.ACCESS_FINE_LOCATION", "android.permission.WRITE_EXTERNAL_STORAGE"));
                    break;
                }
                break;
            case 7:
            case 9:
                long j5 = id;
                if (j5 < 0) {
                    HgRecord deviceData = ((DeviceRegularDataResult) JSON.parseObject(DataHelper.getStringSF(getActivity(), Constants.VIRTUAL_DEVICE_DATA), DeviceRegularDataResult.class)).getResult().getHg().getDeviceData();
                    deviceData.save();
                    launchActivity(VirtualHgHomeActivity.newIntent(getActivity(), deviceData.getDeviceId(), false));
                } else {
                    HgRecord hgRecordByDeviceId = HgUtils.getHgRecordByDeviceId(j5);
                    if (hgRecordByDeviceId == null) {
                        ((HomePagePresenter) this.mPresenter).startActivityForSharedDeviceId(getActivity(), HgHomeActivity.class, j5, homeDeviceData.getType(), new String[0]);
                    } else if (hgRecordByDeviceId.getIsActive() == 0) {
                        launchActivity(BleDeviceBindCompleteActivity.newIntent(getContext(), j5, 22, true));
                    } else {
                        Bundle bundle11 = new Bundle();
                        bundle11.putLong(Constants.EXTRA_DEVICE_ID, j5);
                        bundle11.putBoolean(Constants.EXTRA_DEVICE_IS_BIND, false);
                        Intent intent14 = new Intent(getActivity(), (Class<?>) HgHomeActivity.class);
                        intent14.putExtras(bundle11);
                        launchActivity(intent14);
                    }
                }
                break;
            case 10:
                long j6 = id;
                if (BleDeviceUtils.getK2RecordByDeviceId(j6) == null) {
                    ((HomePagePresenter) this.mPresenter).startActivityForSharedDeviceId(getActivity(), K2HomeActivity.class, j6, homeDeviceData.getType(), new String[0]);
                } else {
                    Bundle bundle12 = new Bundle();
                    bundle12.putLong(Constants.EXTRA_DEVICE_ID, j6);
                    Intent intent15 = new Intent(getActivity(), (Class<?>) K2HomeActivity.class);
                    intent15.putExtras(bundle12);
                    launchActivity(intent15);
                }
                break;
            case 11:
                long j7 = id;
                if (K3Utils.getK3RecordByDeviceId(j7) == null) {
                    ((HomePagePresenter) this.mPresenter).startActivityForSharedDeviceId(getActivity(), K3HomeActivity.class, j7, homeDeviceData.getType(), new String[0]);
                } else {
                    Bundle bundle13 = new Bundle();
                    bundle13.putLong(Constants.EXTRA_DEVICE_ID, j7);
                    Intent intent16 = new Intent(getActivity(), (Class<?>) K3HomeActivity.class);
                    intent16.putExtras(bundle13);
                    launchActivity(intent16);
                }
                break;
            case 12:
                long j8 = id;
                if (P3Utils.getP3RecordByDeviceId(j8) == null) {
                    ((HomePagePresenter) this.mPresenter).startActivityForSharedDeviceId(getActivity(), P3MainActivity.class, j8, homeDeviceData.getType(), new String[0]);
                } else {
                    Bundle bundle14 = new Bundle();
                    bundle14.putLong(Constants.EXTRA_DEVICE_ID, j8);
                    Intent intent17 = new Intent(getActivity(), (Class<?>) P3MainActivity.class);
                    intent17.putExtras(bundle14);
                    launchActivity(intent17);
                }
                break;
            case 13:
                long j9 = id;
                if (R2Utils.getR2RecordByDeviceId(j9) == null) {
                    ((HomePagePresenter) this.mPresenter).startActivityForSharedDeviceId(getActivity(), R2HomeActivity.class, j9, homeDeviceData.getType(), new String[0]);
                } else {
                    Bundle bundle15 = new Bundle();
                    bundle15.putLong(Constants.EXTRA_DEVICE_ID, j9);
                    Intent intent18 = new Intent(getActivity(), (Class<?>) R2HomeActivity.class);
                    intent18.putExtras(bundle15);
                    launchActivity(intent18);
                }
                break;
            case 14:
                long j10 = id;
                if (BleDeviceUtils.getT3RecordByDeviceId(j10) == null) {
                    ((HomePagePresenter) this.mPresenter).startActivityForSharedDeviceId(getActivity(), T3HomeActivity.class, j10, homeDeviceData.getType(), strArr);
                } else {
                    Bundle bundle16 = new Bundle();
                    bundle16.putLong(Constants.EXTRA_DEVICE_ID, j10);
                    if (strArr != null && strArr.length > 0) {
                        bundle16.putString(Consts.APP_WIDGET_EXTRA_DATA, strArr[0]);
                    }
                    Intent intent19 = new Intent(getActivity(), (Class<?>) T3HomeActivity.class);
                    intent19.putExtras(bundle16);
                    launchActivity(intent19);
                }
                break;
            case 15:
                long j11 = id;
                if (T4Utils.clickEnable()) {
                    if (j11 < 0) {
                        T4Record deviceDetail2 = ((DeviceRegularDataResult) JSON.parseObject(DataHelper.getStringSF(getActivity(), Constants.VIRTUAL_DEVICE_DATA), DeviceRegularDataResult.class)).getResult().getT4().getDeviceDetail();
                        deviceDetail2.save();
                        launchActivity(VirtualT4HomeActivity.newIntent(getActivity(), deviceDetail2.getDeviceId()));
                    } else if (T4Utils.getT4RecordByDeviceId(j11) == null) {
                        ((HomePagePresenter) this.mPresenter).startActivityForSharedDeviceId(getActivity(), T4HomeActivity.class, j11, homeDeviceData.getType(), strArr);
                    } else {
                        Bundle bundle17 = new Bundle();
                        bundle17.putLong(Constants.EXTRA_DEVICE_ID, j11);
                        if (strArr != null && strArr.length > 0) {
                            bundle17.putString(Consts.APP_WIDGET_EXTRA_DATA, strArr[0]);
                        }
                        Intent intent20 = new Intent(getActivity(), (Class<?>) T4HomeActivity.class);
                        intent20.putExtras(bundle17);
                        launchActivity(intent20);
                    }
                    break;
                }
                break;
            case 16:
                long j12 = id;
                if (homeDeviceData.getData().getStatus().getAgree() != null && homeDeviceData.getData().getStatus().getAgree().intValue() != 1) {
                    refreshCameraPrivacyPolicy(homeDeviceData.getData().getStatus().getAgree().intValue(), j12, 21, homeDeviceData.getData().getStatus().getVersionNo());
                } else if (T6Utils.getT6RecordByDeviceId(j12, 1) == null) {
                    ((HomePagePresenter) this.mPresenter).startActivityForSharedDeviceId(getActivity(), T5HomeActivity.class, j12, "T5", new String[0]);
                } else {
                    Bundle bundle18 = new Bundle();
                    bundle18.putLong(Constants.EXTRA_DEVICE_ID, j12);
                    bundle18.putInt(Constants.EXTRA_DEVICE_TYPE, 21);
                    if (strArr != null && strArr.length > 0) {
                        bundle18.putString(Consts.APP_WIDGET_EXTRA_DATA, strArr[0]);
                    }
                    Intent intent21 = new Intent(getActivity(), (Class<?>) T5HomeActivity.class);
                    intent21.putExtras(bundle18);
                    launchActivity(intent21);
                }
                break;
            case 17:
                long j13 = id;
                if (j13 < 0) {
                    DeviceRegularDataResult deviceRegularDataResult = (DeviceRegularDataResult) JSON.parseObject(DataHelper.getStringSF(getActivity(), Constants.VIRTUAL_DEVICE_DATA), DeviceRegularDataResult.class);
                    T6Record deviceDetail3 = deviceRegularDataResult.getResult().getT6().getDeviceDetail();
                    deviceRegularDataResult.getResult().getT6().getDeviceDetail().setTypeCode(0);
                    deviceRegularDataResult.getResult().getT6().getDeviceDetail().setDeviceId(j13);
                    deviceDetail3.save();
                    launchActivity(VirtualT6HomeActivity.newIntent(getActivity(), j13, 0));
                } else if (homeDeviceData.getData().getStatus().getAgree() != null && homeDeviceData.getData().getStatus().getAgree().intValue() != 1) {
                    refreshCameraPrivacyPolicy(homeDeviceData.getData().getStatus().getAgree().intValue(), j13, 27, homeDeviceData.getData().getStatus().getVersionNo());
                } else if (T6Utils.getT6RecordByDeviceId(j13, 0) == null) {
                    ((HomePagePresenter) this.mPresenter).startActivityForSharedDeviceId(getActivity(), T6HomeActivity.class, j13, "T6", new String[0]);
                } else {
                    Bundle bundle19 = new Bundle();
                    bundle19.putLong(Constants.EXTRA_DEVICE_ID, j13);
                    bundle19.putInt(Constants.EXTRA_DEVICE_TYPE, 27);
                    if (strArr != null && strArr.length > 0) {
                        bundle19.putString(Consts.APP_WIDGET_EXTRA_DATA, strArr[0]);
                    }
                    Intent intent22 = new Intent(getActivity(), (Class<?>) T6HomeActivity.class);
                    intent22.putExtras(bundle19);
                    launchActivity(intent22);
                }
                break;
            case 18:
                long j14 = id;
                if (homeDeviceData.getData().getStatus().getAgree() != null && homeDeviceData.getData().getStatus().getAgree().intValue() != 1) {
                    refreshCameraPrivacyPolicy(homeDeviceData.getData().getStatus().getAgree().intValue(), j14, 28, homeDeviceData.getData().getStatus().getVersionNo());
                } else if (T7DataUtils.getInstance().getT7RecordById(j14) == null) {
                    ((HomePagePresenter) this.mPresenter).startActivityForSharedDeviceId(getActivity(), T7HomeActivity.class, j14, "T7", new String[0]);
                } else {
                    Bundle bundle20 = new Bundle();
                    bundle20.putLong(Constants.EXTRA_DEVICE_ID, j14);
                    bundle20.putInt(Constants.EXTRA_DEVICE_TYPE, 28);
                    if (strArr != null && strArr.length > 0) {
                        bundle20.putString(Consts.APP_WIDGET_EXTRA_DATA, strArr[0]);
                    }
                    Intent intent23 = new Intent(getActivity(), (Class<?>) T7HomeActivity.class);
                    intent23.putExtras(bundle20);
                    launchActivity(intent23);
                }
                break;
            case 19:
                long j15 = id;
                if (j15 < 0) {
                    W5Record deviceData2 = ((DeviceRegularDataResult) JSON.parseObject(DataHelper.getStringSF(getActivity(), Constants.VIRTUAL_DEVICE_DATA), DeviceRegularDataResult.class)).getResult().getW5().getDeviceData();
                    deviceData2.save();
                    launchActivity(VirtualW5HomeActivity.newIntent(getActivity(), deviceData2.getDeviceId()));
                } else if (W5Utils.getW5RecordByDeviceId(j15) == null) {
                    ((HomePagePresenter) this.mPresenter).startActivityForSharedDeviceId(getActivity(), W5HomeActivity.class, j15, homeDeviceData.getType(), strArr);
                } else {
                    Bundle bundle21 = new Bundle();
                    bundle21.putLong(Constants.EXTRA_DEVICE_ID, j15);
                    if (strArr != null && strArr.length > 0) {
                        bundle21.putString(Consts.APP_WIDGET_EXTRA_DATA, strArr[0]);
                    }
                    Intent intent24 = new Intent(getActivity(), (Class<?>) W5HomeActivity.class);
                    intent24.putExtras(bundle21);
                    launchActivity(intent24);
                }
                break;
            case 20:
                long j16 = id;
                if (AqrUtils.getAqrRecordByDeviceId(j16) == null) {
                    ((HomePagePresenter) this.mPresenter).startAqrById(getActivity(), AqrHomeActivity.class, j16);
                } else {
                    Bundle bundle22 = new Bundle();
                    bundle22.putLong(Constants.EXTRA_DEVICE_ID, j16);
                    Intent intent25 = new Intent(getActivity(), (Class<?>) AqrHomeActivity.class);
                    intent25.putExtras(bundle22);
                    launchActivity(intent25);
                }
                break;
            case 21:
            case 22:
                long j17 = id;
                if (j17 < 0) {
                    DeviceRegularDataResult deviceRegularDataResult2 = (DeviceRegularDataResult) JSON.parseObject(DataHelper.getStringSF(getActivity(), Constants.VIRTUAL_DEVICE_DATA), DeviceRegularDataResult.class);
                    D4shRecord deviceDetail4 = deviceRegularDataResult2.getResult().getD4h().getDeviceDetail();
                    deviceRegularDataResult2.getResult().getD4h().getDeviceDetail().setTypeCode(1);
                    deviceRegularDataResult2.getResult().getD4h().getDeviceDetail().setDeviceId(j17);
                    deviceDetail4.save();
                    launchActivity(VirtualD4shHomeActivity.newIntent(getActivity(), j17, 1));
                } else if (homeDeviceData.getData().getStatus().getAgree() != null && homeDeviceData.getData().getStatus().getAgree().intValue() != 1) {
                    refreshCameraPrivacyPolicy(homeDeviceData.getData().getStatus().getAgree().intValue(), j17, 26, homeDeviceData.getData().getStatus().getVersionNo());
                } else if (D4shUtils.getD4shRecordByDeviceId(j17, 1) == null) {
                    ((HomePagePresenter) this.mPresenter).startActivityForSharedDeviceId(getActivity(), D4shHomeActivity.class, j17, homeDeviceData.getType(), strArr);
                } else {
                    Bundle bundle23 = new Bundle();
                    bundle23.putLong(Constants.EXTRA_DEVICE_ID, j17);
                    bundle23.putInt(Constants.EXTRA_DEVICE_TYPE_CODE, 1);
                    if (strArr != null && strArr.length > 0) {
                        bundle23.putString(Consts.APP_WIDGET_EXTRA_DATA, strArr[0]);
                    }
                    Intent intent26 = new Intent(getActivity(), (Class<?>) D4shHomeActivity.class);
                    intent26.putExtras(bundle23);
                    launchActivity(intent26);
                }
                break;
            case 23:
                long j18 = id;
                if (D4sUtils.getD4sRecordByDeviceId(j18) == null) {
                    ((HomePagePresenter) this.mPresenter).startActivityForSharedDeviceId(getActivity(), D4sHomeActivity.class, j18, homeDeviceData.getType(), strArr);
                } else {
                    Bundle bundle24 = new Bundle();
                    bundle24.putLong(Constants.EXTRA_DEVICE_ID, j18);
                    if (strArr != null && strArr.length > 0) {
                        bundle24.putString(Consts.APP_WIDGET_EXTRA_DATA, strArr[0]);
                    }
                    Intent intent27 = new Intent(getActivity(), (Class<?>) D4sHomeActivity.class);
                    intent27.putExtras(bundle24);
                    launchActivity(intent27);
                }
                break;
            case 24:
            case 25:
                if (Build.VERSION.SDK_INT >= 31) {
                    if (CommonUtils.checkPermission(requireActivity(), "android.permission.BLUETOOTH_SCAN")) {
                        FragmentActivity activity = getActivity();
                        str2 = AudioDeviceInventoryLowerThanM.PERMISSION_BLUETOOTH_CONNECT;
                        if (CommonUtils.checkPermission(activity, str2)) {
                            str = "android.permission.BLUETOOTH_ADVERTISE";
                            if (CommonUtils.checkPermission(getActivity(), str)) {
                                Bundle bundle25 = new Bundle();
                                bundle25.putSerializable(Constants.EXTRA_DOG, UserInforUtils.getPetById(homeDeviceData.getData().getPetId()));
                                Intent intent28 = new Intent(getActivity(), (Class<?>) PetkitDetailActivity.class);
                                intent28.putExtras(bundle25);
                                launchActivity(intent28);
                            }
                        } else {
                            str = "android.permission.BLUETOOTH_ADVERTISE";
                        }
                    } else {
                        str = "android.permission.BLUETOOTH_ADVERTISE";
                        str2 = AudioDeviceInventoryLowerThanM.PERMISSION_BLUETOOTH_CONNECT;
                    }
                    startActivity(PermissionDialogActivity.newIntent(getActivity(), getClass().getName(), "android.permission.BLUETOOTH_SCAN", str2, str));
                } else {
                    Bundle bundle26 = new Bundle();
                    bundle26.putSerializable(Constants.EXTRA_DOG, UserInforUtils.getPetById(homeDeviceData.getData().getPetId()));
                    Intent intent29 = new Intent(getActivity(), (Class<?>) PetkitDetailActivity.class);
                    intent29.putExtras(bundle26);
                    launchActivity(intent29);
                }
                break;
            case 26:
            case 27:
                long j19 = id;
                if (homeDeviceData.getData().getStatus().getAgree() != null && homeDeviceData.getData().getStatus().getAgree().intValue() != 1) {
                    refreshCameraPrivacyPolicy(homeDeviceData.getData().getStatus().getAgree().intValue(), j19, 29, homeDeviceData.getData().getStatus().getVersionNo());
                } else if (W7hDataUtils.getInstance().getW7hRecordByDeviceId(j19) == null) {
                    ((HomePagePresenter) this.mPresenter).startActivityForSharedDeviceId(getActivity(), W7hHomeActivity.class, j19, "W7H", new String[0]);
                } else {
                    Bundle bundle27 = new Bundle();
                    bundle27.putLong(Constants.EXTRA_DEVICE_ID, j19);
                    bundle27.putInt(Constants.EXTRA_DEVICE_TYPE, 29);
                    if (strArr != null && strArr.length > 0) {
                        bundle27.putString(Consts.APP_WIDGET_EXTRA_DATA, strArr[0]);
                    }
                    Intent intent30 = new Intent(getActivity(), (Class<?>) W7hHomeActivity.class);
                    intent30.putExtras(bundle27);
                    launchActivity(intent30);
                }
                break;
            case 28:
                long j20 = id;
                if (Aqh1Utils.getAqh1RecordByDeviceId(j20) == null) {
                    ((HomePagePresenter) this.mPresenter).startActivityForSharedDeviceId(getActivity(), Aqh1HomeActivity.class, j20, homeDeviceData.getType(), new String[0]);
                } else {
                    Bundle bundle28 = new Bundle();
                    bundle28.putLong(Constants.EXTRA_DEVICE_ID, j20);
                    Intent intent31 = new Intent(getActivity(), (Class<?>) Aqh1HomeActivity.class);
                    intent31.putExtras(bundle28);
                    launchActivity(intent31);
                }
                break;
            case 29:
            case 32:
                long j21 = id;
                CozyRecord cozyRecordByDeviceId = CozyUtils.getCozyRecordByDeviceId(j21);
                if (cozyRecordByDeviceId == null) {
                    CozyUtils.startActivityForCozySharedDeviceid(getActivity(), CozyHomeActivity.class, j21);
                } else if (TextUtils.isEmpty(cozyRecordByDeviceId.getPetId()) && (cozyRecordByDeviceId.getOwner() == null || TextUtils.isEmpty(cozyRecordByDeviceId.getOwner().getPetId()))) {
                    startActivity(DeviceSetInfoActivity.newIntent(getContext(), j21, 5, false));
                } else {
                    Bundle bundle29 = new Bundle();
                    bundle29.putLong(Constants.EXTRA_DEVICE_ID, j21);
                    Intent intent32 = new Intent(getActivity(), (Class<?>) CozyHomeActivity.class);
                    intent32.putExtras(bundle29);
                    launchActivity(intent32);
                }
                break;
            case 30:
                long j22 = id;
                D4shRecord d4shRecordByDeviceId = D4shUtils.getD4shRecordByDeviceId(j22, 0);
                if (j22 < 0) {
                    DeviceRegularDataResult deviceRegularDataResult3 = (DeviceRegularDataResult) JSON.parseObject(DataHelper.getStringSF(getActivity(), Constants.VIRTUAL_DEVICE_DATA), DeviceRegularDataResult.class);
                    D4shRecord deviceDetail5 = deviceRegularDataResult3.getResult().getD4sh().getDeviceDetail();
                    deviceRegularDataResult3.getResult().getD4sh().getDeviceDetail().setTypeCode(0);
                    deviceRegularDataResult3.getResult().getD4sh().getDeviceDetail().setDeviceId(j22);
                    deviceDetail5.save();
                    launchActivity(VirtualD4shHomeActivity.newIntent(getActivity(), j22, 0));
                } else if (homeDeviceData.getData().getStatus().getAgree() != null && homeDeviceData.getData().getStatus().getAgree().intValue() != 1) {
                    refreshCameraPrivacyPolicy(homeDeviceData.getData().getStatus().getAgree().intValue(), j22, 25, homeDeviceData.getData().getStatus().getVersionNo());
                } else if (d4shRecordByDeviceId == null) {
                    ((HomePagePresenter) this.mPresenter).startActivityForSharedDeviceId(getActivity(), D4shHomeActivity.class, j22, homeDeviceData.getType(), strArr);
                } else {
                    Bundle bundle30 = new Bundle();
                    bundle30.putLong(Constants.EXTRA_DEVICE_ID, j22);
                    bundle30.putInt(Constants.EXTRA_DEVICE_TYPE_CODE, 0);
                    if (strArr != null && strArr.length > 0) {
                        bundle30.putString(Consts.APP_WIDGET_EXTRA_DATA, strArr[0]);
                    }
                    Intent intent33 = new Intent(getActivity(), (Class<?>) D4shHomeActivity.class);
                    intent33.putExtras(bundle30);
                    launchActivity(intent33);
                }
                break;
            case 31:
            case 33:
            case 36:
                if (id < 0) {
                    CTW3Record deviceData3 = ((DeviceRegularDataResult) JSON.parseObject(DataHelper.getStringSF(getActivity(), Constants.VIRTUAL_DEVICE_DATA), DeviceRegularDataResult.class)).getResult().getCtw3().getDeviceData();
                    deviceData3.save();
                    launchActivity(VirtualCTW3HomeActivity.newIntent(getActivity(), deviceData3.getDeviceId()));
                } else if (CTW3Utils.getCTW3RecordByDeviceId(id) == null) {
                    ((HomePagePresenter) this.mPresenter).startActivityForSharedDeviceId(getActivity(), CTW3HomeActivity.class, id, homeDeviceData.getType(), strArr);
                } else {
                    Bundle bundle31 = new Bundle();
                    bundle31.putLong(Constants.EXTRA_DEVICE_ID, id);
                    bundle31.putBoolean(Constants.EXTRA_DEVICE_IS_BIND, false);
                    if (strArr != null && strArr.length > 0) {
                        bundle31.putString(Consts.APP_WIDGET_EXTRA_DATA, strArr[0]);
                    }
                    Intent intent34 = new Intent(getActivity(), (Class<?>) CTW3HomeActivity.class);
                    intent34.putExtras(bundle31);
                    launchActivity(intent34);
                }
                break;
            case 34:
            case 35:
                if (DeviceUtils.isPackageExist(getContext(), Constants.MATE_APP_PACKAGE_NAME)) {
                    MateDevice ownerDeviceByID = HsConsts.getOwnerDeviceByID(getContext(), String.valueOf(homeDeviceData.getId()));
                    if (ownerDeviceByID != null) {
                        if (homeDeviceData.getData() != null) {
                            ownerDeviceByID.setName(homeDeviceData.getData().getName());
                        }
                        Intent launchIntentForPackage = getActivity().getPackageManager().getLaunchIntentForPackage(Constants.MATE_APP_PACKAGE_NAME);
                        LogcatStorageHelper.addLog("[MATE] enter mate ");
                        launchIntentForPackage.putExtra(Constants.EXTRA_HS_DEVICE_DEATILS, new Gson().toJson(ownerDeviceByID));
                        if (!CommonUtils.isEmpty(CommonUtils.getSysMap(Constants.SHARED_BASE_GATEWAY))) {
                            launchIntentForPackage.putExtra(Constants.MATE_BASE_GATEWAY, CommonUtils.getSysMap(Constants.SHARED_BASE_GATEWAY));
                        }
                        String sysMap = CommonUtils.getSysMap(Consts.SHARED_SESSION_ID);
                        if (!CommonUtils.isEmpty(sysMap)) {
                            launchIntentForPackage.putExtra(Consts.HTTP_HEADER_SESSION, sysMap);
                        }
                        launchIntentForPackage.putExtra(Consts.SHARED_USER_ID, CommonUtils.getCurrentUserId());
                        launchIntentForPackage.putExtra(Consts.SHARED_LOGIN_RESULT, new Gson().toJson(UserInforUtils.getCurrentLoginResult()));
                        launchIntentForPackage.putExtra(Consts.SHARED_SETTING_LANGUAGE, UserInforUtils.getLanguageLocaleString(getContext()));
                        startActivity(launchIntentForPackage);
                    } else {
                        HsConsts.startMateAppInCallActivityForDeviceid(getActivity(), String.valueOf(homeDeviceData.getId()));
                    }
                } else {
                    Intent intent35 = new Intent(getActivity(), (Class<?>) MateDownloadActivity.class);
                    intent35.putExtra(Constants.EXTRA_HS_DEVICE_DEATILS, String.valueOf(homeDeviceData.getId()));
                    startActivity(intent35);
                }
                break;
            case 37:
                if (CommonUtils.checkPermission(requireActivity(), "android.permission.ACCESS_FINE_LOCATION") && CommonUtils.checkPermission(getActivity(), "android.permission.WRITE_EXTERNAL_STORAGE")) {
                    DataHelper.setBooleanSF(getContext(), "hasWalkdog", Boolean.TRUE);
                    GoWalkData goWalkDataByState = GoDataUtils.getGoWalkDataByState(1);
                    if (goWalkDataByState != null) {
                        Bundle bundle32 = new Bundle();
                        bundle32.putLong(Constants.EXTRA_TAG_ID, goWalkDataByState.getDeviceId());
                        Intent intent36 = new Intent(getActivity(), (Class<?>) GoWalkingActivity.class);
                        intent36.putExtras(bundle32);
                        launchActivity(intent36);
                    } else if (WalkDataUtils.getWalkRecord().getGoal() <= 0) {
                        Intent intent37 = new Intent(getContext(), (Class<?>) WalkSettingTargetActivity.class);
                        intent37.putExtra(WalkDataUtils.EXTRA_WALK_PET_ID, PetUtils.ALL_DEVICE);
                        startActivity(intent37);
                    } else if (Select.from(WalkData.class).where(Condition.prop("ownerId").eq(Long.valueOf(CommonUtils.getCurrentUserId()))).where(Condition.prop("state").eq(1)).count() > 0) {
                        launchActivity(new Intent(getActivity(), (Class<?>) WalkingActivity.class));
                    } else {
                        Intent intent38 = new Intent(getContext(), (Class<?>) WalkHomeActivity.class);
                        intent38.putExtra(WalkDataUtils.EXTRA_WALK_PET_ID, PetUtils.ALL_DEVICE);
                        startActivity(intent38);
                    }
                } else {
                    startActivity(PermissionDialogActivity.newIntent(getActivity(), HomePageFragment.class.getName(), "android.permission.ACCESS_FINE_LOCATION", "android.permission.WRITE_EXTERNAL_STORAGE"));
                }
                break;
            case 39:
                break;
            case 40:
            case 41:
                FeederRecord feederRecordByDeviceId = FeederUtils.getFeederRecordByDeviceId(id);
                if (feederRecordByDeviceId == null) {
                    FeederUtils.startActivityForFeederSharedDeviceid(getActivity(), FeederHomeActivity.class, id);
                } else if (feederRecordByDeviceId.getShared() == null && FeederUtils.getFeederPlanForDeviceId(id) == null) {
                    Bundle bundle33 = new Bundle();
                    bundle33.putLong(FeederUtils.EXTRA_FEEDER_ID, homeDeviceData.getId());
                    Intent intent39 = new Intent(getActivity(), (Class<?>) FeederPlanPromptActivity.class);
                    intent39.putExtras(bundle33);
                    launchActivity(intent39);
                } else {
                    Bundle bundle34 = new Bundle();
                    bundle34.putSerializable(FeederUtils.EXTRA_FEEDER_ID, Long.valueOf(id));
                    Intent intent40 = new Intent(getActivity(), (Class<?>) FeederHomeActivity.class);
                    intent40.putExtras(bundle34);
                    launchActivity(intent40);
                }
                break;
            default:
                Bundle bundle35 = new Bundle();
                bundle35.putSerializable(Constants.EXTRA_DOG, UserInforUtils.getPetById(homeDeviceData.getData().getPetId()));
                Intent intent41 = new Intent(getActivity(), (Class<?>) PetkitDetailActivity.class);
                intent41.putExtras(bundle35);
                launchActivity(intent41);
                break;
        }
    }

    @Override // com.petkit.android.activities.home.NewCardOnClickListener
    public void onVirtualCloseClick(HomeDeviceData homeDeviceData) {
        showCloseVirtualWindow(homeDeviceData);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:60:0x001b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void showCloseVirtualWindow(com.petkit.android.activities.home.adapter.model.HomeDeviceData r10) {
        /*
            Method dump skipped, instruction units count: 300
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.petkit.android.activities.home.HomePageFragment.showCloseVirtualWindow(com.petkit.android.activities.home.adapter.model.HomeDeviceData):void");
    }

    @Override // com.petkit.android.activities.home.NewCardOnClickListener
    public void onSettingClick(HomeDeviceData homeDeviceData) {
        String type = homeDeviceData.getType();
        type.hashCode();
        switch (type) {
            case "D3":
                launchActivity(homeDeviceData, 9);
                break;
            case "D4":
                launchActivity(homeDeviceData, 11);
                break;
            case "T3":
                launchActivity(homeDeviceData, 7);
                break;
            case "T4":
                T4Record t4RecordByDeviceId = T4Utils.getT4RecordByDeviceId(homeDeviceData.getId());
                if (t4RecordByDeviceId != null && t4RecordByDeviceId.getK3Id() > 0) {
                    launchActivity(CardShortcutsActivity.newIntent(getActivity(), homeDeviceData.getId(), 15, homeDeviceData.getData().getControlSettings(), true));
                    break;
                } else {
                    launchActivity(CardShortcutsActivity.newIntent(getActivity(), homeDeviceData.getId(), 15, homeDeviceData.getData().getControlSettings(), false));
                    break;
                }
                break;
            case "T6":
                launchActivity(homeDeviceData, 27);
                break;
            case "D4H":
            case "D4h":
                launchActivity(homeDeviceData, 26);
                break;
            case "D4s":
                launchActivity(homeDeviceData, 20);
                break;
            case "D4SH":
            case "D4sh":
                launchActivity(homeDeviceData, 25);
                break;
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.home.HomePageFragment$19 */
    public class AnonymousClass19 implements ThreeChoicesWindow.OnClickThreeChoices {
        public final /* synthetic */ HomeDeviceData val$data;
        public final /* synthetic */ int val$deviceType;

        public AnonymousClass19(int i, HomeDeviceData homeDeviceData) {
            i = i;
            homeDeviceData = homeDeviceData;
        }

        @Override // com.petkit.android.activities.device.widget.ThreeChoicesWindow.OnClickThreeChoices
        public void onClickFirstChoice() {
            int i = i;
            if (i == 28) {
                HomePageFragment homePageFragment = HomePageFragment.this;
                homePageFragment.launchActivity(T7WorkingTimeActivity.newIntent(homePageFragment.getContext(), homeDeviceData.getId()));
            } else if (i == 29) {
                HomePageFragment homePageFragment2 = HomePageFragment.this;
                homePageFragment2.launchActivity(W7hCameraPeriodActivity.newIntent(homePageFragment2.getContext(), homeDeviceData.getId(), i));
            } else {
                HomePageFragment homePageFragment3 = HomePageFragment.this;
                homePageFragment3.launchActivity(T6WorkingTimeActivity.newIntent(homePageFragment3.getContext(), homeDeviceData.getId(), i));
            }
            HomePageFragment.this.threeChoicesWindow.dismiss();
        }

        @Override // com.petkit.android.activities.device.widget.ThreeChoicesWindow.OnClickThreeChoices
        public void onClickSecondChoice() {
            int i = i;
            if (i == 28) {
                ((HomePagePresenter) ((BaseFragment) HomePageFragment.this).mPresenter).temporaryT7OpenCamera(homeDeviceData.getId());
            } else if (i == 29) {
                ((HomePagePresenter) ((BaseFragment) HomePageFragment.this).mPresenter).temporaryW7hOpenCamera(homeDeviceData.getId());
            } else {
                ((HomePagePresenter) ((BaseFragment) HomePageFragment.this).mPresenter).temporaryOpenCamera(homeDeviceData, i);
            }
            HomePageFragment.this.show5MinWindowTips();
            HomePageFragment.this.threeChoicesWindow.dismiss();
        }

        @Override // com.petkit.android.activities.device.widget.ThreeChoicesWindow.OnClickThreeChoices
        public void onClickThirdChoice() {
            HomePageFragment.this.threeChoicesWindow.dismiss();
        }
    }

    public void show5MinTurnOnCameraWindow(HomeDeviceData homeDeviceData, int i) {
        ThreeChoicesWindow threeChoicesWindow = new ThreeChoicesWindow(getContext(), new ThreeChoicesWindow.OnClickThreeChoices() { // from class: com.petkit.android.activities.home.HomePageFragment.19
            public final /* synthetic */ HomeDeviceData val$data;
            public final /* synthetic */ int val$deviceType;

            public AnonymousClass19(int i2, HomeDeviceData homeDeviceData2) {
                i = i2;
                homeDeviceData = homeDeviceData2;
            }

            @Override // com.petkit.android.activities.device.widget.ThreeChoicesWindow.OnClickThreeChoices
            public void onClickFirstChoice() {
                int i2 = i;
                if (i2 == 28) {
                    HomePageFragment homePageFragment = HomePageFragment.this;
                    homePageFragment.launchActivity(T7WorkingTimeActivity.newIntent(homePageFragment.getContext(), homeDeviceData.getId()));
                } else if (i2 == 29) {
                    HomePageFragment homePageFragment2 = HomePageFragment.this;
                    homePageFragment2.launchActivity(W7hCameraPeriodActivity.newIntent(homePageFragment2.getContext(), homeDeviceData.getId(), i));
                } else {
                    HomePageFragment homePageFragment3 = HomePageFragment.this;
                    homePageFragment3.launchActivity(T6WorkingTimeActivity.newIntent(homePageFragment3.getContext(), homeDeviceData.getId(), i));
                }
                HomePageFragment.this.threeChoicesWindow.dismiss();
            }

            @Override // com.petkit.android.activities.device.widget.ThreeChoicesWindow.OnClickThreeChoices
            public void onClickSecondChoice() {
                int i2 = i;
                if (i2 == 28) {
                    ((HomePagePresenter) ((BaseFragment) HomePageFragment.this).mPresenter).temporaryT7OpenCamera(homeDeviceData.getId());
                } else if (i2 == 29) {
                    ((HomePagePresenter) ((BaseFragment) HomePageFragment.this).mPresenter).temporaryW7hOpenCamera(homeDeviceData.getId());
                } else {
                    ((HomePagePresenter) ((BaseFragment) HomePageFragment.this).mPresenter).temporaryOpenCamera(homeDeviceData, i);
                }
                HomePageFragment.this.show5MinWindowTips();
                HomePageFragment.this.threeChoicesWindow.dismiss();
            }

            @Override // com.petkit.android.activities.device.widget.ThreeChoicesWindow.OnClickThreeChoices
            public void onClickThirdChoice() {
                HomePageFragment.this.threeChoicesWindow.dismiss();
            }
        }, getResources().getString(R.string.Open_camera_tips), getResources().getString(R.string.Open_whole_day_camera), getResources().getString(R.string.Open_temp_five_mins), getResources().getString(R.string.Cancel));
        this.threeChoicesWindow = threeChoicesWindow;
        threeChoicesWindow.setThreeChoicesTextColor(getResources().getColor(R.color.new_bind_blue), getResources().getColor(R.color.new_bind_blue), getResources().getColor(R.color.gray));
        this.threeChoicesWindow.show(requireActivity().getWindow().getDecorView());
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.home.HomePageFragment$20 */
    public class AnonymousClass20 implements ThreeChoicesWindow.OnClickThreeChoices {
        public final /* synthetic */ HomeDeviceData val$data;
        public final /* synthetic */ int val$deviceType;

        public AnonymousClass20(HomeDeviceData homeDeviceData, int i) {
            homeDeviceData = homeDeviceData;
            i = i;
        }

        @Override // com.petkit.android.activities.device.widget.ThreeChoicesWindow.OnClickThreeChoices
        public void onClickFirstChoice() {
            HomePageFragment homePageFragment = HomePageFragment.this;
            homePageFragment.launchActivity(D4shWorkingTimeActivity.newIntent(homePageFragment.getContext(), homeDeviceData.getId(), i));
            HomePageFragment.this.threeChoicesWindow.dismiss();
        }

        @Override // com.petkit.android.activities.device.widget.ThreeChoicesWindow.OnClickThreeChoices
        public void onClickSecondChoice() {
            ((HomePagePresenter) ((BaseFragment) HomePageFragment.this).mPresenter).temporaryD4shOpenCamera(homeDeviceData, i);
            HomePageFragment.this.show5MinWindowTips();
            HomePageFragment.this.threeChoicesWindow.dismiss();
        }

        @Override // com.petkit.android.activities.device.widget.ThreeChoicesWindow.OnClickThreeChoices
        public void onClickThirdChoice() {
            HomePageFragment.this.threeChoicesWindow.dismiss();
        }
    }

    public void showTurnOnCameraWindow(HomeDeviceData homeDeviceData, int i) {
        ThreeChoicesWindow threeChoicesWindow = new ThreeChoicesWindow(getContext(), new ThreeChoicesWindow.OnClickThreeChoices() { // from class: com.petkit.android.activities.home.HomePageFragment.20
            public final /* synthetic */ HomeDeviceData val$data;
            public final /* synthetic */ int val$deviceType;

            public AnonymousClass20(HomeDeviceData homeDeviceData2, int i2) {
                homeDeviceData = homeDeviceData2;
                i = i2;
            }

            @Override // com.petkit.android.activities.device.widget.ThreeChoicesWindow.OnClickThreeChoices
            public void onClickFirstChoice() {
                HomePageFragment homePageFragment = HomePageFragment.this;
                homePageFragment.launchActivity(D4shWorkingTimeActivity.newIntent(homePageFragment.getContext(), homeDeviceData.getId(), i));
                HomePageFragment.this.threeChoicesWindow.dismiss();
            }

            @Override // com.petkit.android.activities.device.widget.ThreeChoicesWindow.OnClickThreeChoices
            public void onClickSecondChoice() {
                ((HomePagePresenter) ((BaseFragment) HomePageFragment.this).mPresenter).temporaryD4shOpenCamera(homeDeviceData, i);
                HomePageFragment.this.show5MinWindowTips();
                HomePageFragment.this.threeChoicesWindow.dismiss();
            }

            @Override // com.petkit.android.activities.device.widget.ThreeChoicesWindow.OnClickThreeChoices
            public void onClickThirdChoice() {
                HomePageFragment.this.threeChoicesWindow.dismiss();
            }
        }, getResources().getString(R.string.Open_camera_tips), getResources().getString(R.string.Open_whole_day_camera), getResources().getString(R.string.Open_temp_five_mins), getResources().getString(R.string.Cancel));
        this.threeChoicesWindow = threeChoicesWindow;
        threeChoicesWindow.setThreeChoicesTextColor(getResources().getColor(R.color.new_bind_blue), getResources().getColor(R.color.new_bind_blue), getResources().getColor(R.color.gray));
        this.threeChoicesWindow.show(requireActivity().getWindow().getDecorView());
    }

    public void launchActivity(HomeDeviceData homeDeviceData, int i) {
        launchActivity(CardShortcutsActivity.newIntent(getActivity(), homeDeviceData.getId(), i, homeDeviceData.getData().getControlSettings()));
    }

    public void openIKnowTipWindow(String str) {
        NewIKnowWindow newIKnowWindow = new NewIKnowWindow(getContext(), (String) null, str, (String) null);
        this.iKnowWindow = newIKnowWindow;
        newIKnowWindow.show(getActivity().getWindow().getDecorView());
    }

    public void openHomeModeTipWindowWithListner(String str, NewIKnowWindow.onClickIKnowListener onclickiknowlistener) {
        NewIKnowWindow newIKnowWindow = new NewIKnowWindow(getActivity(), (String) null, str, (String) null);
        this.homeModeWindowWithListner = newIKnowWindow;
        newIKnowWindow.setOnClickIKnowListener(onclickiknowlistener);
        this.homeModeWindowWithListner.show(getActivity().getWindow().getDecorView());
    }

    private boolean canCamera(HomeDeviceData homeDeviceData) {
        int state;
        if (homeDeviceData == null || homeDeviceData.getData() == null || (state = homeDeviceData.getData().getState()) == 2 || homeDeviceData.getData().getStatus().getPower() == 0 || homeDeviceData.getData().getStatus().getOta() == 1 || homeDeviceData.getData().getStatus().getPim() == 0) {
            return false;
        }
        return homeDeviceData.getData().getStatus().getWaterPumpState() == 1 || state != 4;
    }

    private boolean canAW(HomeDeviceData homeDeviceData) {
        if (homeDeviceData == null || homeDeviceData.getData() == null) {
            return false;
        }
        return (homeDeviceData.getData().getState() == 2 || homeDeviceData.getData().getStatus().getPower() == 0 || homeDeviceData.getData().getStatus().getOta() == 1 || homeDeviceData.getData().getStatus().getPim() == 0 || homeDeviceData.getData().getStatus().w7hAWAvailable() || homeDeviceData.getData().getState() == 4) ? false : true;
    }

    @Override // com.petkit.android.activities.home.NewCardOnClickListener
    public void onControlDeviceClick(HomeDeviceData homeDeviceData) {
        String type = homeDeviceData.getType();
        type.hashCode();
        switch (type) {
            case "D3":
                if (homeDeviceData.getData().getState() == 2 || homeDeviceData.getData().getState() == 4 || homeDeviceData.getData().getState() == 5) {
                    PetkitLog.d("D3非离线和升级中");
                    break;
                } else {
                    if (homeDeviceData.getData().getStatus().getPim() == 2) {
                        openModeWindow(getResources().getString(R.string.Battery_mode_disabled));
                    } else if (homeDeviceData.getData().getControlSettings() == 1) {
                        if (homeDeviceData.getData().getStatus().getFeeding() == 1) {
                            showCancelD3FeedingDialog(homeDeviceData.getId());
                        } else {
                            showD3ManualFeedWindow(homeDeviceData.getId());
                        }
                    } else if (homeDeviceData.getData().getControlSettings() == 2) {
                        ActivityUtils.getInstance().getDeviceDetailAndEnterAppointView(getActivity(), homeDeviceData.getId(), 9, DeviceFeedPlansMainActivity.newIntent(getActivity(), homeDeviceData.getId(), 9, false));
                    }
                    break;
                }
                break;
            case "D4":
                if (homeDeviceData.getData().getState() == 2 || homeDeviceData.getData().getState() == 4 || homeDeviceData.getData().getState() == 5) {
                    PetkitLog.d("D4非离线和升级中");
                    break;
                } else {
                    if (homeDeviceData.getData().getStatus().getPim() == 2) {
                        openModeWindow(getResources().getString(R.string.Battery_mode_disabled));
                    } else if (homeDeviceData.getData().getControlSettings() == 1) {
                        if (homeDeviceData.getData().getState() == 3) {
                            showCancelD4FeedingDialog(homeDeviceData.getId());
                        } else {
                            showD4ManualFeedWindow(homeDeviceData.getId());
                        }
                    } else if (homeDeviceData.getData().getControlSettings() == 2) {
                        ActivityUtils.getInstance().getDeviceDetailAndEnterAppointView(getActivity(), homeDeviceData.getId(), 11, DeviceFeedPlansMainActivity.newIntent(getActivity(), homeDeviceData.getId(), 11, false));
                    }
                    break;
                }
                break;
            case "T3":
                if (homeDeviceData.getData().getState() != 2 && homeDeviceData.getData().getState() != 4 && homeDeviceData.getData().getStatus().getPower() != 0 && homeDeviceData.getData().getStatus().getOta() != 1) {
                    if (homeDeviceData.getData().getControlSettings() == 1) {
                        if (homeDeviceData.getData().getStatus().getWorkState() != null && homeDeviceData.getData().getStatus().getWorkState().getWorkMode() == 0) {
                            PetkitLog.d("T3清理中");
                        } else {
                            showT3CleanUpWindow(homeDeviceData.getId());
                        }
                    } else if (homeDeviceData.getData().getControlSettings() == 2) {
                        if (homeDeviceData.getData().getStatus().getWorkState() != null && homeDeviceData.getData().getStatus().getWorkState().getWorkMode() == 2) {
                            PetkitLog.d("T3除臭中");
                        } else {
                            ((HomePagePresenter) this.mPresenter).controlT3Device(homeDeviceData.getId(), 2, 1);
                        }
                    }
                    break;
                }
                break;
            case "T4":
                if (homeDeviceData.getData().getState() != 2 && homeDeviceData.getData().getState() != 4 && homeDeviceData.getData().getStatus().getPower() != 0 && homeDeviceData.getData().getStatus().getOta() != 1) {
                    if (homeDeviceData.getData().getControlSettings() == 1) {
                        if (homeDeviceData.getData().getStatus().getWorkState() != null && homeDeviceData.getData().getStatus().getWorkState().getWorkMode() == 0) {
                            PetkitLog.d("T4清理中");
                        } else {
                            showT4CleanUpWindow(homeDeviceData.getId());
                        }
                    } else if (homeDeviceData.getData().getControlSettings() == 2) {
                        if (homeDeviceData.getData().getStatus().getWorkState() != null && homeDeviceData.getData().getStatus().getWorkState().getWorkMode() == 2) {
                            PetkitLog.d("T4除臭中");
                        } else {
                            ((HomePagePresenter) this.mPresenter).controlT4Device(homeDeviceData.getId(), 2, 1);
                        }
                    }
                    break;
                }
                break;
            case "T5":
            case "T6":
                if (homeDeviceData.getData().getState() == 2 || homeDeviceData.getData().getState() == 4 || homeDeviceData.getData().getState() == 5 || homeDeviceData.getData().getStatus().getPower() == 0) {
                    PetkitLog.d("非离线和升级中");
                    if (homeDeviceData.getData().getControlSettings() == 0) {
                        launchActivity(homeDeviceData, homeDeviceData.getType().equals("T5") ? 21 : 27);
                    }
                    break;
                } else if (homeDeviceData.getData().getControlSettings() == 3 && homeDeviceData.getType().equals("T6")) {
                    if (homeDeviceData.getData().getDeviceShared() == null) {
                        T6Record t6RecordByDeviceId = T6Utils.getT6RecordByDeviceId(homeDeviceData.getData().getId(), 0);
                        if (t6RecordByDeviceId != null && homeDeviceData.getData().getStatus().getPetInTime() == 0) {
                            if (homeDeviceData.getData().getStatus().getOta() == 1) {
                                PetkitToast.showTopToast(requireActivity(), getContext().getString(R.string.Cant_controll_now_updating), 0, 0);
                            } else if (t6RecordByDeviceId.getSettings().getCameraOff() == 0 && t6RecordByDeviceId.getSettings().getNoSound() == 0 && t6RecordByDeviceId.getSettings().getCameraInward() == 0) {
                                launchActivity(T6InitHomeModeActivity.newIntent(getContext(), homeDeviceData.getData().getId(), 0));
                            } else if (homeDeviceData.getData().getSettings().getHomeMode() == 1) {
                                if (t6RecordByDeviceId.getState().getOverall() == 2) {
                                    PetkitToast.showTopToast(getActivity(), getString(R.string.Cant_controll_now_offline), 0, 0);
                                } else if (!TextUtils.isEmpty(t6RecordByDeviceId.getState().getErrorCode())) {
                                    PetkitToast.showTopToast(getActivity(), getString(R.string.Cant_controll_now_err), 0, 0);
                                } else if (t6RecordByDeviceId.getState().getPower() == 0) {
                                    PetkitToast.showTopToast(getActivity(), getString(R.string.Cant_controll_now_power_off), 0, 0);
                                } else if (t6RecordByDeviceId.getState().getOverall() == 3) {
                                    PetkitToast.showTopToast(getActivity(), getString(R.string.Cant_controll_now_updating), 0, 0);
                                } else if (t6RecordByDeviceId.getState().getWorkState() != null) {
                                    PetkitToast.showTopToast(getActivity(), getString(R.string.Cant_controll_now_working), 0, 0);
                                } else {
                                    if (getActivity() != null) {
                                        LoadDialog.show(getActivity());
                                    }
                                    ((HomePagePresenter) this.mPresenter).updateT6DeviceSettings(homeDeviceData, "homeMode", false, 27);
                                }
                            } else if (t6RecordByDeviceId.getSettings().getCameraConfig() != 1 && t6RecordByDeviceId.getSettings().getCameraOff() == 1) {
                                openHomeModeTipWindowWithListner(getString(R.string.Home_mode_in_working_time_tip), new NewIKnowWindow.onClickIKnowListener() { // from class: com.petkit.android.activities.home.HomePageFragment.21
                                    public final /* synthetic */ HomeDeviceData val$data;
                                    public final /* synthetic */ T6Record val$t6Record;

                                    public AnonymousClass21(T6Record t6RecordByDeviceId2, HomeDeviceData homeDeviceData2) {
                                        t6Record = t6RecordByDeviceId2;
                                        homeDeviceData = homeDeviceData2;
                                    }

                                    @Override // com.petkit.android.activities.petkitBleDevice.r2.widget.NewIKnowWindow.onClickIKnowListener
                                    public void onClickIKnow() {
                                        if (t6Record.getState().getOverall() == 2) {
                                            PetkitToast.showTopToast(HomePageFragment.this.getActivity(), HomePageFragment.this.getString(R.string.Cant_controll_now_offline), 0, 0);
                                            return;
                                        }
                                        if (!TextUtils.isEmpty(t6Record.getState().getErrorCode())) {
                                            PetkitToast.showTopToast(HomePageFragment.this.getActivity(), HomePageFragment.this.getString(R.string.Cant_controll_now_err), 0, 0);
                                            return;
                                        }
                                        if (t6Record.getState().getPower() == 0) {
                                            PetkitToast.showTopToast(HomePageFragment.this.getActivity(), HomePageFragment.this.getString(R.string.Cant_controll_now_power_off), 0, 0);
                                            return;
                                        }
                                        if (t6Record.getState().getOverall() == 3) {
                                            PetkitToast.showTopToast(HomePageFragment.this.getActivity(), HomePageFragment.this.getString(R.string.Cant_controll_now_updating), 0, 0);
                                        } else {
                                            if (t6Record.getState().getWorkState() != null) {
                                                PetkitToast.showTopToast(HomePageFragment.this.getActivity(), HomePageFragment.this.getString(R.string.Cant_controll_now_working), 0, 0);
                                                return;
                                            }
                                            if (HomePageFragment.this.getActivity() != null) {
                                                LoadDialog.show(HomePageFragment.this.getActivity());
                                            }
                                            ((HomePagePresenter) ((BaseFragment) HomePageFragment.this).mPresenter).updateT6DeviceSettings(homeDeviceData, "homeMode", true, 27);
                                        }
                                    }
                                });
                            } else if (t6RecordByDeviceId2.getState().getOverall() == 2) {
                                PetkitToast.showTopToast(getActivity(), getString(R.string.Cant_controll_now_offline), 0, 0);
                            } else if (!TextUtils.isEmpty(t6RecordByDeviceId2.getState().getErrorCode())) {
                                PetkitToast.showTopToast(getActivity(), getString(R.string.Cant_controll_now_err), 0, 0);
                            } else if (t6RecordByDeviceId2.getState().getPower() == 0) {
                                PetkitToast.showTopToast(getActivity(), getString(R.string.Cant_controll_now_power_off), 0, 0);
                            } else if (t6RecordByDeviceId2.getState().getOverall() == 3) {
                                PetkitToast.showTopToast(getActivity(), getString(R.string.Cant_controll_now_updating), 0, 0);
                            } else if (t6RecordByDeviceId2.getState().getWorkState() != null) {
                                PetkitToast.showTopToast(getActivity(), getString(R.string.Cant_controll_now_working), 0, 0);
                            } else {
                                if (getActivity() != null) {
                                    LoadDialog.show(getActivity());
                                }
                                ((HomePagePresenter) this.mPresenter).updateT6DeviceSettings(homeDeviceData2, "homeMode", true, 27);
                            }
                        }
                    } else {
                        openIKnowTipWindow(getString(R.string.Shared_device_is_limited_tip));
                    }
                    break;
                } else if (homeDeviceData2.getData().getControlSettings() == 2) {
                    if (homeDeviceData2.getData().getSettings().getHomeMode() == 1 && homeDeviceData2.getType().equals("T6") && T6Utils.getT6RecordByDeviceId(homeDeviceData2.getData().getId(), 0).getSettings().getCameraOff() == 1) {
                        openIKnowTipWindow(getString(R.string.Home_mode_can_not_open_camera_tip));
                    } else if (homeDeviceData2.getData().getSettings().getCamera() == 0) {
                        ((HomePagePresenter) this.mPresenter).updateT6DeviceSettings(homeDeviceData2, "camera", true, homeDeviceData2.getType().equals("T5") ? 21 : 27);
                    } else if (homeDeviceData2.getData().getSettings().getCamera() == 1 && homeDeviceData2.getData().getStatus().getCameraStatus() != null && homeDeviceData2.getData().getStatus().getCameraStatus().intValue() == 0) {
                        show5MinTurnOnCameraWindow(homeDeviceData2, homeDeviceData2.getType().equals("T5") ? 21 : 27);
                    } else if (homeDeviceData2.getData().getSettings().getCamera() == 1) {
                        t6OpenCloseCameraWindow(homeDeviceData2, getResources().getString(R.string.T6_close_camera), "camera", false, homeDeviceData2.getType().equals("T5") ? 21 : 27);
                    }
                    break;
                } else if (homeDeviceData2.getData().getControlSettings() == 1) {
                    if (homeDeviceData2.getType().equals("T5")) {
                        if (homeDeviceData2.getData().getStatus().getWeightState() != 1 && homeDeviceData2.getData().getStatus().getOta() != 1) {
                            if (homeDeviceData2.getData().getStatus().getErrorMsg() == null || homeDeviceData2.getData().getStatus().getErrorCode().equals("camera")) {
                                if (homeDeviceData2.getData().getStatus().getWorkState() != null && homeDeviceData2.getData().getStatus().getWorkState().getWorkMode() == 0) {
                                    PetkitLog.d("T5清理中");
                                } else {
                                    showT6CleanUpWindow(homeDeviceData2.getData().getId(), 21);
                                }
                            }
                            break;
                        }
                    } else if (homeDeviceData2.getData().getStatus().getOta() != 1 && homeDeviceData2.getData().getStatus().getTopIns() != 1 && homeDeviceData2.getData().getStatus().getPiIns() != 1 && homeDeviceData2.getData().getStatus().getWeightState() != 1 && homeDeviceData2.getData().getStatus().getTrunkState() != 1 && homeDeviceData2.getData().getStatus().getPackageState() != 2 && homeDeviceData2.getData().getStatus().getPackageState() != 3 && homeDeviceData2.getData().getStatus().getPackageState() != 4 && homeDeviceData2.getData().getStatus().getPackageState() != 5) {
                        if (homeDeviceData2.getData().getStatus().getErrorMsg() == null || homeDeviceData2.getData().getStatus().getErrorCode().equals("camera")) {
                            if (homeDeviceData2.getData().getStatus().getWorkState() != null && homeDeviceData2.getData().getStatus().getWorkState().getWorkMode() == 0) {
                                PetkitLog.d("T6清理中");
                            } else {
                                showT6CleanUpWindow(homeDeviceData2.getData().getId(), 27);
                            }
                        }
                        break;
                    }
                } else {
                    if (homeDeviceData2.getData().getControlSettings() == 0) {
                        launchActivity(homeDeviceData2, homeDeviceData2.getType().equals("T5") ? 21 : 27);
                    }
                    break;
                }
                break;
            case "T7":
                if (homeDeviceData2.getData().getControlSettings() == 1) {
                    if (homeDeviceData2.getData().getStatus().getWorkState() != null && homeDeviceData2.getData().getStatus().getWorkState().getWorkMode() == 0) {
                        onClick(homeDeviceData2, new String[0]);
                    } else {
                        showT7Clean(homeDeviceData2);
                    }
                    break;
                } else {
                    if (homeDeviceData2.getData().getControlSettings() == 2) {
                        if (homeDeviceData2.getData().getSettings().getCamera() == 0) {
                            P p = this.mPresenter;
                            if (p != 0) {
                                ((HomePagePresenter) p).updateT7DeviceSettings(homeDeviceData2.getId(), "camera", 1);
                            }
                        } else if (homeDeviceData2.getData().getSettings().getCamera() == 1 && homeDeviceData2.getData().getStatus().getCameraStatus() != null && homeDeviceData2.getData().getStatus().getCameraStatus().intValue() == 0) {
                            show5MinTurnOnCameraWindow(homeDeviceData2, 28);
                        } else if (homeDeviceData2.getData().getSettings().getCamera() == 1) {
                            t6OpenCloseCameraWindow(homeDeviceData2, getResources().getString(R.string.T6_close_camera), "camera", false, 28);
                        }
                    } else {
                        launchActivity(homeDeviceData2, 28);
                    }
                    break;
                }
                break;
            case "D4H":
            case "D4h":
                if (homeDeviceData2.getData().getState() == 2 || homeDeviceData2.getData().getState() == 4 || homeDeviceData2.getData().getState() == 5) {
                    PetkitLog.d("D4h非离线和升级中");
                    if (homeDeviceData2.getData().getControlSettings() == 0) {
                        launchActivity(homeDeviceData2, 26);
                    }
                    break;
                } else {
                    if (homeDeviceData2.getData().getStatus().getPim() == 2) {
                        openModeWindow(getResources().getString(R.string.Battery_mode_disabled));
                    } else if (homeDeviceData2.getData().getControlSettings() == 1) {
                        if (homeDeviceData2.getData().getStatus().getFeeding() == 1 || homeDeviceData2.getData().getStatus().getFeeding() == 2 || homeDeviceData2.getData().getStatus().getFeeding() == 3) {
                            showCancelD4hFeedingDialog(homeDeviceData2.getId());
                        } else {
                            showD4hManualFeedWindow(homeDeviceData2.getId());
                        }
                    } else if (homeDeviceData2.getData().getControlSettings() == 2) {
                        ActivityUtils.getInstance().getDeviceDetailAndEnterAppointView(getActivity(), homeDeviceData2.getId(), 26, DeviceFeedPlansMainActivity.newIntent(getActivity(), homeDeviceData2.getId(), 26, false));
                    } else if (homeDeviceData2.getData().getControlSettings() == 3) {
                        if (homeDeviceData2.getData().getStatus().getPim() == 2) {
                            openBatteryModeWindow();
                        } else if (homeDeviceData2.getData().getSettings().getCamera() == 0) {
                            ((HomePagePresenter) this.mPresenter).updateDeviceSettings(homeDeviceData2, 1, "camera", true);
                        } else if (homeDeviceData2.getData().getSettings().getCamera() == 1 && homeDeviceData2.getData().getStatus().getCameraStatus().intValue() == 0) {
                            showTurnOnCameraWindow(homeDeviceData2, 26);
                        } else if (homeDeviceData2.getData().getSettings().getCamera() == 1) {
                            openCloseCameraWindow(homeDeviceData2, 1, getResources().getString(R.string.D4sh_close_camera_affirm_tips), "camera", false);
                        }
                    } else if (homeDeviceData2.getData().getControlSettings() == 0) {
                        launchActivity(homeDeviceData2, 26);
                    }
                    break;
                }
                break;
            case "D4s":
                if (homeDeviceData2.getData().getState() == 2 || homeDeviceData2.getData().getState() == 4 || homeDeviceData2.getData().getState() == 5) {
                    PetkitLog.d("D4s非离线和升级中");
                    break;
                } else {
                    if (homeDeviceData2.getData().getStatus().getPim() == 2 && homeDeviceData2.getData().getStatus().getConservationStatus() == 1) {
                        openModeWindow(getResources().getString(R.string.Energy_conservation_mode_disabled));
                    } else if (homeDeviceData2.getData().getControlSettings() == 1) {
                        if (homeDeviceData2.getData().getStatus().getFeeding() == 1 || homeDeviceData2.getData().getStatus().getFeeding() == 2 || homeDeviceData2.getData().getStatus().getFeeding() == 3) {
                            showCancelD4sFeedingDialog(homeDeviceData2.getId());
                        } else {
                            showD4sManualFeedWindow(homeDeviceData2.getId());
                        }
                    } else if (homeDeviceData2.getData().getControlSettings() == 2) {
                        ActivityUtils.getInstance().getDeviceDetailAndEnterAppointView(getActivity(), homeDeviceData2.getId(), 20, DeviceFeedPlansMainActivity.newIntent(getActivity(), homeDeviceData2.getId(), 20, false));
                    }
                    break;
                }
                break;
            case "W7H":
            case "W7h":
                if (homeDeviceData2.getData().getControlSettings() == 1) {
                    if (canAW(homeDeviceData2)) {
                        if (((homeDeviceData2.getData().getStatus().getLiftValveState() == 4 || homeDeviceData2.getData().getStatus().getLiftValveState() == 3) && homeDeviceData2.getData().getSettings().getCamera() == 0) || homeDeviceData2.getData().getStatus().getCameraStatus().intValue() == 0) {
                            w7hOpenCameraAndRefillWindow(homeDeviceData2.getId());
                        } else if (homeDeviceData2.getData().getStatus().getAddWaterState() == 0) {
                            ((HomePagePresenter) this.mPresenter).controlAddWater(homeDeviceData2.getId());
                        }
                        break;
                    }
                } else if (homeDeviceData2.getData().getControlSettings() == 2) {
                    if (canCamera(homeDeviceData2) && !homeDeviceData2.getData().getStatus().w7hCameraAvailable()) {
                        if (homeDeviceData2.getData().getSettings().getCamera() == 0) {
                            P p2 = this.mPresenter;
                            if (p2 != 0) {
                                ((HomePagePresenter) p2).updateW7hDeviceSettings(homeDeviceData2.getId(), "camera", 1);
                            }
                        } else if (homeDeviceData2.getData().getSettings().getCamera() == 1 && homeDeviceData2.getData().getStatus().getCameraStatus() != null && homeDeviceData2.getData().getStatus().getCameraStatus().intValue() == 0) {
                            show5MinTurnOnCameraWindow(homeDeviceData2, 29);
                        } else if (homeDeviceData2.getData().getSettings().getCamera() == 1) {
                            w7hOpenCloseCameraWindow(homeDeviceData2.getId(), getResources().getString(R.string.W7h_close_camera_affirm_tips), "camera", 0);
                        }
                        break;
                    }
                } else {
                    if (homeDeviceData2.getData().getControlSettings() == 0) {
                        launchActivity(homeDeviceData2, 29);
                    }
                    break;
                }
                break;
            case "D4SH":
            case "D4sh":
                if (homeDeviceData2.getData().getState() == 2 || homeDeviceData2.getData().getState() == 4 || homeDeviceData2.getData().getState() == 5) {
                    PetkitLog.d("D4sh非离线和升级中");
                    if (homeDeviceData2.getData().getControlSettings() == 0) {
                        launchActivity(homeDeviceData2, 25);
                    }
                    break;
                } else {
                    if (homeDeviceData2.getData().getStatus().getPim() == 2) {
                        openModeWindow(getResources().getString(R.string.Battery_mode_disabled));
                    } else if (homeDeviceData2.getData().getControlSettings() == 1) {
                        if (homeDeviceData2.getData().getStatus().getFeeding() == 1 || homeDeviceData2.getData().getStatus().getFeeding() == 2 || homeDeviceData2.getData().getStatus().getFeeding() == 3) {
                            showCancelD4shFeedingDialog(homeDeviceData2.getId());
                        } else {
                            showD4shManualFeedWindow(homeDeviceData2.getId());
                        }
                    } else if (homeDeviceData2.getData().getControlSettings() == 2) {
                        ActivityUtils.getInstance().getDeviceDetailAndEnterAppointView(getActivity(), homeDeviceData2.getId(), 25, DeviceFeedPlansMainActivity.newIntent(getActivity(), homeDeviceData2.getId(), 25, false));
                    } else if (homeDeviceData2.getData().getControlSettings() == 3) {
                        if (homeDeviceData2.getData().getStatus().getPim() == 2) {
                            openBatteryModeWindow();
                        } else if (homeDeviceData2.getData().getSettings().getCamera() == 0) {
                            ((HomePagePresenter) this.mPresenter).updateDeviceSettings(homeDeviceData2, 0, "camera", true);
                        } else if (homeDeviceData2.getData().getSettings().getCamera() == 1 && homeDeviceData2.getData().getStatus().getCameraStatus().intValue() == 0) {
                            showTurnOnCameraWindow(homeDeviceData2, 25);
                        } else if (homeDeviceData2.getData().getSettings().getCamera() == 1) {
                            openCloseCameraWindow(homeDeviceData2, 0, getResources().getString(R.string.D4sh_close_camera_affirm_tips), "camera", false);
                        }
                    } else if (homeDeviceData2.getData().getControlSettings() == 0) {
                        launchActivity(homeDeviceData2, 25);
                    }
                    break;
                }
                break;
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.home.HomePageFragment$21 */
    public class AnonymousClass21 implements NewIKnowWindow.onClickIKnowListener {
        public final /* synthetic */ HomeDeviceData val$data;
        public final /* synthetic */ T6Record val$t6Record;

        public AnonymousClass21(T6Record t6RecordByDeviceId2, HomeDeviceData homeDeviceData2) {
            t6Record = t6RecordByDeviceId2;
            homeDeviceData = homeDeviceData2;
        }

        @Override // com.petkit.android.activities.petkitBleDevice.r2.widget.NewIKnowWindow.onClickIKnowListener
        public void onClickIKnow() {
            if (t6Record.getState().getOverall() == 2) {
                PetkitToast.showTopToast(HomePageFragment.this.getActivity(), HomePageFragment.this.getString(R.string.Cant_controll_now_offline), 0, 0);
                return;
            }
            if (!TextUtils.isEmpty(t6Record.getState().getErrorCode())) {
                PetkitToast.showTopToast(HomePageFragment.this.getActivity(), HomePageFragment.this.getString(R.string.Cant_controll_now_err), 0, 0);
                return;
            }
            if (t6Record.getState().getPower() == 0) {
                PetkitToast.showTopToast(HomePageFragment.this.getActivity(), HomePageFragment.this.getString(R.string.Cant_controll_now_power_off), 0, 0);
                return;
            }
            if (t6Record.getState().getOverall() == 3) {
                PetkitToast.showTopToast(HomePageFragment.this.getActivity(), HomePageFragment.this.getString(R.string.Cant_controll_now_updating), 0, 0);
            } else {
                if (t6Record.getState().getWorkState() != null) {
                    PetkitToast.showTopToast(HomePageFragment.this.getActivity(), HomePageFragment.this.getString(R.string.Cant_controll_now_working), 0, 0);
                    return;
                }
                if (HomePageFragment.this.getActivity() != null) {
                    LoadDialog.show(HomePageFragment.this.getActivity());
                }
                ((HomePagePresenter) ((BaseFragment) HomePageFragment.this).mPresenter).updateT6DeviceSettings(homeDeviceData, "homeMode", true, 27);
            }
        }
    }

    private void showCloseWindow(final int i, final int i2) {
        View viewInflate = LayoutInflater.from(getActivity()).inflate(R.layout.pop_prompt_layout, (ViewGroup) null);
        final EasyPopupWindow easyPopupWindow = new EasyPopupWindow(getActivity());
        easyPopupWindow.setOutsideTouchable(false);
        TextView textView = (TextView) viewInflate.findViewById(R.id.tv_cancel);
        textView.setText(getResources().getString(R.string.Confirm_to_close));
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.home.HomePageFragment$$ExternalSyntheticLambda30
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$showCloseWindow$13(i, i2, easyPopupWindow, view);
            }
        });
        TextView textView2 = (TextView) viewInflate.findViewById(R.id.tv_confirm);
        textView2.setText(getResources().getString(R.string.Continue_to_experience));
        textView2.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.home.HomePageFragment$$ExternalSyntheticLambda31
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                HomePageFragment.lambda$showCloseWindow$14(easyPopupWindow, i, view);
            }
        });
        ((TextView) viewInflate.findViewById(R.id.tv_content)).setText(getString(R.string.Close_experience_prompt));
        easyPopupWindow.setContentView(viewInflate);
        easyPopupWindow.setHeight(-1);
        easyPopupWindow.setWidth(-1);
        easyPopupWindow.showAtLocation(requireActivity().getWindow().getDecorView(), 17, 0, 0);
    }

    public /* synthetic */ void lambda$showCloseWindow$13(int i, int i2, EasyPopupWindow easyPopupWindow, View view) {
        ((HomePagePresenter) this.mPresenter).updateVirtualEntrance(i, i2);
        HomeCardDataUtils.getInstance().setIsNeedRefreshDeviceRoaster(getContext(), FamilyUtils.getInstance().getCurrentFamilyId(getContext()), true);
        ((HomePagePresenter) this.mPresenter).getHomeDeviceList();
        easyPopupWindow.dismiss();
        HashMap map = new HashMap();
        if (i == 9) {
            map.put("type", "D");
            return;
        }
        if (i == 22) {
            map.put("type", "H");
            return;
        }
        if (i == 14) {
            map.put("type", ExifInterface.LONGITUDE_WEST);
            return;
        }
        if (i == 15) {
            map.put("type", ExifInterface.GPS_DIRECTION_TRUE);
        } else if (i == 25) {
            map.put("type", "D4SH");
        } else {
            if (i != 26) {
                return;
            }
            map.put("type", "D4H");
        }
    }

    public static /* synthetic */ void lambda$showCloseWindow$14(EasyPopupWindow easyPopupWindow, int i, View view) {
        easyPopupWindow.dismiss();
        HashMap map = new HashMap();
        if (i == 9) {
            map.put("type", "D");
            return;
        }
        if (i == 22) {
            map.put("type", "H");
            return;
        }
        if (i == 14) {
            map.put("type", ExifInterface.LONGITUDE_WEST);
            return;
        }
        if (i == 15) {
            map.put("type", ExifInterface.GPS_DIRECTION_TRUE);
        } else if (i == 25) {
            map.put("type", "D4SH");
        } else {
            if (i != 26) {
                return;
            }
            map.put("type", "D4H");
        }
    }

    public void showDeviceCloseWindow(final long j, final String str, final String str2) {
        View viewInflate = LayoutInflater.from(getActivity()).inflate(R.layout.pop_prompt_layout, (ViewGroup) null);
        final EasyPopupWindow easyPopupWindow = new EasyPopupWindow(getActivity());
        easyPopupWindow.setOutsideTouchable(false);
        ((TextView) viewInflate.findViewById(R.id.tv_cancel)).setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.home.HomePageFragment$$ExternalSyntheticLambda41
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                easyPopupWindow.dismiss();
            }
        });
        ((TextView) viewInflate.findViewById(R.id.tv_confirm)).setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.home.HomePageFragment$$ExternalSyntheticLambda42
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$showDeviceCloseWindow$16(easyPopupWindow, str, j, str2, view);
            }
        });
        ((TextView) viewInflate.findViewById(R.id.tv_content)).setText(getString(R.string.Hint_delete_k2));
        easyPopupWindow.setContentView(viewInflate);
        easyPopupWindow.setHeight(-1);
        easyPopupWindow.setWidth(-1);
        easyPopupWindow.showAtLocation(requireActivity().getWindow().getDecorView(), 17, 0, 0);
    }

    public /* synthetic */ void lambda$showDeviceCloseWindow$16(EasyPopupWindow easyPopupWindow, String str, long j, String str2, View view) {
        easyPopupWindow.dismiss();
        if (CommonUtils.getDeviceTypeByString(str) == 3) {
            if (GoDataUtils.getGoRecordById(j) == null || !GoDataUtils.getGoRecordById(j).getOwnerId().equals(UserInforUtils.getCurrentUserId(getContext()))) {
                return;
            }
            ((HomePagePresenter) this.mPresenter).deleteDevice(j, str, str2);
            return;
        }
        if (CommonUtils.getDeviceTypeByString(str) == 1) {
            ((HomePagePresenter) this.mPresenter).deleteDevice(j, str, str2);
        } else {
            com.petkit.android.activities.device.utils.DeviceUtils.findDeviceInfo(getActivity(), j, CommonUtils.getDeviceTypeByString(str), new PetkitCallback<DeviceInfos>() { // from class: com.petkit.android.activities.home.HomePageFragment.22
                public final /* synthetic */ long val$deviceId;
                public final /* synthetic */ String val$deviceType;
                public final /* synthetic */ String val$petId;

                @Override // com.petkit.android.api.PetkitCallback
                public void onFailure(ErrorInfor errorInfor) {
                }

                public AnonymousClass22(String str3, long j2, String str22) {
                    str = str3;
                    j = j2;
                    str = str22;
                }

                @Override // com.petkit.android.api.PetkitCallback
                public void onSuccess(DeviceInfos deviceInfos) {
                    if (CommonUtils.getDeviceTypeByString(str) != 5 ? deviceInfos.getDeviceShared() == null : ((CozyRecord) deviceInfos).getShared() == null) {
                        ((HomePagePresenter) ((BaseFragment) HomePageFragment.this).mPresenter).deleteDevice(j, str, str);
                    } else {
                        ((HomePagePresenter) ((BaseFragment) HomePageFragment.this).mPresenter).cancelShare(j, str);
                    }
                }
            });
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.home.HomePageFragment$22 */
    public class AnonymousClass22 implements PetkitCallback<DeviceInfos> {
        public final /* synthetic */ long val$deviceId;
        public final /* synthetic */ String val$deviceType;
        public final /* synthetic */ String val$petId;

        @Override // com.petkit.android.api.PetkitCallback
        public void onFailure(ErrorInfor errorInfor) {
        }

        public AnonymousClass22(String str3, long j2, String str22) {
            str = str3;
            j = j2;
            str = str22;
        }

        @Override // com.petkit.android.api.PetkitCallback
        public void onSuccess(DeviceInfos deviceInfos) {
            if (CommonUtils.getDeviceTypeByString(str) != 5 ? deviceInfos.getDeviceShared() == null : ((CozyRecord) deviceInfos).getShared() == null) {
                ((HomePagePresenter) ((BaseFragment) HomePageFragment.this).mPresenter).deleteDevice(j, str, str);
            } else {
                ((HomePagePresenter) ((BaseFragment) HomePageFragment.this).mPresenter).cancelShare(j, str);
            }
        }
    }

    @Override // com.petkit.android.activities.home.NewCardOnClickListener
    public void onBannerClick(HomeDeviceData homeDeviceData, int i) {
        if (TextUtils.isEmpty(homeDeviceData.getAppBanner().get(i).getTargetUrl())) {
            return;
        }
        new HashMap().put("type", String.valueOf(homeDeviceData.getAppBanner().get(i).getId()));
        startActivity(WebviewActivity.newIntent(getActivity(), "", homeDeviceData.getAppBanner().get(i).getTargetUrl()));
    }

    @Override // com.petkit.android.activities.home.NewCardOnClickListener
    public void onLargeVirtualCloseClick(HomeDeviceData homeDeviceData) {
        showCloseVirtualWindow(homeDeviceData);
    }

    @Override // com.petkit.android.activities.home.NewCardOnClickListener
    public void onConsumableClick(View view, long j, int i, ToDoBean toDoBean, String str) {
        onCbClick(view, toDoBean, j, i, str);
    }

    @Override // com.petkit.android.activities.home.NewCardOnClickListener
    public void onSmallConsumableTitleClick(View view, long j, int i, ToDoBean toDoBean, String str) {
        onCbTitleClick(view, toDoBean, j, i, str);
    }

    @Override // com.petkit.android.activities.home.NewCardOnClickListener
    public void onConsumableTitleClick(View view, long j, int i, ToDoBean toDoBean, String str) {
        onCbTitleClick(view, toDoBean, j, i, str);
    }

    @Override // com.petkit.android.activities.home.NewCardOnClickListener
    public void onVirtualDraged(RecyclerView.ViewHolder viewHolder, HomeDeviceData homeDeviceData) {
        if (viewHolder != null) {
            getLocation(viewHolder.itemView, homeDeviceData, false);
        }
    }

    @Override // com.petkit.android.activities.home.NewCardOnClickListener
    public void onSmallConsumableClick(View view, long j, int i, ToDoBean toDoBean, String str) {
        onCbClick(view, toDoBean, j, i, str);
    }

    @Override // com.petkit.android.activities.home.NewCardOnClickListener
    public void onItemMove(CardRankResult cardRankResult, int i) {
        ((HomePagePresenter) this.mPresenter).saveDeviceCardRank(cardRankResult);
        if (i == 1) {
            if (this.homePageLargeDeviceAdapter != null) {
                this.isChangePosition = true;
                List<NewHomeCardData> newLocalCardList = ((HomePagePresenter) this.mPresenter).getNewLocalCardList();
                this.deviceDataListLarge = newLocalCardList;
                this.homePageLargeDeviceAdapter.updateAdapter(newLocalCardList);
                return;
            }
            return;
        }
        if (i != 2 || this.homePageDeviceAdapter == null) {
            return;
        }
        this.isChangePosition = true;
        List<NewHomeCardData> newLocalCardList2 = ((HomePagePresenter) this.mPresenter).getNewLocalCardList();
        this.newHomeCardDataList = newLocalCardList2;
        this.homePageDeviceAdapter.updateAdapter(newLocalCardList2);
    }

    @Override // com.petkit.android.activities.home.adapter.HomeTopCardPageAdapter.OnItemClickListener
    public void onItemClick(int i) {
        if (i == 0) {
            launchActivity(TodoActivity.newIntent(getActivity()));
        } else if (i == 1) {
            launchActivity(CommonFragmentActivity.newIntent(getActivity(), 0, 1));
        } else {
            if (i != 2) {
                return;
            }
            launchActivity(CommonFragmentActivity.newIntent(getActivity(), 0, 0));
        }
    }

    @Override // com.petkit.android.activities.home.adapter.HomeBannerPageAdapter.OnItemClickListener
    public void onBannerItemClick(int i) {
        try {
            List<AppBanner> appBannerList = DeviceCenterUtils.getAppBannerList();
            if (appBannerList == null || TextUtils.isEmpty(appBannerList.get(i).getTargetUrl())) {
                return;
            }
            MallUtils.goToWebOrProductDetail(getActivity(), appBannerList.get(i).getTargetUrl(), 0);
        } catch (Exception e) {
            PetkitLog.d("onBannerItemClick error:" + e.getMessage());
            LogcatStorageHelper.addLog("onBannerItemClick error:" + e.getMessage());
        }
    }

    @Override // com.petkit.android.activities.home.contract.HomePageContract.View
    public void refreshCameraPrivacyPolicy(int i, long j, int i2, int i3) {
        if (i != 1) {
            if (!FamilyUtils.getInstance().checkIsSharedDevice(getContext(), j, i2)) {
                showCameraPrivacyWindow(i3, j, i2);
                return;
            } else {
                showNotifyOwnerAgreeCameraPrivacyWindow();
                return;
            }
        }
        cameraDeviceEnterDeviceView(j, i2);
    }

    @Override // com.petkit.android.activities.home.contract.HomePageContract.View
    public void saveCardMode(int i) {
        CommonUtils.addSysIntMap(requireActivity(), "HOME_CARD_MODE", i);
    }

    @Override // androidx.fragment.app.Fragment
    public void onHiddenChanged(boolean z) {
        String hexString;
        super.onHiddenChanged(z);
        if (z || getActivity() == null || !isAdded()) {
            return;
        }
        float f = this.scale;
        if (f == 0.0f) {
            ImmersionBar.with(this).statusBarColor(R.color.transparent).statusBarDarkFont(true).navigationBarColor(R.color.gray_bg).navigationBarDarkIcon(true).init();
            EventBus.getDefault().post(new ScrollEvent());
            return;
        }
        if (Integer.toHexString((int) (f * 255.0f)).length() < 2) {
            hexString = "0" + Integer.toHexString((int) (this.scale * 255.0f));
        } else {
            hexString = Integer.toHexString((int) (this.scale * 255.0f));
        }
        EventBus.getDefault().post(new ScrollEvent((MqttTopic.MULTI_LEVEL_WILDCARD + hexString + "FFFFFF").toUpperCase()));
    }

    @Override // com.jess.arms.base.BaseFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        Disposable disposable = this.disposable;
        if (disposable != null && !disposable.isDisposed()) {
            this.disposable.dispose();
            this.disposable = null;
        }
        Bitmap bitmap = this.bitmap;
        if (bitmap != null) {
            bitmap.recycle();
            this.bitmap = null;
        }
        Disposable disposable2 = this.d;
        if (disposable2 == null || disposable2.isDisposed()) {
            return;
        }
        this.d.dispose();
        this.d = null;
    }

    @Subscriber
    public void onTempUnitChanged(TempUnitChangeEvent tempUnitChangeEvent) {
        for (int i = 0; i < this.homePageDeviceAdapter.getDeviceDataList().size(); i++) {
            if (this.homePageDeviceAdapter.getDeviceDataList().get(i).getHomeDeviceData().getType().equals("Hg")) {
                PetkitBleDeviceManager.getInstance().postCustomData(HgUtils.getHgRecordByDeviceId(this.homePageDeviceAdapter.getDeviceDataList().get(i).getHomeDeviceData().getData().getId()).getMac(), 22, HgDataConvertor.modifyUnit(UserInforUtils.getCurrentLoginResult().getSettings().getTempUnit()));
            }
        }
    }

    @Override // com.petkit.android.activities.universalWindow.FamilySwitchWindow.FamilySwitchOnClickListener
    public void onViewClick(@NonNull FamilyInfor familyInfor) {
        FamilyUtils.getInstance().saveCurrentFamilyId(getActivity(), familyInfor.getGroupId());
        this.isSwitchCardMode = true;
        this.showFamilyName = true;
        P p = this.mPresenter;
        if (p != 0) {
            ((HomePagePresenter) p).changeFamily(true);
        }
    }

    @Override // com.petkit.android.activities.home.contract.HomePageContract.View
    public void changeFamily(long j) {
        FamilyUtils.getInstance().saveCurrentFamilyId(getActivity(), j);
        this.isSwitchCardMode = true;
        this.showFamilyName = true;
        P p = this.mPresenter;
        if (p != 0) {
            ((HomePagePresenter) p).changeFamily(true);
        }
    }

    @Override // com.petkit.android.activities.universalWindow.FamilySwitchWindow.FamilySwitchOnClickListener
    public void onFamilyManager() {
        if (getActivity() != null) {
            getActivity().startActivity(FamilyListActivity.newIntent(getContext()));
        }
    }

    private void showD3ManualFeedWindow(long j) {
        D3ManualWindow d3ManualWindow = this.d3ManualFeedWindow;
        if (d3ManualWindow == null || !d3ManualWindow.isShowing()) {
            D3ManualWindow d3ManualWindow2 = new D3ManualWindow(getActivity(), true, j);
            this.d3ManualFeedWindow = d3ManualWindow2;
            d3ManualWindow2.setBackgroundDrawable(new BitmapDrawable());
            this.d3ManualFeedWindow.setFeedManualListener(new D3ManualWindow.onFeedManualListener() { // from class: com.petkit.android.activities.home.HomePageFragment$$ExternalSyntheticLambda36
                @Override // com.petkit.android.activities.petkitBleDevice.d3.widget.D3ManualWindow.onFeedManualListener
                public final void onFeedManualSuccess(int i, int i2, int i3) {
                    HomePageFragment.lambda$showD3ManualFeedWindow$17(i, i2, i3);
                }
            });
            this.d3ManualFeedWindow.showAtLocation(requireActivity().getWindow().getDecorView(), 17, 0, 0);
        }
    }

    private void showD4ManualFeedWindow(long j) {
        D4ManualWindow d4ManualWindow = this.d4ManualFeedWindow;
        if (d4ManualWindow == null || !d4ManualWindow.isShowing()) {
            D4ManualWindow d4ManualWindow2 = new D4ManualWindow(getActivity(), true, j);
            this.d4ManualFeedWindow = d4ManualWindow2;
            d4ManualWindow2.setBackgroundDrawable(new BitmapDrawable());
            this.d4ManualFeedWindow.setFeedManualListener(new D4ManualWindow.onFeedManualListener() { // from class: com.petkit.android.activities.home.HomePageFragment$$ExternalSyntheticLambda32
                @Override // com.petkit.android.activities.petkitBleDevice.d4.widget.D4ManualWindow.onFeedManualListener
                public final void onFeedManualSuccess(int i, int i2, int i3) {
                    HomePageFragment.lambda$showD4ManualFeedWindow$18(i, i2, i3);
                }
            });
            this.d4ManualFeedWindow.showAtLocation(requireActivity().getWindow().getDecorView(), 17, 0, 0);
        }
    }

    private void showD4sManualFeedWindow(long j) {
        D4sManualWindow d4sManualWindow = this.d4sManualFeedWindow;
        if (d4sManualWindow == null || !d4sManualWindow.isShowing()) {
            D4sManualWindow d4sManualWindow2 = new D4sManualWindow(getActivity(), j);
            this.d4sManualFeedWindow = d4sManualWindow2;
            d4sManualWindow2.setBackgroundDrawable(new BitmapDrawable());
            this.d4sManualFeedWindow.setFeedManualListener(new D4sManualWindow.onFeedManualListener() { // from class: com.petkit.android.activities.home.HomePageFragment$$ExternalSyntheticLambda3
                @Override // com.petkit.android.activities.petkitBleDevice.d4s.widget.D4sManualWindow.onFeedManualListener
                public final void onFeedManualSuccess(int i, int i2, int i3, int i4) {
                    HomePageFragment.lambda$showD4sManualFeedWindow$19(i, i2, i3, i4);
                }
            });
            this.d4sManualFeedWindow.showAtLocation(requireActivity().getWindow().getDecorView(), 17, 0, 0);
        }
    }

    private void showCancelD4sFeedingDialog(final long j) {
        new AlertDialog.Builder(getActivity()).setCancelable(false).setTitle(R.string.Prompt).setMessage(R.string.Hint_feeding_stop).setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() { // from class: com.petkit.android.activities.home.HomePageFragment$$ExternalSyntheticLambda8
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.lambda$showCancelD4sFeedingDialog$20(j, dialogInterface, i);
            }
        }).setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() { // from class: com.petkit.android.activities.home.HomePageFragment$$ExternalSyntheticLambda9
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                HomePageFragment.lambda$showCancelD4sFeedingDialog$21(dialogInterface, i);
            }
        }).show();
    }

    public /* synthetic */ void lambda$showCancelD4sFeedingDialog$20(long j, DialogInterface dialogInterface, int i) {
        ((HomePagePresenter) this.mPresenter).stopD4sFeeding(j);
    }

    private void showD4shManualFeedWindow(long j) {
        D4shManualWindow d4shManualWindow = this.d4shManualFeedWindow;
        if (d4shManualWindow == null || !d4shManualWindow.isShowing()) {
            int sysIntMap = CommonUtils.getSysIntMap(getActivity(), Consts.DEVICE_ADD_MEAL_NOW_D4SH_LAST_AMOUNT1, 1);
            int sysIntMap2 = CommonUtils.getSysIntMap(getActivity(), Consts.DEVICE_ADD_MEAL_NOW_D4SH_LAST_AMOUNT2, 1);
            D4shManualWindow d4shManualWindow2 = new D4shManualWindow(getActivity(), j, 0);
            this.d4shManualFeedWindow = d4shManualWindow2;
            d4shManualWindow2.setNumberPickerValue(sysIntMap, sysIntMap2);
            this.d4shManualFeedWindow.setFeedManualListener(new D4shManualWindow.onFeedManualListener() { // from class: com.petkit.android.activities.home.HomePageFragment$$ExternalSyntheticLambda65
                @Override // com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shManualWindow.onFeedManualListener
                public final void onFeedManualSuccess(int i, int i2, int i3, int i4) {
                    HomePageFragment.lambda$showD4shManualFeedWindow$22(i, i2, i3, i4);
                }
            });
            this.d4shManualFeedWindow.show(requireActivity().getWindow().getDecorView());
        }
    }

    private void showD4hManualFeedWindow(long j) {
        D4shRecord d4shRecordByDeviceId;
        D4hManualWindow d4hManualWindow = this.d4hManualFeedWindow;
        if ((d4hManualWindow == null || !d4hManualWindow.isShowing()) && (d4shRecordByDeviceId = D4shUtils.getD4shRecordByDeviceId(j, 1)) != null) {
            D4hManualWindow d4hManualWindow2 = new D4hManualWindow(getActivity(), j, d4shRecordByDeviceId);
            this.d4hManualFeedWindow = d4hManualWindow2;
            d4hManualWindow2.setBackgroundDrawable(new BitmapDrawable());
            this.d4hManualFeedWindow.setFeedManualListener(new D4hManualWindow.onFeedManualListener() { // from class: com.petkit.android.activities.home.HomePageFragment$$ExternalSyntheticLambda66
                @Override // com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4hManualWindow.onFeedManualListener
                public final void onFeedManualSuccess(int i, int i2, int i3, int i4) {
                    HomePageFragment.lambda$showD4hManualFeedWindow$23(i, i2, i3, i4);
                }
            });
            this.d4hManualFeedWindow.showAtLocation(requireActivity().getWindow().getDecorView(), 17, 0, 0);
        }
    }

    private void showCancelD4FeedingDialog(final long j) {
        new AlertDialog.Builder(getActivity()).setCancelable(false).setTitle(R.string.Prompt).setMessage(R.string.Hint_feeding_stop).setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() { // from class: com.petkit.android.activities.home.HomePageFragment$$ExternalSyntheticLambda27
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.lambda$showCancelD4FeedingDialog$24(j, dialogInterface, i);
            }
        }).setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() { // from class: com.petkit.android.activities.home.HomePageFragment$$ExternalSyntheticLambda28
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                HomePageFragment.lambda$showCancelD4FeedingDialog$25(dialogInterface, i);
            }
        }).show();
    }

    public /* synthetic */ void lambda$showCancelD4FeedingDialog$24(long j, DialogInterface dialogInterface, int i) {
        ((HomePagePresenter) this.mPresenter).stopD4Feeding(j);
    }

    private void showCancelD4shFeedingDialog(final long j) {
        new AlertDialog.Builder(getActivity()).setCancelable(false).setTitle(R.string.Prompt).setMessage(R.string.Hint_feeding_stop).setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() { // from class: com.petkit.android.activities.home.HomePageFragment$$ExternalSyntheticLambda63
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.lambda$showCancelD4shFeedingDialog$26(j, dialogInterface, i);
            }
        }).setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() { // from class: com.petkit.android.activities.home.HomePageFragment$$ExternalSyntheticLambda64
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                HomePageFragment.lambda$showCancelD4shFeedingDialog$27(dialogInterface, i);
            }
        }).show();
    }

    public /* synthetic */ void lambda$showCancelD4shFeedingDialog$26(long j, DialogInterface dialogInterface, int i) {
        ((HomePagePresenter) this.mPresenter).stopD4shFeeding(j);
    }

    private void showCancelD3FeedingDialog(final long j) {
        new AlertDialog.Builder(getActivity()).setCancelable(false).setTitle(R.string.Prompt).setMessage(R.string.Hint_feeding_stop).setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() { // from class: com.petkit.android.activities.home.HomePageFragment$$ExternalSyntheticLambda67
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.lambda$showCancelD3FeedingDialog$28(j, dialogInterface, i);
            }
        }).setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() { // from class: com.petkit.android.activities.home.HomePageFragment$$ExternalSyntheticLambda68
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                HomePageFragment.lambda$showCancelD3FeedingDialog$29(dialogInterface, i);
            }
        }).show();
    }

    public /* synthetic */ void lambda$showCancelD3FeedingDialog$28(long j, DialogInterface dialogInterface, int i) {
        ((HomePagePresenter) this.mPresenter).stopD3Feeding(j);
    }

    private void showCancelD4hFeedingDialog(final long j) {
        new AlertDialog.Builder(getActivity()).setCancelable(false).setTitle(R.string.Prompt).setMessage(R.string.Hint_feeding_stop).setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() { // from class: com.petkit.android.activities.home.HomePageFragment$$ExternalSyntheticLambda34
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.lambda$showCancelD4hFeedingDialog$30(j, dialogInterface, i);
            }
        }).setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() { // from class: com.petkit.android.activities.home.HomePageFragment$$ExternalSyntheticLambda35
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                HomePageFragment.lambda$showCancelD4hFeedingDialog$31(dialogInterface, i);
            }
        }).show();
    }

    public /* synthetic */ void lambda$showCancelD4hFeedingDialog$30(long j, DialogInterface dialogInterface, int i) {
        ((HomePagePresenter) this.mPresenter).stopD4hFeeding(j);
    }

    private void showT6CleanUpWindow(final long j, final int i) {
        View viewInflate = LayoutInflater.from(getActivity()).inflate(R.layout.pop_t3_clean_layout, (ViewGroup) null);
        final EasyPopupWindow easyPopupWindow = new EasyPopupWindow(getActivity());
        easyPopupWindow.setOutsideTouchable(false);
        TextView textView = (TextView) viewInflate.findViewById(R.id.tv_cancel);
        textView.setTextColor(getResources().getColor(R.color.t3_main_blue));
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.home.HomePageFragment$$ExternalSyntheticLambda10
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                easyPopupWindow.dismiss();
            }
        });
        TextView textView2 = (TextView) viewInflate.findViewById(R.id.tv_confirm);
        textView2.setTextColor(getResources().getColor(R.color.t3_main_blue));
        textView2.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.home.HomePageFragment$$ExternalSyntheticLambda11
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$showT6CleanUpWindow$33(easyPopupWindow, j, i, view);
            }
        });
        TextView textView3 = (TextView) viewInflate.findViewById(R.id.tv_content);
        T6Record t6RecordByDeviceId = T6Utils.getT6RecordByDeviceId(j, 27 == i ? 0 : 1);
        if (t6RecordByDeviceId != null && t6RecordByDeviceId.getSettings().getKitten() == 1) {
            textView3.setText(getString(R.string.Kitten_clean_prompt));
        } else {
            textView3.setText(getString(R.string.Clean_litter_prompt));
        }
        easyPopupWindow.setContentView(viewInflate);
        easyPopupWindow.setHeight(-1);
        easyPopupWindow.setWidth((int) (BaseApplication.displayMetrics.widthPixels * 0.8f));
        easyPopupWindow.showAtLocation(requireActivity().getWindow().getDecorView(), 17, 0, 0);
    }

    public /* synthetic */ void lambda$showT6CleanUpWindow$33(EasyPopupWindow easyPopupWindow, long j, int i, View view) {
        easyPopupWindow.dismiss();
        ((HomePagePresenter) this.mPresenter).controlT6Device(j, 0, 1, i);
    }

    private void showT4CleanUpWindow(final long j) {
        View viewInflate = LayoutInflater.from(getActivity()).inflate(R.layout.pop_t3_clean_layout, (ViewGroup) null);
        final EasyPopupWindow easyPopupWindow = new EasyPopupWindow(getActivity());
        easyPopupWindow.setOutsideTouchable(false);
        TextView textView = (TextView) viewInflate.findViewById(R.id.tv_cancel);
        textView.setTextColor(getResources().getColor(R.color.t3_main_blue));
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.home.HomePageFragment$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                easyPopupWindow.dismiss();
            }
        });
        TextView textView2 = (TextView) viewInflate.findViewById(R.id.tv_confirm);
        textView2.setTextColor(getResources().getColor(R.color.t3_main_blue));
        textView2.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.home.HomePageFragment$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$showT4CleanUpWindow$35(easyPopupWindow, j, view);
            }
        });
        TextView textView3 = (TextView) viewInflate.findViewById(R.id.tv_content);
        textView3.setText(getString(R.string.Clean_litter_prompt));
        if (T4Utils.getT4RecordByDeviceId(j).getSettings().getKitten() == 1) {
            textView3.setText(getString(R.string.Kitten_clean_prompt));
        } else {
            textView3.setText(getString(R.string.Clean_litter_prompt));
        }
        easyPopupWindow.setContentView(viewInflate);
        easyPopupWindow.setHeight(-1);
        easyPopupWindow.setWidth((int) (BaseApplication.displayMetrics.widthPixels * 0.8f));
        easyPopupWindow.showAtLocation(requireActivity().getWindow().getDecorView(), 17, 0, 0);
    }

    public /* synthetic */ void lambda$showT4CleanUpWindow$35(EasyPopupWindow easyPopupWindow, long j, View view) {
        easyPopupWindow.dismiss();
        ((HomePagePresenter) this.mPresenter).controlT4Device(j, 0, 1);
    }

    private void showT3CleanUpWindow(final long j) {
        View viewInflate = LayoutInflater.from(getActivity()).inflate(R.layout.pop_t3_clean_layout, (ViewGroup) null);
        final EasyPopupWindow easyPopupWindow = new EasyPopupWindow(getActivity());
        easyPopupWindow.setOutsideTouchable(false);
        TextView textView = (TextView) viewInflate.findViewById(R.id.tv_cancel);
        textView.setTextColor(getResources().getColor(R.color.t3_main_blue));
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.home.HomePageFragment$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                easyPopupWindow.dismiss();
            }
        });
        TextView textView2 = (TextView) viewInflate.findViewById(R.id.tv_confirm);
        textView2.setTextColor(getResources().getColor(R.color.t3_main_blue));
        textView2.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.home.HomePageFragment$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$showT3CleanUpWindow$37(easyPopupWindow, j, view);
            }
        });
        TextView textView3 = (TextView) viewInflate.findViewById(R.id.tv_content);
        T3Record t3RecordByDeviceId = BleDeviceUtils.getT3RecordByDeviceId(j);
        if (t3RecordByDeviceId == null) {
            return;
        }
        if (t3RecordByDeviceId.getSettings().getKitten() == 1) {
            textView3.setText(getString(R.string.Kitten_clean_prompt));
        } else {
            textView3.setText(getString(R.string.Clean_litter_prompt));
        }
        easyPopupWindow.setContentView(viewInflate);
        easyPopupWindow.setHeight(-1);
        easyPopupWindow.setWidth((int) (BaseApplication.displayMetrics.widthPixels * 0.8f));
        easyPopupWindow.showAtLocation(requireActivity().getWindow().getDecorView(), 17, 0, 0);
    }

    public /* synthetic */ void lambda$showT3CleanUpWindow$37(EasyPopupWindow easyPopupWindow, long j, View view) {
        easyPopupWindow.dismiss();
        ((HomePagePresenter) this.mPresenter).controlT3Device(j, 0, 1);
    }

    public void onCbTitleClick(View view, ToDoBean toDoBean, final long j, int i, String str) {
        String str2;
        boolean z = toDoBean.getErrType() != null && toDoBean.getErrType().equals(Constants.HOME_TODO_CARD_DEVICE_ERR_WARNING);
        boolean z2 = toDoBean.getErrType() != null && toDoBean.getErrType().equals(Constants.HOME_TODO_CARD_DEVICE_ERR_DESICCANT);
        final boolean z3 = toDoBean.getErrType() != null && toDoBean.getErrType().equals(Constants.HOME_TODO_CARD_DEVICE_ERR_DEODORANT);
        boolean z4 = toDoBean.getErrType() != null && toDoBean.getErrType().equals(Constants.HOME_TODO_CARD_DEVICE_ERR_PURIFICATION);
        boolean z5 = toDoBean.getErrType() != null && toDoBean.getErrType().equals(Constants.HOME_TODO_CARD_DEVICE_ERR_FILTER);
        HashMap map = new HashMap();
        map.put("Enter", "HomeCard");
        if (z || z2 || z3 || z4 || z5) {
            if (i == 14) {
                W5Record w5RecordByDeviceId = W5Utils.getW5RecordByDeviceId(j);
                if (w5RecordByDeviceId != null) {
                    if (w5RecordByDeviceId.getTypeCode() == 1) {
                        map.put("DeviceType", "w5");
                        str2 = "W5";
                    } else {
                        if (w5RecordByDeviceId.getTypeCode() == 2) {
                            map.put("DeviceType", "w5c");
                        } else if (w5RecordByDeviceId.getTypeCode() == 3) {
                            map.put("DeviceType", "w5c");
                            str2 = "W5N";
                        } else if (w5RecordByDeviceId.getTypeCode() == 4 || w5RecordByDeviceId.getTypeCode() == 6) {
                            map.put("DeviceType", "w4x");
                            str2 = "W4X";
                        } else if (w5RecordByDeviceId.getTypeCode() == 5) {
                            map.put("DeviceType", "ctw2");
                            str2 = "CTW2";
                        }
                        str2 = "W5C";
                    }
                    map.put("AccessoriesType", Constants.HOME_TODO_CARD_DEVICE_ERR_FILTER);
                    ((HomePagePresenter) this.mPresenter).getRelatedProductsInfor(str2, new HomePagePresenter.MallInfoCallBack() { // from class: com.petkit.android.activities.home.HomePageFragment$$ExternalSyntheticLambda43
                        @Override // com.petkit.android.activities.home.presenter.HomePagePresenter.MallInfoCallBack
                        public final void onResult(RelatedProductsInfor relatedProductsInfor) {
                            this.f$0.lambda$onCbTitleClick$38(j, relatedProductsInfor);
                        }
                    });
                    return;
                }
                return;
            }
            if (i == 11) {
                D4Record d4RecordByDeviceId = D4Utils.getD4RecordByDeviceId(j);
                map.put("DeviceType", "d4");
                map.put("AccessoriesType", Constants.HOME_TODO_CARD_DEVICE_ERR_DESICCANT);
                if (d4RecordByDeviceId != null) {
                    ((HomePagePresenter) this.mPresenter).getRelatedProductsInfor("D4", new HomePagePresenter.MallInfoCallBack() { // from class: com.petkit.android.activities.home.HomePageFragment$$ExternalSyntheticLambda46
                        @Override // com.petkit.android.activities.home.presenter.HomePagePresenter.MallInfoCallBack
                        public final void onResult(RelatedProductsInfor relatedProductsInfor) {
                            this.f$0.lambda$onCbTitleClick$39(j, relatedProductsInfor);
                        }
                    });
                    return;
                }
                return;
            }
            if (i == 20) {
                D4sRecord d4sRecordByDeviceId = D4sUtils.getD4sRecordByDeviceId(j);
                map.put("DeviceType", "d4s");
                map.put("AccessoriesType", Constants.HOME_TODO_CARD_DEVICE_ERR_DESICCANT);
                if (d4sRecordByDeviceId != null) {
                    ((HomePagePresenter) this.mPresenter).getRelatedProductsInfor("D4S", new HomePagePresenter.MallInfoCallBack() { // from class: com.petkit.android.activities.home.HomePageFragment$$ExternalSyntheticLambda47
                        @Override // com.petkit.android.activities.home.presenter.HomePagePresenter.MallInfoCallBack
                        public final void onResult(RelatedProductsInfor relatedProductsInfor) {
                            this.f$0.lambda$onCbTitleClick$40(j, relatedProductsInfor);
                        }
                    });
                    return;
                }
                return;
            }
            if (i == 9) {
                D3Record d3RecordByDeviceId = D3Utils.getD3RecordByDeviceId(j);
                map.put("DeviceType", "d3");
                map.put("AccessoriesType", Constants.HOME_TODO_CARD_DEVICE_ERR_DESICCANT);
                if (d3RecordByDeviceId != null) {
                    ((HomePagePresenter) this.mPresenter).getRelatedProductsInfor("D3", new HomePagePresenter.MallInfoCallBack() { // from class: com.petkit.android.activities.home.HomePageFragment$$ExternalSyntheticLambda48
                        @Override // com.petkit.android.activities.home.presenter.HomePagePresenter.MallInfoCallBack
                        public final void onResult(RelatedProductsInfor relatedProductsInfor) {
                            this.f$0.lambda$onCbTitleClick$41(j, relatedProductsInfor);
                        }
                    });
                    return;
                }
                return;
            }
            if (i == 6) {
                D2Record d2RecordByDeviceId = D2Utils.getD2RecordByDeviceId(j);
                map.put("DeviceType", "d2");
                map.put("AccessoriesType", Constants.HOME_TODO_CARD_DEVICE_ERR_DESICCANT);
                if (d2RecordByDeviceId != null) {
                    ((HomePagePresenter) this.mPresenter).getRelatedProductsInfor("D2", new HomePagePresenter.MallInfoCallBack() { // from class: com.petkit.android.activities.home.HomePageFragment$$ExternalSyntheticLambda49
                        @Override // com.petkit.android.activities.home.presenter.HomePagePresenter.MallInfoCallBack
                        public final void onResult(RelatedProductsInfor relatedProductsInfor) {
                            this.f$0.lambda$onCbTitleClick$42(j, relatedProductsInfor);
                        }
                    });
                    return;
                }
                return;
            }
            if (i == 4) {
                FeederRecord feederRecordByDeviceId = FeederUtils.getFeederRecordByDeviceId(j);
                map.put("DeviceType", "d1");
                map.put("AccessoriesType", Constants.HOME_TODO_CARD_DEVICE_ERR_DESICCANT);
                if (feederRecordByDeviceId != null) {
                    ((HomePagePresenter) this.mPresenter).getRelatedProductsInfor("D1", new HomePagePresenter.MallInfoCallBack() { // from class: com.petkit.android.activities.home.HomePageFragment$$ExternalSyntheticLambda50
                        @Override // com.petkit.android.activities.home.presenter.HomePagePresenter.MallInfoCallBack
                        public final void onResult(RelatedProductsInfor relatedProductsInfor) {
                            this.f$0.lambda$onCbTitleClick$43(j, relatedProductsInfor);
                        }
                    });
                    return;
                }
                return;
            }
            if (i == 15) {
                T4Record t4RecordByDeviceId = T4Utils.getT4RecordByDeviceId(j);
                map.put("DeviceType", "t4");
                map.put("AccessoriesType", "N50");
                if (t4RecordByDeviceId != null) {
                    ((HomePagePresenter) this.mPresenter).getRelatedProductsInfor("T4", new HomePagePresenter.MallInfoCallBack() { // from class: com.petkit.android.activities.home.HomePageFragment$$ExternalSyntheticLambda51
                        @Override // com.petkit.android.activities.home.presenter.HomePagePresenter.MallInfoCallBack
                        public final void onResult(RelatedProductsInfor relatedProductsInfor) {
                            this.f$0.lambda$onCbTitleClick$44(j, relatedProductsInfor);
                        }
                    });
                    return;
                }
                return;
            }
            if (i == 25) {
                D4shRecord d4shRecordByDeviceId = D4shUtils.getD4shRecordByDeviceId(j, 0);
                map.put("DeviceType", "d4sh");
                map.put("AccessoriesType", Constants.HOME_TODO_CARD_DEVICE_ERR_DESICCANT);
                if (d4shRecordByDeviceId != null) {
                    ((HomePagePresenter) this.mPresenter).getRelatedProductsInfor("D4SH", new HomePagePresenter.MallInfoCallBack() { // from class: com.petkit.android.activities.home.HomePageFragment$$ExternalSyntheticLambda52
                        @Override // com.petkit.android.activities.home.presenter.HomePagePresenter.MallInfoCallBack
                        public final void onResult(RelatedProductsInfor relatedProductsInfor) {
                            this.f$0.lambda$onCbTitleClick$45(j, relatedProductsInfor);
                        }
                    });
                    return;
                }
                return;
            }
            if (i == 26) {
                D4shRecord d4shRecordByDeviceId2 = D4shUtils.getD4shRecordByDeviceId(j, 1);
                map.put("DeviceType", "d4h");
                map.put("AccessoriesType", Constants.HOME_TODO_CARD_DEVICE_ERR_DESICCANT);
                if (d4shRecordByDeviceId2 != null) {
                    ((HomePagePresenter) this.mPresenter).getRelatedProductsInfor("D4H", new HomePagePresenter.MallInfoCallBack() { // from class: com.petkit.android.activities.home.HomePageFragment$$ExternalSyntheticLambda53
                        @Override // com.petkit.android.activities.home.presenter.HomePagePresenter.MallInfoCallBack
                        public final void onResult(RelatedProductsInfor relatedProductsInfor) {
                            this.f$0.lambda$onCbTitleClick$46(j, relatedProductsInfor);
                        }
                    });
                    return;
                }
                return;
            }
            if (i == 21) {
                T6Record t6RecordByDeviceId = T6Utils.getT6RecordByDeviceId(j, 1);
                map.put("DeviceType", "t5");
                map.put("AccessoriesType", "purifyingLiquid");
                if (t6RecordByDeviceId != null) {
                    ((HomePagePresenter) this.mPresenter).getRelatedProductsInfor("T5", new HomePagePresenter.MallInfoCallBack() { // from class: com.petkit.android.activities.home.HomePageFragment$$ExternalSyntheticLambda54
                        @Override // com.petkit.android.activities.home.presenter.HomePagePresenter.MallInfoCallBack
                        public final void onResult(RelatedProductsInfor relatedProductsInfor) {
                            this.f$0.lambda$onCbTitleClick$47(j, z3, relatedProductsInfor);
                        }
                    });
                    return;
                }
                return;
            }
            if (i == 27) {
                T6Record t6RecordByDeviceId2 = T6Utils.getT6RecordByDeviceId(j, 0);
                map.put("DeviceType", "t6");
                map.put("AccessoriesType", Constants.HOME_TODO_CARD_DEVICE_ERR_PURIFICATION);
                if (t6RecordByDeviceId2 != null) {
                    ((HomePagePresenter) this.mPresenter).getRelatedProductsInfor("T6", new HomePagePresenter.MallInfoCallBack() { // from class: com.petkit.android.activities.home.HomePageFragment$$ExternalSyntheticLambda44
                        @Override // com.petkit.android.activities.home.presenter.HomePagePresenter.MallInfoCallBack
                        public final void onResult(RelatedProductsInfor relatedProductsInfor) {
                            this.f$0.lambda$onCbTitleClick$48(j, relatedProductsInfor);
                        }
                    });
                    return;
                }
                return;
            }
            if (i == 29) {
                W7hRecord w7hRecordByDeviceId = W7hDataUtils.getInstance().getW7hRecordByDeviceId(j);
                map.put("DeviceType", "w7h");
                map.put("AccessoriesType", Constants.HOME_TODO_CARD_DEVICE_ERR_FILTER);
                if (w7hRecordByDeviceId != null) {
                    ((HomePagePresenter) this.mPresenter).getRelatedProductsInfor("W7H", new HomePagePresenter.MallInfoCallBack() { // from class: com.petkit.android.activities.home.HomePageFragment$$ExternalSyntheticLambda45
                        @Override // com.petkit.android.activities.home.presenter.HomePagePresenter.MallInfoCallBack
                        public final void onResult(RelatedProductsInfor relatedProductsInfor) {
                            this.f$0.lambda$onCbTitleClick$49(j, relatedProductsInfor);
                        }
                    });
                }
            }
        }
    }

    public /* synthetic */ void lambda$onCbTitleClick$38(long j, RelatedProductsInfor relatedProductsInfor) {
        launchActivity(W5FilterRemainingActivity.newIntent(getActivity(), j, relatedProductsInfor != null ? relatedProductsInfor.getStandard().getW5() : null));
    }

    public /* synthetic */ void lambda$onCbTitleClick$39(long j, RelatedProductsInfor relatedProductsInfor) {
        launchActivity(FeederConsumablesActivity.newIntent(getActivity(), j, relatedProductsInfor, 11));
    }

    public /* synthetic */ void lambda$onCbTitleClick$40(long j, RelatedProductsInfor relatedProductsInfor) {
        launchActivity(FeederConsumablesActivity.newIntent(getActivity(), j, relatedProductsInfor, 20));
    }

    public /* synthetic */ void lambda$onCbTitleClick$41(long j, RelatedProductsInfor relatedProductsInfor) {
        launchActivity(FeederConsumablesActivity.newIntent(getActivity(), j, relatedProductsInfor, 9));
    }

    public /* synthetic */ void lambda$onCbTitleClick$42(long j, RelatedProductsInfor relatedProductsInfor) {
        launchActivity(FeederConsumablesActivity.newIntent(getActivity(), j, relatedProductsInfor, 6));
    }

    public /* synthetic */ void lambda$onCbTitleClick$43(long j, RelatedProductsInfor relatedProductsInfor) {
        launchActivity(FeederConsumablesActivity.newIntent(getActivity(), j, relatedProductsInfor, 4));
    }

    public /* synthetic */ void lambda$onCbTitleClick$44(long j, RelatedProductsInfor relatedProductsInfor) {
        launchActivity(T4ConsumablesActivity.newIntent(getActivity(), j, 4, relatedProductsInfor));
    }

    public /* synthetic */ void lambda$onCbTitleClick$45(long j, RelatedProductsInfor relatedProductsInfor) {
        launchActivity(FeederConsumablesActivity.newIntent(getActivity(), j, relatedProductsInfor, 25));
    }

    public /* synthetic */ void lambda$onCbTitleClick$46(long j, RelatedProductsInfor relatedProductsInfor) {
        launchActivity(FeederConsumablesActivity.newIntent(getActivity(), j, relatedProductsInfor, 26));
    }

    public /* synthetic */ void lambda$onCbTitleClick$47(long j, boolean z, RelatedProductsInfor relatedProductsInfor) {
        launchActivity(T6ConsumablesActivity.newIntent(getActivity(), j, 21, z ? 5 : 6, relatedProductsInfor));
    }

    public /* synthetic */ void lambda$onCbTitleClick$48(long j, RelatedProductsInfor relatedProductsInfor) {
        launchActivity(T6ConsumablesActivity.newIntent(getActivity(), j, 27, 4, relatedProductsInfor));
    }

    public /* synthetic */ void lambda$onCbTitleClick$49(long j, RelatedProductsInfor relatedProductsInfor) {
        W7hDataUtils.getInstance().saveRelativeProductInfo(relatedProductsInfor);
        launchActivity(W7hConsumablesActivity.newIntent(getActivity(), j, 3));
    }

    private void showT7Clean(HomeDeviceData homeDeviceData) {
        T7Record t7RecordById = T7DataUtils.getInstance().getT7RecordById(homeDeviceData.getData().getId());
        if (t7RecordById.getSettings().getKitten() == 1) {
            showCleanWindow(getString(R.string.Kitten_clean_prompt), homeDeviceData.getData().getId());
            return;
        }
        if (t7RecordById.getState().getSoftState() == 1) {
            showSoftCleanWindow(homeDeviceData.getData().getId());
        } else if ((System.currentTimeMillis() / 1000) - t7RecordById.getPetOutTime() < 1200) {
            showTimeLimitCleanWindow(homeDeviceData.getData().getId());
        } else {
            showCleanWindow(getString(R.string.Clean_litter_prompt), homeDeviceData.getData().getId());
        }
    }

    private void showCleanWindow(String str, final long j) {
        if (getActivity() == null || getActivity().isFinishing()) {
            return;
        }
        new FunctionDialog.Builder().setMarginPercent(0.14f).setShowTopLine(false).setCenterTipOne(str).setBottomLeftStr(getString(R.string.Cancel)).setBottomLeftColor(getResources().getColor(R.color.t4_text_gray)).setBottomRightStr(getString(R.string.Confirm)).setBottomRightColor(getResources().getColor(R.color.new_bind_blue)).setRightListener(new View.OnClickListener() { // from class: com.petkit.android.activities.home.HomePageFragment$$ExternalSyntheticLambda58
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$showCleanWindow$50(j, view);
            }
        }).builder((AppCompatActivity) getActivity()).show(getActivity().getWindow().getDecorView());
    }

    public /* synthetic */ void lambda$showCleanWindow$50(long j, View view) {
        ((HomePagePresenter) this.mPresenter).controlT7Device(j, 0, 1);
    }

    private void showSoftCleanWindow(final long j) {
        if (getActivity() == null || getActivity().isFinishing()) {
            return;
        }
        new FunctionDialog.Builder().setMarginPercent(0.14f).setShowTopLine(false).setCenterTipOne(getString(R.string.T7_recognize_soft_stools_clean_confirm)).setBottomLeftStr(getString(R.string.Not_cleaned_up)).setBottomLeftColor(getResources().getColor(R.color.t4_text_gray)).setBottomRightStr(getString(R.string.Clean_up)).setBottomRightColor(getResources().getColor(R.color.new_bind_blue)).setRightListener(new View.OnClickListener() { // from class: com.petkit.android.activities.home.HomePageFragment$$ExternalSyntheticLambda29
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$showSoftCleanWindow$51(j, view);
            }
        }).builder((AppCompatActivity) getActivity()).show(getActivity().getWindow().getDecorView());
    }

    public /* synthetic */ void lambda$showSoftCleanWindow$51(long j, View view) {
        ((HomePagePresenter) this.mPresenter).controlT7Device(j, 0, 1);
    }

    private void showTimeLimitCleanWindow(final long j) {
        if (getActivity() == null || getActivity().isFinishing()) {
            return;
        }
        new FunctionDialog.Builder().setMarginPercent(0.14f).setShowTopLine(false).setCenterTipOne(getString(R.string.T7_short_drying_time_clean_confirm)).setBottomLeftStr(getString(R.string.Not_cleaned_up)).setBottomLeftColor(getResources().getColor(R.color.t4_text_gray)).setBottomRightStr(getString(R.string.Clean_up)).setBottomRightColor(getResources().getColor(R.color.new_bind_blue)).setRightListener(new View.OnClickListener() { // from class: com.petkit.android.activities.home.HomePageFragment$$ExternalSyntheticLambda39
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$showTimeLimitCleanWindow$52(j, view);
            }
        }).builder((AppCompatActivity) getActivity()).show(getActivity().getWindow().getDecorView());
    }

    public /* synthetic */ void lambda$showTimeLimitCleanWindow$52(long j, View view) {
        ((HomePagePresenter) this.mPresenter).controlT7Device(j, 0, 1);
    }

    public void onCbClick(View view, final ToDoBean toDoBean, final long j, final int i, final String str) {
        String str2;
        int i2;
        int i3;
        this.bitmap = Bitmap.createBitmap(this.width, this.height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(this.bitmap);
        if (this.consView.getBackground() != null) {
            this.consView.getBackground().draw(canvas);
        }
        this.consView.draw(canvas);
        this.ivBlurBg.setImageBitmap(createBlurBitmap(this.bitmap));
        int i4 = BaseApplication.displayMetrics.heightPixels / 2;
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        int i5 = iArr[1];
        Rect rect = new Rect();
        if (getActivity() != null) {
            getActivity().getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        }
        int i6 = rect.top;
        int i7 = i5 - i6;
        PetkitLog.d("TodoActivity", "y:" + i5 + ",relativeY:" + i7 + ",statusBarHeight:" + i6);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.rlTodoItem.getLayoutParams();
        layoutParams.topMargin = i7;
        this.rlTodoItem.setLayoutParams(layoutParams);
        this.tvContent.setText(toDoBean.getDesc());
        this.tvRemainTime.setText(DateUtil.getTodoRemindDateTime(getContext(), toDoBean.getTime()));
        this.tvNotRemind.setText(R.string.Not_remind);
        boolean z = toDoBean.getErrType() != null && toDoBean.getErrType().equals(Constants.HOME_TODO_CARD_DEVICE_ERR_WARNING);
        boolean z2 = toDoBean.getErrType() != null && toDoBean.getErrType().equals(Constants.HOME_TODO_CARD_DEVICE_ERR_DESICCANT);
        boolean z3 = toDoBean.getErrType() != null && toDoBean.getErrType().equals(Constants.HOME_TODO_CARD_DEVICE_ERR_DEODORANT);
        boolean z4 = toDoBean.getErrType() != null && toDoBean.getErrType().equals(Constants.HOME_TODO_CARD_DEVICE_ERR_PURIFICATION);
        boolean z5 = toDoBean.getErrType() != null && toDoBean.getErrType().equals(Constants.HOME_TODO_CARD_DEVICE_ERR_FILTER);
        if (z || z2 || z3 || z4 || z5) {
            final HashMap map = new HashMap();
            map.put("Enter", "HomeCard");
            this.tvToReset.setVisibility(0);
            this.lineToReset.setVisibility(0);
            this.tvNotRemind3Days.setVisibility(0);
            if (i == 21 || i == 27 || i == 28 || i == 29) {
                this.tvNotRemind3Days.setVisibility(8);
                this.tvNotRemind.setText(R.string.Invent_ignore);
            }
            str2 = "TodoActivity";
            final boolean z6 = z3;
            this.tvToReset.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.home.HomePageFragment$$ExternalSyntheticLambda70
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.f$0.lambda$onCbClick$67(i, j, map, z6, view2);
                }
            });
            this.tvPrompt.setText(R.string.To_reset_prompt);
        } else {
            this.tvToReset.setVisibility(8);
            this.lineToReset.setVisibility(8);
            if (toDoBean.getErrType() != null) {
                i3 = R.string.Deal_with_todo_prompt7;
                String errType = toDoBean.getErrType();
                errType.hashCode();
                switch (errType) {
                    case "deodorant":
                        i3 = R.string.To_reset_prompt;
                        break;
                    case "offline":
                        i3 = R.string.Deal_with_todo_prompt1;
                        break;
                    case "cpoint":
                        i3 = R.string.Deal_with_todo_prompt10;
                        break;
                    case "battery":
                        if (i == 12 || i == 16 || i == 1 || i == 2 || i == 11) {
                            i3 = R.string.Deal_with_todo_prompt6;
                            break;
                        } else {
                            i3 = R.string.Deal_with_todo_prompt5;
                            break;
                        }
                        break;
                    case "upgrade":
                        i3 = R.string.Deal_with_todo_prompt9;
                        break;
                    case "food":
                        i3 = R.string.Deal_with_todo_prompt3;
                        break;
                    case "lack":
                        if (i == 7 || i == 16 || i == 8 || i == 15 || i == 21 || i == 27) {
                            i3 = R.string.Deal_with_todo_prompt8;
                            break;
                        } else {
                            i3 = R.string.Deal_with_todo_prompt4;
                            break;
                        }
                        break;
                    case "empty":
                        i3 = R.string.Deal_with_todo_prompt8;
                        break;
                    case "sandlack":
                        i3 = R.string.Deal_with_todo_prompt2;
                        break;
                }
                this.tvPrompt.setText(i3);
            }
            str2 = "TodoActivity";
        }
        final boolean z7 = z3;
        final boolean z8 = z4;
        final boolean z9 = z5;
        this.tvNotRemind.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.home.HomePageFragment$$ExternalSyntheticLambda71
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.lambda$onCbClick$68(z7, z8, i, j, z9, toDoBean, str, view2);
            }
        });
        this.tvNotRemind3Days.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.home.HomePageFragment$$ExternalSyntheticLambda72
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.lambda$onCbClick$69(toDoBean, i, j, str, view2);
            }
        });
        if (i5 >= i4) {
            this.llControl.measure(View.MeasureSpec.makeMeasureSpec(ArmsUtils.dip2px(getActivity(), 240.0f), 1073741824), View.MeasureSpec.makeMeasureSpec(LockFreeTaskQueueCore.MAX_CAPACITY_MASK, Integer.MIN_VALUE));
            int measuredHeight = this.llControl.getMeasuredHeight();
            int iDip2px = (i7 - ArmsUtils.dip2px(getActivity(), 10.0f)) - measuredHeight;
            PetkitLog.d(str2, "llControl measureHeight:" + measuredHeight + ",marginTop:" + iDip2px);
            RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) this.llControl.getLayoutParams();
            layoutParams2.removeRule(3);
            layoutParams2.topMargin = iDip2px;
            i2 = 0;
            layoutParams2.bottomMargin = 0;
            this.llControl.setLayoutParams(layoutParams2);
        } else {
            i2 = 0;
            RelativeLayout.LayoutParams layoutParams3 = (RelativeLayout.LayoutParams) this.llControl.getLayoutParams();
            layoutParams3.addRule(3, R.id.rl_todo_item);
            layoutParams3.bottomMargin = 0;
            layoutParams3.topMargin = ArmsUtils.dip2px(getActivity(), 10.0f);
            this.llControl.setLayoutParams(layoutParams3);
        }
        this.ivBlurBg.setVisibility(i2);
        this.transparentMaskView.setVisibility(i2);
        this.rlMask.setVisibility(i2);
        HomePageListener homePageListener = this.homePageListener;
        if (homePageListener != null) {
            homePageListener.dismiss(true);
        }
    }

    public /* synthetic */ void lambda$onCbClick$67(int i, final long j, HashMap map, final boolean z, View view) {
        final String str;
        String str2;
        if (i == 14) {
            W5Record w5RecordByDeviceId = W5Utils.getW5RecordByDeviceId(j);
            if (w5RecordByDeviceId != null) {
                if (w5RecordByDeviceId.getTypeCode() == 1) {
                    map.put("DeviceType", "w5");
                    str2 = "W5";
                } else {
                    if (w5RecordByDeviceId.getTypeCode() == 2) {
                        map.put("DeviceType", "w5c");
                    } else if (w5RecordByDeviceId.getTypeCode() == 3) {
                        map.put("DeviceType", "w5c");
                        str2 = "W5N";
                    } else if (w5RecordByDeviceId.getTypeCode() == 4 || w5RecordByDeviceId.getTypeCode() == 6) {
                        map.put("DeviceType", "w4x");
                        str2 = "W4X";
                    } else if (w5RecordByDeviceId.getTypeCode() == 5) {
                        map.put("DeviceType", "ctw2");
                        str2 = "CTW2";
                    }
                    str2 = "W5C";
                }
                map.put("AccessoriesType", Constants.HOME_TODO_CARD_DEVICE_ERR_FILTER);
                ((HomePagePresenter) this.mPresenter).getRelatedProductsInfor(str2, new HomePagePresenter.MallInfoCallBack() { // from class: com.petkit.android.activities.home.HomePageFragment$$ExternalSyntheticLambda12
                    @Override // com.petkit.android.activities.home.presenter.HomePagePresenter.MallInfoCallBack
                    public final void onResult(RelatedProductsInfor relatedProductsInfor) {
                        this.f$0.lambda$onCbClick$53(j, relatedProductsInfor);
                    }
                });
                return;
            }
            return;
        }
        if (i == 24) {
            CTW3Record cTW3RecordByDeviceId = CTW3Utils.getCTW3RecordByDeviceId(j);
            if (cTW3RecordByDeviceId != null) {
                int typeCode = cTW3RecordByDeviceId.getTypeCode();
                if (typeCode == 2) {
                    str = "CTW3-100";
                } else if (typeCode == 3) {
                    str = "CTW3-uv";
                } else if (typeCode == 4) {
                    str = "CTW3-100-uv";
                } else {
                    str = "CTW3";
                }
                ((HomePagePresenter) this.mPresenter).getRelatedProductsInfor(str, new HomePagePresenter.MallInfoCallBack() { // from class: com.petkit.android.activities.home.HomePageFragment$$ExternalSyntheticLambda17
                    @Override // com.petkit.android.activities.home.presenter.HomePagePresenter.MallInfoCallBack
                    public final void onResult(RelatedProductsInfor relatedProductsInfor) {
                        this.f$0.lambda$onCbClick$54(str, j, relatedProductsInfor);
                    }
                });
                return;
            }
            return;
        }
        if (i == 11) {
            D4Record d4RecordByDeviceId = D4Utils.getD4RecordByDeviceId(j);
            map.put("DeviceType", "d4");
            map.put("AccessoriesType", Constants.HOME_TODO_CARD_DEVICE_ERR_DESICCANT);
            if (d4RecordByDeviceId != null) {
                ((HomePagePresenter) this.mPresenter).getRelatedProductsInfor("D4", new HomePagePresenter.MallInfoCallBack() { // from class: com.petkit.android.activities.home.HomePageFragment$$ExternalSyntheticLambda18
                    @Override // com.petkit.android.activities.home.presenter.HomePagePresenter.MallInfoCallBack
                    public final void onResult(RelatedProductsInfor relatedProductsInfor) {
                        this.f$0.lambda$onCbClick$55(j, relatedProductsInfor);
                    }
                });
                return;
            }
            return;
        }
        if (i == 20) {
            D4sRecord d4sRecordByDeviceId = D4sUtils.getD4sRecordByDeviceId(j);
            map.put("DeviceType", "d4s");
            map.put("AccessoriesType", Constants.HOME_TODO_CARD_DEVICE_ERR_DESICCANT);
            if (d4sRecordByDeviceId != null) {
                ((HomePagePresenter) this.mPresenter).getRelatedProductsInfor("D4S", new HomePagePresenter.MallInfoCallBack() { // from class: com.petkit.android.activities.home.HomePageFragment$$ExternalSyntheticLambda19
                    @Override // com.petkit.android.activities.home.presenter.HomePagePresenter.MallInfoCallBack
                    public final void onResult(RelatedProductsInfor relatedProductsInfor) {
                        this.f$0.lambda$onCbClick$56(j, relatedProductsInfor);
                    }
                });
                return;
            }
            return;
        }
        if (i == 9) {
            D3Record d3RecordByDeviceId = D3Utils.getD3RecordByDeviceId(j);
            map.put("DeviceType", "d3");
            map.put("AccessoriesType", Constants.HOME_TODO_CARD_DEVICE_ERR_DESICCANT);
            if (d3RecordByDeviceId != null) {
                ((HomePagePresenter) this.mPresenter).getRelatedProductsInfor("D3", new HomePagePresenter.MallInfoCallBack() { // from class: com.petkit.android.activities.home.HomePageFragment$$ExternalSyntheticLambda20
                    @Override // com.petkit.android.activities.home.presenter.HomePagePresenter.MallInfoCallBack
                    public final void onResult(RelatedProductsInfor relatedProductsInfor) {
                        this.f$0.lambda$onCbClick$57(j, relatedProductsInfor);
                    }
                });
                return;
            }
            return;
        }
        if (i == 6) {
            D2Record d2RecordByDeviceId = D2Utils.getD2RecordByDeviceId(j);
            map.put("DeviceType", "d2");
            map.put("AccessoriesType", Constants.HOME_TODO_CARD_DEVICE_ERR_DESICCANT);
            if (d2RecordByDeviceId != null) {
                ((HomePagePresenter) this.mPresenter).getRelatedProductsInfor("D2", new HomePagePresenter.MallInfoCallBack() { // from class: com.petkit.android.activities.home.HomePageFragment$$ExternalSyntheticLambda21
                    @Override // com.petkit.android.activities.home.presenter.HomePagePresenter.MallInfoCallBack
                    public final void onResult(RelatedProductsInfor relatedProductsInfor) {
                        this.f$0.lambda$onCbClick$58(j, relatedProductsInfor);
                    }
                });
                return;
            }
            return;
        }
        if (i == 4) {
            FeederRecord feederRecordByDeviceId = FeederUtils.getFeederRecordByDeviceId(j);
            map.put("DeviceType", "d1");
            map.put("AccessoriesType", Constants.HOME_TODO_CARD_DEVICE_ERR_DESICCANT);
            if (feederRecordByDeviceId != null) {
                ((HomePagePresenter) this.mPresenter).getRelatedProductsInfor("D1", new HomePagePresenter.MallInfoCallBack() { // from class: com.petkit.android.activities.home.HomePageFragment$$ExternalSyntheticLambda22
                    @Override // com.petkit.android.activities.home.presenter.HomePagePresenter.MallInfoCallBack
                    public final void onResult(RelatedProductsInfor relatedProductsInfor) {
                        this.f$0.lambda$onCbClick$59(j, relatedProductsInfor);
                    }
                });
                return;
            }
            return;
        }
        if (i == 15) {
            T4Record t4RecordByDeviceId = T4Utils.getT4RecordByDeviceId(j);
            map.put("DeviceType", "t4");
            map.put("AccessoriesType", "N50");
            if (t4RecordByDeviceId != null) {
                ((HomePagePresenter) this.mPresenter).getRelatedProductsInfor("T4", new HomePagePresenter.MallInfoCallBack() { // from class: com.petkit.android.activities.home.HomePageFragment$$ExternalSyntheticLambda23
                    @Override // com.petkit.android.activities.home.presenter.HomePagePresenter.MallInfoCallBack
                    public final void onResult(RelatedProductsInfor relatedProductsInfor) {
                        this.f$0.lambda$onCbClick$60(j, relatedProductsInfor);
                    }
                });
                return;
            }
            return;
        }
        if (i == 25) {
            D4shRecord d4shRecordByDeviceId = D4shUtils.getD4shRecordByDeviceId(j, 0);
            map.put("DeviceType", "d4sh");
            map.put("AccessoriesType", Constants.HOME_TODO_CARD_DEVICE_ERR_DESICCANT);
            if (d4shRecordByDeviceId != null) {
                ((HomePagePresenter) this.mPresenter).getRelatedProductsInfor("D4SH", new HomePagePresenter.MallInfoCallBack() { // from class: com.petkit.android.activities.home.HomePageFragment$$ExternalSyntheticLambda24
                    @Override // com.petkit.android.activities.home.presenter.HomePagePresenter.MallInfoCallBack
                    public final void onResult(RelatedProductsInfor relatedProductsInfor) {
                        this.f$0.lambda$onCbClick$61(j, relatedProductsInfor);
                    }
                });
                return;
            }
            return;
        }
        if (i == 26) {
            D4shRecord d4shRecordByDeviceId2 = D4shUtils.getD4shRecordByDeviceId(j, 1);
            map.put("DeviceType", "d4h");
            map.put("AccessoriesType", Constants.HOME_TODO_CARD_DEVICE_ERR_DESICCANT);
            if (d4shRecordByDeviceId2 != null) {
                ((HomePagePresenter) this.mPresenter).getRelatedProductsInfor("D4H", new HomePagePresenter.MallInfoCallBack() { // from class: com.petkit.android.activities.home.HomePageFragment$$ExternalSyntheticLambda25
                    @Override // com.petkit.android.activities.home.presenter.HomePagePresenter.MallInfoCallBack
                    public final void onResult(RelatedProductsInfor relatedProductsInfor) {
                        this.f$0.lambda$onCbClick$62(j, relatedProductsInfor);
                    }
                });
                return;
            }
            return;
        }
        if (i == 21) {
            T6Record t6RecordByDeviceId = T6Utils.getT6RecordByDeviceId(j, 1);
            map.put("DeviceType", "t5");
            map.put("AccessoriesType", "purifyingLiquid");
            if (t6RecordByDeviceId != null) {
                ((HomePagePresenter) this.mPresenter).getRelatedProductsInfor("T5", new HomePagePresenter.MallInfoCallBack() { // from class: com.petkit.android.activities.home.HomePageFragment$$ExternalSyntheticLambda13
                    @Override // com.petkit.android.activities.home.presenter.HomePagePresenter.MallInfoCallBack
                    public final void onResult(RelatedProductsInfor relatedProductsInfor) {
                        this.f$0.lambda$onCbClick$63(j, z, relatedProductsInfor);
                    }
                });
                return;
            }
            return;
        }
        if (i == 27) {
            T6Record t6RecordByDeviceId2 = T6Utils.getT6RecordByDeviceId(j, 0);
            map.put("DeviceType", "t6");
            map.put("AccessoriesType", Constants.HOME_TODO_CARD_DEVICE_ERR_PURIFICATION);
            if (t6RecordByDeviceId2 != null) {
                ((HomePagePresenter) this.mPresenter).getRelatedProductsInfor("T6", new HomePagePresenter.MallInfoCallBack() { // from class: com.petkit.android.activities.home.HomePageFragment$$ExternalSyntheticLambda14
                    @Override // com.petkit.android.activities.home.presenter.HomePagePresenter.MallInfoCallBack
                    public final void onResult(RelatedProductsInfor relatedProductsInfor) {
                        this.f$0.lambda$onCbClick$64(j, relatedProductsInfor);
                    }
                });
                return;
            }
            return;
        }
        if (i == 28) {
            if (T7DataUtils.getInstance().getT7RecordById(j) != null) {
                ((HomePagePresenter) this.mPresenter).getRelatedProductsInfor("T7", new HomePagePresenter.MallInfoCallBack() { // from class: com.petkit.android.activities.home.HomePageFragment$$ExternalSyntheticLambda15
                    @Override // com.petkit.android.activities.home.presenter.HomePagePresenter.MallInfoCallBack
                    public final void onResult(RelatedProductsInfor relatedProductsInfor) {
                        this.f$0.lambda$onCbClick$65(j, relatedProductsInfor);
                    }
                });
            }
        } else {
            if (i != 29 || W7hDataUtils.getInstance().getW7hRecordByDeviceId(j) == null) {
                return;
            }
            ((HomePagePresenter) this.mPresenter).getRelatedProductsInfor("W7H", new HomePagePresenter.MallInfoCallBack() { // from class: com.petkit.android.activities.home.HomePageFragment$$ExternalSyntheticLambda16
                @Override // com.petkit.android.activities.home.presenter.HomePagePresenter.MallInfoCallBack
                public final void onResult(RelatedProductsInfor relatedProductsInfor) {
                    this.f$0.lambda$onCbClick$66(j, relatedProductsInfor);
                }
            });
        }
    }

    public /* synthetic */ void lambda$onCbClick$53(long j, RelatedProductsInfor relatedProductsInfor) {
        launchActivity(W5FilterRemainingActivity.newIntent(getActivity(), j, relatedProductsInfor != null ? relatedProductsInfor.getStandard().getW5() : null));
    }

    public /* synthetic */ void lambda$onCbClick$54(String str, long j, RelatedProductsInfor relatedProductsInfor) {
        CTW3ProductsInfor cTW3_100_uv;
        str.hashCode();
        switch (str) {
            case "CTW3-100-uv":
                cTW3_100_uv = relatedProductsInfor.getStandard().getCTW3_100_uv();
                break;
            case "CTW3-100":
                cTW3_100_uv = relatedProductsInfor.getStandard().getCTW3_100();
                break;
            case "CTW3-uv":
                cTW3_100_uv = relatedProductsInfor.getStandard().getCTW3_uv();
                break;
            default:
                cTW3_100_uv = relatedProductsInfor.getStandard().getCTW3();
                break;
        }
        launchActivity(CTW3FilterRemainingActivity.newIntent(getActivity(), j, cTW3_100_uv));
    }

    public /* synthetic */ void lambda$onCbClick$55(long j, RelatedProductsInfor relatedProductsInfor) {
        launchActivity(FeederConsumablesActivity.newIntent(getActivity(), j, relatedProductsInfor, 11));
    }

    public /* synthetic */ void lambda$onCbClick$56(long j, RelatedProductsInfor relatedProductsInfor) {
        launchActivity(FeederConsumablesActivity.newIntent(getActivity(), j, relatedProductsInfor, 20));
    }

    public /* synthetic */ void lambda$onCbClick$57(long j, RelatedProductsInfor relatedProductsInfor) {
        launchActivity(FeederConsumablesActivity.newIntent(getActivity(), j, relatedProductsInfor, 9));
    }

    public /* synthetic */ void lambda$onCbClick$58(long j, RelatedProductsInfor relatedProductsInfor) {
        launchActivity(FeederConsumablesActivity.newIntent(getActivity(), j, relatedProductsInfor, 6));
    }

    public /* synthetic */ void lambda$onCbClick$59(long j, RelatedProductsInfor relatedProductsInfor) {
        launchActivity(FeederConsumablesActivity.newIntent(getActivity(), j, relatedProductsInfor, 4));
    }

    public /* synthetic */ void lambda$onCbClick$60(long j, RelatedProductsInfor relatedProductsInfor) {
        launchActivity(T4ConsumablesActivity.newIntent(getActivity(), j, 4, relatedProductsInfor));
    }

    public /* synthetic */ void lambda$onCbClick$61(long j, RelatedProductsInfor relatedProductsInfor) {
        launchActivity(FeederConsumablesActivity.newIntent(getActivity(), j, relatedProductsInfor, 25));
    }

    public /* synthetic */ void lambda$onCbClick$62(long j, RelatedProductsInfor relatedProductsInfor) {
        launchActivity(FeederConsumablesActivity.newIntent(getActivity(), j, relatedProductsInfor, 26));
    }

    public /* synthetic */ void lambda$onCbClick$63(long j, boolean z, RelatedProductsInfor relatedProductsInfor) {
        launchActivity(T6ConsumablesActivity.newIntent(getActivity(), j, 21, z ? 5 : 6, relatedProductsInfor));
    }

    public /* synthetic */ void lambda$onCbClick$64(long j, RelatedProductsInfor relatedProductsInfor) {
        launchActivity(T6ConsumablesActivity.newIntent(getActivity(), j, 27, 4, relatedProductsInfor));
    }

    public /* synthetic */ void lambda$onCbClick$65(long j, RelatedProductsInfor relatedProductsInfor) {
        T7DataUtils.getInstance().saveRelativeProductInfo(relatedProductsInfor);
        launchActivity(T7ConsumableActivity.newIntent(getActivity(), j, 2));
    }

    public /* synthetic */ void lambda$onCbClick$66(long j, RelatedProductsInfor relatedProductsInfor) {
        W7hDataUtils.getInstance().saveRelativeProductInfo(relatedProductsInfor);
        launchActivity(W7hConsumablesActivity.newIntent(getActivity(), j, 3));
    }

    public /* synthetic */ void lambda$onCbClick$68(boolean z, boolean z2, int i, long j, boolean z3, ToDoBean toDoBean, String str, View view) {
        if ((!z && !z2) || (i != 21 && i != 27 && i != 28)) {
            if (z3 && i == 29) {
                ((HomePagePresenter) this.mPresenter).ignoreRemind(toDoBean, i, j, 30, str);
                return;
            } else {
                ((HomePagePresenter) this.mPresenter).ignoreRemind(toDoBean, i, j, 30, str);
                return;
            }
        }
        if (z && i == 21) {
            ((HomePagePresenter) this.mPresenter).ignoreT5N50State(j);
            return;
        }
        if (z2 && i == 21) {
            ((HomePagePresenter) this.mPresenter).ignoreT5N60State(j);
            return;
        }
        if (z2 && i == 28) {
            ((HomePagePresenter) this.mPresenter).ignoreT7N60State(j);
        } else if (z2) {
            ((HomePagePresenter) this.mPresenter).ignoreT6N60State(j);
        }
    }

    public /* synthetic */ void lambda$onCbClick$69(ToDoBean toDoBean, int i, long j, String str, View view) {
        ((HomePagePresenter) this.mPresenter).ignoreRemind(toDoBean, i, j, 3, str);
    }

    private Bitmap createBlurBitmap(Bitmap bitmap) {
        return BlurUtils.blur(getActivity(), bitmap, 10, 0.125f);
    }

    @Override // com.petkit.android.activities.home.contract.HomePageContract.View
    public void hideMaskView() {
        if (this.rlMask.getVisibility() == 0) {
            this.rlMask.setVisibility(8);
            this.ivBlurBg.setVisibility(8);
            this.transparentMaskView.setVisibility(8);
            HomePageListener homePageListener = this.homePageListener;
            if (homePageListener != null) {
                homePageListener.dismiss(false);
            }
        }
    }

    @Override // com.petkit.android.activities.home.contract.HomePageContract.View
    public void deleteSuccess() {
        HomeCardDataUtils.getInstance().setIsNeedRefreshDeviceRoaster(getContext(), FamilyUtils.getInstance().getCurrentFamilyId(getContext()), true);
        ((HomePagePresenter) this.mPresenter).getHomeDeviceList();
    }

    @Override // com.petkit.android.activities.home.contract.HomePageContract.View
    public void autoRefresh() {
        MyRefreshView myRefreshView = this.srl;
        if (myRefreshView != null) {
            if (!this.isTop) {
                scrollTop();
                this.isAutoRefresh = true;
            } else {
                myRefreshView.autoRefresh();
            }
        }
    }

    @Override // com.petkit.android.activities.home.contract.HomePageContract.View
    public void getWallPager(WallPagerInfo wallPagerInfo) {
        FamilyUtils.getInstance().saveFamilyWallPager(getActivity(), wallPagerInfo.getDefaultPic());
        FamilyUtils.getInstance().saveAllFamilyWallPager(getActivity(), wallPagerInfo.getFamilyPic());
    }

    @Override // com.petkit.android.activities.home.contract.HomePageContract.View
    public void updateRemindRedPoint(UnReadStatusRsp unReadStatusRsp) {
        if (unReadStatusRsp != null && unReadStatusRsp.getResult() != null) {
            unReadStatusRsp.getResult().getSchedule();
        }
        HomePageListener homePageListener = this.homePageListener;
        if (homePageListener != null) {
            homePageListener.redPointDismiss();
        }
    }

    @Override // com.petkit.android.activities.home.contract.HomePageContract.View
    public void selectFamily(boolean z) {
        this.showFamilyName = true;
    }

    public void setHomePageListener(HomePageListener homePageListener) {
        this.homePageListener = homePageListener;
    }

    public void openModeWindow(String str) {
        if (getActivity() == null) {
            return;
        }
        new NewIKnowWindow(getActivity(), (String) null, new SpannableString(str), (String) null, getResources().getString(R.string.Okay)).show(getActivity().getWindow().getDecorView());
    }

    private void getLocation(View view, HomeDeviceData homeDeviceData, boolean z) {
        if (!FamilyUtils.getInstance().checkIsSharedDevice(getActivity(), homeDeviceData.getId(), CommonUtils.getDeviceTypeByString(homeDeviceData.getType())) || CommonUtil.isHaveQuichCardActions(homeDeviceData.getType()) || homeDeviceData.getData().getId() == -1) {
            int[] iArr = new int[2];
            view.getLocationInWindow(iArr);
            showDeleteGuide(view, iArr[1] > this.halfHeight, homeDeviceData, z);
        }
    }

    private void showDeleteGuide(View view, boolean z, HomeDeviceData homeDeviceData, boolean z2) {
        GuideBuilder guideBuilder = new GuideBuilder();
        guideBuilder.setTargetView(view).setAlpha(180).setHighTargetCorner(30).setHighTargetPaddingTop(ArmsUtils.dip2px(getActivity(), 5.0f)).setHighTargetPaddingBottom(ArmsUtils.dip2px(getActivity(), -20.0f)).setOutsideTouchable(false).setAutoDismiss(true);
        AnonymousClass23 anonymousClass23 = new HomeQuickActionListener() { // from class: com.petkit.android.activities.home.HomePageFragment.23
            public final /* synthetic */ HomeDeviceData val$data;

            public AnonymousClass23(HomeDeviceData homeDeviceData2) {
                homeDeviceData = homeDeviceData2;
            }

            @Override // com.petkit.android.activities.petkitBleDevice.w5.guide.HomeQuickActionListener
            public void onConfirmListener() {
                if (homeDeviceData.getId() == -1) {
                    HomePageFragment.this.showCloseVirtualWindow(homeDeviceData);
                } else {
                    HomePageFragment.this.showDeviceCloseWindow(homeDeviceData.getId(), homeDeviceData.getType(), homeDeviceData.getData().getPetId());
                }
                if (HomePageFragment.this.deleteGuide != null) {
                    HomePageFragment.this.deleteGuide.dismiss();
                }
            }

            @Override // com.petkit.android.activities.petkitBleDevice.w5.guide.HomeQuickActionListener
            public void onEditCardListener() {
                if (HomePageFragment.this.deleteGuide != null) {
                    HomePageFragment.this.deleteGuide.dismiss();
                }
                String type = homeDeviceData.getType();
                type.hashCode();
                switch (type) {
                    case "D3":
                        HomePageFragment.this.launchActivity(homeDeviceData, 9);
                        break;
                    case "D4":
                        HomePageFragment.this.launchActivity(homeDeviceData, 11);
                        break;
                    case "T3":
                        HomePageFragment.this.launchActivity(homeDeviceData, 7);
                        break;
                    case "T4":
                        T4Record t4RecordByDeviceId = T4Utils.getT4RecordByDeviceId(homeDeviceData.getId());
                        if (t4RecordByDeviceId != null && t4RecordByDeviceId.getK3Id() > 0) {
                            HomePageFragment homePageFragment = HomePageFragment.this;
                            homePageFragment.launchActivity(CardShortcutsActivity.newIntent(homePageFragment.getActivity(), homeDeviceData.getId(), 15, homeDeviceData.getData().getControlSettings(), true));
                            break;
                        } else {
                            HomePageFragment homePageFragment2 = HomePageFragment.this;
                            homePageFragment2.launchActivity(CardShortcutsActivity.newIntent(homePageFragment2.getActivity(), homeDeviceData.getId(), 15, homeDeviceData.getData().getControlSettings(), false));
                            break;
                        }
                        break;
                    case "T5":
                        HomePageFragment.this.launchActivity(homeDeviceData, 21);
                        break;
                    case "T6":
                        HomePageFragment.this.launchActivity(homeDeviceData, 27);
                        break;
                    case "T7":
                        HomePageFragment.this.launchActivity(homeDeviceData, 28);
                        break;
                    case "D4H":
                    case "D4h":
                        HomePageFragment.this.launchActivity(homeDeviceData, 26);
                        break;
                    case "D4s":
                        HomePageFragment.this.launchActivity(homeDeviceData, 20);
                        break;
                    case "W7H":
                    case "W7h":
                        HomePageFragment.this.launchActivity(homeDeviceData, 29);
                        break;
                    case "D4SH":
                    case "D4sh":
                        HomePageFragment.this.launchActivity(homeDeviceData, 25);
                        break;
                }
            }
        };
        if (FamilyUtils.getInstance().checkIsSharedDevice(getActivity(), homeDeviceData2.getId(), CommonUtils.getDeviceTypeByString(homeDeviceData2.getType())) && CommonUtil.isHaveQuichCardActions(homeDeviceData2.getType()) && homeDeviceData2.getId() != -1) {
            if (z2) {
                if (homeDeviceData2.getData().getDeviceShared() != null) {
                    guideBuilder.addComponent(new DeleteItemComponent(getActivity(), new GuideParam("", "", z ? 2 : 4, 16, 0, z ? -10 : 10, "", R.layout.layout_home_guide_delete), anonymousClass23, true, true));
                } else {
                    guideBuilder.addComponent(new DeleteItemComponent(getActivity(), new GuideParam("", "", z ? 2 : 4, 16, 0, z ? -10 : 10, "", R.layout.layout_home_guide_delete), anonymousClass23, true, false));
                }
            } else if (homeDeviceData2.getData().getDeviceShared() != null) {
                guideBuilder.addComponent(new DeleteItemComponent(getActivity(), new GuideParam("", "", z ? 2 : 4, 16, 0, z ? -10 : 10, "", R.layout.layout_home_guide_delete), anonymousClass23, false, true));
            }
            Guide guideCreateGuide = guideBuilder.createGuide();
            this.deleteGuide = guideCreateGuide;
            guideCreateGuide.show(getActivity());
            return;
        }
        if (!FamilyUtils.getInstance().checkIsSharedDevice(getActivity(), homeDeviceData2.getId(), CommonUtils.getDeviceTypeByString(homeDeviceData2.getType())) && CommonUtil.isHaveQuichCardActions(homeDeviceData2.getType()) && homeDeviceData2.getId() != -1) {
            if (z2) {
                guideBuilder.addComponent(new DeleteItemComponent(getActivity(), new GuideParam("", "", z ? 2 : 4, 16, 0, z ? -10 : 10, "", R.layout.layout_home_guide_delete), anonymousClass23, true, true));
            } else {
                guideBuilder.addComponent(new DeleteItemComponent(getActivity(), new GuideParam("", "", z ? 2 : 4, 16, 0, z ? -10 : 10, "", R.layout.layout_home_guide_delete), anonymousClass23, false, true));
            }
            Guide guideCreateGuide2 = guideBuilder.createGuide();
            this.deleteGuide = guideCreateGuide2;
            guideCreateGuide2.show(getActivity());
            return;
        }
        if (!FamilyUtils.getInstance().checkIsSharedDevice(getActivity(), homeDeviceData2.getId(), CommonUtils.getDeviceTypeByString(homeDeviceData2.getType())) && !CommonUtil.isHaveQuichCardActions(homeDeviceData2.getType())) {
            guideBuilder.addComponent(new DeleteItemComponent(getActivity(), new GuideParam("", "", z ? 2 : 4, 16, 0, z ? -10 : 10, "", R.layout.layout_home_guide_delete), anonymousClass23, false, true));
            Guide guideCreateGuide3 = guideBuilder.createGuide();
            this.deleteGuide = guideCreateGuide3;
            guideCreateGuide3.show(getActivity());
            return;
        }
        if (homeDeviceData2.getId() == -1) {
            guideBuilder.addComponent(new DeleteItemComponent(getActivity(), new GuideParam("", "", z ? 2 : 4, 16, 0, z ? -10 : 10, "", R.layout.layout_home_guide_delete), anonymousClass23, false, true));
            Guide guideCreateGuide4 = guideBuilder.createGuide();
            this.deleteGuide = guideCreateGuide4;
            guideCreateGuide4.show(getActivity());
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.home.HomePageFragment$23 */
    public class AnonymousClass23 implements HomeQuickActionListener {
        public final /* synthetic */ HomeDeviceData val$data;

        public AnonymousClass23(HomeDeviceData homeDeviceData2) {
            homeDeviceData = homeDeviceData2;
        }

        @Override // com.petkit.android.activities.petkitBleDevice.w5.guide.HomeQuickActionListener
        public void onConfirmListener() {
            if (homeDeviceData.getId() == -1) {
                HomePageFragment.this.showCloseVirtualWindow(homeDeviceData);
            } else {
                HomePageFragment.this.showDeviceCloseWindow(homeDeviceData.getId(), homeDeviceData.getType(), homeDeviceData.getData().getPetId());
            }
            if (HomePageFragment.this.deleteGuide != null) {
                HomePageFragment.this.deleteGuide.dismiss();
            }
        }

        @Override // com.petkit.android.activities.petkitBleDevice.w5.guide.HomeQuickActionListener
        public void onEditCardListener() {
            if (HomePageFragment.this.deleteGuide != null) {
                HomePageFragment.this.deleteGuide.dismiss();
            }
            String type = homeDeviceData.getType();
            type.hashCode();
            switch (type) {
                case "D3":
                    HomePageFragment.this.launchActivity(homeDeviceData, 9);
                    break;
                case "D4":
                    HomePageFragment.this.launchActivity(homeDeviceData, 11);
                    break;
                case "T3":
                    HomePageFragment.this.launchActivity(homeDeviceData, 7);
                    break;
                case "T4":
                    T4Record t4RecordByDeviceId = T4Utils.getT4RecordByDeviceId(homeDeviceData.getId());
                    if (t4RecordByDeviceId != null && t4RecordByDeviceId.getK3Id() > 0) {
                        HomePageFragment homePageFragment = HomePageFragment.this;
                        homePageFragment.launchActivity(CardShortcutsActivity.newIntent(homePageFragment.getActivity(), homeDeviceData.getId(), 15, homeDeviceData.getData().getControlSettings(), true));
                        break;
                    } else {
                        HomePageFragment homePageFragment2 = HomePageFragment.this;
                        homePageFragment2.launchActivity(CardShortcutsActivity.newIntent(homePageFragment2.getActivity(), homeDeviceData.getId(), 15, homeDeviceData.getData().getControlSettings(), false));
                        break;
                    }
                    break;
                case "T5":
                    HomePageFragment.this.launchActivity(homeDeviceData, 21);
                    break;
                case "T6":
                    HomePageFragment.this.launchActivity(homeDeviceData, 27);
                    break;
                case "T7":
                    HomePageFragment.this.launchActivity(homeDeviceData, 28);
                    break;
                case "D4H":
                case "D4h":
                    HomePageFragment.this.launchActivity(homeDeviceData, 26);
                    break;
                case "D4s":
                    HomePageFragment.this.launchActivity(homeDeviceData, 20);
                    break;
                case "W7H":
                case "W7h":
                    HomePageFragment.this.launchActivity(homeDeviceData, 29);
                    break;
                case "D4SH":
                case "D4sh":
                    HomePageFragment.this.launchActivity(homeDeviceData, 25);
                    break;
            }
        }
    }

    public void openBatteryModeWindow() {
        new NewIKnowWindow(getActivity(), (String) null, new SpannableString(getResources().getString(R.string.Battery_mode_is_being_used)), (String) null).show(requireActivity().getWindow().getDecorView());
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.home.HomePageFragment$24 */
    public class AnonymousClass24 implements NormalCenterTipWindow.OnClickOk {
        public final /* synthetic */ long val$deviceId;
        public final /* synthetic */ String val$key;
        public final /* synthetic */ int val$value;

        @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
        public void onCancelClick() {
        }

        public AnonymousClass24(String str, long j, int i) {
            str = str;
            j = j;
            i = i;
        }

        @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
        public void onOkClick() {
            if (str.equals("camera")) {
                ((HomePagePresenter) ((BaseFragment) HomePageFragment.this).mPresenter).updateW7hDeviceSettings(j, str, i);
            } else if (str.equals("start")) {
                ((HomePagePresenter) ((BaseFragment) HomePageFragment.this).mPresenter).controlAddWater(j);
            }
        }
    }

    public void w7hOpenCloseCameraWindow(long j, String str, String str2, int i) {
        NormalCenterTipWindow normalCenterTipWindow = new NormalCenterTipWindow(getActivity(), new NormalCenterTipWindow.OnClickOk() { // from class: com.petkit.android.activities.home.HomePageFragment.24
            public final /* synthetic */ long val$deviceId;
            public final /* synthetic */ String val$key;
            public final /* synthetic */ int val$value;

            @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
            public void onCancelClick() {
            }

            public AnonymousClass24(String str22, long j2, int i2) {
                str = str22;
                j = j2;
                i = i2;
            }

            @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
            public void onOkClick() {
                if (str.equals("camera")) {
                    ((HomePagePresenter) ((BaseFragment) HomePageFragment.this).mPresenter).updateW7hDeviceSettings(j, str, i);
                } else if (str.equals("start")) {
                    ((HomePagePresenter) ((BaseFragment) HomePageFragment.this).mPresenter).controlAddWater(j);
                }
            }
        }, (String) null, new SpannableString(str));
        ((TextView) normalCenterTipWindow.getContentView().findViewById(R.id.tv_cancel)).setText(getString(R.string.Cancel));
        ((TextView) normalCenterTipWindow.getContentView().findViewById(R.id.tv_cancel)).setTextColor(getResources().getColor(R.color.gray));
        ((TextView) normalCenterTipWindow.getContentView().findViewById(R.id.tv_ok)).setText(getString(R.string.Confirm));
        ((TextView) normalCenterTipWindow.getContentView().findViewById(R.id.tv_ok)).setTextColor(getResources().getColor(R.color.new_bind_blue));
        normalCenterTipWindow.show(requireActivity().getWindow().getDecorView());
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.home.HomePageFragment$25 */
    public class AnonymousClass25 implements NormalCenterTipWindow.OnClickOk {
        public final /* synthetic */ long val$deviceId;

        @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
        public void onCancelClick() {
        }

        public AnonymousClass25(long j) {
            j = j;
        }

        @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
        public void onOkClick() {
            ((HomePagePresenter) ((BaseFragment) HomePageFragment.this).mPresenter).w7hTemporaryOpenCameraAW(j);
        }
    }

    public void w7hOpenCameraAndRefillWindow(long j) {
        NormalCenterTipWindow normalCenterTipWindow = new NormalCenterTipWindow(getActivity(), new NormalCenterTipWindow.OnClickOk() { // from class: com.petkit.android.activities.home.HomePageFragment.25
            public final /* synthetic */ long val$deviceId;

            @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
            public void onCancelClick() {
            }

            public AnonymousClass25(long j2) {
                j = j2;
            }

            @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
            public void onOkClick() {
                ((HomePagePresenter) ((BaseFragment) HomePageFragment.this).mPresenter).w7hTemporaryOpenCameraAW(j);
            }
        }, (String) null, new SpannableString(getString(R.string.Temp_refill_remind)));
        ((TextView) normalCenterTipWindow.getContentView().findViewById(R.id.tv_cancel)).setText(getString(R.string.Skip_adding));
        ((TextView) normalCenterTipWindow.getContentView().findViewById(R.id.tv_cancel)).setTextColor(getResources().getColor(R.color.new_bind_blue));
        ((TextView) normalCenterTipWindow.getContentView().findViewById(R.id.tv_ok)).setText(getString(R.string.Enable_Camera_Add_Water));
        ((TextView) normalCenterTipWindow.getContentView().findViewById(R.id.tv_ok)).setTextColor(getResources().getColor(R.color.new_bind_blue));
        normalCenterTipWindow.show(requireActivity().getWindow().getDecorView());
    }

    @Override // com.petkit.android.activities.home.contract.HomePageContract.View
    public void controlAddWater(long j) {
        ((HomePagePresenter) this.mPresenter).controlCameraAddWater(j);
    }

    @Override // com.petkit.android.activities.home.contract.HomePageContract.View
    public void showAddWaterTip() {
        new SimpleTipWindow(requireActivity(), getString(R.string.Prompt), getString(R.string.After_water_refilling_complete), false).showAtLocation(requireActivity().getWindow().getDecorView(), 17, 0, 0);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.home.HomePageFragment$26 */
    public class AnonymousClass26 implements NormalCenterTipWindow.OnClickOk {
        public final /* synthetic */ HomeDeviceData val$data;
        public final /* synthetic */ int val$deviceType;
        public final /* synthetic */ String val$key;
        public final /* synthetic */ boolean val$value;

        @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
        public void onCancelClick() {
        }

        public AnonymousClass26(int i, HomeDeviceData homeDeviceData, String str, boolean z) {
            i = i;
            homeDeviceData = homeDeviceData;
            str = str;
            z = z;
        }

        @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
        public void onOkClick() {
            if (i == 28) {
                ((HomePagePresenter) ((BaseFragment) HomePageFragment.this).mPresenter).updateT7DeviceSettings(homeDeviceData.getId(), str, z ? 1 : 0);
            } else {
                ((HomePagePresenter) ((BaseFragment) HomePageFragment.this).mPresenter).updateT6DeviceSettings(homeDeviceData, str, z, i);
            }
        }
    }

    public void t6OpenCloseCameraWindow(HomeDeviceData homeDeviceData, String str, String str2, boolean z, int i) {
        NormalCenterTipWindow normalCenterTipWindow = new NormalCenterTipWindow(getActivity(), new NormalCenterTipWindow.OnClickOk() { // from class: com.petkit.android.activities.home.HomePageFragment.26
            public final /* synthetic */ HomeDeviceData val$data;
            public final /* synthetic */ int val$deviceType;
            public final /* synthetic */ String val$key;
            public final /* synthetic */ boolean val$value;

            @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
            public void onCancelClick() {
            }

            public AnonymousClass26(int i2, HomeDeviceData homeDeviceData2, String str22, boolean z2) {
                i = i2;
                homeDeviceData = homeDeviceData2;
                str = str22;
                z = z2;
            }

            @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
            public void onOkClick() {
                if (i == 28) {
                    ((HomePagePresenter) ((BaseFragment) HomePageFragment.this).mPresenter).updateT7DeviceSettings(homeDeviceData.getId(), str, z ? 1 : 0);
                } else {
                    ((HomePagePresenter) ((BaseFragment) HomePageFragment.this).mPresenter).updateT6DeviceSettings(homeDeviceData, str, z, i);
                }
            }
        }, (String) null, new SpannableString(str));
        ((TextView) normalCenterTipWindow.getContentView().findViewById(R.id.tv_cancel)).setText(getString(R.string.Cancel));
        ((TextView) normalCenterTipWindow.getContentView().findViewById(R.id.tv_cancel)).setTextColor(getResources().getColor(R.color.gray));
        ((TextView) normalCenterTipWindow.getContentView().findViewById(R.id.tv_ok)).setText(getString(R.string.Confirm));
        ((TextView) normalCenterTipWindow.getContentView().findViewById(R.id.tv_ok)).setTextColor(getResources().getColor(R.color.new_bind_blue));
        normalCenterTipWindow.show(requireActivity().getWindow().getDecorView());
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.home.HomePageFragment$27 */
    public class AnonymousClass27 implements NormalCenterTipWindow.OnClickOk {
        public final /* synthetic */ HomeDeviceData val$data;
        public final /* synthetic */ String val$key;
        public final /* synthetic */ int val$typeCode;
        public final /* synthetic */ boolean val$value;

        @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
        public void onCancelClick() {
        }

        public AnonymousClass27(HomeDeviceData homeDeviceData, int i, String str, boolean z) {
            homeDeviceData = homeDeviceData;
            i = i;
            str = str;
            z = z;
        }

        @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
        public void onOkClick() {
            ((HomePagePresenter) ((BaseFragment) HomePageFragment.this).mPresenter).updateDeviceSettings(homeDeviceData, i, str, z);
        }
    }

    public void openCloseCameraWindow(HomeDeviceData homeDeviceData, int i, String str, String str2, boolean z) {
        NormalCenterTipWindow normalCenterTipWindow = new NormalCenterTipWindow(getActivity(), new NormalCenterTipWindow.OnClickOk() { // from class: com.petkit.android.activities.home.HomePageFragment.27
            public final /* synthetic */ HomeDeviceData val$data;
            public final /* synthetic */ String val$key;
            public final /* synthetic */ int val$typeCode;
            public final /* synthetic */ boolean val$value;

            @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
            public void onCancelClick() {
            }

            public AnonymousClass27(HomeDeviceData homeDeviceData2, int i2, String str22, boolean z2) {
                homeDeviceData = homeDeviceData2;
                i = i2;
                str = str22;
                z = z2;
            }

            @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
            public void onOkClick() {
                ((HomePagePresenter) ((BaseFragment) HomePageFragment.this).mPresenter).updateDeviceSettings(homeDeviceData, i, str, z);
            }
        }, (String) null, new SpannableString(str));
        ((TextView) normalCenterTipWindow.getContentView().findViewById(R.id.tv_cancel)).setText(getString(R.string.Cancel));
        ((TextView) normalCenterTipWindow.getContentView().findViewById(R.id.tv_cancel)).setTextColor(getResources().getColor(R.color.gray));
        ((TextView) normalCenterTipWindow.getContentView().findViewById(R.id.tv_ok)).setText(getString(R.string.Confirm));
        ((TextView) normalCenterTipWindow.getContentView().findViewById(R.id.tv_ok)).setTextColor(getResources().getColor(R.color.new_bind_blue));
        normalCenterTipWindow.show(requireActivity().getWindow().getDecorView());
    }

    public void show5MinWindowTips() {
        new NewIKnowWindow(getActivity(), "", getResources().getString(R.string.Camera_open_five_minute_tips), "").show(requireActivity().getWindow().getDecorView());
    }

    private void showNotifyOwnerAgreeCameraPrivacyWindow() {
        new NewIKnowWindow(getContext(), (String) null, getString(R.string.User_not_agree_camera_policy_member), (String) null, getString(R.string.I_already_know)).show(requireActivity().getWindow().getDecorView());
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0015  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void showCameraPrivacyWindow(int r12, long r13, int r15) {
        /*
            Method dump skipped, instruction units count: 270
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.petkit.android.activities.home.HomePageFragment.showCameraPrivacyWindow(int, long, int):void");
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.home.HomePageFragment$28 */
    public class AnonymousClass28 extends ClickableSpan {
        public AnonymousClass28() {
        }

        @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
        public void updateDrawState(@NonNull TextPaint textPaint) {
            super.updateDrawState(textPaint);
            textPaint.setColor(HomePageFragment.this.getResources().getColor(R.color.new_bind_blue));
            textPaint.setUnderlineText(false);
        }

        @Override // android.text.style.ClickableSpan
        public void onClick(@NonNull View view) {
            HomePageFragment homePageFragment = HomePageFragment.this;
            homePageFragment.launchActivity(WebviewActivity.newIntent(homePageFragment.getActivity(), HomePageFragment.this.getString(R.string.Privacy_policy), ApiTools.getWebUrlByKey("camera_policy")));
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.home.HomePageFragment$29 */
    public class AnonymousClass29 implements NormalCenterTipWindow.OnClickOk {
        public final /* synthetic */ long val$deviceId;
        public final /* synthetic */ int val$deviceType;
        public final /* synthetic */ int val$versionNo;

        public AnonymousClass29(long j, int i, int i2) {
            j = j;
            i = i;
            i = i2;
        }

        @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
        public void onOkClick() {
            ((HomePagePresenter) ((BaseFragment) HomePageFragment.this).mPresenter).acceptPolicy(j, i, i, 1);
        }

        @Override // com.petkit.android.activities.universalWindow.NormalCenterTipWindow.OnClickOk
        public void onCancelClick() {
            ((HomePagePresenter) ((BaseFragment) HomePageFragment.this).mPresenter).acceptPolicy(j, i, i, 2);
        }
    }

    @Subscriber
    public void onWallPagerChange(WallPagerEvent wallPagerEvent) {
        if (getActivity() == null || wallPagerEvent == null || this.familyId != wallPagerEvent.getFamilyId() || this.familyId <= 0) {
            return;
        }
        if (wallPagerEvent.getType() == 1) {
            changeHomeTheme(wallPagerEvent.getMode() == 2);
            return;
        }
        if (TextUtils.isEmpty(wallPagerEvent.getUrl())) {
            return;
        }
        String url = wallPagerEvent.getUrl();
        if (TextUtils.isEmpty(url) || url.equals(this.homeWallPager)) {
            return;
        }
        new MyGlideTask((Context) getActivity(), new MyGlideTask.GlideSourceListener() { // from class: com.petkit.android.activities.home.HomePageFragment$$ExternalSyntheticLambda40
            @Override // com.petkit.android.utils.MyGlideTask.GlideSourceListener
            public final void onPostExecute(Bitmap bitmap) {
                this.f$0.lambda$onWallPagerChange$70(bitmap);
            }

            @Override // com.petkit.android.utils.MyGlideTask.GlideSourceListener
            public /* synthetic */ void onPreExecute() {
                MyGlideTask.GlideSourceListener.CC.$default$onPreExecute(this);
            }

            @Override // com.petkit.android.utils.MyGlideTask.GlideSourceListener
            public /* synthetic */ void onProgressUpdate(Integer... numArr) {
                MyGlideTask.GlideSourceListener.CC.$default$onProgressUpdate(this, numArr);
            }
        }, true).execute(url);
        this.homeWallPager = url;
    }

    public /* synthetic */ void lambda$onWallPagerChange$70(Bitmap bitmap) {
        if (bitmap == null) {
            this.ivHomeWallPager.setImageResource(R.drawable.img_wall_pager_default);
            changeHomeTheme(false);
            return;
        }
        this.ivHomeWallPager.setImageBitmap(bitmap);
        boolean zWhiteTheme = GrayLevelUtil.whiteTheme(bitmap);
        if (zWhiteTheme || HomeActivity.currentTheme != 1) {
            if (!zWhiteTheme || HomeActivity.currentTheme == 1) {
                changeHomeTheme(zWhiteTheme);
            }
        }
    }

    private void changeHomeTheme(boolean z) {
        int currentFamilyTheme = FamilyUtils.getInstance().getCurrentFamilyTheme(CommonUtils.getAppContext(), this.familyId);
        if (currentFamilyTheme > 0) {
            HomePageListener homePageListener = this.homePageListener;
            if (homePageListener != null) {
                homePageListener.changeTheme(currentFamilyTheme == 2 ? 0 : 1, this.familyId);
                return;
            }
            return;
        }
        HomePageListener homePageListener2 = this.homePageListener;
        if (homePageListener2 != null) {
            homePageListener2.changeTheme(!z ? 1 : 0, this.familyId);
        }
    }

    private ContextThemeWrapper createThemedContext() {
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(requireActivity(), R.style.PetKitHomeStyle);
        Configuration configuration = new Configuration(contextThemeWrapper.getBaseContext().getResources().getConfiguration());
        PetkitLog.d("homeTheme", "当前的主题是：" + HomeActivity.currentTheme);
        if (HomeActivity.currentTheme != 1) {
            configuration.uiMode = (configuration.uiMode & (-49)) | 32;
        } else {
            configuration.uiMode = (configuration.uiMode & (-49)) | 16;
        }
        contextThemeWrapper.applyOverrideConfiguration(configuration);
        return contextThemeWrapper;
    }

    private void showChangeFamilyToast(final String str) {
        if (System.currentTimeMillis() - this.showTime < 500) {
            return;
        }
        this.showTime = System.currentTimeMillis();
        new Handler().postDelayed(new Runnable() { // from class: com.petkit.android.activities.home.HomePageFragment$$ExternalSyntheticLambda33
            @Override // java.lang.Runnable
            public final void run() {
                HomePageFragment.lambda$showChangeFamilyToast$71(str);
            }
        }, 800L);
        this.showFamilyName = false;
    }

    public static /* synthetic */ void lambda$showChangeFamilyToast$71(String str) {
        PetkitToast.showTopToast(CommonUtils.getAppContext(), str, R.drawable.top_toast_success_icon, 0);
    }
}

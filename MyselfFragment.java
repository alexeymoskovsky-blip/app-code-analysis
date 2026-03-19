package com.petkit.android.activities.home;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.google.gson.Gson;
import com.jess.arms.utils.Consts;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.widget.imageloader.glide.GlideCircleTransform;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.orm.SugarRecord;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.base.BaseFragment;
import com.petkit.android.activities.base.fragment.BaseTopicsListFragment;
import com.petkit.android.activities.card.MyCardHomeActivity;
import com.petkit.android.activities.cloudservice.PackagePurchaseActivity;
import com.petkit.android.activities.cloudservice.ServiceManagementActivity;
import com.petkit.android.activities.cloudservice.mode.CloudService;
import com.petkit.android.activities.community.ImageDetailActivity;
import com.petkit.android.activities.cs.CsQiYuEvent;
import com.petkit.android.activities.cs.CsUtils;
import com.petkit.android.activities.device.utils.ServiceUtils;
import com.petkit.android.activities.doctor.ConsultationManagementActivity;
import com.petkit.android.activities.doctor.mode.MedicalConversionList;
import com.petkit.android.activities.doctor.mode.MedicalEvent;
import com.petkit.android.activities.doctor.utils.DoctorUtils;
import com.petkit.android.activities.feed.FeedCalculatorActivity;
import com.petkit.android.activities.go.utils.GoDataUtils;
import com.petkit.android.activities.home.mode.SaleHotLineData;
import com.petkit.android.activities.home.mode.ServiceManageRsp;
import com.petkit.android.activities.mall.utils.ShopifyUtils;
import com.petkit.android.activities.mate.utils.HsConsts;
import com.petkit.android.activities.permission.PermissionDialogActivity;
import com.petkit.android.activities.personal.PersonalActivity;
import com.petkit.android.activities.personal.PersonalDetailActivity;
import com.petkit.android.activities.personal.PersonalFansListActivity;
import com.petkit.android.activities.personal.PersonalFollowsListActivity;
import com.petkit.android.activities.personal.RankDetailActivity;
import com.petkit.android.activities.petkitBleDevice.AdWebViewActivity;
import com.petkit.android.activities.petkitBleDevice.DeviceConsumablesActivity;
import com.petkit.android.activities.petkitBleDevice.d4sh.utils.D4shUtils;
import com.petkit.android.activities.petkitBleDevice.download.LocalAlbumActivity;
import com.petkit.android.activities.petkitBleDevice.download.MediaDownloadManagerActivity;
import com.petkit.android.activities.petkitBleDevice.r2.widget.NewIKnowWindow;
import com.petkit.android.activities.petkitBleDevice.utils.FamilyUtils;
import com.petkit.android.activities.registe.NormalLoginActivity;
import com.petkit.android.activities.remind.HealthRemindActivity2_0;
import com.petkit.android.activities.setting.SettingActivity;
import com.petkit.android.activities.statistics.WeightStatisticsActivity;
import com.petkit.android.activities.universalWindow.BottomSelectList1Window;
import com.petkit.android.api.PetkitCallback;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.apiResponse.CareConfigRsp;
import com.petkit.android.api.http.apiResponse.ErrorInfor;
import com.petkit.android.api.http.apiResponse.LoginRsp;
import com.petkit.android.api.http.apiResponse.UnReadStatusRsp;
import com.petkit.android.api.http.apiResponse.UserDetailsRsp;
import com.petkit.android.api.http.apiResponse.UserPointRsp;
import com.petkit.android.api.repository.WebModelRepository;
import com.petkit.android.model.ChatCenter;
import com.petkit.android.model.User;
import com.petkit.android.model.UserPoint;
import com.petkit.android.utils.AppVersionStateUtils;
import com.petkit.android.utils.ChatUtils;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.PetUtils;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.android.widget.DeviceMarksView;
import com.petkit.android.widget.PetListView;
import com.petkit.android.widget.SpecialMarksView;
import com.petkit.oversea.R;
import cz.msebera.android.httpclient.Header;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.TimeZone;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

/* JADX INFO: loaded from: classes4.dex */
public class MyselfFragment extends BaseFragment implements View.OnClickListener {
    private static final long PERSONAL_DETAIL_GAP_MILLISECOND = 60000;
    private int hasAq;
    private int hasAq1s;
    private int hasAqH1;
    private int hasAqr;
    private int hasCozy;
    private int hasCtw2;
    private int hasCtw3;
    private int hasD3;
    private int hasD4;
    private int hasD4s;
    private int hasD4sh;
    private int hasFeeder;
    private int hasFeederMini;
    private int hasFit;
    private int hasGo;
    private int hasH2;
    private int hasHg;
    private int hasK2;
    private int hasK3;
    private int hasMate;
    private int hasP3;
    private int hasP3c;
    private int hasP3d;
    private int hasR2;
    private int hasT3;
    private int hasT4;
    private int hasW4x;
    private int hasW5;
    private int hasW5c;
    private int hasW5n;
    private Long lastTime;
    private BroadcastReceiver mBroadcastReceiver;
    private int mSigned;
    private User mUser;
    private boolean needReload;
    private SaleHotLineData saleHotLineData;
    private boolean showRedPoint;

    private void refreshCsState() {
    }

    @Override // com.petkit.android.activities.base.BaseFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            this.mSigned = bundle.getInt("signed");
        }
        registerBoradcastReceiver();
        EventBus.getDefault().register(this);
    }

    @Override // com.petkit.android.activities.base.BaseFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        unregisterBroadcastReceiver();
        EventBus.getDefault().unregister(this);
    }

    @Override // androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        refreshPersonalDetail();
    }

    @Override // androidx.fragment.app.Fragment
    public void onSaveInstanceState(@NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt("signed", this.mSigned);
    }

    @Override // com.petkit.android.activities.base.BaseFragment
    @SuppressLint({"InflateParams"})
    public void setupViews(LayoutInflater layoutInflater) {
        setContentView(layoutInflater, R.layout.fragment_me);
        this.lastTime = 0L;
        setNoTitle();
        initView();
    }

    private void initView() {
        setNoTitleLeftButton();
        this.saleHotLineData = (SaleHotLineData) new Gson().fromJson(CommonUtils.getSysMap(Consts.SALE_HOTLINE_DATA), SaleHotLineData.class);
        LoginRsp.LoginResult currentLoginResult = UserInforUtils.getCurrentLoginResult();
        if (currentLoginResult != null) {
            this.mUser = currentLoginResult.getUser();
        }
        refreshMedical(new MedicalEvent(2));
        if (AppVersionStateUtils.getCurrentAppVersionCheckState()) {
            this.contentView.findViewById(R.id.ll_purchase_entry).setVisibility(8);
        } else {
            this.contentView.findViewById(R.id.ll_purchase_entry).setVisibility(0);
        }
        setPersonalDetailView();
        this.contentView.findViewById(R.id.menu_help_center).setOnClickListener(this);
        this.contentView.findViewById(R.id.menu_setting).setOnClickListener(this);
        this.contentView.findViewById(R.id.my_name_marks).setOnClickListener(this);
        this.contentView.findViewById(R.id.rank_container).setOnClickListener(this);
        this.contentView.findViewById(R.id.ll_order_management).setOnClickListener(this);
        this.contentView.findViewById(R.id.ll_device_consumables).setOnClickListener(this);
        this.contentView.findViewById(R.id.tv_personal_home_page).setOnClickListener(this);
        this.contentView.findViewById(R.id.ll_share_management).setOnClickListener(this);
        this.contentView.findViewById(R.id.menu_dailylife).setOnClickListener(this);
        this.contentView.findViewById(R.id.menu_circle).setOnClickListener(this);
        this.contentView.findViewById(R.id.look_all_pet).setOnClickListener(this);
        this.contentView.findViewById(R.id.rl_customer_service).setOnClickListener(this);
        this.contentView.findViewById(R.id.iv_email).setOnClickListener(this);
        this.contentView.findViewById(R.id.menu_album).setOnClickListener(this);
        this.contentView.findViewById(R.id.menu_download_manager).setOnClickListener(this);
        this.contentView.findViewById(R.id.menu_package_purchase).setOnClickListener(this);
        this.contentView.findViewById(R.id.menu_my_card).setOnClickListener(this);
        this.contentView.findViewById(R.id.menu_feed).setOnClickListener(this);
        this.contentView.findViewById(R.id.menu_weight).setOnClickListener(this);
        this.contentView.findViewById(R.id.menu_remind).setOnClickListener(this);
        this.contentView.findViewById(R.id.menu_other).setOnClickListener(this);
        this.contentView.findViewById(R.id.menu_personal_page).setOnClickListener(this);
        this.contentView.findViewById(R.id.menu_urology_clinic).setOnClickListener(this);
        refreshSignedView(this.mSigned);
        getCareConfig();
    }

    @Override // com.petkit.android.activities.base.BaseFragment, android.view.View.OnClickListener
    public void onClick(View view) {
        new HashMap();
        int id = view.getId();
        if (id == R.id.menu_package_purchase) {
            getServiceManage();
            return;
        }
        if (id == R.id.menu_my_card) {
            requireActivity().startActivity(MyCardHomeActivity.newIntent(getActivity()));
            if (this.showRedPoint) {
                DataHelper.setBooleanSF(getActivity(), Constants.CARD_COUPON_REMIND, Boolean.FALSE);
                refreshMenuCell2(this.contentView.findViewById(R.id.menu_my_card), R.string.My_card_coupon, R.drawable.ico_my_card, "", true, false);
                return;
            }
            return;
        }
        int i = R.id.menu_download_manager;
        if (id == i) {
            requireActivity().startActivity(MediaDownloadManagerActivity.newIntent(getActivity()));
            return;
        }
        if (id == R.id.menu_urology_clinic) {
            startActivity(new Intent(getActivity(), (Class<?>) ConsultationManagementActivity.class));
            return;
        }
        if (id == i) {
            getActivity().startActivity(MediaDownloadManagerActivity.newIntent(getActivity()));
            return;
        }
        if (id == R.id.menu_album) {
            if (CommonUtils.checkPermission(requireActivity(), "android.permission.WRITE_EXTERNAL_STORAGE")) {
                requireActivity().startActivity(LocalAlbumActivity.newIntent(getActivity()));
                return;
            } else {
                requireActivity().startActivity(PermissionDialogActivity.newIntent(getActivity(), getClass().getName(), "android.permission.WRITE_EXTERNAL_STORAGE"));
                return;
            }
        }
        if (id == R.id.menu_feed) {
            requireActivity().startActivity(FeedCalculatorActivity.newIntent(getActivity()));
            return;
        }
        if (id == R.id.menu_weight) {
            requireActivity().startActivity(WeightStatisticsActivity.newIntent(getActivity()));
            return;
        }
        if (id == R.id.menu_remind) {
            Intent intent = new Intent(getActivity(), (Class<?>) HealthRemindActivity2_0.class);
            intent.putExtra(Constants.EXTRA_PET_ID, PetUtils.ALL_DEVICE);
            startActivity(intent);
            return;
        }
        if (id == R.id.menu_other) {
            walkDog();
            return;
        }
        if (id == R.id.ll_order_management) {
            if ("US".equalsIgnoreCase(UserInforUtils.getAccount().getRegion()) || "UM".equalsIgnoreCase(UserInforUtils.getAccount().getRegion()) || "GB".equalsIgnoreCase(UserInforUtils.getAccount().getRegion())) {
                ShopifyUtils.getShopUrl(getActivity(), 2, new PetkitCallback<String>() { // from class: com.petkit.android.activities.home.MyselfFragment.1
                    @Override // com.petkit.android.api.PetkitCallback
                    public void onFailure(ErrorInfor errorInfor) {
                    }

                    @Override // com.petkit.android.api.PetkitCallback
                    public void onSuccess(String str) {
                        if (str == null || str.isEmpty()) {
                            return;
                        }
                        MyselfFragment myselfFragment = MyselfFragment.this;
                        myselfFragment.launchActivity(AdWebViewActivity.newIntent(myselfFragment.getActivity(), "", str));
                    }
                });
                return;
            }
            return;
        }
        if (id == R.id.ll_device_consumables) {
            startActivity(new Intent(getActivity(), (Class<?>) DeviceConsumablesActivity.class));
            return;
        }
        if (id == R.id.ll_share_management) {
            startActivity(ShareManagementActivity.newIntent(getActivity()));
            return;
        }
        if (id == R.id.menu_setting) {
            startActivityForResult(new Intent(getActivity(), (Class<?>) SettingActivity.class), 255);
            return;
        }
        if (id == R.id.tv_personal_home_page) {
            startActivity(PersonalActivity.class, false);
            return;
        }
        if (id == R.id.ll_my_follows) {
            Bundle bundle = new Bundle();
            bundle.putInt(Constants.EXTRA_TYPE, 3);
            bundle.putString(Constants.EXTRA_STRING_ID, CommonUtils.getCurrentUserId());
            startActivityWithData(PersonalFollowsListActivity.class, bundle, false);
            return;
        }
        if (id == R.id.ll_my_followers) {
            Bundle bundle2 = new Bundle();
            bundle2.putInt(Constants.EXTRA_TYPE, 4);
            bundle2.putString(Constants.EXTRA_STRING_ID, CommonUtils.getCurrentUserId());
            startActivityWithData(PersonalFansListActivity.class, bundle2, false);
            return;
        }
        if (id == R.id.my_avatar || id == R.id.rl_mask) {
            Bundle bundle3 = new Bundle();
            bundle3.putSerializable(Constants.EXTRA_USER_DETAIL, this.mUser);
            startActivityWithData(PersonalDetailActivity.class, bundle3, false);
            return;
        }
        if (id == R.id.rank_container) {
            Bundle bundle4 = new Bundle();
            bundle4.putSerializable(Constants.EXTRA_USERPOINT, this.mUser.getPoint());
            startActivityWithData(RankDetailActivity.class, bundle4, false);
            return;
        }
        if (id == R.id.sign_in) {
            setSigned();
            return;
        }
        if (id == R.id.menu_help_center) {
            startActivity(AdWebViewActivity.newIntent(requireActivity(), "", ApiTools.getWebUrlByKey("help_center")));
            return;
        }
        if (id == R.id.menu_dailylife) {
            startActivity(CommonFragmentActivity.newIntent(getContext(), 1, 1));
            return;
        }
        if (id == R.id.menu_circle) {
            startActivity(CommonFragmentActivity.newIntent(getContext(), 2, 1));
            return;
        }
        if (id == R.id.look_all_pet) {
            startActivity(PetsListActivity.class, false);
            return;
        }
        if (id == R.id.iv_email) {
            ServiceUtils.startToEmail2(requireActivity());
        } else if (id == R.id.rl_customer_service) {
            CommonUtils.enterContactCs(requireActivity());
        } else if (id == R.id.menu_personal_page) {
            startActivity(new Intent(requireActivity(), (Class<?>) PersonalActivity.class));
        }
    }

    private void getServiceManage() {
        if (getActivity() == null) {
            return;
        }
        HashMap map = new HashMap();
        map.put("v2", String.valueOf(1));
        map.put(BaseTopicsListFragment.USERID, UserInforUtils.getCurrentUserId(getActivity()));
        post(ApiTools.SAMPLE_API_DEVICE_SERVICE_MANAGE, map, new AsyncHttpRespHandler(getActivity()) { // from class: com.petkit.android.activities.home.MyselfFragment.2
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                if (MyselfFragment.this.getActivity() == null) {
                    return;
                }
                ServiceManageRsp serviceManageRsp = (ServiceManageRsp) this.gson.fromJson(this.responseResult, ServiceManageRsp.class);
                if (serviceManageRsp.getError() != null) {
                    MyselfFragment.this.showLongToast(serviceManageRsp.getError().getMsg());
                    return;
                }
                if (serviceManageRsp.getResult() != null) {
                    if (!serviceManageRsp.getResult().isEmpty()) {
                        for (CloudService cloudService : serviceManageRsp.getResult()) {
                            if (cloudService.getProductList() != null && !cloudService.getProductList().isEmpty()) {
                                MyselfFragment.this.getActivity().startActivity(new Intent(MyselfFragment.this.getActivity(), (Class<?>) ServiceManagementActivity.class));
                                return;
                            }
                        }
                        MyselfFragment.this.getActivity().startActivity(PackagePurchaseActivity.newIntent(MyselfFragment.this.getActivity(), D4shUtils.getStatusBarHeight(MyselfFragment.this.getActivity().getWindow())));
                        return;
                    }
                    MyselfFragment.this.getActivity().startActivity(PackagePurchaseActivity.newIntent(MyselfFragment.this.getActivity(), D4shUtils.getStatusBarHeight(MyselfFragment.this.getActivity().getWindow())));
                    return;
                }
                MyselfFragment.this.getActivity().startActivity(PackagePurchaseActivity.newIntent(MyselfFragment.this.getActivity(), D4shUtils.getStatusBarHeight(MyselfFragment.this.getActivity().getWindow())));
            }
        }, false);
    }

    public void reLoadMyselfNet() {
        if (this.needReload) {
            return;
        }
        this.needReload = true;
        setPersonalDetailView();
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == -1 && i == 255) {
            startActivity(NormalLoginActivity.class, true);
        }
    }

    public boolean checkIsInHotlineTime() {
        Calendar calendar = Calendar.getInstance();
        int i = calendar.get(7) - 1;
        int i2 = i != 0 ? i : 7;
        SaleHotLineData saleHotLineData = this.saleHotLineData;
        if (saleHotLineData != null && saleHotLineData.getDay().contains(String.valueOf(i2))) {
            calendar.setTimeZone(TimeZone.getTimeZone("GMT-5:00"));
            int i3 = (calendar.get(11) * 60) + calendar.get(12);
            if (this.saleHotLineData.getTime() != null && this.saleHotLineData.getTime().size() == 2) {
                if (Integer.parseInt(this.saleHotLineData.getTime().get(0)) < Integer.parseInt(this.saleHotLineData.getTime().get(1))) {
                    return i3 >= Integer.parseInt(this.saleHotLineData.getTime().get(0)) && i3 <= Integer.parseInt(this.saleHotLineData.getTime().get(1));
                }
                if (i3 <= Integer.parseInt(this.saleHotLineData.getTime().get(0)) && i3 >= Integer.parseInt(this.saleHotLineData.getTime().get(1))) {
                    return true;
                }
            }
        }
        return false;
    }

    public void openContactUsWindow() {
        final BottomSelectList1Window bottomSelectList1Window = new BottomSelectList1Window(getContext(), null, false);
        TextView textView = new TextView(getContext());
        textView.setText(getContext().getString(R.string.Contact_us_by_email));
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.home.MyselfFragment.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ServiceUtils.startToEmail2(MyselfFragment.this.getActivity());
                bottomSelectList1Window.dismiss();
            }
        });
        TextView textView2 = new TextView(getContext());
        textView2.setText(getContext().getString(R.string.Contact_us_by_phone));
        textView2.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.home.MyselfFragment.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MyselfFragment.this.openCallPhoneWindow();
                bottomSelectList1Window.dismiss();
            }
        });
        bottomSelectList1Window.addSelectView(textView2);
        bottomSelectList1Window.addSelectView(textView);
        bottomSelectList1Window.show(getActivity().getWindow().getDecorView());
    }

    public void openCallPhoneWindow() {
        if (this.saleHotLineData != null) {
            final BottomSelectList1Window bottomSelectList1Window = new BottomSelectList1Window(getContext(), null, false);
            TextView textView = new TextView(getContext());
            textView.setText(getResources().getString(R.string.Call) + this.saleHotLineData.getHotline());
            textView.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.home.MyselfFragment.5
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (MyselfFragment.this.checkIsInHotlineTime()) {
                        MyselfFragment.this.callUp();
                    } else {
                        MyselfFragment.this.notInHotlineTimeTipWindow();
                    }
                    bottomSelectList1Window.dismiss();
                }
            });
            bottomSelectList1Window.addSelectView(textView);
            bottomSelectList1Window.show(getActivity().getWindow().getDecorView());
        }
    }

    public void notInHotlineTimeTipWindow() {
        if (this.saleHotLineData != null) {
            new NewIKnowWindow(getContext(), (String) null, this.saleHotLineData.getTip(), (String) null).show(getActivity().getWindow().getDecorView());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void callUp() {
        if (this.saleHotLineData != null) {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.DIAL");
            intent.setData(Uri.parse("tel:" + this.saleHotLineData.getHotline()));
            startActivity(intent);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setPersonalDetailView() {
        if (this.mUser == null) {
            return;
        }
        SpecialMarksView specialMarksView = (SpecialMarksView) this.contentView.findViewById(R.id.my_name_marks);
        specialMarksView.setOnClickIconListener(new SpecialMarksView.OnClickIconListener() { // from class: com.petkit.android.activities.home.MyselfFragment.6
            @Override // com.petkit.android.widget.SpecialMarksView.OnClickIconListener
            public void onClickIcon() {
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.EXTRA_USERPOINT, MyselfFragment.this.mUser.getPoint());
                MyselfFragment.this.startActivityWithData(RankDetailActivity.class, bundle, false);
            }
        });
        specialMarksView.setShowGenderIcon(false);
        specialMarksView.setUser(this.mUser, this.hasFit, this.hasMate, this.hasGo, this.hasFeeder, this.hasCozy, this.hasFeederMini, this.hasK2, this.hasT3, this.hasAq, this.hasD3, this.hasD4, this.hasP3, this.hasH2, this.hasW5, this.hasAq1s, this.hasP3c, this.hasP3d, this.hasT4, this.hasK3, this.hasAqr, this.hasR2, this.hasW5c, this.hasW5n, this.hasW4x, this.hasAqH1, this.hasCtw2, this.hasD4s, this.hasHg, this.hasCtw3, this.hasD4sh);
        ((DeviceMarksView) this.contentView.findViewById(R.id.my_device_marks)).setUserDeviceMarksView(this.hasFit, this.hasMate, this.hasGo, this.hasFeeder, this.hasCozy, this.hasFeederMini, this.hasK2, this.hasT3, this.hasAq, this.hasD3, this.hasD4, this.hasP3, this.hasH2, this.hasW5, this.hasAq1s, this.hasP3c, this.hasP3d, this.hasT4, this.hasK3, this.hasAqr, this.hasR2, this.hasW5c, this.hasW5n, this.hasW4x, this.hasAqH1, this.hasCtw2, this.hasD4s, this.hasHg, this.hasCtw3, this.hasD4sh);
        ((TextView) this.contentView.findViewById(R.id.tv_my_device_num)).setText(getString(R.string.Have_smart_devices_count, String.valueOf(FamilyUtils.getInstance().getFamilyListAllDevicesCount(FamilyUtils.getInstance().getAllOwnFamilyList(getContext())) + HsConsts.getOwnerDeviceCount(getContext()) + GoDataUtils.getGoRecordList().size())));
        RelativeLayout relativeLayout = (RelativeLayout) this.contentView.findViewById(R.id.rl_mask);
        relativeLayout.setVisibility(0);
        relativeLayout.setOnClickListener(this);
        ImageView imageView = (ImageView) this.contentView.findViewById(R.id.my_avatar);
        imageView.setOnClickListener(this);
        ((BaseApplication) getActivity().getApplication()).getAppComponent().imageLoader().loadImage(getContext(), GlideImageConfig.builder().url(this.mUser.getAvatar()).imageView(imageView).transformation(new GlideCircleTransform(getContext())).build());
        refreshMyselfView();
    }

    private void refreshMyselfView() {
        boolean zIsEnable = CsUtils.getInstance().isEnable(requireActivity());
        int unreadCount = CsUtils.getInstance().getUnreadCount();
        if (zIsEnable) {
            this.contentView.findViewById(R.id.iv_email).setVisibility(0);
            this.contentView.findViewById(R.id.rl_customer_service).setVisibility(0);
            if (unreadCount > 0) {
                this.contentView.findViewById(R.id.cs_new_msg_circle).setVisibility(0);
            } else {
                this.contentView.findViewById(R.id.cs_new_msg_circle).setVisibility(8);
            }
        } else {
            this.contentView.findViewById(R.id.iv_email).setVisibility(0);
            this.contentView.findViewById(R.id.rl_customer_service).setVisibility(8);
        }
        this.showRedPoint = DataHelper.getBooleanSF(getActivity(), Constants.CARD_COUPON_REMIND);
        ChatCenter chatCenter = ChatUtils.getChatCenter(CommonUtils.getCurrentUserId());
        if (CommonUtils.isPetkitGuild(CommonUtils.getCurrentUserId())) {
            this.contentView.findViewById(R.id.ll_my_followers).setVisibility(8);
        }
        ((TextView) this.contentView.findViewById(R.id.my_follows)).setText(String.valueOf(chatCenter.getFollowerscount()));
        this.contentView.findViewById(R.id.ll_my_follows).setOnClickListener(this);
        if ("ja_JP".equals(UserInforUtils.getLanguageLocaleString(CommonUtils.getAppContext())) || "ru_RU".equals(UserInforUtils.getLanguageLocaleString(CommonUtils.getAppContext()))) {
            ((TextView) this.contentView.findViewById(R.id.my_follows_prompt)).setTextSize(1, 10.0f);
        }
        ((TextView) this.contentView.findViewById(R.id.my_followers)).setText(String.valueOf(chatCenter.getFanscount()));
        this.contentView.findViewById(R.id.ll_my_followers).setOnClickListener(this);
        if ("ja_JP".equals(UserInforUtils.getLanguageLocaleString(CommonUtils.getAppContext())) || "ru_RU".equals(UserInforUtils.getLanguageLocaleString(CommonUtils.getAppContext()))) {
            ((TextView) this.contentView.findViewById(R.id.my_followers_prompt)).setTextSize(1, 10.0f);
        }
        refreshMenuCell(this.contentView.findViewById(R.id.ll_share_management), R.string.Sharing_management, R.drawable.mine_share_icon, "", true);
        refreshMenuCell(this.contentView.findViewById(R.id.menu_package_purchase), R.string.Package_purchase, R.drawable.menu_package_purchase, "", true);
        refreshMenuCell2(this.contentView.findViewById(R.id.menu_my_card), R.string.My_card_coupon, R.drawable.ico_my_card, "", true, this.showRedPoint);
        refreshMenuCell(this.contentView.findViewById(R.id.menu_album), R.string.My_albums, R.drawable.mine_album_icon, "", true);
        refreshMenuCell(this.contentView.findViewById(R.id.menu_download_manager), R.string.Download_management, R.drawable.menu_download_manage, "", true);
        refreshMenuCell(this.contentView.findViewById(R.id.ll_device_consumables), R.string.Device_consumables, R.drawable.device_consumables_icon, "", false);
        String region = UserInforUtils.getAccount().getRegion();
        if ("US".equalsIgnoreCase(region) || "UM".equalsIgnoreCase(region) || "GB".equalsIgnoreCase(region)) {
            this.contentView.findViewById(R.id.ll_order_management).setVisibility(0);
        } else {
            this.contentView.findViewById(R.id.ll_order_management).setVisibility(8);
        }
        refreshMenuCell(this.contentView.findViewById(R.id.ll_order_management), R.string.Order_management, R.drawable.mine_order_icon, "", true);
        refreshMenuCell(this.contentView.findViewById(R.id.menu_feed), R.string.Feed_calculator, R.drawable.icon_self_feeder, "", true);
        refreshMenuCell(this.contentView.findViewById(R.id.menu_weight), R.string.Weight, R.drawable.icon_self_weight, "", true);
        refreshMenuCell2(this.contentView.findViewById(R.id.menu_remind), R.string.Smart_reminder, R.drawable.icon_self_remind, "", true, getScheduleCount() > 0);
        refreshMenuCell(this.contentView.findViewById(R.id.menu_other), R.string.Other, R.drawable.mine_walk_dog, "", true);
        refreshMenuCell(this.contentView.findViewById(R.id.menu_help_center), R.string.Help_center, R.drawable.icon_help_center, "", true);
        refreshMenuCell(this.contentView.findViewById(R.id.menu_dailylife), R.string.Homepage_daily_life, R.drawable.icon_dailylife, "", true);
        refreshMenuCell(this.contentView.findViewById(R.id.menu_personal_page), R.string.Personal_home_page, R.drawable.icon_personal_page, "", true);
        refreshMenuCell(this.contentView.findViewById(R.id.menu_circle), R.string.Homepage_pet_community, R.drawable.icon_pet_circle, "", true, CommonUtils.getPetCircleNewMsgCount() > 0);
        if (CommonUtils.getUnreadMedicalMsgNum() > 999) {
            refreshMenuCell(this.contentView.findViewById(R.id.menu_urology_clinic), R.string.Consult_urology_specialist_room_beta, R.drawable.urology_clini, "...", true);
        } else if (CommonUtils.getUnreadMedicalMsgNum() > 0) {
            refreshMenuCell(this.contentView.findViewById(R.id.menu_urology_clinic), R.string.Consult_urology_specialist_room_beta, R.drawable.urology_clini, String.valueOf(CommonUtils.getUnreadMedicalMsgNum()), true);
        } else {
            refreshMenuCell(this.contentView.findViewById(R.id.menu_urology_clinic), R.string.Consult_urology_specialist_room_beta, R.drawable.urology_clini, "", true);
        }
        TextView textView = (TextView) this.contentView.findViewById(R.id.my_pets);
        textView.setText(R.string.Pet);
        StringBuilder sb = new StringBuilder();
        sb.append(textView.getText().toString());
        sb.append(" ");
        sb.append(String.valueOf(this.mUser.getDogs(true) != null ? this.mUser.getDogs().size() : 0));
        SpannableString spannableString = new SpannableString(sb.toString());
        spannableString.setSpan(new StyleSpan(1), sb.toString().indexOf(textView.getText().toString()), sb.toString().indexOf(textView.getText().toString()) + textView.getText().toString().length(), 33);
        textView.setText(spannableString);
        initPetsView();
        refreshPointView();
        refreshCsState();
    }

    private void refreshMenuCell(View view, int i, int i2, String str, boolean z) {
        refreshMenuCell(view, i, i2, str, z, false);
    }

    private void refreshMenuCell2(View view, int i, int i2, String str, boolean z, boolean z2) {
        refreshMenuCell(view, i, i2, str, z, z2);
    }

    private void refreshMenuCell(View view, int i, int i2, String str, boolean z, boolean z2) {
        if (!isAdded() || getContext() == null) {
            return;
        }
        ((ImageView) view.findViewById(R.id.menu_icon)).setImageResource(i2);
        ((TextView) view.findViewById(R.id.menu_title)).setText(getResources().getString(i));
        TextView textView = (TextView) view.findViewById(R.id.menu_new_flag);
        if (!TextUtils.isEmpty(str)) {
            view.findViewById(R.id.menu_new_msg_circle).setVisibility(4);
            textView.setVisibility(0);
            textView.setText(str);
        } else {
            textView.setVisibility(4);
            view.findViewById(R.id.menu_new_msg_circle).setVisibility(z2 ? 0 : 4);
        }
        view.findViewById(R.id.menu_bottom_gap_line).setVisibility(z ? 4 : 0);
    }

    private void refreshPointView() {
        if (this.mUser.getPoint() != null && !CommonUtils.isEmpty(this.mUser.getPoint().getIcon())) {
            this.contentView.findViewById(R.id.rank_container).setVisibility(8);
            ((TextView) this.contentView.findViewById(R.id.my_rank_honor)).setText(this.mUser.getPoint().getHonour());
            ImageView imageView = (ImageView) this.contentView.findViewById(R.id.my_rank);
            if (!isEmpty(this.mUser.getPoint().getIcon()) && this.mUser.getPoint().getRank() >= 0) {
                ((BaseApplication) CommonUtils.getAppContext()).getAppComponent().imageLoader().loadImage(CommonUtils.getAppContext(), GlideImageConfig.builder().url(this.mUser.getPoint().getIcon()).imageView(imageView).build());
                return;
            } else {
                imageView.setVisibility(8);
                return;
            }
        }
        this.contentView.findViewById(R.id.rank_container).setVisibility(8);
    }

    public void refreshPersonalDetail() {
        if (this.lastTime == null || System.currentTimeMillis() - this.lastTime.longValue() > 60000) {
            getPersonalDetail();
        }
    }

    public void refreshCloudDevicesView(CareConfigRsp careConfigRsp) {
        if (!careConfigRsp.getResult().isCare()) {
            this.contentView.findViewById(R.id.ll_purchase_entry).setVisibility(8);
        } else {
            this.contentView.findViewById(R.id.ll_purchase_entry).setVisibility(AppVersionStateUtils.getCurrentAppVersionCheckState() ? 8 : 0);
        }
    }

    public void getCareConfig() {
        post(ApiTools.SAMPLE_API_GET_CARE_CONFIG, (AsyncHttpResponseHandler) new AsyncHttpRespHandler(getActivity()) { // from class: com.petkit.android.activities.home.MyselfFragment.7
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                if (MyselfFragment.this.getActivity() == null) {
                    return;
                }
                CareConfigRsp careConfigRsp = (CareConfigRsp) this.gson.fromJson(this.responseResult, CareConfigRsp.class);
                if (careConfigRsp.getError() != null) {
                    MyselfFragment.this.showLongToast(careConfigRsp.getError().getMsg());
                } else if (careConfigRsp.getResult() != null) {
                    DataHelper.setStringSF(MyselfFragment.this.getContext(), Consts.CARE_CONFIG, careConfigRsp.getResult().getUrl());
                    MyselfFragment.this.refreshCloudDevicesView(careConfigRsp);
                }
            }
        }, false);
    }

    @Subscriber
    public void refreshMedical(MedicalEvent medicalEvent) {
        if (medicalEvent.getType() == 1) {
            if (CommonUtils.getUnreadMedicalMsgNum() > 999) {
                refreshMenuCell(this.contentView.findViewById(R.id.menu_urology_clinic), R.string.Consult_urology_specialist_room_beta, R.drawable.urology_clini, "...", true);
                return;
            } else if (CommonUtils.getUnreadMedicalMsgNum() > 0) {
                refreshMenuCell(this.contentView.findViewById(R.id.menu_urology_clinic), R.string.Consult_urology_specialist_room_beta, R.drawable.urology_clini, String.valueOf(CommonUtils.getUnreadMedicalMsgNum()), true);
                return;
            } else {
                refreshMenuCell(this.contentView.findViewById(R.id.menu_urology_clinic), R.string.Consult_urology_specialist_room_beta, R.drawable.urology_clini, "", true);
                return;
            }
        }
        if (medicalEvent.getType() == 2) {
            if (DoctorUtils.getInstance().isTestUser(getActivity())) {
                HashMap map = new HashMap();
                map.put("type", String.valueOf(0));
                WebModelRepository.getInstance().medicalConversionList(map, new PetkitCallback<MedicalConversionList>() { // from class: com.petkit.android.activities.home.MyselfFragment.8
                    @Override // com.petkit.android.api.PetkitCallback
                    public void onSuccess(MedicalConversionList medicalConversionList) {
                        if (medicalConversionList == null || medicalConversionList.getList() == null || medicalConversionList.getList().size() <= 0) {
                            ((BaseFragment) MyselfFragment.this).contentView.findViewById(R.id.menu_urology_clinic).setVisibility(8);
                        } else {
                            ((BaseFragment) MyselfFragment.this).contentView.findViewById(R.id.menu_urology_clinic).setVisibility(0);
                        }
                    }

                    @Override // com.petkit.android.api.PetkitCallback
                    public void onFailure(ErrorInfor errorInfor) {
                        ((BaseFragment) MyselfFragment.this).contentView.findViewById(R.id.menu_urology_clinic).setVisibility(8);
                    }
                });
                return;
            }
            this.contentView.findViewById(R.id.menu_urology_clinic).setVisibility(8);
        }
    }

    @Subscriber
    public void refreshQiYuCs(CsQiYuEvent csQiYuEvent) {
        if (csQiYuEvent.getType() == 1) {
            refreshCsState();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getPersonalDetail() {
        if (getActivity() == null) {
            return;
        }
        HashMap map = new HashMap();
        map.put(BaseTopicsListFragment.USERID, UserInforUtils.getCurrentUserId(getActivity()));
        post(ApiTools.SAMPLE_API_USER_DETAILS, map, new AsyncHttpRespHandler(getActivity()) { // from class: com.petkit.android.activities.home.MyselfFragment.9
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                if (MyselfFragment.this.getActivity() == null) {
                    return;
                }
                UserDetailsRsp userDetailsRsp = (UserDetailsRsp) this.gson.fromJson(this.responseResult, UserDetailsRsp.class);
                if (userDetailsRsp.getError() != null) {
                    MyselfFragment.this.showLongToast(userDetailsRsp.getError().getMsg());
                    return;
                }
                if (userDetailsRsp.getResult() == null || userDetailsRsp.getResult().getUser() == null) {
                    return;
                }
                MyselfFragment.this.mUser = userDetailsRsp.getResult().getUser();
                LoginRsp.LoginResult currentLoginResult = UserInforUtils.getCurrentLoginResult();
                currentLoginResult.setUser(MyselfFragment.this.mUser);
                UserInforUtils.updateLoginResult(currentLoginResult);
                Intent intent = new Intent();
                intent.setAction(Constants.BROADCAST_MSG_UPDATE_PET);
                LocalBroadcastManager.getInstance(MyselfFragment.this.getActivity()).sendBroadcast(intent);
                ChatCenter chatCenter = ChatUtils.getChatCenter(CommonUtils.getCurrentUserId());
                chatCenter.setPostsCount(userDetailsRsp.getResult().getPostCount());
                chatCenter.setCollectsCount(userDetailsRsp.getResult().getPostCollectCount());
                chatCenter.setFollowerscount(userDetailsRsp.getResult().getFolloweeCount());
                chatCenter.setFanscount(userDetailsRsp.getResult().getFollowerCount());
                SugarRecord.save(chatCenter);
                DoctorUtils.getInstance().saveMedicalOnlineTime(CommonUtils.getAppContext(), userDetailsRsp.getResult().getMedicalOnlineAm(), userDetailsRsp.getResult().getMedicalOnlinePm());
                DoctorUtils.getInstance().setIsEnable(CommonUtils.getAppContext(), userDetailsRsp.getResult().getMedicalEnable() == 1);
                CsUtils.getInstance().setIsEnable(CommonUtils.getAppContext(), userDetailsRsp.getResult().getCsEnable() == 1);
                CsUtils.getInstance().setIsEnable(CommonUtils.getAppContext(), userDetailsRsp.getResult().getCsEnable() == 1);
                MyselfFragment.this.hasFit = userDetailsRsp.getResult().getHasFit();
                MyselfFragment.this.hasMate = userDetailsRsp.getResult().getHasMate();
                MyselfFragment.this.hasGo = userDetailsRsp.getResult().getHasGo();
                MyselfFragment.this.hasFeeder = userDetailsRsp.getResult().getHasFeeder();
                MyselfFragment.this.hasCozy = userDetailsRsp.getResult().getHasCozy();
                MyselfFragment.this.hasK2 = userDetailsRsp.getResult().getHasK2();
                MyselfFragment.this.hasT3 = userDetailsRsp.getResult().getHasT3();
                MyselfFragment.this.hasAq = userDetailsRsp.getResult().getHasAq();
                MyselfFragment.this.hasD3 = userDetailsRsp.getResult().getHasD3();
                MyselfFragment.this.hasD4 = userDetailsRsp.getResult().getHasD4();
                MyselfFragment.this.hasD4s = userDetailsRsp.getResult().getHasD4s();
                MyselfFragment.this.hasP3 = userDetailsRsp.getResult().getHasP3();
                MyselfFragment.this.hasH2 = userDetailsRsp.getResult().getHasH2();
                MyselfFragment.this.hasW5 = userDetailsRsp.getResult().getHasW5();
                MyselfFragment.this.hasW5c = userDetailsRsp.getResult().getHasW5c();
                MyselfFragment.this.hasW5n = userDetailsRsp.getResult().getHasW5n();
                MyselfFragment.this.hasW4x = userDetailsRsp.getResult().getHasW4x();
                MyselfFragment.this.hasCtw2 = userDetailsRsp.getResult().getHasCtw2();
                MyselfFragment.this.hasAq1s = userDetailsRsp.getResult().getHasAq1s();
                MyselfFragment.this.hasP3d = userDetailsRsp.getResult().getHasP3d();
                MyselfFragment.this.hasP3c = userDetailsRsp.getResult().getHasP3c();
                MyselfFragment.this.hasT4 = userDetailsRsp.getResult().getHasT4();
                MyselfFragment.this.hasK3 = userDetailsRsp.getResult().getHasK3();
                MyselfFragment.this.hasAqr = userDetailsRsp.getResult().getHasAqr();
                MyselfFragment.this.hasR2 = userDetailsRsp.getResult().getHasR2();
                MyselfFragment.this.hasAqH1 = userDetailsRsp.getResult().getHasAqH1();
                MyselfFragment.this.hasFeederMini = userDetailsRsp.getResult().getHasFeederMini();
                MyselfFragment.this.hasHg = userDetailsRsp.getResult().getHasHg();
                MyselfFragment.this.hasCtw3 = userDetailsRsp.getResult().getHasCtw3();
                MyselfFragment.this.hasD4sh = userDetailsRsp.getResult().getHasD4sh();
                MyselfFragment.this.hasFeederMini = userDetailsRsp.getResult().getHasFeederMini();
                MyselfFragment.this.setPersonalDetailView();
                MyselfFragment.this.refreshSignedView(userDetailsRsp.getResult().getSigned());
                MyselfFragment.this.lastTime = Long.valueOf(System.currentTimeMillis());
            }
        }, false);
    }

    private void registerBoradcastReceiver() {
        this.mBroadcastReceiver = new BroadcastReceiver() { // from class: com.petkit.android.activities.home.MyselfFragment.10
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                if (intent == null || intent.getAction() == null) {
                    return;
                }
                if (intent.getAction().equals(Constants.BROADCAST_MSG_UPDATE_USER_AVATAR)) {
                    MyselfFragment.this.mUser.setAvatar(intent.getStringExtra("infor"));
                    MyselfFragment.this.setPersonalDetailView();
                    return;
                }
                if (intent.getAction().equals(Constants.BROADCAST_MSG_UPDATE_USER)) {
                    MyselfFragment.this.mUser = (User) intent.getSerializableExtra(Constants.JID_TYPE_USER);
                    MyselfFragment.this.setPersonalDetailView();
                    return;
                }
                if (intent.getAction().equals(Constants.BROADCAST_MSG_CHANGE_USER)) {
                    MyselfFragment.this.mUser = UserInforUtils.getCurrentLoginResult().getUser();
                    MyselfFragment.this.setPersonalDetailView();
                    MyselfFragment.this.getPersonalDetail();
                    return;
                }
                if (intent.getAction().equals(Constants.BROADCAST_MSG_UPDATE_DOG)) {
                    MyselfFragment.this.mUser = UserInforUtils.getCurrentLoginResult().getUser();
                    MyselfFragment.this.setPersonalDetailView();
                } else {
                    if (intent.getAction().equals(Constants.BROADCAST_MSG_POST_INFOR_CHANGED)) {
                        MyselfFragment.this.setPersonalDetailView();
                        return;
                    }
                    if (intent.getAction().equals(Constants.BROADCAST_MSG_REFRESH_PERSONAL_DETAIL)) {
                        MyselfFragment.this.getPersonalDetail();
                        MyselfFragment.this.getCareConfig();
                    } else if (Constants.BROADCAST_MSG_UPDATE_MESSAGE.equals(intent.getAction())) {
                        MyselfFragment.this.setPersonalDetailView();
                    }
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.petkit.android.exit");
        intentFilter.addAction(Constants.BROADCAST_MSG_UPDATE_USER_AVATAR);
        intentFilter.addAction(Constants.BROADCAST_MSG_UPDATE_DOG);
        intentFilter.addAction(Constants.BROADCAST_MSG_UPDATE_USER);
        intentFilter.addAction(Constants.BROADCAST_MSG_CHANGE_USER);
        intentFilter.addAction(Constants.BROADCAST_MSG_POST_INFOR_CHANGED);
        intentFilter.addAction(Constants.BROADCAST_MSG_REFRESH_PERSONAL_DETAIL);
        intentFilter.addAction(Constants.BROADCAST_MSG_UPDATE_MESSAGE);
        LocalBroadcastManager.getInstance(requireActivity()).registerReceiver(this.mBroadcastReceiver, intentFilter);
    }

    private void unregisterBroadcastReceiver() {
        LocalBroadcastManager.getInstance(requireActivity()).unregisterReceiver(this.mBroadcastReceiver);
    }

    private void walkDog() {
        startActivity(new Intent(requireActivity(), (Class<?>) OtherSettingActivity.class));
    }

    private void initPetsView() {
        LinearLayout linearLayout = (LinearLayout) this.contentView.findViewById(R.id.ll_pets_view);
        linearLayout.removeAllViews();
        linearLayout.addView(new PetListView(getActivity(), this.mUser.getDogs(false), null, 0), new LinearLayout.LayoutParams(-1, -2));
    }

    @Override // com.petkit.android.activities.base.BaseFragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        refreshMyselfView();
    }

    private void entryImageDetail(String str) {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(str);
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(ImageDetailActivity.IMAGE_LIST_DATA, arrayList);
        bundle.putInt(ImageDetailActivity.IMAGE_LIST_POSITION, 0);
        startActivityWithData(ImageDetailActivity.class, bundle, false);
        requireActivity().overridePendingTransition(R.anim.img_scale_in, R.anim.img_scale_out);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshSignedView(int i) {
        TextView textView = (TextView) this.contentView.findViewById(R.id.sign_in);
        textView.setVisibility(8);
        if (i == 1) {
            textView.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.sign_in_not_icon), (Drawable) null, (Drawable) null, (Drawable) null);
            textView.setText(R.string.Signed_in);
            textView.setTextColor(CommonUtils.getColorById(R.color.gray));
            textView.setOnClickListener(null);
        } else {
            textView.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.sign_in_icon), (Drawable) null, (Drawable) null, (Drawable) null);
            textView.setText(R.string.Sign_in);
            textView.setTextColor(CommonUtils.getColorById(R.color.food_rate_4));
            textView.setOnClickListener(this);
        }
        this.mSigned = i;
    }

    private void setSigned() {
        if (getActivity() == null) {
            return;
        }
        post(ApiTools.SAMPLE_API_RANK_SIGNIN, (AsyncHttpResponseHandler) new AsyncHttpRespHandler(getActivity(), true) { // from class: com.petkit.android.activities.home.MyselfFragment.11
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                if (MyselfFragment.this.getActivity() == null) {
                    return;
                }
                UserPointRsp userPointRsp = (UserPointRsp) this.gson.fromJson(this.responseResult, UserPointRsp.class);
                if (userPointRsp.getError() != null) {
                    MyselfFragment.this.showLongToast(userPointRsp.getError().getMsg());
                    return;
                }
                LoginRsp.LoginResult currentLoginResult = UserInforUtils.getCurrentLoginResult();
                UserPoint point = userPointRsp.getResult().getUser().getPoint();
                if (userPointRsp.getResult().getWarmTips() == 0) {
                    MyselfFragment myselfFragment = MyselfFragment.this;
                    myselfFragment.showRankToast(R.drawable.rank_signed_upgrade, myselfFragment.getResources().getString(R.string.Level_upgrade_alert_text), MyselfFragment.this.getActivity().getApplicationContext().getResources().getString(R.string.Level_upgrade_alert_detail_text, point.getHonour()));
                } else {
                    MyselfFragment myselfFragment2 = MyselfFragment.this;
                    myselfFragment2.showRankToast(R.drawable.rank_signed, myselfFragment2.getResources().getString(R.string.Sign_in_success), userPointRsp.getResult().getScore() + MyselfFragment.this.getResources().getString(R.string.Point));
                }
                MyselfFragment.this.mUser.setPoint(point);
                if (currentLoginResult != null) {
                    currentLoginResult.setUser(MyselfFragment.this.mUser);
                }
                UserInforUtils.updateLoginResult(currentLoginResult);
                MyselfFragment.this.refreshSignedView(1);
            }
        }, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showRankToast(int i, String str, String str2) {
        View viewInflate = requireActivity().getLayoutInflater().inflate(R.layout.layout_rank_toast, (ViewGroup) null);
        ((ImageView) viewInflate.findViewById(R.id.image)).setImageResource(i);
        ((TextView) viewInflate.findViewById(R.id.textView1)).setText(str);
        ((TextView) viewInflate.findViewById(R.id.textView2)).setText(str2);
        Toast toast = new Toast(getActivity());
        toast.setGravity(17, 0, 0);
        toast.setDuration(0);
        toast.setView(viewInflate);
        toast.show();
    }

    private int getScheduleCount() {
        UnReadStatusRsp unReadMsg = CommonUtils.getUnReadMsg();
        if (unReadMsg == null || unReadMsg.getResult() == null) {
            return 0;
        }
        return unReadMsg.getResult().getSchedule();
    }

    public void updateRemindCount() {
        if (this.contentView == null) {
            return;
        }
        refreshMenuCell2(this.contentView.findViewById(R.id.menu_remind), R.string.Smart_reminder, R.drawable.icon_self_remind, "", true, getScheduleCount() > 0);
    }

    public void updateCsCount(int i) {
        if (i > 0) {
            this.contentView.findViewById(R.id.cs_new_msg_circle).setVisibility(0);
        } else {
            this.contentView.findViewById(R.id.cs_new_msg_circle).setVisibility(8);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onHiddenChanged(boolean z) {
        super.onHiddenChanged(z);
        if (z) {
            return;
        }
        refreshCsState();
    }

    @Override // androidx.fragment.app.Fragment
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }
}

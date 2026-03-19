package com.petkit.android.activities.fit;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.constraintlayout.motion.widget.Key;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.facebook.CallbackManager;
import com.facebook.devicerequests.internal.DeviceRequestsHelper;
import com.facebook.internal.ServerProtocol;
import com.google.gson.Gson;
import com.jess.arms.utils.Consts;
import com.jess.arms.utils.DeviceUtils;
import com.jess.arms.widget.InterceptScrollView;
import com.jess.arms.widget.imageloader.glide.GlideCircleTransform;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.jjoe64.graphview.BarGraphView;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.IInterceptListener;
import com.jjoe64.graphview.RectGraphView;
import com.orm.SugarRecord;
import com.orm.query.Condition;
import com.orm.query.Select;
import com.petkit.android.activities.base.BaseActivity;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.community.WebviewActivity;
import com.petkit.android.activities.home.DeviceAddPetSelectListActivity;
import com.petkit.android.activities.pet.PetCreateActivity;
import com.petkit.android.activities.registe.NormalLoginActivity;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.AsyncHttpUtil;
import com.petkit.android.api.http.apiResponse.DailyDetailRsp;
import com.petkit.android.api.http.apiResponse.LoginRsp;
import com.petkit.android.api.http.apiResponse.ReportSyncRsp;
import com.petkit.android.api.http.apiResponse.ResultStringArrayRsp;
import com.petkit.android.api.http.apiResponse.StaticDataRsp;
import com.petkit.android.ble.BLEConsts;
import com.petkit.android.ble.DeviceInfo;
import com.petkit.android.ble.samsung.BaseBluetoothLeUtils;
import com.petkit.android.ble.samsung.ISamsungBLEListener;
import com.petkit.android.ble.samsung.SSBluetoothLeUtils;
import com.petkit.android.ble.service.ActivityDataProcessService;
import com.petkit.android.ble.service.AndroidBLEActionService;
import com.petkit.android.model.DailyDetailItem;
import com.petkit.android.model.Device;
import com.petkit.android.model.Food;
import com.petkit.android.model.Pet;
import com.petkit.android.model.PrivateFood;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.DailyDetailUtils;
import com.petkit.android.utils.DateUtil;
import com.petkit.android.utils.FileUtils;
import com.petkit.android.utils.LogcatStorageHelper;
import com.petkit.android.utils.PetkitLog;
import com.petkit.android.utils.ShareHelper;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.android.widget.LowBatteryDialog;
import com.petkit.android.widget.SharePopMenu;
import com.petkit.oversea.R;
import cz.msebera.android.httpclient.Header;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sourceforge.pinyin4j.ChineseToPinyinResource;

/* JADX INFO: loaded from: classes3.dex */
@SuppressLint({"SimpleDateFormat", "InflateParams", "ViewHolder"})
public class PetkitDetailActivity extends BaseActivity implements View.OnClickListener, ISamsungBLEListener, IInterceptListener, View.OnTouchListener {
    public static final int ACTIVITY_RESULT_DEVICE_MANAGE = 390;
    private static final int ACTIVITY_RESULT_REPORT = 388;
    private static final int ANIMATION_TYPE_GONE = 3;
    private static final int ANIMATION_TYPE_SYNC_START = 1;
    private static final int ANIMATION_TYPE_SYNC_STOP = 2;
    private static final int ANIMATION_TYPE_VISIBILITY = 4;
    private static final float UNIT_TEXT_SIZE = 0.8f;
    public static int deviceConnectState = -1;
    private AnimatorSet animSet;
    private CallbackManager callbackManager;
    private int change;
    private Pet curPet;
    private ImageView dogAvatar;
    private Drawable[] drawables;
    private Handler handler;
    private int[] ids;
    private InductionTimePagerAdapter inductionTimePagerAdapter;
    private boolean isAutoSync;
    private boolean isPlay;
    private RelativeLayout lowBatteryHint;
    private BaseBluetoothLeUtils mBluetoothLeUtils;
    private BroadcastReceiver mBroadcastReceiver;
    private TextView mCancelTextView;
    private String mCurrentDay;
    private Map<String, DailyDetailItem> mDailyDataMap;
    private Device mDevice;
    private View mPetkitDailyView;
    private View mPetkitNullView;
    private ImageView mStateImageView;
    private TextView mStateTextView;
    private ArrayList<String> mWeekDays;
    private Date petCreateDate;
    private LinearLayout petTitle;
    private View petkitSyncBtnView;
    private InterceptScrollView scrollView;
    private View syncImageView1;
    private View syncImageView2;
    private View syncImageView3;
    private View syncImageView4;
    private float syncImageViewPositionX;
    private float syncImageViewPositionY;
    private Date todayDate;
    private ViewPager viewPager;
    private boolean pound = false;
    private boolean isToday = true;
    private int subTitleAnimState = 4;
    private boolean isThisActivityOnTop = false;
    private boolean isSamsungBLE = false;
    private boolean canStartSync = false;
    private int pagerCount = 0;
    private int toDayPosition = 0;
    private int petCreatePosition = 0;
    private int currentPosition = 0;
    private float petkitSyncBtnViewPositionY = 0.0f;

    @Override // com.petkit.android.activities.base.BaseActivity, com.petkit.android.activities.common.fragment.FailureFragment.FailureOnClickListener
    public void onRefresh(boolean z) {
    }

    public PetkitDetailActivity() {
        int i = R.drawable.solid_white_report_bg;
        int i2 = R.drawable.solid_orange_report_bg;
        this.ids = new int[]{i, i2, i, i2, i, i2};
        this.isPlay = true;
        this.change = 0;
        this.handler = new Handler(new Handler.Callback() { // from class: com.petkit.android.activities.fit.PetkitDetailActivity.19
            @Override // android.os.Handler.Callback
            @SuppressLint({"NewApi"})
            public boolean handleMessage(Message message) {
                if (message.what == 1001) {
                    int i3 = message.arg1;
                    TransitionDrawable transitionDrawable = new TransitionDrawable(new Drawable[]{PetkitDetailActivity.this.drawables[PetkitDetailActivity.this.change % PetkitDetailActivity.this.ids.length], PetkitDetailActivity.this.drawables[(PetkitDetailActivity.this.change + 1) % PetkitDetailActivity.this.ids.length]});
                    PetkitDetailActivity.this.findViewById(R.id.petkit_report_rl).setBackground(transitionDrawable);
                    transitionDrawable.startTransition(i3);
                    PetkitDetailActivity.access$3108(PetkitDetailActivity.this);
                    if (PetkitDetailActivity.this.change > 5) {
                        PetkitDetailActivity.this.isPlay = false;
                    }
                }
                return false;
            }
        });
        this.animSet = new AnimatorSet();
        this.syncImageViewPositionX = 0.0f;
        this.syncImageViewPositionY = 0.0f;
    }

    public static /* synthetic */ int access$3108(PetkitDetailActivity petkitDetailActivity) {
        int i = petkitDetailActivity.change;
        petkitDetailActivity.change = i + 1;
        return i;
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        boolean z = false;
        if (bundle != null) {
            this.isAutoSync = bundle.getBoolean(Constants.EXTRA_BOOLEAN);
            this.curPet = (Pet) bundle.getSerializable(Constants.EXTRA_DOG);
        } else {
            this.isAutoSync = getIntent().getBooleanExtra(Constants.EXTRA_BOOLEAN, false);
            this.curPet = (Pet) getIntent().getSerializableExtra(Constants.EXTRA_DOG);
        }
        initDogs();
        setDeviceUpdateFlag();
        setContentView(R.layout.activity_petkit_detail);
        if (CommonUtils.getAndroidSDKVersion() == 17 && CommonUtils.isSamsungDevice(Build.MODEL)) {
            z = true;
        }
        this.isSamsungBLE = z;
        this.callbackManager = CallbackManager.Factory.create();
        this.handler.postDelayed(new Runnable() { // from class: com.petkit.android.activities.fit.PetkitDetailActivity.1
            @Override // java.lang.Runnable
            public void run() {
                if (!PetkitDetailActivity.this.isAutoSync || PetkitDetailActivity.this.petkitSyncBtnView == null || PetkitDetailActivity.this.curPet == null || PetkitDetailActivity.this.curPet.getDevice() == null || PetkitDetailActivity.deviceConnectState != -1 || !PetkitDetailActivity.this.canStartSync) {
                    return;
                }
                PetkitDetailActivity.this.startScan();
                PetkitDetailActivity.this.getWindow().addFlags(128);
            }
        }, 1000L);
        registerBoradcastReceiver();
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBoolean(Constants.EXTRA_BOOLEAN, this.isAutoSync);
        bundle.putSerializable(Constants.EXTRA_DOG, this.curPet);
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        this.isThisActivityOnTop = false;
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        this.isThisActivityOnTop = true;
        if (deviceConnectState != -1) {
            updateBLESyncProgress(CommonUtils.getSysIntMap(this, Consts.SHARED_DEVICE_CONNECT_STATE, 255), null);
        }
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        try {
            getWindow().clearFlags(128);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (this.isSamsungBLE) {
            deviceConnectState = -1;
            BaseBluetoothLeUtils baseBluetoothLeUtils = this.mBluetoothLeUtils;
            if (baseBluetoothLeUtils != null) {
                baseBluetoothLeUtils.stop();
            }
        }
        unregisterBroadcastReceiver();
    }

    @Override // com.petkit.android.activities.base.BaseActivity
    public void setupViews() {
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        this.todayDate = date;
        calendar.setTime(date);
        this.mCurrentDay = DateUtil.date2Str(this.todayDate, DateUtil.DATE_FORMAT_7);
        int i = calendar.get(7);
        if (i - 1 <= 0) {
            this.currentPosition = 6;
        } else {
            this.currentPosition = i - 2;
        }
        this.toDayPosition = this.currentPosition;
        View viewFindViewById = findViewById(R.id.petkit_sync_btn_view);
        this.petkitSyncBtnView = viewFindViewById;
        viewFindViewById.setOnClickListener(this);
        Pet pet = this.curPet;
        if (pet != null) {
            try {
                this.petCreateDate = DateUtil.parseISO8601Date(pet.getCreatedAt());
            } catch (Exception e) {
                e.printStackTrace();
            }
            Date date2 = this.petCreateDate;
            if (date2 != null) {
                calendar.setTime(date2);
            } else {
                uploadUrgentLog("[WARNING] curPet.getCreatedAt() = NULL !!! curPet id: " + this.curPet.getId() + "  LoginResult: " + CommonUtils.getSysMap(Consts.SHARED_LOGIN_RESULT));
            }
            int i2 = calendar.get(7);
            if (i2 - 1 <= 0) {
                this.petCreatePosition = 6;
            } else {
                this.petCreatePosition = i2 - 2;
            }
            int iAbs = Math.abs(DateUtil.getOffsetDaysToTodayFromString(this.curPet.getCreatedAt())) + this.petCreatePosition + 1;
            if (iAbs % 7 != 0) {
                this.pagerCount = (iAbs / 7) + 1;
            } else {
                this.pagerCount = iAbs / 7;
            }
        }
        this.viewPager = (ViewPager) findViewById(R.id.view_pager);
        InductionTimePagerAdapter inductionTimePagerAdapter = new InductionTimePagerAdapter(this.pagerCount);
        this.inductionTimePagerAdapter = inductionTimePagerAdapter;
        this.viewPager.setAdapter(inductionTimePagerAdapter);
        this.viewPager.setCurrentItem(this.pagerCount - 1);
        this.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.petkit.android.activities.fit.PetkitDetailActivity.2
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i3) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i3, float f, int i4) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int i3) {
                PetkitDetailActivity petkitDetailActivity = PetkitDetailActivity.this;
                petkitDetailActivity.mWeekDays = petkitDetailActivity.getWeekDays(i3);
                PetkitDetailActivity petkitDetailActivity2 = PetkitDetailActivity.this;
                petkitDetailActivity2.mCurrentDay = (String) petkitDetailActivity2.mWeekDays.get(PetkitDetailActivity.this.currentPosition);
                if (DateUtil.str2Date(PetkitDetailActivity.this.mCurrentDay, DateUtil.DATE_FORMAT_7).compareTo(PetkitDetailActivity.this.todayDate) <= 0) {
                    if (PetkitDetailActivity.this.petCreateDate != null && DateUtil.str2Date(PetkitDetailActivity.this.mCurrentDay, DateUtil.DATE_FORMAT_7).compareTo(PetkitDetailActivity.this.petCreateDate) < 0) {
                        PetkitDetailActivity petkitDetailActivity3 = PetkitDetailActivity.this;
                        petkitDetailActivity3.mCurrentDay = (String) petkitDetailActivity3.mWeekDays.get(PetkitDetailActivity.this.petCreatePosition);
                    }
                } else {
                    PetkitDetailActivity petkitDetailActivity4 = PetkitDetailActivity.this;
                    petkitDetailActivity4.mCurrentDay = (String) petkitDetailActivity4.mWeekDays.get(PetkitDetailActivity.this.toDayPosition);
                }
                LocalBroadcastManager.getInstance(PetkitDetailActivity.this).sendBroadcast(new Intent(Constants.BROADCAST_MSG_TIMEGRIDVIEW_POSITION));
                if (PetkitDetailActivity.this.mDailyDataMap == null || !PetkitDetailActivity.this.mDailyDataMap.containsKey(PetkitDetailActivity.this.mWeekDays.get(0))) {
                    for (int i4 = 0; i4 < 7; i4++) {
                        String str = (String) PetkitDetailActivity.this.mWeekDays.get(i4);
                        DailyDetailItem dailyDetailItem = (DailyDetailItem) Select.from(DailyDetailItem.class).where(Condition.prop("dogId").eq(PetkitDetailActivity.this.curPet.getId()), Condition.prop("day").eq(str)).first();
                        if (dailyDetailItem == null && i4 == 0) {
                            PetkitDetailActivity.this.findViewById(R.id.petkit_detail_loading_view).setVisibility(0);
                            PetkitDetailActivity.this.findViewById(R.id.petkit_data_empty_view).setVisibility(8);
                            PetkitDetailActivity petkitDetailActivity5 = PetkitDetailActivity.this;
                            petkitDetailActivity5.syncChangedActivityDailyDetail(petkitDetailActivity5.curPet.getId(), (String) PetkitDetailActivity.this.mWeekDays.get(0), (String) PetkitDetailActivity.this.mWeekDays.get(PetkitDetailActivity.this.mWeekDays.size() - 1), null);
                            return;
                        }
                        if (dailyDetailItem != null) {
                            if (PetkitDetailActivity.this.mDailyDataMap != null) {
                                PetkitDetailActivity.this.mDailyDataMap.put(str, DailyDetailUtils.getDailyDetailItem(PetkitDetailActivity.this.curPet.getId(), str));
                            }
                        } else if (PetkitDetailActivity.this.mDailyDataMap != null) {
                            PetkitDetailActivity.this.mDailyDataMap.put(str, DailyDetailUtils.getDailyDetailItem(PetkitDetailActivity.this.curPet.getId(), str));
                        }
                    }
                    PetkitDetailActivity.this.initPetkitDailyDetailView();
                    return;
                }
                PetkitDetailActivity.this.initPetkitDailyDetailView();
            }
        });
        this.mPetkitDailyView = findViewById(R.id.petkit_detail_view);
        this.mPetkitNullView = findViewById(R.id.petkit_null_view);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.low_battery_hint);
        this.lowBatteryHint = relativeLayout;
        relativeLayout.setOnClickListener(this);
        setTitleLeftButton(R.drawable.btn_back_gray);
        initSyncingView();
        findViewById(R.id.petkit_view).setMinimumHeight(BaseApplication.displayMetrics.heightPixels - ((int) getResources().getDimension(R.dimen.base_titleheight)));
        Pet pet2 = this.curPet;
        if (pet2 == null || pet2.getDevice() == null) {
            initPetkitNullView();
        } else {
            initPetkitDailyView();
        }
        int sysIntMap = CommonUtils.getSysIntMap(this, Consts.SHARED_DEVICE_CONNECT_STATE, 255);
        if (sysIntMap <= 100) {
            updateBLESyncProgress(sysIntMap, null);
        } else {
            deviceConnectState = -1;
        }
        int i3 = deviceConnectState;
        if (i3 != -1 && i3 != 3) {
            new Handler().postDelayed(new Runnable() { // from class: com.petkit.android.activities.fit.PetkitDetailActivity.3
                @Override // java.lang.Runnable
                public void run() {
                    PetkitDetailActivity.this.startSubtitleAnimation(1);
                }
            }, 100L);
        }
        this.scrollView = (InterceptScrollView) findViewById(R.id.scrollview);
        LinearLayout titleTab = getTitleTab();
        this.petTitle = titleTab;
        titleTab.setVisibility(0);
        this.petTitle.setOnClickListener(this);
        refreshSubtitle();
    }

    public ImageView getDogAvatar() {
        return this.dogAvatar;
    }

    private void refreshPetTitleView(Pet pet) {
        this.petTitle.removeAllViews();
        View viewInflate = LayoutInflater.from(this).inflate(R.layout.layout_pet_title, (ViewGroup) null);
        this.dogAvatar = (ImageView) viewInflate.findViewById(R.id.dog_avatar);
        TextView textView = (TextView) viewInflate.findViewById(R.id.dog_name);
        ((BaseApplication) getApplication()).getAppComponent().imageLoader().loadImage(this, GlideImageConfig.builder().url(pet.getAvatar()).imageView(this.dogAvatar).errorPic(pet.getType().getId() == 1 ? R.drawable.default_header_dog : R.drawable.default_header_cat).transformation(new GlideCircleTransform(this)).build());
        textView.setText(pet.getName());
        this.petTitle.addView(viewInflate);
    }

    @Override // com.petkit.android.activities.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == ACTIVITY_RESULT_REPORT) {
            this.isThisActivityOnTop = true;
        } else if (i == 390 && i2 == -1) {
            changeCurrentDog((Pet) intent.getSerializableExtra(Constants.EXTRA_DOG));
        }
        CallbackManager callbackManager = this.callbackManager;
        if (callbackManager != null) {
            callbackManager.onActivityResult(i, i2, intent);
        }
        ShareHelper.dealWithOnActivityResult(this, i, i2, intent);
    }

    private void initDogs() {
        LoginRsp.LoginResult currentLoginResult = UserInforUtils.getCurrentLoginResult();
        if (currentLoginResult == null || currentLoginResult.getSettings() == null) {
            return;
        }
        this.pound = currentLoginResult.getSettings().getUnit() == 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onRefreshDogFood(String str) {
        Map<String, DailyDetailItem> map = this.mDailyDataMap;
        if (map == null || map.size() <= 0 || this.mDailyDataMap.get(this.mCurrentDay) == null) {
            return;
        }
        this.mDailyDataMap.get(this.mCurrentDay).setFood(str);
        SugarRecord.save(this.mDailyDataMap.get(this.mCurrentDay));
        initPetkitDailyDetailView();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeCurrentDog(Pet pet) {
        if (pet == null) {
            finish();
            return;
        }
        if (isFinishing()) {
            return;
        }
        this.curPet = pet;
        this.mDailyDataMap = null;
        try {
            this.petCreateDate = DateUtil.parseISO8601Date(pet.getCreatedAt());
        } catch (Exception unused) {
        }
        Calendar calendar = Calendar.getInstance();
        Date date = this.petCreateDate;
        if (date != null) {
            calendar.setTime(date);
        }
        int i = calendar.get(7);
        if (i - 1 <= 0) {
            this.petCreatePosition = 6;
        } else {
            this.petCreatePosition = i - 2;
        }
        int iAbs = Math.abs(DateUtil.getOffsetDaysToTodayFromString(this.curPet.getCreatedAt())) + this.petCreatePosition + 1;
        if (iAbs % 7 != 0) {
            this.pagerCount = (iAbs / 7) + 1;
        } else {
            this.pagerCount = iAbs / 7;
        }
        InductionTimePagerAdapter inductionTimePagerAdapter = new InductionTimePagerAdapter(this.pagerCount);
        this.inductionTimePagerAdapter = inductionTimePagerAdapter;
        this.viewPager.setAdapter(inductionTimePagerAdapter);
        this.viewPager.setCurrentItem(this.pagerCount - 1);
        this.mCurrentDay = DateUtil.date2Str(this.todayDate, DateUtil.DATE_FORMAT_7);
        this.currentPosition = this.toDayPosition;
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(Constants.BROADCAST_MSG_TIMEGRIDVIEW_POSITION));
        refreshPetkitView();
    }

    private void refreshPetkitView() {
        if (isFinishing()) {
            return;
        }
        Pet pet = this.curPet;
        if (pet == null || pet.getDevice() == null) {
            initPetkitNullView();
        } else {
            initPetkitDailyView();
        }
        refreshSubtitle();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeDeviceConnectState(int i) {
        changeDeviceConnectState(i, 0);
    }

    private void changeDeviceConnectState(int i, int i2) {
        String str;
        int i3 = deviceConnectState;
        if (i3 == -1) {
            return;
        }
        if (i3 == 3 && i == 5) {
            return;
        }
        if (i == 3 && (i3 == 5 || i3 == 7)) {
            return;
        }
        if (i == 8 && i3 == 0) {
            deviceConnectState = 4;
        } else {
            deviceConnectState = i;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("device connection state change: ");
        sb.append(CommonUtils.getConnectionStateDesc(i));
        if (i == 2) {
            str = " syncDataProgress: " + i2;
        } else {
            str = "";
        }
        sb.append(str);
        LogcatStorageHelper.addLog(sb.toString());
        changeSyncState(deviceConnectState, i2);
    }

    private void initPetkitDailyView() {
        this.mPetkitDailyView.setVisibility(0);
        this.petkitSyncBtnView.setVisibility(0);
        this.viewPager.setVisibility(0);
        this.mPetkitNullView.setVisibility(8);
        this.mPetkitDailyView.findViewById(R.id.data_activity).setOnClickListener(this);
        this.mPetkitDailyView.findViewById(R.id.data_rest).setOnClickListener(this);
        this.mPetkitDailyView.findViewById(R.id.data_consumption).setOnClickListener(this);
        this.mPetkitDailyView.findViewById(R.id.data_activity).setOnTouchListener(this);
        this.mPetkitDailyView.findViewById(R.id.data_rest).setOnTouchListener(this);
        this.mPetkitDailyView.findViewById(R.id.data_consumption).setOnTouchListener(this);
        initPetkitDailyDetailView();
    }

    private void initPetkitNullView() {
        this.mPetkitNullView.findViewById(R.id.petkit_get).setOnClickListener(this);
        this.mPetkitNullView.findViewById(R.id.petkit_connect).setOnClickListener(this);
        this.mPetkitDailyView.setVisibility(8);
        findViewById(R.id.petkit_data_empty_view).setVisibility(8);
        this.viewPager.setVisibility(8);
        this.mPetkitNullView.setVisibility(0);
        startSubtitleAnimation(3);
        this.mPetkitNullView.findViewById(R.id.pet_null_view).setVisibility(0);
        ((TextView) this.mPetkitNullView.findViewById(R.id.pet_null_view)).setText("p2        " + getString(R.string.Or) + "        p1\n\n\n\n\n" + getString(R.string.Dog_has_no_device_notice_2));
        this.petkitSyncBtnView.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setDeviceUpdateFlag() {
        Pet pet = this.curPet;
        setTitleRight2ImageView((pet == null || pet.getDevice() == null || !UserInforUtils.checkFitOTAStateById(this.curPet.getDevice().getId())) ? R.drawable.btn_setting : R.drawable.btn_setting_with_notify_flag, this, R.drawable.posts_share, this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setUpdateDailyDetailFailedFlag() {
        Map<String, DailyDetailItem> map = this.mDailyDataMap;
        if (map == null || map.size() == 0 || this.mDailyDataMap.get(this.mCurrentDay) == null) {
            return;
        }
        this.mDailyDataMap.get(this.mCurrentDay).setlastest(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setDataPromptView() {
        int i = deviceConnectState;
        if (i != 12 && i != 13) {
            ((TextView) this.mPetkitDailyView.findViewById(R.id.data_prompt_status_title)).setText(R.string.Hint_analyse_data_not_newest);
            ((TextView) this.mPetkitDailyView.findViewById(R.id.data_prompt_status_desc)).setText("");
            TextView textView = (TextView) this.mPetkitDailyView.findViewById(R.id.data_prompt_btn);
            textView.setText(R.string.Retry);
            textView.setOnClickListener(this);
            this.mPetkitDailyView.findViewById(R.id.data_prompt_status_img).setVisibility(0);
            this.mPetkitDailyView.findViewById(R.id.data_prompt_loadding).setVisibility(8);
            return;
        }
        ((TextView) this.mPetkitDailyView.findViewById(R.id.data_prompt_status_title)).setText(R.string.Hint_network_failed);
        ((TextView) this.mPetkitDailyView.findViewById(R.id.data_prompt_status_desc)).setText(R.string.Hint_analyse_data_failed);
        TextView textView2 = (TextView) this.mPetkitDailyView.findViewById(R.id.data_prompt_btn);
        textView2.setText(R.string.Retry);
        textView2.setOnClickListener(this);
        this.mPetkitDailyView.findViewById(R.id.data_prompt_status_img).setVisibility(0);
        this.mPetkitDailyView.findViewById(R.id.data_prompt_loadding).setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setRefreshFailView() {
        this.mPetkitDailyView.findViewById(R.id.petkit_data_detail_view).setVisibility(8);
        findViewById(R.id.petkit_data_empty_view).setVisibility(0);
        TextView textView = (TextView) findViewById(R.id.list_empty_text);
        textView.setVisibility(0);
        textView.setText(R.string.Hint_network_failed);
        ImageView imageView = (ImageView) findViewById(R.id.list_empty_image);
        imageView.setVisibility(0);
        imageView.setImageResource(R.drawable.default_list_empty_icon);
        Button button = (Button) findViewById(R.id.list_empty_btn);
        button.setVisibility(0);
        button.setText(R.string.Retry);
        button.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.fit.PetkitDetailActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PetkitDetailActivity.this.findViewById(R.id.petkit_detail_loading_view).setVisibility(0);
                PetkitDetailActivity petkitDetailActivity = PetkitDetailActivity.this;
                petkitDetailActivity.syncChangedActivityDailyDetail(petkitDetailActivity.curPet.getId(), (String) PetkitDetailActivity.this.mWeekDays.get(0), (String) PetkitDetailActivity.this.mWeekDays.get(PetkitDetailActivity.this.mWeekDays.size() - 1), null);
            }
        });
        setTitleRight2ImageViewVisibility(-1, 8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0188  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0048  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void initPetkitDailyDetailView() {
        /*
            Method dump skipped, instruction units count: 2038
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.petkit.android.activities.fit.PetkitDetailActivity.initPetkitDailyDetailView():void");
    }

    private void initPetkitDailyDetailData() {
        updateLocalDailyDetailItems(Integer.valueOf(CommonUtils.getDateStringByOffset(-6)).intValue(), Integer.valueOf(CommonUtils.getDateStringByOffset(0)).intValue(), new String[0]);
    }

    public void startScan() {
        Pet pet = this.curPet;
        if (pet == null || pet.getDevice() == null) {
            return;
        }
        if (CommonUtils.getSysIntMap(CommonUtils.getAppContext(), Consts.SHARED_SYSTEM_TIME_VALID_STATE) != 0) {
            showLongToast(R.string.BLEUI_ble_system_time_error);
        } else if (deviceConnectState == -1) {
            startSyncDevice();
        }
    }

    private void backToday() {
        this.mCurrentDay = DateUtil.date2Str(this.todayDate, DateUtil.DATE_FORMAT_7);
        this.isToday = true;
        initPetkitDailyDetailView();
    }

    @Override // com.petkit.android.activities.base.BaseActivity, android.view.View.OnClickListener
    public void onClick(View view) {
        Pet pet;
        if (this.isThisActivityOnTop) {
            int id = view.getId();
            if (id == R.id.data_activity) {
                Map<String, DailyDetailItem> map = this.mDailyDataMap;
                if (map == null || map.get(this.mCurrentDay) == null || !this.mDailyDataMap.get(this.mCurrentDay).isInit()) {
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString(Constants.EXTRA_Characteristic_TYPE, Consts.Characteristic_TYPE_CALORIE);
                bundle.putString(Constants.EXTRA_Characteristic_DAY, this.mDailyDataMap.get(this.mCurrentDay).getDay());
                bundle.putSerializable(Constants.EXTRA_DOG, this.curPet);
                bundle.putInt(Constants.EXTRA_STRING_ID, 0);
                bundle.putBoolean(Constants.EXTRA_BOOLEAN, this.pound);
                startActivityWithData(CharacteristicDetailActivity.class, bundle, false);
                return;
            }
            if (id == R.id.data_consumption) {
                Map<String, DailyDetailItem> map2 = this.mDailyDataMap;
                if (map2 == null || map2.get(this.mCurrentDay) == null || !this.mDailyDataMap.get(this.mCurrentDay).isInit()) {
                    return;
                }
                Bundle bundle2 = new Bundle();
                bundle2.putSerializable(Constants.EXTRA_DOG, this.curPet);
                bundle2.putString(Constants.EXTRA_Characteristic_TYPE, Consts.Characteristic_TYPE_CONSUME);
                bundle2.putString(Constants.EXTRA_Characteristic_DAY, this.mDailyDataMap.get(this.mCurrentDay).getDay());
                bundle2.putInt(Constants.EXTRA_STRING_ID, 2);
                bundle2.putBoolean(Constants.EXTRA_BOOLEAN, this.pound);
                startActivityWithData(CharacteristicDetailActivity.class, bundle2, false);
                return;
            }
            if (id == R.id.data_rest) {
                Map<String, DailyDetailItem> map3 = this.mDailyDataMap;
                if (map3 == null || map3.get(this.mCurrentDay) == null || !this.mDailyDataMap.get(this.mCurrentDay).isInit()) {
                    return;
                }
                Bundle bundle3 = new Bundle();
                bundle3.putString(Constants.EXTRA_Characteristic_TYPE, Consts.Characteristic_TYPE_REST);
                bundle3.putString(Constants.EXTRA_Characteristic_DAY, this.mDailyDataMap.get(this.mCurrentDay).getDay());
                bundle3.putSerializable(Constants.EXTRA_DOG, this.curPet);
                bundle3.putInt(Constants.EXTRA_STRING_ID, 1);
                startActivityWithData(CharacteristicDetailActivity.class, bundle3, false);
                return;
            }
            if (id == R.id.petkit_connect) {
                if (deviceConnectState != -1) {
                    showLongToast(R.string.Error_ble_is_using);
                    return;
                }
                if (this.curPet != null) {
                    startActivityForResult(DeviceAddPetSelectListActivity.class, 390);
                    overridePendingTransition(R.anim.slide_in_from_top, R.anim.slide_none);
                    return;
                } else {
                    Bundle bundle4 = new Bundle();
                    bundle4.putBoolean(Constants.EXTRA_BOOLEAN, true);
                    bundle4.putBoolean(Constants.EXTRA_FROM_DEVICE, true);
                    startActivity(PetCreateActivity.class, false);
                    return;
                }
            }
            if (id == R.id.petkit_get) {
                startActivity(WebviewActivity.newIntent(this, getString(R.string.Title_PETKIT_store), ApiTools.getWebUrlByKey("buy_petkit_p")));
                return;
            }
            if (id == R.id.title_right_image1) {
                Intent intent = new Intent(this, (Class<?>) PetkitDeviceInforActivity.class);
                intent.putExtra(Constants.EXTRA_DOG, this.curPet);
                startActivity(intent);
                return;
            }
            if (id == R.id.petkit_sync_btn_view) {
                if (CommonUtils.getSysMap(Consts.SHARED_DEVICE_BATTERY_LOW_FLAG).contains("&" + this.curPet.getDevice().getId())) {
                    new LowBatteryDialog(this, this.curPet.getDevice()).show();
                    this.lowBatteryHint.setVisibility(8);
                    return;
                } else {
                    if (this.petkitSyncBtnView.getVisibility() == 0 && (pet = this.curPet) != null && pet.getDevice() != null && deviceConnectState == -1 && this.canStartSync) {
                        scrollTo(this.scrollView, 33);
                        backToday();
                        startScan();
                        getWindow().addFlags(128);
                        return;
                    }
                    return;
                }
            }
            if (id == R.id.data_prompt_btn) {
                if (new File(FileUtils.getAppCacheActivityDataDirPath() + this.curPet.getId() + "-" + Consts.TEMP_ACTIVITY_DATA_FILE_NAME).exists()) {
                    syncActivityDataToService();
                } else {
                    syncChangedActivityDailyDetail(this.curPet.getId(), CommonUtils.getDateStringByOffset(-6), CommonUtils.getDateStringByOffset(0), null);
                }
                ((TextView) this.mPetkitDailyView.findViewById(R.id.data_prompt_btn)).setText("");
                ((TextView) this.mPetkitDailyView.findViewById(R.id.data_prompt_status_desc)).setText("");
                ((TextView) this.mPetkitDailyView.findViewById(R.id.data_prompt_status_title)).setText("");
                this.mPetkitDailyView.findViewById(R.id.data_prompt_loadding).setVisibility(0);
                this.mPetkitDailyView.findViewById(R.id.data_prompt_status_img).setVisibility(8);
                return;
            }
            if (id == R.id.petkit_report_rl) {
                Map<String, DailyDetailItem> map4 = this.mDailyDataMap;
                if (map4 == null || map4.get(this.mCurrentDay) == null) {
                    return;
                }
                Bundle bundle5 = new Bundle();
                bundle5.putString(Constants.EXTRA_Characteristic_DAY, this.mDailyDataMap.get(this.mCurrentDay).getDay());
                bundle5.putSerializable(Constants.EXTRA_DOG, this.curPet);
                Intent intent2 = new Intent(this, (Class<?>) PetkitReportDialogActivity.class);
                intent2.putExtra(Constants.EXTRA_Characteristic_DAY, this.mDailyDataMap.get(this.mCurrentDay).getDay());
                intent2.putExtra(Constants.EXTRA_DOG, this.curPet);
                startActivityForResult(intent2, ACTIVITY_RESULT_REPORT);
                overridePendingTransition(R.anim.slide_in_from_bottom, 0);
                return;
            }
            if (id == R.id.low_battery_hint) {
                new LowBatteryDialog(this, this.curPet.getDevice()).show();
                this.lowBatteryHint.setVisibility(8);
                return;
            }
            if (id == R.id.title_right_image2) {
                SharePopMenu sharePopMenu = new SharePopMenu(this, this.callbackManager);
                sharePopMenu.setData(2, getString(R.string.Activity_share_text_format, this.curPet.getName()), this.curPet, this.mCurrentDay);
                sharePopMenu.show();
                return;
            }
            if (id == R.id.title_tab) {
                this.viewPager.setCurrentItem(this.pagerCount - 1);
                this.mCurrentDay = DateUtil.date2Str(this.todayDate, DateUtil.DATE_FORMAT_7);
                this.currentPosition = this.toDayPosition;
                backToday();
                LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(Constants.BROADCAST_MSG_TIMEGRIDVIEW_POSITION));
                return;
            }
            if (id == R.id.pet_sync_cancel) {
                BaseBluetoothLeUtils baseBluetoothLeUtils = this.mBluetoothLeUtils;
                if (baseBluetoothLeUtils != null) {
                    baseBluetoothLeUtils.stop();
                } else {
                    Intent intent3 = new Intent(BLEConsts.BROADCAST_ACTION);
                    intent3.putExtra(BLEConsts.EXTRA_ACTION, 2);
                    LocalBroadcastManager.getInstance(this).sendBroadcast(intent3);
                }
                this.mCancelTextView.setVisibility(8);
                deviceConnectState = -1;
                startSubtitleAnimation(2);
            }
        }
    }

    private View initCaloriesGraphView(List<Integer> list) {
        BarGraphView barGraphView = new BarGraphView(this, "", this, new View.OnClickListener() { // from class: com.petkit.android.activities.fit.PetkitDetailActivity.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (PetkitDetailActivity.this.mDailyDataMap == null || PetkitDetailActivity.this.mDailyDataMap.get(PetkitDetailActivity.this.mCurrentDay) == null || !((DailyDetailItem) PetkitDetailActivity.this.mDailyDataMap.get(PetkitDetailActivity.this.mCurrentDay)).isInit()) {
                    return;
                }
                if (PetkitDetailActivity.this.isSamsungBLE || PetkitDetailActivity.deviceConnectState == -1) {
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.EXTRA_Characteristic_TYPE, Consts.Characteristic_TYPE_CALORIE);
                    bundle.putString(Constants.EXTRA_Characteristic_DAY, ((DailyDetailItem) PetkitDetailActivity.this.mDailyDataMap.get(PetkitDetailActivity.this.mCurrentDay)).getDay());
                    bundle.putSerializable(Constants.EXTRA_DOG, PetkitDetailActivity.this.curPet);
                    bundle.putInt(Constants.EXTRA_STRING_ID, 0);
                    PetkitDetailActivity.this.startActivityWithData(CharacteristicDetailActivity.class, bundle, false);
                }
            }
        });
        barGraphView.getGraphViewStyle().setHorizontalLabelsColor(CommonUtils.getColorById(R.color.white));
        barGraphView.setManualYAxisBounds(225000.0d, 0.0d);
        barGraphView.setHorizontalLabels(new String[]{"0", com.tencent.connect.common.Constants.VIA_SHARE_TYPE_INFO, "12", "18", com.tencent.connect.common.Constants.VIA_REPORT_TYPE_CHAT_AIO});
        GraphViewSeries.GraphViewSeriesStyle graphViewSeriesStyle = new GraphViewSeries.GraphViewSeriesStyle(CommonUtils.getColorById(R.color.graph_view_gray_pool), 0);
        graphViewSeriesStyle.setQuad(true);
        graphViewSeriesStyle.color = CommonUtils.getColorById(R.color.white);
        graphViewSeriesStyle.setGradientColors(new int[]{CommonUtils.getColorById(R.color.white), CommonUtils.getColorById(R.color.white)});
        GraphViewSeries graphViewSeries = new GraphViewSeries("", graphViewSeriesStyle, new GraphView.GraphViewData[0]);
        if (list == null) {
            barGraphView.addSeries(graphViewSeries);
        } else {
            for (int i = 0; i < list.size(); i++) {
                graphViewSeries.appendData(new GraphView.GraphViewData(i, list.get(i).intValue()), false);
            }
            barGraphView.addSeries(graphViewSeries);
        }
        return barGraphView;
    }

    private View initRestGrapView(DailyDetailItem dailyDetailItem) {
        RectGraphView rectGraphView = new RectGraphView(this, "", this, new View.OnClickListener() { // from class: com.petkit.android.activities.fit.PetkitDetailActivity.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (((DailyDetailItem) PetkitDetailActivity.this.mDailyDataMap.get(PetkitDetailActivity.this.mCurrentDay)).isInit()) {
                    if (PetkitDetailActivity.this.isSamsungBLE || PetkitDetailActivity.deviceConnectState == -1) {
                        Bundle bundle = new Bundle();
                        bundle.putString(Constants.EXTRA_Characteristic_DAY, ((DailyDetailItem) PetkitDetailActivity.this.mDailyDataMap.get(PetkitDetailActivity.this.mCurrentDay)).getDay());
                        bundle.putSerializable(Constants.EXTRA_DOG, PetkitDetailActivity.this.curPet);
                        bundle.putInt(Constants.EXTRA_STRING_ID, 1);
                        PetkitDetailActivity.this.startActivityWithData(CharacteristicDetailActivity.class, bundle, false);
                    }
                }
            }
        });
        rectGraphView.setHorizontalLabels(new String[]{"0", com.tencent.connect.common.Constants.VIA_SHARE_TYPE_INFO, "12", "18", com.tencent.connect.common.Constants.VIA_REPORT_TYPE_CHAT_AIO});
        rectGraphView.getGraphViewStyle().setHorizontalLabelsColor(CommonUtils.getColorById(R.color.white));
        if (dailyDetailItem != null && dailyDetailItem.getDeepsleeps() != null && dailyDetailItem.getDeepsleeps().size() > 0) {
            GraphViewSeries.GraphViewSeriesStyle graphViewSeriesStyle = new GraphViewSeries.GraphViewSeriesStyle(CommonUtils.getColorById(checkCurrentPetIsDog() ? R.color.sleep_deep_color : R.color.sleep_light_color), 0);
            checkCurrentPetIsDog();
            graphViewSeriesStyle.setWeight(0.9f);
            GraphViewSeries graphViewSeries = new GraphViewSeries("", graphViewSeriesStyle, new GraphView.GraphViewData[0]);
            for (int i = 0; i < dailyDetailItem.getDeepsleeps().size(); i++) {
                graphViewSeries.appendData(new GraphView.GraphViewData(dailyDetailItem.getDeepsleeps().get(i).get(0).intValue(), dailyDetailItem.getDeepsleeps().get(i).get(1).intValue()), false);
            }
            rectGraphView.addSeries(graphViewSeries);
        }
        if (dailyDetailItem != null && dailyDetailItem.getLightsleeps() != null && dailyDetailItem.getLightsleeps().size() > 0) {
            GraphViewSeries.GraphViewSeriesStyle graphViewSeriesStyle2 = new GraphViewSeries.GraphViewSeriesStyle(CommonUtils.getColorById(R.color.sleep_light_color), 0);
            checkCurrentPetIsDog();
            graphViewSeriesStyle2.setWeight(0.9f);
            GraphViewSeries graphViewSeries2 = new GraphViewSeries("", graphViewSeriesStyle2, new GraphView.GraphViewData[0]);
            for (int i2 = 0; i2 < dailyDetailItem.getLightsleeps().size(); i2++) {
                graphViewSeries2.appendData(new GraphView.GraphViewData(dailyDetailItem.getLightsleeps().get(i2).get(0).intValue(), dailyDetailItem.getLightsleeps().get(i2).get(1).intValue()), false);
            }
            rectGraphView.addSeries(graphViewSeries2);
        }
        return rectGraphView;
    }

    public void syncActivityDataToService() {
        Pet pet;
        if (this.curPet != null) {
            int i = deviceConnectState;
            if (i == -1 || 3 == i) {
                String fileToString = FileUtils.readFileToString(new File(FileUtils.getAppCacheActivityDataDirPath() + this.curPet.getId() + "-" + Consts.TEMP_ACTIVITY_DATA_FILE_NAME));
                if (isEmpty(fileToString)) {
                    PetkitLog.d("syncActivityDataToService null, no data need to send");
                    return;
                }
                final String id = this.curPet.getId();
                HashMap map = new HashMap();
                map.put("petId", id);
                map.put("data", fileToString);
                if (this.mDevice != null && (pet = this.curPet) != null && pet.getDevice() != null) {
                    Device device = new Device();
                    device.setFirmware(this.mDevice.getFirmware());
                    device.setHardware(this.mDevice.getHardware());
                    device.setExtra(this.mDevice.getExtra());
                    device.setBattery(this.mDevice.getBattery());
                    device.setId(this.curPet.getDevice().getId());
                    map.put(DeviceRequestsHelper.DEVICE_INFO_DEVICE, new Gson().toJson(device));
                    if (this.mDevice.getVoltage() > 0.0f) {
                        map.put("voltage", this.mDevice.getVoltage() + "");
                    }
                }
                post(ApiTools.SAMPLE_API_ACTIVITY_SAVE, map, new AsyncHttpRespHandler(this) { // from class: com.petkit.android.activities.fit.PetkitDetailActivity.8
                    @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
                    public void onSuccess(int i2, Header[] headerArr, byte[] bArr) {
                        super.onSuccess(i2, headerArr, bArr);
                        ResultStringArrayRsp resultStringArrayRsp = (ResultStringArrayRsp) this.gson.fromJson(this.responseResult, ResultStringArrayRsp.class);
                        if (resultStringArrayRsp.getError() != null) {
                            PetkitDetailActivity.this.setDataPromptView();
                            LogcatStorageHelper.addLog("syncActivityDataToService dogId: " + PetkitDetailActivity.this.curPet.getId() + "failed: " + resultStringArrayRsp.getError().getMsg());
                            return;
                        }
                        LogcatStorageHelper.addLog("activity data upload success ");
                        new File(FileUtils.getAppCacheActivityDataDirPath() + id + "-" + Consts.TEMP_ACTIVITY_DATA_FILE_NAME).delete();
                        PetkitDetailActivity.this.syncChangedActivityDailyDetail(id, null, null, resultStringArrayRsp.getResult());
                    }

                    @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
                    public void onFailure(int i2, Header[] headerArr, byte[] bArr, Throwable th) {
                        super.onFailure(i2, headerArr, bArr, th);
                        LogcatStorageHelper.addLog("activity data upload failure ");
                        PetkitDetailActivity.this.setDataPromptView();
                    }

                    @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
                    public void onFinish() {
                        super.onFinish();
                        LogcatStorageHelper.addLog("activity data upload finish ");
                    }
                }, false);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void syncChangedActivityDailyDetail(final String str, final String str2, final String str3, final String[] strArr) {
        if (isEmpty(str2) && isEmpty(str3) && (strArr == null || strArr.length == 0)) {
            return;
        }
        HashMap map = new HashMap();
        if (!isEmpty(str2) && !isEmpty(str3)) {
            map.put("since", str2);
            map.put("until", str3);
        } else {
            StringBuilder sb = new StringBuilder(strArr[0]);
            for (int i = 1; i < strArr.length; i++) {
                sb.append(ChineseToPinyinResource.Field.COMMA);
                sb.append(strArr[i]);
            }
            map.put("days", sb.toString());
        }
        map.put("version", "5");
        map.put("petId", str);
        map.put("withdata", ServerProtocol.DIALOG_RETURN_SCOPES_TRUE);
        map.put("dataFrequency", "900");
        cancenRequest(true);
        post(ApiTools.SAMPLE_API_ACTIVITY_DAILYDETAIL, map, new AsyncHttpRespHandler(this, false) { // from class: com.petkit.android.activities.fit.PetkitDetailActivity.9
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFinish() throws Throwable {
                super.onFinish();
                PetkitDetailActivity.this.findViewById(R.id.petkit_detail_loading_view).setVisibility(8);
                LogcatStorageHelper.uploadLog();
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i2, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i2, headerArr, bArr);
                DailyDetailRsp dailyDetailRsp = (DailyDetailRsp) this.gson.fromJson(this.responseResult, DailyDetailRsp.class);
                if (dailyDetailRsp.getError() != null) {
                    if (PetkitDetailActivity.this.mDailyDataMap == null || PetkitDetailActivity.this.mDailyDataMap.size() <= 0 || PetkitDetailActivity.this.mDailyDataMap.get(PetkitDetailActivity.this.mCurrentDay) == null) {
                        PetkitDetailActivity.this.setRefreshFailView();
                        return;
                    } else {
                        PetkitDetailActivity.this.setUpdateDailyDetailFailedFlag();
                        PetkitDetailActivity.this.setDataPromptView();
                        return;
                    }
                }
                if (dailyDetailRsp.getResult() != null) {
                    DailyDetailUtils.updateDailyDetailItems(str, dailyDetailRsp.getResult(), str2, str3);
                    if (str.equals(PetkitDetailActivity.this.curPet.getId())) {
                        if (PetkitDetailActivity.this.isEmpty(str2) || PetkitDetailActivity.this.isEmpty(str3)) {
                            PetkitDetailActivity.this.updateLocalDailyDetailItems(0, 0, strArr);
                        } else {
                            PetkitDetailActivity.this.updateLocalDailyDetailItems(Integer.valueOf(str2).intValue(), Integer.valueOf(str3).intValue(), new String[0]);
                        }
                        PetkitDetailActivity.this.initPetkitDailyDetailView();
                    }
                }
            }

            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onFailure(int i2, Header[] headerArr, byte[] bArr, Throwable th) {
                super.onFailure(i2, headerArr, bArr, th);
                if (PetkitDetailActivity.this.mDailyDataMap == null || PetkitDetailActivity.this.mDailyDataMap.size() <= 0 || PetkitDetailActivity.this.mDailyDataMap.get(PetkitDetailActivity.this.mCurrentDay) == null) {
                    PetkitDetailActivity.this.setRefreshFailView();
                } else {
                    PetkitDetailActivity.this.setUpdateDailyDetailFailedFlag();
                    PetkitDetailActivity.this.setDataPromptView();
                }
            }
        }, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateLocalDailyDetailItems(int i, int i2, String... strArr) {
        String[] strArr2 = strArr;
        if (this.mDailyDataMap == null) {
            this.mDailyDataMap = new HashMap();
        }
        if (i != 0 || i2 != 0) {
            for (int dayAfterOffset = i; dayAfterOffset <= i2; dayAfterOffset = CommonUtils.getDayAfterOffset(dayAfterOffset, 1)) {
                if (((DailyDetailItem) Select.from(DailyDetailItem.class).where(Condition.prop("dogId").eq(this.curPet.getId()), Condition.prop("day").eq(String.valueOf(dayAfterOffset))).first()) != null || this.mDailyDataMap.size() != 0) {
                    DailyDetailItem dailyDetailItem = DailyDetailUtils.getDailyDetailItem(this.curPet.getId(), String.valueOf(dayAfterOffset));
                    this.mDailyDataMap.put(dayAfterOffset + "", dailyDetailItem);
                }
            }
        } else if (strArr2 == null || strArr2.length == 0) {
            for (String str : this.mDailyDataMap.keySet()) {
                this.mDailyDataMap.put(str, DailyDetailUtils.getDailyDetailItem(this.curPet.getId(), str));
            }
        } else {
            int length = strArr2.length;
            int i3 = 0;
            while (i3 < length) {
                String str2 = strArr2[i3];
                if (((DailyDetailItem) Select.from(DailyDetailItem.class).where(Condition.prop("dogId").eq(this.curPet.getId()), Condition.prop("day").eq(str2)).first()) != null) {
                    DailyDetailItem dailyDetailItem2 = DailyDetailUtils.getDailyDetailItem(this.curPet.getId(), str2);
                    SugarRecord.save(dailyDetailItem2);
                    this.mDailyDataMap.put(str2, dailyDetailItem2);
                }
                i3++;
                strArr2 = strArr;
            }
        }
        if (this.mDailyDataMap.size() == 0) {
            this.mDailyDataMap.put(i2 + "", DailyDetailUtils.getDailyDetailItem(this.curPet.getId(), String.valueOf(i2)));
        }
    }

    private void getSupportDevices() {
        HashMap map = new HashMap();
        map.put("key", Consts.ANDROID_SUPPORTED_DEVICES);
        post(ApiTools.SAMPLE_API_APP_STATICDATA, map, new AsyncHttpRespHandler(this) { // from class: com.petkit.android.activities.fit.PetkitDetailActivity.10
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                StaticDataRsp staticDataRsp = (StaticDataRsp) this.gson.fromJson(this.responseResult, StaticDataRsp.class);
                if (staticDataRsp.getError() != null || staticDataRsp.getResult() == null || staticDataRsp.getResult().getData() == null || staticDataRsp.getResult().getData().length() <= 0) {
                    return;
                }
                CommonUtils.addSysMap(CommonUtils.getAppContext(), Consts.SHARED_ANDROID_DEVICE_LIST, staticDataRsp.getResult().getData());
            }
        }, false);
    }

    private void refreshSubtitle() {
        if (this.curPet != null) {
            this.petTitle.setVisibility(0);
            refreshPetTitleView(this.curPet);
            if (this.curPet.getDevice() == null) {
                setTitleRight2ImageViewVisibility(8, 8);
                this.petTitle.setVisibility(8);
                setTitle(R.string.Homepage_petkit);
                return;
            }
            Map<String, DailyDetailItem> map = this.mDailyDataMap;
            DailyDetailItem dailyDetailItem = (map == null || map.size() <= 0) ? null : this.mDailyDataMap.get(this.mCurrentDay);
            if (dailyDetailItem == null || (!dailyDetailItem.isInit() && dailyDetailItem.getData().get(0).intValue() == -1)) {
                setTitleRight2ImageViewVisibility(0, 8);
            } else {
                setTitleRight2ImageViewVisibility(0, 0);
            }
            if (this.curPet.getDevice() != null) {
                if (CommonUtils.getSysMap(Consts.SHARED_DEVICE_BATTERY_LOW_FLAG).contains("&" + this.curPet.getDevice().getId())) {
                    this.lowBatteryHint.setVisibility(0);
                    return;
                }
            }
            this.lowBatteryHint.setVisibility(8);
            return;
        }
        this.petTitle.setVisibility(8);
        setTitle(R.string.Homepage_petkit);
        setTitleRight2ImageViewVisibility(8, 8);
    }

    @Override // com.jjoe64.graphview.IInterceptListener
    public void changeInterceptState(boolean z) {
        this.scrollView.setScrollingEnabled(!z);
    }

    private void registerBoradcastReceiver() {
        this.mBroadcastReceiver = new BroadcastReceiver() { // from class: com.petkit.android.activities.fit.PetkitDetailActivity.11
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals("com.petkit.android.updateDogFood")) {
                    Food food = (Food) intent.getSerializableExtra(Constants.EXTRA_SELECT_FOOD);
                    PrivateFood privateFood = (PrivateFood) intent.getSerializableExtra(Constants.EXTRA_PRIVATE_FOOD);
                    if (food == null) {
                        if (privateFood != null) {
                            PetkitDetailActivity.this.onRefreshDogFood((privateFood.getId() + "/") + privateFood.getName());
                            return;
                        }
                        return;
                    }
                    String str = food.getId() + "/";
                    if (food.getBrand() != null) {
                        str = str + food.getBrand().getName() + "/";
                    }
                    PetkitDetailActivity.this.onRefreshDogFood(str + food.getName());
                    return;
                }
                if (intent.getAction().equals(Constants.BROADCAST_MSG_UPDATE_DOG) || intent.getAction().equals(Constants.BROADCAST_MSG_DELETE_DOG)) {
                    Pet pet = (Pet) intent.getSerializableExtra(Constants.EXTRA_DOG);
                    String stringExtra = intent.getStringExtra(Constants.BROADCAST_MSG_DELETE_DOG);
                    if (PetkitDetailActivity.this.curPet == null || PetkitDetailActivity.this.curPet.getId().equals(stringExtra)) {
                        PetkitDetailActivity.this.finish();
                        return;
                    } else {
                        if (pet != null) {
                            if (pet.getDevice() != null) {
                                PetkitDetailActivity.this.changeCurrentDog(pet);
                                return;
                            } else {
                                PetkitDetailActivity.this.finish();
                                return;
                            }
                        }
                        return;
                    }
                }
                if (intent.getAction().equals(BLEConsts.BROADCAST_PROGRESS)) {
                    if (PetkitDetailActivity.deviceConnectState == -1) {
                        return;
                    }
                    int intExtra = intent.getIntExtra(BLEConsts.EXTRA_PROGRESS, 0);
                    String stringExtra2 = intent.getStringExtra(BLEConsts.EXTRA_DATA);
                    if (PetkitDetailActivity.this.isThisActivityOnTop) {
                        PetkitDetailActivity.this.updateBLESyncProgress(intExtra, stringExtra2);
                        PetkitLog.d(String.format("BROADCAST_PROGRESS: op = %s, data = %s", Integer.valueOf(intExtra), stringExtra2));
                        return;
                    }
                    return;
                }
                if (intent.getAction().equals(BLEConsts.BROADCAST_ERROR)) {
                    if (PetkitDetailActivity.deviceConnectState == -1 || !PetkitDetailActivity.this.isThisActivityOnTop) {
                        return;
                    }
                    if (intent.getIntExtra(BLEConsts.EXTRA_DATA, 0) != 4114) {
                        PetkitDetailActivity.this.changeDeviceConnectState(5);
                        return;
                    } else if (!intent.getBooleanExtra(BLEConsts.EXTRA_BOOLEAN_LOGOUT, false)) {
                        PetkitDetailActivity.this.changeDeviceConnectState(12);
                        return;
                    } else {
                        new AlertDialog.Builder(PetkitDetailActivity.this).setCancelable(false).setTitle(PetkitDetailActivity.this.getString(R.string.Prompt)).setMessage(intent.getStringExtra(BLEConsts.EXTRA_LOG_MESSAGE)).setPositiveButton(PetkitDetailActivity.this.getString(R.string.OK), new DialogInterface.OnClickListener() { // from class: com.petkit.android.activities.fit.PetkitDetailActivity.11.1
                            @Override // android.content.DialogInterface.OnClickListener
                            public void onClick(DialogInterface dialogInterface, int i) {
                                CommonUtils.addSysMap(PetkitDetailActivity.this, Consts.SHARED_SESSION_ID, "");
                                Intent intent2 = new Intent();
                                intent2.setClass(PetkitDetailActivity.this, NormalLoginActivity.class);
                                intent2.putExtra(Constants.EXTRA_CAN_GO_BACK, false);
                                PetkitDetailActivity.this.startActivity(intent2);
                                LocalBroadcastManager.getInstance(PetkitDetailActivity.this).sendBroadcast(new Intent(Constants.BROADCAST_MSG_CLOSE_MAINACTIVITY));
                                LocalBroadcastManager.getInstance(PetkitDetailActivity.this).sendBroadcast(new Intent("com.petkit.android.exit"));
                            }
                        }).create().show();
                        return;
                    }
                }
                if (intent.getAction().equals(BLEConsts.BROADCAST_LOG)) {
                    if (PetkitDetailActivity.this.isThisActivityOnTop) {
                        PetkitLog.d(String.format("BROADCAST_LOG: data = %s", intent.getStringExtra(BLEConsts.EXTRA_LOG_MESSAGE)));
                    }
                } else if (intent.getAction().equals(Constants.BROADCAST_MSG_UPDATE_DEVICE_UPDATE_NOTIFICATION)) {
                    PetkitDetailActivity.this.setDeviceUpdateFlag();
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.petkit.android.updateDogFood");
        intentFilter.addAction(Constants.BROADCAST_MSG_UPDATE_DOG);
        intentFilter.addAction(Constants.BROADCAST_MSG_CLOSE_BLE_RECEIVE);
        intentFilter.addAction(BLEConsts.BROADCAST_PROGRESS);
        intentFilter.addAction(BLEConsts.BROADCAST_ERROR);
        intentFilter.addAction(BLEConsts.BROADCAST_LOG);
        intentFilter.addAction(Constants.BROADCAST_MSG_UPDATE_DEVICE_UPDATE_NOTIFICATION);
        LocalBroadcastManager.getInstance(this).registerReceiver(this.mBroadcastReceiver, intentFilter);
    }

    private void unregisterBroadcastReceiver() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(this.mBroadcastReceiver);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateBLESyncProgress(int i, String str) {
        Device device;
        PetkitLog.d("updateBLESyncProgress progress= " + i + "////data=" + str);
        if (i == -25) {
            changeDeviceConnectState(3);
            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(Constants.BROADCAST_MSG_DEVICE_UPDATE));
        }
        if (i == -11) {
            PetkitLog.d("battery: " + str);
            if (isEmpty(str) || (device = this.mDevice) == null) {
                return;
            }
            try {
                device.setBattery(Integer.valueOf(str).intValue());
                this.curPet.getDevice().setBattery(this.mDevice.getBattery());
                this.curPet.getDevice().setFirmware(this.mDevice.getFirmware());
                this.curPet.getDevice().setHardware(this.mDevice.getHardware());
                UserInforUtils.updateDogInformation(this.curPet, 3);
                return;
            } catch (NumberFormatException unused) {
                LogcatStorageHelper.addLog("read battery error, value is " + str);
                return;
            }
        }
        if (i == -5) {
            changeDeviceConnectState(5);
            return;
        }
        if (i == -1) {
            changeDeviceConnectState(1);
            return;
        }
        if (i == -9) {
            if (isEmpty(str)) {
                return;
            }
            this.mDevice = (Device) new Gson().fromJson(str, Device.class);
            return;
        }
        if (i == -8) {
            changeDeviceConnectState(0);
            return;
        }
        switch (i) {
            case BLEConsts.PROGRESS_UPLOAD_ACTIVITY_DATA /* -23 */:
                changeDeviceConnectState(10);
                break;
            case BLEConsts.PROGRESS_BLE_START /* -22 */:
            case -21:
                break;
            default:
                switch (i) {
                    case -16:
                        changeDeviceConnectState(2);
                        break;
                    case -15:
                        changeDeviceConnectState(8);
                        break;
                    case -14:
                        changeDeviceConnectState(4);
                        if (!isFinishing()) {
                            reportSyncIssue();
                        }
                        break;
                    case -13:
                        break;
                    default:
                        if (i >= 0 && i <= 100) {
                            changeDeviceConnectState(2, i);
                            break;
                        }
                        break;
                }
                break;
        }
    }

    private void startSyncDevice() {
        if (CommonUtils.getAndroidSDKVersion() >= 18) {
            if (getPackageManager().hasSystemFeature("android.hardware.bluetooth_le")) {
                Intent intent = new Intent(BLEConsts.BROADCAST_ACTION);
                intent.putExtra(BLEConsts.EXTRA_ACTION, 2);
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
                Intent intent2 = new Intent(this, (Class<?>) AndroidBLEActionService.class);
                intent2.putExtra(BLEConsts.EXTRA_ACTION, 1);
                intent2.putExtra(BLEConsts.EXTRA_DOG, this.curPet);
                intent2.putExtra(BLEConsts.EXTRA_URL_DATA_SAVE, ApiTools.SAMPLE_API_ACTIVITY_SAVE);
                intent2.putExtra(BLEConsts.EXTRA_URL_DAILY_DETAIL, ApiTools.SAMPLE_API_ACTIVITY_DAILYDETAIL);
                startService(intent2);
                deviceConnectState = 0;
                startSubtitleAnimation(1);
                return;
            }
            showLongToast(R.string.BLEUI_not_supported);
        }
        if (this.isSamsungBLE) {
            SSBluetoothLeUtils sSBluetoothLeUtils = new SSBluetoothLeUtils((Activity) this, (ISamsungBLEListener) this);
            this.mBluetoothLeUtils = sSBluetoothLeUtils;
            sSBluetoothLeUtils.start();
            deviceConnectState = 0;
            startSubtitleAnimation(1);
            return;
        }
        showLongToast(R.string.BLEUI_not_supported);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startSubtitleAnimation(int i) {
        startSubtitleAnimation(i, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startSubtitleAnimation(int i, final boolean z) {
        if (this.subTitleAnimState != i) {
            float y = this.petkitSyncBtnView.getY();
            if (this.petkitSyncBtnViewPositionY == 0.0f) {
                this.petkitSyncBtnViewPositionY = y;
            }
            if (i != 1) {
                if (i != 2) {
                    if (i != 3) {
                        if (i == 4) {
                            if (this.subTitleAnimState != 3) {
                                return;
                            }
                            findViewById(R.id.petkit_sync_gap_view).setVisibility(0);
                            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this.petkitSyncBtnView, Key.SCALE_X, 0.0f, 1.0f);
                            ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(this.petkitSyncBtnView, Key.SCALE_Y, 0.0f, 1.0f);
                            AnimatorSet animatorSet = new AnimatorSet();
                            animatorSet.setDuration(800L);
                            animatorSet.playTogether(objectAnimatorOfFloat, objectAnimatorOfFloat2);
                            animatorSet.start();
                            this.petkitSyncBtnView.setClickable(true);
                        }
                    } else {
                        if (this.subTitleAnimState == 1) {
                            return;
                        }
                        findViewById(R.id.petkit_sync_gap_view).setVisibility(8);
                        ObjectAnimator objectAnimatorOfFloat3 = ObjectAnimator.ofFloat(this.petkitSyncBtnView, Key.SCALE_X, 1.0f, 0.0f);
                        ObjectAnimator objectAnimatorOfFloat4 = ObjectAnimator.ofFloat(this.petkitSyncBtnView, Key.SCALE_Y, 1.0f, 0.0f);
                        AnimatorSet animatorSet2 = new AnimatorSet();
                        animatorSet2.setDuration(800L);
                        animatorSet2.playTogether(objectAnimatorOfFloat3, objectAnimatorOfFloat4);
                        animatorSet2.start();
                        this.petkitSyncBtnView.setClickable(false);
                    }
                } else {
                    if (this.subTitleAnimState != 1) {
                        return;
                    }
                    findViewById(R.id.petkit_detail_sync_view).setClickable(false);
                    startSyncingAnimation(2);
                    this.petkitSyncBtnView.findViewById(R.id.petkit_sync_btn_image).setVisibility(0);
                    float y2 = this.petkitSyncBtnView.getY();
                    ObjectAnimator objectAnimatorOfFloat5 = ObjectAnimator.ofFloat(this.petkitSyncBtnView, Key.SCALE_X, 3.0f, 1.0f);
                    ObjectAnimator objectAnimatorOfFloat6 = ObjectAnimator.ofFloat(this.petkitSyncBtnView, Key.SCALE_Y, 3.0f, 1.0f);
                    ObjectAnimator objectAnimatorOfFloat7 = ObjectAnimator.ofFloat(this.petkitSyncBtnView, "y", y2, this.petkitSyncBtnViewPositionY);
                    objectAnimatorOfFloat7.setInterpolator(new OvershootInterpolator());
                    objectAnimatorOfFloat7.addListener(new Animator.AnimatorListener() { // from class: com.petkit.android.activities.fit.PetkitDetailActivity.14
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
                            ((ImageView) PetkitDetailActivity.this.petkitSyncBtnView.findViewById(R.id.petkit_sync_btn_image)).setImageResource(R.drawable.sync_syncing);
                            PetkitDetailActivity.this.findViewById(R.id.petkit_detail_sync_view).setVisibility(8);
                            PetkitDetailActivity.this.findViewById(R.id.title_mask).setVisibility(8);
                            PetkitDetailActivity.this.updateLocalDailyDetailItems(0, 0, new String[0]);
                            PetkitDetailActivity.this.initPetkitDailyDetailView();
                            if (z) {
                                PetkitDetailActivity.this.startSubtitleAnimation(3);
                            }
                        }
                    });
                    AnimatorSet animatorSet3 = new AnimatorSet();
                    animatorSet3.setDuration(400L);
                    animatorSet3.playTogether(objectAnimatorOfFloat5, objectAnimatorOfFloat6);
                    animatorSet3.play(objectAnimatorOfFloat7).after(objectAnimatorOfFloat5);
                    animatorSet3.start();
                    findViewById(R.id.petkit_detail_sync_view).startAnimation(AnimationUtils.loadAnimation(this, R.anim.zoom_out));
                    findViewById(R.id.title_mask).startAnimation(AnimationUtils.loadAnimation(this, R.anim.zoom_out));
                }
            } else {
                if (this.subTitleAnimState == 3) {
                    return;
                }
                findViewById(R.id.petkit_detail_sync_view).setClickable(true);
                ((ImageView) this.petkitSyncBtnView.findViewById(R.id.petkit_sync_btn_image)).setImageResource(R.color.transparent);
                ObjectAnimator objectAnimatorOfFloat8 = ObjectAnimator.ofFloat(this.petkitSyncBtnView, "y", y, (y - (BaseApplication.displayMetrics.heightPixels - DeviceUtils.dpToPixel(this, 215.0f))) - (CommonUtils.getStatusHeight() / 2));
                ObjectAnimator objectAnimatorOfFloat9 = ObjectAnimator.ofFloat(this.petkitSyncBtnView, Key.SCALE_X, 1.0f, 3.0f);
                ObjectAnimator objectAnimatorOfFloat10 = ObjectAnimator.ofFloat(this.petkitSyncBtnView, Key.SCALE_Y, 1.0f, 3.0f);
                objectAnimatorOfFloat9.setInterpolator(new OvershootInterpolator());
                objectAnimatorOfFloat10.setInterpolator(new OvershootInterpolator());
                objectAnimatorOfFloat9.addListener(new Animator.AnimatorListener() { // from class: com.petkit.android.activities.fit.PetkitDetailActivity.12
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
                        PetkitDetailActivity.this.mCancelTextView.setVisibility(0);
                        PetkitDetailActivity.this.startSyncingAnimation(1);
                    }
                });
                AnimatorSet animatorSet4 = new AnimatorSet();
                animatorSet4.setDuration(400L);
                animatorSet4.playTogether(objectAnimatorOfFloat8);
                animatorSet4.play(objectAnimatorOfFloat9).after(objectAnimatorOfFloat8);
                animatorSet4.play(objectAnimatorOfFloat10).after(objectAnimatorOfFloat8);
                animatorSet4.start();
                new Handler().postDelayed(new Runnable() { // from class: com.petkit.android.activities.fit.PetkitDetailActivity.13
                    @Override // java.lang.Runnable
                    public void run() {
                        PetkitDetailActivity.this.findViewById(R.id.petkit_detail_sync_view).setVisibility(0);
                        PetkitDetailActivity.this.findViewById(R.id.petkit_detail_sync_view).startAnimation(AnimationUtils.loadAnimation(PetkitDetailActivity.this, R.anim.zoom_in));
                        PetkitDetailActivity petkitDetailActivity = PetkitDetailActivity.this;
                        petkitDetailActivity.setTitleMask(AnimationUtils.loadAnimation(petkitDetailActivity, R.anim.zoom_in), 0);
                    }
                }, 200L);
            }
            this.subTitleAnimState = i;
        }
    }

    @Override // android.view.View.OnTouchListener
    @SuppressLint({"ClickableViewAccessibility"})
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (deviceConnectState != -1) {
            return true;
        }
        if (motionEvent.getAction() == 0) {
            view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.touch_down));
        } else if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {
            view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.touch_up));
            if (motionEvent.getAction() == 1) {
                onClick(view);
            }
        }
        return true;
    }

    @Override // com.petkit.android.ble.samsung.ISamsungBLEListener
    public void onScanResultChange(DeviceInfo deviceInfo) {
        Pet pet = this.curPet;
        if (pet == null || deviceInfo == null || pet.getDevice() == null || deviceInfo.getDeviceId() != this.curPet.getDevice().getId() || deviceConnectState != 0) {
            return;
        }
        LogcatStorageHelper.addLog("find device, begin connect deviceId: " + this.curPet.getDevice().getId());
        this.mBluetoothLeUtils.onDeviceConnect(deviceInfo);
        runOnUiThread(new Runnable() { // from class: com.petkit.android.activities.fit.PetkitDetailActivity.15
            @Override // java.lang.Runnable
            public void run() {
                PetkitDetailActivity.this.changeDeviceConnectState(1);
            }
        });
    }

    private void stopGatt() {
        BaseBluetoothLeUtils baseBluetoothLeUtils = this.mBluetoothLeUtils;
        if (baseBluetoothLeUtils != null) {
            baseBluetoothLeUtils.stopGatt();
        }
    }

    private void syncDeviceData() {
        Pet pet = this.curPet;
        if (pet == null) {
            PetkitLog.d("syncDeviceData curPet == null");
            return;
        }
        BaseBluetoothLeUtils baseBluetoothLeUtils = this.mBluetoothLeUtils;
        if (baseBluetoothLeUtils != null) {
            baseBluetoothLeUtils.startSync(pet, this);
        }
    }

    @Override // com.petkit.android.ble.samsung.ISamsungBLEListener
    public void updateProgress(final int i, final String str) {
        runOnUiThread(new Runnable() { // from class: com.petkit.android.activities.fit.PetkitDetailActivity.16
            @Override // java.lang.Runnable
            public void run() {
                PetkitDetailActivity.this.updateBLESyncProgress(i, str);
            }
        });
        if (i != -22) {
            if (i == -16) {
                syncDeviceData();
            } else if (i == -6) {
                Intent intent = new Intent(this, (Class<?>) ActivityDataProcessService.class);
                intent.putExtra(BLEConsts.EXTRA_DEVICE_INFO, this.mDevice);
                intent.putExtra(BLEConsts.EXTRA_DOG, this.curPet);
                intent.putExtra(BLEConsts.EXTRA_URL_DATA_SAVE, ApiTools.SAMPLE_API_ACTIVITY_SAVE);
                intent.putExtra(BLEConsts.EXTRA_URL_DAILY_DETAIL, ApiTools.SAMPLE_API_ACTIVITY_DAILYDETAIL);
                startService(intent);
            }
        } else if (CommonUtils.getSysIntMap(CommonUtils.getAppContext(), Consts.SHARED_SYSTEM_TIME_VALID_STATE) != 0) {
            showLongToast(R.string.BLEUI_ble_system_time_error);
        } else {
            int i2 = deviceConnectState;
            if (i2 == -1 || i2 == 0) {
                this.mBluetoothLeUtils.startScan();
            }
        }
        if (i > 100) {
            stopGatt();
            runOnUiThread(new Runnable() { // from class: com.petkit.android.activities.fit.PetkitDetailActivity.17
                @Override // java.lang.Runnable
                public void run() {
                    PetkitDetailActivity.this.changeDeviceConnectState(5);
                }
            });
        }
    }

    private boolean checkCurrentPetIsDog() {
        return this.curPet.getType().getId() == 1;
    }

    private void scrollTo(final ScrollView scrollView, final int i) {
        new Handler().post(new Runnable() { // from class: com.petkit.android.activities.fit.PetkitDetailActivity.18
            @Override // java.lang.Runnable
            public void run() {
                scrollView.fullScroll(i);
            }
        });
    }

    private void reportAnimation() {
        this.drawables = new Drawable[this.ids.length];
        int i = 0;
        while (true) {
            int[] iArr = this.ids;
            if (i < iArr.length) {
                this.drawables[i] = ContextCompat.getDrawable(this, iArr[i]);
                i++;
            } else {
                new Thread(new reportRunnable()).start();
                this.change = 0;
                this.isPlay = true;
                return;
            }
        }
    }

    public class reportRunnable implements Runnable {
        public reportRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
            while (PetkitDetailActivity.this.isPlay) {
                Message messageObtainMessage = PetkitDetailActivity.this.handler.obtainMessage();
                messageObtainMessage.arg1 = 800;
                messageObtainMessage.what = 1001;
                PetkitDetailActivity.this.handler.sendMessage(messageObtainMessage);
                try {
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public class InductionTimePagerAdapter extends PagerAdapter {
        public int size;
        public SparseArray<View> viewArray = new SparseArray<>();

        public int getItem(int i) {
            return i;
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        public InductionTimePagerAdapter(int i) {
            this.size = i;
        }

        public void setSize(int i) {
            this.size = i;
            notifyDataSetChanged();
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public int getCount() {
            return this.size;
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public View instantiateItem(ViewGroup viewGroup, final int i) {
            final View viewInflate = LayoutInflater.from(PetkitDetailActivity.this).inflate(R.layout.adapter_induction_time_view, (ViewGroup) null);
            ArrayList weekDays = PetkitDetailActivity.this.getWeekDays(i);
            ((TextView) viewInflate.findViewById(R.id.month_tv)).setText(DateUtil.getConvertDateString((String) weekDays.get(PetkitDetailActivity.this.currentPosition), DateUtil.DATE_FORMAT_7, "yyyy-MM-dd").substring(0, 7));
            GridView gridView = (GridView) viewInflate.findViewById(R.id.date_gridview);
            PetkitDetailActivity petkitDetailActivity = PetkitDetailActivity.this;
            final TimeGridViewAdapter timeGridViewAdapter = petkitDetailActivity.new TimeGridViewAdapter(petkitDetailActivity, weekDays);
            this.viewArray.append(i, viewInflate);
            gridView.setAdapter((ListAdapter) timeGridViewAdapter);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.petkit.android.activities.fit.PetkitDetailActivity.InductionTimePagerAdapter.1
                @Override // android.widget.AdapterView.OnItemClickListener
                public void onItemClick(AdapterView<?> adapterView, View view, int i2, long j) {
                    String item = timeGridViewAdapter.getItem(i2);
                    if (DateUtil.str2Date(item, DateUtil.DATE_FORMAT_7).compareTo(PetkitDetailActivity.this.todayDate) > 0 || PetkitDetailActivity.this.petCreateDate == null || DateUtil.str2Date(item, DateUtil.DATE_FORMAT_7).compareTo(PetkitDetailActivity.this.petCreateDate) < 0) {
                        return;
                    }
                    PetkitDetailActivity.this.mCurrentDay = item;
                    timeGridViewAdapter.setCurrentPosition(i2);
                    ((TextView) viewInflate.findViewById(R.id.month_tv)).setText(DateUtil.getConvertDateString(item, DateUtil.DATE_FORMAT_7, "yyyy-MM-dd").substring(0, 7));
                    LocalBroadcastManager.getInstance(PetkitDetailActivity.this).sendBroadcast(new Intent(Constants.BROADCAST_MSG_TIMEGRIDVIEW_POSITION));
                    View view2 = (View) InductionTimePagerAdapter.this.viewArray.get(i + 1);
                    ArrayList weekDays2 = PetkitDetailActivity.this.getWeekDays(i + 1);
                    if (view2 != null) {
                        ((TextView) view2.findViewById(R.id.month_tv)).setText(DateUtil.getConvertDateString((String) weekDays2.get(i2), DateUtil.DATE_FORMAT_7, "yyyy-MM-dd").substring(0, 7));
                    }
                    View view3 = (View) InductionTimePagerAdapter.this.viewArray.get(i - 1);
                    ArrayList weekDays3 = PetkitDetailActivity.this.getWeekDays(i - 1);
                    if (view3 != null) {
                        ((TextView) view3.findViewById(R.id.month_tv)).setText(DateUtil.getConvertDateString((String) weekDays3.get(i2), DateUtil.DATE_FORMAT_7, "yyyy-MM-dd").substring(0, 7));
                    }
                    if (PetkitDetailActivity.this.mDailyDataMap.get(PetkitDetailActivity.this.mCurrentDay) != null) {
                        PetkitDetailActivity.this.initPetkitDailyDetailView();
                    }
                }
            });
            viewGroup.addView(viewInflate, 0);
            return viewInflate;
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
            viewGroup.removeView((View) obj);
            this.viewArray.remove(i);
        }
    }

    public class TimeGridViewAdapter extends BaseAdapter {
        private Context mContext;
        private List<String> mWeekDays;

        @Override // android.widget.Adapter
        public int getCount() {
            return 7;
        }

        @Override // android.widget.Adapter
        public long getItemId(int i) {
            return 0L;
        }

        public TimeGridViewAdapter(Context context, List<String> list) {
            this.mContext = context;
            this.mWeekDays = list;
            registerReceiver();
        }

        public void finalize() throws Throwable {
            super.finalize();
            unRegisterReceiver();
        }

        public void setList(List<String> list) {
            this.mWeekDays = list;
            notifyDataSetChanged();
        }

        public void setCurrentPosition(int i) {
            PetkitDetailActivity.this.currentPosition = i;
            notifyDataSetChanged();
        }

        @Override // android.widget.Adapter
        public String getItem(int i) {
            if (i < this.mWeekDays.size()) {
                return this.mWeekDays.get(i);
            }
            return null;
        }

        @Override // android.widget.Adapter
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            String string;
            if (view == null || !(view.getTag() instanceof ViewHolder)) {
                viewHolder = new ViewHolder();
                view = LayoutInflater.from(this.mContext).inflate(R.layout.adapter_time_view, (ViewGroup) null);
                viewHolder.week = (TextView) view.findViewById(R.id.week_tv);
                viewHolder.day = (TextView) view.findViewById(R.id.day_tv);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            String convertDateString = DateUtil.getConvertDateString(this.mWeekDays.get(i), DateUtil.DATE_FORMAT_7, "yyyy-MM-dd");
            if (i == 0) {
                string = PetkitDetailActivity.this.getString(R.string.Week_monday);
            } else if (i == 1) {
                string = PetkitDetailActivity.this.getString(R.string.Week_tuesday);
            } else if (i == 2) {
                string = PetkitDetailActivity.this.getString(R.string.Week_wednesday);
            } else if (i == 3) {
                string = PetkitDetailActivity.this.getString(R.string.Week_thursday);
            } else if (i == 4) {
                string = PetkitDetailActivity.this.getString(R.string.Week_friday);
            } else if (i == 5) {
                string = PetkitDetailActivity.this.getString(R.string.Week_saturday);
            } else if (i != 6) {
                string = "";
            } else {
                string = PetkitDetailActivity.this.getString(R.string.Week_sunday);
            }
            if (DateUtil.str2Date(convertDateString, "yyyy-MM-dd").compareTo(PetkitDetailActivity.this.todayDate) > 0 || (PetkitDetailActivity.this.petCreateDate != null && DateUtil.str2Date(convertDateString, "yyyy-MM-dd").compareTo(PetkitDetailActivity.this.petCreateDate) < 0)) {
                viewHolder.day.setTextColor(CommonUtils.getColorById(R.color.gray));
                viewHolder.week.setTextColor(CommonUtils.getColorById(R.color.gray));
            } else {
                viewHolder.week.setTextColor(CommonUtils.getColorById(R.color.black));
                if (this.mWeekDays.contains(PetkitDetailActivity.this.mCurrentDay)) {
                    if (i != PetkitDetailActivity.this.currentPosition) {
                        if (convertDateString.equals(DateUtil.date2Str(PetkitDetailActivity.this.todayDate, "yyyy-MM-dd"))) {
                            if (PetkitDetailActivity.this.currentPosition > PetkitDetailActivity.this.toDayPosition) {
                                viewHolder.day.setBackgroundResource(R.drawable.solid_blue_circle_bg);
                                viewHolder.day.setTextColor(CommonUtils.getColorById(R.color.white));
                            } else {
                                viewHolder.day.setBackgroundResource(R.drawable.solid_white_circle_bg);
                                viewHolder.day.setTextColor(CommonUtils.getColorById(R.color.blue));
                            }
                        } else if (PetkitDetailActivity.this.petCreateDate != null && convertDateString.equals(DateUtil.date2Str(PetkitDetailActivity.this.petCreateDate, "yyyy-MM-dd")) && PetkitDetailActivity.this.currentPosition < PetkitDetailActivity.this.petCreatePosition) {
                            viewHolder.day.setBackgroundResource(R.drawable.solid_blue_circle_bg);
                            viewHolder.day.setTextColor(CommonUtils.getColorById(R.color.white));
                        } else {
                            viewHolder.day.setBackgroundResource(R.drawable.solid_white_circle_bg);
                            viewHolder.day.setTextColor(CommonUtils.getColorById(R.color.black));
                        }
                    } else {
                        viewHolder.day.setBackgroundResource(R.drawable.solid_blue_circle_bg);
                        viewHolder.day.setTextColor(CommonUtils.getColorById(R.color.white));
                    }
                } else {
                    viewHolder.day.setBackgroundResource(R.drawable.solid_white_circle_bg);
                    if (convertDateString.equals(DateUtil.date2Str(PetkitDetailActivity.this.todayDate, "yyyy-MM-dd"))) {
                        viewHolder.day.setTextColor(CommonUtils.getColorById(R.color.blue));
                    } else {
                        viewHolder.day.setTextColor(CommonUtils.getColorById(R.color.black));
                    }
                }
            }
            viewHolder.week.setText(string);
            viewHolder.day.setText(convertDateString.split("-")[2]);
            return view;
        }

        public class ViewHolder {
            public TextView day;
            public TextView week;

            public ViewHolder() {
            }
        }

        private void registerReceiver() {
            PetkitDetailActivity.this.mBroadcastReceiver = new BroadcastReceiver() { // from class: com.petkit.android.activities.fit.PetkitDetailActivity.TimeGridViewAdapter.1
                @Override // android.content.BroadcastReceiver
                public void onReceive(Context context, Intent intent) {
                    if (intent.getAction().equals(Constants.BROADCAST_MSG_TIMEGRIDVIEW_POSITION)) {
                        TimeGridViewAdapter.this.notifyDataSetChanged();
                    }
                }
            };
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(Constants.BROADCAST_MSG_TIMEGRIDVIEW_POSITION);
            LocalBroadcastManager.getInstance(this.mContext).registerReceiver(PetkitDetailActivity.this.mBroadcastReceiver, intentFilter);
        }

        private void unRegisterReceiver() {
            LocalBroadcastManager.getInstance(this.mContext).unregisterReceiver(PetkitDetailActivity.this.mBroadcastReceiver);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ArrayList<String> getWeekDays(int i) {
        int i2 = ((this.pagerCount - i) - 1) * 7;
        Date date = this.todayDate;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(5, -i2);
        ArrayList<String> arrayList = new ArrayList<>();
        while (calendar.get(7) != 1) {
            calendar.add(5, 1);
        }
        for (int i3 = 0; i3 < 7; i3++) {
            arrayList.add(new SimpleDateFormat(DateUtil.DATE_FORMAT_7).format(calendar.getTime()));
            calendar.add(5, -1);
        }
        Collections.sort(arrayList, new Comparator<String>() { // from class: com.petkit.android.activities.fit.PetkitDetailActivity.20
            @Override // java.util.Comparator
            public int compare(String str, String str2) {
                return str.compareTo(str2);
            }
        });
        return arrayList;
    }

    private void initSyncingView() {
        this.mStateImageView = (ImageView) findViewById(R.id.pet_state_img);
        this.mStateTextView = (TextView) findViewById(R.id.pet_state_text);
        TextView textView = (TextView) findViewById(R.id.pet_sync_cancel);
        this.mCancelTextView = textView;
        textView.setOnClickListener(this);
        this.syncImageView1 = findViewById(R.id.petkit_syncing_bg_1);
        this.syncImageView2 = findViewById(R.id.petkit_syncing_bg_2);
        this.syncImageView3 = findViewById(R.id.petkit_syncing_bg_3);
        this.syncImageView4 = findViewById(R.id.petkit_syncing_bg_4);
        findViewById(R.id.pet_syncing_view).setVisibility(4);
    }

    private void changeSyncState(int i, int i2) {
        if (deviceConnectState == -1) {
        }
        deviceConnectState = i;
        PetkitLog.d("changeSyncState: " + i);
        this.mStateImageView.setImageResource(R.drawable.sync_ble);
        switch (i) {
            case 0:
                this.mStateTextView.setText(R.string.BLEUI_scanning);
                break;
            case 1:
                this.mStateTextView.setText(R.string.BLEUI_connecting);
                break;
            case 2:
                if (i2 < 0 || i2 > 100) {
                    i2 = 0;
                }
                this.mStateTextView.setText(getString(R.string.Synchronizing) + i2 + "%");
                this.mCancelTextView.setVisibility(8);
                break;
            case 3:
            case 4:
            case 5:
            case 7:
            case 11:
            case 12:
                getWindow().clearFlags(128);
                this.mCancelTextView.setVisibility(8);
                if (3 == i || 11 == i) {
                    this.mStateImageView.setImageResource(R.drawable.sync_done);
                    this.mStateTextView.setText(R.string.Synchronized);
                    LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(Constants.BROADCAST_MSG_REFRESH_BANNERDATA));
                    reportAnimation();
                } else if (5 == i) {
                    this.mStateImageView.setImageResource(R.drawable.sync_warn);
                    this.mStateTextView.setText(R.string.BLEUI_connect_failed);
                } else if (7 == i) {
                    this.mStateImageView.setImageResource(R.drawable.sync_warn);
                    this.mStateTextView.setText(R.string.BLEUI_sync_timeout);
                } else if (12 == i) {
                    this.mStateImageView.setImageResource(R.drawable.sync_warn);
                    this.mStateTextView.setText(R.string.Hint_network_failed);
                } else {
                    this.mStateImageView.setImageResource(R.drawable.sync_warn);
                    this.mStateTextView.setText(R.string.BLEUI_scan_timeout);
                }
                new Handler().postDelayed(new Runnable() { // from class: com.petkit.android.activities.fit.PetkitDetailActivity.21
                    /* JADX WARN: Removed duplicated region for block: B:11:0x0093  */
                    @Override // java.lang.Runnable
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public void run() {
                        /*
                            r6 = this;
                            com.petkit.android.activities.fit.PetkitDetailActivity r0 = com.petkit.android.activities.fit.PetkitDetailActivity.this
                            java.util.Map r0 = com.petkit.android.activities.fit.PetkitDetailActivity.access$1200(r0)
                            com.petkit.android.activities.fit.PetkitDetailActivity r1 = com.petkit.android.activities.fit.PetkitDetailActivity.this
                            java.lang.String r1 = com.petkit.android.activities.fit.PetkitDetailActivity.access$600(r1)
                            java.lang.Object r0 = r0.get(r1)
                            com.petkit.android.model.DailyDetailItem r0 = (com.petkit.android.model.DailyDetailItem) r0
                            boolean r0 = r0.isInit()
                            r1 = 2
                            r2 = -1
                            if (r0 == 0) goto L32
                            com.petkit.android.activities.fit.PetkitDetailActivity r0 = com.petkit.android.activities.fit.PetkitDetailActivity.this
                            java.util.Map r0 = com.petkit.android.activities.fit.PetkitDetailActivity.access$1200(r0)
                            com.petkit.android.activities.fit.PetkitDetailActivity r3 = com.petkit.android.activities.fit.PetkitDetailActivity.this
                            java.lang.String r3 = com.petkit.android.activities.fit.PetkitDetailActivity.access$600(r3)
                            java.lang.Object r0 = r0.get(r3)
                            com.petkit.android.model.DailyDetailItem r0 = (com.petkit.android.model.DailyDetailItem) r0
                            boolean r0 = r0.islastest()
                            if (r0 != 0) goto L93
                        L32:
                            com.petkit.android.activities.fit.PetkitDetailActivity r0 = com.petkit.android.activities.fit.PetkitDetailActivity.this
                            java.util.Map r0 = com.petkit.android.activities.fit.PetkitDetailActivity.access$1200(r0)
                            com.petkit.android.activities.fit.PetkitDetailActivity r3 = com.petkit.android.activities.fit.PetkitDetailActivity.this
                            java.lang.String r3 = com.petkit.android.activities.fit.PetkitDetailActivity.access$600(r3)
                            java.lang.Object r0 = r0.get(r3)
                            com.petkit.android.model.DailyDetailItem r0 = (com.petkit.android.model.DailyDetailItem) r0
                            java.util.List r0 = r0.getData()
                            r3 = 0
                            java.lang.Object r0 = r0.get(r3)
                            java.lang.Integer r0 = (java.lang.Integer) r0
                            int r0 = r0.intValue()
                            if (r0 == r2) goto L93
                            java.io.File r0 = new java.io.File
                            java.lang.StringBuilder r4 = new java.lang.StringBuilder
                            r4.<init>()
                            java.lang.String r5 = com.petkit.android.utils.FileUtils.getAppCacheActivityDataDirPath()
                            r4.append(r5)
                            com.petkit.android.activities.fit.PetkitDetailActivity r5 = com.petkit.android.activities.fit.PetkitDetailActivity.this
                            com.petkit.android.model.Pet r5 = com.petkit.android.activities.fit.PetkitDetailActivity.access$200(r5)
                            java.lang.String r5 = r5.getId()
                            r4.append(r5)
                            java.lang.String r5 = "-"
                            r4.append(r5)
                            java.lang.String r5 = "tempActivityData.json"
                            r4.append(r5)
                            java.lang.String r4 = r4.toString()
                            r0.<init>(r4)
                            boolean r0 = r0.exists()
                            if (r0 == 0) goto L93
                            com.petkit.android.activities.fit.PetkitDetailActivity r0 = com.petkit.android.activities.fit.PetkitDetailActivity.this
                            com.petkit.android.activities.fit.PetkitDetailActivity.access$302(r0, r3)
                            com.petkit.android.activities.fit.PetkitDetailActivity r0 = com.petkit.android.activities.fit.PetkitDetailActivity.this
                            r3 = 1
                            com.petkit.android.activities.fit.PetkitDetailActivity.access$3800(r0, r1, r3)
                            goto L98
                        L93:
                            com.petkit.android.activities.fit.PetkitDetailActivity r0 = com.petkit.android.activities.fit.PetkitDetailActivity.this
                            com.petkit.android.activities.fit.PetkitDetailActivity.access$1500(r0, r1)
                        L98:
                            com.petkit.android.activities.fit.PetkitDetailActivity.deviceConnectState = r2
                            return
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.petkit.android.activities.fit.PetkitDetailActivity.AnonymousClass21.run():void");
                    }
                }, 1000L);
                break;
            case 6:
                this.mStateTextView.setText(R.string.Synchronizing);
                this.mCancelTextView.setVisibility(8);
                break;
            case 10:
                this.mStateImageView.setImageResource(R.drawable.sync_upload);
                this.mStateTextView.setText(R.string.BLEUI_synchronization_analyze_data_cloud);
                this.mCancelTextView.setVisibility(8);
                saveFitBug6292State();
                break;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startSyncingAnimation(int i) {
        AnimatorSet animatorSet;
        if (i != 1) {
            if (i == 2 && (animatorSet = this.animSet) != null && animatorSet.isStarted()) {
                this.animSet.cancel();
                this.animSet = null;
                this.petkitSyncBtnView.setVisibility(0);
                findViewById(R.id.pet_syncing_view).setVisibility(4);
                this.syncImageView1.setX(this.syncImageViewPositionX);
                this.syncImageView1.setY(this.syncImageViewPositionY);
                this.syncImageView2.setX(this.syncImageViewPositionX);
                this.syncImageView2.setY(this.syncImageViewPositionY);
                this.syncImageView3.setX(this.syncImageViewPositionX);
                this.syncImageView3.setY(this.syncImageViewPositionY);
                this.syncImageView4.setX(this.syncImageViewPositionX);
                this.syncImageView4.setY(this.syncImageViewPositionY);
                findViewById(R.id.pet_syncing_bg_view).clearAnimation();
                return;
            }
            return;
        }
        findViewById(R.id.pet_syncing_view).setVisibility(0);
        this.petkitSyncBtnView.setVisibility(4);
        if (this.syncImageViewPositionX == 0.0f || this.syncImageViewPositionY == 0.0f) {
            this.syncImageViewPositionX = this.syncImageView1.getX();
            this.syncImageViewPositionY = this.syncImageView1.getY();
        }
        findViewById(R.id.pet_syncing_bg_view).startAnimation(AnimationUtils.loadAnimation(this, R.anim.scan_rotate));
        float fDpToPixel = DeviceUtils.dpToPixel(this, 10.0f);
        View view = this.syncImageView1;
        float f = this.syncImageViewPositionX;
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, "x", f, f - fDpToPixel);
        objectAnimatorOfFloat.setRepeatMode(2);
        objectAnimatorOfFloat.setRepeatCount(100);
        View view2 = this.syncImageView1;
        float f2 = this.syncImageViewPositionY;
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(view2, "y", f2, f2 - fDpToPixel);
        objectAnimatorOfFloat2.setRepeatMode(2);
        objectAnimatorOfFloat2.setRepeatCount(100);
        View view3 = this.syncImageView2;
        float f3 = this.syncImageViewPositionX;
        ObjectAnimator objectAnimatorOfFloat3 = ObjectAnimator.ofFloat(view3, "x", f3, f3 + fDpToPixel);
        objectAnimatorOfFloat3.setRepeatMode(2);
        objectAnimatorOfFloat3.setRepeatCount(100);
        View view4 = this.syncImageView2;
        float f4 = this.syncImageViewPositionY;
        ObjectAnimator objectAnimatorOfFloat4 = ObjectAnimator.ofFloat(view4, "y", f4, f4 + fDpToPixel);
        objectAnimatorOfFloat4.setRepeatMode(2);
        objectAnimatorOfFloat4.setRepeatCount(100);
        View view5 = this.syncImageView3;
        float f5 = this.syncImageViewPositionX;
        ObjectAnimator objectAnimatorOfFloat5 = ObjectAnimator.ofFloat(view5, "x", f5, f5 - fDpToPixel);
        objectAnimatorOfFloat5.setRepeatMode(2);
        objectAnimatorOfFloat5.setRepeatCount(100);
        View view6 = this.syncImageView3;
        float f6 = this.syncImageViewPositionY;
        ObjectAnimator objectAnimatorOfFloat6 = ObjectAnimator.ofFloat(view6, "y", f6, f6 + fDpToPixel);
        objectAnimatorOfFloat6.setRepeatMode(2);
        objectAnimatorOfFloat6.setRepeatCount(100);
        View view7 = this.syncImageView4;
        float f7 = this.syncImageViewPositionX;
        ObjectAnimator objectAnimatorOfFloat7 = ObjectAnimator.ofFloat(view7, "x", f7, f7 + fDpToPixel);
        objectAnimatorOfFloat7.setRepeatMode(2);
        objectAnimatorOfFloat7.setRepeatCount(100);
        View view8 = this.syncImageView4;
        float f8 = this.syncImageViewPositionY;
        ObjectAnimator objectAnimatorOfFloat8 = ObjectAnimator.ofFloat(view8, "y", f8, f8 - fDpToPixel);
        objectAnimatorOfFloat8.setRepeatMode(2);
        objectAnimatorOfFloat8.setRepeatCount(100);
        AnimatorSet animatorSet2 = new AnimatorSet();
        this.animSet = animatorSet2;
        animatorSet2.setDuration(800L);
        this.animSet.playTogether(objectAnimatorOfFloat, objectAnimatorOfFloat2, objectAnimatorOfFloat3, objectAnimatorOfFloat4, objectAnimatorOfFloat5, objectAnimatorOfFloat6, objectAnimatorOfFloat7, objectAnimatorOfFloat8);
        this.animSet.start();
    }

    private void reportSyncIssue() {
        HashMap map = new HashMap();
        map.put("deviceId", String.valueOf(this.curPet.getDevice().getId()));
        post(ApiTools.SAMPLE_API_DEVICE_REPORTSYNCISSUE, map, new AsyncHttpRespHandler(this) { // from class: com.petkit.android.activities.fit.PetkitDetailActivity.22
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                if (PetkitDetailActivity.this.isFinishing()) {
                    return;
                }
                ReportSyncRsp reportSyncRsp = (ReportSyncRsp) this.gson.fromJson(this.responseResult, ReportSyncRsp.class);
                if (reportSyncRsp.getResult() == null || reportSyncRsp.getResult().getCode() != 1) {
                    return;
                }
                LogcatStorageHelper.addLog("low battery: " + reportSyncRsp.getResult().getMsg());
                PetkitDetailActivity petkitDetailActivity = PetkitDetailActivity.this;
                new LowBatteryDialog(petkitDetailActivity, petkitDetailActivity.curPet.getDevice(), reportSyncRsp.getResult().getMsg()).show();
            }
        }, false);
    }

    private void uploadUrgentLog(String str) {
        HashMap map = new HashMap();
        map.put("message", str);
        map.put("client", CommonUtils.getClientInfor(Constants.PETKIT_APP_ID));
        post(ApiTools.SAMPLE_API_APP_UPLOADURGENTLOG, map, new AsyncHttpRespHandler() { // from class: com.petkit.android.activities.fit.PetkitDetailActivity.23
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
            }
        });
    }

    private void saveFitBug6292State() {
        HashMap map = new HashMap();
        map.put("deviceId", String.valueOf(this.curPet.getDevice().getId()));
        AsyncHttpUtil.post(ApiTools.SAMPLE_API_FIT_BUG6292_SAVE, map, new AsyncHttpRespHandler() { // from class: com.petkit.android.activities.fit.PetkitDetailActivity.24
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
            }
        });
    }
}

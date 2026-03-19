package com.petkit.android.activities.petkitBleDevice.k3.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.motion.widget.Key;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.cozy.widget.EasyPopupWindow;
import com.petkit.android.activities.family.mode.FamilyInfor;
import com.petkit.android.activities.mall.mode.PromoteData;
import com.petkit.android.activities.mall.widget.PromoteView;
import com.petkit.android.activities.petkitBleDevice.ble.PetkitBleDeviceManager;
import com.petkit.android.activities.petkitBleDevice.k3.adapter.K3DeodorantRecordAdapter;
import com.petkit.android.activities.petkitBleDevice.k3.mode.K3BarChartData;
import com.petkit.android.activities.petkitBleDevice.k3.mode.K3DeodorantRecord;
import com.petkit.android.activities.petkitBleDevice.k3.mode.K3Record;
import com.petkit.android.activities.petkitBleDevice.k3.utils.K3Utils;
import com.petkit.android.activities.petkitBleDevice.k3.widget.K3BatteryWindow;
import com.petkit.android.activities.petkitBleDevice.t4.mode.RelatedProductsInfor;
import com.petkit.android.activities.petkitBleDevice.t4.utils.T4Utils;
import com.petkit.android.activities.petkitBleDevice.t4.widget.T4ScrollRecyclerView;
import com.petkit.android.activities.petkitBleDevice.t4.widget.T4TipWindow;
import com.petkit.android.activities.petkitBleDevice.utils.NestedScrollableViewHelper;
import com.petkit.android.activities.petkitBleDevice.widget.T4PetkitSlidingUpPanelLayout;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.LogcatStorageHelper;
import com.petkit.android.utils.PetkitLog;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.oversea.R;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.simple.eventbus.EventBus;

/* JADX INFO: loaded from: classes4.dex */
public class K3HomeDeviceView extends LinearLayout implements View.OnClickListener, T4PetkitSlidingUpPanelLayout.PanelSlideListener, K3BatteryWindow.TipClickListener, T4TipWindow.T4TipWindowListener {
    private Activity activity;
    private AnimatorSet animatorSet;
    private EasyPopupWindow bindT4PopupWindow;
    private int btStateColorResId;
    private String btStateText;
    private int btStateVisibleStatus;
    private View contentView;
    private int deviceLeftMargin;
    private ObjectAnimator devicePositiveAlphaObjectAnimator;
    private int deviceRefreshingLeftMargin;
    private ObjectAnimator deviceSideAlphaObjectAnimator;
    private ObjectAnimator deviceSideTranslationObjectAnimator;
    private FamilyInfor familyInfor;
    private FrameLayout flTopPanel;
    private boolean initPetkitSlidingUpPanelLayoutFinish;
    private boolean isDeviceNotFound;
    private ImageView ivDeodorant;
    private ImageView ivK3RefreshAnimation;
    private ImageView ivK3RefreshLight;
    private ImageView ivK3SideView;
    private ImageView ivLightOnOff;
    private ImageView ivLiquidWarnIcon;
    private ImageView ivRegularDeodorize;
    private ImageView ivUpArrow;
    private List<K3BarChartData> k3BarChartDataList;
    private K3BatteryWindow k3BatteryWindow;
    private K3DeodorantRecordAdapter k3DeodorantRecordAdapter;
    private K3NewHistogram k3Histogram;
    private K3LiquidWindow k3LiquidWindow;
    private K3Record k3Record;
    private ImageView k3ViewDeviceCenter;
    private ImageView k3ViewDeviceLighting;
    private NestedScrollView k3ViewLayout;
    private T4PetkitSlidingUpPanelLayout k3ViewSlidingDrawer;
    private ImageView k3ViewStateIconBattery;
    private ImageView k3ViewStateIconDeodorant;
    private ImageView k3ViewStateIconUnbindT4;
    private Integer lighting;
    private ObjectAnimator lightingObjectAnimator;
    private LinearLayout llBottomMenuParentView;
    private LinearLayout llBtnAndWarnPanel;
    private LinearLayout llHistoryRecordPanel;
    private LinearLayout llMenuPanel;
    private LinearLayout llTodayDataPanel;
    private Context mContext;
    private VelocityTracker mVelocityTracker;
    private MainHandler mainHandler;
    private MenuOnClickListener menuOnClickListener;
    private PromoteView promoteView;
    private AnimationDrawable refreshAnimationDrawable;
    private ObjectAnimator refreshLightingObjectAnimator;
    private Integer refreshing;
    private RelativeLayout rlDeodorize;
    private RelativeLayout rlK3Liquid;
    private RelativeLayout rlLightTurnOnOff;
    private RelativeLayout rlMiddlePanel;
    private RelativeLayout rlRegularDeodorize;
    private RelativeLayout rlTimePanel;
    private RelativeLayout rlTopView;
    private RelativeLayout rlViewK3DeviceCenter;
    private T4ScrollRecyclerView rvK3RecordView;
    private String symbol;
    private float tempY;
    private TextView tvDeodorantRecordTitle;
    private TextView tvDeodorizeImmediately;
    private TextView tvLightOnOff;
    private TextView tvLiquidAllowance;
    private TextView tvRegularDeodorize;
    private TextView tvTimes;
    private TextView tvWarnText;
    private float widthScale;

    public interface AnimationEndCallback {
        void animationEnd();
    }

    public interface MenuOnClickListener {
        void onBottomMenuClick(MenuType menuType);
    }

    public enum MenuType {
        IMMEDIATE_CONTROL_LIGHT,
        IMMEDIATE_CONTROL_DEODORIZA,
        WRAN_TIP,
        REGULAR_DEODORIZA,
        RESET,
        BIND_T4
    }

    @Override // com.petkit.android.activities.petkitBleDevice.widget.T4PetkitSlidingUpPanelLayout.PanelSlideListener
    public void onPanelSlide(View view, float f) {
    }

    @Override // com.petkit.android.activities.petkitBleDevice.widget.T4PetkitSlidingUpPanelLayout.PanelSlideListener
    public void onPanelStateChanged(View view, T4PetkitSlidingUpPanelLayout.PanelState panelState, T4PetkitSlidingUpPanelLayout.PanelState panelState2) {
    }

    public K3HomeDeviceView(Context context) {
        super(context);
        this.widthScale = 0.28f;
        this.initPetkitSlidingUpPanelLayoutFinish = false;
        this.mContext = context;
        this.activity = (Activity) context;
        initView();
    }

    public K3HomeDeviceView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.widthScale = 0.28f;
        this.initPetkitSlidingUpPanelLayoutFinish = false;
        this.mContext = context;
        this.activity = (Activity) context;
        initView();
    }

    public K3HomeDeviceView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.widthScale = 0.28f;
        this.initPetkitSlidingUpPanelLayoutFinish = false;
        this.mContext = context;
        this.activity = (Activity) context;
        initView();
    }

    public void setFamilyInfor(FamilyInfor familyInfor) {
        this.familyInfor = familyInfor;
    }

    private void initView() {
        this.mainHandler = new MainHandler(this.mContext);
        this.mVelocityTracker = VelocityTracker.obtain();
        View viewInflate = LayoutInflater.from(this.mContext).inflate(R.layout.layout_k3_home_device_view, (ViewGroup) null);
        this.contentView = viewInflate;
        addView(viewInflate, -1, -1);
        initViews();
        this.k3ViewSlidingDrawer.setScrollableViewHelper(new NestedScrollableViewHelper());
        this.k3ViewSlidingDrawer.addPanelSlideListener(this);
        this.rlTopView.setOnTouchListener(new View.OnTouchListener() { // from class: com.petkit.android.activities.petkitBleDevice.k3.widget.K3HomeDeviceView.1
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (!K3HomeDeviceView.this.initPetkitSlidingUpPanelLayoutFinish) {
                    return true;
                }
                K3HomeDeviceView.this.mVelocityTracker.addMovement(motionEvent);
                int action = motionEvent.getAction();
                if (action == 0) {
                    K3HomeDeviceView.this.tempY = motionEvent.getY();
                    Log.d("resOffset", "ACTION_DOWN:tempY:" + K3HomeDeviceView.this.tempY);
                } else if (action == 1) {
                    Log.d("resOffset", "ACTION_UP");
                    K3HomeDeviceView.this.mVelocityTracker.computeCurrentVelocity(1000);
                    float xVelocity = K3HomeDeviceView.this.mVelocityTracker.getXVelocity();
                    float yVelocity = K3HomeDeviceView.this.mVelocityTracker.getYVelocity();
                    Log.d("resOffset", "xVelocity:" + xVelocity + " yVelocity:" + yVelocity);
                    K3HomeDeviceView.this.k3ViewSlidingDrawer.fling(K3HomeDeviceView.this.mVelocityTracker, xVelocity, yVelocity * (-1.0f));
                } else if (action == 2) {
                    float y = motionEvent.getY();
                    K3HomeDeviceView.this.k3ViewSlidingDrawer.smoothPanelView(y - K3HomeDeviceView.this.tempY);
                    K3HomeDeviceView.this.tempY = y;
                }
                return true;
            }
        });
        this.llTodayDataPanel.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.petkit.android.activities.petkitBleDevice.k3.widget.K3HomeDeviceView.2
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                K3HomeDeviceView.this.llTodayDataPanel.getHeight();
                final int height = K3HomeDeviceView.this.k3ViewSlidingDrawer.getChildAt(0).getHeight();
                K3HomeDeviceView.this.mainHandler.postDelayed(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.k3.widget.K3HomeDeviceView.2.1
                    @Override // java.lang.Runnable
                    public void run() {
                        int height2 = K3HomeDeviceView.this.contentView.getHeight() - height;
                        if (height2 > 0) {
                            K3HomeDeviceView.this.k3ViewSlidingDrawer.setPanelHeight(height2);
                        }
                        K3HomeDeviceView.this.initPetkitSlidingUpPanelLayoutFinish = true;
                        K3HomeDeviceView k3HomeDeviceView = K3HomeDeviceView.this;
                        k3HomeDeviceView.refreshView(k3HomeDeviceView.k3Record);
                    }
                }, 200L);
                K3HomeDeviceView.this.llTodayDataPanel.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        this.rvK3RecordView.setLayoutManager(new LinearLayoutManager(this.mContext));
        K3DeodorantRecordAdapter k3DeodorantRecordAdapter = new K3DeodorantRecordAdapter(this.mContext);
        this.k3DeodorantRecordAdapter = k3DeodorantRecordAdapter;
        this.rvK3RecordView.setAdapter(k3DeodorantRecordAdapter);
        initSize();
    }

    private void initViews() {
        this.tvTimes = (TextView) this.contentView.findViewById(R.id.tv_times);
        this.tvLiquidAllowance = (TextView) this.contentView.findViewById(R.id.tv_liquid_allowance);
        this.k3ViewStateIconBattery = (ImageView) this.contentView.findViewById(R.id.k3_view_state_icon_battery);
        this.ivLiquidWarnIcon = (ImageView) this.contentView.findViewById(R.id.iv_liquid_warn_icon);
        this.rlLightTurnOnOff = (RelativeLayout) this.contentView.findViewById(R.id.rl_light_turn_on_off);
        this.rlDeodorize = (RelativeLayout) this.contentView.findViewById(R.id.rl_deodorize);
        this.rlRegularDeodorize = (RelativeLayout) this.contentView.findViewById(R.id.rl_regular_deodorize);
        this.llTodayDataPanel = (LinearLayout) this.contentView.findViewById(R.id.ll_today_data_panel);
        this.k3ViewSlidingDrawer = (T4PetkitSlidingUpPanelLayout) this.contentView.findViewById(R.id.k3_view_slidingDrawer);
        this.tvWarnText = (TextView) this.contentView.findViewById(R.id.tv_warn_text);
        this.k3ViewDeviceCenter = (ImageView) this.contentView.findViewById(R.id.k3_view_device_center);
        this.ivK3SideView = (ImageView) this.contentView.findViewById(R.id.iv_k3_side_view);
        this.k3ViewDeviceLighting = (ImageView) this.contentView.findViewById(R.id.k3_view_device_lighting);
        this.k3ViewStateIconUnbindT4 = (ImageView) this.contentView.findViewById(R.id.k3_view_state_icon_unbind_t4);
        this.k3ViewStateIconDeodorant = (ImageView) this.contentView.findViewById(R.id.k3_view_state_icon_deodorant);
        this.rlViewK3DeviceCenter = (RelativeLayout) this.contentView.findViewById(R.id.rl_view_k3_device_center);
        this.llBtnAndWarnPanel = (LinearLayout) this.contentView.findViewById(R.id.ll_btn_and_warn_panel);
        this.rlMiddlePanel = (RelativeLayout) this.contentView.findViewById(R.id.rl_middle_panel);
        this.rlK3Liquid = (RelativeLayout) this.contentView.findViewById(R.id.rl_K3_liquid);
        this.llMenuPanel = (LinearLayout) this.contentView.findViewById(R.id.ll_menu_panel);
        this.ivUpArrow = (ImageView) this.contentView.findViewById(R.id.iv_up_arrow);
        this.rlTopView = (RelativeLayout) this.contentView.findViewById(R.id.rl_top_view);
        this.flTopPanel = (FrameLayout) this.contentView.findViewById(R.id.fl_top_panel);
        this.rlTimePanel = (RelativeLayout) this.contentView.findViewById(R.id.rl_time_panel);
        this.tvDeodorantRecordTitle = (TextView) this.contentView.findViewById(R.id.tv_deodorant_record_title);
        this.llHistoryRecordPanel = (LinearLayout) this.contentView.findViewById(R.id.ll_history_record_panel);
        this.rvK3RecordView = (T4ScrollRecyclerView) this.contentView.findViewById(R.id.rv_k3_recordView);
        this.llBottomMenuParentView = (LinearLayout) this.contentView.findViewById(R.id.ll_bottom_menu_parent_view);
        this.k3ViewLayout = (NestedScrollView) this.contentView.findViewById(R.id.k3_view_layout);
        this.ivLightOnOff = (ImageView) this.contentView.findViewById(R.id.iv_light_on_off);
        this.ivDeodorant = (ImageView) this.contentView.findViewById(R.id.iv_deodorant);
        this.ivRegularDeodorize = (ImageView) this.contentView.findViewById(R.id.iv_regular_deodorize);
        this.tvLightOnOff = (TextView) this.contentView.findViewById(R.id.tv_light_on_off);
        this.tvDeodorizeImmediately = (TextView) this.contentView.findViewById(R.id.tv_deodorize_immediately);
        this.tvRegularDeodorize = (TextView) this.contentView.findViewById(R.id.tv_regular_deodorize);
        this.ivK3RefreshAnimation = (ImageView) this.contentView.findViewById(R.id.iv_k3_refresh_animation);
        this.ivK3RefreshLight = (ImageView) this.contentView.findViewById(R.id.iv_k3_refresh_light);
        this.k3Histogram = (K3NewHistogram) this.contentView.findViewById(R.id.view_k3_histogram);
        this.promoteView = (PromoteView) this.contentView.findViewById(R.id.promoteView);
        this.rlDeodorize.setOnClickListener(this);
        this.rlRegularDeodorize.setOnClickListener(this);
        this.rlLightTurnOnOff.setOnClickListener(this);
        this.k3ViewStateIconBattery.setOnClickListener(this);
        this.tvWarnText.setOnClickListener(this);
        this.k3ViewStateIconUnbindT4.setOnClickListener(this);
        this.k3ViewStateIconDeodorant.setOnClickListener(this);
        this.rlK3Liquid.setOnClickListener(this);
    }

    public void initSize() {
        int i = BaseApplication.displayMetrics.widthPixels;
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.k3ViewDeviceCenter.getLayoutParams();
        int i2 = (int) (i * this.widthScale);
        layoutParams.width = i2;
        layoutParams.height = (int) (i2 * 2.3619f);
        layoutParams.setMargins(0, (int) ((i2 / 105.0f) * 17.0f), 0, 0);
        this.k3ViewDeviceCenter.setLayoutParams(layoutParams);
        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) this.ivK3SideView.getLayoutParams();
        layoutParams2.width = (int) ((layoutParams.width / 105.0f) * 119.0f);
        layoutParams2.height = (int) ((layoutParams.width / 105.0f) * 248.0f);
        this.ivK3SideView.setLayoutParams(layoutParams2);
        int i3 = layoutParams.width;
        this.deviceLeftMargin = (int) ((i - ((int) ((i3 / 105.0f) * 119.0f))) / 2.0f);
        this.deviceRefreshingLeftMargin = (int) ((i3 / 105.0f) * 33.0f);
        RelativeLayout.LayoutParams layoutParams3 = (RelativeLayout.LayoutParams) this.k3ViewDeviceLighting.getLayoutParams();
        layoutParams3.width = (int) ((layoutParams.width / 105.0f) * 167.0f);
        int i4 = layoutParams.width;
        layoutParams3.height = (int) ((i4 / 105.0f) * 52.0f);
        layoutParams3.setMargins((int) ((i4 / 105.0f) * 104.0f), (int) ((i4 / 105.0f) * 252.0f), 0, 0);
        this.k3ViewDeviceLighting.setLayoutParams(layoutParams3);
        RelativeLayout.LayoutParams layoutParams4 = (RelativeLayout.LayoutParams) this.ivK3RefreshAnimation.getLayoutParams();
        int i5 = layoutParams.width;
        layoutParams4.height = (int) ((i5 / 105.0f) * 268.0f);
        layoutParams4.setMargins(0, (int) ((i5 / 105.0f) * 11.0f), 0, 0);
        this.ivK3RefreshAnimation.setLayoutParams(layoutParams4);
        RelativeLayout.LayoutParams layoutParams5 = (RelativeLayout.LayoutParams) this.ivK3RefreshLight.getLayoutParams();
        layoutParams5.width = (int) ((layoutParams.width / 105.0f) * 136.0f);
        int i6 = layoutParams.width;
        layoutParams5.height = (int) ((i6 / 105.0f) * 63.0f);
        layoutParams5.setMargins((int) ((i6 / 105.0f) * 82.0f), (int) ((i6 / 105.0f) * 229.0f), 0, 0);
        this.ivK3RefreshLight.setLayoutParams(layoutParams5);
    }

    public void refreshBarChart(List<K3BarChartData> list) {
        this.k3Histogram.updateBarChartData(list);
    }

    private void checkBatteryState() {
        if (this.k3Record.getBattery() == 100) {
            this.k3ViewStateIconBattery.setImageResource(R.drawable.k3_battery_100_icon);
            return;
        }
        if (this.k3Record.getBattery() >= 80) {
            this.k3ViewStateIconBattery.setImageResource(R.drawable.k3_battery_80_icon);
            return;
        }
        if (this.k3Record.getBattery() >= 60) {
            this.k3ViewStateIconBattery.setImageResource(R.drawable.k3_battery_60_icon);
            return;
        }
        if (this.k3Record.getBattery() >= 40) {
            this.k3ViewStateIconBattery.setImageResource(R.drawable.k3_battery_40_icon);
        } else if (this.k3Record.getBattery() >= 20) {
            this.k3ViewStateIconBattery.setImageResource(R.drawable.k3_battery_20_icon);
        } else if (this.k3Record.getBattery() < 20) {
            this.k3ViewStateIconBattery.setImageResource(R.drawable.t4_battery_warn_icon);
        }
    }

    private void checkDeviceWarnState() {
        if (PetkitBleDeviceManager.getInstance().checkDeviceState(this.k3Record.getMac(), 16) && this.k3Record.getLiquidLack() == 1) {
            this.k3ViewStateIconDeodorant.setVisibility(0);
        } else {
            this.k3ViewStateIconDeodorant.setVisibility(8);
        }
        if (this.k3Record.getRelateT4() != 0 && T4Utils.getT4NumbersInFamily(getContext(), this.familyInfor) > 0) {
            this.k3ViewStateIconUnbindT4.setVisibility(0);
        } else {
            this.k3ViewStateIconUnbindT4.setVisibility(8);
        }
    }

    public void startUpArrowAnimation() {
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this.ivUpArrow, Key.TRANSLATION_Y, 0.0f, -16.0f);
        objectAnimatorOfFloat.setRepeatMode(1);
        objectAnimatorOfFloat.setRepeatCount(-1);
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(this.ivUpArrow, Key.ALPHA, 0.0f, 1.0f, 0.0f);
        objectAnimatorOfFloat2.setRepeatMode(1);
        objectAnimatorOfFloat2.setRepeatCount(-1);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(3500L);
        animatorSet.playTogether(objectAnimatorOfFloat2, objectAnimatorOfFloat);
        animatorSet.start();
    }

    public void startRefreshingAnimator(final AnimationEndCallback animationEndCallback) {
        deviceCenterAlphaAnimation(1.0f, 0.0f, new AnimationEndCallback() { // from class: com.petkit.android.activities.petkitBleDevice.k3.widget.K3HomeDeviceView.3
            @Override // com.petkit.android.activities.petkitBleDevice.k3.widget.K3HomeDeviceView.AnimationEndCallback
            public void animationEnd() {
                K3HomeDeviceView.this.k3ViewDeviceCenter.setVisibility(8);
                K3HomeDeviceView.this.ivK3SideView.setVisibility(0);
                K3HomeDeviceView k3HomeDeviceView = K3HomeDeviceView.this;
                k3HomeDeviceView.deviceSideViewAnimation(0.2f, 1.0f, 0, k3HomeDeviceView.deviceRefreshingLeftMargin - K3HomeDeviceView.this.deviceLeftMargin, new AnimationEndCallback() { // from class: com.petkit.android.activities.petkitBleDevice.k3.widget.K3HomeDeviceView.3.1
                    @Override // com.petkit.android.activities.petkitBleDevice.k3.widget.K3HomeDeviceView.AnimationEndCallback
                    public void animationEnd() {
                        K3HomeDeviceView.this.startRefreshingAnimation();
                        animationEndCallback.animationEnd();
                    }
                });
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startRefreshingAnimation() {
        this.ivK3RefreshAnimation.setVisibility(0);
        if (this.refreshAnimationDrawable == null) {
            this.ivK3RefreshAnimation.setBackgroundResource(R.drawable.k3_refresh_animation);
            AnimationDrawable animationDrawable = (AnimationDrawable) this.ivK3RefreshAnimation.getDrawable();
            this.refreshAnimationDrawable = animationDrawable;
            animationDrawable.start();
        }
    }

    private void stopRefreshAnimation(final AnimationEndCallback animationEndCallback) {
        this.ivK3RefreshAnimation.setVisibility(8);
        AnimationDrawable animationDrawable = this.refreshAnimationDrawable;
        if (animationDrawable != null) {
            animationDrawable.stop();
            this.refreshAnimationDrawable = null;
        }
        deviceSideViewAnimation(1.0f, 0.2f, this.deviceRefreshingLeftMargin - this.deviceLeftMargin, 0, new AnimationEndCallback() { // from class: com.petkit.android.activities.petkitBleDevice.k3.widget.K3HomeDeviceView.4
            @Override // com.petkit.android.activities.petkitBleDevice.k3.widget.K3HomeDeviceView.AnimationEndCallback
            public void animationEnd() {
                K3HomeDeviceView.this.ivK3SideView.setVisibility(8);
                K3HomeDeviceView.this.k3ViewDeviceCenter.setVisibility(0);
                K3HomeDeviceView.this.deviceCenterAlphaAnimation(0.0f, 1.0f, new AnimationEndCallback() { // from class: com.petkit.android.activities.petkitBleDevice.k3.widget.K3HomeDeviceView.4.1
                    @Override // com.petkit.android.activities.petkitBleDevice.k3.widget.K3HomeDeviceView.AnimationEndCallback
                    public void animationEnd() {
                        animationEndCallback.animationEnd();
                    }
                });
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void deviceCenterAlphaAnimation(float f, float f2, final AnimationEndCallback animationEndCallback) {
        if (this.devicePositiveAlphaObjectAnimator == null) {
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this.k3ViewDeviceCenter, Key.ALPHA, f, f2);
            this.devicePositiveAlphaObjectAnimator = objectAnimatorOfFloat;
            objectAnimatorOfFloat.setRepeatMode(1);
            this.devicePositiveAlphaObjectAnimator.setDuration(800L);
            this.devicePositiveAlphaObjectAnimator.setInterpolator(new LinearInterpolator());
            this.devicePositiveAlphaObjectAnimator.setRepeatCount(0);
            this.devicePositiveAlphaObjectAnimator.start();
            this.devicePositiveAlphaObjectAnimator.addListener(new Animator.AnimatorListener() { // from class: com.petkit.android.activities.petkitBleDevice.k3.widget.K3HomeDeviceView.5
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
                    animationEndCallback.animationEnd();
                    K3HomeDeviceView.this.devicePositiveAlphaObjectAnimator = null;
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void deviceSideViewAnimation(float f, float f2, int i, int i2, final AnimationEndCallback animationEndCallback) {
        if (this.deviceSideAlphaObjectAnimator == null) {
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this.ivK3SideView, Key.ALPHA, f, f2);
            this.deviceSideAlphaObjectAnimator = objectAnimatorOfFloat;
            objectAnimatorOfFloat.setRepeatMode(1);
            this.deviceSideAlphaObjectAnimator.setInterpolator(new LinearInterpolator());
            this.deviceSideAlphaObjectAnimator.setRepeatCount(0);
        }
        if (this.deviceSideTranslationObjectAnimator == null) {
            ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(this.ivK3SideView, Key.TRANSLATION_X, i, i2);
            this.deviceSideTranslationObjectAnimator = objectAnimatorOfFloat2;
            objectAnimatorOfFloat2.setRepeatMode(1);
            this.deviceSideTranslationObjectAnimator.setInterpolator(new LinearInterpolator());
            this.deviceSideTranslationObjectAnimator.setRepeatCount(0);
            this.deviceSideTranslationObjectAnimator.addListener(new Animator.AnimatorListener() { // from class: com.petkit.android.activities.petkitBleDevice.k3.widget.K3HomeDeviceView.6
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
                    K3HomeDeviceView.this.deviceSideAlphaObjectAnimator = null;
                    K3HomeDeviceView.this.deviceSideTranslationObjectAnimator = null;
                    K3HomeDeviceView.this.animatorSet = null;
                    animationEndCallback.animationEnd();
                }
            });
        }
        if (this.animatorSet == null) {
            AnimatorSet animatorSet = new AnimatorSet();
            this.animatorSet = animatorSet;
            animatorSet.playTogether(this.deviceSideAlphaObjectAnimator, this.deviceSideTranslationObjectAnimator);
            this.animatorSet.setDuration(1200L);
            this.animatorSet.start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startRefreshLightAnimation() {
        stopLightAnimator();
        this.ivK3RefreshLight.setVisibility(0);
        if (this.refreshLightingObjectAnimator == null) {
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this.ivK3RefreshLight, Key.ALPHA, 0.5f, 1.0f);
            this.refreshLightingObjectAnimator = objectAnimatorOfFloat;
            objectAnimatorOfFloat.setRepeatMode(2);
            this.refreshLightingObjectAnimator.setDuration(1000L);
            this.refreshLightingObjectAnimator.setInterpolator(new LinearInterpolator());
            this.refreshLightingObjectAnimator.setRepeatCount(-1);
            this.refreshLightingObjectAnimator.start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopRefreshLightAnimator() {
        this.ivK3RefreshLight.setVisibility(8);
        ObjectAnimator objectAnimator = this.refreshLightingObjectAnimator;
        if (objectAnimator != null) {
            objectAnimator.cancel();
            this.refreshLightingObjectAnimator = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startLightAnimator() {
        stopRefreshLightAnimator();
        this.k3ViewDeviceLighting.setVisibility(0);
        if (this.lightingObjectAnimator == null) {
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this.k3ViewDeviceLighting, Key.ALPHA, 0.5f, 1.0f);
            this.lightingObjectAnimator = objectAnimatorOfFloat;
            objectAnimatorOfFloat.setRepeatMode(2);
            this.lightingObjectAnimator.setDuration(1000L);
            this.lightingObjectAnimator.setInterpolator(new LinearInterpolator());
            this.lightingObjectAnimator.setRepeatCount(-1);
            this.lightingObjectAnimator.start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopLightAnimator() {
        this.k3ViewDeviceLighting.setVisibility(4);
        ObjectAnimator objectAnimator = this.lightingObjectAnimator;
        if (objectAnimator != null) {
            objectAnimator.cancel();
            this.lightingObjectAnimator = null;
        }
    }

    public void refreshDeodorantRecord(List<K3DeodorantRecord> list) {
        Resources resources;
        int i;
        K3DeodorantRecordAdapter k3DeodorantRecordAdapter = this.k3DeodorantRecordAdapter;
        if (k3DeodorantRecordAdapter != null) {
            k3DeodorantRecordAdapter.setData(list, this.k3Record.getTimezone());
            this.k3DeodorantRecordAdapter.notifyDataSetChanged();
            Iterator<K3DeodorantRecord> it = list.iterator();
            int i2 = 0;
            while (it.hasNext()) {
                if (it.next().getType() == 1) {
                    i2++;
                }
            }
            this.symbol = "zh_CN".equalsIgnoreCase(UserInforUtils.getLanguageLocaleString(CommonUtils.getAppContext())) ? "" : " ";
            TextView textView = this.tvDeodorantRecordTitle;
            String str = "%s %s" + this.symbol + "%s";
            String string = this.mContext.getResources().getString(R.string.Number_of_deodorant);
            String strValueOf = String.valueOf(i2);
            if (i2 > 1) {
                resources = this.mContext.getResources();
                i = R.string.Unit_times;
            } else {
                resources = this.mContext.getResources();
                i = R.string.Unit_time;
            }
            textView.setText(K3Utils.getK3HomeDeodorantCount(String.format(str, string, strValueOf, resources.getString(i))));
        }
    }

    public void refreshTvWarnText(int i, String str, int i2) {
        this.btStateColorResId = i2;
        this.btStateVisibleStatus = i;
        this.btStateText = str;
        if (i == 0) {
            this.tvWarnText.setVisibility(0);
        } else if (i == 1) {
            this.tvWarnText.setVisibility(4);
        } else {
            this.tvWarnText.setVisibility(8);
        }
        this.tvWarnText.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
        this.tvWarnText.setText(str);
        this.tvWarnText.setTextColor(getResources().getColor(i2));
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        String string;
        SpannableString spannableString;
        SpannableString spannableString2;
        SpannableString spannableString3;
        String string2;
        int id = view.getId();
        MenuOnClickListener menuOnClickListener = this.menuOnClickListener;
        if (menuOnClickListener != null) {
            if (id == R.id.rl_light_turn_on_off) {
                menuOnClickListener.onBottomMenuClick(MenuType.IMMEDIATE_CONTROL_LIGHT);
            } else if (id == R.id.rl_deodorize) {
                menuOnClickListener.onBottomMenuClick(MenuType.IMMEDIATE_CONTROL_DEODORIZA);
            } else if (id == R.id.rl_regular_deodorize) {
                menuOnClickListener.onBottomMenuClick(MenuType.REGULAR_DEODORIZA);
            } else if (id == R.id.tv_warn_text) {
                menuOnClickListener.onBottomMenuClick(MenuType.WRAN_TIP);
            } else if (id == R.id.k3_view_state_icon_unbind_t4) {
                menuOnClickListener.onBottomMenuClick(MenuType.BIND_T4);
            }
        }
        if (id == R.id.k3_view_state_icon_battery) {
            if (this.k3Record.getBattery() >= 20) {
                string2 = String.format("%s：%s", this.mContext.getResources().getString(R.string.Battery_remaining), this.k3Record.getBattery() + "%");
            } else {
                string2 = this.mContext.getResources().getString(R.string.K3_low_battery_prompt, this.k3Record.getBattery() + "%");
            }
            String str = string2;
            K3BatteryWindow k3BatteryWindow = this.k3BatteryWindow;
            if (k3BatteryWindow == null || !k3BatteryWindow.isShowing()) {
                String string3 = this.k3Record.getUpdatedAt() != null ? getResources().getString(R.string.Last_update_time, CommonUtils.getTimeSnapFromDate(this.mContext, this.k3Record.getUpdatedAt())) : "";
                K3BatteryWindow k3BatteryWindow2 = new K3BatteryWindow(this.mContext, str, string3, this, this.k3Record.getBattery() < 20, this.k3Record.getBattery() + "%");
                this.k3BatteryWindow = k3BatteryWindow2;
                k3BatteryWindow2.show(this.activity.getWindow().getDecorView());
                return;
            }
            return;
        }
        int i = R.id.k3_view_state_icon_deodorant;
        if (id == i || id == R.id.rl_K3_liquid) {
            if (id != i || this.k3Record.getLiquidLack() == 1) {
                SpannableString spannableString4 = new SpannableString("");
                if (this.k3Record.getLiquidLack() == 3) {
                    string = this.mContext.getResources().getString(R.string.K3_Liquid_adequate_title);
                } else if (this.k3Record.getLiquidLack() == 2) {
                    string = this.mContext.getResources().getString(R.string.K3_Liquid_fewer_title);
                } else {
                    string = this.mContext.getResources().getString(R.string.K3_Liquid_lack_title);
                }
                String str2 = string;
                String string4 = this.mContext.getResources().getString(R.string.K3_manual_calibration);
                if (this.k3Record.getLiquidLack() == 2 || this.k3Record.getLiquidLack() == 1) {
                    String string5 = this.mContext.getResources().getString(R.string.K3_manual_calibration_prompt, this.mContext.getResources().getString(R.string.K3_manual_calibration));
                    SpannableString spannableString5 = new SpannableString(string5);
                    spannableString5.setSpan(new ClickableSpan() { // from class: com.petkit.android.activities.petkitBleDevice.k3.widget.K3HomeDeviceView.7
                        @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
                        public void updateDrawState(@NonNull TextPaint textPaint) {
                            textPaint.setColor(K3HomeDeviceView.this.getResources().getColor(R.color.k3_main_blue));
                            textPaint.setUnderlineText(false);
                        }

                        @Override // android.text.style.ClickableSpan
                        public void onClick(@NonNull View view2) {
                            if (K3HomeDeviceView.this.menuOnClickListener != null) {
                                K3HomeDeviceView.this.menuOnClickListener.onBottomMenuClick(MenuType.RESET);
                            }
                            if (K3HomeDeviceView.this.k3LiquidWindow != null) {
                                K3HomeDeviceView.this.k3LiquidWindow.dismiss();
                            }
                        }
                    }, string5.indexOf(string4), string5.indexOf(string4) + string4.length(), 17);
                    if (this.k3Record.getLiquidLack() == 2) {
                        spannableString = new SpannableString(this.mContext.getResources().getString(R.string.Liquid_lack_prompt));
                    } else {
                        spannableString = new SpannableString(this.mContext.getResources().getString(R.string.Liquid_insufficient_prompt));
                    }
                    spannableString2 = spannableString;
                    spannableString3 = spannableString5;
                } else {
                    spannableString3 = spannableString4;
                    spannableString2 = new SpannableString(this.mContext.getResources().getString(R.string.K3_liquid_lack_prompt));
                }
                K3LiquidWindow k3LiquidWindow = new K3LiquidWindow(this.activity, str2, spannableString2, spannableString3, true, this.k3Record.getLiquidLack() != 3);
                this.k3LiquidWindow = k3LiquidWindow;
                k3LiquidWindow.showAtLocation(this.activity.getWindow().getDecorView(), 17, 0, 0);
            }
        }
    }

    public void showBindT4Window() {
        EasyPopupWindow easyPopupWindow = this.bindT4PopupWindow;
        if (easyPopupWindow == null || !easyPopupWindow.isShowing()) {
            View viewInflate = LayoutInflater.from(this.mContext).inflate(R.layout.pop_k3_bind_t4, (ViewGroup) null);
            EasyPopupWindow easyPopupWindow2 = new EasyPopupWindow(this.mContext);
            this.bindT4PopupWindow = easyPopupWindow2;
            easyPopupWindow2.setOutsideTouchable(false);
            ((LinearLayout) viewInflate.findViewById(R.id.ll_outside)).setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.k3.widget.K3HomeDeviceView.8
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    K3HomeDeviceView.this.bindT4PopupWindow.dismiss();
                }
            });
            ImageView imageView = (ImageView) viewInflate.findViewById(R.id.iv_t4_device);
            LinearLayout linearLayout = (LinearLayout) viewInflate.findViewById(R.id.ll_k3_bind_t4);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
            layoutParams.setMargins(0, 0, 0, (int) (BaseApplication.displayMetrics.widthPixels * 0.05f));
            linearLayout.setLayoutParams(layoutParams);
            Glide.with(getContext()).load(Integer.valueOf(R.drawable.t4_bind_k3_animation)).into(new GlideDrawableImageViewTarget(imageView));
            ((TextView) viewInflate.findViewById(R.id.tv_start_associate)).setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.k3.widget.K3HomeDeviceView.9
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    K3HomeDeviceView.this.bindT4PopupWindow.dismiss();
                    if (K3HomeDeviceView.this.menuOnClickListener != null) {
                        K3HomeDeviceView.this.menuOnClickListener.onBottomMenuClick(MenuType.BIND_T4);
                    }
                }
            });
            this.bindT4PopupWindow.setmShowAlpha(0.5f);
            this.bindT4PopupWindow.setContentView(viewInflate);
            this.bindT4PopupWindow.setHeight(-1);
            this.bindT4PopupWindow.setWidth((int) (BaseApplication.displayMetrics.widthPixels * 0.9f));
            this.bindT4PopupWindow.showAtLocation(this.contentView, 17, 0, 0);
        }
    }

    public void refreshView(K3Record k3Record) {
        if (k3Record == null) {
            return;
        }
        this.k3Record = k3Record;
        if (PetkitBleDeviceManager.getInstance().checkDeviceState(k3Record.getMac(), 16)) {
            this.ivRegularDeodorize.setImageResource(R.drawable.k3_deodorize_regular_icon);
            this.ivDeodorant.setImageResource(R.drawable.k3_deodorize_immediately_icon);
            this.ivLightOnOff.setImageResource(R.drawable.k3_light_switch_icon);
            this.rlRegularDeodorize.setClickable(true);
            this.rlDeodorize.setClickable(true);
            this.rlLightTurnOnOff.setClickable(true);
            this.tvLightOnOff.setTextColor(this.mContext.getResources().getColor(R.color.k3_home_black));
            this.tvRegularDeodorize.setTextColor(this.mContext.getResources().getColor(R.color.k3_home_black));
            this.tvDeodorizeImmediately.setTextColor(this.mContext.getResources().getColor(R.color.k3_home_black));
            this.tvTimes.setText(String.valueOf(k3Record.getSprayTimes()));
            if (k3Record.getLiquidLack() == 3) {
                this.tvLiquidAllowance.setText(this.mContext.getResources().getString(R.string.T4_sand_adequate));
                this.tvLiquidAllowance.setTextColor(this.mContext.getResources().getColor(R.color.k3_home_black));
            } else if (k3Record.getLiquidLack() == 2) {
                this.tvLiquidAllowance.setText(this.mContext.getResources().getString(R.string.T4_sand_low));
                this.tvLiquidAllowance.setTextColor(this.mContext.getResources().getColor(R.color.k3_home_black));
            } else if (k3Record.getLiquidLack() == 1) {
                this.tvLiquidAllowance.setText(this.mContext.getResources().getString(R.string.T4_sand_insufficient));
                this.tvLiquidAllowance.setTextColor(this.mContext.getResources().getColor(R.color.w5_home_red));
            }
            if (k3Record.getLighting() == 1) {
                this.ivLightOnOff.setImageResource(R.drawable.k3_light_open_icon);
                this.tvLightOnOff.setText(this.mContext.getResources().getString(R.string.K3_turn_off_light));
            } else {
                this.ivLightOnOff.setImageResource(R.drawable.k3_light_switch_icon);
                this.tvLightOnOff.setText(this.mContext.getResources().getString(R.string.K3_turn_on_light));
            }
            this.ivLiquidWarnIcon.setVisibility(0);
            if (this.lighting == null || this.refreshing == null || k3Record.getRefreshing() != this.refreshing.intValue() || this.lighting.intValue() != k3Record.getLighting()) {
                this.lighting = Integer.valueOf(k3Record.getLighting());
                this.refreshing = Integer.valueOf(k3Record.getRefreshing());
                stopLightAnimator();
                stopRefreshLightAnimator();
                PetkitLog.d(String.format("lighting:%s,refreshing:%s", Integer.valueOf(k3Record.getLighting()), Integer.valueOf(k3Record.getRefreshing())));
                if (this.refreshing.intValue() == 1) {
                    if (this.refreshAnimationDrawable != null) {
                        if (this.lighting.intValue() == 1) {
                            startRefreshLightAnimation();
                        } else {
                            stopRefreshLightAnimator();
                        }
                    } else {
                        startRefreshingAnimator(new AnimationEndCallback() { // from class: com.petkit.android.activities.petkitBleDevice.k3.widget.K3HomeDeviceView.10
                            @Override // com.petkit.android.activities.petkitBleDevice.k3.widget.K3HomeDeviceView.AnimationEndCallback
                            public void animationEnd() {
                                PetkitLog.d(String.format("startRefreshingAnimator-animationEnd,lighting:%s", K3HomeDeviceView.this.lighting));
                                LogcatStorageHelper.addLog(String.format("startRefreshingAnimator-animationEnd,lighting:%s", K3HomeDeviceView.this.lighting));
                                if (K3HomeDeviceView.this.lighting.intValue() == 1) {
                                    K3HomeDeviceView.this.startRefreshLightAnimation();
                                } else {
                                    K3HomeDeviceView.this.stopRefreshLightAnimator();
                                }
                            }
                        });
                    }
                    refreshTvWarnText(0, this.mContext.getResources().getString(R.string.Device_deodorizing_prompt), R.color.light_black);
                } else {
                    if (this.ivK3SideView.getVisibility() == 8) {
                        if (this.lighting.intValue() == 1) {
                            startLightAnimator();
                        } else {
                            stopLightAnimator();
                        }
                    } else {
                        stopRefreshAnimation(new AnimationEndCallback() { // from class: com.petkit.android.activities.petkitBleDevice.k3.widget.K3HomeDeviceView.11
                            @Override // com.petkit.android.activities.petkitBleDevice.k3.widget.K3HomeDeviceView.AnimationEndCallback
                            public void animationEnd() {
                                PetkitLog.d(String.format("stopRefreshAnimation-animationEnd,lighting:%s", K3HomeDeviceView.this.lighting));
                                LogcatStorageHelper.addLog(String.format("stopRefreshAnimation-animationEnd,lighting:%s", K3HomeDeviceView.this.lighting));
                                if (K3HomeDeviceView.this.lighting.intValue() == 1) {
                                    K3HomeDeviceView.this.startLightAnimator();
                                } else {
                                    K3HomeDeviceView.this.stopLightAnimator();
                                }
                            }
                        });
                    }
                    this.tvWarnText.setVisibility(4);
                }
            }
        } else {
            stopLightAnimator();
            stopRefreshLightAnimator();
            this.ivRegularDeodorize.setImageResource(R.drawable.k3_deodorize_regular_icon_gray);
            this.ivDeodorant.setImageResource(R.drawable.k3_deodorize_immediately_icon_gray);
            this.ivLightOnOff.setImageResource(R.drawable.k3_light_switch_icon_gray);
            this.rlRegularDeodorize.setClickable(false);
            this.rlDeodorize.setClickable(false);
            this.rlLightTurnOnOff.setClickable(false);
            this.tvLightOnOff.setTextColor(this.mContext.getResources().getColor(R.color.k3_home_gray));
            this.tvRegularDeodorize.setTextColor(this.mContext.getResources().getColor(R.color.k3_home_gray));
            this.tvDeodorizeImmediately.setTextColor(this.mContext.getResources().getColor(R.color.k3_home_gray));
            this.tvTimes.setText("- -");
            this.tvLiquidAllowance.setText("- -");
            this.tvLiquidAllowance.setTextColor(this.mContext.getResources().getColor(R.color.w5_home_black));
            this.tvWarnText.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
            this.tvWarnText.setVisibility(0);
            this.tvWarnText.setTextColor(getResources().getColor(R.color.w5_main_blue));
            if (!CommonUtils.checkPermission(this.activity, "android.permission.ACCESS_FINE_LOCATION")) {
                this.tvWarnText.setText(this.activity.getString(R.string.Location_permission_prompt) + " >");
            } else if (!CommonUtils.checkGPSIsOpened(this.activity)) {
                this.tvWarnText.setText(this.activity.getString(R.string.Gps_permission_prompt) + " >");
            } else if (!CommonUtils.checkBlueTooth(this.activity)) {
                this.tvWarnText.setText(this.activity.getString(R.string.Ble_device_not_open) + " >");
            } else if (this.isDeviceNotFound) {
                this.tvWarnText.setText(this.activity.getString(R.string.Ble_device_not_found));
            } else {
                String str = this.btStateText;
                if (str != null) {
                    refreshTvWarnText(this.btStateVisibleStatus, str, this.btStateColorResId);
                } else {
                    this.tvWarnText.setText(this.activity.getString(R.string.Ble_device_connect_fail));
                }
            }
            this.ivLiquidWarnIcon.setVisibility(8);
        }
        checkBatteryState();
        checkDeviceWarnState();
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t4.widget.T4TipWindow.T4TipWindowListener
    public void onBtnClick() {
        MenuOnClickListener menuOnClickListener = this.menuOnClickListener;
        if (menuOnClickListener != null) {
            menuOnClickListener.onBottomMenuClick(MenuType.RESET);
        }
    }

    public static class MainHandler extends Handler {
        public final WeakReference<Context> mContext;

        public MainHandler(Context context) {
            super(Looper.getMainLooper());
            this.mContext = new WeakReference<>(context);
        }
    }

    public void setMenuOnClickListener(MenuOnClickListener menuOnClickListener) {
        this.menuOnClickListener = menuOnClickListener;
    }

    @Override // com.petkit.android.activities.petkitBleDevice.k3.widget.K3BatteryWindow.TipClickListener
    public void onConfirmClick() {
        K3BatteryWindow k3BatteryWindow = this.k3BatteryWindow;
        if (k3BatteryWindow != null) {
            k3BatteryWindow.dismiss();
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
            this.promoteView.setPromoteViewOnItemListener(new PromoteView.PromoteViewOnItemListener() { // from class: com.petkit.android.activities.petkitBleDevice.k3.widget.K3HomeDeviceView.12
                @Override // com.petkit.android.activities.mall.widget.PromoteView.PromoteViewOnItemListener
                public void onItemClick(PromoteData promoteData) {
                    EventBus.getDefault().post(promoteData);
                }
            });
        }
    }
}

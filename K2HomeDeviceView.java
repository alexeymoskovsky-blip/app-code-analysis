package com.petkit.android.activities.petkitBleDevice.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
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
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.constraintlayout.motion.widget.Key;
import com.facebook.login.widget.ToolTipPopup;
import com.jess.arms.utils.ArmsUtils;
import com.petkit.android.activities.mall.mode.PromoteData;
import com.petkit.android.activities.mall.widget.PromoteView;
import com.petkit.android.activities.petkitBleDevice.BleDeviceBindNetWorkActivity;
import com.petkit.android.activities.petkitBleDevice.BleDeviceWifiSettingActivity;
import com.petkit.android.activities.petkitBleDevice.mode.K2MenuItem;
import com.petkit.android.activities.petkitBleDevice.mode.K2Record;
import com.petkit.android.activities.petkitBleDevice.mode.K2TimingResult;
import com.petkit.android.activities.petkitBleDevice.mode.TimeViewResult;
import com.petkit.android.activities.petkitBleDevice.t4.mode.RelatedProductsInfor;
import com.petkit.android.activities.petkitBleDevice.utils.BleDeviceUtils;
import com.petkit.android.activities.petkitBleDevice.utils.NestedScrollableViewHelper;
import com.petkit.android.activities.petkitBleDevice.widget.BleDeviceHomeOfflineWarnWindow;
import com.petkit.android.activities.petkitBleDevice.widget.K2HomeMenuView;
import com.petkit.android.activities.petkitBleDevice.widget.PetkitSlidingUpPanelLayout;
import com.petkit.android.activities.petkitBleDevice.widget.frame.FrameSurfaceView;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.PetkitLog;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.oversea.R;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.lang.ref.WeakReference;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.simple.eventbus.EventBus;

/* JADX INFO: loaded from: classes5.dex */
public class K2HomeDeviceView extends LinearLayout implements View.OnClickListener, PetkitSlidingUpPanelLayout.PanelSlideListener, K2HomeMenuView.OnMenuClickListener, BleDeviceHomeOfflineWarnWindow.OfflineClickListener {
    private Activity activity;
    private View aniViewOne;
    private RelativeLayout aniViewPointOne;
    private RelativeLayout aniViewPointThree;
    private RelativeLayout aniViewPointTwo;
    private View aniViewThree;
    private View aniViewTwo;
    private boolean animationIsRunning;
    private List<AnimatorSet> animatorSetList;
    private DecimalFormat decimalFormat;
    private boolean deviceAni;
    private Disposable disposable;
    private int distance;
    private FrameLayout flTopRootPanel;
    private FrameSurfaceView fsvDeviceAni;
    private boolean isFirst;
    private boolean isFirstOnPanelSlide;
    private ImageView ivDeviceIcon;
    private ImageView ivLiquidState;
    private ImageView ivUpArrow;
    private CheckBox k2DeodorantSwitchCheckbox;
    private K2DeviceViewPanelSlideListener k2DeviceViewPanelSlideListener;
    private CheckBox k2LightSwitchCheckbox;
    private K2HomeMenuView k2MenuView;
    private K2Record k2Record;
    private CheckBox k2VoiceSwitchCheckbox;
    private View lineLight;
    private ObjectAnimator liquideAlphaAnimator;
    private LinearLayout llBottomMenuView;
    private LinearLayout llK2EditTime;
    private LinearLayout llStateTopPanel;
    private LinearLayout llStateTopTextPanel;
    private LinearLayout llTopDes;
    private Context mContext;
    private float mSlideOffset;
    private VelocityTracker mVelocityTracker;
    private MainHandler mainHandler;
    private OnMenuClickListener onMenuClickListener;
    private PromoteView promoteView;
    private List<K2TimingResult> result;
    private RelativeLayout rlCenterIcon;
    private RelativeLayout rlDeodorantAmount;
    private RelativeLayout rlLightTime;
    private RelativeLayout rlLiquidState;
    private RelativeLayout rlTemperatureUnitSwitch;
    private RelativeLayout rlTimePanel;
    private RelativeLayout rlTimingSwitch;
    private RelativeLayout rlTopView;
    private PetkitSlidingUpPanelLayout slidingUpPanelLayout;
    private float tempY;
    private BleTimeView timeView;
    private ImageView timezoneIcon;
    private TextView tvHumidity;
    private TextView tvK2HomePrompt;
    private TextView tvK2Humidity;
    private TextView tvK2Temperature;
    private TextView tvK2TopHumidity;
    private TextView tvK2TopTemperature;
    private TextView tvK2Volume;
    private TextView tvLightTime;
    private TextView tvLiquidState;
    private TextView tvTemperature;
    private TextView tvTimeContent;
    private TextView tvTimeTitle;
    private TextView tvTopHumidity;
    private TextView tvTopTemperature;
    private TextView tvWarnText;
    private boolean visibility;

    public interface K2DeviceViewPanelSlideListener {
        void onPanelSlide(View view, float f);
    }

    public enum MenuType {
        ON_OFF,
        AUTO,
        MUTE,
        GENERAL,
        POWERFUL,
        DEODORANT,
        TIMING,
        VOICE,
        LIGHT,
        LIGHT_TIME,
        TEMPERATURE,
        CONSUMABLES_LIQUID
    }

    public interface OnMenuClickListener {
        void onMenuClick(MenuType menuType);

        void onMenuClick(MenuType menuType, boolean z);

        void onSwitchClick();
    }

    public static class MainHandler extends Handler {
        public final WeakReference<Context> mContext;

        public MainHandler(Context context) {
            super(Looper.getMainLooper());
            this.mContext = new WeakReference<>(context);
        }
    }

    public K2HomeDeviceView(Context context) {
        super(context);
        this.isFirst = true;
        this.isFirstOnPanelSlide = true;
        this.decimalFormat = new DecimalFormat(MqttTopic.MULTI_LEVEL_WILDCARD);
        this.deviceAni = true;
        this.mContext = context;
        this.activity = (Activity) context;
        this.animatorSetList = new ArrayList();
        initView();
    }

    public K2HomeDeviceView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.isFirst = true;
        this.isFirstOnPanelSlide = true;
        this.decimalFormat = new DecimalFormat(MqttTopic.MULTI_LEVEL_WILDCARD);
        this.deviceAni = true;
        this.mContext = context;
        this.activity = (Activity) context;
        this.animatorSetList = new ArrayList();
        initView();
    }

    public K2HomeDeviceView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.isFirst = true;
        this.isFirstOnPanelSlide = true;
        this.decimalFormat = new DecimalFormat(MqttTopic.MULTI_LEVEL_WILDCARD);
        this.deviceAni = true;
        this.mContext = context;
        this.activity = (Activity) context;
        this.animatorSetList = new ArrayList();
        initView();
    }

    public OnMenuClickListener getOnMenuClickListener() {
        return this.onMenuClickListener;
    }

    public void setOnMenuClickListener(OnMenuClickListener onMenuClickListener) {
        this.onMenuClickListener = onMenuClickListener;
    }

    @SuppressLint({"ClickableViewAccessibility"})
    private void initView() {
        this.mVelocityTracker = VelocityTracker.obtain();
        this.mainHandler = new MainHandler(this.mContext);
        View viewInflate = LayoutInflater.from(this.mContext).inflate(R.layout.layout_k2_home_device, (ViewGroup) null);
        addView(viewInflate, -1, -1);
        this.timezoneIcon = (ImageView) viewInflate.findViewById(R.id.k2_view_state_icon_timezone);
        this.llK2EditTime = (LinearLayout) viewInflate.findViewById(R.id.ll_k2_edit_time);
        this.timeView = (BleTimeView) viewInflate.findViewById(R.id.k2_time_view);
        this.tvTimeContent = (TextView) viewInflate.findViewById(R.id.tv_time_content);
        this.tvTimeTitle = (TextView) viewInflate.findViewById(R.id.tv_time_title);
        this.rlTimePanel = (RelativeLayout) viewInflate.findViewById(R.id.rl_time_panel);
        this.flTopRootPanel = (FrameLayout) viewInflate.findViewById(R.id.fl_top_panel);
        this.fsvDeviceAni = (FrameSurfaceView) viewInflate.findViewById(R.id.fsv_device_ani);
        this.k2DeodorantSwitchCheckbox = (CheckBox) viewInflate.findViewById(R.id.k2_deodorant_switch_checkbox);
        this.ivUpArrow = (ImageView) viewInflate.findViewById(R.id.iv_up_arrow);
        this.tvK2HomePrompt = (TextView) viewInflate.findViewById(R.id.tv_k2_home_prompt);
        this.llStateTopTextPanel = (LinearLayout) viewInflate.findViewById(R.id.ll_k2_view_state_top_text_panel);
        LinearLayout linearLayout = (LinearLayout) viewInflate.findViewById(R.id.ll_k2_view_state_top_panel);
        this.llStateTopPanel = linearLayout;
        linearLayout.setAlpha(0.0f);
        this.llBottomMenuView = (LinearLayout) viewInflate.findViewById(R.id.ll_bottom_menu_parent_view);
        this.tvK2TopTemperature = (TextView) viewInflate.findViewById(R.id.tv_k2_top_temperature);
        this.tvK2TopHumidity = (TextView) viewInflate.findViewById(R.id.tv_k2_top_humidity);
        this.tvTopTemperature = (TextView) viewInflate.findViewById(R.id.tv_top_temperature);
        this.tvTopHumidity = (TextView) viewInflate.findViewById(R.id.tv_top_humidity);
        this.tvTemperature = (TextView) viewInflate.findViewById(R.id.tv_temperature);
        TextView textView = (TextView) viewInflate.findViewById(R.id.tv_humidity);
        this.tvHumidity = textView;
        textView.setText(this.mContext.getString(R.string.Humidity));
        this.tvTopHumidity.setText(this.mContext.getString(R.string.Humidity));
        this.lineLight = viewInflate.findViewById(R.id.line_light_time);
        this.tvLightTime = (TextView) viewInflate.findViewById(R.id.tv_light_time);
        this.llTopDes = (LinearLayout) viewInflate.findViewById(R.id.ll_top_des);
        this.aniViewPointOne = (RelativeLayout) viewInflate.findViewById(R.id.ani_view_point_one);
        this.aniViewPointTwo = (RelativeLayout) viewInflate.findViewById(R.id.ani_view_point_two);
        this.aniViewPointThree = (RelativeLayout) viewInflate.findViewById(R.id.ani_view_point_three);
        this.aniViewOne = viewInflate.findViewById(R.id.ani_view_one);
        this.aniViewTwo = viewInflate.findViewById(R.id.ani_view_two);
        this.aniViewThree = viewInflate.findViewById(R.id.ani_view_three);
        this.rlTopView = (RelativeLayout) viewInflate.findViewById(R.id.rl_top_view);
        this.tvK2Volume = (TextView) viewInflate.findViewById(R.id.tv_k2_volume);
        this.rlCenterIcon = (RelativeLayout) viewInflate.findViewById(R.id.rl_center_icon);
        this.tvK2Temperature = (TextView) viewInflate.findViewById(R.id.tv_k2_temperature);
        this.tvK2Humidity = (TextView) viewInflate.findViewById(R.id.tv_k2_humidity);
        this.tvWarnText = (TextView) viewInflate.findViewById(R.id.tv_warn_text);
        this.k2MenuView = (K2HomeMenuView) viewInflate.findViewById(R.id.k2_menu_view);
        this.rlDeodorantAmount = (RelativeLayout) viewInflate.findViewById(R.id.rl_deodorant_amount);
        this.rlTimingSwitch = (RelativeLayout) viewInflate.findViewById(R.id.rl_timing_switch);
        this.k2VoiceSwitchCheckbox = (CheckBox) viewInflate.findViewById(R.id.k2_voice_switch_checkbox);
        this.k2LightSwitchCheckbox = (CheckBox) viewInflate.findViewById(R.id.k2_light_switch_checkbox);
        this.rlLightTime = (RelativeLayout) viewInflate.findViewById(R.id.rl_light_time);
        this.rlTemperatureUnitSwitch = (RelativeLayout) viewInflate.findViewById(R.id.rl_temperature_unit_switch);
        this.k2MenuView = (K2HomeMenuView) viewInflate.findViewById(R.id.k2_menu_view);
        this.ivDeviceIcon = (ImageView) viewInflate.findViewById(R.id.iv_device_icon);
        this.slidingUpPanelLayout = (PetkitSlidingUpPanelLayout) viewInflate.findViewById(R.id.k2_view_slidingDrawer);
        this.promoteView = (PromoteView) viewInflate.findViewById(R.id.promoteView);
        this.rlLiquidState = (RelativeLayout) viewInflate.findViewById(R.id.rl_liquid_state);
        this.tvLiquidState = (TextView) viewInflate.findViewById(R.id.tv_liquid_state);
        this.ivLiquidState = (ImageView) viewInflate.findViewById(R.id.iv_liquid_state);
        this.slidingUpPanelLayout.setScrollableViewHelper(new NestedScrollableViewHelper());
        this.slidingUpPanelLayout.addPanelSlideListener(this);
        this.k2MenuView.setOnMenuClickListener(this);
        this.tvWarnText.setOnClickListener(this);
        this.rlDeodorantAmount.setOnClickListener(this);
        this.rlTemperatureUnitSwitch.setOnClickListener(this);
        this.rlTimingSwitch.setOnClickListener(this);
        this.rlLightTime.setOnClickListener(this);
        this.llK2EditTime.setOnClickListener(this);
        this.timezoneIcon.setOnClickListener(this);
        this.k2DeodorantSwitchCheckbox.setOnClickListener(this);
        this.k2VoiceSwitchCheckbox.setOnClickListener(this);
        this.k2LightSwitchCheckbox.setOnClickListener(this);
        this.rlLiquidState.setOnClickListener(this);
        this.ivLiquidState.setOnClickListener(this);
        this.mainHandler.postDelayed(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.widget.K2HomeDeviceView.1
            public AnonymousClass1() {
            }

            @Override // java.lang.Runnable
            public void run() {
                K2HomeDeviceView.this.activity.getWindow().getDecorView().setSystemUiVisibility(0);
                K2HomeDeviceView.this.initViewSizeAndEvent();
            }
        }, 200L);
        initFrameAnimation();
        this.activity.getWindow().getDecorView().setSystemUiVisibility(0);
        initViewSizeAndEvent();
        setAnimationViewSize();
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.widget.K2HomeDeviceView$1 */
    public class AnonymousClass1 implements Runnable {
        public AnonymousClass1() {
        }

        @Override // java.lang.Runnable
        public void run() {
            K2HomeDeviceView.this.activity.getWindow().getDecorView().setSystemUiVisibility(0);
            K2HomeDeviceView.this.initViewSizeAndEvent();
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.widget.K2HomeDeviceView$2 */
    public class AnonymousClass2 implements ViewTreeObserver.OnGlobalLayoutListener {
        public AnonymousClass2() {
        }

        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public void onGlobalLayout() {
            K2HomeDeviceView.this.mainHandler.postDelayed(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.widget.K2HomeDeviceView.2.1
                public final /* synthetic */ int val$height;
                public final /* synthetic */ int val$timePanelHeight;

                public AnonymousClass1(int i, int i2) {
                    i = i;
                    i = i2;
                }

                @Override // java.lang.Runnable
                public void run() {
                    K2HomeDeviceView.this.slidingUpPanelLayout.setPanelHeight(i + i + ArmsUtils.dip2px(K2HomeDeviceView.this.activity, 5.0f));
                }
            }, 200L);
            K2HomeDeviceView.this.rlTimePanel.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }

        /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.widget.K2HomeDeviceView$2$1 */
        public class AnonymousClass1 implements Runnable {
            public final /* synthetic */ int val$height;
            public final /* synthetic */ int val$timePanelHeight;

            public AnonymousClass1(int i, int i2) {
                i = i;
                i = i2;
            }

            @Override // java.lang.Runnable
            public void run() {
                K2HomeDeviceView.this.slidingUpPanelLayout.setPanelHeight(i + i + ArmsUtils.dip2px(K2HomeDeviceView.this.activity, 5.0f));
            }
        }
    }

    public void initViewSizeAndEvent() {
        this.rlTimePanel.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.petkit.android.activities.petkitBleDevice.widget.K2HomeDeviceView.2
            public AnonymousClass2() {
            }

            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                K2HomeDeviceView.this.mainHandler.postDelayed(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.widget.K2HomeDeviceView.2.1
                    public final /* synthetic */ int val$height;
                    public final /* synthetic */ int val$timePanelHeight;

                    public AnonymousClass1(int i, int i2) {
                        i = i;
                        i = i2;
                    }

                    @Override // java.lang.Runnable
                    public void run() {
                        K2HomeDeviceView.this.slidingUpPanelLayout.setPanelHeight(i + i + ArmsUtils.dip2px(K2HomeDeviceView.this.activity, 5.0f));
                    }
                }, 200L);
                K2HomeDeviceView.this.rlTimePanel.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }

            /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.widget.K2HomeDeviceView$2$1 */
            public class AnonymousClass1 implements Runnable {
                public final /* synthetic */ int val$height;
                public final /* synthetic */ int val$timePanelHeight;

                public AnonymousClass1(int i, int i2) {
                    i = i;
                    i = i2;
                }

                @Override // java.lang.Runnable
                public void run() {
                    K2HomeDeviceView.this.slidingUpPanelLayout.setPanelHeight(i + i + ArmsUtils.dip2px(K2HomeDeviceView.this.activity, 5.0f));
                }
            }
        });
        this.rlCenterIcon.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.petkit.android.activities.petkitBleDevice.widget.K2HomeDeviceView.3
            public AnonymousClass3() {
            }

            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                int height = K2HomeDeviceView.this.llTopDes.getHeight();
                int bottom = K2HomeDeviceView.this.tvK2Volume.getBottom();
                if (K2HomeDeviceView.this.isFirst) {
                    K2HomeDeviceView.this.distance = (height - bottom) - 10;
                    K2HomeDeviceView.this.isFirst = false;
                    if (K2HomeDeviceView.this.animationIsRunning) {
                        K2HomeDeviceView.this.cancelRunningAnimation();
                        K2HomeDeviceView.this.startRunningAnimation();
                    }
                }
            }
        });
        this.flTopRootPanel.setOnTouchListener(new View.OnTouchListener() { // from class: com.petkit.android.activities.petkitBleDevice.widget.K2HomeDeviceView.4
            public AnonymousClass4() {
            }

            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                K2HomeDeviceView.this.mVelocityTracker.addMovement(motionEvent);
                int action = motionEvent.getAction();
                if (action == 0) {
                    K2HomeDeviceView.this.tempY = motionEvent.getY();
                    Log.e("resOffset", "ACTION_DOWN:tempY:" + K2HomeDeviceView.this.tempY);
                } else if (action == 1) {
                    Log.e("resOffset", "ACTION_UP");
                    K2HomeDeviceView.this.mVelocityTracker.computeCurrentVelocity(1000);
                    float xVelocity = K2HomeDeviceView.this.mVelocityTracker.getXVelocity();
                    float yVelocity = K2HomeDeviceView.this.mVelocityTracker.getYVelocity();
                    Log.e("resOffset", "xVelocity:" + xVelocity + " yVelocity:" + yVelocity);
                    K2HomeDeviceView.this.slidingUpPanelLayout.fling(K2HomeDeviceView.this.mVelocityTracker, xVelocity, yVelocity * (-1.0f));
                } else if (action == 2) {
                    float y = motionEvent.getY();
                    K2HomeDeviceView.this.slidingUpPanelLayout.smoothPanelView(y - K2HomeDeviceView.this.tempY);
                    K2HomeDeviceView.this.tempY = y;
                }
                return true;
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.widget.K2HomeDeviceView$3 */
    public class AnonymousClass3 implements ViewTreeObserver.OnGlobalLayoutListener {
        public AnonymousClass3() {
        }

        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public void onGlobalLayout() {
            int height = K2HomeDeviceView.this.llTopDes.getHeight();
            int bottom = K2HomeDeviceView.this.tvK2Volume.getBottom();
            if (K2HomeDeviceView.this.isFirst) {
                K2HomeDeviceView.this.distance = (height - bottom) - 10;
                K2HomeDeviceView.this.isFirst = false;
                if (K2HomeDeviceView.this.animationIsRunning) {
                    K2HomeDeviceView.this.cancelRunningAnimation();
                    K2HomeDeviceView.this.startRunningAnimation();
                }
            }
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.widget.K2HomeDeviceView$4 */
    public class AnonymousClass4 implements View.OnTouchListener {
        public AnonymousClass4() {
        }

        @Override // android.view.View.OnTouchListener
        public boolean onTouch(View view, MotionEvent motionEvent) {
            K2HomeDeviceView.this.mVelocityTracker.addMovement(motionEvent);
            int action = motionEvent.getAction();
            if (action == 0) {
                K2HomeDeviceView.this.tempY = motionEvent.getY();
                Log.e("resOffset", "ACTION_DOWN:tempY:" + K2HomeDeviceView.this.tempY);
            } else if (action == 1) {
                Log.e("resOffset", "ACTION_UP");
                K2HomeDeviceView.this.mVelocityTracker.computeCurrentVelocity(1000);
                float xVelocity = K2HomeDeviceView.this.mVelocityTracker.getXVelocity();
                float yVelocity = K2HomeDeviceView.this.mVelocityTracker.getYVelocity();
                Log.e("resOffset", "xVelocity:" + xVelocity + " yVelocity:" + yVelocity);
                K2HomeDeviceView.this.slidingUpPanelLayout.fling(K2HomeDeviceView.this.mVelocityTracker, xVelocity, yVelocity * (-1.0f));
            } else if (action == 2) {
                float y = motionEvent.getY();
                K2HomeDeviceView.this.slidingUpPanelLayout.smoothPanelView(y - K2HomeDeviceView.this.tempY);
                K2HomeDeviceView.this.tempY = y;
            }
            return true;
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
            this.promoteView.setPromoteViewOnItemListener(new PromoteView.PromoteViewOnItemListener() { // from class: com.petkit.android.activities.petkitBleDevice.widget.K2HomeDeviceView.5
                public AnonymousClass5() {
                }

                @Override // com.petkit.android.activities.mall.widget.PromoteView.PromoteViewOnItemListener
                public void onItemClick(PromoteData promoteData) {
                    EventBus.getDefault().post(promoteData);
                }
            });
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.widget.K2HomeDeviceView$5 */
    public class AnonymousClass5 implements PromoteView.PromoteViewOnItemListener {
        public AnonymousClass5() {
        }

        @Override // com.petkit.android.activities.mall.widget.PromoteView.PromoteViewOnItemListener
        public void onItemClick(PromoteData promoteData) {
            EventBus.getDefault().post(promoteData);
        }
    }

    private void initFrameAnimation() {
        this.fsvDeviceAni.setBitmapIds(Arrays.asList(Integer.valueOf(R.drawable.k2_device_ani_1), Integer.valueOf(R.drawable.k2_device_ani_2), Integer.valueOf(R.drawable.k2_device_ani_3), Integer.valueOf(R.drawable.k2_device_ani_4), Integer.valueOf(R.drawable.k2_device_ani_5), Integer.valueOf(R.drawable.k2_device_ani_6), Integer.valueOf(R.drawable.k2_device_ani_7), Integer.valueOf(R.drawable.k2_device_ani_8), Integer.valueOf(R.drawable.k2_device_ani_9), Integer.valueOf(R.drawable.k2_device_ani_10), Integer.valueOf(R.drawable.k2_device_ani_11), Integer.valueOf(R.drawable.k2_device_ani_12), Integer.valueOf(R.drawable.k2_device_ani_13), Integer.valueOf(R.drawable.k2_device_ani_14), Integer.valueOf(R.drawable.k2_device_ani_15), Integer.valueOf(R.drawable.k2_device_ani_16), Integer.valueOf(R.drawable.k2_device_ani_17), Integer.valueOf(R.drawable.k2_device_ani_18), Integer.valueOf(R.drawable.k2_device_ani_19), Integer.valueOf(R.drawable.k2_device_ani_20), Integer.valueOf(R.drawable.k2_device_ani_21), Integer.valueOf(R.drawable.k2_device_ani_22), Integer.valueOf(R.drawable.k2_device_ani_23), Integer.valueOf(R.drawable.k2_device_ani_24), Integer.valueOf(R.drawable.k2_device_ani_25), Integer.valueOf(R.drawable.k2_device_ani_26), Integer.valueOf(R.drawable.k2_device_ani_27), Integer.valueOf(R.drawable.k2_device_ani_28), Integer.valueOf(R.drawable.k2_device_ani_29), Integer.valueOf(R.drawable.k2_device_ani_30)));
        this.fsvDeviceAni.setDuration(3000);
    }

    public void setPointViewSize(int i) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.aniViewPointOne.getLayoutParams();
        layoutParams.height = this.aniViewOne.getHeight();
        layoutParams.width = this.aniViewOne.getWidth();
        layoutParams.setMargins(0, 0, 0, i);
        layoutParams.addRule(14);
        this.aniViewPointOne.setLayoutParams(layoutParams);
        this.aniViewPointTwo.setLayoutParams(layoutParams);
        this.aniViewPointThree.setLayoutParams(layoutParams);
    }

    public void cancelRunningAnimation() {
        if (this.animatorSetList.size() > 0) {
            Iterator<AnimatorSet> it = this.animatorSetList.iterator();
            while (it.hasNext()) {
                it.next().cancel();
            }
            this.animatorSetList.clear();
        }
        Disposable disposable = this.disposable;
        if (disposable != null && !disposable.isDisposed()) {
            this.disposable.dispose();
        }
        this.aniViewOne.setVisibility(4);
        this.aniViewPointOne.setVisibility(4);
        this.aniViewTwo.setVisibility(4);
        this.aniViewPointTwo.setVisibility(4);
        this.aniViewThree.setVisibility(4);
        this.aniViewPointThree.setVisibility(4);
        this.animationIsRunning = false;
    }

    public void startRunningAnimation() {
        if (this.animationIsRunning) {
            return;
        }
        startDeviceAnimation(this.aniViewOne, 0);
        startDevicePointAnimation(this.aniViewPointOne, 0);
        startDeviceAnimation(this.aniViewTwo, 2000);
        startDevicePointAnimation(this.aniViewPointTwo, 2000);
        startDeviceAnimation(this.aniViewThree, 4000);
        startDevicePointAnimation(this.aniViewPointThree, 4000);
        startFrameAnimation();
        this.animationIsRunning = true;
    }

    private void startFrameAnimation() {
        Observable.interval(5000L, ToolTipPopup.DEFAULT_POPUP_DISPLAY_TIME, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Long>() { // from class: com.petkit.android.activities.petkitBleDevice.widget.K2HomeDeviceView.6
            @Override // io.reactivex.Observer
            public void onComplete() {
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable th) {
            }

            public AnonymousClass6() {
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                K2HomeDeviceView.this.disposable = disposable;
            }

            @Override // io.reactivex.Observer
            public void onNext(Long l) {
                if (K2HomeDeviceView.this.fsvDeviceAni != null) {
                    K2HomeDeviceView.this.fsvDeviceAni.start();
                }
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.widget.K2HomeDeviceView$6 */
    public class AnonymousClass6 implements Observer<Long> {
        @Override // io.reactivex.Observer
        public void onComplete() {
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
        }

        public AnonymousClass6() {
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            K2HomeDeviceView.this.disposable = disposable;
        }

        @Override // io.reactivex.Observer
        public void onNext(Long l) {
            if (K2HomeDeviceView.this.fsvDeviceAni != null) {
                K2HomeDeviceView.this.fsvDeviceAni.start();
            }
        }
    }

    private void setAnimationViewSize() {
        initAnimationViewSize(this.aniViewOne);
        initAnimationViewSize(this.aniViewTwo);
        initAnimationViewSize(this.aniViewThree);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.widget.K2HomeDeviceView$7 */
    public class AnonymousClass7 implements ViewTreeObserver.OnGlobalLayoutListener {
        final /* synthetic */ View val$aniView;

        public AnonymousClass7(View view) {
            view = view;
        }

        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public void onGlobalLayout() {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
            int height = (view.getHeight() + ArmsUtils.dip2px(K2HomeDeviceView.this.activity, 0.0f)) * (-1);
            layoutParams.setMargins(0, 0, 0, height);
            view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            K2HomeDeviceView.this.setPointViewSize(height);
        }
    }

    private void initAnimationViewSize(View view) {
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.petkit.android.activities.petkitBleDevice.widget.K2HomeDeviceView.7
            final /* synthetic */ View val$aniView;

            public AnonymousClass7(View view2) {
                view = view2;
            }

            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                int height = (view.getHeight() + ArmsUtils.dip2px(K2HomeDeviceView.this.activity, 0.0f)) * (-1);
                layoutParams.setMargins(0, 0, 0, height);
                view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                K2HomeDeviceView.this.setPointViewSize(height);
            }
        });
    }

    private void startDeviceAnimation(View view, int i) {
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, Key.TRANSLATION_Y, 0.0f, -this.distance);
        objectAnimatorOfFloat.setRepeatMode(1);
        objectAnimatorOfFloat.setRepeatCount(-1);
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(view, Key.ALPHA, 0.0f, 0.8f, 0.0f);
        objectAnimatorOfFloat2.setRepeatMode(1);
        objectAnimatorOfFloat2.setRepeatCount(-1);
        ObjectAnimator objectAnimatorOfFloat3 = ObjectAnimator.ofFloat(view, Key.SCALE_X, 1.0f, 2.0f);
        objectAnimatorOfFloat3.setRepeatMode(1);
        objectAnimatorOfFloat3.setRepeatCount(-1);
        ObjectAnimator objectAnimatorOfFloat4 = ObjectAnimator.ofFloat(view, Key.SCALE_Y, 1.0f, 1.6f);
        objectAnimatorOfFloat4.setRepeatMode(1);
        objectAnimatorOfFloat4.setRepeatCount(-1);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.petkit.android.activities.petkitBleDevice.widget.K2HomeDeviceView.8
            public final /* synthetic */ View val$view;

            public AnonymousClass8(View view2) {
                view = view2;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                super.onAnimationStart(animator);
                view.setVisibility(0);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                view.setTranslationY(0.0f);
                view.setAlpha(0.0f);
                view.setScaleX(1.0f);
                view.setScaleY(1.0f);
            }
        });
        animatorSet.setDuration(ToolTipPopup.DEFAULT_POPUP_DISPLAY_TIME);
        animatorSet.setStartDelay(i);
        animatorSet.playTogether(objectAnimatorOfFloat2, objectAnimatorOfFloat3, objectAnimatorOfFloat4, objectAnimatorOfFloat);
        animatorSet.start();
        this.animatorSetList.add(animatorSet);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.widget.K2HomeDeviceView$8 */
    public class AnonymousClass8 extends AnimatorListenerAdapter {
        public final /* synthetic */ View val$view;

        public AnonymousClass8(View view2) {
            view = view2;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationStart(Animator animator) {
            super.onAnimationStart(animator);
            view.setVisibility(0);
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            super.onAnimationEnd(animator);
            view.setTranslationY(0.0f);
            view.setAlpha(0.0f);
            view.setScaleX(1.0f);
            view.setScaleY(1.0f);
        }
    }

    private void startDevicePointAnimation(View view, int i) {
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, Key.TRANSLATION_Y, 0.0f, -this.distance);
        objectAnimatorOfFloat.setRepeatMode(1);
        objectAnimatorOfFloat.setRepeatCount(-1);
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(view, Key.ALPHA, 0.0f, 0.8f, 0.0f);
        objectAnimatorOfFloat2.setRepeatMode(1);
        objectAnimatorOfFloat2.setRepeatCount(-1);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.petkit.android.activities.petkitBleDevice.widget.K2HomeDeviceView.9
            public final /* synthetic */ View val$view;

            public AnonymousClass9(View view2) {
                view = view2;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                super.onAnimationStart(animator);
                view.setVisibility(0);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                view.setTranslationY(0.0f);
                view.setAlpha(0.0f);
            }
        });
        animatorSet.setDuration(ToolTipPopup.DEFAULT_POPUP_DISPLAY_TIME);
        animatorSet.setStartDelay(i);
        animatorSet.playTogether(objectAnimatorOfFloat2, objectAnimatorOfFloat);
        animatorSet.start();
        this.animatorSetList.add(animatorSet);
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.widget.K2HomeDeviceView$9 */
    public class AnonymousClass9 extends AnimatorListenerAdapter {
        public final /* synthetic */ View val$view;

        public AnonymousClass9(View view2) {
            view = view2;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationStart(Animator animator) {
            super.onAnimationStart(animator);
            view.setVisibility(0);
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            super.onAnimationEnd(animator);
            view.setTranslationY(0.0f);
            view.setAlpha(0.0f);
        }
    }

    public void startUpArrowAnimation() {
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this.ivUpArrow, Key.TRANSLATION_Y, 0.0f, -12.0f);
        objectAnimatorOfFloat.setRepeatMode(1);
        objectAnimatorOfFloat.setRepeatCount(-1);
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(this.ivUpArrow, Key.ALPHA, 0.0f, 1.0f, 0.0f);
        objectAnimatorOfFloat2.setRepeatMode(1);
        objectAnimatorOfFloat2.setRepeatCount(5);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(3500L);
        animatorSet.playTogether(objectAnimatorOfFloat2, objectAnimatorOfFloat);
        animatorSet.start();
        this.animatorSetList.add(animatorSet);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:164:0x01cd  */
    @Override // android.view.View.OnClickListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onClick(android.view.View r14) {
        /*
            Method dump skipped, instruction units count: 884
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.petkit.android.activities.petkitBleDevice.widget.K2HomeDeviceView.onClick(android.view.View):void");
    }

    @Override // com.petkit.android.activities.petkitBleDevice.widget.PetkitSlidingUpPanelLayout.PanelSlideListener
    public void onPanelSlide(View view, float f) {
        boolean z = this.mSlideOffset < f;
        this.mSlideOffset = f;
        if (f >= 0.0f) {
            float f2 = 1.0f - (2.0f * f);
            if (z) {
                this.rlTopView.setAlpha(f2);
                if (this.visibility && f2 <= 0.0f) {
                    this.llStateTopPanel.setAlpha((-1.0f) * f2);
                }
            } else if (this.visibility && f2 <= 0.0f) {
                this.llStateTopPanel.setAlpha((-1.0f) * f2);
            } else {
                this.llStateTopPanel.setAlpha(0.0f);
                this.rlTopView.setAlpha(f2);
            }
            PetkitLog.d(Key.ALPHA, "alpha:" + f2 + " slideOffset:" + f);
        }
        K2DeviceViewPanelSlideListener k2DeviceViewPanelSlideListener = this.k2DeviceViewPanelSlideListener;
        if (k2DeviceViewPanelSlideListener != null) {
            k2DeviceViewPanelSlideListener.onPanelSlide(view, f);
        }
        int height = this.llBottomMenuView.getHeight();
        int height2 = this.llStateTopPanel.getHeight();
        int height3 = getHeight();
        PetkitLog.d("panelHeight:" + height + " height:" + height2 + " parentHeight:" + height3 + " visibility:" + this.visibility + " slideOffset:" + f);
        int i = height3 - height;
        this.visibility = this.llStateTopTextPanel.getHeight() <= i;
        if (this.isFirstOnPanelSlide) {
            this.llStateTopPanel.setLayoutParams(new FrameLayout.LayoutParams(-1, i));
            this.isFirstOnPanelSlide = false;
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.widget.PetkitSlidingUpPanelLayout.PanelSlideListener
    public void onPanelStateChanged(View view, PetkitSlidingUpPanelLayout.PanelState panelState, PetkitSlidingUpPanelLayout.PanelState panelState2) {
        if (panelState2.equals(PetkitSlidingUpPanelLayout.PanelState.EXPANDED) || panelState2.equals(PetkitSlidingUpPanelLayout.PanelState.DRAGGING)) {
            this.fsvDeviceAni.setVisibility(4);
        } else if (!this.deviceAni) {
            this.fsvDeviceAni.setVisibility(4);
        } else {
            this.fsvDeviceAni.setVisibility(0);
        }
    }

    public void setK2Record(K2Record k2Record) {
        this.k2Record = k2Record;
        refreshView();
    }

    private void refreshView() {
        K2Record k2Record = this.k2Record;
        if (k2Record == null) {
            return;
        }
        if (CommonUtils.isSameTimeZoneAsLocal(k2Record.getLocale(), this.k2Record.getTimezone())) {
            this.timezoneIcon.setVisibility(4);
        } else {
            this.timezoneIcon.setVisibility(0);
        }
        this.k2MenuView.changeAllMenuAniState(false);
        boolean z = UserInforUtils.getCurrentLoginResult().getSettings().getTempUnit() == 0;
        this.tvTemperature.setText(this.mContext.getString(R.string.Indoor_temp_format, ""));
        this.tvTopTemperature.setText(this.mContext.getString(R.string.Indoor_temp_format, ""));
        if (this.k2Record.getState() != null && this.k2Record.getState().getOverall() == 2) {
            setConsumablesInletGone();
            cancelRunningAnimation();
            this.tvWarnText.setVisibility(0);
            this.tvK2HomePrompt.setVisibility(0);
            this.ivDeviceIcon.setImageResource(R.drawable.k2_home_off_icon);
            this.tvK2Volume.setTextColor(this.mContext.getResources().getColor(R.color.black));
            SpannableString spannableString = new SpannableString(" - " + this.mContext.getString(R.string.Cubic_metres));
            spannableString.setSpan(new RelativeSizeSpan(0.6f), spannableString.toString().indexOf(this.mContext.getString(R.string.Cubic_metres)), spannableString.length(), 33);
            spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.walk_text)), spannableString.toString().indexOf(this.mContext.getString(R.string.Cubic_metres)), spannableString.length(), 33);
            this.tvK2Volume.setText(spannableString);
            this.tvK2Humidity.setText("-");
            TextView textView = this.tvK2Temperature;
            StringBuilder sb = new StringBuilder();
            sb.append("-");
            sb.append(z ? "˚C" : "˚F");
            textView.setText(sb.toString());
            this.tvK2TopHumidity.setText("-");
            TextView textView2 = this.tvK2TopTemperature;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("-");
            sb2.append(z ? "˚C" : "˚F");
            textView2.setText(sb2.toString());
            this.tvWarnText.setText(this.mContext.getString(R.string.Device_off_line_tip) + " >");
            this.tvWarnText.setTextColor(this.mContext.getResources().getColor(R.color.feeder_plan_save_black));
            this.k2MenuView.changeAllMask(true);
            this.k2VoiceSwitchCheckbox.setChecked(this.k2Record.getSettings().getSound() == 1);
            this.k2LightSwitchCheckbox.setChecked(this.k2Record.getSettings().getLightMode() == 1);
            this.k2LightSwitchCheckbox.setChecked(this.k2Record.getSettings().getLightMode() == 1);
            this.timeView.setTimeViewColor(this.mContext.getResources().getColor(R.color.edit_text), this.mContext.getResources().getColor(R.color.k2_time_off_bg_gray), this.mContext.getResources().getColor(R.color.walk_text));
            this.tvTimeContent.setTextColor(this.mContext.getResources().getColor(R.color.walk_text));
            new HashMap().put("type", Constants.HOME_TODO_CARD_DEVICE_ERR_OFFLINE);
        } else if (this.k2Record.getState() != null && this.k2Record.getState().getOverall() == 4) {
            setConsumablesInletGone();
            this.tvWarnText.setTextColor(this.mContext.getResources().getColor(R.color.k2_warn_red));
            this.tvWarnText.setVisibility(0);
            new HashMap().put("type", this.k2Record.getState().getErrorCode());
            if (this.k2Record.getState().getErrorLevel() == 1) {
                cancelRunningAnimation();
                this.tvK2Volume.setVisibility(4);
                this.tvK2HomePrompt.setVisibility(4);
                this.timeView.setTimeViewColor(this.mContext.getResources().getColor(R.color.edit_text), this.mContext.getResources().getColor(R.color.k2_time_off_bg_gray), this.mContext.getResources().getColor(R.color.walk_text));
                this.tvTimeContent.setTextColor(this.mContext.getResources().getColor(R.color.walk_text));
                this.ivDeviceIcon.setImageResource(R.drawable.k2_home_error_icon);
                this.tvK2Volume.setTextColor(this.mContext.getResources().getColor(R.color.black));
                SpannableString spannableString2 = new SpannableString(this.decimalFormat.format(this.k2Record.getState().getRefresh()) + " " + this.mContext.getString(R.string.Cubic_metres));
                spannableString2.setSpan(new RelativeSizeSpan(0.6f), spannableString2.toString().indexOf(this.mContext.getString(R.string.Cubic_metres)), spannableString2.length(), 33);
                spannableString2.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.walk_text)), spannableString2.toString().indexOf(this.mContext.getString(R.string.Cubic_metres)), spannableString2.length(), 33);
                this.tvK2Volume.setText(spannableString2);
                this.tvK2Humidity.setText(String.valueOf(Math.round((((float) this.k2Record.getState().getHumidity()) * 1.0f) / 10.0f)));
                this.tvK2TopHumidity.setText(String.valueOf(Math.round((this.k2Record.getState().getHumidity() * 1.0f) / 10.0f)));
                float temp = (this.k2Record.getState().getTemp() * 1.0f) / 10.0f;
                if (!z) {
                    temp = ((temp * 9.0f) / 5.0f) + 32.0f;
                }
                int iRound = Math.round(temp);
                TextView textView3 = this.tvK2Temperature;
                StringBuilder sb3 = new StringBuilder();
                sb3.append(String.valueOf(iRound));
                sb3.append(z ? "˚C" : "˚F");
                textView3.setText(sb3.toString());
                TextView textView4 = this.tvK2TopTemperature;
                StringBuilder sb4 = new StringBuilder();
                sb4.append(String.valueOf(iRound));
                sb4.append(z ? "˚C" : "˚F");
                textView4.setText(sb4.toString());
                this.k2MenuView.changeAllMask(true);
                this.k2MenuView.changeMenuBtnState(this.k2Record.getState().getMode() + 1);
                this.k2VoiceSwitchCheckbox.setChecked(this.k2Record.getSettings().getSound() == 1);
                this.k2LightSwitchCheckbox.setChecked(this.k2Record.getSettings().getLightMode() == 1);
                this.k2DeodorantSwitchCheckbox.setChecked(this.k2Record.getSettings().getLackNotify() == 1);
                TextView textView5 = this.tvLightTime;
                Context context = this.mContext;
                Integer num = this.k2Record.getSettings().getLightRange().get(0);
                num.intValue();
                int iIntValue = num.intValue();
                Integer num2 = this.k2Record.getSettings().getLightRange().get(1);
                num2.intValue();
                textView5.setText(BleDeviceUtils.formatLightTime(context, iIntValue, num2.intValue()));
                this.tvWarnText.setText(this.k2Record.getState().getErrorMsg() + " >");
            } else {
                this.timeView.setTimeViewColor(this.mContext.getResources().getColor(R.color.k2_main_green), this.mContext.getResources().getColor(R.color.k2_time_off_bg_gray), this.mContext.getResources().getColor(R.color.walk_text));
                this.tvTimeContent.setTextColor(this.mContext.getResources().getColor(R.color.k2_main_green));
                this.ivDeviceIcon.setImageResource(R.drawable.k2_home_on_icon);
                this.tvK2Volume.setTextColor(this.mContext.getResources().getColor(R.color.k2_main_green));
                SpannableString spannableString3 = new SpannableString(this.decimalFormat.format(this.k2Record.getState().getRefresh()) + " " + this.mContext.getString(R.string.Cubic_metres));
                spannableString3.setSpan(new RelativeSizeSpan(0.6f), spannableString3.toString().indexOf(this.mContext.getString(R.string.Cubic_metres)), spannableString3.length(), 33);
                spannableString3.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.walk_text)), spannableString3.toString().indexOf(this.mContext.getString(R.string.Cubic_metres)), spannableString3.length(), 33);
                this.tvK2Volume.setText(spannableString3);
                this.tvK2Humidity.setText(String.valueOf(Math.round((((float) this.k2Record.getState().getHumidity()) * 1.0f) / 10.0f)));
                this.tvK2TopHumidity.setText(String.valueOf(Math.round((this.k2Record.getState().getHumidity() * 1.0f) / 10.0f)));
                float temp2 = (this.k2Record.getState().getTemp() * 1.0f) / 10.0f;
                if (!z) {
                    temp2 = ((temp2 * 9.0f) / 5.0f) + 32.0f;
                }
                int iRound2 = Math.round(temp2);
                TextView textView6 = this.tvK2Temperature;
                StringBuilder sb5 = new StringBuilder();
                sb5.append(String.valueOf(iRound2));
                sb5.append(z ? "˚C" : "˚F");
                textView6.setText(sb5.toString());
                TextView textView7 = this.tvK2TopTemperature;
                StringBuilder sb6 = new StringBuilder();
                sb6.append(String.valueOf(iRound2));
                sb6.append(z ? "˚C" : "˚F");
                textView7.setText(sb6.toString());
                this.k2MenuView.changeAllMask(false);
                this.k2MenuView.changeMenuBtnState(this.k2Record.getState().getMode() + 1);
                this.k2VoiceSwitchCheckbox.setChecked(this.k2Record.getSettings().getSound() == 1);
                this.k2DeodorantSwitchCheckbox.setChecked(this.k2Record.getSettings().getLackNotify() == 1);
                TextView textView8 = this.tvLightTime;
                Context context2 = this.mContext;
                Integer num3 = this.k2Record.getSettings().getLightRange().get(0);
                num3.intValue();
                int iIntValue2 = num3.intValue();
                Integer num4 = this.k2Record.getSettings().getLightRange().get(1);
                num4.intValue();
                textView8.setText(BleDeviceUtils.formatLightTime(context2, iIntValue2, num4.intValue()));
                this.tvWarnText.setTextColor(this.mContext.getResources().getColor(R.color.k2_warn_red));
                this.tvWarnText.setText(this.k2Record.getState().getErrorMsg() + " >");
                if (this.k2Record.getState().getLiquid() < 10) {
                    cancelRunningAnimation();
                    this.tvWarnText.setTextColor(this.mContext.getResources().getColor(R.color.feeder_plan_save_black));
                    this.tvWarnText.setText(this.k2Record.getState().getErrorMsg() + " >");
                    this.tvK2Volume.setVisibility(4);
                    this.tvK2HomePrompt.setVisibility(4);
                } else {
                    startRunningAnimation();
                    this.tvK2Volume.setVisibility(0);
                    this.tvK2HomePrompt.setVisibility(0);
                }
            }
        } else {
            setConsumablesInletVisible();
            if (this.k2Record.getState() != null && this.k2Record.getState().getPower() == 0) {
                setConsumablesInletGone();
                cancelRunningAnimation();
                this.tvK2Volume.setVisibility(4);
                this.tvK2HomePrompt.setVisibility(4);
                this.ivDeviceIcon.setImageResource(R.drawable.k2_home_off_icon);
                this.tvK2Volume.setTextColor(this.mContext.getResources().getColor(R.color.black));
                SpannableString spannableString4 = new SpannableString(this.decimalFormat.format(this.k2Record.getState().getRefresh()) + " " + this.mContext.getString(R.string.Cubic_metres));
                spannableString4.setSpan(new RelativeSizeSpan(0.6f), spannableString4.toString().indexOf(this.mContext.getString(R.string.Cubic_metres)), spannableString4.length(), 33);
                spannableString4.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.walk_text)), spannableString4.toString().indexOf(this.mContext.getString(R.string.Cubic_metres)), spannableString4.length(), 33);
                this.tvK2Volume.setText(spannableString4);
                this.tvK2Humidity.setText(String.valueOf(Math.round((((float) this.k2Record.getState().getHumidity()) * 1.0f) / 10.0f)));
                this.tvK2TopHumidity.setText(String.valueOf(Math.round((this.k2Record.getState().getHumidity() * 1.0f) / 10.0f)));
                float temp3 = (this.k2Record.getState().getTemp() * 1.0f) / 10.0f;
                if (!z) {
                    temp3 = ((temp3 * 9.0f) / 5.0f) + 32.0f;
                }
                int iRound3 = Math.round(temp3);
                TextView textView9 = this.tvK2Temperature;
                StringBuilder sb7 = new StringBuilder();
                sb7.append(String.valueOf(iRound3));
                sb7.append(z ? "˚C" : "˚F");
                textView9.setText(sb7.toString());
                TextView textView10 = this.tvK2TopTemperature;
                StringBuilder sb8 = new StringBuilder();
                sb8.append(String.valueOf(iRound3));
                sb8.append(z ? "˚C" : "˚F");
                textView10.setText(sb8.toString());
                PetkitLog.d("setTemp:off:" + this.k2Record.getState().getTemp() + " :temp:" + iRound3);
                this.tvWarnText.setVisibility(8);
                this.k2MenuView.changeSingleMaskVisible(0);
                this.k2MenuView.changeAllMenuBtnState(false);
                this.k2VoiceSwitchCheckbox.setChecked(this.k2Record.getSettings().getSound() == 1);
                this.k2LightSwitchCheckbox.setChecked(this.k2Record.getSettings().getLightMode() == 1);
                this.k2DeodorantSwitchCheckbox.setChecked(this.k2Record.getSettings().getLackNotify() == 1);
                TextView textView11 = this.tvLightTime;
                Context context3 = this.mContext;
                Integer num5 = this.k2Record.getSettings().getLightRange().get(0);
                num5.intValue();
                int iIntValue3 = num5.intValue();
                Integer num6 = this.k2Record.getSettings().getLightRange().get(1);
                num6.intValue();
                textView11.setText(BleDeviceUtils.formatLightTime(context3, iIntValue3, num6.intValue()));
                this.timeView.setTimeViewColor(this.mContext.getResources().getColor(R.color.edit_text), this.mContext.getResources().getColor(R.color.k2_time_off_bg_gray), this.mContext.getResources().getColor(R.color.walk_text));
                this.tvTimeContent.setTextColor(this.mContext.getResources().getColor(R.color.walk_text));
            } else if (this.k2Record.getState() != null && this.k2Record.getState().getPower() == 2) {
                this.timeView.setTimeViewColor(this.mContext.getResources().getColor(R.color.k2_main_green), this.mContext.getResources().getColor(R.color.k2_time_bg_gray), this.mContext.getResources().getColor(R.color.feeder_scale_gray));
                this.tvTimeContent.setTextColor(this.mContext.getResources().getColor(R.color.k2_main_green));
                this.ivDeviceIcon.setImageResource(R.drawable.k2_home_on_icon);
                this.tvK2Volume.setVisibility(4);
                this.tvK2HomePrompt.setVisibility(4);
                cancelRunningAnimation();
                this.tvK2Humidity.setText(String.valueOf(Math.round((this.k2Record.getState().getHumidity() * 1.0f) / 10.0f)));
                this.tvK2TopHumidity.setText(String.valueOf(Math.round((this.k2Record.getState().getHumidity() * 1.0f) / 10.0f)));
                float temp4 = (this.k2Record.getState().getTemp() * 1.0f) / 10.0f;
                if (!z) {
                    temp4 = ((temp4 * 9.0f) / 5.0f) + 32.0f;
                }
                int iRound4 = Math.round(temp4);
                TextView textView12 = this.tvK2Temperature;
                StringBuilder sb9 = new StringBuilder();
                sb9.append(String.valueOf(iRound4));
                sb9.append(z ? "˚C" : "˚F");
                textView12.setText(sb9.toString());
                TextView textView13 = this.tvK2TopTemperature;
                StringBuilder sb10 = new StringBuilder();
                sb10.append(String.valueOf(iRound4));
                sb10.append(z ? "˚C" : "˚F");
                textView13.setText(sb10.toString());
                PetkitLog.d("setTemp:await:" + this.k2Record.getState().getTemp() + " :temp:" + iRound4);
                this.k2MenuView.changeAllMask(false);
                this.tvWarnText.setVisibility(0);
                this.tvWarnText.setText(this.k2Record.getState().getErrorMsg() + " >");
                this.tvWarnText.setTextColor(this.mContext.getResources().getColor(R.color.feeder_plan_save_black));
                if (this.k2Record.getState().getLiquid() < 10 && !TextUtils.isEmpty(this.k2Record.getState().getErrorMsg())) {
                    liquidStateEmpty();
                } else {
                    liquidStateFull();
                }
                this.k2MenuView.changeMenuBtnState(this.k2Record.getState().getMode() + 1);
                this.k2VoiceSwitchCheckbox.setChecked(this.k2Record.getSettings().getSound() == 1);
                this.k2LightSwitchCheckbox.setChecked(this.k2Record.getSettings().getLightMode() == 1);
                this.k2DeodorantSwitchCheckbox.setChecked(this.k2Record.getSettings().getLackNotify() == 1);
                TextView textView14 = this.tvLightTime;
                Context context4 = this.mContext;
                Integer num7 = this.k2Record.getSettings().getLightRange().get(0);
                num7.intValue();
                int iIntValue4 = num7.intValue();
                Integer num8 = this.k2Record.getSettings().getLightRange().get(1);
                num8.intValue();
                textView14.setText(BleDeviceUtils.formatLightTime(context4, iIntValue4, num8.intValue()));
            } else {
                this.timeView.setTimeViewColor(this.mContext.getResources().getColor(R.color.k2_main_green), this.mContext.getResources().getColor(R.color.k2_time_bg_gray), this.mContext.getResources().getColor(R.color.feeder_scale_gray));
                this.tvTimeContent.setTextColor(this.mContext.getResources().getColor(R.color.k2_main_green));
                this.ivDeviceIcon.setImageResource(R.drawable.k2_home_on_icon);
                this.tvK2Volume.setVisibility(0);
                this.tvK2HomePrompt.setVisibility(0);
                this.tvK2Volume.setTextColor(this.mContext.getResources().getColor(R.color.k2_main_green));
                SpannableString spannableString5 = new SpannableString(this.decimalFormat.format(this.k2Record.getState().getRefresh()) + " " + this.mContext.getString(R.string.Cubic_metres));
                spannableString5.setSpan(new RelativeSizeSpan(0.6f), spannableString5.toString().indexOf(this.mContext.getString(R.string.Cubic_metres)), spannableString5.length(), 33);
                spannableString5.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.walk_text)), spannableString5.toString().indexOf(this.mContext.getString(R.string.Cubic_metres)), spannableString5.length(), 33);
                this.tvK2Volume.setText(spannableString5);
                this.tvK2Humidity.setText(String.valueOf(Math.round((((float) this.k2Record.getState().getHumidity()) * 1.0f) / 10.0f)));
                this.tvK2TopHumidity.setText(String.valueOf(Math.round((this.k2Record.getState().getHumidity() * 1.0f) / 10.0f)));
                float temp5 = (this.k2Record.getState().getTemp() * 1.0f) / 10.0f;
                if (!z) {
                    temp5 = ((temp5 * 9.0f) / 5.0f) + 32.0f;
                }
                int iRound5 = Math.round(temp5);
                TextView textView15 = this.tvK2Temperature;
                StringBuilder sb11 = new StringBuilder();
                sb11.append(String.valueOf(iRound5));
                sb11.append(z ? "˚C" : "˚F");
                textView15.setText(sb11.toString());
                TextView textView16 = this.tvK2TopTemperature;
                StringBuilder sb12 = new StringBuilder();
                sb12.append(String.valueOf(iRound5));
                sb12.append(z ? "˚C" : "˚F");
                textView16.setText(sb12.toString());
                PetkitLog.d("setTemp:on:" + this.k2Record.getState().getTemp() + " :temp:" + iRound5);
                this.k2MenuView.changeAllMask(false);
                this.tvWarnText.setVisibility(8);
                this.k2MenuView.changeMenuBtnState(this.k2Record.getState().getMode() + 1);
                this.k2VoiceSwitchCheckbox.setChecked(this.k2Record.getSettings().getSound() == 1);
                this.k2LightSwitchCheckbox.setChecked(this.k2Record.getSettings().getLightMode() == 1);
                this.k2DeodorantSwitchCheckbox.setChecked(this.k2Record.getSettings().getLackNotify() == 1);
                TextView textView17 = this.tvLightTime;
                Context context5 = this.mContext;
                Integer num9 = this.k2Record.getSettings().getLightRange().get(0);
                num9.intValue();
                int iIntValue5 = num9.intValue();
                Integer num10 = this.k2Record.getSettings().getLightRange().get(1);
                num10.intValue();
                textView17.setText(BleDeviceUtils.formatLightTime(context5, iIntValue5, num10.intValue()));
                startRunningAnimation();
                if (checkNowMode()) {
                    this.tvWarnText.setVisibility(0);
                    this.tvWarnText.setTextColor(this.mContext.getResources().getColor(R.color.feeder_plan_save_black));
                    String string = this.mContext.getResources().getString(R.string.Immediately_switch);
                    SpannableString spannableString6 = new SpannableString(this.mContext.getResources().getString(R.string.Manual_work_prompt, string));
                    spannableString6.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.k2_main_green)), spannableString6.toString().indexOf(string), spannableString6.toString().indexOf(string) + string.length(), 33);
                    spannableString6.setSpan(new ClickableSpan() { // from class: com.petkit.android.activities.petkitBleDevice.widget.K2HomeDeviceView.10
                        public AnonymousClass10() {
                        }

                        @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
                        public void updateDrawState(TextPaint textPaint) {
                            super.updateDrawState(textPaint);
                            textPaint.setColor(K2HomeDeviceView.this.getResources().getColor(R.color.k2_main_green));
                            textPaint.setUnderlineText(true);
                        }

                        @Override // android.text.style.ClickableSpan
                        public void onClick(View view) {
                            if (K2HomeDeviceView.this.onMenuClickListener != null) {
                                K2HomeDeviceView.this.onMenuClickListener.onSwitchClick();
                            }
                        }
                    }, spannableString6.toString().indexOf(string), spannableString6.toString().indexOf(string) + string.length(), 33);
                    this.tvWarnText.setHighlightColor(0);
                    this.tvWarnText.setMovementMethod(LinkMovementMethod.getInstance());
                    this.tvWarnText.setText(spannableString6);
                }
                if (!TextUtils.isEmpty(this.k2Record.getState().getErrorCode()) && this.k2Record.getState().getErrorLevel() != 3) {
                    this.tvWarnText.setText(this.k2Record.getState().getErrorMsg() + " >");
                    this.tvWarnText.setTextColor(this.mContext.getResources().getColor(R.color.k2_warn_red));
                    this.tvWarnText.setVisibility(0);
                }
                if (this.k2Record.getState().getLiquid() < 10 && !TextUtils.isEmpty(this.k2Record.getState().getErrorMsg())) {
                    cancelRunningAnimation();
                    liquidStateEmpty();
                    this.tvWarnText.setTextColor(this.mContext.getResources().getColor(R.color.feeder_plan_save_black));
                    this.tvWarnText.setText(this.k2Record.getState().getErrorMsg() + " >");
                    this.tvWarnText.setVisibility(0);
                    this.tvK2Volume.setVisibility(4);
                    this.tvK2HomePrompt.setVisibility(4);
                } else {
                    liquidStateFull();
                }
                if (this.k2Record.getState().getRefresh() <= 0.0d || this.k2Record.getState().getLiquid() < 10) {
                    this.tvK2HomePrompt.setVisibility(4);
                    this.tvK2Volume.setVisibility(4);
                } else {
                    this.tvK2HomePrompt.setVisibility(0);
                    this.tvK2Volume.setVisibility(0);
                }
            }
        }
        if (this.k2Record.getSettings().getLightMode() == 1) {
            this.tvLightTime.setTextColor(this.mContext.getResources().getColor(R.color.k2_main_green));
        } else {
            this.tvLightTime.setTextColor(this.mContext.getResources().getColor(R.color.walk_text));
        }
        if (this.k2Record.getState().getLiquid() < 10) {
            new HashMap().put("type", "lackLiquid");
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.widget.K2HomeDeviceView$10 */
    public class AnonymousClass10 extends ClickableSpan {
        public AnonymousClass10() {
        }

        @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
        public void updateDrawState(TextPaint textPaint) {
            super.updateDrawState(textPaint);
            textPaint.setColor(K2HomeDeviceView.this.getResources().getColor(R.color.k2_main_green));
            textPaint.setUnderlineText(true);
        }

        @Override // android.text.style.ClickableSpan
        public void onClick(View view) {
            if (K2HomeDeviceView.this.onMenuClickListener != null) {
                K2HomeDeviceView.this.onMenuClickListener.onSwitchClick();
            }
        }
    }

    private void setConsumablesInletVisible() {
        this.rlLiquidState.setVisibility(0);
    }

    private void setConsumablesInletGone() {
        this.rlLiquidState.setVisibility(8);
    }

    private void liquidStateEmpty() {
        this.tvLiquidState.setText(this.mContext.getResources().getString(R.string.K3_Liquid_lack_title));
        this.tvLiquidState.setBackground(this.mContext.getResources().getDrawable(R.drawable.shape_t4_state_red_bg));
        this.ivLiquidState.setImageResource(R.drawable.t4_state_red_icon);
        ObjectAnimator objectAnimator = this.liquideAlphaAnimator;
        if (objectAnimator == null || !objectAnimator.isRunning()) {
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this.ivLiquidState, Key.ALPHA, 0.0f, 1.0f, 0.0f);
            this.liquideAlphaAnimator = objectAnimatorOfFloat;
            objectAnimatorOfFloat.setDuration(2000L);
            this.liquideAlphaAnimator.setInterpolator(new LinearInterpolator());
            this.liquideAlphaAnimator.setRepeatCount(-1);
            this.liquideAlphaAnimator.setRepeatMode(1);
            this.liquideAlphaAnimator.start();
            this.mainHandler.postDelayed(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.widget.K2HomeDeviceView.11
                public AnonymousClass11() {
                }

                @Override // java.lang.Runnable
                public void run() {
                    if (K2HomeDeviceView.this.liquideAlphaAnimator != null) {
                        K2HomeDeviceView.this.liquideAlphaAnimator.cancel();
                    }
                }
            }, 7000L);
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.widget.K2HomeDeviceView$11 */
    public class AnonymousClass11 implements Runnable {
        public AnonymousClass11() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (K2HomeDeviceView.this.liquideAlphaAnimator != null) {
                K2HomeDeviceView.this.liquideAlphaAnimator.cancel();
            }
        }
    }

    private void liquidStateFull() {
        this.tvLiquidState.setText(this.mContext.getResources().getString(R.string.K3_Liquid_adequate_title));
        this.tvLiquidState.setBackground(this.mContext.getResources().getDrawable(R.drawable.shape_t4_state_blue_bg));
        this.ivLiquidState.setImageResource(R.drawable.t4_state_blue_icon);
        ObjectAnimator objectAnimator = this.liquideAlphaAnimator;
        if (objectAnimator != null) {
            objectAnimator.cancel();
            this.liquideAlphaAnimator = null;
        }
    }

    private boolean checkNowMode() {
        List<List<Integer>> workTime = this.k2Record.getWorkTime();
        if ((workTime == null || workTime.size() == 0) && this.k2Record.getState().getPower() != 2) {
            return true;
        }
        Calendar calendar = Calendar.getInstance();
        int i = (calendar.get(11) * 60) + calendar.get(12);
        boolean z = false;
        for (List<Integer> list : workTime) {
            if (list.get(0).intValue() <= i && i < list.get(1).intValue()) {
                z = true;
            }
        }
        return (z || this.k2Record.getState().getPower() == 2) ? false : true;
    }

    @Override // com.petkit.android.activities.petkitBleDevice.widget.K2HomeMenuView.OnMenuClickListener
    public void onMenuClick(K2MenuItem k2MenuItem, int i) {
        OnMenuClickListener onMenuClickListener;
        if (i == 0) {
            OnMenuClickListener onMenuClickListener2 = this.onMenuClickListener;
            if (onMenuClickListener2 != null) {
                onMenuClickListener2.onMenuClick(MenuType.ON_OFF);
            }
        } else if (i == 1) {
            OnMenuClickListener onMenuClickListener3 = this.onMenuClickListener;
            if (onMenuClickListener3 != null) {
                onMenuClickListener3.onMenuClick(MenuType.AUTO);
            }
        } else if (i == 2) {
            OnMenuClickListener onMenuClickListener4 = this.onMenuClickListener;
            if (onMenuClickListener4 != null) {
                onMenuClickListener4.onMenuClick(MenuType.MUTE);
            }
        } else if (i == 3) {
            OnMenuClickListener onMenuClickListener5 = this.onMenuClickListener;
            if (onMenuClickListener5 != null) {
                onMenuClickListener5.onMenuClick(MenuType.GENERAL);
            }
        } else if (i == 4 && (onMenuClickListener = this.onMenuClickListener) != null) {
            onMenuClickListener.onMenuClick(MenuType.POWERFUL);
        }
        this.k2MenuView.changeMenuAniState(i, true);
    }

    public void changeMenuBtnState(int i) {
        this.k2MenuView.changeAllMenuAniState(false);
        this.k2MenuView.changeMenuBtnState(i);
    }

    @Override // com.petkit.android.activities.petkitBleDevice.widget.BleDeviceHomeOfflineWarnWindow.OfflineClickListener
    public void onClickSeeDetails() {
        Activity activity = this.activity;
        activity.startActivity(BleDeviceWifiSettingActivity.newIntent(activity, this.k2Record.getDeviceId(), 8));
    }

    @Override // com.petkit.android.activities.petkitBleDevice.widget.BleDeviceHomeOfflineWarnWindow.OfflineClickListener
    public void onClickReset() {
        Activity activity = this.activity;
        activity.startActivity(BleDeviceBindNetWorkActivity.newIntent((Context) activity, this.k2Record.getDeviceId(), 8, this.k2Record.getBtMac(), false));
    }

    public void setTimeSetting(List<K2TimingResult> list) {
        if (list == null) {
            return;
        }
        this.result = list;
        ArrayList arrayList = new ArrayList();
        for (K2TimingResult k2TimingResult : list) {
            arrayList.add(new TimeViewResult(k2TimingResult.getStartTime(), k2TimingResult.getEndTime()));
        }
        this.timeView.setTimeDataList(arrayList);
        int endTime = 0;
        for (K2TimingResult k2TimingResult2 : list) {
            endTime += k2TimingResult2.getEndTime() - k2TimingResult2.getStartTime();
        }
        int i = endTime / 60;
        int i2 = endTime % 60;
        if (this.k2Record.getState().getPower() == 0) {
            this.tvTimeContent.setText("0" + this.mContext.getResources().getString(R.string.Unit_hour));
            return;
        }
        TextView textView = this.tvTimeContent;
        StringBuilder sb = new StringBuilder();
        sb.append(i);
        sb.append(this.mContext.getResources().getString(R.string.Unit_hours));
        sb.append(i2 > 0 ? i2 + this.mContext.getResources().getString(R.string.Unit_minutes) : "");
        textView.setText(sb.toString());
    }

    public FrameSurfaceView getFsvDeviceAni() {
        return this.fsvDeviceAni;
    }

    public void setFsvDeviceAni(FrameSurfaceView frameSurfaceView) {
        this.fsvDeviceAni = frameSurfaceView;
    }

    public boolean isDeviceAni() {
        return this.deviceAni;
    }

    public void setDeviceAni(boolean z) {
        this.deviceAni = z;
    }

    public K2DeviceViewPanelSlideListener getK2DeviceViewPanelSlideListener() {
        return this.k2DeviceViewPanelSlideListener;
    }

    public void setK2DeviceViewPanelSlideListener(K2DeviceViewPanelSlideListener k2DeviceViewPanelSlideListener) {
        this.k2DeviceViewPanelSlideListener = k2DeviceViewPanelSlideListener;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        Disposable disposable = this.disposable;
        if (disposable != null && !disposable.isDisposed()) {
            this.disposable.dispose();
        }
        FrameSurfaceView frameSurfaceView = this.fsvDeviceAni;
        if (frameSurfaceView != null) {
            frameSurfaceView.destroy();
        }
        MainHandler mainHandler = this.mainHandler;
        if (mainHandler != null) {
            mainHandler.removeCallbacksAndMessages(null);
            this.mainHandler = null;
        }
        super.onDetachedFromWindow();
    }
}

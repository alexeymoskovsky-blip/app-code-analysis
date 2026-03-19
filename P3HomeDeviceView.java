package com.petkit.android.activities.petkitBleDevice.p3.widget;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.constraintlayout.motion.widget.Key;
import com.jess.arms.utils.ArmsUtils;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.community.WebviewActivity;
import com.petkit.android.activities.petkitBleDevice.ble.PetkitBleDeviceManager;
import com.petkit.android.activities.petkitBleDevice.mode.BleMenuItem;
import com.petkit.android.activities.petkitBleDevice.p3.mode.ActivityStatisticResult;
import com.petkit.android.activities.petkitBleDevice.p3.mode.CalorieStatisticResult;
import com.petkit.android.activities.petkitBleDevice.p3.mode.P3BarChartData;
import com.petkit.android.activities.petkitBleDevice.p3.mode.P3Record;
import com.petkit.android.activities.petkitBleDevice.p3.mode.P3SyncDataFlag;
import com.petkit.android.activities.petkitBleDevice.p3.mode.SleepDetailResult;
import com.petkit.android.activities.petkitBleDevice.p3.mode.SleepStatisticResult;
import com.petkit.android.activities.petkitBleDevice.p3.utils.P3Utils;
import com.petkit.android.activities.petkitBleDevice.p3.widget.P3BarChartView;
import com.petkit.android.activities.petkitBleDevice.utils.BleDeviceUtils;
import com.petkit.android.activities.petkitBleDevice.utils.NestedScrollableViewHelper;
import com.petkit.android.activities.petkitBleDevice.widget.BleDeviceHomeMenuView;
import com.petkit.android.activities.petkitBleDevice.widget.PetkitSlidingUpPanelLayout;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.DateUtil;
import com.petkit.android.utils.PetkitLog;
import com.petkit.oversea.R;
import io.agora.rtc2.internal.AudioDeviceInventoryLowerThanM;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes4.dex */
public class P3HomeDeviceView extends LinearLayout implements PetkitSlidingUpPanelLayout.PanelSlideListener, BleDeviceHomeMenuView.OnMenuClickListener, View.OnClickListener {
    private Activity activity;
    P3BarChartView.ChartOnClickListener activityClickListener;
    private List<ActivityStatisticResult> activityStatisticResults;
    private int activityStatisticType;
    private P3RatioView activityView;
    private float alpha;
    private BleDeviceHomeMenuView bleMenuView;
    P3BarChartView.ChartOnClickListener calorieClickListener;
    private List<CalorieStatisticResult> calorieStatisticResults;
    private int calorieStatisticType;
    private String calorieUnit;
    private P3RatioView calorieView;
    private SimpleDateFormat dateFormat;
    private Disposable disposable;
    private SimpleDateFormat format;
    private boolean isDeviceNotFound;
    private ImageView ivBatteryWarn;
    private ImageView ivUpArrow;
    private LinearLayout llCaloriePanel;
    private Context mContext;
    private float mSlideOffset;
    private VelocityTracker mVelocityTracker;
    private MainHandler mainHandler;
    private MenuOnClickListener menuOnClickListener;
    private P3BarChartView p3ActivityBarChartView;
    private P3BarChartView p3CalorieBarChartView;
    private P3CenterView p3CenterView;
    private P3Record p3Record;
    private P3BarChartView p3SleepBarChartView;
    private RippleView rippleView;
    private RelativeLayout rlRippleView;
    private RelativeLayout rlTimePanel;
    private RelativeLayout rlTopView;
    P3BarChartView.ChartOnClickListener sleepClickListener;
    private SleepDetailResult sleepDetailResult;
    private SleepQualityBarView sleepQualityBarView;
    private List<SleepStatisticResult> sleepStatisticResults;
    private int sleepStatisticType;
    private final String tag;
    private float tempY;
    private TodayBarChartView todayBarChartView;
    private TextView tvActivity;
    private TextView tvActivityConsume;
    private TextView tvActivityData;
    private TextView tvActivityStateRun;
    private TextView tvActivityStatisticsDate;
    private TextView tvActivityStatisticsMonth;
    private TextView tvActivityStatisticsWeek;
    private TextView tvActivityUnit;
    private TextView tvBaseConsume;
    private TextView tvBaseConsumeData;
    private TextView tvCalorie;
    private TextView tvCalorieStatisticsDate;
    private TextView tvCalorieStatisticsMonth;
    private TextView tvCalorieStatisticsWeek;
    private TextView tvCalorieUnit;
    private TextView tvDarkSleep;
    private TextView tvDeepSleepPrompt;
    private TextView tvKnowMoreAboutCalorie;
    private TextView tvLightSleep;
    private TextView tvLightSleepPrompt;
    private TextView tvSleep;
    private TextView tvSleepPrompt;
    private TextView tvSleepStatisticsDate;
    private TextView tvSleepStatisticsMonth;
    private TextView tvSleepStatisticsWeek;
    private TextView tvSleepUnit;
    private TextView tvStateWalk;
    private TextView tvTodayTrendTime;
    private TextView tvTodayTrendTitle;
    private TextView tvWarnText;
    private PetkitSlidingUpPanelLayout viewSlidingDrawerLayout;

    public interface MenuOnClickListener {
        void onBottomMenuClick(MenuType menuType);

        void onBottomMenuClick(MenuType menuType, String str);
    }

    public enum MenuType {
        SYNC_DATA,
        FIND_PET,
        WARN_PROMPT,
        CALORIE_TYPE_MONTH,
        CALORIE_TYPE_WEEK,
        CALORIE_TYPE_DATE,
        ACTIVITY_TYPE_MONTH,
        ACTIVITY_TYPE_WEEK,
        ACTIVITY_TYPE_DATE,
        SLEEP_TYPE_MONTH,
        SLEEP_TYPE_WEEK,
        SLEEP_TYPE_DATE,
        SLEEP_DETAIL_SWITCH
    }

    @Override // com.petkit.android.activities.petkitBleDevice.widget.PetkitSlidingUpPanelLayout.PanelSlideListener
    public void onPanelStateChanged(View view, PetkitSlidingUpPanelLayout.PanelState panelState, PetkitSlidingUpPanelLayout.PanelState panelState2) {
    }

    public P3HomeDeviceView(Context context) {
        super(context);
        this.tag = "P3HomeDeviceView";
        this.dateFormat = new SimpleDateFormat(DateUtil.DATE_FORMAT_7);
        this.format = new SimpleDateFormat("MM.dd");
        this.calorieStatisticType = 0;
        this.activityStatisticType = 0;
        this.sleepStatisticType = 0;
        this.calorieClickListener = new P3BarChartView.ChartOnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.p3.widget.P3HomeDeviceView.5
            @Override // com.petkit.android.activities.petkitBleDevice.p3.widget.P3BarChartView.ChartOnClickListener
            public void onChartClick(int i) {
                if (P3HomeDeviceView.this.calorieStatisticResults == null || P3HomeDeviceView.this.calorieStatisticResults.size() <= 0) {
                    return;
                }
                P3HomeDeviceView.this.tvCalorie.setText(P3Utils.getP3CalorieSpannableString(String.valueOf(((CalorieStatisticResult) P3HomeDeviceView.this.calorieStatisticResults.get(i)).getActivityCalorieSum() + ((CalorieStatisticResult) P3HomeDeviceView.this.calorieStatisticResults.get(i)).getBasicCalorieSum()), 0.5f));
                P3HomeDeviceView.this.tvBaseConsumeData.setText(P3Utils.getP3CalorieSpannableString(String.valueOf(((CalorieStatisticResult) P3HomeDeviceView.this.calorieStatisticResults.get(i)).getBasicCalorieSum()), 0.5f));
                P3HomeDeviceView.this.tvActivityData.setText(P3Utils.getP3CalorieSpannableString(String.valueOf(((CalorieStatisticResult) P3HomeDeviceView.this.calorieStatisticResults.get(i)).getActivityCalorieSum()), 0.5f));
                P3HomeDeviceView.this.calorieView.setColorRatio(((CalorieStatisticResult) P3HomeDeviceView.this.calorieStatisticResults.get(i)).getActivityCalorieSum(), ((CalorieStatisticResult) P3HomeDeviceView.this.calorieStatisticResults.get(i)).getBasicCalorieSum());
            }
        };
        this.activityClickListener = new P3BarChartView.ChartOnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.p3.widget.P3HomeDeviceView.6
            @Override // com.petkit.android.activities.petkitBleDevice.p3.widget.P3BarChartView.ChartOnClickListener
            public void onChartClick(int i) {
                if (P3HomeDeviceView.this.activityStatisticResults == null || P3HomeDeviceView.this.activityStatisticResults.size() <= 0) {
                    return;
                }
                P3HomeDeviceView.this.tvActivity.setText(P3Utils.getP3TimeSpannableString(P3Utils.timesToString(P3HomeDeviceView.this.mContext, ((ActivityStatisticResult) P3HomeDeviceView.this.activityStatisticResults.get(i)).getWalkSum(), ((ActivityStatisticResult) P3HomeDeviceView.this.activityStatisticResults.get(i)).getPlaySum()), 0.5f));
                P3HomeDeviceView.this.tvActivityStateRun.setText(P3Utils.getP3TimeSpannableString(P3Utils.timeToString(P3HomeDeviceView.this.mContext, ((ActivityStatisticResult) P3HomeDeviceView.this.activityStatisticResults.get(i)).getPlaySum()), 0.5f));
                P3HomeDeviceView.this.tvStateWalk.setText(P3Utils.getP3TimeSpannableString(P3Utils.timeToString(P3HomeDeviceView.this.mContext, ((ActivityStatisticResult) P3HomeDeviceView.this.activityStatisticResults.get(i)).getWalkSum()), 0.5f));
                P3HomeDeviceView.this.activityView.setColorRatio(((ActivityStatisticResult) P3HomeDeviceView.this.activityStatisticResults.get(i)).getPlaySum() / 60, ((ActivityStatisticResult) P3HomeDeviceView.this.activityStatisticResults.get(i)).getWalkSum() / 60);
            }
        };
        this.sleepClickListener = new P3BarChartView.ChartOnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.p3.widget.P3HomeDeviceView.7
            @Override // com.petkit.android.activities.petkitBleDevice.p3.widget.P3BarChartView.ChartOnClickListener
            public void onChartClick(int i) {
                if (P3HomeDeviceView.this.sleepStatisticResults == null || P3HomeDeviceView.this.sleepStatisticResults.size() <= 0) {
                    return;
                }
                P3HomeDeviceView.this.tvSleep.setText(P3Utils.getP3TimeSpannableString(P3Utils.timesToString(P3HomeDeviceView.this.mContext, ((SleepStatisticResult) P3HomeDeviceView.this.sleepStatisticResults.get(i)).getDeepSleepSum(), ((SleepStatisticResult) P3HomeDeviceView.this.sleepStatisticResults.get(i)).getLightSleepSum()), 0.5f));
                P3HomeDeviceView.this.tvDarkSleep.setText(P3Utils.getP3TimeSpannableString(P3Utils.timeToString(P3HomeDeviceView.this.mContext, ((SleepStatisticResult) P3HomeDeviceView.this.sleepStatisticResults.get(i)).getDeepSleepSum()), 0.5f));
                P3HomeDeviceView.this.tvLightSleep.setText(P3Utils.getP3TimeSpannableString(P3Utils.timeToString(P3HomeDeviceView.this.mContext, ((SleepStatisticResult) P3HomeDeviceView.this.sleepStatisticResults.get(i)).getLightSleepSum()), 0.5f));
                if (P3HomeDeviceView.this.menuOnClickListener == null || P3HomeDeviceView.this.sleepStatisticType != 0) {
                    return;
                }
                P3HomeDeviceView.this.menuOnClickListener.onBottomMenuClick(MenuType.SLEEP_DETAIL_SWITCH, ((SleepStatisticResult) P3HomeDeviceView.this.sleepStatisticResults.get(i)).getTime());
            }
        };
        this.mContext = context;
        this.activity = (Activity) context;
        initView();
    }

    public P3HomeDeviceView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.tag = "P3HomeDeviceView";
        this.dateFormat = new SimpleDateFormat(DateUtil.DATE_FORMAT_7);
        this.format = new SimpleDateFormat("MM.dd");
        this.calorieStatisticType = 0;
        this.activityStatisticType = 0;
        this.sleepStatisticType = 0;
        this.calorieClickListener = new P3BarChartView.ChartOnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.p3.widget.P3HomeDeviceView.5
            @Override // com.petkit.android.activities.petkitBleDevice.p3.widget.P3BarChartView.ChartOnClickListener
            public void onChartClick(int i) {
                if (P3HomeDeviceView.this.calorieStatisticResults == null || P3HomeDeviceView.this.calorieStatisticResults.size() <= 0) {
                    return;
                }
                P3HomeDeviceView.this.tvCalorie.setText(P3Utils.getP3CalorieSpannableString(String.valueOf(((CalorieStatisticResult) P3HomeDeviceView.this.calorieStatisticResults.get(i)).getActivityCalorieSum() + ((CalorieStatisticResult) P3HomeDeviceView.this.calorieStatisticResults.get(i)).getBasicCalorieSum()), 0.5f));
                P3HomeDeviceView.this.tvBaseConsumeData.setText(P3Utils.getP3CalorieSpannableString(String.valueOf(((CalorieStatisticResult) P3HomeDeviceView.this.calorieStatisticResults.get(i)).getBasicCalorieSum()), 0.5f));
                P3HomeDeviceView.this.tvActivityData.setText(P3Utils.getP3CalorieSpannableString(String.valueOf(((CalorieStatisticResult) P3HomeDeviceView.this.calorieStatisticResults.get(i)).getActivityCalorieSum()), 0.5f));
                P3HomeDeviceView.this.calorieView.setColorRatio(((CalorieStatisticResult) P3HomeDeviceView.this.calorieStatisticResults.get(i)).getActivityCalorieSum(), ((CalorieStatisticResult) P3HomeDeviceView.this.calorieStatisticResults.get(i)).getBasicCalorieSum());
            }
        };
        this.activityClickListener = new P3BarChartView.ChartOnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.p3.widget.P3HomeDeviceView.6
            @Override // com.petkit.android.activities.petkitBleDevice.p3.widget.P3BarChartView.ChartOnClickListener
            public void onChartClick(int i) {
                if (P3HomeDeviceView.this.activityStatisticResults == null || P3HomeDeviceView.this.activityStatisticResults.size() <= 0) {
                    return;
                }
                P3HomeDeviceView.this.tvActivity.setText(P3Utils.getP3TimeSpannableString(P3Utils.timesToString(P3HomeDeviceView.this.mContext, ((ActivityStatisticResult) P3HomeDeviceView.this.activityStatisticResults.get(i)).getWalkSum(), ((ActivityStatisticResult) P3HomeDeviceView.this.activityStatisticResults.get(i)).getPlaySum()), 0.5f));
                P3HomeDeviceView.this.tvActivityStateRun.setText(P3Utils.getP3TimeSpannableString(P3Utils.timeToString(P3HomeDeviceView.this.mContext, ((ActivityStatisticResult) P3HomeDeviceView.this.activityStatisticResults.get(i)).getPlaySum()), 0.5f));
                P3HomeDeviceView.this.tvStateWalk.setText(P3Utils.getP3TimeSpannableString(P3Utils.timeToString(P3HomeDeviceView.this.mContext, ((ActivityStatisticResult) P3HomeDeviceView.this.activityStatisticResults.get(i)).getWalkSum()), 0.5f));
                P3HomeDeviceView.this.activityView.setColorRatio(((ActivityStatisticResult) P3HomeDeviceView.this.activityStatisticResults.get(i)).getPlaySum() / 60, ((ActivityStatisticResult) P3HomeDeviceView.this.activityStatisticResults.get(i)).getWalkSum() / 60);
            }
        };
        this.sleepClickListener = new P3BarChartView.ChartOnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.p3.widget.P3HomeDeviceView.7
            @Override // com.petkit.android.activities.petkitBleDevice.p3.widget.P3BarChartView.ChartOnClickListener
            public void onChartClick(int i) {
                if (P3HomeDeviceView.this.sleepStatisticResults == null || P3HomeDeviceView.this.sleepStatisticResults.size() <= 0) {
                    return;
                }
                P3HomeDeviceView.this.tvSleep.setText(P3Utils.getP3TimeSpannableString(P3Utils.timesToString(P3HomeDeviceView.this.mContext, ((SleepStatisticResult) P3HomeDeviceView.this.sleepStatisticResults.get(i)).getDeepSleepSum(), ((SleepStatisticResult) P3HomeDeviceView.this.sleepStatisticResults.get(i)).getLightSleepSum()), 0.5f));
                P3HomeDeviceView.this.tvDarkSleep.setText(P3Utils.getP3TimeSpannableString(P3Utils.timeToString(P3HomeDeviceView.this.mContext, ((SleepStatisticResult) P3HomeDeviceView.this.sleepStatisticResults.get(i)).getDeepSleepSum()), 0.5f));
                P3HomeDeviceView.this.tvLightSleep.setText(P3Utils.getP3TimeSpannableString(P3Utils.timeToString(P3HomeDeviceView.this.mContext, ((SleepStatisticResult) P3HomeDeviceView.this.sleepStatisticResults.get(i)).getLightSleepSum()), 0.5f));
                if (P3HomeDeviceView.this.menuOnClickListener == null || P3HomeDeviceView.this.sleepStatisticType != 0) {
                    return;
                }
                P3HomeDeviceView.this.menuOnClickListener.onBottomMenuClick(MenuType.SLEEP_DETAIL_SWITCH, ((SleepStatisticResult) P3HomeDeviceView.this.sleepStatisticResults.get(i)).getTime());
            }
        };
        this.mContext = context;
        this.activity = (Activity) context;
        initView();
    }

    public P3HomeDeviceView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.tag = "P3HomeDeviceView";
        this.dateFormat = new SimpleDateFormat(DateUtil.DATE_FORMAT_7);
        this.format = new SimpleDateFormat("MM.dd");
        this.calorieStatisticType = 0;
        this.activityStatisticType = 0;
        this.sleepStatisticType = 0;
        this.calorieClickListener = new P3BarChartView.ChartOnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.p3.widget.P3HomeDeviceView.5
            @Override // com.petkit.android.activities.petkitBleDevice.p3.widget.P3BarChartView.ChartOnClickListener
            public void onChartClick(int i2) {
                if (P3HomeDeviceView.this.calorieStatisticResults == null || P3HomeDeviceView.this.calorieStatisticResults.size() <= 0) {
                    return;
                }
                P3HomeDeviceView.this.tvCalorie.setText(P3Utils.getP3CalorieSpannableString(String.valueOf(((CalorieStatisticResult) P3HomeDeviceView.this.calorieStatisticResults.get(i2)).getActivityCalorieSum() + ((CalorieStatisticResult) P3HomeDeviceView.this.calorieStatisticResults.get(i2)).getBasicCalorieSum()), 0.5f));
                P3HomeDeviceView.this.tvBaseConsumeData.setText(P3Utils.getP3CalorieSpannableString(String.valueOf(((CalorieStatisticResult) P3HomeDeviceView.this.calorieStatisticResults.get(i2)).getBasicCalorieSum()), 0.5f));
                P3HomeDeviceView.this.tvActivityData.setText(P3Utils.getP3CalorieSpannableString(String.valueOf(((CalorieStatisticResult) P3HomeDeviceView.this.calorieStatisticResults.get(i2)).getActivityCalorieSum()), 0.5f));
                P3HomeDeviceView.this.calorieView.setColorRatio(((CalorieStatisticResult) P3HomeDeviceView.this.calorieStatisticResults.get(i2)).getActivityCalorieSum(), ((CalorieStatisticResult) P3HomeDeviceView.this.calorieStatisticResults.get(i2)).getBasicCalorieSum());
            }
        };
        this.activityClickListener = new P3BarChartView.ChartOnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.p3.widget.P3HomeDeviceView.6
            @Override // com.petkit.android.activities.petkitBleDevice.p3.widget.P3BarChartView.ChartOnClickListener
            public void onChartClick(int i2) {
                if (P3HomeDeviceView.this.activityStatisticResults == null || P3HomeDeviceView.this.activityStatisticResults.size() <= 0) {
                    return;
                }
                P3HomeDeviceView.this.tvActivity.setText(P3Utils.getP3TimeSpannableString(P3Utils.timesToString(P3HomeDeviceView.this.mContext, ((ActivityStatisticResult) P3HomeDeviceView.this.activityStatisticResults.get(i2)).getWalkSum(), ((ActivityStatisticResult) P3HomeDeviceView.this.activityStatisticResults.get(i2)).getPlaySum()), 0.5f));
                P3HomeDeviceView.this.tvActivityStateRun.setText(P3Utils.getP3TimeSpannableString(P3Utils.timeToString(P3HomeDeviceView.this.mContext, ((ActivityStatisticResult) P3HomeDeviceView.this.activityStatisticResults.get(i2)).getPlaySum()), 0.5f));
                P3HomeDeviceView.this.tvStateWalk.setText(P3Utils.getP3TimeSpannableString(P3Utils.timeToString(P3HomeDeviceView.this.mContext, ((ActivityStatisticResult) P3HomeDeviceView.this.activityStatisticResults.get(i2)).getWalkSum()), 0.5f));
                P3HomeDeviceView.this.activityView.setColorRatio(((ActivityStatisticResult) P3HomeDeviceView.this.activityStatisticResults.get(i2)).getPlaySum() / 60, ((ActivityStatisticResult) P3HomeDeviceView.this.activityStatisticResults.get(i2)).getWalkSum() / 60);
            }
        };
        this.sleepClickListener = new P3BarChartView.ChartOnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.p3.widget.P3HomeDeviceView.7
            @Override // com.petkit.android.activities.petkitBleDevice.p3.widget.P3BarChartView.ChartOnClickListener
            public void onChartClick(int i2) {
                if (P3HomeDeviceView.this.sleepStatisticResults == null || P3HomeDeviceView.this.sleepStatisticResults.size() <= 0) {
                    return;
                }
                P3HomeDeviceView.this.tvSleep.setText(P3Utils.getP3TimeSpannableString(P3Utils.timesToString(P3HomeDeviceView.this.mContext, ((SleepStatisticResult) P3HomeDeviceView.this.sleepStatisticResults.get(i2)).getDeepSleepSum(), ((SleepStatisticResult) P3HomeDeviceView.this.sleepStatisticResults.get(i2)).getLightSleepSum()), 0.5f));
                P3HomeDeviceView.this.tvDarkSleep.setText(P3Utils.getP3TimeSpannableString(P3Utils.timeToString(P3HomeDeviceView.this.mContext, ((SleepStatisticResult) P3HomeDeviceView.this.sleepStatisticResults.get(i2)).getDeepSleepSum()), 0.5f));
                P3HomeDeviceView.this.tvLightSleep.setText(P3Utils.getP3TimeSpannableString(P3Utils.timeToString(P3HomeDeviceView.this.mContext, ((SleepStatisticResult) P3HomeDeviceView.this.sleepStatisticResults.get(i2)).getLightSleepSum()), 0.5f));
                if (P3HomeDeviceView.this.menuOnClickListener == null || P3HomeDeviceView.this.sleepStatisticType != 0) {
                    return;
                }
                P3HomeDeviceView.this.menuOnClickListener.onBottomMenuClick(MenuType.SLEEP_DETAIL_SWITCH, ((SleepStatisticResult) P3HomeDeviceView.this.sleepStatisticResults.get(i2)).getTime());
            }
        };
        this.mContext = context;
        this.activity = (Activity) context;
        initView();
    }

    private void initView() {
        this.mVelocityTracker = VelocityTracker.obtain();
        this.mainHandler = new MainHandler(this.mContext);
        View viewInflate = LayoutInflater.from(this.mContext).inflate(R.layout.layout_p3_home_device_view, (ViewGroup) null);
        addView(viewInflate, -1, -1);
        this.todayBarChartView = (TodayBarChartView) viewInflate.findViewById(R.id.p3_bar_chart);
        P3CenterView p3CenterView = (P3CenterView) viewInflate.findViewById(R.id.p3_center_view);
        this.p3CenterView = p3CenterView;
        p3CenterView.setP3State(0);
        this.p3CenterView.invalidate();
        TextView textView = (TextView) viewInflate.findViewById(R.id.tv_light_sleep_prompt);
        this.tvLightSleepPrompt = textView;
        textView.setText(this.mContext.getResources().getString(R.string.Sleep_light) + "/" + this.mContext.getResources().getString(R.string.Rest));
        TextView textView2 = (TextView) viewInflate.findViewById(R.id.tv_sleep_prompt);
        this.tvSleepPrompt = textView2;
        textView2.setText(this.mContext.getResources().getString(R.string.Sleep) + "/" + this.mContext.getResources().getString(R.string.Rest));
        this.rippleView = (RippleView) viewInflate.findViewById(R.id.ripple_view);
        this.rlRippleView = (RelativeLayout) viewInflate.findViewById(R.id.rl_ripple_view);
        SleepQualityBarView sleepQualityBarView = (SleepQualityBarView) viewInflate.findViewById(R.id.sleep_bar_view);
        this.sleepQualityBarView = sleepQualityBarView;
        sleepQualityBarView.setBarColors(CommonUtils.getColorById(R.color.p3_dark_purple), CommonUtils.getColorById(R.color.p3_light_purple));
        P3BarChartView p3BarChartView = (P3BarChartView) viewInflate.findViewById(R.id.p3_calorie_bar_chart_view);
        this.p3CalorieBarChartView = p3BarChartView;
        p3BarChartView.setChartOnClickListener(this.calorieClickListener);
        this.p3CalorieBarChartView.setSelectBarGradientColor(-19154, -40703);
        this.p3CalorieBarChartView.setColors(CommonUtils.getColorById(R.color.p3_main_orange), CommonUtils.getColorById(R.color.p3_main_orange), CommonUtils.getColorById(R.color.p3_main_orange), CommonUtils.getColorById(R.color.p3_center_bg_light_orange));
        this.p3CalorieBarChartView.setyType(0);
        P3BarChartView p3BarChartView2 = (P3BarChartView) viewInflate.findViewById(R.id.p3_activity_bar_chart_view);
        this.p3ActivityBarChartView = p3BarChartView2;
        p3BarChartView2.setChartOnClickListener(this.activityClickListener);
        this.p3ActivityBarChartView.setSelectBarGradientColor(-3080242, -11885312);
        this.p3ActivityBarChartView.setColors(CommonUtils.getColorById(R.color.p3_green), CommonUtils.getColorById(R.color.p3_green), CommonUtils.getColorById(R.color.p3_green), CommonUtils.getColorById(R.color.p3_light_green));
        this.p3ActivityBarChartView.setyType(1);
        P3BarChartView p3BarChartView3 = (P3BarChartView) viewInflate.findViewById(R.id.p3_sleep_bar_chart_view);
        this.p3SleepBarChartView = p3BarChartView3;
        p3BarChartView3.setChartOnClickListener(this.sleepClickListener);
        this.p3SleepBarChartView.setSelectBarGradientColor(-6062348, -9811739);
        this.p3SleepBarChartView.setColors(CommonUtils.getColorById(R.color.p3_dark_purple), CommonUtils.getColorById(R.color.p3_dark_purple), CommonUtils.getColorById(R.color.p3_dark_purple), CommonUtils.getColorById(R.color.p3_light_purple));
        this.p3SleepBarChartView.setyType(1);
        this.tvDeepSleepPrompt = (TextView) viewInflate.findViewById(R.id.tv_deep_sleep_prompt);
        P3RatioView p3RatioView = (P3RatioView) viewInflate.findViewById(R.id.calorie_view);
        this.calorieView = p3RatioView;
        p3RatioView.setCircleColorOne(R.color.p3_main_orange);
        this.calorieView.setCircleColorTwo(R.color.p3_center_bg_light_orange);
        this.calorieView.setCenterIcon(BitmapFactory.decodeResource(getResources(), R.drawable.calorie_center_icon));
        P3RatioView p3RatioView2 = (P3RatioView) viewInflate.findViewById(R.id.activity_view);
        this.activityView = p3RatioView2;
        p3RatioView2.setCircleColorOne(R.color.p3_green);
        this.activityView.setCircleColorTwo(R.color.p3_light_green);
        this.activityView.setCenterIcon(BitmapFactory.decodeResource(getResources(), R.drawable.activity_center_icon));
        this.tvCalorie = (TextView) viewInflate.findViewById(R.id.tv_calorie);
        this.tvCalorieStatisticsDate = (TextView) viewInflate.findViewById(R.id.tv_calorie_statistics_date);
        this.tvCalorieStatisticsWeek = (TextView) viewInflate.findViewById(R.id.tv_calorie_statistics_week);
        this.tvCalorieStatisticsMonth = (TextView) viewInflate.findViewById(R.id.tv_calorie_statistics_month);
        this.tvCalorieStatisticsDate.setSelected(true);
        this.tvCalorieStatisticsDate.setTextColor(this.mContext.getResources().getColor(R.color.p3_main_orange));
        this.tvKnowMoreAboutCalorie = (TextView) viewInflate.findViewById(R.id.tv_know_more_about_calorie);
        this.tvActivity = (TextView) viewInflate.findViewById(R.id.tv_activity);
        this.tvActivityData = (TextView) viewInflate.findViewById(R.id.tv_activity_consume_data);
        this.tvBaseConsumeData = (TextView) viewInflate.findViewById(R.id.tv_base_consume_data);
        this.tvActivityStatisticsDate = (TextView) viewInflate.findViewById(R.id.tv_activity_statistics_date);
        this.tvActivityStatisticsWeek = (TextView) viewInflate.findViewById(R.id.tv_activity_statistics_week);
        this.tvActivityStatisticsMonth = (TextView) viewInflate.findViewById(R.id.tv_activity_statistics_month);
        this.tvActivityStatisticsDate.setSelected(true);
        this.tvActivityStatisticsDate.setTextColor(this.mContext.getResources().getColor(R.color.p3_green));
        this.tvStateWalk = (TextView) viewInflate.findViewById(R.id.tv_state_walk);
        this.tvActivityStateRun = (TextView) viewInflate.findViewById(R.id.tv_activity_state_run);
        this.tvSleep = (TextView) viewInflate.findViewById(R.id.tv_sleep);
        this.tvSleepStatisticsDate = (TextView) viewInflate.findViewById(R.id.tv_sleep_statistics_date);
        this.tvSleepStatisticsWeek = (TextView) viewInflate.findViewById(R.id.tv_sleep_statistics_week);
        this.tvSleepStatisticsMonth = (TextView) viewInflate.findViewById(R.id.tv_sleep_statistics_month);
        this.tvSleepStatisticsDate.setSelected(true);
        this.tvSleepStatisticsDate.setTextColor(this.mContext.getResources().getColor(R.color.p3_dark_purple));
        this.tvDarkSleep = (TextView) viewInflate.findViewById(R.id.tv_dark_sleep);
        this.tvLightSleep = (TextView) viewInflate.findViewById(R.id.tv_light_sleep);
        this.ivBatteryWarn = (ImageView) viewInflate.findViewById(R.id.iv_battery_warn);
        this.llCaloriePanel = (LinearLayout) viewInflate.findViewById(R.id.ll_calorie_panel);
        this.tvBaseConsume = (TextView) viewInflate.findViewById(R.id.tv_base_consume);
        this.tvActivityConsume = (TextView) viewInflate.findViewById(R.id.tv_activity_consume);
        this.tvTodayTrendTitle = (TextView) findViewById(R.id.tv_today_trend_title);
        this.tvTodayTrendTime = (TextView) findViewById(R.id.tv_today_trend_time);
        this.tvWarnText = (TextView) viewInflate.findViewById(R.id.tv_warn_text);
        this.ivUpArrow = (ImageView) viewInflate.findViewById(R.id.iv_up_arrow);
        this.rlTopView = (RelativeLayout) viewInflate.findViewById(R.id.rl_top_view);
        this.bleMenuView = (BleDeviceHomeMenuView) viewInflate.findViewById(R.id.ble_menu_view);
        this.rlTimePanel = (RelativeLayout) viewInflate.findViewById(R.id.rl_time_panel);
        this.tvCalorieUnit = (TextView) viewInflate.findViewById(R.id.tv_calorie_unit);
        this.tvActivityUnit = (TextView) viewInflate.findViewById(R.id.tv_activity_unit);
        this.tvSleepUnit = (TextView) viewInflate.findViewById(R.id.tv_sleep_unit);
        PetkitSlidingUpPanelLayout petkitSlidingUpPanelLayout = (PetkitSlidingUpPanelLayout) viewInflate.findViewById(R.id.view_slidingDrawer);
        this.viewSlidingDrawerLayout = petkitSlidingUpPanelLayout;
        petkitSlidingUpPanelLayout.setScrollableViewHelper(new NestedScrollableViewHelper());
        this.viewSlidingDrawerLayout.addPanelSlideListener(this);
        this.bleMenuView.setDeviceType(12, 0);
        this.bleMenuView.setOnMenuClickListener(this);
        this.tvWarnText.setOnClickListener(this);
        this.ivBatteryWarn.setOnClickListener(this);
        this.tvCalorieStatisticsDate.setOnClickListener(this);
        this.tvCalorieStatisticsWeek.setOnClickListener(this);
        this.tvCalorieStatisticsMonth.setOnClickListener(this);
        this.tvSleepStatisticsDate.setOnClickListener(this);
        this.tvSleepStatisticsMonth.setOnClickListener(this);
        this.tvSleepStatisticsWeek.setOnClickListener(this);
        this.tvActivityStatisticsDate.setOnClickListener(this);
        this.tvActivityStatisticsWeek.setOnClickListener(this);
        this.tvActivityStatisticsMonth.setOnClickListener(this);
        this.activity.getWindow().getDecorView().setSystemUiVisibility(0);
        initViewSizeAndPosition();
        this.calorieUnit = " " + this.mContext.getString(R.string.Unit_kcal);
        String string = this.mContext.getString(R.string.Consumption_definition);
        SpannableString spannableString = new SpannableString(string);
        spannableString.setSpan(new ForegroundColorSpan(CommonUtils.getColorById(R.color.p3_main_orange)), spannableString.toString().indexOf(string), spannableString.toString().indexOf(string) + string.length(), 33);
        spannableString.setSpan(new ClickableSpan() { // from class: com.petkit.android.activities.petkitBleDevice.p3.widget.P3HomeDeviceView.1
            @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
            public void updateDrawState(TextPaint textPaint) {
                super.updateDrawState(textPaint);
                textPaint.setColor(CommonUtils.getColorById(R.color.p3_main_orange));
                textPaint.setUnderlineText(true);
            }

            @Override // android.text.style.ClickableSpan
            public void onClick(View view) {
                P3HomeDeviceView.this.activity.startActivity(WebviewActivity.newIntent(P3HomeDeviceView.this.activity, "", ApiTools.getWebUrlByKey("activity_consume")));
            }
        }, spannableString.toString().indexOf(string), spannableString.toString().indexOf(string) + string.length(), 33);
        this.tvKnowMoreAboutCalorie.setHighlightColor(0);
        this.tvKnowMoreAboutCalorie.setMovementMethod(LinkMovementMethod.getInstance());
        this.tvKnowMoreAboutCalorie.setText(spannableString);
        this.tvCalorieUnit.setText(this.mContext.getResources().getString(R.string.Unit_prompt, this.mContext.getResources().getString(R.string.Unit_kcal)));
        this.tvActivityUnit.setText(this.mContext.getResources().getString(R.string.Unit_prompt, this.mContext.getResources().getString(R.string.Unit_hour)));
        this.tvSleepUnit.setText(this.mContext.getResources().getString(R.string.Unit_prompt, this.mContext.getResources().getString(R.string.Unit_hour)));
    }

    private void initViewSizeAndPosition() {
        this.rlTimePanel.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.petkit.android.activities.petkitBleDevice.p3.widget.P3HomeDeviceView.2
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                final int height = P3HomeDeviceView.this.bleMenuView.getHeight();
                final int height2 = P3HomeDeviceView.this.rlTimePanel.getHeight();
                P3HomeDeviceView.this.mainHandler.postDelayed(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.p3.widget.P3HomeDeviceView.2.1
                    @Override // java.lang.Runnable
                    public void run() {
                        P3HomeDeviceView.this.viewSlidingDrawerLayout.setPanelHeight(height + (height2 / 3) + ArmsUtils.dip2px(P3HomeDeviceView.this.activity, 5.0f));
                    }
                }, 350L);
                int height3 = ((P3HomeDeviceView.this.viewSlidingDrawerLayout.getChildAt(0).getHeight() - (height + height2)) - P3HomeDeviceView.this.p3CenterView.getHeight()) - P3HomeDeviceView.this.tvWarnText.getHeight();
                if (height3 > 0) {
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) P3HomeDeviceView.this.p3CenterView.getLayoutParams();
                    float f = height3;
                    layoutParams.setMargins(layoutParams.leftMargin, (int) (0.4f * f), layoutParams.rightMargin, layoutParams.bottomMargin);
                    P3HomeDeviceView.this.p3CenterView.setLayoutParams(layoutParams);
                    RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) P3HomeDeviceView.this.ivBatteryWarn.getLayoutParams();
                    layoutParams2.setMargins(layoutParams.leftMargin, (int) (f * 0.8f), ArmsUtils.dip2px(P3HomeDeviceView.this.mContext, 15.0f), layoutParams.bottomMargin);
                    P3HomeDeviceView.this.ivBatteryWarn.setLayoutParams(layoutParams2);
                } else {
                    RelativeLayout.LayoutParams layoutParams3 = (RelativeLayout.LayoutParams) P3HomeDeviceView.this.p3CenterView.getLayoutParams();
                    layoutParams3.setMargins(layoutParams3.leftMargin, 0, layoutParams3.rightMargin, layoutParams3.bottomMargin);
                    P3HomeDeviceView.this.p3CenterView.setLayoutParams(layoutParams3);
                    RelativeLayout.LayoutParams layoutParams4 = (RelativeLayout.LayoutParams) P3HomeDeviceView.this.ivBatteryWarn.getLayoutParams();
                    layoutParams4.setMargins(layoutParams3.leftMargin, 0, ArmsUtils.dip2px(P3HomeDeviceView.this.mContext, 15.0f), layoutParams3.bottomMargin);
                    P3HomeDeviceView.this.ivBatteryWarn.setLayoutParams(layoutParams4);
                }
                P3HomeDeviceView.this.rlTimePanel.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        this.rlTopView.setOnTouchListener(new View.OnTouchListener() { // from class: com.petkit.android.activities.petkitBleDevice.p3.widget.P3HomeDeviceView.3
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                P3HomeDeviceView.this.mVelocityTracker.addMovement(motionEvent);
                int action = motionEvent.getAction();
                if (action == 0) {
                    P3HomeDeviceView.this.tempY = motionEvent.getY();
                } else if (action == 1) {
                    P3HomeDeviceView.this.mVelocityTracker.computeCurrentVelocity(1000);
                    P3HomeDeviceView.this.viewSlidingDrawerLayout.fling(P3HomeDeviceView.this.mVelocityTracker, P3HomeDeviceView.this.mVelocityTracker.getXVelocity(), P3HomeDeviceView.this.mVelocityTracker.getYVelocity() * (-1.0f));
                } else if (action == 2) {
                    float y = motionEvent.getY();
                    P3HomeDeviceView.this.viewSlidingDrawerLayout.smoothPanelView(y - P3HomeDeviceView.this.tempY);
                    P3HomeDeviceView.this.tempY = y;
                }
                return true;
            }
        });
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
            this.rlTopView.setAlpha(this.alpha);
        }
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

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.iv_battery_warn) {
            new P3WarnWindow(this.activity, this.mContext.getString(R.string.Prompt), this.activity.getString(R.string.P3_device_low_battery), true).showAtLocation(this.activity.getWindow().getDecorView(), 17, 0, 0);
            return;
        }
        if (id == R.id.tv_warn_text) {
            MenuOnClickListener menuOnClickListener = this.menuOnClickListener;
            if (menuOnClickListener != null) {
                menuOnClickListener.onBottomMenuClick(MenuType.WARN_PROMPT);
                return;
            }
            return;
        }
        if (id == R.id.tv_calorie_statistics_month) {
            this.p3CalorieBarChartView.setMaxCount(7);
            this.p3CalorieBarChartView.setXTextSize(28);
            this.tvCalorieStatisticsMonth.setSelected(true);
            this.tvCalorieStatisticsWeek.setSelected(false);
            this.tvCalorieStatisticsDate.setSelected(false);
            this.tvCalorieStatisticsMonth.setTextColor(getResources().getColor(R.color.p3_main_orange));
            this.tvCalorieStatisticsWeek.setTextColor(getResources().getColor(R.color.feeder_plan_save_black));
            this.tvCalorieStatisticsDate.setTextColor(getResources().getColor(R.color.feeder_plan_save_black));
            MenuOnClickListener menuOnClickListener2 = this.menuOnClickListener;
            if (menuOnClickListener2 != null) {
                this.calorieStatisticType = 2;
                menuOnClickListener2.onBottomMenuClick(MenuType.CALORIE_TYPE_MONTH);
                return;
            }
            return;
        }
        if (id == R.id.tv_calorie_statistics_week) {
            this.p3CalorieBarChartView.setMaxCount(5);
            this.p3CalorieBarChartView.setXTextSize(23);
            this.tvCalorieStatisticsWeek.setSelected(true);
            this.tvCalorieStatisticsMonth.setSelected(false);
            this.tvCalorieStatisticsDate.setSelected(false);
            this.tvCalorieStatisticsWeek.setTextColor(getResources().getColor(R.color.p3_main_orange));
            this.tvCalorieStatisticsMonth.setTextColor(getResources().getColor(R.color.feeder_plan_save_black));
            this.tvCalorieStatisticsDate.setTextColor(getResources().getColor(R.color.feeder_plan_save_black));
            MenuOnClickListener menuOnClickListener3 = this.menuOnClickListener;
            if (menuOnClickListener3 != null) {
                this.calorieStatisticType = 1;
                menuOnClickListener3.onBottomMenuClick(MenuType.CALORIE_TYPE_WEEK);
                return;
            }
            return;
        }
        if (id == R.id.tv_calorie_statistics_date) {
            this.p3CalorieBarChartView.setMaxCount(7);
            this.p3CalorieBarChartView.setXTextSize(28);
            this.tvCalorieStatisticsDate.setSelected(true);
            this.tvCalorieStatisticsWeek.setSelected(false);
            this.tvCalorieStatisticsMonth.setSelected(false);
            this.tvCalorieStatisticsDate.setTextColor(getResources().getColor(R.color.p3_main_orange));
            this.tvCalorieStatisticsWeek.setTextColor(getResources().getColor(R.color.feeder_plan_save_black));
            this.tvCalorieStatisticsMonth.setTextColor(getResources().getColor(R.color.feeder_plan_save_black));
            MenuOnClickListener menuOnClickListener4 = this.menuOnClickListener;
            if (menuOnClickListener4 != null) {
                this.calorieStatisticType = 0;
                menuOnClickListener4.onBottomMenuClick(MenuType.CALORIE_TYPE_DATE);
                return;
            }
            return;
        }
        if (id == R.id.tv_activity_statistics_month) {
            this.p3ActivityBarChartView.setMaxCount(7);
            this.p3ActivityBarChartView.setXTextSize(28);
            this.tvActivityStatisticsMonth.setSelected(true);
            this.tvActivityStatisticsWeek.setSelected(false);
            this.tvActivityStatisticsDate.setSelected(false);
            this.tvActivityStatisticsMonth.setTextColor(getResources().getColor(R.color.p3_green));
            this.tvActivityStatisticsWeek.setTextColor(getResources().getColor(R.color.feeder_plan_save_black));
            this.tvActivityStatisticsDate.setTextColor(getResources().getColor(R.color.feeder_plan_save_black));
            MenuOnClickListener menuOnClickListener5 = this.menuOnClickListener;
            if (menuOnClickListener5 != null) {
                this.activityStatisticType = 2;
                menuOnClickListener5.onBottomMenuClick(MenuType.ACTIVITY_TYPE_MONTH);
                return;
            }
            return;
        }
        if (id == R.id.tv_activity_statistics_week) {
            this.p3ActivityBarChartView.setMaxCount(5);
            this.p3ActivityBarChartView.setXTextSize(23);
            this.tvActivityStatisticsWeek.setSelected(true);
            this.tvActivityStatisticsMonth.setSelected(false);
            this.tvActivityStatisticsDate.setSelected(false);
            this.tvActivityStatisticsWeek.setTextColor(getResources().getColor(R.color.p3_green));
            this.tvActivityStatisticsMonth.setTextColor(getResources().getColor(R.color.feeder_plan_save_black));
            this.tvActivityStatisticsDate.setTextColor(getResources().getColor(R.color.feeder_plan_save_black));
            MenuOnClickListener menuOnClickListener6 = this.menuOnClickListener;
            if (menuOnClickListener6 != null) {
                this.activityStatisticType = 1;
                menuOnClickListener6.onBottomMenuClick(MenuType.ACTIVITY_TYPE_WEEK);
                return;
            }
            return;
        }
        if (id == R.id.tv_activity_statistics_date) {
            this.p3ActivityBarChartView.setMaxCount(7);
            this.p3ActivityBarChartView.setXTextSize(28);
            this.tvActivityStatisticsDate.setSelected(true);
            this.tvActivityStatisticsWeek.setSelected(false);
            this.tvActivityStatisticsMonth.setSelected(false);
            this.tvActivityStatisticsDate.setTextColor(getResources().getColor(R.color.p3_green));
            this.tvActivityStatisticsWeek.setTextColor(getResources().getColor(R.color.feeder_plan_save_black));
            this.tvActivityStatisticsMonth.setTextColor(getResources().getColor(R.color.feeder_plan_save_black));
            MenuOnClickListener menuOnClickListener7 = this.menuOnClickListener;
            if (menuOnClickListener7 != null) {
                this.activityStatisticType = 0;
                menuOnClickListener7.onBottomMenuClick(MenuType.ACTIVITY_TYPE_DATE);
                return;
            }
            return;
        }
        if (id == R.id.tv_sleep_statistics_month) {
            this.p3SleepBarChartView.setMaxCount(7);
            this.p3SleepBarChartView.setXTextSize(28);
            this.tvSleepStatisticsMonth.setSelected(true);
            this.tvSleepStatisticsWeek.setSelected(false);
            this.tvSleepStatisticsDate.setSelected(false);
            this.tvSleepStatisticsMonth.setTextColor(getResources().getColor(R.color.p3_dark_purple));
            this.tvSleepStatisticsWeek.setTextColor(getResources().getColor(R.color.feeder_plan_save_black));
            this.tvSleepStatisticsDate.setTextColor(getResources().getColor(R.color.feeder_plan_save_black));
            MenuOnClickListener menuOnClickListener8 = this.menuOnClickListener;
            if (menuOnClickListener8 != null) {
                this.sleepStatisticType = 2;
                menuOnClickListener8.onBottomMenuClick(MenuType.SLEEP_TYPE_MONTH);
            }
            this.sleepQualityBarView.setVisibility(8);
            return;
        }
        if (id == R.id.tv_sleep_statistics_week) {
            this.p3SleepBarChartView.setMaxCount(5);
            this.p3SleepBarChartView.setXTextSize(23);
            this.tvSleepStatisticsWeek.setSelected(true);
            this.tvSleepStatisticsMonth.setSelected(false);
            this.tvSleepStatisticsDate.setSelected(false);
            this.tvSleepStatisticsWeek.setTextColor(getResources().getColor(R.color.p3_dark_purple));
            this.tvSleepStatisticsMonth.setTextColor(getResources().getColor(R.color.feeder_plan_save_black));
            this.tvSleepStatisticsDate.setTextColor(getResources().getColor(R.color.feeder_plan_save_black));
            MenuOnClickListener menuOnClickListener9 = this.menuOnClickListener;
            if (menuOnClickListener9 != null) {
                this.sleepStatisticType = 1;
                menuOnClickListener9.onBottomMenuClick(MenuType.SLEEP_TYPE_WEEK);
            }
            this.sleepQualityBarView.setVisibility(8);
            return;
        }
        if (id == R.id.tv_sleep_statistics_date) {
            this.p3SleepBarChartView.setMaxCount(7);
            this.p3SleepBarChartView.setXTextSize(28);
            this.tvSleepStatisticsDate.setSelected(true);
            this.tvSleepStatisticsWeek.setSelected(false);
            this.tvSleepStatisticsMonth.setSelected(false);
            this.tvSleepStatisticsDate.setTextColor(getResources().getColor(R.color.p3_dark_purple));
            this.tvSleepStatisticsWeek.setTextColor(getResources().getColor(R.color.feeder_plan_save_black));
            this.tvSleepStatisticsMonth.setTextColor(getResources().getColor(R.color.feeder_plan_save_black));
            MenuOnClickListener menuOnClickListener10 = this.menuOnClickListener;
            if (menuOnClickListener10 != null) {
                this.sleepStatisticType = 0;
                menuOnClickListener10.onBottomMenuClick(MenuType.SLEEP_TYPE_DATE);
            }
            this.sleepQualityBarView.setVisibility(0);
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.widget.BleDeviceHomeMenuView.OnMenuClickListener
    public void onMenuClick(BleMenuItem bleMenuItem, int i) {
        MenuOnClickListener menuOnClickListener = this.menuOnClickListener;
        if (menuOnClickListener != null) {
            if (i == 0) {
                menuOnClickListener.onBottomMenuClick(MenuType.SYNC_DATA);
            } else {
                if (i != 1) {
                    return;
                }
                menuOnClickListener.onBottomMenuClick(MenuType.FIND_PET);
            }
        }
    }

    public MenuOnClickListener getMenuOnClickListener() {
        return this.menuOnClickListener;
    }

    public void setMenuOnClickListener(MenuOnClickListener menuOnClickListener) {
        this.menuOnClickListener = menuOnClickListener;
    }

    public void closeAllAni() {
        P3CenterView p3CenterView = this.p3CenterView;
        if (p3CenterView != null) {
            p3CenterView.closeAllAni();
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

    public void setP3CenterViewState(int i) {
        P3CenterView p3CenterView = this.p3CenterView;
        if (p3CenterView != null) {
            p3CenterView.setP3State(i);
        }
    }

    public void setWarnTextAndVisibility(String str, int i) {
        this.tvWarnText.setText(str);
        this.tvWarnText.setVisibility(i);
    }

    public void setP3Record(P3Record p3Record, boolean z) {
        this.p3Record = p3Record;
        this.isDeviceNotFound = z;
        refreshView();
    }

    private void refreshView() {
        if (!PetkitBleDeviceManager.getInstance().checkDeviceState(this.p3Record.getMac())) {
            this.tvWarnText.setVisibility(0);
            if (Build.VERSION.SDK_INT >= 31) {
                if (!CommonUtils.checkPermission((Activity) this.mContext, "android.permission.ACCESS_FINE_LOCATION") || !CommonUtils.checkPermission((Activity) this.mContext, "android.permission.BLUETOOTH_SCAN") || !CommonUtils.checkPermission((Activity) this.mContext, AudioDeviceInventoryLowerThanM.PERMISSION_BLUETOOTH_CONNECT) || !CommonUtils.checkPermission((Activity) this.mContext, "android.permission.BLUETOOTH_ADVERTISE")) {
                    this.tvWarnText.setText(this.mContext.getString(R.string.Location_permission_prompt) + " >");
                } else if (!CommonUtils.checkGPSIsOpened(this.mContext)) {
                    this.tvWarnText.setText(this.mContext.getString(R.string.Gps_permission_prompt) + " >");
                } else if (!CommonUtils.checkBlueTooth((Activity) this.mContext)) {
                    this.tvWarnText.setText(this.mContext.getString(R.string.Ble_device_not_open) + " >");
                } else if (this.isDeviceNotFound) {
                    this.tvWarnText.setText(this.mContext.getString(R.string.Ble_device_not_found));
                } else {
                    this.tvWarnText.setText(this.mContext.getString(R.string.Ble_device_connect_fail));
                    this.tvWarnText.setVisibility(4);
                }
            } else if (!CommonUtils.checkPermission((Activity) this.mContext, "android.permission.ACCESS_FINE_LOCATION")) {
                this.tvWarnText.setText(this.mContext.getString(R.string.Location_permission_prompt) + " >");
            } else if (!CommonUtils.checkGPSIsOpened(this.mContext)) {
                this.tvWarnText.setText(this.mContext.getString(R.string.Gps_permission_prompt) + " >");
            } else if (!CommonUtils.checkBlueTooth((Activity) this.mContext)) {
                this.tvWarnText.setText(this.mContext.getString(R.string.Ble_device_not_open) + " >");
            } else if (this.isDeviceNotFound) {
                this.tvWarnText.setText(this.mContext.getString(R.string.Ble_device_not_found));
            } else {
                this.tvWarnText.setText(this.mContext.getString(R.string.Ble_device_connect_fail));
                this.tvWarnText.setVisibility(4);
            }
            this.llCaloriePanel.setVisibility(0);
            this.p3CenterView.setP3State(0);
        } else {
            this.tvWarnText.setVisibility(4);
            if (((BaseApplication) CommonUtils.getAppContext()).getP3SyncDevice().containsKey(new P3SyncDataFlag(this.p3Record.getDeviceId()))) {
                this.llCaloriePanel.setVisibility(0);
                this.p3CenterView.setP3State(3);
                if (PetkitBleDeviceManager.getInstance().getBleClientByMacAndType(this.p3Record.getMac(), 12) == null) {
                    this.tvWarnText.setText(this.mContext.getResources().getString(R.string.BLEUI_synchronizing));
                } else {
                    this.tvWarnText.setText(this.mContext.getResources().getString(R.string.BLEUI_synchronizing) + PetkitBleDeviceManager.getInstance().getBleClientByMacAndType(this.p3Record.getMac(), 12).getProgress() + "%");
                }
                this.tvWarnText.setVisibility(0);
            } else {
                this.llCaloriePanel.setVisibility(0);
                this.p3CenterView.setP3State(2);
                this.tvWarnText.setVisibility(4);
            }
        }
        this.p3CenterView.setConsume(this.p3Record.getBasicCalorie(), this.p3Record.getActivityCalorie());
        this.tvBaseConsume.setText(P3Utils.getP3CalorieSpannableString(this.p3Record.getBasicCalorie() < 0 ? "- -" : String.valueOf(this.p3Record.getBasicCalorie()), 0.5f));
        this.tvActivityConsume.setText(P3Utils.getP3CalorieSpannableString(this.p3Record.getActivityCalorie() >= 0 ? String.valueOf(this.p3Record.getActivityCalorie()) : "- -", 0.5f));
        if (!TextUtils.isEmpty(this.p3Record.getSyncTime())) {
            this.tvTodayTrendTime.setText(this.mContext.getResources().getString(R.string.Last_sync_time, CommonUtils.getDisplayTimeFromDate(this.mContext, this.p3Record.getSyncTime())));
        } else {
            this.tvTodayTrendTime.setText("");
        }
        if (this.p3Record.getLowBattery() == 1) {
            this.ivBatteryWarn.setVisibility(0);
        } else {
            this.ivBatteryWarn.setVisibility(4);
        }
        this.todayBarChartView.setBarChartDataList(this.p3Record.getData() == null ? new ArrayList<>() : this.p3Record.getData());
        refreshCalorieStatistic();
        refreshActivityStatistic();
        refreshSleepStatistic();
        refreshSleepDetail();
    }

    public void refreshSleepDetail() {
        SleepDetailResult sleepDetailResult = this.sleepDetailResult;
        if (sleepDetailResult != null) {
            this.sleepQualityBarView.setBarChartDataList(sleepDetailResult.getDeepSleeps(), this.sleepDetailResult.getLightSleeps());
        }
    }

    public void refreshSleepStatistic() {
        List<SleepStatisticResult> list = this.sleepStatisticResults;
        if (list == null || list.size() <= 0) {
            return;
        }
        TextView textView = this.tvSleep;
        Context context = this.mContext;
        List<SleepStatisticResult> list2 = this.sleepStatisticResults;
        int deepSleepSum = list2.get(list2.size() - 1).getDeepSleepSum();
        List<SleepStatisticResult> list3 = this.sleepStatisticResults;
        textView.setText(P3Utils.getP3TimeSpannableString(P3Utils.timesToString(context, deepSleepSum, list3.get(list3.size() - 1).getLightSleepSum()), 0.5f));
        TextView textView2 = this.tvDarkSleep;
        Context context2 = this.mContext;
        List<SleepStatisticResult> list4 = this.sleepStatisticResults;
        textView2.setText(P3Utils.getP3TimeSpannableString(P3Utils.timeToString(context2, list4.get(list4.size() - 1).getDeepSleepSum()), 0.5f));
        TextView textView3 = this.tvLightSleep;
        Context context3 = this.mContext;
        List<SleepStatisticResult> list5 = this.sleepStatisticResults;
        textView3.setText(P3Utils.getP3TimeSpannableString(P3Utils.timeToString(context3, list5.get(list5.size() - 1).getLightSleepSum()), 0.5f));
        ArrayList arrayList = new ArrayList();
        int i = this.sleepStatisticType;
        int i2 = 0;
        if (i == 0) {
            while (i2 < this.sleepStatisticResults.size()) {
                SleepStatisticResult sleepStatisticResult = this.sleepStatisticResults.get(i2);
                if (i2 == this.sleepStatisticResults.size() - 1) {
                    arrayList.add(new P3BarChartData(CommonUtils.getAppContext().getResources().getString(R.string.Today), sleepStatisticResult.getDeepSleepSum() + sleepStatisticResult.getLightSleepSum(), 0.0f));
                } else if (i2 == this.sleepStatisticResults.size() - 2) {
                    arrayList.add(new P3BarChartData(CommonUtils.getAppContext().getResources().getString(R.string.Yesterday), sleepStatisticResult.getDeepSleepSum() + sleepStatisticResult.getLightSleepSum(), 0.0f));
                } else {
                    try {
                        arrayList.add(new P3BarChartData(this.format.format(this.dateFormat.parse(sleepStatisticResult.getTime())), sleepStatisticResult.getDeepSleepSum() + sleepStatisticResult.getLightSleepSum(), 0.0f));
                    } catch (Exception e) {
                        PetkitLog.e("P3HomeDeviceView", e.getMessage());
                    }
                }
                i2++;
            }
        } else if (i == 1) {
            while (i2 < this.sleepStatisticResults.size()) {
                SleepStatisticResult sleepStatisticResult2 = this.sleepStatisticResults.get(i2);
                if (i2 == this.sleepStatisticResults.size() - 1) {
                    arrayList.add(new P3BarChartData(CommonUtils.getAppContext().getResources().getString(R.string.This_week), sleepStatisticResult2.getDeepSleepSum() + sleepStatisticResult2.getLightSleepSum(), 0.0f));
                } else if (i2 == this.sleepStatisticResults.size() - 2) {
                    arrayList.add(new P3BarChartData(CommonUtils.getAppContext().getResources().getString(R.string.Last_week), sleepStatisticResult2.getDeepSleepSum() + sleepStatisticResult2.getLightSleepSum(), 0.0f));
                } else {
                    arrayList.add(new P3BarChartData(sleepStatisticResult2.getTime(), sleepStatisticResult2.getDeepSleepSum() + sleepStatisticResult2.getLightSleepSum(), 0.0f));
                }
                i2++;
            }
        } else if (i == 2) {
            while (i2 < this.sleepStatisticResults.size()) {
                arrayList.add(new P3BarChartData(BleDeviceUtils.getMonthStatisticChartTime(this.sleepStatisticResults.get(i2).getTime()), r1.getDeepSleepSum() + r1.getLightSleepSum(), 0.0f));
                i2++;
            }
        }
        this.p3SleepBarChartView.refreshData(arrayList);
    }

    public void refreshActivityStatistic() {
        List<ActivityStatisticResult> list = this.activityStatisticResults;
        if (list == null || list.size() <= 0) {
            return;
        }
        TextView textView = this.tvActivity;
        Context context = this.mContext;
        List<ActivityStatisticResult> list2 = this.activityStatisticResults;
        int walkSum = list2.get(list2.size() - 1).getWalkSum();
        List<ActivityStatisticResult> list3 = this.activityStatisticResults;
        textView.setText(P3Utils.getP3TimeSpannableString(P3Utils.timesToString(context, walkSum, list3.get(list3.size() - 1).getPlaySum()), 0.5f));
        TextView textView2 = this.tvActivityStateRun;
        Context context2 = this.mContext;
        List<ActivityStatisticResult> list4 = this.activityStatisticResults;
        textView2.setText(P3Utils.getP3TimeSpannableString(P3Utils.timeToString(context2, list4.get(list4.size() - 1).getPlaySum()), 0.5f));
        TextView textView3 = this.tvStateWalk;
        Context context3 = this.mContext;
        List<ActivityStatisticResult> list5 = this.activityStatisticResults;
        textView3.setText(P3Utils.getP3TimeSpannableString(P3Utils.timeToString(context3, list5.get(list5.size() - 1).getWalkSum()), 0.5f));
        P3RatioView p3RatioView = this.activityView;
        List<ActivityStatisticResult> list6 = this.activityStatisticResults;
        float playSum = list6.get(list6.size() - 1).getPlaySum() / 60;
        List<ActivityStatisticResult> list7 = this.activityStatisticResults;
        p3RatioView.setColorRatio(playSum, list7.get(list7.size() - 1).getWalkSum() / 60);
        ArrayList arrayList = new ArrayList();
        int i = this.activityStatisticType;
        int i2 = 0;
        if (i == 0) {
            while (i2 < this.activityStatisticResults.size()) {
                ActivityStatisticResult activityStatisticResult = this.activityStatisticResults.get(i2);
                if (i2 == this.activityStatisticResults.size() - 1) {
                    arrayList.add(new P3BarChartData(CommonUtils.getAppContext().getResources().getString(R.string.Today), activityStatisticResult.getWalkSum() + activityStatisticResult.getPlaySum(), 0.0f));
                } else if (i2 == this.activityStatisticResults.size() - 2) {
                    arrayList.add(new P3BarChartData(CommonUtils.getAppContext().getResources().getString(R.string.Yesterday), activityStatisticResult.getPlaySum() + activityStatisticResult.getWalkSum(), 0.0f));
                } else {
                    try {
                        arrayList.add(new P3BarChartData(this.format.format(this.dateFormat.parse(activityStatisticResult.getTime())), activityStatisticResult.getPlaySum() + activityStatisticResult.getWalkSum(), 0.0f));
                    } catch (Exception e) {
                        PetkitLog.e("P3HomeDeviceView", e.getMessage());
                    }
                }
                i2++;
            }
        } else if (i == 1) {
            while (i2 < this.activityStatisticResults.size()) {
                ActivityStatisticResult activityStatisticResult2 = this.activityStatisticResults.get(i2);
                if (i2 == this.activityStatisticResults.size() - 1) {
                    arrayList.add(new P3BarChartData(CommonUtils.getAppContext().getResources().getString(R.string.This_week), activityStatisticResult2.getPlaySum() + activityStatisticResult2.getWalkSum(), 0.0f));
                } else if (i2 == this.activityStatisticResults.size() - 2) {
                    arrayList.add(new P3BarChartData(CommonUtils.getAppContext().getResources().getString(R.string.Last_week), activityStatisticResult2.getPlaySum() + activityStatisticResult2.getWalkSum(), 0.0f));
                } else {
                    arrayList.add(new P3BarChartData(activityStatisticResult2.getTime(), activityStatisticResult2.getPlaySum() + activityStatisticResult2.getWalkSum(), 0.0f));
                }
                i2++;
            }
        } else if (i == 2) {
            while (i2 < this.activityStatisticResults.size()) {
                arrayList.add(new P3BarChartData(BleDeviceUtils.getMonthStatisticChartTime(this.activityStatisticResults.get(i2).getTime()), r1.getPlaySum() + r1.getWalkSum(), 0.0f));
                i2++;
            }
        }
        this.p3ActivityBarChartView.refreshData(arrayList);
    }

    public void refreshCalorieStatistic() {
        List<CalorieStatisticResult> list = this.calorieStatisticResults;
        if (list == null || list.size() <= 0) {
            return;
        }
        TextView textView = this.tvCalorie;
        List<CalorieStatisticResult> list2 = this.calorieStatisticResults;
        int activityCalorieSum = list2.get(list2.size() - 1).getActivityCalorieSum();
        List<CalorieStatisticResult> list3 = this.calorieStatisticResults;
        textView.setText(P3Utils.getP3CalorieSpannableString(String.valueOf(activityCalorieSum + list3.get(list3.size() - 1).getBasicCalorieSum()), 0.5f));
        TextView textView2 = this.tvBaseConsumeData;
        List<CalorieStatisticResult> list4 = this.calorieStatisticResults;
        textView2.setText(P3Utils.getP3CalorieSpannableString(String.valueOf(list4.get(list4.size() - 1).getBasicCalorieSum()), 0.5f));
        TextView textView3 = this.tvActivityData;
        List<CalorieStatisticResult> list5 = this.calorieStatisticResults;
        textView3.setText(P3Utils.getP3CalorieSpannableString(String.valueOf(list5.get(list5.size() - 1).getActivityCalorieSum()), 0.5f));
        P3RatioView p3RatioView = this.calorieView;
        List<CalorieStatisticResult> list6 = this.calorieStatisticResults;
        float activityCalorieSum2 = list6.get(list6.size() - 1).getActivityCalorieSum();
        List<CalorieStatisticResult> list7 = this.calorieStatisticResults;
        p3RatioView.setColorRatio(activityCalorieSum2, list7.get(list7.size() - 1).getBasicCalorieSum());
        ArrayList arrayList = new ArrayList();
        int i = this.calorieStatisticType;
        int i2 = 0;
        if (i == 0) {
            while (i2 < this.calorieStatisticResults.size()) {
                CalorieStatisticResult calorieStatisticResult = this.calorieStatisticResults.get(i2);
                if (i2 == this.calorieStatisticResults.size() - 1) {
                    arrayList.add(new P3BarChartData(CommonUtils.getAppContext().getResources().getString(R.string.Today), calorieStatisticResult.getActivityCalorieSum() + calorieStatisticResult.getBasicCalorieSum(), 0.0f));
                } else if (i2 == this.calorieStatisticResults.size() - 2) {
                    arrayList.add(new P3BarChartData(CommonUtils.getAppContext().getResources().getString(R.string.Yesterday), calorieStatisticResult.getActivityCalorieSum() + calorieStatisticResult.getBasicCalorieSum(), 0.0f));
                } else {
                    try {
                        arrayList.add(new P3BarChartData(this.format.format(this.dateFormat.parse(calorieStatisticResult.getTime())), calorieStatisticResult.getActivityCalorieSum() + calorieStatisticResult.getBasicCalorieSum(), 0.0f));
                    } catch (Exception e) {
                        PetkitLog.e("P3HomeDeviceView", e.getMessage());
                    }
                }
                i2++;
            }
        } else if (i == 1) {
            while (i2 < this.calorieStatisticResults.size()) {
                CalorieStatisticResult calorieStatisticResult2 = this.calorieStatisticResults.get(i2);
                if (i2 == this.calorieStatisticResults.size() - 1) {
                    arrayList.add(new P3BarChartData(CommonUtils.getAppContext().getResources().getString(R.string.This_week), calorieStatisticResult2.getActivityCalorieSum() + calorieStatisticResult2.getBasicCalorieSum(), 0.0f));
                } else if (i2 == this.calorieStatisticResults.size() - 2) {
                    arrayList.add(new P3BarChartData(CommonUtils.getAppContext().getResources().getString(R.string.Last_week), calorieStatisticResult2.getActivityCalorieSum() + calorieStatisticResult2.getBasicCalorieSum(), 0.0f));
                } else {
                    arrayList.add(new P3BarChartData(calorieStatisticResult2.getTime(), calorieStatisticResult2.getActivityCalorieSum() + calorieStatisticResult2.getBasicCalorieSum(), 0.0f));
                }
                i2++;
            }
        } else if (i == 2) {
            while (i2 < this.calorieStatisticResults.size()) {
                arrayList.add(new P3BarChartData(BleDeviceUtils.getMonthStatisticChartTime(this.calorieStatisticResults.get(i2).getTime()), r1.getActivityCalorieSum() + r1.getBasicCalorieSum(), 0.0f));
                i2++;
            }
        }
        this.p3CalorieBarChartView.refreshData(arrayList);
    }

    public float getCenterCircleRadius() {
        P3CenterView p3CenterView = this.p3CenterView;
        if (p3CenterView != null) {
            return p3CenterView.getCenterCircleRadius();
        }
        return 0.0f;
    }

    public int[] getCircleCenter() {
        int[] iArr = new int[2];
        if (this.p3CenterView != null) {
            iArr[0] = (int) (r2.getTop() + (this.p3CenterView.getHeight() / 2) + this.mContext.getResources().getDimension(R.dimen.base_titleheight));
            iArr[1] = this.p3CenterView.getLeft() + (this.p3CenterView.getWidth() / 2);
        }
        return iArr;
    }

    public void startFindPetAni() {
        this.rippleView.setmDefaultRadius(this.p3CenterView.getCenterCircleRadius());
        this.rippleView.setmDensity((int) (this.p3CenterView.getCenterCircleRadius() / 3.0f));
        this.rippleView.reStart();
        this.rlRippleView.setVisibility(0);
        Disposable disposable = this.disposable;
        if (disposable != null && !disposable.isDisposed()) {
            this.disposable.dispose();
        }
        this.disposable = Observable.timer(3000L, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() { // from class: com.petkit.android.activities.petkitBleDevice.p3.widget.P3HomeDeviceView.4
            @Override // io.reactivex.functions.Consumer
            public void accept(Long l) throws Exception {
                P3HomeDeviceView.this.stopFindPetAni();
            }
        });
    }

    public void stopFindPetAni() {
        this.rlRippleView.setVisibility(8);
    }

    public void setCalorieStatistic(List<CalorieStatisticResult> list) {
        this.calorieStatisticResults = list;
        refreshCalorieStatistic();
    }

    public void setSleepDetailResults(SleepDetailResult sleepDetailResult) {
        this.sleepDetailResult = sleepDetailResult;
        refreshSleepDetail();
    }

    public void setSleepStatisticResults(List<SleepStatisticResult> list) {
        this.sleepStatisticResults = list;
        refreshSleepStatistic();
    }

    public void setActivityStatistic(List<ActivityStatisticResult> list) {
        this.activityStatisticResults = list;
        refreshActivityStatistic();
    }
}

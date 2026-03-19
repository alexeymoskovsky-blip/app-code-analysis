package com.petkit.android.activities.petkitBleDevice.d4.widget;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import com.jess.arms.utils.Consts;
import com.jess.arms.utils.DeviceUtils;
import com.jess.arms.widget.InterceptScrollView;
import com.jess.arms.widget.PetkitToast;
import com.loopj.android.http.ResponseHandlerInterface;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.feed.mode.RefreshFeedDataEvent;
import com.petkit.android.activities.feeder.widget.BaseFeederWindow;
import com.petkit.android.activities.go.widget.BaseScaleView;
import com.petkit.android.activities.petkitBleDevice.d4.mode.D4DailyFeeds;
import com.petkit.android.activities.petkitBleDevice.d4.mode.D4FeedsItemBean;
import com.petkit.android.activities.petkitBleDevice.d4.mode.D4Record;
import com.petkit.android.activities.petkitBleDevice.d4.utils.D4Utils;
import com.petkit.android.activities.petkitBleDevice.d4.widget.D4ScaleScrollView;
import com.petkit.android.activities.petkitBleDevice.utils.TimeUtils;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.AsyncHttpUtil;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.DateUtil;
import com.petkit.android.widget.windows.BasePetkitWindow;
import com.petkit.oversea.R;
import com.tencent.connect.common.Constants;
import cz.msebera.android.httpclient.Header;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.sourceforge.pinyin4j.ChineseToPinyinResource;
import org.simple.eventbus.EventBus;

/* JADX INFO: loaded from: classes4.dex */
public class D4ManualWindow extends BaseFeederWindow {
    private int amount;
    private int curHourOfDay;
    private int curMinute;
    private int curTime;
    private D4ScaleScrollView d4ScaleScrollView;
    private SimpleDateFormat dateFormat;
    private TextView feederValueTextView;
    private Activity mActivity;
    private CheckBox mCheckBox;
    private D4Record mD4Record;
    private int mDay;
    private long mDeviceId;
    private onFeedManualListener mFeedManualListener;
    private TimePicker mTimePicker;
    private View mTimeSelectView;
    private int time;
    private TextView timeTextView;
    private TextView tvFeedManualCenterAmount;
    private TextView tvFoodPrompt;
    private final TextView tvPrompt;
    private TextView tvToday;
    private TextView tvTomorrow;

    public interface onFeedManualListener {
        void onFeedManualSuccess(int i, int i2, int i3);
    }

    public D4ManualWindow(Activity activity, boolean z, long j) {
        super(activity, z);
        this.time = -2;
        this.dateFormat = new SimpleDateFormat(DateUtil.DATE_FORMAT_7);
        this.mActivity = activity;
        this.mDeviceId = j;
        this.mD4Record = D4Utils.getD4RecordByDeviceId(j);
        initContentView(R.layout.d4_pop_maunal_feed);
        setTitle(this.mActivity.getString(R.string.Feeder_add_manual_online));
        this.tvFoodPrompt = (TextView) getContentView().findViewById(R.id.tv_food_prompt);
        this.tvFeedManualCenterAmount = (TextView) getContentView().findViewById(R.id.feed_manual_center_amount);
        TextView textView = (TextView) getContentView().findViewById(R.id.tv_prompt);
        this.tvPrompt = textView;
        this.dateFormat.setTimeZone(this.mD4Record.getActualTimeZone());
        this.context.getResources().getString(R.string.About);
        D4ScaleScrollView d4ScaleScrollView = (D4ScaleScrollView) getContentView().findViewById(R.id.d4_scroll_view);
        this.d4ScaleScrollView = d4ScaleScrollView;
        d4ScaleScrollView.setmSelectedColor(R.color.d4_main_yellow);
        this.d4ScaleScrollView.setOnScrollListener(new BaseScaleView.OnScrollListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4.widget.D4ManualWindow.1
            @Override // com.petkit.android.activities.go.widget.BaseScaleView.OnScrollListener
            public void onScaleScroll(int i) {
                D4ManualWindow.this.tvFeedManualCenterAmount.setText(D4ManualWindow.this.mActivity.getString(R.string.About) + i + D4ManualWindow.this.mActivity.getString(R.string.Unit_g));
                D4ManualWindow.this.feederValueTextView.setText(D4Utils.getAmountFormat(i, D4ManualWindow.this.mD4Record.getSettings().getFactor()) + D4Utils.getD4AmountUnit(((BasePetkitWindow) D4ManualWindow.this).context, i, D4ManualWindow.this.mD4Record.getSettings().getFactor()) + ChineseToPinyinResource.Field.LEFT_BRACKET + D4ManualWindow.this.mActivity.getString(R.string.About) + i + D4ManualWindow.this.mActivity.getString(R.string.Unit_g) + ChineseToPinyinResource.Field.RIGHT_BRACKET);
                D4ManualWindow.this.amount = i;
            }
        });
        this.d4ScaleScrollView.setScaleColor(-7829368);
        this.d4ScaleScrollView.setScaleStyle(1);
        this.d4ScaleScrollView.setSelectScaleColor(-1);
        this.d4ScaleScrollView.setAmountUnit(this.mD4Record.getSettings().getFactor());
        this.d4ScaleScrollView.setPointerRadius((int) DeviceUtils.dpToPixel(this.mActivity, 40.0f));
        final InterceptScrollView interceptScrollView = (InterceptScrollView) getContentView().findViewById(R.id.pop_content_id);
        this.d4ScaleScrollView.setScrollStateListener(new D4ScaleScrollView.IScrollStateListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4.widget.D4ManualWindow.2
            @Override // com.petkit.android.activities.petkitBleDevice.d4.widget.D4ScaleScrollView.IScrollStateListener
            public void onScrollStateChanged(boolean z2) {
                interceptScrollView.setScrollingEnabled(!z2);
            }
        });
        this.mTimeSelectView = getContentView().findViewById(R.id.feed_manual_time_parent);
        this.mTimePicker = (TimePicker) getContentView().findViewById(R.id.TimePicker);
        this.timeTextView = (TextView) getContentView().findViewById(R.id.feed_manual_time);
        this.mCheckBox = (CheckBox) getContentView().findViewById(R.id.feed_now_checkbox);
        String string = this.context.getResources().getString(R.string.Ten_minutes_later);
        SpannableString spannableString = new SpannableString(this.context.getResources().getString(R.string.D3_add_meal_prompt, string));
        spannableString.setSpan(new ForegroundColorSpan(this.context.getResources().getColor(R.color.d4_main_yellow)), spannableString.toString().indexOf(string), spannableString.toString().indexOf(string) + string.length(), 33);
        textView.setText(spannableString);
        textView.setVisibility(8);
        if (this.mD4Record.getState().getPim() == 2) {
            textView.setVisibility(0);
            this.mTimeSelectView.setVisibility(0);
            this.mTimePicker.setVisibility(0);
            getContentView().findViewById(R.id.feed_manual_checkbox_parent).setVisibility(8);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeZone(this.mD4Record.getActualTimeZone());
            calendar.add(12, 10);
            this.curHourOfDay = calendar.get(11);
            int i = calendar.get(12);
            this.curMinute = i;
            this.time = (this.curHourOfDay * 3600) + (i * 60);
            this.timeTextView.setText(TimeUtils.getInstance().secondsToTimeStringWithUnit(this.context, this.time));
            this.curTime = this.time;
            if (Build.VERSION.SDK_INT >= 23) {
                this.mTimePicker.setMinute(this.curMinute);
                this.mTimePicker.setHour(this.curHourOfDay);
            } else {
                this.mTimePicker.setCurrentMinute(Integer.valueOf(this.curMinute));
                this.mTimePicker.setCurrentHour(Integer.valueOf(this.curHourOfDay));
            }
        }
        getContentView().findViewById(R.id.feed_manual_time_view).setOnClickListener(this);
        resizePikcer(this.mTimePicker);
        this.feederValueTextView = (TextView) getContentView().findViewById(R.id.feed_manual_amount);
        getContentView().findViewById(R.id.feed_manual_amount_view).setOnClickListener(this);
        initView();
        setNoTitleGapLine();
        setActionString(this.mActivity.getString(R.string.Save));
        this.action.setBackgroundResource(R.drawable.solid_d4_main_color_with_bottom_radius);
        this.close.setImageResource(R.drawable.d4_close_icon);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) getContentView().findViewById(R.id.menu_layout).getLayoutParams();
        layoutParams.height = (int) (BaseApplication.displayMetrics.heightPixels * 0.6f);
        getContentView().findViewById(R.id.menu_layout).setLayoutParams(layoutParams);
        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) getContentView().findViewById(R.id.pop_action).getLayoutParams();
        layoutParams2.addRule(3, 0);
        layoutParams2.addRule(12);
        ((RelativeLayout.LayoutParams) getContentView().findViewById(R.id.pop_content_id).getLayoutParams()).addRule(2, R.id.pop_action);
    }

    private void initView() {
        this.mTimePicker.setIs24HourView(Boolean.valueOf(CommonUtils.getSystemTimeFormat(this.context).equals(Constants.VIA_REPORT_TYPE_CHAT_AIO)));
        this.tvToday = (TextView) getContentView().findViewById(R.id.tv_today);
        this.tvTomorrow = (TextView) getContentView().findViewById(R.id.tv_tomorrow);
        this.tvToday.setSelected(true);
        this.tvToday.setTextColor(-1);
        this.tvTomorrow.setSelected(false);
        this.tvTomorrow.setTextColor(this.mActivity.getResources().getColor(R.color.d4_main_yellow));
        this.tvToday.setOnClickListener(this);
        this.tvTomorrow.setOnClickListener(this);
        this.d4ScaleScrollView.setDefault(CommonUtils.getSysIntMap(this.mActivity, Consts.D4_LAST_FEED_AMOUNT + this.mDeviceId, 10) / this.mD4Record.getSettings().getFactor());
        this.mTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4.widget.D4ManualWindow.3
            @Override // android.widget.TimePicker.OnTimeChangedListener
            public void onTimeChanged(TimePicker timePicker, int i, int i2) {
                if (D4ManualWindow.this.mDay != Integer.parseInt(D4ManualWindow.this.dateFormat.format(Calendar.getInstance(D4ManualWindow.this.mD4Record.getActualTimeZone()).getTime())) || D4ManualWindow.this.mD4Record.getState().getPim() != 2 || (i * 3600) + (i2 * 60) >= D4ManualWindow.this.curTime) {
                    D4ManualWindow.this.time = (i * 3600) + (i2 * 60);
                    D4ManualWindow.this.timeTextView.setText(TimeUtils.getInstance().secondsToTimeStringWithUnit(((BasePetkitWindow) D4ManualWindow.this).context, D4ManualWindow.this.time));
                } else if (Build.VERSION.SDK_INT >= 23) {
                    D4ManualWindow.this.mTimePicker.setMinute(D4ManualWindow.this.curMinute);
                    D4ManualWindow.this.mTimePicker.setHour(D4ManualWindow.this.curHourOfDay);
                } else {
                    D4ManualWindow.this.mTimePicker.setCurrentMinute(Integer.valueOf(D4ManualWindow.this.curMinute));
                    D4ManualWindow.this.mTimePicker.setCurrentHour(Integer.valueOf(D4ManualWindow.this.curHourOfDay));
                }
            }
        });
        CheckBox checkBox = this.mCheckBox;
        if (checkBox != null) {
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4.widget.D4ManualWindow.4
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    if (z) {
                        D4ManualWindow.this.mTimeSelectView.setVisibility(8);
                        return;
                    }
                    D4ManualWindow.this.mTimeSelectView.setVisibility(0);
                    D4ManualWindow.this.mTimePicker.setVisibility(0);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeZone(D4ManualWindow.this.mD4Record.getActualTimeZone());
                    calendar.add(12, 5);
                    int i = calendar.get(11);
                    int i2 = calendar.get(12);
                    D4ManualWindow.this.time = (i * 3600) + (i2 * 60);
                    D4ManualWindow.this.timeTextView.setText(TimeUtils.getInstance().secondsToTimeStringWithUnit(((BasePetkitWindow) D4ManualWindow.this).context, D4ManualWindow.this.time));
                    if (Build.VERSION.SDK_INT >= 23) {
                        D4ManualWindow.this.mTimePicker.setMinute(i2);
                        D4ManualWindow.this.mTimePicker.setHour(i);
                    } else {
                        D4ManualWindow.this.mTimePicker.setCurrentMinute(Integer.valueOf(i2));
                        D4ManualWindow.this.mTimePicker.setCurrentHour(Integer.valueOf(i));
                    }
                }
            });
            if (this.mD4Record.getState().getPim() != 2) {
                this.mCheckBox.setChecked(true);
            }
        }
        if (this.tvToday.isSelected()) {
            this.mDay = Integer.parseInt(this.dateFormat.format(Calendar.getInstance(this.mD4Record.getActualTimeZone()).getTime()));
            return;
        }
        Calendar calendar = Calendar.getInstance(this.mD4Record.getActualTimeZone());
        calendar.add(5, 1);
        this.mDay = Integer.parseInt(this.dateFormat.format(calendar.getTime()));
    }

    @Override // com.petkit.android.activities.feeder.widget.BaseFeederWindow, com.petkit.android.widget.windows.BasePetkitWindow, android.view.View.OnClickListener
    public void onClick(View view) {
        super.onClick(view);
        int id = view.getId();
        if (id == R.id.feed_manual_amount_view) {
            if (this.timeTextView.getVisibility() == 8) {
                this.mTimeSelectView.setVisibility(8);
            } else {
                this.mTimePicker.setVisibility(8);
            }
            this.tvFoodPrompt.setVisibility(0);
            return;
        }
        if (id == R.id.feed_manual_tomorrow_view) {
            if (this.mTimePicker.getVisibility() == 0) {
                this.mTimePicker.setVisibility(8);
                this.tvFoodPrompt.setVisibility(0);
                return;
            } else {
                this.mTimePicker.setVisibility(0);
                this.tvFoodPrompt.setVisibility(8);
                return;
            }
        }
        if (id == R.id.tv_tomorrow || id == R.id.tv_today) {
            this.tvToday.setSelected(!r5.isSelected());
            TextView textView = this.tvToday;
            textView.setTextColor(textView.isSelected() ? -1 : this.mActivity.getResources().getColor(R.color.d4_main_yellow));
            this.tvTomorrow.setSelected(!r5.isSelected());
            TextView textView2 = this.tvTomorrow;
            textView2.setTextColor(textView2.isSelected() ? -1 : this.mActivity.getResources().getColor(R.color.d4_main_yellow));
            if (this.tvToday.isSelected()) {
                this.mDay = Integer.parseInt(this.dateFormat.format(Calendar.getInstance(this.mD4Record.getActualTimeZone()).getTime()));
                return;
            }
            Calendar calendar = Calendar.getInstance(this.mD4Record.getActualTimeZone());
            calendar.add(5, 1);
            this.mDay = Integer.parseInt(this.dateFormat.format(calendar.getTime()));
        }
    }

    @Override // com.petkit.android.activities.feeder.widget.BaseFeederWindow
    public void onActionClick() {
        if (this.amount < 5) {
            PetkitToast.showToast(this.mActivity.getResources().getString(R.string.Feed_min_prompt));
            return;
        }
        CheckBox checkBox = this.mCheckBox;
        if (checkBox != null && checkBox.isChecked()) {
            this.time = -1;
        } else if (this.time == -2) {
            PetkitToast.showShortToast(this.mActivity, R.string.Hint_set_feeder_plan_time_null);
            return;
        }
        if (this.amount == 0) {
            PetkitToast.showShortToast(this.mActivity, R.string.Hint_set_feeder_plan_amount_null);
            return;
        }
        if (this.tvToday.isSelected()) {
            this.mDay = Integer.parseInt(this.dateFormat.format(Calendar.getInstance(this.mD4Record.getActualTimeZone()).getTime()));
        } else {
            Calendar calendar = Calendar.getInstance(this.mD4Record.getActualTimeZone());
            calendar.add(5, 1);
            this.mDay = Integer.parseInt(this.dateFormat.format(calendar.getTime()));
        }
        saveManualFeed();
    }

    private void saveManualFeed() {
        HashMap map = new HashMap();
        map.put("deviceId", String.valueOf(this.mDeviceId));
        map.put("day", String.valueOf(this.mDay));
        map.put("time", String.valueOf(this.time));
        map.put("amount", String.valueOf(this.amount));
        Activity activity = this.mActivity;
        AsyncHttpUtil.post((Context) activity, ApiTools.SAMPLE_API_D4_SAVE_DAILY_FEED, (Map<String, String>) map, (ResponseHandlerInterface) new AsyncHttpRespHandler(activity, true) { // from class: com.petkit.android.activities.petkitBleDevice.d4.widget.D4ManualWindow.5
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                D4FeedsItemBean d4FeedsItemBean = (D4FeedsItemBean) this.gson.fromJson(this.responseResult, D4FeedsItemBean.class);
                if (d4FeedsItemBean.getError() != null) {
                    PetkitToast.showShortToast(D4ManualWindow.this.mActivity, d4FeedsItemBean.getError().getMsg());
                    return;
                }
                D4ManualWindow.this.dismiss();
                D4DailyFeeds.D4DailyFeed.ItemsBean result = d4FeedsItemBean.getResult();
                if (D4ManualWindow.this.time == -1) {
                    result.setIsExecuted(1);
                }
                if (D4ManualWindow.this.time != -1) {
                    EventBus.getDefault().post(new RefreshFeedDataEvent());
                }
                CommonUtils.addSysIntMap(D4ManualWindow.this.mActivity, Consts.D4_LAST_FEED_AMOUNT + D4ManualWindow.this.mDeviceId, D4ManualWindow.this.amount);
                if (D4ManualWindow.this.mFeedManualListener != null) {
                    D4ManualWindow.this.mFeedManualListener.onFeedManualSuccess(D4ManualWindow.this.mDay, result.getTime(), D4ManualWindow.this.amount);
                }
            }
        }, false);
    }

    public void setFeedManualListener(onFeedManualListener onfeedmanuallistener) {
        this.mFeedManualListener = onfeedmanuallistener;
    }

    private void resizePikcer(FrameLayout frameLayout) {
        Iterator<NumberPicker> it = findNumberPicker(frameLayout).iterator();
        while (it.hasNext()) {
            resizeNumberPicker(it.next());
        }
    }

    private List<NumberPicker> findNumberPicker(ViewGroup viewGroup) {
        ArrayList arrayList = new ArrayList();
        if (viewGroup != null) {
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View childAt = viewGroup.getChildAt(i);
                if (childAt instanceof NumberPicker) {
                    arrayList.add((NumberPicker) childAt);
                } else if (childAt instanceof LinearLayout) {
                    List<NumberPicker> listFindNumberPicker = findNumberPicker((ViewGroup) childAt);
                    if (listFindNumberPicker.size() > 0) {
                        return listFindNumberPicker;
                    }
                } else {
                    continue;
                }
            }
        }
        return arrayList;
    }

    private void resizeNumberPicker(NumberPicker numberPicker) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((int) DeviceUtils.dpToPixel(this.mActivity, 50.0f), -2);
        layoutParams.setMargins(10, 0, 10, 0);
        numberPicker.setLayoutParams(layoutParams);
    }
}

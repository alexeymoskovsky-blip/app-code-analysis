package com.petkit.android.activities.d2.widget;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
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
import com.jess.arms.utils.DeviceUtils;
import com.jess.arms.widget.InterceptScrollView;
import com.jess.arms.widget.PetkitToast;
import com.loopj.android.http.ResponseHandlerInterface;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.d2.mode.D2Record;
import com.petkit.android.activities.d2.utils.D2Utils;
import com.petkit.android.activities.d2.widget.D2ScaleScrollView;
import com.petkit.android.activities.feeder.api.DailyFeedsItemRsp;
import com.petkit.android.activities.feeder.model.DailyFeedsItemBean;
import com.petkit.android.activities.feeder.widget.BaseFeederWindow;
import com.petkit.android.activities.go.widget.BaseScaleView;
import com.petkit.android.activities.petkitBleDevice.utils.TimeUtils;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.AsyncHttpUtil;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.widget.windows.BasePetkitWindow;
import com.petkit.oversea.R;
import com.tencent.connect.common.Constants;
import cz.msebera.android.httpclient.Header;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public class D2ManualWindow extends BaseFeederWindow {
    private int amount;
    private TextView amountPromptTextView;
    private int curHourOfDay;
    private int curMinute;
    private int curTime;
    private TextView feederValueTextView;
    private Activity mActivity;
    private CheckBox mCheckBox;
    private D2Record mD2Record;
    private D2ScaleScrollView mD2ScaleScrollView;
    private int mDay;
    private long mDeviceId;
    private onFeedMaunalListener mFeedMaunalListener;
    private TimePicker mTimePicker;
    private View mTimeSelectView;
    private int time;
    private TextView timeTextView;
    private TextView tvFoodPrompt;

    public interface onFeedMaunalListener {
        void onFeedManualSuccess(int i, int i2);
    }

    public D2ManualWindow(Activity activity, boolean z, long j, int i) {
        super(activity, z);
        this.time = -2;
        this.mActivity = activity;
        this.mDeviceId = j;
        this.mDay = i;
        this.mD2Record = D2Utils.getD2RecordByDeviceId(j);
        initContentView(R.layout.d2_pop_maunal_feed);
        this.mD2ScaleScrollView = (D2ScaleScrollView) getContentView().findViewById(R.id.d2_scroll_view);
        this.amountPromptTextView = (TextView) getContentView().findViewById(R.id.feed_manual_amount_prompt);
        this.tvFoodPrompt = (TextView) getContentView().findViewById(R.id.tv_food_prompt);
        if (Integer.valueOf(CommonUtils.getDateStringByOffset(0, this.mD2Record.getActualTimeZone())).intValue() == this.mDay) {
            new HashMap().put("type", "today");
            setTitle(this.mActivity.getString(R.string.Feeder_add_today));
            this.mTimeSelectView = getContentView().findViewById(R.id.feed_manual_time_parent);
            this.mTimePicker = (TimePicker) getContentView().findViewById(R.id.TimePicker);
            this.timeTextView = (TextView) getContentView().findViewById(R.id.feed_manual_time);
            this.mCheckBox = (CheckBox) getContentView().findViewById(R.id.feed_now_checkbox);
            this.mTimePicker.setIs24HourView(Boolean.valueOf(CommonUtils.getSystemTimeFormat(this.context).equals(Constants.VIA_REPORT_TYPE_CHAT_AIO)));
            if (this.mD2Record.getState().getBatteryStatus() != 0) {
                this.mTimeSelectView.setVisibility(0);
                this.mTimePicker.setVisibility(0);
                this.mD2ScaleScrollView.setVisibility(0);
                this.amountPromptTextView.setVisibility(0);
                getContentView().findViewById(R.id.feed_manual_checkbox_parent).setVisibility(8);
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeZone(this.mD2Record.getActualTimeZone());
                calendar.add(12, 10);
                this.curHourOfDay = calendar.get(11);
                int i2 = calendar.get(12);
                this.curMinute = i2;
                this.time = (this.curHourOfDay * 3600) + (i2 * 60);
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
        } else {
            setTitle(this.mActivity.getString(R.string.Feeder_add_tomorow));
            getContentView().findViewById(R.id.feed_manual_tomorrow_parent).setVisibility(0);
            getContentView().findViewById(R.id.feed_manual_time_parent).setVisibility(8);
            getContentView().findViewById(R.id.feed_manual_checkbox_parent).setVisibility(8);
            this.mTimeSelectView = getContentView().findViewById(R.id.feed_manual_tomorrow_view);
            this.mTimePicker = (TimePicker) getContentView().findViewById(R.id.TimePicker_tomorrow);
            this.timeTextView = (TextView) getContentView().findViewById(R.id.feed_manual_tomorrow_time);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTimeZone(this.mD2Record.getActualTimeZone());
            int i3 = calendar2.get(11);
            int i4 = calendar2.get(12);
            this.time = (i3 * 3600) + (i4 * 60);
            this.timeTextView.setText(TimeUtils.getInstance().secondsToTimeStringWithUnit(this.context, this.time));
            this.mTimePicker.setIs24HourView(Boolean.valueOf(CommonUtils.getSystemTimeFormat(this.context).equals(Constants.VIA_REPORT_TYPE_CHAT_AIO)));
            if (Build.VERSION.SDK_INT >= 23) {
                this.mTimePicker.setMinute(i4);
                this.mTimePicker.setHour(i3);
            } else {
                this.mTimePicker.setCurrentMinute(Integer.valueOf(i4));
                this.mTimePicker.setCurrentHour(Integer.valueOf(i3));
            }
            getContentView().findViewById(R.id.feed_manual_tomorrow_view).setOnClickListener(this);
        }
        resizePikcer(this.mTimePicker);
        this.feederValueTextView = (TextView) getContentView().findViewById(R.id.feed_manual_amount);
        getContentView().findViewById(R.id.feed_manual_amount_view).setOnClickListener(this);
        initView();
        setNoTitleGapLine();
        setActionString(this.mActivity.getString(R.string.Save));
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
        this.mD2ScaleScrollView.setOnScrollListener(new BaseScaleView.OnScrollListener() { // from class: com.petkit.android.activities.d2.widget.D2ManualWindow.1
            @Override // com.petkit.android.activities.go.widget.BaseScaleView.OnScrollListener
            public void onScaleScroll(int i) {
                D2ManualWindow.this.amountPromptTextView.setText(D2ManualWindow.this.mActivity.getString(R.string.About) + i + D2ManualWindow.this.mActivity.getString(R.string.Unit_g));
                D2ManualWindow.this.feederValueTextView.setText(D2Utils.getAmountFormat(i) + D2ManualWindow.this.mActivity.getString(D2Utils.getD2AmountUnit(i)));
                D2ManualWindow.this.amount = i;
            }
        });
        this.mD2ScaleScrollView.setScaleColor(-7829368);
        this.mD2ScaleScrollView.setScaleStyle(1);
        this.mD2ScaleScrollView.setSelectScaleColor(-1);
        this.mD2ScaleScrollView.setPointerRadius((int) DeviceUtils.dpToPixel(this.mActivity, 40.0f));
        this.mD2ScaleScrollView.setDefault(5);
        final InterceptScrollView interceptScrollView = (InterceptScrollView) getContentView().findViewById(R.id.pop_content_id);
        this.mD2ScaleScrollView.setScrollStateListener(new D2ScaleScrollView.IScrollStateListener() { // from class: com.petkit.android.activities.d2.widget.D2ManualWindow.2
            @Override // com.petkit.android.activities.d2.widget.D2ScaleScrollView.IScrollStateListener
            public void onScrollStateChanged(boolean z) {
                interceptScrollView.setScrollingEnabled(!z);
            }
        });
        this.mTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() { // from class: com.petkit.android.activities.d2.widget.D2ManualWindow.3
            @Override // android.widget.TimePicker.OnTimeChangedListener
            public void onTimeChanged(TimePicker timePicker, int i, int i2) {
                if (D2ManualWindow.this.mD2Record.getState().getBatteryStatus() == 0 || (i * 3600) + (i2 * 60) >= D2ManualWindow.this.curTime) {
                    D2ManualWindow.this.time = (i * 3600) + (i2 * 60);
                    D2ManualWindow.this.timeTextView.setText(TimeUtils.getInstance().secondsToTimeStringWithUnit(((BasePetkitWindow) D2ManualWindow.this).context, D2ManualWindow.this.time));
                } else if (Build.VERSION.SDK_INT >= 23) {
                    D2ManualWindow.this.mTimePicker.setMinute(D2ManualWindow.this.curMinute);
                    D2ManualWindow.this.mTimePicker.setHour(D2ManualWindow.this.curHourOfDay);
                } else {
                    D2ManualWindow.this.mTimePicker.setCurrentMinute(Integer.valueOf(D2ManualWindow.this.curMinute));
                    D2ManualWindow.this.mTimePicker.setCurrentHour(Integer.valueOf(D2ManualWindow.this.curHourOfDay));
                }
            }
        });
        CheckBox checkBox = this.mCheckBox;
        if (checkBox != null) {
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.petkit.android.activities.d2.widget.D2ManualWindow.4
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    if (z) {
                        D2ManualWindow.this.mTimeSelectView.setVisibility(8);
                        D2ManualWindow.this.mD2ScaleScrollView.setVisibility(0);
                        D2ManualWindow.this.amountPromptTextView.setVisibility(0);
                        return;
                    }
                    D2ManualWindow.this.mTimeSelectView.setVisibility(0);
                    D2ManualWindow.this.mTimePicker.setVisibility(0);
                    D2ManualWindow.this.mD2ScaleScrollView.setVisibility(0);
                    D2ManualWindow.this.amountPromptTextView.setVisibility(0);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeZone(D2ManualWindow.this.mD2Record.getActualTimeZone());
                    calendar.add(12, 5);
                    int i = calendar.get(11);
                    int i2 = calendar.get(12);
                    D2ManualWindow.this.time = (i * 3600) + (i2 * 60);
                    D2ManualWindow.this.timeTextView.setText(TimeUtils.getInstance().secondsToTimeStringWithUnit(((BasePetkitWindow) D2ManualWindow.this).context, D2ManualWindow.this.time));
                    if (Build.VERSION.SDK_INT >= 23) {
                        D2ManualWindow.this.mTimePicker.setMinute(i2);
                        D2ManualWindow.this.mTimePicker.setHour(i);
                    } else {
                        D2ManualWindow.this.mTimePicker.setCurrentMinute(Integer.valueOf(i2));
                        D2ManualWindow.this.mTimePicker.setCurrentHour(Integer.valueOf(i));
                    }
                }
            });
            if (this.mD2Record.getState().getBatteryStatus() == 0) {
                this.mCheckBox.setChecked(true);
            }
        }
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
            this.mD2ScaleScrollView.setVisibility(0);
            this.amountPromptTextView.setVisibility(0);
            this.tvFoodPrompt.setVisibility(0);
            return;
        }
        if (id == R.id.feed_manual_time_view || id == R.id.feed_manual_tomorrow_view) {
            if (this.mTimePicker.getVisibility() == 0) {
                this.mTimePicker.setVisibility(8);
                this.mD2ScaleScrollView.setVisibility(0);
                this.amountPromptTextView.setVisibility(0);
                this.tvFoodPrompt.setVisibility(0);
                return;
            }
            this.mTimePicker.setVisibility(0);
            this.mD2ScaleScrollView.setVisibility(8);
            this.tvFoodPrompt.setVisibility(8);
            this.amountPromptTextView.setVisibility(8);
        }
    }

    @Override // com.petkit.android.activities.feeder.widget.BaseFeederWindow
    public void onActionClick() {
        CheckBox checkBox = this.mCheckBox;
        if (checkBox != null && checkBox.isChecked()) {
            this.time = -1;
        } else if (this.time == -2) {
            PetkitToast.showShortToast(this.mActivity, R.string.Hint_set_feeder_plan_time_null);
            return;
        }
        if (this.amount == 0) {
            PetkitToast.showShortToast(this.mActivity, R.string.Hint_set_feeder_plan_amount_null);
        } else {
            saveMaunalFeed();
        }
    }

    private void saveMaunalFeed() {
        HashMap map = new HashMap();
        map.put("deviceId", String.valueOf(this.mDeviceId));
        map.put("day", String.valueOf(this.mDay));
        map.put("time", String.valueOf(this.time));
        map.put("amount", String.valueOf(this.amount));
        Activity activity = this.mActivity;
        AsyncHttpUtil.post((Context) activity, ApiTools.SAMPLET_API_FEEDERMINI_SAVE_DAILY_FEED, (Map<String, String>) map, (ResponseHandlerInterface) new AsyncHttpRespHandler(activity, true) { // from class: com.petkit.android.activities.d2.widget.D2ManualWindow.5
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                DailyFeedsItemRsp dailyFeedsItemRsp = (DailyFeedsItemRsp) this.gson.fromJson(this.responseResult, DailyFeedsItemRsp.class);
                if (dailyFeedsItemRsp.getError() != null) {
                    PetkitToast.showShortToast(D2ManualWindow.this.mActivity, dailyFeedsItemRsp.getError().getMsg());
                    return;
                }
                D2ManualWindow.this.dismiss();
                DailyFeedsItemBean result = dailyFeedsItemRsp.getResult();
                if (D2ManualWindow.this.time == -1) {
                    result.setIsExecuted(1);
                }
                D2Utils.saveD2ItemDataFromItemBean(D2ManualWindow.this.mDeviceId, D2ManualWindow.this.mDay, result, false);
                if (D2ManualWindow.this.mFeedMaunalListener != null) {
                    D2ManualWindow.this.mFeedMaunalListener.onFeedManualSuccess(D2ManualWindow.this.mDay, result.getTime());
                }
            }
        }, false);
    }

    public void setFeedMaunalListener(onFeedMaunalListener onfeedmaunallistener) {
        this.mFeedMaunalListener = onfeedmaunallistener;
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

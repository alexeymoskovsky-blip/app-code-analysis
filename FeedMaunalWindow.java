package com.petkit.android.activities.feeder.widget;

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
import com.petkit.android.activities.feeder.Utils.FeederUtils;
import com.petkit.android.activities.feeder.api.DailyFeedsItemRsp;
import com.petkit.android.activities.feeder.model.DailyFeedsItemBean;
import com.petkit.android.activities.feeder.model.FeederRecord;
import com.petkit.android.activities.feeder.widget.FeederScaleScrollView;
import com.petkit.android.activities.go.widget.BaseScaleView;
import com.petkit.android.activities.petkitBleDevice.utils.TimeUtils;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.AsyncHttpUtil;
import com.petkit.android.utils.CommonUtils;
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
public class FeedMaunalWindow extends BaseFeederWindow {
    private int amount;
    private TextView amountPromptTextView;
    private TextView feederValueTextView;
    private Activity mActivity;
    private CheckBox mCheckBox;
    private int mDay;
    private long mDeviceId;
    private onFeedMaunalListener mFeedMaunalListener;
    FeederRecord mFeederRecord;
    private FeederScaleScrollView mFeederScaleScrollView;
    private TimePicker mTimePicker;
    private View mTimeSelectView;
    private int time;
    private TextView timeTextView;

    public interface onFeedMaunalListener {
        void onFeedManualSuccess(int i, int i2);
    }

    public FeedMaunalWindow(Activity activity, boolean z, long j, int i, FeederRecord feederRecord) {
        String str;
        super(activity, z);
        this.time = -2;
        this.mActivity = activity;
        this.mDeviceId = j;
        this.mDay = i;
        FeederUtils.getFeederRecordByDeviceId(j);
        this.mFeederRecord = feederRecord;
        initContentView(R.layout.pop_maunal_feed);
        if (Integer.valueOf(CommonUtils.getDateStringByOffset(0, this.mFeederRecord.getActualTimeZone())).intValue() == this.mDay) {
            setTitle(this.mActivity.getString(R.string.Feeder_add_today));
            this.mTimeSelectView = getContentView().findViewById(R.id.feed_manual_time_parent);
            this.mTimePicker = (TimePicker) getContentView().findViewById(R.id.TimePicker);
            this.timeTextView = (TextView) getContentView().findViewById(R.id.feed_manual_time);
            this.mCheckBox = (CheckBox) getContentView().findViewById(R.id.feed_now_checkbox);
            getContentView().findViewById(R.id.feed_manual_time_view).setOnClickListener(this);
            str = "Today";
        } else {
            setTitle(this.mActivity.getString(R.string.Feeder_add_tomorow));
            getContentView().findViewById(R.id.feed_manual_tomorrow_parent).setVisibility(0);
            getContentView().findViewById(R.id.feed_manual_time_parent).setVisibility(8);
            getContentView().findViewById(R.id.feed_manual_checkbox_parent).setVisibility(8);
            this.mTimeSelectView = getContentView().findViewById(R.id.feed_manual_tomorrow_view);
            this.mTimePicker = (TimePicker) getContentView().findViewById(R.id.TimePicker_tomorrow);
            this.timeTextView = (TextView) getContentView().findViewById(R.id.feed_manual_tomorrow_time);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeZone(this.mFeederRecord.getActualTimeZone());
            int i2 = calendar.get(11);
            int i3 = calendar.get(12);
            this.time = (i2 * 3600) + (i3 * 60);
            this.timeTextView.setText(TimeUtils.getInstance().secondsToTimeStringWithUnit(this.mActivity, this.time));
            if (Build.VERSION.SDK_INT >= 23) {
                this.mTimePicker.setMinute(i3);
                this.mTimePicker.setHour(i2);
            } else {
                this.mTimePicker.setCurrentMinute(Integer.valueOf(i3));
                this.mTimePicker.setCurrentHour(Integer.valueOf(i2));
            }
            getContentView().findViewById(R.id.feed_manual_tomorrow_view).setOnClickListener(this);
            str = "Tomorrow";
        }
        resizePikcer(this.mTimePicker);
        this.mFeederScaleScrollView = (FeederScaleScrollView) getContentView().findViewById(R.id.feeder_scroll_view);
        this.feederValueTextView = (TextView) getContentView().findViewById(R.id.feed_manual_amount);
        TextView textView = (TextView) getContentView().findViewById(R.id.feed_manual_amount_prompt);
        this.amountPromptTextView = textView;
        textView.setVisibility(CommonUtils.isSystemLanguateZh() ? 0 : 4);
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
        new HashMap().put("type", str);
    }

    private void initView() {
        this.mTimePicker.setIs24HourView(Boolean.valueOf(CommonUtils.getSystemTimeFormat(this.context).equals(Constants.VIA_REPORT_TYPE_CHAT_AIO)));
        this.mFeederScaleScrollView.setOnScrollListener(new BaseScaleView.OnScrollListener() { // from class: com.petkit.android.activities.feeder.widget.FeedMaunalWindow.1
            @Override // com.petkit.android.activities.go.widget.BaseScaleView.OnScrollListener
            public void onScaleScroll(int i) {
                FeedMaunalWindow.this.amountPromptTextView.setText(FeedMaunalWindow.this.mActivity.getString(R.string.About) + i + FeedMaunalWindow.this.mActivity.getString(R.string.Unit_g));
                FeedMaunalWindow.this.feederValueTextView.setText(FeederUtils.getAmountFormat(i) + FeedMaunalWindow.this.mActivity.getString(R.string.Feeder_unit_short));
                FeedMaunalWindow.this.amount = i;
            }
        });
        this.mFeederScaleScrollView.setScaleColor(-7829368);
        this.mFeederScaleScrollView.setScaleStyle(1);
        this.mFeederScaleScrollView.setSelectScaleColor(-1);
        this.mFeederScaleScrollView.setPointerRadius((int) DeviceUtils.dpToPixel(this.mActivity, 40.0f));
        this.mFeederScaleScrollView.setDefault(5);
        final InterceptScrollView interceptScrollView = (InterceptScrollView) getContentView().findViewById(R.id.pop_content_id);
        this.mFeederScaleScrollView.setScrollStateListener(new FeederScaleScrollView.IScrollStateListener() { // from class: com.petkit.android.activities.feeder.widget.FeedMaunalWindow.2
            @Override // com.petkit.android.activities.feeder.widget.FeederScaleScrollView.IScrollStateListener
            public void onScrollStateChanged(boolean z) {
                interceptScrollView.setScrollingEnabled(!z);
            }
        });
        this.mTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() { // from class: com.petkit.android.activities.feeder.widget.FeedMaunalWindow.3
            @Override // android.widget.TimePicker.OnTimeChangedListener
            public void onTimeChanged(TimePicker timePicker, int i, int i2) {
                FeedMaunalWindow.this.time = (i * 3600) + (i2 * 60);
                FeedMaunalWindow.this.timeTextView.setText(TimeUtils.getInstance().secondsToTimeStringWithUnit(FeedMaunalWindow.this.mActivity, FeedMaunalWindow.this.time));
            }
        });
        CheckBox checkBox = this.mCheckBox;
        if (checkBox != null) {
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.petkit.android.activities.feeder.widget.FeedMaunalWindow.4
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    if (z) {
                        FeedMaunalWindow.this.mTimeSelectView.setVisibility(8);
                        FeedMaunalWindow.this.mFeederScaleScrollView.setVisibility(0);
                        FeedMaunalWindow.this.amountPromptTextView.setVisibility(0);
                        return;
                    }
                    FeedMaunalWindow.this.mTimeSelectView.setVisibility(0);
                    FeedMaunalWindow.this.mTimePicker.setVisibility(0);
                    FeedMaunalWindow.this.mFeederScaleScrollView.setVisibility(8);
                    FeedMaunalWindow.this.amountPromptTextView.setVisibility(8);
                    if (CommonUtils.isEmpty(FeedMaunalWindow.this.timeTextView.getText().toString())) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeZone(FeedMaunalWindow.this.mFeederRecord.getActualTimeZone());
                        calendar.add(12, 5);
                        int i = calendar.get(11);
                        int i2 = calendar.get(12);
                        FeedMaunalWindow.this.time = (i * 3600) + (i2 * 60);
                        FeedMaunalWindow.this.timeTextView.setText(TimeUtils.getInstance().secondsToTimeStringWithUnit(FeedMaunalWindow.this.mActivity, FeedMaunalWindow.this.time));
                        if (Build.VERSION.SDK_INT >= 23) {
                            FeedMaunalWindow.this.mTimePicker.setMinute(i2);
                            FeedMaunalWindow.this.mTimePicker.setHour(i);
                        } else {
                            FeedMaunalWindow.this.mTimePicker.setCurrentMinute(Integer.valueOf(i2));
                            FeedMaunalWindow.this.mTimePicker.setCurrentHour(Integer.valueOf(i));
                        }
                    }
                }
            });
            this.mCheckBox.setChecked(true);
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
            this.mFeederScaleScrollView.setVisibility(0);
            this.amountPromptTextView.setVisibility(0);
            return;
        }
        if (id == R.id.feed_manual_time_view || id == R.id.feed_manual_tomorrow_view) {
            if (this.mTimePicker.getVisibility() == 0) {
                this.mTimePicker.setVisibility(8);
                this.mFeederScaleScrollView.setVisibility(0);
                this.amountPromptTextView.setVisibility(0);
            } else {
                this.mTimePicker.setVisibility(0);
                this.mFeederScaleScrollView.setVisibility(8);
                this.amountPromptTextView.setVisibility(8);
            }
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
        new HashMap().put("type", this.time == -1 ? "now" : "delay");
        Activity activity = this.mActivity;
        AsyncHttpUtil.post((Context) activity, ApiTools.SAMPLET_API_FEEDER_SAVE_DAILY_FEED, (Map<String, String>) map, (ResponseHandlerInterface) new AsyncHttpRespHandler(activity, true) { // from class: com.petkit.android.activities.feeder.widget.FeedMaunalWindow.5
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                DailyFeedsItemRsp dailyFeedsItemRsp = (DailyFeedsItemRsp) this.gson.fromJson(this.responseResult, DailyFeedsItemRsp.class);
                if (dailyFeedsItemRsp.getError() != null) {
                    PetkitToast.showShortToast(FeedMaunalWindow.this.mActivity, dailyFeedsItemRsp.getError().getMsg());
                    return;
                }
                FeedMaunalWindow.this.dismiss();
                DailyFeedsItemBean result = dailyFeedsItemRsp.getResult();
                FeederUtils.saveFeederItemDataFromItemBean(FeedMaunalWindow.this.mDeviceId, FeedMaunalWindow.this.mDay, result, false);
                if (FeedMaunalWindow.this.mFeedMaunalListener != null) {
                    FeedMaunalWindow.this.mFeedMaunalListener.onFeedManualSuccess(FeedMaunalWindow.this.mDay, result.getTime());
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

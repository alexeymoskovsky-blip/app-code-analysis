package com.petkit.android.activities.petkitBleDevice.d4sh.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.style.BackgroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.TimePicker;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.Consts;
import com.jess.arms.utils.DeviceUtils;
import com.jess.arms.widget.PetkitToast;
import com.loopj.android.http.ResponseHandlerInterface;
import com.petkit.android.activities.feed.mode.RefreshFeedDataEvent;
import com.petkit.android.activities.go.widget.BaseScaleView;
import com.petkit.android.activities.petkitBleDevice.d4.widget.D4ScaleScrollView;
import com.petkit.android.activities.petkitBleDevice.d4sh.mode.D4shDailyFeeds;
import com.petkit.android.activities.petkitBleDevice.d4sh.mode.D4shFeedsItemBean;
import com.petkit.android.activities.petkitBleDevice.d4sh.mode.D4shRecord;
import com.petkit.android.activities.petkitBleDevice.d4sh.utils.D4shUtils;
import com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shManualWindow;
import com.petkit.android.activities.petkitBleDevice.utils.TimeUtils;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.AsyncHttpRespHandler;
import com.petkit.android.api.http.AsyncHttpUtil;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.DateUtil;
import com.petkit.oversea.R;
import com.tencent.connect.common.Constants;
import cz.msebera.android.httpclient.Header;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.simple.eventbus.EventBus;

/* JADX INFO: loaded from: classes4.dex */
public class D4hPlayerLandscapeExtraMealWindow extends PopupWindow implements View.OnClickListener {
    private int amount;
    private int curHourOfDay;
    private int curMinute;
    private int curTime;
    private D4shRecord d4sRecord;
    private SimpleDateFormat dateFormat;
    private TextView feederValueTextView;
    private boolean isShowPicker;
    private Activity mActivity;
    private int mDay;
    private long mDeviceId;
    private D4shManualWindow.onFeedManualListener mFeedManualListener;
    private TimePicker mTimePicker;
    private View mTimeSelectView;
    private int time;
    private TextView tvConfirm;
    private TextView tvFeedManualCenterAmount;
    private TextView tvSelectedTime;
    private TextView tvToday;
    private TextView tvTomorrow;

    public interface onFeedManualListener {
        void onFeedManualSuccess(int i, int i2, int i3, int i4);
    }

    public D4hPlayerLandscapeExtraMealWindow(final Activity activity, long j, int i) {
        super(LayoutInflater.from(activity).inflate(R.layout.d4h_player_landscape_extra_meal_view, (ViewGroup) null), ArmsUtils.dip2px(activity, 375.0f), -1);
        this.isShowPicker = false;
        this.time = -2;
        this.dateFormat = new SimpleDateFormat(DateUtil.DATE_FORMAT_7);
        setFocusable(true);
        setOutsideTouchable(true);
        setBackgroundDrawable(new BitmapDrawable());
        setAnimationStyle(R.style.Base_Petkit_Player_Landscape_Selector_Window_Appearance_Animation);
        this.mActivity = activity;
        this.mDeviceId = j;
        this.d4sRecord = D4shUtils.getD4shRecordByDeviceId(j, i);
        this.tvFeedManualCenterAmount = (TextView) getContentView().findViewById(R.id.feed_manual_center_amount);
        this.tvToday = (TextView) getContentView().findViewById(R.id.tv_today);
        this.tvTomorrow = (TextView) getContentView().findViewById(R.id.tv_tomorrow);
        this.tvConfirm = (TextView) getContentView().findViewById(R.id.tv_confirm);
        this.dateFormat.setTimeZone(this.d4sRecord.getActualTimeZone());
        activity.getResources().getString(R.string.About);
        setSoftInputMode(32);
        D4ScaleScrollView d4ScaleScrollView = (D4ScaleScrollView) getContentView().findViewById(R.id.d4_scroll_view);
        d4ScaleScrollView.setmSelectedColor(R.color.d4sh_main_orange);
        d4ScaleScrollView.setOnScrollListener(new BaseScaleView.OnScrollListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4hPlayerLandscapeExtraMealWindow.1
            @Override // com.petkit.android.activities.go.widget.BaseScaleView.OnScrollListener
            public void onScaleScroll(int i2) {
                D4hPlayerLandscapeExtraMealWindow.this.amount = i2;
            }
        });
        int sysIntMap = CommonUtils.getSysIntMap(this.mActivity, Consts.D4H_LAST_FEED_AMOUNT + this.mDeviceId, 10) / this.d4sRecord.getSettings().getFactor();
        this.amount = sysIntMap;
        d4ScaleScrollView.setDefault(sysIntMap);
        d4ScaleScrollView.setScaleColor(-7829368);
        d4ScaleScrollView.setScaleStyle(1);
        d4ScaleScrollView.setSelectScaleColor(-1);
        d4ScaleScrollView.setAmountUnit(this.d4sRecord.getSettings().getFactor());
        d4ScaleScrollView.setPointerRadius((int) DeviceUtils.dpToPixel(this.mActivity, 40.0f));
        this.mTimeSelectView = getContentView().findViewById(R.id.feed_manual_time_parent);
        this.mTimePicker = (TimePicker) getContentView().findViewById(R.id.TimePicker);
        this.tvSelectedTime = (TextView) getContentView().findViewById(R.id.tv_selected_time);
        if (this.d4sRecord.getState().getPim() == 2) {
            this.mTimeSelectView.setVisibility(8);
            this.mTimePicker.setVisibility(0);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeZone(this.d4sRecord.getActualTimeZone());
            calendar.add(12, 10);
            this.curHourOfDay = calendar.get(11);
            int i2 = calendar.get(12);
            this.curMinute = i2;
            this.time = (this.curHourOfDay * 3600) + (i2 * 60);
            this.tvSelectedTime.setText(TimeUtils.getInstance().secondsToTimeStringWithUnit(activity, this.time));
            this.curTime = this.time;
            if (Build.VERSION.SDK_INT >= 23) {
                this.mTimePicker.setMinute(this.curMinute);
                this.mTimePicker.setHour(this.curHourOfDay);
            } else {
                this.mTimePicker.setCurrentMinute(Integer.valueOf(this.curMinute));
                this.mTimePicker.setCurrentHour(Integer.valueOf(this.curHourOfDay));
            }
        } else {
            this.tvSelectedTime.setText(R.string.Now);
            this.mTimeSelectView.setVisibility(8);
            this.time = -1;
        }
        this.tvToday.setOnClickListener(this);
        this.tvTomorrow.setOnClickListener(this);
        getContentView().findViewById(R.id.tv_confirm).setOnClickListener(this);
        getContentView().findViewById(R.id.tv_selected_time).setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4hPlayerLandscapeExtraMealWindow.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (D4hPlayerLandscapeExtraMealWindow.this.isShowPicker) {
                    D4hPlayerLandscapeExtraMealWindow.this.tvSelectedTime.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, CommonUtils.getAppContext().getResources().getDrawable(R.drawable.up_icon), (Drawable) null);
                    D4hPlayerLandscapeExtraMealWindow.this.mTimeSelectView.setVisibility(8);
                } else {
                    D4hPlayerLandscapeExtraMealWindow.this.tvSelectedTime.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, CommonUtils.getAppContext().getResources().getDrawable(R.drawable.down_icon), (Drawable) null);
                    D4hPlayerLandscapeExtraMealWindow.this.mTimeSelectView.setVisibility(0);
                    D4hPlayerLandscapeExtraMealWindow.this.mTimePicker.setVisibility(0);
                    Calendar calendar2 = Calendar.getInstance();
                    calendar2.setTimeZone(D4hPlayerLandscapeExtraMealWindow.this.d4sRecord.getActualTimeZone());
                    calendar2.add(12, 10);
                    int i3 = calendar2.get(11);
                    int i4 = calendar2.get(12);
                    D4hPlayerLandscapeExtraMealWindow.this.time = (i3 * 3600) + (i4 * 60);
                    D4hPlayerLandscapeExtraMealWindow.this.tvSelectedTime.setText(TimeUtils.getInstance().secondsToTimeStringWithUnit(activity, D4hPlayerLandscapeExtraMealWindow.this.time));
                    if (Build.VERSION.SDK_INT >= 23) {
                        D4hPlayerLandscapeExtraMealWindow.this.mTimePicker.setMinute(i4);
                        D4hPlayerLandscapeExtraMealWindow.this.mTimePicker.setHour(i3);
                    } else {
                        D4hPlayerLandscapeExtraMealWindow.this.mTimePicker.setCurrentMinute(Integer.valueOf(i4));
                        D4hPlayerLandscapeExtraMealWindow.this.mTimePicker.setCurrentHour(Integer.valueOf(i3));
                    }
                }
                D4hPlayerLandscapeExtraMealWindow.this.isShowPicker = !r6.isShowPicker;
            }
        });
        this.feederValueTextView = (TextView) getContentView().findViewById(R.id.feed_manual_amount);
        initView();
    }

    private void initView() {
        D4shUtils.getD4AmountNumAndUnit(this.mActivity, 0);
        D4shUtils.getD4AmountNumAndUnit(this.mActivity, 1);
        D4shUtils.getD4AmountNumAndUnit(this.mActivity, 2);
        D4shUtils.getD4AmountNumAndUnit(this.mActivity, 3);
        D4shUtils.getD4AmountNumAndUnit(this.mActivity, 4);
        D4shUtils.getD4AmountNumAndUnit(this.mActivity, 5);
        D4shUtils.getD4AmountNumAndUnit(this.mActivity, 6);
        D4shUtils.getD4AmountNumAndUnit(this.mActivity, 7);
        D4shUtils.getD4AmountNumAndUnit(this.mActivity, 8);
        D4shUtils.getD4AmountNumAndUnit(this.mActivity, 9);
        D4shUtils.getD4AmountNumAndUnit(this.mActivity, 10);
        this.mTimePicker.setIs24HourView(Boolean.valueOf(CommonUtils.getSystemTimeFormat(this.mActivity).equals(Constants.VIA_REPORT_TYPE_CHAT_AIO)));
        this.tvToday.setSelected(true);
        this.tvToday.setTextColor(-1);
        this.tvTomorrow.setSelected(false);
        this.tvTomorrow.setTextColor(this.mActivity.getResources().getColor(R.color.d4sh_main_orange));
        this.mTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4hPlayerLandscapeExtraMealWindow.3
            @Override // android.widget.TimePicker.OnTimeChangedListener
            public void onTimeChanged(TimePicker timePicker, int i, int i2) {
                if (D4hPlayerLandscapeExtraMealWindow.this.mDay != Integer.parseInt(D4hPlayerLandscapeExtraMealWindow.this.dateFormat.format(Calendar.getInstance(D4hPlayerLandscapeExtraMealWindow.this.d4sRecord.getActualTimeZone()).getTime())) || D4hPlayerLandscapeExtraMealWindow.this.d4sRecord.getState().getPim() != 2 || (i * 3600) + (i2 * 60) >= D4hPlayerLandscapeExtraMealWindow.this.curTime) {
                    D4hPlayerLandscapeExtraMealWindow.this.time = (i * 3600) + (i2 * 60);
                    D4hPlayerLandscapeExtraMealWindow.this.tvSelectedTime.setText(TimeUtils.getInstance().secondsToTimeStringWithUnit(D4hPlayerLandscapeExtraMealWindow.this.mActivity, D4hPlayerLandscapeExtraMealWindow.this.time));
                } else if (Build.VERSION.SDK_INT >= 23) {
                    D4hPlayerLandscapeExtraMealWindow.this.mTimePicker.setMinute(D4hPlayerLandscapeExtraMealWindow.this.curMinute);
                    D4hPlayerLandscapeExtraMealWindow.this.mTimePicker.setHour(D4hPlayerLandscapeExtraMealWindow.this.curHourOfDay);
                } else {
                    D4hPlayerLandscapeExtraMealWindow.this.mTimePicker.setCurrentMinute(Integer.valueOf(D4hPlayerLandscapeExtraMealWindow.this.curMinute));
                    D4hPlayerLandscapeExtraMealWindow.this.mTimePicker.setCurrentHour(Integer.valueOf(D4hPlayerLandscapeExtraMealWindow.this.curHourOfDay));
                }
            }
        });
        if (this.tvToday.isSelected()) {
            this.mDay = Integer.parseInt(this.dateFormat.format(Calendar.getInstance(this.d4sRecord.getActualTimeZone()).getTime()));
            return;
        }
        Calendar calendar = Calendar.getInstance(this.d4sRecord.getActualTimeZone());
        calendar.add(5, 1);
        this.mDay = Integer.parseInt(this.dateFormat.format(calendar.getTime()));
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tv_tomorrow || id == R.id.tv_today) {
            this.tvToday.setSelected(!r5.isSelected());
            TextView textView = this.tvToday;
            textView.setTextColor(textView.isSelected() ? -1 : this.mActivity.getResources().getColor(R.color.d4sh_main_orange));
            this.tvTomorrow.setSelected(!r5.isSelected());
            TextView textView2 = this.tvTomorrow;
            textView2.setTextColor(textView2.isSelected() ? -1 : this.mActivity.getResources().getColor(R.color.d4sh_main_orange));
            return;
        }
        if (id == R.id.iv_cancle) {
            dismiss();
            return;
        }
        if (id == R.id.tv_confirm) {
            if (this.time == -2) {
                PetkitToast.showShortToast(this.mActivity, R.string.Hint_set_feeder_plan_time_null);
                return;
            }
            if (this.amount == 0) {
                PetkitToast.showShortToast(this.mActivity, R.string.Hint_set_feeder_plan_amount_null);
                return;
            }
            if (this.tvToday.isSelected()) {
                this.mDay = Integer.parseInt(this.dateFormat.format(Calendar.getInstance(this.d4sRecord.getActualTimeZone()).getTime()));
            } else {
                Calendar calendar = Calendar.getInstance(this.d4sRecord.getActualTimeZone());
                calendar.add(5, 1);
                this.mDay = Integer.parseInt(this.dateFormat.format(calendar.getTime()));
            }
            saveManualFeed();
        }
    }

    private void saveManualFeed() {
        HashMap map = new HashMap();
        map.put("deviceId", String.valueOf(this.mDeviceId));
        map.put("day", String.valueOf(this.mDay));
        map.put("time", String.valueOf(this.time));
        map.put("amount", String.valueOf(this.amount));
        Activity activity = this.mActivity;
        AsyncHttpUtil.post((Context) activity, ApiTools.SAMPLE_API_D4H_SAVE_DAILY_FEED, (Map<String, String>) map, (ResponseHandlerInterface) new AsyncHttpRespHandler(activity, true) { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4hPlayerLandscapeExtraMealWindow.4
            @Override // com.petkit.android.api.http.AsyncHttpRespHandler, com.loopj.android.http.AsyncHttpResponseHandler
            public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
                super.onSuccess(i, headerArr, bArr);
                D4shFeedsItemBean d4shFeedsItemBean = (D4shFeedsItemBean) this.gson.fromJson(this.responseResult, D4shFeedsItemBean.class);
                if (d4shFeedsItemBean.getError() != null) {
                    PetkitToast.showShortToast(D4hPlayerLandscapeExtraMealWindow.this.mActivity, d4shFeedsItemBean.getError().getMsg());
                    return;
                }
                D4hPlayerLandscapeExtraMealWindow.this.dismiss();
                D4shDailyFeeds.D4shDailyFeed.ItemsBean result = d4shFeedsItemBean.getResult();
                if (D4hPlayerLandscapeExtraMealWindow.this.time == -1) {
                    result.setIsExecuted(1);
                }
                if (D4hPlayerLandscapeExtraMealWindow.this.time != -1) {
                    EventBus.getDefault().post(new RefreshFeedDataEvent());
                }
                CommonUtils.addSysIntMap(Consts.D4H_LAST_FEED_AMOUNT, D4hPlayerLandscapeExtraMealWindow.this.amount);
                if (D4hPlayerLandscapeExtraMealWindow.this.mFeedManualListener != null) {
                    D4hPlayerLandscapeExtraMealWindow.this.mFeedManualListener.onFeedManualSuccess(D4hPlayerLandscapeExtraMealWindow.this.mDay, result.getTime(), D4hPlayerLandscapeExtraMealWindow.this.amount, -1);
                }
            }
        }, false);
    }

    public void setFeedManualListener(D4shManualWindow.onFeedManualListener onfeedmanuallistener) {
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
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((int) DeviceUtils.dpToPixel(this.mActivity, 40.0f), -2);
        layoutParams.setMargins(0, 0, 0, 0);
        numberPicker.setLayoutParams(layoutParams);
    }

    public void show(View view) {
        showAtLocation(view, 5, 0, 0);
    }

    private void setDatePickerDividerColor(TimePicker timePicker) {
        LinearLayout linearLayout = (LinearLayout) timePicker.getChildAt(0);
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            NumberPicker numberPicker = (NumberPicker) linearLayout.getChildAt(i);
            Field[] declaredFields = NumberPicker.class.getDeclaredFields();
            int length = declaredFields.length;
            int i2 = 0;
            while (true) {
                if (i2 < length) {
                    Field field = declaredFields[i2];
                    if (field.getName().equals("mSelectionDivider")) {
                        field.setAccessible(true);
                        try {
                            field.set(numberPicker, new BackgroundColorSpan(R.drawable.shape_new_solid_corner_26_blue));
                            break;
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (IllegalArgumentException e2) {
                            e2.printStackTrace();
                        }
                    } else {
                        i2++;
                    }
                }
            }
        }
    }
}

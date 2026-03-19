package com.petkit.android.activities.virtual.d4sh.widget;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import com.alibaba.fastjson.JSON;
import com.jess.arms.utils.Consts;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.utils.DeviceUtils;
import com.jess.arms.widget.PetkitToast;
import com.petkit.android.activities.go.widget.BaseScaleView;
import com.petkit.android.activities.petkitBleDevice.BleDeviceBindNetWorkActivity;
import com.petkit.android.activities.petkitBleDevice.d4.widget.D4ScaleScrollView;
import com.petkit.android.activities.petkitBleDevice.d4sh.mode.D4shRecord;
import com.petkit.android.activities.petkitBleDevice.d4sh.utils.D4shUtils;
import com.petkit.android.activities.petkitBleDevice.utils.FamilyUtils;
import com.petkit.android.activities.virtual.mode.DeviceRegularDataResult;
import com.petkit.android.activities.virtual.widget.ExperienceMoreWindow;
import com.petkit.android.model.ChatMsgTemp;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.DateUtil;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.android.widget.windows.BasePetkitWindow;
import com.petkit.oversea.R;
import com.shopify.sample.util.MallUtils;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes6.dex */
public class VirtualD4hManualWindow extends BasePetkitWindow implements View.OnClickListener {
    private int amount;
    private int curHourOfDay;
    private int curMinute;
    private int curTime;
    private D4shRecord d4sRecord;
    private SimpleDateFormat dateFormat;
    private DeviceRegularDataResult deviceRegularDataResult;
    private TextView feederValueTextView;
    private boolean isShowPicker;
    private Activity mActivity;
    private int mDay;
    private long mDeviceId;
    private onFeedManualListener mFeedManualListener;
    private TimePicker mTimePicker;
    private View mTimeSelectView;
    private int time;
    private TextView tvConfirm;
    private TextView tvFeedManualCenterAmount;
    private TextView tvSelectedTime;
    private TextView tvToday;
    private TextView tvTomorrow;

    public interface onFeedManualListener {
        void onFeedManualSuccess(int i, int i2, int i3, ChatMsgTemp chatMsgTemp);
    }

    public VirtualD4hManualWindow(Activity activity, long j, int i) {
        super((Context) activity, LayoutInflater.from(activity).inflate(R.layout.d4h_pop_maunal_feed, (ViewGroup) null), true);
        this.isShowPicker = false;
        this.time = -2;
        this.dateFormat = new SimpleDateFormat(DateUtil.DATE_FORMAT_7);
        this.mActivity = activity;
        this.mDeviceId = j;
        this.d4sRecord = D4shUtils.getD4shRecordByDeviceId(j, i);
        this.tvFeedManualCenterAmount = (TextView) getContentView().findViewById(R.id.feed_manual_center_amount);
        this.tvToday = (TextView) getContentView().findViewById(R.id.tv_today);
        this.tvTomorrow = (TextView) getContentView().findViewById(R.id.tv_tomorrow);
        this.tvConfirm = (TextView) getContentView().findViewById(R.id.tv_confirm);
        this.context.getResources().getString(R.string.About);
        setSoftInputMode(32);
        this.mTimeSelectView = getContentView().findViewById(R.id.feed_manual_time_parent);
        this.mTimePicker = (TimePicker) getContentView().findViewById(R.id.TimePicker);
        TextView textView = (TextView) getContentView().findViewById(R.id.tv_selected_time);
        this.tvSelectedTime = textView;
        textView.setText(R.string.Now);
        this.mTimeSelectView.setVisibility(8);
        this.time = -1;
        D4ScaleScrollView d4ScaleScrollView = (D4ScaleScrollView) getContentView().findViewById(R.id.d4_scroll_view);
        d4ScaleScrollView.setmSelectedColor(R.color.d4sh_main_orange);
        d4ScaleScrollView.setOnScrollListener(new BaseScaleView.OnScrollListener() { // from class: com.petkit.android.activities.virtual.d4sh.widget.VirtualD4hManualWindow.1
            @Override // com.petkit.android.activities.go.widget.BaseScaleView.OnScrollListener
            public void onScaleScroll(int i2) {
                VirtualD4hManualWindow.this.tvFeedManualCenterAmount.setText(VirtualD4hManualWindow.this.mActivity.getString(R.string.About) + i2 + VirtualD4hManualWindow.this.mActivity.getString(R.string.Unit_g));
                VirtualD4hManualWindow.this.amount = i2;
            }
        });
        d4ScaleScrollView.setScaleColor(-7829368);
        d4ScaleScrollView.setScaleStyle(1);
        d4ScaleScrollView.setSelectScaleColor(-1);
        d4ScaleScrollView.setAmountUnit(this.d4sRecord.getSettings().getFactor());
        d4ScaleScrollView.setPointerRadius((int) DeviceUtils.dpToPixel(this.mActivity, 40.0f));
        int sysIntMap = CommonUtils.getSysIntMap(this.mActivity, Consts.D4H_LAST_FEED_AMOUNT + this.mDeviceId, 10) / this.d4sRecord.getSettings().getFactor();
        this.amount = sysIntMap;
        d4ScaleScrollView.setDefault(sysIntMap);
        d4ScaleScrollView.setScrollStateListener(new D4ScaleScrollView.IScrollStateListener() { // from class: com.petkit.android.activities.virtual.d4sh.widget.VirtualD4hManualWindow.2
            @Override // com.petkit.android.activities.petkitBleDevice.d4.widget.D4ScaleScrollView.IScrollStateListener
            public void onScrollStateChanged(boolean z) {
            }
        });
        this.tvToday.setOnClickListener(this);
        this.tvTomorrow.setOnClickListener(this);
        getContentView().findViewById(R.id.iv_cancle).setOnClickListener(this);
        getContentView().findViewById(R.id.tv_confirm).setOnClickListener(this);
        getContentView().findViewById(R.id.tv_selected_time).setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.virtual.d4sh.widget.VirtualD4hManualWindow.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (VirtualD4hManualWindow.this.isShowPicker) {
                    VirtualD4hManualWindow.this.mTimeSelectView.setVisibility(8);
                } else {
                    VirtualD4hManualWindow.this.mTimeSelectView.setVisibility(0);
                    VirtualD4hManualWindow.this.mTimePicker.setVisibility(0);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeZone(VirtualD4hManualWindow.this.d4sRecord.getActualTimeZone());
                    calendar.add(12, 10);
                    int i2 = calendar.get(11);
                    int i3 = calendar.get(12);
                    VirtualD4hManualWindow.this.tvSelectedTime.setText(String.format("%02d:%02d", Integer.valueOf(i2), Integer.valueOf(i3)));
                    VirtualD4hManualWindow.this.time = (i2 * 3600) + (i3 * 60);
                    if (Build.VERSION.SDK_INT >= 23) {
                        VirtualD4hManualWindow.this.mTimePicker.setMinute(i3);
                        VirtualD4hManualWindow.this.mTimePicker.setHour(i2);
                    } else {
                        VirtualD4hManualWindow.this.mTimePicker.setCurrentMinute(Integer.valueOf(i3));
                        VirtualD4hManualWindow.this.mTimePicker.setCurrentHour(Integer.valueOf(i2));
                    }
                }
                VirtualD4hManualWindow virtualD4hManualWindow = VirtualD4hManualWindow.this;
                virtualD4hManualWindow.isShowPicker = true ^ virtualD4hManualWindow.isShowPicker;
            }
        });
        this.feederValueTextView = (TextView) getContentView().findViewById(R.id.feed_manual_amount);
        initView();
        this.deviceRegularDataResult = (DeviceRegularDataResult) JSON.parseObject(DataHelper.getStringSF(this.mActivity, Constants.VIRTUAL_DEVICE_DATA), DeviceRegularDataResult.class);
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
        this.mTimePicker.setIs24HourView(Boolean.valueOf(CommonUtils.getSystemTimeFormat(this.context).equals(com.tencent.connect.common.Constants.VIA_REPORT_TYPE_CHAT_AIO)));
        this.tvToday.setSelected(true);
        this.tvToday.setTextColor(-1);
        this.tvTomorrow.setSelected(false);
        this.tvTomorrow.setTextColor(this.mActivity.getResources().getColor(R.color.d4sh_main_orange));
        this.mTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() { // from class: com.petkit.android.activities.virtual.d4sh.widget.VirtualD4hManualWindow.4
            @Override // android.widget.TimePicker.OnTimeChangedListener
            public void onTimeChanged(TimePicker timePicker, int i, int i2) {
                if (VirtualD4hManualWindow.this.mDay != Integer.parseInt(VirtualD4hManualWindow.this.dateFormat.format(Calendar.getInstance().getTime())) || VirtualD4hManualWindow.this.d4sRecord.getState().getPim() != 2 || (i * 3600) + (i2 * 60) >= VirtualD4hManualWindow.this.curTime) {
                    VirtualD4hManualWindow.this.tvSelectedTime.setText(String.format("%02d:%02d", Integer.valueOf(i), Integer.valueOf(i2)));
                    VirtualD4hManualWindow.this.time = (i * 3600) + (i2 * 60);
                } else if (Build.VERSION.SDK_INT >= 23) {
                    VirtualD4hManualWindow.this.mTimePicker.setMinute(VirtualD4hManualWindow.this.curMinute);
                    VirtualD4hManualWindow.this.mTimePicker.setHour(VirtualD4hManualWindow.this.curHourOfDay);
                } else {
                    VirtualD4hManualWindow.this.mTimePicker.setCurrentMinute(Integer.valueOf(VirtualD4hManualWindow.this.curMinute));
                    VirtualD4hManualWindow.this.mTimePicker.setCurrentHour(Integer.valueOf(VirtualD4hManualWindow.this.curHourOfDay));
                }
            }
        });
        if (this.tvToday.isSelected()) {
            this.mDay = Integer.parseInt(this.dateFormat.format(Calendar.getInstance().getTime()));
            return;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.add(5, 1);
        this.mDay = Integer.parseInt(this.dateFormat.format(calendar.getTime()));
    }

    @Override // com.petkit.android.widget.windows.BasePetkitWindow, android.view.View.OnClickListener
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
                this.mDay = Integer.parseInt(this.dateFormat.format(Calendar.getInstance().getTime()));
            } else {
                Calendar calendar = Calendar.getInstance();
                calendar.add(5, 1);
                this.mDay = Integer.parseInt(this.dateFormat.format(calendar.getTime()));
            }
            saveManualFeed();
        }
    }

    private void saveManualFeed() {
        new ExperienceMoreWindow(this.mActivity, new ExperienceMoreWindow.OnClickListener() { // from class: com.petkit.android.activities.virtual.d4sh.widget.VirtualD4hManualWindow.5
            @Override // com.petkit.android.activities.virtual.widget.ExperienceMoreWindow.OnClickListener
            public void onClickFirstChoice() {
                MallUtils.goToWebOrProductDetail(VirtualD4hManualWindow.this.mActivity, VirtualD4hManualWindow.this.deviceRegularDataResult.getResult().getD4h().getShareUrl().getUrl(), 2);
                VirtualD4hManualWindow.this.dismiss();
            }

            @Override // com.petkit.android.activities.virtual.widget.ExperienceMoreWindow.OnClickListener
            public void onClickSecondChoice() {
                VirtualD4hManualWindow.this.dismiss();
                if (Long.parseLong(UserInforUtils.getCurrentUserId(VirtualD4hManualWindow.this.mActivity)) != FamilyUtils.getInstance().getCurrentFamilyInfo(VirtualD4hManualWindow.this.mActivity).getOwner()) {
                    PetkitToast.showTopToast(VirtualD4hManualWindow.this.mActivity, VirtualD4hManualWindow.this.mActivity.getResources().getString(R.string.Bind_device_family_check_prompt), R.drawable.top_toast_warn_icon, 1);
                } else {
                    VirtualD4hManualWindow.this.mActivity.startActivity(BleDeviceBindNetWorkActivity.newIntent((Context) VirtualD4hManualWindow.this.mActivity, 0L, 26, "", true));
                }
            }
        }, this.mActivity.getResources().getString(R.string.Prompt), this.mActivity.getResources().getString(R.string.Experience_prompt), this.mActivity.getResources().getString(R.string.Buy_immediately), this.mActivity.getResources().getString(R.string.Device_add), !TextUtils.isEmpty(this.deviceRegularDataResult.getResult().getD4h().getShareUrl().getUrl())).show(this.mActivity.getWindow().getDecorView());
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
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((int) DeviceUtils.dpToPixel(this.mActivity, 40.0f), -2);
        layoutParams.setMargins(0, 0, 0, 0);
        numberPicker.setLayoutParams(layoutParams);
    }

    public void show(View view) {
        setFocusable(true);
        setOutsideTouchable(false);
        showAtLocation(view, 17, 0, 0);
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

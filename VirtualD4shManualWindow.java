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
import com.jess.arms.utils.DataHelper;
import com.jess.arms.utils.DeviceUtils;
import com.jess.arms.widget.PetkitToast;
import com.petkit.android.activities.petkitBleDevice.BleDeviceBindNetWorkActivity;
import com.petkit.android.activities.petkitBleDevice.d4sh.mode.D4shRecord;
import com.petkit.android.activities.petkitBleDevice.d4sh.utils.D4shUtils;
import com.petkit.android.activities.petkitBleDevice.utils.FamilyUtils;
import com.petkit.android.activities.petkitBleDevice.utils.TimeUtils;
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
public class VirtualD4shManualWindow extends BasePetkitWindow implements View.OnClickListener {
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
    private NumberPicker npBlucket1;
    private NumberPicker npBlucket2;
    private int time;
    private TextView tvConfirm;
    private TextView tvFeedManualCenterAmount;
    private TextView tvSelectedTime;
    private TextView tvToday;
    private TextView tvTomorrow;

    public interface onFeedManualListener {
        void onFeedManualSuccess(int i, int i2, int i3, int i4, ChatMsgTemp chatMsgTemp);
    }

    public VirtualD4shManualWindow(Activity activity, long j, int i) {
        super((Context) activity, LayoutInflater.from(activity).inflate(R.layout.d4sh_pop_maunal_feed, (ViewGroup) null), true);
        this.isShowPicker = false;
        this.time = -2;
        this.dateFormat = new SimpleDateFormat(DateUtil.DATE_FORMAT_7);
        this.mActivity = activity;
        this.mDeviceId = j;
        this.d4sRecord = D4shUtils.getD4shRecordByDeviceId(j, i);
        this.tvFeedManualCenterAmount = (TextView) getContentView().findViewById(R.id.feed_manual_center_amount);
        this.npBlucket1 = (NumberPicker) getContentView().findViewById(R.id.nb_blucket1);
        this.npBlucket2 = (NumberPicker) getContentView().findViewById(R.id.nb_blucket2);
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
        this.tvToday.setOnClickListener(this);
        this.tvTomorrow.setOnClickListener(this);
        getContentView().findViewById(R.id.iv_cancle).setOnClickListener(this);
        getContentView().findViewById(R.id.tv_confirm).setOnClickListener(this);
        getContentView().findViewById(R.id.tv_selected_time).setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.virtual.d4sh.widget.VirtualD4shManualWindow.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (VirtualD4shManualWindow.this.isShowPicker) {
                    VirtualD4shManualWindow.this.mTimeSelectView.setVisibility(8);
                } else {
                    VirtualD4shManualWindow.this.mTimeSelectView.setVisibility(0);
                    VirtualD4shManualWindow.this.mTimePicker.setVisibility(0);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeZone(VirtualD4shManualWindow.this.d4sRecord.getActualTimeZone());
                    calendar.add(12, 10);
                    int i2 = calendar.get(11);
                    int i3 = calendar.get(12);
                    VirtualD4shManualWindow.this.time = (i2 * 3600) + (i3 * 60);
                    VirtualD4shManualWindow.this.tvSelectedTime.setText(TimeUtils.getInstance().secondsToTimeStringWithUnit(((BasePetkitWindow) VirtualD4shManualWindow.this).context, VirtualD4shManualWindow.this.time));
                    if (Build.VERSION.SDK_INT >= 23) {
                        VirtualD4shManualWindow.this.mTimePicker.setMinute(i3);
                        VirtualD4shManualWindow.this.mTimePicker.setHour(i2);
                    } else {
                        VirtualD4shManualWindow.this.mTimePicker.setCurrentMinute(Integer.valueOf(i3));
                        VirtualD4shManualWindow.this.mTimePicker.setCurrentHour(Integer.valueOf(i2));
                    }
                }
                VirtualD4shManualWindow.this.isShowPicker = !r6.isShowPicker;
            }
        });
        this.feederValueTextView = (TextView) getContentView().findViewById(R.id.feed_manual_amount);
        initView();
        this.deviceRegularDataResult = (DeviceRegularDataResult) JSON.parseObject(DataHelper.getStringSF(this.mActivity, Constants.VIRTUAL_DEVICE_DATA), DeviceRegularDataResult.class);
    }

    private void initView() {
        String[] strArr = {D4shUtils.getD4AmountNumAndUnit(this.mActivity, 0), D4shUtils.getD4AmountNumAndUnit(this.mActivity, 1), D4shUtils.getD4AmountNumAndUnit(this.mActivity, 2), D4shUtils.getD4AmountNumAndUnit(this.mActivity, 3), D4shUtils.getD4AmountNumAndUnit(this.mActivity, 4), D4shUtils.getD4AmountNumAndUnit(this.mActivity, 5), D4shUtils.getD4AmountNumAndUnit(this.mActivity, 6), D4shUtils.getD4AmountNumAndUnit(this.mActivity, 7), D4shUtils.getD4AmountNumAndUnit(this.mActivity, 8), D4shUtils.getD4AmountNumAndUnit(this.mActivity, 9), D4shUtils.getD4AmountNumAndUnit(this.mActivity, 10)};
        setList(strArr, strArr);
        this.npBlucket1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() { // from class: com.petkit.android.activities.virtual.d4sh.widget.VirtualD4shManualWindow.2
            @Override // android.widget.NumberPicker.OnValueChangeListener
            public void onValueChange(NumberPicker numberPicker, int i, int i2) {
                if (numberPicker.getValue() != 0 || VirtualD4shManualWindow.this.npBlucket2.getValue() != 0) {
                    VirtualD4shManualWindow.this.tvConfirm.setBackgroundResource(R.drawable.solid_d4sh_orange_btn_with_radius);
                } else {
                    VirtualD4shManualWindow.this.tvConfirm.setBackgroundResource(R.drawable.shape_new_solid_corner_26_gray);
                }
            }
        });
        this.npBlucket2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() { // from class: com.petkit.android.activities.virtual.d4sh.widget.VirtualD4shManualWindow.3
            @Override // android.widget.NumberPicker.OnValueChangeListener
            public void onValueChange(NumberPicker numberPicker, int i, int i2) {
                if (numberPicker.getValue() != 0 || VirtualD4shManualWindow.this.npBlucket1.getValue() != 0) {
                    VirtualD4shManualWindow.this.tvConfirm.setBackgroundResource(R.drawable.solid_d4sh_orange_btn_with_radius);
                } else {
                    VirtualD4shManualWindow.this.tvConfirm.setBackgroundResource(R.drawable.shape_new_solid_corner_26_gray);
                }
            }
        });
        this.npBlucket1.setValue(1);
        this.npBlucket2.setValue(1);
        this.mTimePicker.setIs24HourView(Boolean.valueOf(CommonUtils.getSystemTimeFormat(this.context).equals(com.tencent.connect.common.Constants.VIA_REPORT_TYPE_CHAT_AIO)));
        this.tvToday.setSelected(true);
        this.tvToday.setTextColor(-1);
        this.tvTomorrow.setSelected(false);
        this.tvTomorrow.setTextColor(this.mActivity.getResources().getColor(R.color.d4sh_main_orange));
        this.mTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() { // from class: com.petkit.android.activities.virtual.d4sh.widget.VirtualD4shManualWindow.4
            @Override // android.widget.TimePicker.OnTimeChangedListener
            public void onTimeChanged(TimePicker timePicker, int i, int i2) {
                if (VirtualD4shManualWindow.this.mDay != Integer.parseInt(VirtualD4shManualWindow.this.dateFormat.format(Calendar.getInstance().getTime())) || VirtualD4shManualWindow.this.d4sRecord.getState().getPim() != 2 || (i * 3600) + (i2 * 60) >= VirtualD4shManualWindow.this.curTime) {
                    VirtualD4shManualWindow.this.time = (i * 3600) + (i2 * 60);
                    VirtualD4shManualWindow.this.tvSelectedTime.setText(TimeUtils.getInstance().secondsToTimeStringWithUnit(((BasePetkitWindow) VirtualD4shManualWindow.this).context, VirtualD4shManualWindow.this.time));
                } else if (Build.VERSION.SDK_INT >= 23) {
                    VirtualD4shManualWindow.this.mTimePicker.setMinute(VirtualD4shManualWindow.this.curMinute);
                    VirtualD4shManualWindow.this.mTimePicker.setHour(VirtualD4shManualWindow.this.curHourOfDay);
                } else {
                    VirtualD4shManualWindow.this.mTimePicker.setCurrentMinute(Integer.valueOf(VirtualD4shManualWindow.this.curMinute));
                    VirtualD4shManualWindow.this.mTimePicker.setCurrentHour(Integer.valueOf(VirtualD4shManualWindow.this.curHourOfDay));
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

    public void setNumberPickerValue(int i, int i2) {
        NumberPicker numberPicker = this.npBlucket1;
        if (numberPicker != null) {
            numberPicker.setValue(i);
        }
        NumberPicker numberPicker2 = this.npBlucket2;
        if (numberPicker2 != null) {
            numberPicker2.setValue(i2);
        }
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
            if (this.npBlucket1.getValue() == 0 && this.npBlucket2.getValue() == 0) {
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
        new ExperienceMoreWindow(this.mActivity, new ExperienceMoreWindow.OnClickListener() { // from class: com.petkit.android.activities.virtual.d4sh.widget.VirtualD4shManualWindow.5
            @Override // com.petkit.android.activities.virtual.widget.ExperienceMoreWindow.OnClickListener
            public void onClickFirstChoice() {
                MallUtils.goToWebOrProductDetail(VirtualD4shManualWindow.this.mActivity, VirtualD4shManualWindow.this.deviceRegularDataResult.getResult().getD4sh().getShareUrl().getUrl(), 2);
                VirtualD4shManualWindow.this.dismiss();
            }

            @Override // com.petkit.android.activities.virtual.widget.ExperienceMoreWindow.OnClickListener
            public void onClickSecondChoice() {
                VirtualD4shManualWindow.this.dismiss();
                if (Long.parseLong(UserInforUtils.getCurrentUserId(VirtualD4shManualWindow.this.mActivity)) != FamilyUtils.getInstance().getCurrentFamilyInfo(VirtualD4shManualWindow.this.mActivity).getOwner()) {
                    PetkitToast.showTopToast(VirtualD4shManualWindow.this.mActivity, VirtualD4shManualWindow.this.mActivity.getResources().getString(R.string.Bind_device_family_check_prompt), R.drawable.top_toast_warn_icon, 1);
                } else {
                    VirtualD4shManualWindow.this.mActivity.startActivity(BleDeviceBindNetWorkActivity.newIntent((Context) VirtualD4shManualWindow.this.mActivity, 0L, 25, "", true));
                }
            }
        }, this.mActivity.getResources().getString(R.string.Prompt), this.mActivity.getResources().getString(R.string.Experience_prompt), this.mActivity.getResources().getString(R.string.Buy_immediately), this.mActivity.getResources().getString(R.string.Device_add), !TextUtils.isEmpty(this.deviceRegularDataResult.getResult().getD4sh().getShareUrl().getUrl())).show(this.mActivity.getWindow().getDecorView());
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

    public void setList(String[] strArr, String[] strArr2) {
        this.npBlucket1.setDisplayedValues(strArr);
        this.npBlucket1.setValue(0);
        this.npBlucket1.setMinValue(0);
        this.npBlucket1.setMaxValue(strArr.length - 1);
        this.npBlucket1.setFocusable(true);
        this.npBlucket1.setFocusableInTouchMode(true);
        this.npBlucket1.setWrapSelectorWheel(false);
        this.npBlucket2.setDisplayedValues(strArr2);
        this.npBlucket1.setValue(0);
        this.npBlucket2.setMinValue(0);
        this.npBlucket2.setMaxValue(strArr2.length - 1);
        this.npBlucket2.setFocusable(true);
        this.npBlucket2.setFocusableInTouchMode(true);
        this.npBlucket2.setWrapSelectorWheel(false);
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

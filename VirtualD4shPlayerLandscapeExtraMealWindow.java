package com.petkit.android.activities.virtual.d4sh.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
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
import com.alibaba.fastjson.JSON;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.utils.DeviceUtils;
import com.jess.arms.widget.PetkitToast;
import com.petkit.android.activities.petkitBleDevice.BleDeviceBindNetWorkActivity;
import com.petkit.android.activities.petkitBleDevice.d4sh.mode.D4shRecord;
import com.petkit.android.activities.petkitBleDevice.d4sh.utils.D4shUtils;
import com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shManualWindow;
import com.petkit.android.activities.petkitBleDevice.utils.FamilyUtils;
import com.petkit.android.activities.petkitBleDevice.utils.TimeUtils;
import com.petkit.android.activities.virtual.mode.DeviceRegularDataResult;
import com.petkit.android.activities.virtual.widget.ExperienceMoreWindow;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.DateUtil;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.oversea.R;
import com.shopify.sample.util.MallUtils;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes6.dex */
public class VirtualD4shPlayerLandscapeExtraMealWindow extends PopupWindow implements View.OnClickListener {
    private int curHourOfDay;
    private int curMinute;
    private int curTime;
    private D4shRecord d4sRecord;
    private SimpleDateFormat dateFormat;
    private final DeviceRegularDataResult deviceRegularDataResult;
    private TextView feederValueTextView;
    private boolean isShowPicker;
    private Activity mActivity;
    private int mDay;
    private long mDeviceId;
    private D4shManualWindow.onFeedManualListener mFeedManualListener;
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
        void onFeedManualSuccess(int i, int i2, int i3, int i4);
    }

    public VirtualD4shPlayerLandscapeExtraMealWindow(Activity activity, long j, int i) {
        super(LayoutInflater.from(activity).inflate(R.layout.d4sh_player_landscape_extra_meal_view, (ViewGroup) null), ArmsUtils.dip2px(activity, 375.0f), -1);
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
        this.npBlucket1 = (NumberPicker) getContentView().findViewById(R.id.nb_blucket1);
        this.npBlucket2 = (NumberPicker) getContentView().findViewById(R.id.nb_blucket2);
        this.tvToday = (TextView) getContentView().findViewById(R.id.tv_today);
        this.tvTomorrow = (TextView) getContentView().findViewById(R.id.tv_tomorrow);
        this.tvConfirm = (TextView) getContentView().findViewById(R.id.tv_confirm);
        activity.getResources().getString(R.string.About);
        setSoftInputMode(32);
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
            this.curMinute = calendar.get(12);
            this.tvSelectedTime.setText(TimeUtils.getInstance().secondsToTimeStringWithoutUnit((int) (calendar.getTime().getTime() / 1000)));
            int i2 = this.curHourOfDay * 3600;
            int i3 = this.curMinute;
            int i4 = i2 + (i3 * 60);
            this.time = i4;
            this.curTime = i4;
            if (Build.VERSION.SDK_INT >= 23) {
                this.mTimePicker.setMinute(i3);
                this.mTimePicker.setHour(this.curHourOfDay);
            } else {
                this.mTimePicker.setCurrentMinute(Integer.valueOf(i3));
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
        getContentView().findViewById(R.id.tv_selected_time).setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.virtual.d4sh.widget.VirtualD4shPlayerLandscapeExtraMealWindow.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (VirtualD4shPlayerLandscapeExtraMealWindow.this.isShowPicker) {
                    VirtualD4shPlayerLandscapeExtraMealWindow.this.tvSelectedTime.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, CommonUtils.getAppContext().getResources().getDrawable(R.drawable.up_icon), (Drawable) null);
                    VirtualD4shPlayerLandscapeExtraMealWindow.this.mTimeSelectView.setVisibility(8);
                } else {
                    VirtualD4shPlayerLandscapeExtraMealWindow.this.tvSelectedTime.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, CommonUtils.getAppContext().getResources().getDrawable(R.drawable.down_icon), (Drawable) null);
                    VirtualD4shPlayerLandscapeExtraMealWindow.this.mTimeSelectView.setVisibility(0);
                    VirtualD4shPlayerLandscapeExtraMealWindow.this.mTimePicker.setVisibility(0);
                    Calendar calendar2 = Calendar.getInstance();
                    calendar2.setTimeZone(VirtualD4shPlayerLandscapeExtraMealWindow.this.d4sRecord.getActualTimeZone());
                    calendar2.add(12, 10);
                    int i5 = calendar2.get(11);
                    int i6 = calendar2.get(12);
                    VirtualD4shPlayerLandscapeExtraMealWindow.this.time = (i5 * 3600) + (i6 * 60);
                    VirtualD4shPlayerLandscapeExtraMealWindow.this.tvSelectedTime.setText(TimeUtils.getInstance().secondsToTimeStringWithoutUnit(VirtualD4shPlayerLandscapeExtraMealWindow.this.time));
                    if (Build.VERSION.SDK_INT >= 23) {
                        VirtualD4shPlayerLandscapeExtraMealWindow.this.mTimePicker.setMinute(i6);
                        VirtualD4shPlayerLandscapeExtraMealWindow.this.mTimePicker.setHour(i5);
                    } else {
                        VirtualD4shPlayerLandscapeExtraMealWindow.this.mTimePicker.setCurrentMinute(Integer.valueOf(i6));
                        VirtualD4shPlayerLandscapeExtraMealWindow.this.mTimePicker.setCurrentHour(Integer.valueOf(i5));
                    }
                }
                VirtualD4shPlayerLandscapeExtraMealWindow.this.isShowPicker = !r5.isShowPicker;
            }
        });
        this.feederValueTextView = (TextView) getContentView().findViewById(R.id.feed_manual_amount);
        initView();
        this.deviceRegularDataResult = (DeviceRegularDataResult) JSON.parseObject(DataHelper.getStringSF(this.mActivity, Constants.VIRTUAL_DEVICE_DATA), DeviceRegularDataResult.class);
    }

    private void initView() {
        String[] strArr = {D4shUtils.getD4AmountNumAndUnit(this.mActivity, 0), D4shUtils.getD4AmountNumAndUnit(this.mActivity, 1), D4shUtils.getD4AmountNumAndUnit(this.mActivity, 2), D4shUtils.getD4AmountNumAndUnit(this.mActivity, 3), D4shUtils.getD4AmountNumAndUnit(this.mActivity, 4), D4shUtils.getD4AmountNumAndUnit(this.mActivity, 5), D4shUtils.getD4AmountNumAndUnit(this.mActivity, 6), D4shUtils.getD4AmountNumAndUnit(this.mActivity, 7), D4shUtils.getD4AmountNumAndUnit(this.mActivity, 8), D4shUtils.getD4AmountNumAndUnit(this.mActivity, 9), D4shUtils.getD4AmountNumAndUnit(this.mActivity, 10)};
        setList(strArr, strArr);
        this.npBlucket1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() { // from class: com.petkit.android.activities.virtual.d4sh.widget.VirtualD4shPlayerLandscapeExtraMealWindow.2
            @Override // android.widget.NumberPicker.OnValueChangeListener
            public void onValueChange(NumberPicker numberPicker, int i, int i2) {
                if (numberPicker.getValue() != 0 || VirtualD4shPlayerLandscapeExtraMealWindow.this.npBlucket2.getValue() != 0) {
                    VirtualD4shPlayerLandscapeExtraMealWindow.this.tvConfirm.setBackgroundResource(R.drawable.solid_d4sh_orange_btn_with_radius);
                } else {
                    VirtualD4shPlayerLandscapeExtraMealWindow.this.tvConfirm.setBackgroundResource(R.drawable.shape_new_solid_corner_26_gray);
                }
            }
        });
        this.npBlucket2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() { // from class: com.petkit.android.activities.virtual.d4sh.widget.VirtualD4shPlayerLandscapeExtraMealWindow.3
            @Override // android.widget.NumberPicker.OnValueChangeListener
            public void onValueChange(NumberPicker numberPicker, int i, int i2) {
                if (numberPicker.getValue() != 0 || VirtualD4shPlayerLandscapeExtraMealWindow.this.npBlucket1.getValue() != 0) {
                    VirtualD4shPlayerLandscapeExtraMealWindow.this.tvConfirm.setBackgroundResource(R.drawable.solid_d4sh_orange_btn_with_radius);
                } else {
                    VirtualD4shPlayerLandscapeExtraMealWindow.this.tvConfirm.setBackgroundResource(R.drawable.shape_new_solid_corner_26_gray);
                }
            }
        });
        this.npBlucket1.setValue(1);
        this.npBlucket2.setValue(1);
        this.mTimePicker.setIs24HourView(Boolean.valueOf(CommonUtils.getSystemTimeFormat(this.mActivity).equals(com.tencent.connect.common.Constants.VIA_REPORT_TYPE_CHAT_AIO)));
        this.tvToday.setSelected(true);
        this.tvToday.setTextColor(-1);
        this.tvTomorrow.setSelected(false);
        this.tvTomorrow.setTextColor(this.mActivity.getResources().getColor(R.color.d4sh_main_orange));
        this.mTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() { // from class: com.petkit.android.activities.virtual.d4sh.widget.VirtualD4shPlayerLandscapeExtraMealWindow.4
            @Override // android.widget.TimePicker.OnTimeChangedListener
            public void onTimeChanged(TimePicker timePicker, int i, int i2) {
                if (VirtualD4shPlayerLandscapeExtraMealWindow.this.mDay != Integer.parseInt(VirtualD4shPlayerLandscapeExtraMealWindow.this.dateFormat.format(Calendar.getInstance().getTime())) || VirtualD4shPlayerLandscapeExtraMealWindow.this.d4sRecord.getState().getPim() != 2 || (i * 3600) + (i2 * 60) >= VirtualD4shPlayerLandscapeExtraMealWindow.this.curTime) {
                    int i3 = i2 * 60;
                    VirtualD4shPlayerLandscapeExtraMealWindow.this.tvSelectedTime.setText(TimeUtils.getInstance().secondsToTimeStringWithoutUnit((i * 3600) + i3));
                    VirtualD4shPlayerLandscapeExtraMealWindow.this.time = (i * 3600) + i3;
                    return;
                }
                if (Build.VERSION.SDK_INT >= 23) {
                    VirtualD4shPlayerLandscapeExtraMealWindow.this.mTimePicker.setMinute(VirtualD4shPlayerLandscapeExtraMealWindow.this.curMinute);
                    VirtualD4shPlayerLandscapeExtraMealWindow.this.mTimePicker.setHour(VirtualD4shPlayerLandscapeExtraMealWindow.this.curHourOfDay);
                } else {
                    VirtualD4shPlayerLandscapeExtraMealWindow.this.mTimePicker.setCurrentMinute(Integer.valueOf(VirtualD4shPlayerLandscapeExtraMealWindow.this.curMinute));
                    VirtualD4shPlayerLandscapeExtraMealWindow.this.mTimePicker.setCurrentHour(Integer.valueOf(VirtualD4shPlayerLandscapeExtraMealWindow.this.curHourOfDay));
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
        new ExperienceMoreWindow(this.mActivity, new ExperienceMoreWindow.OnClickListener() { // from class: com.petkit.android.activities.virtual.d4sh.widget.VirtualD4shPlayerLandscapeExtraMealWindow.5
            @Override // com.petkit.android.activities.virtual.widget.ExperienceMoreWindow.OnClickListener
            public void onClickFirstChoice() {
                MallUtils.goToWebOrProductDetail(VirtualD4shPlayerLandscapeExtraMealWindow.this.mActivity, VirtualD4shPlayerLandscapeExtraMealWindow.this.deviceRegularDataResult.getResult().getD4sh().getShareUrl().getUrl(), 2);
                VirtualD4shPlayerLandscapeExtraMealWindow.this.dismiss();
            }

            @Override // com.petkit.android.activities.virtual.widget.ExperienceMoreWindow.OnClickListener
            public void onClickSecondChoice() {
                VirtualD4shPlayerLandscapeExtraMealWindow.this.dismiss();
                if (Long.parseLong(UserInforUtils.getCurrentUserId(VirtualD4shPlayerLandscapeExtraMealWindow.this.mActivity)) != FamilyUtils.getInstance().getCurrentFamilyInfo(VirtualD4shPlayerLandscapeExtraMealWindow.this.mActivity).getOwner()) {
                    PetkitToast.showTopToast(VirtualD4shPlayerLandscapeExtraMealWindow.this.mActivity, VirtualD4shPlayerLandscapeExtraMealWindow.this.mActivity.getResources().getString(R.string.Bind_device_family_check_prompt), R.drawable.top_toast_warn_icon, 1);
                } else {
                    VirtualD4shPlayerLandscapeExtraMealWindow.this.mActivity.startActivity(BleDeviceBindNetWorkActivity.newIntent((Context) VirtualD4shPlayerLandscapeExtraMealWindow.this.mActivity, 0L, 25, "", true));
                }
            }
        }, this.mActivity.getResources().getString(R.string.Prompt), this.mActivity.getResources().getString(R.string.Experience_prompt), this.mActivity.getResources().getString(R.string.Buy_immediately), this.mActivity.getResources().getString(R.string.Device_add), !TextUtils.isEmpty(this.deviceRegularDataResult.getResult().getD4sh().getShareUrl().getUrl())).show(this.mActivity.getWindow().getDecorView());
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

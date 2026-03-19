package com.petkit.android.activities.virtual.d3.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.SpannableString;
import android.text.TextUtils;
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
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.alibaba.fastjson.JSON;
import com.jess.arms.utils.Consts;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.utils.DeviceUtils;
import com.jess.arms.widget.InterceptScrollView;
import com.jess.arms.widget.PetkitToast;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.chat.SpawningService;
import com.petkit.android.activities.feeder.widget.BaseFeederWindow;
import com.petkit.android.activities.go.widget.BaseScaleView;
import com.petkit.android.activities.petkitBleDevice.BleDeviceBindNetWorkActivity;
import com.petkit.android.activities.petkitBleDevice.d3.mode.D3Record;
import com.petkit.android.activities.petkitBleDevice.d3.utils.D3Utils;
import com.petkit.android.activities.petkitBleDevice.d3.widget.D3ScaleScrollView;
import com.petkit.android.activities.petkitBleDevice.utils.FamilyUtils;
import com.petkit.android.activities.virtual.mode.DeviceRegularDataResult;
import com.petkit.android.activities.virtual.widget.ExperienceMoreWindow;
import com.petkit.android.model.ChatMsgTemp;
import com.petkit.android.model.PayloadTemp;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.DateUtil;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.android.widget.windows.BasePetkitWindow;
import com.petkit.oversea.R;
import com.shopify.sample.util.MallUtils;
import com.tencent.connect.common.Constants;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes6.dex */
public class VirtualD3ManualWindow extends BaseFeederWindow {
    private int amount;
    private int curHourOfDay;
    private int curMinute;
    private int curTime;
    private final D3ScaleScrollView d3ScaleScrollView;
    private SimpleDateFormat dateFormat;
    private final DeviceRegularDataResult deviceRegularDataResult;
    private TextView feederValueTextView;
    private Activity mActivity;
    private CheckBox mCheckBox;
    private D3Record mD3Record;
    private int mDay;
    private long mDeviceId;
    private onFeedManualListener mFeedManualListener;
    private TimePicker mTimePicker;
    private View mTimeSelectView;
    private int time;
    private TextView timeTextView;
    private final TextView tvFeedManualCenterAmount;
    private TextView tvFoodPrompt;
    private TextView tvToday;
    private TextView tvTomorrow;

    public interface onFeedManualListener {
        void onFeedManualSuccess(int i, int i2, int i3, ChatMsgTemp chatMsgTemp);
    }

    public VirtualD3ManualWindow(Activity activity, boolean z, long j) {
        super(activity, z);
        this.time = -2;
        this.dateFormat = new SimpleDateFormat(DateUtil.DATE_FORMAT_7);
        this.mActivity = activity;
        this.mDeviceId = j;
        this.mD3Record = D3Utils.getD3RecordByDeviceId(j);
        initContentView(R.layout.d3_pop_maunal_feed);
        setTitle(this.mActivity.getString(R.string.Feeder_add_manual_online));
        this.tvFoodPrompt = (TextView) getContentView().findViewById(R.id.tv_food_prompt);
        this.tvFeedManualCenterAmount = (TextView) getContentView().findViewById(R.id.feed_manual_center_amount);
        final String string = this.context.getResources().getString(R.string.About);
        D3ScaleScrollView d3ScaleScrollView = (D3ScaleScrollView) getContentView().findViewById(R.id.d3_scroll_view);
        this.d3ScaleScrollView = d3ScaleScrollView;
        d3ScaleScrollView.setmSelectedColor(R.color.d3_main_green);
        d3ScaleScrollView.setOnScrollListener(new BaseScaleView.OnScrollListener() { // from class: com.petkit.android.activities.virtual.d3.widget.VirtualD3ManualWindow.1
            @Override // com.petkit.android.activities.go.widget.BaseScaleView.OnScrollListener
            public void onScaleScroll(int i) {
                VirtualD3ManualWindow.this.amount = i;
                SpannableString spannableString = new SpannableString(string + i + ((BasePetkitWindow) VirtualD3ManualWindow.this).context.getResources().getString(R.string.Unit_g));
                spannableString.setSpan(new ForegroundColorSpan(VirtualD3ManualWindow.this.mActivity.getResources().getColor(R.color.gray)), spannableString.toString().indexOf(string), spannableString.toString().indexOf(string) + string.length(), 33);
                VirtualD3ManualWindow.this.feederValueTextView.setText(spannableString);
                VirtualD3ManualWindow.this.tvFeedManualCenterAmount.setText(i + ((BasePetkitWindow) VirtualD3ManualWindow.this).context.getResources().getString(R.string.Unit_g));
            }
        });
        d3ScaleScrollView.setScaleColor(-7829368);
        d3ScaleScrollView.setScaleStyle(1);
        d3ScaleScrollView.setSelectScaleColor(-1);
        d3ScaleScrollView.setPointerRadius((int) DeviceUtils.dpToPixel(this.mActivity, 40.0f));
        final InterceptScrollView interceptScrollView = (InterceptScrollView) getContentView().findViewById(R.id.pop_content_id);
        d3ScaleScrollView.setScrollStateListener(new D3ScaleScrollView.IScrollStateListener() { // from class: com.petkit.android.activities.virtual.d3.widget.VirtualD3ManualWindow.2
            @Override // com.petkit.android.activities.petkitBleDevice.d3.widget.D3ScaleScrollView.IScrollStateListener
            public void onScrollStateChanged(boolean z2) {
                interceptScrollView.setScrollingEnabled(!z2);
            }
        });
        this.mTimeSelectView = getContentView().findViewById(R.id.feed_manual_time_parent);
        this.mTimePicker = (TimePicker) getContentView().findViewById(R.id.TimePicker);
        this.timeTextView = (TextView) getContentView().findViewById(R.id.feed_manual_time);
        this.mCheckBox = (CheckBox) getContentView().findViewById(R.id.feed_now_checkbox);
        this.mTimePicker.setIs24HourView(Boolean.valueOf(CommonUtils.getSystemTimeFormat(this.context).equals(Constants.VIA_REPORT_TYPE_CHAT_AIO)));
        if (this.mD3Record.getState().getPim() == 2) {
            this.mTimeSelectView.setVisibility(0);
            this.mTimePicker.setVisibility(0);
            getContentView().findViewById(R.id.feed_manual_checkbox_parent).setVisibility(8);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeZone(this.mD3Record.getActualTimeZone());
            calendar.add(12, 10);
            this.curHourOfDay = calendar.get(11);
            this.curMinute = calendar.get(12);
            this.timeTextView.setText(String.format("%02d:%02d", Integer.valueOf(this.curHourOfDay), Integer.valueOf(this.curMinute)));
            int i = this.curHourOfDay * 3600;
            int i2 = this.curMinute;
            int i3 = i + (i2 * 60);
            this.time = i3;
            this.curTime = i3;
            if (Build.VERSION.SDK_INT >= 23) {
                this.mTimePicker.setMinute(i2);
                this.mTimePicker.setHour(this.curHourOfDay);
            } else {
                this.mTimePicker.setCurrentMinute(Integer.valueOf(i2));
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
        this.action.setBackgroundResource(R.drawable.selector_solid_d3_main_color_with_bottom_radius);
        this.close.setImageResource(R.drawable.d3_close_icon);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) getContentView().findViewById(R.id.menu_layout).getLayoutParams();
        layoutParams.height = (int) (BaseApplication.displayMetrics.heightPixels * 0.6f);
        getContentView().findViewById(R.id.menu_layout).setLayoutParams(layoutParams);
        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) getContentView().findViewById(R.id.pop_action).getLayoutParams();
        layoutParams2.addRule(3, 0);
        layoutParams2.addRule(12);
        ((RelativeLayout.LayoutParams) getContentView().findViewById(R.id.pop_content_id).getLayoutParams()).addRule(2, R.id.pop_action);
        this.deviceRegularDataResult = (DeviceRegularDataResult) JSON.parseObject(DataHelper.getStringSF(this.mActivity, com.petkit.android.utils.Constants.VIRTUAL_DEVICE_DATA), DeviceRegularDataResult.class);
    }

    private void initView() {
        this.mTimePicker.setIs24HourView(Boolean.valueOf(CommonUtils.getSystemTimeFormat(this.context).equals(Constants.VIA_REPORT_TYPE_CHAT_AIO)));
        this.tvToday = (TextView) getContentView().findViewById(R.id.tv_today);
        this.tvTomorrow = (TextView) getContentView().findViewById(R.id.tv_tomorrow);
        this.tvToday.setSelected(true);
        this.tvToday.setTextColor(-1);
        this.tvTomorrow.setSelected(false);
        this.tvTomorrow.setTextColor(this.mActivity.getResources().getColor(R.color.d3_main_green));
        this.tvToday.setOnClickListener(this);
        this.tvTomorrow.setOnClickListener(this);
        int sysIntMap = CommonUtils.getSysIntMap(this.mActivity, Consts.D3_LAST_FEED_AMOUNT + this.mDeviceId, 15);
        if (sysIntMap < 5) {
            sysIntMap = 5;
        }
        this.d3ScaleScrollView.setDefault(sysIntMap);
        this.mTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() { // from class: com.petkit.android.activities.virtual.d3.widget.VirtualD3ManualWindow.3
            @Override // android.widget.TimePicker.OnTimeChangedListener
            public void onTimeChanged(TimePicker timePicker, int i, int i2) {
                if (VirtualD3ManualWindow.this.mDay != Integer.parseInt(VirtualD3ManualWindow.this.dateFormat.format(Calendar.getInstance().getTime())) || VirtualD3ManualWindow.this.mD3Record.getState().getPim() != 2 || (i * 3600) + (i2 * 60) >= VirtualD3ManualWindow.this.curTime) {
                    VirtualD3ManualWindow.this.timeTextView.setText(String.format("%02d:%02d", Integer.valueOf(i), Integer.valueOf(i2)));
                    VirtualD3ManualWindow.this.time = (i * 3600) + (i2 * 60);
                } else if (Build.VERSION.SDK_INT >= 23) {
                    VirtualD3ManualWindow.this.mTimePicker.setMinute(VirtualD3ManualWindow.this.curMinute);
                    VirtualD3ManualWindow.this.mTimePicker.setHour(VirtualD3ManualWindow.this.curHourOfDay);
                } else {
                    VirtualD3ManualWindow.this.mTimePicker.setCurrentMinute(Integer.valueOf(VirtualD3ManualWindow.this.curMinute));
                    VirtualD3ManualWindow.this.mTimePicker.setCurrentHour(Integer.valueOf(VirtualD3ManualWindow.this.curHourOfDay));
                }
            }
        });
        CheckBox checkBox = this.mCheckBox;
        if (checkBox != null) {
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.petkit.android.activities.virtual.d3.widget.VirtualD3ManualWindow.4
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    if (z) {
                        VirtualD3ManualWindow.this.mTimeSelectView.setVisibility(8);
                        return;
                    }
                    VirtualD3ManualWindow.this.mTimeSelectView.setVisibility(0);
                    VirtualD3ManualWindow.this.mTimePicker.setVisibility(0);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeZone(VirtualD3ManualWindow.this.mD3Record.getActualTimeZone());
                    calendar.add(12, 5);
                    int i = calendar.get(11);
                    int i2 = calendar.get(12);
                    VirtualD3ManualWindow.this.timeTextView.setText(String.format("%02d:%02d", Integer.valueOf(i), Integer.valueOf(i2)));
                    VirtualD3ManualWindow.this.time = (i * 3600) + (i2 * 60);
                    if (Build.VERSION.SDK_INT >= 23) {
                        VirtualD3ManualWindow.this.mTimePicker.setMinute(i2);
                        VirtualD3ManualWindow.this.mTimePicker.setHour(i);
                    } else {
                        VirtualD3ManualWindow.this.mTimePicker.setCurrentMinute(Integer.valueOf(i2));
                        VirtualD3ManualWindow.this.mTimePicker.setCurrentHour(Integer.valueOf(i));
                    }
                }
            });
            if (this.mD3Record.getState().getPim() != 2) {
                this.mCheckBox.setChecked(true);
            }
        }
        if (this.tvToday.isSelected()) {
            this.mDay = Integer.parseInt(this.dateFormat.format(Calendar.getInstance().getTime()));
            return;
        }
        Calendar calendar = Calendar.getInstance();
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
            textView.setTextColor(textView.isSelected() ? -1 : this.mActivity.getResources().getColor(R.color.d3_main_green));
            this.tvTomorrow.setSelected(!r5.isSelected());
            TextView textView2 = this.tvTomorrow;
            textView2.setTextColor(textView2.isSelected() ? -1 : this.mActivity.getResources().getColor(R.color.d3_main_green));
            if (this.tvToday.isSelected()) {
                this.mDay = Integer.parseInt(this.dateFormat.format(Calendar.getInstance().getTime()));
                return;
            }
            Calendar calendar = Calendar.getInstance();
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
            this.mDay = Integer.parseInt(this.dateFormat.format(Calendar.getInstance().getTime()));
        } else {
            Calendar calendar = Calendar.getInstance();
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
        if (this.time == -1) {
            dismiss();
            CommonUtils.addSysIntMap(this.mActivity, Consts.D3_LAST_FEED_AMOUNT + this.mDeviceId, this.amount);
            int i = (Calendar.getInstance().get(11) * 3600) + (Calendar.getInstance().get(12) * 60) + Calendar.getInstance().get(13);
            ChatMsgTemp chatMsgTemp = new ChatMsgTemp();
            long jCurrentTimeMillis = System.currentTimeMillis() / 1000;
            chatMsgTemp.setCreatetimeindex(this.time);
            chatMsgTemp.setFrom(UserInforUtils.getCurrentUserId(this.mActivity));
            chatMsgTemp.setTimestamp(String.valueOf(jCurrentTimeMillis));
            chatMsgTemp.setTo(com.petkit.android.utils.Constants.DEVICE_D3_FLAG + this.mDeviceId);
            chatMsgTemp.setType(1);
            chatMsgTemp.setStatus(0);
            PayloadTemp payloadTemp = new PayloadTemp();
            payloadTemp.setType(com.petkit.android.utils.Constants.IM_PAYLOAD_TYPE_API_D3_IMMEDIATE_STARTFEED);
            payloadTemp.setContent("{\"item\":{\"amount\":" + this.amount + ",\"id\":\"r" + i + "-1\",\"isExecuted\":0,\"src\":3,\"status\":0,\"time\":" + i + "},\"deviceId\":" + this.mDeviceId + ",\"day\":" + this.mDay + "}");
            chatMsgTemp.setPayload(payloadTemp);
            Intent intent = new Intent(D3Utils.BROADCAST_D3_FEED_START);
            intent.putExtra(com.petkit.android.utils.Constants.EXTRA_DEVICE_ID, this.mDeviceId);
            intent.putExtra(SpawningService.REALM_CHAT_MSG, chatMsgTemp);
            LocalBroadcastManager.getInstance(this.mActivity).sendBroadcast(intent);
            onFeedManualListener onfeedmanuallistener = this.mFeedManualListener;
            if (onfeedmanuallistener != null) {
                onfeedmanuallistener.onFeedManualSuccess(this.mDay, this.time, this.amount, chatMsgTemp);
                return;
            }
            return;
        }
        new ExperienceMoreWindow(this.mActivity, new ExperienceMoreWindow.OnClickListener() { // from class: com.petkit.android.activities.virtual.d3.widget.VirtualD3ManualWindow.5
            @Override // com.petkit.android.activities.virtual.widget.ExperienceMoreWindow.OnClickListener
            public void onClickFirstChoice() {
                new HashMap().put("type", UserInforUtils.getAccount().getRegion());
                MallUtils.goToWebOrProductDetail(VirtualD3ManualWindow.this.mActivity, VirtualD3ManualWindow.this.deviceRegularDataResult.getResult().getD3().getShareUrl().getUrl(), 2);
                VirtualD3ManualWindow.this.dismiss();
            }

            @Override // com.petkit.android.activities.virtual.widget.ExperienceMoreWindow.OnClickListener
            public void onClickSecondChoice() {
                VirtualD3ManualWindow.this.dismiss();
                if (Long.parseLong(UserInforUtils.getCurrentUserId(VirtualD3ManualWindow.this.mActivity)) != FamilyUtils.getInstance().getCurrentFamilyInfo(VirtualD3ManualWindow.this.mActivity).getOwner()) {
                    PetkitToast.showTopToast(VirtualD3ManualWindow.this.mActivity, VirtualD3ManualWindow.this.mActivity.getResources().getString(R.string.Bind_device_family_check_prompt), R.drawable.top_toast_warn_icon, 1);
                } else {
                    VirtualD3ManualWindow.this.mActivity.startActivity(BleDeviceBindNetWorkActivity.newIntent((Context) VirtualD3ManualWindow.this.mActivity, 0L, 9, "", true));
                }
            }
        }, this.mActivity.getResources().getString(R.string.Prompt), this.mActivity.getResources().getString(R.string.Experience_prompt), this.mActivity.getResources().getString(R.string.Buy_immediately), this.mActivity.getResources().getString(R.string.Device_add), !TextUtils.isEmpty(this.deviceRegularDataResult.getResult().getD3().getShareUrl().getUrl())).show(this.mActivity.getWindow().getDecorView());
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

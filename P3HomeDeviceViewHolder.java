package com.petkit.android.activities.home.adapter.holder;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.jess.arms.utils.ArmsUtils;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.common.utils.DeviceCenterUtils;
import com.petkit.android.activities.home.HomeActivity;
import com.petkit.android.activities.home.adapter.model.HomeDeviceData;
import com.petkit.android.activities.petkitBleDevice.ble.PetkitBleDeviceManager;
import com.petkit.android.activities.petkitBleDevice.p3.mode.CheckP3ConnectStateEvent;
import com.petkit.android.activities.petkitBleDevice.p3.mode.P3Record;
import com.petkit.android.activities.petkitBleDevice.p3.mode.P3SyncDataFlag;
import com.petkit.android.activities.petkitBleDevice.p3.utils.P3Utils;
import com.petkit.android.activities.petkitBleDevice.p3.widget.TodayLineChartView;
import com.petkit.android.activities.statistics.utils.StatisticsUtils;
import com.petkit.android.utils.DateUtil;
import com.petkit.oversea.R;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

/* JADX INFO: loaded from: classes4.dex */
public class P3HomeDeviceViewHolder extends BaseHomeDeviceViewHolder {
    public final Context context;
    public HomeDeviceData data;
    public FrameLayout flState;
    public SimpleDateFormat format;
    public ImageView ivLoading;
    public P3Record p3Record;
    public TodayLineChartView todayLineChartView;
    public TextView tvDate;

    public P3HomeDeviceViewHolder(@NonNull View view) {
        super(view);
        this.format = new SimpleDateFormat(DateUtil.DATE_FORMAT_7);
        Context context = view.getContext();
        this.context = context;
        this.ivLoading = (ImageView) view.findViewById(R.id.iv_loading);
        this.todayLineChartView = (TodayLineChartView) view.findViewById(R.id.p3_line_chart);
        this.tvDate = (TextView) view.findViewById(R.id.tv_date);
        this.todayLineChartView.setTextColor(R.color.home_chart_text_color);
        this.todayLineChartView.setTextSize(ArmsUtils.dip2px(context, 7.0f));
        EventBus.getDefault().register(this);
        this.flState = (FrameLayout) view.findViewById(R.id.fl_state_bg);
    }

    @Override // com.petkit.android.activities.home.adapter.holder.BaseHomeDeviceViewHolder
    public void updateData(HomeDeviceData homeDeviceData) {
        this.data = homeDeviceData;
        this.flState.setBackgroundResource(R.drawable.solid_home_device_state_tran);
        this.tvDeviceState.setTextColor(this.context.getResources().getColor(R.color.home_chart_text_color));
        this.tvDeviceName.setText(homeDeviceData.getData().getName());
        this.ivRedPoint.setVisibility(DeviceCenterUtils.isP3NeedOtaById(homeDeviceData.getId()) ? 0 : 8);
        this.p3Record = P3Utils.getP3RecordByDeviceId(homeDeviceData.getId());
        this.tvDeviceName.setText(homeDeviceData.getData().getName());
        this.tvDate.setVisibility(8);
        try {
            String formatYMDDateFromString = StatisticsUtils.getFormatYMDDateFromString(this.format.format(DateUtil.parseISO8601Date(homeDeviceData.getData().getSyncTime())));
            if (!TextUtils.isEmpty(formatYMDDateFromString)) {
                this.tvDate.setText(formatYMDDateFromString);
                this.tvDate.setVisibility(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        checkState(null);
        ArrayList<Integer> data = homeDeviceData.getData().getData();
        if (data != null) {
            this.todayLineChartView.setBarChartDataList(data);
        }
        if (homeDeviceData.getData().isLoading()) {
            startLoad();
        } else {
            stopLoad();
        }
    }

    public void startLoad() {
        this.ivLoading.setVisibility(0);
        this.todayLineChartView.setVisibility(4);
        if (this.ivLoading.getVisibility() == 0) {
            this.ivLoading.startAnimation(AnimationUtils.loadAnimation(this.context, R.anim.rotate_anim));
        }
    }

    public void stopLoad() {
        this.ivLoading.clearAnimation();
        this.ivLoading.setVisibility(4);
        this.todayLineChartView.setVisibility(0);
    }

    @Subscriber(mode = ThreadMode.MAIN)
    public void checkState(CheckP3ConnectStateEvent checkP3ConnectStateEvent) {
        boolean zContainsKey;
        this.tvDeviceState.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
        if (this.p3Record != null) {
            if (PetkitBleDeviceManager.getInstance().checkDeviceState(this.p3Record.getMac())) {
                Context context = this.context;
                if (context instanceof HomeActivity) {
                    zContainsKey = ((BaseApplication) context.getApplicationContext()).getP3SyncDevice().containsKey(new P3SyncDataFlag(this.p3Record.getDeviceId()));
                } else {
                    zContainsKey = ((BaseApplication) context.getApplicationContext()).getP3SyncDevice().containsKey(new P3SyncDataFlag(this.p3Record.getDeviceId()));
                }
                if (zContainsKey) {
                    this.tvDeviceState.setText(this.context.getResources().getString(R.string.Synchronizing));
                    return;
                }
                if (this.data.getData().getLowBattery() == 1) {
                    this.tvDeviceState.setText(this.context.getResources().getString(R.string.Notify_lowbattery));
                    this.tvDeviceState.setCompoundDrawablesWithIntrinsicBounds(this.context.getResources().getDrawable(R.drawable.home_item_warn_icon), (Drawable) null, (Drawable) null, (Drawable) null);
                    this.tvDeviceState.setText(this.context.getResources().getString(R.string.Notify_lowbattery));
                    this.tvDeviceState.setCompoundDrawablesWithIntrinsicBounds(this.context.getResources().getDrawable(R.drawable.home_item_warn_white_icon), (Drawable) null, (Drawable) null, (Drawable) null);
                    this.flState.setBackgroundResource(R.drawable.solid_home_device_state_red);
                    this.tvDeviceState.setTextColor(this.context.getResources().getColor(R.color.white));
                    return;
                }
                this.tvDeviceState.setText(this.context.getResources().getString(R.string.Battery_left_format, this.data.getData().getBattery() + "%"));
                return;
            }
            this.tvDeviceState.setText(R.string.Disconnect);
        }
    }
}

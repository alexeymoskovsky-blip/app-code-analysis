package com.petkit.android.activities.appwidget.mode.small;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import com.alibaba.sdk.android.oss.common.RequestParameters;
import com.jess.arms.utils.ArmsUtils;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.common.utils.DeviceCenterUtils;
import com.petkit.android.activities.home.adapter.model.HomeDeviceData;
import com.petkit.android.activities.petkitBleDevice.ble.PetkitBleDeviceManager;
import com.petkit.android.activities.petkitBleDevice.p3.mode.P3Record;
import com.petkit.android.activities.petkitBleDevice.p3.mode.P3SyncDataFlag;
import com.petkit.android.activities.petkitBleDevice.p3.utils.P3Utils;
import com.petkit.android.activities.petkitBleDevice.p3.widget.TodayLineChartView;
import com.petkit.android.utils.CommonUtils;
import com.petkit.oversea.R;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes3.dex */
public class P3SmallWidgetView extends BaseSmallWidgetView {

    @SuppressLint({"StaticFieldLeak"})
    public static class P3SmallWidgetViewHolder {
        public static final P3SmallWidgetView instance = new P3SmallWidgetView();
    }

    public static P3SmallWidgetView getInstance() {
        return P3SmallWidgetViewHolder.instance;
    }

    @Override // com.petkit.android.activities.appwidget.mode.small.BaseSmallWidgetView
    public int initRemoteView() {
        return R.layout.layout_app_small_widget_p;
    }

    @Override // com.petkit.android.activities.appwidget.mode.small.BaseSmallWidgetView
    public void setDeviceData(Context context, HomeDeviceData homeDeviceData) {
        if (homeDeviceData == null || homeDeviceData.getData() == null) {
            setDeviceError(RequestParameters.SUBRESOURCE_DELETE);
            return;
        }
        setDeviceNormal();
        this.remoteViews.setImageViewResource(R.id.iv_device_icon, R.drawable.dev_widget_p3);
        setRedPointViewVisible(DeviceCenterUtils.isP3NeedOtaById(homeDeviceData.getId()) ? 0 : 8);
        setDeviceName(homeDeviceData.getData().getName() == null ? "" : homeDeviceData.getData().getName());
        TodayLineChartView todayLineChartView = new TodayLineChartView(this.context);
        todayLineChartView.setTextColor(R.color.home_p3_line_gray);
        todayLineChartView.setTextSize(ArmsUtils.dip2px(CommonUtils.getAppContext(), 7.0f));
        P3Record p3RecordByDeviceId = P3Utils.getP3RecordByDeviceId(homeDeviceData.getId());
        if (p3RecordByDeviceId != null) {
            if (PetkitBleDeviceManager.getInstance().checkDeviceState(p3RecordByDeviceId.getMac())) {
                if (((BaseApplication) CommonUtils.getAppContext()).getP3SyncDevice().containsKey(new P3SyncDataFlag(p3RecordByDeviceId.getDeviceId()))) {
                    setDeviceWarn(false, this.context.getResources().getString(R.string.Synchronizing));
                } else if (homeDeviceData.getData().getLowBattery() == 1) {
                    setDeviceWarn(true, this.context.getResources().getString(R.string.Notify_lowbattery));
                    this.remoteViews.setTextViewCompoundDrawables(R.id.tv_device_state, R.drawable.home_item_warn_icon, 0, 0, 0);
                } else {
                    setDeviceWarn(false, this.context.getResources().getString(R.string.Battery_left_format, homeDeviceData.getData().getBattery() + "%"));
                }
            } else if (homeDeviceData.getData().getLowBattery() == 1) {
                setDeviceWarn(true, this.context.getResources().getString(R.string.Notify_lowbattery));
                this.remoteViews.setTextViewCompoundDrawables(R.id.tv_device_state, R.drawable.home_item_warn_icon, 0, 0, 0);
            } else {
                setDeviceWarn(false, this.context.getResources().getString(R.string.Battery_left_format, homeDeviceData.getData().getBattery() + "%"));
            }
        }
        ArrayList<Integer> data = homeDeviceData.getData().getData();
        if (data != null) {
            todayLineChartView.setBarChartDataList(data);
        }
        recycleBitmap();
        Bitmap bitmapCreateBitmap = createBitmap(todayLineChartView, ArmsUtils.dip2px(this.context, 136.0f), ArmsUtils.dip2px(this.context, 90.0f));
        this.chartBitmap = bitmapCreateBitmap;
        this.remoteViews.setImageViewBitmap(R.id.btv_p, bitmapCreateBitmap);
    }

    private void setDeviceWarn(boolean z, String str) {
        this.remoteViews.setViewVisibility(R.id.iv_warn, z ? 0 : 8);
        this.remoteViews.setImageViewResource(R.id.iv_warn, R.drawable.home_item_warn_icon);
        this.remoteViews.setTextViewText(R.id.tv_warn, str);
    }
}

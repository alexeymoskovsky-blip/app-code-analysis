package com.petkit.android.activities.appwidget.mode.small;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.text.TextUtils;
import androidx.core.content.ContextCompat;
import com.alibaba.sdk.android.oss.common.RequestParameters;
import com.jess.arms.utils.ArmsUtils;
import com.petkit.android.activities.common.utils.DeviceCenterUtils;
import com.petkit.android.activities.home.adapter.model.HomeDeviceData;
import com.petkit.android.activities.petkitBleDevice.mode.TimeViewResult;
import com.petkit.android.activities.petkitBleDevice.t4.mode.T4Record;
import com.petkit.android.activities.petkitBleDevice.t4.utils.T4Utils;
import com.petkit.android.activities.petkitBleDevice.widget.BleTimeView;
import com.petkit.android.utils.CommonUtils;
import com.petkit.oversea.R;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes3.dex */
public class T4SmallWidgetView extends BaseSmallWidgetView {
    public BleTimeView t3TimeView;

    @SuppressLint({"StaticFieldLeak"})
    public static class T4SmallWidgetViewHolder {
        public static final T4SmallWidgetView instance = new T4SmallWidgetView();
    }

    public static T4SmallWidgetView getInstance() {
        return T4SmallWidgetViewHolder.instance;
    }

    @Override // com.petkit.android.activities.appwidget.mode.small.BaseSmallWidgetView
    public int initRemoteView() {
        return R.layout.layout_app_small_widget_t;
    }

    @Override // com.petkit.android.activities.appwidget.mode.small.BaseSmallWidgetView
    public void setDeviceData(Context context, HomeDeviceData homeDeviceData) {
        Resources resources;
        int i;
        if (homeDeviceData == null || homeDeviceData.getData() == null) {
            setDeviceError(RequestParameters.SUBRESOURCE_DELETE);
            return;
        }
        setDeviceNormal();
        initBleTimeView();
        T4Record t4RecordByDeviceId = T4Utils.getT4RecordByDeviceId(homeDeviceData.getId());
        if (t4RecordByDeviceId != null && t4RecordByDeviceId.getK3Id() > 0) {
            setRedPointViewVisible((DeviceCenterUtils.isT4NeedOtaById(homeDeviceData.getId()) || DeviceCenterUtils.isK3NeedOtaById((long) t4RecordByDeviceId.getK3Id())) ? 0 : 8);
        } else {
            setRedPointViewVisible(DeviceCenterUtils.isT4NeedOtaById(homeDeviceData.getId()) ? 0 : 8);
        }
        String name = homeDeviceData.getData().getName();
        if (!TextUtils.isEmpty(name)) {
            setDeviceName(name);
        } else {
            setDeviceName(this.context.getResources().getString(R.string.T4_name_default));
        }
        boolean z = homeDeviceData.getData().getDeviceShared(15) != null;
        this.remoteViews.setTextViewText(R.id.tv_share, this.context.getResources().getString(R.string.Mate_share_setting));
        this.remoteViews.setViewVisibility(R.id.tv_share, z ? 0 : 8);
        ArrayList<Integer> data = homeDeviceData.getData().getData();
        ArrayList arrayList = new ArrayList();
        if (data != null) {
            for (int i2 = 0; i2 < data.size(); i2++) {
                arrayList.add(new TimeViewResult(data.get(i2).intValue() / 60, data.get(i2).intValue() / 60));
            }
            this.t3TimeView.setTimeDataList(arrayList);
        }
        if (arrayList.size() > 1) {
            resources = this.context.getResources();
            i = R.string.Unit_times;
        } else {
            resources = this.context.getResources();
            i = R.string.Unit_time;
        }
        String string = resources.getString(i);
        this.remoteViews.setTextViewText(R.id.tv_main_data, arrayList.size() + string);
        if ((homeDeviceData.getData().getStatus() == null ? -1 : homeDeviceData.getData().getStatus().getOverall()) == 2 || homeDeviceData.getData().getStatus().getPower() == 0) {
            this.lightStyle = false;
            setBackgroundRes(R.drawable.solid_home_card_bg_gray);
            this.remoteViews.setImageViewResource(R.id.iv_refresh_icon, R.drawable.app_widget_refresh_icon);
            BleTimeView bleTimeView = this.t3TimeView;
            int i3 = R.color.gray;
            bleTimeView.setTimeViewColor(i3, R.color.transparent, i3, R.color.home_p3_line_gray);
            this.t3TimeView.setPointIcon(R.drawable.home_iten_gray_flag_icon);
            this.t3TimeView.setPointIcons(R.drawable.home_iten_gray_flag_icon);
            this.remoteViews.setTextColor(R.id.tv_device_name, ContextCompat.getColor(this.context, R.color.common_text));
            this.remoteViews.setTextColor(R.id.tv_main_data, ContextCompat.getColor(this.context, R.color.common_text));
            this.remoteViews.setTextColor(R.id.tv_main_data_dsc, ContextCompat.getColor(this.context, R.color.t4_text_gray));
            this.remoteViews.setImageViewResource(R.id.iv_device_icon, R.drawable.dev_widget_t4);
        } else {
            this.lightStyle = true;
            setBackgroundRes(R.drawable.solid_home_card_bg_blue);
            this.remoteViews.setImageViewResource(R.id.iv_refresh_icon, R.drawable.app_widget_refresh_white_icon);
            BleTimeView bleTimeView2 = this.t3TimeView;
            int i4 = R.color.cozy_circle_color;
            bleTimeView2.setTimeViewColor(i4, R.color.transparent, i4, i4);
            this.t3TimeView.setPointIcon(R.drawable.home_iten_flag_icon);
            this.t3TimeView.setPointIcons(R.drawable.home_iten_flag_icon);
            this.remoteViews.setTextColor(R.id.tv_device_name, ContextCompat.getColor(this.context, R.color.white));
            this.remoteViews.setTextColor(R.id.tv_main_data, ContextCompat.getColor(this.context, R.color.white));
            this.remoteViews.setTextColor(R.id.tv_main_data_dsc, ContextCompat.getColor(this.context, R.color.white));
            this.remoteViews.setImageViewResource(R.id.iv_device_icon, R.drawable.dev_widget_t4_blue);
        }
        recycleBitmap();
        Bitmap bitmapCreateBitmap = createBitmap(this.t3TimeView, ArmsUtils.dip2px(this.context, 136.0f), ArmsUtils.dip2px(this.context, 10.0f));
        this.chartBitmap = bitmapCreateBitmap;
        this.remoteViews.setImageViewBitmap(R.id.btv_t, bitmapCreateBitmap);
        this.remoteViews.setTextViewText(R.id.tv_main_data_dsc, this.context.getResources().getString(R.string.Number_of_toilet_trips));
    }

    private void initBleTimeView() {
        BleTimeView bleTimeView = new BleTimeView(this.context);
        this.t3TimeView = bleTimeView;
        bleTimeView.setProgressHeight(ArmsUtils.dip2px(CommonUtils.getAppContext(), 1.0f));
        this.t3TimeView.setTextSize(ArmsUtils.dip2px(CommonUtils.getAppContext(), 7.0f));
        this.t3TimeView.setMode(BleTimeView.Mode.TIME_POINT);
        ArrayList arrayList = new ArrayList();
        arrayList.add(0 + this.context.getResources().getString(R.string.Unit_hour_short));
        arrayList.add(8 + this.context.getResources().getString(R.string.Unit_hour_short));
        arrayList.add(16 + this.context.getResources().getString(R.string.Unit_hour_short));
        arrayList.add(24 + this.context.getResources().getString(R.string.Unit_hour_short));
        this.t3TimeView.setMaxTimeIndex(arrayList);
    }
}

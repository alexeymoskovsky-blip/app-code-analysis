package com.petkit.android.activities.appwidget.mode.small;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import com.alibaba.sdk.android.oss.common.RequestParameters;
import com.petkit.android.activities.common.utils.DeviceCenterUtils;
import com.petkit.android.activities.home.adapter.model.HomeDeviceData;
import com.petkit.android.activities.petkitBleDevice.ble.PetkitBleDeviceManager;
import com.petkit.android.activities.petkitBleDevice.w5.mode.W5Record;
import com.petkit.android.activities.petkitBleDevice.w5.utils.W5Utils;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.oversea.R;

/* JADX INFO: loaded from: classes3.dex */
public class W5SmallWidgetView extends BaseSmallWidgetView {

    @SuppressLint({"StaticFieldLeak"})
    public static class W5SmallWidgetViewHolder {
        public static final W5SmallWidgetView instance = new W5SmallWidgetView();
    }

    public static W5SmallWidgetView getInstance() {
        return W5SmallWidgetViewHolder.instance;
    }

    @Override // com.petkit.android.activities.appwidget.mode.small.BaseSmallWidgetView
    public int initRemoteView() {
        return R.layout.layout_app_small_widget_common;
    }

    @Override // com.petkit.android.activities.appwidget.mode.small.BaseSmallWidgetView
    public void setDeviceData(Context context, HomeDeviceData homeDeviceData) {
        Resources resources;
        int i;
        Resources resources2;
        int i2;
        if (homeDeviceData == null || homeDeviceData.getData() == null) {
            setDeviceError(RequestParameters.SUBRESOURCE_DELETE);
            return;
        }
        setDeviceNormal();
        setRedPointViewVisible(DeviceCenterUtils.isW5NeedOtaById(homeDeviceData.getId()) ? 0 : 8);
        setDeviceName(homeDeviceData.getData().getName() == null ? "" : homeDeviceData.getData().getName());
        W5Record w5RecordByDeviceId = W5Utils.getW5RecordByDeviceId(homeDeviceData.getId());
        if (w5RecordByDeviceId != null) {
            if (w5RecordByDeviceId.getTypeCode() == 1 || w5RecordByDeviceId.getTypeCode() == 2) {
                this.remoteViews.setImageViewResource(R.id.iv_device_icon, R.drawable.dev_widget_w5);
            } else if (w5RecordByDeviceId.getTypeCode() == 3) {
                this.remoteViews.setImageViewResource(R.id.iv_device_icon, R.drawable.dev_widget_w5n);
            } else if (w5RecordByDeviceId.getTypeCode() == 4 || w5RecordByDeviceId.getTypeCode() == 6) {
                this.remoteViews.setImageViewResource(R.id.iv_device_icon, R.drawable.dev_widget_w4x);
            } else if (w5RecordByDeviceId.getTypeCode() == 5) {
                this.remoteViews.setImageViewResource(R.id.iv_device_icon, R.drawable.dev_widget_ctw2);
            }
            if (PetkitBleDeviceManager.getInstance().checkDeviceState(w5RecordByDeviceId.getMac(), 14)) {
                if (w5RecordByDeviceId.getBreakdownWarning() == 0) {
                    int mode = w5RecordByDeviceId.getMode();
                    if (mode == 1) {
                        setDeviceWarn(false, this.context.getResources().getString(R.string.Normal_mode));
                    } else if (mode == 2) {
                        setDeviceWarn(false, this.context.getResources().getString(R.string.Intelligent_mode));
                    }
                } else {
                    setDeviceWarn(true, this.context.getResources().getString(R.string.State_error));
                }
            } else {
                setDeviceWarn(false, this.context.getResources().getString(R.string.Disconnect));
            }
            if (w5RecordByDeviceId.getLackWarning() == 1) {
                setContent(this.context.getResources().getString(R.string.Water_lack));
            } else if (w5RecordByDeviceId.getPowerStatus() == 0 || w5RecordByDeviceId.getLackWarning() == 1 || (w5RecordByDeviceId.getSettings().getNoDisturbingSwitch() == 1 && w5RecordByDeviceId.getIsNightNoDisturbing() == 1)) {
                setContent(CommonUtils.getAppContext().getResources().getString(R.string.State_pausing));
            } else if (w5RecordByDeviceId.getPowerStatus() == 1 && w5RecordByDeviceId.getMode() == 2 && w5RecordByDeviceId.getRunStatus() == 0) {
                setContent(CommonUtils.getAppContext().getResources().getString(R.string.State_dormancying));
            } else {
                setContent(CommonUtils.getAppContext().getResources().getString(R.string.State_working));
            }
            if (w5RecordByDeviceId.getPowerStatus() == 0) {
                setContentPrompt(this.context.getResources().getString(R.string.Return_to_work_prompt));
                this.remoteViews.setViewVisibility(R.id.tv_device_state, 8);
                return;
            }
            if (w5RecordByDeviceId.getMode() == 1) {
                setContentPrompt(this.context.getString(R.string.Ctw3_work_all_day));
                return;
            }
            if (w5RecordByDeviceId.getSettings().getSmartWorkingTime() > 1) {
                resources = this.context.getResources();
                i = R.string.Unit_minutes;
            } else {
                resources = this.context.getResources();
                i = R.string.Unit_minute;
            }
            String string = resources.getString(i);
            if (w5RecordByDeviceId.getSettings().getSmartSleepTime() > 1) {
                resources2 = this.context.getResources();
                i2 = R.string.Unit_minutes;
            } else {
                resources2 = this.context.getResources();
                i2 = R.string.Unit_minute;
            }
            String string2 = resources2.getString(i2);
            boolean zEquals = "zh_CN".equals(UserInforUtils.getLanguageLocaleString(CommonUtils.getAppContext()));
            Resources resources3 = this.context.getResources();
            int i3 = R.string.Water_flowing_time_Dormant_time;
            StringBuilder sb = new StringBuilder();
            sb.append(w5RecordByDeviceId.getSettings().getSmartWorkingTime());
            sb.append(zEquals ? "" : " ");
            sb.append(string);
            String string3 = sb.toString();
            StringBuilder sb2 = new StringBuilder();
            sb2.append(w5RecordByDeviceId.getSettings().getSmartSleepTime());
            sb2.append(zEquals ? "" : " ");
            sb2.append(string2);
            setContentPrompt(resources3.getString(i3, string3, sb2.toString()));
            return;
        }
        setDeviceWarn(false, this.context.getResources().getString(R.string.Disconnect));
    }

    private void setContent(String str) {
        this.remoteViews.setTextViewText(R.id.tv_main_data, str);
    }

    public final void setContentPrompt(String str) {
        this.remoteViews.setTextViewText(R.id.tv_main_data_dsc, str);
    }

    private void setDeviceWarn(boolean z, String str) {
        this.remoteViews.setViewVisibility(R.id.iv_warn, z ? 0 : 8);
        this.remoteViews.setImageViewResource(R.id.iv_warn, R.drawable.home_item_warn_icon);
        this.remoteViews.setTextViewText(R.id.tv_warn, str);
    }
}

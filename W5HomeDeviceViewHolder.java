package com.petkit.android.activities.home.adapter.holder;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.google.gson.Gson;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.Consts;
import com.petkit.android.activities.common.utils.DeviceCenterUtils;
import com.petkit.android.activities.home.adapter.model.HomeDeviceData;
import com.petkit.android.activities.petkitBleDevice.w5.mode.W5Record;
import com.petkit.android.activities.petkitBleDevice.w5.utils.W5Utils;
import com.petkit.android.api.http.apiResponse.HomeDeviceListRsp;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.ConvertDipPx;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.oversea.R;

/* JADX INFO: loaded from: classes4.dex */
public class W5HomeDeviceViewHolder extends BaseHomeDeviceViewHolder {
    public final Context context;
    public FrameLayout flState;
    public HomeDeviceListRsp homeDeviceListRsp;
    public int purifiedWaterToday;
    public TextView tvDevicePrompt;
    public TextView tvDeviceStatus;
    public TextView tvVirtualDevice;

    public W5HomeDeviceViewHolder(@NonNull View view) {
        super(view);
        this.context = view.getContext();
        this.tvDeviceStatus = (TextView) view.findViewById(R.id.tv_device_status);
        this.tvDevicePrompt = (TextView) view.findViewById(R.id.tv_device_prompt);
        this.tvVirtualDevice = (TextView) view.findViewById(R.id.tv_virtual_device);
        this.flState = (FrameLayout) view.findViewById(R.id.fl_state_bg);
    }

    @Override // com.petkit.android.activities.home.adapter.holder.BaseHomeDeviceViewHolder
    public void updateData(HomeDeviceData homeDeviceData) {
        Context context;
        int i;
        Context context2;
        int i2;
        Resources resources;
        int i3;
        Context context3;
        int i4;
        this.flState.setBackgroundResource(R.drawable.solid_home_device_state_tran);
        this.tvDeviceState.setTextColor(this.context.getResources().getColor(R.color.home_chart_text_color));
        String name = homeDeviceData.getData().getName();
        HomeDeviceListRsp homeDeviceListRsp = (HomeDeviceListRsp) new Gson().fromJson(CommonUtils.getSysMap(Consts.SP_DEVICE_ROSTER), HomeDeviceListRsp.class);
        this.homeDeviceListRsp = homeDeviceListRsp;
        this.homeDeviceListRsp = CommonUtils.filterDevices(homeDeviceListRsp);
        this.tvDeviceName.setText(name);
        this.tvDeviceState.setVisibility(0);
        W5Record w5RecordByDeviceId = W5Utils.getW5RecordByDeviceId(homeDeviceData.getId());
        this.tvDeviceState.setCompoundDrawablePadding(ArmsUtils.dip2px(this.context, 6.0f));
        if (w5RecordByDeviceId != null) {
            if (w5RecordByDeviceId.getTypeCode() == 1 || w5RecordByDeviceId.getTypeCode() == 2) {
                this.ivDeviceIcon.setImageDrawable(this.context.getResources().getDrawable(R.drawable.dev_home_w5));
            } else if (w5RecordByDeviceId.getTypeCode() == 3) {
                this.ivDeviceIcon.setImageDrawable(this.context.getResources().getDrawable(R.drawable.dev_home_w5n));
            } else if (w5RecordByDeviceId.getTypeCode() == 4 || w5RecordByDeviceId.getTypeCode() == 6) {
                this.ivDeviceIcon.setImageDrawable(this.context.getResources().getDrawable(R.drawable.dev_home_w4x));
            } else if (w5RecordByDeviceId.getTypeCode() == 5) {
                this.ivDeviceIcon.setImageDrawable(this.context.getResources().getDrawable(R.drawable.dev_home_ctw2));
            }
        }
        if (w5RecordByDeviceId != null) {
            if (w5RecordByDeviceId.getBreakdownWarning() == 0) {
                if (this.homeDeviceListRsp.getResult().isHasRelay()) {
                    if (w5RecordByDeviceId.getTypeCode() == 2) {
                        if (w5RecordByDeviceId.getFirmware() < 24) {
                            this.tvDeviceState.setText(this.context.getString(R.string.Purified_water_today) + "：--");
                        } else {
                            if (w5RecordByDeviceId.getTypeCode() == 1) {
                                this.purifiedWaterToday = (int) canculateWxEnergyForType(1, "W5", w5RecordByDeviceId.getTodayPumpRunTime());
                            } else if (w5RecordByDeviceId.getTypeCode() == 2) {
                                this.purifiedWaterToday = (int) canculateWxEnergyForType(1, "W5C", w5RecordByDeviceId.getTodayPumpRunTime());
                            } else if (w5RecordByDeviceId.getTypeCode() == 3) {
                                this.purifiedWaterToday = (int) canculateWxEnergyForType(1, "W5N", w5RecordByDeviceId.getTodayPumpRunTime());
                            } else if (w5RecordByDeviceId.getTypeCode() == 4 || w5RecordByDeviceId.getTypeCode() == 6) {
                                this.purifiedWaterToday = (int) canculateWxEnergyForType(1, "W4X", w5RecordByDeviceId.getTodayPumpRunTime());
                            } else if (w5RecordByDeviceId.getTypeCode() == 5) {
                                this.purifiedWaterToday = (int) canculateWxEnergyForType(1, "CTW2", w5RecordByDeviceId.getTodayPumpRunTime());
                            } else {
                                this.purifiedWaterToday = 0;
                            }
                            if (this.purifiedWaterToday == 0) {
                                this.tvDeviceState.setText(this.context.getString(R.string.Purified_water_today) + "：--");
                            } else {
                                TextView textView = this.tvDeviceState;
                                StringBuilder sb = new StringBuilder();
                                sb.append(this.context.getString(R.string.Purified_water_today));
                                sb.append("：");
                                sb.append(this.purifiedWaterToday);
                                if (this.purifiedWaterToday > 1) {
                                    context3 = this.context;
                                    i4 = R.string.Unit_times;
                                } else {
                                    context3 = this.context;
                                    i4 = R.string.Unit_time;
                                }
                                sb.append(context3.getString(i4));
                                textView.setText(sb.toString());
                            }
                        }
                    } else if (w5RecordByDeviceId.getFirmware() < 35) {
                        this.tvDeviceState.setText(this.context.getString(R.string.Purified_water_today) + "：--");
                    } else {
                        if (w5RecordByDeviceId.getTypeCode() == 1) {
                            this.purifiedWaterToday = (int) canculateWxEnergyForType(1, "W5", w5RecordByDeviceId.getTodayPumpRunTime());
                        } else if (w5RecordByDeviceId.getTypeCode() == 2) {
                            this.purifiedWaterToday = (int) canculateWxEnergyForType(1, "W5C", w5RecordByDeviceId.getTodayPumpRunTime());
                        } else if (w5RecordByDeviceId.getTypeCode() == 3) {
                            this.purifiedWaterToday = (int) canculateWxEnergyForType(1, "W5N", w5RecordByDeviceId.getTodayPumpRunTime());
                        } else if (w5RecordByDeviceId.getTypeCode() == 4 || w5RecordByDeviceId.getTypeCode() == 6) {
                            this.purifiedWaterToday = (int) canculateWxEnergyForType(1, "W4X", w5RecordByDeviceId.getTodayPumpRunTime());
                        } else if (w5RecordByDeviceId.getTypeCode() == 5) {
                            this.purifiedWaterToday = (int) canculateWxEnergyForType(1, "CTW2", w5RecordByDeviceId.getTodayPumpRunTime());
                        } else {
                            this.purifiedWaterToday = 0;
                        }
                        if (this.purifiedWaterToday == 0) {
                            this.tvDeviceState.setText(this.context.getResources().getString(R.string.Purified_water_today) + "：- -");
                        } else {
                            TextView textView2 = this.tvDeviceState;
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append(this.context.getResources().getString(R.string.Purified_water_today));
                            sb2.append("：");
                            sb2.append(this.purifiedWaterToday);
                            if (this.purifiedWaterToday > 1) {
                                resources = this.context.getResources();
                                i3 = R.string.Unit_times;
                            } else {
                                resources = this.context.getResources();
                                i3 = R.string.Unit_time;
                            }
                            sb2.append(resources.getString(i3));
                            textView2.setText(sb2.toString());
                        }
                    }
                } else {
                    int mode = w5RecordByDeviceId.getMode();
                    if (mode == 1) {
                        this.tvDeviceState.setText(R.string.Normal_mode);
                    } else if (mode == 2) {
                        this.tvDeviceState.setText(R.string.Intelligent_mode);
                    }
                }
            } else {
                this.tvDeviceState.setText(R.string.State_error);
                this.tvDeviceState.setCompoundDrawablesWithIntrinsicBounds(this.context.getResources().getDrawable(R.drawable.home_item_warn_icon), (Drawable) null, (Drawable) null, (Drawable) null);
                this.tvDeviceState.setCompoundDrawablePadding(ArmsUtils.dip2px(this.context, 6.0f));
                this.tvDeviceState.setCompoundDrawablesWithIntrinsicBounds(this.context.getResources().getDrawable(R.drawable.home_item_warn_white_icon), (Drawable) null, (Drawable) null, (Drawable) null);
                this.tvDeviceState.setTextColor(CommonUtils.getColorById(R.color.white));
                this.tvDeviceState.setTextSize(ConvertDipPx.dip2px(CommonUtils.getAppContext(), 5.0f));
                this.flState.setBackgroundResource(R.drawable.solid_home_device_state_red);
            }
        } else {
            this.tvDeviceState.setText(R.string.Disconnect);
        }
        if (w5RecordByDeviceId != null) {
            if (w5RecordByDeviceId.getPowerStatus() == 0) {
                this.tvDevicePrompt.setText(this.context.getString(R.string.Return_to_work_prompt));
            } else if (w5RecordByDeviceId.getMode() == 1) {
                this.tvDevicePrompt.setText(this.context.getString(R.string.Work_all_day));
            } else {
                if (w5RecordByDeviceId.getSettings().getSmartWorkingTime() > 1) {
                    context = this.context;
                    i = R.string.Unit_minutes;
                } else {
                    context = this.context;
                    i = R.string.Unit_minute;
                }
                String string = context.getString(i);
                if (w5RecordByDeviceId.getSettings().getSmartSleepTime() > 1) {
                    context2 = this.context;
                    i2 = R.string.Unit_minutes;
                } else {
                    context2 = this.context;
                    i2 = R.string.Unit_minute;
                }
                String string2 = context2.getString(i2);
                boolean zEquals = "zh_CN".equals(UserInforUtils.getLanguageLocaleString(CommonUtils.getAppContext()));
                TextView textView3 = this.tvDevicePrompt;
                Context context4 = this.context;
                int i5 = R.string.Water_flowing_time_Dormant_time;
                StringBuilder sb3 = new StringBuilder();
                sb3.append(w5RecordByDeviceId.getSettings().getSmartWorkingTime());
                sb3.append(zEquals ? "" : " ");
                sb3.append(string);
                String string3 = sb3.toString();
                StringBuilder sb4 = new StringBuilder();
                sb4.append(w5RecordByDeviceId.getSettings().getSmartSleepTime());
                sb4.append(zEquals ? "" : " ");
                sb4.append(string2);
                textView3.setText(context4.getString(i5, string3, sb4.toString()));
            }
            if (w5RecordByDeviceId.getLackWarning() == 1) {
                this.tvDeviceState.setText(this.context.getString(R.string.Water_lack));
                this.tvDeviceState.setCompoundDrawablesWithIntrinsicBounds(this.context.getResources().getDrawable(R.drawable.home_item_warn_white_icon), (Drawable) null, (Drawable) null, (Drawable) null);
                this.tvDeviceState.setCompoundDrawablePadding(ArmsUtils.dip2px(this.context, 6.0f));
                this.tvDeviceState.setTextColor(this.context.getResources().getColor(R.color.white));
                this.tvDevicePrompt.setText("");
                this.flState.setBackgroundResource(R.drawable.solid_home_device_state_red);
                this.tvDeviceStatus.setText("--");
            } else if (w5RecordByDeviceId.getPowerStatus() == 0 || w5RecordByDeviceId.getLackWarning() == 1 || (w5RecordByDeviceId.getSettings().getNoDisturbingSwitch() == 1 && w5RecordByDeviceId.getIsNightNoDisturbing() == 1)) {
                this.tvDeviceStatus.setText(this.context.getResources().getString(R.string.State_pausing));
                this.tvDeviceStatus.setText(R.string.State_pausing);
            } else if (w5RecordByDeviceId.getPowerStatus() == 1 && w5RecordByDeviceId.getMode() == 2 && w5RecordByDeviceId.getRunStatus() == 0) {
                this.tvDeviceStatus.setText(this.context.getResources().getString(R.string.State_dormancying));
            } else {
                this.tvDeviceStatus.setText(this.context.getResources().getString(R.string.State_working));
            }
        }
        if (homeDeviceData.getId() == -1) {
            this.tvVirtualDevice.setVisibility(0);
            this.ivRedPoint.setVisibility(8);
            this.tvDeviceState.setVisibility(8);
            this.flState.setVisibility(8);
            if (homeDeviceData.getData().getTypeCode() == 1) {
                this.tvDeviceName.setText(this.context.getResources().getString(R.string.W5_name_default));
                return;
            }
            if (homeDeviceData.getData().getTypeCode() == 2) {
                this.tvDeviceName.setText(this.context.getResources().getString(R.string.W5c_name_default));
                return;
            }
            if (homeDeviceData.getData().getTypeCode() == 3) {
                this.tvDeviceName.setText(this.context.getResources().getString(R.string.W5N_name_default));
                return;
            }
            if (homeDeviceData.getData().getTypeCode() == 4) {
                this.tvDeviceName.setText(this.context.getResources().getString(R.string.W4X_name_default));
                return;
            } else if (homeDeviceData.getData().getTypeCode() == 5) {
                this.tvDeviceName.setText(this.context.getResources().getString(R.string.CTW2_name_default));
                return;
            } else {
                this.tvDeviceName.setText(this.context.getResources().getString(R.string.W4X_UVC_name_default));
                return;
            }
        }
        this.flState.setVisibility(0);
        this.tvVirtualDevice.setVisibility(8);
        this.ivRedPoint.setVisibility(DeviceCenterUtils.isW5NeedOtaById(homeDeviceData.getId()) ? 0 : 8);
        this.tvDeviceState.setVisibility(0);
    }

    public final float canculateWxEnergyForType(int i, String str, int i2) {
        float f;
        float f2;
        if (i == 1) {
            float f3 = "W5C".equals(str) ? 1.3f : 1.5f;
            f2 = "W5C".equals(str) ? 1.0f : "W4X".equals(str) ? 1.8f : 2.0f;
            f = (f3 * i2) / 60.0f;
        } else {
            f = ("W5C".equals(str) ? 0.182f : 0.75f) * i2;
            f2 = 3600000.0f;
        }
        return f / f2;
    }
}

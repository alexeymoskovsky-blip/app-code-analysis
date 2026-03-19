package com.petkit.android.activities.home.adapter.holder;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.airbnb.lottie.LottieAnimationView;
import com.jess.arms.utils.ArmsUtils;
import com.petkit.android.activities.common.utils.DeviceCenterUtils;
import com.petkit.android.activities.home.adapter.model.HomeDeviceData;
import com.petkit.android.activities.petkitBleDevice.ble.PetkitBleDeviceManager;
import com.petkit.android.activities.petkitBleDevice.r2.mode.R2Record;
import com.petkit.android.activities.petkitBleDevice.r2.utils.R2Utils;
import com.petkit.android.activities.petkitBleDevice.utils.TextUtil;
import com.petkit.android.api.http.apiResponse.LoginRsp;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.oversea.R;

/* JADX INFO: loaded from: classes4.dex */
public class R2HomeDeviceViewHolder extends BaseHomeDeviceViewHolder {
    public final Context context;
    public FrameLayout flState;
    public boolean isCentigrade;
    public LottieAnimationView ivLoading;
    public TextView tvR2Temp;
    public TextView tvTimePrompt;

    private float CentigradeToFahrenheit(float f) {
        return (f * 1.8f) + 32.0f;
    }

    public R2HomeDeviceViewHolder(@NonNull View view) {
        super(view);
        this.context = view.getContext();
        this.tvR2Temp = (TextView) view.findViewById(R.id.tv_r2_temp);
        this.tvTimePrompt = (TextView) view.findViewById(R.id.tv_time_prompt);
        this.ivLoading = (LottieAnimationView) view.findViewById(R.id.iv_loading);
        this.flState = (FrameLayout) view.findViewById(R.id.fl_state_bg);
    }

    @Override // com.petkit.android.activities.home.adapter.holder.BaseHomeDeviceViewHolder
    public void updateData(HomeDeviceData homeDeviceData) {
        this.flState.setBackgroundResource(R.drawable.solid_home_device_state_tran);
        this.tvDeviceState.setTextColor(this.context.getResources().getColor(R.color.home_chart_text_color));
        this.ivDeviceIcon.setImageResource(R.drawable.dev_home_r2_white);
        this.tvDeviceState.setCompoundDrawablePadding(ArmsUtils.dip2px(this.context, 6.0f));
        this.ivRedPoint.setVisibility(DeviceCenterUtils.isR2NeedOtaById(homeDeviceData.getId()) ? 0 : 8);
        if (homeDeviceData.getData().getShared() == null) {
            this.tvDeviceName.setText(homeDeviceData.getData().getName());
        } else {
            this.tvDeviceName.setText(homeDeviceData.getData().getShared().getOwnerNick() + "-" + homeDeviceData.getData().getName());
        }
        String string = this.context.getString(R.string.symbol_temperature);
        this.isCentigrade = true;
        LoginRsp.LoginResult currentLoginResult = UserInforUtils.getCurrentLoginResult();
        if (currentLoginResult != null && currentLoginResult.getSettings().getTempUnit() == 1) {
            string = this.context.getString(R.string.fahrenheit_temperature);
            this.isCentigrade = false;
        }
        R2Record r2RecordByDeviceId = R2Utils.getR2RecordByDeviceId(homeDeviceData.getId());
        stopLottieAnimationLoading();
        if (r2RecordByDeviceId != null) {
            if (PetkitBleDeviceManager.getInstance().checkDeviceState(r2RecordByDeviceId.getMac(), 18)) {
                if (homeDeviceData.getData().getPower() == 0) {
                    this.tvDeviceState.setText(R.string.Power_off);
                    this.tvDeviceState.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
                    String str = "——" + string;
                    this.tvR2Temp.setText(TextUtil.getInstance().makePartStringSpannableInTotalString(new SpannableString(str), str, "——", -1, 28, true));
                    this.tvTimePrompt.setVisibility(8);
                    return;
                }
                if (homeDeviceData.getData().getCurrentWarning() == 1 || homeDeviceData.getData().getVoltageWarning() == 1) {
                    String str2 = "——" + string;
                    this.tvR2Temp.setText(TextUtil.getInstance().makePartStringSpannableInTotalString(new SpannableString(str2), str2, "——", -1, 28, true));
                    this.tvTimePrompt.setVisibility(8);
                    this.tvDeviceState.setText(this.context.getString(R.string.Cozy_error));
                    this.flState.setBackgroundResource(R.drawable.solid_home_device_state_red);
                    this.tvDeviceState.setTextColor(CommonUtils.getColorById(R.color.white));
                    this.tvDeviceState.setCompoundDrawablesWithIntrinsicBounds(this.context.getResources().getDrawable(R.drawable.home_item_warn_white_icon), (Drawable) null, (Drawable) null, (Drawable) null);
                    return;
                }
                if (homeDeviceData.getData().getLackWarning() == 1) {
                    String str3 = "——" + string;
                    this.tvR2Temp.setText(TextUtil.getInstance().makePartStringSpannableInTotalString(new SpannableString(str3), str3, "——", -1, 28, true));
                    this.tvTimePrompt.setVisibility(8);
                    this.tvDeviceState.setText(this.context.getString(R.string.Water_lack));
                    this.flState.setBackgroundResource(R.drawable.solid_home_device_state_red);
                    this.tvDeviceState.setTextColor(CommonUtils.getColorById(R.color.white));
                    this.tvDeviceState.setCompoundDrawablesWithIntrinsicBounds(this.context.getResources().getDrawable(R.drawable.home_item_warn_white_icon), (Drawable) null, (Drawable) null, (Drawable) null);
                    return;
                }
                if (homeDeviceData.getData().getHeatStatus() == 1) {
                    startLottieAnimationLoading(true);
                    if (homeDeviceData.getData().getNoUpWarning() == 0) {
                        this.tvR2Temp.setText(this.context.getString(R.string.Cozy_temperature_hoting));
                        this.tvTimePrompt.setVisibility(0);
                        if (this.isCentigrade) {
                            this.tvTimePrompt.setText(this.context.getString(R.string.Cozy_target_temp) + " " + Math.round(homeDeviceData.getData().getTargetTemp()) + string);
                        } else {
                            this.tvTimePrompt.setText(this.context.getString(R.string.Cozy_target_temp) + " " + Math.round(CentigradeToFahrenheit(homeDeviceData.getData().getTargetTemp())) + string);
                        }
                        this.tvDeviceState.setText(this.context.getString(R.string.R2_heat_time) + ": " + homeDeviceData.getData().getLeftTime() + this.context.getString(R.string.Unit_minute));
                        this.tvDeviceState.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
                        return;
                    }
                    StringBuilder sb = new StringBuilder();
                    boolean z = this.isCentigrade;
                    float temp = homeDeviceData.getData().getTemp();
                    if (!z) {
                        temp = CentigradeToFahrenheit(temp);
                    }
                    sb.append(Math.round(temp));
                    sb.append("");
                    String string2 = sb.toString();
                    String str4 = string2 + string;
                    this.tvR2Temp.setText(TextUtil.getInstance().makePartStringSpannableInTotalString(new SpannableString(str4), str4, string2, -1, 28, true));
                    this.tvTimePrompt.setVisibility(0);
                    this.tvTimePrompt.setText(this.context.getString(R.string.Cozy_work_temp));
                    this.tvDeviceState.setText("");
                    this.tvDeviceState.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
                    return;
                }
                if (homeDeviceData.getData().getHeatStatus() == 2) {
                    StringBuilder sb2 = new StringBuilder();
                    boolean z2 = this.isCentigrade;
                    float temp2 = homeDeviceData.getData().getTemp();
                    if (!z2) {
                        temp2 = CentigradeToFahrenheit(temp2);
                    }
                    sb2.append(Math.round(temp2));
                    sb2.append("");
                    String string3 = sb2.toString();
                    String str5 = string3 + string;
                    this.tvR2Temp.setText(TextUtil.getInstance().makePartStringSpannableInTotalString(new SpannableString(str5), str5, string3, -1, 28, true));
                    this.tvTimePrompt.setVisibility(0);
                    this.tvTimePrompt.setText(this.context.getString(R.string.Cozy_work_temp));
                    this.tvDeviceState.setText("");
                    this.tvDeviceState.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
                    return;
                }
                if (homeDeviceData.getData().getHeatStatus() == 3) {
                    startLottieAnimationLoading(false);
                    if (homeDeviceData.getData().getNoUpWarning() == 0) {
                        this.tvR2Temp.setText(this.context.getString(R.string.Cozy_temperature_colding));
                        this.tvTimePrompt.setVisibility(0);
                        if (this.isCentigrade) {
                            this.tvTimePrompt.setText(this.context.getString(R.string.Cozy_target_temp) + " " + Math.round(homeDeviceData.getData().getTargetTemp()) + string);
                        } else {
                            this.tvTimePrompt.setText(this.context.getString(R.string.Cozy_target_temp) + " " + Math.round(CentigradeToFahrenheit(homeDeviceData.getData().getTargetTemp())) + string);
                        }
                        this.tvDeviceState.setText("");
                        this.tvDeviceState.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
                        return;
                    }
                    StringBuilder sb3 = new StringBuilder();
                    boolean z3 = this.isCentigrade;
                    float temp3 = homeDeviceData.getData().getTemp();
                    if (!z3) {
                        temp3 = CentigradeToFahrenheit(temp3);
                    }
                    sb3.append(Math.round(temp3));
                    sb3.append("");
                    String string4 = sb3.toString();
                    String str6 = string4 + string;
                    this.tvR2Temp.setText(TextUtil.getInstance().makePartStringSpannableInTotalString(new SpannableString(str6), str6, string4, -1, 28, true));
                    this.tvTimePrompt.setVisibility(0);
                    this.tvTimePrompt.setText(this.context.getString(R.string.Cozy_work_temp));
                    this.tvDeviceState.setText("");
                    this.tvDeviceState.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
                    return;
                }
                return;
            }
            this.tvDeviceState.setText(R.string.Disconnect);
            this.tvDeviceState.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
            String str7 = "——" + string;
            this.tvR2Temp.setText(TextUtil.getInstance().makePartStringSpannableInTotalString(new SpannableString(str7), str7, "——", -1, 28, true));
            this.tvTimePrompt.setVisibility(8);
            return;
        }
        this.tvDeviceState.setText(R.string.Disconnect);
        this.tvDeviceState.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
        String str8 = "——" + string;
        this.tvR2Temp.setText(TextUtil.getInstance().makePartStringSpannableInTotalString(new SpannableString(str8), str8, "——", -1, 28, true));
        this.tvTimePrompt.setVisibility(8);
    }

    public void startLoad() {
        this.ivLoading.setVisibility(0);
        if (this.ivLoading.getVisibility() == 0) {
            this.ivLoading.startAnimation(AnimationUtils.loadAnimation(this.context, R.anim.rotate_anim));
        }
    }

    public void stopLoad() {
        this.ivLoading.clearAnimation();
        this.ivLoading.setVisibility(4);
    }

    public final void startLottieAnimationLoading(boolean z) {
        this.ivLoading.setVisibility(0);
        if (z) {
            this.ivLoading.setAnimation(R.raw.r2_warming_animation);
        } else {
            this.ivLoading.setAnimation(R.raw.r2_cooling_animation);
        }
        this.ivLoading.playAnimation();
    }

    public final void stopLottieAnimationLoading() {
        this.ivLoading.cancelAnimation();
        this.ivLoading.setVisibility(4);
    }
}

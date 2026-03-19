package com.petkit.android.activities.home.adapter.newholder;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.format.Time;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.Consts;
import com.petkit.android.activities.common.utils.DeviceCenterUtils;
import com.petkit.android.activities.home.NewCardOnClickListener;
import com.petkit.android.activities.home.adapter.model.HomeDeviceData;
import com.petkit.android.activities.petkitBleDevice.mode.TimeViewResult;
import com.petkit.android.activities.petkitBleDevice.t4.mode.T4Record;
import com.petkit.android.activities.petkitBleDevice.t4.utils.T4Utils;
import com.petkit.android.activities.petkitBleDevice.utils.TextUtil;
import com.petkit.android.activities.petkitBleDevice.utils.TimeUtils;
import com.petkit.android.utils.CommonUtils;
import com.petkit.oversea.R;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes4.dex */
public class T4HomeLargeDeviceViewHolder extends BaseHomeLargeDeviceWithControlViewHolder {
    public final Context context;
    public ImageView ivSetting;
    public final TextView tvShare;

    public T4HomeLargeDeviceViewHolder(@NonNull View view) {
        super(view);
        this.context = view.getContext();
        this.ivSetting = (ImageView) view.findViewById(R.id.iv_setting);
        this.tvShare = (TextView) view.findViewById(R.id.tv_share);
    }

    @Override // com.petkit.android.activities.home.adapter.newholder.BaseHomeLargeDeviceViewHolder
    public void updateData(HomeDeviceData homeDeviceData, int i, NewCardOnClickListener newCardOnClickListener) {
        String string;
        int color;
        int overall;
        Context context;
        int i2;
        String strSecondsToTimeStringWithUnit;
        this.tvTimePrompt.setText("");
        T4Record t4RecordByDeviceId = T4Utils.getT4RecordByDeviceId(homeDeviceData.getId());
        if (homeDeviceData.getData().getTypeCode() == 2) {
            this.ivDeviceIcon.setImageResource(R.drawable.large_home_card_t4_2_icon);
        } else {
            this.ivDeviceIcon.setImageResource(R.drawable.large_home_card_t4_icon);
        }
        int controlSettings = homeDeviceData.getData().getControlSettings();
        if (controlSettings == 1) {
            this.rlControl.setVisibility(0);
            this.ivDeviceIcon.setVisibility(8);
            if (homeDeviceData.getData().getState() != 2 && homeDeviceData.getData().getState() != 4 && homeDeviceData.getData().getStatus().getPower() != 0 && homeDeviceData.getData().getStatus().getOta() != 1) {
                if (homeDeviceData.getData().getStatus().getWorkState() != null && homeDeviceData.getData().getStatus().getWorkState().getWorkMode() == 0) {
                    this.tvControl.setText(this.context.getString(R.string.Cleaning));
                    this.ivControl.setBackgroundResource(R.drawable.card_t6_cleaning_up_gray_icon);
                    this.rlControl.setBackgroundResource(R.drawable.solid_card_control_blue_corners_16);
                    this.tvControl.setTextColor(this.context.getResources().getColor(R.color.new_home_card_light_blue));
                } else {
                    this.tvControl.setText(this.context.getString(R.string.Clean));
                    this.ivControl.setBackgroundResource(R.drawable.card_t6_clean_up_icon);
                    this.rlControl.setBackgroundResource(R.drawable.solid_card_control_blue_corners_16);
                    this.tvControl.setTextColor(this.context.getResources().getColor(R.color.new_home_card_blue));
                }
            } else {
                this.tvControl.setText(this.context.getString(R.string.Clean));
                this.ivControl.setBackgroundResource(R.drawable.card_t6_clean_up_gray_icon);
                this.rlControl.setBackgroundResource(R.drawable.solid_card_control_blue_corners_16);
                this.tvControl.setTextColor(this.context.getResources().getColor(R.color.t4_text_gray));
            }
        } else if (controlSettings == 2) {
            this.rlControl.setVisibility(0);
            this.ivDeviceIcon.setVisibility(8);
            if (homeDeviceData.getData().getState() != 2 && homeDeviceData.getData().getState() != 4 && homeDeviceData.getData().getStatus().getPower() != 0 && homeDeviceData.getData().getStatus().getOta() != 1) {
                if (homeDeviceData.getData().getStatus().getRefreshState() != null && homeDeviceData.getData().getStatus().getRefreshState().getWorkProcess() == 1) {
                    this.tvControl.setText(this.context.getString(R.string.Deodorizing));
                    this.ivControl.setBackgroundResource(R.drawable.control_deodorization_gray_icon);
                    this.rlControl.setBackgroundResource(R.drawable.solid_card_control_blue_corners_16);
                    this.tvControl.setTextColor(this.context.getResources().getColor(R.color.new_home_card_light_blue));
                } else {
                    this.tvControl.setText(this.context.getString(R.string.Deodorize));
                    this.ivControl.setBackgroundResource(R.drawable.control_deodorization_blue_icon);
                    this.rlControl.setBackgroundResource(R.drawable.solid_card_control_blue_corners_16);
                    this.tvControl.setTextColor(this.context.getResources().getColor(R.color.new_home_card_blue));
                }
            } else {
                this.tvControl.setText(this.context.getString(R.string.Deodorize));
                this.ivControl.setBackgroundResource(R.drawable.control_deodorization_gray_icon);
                this.rlControl.setBackgroundResource(R.drawable.solid_card_control_blue_corners_16);
                this.tvControl.setTextColor(this.context.getResources().getColor(R.color.t4_text_gray));
            }
            if (t4RecordByDeviceId == null || t4RecordByDeviceId.getK3Id() == 0) {
                this.rlControl.setVisibility(8);
                this.ivDeviceIcon.setVisibility(0);
            }
        } else {
            this.rlControl.setVisibility(8);
            this.ivDeviceIcon.setVisibility(0);
        }
        this.tvDeviceState.setTextColor(this.context.getResources().getColor(R.color.white));
        this.tvDeviceState.setPadding(0, 0, 0, 0);
        this.tvDeviceState.setBackground(new ColorDrawable(0));
        int iMax = homeDeviceData.getData().getStatus() != null ? Math.max(homeDeviceData.getData().getStatus().getDeodorantLeftDays(), 0) : 0;
        if (iMax > 0) {
            string = this.context.getString(R.string.Unit_days);
            color = this.context.getResources().getColor(R.color.home_black_111111);
            this.tvConsumableContentOne.setTextColor(this.context.getResources().getColor(R.color.home_black_111111));
            this.tvConsumableContentOne.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
        } else {
            string = this.context.getString(R.string.Unit_day);
            color = this.context.getResources().getColor(R.color.home_black_111111);
            this.tvConsumableContentOne.setTextColor(color);
            this.tvConsumableContentOne.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, this.context.getResources().getDrawable(R.drawable.icon_home_card_yellow_warn), (Drawable) null);
        }
        String str = iMax + string;
        this.tvConsumableContentOne.setText(TextUtil.getInstance().makePartStringSpannableInTotalString(new SpannableString(str), str, string, color, 10, true));
        if (t4RecordByDeviceId != null) {
            Context context2 = this.context;
            StringBuilder sb = new StringBuilder();
            sb.append(Consts.T4_DEODORIZATION_TAG);
            sb.append(t4RecordByDeviceId.getDeviceId());
            this.llConsumableContentOne.setVisibility(CommonUtils.getSysBoolMap(context2, sb.toString(), true) ? 0 : 8);
        } else {
            this.llConsumableContentOne.setVisibility(0);
        }
        if (homeDeviceData.getData().getStatus() != null) {
            if (homeDeviceData.getData().getStatus().isLiquidLack()) {
                this.tvConsumableContentTwo.setText(R.string.T4_sand_insufficient);
                this.tvConsumableContentTwo.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, this.context.getResources().getDrawable(R.drawable.icon_home_card_yellow_warn), (Drawable) null);
            } else {
                this.tvConsumableContentTwo.setText(R.string.T4_sand_adequate);
                this.tvConsumableContentTwo.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
            }
        }
        this.tvShare.setVisibility(homeDeviceData.getShared() == 1 ? 0 : 8);
        if (homeDeviceData.getData().getDeviceShared(15) != null) {
            overall = homeDeviceData.getData().getStatus().getOverall();
            this.tvDeviceName.setMaxLines(1);
            this.tvDeviceName.setText(homeDeviceData.getData().getName());
            String name = homeDeviceData.getData().getName();
            if (!TextUtils.isEmpty(name)) {
                this.tvDeviceName.setText(name);
            } else {
                this.tvDeviceName.setText(this.context.getString(R.string.T4_name_default));
            }
        } else {
            this.tvDeviceName.setMaxLines(1);
            overall = homeDeviceData.getData().getStatus().getOverall();
            String name2 = homeDeviceData.getData().getName();
            if (!TextUtils.isEmpty(name2)) {
                this.tvDeviceName.setText(name2);
            } else {
                this.tvDeviceName.setText(this.context.getString(R.string.T4_name_default));
            }
        }
        this.tvDeviceState.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
        this.tvDeviceState.setCompoundDrawablePadding(ArmsUtils.dip2px(this.context, 6.0f));
        this.ivSetting.setVisibility(8);
        if (2 == overall) {
            this.tvDeviceState.setText(R.string.Offline);
            this.tvDeviceState.setTextColor(this.context.getResources().getColor(R.color.white));
            setUnconnectDeviceState();
        } else {
            if (homeDeviceData.getData().getStatus().getPetInTime() != 0) {
                this.tvTimePrompt.setVisibility(0);
                this.tvTimePrompt.setText(R.string.Home_item_pet_into_prompt);
            } else if (homeDeviceData.getData().getStatus().getWorkState() != null || homeDeviceData.getData().getStatus().getRefreshState() != null) {
                this.tvTimePrompt.setVisibility(0);
                this.tvTimePrompt.setText(R.string.State_working);
            } else if (homeDeviceData.getData().getDesc() == null) {
                this.tvTimePrompt.setVisibility(8);
            } else {
                this.tvTimePrompt.setText(homeDeviceData.getData().getDesc());
                this.tvTimePrompt.setVisibility(0);
            }
            if (homeDeviceData.getData().getStatus().getPower() == 0) {
                this.tvDeviceState.setText(R.string.Power_off);
                setUnconnectDeviceState();
            } else if (homeDeviceData.getData().getStatus().getOta() == 1) {
                this.tvDeviceState.setText(R.string.Card_device_up);
                setUpdatingDeviceState();
            } else if (overall == 4) {
                setWarnDeviceState();
                this.tvDeviceState.setText(R.string.Cozy_error);
            } else if (homeDeviceData.getData().getTypeCode() == 2 && homeDeviceData.getData().getStatus().getBoxState() == 0) {
                setWarnDeviceState();
                this.tvDeviceState.setText(R.string.T4_box_not_install);
            } else if (homeDeviceData.getData().getStatus().isBoxFull()) {
                setWarnDeviceState();
                this.tvDeviceState.setText(R.string.Box_is_full);
            } else {
                this.rlDeviceState.setVisibility(8);
                if (homeDeviceData.getId() != -1) {
                    this.ivSetting.setVisibility(8);
                }
            }
        }
        ArrayList<Integer> data = homeDeviceData.getData().getData();
        ArrayList arrayList = new ArrayList();
        if (data != null) {
            for (int i3 = 0; i3 < data.size(); i3++) {
                arrayList.add(new TimeViewResult(data.get(i3).intValue() / 60, data.get(i3).intValue() / 60));
            }
        }
        if (arrayList.size() != 1) {
            context = this.context;
            i2 = R.string.Unit_times;
        } else {
            context = this.context;
            i2 = R.string.Unit_time;
        }
        String string2 = context.getString(i2);
        SpannableString spannableString = new SpannableString(arrayList.size() + string2);
        this.tvTime.setText(TextUtil.getInstance().makePartStringSpannableInTotalString(spannableString, arrayList.size() + string2, string2, this.context.getResources().getColor(R.color.home_black_111111), 12, true));
        if (homeDeviceData.getId() == -1) {
            this.rlDeviceState.setVisibility(0);
            this.ivRedPoint.setVisibility(8);
            this.tvDeviceState.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
            this.rlDeviceState.setBackgroundResource(R.drawable.solid_home_device_new_state_virtual_experience);
            this.tvDeviceState.setText(R.string.Virtual_experience);
            this.tvDeviceState.setTextColor(this.context.getResources().getColor(R.color.light_black));
            this.tvDeviceName.setText(this.context.getResources().getString(R.string.T4_name_default));
            String string3 = this.context.getString(R.string.Unit_days);
            String str2 = 30 + string3;
            this.tvConsumableContentOne.setText(TextUtil.getInstance().makePartStringSpannableInTotalString(new SpannableString(str2), str2, string3, this.context.getResources().getColor(R.color.home_black_111111), 10, true));
            this.tvConsumableContentOne.setTextColor(this.context.getResources().getColor(R.color.home_black_111111));
            this.tvConsumableContentOne.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
            this.llConsumableContentOne.setVisibility(0);
            this.tvConsumableContentTwo.setText(R.string.T4_sand_adequate);
            this.tvConsumableContentTwo.setTextColor(this.context.getResources().getColor(R.color.home_black_111111));
            this.tvConsumableContentTwo.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
            this.llConsumableContentTwo.setVisibility(0);
            if (t4RecordByDeviceId != null) {
                String string4 = this.context.getString(R.string.Last_clean_time);
                Time time = new Time();
                time.setToNow();
                int i4 = time.hour;
                int i5 = time.minute;
                if (i4 > 18 && i5 > 1) {
                    strSecondsToTimeStringWithUnit = TimeUtils.getInstance().secondsToTimeStringWithUnit(this.context, 64860);
                } else if (i4 > 16 && i5 > 2) {
                    strSecondsToTimeStringWithUnit = TimeUtils.getInstance().secondsToTimeStringWithUnit(this.context, 57720);
                } else if (i4 > 6 && i5 > 1) {
                    strSecondsToTimeStringWithUnit = TimeUtils.getInstance().secondsToTimeStringWithUnit(this.context, 21660);
                } else if (i4 > 4 && i5 > 30) {
                    strSecondsToTimeStringWithUnit = TimeUtils.getInstance().secondsToTimeStringWithUnit(this.context, 16200);
                } else {
                    strSecondsToTimeStringWithUnit = TimeUtils.getInstance().secondsToTimeStringWithUnit(this.context, 0);
                }
                this.tvTimePrompt.setVisibility(0);
                this.tvTimePrompt.setText(string4 + strSecondsToTimeStringWithUnit);
                return;
            }
            this.tvTimePrompt.setVisibility(8);
            return;
        }
        if (homeDeviceData.getData().getDeviceShared(15) != null) {
            this.ivRedPoint.setVisibility(8);
            if (t4RecordByDeviceId != null && t4RecordByDeviceId.getK3Id() > 0) {
                this.llConsumableContentTwo.setVisibility(0);
                return;
            } else {
                this.llConsumableContentTwo.setVisibility(8);
                return;
            }
        }
        if (t4RecordByDeviceId != null && t4RecordByDeviceId.getK3Id() > 0) {
            this.llConsumableContentTwo.setVisibility(0);
            this.ivRedPoint.setVisibility((DeviceCenterUtils.isT4NeedOtaById(homeDeviceData.getId()) || DeviceCenterUtils.isK3NeedOtaById((long) t4RecordByDeviceId.getK3Id())) ? 0 : 8);
        } else {
            this.llConsumableContentTwo.setVisibility(8);
            this.ivRedPoint.setVisibility(DeviceCenterUtils.isT4NeedOtaById(homeDeviceData.getId()) ? 0 : 8);
        }
    }
}

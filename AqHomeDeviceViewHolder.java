package com.petkit.android.activities.home.adapter.holder;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.jess.arms.utils.ArmsUtils;
import com.petkit.android.activities.common.utils.DeviceCenterUtils;
import com.petkit.android.activities.home.adapter.model.HomeDeviceData;
import com.petkit.android.activities.petkitBleDevice.aq.mode.AqRecord;
import com.petkit.android.activities.petkitBleDevice.aq.utils.AqUtils;
import com.petkit.android.activities.petkitBleDevice.ble.PetkitBleDeviceManager;
import com.petkit.android.utils.LogcatStorageHelper;
import com.petkit.android.utils.PetkitLog;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.oversea.R;

/* JADX INFO: loaded from: classes4.dex */
public class AqHomeDeviceViewHolder extends BaseHomeDeviceViewHolder {
    public final Context context;
    public FrameLayout flState;
    public ImageView ivStreamIcon;
    public TextView tvMode;
    public TextView tvTimePrompt;

    public AqHomeDeviceViewHolder(@NonNull View view) {
        super(view);
        this.context = view.getContext();
        this.tvMode = (TextView) view.findViewById(R.id.tv_mode);
        this.tvTimePrompt = (TextView) view.findViewById(R.id.tv_time_prompt);
        this.ivStreamIcon = (ImageView) view.findViewById(R.id.iv_stream_icon);
        this.flState = (FrameLayout) view.findViewById(R.id.fl_state_bg);
    }

    @Override // com.petkit.android.activities.home.adapter.holder.BaseHomeDeviceViewHolder
    public void updateData(HomeDeviceData homeDeviceData) {
        this.flState.setBackgroundResource(R.drawable.solid_home_device_state_tran);
        this.tvDeviceState.setTextColor(this.context.getResources().getColor(R.color.home_chart_text_color));
        this.ivRedPoint.setVisibility(DeviceCenterUtils.isAqNeedOtaById(homeDeviceData.getId()) ? 0 : 8);
        AqRecord aqRecordByDeviceId = AqUtils.getAqRecordByDeviceId(homeDeviceData.getId());
        this.tvDeviceName.setText(homeDeviceData.getData().getName());
        this.tvDeviceState.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
        if (homeDeviceData.getData().getTypeCode() == 1) {
            this.ivDeviceIcon.setImageResource(R.drawable.dev_home_aq);
        } else if (homeDeviceData.getData().getTypeCode() == 2) {
            this.ivDeviceIcon.setImageResource(R.drawable.dev_home_aq1s);
        } else if (homeDeviceData.getData().getTypeCode() == 3) {
            this.ivDeviceIcon.setImageResource(R.drawable.dev_home_aq2);
        }
        if (aqRecordByDeviceId != null && PetkitBleDeviceManager.getInstance().checkDeviceState(aqRecordByDeviceId.getMac(), 10)) {
            if (homeDeviceData.getData().getErrState() == 0 || homeDeviceData.getData().getErrState() == 8) {
                if (homeDeviceData.getData().getPower() != 0) {
                    this.tvDeviceState.setText("");
                } else if (homeDeviceData.getData().getTypeCode() == 1) {
                    this.tvDeviceState.setText(R.string.Turn_off);
                } else if (homeDeviceData.getData().getTypeCode() == 2 || homeDeviceData.getData().getTypeCode() == 3) {
                    this.tvDeviceState.setText(R.string.AQ1S_already_turn_off_light);
                }
            } else if (homeDeviceData.getData().getPower() == 0) {
                if (homeDeviceData.getData().getTypeCode() == 1) {
                    this.tvDeviceState.setText(R.string.Turn_off);
                } else if (homeDeviceData.getData().getTypeCode() == 2 || homeDeviceData.getData().getTypeCode() == 3) {
                    this.tvDeviceState.setText(R.string.AQ1S_already_turn_off_light);
                }
            } else {
                this.tvDeviceState.setText(R.string.State_error);
                this.tvDeviceState.setCompoundDrawablesWithIntrinsicBounds(this.context.getResources().getDrawable(R.drawable.home_item_warn_white_icon), (Drawable) null, (Drawable) null, (Drawable) null);
                this.tvDeviceState.setCompoundDrawablePadding(ArmsUtils.dip2px(this.context, 6.0f));
                this.flState.setBackgroundResource(R.drawable.solid_home_device_state_red);
                this.tvDeviceState.setTextColor(this.context.getResources().getColor(R.color.white));
            }
        } else {
            this.tvDeviceState.setText(R.string.Disconnect);
        }
        try {
            int mode = homeDeviceData.getData().getMode();
            if (mode != 1) {
                if (mode != 2) {
                    if (mode != 3) {
                        return;
                    }
                    this.tvMode.setText(this.context.getString(R.string.Aq_stream_light) + this.context.getString(R.string.Video_mode));
                    this.ivStreamIcon.setVisibility(8);
                    this.tvTimePrompt.setVisibility(8);
                    this.tvTimePrompt.setText("");
                    return;
                }
                this.tvMode.setText(this.context.getString(R.string.Aq_color_light) + this.context.getString(R.string.Video_mode));
                this.ivStreamIcon.setVisibility(8);
                this.tvTimePrompt.setText(this.context.getString(R.string.Brightness_value, homeDeviceData.getData().getSettings().getColorfulMode().getLight() + "%"));
                this.tvTimePrompt.setVisibility(0);
                return;
            }
            if (homeDeviceData.getData().getSettings().getWhiteMode().getChasingLight() == 1) {
                this.tvMode.setText(R.string.Aq_spotlight_mode);
                this.ivStreamIcon.setVisibility(0);
                this.tvTimePrompt.setText("");
                if ("vi_VN".equalsIgnoreCase(UserInforUtils.getLanguageLocaleString(this.context)) || "es_ES".equalsIgnoreCase(UserInforUtils.getLanguageLocaleString(this.context)) || "ru_RU".equalsIgnoreCase(UserInforUtils.getLanguageLocaleString(this.context)) || "pt_PT".equalsIgnoreCase(UserInforUtils.getLanguageLocaleString(this.context)) || "fr_FR".equalsIgnoreCase(UserInforUtils.getLanguageLocaleString(this.context))) {
                    this.tvMode.setTextSize(1, 14.0f);
                } else {
                    this.tvMode.setTextSize(1, 22.0f);
                }
                this.tvTimePrompt.setVisibility(8);
                return;
            }
            this.ivStreamIcon.setVisibility(8);
            this.tvTimePrompt.setVisibility(0);
            this.tvTimePrompt.setText(this.context.getString(R.string.Brightness_value, homeDeviceData.getData().getSettings().getWhiteMode().getLight() + "%"));
            if (homeDeviceData.getData().getTypeCode() == 1) {
                this.tvMode.setText(this.context.getString(R.string.Aq_white_light) + this.context.getString(R.string.Video_mode));
                return;
            }
            if (homeDeviceData.getData().getTypeCode() == 2) {
                this.tvMode.setText(this.context.getString(R.string.AQ1S_nature_light) + this.context.getString(R.string.Video_mode));
                return;
            }
            if (homeDeviceData.getData().getTypeCode() == 3) {
                this.tvMode.setText(this.context.getString(R.string.AQ1S_nature_light) + this.context.getString(R.string.Video_mode));
            }
        } catch (Exception e) {
            PetkitLog.d("AqHomeDeviceViewHolder: " + e.getMessage());
            LogcatStorageHelper.addLog("AqHomeDeviceViewHolder: " + e.getMessage());
        }
    }
}

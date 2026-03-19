package com.petkit.android.activities.home.adapter.holder;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.jess.arms.utils.ArmsUtils;
import com.petkit.android.activities.common.utils.DeviceCenterUtils;
import com.petkit.android.activities.home.adapter.model.HomeDeviceData;
import com.petkit.android.activities.petkitBleDevice.ble.PetkitBleDeviceManager;
import com.petkit.android.activities.petkitBleDevice.k3.mode.K3Record;
import com.petkit.android.activities.petkitBleDevice.k3.utils.K3Utils;
import com.petkit.android.activities.petkitBleDevice.utils.TextUtil;
import com.petkit.android.utils.CommonUtils;
import com.petkit.oversea.R;

/* JADX INFO: loaded from: classes4.dex */
public class K3HomeDeviceViewHolder extends BaseHomeDeviceViewHolder {
    public final Context context;
    public FrameLayout flState;
    public TextView tvTime;

    public K3HomeDeviceViewHolder(@NonNull View view) {
        super(view);
        this.context = view.getContext();
        this.tvTime = (TextView) view.findViewById(R.id.tv_time);
        this.flState = (FrameLayout) view.findViewById(R.id.fl_state_bg);
    }

    @Override // com.petkit.android.activities.home.adapter.holder.BaseHomeDeviceViewHolder
    public void updateData(HomeDeviceData homeDeviceData) {
        Context context;
        int i;
        this.flState.setBackgroundResource(R.drawable.solid_home_device_state_tran);
        this.tvDeviceState.setTextColor(this.context.getResources().getColor(R.color.home_chart_text_color));
        this.ivRedPoint.setVisibility(DeviceCenterUtils.isK3NeedOtaById(homeDeviceData.getId()) ? 0 : 8);
        K3Record k3RecordByDeviceId = K3Utils.getK3RecordByDeviceId(homeDeviceData.getId());
        if (homeDeviceData.getData().getDeviceShared() != null) {
            this.ivRedPoint.setVisibility(8);
            String name = homeDeviceData.getData().getName();
            if (!TextUtils.isEmpty(name)) {
                this.tvDeviceName.setText(homeDeviceData.getData().getDeviceShared().getOwnerNick() + "-" + name);
            } else {
                this.tvDeviceName.setText(homeDeviceData.getData().getDeviceShared().getOwnerNick() + "-" + this.context.getString(R.string.K3_name_default));
            }
        } else {
            String name2 = homeDeviceData.getData().getName();
            if (!TextUtils.isEmpty(name2)) {
                this.tvDeviceName.setText(name2);
            } else {
                this.tvDeviceName.setText(this.context.getString(R.string.K3_name_default));
            }
        }
        if (homeDeviceData.getData().getSprayCount() > 1) {
            context = this.context;
            i = R.string.Unit_times;
        } else {
            context = this.context;
            i = R.string.Unit_time;
        }
        String string = context.getString(i);
        String str = "" + homeDeviceData.getData().getSprayCount();
        String str2 = str + string;
        this.tvTime.setText(TextUtil.getInstance().makePartStringSpannableInTotalString(new SpannableString(str2), str2, str, -1, 28, true));
        this.tvDeviceState.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
        this.tvDeviceState.setCompoundDrawablePadding(ArmsUtils.dip2px(this.context, 6.0f));
        if (k3RecordByDeviceId != null) {
            if (PetkitBleDeviceManager.getInstance().checkDeviceState(k3RecordByDeviceId.getMac(), 16)) {
                if (homeDeviceData.getData().getRefreshing() == 1) {
                    this.tvDeviceState.setText(this.context.getResources().getString(R.string.Deodorizing_prompt));
                    return;
                }
                if (homeDeviceData.getData().getBattery() < 20) {
                    this.tvDeviceState.setText(this.context.getResources().getString(R.string.Battery_lack));
                    this.tvDeviceState.setCompoundDrawablesWithIntrinsicBounds(this.context.getResources().getDrawable(R.drawable.home_item_warn_white_icon), (Drawable) null, (Drawable) null, (Drawable) null);
                    this.tvDeviceState.setPadding(ArmsUtils.dip2px(this.context, 5.0f), 0, ArmsUtils.dip2px(this.context, 5.0f), 0);
                    this.flState.setBackgroundResource(R.drawable.solid_home_device_state_red);
                    this.tvDeviceState.setTextColor(CommonUtils.getColorById(R.color.white));
                    return;
                }
                if (homeDeviceData.getData().getLiquidLack() == 1) {
                    this.tvDeviceState.setText(this.context.getResources().getString(R.string.K3_Liquid_lack_title));
                    this.tvDeviceState.setCompoundDrawablesWithIntrinsicBounds(this.context.getResources().getDrawable(R.drawable.home_item_warn_white_icon), (Drawable) null, (Drawable) null, (Drawable) null);
                    this.tvDeviceState.setPadding(ArmsUtils.dip2px(this.context, 5.0f), 0, ArmsUtils.dip2px(this.context, 5.0f), 0);
                    this.flState.setBackgroundResource(R.drawable.solid_home_device_state_red);
                    this.tvDeviceState.setTextColor(CommonUtils.getColorById(R.color.white));
                    return;
                }
                if (homeDeviceData.getData().getLiquidLack() == 2) {
                    this.tvDeviceState.setText(this.context.getResources().getString(R.string.K3_Liquid_fewer_title));
                    this.tvDeviceState.setCompoundDrawablesWithIntrinsicBounds(this.context.getResources().getDrawable(R.drawable.home_item_warn_white_icon), (Drawable) null, (Drawable) null, (Drawable) null);
                    this.tvDeviceState.setPadding(ArmsUtils.dip2px(this.context, 5.0f), 0, ArmsUtils.dip2px(this.context, 5.0f), 0);
                    this.flState.setBackgroundResource(R.drawable.solid_home_device_state_red);
                    this.tvDeviceState.setTextColor(CommonUtils.getColorById(R.color.white));
                    return;
                }
                this.tvDeviceState.setText(this.context.getResources().getString(R.string.Battery_left_format, homeDeviceData.getData().getBattery() + "%"));
                return;
            }
            this.tvDeviceState.setText(R.string.Disconnect);
            return;
        }
        this.tvDeviceState.setText(R.string.Disconnect);
    }
}

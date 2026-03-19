package com.petkit.android.activities.home.adapter.holder;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import com.petkit.android.activities.base.adapter.helper.IRecyclerItemClickListener;
import com.petkit.android.activities.common.utils.DeviceCenterUtils;
import com.petkit.android.activities.home.adapter.model.DeviceCard;
import com.petkit.android.activities.petkitBleDevice.utils.BleDeviceUtils;
import com.petkit.android.activities.petkitBleDevice.utils.DeviceUpgradeUtils;
import com.petkit.android.utils.CommonUtils;
import com.petkit.oversea.R;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes4.dex */
public class K2DeviceViewHolder extends BaseDeviceViewHolder {
    private Activity activity;

    public K2DeviceViewHolder(Activity activity, View view, IRecyclerItemClickListener iRecyclerItemClickListener) {
        super(activity, view, iRecyclerItemClickListener);
        this.activity = activity;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.petkit.android.activities.home.adapter.holder.BaseDeviceViewHolder, com.petkit.android.activities.base.adapter.BaseTouchViewHolder
    public void updateData(DeviceCard deviceCard) {
        int overall;
        super.updateData(deviceCard);
        this.noticeFlag.setVisibility(DeviceCenterUtils.isD2NeedOtaById(deviceCard.getDeviceData().getData().getId()) ? 0 : 8);
        if (BleDeviceUtils.getK2RecordByDeviceId(deviceCard.getDeviceData().getData().getId()) != null || deviceCard.getDeviceData().getData().getDeviceShared() != null) {
            if (deviceCard.getDeviceData().getData().getDeviceShared() != null) {
                this.desc.setText(this.mActivity.getString(R.string.Mate_share_owner_format, deviceCard.getDeviceData().getData().getDeviceShared().getOwnerNick()));
                overall = deviceCard.getDeviceData().getData().getStatus().getOverall();
                this.noticeFlag.setVisibility(8);
                String name = deviceCard.getDeviceData().getData().getName();
                if (!TextUtils.isEmpty(name)) {
                    this.name.setText(name);
                } else {
                    this.name.setText(this.activity.getString(R.string.K2_name_default));
                }
            } else {
                this.desc.setText(deviceCard.getDeviceData().getData().getDesc());
                overall = deviceCard.getDeviceData().getData().getStatus().getOverall();
                String name2 = deviceCard.getDeviceData().getData().getName();
                if (!TextUtils.isEmpty(name2)) {
                    this.name.setText(name2);
                } else {
                    this.name.setText(this.activity.getString(R.string.K2_name_default));
                }
            }
            if (overall != 1) {
                if (overall == 2) {
                    this.state.setText(R.string.Offline);
                    this.state.setTextColor(CommonUtils.getColorById(R.color.gray));
                } else if (overall == 3) {
                    this.state.setText(R.string.Mate_ota);
                    this.state.setTextColor(CommonUtils.getColorById(R.color.device_error));
                } else if (overall == 4) {
                    this.state.setText(R.string.State_error);
                    this.state.setTextColor(CommonUtils.getColorById(R.color.device_error));
                }
            } else if (deviceCard.getDeviceData().getData().getStatus().getLiquid() < 10) {
                this.state.setTextColor(CommonUtils.getColorById(R.color.walk_text));
                this.state.setText(R.string.Lack_liquid);
            } else {
                this.state.setTextColor(CommonUtils.getColorById(R.color.device_normal));
                if (deviceCard.getDeviceData().getData().getStatus().getPower() == 1) {
                    this.state.setText(R.string.State_working);
                } else if (deviceCard.getDeviceData().getData().getStatus().getPower() == 2) {
                    this.state.setText(R.string.Cozy_await);
                } else {
                    this.state.setText(R.string.Power_off);
                }
            }
            if (deviceCard.getDeviceData().getData().getStatus().getOverall() == 2) {
                this.icon.setImageResource(R.drawable.k2_home_item_icon);
                this.name.setTextColor(CommonUtils.getColorById(R.color.device_state_offline));
                this.desc.setTextColor(CommonUtils.getColorById(R.color.gray));
                return;
            } else {
                this.icon.setImageResource(R.drawable.k2_home_item_icon);
                this.name.setTextColor(CommonUtils.getColorById(R.color.black));
                this.desc.setTextColor(CommonUtils.getColorById(R.color.gray));
                return;
            }
        }
        DeviceCenterUtils.setK2RecordUpdate(false);
        ArrayList arrayList = new ArrayList();
        arrayList.add(8);
        DeviceUpgradeUtils.updateDeviceInfo(this.activity, arrayList);
    }
}

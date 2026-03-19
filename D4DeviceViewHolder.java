package com.petkit.android.activities.home.adapter.holder;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import com.petkit.android.activities.base.adapter.helper.IRecyclerItemClickListener;
import com.petkit.android.activities.common.utils.DeviceCenterUtils;
import com.petkit.android.activities.home.adapter.model.DeviceCard;
import com.petkit.android.activities.petkitBleDevice.d4.utils.D4Utils;
import com.petkit.android.activities.petkitBleDevice.utils.DeviceUpgradeUtils;
import com.petkit.android.model.DeviceRelation;
import com.petkit.android.utils.CommonUtils;
import com.petkit.oversea.R;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes4.dex */
public class D4DeviceViewHolder extends BaseDeviceViewHolder {
    private Activity activity;

    public D4DeviceViewHolder(Activity activity, View view, IRecyclerItemClickListener iRecyclerItemClickListener) {
        super(activity, view, iRecyclerItemClickListener);
        this.activity = activity;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.petkit.android.activities.home.adapter.holder.BaseDeviceViewHolder, com.petkit.android.activities.base.adapter.BaseTouchViewHolder
    public void updateData(DeviceCard deviceCard) {
        int overall;
        super.updateData(deviceCard);
        this.noticeFlag.setVisibility(DeviceCenterUtils.isD4NeedOtaById(deviceCard.getDeviceData().getData().getId()) ? 0 : 8);
        if (D4Utils.getD4RecordByDeviceId(deviceCard.getDeviceData().getData().getId()) != null || deviceCard.getDeviceData().getData().getDeviceShared() != null) {
            if (deviceCard.getDeviceData().getData().getDeviceShared() != null) {
                this.desc.setText(this.mActivity.getString(R.string.Mate_share_owner_format, deviceCard.getDeviceData().getData().getDeviceShared().getOwnerNick()));
                overall = deviceCard.getDeviceData().getData().getState();
                this.noticeFlag.setVisibility(8);
                String name = deviceCard.getDeviceData().getData().getName();
                deviceCard.getDeviceData().getData().getDeviceShared().getPets();
                if (!TextUtils.isEmpty(name)) {
                    this.name.setText(name);
                } else {
                    this.name.setText(this.activity.getString(R.string.Device_d4_name));
                }
            } else {
                this.desc.setText(deviceCard.getDeviceData().getData().getDesc());
                overall = deviceCard.getDeviceData().getData().getStatus().getOverall();
                String name2 = deviceCard.getDeviceData().getData().getName();
                DeviceRelation relation = deviceCard.getDeviceData().getData().getRelation();
                if (relation != null) {
                    relation.getPetIds();
                }
                if (!TextUtils.isEmpty(name2)) {
                    this.name.setText(name2);
                } else {
                    this.name.setText(this.activity.getString(R.string.Device_d4_name));
                }
            }
            if (overall != 1) {
                if (overall == 3) {
                    this.state.setText(R.string.Feeding);
                    this.state.setTextColor(CommonUtils.getColorById(R.color.device_normal));
                } else if (overall == 4) {
                    this.state.setText(R.string.Mate_ota);
                    this.state.setTextColor(CommonUtils.getColorById(R.color.device_error));
                } else if (overall == 5) {
                    this.state.setText(R.string.State_error);
                    this.state.setTextColor(CommonUtils.getColorById(R.color.device_error));
                } else if (overall == 6) {
                    if (deviceCard.getDeviceData().getData().getStatus().getFood() == 0) {
                        this.state.setText(R.string.Feeder_food_empty);
                        this.state.setTextColor(CommonUtils.getColorById(R.color.device_error));
                    } else {
                        this.state.setText(R.string.D2_battery_mode);
                        this.state.setTextColor(CommonUtils.getColorById(R.color.device_normal));
                    }
                } else {
                    this.state.setText(R.string.Offline);
                    this.state.setTextColor(CommonUtils.getColorById(R.color.gray));
                }
            } else if (deviceCard.getDeviceData().getData().getStatus().getFood() == 0) {
                this.state.setText(R.string.Feeder_food_empty);
                this.state.setTextColor(CommonUtils.getColorById(R.color.device_error));
            } else {
                this.state.setText(R.string.Online);
                this.state.setTextColor(CommonUtils.getColorById(R.color.device_normal));
            }
            if (deviceCard.getDeviceData().getData().getStatus().getOverall() == 2) {
                this.icon.setImageResource(R.drawable.home_dev_d4);
                this.name.setTextColor(CommonUtils.getColorById(R.color.device_state_offline));
                this.desc.setTextColor(CommonUtils.getColorById(R.color.gray));
                return;
            } else {
                this.icon.setImageResource(R.drawable.home_dev_d4);
                this.name.setTextColor(CommonUtils.getColorById(R.color.black));
                this.desc.setTextColor(CommonUtils.getColorById(R.color.gray));
                return;
            }
        }
        DeviceCenterUtils.setD4RecordUpdate(false);
        ArrayList arrayList = new ArrayList();
        arrayList.add(11);
        DeviceUpgradeUtils.updateDeviceInfo(this.activity, arrayList);
    }
}

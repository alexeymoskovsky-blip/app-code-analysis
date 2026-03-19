package com.petkit.android.activities.home.adapter.holder;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import com.petkit.android.activities.base.adapter.helper.IRecyclerItemClickListener;
import com.petkit.android.activities.common.utils.DeviceCenterUtils;
import com.petkit.android.activities.home.adapter.model.DeviceCard;
import com.petkit.android.activities.petkitBleDevice.d3.mode.D3Record;
import com.petkit.android.activities.petkitBleDevice.d3.utils.D3Utils;
import com.petkit.android.activities.petkitBleDevice.utils.DeviceUpgradeUtils;
import com.petkit.android.model.DeviceRelation;
import com.petkit.android.utils.CommonUtils;
import com.petkit.oversea.R;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes4.dex */
public class D3DeviceViewHolder extends BaseDeviceViewHolder {
    private Activity activity;

    public D3DeviceViewHolder(Activity activity, View view, IRecyclerItemClickListener iRecyclerItemClickListener) {
        super(activity, view, iRecyclerItemClickListener);
        this.activity = activity;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.petkit.android.activities.home.adapter.holder.BaseDeviceViewHolder, com.petkit.android.activities.base.adapter.BaseTouchViewHolder
    public void updateData(DeviceCard deviceCard) {
        int overall;
        super.updateData(deviceCard);
        this.noticeFlag.setVisibility(DeviceCenterUtils.isD3NeedOtaById(deviceCard.getDeviceData().getData().getId()) ? 0 : 8);
        D3Record d3RecordByDeviceId = D3Utils.getD3RecordByDeviceId(deviceCard.getDeviceData().getData().getId());
        if (d3RecordByDeviceId != null || deviceCard.getDeviceData().getData().getDeviceShared() != null) {
            if (deviceCard.getDeviceData().getData().getDeviceShared() != null) {
                this.desc.setText(this.mActivity.getString(R.string.Mate_share_owner_format, deviceCard.getDeviceData().getData().getDeviceShared().getOwnerNick()));
                overall = deviceCard.getDeviceData().getData().getState();
                this.noticeFlag.setVisibility(8);
                String name = deviceCard.getDeviceData().getData().getName();
                deviceCard.getDeviceData().getData().getDeviceShared().getPets();
                if (!TextUtils.isEmpty(name)) {
                    this.name.setText(name);
                } else {
                    this.name.setText(this.activity.getString(R.string.Device_d3_name));
                }
            } else {
                this.desc.setText(deviceCard.getDeviceData().getData().getDesc());
                overall = d3RecordByDeviceId.getState().getOverall();
                String name2 = deviceCard.getDeviceData().getData().getName();
                DeviceRelation relation = deviceCard.getDeviceData().getData().getRelation();
                if (relation != null) {
                    relation.getPetIds();
                }
                if (!TextUtils.isEmpty(name2)) {
                    this.name.setText(name2);
                } else {
                    this.name.setText(this.activity.getString(R.string.Device_d3_name));
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
                    if (deviceCard.getDeviceData().getData().getStatus().getFood() == 0 || deviceCard.getDeviceData().getData().getStatus().getFood() == 1) {
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
            } else if (deviceCard.getDeviceData().getData().getStatus().getFood() == 0 || deviceCard.getDeviceData().getData().getStatus().getFood() == 1) {
                this.state.setText(R.string.Feeder_food_empty);
                this.state.setTextColor(CommonUtils.getColorById(R.color.device_error));
            } else {
                this.state.setText(R.string.Online);
                this.state.setTextColor(CommonUtils.getColorById(R.color.device_normal));
            }
            if (deviceCard.getDeviceData().getData().getState() == 2) {
                this.icon.setImageResource(R.drawable.home_dev_d3);
                this.name.setTextColor(CommonUtils.getColorById(R.color.device_state_offline));
                this.desc.setTextColor(CommonUtils.getColorById(R.color.gray));
                return;
            } else {
                this.icon.setImageResource(R.drawable.home_dev_d3);
                this.name.setTextColor(CommonUtils.getColorById(R.color.black));
                this.desc.setTextColor(CommonUtils.getColorById(R.color.gray));
                return;
            }
        }
        DeviceCenterUtils.setD3RecordUpdate(false);
        ArrayList arrayList = new ArrayList();
        arrayList.add(9);
        DeviceUpgradeUtils.updateDeviceInfo(this.activity, arrayList);
    }
}

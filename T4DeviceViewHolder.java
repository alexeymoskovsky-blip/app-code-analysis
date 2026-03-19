package com.petkit.android.activities.home.adapter.holder;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import com.petkit.android.activities.base.adapter.helper.IRecyclerItemClickListener;
import com.petkit.android.activities.common.utils.DeviceCenterUtils;
import com.petkit.android.activities.home.adapter.model.DeviceCard;
import com.petkit.android.activities.petkitBleDevice.t4.mode.T4Record;
import com.petkit.android.activities.petkitBleDevice.t4.utils.T4Utils;
import com.petkit.android.activities.petkitBleDevice.utils.DeviceUpgradeUtils;
import com.petkit.android.utils.CommonUtils;
import com.petkit.oversea.R;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes4.dex */
public class T4DeviceViewHolder extends BaseDeviceViewHolder {
    private Activity activity;

    public T4DeviceViewHolder(Activity activity, View view, IRecyclerItemClickListener iRecyclerItemClickListener) {
        super(activity, view, iRecyclerItemClickListener);
        this.activity = activity;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.petkit.android.activities.home.adapter.holder.BaseDeviceViewHolder, com.petkit.android.activities.base.adapter.BaseTouchViewHolder
    public void updateData(DeviceCard deviceCard) {
        int overall;
        super.updateData(deviceCard);
        T4Record t4RecordByDeviceId = T4Utils.getT4RecordByDeviceId(deviceCard.getDeviceData().getData().getId());
        if (t4RecordByDeviceId != null && t4RecordByDeviceId.getK3Id() > 0) {
            this.noticeFlag.setVisibility((DeviceCenterUtils.isT4NeedOtaById(deviceCard.getDeviceData().getData().getId()) || DeviceCenterUtils.isK3NeedOtaById((long) t4RecordByDeviceId.getK3Id())) ? 0 : 8);
        } else {
            this.noticeFlag.setVisibility(DeviceCenterUtils.isT4NeedOtaById(deviceCard.getDeviceData().getData().getId()) ? 0 : 8);
        }
        if (t4RecordByDeviceId != null || deviceCard.getDeviceData().getData().getDeviceShared() != null) {
            if (deviceCard.getDeviceData().getData().getDeviceShared() != null) {
                this.desc.setText(this.mActivity.getString(R.string.Mate_share_owner_format, deviceCard.getDeviceData().getData().getDeviceShared().getOwnerNick()));
                overall = deviceCard.getDeviceData().getData().getStatus() != null ? deviceCard.getDeviceData().getData().getStatus().getOverall() : 2;
                this.noticeFlag.setVisibility(8);
                String name = deviceCard.getDeviceData().getData().getName();
                if (!TextUtils.isEmpty(name)) {
                    this.name.setText(name);
                } else {
                    this.name.setText(this.activity.getString(R.string.T4_name_default));
                }
            } else {
                this.desc.setText(deviceCard.getDeviceData().getData().getDesc());
                overall = deviceCard.getDeviceData().getData().getStatus() != null ? deviceCard.getDeviceData().getData().getStatus().getOverall() : 2;
                String name2 = deviceCard.getDeviceData().getData().getName();
                if (!TextUtils.isEmpty(name2)) {
                    this.name.setText(name2);
                } else {
                    this.name.setText(this.activity.getString(R.string.T4_name_default));
                }
            }
            if (overall == 2) {
                this.state.setText(R.string.Offline);
                this.state.setTextColor(CommonUtils.getColorById(R.color.gray));
            } else if (overall == 3) {
                this.state.setText(R.string.Mate_ota);
                this.state.setTextColor(CommonUtils.getColorById(R.color.device_error));
            } else if (overall == 4) {
                this.state.setText(R.string.State_error);
                this.state.setTextColor(CommonUtils.getColorById(R.color.device_error));
            } else {
                this.state.setTextColor(CommonUtils.getColorById(R.color.device_normal));
                if (deviceCard.getDeviceData().getData().getStatus().getPower() == 0) {
                    this.state.setText(R.string.Power_off);
                } else if (deviceCard.getDeviceData().getData().getStatus().getWorkState() == null) {
                    this.state.setText(R.string.Online);
                } else {
                    this.state.setText(R.string.State_working);
                }
            }
            if (deviceCard.getDeviceData().getData().getState() == 2) {
                this.icon.setImageResource(R.drawable.t4_home_item_icon);
                this.name.setTextColor(CommonUtils.getColorById(R.color.device_state_offline));
                this.desc.setTextColor(CommonUtils.getColorById(R.color.gray));
                return;
            } else {
                this.icon.setImageResource(R.drawable.t4_home_item_icon);
                this.name.setTextColor(CommonUtils.getColorById(R.color.black));
                this.desc.setTextColor(CommonUtils.getColorById(R.color.gray));
                return;
            }
        }
        DeviceCenterUtils.setT4RecordUpdate(false);
        ArrayList arrayList = new ArrayList();
        arrayList.add(15);
        DeviceUpgradeUtils.updateDeviceInfo(this.activity, arrayList);
    }
}

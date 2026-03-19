package com.petkit.android.activities.home.adapter.holder;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import com.petkit.android.activities.base.adapter.helper.IRecyclerItemClickListener;
import com.petkit.android.activities.common.utils.DeviceCenterUtils;
import com.petkit.android.activities.feeder.Utils.FeederUtils;
import com.petkit.android.activities.feeder.model.FeederRecord;
import com.petkit.android.activities.home.adapter.model.DeviceCard;
import com.petkit.android.activities.petkitBleDevice.utils.DeviceUpgradeUtils;
import com.petkit.android.model.DeviceRelation;
import com.petkit.android.model.Pet;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.oversea.R;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class FeederDeviceViewHolder extends BaseDeviceViewHolder {
    private Activity activity;

    public FeederDeviceViewHolder(Activity activity, View view, IRecyclerItemClickListener iRecyclerItemClickListener) {
        super(activity, view, iRecyclerItemClickListener);
        this.activity = activity;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.petkit.android.activities.home.adapter.holder.BaseDeviceViewHolder, com.petkit.android.activities.base.adapter.BaseTouchViewHolder
    public void updateData(DeviceCard deviceCard) {
        int state;
        super.updateData(deviceCard);
        this.noticeFlag.setVisibility(DeviceCenterUtils.isFeederNeedOtaById(deviceCard.getDeviceData().getData().getId()) ? 0 : 8);
        FeederRecord feederRecordByDeviceId = FeederUtils.getFeederRecordByDeviceId(deviceCard.getDeviceData().getData().getId());
        if (feederRecordByDeviceId != null || deviceCard.getDeviceData().getData().getDeviceShared() != null) {
            if (deviceCard.getDeviceData().getData().getDeviceShared() != null) {
                this.desc.setText(this.mActivity.getString(R.string.Mate_share_owner_format, deviceCard.getDeviceData().getData().getDeviceShared().getOwnerNick()));
                state = deviceCard.getDeviceData().getData().getState();
                String name = deviceCard.getDeviceData().getData().getName();
                List<Pet> pets = deviceCard.getDeviceData().getData().getDeviceShared().getPets();
                if (!TextUtils.isEmpty(name)) {
                    this.name.setText(name);
                } else if (pets != null && pets.size() > 0 && !TextUtils.isEmpty(pets.get(0).getName())) {
                    this.name.setText(this.activity.getString(R.string.Feeder_name_format, pets.get(0).getName()));
                } else {
                    this.name.setText(this.activity.getString(R.string.Device_d1_name));
                }
            } else {
                this.desc.setText(deviceCard.getDeviceData().getData().getDesc());
                state = feederRecordByDeviceId.getState();
                this.name.setText(deviceCard.getDeviceData().getData().getName());
                String name2 = deviceCard.getDeviceData().getData().getName();
                DeviceRelation relation = deviceCard.getDeviceData().getData().getRelation();
                List<String> petIds = relation != null ? relation.getPetIds() : null;
                if (!TextUtils.isEmpty(name2)) {
                    this.name.setText(name2);
                } else if (petIds != null && petIds.size() > 0 && UserInforUtils.getPetById(petIds.get(0)) != null) {
                    this.name.setText(this.activity.getString(R.string.Feeder_name_format, UserInforUtils.getPetById(petIds.get(0)).getName()));
                } else {
                    this.name.setText(this.activity.getString(R.string.Device_d1_name));
                }
            }
            if (state != 1) {
                if (state == 3) {
                    this.state.setText(R.string.Feeding);
                    this.state.setTextColor(CommonUtils.getColorById(R.color.device_normal));
                } else if (state == 4) {
                    this.state.setText(R.string.Mate_ota);
                    this.state.setTextColor(CommonUtils.getColorById(R.color.device_error));
                } else if (state == 5) {
                    this.state.setText(R.string.State_error);
                    this.state.setTextColor(CommonUtils.getColorById(R.color.device_error));
                } else {
                    this.state.setText(R.string.Offline);
                    this.state.setTextColor(CommonUtils.getColorById(R.color.gray));
                }
            } else if (deviceCard.getDeviceData().getData().getStatus().getPercent() <= 5) {
                this.state.setText(R.string.Feeder_food_empty);
                this.state.setTextColor(CommonUtils.getColorById(R.color.device_error));
            } else {
                this.state.setText(R.string.Online);
                this.state.setTextColor(CommonUtils.getColorById(R.color.device_normal));
            }
            if (deviceCard.getDeviceData().getData().getState() == 2) {
                this.icon.setImageResource(R.drawable.home_dev_feeder);
                this.name.setTextColor(CommonUtils.getColorById(R.color.device_state_offline));
                this.desc.setTextColor(CommonUtils.getColorById(R.color.gray));
                return;
            } else {
                this.icon.setImageResource(R.drawable.home_dev_feeder);
                this.name.setTextColor(CommonUtils.getColorById(R.color.black));
                this.desc.setTextColor(CommonUtils.getColorById(R.color.gray));
                return;
            }
        }
        DeviceCenterUtils.setFeederRecordUpdated(false);
        ArrayList arrayList = new ArrayList();
        arrayList.add(4);
        DeviceUpgradeUtils.updateDeviceInfo(this.activity, arrayList);
    }
}

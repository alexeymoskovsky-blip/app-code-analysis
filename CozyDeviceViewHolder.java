package com.petkit.android.activities.home.adapter.holder;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import com.jess.arms.utils.ArmsUtils;
import com.petkit.android.activities.base.adapter.helper.IRecyclerItemClickListener;
import com.petkit.android.activities.common.utils.DeviceCenterUtils;
import com.petkit.android.activities.cozy.mode.CozyRecord;
import com.petkit.android.activities.cozy.utils.CozyUtils;
import com.petkit.android.activities.home.adapter.model.DeviceCard;
import com.petkit.android.activities.petkitBleDevice.utils.DeviceUpgradeUtils;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.oversea.R;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes4.dex */
public class CozyDeviceViewHolder extends BaseDeviceViewHolder {
    private Activity activity;

    public CozyDeviceViewHolder(Activity activity, View view, IRecyclerItemClickListener iRecyclerItemClickListener) {
        super(activity, view, iRecyclerItemClickListener);
        this.activity = activity;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.petkit.android.activities.home.adapter.holder.BaseDeviceViewHolder, com.petkit.android.activities.base.adapter.BaseTouchViewHolder
    public void updateData(DeviceCard deviceCard) {
        super.updateData(deviceCard);
        CozyRecord cozyRecordByDeviceId = CozyUtils.getCozyRecordByDeviceId(deviceCard.getDeviceData().getData().getId());
        this.noticeFlag.setVisibility(DeviceCenterUtils.isCozyNeedOtaById(deviceCard.getDeviceData().getData().getId()) ? 0 : 8);
        this.name.setText(deviceCard.getDeviceData().getData().getName());
        if (cozyRecordByDeviceId == null && deviceCard.getDeviceData().getData().getShared() == null) {
            DeviceCenterUtils.setCozyRecordUpdated(false);
            ArrayList arrayList = new ArrayList();
            arrayList.add(5);
            DeviceUpgradeUtils.updateDeviceInfo(this.activity, arrayList);
        } else if (cozyRecordByDeviceId == null) {
            cozyRecordByDeviceId = new CozyRecord();
            cozyRecordByDeviceId.getState().setPim(deviceCard.getDeviceData().getData().getStatus().getPim());
            cozyRecordByDeviceId.getState().setErrorLevel(deviceCard.getDeviceData().getData().getStatus().getErrorLevel());
            cozyRecordByDeviceId.getState().setMode(deviceCard.getDeviceData().getData().getStatus().getMode());
            cozyRecordByDeviceId.getState().setOta(deviceCard.getDeviceData().getData().getStatus().getOta());
            cozyRecordByDeviceId.getState().setInTemp(deviceCard.getDeviceData().getData().getStatus().getInTemp());
            cozyRecordByDeviceId.getState().setErrorCode(deviceCard.getDeviceData().getData().getStatus().getErrorCode());
        }
        if (cozyRecordByDeviceId == null || cozyRecordByDeviceId.getState() == null) {
            this.state.setText("");
        } else if (cozyRecordByDeviceId.getState().getErrorLevel() == 1) {
            this.state.setText(R.string.State_error);
            this.state.setTextColor(ArmsUtils.getColor(this.mActivity, R.color.device_error));
        } else if (cozyRecordByDeviceId.getState().getPim() == 0) {
            this.state.setText(R.string.Offline);
            this.state.setTextColor(ArmsUtils.getColor(this.mActivity, R.color.gray));
        } else if (cozyRecordByDeviceId.getState().getInTemp() == -1000) {
            this.state.setText(R.string.Loading);
            this.state.setTextColor(ArmsUtils.getColor(this.mActivity, R.color.gray));
        } else if (cozyRecordByDeviceId.getState().getOta() == 1) {
            this.state.setText(R.string.Mate_ota);
            this.state.setTextColor(ArmsUtils.getColor(this.mActivity, R.color.device_error));
        } else if (cozyRecordByDeviceId.getState().getMode() == 0) {
            if (cozyRecordByDeviceId.getState().getTempCtrMode() == 2 && cozyRecordByDeviceId.getState().getAutoworkType() == -1) {
                this.state.setText(R.string.Cozy_await);
            } else {
                this.state.setText(R.string.Power_off);
            }
            this.state.setTextColor(ArmsUtils.getColor(this.mActivity, R.color.device_normal));
        } else if (cozyRecordByDeviceId.getState().getMode() == 1) {
            this.state.setText(R.string.Mode_balance);
            this.state.setTextColor(ArmsUtils.getColor(this.mActivity, R.color.device_normal));
        } else if (cozyRecordByDeviceId.getState().getMode() == 5 || cozyRecordByDeviceId.getState().getMode() == 2) {
            this.state.setText(R.string.State_working);
            this.state.setTextColor(ArmsUtils.getColor(this.mActivity, R.color.device_normal));
        } else {
            this.state.setText(R.string.State_working);
            this.state.setTextColor(ArmsUtils.getColor(this.mActivity, R.color.device_normal));
        }
        this.name.setTextColor(CommonUtils.getColorById(R.color.black));
        this.desc.setTextColor(CommonUtils.getColorById(R.color.gray));
        this.desc.setText(deviceCard.getDeviceData().getData().getDesc());
        if (deviceCard.getDeviceData().getData().getShared() != null) {
            this.noticeFlag.setVisibility(8);
            this.desc.setText(this.mActivity.getString(R.string.Mate_share_owner_format, deviceCard.getDeviceData().getData().getShared().getOwnerNick()));
        }
        if (cozyRecordByDeviceId != null && cozyRecordByDeviceId.getState().getPim() == 1) {
            this.icon.setImageResource(R.drawable.home_dev_cozy);
            this.name.setTextColor(CommonUtils.getColorById(R.color.black));
            this.desc.setTextColor(CommonUtils.getColorById(R.color.gray));
        } else {
            this.icon.setImageResource(R.drawable.home_dev_cozy);
            this.name.setTextColor(CommonUtils.getColorById(R.color.device_state_offline));
            this.desc.setTextColor(CommonUtils.getColorById(R.color.gray));
        }
        if (deviceCard.getDeviceData().getData().getShared() != null) {
            String name = deviceCard.getDeviceData().getData().getName();
            String petNick = deviceCard.getDeviceData().getData().getShared().getPetNick();
            if (!TextUtils.isEmpty(name)) {
                this.name.setText(name);
                return;
            } else if (!TextUtils.isEmpty(petNick)) {
                this.name.setText(this.activity.getString(R.string.Cozy_name_format, petNick));
                return;
            } else {
                this.name.setText(this.activity.getString(R.string.Device_z1_name));
                return;
            }
        }
        String name2 = deviceCard.getDeviceData().getData().getName();
        String petId = deviceCard.getDeviceData().getData().getOwner().getPetId();
        if (!TextUtils.isEmpty(name2)) {
            this.name.setText(name2);
        } else if (UserInforUtils.getPetById(petId) != null) {
            this.name.setText(this.activity.getString(R.string.Cozy_name_format, UserInforUtils.getPetById(petId).getName()));
        } else {
            this.name.setText(this.activity.getString(R.string.Device_z1_name));
        }
    }
}

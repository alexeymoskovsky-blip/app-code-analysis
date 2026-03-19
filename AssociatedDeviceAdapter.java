package com.petkit.android.activities.petkitBleDevice.w5.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.petkit.android.activities.cozy.mode.CozyRecord;
import com.petkit.android.activities.d2.mode.D2Record;
import com.petkit.android.activities.device.mode.DeviceInfos;
import com.petkit.android.activities.feeder.model.FeederRecord;
import com.petkit.android.activities.petkitBleDevice.aq.mode.AqRecord;
import com.petkit.android.activities.petkitBleDevice.aqh1.mode.Aqh1Record;
import com.petkit.android.activities.petkitBleDevice.aqr.mode.AqrRecord;
import com.petkit.android.activities.petkitBleDevice.d3.mode.D3Record;
import com.petkit.android.activities.petkitBleDevice.d4.mode.D4Record;
import com.petkit.android.activities.petkitBleDevice.d4s.mode.D4sRecord;
import com.petkit.android.activities.petkitBleDevice.d4sh.mode.D4shRecord;
import com.petkit.android.activities.petkitBleDevice.hg.mode.HgRecord;
import com.petkit.android.activities.petkitBleDevice.k3.mode.K3Record;
import com.petkit.android.activities.petkitBleDevice.mode.K2Record;
import com.petkit.android.activities.petkitBleDevice.mode.T3Record;
import com.petkit.android.activities.petkitBleDevice.p3.mode.P3Record;
import com.petkit.android.activities.petkitBleDevice.r2.mode.R2Record;
import com.petkit.android.activities.petkitBleDevice.t4.mode.T4Record;
import com.petkit.android.activities.petkitBleDevice.utils.BleDeviceUtils;
import com.petkit.android.activities.petkitBleDevice.w5.mode.W5Record;
import com.petkit.oversea.R;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes5.dex */
public class AssociatedDeviceAdapter extends RecyclerView.Adapter<AssociatedDeviceViewHolder> {
    public long deviceId;
    public List<DeviceInfos> deviceInfos;
    public int deviceType;
    public OnClickListener listener;
    public Context mContext;

    public interface OnClickListener {
        void onViewClick(DeviceInfos deviceInfos, int i);
    }

    public AssociatedDeviceAdapter(Context context, List<DeviceInfos> list, OnClickListener onClickListener) {
        new ArrayList();
        this.listener = onClickListener;
        this.deviceInfos = list;
        this.mContext = context;
    }

    public AssociatedDeviceAdapter(Context context, List<DeviceInfos> list, long j, int i, OnClickListener onClickListener) {
        new ArrayList();
        this.listener = onClickListener;
        this.deviceInfos = list;
        this.mContext = context;
        this.deviceId = j;
        this.deviceType = i;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public AssociatedDeviceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new AssociatedDeviceViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.adapter_associated_device_item_layout, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(@NonNull AssociatedDeviceViewHolder associatedDeviceViewHolder, final int i) {
        DeviceInfos deviceInfos = this.deviceInfos.get(i);
        if (deviceInfos instanceof CozyRecord) {
            if (this.deviceType == 5 && this.deviceId == deviceInfos.getDeviceId()) {
                associatedDeviceViewHolder.ivRelevance.setVisibility(0);
            }
            associatedDeviceViewHolder.tvDeviceIcon.setImageResource(R.drawable.dev_big_cozy);
        } else if (deviceInfos instanceof FeederRecord) {
            if (this.deviceType == 4 && this.deviceId == deviceInfos.getDeviceId()) {
                associatedDeviceViewHolder.ivRelevance.setVisibility(0);
            }
            associatedDeviceViewHolder.tvDeviceIcon.setImageResource(R.drawable.feeder_setting_icon);
        } else if (deviceInfos instanceof D2Record) {
            if (this.deviceType == 6 && this.deviceId == deviceInfos.getDeviceId()) {
                associatedDeviceViewHolder.ivRelevance.setVisibility(0);
            }
            associatedDeviceViewHolder.tvDeviceIcon.setImageResource(R.drawable.d2_setting_icon);
        } else if (deviceInfos instanceof D3Record) {
            if (this.deviceType == 9 && this.deviceId == deviceInfos.getDeviceId()) {
                associatedDeviceViewHolder.ivRelevance.setVisibility(0);
            }
            associatedDeviceViewHolder.tvDeviceIcon.setImageResource(R.drawable.d3_setting_icon);
        } else if (deviceInfos instanceof D4Record) {
            if (this.deviceType == 11 && this.deviceId == deviceInfos.getDeviceId()) {
                associatedDeviceViewHolder.ivRelevance.setVisibility(0);
            }
            associatedDeviceViewHolder.tvDeviceIcon.setImageResource(R.drawable.d4_setting_icon);
        } else if (deviceInfos instanceof D4sRecord) {
            if (this.deviceType == 20 && this.deviceId == deviceInfos.getDeviceId()) {
                associatedDeviceViewHolder.ivRelevance.setVisibility(0);
            }
            associatedDeviceViewHolder.tvDeviceIcon.setImageResource(R.drawable.d4s_setting_icon);
        } else if (deviceInfos instanceof D4shRecord) {
            if (this.deviceType == 25 && this.deviceId == deviceInfos.getDeviceId()) {
                associatedDeviceViewHolder.ivRelevance.setVisibility(0);
            }
            associatedDeviceViewHolder.tvDeviceIcon.setImageResource(R.drawable.d4sh_setting_icon);
        } else if (deviceInfos instanceof K2Record) {
            if (this.deviceType == 8 && this.deviceId == deviceInfos.getDeviceId()) {
                associatedDeviceViewHolder.ivRelevance.setVisibility(0);
            }
            associatedDeviceViewHolder.tvDeviceIcon.setImageResource(R.drawable.k2_device_set);
        } else if (deviceInfos instanceof K3Record) {
            if (this.deviceType == 16 && this.deviceId == deviceInfos.getDeviceId()) {
                associatedDeviceViewHolder.ivRelevance.setVisibility(0);
            }
            associatedDeviceViewHolder.tvDeviceIcon.setImageResource(R.drawable.k3_setting_icon);
        } else if (deviceInfos instanceof T3Record) {
            if (this.deviceType == 7 && this.deviceId == deviceInfos.getDeviceId()) {
                associatedDeviceViewHolder.ivRelevance.setVisibility(0);
            }
            associatedDeviceViewHolder.tvDeviceIcon.setImageResource(R.drawable.t3_setting_device_icon);
        } else if (deviceInfos instanceof T4Record) {
            if (this.deviceType == 15 && this.deviceId == deviceInfos.getDeviceId()) {
                associatedDeviceViewHolder.ivRelevance.setVisibility(0);
            }
            associatedDeviceViewHolder.tvDeviceIcon.setImageResource(R.drawable.t4_setting_device_icon);
        } else if (deviceInfos instanceof AqRecord) {
            if (this.deviceType == 10 && this.deviceId == deviceInfos.getDeviceId()) {
                associatedDeviceViewHolder.ivRelevance.setVisibility(0);
            }
            if (((AqRecord) deviceInfos).getTypeCode() == 2) {
                associatedDeviceViewHolder.tvDeviceIcon.setImageResource(R.drawable.aq1s_setting_icon);
            } else {
                associatedDeviceViewHolder.tvDeviceIcon.setImageResource(R.drawable.aq_setting_icon);
            }
        } else if (deviceInfos instanceof AqrRecord) {
            if (this.deviceType == 17 && this.deviceId == deviceInfos.getDeviceId()) {
                associatedDeviceViewHolder.ivRelevance.setVisibility(0);
            }
            associatedDeviceViewHolder.tvDeviceIcon.setImageResource(R.drawable.aqr_setting_icon);
        } else if (deviceInfos instanceof P3Record) {
            if (this.deviceType == 12 && this.deviceId == deviceInfos.getDeviceId()) {
                associatedDeviceViewHolder.ivRelevance.setVisibility(0);
            }
            if (((P3Record) deviceInfos).getTypeCode() == 2) {
                associatedDeviceViewHolder.tvDeviceIcon.setImageResource(R.drawable.p3d_setting_icon);
            } else {
                associatedDeviceViewHolder.tvDeviceIcon.setImageResource(R.drawable.p3c_setting_icon);
            }
        } else if (deviceInfos instanceof R2Record) {
            if (this.deviceType == 18 && this.deviceId == deviceInfos.getDeviceId()) {
                associatedDeviceViewHolder.ivRelevance.setVisibility(0);
            }
            associatedDeviceViewHolder.tvDeviceIcon.setImageResource(R.drawable.r2_setting_icon);
        } else if (deviceInfos instanceof HgRecord) {
            if (this.deviceType == 22 && this.deviceId == deviceInfos.getDeviceId()) {
                associatedDeviceViewHolder.ivRelevance.setVisibility(0);
            }
            associatedDeviceViewHolder.tvDeviceIcon.setImageResource(R.drawable.hg_setting_icon);
        } else if (deviceInfos instanceof Aqh1Record) {
            if (this.deviceType == 19 && this.deviceId == deviceInfos.getDeviceId()) {
                associatedDeviceViewHolder.ivRelevance.setVisibility(0);
            }
            if (((Aqh1Record) deviceInfos).getTypeCode() == 2) {
                associatedDeviceViewHolder.tvDeviceIcon.setImageResource(R.drawable.aqh1_h_setting_icon);
            } else {
                associatedDeviceViewHolder.tvDeviceIcon.setImageResource(R.drawable.aqh1_l_setting_icon);
            }
        } else if (deviceInfos instanceof W5Record) {
            if (this.deviceType == 14 && this.deviceId == deviceInfos.getDeviceId()) {
                associatedDeviceViewHolder.ivRelevance.setVisibility(0);
            }
            W5Record w5Record = (W5Record) deviceInfos;
            if (w5Record.getTypeCode() == 1) {
                associatedDeviceViewHolder.tvDeviceIcon.setImageResource(R.drawable.w5_setting_icon);
            } else if (w5Record.getTypeCode() == 2) {
                associatedDeviceViewHolder.tvDeviceIcon.setImageResource(R.drawable.w5c_setting_icon);
            } else if (w5Record.getTypeCode() == 3) {
                associatedDeviceViewHolder.tvDeviceIcon.setImageResource(R.drawable.w5n_setting_icon);
            } else if (w5Record.getTypeCode() == 4 || w5Record.getTypeCode() == 6) {
                associatedDeviceViewHolder.tvDeviceIcon.setImageResource(R.drawable.w4x_setting_icon);
            } else if (w5Record.getTypeCode() == 5) {
                associatedDeviceViewHolder.tvDeviceIcon.setImageResource(R.drawable.ctw2_setting_icon);
            }
        }
        if (this.deviceId != 0 && this.deviceType != 0 && deviceInfos.getDeviceId() == this.deviceId && BleDeviceUtils.getDeviceTypeByDeviceInfos(deviceInfos) == this.deviceType) {
            associatedDeviceViewHolder.ivRelevance.setVisibility(0);
        } else {
            associatedDeviceViewHolder.ivRelevance.setVisibility(8);
        }
        associatedDeviceViewHolder.tvNoAssociatedDevice.setText(this.deviceInfos.get(i).getName());
        associatedDeviceViewHolder.rlRootPanel.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.w5.adapter.AssociatedDeviceAdapter$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$onBindViewHolder$0(i, view);
            }
        });
    }

    public /* synthetic */ void lambda$onBindViewHolder$0(int i, View view) {
        OnClickListener onClickListener = this.listener;
        if (onClickListener != null) {
            onClickListener.onViewClick(this.deviceInfos.get(i), i);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.deviceInfos.size();
    }

    public static class AssociatedDeviceViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivRelevance;
        public RelativeLayout rlRootPanel;
        public ImageView tvDeviceIcon;
        public TextView tvNoAssociatedDevice;

        public AssociatedDeviceViewHolder(View view) {
            super(view);
            this.tvDeviceIcon = (ImageView) view.findViewById(R.id.tv_device_icon);
            this.tvNoAssociatedDevice = (TextView) view.findViewById(R.id.tv_no_associated_device);
            this.rlRootPanel = (RelativeLayout) view.findViewById(R.id.rl_root_panel);
            this.ivRelevance = (ImageView) view.findViewById(R.id.iv_relevance);
        }
    }
}

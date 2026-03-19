package com.petkit.android.activities.cloudservice.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.petkit.android.activities.base.adapter.BaseRecyclerAdapter;
import com.petkit.android.activities.cloudservice.mode.MineSupportCsDevice;
import com.petkit.android.activities.petkitBleDevice.d4sh.utils.D4shUtils;
import com.petkit.android.activities.petkitBleDevice.t6.utils.T6Utils;
import com.petkit.android.activities.petkitBleDevice.t7.mode.T7Record;
import com.petkit.android.activities.petkitBleDevice.t7.utils.T7DataUtils;
import com.petkit.android.activities.petkitBleDevice.utils.IconUtils;
import com.petkit.android.utils.CommonUtil;
import com.petkit.android.utils.DateUtil;
import com.petkit.oversea.R;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class MineSupportCsDeviceAdapter extends BaseRecyclerAdapter<MineSupportCsDevice> {
    public OnItemClickListener onItemClickListener;
    public int selectIndex;

    public interface OnItemClickListener {
        void onItemClick(int i, MineSupportCsDevice mineSupportCsDevice);
    }

    public MineSupportCsDeviceAdapter(Activity activity) {
        super(activity);
        this.selectIndex = -1;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override // com.petkit.android.activities.base.adapter.BaseRecyclerAdapter
    public void showData(RecyclerView.ViewHolder viewHolder, final int i, List<MineSupportCsDevice> list) {
        MineSupportCsDeviceViewHolder mineSupportCsDeviceViewHolder = (MineSupportCsDeviceViewHolder) viewHolder;
        final MineSupportCsDevice mineSupportCsDevice = list.get(i);
        mineSupportCsDeviceViewHolder.deviceName.setText(mineSupportCsDevice.getDeviceName());
        mineSupportCsDeviceViewHolder.deviceIcon.setImageResource(IconUtils.getInstance().getRealDeviceIcon(mineSupportCsDevice.getDeviceType(), 0));
        if (mineSupportCsDevice.getDeviceType() == 27) {
            mineSupportCsDeviceViewHolder.deviceIcon.setImageResource(R.drawable.dev_t6_search);
        } else if (mineSupportCsDevice.getDeviceType() == 21) {
            if (T6Utils.getOrCreateT6RecordByDeviceId(mineSupportCsDevice.getDeviceId(), 1).getModelCode() == 1) {
                mineSupportCsDeviceViewHolder.deviceIcon.setImageResource(R.drawable.dev_t5_search);
            } else {
                mineSupportCsDeviceViewHolder.deviceIcon.setImageResource(R.drawable.dev_t5_2_search);
            }
        } else if (mineSupportCsDevice.getDeviceType() == 28) {
            T7Record t7RecordById = T7DataUtils.getInstance().getT7RecordById(mineSupportCsDevice.getDeviceId());
            if (t7RecordById != null && t7RecordById.getModelCode() == 1) {
                mineSupportCsDeviceViewHolder.deviceIcon.setImageResource(R.drawable.dev_t7_g_search);
            } else {
                mineSupportCsDeviceViewHolder.deviceIcon.setImageResource(R.drawable.dev_t7_search);
            }
        } else if (mineSupportCsDevice.getDeviceType() == 26) {
            mineSupportCsDeviceViewHolder.deviceIcon.setImageResource(IconUtils.getInstance().getRealDeviceIcon(26, D4shUtils.getD4shRecordByDeviceId(mineSupportCsDevice.getDeviceId()).getModelCode()));
        } else if (mineSupportCsDevice.getDeviceType() == 25) {
            mineSupportCsDeviceViewHolder.deviceIcon.setImageResource(IconUtils.getInstance().getRealDeviceIcon(25, D4shUtils.getD4shRecordByDeviceId(mineSupportCsDevice.getDeviceId()).getModelCode()));
        }
        if (TextUtils.isEmpty(mineSupportCsDevice.getWorkIndate()) || mineSupportCsDevice.getWorkIndate().equals("0")) {
            mineSupportCsDeviceViewHolder.csStatus.setText(this.mActivity.getResources().getString(R.string.Not_open_for_now));
        } else {
            mineSupportCsDeviceViewHolder.csStatus.setText(getTime(CommonUtil.getLong(mineSupportCsDevice.getWorkIndate()) * 1000));
        }
        if (i == this.selectIndex) {
            mineSupportCsDeviceViewHolder.selectStatus.setImageResource(R.drawable.mine_support_cs_device_yellow_circle);
        } else {
            mineSupportCsDeviceViewHolder.selectStatus.setImageResource(R.drawable.mine_support_cs_device_blank_circle);
        }
        mineSupportCsDeviceViewHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.cloudservice.adapter.MineSupportCsDeviceAdapter$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$showData$0(i, mineSupportCsDevice, view);
            }
        });
    }

    public final /* synthetic */ void lambda$showData$0(int i, MineSupportCsDevice mineSupportCsDevice, View view) {
        OnItemClickListener onItemClickListener = this.onItemClickListener;
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(i, mineSupportCsDevice);
        }
    }

    public final String getTime(long j) {
        return DateUtil.getDateFormatShortString(j);
    }

    @SuppressLint({"NotifyDataSetChanged"})
    public void setSelectIndex(int i) {
        this.selectIndex = i;
        notifyDataSetChanged();
    }

    public MineSupportCsDevice getSelectDevice() {
        int i = this.selectIndex;
        if (i < 0) {
            return null;
        }
        return getItemData(i);
    }

    @Override // com.petkit.android.activities.base.adapter.BaseRecyclerAdapter
    public View createView(ViewGroup viewGroup, int i) {
        return LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_mine_support_cs_device, viewGroup, false);
    }

    @Override // com.petkit.android.activities.base.adapter.BaseRecyclerAdapter
    public RecyclerView.ViewHolder createViewHolder(View view, int i) {
        return new MineSupportCsDeviceViewHolder(view);
    }

    public static class MineSupportCsDeviceViewHolder extends RecyclerView.ViewHolder {
        public TextView csStatus;
        public ImageView deviceIcon;
        public TextView deviceName;
        public ImageView selectStatus;

        public MineSupportCsDeviceViewHolder(@NonNull View view) {
            super(view);
            this.deviceIcon = (ImageView) view.findViewById(R.id.device_icon);
            this.deviceName = (TextView) view.findViewById(R.id.device_name);
            this.csStatus = (TextView) view.findViewById(R.id.cs_status);
            this.selectStatus = (ImageView) view.findViewById(R.id.select_status);
        }
    }
}

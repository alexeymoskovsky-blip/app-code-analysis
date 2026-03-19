package com.petkit.android.activities.device.adapter;

import android.content.Context;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.petkit.android.activities.common.utils.DeviceCenterUtils;
import com.petkit.android.activities.device.mode.BaseDevice;
import com.petkit.android.activities.device.utils.DeviceUtils;
import com.petkit.android.activities.home.adapter.model.DeviceCard;
import com.petkit.android.activities.petkitBleDevice.d4.mode.D4Record;
import com.petkit.android.activities.petkitBleDevice.d4.utils.D4Utils;
import com.petkit.android.activities.petkitBleDevice.utils.TimeUtils;
import com.petkit.android.model.DeviceRelation;
import com.petkit.android.model.ItemsBean;
import com.petkit.android.model.Pet;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.oversea.R;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class DeviceHealthFeedListAdapter extends RecyclerView.Adapter<DeviceFeedViewHolder> {
    public boolean isEdit = false;
    public ArrayList<ItemsBean> items = new ArrayList<>();
    public OnClickListener listener;
    public Context mContext;
    public Pet pet;

    public interface OnClickListener {
        void onViewClick(ItemsBean itemsBean, int i);
    }

    public OnClickListener getListener() {
        return this.listener;
    }

    public void setListener(OnClickListener onClickListener) {
        this.listener = onClickListener;
    }

    public void setData(List<ItemsBean> list) {
        this.items.clear();
        this.items.addAll(list);
    }

    public ArrayList<ItemsBean> getData() {
        return this.items;
    }

    public DeviceHealthFeedListAdapter(Context context, Pet pet, OnClickListener onClickListener) {
        this.listener = onClickListener;
        this.mContext = context;
        this.pet = pet;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public DeviceFeedViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new DeviceFeedViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.adapter_device_health_feed_item, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(@NonNull final DeviceFeedViewHolder deviceFeedViewHolder, final int i) {
        SpannableString spannableString;
        D4Record d4RecordByDeviceId;
        final ItemsBean itemsBean = this.items.get(i);
        deviceFeedViewHolder.tvTime.setText(TimeUtils.getInstance().secondsToTimeStringWithUnit(this.mContext, itemsBean.getTime()));
        deviceFeedViewHolder.tvName.setText(itemsBean.getName());
        if (this.items.get(i).getDeviceId() == 0) {
            Pet pet = this.pet;
            if (pet != null) {
                List<BaseDevice> feedDeviceByPet = DeviceCenterUtils.getFeedDeviceByPet(pet.getId());
                if (feedDeviceByPet != null && feedDeviceByPet.size() > 0) {
                    deviceFeedViewHolder.tvAmount.setText(this.items.get(i).getAmount() + this.mContext.getString(R.string.Unit_g));
                    deviceFeedViewHolder.tvPetName.setText("[" + this.mContext.getString(R.string.Unspecified_device) + "]");
                    deviceFeedViewHolder.tvPetName.setTextColor(this.mContext.getResources().getColor(R.color.cozy_tempbar_dew));
                    deviceFeedViewHolder.tvPetName.setVisibility(8);
                } else {
                    deviceFeedViewHolder.tvAmount.setText(this.items.get(i).getAmount() + this.mContext.getString(R.string.Unit_g));
                    deviceFeedViewHolder.tvPetName.setVisibility(8);
                }
            }
        } else {
            String amountFormatDesc = DeviceUtils.getAmountFormatDesc(itemsBean.getAmount(), itemsBean.getDeviceType(), this.mContext);
            if (itemsBean.getDeviceType() == 11 && (d4RecordByDeviceId = D4Utils.getD4RecordByDeviceId(itemsBean.getDeviceId())) != null) {
                amountFormatDesc = DeviceUtils.getAmountFormatDesc(d4RecordByDeviceId.getSettings().getFactor(), itemsBean.getAmount(), itemsBean.getDeviceType(), this.mContext);
            }
            String str = this.mContext.getString(R.string.About) + itemsBean.getAmount() + this.mContext.getString(R.string.Unit_g);
            if (this.items.get(i).getDeviceType() == 9) {
                spannableString = new SpannableString(amountFormatDesc);
            } else {
                SpannableString spannableString2 = new SpannableString(amountFormatDesc + "\n" + str);
                spannableString2.setSpan(new ForegroundColorSpan(this.mContext.getResources().getColor(R.color.gray)), amountFormatDesc.length(), spannableString2.length(), 33);
                spannableString2.setSpan(new RelativeSizeSpan(0.6f), amountFormatDesc.length(), spannableString2.length(), 33);
                spannableString = spannableString2;
            }
            deviceFeedViewHolder.tvAmount.setText(spannableString);
            deviceFeedViewHolder.tvPetName.setVisibility(0);
            DeviceCard device = DeviceCenterUtils.getDevice(Long.valueOf(this.items.get(i).getDeviceId()), this.items.get(i).getDeviceType());
            if (device != null) {
                String name = device.getDeviceData().getData().getName();
                DeviceRelation relation = device.getDeviceData().getData().getRelation();
                String string = null;
                List<String> petIds = relation != null ? relation.getPetIds() : null;
                int viewType = device.getViewType();
                if (viewType == 4) {
                    string = this.mContext.getString(R.string.Device_d1_name);
                } else if (viewType == 6) {
                    string = this.mContext.getString(R.string.Device_mini_name);
                }
                if (!TextUtils.isEmpty(name)) {
                    string = "[" + name + "]";
                } else if (petIds != null && petIds.size() > 0 && UserInforUtils.getPetById(petIds.get(0)) != null) {
                    if (device.getViewType() == 4) {
                        string = "[" + this.mContext.getString(R.string.Feeder_name_format, UserInforUtils.getPetById(petIds.get(0)).getName()) + "]";
                    } else if (device.getViewType() == 6) {
                        string = "[" + this.mContext.getString(R.string.D2_name_format, UserInforUtils.getPetById(petIds.get(0)).getName()) + "]";
                    }
                } else {
                    string = "[" + string + "]";
                }
                deviceFeedViewHolder.tvPetName.setText(string);
                deviceFeedViewHolder.tvPetName.setTextColor(this.mContext.getResources().getColor(R.color.gray));
            }
        }
        if (this.isEdit) {
            deviceFeedViewHolder.imgArrow.setVisibility(0);
        } else {
            deviceFeedViewHolder.imgArrow.setVisibility(4);
        }
        deviceFeedViewHolder.llPanel.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.device.adapter.DeviceHealthFeedListAdapter$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$onBindViewHolder$0(itemsBean, i, view);
            }
        });
        deviceFeedViewHolder.tvTime.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.petkit.android.activities.device.adapter.DeviceHealthFeedListAdapter.1
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                int measuredHeight = deviceFeedViewHolder.tvTime.getMeasuredHeight();
                ViewGroup.LayoutParams layoutParams = deviceFeedViewHolder.rlTopPanel.getLayoutParams();
                layoutParams.height = measuredHeight;
                deviceFeedViewHolder.rlTopPanel.setLayoutParams(layoutParams);
                deviceFeedViewHolder.tvTime.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    public final /* synthetic */ void lambda$onBindViewHolder$0(ItemsBean itemsBean, int i, View view) {
        OnClickListener onClickListener = this.listener;
        if (onClickListener != null) {
            onClickListener.onViewClick(itemsBean, i);
        }
    }

    public void sortAndNotifyDataSetChanged() {
        Collections.sort(this.items);
        notifyDataSetChanged();
    }

    public boolean isEdit() {
        return this.isEdit;
    }

    public void setEdit(boolean z) {
        this.isEdit = z;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.items.size();
    }

    public static class DeviceFeedViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgArrow;
        public RelativeLayout llPanel;
        public RelativeLayout rlTopPanel;
        public TextView tvAmount;
        public TextView tvAmountDetail;
        public TextView tvName;
        public TextView tvPetName;
        public TextView tvTime;

        public DeviceFeedViewHolder(View view) {
            super(view);
            this.tvTime = (TextView) view.findViewById(R.id.tv_time);
            this.tvName = (TextView) view.findViewById(R.id.tv_name);
            this.tvPetName = (TextView) view.findViewById(R.id.tv_pet_name);
            this.tvAmount = (TextView) view.findViewById(R.id.tv_amount);
            this.imgArrow = (ImageView) view.findViewById(R.id.img_arrow);
            this.llPanel = (RelativeLayout) view.findViewById(R.id.ll_panel);
            this.rlTopPanel = (RelativeLayout) view.findViewById(R.id.rl_top_panel);
            this.tvAmountDetail = (TextView) view.findViewById(R.id.tv_amount_detail);
        }
    }
}

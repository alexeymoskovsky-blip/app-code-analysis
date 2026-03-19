package com.petkit.android.activities.petkitBleDevice.p3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.jess.arms.widget.imageloader.glide.GlideCircleTransformWithBorder;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.petkitBleDevice.p3.mode.ScanPetData;
import com.petkit.android.model.Pet;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.oversea.R;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes4.dex */
public class P3FindPetAdapter extends RecyclerView.Adapter<FindPetViewHolder> {
    public OnClickListener listener;
    public Context mContext;
    public List<ScanPetData> scanPetDataList;

    public interface OnClickListener {
        void onViewClick(ScanPetData scanPetData, int i);
    }

    public static /* synthetic */ void lambda$onBindViewHolder$2(Throwable th) throws Exception {
    }

    public List<ScanPetData> getScanPetDataList() {
        return this.scanPetDataList;
    }

    public void setScanPetDataList(List<ScanPetData> list) {
        this.scanPetDataList = list;
    }

    public OnClickListener getListener() {
        return this.listener;
    }

    public void setListener(OnClickListener onClickListener) {
        this.listener = onClickListener;
    }

    public P3FindPetAdapter(Context context) {
        this.scanPetDataList = new ArrayList();
        this.mContext = context;
    }

    public P3FindPetAdapter(Context context, List<ScanPetData> list, OnClickListener onClickListener) {
        new ArrayList();
        this.listener = onClickListener;
        this.scanPetDataList = list;
        this.mContext = context;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public FindPetViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new FindPetViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.adapter_p3_find_pets_item, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(@NonNull FindPetViewHolder findPetViewHolder, final int i) {
        Pet petById = UserInforUtils.getPetById(this.scanPetDataList.get(i).getPetId());
        ImageLoader imageLoader = ((BaseApplication) CommonUtils.getAppContext()).getAppComponent().imageLoader();
        Context context = this.mContext;
        String name = "";
        GlideImageConfig.Builder builderErrorPic = GlideImageConfig.builder().url(petById == null ? "" : petById.getAvatar()).imageView(findPetViewHolder.ivPetImage).errorPic(R.drawable.default_header_dog);
        Context context2 = this.mContext;
        imageLoader.loadImage(context, builderErrorPic.transformation(new GlideCircleTransformWithBorder(context2, 1, context2.getResources().getColor(R.color.feeder_recommended_daily_gray))).build());
        if (petById != null) {
            name = petById.getName();
        }
        findPetViewHolder.tvPetName.setText(name);
        findPetViewHolder.tvBleRssi.setText(String.valueOf(this.scanPetDataList.get(i).getRssi()));
        findPetViewHolder.tvBleRssi.setVisibility(8);
        findPetViewHolder.tvDeviceState.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.p3.adapter.P3FindPetAdapter$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$onBindViewHolder$0(i, view);
            }
        });
        if (this.scanPetDataList.get(i).isConnected()) {
            findPetViewHolder.ivPetBlePower.setVisibility(8);
            findPetViewHolder.tvPetState.setVisibility(0);
            findPetViewHolder.tvPetState.setText(this.mContext.getResources().getString(R.string.Connected));
            findPetViewHolder.tvPetState.setTextColor(this.mContext.getResources().getColor(R.color.light_black));
            findPetViewHolder.tvDeviceState.setClickable(true);
            findPetViewHolder.tvDeviceState.setTextColor(this.mContext.getResources().getColor(R.color.p3_main_orange));
            findPetViewHolder.tvDeviceState.setBackgroundResource(R.drawable.solid_p3_orange_bg);
        } else if (this.scanPetDataList.get(i).isConnecting()) {
            findPetViewHolder.ivPetBlePower.setVisibility(0);
            findPetViewHolder.tvPetState.setVisibility(0);
            findPetViewHolder.tvPetState.setText(this.mContext.getResources().getString(R.string.P3_connecting_prompt));
            findPetViewHolder.tvPetState.setTextColor(this.mContext.getResources().getColor(R.color.gray));
            findPetViewHolder.tvDeviceState.setClickable(false);
            findPetViewHolder.tvDeviceState.setTextColor(this.mContext.getResources().getColor(R.color.gray));
            findPetViewHolder.tvDeviceState.setBackgroundResource(R.drawable.solid_p3_gray_bg);
            findPetViewHolder.tvDeviceState.setText(this.mContext.getResources().getString(R.string.Connecting));
        } else {
            findPetViewHolder.ivPetBlePower.setVisibility(0);
            findPetViewHolder.tvPetState.setVisibility(8);
            findPetViewHolder.tvDeviceState.setClickable(true);
            findPetViewHolder.tvDeviceState.setTextColor(this.mContext.getResources().getColor(R.color.p3_main_orange));
            findPetViewHolder.tvDeviceState.setBackgroundResource(R.drawable.solid_p3_orange_bg);
        }
        if (this.scanPetDataList.get(i).isNeedStartAni()) {
            findPetViewHolder.tvDeviceState.setClickable(false);
            findPetViewHolder.tvDeviceState.setTextColor(this.mContext.getResources().getColor(R.color.gray));
            findPetViewHolder.tvDeviceState.setBackgroundResource(R.drawable.solid_p3_gray_bg);
            findPetViewHolder.tvDeviceState.setText(this.mContext.getResources().getString(R.string.Calling));
            Observable.timer(3L, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.petkit.android.activities.petkitBleDevice.p3.adapter.P3FindPetAdapter$$ExternalSyntheticLambda1
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f$0.lambda$onBindViewHolder$1(i, (Long) obj);
                }
            }, new Consumer() { // from class: com.petkit.android.activities.petkitBleDevice.p3.adapter.P3FindPetAdapter$$ExternalSyntheticLambda2
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    P3FindPetAdapter.lambda$onBindViewHolder$2((Throwable) obj);
                }
            });
        } else if (this.scanPetDataList.get(i).isConnecting()) {
            findPetViewHolder.ivPetBlePower.setVisibility(0);
            findPetViewHolder.tvPetState.setVisibility(0);
            findPetViewHolder.tvPetState.setText(this.mContext.getResources().getString(R.string.P3_connecting_prompt));
            findPetViewHolder.tvPetState.setTextColor(this.mContext.getResources().getColor(R.color.gray));
            findPetViewHolder.tvDeviceState.setClickable(false);
            findPetViewHolder.tvDeviceState.setTextColor(this.mContext.getResources().getColor(R.color.gray));
            findPetViewHolder.tvDeviceState.setBackgroundResource(R.drawable.solid_p3_gray_bg);
            findPetViewHolder.tvDeviceState.setText(this.mContext.getResources().getString(R.string.Connecting));
        } else {
            findPetViewHolder.tvDeviceState.setClickable(true);
            findPetViewHolder.tvDeviceState.setTextColor(this.mContext.getResources().getColor(R.color.p3_main_orange));
            findPetViewHolder.tvDeviceState.setBackgroundResource(R.drawable.solid_p3_orange_bg);
            findPetViewHolder.tvDeviceState.setText(this.mContext.getResources().getString(R.string.Immediately_call));
        }
        int rssi = this.scanPetDataList.get(i).getRssi();
        if (rssi >= -50) {
            findPetViewHolder.ivPetBlePower.setImageResource(R.drawable.p3_ble_power_five);
        } else if (rssi >= -60 && rssi < -50) {
            findPetViewHolder.ivPetBlePower.setImageResource(R.drawable.p3_ble_power_four);
        } else if (rssi >= -70 && rssi < -60) {
            findPetViewHolder.ivPetBlePower.setImageResource(R.drawable.p3_ble_power_three);
        } else if (rssi >= -80 && rssi < -70) {
            findPetViewHolder.ivPetBlePower.setImageResource(R.drawable.p3_ble_power_two);
        } else if (rssi < -80) {
            findPetViewHolder.ivPetBlePower.setImageResource(R.drawable.p3_ble_power_one);
        }
        findPetViewHolder.ivPetBleImage.setVisibility(8);
    }

    public /* synthetic */ void lambda$onBindViewHolder$0(int i, View view) {
        OnClickListener onClickListener = this.listener;
        if (onClickListener != null) {
            onClickListener.onViewClick(this.scanPetDataList.get(i), i);
        }
    }

    public final /* synthetic */ void lambda$onBindViewHolder$1(int i, Long l) throws Exception {
        List<ScanPetData> list = this.scanPetDataList;
        if (list != null) {
            list.get(i).setNeedStartAni(false);
            notifyDataSetChanged();
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.scanPetDataList.size();
    }

    public static class FindPetViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivPetBleImage;
        public ImageView ivPetBlePower;
        public ImageView ivPetImage;
        public LinearLayout llBlePower;
        public RelativeLayout llName;
        public RelativeLayout rlRootPanel;
        public TextView tvBleRssi;
        public TextView tvDeviceState;
        public TextView tvPetName;
        public TextView tvPetState;

        public FindPetViewHolder(View view) {
            super(view);
            this.tvDeviceState = (TextView) view.findViewById(R.id.tv_device_state);
            this.ivPetImage = (ImageView) view.findViewById(R.id.iv_pet_image);
            this.ivPetBleImage = (ImageView) view.findViewById(R.id.iv_pet_ble_image);
            this.ivPetBlePower = (ImageView) view.findViewById(R.id.iv_pet_ble_power);
            this.tvBleRssi = (TextView) view.findViewById(R.id.tv_ble_rssi);
            this.llBlePower = (LinearLayout) view.findViewById(R.id.ll_ble_power);
            this.tvPetName = (TextView) view.findViewById(R.id.tv_pet_name);
            this.tvPetState = (TextView) view.findViewById(R.id.tv_pet_state);
            this.llName = (RelativeLayout) view.findViewById(R.id.ll_name);
            this.rlRootPanel = (RelativeLayout) view.findViewById(R.id.rl_root_panel);
        }
    }
}

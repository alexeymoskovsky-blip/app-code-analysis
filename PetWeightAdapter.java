package com.petkit.android.activities.petkitBleDevice.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.jess.arms.widget.imageloader.glide.GlideCircleTransform;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.pet.PetDetailModifyActivity;
import com.petkit.android.model.Pet;
import com.petkit.android.utils.CommonUtil;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.oversea.R;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class PetWeightAdapter extends RecyclerView.Adapter<BasePetWeightViewHolder> {
    public final int deviceType;
    public OnClickListener listener;
    public Context mContext;
    public ArrayList<Pet> pets = new ArrayList<>();
    public UploadCatFaceListener uploadCatFaceListener;

    public interface OnClickListener {
        void onViewClick(Pet pet);
    }

    public interface UploadCatFaceListener {
        void upload(Pet pet);
    }

    public OnClickListener getListener() {
        return this.listener;
    }

    public void setListener(OnClickListener onClickListener) {
        this.listener = onClickListener;
    }

    public void setData(List<Pet> list) {
        this.pets.clear();
        this.pets.addAll(list);
        notifyDataSetChanged();
    }

    public ArrayList<Pet> getData() {
        return this.pets;
    }

    public PetWeightAdapter(Context context, int i, OnClickListener onClickListener) {
        this.deviceType = i;
        this.listener = onClickListener;
        this.mContext = context;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public BasePetWeightViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == 3) {
            return new EmptyViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.empty_list_data_layout, viewGroup, false));
        }
        if (catFace()) {
            return new VideoPetViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.layout_video_pet_weight, viewGroup, false));
        }
        return new PetViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.layout_pet_weight, viewGroup, false));
    }

    public final boolean catFace() {
        int i = this.deviceType;
        return i == 29 || i == 21 || i == 27 || i == 25 || i == 26;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(@NonNull BasePetWeightViewHolder basePetWeightViewHolder, final int i) {
        Resources resources;
        int i2;
        Resources resources2;
        int i3;
        if (basePetWeightViewHolder instanceof EmptyViewHolder) {
            EmptyViewHolder emptyViewHolder = (EmptyViewHolder) basePetWeightViewHolder;
            emptyViewHolder.tvCenterPrompt.setText(R.string.You_have_no_pet);
            emptyViewHolder.ivCenterIcon.setImageResource(R.drawable.no_pet_icon);
            return;
        }
        if (basePetWeightViewHolder instanceof PetViewHolder) {
            PetViewHolder petViewHolder = (PetViewHolder) basePetWeightViewHolder;
            petViewHolder.rlRootPanel.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.adapter.PetWeightAdapter$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$onBindViewHolder$0(i, view);
                }
            });
            ((BaseApplication) CommonUtils.getAppContext()).getAppComponent().imageLoader().loadImage(CommonUtils.getAppContext(), GlideImageConfig.builder().url(this.pets.get(i).getAvatar()).imageView(petViewHolder.ivPetAvatar).errorPic(R.drawable.default_header_cat).transformation(new GlideCircleTransform(CommonUtils.getAppContext())).build());
            petViewHolder.tvPetName.setText(this.pets.get(i).getName());
            if (UserInforUtils.getCurrentLoginResult().getSettings().getUnit() == 1) {
                resources2 = this.mContext.getResources();
                i3 = R.string.Unit_lb;
            } else {
                resources2 = this.mContext.getResources();
                i3 = R.string.Unit_kg;
            }
            String string = resources2.getString(i3);
            double dDoubleToDouble = CommonUtil.doubleToDouble(UserInforUtils.getCurrentLoginResult().getSettings().getUnit() == 1 ? CommonUtil.KgToLb(Double.valueOf(this.pets.get(i).getWeight()).doubleValue()) : Double.valueOf(this.pets.get(i).getWeight()).doubleValue());
            petViewHolder.tvPetWeight.setText(dDoubleToDouble + string);
            petViewHolder.ivPetAvatar.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.adapter.PetWeightAdapter$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$onBindViewHolder$1(i, view);
                }
            });
            petViewHolder.tvPetWeight.setTextColor(ContextCompat.getColor(this.mContext, R.color.light_black));
            petViewHolder.viewLine.setVisibility(i == this.pets.size() - 1 ? 8 : 0);
            return;
        }
        if (basePetWeightViewHolder instanceof VideoPetViewHolder) {
            VideoPetViewHolder videoPetViewHolder = (VideoPetViewHolder) basePetWeightViewHolder;
            videoPetViewHolder.llWeight.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.adapter.PetWeightAdapter$$ExternalSyntheticLambda2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$onBindViewHolder$2(i, view);
                }
            });
            ((BaseApplication) CommonUtils.getAppContext()).getAppComponent().imageLoader().loadImage(CommonUtils.getAppContext(), GlideImageConfig.builder().url(this.pets.get(i).getAvatar()).imageView(videoPetViewHolder.ivPetAvatar).errorPic(R.drawable.default_header_cat).transformation(new GlideCircleTransform(CommonUtils.getAppContext())).build());
            videoPetViewHolder.tvPetName.setText(this.pets.get(i).getName());
            if (UserInforUtils.getCurrentLoginResult().getSettings().getUnit() == 1) {
                resources = this.mContext.getResources();
                i2 = R.string.Unit_lb;
            } else {
                resources = this.mContext.getResources();
                i2 = R.string.Unit_kg;
            }
            String string2 = resources.getString(i2);
            double dDoubleToDouble2 = CommonUtil.doubleToDouble(UserInforUtils.getCurrentLoginResult().getSettings().getUnit() == 1 ? CommonUtil.KgToLb(Double.valueOf(this.pets.get(i).getWeight()).doubleValue()) : Double.valueOf(this.pets.get(i).getWeight()).doubleValue());
            videoPetViewHolder.tvPetWeight.setText(dDoubleToDouble2 + string2);
            videoPetViewHolder.ivPetAvatar.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.adapter.PetWeightAdapter$$ExternalSyntheticLambda3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$onBindViewHolder$3(i, view);
                }
            });
            videoPetViewHolder.tvPetWeight.setTextColor(ContextCompat.getColor(this.mContext, R.color.light_black));
            if (!UserInforUtils.uploadCatFace(this.pets.get(i).getId())) {
                videoPetViewHolder.tvFaceStatus.setText(this.mContext.getResources().getString(R.string.Not_uploaded));
                videoPetViewHolder.tvFaceStatus.setTextColor(ContextCompat.getColor(this.mContext, R.color.new_bind_blue));
            } else {
                videoPetViewHolder.tvFaceStatus.setText(this.mContext.getResources().getString(R.string.Uploaded));
                videoPetViewHolder.tvFaceStatus.setTextColor(ContextCompat.getColor(this.mContext, R.color.light_black));
            }
            videoPetViewHolder.tvFaceStatus.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.adapter.PetWeightAdapter$$ExternalSyntheticLambda4
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$onBindViewHolder$4(i, view);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$0(int i, View view) {
        OnClickListener onClickListener = this.listener;
        if (onClickListener != null) {
            onClickListener.onViewClick(this.pets.get(i));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$1(int i, View view) {
        Intent intent = new Intent(this.mContext, (Class<?>) PetDetailModifyActivity.class);
        intent.putExtra(Constants.EXTRA_DOG, this.pets.get(i));
        this.mContext.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$2(int i, View view) {
        OnClickListener onClickListener = this.listener;
        if (onClickListener != null) {
            onClickListener.onViewClick(this.pets.get(i));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$3(int i, View view) {
        Intent intent = new Intent(this.mContext, (Class<?>) PetDetailModifyActivity.class);
        intent.putExtra(Constants.EXTRA_DOG, this.pets.get(i));
        this.mContext.startActivity(intent);
    }

    public final /* synthetic */ void lambda$onBindViewHolder$4(int i, View view) {
        UploadCatFaceListener uploadCatFaceListener = this.uploadCatFaceListener;
        if (uploadCatFaceListener != null) {
            uploadCatFaceListener.upload(this.pets.get(i));
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        return this.pets.size() == 0 ? 3 : 2;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.pets.size();
    }

    public static class EmptyViewHolder extends BasePetWeightViewHolder {
        public ImageView ivCenterIcon;
        public TextView tvCenterPrompt;

        public EmptyViewHolder(View view) {
            super(view);
            this.ivCenterIcon = (ImageView) view.findViewById(R.id.iv_center_icon);
            this.tvCenterPrompt = (TextView) view.findViewById(R.id.tv_center_prompt);
        }
    }

    public static class PetViewHolder extends BasePetWeightViewHolder {
        public ImageView ivArrow;
        public ImageView ivPetAvatar;
        public RelativeLayout rlRootPanel;
        public TextView tvPetName;
        public TextView tvPetWeight;
        public View viewLine;

        public PetViewHolder(View view) {
            super(view);
            this.ivPetAvatar = (ImageView) view.findViewById(R.id.iv_pet_avatar);
            this.tvPetName = (TextView) view.findViewById(R.id.tv_pet_name);
            this.ivArrow = (ImageView) view.findViewById(R.id.iv_arrow);
            this.tvPetWeight = (TextView) view.findViewById(R.id.tv_pet_weight);
            this.rlRootPanel = (RelativeLayout) view.findViewById(R.id.rl_root_panel);
            this.viewLine = view.findViewById(R.id.viewLine);
        }
    }

    public static class VideoPetViewHolder extends BasePetWeightViewHolder {
        public ImageView ivArrow;
        public ImageView ivPetAvatar;
        public LinearLayout llWeight;
        public TextView tvFaceStatus;
        public TextView tvPetName;
        public TextView tvPetWeight;

        public VideoPetViewHolder(View view) {
            super(view);
            this.ivPetAvatar = (ImageView) view.findViewById(R.id.iv_pet_avatar);
            this.llWeight = (LinearLayout) view.findViewById(R.id.ll_weight);
            this.tvPetName = (TextView) view.findViewById(R.id.tv_pet_name);
            this.ivArrow = (ImageView) view.findViewById(R.id.iv_arrow);
            this.tvPetWeight = (TextView) view.findViewById(R.id.tv_pet_weight);
            this.tvFaceStatus = (TextView) view.findViewById(R.id.tv_pet_face);
        }
    }

    public static class BasePetWeightViewHolder extends RecyclerView.ViewHolder {
        public BasePetWeightViewHolder(View view) {
            super(view);
        }
    }

    public void setUploadCatFaceListener(UploadCatFaceListener uploadCatFaceListener) {
        this.uploadCatFaceListener = uploadCatFaceListener;
    }
}

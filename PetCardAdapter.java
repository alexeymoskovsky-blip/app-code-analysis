package com.petkit.android.activities.home.adapter;

import android.content.Context;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.jess.arms.widget.imageloader.glide.GlideRoundTransform;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.model.Pet;
import com.petkit.android.utils.CommonUtil;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.oversea.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public class PetCardAdapter extends RecyclerView.Adapter<PetCardViewHolder> {
    public boolean flag;
    public Context mContext;
    public OnItemClickListener onItemClickListener;
    public Map<Integer, PetCardViewHolder> petCardViewHolderMap = new HashMap();
    public List<Pet> pets;

    public interface OnItemClickListener {
        void onItemClick(int i);
    }

    public PetCardAdapter(Context context, List<Pet> list, OnItemClickListener onItemClickListener) {
        this.pets = new ArrayList();
        this.mContext = context;
        this.pets = list;
        this.onItemClickListener = onItemClickListener;
    }

    public void clearList() {
        this.pets.clear();
        notifyDataSetChanged();
    }

    public void addList(List<Pet> list) {
        this.pets.addAll(list);
        notifyDataSetChanged();
    }

    public void setFlag(boolean z) {
        this.flag = z;
    }

    public PetCardViewHolder getPetCardViewHolder(int i) {
        return this.petCardViewHolderMap.get(Integer.valueOf(i));
    }

    public List<Pet> getPets() {
        return this.pets;
    }

    public static class PetCardViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivBirth;
        public ImageView ivPetAvatar;
        public ImageView ivPetSex;
        public ImageView ivShade;
        public ImageView ivWeight;
        public LinearLayout llAddPet;
        public RelativeLayout rlRootPanel;
        public Space spaceAvatar;
        public TextView tvAddPet;
        public TextView tvBirth;
        public TextView tvPetName;
        public TextView tvWeight;

        public PetCardViewHolder(@NonNull View view) {
            super(view);
            this.spaceAvatar = (Space) view.findViewById(R.id.space_avatar);
            this.tvPetName = (TextView) view.findViewById(R.id.tv_pet_name);
            this.ivPetSex = (ImageView) view.findViewById(R.id.iv_pet_sex);
            this.ivBirth = (ImageView) view.findViewById(R.id.iv_birth);
            this.tvBirth = (TextView) view.findViewById(R.id.tv_birth);
            this.ivWeight = (ImageView) view.findViewById(R.id.iv_weight);
            this.tvWeight = (TextView) view.findViewById(R.id.tv_weight);
            this.ivPetAvatar = (ImageView) view.findViewById(R.id.iv_pet_avatar);
            this.rlRootPanel = (RelativeLayout) view.findViewById(R.id.rl_root_panel);
            this.llAddPet = (LinearLayout) view.findViewById(R.id.ll_add_pet);
            this.tvAddPet = (TextView) view.findViewById(R.id.tv_add_pet);
            this.ivShade = (ImageView) view.findViewById(R.id.iv_shade);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public PetCardViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new PetCardViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.layout_home_pet_card, (ViewGroup) null));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(@NonNull PetCardViewHolder petCardViewHolder, final int i) {
        SpannableString spannableString = new SpannableString("+" + this.mContext.getResources().getString(R.string.Immediately_create));
        spannableString.setSpan(new RelativeSizeSpan(0.8f), spannableString.toString().indexOf("+"), 1, 33);
        petCardViewHolder.tvAddPet.setText(spannableString);
        if (this.flag) {
            petCardViewHolder.ivPetAvatar.setImageResource(R.drawable.none_pet_icon);
            petCardViewHolder.ivPetSex.setVisibility(8);
            petCardViewHolder.ivBirth.setVisibility(8);
            petCardViewHolder.ivWeight.setVisibility(8);
            petCardViewHolder.llAddPet.setVisibility(0);
            petCardViewHolder.tvBirth.setText("");
            petCardViewHolder.tvWeight.setText("");
            if (this.onItemClickListener != null) {
                petCardViewHolder.llAddPet.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.home.adapter.PetCardAdapter$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$onBindViewHolder$0(view);
                    }
                });
                return;
            }
            return;
        }
        if (!this.petCardViewHolderMap.containsKey(Integer.valueOf(i))) {
            this.petCardViewHolderMap.put(Integer.valueOf(i), petCardViewHolder);
        }
        petCardViewHolder.llAddPet.setVisibility(8);
        petCardViewHolder.ivPetSex.setVisibility(0);
        petCardViewHolder.ivBirth.setVisibility(0);
        petCardViewHolder.ivWeight.setVisibility(0);
        ImageLoader imageLoader = ((BaseApplication) CommonUtils.getApplication()).getAppComponent().imageLoader();
        Context context = this.mContext;
        GlideImageConfig.Builder builderErrorPic = GlideImageConfig.builder().url(this.pets.get(i).getAvatar()).imageView(petCardViewHolder.ivPetAvatar).placeholder(R.drawable.default_header_cat).errorPic(this.pets.get(i).getType().getId() == 1 ? R.drawable.default_header_dog : R.drawable.default_header_cat);
        Context context2 = this.mContext;
        imageLoader.loadImage(context, builderErrorPic.transformation(new GlideRoundTransform(context2, ArmsUtils.dip2px(context2, 24.0f))).build());
        petCardViewHolder.tvPetName.setText(this.pets.get(i).getName());
        petCardViewHolder.tvBirth.setText(this.pets.get(i).getBirth());
        petCardViewHolder.tvWeight.setText(this.pets.get(i).getWeight());
        petCardViewHolder.ivPetSex.setImageResource(this.pets.get(i).getGender() == 1 ? R.drawable.gender_man : R.drawable.gender_women);
        petCardViewHolder.tvBirth.setText(CommonUtils.getSimplifyAgeByBirthday(this.mContext, this.pets.get(i).getBirth()));
        petCardViewHolder.tvWeight.setText(calcPetWeight(this.pets.get(i).getWeight()));
        if (this.onItemClickListener != null) {
            petCardViewHolder.rlRootPanel.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.home.adapter.PetCardAdapter$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$onBindViewHolder$1(i, view);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$0(View view) {
        this.onItemClickListener.onItemClick(-1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$1(int i, View view) {
        this.onItemClickListener.onItemClick(i);
    }

    public final String calcPetWeight(String str) {
        if (UserInforUtils.getCurrentLoginResult().getSettings().getUnit() == 1) {
            return CommonUtil.doubleToDouble(CommonUtil.KgToLb(Double.valueOf(str).doubleValue())) + this.mContext.getString(R.string.Unit_lb);
        }
        return CommonUtil.doubleToDouble(Double.valueOf(str).doubleValue()) + this.mContext.getString(R.string.Unit_kilogram_short);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.pets.size();
    }
}

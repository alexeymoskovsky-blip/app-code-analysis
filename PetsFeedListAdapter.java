package com.petkit.android.activities.feed.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.jess.arms.widget.imageloader.glide.GlideCircleTransform;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.base.adapter.NormalBaseAdapter;
import com.petkit.android.activities.d2.utils.D2Utils;
import com.petkit.android.activities.feeder.Utils.FeederUtils;
import com.petkit.android.activities.petkitBleDevice.d3.utils.D3Utils;
import com.petkit.android.api.http.apiResponse.MyOwnPetsRsp;
import com.petkit.android.utils.CommonUtil;
import com.petkit.android.utils.PetUtils;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.oversea.R;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class PetsFeedListAdapter extends NormalBaseAdapter {
    public PetsFeedListAdapter(Activity activity, List list) {
        super(activity, list);
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        String str;
        if (view == null || !(view.getTag() instanceof ViewHolder)) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(this.mActivity).inflate(R.layout.adapter_pets_feed_list, (ViewGroup) null);
            viewHolder.avatar = (ImageView) view.findViewById(R.id.pet_avatar);
            viewHolder.name = (TextView) view.findViewById(R.id.pet_name);
            viewHolder.status = (TextView) view.findViewById(R.id.pet_status);
            viewHolder.weight = (TextView) view.findViewById(R.id.pet_weight);
            viewHolder.weightStatus = (TextView) view.findViewById(R.id.pet_weight_status);
            viewHolder.feed = (TextView) view.findViewById(R.id.feed_value);
            viewHolder.food = (TextView) view.findViewById(R.id.pet_food);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        MyOwnPetsRsp.MyOwnPetsResult myOwnPetsResult = (MyOwnPetsRsp.MyOwnPetsResult) getItem(i);
        String petIdsHasD2 = D2Utils.getPetIdsHasD2();
        String petIdsHasFeeder = FeederUtils.getPetIdsHasFeeder();
        String petIdsHasD3 = D3Utils.getPetIdsHasD3();
        try {
            if (!TextUtils.isEmpty(petIdsHasD3) && petIdsHasD3.contains(myOwnPetsResult.getPet().getId())) {
                viewHolder.weightStatus.setBackgroundResource(R.drawable.solid_pet_state_d3_green_bg);
            } else if (!petIdsHasD2.contains(myOwnPetsResult.getPet().getId()) && !petIdsHasFeeder.contains(myOwnPetsResult.getPet().getId()) && !petIdsHasD3.contains(myOwnPetsResult.getPet().getId())) {
                viewHolder.weightStatus.setBackgroundResource(R.drawable.solid_pet_state_d3_green_bg);
            } else if (petIdsHasD2.contains(myOwnPetsResult.getPet().getId()) || petIdsHasFeeder.contains(myOwnPetsResult.getPet().getId())) {
                viewHolder.weightStatus.setBackgroundResource(R.drawable.solid_pet_state_green_bg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ((BaseApplication) this.mActivity.getApplication()).getAppComponent().imageLoader().loadImage(this.mActivity, GlideImageConfig.builder().url(myOwnPetsResult.getPet().getAvatar()).imageView(viewHolder.avatar).errorPic(myOwnPetsResult.getPet().getType().getId() == 1 ? R.drawable.default_header_dog : R.drawable.default_header_cat).transformation(new GlideCircleTransform(this.mActivity)).build());
        viewHolder.name.setText(myOwnPetsResult.getPet().getName());
        viewHolder.status.setVisibility(8);
        if (myOwnPetsResult.getPet().getStates() != null) {
            Iterator<Integer> it = myOwnPetsResult.getPet().getStates().iterator();
            while (it.hasNext()) {
                int iIntValue = it.next().intValue();
                if (iIntValue == 3) {
                    viewHolder.status.setText(R.string.Pet_state_pregnancy);
                    viewHolder.status.setVisibility(0);
                } else if (iIntValue == 4) {
                    viewHolder.status.setText(R.string.Pet_state_lactation);
                    viewHolder.status.setVisibility(0);
                }
            }
        }
        if (!TextUtils.isEmpty(myOwnPetsResult.getPet().getWeight())) {
            if (UserInforUtils.getCurrentLoginResult().getSettings().getUnit() == 1) {
                double dKgToLb = CommonUtil.KgToLb(Double.valueOf(myOwnPetsResult.getPet().getWeight()).doubleValue());
                viewHolder.weight.setText(CommonUtil.doubleToDouble(dKgToLb) + this.mActivity.getString(R.string.Unit_lb));
            } else {
                viewHolder.weight.setText(CommonUtil.doubleToDouble(Double.valueOf(myOwnPetsResult.getPet().getWeight()).doubleValue()) + this.mActivity.getString(R.string.Unit_kg));
            }
        }
        if (myOwnPetsResult.getPet().getWeightLabel() == null) {
            viewHolder.weightStatus.setVisibility(8);
        } else {
            viewHolder.weightStatus.setVisibility(8);
            viewHolder.weightStatus.setText(myOwnPetsResult.getPet().getWeightLabel());
        }
        viewHolder.food.setText(PetUtils.getPetFoodInfo(this.mActivity, myOwnPetsResult.getPet()));
        if (myOwnPetsResult.getFeed() > 0) {
            str = myOwnPetsResult.getFeed() + this.mActivity.getString(R.string.Unit_g);
        } else if (myOwnPetsResult.getRequiredEnergy() > 0) {
            str = myOwnPetsResult.getRequiredEnergy() + this.mActivity.getString(R.string.Unit_kilocalorie);
        } else {
            str = myOwnPetsResult.getFeed() + this.mActivity.getString(R.string.Unit_g);
        }
        String string = this.mActivity.getString(R.string.Pet_feed_value_format, str);
        SpannableString spannableString = new SpannableString(string);
        spannableString.setSpan(new RelativeSizeSpan(1.4f), string.indexOf(str), string.indexOf(str) + str.length(), 33);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#212D38")), string.indexOf(str), string.indexOf(str) + str.length(), 33);
        viewHolder.feed.setText(spannableString);
        return view;
    }

    public class ViewHolder {
        public ImageView avatar;
        public TextView feed;
        public TextView food;
        public TextView name;
        public TextView status;
        public TextView weight;
        public TextView weightStatus;

        public ViewHolder() {
        }
    }
}

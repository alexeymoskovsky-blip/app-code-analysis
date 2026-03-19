package com.petkit.android.activities.feed.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.jess.arms.widget.imageloader.glide.GlideCircleTransform;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.base.adapter.LoadMoreBaseAdapter;
import com.petkit.android.activities.personal.PersonalActivity;
import com.petkit.android.model.Pet;
import com.petkit.android.utils.CommonUtil;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.PetUtils;
import com.petkit.android.utils.SpannableStringUtils;
import com.petkit.oversea.R;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class FeedCompareListAdapter extends LoadMoreBaseAdapter {
    private boolean pound;

    public FeedCompareListAdapter(Activity activity, List list, boolean z) {
        super(activity, list);
        this.pound = z;
    }

    @Override // com.petkit.android.activities.base.adapter.LoadMoreBaseAdapter
    public View getEmptyFootView() {
        if (getCount() == 0) {
            View viewInflate = LayoutInflater.from(this.mActivity).inflate(R.layout.layout_list_footerview_prompt, (ViewGroup) null);
            TextView textView = (TextView) viewInflate.findViewById(R.id.footer_prompt);
            textView.setText(R.string.Health_feed_no_equal_age_pet);
            textView.setTextColor(CommonUtils.getColorById(R.color.gray));
            return viewInflate;
        }
        return super.getEmptyFootView();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.petkit.android.activities.base.adapter.LoadMoreBaseAdapter
    public View getContentView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        String weight;
        Activity activity;
        int i2;
        final Pet pet = (Pet) getItem(i);
        if (view == null || !(view.getTag() instanceof ViewHolder)) {
            view = LayoutInflater.from(this.mActivity).inflate(R.layout.adapter_feed_compare, (ViewGroup) null);
            viewHolder = new ViewHolder();
            viewHolder.avatar = (ImageView) view.findViewById(R.id.avatar);
            viewHolder.name = (TextView) view.findViewById(R.id.name);
            viewHolder.age = (TextView) view.findViewById(R.id.age);
            viewHolder.food = (TextView) view.findViewById(R.id.food);
            viewHolder.weight = (TextView) view.findViewById(R.id.weight);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        if (this.pound) {
            weight = "" + CommonUtil.doubleToDouble(CommonUtil.KgToLb(Double.valueOf(pet.getWeight()).doubleValue()));
        } else {
            weight = pet.getWeight();
        }
        SpannableStringUtils.SpanText spanText = new SpannableStringUtils.SpanText(weight, CommonUtils.getColorById(R.color.black), 2.0f);
        if (this.pound) {
            activity = this.mActivity;
            i2 = R.string.Unit_lb;
        } else {
            activity = this.mActivity;
            i2 = R.string.Unit_kilogram_short;
        }
        viewHolder.weight.setText(SpannableStringUtils.makeSpannableString(spanText, new SpannableStringUtils.SpanText(activity.getString(i2), CommonUtils.getColorById(R.color.gray), 0.8f)));
        ((BaseApplication) this.mActivity.getApplication()).getAppComponent().imageLoader().loadImage(this.mActivity, GlideImageConfig.builder().url(pet.getAvatar()).imageView(viewHolder.avatar).errorPic(pet.getType().getId() == 1 ? R.drawable.default_header_dog : R.drawable.default_header_cat).transformation(new GlideCircleTransform(this.mActivity)).build());
        viewHolder.avatar.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.feed.adapter.FeedCompareListAdapter$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.lambda$getContentView$0(pet, view2);
            }
        });
        viewHolder.name.setText(pet.getName());
        viewHolder.age.setCompoundDrawablesWithIntrinsicBounds(pet.getGender() == 1 ? R.drawable.gender_man : R.drawable.gender_women, 0, 0, 0);
        viewHolder.age.setText(CommonUtils.getSimplifyAgeByBirthday(this.mActivity, pet.getBirth()));
        viewHolder.age.setText(PetUtils.getPetFoodInfo(this.mActivity, pet));
        return view;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getContentView$0(Pet pet, View view) {
        Intent intent = new Intent(this.mActivity, (Class<?>) PersonalActivity.class);
        intent.putExtra(Constants.EXTRA_AUTHOR, pet.getOwner());
        this.mActivity.startActivity(intent);
    }

    public class ViewHolder {
        public TextView age;
        public ImageView avatar;
        public TextView food;
        public TextView name;
        public TextView weight;

        public ViewHolder() {
        }
    }
}

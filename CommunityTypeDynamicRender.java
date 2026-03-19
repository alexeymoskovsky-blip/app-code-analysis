package com.petkit.android.activities.community.adapter.render;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.jess.arms.utils.DeviceUtils;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.jess.arms.widget.imageloader.glide.GlideCircleTransform;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.jess.arms.widget.imageloader.glide.GlideRoundTransform;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.base.adapter.AdapterTypeRender;
import com.petkit.android.activities.community.adapter.CommunityListAdapter;
import com.petkit.android.activities.personal.PersonalActivity;
import com.petkit.android.model.Dynamic;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.oversea.R;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class CommunityTypeDynamicRender implements AdapterTypeRender {
    public Activity activity;
    public CommunityListAdapter communityListAdapter;
    public View contentView;
    public TextView dynamic;
    public LinearLayout dynamicLayout;
    public View dynamicLine;

    @Override // com.petkit.android.activities.base.adapter.AdapterTypeRender
    public void fitEvents() {
    }

    public CommunityTypeDynamicRender(Activity activity, CommunityListAdapter communityListAdapter) {
        this.communityListAdapter = communityListAdapter;
        this.activity = activity;
        View viewInflate = LayoutInflater.from(activity).inflate(R.layout.adapter_community_dynamic, (ViewGroup) null);
        this.contentView = viewInflate;
        this.dynamicLayout = (LinearLayout) viewInflate.findViewById(R.id.dynamic_layout);
        this.dynamic = (TextView) this.contentView.findViewById(R.id.dynamic);
        this.dynamicLine = this.contentView.findViewById(R.id.dynamic_line);
    }

    @Override // com.petkit.android.activities.base.adapter.AdapterTypeRender
    public View getConvertView() {
        return this.contentView;
    }

    @Override // com.petkit.android.activities.base.adapter.AdapterTypeRender
    public void fitDatas(int i) {
        this.dynamicLayout.removeAllViews();
        List list = (List) this.communityListAdapter.getItem(i);
        for (int i2 = 0; i2 < list.size(); i2++) {
            final Dynamic dynamic = (Dynamic) list.get(i2);
            View viewInflate = LayoutInflater.from(this.activity).inflate(R.layout.layout_dynamic_infor, (ViewGroup) null);
            ImageView imageView = (ImageView) viewInflate.findViewById(R.id.user_avatar);
            ImageView imageView2 = (ImageView) viewInflate.findViewById(R.id.user_avatar_small);
            ImageView imageView3 = (ImageView) viewInflate.findViewById(R.id.pet_avatar_small);
            TextView textView = (TextView) viewInflate.findViewById(R.id.dynamic_title);
            TextView textView2 = (TextView) viewInflate.findViewById(R.id.dynamic_time);
            View viewFindViewById = viewInflate.findViewById(R.id.dynamic_infor_line);
            if (i2 == 0) {
                viewFindViewById.setVisibility(8);
            } else {
                viewFindViewById.setVisibility(0);
            }
            textView2.setText(CommonUtils.getDisplayTimeFromDate(this.activity, dynamic.getCreatedAt()));
            if (dynamic.getPet() != null) {
                imageView.setVisibility(8);
                imageView2.setVisibility(0);
                imageView3.setVisibility(0);
                ImageLoader imageLoader = ((BaseApplication) this.activity.getApplication()).getAppComponent().imageLoader();
                Activity activity = this.activity;
                GlideImageConfig.Builder builderErrorPic = GlideImageConfig.builder().url(dynamic.getAuthor().getAvatar()).imageView(imageView2).errorPic(dynamic.getAuthor().getGender() == 1 ? R.drawable.default_user_header_m : R.drawable.default_user_header_f);
                Activity activity2 = this.activity;
                imageLoader.loadImage(activity, builderErrorPic.transformation(new GlideRoundTransform(activity2, (int) DeviceUtils.dpToPixel(activity2, 5.0f))).build());
                ((BaseApplication) this.activity.getApplication()).getAppComponent().imageLoader().loadImage(this.activity, GlideImageConfig.builder().url(dynamic.getPet().getAvatar()).imageView(imageView3).errorPic(dynamic.getPet().getType().getId() == 1 ? R.drawable.default_header_dog : R.drawable.default_header_cat).transformation(new GlideCircleTransform(CommonUtils.getAppContext())).build());
                imageView2.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.community.adapter.render.CommunityTypeDynamicRender$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$fitDatas$0(dynamic, view);
                    }
                });
            } else {
                imageView.setVisibility(0);
                imageView2.setVisibility(8);
                imageView3.setVisibility(8);
                ImageLoader imageLoader2 = ((BaseApplication) this.activity.getApplication()).getAppComponent().imageLoader();
                Activity activity3 = this.activity;
                GlideImageConfig.Builder builderErrorPic2 = GlideImageConfig.builder().url(dynamic.getAuthor().getAvatar()).imageView(imageView).errorPic(dynamic.getAuthor().getGender() == 1 ? R.drawable.default_user_header_m : R.drawable.default_user_header_f);
                Activity activity4 = this.activity;
                imageLoader2.loadImage(activity3, builderErrorPic2.transformation(new GlideRoundTransform(activity4, (int) DeviceUtils.dpToPixel(activity4, 5.0f))).build());
                imageView.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.community.adapter.render.CommunityTypeDynamicRender$$ExternalSyntheticLambda1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$fitDatas$1(dynamic, view);
                    }
                });
            }
            textView.setText(dynamic.getDescribe());
            this.dynamicLayout.addView(viewInflate);
        }
    }

    public final /* synthetic */ void lambda$fitDatas$0(Dynamic dynamic, View view) {
        Intent intent = new Intent(this.activity, (Class<?>) PersonalActivity.class);
        intent.putExtra(Constants.EXTRA_AUTHOR, dynamic.getAuthor());
        this.activity.startActivityForResult(intent, PersonalActivity.PERSONAL_FOLLOW_CHANGED_RESULT);
    }

    public final /* synthetic */ void lambda$fitDatas$1(Dynamic dynamic, View view) {
        Intent intent = new Intent(this.activity, (Class<?>) PersonalActivity.class);
        intent.putExtra(Constants.EXTRA_AUTHOR, dynamic.getAuthor());
        this.activity.startActivityForResult(intent, PersonalActivity.PERSONAL_FOLLOW_CHANGED_RESULT);
    }
}

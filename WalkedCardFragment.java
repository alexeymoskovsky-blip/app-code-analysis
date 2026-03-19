package com.petkit.android.activities.walkdog.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.github.sunnysuperman.commons.utils.CollectionUtil;
import com.jess.arms.widget.imageloader.glide.GlideCircleTransform;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.base.BaseFragment;
import com.petkit.android.activities.common.utils.DeviceCenterUtils;
import com.petkit.android.activities.go.PetListSelectActivity;
import com.petkit.android.activities.pet.PetCreateActivity;
import com.petkit.android.activities.walkdog.model.NewPoi;
import com.petkit.android.activities.walkdog.model.WalkData;
import com.petkit.android.activities.walkdog.model.WalkRecord;
import com.petkit.android.activities.walkdog.utils.WalkDataUtils;
import com.petkit.android.activities.walkdog.utils.WalkUploadInstance;
import com.petkit.android.activities.walkdog.widget.WalkMarkerListView;
import com.petkit.android.model.Pet;
import com.petkit.android.model.Poi;
import com.petkit.android.utils.CommonUtil;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.DateUtil;
import com.petkit.oversea.R;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

/* JADX INFO: loaded from: classes6.dex */
public class WalkedCardFragment extends BaseFragment {
    private WalkData mWalkData;

    @Override // com.petkit.android.activities.base.BaseFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override // com.petkit.android.activities.base.BaseFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
    }

    @Override // com.petkit.android.activities.base.BaseFragment
    public void setupViews(LayoutInflater layoutInflater) {
        setContentView(layoutInflater, R.layout.fragment_walked_card);
        setNoTitle();
        this.mWalkData = WalkDataUtils.getWalkDataById(getArguments().getLong(Constants.EXTRA_TAG_ID));
        WalkRecord walkRecord = WalkDataUtils.getWalkRecord();
        SlidingUpPanelLayout slidingUpPanelLayout = (SlidingUpPanelLayout) this.contentView.findViewById(R.id.slidingDrawer);
        slidingUpPanelLayout.setDragView(this.contentView.findViewById(R.id.drag_view));
        ((TextView) this.contentView.findViewById(R.id.go_walk_duration)).setText(CommonUtil.setSpannableStringIntegerSize(WalkDataUtils.formatWalkTime(getContext(), this.mWalkData.getDuration(), "-"), 3.0f));
        ((TextView) this.contentView.findViewById(R.id.go_time)).setText(CommonUtil.setSpannableStringIntegerSize(getString(R.string.Normal_time_to_time_format, DateUtil.getTimeShortString(getContext(), this.mWalkData.getT1()), DateUtil.getTimeShortString(getContext(), this.mWalkData.getT2())), 1.2f));
        ((TextView) this.contentView.findViewById(R.id.go_distance)).setText(CommonUtil.setSpannableStringIntegerSize(WalkDataUtils.formatDistance(getContext(), this.mWalkData.getDistance()), 1.2f));
        ((TextView) this.contentView.findViewById(R.id.go_marker)).setText(CommonUtil.setSpannableStringIntegerSize(String.valueOf(this.mWalkData.getMarkers().size()), 1.2f));
        TextView textView = (TextView) this.contentView.findViewById(R.id.go_location);
        if (this.mWalkData.getState() == 2) {
            textView.setVisibility(0);
            if (this.mWalkData.getPoi() != null) {
                textView.setText(this.mWalkData.getPoi().getName());
            } else {
                textView.setText(R.string.Hint_set_go_walk_location);
            }
            textView.setOnClickListener(this);
            walkRecord.decreaseNumberUnread(getContext());
        } else if (this.mWalkData.getPoi() != null) {
            textView.setText(this.mWalkData.getPoi().getName());
            textView.setVisibility(0);
        } else {
            textView.setVisibility(8);
        }
        initPetAvatarView();
        WalkMarkerListView walkMarkerListView = (WalkMarkerListView) this.contentView.findViewById(R.id.go_markers_view);
        walkMarkerListView.setGoWalkData(this.mWalkData);
        slidingUpPanelLayout.setScrollableView(walkMarkerListView);
        if (this.mWalkData.getMarkers() == null || this.mWalkData.getMarkers().size() != DeviceCenterUtils.getDeviceCenter().getMaxMarkPerRoute()) {
            return;
        }
        ((TextView) this.contentView.findViewById(R.id.go_markes_limit)).setText(getString(R.string.Mark_save_max, DeviceCenterUtils.getDeviceCenter().getMaxMarkPerRoute() + ""));
    }

    private void initPetAvatarView() {
        LinearLayout linearLayout = (LinearLayout) this.contentView.findViewById(R.id.ll_avatar_container);
        linearLayout.removeAllViews();
        for (Pet pet : this.mWalkData.getmPets()) {
            ImageView imageView = new ImageView(getContext());
            imageView.setBackground(getResources().getDrawable(R.drawable.circle_white_bg));
            imageView.setLayoutParams(new LinearLayout.LayoutParams((int) dip2px(40.0f), (int) dip2px(40.0f)));
            imageView.setPadding((int) dip2px(2.0f), 2, (int) dip2px(2.0f), 2);
            ((BaseApplication) CommonUtils.getAppContext()).getAppComponent().imageLoader().loadImage(CommonUtils.getAppContext(), GlideImageConfig.builder().url(pet.getAvatar()).cacheStrategy(3).imageView(imageView).placeholder(pet.getType().getId() == 1 ? R.drawable.default_header_dog : R.drawable.default_header_cat).transformation(new GlideCircleTransform(CommonUtils.getAppContext())).build());
            linearLayout.addView(imageView);
        }
    }

    @Override // com.petkit.android.activities.base.BaseFragment, android.view.View.OnClickListener
    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.dog_action_view) {
            if (CollectionUtil.isEmpty(this.mWalkData.getmPets())) {
                startActivity(new Intent(getContext(), (Class<?>) PetCreateActivity.class));
                return;
            }
            Intent intent = new Intent(getContext(), (Class<?>) PetListSelectActivity.class);
            intent.putExtra(Constants.EXTRA_DOG, this.mWalkData.getmPets().get(0));
            startActivityForResult(intent, 6290);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityResult(int i, int i2, Intent intent) {
        Pet pet;
        super.onActivityResult(i, i2, intent);
        if (i2 == -1) {
            if (i != 6289) {
                if (i == 6290 && (pet = (Pet) intent.getSerializableExtra(Constants.EXTRA_DOG)) != null) {
                    WalkData walkDataById = WalkDataUtils.getWalkDataById(this.mWalkData.getId().longValue());
                    this.mWalkData = walkDataById;
                    walkDataById.getPetIds().add(pet.getId());
                    this.mWalkData.getmPets().add(pet);
                    this.mWalkData.save();
                    ((BaseApplication) getActivity().getApplication()).getAppComponent().imageLoader().loadImage(getContext(), GlideImageConfig.builder().url(this.mWalkData.getmPets().get(0).getAvatar()).imageView((ImageView) this.contentView.findViewById(R.id.dog_avatar)).errorPic(R.drawable.default_go_walk_dog_avatar).transformation(new GlideCircleTransform(getContext())).build());
                    this.mWalkData = WalkDataUtils.getWalkDataById(this.mWalkData.getId().longValue());
                    WalkUploadInstance.getInstance().start();
                    return;
                }
                return;
            }
            Poi poi = (Poi) intent.getSerializableExtra(Constants.EXTRA_LOCATION_POI);
            TextView textView = (TextView) this.contentView.findViewById(R.id.go_location);
            if (poi != null) {
                NewPoi newPoi = new NewPoi();
                newPoi.setLocationString(poi.getPoiLocation());
                newPoi.setName(poi.getPoiName());
                WalkData walkDataById2 = WalkDataUtils.getWalkDataById(this.mWalkData.getId().longValue());
                this.mWalkData = walkDataById2;
                walkDataById2.setPoi(newPoi);
                if (this.mWalkData.getState() == 4) {
                    this.mWalkData.setState(6);
                }
                this.mWalkData.save();
                this.mWalkData = WalkDataUtils.getWalkDataById(this.mWalkData.getId().longValue());
                WalkUploadInstance.getInstance().start();
                textView.setText(newPoi.getName());
            }
        }
    }
}

package com.petkit.android.activities.go.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.jess.arms.widget.imageloader.glide.GlideCircleTransform;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.base.BaseFragment;
import com.petkit.android.activities.common.utils.DeviceCenterUtils;
import com.petkit.android.activities.go.PetListSelectActivity;
import com.petkit.android.activities.go.model.GoRecord;
import com.petkit.android.activities.go.model.GoWalkData;
import com.petkit.android.activities.go.model.NewPoi;
import com.petkit.android.activities.go.utils.GoDataUtils;
import com.petkit.android.activities.go.utils.GoWalkUploadInstance;
import com.petkit.android.activities.go.widget.GoMarkerListView;
import com.petkit.android.activities.pet.PetCreateActivity;
import com.petkit.android.model.Pet;
import com.petkit.android.model.Poi;
import com.petkit.android.utils.CommonUtil;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.DateUtil;
import com.petkit.oversea.R;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

/* JADX INFO: loaded from: classes3.dex */
public class GoWalkedCardFragment extends BaseFragment {
    private BroadcastReceiver mBroadcastReceiver;
    private GoWalkData mGoWalkData;

    @Override // com.petkit.android.activities.base.BaseFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        registerBoradcastReceiver();
    }

    @Override // com.petkit.android.activities.base.BaseFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        unregisterBroadcastReceiver();
    }

    @Override // com.petkit.android.activities.base.BaseFragment
    public void setupViews(LayoutInflater layoutInflater) {
        setContentView(layoutInflater, R.layout.fragment_go_walked_card);
        setNoTitle();
        GoWalkData goWalkDataById = GoDataUtils.getGoWalkDataById(getArguments().getLong(Constants.EXTRA_TAG_ID));
        this.mGoWalkData = goWalkDataById;
        GoRecord goRecordById = GoDataUtils.getGoRecordById(goWalkDataById.getDeviceId());
        if (goRecordById == null) {
            return;
        }
        SlidingUpPanelLayout slidingUpPanelLayout = (SlidingUpPanelLayout) this.contentView.findViewById(R.id.slidingDrawer);
        slidingUpPanelLayout.setDragView(this.contentView.findViewById(R.id.drag_view));
        ((TextView) this.contentView.findViewById(R.id.go_walk_duration)).setText(CommonUtil.setSpannableStringIntegerSize(GoDataUtils.formatGoTime(getContext(), this.mGoWalkData.getDuration(), "-"), 3.0f));
        ((TextView) this.contentView.findViewById(R.id.go_time)).setText(getString(R.string.Normal_time_to_time_format, DateUtil.getTimeShortString(getContext(), this.mGoWalkData.getT1()), DateUtil.getTimeShortString(getContext(), this.mGoWalkData.getT2())));
        ((TextView) this.contentView.findViewById(R.id.go_distance)).setText(CommonUtil.setSpannableStringIntegerSize(GoDataUtils.formatDistance(getContext(), this.mGoWalkData.getDistance()), 1.5f));
        ((TextView) this.contentView.findViewById(R.id.go_marker)).setText(CommonUtil.setSpannableStringIntegerSize(String.valueOf(this.mGoWalkData.getMarkers().size()), 1.5f));
        TextView textView = (TextView) this.contentView.findViewById(R.id.go_location);
        ImageView imageView = (ImageView) this.contentView.findViewById(R.id.dog_avatar);
        ImageView imageView2 = (ImageView) this.contentView.findViewById(R.id.dog_action);
        if (this.mGoWalkData.getState() == 2) {
            textView.setVisibility(0);
            if (this.mGoWalkData.getPoi() != null) {
                textView.setText(this.mGoWalkData.getPoi().getName());
            } else {
                textView.setText(R.string.Hint_set_go_walk_location);
            }
            textView.setOnClickListener(this);
            imageView2.setVisibility(0);
            this.contentView.findViewById(R.id.dog_action_view).setVisibility(0);
            if (this.mGoWalkData.getPet() == null) {
                imageView2.setImageResource(R.drawable.go_walk_dog_add);
                imageView.setImageResource(R.drawable.default_go_walk_dog_avatar);
            } else {
                imageView2.setImageResource(R.drawable.go_walk_dog_change);
                ((BaseApplication) getActivity().getApplication()).getAppComponent().imageLoader().loadImage(getContext(), GlideImageConfig.builder().url(this.mGoWalkData.getPet().getAvatar()).imageView(imageView).errorPic(R.drawable.default_go_walk_dog_avatar).transformation(new GlideCircleTransform(getContext())).build());
            }
            this.contentView.findViewById(R.id.dog_action_view).setOnClickListener(this);
            goRecordById.decreaseNumberUnread(getContext());
        } else {
            if (this.mGoWalkData.getPoi() != null) {
                textView.setText(this.mGoWalkData.getPoi().getName());
                textView.setVisibility(0);
            } else {
                textView.setVisibility(8);
            }
            imageView2.setVisibility(8);
            if (this.mGoWalkData.getPet() != null) {
                ((BaseApplication) getActivity().getApplication()).getAppComponent().imageLoader().loadImage(getContext(), GlideImageConfig.builder().url(this.mGoWalkData.getPet().getAvatar()).imageView(imageView).errorPic(R.drawable.default_go_walk_dog_avatar).transformation(new GlideCircleTransform(getContext())).build());
                this.contentView.findViewById(R.id.dog_action_view).setVisibility(0);
            } else {
                this.contentView.findViewById(R.id.dog_action_view).setVisibility(8);
            }
            this.contentView.findViewById(R.id.dog_action_view).setOnClickListener(null);
        }
        GoMarkerListView goMarkerListView = (GoMarkerListView) this.contentView.findViewById(R.id.go_markers_view);
        goMarkerListView.setGoWalkData(this.mGoWalkData);
        slidingUpPanelLayout.setScrollableView(goMarkerListView);
        if (this.mGoWalkData.getMarkers() == null || this.mGoWalkData.getMarkers().size() != DeviceCenterUtils.getDeviceCenter().getMaxMarkPerRoute()) {
            return;
        }
        ((TextView) this.contentView.findViewById(R.id.go_markes_limit)).setText(getString(R.string.Mark_save_max, DeviceCenterUtils.getDeviceCenter().getMaxMarkPerRoute() + ""));
    }

    @Override // com.petkit.android.activities.base.BaseFragment, android.view.View.OnClickListener
    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.dog_action_view) {
            if (this.mGoWalkData.getPet() == null) {
                startActivity(new Intent(getContext(), (Class<?>) PetCreateActivity.class));
                return;
            }
            Intent intent = new Intent(getContext(), (Class<?>) PetListSelectActivity.class);
            intent.putExtra(Constants.EXTRA_DOG, this.mGoWalkData.getPet());
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
                    GoWalkData goWalkDataById = GoDataUtils.getGoWalkDataById(this.mGoWalkData.getId().longValue());
                    this.mGoWalkData = goWalkDataById;
                    goWalkDataById.setPetId(pet.getId());
                    this.mGoWalkData.setPet(pet);
                    this.mGoWalkData.save();
                    ((BaseApplication) getActivity().getApplication()).getAppComponent().imageLoader().loadImage(getContext(), GlideImageConfig.builder().url(this.mGoWalkData.getPet().getAvatar()).imageView((ImageView) this.contentView.findViewById(R.id.dog_avatar)).errorPic(R.drawable.default_go_walk_dog_avatar).transformation(new GlideCircleTransform(getContext())).build());
                    this.mGoWalkData = GoDataUtils.getGoWalkDataById(this.mGoWalkData.getId().longValue());
                    GoWalkUploadInstance.getInstance().start();
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
                GoWalkData goWalkDataById2 = GoDataUtils.getGoWalkDataById(this.mGoWalkData.getId().longValue());
                this.mGoWalkData = goWalkDataById2;
                goWalkDataById2.setPoi(newPoi);
                if (this.mGoWalkData.getState() == 4) {
                    this.mGoWalkData.setState(6);
                }
                this.mGoWalkData.save();
                this.mGoWalkData = GoDataUtils.getGoWalkDataById(this.mGoWalkData.getId().longValue());
                GoWalkUploadInstance.getInstance().start();
                textView.setText(newPoi.getName());
                textView.append(">");
            }
        }
    }

    private void registerBoradcastReceiver() {
        this.mBroadcastReceiver = new BroadcastReceiver() { // from class: com.petkit.android.activities.go.fragment.GoWalkedCardFragment.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                Pet defaultPetForGoWalkData;
                if (GoWalkedCardFragment.this.mGoWalkData == null || GoDataUtils.getGoWalkDataById(GoWalkedCardFragment.this.mGoWalkData.getId().longValue()) == null) {
                    return;
                }
                GoWalkedCardFragment goWalkedCardFragment = GoWalkedCardFragment.this;
                goWalkedCardFragment.mGoWalkData = GoDataUtils.getGoWalkDataById(goWalkedCardFragment.mGoWalkData.getId().longValue());
                String action = intent.getAction();
                action.hashCode();
                if (action.equals(Constants.BROADCAST_MSG_ADD_DOG_COMPLETE) && (defaultPetForGoWalkData = GoDataUtils.getDefaultPetForGoWalkData(GoWalkedCardFragment.this.mGoWalkData)) != null) {
                    GoWalkedCardFragment.this.mGoWalkData.setPet(defaultPetForGoWalkData);
                    GoWalkedCardFragment.this.mGoWalkData.setPetId(defaultPetForGoWalkData.getId());
                    GoWalkedCardFragment.this.mGoWalkData.save();
                    GoWalkedCardFragment goWalkedCardFragment2 = GoWalkedCardFragment.this;
                    goWalkedCardFragment2.mGoWalkData = GoDataUtils.getGoWalkDataById(goWalkedCardFragment2.mGoWalkData.getId().longValue());
                    ((BaseApplication) GoWalkedCardFragment.this.getActivity().getApplication()).getAppComponent().imageLoader().loadImage(GoWalkedCardFragment.this.getContext(), GlideImageConfig.builder().url(GoWalkedCardFragment.this.mGoWalkData.getPet().getAvatar()).imageView((ImageView) ((BaseFragment) GoWalkedCardFragment.this).contentView.findViewById(R.id.dog_avatar)).errorPic(R.drawable.default_go_walk_dog_avatar).transformation(new GlideCircleTransform(GoWalkedCardFragment.this.getContext())).build());
                    ((ImageView) ((BaseFragment) GoWalkedCardFragment.this).contentView.findViewById(R.id.dog_action)).setImageResource(R.drawable.go_walk_dog_change);
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.BROADCAST_MSG_ADD_DOG_COMPLETE);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(this.mBroadcastReceiver, intentFilter);
    }

    private void unregisterBroadcastReceiver() {
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(this.mBroadcastReceiver);
    }
}

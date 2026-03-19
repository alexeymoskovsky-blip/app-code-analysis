package com.petkit.android.activities.walkdog.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.github.sunnysuperman.commons.utils.CollectionUtil;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.widget.imageloader.glide.GlideCircleTransform;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.base.adapter.LoadMoreBaseAdapter;
import com.petkit.android.activities.walkdog.model.WalkData;
import com.petkit.android.activities.walkdog.utils.WalkDataUtils;
import com.petkit.android.utils.CommonUtil;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.DateUtil;
import com.petkit.oversea.R;
import java.util.List;

/* JADX INFO: loaded from: classes6.dex */
public class WalkListAdapter extends LoadMoreBaseAdapter {
    public WalkListAdapter(Activity activity, List list) {
        super(activity, list);
    }

    @Override // com.petkit.android.activities.base.adapter.LoadMoreBaseAdapter
    public View getEmptyFootView() {
        if (this.mList.size() == 0) {
            View viewInflate = LayoutInflater.from(this.mActivity).inflate(R.layout.layout_list_empty, (ViewGroup) null);
            TextView textView = (TextView) viewInflate.findViewById(R.id.list_empty_text);
            textView.setVisibility(0);
            textView.setText(R.string.Go_walk_list_emty);
            textView.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.go_walk_null_icon2, 0, 0);
            ((ImageView) viewInflate.findViewById(R.id.list_empty_image)).setVisibility(8);
            viewInflate.setBackgroundColor(ArmsUtils.getColor(this.mActivity, R.color.transparent));
            return viewInflate;
        }
        return super.getEmptyFootView();
    }

    public long getOldestTimeindex() {
        if (this.mList.size() == 0) {
            return System.currentTimeMillis();
        }
        return ((WalkData) this.mList.get(r0.size() - 1)).getTimeindex();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.petkit.android.activities.base.adapter.LoadMoreBaseAdapter
    public View getContentView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null || !(view.getTag() instanceof ViewHolder)) {
            view = LayoutInflater.from(this.mActivity).inflate(R.layout.adapter_walk_list, (ViewGroup) null);
            viewHolder = new ViewHolder();
            viewHolder.duration = (TextView) view.findViewById(R.id.go_duration);
            viewHolder.time = (TextView) view.findViewById(R.id.go_time);
            viewHolder.distance = (TextView) view.findViewById(R.id.go_distance);
            viewHolder.location = (TextView) view.findViewById(R.id.go_location);
            viewHolder.image = (ImageView) view.findViewById(R.id.go_map_image);
            viewHolder.markers = (TextView) view.findViewById(R.id.go_markers);
            viewHolder.tvCount = (TextView) view.findViewById(R.id.tv_count);
            viewHolder.imgPet = (ImageView) view.findViewById(R.id.img_pet);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        WalkData walkData = (WalkData) getItem(i);
        if (walkData.hasPoints()) {
            ((BaseApplication) this.mActivity.getApplication()).getAppComponent().imageLoader().loadImage(this.mActivity, GlideImageConfig.builder().url(walkData.getImage()).errorPic(R.drawable.default_go_walk_map).transformation(new GlideCircleTransform(this.mActivity)).imageView(viewHolder.image).build());
        } else {
            ((BaseApplication) this.mActivity.getApplication()).getAppComponent().imageLoader().loadImage(this.mActivity, GlideImageConfig.builder().url(walkData.getImage()).errorPic(R.drawable.go_walk_map_null).transformation(new GlideCircleTransform(this.mActivity)).imageView(viewHolder.image).build());
        }
        if (walkData.getPoi() != null) {
            viewHolder.location.setText(walkData.getPoi().getName());
            viewHolder.location.setVisibility(0);
        } else {
            viewHolder.location.setVisibility(8);
        }
        if (walkData.getMarkers().size() > 0) {
            viewHolder.markers.setText(this.mActivity.getString(R.string.Markers_number_format, walkData.getMarkers().size() + ""));
            viewHolder.markers.setVisibility(0);
        } else {
            viewHolder.markers.setVisibility(8);
        }
        if (walkData.getPetIds().size() > 1) {
            viewHolder.tvCount.setVisibility(0);
            viewHolder.tvCount.setText(walkData.getPetIds().size() + "");
        } else {
            viewHolder.tvCount.setVisibility(8);
        }
        viewHolder.imgPet.setVisibility(0);
        if (!CollectionUtil.isEmpty(walkData.getmPets())) {
            ((BaseApplication) CommonUtils.getAppContext()).getAppComponent().imageLoader().loadImage(CommonUtils.getAppContext(), GlideImageConfig.builder().url(walkData.getmPets().get(0).getAvatar()).imageView(viewHolder.imgPet).placeholder(R.drawable.default_header_dog).errorPic(R.drawable.default_header_dog).transformation(new GlideCircleTransform(CommonUtils.getAppContext())).build());
        } else {
            Glide.with(this.mActivity).load(Integer.valueOf(R.drawable.default_header_dog)).transform(new GlideCircleTransform(this.mActivity)).into(viewHolder.imgPet);
        }
        viewHolder.duration.setText(DateUtil.getTimeShortString(this.mActivity, walkData.getT1()));
        viewHolder.time.setText(CommonUtil.setSpannableStringIntegerSize(WalkDataUtils.formatWalkTime(this.mActivity, walkData.getDuration(), "- -"), 1.5f));
        viewHolder.distance.setText(CommonUtil.setSpannableStringIntegerSize(WalkDataUtils.formatDistance(this.mActivity, walkData.getDistance(), ""), 1.5f));
        return view;
    }

    public class ViewHolder {
        public TextView distance;
        public TextView duration;
        public ImageView image;
        public ImageView imgPet;
        public TextView location;
        public TextView markers;
        public TextView time;
        public TextView tvCount;

        public ViewHolder() {
        }
    }
}

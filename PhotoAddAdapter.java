package com.petkit.android.activities.community.adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.jess.arms.utils.DeviceUtils;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.community.localphoto.PhotoInfo;
import com.petkit.android.utils.CommonUtils;
import com.petkit.oversea.R;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class PhotoAddAdapter extends BaseAdapter {
    private int container_length;
    private Context context;
    private List<PhotoInfo> hasList;
    private int image_length;
    private int mMaxPhotoCount;
    private int widthPixels;

    public static class ViewHolder {
        public RelativeLayout addImageLayout;
        public RelativeLayout container;
        public ImageView deleteView;
        public ImageView imageView;
    }

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    public PhotoAddAdapter(Context context, List<PhotoInfo> list, int i) {
        this.image_length = 0;
        this.container_length = 0;
        this.mMaxPhotoCount = 9;
        int i2 = BaseApplication.displayMetrics.widthPixels;
        this.widthPixels = i2;
        int iDpToPixel = (int) ((i2 - (DeviceUtils.dpToPixel(context, 6.0f) * 5.0f)) / 4.0f);
        this.container_length = iDpToPixel;
        this.image_length = iDpToPixel;
        this.context = context;
        this.hasList = list;
        this.mMaxPhotoCount = i;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        List<PhotoInfo> list = this.hasList;
        if (list == null || list.size() == 0) {
            return 1;
        }
        int size = this.hasList.size();
        int i = this.mMaxPhotoCount;
        return size == i ? i : this.hasList.size() + 1;
    }

    @Override // android.widget.Adapter
    public Object getItem(int i) {
        List<PhotoInfo> list = this.hasList;
        if (list == null || i == list.size()) {
            return null;
        }
        return this.hasList.get(i);
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder viewHolder = new ViewHolder();
        View viewInflate = LayoutInflater.from(this.context).inflate(R.layout.adapter_add_image_view, (ViewGroup) null);
        viewHolder.container = (RelativeLayout) viewInflate.findViewById(R.id.container_layout);
        viewHolder.addImageLayout = (RelativeLayout) viewInflate.findViewById(R.id.add_image_layout);
        viewHolder.deleteView = (ImageView) viewInflate.findViewById(R.id.delete_view);
        viewHolder.imageView = (ImageView) viewInflate.findViewById(R.id.image_view);
        RelativeLayout relativeLayout = viewHolder.container;
        int i2 = this.container_length;
        relativeLayout.setLayoutParams(new FrameLayout.LayoutParams(i2, i2));
        viewInflate.setTag(viewHolder);
        PhotoInfo photoInfo = (PhotoInfo) getItem(i);
        if (photoInfo == null) {
            viewHolder.deleteView.setVisibility(8);
            int i3 = this.image_length;
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(i3, i3);
            layoutParams.addRule(13);
            viewHolder.addImageLayout.setLayoutParams(layoutParams);
            viewHolder.addImageLayout.setBackgroundResource(R.drawable.solid_gray_bg);
            ((GradientDrawable) viewHolder.addImageLayout.getBackground()).setColor(CommonUtils.getColorById(R.color.add_image_bg));
            RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-2, -2);
            layoutParams2.addRule(13);
            viewHolder.imageView.setLayoutParams(layoutParams2);
            viewHolder.imageView.setImageResource(R.drawable.default_image_add);
        } else {
            viewHolder.addImageLayout.setBackgroundColor(CommonUtils.getColorById(R.color.white));
            viewHolder.deleteView.setVisibility(0);
            int i4 = this.image_length;
            RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(i4, i4);
            viewHolder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            viewHolder.imageView.setLayoutParams(layoutParams3);
            Glide.with(CommonUtils.getAppContext()).load(photoInfo.getPath_file()).error(R.drawable.default_image).into(new SimpleTarget<GlideDrawable>() { // from class: com.petkit.android.activities.community.adapter.PhotoAddAdapter.1
                @Override // com.bumptech.glide.request.target.Target
                public /* bridge */ /* synthetic */ void onResourceReady(Object obj, GlideAnimation glideAnimation) {
                    onResourceReady((GlideDrawable) obj, (GlideAnimation<? super GlideDrawable>) glideAnimation);
                }

                public void onResourceReady(GlideDrawable glideDrawable, GlideAnimation<? super GlideDrawable> glideAnimation) {
                    viewHolder.imageView.setImageDrawable(glideDrawable);
                }
            });
        }
        return viewInflate;
    }
}

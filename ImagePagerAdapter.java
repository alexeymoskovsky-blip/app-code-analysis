package com.petkit.android.activities.home.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.viewpager.widget.PagerAdapter;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.community.PostDetailActivity;
import com.petkit.android.activities.community.PostDetailLinkActivity;
import com.petkit.android.activities.community.TopicDetailListActivity;
import com.petkit.android.activities.community.WebviewActivity;
import com.petkit.android.model.UserBanner;
import com.petkit.android.utils.Constants;
import com.petkit.oversea.R;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class ImagePagerAdapter extends PagerAdapter {
    public boolean isSlide;
    public Activity mActivity;
    public List<UserBanner> mList;

    @Override // androidx.viewpager.widget.PagerAdapter
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }

    public ImagePagerAdapter(Activity activity, List<UserBanner> list) {
        new ArrayList();
        this.isSlide = false;
        this.mActivity = activity;
        this.mList = list;
    }

    public void setList(List<UserBanner> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public int getCount() {
        return this.mList.size();
    }

    public int getPagerSize() {
        List<UserBanner> list = this.mList;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public UserBanner getItem(int i) {
        List<UserBanner> list = this.mList;
        if (list == null || list.size() == 0) {
            return null;
        }
        return this.mList.get(i);
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public View instantiateItem(ViewGroup viewGroup, int i) {
        final View viewInflate = LayoutInflater.from(this.mActivity).inflate(R.layout.adapter_binder_view, (ViewGroup) null);
        UserBanner item = getItem(i % getPagerSize());
        if (item != null) {
            ((BaseApplication) this.mActivity.getApplication()).getAppComponent().imageLoader().loadImage(this.mActivity, GlideImageConfig.builder().url(item.getImg()).imageView((ImageView) viewInflate.findViewById(R.id.imageView)).errorPic(R.drawable.default_image_middle).listener(new RequestListener<String, Bitmap>() { // from class: com.petkit.android.activities.home.adapter.ImagePagerAdapter.1
                @Override // com.bumptech.glide.request.RequestListener
                public boolean onException(Exception exc, String str, Target<Bitmap> target, boolean z) {
                    viewInflate.findViewById(R.id.scale_loading).setVisibility(8);
                    return false;
                }

                @Override // com.bumptech.glide.request.RequestListener
                public boolean onResourceReady(Bitmap bitmap, String str, Target<Bitmap> target, boolean z, boolean z2) {
                    viewInflate.findViewById(R.id.scale_loading).setVisibility(8);
                    return false;
                }
            }).build());
        }
        viewInflate.setTag(item);
        viewInflate.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.home.adapter.ImagePagerAdapter.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                UserBanner userBanner = (UserBanner) view.getTag();
                if (userBanner != null) {
                    int type = userBanner.getType();
                    if (type == 1) {
                        Intent intent = new Intent();
                        if (userBanner.getPostType() == 3) {
                            intent.setClass(ImagePagerAdapter.this.mActivity, PostDetailLinkActivity.class);
                        } else {
                            intent.setClass(ImagePagerAdapter.this.mActivity, PostDetailActivity.class);
                        }
                        intent.putExtra(Constants.EXTRA_POST_ID, userBanner.getPost());
                        ImagePagerAdapter.this.mActivity.startActivity(intent);
                        return;
                    }
                    if (type == 2) {
                        ImagePagerAdapter.this.mActivity.startActivity(WebviewActivity.newIntent(ImagePagerAdapter.this.mActivity, "", userBanner.getLink()));
                    } else {
                        if (type != 3) {
                            return;
                        }
                        Intent intent2 = new Intent(ImagePagerAdapter.this.mActivity, (Class<?>) TopicDetailListActivity.class);
                        intent2.putExtra(Constants.EXTRA_STRING_ID, userBanner.getTopicId());
                        ImagePagerAdapter.this.mActivity.startActivity(intent2);
                    }
                }
            }
        });
        viewGroup.addView(viewInflate, 0);
        return viewInflate;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        viewGroup.removeView((View) obj);
    }
}

package com.petkit.android.activities.community.adapter.render;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.base.adapter.AdapterTypeRender;
import com.petkit.android.activities.community.PostDetailActivity;
import com.petkit.android.activities.community.PostDetailLinkActivity;
import com.petkit.android.activities.community.TopicDetailListActivity;
import com.petkit.android.activities.community.TopicsNewListActivity;
import com.petkit.android.activities.community.adapter.CommunityListAdapter;
import com.petkit.android.activities.petkitBleDevice.AdWebViewActivity;
import com.petkit.android.model.UserBanner;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.oversea.R;
import com.viewpagerindicator.CirclePageIndicator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class CommunityTypeBannerRender implements AdapterTypeRender, View.OnClickListener, Runnable {
    private static final int POST_DELAY_TIME = 5000;
    private Activity activity;
    private CommunityListAdapter communityListAdapterNew;
    private View contentView;
    private CirclePageIndicator indicator;
    private ImagePagerAdapter mImageAdapter;
    private ViewPager mImagepager;
    private boolean autoChange = true;
    private long lastChangeTime = 0;
    private boolean isSlide = true;
    private float ratio = 1.7777778f;

    private void goToPetkitStore() {
    }

    @SuppressLint({"InflateParams"})
    public CommunityTypeBannerRender(Activity activity, CommunityListAdapter communityListAdapter) {
        this.activity = activity;
        this.communityListAdapterNew = communityListAdapter;
        initView();
    }

    @SuppressLint({"InflateParams"})
    private void initView() {
        View viewInflate = LayoutInflater.from(this.activity).inflate(R.layout.activity_community_follow_header, (ViewGroup) null);
        this.contentView = viewInflate;
        ViewPager viewPager = (ViewPager) viewInflate.findViewById(R.id.discover_pager);
        this.mImagepager = viewPager;
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) viewPager.getLayoutParams();
        layoutParams.height = (int) (BaseApplication.displayMetrics.widthPixels / this.ratio);
        this.mImagepager.setLayoutParams(layoutParams);
        CirclePageIndicator circlePageIndicator = (CirclePageIndicator) this.contentView.findViewById(R.id.indicator);
        this.indicator = circlePageIndicator;
        circlePageIndicator.setPageColor(Color.parseColor("#66ffffff"));
        this.indicator.setFillColor(CommonUtils.getColorById(R.color.white));
        this.indicator.setIndicatorStyle(1);
    }

    @Override // com.petkit.android.activities.base.adapter.AdapterTypeRender
    public View getConvertView() {
        return this.contentView;
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.community.adapter.render.CommunityTypeBannerRender$1 */
    public class AnonymousClass1 implements ViewPager.OnPageChangeListener {
        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrollStateChanged(int i) {
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrolled(int i, float f, int i2) {
        }

        public AnonymousClass1() {
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageSelected(int i) {
            if (CommunityTypeBannerRender.this.autoChange) {
                long jCurrentTimeMillis = System.currentTimeMillis();
                if (jCurrentTimeMillis - CommunityTypeBannerRender.this.lastChangeTime < 5000) {
                    CommunityTypeBannerRender.this.autoChange = false;
                }
                CommunityTypeBannerRender.this.lastChangeTime = jCurrentTimeMillis;
            }
        }
    }

    @Override // com.petkit.android.activities.base.adapter.AdapterTypeRender
    public void fitEvents() {
        this.indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.petkit.android.activities.community.adapter.render.CommunityTypeBannerRender.1
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i, float f, int i2) {
            }

            public AnonymousClass1() {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int i) {
                if (CommunityTypeBannerRender.this.autoChange) {
                    long jCurrentTimeMillis = System.currentTimeMillis();
                    if (jCurrentTimeMillis - CommunityTypeBannerRender.this.lastChangeTime < 5000) {
                        CommunityTypeBannerRender.this.autoChange = false;
                    }
                    CommunityTypeBannerRender.this.lastChangeTime = jCurrentTimeMillis;
                }
            }
        });
        this.indicator.setSnap(true);
        if (this.isSlide) {
            this.mImagepager.setCurrentItem(500);
            this.mImagepager.postDelayed(this, 5000L);
        }
    }

    @Override // com.petkit.android.activities.base.adapter.AdapterTypeRender
    public void fitDatas(int i) {
        List<UserBanner> list = (List) this.communityListAdapterNew.getItem(i);
        this.contentView.findViewById(R.id.banner_layout).setVisibility(0);
        this.isSlide = list.size() != 1;
        ImagePagerAdapter imagePagerAdapter = this.mImageAdapter;
        if (imagePagerAdapter == null) {
            ImagePagerAdapter imagePagerAdapter2 = new ImagePagerAdapter(list);
            this.mImageAdapter = imagePagerAdapter2;
            this.mImagepager.setAdapter(imagePagerAdapter2);
            this.indicator.setViewPager(this.mImagepager, 0, this.mImageAdapter.getPagerSize());
        } else {
            imagePagerAdapter.setList(list);
        }
        if (this.autoChange || !this.isSlide) {
            return;
        }
        this.autoChange = true;
        this.mImagepager.postDelayed(this, 5000L);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.follow_header_topic_hot) {
            goToTopic();
        } else if (id == R.id.follow_header_petkit_store) {
            goToPetkitStore();
        }
    }

    private void goToTopic() {
        this.activity.startActivity(new Intent(this.activity, (Class<?>) TopicsNewListActivity.class));
    }

    @Override // java.lang.Runnable
    public void run() {
        ImagePagerAdapter imagePagerAdapter = this.mImageAdapter;
        if (imagePagerAdapter == null || imagePagerAdapter.getCount() <= 0 || !this.autoChange) {
            return;
        }
        ViewPager viewPager = this.mImagepager;
        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
        this.mImagepager.postDelayed(this, 5000L);
    }

    public class ImagePagerAdapter extends PagerAdapter {
        public List<UserBanner> mList;

        @Override // androidx.viewpager.widget.PagerAdapter
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        public ImagePagerAdapter(List<UserBanner> list) {
            new ArrayList();
            this.mList = list;
        }

        public void setList(List<UserBanner> list) {
            this.mList = list;
            notifyDataSetChanged();
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public int getCount() {
            return CommunityTypeBannerRender.this.isSlide ? Integer.MAX_VALUE : 1;
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
            View viewInflate = LayoutInflater.from(CommunityTypeBannerRender.this.activity).inflate(R.layout.adapter_binder_view, (ViewGroup) null);
            UserBanner item = getItem(i % getPagerSize());
            if (item != null) {
                ((BaseApplication) CommunityTypeBannerRender.this.activity.getApplication()).getAppComponent().imageLoader().loadImage(CommunityTypeBannerRender.this.activity, GlideImageConfig.builder().url(item.getImg()).imageView((ImageView) viewInflate.findViewById(R.id.imageView)).errorPic(R.drawable.default_image_middle).listener(new RequestListener<String, Bitmap>() { // from class: com.petkit.android.activities.community.adapter.render.CommunityTypeBannerRender.ImagePagerAdapter.1
                    public final /* synthetic */ View val$view;

                    public AnonymousClass1(View viewInflate2) {
                        view = viewInflate2;
                    }

                    @Override // com.bumptech.glide.request.RequestListener
                    public boolean onException(Exception exc, String str, Target<Bitmap> target, boolean z) {
                        view.findViewById(R.id.scale_loading).setVisibility(8);
                        return false;
                    }

                    @Override // com.bumptech.glide.request.RequestListener
                    public boolean onResourceReady(Bitmap bitmap, String str, Target<Bitmap> target, boolean z, boolean z2) {
                        view.findViewById(R.id.scale_loading).setVisibility(8);
                        return false;
                    }
                }).build());
            }
            viewInflate2.setTag(item);
            viewInflate2.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.community.adapter.render.CommunityTypeBannerRender.ImagePagerAdapter.2
                public AnonymousClass2() {
                }

                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    UserBanner userBanner = (UserBanner) view.getTag();
                    if (userBanner != null) {
                        new HashMap().put("type", userBanner.getId());
                        int type = userBanner.getType();
                        if (type == 1) {
                            Intent intent = new Intent();
                            if (userBanner.getPostType() == 3) {
                                intent.setClass(CommunityTypeBannerRender.this.activity, PostDetailLinkActivity.class);
                            } else {
                                intent.setClass(CommunityTypeBannerRender.this.activity, PostDetailActivity.class);
                            }
                            intent.putExtra(Constants.EXTRA_POST_ID, userBanner.getPost());
                            CommunityTypeBannerRender.this.activity.startActivity(intent);
                            return;
                        }
                        if (type == 2) {
                            CommunityTypeBannerRender.this.activity.startActivity(AdWebViewActivity.newIntent(CommunityTypeBannerRender.this.activity, "", userBanner.getLink()));
                        } else {
                            if (type != 3) {
                                return;
                            }
                            Intent intent2 = new Intent(CommunityTypeBannerRender.this.activity, (Class<?>) TopicDetailListActivity.class);
                            intent2.putExtra(Constants.EXTRA_STRING_ID, userBanner.getTopicId());
                            CommunityTypeBannerRender.this.activity.startActivity(intent2);
                        }
                    }
                }
            });
            viewGroup.addView(viewInflate2, 0);
            return viewInflate2;
        }

        /* JADX INFO: renamed from: com.petkit.android.activities.community.adapter.render.CommunityTypeBannerRender$ImagePagerAdapter$1 */
        public class AnonymousClass1 implements RequestListener<String, Bitmap> {
            public final /* synthetic */ View val$view;

            public AnonymousClass1(View viewInflate2) {
                view = viewInflate2;
            }

            @Override // com.bumptech.glide.request.RequestListener
            public boolean onException(Exception exc, String str, Target<Bitmap> target, boolean z) {
                view.findViewById(R.id.scale_loading).setVisibility(8);
                return false;
            }

            @Override // com.bumptech.glide.request.RequestListener
            public boolean onResourceReady(Bitmap bitmap, String str, Target<Bitmap> target, boolean z, boolean z2) {
                view.findViewById(R.id.scale_loading).setVisibility(8);
                return false;
            }
        }

        /* JADX INFO: renamed from: com.petkit.android.activities.community.adapter.render.CommunityTypeBannerRender$ImagePagerAdapter$2 */
        public class AnonymousClass2 implements View.OnClickListener {
            public AnonymousClass2() {
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                UserBanner userBanner = (UserBanner) view.getTag();
                if (userBanner != null) {
                    new HashMap().put("type", userBanner.getId());
                    int type = userBanner.getType();
                    if (type == 1) {
                        Intent intent = new Intent();
                        if (userBanner.getPostType() == 3) {
                            intent.setClass(CommunityTypeBannerRender.this.activity, PostDetailLinkActivity.class);
                        } else {
                            intent.setClass(CommunityTypeBannerRender.this.activity, PostDetailActivity.class);
                        }
                        intent.putExtra(Constants.EXTRA_POST_ID, userBanner.getPost());
                        CommunityTypeBannerRender.this.activity.startActivity(intent);
                        return;
                    }
                    if (type == 2) {
                        CommunityTypeBannerRender.this.activity.startActivity(AdWebViewActivity.newIntent(CommunityTypeBannerRender.this.activity, "", userBanner.getLink()));
                    } else {
                        if (type != 3) {
                            return;
                        }
                        Intent intent2 = new Intent(CommunityTypeBannerRender.this.activity, (Class<?>) TopicDetailListActivity.class);
                        intent2.putExtra(Constants.EXTRA_STRING_ID, userBanner.getTopicId());
                        CommunityTypeBannerRender.this.activity.startActivity(intent2);
                    }
                }
            }
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
            viewGroup.removeView((View) obj);
        }
    }
}

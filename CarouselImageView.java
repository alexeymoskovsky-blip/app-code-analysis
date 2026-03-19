package com.petkit.android.activities.mall.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.viewpager.widget.ViewPager;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.widget.InterceptViewPager;
import com.petkit.android.activities.mall.adapter.CarouselImagePageAdapter;
import com.petkit.android.utils.CommonUtils;
import com.petkit.oversea.R;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes4.dex */
public class CarouselImageView extends LinearLayout implements CarouselImagePageAdapter.OnItemClickListener {
    private Context context;
    private int currentDrawable;
    private Disposable disposable;
    private List<String> imageList;
    private int indicatorHeight;
    private int indicatorInterval;
    private int indicatorWidth;
    private LinearLayout llIndicator;
    private RelativeLayout llRoot;
    private int normalDrawable;
    private int period;
    private PromoteViewOnItemListener promoteViewOnItemListener;
    private InterceptViewPager vpImage;

    public interface PromoteViewOnItemListener {
        void onItemClick(String str);
    }

    public CarouselImageView(Context context) {
        super(context);
        this.indicatorWidth = 6;
        this.indicatorHeight = 2;
        this.indicatorInterval = 3;
        this.imageList = new ArrayList();
        this.period = 10000;
        this.currentDrawable = R.drawable.count_blue_badge;
        this.normalDrawable = R.drawable.count_gray_badge;
        init(context);
    }

    public CarouselImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.indicatorWidth = 6;
        this.indicatorHeight = 2;
        this.indicatorInterval = 3;
        this.imageList = new ArrayList();
        this.period = 10000;
        this.currentDrawable = R.drawable.count_blue_badge;
        this.normalDrawable = R.drawable.count_gray_badge;
        init(context);
    }

    public CarouselImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.indicatorWidth = 6;
        this.indicatorHeight = 2;
        this.indicatorInterval = 3;
        this.imageList = new ArrayList();
        this.period = 10000;
        this.currentDrawable = R.drawable.count_blue_badge;
        this.normalDrawable = R.drawable.count_gray_badge;
        init(context);
    }

    private void init(final Context context) {
        this.context = context;
        this.indicatorWidth = ArmsUtils.dip2px(CommonUtils.getAppContext(), 6.0f);
        this.indicatorHeight = ArmsUtils.dip2px(CommonUtils.getAppContext(), 6.0f);
        this.indicatorInterval = ArmsUtils.dip2px(CommonUtils.getAppContext(), 10.0f);
        RelativeLayout relativeLayout = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.layout_carousel_image_view_root, (ViewGroup) null);
        this.vpImage = (InterceptViewPager) relativeLayout.findViewById(R.id.vp_promote);
        this.llIndicator = (LinearLayout) relativeLayout.findViewById(R.id.ll_indicator);
        this.llRoot = (RelativeLayout) relativeLayout.findViewById(R.id.ll_root);
        this.vpImage.setPagingEnabled(true);
        this.imageList.clear();
        this.vpImage.setAdapter(new CarouselImagePageAdapter(context, this.imageList, this));
        if (this.imageList.size() == 0) {
            this.llIndicator.removeAllViews();
            this.llIndicator.setVisibility(8);
            this.vpImage.setVisibility(8);
        } else {
            if (this.imageList.size() == 1) {
                this.llIndicator.removeAllViews();
                this.llIndicator.setVisibility(8);
                this.vpImage.setVisibility(0);
            } else {
                this.vpImage.setVisibility(0);
                this.llIndicator.setVisibility(0);
                this.llIndicator.removeAllViews();
                for (int i = 0; i < this.imageList.size(); i++) {
                    View viewInflate = View.inflate(context, R.layout.layout_view_indicator_item, null);
                    if (i == 0) {
                        viewInflate.setBackgroundResource(this.currentDrawable);
                        this.llIndicator.addView(viewInflate, this.indicatorWidth, this.indicatorHeight);
                    } else {
                        viewInflate.setBackgroundResource(this.normalDrawable);
                        this.llIndicator.addView(View.inflate(context, R.layout.layout_view_empty_indicator_item, null), this.indicatorInterval, this.indicatorHeight);
                        this.llIndicator.addView(viewInflate, this.indicatorWidth, this.indicatorHeight);
                    }
                }
                this.vpImage.setCurrentItem(this.imageList.size() * 100);
            }
        }
        addView(relativeLayout, -1, -1);
        this.vpImage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.petkit.android.activities.mall.widget.CarouselImageView.1
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i2) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i2, float f, int i3) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int i2) {
                if (CarouselImageView.this.imageList.size() == 0) {
                    CarouselImageView.this.llIndicator.removeAllViews();
                    CarouselImageView.this.llIndicator.setVisibility(8);
                    CarouselImageView.this.vpImage.setVisibility(8);
                    return;
                }
                if (CarouselImageView.this.imageList.size() == 1) {
                    CarouselImageView.this.llIndicator.removeAllViews();
                    CarouselImageView.this.llIndicator.setVisibility(8);
                    CarouselImageView.this.vpImage.setVisibility(0);
                    return;
                }
                CarouselImageView.this.llIndicator.setVisibility(0);
                CarouselImageView.this.vpImage.setVisibility(0);
                CarouselImageView.this.llIndicator.removeAllViews();
                for (int i3 = 0; i3 < CarouselImageView.this.imageList.size(); i3++) {
                    View viewInflate2 = View.inflate(context, R.layout.layout_view_indicator_item, null);
                    if (i3 == i2 % CarouselImageView.this.imageList.size()) {
                        viewInflate2.setBackgroundResource(CarouselImageView.this.currentDrawable);
                        View viewInflate3 = View.inflate(context, R.layout.layout_view_empty_indicator_item, null);
                        if (i3 != 0) {
                            CarouselImageView.this.llIndicator.addView(viewInflate3, CarouselImageView.this.indicatorInterval, CarouselImageView.this.indicatorHeight);
                        }
                        CarouselImageView.this.llIndicator.addView(viewInflate2, CarouselImageView.this.indicatorWidth, CarouselImageView.this.indicatorHeight);
                    } else {
                        viewInflate2.setBackgroundResource(CarouselImageView.this.normalDrawable);
                        View viewInflate4 = View.inflate(context, R.layout.layout_view_empty_indicator_item, null);
                        if (i3 != 0) {
                            CarouselImageView.this.llIndicator.addView(viewInflate4, CarouselImageView.this.indicatorInterval, CarouselImageView.this.indicatorHeight);
                        }
                        CarouselImageView.this.llIndicator.addView(viewInflate2, CarouselImageView.this.indicatorWidth, CarouselImageView.this.indicatorHeight);
                    }
                }
            }
        });
    }

    @Override // com.petkit.android.activities.mall.adapter.CarouselImagePageAdapter.OnItemClickListener
    public void onItemClick(int i) {
        PromoteViewOnItemListener promoteViewOnItemListener = this.promoteViewOnItemListener;
        if (promoteViewOnItemListener != null) {
            List<String> list = this.imageList;
            promoteViewOnItemListener.onItemClick(list.get(i % list.size()));
        }
    }

    public void refreshCarouselImageData(List<String> list) {
        this.imageList.clear();
        this.imageList.addAll(list);
        this.vpImage.setAdapter(new CarouselImagePageAdapter(this.context, this.imageList, this));
        if (this.imageList.size() == 0) {
            this.llIndicator.removeAllViews();
            this.llIndicator.setVisibility(8);
            this.vpImage.setVisibility(8);
            return;
        }
        if (this.imageList.size() == 1) {
            this.llIndicator.removeAllViews();
            this.llIndicator.setVisibility(8);
            this.vpImage.setVisibility(0);
            return;
        }
        this.vpImage.setVisibility(0);
        this.llIndicator.setVisibility(0);
        this.llIndicator.removeAllViews();
        for (int i = 0; i < this.imageList.size(); i++) {
            View viewInflate = View.inflate(this.context, R.layout.layout_view_indicator_item, null);
            if (i == 0) {
                viewInflate.setBackgroundResource(this.currentDrawable);
                this.llIndicator.addView(viewInflate, this.indicatorWidth, this.indicatorHeight);
            } else {
                viewInflate.setBackgroundResource(this.normalDrawable);
                this.llIndicator.addView(View.inflate(this.context, R.layout.layout_view_empty_indicator_item, null), this.indicatorInterval, this.indicatorHeight);
                this.llIndicator.addView(viewInflate, this.indicatorWidth, this.indicatorHeight);
            }
        }
        this.vpImage.setCurrentItem(this.imageList.size() * 100);
        Disposable disposable = this.disposable;
        if (disposable != null && !disposable.isDisposed()) {
            this.disposable.dispose();
            this.disposable = null;
        }
        int i2 = this.period;
        Observable.interval(i2, i2, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Long>() { // from class: com.petkit.android.activities.mall.widget.CarouselImageView.2
            @Override // io.reactivex.Observer
            public void onComplete() {
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable th) {
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable disposable2) {
                CarouselImageView.this.disposable = disposable2;
            }

            @Override // io.reactivex.Observer
            public void onNext(Long l) {
                CarouselImageView.this.vpImage.setCurrentItem(CarouselImageView.this.vpImage.getCurrentItem() + 1);
            }
        });
    }

    public void setPeriod(int i) {
        this.period = i;
        refreshCarouselImageData(this.imageList);
    }

    public void setCurrentDrawable(int i) {
        this.currentDrawable = i;
        refreshCarouselImageData(this.imageList);
    }

    public void setNormalDrawable(int i) {
        this.normalDrawable = i;
        refreshCarouselImageData(this.imageList);
    }

    public void setPromoteViewOnItemListener(PromoteViewOnItemListener promoteViewOnItemListener) {
        this.promoteViewOnItemListener = promoteViewOnItemListener;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        Disposable disposable = this.disposable;
        if (disposable != null && !disposable.isDisposed()) {
            this.disposable.dispose();
            this.disposable = null;
        }
        super.onDetachedFromWindow();
    }
}

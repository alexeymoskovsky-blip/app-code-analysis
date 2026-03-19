package com.petkit.android.activities.petkitBleDevice.d3.widget;

import android.content.Context;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.viewpager.widget.ViewPager;
import com.jess.arms.utils.ArmsUtils;
import com.petkit.android.activities.petkitBleDevice.BaseBarView;
import com.petkit.android.activities.petkitBleDevice.d3.adapter.D3HistogramPagerAdapter;
import com.petkit.android.activities.petkitBleDevice.k3.IK3Context;
import com.petkit.android.activities.petkitBleDevice.t4.mode.T4BarData;
import com.petkit.android.activities.petkitBleDevice.t4.utils.T4Utils;
import com.petkit.android.activities.petkitBleDevice.t4.widget.BounceBackViewPager;
import com.petkit.android.activities.petkitBleDevice.t4.widget.DashLineView;
import com.petkit.android.utils.CommonUtils;
import com.petkit.oversea.R;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes4.dex */
public class D3Histogram extends RelativeLayout implements BaseBarView.ChartOnClickListener, D3HistogramPagerAdapter.OnFlagChange {
    private Context context;
    private int currentPostion;
    private int dataType;
    private Long deviceId;
    private Disposable disposable;
    private OnPageChanged onPageChanged;
    private boolean pageChange;
    private D3HistogramPagerAdapter pagerAdpater;
    private Map<Integer, Integer> positionMap;
    private RelativeLayout rlBg;
    private RelativeLayout rlScale;
    private boolean showMonthTipWindow;
    private boolean showWeekTipWindow;
    private View viewMask;
    private ViewPager vpK3Bar;
    private int yAsixDesWidth;

    public interface ChartOnClickListener {
        void clearSelection();

        void onChartClick(int i, int i2, int i3);
    }

    public interface OnPageChanged {
        void change(int i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearMap() {
    }

    @Override // com.petkit.android.activities.petkitBleDevice.BaseBarView.ChartOnClickListener
    public void clearSelection() {
    }

    @Override // com.petkit.android.activities.petkitBleDevice.BaseBarView.ChartOnClickListener
    public void onChartClick(int i, List<RectF> list) {
    }

    public D3Histogram(Context context) {
        super(context);
        this.currentPostion = 0;
        this.pageChange = false;
        this.positionMap = new HashMap();
        this.yAsixDesWidth = 0;
        this.context = context;
        initView();
    }

    public D3Histogram(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.currentPostion = 0;
        this.pageChange = false;
        this.positionMap = new HashMap();
        this.yAsixDesWidth = 0;
        this.context = context;
        initView();
    }

    public D3Histogram(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.currentPostion = 0;
        this.pageChange = false;
        this.positionMap = new HashMap();
        this.yAsixDesWidth = 0;
        this.context = context;
        initView();
    }

    public void setOnPageChanged(OnPageChanged onPageChanged) {
        this.onPageChanged = onPageChanged;
    }

    public void initView() {
        View viewInflate = View.inflate(this.context, R.layout.layout_t4_histogram, null);
        addView(viewInflate, -1, -1);
        this.showWeekTipWindow = true;
        this.showMonthTipWindow = true;
        this.viewMask = viewInflate.findViewById(R.id.view_mask);
        this.vpK3Bar = (ViewPager) viewInflate.findViewById(R.id.vp_k3_bar);
        this.rlScale = (RelativeLayout) viewInflate.findViewById(R.id.rl_scale);
        this.rlBg = (RelativeLayout) viewInflate.findViewById(R.id.rl_bg);
    }

    public void setMaskVisibility(int i) {
        this.viewMask.setVisibility(i);
    }

    public void setEnable(boolean z) {
        ((BounceBackViewPager) this.vpK3Bar).setScroll(z);
    }

    public void updateBarChartData(int i) {
        if (this.pagerAdpater == null || this.dataType != i) {
            Long k3DeviceId = ((IK3Context) this.context).getK3DeviceId();
            this.deviceId = k3DeviceId;
            D3HistogramPagerAdapter d3HistogramPagerAdapter = new D3HistogramPagerAdapter(this.context, k3DeviceId.longValue(), this, i, this.showMonthTipWindow, this.showWeekTipWindow, this);
            this.pagerAdpater = d3HistogramPagerAdapter;
            this.vpK3Bar.setAdapter(d3HistogramPagerAdapter);
            this.vpK3Bar.setCurrentItem(this.pagerAdpater.getCount() - 1);
            this.disposable = Observable.timer(50L, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() { // from class: com.petkit.android.activities.petkitBleDevice.d3.widget.D3Histogram.1
                @Override // io.reactivex.functions.Consumer
                public void accept(Long l) throws Exception {
                    if (D3Histogram.this.vpK3Bar == null || D3Histogram.this.pagerAdpater == null) {
                        return;
                    }
                    D3Histogram.this.vpK3Bar.setCurrentItem(D3Histogram.this.pagerAdpater.getCount() - 2);
                }
            });
            this.currentPostion = this.pagerAdpater.getCount() - 1;
            this.vpK3Bar.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.petkit.android.activities.petkitBleDevice.d3.widget.D3Histogram.2
                @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                public void onPageScrolled(int i2, float f, int i3) {
                }

                @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                public void onPageSelected(int i2) {
                    D3Histogram d3Histogram = D3Histogram.this;
                    d3Histogram.pageChange = d3Histogram.currentPostion != i2;
                    D3Histogram.this.currentPostion = i2;
                }

                @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                public void onPageScrollStateChanged(int i2) {
                    if (i2 != 0) {
                        return;
                    }
                    int count = (D3Histogram.this.currentPostion - D3Histogram.this.pagerAdpater.getCount()) + 1;
                    D3Histogram.this.clearMap();
                    if (D3Histogram.this.onPageChanged != null) {
                        D3Histogram.this.onPageChanged.change(count);
                    }
                }
            });
        }
        D3HistogramPagerAdapter d3HistogramPagerAdapter2 = this.pagerAdpater;
        if (d3HistogramPagerAdapter2 != null) {
            d3HistogramPagerAdapter2.notifyDataSetChanged();
        }
        this.dataType = i;
    }

    @Override // com.petkit.android.activities.petkitBleDevice.BaseBarView.ChartOnClickListener
    public void onChartClick(int i, int i2, int i3, int i4) {
        ArmsUtils.dip2px(this.context, 10.0f);
        ArmsUtils.dip2px(this.context, 3.0f);
    }

    @Override // com.petkit.android.activities.petkitBleDevice.BaseBarView.ChartOnClickListener
    public void drawFinish(List<Float> list) {
        if (this.pagerAdpater.getPositionMap().containsKey(this.dataType + "-" + this.currentPostion)) {
            int i = 0;
            if (list.size() > 0 && this.rlBg.getChildCount() <= 0) {
                int iAbs = Math.abs(list.get(0).intValue() - list.get(list.size() - 1).intValue());
                for (int i2 = 1; i2 < list.size(); i2++) {
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, ArmsUtils.dip2px(this.context, 1.0f));
                    layoutParams.topMargin = list.get(i2).intValue();
                    if (i2 != list.size() - 1) {
                        this.rlBg.addView(new DashLineView(this.context), layoutParams);
                    }
                }
                View view = new View(this.context);
                view.setBackground(this.context.getResources().getDrawable(R.color.k3_line_gray));
                RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(ArmsUtils.dip2px(this.context, 1.0f), iAbs);
                layoutParams2.topMargin = ArmsUtils.dip2px(this.context, 6.0f);
                layoutParams2.addRule(9);
                this.rlBg.addView(view, layoutParams2);
            }
            Map<String, List<T4BarData>> positionMap = this.pagerAdpater.getPositionMap();
            float histogramMaxYValue = (T4Utils.getHistogramMaxYValue(2, positionMap.get(this.dataType + "-" + this.currentPostion)) * 1.0f) / (list.size() - 1);
            if (list.size() > 0) {
                if (this.rlScale.getChildCount() > 0) {
                    int i3 = 0;
                    while (i < this.rlScale.getChildCount()) {
                        View childAt = this.rlScale.getChildAt(i);
                        if (childAt instanceof TextView) {
                            ((TextView) childAt).setText("" + i3);
                            i3 = (int) (((float) i3) + histogramMaxYValue);
                        }
                        i++;
                    }
                } else {
                    int i4 = 0;
                    while (i < list.size()) {
                        TextView textView = new TextView(this.context);
                        textView.setText("" + i4);
                        textView.setTextSize(11.0f);
                        textView.setTextColor(CommonUtils.getColorById(R.color.t4_text_gray));
                        textView.setGravity(17);
                        RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(ArmsUtils.dip2px(this.context, 25.0f), -2);
                        layoutParams3.topMargin = list.get(i).intValue() - ArmsUtils.dip2px(this.context, 6.0f);
                        this.rlScale.addView(textView, layoutParams3);
                        i4 = (int) (i4 + histogramMaxYValue);
                        i++;
                    }
                }
            }
            this.rlScale.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.petkit.android.activities.petkitBleDevice.d3.widget.D3Histogram.3
                @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                public void onGlobalLayout() {
                    D3Histogram d3Histogram = D3Histogram.this;
                    d3Histogram.yAsixDesWidth = d3Histogram.rlScale.getWidth();
                    D3Histogram.this.rlScale.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            });
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.d3.adapter.D3HistogramPagerAdapter.OnFlagChange
    public void change(String str) {
        str.hashCode();
        if (str.equals("week")) {
            this.showWeekTipWindow = false;
        } else if (str.equals("month")) {
            this.showMonthTipWindow = false;
        }
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

    public void setCurrentItem(int i) {
        this.vpK3Bar.setCurrentItem((this.pagerAdpater.getCount() - 1) + i);
    }
}

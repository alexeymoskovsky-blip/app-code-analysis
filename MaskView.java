package com.petkit.android.activities.home.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.widget.NestedScrollView;
import com.jess.arms.utils.ArmsUtils;
import com.petkit.android.utils.PetkitLog;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes4.dex */
@RequiresApi(api = 23)
public class MaskView extends FrameLayout implements View.OnScrollChangeListener {
    private Context context;
    Disposable disposable;
    private boolean isUp;
    float lastX;
    float lastY;
    private VelocityTracker mVelocityTracker;
    private MaskViewListener maskViewListener;
    private int maxHeight;
    private int minHeight;
    private NestedScrollView nestedScrollView;
    long pressTime;
    private int statusBarHeight;
    int time;
    private View topView;

    public interface MaskViewListener {
        void onRefreshFinish();

        void onRefreshStart(int i);

        void onScrollChange(float f, int i);
    }

    public MaskView(Context context) {
        super(context);
        this.pressTime = 0L;
        this.lastX = 0.0f;
        this.lastY = 0.0f;
        this.time = 0;
        this.context = context;
        init();
    }

    private void init() {
        this.mVelocityTracker = VelocityTracker.obtain();
        NestedScrollView nestedScrollView = this.nestedScrollView;
        if (nestedScrollView != null) {
            nestedScrollView.setOnScrollChangeListener(this);
        }
        this.maxHeight = ArmsUtils.dip2px(this.context, 323.0f);
        this.minHeight = ArmsUtils.dip2px(this.context, 44.0f);
    }

    public MaskView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.pressTime = 0L;
        this.lastX = 0.0f;
        this.lastY = 0.0f;
        this.time = 0;
        this.context = context;
        init();
    }

    public MaskView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.pressTime = 0L;
        this.lastX = 0.0f;
        this.lastY = 0.0f;
        this.time = 0;
        this.context = context;
        init();
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 0) {
            PetkitLog.d("onInterceptTouchEvent", "ACTION_DOWN:x:" + motionEvent.getRawX() + " y:" + motionEvent.getRawY());
            this.pressTime = System.currentTimeMillis();
            this.lastY = motionEvent.getRawY();
            this.lastX = motionEvent.getRawX();
            this.time = 0;
            Disposable disposable = this.disposable;
            if (disposable != null && !disposable.isDisposed()) {
                this.disposable.dispose();
                this.disposable = null;
            }
        } else if (action == 1) {
            PetkitLog.d("onInterceptTouchEvent", "ACTION_UP:x:" + motionEvent.getRawX() + " y:" + motionEvent.getRawY());
        } else if (action == 2) {
            PetkitLog.d("onInterceptTouchEvent", "ACTION_MOVE:x:" + motionEvent.getRawX() + " y:" + motionEvent.getRawY());
            float fAbs = Math.abs(this.lastY - motionEvent.getRawY());
            float fAbs2 = Math.abs(this.lastX - motionEvent.getRawX());
            if (System.currentTimeMillis() - this.pressTime <= 2000 && fAbs2 <= fAbs) {
                return fAbs >= 10.0f || fAbs2 >= 10.0f;
            }
            return false;
        }
        return super.onInterceptTouchEvent(motionEvent);
    }

    public void setStatusBarHeight(int i) {
        this.statusBarHeight = i;
        this.minHeight = ArmsUtils.dip2px(this.context, 44.0f) + i;
    }

    public NestedScrollView getNestedScrollView() {
        return this.nestedScrollView;
    }

    public void setTopViewAndNestedScrollView(View view, NestedScrollView nestedScrollView) {
        this.nestedScrollView = nestedScrollView;
        this.topView = view;
        nestedScrollView.setOnScrollChangeListener(this);
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x00a9  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouchEvent(android.view.MotionEvent r7) {
        /*
            Method dump skipped, instruction units count: 364
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.petkit.android.activities.home.widget.MaskView.onTouchEvent(android.view.MotionEvent):boolean");
    }

    private void continueScroll(final float f) {
        PetkitLog.d("setNestedScrollView yVelocity:" + f);
        Disposable disposable = this.disposable;
        if (disposable != null && !disposable.isDisposed()) {
            this.disposable.dispose();
            this.disposable = null;
        }
        if (Math.abs(f) > 1000.0f) {
            Observable.interval(0L, 16L, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Long>() { // from class: com.petkit.android.activities.home.widget.MaskView.1
                @Override // io.reactivex.Observer
                public void onComplete() {
                }

                @Override // io.reactivex.Observer
                public void onError(Throwable th) {
                }

                @Override // io.reactivex.Observer
                public void onSubscribe(Disposable disposable2) {
                    MaskView.this.disposable = disposable2;
                }

                @Override // io.reactivex.Observer
                public void onNext(Long l) {
                    MaskView maskView = MaskView.this;
                    if (maskView.time < 20) {
                        int iNextInt = new Random().nextInt(100);
                        StringBuilder sb = new StringBuilder();
                        sb.append("setNestedScrollView onScrollChange:nextInt:");
                        sb.append(iNextInt);
                        sb.append(f > 0.0f);
                        sb.append(" yVelocity:");
                        sb.append(f);
                        PetkitLog.d(sb.toString());
                        MaskView maskView2 = MaskView.this;
                        NestedScrollView nestedScrollView = maskView2.nestedScrollView;
                        if (f > 0.0f) {
                            iNextInt = -iNextInt;
                        }
                        maskView2.onScrollChange(nestedScrollView, iNextInt);
                        MaskView.this.time++;
                        return;
                    }
                    maskView.disposable.dispose();
                    MaskView.this.time = 0;
                }
            });
            return;
        }
        if (Math.abs(f) <= 1000.0f && Math.abs(f) > 500.0f) {
            Observable.interval(0L, 16L, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Long>() { // from class: com.petkit.android.activities.home.widget.MaskView.2
                @Override // io.reactivex.Observer
                public void onComplete() {
                }

                @Override // io.reactivex.Observer
                public void onError(Throwable th) {
                }

                @Override // io.reactivex.Observer
                public void onSubscribe(Disposable disposable2) {
                    MaskView.this.disposable = disposable2;
                }

                @Override // io.reactivex.Observer
                public void onNext(Long l) {
                    MaskView maskView = MaskView.this;
                    if (maskView.time < 10) {
                        int iNextInt = new Random().nextInt(50);
                        StringBuilder sb = new StringBuilder();
                        sb.append("setNestedScrollView onScrollChange:nextInt:");
                        sb.append(iNextInt);
                        sb.append(f > 0.0f);
                        sb.append(" yVelocity:");
                        sb.append(f);
                        PetkitLog.d(sb.toString());
                        MaskView maskView2 = MaskView.this;
                        NestedScrollView nestedScrollView = maskView2.nestedScrollView;
                        if (f > 0.0f) {
                            iNextInt = -iNextInt;
                        }
                        maskView2.onScrollChange(nestedScrollView, iNextInt);
                        MaskView.this.time++;
                        return;
                    }
                    maskView.disposable.dispose();
                    MaskView.this.time = 0;
                }
            });
        } else {
            if (Math.abs(f) > 500.0f || Math.abs(f) <= 250.0f) {
                return;
            }
            Observable.interval(0L, 16L, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Long>() { // from class: com.petkit.android.activities.home.widget.MaskView.3
                @Override // io.reactivex.Observer
                public void onComplete() {
                }

                @Override // io.reactivex.Observer
                public void onError(Throwable th) {
                }

                @Override // io.reactivex.Observer
                public void onSubscribe(Disposable disposable2) {
                    MaskView.this.disposable = disposable2;
                }

                @Override // io.reactivex.Observer
                public void onNext(Long l) {
                    MaskView maskView = MaskView.this;
                    if (maskView.time < 6) {
                        int iNextInt = new Random().nextInt(25);
                        StringBuilder sb = new StringBuilder();
                        sb.append("setNestedScrollView onScrollChange:nextInt:");
                        sb.append(iNextInt);
                        sb.append(f > 0.0f);
                        sb.append(" yVelocity:");
                        sb.append(f);
                        PetkitLog.d(sb.toString());
                        MaskView maskView2 = MaskView.this;
                        NestedScrollView nestedScrollView = maskView2.nestedScrollView;
                        if (f > 0.0f) {
                            iNextInt = -iNextInt;
                        }
                        maskView2.onScrollChange(nestedScrollView, iNextInt);
                        MaskView.this.time++;
                        return;
                    }
                    maskView.disposable.dispose();
                    MaskView.this.time = 0;
                }
            });
        }
    }

    @Override // android.view.View.OnScrollChangeListener
    public void onScrollChange(View view, int i, int i2, int i3, int i4) {
        PetkitLog.d("setNestedScrollView onScrollChange:scrollX:" + i + " scrollY:" + i2 + " oldScrollX:" + i3 + " oldScrollY:" + i4 + " dy:" + (i2 - i4));
    }

    public void onScrollChange(View view, int i) {
        if (i >= 0 || this.topView.getHeight() < this.maxHeight) {
            PetkitLog.d("setNestedScrollView onScrollChange:dy:" + i);
            ViewGroup.LayoutParams layoutParams = this.topView.getLayoutParams();
            FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) getLayoutParams();
            int i2 = layoutParams.height - i;
            int i3 = layoutParams2.topMargin - i;
            int i4 = this.maxHeight;
            if (i2 > i4) {
                layoutParams.height = i4;
                layoutParams2.topMargin = i4;
            } else {
                int i5 = this.minHeight;
                if (i2 < i5) {
                    layoutParams.height = i5;
                    layoutParams2.topMargin = i5;
                } else {
                    layoutParams.height = i2;
                    layoutParams2.topMargin = i3;
                }
            }
            this.topView.setLayoutParams(layoutParams);
            setLayoutParams(layoutParams2);
            MaskViewListener maskViewListener = this.maskViewListener;
            if (maskViewListener != null) {
                int i6 = layoutParams.height;
                int i7 = this.minHeight;
                maskViewListener.onScrollChange(((i6 - i7) * 1.0f) / (this.maxHeight - i7), i > 0 ? 1 : -1);
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Disposable disposable = this.disposable;
        if (disposable == null || disposable.isDisposed()) {
            return;
        }
        this.disposable.dispose();
        this.disposable = null;
    }

    public MaskViewListener getMaskViewListener() {
        return this.maskViewListener;
    }

    public void setMaskViewListener(MaskViewListener maskViewListener) {
        this.maskViewListener = maskViewListener;
    }

    public int getMaxHeight() {
        return this.maxHeight;
    }

    public void setMaxHeight(int i) {
        this.maxHeight = i;
        postInvalidate();
    }

    public int getMinHeight() {
        return this.minHeight;
    }

    public void setMinHeight(int i) {
        this.minHeight = i;
    }
}

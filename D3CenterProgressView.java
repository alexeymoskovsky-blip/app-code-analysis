package com.petkit.android.activities.petkitBleDevice.d3.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import androidx.annotation.Nullable;
import com.jess.arms.utils.ArmsUtils;
import com.petkit.android.activities.petkitBleDevice.d3.mode.D3Record;
import com.petkit.android.utils.CommonUtils;
import com.petkit.oversea.R;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes4.dex */
public class D3CenterProgressView extends View {
    private String TAG;
    private int[] aniColors;
    private float[] aniPos;
    private Paint animationPaint;
    private int animationStep;
    private float animationSweepAngle;
    private int backgroundColor;
    private Bitmap batteryIcon;
    private int batteryIconMarginTop;
    private int[] bgColors;
    private String centerText;
    private int[] colors;
    private Context context;
    private D3Record d3Record;
    Disposable disposable;
    private int downX;
    private int downY;
    private Bitmap eatAniOne;
    private Bitmap eatAniTwo;
    private int eatIconCount;
    private float[] eatIconPos;
    private int eatIconRotate;
    private Paint eatPaint;
    private Path eatPath;
    private PathMeasure eatPathMeasure;
    private int feedingAmount;
    private Bitmap helpIcon;
    private Rect helpIconRect;
    private float inProgress;
    private int inProgressBgColor;
    private SweepGradient inProgressBgSweepGradient;
    private int inProgressBgWidth;
    private int inProgressColor;
    private Paint inProgressPaint;
    private Path inProgressPath;
    private SweepGradient inProgressSweepGradient;
    private int interval;
    private int mHeight;
    private int mWidth;
    private float originalSweepAngle;
    private float outProgress;
    private int outProgressBgColor;
    private int outProgressBgWidth;
    private int outProgressColor;
    private Paint outProgressPaint;
    private Path outProgressPath;
    private float[] pos;
    private float startAngle;
    private boolean startEatAni;
    private boolean startFeedAni;
    private ValueAnimator stepOneAnimator;
    private ValueAnimator stepThreeAnimator;
    private ValueAnimator stepTwoAnimator;
    private int textColor;
    private Paint textPaint;
    private ViewClick viewClick;
    private int weightRectFMarginLength;

    public interface ViewClick {
        void onAdjustBtnClick();

        void onInProgressViewClick();

        void onOutProgressViewClick();

        void refreshPromptPos(float f);
    }

    public int getFeedingAmount() {
        return this.feedingAmount;
    }

    public void setFeedingAmount(int i) {
        this.feedingAmount = i;
    }

    public D3CenterProgressView(Context context) {
        super(context);
        this.TAG = "D3CenterProgressView";
        this.outProgressBgWidth = 30;
        this.inProgressBgWidth = 20;
        this.inProgressBgColor = R.color.d3_home_progress_bg_yellow;
        int i = R.color.d3_main_green;
        this.inProgressColor = i;
        this.outProgressBgColor = R.color.d3_home_progress_bg_green;
        this.outProgressColor = i;
        this.backgroundColor = R.color.d3_bg_gray;
        this.textColor = R.color.white;
        this.startAngle = 150.0f;
        this.originalSweepAngle = 240.0f;
        this.outProgress = 50.0f;
        this.inProgress = 50.0f;
        this.interval = 20;
        this.batteryIconMarginTop = 30;
        this.centerText = "--";
        this.animationSweepAngle = 50.0f;
        this.animationStep = 1;
        this.feedingAmount = 0;
        this.eatIconCount = 0;
        this.eatIconRotate = 0;
        this.weightRectFMarginLength = 0;
        this.context = context;
        initView();
    }

    public D3CenterProgressView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.TAG = "D3CenterProgressView";
        this.outProgressBgWidth = 30;
        this.inProgressBgWidth = 20;
        this.inProgressBgColor = R.color.d3_home_progress_bg_yellow;
        int i = R.color.d3_main_green;
        this.inProgressColor = i;
        this.outProgressBgColor = R.color.d3_home_progress_bg_green;
        this.outProgressColor = i;
        this.backgroundColor = R.color.d3_bg_gray;
        this.textColor = R.color.white;
        this.startAngle = 150.0f;
        this.originalSweepAngle = 240.0f;
        this.outProgress = 50.0f;
        this.inProgress = 50.0f;
        this.interval = 20;
        this.batteryIconMarginTop = 30;
        this.centerText = "--";
        this.animationSweepAngle = 50.0f;
        this.animationStep = 1;
        this.feedingAmount = 0;
        this.eatIconCount = 0;
        this.eatIconRotate = 0;
        this.weightRectFMarginLength = 0;
        this.context = context;
        initView();
    }

    public D3CenterProgressView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.TAG = "D3CenterProgressView";
        this.outProgressBgWidth = 30;
        this.inProgressBgWidth = 20;
        this.inProgressBgColor = R.color.d3_home_progress_bg_yellow;
        int i2 = R.color.d3_main_green;
        this.inProgressColor = i2;
        this.outProgressBgColor = R.color.d3_home_progress_bg_green;
        this.outProgressColor = i2;
        this.backgroundColor = R.color.d3_bg_gray;
        this.textColor = R.color.white;
        this.startAngle = 150.0f;
        this.originalSweepAngle = 240.0f;
        this.outProgress = 50.0f;
        this.inProgress = 50.0f;
        this.interval = 20;
        this.batteryIconMarginTop = 30;
        this.centerText = "--";
        this.animationSweepAngle = 50.0f;
        this.animationStep = 1;
        this.feedingAmount = 0;
        this.eatIconCount = 0;
        this.eatIconRotate = 0;
        this.weightRectFMarginLength = 0;
        this.context = context;
        initView();
    }

    private void initView() {
        this.outProgressBgWidth = ArmsUtils.dip2px(this.context, 18.0f);
        this.inProgressBgWidth = ArmsUtils.dip2px(this.context, 14.0f);
        this.interval = ArmsUtils.dip2px(this.context, 10.0f);
        this.batteryIcon = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.d3_battery_max);
        this.eatAniOne = BitmapFactory.decodeResource(getResources(), R.drawable.d3_eat_ani_1);
        this.eatAniTwo = BitmapFactory.decodeResource(getResources(), R.drawable.d3_eat_ani_2);
        this.weightRectFMarginLength = (this.outProgressBgWidth * 2) + this.interval;
        this.batteryIconMarginTop = ArmsUtils.dip2px(this.context, 30.0f);
        this.helpIcon = BitmapFactory.decodeResource(getResources(), R.drawable.p3_help_icon);
        initPaint();
    }

    private void initPaint() {
        Paint paint = new Paint();
        this.outProgressPaint = paint;
        paint.setAntiAlias(true);
        this.outProgressPaint.setColor(getResources().getColor(this.outProgressBgColor));
        Paint paint2 = this.outProgressPaint;
        Paint.Cap cap = Paint.Cap.ROUND;
        paint2.setStrokeCap(cap);
        Paint paint3 = this.outProgressPaint;
        Paint.Style style = Paint.Style.STROKE;
        paint3.setStyle(style);
        this.outProgressPaint.setStrokeWidth(this.outProgressBgWidth);
        Paint paint4 = new Paint();
        this.inProgressPaint = paint4;
        paint4.setAntiAlias(true);
        this.inProgressPaint.setStrokeCap(cap);
        this.inProgressPaint.setStyle(style);
        this.inProgressPaint.setStrokeWidth(this.inProgressBgWidth);
        Paint paint5 = new Paint();
        this.eatPaint = paint5;
        paint5.setAntiAlias(true);
        this.eatPaint.setStrokeCap(cap);
        Paint paint6 = new Paint();
        this.animationPaint = paint6;
        paint6.setAntiAlias(true);
        this.animationPaint.setStrokeCap(cap);
        this.animationPaint.setStyle(style);
        this.animationPaint.setStrokeWidth(this.outProgressBgWidth);
        this.colors = new int[]{-14631267, -13442636};
        this.bgColors = new int[]{452306467, 452306467};
        this.pos = new float[]{1.0f, 0.0f};
        this.aniPos = new float[]{0.5f, 0.75f};
        this.aniColors = new int[]{-2492431, -14239848};
        Paint paint7 = new Paint();
        this.textPaint = paint7;
        paint7.setAntiAlias(true);
        this.textPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override // android.view.View
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int mode = View.MeasureSpec.getMode(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        int size = View.MeasureSpec.getSize(i);
        int size2 = View.MeasureSpec.getSize(i2);
        if (mode == 1073741824 || mode == Integer.MIN_VALUE) {
            this.mWidth = size;
        } else {
            this.mWidth = 0;
        }
        if (mode2 == Integer.MIN_VALUE || mode2 == 0) {
            this.mHeight = ArmsUtils.dip2px(this.context, 10.0f);
        } else {
            this.mHeight = size2;
        }
        setMeasuredDimension(this.mWidth, this.mHeight);
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(this.context.getResources().getColor(this.backgroundColor));
        drawCenterText(canvas);
        canvas.save();
        if (this.startFeedAni) {
            int i = this.animationStep;
            if (i == 1) {
                drawAniOutProgress(canvas);
                return;
            } else {
                if (i != 2) {
                    return;
                }
                drawAniRunProgress(canvas);
                return;
            }
        }
        drawOutProgress(canvas);
        if (this.d3Record.getState().getPim() != 0) {
            drawBatteryPower(canvas);
        }
        if (this.startEatAni) {
            initInIconPath(canvas);
            drawInIcon(canvas);
        }
    }

    private void initInIconPath(Canvas canvas) {
        int i = this.weightRectFMarginLength;
        RectF rectF = new RectF(i, i, this.mWidth - i, this.mHeight - i);
        Path path = new Path();
        this.eatPath = path;
        float f = (this.originalSweepAngle / 100.0f) * this.inProgress;
        this.eatIconRotate = (int) (f - 120.0f);
        float f2 = this.startAngle;
        if (f <= 0.0f) {
            f = 1.0f;
        }
        path.addArc(rectF, f2, f);
        this.eatPaint.setColor(0);
        canvas.drawPath(this.eatPath, this.eatPaint);
        this.eatIconPos = new float[2];
        PathMeasure pathMeasure = new PathMeasure();
        this.eatPathMeasure = pathMeasure;
        pathMeasure.setPath(this.eatPath, false);
        PathMeasure pathMeasure2 = this.eatPathMeasure;
        pathMeasure2.getPosTan(pathMeasure2.getLength(), this.eatIconPos, null);
    }

    public void startEatAni() {
        stopEatAni();
        this.startEatAni = true;
        this.eatIconCount = 0;
        Observable.interval(0L, 1000L, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Long>() { // from class: com.petkit.android.activities.petkitBleDevice.d3.widget.D3CenterProgressView.1
            @Override // io.reactivex.Observer
            public void onComplete() {
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable th) {
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                D3CenterProgressView.this.disposable = disposable;
            }

            @Override // io.reactivex.Observer
            public void onNext(Long l) {
                D3CenterProgressView.this.postInvalidate();
            }
        });
    }

    public void stopEatAni() {
        this.startEatAni = false;
        this.eatIconCount = 0;
        Disposable disposable = this.disposable;
        if (disposable == null || disposable.isDisposed()) {
            return;
        }
        this.disposable.dispose();
    }

    private void drawInIcon(Canvas canvas) {
        this.eatPaint.setColor(-16777216);
        if (this.eatIconCount % 2 == 0) {
            drawRotateBitmap(canvas, this.eatPaint, this.eatAniOne, this.eatIconRotate, this.eatIconPos[0] - (r3.getWidth() / 2), this.eatIconPos[1] - (this.eatAniOne.getHeight() / 2));
        } else {
            drawRotateBitmap(canvas, this.eatPaint, this.eatAniTwo, this.eatIconRotate, this.eatIconPos[0] - (this.eatAniOne.getWidth() / 2), this.eatIconPos[1] - (this.eatAniOne.getHeight() / 2));
        }
        this.eatIconCount++;
    }

    private void drawRotateBitmap(Canvas canvas, Paint paint, Bitmap bitmap, float f, float f2, float f3) {
        Matrix matrix = new Matrix();
        int width = bitmap.getWidth() / 2;
        int height = bitmap.getHeight() / 2;
        matrix.postTranslate(-width, -height);
        matrix.postRotate(f);
        matrix.postTranslate(f2 + width, f3 + height);
        canvas.drawBitmap(bitmap, matrix, paint);
    }

    private void drawAniRunProgress(Canvas canvas) {
        this.animationSweepAngle = (this.originalSweepAngle / 100.0f) * this.outProgress;
        this.animationPaint.setShader(null);
        this.animationPaint.setColor(getResources().getColor(this.outProgressColor));
        int i = this.outProgressBgWidth;
        RectF rectF = new RectF(i, i, this.mWidth - i, this.mHeight - i);
        canvas.drawArc(rectF, this.startAngle, this.animationSweepAngle, false, this.animationPaint);
        this.animationPaint.setColor(getResources().getColor(R.color.d3_light_green));
        this.animationPaint.setStrokeWidth(this.outProgressBgWidth);
        canvas.drawArc(rectF, this.startAngle, this.originalSweepAngle, false, this.animationPaint);
        this.animationPaint.setColor(getResources().getColor(R.color.d3_progress_light_green));
        this.animationPaint.setStrokeWidth(this.outProgressBgWidth);
        float f = this.startAngle;
        float f2 = this.originalSweepAngle / 100.0f;
        float f3 = this.outProgress;
        canvas.drawArc(rectF, f + (f2 * f3), 25.5f - (f3 / 4.0f), false, this.animationPaint);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startNextAni() {
        int i = this.animationStep;
        if (i == 1) {
            if (this.stepOneAnimator == null) {
                ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(this.outProgress, 0.0f);
                this.stepOneAnimator = valueAnimatorOfFloat;
                valueAnimatorOfFloat.setDuration(1000L);
                this.stepOneAnimator.setInterpolator(new DecelerateInterpolator());
                this.stepOneAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.petkit.android.activities.petkitBleDevice.d3.widget.D3CenterProgressView.2
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        D3CenterProgressView.this.outProgress = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                        D3CenterProgressView.this.postInvalidate();
                    }
                });
                this.stepOneAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.petkit.android.activities.petkitBleDevice.d3.widget.D3CenterProgressView.3
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        super.onAnimationEnd(animator);
                        D3CenterProgressView.this.animationStep = 2;
                        D3CenterProgressView.this.startNextAni();
                    }
                });
                this.stepOneAnimator.start();
                return;
            }
            return;
        }
        if (i == 2 && this.stepTwoAnimator == null) {
            ValueAnimator valueAnimatorOfFloat2 = ValueAnimator.ofFloat(0.0f, 100.0f);
            this.stepTwoAnimator = valueAnimatorOfFloat2;
            valueAnimatorOfFloat2.setDuration(1200L);
            this.stepTwoAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            this.stepTwoAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.petkit.android.activities.petkitBleDevice.d3.widget.D3CenterProgressView.4
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                    D3CenterProgressView d3CenterProgressView = D3CenterProgressView.this;
                    if (fFloatValue > 100.0f) {
                        fFloatValue = 100.0f;
                    }
                    d3CenterProgressView.outProgress = fFloatValue;
                    D3CenterProgressView.this.postInvalidate();
                }
            });
            this.stepTwoAnimator.addListener(new AnonymousClass5());
            this.stepTwoAnimator.start();
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.d3.widget.D3CenterProgressView$5, reason: invalid class name */
    public class AnonymousClass5 extends AnimatorListenerAdapter {
        public AnonymousClass5() {
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            super.onAnimationEnd(animator);
            Observable.just("").delay(500L, TimeUnit.MILLISECONDS).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.petkit.android.activities.petkitBleDevice.d3.widget.D3CenterProgressView$5$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f$0.lambda$onAnimationEnd$0((String) obj);
                }
            });
        }

        public final /* synthetic */ void lambda$onAnimationEnd$0(String str) throws Exception {
            if (D3CenterProgressView.this.stepTwoAnimator != null) {
                D3CenterProgressView.this.stepTwoAnimator.start();
            }
        }
    }

    private void drawAniOutProgress(Canvas canvas) {
        this.animationSweepAngle = (this.originalSweepAngle / 100.0f) * this.outProgress;
        this.animationPaint.setShader(null);
        this.animationPaint.setColor(getResources().getColor(this.outProgressColor));
        int i = this.outProgressBgWidth;
        RectF rectF = new RectF(i, i, this.mWidth - i, this.mHeight - i);
        canvas.drawArc(rectF, this.startAngle, this.animationSweepAngle, false, this.animationPaint);
        this.animationPaint.setColor(getResources().getColor(R.color.d3_light_green));
        this.animationPaint.setStrokeWidth(this.outProgressBgWidth);
        canvas.drawArc(rectF, this.startAngle, this.originalSweepAngle, false, this.animationPaint);
        this.animationPaint.setColor(getResources().getColor(R.color.d3_progress_light_green));
        this.animationPaint.setStrokeWidth(this.outProgressBgWidth);
        canvas.drawArc(rectF, this.startAngle, this.animationSweepAngle, false, this.animationPaint);
    }

    private void drawCenterText(Canvas canvas) {
        int i = this.weightRectFMarginLength;
        RectF rectF = new RectF(i, i, this.mWidth - i, this.mHeight - i);
        this.textPaint.setColor(CommonUtils.getColorById(R.color.walk_text));
        this.textPaint.setTextSize(ArmsUtils.dip2px(this.context, 12.0f));
        if (this.d3Record.getState().getFeeding() != 1) {
            String string = this.context.getResources().getString(R.string.D3_amount_of_eat_food_today);
            ViewClick viewClick = this.viewClick;
            if (viewClick != null) {
                viewClick.refreshPromptPos(rectF.centerY() + ArmsUtils.dip2px(this.context, 32.0f));
            }
            this.textPaint.getTextBounds(string, 0, string.length(), new Rect());
            this.helpIconRect = new Rect(((int) ((rectF.centerX() - (this.helpIcon.getWidth() / 2.0f)) + (r4.width() / 2.0f) + ArmsUtils.dip2px(this.context, 2.0f))) + ArmsUtils.dip2px(this.context, 4.0f), ((int) ((rectF.centerY() - ArmsUtils.dip2px(this.context, 13.0f)) - r4.height())) + ArmsUtils.dip2px(this.context, 4.0f), ((int) ((rectF.centerX() - (this.helpIcon.getWidth() / 2.0f)) + (r4.width() / 2.0f) + ArmsUtils.dip2px(this.context, 2.0f))) + this.helpIcon.getWidth() + ArmsUtils.dip2px(this.context, 8.0f), ((int) ((rectF.centerY() - ArmsUtils.dip2px(this.context, 13.0f)) - r4.height())) + ArmsUtils.dip2px(this.context, 8.0f) + this.helpIcon.getHeight());
            this.feedingAmount = 0;
        }
        this.textPaint.setTypeface(Typeface.DEFAULT_BOLD);
        if (this.d3Record.getState().getPim() != 0 && this.d3Record.getState().getFeeding() == 1) {
            this.textPaint.setTextSize(60.0f);
            this.textColor = R.color.d3_main_green;
            this.textPaint.setColor(getResources().getColor(this.textColor));
            canvas.drawText(this.context.getResources().getString(R.string.Feeding), rectF.centerX(), rectF.centerY() + ArmsUtils.dip2px(this.context, 23.0f), this.textPaint);
            return;
        }
        this.textPaint.setTextSize(120.0f);
        if (this.d3Record.getState().getPim() == 0) {
            this.textColor = R.color.walk_text;
        } else {
            this.textColor = R.color.d3_main_green;
        }
        this.textPaint.setColor(getResources().getColor(this.textColor));
        Rect rect = new Rect();
        Paint paint = this.textPaint;
        String str = this.centerText;
        paint.getTextBounds(str, 0, str.length(), rect);
        canvas.drawText(this.centerText, rectF.centerX(), rectF.centerY() + ArmsUtils.dip2px(this.context, 23.0f), this.textPaint);
        if ("--".equals(this.centerText)) {
            return;
        }
        this.textPaint.setTextSize(60.0f);
        Rect rect2 = new Rect();
        String string2 = getResources().getString(R.string.Unit_g);
        this.textPaint.getTextBounds(string2, 0, string2.length(), rect2);
        canvas.drawText(string2, rectF.centerX() + (rect.width() / 2) + rect2.width(), rectF.centerY() + ArmsUtils.dip2px(this.context, 23.0f), this.textPaint);
    }

    private void drawBatteryPower(Canvas canvas) {
        int i = this.weightRectFMarginLength;
        RectF rectF = new RectF(i, i, this.mWidth - i, this.mHeight - i);
        canvas.drawBitmap(Bitmap.createScaledBitmap(this.batteryIcon, (int) (r1.getWidth() * 1.2f), (int) (this.batteryIcon.getHeight() * 1.2f), true), rectF.centerX() - (this.batteryIcon.getWidth() / 2), rectF.top + this.batteryIconMarginTop, this.inProgressPaint);
    }

    private void drawInProgress(Canvas canvas) {
        int i = this.weightRectFMarginLength;
        RectF rectF = new RectF(i, i, this.mWidth - i, this.mHeight - i);
        Matrix matrix = new Matrix();
        matrix.setRotate(90.0f, rectF.centerX(), rectF.centerY());
        SweepGradient sweepGradient = new SweepGradient(rectF.centerX(), rectF.centerY(), this.bgColors, (float[]) null);
        this.inProgressBgSweepGradient = sweepGradient;
        sweepGradient.setLocalMatrix(matrix);
        this.inProgressPaint.setShader(this.inProgressBgSweepGradient);
        canvas.drawArc(rectF, this.startAngle, this.originalSweepAngle, false, this.inProgressPaint);
        SweepGradient sweepGradient2 = new SweepGradient(rectF.centerX(), rectF.centerY(), this.colors, (float[]) null);
        this.inProgressSweepGradient = sweepGradient2;
        sweepGradient2.setLocalMatrix(matrix);
        this.inProgressPaint.setShader(this.inProgressSweepGradient);
        canvas.drawArc(rectF, this.startAngle, (this.originalSweepAngle / 100.0f) * this.inProgress, false, this.inProgressPaint);
    }

    private void drawOutProgress(Canvas canvas) {
        this.outProgressPaint.setShader(null);
        this.outProgressPaint.setColor(getResources().getColor(this.outProgressColor));
        int i = this.outProgressBgWidth;
        RectF rectF = new RectF(i, i, this.mWidth - i, this.mHeight - i);
        canvas.drawArc(rectF, this.startAngle, this.originalSweepAngle, false, this.outProgressPaint);
        if (this.d3Record.getState().getPim() != 0) {
            this.outProgressPaint.setColor(CommonUtils.getColorById(R.color.d3_light_green));
        } else {
            this.outProgressPaint.setColor(CommonUtils.getColorById(R.color.d3_offline_progress_gray));
        }
        canvas.drawArc(rectF, this.startAngle, this.originalSweepAngle, false, this.outProgressPaint);
        Matrix matrix = new Matrix();
        matrix.setRotate(90.0f, rectF.centerX(), rectF.centerY());
        SweepGradient sweepGradient = new SweepGradient(rectF.centerX(), rectF.centerY(), this.colors, (float[]) null);
        sweepGradient.setLocalMatrix(matrix);
        this.outProgressPaint.setShader(sweepGradient);
        this.outProgressPaint.setStrokeWidth(this.outProgressBgWidth);
        canvas.drawArc(rectF, this.startAngle, (this.originalSweepAngle / 100.0f) * this.outProgress, false, this.outProgressPaint);
    }

    public void setDatas(D3Record d3Record) {
        this.d3Record = d3Record;
        if (d3Record.getState().getCharge() == 1) {
            this.batteryIcon = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.d3_battery_charging);
        } else {
            int batteryPower = d3Record.getState().getBatteryPower();
            if (batteryPower == 0 || batteryPower == 1) {
                this.batteryIcon = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.d3_battery_one);
            } else if (batteryPower == 2) {
                this.batteryIcon = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.d3_battery_two);
            } else if (batteryPower == 3) {
                this.batteryIcon = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.d3_battery_three);
            } else if (batteryPower == 4) {
                this.batteryIcon = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.d3_battery_max);
            }
        }
        calcValueAndColor();
        postInvalidate();
    }

    public void setD3Record(D3Record d3Record) {
        this.d3Record = d3Record;
        if (d3Record.getState().getCharge() == 1) {
            this.batteryIcon = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.d3_battery_charging);
        } else {
            int batteryPower = d3Record.getState().getBatteryPower();
            if (batteryPower == 0 || batteryPower == 1) {
                this.batteryIcon = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.d3_battery_one);
            } else if (batteryPower == 2) {
                this.batteryIcon = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.d3_battery_two);
            } else if (batteryPower == 3) {
                this.batteryIcon = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.d3_battery_three);
            } else if (batteryPower == 4) {
                this.batteryIcon = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.d3_battery_max);
            }
        }
        postInvalidate();
    }

    public int getOutProgressBgColor() {
        return this.outProgressBgColor;
    }

    public void setOutProgressBgColor(int i) {
        this.outProgressBgColor = i;
        postInvalidate();
    }

    public int getOutProgressColor() {
        return this.outProgressColor;
    }

    public void setOutProgressColor(int i) {
        this.outProgressColor = i;
        postInvalidate();
    }

    public int getBackgroundColor() {
        return this.backgroundColor;
    }

    public float getOutProgress() {
        return this.outProgress;
    }

    public void setOutProgress(float f) {
        this.outProgress = f;
        postInvalidate();
    }

    public float getInProgress() {
        return this.inProgress;
    }

    public void setInProgress(float f) {
        this.inProgress = f;
        postInvalidate();
    }

    public int getInProgressBgWidth() {
        return this.inProgressBgWidth;
    }

    public void setInProgressBgWidth(int i) {
        this.inProgressBgWidth = i;
        postInvalidate();
    }

    public int getInProgressBgColor() {
        return this.inProgressBgColor;
    }

    public void setInProgressBgColor(int i) {
        this.inProgressBgColor = i;
        this.inProgressBgSweepGradient = null;
        postInvalidate();
    }

    public int getInProgressColor() {
        return this.inProgressColor;
    }

    public void setInProgressColor(int i) {
        this.inProgressColor = i;
        this.inProgressSweepGradient = null;
        postInvalidate();
    }

    private void calcValueAndColor() {
        if (this.d3Record.getState().getFeedState() == null) {
            this.outProgress = 0.0f;
            this.inProgress = 0.0f;
            this.centerText = "--";
        } else {
            this.outProgress = ((this.d3Record.getState().getFeedState().getEatAmountTotal() * 1.0f) / (this.d3Record.getState().getFeedState().getPlanAmountTotal() == 0 ? 1 : this.d3Record.getState().getFeedState().getPlanAmountTotal())) * 100.0f;
            float weight = ((this.d3Record.getState().getWeight() * 1.0f) / 200.0f) * 100.0f;
            this.inProgress = weight;
            if (weight > 100.0f) {
                this.inProgress = 100.0f;
            }
            if (this.outProgress > 100.0f) {
                this.outProgress = 100.0f;
            }
            this.centerText = String.valueOf(this.d3Record.getState().getFeedState().getEatAmountTotal());
        }
        if (this.d3Record.getState().getPim() == 0) {
            this.centerText = "--";
            int i = R.color.walk_text;
            this.outProgressColor = i;
            int[] iArr = this.colors;
            iArr[0] = -7104606;
            iArr[1] = -3223858;
            int[] iArr2 = this.bgColors;
            iArr2[0] = -1644826;
            iArr2[1] = -1644826;
            this.textColor = i;
            return;
        }
        int i2 = R.color.d3_main_green;
        this.outProgressColor = i2;
        int[] iArr3 = this.colors;
        iArr3[0] = -14631267;
        iArr3[1] = -13442636;
        int[] iArr4 = this.bgColors;
        iArr4[0] = 452306467;
        iArr4[1] = 452306467;
        this.textColor = i2;
    }

    public void startFeedAni() {
        if (this.startFeedAni) {
            return;
        }
        this.startFeedAni = true;
        this.animationStep = 1;
        this.animationSweepAngle = (this.originalSweepAngle / 100.0f) * this.outProgress;
        startNextAni();
    }

    public void stopFeedAni() {
        this.startFeedAni = false;
        this.animationStep = 1;
        this.animationSweepAngle = this.outProgress;
        float[] fArr = this.aniPos;
        fArr[0] = 0.5f;
        fArr[1] = 0.75f;
        ValueAnimator valueAnimator = this.stepThreeAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
            this.stepThreeAnimator = null;
        }
        ValueAnimator valueAnimator2 = this.stepOneAnimator;
        if (valueAnimator2 != null) {
            valueAnimator2.cancel();
            this.stepOneAnimator = null;
        }
        ValueAnimator valueAnimator3 = this.stepTwoAnimator;
        if (valueAnimator3 != null) {
            valueAnimator3.cancel();
            this.stepTwoAnimator = null;
        }
    }

    @Override // android.view.View
    public void setBackgroundColor(int i) {
        this.backgroundColor = i;
        postInvalidate();
    }

    @Override // android.view.View
    public void onDetachedFromWindow() {
        Disposable disposable = this.disposable;
        if (disposable != null && !disposable.isDisposed()) {
            this.disposable.dispose();
        }
        super.onDetachedFromWindow();
    }

    public void setViewClick(ViewClick viewClick) {
        this.viewClick = viewClick;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        ViewClick viewClick;
        ViewClick viewClick2;
        int action = motionEvent.getAction();
        if (action == 0) {
            this.downX = (int) motionEvent.getX();
            this.downY = (int) motionEvent.getY();
        } else if (action == 1) {
            int i = this.weightRectFMarginLength;
            RectF rectF = new RectF(i, i, this.mWidth - i, this.mHeight - i);
            if (Math.abs(motionEvent.getX() - this.downX) <= 20.0f && Math.abs(motionEvent.getY() - this.downY) <= 20.0f && !this.startFeedAni) {
                if (this.helpIconRect.contains((int) motionEvent.getX(), (int) motionEvent.getY())) {
                    ViewClick viewClick3 = this.viewClick;
                    if (viewClick3 != null) {
                        viewClick3.onAdjustBtnClick();
                    }
                } else {
                    int iSqrt = (int) Math.sqrt(Math.pow(Math.abs(motionEvent.getX() - rectF.centerX()), 2.0d) + Math.pow(Math.abs(motionEvent.getY() - rectF.centerY()), 2.0d));
                    int i2 = this.mHeight;
                    int i3 = this.outProgressBgWidth;
                    if (iSqrt <= (i2 / 2) - (i3 / 2) && iSqrt >= ((i2 / 2) - (i3 * 2)) - (this.interval / 2)) {
                        if (motionEvent.getY() < (this.mHeight / 2.0f) + ((int) (((double) ((i2 / 2) - i3)) * Math.cos(Math.toRadians(60.0d)))) && (viewClick2 = this.viewClick) != null) {
                            viewClick2.onOutProgressViewClick();
                        }
                    }
                    int i4 = this.mHeight;
                    int i5 = this.weightRectFMarginLength;
                    if (iSqrt <= ((i4 / 2) - i5) + (this.interval / 2)) {
                        int i6 = this.inProgressBgWidth;
                        if (iSqrt >= (((i4 / 2) - i5) - i6) - (i6 / 2) && this.viewClick != null) {
                            if (motionEvent.getY() < (this.mHeight / 2.0f) + ((int) (((double) ((i4 / 2) - i5)) * Math.cos(Math.toRadians(60.0d)))) && (viewClick = this.viewClick) != null) {
                                viewClick.onInProgressViewClick();
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
}

package com.petkit.android.activities.petkitBleDevice.d4s.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import androidx.annotation.Nullable;
import com.jess.arms.utils.ArmsUtils;
import com.petkit.android.activities.petkitBleDevice.d4.mode.FeederCenterViewClick;
import com.petkit.android.activities.petkitBleDevice.d4s.mode.D4sCenterViewCallBack;
import com.petkit.android.activities.petkitBleDevice.d4s.mode.D4sRecord;
import com.petkit.android.activities.petkitBleDevice.d4s.utils.D4sUtils;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.LogcatStorageHelper;
import com.petkit.android.utils.PetkitLog;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.oversea.R;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;
import net.sourceforge.pinyin4j.ChineseToPinyinResource;

/* JADX INFO: loaded from: classes4.dex */
public class D4sCenterProgressView extends View {
    private String TAG;
    private int aniColor;
    private int[] aniColors;
    private float[] aniPos;
    private Paint animationPaint;
    private int animationStep;
    private float animationSweepAngle;
    private int backgroundColor;
    private int[] bgColors;
    private int bigArcHeight;
    private int bigArcWidth;
    private int bigThinArcLineWidth;
    private int bigThinArcMarginTop;
    private D4sCenterViewCallBack callBack;
    private String centerText;
    private int[] colors;
    private Context context;
    private D4sRecord d4Record;
    private Disposable disposable1;
    private int feeding;
    private int feedingAmount;
    private int interval;
    private int mHeight;
    private int mWidth;
    private float originalSweepAngle;
    private int outBigProgressWidth;
    private float outProgress;
    private int outProgressBg2Color;
    private int outProgressBgColor;
    private int outProgressBgWidth;
    private int outProgressColor;
    private Paint outProgressPaint;
    private int outSmallProgressWidth;
    private float[] pos;
    private float ratio;
    private float smallArcAnimationSweepAngle;
    private String smallArcCenterText;
    private int smallArcHeight;
    private int smallArcLineWidth;
    private Paint smallArcPaint;
    private float smallArcProgress;
    private float smallArcSweepAngle;
    private int smallArcTop;
    private int smallArcWidth;
    private int smallThinArcLineWidth;
    private int smallThinArcMarginTop;
    private float startAngle;
    private boolean startFeedAni;
    private ValueAnimator stepOneAnimator;
    private ValueAnimator stepTwoAnimator;
    private int textColor;
    private Paint textPaint;
    private FeederCenterViewClick viewClick;

    public void setCallBack(D4sCenterViewCallBack d4sCenterViewCallBack) {
        this.callBack = d4sCenterViewCallBack;
    }

    public int getFeedingAmount() {
        return this.feedingAmount;
    }

    public void setFeedingAmount(int i) {
        this.feedingAmount = i;
    }

    public D4sCenterProgressView(Context context) {
        super(context);
        this.TAG = "D4sCenterProgressView";
        this.outProgressBgWidth = 30;
        this.outBigProgressWidth = 30;
        this.outSmallProgressWidth = 30;
        this.outProgressBgColor = R.color.d4_main_light_yellow;
        this.outProgressBg2Color = R.color.d4s_light_yellow;
        this.outProgressColor = R.color.d4_main_yellow;
        this.backgroundColor = R.color.d3_bg_gray;
        this.aniColor = R.color.d4_light_yellow;
        this.textColor = R.color.white;
        this.startAngle = 150.0f;
        this.originalSweepAngle = 240.0f;
        this.outProgress = 50.0f;
        this.interval = 20;
        this.centerText = "--";
        this.animationSweepAngle = 50.0f;
        this.animationStep = 1;
        this.feedingAmount = 0;
        this.smallArcSweepAngle = 163.0f;
        this.smallArcProgress = 50.0f;
        this.context = context;
        initView();
    }

    public D4sCenterProgressView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.TAG = "D4sCenterProgressView";
        this.outProgressBgWidth = 30;
        this.outBigProgressWidth = 30;
        this.outSmallProgressWidth = 30;
        this.outProgressBgColor = R.color.d4_main_light_yellow;
        this.outProgressBg2Color = R.color.d4s_light_yellow;
        this.outProgressColor = R.color.d4_main_yellow;
        this.backgroundColor = R.color.d3_bg_gray;
        this.aniColor = R.color.d4_light_yellow;
        this.textColor = R.color.white;
        this.startAngle = 150.0f;
        this.originalSweepAngle = 240.0f;
        this.outProgress = 50.0f;
        this.interval = 20;
        this.centerText = "--";
        this.animationSweepAngle = 50.0f;
        this.animationStep = 1;
        this.feedingAmount = 0;
        this.smallArcSweepAngle = 163.0f;
        this.smallArcProgress = 50.0f;
        this.context = context;
        initView();
    }

    public D4sCenterProgressView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.TAG = "D4sCenterProgressView";
        this.outProgressBgWidth = 30;
        this.outBigProgressWidth = 30;
        this.outSmallProgressWidth = 30;
        this.outProgressBgColor = R.color.d4_main_light_yellow;
        this.outProgressBg2Color = R.color.d4s_light_yellow;
        this.outProgressColor = R.color.d4_main_yellow;
        this.backgroundColor = R.color.d3_bg_gray;
        this.aniColor = R.color.d4_light_yellow;
        this.textColor = R.color.white;
        this.startAngle = 150.0f;
        this.originalSweepAngle = 240.0f;
        this.outProgress = 50.0f;
        this.interval = 20;
        this.centerText = "--";
        this.animationSweepAngle = 50.0f;
        this.animationStep = 1;
        this.feedingAmount = 0;
        this.smallArcSweepAngle = 163.0f;
        this.smallArcProgress = 50.0f;
        this.context = context;
        initView();
    }

    private void initView() {
        this.outProgressBgWidth = ArmsUtils.dip2px(this.context, 20.0f);
        this.smallArcLineWidth = ArmsUtils.dip2px(this.context, 15.0f);
        this.outBigProgressWidth = ArmsUtils.dip2px(this.context, 18.0f);
        this.outSmallProgressWidth = ArmsUtils.dip2px(this.context, 13.0f);
        this.bigThinArcLineWidth = ArmsUtils.dip2px(this.context, 2.0f);
        this.smallThinArcLineWidth = ArmsUtils.dip2px(this.context, 1.0f);
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
        this.smallArcPaint = paint4;
        paint4.setAntiAlias(true);
        this.smallArcPaint.setColor(getResources().getColor(this.outProgressBgColor));
        this.smallArcPaint.setStrokeCap(cap);
        this.smallArcPaint.setStyle(style);
        this.smallArcPaint.setStrokeWidth(this.smallArcLineWidth);
        Paint paint5 = new Paint();
        this.animationPaint = paint5;
        paint5.setAntiAlias(true);
        this.animationPaint.setStrokeCap(cap);
        this.animationPaint.setStyle(style);
        this.animationPaint.setStrokeWidth(this.outProgressBgWidth);
        this.colors = new int[2];
        this.bgColors = new int[]{452306467, 452306467};
        this.pos = new float[]{1.0f, 0.0f};
        this.aniPos = new float[]{0.5f, 0.75f};
        this.aniColors = new int[]{-2492431, -14239848};
        Paint paint6 = new Paint();
        this.textPaint = paint6;
        paint6.setTextAlign(Paint.Align.CENTER);
    }

    @Override // android.view.View
    public void onMeasure(int i, int i2) {
        int mode = View.MeasureSpec.getMode(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        View.MeasureSpec.getSize(i);
        View.MeasureSpec.getSize(i2);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) this.context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int iDip2px = displayMetrics.widthPixels - ArmsUtils.dip2px(this.context, 80.0f);
        int iRound = Math.round((iDip2px / 2.94f) * 1.94f);
        this.bigArcWidth = iRound;
        this.bigArcHeight = iRound;
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(iDip2px, mode);
        int iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(this.bigArcHeight, mode2);
        this.ratio = (this.bigArcWidth * 1.0f) / ArmsUtils.dip2px(this.context, 194.0f);
        int iRound2 = Math.round(ArmsUtils.dip2px(this.context, 117.0f) * this.ratio);
        this.smallArcWidth = iRound2;
        this.smallArcHeight = iRound2;
        this.smallArcTop = Math.round(ArmsUtils.dip2px(this.context, 61.5f) * this.ratio);
        this.bigThinArcMarginTop = Math.round(ArmsUtils.dip2px(this.context, 10.0f) * this.ratio);
        this.smallThinArcMarginTop = Math.round(ArmsUtils.dip2px(this.context, 4.5f) * this.ratio);
        super.onMeasure(iMakeMeasureSpec, iMakeMeasureSpec2);
    }

    @Override // android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.mWidth = getMeasuredWidth();
        this.mHeight = getMeasuredHeight();
        PetkitLog.d(this.TAG, String.format("mWidth:%d,mHeight:%d,bigArcWidth:%d,bigArcHeight:%d,smallArcWidth:%d,smallArcHeight:%d,smallArcTop:%d", Integer.valueOf(this.mWidth), Integer.valueOf(this.mHeight), Integer.valueOf(this.bigArcWidth), Integer.valueOf(this.bigArcHeight), Integer.valueOf(this.smallArcWidth), Integer.valueOf(this.smallArcHeight), Integer.valueOf(this.smallArcTop)));
        LogcatStorageHelper.addLog(this.TAG + ChineseToPinyinResource.Field.COMMA + String.format("mWidth:%d,mHeight:%d,bigArcWidth:%d,bigArcHeight:%d,smallArcWidth:%d,smallArcHeight:%d,smallArcTop:%d", Integer.valueOf(this.mWidth), Integer.valueOf(this.mHeight), Integer.valueOf(this.bigArcWidth), Integer.valueOf(this.bigArcHeight), Integer.valueOf(this.smallArcWidth), Integer.valueOf(this.smallArcHeight), Integer.valueOf(this.smallArcTop)));
        D4sCenterViewCallBack d4sCenterViewCallBack = this.callBack;
        if (d4sCenterViewCallBack != null) {
            d4sCenterViewCallBack.initSizeWithParams(this.ratio, this.bigArcHeight, this.smallArcTop, this.smallArcWidth);
        }
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(this.context.getResources().getColor(this.backgroundColor));
        canvas.save();
        if (this.startFeedAni) {
            int i = this.animationStep;
            if (i == 1) {
                int i2 = this.feeding;
                if (i2 == 1) {
                    drawSmallArcAniOutProgress(canvas);
                    drawOutProgress(canvas);
                } else if (i2 == 2) {
                    drawAniOutProgress(canvas);
                    drawLittleOutProgress(canvas);
                } else if (i2 == 3) {
                    drawAniOutProgress(canvas);
                    drawSmallArcAniOutProgress(canvas);
                    drawOutProgress(canvas);
                    drawLittleOutProgress(canvas);
                }
            } else if (i == 2) {
                int i3 = this.feeding;
                if (i3 == 1) {
                    drawSmallArcAniRunProgress(canvas);
                    drawOutProgress(canvas);
                } else if (i3 == 2) {
                    drawAniRunProgress(canvas);
                    drawLittleOutProgress(canvas);
                } else if (i3 == 3) {
                    drawAniRunProgress(canvas);
                    drawSmallArcAniRunProgress(canvas);
                    drawOutProgress(canvas);
                    drawLittleOutProgress(canvas);
                }
            }
        } else {
            drawOutProgress(canvas);
            drawLittleOutProgress(canvas);
        }
        drawThinArc(canvas);
    }

    private void drawThinArc(Canvas canvas) {
        boolean z = this.d4Record.getState().getPim() != 0;
        int i = this.bigThinArcLineWidth / 2;
        int i2 = this.mWidth - this.bigArcWidth;
        int i3 = this.outProgressBgWidth;
        int i4 = this.bigThinArcMarginTop;
        RectF rectF = new RectF(i2 + i3 + i4 + i, i3 + i4 + i, ((r5 - i3) - i4) - i, ((this.bigArcHeight - i3) - i4) - i);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(getResources().getColor(R.color.big_thin_gradient_dark_color));
        Paint.Cap cap = Paint.Cap.ROUND;
        paint.setStrokeCap(cap);
        Paint.Style style = Paint.Style.STROKE;
        paint.setStyle(style);
        paint.setStrokeWidth(this.bigThinArcLineWidth);
        paint.setAlpha(30);
        Matrix matrix = new Matrix();
        matrix.setRotate(90.0f, rectF.centerX(), rectF.centerY());
        SweepGradient sweepGradient = new SweepGradient(rectF.centerX(), rectF.centerY(), new int[]{this.context.getResources().getColor(R.color.big_thin_gradient_light_color), this.context.getResources().getColor(z ? R.color.big_thin_gradient_dark_color : R.color.walk_text), this.context.getResources().getColor(R.color.big_thin_gradient_light_color)}, new float[]{0.25f, 0.5f, 0.75f});
        sweepGradient.setLocalMatrix(matrix);
        paint.setShader(sweepGradient);
        canvas.drawArc(rectF, this.startAngle, this.originalSweepAngle, false, paint);
        int i5 = this.smallThinArcLineWidth / 2;
        int i6 = this.smallArcLineWidth;
        int i7 = this.smallThinArcMarginTop;
        int i8 = this.smallArcTop;
        RectF rectF2 = new RectF(i6 + i7 + i5, i8 + i6 + i7 + i5, ((this.smallArcWidth - i6) - i7) - i5, (((i8 + this.smallArcHeight) - i6) - i7) - i5);
        Paint paint2 = new Paint();
        paint2.setAntiAlias(true);
        paint2.setColor(getResources().getColor(R.color.small_thin_gradient_dark_color));
        paint2.setStrokeCap(cap);
        paint2.setStyle(style);
        paint2.setStrokeWidth(this.smallThinArcLineWidth);
        paint2.setAlpha(30);
        SweepGradient sweepGradient2 = new SweepGradient(rectF2.centerX(), rectF2.centerY(), new int[]{this.context.getResources().getColor(R.color.small_thin_gradient_light_color), this.context.getResources().getColor(z ? R.color.small_thin_gradient_dark_color : R.color.walk_text), this.context.getResources().getColor(R.color.small_thin_gradient_light_color)}, new float[]{0.25f, 0.5f, 0.75f});
        sweepGradient2.setLocalMatrix(matrix);
        paint.setShader(sweepGradient2);
        canvas.drawArc(rectF2, this.startAngle, this.originalSweepAngle, false, paint2);
    }

    private void drawAniRunProgress(Canvas canvas) {
        this.animationSweepAngle = (this.originalSweepAngle / 100.0f) * this.outProgress;
        this.animationPaint.setShader(null);
        this.animationPaint.setColor(getResources().getColor(this.outProgressColor));
        RectF bigArcRectF = getBigArcRectF();
        this.animationPaint.setColor(getResources().getColor(this.outProgressBgColor));
        this.animationPaint.setStrokeWidth(this.outBigProgressWidth);
        canvas.drawArc(bigArcRectF, this.startAngle, this.originalSweepAngle, false, this.animationPaint);
        this.animationPaint.setColor(getResources().getColor(this.aniColor));
        this.animationPaint.setStrokeWidth(this.outProgressBgWidth);
        float f = this.startAngle;
        float f2 = this.originalSweepAngle / 100.0f;
        float f3 = this.outProgress;
        canvas.drawArc(bigArcRectF, f + (f2 * f3), 25.5f - (f3 / 4.0f), false, this.animationPaint);
    }

    private void drawSmallArcAniRunProgress(Canvas canvas) {
        this.smallArcAnimationSweepAngle = (this.smallArcSweepAngle / 100.0f) * this.smallArcProgress;
        this.animationPaint.setShader(null);
        this.animationPaint.setColor(getResources().getColor(this.outProgressColor));
        RectF smallArcRectF = getSmallArcRectF();
        this.animationPaint.setColor(getResources().getColor(this.outProgressBg2Color));
        this.animationPaint.setStrokeWidth(this.outSmallProgressWidth);
        canvas.drawArc(smallArcRectF, this.startAngle, this.smallArcSweepAngle, false, this.animationPaint);
        this.animationPaint.setColor(getResources().getColor(this.aniColor));
        this.animationPaint.setStrokeWidth(this.smallArcLineWidth);
        float f = this.startAngle;
        float f2 = this.smallArcSweepAngle / 100.0f;
        float f3 = this.smallArcProgress;
        canvas.drawArc(smallArcRectF, f + (f2 * f3), 25.5f - (f3 / 4.0f), false, this.animationPaint);
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
                this.stepOneAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4s.widget.D4sCenterProgressView.1
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        D4sCenterProgressView.this.outProgress = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                        D4sCenterProgressView d4sCenterProgressView = D4sCenterProgressView.this;
                        d4sCenterProgressView.smallArcProgress = d4sCenterProgressView.outProgress;
                        D4sCenterProgressView.this.postInvalidate();
                    }
                });
                this.stepOneAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.petkit.android.activities.petkitBleDevice.d4s.widget.D4sCenterProgressView.2
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        super.onAnimationEnd(animator);
                        D4sCenterProgressView.this.animationStep = 2;
                        D4sCenterProgressView.this.startNextAni();
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
            this.stepTwoAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4s.widget.D4sCenterProgressView.3
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                    D4sCenterProgressView d4sCenterProgressView = D4sCenterProgressView.this;
                    if (fFloatValue > 100.0f) {
                        fFloatValue = 100.0f;
                    }
                    d4sCenterProgressView.outProgress = fFloatValue;
                    D4sCenterProgressView d4sCenterProgressView2 = D4sCenterProgressView.this;
                    d4sCenterProgressView2.smallArcProgress = d4sCenterProgressView2.outProgress;
                    D4sCenterProgressView.this.postInvalidate();
                }
            });
            this.stepTwoAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.petkit.android.activities.petkitBleDevice.d4s.widget.D4sCenterProgressView.4
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    D4sCenterProgressView.this.disposable1 = Observable.just("").delay(500L, TimeUnit.MILLISECONDS).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<String>() { // from class: com.petkit.android.activities.petkitBleDevice.d4s.widget.D4sCenterProgressView.4.1
                        @Override // io.reactivex.functions.Consumer
                        public void accept(String str) throws Exception {
                            D4sCenterProgressView.this.stepTwoAnimator.start();
                        }
                    });
                }
            });
            this.stepTwoAnimator.start();
        }
    }

    private void drawAniOutProgress(Canvas canvas) {
        this.animationSweepAngle = (this.originalSweepAngle / 100.0f) * this.outProgress;
        this.animationPaint.setShader(null);
        this.animationPaint.setColor(getResources().getColor(this.outProgressColor));
        RectF bigArcRectF = getBigArcRectF();
        this.animationPaint.setColor(getResources().getColor(this.outProgressBgColor));
        this.animationPaint.setStrokeWidth(this.outProgressBgWidth);
        canvas.drawArc(bigArcRectF, this.startAngle, this.originalSweepAngle, false, this.animationPaint);
        this.animationPaint.setColor(getResources().getColor(this.aniColor));
        this.animationPaint.setStrokeWidth(this.outProgressBgWidth);
        canvas.drawArc(bigArcRectF, this.startAngle, this.animationSweepAngle, false, this.animationPaint);
    }

    private void drawSmallArcAniOutProgress(Canvas canvas) {
        this.smallArcAnimationSweepAngle = (this.smallArcSweepAngle / 100.0f) * this.smallArcProgress;
        this.animationPaint.setShader(null);
        this.animationPaint.setColor(getResources().getColor(this.outProgressColor));
        RectF smallArcRectF = getSmallArcRectF();
        this.animationPaint.setColor(getResources().getColor(this.outProgressBgColor));
        this.animationPaint.setStrokeWidth(this.outProgressBgWidth);
        canvas.drawArc(smallArcRectF, this.startAngle, this.smallArcSweepAngle, false, this.animationPaint);
        this.animationPaint.setColor(getResources().getColor(this.aniColor));
        this.animationPaint.setStrokeWidth(this.outProgressBgWidth);
        canvas.drawArc(smallArcRectF, this.startAngle, this.smallArcAnimationSweepAngle, false, this.animationPaint);
    }

    private void drawCenterText(Canvas canvas) {
        int i;
        Resources resources;
        int i2;
        int i3 = this.outProgressBgWidth;
        int i4 = this.interval;
        RectF rectF = new RectF(i3 + i4, i3 + i4, (this.mWidth - i3) - i4, (this.mHeight - i3) - i4);
        this.textPaint.setTypeface(Typeface.DEFAULT_BOLD);
        this.textPaint.setColor(getResources().getColor(this.textColor));
        if (this.d4Record.getState().getPim() != 0 && this.d4Record.getState().getFeeding() == 1) {
            this.textPaint.setTextSize(60.0f);
            canvas.drawText(this.context.getResources().getString(R.string.Feeding), rectF.centerX(), rectF.centerY(), this.textPaint);
        } else {
            this.textPaint.setTextSize(120.0f);
            canvas.drawText(this.centerText, rectF.centerX(), rectF.centerY(), this.textPaint);
            Rect rect = new Rect();
            Paint paint = this.textPaint;
            String str = this.centerText;
            paint.getTextBounds(str, 0, str.length(), rect);
            this.textPaint.setTextSize(50.0f);
            try {
                i = Integer.parseInt(this.centerText);
            } catch (Exception e) {
                PetkitLog.d(e.getMessage());
                i = 0;
            }
            int i5 = "zh_CN".equalsIgnoreCase(UserInforUtils.getLanguageLocaleString(CommonUtils.getAppContext())) ? 15 : 50;
            if (i > 1) {
                resources = getResources();
                i2 = R.string.Feeders_unit;
            } else {
                resources = getResources();
                i2 = R.string.Feeder_unit;
            }
            canvas.drawText(resources.getString(i2), rectF.centerX() + rect.width() + i5, rectF.centerY(), this.textPaint);
        }
        if (this.d4Record.getState().getFeeding() != 1) {
            Rect rect2 = new Rect();
            this.textPaint.setColor(CommonUtils.getColorById(R.color.walk_text));
            this.textPaint.setTextSize(ArmsUtils.dip2px(this.context, 13.0f));
            Paint paint2 = this.textPaint;
            String str2 = this.centerText;
            paint2.getTextBounds(str2, 0, str2.length(), rect2);
            this.context.getResources().getString(R.string.Feeder_amount_feeded);
            FeederCenterViewClick feederCenterViewClick = this.viewClick;
            if (feederCenterViewClick != null) {
                feederCenterViewClick.refreshPromptPos(rectF.centerY() + 30.0f);
            }
            this.feedingAmount = 0;
        }
    }

    private void drawOutProgress(Canvas canvas) {
        this.outProgressPaint.setShader(null);
        this.outProgressPaint.setStrokeWidth(this.outBigProgressWidth);
        RectF bigArcRectF = getBigArcRectF();
        this.outProgressPaint.setColor(this.context.getResources().getColor(this.outProgressBgColor));
        canvas.drawArc(bigArcRectF, this.startAngle, this.originalSweepAngle, false, this.outProgressPaint);
        Matrix matrix = new Matrix();
        matrix.setRotate(90.0f, bigArcRectF.centerX(), bigArcRectF.centerY());
        SweepGradient sweepGradient = new SweepGradient(bigArcRectF.centerX(), bigArcRectF.centerY(), this.colors, new float[]{0.0f, 1.0f});
        sweepGradient.setLocalMatrix(matrix);
        this.outProgressPaint.setShader(sweepGradient);
        this.outProgressPaint.setStrokeWidth(this.outProgressBgWidth);
        canvas.drawArc(bigArcRectF, this.startAngle, (this.originalSweepAngle / 100.0f) * this.outProgress, false, this.outProgressPaint);
    }

    private void drawLittleOutProgress(Canvas canvas) {
        this.smallArcPaint.setShader(null);
        RectF smallArcRectF = getSmallArcRectF();
        this.smallArcPaint.setColor(this.context.getResources().getColor(this.outProgressBg2Color));
        this.smallArcPaint.setStrokeWidth(this.outSmallProgressWidth);
        canvas.drawArc(smallArcRectF, this.startAngle, this.smallArcSweepAngle, false, this.smallArcPaint);
        Matrix matrix = new Matrix();
        matrix.setRotate(90.0f, smallArcRectF.centerX(), smallArcRectF.centerY());
        SweepGradient sweepGradient = new SweepGradient(smallArcRectF.centerX(), smallArcRectF.centerY(), this.colors, new float[]{0.0f, 1.0f});
        sweepGradient.setLocalMatrix(matrix);
        this.smallArcPaint.setShader(sweepGradient);
        this.smallArcPaint.setStrokeWidth(this.smallArcLineWidth);
        canvas.drawArc(smallArcRectF, this.startAngle, (this.smallArcSweepAngle / 100.0f) * this.outProgress, false, this.smallArcPaint);
    }

    private RectF getBigArcRectF() {
        int i = this.outProgressBgWidth / 2;
        int i2 = this.mWidth;
        return new RectF((i2 - this.bigArcWidth) + i, i, i2 - i, this.bigArcHeight - i);
    }

    private RectF getSmallArcRectF() {
        int i = this.smallArcLineWidth / 2;
        int i2 = this.smallArcTop;
        return new RectF(i, i2 + i, this.smallArcWidth - i, (i2 + this.smallArcHeight) - i);
    }

    public void setDatas(D4sRecord d4sRecord) {
        this.d4Record = d4sRecord;
        calcValueAndColor();
        postInvalidate();
    }

    public void setD4Record(D4sRecord d4sRecord) {
        this.d4Record = d4sRecord;
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

    private void calcValueAndColor() {
        if (this.d4Record.getState().getFeedState() == null) {
            this.outProgress = 0.0f;
            this.smallArcProgress = 0.0f;
            this.centerText = "--";
            this.smallArcCenterText = "--";
        } else {
            int planAmountTotal2 = this.d4Record.getState().getFeedState().getPlanAmountTotal2() + this.d4Record.getState().getFeedState().getAddAmountTotal2();
            float realAmountTotal2 = this.d4Record.getState().getFeedState().getRealAmountTotal2() * 1.0f;
            if (planAmountTotal2 == 0) {
                planAmountTotal2 = 1;
            }
            float f = (realAmountTotal2 / planAmountTotal2) * 100.0f;
            this.outProgress = f;
            if (f > 100.0f) {
                this.outProgress = 100.0f;
            }
            this.centerText = D4sUtils.getAmountFormat(this.d4Record.getState().getFeedState().getRealAmountTotal2(), this.d4Record.getSettings().getFactor2());
            int planAmountTotal1 = this.d4Record.getState().getFeedState().getPlanAmountTotal1() + this.d4Record.getState().getFeedState().getAddAmountTotal1();
            float realAmountTotal1 = this.d4Record.getState().getFeedState().getRealAmountTotal1() * 1.0f;
            if (planAmountTotal1 == 0) {
                planAmountTotal1 = 1;
            }
            float f2 = (realAmountTotal1 / planAmountTotal1) * 100.0f;
            this.smallArcProgress = f2;
            if (f2 > 100.0f) {
                this.smallArcProgress = 100.0f;
            }
            this.smallArcCenterText = D4sUtils.getAmountFormat(this.d4Record.getState().getFeedState().getRealAmountTotal1(), this.d4Record.getSettings().getFactor1());
        }
        if (this.d4Record.getState().getPim() == 0) {
            this.centerText = "--";
            int i = R.color.d4_main_yellow;
            this.outProgressColor = i;
            int[] iArr = this.colors;
            iArr[0] = -295875;
            iArr[1] = -78757;
            int[] iArr2 = this.bgColors;
            iArr2[0] = -398639;
            iArr2[1] = -398639;
            this.textColor = i;
            return;
        }
        int i2 = R.color.d4_main_yellow;
        this.outProgressColor = i2;
        int[] iArr3 = this.colors;
        iArr3[0] = -295875;
        iArr3[1] = -78757;
        int[] iArr4 = this.bgColors;
        iArr4[0] = -398639;
        iArr4[1] = -398639;
        this.textColor = i2;
    }

    public void startFeedAni(int i) {
        if (this.startFeedAni) {
            return;
        }
        this.feeding = i;
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
        Disposable disposable = this.disposable1;
        if (disposable != null && !disposable.isDisposed()) {
            this.disposable1.dispose();
        }
        ValueAnimator valueAnimator = this.stepOneAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
            this.stepOneAnimator = null;
        }
        ValueAnimator valueAnimator2 = this.stepTwoAnimator;
        if (valueAnimator2 != null) {
            valueAnimator2.cancel();
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
        Disposable disposable = this.disposable1;
        if (disposable != null && !disposable.isDisposed()) {
            this.disposable1.dispose();
        }
        stopFeedAni();
        super.onDetachedFromWindow();
    }

    public void setViewClick(FeederCenterViewClick feederCenterViewClick) {
        this.viewClick = feederCenterViewClick;
    }
}

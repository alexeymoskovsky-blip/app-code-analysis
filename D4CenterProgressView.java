package com.petkit.android.activities.petkitBleDevice.d4.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
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
import com.petkit.android.activities.petkitBleDevice.d4.mode.D4Record;
import com.petkit.android.activities.petkitBleDevice.d4.mode.FeederCenterViewClick;
import com.petkit.android.activities.petkitBleDevice.d4.utils.D4Utils;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.oversea.R;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes4.dex */
public class D4CenterProgressView extends View {
    private String TAG;
    private int aniColor;
    private int[] aniColors;
    private float[] aniPos;
    private Paint animationPaint;
    private int animationStep;
    private float animationSweepAngle;
    private int backgroundColor;
    private int[] bgColors;
    private String centerText;
    private int[] colors;
    private Context context;
    private D4Record d4Record;
    private Disposable disposable1;
    private int downX;
    private int downY;
    private int feedingAmount;
    private int interval;
    private int mHeight;
    private int mWidth;
    private float originalSweepAngle;
    private float outProgress;
    private int outProgressBgColor;
    private int outProgressBgWidth;
    private int outProgressColor;
    private Paint outProgressPaint;
    private float[] pos;
    private float startAngle;
    private boolean startFeedAni;
    private ValueAnimator stepOneAnimator;
    private ValueAnimator stepTwoAnimator;
    private int textColor;
    private Paint textPaint;
    private FeederCenterViewClick viewClick;

    public int getFeedingAmount() {
        return this.feedingAmount;
    }

    public void setFeedingAmount(int i) {
        this.feedingAmount = i;
    }

    public D4CenterProgressView(Context context) {
        super(context);
        this.TAG = "D4CenterProgressView";
        this.outProgressBgWidth = 30;
        this.outProgressBgColor = R.color.d4_main_light_yellow;
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
        this.context = context;
        initView();
    }

    public D4CenterProgressView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.TAG = "D4CenterProgressView";
        this.outProgressBgWidth = 30;
        this.outProgressBgColor = R.color.d4_main_light_yellow;
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
        this.context = context;
        initView();
    }

    public D4CenterProgressView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.TAG = "D4CenterProgressView";
        this.outProgressBgWidth = 30;
        this.outProgressBgColor = R.color.d4_main_light_yellow;
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
        this.context = context;
        initView();
    }

    private void initView() {
        this.outProgressBgWidth = ArmsUtils.dip2px(this.context, 18.0f);
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
        this.animationPaint = paint4;
        paint4.setAntiAlias(true);
        this.animationPaint.setStrokeCap(cap);
        this.animationPaint.setStyle(style);
        this.animationPaint.setStrokeWidth(this.outProgressBgWidth);
        this.colors = new int[2];
        this.bgColors = new int[]{452306467, 452306467};
        this.pos = new float[]{1.0f, 0.0f};
        this.aniPos = new float[]{0.5f, 0.75f};
        this.aniColors = new int[]{-2492431, -14239848};
        Paint paint5 = new Paint();
        this.textPaint = paint5;
        paint5.setTextAlign(Paint.Align.CENTER);
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
        this.animationPaint.setColor(getResources().getColor(this.outProgressBgColor));
        this.animationPaint.setStrokeWidth(this.outProgressBgWidth);
        canvas.drawArc(rectF, this.startAngle, this.originalSweepAngle, false, this.animationPaint);
        this.animationPaint.setColor(getResources().getColor(this.aniColor));
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
                this.stepOneAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4.widget.D4CenterProgressView.1
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        D4CenterProgressView.this.outProgress = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                        D4CenterProgressView.this.postInvalidate();
                    }
                });
                this.stepOneAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.petkit.android.activities.petkitBleDevice.d4.widget.D4CenterProgressView.2
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        super.onAnimationEnd(animator);
                        D4CenterProgressView.this.animationStep = 2;
                        D4CenterProgressView.this.startNextAni();
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
            this.stepTwoAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4.widget.D4CenterProgressView.3
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                    D4CenterProgressView d4CenterProgressView = D4CenterProgressView.this;
                    if (fFloatValue > 100.0f) {
                        fFloatValue = 100.0f;
                    }
                    d4CenterProgressView.outProgress = fFloatValue;
                    D4CenterProgressView.this.postInvalidate();
                }
            });
            this.stepTwoAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.petkit.android.activities.petkitBleDevice.d4.widget.D4CenterProgressView.4
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    D4CenterProgressView.this.disposable1 = Observable.just("").delay(500L, TimeUnit.MILLISECONDS).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<String>() { // from class: com.petkit.android.activities.petkitBleDevice.d4.widget.D4CenterProgressView.4.1
                        @Override // io.reactivex.functions.Consumer
                        public void accept(String str) throws Exception {
                            D4CenterProgressView.this.stepTwoAnimator.start();
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
        int i = this.outProgressBgWidth;
        RectF rectF = new RectF(i, i, this.mWidth - i, this.mHeight - i);
        canvas.drawArc(rectF, this.startAngle, this.animationSweepAngle, false, this.animationPaint);
        this.animationPaint.setColor(getResources().getColor(this.outProgressBgColor));
        this.animationPaint.setStrokeWidth(this.outProgressBgWidth);
        canvas.drawArc(rectF, this.startAngle, this.originalSweepAngle, false, this.animationPaint);
        this.animationPaint.setColor(getResources().getColor(this.aniColor));
        this.animationPaint.setStrokeWidth(this.outProgressBgWidth);
        canvas.drawArc(rectF, this.startAngle, this.animationSweepAngle, false, this.animationPaint);
    }

    private void drawCenterText(Canvas canvas) {
        int i = this.outProgressBgWidth;
        int i2 = this.interval;
        RectF rectF = new RectF(i + i2, i + i2, (this.mWidth - i) - i2, (this.mHeight - i) - i2);
        this.textPaint.setTypeface(Typeface.DEFAULT_BOLD);
        this.textPaint.setColor(getResources().getColor(this.textColor));
        if (this.d4Record.getState().getPim() != 0 && this.d4Record.getState().getFeeding() == 1) {
            this.textPaint.setTextSize(60.0f);
            canvas.drawText(this.context.getResources().getString(R.string.Feeding), rectF.centerX(), rectF.centerY(), this.textPaint);
        } else {
            if ("pt_PT".equalsIgnoreCase(UserInforUtils.getLanguageLocaleString(CommonUtils.getAppContext())) || "fr_FR".equalsIgnoreCase(UserInforUtils.getLanguageLocaleString(CommonUtils.getAppContext()))) {
                this.textPaint.setTextSize(80.0f);
            } else {
                this.textPaint.setTextSize(120.0f);
            }
            canvas.drawText(this.centerText, rectF.centerX(), rectF.centerY(), this.textPaint);
            Rect rect = new Rect();
            Paint paint = this.textPaint;
            String str = this.centerText;
            paint.getTextBounds(str, 0, str.length(), rect);
            if ("pt_PT".equalsIgnoreCase(UserInforUtils.getLanguageLocaleString(CommonUtils.getAppContext())) || "fr_FR".equalsIgnoreCase(UserInforUtils.getLanguageLocaleString(CommonUtils.getAppContext()))) {
                this.textPaint.setTextSize(30.0f);
            } else {
                this.textPaint.setTextSize(50.0f);
            }
            this.textPaint.getTextBounds(getResources().getString(R.string.Feeder_unit), 0, getResources().getString(R.string.Feeder_unit).length(), new Rect());
            canvas.drawText(getResources().getString(R.string.Feeder_unit), rectF.centerX() + (rect.width() / 2) + (r5.width() / 2) + 10.0f, rectF.centerY(), this.textPaint);
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
        this.outProgressPaint.setColor(getResources().getColor(this.outProgressColor));
        int i = this.outProgressBgWidth;
        RectF rectF = new RectF(i, i, this.mWidth - i, this.mHeight - i);
        if (this.d4Record.getState().getPim() != 0) {
            this.outProgressPaint.setColor(CommonUtils.getColorById(this.outProgressBgColor));
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

    public void setDatas(D4Record d4Record) {
        this.d4Record = d4Record;
        calcValueAndColor();
        postInvalidate();
    }

    public void setD4Record(D4Record d4Record) {
        this.d4Record = d4Record;
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
            this.centerText = "--";
        } else {
            int planAmountTotal = this.d4Record.getState().getFeedState().getPlanAmountTotal() + this.d4Record.getState().getFeedState().getAddAmountTotal();
            float realAmountTotal = this.d4Record.getState().getFeedState().getRealAmountTotal() * 1.0f;
            if (planAmountTotal == 0) {
                planAmountTotal = 1;
            }
            float f = (realAmountTotal / planAmountTotal) * 100.0f;
            this.outProgress = f;
            if (f > 100.0f) {
                this.outProgress = 100.0f;
            }
            this.centerText = D4Utils.getAmountFormat(this.d4Record.getState().getFeedState().getRealAmountTotal(), this.d4Record.getSettings().getFactor());
        }
        if (this.d4Record.getState().getPim() == 0) {
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
        int i2 = R.color.d4_main_yellow;
        this.outProgressColor = i2;
        int[] iArr3 = this.colors;
        iArr3[0] = -25550;
        iArr3[1] = -12173;
        int[] iArr4 = this.bgColors;
        iArr4[0] = -398639;
        iArr4[1] = -398639;
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

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        FeederCenterViewClick feederCenterViewClick;
        int action = motionEvent.getAction();
        if (action == 0) {
            this.downX = (int) motionEvent.getX();
            this.downY = (int) motionEvent.getY();
        } else if (action == 1) {
            RectF rectF = new RectF(0.0f, 0.0f, this.mWidth, this.mHeight);
            if (Math.abs(motionEvent.getX() - this.downX) <= 20.0f && Math.abs(motionEvent.getY() - this.downY) <= 20.0f && !this.startFeedAni) {
                int iSqrt = (int) Math.sqrt(Math.pow(Math.abs(motionEvent.getX() - rectF.centerX()), 2.0d) + Math.pow(Math.abs(motionEvent.getY() - rectF.centerY()), 2.0d));
                int i = this.mHeight;
                int i2 = this.outProgressBgWidth;
                if (iSqrt <= (i / 2) - (i2 / 2) && iSqrt >= ((i / 2) - (i2 * 2)) - (i2 / 2)) {
                    if (motionEvent.getY() < (this.mHeight / 2.0f) + ((int) (((double) ((i / 2) - i2)) * Math.cos(Math.toRadians(60.0d)))) && (feederCenterViewClick = this.viewClick) != null) {
                        feederCenterViewClick.onOutProgressViewClick();
                    }
                }
            }
        }
        return true;
    }

    public void setViewClick(FeederCenterViewClick feederCenterViewClick) {
        this.viewClick = feederCenterViewClick;
    }
}

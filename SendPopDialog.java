package com.petkit.android.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import androidx.browser.browseractions.BrowserServiceFileProvider;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.chat.downloadManager.downloader.DLCons;
import com.petkit.android.activities.community.TopicDetailListActivity;
import com.petkit.android.activities.home.CommonFragmentActivity;
import com.petkit.android.activities.home.HomeActivity;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.FileUtils;
import com.petkit.oversea.R;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;
import java.io.File;
import java.util.Date;

/* JADX INFO: loaded from: classes6.dex */
public class SendPopDialog extends Dialog implements View.OnClickListener {
    private int ANIMATIONTIME;
    private Handler handler;
    private OnBtnClickListener listener;
    private Activity mActivity;
    private int[] photoLocation;
    private int[] picLocation;
    private int screenHeight;
    private int[] textLocation;
    private int[] videoLoaction;
    private View viewSendPic;
    private View viewSendText;
    private View viewSendVideo;
    private View viewTakePhoto;

    public interface OnBtnClickListener {
        void onBtnClick(int i);
    }

    public SendPopDialog(Activity activity, OnBtnClickListener onBtnClickListener) {
        super(activity, R.style.low_battery_dialog);
        this.ANIMATIONTIME = 250;
        this.videoLoaction = new int[2];
        this.picLocation = new int[2];
        this.photoLocation = new int[2];
        this.textLocation = new int[2];
        this.mActivity = activity;
        this.listener = onBtnClickListener;
    }

    @Override // android.app.Dialog
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.layout_send_pop);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.menu_container);
        DisplayMetrics displayMetrics = BaseApplication.displayMetrics;
        relativeLayout.setLayoutParams(new FrameLayout.LayoutParams(displayMetrics.widthPixels, displayMetrics.heightPixels));
        relativeLayout.setOnClickListener(this);
        findViewById(R.id.btn_send_close).setOnClickListener(this);
        View viewFindViewById = findViewById(R.id.layout_send_video);
        this.viewSendVideo = viewFindViewById;
        viewFindViewById.setVisibility(4);
        findViewById(R.id.btn_send_video).setOnClickListener(this);
        View viewFindViewById2 = findViewById(R.id.layout_send_picture);
        this.viewSendPic = viewFindViewById2;
        viewFindViewById2.setVisibility(4);
        findViewById(R.id.btn_send_picture).setOnClickListener(this);
        View viewFindViewById3 = findViewById(R.id.layout_send_photo);
        this.viewTakePhoto = viewFindViewById3;
        viewFindViewById3.setVisibility(4);
        findViewById(R.id.btn_send_photo).setOnClickListener(this);
        View viewFindViewById4 = findViewById(R.id.layout_send_text);
        this.viewSendText = viewFindViewById4;
        viewFindViewById4.setVisibility(4);
        findViewById(R.id.btn_send_text).setOnClickListener(this);
        this.screenHeight = BaseApplication.displayMetrics.heightPixels;
        this.handler = new Handler();
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        if (z) {
            this.viewSendVideo.getLocationInWindow(this.videoLoaction);
            this.viewSendPic.getLocationInWindow(this.picLocation);
            this.viewTakePhoto.getLocationInWindow(this.photoLocation);
            this.viewSendText.getLocationInWindow(this.textLocation);
            enterAnimation();
        }
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.handler = null;
        this.videoLoaction = null;
        this.picLocation = null;
        this.photoLocation = null;
        this.textLocation = null;
    }

    private void enterAnimation() {
        final ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this.viewSendVideo, "y", this.screenHeight, this.videoLoaction[1]);
        objectAnimatorOfFloat.setDuration(this.ANIMATIONTIME);
        objectAnimatorOfFloat.setInterpolator(new MyInterpolator());
        objectAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.petkit.android.widget.SendPopDialog.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                super.onAnimationStart(animator);
                SendPopDialog.this.viewSendVideo.setVisibility(0);
            }
        });
        View view = this.viewSendPic;
        int i = this.screenHeight;
        final ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(view, "y", (i + r7) - this.videoLoaction[1], this.picLocation[1]);
        objectAnimatorOfFloat2.setDuration(this.ANIMATIONTIME);
        objectAnimatorOfFloat2.setInterpolator(new MyInterpolator());
        objectAnimatorOfFloat2.addListener(new AnimatorListenerAdapter() { // from class: com.petkit.android.widget.SendPopDialog.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                super.onAnimationStart(animator);
                SendPopDialog.this.viewSendPic.setVisibility(0);
            }
        });
        View view2 = this.viewTakePhoto;
        int i2 = this.screenHeight;
        final ObjectAnimator objectAnimatorOfFloat3 = ObjectAnimator.ofFloat(view2, "y", (i2 + r8) - this.videoLoaction[1], this.photoLocation[1]);
        objectAnimatorOfFloat3.setDuration(this.ANIMATIONTIME);
        objectAnimatorOfFloat3.setInterpolator(new MyInterpolator());
        objectAnimatorOfFloat3.addListener(new AnimatorListenerAdapter() { // from class: com.petkit.android.widget.SendPopDialog.3
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                super.onAnimationStart(animator);
                SendPopDialog.this.viewTakePhoto.setVisibility(0);
            }
        });
        View view3 = this.viewSendText;
        int i3 = this.screenHeight;
        final ObjectAnimator objectAnimatorOfFloat4 = ObjectAnimator.ofFloat(view3, "y", (i3 + r9) - this.videoLoaction[1], this.textLocation[1]);
        objectAnimatorOfFloat4.setDuration(this.ANIMATIONTIME);
        objectAnimatorOfFloat4.setInterpolator(new MyInterpolator());
        objectAnimatorOfFloat4.addListener(new AnimatorListenerAdapter() { // from class: com.petkit.android.widget.SendPopDialog.4
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                super.onAnimationStart(animator);
                SendPopDialog.this.viewSendText.setVisibility(0);
            }
        });
        this.handler.postDelayed(new Runnable() { // from class: com.petkit.android.widget.SendPopDialog.5
            @Override // java.lang.Runnable
            public void run() {
                objectAnimatorOfFloat.start();
            }
        }, 50L);
        this.handler.postDelayed(new Runnable() { // from class: com.petkit.android.widget.SendPopDialog.6
            @Override // java.lang.Runnable
            public void run() {
                objectAnimatorOfFloat2.start();
            }
        }, 150L);
        this.handler.postDelayed(new Runnable() { // from class: com.petkit.android.widget.SendPopDialog.7
            @Override // java.lang.Runnable
            public void run() {
                objectAnimatorOfFloat3.start();
            }
        }, 180L);
        this.handler.postDelayed(new Runnable() { // from class: com.petkit.android.widget.SendPopDialog.8
            @Override // java.lang.Runnable
            public void run() {
                objectAnimatorOfFloat4.start();
            }
        }, 300L);
    }

    private void skipAnimation(final int i) {
        final ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this.viewSendVideo, "y", this.videoLoaction[1], r0.getHeight() * (-3));
        objectAnimatorOfFloat.setDuration(this.ANIMATIONTIME + 100);
        objectAnimatorOfFloat.setInterpolator(new MyInterpolator());
        final ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(this.viewSendPic, "y", this.picLocation[1], r3.getHeight() * (-2));
        objectAnimatorOfFloat2.setDuration(this.ANIMATIONTIME + 100);
        objectAnimatorOfFloat2.setInterpolator(new MyInterpolator());
        final ObjectAnimator objectAnimatorOfFloat3 = ObjectAnimator.ofFloat(this.viewTakePhoto, "y", this.photoLocation[1], r5.getHeight() * (-2));
        objectAnimatorOfFloat3.setDuration(this.ANIMATIONTIME + 100);
        objectAnimatorOfFloat3.setInterpolator(new MyInterpolator());
        final ObjectAnimator objectAnimatorOfFloat4 = ObjectAnimator.ofFloat(this.viewSendText, "y", this.textLocation[1], -r7.getHeight());
        objectAnimatorOfFloat4.setDuration(this.ANIMATIONTIME + 100);
        objectAnimatorOfFloat4.setInterpolator(new MyInterpolator());
        objectAnimatorOfFloat4.addListener(new AnimatorListenerAdapter() { // from class: com.petkit.android.widget.SendPopDialog.9
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                SendPopDialog.this.dismiss();
                if (i != R.id.btn_send_photo) {
                    SendPopDialog.this.listener.onBtnClick(i);
                } else {
                    SendPopDialog.this.takePhoto();
                }
            }
        });
        this.handler.postDelayed(new Runnable() { // from class: com.petkit.android.widget.SendPopDialog.10
            @Override // java.lang.Runnable
            public void run() {
                objectAnimatorOfFloat.start();
            }
        }, 50L);
        this.handler.postDelayed(new Runnable() { // from class: com.petkit.android.widget.SendPopDialog.11
            @Override // java.lang.Runnable
            public void run() {
                objectAnimatorOfFloat2.start();
            }
        }, 130L);
        this.handler.postDelayed(new Runnable() { // from class: com.petkit.android.widget.SendPopDialog.12
            @Override // java.lang.Runnable
            public void run() {
                objectAnimatorOfFloat3.start();
            }
        }, 180L);
        this.handler.postDelayed(new Runnable() { // from class: com.petkit.android.widget.SendPopDialog.13
            @Override // java.lang.Runnable
            public void run() {
                objectAnimatorOfFloat4.start();
            }
        }, 300L);
    }

    private void exitAnimation() {
        final ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this.viewSendText, "y", this.textLocation[1], (this.screenHeight + r1) - this.videoLoaction[1]);
        objectAnimatorOfFloat.setDuration(this.ANIMATIONTIME);
        objectAnimatorOfFloat.setInterpolator(new MyInterpolator());
        final ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(this.viewTakePhoto, "y", this.photoLocation[1], (this.screenHeight + r5) - this.videoLoaction[1]);
        objectAnimatorOfFloat2.setDuration(this.ANIMATIONTIME);
        objectAnimatorOfFloat2.setInterpolator(new MyInterpolator());
        final ObjectAnimator objectAnimatorOfFloat3 = ObjectAnimator.ofFloat(this.viewSendPic, "y", this.picLocation[1], (this.screenHeight + r7) - this.videoLoaction[1]);
        objectAnimatorOfFloat3.setDuration(this.ANIMATIONTIME);
        objectAnimatorOfFloat3.setInterpolator(new MyInterpolator());
        final ObjectAnimator objectAnimatorOfFloat4 = ObjectAnimator.ofFloat(this.viewSendVideo, "y", this.videoLoaction[1], this.screenHeight);
        objectAnimatorOfFloat4.setDuration(this.ANIMATIONTIME);
        objectAnimatorOfFloat4.setInterpolator(new MyInterpolator());
        objectAnimatorOfFloat4.addListener(new AnimatorListenerAdapter() { // from class: com.petkit.android.widget.SendPopDialog.14
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                SendPopDialog.this.dismiss();
            }
        });
        this.handler.postDelayed(new Runnable() { // from class: com.petkit.android.widget.SendPopDialog.15
            @Override // java.lang.Runnable
            public void run() {
                objectAnimatorOfFloat.start();
            }
        }, 50L);
        this.handler.postDelayed(new Runnable() { // from class: com.petkit.android.widget.SendPopDialog.16
            @Override // java.lang.Runnable
            public void run() {
                objectAnimatorOfFloat2.start();
            }
        }, 180L);
        this.handler.postDelayed(new Runnable() { // from class: com.petkit.android.widget.SendPopDialog.17
            @Override // java.lang.Runnable
            public void run() {
                objectAnimatorOfFloat3.start();
            }
        }, 130L);
        this.handler.postDelayed(new Runnable() { // from class: com.petkit.android.widget.SendPopDialog.18
            @Override // java.lang.Runnable
            public void run() {
                objectAnimatorOfFloat4.start();
            }
        }, 300L);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.menu_container || id == R.id.btn_send_close) {
            exitAnimation();
            return;
        }
        int i = R.id.btn_send_video;
        if (id == i) {
            skipAnimation(i);
            return;
        }
        int i2 = R.id.btn_send_picture;
        if (id == i2) {
            skipAnimation(i2);
            return;
        }
        int i3 = R.id.btn_send_photo;
        if (id == i3) {
            skipAnimation(i3);
            return;
        }
        int i4 = R.id.btn_send_text;
        if (id == i4) {
            skipAnimation(i4);
        }
    }

    public void takePhoto() {
        Uri uriInsert;
        String str = new Date().getTime() + BrowserServiceFileProvider.FILE_EXTENSION;
        File file = new File(FileUtils.getAppImagesDirPath());
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        File file2 = new File(file, str);
        if (CommonUtils.getAppVersionCode(this.mActivity) < 24) {
            uriInsert = Uri.fromFile(file2);
            intent.putExtra("output", uriInsert);
        } else if (Build.VERSION.SDK_INT < 29) {
            ContentValues contentValues = new ContentValues(1);
            contentValues.put("_data", file2.getAbsolutePath());
            uriInsert = this.mActivity.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            intent.putExtra("output", uriInsert);
        } else {
            ContentValues contentValues2 = new ContentValues();
            contentValues2.put("relative_path", "DCIM/");
            contentValues2.put("_display_name", new Date().getTime() + "camera.dest.jpg");
            contentValues2.put(DLCons.DBCons.TB_TASK_MIME_TYPE, TweetComposer.MIME_TYPE_JPEG);
            uriInsert = this.mActivity.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues2);
            intent.putExtra("output", uriInsert);
        }
        Activity activity = this.mActivity;
        if (activity instanceof HomeActivity) {
            ((HomeActivity) activity).setLocalTempImageFileName(file2.getAbsolutePath(), uriInsert);
        } else if (activity instanceof CommonFragmentActivity) {
            ((CommonFragmentActivity) activity).setLocalTempImageFileName(file2.getAbsolutePath(), uriInsert);
        } else if (activity instanceof TopicDetailListActivity) {
            ((TopicDetailListActivity) activity).setLocalTempImageFileName(file2.getAbsolutePath(), uriInsert);
        } else if (activity instanceof CommonFragmentActivity) {
            ((CommonFragmentActivity) activity).setLocalTempImageFileName(file2.getAbsolutePath(), uriInsert);
        }
        intent.putExtra("orientation", 0);
        intent.putExtra("output", uriInsert);
        this.mActivity.startActivityForResult(intent, 1);
    }

    public class MyInterpolator extends OvershootInterpolator {
        @Override // android.view.animation.OvershootInterpolator, android.animation.TimeInterpolator
        public float getInterpolation(float f) {
            float f2 = f - 1.0f;
            return (f2 * f2 * ((f2 * 1.8f) + 0.8f)) + 1.0f;
        }

        public MyInterpolator() {
        }
    }

    @Override // android.app.Dialog, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 4) {
            exitAnimation();
            return true;
        }
        return super.onKeyDown(i, keyEvent);
    }
}

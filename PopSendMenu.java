package com.petkit.android.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.browser.browseractions.BrowserServiceFileProvider;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DeviceUtils;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.chat.downloadManager.downloader.DLCons;
import com.petkit.android.activities.community.TopicDetailListActivity;
import com.petkit.android.activities.go.GoWalkingActivity;
import com.petkit.android.activities.home.CommonFragmentActivity;
import com.petkit.android.activities.personal.PersonalActivity;
import com.petkit.android.activities.walkdog.WalkingActivity;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.FileUtils;
import com.petkit.oversea.R;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;
import java.io.File;
import java.util.Date;

/* JADX INFO: loaded from: classes6.dex */
public class PopSendMenu extends PopupWindow implements View.OnClickListener {
    private LinearLayout layoutSendPicture;
    private LinearLayout layoutSendText;
    private LinearLayout layoutSendVideo;
    private LinearLayout layoutTakePhoto;
    private OnBtnClickListener listener;
    private LinearLayout llBottom;
    private LinearLayout llRoot;
    private Activity mActivity;
    private View popMenu;
    private View statusView;
    private TextView tvCancel;

    public interface OnBtnClickListener {
        void onBtnClick(int i);
    }

    @SuppressLint({"InflateParams"})
    public PopSendMenu(Activity activity) {
        this.mActivity = activity;
        View viewInflate = LayoutInflater.from(activity).inflate(R.layout.layout_post_send, (ViewGroup) null);
        this.popMenu = viewInflate;
        this.llRoot = (LinearLayout) viewInflate.findViewById(R.id.ll_root);
        this.llBottom = (LinearLayout) this.popMenu.findViewById(R.id.ll_bottom_panel);
        this.tvCancel = (TextView) this.popMenu.findViewById(R.id.tv_cancel);
        this.layoutSendText = (LinearLayout) this.popMenu.findViewById(R.id.layout_send_text);
        this.popMenu.findViewById(R.id.btn_send_text).setOnClickListener(this);
        this.layoutTakePhoto = (LinearLayout) this.popMenu.findViewById(R.id.layout_take_photo);
        this.popMenu.findViewById(R.id.btn_take_photo).setOnClickListener(this);
        this.layoutSendPicture = (LinearLayout) this.popMenu.findViewById(R.id.layout_send_picture);
        this.popMenu.findViewById(R.id.btn_send_picture).setOnClickListener(this);
        this.layoutSendVideo = (LinearLayout) this.popMenu.findViewById(R.id.layout_send_video);
        this.popMenu.findViewById(R.id.btn_send_video).setOnClickListener(this);
        this.popMenu.findViewById(R.id.btn_feedback).setOnClickListener(this);
        setWidth(-1);
        setHeight(-1);
        setBackgroundDrawable(new BitmapDrawable());
        setOutsideTouchable(true);
        setFocusable(true);
        this.statusView = this.popMenu.findViewById(R.id.pop_status_view);
        this.popMenu.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.widget.PopSendMenu.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PopSendMenu.this.dismiss();
            }
        });
        this.tvCancel.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.widget.PopSendMenu$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$new$0(view);
            }
        });
        setContentView(this.popMenu);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        dismiss();
    }

    public PopSendMenu(Activity activity, OnBtnClickListener onBtnClickListener) {
        this(activity);
        this.listener = onBtnClickListener;
    }

    private void initAnimation() {
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
        int iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(0, 0);
        this.popMenu.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this.popMenu, "y", -this.popMenu.getMeasuredHeight(), 0.0f);
        objectAnimatorOfFloat.setDuration(200L);
        objectAnimatorOfFloat.setInterpolator(new LinearInterpolator());
        int i = BaseApplication.displayMetrics.widthPixels;
        this.layoutSendText.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
        int iDpToPixel = (int) ((i - DeviceUtils.dpToPixel(this.mActivity, 20.0f)) / 4.0f);
        int iDpToPixel2 = (int) DeviceUtils.dpToPixel(this.mActivity, 10.0f);
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(this.layoutSendText, "x", r4 - i, (iDpToPixel * 3) + iDpToPixel2);
        objectAnimatorOfFloat2.setDuration(400L);
        objectAnimatorOfFloat2.addListener(new PopAnimatorListener() { // from class: com.petkit.android.widget.PopSendMenu.2
            @Override // com.petkit.android.widget.PopSendMenu.PopAnimatorListener, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                PopSendMenu.this.layoutSendText.setVisibility(0);
            }
        });
        final ObjectAnimator objectAnimatorOfFloat3 = ObjectAnimator.ofFloat(this.layoutTakePhoto, "x", r10 - i, (iDpToPixel * 2) + iDpToPixel2);
        objectAnimatorOfFloat3.setDuration(300L);
        objectAnimatorOfFloat3.addListener(new PopAnimatorListener() { // from class: com.petkit.android.widget.PopSendMenu.3
            @Override // com.petkit.android.widget.PopSendMenu.PopAnimatorListener, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                PopSendMenu.this.layoutTakePhoto.setVisibility(0);
            }
        });
        final ObjectAnimator objectAnimatorOfFloat4 = ObjectAnimator.ofFloat(this.layoutSendPicture, "x", r1 - i, iDpToPixel + iDpToPixel2);
        objectAnimatorOfFloat4.setDuration(200L);
        objectAnimatorOfFloat4.addListener(new PopAnimatorListener() { // from class: com.petkit.android.widget.PopSendMenu.4
            @Override // com.petkit.android.widget.PopSendMenu.PopAnimatorListener, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                PopSendMenu.this.layoutSendPicture.setVisibility(0);
            }
        });
        final ObjectAnimator objectAnimatorOfFloat5 = ObjectAnimator.ofFloat(this.layoutSendVideo, "x", iDpToPixel2 - i, iDpToPixel2);
        objectAnimatorOfFloat5.setDuration(200L);
        objectAnimatorOfFloat5.addListener(new PopAnimatorListener() { // from class: com.petkit.android.widget.PopSendMenu.5
            @Override // com.petkit.android.widget.PopSendMenu.PopAnimatorListener, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                PopSendMenu.this.layoutSendVideo.setVisibility(0);
            }
        });
        AnimatorSet animatorSet = new AnimatorSet();
        objectAnimatorOfFloat2.setInterpolator(new TimeInterpolator() { // from class: com.petkit.android.widget.PopSendMenu.6
            @Override // android.animation.TimeInterpolator
            public float getInterpolation(float f) {
                if (f > 0.4f && !objectAnimatorOfFloat3.isRunning()) {
                    objectAnimatorOfFloat3.start();
                }
                return ((float) (Math.cos(((double) (f + 1.0f)) * 3.141592653589793d) / 2.0d)) + 0.5f;
            }
        });
        objectAnimatorOfFloat3.setInterpolator(new TimeInterpolator() { // from class: com.petkit.android.widget.PopSendMenu.7
            @Override // android.animation.TimeInterpolator
            public float getInterpolation(float f) {
                if (f > 0.4f && !objectAnimatorOfFloat4.isRunning()) {
                    objectAnimatorOfFloat4.start();
                }
                return ((float) (Math.cos(((double) (f + 1.0f)) * 3.141592653589793d) / 2.0d)) + 0.5f;
            }
        });
        objectAnimatorOfFloat4.setInterpolator(new TimeInterpolator() { // from class: com.petkit.android.widget.PopSendMenu.8
            @Override // android.animation.TimeInterpolator
            public float getInterpolation(float f) {
                if (f > 0.4f && !objectAnimatorOfFloat5.isRunning()) {
                    objectAnimatorOfFloat5.start();
                }
                float f2 = 1.0f - f;
                return 1.0f - (f2 * f2);
            }
        });
        animatorSet.play(objectAnimatorOfFloat2);
        animatorSet.start();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        int i = R.id.btn_send_text;
        if (id == i) {
            this.listener.onBtnClick(i);
        } else if (id == R.id.btn_take_photo) {
            takePhoto();
        } else {
            int i2 = R.id.btn_send_picture;
            if (id == i2) {
                this.listener.onBtnClick(i2);
            } else {
                int i3 = R.id.btn_send_video;
                if (id == i3) {
                    this.listener.onBtnClick(i3);
                } else {
                    int i4 = R.id.btn_feedback;
                    if (id == i4) {
                        this.listener.onBtnClick(i4);
                    }
                }
            }
        }
        dismiss();
        this.mActivity.overridePendingTransition(R.anim.slide_in_from_top, 0);
    }

    public void takePhoto() {
        Uri uriInsert;
        File file = new File(new File(FileUtils.getAppCacheImageDirPath()), new Date().getTime() + BrowserServiceFileProvider.FILE_EXTENSION);
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra("orientation", 0);
        if (CommonUtils.getAppVersionCode(this.mActivity) < 24) {
            intent.putExtra("output", Uri.fromFile(file));
            uriInsert = null;
        } else if (Build.VERSION.SDK_INT < 29) {
            ContentValues contentValues = new ContentValues(1);
            contentValues.put("_data", file.getAbsolutePath());
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
        if (activity instanceof CommonFragmentActivity) {
            ((CommonFragmentActivity) activity).setLocalTempImageFileName(file.getAbsolutePath(), uriInsert);
        } else if (activity instanceof TopicDetailListActivity) {
            ((TopicDetailListActivity) activity).setLocalTempImageFileName(file.getAbsolutePath(), uriInsert);
        } else if (activity instanceof WalkingActivity) {
            ((WalkingActivity) activity).setLocalTempImageFileName(file.getAbsolutePath(), uriInsert);
        } else if (activity instanceof GoWalkingActivity) {
            ((GoWalkingActivity) activity).setLocalTempImageFileName(file.getAbsolutePath(), uriInsert);
        } else if (activity instanceof PersonalActivity) {
            ((PersonalActivity) activity).setLocalTempImageFileName(file.getAbsolutePath(), uriInsert);
        }
        this.mActivity.startActivityForResult(intent, 1);
    }

    public void showPopWindow(View view) {
        if (!isShowing()) {
            if (Build.VERSION.SDK_INT < 24) {
                showAsDropDown(view);
                return;
            }
            View view2 = this.statusView;
            if (view2 != null) {
                view2.setVisibility(0);
            }
            view.getLocationOnScreen(new int[2]);
            showAtLocation(view, 0, 0, 0);
            return;
        }
        dismiss();
    }

    public void showBottomPopWindow(View view) {
        this.llRoot.setBackgroundResource(R.drawable.solid_white_pop_large_top_radius);
        this.llBottom.setVisibility(0);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.llRoot.getLayoutParams();
        layoutParams.addRule(2, R.id.ll_bottom_panel);
        this.llRoot.setLayoutParams(layoutParams);
        LinearLayout linearLayout = this.llRoot;
        linearLayout.setPadding(linearLayout.getBaseline(), ArmsUtils.dip2px(this.mActivity, 40.0f), this.llRoot.getPaddingRight(), ArmsUtils.dip2px(this.mActivity, 30.0f));
        if (!isShowing()) {
            if (Build.VERSION.SDK_INT < 24) {
                showAsDropDown(view);
                return;
            }
            View view2 = this.statusView;
            if (view2 != null) {
                view2.setVisibility(8);
            }
            view.getLocationOnScreen(new int[2]);
            showAtLocation(view, 0, 0, 0);
            return;
        }
        dismiss();
    }

    public class PopAnimatorListener implements Animator.AnimatorListener {
        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationCancel(Animator animator) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationRepeat(Animator animator) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationStart(Animator animator) {
        }

        public PopAnimatorListener() {
        }
    }
}

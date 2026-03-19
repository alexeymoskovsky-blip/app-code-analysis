package com.qiyukf.uikit.session.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.qiyukf.module.log.base.AbsUnicornLog;
import com.qiyukf.nimlib.n.m;
import com.qiyukf.nimlib.n.y;
import com.qiyukf.nimlib.net.a.a.f;
import com.qiyukf.nimlib.net.a.a.g;
import com.qiyukf.nimlib.net.a.c.a;
import com.qiyukf.nimlib.sdk.AbortableFuture;
import com.qiyukf.nimlib.sdk.NIMClient;
import com.qiyukf.nimlib.sdk.Observer;
import com.qiyukf.nimlib.sdk.msg.MsgService;
import com.qiyukf.nimlib.sdk.msg.MsgServiceObserve;
import com.qiyukf.nimlib.sdk.msg.attachment.VideoAttachment;
import com.qiyukf.nimlib.sdk.msg.constant.AttachStatusEnum;
import com.qiyukf.nimlib.sdk.msg.model.AttachmentProgress;
import com.qiyukf.nimlib.sdk.msg.model.IMMessage;
import com.qiyukf.uikit.common.activity.BaseFragmentActivity;
import com.qiyukf.unicorn.R;
import com.qiyukf.unicorn.api.event.EventCallback;
import com.qiyukf.unicorn.api.event.UnicornEventBase;
import com.qiyukf.unicorn.api.event.entry.RequestPermissionEventEntry;
import com.qiyukf.unicorn.c;
import com.qiyukf.unicorn.f.l;
import com.qiyukf.unicorn.n.b.e;
import com.qiyukf.unicorn.n.e.b;
import com.qiyukf.unicorn.n.e.d;
import com.qiyukf.unicorn.n.j;
import com.qiyukf.unicorn.n.o;
import com.qiyukf.unicorn.n.t;
import java.io.File;
import java.util.Arrays;
import java.util.List;

/* JADX INFO: loaded from: classes6.dex */
public class WatchVideoActivity extends BaseFragmentActivity implements View.OnClickListener {
    public static final String INTENT_EXTRA_DATA = "EXTRA_DATA";
    public static final String INTENT_EXTRA_MENU = "EXTRA_MENU";
    public static final String INTENT_EXTRA_VIDEO_URL = "INTENT_EXTRA_VIDEO_URL";
    private static final int PLAY_STATE_PAUSE = 3;
    private static final int PLAY_STATE_PLAYING = 1;
    private static final int PLAY_STATE_STOP = 2;
    private static final String TAG = "WatchVideoActivity";
    CountDownTimer countDownTimer;
    private ImageView downloadBtn;
    private AbortableFuture downloadFuture;
    private View downloadLayout;
    private View downloadProgressBackground;
    private View downloadProgressForeground;
    private TextView downloadProgressText;
    private String downloadUrl;
    private boolean downloading;
    protected TextView fileInfoTextView;
    private float lastPercent;
    private MediaPlayer mediaPlayer;
    private IMMessage message;
    private SurfaceHolder surfaceHolder;
    private SurfaceView surfaceView;
    protected String videoFilePath;
    private View videoIcon;
    ImageView ysfIvVideoProgressBtn;
    ImageView ysfIvWatchVideoFinish;
    ProgressBar ysfPbVideoProgressBar;
    TextView ysfTvVideoProgressSecond;
    private LinearLayout ysfWatchVideoDownloadParent;
    long unitSecond = 0;
    private Handler handlerTimes = new Handler();
    private boolean isNoVoice = true;
    private boolean isSurfaceCreated = false;
    protected long videoLength = 0;
    private int playState = 2;
    private Runnable timeRunnable = new Runnable() { // from class: com.qiyukf.uikit.session.activity.WatchVideoActivity.2
        public AnonymousClass2() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (WatchVideoActivity.this.mediaPlayer == null || !WatchVideoActivity.this.mediaPlayer.isPlaying()) {
                return;
            }
            WatchVideoActivity.this.playState = 1;
            WatchVideoActivity watchVideoActivity = WatchVideoActivity.this;
            if (watchVideoActivity.videoLength > 0) {
                int currentPosition = watchVideoActivity.mediaPlayer.getCurrentPosition();
                if (currentPosition < 0) {
                    currentPosition = 0;
                }
                long jB = y.b(currentPosition);
                WatchVideoActivity.this.ysfTvVideoProgressSecond.setText(WatchVideoActivity.secodeToTime(jB));
                WatchVideoActivity.this.ysfPbVideoProgressBar.setProgress((int) jB);
                WatchVideoActivity.this.handlerTimes.postDelayed(this, 1000L);
                return;
            }
            watchVideoActivity.ysfTvVideoProgressSecond.setVisibility(4);
        }
    };
    private Observer<IMMessage> statusObserver = new Observer<IMMessage>() { // from class: com.qiyukf.uikit.session.activity.WatchVideoActivity.6
        public AnonymousClass6() {
        }

        @Override // com.qiyukf.nimlib.sdk.Observer
        public void onEvent(IMMessage iMMessage) {
            if (!iMMessage.isTheSame(WatchVideoActivity.this.message) || WatchVideoActivity.this.isDestroyedCompatible()) {
                return;
            }
            if (iMMessage.getAttachStatus() == AttachStatusEnum.transferred && WatchVideoActivity.this.isVideoHasDownloaded(iMMessage)) {
                WatchVideoActivity watchVideoActivity = WatchVideoActivity.this;
                watchVideoActivity.onDownloadSuccess(((VideoAttachment) watchVideoActivity.message.getAttachment()).getPath());
            } else if (iMMessage.getAttachStatus() == AttachStatusEnum.fail) {
                WatchVideoActivity.this.onDownloadFailed();
            }
        }
    };
    private Observer<AttachmentProgress> attachmentProgressObserver = new Observer<AttachmentProgress>() { // from class: com.qiyukf.uikit.session.activity.WatchVideoActivity.7
        public AnonymousClass7() {
        }

        @Override // com.qiyukf.nimlib.sdk.Observer
        public void onEvent(AttachmentProgress attachmentProgress) {
            long j;
            long total = attachmentProgress.getTotal();
            long transferred = attachmentProgress.getTransferred();
            float f = transferred / total;
            if (f > 1.0d) {
                f = 1.0f;
                j = total;
            } else {
                j = transferred;
            }
            if (f - WatchVideoActivity.this.lastPercent >= 0.1d) {
                WatchVideoActivity.this.lastPercent = f;
                WatchVideoActivity watchVideoActivity = WatchVideoActivity.this;
                watchVideoActivity.setDownloadProgress(watchVideoActivity.getString(R.string.ysf_download_video), j, total);
                return;
            }
            if (WatchVideoActivity.this.lastPercent == 0.0d) {
                WatchVideoActivity.this.lastPercent = f;
                WatchVideoActivity watchVideoActivity2 = WatchVideoActivity.this;
                watchVideoActivity2.setDownloadProgress(watchVideoActivity2.getString(R.string.ysf_download_video), j, total);
            }
            if (f != 1.0d || WatchVideoActivity.this.lastPercent == 1.0d) {
                return;
            }
            WatchVideoActivity.this.lastPercent = f;
            WatchVideoActivity watchVideoActivity3 = WatchVideoActivity.this;
            watchVideoActivity3.setDownloadProgress(watchVideoActivity3.getString(R.string.ysf_download_video), j, total);
        }
    };

    @Override // com.qiyukf.uikit.common.activity.BaseFragmentActivity
    public boolean hasTitleBar() {
        return false;
    }

    public static void start(Context context, IMMessage iMMessage) {
        Intent intent = new Intent();
        intent.putExtra(INTENT_EXTRA_DATA, iMMessage);
        intent.setClass(context, WatchVideoActivity.class);
        context.startActivity(intent);
    }

    public static void start(Context context, IMMessage iMMessage, boolean z) {
        Intent intent = new Intent();
        intent.putExtra(INTENT_EXTRA_DATA, iMMessage);
        intent.putExtra(INTENT_EXTRA_MENU, z);
        intent.setClass(context, WatchVideoActivity.class);
        context.startActivity(intent);
    }

    public static void start(Context context, String str) {
        Intent intent = new Intent();
        intent.putExtra(INTENT_EXTRA_VIDEO_URL, str);
        intent.setClass(context, WatchVideoActivity.class);
        context.startActivity(intent);
    }

    @Override // com.qiyukf.uikit.common.activity.BaseFragmentActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.ysf_watch_video_activity);
        getWindow().setFlags(1024, 1024);
        onParseIntent();
        findViews();
        showVideoInfo();
        registerObservers(true);
        download();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        this.mediaPlayer = new MediaPlayer();
        if (this.isSurfaceCreated) {
            play();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        stopMediaPlayer();
    }

    @Override // com.qiyukf.uikit.common.activity.BaseFragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        stopDownload();
        super.onBackPressed();
    }

    @Override // com.qiyukf.uikit.common.activity.BaseFragmentActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        registerObservers(false);
        CountDownTimer countDownTimer = this.countDownTimer;
        if (countDownTimer != null) {
            countDownTimer.cancel();
            this.countDownTimer = null;
        }
    }

    private void onParseIntent() {
        IMMessage iMMessage = (IMMessage) getIntent().getSerializableExtra(INTENT_EXTRA_DATA);
        this.message = iMMessage;
        if (iMMessage != null) {
            setTitle(getString(R.string.ysf_video_send_by, y.a(iMMessage.getTime())));
            this.isNoVoice = getIntent().getBooleanExtra(INTENT_EXTRA_MENU, true);
        } else {
            setTitle(R.string.ysf_look_video);
            this.isNoVoice = false;
            this.downloadUrl = getIntent().getStringExtra(INTENT_EXTRA_VIDEO_URL);
        }
    }

    private void findViews() {
        this.downloadLayout = findViewById(R.id.layoutDownload);
        this.ysfWatchVideoDownloadParent = (LinearLayout) findViewById(R.id.ysf_watch_video_download_parent);
        this.downloadProgressBackground = findViewById(R.id.downloadProgressBackground);
        this.downloadProgressForeground = findViewById(R.id.downloadProgressForeground);
        this.downloadProgressText = (TextView) findViewById(R.id.downloadProgressText);
        this.videoIcon = findViewById(R.id.videoIcon);
        ImageView imageView = (ImageView) findViewById(R.id.ysf_iv_watch_video_finish);
        this.ysfIvWatchVideoFinish = imageView;
        imageView.setOnClickListener(this);
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.videoView);
        this.surfaceView = surfaceView;
        SurfaceHolder holder = surfaceView.getHolder();
        this.surfaceHolder = holder;
        holder.setType(3);
        this.surfaceHolder.addCallback(new SurfaceHolder.Callback() { // from class: com.qiyukf.uikit.session.activity.WatchVideoActivity.1
            @Override // android.view.SurfaceHolder.Callback
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
            }

            public AnonymousClass1() {
            }

            @Override // android.view.SurfaceHolder.Callback
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                if (WatchVideoActivity.this.isSurfaceCreated) {
                    return;
                }
                WatchVideoActivity.this.isSurfaceCreated = true;
                WatchVideoActivity.this.play();
            }

            @Override // android.view.SurfaceHolder.Callback
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                WatchVideoActivity.this.isSurfaceCreated = false;
            }
        });
        this.fileInfoTextView = (TextView) findViewById(R.id.lblVideoFileInfo);
        ImageView imageView2 = (ImageView) findViewById(R.id.control_download_btn);
        this.downloadBtn = imageView2;
        imageView2.setOnClickListener(this);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.ysf_pb_video_progress_bar);
        this.ysfPbVideoProgressBar = progressBar;
        IMMessage iMMessage = this.message;
        if (iMMessage != null) {
            progressBar.setMax((int) y.b(((VideoAttachment) iMMessage.getAttachment()).getDuration()));
        }
        this.ysfIvVideoProgressBtn = (ImageView) findViewById(R.id.ysf_iv_video_progress_btn);
        this.ysfTvVideoProgressSecond = (TextView) findViewById(R.id.ysf_tv_video_progress_second);
    }

    /* JADX INFO: renamed from: com.qiyukf.uikit.session.activity.WatchVideoActivity$1 */
    public class AnonymousClass1 implements SurfaceHolder.Callback {
        @Override // android.view.SurfaceHolder.Callback
        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        }

        public AnonymousClass1() {
        }

        @Override // android.view.SurfaceHolder.Callback
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            if (WatchVideoActivity.this.isSurfaceCreated) {
                return;
            }
            WatchVideoActivity.this.isSurfaceCreated = true;
            WatchVideoActivity.this.play();
        }

        @Override // android.view.SurfaceHolder.Callback
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            WatchVideoActivity.this.isSurfaceCreated = false;
        }
    }

    public void initVideoSize() {
        MediaPlayer mediaPlayer = this.mediaPlayer;
        if (mediaPlayer == null) {
            return;
        }
        int videoWidth = mediaPlayer.getVideoWidth();
        int videoHeight = this.mediaPlayer.getVideoHeight();
        if (videoWidth <= 0 || videoHeight <= 0) {
            return;
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int i = displayMetrics.widthPixels;
        int i2 = displayMetrics.heightPixels;
        if (i / i2 > videoWidth / videoHeight) {
            int i3 = (videoWidth * i2) / videoHeight;
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(i3, i2);
            int i4 = (i - i3) / 2;
            layoutParams.setMargins(i4, 0, i4, 0);
            this.surfaceView.setLayoutParams(layoutParams);
            return;
        }
        int i5 = (videoHeight * i) / videoWidth;
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(i, i5);
        int i6 = (i2 - i5) / 2;
        layoutParams2.setMargins(0, i6, 0, i6);
        this.surfaceView.setLayoutParams(layoutParams2);
    }

    private void stopMediaPlayer() {
        MediaPlayer mediaPlayer = this.mediaPlayer;
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                this.mediaPlayer.stop();
            }
            this.mediaPlayer.reset();
            this.mediaPlayer.release();
            this.mediaPlayer = null;
        }
    }

    /* JADX INFO: renamed from: com.qiyukf.uikit.session.activity.WatchVideoActivity$2 */
    public class AnonymousClass2 implements Runnable {
        public AnonymousClass2() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (WatchVideoActivity.this.mediaPlayer == null || !WatchVideoActivity.this.mediaPlayer.isPlaying()) {
                return;
            }
            WatchVideoActivity.this.playState = 1;
            WatchVideoActivity watchVideoActivity = WatchVideoActivity.this;
            if (watchVideoActivity.videoLength > 0) {
                int currentPosition = watchVideoActivity.mediaPlayer.getCurrentPosition();
                if (currentPosition < 0) {
                    currentPosition = 0;
                }
                long jB = y.b(currentPosition);
                WatchVideoActivity.this.ysfTvVideoProgressSecond.setText(WatchVideoActivity.secodeToTime(jB));
                WatchVideoActivity.this.ysfPbVideoProgressBar.setProgress((int) jB);
                WatchVideoActivity.this.handlerTimes.postDelayed(this, 1000L);
                return;
            }
            watchVideoActivity.ysfTvVideoProgressSecond.setVisibility(4);
        }
    }

    public void pauseVideo() {
        this.videoIcon.setVisibility(0);
        this.ysfIvVideoProgressBtn.setBackgroundResource(R.drawable.ysf_ic_video_start_btn_back);
        MediaPlayer mediaPlayer = this.mediaPlayer;
        if (mediaPlayer == null || !mediaPlayer.isPlaying()) {
            return;
        }
        this.mediaPlayer.pause();
        this.handlerTimes.removeCallbacks(this.timeRunnable);
        this.playState = 3;
    }

    public void resumeVideo() {
        this.videoIcon.setVisibility(8);
        this.ysfIvVideoProgressBtn.setBackgroundResource(R.drawable.ysf_ic_video_pause_btn_back);
        MediaPlayer mediaPlayer = this.mediaPlayer;
        if (mediaPlayer == null || mediaPlayer.isPlaying()) {
            return;
        }
        this.mediaPlayer.start();
        this.playState = 1;
        this.handlerTimes.postDelayed(this.timeRunnable, 100L);
    }

    public void playVideo(boolean z) {
        if (this.message == null) {
            MediaPlayer videoMediaPlayer = getVideoMediaPlayer(new File(this.videoFilePath));
            long duration = videoMediaPlayer == null ? 0 : videoMediaPlayer.getDuration();
            this.ysfPbVideoProgressBar.setMax((int) y.b(duration));
            long jB = y.b(duration);
            this.videoLength = jB;
            if (jB == 0) {
                this.unitSecond = 0L;
            } else {
                this.unitSecond = (100 / jB) * 100;
            }
        }
        this.videoIcon.setVisibility(8);
        setOperateVideoPanele(z ? 8 : 0);
        this.ysfIvVideoProgressBtn.setBackgroundResource(R.drawable.ysf_ic_video_pause_btn_back);
        MediaPlayer mediaPlayer = this.mediaPlayer;
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                this.mediaPlayer.stop();
            } else if (this.isSurfaceCreated) {
                this.mediaPlayer.setDisplay(this.surfaceHolder);
            } else {
                t.b(R.string.ysf_look_video_fail_try_again);
                return;
            }
            this.mediaPlayer.reset();
            try {
                this.mediaPlayer.setDataSource(this.videoFilePath);
                setMediaPlayerListener();
                this.mediaPlayer.prepareAsync();
            } catch (Exception e) {
                t.b(R.string.ysf_look_video_fail_try_again);
                e.printStackTrace();
            }
        }
    }

    /* JADX INFO: renamed from: com.qiyukf.uikit.session.activity.WatchVideoActivity$3 */
    public class AnonymousClass3 implements MediaPlayer.OnCompletionListener {
        public AnonymousClass3() {
        }

        @Override // android.media.MediaPlayer.OnCompletionListener
        public void onCompletion(MediaPlayer mediaPlayer) {
            WatchVideoActivity.this.videoIcon.setVisibility(0);
            WatchVideoActivity.this.ysfIvVideoProgressBtn.setBackgroundResource(R.drawable.ysf_ic_video_start_btn_back);
            WatchVideoActivity.this.playState = 2;
            WatchVideoActivity.this.ysfPbVideoProgressBar.setProgress(100);
            if (WatchVideoActivity.this.message == null) {
                WatchVideoActivity.this.ysfTvVideoProgressSecond.setText(WatchVideoActivity.secodeToTime((int) y.b(WatchVideoActivity.this.getVideoMediaPlayer(new File(WatchVideoActivity.this.videoFilePath)) == null ? 0 : r5.getDuration())));
            } else {
                WatchVideoActivity.this.ysfTvVideoProgressSecond.setText(WatchVideoActivity.secodeToTime((int) y.b(((VideoAttachment) WatchVideoActivity.this.message.getAttachment()).getDuration())));
            }
            WatchVideoActivity.this.setOperateVideoPanele(0);
            WatchVideoActivity.this.handlerTimes.removeCallbacks(WatchVideoActivity.this.timeRunnable);
        }
    }

    private void setMediaPlayerListener() {
        this.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() { // from class: com.qiyukf.uikit.session.activity.WatchVideoActivity.3
            public AnonymousClass3() {
            }

            @Override // android.media.MediaPlayer.OnCompletionListener
            public void onCompletion(MediaPlayer mediaPlayer) {
                WatchVideoActivity.this.videoIcon.setVisibility(0);
                WatchVideoActivity.this.ysfIvVideoProgressBtn.setBackgroundResource(R.drawable.ysf_ic_video_start_btn_back);
                WatchVideoActivity.this.playState = 2;
                WatchVideoActivity.this.ysfPbVideoProgressBar.setProgress(100);
                if (WatchVideoActivity.this.message == null) {
                    WatchVideoActivity.this.ysfTvVideoProgressSecond.setText(WatchVideoActivity.secodeToTime((int) y.b(WatchVideoActivity.this.getVideoMediaPlayer(new File(WatchVideoActivity.this.videoFilePath)) == null ? 0 : r5.getDuration())));
                } else {
                    WatchVideoActivity.this.ysfTvVideoProgressSecond.setText(WatchVideoActivity.secodeToTime((int) y.b(((VideoAttachment) WatchVideoActivity.this.message.getAttachment()).getDuration())));
                }
                WatchVideoActivity.this.setOperateVideoPanele(0);
                WatchVideoActivity.this.handlerTimes.removeCallbacks(WatchVideoActivity.this.timeRunnable);
            }
        });
        this.mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() { // from class: com.qiyukf.uikit.session.activity.WatchVideoActivity.4
            public AnonymousClass4() {
            }

            @Override // android.media.MediaPlayer.OnErrorListener
            public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
                try {
                    Intent intent = new Intent("android.intent.action.VIEW");
                    intent.setDataAndType(Uri.parse("file://" + WatchVideoActivity.this.videoFilePath), "video/3gp");
                    WatchVideoActivity.this.startActivity(intent);
                    WatchVideoActivity.this.finish();
                    return true;
                } catch (Exception unused) {
                    t.b(R.string.ysf_look_video_fail);
                    return true;
                }
            }
        });
        this.mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() { // from class: com.qiyukf.uikit.session.activity.WatchVideoActivity.5
            public AnonymousClass5() {
            }

            @Override // android.media.MediaPlayer.OnPreparedListener
            public void onPrepared(MediaPlayer mediaPlayer) {
                WatchVideoActivity.this.mediaPlayer.start();
                WatchVideoActivity.this.initVideoSize();
                WatchVideoActivity.this.handlerTimes.postDelayed(WatchVideoActivity.this.timeRunnable, 100L);
                if (WatchVideoActivity.this.isNoVoice) {
                    WatchVideoActivity.this.mediaPlayer.setVolume(0.0f, 0.0f);
                }
            }
        });
    }

    /* JADX INFO: renamed from: com.qiyukf.uikit.session.activity.WatchVideoActivity$4 */
    public class AnonymousClass4 implements MediaPlayer.OnErrorListener {
        public AnonymousClass4() {
        }

        @Override // android.media.MediaPlayer.OnErrorListener
        public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
            try {
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.setDataAndType(Uri.parse("file://" + WatchVideoActivity.this.videoFilePath), "video/3gp");
                WatchVideoActivity.this.startActivity(intent);
                WatchVideoActivity.this.finish();
                return true;
            } catch (Exception unused) {
                t.b(R.string.ysf_look_video_fail);
                return true;
            }
        }
    }

    /* JADX INFO: renamed from: com.qiyukf.uikit.session.activity.WatchVideoActivity$5 */
    public class AnonymousClass5 implements MediaPlayer.OnPreparedListener {
        public AnonymousClass5() {
        }

        @Override // android.media.MediaPlayer.OnPreparedListener
        public void onPrepared(MediaPlayer mediaPlayer) {
            WatchVideoActivity.this.mediaPlayer.start();
            WatchVideoActivity.this.initVideoSize();
            WatchVideoActivity.this.handlerTimes.postDelayed(WatchVideoActivity.this.timeRunnable, 100L);
            if (WatchVideoActivity.this.isNoVoice) {
                WatchVideoActivity.this.mediaPlayer.setVolume(0.0f, 0.0f);
            }
        }
    }

    private void showVideoInfo() {
        IMMessage iMMessage = this.message;
        if (iMMessage != null) {
            long duration = ((VideoAttachment) iMMessage.getAttachment()).getDuration();
            long size = ((VideoAttachment) this.message.getAttachment()).getSize();
            if (duration <= 0) {
                this.fileInfoTextView.setText(getString(R.string.ysf_video_size_str, e.a(size)));
                return;
            }
            long jB = y.b(duration);
            this.fileInfoTextView.setText(getString(R.string.ysf_send_video_info, e.a(size), String.valueOf(jB)));
            this.videoLength = jB;
            if (jB == 0) {
                this.unitSecond = 0L;
            } else {
                this.unitSecond = (100 / jB) * 100;
            }
        }
    }

    private void registerObservers(boolean z) {
        ((MsgServiceObserve) NIMClient.getService(MsgServiceObserve.class)).observeMsgStatus(this.statusObserver, z);
        ((MsgServiceObserve) NIMClient.getService(MsgServiceObserve.class)).observeAttachmentProgress(this.attachmentProgressObserver, z);
    }

    /* JADX INFO: renamed from: com.qiyukf.uikit.session.activity.WatchVideoActivity$6 */
    public class AnonymousClass6 implements Observer<IMMessage> {
        public AnonymousClass6() {
        }

        @Override // com.qiyukf.nimlib.sdk.Observer
        public void onEvent(IMMessage iMMessage) {
            if (!iMMessage.isTheSame(WatchVideoActivity.this.message) || WatchVideoActivity.this.isDestroyedCompatible()) {
                return;
            }
            if (iMMessage.getAttachStatus() == AttachStatusEnum.transferred && WatchVideoActivity.this.isVideoHasDownloaded(iMMessage)) {
                WatchVideoActivity watchVideoActivity = WatchVideoActivity.this;
                watchVideoActivity.onDownloadSuccess(((VideoAttachment) watchVideoActivity.message.getAttachment()).getPath());
            } else if (iMMessage.getAttachStatus() == AttachStatusEnum.fail) {
                WatchVideoActivity.this.onDownloadFailed();
            }
        }
    }

    /* JADX INFO: renamed from: com.qiyukf.uikit.session.activity.WatchVideoActivity$7 */
    public class AnonymousClass7 implements Observer<AttachmentProgress> {
        public AnonymousClass7() {
        }

        @Override // com.qiyukf.nimlib.sdk.Observer
        public void onEvent(AttachmentProgress attachmentProgress) {
            long j;
            long total = attachmentProgress.getTotal();
            long transferred = attachmentProgress.getTransferred();
            float f = transferred / total;
            if (f > 1.0d) {
                f = 1.0f;
                j = total;
            } else {
                j = transferred;
            }
            if (f - WatchVideoActivity.this.lastPercent >= 0.1d) {
                WatchVideoActivity.this.lastPercent = f;
                WatchVideoActivity watchVideoActivity = WatchVideoActivity.this;
                watchVideoActivity.setDownloadProgress(watchVideoActivity.getString(R.string.ysf_download_video), j, total);
                return;
            }
            if (WatchVideoActivity.this.lastPercent == 0.0d) {
                WatchVideoActivity.this.lastPercent = f;
                WatchVideoActivity watchVideoActivity2 = WatchVideoActivity.this;
                watchVideoActivity2.setDownloadProgress(watchVideoActivity2.getString(R.string.ysf_download_video), j, total);
            }
            if (f != 1.0d || WatchVideoActivity.this.lastPercent == 1.0d) {
                return;
            }
            WatchVideoActivity.this.lastPercent = f;
            WatchVideoActivity watchVideoActivity3 = WatchVideoActivity.this;
            watchVideoActivity3.setDownloadProgress(watchVideoActivity3.getString(R.string.ysf_download_video), j, total);
        }
    }

    /* JADX INFO: renamed from: com.qiyukf.uikit.session.activity.WatchVideoActivity$8 */
    public class AnonymousClass8 implements Runnable {
        final /* synthetic */ String val$label;
        final /* synthetic */ float val$percent;
        final /* synthetic */ long val$progress;
        final /* synthetic */ long val$total;

        public AnonymousClass8(float f, String str, long j, long j2) {
            f = f;
            str = str;
            j = j;
            j = j2;
        }

        @Override // java.lang.Runnable
        public void run() {
            ViewGroup.LayoutParams layoutParams = WatchVideoActivity.this.downloadProgressForeground.getLayoutParams();
            layoutParams.width = (int) (WatchVideoActivity.this.downloadProgressBackground.getWidth() * f);
            WatchVideoActivity.this.downloadProgressForeground.setLayoutParams(layoutParams);
            WatchVideoActivity.this.downloadProgressText.setText(String.format(WatchVideoActivity.this.getString(R.string.ysf_download_progress_description), str, e.a(j), e.a(j)));
        }
    }

    public void setDownloadProgress(String str, long j, long j2) {
        runOnUiThread(new Runnable() { // from class: com.qiyukf.uikit.session.activity.WatchVideoActivity.8
            final /* synthetic */ String val$label;
            final /* synthetic */ float val$percent;
            final /* synthetic */ long val$progress;
            final /* synthetic */ long val$total;

            public AnonymousClass8(float f, String str2, long j3, long j22) {
                f = f;
                str = str2;
                j = j3;
                j = j22;
            }

            @Override // java.lang.Runnable
            public void run() {
                ViewGroup.LayoutParams layoutParams = WatchVideoActivity.this.downloadProgressForeground.getLayoutParams();
                layoutParams.width = (int) (WatchVideoActivity.this.downloadProgressBackground.getWidth() * f);
                WatchVideoActivity.this.downloadProgressForeground.setLayoutParams(layoutParams);
                WatchVideoActivity.this.downloadProgressText.setText(String.format(WatchVideoActivity.this.getString(R.string.ysf_download_progress_description), str, e.a(j), e.a(j)));
            }
        });
    }

    public boolean isVideoHasDownloaded(IMMessage iMMessage) {
        return (iMMessage == null || iMMessage.getAttachStatus() != AttachStatusEnum.transferred || TextUtils.isEmpty(((VideoAttachment) iMMessage.getAttachment()).getPath())) ? false : true;
    }

    private boolean isVideoHasDownloaded(String str) {
        if (TextUtils.isEmpty(getDownFilePath(str))) {
            return false;
        }
        return new File(getDownFilePath(str)).exists();
    }

    private void download() {
        IMMessage iMMessage = this.message;
        if (iMMessage != null) {
            if (isVideoHasDownloaded(iMMessage)) {
                return;
            }
            onDownloadStart(this.message);
            this.downloadFuture = ((MsgService) NIMClient.getService(MsgService.class)).downloadAttachment(this.message, false);
            this.downloading = true;
            return;
        }
        if (isVideoHasDownloaded(this.downloadUrl)) {
            return;
        }
        this.downloadLayout.setVisibility(8);
        this.ysfWatchVideoDownloadParent.setVisibility(0);
        this.downloading = true;
        String str = this.downloadUrl;
        downloadUrlVideo(str, getDownFilePath(str));
    }

    public void play() {
        IMMessage iMMessage = this.message;
        if (iMMessage != null) {
            if (isVideoHasDownloaded(iMMessage)) {
                onDownloadSuccess(((VideoAttachment) this.message.getAttachment()).getPath());
            }
        } else if (isVideoHasDownloaded(this.downloadUrl)) {
            onDownloadSuccess(getDownFilePath(this.downloadUrl));
        }
    }

    public void onDownloadSuccess(String str) {
        this.downloadFuture = null;
        this.ysfWatchVideoDownloadParent.setVisibility(8);
        this.downloadLayout.setVisibility(8);
        this.videoFilePath = str;
        this.ysfIvVideoProgressBtn.setOnClickListener(this);
        this.surfaceView.setOnClickListener(this);
        this.surfaceView.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.qiyukf.uikit.session.activity.WatchVideoActivity.9
            public AnonymousClass9() {
            }

            @Override // android.view.View.OnLongClickListener
            public boolean onLongClick(View view) {
                WatchVideoActivity.this.popupSaveVideoWindow();
                return true;
            }
        });
        playVideo(true);
    }

    /* JADX INFO: renamed from: com.qiyukf.uikit.session.activity.WatchVideoActivity$9 */
    public class AnonymousClass9 implements View.OnLongClickListener {
        public AnonymousClass9() {
        }

        @Override // android.view.View.OnLongClickListener
        public boolean onLongClick(View view) {
            WatchVideoActivity.this.popupSaveVideoWindow();
            return true;
        }
    }

    public void onDownloadFailed() {
        this.downloadFuture = null;
        this.ysfWatchVideoDownloadParent.setVisibility(8);
        this.downloadLayout.setVisibility(8);
        t.b(R.string.ysf_download_video_fail);
    }

    private void onDownloadStart(IMMessage iMMessage) {
        setDownloadProgress(getString(R.string.ysf_download_video), 0L, ((VideoAttachment) iMMessage.getAttachment()).getSize());
        this.downloadLayout.setVisibility(0);
    }

    private void stopDownload() {
        AbortableFuture abortableFuture = this.downloadFuture;
        if (abortableFuture != null) {
            abortableFuture.abort();
            this.downloadFuture = null;
            this.downloading = false;
        }
    }

    @Override // com.qiyukf.uikit.common.activity.BaseFragmentActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        initVideoSize();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.ysf_iv_watch_video_finish) {
            finish();
            return;
        }
        if (id == R.id.control_download_btn) {
            if (this.downloading) {
                stopDownload();
            } else {
                download();
            }
            this.downloadBtn.setImageResource(this.downloading ? R.drawable.ysf_icon_download_pause : R.drawable.ysf_icon_download_resume);
            return;
        }
        if (id == R.id.ysf_iv_video_progress_btn) {
            int i = this.playState;
            if (i == 3) {
                resumeVideo();
                return;
            } else if (i == 1) {
                pauseVideo();
                return;
            } else {
                if (i == 2) {
                    playVideo(false);
                    return;
                }
                return;
            }
        }
        if (id == R.id.videoView) {
            int i2 = this.playState;
            if (i2 == 3) {
                resumeVideo();
                return;
            }
            if (i2 != 1) {
                if (i2 == 2) {
                    playVideo(true);
                }
            } else if (this.ysfIvWatchVideoFinish.getVisibility() == 8) {
                setOperateVideoPanele(0);
            } else {
                setOperateVideoPanele(8);
            }
        }
    }

    public void popupSaveVideoWindow() {
        View viewInflate = LayoutInflater.from(this).inflate(R.layout.ysf_popup_save_video, (ViewGroup) null);
        final PopupWindow popupWindow = new PopupWindow(viewInflate, -1, -1, true);
        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() { // from class: com.qiyukf.uikit.session.activity.WatchVideoActivity.10
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return false;
            }

            public AnonymousClass10() {
            }
        });
        TextView textView = (TextView) viewInflate.findViewById(R.id.ysf_tv_watch_video_save);
        TextView textView2 = (TextView) viewInflate.findViewById(R.id.ysf_tv_watch_video_save_cancel);
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.qiyukf.uikit.session.activity.WatchVideoActivity.11
            final /* synthetic */ PopupWindow val$popupWindow;

            public AnonymousClass11(final PopupWindow popupWindow2) {
                popupWindow = popupWindow2;
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (c.f().sdkEvents != null && c.f().sdkEvents.eventProcessFactory != null) {
                    UnicornEventBase unicornEventBaseEventOf = c.f().sdkEvents.eventProcessFactory.eventOf(5);
                    if (unicornEventBaseEventOf != null) {
                        List<String> listAsList = Arrays.asList(l.b);
                        RequestPermissionEventEntry requestPermissionEventEntry = new RequestPermissionEventEntry();
                        requestPermissionEventEntry.setScenesType(3);
                        requestPermissionEventEntry.setPermissionList(listAsList);
                        unicornEventBaseEventOf.onEvent(requestPermissionEventEntry, WatchVideoActivity.this, new EventCallback() { // from class: com.qiyukf.uikit.session.activity.WatchVideoActivity.11.1
                            public AnonymousClass1() {
                            }

                            @Override // com.qiyukf.unicorn.api.event.EventCallback
                            public void onProcessEventSuccess(Object obj) {
                                popupWindow.dismiss();
                                WatchVideoActivity.this.checkPermissionAndSave();
                            }

                            @Override // com.qiyukf.unicorn.api.event.EventCallback
                            public void onPorcessEventError() {
                                popupWindow.dismiss();
                                WatchVideoActivity.this.checkPermissionAndSave();
                            }

                            @Override // com.qiyukf.unicorn.api.event.EventCallback
                            public void onNotPorcessEvent() {
                                popupWindow.dismiss();
                                WatchVideoActivity.this.checkPermissionAndSave();
                            }

                            @Override // com.qiyukf.unicorn.api.event.EventCallback
                            public void onInterceptEvent() {
                                t.a(R.string.ysf_no_permission_save_video);
                            }
                        });
                        return;
                    }
                    popupWindow.dismiss();
                    WatchVideoActivity.this.checkPermissionAndSave();
                    return;
                }
                popupWindow.dismiss();
                WatchVideoActivity.this.checkPermissionAndSave();
            }

            /* JADX INFO: renamed from: com.qiyukf.uikit.session.activity.WatchVideoActivity$11$1 */
            public class AnonymousClass1 implements EventCallback {
                public AnonymousClass1() {
                }

                @Override // com.qiyukf.unicorn.api.event.EventCallback
                public void onProcessEventSuccess(Object obj) {
                    popupWindow.dismiss();
                    WatchVideoActivity.this.checkPermissionAndSave();
                }

                @Override // com.qiyukf.unicorn.api.event.EventCallback
                public void onPorcessEventError() {
                    popupWindow.dismiss();
                    WatchVideoActivity.this.checkPermissionAndSave();
                }

                @Override // com.qiyukf.unicorn.api.event.EventCallback
                public void onNotPorcessEvent() {
                    popupWindow.dismiss();
                    WatchVideoActivity.this.checkPermissionAndSave();
                }

                @Override // com.qiyukf.unicorn.api.event.EventCallback
                public void onInterceptEvent() {
                    t.a(R.string.ysf_no_permission_save_video);
                }
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() { // from class: com.qiyukf.uikit.session.activity.WatchVideoActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                popupWindow2.dismiss();
            }
        });
        popupWindow2.showAsDropDown(this.surfaceView);
    }

    /* JADX INFO: renamed from: com.qiyukf.uikit.session.activity.WatchVideoActivity$10 */
    public class AnonymousClass10 implements View.OnTouchListener {
        @Override // android.view.View.OnTouchListener
        public boolean onTouch(View view, MotionEvent motionEvent) {
            return false;
        }

        public AnonymousClass10() {
        }
    }

    /* JADX INFO: renamed from: com.qiyukf.uikit.session.activity.WatchVideoActivity$11 */
    public class AnonymousClass11 implements View.OnClickListener {
        final /* synthetic */ PopupWindow val$popupWindow;

        public AnonymousClass11(final PopupWindow popupWindow2) {
            popupWindow = popupWindow2;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (c.f().sdkEvents != null && c.f().sdkEvents.eventProcessFactory != null) {
                UnicornEventBase unicornEventBaseEventOf = c.f().sdkEvents.eventProcessFactory.eventOf(5);
                if (unicornEventBaseEventOf != null) {
                    List<String> listAsList = Arrays.asList(l.b);
                    RequestPermissionEventEntry requestPermissionEventEntry = new RequestPermissionEventEntry();
                    requestPermissionEventEntry.setScenesType(3);
                    requestPermissionEventEntry.setPermissionList(listAsList);
                    unicornEventBaseEventOf.onEvent(requestPermissionEventEntry, WatchVideoActivity.this, new EventCallback() { // from class: com.qiyukf.uikit.session.activity.WatchVideoActivity.11.1
                        public AnonymousClass1() {
                        }

                        @Override // com.qiyukf.unicorn.api.event.EventCallback
                        public void onProcessEventSuccess(Object obj) {
                            popupWindow.dismiss();
                            WatchVideoActivity.this.checkPermissionAndSave();
                        }

                        @Override // com.qiyukf.unicorn.api.event.EventCallback
                        public void onPorcessEventError() {
                            popupWindow.dismiss();
                            WatchVideoActivity.this.checkPermissionAndSave();
                        }

                        @Override // com.qiyukf.unicorn.api.event.EventCallback
                        public void onNotPorcessEvent() {
                            popupWindow.dismiss();
                            WatchVideoActivity.this.checkPermissionAndSave();
                        }

                        @Override // com.qiyukf.unicorn.api.event.EventCallback
                        public void onInterceptEvent() {
                            t.a(R.string.ysf_no_permission_save_video);
                        }
                    });
                    return;
                }
                popupWindow.dismiss();
                WatchVideoActivity.this.checkPermissionAndSave();
                return;
            }
            popupWindow.dismiss();
            WatchVideoActivity.this.checkPermissionAndSave();
        }

        /* JADX INFO: renamed from: com.qiyukf.uikit.session.activity.WatchVideoActivity$11$1 */
        public class AnonymousClass1 implements EventCallback {
            public AnonymousClass1() {
            }

            @Override // com.qiyukf.unicorn.api.event.EventCallback
            public void onProcessEventSuccess(Object obj) {
                popupWindow.dismiss();
                WatchVideoActivity.this.checkPermissionAndSave();
            }

            @Override // com.qiyukf.unicorn.api.event.EventCallback
            public void onPorcessEventError() {
                popupWindow.dismiss();
                WatchVideoActivity.this.checkPermissionAndSave();
            }

            @Override // com.qiyukf.unicorn.api.event.EventCallback
            public void onNotPorcessEvent() {
                popupWindow.dismiss();
                WatchVideoActivity.this.checkPermissionAndSave();
            }

            @Override // com.qiyukf.unicorn.api.event.EventCallback
            public void onInterceptEvent() {
                t.a(R.string.ysf_no_permission_save_video);
            }
        }
    }

    public void checkPermissionAndSave() {
        j.a((Activity) this).a(l.b).a(new j.a() { // from class: com.qiyukf.uikit.session.activity.WatchVideoActivity.12
            public AnonymousClass12() {
            }

            @Override // com.qiyukf.unicorn.n.j.a
            public void onGranted() {
                WatchVideoActivity.this.saveVideo();
            }

            @Override // com.qiyukf.unicorn.n.j.a
            public void onDenied() {
                t.a(R.string.ysf_no_permission_save_video);
            }
        }).a();
    }

    /* JADX INFO: renamed from: com.qiyukf.uikit.session.activity.WatchVideoActivity$12 */
    public class AnonymousClass12 implements j.a {
        public AnonymousClass12() {
        }

        @Override // com.qiyukf.unicorn.n.j.a
        public void onGranted() {
            WatchVideoActivity.this.saveVideo();
        }

        @Override // com.qiyukf.unicorn.n.j.a
        public void onDenied() {
            t.a(R.string.ysf_no_permission_save_video);
        }
    }

    public void saveVideo() {
        if (TextUtils.isEmpty(this.videoFilePath)) {
            t.a(R.string.ysf_first_download_video);
            return;
        }
        String strB = d.b(this);
        if (o.a()) {
            if (b.b(this, new File(this.videoFilePath))) {
                t.b(getString(R.string.ysf_video_save_success));
                return;
            } else {
                t.a(R.string.ysf_video_save_fail);
                return;
            }
        }
        String str = strB + ("video_" + System.currentTimeMillis() + ".mp4");
        if (a.a(this.videoFilePath, str) != -1) {
            try {
                Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
                intent.setData(Uri.fromFile(new File(str)));
                sendBroadcast(intent);
                t.b(getString(R.string.ysf_video_save_to, strB));
                return;
            } catch (Exception unused) {
                t.b(R.string.ysf_picture_save_fail);
                return;
            }
        }
        t.a(R.string.ysf_video_save_fail);
    }

    public void setOperateVideoPanele(int i) {
        CountDownTimer countDownTimer;
        this.ysfIvWatchVideoFinish.setVisibility(i);
        this.ysfIvVideoProgressBtn.setVisibility(i);
        this.ysfPbVideoProgressBar.setVisibility(i);
        this.ysfTvVideoProgressSecond.setVisibility(i);
        if (i == 8 && (countDownTimer = this.countDownTimer) != null) {
            countDownTimer.cancel();
            return;
        }
        CountDownTimer countDownTimer2 = this.countDownTimer;
        if (countDownTimer2 != null) {
            countDownTimer2.cancel();
        }
        initCountDownTimer(3000L);
        this.countDownTimer.start();
    }

    /* JADX INFO: renamed from: com.qiyukf.uikit.session.activity.WatchVideoActivity$13 */
    public class AnonymousClass13 extends CountDownTimer {
        @Override // android.os.CountDownTimer
        public void onTick(long j) {
        }

        public AnonymousClass13(long j, long j2) {
            super(j, j2);
        }

        @Override // android.os.CountDownTimer
        public void onFinish() {
            if (WatchVideoActivity.this.playState == 1) {
                WatchVideoActivity.this.setOperateVideoPanele(8);
            }
        }
    }

    public void initCountDownTimer(long j) {
        this.countDownTimer = new CountDownTimer(j, 1000L) { // from class: com.qiyukf.uikit.session.activity.WatchVideoActivity.13
            @Override // android.os.CountDownTimer
            public void onTick(long j2) {
            }

            public AnonymousClass13(long j2, long j22) {
                super(j2, j22);
            }

            @Override // android.os.CountDownTimer
            public void onFinish() {
                if (WatchVideoActivity.this.playState == 1) {
                    WatchVideoActivity.this.setOperateVideoPanele(8);
                }
            }
        };
    }

    public MediaPlayer getVideoMediaPlayer(File file) {
        try {
            return MediaPlayer.create(this, Uri.fromFile(file));
        } catch (Exception e) {
            AbsUnicornLog.i(TAG, "getVideoMediaPlayer is error", e);
            return null;
        }
    }

    private void downloadUrlVideo(String str, String str2) {
        if (TextUtils.isEmpty(str) || str2 == null) {
            t.a(R.string.ysf_download_video_fail);
        } else {
            g.a().a(new com.qiyukf.nimlib.net.a.a.e(str, str2, new f() { // from class: com.qiyukf.uikit.session.activity.WatchVideoActivity.14
                final /* synthetic */ String val$downloadUrl;

                @Override // com.qiyukf.nimlib.net.a.a.f
                public void onCancel(com.qiyukf.nimlib.net.a.a.e eVar) {
                }

                @Override // com.qiyukf.nimlib.net.a.a.f
                public void onExpire(com.qiyukf.nimlib.net.a.a.e eVar, String str3) {
                }

                @Override // com.qiyukf.nimlib.net.a.a.f
                public void onFail(com.qiyukf.nimlib.net.a.a.e eVar, String str3) {
                }

                @Override // com.qiyukf.nimlib.net.a.a.f
                public void onGetLength(com.qiyukf.nimlib.net.a.a.e eVar, long j) {
                }

                @Override // com.qiyukf.nimlib.net.a.a.f
                public void onProgress(com.qiyukf.nimlib.net.a.a.e eVar, long j) {
                }

                @Override // com.qiyukf.nimlib.net.a.a.f
                public void onStart(com.qiyukf.nimlib.net.a.a.e eVar) {
                }

                public AnonymousClass14(String str3) {
                    str = str3;
                }

                @Override // com.qiyukf.nimlib.net.a.a.f
                public void onOK(com.qiyukf.nimlib.net.a.a.e eVar) {
                    WatchVideoActivity watchVideoActivity = WatchVideoActivity.this;
                    watchVideoActivity.onDownloadSuccess(watchVideoActivity.getDownFilePath(str));
                }
            }));
        }
    }

    /* JADX INFO: renamed from: com.qiyukf.uikit.session.activity.WatchVideoActivity$14 */
    public class AnonymousClass14 implements f {
        final /* synthetic */ String val$downloadUrl;

        @Override // com.qiyukf.nimlib.net.a.a.f
        public void onCancel(com.qiyukf.nimlib.net.a.a.e eVar) {
        }

        @Override // com.qiyukf.nimlib.net.a.a.f
        public void onExpire(com.qiyukf.nimlib.net.a.a.e eVar, String str3) {
        }

        @Override // com.qiyukf.nimlib.net.a.a.f
        public void onFail(com.qiyukf.nimlib.net.a.a.e eVar, String str3) {
        }

        @Override // com.qiyukf.nimlib.net.a.a.f
        public void onGetLength(com.qiyukf.nimlib.net.a.a.e eVar, long j) {
        }

        @Override // com.qiyukf.nimlib.net.a.a.f
        public void onProgress(com.qiyukf.nimlib.net.a.a.e eVar, long j) {
        }

        @Override // com.qiyukf.nimlib.net.a.a.f
        public void onStart(com.qiyukf.nimlib.net.a.a.e eVar) {
        }

        public AnonymousClass14(String str3) {
            str = str3;
        }

        @Override // com.qiyukf.nimlib.net.a.a.f
        public void onOK(com.qiyukf.nimlib.net.a.a.e eVar) {
            WatchVideoActivity watchVideoActivity = WatchVideoActivity.this;
            watchVideoActivity.onDownloadSuccess(watchVideoActivity.getDownFilePath(str));
        }
    }

    public String getDownFilePath(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return d.a(m.a(str), com.qiyukf.unicorn.n.e.c.TYPE_FILE);
    }

    public static String secodeToTime(long j) {
        long j2 = j / 60;
        long j3 = j % 60;
        StringBuilder sb = new StringBuilder();
        String strValueOf = String.valueOf(j2);
        if (j2 < 10) {
            strValueOf = "0".concat(strValueOf);
        }
        sb.append(strValueOf);
        sb.append(":");
        String strValueOf2 = String.valueOf(j3);
        if (j3 < 10) {
            strValueOf2 = "0".concat(strValueOf2);
        }
        sb.append(strValueOf2);
        return sb.toString();
    }
}

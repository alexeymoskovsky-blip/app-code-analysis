package com.petkit.android.activities.petkitBleDevice.w7h.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.utils.ImageUtils;
import com.jess.arms.widget.imageloader.ImageCacheSimple;
import com.petkit.android.activities.home.utils.BlurUtils;
import com.petkit.android.activities.petkitBleDevice.t6.widget.PetkitVideoPlayerView;
import com.petkit.android.activities.petkitBleDevice.t6.widget.VideoPlayerViewListener;
import com.petkit.android.activities.petkitBleDevice.utils.DeviceVolumeUtils;
import com.petkit.android.activities.petkitBleDevice.w7h.mode.W7hRecord;
import com.petkit.android.activities.petkitBleDevice.w7h.utils.W7hUtils;
import com.petkit.android.media.video.rtmUtil.PetKitQualityUtil;
import com.petkit.android.utils.CommonUtil;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.DateUtil;
import com.petkit.oversea.R;
import com.petkit.oversea.databinding.LayoutW7hLiveVideoViewBinding;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.io.File;

/* JADX INFO: loaded from: classes5.dex */
public class W7hLiveVideoView extends RelativeLayout {
    private LayoutW7hLiveVideoViewBinding binding;
    private Context context;
    private boolean isHaveStartLive;
    private boolean isInLive;
    private boolean isLandScape;
    public boolean isPlayed;
    private LiveVideoViewStateListener liveVideoViewStateListener;
    private W7hRecord w7hRecord;

    public interface LiveVideoViewStateListener {
        void autoStopRecording();

        void backToPortual();

        void cameraOnClick();

        void eventCloseOnClick();

        void eventOnClick();

        void liveQualityOnClick();

        void liveRetry();

        void muteIconOnClick();

        void normalLive();

        void offlineOnClick();

        void recordOnClick();

        void refillClick();

        void startLiveVideo();

        void stopLiveVideo();

        void switchLandscape();

        void talkDown();

        void talkUp();

        void turnOnCamera();

        void turnOnCamera5Minutes();
    }

    public W7hLiveVideoView(Context context) {
        super(context);
        this.context = context;
        if (isInEditMode()) {
            return;
        }
        initView(context);
    }

    public W7hLiveVideoView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.context = context;
        if (isInEditMode()) {
            return;
        }
        initView(context);
    }

    public W7hLiveVideoView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.context = context;
        if (isInEditMode()) {
            return;
        }
        initView(context);
    }

    private void initView(Context context) {
        LayoutW7hLiveVideoViewBinding layoutW7hLiveVideoViewBindingInflate = LayoutW7hLiveVideoViewBinding.inflate(LayoutInflater.from(context), this, true);
        this.binding = layoutW7hLiveVideoViewBindingInflate;
        layoutW7hLiveVideoViewBindingInflate.videoPlayerView.setLoadingType(1);
        this.binding.videoPlayerView.setTouchListener();
        this.binding.videoPlayerView.setVideoPlayerViewListener(new VideoPlayerViewListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hLiveVideoView.1
            @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.VideoPlayerViewListener
            public void continueVideo(int i) {
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.VideoPlayerViewListener
            public void onBuffering(int i) {
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.VideoPlayerViewListener
            public void onCompleted() {
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.VideoPlayerViewListener
            public void onError(String str) {
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.VideoPlayerViewListener
            public void onFullScreen() {
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.VideoPlayerViewListener
            public void onInitSuccess() {
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.VideoPlayerViewListener
            public void onNormalScreen() {
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.VideoPlayerViewListener
            public void onPausePlay() {
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.VideoPlayerViewListener
            public void onPlaying() {
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.VideoPlayerViewListener
            public void onPrepared() {
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.VideoPlayerViewListener
            public void onReStart() {
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.VideoPlayerViewListener
            public void onReset() {
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.VideoPlayerViewListener
            public void onSeekCompleted() {
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.VideoPlayerViewListener
            public void onStartPlay() {
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.VideoPlayerViewListener
            public void onVideoLongClick() {
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.VideoPlayerViewListener
            public void onVideoLongClickEnd() {
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.VideoPlayerViewListener
            public void playing(String str, long j) {
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.VideoPlayerViewListener
            public void preparedVideo(String str, int i, int i2) {
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.VideoPlayerViewListener
            public void speed(String str) {
            }

            @Override // com.petkit.android.activities.petkitBleDevice.t6.widget.VideoPlayerViewListener
            public void onVideoClick() {
                if (W7hLiveVideoView.this.isLandScape) {
                    W7hLiveVideoView.this.binding.rlLandscapeMask.setVisibility(W7hLiveVideoView.this.binding.rlLandscapeMask.getVisibility() == 8 ? 0 : 8);
                    W7hLiveVideoView.this.liveVideoViewStateListener.eventCloseOnClick();
                } else {
                    W7hLiveVideoView.this.liveVideoViewStateListener.switchLandscape();
                    W7hLiveVideoView.this.setLandscape(true);
                }
            }
        });
        this.binding.rlRootView.setRadius(16.0f);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        return super.dispatchTouchEvent(motionEvent);
    }

    public void setRadius(int i) {
        this.binding.rlRootView.setRadius(i);
    }

    public void setPlayerType(PetkitVideoPlayerView.PlayerType playerType) {
        PetkitVideoPlayerView petkitVideoPlayerView = this.binding.videoPlayerView;
        if (petkitVideoPlayerView != null) {
            petkitVideoPlayerView.setType(playerType);
        }
    }

    public void startRecord() {
        this.binding.recordTime.setVisibility(0);
        this.binding.ivLandscapeRecord.setImageResource(R.drawable.w7h_landscape_live_recording_icon);
    }

    public void stopRecord() {
        this.binding.recordTime.setVisibility(4);
        this.binding.ivLandscapeRecord.setImageResource(R.drawable.w7h_landscape_live_record_icon);
    }

    public void refreshRecordTime(String str) {
        this.binding.recordTime.setText(str);
    }

    public void setLandscape(boolean z) {
        this.isLandScape = z;
        if (z) {
            this.binding.rlLandscapeMask.setVisibility(0);
            this.binding.rlMask.setVisibility(8);
        } else {
            this.binding.rlLandscapeMask.setVisibility(8);
            this.binding.rlMask.setVisibility(0);
        }
        this.binding.videoPlayerView.initPlayerSize();
    }

    public PetkitVideoPlayerView getVideoPlayerView() {
        return this.binding.videoPlayerView;
    }

    public void setupRecord(W7hRecord w7hRecord) {
        this.w7hRecord = w7hRecord;
        refreshLiveView();
    }

    public void setupVoiceIcon() {
        int i = DeviceVolumeUtils.getInstance().isLiveMuteFlag(29) ? R.drawable.t6_home_mute_icon : R.drawable.t6_home_mute_off_icon;
        this.binding.ivMuteIcon.setImageResource(i);
        this.binding.ivVoiceLandscape.setImageResource(i);
    }

    public void setTempVoiceIcon(boolean z) {
        int i = z ? R.drawable.t6_home_mute_icon : R.drawable.t6_home_mute_off_icon;
        this.binding.ivMuteIcon.setImageResource(i);
        this.binding.ivVoiceLandscape.setImageResource(i);
    }

    public void setIsInlive(boolean z) {
        this.isInLive = z;
        this.isHaveStartLive = z;
        int i = z ? 0 : 8;
        this.binding.videoTagLayout.setVisibility(i);
        this.binding.ivMuteIcon.setVisibility(i);
        this.binding.ivVoiceLandscape.setVisibility(i);
        this.binding.rlLandscapeControl.setVisibility(i);
        this.binding.landscapeVideoTagLayout.setVisibility(i);
        if (z) {
            this.binding.rlOfflinePanel.setVisibility(8);
            this.binding.rlBlackPanel.setVisibility(8);
        }
    }

    public void onSpeaking(int i) {
        this.binding.landscapeSoundWaveView.setVoiceLineVolume(i);
    }

    public void setLandscapeSoundWaveViewIsVisible(boolean z) {
        this.binding.landscapeSoundWaveView.setVisibility(z ? 0 : 4);
    }

    public void setupLiveQuality() {
        String string;
        if (PetKitQualityUtil.getInstance().getQualitySelf(this.context, this.w7hRecord.getDeviceId(), 29)) {
            string = this.context.getString(R.string.Cozy_auto);
        } else {
            if (DataHelper.getIntergerSF(this.context, "VIDEO_DEFINITION_29_" + this.w7hRecord.getDeviceId(), 2) == 2) {
                string = this.context.getString(R.string.Quality_hd);
            } else {
                string = this.context.getString(R.string.Standard_definition);
            }
        }
        this.binding.tvVideoQuality.setText(string);
    }

    private void refreshLiveView() {
        LiveVideoViewStateListener liveVideoViewStateListener;
        if (this.w7hRecord == null) {
            return;
        }
        this.binding.rlOfflinePanel.setOnClickListener(null);
        setLandscape(this.isLandScape);
        setupLiveQuality();
        this.binding.rlBlackGround.setVisibility(8);
        this.binding.ivPreview.setVisibility(8);
        this.binding.ivPlayBtn.setVisibility(8);
        this.binding.rlOfflinePanel.setVisibility(8);
        this.binding.rlBlackPanel.setVisibility(8);
        this.binding.tvContent.setVisibility(8);
        this.binding.tvPrompt.setVisibility(8);
        this.binding.btnControl.setVisibility(8);
        this.binding.tvVideoQuality.setVisibility(0);
        if (!W7hUtils.getInstance().checkRefillState(this.w7hRecord)) {
            this.binding.ivLandscapeRefill.setImageResource(R.drawable.w7h_live_add_water_icon);
        } else {
            this.binding.ivLandscapeRefill.setImageResource(R.drawable.w7h_live_add_water_gray_icon);
        }
        if ((this.w7hRecord.getState().getOverall() == 2 || this.w7hRecord.getState().getPower() == 0 || this.w7hRecord.getSettings().getCamera() == 0 || this.w7hRecord.getState().getCameraStatus() == 0 || (this.w7hRecord.getSettings().getPreLive() == 0 && !this.isPlayed)) && (liveVideoViewStateListener = this.liveVideoViewStateListener) != null) {
            liveVideoViewStateListener.autoStopRecording();
        }
        if (this.w7hRecord.getState().getOverall() == 2) {
            setIsInlive(false);
            this.binding.rlOfflinePanel.setVisibility(0);
            this.binding.tvOfflineTime.setVisibility(0);
            this.binding.tvOfflineTime.setText(this.context.getString(R.string.Offline_time, DateUtil.getDateFormatShortTimeShortString(DateUtil.long2str(this.w7hRecord.getState().getOfflineTime(), "yyyy-MM-dd'T'HH:mm:ss.SSSZ"))));
            this.binding.ivOfflineIcon.setVisibility(0);
            this.binding.tvVideoQuality.setVisibility(8);
            this.binding.rlBlackGround.setVisibility(0);
            this.binding.rlOfflinePanel.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hLiveVideoView$$ExternalSyntheticLambda3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$refreshLiveView$0(view);
                }
            });
            LiveVideoViewStateListener liveVideoViewStateListener2 = this.liveVideoViewStateListener;
            if (liveVideoViewStateListener2 != null) {
                liveVideoViewStateListener2.stopLiveVideo();
            }
        } else if (this.w7hRecord.getState().getPower() == 0) {
            setIsInlive(false);
            this.binding.rlOfflinePanel.setVisibility(0);
            this.binding.tvOfflineTime.setVisibility(0);
            this.binding.tvOfflineTime.setText(R.string.Power_off);
            this.binding.ivOfflineIcon.setVisibility(8);
            this.binding.tvVideoQuality.setVisibility(8);
            this.binding.rlBlackGround.setVisibility(0);
            LiveVideoViewStateListener liveVideoViewStateListener3 = this.liveVideoViewStateListener;
            if (liveVideoViewStateListener3 != null) {
                liveVideoViewStateListener3.stopLiveVideo();
            }
        } else if (this.w7hRecord.getSettings().getCamera() == 0 && this.w7hRecord.getState().getCameraStatus() == 1) {
            LiveVideoViewStateListener liveVideoViewStateListener4 = this.liveVideoViewStateListener;
            if (liveVideoViewStateListener4 != null) {
                liveVideoViewStateListener4.normalLive();
            }
        } else if (this.w7hRecord.getSettings().getCamera() == 0) {
            setIsInlive(false);
            showBlackPanel(R.string.Camera_is_off, R.string.W7h_work_during_camera_closed, R.string.Turn_on_camera);
            this.binding.btnControl.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hLiveVideoView$$ExternalSyntheticLambda4
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$refreshLiveView$1(view);
                }
            });
            this.binding.rlBlackGround.setVisibility(0);
            this.binding.tvVideoQuality.setVisibility(8);
            LiveVideoViewStateListener liveVideoViewStateListener5 = this.liveVideoViewStateListener;
            if (liveVideoViewStateListener5 != null) {
                liveVideoViewStateListener5.stopLiveVideo();
            }
        } else if (this.w7hRecord.getState().getCameraStatus() == 0) {
            setIsInlive(false);
            showBlackPanel(R.string.Camera_off_as_planed, R.string.W7h_work_during_camera_closed, R.string.Turn_on_camera_out_worktime);
            this.binding.btnControl.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hLiveVideoView$$ExternalSyntheticLambda5
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$refreshLiveView$2(view);
                }
            });
            this.binding.rlBlackGround.setVisibility(0);
            this.binding.tvVideoQuality.setVisibility(8);
            LiveVideoViewStateListener liveVideoViewStateListener6 = this.liveVideoViewStateListener;
            if (liveVideoViewStateListener6 != null) {
                liveVideoViewStateListener6.stopLiveVideo();
            }
        } else if (this.w7hRecord.getSettings().getPreLive() == 0) {
            if (this.isPlayed) {
                this.binding.ivMuteIcon.setVisibility(0);
                LiveVideoViewStateListener liveVideoViewStateListener7 = this.liveVideoViewStateListener;
                if (liveVideoViewStateListener7 != null && !this.isHaveStartLive) {
                    this.isHaveStartLive = true;
                    liveVideoViewStateListener7.startLiveVideo();
                }
            } else {
                this.binding.rlBlackGround.setVisibility(0);
                this.binding.ivPlayBtn.setVisibility(0);
                this.binding.ivPreview.setVisibility(0);
                this.isHaveStartLive = false;
                setIsInlive(false);
                this.binding.ivPlayBtn.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hLiveVideoView$$ExternalSyntheticLambda6
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$refreshLiveView$3(view);
                    }
                });
                LiveVideoViewStateListener liveVideoViewStateListener8 = this.liveVideoViewStateListener;
                if (liveVideoViewStateListener8 != null) {
                    liveVideoViewStateListener8.stopLiveVideo();
                }
                loadAndBlurPreviewImage();
            }
        } else {
            LiveVideoViewStateListener liveVideoViewStateListener9 = this.liveVideoViewStateListener;
            if (liveVideoViewStateListener9 != null) {
                liveVideoViewStateListener9.normalLive();
            }
        }
        setupVoiceIcon();
        initListener();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$refreshLiveView$0(View view) {
        this.liveVideoViewStateListener.offlineOnClick();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$refreshLiveView$1(View view) {
        this.liveVideoViewStateListener.turnOnCamera();
        this.binding.rlBlackGround.setVisibility(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$refreshLiveView$2(View view) {
        this.liveVideoViewStateListener.turnOnCamera5Minutes();
        this.binding.rlBlackGround.setVisibility(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$refreshLiveView$3(View view) {
        LiveVideoViewStateListener liveVideoViewStateListener = this.liveVideoViewStateListener;
        if (liveVideoViewStateListener != null) {
            liveVideoViewStateListener.startLiveVideo();
            this.binding.rlBlackGround.setVisibility(8);
            this.binding.ivPreview.setVisibility(8);
            this.binding.videoPlayerView.showLoadingView();
            this.isPlayed = true;
        }
    }

    private void showBlackPanel(int i, int i2, int i3) {
        this.binding.rlOfflinePanel.setVisibility(8);
        this.binding.rlBlackPanel.setVisibility(0);
        this.binding.tvContent.setVisibility(0);
        this.binding.tvPrompt.setVisibility(0);
        this.binding.btnControl.setVisibility(0);
        this.binding.tvContent.setText(i);
        this.binding.tvPrompt.setText(i2);
        this.binding.btnControl.setText(i3);
    }

    public boolean liveIsBlack() {
        return this.binding.btnControl.getVisibility() == 0 || this.binding.tvContent.getVisibility() == 0;
    }

    @SuppressLint({"CheckResult"})
    private void loadAndBlurPreviewImage() {
        if (TextUtils.isEmpty(W7hUtils.getInstance().getEventPreview(this.context, this.w7hRecord.getDeviceId()))) {
            return;
        }
        new ImageCacheSimple(CommonUtils.getAppContext()).getImageCachePath(CommonUtil.httpToHttps(W7hUtils.getInstance().getEventPreview(this.context, this.w7hRecord.getDeviceId())), new ImageCacheSimple.IGetImageCacheListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hLiveVideoView$$ExternalSyntheticLambda7
            @Override // com.jess.arms.widget.imageloader.ImageCacheSimple.IGetImageCacheListener
            public final void onCachePath(String str) {
                this.f$0.lambda$loadAndBlurPreviewImage$7(str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadAndBlurPreviewImage$7(String str) {
        if (str == null) {
            return;
        }
        Observable.just(str).subscribeOn(Schedulers.io()).map(new Function() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hLiveVideoView$$ExternalSyntheticLambda0
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return this.f$0.lambda$loadAndBlurPreviewImage$4((String) obj);
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hLiveVideoView$$ExternalSyntheticLambda1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                this.f$0.lambda$loadAndBlurPreviewImage$5((Bitmap) obj);
            }
        }, new Consumer() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hLiveVideoView$$ExternalSyntheticLambda2
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                ((Throwable) obj).printStackTrace();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Bitmap lambda$loadAndBlurPreviewImage$4(String str) throws Exception {
        Bitmap bitmapDecodeFile = BitmapFactory.decodeFile(ImageUtils.decryptImageFile(new File(str), W7hUtils.getInstance().getEventPreviewKey(this.context, this.w7hRecord.getDeviceId())).getAbsolutePath());
        if (bitmapDecodeFile == null) {
            throw new RuntimeException("Bitmap decode failed");
        }
        return CommonUtil.bimapSquareRound(200, BlurUtils.blur(CommonUtils.getAppContext(), bitmapDecodeFile, 10, 0.125f));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadAndBlurPreviewImage$5(Bitmap bitmap) throws Exception {
        this.binding.ivPreview.setImageBitmap(bitmap);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initListener$8(View view) {
        this.liveVideoViewStateListener.liveQualityOnClick();
    }

    public void initListener() {
        this.binding.tvVideoQuality.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hLiveVideoView$$ExternalSyntheticLambda10
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initListener$8(view);
            }
        });
        this.binding.tvEvent.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hLiveVideoView$$ExternalSyntheticLambda11
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initListener$9(view);
            }
        });
        this.binding.ivLandscapeRecord.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hLiveVideoView$$ExternalSyntheticLambda12
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initListener$10(view);
            }
        });
        this.binding.ivLandscapeCamera.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hLiveVideoView$$ExternalSyntheticLambda13
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initListener$11(view);
            }
        });
        this.binding.ivLandscapeRefill.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hLiveVideoView$$ExternalSyntheticLambda14
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initListener$12(view);
            }
        });
        this.binding.ibBackLandscape.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hLiveVideoView$$ExternalSyntheticLambda15
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initListener$13(view);
            }
        });
        this.binding.ivMuteIcon.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hLiveVideoView$$ExternalSyntheticLambda16
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initListener$14(view);
            }
        });
        this.binding.ivVoiceLandscape.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hLiveVideoView$$ExternalSyntheticLambda17
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initListener$15(view);
            }
        });
        this.binding.ivLandscapeSpeak.setOnTouchListener(new View.OnTouchListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hLiveVideoView$$ExternalSyntheticLambda18
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return this.f$0.lambda$initListener$16(view, motionEvent);
            }
        });
        this.binding.rlBlackGround.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hLiveVideoView$$ExternalSyntheticLambda19
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initListener$17(view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initListener$9(View view) {
        this.liveVideoViewStateListener.eventOnClick();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initListener$10(View view) {
        this.liveVideoViewStateListener.recordOnClick();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initListener$11(View view) {
        this.liveVideoViewStateListener.cameraOnClick();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initListener$12(View view) {
        if (W7hUtils.getInstance().checkRefillState(this.w7hRecord)) {
            return;
        }
        this.liveVideoViewStateListener.refillClick();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initListener$13(View view) {
        this.liveVideoViewStateListener.backToPortual();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initListener$14(View view) {
        this.liveVideoViewStateListener.muteIconOnClick();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initListener$15(View view) {
        this.liveVideoViewStateListener.muteIconOnClick();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$initListener$16(View view, MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 0) {
            this.binding.ivLandscapeSpeak.setImageResource(R.drawable.w7h_landscape_live_speak_down_icon);
            this.liveVideoViewStateListener.talkDown();
        } else if (action == 1 || action == 3) {
            this.binding.ivLandscapeSpeak.setImageResource(R.drawable.w7h_landscape_live_speak_icon);
            this.liveVideoViewStateListener.talkUp();
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initListener$17(View view) {
        if (this.isLandScape) {
            this.binding.rlLandscapeMask.setVisibility(this.binding.rlLandscapeMask.getVisibility() == 8 ? 0 : 8);
            this.liveVideoViewStateListener.eventCloseOnClick();
        } else {
            this.liveVideoViewStateListener.switchLandscape();
            setLandscape(true);
        }
    }

    public void refreshTimeoutState() {
        this.binding.ivPlayBtn.setVisibility(8);
        this.binding.rlOfflinePanel.setVisibility(8);
        this.binding.rlBlackPanel.setVisibility(0);
        this.binding.rlBlackGround.setVisibility(0);
        this.binding.btnControl.setVisibility(0);
        this.binding.tvContent.setVisibility(0);
        this.binding.tvContent.setText(R.string.Load_timeout);
        this.binding.tvPrompt.setVisibility(8);
        this.binding.btnControl.setText(R.string.Retry);
        setIsInlive(false);
        this.binding.btnControl.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hLiveVideoView$$ExternalSyntheticLambda8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$refreshTimeoutState$18(view);
            }
        });
        this.binding.rlBlackPanel.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.w7h.widget.W7hLiveVideoView$$ExternalSyntheticLambda9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$refreshTimeoutState$19(view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$refreshTimeoutState$18(View view) {
        LiveVideoViewStateListener liveVideoViewStateListener = this.liveVideoViewStateListener;
        if (liveVideoViewStateListener != null) {
            liveVideoViewStateListener.liveRetry();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$refreshTimeoutState$19(View view) {
        LiveVideoViewStateListener liveVideoViewStateListener = this.liveVideoViewStateListener;
        if (liveVideoViewStateListener != null) {
            liveVideoViewStateListener.offlineOnClick();
        }
    }

    public void setLiveVideoViewStateListener(LiveVideoViewStateListener liveVideoViewStateListener) {
        this.liveVideoViewStateListener = liveVideoViewStateListener;
    }

    public void resetTimeoutView() {
        this.binding.ivPlayBtn.setVisibility(8);
        this.binding.rlOfflinePanel.setVisibility(8);
        this.binding.rlBlackPanel.setVisibility(8);
        this.binding.btnControl.setOnClickListener(null);
    }

    public void setListener(VideoPlayerViewListener videoPlayerViewListener) {
        PetkitVideoPlayerView petkitVideoPlayerView = this.binding.videoPlayerView;
        if (petkitVideoPlayerView != null) {
            petkitVideoPlayerView.setVideoPlayerViewListener(videoPlayerViewListener);
        }
    }

    public void onConfigChanged(Configuration configuration) {
        this.binding.videoPlayerView.onConfigChanged(configuration);
    }

    public void setTouchListener() {
        this.binding.videoPlayerView.setTouchListener();
    }
}

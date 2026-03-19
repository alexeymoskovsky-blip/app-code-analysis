package com.petkit.android.activities.virtual.d4sh.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.jess.arms.utils.Consts;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.widget.PetkitToast;
import com.petkit.android.activities.petkitBleDevice.d4sh.listener.D4shHomePlayerLandscapeLiveViewListener;
import com.petkit.android.activities.petkitBleDevice.d4sh.mode.D4shDailyFeeds;
import com.petkit.android.activities.petkitBleDevice.d4sh.mode.D4shDayItem;
import com.petkit.android.activities.petkitBleDevice.d4sh.mode.D4shRecord;
import com.petkit.android.activities.petkitBleDevice.d4sh.utils.D4shUtils;
import com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomePlayerLandscapeLiveView;
import com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shHomePlayerLandscapeRecordView;
import com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shManualWindow;
import com.petkit.android.activities.petkitBleDevice.download.LocalAlbumActivity;
import com.petkit.android.activities.petkitBleDevice.download.mode.CloudVideo;
import com.petkit.android.activities.petkitBleDevice.download.mode.MediaMsg;
import com.petkit.android.activities.petkitBleDevice.download.mode.VideoDownloadRecord;
import com.petkit.android.activities.petkitBleDevice.download.utils.VideoDownloadManager;
import com.petkit.android.activities.petkitBleDevice.listener.BasePetkitLandscapeEatVideoViewListener;
import com.petkit.android.activities.petkitBleDevice.listener.BasePetkitPlayerLandscapeRecordViewListener;
import com.petkit.android.activities.petkitBleDevice.widget.PetkitDeviceVideoRecordListViewGroup;
import com.petkit.android.activities.petkitBleDevice.widget.datepicker.BasePetkitDeviceDatePickerView;
import com.petkit.android.activities.petkitBleDevice.widget.datepicker.PetkitPlayerLandscapeDatePickerWindow;
import com.petkit.android.activities.petkitBleDevice.widget.feeder.BasePetkitPlayerLandscapeSelectorWindow;
import com.petkit.android.activities.virtual.d4sh.widget.VirtualD4hManualWindow;
import com.petkit.android.media.video.player.BasePetkitPlayerLandscapeView;
import com.petkit.android.media.video.player.BasePetkitPlayerLandscapeViewClickListener;
import com.petkit.android.media.video.player.ijkplayer.VideoUtils;
import com.petkit.android.model.ChatMsgTemp;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.oversea.R;
import java.io.File;
import java.util.HashMap;

/* JADX INFO: loaded from: classes6.dex */
public class VirtualD4shHomePlayerLandscapeView extends BasePetkitPlayerLandscapeView implements D4shHomePlayerLandscapeLiveViewListener, BasePetkitPlayerLandscapeRecordViewListener, View.OnClickListener, BasePetkitLandscapeEatVideoViewListener {
    private AnimationDrawable animationDrawable;
    private int cloudVideoTimesSpeedType;
    protected VirtualD4hPlayerLandscapeExtraMealWindow d4hPlayerLandscapeExtraMealWindow;
    protected VirtualD4shPlayerLandscapeExtraMealWindow d4shPlayerLandscapeExtraMealWindow;
    private PetkitPlayerLandscapeDatePickerWindow datePickerWindow;
    private long deviceId;
    private int deviceType;
    private ImageView doubleSpeedImageView;
    private String eatVideoFullVideoUrl;
    private D4shDayItem eatVideoItem;
    private String eatVideoShortVideoUrl;
    private int eatVideoType;
    private VirtualBasePetkitPlayerLandscapeEatVideoWindow eatVideoWindow;
    private long expire;
    private long mark;
    private D4shHomePlayerLandscapeLiveView playerLandscapeLiveView;
    private D4shHomePlayerLandscapeRecordView playerLandscapeRecordView;
    private boolean serviceUnavailable;
    private long startTime;
    private TextView timeSpeedTipTv;
    private LinearLayout timeSpeedView;
    private BasePetkitPlayerLandscapeSelectorWindow videoQualitySelectorWindow;
    private int videoType;

    private void initMultipleVideoView() {
    }

    @Override // com.petkit.android.activities.petkitBleDevice.listener.BasePetkitLandscapeEatVideoViewListener
    public void onEatCompareImageClick(D4shDayItem d4shDayItem, String str, String str2) {
    }

    @Override // com.petkit.android.activities.petkitBleDevice.listener.BasePetkitPlayerLandscapeRecordViewListener
    public /* synthetic */ void onRecordAudit() {
        BasePetkitPlayerLandscapeRecordViewListener.CC.$default$onRecordAudit(this);
    }

    @Override // com.petkit.android.activities.petkitBleDevice.listener.BasePetkitPlayerLandscapeRecordViewListener
    public void onRecordViewStopStartVideoBtnClick() {
    }

    @Override // com.petkit.android.media.video.player.BasePetkitPlayerLandscapeView
    public void setRecordVideoType(int i) {
    }

    @Override // com.petkit.android.media.video.player.BasePetkitPlayerLandscapeView
    public void setTimeSpeed(String str) {
    }

    @Override // com.petkit.android.media.video.player.BasePetkitPlayerLandscapeView
    public void setTosvSelectIndex(int i) {
    }

    @Override // com.petkit.android.media.video.player.BasePetkitPlayerLandscapeView
    public void setTripodHeadStatus(Boolean bool, Integer num, Boolean bool2) {
    }

    public VirtualD4shHomePlayerLandscapeView(Context context) {
        super(context);
        this.cloudVideoTimesSpeedType = 1;
        this.eatVideoType = 1;
        this.expire = 0L;
        this.mark = 0L;
        this.startTime = 0L;
        this.videoType = 1;
    }

    public VirtualD4shHomePlayerLandscapeView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.cloudVideoTimesSpeedType = 1;
        this.eatVideoType = 1;
        this.expire = 0L;
        this.mark = 0L;
        this.startTime = 0L;
        this.videoType = 1;
    }

    public VirtualD4shHomePlayerLandscapeView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.cloudVideoTimesSpeedType = 1;
        this.eatVideoType = 1;
        this.expire = 0L;
        this.mark = 0L;
        this.startTime = 0L;
        this.videoType = 1;
    }

    @Override // com.petkit.android.media.video.player.BasePetkitPlayerLandscapeView
    public void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.d4sh_player_landscape_view, this);
        initLiveView();
        initRecordView();
        initMultipleVideoView();
        this.timeSpeedView = (LinearLayout) findViewById(R.id.time_speed_view);
        this.doubleSpeedImageView = (ImageView) findViewById(R.id.double_speed_image_view);
        this.timeSpeedTipTv = (TextView) findViewById(R.id.time_speed_tip_tv);
        this.animationDrawable = (AnimationDrawable) this.doubleSpeedImageView.getBackground();
        setVisibility(8);
    }

    public void setDeviceInfo(int i, long j) {
        this.deviceType = i;
        this.deviceId = j;
        if (DataHelper.getIntergerSF(getContext(), Constants.VIDEO_DEFINITION + i + "_" + j, 2) == 2) {
            this.playerLandscapeLiveView.setQuality(getContext().getResources().getString(R.string.Quality_hd));
        } else {
            this.playerLandscapeLiveView.setQuality(getContext().getResources().getString(R.string.Standard_definition));
        }
    }

    private void initLiveView() {
        this.playerLandscapeLiveView = (D4shHomePlayerLandscapeLiveView) findViewById(R.id.d4sh_home_player_landscape_live_view);
    }

    private void initRecordView() {
        D4shHomePlayerLandscapeRecordView d4shHomePlayerLandscapeRecordView = (D4shHomePlayerLandscapeRecordView) findViewById(R.id.d4sh_home_player_landscape_record_view);
        this.playerLandscapeRecordView = d4shHomePlayerLandscapeRecordView;
        d4shHomePlayerLandscapeRecordView.setVisibility(8);
        this.playerLandscapeRecordView.setFeedbackBtnVisible(Boolean.FALSE, new int[0]);
    }

    @Override // com.petkit.android.media.video.player.BasePetkitPlayerLandscapeView
    public void initOtherView(Context context) {
        initWindow(context);
    }

    @Override // com.petkit.android.media.video.player.BasePetkitPlayerLandscapeView
    public void initClickEvent() {
        this.playerLandscapeLiveView.setViewListener(this);
        this.playerLandscapeRecordView.setViewListener(this);
    }

    @Override // com.petkit.android.media.video.player.BasePetkitPlayerLandscapeView
    public void hideOneself() {
        setVisibility(8);
    }

    @Override // com.petkit.android.media.video.player.BasePetkitPlayerLandscapeView
    public void showOneself(Boolean bool, Boolean bool2) {
        setVisibility(0);
    }

    @Override // com.petkit.android.media.video.player.BasePetkitPlayerLandscapeView
    public void setQuality(String str) {
        this.playerLandscapeLiveView.setQuality(str);
    }

    public void setTipState(D4shRecord d4shRecord) {
        this.eatVideoWindow.setTipState(d4shRecord);
    }

    @Override // com.petkit.android.media.video.player.BasePetkitPlayerLandscapeView
    public void setVolumeImageResource(int i) {
        this.playerLandscapeLiveView.setVolumeImageResource(i);
        this.playerLandscapeRecordView.setVolumeImageResource(i);
    }

    @Override // com.petkit.android.media.video.player.BasePetkitPlayerLandscapeView
    public void setRecordBtnImageResource(Boolean bool, int i) {
        if (this.playerLandscapeLiveView.getVisibility() == 0) {
            this.playerLandscapeLiveView.setRecordBtnImageResource(bool, i);
        } else {
            this.playerLandscapeRecordView.setRecordBtnImageResource(bool, i);
        }
    }

    @Override // com.petkit.android.media.video.player.BasePetkitPlayerLandscapeView
    public void setRecordTimeText(Boolean bool, String str) {
        if (this.playerLandscapeLiveView.getVisibility() == 0) {
            this.playerLandscapeLiveView.setRecordTimeText(bool, str);
        } else {
            this.playerLandscapeRecordView.setRecordTimeText(bool, str);
        }
    }

    @Override // com.petkit.android.media.video.player.BasePetkitPlayerLandscapeView
    public void setExtraMealBtnImageResource(Integer num, Integer num2) {
        this.playerLandscapeLiveView.setExtraMealImageResource(num, num2);
    }

    @Override // com.petkit.android.media.video.player.BasePetkitPlayerLandscapeView
    public void setPlayerSoundWaveViewStatus(Boolean bool, Integer num) {
        this.playerLandscapeLiveView.setPlayerSoundWaveViewStatus(bool, num);
    }

    @Override // com.petkit.android.media.video.player.BasePetkitPlayerLandscapeView
    public void setSeekbarStatus(Integer num, Integer num2, String str) {
        this.playerLandscapeRecordView.setSeekbarAndTimeStatus(num, num2, str);
    }

    @Override // com.petkit.android.media.video.player.BasePetkitPlayerLandscapeView
    public void setPlayerSwitchImageViewResource(Integer num) {
        this.playerLandscapeRecordView.setVideoStopStartImageViewResource(num);
    }

    @Override // com.petkit.android.media.video.player.BasePetkitPlayerLandscapeView
    public void setLiveButtonVisibleStatus(Boolean bool) {
        this.playerLandscapeLiveView.setButtonVisibleStatus(bool);
    }

    @Override // com.petkit.android.media.video.player.BasePetkitPlayerLandscapeView
    public void setRecordButtonVisibleStatus(Boolean bool) {
        this.playerLandscapeRecordView.setButtonVisibleStatus(bool);
    }

    @Override // com.petkit.android.media.video.player.BasePetkitPlayerLandscapeView
    public void resetRecordViewStatus() {
        this.playerLandscapeRecordView.resetViewStatus();
    }

    @Override // com.petkit.android.media.video.player.BasePetkitPlayerLandscapeView
    public void viewClick() {
        int i = this.videoType;
        if (i == 1) {
            D4shHomePlayerLandscapeLiveView d4shHomePlayerLandscapeLiveView = this.playerLandscapeLiveView;
            d4shHomePlayerLandscapeLiveView.setVisibility(d4shHomePlayerLandscapeLiveView.getVisibility() != 8 ? 8 : 0);
        } else if (i == 2) {
            D4shHomePlayerLandscapeRecordView d4shHomePlayerLandscapeRecordView = this.playerLandscapeRecordView;
            d4shHomePlayerLandscapeRecordView.setVisibility(d4shHomePlayerLandscapeRecordView.getVisibility() != 8 ? 8 : 0);
        }
    }

    @Override // com.petkit.android.media.video.player.BasePetkitPlayerLandscapeView
    public void setIntercomBtnImageResource(int i) {
        this.playerLandscapeLiveView.setIntercomBtnImageResource(i);
    }

    private void initWindow(Context context) {
        initVideoQualityWindow(context);
        initEatVideoWindow(context);
        initDatePickerWindow(context);
        initTimesSpeedSelectorWindow(this, new PopupWindow.OnDismissListener() { // from class: com.petkit.android.activities.virtual.d4sh.widget.VirtualD4shHomePlayerLandscapeView.1
            @Override // android.widget.PopupWindow.OnDismissListener
            public void onDismiss() {
                VirtualD4shHomePlayerLandscapeView.this.playerLandscapeRecordView.setVisibility(0);
            }
        }, "1X", "2X", "4X");
        initMoreActionSelectorWindow(this, new PopupWindow.OnDismissListener() { // from class: com.petkit.android.activities.virtual.d4sh.widget.VirtualD4shHomePlayerLandscapeView.2
            @Override // android.widget.PopupWindow.OnDismissListener
            public void onDismiss() {
                VirtualD4shHomePlayerLandscapeView.this.playerLandscapeRecordView.setVisibility(0);
            }
        }, getContext().getResources().getString(R.string.Download_preview_video), getContext().getResources().getString(R.string.Download_full_video), getContext().getResources().getString(R.string.Delete));
    }

    private void initVideoQualityWindow(Context context) {
        BasePetkitPlayerLandscapeSelectorWindow basePetkitPlayerLandscapeSelectorWindow = new BasePetkitPlayerLandscapeSelectorWindow(context);
        this.videoQualitySelectorWindow = basePetkitPlayerLandscapeSelectorWindow;
        basePetkitPlayerLandscapeSelectorWindow.setSelectorOptionsText(context.getString(R.string.Standard_definition), context.getString(R.string.Quality_hd), null, null, this);
        this.videoQualitySelectorWindow.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.petkit.android.activities.virtual.d4sh.widget.VirtualD4shHomePlayerLandscapeView$$ExternalSyntheticLambda0
            @Override // android.widget.PopupWindow.OnDismissListener
            public final void onDismiss() {
                this.f$0.lambda$initVideoQualityWindow$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initVideoQualityWindow$0() {
        this.playerLandscapeLiveView.setVisibility(0);
    }

    private void initEatVideoWindow(Context context) {
        VirtualBasePetkitPlayerLandscapeEatVideoWindow virtualBasePetkitPlayerLandscapeEatVideoWindow = new VirtualBasePetkitPlayerLandscapeEatVideoWindow(context);
        this.eatVideoWindow = virtualBasePetkitPlayerLandscapeEatVideoWindow;
        virtualBasePetkitPlayerLandscapeEatVideoWindow.setCalenderVisibility(4);
        this.eatVideoWindow.setVideoRecordTagViewGroupClickListener(this, this);
        this.eatVideoWindow.setEatVideoViewListener(this);
        this.eatVideoWindow.setCalenderVisibility(4);
    }

    private void initDatePickerWindow(Context context) {
        this.datePickerWindow = new PetkitPlayerLandscapeDatePickerWindow(context);
    }

    private void showVideoQualityWindow() {
        if (DataHelper.getIntergerSF(getContext(), Constants.VIDEO_DEFINITION + this.deviceType + "_" + this.deviceId, 2) == 2) {
            this.videoQualitySelectorWindow.setSelectorOptionViewStyleResId(null, Integer.valueOf(R.style.New_Style_Content_16_D4sh_Main_Orange_With_Bold), null, null);
        } else {
            this.videoQualitySelectorWindow.setSelectorOptionViewStyleResId(Integer.valueOf(R.style.New_Style_Content_16_D4sh_Main_Orange_With_Bold), null, null, null);
        }
        this.videoQualitySelectorWindow.showAtLocation(((Activity) getContext()).getWindow().getDecorView(), 5, 0, 0);
    }

    public void showEatVideoWindow(D4shDailyFeeds d4shDailyFeeds, String str, String str2, D4shRecord d4shRecord) {
        if (str2 != null) {
            this.eatVideoWindow.setDeviceRecordDate(str, str2);
        }
        if (d4shDailyFeeds != null) {
            this.eatVideoWindow.refreshDateData(d4shDailyFeeds, this.eatVideoItem, d4shRecord);
        }
        this.eatVideoWindow.showAtLocation(((Activity) getContext()).getWindow().getDecorView(), 5, 0, 0);
    }

    public void refreshEatVideoDeviceRecord(D4shDailyFeeds d4shDailyFeeds, D4shRecord d4shRecord) {
        VirtualBasePetkitPlayerLandscapeEatVideoWindow virtualBasePetkitPlayerLandscapeEatVideoWindow;
        if (d4shDailyFeeds == null || (virtualBasePetkitPlayerLandscapeEatVideoWindow = this.eatVideoWindow) == null || !virtualBasePetkitPlayerLandscapeEatVideoWindow.isShowing()) {
            return;
        }
        this.eatVideoWindow.refreshDateData(d4shDailyFeeds, this.eatVideoItem, d4shRecord);
    }

    public void showDatePickerWindow(String str, String str2, long j, BasePetkitDeviceDatePickerView.OnCalendarChangeListener onCalendarChangeListener) {
        VirtualBasePetkitPlayerLandscapeEatVideoWindow virtualBasePetkitPlayerLandscapeEatVideoWindow = this.eatVideoWindow;
        if (virtualBasePetkitPlayerLandscapeEatVideoWindow != null && virtualBasePetkitPlayerLandscapeEatVideoWindow.isShowing()) {
            this.eatVideoWindow.dismiss();
        }
        this.datePickerWindow.showAtLocation(((Activity) getContext()).getWindow().getDecorView(), 5, 0, 0);
        this.datePickerWindow.setDatePageChangeListener(onCalendarChangeListener);
        this.datePickerWindow.setDeviceRecordInfo(str, str2, 25, j);
    }

    public void refreshDatePickerSelectDate() {
        PetkitPlayerLandscapeDatePickerWindow petkitPlayerLandscapeDatePickerWindow = this.datePickerWindow;
        if (petkitPlayerLandscapeDatePickerWindow == null || !petkitPlayerLandscapeDatePickerWindow.isShowing()) {
            return;
        }
        this.datePickerWindow.refreshData();
    }

    public void dismissDatePickerWindowAfterSelectDate() {
        PetkitPlayerLandscapeDatePickerWindow petkitPlayerLandscapeDatePickerWindow = this.datePickerWindow;
        if (petkitPlayerLandscapeDatePickerWindow == null || !petkitPlayerLandscapeDatePickerWindow.isShowing()) {
            return;
        }
        this.datePickerWindow.dismiss();
    }

    private void showLandscapeLiveView() {
        if (this.playerLandscapeLiveView.getVisibility() == 8) {
            this.playerLandscapeLiveView.setVisibility(0);
            this.playerLandscapeRecordView.setVisibility(8);
        }
        this.videoType = 1;
    }

    private void showLandscapeRecordView() {
        if (this.playerLandscapeRecordView.getVisibility() == 8) {
            this.playerLandscapeRecordView.setVisibility(0);
            this.playerLandscapeLiveView.setVisibility(8);
        }
        this.videoType = 2;
    }

    public void showExtraMealWindow(long j, int i) {
        if (i == 0) {
            VirtualD4shPlayerLandscapeExtraMealWindow virtualD4shPlayerLandscapeExtraMealWindow = this.d4shPlayerLandscapeExtraMealWindow;
            if (virtualD4shPlayerLandscapeExtraMealWindow == null || !virtualD4shPlayerLandscapeExtraMealWindow.isShowing()) {
                new HashMap().put("type", "Livefullscreen");
                this.d4shPlayerLandscapeExtraMealWindow = new VirtualD4shPlayerLandscapeExtraMealWindow((Activity) getContext(), j, i);
                this.d4shPlayerLandscapeExtraMealWindow.setNumberPickerValue(CommonUtils.getSysIntMap(getContext(), Consts.DEVICE_ADD_MEAL_NOW_D4SH_LAST_AMOUNT1, 1), CommonUtils.getSysIntMap(getContext(), Consts.DEVICE_ADD_MEAL_NOW_D4SH_LAST_AMOUNT2, 1));
                this.d4shPlayerLandscapeExtraMealWindow.setFeedManualListener(new D4shManualWindow.onFeedManualListener() { // from class: com.petkit.android.activities.virtual.d4sh.widget.VirtualD4shHomePlayerLandscapeView.3
                    @Override // com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shManualWindow.onFeedManualListener
                    public void onFeedManualSuccess(int i2, int i3, int i4, int i5) {
                    }
                });
                this.d4shPlayerLandscapeExtraMealWindow.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.petkit.android.activities.virtual.d4sh.widget.VirtualD4shHomePlayerLandscapeView.4
                    @Override // android.widget.PopupWindow.OnDismissListener
                    public void onDismiss() {
                        VirtualD4shHomePlayerLandscapeView.this.playerLandscapeLiveView.setVisibility(0);
                    }
                });
                this.d4shPlayerLandscapeExtraMealWindow.show(((Activity) getContext()).getWindow().getDecorView());
                return;
            }
            return;
        }
        if (i == 1) {
            VirtualD4hPlayerLandscapeExtraMealWindow virtualD4hPlayerLandscapeExtraMealWindow = this.d4hPlayerLandscapeExtraMealWindow;
            if (virtualD4hPlayerLandscapeExtraMealWindow == null || !virtualD4hPlayerLandscapeExtraMealWindow.isShowing()) {
                new HashMap().put("type", "Livefullscreen");
                VirtualD4hPlayerLandscapeExtraMealWindow virtualD4hPlayerLandscapeExtraMealWindow2 = new VirtualD4hPlayerLandscapeExtraMealWindow((Activity) getContext(), j, i);
                this.d4hPlayerLandscapeExtraMealWindow = virtualD4hPlayerLandscapeExtraMealWindow2;
                virtualD4hPlayerLandscapeExtraMealWindow2.setFeedManualListener(new VirtualD4hManualWindow.onFeedManualListener() { // from class: com.petkit.android.activities.virtual.d4sh.widget.VirtualD4shHomePlayerLandscapeView.5
                    @Override // com.petkit.android.activities.virtual.d4sh.widget.VirtualD4hManualWindow.onFeedManualListener
                    public void onFeedManualSuccess(int i2, int i3, int i4, ChatMsgTemp chatMsgTemp) {
                    }
                });
                this.d4hPlayerLandscapeExtraMealWindow.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.petkit.android.activities.virtual.d4sh.widget.VirtualD4shHomePlayerLandscapeView.6
                    @Override // android.widget.PopupWindow.OnDismissListener
                    public void onDismiss() {
                        VirtualD4shHomePlayerLandscapeView.this.playerLandscapeLiveView.setVisibility(0);
                    }
                });
                this.d4hPlayerLandscapeExtraMealWindow.show(((Activity) getContext()).getWindow().getDecorView());
            }
        }
    }

    public void downloadShortVideoSuccess(MediaMsg mediaMsg) {
        if (!TextUtils.isEmpty(this.eatVideoShortVideoUrl) && mediaMsg.getUrl().equals(VideoUtils.getVideoUrl(this.eatVideoShortVideoUrl))) {
            if (mediaMsg.getState() == 12) {
                PetkitToast.showTopToast(getContext(), getContext().getString(R.string.Saved_to_album_click_to_view) + " >", 0, 1, new View.OnClickListener() { // from class: com.petkit.android.activities.virtual.d4sh.widget.VirtualD4shHomePlayerLandscapeView$$ExternalSyntheticLambda1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$downloadShortVideoSuccess$1(view);
                    }
                });
                return;
            }
            if (mediaMsg.getState() == 0) {
                PetkitToast.showTopToast(getContext(), getContext().getResources().getString(R.string.Download_failure), 0, 1);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$downloadShortVideoSuccess$1(View view) {
        ((Activity) getContext()).startActivity(LocalAlbumActivity.getDeviceJumpIntent(getContext(), 1));
    }

    public int getVideoType() {
        return this.videoType;
    }

    public int getEatVideoType() {
        return this.eatVideoType;
    }

    public void deleteRecordSuccess() {
        if (this.eatVideoWindow.playNextItem()) {
            return;
        }
        this.eatVideoItem = null;
        showLandscapeLiveView();
        BasePetkitPlayerLandscapeViewClickListener basePetkitPlayerLandscapeViewClickListener = this.viewClickListener;
        if (basePetkitPlayerLandscapeViewClickListener != null) {
            basePetkitPlayerLandscapeViewClickListener.onBackLiveBtnClick();
        }
    }

    public void setServiceStatus(boolean z, Boolean bool) {
        PopupWindow.OnDismissListener onDismissListener = new PopupWindow.OnDismissListener() { // from class: com.petkit.android.activities.virtual.d4sh.widget.VirtualD4shHomePlayerLandscapeView.7
            @Override // android.widget.PopupWindow.OnDismissListener
            public void onDismiss() {
                VirtualD4shHomePlayerLandscapeView.this.playerLandscapeRecordView.setVisibility(0);
            }
        };
        if (z) {
            if (bool.booleanValue()) {
                initMoreActionSelectorWindow(this, onDismissListener, getContext().getResources().getString(R.string.Download_preview_video), null, null);
                return;
            } else {
                initMoreActionSelectorWindow(this, onDismissListener, getContext().getResources().getString(R.string.Download_preview_video), getContext().getResources().getString(R.string.Download_full_video), null);
                return;
            }
        }
        if (bool.booleanValue()) {
            initMoreActionSelectorWindow(this, onDismissListener, getContext().getResources().getString(R.string.Download_preview_video), getContext().getResources().getString(R.string.Delete), null);
        } else {
            initMoreActionSelectorWindow(this, onDismissListener, getContext().getResources().getString(R.string.Download_preview_video), getContext().getResources().getString(R.string.Download_full_video), getContext().getResources().getString(R.string.Delete));
        }
    }

    public void refreshFullVideoStatus() {
        BasePetkitPlayerLandscapeViewClickListener basePetkitPlayerLandscapeViewClickListener;
        if (this.eatVideoItem == null || this.eatVideoShortVideoUrl == null || (basePetkitPlayerLandscapeViewClickListener = this.viewClickListener) == null) {
            return;
        }
        basePetkitPlayerLandscapeViewClickListener.onVideoRecordClick(Integer.valueOf(this.eatVideoType), this.eatVideoShortVideoUrl, this.eatVideoFullVideoUrl, Float.valueOf(this.cloudVideoTimesSpeedType), D4shUtils.getExpireFromD4shDayItem(this.eatVideoItem), Long.valueOf(D4shUtils.getEatEndTimeFromD4shDayItem(this.eatVideoItem)), (int) D4shUtils.getEatStartTimeFromD4shDayItem(this.eatVideoItem), D4shUtils.getPreviewFromD4shDayItem(this.eatVideoItem), D4shUtils.getPreviewAesKeyFromD4shDayItem(this.eatVideoItem), this.eatVideoItem, this.mark);
    }

    public void setVideoSegmentListSize(int i) {
        this.playerLandscapeRecordView.setVideoSegmentListSize(i);
    }

    public void initTotalBarCount(int i, PetkitDeviceVideoRecordListViewGroup.VideoRecordListViewListener videoRecordListViewListener) {
        this.playerLandscapeRecordView.initTotalBarCount(i, videoRecordListViewListener);
    }

    public void setVideoListProgressState(int i, int i2, int i3) {
        this.playerLandscapeRecordView.setVideoListProgressState(i, i2, i3);
    }

    public void showTimeSpeedTip() {
        this.timeSpeedView.setVisibility(0);
        this.animationDrawable.start();
        this.playerLandscapeLiveView.setVisibility(8);
        this.playerLandscapeRecordView.setVisibility(8);
    }

    public void hideTimeSpeedTip() {
        this.timeSpeedView.setVisibility(4);
        this.animationDrawable.stop();
    }

    public void setServiceUnavailable(boolean z) {
        this.serviceUnavailable = z;
    }

    public void setCloudVideoTimesSpeedType(int i) {
        this.cloudVideoTimesSpeedType = i;
    }

    @Override // com.petkit.android.activities.petkitBleDevice.d4sh.listener.D4shHomePlayerLandscapeLiveViewListener
    public void onExitFullScreenBtnClick() {
        BasePetkitPlayerLandscapeViewClickListener basePetkitPlayerLandscapeViewClickListener;
        if (this.playerLandscapeLiveView.getRecording() != null && this.playerLandscapeLiveView.getRecording().booleanValue() && (basePetkitPlayerLandscapeViewClickListener = this.viewClickListener) != null) {
            basePetkitPlayerLandscapeViewClickListener.onCancelRecord();
        }
        BasePetkitPlayerLandscapeViewClickListener basePetkitPlayerLandscapeViewClickListener2 = this.viewClickListener;
        if (basePetkitPlayerLandscapeViewClickListener2 != null) {
            basePetkitPlayerLandscapeViewClickListener2.onExitFullScreenBtnClick();
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.d4sh.listener.D4shHomePlayerLandscapeLiveViewListener
    public void onVolumeBtnClick() {
        BasePetkitPlayerLandscapeViewClickListener basePetkitPlayerLandscapeViewClickListener = this.viewClickListener;
        if (basePetkitPlayerLandscapeViewClickListener != null) {
            basePetkitPlayerLandscapeViewClickListener.onLandscapeVolumeBtnClick();
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.d4sh.listener.D4shHomePlayerLandscapeLiveViewListener
    public void onVideoQualityBtnClick() {
        BasePetkitPlayerLandscapeViewClickListener basePetkitPlayerLandscapeViewClickListener = this.viewClickListener;
        if (basePetkitPlayerLandscapeViewClickListener != null) {
            basePetkitPlayerLandscapeViewClickListener.onVirtualMoreClick();
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.d4sh.listener.D4shHomePlayerLandscapeLiveViewListener
    public void onVideoRecordBtnClick() {
        BasePetkitPlayerLandscapeViewClickListener basePetkitPlayerLandscapeViewClickListener = this.viewClickListener;
        if (basePetkitPlayerLandscapeViewClickListener != null) {
            basePetkitPlayerLandscapeViewClickListener.onLandscapeRecordBtnClick();
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.d4sh.listener.D4shHomePlayerLandscapeLiveViewListener
    public void onScreenShotBtnClick() {
        BasePetkitPlayerLandscapeViewClickListener basePetkitPlayerLandscapeViewClickListener = this.viewClickListener;
        if (basePetkitPlayerLandscapeViewClickListener != null) {
            basePetkitPlayerLandscapeViewClickListener.onLandscapeScreenShotBtnClick();
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.d4sh.listener.D4shHomePlayerLandscapeLiveViewListener
    public void onEatVideoBtnClick() {
        if (this.playerLandscapeLiveView.getRecording() != null && this.playerLandscapeLiveView.getRecording().booleanValue()) {
            PetkitToast.showTopToast(getContext(), getContext().getResources().getString(R.string.Unable_switch_between_recording_videos), 0, 1);
            return;
        }
        BasePetkitPlayerLandscapeViewClickListener basePetkitPlayerLandscapeViewClickListener = this.viewClickListener;
        if (basePetkitPlayerLandscapeViewClickListener != null) {
            basePetkitPlayerLandscapeViewClickListener.onLandscapeEatVideoBtnClick();
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.d4sh.listener.D4shHomePlayerLandscapeLiveViewListener
    public void onExtraMealBtnClick() {
        BasePetkitPlayerLandscapeViewClickListener basePetkitPlayerLandscapeViewClickListener = this.viewClickListener;
        if (basePetkitPlayerLandscapeViewClickListener != null) {
            basePetkitPlayerLandscapeViewClickListener.onLandscapeExtraMealBtnClick();
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.d4sh.listener.D4shHomePlayerLandscapeLiveViewListener
    public void onMicrophoneBtnTouchDown() {
        BasePetkitPlayerLandscapeViewClickListener basePetkitPlayerLandscapeViewClickListener = this.viewClickListener;
        if (basePetkitPlayerLandscapeViewClickListener != null) {
            basePetkitPlayerLandscapeViewClickListener.onLandscapeIntercomBtnTouchDown();
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.d4sh.listener.D4shHomePlayerLandscapeLiveViewListener
    public void onMicrophoneBtnTouchUp() {
        BasePetkitPlayerLandscapeViewClickListener basePetkitPlayerLandscapeViewClickListener = this.viewClickListener;
        if (basePetkitPlayerLandscapeViewClickListener != null) {
            basePetkitPlayerLandscapeViewClickListener.onLandscapeIntercomBtnTouchUp();
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        BasePetkitPlayerLandscapeViewClickListener basePetkitPlayerLandscapeViewClickListener;
        BasePetkitPlayerLandscapeSelectorWindow basePetkitPlayerLandscapeSelectorWindow;
        D4shDayItem d4shDayItem;
        BasePetkitPlayerLandscapeSelectorWindow basePetkitPlayerLandscapeSelectorWindow2;
        BasePetkitPlayerLandscapeSelectorWindow basePetkitPlayerLandscapeSelectorWindow3;
        BasePetkitPlayerLandscapeViewClickListener basePetkitPlayerLandscapeViewClickListener2;
        D4shDayItem d4shDayItem2;
        BasePetkitPlayerLandscapeSelectorWindow basePetkitPlayerLandscapeSelectorWindow4;
        BasePetkitPlayerLandscapeSelectorWindow basePetkitPlayerLandscapeSelectorWindow5;
        BasePetkitPlayerLandscapeSelectorWindow basePetkitPlayerLandscapeSelectorWindow6;
        CloudVideo cloudVideo;
        BasePetkitPlayerLandscapeSelectorWindow basePetkitPlayerLandscapeSelectorWindow7;
        BasePetkitPlayerLandscapeSelectorWindow basePetkitPlayerLandscapeSelectorWindow8;
        if (view instanceof VirtualD4shHomePlayerLandscapeView) {
            int i = this.videoType;
            if (i == 1) {
                D4shHomePlayerLandscapeLiveView d4shHomePlayerLandscapeLiveView = this.playerLandscapeLiveView;
                d4shHomePlayerLandscapeLiveView.setVisibility(d4shHomePlayerLandscapeLiveView.getVisibility() != 8 ? 8 : 0);
                return;
            } else {
                if (i == 2) {
                    D4shHomePlayerLandscapeRecordView d4shHomePlayerLandscapeRecordView = this.playerLandscapeRecordView;
                    d4shHomePlayerLandscapeRecordView.setVisibility(d4shHomePlayerLandscapeRecordView.getVisibility() != 8 ? 8 : 0);
                    return;
                }
                return;
            }
        }
        int id = view.getId();
        if (id == R.id.selector_option1_text_view) {
            boolean z = view instanceof TextView;
            if (z && (basePetkitPlayerLandscapeSelectorWindow8 = this.videoQualitySelectorWindow) != null && basePetkitPlayerLandscapeSelectorWindow8.isShowing()) {
                if (DataHelper.getIntergerSF(getContext(), Constants.VIDEO_DEFINITION + this.deviceType + "_" + this.deviceId, 2) != 1) {
                    this.playerLandscapeLiveView.setQuality(getContext().getResources().getString(R.string.Standard_definition));
                    BasePetkitPlayerLandscapeViewClickListener basePetkitPlayerLandscapeViewClickListener3 = this.viewClickListener;
                    if (basePetkitPlayerLandscapeViewClickListener3 != null) {
                        basePetkitPlayerLandscapeViewClickListener3.onLandscapeQualityBtnClick(1);
                    }
                }
                this.videoQualitySelectorWindow.dismiss();
                return;
            }
            if (z && (basePetkitPlayerLandscapeSelectorWindow7 = this.timesSpeedSelectorWindow) != null && basePetkitPlayerLandscapeSelectorWindow7.isShowing()) {
                if (this.cloudVideoTimesSpeedType != 1) {
                    this.playerLandscapeRecordView.setTimesSpeedText(getContext().getResources().getString(R.string.Times_speed));
                    BasePetkitPlayerLandscapeViewClickListener basePetkitPlayerLandscapeViewClickListener4 = this.viewClickListener;
                    if (basePetkitPlayerLandscapeViewClickListener4 != null) {
                        basePetkitPlayerLandscapeViewClickListener4.onLandscapeTimeSpeedBtnClick(1);
                    }
                    this.cloudVideoTimesSpeedType = 1;
                }
                this.timesSpeedSelectorWindow.dismiss();
                return;
            }
            if (z && (basePetkitPlayerLandscapeSelectorWindow6 = this.moreActionSelectorWindow) != null && basePetkitPlayerLandscapeSelectorWindow6.isShowing()) {
                if (this.serviceUnavailable) {
                    PetkitToast.showTopToast(getContext(), getContext().getResources().getString(R.string.No_service_cannot_download), 0, 1);
                } else if (this.eatVideoFullVideoUrl != null && (cloudVideo = D4shUtils.getCloudVideo(this.eatVideoItem)) != null) {
                    VideoDownloadRecord videoDownloadRecordByUrlAndType = VideoDownloadManager.getVideoDownloadRecordByUrlAndType(cloudVideo.getMediaApi(), 1);
                    if (videoDownloadRecordByUrlAndType != null && !TextUtils.isEmpty(videoDownloadRecordByUrlAndType.getTranscodePath()) && videoDownloadRecordByUrlAndType.getState() == 12 && new File(videoDownloadRecordByUrlAndType.getTranscodePath()).exists()) {
                        VideoDownloadManager.removeTask(getContext(), cloudVideo.getMediaApi(), 1);
                    }
                    VideoDownloadManager.startDownloadTask(getContext(), cloudVideo, true);
                }
                this.moreActionSelectorWindow.dismiss();
                return;
            }
            return;
        }
        if (id == R.id.selector_option2_text_view) {
            boolean z2 = view instanceof TextView;
            if (z2 && (basePetkitPlayerLandscapeSelectorWindow5 = this.videoQualitySelectorWindow) != null && basePetkitPlayerLandscapeSelectorWindow5.isShowing()) {
                if (DataHelper.getIntergerSF(getContext(), Constants.VIDEO_DEFINITION + this.deviceType + "_" + this.deviceId, 2) != 2) {
                    this.playerLandscapeLiveView.setQuality(getContext().getResources().getString(R.string.Quality_hd));
                    BasePetkitPlayerLandscapeViewClickListener basePetkitPlayerLandscapeViewClickListener5 = this.viewClickListener;
                    if (basePetkitPlayerLandscapeViewClickListener5 != null) {
                        basePetkitPlayerLandscapeViewClickListener5.onLandscapeQualityBtnClick(2);
                    }
                }
                this.videoQualitySelectorWindow.dismiss();
                return;
            }
            if (z2 && (basePetkitPlayerLandscapeSelectorWindow4 = this.timesSpeedSelectorWindow) != null && basePetkitPlayerLandscapeSelectorWindow4.isShowing()) {
                if (this.cloudVideoTimesSpeedType != 2) {
                    this.playerLandscapeRecordView.setTimesSpeedText("2X");
                    BasePetkitPlayerLandscapeViewClickListener basePetkitPlayerLandscapeViewClickListener6 = this.viewClickListener;
                    if (basePetkitPlayerLandscapeViewClickListener6 != null) {
                        basePetkitPlayerLandscapeViewClickListener6.onLandscapeTimeSpeedBtnClick(2);
                    }
                    this.cloudVideoTimesSpeedType = 2;
                }
                this.timesSpeedSelectorWindow.dismiss();
                return;
            }
            if (z2 && (basePetkitPlayerLandscapeSelectorWindow3 = this.moreActionSelectorWindow) != null && basePetkitPlayerLandscapeSelectorWindow3.isShowing()) {
                if (this.serviceUnavailable) {
                    if (this.viewClickListener != null && (d4shDayItem2 = this.eatVideoItem) != null) {
                        if (d4shDayItem2.getType() == 2) {
                            D4shDailyFeeds.D4shDailyEat.ItemsBean itemsBean = (D4shDailyFeeds.D4shDailyEat.ItemsBean) this.eatVideoItem.getBean();
                            this.viewClickListener.onDeleteEatVideoBtnClick(null, itemsBean.getEndTime(), itemsBean.getStartTime(), -1);
                        } else if (this.eatVideoItem.getType() == 3) {
                            this.viewClickListener.onDeleteEatVideoBtnClick(((D4shDailyFeeds.PetBean.ItemsBean) this.eatVideoItem.getBean()).getEventId(), 0, 0, -1);
                        } else if (this.eatVideoItem.getType() == 4) {
                            this.viewClickListener.onDeleteEatVideoBtnClick(((D4shDailyFeeds.MoveBean.ItemsBeanX) this.eatVideoItem.getBean()).getEventId(), 0, 0, -1);
                        } else if (this.eatVideoItem.getType() == 1) {
                            this.viewClickListener.onDeleteEatVideoBtnClick(this.eatVideoItem.getType(), ((D4shDailyFeeds.D4shDailyFeed.ItemsBean) this.eatVideoItem.getBean()).getEventId(), 0L, r12.getTime(), -1);
                        }
                    }
                } else if (this.eatVideoShortVideoUrl != null && (basePetkitPlayerLandscapeViewClickListener2 = this.viewClickListener) != null && this.eatVideoItem != null) {
                    basePetkitPlayerLandscapeViewClickListener2.onDownloadFullVideo(Long.valueOf(this.startTime), Long.valueOf(this.mark), Long.valueOf(D4shUtils.getEatEndTimeFromD4shDayItem(this.eatVideoItem)), Long.valueOf(this.expire));
                }
                this.moreActionSelectorWindow.dismiss();
                return;
            }
            if (z2 && ((TextView) view).getText().equals(getContext().getResources().getString(R.string.Delete))) {
                BasePetkitPlayerLandscapeViewClickListener basePetkitPlayerLandscapeViewClickListener7 = this.viewClickListener;
                if (basePetkitPlayerLandscapeViewClickListener7 != null) {
                    basePetkitPlayerLandscapeViewClickListener7.onEventDelete();
                }
                this.moreActionSelectorWindow.dismiss();
                return;
            }
            return;
        }
        if (id == R.id.selector_option3_text_view) {
            boolean z3 = view instanceof TextView;
            if (z3 && (basePetkitPlayerLandscapeSelectorWindow2 = this.timesSpeedSelectorWindow) != null && basePetkitPlayerLandscapeSelectorWindow2.isShowing()) {
                if (this.cloudVideoTimesSpeedType != 4) {
                    this.playerLandscapeRecordView.setTimesSpeedText("4X");
                    BasePetkitPlayerLandscapeViewClickListener basePetkitPlayerLandscapeViewClickListener8 = this.viewClickListener;
                    if (basePetkitPlayerLandscapeViewClickListener8 != null) {
                        basePetkitPlayerLandscapeViewClickListener8.onLandscapeTimeSpeedBtnClick(4);
                    }
                    this.cloudVideoTimesSpeedType = 4;
                }
                this.timesSpeedSelectorWindow.dismiss();
                return;
            }
            if (z3 && (basePetkitPlayerLandscapeSelectorWindow = this.moreActionSelectorWindow) != null && basePetkitPlayerLandscapeSelectorWindow.isShowing()) {
                if (this.viewClickListener != null && (d4shDayItem = this.eatVideoItem) != null) {
                    if (d4shDayItem.getType() == 2) {
                        D4shDailyFeeds.D4shDailyEat.ItemsBean itemsBean2 = (D4shDailyFeeds.D4shDailyEat.ItemsBean) this.eatVideoItem.getBean();
                        this.viewClickListener.onDeleteEatVideoBtnClick(null, itemsBean2.getEndTime(), itemsBean2.getStartTime(), -1);
                    } else if (this.eatVideoItem.getType() == 3) {
                        this.viewClickListener.onDeleteEatVideoBtnClick(((D4shDailyFeeds.PetBean.ItemsBean) this.eatVideoItem.getBean()).getEventId(), 0, 0, -1);
                    } else if (this.eatVideoItem.getType() == 4) {
                        this.viewClickListener.onDeleteEatVideoBtnClick(((D4shDailyFeeds.MoveBean.ItemsBeanX) this.eatVideoItem.getBean()).getEventId(), 0, 0, -1);
                    } else if (this.eatVideoItem.getType() == 1) {
                        this.viewClickListener.onDeleteEatVideoBtnClick(this.eatVideoItem.getType(), ((D4shDailyFeeds.D4shDailyFeed.ItemsBean) this.eatVideoItem.getBean()).getEventId(), 0L, r12.getTime(), -1);
                    }
                }
                this.moreActionSelectorWindow.dismiss();
                return;
            }
            return;
        }
        if (id == R.id.d4sh_device_record_all_type_number_view) {
            this.eatVideoWindow.setFilterRecordType(0);
            return;
        }
        if (id == R.id.d4sh_device_record_pet_type_number_view) {
            this.eatVideoWindow.setFilterRecordType(1);
            return;
        }
        if (id == R.id.d4sh_device_record_eat_type_number_view) {
            this.eatVideoWindow.setFilterRecordType(2);
            return;
        }
        if (id == R.id.d4sh_device_record_activity_type_number_view) {
            this.eatVideoWindow.setFilterRecordType(3);
            return;
        }
        if (id == R.id.d4sh_device_record_work_type_number_view) {
            this.eatVideoWindow.setFilterRecordType(4);
        } else {
            if (id != R.id.d4sh_device_record_date_image_view || (basePetkitPlayerLandscapeViewClickListener = this.viewClickListener) == null) {
                return;
            }
            basePetkitPlayerLandscapeViewClickListener.onLandscapeDatePickerBtnClick();
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.listener.BasePetkitPlayerLandscapeRecordViewListener
    public void onRecordViewExitFullScreenBtnClick() {
        BasePetkitPlayerLandscapeViewClickListener basePetkitPlayerLandscapeViewClickListener;
        if (this.playerLandscapeLiveView.getRecording() != null && this.playerLandscapeLiveView.getRecording().booleanValue() && (basePetkitPlayerLandscapeViewClickListener = this.viewClickListener) != null) {
            basePetkitPlayerLandscapeViewClickListener.onCancelRecord();
        }
        this.eatVideoItem = null;
        showLandscapeLiveView();
        BasePetkitPlayerLandscapeViewClickListener basePetkitPlayerLandscapeViewClickListener2 = this.viewClickListener;
        if (basePetkitPlayerLandscapeViewClickListener2 != null) {
            basePetkitPlayerLandscapeViewClickListener2.onBackLiveBtnClick();
        }
        BasePetkitPlayerLandscapeViewClickListener basePetkitPlayerLandscapeViewClickListener3 = this.viewClickListener;
        if (basePetkitPlayerLandscapeViewClickListener3 != null) {
            basePetkitPlayerLandscapeViewClickListener3.onExitFullScreenBtnClick();
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.listener.BasePetkitPlayerLandscapeRecordViewListener
    public void onRecordViewVolumeBtnClick() {
        BasePetkitPlayerLandscapeViewClickListener basePetkitPlayerLandscapeViewClickListener = this.viewClickListener;
        if (basePetkitPlayerLandscapeViewClickListener != null) {
            basePetkitPlayerLandscapeViewClickListener.onLandscapeVolumeBtnClick();
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.listener.BasePetkitPlayerLandscapeRecordViewListener
    public void onRecordViewTimesSpeedBtnClick() {
        BasePetkitPlayerLandscapeViewClickListener basePetkitPlayerLandscapeViewClickListener = this.viewClickListener;
        if (basePetkitPlayerLandscapeViewClickListener != null) {
            basePetkitPlayerLandscapeViewClickListener.onVirtualMoreClick();
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.listener.BasePetkitPlayerLandscapeRecordViewListener
    public void onRecordViewVideoRecordBtnClick() {
        BasePetkitPlayerLandscapeViewClickListener basePetkitPlayerLandscapeViewClickListener = this.viewClickListener;
        if (basePetkitPlayerLandscapeViewClickListener != null) {
            basePetkitPlayerLandscapeViewClickListener.onLandscapeRecordBtnClick();
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.listener.BasePetkitPlayerLandscapeRecordViewListener
    public void onRecordViewScreenShotBtnClick() {
        BasePetkitPlayerLandscapeViewClickListener basePetkitPlayerLandscapeViewClickListener = this.viewClickListener;
        if (basePetkitPlayerLandscapeViewClickListener != null) {
            basePetkitPlayerLandscapeViewClickListener.onLandscapeScreenShotBtnClick();
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.listener.BasePetkitPlayerLandscapeRecordViewListener
    public void onRecordViewEatVideoBtnClick() {
        if (this.playerLandscapeLiveView.getRecording() != null && this.playerLandscapeLiveView.getRecording().booleanValue()) {
            PetkitToast.showTopToast(getContext(), getContext().getResources().getString(R.string.Unable_switch_between_recording_videos), 0, 1);
            return;
        }
        BasePetkitPlayerLandscapeViewClickListener basePetkitPlayerLandscapeViewClickListener = this.viewClickListener;
        if (basePetkitPlayerLandscapeViewClickListener != null) {
            basePetkitPlayerLandscapeViewClickListener.onLandscapeEatVideoBtnClick();
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.listener.BasePetkitPlayerLandscapeRecordViewListener
    public void onRecordViewBackLiveBtnClick() {
        if (this.playerLandscapeLiveView.getRecording() != null && this.playerLandscapeLiveView.getRecording().booleanValue()) {
            PetkitToast.showTopToast(getContext(), getContext().getResources().getString(R.string.Unable_switch_between_recording_videos), 0, 1);
            return;
        }
        this.eatVideoItem = null;
        showLandscapeLiveView();
        BasePetkitPlayerLandscapeViewClickListener basePetkitPlayerLandscapeViewClickListener = this.viewClickListener;
        if (basePetkitPlayerLandscapeViewClickListener != null) {
            basePetkitPlayerLandscapeViewClickListener.onBackLiveBtnClick();
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.listener.BasePetkitPlayerLandscapeRecordViewListener
    public void onRecordViewMoreActionBtnClick() {
        this.playerLandscapeRecordView.setVisibility(8);
        BasePetkitPlayerLandscapeViewClickListener basePetkitPlayerLandscapeViewClickListener = this.viewClickListener;
        if (basePetkitPlayerLandscapeViewClickListener != null) {
            basePetkitPlayerLandscapeViewClickListener.onRecordFeedbackClick();
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.listener.BasePetkitPlayerLandscapeRecordViewListener
    public void onRecordViewPreviewVideoBtnClick() {
        this.eatVideoType = 1;
        BasePetkitPlayerLandscapeViewClickListener basePetkitPlayerLandscapeViewClickListener = this.viewClickListener;
        if (basePetkitPlayerLandscapeViewClickListener != null) {
            basePetkitPlayerLandscapeViewClickListener.onVideoRecordClick(1, this.eatVideoShortVideoUrl, this.eatVideoFullVideoUrl, Float.valueOf(this.cloudVideoTimesSpeedType), D4shUtils.getExpireFromD4shDayItem(this.eatVideoItem), Long.valueOf(D4shUtils.getEatEndTimeFromD4shDayItem(this.eatVideoItem)), (int) D4shUtils.getEatStartTimeFromD4shDayItem(this.eatVideoItem), D4shUtils.getPreviewFromD4shDayItem(this.eatVideoItem), D4shUtils.getPreviewAesKeyFromD4shDayItem(this.eatVideoItem), this.eatVideoItem, this.mark);
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.listener.BasePetkitPlayerLandscapeRecordViewListener
    public void onRecordViewFullVideoBtnClick() {
        this.eatVideoType = 2;
        BasePetkitPlayerLandscapeViewClickListener basePetkitPlayerLandscapeViewClickListener = this.viewClickListener;
        if (basePetkitPlayerLandscapeViewClickListener != null) {
            basePetkitPlayerLandscapeViewClickListener.onVideoRecordClick(2, this.eatVideoShortVideoUrl, this.eatVideoFullVideoUrl, Float.valueOf(this.cloudVideoTimesSpeedType), D4shUtils.getExpireFromD4shDayItem(this.eatVideoItem), Long.valueOf(D4shUtils.getEatEndTimeFromD4shDayItem(this.eatVideoItem)), (int) D4shUtils.getEatStartTimeFromD4shDayItem(this.eatVideoItem), D4shUtils.getPreviewFromD4shDayItem(this.eatVideoItem), D4shUtils.getPreviewAesKeyFromD4shDayItem(this.eatVideoItem), this.eatVideoItem, this.mark);
        }
        this.playerLandscapeRecordView.showSingleProgress();
    }

    @Override // com.petkit.android.activities.petkitBleDevice.listener.BasePetkitPlayerLandscapeRecordViewListener
    public void onRecordViewSeekbarProgressChanged(int i) {
        BasePetkitPlayerLandscapeViewClickListener basePetkitPlayerLandscapeViewClickListener = this.viewClickListener;
        if (basePetkitPlayerLandscapeViewClickListener != null) {
            basePetkitPlayerLandscapeViewClickListener.onSeekbarProgressChanged(i);
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.listener.BasePetkitPlayerLandscapeRecordViewListener
    public void onRecordViewPlayBtnClick() {
        BasePetkitPlayerLandscapeViewClickListener basePetkitPlayerLandscapeViewClickListener;
        Long expireFromD4shDayItem;
        if (this.playerLandscapeLiveView.getRecording() != null && this.playerLandscapeLiveView.getRecording().booleanValue()) {
            PetkitToast.showTopToast(getContext(), getContext().getResources().getString(R.string.Unable_switch_between_recording_videos), 0, 1);
            return;
        }
        D4shDayItem d4shDayItem = this.eatVideoItem;
        if ((d4shDayItem == null || (expireFromD4shDayItem = D4shUtils.getExpireFromD4shDayItem(d4shDayItem)) == null || System.currentTimeMillis() / 1000 <= expireFromD4shDayItem.longValue()) && (basePetkitPlayerLandscapeViewClickListener = this.viewClickListener) != null) {
            basePetkitPlayerLandscapeViewClickListener.onLandscapePlayBtnClick();
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.listener.BasePetkitPlayerLandscapeRecordViewListener
    public void onSeekbarStartMoving() {
        BasePetkitPlayerLandscapeViewClickListener basePetkitPlayerLandscapeViewClickListener = this.viewClickListener;
        if (basePetkitPlayerLandscapeViewClickListener != null) {
            basePetkitPlayerLandscapeViewClickListener.onSeekbarStartMoving();
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.listener.BasePetkitPlayerLandscapeRecordViewListener
    public void onRecordFeedbackClick() {
        BasePetkitPlayerLandscapeViewClickListener basePetkitPlayerLandscapeViewClickListener = this.viewClickListener;
        if (basePetkitPlayerLandscapeViewClickListener != null) {
            basePetkitPlayerLandscapeViewClickListener.onRecordFeedbackClick();
        }
    }

    @Override // com.petkit.android.activities.petkitBleDevice.listener.BasePetkitLandscapeEatVideoViewListener
    public void onEatVideoClick(D4shDayItem d4shDayItem, String str, Long l, Integer num, String str2, String str3, long j) {
        VirtualBasePetkitPlayerLandscapeEatVideoWindow virtualBasePetkitPlayerLandscapeEatVideoWindow = this.eatVideoWindow;
        if (virtualBasePetkitPlayerLandscapeEatVideoWindow != null && virtualBasePetkitPlayerLandscapeEatVideoWindow.isShowing()) {
            this.eatVideoWindow.dismiss();
        }
        showLandscapeRecordView();
        this.playerLandscapeRecordView.setRecordInfo(num, str2, str3);
        this.playerLandscapeRecordView.setCompareTosvVisible(Boolean.FALSE);
        this.mark = j;
        this.startTime = l.longValue();
        this.eatVideoItem = d4shDayItem;
        this.eatVideoShortVideoUrl = str;
        this.eatVideoFullVideoUrl = D4shUtils.getVirtualMediaApiFromD4shDayItem(d4shDayItem);
        this.expire = D4shUtils.getExpireFromD4shDayItem(d4shDayItem).longValue();
        int i = this.eatVideoType;
        if (i == 1) {
            BasePetkitPlayerLandscapeViewClickListener basePetkitPlayerLandscapeViewClickListener = this.viewClickListener;
            if (basePetkitPlayerLandscapeViewClickListener != null) {
                basePetkitPlayerLandscapeViewClickListener.onVideoRecordClick(Integer.valueOf(i), str, this.eatVideoFullVideoUrl, Float.valueOf(this.cloudVideoTimesSpeedType), D4shUtils.getExpireFromD4shDayItem(d4shDayItem), Long.valueOf(D4shUtils.getEatEndTimeFromD4shDayItem(this.eatVideoItem)), (int) D4shUtils.getEatStartTimeFromD4shDayItem(this.eatVideoItem), D4shUtils.getPreviewFromD4shDayItem(this.eatVideoItem), D4shUtils.getPreviewAesKeyFromD4shDayItem(this.eatVideoItem), this.eatVideoItem, j);
                return;
            }
            return;
        }
        BasePetkitPlayerLandscapeViewClickListener basePetkitPlayerLandscapeViewClickListener2 = this.viewClickListener;
        if (basePetkitPlayerLandscapeViewClickListener2 != null) {
            basePetkitPlayerLandscapeViewClickListener2.onVideoRecordClick(Integer.valueOf(i), this.eatVideoShortVideoUrl, this.eatVideoFullVideoUrl, Float.valueOf(this.cloudVideoTimesSpeedType), D4shUtils.getExpireFromD4shDayItem(d4shDayItem), Long.valueOf(D4shUtils.getEatEndTimeFromD4shDayItem(this.eatVideoItem)), (int) D4shUtils.getEatStartTimeFromD4shDayItem(this.eatVideoItem), D4shUtils.getPreviewFromD4shDayItem(this.eatVideoItem), D4shUtils.getPreviewAesKeyFromD4shDayItem(this.eatVideoItem), this.eatVideoItem, j);
        }
    }
}

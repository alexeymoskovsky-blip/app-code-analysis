package com.petkit.android.activities.home.adapter.newholder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.ImageUtils;
import com.jess.arms.widget.imageloader.ImageCacheSimple;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.common.utils.DeviceCenterUtils;
import com.petkit.android.activities.home.NewCardOnClickListener;
import com.petkit.android.activities.home.adapter.model.HomeDeviceData;
import com.petkit.android.activities.home.utils.BlurUtils;
import com.petkit.android.activities.petkitBleDevice.d4sh.mode.D4shRecord;
import com.petkit.android.activities.petkitBleDevice.d4sh.utils.D4shUtils;
import com.petkit.android.activities.petkitBleDevice.t4.widget.RoundImageview;
import com.petkit.android.activities.petkitBleDevice.utils.TimeUtils;
import com.petkit.android.utils.CommonUtil;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.DateUtil;
import com.petkit.android.utils.PetkitLog;
import com.petkit.android.utils.ThreadPoolManager;
import com.petkit.oversea.R;
import java.io.File;

/* JADX INFO: loaded from: classes4.dex */
public class D4shHomeLargeDeviceViewHolder extends BaseHomeLargeDeviceWithControlViewHolder {
    public final Context context;
    public D4shRecord d4shRecord;
    public final ImageView ivNoEvent;
    public ImageView ivOffline;
    public ImageView ivPlay;
    public ImageView ivSetting;
    public final ImageView ivShadow;
    public RoundImageview ivVideo;
    public final LinearLayout llNoEvent;
    public HomeDeviceData oldData;
    public RelativeLayout rlControlView;
    public RelativeLayout rlImage;
    public final TextView tvDeviceTag;
    public final TextView tvEvent;
    public final TextView tvNoEvent;
    public final TextView tvPlay;
    public final TextView tvShare;
    public final TextView tvShareLine;
    public final TextView tvTip;

    public D4shHomeLargeDeviceViewHolder(@NonNull View view) {
        super(view);
        this.context = view.getContext();
        this.tvDeviceTag = (TextView) view.findViewById(R.id.tv_device_tag);
        this.rlControlView = (RelativeLayout) view.findViewById(R.id.rl_control_view);
        this.ivVideo = (RoundImageview) view.findViewById(R.id.iv_video);
        this.rlImage = (RelativeLayout) view.findViewById(R.id.rl_img);
        this.ivPlay = (ImageView) view.findViewById(R.id.iv_play);
        this.tvPlay = (TextView) view.findViewById(R.id.tv_play);
        this.ivNoEvent = (ImageView) view.findViewById(R.id.iv_no_event);
        this.llNoEvent = (LinearLayout) view.findViewById(R.id.ll_no_event);
        this.tvNoEvent = (TextView) view.findViewById(R.id.tv_no_event);
        this.ivOffline = (ImageView) view.findViewById(R.id.iv_offline);
        this.ivSetting = (ImageView) view.findViewById(R.id.iv_setting);
        this.tvShare = (TextView) view.findViewById(R.id.tv_share);
        this.tvShareLine = (TextView) view.findViewById(R.id.tv_share_line);
        this.tvEvent = (TextView) view.findViewById(R.id.tv_event);
        this.tvTip = (TextView) view.findViewById(R.id.tv_tip);
        this.ivShadow = (ImageView) view.findViewById(R.id.iv_shadow);
        this.ivVideo.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.petkit.android.activities.home.adapter.newholder.D4shHomeLargeDeviceViewHolder.1
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                ViewGroup.LayoutParams layoutParams = D4shHomeLargeDeviceViewHolder.this.rlImage.getLayoutParams();
                layoutParams.height = ArmsUtils.dip2px(D4shHomeLargeDeviceViewHolder.this.context, 250.0f);
                D4shHomeLargeDeviceViewHolder.this.rlImage.setLayoutParams(layoutParams);
                ViewGroup.LayoutParams layoutParams2 = D4shHomeLargeDeviceViewHolder.this.ivVideo.getLayoutParams();
                layoutParams2.height = ArmsUtils.dip2px(D4shHomeLargeDeviceViewHolder.this.context, 250.0f);
                D4shHomeLargeDeviceViewHolder.this.ivVideo.setLayoutParams(layoutParams2);
                D4shHomeLargeDeviceViewHolder.this.ivVideo.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    @Override // com.petkit.android.activities.home.adapter.newholder.BaseHomeLargeDeviceViewHolder
    public void updateData(final HomeDeviceData homeDeviceData, int i, NewCardOnClickListener newCardOnClickListener) {
        String strValueOf;
        String strValueOf2;
        D4shRecord d4shRecord;
        int i2;
        int controlSettings = homeDeviceData.getData().getControlSettings();
        if (controlSettings == 1) {
            this.rlControl.setVisibility(0);
            this.rlControl.setBackgroundResource(R.drawable.solid_card_control_blue_corners_16);
            if (homeDeviceData.getData().getState() != 2 && homeDeviceData.getData().getState() != 4 && homeDeviceData.getData().getState() != 5) {
                if (homeDeviceData.getData().getStatus().getFeeding() == 1 || homeDeviceData.getData().getStatus().getFeeding() == 2 || homeDeviceData.getData().getStatus().getFeeding() == 3) {
                    this.ivControl.setBackgroundResource(R.drawable.control_feed_stop_white_icon);
                    this.rlControl.setBackgroundResource(R.drawable.solid_card_control_blue_corners_16);
                } else {
                    this.ivControl.setBackgroundResource(R.drawable.control_d4sh_feed_icon);
                }
            } else {
                this.ivControl.setBackgroundResource(R.drawable.control_d4sh_feed_gray_icon);
            }
        } else if (controlSettings == 2) {
            this.rlControl.setVisibility(0);
            this.rlControl.setBackgroundResource(R.drawable.solid_card_control_blue_corners_16);
            if (homeDeviceData.getData().getState() != 2 && homeDeviceData.getData().getState() != 4 && homeDeviceData.getData().getState() != 5) {
                this.ivControl.setBackgroundResource(R.drawable.control_d4sh_plan_icon);
            } else {
                this.ivControl.setBackgroundResource(R.drawable.control_d4sh_plan_gray_icon);
            }
            this.tvControl.setText(this.context.getString(R.string.Feeder_plan));
        } else if (controlSettings == 3) {
            this.rlControl.setVisibility(0);
            this.rlControl.setBackgroundResource(R.drawable.solid_card_control_blue_corners_16);
            if (homeDeviceData.getData().getStatus() != null && homeDeviceData.getData().getStatus().getCameraStatus() != null && homeDeviceData.getData().getStatus().getCameraStatus().intValue() == 1) {
                this.ivControl.setBackgroundResource(R.drawable.camera_on_icon);
            } else {
                this.ivControl.setBackgroundResource(R.drawable.camera_off_icon);
            }
        } else {
            this.rlControl.setVisibility(0);
            this.rlControl.setBackgroundResource(R.drawable.circle_t6_card_black);
            this.ivControl.setBackgroundResource(R.drawable.edit_control_icon);
        }
        if (homeDeviceData.getData().getState() == 2) {
            this.rlControlView.setAlpha(0.5f);
        } else {
            this.rlControlView.setAlpha(1.0f);
        }
        this.tvEvent.setText("");
        this.tvEvent.setVisibility(8);
        this.ivShadow.setVisibility(0);
        this.llNoEvent.setVisibility(8);
        this.ivVideo.setAllRound();
        this.tvEvent.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
        this.tvEvent.setCompoundDrawablePadding(ArmsUtils.dip2px(this.context, 3.0f));
        if (homeDeviceData.getData().getDailyFeed() == null || homeDeviceData.getData().getDailyFeed().getEatCount() == 0) {
            strValueOf = "0";
        } else {
            strValueOf = String.valueOf(homeDeviceData.getData().getDailyFeed().getEatCount());
        }
        this.tvConsumableContentOne.setText(strValueOf);
        if (homeDeviceData.getData().getDailyFeed() == null) {
            strValueOf2 = String.valueOf(0);
        } else {
            strValueOf2 = String.valueOf(homeDeviceData.getData().getDailyFeed().getRealAmount1() + homeDeviceData.getData().getDailyFeed().getRealAmount2());
        }
        this.tvConsumableContentTwo.setText(strValueOf2);
        this.tvDeviceTag.setVisibility(4);
        int state = homeDeviceData.getData().getState();
        if (homeDeviceData.getData().getServiceStatus() == 0) {
            this.tvDeviceTag.setVisibility(4);
        } else if (homeDeviceData.getData().getServiceStatus() == 1) {
            this.tvDeviceTag.setVisibility(0);
            this.tvDeviceTag.setTextColor(CommonUtils.getColorById(R.color.receive_for_free));
            this.tvDeviceTag.setBackgroundResource(R.drawable.gradient_gold_corners_petkitcare_tag_bg);
        } else if (homeDeviceData.getData().getServiceStatus() == 2) {
            this.tvDeviceTag.setTextColor(CommonUtils.getColorById(R.color.home_feeder_bg_gray));
            this.tvDeviceTag.setBackgroundResource(R.drawable.gradient_gray_corners_petkitcare_tag_bg);
            this.tvDeviceTag.setVisibility(0);
        }
        checkDeviceNameSize(homeDeviceData);
        this.tvDeviceState.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
        PetkitLog.d(getClass().getSimpleName(), "preview:" + homeDeviceData.getData().getPreview());
        this.ivSetting.setVisibility(8);
        this.tvTip.setVisibility(8);
        this.d4shRecord = D4shUtils.getD4shRecordByDeviceId(homeDeviceData.getId(), 0);
        if (homeDeviceData.getData().getStatus().getCameraStatus() == null) {
            if (2 == state || homeDeviceData.getData().getStatus().getBatteryStatus() != 0) {
                this.ivVideo.setImageResource(R.drawable.transparent);
                this.ivShadow.setVisibility(8);
                this.ivPlay.setVisibility(8);
                this.ivOffline.setVisibility(8);
                this.ivNoEvent.setImageResource(R.drawable.offline_icon);
                D4shRecord d4shRecordByDeviceId = D4shUtils.getD4shRecordByDeviceId(homeDeviceData.getData().getId(), 0);
                if (d4shRecordByDeviceId == null || d4shRecordByDeviceId.getState().getOfflineTime() <= 0) {
                    this.tvNoEvent.setText(this.context.getString(R.string.Feeder_is_offline));
                } else {
                    this.tvNoEvent.setText(this.context.getString(R.string.Offline_time, DateUtil.getDateFormatShortTimeShortString(DateUtil.long2str(d4shRecordByDeviceId.getState().getOfflineTime(), "yyyy-MM-dd'T'HH:mm:ss.SSSZ"))));
                }
                this.llNoEvent.setVisibility(0);
            } else if (homeDeviceData.getData().getSettings().getCamera() == 0) {
                this.ivVideo.setImageResource(R.drawable.solid_dark_black_8);
                this.ivPlay.setVisibility(8);
                this.ivOffline.setVisibility(8);
                this.tvTip.setVisibility(0);
                this.llNoEvent.setVisibility(8);
                this.tvTip.setText(this.context.getString(R.string.Camera_is_off));
            } else if (homeDeviceData.getData().getSettings().getPreLive() == 0) {
                this.ivPlay.setVisibility(8);
                this.ivOffline.setVisibility(8);
                if (!TextUtils.isEmpty(homeDeviceData.getData().getPreview())) {
                    new ImageCacheSimple(CommonUtils.getAppContext()).getImageCachePath(CommonUtil.httpToHttps(homeDeviceData.getData().getPreview()), new ImageCacheSimple.IGetImageCacheListener() { // from class: com.petkit.android.activities.home.adapter.newholder.D4shHomeLargeDeviceViewHolder$$ExternalSyntheticLambda0
                        @Override // com.jess.arms.widget.imageloader.ImageCacheSimple.IGetImageCacheListener
                        public final void onCachePath(String str) {
                            this.f$0.lambda$updateData$0(homeDeviceData, str);
                        }
                    });
                } else {
                    this.llNoEvent.setVisibility(0);
                    this.ivNoEvent.setImageResource(R.drawable.home_card_no_event_icon);
                    if (homeDeviceData.getData().getEvent() == null) {
                        this.tvNoEvent.setText(R.string.H3_no_event);
                    } else {
                        this.tvNoEvent.setText(R.string.No_video);
                    }
                    this.ivVideo.setImageResource(R.drawable.transparent);
                    this.ivShadow.setVisibility(8);
                }
            } else if (TextUtils.isEmpty(homeDeviceData.getData().getPreview())) {
                this.llNoEvent.setVisibility(0);
                this.ivNoEvent.setImageResource(R.drawable.home_card_no_event_icon);
                if (homeDeviceData.getData().getEvent() == null) {
                    this.tvNoEvent.setText(R.string.H3_no_event);
                } else {
                    this.tvNoEvent.setText(R.string.No_video);
                }
                this.ivVideo.setImageResource(R.drawable.transparent);
                this.ivShadow.setVisibility(8);
                this.ivPlay.setVisibility(8);
                this.ivOffline.setVisibility(8);
            } else if (!TextUtils.isEmpty(homeDeviceData.getData().getPreview())) {
                this.ivPlay.setVisibility(8);
                this.ivOffline.setVisibility(8);
                this.llNoEvent.setVisibility(8);
                ((BaseApplication) CommonUtils.getApplication()).getAppComponent().imageLoader().loadImage(this.context, GlideImageConfig.builder().url(CommonUtil.httpToHttps(homeDeviceData.getData().getPreview())).secretKey(homeDeviceData.getData().getAesKey()).errorPic(R.drawable.solid_dark_black_8).imageView(this.ivVideo).build());
            } else {
                this.ivVideo.setImageResource(R.drawable.solid_dark_black_8);
                this.ivPlay.setVisibility(8);
                this.ivOffline.setVisibility(0);
            }
        } else if (2 == state || homeDeviceData.getData().getStatus().getBatteryStatus() != 0) {
            this.ivVideo.setImageResource(R.drawable.transparent);
            this.ivShadow.setVisibility(8);
            this.ivPlay.setVisibility(8);
            this.ivOffline.setVisibility(8);
            this.ivNoEvent.setImageResource(R.drawable.offline_icon);
            D4shRecord d4shRecordByDeviceId2 = D4shUtils.getD4shRecordByDeviceId(homeDeviceData.getData().getId(), 0);
            if (d4shRecordByDeviceId2 != null) {
                this.tvNoEvent.setText(this.context.getString(R.string.Offline_time, DateUtil.getDateFormatShortTimeShortString(DateUtil.long2str(d4shRecordByDeviceId2.getState().getOfflineTime(), "yyyy-MM-dd'T'HH:mm:ss.SSSZ"))));
            }
            this.llNoEvent.setVisibility(0);
        } else if (homeDeviceData.getData().getSettings().getCamera() == 0) {
            this.ivVideo.setImageResource(R.drawable.solid_dark_black_8);
            this.ivPlay.setVisibility(8);
            this.ivOffline.setVisibility(8);
            this.llNoEvent.setVisibility(0);
            this.ivNoEvent.setImageResource(R.drawable.home_card_camera_off_icon);
            this.tvNoEvent.setText(this.context.getString(R.string.Camera_is_off));
        } else if (homeDeviceData.getData().getStatus().getCameraStatus().intValue() == 0 && (d4shRecord = this.d4shRecord) != null && d4shRecord.getSettings().getCameraConfig() != 1) {
            this.ivVideo.setImageResource(R.drawable.solid_dark_black_8);
            this.ivPlay.setVisibility(8);
            this.ivOffline.setVisibility(8);
            this.tvTip.setVisibility(8);
            this.llNoEvent.setVisibility(0);
            this.ivNoEvent.setImageResource(R.drawable.home_card_camera_off_icon);
            this.tvNoEvent.setText(this.context.getString(R.string.Camera_off));
        } else if (homeDeviceData.getData().getSettings().getPreLive() == 0) {
            this.ivPlay.setVisibility(8);
            this.ivOffline.setVisibility(8);
            this.llNoEvent.setVisibility(8);
            if (!TextUtils.isEmpty(homeDeviceData.getData().getPreview())) {
                new ImageCacheSimple(CommonUtils.getAppContext()).getImageCachePath(CommonUtil.httpToHttps(homeDeviceData.getData().getPreview()), new ImageCacheSimple.IGetImageCacheListener() { // from class: com.petkit.android.activities.home.adapter.newholder.D4shHomeLargeDeviceViewHolder$$ExternalSyntheticLambda1
                    @Override // com.jess.arms.widget.imageloader.ImageCacheSimple.IGetImageCacheListener
                    public final void onCachePath(String str) {
                        this.f$0.lambda$updateData$1(homeDeviceData, str);
                    }
                });
            } else {
                this.ivVideo.setImageResource(R.drawable.solid_dark_black_8);
            }
        } else if (TextUtils.isEmpty(homeDeviceData.getData().getPreview())) {
            this.llNoEvent.setVisibility(0);
            if (homeDeviceData.getData().getEvent() == null) {
                this.tvNoEvent.setText(R.string.H3_no_event);
            } else {
                this.tvNoEvent.setText(R.string.No_video);
            }
            this.ivNoEvent.setImageResource(R.drawable.home_card_no_event_icon);
            this.ivVideo.setImageResource(R.drawable.transparent);
            this.ivShadow.setVisibility(8);
            this.ivPlay.setVisibility(8);
            this.ivOffline.setVisibility(8);
        } else if (!TextUtils.isEmpty(homeDeviceData.getData().getPreview())) {
            this.ivPlay.setVisibility(8);
            this.ivOffline.setVisibility(8);
            this.llNoEvent.setVisibility(8);
            ((BaseApplication) CommonUtils.getApplication()).getAppComponent().imageLoader().loadImage(this.context, GlideImageConfig.builder().url(CommonUtil.httpToHttps(homeDeviceData.getData().getPreview())).secretKey(homeDeviceData.getData().getAesKey()).errorPic(R.drawable.solid_dark_black_8).imageView(this.ivVideo).build());
        } else {
            this.ivVideo.setImageResource(R.drawable.solid_dark_black_8);
            this.ivPlay.setVisibility(8);
            this.llNoEvent.setVisibility(8);
            this.ivOffline.setVisibility(0);
        }
        if (2 == state) {
            setUnconnectDeviceState();
            this.tvDeviceState.setText(this.context.getResources().getString(R.string.Offline));
        } else {
            D4shRecord d4shRecordByDeviceId3 = D4shUtils.getD4shRecordByDeviceId(homeDeviceData.getId(), 0);
            if (homeDeviceData.getData().getEvent() != null) {
                if (homeDeviceData.getData().getEvent().getEventType() == 7) {
                    StringBuilder sb = new StringBuilder();
                    i2 = state;
                    sb.append(TimeUtils.getInstance().stampToDate(this.context, homeDeviceData.getData().getEvent().getTimestamp(), d4shRecordByDeviceId3 == null ? null : d4shRecordByDeviceId3.getActualTimeZone()));
                    sb.append(" ");
                    sb.append(this.context.getString(R.string.Movement_detected));
                    this.tvEvent.setText(sb.toString());
                    this.tvEvent.setCompoundDrawablesWithIntrinsicBounds(this.context.getResources().getDrawable(R.drawable.card_pet_appear_icon), (Drawable) null, (Drawable) null, (Drawable) null);
                    this.tvEvent.setVisibility(0);
                } else {
                    i2 = state;
                    if (homeDeviceData.getData().getEvent().getEventType() == 8) {
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(TimeUtils.getInstance().stampToDate(this.context, homeDeviceData.getData().getEvent().getTimestamp(), d4shRecordByDeviceId3 == null ? null : d4shRecordByDeviceId3.getActualTimeZone()));
                        sb2.append(" ");
                        sb2.append(this.context.getString(R.string.Cat_appeared));
                        this.tvEvent.setText(sb2.toString());
                        this.tvEvent.setCompoundDrawablesWithIntrinsicBounds(this.context.getResources().getDrawable(R.drawable.card_pet_appear_icon), (Drawable) null, (Drawable) null, (Drawable) null);
                        this.tvEvent.setVisibility(0);
                    } else if (homeDeviceData.getData().getEvent().getEventType() == 5 || homeDeviceData.getData().getEvent().getEventType() == 6) {
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append(TimeUtils.getInstance().stampToDate(this.context, homeDeviceData.getData().getEvent().getTimestamp(), d4shRecordByDeviceId3 == null ? null : d4shRecordByDeviceId3.getActualTimeZone()));
                        sb3.append(" ");
                        sb3.append(this.context.getString(R.string.D4sh_device_record_pet_ate));
                        this.tvEvent.setText(sb3.toString());
                        this.tvEvent.setCompoundDrawablesWithIntrinsicBounds(this.context.getResources().getDrawable(R.drawable.card_pet_eat_icon), (Drawable) null, (Drawable) null, (Drawable) null);
                        this.tvEvent.setVisibility(0);
                    } else {
                        this.tvEvent.setText("");
                        this.tvEvent.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
                        this.tvEvent.setVisibility(8);
                    }
                }
                state = i2;
            } else {
                this.tvEvent.setText("");
                this.tvEvent.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
                this.tvEvent.setVisibility(8);
            }
            if (state == 5) {
                setWarnDeviceState();
                this.tvDeviceState.setText(R.string.Cozy_error);
            } else if (state == 4) {
                setUpdatingDeviceState();
                this.tvDeviceState.setText(R.string.Card_device_up);
            } else if (homeDeviceData.getData().getStatus() != null) {
                if (homeDeviceData.getData().getStatus().getFeeding() != 1 && homeDeviceData.getData().getStatus().getFeeding() != 2 && homeDeviceData.getData().getStatus().getFeeding() != 3) {
                    homeDeviceData.getData().getDesc();
                }
                this.tvPlay.setVisibility(8);
                this.ivControl.setVisibility(0);
                if (homeDeviceData.getData().getStatus().getBatteryStatus() == 2) {
                    this.tvDeviceState.setText(R.string.Battery_lack);
                    setWarnDeviceState();
                    this.tvPlay.setText(R.string.D4sh_battery_mode_tips);
                    this.ivOffline.setVisibility(8);
                    this.tvPlay.setVisibility(0);
                    this.tvEvent.setText("");
                    this.tvEvent.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
                    this.llNoEvent.setVisibility(8);
                } else if (homeDeviceData.getData().getStatus().getBatteryStatus() == 1) {
                    this.rlDeviceState.setVisibility(8);
                    this.tvPlay.setText(R.string.D4sh_battery_mode_tips);
                    this.tvPlay.setVisibility(0);
                    this.tvEvent.setText("");
                    this.tvEvent.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
                    this.llNoEvent.setVisibility(8);
                } else if (homeDeviceData.getData().getStatus().getFood1() == 0 || homeDeviceData.getData().getStatus().getFood2() == 0) {
                    this.tvDeviceState.setText(R.string.Empty_food);
                    setWarnDeviceState();
                } else if (homeDeviceData.getData().getStatus().getFood1() == 1 || homeDeviceData.getData().getStatus().getFood2() == 1) {
                    this.tvDeviceState.setText(R.string.Food_lack);
                    setWarnDeviceState();
                } else {
                    this.rlDeviceState.setVisibility(8);
                    if (homeDeviceData.getId() != -1) {
                        this.ivSetting.setVisibility(8);
                    }
                }
            } else {
                homeDeviceData.getData().getDesc();
                this.rlDeviceState.setVisibility(8);
                if (homeDeviceData.getId() != -1) {
                    this.ivSetting.setVisibility(8);
                }
            }
        }
        if (this.tvShareLine.getVisibility() == 0 && (this.tvEvent.getVisibility() == 8 || this.tvEvent.getText() == null || this.tvEvent.getText().length() <= 1)) {
            this.tvShareLine.setVisibility(8);
        }
        if (homeDeviceData.getId() == -1) {
            this.rlDeviceState.setVisibility(0);
            this.ivRedPoint.setVisibility(8);
            this.tvDeviceState.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
            this.rlDeviceState.setBackgroundResource(R.drawable.solid_home_device_new_state_virtual_experience);
            this.tvDeviceState.setText(R.string.Virtual_experience);
            this.tvDeviceState.setTextColor(this.context.getResources().getColor(R.color.white));
            this.tvDeviceName.setText(this.context.getResources().getString(R.string.D4SH_name_default));
            if (state != 2) {
                if (homeDeviceData.getData().getEvent() != null) {
                    if (homeDeviceData.getData().getEvent().getEventType() == 7) {
                        this.tvEvent.setText(TimeUtils.getInstance().secondsToTimeStringWithUnit(this.context, homeDeviceData.getData().getEvent().getTimestamp()) + " " + this.context.getString(R.string.Movement_detected));
                        return;
                    }
                    if (homeDeviceData.getData().getEvent().getEventType() == 8) {
                        this.tvEvent.setText(TimeUtils.getInstance().secondsToTimeStringWithUnit(this.context, homeDeviceData.getData().getEvent().getTimestamp()) + " " + this.context.getString(R.string.Cat_appeared));
                        return;
                    }
                    if (homeDeviceData.getData().getEvent().getEventType() == 5 || homeDeviceData.getData().getEvent().getEventType() == 6) {
                        this.tvEvent.setText(TimeUtils.getInstance().secondsToTimeStringWithUnit(this.context, homeDeviceData.getData().getEvent().getTimestamp()) + " " + this.context.getString(R.string.D4sh_device_record_pet_ate));
                        return;
                    }
                    this.tvEvent.setText("");
                    this.tvEvent.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
                    return;
                }
                this.tvEvent.setText("");
                this.tvEvent.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
                return;
            }
            return;
        }
        this.tvDeviceState.setTextColor(this.context.getResources().getColor(R.color.white));
        this.ivRedPoint.setVisibility(DeviceCenterUtils.isD4shNeedOtaById(homeDeviceData.getId()) ? 0 : 8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateData$0(final HomeDeviceData homeDeviceData, final String str) {
        if (str != null) {
            ThreadPoolManager.getInstance().execute(new Runnable() { // from class: com.petkit.android.activities.home.adapter.newholder.D4shHomeLargeDeviceViewHolder.2
                @Override // java.lang.Runnable
                public void run() {
                    final Bitmap bitmapBlur = BlurUtils.blur(D4shHomeLargeDeviceViewHolder.this.context, BitmapFactory.decodeFile(ImageUtils.decryptImageFile(new File(str), homeDeviceData.getData().getAesKey()).getAbsolutePath()), 10, 0.125f);
                    new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.petkit.android.activities.home.adapter.newholder.D4shHomeLargeDeviceViewHolder.2.1
                        @Override // java.lang.Runnable
                        public void run() {
                            D4shHomeLargeDeviceViewHolder.this.ivVideo.setImageBitmap(bitmapBlur);
                        }
                    });
                }
            });
            this.llNoEvent.setVisibility(8);
            return;
        }
        this.llNoEvent.setVisibility(0);
        this.ivNoEvent.setImageResource(R.drawable.home_card_no_event_icon);
        if (homeDeviceData.getData().getEvent() == null) {
            this.tvNoEvent.setText(R.string.H3_no_event);
        } else {
            this.tvNoEvent.setText(R.string.No_video);
        }
        this.ivVideo.setImageResource(R.drawable.transparent);
        this.ivShadow.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateData$1(final HomeDeviceData homeDeviceData, final String str) {
        if (str != null) {
            ThreadPoolManager.getInstance().execute(new Runnable() { // from class: com.petkit.android.activities.home.adapter.newholder.D4shHomeLargeDeviceViewHolder.3
                @Override // java.lang.Runnable
                public void run() {
                    final Bitmap bitmapBlur = BlurUtils.blur(D4shHomeLargeDeviceViewHolder.this.context, BitmapFactory.decodeFile(ImageUtils.decryptImageFile(new File(str), homeDeviceData.getData().getAesKey()).getAbsolutePath()), 10, 0.125f);
                    new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.petkit.android.activities.home.adapter.newholder.D4shHomeLargeDeviceViewHolder.3.1
                        @Override // java.lang.Runnable
                        public void run() {
                            D4shHomeLargeDeviceViewHolder.this.ivVideo.setImageBitmap(bitmapBlur);
                        }
                    });
                }
            });
        } else {
            this.ivVideo.setImageResource(R.drawable.solid_dark_black_8);
        }
    }

    private Bitmap bimapRound(Bitmap bitmap, float f) {
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        RectF rectF = new RectF(rect);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawRoundRect(rectF, f, f, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return bitmapCreateBitmap;
    }

    private void checkDeviceNameSize(HomeDeviceData homeDeviceData) {
        if (homeDeviceData.getData().getDeviceShared(25) != null) {
            this.ivRedPoint.setVisibility(8);
            this.tvDeviceName.setMaxLines(1);
            this.tvShare.setVisibility(0);
            this.tvShareLine.setVisibility(0);
            String name = homeDeviceData.getData().getName();
            if (!TextUtils.isEmpty(name)) {
                this.tvDeviceName.setText(name);
                return;
            } else {
                this.tvDeviceName.setText(this.context.getString(R.string.Device_d4sh_name));
                return;
            }
        }
        this.tvShare.setVisibility(8);
        this.tvShareLine.setVisibility(8);
        this.tvDeviceName.setMaxLines(1);
        String name2 = homeDeviceData.getData().getName();
        if (!TextUtils.isEmpty(name2)) {
            this.tvDeviceName.setText(name2);
        } else {
            this.tvDeviceName.setText(this.context.getString(R.string.Device_d4sh_name));
        }
    }
}

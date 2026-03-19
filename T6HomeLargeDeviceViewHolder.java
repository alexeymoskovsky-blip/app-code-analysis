package com.petkit.android.activities.home.adapter.newholder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.text.SpannableString;
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
import com.petkit.android.activities.common.utils.DeviceCenterUtils;
import com.petkit.android.activities.home.NewCardOnClickListener;
import com.petkit.android.activities.home.adapter.model.HomeDeviceData;
import com.petkit.android.activities.home.utils.BlurUtils;
import com.petkit.android.activities.petkitBleDevice.t4.widget.RoundImageview;
import com.petkit.android.activities.petkitBleDevice.t6.mode.T6Record;
import com.petkit.android.activities.petkitBleDevice.t6.utils.T6Utils;
import com.petkit.android.activities.petkitBleDevice.utils.TimeUtils;
import com.petkit.android.utils.CommonUtil;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.DateUtil;
import com.petkit.android.utils.PetkitLog;
import com.petkit.android.utils.ThreadPoolManager;
import com.petkit.oversea.R;
import java.io.File;

/* JADX INFO: loaded from: classes4.dex */
public class T6HomeLargeDeviceViewHolder extends BaseHomeLargeDeviceWithControlViewHolder {
    public final Context context;
    public final RoundImageview ivBg;
    public final ImageView ivCameraTurnOff;
    public final ImageView ivNoEvent;
    public final ImageView ivPlay;
    public ImageView ivSetting;
    public final ImageView ivShadow;
    public final ImageView ivVideo;
    public final LinearLayout llCameraTurnOff;
    public final LinearLayout llNoEvent;
    public final RelativeLayout llParentPanel;
    public RelativeLayout rlControlView;
    public T6Record t6Record;
    public final TextView tvContentOne;
    public final TextView tvDeviceTag;
    public final TextView tvEvent;
    public final TextView tvNoEvent;
    public final TextView tvOffCamera;
    public final TextView tvShare;
    public final TextView tvShareLine;
    public final TextView tvTime;
    public final TextView tvTip;
    public final View viewBlack;

    public T6HomeLargeDeviceViewHolder(@NonNull View view) {
        super(view);
        Context context = view.getContext();
        this.context = context;
        this.rlControlView = (RelativeLayout) view.findViewById(R.id.rl_control_view);
        this.ivShadow = (ImageView) view.findViewById(R.id.iv_shadow);
        this.viewBlack = view.findViewById(R.id.view_black);
        this.llCameraTurnOff = (LinearLayout) view.findViewById(R.id.ll_camera_turn_off);
        this.tvOffCamera = (TextView) view.findViewById(R.id.tv_off_camera);
        this.ivCameraTurnOff = (ImageView) view.findViewById(R.id.iv_camera_turn_off);
        this.tvDeviceTag = (TextView) view.findViewById(R.id.tv_device_tag);
        this.ivVideo = (ImageView) view.findViewById(R.id.iv_video);
        RoundImageview roundImageview = (RoundImageview) view.findViewById(R.id.iv_bg);
        this.ivBg = roundImageview;
        this.ivPlay = (ImageView) view.findViewById(R.id.iv_play);
        this.ivSetting = (ImageView) view.findViewById(R.id.iv_setting);
        this.ivNoEvent = (ImageView) view.findViewById(R.id.iv_no_event);
        this.llNoEvent = (LinearLayout) view.findViewById(R.id.ll_no_event);
        this.tvNoEvent = (TextView) view.findViewById(R.id.tv_no_event);
        this.tvShare = (TextView) view.findViewById(R.id.tv_share);
        this.tvShareLine = (TextView) view.findViewById(R.id.tv_share_line);
        this.tvEvent = (TextView) view.findViewById(R.id.tv_event);
        this.tvTip = (TextView) view.findViewById(R.id.tv_tip);
        this.tvTime = (TextView) view.findViewById(R.id.tv_time);
        this.tvContentOne = (TextView) view.findViewById(R.id.tv_consumable_one_content);
        RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.ll_parent_panel);
        this.llParentPanel = relativeLayout;
        roundImageview.setAllRound();
        this.tvDeviceState.setCompoundDrawablePadding(ArmsUtils.dip2px(context, 4.0f));
        relativeLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.petkit.android.activities.home.adapter.newholder.T6HomeLargeDeviceViewHolder.1
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                int height = T6HomeLargeDeviceViewHolder.this.llParentPanel.getHeight();
                ViewGroup.LayoutParams layoutParams = T6HomeLargeDeviceViewHolder.this.ivBg.getLayoutParams();
                layoutParams.height = height;
                T6HomeLargeDeviceViewHolder.this.ivBg.setLayoutParams(layoutParams);
                T6HomeLargeDeviceViewHolder.this.llParentPanel.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    @Override // com.petkit.android.activities.home.adapter.newholder.BaseHomeLargeDeviceViewHolder
    public void updateData(final HomeDeviceData homeDeviceData, int i, NewCardOnClickListener newCardOnClickListener) {
        this.tvDeviceState.setText("");
        this.tvTip.setText("");
        this.viewBlack.setVisibility(8);
        this.tvEvent.setVisibility(0);
        this.ivShadow.setVisibility(0);
        this.ivBg.setImageResource(0);
        this.ivVideo.setImageResource(0);
        this.rlDeviceState.setBackgroundResource(0);
        int controlSettings = homeDeviceData.getData().getControlSettings();
        if (controlSettings == 1) {
            this.rlControl.setVisibility(0);
            PetkitLog.d("getTrunkState", "重量状态：" + homeDeviceData.getData().getStatus().getTrunkState());
            if (homeDeviceData.getData().getState() != 2 && ((homeDeviceData.getData().getState() != 4 || homeDeviceData.getData().getStatus().getErrorCode().equals("camera")) && homeDeviceData.getData().getStatus().getPower() != 0 && homeDeviceData.getData().getStatus().getOta() != 1 && homeDeviceData.getData().getStatus().getTopIns() != 1 && homeDeviceData.getData().getStatus().getPiIns() != 1 && homeDeviceData.getData().getStatus().getTrunkState() != 1 && homeDeviceData.getData().getStatus().getPackageState() != 2 && homeDeviceData.getData().getStatus().getPackageState() != 3 && homeDeviceData.getData().getStatus().getWeightState() != 1 && homeDeviceData.getData().getStatus().getPackageState() != 4 && homeDeviceData.getData().getStatus().getPackageState() != 5 && homeDeviceData.getData().getStatus().getBaggingState().intValue() != 0 && (homeDeviceData.getData().getStatus().getErrorMsg() == null || homeDeviceData.getData().getStatus().getErrorCode().equals("camera")))) {
                if (homeDeviceData.getData().getStatus().getWorkState() != null && homeDeviceData.getData().getStatus().getWorkState().getWorkMode() == 0) {
                    this.tvControl.setText(this.context.getString(R.string.T6_tab_clean));
                    this.ivControl.setBackgroundResource(R.drawable.card_t6_cleaning_up_gray_icon);
                    this.rlControl.setBackgroundResource(R.drawable.circle_t6_card_black);
                    this.tvControl.setTextColor(this.context.getResources().getColor(R.color.new_home_card_light_blue));
                } else {
                    this.tvControl.setText(this.context.getString(R.string.T6_tab_clean));
                    this.ivControl.setBackgroundResource(R.drawable.card_t6_clean_up_icon);
                    this.rlControl.setBackgroundResource(R.drawable.circle_t6_card_black);
                    this.tvControl.setTextColor(this.context.getResources().getColor(R.color.new_home_card_blue));
                }
            } else {
                this.tvControl.setText(this.context.getString(R.string.T6_tab_clean));
                this.ivControl.setBackgroundResource(R.drawable.card_t6_clean_up_gray_icon);
                this.rlControl.setBackgroundResource(R.drawable.circle_t6_card_black);
                this.tvControl.setTextColor(this.context.getResources().getColor(R.color.t4_text_gray));
            }
        } else if (controlSettings == 2) {
            this.rlControl.setVisibility(0);
            this.rlControl.setBackgroundResource(R.drawable.circle_t6_card_black);
            if (homeDeviceData.getData().getState() != 2 && homeDeviceData.getData().getState() != 4 && homeDeviceData.getData().getStatus().getPower() != 0 && homeDeviceData.getData().getStatus().getOta() != 1) {
                if (homeDeviceData.getData().getStatus().getCameraStatus().intValue() == 1) {
                    this.ivControl.setBackgroundResource(R.drawable.camera_on_icon);
                } else {
                    this.ivControl.setBackgroundResource(R.drawable.camera_off_icon);
                }
            } else {
                this.rlControl.setBackgroundResource(R.drawable.circle_t6_card_black);
                if (homeDeviceData.getData().getStatus().getCameraStatus().intValue() == 1) {
                    this.ivControl.setBackgroundResource(R.drawable.camera_on_gray_icon);
                } else {
                    this.ivControl.setBackgroundResource(R.drawable.camera_off_gray_icon);
                }
                this.tvControl.setTextColor(this.context.getResources().getColor(R.color.t4_text_gray));
            }
        } else if (controlSettings == 3) {
            this.rlControl.setVisibility(0);
            this.rlControl.setBackgroundResource(R.drawable.circle_t6_card_black);
            if (homeDeviceData.getData().getState() == 2 || homeDeviceData.getData().getState() == 4 || homeDeviceData.getData().getStatus().getPower() == 0 || homeDeviceData.getData().getStatus().getOta() == 1 || homeDeviceData.getData().getStatus().getWorkState() != null || homeDeviceData.getData().getStatus().getPetInTime() != 0) {
                this.ivControl.setBackgroundResource(R.drawable.home_card_home_mode_gray_icon);
            } else if (homeDeviceData.getData().getSettings().getHomeMode() == 1) {
                this.ivControl.setBackgroundResource(R.drawable.home_card_home_mode_on_icon);
            } else {
                this.ivControl.setBackgroundResource(R.drawable.home_card_home_mode_off_icon);
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
        this.tvEvent.setCompoundDrawablePadding(ArmsUtils.dip2px(this.context, 3.0f));
        this.tvDeviceTag.setVisibility(4);
        this.llCameraTurnOff.setVisibility(8);
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
        if (homeDeviceData.getData().getSettings().getHomeMode() == 1) {
            this.tvDeviceName.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, this.context.getResources().getDrawable(R.drawable.home_card_small_home_mode_icon), (Drawable) null);
        } else {
            this.tvDeviceName.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
        }
        T6Utils.getT6RecordByDeviceId(homeDeviceData.getData().getId(), 0);
        this.tvDeviceState.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
        this.tvEvent.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
        this.tvDeviceState.setCompoundDrawablePadding(ArmsUtils.dip2px(this.context, 6.0f));
        PetkitLog.d(getClass().getSimpleName(), "preview:" + homeDeviceData.getData().getPreview());
        this.ivSetting.setVisibility(8);
        this.tvTip.setVisibility(8);
        this.ivCameraTurnOff.setVisibility(8);
        this.tvDeviceState.setText("");
        T6Record t6RecordByDeviceId = T6Utils.getT6RecordByDeviceId(homeDeviceData.getData().getId(), 0);
        refreshToiletData(homeDeviceData);
        if (2 == state) {
            this.ivVideo.setImageResource(R.color.transparent);
            this.ivShadow.setVisibility(8);
            this.ivPlay.setVisibility(8);
            this.tvTip.setVisibility(8);
            this.ivNoEvent.setImageResource(R.drawable.offline_icon);
            if (t6RecordByDeviceId == null || t6RecordByDeviceId.getState() == null || t6RecordByDeviceId.getState().getOfflineTime() <= 0) {
                this.tvNoEvent.setText(this.context.getString(R.string.Toilet_is_offline));
            } else {
                this.tvNoEvent.setText(this.context.getString(R.string.Offline_time, DateUtil.getDateFormatShortTimeShortString(DateUtil.long2str(t6RecordByDeviceId.getState().getOfflineTime(), "yyyy-MM-dd'T'HH:mm:ss.SSSZ"))));
            }
            this.llNoEvent.setVisibility(0);
        } else if (homeDeviceData.getData().getStatus().getPower() == 0) {
            this.ivVideo.setImageResource(R.drawable.solid_dark_black_corners_top_8);
            this.tvTip.setText(R.string.T6_Power_off);
            this.tvTip.setVisibility(0);
            this.llNoEvent.setVisibility(8);
        } else if (homeDeviceData.getData().getSettings() != null && homeDeviceData.getData().getSettings().getCamera() == 0) {
            this.ivVideo.setImageResource(R.drawable.solid_dark_black_corners_top_8);
            this.ivPlay.setVisibility(8);
            this.ivCameraTurnOff.setVisibility(0);
            this.llNoEvent.setVisibility(8);
        } else if ((homeDeviceData.getData().getSettings() != null && homeDeviceData.getData().getSettings().getHomeMode() == 1 && t6RecordByDeviceId != null && t6RecordByDeviceId.getSettings().getCameraOff() == 1) || homeDeviceData.getData().getStatus().getCameraStatus().intValue() == 0) {
            this.ivVideo.setImageResource(R.drawable.solid_dark_black_corners_top_8);
            this.ivPlay.setVisibility(8);
            this.ivCameraTurnOff.setVisibility(0);
            this.llNoEvent.setVisibility(8);
        } else if (homeDeviceData.getData().getSettings().getPreLive() == 0) {
            this.ivPlay.setVisibility(8);
            if (!TextUtils.isEmpty(homeDeviceData.getData().getPreview())) {
                new ImageCacheSimple(CommonUtils.getAppContext()).getImageCachePath(CommonUtil.httpToHttps(homeDeviceData.getData().getPreview()), new ImageCacheSimple.IGetImageCacheListener() { // from class: com.petkit.android.activities.home.adapter.newholder.T6HomeLargeDeviceViewHolder$$ExternalSyntheticLambda0
                    @Override // com.jess.arms.widget.imageloader.ImageCacheSimple.IGetImageCacheListener
                    public final void onCachePath(String str) {
                        this.f$0.lambda$updateData$0(homeDeviceData, str);
                    }
                });
            } else {
                this.ivVideo.setImageResource(R.drawable.solid_dark_black_8);
                this.llNoEvent.setVisibility(0);
                if (homeDeviceData.getData().getEvent() == null) {
                    this.tvNoEvent.setText(R.string.H3_no_event);
                } else {
                    this.tvNoEvent.setText(R.string.No_video);
                }
            }
        } else if (TextUtils.isEmpty(homeDeviceData.getData().getPreview())) {
            this.ivVideo.setImageResource(R.color.transparent);
            this.ivShadow.setVisibility(8);
            this.ivPlay.setVisibility(8);
            this.llNoEvent.setVisibility(0);
            if (homeDeviceData.getData().getEvent() == null) {
                this.tvNoEvent.setText(R.string.H3_no_event);
            } else {
                this.tvNoEvent.setText(R.string.No_video);
            }
        } else if (!TextUtils.isEmpty(homeDeviceData.getData().getPreview())) {
            this.ivPlay.setVisibility(8);
            new ImageCacheSimple(CommonUtils.getAppContext()).getImageCachePath(CommonUtil.httpToHttps(homeDeviceData.getData().getPreview()), new ImageCacheSimple.IGetImageCacheListener() { // from class: com.petkit.android.activities.home.adapter.newholder.T6HomeLargeDeviceViewHolder$$ExternalSyntheticLambda1
                @Override // com.jess.arms.widget.imageloader.ImageCacheSimple.IGetImageCacheListener
                public final void onCachePath(String str) {
                    this.f$0.lambda$updateData$1(homeDeviceData, str);
                }
            });
        } else {
            this.ivVideo.setImageResource(R.color.transparent);
            this.ivShadow.setVisibility(8);
            this.ivPlay.setVisibility(8);
            this.tvTip.setVisibility(8);
            this.ivNoEvent.setImageResource(R.drawable.offline_icon);
            if (t6RecordByDeviceId != null) {
                if (t6RecordByDeviceId.getState().getOfflineTime() > 0) {
                    this.tvNoEvent.setText(this.context.getString(R.string.Toilet_is_offline));
                } else {
                    this.tvNoEvent.setText(this.context.getString(R.string.Offline_time, DateUtil.getDateFormatShortTimeShortString(DateUtil.long2str(t6RecordByDeviceId.getState().getOfflineTime(), "yyyy-MM-dd'T'HH:mm:ss.SSSZ"))));
                }
            }
            this.llNoEvent.setVisibility(0);
        }
        if (homeDeviceData.getData().getDesc() == null) {
            this.tvEvent.setText("");
        } else {
            this.tvEvent.setText(homeDeviceData.getData().getDesc());
        }
        if (state == 2) {
            this.tvDeviceState.setText(R.string.Offline);
            setUnconnectDeviceState();
        } else if (homeDeviceData.getData().getStatus().getOta() == 1) {
            setUpdatingDeviceState();
            this.tvDeviceState.setText(R.string.Card_device_up);
        } else if (!TextUtils.isEmpty(homeDeviceData.getData().getStatus().getErrorCode())) {
            setWarnDeviceState();
            this.tvDeviceState.setText(R.string.Cozy_error);
        } else if (homeDeviceData.getData().getStatus().getPackageState() == 5) {
            setWarnDeviceState();
            this.tvDeviceState.setText(R.string.T6_error_bagBox_not_installed);
        } else if (homeDeviceData.getData().getStatus().getPiIns() == 1) {
            setWarnDeviceState();
            this.tvDeviceState.setText(R.string.T6_error_bagBoxTopIns_not_installed);
        } else if (homeDeviceData.getData().getStatus().getTopIns() == 1) {
            setWarnDeviceState();
            this.tvDeviceState.setText(R.string.Main_engine_tail_box_not_installed);
        } else if (homeDeviceData.getData().getStatus().getPackageState() == 2) {
            setWarnDeviceState();
            this.tvDeviceState.setText(R.string.T6_error_bags_have_run_out);
        } else if (homeDeviceData.getData().getStatus().getPackageState() == 3) {
            setWarnDeviceState();
            this.tvDeviceState.setText(R.string.T6_error_bagBox_invalid);
        } else if (homeDeviceData.getData().getStatus().getPackageState() == 4) {
            setWarnDeviceState();
            this.tvDeviceState.setText(R.string.T6_error_bagBox_not_avaliable);
        } else if (homeDeviceData.getData().getStatus().getBaggingState() != null && homeDeviceData.getData().getStatus().getBaggingState().intValue() == 0) {
            setWarnDeviceState();
            this.tvDeviceState.setText(R.string.Auto_bagging_fail);
        } else if (homeDeviceData.getData().getStatus().isBoxFull()) {
            setWarnDeviceState();
            this.tvDeviceState.setText(R.string.T6_err_box_full);
        } else if (homeDeviceData.getData().getStatus().getPackageState() == 1 && homeDeviceData.getData().getPackageIgnoreState() == 0.0f) {
            setWarnDeviceState();
            this.tvDeviceState.setText(R.string.T6_error_bags_running_out);
        } else {
            if (homeDeviceData.getData().getDesc() == null) {
                this.tvEvent.setText("");
            } else {
                this.tvEvent.setText(homeDeviceData.getData().getDesc());
            }
            this.rlDeviceState.setVisibility(8);
            if (homeDeviceData.getData().getId() != -1) {
                this.ivSetting.setVisibility(8);
            }
        }
        if (state != 2) {
            if (homeDeviceData.getData().getEvent() != null && homeDeviceData.getData().getStatus().getPower() != 0 && homeDeviceData.getData().getStatus().getWorkState() == null && homeDeviceData.getData().getStatus().getRefreshState() == null) {
                String strStampToDate = TimeUtils.getInstance().stampToDate(this.context, homeDeviceData.getData().getEvent().getTimestamp(), t6RecordByDeviceId == null ? null : t6RecordByDeviceId.getActualTimeZone());
                int eventType = homeDeviceData.getData().getEvent().getEventType();
                if (eventType == 10) {
                    this.tvEvent.setText(strStampToDate + " " + this.context.getResources().getString(R.string.T6_toilet_event_coming));
                    this.tvEvent.setCompoundDrawablesWithIntrinsicBounds(this.context.getResources().getDrawable(R.drawable.card_pet_toilet_icon), (Drawable) null, (Drawable) null, (Drawable) null);
                } else if (eventType == 19) {
                    this.tvEvent.setText(strStampToDate + " " + this.context.getResources().getString(R.string.T6_pet_event_coming));
                    this.tvEvent.setCompoundDrawablesWithIntrinsicBounds(this.context.getResources().getDrawable(R.drawable.card_pet_appear_icon), (Drawable) null, (Drawable) null, (Drawable) null);
                } else if (eventType == 20) {
                    this.tvEvent.setText(strStampToDate + " " + this.context.getResources().getString(R.string.Cat_appeared));
                    this.tvEvent.setCompoundDrawablesWithIntrinsicBounds(this.context.getResources().getDrawable(R.drawable.card_pet_appear_icon), (Drawable) null, (Drawable) null, (Drawable) null);
                } else {
                    this.tvEvent.setText("");
                }
            } else if (homeDeviceData.getData().getStatus().getWorkState() != null || homeDeviceData.getData().getStatus().getRefreshState() != null) {
                this.tvEvent.setText(R.string.State_working);
                this.tvEvent.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
                this.tvEvent.setVisibility(0);
            } else {
                this.tvEvent.setText("");
            }
        } else {
            this.tvEvent.setText("");
        }
        if (homeDeviceData.getData().getStatus().getWorkState() != null) {
            this.tvEvent.setText(R.string.State_working);
            this.tvEvent.setVisibility(0);
        }
        if (homeDeviceData.getData().getId() != -1) {
            this.tvDeviceState.setTextColor(this.context.getResources().getColor(R.color.white));
            this.ivRedPoint.setVisibility(DeviceCenterUtils.isT6NeedOtaById(homeDeviceData.getData().getId()) ? 0 : 8);
        }
        if (this.tvShareLine.getVisibility() == 0) {
            if (this.tvEvent.getVisibility() == 8 || this.tvEvent.getText() == null || this.tvEvent.getText().length() == 0) {
                this.tvShareLine.setVisibility(8);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateData$0(final HomeDeviceData homeDeviceData, final String str) {
        if (str != null) {
            ThreadPoolManager.getInstance().execute(new Runnable() { // from class: com.petkit.android.activities.home.adapter.newholder.T6HomeLargeDeviceViewHolder.2
                @Override // java.lang.Runnable
                public void run() {
                    final Bitmap bitmapBlur = BlurUtils.blur(T6HomeLargeDeviceViewHolder.this.context, T6Utils.getRotateBitmap(ImageUtils.decryptImageFile(new File(str), homeDeviceData.getData().getAesKey()).getAbsolutePath(), homeDeviceData.getData().getPreview()), 10, 0.125f);
                    new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.petkit.android.activities.home.adapter.newholder.T6HomeLargeDeviceViewHolder.2.1
                        @Override // java.lang.Runnable
                        public void run() {
                            T6HomeLargeDeviceViewHolder.this.ivVideo.setImageBitmap(bitmapBlur);
                        }
                    });
                }
            });
            this.llNoEvent.setVisibility(8);
            this.viewBlack.setVisibility(8);
        } else {
            this.llNoEvent.setVisibility(0);
            this.ivNoEvent.setImageResource(R.drawable.home_card_no_event_icon);
            if (homeDeviceData.getData().getEvent() == null) {
                this.tvNoEvent.setText(R.string.H3_no_event);
            } else {
                this.tvNoEvent.setText(R.string.No_video);
            }
            this.ivVideo.setImageResource(R.drawable.solid_dark_black_8);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateData$1(final HomeDeviceData homeDeviceData, final String str) {
        if (str != null) {
            ThreadPoolManager.getInstance().execute(new Runnable() { // from class: com.petkit.android.activities.home.adapter.newholder.T6HomeLargeDeviceViewHolder.3
                @Override // java.lang.Runnable
                public void run() {
                    final Bitmap bitmapBimapSquareRound = CommonUtil.bimapSquareRound(170, T6Utils.getRotateBitmap(ImageUtils.decryptImageFile(new File(str), homeDeviceData.getData().getAesKey()).getAbsolutePath(), homeDeviceData.getData().getPreview()));
                    new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.petkit.android.activities.home.adapter.newholder.T6HomeLargeDeviceViewHolder.3.1
                        @Override // java.lang.Runnable
                        public void run() {
                            T6HomeLargeDeviceViewHolder.this.ivVideo.setImageBitmap(bitmapBimapSquareRound);
                        }
                    });
                }
            });
            this.llNoEvent.setVisibility(8);
            this.viewBlack.setVisibility(8);
        }
    }

    private void refreshToiletData(HomeDeviceData homeDeviceData) {
        this.tvTime.setText(new SpannableString(homeDeviceData.getData().getToiletCount() + ""));
        this.tvContentOne.setText(T6Utils.getT6HomeCardString(homeDeviceData.getData().getToiletTimeAvg()));
        if (homeDeviceData.getData().getStatus().getPackageState() == 2 || homeDeviceData.getData().getStatus().getPackageState() == 3 || homeDeviceData.getData().getStatus().getPackageState() == 4) {
            return;
        }
        homeDeviceData.getData().getStatus().getPackageState();
    }

    private void checkDeviceNameSize(HomeDeviceData homeDeviceData) {
        this.tvShare.setVisibility(homeDeviceData.getShared() == 1 ? 0 : 8);
        this.tvShareLine.setVisibility(homeDeviceData.getShared() == 1 ? 0 : 8);
        if (homeDeviceData.getData().getDeviceShared(27) != null) {
            this.tvShare.setVisibility(0);
            this.tvShareLine.setVisibility(0);
            this.ivRedPoint.setVisibility(8);
            this.tvDeviceName.setMaxLines(1);
            String name = homeDeviceData.getData().getName();
            if (!TextUtils.isEmpty(name)) {
                this.tvDeviceName.setText(name);
                return;
            } else {
                this.tvDeviceName.setText(this.context.getString(R.string.Device_t6_name));
                return;
            }
        }
        this.tvShare.setVisibility(8);
        this.tvShareLine.setVisibility(8);
        String name2 = homeDeviceData.getData().getName();
        if (!TextUtils.isEmpty(name2)) {
            this.tvDeviceName.setText(name2);
        } else {
            this.tvDeviceName.setText(this.context.getString(R.string.Device_t6_name));
        }
    }
}

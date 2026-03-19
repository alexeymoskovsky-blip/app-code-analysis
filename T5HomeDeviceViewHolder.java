package com.petkit.android.activities.home.adapter.holder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.ImageUtils;
import com.jess.arms.widget.imageloader.ImageCacheSimple;
import com.petkit.android.activities.common.utils.DeviceCenterUtils;
import com.petkit.android.activities.home.adapter.model.HomeDeviceData;
import com.petkit.android.activities.home.adapter.model.HomeDeviceStatus;
import com.petkit.android.activities.home.utils.BlurUtils;
import com.petkit.android.activities.petkitBleDevice.t4.widget.RoundImageview;
import com.petkit.android.activities.petkitBleDevice.t6.mode.T6Record;
import com.petkit.android.activities.petkitBleDevice.t6.utils.T6Utils;
import com.petkit.android.activities.petkitBleDevice.utils.TimeUtils;
import com.petkit.android.activities.petkitBleDevice.utils.UnitUtils;
import com.petkit.android.utils.CommonUtil;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.PetkitLog;
import com.petkit.oversea.R;
import java.io.File;

/* JADX INFO: loaded from: classes4.dex */
public class T5HomeDeviceViewHolder extends BaseHomeDeviceViewHolder {
    public final Context context;
    public FrameLayout flState;
    public final RoundImageview ivCameraTurnOff;
    public final RelativeLayout ivDownShadow;
    public final ImageView ivNoEvent;
    public final ImageView ivOffline;
    public final RoundImageview ivVideo;
    public final LinearLayout llNoEvent;
    public final TextView tvDeviceTag;
    public final TextView tvEvent;
    public final TextView tvNoEvent;
    public final TextView tvShared;
    public final TextView tvTip;
    public TextView tvVirtualDevice;

    public T5HomeDeviceViewHolder(@NonNull View view) {
        super(view);
        this.context = view.getContext();
        this.ivCameraTurnOff = (RoundImageview) view.findViewById(R.id.iv_camera_turn_off);
        this.tvDeviceTag = (TextView) view.findViewById(R.id.tv_device_tag);
        this.flState = (FrameLayout) view.findViewById(R.id.fl_state_bg);
        this.ivDownShadow = (RelativeLayout) view.findViewById(R.id.rl_down_shadow);
        this.ivVideo = (RoundImageview) view.findViewById(R.id.iv_video);
        this.ivOffline = (ImageView) view.findViewById(R.id.iv_offline);
        this.tvTip = (TextView) view.findViewById(R.id.tv_tip);
        this.tvVirtualDevice = (TextView) view.findViewById(R.id.tv_virtual_device);
        this.tvEvent = (TextView) view.findViewById(R.id.tv_event);
        this.tvShared = (TextView) view.findViewById(R.id.tv_shared);
        this.ivNoEvent = (ImageView) view.findViewById(R.id.iv_no_event);
        this.llNoEvent = (LinearLayout) view.findViewById(R.id.ll_no_event);
        this.tvNoEvent = (TextView) view.findViewById(R.id.tv_no_event);
    }

    public final void setTvDeviceStateGravity(int i) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.tvDeviceState.getLayoutParams();
        layoutParams.gravity = i | 16;
        this.tvDeviceState.setLayoutParams(layoutParams);
    }

    public final void showCare(boolean z) {
        this.tvDeviceTag.setVisibility(z ? 0 : 4);
    }

    @Override // com.petkit.android.activities.home.adapter.holder.BaseHomeDeviceViewHolder
    @SuppressLint({"UseCompatLoadingForDrawables"})
    public void updateData(HomeDeviceData homeDeviceData) {
        this.flState.setBackgroundResource(R.drawable.solid_home_device_state_tran);
        setTvDeviceStateGravity(GravityCompat.START);
        this.ivDownShadow.setVisibility(0);
        if (homeDeviceData.getData().getDeviceShared(21) != null) {
            this.tvShared.setVisibility(0);
        } else {
            this.tvShared.setVisibility(8);
        }
        this.tvDeviceState.setTextColor(CommonUtils.getColorById(R.color.ffffff_with_45_alpha));
        this.ivRedPoint.setVisibility(DeviceCenterUtils.isD4shNeedOtaById(homeDeviceData.getData().getId()) ? 0 : 8);
        int state = homeDeviceData.getData().getState();
        showCare(false);
        this.tvEvent.setVisibility(0);
        this.tvTip.setText("");
        if (homeDeviceData.getData().getServiceStatus() == 0) {
            showCare(false);
        } else if (homeDeviceData.getData().getServiceStatus() == 1) {
            showCare(true);
            this.tvDeviceTag.setTextColor(CommonUtils.getColorById(R.color.receive_for_free));
            this.tvDeviceTag.setBackgroundResource(R.drawable.gradient_gold_corners_petkitcare_tag_bg);
        } else if (homeDeviceData.getData().getServiceStatus() == 2) {
            showCare(true);
            this.tvDeviceTag.setTextColor(CommonUtils.getColorById(R.color.home_feeder_bg_gray));
            this.tvDeviceTag.setBackgroundResource(R.drawable.gradient_gray_corners_petkitcare_tag_bg);
        }
        checkDeviceNameSize(homeDeviceData);
        this.tvDeviceState.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
        this.tvEvent.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
        this.tvDeviceState.setCompoundDrawablePadding(ArmsUtils.dip2px(this.context, 6.0f));
        this.tvDeviceState.setText("");
        PetkitLog.d(getClass().getSimpleName(), "preview:" + homeDeviceData.getData().getPreview());
        if (state != 2) {
            T6Record t6RecordByDeviceId = T6Utils.getT6RecordByDeviceId(homeDeviceData.getData().getId(), 1);
            if (homeDeviceData.getData().getEvent() != null && homeDeviceData.getData().getStatus().getPower() != 0) {
                String strStampToDate = TimeUtils.getInstance().stampToDate(this.context, homeDeviceData.getData().getEvent().getTimestamp(), t6RecordByDeviceId == null ? null : t6RecordByDeviceId.getActualTimeZone());
                int eventType = homeDeviceData.getData().getEvent().getEventType();
                if (eventType == 10) {
                    this.tvEvent.setText(strStampToDate + " " + this.context.getResources().getString(R.string.T6_toilet_event_coming));
                    this.tvEvent.setCompoundDrawablesWithIntrinsicBounds(this.context.getResources().getDrawable(R.drawable.card_pet_toilet_icon), (Drawable) null, (Drawable) null, (Drawable) null);
                } else if (eventType == 19) {
                    this.tvEvent.setText(strStampToDate + " " + this.context.getResources().getString(R.string.T6_pet_event_coming));
                    this.tvEvent.setCompoundDrawablesWithIntrinsicBounds(this.context.getResources().getDrawable(R.drawable.card_pet_appear_icon), (Drawable) null, (Drawable) null, (Drawable) null);
                } else if (eventType == 20) {
                    this.tvEvent.setText(strStampToDate + " " + this.context.getResources().getString(R.string.D4sh_device_record_pet_walk_around));
                    this.tvEvent.setCompoundDrawablesWithIntrinsicBounds(this.context.getResources().getDrawable(R.drawable.card_pet_appear_icon), (Drawable) null, (Drawable) null, (Drawable) null);
                }
            } else {
                this.tvEvent.setText("");
            }
        } else {
            this.tvEvent.setText("");
        }
        this.ivCameraTurnOff.setVisibility(8);
        if (2 == state) {
            this.ivVideo.setImageResource(R.drawable.transparent);
            this.ivDownShadow.setVisibility(8);
            this.ivOffline.setVisibility(8);
            this.tvTip.setVisibility(8);
            this.llNoEvent.setVisibility(0);
            this.tvNoEvent.setText(R.string.Toilet_is_offline);
            this.ivNoEvent.setImageResource(R.drawable.offline_icon);
            this.tvDeviceState.setText(this.context.getResources().getString(R.string.Offline));
            this.tvDeviceState.setCompoundDrawablesWithIntrinsicBounds(this.context.getResources().getDrawable(R.drawable.home_item_warn_white_icon), (Drawable) null, (Drawable) null, (Drawable) null);
            this.flState.setBackgroundResource(R.drawable.solid_home_device_state_gray);
            setTvDeviceStateGravity(17);
            this.tvDeviceState.setTextColor(CommonUtils.getColorById(R.color.white));
            this.tvEvent.setText("");
        } else if (homeDeviceData.getData().getStatus().getOta() == 1) {
            refreshPreview(homeDeviceData, false);
            this.tvDeviceState.setText(R.string.Card_device_up);
            setTvDeviceStateGravity(17);
            this.ivVideo.setImageResource(R.drawable.solid_dark_black_corners_top_8);
            this.ivVideo.setVisibility(0);
            this.tvTip.setVisibility(8);
            this.ivOffline.setVisibility(8);
            this.ivCameraTurnOff.setVisibility(8);
        } else if (homeDeviceData.getData().getStatus().getPower() == 0) {
            this.tvDeviceState.setText(R.string.Power_off);
            setTvDeviceStateGravity(17);
            this.ivVideo.setImageResource(R.drawable.solid_dark_black_corners_top_8);
            this.ivVideo.setVisibility(0);
            this.tvTip.setText(R.string.T6_Power_off);
            this.tvTip.setVisibility(0);
            this.llNoEvent.setVisibility(8);
            this.ivOffline.setVisibility(8);
            this.ivCameraTurnOff.setVisibility(8);
        } else if (breakdown(homeDeviceData.getData().getStatus())) {
            refreshPreview(homeDeviceData, false);
            this.tvDeviceState.setCompoundDrawablesWithIntrinsicBounds(this.context.getResources().getDrawable(R.drawable.home_item_warn_white_icon), (Drawable) null, (Drawable) null, (Drawable) null);
            if (homeDeviceData.getData().getStatus().getErrorCode().equals("hallT")) {
                this.tvDeviceState.setText(R.string.T5_error_hallt);
            } else {
                this.tvDeviceState.setText(R.string.Cozy_error);
            }
            this.flState.setBackgroundResource(R.drawable.solid_home_device_state_red);
            setTvDeviceStateGravity(17);
            this.tvDeviceState.setTextColor(CommonUtils.getColorById(R.color.white));
        } else if (homeDeviceData.getData().getStatus().getBoxState() == 0) {
            refreshPreview(homeDeviceData, false);
            this.tvDeviceState.setCompoundDrawablesWithIntrinsicBounds(this.context.getResources().getDrawable(R.drawable.home_item_warn_white_icon), (Drawable) null, (Drawable) null, (Drawable) null);
            this.flState.setBackgroundResource(R.drawable.solid_home_device_state_red);
            setTvDeviceStateGravity(17);
            this.tvDeviceState.setTextColor(CommonUtils.getColorById(R.color.white));
            this.tvDeviceState.setText(R.string.T5_garbage_bags_not_install);
        } else if (homeDeviceData.getData().getStatus().isBoxFull()) {
            refreshPreview(homeDeviceData, false);
            this.tvDeviceState.setCompoundDrawablesWithIntrinsicBounds(this.context.getResources().getDrawable(R.drawable.home_item_warn_white_icon), (Drawable) null, (Drawable) null, (Drawable) null);
            this.flState.setBackgroundResource(R.drawable.solid_home_device_state_red);
            setTvDeviceStateGravity(17);
            this.tvDeviceState.setTextColor(CommonUtils.getColorById(R.color.white));
            this.tvDeviceState.setText(R.string.T6_err_box_full);
        } else if (homeDeviceData.getData().getStatus().getDeodorantLeftDays() < 1) {
            refreshPreview(homeDeviceData, false);
            this.tvDeviceState.setCompoundDrawablesWithIntrinsicBounds(this.context.getResources().getDrawable(R.drawable.home_item_warn_white_icon), (Drawable) null, (Drawable) null, (Drawable) null);
            this.flState.setBackgroundResource(R.drawable.solid_home_device_state_red);
            setTvDeviceStateGravity(17);
            this.tvDeviceState.setTextColor(CommonUtils.getColorById(R.color.white));
            this.tvDeviceState.setText(R.string.N50_lack);
        } else {
            refreshPreview(homeDeviceData, true);
        }
        if (homeDeviceData.getData().getId() == -1) {
            return;
        }
        this.tvVirtualDevice.setVisibility(8);
        this.ivRedPoint.setVisibility(DeviceCenterUtils.isT6NeedOtaById(homeDeviceData.getData().getId()) ? 0 : 8);
        this.tvDeviceState.setVisibility(0);
        this.flState.setVisibility(0);
    }

    public final void refreshPreview(final HomeDeviceData homeDeviceData, boolean z) {
        if (homeDeviceData.getData().getSettings().getCamera() == 0) {
            this.ivVideo.setImageResource(R.drawable.solid_dark_black_corners_top_8);
            this.ivOffline.setVisibility(8);
            this.ivCameraTurnOff.setVisibility(8);
            this.ivNoEvent.setImageResource(R.drawable.home_card_camera_off_icon);
            this.tvNoEvent.setText(R.string.Camera_is_off);
            this.ivNoEvent.setVisibility(0);
            this.llNoEvent.setVisibility(0);
            if (homeDeviceData.getData().getStatus().getWorkState() != null) {
                this.tvDeviceState.setBackground(new ColorDrawable(0));
                this.flState.setBackgroundResource(0);
                this.tvDeviceState.setText(R.string.State_working);
                this.tvEvent.setText("");
                this.tvEvent.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
                this.tvDeviceState.setVisibility(0);
                this.tvDeviceState.setTextColor(CommonUtils.getColorById(R.color.ffffff_with_45_alpha));
                setTvDeviceStateGravity(17);
                this.tvDeviceState.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
                return;
            }
            if (z) {
                this.tvDeviceState.setTextColor(this.context.getResources().getColor(R.color.gray));
                this.tvDeviceState.setBackground(new ColorDrawable(0));
                this.flState.setBackgroundResource(0);
                UnitUtils.getInstance().getUnitThroughNum(homeDeviceData.getData().getToiletCount(), this.context.getString(R.string.Unit_time), this.context.getString(R.string.Unit_times));
                this.tvDeviceState.setText("");
                this.tvDeviceState.setVisibility(0);
                this.tvDeviceState.setTextColor(CommonUtils.getColorById(R.color.gray));
                setTvDeviceStateGravity(GravityCompat.START);
                this.tvDeviceState.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
                return;
            }
            return;
        }
        if (homeDeviceData.getData().getSettings().getCamera() == 1 && homeDeviceData.getData().getStatus().getCameraStatus().intValue() == 0) {
            this.ivVideo.setImageResource(R.drawable.solid_dark_black_corners_top_8);
            this.ivOffline.setVisibility(8);
            this.ivCameraTurnOff.setVisibility(8);
            this.tvNoEvent.setText(R.string.Camera_off);
            this.ivNoEvent.setImageResource(R.drawable.home_card_camera_off_icon);
            this.ivNoEvent.setVisibility(0);
            this.llNoEvent.setVisibility(0);
            if (homeDeviceData.getData().getStatus().getWorkState() != null) {
                this.tvDeviceState.setBackground(new ColorDrawable(0));
                this.flState.setBackgroundResource(0);
                this.tvDeviceState.setText(R.string.State_working);
                this.tvEvent.setText("");
                this.tvEvent.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
                this.tvDeviceState.setVisibility(0);
                this.tvDeviceState.setTextColor(CommonUtils.getColorById(R.color.ffffff_with_45_alpha));
                setTvDeviceStateGravity(17);
                this.tvDeviceState.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
                return;
            }
            if (z) {
                this.tvDeviceState.setTextColor(this.context.getResources().getColor(R.color.gray));
                this.tvDeviceState.setBackground(new ColorDrawable(0));
                this.flState.setBackgroundResource(0);
                UnitUtils.getInstance().getUnitThroughNum(homeDeviceData.getData().getToiletCount(), this.context.getString(R.string.Unit_time), this.context.getString(R.string.Unit_times));
                this.tvDeviceState.setText("");
                this.tvDeviceState.setVisibility(0);
                this.tvDeviceState.setTextColor(CommonUtils.getColorById(R.color.gray));
                setTvDeviceStateGravity(GravityCompat.START);
                this.tvDeviceState.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
                return;
            }
            return;
        }
        if (homeDeviceData.getData().getSettings().getPreLive() == 0) {
            this.ivOffline.setVisibility(8);
            this.tvTip.setVisibility(8);
            this.llNoEvent.setVisibility(8);
            if (!TextUtils.isEmpty(homeDeviceData.getData().getPreview())) {
                new ImageCacheSimple(CommonUtils.getAppContext()).getImageCachePath(CommonUtil.httpToHttps(homeDeviceData.getData().getPreview()), new ImageCacheSimple.IGetImageCacheListener() { // from class: com.petkit.android.activities.home.adapter.holder.T5HomeDeviceViewHolder$$ExternalSyntheticLambda0
                    @Override // com.jess.arms.widget.imageloader.ImageCacheSimple.IGetImageCacheListener
                    public final void onCachePath(String str) {
                        this.f$0.lambda$refreshPreview$0(homeDeviceData, str);
                    }
                });
            } else {
                this.ivVideo.setImageResource(R.drawable.solid_dark_black_corners_top_8);
            }
            if (homeDeviceData.getData().getStatus().getWorkState() != null) {
                this.tvDeviceState.setTextColor(this.context.getResources().getColor(R.color.ffffff_with_45_alpha));
                this.flState.setBackgroundResource(0);
                this.tvDeviceState.setText(R.string.State_working);
                this.tvEvent.setText("");
                this.tvEvent.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
                this.tvDeviceState.setVisibility(0);
                setTvDeviceStateGravity(17);
                this.tvDeviceState.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
                return;
            }
            if (z) {
                this.flState.setBackgroundResource(0);
                UnitUtils.getInstance().getUnitThroughNum(homeDeviceData.getData().getToiletCount(), this.context.getString(R.string.Unit_time), this.context.getString(R.string.Unit_times));
                this.tvDeviceState.setText("");
                this.tvDeviceState.setVisibility(0);
                this.tvDeviceState.setTextColor(CommonUtils.getColorById(R.color.gray));
                setTvDeviceStateGravity(3);
                this.tvDeviceState.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
                return;
            }
            return;
        }
        if (TextUtils.isEmpty(homeDeviceData.getData().getPreview())) {
            this.ivVideo.setImageResource(R.color.transparent);
            this.ivDownShadow.setVisibility(8);
            this.ivOffline.setVisibility(8);
            this.tvTip.setVisibility(8);
            this.llNoEvent.setVisibility(0);
            if (homeDeviceData.getData().getEvent() == null) {
                this.tvNoEvent.setText(R.string.H3_no_event);
            } else {
                this.tvNoEvent.setText(R.string.No_video);
            }
            if (homeDeviceData.getData().getStatus().getWorkState() != null) {
                this.tvDeviceState.setTextColor(this.context.getResources().getColor(R.color.ffffff_with_45_alpha));
                this.flState.setBackgroundResource(0);
                this.tvDeviceState.setText(R.string.State_working);
                this.tvEvent.setText("");
                this.tvEvent.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
                this.tvDeviceState.setVisibility(0);
                setTvDeviceStateGravity(17);
                this.tvDeviceState.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
                return;
            }
            if (z) {
                UnitUtils.getInstance().getUnitThroughNum(homeDeviceData.getData().getToiletCount(), this.context.getString(R.string.Unit_time), this.context.getString(R.string.Unit_times));
                this.tvDeviceState.setText("");
                this.tvDeviceState.setVisibility(0);
                this.tvDeviceState.setTextColor(CommonUtils.getColorById(R.color.gray));
                setTvDeviceStateGravity(GravityCompat.START);
                this.tvDeviceState.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
                return;
            }
            return;
        }
        if (!TextUtils.isEmpty(homeDeviceData.getData().getPreview())) {
            this.ivVideo.setImageResource(R.drawable.solid_dark_black_corners_top_8);
            this.ivOffline.setVisibility(8);
            this.tvTip.setVisibility(8);
            this.llNoEvent.setVisibility(8);
            new ImageCacheSimple(CommonUtils.getAppContext()).getImageCachePath(CommonUtil.httpToHttps(homeDeviceData.getData().getPreview()), new ImageCacheSimple.IGetImageCacheListener() { // from class: com.petkit.android.activities.home.adapter.holder.T5HomeDeviceViewHolder$$ExternalSyntheticLambda1
                @Override // com.jess.arms.widget.imageloader.ImageCacheSimple.IGetImageCacheListener
                public final void onCachePath(String str) {
                    this.f$0.lambda$refreshPreview$1(homeDeviceData, str);
                }
            });
            if (homeDeviceData.getData().getStatus().getWorkState() != null) {
                this.tvDeviceState.setTextColor(this.context.getResources().getColor(R.color.ffffff_with_45_alpha));
                this.flState.setBackgroundResource(0);
                this.tvDeviceState.setText(R.string.State_working);
                this.tvEvent.setText("");
                this.tvEvent.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
                this.tvDeviceState.setVisibility(0);
                setTvDeviceStateGravity(17);
                this.tvDeviceState.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
                return;
            }
            if (z) {
                this.flState.setBackgroundResource(0);
                UnitUtils.getInstance().getUnitThroughNum(homeDeviceData.getData().getToiletCount(), this.context.getString(R.string.Unit_time), this.context.getString(R.string.Unit_times));
                this.tvDeviceState.setText("");
                this.tvDeviceState.setVisibility(0);
                this.tvDeviceState.setTextColor(CommonUtils.getColorById(R.color.gray));
                setTvDeviceStateGravity(3);
                this.tvDeviceState.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
                return;
            }
            return;
        }
        this.flState.setBackgroundResource(0);
        if (homeDeviceData.getData().getStatus().getWorkState() != null) {
            this.tvDeviceState.setTextColor(this.context.getResources().getColor(R.color.ffffff_with_45_alpha));
            this.flState.setBackgroundResource(0);
            this.tvDeviceState.setText(R.string.State_working);
            this.tvEvent.setText("");
            this.tvEvent.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
            this.tvDeviceState.setVisibility(0);
            setTvDeviceStateGravity(17);
            this.tvDeviceState.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
            return;
        }
        if (z) {
            UnitUtils.getInstance().getUnitThroughNum(homeDeviceData.getData().getToiletCount(), this.context.getString(R.string.Unit_time), this.context.getString(R.string.Unit_times));
            this.tvDeviceState.setText("");
            this.tvDeviceState.setVisibility(0);
            this.tvDeviceState.setTextColor(CommonUtils.getColorById(R.color.gray));
            setTvDeviceStateGravity(GravityCompat.START);
            this.tvDeviceState.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
        }
    }

    public final /* synthetic */ void lambda$refreshPreview$0(HomeDeviceData homeDeviceData, String str) {
        if (str != null) {
            this.ivVideo.setImageBitmap(CommonUtil.bimapSquareRound(170, BlurUtils.blur(this.context, BitmapFactory.decodeFile(ImageUtils.decryptImageFile(new File(str), homeDeviceData.getData().getAesKey()).getAbsolutePath()), 10, 0.125f)));
        }
    }

    public final /* synthetic */ void lambda$refreshPreview$1(HomeDeviceData homeDeviceData, String str) {
        if (str != null) {
            this.ivVideo.setImageBitmap(CommonUtil.bimapSquareRound(170, BitmapFactory.decodeFile(ImageUtils.decryptImageFile(new File(str), homeDeviceData.getData().getAesKey()).getAbsolutePath())));
        }
    }

    private void checkDeviceNameSize(HomeDeviceData homeDeviceData) {
        if (homeDeviceData.getData().getDeviceShared(27) != null) {
            this.ivRedPoint.setVisibility(8);
            this.tvDeviceName.setMaxLines(1);
            String name = homeDeviceData.getData().getName();
            if (!TextUtils.isEmpty(name)) {
                this.tvDeviceName.setText(name);
                return;
            } else {
                this.tvDeviceName.setText(this.context.getString(R.string.Device_t5_name));
                return;
            }
        }
        String name2 = homeDeviceData.getData().getName();
        if (!TextUtils.isEmpty(name2)) {
            this.tvDeviceName.setText(name2);
        } else {
            this.tvDeviceName.setText(this.context.getString(R.string.Device_t5_name));
        }
    }

    public final boolean breakdown(HomeDeviceStatus homeDeviceStatus) {
        return (homeDeviceStatus == null || TextUtils.isEmpty(homeDeviceStatus.getErrorCode()) || "full".equals(homeDeviceStatus.getErrorCode()) || "hallB".equals(homeDeviceStatus.getErrorCode())) ? false : true;
    }
}

package com.petkit.android.activities.petkitBleDevice.t7.adp;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.jess.arms.utils.ImageUtils;
import com.jess.arms.widget.imageloader.ImageCacheSimple;
import com.petkit.android.activities.common.utils.DeviceCenterUtils;
import com.petkit.android.activities.home.adapter.holder.BaseHomeDeviceViewHolder;
import com.petkit.android.activities.home.adapter.model.HomeDeviceData;
import com.petkit.android.activities.home.utils.BlurUtils;
import com.petkit.android.activities.petkitBleDevice.t7.mode.T7Record;
import com.petkit.android.activities.petkitBleDevice.t7.utils.T7ConstUtils;
import com.petkit.android.activities.petkitBleDevice.t7.utils.T7DataUtils;
import com.petkit.android.activities.petkitBleDevice.t7.utils.T7Utils;
import com.petkit.android.activities.petkitBleDevice.utils.TimeUtils;
import com.petkit.android.utils.CommonUtil;
import com.petkit.android.utils.CommonUtils;
import com.petkit.oversea.R;
import java.io.File;

/* JADX INFO: loaded from: classes5.dex */
public class T7HomeDeviceViewHolder extends BaseHomeDeviceViewHolder {
    public final Context context;
    public final ImageView ivPetEvent;
    public final ImageView ivVideo;
    public final LinearLayout llBottomOffline;
    public final LinearLayout llCameraClose;
    public final LinearLayout llCameraNotInTime;
    public final LinearLayout llNoEvent;
    public final LinearLayout llNoImage;
    public final LinearLayout llOffline;
    public final LinearLayout llPetEvent;
    public final LinearLayout llUpdate;
    public final LinearLayout llWarn;
    public T7Record t7Record;
    public final TextView tvDeviceTag;
    public final TextView tvGrayText;
    public final TextView tvOfflineTime;
    public final TextView tvPetEvent;
    public final TextView tvPowerDown;
    public final TextView tvWarnText;
    public final TextView tvWorkState;

    public T7HomeDeviceViewHolder(@NonNull View view) {
        super(view);
        this.context = view.getContext();
        this.tvDeviceTag = (TextView) view.findViewById(R.id.tv_device_tag);
        this.tvWorkState = (TextView) view.findViewById(R.id.tv_work_state);
        this.llPetEvent = (LinearLayout) view.findViewById(R.id.ll_pet_event);
        this.ivPetEvent = (ImageView) view.findViewById(R.id.iv_pet_event);
        this.tvPetEvent = (TextView) view.findViewById(R.id.tv_pet_event);
        this.llBottomOffline = (LinearLayout) view.findViewById(R.id.ll_bottom_offline);
        this.llUpdate = (LinearLayout) view.findViewById(R.id.ll_update);
        this.llWarn = (LinearLayout) view.findViewById(R.id.ll_warn);
        this.tvWarnText = (TextView) view.findViewById(R.id.tv_warn);
        this.tvGrayText = (TextView) view.findViewById(R.id.tv_gray_text);
        this.ivVideo = (ImageView) view.findViewById(R.id.iv_video);
        this.tvPowerDown = (TextView) view.findViewById(R.id.tv_power_down);
        this.llCameraClose = (LinearLayout) view.findViewById(R.id.ll_camera_close);
        this.llCameraNotInTime = (LinearLayout) view.findViewById(R.id.ll_camera_not_in_time);
        this.llOffline = (LinearLayout) view.findViewById(R.id.ll_offline);
        this.tvOfflineTime = (TextView) view.findViewById(R.id.tv_offline_time);
        this.llNoEvent = (LinearLayout) view.findViewById(R.id.ll_no_event);
        this.llNoImage = (LinearLayout) view.findViewById(R.id.ll_no_image);
    }

    @Override // com.petkit.android.activities.home.adapter.holder.BaseHomeDeviceViewHolder
    public void updateData(HomeDeviceData homeDeviceData) {
        this.t7Record = T7DataUtils.getInstance().getT7RecordById(homeDeviceData.getData().getId());
        checkDeviceNameSize(homeDeviceData);
        if (homeDeviceData.getData().getServiceStatus() == 0) {
            this.tvDeviceTag.setVisibility(4);
        } else if (homeDeviceData.getData().getServiceStatus() == 1) {
            this.tvDeviceTag.setVisibility(0);
            this.tvDeviceTag.setTextColor(CommonUtils.getColorById(R.color.receive_for_free));
            this.tvDeviceTag.setBackgroundResource(R.drawable.gradient_gold_corners_petkitcare_tag_bg);
        } else if (homeDeviceData.getData().getServiceStatus() == 2) {
            this.tvDeviceTag.setVisibility(0);
            this.tvDeviceTag.setTextColor(CommonUtils.getColorById(R.color.home_feeder_bg_gray));
            this.tvDeviceTag.setBackgroundResource(R.drawable.gradient_gray_corners_petkitcare_tag_bg);
        }
        if (homeDeviceData.getData().getDeviceShared(28) != null) {
            this.tvShare.setVisibility(0);
        } else {
            this.tvShare.setVisibility(4);
        }
        this.ivRedPoint.setVisibility(DeviceCenterUtils.isT7NeedOtaById(homeDeviceData.getData().getId()) ? 0 : 8);
        int state = homeDeviceData.getData().getState();
        if (2 == state) {
            deviceOffLine();
        } else if (homeDeviceData.getData().getStatus().getOta() == 1) {
            deviceUpdate();
        } else if (homeDeviceData.getData().getStatus().getPower() == 0) {
            this.llWarn.setVisibility(8);
        } else if (isError(homeDeviceData.getData().getStatus().getErrorCode())) {
            deviceError(homeDeviceData.getData().getStatus().getErrorMsg());
        } else if (homeDeviceData.getData().getStatus().getSandTrayState() == 3) {
            deviceSandError3();
        } else if (isScoopError(homeDeviceData.getData().getStatus().getErrorCode())) {
            deviceSandError5(homeDeviceData.getData().getStatus().getErrorMsg());
        } else if (homeDeviceData.getData().getStatus().getSandTrayState() == 1 && homeDeviceData.getData().getStatus().getWorkState() == null && homeDeviceData.getData().getStatus().getRefreshState() == null) {
            deviceSandError1();
        } else if (homeDeviceData.getData().getStatus().getSandTrayState() == 2) {
            deviceSandError4();
        } else if (homeDeviceData.getData().getStatus().getSprayLeftDays() < 1) {
            deviceSandError2();
        } else if (isCameraError(homeDeviceData.getData().getStatus().getErrorCode()) && notWork(homeDeviceData)) {
            deviceError(homeDeviceData.getData().getStatus().getErrorMsg());
        } else {
            if (homeDeviceData.getData().getStatus().getWorkState() != null) {
                this.tvWorkState.setVisibility(0);
                this.llPetEvent.setVisibility(8);
            } else if (homeDeviceData.getData().getStatus().getRefreshState() != null && homeDeviceData.getData().getStatus().getRefreshState().getWorkProcess() == 1) {
                this.tvWorkState.setVisibility(0);
                this.llPetEvent.setVisibility(8);
            } else {
                this.tvWorkState.setVisibility(8);
                if (homeDeviceData.getData().getEvent() != null && homeDeviceData.getData().getEvent().getEventType() == 10) {
                    this.llPetEvent.setVisibility(0);
                    this.ivPetEvent.setVisibility(0);
                    this.ivPetEvent.setImageResource(R.drawable.card_pet_toilet_icon);
                    this.tvPetEvent.setVisibility(0);
                    this.tvPetEvent.setText(getPetToiletEvent(homeDeviceData.getData().getEvent().getTimestamp()));
                } else if (homeDeviceData.getData().getEvent() != null && homeDeviceData.getData().getEvent().getEventType() == 20) {
                    this.llPetEvent.setVisibility(0);
                    this.ivPetEvent.setVisibility(0);
                    this.ivPetEvent.setImageResource(R.drawable.card_pet_appear_icon);
                    this.tvPetEvent.setVisibility(0);
                    this.tvPetEvent.setText(getPetEvent(homeDeviceData.getData().getEvent().getTimestamp()));
                } else {
                    this.llPetEvent.setVisibility(8);
                }
            }
            this.llUpdate.setVisibility(8);
            this.llBottomOffline.setVisibility(8);
            this.llWarn.setVisibility(8);
        }
        initUi();
        if (2 == state) {
            this.ivVideo.setImageResource(R.color.transparent);
            this.llOffline.setVisibility(0);
            this.tvOfflineTime.setText(this.context.getString(R.string.Toilet_is_offline));
            return;
        }
        if (homeDeviceData.getData().getStatus().getPower() == 0) {
            this.ivVideo.setImageResource(R.color.dark_black);
            this.tvPowerDown.setVisibility(0);
            return;
        }
        if (homeDeviceData.getData().getSettings() != null && homeDeviceData.getData().getSettings().getCamera() == 0) {
            this.ivVideo.setImageResource(R.color.dark_black);
            this.llCameraClose.setVisibility(0);
            return;
        }
        if (homeDeviceData.getData().getStatus().getCameraStatus().intValue() == 0) {
            this.ivVideo.setImageResource(R.color.dark_black);
            this.llCameraNotInTime.setVisibility(0);
            return;
        }
        if (homeDeviceData.getData().getSettings().getPreLive() == 0) {
            setImageView(homeDeviceData, true);
            return;
        }
        if (TextUtils.isEmpty(homeDeviceData.getData().getPreview())) {
            this.ivVideo.setImageResource(R.color.transparent);
            if (homeDeviceData.getData().getEvent() != null) {
                this.llNoImage.setVisibility(0);
                return;
            } else {
                this.llNoEvent.setVisibility(0);
                return;
            }
        }
        if (TextUtils.isEmpty(homeDeviceData.getData().getPreview())) {
            return;
        }
        setImageView(homeDeviceData, false);
    }

    public final void deviceOffLine() {
        this.tvWorkState.setVisibility(8);
        this.llPetEvent.setVisibility(8);
        this.llUpdate.setVisibility(8);
        this.llWarn.setVisibility(8);
        this.llBottomOffline.setVisibility(0);
        this.tvGrayText.setText(R.string.Offline);
    }

    private void deviceUpdate() {
        this.tvWorkState.setVisibility(8);
        this.llPetEvent.setVisibility(8);
        this.llUpdate.setVisibility(0);
        this.llWarn.setVisibility(8);
        this.llBottomOffline.setVisibility(8);
    }

    public final void deviceError(String str) {
        this.tvWorkState.setVisibility(8);
        this.llPetEvent.setVisibility(8);
        this.llUpdate.setVisibility(8);
        this.llBottomOffline.setVisibility(8);
        this.llWarn.setVisibility(0);
        this.tvWarnText.setText(str);
    }

    public final void deviceSandError1() {
        this.tvWorkState.setVisibility(8);
        this.llPetEvent.setVisibility(8);
        this.llUpdate.setVisibility(8);
        this.llBottomOffline.setVisibility(8);
        this.llWarn.setVisibility(0);
        this.tvWarnText.setText(R.string.T7_crystal_sand_disc_lapse_err);
    }

    public final void deviceSandError4() {
        this.tvWorkState.setVisibility(8);
        this.llPetEvent.setVisibility(8);
        this.llUpdate.setVisibility(8);
        this.llBottomOffline.setVisibility(8);
        this.llWarn.setVisibility(0);
        this.tvWarnText.setText(R.string.Crystal_sand_disc_not_available_card);
    }

    public final void deviceSandError2() {
        this.tvWorkState.setVisibility(8);
        this.llPetEvent.setVisibility(8);
        this.llUpdate.setVisibility(8);
        this.llBottomOffline.setVisibility(8);
        this.llWarn.setVisibility(0);
        this.tvWarnText.setText(R.string.N60_lack);
    }

    public final void deviceSandError3() {
        this.tvWorkState.setVisibility(8);
        this.llPetEvent.setVisibility(8);
        this.llUpdate.setVisibility(8);
        this.llBottomOffline.setVisibility(8);
        this.llWarn.setVisibility(0);
        this.tvWarnText.setText(R.string.T7_crystal_sand_disc_not_install_err);
    }

    public final void deviceSandError5(String str) {
        this.tvWorkState.setVisibility(8);
        this.llPetEvent.setVisibility(8);
        this.llUpdate.setVisibility(8);
        this.llBottomOffline.setVisibility(8);
        this.llWarn.setVisibility(0);
        this.tvWarnText.setText(str);
    }

    public final void initUi() {
        this.tvPowerDown.setVisibility(8);
        this.llOffline.setVisibility(8);
        this.llCameraNotInTime.setVisibility(8);
        this.llCameraClose.setVisibility(8);
        this.llNoEvent.setVisibility(8);
        this.llNoImage.setVisibility(8);
    }

    private void checkDeviceNameSize(HomeDeviceData homeDeviceData) {
        if (homeDeviceData.getData().getDeviceShared(28) != null) {
            this.ivRedPoint.setVisibility(8);
            String name = homeDeviceData.getData().getName();
            if (!TextUtils.isEmpty(name)) {
                this.tvDeviceName.setText(name);
                return;
            } else {
                this.tvDeviceName.setText(this.context.getString(R.string.Device_t7_name));
                return;
            }
        }
        String name2 = homeDeviceData.getData().getName();
        if (!TextUtils.isEmpty(name2)) {
            this.tvDeviceName.setText(name2);
        } else {
            this.tvDeviceName.setText(this.context.getString(R.string.Device_t7_name));
        }
    }

    public final boolean isError(String str) {
        if (TextUtils.isEmpty(str) || str.equals(T7ConstUtils.T7_ERROR_CODE_SCOOP_M) || str.equals(T7ConstUtils.T7_ERROR_CODE_SCOOP_L) || str.equals(T7ConstUtils.T7_ERROR_CODE_LITTER_U) || str.equals(T7ConstUtils.T7_ERROR_CODE_LITTER_E) || str.equals(T7ConstUtils.T7_ERROR_CODE_LITTER_I)) {
            return false;
        }
        return !str.equals("camera");
    }

    public final boolean isScoopError(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return str.equals(T7ConstUtils.T7_ERROR_CODE_SCOOP_M) || str.equals(T7ConstUtils.T7_ERROR_CODE_SCOOP_L);
    }

    public final boolean isCameraError(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return str.equals("camera");
    }

    public final boolean notWork(HomeDeviceData homeDeviceData) {
        return homeDeviceData.getData().getStatus().getWorkState() == null && homeDeviceData.getData().getStatus().getRefreshState() == null;
    }

    public final String getPetToiletEvent(long j) {
        TimeUtils timeUtils = TimeUtils.getInstance();
        Context context = this.context;
        TimeUtils timeUtils2 = TimeUtils.getInstance();
        T7Record t7Record = this.t7Record;
        return timeUtils.secondsToTimeStringWithUnit(context, timeUtils2.timeStampToSeconds(j, t7Record == null ? null : t7Record.getActualTimeZone())) + " " + this.context.getResources().getString(R.string.T6_toilet_event_coming);
    }

    public final String getPetEvent(long j) {
        TimeUtils timeUtils = TimeUtils.getInstance();
        Context context = this.context;
        TimeUtils timeUtils2 = TimeUtils.getInstance();
        T7Record t7Record = this.t7Record;
        return timeUtils.secondsToTimeStringWithUnit(context, timeUtils2.timeStampToSeconds(j, t7Record == null ? null : t7Record.getActualTimeZone())) + " " + this.context.getResources().getString(R.string.Cat_appeared);
    }

    public final void setImageView(final HomeDeviceData homeDeviceData, final boolean z) {
        if (TextUtils.isEmpty(homeDeviceData.getData().getPreview())) {
            this.ivVideo.setImageResource(R.color.transparent);
            if (homeDeviceData.getData().getEvent() != null) {
                this.llNoImage.setVisibility(0);
                return;
            } else {
                this.llNoEvent.setVisibility(0);
                return;
            }
        }
        new ImageCacheSimple(CommonUtils.getAppContext()).getImageCachePath(CommonUtil.httpToHttps(homeDeviceData.getData().getPreview()), new ImageCacheSimple.IGetImageCacheListener() { // from class: com.petkit.android.activities.petkitBleDevice.t7.adp.T7HomeDeviceViewHolder$$ExternalSyntheticLambda0
            @Override // com.jess.arms.widget.imageloader.ImageCacheSimple.IGetImageCacheListener
            public final void onCachePath(String str) {
                this.f$0.lambda$setImageView$0(homeDeviceData, z, str);
            }
        });
    }

    public final /* synthetic */ void lambda$setImageView$0(HomeDeviceData homeDeviceData, boolean z, String str) {
        if (str != null) {
            File fileDecryptImageFile = ImageUtils.decryptImageFile(new File(str), homeDeviceData.getData().getAesKey());
            if (fileDecryptImageFile != null) {
                Bitmap rotateBitmap = T7Utils.getRotateBitmap(fileDecryptImageFile.getAbsolutePath(), homeDeviceData.getData().getPreview());
                if (z) {
                    this.ivVideo.setImageBitmap(BlurUtils.blur(this.context, rotateBitmap, 10, 0.125f));
                    return;
                } else {
                    this.ivVideo.setImageBitmap(rotateBitmap);
                    return;
                }
            }
            this.ivVideo.setImageResource(R.color.transparent);
            if (homeDeviceData.getData().getEvent() != null) {
                this.llNoImage.setVisibility(0);
                return;
            } else {
                this.llNoEvent.setVisibility(0);
                return;
            }
        }
        this.ivVideo.setImageResource(R.color.transparent);
        if (homeDeviceData.getData().getEvent() != null) {
            this.llNoImage.setVisibility(0);
        } else {
            this.llNoEvent.setVisibility(0);
        }
    }
}

package com.petkit.android.activities.petkitBleDevice.t7.adp;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Group;
import com.jess.arms.utils.ImageUtils;
import com.jess.arms.widget.imageloader.ImageCacheSimple;
import com.petkit.android.activities.common.utils.DeviceCenterUtils;
import com.petkit.android.activities.home.NewCardOnClickListener;
import com.petkit.android.activities.home.adapter.model.HomeDeviceData;
import com.petkit.android.activities.home.adapter.newholder.BaseHomeLargeDeviceWithControlViewHolder;
import com.petkit.android.activities.home.utils.BlurUtils;
import com.petkit.android.activities.petkitBleDevice.mode.TimeViewResult;
import com.petkit.android.activities.petkitBleDevice.t7.mode.T7Record;
import com.petkit.android.activities.petkitBleDevice.t7.utils.T7ConstUtils;
import com.petkit.android.activities.petkitBleDevice.t7.utils.T7DataUtils;
import com.petkit.android.activities.petkitBleDevice.t7.utils.T7Utils;
import com.petkit.android.activities.petkitBleDevice.utils.TimeUtils;
import com.petkit.android.utils.CommonUtil;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.DateUtil;
import com.petkit.oversea.R;
import java.io.File;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes5.dex */
public class T7HomeLargeDeviceViewHolder extends BaseHomeLargeDeviceWithControlViewHolder {
    public final Context context;
    public final Group groupShare;
    public final ImageView ivControlDevice;
    public final ImageView ivPetEvent;
    public final ImageView ivTopShadow;
    public final ImageView ivVideo;
    public final LinearLayout llCameraClose;
    public final LinearLayout llCameraNotInTime;
    public final LinearLayout llControlView;
    public final LinearLayout llNoEvent;
    public final LinearLayout llNoImage;
    public final LinearLayout llOffline;
    public T7Record t7Record;
    public final TextView tvConsumableOneContent;
    public final TextView tvConsumableTwoContent;
    public final TextView tvDeviceTag;
    public final TextView tvOfflineTime;
    public final TextView tvPetEvent;
    public final TextView tvPowerDown;
    public final TextView tvShareLine;

    public T7HomeLargeDeviceViewHolder(@NonNull View view) {
        super(view);
        this.context = view.getContext();
        this.tvDeviceTag = (TextView) view.findViewById(R.id.tv_device_tag);
        this.groupShare = (Group) view.findViewById(R.id.group_share);
        this.tvShareLine = (TextView) view.findViewById(R.id.tv_share_line);
        this.tvConsumableOneContent = (TextView) view.findViewById(R.id.tv_consumable_one_content);
        this.tvConsumableTwoContent = (TextView) view.findViewById(R.id.tv_consumable_two_content);
        this.ivControlDevice = (ImageView) view.findViewById(R.id.iv_control_device);
        this.ivPetEvent = (ImageView) view.findViewById(R.id.iv_pet_event);
        this.tvPetEvent = (TextView) view.findViewById(R.id.tv_pet_event);
        this.ivVideo = (ImageView) view.findViewById(R.id.iv_video);
        this.tvPowerDown = (TextView) view.findViewById(R.id.tv_power_down);
        this.llCameraClose = (LinearLayout) view.findViewById(R.id.ll_camera_close);
        this.llCameraNotInTime = (LinearLayout) view.findViewById(R.id.ll_camera_not_in_time);
        this.llOffline = (LinearLayout) view.findViewById(R.id.ll_offline);
        this.tvOfflineTime = (TextView) view.findViewById(R.id.tv_offline_time);
        this.llNoEvent = (LinearLayout) view.findViewById(R.id.ll_no_event);
        this.llControlView = (LinearLayout) view.findViewById(R.id.ll_control_view);
        this.llNoImage = (LinearLayout) view.findViewById(R.id.ll_no_image);
        this.ivTopShadow = (ImageView) view.findViewById(R.id.iv_top_shadow);
    }

    @Override // com.petkit.android.activities.home.adapter.newholder.BaseHomeLargeDeviceViewHolder
    public void updateData(final HomeDeviceData homeDeviceData, int i, final NewCardOnClickListener newCardOnClickListener) {
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
        this.groupShare.setVisibility(homeDeviceData.getShared() == 1 ? 0 : 8);
        this.ivRedPoint.setVisibility(DeviceCenterUtils.isT7NeedOtaById(homeDeviceData.getData().getId()) ? 0 : 8);
        initUi();
        int state = homeDeviceData.getData().getState();
        if (2 == state) {
            this.tvDeviceState.setText(R.string.Offline);
            this.tvDeviceState.setTextColor(this.context.getResources().getColor(R.color.white));
            setUnconnectDeviceState();
        } else if (homeDeviceData.getData().getStatus().getOta() == 1) {
            this.tvDeviceState.setText(R.string.Card_device_up);
            setUpdatingDeviceState();
        } else if (homeDeviceData.getData().getStatus().getPower() == 0) {
            this.rlDeviceState.setVisibility(8);
        } else if (isError(homeDeviceData.getData().getStatus().getErrorCode())) {
            setWarnDeviceState();
            this.tvDeviceState.setText(R.string.Cozy_error);
        } else if (homeDeviceData.getData().getStatus().getSandTrayState() == 3) {
            setWarnDeviceState();
            this.tvDeviceState.setText(R.string.T7_crystal_sand_disc_not_install_err);
        } else if (isScoopError(homeDeviceData.getData().getStatus().getErrorCode())) {
            setWarnDeviceState();
            this.tvDeviceState.setText(R.string.T7_scoop_can_not_push);
        } else if (homeDeviceData.getData().getStatus().getSandTrayState() == 1) {
            setWarnDeviceState();
            this.tvDeviceState.setText(R.string.T7_crystal_sand_disc_lapse_err);
        } else if (homeDeviceData.getData().getStatus().getSandTrayState() == 2) {
            setWarnDeviceState();
            this.tvDeviceState.setText(R.string.Crystal_sand_disc_not_available_card);
        } else if (isCameraError(homeDeviceData.getData().getStatus().getErrorCode()) && notWork(homeDeviceData)) {
            setWarnDeviceState();
            this.tvDeviceState.setText(R.string.Cozy_error);
        } else if (homeDeviceData.getData().getStatus().getSprayLeftDays() < 1) {
            setWarnDeviceState();
            this.tvDeviceState.setText(R.string.N60_lack);
        } else {
            this.rlDeviceState.setVisibility(8);
        }
        if (homeDeviceData.getData().getStatus().getWorkState() != null) {
            this.ivPetEvent.setVisibility(8);
            this.tvPetEvent.setVisibility(0);
            this.tvPetEvent.setText(R.string.State_working);
            this.tvShareLine.setVisibility(this.groupShare.getVisibility() == 0 ? 0 : 8);
        } else if (homeDeviceData.getData().getStatus().getRefreshState() != null && homeDeviceData.getData().getStatus().getRefreshState().getWorkProcess() == 1) {
            this.ivPetEvent.setVisibility(8);
            this.tvPetEvent.setVisibility(0);
            this.tvPetEvent.setText(R.string.State_working);
            this.tvShareLine.setVisibility(this.groupShare.getVisibility() == 0 ? 0 : 8);
        } else if (showEvent(homeDeviceData)) {
            if (homeDeviceData.getData().getEvent() != null && homeDeviceData.getData().getEvent().getEventType() == 10) {
                this.ivPetEvent.setVisibility(0);
                this.tvPetEvent.setVisibility(0);
                this.ivPetEvent.setImageResource(R.drawable.card_pet_toilet_icon);
                this.tvPetEvent.setText(getPetToiletEvent(homeDeviceData.getData().getEvent().getTimestamp()));
                this.tvShareLine.setVisibility(this.groupShare.getVisibility() == 0 ? 0 : 8);
            } else if (homeDeviceData.getData().getEvent() != null && homeDeviceData.getData().getEvent().getEventType() == 20) {
                this.ivPetEvent.setVisibility(0);
                this.tvPetEvent.setVisibility(0);
                this.ivPetEvent.setImageResource(R.drawable.card_pet_appear_icon);
                this.tvPetEvent.setText(getPetEvent(homeDeviceData.getData().getEvent().getTimestamp()));
                this.tvShareLine.setVisibility(this.groupShare.getVisibility() == 0 ? 0 : 8);
            } else {
                this.ivPetEvent.setVisibility(8);
                this.tvPetEvent.setVisibility(8);
                this.tvShareLine.setVisibility(8);
            }
        } else {
            this.ivPetEvent.setVisibility(8);
            this.tvPetEvent.setVisibility(8);
            this.tvShareLine.setVisibility(8);
        }
        if (2 == state) {
            this.ivVideo.setImageResource(R.color.transparent);
            this.llOffline.setVisibility(0);
            T7Record t7Record = this.t7Record;
            if (t7Record == null || t7Record.getState() == null || this.t7Record.getState().getOfflineTime() <= 0) {
                this.tvOfflineTime.setText(this.context.getString(R.string.Toilet_is_offline));
            } else {
                this.tvOfflineTime.setText(this.context.getString(R.string.Offline_time, DateUtil.getDateFormatShortTimeShortString(DateUtil.long2str(this.t7Record.getState().getOfflineTime(), "yyyy-MM-dd'T'HH:mm:ss.SSSZ"))));
            }
        } else if (homeDeviceData.getData().getStatus().getPower() == 0) {
            this.ivVideo.setImageResource(R.color.dark_black);
            this.tvPowerDown.setVisibility(0);
        } else if (homeDeviceData.getData().getSettings() != null && homeDeviceData.getData().getSettings().getCamera() == 0) {
            this.ivVideo.setImageResource(R.color.dark_black);
            this.llCameraClose.setVisibility(0);
        } else if (homeDeviceData.getData().getStatus().getCameraStatus().intValue() == 0) {
            this.ivVideo.setImageResource(R.color.dark_black);
            this.llCameraNotInTime.setVisibility(0);
        } else if (homeDeviceData.getData().getSettings().getPreLive() == 0) {
            setImageView(homeDeviceData, true);
            this.ivTopShadow.setVisibility(0);
        } else if (TextUtils.isEmpty(homeDeviceData.getData().getPreview())) {
            this.ivVideo.setImageResource(R.color.transparent);
            if (homeDeviceData.getData().getEvent() != null) {
                this.llNoImage.setVisibility(0);
            } else {
                this.llNoEvent.setVisibility(0);
            }
        } else if (!TextUtils.isEmpty(homeDeviceData.getData().getPreview())) {
            setImageView(homeDeviceData, false);
            this.ivTopShadow.setVisibility(0);
        }
        this.tvConsumableOneContent.setText(String.valueOf(getToiletTimes(homeDeviceData.getData().getData())));
        int toiletTimeAvg = homeDeviceData.getData().getToiletTimeAvg();
        if (toiletTimeAvg > 0) {
            this.tvConsumableTwoContent.setText(T7Utils.getToiletTimes(toiletTimeAvg, this.context.getResources().getColor(R.color.white), false));
        } else {
            this.tvConsumableTwoContent.setText("0");
        }
        if (homeDeviceData.getData().getState() == 2) {
            this.llControlView.setAlpha(0.35f);
        } else {
            this.llControlView.setAlpha(1.0f);
        }
        int controlSettings = homeDeviceData.getData().getControlSettings();
        if (controlSettings != 1) {
            if (controlSettings == 2) {
                if (homeDeviceData.getData().getSettings().getCamera() == 0) {
                    this.ivControl.setImageResource(canCamera(homeDeviceData) ? R.drawable.camera_off_icon : R.drawable.camera_off_gray_icon);
                } else if (homeDeviceData.getData().getStatus().getCameraStatus().intValue() == 0) {
                    this.ivControl.setImageResource(canCamera(homeDeviceData) ? R.drawable.camera_off_icon : R.drawable.camera_off_gray_icon);
                } else {
                    this.ivControl.setImageResource(canCamera(homeDeviceData) ? R.drawable.camera_on_icon : R.drawable.camera_on_gray_icon);
                }
            } else {
                this.ivControl.setImageResource(R.drawable.edit_control_icon);
            }
        } else if (canClean(homeDeviceData)) {
            if (homeDeviceData.getData().getStatus().getWorkState() != null) {
                if (homeDeviceData.getData().getStatus().getWorkState().getWorkMode() == 0) {
                    this.ivControlDevice.setImageResource(R.drawable.card_t6_cleaning_up_gray_icon);
                } else {
                    this.ivControlDevice.setImageResource(R.drawable.card_t6_clean_up_gray_icon);
                }
            } else {
                this.ivControlDevice.setImageResource(R.drawable.card_t6_clean_up_icon);
            }
        } else {
            this.ivControlDevice.setImageResource(R.drawable.card_t6_clean_up_gray_icon);
        }
        this.rlControl.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t7.adp.T7HomeLargeDeviceViewHolder$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$updateData$0(homeDeviceData, newCardOnClickListener, view);
            }
        });
    }

    public final /* synthetic */ void lambda$updateData$0(HomeDeviceData homeDeviceData, NewCardOnClickListener newCardOnClickListener, View view) {
        if (homeDeviceData.getData().getControlSettings() == 1) {
            if (canClean(homeDeviceData) && newCardOnClickListener != null) {
                newCardOnClickListener.onControlDeviceClick(homeDeviceData);
                return;
            }
            return;
        }
        if (homeDeviceData.getData().getControlSettings() != 2) {
            if (newCardOnClickListener != null) {
                newCardOnClickListener.onControlDeviceClick(homeDeviceData);
            }
        } else if (canCamera(homeDeviceData) && newCardOnClickListener != null) {
            newCardOnClickListener.onControlDeviceClick(homeDeviceData);
        }
    }

    private void initUi() {
        this.tvPowerDown.setVisibility(8);
        this.llOffline.setVisibility(8);
        this.llCameraNotInTime.setVisibility(8);
        this.llCameraClose.setVisibility(8);
        this.llNoEvent.setVisibility(8);
        this.llNoImage.setVisibility(8);
        this.ivPetEvent.setVisibility(8);
        this.tvPetEvent.setVisibility(8);
        this.tvShareLine.setVisibility(8);
        this.ivTopShadow.setVisibility(8);
    }

    public final boolean canClean(HomeDeviceData homeDeviceData) {
        int state;
        if (homeDeviceData == null || homeDeviceData.getData() == null || (state = homeDeviceData.getData().getState()) == 2 || homeDeviceData.getData().getStatus().getPower() == 0 || homeDeviceData.getData().getStatus().getOta() == 1) {
            return false;
        }
        return ((state == 4 && !T7Utils.bottomFilter(homeDeviceData.getData().getStatus().getErrorCode())) || homeDeviceData.getData().getStatus().getSandTrayState() == 1 || homeDeviceData.getData().getStatus().getSandTrayState() == 3) ? false : true;
    }

    private boolean canCamera(HomeDeviceData homeDeviceData) {
        int state;
        if (homeDeviceData == null || homeDeviceData.getData() == null || (state = homeDeviceData.getData().getState()) == 2 || homeDeviceData.getData().getStatus().getPower() == 0 || homeDeviceData.getData().getStatus().getOta() == 1 || isCameraError(homeDeviceData.getData().getStatus().getErrorCode())) {
            return false;
        }
        return T7Utils.bottomFilter(homeDeviceData.getData().getStatus().getErrorCode()) || state != 4;
    }

    private void checkDeviceNameSize(HomeDeviceData homeDeviceData) {
        this.groupShare.setVisibility(homeDeviceData.getShared() == 1 ? 0 : 8);
        if (homeDeviceData.getData().getDeviceShared(28) != null) {
            this.groupShare.setVisibility(0);
            this.ivRedPoint.setVisibility(8);
            this.tvDeviceName.setMaxLines(1);
            String name = homeDeviceData.getData().getName();
            if (!TextUtils.isEmpty(name)) {
                this.tvDeviceName.setText(name);
                return;
            } else {
                this.tvDeviceName.setText(this.context.getString(R.string.Device_t7_name));
                return;
            }
        }
        this.groupShare.setVisibility(8);
        String name2 = homeDeviceData.getData().getName();
        if (!TextUtils.isEmpty(name2)) {
            this.tvDeviceName.setText(name2);
        } else {
            this.tvDeviceName.setText(this.context.getString(R.string.Device_t7_name));
        }
    }

    private boolean isError(String str) {
        if (TextUtils.isEmpty(str) || str.equals(T7ConstUtils.T7_ERROR_CODE_SCOOP_M) || str.equals(T7ConstUtils.T7_ERROR_CODE_SCOOP_L) || str.equals(T7ConstUtils.T7_ERROR_CODE_LITTER_U) || str.equals(T7ConstUtils.T7_ERROR_CODE_LITTER_E) || str.equals(T7ConstUtils.T7_ERROR_CODE_LITTER_I)) {
            return false;
        }
        return !str.equals("camera");
    }

    private boolean isScoopError(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return str.equals(T7ConstUtils.T7_ERROR_CODE_SCOOP_M) || str.equals(T7ConstUtils.T7_ERROR_CODE_SCOOP_L);
    }

    private boolean isCameraError(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return str.equals("camera");
    }

    private boolean notWork(HomeDeviceData homeDeviceData) {
        return homeDeviceData.getData().getStatus().getWorkState() == null && homeDeviceData.getData().getStatus().getRefreshState() == null;
    }

    private boolean showEvent(HomeDeviceData homeDeviceData) {
        if (homeDeviceData == null || homeDeviceData.getData() == null || homeDeviceData.getData().getStatus() == null || 2 == homeDeviceData.getData().getState() || homeDeviceData.getData().getStatus().getOta() == 1 || homeDeviceData.getData().getStatus().getPower() == 0) {
            return false;
        }
        return TextUtils.isEmpty(homeDeviceData.getData().getStatus().getErrorCode());
    }

    public final int getToiletTimes(ArrayList<Integer> arrayList) {
        ArrayList arrayList2 = new ArrayList();
        if (arrayList != null) {
            for (int i = 0; i < arrayList.size(); i++) {
                arrayList2.add(new TimeViewResult(arrayList.get(i).intValue() / 60, arrayList.get(i).intValue() / 60));
            }
        }
        return arrayList2.size();
    }

    private String getPetToiletEvent(long j) {
        TimeUtils timeUtils = TimeUtils.getInstance();
        Context context = this.context;
        T7Record t7Record = this.t7Record;
        return timeUtils.stampToDate(context, j, t7Record == null ? null : t7Record.getActualTimeZone()) + " " + this.context.getResources().getString(R.string.T6_toilet_event_coming);
    }

    private String getPetEvent(long j) {
        TimeUtils timeUtils = TimeUtils.getInstance();
        Context context = this.context;
        T7Record t7Record = this.t7Record;
        return timeUtils.stampToDate(context, j, t7Record == null ? null : t7Record.getActualTimeZone()) + " " + this.context.getResources().getString(R.string.Cat_appeared);
    }

    private void setImageView(final HomeDeviceData homeDeviceData, final boolean z) {
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
        new ImageCacheSimple(CommonUtils.getAppContext()).getImageCachePath(CommonUtil.httpToHttps(homeDeviceData.getData().getPreview()), new ImageCacheSimple.IGetImageCacheListener() { // from class: com.petkit.android.activities.petkitBleDevice.t7.adp.T7HomeLargeDeviceViewHolder$$ExternalSyntheticLambda0
            @Override // com.jess.arms.widget.imageloader.ImageCacheSimple.IGetImageCacheListener
            public final void onCachePath(String str) {
                this.f$0.lambda$setImageView$1(homeDeviceData, z, str);
            }
        });
    }

    public final /* synthetic */ void lambda$setImageView$1(HomeDeviceData homeDeviceData, boolean z, String str) {
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

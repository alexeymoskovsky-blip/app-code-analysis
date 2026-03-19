package com.petkit.android.activities.appwidget.mode.small;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextUtils;
import com.alibaba.sdk.android.oss.common.RequestParameters;
import com.jess.arms.utils.ImageUtils;
import com.jess.arms.widget.imageloader.ImageCacheSimple;
import com.petkit.android.activities.common.utils.DeviceCenterUtils;
import com.petkit.android.activities.home.adapter.model.HomeDeviceData;
import com.petkit.android.activities.home.utils.BlurUtils;
import com.petkit.android.activities.petkitBleDevice.t7.mode.T7Record;
import com.petkit.android.activities.petkitBleDevice.t7.utils.T7DataUtils;
import com.petkit.android.activities.petkitBleDevice.t7.utils.T7Utils;
import com.petkit.android.activities.petkitBleDevice.utils.TimeUtils;
import com.petkit.android.api.ErrorCode;
import com.petkit.android.utils.CommonUtil;
import com.petkit.android.utils.CommonUtils;
import com.petkit.oversea.R;
import java.io.File;

/* JADX INFO: loaded from: classes3.dex */
public class T7SmallWidgetView extends BaseSmallWidgetView {

    @SuppressLint({"StaticFieldLeak"})
    public static class T7SmallWidgetViewHolder {
        public static final T7SmallWidgetView instance = new T7SmallWidgetView();
    }

    public static T7SmallWidgetView getInstance() {
        return T7SmallWidgetViewHolder.instance;
    }

    @Override // com.petkit.android.activities.appwidget.mode.small.BaseSmallWidgetView
    public int initRemoteView() {
        return R.layout.layout_app_small_widget_t7;
    }

    @Override // com.petkit.android.activities.appwidget.mode.small.BaseSmallWidgetView
    public void setDeviceData(Context context, HomeDeviceData homeDeviceData) {
        this.lightStyle = true;
        if (homeDeviceData == null || homeDeviceData.getData() == null) {
            setDeviceError(RequestParameters.SUBRESOURCE_DELETE);
            return;
        }
        setDeviceNormal();
        setRedPointViewVisible(DeviceCenterUtils.isT6NeedOtaById(homeDeviceData.getId()) ? 0 : 8);
        String name = homeDeviceData.getData().getName();
        if (!TextUtils.isEmpty(name)) {
            setDeviceName(name);
        } else {
            setDeviceName(this.context.getResources().getString(R.string.T7_name_default));
        }
        boolean z = homeDeviceData.getData().getDeviceShared(28) != null;
        this.remoteViews.setTextViewText(R.id.tv_share, this.context.getResources().getString(R.string.Mate_share_setting));
        this.remoteViews.setViewVisibility(R.id.tv_share, z ? 0 : 8);
        if ((homeDeviceData.getData().getStatus() == null ? -1 : homeDeviceData.getData().getStatus().getOverall()) == 2) {
            offline();
            return;
        }
        if (homeDeviceData.getData().getStatus().getPower() == 0) {
            powerOff();
            return;
        }
        if (homeDeviceData.getData().getSettings().getCamera() == 0) {
            cameraOff();
            return;
        }
        if (homeDeviceData.getData().getStatus().getCameraStatus().intValue() == 0) {
            cameraClose();
            return;
        }
        this.remoteViews.setViewVisibility(R.id.iv_offline, 8);
        this.remoteViews.setViewVisibility(R.id.tv_camera_off, 8);
        if (TextUtils.isEmpty(homeDeviceData.getData().getPreview())) {
            this.remoteViews.setImageViewResource(R.id.iv_video, R.drawable.solid_dark_black_16);
            this.remoteViews.setViewVisibility(R.id.tv_camera_off, 0);
            this.remoteViews.setTextViewText(R.id.tv_camera_off, this.context.getResources().getString(R.string.No_video));
        } else {
            showImage(homeDeviceData.getData().getPreview(), homeDeviceData.getData().getAesKey(), homeDeviceData.getData().getSettings().getPreLive() == 0);
        }
        T7Record t7RecordById = T7DataUtils.getInstance().getT7RecordById(homeDeviceData.getData().getId());
        if (homeDeviceData.getData().getEvent() != null) {
            String strStampToDate = TimeUtils.getInstance().stampToDate(this.context, homeDeviceData.getData().getEvent().getTimestamp(), t7RecordById == null ? null : t7RecordById.getActualTimeZone());
            int eventType = homeDeviceData.getData().getEvent().getEventType();
            if (eventType == 10) {
                this.remoteViews.setTextViewText(R.id.tv_device_state, strStampToDate + " " + CommonUtils.getAppContext().getResources().getString(R.string.T6_toilet_event_coming));
                this.remoteViews.setImageViewResource(R.id.iv_cat_appear, R.drawable.card_pet_toilet_icon);
                this.remoteViews.setViewVisibility(R.id.tv_device_state, 0);
                this.remoteViews.setViewVisibility(R.id.iv_cat_appear, 0);
                return;
            }
            if (eventType != 20) {
                return;
            }
            this.remoteViews.setTextViewText(R.id.tv_device_state, strStampToDate + " " + CommonUtils.getAppContext().getResources().getString(R.string.D4sh_device_record_pet_walk_around));
            this.remoteViews.setImageViewResource(R.id.iv_cat_appear, R.drawable.card_pet_appear_icon);
            this.remoteViews.setViewVisibility(R.id.tv_device_state, 0);
            this.remoteViews.setViewVisibility(R.id.iv_cat_appear, 0);
            return;
        }
        this.remoteViews.setViewVisibility(R.id.tv_device_state, 8);
        this.remoteViews.setViewVisibility(R.id.iv_cat_appear, 8);
        this.remoteViews.setViewVisibility(R.id.tv_camera_off, 0);
        this.remoteViews.setTextViewText(R.id.tv_camera_off, this.context.getResources().getString(R.string.No_video));
    }

    private void offline() {
        this.remoteViews.setImageViewResource(R.id.iv_video, R.drawable.solid_dark_black_16);
        this.remoteViews.setViewVisibility(R.id.iv_offline, 0);
        this.remoteViews.setViewVisibility(R.id.tv_camera_off, 8);
        this.remoteViews.setViewVisibility(R.id.tv_device_state, 8);
        this.remoteViews.setViewVisibility(R.id.iv_cat_appear, 8);
    }

    private void powerOff() {
        this.remoteViews.setImageViewResource(R.id.iv_video, R.drawable.solid_dark_black_16);
        this.remoteViews.setViewVisibility(R.id.iv_offline, 8);
        this.remoteViews.setViewVisibility(R.id.tv_camera_off, 0);
        this.remoteViews.setTextViewText(R.id.tv_camera_off, this.context.getResources().getString(R.string.T6_Power_off));
        this.remoteViews.setViewVisibility(R.id.tv_device_state, 8);
        this.remoteViews.setViewVisibility(R.id.iv_cat_appear, 8);
    }

    private void cameraOff() {
        this.remoteViews.setImageViewResource(R.id.iv_video, R.drawable.solid_dark_black_16);
        this.remoteViews.setViewVisibility(R.id.iv_offline, 8);
        this.remoteViews.setViewVisibility(R.id.tv_camera_off, 0);
        this.remoteViews.setTextViewText(R.id.tv_camera_off, this.context.getResources().getString(R.string.Camera_is_off));
        this.remoteViews.setViewVisibility(R.id.tv_device_state, 8);
        this.remoteViews.setViewVisibility(R.id.iv_cat_appear, 8);
    }

    private void cameraClose() {
        this.remoteViews.setImageViewResource(R.id.iv_video, R.drawable.solid_dark_black_16);
        this.remoteViews.setViewVisibility(R.id.tv_camera_off, 0);
        this.remoteViews.setViewVisibility(R.id.iv_offline, 8);
        this.remoteViews.setTextViewText(R.id.tv_camera_off, this.context.getResources().getString(R.string.Camera_off_as_planed));
        this.remoteViews.setViewVisibility(R.id.tv_device_state, 8);
        this.remoteViews.setViewVisibility(R.id.iv_cat_appear, 8);
    }

    private void showImage(final String str, final String str2, final boolean z) {
        new ImageCacheSimple(CommonUtils.getAppContext()).getImageCachePath(CommonUtil.httpToHttps(str), new ImageCacheSimple.IGetImageCacheListener() { // from class: com.petkit.android.activities.appwidget.mode.small.T7SmallWidgetView$$ExternalSyntheticLambda0
            @Override // com.jess.arms.widget.imageloader.ImageCacheSimple.IGetImageCacheListener
            public final void onCachePath(String str3) {
                this.f$0.lambda$showImage$0(str2, str, z, str3);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showImage$0(String str, String str2, boolean z, String str3) {
        if (str3 != null) {
            File fileDecryptImageFile = ImageUtils.decryptImageFile(new File(str3), str);
            if (fileDecryptImageFile != null) {
                Bitmap t7WidgetBitmap = CommonUtil.getT7WidgetBitmap(T7Utils.getRotateBitmap(fileDecryptImageFile.getAbsolutePath(), str2), ErrorCode.ERR_USER_PARTNER_ALREADY_BINDED);
                if (z) {
                    t7WidgetBitmap = BlurUtils.blur(CommonUtils.getAppContext(), t7WidgetBitmap, 20, 0.5f);
                }
                this.remoteViews.setImageViewBitmap(R.id.iv_video, bimapRound(t7WidgetBitmap, 6));
                this.appWidgetManager.updateAppWidget(this.appWidgetId, this.remoteViews);
                return;
            }
            this.remoteViews.setImageViewResource(R.id.iv_video, R.drawable.solid_dark_black_16);
            return;
        }
        this.remoteViews.setImageViewResource(R.id.iv_video, R.drawable.solid_dark_black_16);
    }

    private Bitmap bimapRound(Bitmap bitmap, int i) {
        try {
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmapCreateBitmap);
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            float fDip2px = CommonUtil.dip2px(CommonUtils.getAppContext(), i);
            float[] fArr = {fDip2px, fDip2px, fDip2px, fDip2px, fDip2px, fDip2px, fDip2px, fDip2px};
            Path path = new Path();
            path.addRoundRect(new RectF(0.0f, 0.0f, bitmap.getWidth(), bitmap.getHeight()), fArr, Path.Direction.CW);
            canvas.clipPath(path);
            Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            RectF rectF = new RectF(rect);
            canvas.drawARGB(0, 0, 0, 0);
            canvas.drawRoundRect(rectF, 1.0f, 1.0f, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rectF, paint);
            return bitmapCreateBitmap;
        } catch (Exception unused) {
            return bitmap;
        }
    }
}

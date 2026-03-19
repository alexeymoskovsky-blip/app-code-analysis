package com.petkit.android.activities.home.adapter.holder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.jess.arms.utils.ImageUtils;
import com.petkit.android.activities.home.adapter.model.HomeDeviceData;
import com.petkit.android.activities.home.utils.BlurUtils;
import com.petkit.android.activities.petkitBleDevice.d4sh.mode.D4shRecord;
import com.petkit.android.activities.petkitBleDevice.t4.widget.RoundImageview;
import com.petkit.oversea.R;
import java.io.File;

/* JADX INFO: loaded from: classes4.dex */
public class D4hHomeDeviceViewHolder extends BaseHomeDeviceViewHolder {
    public final Context context;
    public D4shRecord d4shRecord;
    public FrameLayout flState;
    public RelativeLayout ivDownShadow;
    public final ImageView ivNoEvent;
    public ImageView ivOffline;
    public RoundImageview ivVideo;
    public final LinearLayout llNoEvent;
    public TextView tvDeviceTag;
    public TextView tvEatTime;
    public TextView tvEvent;
    public final TextView tvNoEvent;
    public final TextView tvShared;
    public TextView tvTip;
    public TextView tvVirtualDevice;

    public D4hHomeDeviceViewHolder(@NonNull View view) {
        super(view);
        this.context = view.getContext();
        this.tvDeviceTag = (TextView) view.findViewById(R.id.tv_device_tag);
        this.flState = (FrameLayout) view.findViewById(R.id.fl_state_bg);
        this.ivVideo = (RoundImageview) view.findViewById(R.id.iv_video);
        this.ivOffline = (ImageView) view.findViewById(R.id.iv_offline);
        this.ivDownShadow = (RelativeLayout) view.findViewById(R.id.rl_down_shadow);
        this.tvEatTime = (TextView) view.findViewById(R.id.tv_eat_time);
        this.ivNoEvent = (ImageView) view.findViewById(R.id.iv_no_event);
        this.llNoEvent = (LinearLayout) view.findViewById(R.id.ll_no_event);
        this.tvNoEvent = (TextView) view.findViewById(R.id.tv_no_event);
        this.tvTip = (TextView) view.findViewById(R.id.tv_tip);
        this.tvEvent = (TextView) view.findViewById(R.id.tv_event);
        this.tvShared = (TextView) view.findViewById(R.id.tv_shared);
        this.tvVirtualDevice = (TextView) view.findViewById(R.id.tv_virtual_device);
    }

    public final void setFlStateMargin(int i) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.flState.getLayoutParams();
        layoutParams.rightMargin = i;
        layoutParams.leftMargin = i;
        this.flState.setLayoutParams(layoutParams);
    }

    /* JADX WARN: Removed duplicated region for block: B:143:0x094a  */
    /* JADX WARN: Removed duplicated region for block: B:161:0x0a66  */
    @Override // com.petkit.android.activities.home.adapter.holder.BaseHomeDeviceViewHolder
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void updateData(final com.petkit.android.activities.home.adapter.model.HomeDeviceData r19) {
        /*
            Method dump skipped, instruction units count: 2710
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.petkit.android.activities.home.adapter.holder.D4hHomeDeviceViewHolder.updateData(com.petkit.android.activities.home.adapter.model.HomeDeviceData):void");
    }

    public final /* synthetic */ void lambda$updateData$0(HomeDeviceData homeDeviceData, String str) {
        if (str != null) {
            Bitmap bitmapBlur = BlurUtils.blur(this.context, BitmapFactory.decodeFile(ImageUtils.decryptImageFile(new File(str), homeDeviceData.getData().getAesKey()).getAbsolutePath()), 10, 0.125f);
            if (bitmapBlur != null) {
                this.ivVideo.setImageBitmap(bimapRound(bitmapBlur, 4.0f));
                this.llNoEvent.setVisibility(8);
            }
        }
    }

    public final /* synthetic */ void lambda$updateData$1(HomeDeviceData homeDeviceData, String str) {
        if (str != null) {
            Bitmap bitmapBlur = BlurUtils.blur(this.context, BitmapFactory.decodeFile(ImageUtils.decryptImageFile(new File(str), homeDeviceData.getData().getAesKey()).getAbsolutePath()), 10, 0.125f);
            if (bitmapBlur != null) {
                this.ivVideo.setImageBitmap(bimapRound(bitmapBlur, 4.0f));
            }
        }
    }

    public final boolean feeding(HomeDeviceData homeDeviceData) {
        if (homeDeviceData == null || homeDeviceData.getData() == null || homeDeviceData.getData().getStatus() == null) {
            return false;
        }
        return homeDeviceData.getData().getStatus().getFeeding() == 1 || homeDeviceData.getData().getStatus().getFeeding() == 2 || homeDeviceData.getData().getStatus().getFeeding() == 3;
    }

    public final void checkDeviceNameSize(HomeDeviceData homeDeviceData) {
        if (homeDeviceData.getData().getDeviceShared(26) != null) {
            this.ivRedPoint.setVisibility(8);
            this.tvDeviceName.setMaxLines(1);
            this.tvShared.setVisibility(0);
            String name = homeDeviceData.getData().getName();
            if (!TextUtils.isEmpty(name)) {
                this.tvDeviceName.setText(name);
                return;
            } else {
                this.tvDeviceName.setText(this.context.getString(R.string.Device_d4sh_name));
                return;
            }
        }
        this.tvShared.setVisibility(8);
        String name2 = homeDeviceData.getData().getName();
        if (!TextUtils.isEmpty(name2)) {
            this.tvDeviceName.setText(name2);
        } else {
            this.tvDeviceName.setText(this.context.getString(R.string.Device_d4sh_name));
        }
    }

    public final Bitmap bimapRound(Bitmap bitmap, float f) {
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
}

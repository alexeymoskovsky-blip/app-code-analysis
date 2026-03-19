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
import com.petkit.android.activities.home.adapter.model.HomeDeviceData;
import com.petkit.android.activities.home.utils.BlurUtils;
import com.petkit.android.activities.petkitBleDevice.d4sh.mode.D4shRecord;
import com.petkit.android.activities.petkitBleDevice.t4.widget.RoundImageview;
import com.petkit.android.utils.ThreadPoolManager;
import com.petkit.oversea.R;
import java.io.File;

/* JADX INFO: loaded from: classes4.dex */
public class D4hHomeLargeDeviceViewHolder extends BaseHomeLargeDeviceWithControlViewHolder {
    public final Context context;
    public D4shRecord d4shRecord;
    public final ImageView ivNoEvent;
    public ImageView ivOffline;
    public ImageView ivPlay;
    public ImageView ivSetting;
    public final ImageView ivShadow;
    public RoundImageview ivVideo;
    public final LinearLayout llNoEvent;
    public RelativeLayout rlControlView;
    public RelativeLayout rlImage;
    public final TextView tvDeviceTag;
    public final TextView tvEvent;
    public final TextView tvNoEvent;
    public final TextView tvPlay;
    public final TextView tvShare;
    public final TextView tvShareLine;
    public final TextView tvTip;

    public D4hHomeLargeDeviceViewHolder(@NonNull View view) {
        super(view);
        this.context = view.getContext();
        this.rlControlView = (RelativeLayout) view.findViewById(R.id.rl_control_view);
        this.tvDeviceTag = (TextView) view.findViewById(R.id.tv_device_tag);
        this.ivVideo = (RoundImageview) view.findViewById(R.id.iv_video);
        this.ivPlay = (ImageView) view.findViewById(R.id.iv_play);
        this.rlImage = (RelativeLayout) view.findViewById(R.id.rl_img);
        this.ivOffline = (ImageView) view.findViewById(R.id.iv_offline);
        this.ivNoEvent = (ImageView) view.findViewById(R.id.iv_no_event);
        this.llNoEvent = (LinearLayout) view.findViewById(R.id.ll_no_event);
        this.tvNoEvent = (TextView) view.findViewById(R.id.tv_no_event);
        this.tvPlay = (TextView) view.findViewById(R.id.tv_play);
        this.ivSetting = (ImageView) view.findViewById(R.id.iv_setting);
        this.tvShare = (TextView) view.findViewById(R.id.tv_share);
        this.tvShareLine = (TextView) view.findViewById(R.id.tv_share_line);
        this.tvEvent = (TextView) view.findViewById(R.id.tv_event);
        this.tvTip = (TextView) view.findViewById(R.id.tv_tip);
        this.ivShadow = (ImageView) view.findViewById(R.id.iv_shadow);
        this.ivVideo.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.petkit.android.activities.home.adapter.newholder.D4hHomeLargeDeviceViewHolder.1
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                ViewGroup.LayoutParams layoutParams = D4hHomeLargeDeviceViewHolder.this.rlImage.getLayoutParams();
                layoutParams.height = ArmsUtils.dip2px(D4hHomeLargeDeviceViewHolder.this.context, 250.0f);
                D4hHomeLargeDeviceViewHolder.this.rlImage.setLayoutParams(layoutParams);
                ViewGroup.LayoutParams layoutParams2 = D4hHomeLargeDeviceViewHolder.this.ivVideo.getLayoutParams();
                layoutParams2.height = ArmsUtils.dip2px(D4hHomeLargeDeviceViewHolder.this.context, 250.0f);
                D4hHomeLargeDeviceViewHolder.this.ivVideo.setLayoutParams(layoutParams2);
                D4hHomeLargeDeviceViewHolder.this.ivVideo.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    /* JADX WARN: Removed duplicated region for block: B:175:0x0787  */
    /* JADX WARN: Removed duplicated region for block: B:190:0x08b0  */
    /* JADX WARN: Removed duplicated region for block: B:197:0x08db  */
    /* JADX WARN: Removed duplicated region for block: B:205:? A[RETURN, SYNTHETIC] */
    @Override // com.petkit.android.activities.home.adapter.newholder.BaseHomeLargeDeviceViewHolder
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void updateData(final com.petkit.android.activities.home.adapter.model.HomeDeviceData r19, int r20, com.petkit.android.activities.home.NewCardOnClickListener r21) {
        /*
            Method dump skipped, instruction units count: 2305
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.petkit.android.activities.home.adapter.newholder.D4hHomeLargeDeviceViewHolder.updateData(com.petkit.android.activities.home.adapter.model.HomeDeviceData, int, com.petkit.android.activities.home.NewCardOnClickListener):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateData$0(final HomeDeviceData homeDeviceData, final String str) {
        if (str != null) {
            ThreadPoolManager.getInstance().execute(new Runnable() { // from class: com.petkit.android.activities.home.adapter.newholder.D4hHomeLargeDeviceViewHolder.2
                @Override // java.lang.Runnable
                public void run() {
                    final Bitmap bitmapBlur = BlurUtils.blur(D4hHomeLargeDeviceViewHolder.this.context, BitmapFactory.decodeFile(ImageUtils.decryptImageFile(new File(str), homeDeviceData.getData().getAesKey()).getAbsolutePath()), 10, 0.125f);
                    new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.petkit.android.activities.home.adapter.newholder.D4hHomeLargeDeviceViewHolder.2.1
                        @Override // java.lang.Runnable
                        public void run() {
                            D4hHomeLargeDeviceViewHolder.this.ivVideo.setImageBitmap(bitmapBlur);
                        }
                    });
                }
            });
            this.llNoEvent.setVisibility(8);
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
        if (homeDeviceData.getData().getDeviceShared(26) != null) {
            this.ivRedPoint.setVisibility(8);
            this.tvShare.setVisibility(0);
            this.tvShareLine.setVisibility(0);
            this.tvDeviceName.setMaxLines(1);
            String name = homeDeviceData.getData().getName();
            if (!TextUtils.isEmpty(name)) {
                this.tvDeviceName.setText(name);
                return;
            } else {
                this.tvDeviceName.setText(this.context.getString(R.string.Device_d4h_name));
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
            this.tvDeviceName.setText(this.context.getString(R.string.Device_d4h_name));
        }
    }
}

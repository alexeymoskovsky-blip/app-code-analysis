package com.petkit.android.activities.home.adapter.newholder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.petkit.android.activities.petkitBleDevice.t4.widget.RoundImageview;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.ThreadPoolManager;
import com.petkit.oversea.R;
import java.io.File;

/* JADX INFO: loaded from: classes4.dex */
public class W7hHomeLargeDeviceViewHolder extends BaseHomeLargeDeviceWithControlViewHolder {
    public final ImageView ivNoEvent;
    public ImageView ivOffline;
    public ImageView ivPlay;
    public ImageView ivSetting;
    public final ImageView ivShadow;
    public RoundImageview ivVideo;
    public final LinearLayout llNoEvent;
    public final RelativeLayout llParentPanel;
    public final Context mContext;
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

    public W7hHomeLargeDeviceViewHolder(@NonNull View view) {
        super(view);
        Context context = view.getContext();
        this.mContext = context;
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
        RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.ll_parent_panel);
        this.llParentPanel = relativeLayout;
        this.tvDeviceState.setCompoundDrawablePadding(ArmsUtils.dip2px(context, 4.0f));
        relativeLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.petkit.android.activities.home.adapter.newholder.W7hHomeLargeDeviceViewHolder.1
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                ViewGroup.LayoutParams layoutParams = W7hHomeLargeDeviceViewHolder.this.rlImage.getLayoutParams();
                layoutParams.height = ArmsUtils.dip2px(W7hHomeLargeDeviceViewHolder.this.mContext, 250.0f);
                W7hHomeLargeDeviceViewHolder.this.rlImage.setLayoutParams(layoutParams);
                ViewGroup.LayoutParams layoutParams2 = W7hHomeLargeDeviceViewHolder.this.llParentPanel.getLayoutParams();
                layoutParams2.height = ArmsUtils.dip2px(W7hHomeLargeDeviceViewHolder.this.mContext, 250.0f);
                W7hHomeLargeDeviceViewHolder.this.llParentPanel.setLayoutParams(layoutParams2);
                W7hHomeLargeDeviceViewHolder.this.llParentPanel.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    public final boolean canCamera(HomeDeviceData homeDeviceData) {
        int state;
        if (homeDeviceData == null || homeDeviceData.getData() == null || (state = homeDeviceData.getData().getState()) == 2 || homeDeviceData.getData().getStatus().getPower() == 0 || homeDeviceData.getData().getStatus().getOta() == 1 || homeDeviceData.getData().getStatus().getPim() == 0 || homeDeviceData.getData().getStatus().w7hCameraAvailable()) {
            return false;
        }
        return homeDeviceData.getData().getStatus().getWaterPumpState() == 1 || state != 4;
    }

    public final boolean canAW(HomeDeviceData homeDeviceData) {
        if (homeDeviceData == null || homeDeviceData.getData() == null) {
            return false;
        }
        return (homeDeviceData.getData().getState() == 2 || homeDeviceData.getData().getStatus().getPower() == 0 || homeDeviceData.getData().getStatus().getOta() == 1 || homeDeviceData.getData().getStatus().getPim() == 0 || homeDeviceData.getData().getStatus().w7hAWAvailable() || homeDeviceData.getData().getState() == 4) ? false : true;
    }

    /* JADX WARN: Removed duplicated region for block: B:170:0x0734  */
    /* JADX WARN: Removed duplicated region for block: B:182:0x0769  */
    /* JADX WARN: Removed duplicated region for block: B:211:0x08e4  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x047e  */
    @Override // com.petkit.android.activities.home.adapter.newholder.BaseHomeLargeDeviceViewHolder
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void updateData(final com.petkit.android.activities.home.adapter.model.HomeDeviceData r20, int r21, com.petkit.android.activities.home.NewCardOnClickListener r22) {
        /*
            Method dump skipped, instruction units count: 2312
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.petkit.android.activities.home.adapter.newholder.W7hHomeLargeDeviceViewHolder.updateData(com.petkit.android.activities.home.adapter.model.HomeDeviceData, int, com.petkit.android.activities.home.NewCardOnClickListener):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateData$0(final HomeDeviceData homeDeviceData, final String str) {
        if (str != null) {
            ThreadPoolManager.getInstance().execute(new Runnable() { // from class: com.petkit.android.activities.home.adapter.newholder.W7hHomeLargeDeviceViewHolder.2
                @Override // java.lang.Runnable
                public void run() {
                    final Bitmap bitmapBlur = BlurUtils.blur(CommonUtils.getAppContext(), BitmapFactory.decodeFile(ImageUtils.decryptImageFile(new File(str), homeDeviceData.getData().getAesKey()).getAbsolutePath()), 10, 0.125f);
                    new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.petkit.android.activities.home.adapter.newholder.W7hHomeLargeDeviceViewHolder.2.1
                        @Override // java.lang.Runnable
                        public void run() {
                            W7hHomeLargeDeviceViewHolder.this.ivVideo.setImageBitmap(bitmapBlur);
                        }
                    });
                }
            });
        } else {
            this.ivVideo.setImageResource(R.drawable.solid_dark_black_8);
        }
    }

    private void checkDeviceNameSize(HomeDeviceData homeDeviceData) {
        if (homeDeviceData.getData().getDeviceShared(29) != null) {
            this.ivRedPoint.setVisibility(8);
            this.tvDeviceName.setMaxLines(1);
            this.tvShare.setVisibility(0);
            this.tvShareLine.setVisibility(0);
            String name = homeDeviceData.getData().getName();
            if (!TextUtils.isEmpty(name)) {
                this.tvDeviceName.setText(name);
                return;
            } else {
                this.tvDeviceName.setText(this.mContext.getString(R.string.W7h_name_default));
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
            this.tvDeviceName.setText(this.mContext.getString(R.string.W7h_name_default));
        }
    }
}

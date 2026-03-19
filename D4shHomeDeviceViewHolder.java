package com.petkit.android.activities.home.adapter.holder;

import android.content.Context;
import android.graphics.BitmapFactory;
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
import com.petkit.android.activities.petkitBleDevice.t4.widget.RoundImageview;
import com.petkit.oversea.R;
import java.io.File;

/* JADX INFO: loaded from: classes4.dex */
public class D4shHomeDeviceViewHolder extends BaseHomeDeviceViewHolder {
    public final Context context;
    public FrameLayout flState;
    public RelativeLayout ivDownShadow;
    public final ImageView ivNoEvent;
    public ImageView ivOffline;
    public RoundImageview ivVideo;
    public final LinearLayout llNoEvent;
    public final TextView tvDeviceTag;
    public TextView tvEatTime;
    public TextView tvEvent;
    public final TextView tvNoEvent;
    public final TextView tvShared;
    public TextView tvTip;
    public TextView tvVirtualDevice;

    public D4shHomeDeviceViewHolder(@NonNull View view) {
        super(view);
        this.context = view.getContext();
        this.tvDeviceTag = (TextView) view.findViewById(R.id.tv_device_tag);
        this.flState = (FrameLayout) view.findViewById(R.id.fl_state_bg);
        this.ivVideo = (RoundImageview) view.findViewById(R.id.iv_video);
        this.ivDownShadow = (RelativeLayout) view.findViewById(R.id.rl_down_shadow);
        this.ivOffline = (ImageView) view.findViewById(R.id.iv_offline);
        this.tvEatTime = (TextView) view.findViewById(R.id.tv_eat_time);
        this.tvTip = (TextView) view.findViewById(R.id.tv_tip);
        this.tvVirtualDevice = (TextView) view.findViewById(R.id.tv_virtual_device);
        this.tvEvent = (TextView) view.findViewById(R.id.tv_event);
        this.tvShared = (TextView) view.findViewById(R.id.tv_shared);
        this.ivNoEvent = (ImageView) view.findViewById(R.id.iv_no_event);
        this.llNoEvent = (LinearLayout) view.findViewById(R.id.ll_no_event);
        this.tvNoEvent = (TextView) view.findViewById(R.id.tv_no_event);
    }

    private void setFlStateMargin(int i) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.flState.getLayoutParams();
        layoutParams.rightMargin = i;
        layoutParams.leftMargin = i;
        this.flState.setLayoutParams(layoutParams);
    }

    /* JADX WARN: Removed duplicated region for block: B:145:0x08c1  */
    /* JADX WARN: Removed duplicated region for block: B:162:0x09e8  */
    @Override // com.petkit.android.activities.home.adapter.holder.BaseHomeDeviceViewHolder
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void updateData(final com.petkit.android.activities.home.adapter.model.HomeDeviceData r19) {
        /*
            Method dump skipped, instruction units count: 2569
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.petkit.android.activities.home.adapter.holder.D4shHomeDeviceViewHolder.updateData(com.petkit.android.activities.home.adapter.model.HomeDeviceData):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateData$0(HomeDeviceData homeDeviceData, String str) {
        if (str != null) {
            this.ivVideo.setImageBitmap(BlurUtils.blur(this.context, BitmapFactory.decodeFile(ImageUtils.decryptImageFile(new File(str), homeDeviceData.getData().getAesKey()).getAbsolutePath()), 10, 0.125f));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateData$1(HomeDeviceData homeDeviceData, String str) {
        if (str != null) {
            this.ivVideo.setImageBitmap(BlurUtils.blur(this.context, BitmapFactory.decodeFile(ImageUtils.decryptImageFile(new File(str), homeDeviceData.getData().getAesKey()).getAbsolutePath()), 10, 0.125f));
        }
    }

    public final boolean showEvent(HomeDeviceData homeDeviceData) {
        return (homeDeviceData == null || homeDeviceData.getData() == null || homeDeviceData.getData().getState() == 2 || homeDeviceData.getData().getState() == 5 || homeDeviceData.getData().getState() == 4 || homeDeviceData.getData().getStatus().getBatteryStatus() == 2 || homeDeviceData.getData().getStatus().getBatteryStatus() == 1 || homeDeviceData.getData().getStatus().getFood1() == 0 || homeDeviceData.getData().getStatus().getFood2() == 0 || homeDeviceData.getData().getStatus().getFood1() == 1 || homeDeviceData.getData().getStatus().getFood2() == 1 || homeDeviceData.getData().getEvent() == null) ? false : true;
    }

    private void checkDeviceNameSize(HomeDeviceData homeDeviceData) {
        if (homeDeviceData.getData().getDeviceShared(25) != null) {
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
}

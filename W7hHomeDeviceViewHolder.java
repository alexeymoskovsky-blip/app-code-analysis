package com.petkit.android.activities.home.adapter.holder;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
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
import com.petkit.android.utils.CommonUtils;
import com.petkit.oversea.R;
import java.io.File;

/* JADX INFO: loaded from: classes4.dex */
public class W7hHomeDeviceViewHolder extends BaseHomeDeviceViewHolder {
    public FrameLayout flState;
    public RelativeLayout ivDownShadow;
    public final ImageView ivNoEvent;
    public ImageView ivOffline;
    public RoundImageview ivVideo;
    public final LinearLayout llNoEvent;
    public Context mContext;
    public final TextView tvDeviceTag;
    public TextView tvEvent;
    public final TextView tvNoEvent;
    public final TextView tvShared;
    public TextView tvTip;
    public TextView tvVirtualDevice;

    public W7hHomeDeviceViewHolder(@NonNull View view) {
        super(view);
        this.mContext = view.getContext();
        this.tvDeviceTag = (TextView) view.findViewById(R.id.tv_device_tag);
        this.flState = (FrameLayout) view.findViewById(R.id.fl_state_bg);
        this.ivVideo = (RoundImageview) view.findViewById(R.id.iv_video);
        this.ivDownShadow = (RelativeLayout) view.findViewById(R.id.rl_down_shadow);
        this.ivOffline = (ImageView) view.findViewById(R.id.iv_offline);
        this.tvTip = (TextView) view.findViewById(R.id.tv_tip);
        this.tvVirtualDevice = (TextView) view.findViewById(R.id.tv_virtual_device);
        this.tvEvent = (TextView) view.findViewById(R.id.tv_event);
        this.tvShared = (TextView) view.findViewById(R.id.tv_shared);
        this.ivNoEvent = (ImageView) view.findViewById(R.id.iv_no_event);
        this.llNoEvent = (LinearLayout) view.findViewById(R.id.ll_no_event);
        this.tvNoEvent = (TextView) view.findViewById(R.id.tv_no_event);
    }

    /* JADX WARN: Removed duplicated region for block: B:124:0x0618  */
    /* JADX WARN: Removed duplicated region for block: B:144:0x0729  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x031c  */
    @Override // com.petkit.android.activities.home.adapter.holder.BaseHomeDeviceViewHolder
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void updateData(final com.petkit.android.activities.home.adapter.model.HomeDeviceData r20) {
        /*
            Method dump skipped, instruction units count: 1870
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.petkit.android.activities.home.adapter.holder.W7hHomeDeviceViewHolder.updateData(com.petkit.android.activities.home.adapter.model.HomeDeviceData):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateData$0(HomeDeviceData homeDeviceData, String str) {
        if (str != null) {
            this.ivVideo.setImageBitmap(BlurUtils.blur(CommonUtils.getAppContext(), BitmapFactory.decodeFile(ImageUtils.decryptImageFile(new File(str), homeDeviceData.getData().getAesKey()).getAbsolutePath()), 10, 0.125f));
        }
    }

    public final void setupError(String str) {
        this.tvDeviceState.setCompoundDrawablesWithIntrinsicBounds(this.mContext.getResources().getDrawable(R.drawable.home_item_warn_white_icon), (Drawable) null, (Drawable) null, (Drawable) null);
        this.tvDeviceState.setText(str);
        this.flState.setBackgroundResource(R.drawable.solid_home_device_state_red);
        this.tvDeviceState.setTextColor(this.mContext.getResources().getColor(R.color.white));
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
                this.tvDeviceName.setText(this.mContext.getString(R.string.W7h_name_default));
                return;
            }
        }
        this.tvShared.setVisibility(8);
        String name2 = homeDeviceData.getData().getName();
        if (!TextUtils.isEmpty(name2)) {
            this.tvDeviceName.setText(name2);
        } else {
            this.tvDeviceName.setText(this.mContext.getString(R.string.W7h_name_default));
        }
    }
}

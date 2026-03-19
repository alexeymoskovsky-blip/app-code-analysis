package com.petkit.android.activities.home.adapter.newholder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.petkit.oversea.R;

/* JADX INFO: loaded from: classes4.dex */
public class W5HomeLargeDeviceViewHolder extends BaseHomeLargeDeviceWithConsumableViewHolder {
    public final Context context;
    public final ImageView ivWarn;
    public TextView tvMode;
    public final TextView tvPercent;

    public W5HomeLargeDeviceViewHolder(@NonNull View view) {
        super(view);
        this.context = view.getContext();
        this.tvMode = (TextView) view.findViewById(R.id.tv_mode);
        this.tvPercent = (TextView) view.findViewById(R.id.tv_percent);
        this.ivWarn = (ImageView) view.findViewById(R.id.iv_warn);
    }

    /* JADX WARN: Removed duplicated region for block: B:66:0x01d4  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x01f2  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x02a7  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x02c4  */
    @Override // com.petkit.android.activities.home.adapter.newholder.BaseHomeLargeDeviceViewHolder
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void updateData(com.petkit.android.activities.home.adapter.model.HomeDeviceData r18, int r19, com.petkit.android.activities.home.NewCardOnClickListener r20) {
        /*
            Method dump skipped, instruction units count: 1465
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.petkit.android.activities.home.adapter.newholder.W5HomeLargeDeviceViewHolder.updateData(com.petkit.android.activities.home.adapter.model.HomeDeviceData, int, com.petkit.android.activities.home.NewCardOnClickListener):void");
    }

    private float canculateWxEnergyForType(int i, String str, int i2) {
        float f;
        float f2;
        if (i == 1) {
            float f3 = "W5C".equals(str) ? 1.3f : 1.5f;
            f2 = "W5C".equals(str) ? 1.0f : "W4X".equals(str) ? 1.8f : 2.0f;
            f = (f3 * i2) / 60.0f;
        } else {
            f = ("W5C".equals(str) ? 0.182f : 0.75f) * i2;
            f2 = 3600000.0f;
        }
        return f / f2;
    }
}

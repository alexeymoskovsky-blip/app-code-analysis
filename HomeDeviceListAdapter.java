package com.petkit.android.activities.home.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.petkit.android.activities.base.adapter.BaseCard;
import com.petkit.android.activities.base.adapter.BaseTouchRecyclerAdapter;
import com.petkit.android.activities.base.adapter.BaseTouchViewHolder;
import com.petkit.android.activities.common.utils.DeviceCenterUtils;
import com.petkit.android.activities.home.adapter.model.DeviceCard;
import com.petkit.oversea.R;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class HomeDeviceListAdapter extends BaseTouchRecyclerAdapter<BaseCard> {
    public boolean isAll;

    public boolean isAll() {
        return this.isAll;
    }

    public void setAll(boolean z) {
        this.isAll = z;
    }

    public HomeDeviceListAdapter(Activity activity) {
        super(activity);
    }

    @Override // com.petkit.android.activities.base.adapter.BaseRecyclerAdapter
    public void showData(RecyclerView.ViewHolder viewHolder, int i, List list) {
        if (viewHolder instanceof BaseTouchViewHolder) {
            ((BaseTouchViewHolder) viewHolder).updateData(list.get(i));
        }
    }

    @Override // com.petkit.android.activities.base.adapter.BaseRecyclerAdapter
    public View createView(ViewGroup viewGroup, int i) {
        if (i == -1) {
            return LayoutInflater.from(this.mActivity).inflate(R.layout.layout_home_device_null, viewGroup, false);
        }
        if (i == 10000) {
            return LayoutInflater.from(this.mActivity).inflate(R.layout.layout_label_item, viewGroup, false);
        }
        return LayoutInflater.from(this.mActivity).inflate(R.layout.layout_home_device_item, viewGroup, false);
    }

    /*  JADX ERROR: UnsupportedOperationException in pass: RegionMakerVisitor
        java.lang.UnsupportedOperationException
        	at java.base/java.util.Collections$UnmodifiableCollection.add(Unknown Source)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker$1.leaveRegion(SwitchRegionMaker.java:390)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:70)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(Unknown Source)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverse(DepthRegionTraversal.java:23)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.insertBreaksForCase(SwitchRegionMaker.java:370)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.insertBreaks(SwitchRegionMaker.java:85)
        	at jadx.core.dex.visitors.regions.PostProcessRegions.leaveRegion(PostProcessRegions.java:33)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:70)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(Unknown Source)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(Unknown Source)
        	at java.base/java.util.Collections$UnmodifiableCollection.forEach(Unknown Source)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(Unknown Source)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(Unknown Source)
        	at java.base/java.util.Collections$UnmodifiableCollection.forEach(Unknown Source)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(Unknown Source)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(Unknown Source)
        	at java.base/java.util.Collections$UnmodifiableCollection.forEach(Unknown Source)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(Unknown Source)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverse(DepthRegionTraversal.java:19)
        	at jadx.core.dex.visitors.regions.PostProcessRegions.process(PostProcessRegions.java:23)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:31)
        */
    @Override // com.petkit.android.activities.base.adapter.BaseRecyclerAdapter
    public androidx.recyclerview.widget.RecyclerView.ViewHolder createViewHolder(android.view.View r3, int r4) {
        /*
            Method dump skipped, instruction units count: 274
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.petkit.android.activities.home.adapter.HomeDeviceListAdapter.createViewHolder(android.view.View, int):androidx.recyclerview.widget.RecyclerView$ViewHolder");
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        return ((BaseCard) this.mItemDataList.get(i)).getViewType();
    }

    @Override // com.petkit.android.activities.base.adapter.BaseTouchRecyclerAdapter, com.petkit.android.activities.base.adapter.helper.ItemTouchHelperAdapter
    public boolean onItemMove(int i, int i2) {
        if (this.isAll) {
            DeviceCenterUtils.changeDeviceListSort(i, i2);
        } else {
            DeviceCard deviceCard = (DeviceCard) this.mItemDataList.get(i);
            DeviceCard deviceCard2 = (DeviceCard) this.mItemDataList.get(i2);
            List<DeviceCard> petDeviceCardList = DeviceCenterUtils.getPetDeviceCardList();
            int i3 = -1;
            int i4 = -1;
            for (int i5 = 0; i5 < petDeviceCardList.size(); i5++) {
                if (petDeviceCardList.get(i5).getDeviceData().equals(deviceCard.getDeviceData())) {
                    i3 = i5;
                } else if (petDeviceCardList.get(i5).getDeviceData().equals(deviceCard2.getDeviceData())) {
                    i4 = i5;
                }
            }
            if (i3 != -1 && i4 != -1) {
                DeviceCenterUtils.changePetDeviceListSort(i3, i4);
            }
        }
        return super.onItemMove(i, i2);
    }
}

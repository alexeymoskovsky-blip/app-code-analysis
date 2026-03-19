package com.petkit.android.activities.petkitBleDevice.d4sh.adapter;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.petkit.android.activities.petkitBleDevice.d4sh.D4shNewVideoRecordFragment;
import com.petkit.android.activities.petkitBleDevice.d4sh.D4shVideoFragment;
import com.petkit.android.activities.petkitBleDevice.d4sh.mode.D4shRecordFragmentInfor;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class D4shVideoPlayFragmentAdapter extends FragmentStateAdapter {
    public boolean isLandscape;
    public List<D4shRecordFragmentInfor> recordFragmentInfors;

    public D4shVideoPlayFragmentAdapter(@NonNull FragmentActivity fragmentActivity, List<D4shRecordFragmentInfor> list) {
        super(fragmentActivity);
        new ArrayList();
        this.recordFragmentInfors = list;
    }

    public void setIsLandscape(boolean z) {
        this.isLandscape = z;
    }

    @Override // androidx.viewpager2.adapter.FragmentStateAdapter
    @NonNull
    public Fragment createFragment(int i) {
        if (i < this.recordFragmentInfors.size()) {
            if (this.recordFragmentInfors.get(i).getType() == 1) {
                D4shVideoFragment d4shVideoFragmentNewInstance = D4shVideoFragment.newInstance(this.recordFragmentInfors.get(i).getD4shVideoRecord(), this.recordFragmentInfors.get(i).getTypeCode(), this.recordFragmentInfors.get(i).getOffset(), this.isLandscape);
                d4shVideoFragmentNewInstance.setMyPosition(i);
                return d4shVideoFragmentNewInstance;
            }
            if (this.recordFragmentInfors.get(i).getType() == 2) {
                return D4shNewVideoRecordFragment.newInstance(this.recordFragmentInfors.get(i).getD4shVideoRecord(), this.recordFragmentInfors.get(i).getTypeCode(), this.recordFragmentInfors.get(i).getOffset(), this.isLandscape);
            }
            return new Fragment();
        }
        return new Fragment();
    }

    public List<D4shRecordFragmentInfor> getRecordFragmentInforsList() {
        return this.recordFragmentInfors;
    }

    @SuppressLint({"NotifyDataSetChanged"})
    public void updateData(List<D4shRecordFragmentInfor> list) {
        this.recordFragmentInfors.clear();
        this.recordFragmentInfors.addAll(list);
        notifyDataSetChanged();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.recordFragmentInfors.size();
    }
}

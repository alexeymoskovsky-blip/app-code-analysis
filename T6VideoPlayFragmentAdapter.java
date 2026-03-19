package com.petkit.android.activities.petkitBleDevice.t6.adapter;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.petkit.android.activities.petkitBleDevice.t6.T6ShitRecordFragment;
import com.petkit.android.activities.petkitBleDevice.t6.T6VideoPlayerFragment;
import com.petkit.android.activities.petkitBleDevice.t6.mode.T6RecordFragmentInfor;
import com.petkit.android.activities.petkitBleDevice.t7.T7LiveVideoListFragment;
import com.petkit.android.activities.petkitBleDevice.t7.T7ShitRecordFragment;
import com.petkit.android.activities.petkitBleDevice.w7h.W7hVideoPlayFragment;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes5.dex */
public class T6VideoPlayFragmentAdapter extends FragmentStateAdapter {
    public long deviceId;
    public int deviceType;
    public boolean isFromLive;
    public W7hVideoPlayFragment.FromLiveOnclickListener liveOnclickListener;
    public List<T6RecordFragmentInfor> recordFragmentInfors;

    public T6VideoPlayFragmentAdapter(@NonNull FragmentActivity fragmentActivity, long j, int i, List<T6RecordFragmentInfor> list) {
        super(fragmentActivity);
        new ArrayList();
        this.recordFragmentInfors = list;
        this.deviceId = j;
        this.deviceType = i;
    }

    public T6VideoPlayFragmentAdapter(@NonNull FragmentActivity fragmentActivity, long j, int i, List<T6RecordFragmentInfor> list, boolean z) {
        super(fragmentActivity);
        new ArrayList();
        this.recordFragmentInfors = list;
        this.deviceId = j;
        this.deviceType = i;
        this.isFromLive = z;
    }

    public void setFromLiveListener(W7hVideoPlayFragment.FromLiveOnclickListener fromLiveOnclickListener) {
        this.liveOnclickListener = fromLiveOnclickListener;
    }

    @Override // androidx.viewpager2.adapter.FragmentStateAdapter
    @NonNull
    public Fragment createFragment(int i) {
        if (i < this.recordFragmentInfors.size()) {
            int i2 = this.deviceType;
            if (i2 == 27 || i2 == 21) {
                if (this.recordFragmentInfors.get(i).getType() == 1) {
                    T6VideoPlayerFragment t6VideoPlayerFragmentNewInstance = T6VideoPlayerFragment.newInstance(this.deviceId, this.deviceType, this.recordFragmentInfors.get(i).getT6EventInfo());
                    t6VideoPlayerFragmentNewInstance.setMyPosition(i);
                    return t6VideoPlayerFragmentNewInstance;
                }
                if (this.recordFragmentInfors.get(i).getType() == 2) {
                    T6ShitRecordFragment t6ShitRecordFragmentNewInstance = T6ShitRecordFragment.newInstance(this.deviceId, this.deviceType, this.recordFragmentInfors.get(i).getT6EventInfo());
                    t6ShitRecordFragmentNewInstance.setMyPosition(i);
                    return t6ShitRecordFragmentNewInstance;
                }
                return new Fragment();
            }
            if (i2 == 28) {
                if (this.recordFragmentInfors.get(i).getType() == 1) {
                    T7LiveVideoListFragment t7LiveVideoListFragmentNewInstance = T7LiveVideoListFragment.newInstance(this.deviceId, this.recordFragmentInfors.get(i).getT7EventInfo());
                    t7LiveVideoListFragmentNewInstance.setMyPosition(i);
                    return t7LiveVideoListFragmentNewInstance;
                }
                if (this.recordFragmentInfors.get(i).getType() == 2) {
                    T7ShitRecordFragment t7ShitRecordFragmentNewInstance = T7ShitRecordFragment.newInstance(this.recordFragmentInfors.get(i).getT7EventInfo(), this.recordFragmentInfors.get(i).getPicType());
                    t7ShitRecordFragmentNewInstance.setMyPosition(i);
                    return t7ShitRecordFragmentNewInstance;
                }
            } else if (i2 == 29 && this.recordFragmentInfors.get(i).getType() == 1) {
                W7hVideoPlayFragment w7hVideoPlayFragmentNewInstance = W7hVideoPlayFragment.newInstance(this.recordFragmentInfors.get(i).getW7hEventInfo(), this.deviceId, 0, this.isFromLive);
                w7hVideoPlayFragmentNewInstance.setMyPosition(i);
                W7hVideoPlayFragment.FromLiveOnclickListener fromLiveOnclickListener = this.liveOnclickListener;
                if (fromLiveOnclickListener != null) {
                    w7hVideoPlayFragmentNewInstance.setFromLiveOnclickListener(fromLiveOnclickListener);
                }
                return w7hVideoPlayFragmentNewInstance;
            }
        }
        return new Fragment();
    }

    public List<T6RecordFragmentInfor> getRecordFragmentInforsList() {
        return this.recordFragmentInfors;
    }

    @SuppressLint({"NotifyDataSetChanged"})
    public void updateData(List<T6RecordFragmentInfor> list) {
        this.recordFragmentInfors.clear();
        this.recordFragmentInfors.addAll(list);
        notifyDataSetChanged();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.recordFragmentInfors.size();
    }
}

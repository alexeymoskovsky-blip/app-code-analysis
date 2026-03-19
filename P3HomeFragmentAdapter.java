package com.petkit.android.activities.petkitBleDevice.p3.adapter;

import android.app.Activity;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import com.petkit.android.activities.petkitBleDevice.p3.P3DeviceHomeFragment;
import com.petkit.android.activities.petkitBleDevice.p3.mode.P3Record;
import com.petkit.android.activities.petkitBleDevice.p3.utils.P3Utils;
import com.petkit.android.activities.petkitBleDevice.t4.mode.RelatedProductsInfor;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.PetkitLog;

/* JADX INFO: loaded from: classes4.dex */
public class P3HomeFragmentAdapter extends FragmentStatePagerAdapter {
    public String createdAt;
    public P3DeviceHomeFragment currentFragment;
    public P3DeviceHomeFragment fragment;
    public P3DeviceHomeFragment.MenuOnClickListener listener;
    public int mCount;
    public long mDeviceId;
    public RelatedProductsInfor relatedProductsInfor;

    public RelatedProductsInfor getRelatedProductsInfor() {
        return this.relatedProductsInfor;
    }

    public void setRelatedProductsInfor(RelatedProductsInfor relatedProductsInfor) {
        this.relatedProductsInfor = relatedProductsInfor;
        notifyDataSetChanged();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public P3HomeFragmentAdapter(FragmentManager fragmentManager, Activity activity, long j, Context context) {
        super(fragmentManager);
        this.mCount = 1400;
        this.mDeviceId = j;
        this.listener = (P3DeviceHomeFragment.MenuOnClickListener) context;
    }

    public void setData(long j) {
        this.mDeviceId = j;
        notifyDataSetChanged();
    }

    public void setTodayFragment(P3DeviceHomeFragment p3DeviceHomeFragment) {
        this.currentFragment = p3DeviceHomeFragment;
    }

    public P3DeviceHomeFragment getTodayFragment() {
        return this.currentFragment;
    }

    @Override // androidx.fragment.app.FragmentStatePagerAdapter
    public Fragment getItem(int i) {
        PetkitLog.d("P3HomeFragmentAdapter getItem position: " + i);
        P3Record p3RecordByDeviceId = P3Utils.getP3RecordByDeviceId(this.mDeviceId);
        if (p3RecordByDeviceId == null) {
            return null;
        }
        String dateStringByOffset = CommonUtils.getDateStringByOffset((i + 1) - this.mCount, p3RecordByDeviceId.getActualTimeZone());
        if (i != this.mCount - 1) {
            if (this.relatedProductsInfor != null) {
                this.fragment = P3DeviceHomeFragment.newInstance(dateStringByOffset, p3RecordByDeviceId.getDeviceId(), i == this.mCount - 1, this.relatedProductsInfor);
            } else {
                this.fragment = P3DeviceHomeFragment.newInstance(dateStringByOffset, p3RecordByDeviceId.getDeviceId(), i == this.mCount - 1);
            }
        } else {
            this.fragment = this.currentFragment;
        }
        this.fragment.setMenuOnClickListener(this.listener);
        return this.fragment;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public int getItemPosition(@NonNull Object obj) {
        return super.getItemPosition(obj);
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public int getCount() {
        return this.mCount;
    }
}

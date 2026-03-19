package com.petkit.android.activities.d2.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import com.petkit.android.activities.d2.D2RecordFragment;
import com.petkit.android.activities.d2.mode.D2Record;
import com.petkit.android.activities.d2.utils.D2Utils;
import com.petkit.android.activities.petkitBleDevice.t4.mode.RelatedProductsInfor;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.PetkitLog;

/* JADX INFO: loaded from: classes3.dex */
public class D2HomeFragmentAdapter extends FragmentStatePagerAdapter {
    public String createdAt;
    public int mCount;
    public long mDeviceId;
    public boolean needRefresh;
    public RelatedProductsInfor relatedProductsInfor;

    public D2HomeFragmentAdapter(FragmentManager fragmentManager, Activity activity, long j, String str) {
        super(fragmentManager);
        this.mDeviceId = j;
        this.createdAt = str;
        this.mCount = getDayCountFromBirthday() + 1;
    }

    public void setData(long j, String str) {
        this.mDeviceId = j;
        this.createdAt = str;
        this.mCount = getDayCountFromBirthday() + 1;
        notifyDataSetChanged();
    }

    @Override // androidx.fragment.app.FragmentStatePagerAdapter
    public Fragment getItem(int i) {
        PetkitLog.d("D2HomeFragmentAdapter getItem position: " + i);
        D2Record d2RecordByDeviceId = D2Utils.getD2RecordByDeviceId(this.mDeviceId);
        if (d2RecordByDeviceId == null) {
            return null;
        }
        D2RecordFragment d2RecordFragment = new D2RecordFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(Constants.EXTRA_DEVICE_ID, this.mDeviceId);
        bundle.putInt("EXTRA_DAY", Integer.valueOf(CommonUtils.getDateStringByOffset((i + 2) - this.mCount, d2RecordByDeviceId.getActualTimeZone())).intValue());
        bundle.putSerializable(Constants.EXTRA_RELATED_PRODUCTS_INFOR, this.relatedProductsInfor);
        int i2 = this.mCount;
        int i3 = 1;
        if (i2 == 1) {
            i3 = 3;
        } else if (i != 0) {
            i3 = i + 1 == i2 ? 2 : 0;
        }
        bundle.putInt(Constants.EXTRA_TYPE, i3);
        d2RecordFragment.setArguments(bundle);
        return d2RecordFragment;
    }

    public void setNeedRefresh(boolean z) {
        this.needRefresh = z;
        notifyDataSetChanged();
        new Handler().postDelayed(new Runnable() { // from class: com.petkit.android.activities.d2.adapter.D2HomeFragmentAdapter.1
            @Override // java.lang.Runnable
            public void run() {
                D2HomeFragmentAdapter.this.needRefresh = false;
            }
        }, 100L);
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public int getItemPosition(Object obj) {
        if (this.needRefresh) {
            return -2;
        }
        return super.getItemPosition(obj);
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public int getCount() {
        return this.mCount;
    }

    private int getDayCountFromBirthday() {
        return CommonUtils.daysCountToToday(this.createdAt, D2Utils.getD2RecordByDeviceId(this.mDeviceId).getActualTimeZone());
    }

    public void setRelatedProductsInfor(RelatedProductsInfor relatedProductsInfor) {
        this.relatedProductsInfor = relatedProductsInfor;
    }
}

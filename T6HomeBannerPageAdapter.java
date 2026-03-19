package com.petkit.android.activities.petkitBleDevice.t6.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.viewpager.widget.PagerAdapter;
import com.bumptech.glide.Glide;
import com.jess.arms.widget.PetkitToast;
import com.petkit.android.activities.petkitBleDevice.d4sh.mode.D4shBannerData;
import com.petkit.android.activities.petkitBleDevice.hg.widget.NewRoundImageview;
import com.petkit.android.activities.petkitBleDevice.t6.mode.T6Record;
import com.petkit.android.activities.petkitBleDevice.utils.FamilyUtils;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.DateUtil;
import com.petkit.android.utils.PetkitLog;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.oversea.R;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/* JADX INFO: loaded from: classes5.dex */
public class T6HomeBannerPageAdapter extends PagerAdapter {
    public Context context;
    public List<D4shBannerData.BannerData> dataList;
    public SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtil.DATE_FORMAT_13);
    public int deviceType;
    public OnItemClickListener onItemClickListener;
    public T6Record t6Record;

    public interface OnItemClickListener {
        void onBannerItemClick(int i);

        void redirectToPurchase(int i, long j);
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }

    public T6HomeBannerPageAdapter(Context context, List<D4shBannerData.BannerData> list, T6Record t6Record, int i, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.dataList = list;
        this.t6Record = t6Record;
        this.deviceType = i;
        this.onItemClickListener = onItemClickListener;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public int getCount() {
        if (this.dataList.size() > 1) {
            return Integer.MAX_VALUE;
        }
        return this.dataList.size();
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public Object instantiateItem(ViewGroup viewGroup, final int i) {
        String actEnglishImage;
        String actChineseImage;
        String actEnglishImage2;
        String actChineseImage2;
        View viewInflate = View.inflate(this.context, R.layout.adapter_d4sh_home_banner_item, null);
        NewRoundImageview newRoundImageview = (NewRoundImageview) viewInflate.findViewById(R.id.iv_image);
        RelativeLayout relativeLayout = (RelativeLayout) viewInflate.findViewById(R.id.rl_tip_panel);
        TextView textView = (TextView) viewInflate.findViewById(R.id.tv_name);
        TextView textView2 = (TextView) viewInflate.findViewById(R.id.tv_time);
        TextView textView3 = (TextView) viewInflate.findViewById(R.id.tv_immediate_renewal);
        int size = this.dataList.size() == 0 ? 1 : this.dataList.size();
        StringBuilder sb = new StringBuilder();
        sb.append("position:");
        sb.append(i);
        int i2 = i % size;
        sb.append(this.dataList.get(i2));
        PetkitLog.d("T6HomeBannerPageAdapter", sb.toString());
        List<D4shBannerData.BannerData> list = this.dataList;
        if (list.get(list.size() > 1 ? i2 : i) instanceof D4shBannerData.ServiceStatusData) {
            newRoundImageview.setVisibility(8);
            relativeLayout.setVisibility(0);
            List<D4shBannerData.BannerData> list2 = this.dataList;
            if (list2.size() > 1) {
                i = i2;
            }
            int status = ((D4shBannerData.ServiceStatusData) list2.get(i)).getStatus();
            if (status == 1) {
                textView.setText(R.string.Petkit_care_plus_services);
                textView2.setText(R.string.Subscribe_service_prompt);
                textView3.setText(R.string.Open_immediately);
                textView3.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.adapter.T6HomeBannerPageAdapter$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$instantiateItem$0(view);
                    }
                });
            } else if (status == 2) {
                textView.setText(R.string.Petkit_care_is_about_to_expire);
                textView2.setText(this.context.getResources().getString(R.string.Period_of_validity, this.dateFormat.format(new Date(Long.parseLong(this.t6Record.getCloudProduct().getWorkIndate()) * 1000))));
                textView3.setText(R.string.Immediate_renewal);
                textView3.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.adapter.T6HomeBannerPageAdapter$$ExternalSyntheticLambda1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$instantiateItem$1(view);
                    }
                });
            } else if (status == 3) {
                textView.setText(R.string.Service_expired_on);
                if (this.t6Record.getCloudProduct() != null) {
                    textView2.setText(this.context.getResources().getString(R.string.Expired_on_prompt, this.dateFormat.format(new Date(Long.parseLong(this.t6Record.getCloudProduct().getWorkIndate()) * 1000))));
                }
                textView3.setText(R.string.Immediate_renewal);
                textView3.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.adapter.T6HomeBannerPageAdapter$$ExternalSyntheticLambda2
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$instantiateItem$2(view);
                    }
                });
            }
        } else {
            List<D4shBannerData.BannerData> list3 = this.dataList;
            if (list3.get(list3.size() > 1 ? i2 : i) instanceof D4shBannerData.FreeActivity) {
                boolean zEquals = "zh_CN".equals(UserInforUtils.getLanguageLocaleString(CommonUtils.getAppContext()));
                List<D4shBannerData.BannerData> list4 = this.dataList;
                if (list4.get(list4.size() > 1 ? i2 : i).getImageRet() != null) {
                    List<D4shBannerData.BannerData> list5 = this.dataList;
                    int size2 = list5.size();
                    if (zEquals) {
                        actEnglishImage2 = list5.get(size2 > 1 ? i2 : i).getImageRet().getActChineseImage();
                    } else {
                        actEnglishImage2 = list5.get(size2 > 1 ? i2 : i).getImageRet().getActEnglishImage();
                    }
                    if (TextUtils.isEmpty(actEnglishImage2)) {
                        if (zEquals) {
                            List<D4shBannerData.BannerData> list6 = this.dataList;
                            if (list6.size() <= 1) {
                                i2 = i;
                            }
                            actChineseImage2 = list6.get(i2).getImageRet().getActEnglishImage();
                        } else {
                            List<D4shBannerData.BannerData> list7 = this.dataList;
                            if (list7.size() <= 1) {
                                i2 = i;
                            }
                            actChineseImage2 = list7.get(i2).getImageRet().getActChineseImage();
                        }
                        actEnglishImage2 = actChineseImage2;
                    }
                    relativeLayout.setVisibility(8);
                    newRoundImageview.setVisibility(0);
                    newRoundImageview.setRectAdius(6.0f);
                    if (size > 0) {
                        Glide.with(this.context).load(actEnglishImage2).override(Integer.MIN_VALUE, Integer.MIN_VALUE).error(R.drawable.default_image_middle).into(newRoundImageview);
                    }
                    if (this.onItemClickListener != null) {
                        newRoundImageview.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.adapter.T6HomeBannerPageAdapter$$ExternalSyntheticLambda3
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                this.f$0.lambda$instantiateItem$3(i, view);
                            }
                        });
                    }
                }
            } else {
                boolean zEquals2 = "zh_CN".equals(UserInforUtils.getLanguageLocaleString(CommonUtils.getAppContext()));
                List<D4shBannerData.BannerData> list8 = this.dataList;
                if (list8.get(list8.size() > 1 ? i2 : i).getImageRet() != null) {
                    List<D4shBannerData.BannerData> list9 = this.dataList;
                    int size3 = list9.size();
                    if (zEquals2) {
                        actEnglishImage = list9.get(size3 > 1 ? i2 : i).getImageRet().getActChineseImage();
                    } else {
                        actEnglishImage = list9.get(size3 > 1 ? i2 : i).getImageRet().getActEnglishImage();
                    }
                    if (TextUtils.isEmpty(actEnglishImage)) {
                        if (zEquals2) {
                            List<D4shBannerData.BannerData> list10 = this.dataList;
                            if (list10.size() <= 1) {
                                i2 = i;
                            }
                            actChineseImage = list10.get(i2).getImageRet().getActEnglishImage();
                        } else {
                            List<D4shBannerData.BannerData> list11 = this.dataList;
                            if (list11.size() <= 1) {
                                i2 = i;
                            }
                            actChineseImage = list11.get(i2).getImageRet().getActChineseImage();
                        }
                        actEnglishImage = actChineseImage;
                    }
                    newRoundImageview.setVisibility(0);
                    relativeLayout.setVisibility(8);
                    newRoundImageview.setRectAdius(6.0f);
                    if (size > 0) {
                        Glide.with(this.context).load(actEnglishImage).override(Integer.MIN_VALUE, Integer.MIN_VALUE).error(R.drawable.default_image_middle).into(newRoundImageview);
                    }
                    if (this.onItemClickListener != null) {
                        newRoundImageview.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.adapter.T6HomeBannerPageAdapter$$ExternalSyntheticLambda4
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                this.f$0.lambda$instantiateItem$4(i, view);
                            }
                        });
                    }
                }
            }
        }
        viewGroup.addView(viewInflate);
        return viewInflate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$instantiateItem$0(View view) {
        if (this.t6Record.getRealDeviceShared() != null || FamilyUtils.getInstance().checkIsSharedDevice(this.context, this.t6Record.getDeviceId(), this.deviceType)) {
            PetkitToast.showTopToast(CommonUtils.getAppContext(), this.context.getResources().getString(R.string.D4sh_no_cloud_buy_tips), R.drawable.top_toast_warn_icon, 1);
        } else {
            redirectToPurchasePage(this.deviceType, this.t6Record.getDeviceId());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$instantiateItem$1(View view) {
        if (this.t6Record.getRealDeviceShared() != null || FamilyUtils.getInstance().checkIsSharedDevice(this.context, this.t6Record.getDeviceId(), this.deviceType)) {
            PetkitToast.showTopToast(CommonUtils.getAppContext(), this.context.getResources().getString(R.string.D4sh_no_cloud_buy_tips), R.drawable.top_toast_warn_icon, 1);
        } else {
            redirectToPurchasePage(this.deviceType, this.t6Record.getDeviceId());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$instantiateItem$2(View view) {
        if (this.t6Record.getRealDeviceShared() != null || FamilyUtils.getInstance().checkIsSharedDevice(this.context, this.t6Record.getDeviceId(), this.deviceType)) {
            PetkitToast.showTopToast(CommonUtils.getAppContext(), this.context.getResources().getString(R.string.D4sh_no_cloud_buy_tips), R.drawable.top_toast_warn_icon, 1);
        } else {
            redirectToPurchasePage(this.deviceType, this.t6Record.getDeviceId());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$instantiateItem$3(int i, View view) {
        this.onItemClickListener.onBannerItemClick(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$instantiateItem$4(int i, View view) {
        this.onItemClickListener.onBannerItemClick(i);
    }

    public List<D4shBannerData.BannerData> getDataList() {
        return this.dataList;
    }

    public void setDataList(List<D4shBannerData.BannerData> list) {
        this.dataList.clear();
        this.dataList.addAll(list);
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        viewGroup.removeView((View) obj);
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public int getItemPosition(Object obj) {
        return super.getItemPosition(obj);
    }

    private void redirectToPurchasePage(int i, long j) {
        OnItemClickListener onItemClickListener = this.onItemClickListener;
        if (onItemClickListener != null) {
            onItemClickListener.redirectToPurchase(i, j);
        }
    }
}

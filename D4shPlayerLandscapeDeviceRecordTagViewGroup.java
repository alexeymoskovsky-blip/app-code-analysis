package com.petkit.android.activities.petkitBleDevice.d4sh.widget;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.widget.PetkitToast;
import com.petkit.android.activities.cloudservice.CloudServiceWebViewActivity;
import com.petkit.android.activities.petkitBleDevice.d4sh.mode.D4shBannerData;
import com.petkit.android.activities.petkitBleDevice.d4sh.mode.D4shRecord;
import com.petkit.android.activities.petkitBleDevice.d4sh.utils.D4shUtils;
import com.petkit.android.activities.petkitBleDevice.t4.widget.RoundImageview;
import com.petkit.android.activities.petkitBleDevice.widget.petkit.BasePetkitDeviceRecordTagViewGroup;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.Constants;
import com.petkit.android.utils.DateUtil;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.oversea.R;
import java.text.SimpleDateFormat;
import java.util.Date;

/* JADX INFO: loaded from: classes4.dex */
public class D4shPlayerLandscapeDeviceRecordTagViewGroup extends BasePetkitDeviceRecordTagViewGroup {
    private SimpleDateFormat dateFormat;
    private RoundImageview ivActivityPic;
    private ImageView ivClose;
    private RelativeLayout rlTipPanel;
    private TextView tvImmediateRenewal;
    private TextView tvName;
    private TextView tvTime;

    @Override // com.petkit.android.activities.petkitBleDevice.widget.petkit.BasePetkitDeviceRecordTagViewGroup
    public boolean showBg() {
        return false;
    }

    public D4shPlayerLandscapeDeviceRecordTagViewGroup(Context context) {
        super(context);
    }

    public D4shPlayerLandscapeDeviceRecordTagViewGroup(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public D4shPlayerLandscapeDeviceRecordTagViewGroup(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // com.petkit.android.activities.petkitBleDevice.widget.petkit.BasePetkitDeviceRecordTagViewGroup
    public void initCustomSetting() {
        this.topDateTitleView.setVisibility(0);
        setD4shDeviceRecordDateViewParams(-2, Integer.valueOf(ArmsUtils.dip2px(getContext(), 10.0f)), Integer.valueOf(R.style.New_Style_Content_18_Light_White_With_Bold), Integer.valueOf(R.drawable.d4sh_device_record_date_white_icon));
        setDeviceRecordTypeNumberViewVisible(null, null, null, Boolean.FALSE, null);
    }

    public void setTipState(D4shRecord d4shRecord) {
        this.dateFormat = new SimpleDateFormat(DateUtil.DATE_FORMAT_7);
        this.rlTipPanel = (RelativeLayout) findViewById(R.id.rl_tip_panel);
        this.tvName = (TextView) findViewById(R.id.tv_name);
        this.tvTime = (TextView) findViewById(R.id.tv_time);
        this.tvImmediateRenewal = (TextView) findViewById(R.id.tv_immediate_renewal);
        this.ivClose = (ImageView) findViewById(R.id.iv_close);
        this.ivActivityPic = (RoundImageview) findViewById(R.id.iv_activity_pic);
        showPurchaseEntry(d4shRecord);
    }

    private void showPurchaseEntry(final D4shRecord d4shRecord) {
        int i = d4shRecord.getTypeCode() == 0 ? 25 : 26;
        DataHelper.getIntergerSF(getContext(), Constants.D4H_D4SH_FREE_TRIAL_LEFT_TIME_PROMPT_COUNT + i + "_" + d4shRecord.getDeviceId(), 2);
        String sysMap = CommonUtils.getSysMap(this.mContext, "D4SH_D4H_BANNER_DATA:" + d4shRecord.getDeviceId() + ":" + d4shRecord.getTypeCode(), "");
        D4shBannerData d4shBannerData = (sysMap == null || sysMap.isEmpty()) ? null : (D4shBannerData) new Gson().fromJson(sysMap, D4shBannerData.class);
        this.ivActivityPic.setVisibility(8);
        this.ivActivityPic.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shPlayerLandscapeDeviceRecordTagViewGroup.1
            public AnonymousClass1() {
            }

            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                ViewGroup.LayoutParams layoutParams = D4shPlayerLandscapeDeviceRecordTagViewGroup.this.ivActivityPic.getLayoutParams();
                layoutParams.height = (int) (D4shPlayerLandscapeDeviceRecordTagViewGroup.this.ivActivityPic.getWidth() / 5.5f);
                D4shPlayerLandscapeDeviceRecordTagViewGroup.this.ivActivityPic.setLayoutParams(layoutParams);
                D4shPlayerLandscapeDeviceRecordTagViewGroup.this.ivActivityPic.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        int serviceStatus = d4shRecord.getServiceStatus();
        if (serviceStatus == 0) {
            if (d4shBannerData != null && d4shBannerData.getFreeActivity() != null && d4shBannerData.getFreeActivity().getImageRet() != null) {
                this.rlTipPanel.setVisibility(8);
                this.ivActivityPic.setVisibility(0);
                boolean zEquals = "zh_CN".equals(UserInforUtils.getLanguageLocaleString(CommonUtils.getAppContext()));
                D4shBannerData.ImageRet imageRet = d4shBannerData.getFreeActivity().getImageRet();
                String actChineseImage = zEquals ? imageRet.getActChineseImage() : imageRet.getActEnglishImage();
                if (TextUtils.isEmpty(actChineseImage)) {
                    actChineseImage = zEquals ? d4shBannerData.getFreeActivity().getImageRet().getActEnglishImage() : d4shBannerData.getFreeActivity().getImageRet().getActChineseImage();
                }
                this.ivActivityPic.setRectAdius(6.0f);
                Glide.with(this.mContext).load(actChineseImage).override(Integer.MIN_VALUE, Integer.MIN_VALUE).error(R.drawable.default_image_middle).into(this.ivActivityPic);
                this.ivActivityPic.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shPlayerLandscapeDeviceRecordTagViewGroup$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$showPurchaseEntry$0(d4shRecord, view);
                    }
                });
                return;
            }
            this.tvName.setText(R.string.Petkit_care_plus_services);
            this.tvTime.setText(R.string.Subscribe_service_prompt);
            this.tvImmediateRenewal.setText(R.string.Open_immediately);
            this.tvImmediateRenewal.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shPlayerLandscapeDeviceRecordTagViewGroup$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$showPurchaseEntry$1(d4shRecord, view);
                }
            });
            this.rlTipPanel.setVisibility(0);
            this.ivClose.setVisibility(8);
            this.ivClose.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shPlayerLandscapeDeviceRecordTagViewGroup.2
                final /* synthetic */ D4shRecord val$d4shRecord;
                final /* synthetic */ int val$deviceType;

                public AnonymousClass2(int i2, final D4shRecord d4shRecord2) {
                    i = i2;
                    d4shRecord = d4shRecord2;
                }

                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    D4shPlayerLandscapeDeviceRecordTagViewGroup.this.rlTipPanel.setVisibility(8);
                    DataHelper.setBooleanSF(D4shPlayerLandscapeDeviceRecordTagViewGroup.this.getContext(), Constants.D4H_D4SH_DEVICE_HOME_FREE_TRIAL_TIP + i + "_" + d4shRecord.getDeviceId(), Boolean.TRUE);
                }
            });
            return;
        }
        if (serviceStatus == 1) {
            this.ivActivityPic.setVisibility(8);
            if (d4shRecord2.getMoreService() == 1 || d4shRecord2.getCloudProduct().getSubscribe() == 1) {
                this.rlTipPanel.setVisibility(8);
                return;
            }
            if ((Long.parseLong(d4shRecord2.getCloudProduct().getWorkIndate()) * 1000) - System.currentTimeMillis() <= 259200000) {
                this.tvName.setText(R.string.Petkit_care_is_about_to_expire);
                this.tvTime.setText(getResources().getString(R.string.Period_of_validity, this.dateFormat.format(new Date(Long.parseLong(d4shRecord2.getCloudProduct().getWorkIndate()) * 1000))));
                this.tvImmediateRenewal.setText(getResources().getString(R.string.Immediate_renewal));
                this.tvImmediateRenewal.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shPlayerLandscapeDeviceRecordTagViewGroup$$ExternalSyntheticLambda4
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.lambda$showPurchaseEntry$4(d4shRecord2, view);
                    }
                });
                this.rlTipPanel.setVisibility(0);
                this.ivClose.setVisibility(8);
                this.ivClose.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shPlayerLandscapeDeviceRecordTagViewGroup.4
                    final /* synthetic */ D4shRecord val$d4shRecord;

                    public AnonymousClass4(final D4shRecord d4shRecord2) {
                        d4shRecord = d4shRecord2;
                    }

                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        Context context = D4shPlayerLandscapeDeviceRecordTagViewGroup.this.getContext();
                        StringBuilder sb = new StringBuilder();
                        sb.append(d4shRecord.getDeviceId());
                        sb.append("_");
                        sb.append(d4shRecord.getTypeCode() == 0 ? 25 : 26);
                        sb.append("_");
                        sb.append(d4shRecord.getCloudProduct().getServiceId());
                        DataHelper.setStringSF(context, sb.toString(), "close");
                        D4shPlayerLandscapeDeviceRecordTagViewGroup.this.rlTipPanel.setVisibility(8);
                    }
                });
                return;
            }
            this.rlTipPanel.setVisibility(8);
            return;
        }
        if (serviceStatus != 2) {
            return;
        }
        if (d4shBannerData != null && d4shBannerData.getFreeActivity() != null && d4shBannerData.getFreeActivity().getImageRet() != null) {
            this.rlTipPanel.setVisibility(8);
            this.ivActivityPic.setVisibility(0);
            boolean zEquals2 = "zh_CN".equals(UserInforUtils.getLanguageLocaleString(CommonUtils.getAppContext()));
            D4shBannerData.ImageRet imageRet2 = d4shBannerData.getFreeActivity().getImageRet();
            String actChineseImage2 = zEquals2 ? imageRet2.getActChineseImage() : imageRet2.getActEnglishImage();
            if (TextUtils.isEmpty(actChineseImage2)) {
                actChineseImage2 = zEquals2 ? d4shBannerData.getFreeActivity().getImageRet().getActEnglishImage() : d4shBannerData.getFreeActivity().getImageRet().getActChineseImage();
            }
            this.ivActivityPic.setRectAdius(6.0f);
            Glide.with(this.mContext).load(actChineseImage2).override(Integer.MIN_VALUE, Integer.MIN_VALUE).error(R.drawable.default_image_middle).into(this.ivActivityPic);
            this.ivActivityPic.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shPlayerLandscapeDeviceRecordTagViewGroup$$ExternalSyntheticLambda2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$showPurchaseEntry$2(d4shRecord2, view);
                }
            });
            return;
        }
        this.tvName.setText(R.string.Service_expired_on);
        this.tvTime.setText(getResources().getString(R.string.Expired_on_prompt, this.dateFormat.format(new Date(Long.parseLong(d4shRecord2.getCloudProduct().getWorkIndate()) * 1000))));
        this.tvImmediateRenewal.setText(R.string.Immediate_renewal);
        this.tvImmediateRenewal.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shPlayerLandscapeDeviceRecordTagViewGroup$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$showPurchaseEntry$3(d4shRecord2, view);
            }
        });
        this.rlTipPanel.setVisibility(0);
        this.ivClose.setVisibility(8);
        this.ivClose.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shPlayerLandscapeDeviceRecordTagViewGroup.3
            final /* synthetic */ D4shRecord val$d4shRecord;

            public AnonymousClass3(final D4shRecord d4shRecord2) {
                d4shRecord = d4shRecord2;
            }

            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Context context = D4shPlayerLandscapeDeviceRecordTagViewGroup.this.getContext();
                StringBuilder sb = new StringBuilder();
                sb.append(d4shRecord.getDeviceId());
                sb.append("_");
                sb.append(d4shRecord.getTypeCode() == 0 ? 25 : 26);
                sb.append("_");
                sb.append(d4shRecord.getCloudProduct().getServiceId());
                DataHelper.setStringSF(context, sb.toString(), "close");
                D4shPlayerLandscapeDeviceRecordTagViewGroup.this.rlTipPanel.setVisibility(8);
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shPlayerLandscapeDeviceRecordTagViewGroup$1 */
    public class AnonymousClass1 implements ViewTreeObserver.OnGlobalLayoutListener {
        public AnonymousClass1() {
        }

        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public void onGlobalLayout() {
            ViewGroup.LayoutParams layoutParams = D4shPlayerLandscapeDeviceRecordTagViewGroup.this.ivActivityPic.getLayoutParams();
            layoutParams.height = (int) (D4shPlayerLandscapeDeviceRecordTagViewGroup.this.ivActivityPic.getWidth() / 5.5f);
            D4shPlayerLandscapeDeviceRecordTagViewGroup.this.ivActivityPic.setLayoutParams(layoutParams);
            D4shPlayerLandscapeDeviceRecordTagViewGroup.this.ivActivityPic.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }
    }

    public /* synthetic */ void lambda$showPurchaseEntry$0(D4shRecord d4shRecord, View view) {
        if (d4shRecord.getRealDeviceShared() != null) {
            PetkitToast.showTopToast(CommonUtils.getAppContext(), this.mContext.getResources().getString(R.string.D4sh_no_cloud_free_tips), R.drawable.top_toast_warn_icon, 1);
        } else {
            redirectToPurchasePage(d4shRecord.getTypeCode() == 0 ? 25 : 26, d4shRecord.getDeviceId());
        }
    }

    public /* synthetic */ void lambda$showPurchaseEntry$1(D4shRecord d4shRecord, View view) {
        if (d4shRecord.getRealDeviceShared() != null) {
            PetkitToast.showTopToast(CommonUtils.getAppContext(), this.mContext.getResources().getString(R.string.D4sh_no_cloud_free_tips), R.drawable.top_toast_warn_icon, 1);
        } else {
            redirectToPurchasePage(d4shRecord.getTypeCode() == 0 ? 25 : 26, d4shRecord.getDeviceId());
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shPlayerLandscapeDeviceRecordTagViewGroup$2 */
    public class AnonymousClass2 implements View.OnClickListener {
        final /* synthetic */ D4shRecord val$d4shRecord;
        final /* synthetic */ int val$deviceType;

        public AnonymousClass2(int i2, final D4shRecord d4shRecord2) {
            i = i2;
            d4shRecord = d4shRecord2;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            D4shPlayerLandscapeDeviceRecordTagViewGroup.this.rlTipPanel.setVisibility(8);
            DataHelper.setBooleanSF(D4shPlayerLandscapeDeviceRecordTagViewGroup.this.getContext(), Constants.D4H_D4SH_DEVICE_HOME_FREE_TRIAL_TIP + i + "_" + d4shRecord.getDeviceId(), Boolean.TRUE);
        }
    }

    public /* synthetic */ void lambda$showPurchaseEntry$2(D4shRecord d4shRecord, View view) {
        if (d4shRecord.getRealDeviceShared() != null) {
            PetkitToast.showTopToast(CommonUtils.getAppContext(), this.mContext.getResources().getString(R.string.D4sh_no_cloud_free_tips), R.drawable.top_toast_warn_icon, 1);
        } else {
            redirectToPurchasePage(d4shRecord.getTypeCode() == 0 ? 25 : 26, d4shRecord.getDeviceId());
        }
    }

    public /* synthetic */ void lambda$showPurchaseEntry$3(D4shRecord d4shRecord, View view) {
        if (d4shRecord.getRealDeviceShared() != null) {
            PetkitToast.showTopToast(CommonUtils.getAppContext(), this.mContext.getResources().getString(R.string.D4sh_no_cloud_buy_tips), R.drawable.top_toast_warn_icon, 1);
        } else {
            redirectToPurchasePage(d4shRecord.getTypeCode() == 0 ? 25 : 26, d4shRecord.getDeviceId());
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shPlayerLandscapeDeviceRecordTagViewGroup$3 */
    public class AnonymousClass3 implements View.OnClickListener {
        final /* synthetic */ D4shRecord val$d4shRecord;

        public AnonymousClass3(final D4shRecord d4shRecord2) {
            d4shRecord = d4shRecord2;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            Context context = D4shPlayerLandscapeDeviceRecordTagViewGroup.this.getContext();
            StringBuilder sb = new StringBuilder();
            sb.append(d4shRecord.getDeviceId());
            sb.append("_");
            sb.append(d4shRecord.getTypeCode() == 0 ? 25 : 26);
            sb.append("_");
            sb.append(d4shRecord.getCloudProduct().getServiceId());
            DataHelper.setStringSF(context, sb.toString(), "close");
            D4shPlayerLandscapeDeviceRecordTagViewGroup.this.rlTipPanel.setVisibility(8);
        }
    }

    public /* synthetic */ void lambda$showPurchaseEntry$4(D4shRecord d4shRecord, View view) {
        if (d4shRecord.getRealDeviceShared() != null) {
            PetkitToast.showTopToast(CommonUtils.getAppContext(), this.mContext.getResources().getString(R.string.D4sh_no_cloud_buy_tips), R.drawable.top_toast_warn_icon, 1);
        } else {
            redirectToPurchasePage(d4shRecord.getTypeCode() == 0 ? 25 : 26, d4shRecord.getDeviceId());
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.d4sh.widget.D4shPlayerLandscapeDeviceRecordTagViewGroup$4 */
    public class AnonymousClass4 implements View.OnClickListener {
        final /* synthetic */ D4shRecord val$d4shRecord;

        public AnonymousClass4(final D4shRecord d4shRecord2) {
            d4shRecord = d4shRecord2;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            Context context = D4shPlayerLandscapeDeviceRecordTagViewGroup.this.getContext();
            StringBuilder sb = new StringBuilder();
            sb.append(d4shRecord.getDeviceId());
            sb.append("_");
            sb.append(d4shRecord.getTypeCode() == 0 ? 25 : 26);
            sb.append("_");
            sb.append(d4shRecord.getCloudProduct().getServiceId());
            DataHelper.setStringSF(context, sb.toString(), "close");
            D4shPlayerLandscapeDeviceRecordTagViewGroup.this.rlTipPanel.setVisibility(8);
        }
    }

    private void redirectToPurchasePage(int i, long j) {
        String strCreatePurchasePageUrl = D4shUtils.createPurchasePageUrl(this.mContext, i, j);
        if (TextUtils.isEmpty(strCreatePurchasePageUrl)) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString(Constants.EXTRA_LOAD_PATH, strCreatePurchasePageUrl);
        Intent intent = new Intent(this.mContext, (Class<?>) CloudServiceWebViewActivity.class);
        intent.putExtras(bundle);
        this.mContext.startActivity(intent);
    }
}

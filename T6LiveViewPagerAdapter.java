package com.petkit.android.activities.petkitBleDevice.t6.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.text.SpannableString;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import com.jess.arms.utils.Consts;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.cozy.widget.EasyPopupWindow;
import com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity;
import com.petkit.android.activities.petkitBleDevice.t6.mode.T6Record;
import com.petkit.android.activities.petkitBleDevice.t6.utils.T6Utils;
import com.petkit.android.activities.petkitBleDevice.t6.widget.BagProgressView;
import com.petkit.android.activities.petkitBleDevice.widget.SimpleTipWindow;
import com.petkit.android.activities.statistics.ToiletStatisticsActivity;
import com.petkit.android.utils.CommonUtils;
import com.petkit.oversea.R;
import razerdp.util.SimpleAnimationUtils;

/* JADX INFO: loaded from: classes5.dex */
public class T6LiveViewPagerAdapter extends PagerAdapter {
    public AlphaAnimation alpha;
    public AlphaAnimation alphaAnimation;
    public AnimationDrawable animationDrawable;
    public SpannableString autoCleanStr;
    public BagProgressView bpv;
    public BagProgressView bpvError;
    public final Context context;
    public int currentPosition = 0;
    public SimpleTipWindow disableCleanUpWindow;
    public RelativeLayout fistLiveView;
    public T6HomeActivity.MyHandler handler;
    public boolean isAni;
    public boolean isLive;
    public boolean isNeedShowAni;
    public ImageView ivAutoClean;
    public ImageView ivBlock;
    public ImageView ivCatLitter;
    public ImageView ivCenterDeviceStateAniIcon;
    public ImageView ivCenterDeviceStateBlockErrorIcon;
    public ImageView ivCenterDeviceStateBoxErrorIcon;
    public ImageView ivCenterDeviceStateCatLitterErrorIcon;
    public ImageView ivCenterDeviceStateErrorIcon;
    public ImageView ivCenterDeviceStateGarbagebagsErrorIcon;
    public ImageView ivCenterDeviceStateIcon;
    public ImageView ivErrorBlock;
    public ImageView ivErrorBox;
    public ImageView ivErrorCatLitter;
    public ImageView ivErrorGarbageBags;
    public ImageView ivGarbageBags;
    public ImageView ivKittenProtection;
    public ImageView ivRegularClean;
    public ImageView ivRightArrow;
    public ImageView ivRightErrorArrow;
    public SpannableString kittenCleanStr;
    public LinearLayout llErrorPercent;
    public LinearLayout llPercent;
    public LinearLayout llSmartSettingPanelOne;
    public LinearLayout llSmartSettingPanelTwo;
    public OnClickListener onClickListener;
    public View pointGuideView;
    public View pointGuideViewCatLitter;
    public View pointGuideViewErrorCatLitter;
    public View pointGuideViewErrorGarbageBags;
    public View pointGuideViewGarbageBags;
    public int position;
    public SpannableString regularCleanStr;
    public RelativeLayout rlAutoCleanParent;
    public RelativeLayout rlBlock;
    public RelativeLayout rlCatLitter;
    public RelativeLayout rlErrorBlock;
    public RelativeLayout rlErrorBox;
    public RelativeLayout rlErrorCatLitter;
    public RelativeLayout rlErrorGarbageBags;
    public RelativeLayout rlGarbageBags;
    public RelativeLayout rlKittenProtectionParent;
    public RelativeLayout rlParent;
    public RelativeLayout rlRegularCleanParent;
    public T6Record t6Record;
    public TextView tvAutoClean;
    public TextView tvBlock;
    public TextView tvCatLitterErrorState;
    public TextView tvCatLitterState;
    public TextView tvErrorBlock;
    public TextView tvErrorBox;
    public TextView tvErrorGarbageBags;
    public TextView tvGarbageBags;
    public TextView tvKittenProtection;
    public TextView tvRegularClean;
    public TextView tvTodayAvgDuration;
    public TextView tvTodayAvgDurationPrompt;
    public TextView tvTodayTimes;
    public TextView tvTodayTimesPrompt;
    public RelativeLayout videoTagLayout;

    public interface OnClickListener {
        void jumpToRegularClean();

        void jumpToSmartSetting();

        void onBlockClick();

        void onBoxClick();

        void onCatLitterClick();

        void onPackageClick();

        void updateDeviceSettings(String str, boolean z);
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public int getCount() {
        return 2;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public boolean isViewFromObject(@NonNull View view, @NonNull Object obj) {
        return view == obj;
    }

    public void setLive(boolean z) {
        this.isLive = z;
    }

    public void setCurrentPosition(int i) {
        this.currentPosition = i;
    }

    public T6LiveViewPagerAdapter(Context context, RelativeLayout relativeLayout, T6HomeActivity.MyHandler myHandler) {
        this.context = context;
        this.fistLiveView = relativeLayout;
        this.handler = myHandler;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    @NonNull
    public Object instantiateItem(@NonNull ViewGroup viewGroup, int i) {
        this.position = i;
        if (i == 0) {
            LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(this.context).inflate(R.layout.layout_t6_live_view, (ViewGroup) null);
            linearLayout.addView(this.fistLiveView);
            viewGroup.addView(linearLayout);
            return linearLayout;
        }
        RelativeLayout relativeLayout = (RelativeLayout) LayoutInflater.from(this.context).inflate(R.layout.layout_t6_live_device_state, (ViewGroup) null);
        this.rlParent = relativeLayout;
        this.llSmartSettingPanelOne = (LinearLayout) relativeLayout.findViewById(R.id.ll_smart_setting_panel_one);
        this.llSmartSettingPanelTwo = (LinearLayout) this.rlParent.findViewById(R.id.ll_smart_setting_panel_two);
        this.tvKittenProtection = (TextView) this.rlParent.findViewById(R.id.tv_kitten_protection);
        this.ivKittenProtection = (ImageView) this.rlParent.findViewById(R.id.iv_kitten_protection);
        this.tvRegularClean = (TextView) this.rlParent.findViewById(R.id.tv_regular_clean);
        this.ivRegularClean = (ImageView) this.rlParent.findViewById(R.id.iv_regular_clean);
        this.ivAutoClean = (ImageView) this.rlParent.findViewById(R.id.iv_auto_clean);
        this.tvAutoClean = (TextView) this.rlParent.findViewById(R.id.tv_auto_clean);
        this.rlRegularCleanParent = (RelativeLayout) this.rlParent.findViewById(R.id.rl_regular_clean_parent);
        this.rlKittenProtectionParent = (RelativeLayout) this.rlParent.findViewById(R.id.rl_kitten_protection_parent);
        this.rlAutoCleanParent = (RelativeLayout) this.rlParent.findViewById(R.id.rl_auto_clean_parent);
        this.ivCenterDeviceStateAniIcon = (ImageView) this.rlParent.findViewById(R.id.iv_center_device_state_ani_icon);
        this.videoTagLayout = (RelativeLayout) this.rlParent.findViewById(R.id.video_tag_layout);
        this.ivCenterDeviceStateIcon = (ImageView) this.rlParent.findViewById(R.id.iv_center_device_state_icon);
        this.rlBlock = (RelativeLayout) this.rlParent.findViewById(R.id.rl_block);
        this.ivBlock = (ImageView) this.rlParent.findViewById(R.id.iv_block);
        this.tvBlock = (TextView) this.rlParent.findViewById(R.id.tv_block);
        this.rlGarbageBags = (RelativeLayout) this.rlParent.findViewById(R.id.rl_garbage_bags);
        this.ivGarbageBags = (ImageView) this.rlParent.findViewById(R.id.iv_garbage_bags);
        this.pointGuideViewGarbageBags = this.rlParent.findViewById(R.id.point_guide_view_garbage_bags);
        this.llPercent = (LinearLayout) this.rlParent.findViewById(R.id.ll_percent);
        this.tvGarbageBags = (TextView) this.rlParent.findViewById(R.id.tv_garbage_bags);
        this.bpv = (BagProgressView) this.rlParent.findViewById(R.id.bpv_view);
        this.ivRightArrow = (ImageView) this.rlParent.findViewById(R.id.iv_right_arrow);
        this.rlCatLitter = (RelativeLayout) this.rlParent.findViewById(R.id.rl_cat_litter);
        this.ivCatLitter = (ImageView) this.rlParent.findViewById(R.id.iv_cat_litter);
        this.pointGuideViewCatLitter = this.rlParent.findViewById(R.id.point_guide_view_cat_litter);
        this.tvCatLitterState = (TextView) this.rlParent.findViewById(R.id.tv_cat_litter_state);
        this.tvTodayTimes = (TextView) this.rlParent.findViewById(R.id.tv_today_times_into);
        this.tvTodayTimesPrompt = (TextView) this.rlParent.findViewById(R.id.tv_today_times_prompt_into);
        this.tvTodayAvgDuration = (TextView) this.rlParent.findViewById(R.id.tv_today_avg_duration_into);
        this.tvTodayAvgDurationPrompt = (TextView) this.rlParent.findViewById(R.id.tv_today_avg_duration_prompt_into);
        this.ivCenterDeviceStateErrorIcon = (ImageView) this.rlParent.findViewById(R.id.iv_center_device_state_error_icon);
        this.ivCenterDeviceStateGarbagebagsErrorIcon = (ImageView) this.rlParent.findViewById(R.id.iv_center_device_state_garbagebags_error_icon);
        this.ivCenterDeviceStateBoxErrorIcon = (ImageView) this.rlParent.findViewById(R.id.iv_center_device_state_box_error_icon);
        this.ivCenterDeviceStateCatLitterErrorIcon = (ImageView) this.rlParent.findViewById(R.id.iv_center_device_state_cat_litter_error_icon);
        this.ivCenterDeviceStateBlockErrorIcon = (ImageView) this.rlParent.findViewById(R.id.iv_center_device_state_block_error_icon);
        this.rlErrorBox = (RelativeLayout) this.rlParent.findViewById(R.id.rl_error_box);
        this.ivErrorBox = (ImageView) this.rlParent.findViewById(R.id.iv_error_box);
        this.pointGuideView = this.rlParent.findViewById(R.id.point_guide_view);
        this.tvErrorBox = (TextView) this.rlParent.findViewById(R.id.tv_error_box);
        this.rlErrorCatLitter = (RelativeLayout) this.rlParent.findViewById(R.id.rl_error_cat_litter);
        this.ivErrorCatLitter = (ImageView) this.rlParent.findViewById(R.id.iv_error_cat_litter);
        this.pointGuideViewErrorCatLitter = this.rlParent.findViewById(R.id.point_guide_view_error_cat_litter);
        this.tvCatLitterErrorState = (TextView) this.rlParent.findViewById(R.id.tv_cat_litter_error_state);
        this.rlErrorBlock = (RelativeLayout) this.rlParent.findViewById(R.id.rl_error_block);
        this.ivErrorBlock = (ImageView) this.rlParent.findViewById(R.id.iv_error_block);
        this.tvErrorBlock = (TextView) this.rlParent.findViewById(R.id.tv_error_block);
        this.rlErrorGarbageBags = (RelativeLayout) this.rlParent.findViewById(R.id.rl_error_garbage_bags);
        this.ivErrorGarbageBags = (ImageView) this.rlParent.findViewById(R.id.iv_error_garbage_bags);
        this.pointGuideViewErrorGarbageBags = this.rlParent.findViewById(R.id.point_guide_view_error_garbage_bags);
        this.llErrorPercent = (LinearLayout) this.rlParent.findViewById(R.id.ll_error_percent);
        this.tvErrorGarbageBags = (TextView) this.rlParent.findViewById(R.id.tv_error_garbage_bags);
        this.bpvError = (BagProgressView) this.rlParent.findViewById(R.id.bpv_error);
        this.ivRightErrorArrow = (ImageView) this.rlParent.findViewById(R.id.iv_right_error_arrow);
        initData();
        viewGroup.addView(this.rlParent);
        return this.rlParent;
    }

    private void initData() {
        T6Record t6Record = this.t6Record;
        if (t6Record == null || this.rlParent == null) {
            return;
        }
        this.tvTodayTimes.setText(T6Utils.getT6HomeTimesString(t6Record.getInTimes()));
        this.tvTodayAvgDuration.setText(T6Utils.getT6HomeString(this.t6Record.getTotalTime() / (this.t6Record.getInTimes() == 0 ? 1 : this.t6Record.getInTimes())));
        String string = this.context.getResources().getString(R.string.Switch_open_state);
        SpannableString spannableString = new SpannableString(this.context.getResources().getString(R.string.Auto_clean) + "\n" + string);
        this.autoCleanStr = spannableString;
        spannableString.setSpan(new ForegroundColorSpan(this.context.getResources().getColor(R.color.gray)), this.autoCleanStr.toString().indexOf(string), this.autoCleanStr.toString().indexOf(string) + string.length(), 33);
        this.autoCleanStr.setSpan(new StyleSpan(0), this.autoCleanStr.toString().indexOf(string), this.autoCleanStr.toString().indexOf(string) + string.length(), 33);
        SpannableString spannableString2 = new SpannableString(this.context.getResources().getString(R.string.Regular_clean) + "\n" + string);
        this.regularCleanStr = spannableString2;
        spannableString2.setSpan(new ForegroundColorSpan(this.context.getResources().getColor(R.color.gray)), this.regularCleanStr.toString().indexOf(string), this.regularCleanStr.toString().indexOf(string) + string.length(), 33);
        this.regularCleanStr.setSpan(new StyleSpan(0), this.regularCleanStr.toString().indexOf(string), this.regularCleanStr.toString().indexOf(string) + string.length(), 33);
        SpannableString spannableString3 = new SpannableString(this.context.getResources().getString(R.string.Kitten_protection) + "\n" + string);
        this.kittenCleanStr = spannableString3;
        spannableString3.setSpan(new ForegroundColorSpan(this.context.getResources().getColor(R.color.gray)), this.kittenCleanStr.toString().indexOf(string), this.kittenCleanStr.toString().indexOf(string) + string.length(), 33);
        this.kittenCleanStr.setSpan(new StyleSpan(0), this.kittenCleanStr.toString().indexOf(string), this.kittenCleanStr.toString().indexOf(string) + string.length(), 33);
        if (this.t6Record.getSettings().getAutoWork() == 1 && this.t6Record.getSettings().getKitten() != 1) {
            this.tvAutoClean.setText(this.autoCleanStr);
            this.ivAutoClean.setImageResource(R.drawable.auto_clean_icon);
        } else {
            this.tvAutoClean.setText(this.context.getResources().getString(R.string.Auto_clean));
            this.ivAutoClean.setImageResource(R.drawable.auto_clean_gray_icon);
        }
        if (this.t6Record.getSettings().getFixedTimeClear() == 1 && this.t6Record.getSettings().getKitten() != 1) {
            this.tvRegularClean.setText(this.regularCleanStr);
            this.ivRegularClean.setImageResource(R.drawable.regular_clean_icon);
        } else {
            this.tvRegularClean.setText(this.context.getResources().getString(R.string.Regular_clean));
            this.ivRegularClean.setImageResource(R.drawable.regular_clean_gray_icon);
        }
        if (this.t6Record.getSettings().getKitten() == 1) {
            this.tvKittenProtection.setText(this.kittenCleanStr);
            this.ivKittenProtection.setImageResource(R.drawable.kitten_protection_icon);
        } else {
            this.tvKittenProtection.setText(this.context.getResources().getString(R.string.Kitten_protection));
            this.ivKittenProtection.setImageResource(R.drawable.kitten_protection_gray_icon);
        }
        this.rlAutoCleanParent.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.adapter.T6LiveViewPagerAdapter$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initData$0(view);
            }
        });
        this.rlKittenProtectionParent.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.adapter.T6LiveViewPagerAdapter$$ExternalSyntheticLambda6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initData$1(view);
            }
        });
        this.rlRegularCleanParent.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.adapter.T6LiveViewPagerAdapter$$ExternalSyntheticLambda7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initData$2(view);
            }
        });
        this.ivAutoClean.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.adapter.T6LiveViewPagerAdapter$$ExternalSyntheticLambda8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initData$3(view);
            }
        });
        this.ivRegularClean.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.adapter.T6LiveViewPagerAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (T6LiveViewPagerAdapter.this.t6Record.getSettings().getKitten() == 1) {
                    T6LiveViewPagerAdapter.this.showDisableCleanUpWindow();
                } else if (T6LiveViewPagerAdapter.this.onClickListener != null) {
                    T6LiveViewPagerAdapter.this.onClickListener.updateDeviceSettings("fixedTimeClear", T6LiveViewPagerAdapter.this.t6Record.getSettings().getFixedTimeClear() != 1);
                }
            }
        });
        this.ivKittenProtection.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.adapter.T6LiveViewPagerAdapter.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                T6LiveViewPagerAdapter t6LiveViewPagerAdapter = T6LiveViewPagerAdapter.this;
                t6LiveViewPagerAdapter.t6Record = T6Utils.getT6RecordByDeviceId(t6LiveViewPagerAdapter.t6Record.getDeviceId(), T6LiveViewPagerAdapter.this.t6Record.getTypeCode());
                if (T6LiveViewPagerAdapter.this.t6Record == null || T6LiveViewPagerAdapter.this.t6Record.getSettings().getKitten() != 1) {
                    T6LiveViewPagerAdapter.this.showOpenKittenConfirmWindow();
                } else {
                    T6LiveViewPagerAdapter.this.showKittenConfirmWindow();
                }
            }
        });
        this.tvTodayTimesPrompt.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.adapter.T6LiveViewPagerAdapter$$ExternalSyntheticLambda9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initData$4(view);
            }
        });
        this.tvTodayTimes.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.adapter.T6LiveViewPagerAdapter$$ExternalSyntheticLambda10
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initData$5(view);
            }
        });
        this.tvTodayAvgDuration.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.adapter.T6LiveViewPagerAdapter$$ExternalSyntheticLambda11
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initData$6(view);
            }
        });
        this.tvTodayAvgDurationPrompt.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.adapter.T6LiveViewPagerAdapter$$ExternalSyntheticLambda12
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initData$7(view);
            }
        });
        this.rlBlock.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.adapter.T6LiveViewPagerAdapter$$ExternalSyntheticLambda13
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initData$8(view);
            }
        });
        this.rlErrorBlock.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.adapter.T6LiveViewPagerAdapter$$ExternalSyntheticLambda14
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initData$9(view);
            }
        });
        this.rlErrorBox.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.adapter.T6LiveViewPagerAdapter$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initData$10(view);
            }
        });
        this.rlErrorCatLitter.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.adapter.T6LiveViewPagerAdapter$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initData$11(view);
            }
        });
        this.rlCatLitter.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.adapter.T6LiveViewPagerAdapter$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initData$12(view);
            }
        });
        this.rlErrorGarbageBags.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.adapter.T6LiveViewPagerAdapter$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initData$13(view);
            }
        });
        this.rlGarbageBags.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.adapter.T6LiveViewPagerAdapter$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initData$14(view);
            }
        });
        if (this.t6Record.getRealDeviceShared() != null) {
            this.tvTodayTimesPrompt.setText(this.context.getResources().getString(R.string.Toilet_today_times));
            this.tvTodayAvgDurationPrompt.setText(this.context.getResources().getString(R.string.Toilet_average_duration));
        } else {
            this.tvTodayTimesPrompt.setText(this.context.getResources().getString(R.string.Toilet_today_times) + " >");
            this.tvTodayAvgDurationPrompt.setText(this.context.getResources().getString(R.string.Toilet_average_duration) + " >");
        }
        if (this.t6Record.getState().getOverall() == 2 || this.t6Record.getState().getPower() != 1) {
            offlineState();
        } else if ((this.t6Record.getState().getPackageState() != null && (this.t6Record.getState().getPackageState().intValue() == 1 || this.t6Record.getState().getPackageState().intValue() == 2 || this.t6Record.getState().getPackageState().intValue() == 3 || this.t6Record.getState().getPackageState().intValue() == 4 || this.t6Record.getState().getPackageState().intValue() == 5 || this.t6Record.getState().getPackageState().intValue() == 6)) || this.t6Record.getPackageTotalCount() - this.t6Record.getPackageUsedCount() <= 1.0f || this.t6Record.getState().getPurificationLeftDays() <= 0 || ((this.t6Record.getState().getBaggingState() != null && this.t6Record.getState().getBaggingState().intValue() == 0) || this.t6Record.getState().isBoxFull())) {
            if (this.currentPosition == 1) {
                startAni();
            } else {
                this.ivCenterDeviceStateAniIcon.setVisibility(4);
                errorState();
            }
            if (this.isLive) {
                this.videoTagLayout.setVisibility(0);
            } else {
                this.videoTagLayout.setVisibility(8);
            }
        } else {
            noErrorState();
            if (this.isLive) {
                this.videoTagLayout.setVisibility(0);
            } else {
                this.videoTagLayout.setVisibility(8);
            }
        }
        if (this.t6Record.getState().getPower() == 1) {
            this.ivCenterDeviceStateIcon.setImageResource(R.drawable.t6_device_state_icon);
        } else {
            this.ivCenterDeviceStateIcon.setImageResource(R.drawable.t6_device_power_off_icon);
        }
    }

    public final /* synthetic */ void lambda$initData$0(View view) {
        OnClickListener onClickListener = this.onClickListener;
        if (onClickListener != null) {
            onClickListener.jumpToSmartSetting();
        }
    }

    public final /* synthetic */ void lambda$initData$1(View view) {
        OnClickListener onClickListener = this.onClickListener;
        if (onClickListener != null) {
            onClickListener.jumpToSmartSetting();
        }
    }

    public final /* synthetic */ void lambda$initData$2(View view) {
        OnClickListener onClickListener = this.onClickListener;
        if (onClickListener != null) {
            onClickListener.jumpToRegularClean();
        }
    }

    public final /* synthetic */ void lambda$initData$3(View view) {
        if (this.t6Record.getSettings().getKitten() == 1) {
            showDisableCleanUpWindow();
            return;
        }
        OnClickListener onClickListener = this.onClickListener;
        if (onClickListener != null) {
            onClickListener.updateDeviceSettings("autoWork", this.t6Record.getSettings().getAutoWork() != 1);
        }
    }

    public final /* synthetic */ void lambda$initData$4(View view) {
        if (this.t6Record.getDeviceShared() == null) {
            Context context = this.context;
            context.startActivity(ToiletStatisticsActivity.newIntent(context));
        }
    }

    public final /* synthetic */ void lambda$initData$5(View view) {
        if (this.t6Record.getDeviceShared() == null) {
            Context context = this.context;
            context.startActivity(ToiletStatisticsActivity.newIntent(context));
        }
    }

    public final /* synthetic */ void lambda$initData$6(View view) {
        if (this.t6Record.getDeviceShared() == null) {
            Context context = this.context;
            context.startActivity(ToiletStatisticsActivity.newIntent(context));
        }
    }

    public final /* synthetic */ void lambda$initData$7(View view) {
        if (this.t6Record.getDeviceShared() == null) {
            Context context = this.context;
            context.startActivity(ToiletStatisticsActivity.newIntent(context));
        }
    }

    public final /* synthetic */ void lambda$initData$8(View view) {
        OnClickListener onClickListener = this.onClickListener;
        if (onClickListener != null) {
            onClickListener.onBlockClick();
        }
    }

    public final /* synthetic */ void lambda$initData$9(View view) {
        OnClickListener onClickListener = this.onClickListener;
        if (onClickListener != null) {
            onClickListener.onBlockClick();
        }
    }

    public final /* synthetic */ void lambda$initData$10(View view) {
        OnClickListener onClickListener = this.onClickListener;
        if (onClickListener != null) {
            onClickListener.onBoxClick();
        }
    }

    public final /* synthetic */ void lambda$initData$11(View view) {
        OnClickListener onClickListener = this.onClickListener;
        if (onClickListener != null) {
            onClickListener.onCatLitterClick();
        }
    }

    public final /* synthetic */ void lambda$initData$12(View view) {
        OnClickListener onClickListener = this.onClickListener;
        if (onClickListener != null) {
            onClickListener.onCatLitterClick();
        }
    }

    public final /* synthetic */ void lambda$initData$13(View view) {
        OnClickListener onClickListener = this.onClickListener;
        if (onClickListener != null) {
            onClickListener.onPackageClick();
        }
    }

    public final /* synthetic */ void lambda$initData$14(View view) {
        OnClickListener onClickListener = this.onClickListener;
        if (onClickListener != null) {
            onClickListener.onPackageClick();
        }
    }

    public void startAni() {
        if (this.isNeedShowAni) {
            if (this.isAni) {
                return;
            }
            errorState();
            return;
        }
        if (this.ivCenterDeviceStateAniIcon != null) {
            this.isNeedShowAni = true;
            this.rlParent.setBackgroundResource(R.drawable.t6_device_bg);
            this.ivCenterDeviceStateIcon.setVisibility(0);
            this.ivCenterDeviceStateErrorIcon.setVisibility(4);
            this.rlBlock.setVisibility(4);
            this.rlCatLitter.setVisibility(4);
            this.rlGarbageBags.setVisibility(4);
            this.rlErrorBlock.setVisibility(4);
            this.rlErrorBox.setVisibility(4);
            this.rlErrorCatLitter.setVisibility(4);
            this.rlErrorGarbageBags.setVisibility(4);
            this.ivCenterDeviceStateGarbagebagsErrorIcon.setVisibility(4);
            this.ivCenterDeviceStateBlockErrorIcon.setVisibility(4);
            this.ivCenterDeviceStateErrorIcon.setVisibility(4);
            this.ivCenterDeviceStateCatLitterErrorIcon.setVisibility(4);
            this.ivCenterDeviceStateBoxErrorIcon.setVisibility(4);
            this.ivCenterDeviceStateAniIcon.setVisibility(0);
            AnimationDrawable animationDrawable = (AnimationDrawable) this.ivCenterDeviceStateAniIcon.getDrawable();
            this.animationDrawable = animationDrawable;
            animationDrawable.setOneShot(true);
            this.animationDrawable.start();
            this.isAni = true;
            this.handler.postDelayed(new AnonymousClass3(), 1350L);
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.adapter.T6LiveViewPagerAdapter$3, reason: invalid class name */
    public class AnonymousClass3 implements Runnable {
        public AnonymousClass3() {
        }

        @Override // java.lang.Runnable
        public void run() {
            T6LiveViewPagerAdapter.this.ivCenterDeviceStateAniIcon.setVisibility(4);
            T6LiveViewPagerAdapter.this.alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
            T6LiveViewPagerAdapter.this.alphaAnimation.setDuration(600L);
            T6LiveViewPagerAdapter.this.alphaAnimation.setAnimationListener(new SimpleAnimationUtils.AnimationListenerAdapter() { // from class: com.petkit.android.activities.petkitBleDevice.t6.adapter.T6LiveViewPagerAdapter.3.1
                @Override // razerdp.util.SimpleAnimationUtils.AnimationListenerAdapter, android.view.animation.Animation.AnimationListener
                public void onAnimationEnd(Animation animation) {
                    super.onAnimationEnd(animation);
                    T6LiveViewPagerAdapter.this.ivCenterDeviceStateIcon.setVisibility(4);
                    T6LiveViewPagerAdapter.this.ivCenterDeviceStateErrorIcon.setVisibility(0);
                    T6LiveViewPagerAdapter.this.alpha = new AlphaAnimation(0.0f, 1.0f);
                    T6LiveViewPagerAdapter.this.alpha.setDuration(600L);
                    T6LiveViewPagerAdapter.this.alpha.setAnimationListener(new SimpleAnimationUtils.AnimationListenerAdapter() { // from class: com.petkit.android.activities.petkitBleDevice.t6.adapter.T6LiveViewPagerAdapter.3.1.1
                        @Override // razerdp.util.SimpleAnimationUtils.AnimationListenerAdapter, android.view.animation.Animation.AnimationListener
                        public void onAnimationEnd(Animation animation2) {
                            super.onAnimationEnd(animation2);
                            T6LiveViewPagerAdapter.this.errorState();
                            T6LiveViewPagerAdapter.this.isAni = false;
                        }
                    });
                    T6LiveViewPagerAdapter.this.ivCenterDeviceStateErrorIcon.startAnimation(T6LiveViewPagerAdapter.this.alpha);
                }
            });
            T6LiveViewPagerAdapter.this.ivCenterDeviceStateIcon.startAnimation(T6LiveViewPagerAdapter.this.alphaAnimation);
        }
    }

    private void offlineState() {
        this.ivCenterDeviceStateAniIcon.setVisibility(8);
        this.videoTagLayout.setVisibility(8);
        this.rlParent.setBackgroundResource(R.drawable.t6_device_gray_bg);
        this.ivCenterDeviceStateIcon.setImageResource(R.drawable.t6_device_state_offline_icon);
        this.ivCenterDeviceStateIcon.setVisibility(0);
        this.ivCenterDeviceStateErrorIcon.setVisibility(4);
        this.rlBlock.setVisibility(4);
        this.rlCatLitter.setVisibility(4);
        this.rlGarbageBags.setVisibility(4);
        this.rlErrorBlock.setVisibility(4);
        this.rlErrorBox.setVisibility(4);
        this.rlErrorCatLitter.setVisibility(4);
        this.rlErrorGarbageBags.setVisibility(4);
        this.ivCenterDeviceStateGarbagebagsErrorIcon.setVisibility(4);
        this.ivCenterDeviceStateBlockErrorIcon.setVisibility(4);
        this.ivCenterDeviceStateErrorIcon.setVisibility(4);
        this.ivCenterDeviceStateCatLitterErrorIcon.setVisibility(4);
        this.ivCenterDeviceStateBoxErrorIcon.setVisibility(4);
        this.tvBlock.setText(this.t6Record.getState().getPurificationLeftDays() <= 0 ? R.string.N60_lack : R.string.N60_enough);
        this.tvCatLitterState.setText(R.string.T4_litter_full);
        int packageLeftPercent = T6Utils.getPackageLeftPercent(this.t6Record);
        if (packageLeftPercent == 10) {
            BagProgressView bagProgressView = this.bpv;
            int i = R.color.transparent;
            int i2 = R.color.w5_home_red;
            bagProgressView.setColorAndPercent(i, i2, i2, 10);
            return;
        }
        if (packageLeftPercent == 50) {
            BagProgressView bagProgressView2 = this.bpv;
            int i3 = R.color.transparent;
            int i4 = R.color.color_FB7C3D;
            bagProgressView2.setColorAndPercent(i3, i4, i4, 50);
            return;
        }
        if (packageLeftPercent == 80) {
            BagProgressView bagProgressView3 = this.bpv;
            int i5 = R.color.transparent;
            int i6 = R.color.color_34C759;
            bagProgressView3.setColorAndPercent(i5, i6, i6, 80);
            return;
        }
        if (packageLeftPercent != 100) {
            return;
        }
        BagProgressView bagProgressView4 = this.bpv;
        int i7 = R.color.transparent;
        int i8 = R.color.color_34C759;
        bagProgressView4.setColorAndPercent(i7, i8, i8, 100);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void errorState() {
        this.rlParent.setBackgroundResource(R.drawable.t6_device_bg);
        this.ivCenterDeviceStateIcon.setVisibility(4);
        this.ivCenterDeviceStateErrorIcon.setVisibility(0);
        this.rlBlock.setVisibility(4);
        this.rlCatLitter.setVisibility(4);
        this.rlGarbageBags.setVisibility(4);
        this.rlErrorBlock.setVisibility(0);
        this.rlErrorBox.setVisibility(0);
        this.rlErrorCatLitter.setVisibility(4);
        this.rlErrorGarbageBags.setVisibility(0);
        this.llSmartSettingPanelOne.setVisibility(0);
        this.llSmartSettingPanelTwo.setVisibility(0);
        this.ivCenterDeviceStateGarbagebagsErrorIcon.setVisibility(4);
        this.ivCenterDeviceStateBlockErrorIcon.setVisibility(4);
        this.ivCenterDeviceStateCatLitterErrorIcon.setVisibility(4);
        this.ivCenterDeviceStateBoxErrorIcon.setVisibility(4);
        if (this.t6Record.getState().getPackageState().intValue() == 2) {
            this.tvErrorGarbageBags.setText(R.string.T6_error_bags_have_run_out);
            this.bpvError.setVisibility(8);
            BagProgressView bagProgressView = this.bpvError;
            int i = R.color.transparent;
            int i2 = R.color.white;
            bagProgressView.setColorAndPercent(i, i2, i2, 10);
            this.ivCenterDeviceStateGarbagebagsErrorIcon.setVisibility(0);
            this.llErrorPercent.setBackgroundResource(R.drawable.solid_light_red_white_bg);
            this.ivErrorGarbageBags.setImageResource(R.drawable.t4_state_red_icon);
        } else if (this.t6Record.getState().getPackageState().intValue() == 3) {
            this.tvErrorGarbageBags.setText(R.string.T6_error_bagBox_invalid);
            this.bpvError.setVisibility(8);
            BagProgressView bagProgressView2 = this.bpvError;
            int i3 = R.color.transparent;
            int i4 = R.color.white;
            bagProgressView2.setColorAndPercent(i3, i4, i4, 10);
            this.ivCenterDeviceStateGarbagebagsErrorIcon.setVisibility(0);
            this.llErrorPercent.setBackgroundResource(R.drawable.solid_light_red_white_bg);
            this.ivErrorGarbageBags.setImageResource(R.drawable.t4_state_red_icon);
        } else if (this.t6Record.getState().getPackageState().intValue() == 4) {
            this.tvErrorGarbageBags.setText(R.string.T6_error_bagBox_not_avaliable);
            this.bpvError.setVisibility(8);
            BagProgressView bagProgressView3 = this.bpvError;
            int i5 = R.color.transparent;
            int i6 = R.color.white;
            bagProgressView3.setColorAndPercent(i5, i6, i6, 10);
            this.ivCenterDeviceStateGarbagebagsErrorIcon.setVisibility(0);
            this.llErrorPercent.setBackgroundResource(R.drawable.solid_light_red_white_bg);
            this.ivErrorGarbageBags.setImageResource(R.drawable.t4_state_red_icon);
        } else if (this.t6Record.getState().getPackageState().intValue() == 6) {
            this.tvErrorGarbageBags.setText(R.string.T6_error_bagBox_not_installed);
            this.bpvError.setVisibility(8);
            BagProgressView bagProgressView4 = this.bpvError;
            int i7 = R.color.transparent;
            int i8 = R.color.white;
            bagProgressView4.setColorAndPercent(i7, i8, i8, 10);
            this.ivCenterDeviceStateGarbagebagsErrorIcon.setVisibility(0);
            this.llErrorPercent.setBackgroundResource(R.drawable.solid_light_red_white_bg);
            this.ivErrorGarbageBags.setImageResource(R.drawable.t4_state_red_icon);
        } else if (this.t6Record.getState().getPackageState().intValue() == 5) {
            this.tvErrorGarbageBags.setText(R.string.T6_error_bagBox_not_installed);
            this.bpvError.setVisibility(8);
            BagProgressView bagProgressView5 = this.bpvError;
            int i9 = R.color.transparent;
            int i10 = R.color.white;
            bagProgressView5.setColorAndPercent(i9, i10, i10, 10);
            this.ivCenterDeviceStateGarbagebagsErrorIcon.setVisibility(0);
            this.llErrorPercent.setBackgroundResource(R.drawable.solid_light_red_white_bg);
            this.ivErrorGarbageBags.setImageResource(R.drawable.t4_state_red_icon);
        } else if (this.t6Record.getState().getBaggingState() != null && this.t6Record.getState().getBaggingState().intValue() == 0) {
            this.tvErrorGarbageBags.setText(R.string.Auto_bagging_fail);
            this.bpvError.setVisibility(8);
            BagProgressView bagProgressView6 = this.bpvError;
            int i11 = R.color.transparent;
            int i12 = R.color.white;
            bagProgressView6.setColorAndPercent(i11, i12, i12, 10);
            this.ivCenterDeviceStateGarbagebagsErrorIcon.setVisibility(0);
            this.llErrorPercent.setBackgroundResource(R.drawable.solid_light_red_white_bg);
            this.ivErrorGarbageBags.setImageResource(R.drawable.t4_state_red_icon);
        } else {
            this.bpvError.setVisibility(0);
            this.tvErrorGarbageBags.setText(R.string.T6_garbebag_left);
            int packageLeftPercent = T6Utils.getPackageLeftPercent(this.t6Record);
            if (packageLeftPercent == 10) {
                BagProgressView bagProgressView7 = this.bpvError;
                int i13 = R.color.transparent;
                int i14 = R.color.w5_home_red;
                bagProgressView7.setColorAndPercent(i13, i14, i14, 10);
                this.ivCenterDeviceStateGarbagebagsErrorIcon.setVisibility(0);
                this.llErrorPercent.setBackgroundResource(R.drawable.solid_light_red_white_bg);
                this.ivErrorGarbageBags.setImageResource(R.drawable.t4_state_red_icon);
            } else if (packageLeftPercent == 50) {
                BagProgressView bagProgressView8 = this.bpvError;
                int i15 = R.color.transparent;
                int i16 = R.color.color_FB7C3D;
                bagProgressView8.setColorAndPercent(i15, i16, i16, 50);
                this.ivCenterDeviceStateGarbagebagsErrorIcon.setVisibility(4);
                this.llErrorPercent.setBackgroundResource(R.drawable.shape_t6_state_white_bg);
                this.ivErrorGarbageBags.setImageResource(R.drawable.t6_device_state_point);
            } else if (packageLeftPercent == 80) {
                BagProgressView bagProgressView9 = this.bpvError;
                int i17 = R.color.transparent;
                int i18 = R.color.color_34C759;
                bagProgressView9.setColorAndPercent(i17, i18, i18, 80);
                this.ivCenterDeviceStateGarbagebagsErrorIcon.setVisibility(4);
                this.llErrorPercent.setBackgroundResource(R.drawable.shape_t6_state_white_bg);
                this.ivErrorGarbageBags.setImageResource(R.drawable.t6_device_state_point);
            } else if (packageLeftPercent == 100) {
                BagProgressView bagProgressView10 = this.bpvError;
                int i19 = R.color.transparent;
                int i20 = R.color.color_34C759;
                bagProgressView10.setColorAndPercent(i19, i20, i20, 100);
                this.ivCenterDeviceStateGarbagebagsErrorIcon.setVisibility(4);
                this.llErrorPercent.setBackgroundResource(R.drawable.shape_t6_state_white_bg);
                this.ivErrorGarbageBags.setImageResource(R.drawable.t6_device_state_point);
            }
        }
        if (this.t6Record.getState().isBoxFull()) {
            this.tvErrorBox.setText(R.string.T6_err_box_full);
            this.ivErrorBox.setImageResource(this.t6Record.getState().isBoxFull() ? R.drawable.t4_state_red_icon : R.drawable.t6_device_state_point);
            this.ivCenterDeviceStateBoxErrorIcon.setVisibility(this.t6Record.getState().isBoxFull() ? 0 : 8);
            this.tvErrorBox.setBackgroundResource(R.drawable.solid_light_red_white_bg);
            this.rlErrorBox.setVisibility(0);
        } else {
            this.rlErrorBox.setVisibility(8);
        }
        this.tvErrorBlock.setText(this.t6Record.getState().getPurificationLeftDays() <= 0 ? R.string.N60_lack : R.string.N60_enough);
        this.ivCenterDeviceStateBlockErrorIcon.setVisibility(this.t6Record.getState().getPurificationLeftDays() <= 0 ? 0 : 8);
        this.ivErrorBlock.setImageResource(this.t6Record.getState().getPurificationLeftDays() > 0 ? R.drawable.t6_device_state_point : R.drawable.t4_state_red_icon);
        this.tvErrorBlock.setBackgroundResource(this.t6Record.getState().getPurificationLeftDays() > 0 ? R.drawable.shape_t6_state_white_bg : R.drawable.solid_light_red_white_bg);
        Context context = this.context;
        StringBuilder sb = new StringBuilder();
        sb.append(Consts.T6_TAG);
        sb.append(this.t6Record.getDeviceId());
        this.rlErrorBlock.setVisibility(CommonUtils.getSysBoolMap(context, sb.toString(), true) ? 0 : 8);
    }

    public final void noErrorState() {
        this.ivCenterDeviceStateAniIcon.setVisibility(8);
        this.rlParent.setBackgroundResource(R.drawable.t6_device_bg);
        this.ivCenterDeviceStateIcon.setVisibility(0);
        this.ivCenterDeviceStateErrorIcon.setVisibility(4);
        this.rlBlock.setVisibility(0);
        this.rlCatLitter.setVisibility(4);
        this.rlGarbageBags.setVisibility(0);
        this.rlErrorBlock.setVisibility(4);
        this.rlErrorBox.setVisibility(4);
        this.rlErrorCatLitter.setVisibility(4);
        this.rlErrorGarbageBags.setVisibility(4);
        this.ivCenterDeviceStateGarbagebagsErrorIcon.setVisibility(4);
        this.ivCenterDeviceStateBlockErrorIcon.setVisibility(4);
        this.ivCenterDeviceStateErrorIcon.setVisibility(4);
        this.ivCenterDeviceStateCatLitterErrorIcon.setVisibility(4);
        this.ivCenterDeviceStateBoxErrorIcon.setVisibility(4);
        this.llSmartSettingPanelOne.setVisibility(0);
        this.llSmartSettingPanelTwo.setVisibility(0);
        this.tvBlock.setText(this.t6Record.getState().getPurificationLeftDays() <= 0 ? R.string.N60_lack : R.string.N60_enough);
        this.tvCatLitterState.setText(R.string.T4_litter_full);
        int packageLeftPercent = T6Utils.getPackageLeftPercent(this.t6Record);
        if (packageLeftPercent == 10) {
            BagProgressView bagProgressView = this.bpv;
            int i = R.color.transparent;
            int i2 = R.color.w5_home_red;
            bagProgressView.setColorAndPercent(i, i2, i2, 10);
        } else if (packageLeftPercent == 50) {
            BagProgressView bagProgressView2 = this.bpv;
            int i3 = R.color.transparent;
            int i4 = R.color.color_FB7C3D;
            bagProgressView2.setColorAndPercent(i3, i4, i4, 50);
        } else if (packageLeftPercent == 80) {
            BagProgressView bagProgressView3 = this.bpv;
            int i5 = R.color.transparent;
            int i6 = R.color.color_34C759;
            bagProgressView3.setColorAndPercent(i5, i6, i6, 80);
        } else if (packageLeftPercent == 100) {
            BagProgressView bagProgressView4 = this.bpv;
            int i7 = R.color.transparent;
            int i8 = R.color.color_34C759;
            bagProgressView4.setColorAndPercent(i7, i8, i8, 100);
        }
        Context context = this.context;
        StringBuilder sb = new StringBuilder();
        sb.append(Consts.T6_TAG);
        sb.append(this.t6Record.getDeviceId());
        this.rlBlock.setVisibility(CommonUtils.getSysBoolMap(context, sb.toString(), true) ? 0 : 8);
    }

    public void updateData(T6Record t6Record) {
        this.t6Record = t6Record;
    }

    public void refreshData() {
        initData();
    }

    public final void showKittenConfirmWindow() {
        View viewInflate = LayoutInflater.from(this.context).inflate(R.layout.pop_t3_clean_layout, (ViewGroup) null);
        final EasyPopupWindow easyPopupWindow = new EasyPopupWindow(this.context);
        easyPopupWindow.setOutsideTouchable(false);
        easyPopupWindow.setFocusable(false);
        TextView textView = (TextView) viewInflate.findViewById(R.id.tv_cancel);
        textView.setTextColor(this.context.getResources().getColor(R.color.gray));
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.adapter.T6LiveViewPagerAdapter$$ExternalSyntheticLambda15
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$showKittenConfirmWindow$15(easyPopupWindow, view);
            }
        });
        TextView textView2 = (TextView) viewInflate.findViewById(R.id.tv_confirm);
        textView2.setTextColor(this.context.getResources().getColor(R.color.new_bind_blue));
        textView2.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.adapter.T6LiveViewPagerAdapter$$ExternalSyntheticLambda16
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$showKittenConfirmWindow$16(easyPopupWindow, view);
            }
        });
        TextView textView3 = (TextView) viewInflate.findViewById(R.id.tv_content);
        textView3.setMovementMethod(ScrollingMovementMethod.getInstance());
        textView3.setText(this.context.getResources().getString(R.string.Kitten_close_prompt));
        easyPopupWindow.setContentView(viewInflate);
        easyPopupWindow.setHeight(-1);
        easyPopupWindow.setWidth((int) (BaseApplication.displayMetrics.widthPixels * 0.8f));
        easyPopupWindow.showAtLocation(((Activity) this.context).getWindow().getDecorView(), 17, 0, 0);
    }

    public final /* synthetic */ void lambda$showKittenConfirmWindow$15(EasyPopupWindow easyPopupWindow, View view) {
        updateData(this.t6Record);
        refreshData();
        easyPopupWindow.dismiss();
    }

    public final /* synthetic */ void lambda$showKittenConfirmWindow$16(EasyPopupWindow easyPopupWindow, View view) {
        easyPopupWindow.dismiss();
        OnClickListener onClickListener = this.onClickListener;
        if (onClickListener != null) {
            onClickListener.updateDeviceSettings("kitten", this.t6Record.getSettings().getKitten() != 1);
        }
    }

    public final void showOpenKittenConfirmWindow() {
        View viewInflate = LayoutInflater.from(this.context).inflate(R.layout.pop_t3_clean_layout, (ViewGroup) null);
        final EasyPopupWindow easyPopupWindow = new EasyPopupWindow(this.context);
        easyPopupWindow.setOutsideTouchable(false);
        easyPopupWindow.setFocusable(false);
        TextView textView = (TextView) viewInflate.findViewById(R.id.tv_cancel);
        textView.setTextColor(this.context.getResources().getColor(R.color.gray));
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.adapter.T6LiveViewPagerAdapter$$ExternalSyntheticLambda17
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$showOpenKittenConfirmWindow$17(easyPopupWindow, view);
            }
        });
        TextView textView2 = (TextView) viewInflate.findViewById(R.id.tv_confirm);
        textView2.setText(this.context.getResources().getString(R.string.Confirm));
        textView2.setTextColor(this.context.getResources().getColor(R.color.login_new_blue));
        textView2.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.adapter.T6LiveViewPagerAdapter$$ExternalSyntheticLambda18
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$showOpenKittenConfirmWindow$18(easyPopupWindow, view);
            }
        });
        TextView textView3 = (TextView) viewInflate.findViewById(R.id.tv_content);
        textView3.setMovementMethod(ScrollingMovementMethod.getInstance());
        String string = this.context.getResources().getString(R.string.Kitten_protection_prompt_two);
        SpannableString spannableString = new SpannableString(this.context.getResources().getString(R.string.Kitten_protection_prompt_one, string));
        spannableString.setSpan(new ForegroundColorSpan(CommonUtils.getColorById(R.color.new_bind_blue)), spannableString.toString().indexOf(string), spannableString.toString().indexOf(string) + string.length(), 17);
        textView3.setText(spannableString);
        easyPopupWindow.setContentView(viewInflate);
        easyPopupWindow.setHeight(-1);
        easyPopupWindow.setWidth((int) (BaseApplication.displayMetrics.widthPixels * 0.8f));
        easyPopupWindow.showAtLocation(((Activity) this.context).getWindow().getDecorView(), 17, 0, 0);
    }

    public final /* synthetic */ void lambda$showOpenKittenConfirmWindow$17(EasyPopupWindow easyPopupWindow, View view) {
        updateData(this.t6Record);
        refreshData();
        easyPopupWindow.dismiss();
    }

    public final /* synthetic */ void lambda$showOpenKittenConfirmWindow$18(EasyPopupWindow easyPopupWindow, View view) {
        easyPopupWindow.dismiss();
        OnClickListener onClickListener = this.onClickListener;
        if (onClickListener != null) {
            onClickListener.updateDeviceSettings("kitten", this.t6Record.getSettings().getKitten() != 1);
        }
    }

    public final void showDisableCleanUpWindow() {
        SimpleTipWindow simpleTipWindow = this.disableCleanUpWindow;
        if (simpleTipWindow == null || !simpleTipWindow.isShowing()) {
            Context context = this.context;
            SimpleTipWindow simpleTipWindow2 = new SimpleTipWindow((Activity) context, context.getResources().getString(R.string.Disable_auto_clean_when_kitten_protection), true, R.color.login_new_blue, true);
            this.disableCleanUpWindow = simpleTipWindow2;
            simpleTipWindow2.showAtLocation(((Activity) this.context).getWindow().getDecorView(), 17, 0, 0);
        }
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public void destroyItem(@NonNull ViewGroup viewGroup, int i, @NonNull Object obj) {
        viewGroup.removeView((View) obj);
    }
}

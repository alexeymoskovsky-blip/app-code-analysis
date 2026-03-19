package com.petkit.android.activities.petkitBleDevice.t6.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
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
import com.petkit.android.activities.petkitBleDevice.t6.T5HomeActivity;
import com.petkit.android.activities.petkitBleDevice.t6.mode.T6Record;
import com.petkit.android.activities.petkitBleDevice.utils.UnitUtils;
import com.petkit.android.utils.CommonUtils;
import com.petkit.oversea.R;
import razerdp.util.SimpleAnimationUtils;

/* JADX INFO: loaded from: classes5.dex */
public class T5LiveViewPagerAdapter extends PagerAdapter {
    public AlphaAnimation alpha;
    public AlphaAnimation alphaAnimation;
    public View clParent;
    public final Context context;
    public int currentPosition = 0;
    public final RelativeLayout fistLiveView;
    public final T5HomeActivity.MyHandler handler;
    public boolean isAni;
    public boolean isLive;
    public boolean isNeedShowAni;
    public ImageView ivAnim;
    public ImageView ivDevice;
    public ImageView ivDeviceError;
    public ImageView ivDeviceErrorDump;
    public ImageView ivDeviceErrorLitter;
    public ImageView ivDeviceErrorN50;
    public ImageView ivDeviceErrorN60;
    public ImageView ivErrorDump;
    public ImageView ivErrorLitter;
    public ImageView ivErrorOdorBox;
    public ImageView ivErrorOdorBox2;
    public ImageView ivNormalDump;
    public ImageView ivNormalLitter;
    public ImageView ivNormalOdorBox;
    public ImageView ivNormalOdorBox2;
    public OnClickListener onClickListener;
    public T6Record t5Record;
    public TextView tvErrorDump;
    public TextView tvErrorLitter;
    public TextView tvErrorOdorBox;
    public TextView tvErrorOdorBox2;
    public TextView tvNormalDump;
    public TextView tvNormalLitter;
    public TextView tvNormalOdorBox;
    public TextView tvNormalOdorBox2;
    public RelativeLayout videoTagLayout;

    public interface OnClickListener {
        void onCatLitterClick();

        void onDumpClick();

        void onN50Click();

        void onN60Click(boolean z);
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

    public T5LiveViewPagerAdapter(Context context, RelativeLayout relativeLayout, T5HomeActivity.MyHandler myHandler) {
        this.context = context;
        this.fistLiveView = relativeLayout;
        this.handler = myHandler;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    @NonNull
    @SuppressLint({"InflateParams"})
    public Object instantiateItem(@NonNull ViewGroup viewGroup, int i) {
        if (i == 0) {
            LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(this.context).inflate(R.layout.layout_t6_live_view, (ViewGroup) null);
            linearLayout.addView(this.fistLiveView);
            viewGroup.addView(linearLayout);
            return linearLayout;
        }
        View viewInflate = LayoutInflater.from(this.context).inflate(R.layout.layout_t5_live_device_state, (ViewGroup) null);
        this.clParent = viewInflate;
        this.videoTagLayout = (RelativeLayout) viewInflate.findViewById(R.id.video_tag_layout);
        this.tvErrorLitter = (TextView) this.clParent.findViewById(R.id.tv_error_litter);
        this.tvErrorOdorBox = (TextView) this.clParent.findViewById(R.id.tv_error_odor_box);
        this.tvErrorOdorBox2 = (TextView) this.clParent.findViewById(R.id.tv_error_odor_box2);
        this.tvErrorDump = (TextView) this.clParent.findViewById(R.id.tv_error_dump);
        this.ivErrorLitter = (ImageView) this.clParent.findViewById(R.id.iv_error_litter);
        this.ivErrorOdorBox = (ImageView) this.clParent.findViewById(R.id.iv_error_odor_box);
        this.ivErrorOdorBox2 = (ImageView) this.clParent.findViewById(R.id.iv_error_odor_box2);
        this.ivErrorDump = (ImageView) this.clParent.findViewById(R.id.iv_error_dump);
        this.ivDeviceError = (ImageView) this.clParent.findViewById(R.id.iv_device_error);
        this.ivDeviceErrorLitter = (ImageView) this.clParent.findViewById(R.id.iv_device_error_litter);
        this.ivDeviceErrorN60 = (ImageView) this.clParent.findViewById(R.id.iv_device_error_n60);
        this.ivDeviceErrorN50 = (ImageView) this.clParent.findViewById(R.id.iv_device_error_n50);
        this.ivDeviceErrorDump = (ImageView) this.clParent.findViewById(R.id.iv_device_error_dump);
        this.tvNormalLitter = (TextView) this.clParent.findViewById(R.id.tv_normal_litter);
        this.tvNormalOdorBox = (TextView) this.clParent.findViewById(R.id.tv_normal_odor_box);
        this.tvNormalOdorBox2 = (TextView) this.clParent.findViewById(R.id.tv_normal_odor_box2);
        this.tvNormalDump = (TextView) this.clParent.findViewById(R.id.tv_normal_dump);
        this.ivNormalLitter = (ImageView) this.clParent.findViewById(R.id.iv_normal_litter);
        this.ivNormalOdorBox = (ImageView) this.clParent.findViewById(R.id.iv_normal_odor_box);
        this.ivNormalOdorBox2 = (ImageView) this.clParent.findViewById(R.id.iv_normal_odor_box2);
        this.ivNormalDump = (ImageView) this.clParent.findViewById(R.id.iv_normal_dump);
        this.ivDevice = (ImageView) this.clParent.findViewById(R.id.iv_device);
        this.ivAnim = (ImageView) this.clParent.findViewById(R.id.iv_center_device_state_ani_icon);
        viewGroup.addView(this.clParent);
        return this.clParent;
    }

    private void initData() {
        if (this.t5Record == null || this.tvNormalOdorBox == null) {
            return;
        }
        initListener();
        if (this.t5Record.getModelCode() == 2) {
            this.ivDeviceError.setImageResource(R.drawable.t5_2_device_trans_img);
            this.ivDeviceErrorDump.setImageResource(R.drawable.t5_2_device_error_dump);
            this.ivDeviceErrorN60.setImageResource(R.drawable.t5_2_device_error_n60);
            this.ivDeviceErrorN50.setImageResource(R.drawable.t5_2_device_error_n50);
            this.ivDeviceErrorLitter.setImageResource(R.drawable.t5_2_device_error_litter);
        } else {
            this.ivDeviceError.setImageResource(R.drawable.t5_device_trans_img);
            this.ivDeviceErrorDump.setImageResource(R.drawable.t5_device_error_dump);
            this.ivDeviceErrorN60.setImageResource(R.drawable.t5_device_error_n60);
            this.ivDeviceErrorN50.setImageResource(R.drawable.t5_device_error_n50);
            this.ivDeviceErrorLitter.setImageResource(R.drawable.t5_device_error_litter);
        }
        if (this.t5Record.getState().getPower() == 1) {
            if (this.t5Record.getModelCode() == 2) {
                this.ivDevice.setImageResource(R.drawable.t5_2_device_img);
            } else {
                this.ivDevice.setImageResource(R.drawable.t5_device_img);
            }
        } else if (this.t5Record.getModelCode() == 2) {
            this.ivDevice.setImageResource(R.drawable.t5_2_device_offline_img);
        } else {
            this.ivDevice.setImageResource(R.drawable.t5_device_offline_img);
        }
        if (this.t5Record.getState().getOverall() == 2 || this.t5Record.getState().getPower() != 1) {
            offlineState();
            return;
        }
        if (this.t5Record.getState().getDeodorantLeftDays() <= 0 || this.t5Record.getState().getSprayState() != 1 || this.t5Record.getState().getSprayLeftDays() <= 0 || this.t5Record.getState().getBoxState() != 1 || this.t5Record.getState().isBoxFull()) {
            if (this.currentPosition == 1) {
                startAni();
            } else {
                errorState();
            }
            if (this.isLive) {
                this.videoTagLayout.setVisibility(0);
                return;
            } else {
                this.videoTagLayout.setVisibility(8);
                return;
            }
        }
        normalState();
        if (this.isLive) {
            this.videoTagLayout.setVisibility(0);
        } else {
            this.videoTagLayout.setVisibility(8);
        }
    }

    public final void offlineState() {
        this.videoTagLayout.setVisibility(8);
        this.clParent.setBackgroundResource(R.drawable.t6_device_gray_bg);
        if (this.t5Record.getModelCode() == 2) {
            this.ivDevice.setImageResource(R.drawable.t5_2_device_offline_img);
        } else {
            this.ivDevice.setImageResource(R.drawable.t5_device_offline_img);
        }
        this.ivDevice.setVisibility(0);
        hideView(this.tvNormalOdorBox, this.tvNormalOdorBox2, this.tvNormalDump, this.ivNormalOdorBox, this.ivNormalOdorBox2, this.ivNormalDump);
        hideView(this.tvErrorOdorBox, this.tvErrorOdorBox2, this.tvErrorDump, this.ivErrorOdorBox, this.ivErrorOdorBox2, this.ivErrorDump, this.ivDeviceError, this.ivDeviceErrorN60, this.ivDeviceErrorN50, this.ivDeviceErrorDump);
    }

    public final void normalState() {
        this.clParent.setBackgroundResource(R.drawable.t6_device_bg);
        showView(this.ivDevice, this.tvNormalOdorBox, this.tvNormalOdorBox2, this.tvNormalDump, this.ivNormalOdorBox, this.ivNormalOdorBox2, this.ivNormalDump);
        hideView(this.tvErrorOdorBox, this.tvErrorOdorBox2, this.tvErrorDump, this.ivErrorOdorBox, this.ivErrorOdorBox2, this.ivErrorDump, this.ivDeviceError, this.ivDeviceErrorN60, this.ivDeviceErrorN50, this.ivDeviceErrorDump);
        this.tvNormalOdorBox.setText(this.context.getResources().getString(R.string.N60_enough));
        this.ivNormalOdorBox.setImageResource(R.drawable.t6_device_state_point);
        boolean sysBoolMap = CommonUtils.getSysBoolMap(this.context, "N60" + this.t5Record.getDeviceId(), true);
        boolean sysBoolMap2 = CommonUtils.getSysBoolMap(this.context, Consts.T5_tag_n60 + this.t5Record.getDeviceId(), false);
        if (sysBoolMap) {
            this.ivNormalOdorBox.setVisibility(0);
            this.tvNormalOdorBox.setVisibility(sysBoolMap2 ? 0 : 8);
        } else {
            this.ivNormalOdorBox.setVisibility(8);
            this.tvNormalOdorBox.setVisibility(8);
        }
        this.tvNormalOdorBox2.setText(this.context.getResources().getString(R.string.T5_n50_left_prompt, UnitUtils.getInstance().getNumAndUnitString(Math.max(this.t5Record.getState().getDeodorantLeftDays(), 0), this.context.getString(R.string.Unit_day), this.context.getString(R.string.Unit_days))));
        this.ivNormalOdorBox2.setImageResource(R.drawable.t6_device_state_point);
        boolean sysBoolMap3 = CommonUtils.getSysBoolMap(this.context, "N50" + this.t5Record.getDeviceId(), true);
        boolean sysBoolMap4 = CommonUtils.getSysBoolMap(this.context, Consts.T5_tag_n50 + this.t5Record.getDeviceId(), false);
        if (sysBoolMap3) {
            this.ivNormalOdorBox2.setVisibility(0);
            this.tvNormalOdorBox2.setVisibility(sysBoolMap4 ? 0 : 8);
        } else {
            this.ivNormalOdorBox2.setVisibility(8);
            this.tvNormalOdorBox2.setVisibility(8);
        }
        this.tvNormalDump.setText(R.string.T5_box_normal);
        this.ivNormalDump.setImageResource(R.drawable.t6_device_state_point);
        Context context = this.context;
        StringBuilder sb = new StringBuilder();
        sb.append(Consts.T5_tag_dump);
        sb.append(this.t5Record.getDeviceId());
        this.tvNormalDump.setVisibility(CommonUtils.getSysBoolMap(context, sb.toString(), false) ? 0 : 8);
        hideTagAfter5Min();
    }

    public final void errorState() {
        this.clParent.setBackgroundResource(R.drawable.t6_device_bg);
        hideView(this.ivDevice, this.tvNormalOdorBox, this.tvNormalOdorBox2, this.tvNormalDump, this.ivNormalOdorBox, this.ivNormalOdorBox2, this.ivNormalDump);
        showView(this.ivDeviceError, this.tvErrorOdorBox, this.tvErrorOdorBox2, this.tvErrorDump, this.ivErrorOdorBox, this.ivErrorOdorBox2, this.ivErrorDump);
        if (this.t5Record.getState().getSprayState() != 1) {
            this.tvErrorOdorBox.setText(R.string.T5_k4_not_install);
            this.ivErrorOdorBox.setImageResource(R.drawable.t4_state_red_icon);
            this.ivErrorOdorBox.setBackgroundResource(R.drawable.solid_light_red_white_bg);
            this.ivDeviceErrorN60.setVisibility(0);
        } else {
            boolean z = this.t5Record.getState().getSprayLeftDays() <= 0;
            this.ivErrorOdorBox.setImageResource(z ? R.drawable.t4_state_red_icon : R.drawable.t6_device_state_point);
            this.tvErrorOdorBox.setText(z ? R.string.N60_lack : R.string.N60_enough);
            this.tvErrorOdorBox.setBackgroundResource(z ? R.drawable.solid_light_red_white_bg : R.drawable.shape_t6_state_white_bg);
            if (z) {
                this.ivDeviceErrorN60.setVisibility(0);
            } else {
                this.ivDeviceErrorN60.setVisibility(8);
            }
        }
        boolean sysBoolMap = CommonUtils.getSysBoolMap(this.context, "N60" + this.t5Record.getDeviceId(), true);
        boolean sysBoolMap2 = CommonUtils.getSysBoolMap(this.context, Consts.T5_tag_n60 + this.t5Record.getDeviceId(), false);
        if (sysBoolMap) {
            this.ivErrorOdorBox.setVisibility(0);
            this.tvErrorOdorBox.setVisibility(sysBoolMap2 ? 0 : 8);
        } else {
            this.ivErrorOdorBox.setVisibility(8);
            this.tvErrorOdorBox.setVisibility(8);
        }
        this.tvErrorOdorBox2.setText(this.context.getResources().getString(R.string.T5_n50_left_prompt, UnitUtils.getInstance().getNumAndUnitString(Math.max(this.t5Record.getState().getDeodorantLeftDays(), 0), this.context.getString(R.string.Unit_day), this.context.getString(R.string.Unit_days))));
        this.ivErrorOdorBox2.setImageResource(this.t5Record.getState().getDeodorantLeftDays() > 0 ? R.drawable.t6_device_state_point : R.drawable.t4_state_red_icon);
        this.tvErrorOdorBox2.setBackgroundResource(this.t5Record.getState().getDeodorantLeftDays() > 0 ? R.drawable.shape_t6_state_white_bg : R.drawable.solid_light_red_white_bg);
        this.ivDeviceErrorN50.setVisibility(this.t5Record.getState().getDeodorantLeftDays() <= 0 ? 0 : 8);
        boolean sysBoolMap3 = CommonUtils.getSysBoolMap(this.context, "N50" + this.t5Record.getDeviceId(), true);
        boolean sysBoolMap4 = CommonUtils.getSysBoolMap(this.context, Consts.T5_tag_n50 + this.t5Record.getDeviceId(), false);
        if (sysBoolMap3) {
            this.ivErrorOdorBox2.setVisibility(0);
            this.tvErrorOdorBox2.setVisibility(sysBoolMap4 ? 0 : 8);
        } else {
            this.ivErrorOdorBox2.setVisibility(8);
            this.tvErrorOdorBox2.setVisibility(8);
        }
        boolean sysBoolMap5 = CommonUtils.getSysBoolMap(this.context, Consts.T5_tag_dump + this.t5Record.getDeviceId(), false);
        if (this.t5Record.getState().isBoxFull() || this.t5Record.getState().getBoxState() != 1) {
            if (this.t5Record.getState().isBoxFull()) {
                this.tvErrorDump.setText(R.string.T6_err_box_full);
            } else {
                this.tvErrorDump.setText(R.string.T5_garbage_bags_not_install);
            }
            this.ivErrorDump.setImageResource(R.drawable.t4_state_red_icon);
            this.tvErrorDump.setBackgroundResource(R.drawable.solid_light_red_white_bg);
            this.ivDeviceErrorDump.setVisibility(0);
        } else {
            this.tvErrorDump.setText(R.string.T5_box_normal);
            this.ivErrorDump.setImageResource(R.drawable.t6_device_state_point);
            this.tvErrorDump.setBackgroundResource(R.drawable.shape_t6_state_white_bg);
            this.ivDeviceErrorDump.setVisibility(8);
        }
        this.tvErrorDump.setVisibility(sysBoolMap5 ? 0 : 8);
        hideTagAfter5Min();
    }

    public final void initListener() {
        this.tvNormalOdorBox.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.adapter.T5LiveViewPagerAdapter$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initListener$0(view);
            }
        });
        this.tvNormalOdorBox2.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.adapter.T5LiveViewPagerAdapter$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initListener$1(view);
            }
        });
        this.tvNormalDump.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.adapter.T5LiveViewPagerAdapter$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initListener$2(view);
            }
        });
        this.tvErrorLitter.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.adapter.T5LiveViewPagerAdapter$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initListener$3(view);
            }
        });
        this.tvErrorOdorBox.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.adapter.T5LiveViewPagerAdapter$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initListener$4(view);
            }
        });
        this.tvErrorOdorBox2.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.adapter.T5LiveViewPagerAdapter$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initListener$5(view);
            }
        });
        this.tvErrorDump.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.adapter.T5LiveViewPagerAdapter$$ExternalSyntheticLambda6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$initListener$6(view);
            }
        });
        setShowHide(this.ivNormalOdorBox, this.tvNormalOdorBox, Consts.T5_tag_n60 + this.t5Record.getDeviceId());
        setShowHide(this.ivNormalOdorBox2, this.tvNormalOdorBox2, Consts.T5_tag_n50 + this.t5Record.getDeviceId());
        setShowHide(this.ivNormalDump, this.tvNormalDump, Consts.T5_tag_dump + this.t5Record.getDeviceId());
        setShowHide(this.ivErrorOdorBox, this.tvErrorOdorBox, Consts.T5_tag_n60 + this.t5Record.getDeviceId());
        setShowHide(this.ivErrorOdorBox2, this.tvErrorOdorBox2, Consts.T5_tag_n50 + this.t5Record.getDeviceId());
        setShowHide(this.ivErrorDump, this.tvErrorDump, Consts.T5_tag_dump + this.t5Record.getDeviceId());
    }

    public final /* synthetic */ void lambda$initListener$0(View view) {
        OnClickListener onClickListener = this.onClickListener;
        if (onClickListener != null) {
            onClickListener.onN60Click(true);
        }
    }

    public final /* synthetic */ void lambda$initListener$1(View view) {
        OnClickListener onClickListener = this.onClickListener;
        if (onClickListener != null) {
            onClickListener.onN50Click();
        }
    }

    public final /* synthetic */ void lambda$initListener$2(View view) {
        OnClickListener onClickListener = this.onClickListener;
        if (onClickListener != null) {
            onClickListener.onDumpClick();
        }
    }

    public final /* synthetic */ void lambda$initListener$3(View view) {
        OnClickListener onClickListener = this.onClickListener;
        if (onClickListener != null) {
            onClickListener.onCatLitterClick();
        }
    }

    public final /* synthetic */ void lambda$initListener$4(View view) {
        OnClickListener onClickListener = this.onClickListener;
        if (onClickListener != null) {
            onClickListener.onN60Click(this.t5Record.getState().getSprayState() == 1);
        }
    }

    public final /* synthetic */ void lambda$initListener$5(View view) {
        OnClickListener onClickListener = this.onClickListener;
        if (onClickListener != null) {
            onClickListener.onN50Click();
        }
    }

    public final /* synthetic */ void lambda$initListener$6(View view) {
        OnClickListener onClickListener = this.onClickListener;
        if (onClickListener != null) {
            onClickListener.onDumpClick();
        }
    }

    public final void setShowHide(ImageView imageView, final TextView textView, final String str) {
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.t6.adapter.T5LiveViewPagerAdapter$$ExternalSyntheticLambda8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$setShowHide$7(textView, str, view);
            }
        });
    }

    public final /* synthetic */ void lambda$setShowHide$7(TextView textView, String str, View view) {
        if (textView.getVisibility() == 0) {
            textView.setVisibility(8);
            CommonUtils.addSysBoolMap(this.context, str, false);
        } else {
            textView.setVisibility(0);
            CommonUtils.addSysBoolMap(this.context, str, true);
        }
    }

    public void startAni() {
        if (this.isNeedShowAni) {
            if (this.isAni) {
                return;
            }
            errorState();
        } else if (this.ivDevice != null) {
            this.isNeedShowAni = true;
            this.clParent.setBackgroundResource(R.drawable.t6_device_bg);
            this.ivDevice.setVisibility(0);
            this.ivAnim.setVisibility(0);
            AnimationDrawable animationDrawable = (AnimationDrawable) this.ivAnim.getDrawable();
            animationDrawable.setOneShot(true);
            animationDrawable.start();
            this.isAni = true;
            this.handler.postDelayed(new AnonymousClass1(), 1350L);
        }
    }

    /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.t6.adapter.T5LiveViewPagerAdapter$1, reason: invalid class name */
    public class AnonymousClass1 implements Runnable {
        public AnonymousClass1() {
        }

        @Override // java.lang.Runnable
        public void run() {
            T5LiveViewPagerAdapter.this.ivAnim.setVisibility(8);
            T5LiveViewPagerAdapter.this.alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
            T5LiveViewPagerAdapter.this.alphaAnimation.setDuration(600L);
            T5LiveViewPagerAdapter.this.alphaAnimation.setAnimationListener(new SimpleAnimationUtils.AnimationListenerAdapter() { // from class: com.petkit.android.activities.petkitBleDevice.t6.adapter.T5LiveViewPagerAdapter.1.1
                @Override // razerdp.util.SimpleAnimationUtils.AnimationListenerAdapter, android.view.animation.Animation.AnimationListener
                public void onAnimationEnd(Animation animation) {
                    super.onAnimationEnd(animation);
                    T5LiveViewPagerAdapter.this.ivDevice.setVisibility(8);
                    T5LiveViewPagerAdapter.this.ivDeviceError.setVisibility(0);
                    T5LiveViewPagerAdapter.this.alpha = new AlphaAnimation(0.0f, 1.0f);
                    T5LiveViewPagerAdapter.this.alpha.setDuration(600L);
                    T5LiveViewPagerAdapter.this.alpha.setAnimationListener(new SimpleAnimationUtils.AnimationListenerAdapter() { // from class: com.petkit.android.activities.petkitBleDevice.t6.adapter.T5LiveViewPagerAdapter.1.1.1
                        @Override // razerdp.util.SimpleAnimationUtils.AnimationListenerAdapter, android.view.animation.Animation.AnimationListener
                        public void onAnimationEnd(Animation animation2) {
                            super.onAnimationEnd(animation2);
                            T5LiveViewPagerAdapter.this.errorState();
                            T5LiveViewPagerAdapter.this.isAni = false;
                        }
                    });
                    T5LiveViewPagerAdapter.this.ivDeviceError.startAnimation(T5LiveViewPagerAdapter.this.alpha);
                }
            });
            T5LiveViewPagerAdapter.this.ivDevice.startAnimation(T5LiveViewPagerAdapter.this.alphaAnimation);
        }
    }

    public final void hideTagAfter5Min() {
        T5HomeActivity.MyHandler myHandler;
        if (!CommonUtils.getSysBoolMap(this.context, Consts.T5_tag_anim, true) || (myHandler = this.handler) == null) {
            return;
        }
        myHandler.postDelayed(new Runnable() { // from class: com.petkit.android.activities.petkitBleDevice.t6.adapter.T5LiveViewPagerAdapter$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$hideTagAfter5Min$8();
            }
        }, 5000L);
    }

    public final /* synthetic */ void lambda$hideTagAfter5Min$8() {
        this.tvNormalOdorBox.setVisibility(8);
        this.tvNormalOdorBox2.setVisibility(8);
        this.tvNormalDump.setVisibility(8);
        this.tvErrorOdorBox.setVisibility(8);
        this.tvErrorOdorBox2.setVisibility(8);
        this.tvErrorDump.setVisibility(8);
        CommonUtils.addSysBoolMap(this.context, Consts.T5_tag_n50 + this.t5Record.getDeviceId(), false);
        CommonUtils.addSysBoolMap(this.context, Consts.T5_tag_n60 + this.t5Record.getDeviceId(), false);
        CommonUtils.addSysBoolMap(this.context, Consts.T5_tag_dump + this.t5Record.getDeviceId(), false);
        CommonUtils.addSysBoolMap(this.context, Consts.T5_tag_anim, false);
    }

    public void updateData(T6Record t6Record) {
        this.t5Record = t6Record;
    }

    public void refreshData() {
        initData();
    }

    public final void hideView(View... viewArr) {
        for (View view : viewArr) {
            view.setVisibility(8);
        }
    }

    public final void showView(View... viewArr) {
        for (View view : viewArr) {
            view.setVisibility(0);
        }
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public void destroyItem(@NonNull ViewGroup viewGroup, int i, @NonNull Object obj) {
        viewGroup.removeView((View) obj);
    }
}

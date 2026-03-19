package com.petkit.android.activities.petkitBleDevice.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.petkit.android.activities.base.BaseApplication;
import com.petkit.android.activities.feeder.widget.BaseFeederWindow;
import com.petkit.android.activities.petkitBleDevice.t6.utils.T6Utils;
import com.petkit.android.activities.petkitBleDevice.utils.BleDeviceUtils;
import com.petkit.android.activities.petkitBleDevice.w7h.utils.W7hUtils;
import com.petkit.android.utils.CommonUtils;
import com.petkit.oversea.R;

/* JADX INFO: loaded from: classes5.dex */
public class DeviceErrorWarnWindow extends BaseFeederWindow {
    private RotateAnimation animation;
    private final TextView contactCustomerService;
    private String content;
    private final TextView contentTextView;
    private int deviceType;
    private String img;
    private ImageView ivLoading;
    private boolean needContactService;
    private OnClickListener onClickListener;
    private int res;
    private RelativeLayout rlImage;
    private String title;

    public void setResetListener() {
        this.contentTextView.setMovementMethod(LinkMovementMethod.getInstance());
        this.contentTextView.setHighlightColor(0);
        Context context = this.context;
        this.content = context.getString(R.string.W7h_lift_valve_error_tips, context.getString(R.string.Reset));
        this.contentTextView.setText(W7hUtils.getInstance().getStrByContent(this.context, this.content, new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.widget.DeviceErrorWarnWindow.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (DeviceErrorWarnWindow.this.onClickListener != null) {
                    DeviceErrorWarnWindow.this.onClickListener.onClickText();
                }
                DeviceErrorWarnWindow.this.dismiss();
            }
        }));
    }

    public DeviceErrorWarnWindow(Activity activity, int i, String str, String str2, String str3) {
        super(activity, true);
        this.needContactService = true;
        this.content = str2;
        this.title = str;
        this.img = str3;
        this.deviceType = i;
        initContentView(R.layout.pop_device_home_fail_warn);
        if (!TextUtils.isEmpty(str)) {
            setTitle(str);
        } else {
            setTitle("");
        }
        setBackgroundDrawable(new BitmapDrawable());
        this.action.setVisibility(8);
        this.close.setImageResource(BleDeviceUtils.getPopWindowCloseIcon(i));
        this.close.setPadding(ArmsUtils.dip2px(CommonUtils.getAppContext(), 16.0f), ArmsUtils.dip2px(CommonUtils.getAppContext(), 16.0f), ArmsUtils.dip2px(CommonUtils.getAppContext(), 16.0f), ArmsUtils.dip2px(CommonUtils.getAppContext(), 16.0f));
        this.ivLoading = (ImageView) getContentView().findViewById(R.id.iv_loading);
        TextView textView = (TextView) getContentView().findViewById(R.id.tv_trouble_warn_tip);
        getContentView().findViewById(R.id.pop_gap_line).setBackgroundColor(CommonUtils.getColorById(R.color.feeder_activate_background));
        TextView textView2 = (TextView) getContentView().findViewById(R.id.tv_k2_trouble_warn_content);
        this.contentTextView = textView2;
        TextView textView3 = (TextView) getContentView().findViewById(R.id.tv_k2_trouble_warn_contact_customer_service);
        this.contactCustomerService = textView3;
        ImageView imageView = (ImageView) getContentView().findViewById(R.id.tv_k2_trouble_warn_icon);
        this.rlImage = (RelativeLayout) getContentView().findViewById(R.id.rl_image);
        startLoading();
        ((BaseApplication) CommonUtils.getApplication()).getAppComponent().imageLoader().autoLoadImage(CommonUtils.getAppContext(), GlideImageConfig.builder().url(this.img).isGif(true).imageView(imageView).newListener(new RequestListener<String, GlideDrawable>() { // from class: com.petkit.android.activities.petkitBleDevice.widget.DeviceErrorWarnWindow.2
            @Override // com.bumptech.glide.request.RequestListener
            public boolean onException(Exception exc, String str4, Target<GlideDrawable> target, boolean z) {
                return false;
            }

            @Override // com.bumptech.glide.request.RequestListener
            public boolean onResourceReady(GlideDrawable glideDrawable, String str4, Target<GlideDrawable> target, boolean z, boolean z2) {
                return false;
            }
        }).build());
        textView.setVisibility(8);
        if (this.needContactService) {
            textView3.setVisibility(0);
        } else {
            textView3.setVisibility(4);
        }
        textView2.setText(T6Utils.getStr(this.context, str2, i));
        textView3.setTextColor(BleDeviceUtils.getDeviceColor(i));
        textView3.setText(activity.getString(R.string.Know_more) + ">");
        textView3.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.widget.DeviceErrorWarnWindow.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (DeviceErrorWarnWindow.this.onClickListener != null) {
                    DeviceErrorWarnWindow.this.onClickListener.onClickLearnMore();
                }
                DeviceErrorWarnWindow.this.dismiss();
            }
        });
    }

    public DeviceErrorWarnWindow(Activity activity, int i, String str, String str2, String str3, String str4) {
        super(activity, true);
        this.needContactService = true;
        this.content = str2;
        this.title = str;
        this.img = str3;
        this.deviceType = i;
        initContentView(R.layout.pop_device_home_fail_warn);
        if (!TextUtils.isEmpty(str)) {
            setTitle(str);
        } else {
            setTitle("");
        }
        setBackgroundDrawable(new BitmapDrawable());
        this.action.setVisibility(8);
        this.close.setImageResource(BleDeviceUtils.getPopWindowCloseIcon(i));
        this.close.setPadding(ArmsUtils.dip2px(CommonUtils.getAppContext(), 16.0f), ArmsUtils.dip2px(CommonUtils.getAppContext(), 16.0f), ArmsUtils.dip2px(CommonUtils.getAppContext(), 16.0f), ArmsUtils.dip2px(CommonUtils.getAppContext(), 16.0f));
        this.ivLoading = (ImageView) getContentView().findViewById(R.id.iv_loading);
        TextView textView = (TextView) getContentView().findViewById(R.id.tv_trouble_warn_tip);
        getContentView().findViewById(R.id.pop_gap_line).setBackgroundColor(CommonUtils.getColorById(R.color.feeder_activate_background));
        TextView textView2 = (TextView) getContentView().findViewById(R.id.tv_k2_trouble_warn_content);
        this.contentTextView = textView2;
        TextView textView3 = (TextView) getContentView().findViewById(R.id.tv_k2_trouble_warn_contact_customer_service);
        this.contactCustomerService = textView3;
        ImageView imageView = (ImageView) getContentView().findViewById(R.id.tv_k2_trouble_warn_icon);
        this.rlImage = (RelativeLayout) getContentView().findViewById(R.id.rl_image);
        startLoading();
        ((BaseApplication) CommonUtils.getApplication()).getAppComponent().imageLoader().autoLoadImage(CommonUtils.getAppContext(), GlideImageConfig.builder().url(str3).isGif(true).imageView(imageView).newListener(new RequestListener<String, GlideDrawable>() { // from class: com.petkit.android.activities.petkitBleDevice.widget.DeviceErrorWarnWindow.4
            @Override // com.bumptech.glide.request.RequestListener
            public boolean onException(Exception exc, String str5, Target<GlideDrawable> target, boolean z) {
                return false;
            }

            @Override // com.bumptech.glide.request.RequestListener
            public boolean onResourceReady(GlideDrawable glideDrawable, String str5, Target<GlideDrawable> target, boolean z, boolean z2) {
                return false;
            }
        }).build());
        if (TextUtils.isEmpty(str4)) {
            textView.setVisibility(8);
        } else {
            textView.setVisibility(0);
            textView.setText(str4);
        }
        if (this.needContactService) {
            textView3.setVisibility(0);
        } else {
            textView3.setVisibility(4);
        }
        textView2.setText(T6Utils.getStr(this.context, str2, i));
        textView3.setTextColor(BleDeviceUtils.getDeviceColor(i));
        textView3.setText(activity.getString(R.string.Know_more) + ">");
        textView3.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.widget.DeviceErrorWarnWindow.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (DeviceErrorWarnWindow.this.onClickListener != null) {
                    DeviceErrorWarnWindow.this.onClickListener.onClickLearnMore();
                }
                DeviceErrorWarnWindow.this.dismiss();
            }
        });
    }

    public DeviceErrorWarnWindow(Activity activity, int i, String str, String str2, String str3, String str4, String str5) {
        super(activity, true);
        this.needContactService = true;
        this.content = str2;
        this.title = str;
        this.img = str3;
        this.deviceType = i;
        initContentView(R.layout.pop_device_home_fail_warn);
        if (!TextUtils.isEmpty(str)) {
            setTitle(str);
        } else {
            setTitle("");
        }
        setBackgroundDrawable(new BitmapDrawable());
        this.action.setVisibility(8);
        this.close.setImageResource(BleDeviceUtils.getPopWindowCloseIcon(i));
        this.close.setPadding(ArmsUtils.dip2px(CommonUtils.getAppContext(), 16.0f), ArmsUtils.dip2px(CommonUtils.getAppContext(), 16.0f), ArmsUtils.dip2px(CommonUtils.getAppContext(), 16.0f), ArmsUtils.dip2px(CommonUtils.getAppContext(), 16.0f));
        this.ivLoading = (ImageView) getContentView().findViewById(R.id.iv_loading);
        TextView textView = (TextView) getContentView().findViewById(R.id.tv_trouble_warn_tip);
        getContentView().findViewById(R.id.pop_gap_line).setBackgroundColor(CommonUtils.getColorById(R.color.feeder_activate_background));
        TextView textView2 = (TextView) getContentView().findViewById(R.id.tv_k2_trouble_warn_content);
        this.contentTextView = textView2;
        TextView textView3 = (TextView) getContentView().findViewById(R.id.tv_k2_trouble_warn_contact_customer_service);
        this.contactCustomerService = textView3;
        ImageView imageView = (ImageView) getContentView().findViewById(R.id.tv_k2_trouble_warn_icon);
        this.rlImage = (RelativeLayout) getContentView().findViewById(R.id.rl_image);
        startLoading();
        ((BaseApplication) CommonUtils.getApplication()).getAppComponent().imageLoader().autoLoadImage(CommonUtils.getAppContext(), GlideImageConfig.builder().url(str3).imageView(imageView).newListener(new RequestListener<String, GlideDrawable>() { // from class: com.petkit.android.activities.petkitBleDevice.widget.DeviceErrorWarnWindow.6
            @Override // com.bumptech.glide.request.RequestListener
            public boolean onException(Exception exc, String str6, Target<GlideDrawable> target, boolean z) {
                return false;
            }

            @Override // com.bumptech.glide.request.RequestListener
            public boolean onResourceReady(GlideDrawable glideDrawable, String str6, Target<GlideDrawable> target, boolean z, boolean z2) {
                return false;
            }
        }).build());
        if (TextUtils.isEmpty(str4)) {
            textView.setVisibility(8);
        } else {
            textView.setVisibility(0);
            textView.setText(str4);
        }
        if (this.needContactService) {
            textView3.setVisibility(0);
        } else {
            textView3.setVisibility(4);
        }
        textView2.setText(T6Utils.getStr(this.context, str2, i));
        textView3.setTextColor(BleDeviceUtils.getDeviceColor(i));
        textView3.setText(str5);
        textView3.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.widget.DeviceErrorWarnWindow.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (DeviceErrorWarnWindow.this.onClickListener != null) {
                    DeviceErrorWarnWindow.this.onClickListener.onClickLearnMore();
                }
                DeviceErrorWarnWindow.this.dismiss();
            }
        });
    }

    public DeviceErrorWarnWindow(Activity activity, int i, String str, String str2, int i2, String str3) {
        super(activity, true);
        this.needContactService = true;
        this.content = str2;
        this.title = str;
        this.res = i2;
        this.deviceType = i;
        initContentView(R.layout.pop_device_home_fail_warn);
        setTitle(str);
        setBackgroundDrawable(new BitmapDrawable());
        this.action.setVisibility(8);
        this.close.setImageResource(BleDeviceUtils.getPopWindowCloseIcon(i));
        this.close.setPadding(ArmsUtils.dip2px(CommonUtils.getAppContext(), 16.0f), ArmsUtils.dip2px(CommonUtils.getAppContext(), 16.0f), ArmsUtils.dip2px(CommonUtils.getAppContext(), 16.0f), ArmsUtils.dip2px(CommonUtils.getAppContext(), 16.0f));
        TextView textView = (TextView) getContentView().findViewById(R.id.tv_trouble_warn_tip);
        getContentView().findViewById(R.id.pop_gap_line).setBackgroundColor(CommonUtils.getColorById(R.color.feeder_activate_background));
        TextView textView2 = (TextView) getContentView().findViewById(R.id.tv_k2_trouble_warn_content);
        this.contentTextView = textView2;
        TextView textView3 = (TextView) getContentView().findViewById(R.id.tv_k2_trouble_warn_contact_customer_service);
        this.contactCustomerService = textView3;
        ImageView imageView = (ImageView) getContentView().findViewById(R.id.tv_k2_trouble_warn_icon);
        this.rlImage = (RelativeLayout) getContentView().findViewById(R.id.rl_image);
        imageView.setImageResource(i2);
        if (TextUtils.isEmpty(str3)) {
            textView.setVisibility(8);
        } else {
            textView.setVisibility(0);
            textView.setText(str3);
        }
        if (this.needContactService) {
            textView3.setVisibility(0);
        } else {
            textView3.setVisibility(4);
        }
        textView2.setText(str2);
        textView3.setTextColor(BleDeviceUtils.getDeviceColor(i));
        textView3.setText(activity.getString(R.string.Know_more) + ">");
        textView3.setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.activities.petkitBleDevice.widget.DeviceErrorWarnWindow.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (DeviceErrorWarnWindow.this.onClickListener != null) {
                    DeviceErrorWarnWindow.this.onClickListener.onClickLearnMore();
                }
                DeviceErrorWarnWindow.this.dismiss();
            }
        });
    }

    public void setNeedContactService(boolean z) {
        this.needContactService = z;
        if (z) {
            this.contactCustomerService.setVisibility(0);
        } else {
            this.contactCustomerService.setVisibility(4);
        }
    }

    public void setBtnText(String str) {
        this.contactCustomerService.setText(str);
    }

    private void startLoading() {
        this.ivLoading.setVisibility(0);
        if (this.animation == null) {
            this.animation = new RotateAnimation(0.0f, 360.0f, 1, 0.5f, 1, 0.5f);
        }
        this.animation.setFillAfter(true);
        this.animation.setDuration(2000L);
        this.animation.setRepeatCount(-1);
        this.animation.setInterpolator(new AccelerateDecelerateInterpolator());
        this.ivLoading.startAnimation(this.animation);
    }

    public void setTitleVisible(boolean z) {
        super.title.setVisibility(z ? 0 : 8);
    }

    public void setContentPadding(int i) {
        float f = i;
        this.rlImage.setPadding(ArmsUtils.dip2px(CommonUtils.getAppContext(), f), 0, ArmsUtils.dip2px(CommonUtils.getAppContext(), f), 0);
    }

    @Override // com.petkit.android.activities.feeder.widget.BaseFeederWindow
    public void onActionClick() {
        dismiss();
    }

    public OnClickListener getOnClickListener() {
        return this.onClickListener;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onClickLearnMore();

        void onClickText();

        /* JADX INFO: renamed from: com.petkit.android.activities.petkitBleDevice.widget.DeviceErrorWarnWindow$OnClickListener$-CC, reason: invalid class name */
        public final /* synthetic */ class CC {
            public static void $default$onClickText(OnClickListener onClickListener) {
            }
        }
    }
}

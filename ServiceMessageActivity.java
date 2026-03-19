package com.qiyukf.unicorn.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import com.qiyukf.module.log.base.AbsUnicornLog;
import com.qiyukf.nimlib.sdk.NimIntent;
import com.qiyukf.nimlib.sdk.msg.model.IMMessage;
import com.qiyukf.uikit.common.activity.BaseFragmentActivity;
import com.qiyukf.unicorn.R;
import com.qiyukf.unicorn.a.a;
import com.qiyukf.unicorn.api.ConsultSource;
import com.qiyukf.unicorn.api.event.UnicornEventBase;
import com.qiyukf.unicorn.api.lifecycle.SessionLifeCycleListener;
import com.qiyukf.unicorn.api.lifecycle.SessionLifeCycleOptions;
import com.qiyukf.unicorn.c;
import com.qiyukf.unicorn.n.g;
import com.qiyukf.unicorn.ui.fragment.ServiceMessageFragment;
import io.agora.rtc2.internal.AudioRoutingController;

/* JADX INFO: loaded from: classes6.dex */
public class ServiceMessageActivity extends BaseFragmentActivity {
    private static final String TAG = "ServiceMessageActivity";
    private ServiceMessageFragment messageFragment;
    private ImageView ysfIvTitleBarRightBtn;
    private TextView ysfTvTitleBarRightBtn;
    private Boolean isRestart = Boolean.FALSE;
    private SessionLifeCycleListener sessionLifeCycleListener = new SessionLifeCycleListener() { // from class: com.qiyukf.unicorn.ui.activity.ServiceMessageActivity.2
        @Override // com.qiyukf.unicorn.api.lifecycle.SessionLifeCycleListener
        public void onLeaveSession() {
            g.a(ServiceMessageActivity.this);
            ServiceMessageActivity.this.finish();
        }
    };

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    @SuppressLint({"MissingSuperCall"})
    public void onSaveInstanceState(@NonNull Bundle bundle) {
    }

    public static void start(Context context, String str, ConsultSource consultSource) {
        Intent intent = new Intent();
        intent.putExtra("source", consultSource);
        intent.putExtra("title", str);
        intent.setClass(context, ServiceMessageActivity.class);
        if (consultSource == null || !consultSource.forbidUseCleanTopStart) {
            intent.addFlags(AudioRoutingController.DEVICE_OUT_USB_HEADSET);
        }
        if (!(context instanceof Activity)) {
            intent.addFlags(268435456);
        }
        context.startActivity(intent);
    }

    @Override // com.qiyukf.uikit.common.activity.BaseFragmentActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        String appName;
        ConsultSource consultSource;
        resetFragments(bundle);
        super.onCreate(bundle);
        setContentView(R.layout.ysf_message_activity);
        if (c.f().titleBarConfig != null && c.f().titleBarConfig.titleBarRightImg != 0 && c.f().titleBarConfig.onTitleBarRightBtnClickListener != null) {
            addCustomMenu();
        }
        a.a(this);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(0);
        linearLayout.setGravity(17);
        addViewMenu(linearLayout);
        Intent intent = getIntent();
        if (intent.hasExtra("source")) {
            appName = intent.getStringExtra("title");
            consultSource = (ConsultSource) intent.getSerializableExtra("source");
        } else if (intent.hasExtra(NimIntent.EXTRA_NOTIFY_CONTENT)) {
            appName = getAppName();
            ConsultSource consultSource2 = new ConsultSource("com.qiyukf.notification", getString(R.string.ysf_service_source_title_notification), null);
            if (intent.getSerializableExtra(NimIntent.EXTRA_NOTIFY_CONTENT) instanceof IMMessage) {
                consultSource2.shopId = ((IMMessage) intent.getSerializableExtra(NimIntent.EXTRA_NOTIFY_CONTENT)).getSessionId();
            }
            consultSource = consultSource2;
        } else {
            appName = getAppName();
            consultSource = null;
        }
        if (consultSource == null) {
            AbsUnicornLog.i(TAG, "ServiceMessageActivity ConsultSource is null");
            consultSource = new ConsultSource(null, null, null);
        }
        if (consultSource.groupId != 0) {
            AbsUnicornLog.i(TAG, "ServiceMessageActivity ConsultSource source.groupId: " + consultSource.groupId);
        }
        if (consultSource.sessionLifeCycleOptions == null) {
            consultSource.sessionLifeCycleOptions = new SessionLifeCycleOptions();
        }
        consultSource.sessionLifeCycleOptions.setSessionLifeCycleListener(this.sessionLifeCycleListener);
        ServiceMessageFragment serviceMessageFragment = new ServiceMessageFragment();
        this.messageFragment = serviceMessageFragment;
        serviceMessageFragment.setArguments(appName, consultSource, linearLayout);
        replaceFragment(R.id.message_fragment_container, this.messageFragment);
    }

    private void resetFragments(Bundle bundle) {
        if (bundle != null) {
            try {
                bundle.putParcelable(FragmentManager.SAVED_STATE_TAG, null);
            } catch (Exception e) {
                AbsUnicornLog.i(TAG, "resetFragments error: " + e.getMessage());
            }
        }
    }

    private void addCustomMenu() {
        View viewInflate = LayoutInflater.from(this).inflate(R.layout.ysf_action_bar_right_custom_img_layout, (ViewGroup) null);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
        layoutParams.gravity = 16;
        viewInflate.setLayoutParams(layoutParams);
        addViewMenu(viewInflate);
        ImageView imageView = (ImageView) viewInflate.findViewById(R.id.ysf_iv_title_bar_right_btn);
        this.ysfIvTitleBarRightBtn = imageView;
        imageView.setImageResource(c.f().titleBarConfig.titleBarRightImg);
        this.ysfTvTitleBarRightBtn = (TextView) viewInflate.findViewById(R.id.ysf_tv_title_bar_right_btn);
        if (!TextUtils.isEmpty(c.f().titleBarConfig.titleBarRightText)) {
            this.ysfTvTitleBarRightBtn.setText(c.f().titleBarConfig.titleBarRightText.length() > 4 ? c.f().titleBarConfig.titleBarRightText.substring(0, 4) : c.f().titleBarConfig.titleBarRightText);
        } else {
            this.ysfTvTitleBarRightBtn.setVisibility(8);
        }
        viewInflate.setOnClickListener(new View.OnClickListener() { // from class: com.qiyukf.unicorn.ui.activity.ServiceMessageActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                c.f().titleBarConfig.onTitleBarRightBtnClickListener.onClick(ServiceMessageActivity.this);
            }
        });
    }

    private String getAppName() {
        try {
            PackageManager packageManager = getApplicationContext().getPackageManager();
            return packageManager.getApplicationLabel(packageManager.getApplicationInfo(getPackageName(), 128)).toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return getString(R.string.ysf_service_title_default);
        }
    }

    @Override // com.qiyukf.uikit.common.activity.BaseFragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        g.a(this);
        try {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStack();
                return;
            }
        } catch (Exception e) {
            AbsUnicornLog.e(TAG, "onBackPressed exception: ".concat(String.valueOf(e)));
        }
        ServiceMessageFragment serviceMessageFragment = this.messageFragment;
        if (serviceMessageFragment == null || !serviceMessageFragment.onBackPressed()) {
            if (c.f().sdkEvents != null && c.f().sdkEvents.eventProcessFactory != null) {
                UnicornEventBase unicornEventBaseEventOf = c.f().sdkEvents.eventProcessFactory.eventOf(3);
                if (unicornEventBaseEventOf != null) {
                    unicornEventBaseEventOf.onEvent("", this, null);
                    return;
                } else {
                    finish();
                    return;
                }
            }
            finish();
        }
    }

    @Override // android.app.Activity
    public void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
        if (bundle != null) {
            this.isRestart = Boolean.TRUE;
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        ServiceMessageFragment serviceMessageFragment;
        super.onActivityResult(i, i2, intent);
        if (!this.isRestart.booleanValue() || (serviceMessageFragment = this.messageFragment) == null) {
            return;
        }
        this.isRestart = Boolean.FALSE;
        serviceMessageFragment.onActivityResult(i, i2, intent);
    }

    @Override // com.qiyukf.uikit.common.activity.BaseFragmentActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        a.a();
        super.onDestroy();
    }
}

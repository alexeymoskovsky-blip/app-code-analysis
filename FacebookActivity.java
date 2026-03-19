package com.facebook;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import com.facebook.internal.FacebookDialogFragment;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.Utility;
import com.facebook.internal.instrument.crashshield.CrashShieldHandler;
import com.facebook.internal.logging.dumpsys.EndToEndDumper;
import com.facebook.login.LoginFragment;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: FacebookActivity.kt */
/* JADX INFO: loaded from: classes.dex */
public class FacebookActivity extends FragmentActivity {

    @NotNull
    private static final String FRAGMENT_TAG = "SingleFragment";

    @NotNull
    public static final String PASS_THROUGH_CANCEL_ACTION = "PassThrough";

    @Nullable
    private Fragment currentFragment;

    @NotNull
    public static final Companion Companion = new Companion(null);
    private static final String TAG = FacebookActivity.class.getName();

    @Nullable
    public final Fragment getCurrentFragment() {
        return this.currentFragment;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        Intent intent = getIntent();
        if (!FacebookSdk.isInitialized()) {
            Utility.logd(TAG, "Facebook SDK not initialized. Make sure you call sdkInitialize inside your Application's onCreate method.");
            Context applicationContext = getApplicationContext();
            Intrinsics.checkNotNullExpressionValue(applicationContext, "applicationContext");
            FacebookSdk.sdkInitialize(applicationContext);
        }
        setContentView(com.facebook.common.R.layout.com_facebook_activity_layout);
        if (Intrinsics.areEqual(PASS_THROUGH_CANCEL_ACTION, intent.getAction())) {
            handlePassThroughError();
        } else {
            this.currentFragment = getFragment();
        }
    }

    @NotNull
    public Fragment getFragment() {
        Fragment fragment;
        Intent intent = getIntent();
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        Intrinsics.checkNotNullExpressionValue(supportFragmentManager, "supportFragmentManager");
        Fragment fragmentFindFragmentByTag = supportFragmentManager.findFragmentByTag(FRAGMENT_TAG);
        if (fragmentFindFragmentByTag != null) {
            return fragmentFindFragmentByTag;
        }
        if (Intrinsics.areEqual(FacebookDialogFragment.TAG, intent.getAction())) {
            FacebookDialogFragment facebookDialogFragment = new FacebookDialogFragment();
            facebookDialogFragment.setRetainInstance(true);
            facebookDialogFragment.show(supportFragmentManager, FRAGMENT_TAG);
            fragment = facebookDialogFragment;
        } else {
            LoginFragment loginFragment = new LoginFragment();
            loginFragment.setRetainInstance(true);
            supportFragmentManager.beginTransaction().add(com.facebook.common.R.id.com_facebook_fragment_container, loginFragment, FRAGMENT_TAG).commit();
            fragment = loginFragment;
        }
        return fragment;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(@NotNull Configuration newConfig) {
        Intrinsics.checkNotNullParameter(newConfig, "newConfig");
        super.onConfigurationChanged(newConfig);
        Fragment fragment = this.currentFragment;
        if (fragment != null) {
            fragment.onConfigurationChanged(newConfig);
        }
    }

    private final void handlePassThroughError() {
        Intent requestIntent = getIntent();
        Intrinsics.checkNotNullExpressionValue(requestIntent, "requestIntent");
        FacebookException exceptionFromErrorData = NativeProtocol.getExceptionFromErrorData(NativeProtocol.getMethodArgumentsFromIntent(requestIntent));
        Intent intent = getIntent();
        Intrinsics.checkNotNullExpressionValue(intent, "intent");
        setResult(0, NativeProtocol.createProtocolResultIntent(intent, null, exceptionFromErrorData));
        finish();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void dump(@NotNull String prefix, @Nullable FileDescriptor fileDescriptor, @NotNull PrintWriter writer, @Nullable String[] strArr) {
        if (CrashShieldHandler.isObjectCrashing(this)) {
            return;
        }
        try {
            Intrinsics.checkNotNullParameter(prefix, "prefix");
            Intrinsics.checkNotNullParameter(writer, "writer");
            EndToEndDumper companion = EndToEndDumper.Companion.getInstance();
            if (companion == null || !companion.maybeDump(prefix, writer, strArr)) {
                super.dump(prefix, fileDescriptor, writer, strArr);
            }
        } catch (Throwable th) {
            CrashShieldHandler.handleThrowable(th, this);
        }
    }

    /* JADX INFO: compiled from: FacebookActivity.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}

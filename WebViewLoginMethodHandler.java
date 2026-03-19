package com.facebook.login;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.RestrictTo;
import androidx.fragment.app.FragmentActivity;
import com.facebook.AccessTokenSource;
import com.facebook.FacebookException;
import com.facebook.internal.FacebookDialogFragment;
import com.facebook.internal.ServerProtocol;
import com.facebook.internal.Utility;
import com.facebook.internal.WebDialog;
import com.facebook.login.LoginClient;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: WebViewLoginMethodHandler.kt */
/* JADX INFO: loaded from: classes.dex */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class WebViewLoginMethodHandler extends WebLoginMethodHandler {

    @NotNull
    private static final String OAUTH_DIALOG = "oauth";

    @Nullable
    private String e2e;

    @Nullable
    private WebDialog loginDialog;

    @NotNull
    private final String nameForLogging;

    @NotNull
    private final AccessTokenSource tokenSource;

    @NotNull
    public static final Companion Companion = new Companion(null);

    @JvmField
    @NotNull
    public static final Parcelable.Creator<WebViewLoginMethodHandler> CREATOR = new Parcelable.Creator<WebViewLoginMethodHandler>() { // from class: com.facebook.login.WebViewLoginMethodHandler$Companion$CREATOR$1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        @NotNull
        public WebViewLoginMethodHandler createFromParcel(@NotNull Parcel source) {
            Intrinsics.checkNotNullParameter(source, "source");
            return new WebViewLoginMethodHandler(source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        @NotNull
        public WebViewLoginMethodHandler[] newArray(int i) {
            return new WebViewLoginMethodHandler[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // com.facebook.login.LoginMethodHandler
    public boolean needsInternetPermission() {
        return true;
    }

    @Nullable
    public final WebDialog getLoginDialog() {
        return this.loginDialog;
    }

    public final void setLoginDialog(@Nullable WebDialog webDialog) {
        this.loginDialog = webDialog;
    }

    @Nullable
    public final String getE2e() {
        return this.e2e;
    }

    public final void setE2e(@Nullable String str) {
        this.e2e = str;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WebViewLoginMethodHandler(@NotNull LoginClient loginClient) {
        super(loginClient);
        Intrinsics.checkNotNullParameter(loginClient, "loginClient");
        this.nameForLogging = "web_view";
        this.tokenSource = AccessTokenSource.WEB_VIEW;
    }

    @Override // com.facebook.login.LoginMethodHandler
    @NotNull
    public String getNameForLogging() {
        return this.nameForLogging;
    }

    @Override // com.facebook.login.WebLoginMethodHandler
    @NotNull
    public AccessTokenSource getTokenSource() {
        return this.tokenSource;
    }

    @Override // com.facebook.login.LoginMethodHandler
    public void cancel() {
        WebDialog webDialog = this.loginDialog;
        if (webDialog != null) {
            if (webDialog != null) {
                webDialog.cancel();
            }
            this.loginDialog = null;
        }
    }

    @Override // com.facebook.login.LoginMethodHandler
    public int tryAuthorize(@NotNull final LoginClient.Request request) {
        Intrinsics.checkNotNullParameter(request, "request");
        Bundle parameters = getParameters(request);
        WebDialog.OnCompleteListener onCompleteListener = new WebDialog.OnCompleteListener() { // from class: com.facebook.login.WebViewLoginMethodHandler$tryAuthorize$listener$1
            @Override // com.facebook.internal.WebDialog.OnCompleteListener
            public void onComplete(@Nullable Bundle bundle, @Nullable FacebookException facebookException) {
                this.this$0.onWebDialogComplete(request, bundle, facebookException);
            }
        };
        String e2e = LoginClient.Companion.getE2E();
        this.e2e = e2e;
        addLoggingExtra("e2e", e2e);
        FragmentActivity activity = getLoginClient().getActivity();
        if (activity == null) {
            return 0;
        }
        boolean zIsChromeOS = Utility.isChromeOS(activity);
        AuthDialogBuilder authDialogBuilder = new AuthDialogBuilder(this, activity, request.getApplicationId(), parameters);
        String str = this.e2e;
        Intrinsics.checkNotNull(str, "null cannot be cast to non-null type kotlin.String");
        this.loginDialog = authDialogBuilder.setE2E(str).setIsChromeOS(zIsChromeOS).setAuthType(request.getAuthType()).setLoginBehavior(request.getLoginBehavior()).setLoginTargetApp(request.getLoginTargetApp()).setFamilyLogin(request.isFamilyLogin()).setShouldSkipDedupe(request.shouldSkipAccountDeduplication()).setOnCompleteListener(onCompleteListener).build();
        FacebookDialogFragment facebookDialogFragment = new FacebookDialogFragment();
        facebookDialogFragment.setRetainInstance(true);
        facebookDialogFragment.setInnerDialog(this.loginDialog);
        facebookDialogFragment.show(activity.getSupportFragmentManager(), FacebookDialogFragment.TAG);
        return 1;
    }

    public final void onWebDialogComplete(@NotNull LoginClient.Request request, @Nullable Bundle bundle, @Nullable FacebookException facebookException) {
        Intrinsics.checkNotNullParameter(request, "request");
        super.onComplete(request, bundle, facebookException);
    }

    /* JADX INFO: compiled from: WebViewLoginMethodHandler.kt */
    public final class AuthDialogBuilder extends WebDialog.Builder {
        public String authType;
        public String e2e;
        private boolean isFamilyLogin;

        @NotNull
        private LoginBehavior loginBehavior;

        @NotNull
        private String redirect_uri;
        private boolean shouldSkipDedupe;

        @NotNull
        private LoginTargetApp targetApp;
        final /* synthetic */ WebViewLoginMethodHandler this$0;

        @NotNull
        public final AuthDialogBuilder setIsRerequest(boolean z) {
            return this;
        }

        @NotNull
        public final String getE2e() {
            String str = this.e2e;
            if (str != null) {
                return str;
            }
            Intrinsics.throwUninitializedPropertyAccessException("e2e");
            return null;
        }

        public final void setE2e(@NotNull String str) {
            Intrinsics.checkNotNullParameter(str, "<set-?>");
            this.e2e = str;
        }

        @NotNull
        public final String getAuthType() {
            String str = this.authType;
            if (str != null) {
                return str;
            }
            Intrinsics.throwUninitializedPropertyAccessException("authType");
            return null;
        }

        /* JADX INFO: renamed from: setAuthType */
        public final void m133setAuthType(@NotNull String str) {
            Intrinsics.checkNotNullParameter(str, "<set-?>");
            this.authType = str;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AuthDialogBuilder(@NotNull WebViewLoginMethodHandler webViewLoginMethodHandler, @NotNull Context context, @NotNull String applicationId, Bundle parameters) {
            super(context, applicationId, "oauth", parameters);
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(applicationId, "applicationId");
            Intrinsics.checkNotNullParameter(parameters, "parameters");
            this.this$0 = webViewLoginMethodHandler;
            this.redirect_uri = ServerProtocol.DIALOG_REDIRECT_URI;
            this.loginBehavior = LoginBehavior.NATIVE_WITH_FALLBACK;
            this.targetApp = LoginTargetApp.FACEBOOK;
        }

        @NotNull
        public final AuthDialogBuilder setE2E(@NotNull String e2e) {
            Intrinsics.checkNotNullParameter(e2e, "e2e");
            setE2e(e2e);
            return this;
        }

        @NotNull
        public final AuthDialogBuilder setIsChromeOS(boolean z) {
            String str;
            if (z) {
                str = ServerProtocol.DIALOG_REDIRECT_CHROME_OS_URI;
            } else {
                str = ServerProtocol.DIALOG_REDIRECT_URI;
            }
            this.redirect_uri = str;
            return this;
        }

        @NotNull
        public final AuthDialogBuilder setAuthType(@NotNull String authType) {
            Intrinsics.checkNotNullParameter(authType, "authType");
            m133setAuthType(authType);
            return this;
        }

        @NotNull
        public final AuthDialogBuilder setLoginBehavior(@NotNull LoginBehavior loginBehavior) {
            Intrinsics.checkNotNullParameter(loginBehavior, "loginBehavior");
            this.loginBehavior = loginBehavior;
            return this;
        }

        @NotNull
        public final AuthDialogBuilder setLoginTargetApp(@NotNull LoginTargetApp targetApp) {
            Intrinsics.checkNotNullParameter(targetApp, "targetApp");
            this.targetApp = targetApp;
            return this;
        }

        @NotNull
        public final AuthDialogBuilder setFamilyLogin(boolean z) {
            this.isFamilyLogin = z;
            return this;
        }

        @NotNull
        public final AuthDialogBuilder setShouldSkipDedupe(boolean z) {
            this.shouldSkipDedupe = z;
            return this;
        }

        @Override // com.facebook.internal.WebDialog.Builder
        @NotNull
        public WebDialog build() {
            String str;
            Bundle parameters = getParameters();
            Intrinsics.checkNotNull(parameters, "null cannot be cast to non-null type android.os.Bundle");
            parameters.putString(ServerProtocol.DIALOG_PARAM_REDIRECT_URI, this.redirect_uri);
            parameters.putString("client_id", getApplicationId());
            parameters.putString("e2e", getE2e());
            if (this.targetApp == LoginTargetApp.INSTAGRAM) {
                str = ServerProtocol.DIALOG_RESPONSE_TYPE_TOKEN_AND_SCOPES;
            } else {
                str = ServerProtocol.DIALOG_RESPONSE_TYPE_TOKEN_AND_SIGNED_REQUEST;
            }
            parameters.putString(ServerProtocol.DIALOG_PARAM_RESPONSE_TYPE, str);
            parameters.putString(ServerProtocol.DIALOG_PARAM_RETURN_SCOPES, ServerProtocol.DIALOG_RETURN_SCOPES_TRUE);
            parameters.putString("auth_type", getAuthType());
            parameters.putString("login_behavior", this.loginBehavior.name());
            if (this.isFamilyLogin) {
                parameters.putString(ServerProtocol.DIALOG_PARAM_FX_APP, this.targetApp.toString());
            }
            if (this.shouldSkipDedupe) {
                parameters.putString(ServerProtocol.DIALOG_PARAM_SKIP_DEDUPE, ServerProtocol.DIALOG_RETURN_SCOPES_TRUE);
            }
            WebDialog.Companion companion = WebDialog.Companion;
            Context context = getContext();
            Intrinsics.checkNotNull(context, "null cannot be cast to non-null type android.content.Context");
            return companion.newInstance(context, "oauth", parameters, getTheme(), this.targetApp, getListener());
        }
    }

    /* JADX INFO: compiled from: WebViewLoginMethodHandler.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WebViewLoginMethodHandler(@NotNull Parcel source) {
        super(source);
        Intrinsics.checkNotNullParameter(source, "source");
        this.nameForLogging = "web_view";
        this.tokenSource = AccessTokenSource.WEB_VIEW;
        this.e2e = source.readString();
    }

    @Override // com.facebook.login.LoginMethodHandler, android.os.Parcelable
    public void writeToParcel(@NotNull Parcel dest, int i) {
        Intrinsics.checkNotNullParameter(dest, "dest");
        super.writeToParcel(dest, i);
        dest.writeString(this.e2e);
    }
}

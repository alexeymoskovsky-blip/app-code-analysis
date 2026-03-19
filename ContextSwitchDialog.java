package com.facebook.gamingservices;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookRequestError;
import com.facebook.FacebookSdk;
import com.facebook.GraphResponse;
import com.facebook.gamingservices.ContextSwitchDialog;
import com.facebook.gamingservices.cloudgaming.CloudGameLoginHandler;
import com.facebook.gamingservices.cloudgaming.DaemonRequest;
import com.facebook.gamingservices.cloudgaming.internal.SDKConstants;
import com.facebook.gamingservices.cloudgaming.internal.SDKMessageEnum;
import com.facebook.gamingservices.model.ContextSwitchContent;
import com.facebook.internal.AppCall;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.internal.DialogPresenter;
import com.facebook.internal.FacebookDialogBase;
import com.facebook.internal.FragmentWrapper;
import com.facebook.internal.NativeProtocol;
import com.facebook.share.internal.ResultProcessor;
import com.facebook.share.internal.ShareInternalUtility;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: ContextSwitchDialog.kt */
/* JADX INFO: loaded from: classes.dex */
@SourceDebugExtension({"SMAP\nContextSwitchDialog.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ContextSwitchDialog.kt\ncom/facebook/gamingservices/ContextSwitchDialog\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,219:1\n1#2:220\n*E\n"})
public final class ContextSwitchDialog extends FacebookDialogBase<ContextSwitchContent, Result> {

    @NotNull
    public static final Companion Companion = new Companion(null);
    private static final int DEFAULT_REQUEST_CODE = CallbackManagerImpl.RequestCodeOffset.GamingContextSwitch.toRequestCode();

    @Nullable
    private FacebookCallback<Result> callback;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ContextSwitchDialog(@NotNull Activity activity) {
        super(activity, DEFAULT_REQUEST_CODE);
        Intrinsics.checkNotNullParameter(activity, "activity");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ContextSwitchDialog(@NotNull Fragment fragment) {
        this(new FragmentWrapper(fragment));
        Intrinsics.checkNotNullParameter(fragment, "fragment");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ContextSwitchDialog(@NotNull android.app.Fragment fragment) {
        this(new FragmentWrapper(fragment));
        Intrinsics.checkNotNullParameter(fragment, "fragment");
    }

    private ContextSwitchDialog(FragmentWrapper fragmentWrapper) {
        super(fragmentWrapper, DEFAULT_REQUEST_CODE);
    }

    @Override // com.facebook.internal.FacebookDialogBase, com.facebook.FacebookDialog
    public boolean canShow(@NotNull ContextSwitchContent content) {
        Intrinsics.checkNotNullParameter(content, "content");
        return CloudGameLoginHandler.isRunningInCloud() || new FacebookAppHandler().canShow(content, true) || new WebHandler().canShow(content, true);
    }

    @Override // com.facebook.internal.FacebookDialogBase
    public void showImpl(@NotNull ContextSwitchContent content, @NotNull Object mode) {
        Intrinsics.checkNotNullParameter(content, "content");
        Intrinsics.checkNotNullParameter(mode, "mode");
        if (CloudGameLoginHandler.isRunningInCloud()) {
            showForCloud(content);
        } else {
            super.showImpl(content, mode);
        }
    }

    private final void showForCloud(ContextSwitchContent contextSwitchContent) {
        AccessToken currentAccessToken = AccessToken.Companion.getCurrentAccessToken();
        if (currentAccessToken == null || currentAccessToken.isExpired()) {
            throw new FacebookException("Attempted to open ContextSwitchContent with an invalid access token");
        }
        DaemonRequest.Callback callback = new DaemonRequest.Callback() { // from class: com.facebook.gamingservices.ContextSwitchDialog$$ExternalSyntheticLambda0
            @Override // com.facebook.gamingservices.cloudgaming.DaemonRequest.Callback
            public final void onCompleted(GraphResponse graphResponse) {
                ContextSwitchDialog.showForCloud$lambda$2(this.f$0, graphResponse);
            }
        };
        String contextID = contextSwitchContent.getContextID();
        if (contextID == null) {
            FacebookCallback<Result> facebookCallback = this.callback;
            if (facebookCallback != null) {
                facebookCallback.onError(new FacebookException("Required string contextID not provided."));
                return;
            }
            return;
        }
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("id", contextID);
            DaemonRequest.executeAsync(getActivityContext(), jSONObject, callback, SDKMessageEnum.CONTEXT_SWITCH);
        } catch (JSONException unused) {
            FacebookCallback<Result> facebookCallback2 = this.callback;
            if (facebookCallback2 != null) {
                facebookCallback2.onError(new FacebookException("Couldn't prepare Context Switch Dialog"));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showForCloud$lambda$2(ContextSwitchDialog this$0, GraphResponse response) {
        Unit unit;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        FacebookCallback<Result> facebookCallback = this$0.callback;
        if (facebookCallback != null) {
            FacebookRequestError error = response.getError();
            if (error != null) {
                facebookCallback.onError(new FacebookException(error.getErrorMessage()));
                unit = Unit.INSTANCE;
            } else {
                unit = null;
            }
            if (unit == null) {
                Intrinsics.checkNotNullExpressionValue(response, "response");
                facebookCallback.onSuccess(new Result(response));
            }
        }
    }

    @Override // com.facebook.internal.FacebookDialogBase
    public void registerCallbackImpl(@NotNull CallbackManagerImpl callbackManager, @NotNull final FacebookCallback<Result> callback) {
        Intrinsics.checkNotNullParameter(callbackManager, "callbackManager");
        Intrinsics.checkNotNullParameter(callback, "callback");
        this.callback = callback;
        final ResultProcessor resultProcessor = new ResultProcessor(callback) { // from class: com.facebook.gamingservices.ContextSwitchDialog$registerCallbackImpl$resultProcessor$1
            final /* synthetic */ FacebookCallback<ContextSwitchDialog.Result> $callback;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(callback);
                this.$callback = callback;
            }

            @Override // com.facebook.share.internal.ResultProcessor
            public void onSuccess(@NotNull AppCall appCall, @Nullable Bundle bundle) {
                Intrinsics.checkNotNullParameter(appCall, "appCall");
                if (bundle != null) {
                    if (bundle.getString("error_message") != null) {
                        this.$callback.onError(new FacebookException(bundle.getString("error_message")));
                        return;
                    }
                    String string = bundle.getString("id");
                    String string2 = bundle.getString(SDKConstants.PARAM_CONTEXT_CONTEXT_ID);
                    if (string != null) {
                        GamingContext.Companion.setCurrentGamingContext(new GamingContext(string));
                        this.$callback.onSuccess(new ContextSwitchDialog.Result(string));
                    } else if (string2 != null) {
                        GamingContext.Companion.setCurrentGamingContext(new GamingContext(string2));
                        this.$callback.onSuccess(new ContextSwitchDialog.Result(string2));
                    }
                    this.$callback.onError(new FacebookException(bundle.getString("Invalid response received from server.")));
                    return;
                }
                onCancel(appCall);
            }
        };
        callbackManager.registerCallback(getRequestCode(), new CallbackManagerImpl.Callback() { // from class: com.facebook.gamingservices.ContextSwitchDialog$$ExternalSyntheticLambda1
            @Override // com.facebook.internal.CallbackManagerImpl.Callback
            public final boolean onActivityResult(int i, Intent intent) {
                return ContextSwitchDialog.registerCallbackImpl$lambda$3(this.f$0, resultProcessor, i, intent);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean registerCallbackImpl$lambda$3(ContextSwitchDialog this$0, ResultProcessor resultProcessor, int i, Intent intent) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(resultProcessor, "$resultProcessor");
        return ShareInternalUtility.handleActivityResult(this$0.getRequestCode(), i, intent, resultProcessor);
    }

    @Override // com.facebook.internal.FacebookDialogBase
    @NotNull
    public List<FacebookDialogBase<ContextSwitchContent, Result>.ModeHandler> getOrderedModeHandlers() {
        return CollectionsKt__CollectionsKt.listOf((Object[]) new FacebookDialogBase.ModeHandler[]{new FacebookAppHandler(), new WebHandler()});
    }

    @Override // com.facebook.internal.FacebookDialogBase
    @NotNull
    public AppCall createBaseAppCall() {
        return new AppCall(getRequestCode(), null, 2, null);
    }

    /* JADX INFO: compiled from: ContextSwitchDialog.kt */
    @SourceDebugExtension({"SMAP\nContextSwitchDialog.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ContextSwitchDialog.kt\ncom/facebook/gamingservices/ContextSwitchDialog$Result\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,219:1\n1#2:220\n*E\n"})
    public static final class Result {

        @Nullable
        private String contextID;

        @Nullable
        public final String getContextID() {
            return this.contextID;
        }

        public final void setContextID(@Nullable String str) {
            this.contextID = str;
        }

        public Result(@NotNull String contextID) {
            Intrinsics.checkNotNullParameter(contextID, "contextID");
            this.contextID = contextID;
        }

        public Result(@NotNull GraphResponse response) {
            JSONObject jSONObjectOptJSONObject;
            Intrinsics.checkNotNullParameter(response, "response");
            try {
                JSONObject jSONObject = response.getJSONObject();
                if (jSONObject == null || (jSONObjectOptJSONObject = jSONObject.optJSONObject("data")) == null) {
                    return;
                }
                Intrinsics.checkNotNullExpressionValue(jSONObjectOptJSONObject, "optJSONObject(\"data\")");
                this.contextID = jSONObjectOptJSONObject.getString("id");
            } catch (JSONException unused) {
                this.contextID = null;
            }
        }
    }

    /* JADX INFO: compiled from: ContextSwitchDialog.kt */
    public final class WebHandler extends FacebookDialogBase<ContextSwitchContent, Result>.ModeHandler {
        @Override // com.facebook.internal.FacebookDialogBase.ModeHandler
        public boolean canShow(@NotNull ContextSwitchContent content, boolean z) {
            Intrinsics.checkNotNullParameter(content, "content");
            return true;
        }

        public WebHandler() {
            super();
        }

        @Override // com.facebook.internal.FacebookDialogBase.ModeHandler
        @NotNull
        public AppCall createAppCall(@NotNull ContextSwitchContent content) {
            Intrinsics.checkNotNullParameter(content, "content");
            AppCall appCallCreateBaseAppCall = ContextSwitchDialog.this.createBaseAppCall();
            Bundle bundle = new Bundle();
            bundle.putString(SDKConstants.PARAM_CONTEXT_CONTEXT_ID, content.getContextID());
            AccessToken currentAccessToken = AccessToken.Companion.getCurrentAccessToken();
            if (currentAccessToken != null) {
                bundle.putString("dialog_access_token", currentAccessToken.getToken());
            }
            DialogPresenter.setupAppCallForWebDialog(appCallCreateBaseAppCall, "context", bundle);
            return appCallCreateBaseAppCall;
        }
    }

    /* JADX INFO: compiled from: ContextSwitchDialog.kt */
    @SourceDebugExtension({"SMAP\nContextSwitchDialog.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ContextSwitchDialog.kt\ncom/facebook/gamingservices/ContextSwitchDialog$FacebookAppHandler\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,219:1\n1#2:220\n*E\n"})
    public final class FacebookAppHandler extends FacebookDialogBase<ContextSwitchContent, Result>.ModeHandler {
        public FacebookAppHandler() {
            super();
        }

        @Override // com.facebook.internal.FacebookDialogBase.ModeHandler
        public boolean canShow(@NotNull ContextSwitchContent content, boolean z) {
            Intrinsics.checkNotNullParameter(content, "content");
            Activity activityContext = ContextSwitchDialog.this.getActivityContext();
            PackageManager packageManager = activityContext != null ? activityContext.getPackageManager() : null;
            Intent intent = new Intent("com.facebook.games.gaming_services.DEEPLINK");
            intent.setType("text/plain");
            boolean z2 = (packageManager != null ? intent.resolveActivity(packageManager) : null) != null;
            AccessToken currentAccessToken = AccessToken.Companion.getCurrentAccessToken();
            return z2 && ((currentAccessToken != null ? currentAccessToken.getGraphDomain() : null) != null && Intrinsics.areEqual(FacebookSdk.GAMING, currentAccessToken.getGraphDomain()));
        }

        @Override // com.facebook.internal.FacebookDialogBase.ModeHandler
        @NotNull
        public AppCall createAppCall(@NotNull ContextSwitchContent content) {
            Intrinsics.checkNotNullParameter(content, "content");
            AppCall appCallCreateBaseAppCall = ContextSwitchDialog.this.createBaseAppCall();
            Intent intent = new Intent("com.facebook.games.gaming_services.DEEPLINK");
            intent.setType("text/plain");
            AccessToken currentAccessToken = AccessToken.Companion.getCurrentAccessToken();
            Bundle bundle = new Bundle();
            bundle.putString(SDKConstants.PARAM_TOURNAMENTS_DEEPLINK, "CONTEXT_SWITCH");
            if (currentAccessToken != null) {
                bundle.putString("game_id", currentAccessToken.getApplicationId());
            } else {
                bundle.putString("game_id", FacebookSdk.getApplicationId());
            }
            if (content.getContextID() != null) {
                bundle.putString("context_token_id", content.getContextID());
            }
            NativeProtocol.setupProtocolRequestIntent(intent, appCallCreateBaseAppCall.getCallId().toString(), "", NativeProtocol.getLatestKnownVersion(), bundle);
            appCallCreateBaseAppCall.setRequestIntent(intent);
            return appCallCreateBaseAppCall;
        }
    }

    /* JADX INFO: compiled from: ContextSwitchDialog.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}

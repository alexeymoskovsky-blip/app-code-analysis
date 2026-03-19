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
import com.facebook.gamingservices.ContextChooseDialog;
import com.facebook.gamingservices.cloudgaming.CloudGameLoginHandler;
import com.facebook.gamingservices.cloudgaming.DaemonRequest;
import com.facebook.gamingservices.cloudgaming.internal.SDKConstants;
import com.facebook.gamingservices.cloudgaming.internal.SDKMessageEnum;
import com.facebook.gamingservices.model.ContextChooseContent;
import com.facebook.internal.AppCall;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.internal.CustomTabUtils;
import com.facebook.internal.DialogPresenter;
import com.facebook.internal.FacebookDialogBase;
import com.facebook.internal.FragmentWrapper;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.ServerProtocol;
import com.facebook.share.internal.ResultProcessor;
import com.facebook.share.internal.ShareInternalUtility;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: ContextChooseDialog.kt */
/* JADX INFO: loaded from: classes.dex */
@SourceDebugExtension({"SMAP\nContextChooseDialog.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ContextChooseDialog.kt\ncom/facebook/gamingservices/ContextChooseDialog\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,251:1\n1#2:252\n*E\n"})
public final class ContextChooseDialog extends FacebookDialogBase<ContextChooseContent, Result> {

    @NotNull
    private static final String CONTEXT_CHOOSE_DIALOG = "context_choose";

    @NotNull
    public static final Companion Companion = new Companion(null);
    private static final int DEFAULT_REQUEST_CODE = CallbackManagerImpl.RequestCodeOffset.GamingContextChoose.toRequestCode();

    @Nullable
    private FacebookCallback<Result> callback;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ContextChooseDialog(@NotNull Activity activity) {
        super(activity, DEFAULT_REQUEST_CODE);
        Intrinsics.checkNotNullParameter(activity, "activity");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ContextChooseDialog(@NotNull Fragment fragment) {
        this(new FragmentWrapper(fragment));
        Intrinsics.checkNotNullParameter(fragment, "fragment");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ContextChooseDialog(@NotNull android.app.Fragment fragment) {
        this(new FragmentWrapper(fragment));
        Intrinsics.checkNotNullParameter(fragment, "fragment");
    }

    private ContextChooseDialog(FragmentWrapper fragmentWrapper) {
        super(fragmentWrapper, DEFAULT_REQUEST_CODE);
    }

    @Override // com.facebook.internal.FacebookDialogBase, com.facebook.FacebookDialog
    public boolean canShow(@NotNull ContextChooseContent content) {
        Intrinsics.checkNotNullParameter(content, "content");
        return CloudGameLoginHandler.isRunningInCloud() || new FacebookAppHandler().canShow(content, true) || new ChromeCustomTabHandler().canShow(content, true);
    }

    @Override // com.facebook.internal.FacebookDialogBase
    public void showImpl(@NotNull ContextChooseContent content, @NotNull Object mode) {
        Intrinsics.checkNotNullParameter(content, "content");
        Intrinsics.checkNotNullParameter(mode, "mode");
        if (CloudGameLoginHandler.isRunningInCloud()) {
            showForCloud(content);
        } else {
            super.showImpl(content, mode);
        }
    }

    private final void showForCloud(ContextChooseContent contextChooseContent) {
        AccessToken currentAccessToken = AccessToken.Companion.getCurrentAccessToken();
        if (currentAccessToken == null || currentAccessToken.isExpired()) {
            throw new FacebookException("Attempted to open ContextChooseContent with an invalid access token");
        }
        DaemonRequest.Callback callback = new DaemonRequest.Callback() { // from class: com.facebook.gamingservices.ContextChooseDialog$$ExternalSyntheticLambda1
            @Override // com.facebook.gamingservices.cloudgaming.DaemonRequest.Callback
            public final void onCompleted(GraphResponse graphResponse) {
                ContextChooseDialog.showForCloud$lambda$2(this.f$0, graphResponse);
            }
        };
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("filters", contextChooseContent.getFilters());
            jSONObject.put(SDKConstants.PARAM_CONTEXT_MIN_SIZE, contextChooseContent.getMinSize());
            List<String> filters = contextChooseContent.getFilters();
            if (filters != null) {
                JSONArray jSONArray = new JSONArray();
                Iterator<String> it = filters.iterator();
                while (it.hasNext()) {
                    jSONArray.put(it.next());
                }
                jSONObject.put("filters", jSONArray);
            }
            DaemonRequest.executeAsync(getActivityContext(), jSONObject, callback, SDKMessageEnum.CONTEXT_CHOOSE);
        } catch (JSONException unused) {
            FacebookCallback<Result> facebookCallback = this.callback;
            if (facebookCallback != null) {
                facebookCallback.onError(new FacebookException("Couldn't prepare Context Choose Dialog"));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showForCloud$lambda$2(ContextChooseDialog this$0, GraphResponse response) {
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

    /* JADX WARN: Type inference failed for: r0v2, types: [com.facebook.gamingservices.ContextChooseDialog$registerCallbackImpl$resultProcessor$1] */
    @Override // com.facebook.internal.FacebookDialogBase
    public void registerCallbackImpl(@NotNull CallbackManagerImpl callbackManager, @NotNull final FacebookCallback<Result> callback) {
        Intrinsics.checkNotNullParameter(callbackManager, "callbackManager");
        Intrinsics.checkNotNullParameter(callback, "callback");
        this.callback = callback;
        final ?? r0 = new ResultProcessor(callback) { // from class: com.facebook.gamingservices.ContextChooseDialog$registerCallbackImpl$resultProcessor$1
            final /* synthetic */ FacebookCallback<ContextChooseDialog.Result> $callback;

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
                    if (string != null) {
                        GamingContext.Companion.setCurrentGamingContext(new GamingContext(string));
                        this.$callback.onSuccess(new ContextChooseDialog.Result(bundle));
                    }
                    this.$callback.onError(new FacebookException(bundle.getString("Invalid response received from server.")));
                    return;
                }
                onCancel(appCall);
            }
        };
        callbackManager.registerCallback(getRequestCode(), new CallbackManagerImpl.Callback() { // from class: com.facebook.gamingservices.ContextChooseDialog$$ExternalSyntheticLambda0
            @Override // com.facebook.internal.CallbackManagerImpl.Callback
            public final boolean onActivityResult(int i, Intent intent) {
                return ContextChooseDialog.registerCallbackImpl$lambda$4(this.f$0, r0, i, intent);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean registerCallbackImpl$lambda$4(ContextChooseDialog this$0, ContextChooseDialog$registerCallbackImpl$resultProcessor$1 resultProcessor, int i, Intent intent) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(resultProcessor, "$resultProcessor");
        return ShareInternalUtility.handleActivityResult(this$0.getRequestCode(), i, intent, resultProcessor);
    }

    @Override // com.facebook.internal.FacebookDialogBase
    @NotNull
    public List<FacebookDialogBase<ContextChooseContent, Result>.ModeHandler> getOrderedModeHandlers() {
        return CollectionsKt__CollectionsKt.listOf((Object[]) new FacebookDialogBase.ModeHandler[]{new FacebookAppHandler(), new ChromeCustomTabHandler()});
    }

    @Override // com.facebook.internal.FacebookDialogBase
    @NotNull
    public AppCall createBaseAppCall() {
        return new AppCall(getRequestCode(), null, 2, 0 == true ? 1 : 0);
    }

    /* JADX INFO: compiled from: ContextChooseDialog.kt */
    @SourceDebugExtension({"SMAP\nContextChooseDialog.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ContextChooseDialog.kt\ncom/facebook/gamingservices/ContextChooseDialog$Result\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,251:1\n1#2:252\n*E\n"})
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

        public Result(@NotNull Bundle results) {
            Intrinsics.checkNotNullParameter(results, "results");
            this.contextID = results.getString("id");
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

    /* JADX INFO: compiled from: ContextChooseDialog.kt */
    @SourceDebugExtension({"SMAP\nContextChooseDialog.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ContextChooseDialog.kt\ncom/facebook/gamingservices/ContextChooseDialog$FacebookAppHandler\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,251:1\n1#2:252\n*E\n"})
    public final class FacebookAppHandler extends FacebookDialogBase<ContextChooseContent, Result>.ModeHandler {
        public FacebookAppHandler() {
            super();
        }

        @Override // com.facebook.internal.FacebookDialogBase.ModeHandler
        public boolean canShow(@NotNull ContextChooseContent content, boolean z) {
            Intrinsics.checkNotNullParameter(content, "content");
            Activity activityContext = ContextChooseDialog.this.getActivityContext();
            PackageManager packageManager = activityContext != null ? activityContext.getPackageManager() : null;
            Intent intent = new Intent("com.facebook.games.gaming_services.DEEPLINK");
            intent.setType("text/plain");
            boolean z2 = (packageManager != null ? intent.resolveActivity(packageManager) : null) != null;
            AccessToken currentAccessToken = AccessToken.Companion.getCurrentAccessToken();
            return z2 && ((currentAccessToken != null ? currentAccessToken.getGraphDomain() : null) != null && Intrinsics.areEqual(FacebookSdk.GAMING, currentAccessToken.getGraphDomain()));
        }

        @Override // com.facebook.internal.FacebookDialogBase.ModeHandler
        @NotNull
        public AppCall createAppCall(@NotNull ContextChooseContent content) {
            Intrinsics.checkNotNullParameter(content, "content");
            AppCall appCallCreateBaseAppCall = ContextChooseDialog.this.createBaseAppCall();
            Intent intent = new Intent("com.facebook.games.gaming_services.DEEPLINK");
            intent.setType("text/plain");
            AccessToken currentAccessToken = AccessToken.Companion.getCurrentAccessToken();
            Bundle bundle = new Bundle();
            bundle.putString(SDKConstants.PARAM_TOURNAMENTS_DEEPLINK, "CONTEXT_CHOOSE");
            if (currentAccessToken != null) {
                bundle.putString("game_id", currentAccessToken.getApplicationId());
            } else {
                bundle.putString("game_id", FacebookSdk.getApplicationId());
            }
            if (content.getMinSize() != null) {
                bundle.putString("min_thread_size", content.getMinSize().toString());
            }
            if (content.getMaxSize() != null) {
                bundle.putString("max_thread_size", content.getMaxSize().toString());
            }
            if (content.getFilters() != null) {
                bundle.putString("filters", new JSONArray((Collection) content.getFilters()).toString());
            }
            NativeProtocol.setupProtocolRequestIntent(intent, appCallCreateBaseAppCall.getCallId().toString(), "", NativeProtocol.getLatestKnownVersion(), bundle);
            appCallCreateBaseAppCall.setRequestIntent(intent);
            return appCallCreateBaseAppCall;
        }
    }

    /* JADX INFO: compiled from: ContextChooseDialog.kt */
    public final class ChromeCustomTabHandler extends FacebookDialogBase<ContextChooseContent, Result>.ModeHandler {
        public ChromeCustomTabHandler() {
            super();
        }

        @Override // com.facebook.internal.FacebookDialogBase.ModeHandler
        public boolean canShow(@NotNull ContextChooseContent content, boolean z) {
            Intrinsics.checkNotNullParameter(content, "content");
            return CustomTabUtils.getChromePackage() != null;
        }

        @Override // com.facebook.internal.FacebookDialogBase.ModeHandler
        @NotNull
        public AppCall createAppCall(@NotNull ContextChooseContent content) {
            String applicationId;
            Intrinsics.checkNotNullParameter(content, "content");
            AppCall appCallCreateBaseAppCall = ContextChooseDialog.this.createBaseAppCall();
            AccessToken currentAccessToken = AccessToken.Companion.getCurrentAccessToken();
            Bundle bundle = new Bundle();
            Bundle bundle2 = new Bundle();
            Bundle bundle3 = new Bundle();
            if (currentAccessToken == null || (applicationId = currentAccessToken.getApplicationId()) == null) {
                applicationId = FacebookSdk.getApplicationId();
            }
            bundle.putString("app_id", applicationId);
            if (content.getMinSize() != null) {
                bundle3.putString("min_size", content.getMinSize().toString());
            }
            if (content.getMaxSize() != null) {
                bundle3.putString("max_size", content.getMaxSize().toString());
            }
            if (content.getFilters() != null) {
                bundle3.putString("filters", new JSONArray((Collection) content.getFilters()).toString());
            }
            bundle2.putString("filters", bundle3.toString());
            bundle.putString("payload", bundle2.toString());
            bundle.putString(ServerProtocol.DIALOG_PARAM_REDIRECT_URI, CustomTabUtils.getDefaultRedirectURI());
            DialogPresenter.setupAppCallForCustomTabDialog(appCallCreateBaseAppCall, ContextChooseDialog.CONTEXT_CHOOSE_DIALOG, bundle);
            return appCallCreateBaseAppCall;
        }
    }

    /* JADX INFO: compiled from: ContextChooseDialog.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}

package com.facebook.gamingservices;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.gamingservices.TournamentJoinDialog;
import com.facebook.gamingservices.cloudgaming.CloudGameLoginHandler;
import com.facebook.gamingservices.cloudgaming.internal.SDKConstants;
import com.facebook.gamingservices.internal.TournamentJoinDialogURIBuilder;
import com.facebook.internal.AppCall;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.internal.CustomTabUtils;
import com.facebook.internal.DialogPresenter;
import com.facebook.internal.FacebookDialogBase;
import com.facebook.internal.FragmentWrapper;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.ServerProtocol;
import com.facebook.internal.instrument.crashshield.AutoHandleExceptions;
import com.facebook.share.internal.ResultProcessor;
import com.facebook.share.internal.ShareInternalUtility;
import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: TournamentJoinDialog.kt */
/* JADX INFO: loaded from: classes.dex */
@AutoHandleExceptions
public final class TournamentJoinDialog extends FacebookDialogBase<String, Result> {

    @NotNull
    public static final Companion Companion = new Companion(null);
    private static final int DEFAULT_REQUEST_CODE = CallbackManagerImpl.RequestCodeOffset.TournamentJoinDialog.toRequestCode();

    @NotNull
    private static final String JOIN_TOURNAMENT_ACCESS_TOKEN_KEY = "access_token";

    @NotNull
    private static final String JOIN_TOURNAMENT_ACTION = "com.facebook.games.gaming_services.DEEPLINK";

    @NotNull
    private static final String JOIN_TOURNAMENT_CONTENT_TYPE = "text/plain";

    @NotNull
    private static final String JOIN_TOURNAMENT_DIALOG = "join_tournament";

    @NotNull
    private static final String JOIN_TOURNAMENT_ERROR_MESSAGE_KEY = "error_message";

    @Nullable
    private String payload;

    @Nullable
    private Number requestID;

    @Nullable
    private String tournamentID;

    /* JADX INFO: compiled from: TournamentJoinDialog.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TournamentJoinDialog(@NotNull Activity activity) {
        super(activity, DEFAULT_REQUEST_CODE);
        Intrinsics.checkNotNullParameter(activity, "activity");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public TournamentJoinDialog(@NotNull Fragment fragment) {
        this(new FragmentWrapper(fragment));
        Intrinsics.checkNotNullParameter(fragment, "fragment");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public TournamentJoinDialog(@NotNull android.app.Fragment fragment) {
        this(new FragmentWrapper(fragment));
        Intrinsics.checkNotNullParameter(fragment, "fragment");
    }

    private TournamentJoinDialog(FragmentWrapper fragmentWrapper) {
        super(fragmentWrapper, DEFAULT_REQUEST_CODE);
    }

    public final void show(@Nullable String str, @Nullable String str2) {
        this.tournamentID = str;
        this.payload = str2;
        super.showImpl(str, FacebookDialogBase.BASE_AUTOMATIC_MODE);
    }

    @Override // com.facebook.internal.FacebookDialogBase
    public void showImpl(@Nullable String str, @NotNull Object mode) {
        Intrinsics.checkNotNullParameter(mode, "mode");
        if (CloudGameLoginHandler.isRunningInCloud()) {
            return;
        }
        super.showImpl(str, mode);
    }

    @Override // com.facebook.internal.FacebookDialogBase
    public void registerCallbackImpl(@NotNull CallbackManagerImpl callbackManager, @NotNull final FacebookCallback<Result> callback) {
        Intrinsics.checkNotNullParameter(callbackManager, "callbackManager");
        Intrinsics.checkNotNullParameter(callback, "callback");
        final ResultProcessor resultProcessor = new ResultProcessor(callback) { // from class: com.facebook.gamingservices.TournamentJoinDialog$registerCallbackImpl$resultProcessor$1
            final /* synthetic */ FacebookCallback<TournamentJoinDialog.Result> $callback;

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
                    } else if (bundle.getString("payload") != null) {
                        this.$callback.onSuccess(new TournamentJoinDialog.Result(bundle));
                        return;
                    }
                }
                onCancel(appCall);
            }
        };
        callbackManager.registerCallback(getRequestCode(), new CallbackManagerImpl.Callback() { // from class: com.facebook.gamingservices.TournamentJoinDialog$$ExternalSyntheticLambda0
            @Override // com.facebook.internal.CallbackManagerImpl.Callback
            public final boolean onActivityResult(int i, Intent intent) {
                return TournamentJoinDialog.registerCallbackImpl$lambda$0(this.f$0, resultProcessor, i, intent);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean registerCallbackImpl$lambda$0(TournamentJoinDialog this$0, ResultProcessor resultProcessor, int i, Intent intent) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(resultProcessor, "$resultProcessor");
        return ShareInternalUtility.handleActivityResult(this$0.getRequestCode(), i, intent, resultProcessor);
    }

    @Override // com.facebook.internal.FacebookDialogBase
    @NotNull
    public List<FacebookDialogBase<String, Result>.ModeHandler> getOrderedModeHandlers() {
        return CollectionsKt__CollectionsKt.listOf((Object[]) new FacebookDialogBase.ModeHandler[]{new FacebookAppHandler(), new ChromeCustomTabHandler()});
    }

    @Override // com.facebook.internal.FacebookDialogBase
    @NotNull
    public AppCall createBaseAppCall() {
        return new AppCall(getRequestCode(), null, 2, null);
    }

    /* JADX INFO: compiled from: TournamentJoinDialog.kt */
    public static final class Result {

        @Nullable
        private String payload;

        @Nullable
        private String requestID;

        @Nullable
        private String tournamentID;

        public Result(@NotNull Bundle results) {
            Intrinsics.checkNotNullParameter(results, "results");
            if (results.getString("request") != null) {
                this.requestID = results.getString("request");
            }
            this.tournamentID = results.getString(SDKConstants.PARAM_TOURNAMENTS_ID);
            this.payload = results.getString("payload");
        }

        @Nullable
        public final String getRequestID() {
            return this.requestID;
        }

        public final void setRequestID(@Nullable String str) {
            this.requestID = str;
        }

        @Nullable
        public final String getTournamentID() {
            return this.tournamentID;
        }

        public final void setTournamentID(@Nullable String str) {
            this.tournamentID = str;
        }

        @Nullable
        public final String getPayload() {
            return this.payload;
        }

        public final void setPayload(@Nullable String str) {
            this.payload = str;
        }
    }

    @Override // com.facebook.internal.FacebookDialogBase, com.facebook.FacebookDialog
    public boolean canShow(@Nullable String str) {
        if (CloudGameLoginHandler.isRunningInCloud()) {
            return false;
        }
        if (new FacebookAppHandler().canShow(str, true)) {
            return true;
        }
        return new ChromeCustomTabHandler().canShow(str, true);
    }

    /* JADX INFO: compiled from: TournamentJoinDialog.kt */
    public final class FacebookAppHandler extends FacebookDialogBase<String, Result>.ModeHandler {
        public FacebookAppHandler() {
            super();
        }

        @Override // com.facebook.internal.FacebookDialogBase.ModeHandler
        public boolean canShow(@Nullable String str, boolean z) {
            PackageManager packageManager = FacebookSdk.getApplicationContext().getPackageManager();
            Intrinsics.checkNotNullExpressionValue(packageManager, "getApplicationContext().packageManager");
            Intent intent = new Intent(TournamentJoinDialog.JOIN_TOURNAMENT_ACTION);
            intent.setType("text/plain");
            return intent.resolveActivity(packageManager) != null;
        }

        @Override // com.facebook.internal.FacebookDialogBase.ModeHandler
        @NotNull
        public AppCall createAppCall(@Nullable String str) {
            AccessToken currentAccessToken = AccessToken.Companion.getCurrentAccessToken();
            AppCall appCallCreateBaseAppCall = TournamentJoinDialog.this.createBaseAppCall();
            Intent intent = new Intent(TournamentJoinDialog.JOIN_TOURNAMENT_ACTION);
            intent.setType("text/plain");
            if (currentAccessToken == null || currentAccessToken.isExpired()) {
                throw new FacebookException("Attempted to present TournamentJoinDialog with an invalid access token");
            }
            if (currentAccessToken.getGraphDomain() != null && !Intrinsics.areEqual(FacebookSdk.GAMING, currentAccessToken.getGraphDomain())) {
                throw new FacebookException("Attempted to present TournamentJoinDialog while user is not gaming logged in");
            }
            NativeProtocol.setupProtocolRequestIntent(intent, appCallCreateBaseAppCall.getCallId().toString(), "", NativeProtocol.PROTOCOL_VERSION_20210906, TournamentJoinDialogURIBuilder.INSTANCE.bundle$facebook_gamingservices_release(currentAccessToken.getApplicationId(), TournamentJoinDialog.this.tournamentID, TournamentJoinDialog.this.payload));
            appCallCreateBaseAppCall.setRequestIntent(intent);
            return appCallCreateBaseAppCall;
        }
    }

    /* JADX INFO: compiled from: TournamentJoinDialog.kt */
    public final class ChromeCustomTabHandler extends FacebookDialogBase<String, Result>.ModeHandler {
        public ChromeCustomTabHandler() {
            super();
        }

        @Override // com.facebook.internal.FacebookDialogBase.ModeHandler
        public boolean canShow(@Nullable String str, boolean z) {
            return CustomTabUtils.getChromePackage() != null;
        }

        @Override // com.facebook.internal.FacebookDialogBase.ModeHandler
        @NotNull
        public AppCall createAppCall(@Nullable String str) {
            String applicationId;
            AppCall appCallCreateBaseAppCall = TournamentJoinDialog.this.createBaseAppCall();
            AccessToken currentAccessToken = AccessToken.Companion.getCurrentAccessToken();
            Bundle bundle = new Bundle();
            Bundle bundle2 = new Bundle();
            if (currentAccessToken == null || (applicationId = currentAccessToken.getApplicationId()) == null) {
                applicationId = FacebookSdk.getApplicationId();
            }
            bundle.putString("app_id", applicationId);
            bundle.putString("payload", bundle2.toString());
            if (currentAccessToken != null) {
                currentAccessToken.getToken();
            }
            bundle.putString("access_token", currentAccessToken != null ? currentAccessToken.getToken() : null);
            bundle.putString(ServerProtocol.DIALOG_PARAM_REDIRECT_URI, CustomTabUtils.getDefaultRedirectURI());
            DialogPresenter.setupAppCallForCustomTabDialog(appCallCreateBaseAppCall, TournamentJoinDialog.JOIN_TOURNAMENT_DIALOG, bundle);
            return appCallCreateBaseAppCall;
        }
    }
}

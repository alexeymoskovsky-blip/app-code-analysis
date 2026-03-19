package com.petkit.android.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.google.gson.GsonBuilder;
import com.jess.arms.base.delegate.AppLifecycles;
import com.jess.arms.di.module.AppModule;
import com.jess.arms.di.module.ClientModule;
import com.jess.arms.di.module.GlobalConfigModule;
import com.jess.arms.http.BaseUrl;
import com.jess.arms.http.GlobalHttpHandler;
import com.jess.arms.integration.ConfigModule;
import com.jess.arms.utils.Consts;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.widget.PetkitToast;
import com.orm.SugarRecord;
import com.petkit.android.activities.common.ReLoginDialogActivity;
import com.petkit.android.api.http.ApiTools;
import com.petkit.android.api.http.PetkitLogoutThrowable;
import com.petkit.android.api.http.PetkitOkHttpSSLContext;
import com.petkit.android.app.utils.NetworkUtils;
import com.petkit.android.utils.CommonUtils;
import com.petkit.android.utils.UserInforUtils;
import com.petkit.oversea.R;
import io.rx_cache2.internal.RxCache;
import java.util.Collections;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import me.jessyan.progressmanager.ProgressManager;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import me.jessyan.rxerrorhandler.handler.listener.ResponseErrorListener;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import timber.log.Timber;

/* JADX INFO: loaded from: classes6.dex */
public class GlobalConfiguration implements ConfigModule {
    public static /* synthetic */ void lambda$applyOptions$2(Context context, Retrofit.Builder builder) {
    }

    @Override // com.jess.arms.integration.ConfigModule
    public void applyOptions(final Context context, GlobalConfigModule.Builder builder) {
        final SugarExclusionStrategy sugarExclusionStrategy = new SugarExclusionStrategy(SugarRecord.class);
        builder.baseurl(new BaseUrl() { // from class: com.petkit.android.app.GlobalConfiguration.2
            @Override // com.jess.arms.http.BaseUrl
            public HttpUrl url() {
                return HttpUrl.parse(ApiTools.getApiHTTPUri());
            }
        }).globalHttpHandler(new GlobalHttpHandler() { // from class: com.petkit.android.app.GlobalConfiguration.1
            @Override // com.jess.arms.http.GlobalHttpHandler
            public Response onHttpResultResponse(String str, Interceptor.Chain chain, Response response) {
                return response;
            }

            @Override // com.jess.arms.http.GlobalHttpHandler
            public Request onHttpRequestBefore(Interceptor.Chain chain, Request request) {
                Request.Builder builderNewBuilder = chain.request().newBuilder();
                builderNewBuilder.addHeader(Consts.HTTP_HEADER_API_VERSION, CommonUtils.getAppVersionName(context)).addHeader(Consts.HTTP_HEADER_LOCATE_KEY, UserInforUtils.getLanguageLocaleString(context)).addHeader(Consts.HTTP_HEADER_TIMEZONE_KEY, String.valueOf(TimeZone.getDefault().getRawOffset() / 3600000.0f)).addHeader(Consts.HTTP_HEADER_TIMEZONE_ID_KEY, TimeZone.getDefault().getID()).addHeader(Consts.HTTP_HEADER_IMAGE, "1").addHeader(Consts.HTTP_HEADER_CLIENT, CommonUtils.getHttpHeaderClientInfo());
                if (!CommonUtils.isEmpty(DataHelper.getStringSF(context, Consts.SHARED_SESSION_ID))) {
                    builderNewBuilder.addHeader(Consts.HTTP_HEADER_SESSION, DataHelper.getStringSF(context, Consts.SHARED_SESSION_ID));
                }
                return builderNewBuilder.build();
            }
        }).responseErrorListener(new ResponseErrorListener() { // from class: com.petkit.android.app.GlobalConfiguration$$ExternalSyntheticLambda0
            @Override // me.jessyan.rxerrorhandler.handler.listener.ResponseErrorListener
            public final void handleResponseError(Context context2, Throwable th) {
                GlobalConfiguration.lambda$applyOptions$0(context2, th);
            }
        }).gsonConfiguration(new AppModule.GsonConfiguration() { // from class: com.petkit.android.app.GlobalConfiguration$$ExternalSyntheticLambda1
            @Override // com.jess.arms.di.module.AppModule.GsonConfiguration
            public final void configGson(Context context2, GsonBuilder gsonBuilder) {
                GlobalConfiguration.lambda$applyOptions$1(sugarExclusionStrategy, context2, gsonBuilder);
            }
        }).retrofitConfiguration(new ClientModule.RetrofitConfiguration() { // from class: com.petkit.android.app.GlobalConfiguration$$ExternalSyntheticLambda2
            @Override // com.jess.arms.di.module.ClientModule.RetrofitConfiguration
            public final void configRetrofit(Context context2, Retrofit.Builder builder2) {
                GlobalConfiguration.lambda$applyOptions$2(context2, builder2);
            }
        }).okhttpConfiguration(new ClientModule.OkhttpConfiguration() { // from class: com.petkit.android.app.GlobalConfiguration$$ExternalSyntheticLambda3
            @Override // com.jess.arms.di.module.ClientModule.OkhttpConfiguration
            public final void configOkhttp(Context context2, OkHttpClient.Builder builder2) {
                GlobalConfiguration.lambda$applyOptions$3(context2, builder2);
            }
        }).rxCacheConfiguration(new ClientModule.RxCacheConfiguration() { // from class: com.petkit.android.app.GlobalConfiguration$$ExternalSyntheticLambda4
            @Override // com.jess.arms.di.module.ClientModule.RxCacheConfiguration
            public final RxCache configRxCache(Context context2, RxCache.Builder builder2) {
                return GlobalConfiguration.lambda$applyOptions$4(context2, builder2);
            }
        });
    }

    public static /* synthetic */ void lambda$applyOptions$0(Context context, Throwable th) {
        if (th instanceof PetkitLogoutThrowable) {
            if (ReLoginDialogActivity.isDialogNeedShowing()) {
                return;
            }
            Intent intent = new Intent(context, (Class<?>) ReLoginDialogActivity.class);
            intent.putExtra("Error_message", th.getMessage());
            intent.setFlags(268435456);
            context.startActivity(intent);
            return;
        }
        Timber.tag("Catch-Error").w(th.getMessage(), new Object[0]);
        PetkitToast.showShortToast(context, NetworkUtils.convertHttpExceptionToString(th));
    }

    public static /* synthetic */ void lambda$applyOptions$1(SugarExclusionStrategy sugarExclusionStrategy, Context context, GsonBuilder gsonBuilder) {
        gsonBuilder.serializeNulls().enableComplexMapKeySerialization().addDeserializationExclusionStrategy(sugarExclusionStrategy).addSerializationExclusionStrategy(sugarExclusionStrategy);
    }

    public static /* synthetic */ void lambda$applyOptions$3(Context context, OkHttpClient.Builder builder) {
        builder.writeTimeout(10L, TimeUnit.SECONDS);
        ProgressManager.getInstance().with(builder);
        RetrofitUrlManager.getInstance().with(builder);
        builder.protocols(Collections.singletonList(Protocol.HTTP_1_1));
        X509TrustManager x509TrustManager = (X509TrustManager) PetkitOkHttpSSLContext.getTrustManagers()[0];
        SSLSocketFactory sSLSocketFactoryCreateSSLSocketFactory = PetkitOkHttpSSLContext.createSSLSocketFactory();
        if (sSLSocketFactoryCreateSSLSocketFactory != null) {
            builder.sslSocketFactory(sSLSocketFactoryCreateSSLSocketFactory, x509TrustManager).hostnameVerifier(new PetkitOkHttpSSLContext.TrustAllHostnameVerifier());
        }
    }

    public static /* synthetic */ RxCache lambda$applyOptions$4(Context context, RxCache.Builder builder) {
        builder.useExpiredDataIfLoaderNotAvailable(true);
        return null;
    }

    @Override // com.jess.arms.integration.ConfigModule
    public void injectAppLifecycle(@NonNull Context context, @NonNull List<AppLifecycles> list) {
        list.add(new AppLifecycles() { // from class: com.petkit.android.app.GlobalConfiguration.3
            @Override // com.jess.arms.base.delegate.AppLifecycles
            public void attachBaseContext(Context context2) {
            }

            @Override // com.jess.arms.base.delegate.AppLifecycles
            public void onCreate(Application application) {
            }

            @Override // com.jess.arms.base.delegate.AppLifecycles
            public void onTerminate(Application application) {
            }
        });
    }

    /* JADX INFO: renamed from: com.petkit.android.app.GlobalConfiguration$4, reason: invalid class name */
    public class AnonymousClass4 implements Application.ActivityLifecycleCallbacks {
        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityCreated(Activity activity, Bundle bundle) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityDestroyed(Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityPaused(Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityResumed(Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStopped(Activity activity) {
        }

        public AnonymousClass4() {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStarted(final Activity activity) {
            if (activity.getIntent().getBooleanExtra("isInitToolbar", false)) {
                return;
            }
            activity.getIntent().putExtra("isInitToolbar", true);
            if (activity.findViewById(R.id.toolbar) != null) {
                if (activity instanceof AppCompatActivity) {
                    AppCompatActivity appCompatActivity = (AppCompatActivity) activity;
                    appCompatActivity.setSupportActionBar((Toolbar) activity.findViewById(R.id.toolbar));
                    appCompatActivity.getSupportActionBar().setDisplayShowTitleEnabled(false);
                } else {
                    activity.setActionBar((android.widget.Toolbar) activity.findViewById(R.id.toolbar));
                    activity.getActionBar().setDisplayShowTitleEnabled(false);
                }
            }
            if (activity.findViewById(R.id.toolbar_title) != null) {
                ((TextView) activity.findViewById(R.id.toolbar_title)).setText(activity.getTitle());
            }
            if (activity.findViewById(R.id.toolbar_back) != null) {
                activity.findViewById(R.id.toolbar_back).setOnClickListener(new View.OnClickListener() { // from class: com.petkit.android.app.GlobalConfiguration$4$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        activity.onBackPressed();
                    }
                });
            }
        }
    }

    @Override // com.jess.arms.integration.ConfigModule
    public void injectActivityLifecycle(Context context, List<Application.ActivityLifecycleCallbacks> list) {
        list.add(new AnonymousClass4());
    }

    @Override // com.jess.arms.integration.ConfigModule
    public void injectFragmentLifecycle(Context context, List<FragmentManager.FragmentLifecycleCallbacks> list) {
        list.add(new FragmentManager.FragmentLifecycleCallbacks() { // from class: com.petkit.android.app.GlobalConfiguration.5
            @Override // androidx.fragment.app.FragmentManager.FragmentLifecycleCallbacks
            public void onFragmentDestroyed(FragmentManager fragmentManager, Fragment fragment) {
            }

            @Override // androidx.fragment.app.FragmentManager.FragmentLifecycleCallbacks
            public void onFragmentCreated(FragmentManager fragmentManager, Fragment fragment, Bundle bundle) {
                fragment.setRetainInstance(true);
            }
        });
    }
}

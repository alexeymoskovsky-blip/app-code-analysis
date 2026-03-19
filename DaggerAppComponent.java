package com.jess.arms.di.component;

import android.app.Application;
import androidx.fragment.app.FragmentManager;
import com.google.gson.Gson;
import com.jess.arms.base.delegate.AppDelegate;
import com.jess.arms.base.delegate.AppDelegate_MembersInjector;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.module.AppModule;
import com.jess.arms.di.module.AppModule_ProvideAppManagerFactory;
import com.jess.arms.di.module.AppModule_ProvideExtrasFactory;
import com.jess.arms.di.module.AppModule_ProvideFragmentLifecyclesFactory;
import com.jess.arms.di.module.AppModule_ProvideGsonFactory;
import com.jess.arms.di.module.ClientModule;
import com.jess.arms.di.module.ClientModule_ProRxErrorHandlerFactory;
import com.jess.arms.di.module.ClientModule_ProvideClientBuilderFactory;
import com.jess.arms.di.module.ClientModule_ProvideClientFactory;
import com.jess.arms.di.module.ClientModule_ProvideRetrofitBuilderFactory;
import com.jess.arms.di.module.ClientModule_ProvideRetrofitFactory;
import com.jess.arms.di.module.ClientModule_ProvideRxCacheDirectoryFactory;
import com.jess.arms.di.module.ClientModule_ProvideRxCacheFactory;
import com.jess.arms.di.module.GlobalConfigModule;
import com.jess.arms.di.module.GlobalConfigModule_ProvideBaseUrlFactory;
import com.jess.arms.di.module.GlobalConfigModule_ProvideCacheFactoryFactory;
import com.jess.arms.di.module.GlobalConfigModule_ProvideCacheFileFactory;
import com.jess.arms.di.module.GlobalConfigModule_ProvideExecutorServiceFactory;
import com.jess.arms.di.module.GlobalConfigModule_ProvideFormatPrinterFactory;
import com.jess.arms.di.module.GlobalConfigModule_ProvideGlobalHttpHandlerFactory;
import com.jess.arms.di.module.GlobalConfigModule_ProvideGsonConfigurationFactory;
import com.jess.arms.di.module.GlobalConfigModule_ProvideImageLoaderStrategyFactory;
import com.jess.arms.di.module.GlobalConfigModule_ProvideInterceptorsFactory;
import com.jess.arms.di.module.GlobalConfigModule_ProvideObtainServiceDelegateFactory;
import com.jess.arms.di.module.GlobalConfigModule_ProvideOkhttpConfigurationFactory;
import com.jess.arms.di.module.GlobalConfigModule_ProvidePrintHttpLogLevelFactory;
import com.jess.arms.di.module.GlobalConfigModule_ProvideResponseErrorListenerFactory;
import com.jess.arms.di.module.GlobalConfigModule_ProvideRetrofitConfigurationFactory;
import com.jess.arms.di.module.GlobalConfigModule_ProvideRxCacheConfigurationFactory;
import com.jess.arms.http.GlobalHttpHandler;
import com.jess.arms.http.log.FormatPrinter;
import com.jess.arms.http.log.RequestInterceptor;
import com.jess.arms.http.log.RequestInterceptor_Factory;
import com.jess.arms.integration.ActivityLifecycle;
import com.jess.arms.integration.ActivityLifecycle_Factory;
import com.jess.arms.integration.AppManager;
import com.jess.arms.integration.FragmentLifecycle;
import com.jess.arms.integration.FragmentLifecycle_Factory;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.integration.RepositoryManager;
import com.jess.arms.integration.RepositoryManager_Factory;
import com.jess.arms.integration.cache.Cache;
import com.jess.arms.integration.lifecycle.ActivityLifecycleForRxLifecycle;
import com.jess.arms.integration.lifecycle.ActivityLifecycleForRxLifecycle_Factory;
import com.jess.arms.integration.lifecycle.FragmentLifecycleForRxLifecycle;
import com.jess.arms.integration.lifecycle.FragmentLifecycleForRxLifecycle_Factory;
import com.jess.arms.widget.imageloader.BaseImageLoaderStrategy;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.jess.arms.widget.imageloader.ImageLoader_Factory;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import dagger.internal.InstanceFactory;
import dagger.internal.Preconditions;
import io.rx_cache2.internal.RxCache;
import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import javax.inject.Provider;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.listener.ResponseErrorListener;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/* JADX INFO: loaded from: classes3.dex */
public final class DaggerAppComponent implements AppComponent {
    public Provider<ActivityLifecycleForRxLifecycle> activityLifecycleForRxLifecycleProvider;
    public Provider<ActivityLifecycle> activityLifecycleProvider;
    public final Application application;
    public Provider<Application> applicationProvider;
    public Provider<FragmentLifecycleForRxLifecycle> fragmentLifecycleForRxLifecycleProvider;
    public Provider<FragmentLifecycle> fragmentLifecycleProvider;
    public Provider<ImageLoader> imageLoaderProvider;
    public Provider<RxErrorHandler> proRxErrorHandlerProvider;
    public Provider<AppManager> provideAppManagerProvider;
    public Provider<HttpUrl> provideBaseUrlProvider;
    public Provider<Cache.Factory> provideCacheFactoryProvider;
    public Provider<File> provideCacheFileProvider;
    public Provider<OkHttpClient.Builder> provideClientBuilderProvider;
    public Provider<OkHttpClient> provideClientProvider;
    public Provider<ExecutorService> provideExecutorServiceProvider;
    public Provider<Cache<String, Object>> provideExtrasProvider;
    public Provider<FormatPrinter> provideFormatPrinterProvider;
    public Provider<List<FragmentManager.FragmentLifecycleCallbacks>> provideFragmentLifecyclesProvider;
    public Provider<GlobalHttpHandler> provideGlobalHttpHandlerProvider;
    public Provider<AppModule.GsonConfiguration> provideGsonConfigurationProvider;
    public Provider<Gson> provideGsonProvider;
    public Provider<BaseImageLoaderStrategy> provideImageLoaderStrategyProvider;
    public Provider<List<Interceptor>> provideInterceptorsProvider;
    public Provider<IRepositoryManager.ObtainServiceDelegate> provideObtainServiceDelegateProvider;
    public Provider<ClientModule.OkhttpConfiguration> provideOkhttpConfigurationProvider;
    public Provider<RequestInterceptor.Level> providePrintHttpLogLevelProvider;
    public Provider<ResponseErrorListener> provideResponseErrorListenerProvider;
    public Provider<Retrofit.Builder> provideRetrofitBuilderProvider;
    public Provider<ClientModule.RetrofitConfiguration> provideRetrofitConfigurationProvider;
    public Provider<Retrofit> provideRetrofitProvider;
    public Provider<ClientModule.RxCacheConfiguration> provideRxCacheConfigurationProvider;
    public Provider<File> provideRxCacheDirectoryProvider;
    public Provider<RxCache> provideRxCacheProvider;
    public Provider<RepositoryManager> repositoryManagerProvider;
    public Provider<RequestInterceptor> requestInterceptorProvider;

    public /* synthetic */ DaggerAppComponent(GlobalConfigModule globalConfigModule, Application application, AnonymousClass1 anonymousClass1) {
        this(globalConfigModule, application);
    }

    public DaggerAppComponent(GlobalConfigModule globalConfigModule, Application application) {
        this.application = application;
        initialize(globalConfigModule, application);
    }

    public static AppComponent.Builder builder() {
        return new Builder();
    }

    public final void initialize(GlobalConfigModule globalConfigModule, Application application) {
        Factory factoryCreate = InstanceFactory.create(application);
        this.applicationProvider = factoryCreate;
        this.provideAppManagerProvider = DoubleCheck.provider(AppModule_ProvideAppManagerFactory.create(factoryCreate));
        this.provideRetrofitConfigurationProvider = DoubleCheck.provider(GlobalConfigModule_ProvideRetrofitConfigurationFactory.create(globalConfigModule));
        this.provideRetrofitBuilderProvider = DoubleCheck.provider(ClientModule_ProvideRetrofitBuilderFactory.create());
        this.provideOkhttpConfigurationProvider = DoubleCheck.provider(GlobalConfigModule_ProvideOkhttpConfigurationFactory.create(globalConfigModule));
        this.provideClientBuilderProvider = DoubleCheck.provider(ClientModule_ProvideClientBuilderFactory.create());
        this.provideGlobalHttpHandlerProvider = DoubleCheck.provider(GlobalConfigModule_ProvideGlobalHttpHandlerFactory.create(globalConfigModule));
        this.provideFormatPrinterProvider = DoubleCheck.provider(GlobalConfigModule_ProvideFormatPrinterFactory.create(globalConfigModule));
        Provider<RequestInterceptor.Level> provider = DoubleCheck.provider(GlobalConfigModule_ProvidePrintHttpLogLevelFactory.create(globalConfigModule));
        this.providePrintHttpLogLevelProvider = provider;
        this.requestInterceptorProvider = DoubleCheck.provider(RequestInterceptor_Factory.create(this.provideGlobalHttpHandlerProvider, this.provideFormatPrinterProvider, provider));
        this.provideInterceptorsProvider = DoubleCheck.provider(GlobalConfigModule_ProvideInterceptorsFactory.create(globalConfigModule));
        Provider<ExecutorService> provider2 = DoubleCheck.provider(GlobalConfigModule_ProvideExecutorServiceFactory.create(globalConfigModule));
        this.provideExecutorServiceProvider = provider2;
        this.provideClientProvider = DoubleCheck.provider(ClientModule_ProvideClientFactory.create(this.applicationProvider, this.provideOkhttpConfigurationProvider, this.provideClientBuilderProvider, this.requestInterceptorProvider, this.provideInterceptorsProvider, this.provideGlobalHttpHandlerProvider, provider2));
        this.provideBaseUrlProvider = DoubleCheck.provider(GlobalConfigModule_ProvideBaseUrlFactory.create(globalConfigModule));
        Provider<AppModule.GsonConfiguration> provider3 = DoubleCheck.provider(GlobalConfigModule_ProvideGsonConfigurationFactory.create(globalConfigModule));
        this.provideGsonConfigurationProvider = provider3;
        Provider<Gson> provider4 = DoubleCheck.provider(AppModule_ProvideGsonFactory.create(this.applicationProvider, provider3));
        this.provideGsonProvider = provider4;
        this.provideRetrofitProvider = DoubleCheck.provider(ClientModule_ProvideRetrofitFactory.create(this.applicationProvider, this.provideRetrofitConfigurationProvider, this.provideRetrofitBuilderProvider, this.provideClientProvider, this.provideBaseUrlProvider, provider4));
        this.provideRxCacheConfigurationProvider = DoubleCheck.provider(GlobalConfigModule_ProvideRxCacheConfigurationFactory.create(globalConfigModule));
        Provider<File> provider5 = DoubleCheck.provider(GlobalConfigModule_ProvideCacheFileFactory.create(globalConfigModule, this.applicationProvider));
        this.provideCacheFileProvider = provider5;
        Provider<File> provider6 = DoubleCheck.provider(ClientModule_ProvideRxCacheDirectoryFactory.create(provider5));
        this.provideRxCacheDirectoryProvider = provider6;
        this.provideRxCacheProvider = DoubleCheck.provider(ClientModule_ProvideRxCacheFactory.create(this.applicationProvider, this.provideRxCacheConfigurationProvider, provider6, this.provideGsonProvider));
        this.provideCacheFactoryProvider = DoubleCheck.provider(GlobalConfigModule_ProvideCacheFactoryFactory.create(globalConfigModule, this.applicationProvider));
        Provider<IRepositoryManager.ObtainServiceDelegate> provider7 = DoubleCheck.provider(GlobalConfigModule_ProvideObtainServiceDelegateFactory.create(globalConfigModule));
        this.provideObtainServiceDelegateProvider = provider7;
        this.repositoryManagerProvider = DoubleCheck.provider(RepositoryManager_Factory.create(this.provideRetrofitProvider, this.provideRxCacheProvider, this.applicationProvider, this.provideCacheFactoryProvider, provider7));
        Provider<ResponseErrorListener> provider8 = DoubleCheck.provider(GlobalConfigModule_ProvideResponseErrorListenerFactory.create(globalConfigModule));
        this.provideResponseErrorListenerProvider = provider8;
        this.proRxErrorHandlerProvider = DoubleCheck.provider(ClientModule_ProRxErrorHandlerFactory.create(this.applicationProvider, provider8));
        Provider<BaseImageLoaderStrategy> provider9 = DoubleCheck.provider(GlobalConfigModule_ProvideImageLoaderStrategyFactory.create(globalConfigModule));
        this.provideImageLoaderStrategyProvider = provider9;
        this.imageLoaderProvider = DoubleCheck.provider(ImageLoader_Factory.create(provider9));
        this.provideExtrasProvider = DoubleCheck.provider(AppModule_ProvideExtrasFactory.create(this.provideCacheFactoryProvider));
        this.fragmentLifecycleProvider = DoubleCheck.provider(FragmentLifecycle_Factory.create());
        Provider<List<FragmentManager.FragmentLifecycleCallbacks>> provider10 = DoubleCheck.provider(AppModule_ProvideFragmentLifecyclesFactory.create());
        this.provideFragmentLifecyclesProvider = provider10;
        this.activityLifecycleProvider = DoubleCheck.provider(ActivityLifecycle_Factory.create(this.provideAppManagerProvider, this.applicationProvider, this.provideExtrasProvider, this.fragmentLifecycleProvider, provider10));
        Provider<FragmentLifecycleForRxLifecycle> provider11 = DoubleCheck.provider(FragmentLifecycleForRxLifecycle_Factory.create());
        this.fragmentLifecycleForRxLifecycleProvider = provider11;
        this.activityLifecycleForRxLifecycleProvider = DoubleCheck.provider(ActivityLifecycleForRxLifecycle_Factory.create(provider11));
    }

    @Override // com.jess.arms.di.component.AppComponent
    public Application application() {
        return this.application;
    }

    @Override // com.jess.arms.di.component.AppComponent
    public AppManager appManager() {
        return this.provideAppManagerProvider.get();
    }

    @Override // com.jess.arms.di.component.AppComponent
    public IRepositoryManager repositoryManager() {
        return this.repositoryManagerProvider.get();
    }

    @Override // com.jess.arms.di.component.AppComponent
    public RxErrorHandler rxErrorHandler() {
        return this.proRxErrorHandlerProvider.get();
    }

    @Override // com.jess.arms.di.component.AppComponent
    public ImageLoader imageLoader() {
        return this.imageLoaderProvider.get();
    }

    @Override // com.jess.arms.di.component.AppComponent
    public OkHttpClient okHttpClient() {
        return this.provideClientProvider.get();
    }

    @Override // com.jess.arms.di.component.AppComponent
    public Gson gson() {
        return this.provideGsonProvider.get();
    }

    @Override // com.jess.arms.di.component.AppComponent
    public File cacheFile() {
        return this.provideCacheFileProvider.get();
    }

    @Override // com.jess.arms.di.component.AppComponent
    public Cache<String, Object> extras() {
        return this.provideExtrasProvider.get();
    }

    @Override // com.jess.arms.di.component.AppComponent
    public Cache.Factory cacheFactory() {
        return this.provideCacheFactoryProvider.get();
    }

    @Override // com.jess.arms.di.component.AppComponent
    public ExecutorService executorService() {
        return this.provideExecutorServiceProvider.get();
    }

    @Override // com.jess.arms.di.component.AppComponent
    public void inject(AppDelegate appDelegate) {
        injectAppDelegate(appDelegate);
    }

    public final AppDelegate injectAppDelegate(AppDelegate appDelegate) {
        AppDelegate_MembersInjector.injectMActivityLifecycle(appDelegate, this.activityLifecycleProvider.get());
        AppDelegate_MembersInjector.injectMActivityLifecycleForRxLifecycle(appDelegate, this.activityLifecycleForRxLifecycleProvider.get());
        return appDelegate;
    }

    public static final class Builder implements AppComponent.Builder {
        public Application application;
        public GlobalConfigModule globalConfigModule;

        public Builder() {
        }

        public /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
            this();
        }

        @Override // com.jess.arms.di.component.AppComponent.Builder
        public Builder application(Application application) {
            this.application = (Application) Preconditions.checkNotNull(application);
            return this;
        }

        @Override // com.jess.arms.di.component.AppComponent.Builder
        public Builder globalConfigModule(GlobalConfigModule globalConfigModule) {
            this.globalConfigModule = (GlobalConfigModule) Preconditions.checkNotNull(globalConfigModule);
            return this;
        }

        @Override // com.jess.arms.di.component.AppComponent.Builder
        public AppComponent build() {
            Preconditions.checkBuilderRequirement(this.application, Application.class);
            Preconditions.checkBuilderRequirement(this.globalConfigModule, GlobalConfigModule.class);
            return new DaggerAppComponent(this.globalConfigModule, this.application);
        }
    }
}

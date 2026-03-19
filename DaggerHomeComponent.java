package com.petkit.android.activities.home.component;

import android.app.Application;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.gson.Gson;
import com.jess.arms.base.BaseActivity_MembersInjector;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.IRepositoryManager;
import com.petkit.android.activities.home.HomeActivity;
import com.petkit.android.activities.home.contract.HomeContract;
import com.petkit.android.activities.home.model.HomeModel;
import com.petkit.android.activities.home.model.HomeModel_Factory;
import com.petkit.android.activities.home.module.HomeModule;
import com.petkit.android.activities.home.module.HomeModule_ProvideHomeModelFactory;
import com.petkit.android.activities.home.module.HomeModule_ProvideHomeViewFactory;
import com.petkit.android.activities.home.presenter.HomePresenter;
import com.petkit.android.activities.home.presenter.HomePresenter_Factory;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/* JADX INFO: loaded from: classes4.dex */
public final class DaggerHomeComponent implements HomeComponent {
    public Provider<Application> applicationProvider;
    public Provider<Gson> gsonProvider;
    public Provider<HomeModel> homeModelProvider;
    public Provider<HomePresenter> homePresenterProvider;
    public Provider<HomeContract.Model> provideHomeModelProvider;
    public Provider<HomeContract.View> provideHomeViewProvider;
    public Provider<IRepositoryManager> repositoryManagerProvider;
    public Provider<RxErrorHandler> rxErrorHandlerProvider;

    public DaggerHomeComponent(HomeModule homeModule, AppComponent appComponent) {
        initialize(homeModule, appComponent);
    }

    public static Builder builder() {
        return new Builder();
    }

    public final void initialize(HomeModule homeModule, AppComponent appComponent) {
        this.repositoryManagerProvider = new com_jess_arms_di_component_AppComponent_repositoryManager(appComponent);
        this.gsonProvider = new com_jess_arms_di_component_AppComponent_gson(appComponent);
        com_jess_arms_di_component_AppComponent_application com_jess_arms_di_component_appcomponent_application = new com_jess_arms_di_component_AppComponent_application(appComponent);
        this.applicationProvider = com_jess_arms_di_component_appcomponent_application;
        Provider<HomeModel> provider = DoubleCheck.provider(HomeModel_Factory.create(this.repositoryManagerProvider, this.gsonProvider, com_jess_arms_di_component_appcomponent_application));
        this.homeModelProvider = provider;
        this.provideHomeModelProvider = DoubleCheck.provider(HomeModule_ProvideHomeModelFactory.create(homeModule, provider));
        this.provideHomeViewProvider = DoubleCheck.provider(HomeModule_ProvideHomeViewFactory.create(homeModule));
        com_jess_arms_di_component_AppComponent_rxErrorHandler com_jess_arms_di_component_appcomponent_rxerrorhandler = new com_jess_arms_di_component_AppComponent_rxErrorHandler(appComponent);
        this.rxErrorHandlerProvider = com_jess_arms_di_component_appcomponent_rxerrorhandler;
        this.homePresenterProvider = DoubleCheck.provider(HomePresenter_Factory.create(this.provideHomeModelProvider, this.provideHomeViewProvider, com_jess_arms_di_component_appcomponent_rxerrorhandler, this.applicationProvider));
    }

    @Override // com.petkit.android.activities.home.component.HomeComponent
    public void inject(HomeActivity homeActivity) {
        injectHomeActivity(homeActivity);
    }

    @CanIgnoreReturnValue
    public final HomeActivity injectHomeActivity(HomeActivity homeActivity) {
        BaseActivity_MembersInjector.injectMPresenter(homeActivity, this.homePresenterProvider.get());
        return homeActivity;
    }

    public static final class Builder {
        public AppComponent appComponent;
        public HomeModule homeModule;

        public Builder() {
        }

        public Builder homeModule(HomeModule homeModule) {
            this.homeModule = (HomeModule) Preconditions.checkNotNull(homeModule);
            return this;
        }

        public Builder appComponent(AppComponent appComponent) {
            this.appComponent = (AppComponent) Preconditions.checkNotNull(appComponent);
            return this;
        }

        public HomeComponent build() {
            Preconditions.checkBuilderRequirement(this.homeModule, HomeModule.class);
            Preconditions.checkBuilderRequirement(this.appComponent, AppComponent.class);
            return new DaggerHomeComponent(this.homeModule, this.appComponent);
        }
    }

    public static class com_jess_arms_di_component_AppComponent_repositoryManager implements Provider<IRepositoryManager> {
        public final AppComponent appComponent;

        public com_jess_arms_di_component_AppComponent_repositoryManager(AppComponent appComponent) {
            this.appComponent = appComponent;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // javax.inject.Provider
        public IRepositoryManager get() {
            return (IRepositoryManager) Preconditions.checkNotNull(this.appComponent.repositoryManager(), "Cannot return null from a non-@Nullable component method");
        }
    }

    public static class com_jess_arms_di_component_AppComponent_gson implements Provider<Gson> {
        public final AppComponent appComponent;

        public com_jess_arms_di_component_AppComponent_gson(AppComponent appComponent) {
            this.appComponent = appComponent;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // javax.inject.Provider
        public Gson get() {
            return (Gson) Preconditions.checkNotNull(this.appComponent.gson(), "Cannot return null from a non-@Nullable component method");
        }
    }

    public static class com_jess_arms_di_component_AppComponent_application implements Provider<Application> {
        public final AppComponent appComponent;

        public com_jess_arms_di_component_AppComponent_application(AppComponent appComponent) {
            this.appComponent = appComponent;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // javax.inject.Provider
        public Application get() {
            return (Application) Preconditions.checkNotNull(this.appComponent.application(), "Cannot return null from a non-@Nullable component method");
        }
    }

    public static class com_jess_arms_di_component_AppComponent_rxErrorHandler implements Provider<RxErrorHandler> {
        public final AppComponent appComponent;

        public com_jess_arms_di_component_AppComponent_rxErrorHandler(AppComponent appComponent) {
            this.appComponent = appComponent;
        }

        @Override // javax.inject.Provider
        public RxErrorHandler get() {
            return (RxErrorHandler) Preconditions.checkNotNull(this.appComponent.rxErrorHandler(), "Cannot return null from a non-@Nullable component method");
        }
    }
}

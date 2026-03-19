package com.petkit.android.activities.home.component;

import android.app.Application;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.gson.Gson;
import com.jess.arms.base.BaseFragment_MembersInjector;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.IRepositoryManager;
import com.petkit.android.activities.home.HomePageFragment;
import com.petkit.android.activities.home.contract.HomePageContract;
import com.petkit.android.activities.home.model.HomePageModel;
import com.petkit.android.activities.home.model.HomePageModel_Factory;
import com.petkit.android.activities.home.module.HomePageModule;
import com.petkit.android.activities.home.module.HomePageModule_ProvideHomePageModelFactory;
import com.petkit.android.activities.home.module.HomePageModule_ProvideHomePageViewFactory;
import com.petkit.android.activities.home.presenter.HomePagePresenter;
import com.petkit.android.activities.home.presenter.HomePagePresenter_Factory;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/* JADX INFO: loaded from: classes4.dex */
public final class DaggerHomePageComponent implements HomePageComponent {
    public Provider<Application> applicationProvider;
    public Provider<Gson> gsonProvider;
    public Provider<HomePageModel> homePageModelProvider;
    public Provider<HomePagePresenter> homePagePresenterProvider;
    public Provider<HomePageContract.Model> provideHomePageModelProvider;
    public Provider<HomePageContract.View> provideHomePageViewProvider;
    public Provider<IRepositoryManager> repositoryManagerProvider;
    public Provider<RxErrorHandler> rxErrorHandlerProvider;

    public DaggerHomePageComponent(HomePageModule homePageModule, AppComponent appComponent) {
        initialize(homePageModule, appComponent);
    }

    public static Builder builder() {
        return new Builder();
    }

    public final void initialize(HomePageModule homePageModule, AppComponent appComponent) {
        this.repositoryManagerProvider = new com_jess_arms_di_component_AppComponent_repositoryManager(appComponent);
        this.gsonProvider = new com_jess_arms_di_component_AppComponent_gson(appComponent);
        com_jess_arms_di_component_AppComponent_application com_jess_arms_di_component_appcomponent_application = new com_jess_arms_di_component_AppComponent_application(appComponent);
        this.applicationProvider = com_jess_arms_di_component_appcomponent_application;
        Provider<HomePageModel> provider = DoubleCheck.provider(HomePageModel_Factory.create(this.repositoryManagerProvider, this.gsonProvider, com_jess_arms_di_component_appcomponent_application));
        this.homePageModelProvider = provider;
        this.provideHomePageModelProvider = DoubleCheck.provider(HomePageModule_ProvideHomePageModelFactory.create(homePageModule, provider));
        this.provideHomePageViewProvider = DoubleCheck.provider(HomePageModule_ProvideHomePageViewFactory.create(homePageModule));
        com_jess_arms_di_component_AppComponent_rxErrorHandler com_jess_arms_di_component_appcomponent_rxerrorhandler = new com_jess_arms_di_component_AppComponent_rxErrorHandler(appComponent);
        this.rxErrorHandlerProvider = com_jess_arms_di_component_appcomponent_rxerrorhandler;
        this.homePagePresenterProvider = DoubleCheck.provider(HomePagePresenter_Factory.create(this.provideHomePageModelProvider, this.provideHomePageViewProvider, com_jess_arms_di_component_appcomponent_rxerrorhandler, this.applicationProvider));
    }

    @Override // com.petkit.android.activities.home.component.HomePageComponent
    public void inject(HomePageFragment homePageFragment) {
        injectHomePageFragment(homePageFragment);
    }

    @CanIgnoreReturnValue
    public final HomePageFragment injectHomePageFragment(HomePageFragment homePageFragment) {
        BaseFragment_MembersInjector.injectMPresenter(homePageFragment, this.homePagePresenterProvider.get());
        return homePageFragment;
    }

    public static final class Builder {
        public AppComponent appComponent;
        public HomePageModule homePageModule;

        public Builder() {
        }

        public Builder homePageModule(HomePageModule homePageModule) {
            this.homePageModule = (HomePageModule) Preconditions.checkNotNull(homePageModule);
            return this;
        }

        public Builder appComponent(AppComponent appComponent) {
            this.appComponent = (AppComponent) Preconditions.checkNotNull(appComponent);
            return this;
        }

        public HomePageComponent build() {
            Preconditions.checkBuilderRequirement(this.homePageModule, HomePageModule.class);
            Preconditions.checkBuilderRequirement(this.appComponent, AppComponent.class);
            return new DaggerHomePageComponent(this.homePageModule, this.appComponent);
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

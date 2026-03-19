package com.petkit.android.activities.virtual.t4.component;

import android.app.Application;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.gson.Gson;
import com.jess.arms.base.BaseActivity_MembersInjector;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.IRepositoryManager;
import com.petkit.android.activities.virtual.t4.VirtualT4HomeActivity;
import com.petkit.android.activities.virtual.t4.contract.VirtualT4HomeContract;
import com.petkit.android.activities.virtual.t4.model.VirtualT4HomeModel;
import com.petkit.android.activities.virtual.t4.model.VirtualT4HomeModel_Factory;
import com.petkit.android.activities.virtual.t4.module.VirtualT4HomeModule;
import com.petkit.android.activities.virtual.t4.module.VirtualT4HomeModule_ProvideVirtualT4HomeModelFactory;
import com.petkit.android.activities.virtual.t4.module.VirtualT4HomeModule_ProvideVirtualT4HomeViewFactory;
import com.petkit.android.activities.virtual.t4.presenter.VirtualT4HomePresenter;
import com.petkit.android.activities.virtual.t4.presenter.VirtualT4HomePresenter_Factory;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/* JADX INFO: loaded from: classes6.dex */
public final class DaggerVirtualT4HomeComponent implements VirtualT4HomeComponent {
    public Provider<Application> applicationProvider;
    public Provider<Gson> gsonProvider;
    public Provider<VirtualT4HomeContract.Model> provideVirtualT4HomeModelProvider;
    public Provider<VirtualT4HomeContract.View> provideVirtualT4HomeViewProvider;
    public Provider<IRepositoryManager> repositoryManagerProvider;
    public Provider<RxErrorHandler> rxErrorHandlerProvider;
    public Provider<VirtualT4HomeModel> virtualT4HomeModelProvider;
    public Provider<VirtualT4HomePresenter> virtualT4HomePresenterProvider;

    public DaggerVirtualT4HomeComponent(VirtualT4HomeModule virtualT4HomeModule, AppComponent appComponent) {
        initialize(virtualT4HomeModule, appComponent);
    }

    public static Builder builder() {
        return new Builder();
    }

    public final void initialize(VirtualT4HomeModule virtualT4HomeModule, AppComponent appComponent) {
        this.repositoryManagerProvider = new com_jess_arms_di_component_AppComponent_repositoryManager(appComponent);
        this.gsonProvider = new com_jess_arms_di_component_AppComponent_gson(appComponent);
        com_jess_arms_di_component_AppComponent_application com_jess_arms_di_component_appcomponent_application = new com_jess_arms_di_component_AppComponent_application(appComponent);
        this.applicationProvider = com_jess_arms_di_component_appcomponent_application;
        Provider<VirtualT4HomeModel> provider = DoubleCheck.provider(VirtualT4HomeModel_Factory.create(this.repositoryManagerProvider, this.gsonProvider, com_jess_arms_di_component_appcomponent_application));
        this.virtualT4HomeModelProvider = provider;
        this.provideVirtualT4HomeModelProvider = DoubleCheck.provider(VirtualT4HomeModule_ProvideVirtualT4HomeModelFactory.create(virtualT4HomeModule, provider));
        this.provideVirtualT4HomeViewProvider = DoubleCheck.provider(VirtualT4HomeModule_ProvideVirtualT4HomeViewFactory.create(virtualT4HomeModule));
        com_jess_arms_di_component_AppComponent_rxErrorHandler com_jess_arms_di_component_appcomponent_rxerrorhandler = new com_jess_arms_di_component_AppComponent_rxErrorHandler(appComponent);
        this.rxErrorHandlerProvider = com_jess_arms_di_component_appcomponent_rxerrorhandler;
        this.virtualT4HomePresenterProvider = DoubleCheck.provider(VirtualT4HomePresenter_Factory.create(this.provideVirtualT4HomeModelProvider, this.provideVirtualT4HomeViewProvider, com_jess_arms_di_component_appcomponent_rxerrorhandler, this.applicationProvider));
    }

    @Override // com.petkit.android.activities.virtual.t4.component.VirtualT4HomeComponent
    public void inject(VirtualT4HomeActivity virtualT4HomeActivity) {
        injectVirtualT4HomeActivity(virtualT4HomeActivity);
    }

    @CanIgnoreReturnValue
    public final VirtualT4HomeActivity injectVirtualT4HomeActivity(VirtualT4HomeActivity virtualT4HomeActivity) {
        BaseActivity_MembersInjector.injectMPresenter(virtualT4HomeActivity, this.virtualT4HomePresenterProvider.get());
        return virtualT4HomeActivity;
    }

    public static final class Builder {
        public AppComponent appComponent;
        public VirtualT4HomeModule virtualT4HomeModule;

        public Builder() {
        }

        public Builder virtualT4HomeModule(VirtualT4HomeModule virtualT4HomeModule) {
            this.virtualT4HomeModule = (VirtualT4HomeModule) Preconditions.checkNotNull(virtualT4HomeModule);
            return this;
        }

        public Builder appComponent(AppComponent appComponent) {
            this.appComponent = (AppComponent) Preconditions.checkNotNull(appComponent);
            return this;
        }

        public VirtualT4HomeComponent build() {
            Preconditions.checkBuilderRequirement(this.virtualT4HomeModule, VirtualT4HomeModule.class);
            Preconditions.checkBuilderRequirement(this.appComponent, AppComponent.class);
            return new DaggerVirtualT4HomeComponent(this.virtualT4HomeModule, this.appComponent);
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

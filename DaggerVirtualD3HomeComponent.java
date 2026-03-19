package com.petkit.android.activities.virtual.d3.component;

import android.app.Application;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.gson.Gson;
import com.jess.arms.base.BaseActivity_MembersInjector;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.IRepositoryManager;
import com.petkit.android.activities.virtual.d3.VirtualD3HomeActivity;
import com.petkit.android.activities.virtual.d3.contract.VirtualD3HomeContract;
import com.petkit.android.activities.virtual.d3.model.VirtualD3HomeModel;
import com.petkit.android.activities.virtual.d3.model.VirtualD3HomeModel_Factory;
import com.petkit.android.activities.virtual.d3.module.VirtualD3HomeModule;
import com.petkit.android.activities.virtual.d3.module.VirtualD3HomeModule_ProvideVirtualD3HomeModelFactory;
import com.petkit.android.activities.virtual.d3.module.VirtualD3HomeModule_ProvideVirtualD3HomeViewFactory;
import com.petkit.android.activities.virtual.d3.presenter.VirtualD3HomePresenter;
import com.petkit.android.activities.virtual.d3.presenter.VirtualD3HomePresenter_Factory;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/* JADX INFO: loaded from: classes6.dex */
public final class DaggerVirtualD3HomeComponent implements VirtualD3HomeComponent {
    public Provider<Application> applicationProvider;
    public Provider<Gson> gsonProvider;
    public Provider<VirtualD3HomeContract.Model> provideVirtualD3HomeModelProvider;
    public Provider<VirtualD3HomeContract.View> provideVirtualD3HomeViewProvider;
    public Provider<IRepositoryManager> repositoryManagerProvider;
    public Provider<RxErrorHandler> rxErrorHandlerProvider;
    public Provider<VirtualD3HomeModel> virtualD3HomeModelProvider;
    public Provider<VirtualD3HomePresenter> virtualD3HomePresenterProvider;

    public DaggerVirtualD3HomeComponent(VirtualD3HomeModule virtualD3HomeModule, AppComponent appComponent) {
        initialize(virtualD3HomeModule, appComponent);
    }

    public static Builder builder() {
        return new Builder();
    }

    public final void initialize(VirtualD3HomeModule virtualD3HomeModule, AppComponent appComponent) {
        this.repositoryManagerProvider = new com_jess_arms_di_component_AppComponent_repositoryManager(appComponent);
        this.gsonProvider = new com_jess_arms_di_component_AppComponent_gson(appComponent);
        com_jess_arms_di_component_AppComponent_application com_jess_arms_di_component_appcomponent_application = new com_jess_arms_di_component_AppComponent_application(appComponent);
        this.applicationProvider = com_jess_arms_di_component_appcomponent_application;
        Provider<VirtualD3HomeModel> provider = DoubleCheck.provider(VirtualD3HomeModel_Factory.create(this.repositoryManagerProvider, this.gsonProvider, com_jess_arms_di_component_appcomponent_application));
        this.virtualD3HomeModelProvider = provider;
        this.provideVirtualD3HomeModelProvider = DoubleCheck.provider(VirtualD3HomeModule_ProvideVirtualD3HomeModelFactory.create(virtualD3HomeModule, provider));
        this.provideVirtualD3HomeViewProvider = DoubleCheck.provider(VirtualD3HomeModule_ProvideVirtualD3HomeViewFactory.create(virtualD3HomeModule));
        com_jess_arms_di_component_AppComponent_rxErrorHandler com_jess_arms_di_component_appcomponent_rxerrorhandler = new com_jess_arms_di_component_AppComponent_rxErrorHandler(appComponent);
        this.rxErrorHandlerProvider = com_jess_arms_di_component_appcomponent_rxerrorhandler;
        this.virtualD3HomePresenterProvider = DoubleCheck.provider(VirtualD3HomePresenter_Factory.create(this.provideVirtualD3HomeModelProvider, this.provideVirtualD3HomeViewProvider, com_jess_arms_di_component_appcomponent_rxerrorhandler, this.applicationProvider));
    }

    @Override // com.petkit.android.activities.virtual.d3.component.VirtualD3HomeComponent
    public void inject(VirtualD3HomeActivity virtualD3HomeActivity) {
        injectVirtualD3HomeActivity(virtualD3HomeActivity);
    }

    @CanIgnoreReturnValue
    public final VirtualD3HomeActivity injectVirtualD3HomeActivity(VirtualD3HomeActivity virtualD3HomeActivity) {
        BaseActivity_MembersInjector.injectMPresenter(virtualD3HomeActivity, this.virtualD3HomePresenterProvider.get());
        return virtualD3HomeActivity;
    }

    public static final class Builder {
        public AppComponent appComponent;
        public VirtualD3HomeModule virtualD3HomeModule;

        public Builder() {
        }

        public Builder virtualD3HomeModule(VirtualD3HomeModule virtualD3HomeModule) {
            this.virtualD3HomeModule = (VirtualD3HomeModule) Preconditions.checkNotNull(virtualD3HomeModule);
            return this;
        }

        public Builder appComponent(AppComponent appComponent) {
            this.appComponent = (AppComponent) Preconditions.checkNotNull(appComponent);
            return this;
        }

        public VirtualD3HomeComponent build() {
            Preconditions.checkBuilderRequirement(this.virtualD3HomeModule, VirtualD3HomeModule.class);
            Preconditions.checkBuilderRequirement(this.appComponent, AppComponent.class);
            return new DaggerVirtualD3HomeComponent(this.virtualD3HomeModule, this.appComponent);
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

package com.petkit.android.activities.virtual.w5.component;

import android.app.Application;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.gson.Gson;
import com.jess.arms.base.BaseActivity_MembersInjector;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.IRepositoryManager;
import com.petkit.android.activities.virtual.w5.VirtualW5HomeActivity;
import com.petkit.android.activities.virtual.w5.contract.VirtualW5HomeContract;
import com.petkit.android.activities.virtual.w5.model.VirtualW5HomeModel;
import com.petkit.android.activities.virtual.w5.model.VirtualW5HomeModel_Factory;
import com.petkit.android.activities.virtual.w5.module.VirtualW5HomeModule;
import com.petkit.android.activities.virtual.w5.module.VirtualW5HomeModule_ProvideVirtualW5HomeModelFactory;
import com.petkit.android.activities.virtual.w5.module.VirtualW5HomeModule_ProvideVirtualW5HomeViewFactory;
import com.petkit.android.activities.virtual.w5.presenter.VirtualW5HomePresenter;
import com.petkit.android.activities.virtual.w5.presenter.VirtualW5HomePresenter_Factory;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/* JADX INFO: loaded from: classes6.dex */
public final class DaggerVirtualW5HomeComponent implements VirtualW5HomeComponent {
    public Provider<Application> applicationProvider;
    public Provider<Gson> gsonProvider;
    public Provider<VirtualW5HomeContract.Model> provideVirtualW5HomeModelProvider;
    public Provider<VirtualW5HomeContract.View> provideVirtualW5HomeViewProvider;
    public Provider<IRepositoryManager> repositoryManagerProvider;
    public Provider<RxErrorHandler> rxErrorHandlerProvider;
    public Provider<VirtualW5HomeModel> virtualW5HomeModelProvider;
    public Provider<VirtualW5HomePresenter> virtualW5HomePresenterProvider;

    public DaggerVirtualW5HomeComponent(VirtualW5HomeModule virtualW5HomeModule, AppComponent appComponent) {
        initialize(virtualW5HomeModule, appComponent);
    }

    public static Builder builder() {
        return new Builder();
    }

    public final void initialize(VirtualW5HomeModule virtualW5HomeModule, AppComponent appComponent) {
        this.repositoryManagerProvider = new com_jess_arms_di_component_AppComponent_repositoryManager(appComponent);
        this.gsonProvider = new com_jess_arms_di_component_AppComponent_gson(appComponent);
        com_jess_arms_di_component_AppComponent_application com_jess_arms_di_component_appcomponent_application = new com_jess_arms_di_component_AppComponent_application(appComponent);
        this.applicationProvider = com_jess_arms_di_component_appcomponent_application;
        Provider<VirtualW5HomeModel> provider = DoubleCheck.provider(VirtualW5HomeModel_Factory.create(this.repositoryManagerProvider, this.gsonProvider, com_jess_arms_di_component_appcomponent_application));
        this.virtualW5HomeModelProvider = provider;
        this.provideVirtualW5HomeModelProvider = DoubleCheck.provider(VirtualW5HomeModule_ProvideVirtualW5HomeModelFactory.create(virtualW5HomeModule, provider));
        this.provideVirtualW5HomeViewProvider = DoubleCheck.provider(VirtualW5HomeModule_ProvideVirtualW5HomeViewFactory.create(virtualW5HomeModule));
        com_jess_arms_di_component_AppComponent_rxErrorHandler com_jess_arms_di_component_appcomponent_rxerrorhandler = new com_jess_arms_di_component_AppComponent_rxErrorHandler(appComponent);
        this.rxErrorHandlerProvider = com_jess_arms_di_component_appcomponent_rxerrorhandler;
        this.virtualW5HomePresenterProvider = DoubleCheck.provider(VirtualW5HomePresenter_Factory.create(this.provideVirtualW5HomeModelProvider, this.provideVirtualW5HomeViewProvider, com_jess_arms_di_component_appcomponent_rxerrorhandler, this.applicationProvider));
    }

    @Override // com.petkit.android.activities.virtual.w5.component.VirtualW5HomeComponent
    public void inject(VirtualW5HomeActivity virtualW5HomeActivity) {
        injectVirtualW5HomeActivity(virtualW5HomeActivity);
    }

    @CanIgnoreReturnValue
    public final VirtualW5HomeActivity injectVirtualW5HomeActivity(VirtualW5HomeActivity virtualW5HomeActivity) {
        BaseActivity_MembersInjector.injectMPresenter(virtualW5HomeActivity, this.virtualW5HomePresenterProvider.get());
        return virtualW5HomeActivity;
    }

    public static final class Builder {
        public AppComponent appComponent;
        public VirtualW5HomeModule virtualW5HomeModule;

        public Builder() {
        }

        public Builder virtualW5HomeModule(VirtualW5HomeModule virtualW5HomeModule) {
            this.virtualW5HomeModule = (VirtualW5HomeModule) Preconditions.checkNotNull(virtualW5HomeModule);
            return this;
        }

        public Builder appComponent(AppComponent appComponent) {
            this.appComponent = (AppComponent) Preconditions.checkNotNull(appComponent);
            return this;
        }

        public VirtualW5HomeComponent build() {
            Preconditions.checkBuilderRequirement(this.virtualW5HomeModule, VirtualW5HomeModule.class);
            Preconditions.checkBuilderRequirement(this.appComponent, AppComponent.class);
            return new DaggerVirtualW5HomeComponent(this.virtualW5HomeModule, this.appComponent);
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

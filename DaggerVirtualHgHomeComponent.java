package com.petkit.android.activities.virtual.hg.component;

import android.app.Application;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.gson.Gson;
import com.jess.arms.base.BaseActivity_MembersInjector;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.IRepositoryManager;
import com.petkit.android.activities.virtual.hg.VirtualHgHomeActivity;
import com.petkit.android.activities.virtual.hg.contract.VirtualHgHomeContract;
import com.petkit.android.activities.virtual.hg.model.VirtualHgHomeModel;
import com.petkit.android.activities.virtual.hg.model.VirtualHgHomeModel_Factory;
import com.petkit.android.activities.virtual.hg.module.VirtualHgHomeModule;
import com.petkit.android.activities.virtual.hg.module.VirtualHgHomeModule_ProvideVirtualHgHomeModelFactory;
import com.petkit.android.activities.virtual.hg.module.VirtualHgHomeModule_ProvideVirtualHgHomeViewFactory;
import com.petkit.android.activities.virtual.hg.presenter.VirtualHgHomePresenter;
import com.petkit.android.activities.virtual.hg.presenter.VirtualHgHomePresenter_Factory;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/* JADX INFO: loaded from: classes6.dex */
public final class DaggerVirtualHgHomeComponent implements VirtualHgHomeComponent {
    public Provider<Application> applicationProvider;
    public Provider<Gson> gsonProvider;
    public Provider<VirtualHgHomeContract.Model> provideVirtualHgHomeModelProvider;
    public Provider<VirtualHgHomeContract.View> provideVirtualHgHomeViewProvider;
    public Provider<IRepositoryManager> repositoryManagerProvider;
    public Provider<RxErrorHandler> rxErrorHandlerProvider;
    public Provider<VirtualHgHomeModel> virtualHgHomeModelProvider;
    public Provider<VirtualHgHomePresenter> virtualHgHomePresenterProvider;

    public DaggerVirtualHgHomeComponent(VirtualHgHomeModule virtualHgHomeModule, AppComponent appComponent) {
        initialize(virtualHgHomeModule, appComponent);
    }

    public static Builder builder() {
        return new Builder();
    }

    public final void initialize(VirtualHgHomeModule virtualHgHomeModule, AppComponent appComponent) {
        this.repositoryManagerProvider = new com_jess_arms_di_component_AppComponent_repositoryManager(appComponent);
        this.gsonProvider = new com_jess_arms_di_component_AppComponent_gson(appComponent);
        com_jess_arms_di_component_AppComponent_application com_jess_arms_di_component_appcomponent_application = new com_jess_arms_di_component_AppComponent_application(appComponent);
        this.applicationProvider = com_jess_arms_di_component_appcomponent_application;
        Provider<VirtualHgHomeModel> provider = DoubleCheck.provider(VirtualHgHomeModel_Factory.create(this.repositoryManagerProvider, this.gsonProvider, com_jess_arms_di_component_appcomponent_application));
        this.virtualHgHomeModelProvider = provider;
        this.provideVirtualHgHomeModelProvider = DoubleCheck.provider(VirtualHgHomeModule_ProvideVirtualHgHomeModelFactory.create(virtualHgHomeModule, provider));
        this.provideVirtualHgHomeViewProvider = DoubleCheck.provider(VirtualHgHomeModule_ProvideVirtualHgHomeViewFactory.create(virtualHgHomeModule));
        com_jess_arms_di_component_AppComponent_rxErrorHandler com_jess_arms_di_component_appcomponent_rxerrorhandler = new com_jess_arms_di_component_AppComponent_rxErrorHandler(appComponent);
        this.rxErrorHandlerProvider = com_jess_arms_di_component_appcomponent_rxerrorhandler;
        this.virtualHgHomePresenterProvider = DoubleCheck.provider(VirtualHgHomePresenter_Factory.create(this.provideVirtualHgHomeModelProvider, this.provideVirtualHgHomeViewProvider, com_jess_arms_di_component_appcomponent_rxerrorhandler, this.applicationProvider));
    }

    @Override // com.petkit.android.activities.virtual.hg.component.VirtualHgHomeComponent
    public void inject(VirtualHgHomeActivity virtualHgHomeActivity) {
        injectVirtualHgHomeActivity(virtualHgHomeActivity);
    }

    @CanIgnoreReturnValue
    public final VirtualHgHomeActivity injectVirtualHgHomeActivity(VirtualHgHomeActivity virtualHgHomeActivity) {
        BaseActivity_MembersInjector.injectMPresenter(virtualHgHomeActivity, this.virtualHgHomePresenterProvider.get());
        return virtualHgHomeActivity;
    }

    public static final class Builder {
        public AppComponent appComponent;
        public VirtualHgHomeModule virtualHgHomeModule;

        public Builder() {
        }

        public Builder virtualHgHomeModule(VirtualHgHomeModule virtualHgHomeModule) {
            this.virtualHgHomeModule = (VirtualHgHomeModule) Preconditions.checkNotNull(virtualHgHomeModule);
            return this;
        }

        public Builder appComponent(AppComponent appComponent) {
            this.appComponent = (AppComponent) Preconditions.checkNotNull(appComponent);
            return this;
        }

        public VirtualHgHomeComponent build() {
            Preconditions.checkBuilderRequirement(this.virtualHgHomeModule, VirtualHgHomeModule.class);
            Preconditions.checkBuilderRequirement(this.appComponent, AppComponent.class);
            return new DaggerVirtualHgHomeComponent(this.virtualHgHomeModule, this.appComponent);
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

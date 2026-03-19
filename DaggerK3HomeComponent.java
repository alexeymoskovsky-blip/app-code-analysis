package com.petkit.android.activities.petkitBleDevice.k3.component;

import android.app.Application;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.gson.Gson;
import com.jess.arms.base.BaseActivity_MembersInjector;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.IRepositoryManager;
import com.petkit.android.activities.petkitBleDevice.k3.K3HomeActivity;
import com.petkit.android.activities.petkitBleDevice.k3.contract.K3HomeContract;
import com.petkit.android.activities.petkitBleDevice.k3.model.K3HomeModel;
import com.petkit.android.activities.petkitBleDevice.k3.model.K3HomeModel_Factory;
import com.petkit.android.activities.petkitBleDevice.k3.module.K3HomeModule;
import com.petkit.android.activities.petkitBleDevice.k3.module.K3HomeModule_ProvideK3HomeModelFactory;
import com.petkit.android.activities.petkitBleDevice.k3.module.K3HomeModule_ProvideK3HomeViewFactory;
import com.petkit.android.activities.petkitBleDevice.k3.presenter.K3HomePresenter;
import com.petkit.android.activities.petkitBleDevice.k3.presenter.K3HomePresenter_Factory;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/* JADX INFO: loaded from: classes4.dex */
public final class DaggerK3HomeComponent implements K3HomeComponent {
    public Provider<Application> applicationProvider;
    public Provider<Gson> gsonProvider;
    public Provider<K3HomeModel> k3HomeModelProvider;
    public Provider<K3HomePresenter> k3HomePresenterProvider;
    public Provider<K3HomeContract.Model> provideK3HomeModelProvider;
    public Provider<K3HomeContract.View> provideK3HomeViewProvider;
    public Provider<IRepositoryManager> repositoryManagerProvider;
    public Provider<RxErrorHandler> rxErrorHandlerProvider;

    public DaggerK3HomeComponent(K3HomeModule k3HomeModule, AppComponent appComponent) {
        initialize(k3HomeModule, appComponent);
    }

    public static Builder builder() {
        return new Builder();
    }

    public final void initialize(K3HomeModule k3HomeModule, AppComponent appComponent) {
        this.repositoryManagerProvider = new com_jess_arms_di_component_AppComponent_repositoryManager(appComponent);
        this.gsonProvider = new com_jess_arms_di_component_AppComponent_gson(appComponent);
        com_jess_arms_di_component_AppComponent_application com_jess_arms_di_component_appcomponent_application = new com_jess_arms_di_component_AppComponent_application(appComponent);
        this.applicationProvider = com_jess_arms_di_component_appcomponent_application;
        Provider<K3HomeModel> provider = DoubleCheck.provider(K3HomeModel_Factory.create(this.repositoryManagerProvider, this.gsonProvider, com_jess_arms_di_component_appcomponent_application));
        this.k3HomeModelProvider = provider;
        this.provideK3HomeModelProvider = DoubleCheck.provider(K3HomeModule_ProvideK3HomeModelFactory.create(k3HomeModule, provider));
        this.provideK3HomeViewProvider = DoubleCheck.provider(K3HomeModule_ProvideK3HomeViewFactory.create(k3HomeModule));
        com_jess_arms_di_component_AppComponent_rxErrorHandler com_jess_arms_di_component_appcomponent_rxerrorhandler = new com_jess_arms_di_component_AppComponent_rxErrorHandler(appComponent);
        this.rxErrorHandlerProvider = com_jess_arms_di_component_appcomponent_rxerrorhandler;
        this.k3HomePresenterProvider = DoubleCheck.provider(K3HomePresenter_Factory.create(this.provideK3HomeModelProvider, this.provideK3HomeViewProvider, com_jess_arms_di_component_appcomponent_rxerrorhandler, this.applicationProvider));
    }

    @Override // com.petkit.android.activities.petkitBleDevice.k3.component.K3HomeComponent
    public void inject(K3HomeActivity k3HomeActivity) {
        injectK3HomeActivity(k3HomeActivity);
    }

    @CanIgnoreReturnValue
    public final K3HomeActivity injectK3HomeActivity(K3HomeActivity k3HomeActivity) {
        BaseActivity_MembersInjector.injectMPresenter(k3HomeActivity, this.k3HomePresenterProvider.get());
        return k3HomeActivity;
    }

    public static final class Builder {
        public AppComponent appComponent;
        public K3HomeModule k3HomeModule;

        public Builder() {
        }

        public Builder k3HomeModule(K3HomeModule k3HomeModule) {
            this.k3HomeModule = (K3HomeModule) Preconditions.checkNotNull(k3HomeModule);
            return this;
        }

        public Builder appComponent(AppComponent appComponent) {
            this.appComponent = (AppComponent) Preconditions.checkNotNull(appComponent);
            return this;
        }

        public K3HomeComponent build() {
            Preconditions.checkBuilderRequirement(this.k3HomeModule, K3HomeModule.class);
            Preconditions.checkBuilderRequirement(this.appComponent, AppComponent.class);
            return new DaggerK3HomeComponent(this.k3HomeModule, this.appComponent);
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

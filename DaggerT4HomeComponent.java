package com.petkit.android.activities.petkitBleDevice.t4.component;

import android.app.Application;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.gson.Gson;
import com.jess.arms.base.BaseActivity_MembersInjector;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.IRepositoryManager;
import com.petkit.android.activities.petkitBleDevice.t4.T4HomeActivity;
import com.petkit.android.activities.petkitBleDevice.t4.contract.T4HomeContract;
import com.petkit.android.activities.petkitBleDevice.t4.model.T4HomeModel;
import com.petkit.android.activities.petkitBleDevice.t4.model.T4HomeModel_Factory;
import com.petkit.android.activities.petkitBleDevice.t4.module.T4HomeModule;
import com.petkit.android.activities.petkitBleDevice.t4.module.T4HomeModule_ProvideT4HomeModelFactory;
import com.petkit.android.activities.petkitBleDevice.t4.module.T4HomeModule_ProvideT4HomeViewFactory;
import com.petkit.android.activities.petkitBleDevice.t4.presenter.T4HomePresenter;
import com.petkit.android.activities.petkitBleDevice.t4.presenter.T4HomePresenter_Factory;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/* JADX INFO: loaded from: classes5.dex */
public final class DaggerT4HomeComponent implements T4HomeComponent {
    public Provider<Application> applicationProvider;
    public Provider<Gson> gsonProvider;
    public Provider<T4HomeContract.Model> provideT4HomeModelProvider;
    public Provider<T4HomeContract.View> provideT4HomeViewProvider;
    public Provider<IRepositoryManager> repositoryManagerProvider;
    public Provider<RxErrorHandler> rxErrorHandlerProvider;
    public Provider<T4HomeModel> t4HomeModelProvider;
    public Provider<T4HomePresenter> t4HomePresenterProvider;

    public DaggerT4HomeComponent(T4HomeModule t4HomeModule, AppComponent appComponent) {
        initialize(t4HomeModule, appComponent);
    }

    public static Builder builder() {
        return new Builder();
    }

    public final void initialize(T4HomeModule t4HomeModule, AppComponent appComponent) {
        this.repositoryManagerProvider = new com_jess_arms_di_component_AppComponent_repositoryManager(appComponent);
        this.gsonProvider = new com_jess_arms_di_component_AppComponent_gson(appComponent);
        com_jess_arms_di_component_AppComponent_application com_jess_arms_di_component_appcomponent_application = new com_jess_arms_di_component_AppComponent_application(appComponent);
        this.applicationProvider = com_jess_arms_di_component_appcomponent_application;
        Provider<T4HomeModel> provider = DoubleCheck.provider(T4HomeModel_Factory.create(this.repositoryManagerProvider, this.gsonProvider, com_jess_arms_di_component_appcomponent_application));
        this.t4HomeModelProvider = provider;
        this.provideT4HomeModelProvider = DoubleCheck.provider(T4HomeModule_ProvideT4HomeModelFactory.create(t4HomeModule, provider));
        this.provideT4HomeViewProvider = DoubleCheck.provider(T4HomeModule_ProvideT4HomeViewFactory.create(t4HomeModule));
        com_jess_arms_di_component_AppComponent_rxErrorHandler com_jess_arms_di_component_appcomponent_rxerrorhandler = new com_jess_arms_di_component_AppComponent_rxErrorHandler(appComponent);
        this.rxErrorHandlerProvider = com_jess_arms_di_component_appcomponent_rxerrorhandler;
        this.t4HomePresenterProvider = DoubleCheck.provider(T4HomePresenter_Factory.create(this.provideT4HomeModelProvider, this.provideT4HomeViewProvider, com_jess_arms_di_component_appcomponent_rxerrorhandler, this.applicationProvider));
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t4.component.T4HomeComponent
    public void inject(T4HomeActivity t4HomeActivity) {
        injectT4HomeActivity(t4HomeActivity);
    }

    @CanIgnoreReturnValue
    public final T4HomeActivity injectT4HomeActivity(T4HomeActivity t4HomeActivity) {
        BaseActivity_MembersInjector.injectMPresenter(t4HomeActivity, this.t4HomePresenterProvider.get());
        return t4HomeActivity;
    }

    public static final class Builder {
        public AppComponent appComponent;
        public T4HomeModule t4HomeModule;

        public Builder() {
        }

        public Builder t4HomeModule(T4HomeModule t4HomeModule) {
            this.t4HomeModule = (T4HomeModule) Preconditions.checkNotNull(t4HomeModule);
            return this;
        }

        public Builder appComponent(AppComponent appComponent) {
            this.appComponent = (AppComponent) Preconditions.checkNotNull(appComponent);
            return this;
        }

        public T4HomeComponent build() {
            Preconditions.checkBuilderRequirement(this.t4HomeModule, T4HomeModule.class);
            Preconditions.checkBuilderRequirement(this.appComponent, AppComponent.class);
            return new DaggerT4HomeComponent(this.t4HomeModule, this.appComponent);
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

package com.petkit.android.activities.petkitBleDevice.t4.component;

import android.app.Application;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.gson.Gson;
import com.jess.arms.base.BaseActivity_MembersInjector;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.IRepositoryManager;
import com.petkit.android.activities.petkitBleDevice.t4.T4SettingActivity;
import com.petkit.android.activities.petkitBleDevice.t4.contract.T4SettingContract;
import com.petkit.android.activities.petkitBleDevice.t4.model.T4SettingModel;
import com.petkit.android.activities.petkitBleDevice.t4.model.T4SettingModel_Factory;
import com.petkit.android.activities.petkitBleDevice.t4.module.T4SettingModule;
import com.petkit.android.activities.petkitBleDevice.t4.module.T4SettingModule_ProvideT4SettingModelFactory;
import com.petkit.android.activities.petkitBleDevice.t4.module.T4SettingModule_ProvideT4SettingViewFactory;
import com.petkit.android.activities.petkitBleDevice.t4.presenter.T4SettingPresenter;
import com.petkit.android.activities.petkitBleDevice.t4.presenter.T4SettingPresenter_Factory;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/* JADX INFO: loaded from: classes5.dex */
public final class DaggerT4SettingComponent implements T4SettingComponent {
    public Provider<Application> applicationProvider;
    public Provider<Gson> gsonProvider;
    public Provider<T4SettingContract.Model> provideT4SettingModelProvider;
    public Provider<T4SettingContract.View> provideT4SettingViewProvider;
    public Provider<IRepositoryManager> repositoryManagerProvider;
    public Provider<RxErrorHandler> rxErrorHandlerProvider;
    public Provider<T4SettingModel> t4SettingModelProvider;
    public Provider<T4SettingPresenter> t4SettingPresenterProvider;

    public DaggerT4SettingComponent(T4SettingModule t4SettingModule, AppComponent appComponent) {
        initialize(t4SettingModule, appComponent);
    }

    public static Builder builder() {
        return new Builder();
    }

    public final void initialize(T4SettingModule t4SettingModule, AppComponent appComponent) {
        this.repositoryManagerProvider = new com_jess_arms_di_component_AppComponent_repositoryManager(appComponent);
        this.gsonProvider = new com_jess_arms_di_component_AppComponent_gson(appComponent);
        com_jess_arms_di_component_AppComponent_application com_jess_arms_di_component_appcomponent_application = new com_jess_arms_di_component_AppComponent_application(appComponent);
        this.applicationProvider = com_jess_arms_di_component_appcomponent_application;
        Provider<T4SettingModel> provider = DoubleCheck.provider(T4SettingModel_Factory.create(this.repositoryManagerProvider, this.gsonProvider, com_jess_arms_di_component_appcomponent_application));
        this.t4SettingModelProvider = provider;
        this.provideT4SettingModelProvider = DoubleCheck.provider(T4SettingModule_ProvideT4SettingModelFactory.create(t4SettingModule, provider));
        this.provideT4SettingViewProvider = DoubleCheck.provider(T4SettingModule_ProvideT4SettingViewFactory.create(t4SettingModule));
        com_jess_arms_di_component_AppComponent_rxErrorHandler com_jess_arms_di_component_appcomponent_rxerrorhandler = new com_jess_arms_di_component_AppComponent_rxErrorHandler(appComponent);
        this.rxErrorHandlerProvider = com_jess_arms_di_component_appcomponent_rxerrorhandler;
        this.t4SettingPresenterProvider = DoubleCheck.provider(T4SettingPresenter_Factory.create(this.provideT4SettingModelProvider, this.provideT4SettingViewProvider, com_jess_arms_di_component_appcomponent_rxerrorhandler, this.applicationProvider));
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t4.component.T4SettingComponent
    public void inject(T4SettingActivity t4SettingActivity) {
        injectT4SettingActivity(t4SettingActivity);
    }

    @CanIgnoreReturnValue
    public final T4SettingActivity injectT4SettingActivity(T4SettingActivity t4SettingActivity) {
        BaseActivity_MembersInjector.injectMPresenter(t4SettingActivity, this.t4SettingPresenterProvider.get());
        return t4SettingActivity;
    }

    public static final class Builder {
        public AppComponent appComponent;
        public T4SettingModule t4SettingModule;

        public Builder() {
        }

        public Builder t4SettingModule(T4SettingModule t4SettingModule) {
            this.t4SettingModule = (T4SettingModule) Preconditions.checkNotNull(t4SettingModule);
            return this;
        }

        public Builder appComponent(AppComponent appComponent) {
            this.appComponent = (AppComponent) Preconditions.checkNotNull(appComponent);
            return this;
        }

        public T4SettingComponent build() {
            Preconditions.checkBuilderRequirement(this.t4SettingModule, T4SettingModule.class);
            Preconditions.checkBuilderRequirement(this.appComponent, AppComponent.class);
            return new DaggerT4SettingComponent(this.t4SettingModule, this.appComponent);
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

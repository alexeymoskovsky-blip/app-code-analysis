package com.petkit.android.activities.petkitBleDevice.d4s.component;

import android.app.Application;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.gson.Gson;
import com.jess.arms.base.BaseActivity_MembersInjector;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.IRepositoryManager;
import com.petkit.android.activities.petkitBleDevice.d4s.D4sSettingActivity;
import com.petkit.android.activities.petkitBleDevice.d4s.contract.D4sSettingContract;
import com.petkit.android.activities.petkitBleDevice.d4s.model.D4sSettingModel;
import com.petkit.android.activities.petkitBleDevice.d4s.model.D4sSettingModel_Factory;
import com.petkit.android.activities.petkitBleDevice.d4s.module.D4sSettingModule;
import com.petkit.android.activities.petkitBleDevice.d4s.module.D4sSettingModule_ProvideD4sSettingModelFactory;
import com.petkit.android.activities.petkitBleDevice.d4s.module.D4sSettingModule_ProvideD4sSettingViewFactory;
import com.petkit.android.activities.petkitBleDevice.d4s.presenter.D4sSettingPresenter;
import com.petkit.android.activities.petkitBleDevice.d4s.presenter.D4sSettingPresenter_Factory;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/* JADX INFO: loaded from: classes4.dex */
public final class DaggerD4sSettingComponent implements D4sSettingComponent {
    public Provider<Application> applicationProvider;
    public Provider<D4sSettingModel> d4sSettingModelProvider;
    public Provider<D4sSettingPresenter> d4sSettingPresenterProvider;
    public Provider<Gson> gsonProvider;
    public Provider<D4sSettingContract.Model> provideD4sSettingModelProvider;
    public Provider<D4sSettingContract.View> provideD4sSettingViewProvider;
    public Provider<IRepositoryManager> repositoryManagerProvider;
    public Provider<RxErrorHandler> rxErrorHandlerProvider;

    public DaggerD4sSettingComponent(D4sSettingModule d4sSettingModule, AppComponent appComponent) {
        initialize(d4sSettingModule, appComponent);
    }

    public static Builder builder() {
        return new Builder();
    }

    public final void initialize(D4sSettingModule d4sSettingModule, AppComponent appComponent) {
        this.repositoryManagerProvider = new com_jess_arms_di_component_AppComponent_repositoryManager(appComponent);
        this.gsonProvider = new com_jess_arms_di_component_AppComponent_gson(appComponent);
        com_jess_arms_di_component_AppComponent_application com_jess_arms_di_component_appcomponent_application = new com_jess_arms_di_component_AppComponent_application(appComponent);
        this.applicationProvider = com_jess_arms_di_component_appcomponent_application;
        Provider<D4sSettingModel> provider = DoubleCheck.provider(D4sSettingModel_Factory.create(this.repositoryManagerProvider, this.gsonProvider, com_jess_arms_di_component_appcomponent_application));
        this.d4sSettingModelProvider = provider;
        this.provideD4sSettingModelProvider = DoubleCheck.provider(D4sSettingModule_ProvideD4sSettingModelFactory.create(d4sSettingModule, provider));
        this.provideD4sSettingViewProvider = DoubleCheck.provider(D4sSettingModule_ProvideD4sSettingViewFactory.create(d4sSettingModule));
        com_jess_arms_di_component_AppComponent_rxErrorHandler com_jess_arms_di_component_appcomponent_rxerrorhandler = new com_jess_arms_di_component_AppComponent_rxErrorHandler(appComponent);
        this.rxErrorHandlerProvider = com_jess_arms_di_component_appcomponent_rxerrorhandler;
        this.d4sSettingPresenterProvider = DoubleCheck.provider(D4sSettingPresenter_Factory.create(this.provideD4sSettingModelProvider, this.provideD4sSettingViewProvider, com_jess_arms_di_component_appcomponent_rxerrorhandler, this.applicationProvider));
    }

    @Override // com.petkit.android.activities.petkitBleDevice.d4s.component.D4sSettingComponent
    public void inject(D4sSettingActivity d4sSettingActivity) {
        injectD4sSettingActivity(d4sSettingActivity);
    }

    @CanIgnoreReturnValue
    public final D4sSettingActivity injectD4sSettingActivity(D4sSettingActivity d4sSettingActivity) {
        BaseActivity_MembersInjector.injectMPresenter(d4sSettingActivity, this.d4sSettingPresenterProvider.get());
        return d4sSettingActivity;
    }

    public static final class Builder {
        public AppComponent appComponent;
        public D4sSettingModule d4sSettingModule;

        public Builder() {
        }

        public Builder d4sSettingModule(D4sSettingModule d4sSettingModule) {
            this.d4sSettingModule = (D4sSettingModule) Preconditions.checkNotNull(d4sSettingModule);
            return this;
        }

        public Builder appComponent(AppComponent appComponent) {
            this.appComponent = (AppComponent) Preconditions.checkNotNull(appComponent);
            return this;
        }

        public D4sSettingComponent build() {
            Preconditions.checkBuilderRequirement(this.d4sSettingModule, D4sSettingModule.class);
            Preconditions.checkBuilderRequirement(this.appComponent, AppComponent.class);
            return new DaggerD4sSettingComponent(this.d4sSettingModule, this.appComponent);
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

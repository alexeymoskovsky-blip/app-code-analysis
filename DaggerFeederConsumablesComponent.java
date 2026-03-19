package com.petkit.android.activities.petkitBleDevice.d3.component;

import android.app.Application;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.gson.Gson;
import com.jess.arms.base.BaseActivity_MembersInjector;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.IRepositoryManager;
import com.petkit.android.activities.petkitBleDevice.d3.FeederConsumablesActivity;
import com.petkit.android.activities.petkitBleDevice.d3.contract.FeederConsumablesContract;
import com.petkit.android.activities.petkitBleDevice.d3.model.FeederConsumablesModel;
import com.petkit.android.activities.petkitBleDevice.d3.model.FeederConsumablesModel_Factory;
import com.petkit.android.activities.petkitBleDevice.d3.module.FeederConsumablesModule;
import com.petkit.android.activities.petkitBleDevice.d3.module.FeederConsumablesModule_ProvideFeederConsumablesModelFactory;
import com.petkit.android.activities.petkitBleDevice.d3.module.FeederConsumablesModule_ProvideFeederConsumablesViewFactory;
import com.petkit.android.activities.petkitBleDevice.d3.presenter.FeederConsumablesPresenter;
import com.petkit.android.activities.petkitBleDevice.d3.presenter.FeederConsumablesPresenter_Factory;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/* JADX INFO: loaded from: classes4.dex */
public final class DaggerFeederConsumablesComponent implements FeederConsumablesComponent {
    public Provider<Application> applicationProvider;
    public Provider<FeederConsumablesModel> feederConsumablesModelProvider;
    public Provider<FeederConsumablesPresenter> feederConsumablesPresenterProvider;
    public Provider<Gson> gsonProvider;
    public Provider<FeederConsumablesContract.Model> provideFeederConsumablesModelProvider;
    public Provider<FeederConsumablesContract.View> provideFeederConsumablesViewProvider;
    public Provider<IRepositoryManager> repositoryManagerProvider;
    public Provider<RxErrorHandler> rxErrorHandlerProvider;

    public DaggerFeederConsumablesComponent(FeederConsumablesModule feederConsumablesModule, AppComponent appComponent) {
        initialize(feederConsumablesModule, appComponent);
    }

    public static Builder builder() {
        return new Builder();
    }

    public final void initialize(FeederConsumablesModule feederConsumablesModule, AppComponent appComponent) {
        this.repositoryManagerProvider = new com_jess_arms_di_component_AppComponent_repositoryManager(appComponent);
        this.gsonProvider = new com_jess_arms_di_component_AppComponent_gson(appComponent);
        com_jess_arms_di_component_AppComponent_application com_jess_arms_di_component_appcomponent_application = new com_jess_arms_di_component_AppComponent_application(appComponent);
        this.applicationProvider = com_jess_arms_di_component_appcomponent_application;
        Provider<FeederConsumablesModel> provider = DoubleCheck.provider(FeederConsumablesModel_Factory.create(this.repositoryManagerProvider, this.gsonProvider, com_jess_arms_di_component_appcomponent_application));
        this.feederConsumablesModelProvider = provider;
        this.provideFeederConsumablesModelProvider = DoubleCheck.provider(FeederConsumablesModule_ProvideFeederConsumablesModelFactory.create(feederConsumablesModule, provider));
        this.provideFeederConsumablesViewProvider = DoubleCheck.provider(FeederConsumablesModule_ProvideFeederConsumablesViewFactory.create(feederConsumablesModule));
        com_jess_arms_di_component_AppComponent_rxErrorHandler com_jess_arms_di_component_appcomponent_rxerrorhandler = new com_jess_arms_di_component_AppComponent_rxErrorHandler(appComponent);
        this.rxErrorHandlerProvider = com_jess_arms_di_component_appcomponent_rxerrorhandler;
        this.feederConsumablesPresenterProvider = DoubleCheck.provider(FeederConsumablesPresenter_Factory.create(this.provideFeederConsumablesModelProvider, this.provideFeederConsumablesViewProvider, com_jess_arms_di_component_appcomponent_rxerrorhandler, this.applicationProvider));
    }

    @Override // com.petkit.android.activities.petkitBleDevice.d3.component.FeederConsumablesComponent
    public void inject(FeederConsumablesActivity feederConsumablesActivity) {
        injectFeederConsumablesActivity(feederConsumablesActivity);
    }

    @CanIgnoreReturnValue
    public final FeederConsumablesActivity injectFeederConsumablesActivity(FeederConsumablesActivity feederConsumablesActivity) {
        BaseActivity_MembersInjector.injectMPresenter(feederConsumablesActivity, this.feederConsumablesPresenterProvider.get());
        return feederConsumablesActivity;
    }

    public static final class Builder {
        public AppComponent appComponent;
        public FeederConsumablesModule feederConsumablesModule;

        public Builder() {
        }

        public Builder feederConsumablesModule(FeederConsumablesModule feederConsumablesModule) {
            this.feederConsumablesModule = (FeederConsumablesModule) Preconditions.checkNotNull(feederConsumablesModule);
            return this;
        }

        public Builder appComponent(AppComponent appComponent) {
            this.appComponent = (AppComponent) Preconditions.checkNotNull(appComponent);
            return this;
        }

        public FeederConsumablesComponent build() {
            Preconditions.checkBuilderRequirement(this.feederConsumablesModule, FeederConsumablesModule.class);
            Preconditions.checkBuilderRequirement(this.appComponent, AppComponent.class);
            return new DaggerFeederConsumablesComponent(this.feederConsumablesModule, this.appComponent);
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

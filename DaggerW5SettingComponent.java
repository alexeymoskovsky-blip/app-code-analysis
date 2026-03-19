package com.petkit.android.activities.petkitBleDevice.w5.component;

import android.app.Application;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.gson.Gson;
import com.jess.arms.base.BaseActivity_MembersInjector;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.IRepositoryManager;
import com.petkit.android.activities.petkitBleDevice.w5.W5SettingActivity;
import com.petkit.android.activities.petkitBleDevice.w5.contract.W5SettingContract;
import com.petkit.android.activities.petkitBleDevice.w5.model.W5SettingModel;
import com.petkit.android.activities.petkitBleDevice.w5.model.W5SettingModel_Factory;
import com.petkit.android.activities.petkitBleDevice.w5.module.W5SettingModule;
import com.petkit.android.activities.petkitBleDevice.w5.module.W5SettingModule_ProvideW5SettingModelFactory;
import com.petkit.android.activities.petkitBleDevice.w5.module.W5SettingModule_ProvideW5SettingViewFactory;
import com.petkit.android.activities.petkitBleDevice.w5.presenter.W5SettingPresenter;
import com.petkit.android.activities.petkitBleDevice.w5.presenter.W5SettingPresenter_Factory;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/* JADX INFO: loaded from: classes5.dex */
public final class DaggerW5SettingComponent implements W5SettingComponent {
    public Provider<Application> applicationProvider;
    public Provider<Gson> gsonProvider;
    public Provider<W5SettingContract.Model> provideW5SettingModelProvider;
    public Provider<W5SettingContract.View> provideW5SettingViewProvider;
    public Provider<IRepositoryManager> repositoryManagerProvider;
    public Provider<RxErrorHandler> rxErrorHandlerProvider;
    public Provider<W5SettingModel> w5SettingModelProvider;
    public Provider<W5SettingPresenter> w5SettingPresenterProvider;

    public DaggerW5SettingComponent(W5SettingModule w5SettingModule, AppComponent appComponent) {
        initialize(w5SettingModule, appComponent);
    }

    public static Builder builder() {
        return new Builder();
    }

    public final void initialize(W5SettingModule w5SettingModule, AppComponent appComponent) {
        this.repositoryManagerProvider = new com_jess_arms_di_component_AppComponent_repositoryManager(appComponent);
        this.gsonProvider = new com_jess_arms_di_component_AppComponent_gson(appComponent);
        com_jess_arms_di_component_AppComponent_application com_jess_arms_di_component_appcomponent_application = new com_jess_arms_di_component_AppComponent_application(appComponent);
        this.applicationProvider = com_jess_arms_di_component_appcomponent_application;
        Provider<W5SettingModel> provider = DoubleCheck.provider(W5SettingModel_Factory.create(this.repositoryManagerProvider, this.gsonProvider, com_jess_arms_di_component_appcomponent_application));
        this.w5SettingModelProvider = provider;
        this.provideW5SettingModelProvider = DoubleCheck.provider(W5SettingModule_ProvideW5SettingModelFactory.create(w5SettingModule, provider));
        this.provideW5SettingViewProvider = DoubleCheck.provider(W5SettingModule_ProvideW5SettingViewFactory.create(w5SettingModule));
        com_jess_arms_di_component_AppComponent_rxErrorHandler com_jess_arms_di_component_appcomponent_rxerrorhandler = new com_jess_arms_di_component_AppComponent_rxErrorHandler(appComponent);
        this.rxErrorHandlerProvider = com_jess_arms_di_component_appcomponent_rxerrorhandler;
        this.w5SettingPresenterProvider = DoubleCheck.provider(W5SettingPresenter_Factory.create(this.provideW5SettingModelProvider, this.provideW5SettingViewProvider, com_jess_arms_di_component_appcomponent_rxerrorhandler, this.applicationProvider));
    }

    @Override // com.petkit.android.activities.petkitBleDevice.w5.component.W5SettingComponent
    public void inject(W5SettingActivity w5SettingActivity) {
        injectW5SettingActivity(w5SettingActivity);
    }

    @CanIgnoreReturnValue
    public final W5SettingActivity injectW5SettingActivity(W5SettingActivity w5SettingActivity) {
        BaseActivity_MembersInjector.injectMPresenter(w5SettingActivity, this.w5SettingPresenterProvider.get());
        return w5SettingActivity;
    }

    public static final class Builder {
        public AppComponent appComponent;
        public W5SettingModule w5SettingModule;

        public Builder() {
        }

        public Builder w5SettingModule(W5SettingModule w5SettingModule) {
            this.w5SettingModule = (W5SettingModule) Preconditions.checkNotNull(w5SettingModule);
            return this;
        }

        public Builder appComponent(AppComponent appComponent) {
            this.appComponent = (AppComponent) Preconditions.checkNotNull(appComponent);
            return this;
        }

        public W5SettingComponent build() {
            Preconditions.checkBuilderRequirement(this.w5SettingModule, W5SettingModule.class);
            Preconditions.checkBuilderRequirement(this.appComponent, AppComponent.class);
            return new DaggerW5SettingComponent(this.w5SettingModule, this.appComponent);
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

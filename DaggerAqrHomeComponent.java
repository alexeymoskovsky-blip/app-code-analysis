package com.petkit.android.activities.petkitBleDevice.aqr.component;

import android.app.Application;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.gson.Gson;
import com.jess.arms.base.BaseActivity_MembersInjector;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.IRepositoryManager;
import com.petkit.android.activities.petkitBleDevice.aqr.AqrHomeActivity;
import com.petkit.android.activities.petkitBleDevice.aqr.contract.AqrHomeContract;
import com.petkit.android.activities.petkitBleDevice.aqr.model.AqrHomeModel;
import com.petkit.android.activities.petkitBleDevice.aqr.model.AqrHomeModel_Factory;
import com.petkit.android.activities.petkitBleDevice.aqr.module.AqrHomeModule;
import com.petkit.android.activities.petkitBleDevice.aqr.module.AqrHomeModule_ProvideAqrHomeModelFactory;
import com.petkit.android.activities.petkitBleDevice.aqr.module.AqrHomeModule_ProvideAqrHomeViewFactory;
import com.petkit.android.activities.petkitBleDevice.aqr.presenter.AqrHomePresenter;
import com.petkit.android.activities.petkitBleDevice.aqr.presenter.AqrHomePresenter_Factory;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/* JADX INFO: loaded from: classes4.dex */
public final class DaggerAqrHomeComponent implements AqrHomeComponent {
    public Provider<Application> applicationProvider;
    public Provider<AqrHomeModel> aqrHomeModelProvider;
    public Provider<AqrHomePresenter> aqrHomePresenterProvider;
    public Provider<Gson> gsonProvider;
    public Provider<AqrHomeContract.Model> provideAqrHomeModelProvider;
    public Provider<AqrHomeContract.View> provideAqrHomeViewProvider;
    public Provider<IRepositoryManager> repositoryManagerProvider;
    public Provider<RxErrorHandler> rxErrorHandlerProvider;

    public DaggerAqrHomeComponent(AqrHomeModule aqrHomeModule, AppComponent appComponent) {
        initialize(aqrHomeModule, appComponent);
    }

    public static Builder builder() {
        return new Builder();
    }

    public final void initialize(AqrHomeModule aqrHomeModule, AppComponent appComponent) {
        this.repositoryManagerProvider = new com_jess_arms_di_component_AppComponent_repositoryManager(appComponent);
        this.gsonProvider = new com_jess_arms_di_component_AppComponent_gson(appComponent);
        com_jess_arms_di_component_AppComponent_application com_jess_arms_di_component_appcomponent_application = new com_jess_arms_di_component_AppComponent_application(appComponent);
        this.applicationProvider = com_jess_arms_di_component_appcomponent_application;
        Provider<AqrHomeModel> provider = DoubleCheck.provider(AqrHomeModel_Factory.create(this.repositoryManagerProvider, this.gsonProvider, com_jess_arms_di_component_appcomponent_application));
        this.aqrHomeModelProvider = provider;
        this.provideAqrHomeModelProvider = DoubleCheck.provider(AqrHomeModule_ProvideAqrHomeModelFactory.create(aqrHomeModule, provider));
        this.provideAqrHomeViewProvider = DoubleCheck.provider(AqrHomeModule_ProvideAqrHomeViewFactory.create(aqrHomeModule));
        com_jess_arms_di_component_AppComponent_rxErrorHandler com_jess_arms_di_component_appcomponent_rxerrorhandler = new com_jess_arms_di_component_AppComponent_rxErrorHandler(appComponent);
        this.rxErrorHandlerProvider = com_jess_arms_di_component_appcomponent_rxerrorhandler;
        this.aqrHomePresenterProvider = DoubleCheck.provider(AqrHomePresenter_Factory.create(this.provideAqrHomeModelProvider, this.provideAqrHomeViewProvider, com_jess_arms_di_component_appcomponent_rxerrorhandler, this.applicationProvider));
    }

    @Override // com.petkit.android.activities.petkitBleDevice.aqr.component.AqrHomeComponent
    public void inject(AqrHomeActivity aqrHomeActivity) {
        injectAqrHomeActivity(aqrHomeActivity);
    }

    @CanIgnoreReturnValue
    public final AqrHomeActivity injectAqrHomeActivity(AqrHomeActivity aqrHomeActivity) {
        BaseActivity_MembersInjector.injectMPresenter(aqrHomeActivity, this.aqrHomePresenterProvider.get());
        return aqrHomeActivity;
    }

    public static final class Builder {
        public AppComponent appComponent;
        public AqrHomeModule aqrHomeModule;

        public Builder() {
        }

        public Builder aqrHomeModule(AqrHomeModule aqrHomeModule) {
            this.aqrHomeModule = (AqrHomeModule) Preconditions.checkNotNull(aqrHomeModule);
            return this;
        }

        public Builder appComponent(AppComponent appComponent) {
            this.appComponent = (AppComponent) Preconditions.checkNotNull(appComponent);
            return this;
        }

        public AqrHomeComponent build() {
            Preconditions.checkBuilderRequirement(this.aqrHomeModule, AqrHomeModule.class);
            Preconditions.checkBuilderRequirement(this.appComponent, AppComponent.class);
            return new DaggerAqrHomeComponent(this.aqrHomeModule, this.appComponent);
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

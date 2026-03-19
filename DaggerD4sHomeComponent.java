package com.petkit.android.activities.petkitBleDevice.d4s.component;

import android.app.Application;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.gson.Gson;
import com.jess.arms.base.BaseActivity_MembersInjector;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.IRepositoryManager;
import com.petkit.android.activities.petkitBleDevice.d4s.D4sHomeActivity;
import com.petkit.android.activities.petkitBleDevice.d4s.contract.D4sHomeContract;
import com.petkit.android.activities.petkitBleDevice.d4s.model.D4sHomeModel;
import com.petkit.android.activities.petkitBleDevice.d4s.model.D4sHomeModel_Factory;
import com.petkit.android.activities.petkitBleDevice.d4s.module.D4sHomeModule;
import com.petkit.android.activities.petkitBleDevice.d4s.module.D4sHomeModule_ProvideD4sHomeActivityModelFactory;
import com.petkit.android.activities.petkitBleDevice.d4s.module.D4sHomeModule_ProvideD4sHomeActivityViewFactory;
import com.petkit.android.activities.petkitBleDevice.d4s.presenter.D4sHomePresenter;
import com.petkit.android.activities.petkitBleDevice.d4s.presenter.D4sHomePresenter_Factory;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/* JADX INFO: loaded from: classes4.dex */
public final class DaggerD4sHomeComponent implements D4sHomeComponent {
    public Provider<Application> applicationProvider;
    public Provider<D4sHomeModel> d4sHomeModelProvider;
    public Provider<D4sHomePresenter> d4sHomePresenterProvider;
    public Provider<Gson> gsonProvider;
    public Provider<D4sHomeContract.Model> provideD4sHomeActivityModelProvider;
    public Provider<D4sHomeContract.View> provideD4sHomeActivityViewProvider;
    public Provider<IRepositoryManager> repositoryManagerProvider;
    public Provider<RxErrorHandler> rxErrorHandlerProvider;

    public DaggerD4sHomeComponent(D4sHomeModule d4sHomeModule, AppComponent appComponent) {
        initialize(d4sHomeModule, appComponent);
    }

    public static Builder builder() {
        return new Builder();
    }

    public final void initialize(D4sHomeModule d4sHomeModule, AppComponent appComponent) {
        this.repositoryManagerProvider = new com_jess_arms_di_component_AppComponent_repositoryManager(appComponent);
        this.gsonProvider = new com_jess_arms_di_component_AppComponent_gson(appComponent);
        com_jess_arms_di_component_AppComponent_application com_jess_arms_di_component_appcomponent_application = new com_jess_arms_di_component_AppComponent_application(appComponent);
        this.applicationProvider = com_jess_arms_di_component_appcomponent_application;
        Provider<D4sHomeModel> provider = DoubleCheck.provider(D4sHomeModel_Factory.create(this.repositoryManagerProvider, this.gsonProvider, com_jess_arms_di_component_appcomponent_application));
        this.d4sHomeModelProvider = provider;
        this.provideD4sHomeActivityModelProvider = DoubleCheck.provider(D4sHomeModule_ProvideD4sHomeActivityModelFactory.create(d4sHomeModule, provider));
        this.provideD4sHomeActivityViewProvider = DoubleCheck.provider(D4sHomeModule_ProvideD4sHomeActivityViewFactory.create(d4sHomeModule));
        com_jess_arms_di_component_AppComponent_rxErrorHandler com_jess_arms_di_component_appcomponent_rxerrorhandler = new com_jess_arms_di_component_AppComponent_rxErrorHandler(appComponent);
        this.rxErrorHandlerProvider = com_jess_arms_di_component_appcomponent_rxerrorhandler;
        this.d4sHomePresenterProvider = DoubleCheck.provider(D4sHomePresenter_Factory.create(this.provideD4sHomeActivityModelProvider, this.provideD4sHomeActivityViewProvider, com_jess_arms_di_component_appcomponent_rxerrorhandler, this.applicationProvider));
    }

    @Override // com.petkit.android.activities.petkitBleDevice.d4s.component.D4sHomeComponent
    public void inject(D4sHomeActivity d4sHomeActivity) {
        injectD4sHomeActivity(d4sHomeActivity);
    }

    @CanIgnoreReturnValue
    public final D4sHomeActivity injectD4sHomeActivity(D4sHomeActivity d4sHomeActivity) {
        BaseActivity_MembersInjector.injectMPresenter(d4sHomeActivity, this.d4sHomePresenterProvider.get());
        return d4sHomeActivity;
    }

    public static final class Builder {
        public AppComponent appComponent;
        public D4sHomeModule d4sHomeModule;

        public Builder() {
        }

        public Builder d4sHomeModule(D4sHomeModule d4sHomeModule) {
            this.d4sHomeModule = (D4sHomeModule) Preconditions.checkNotNull(d4sHomeModule);
            return this;
        }

        public Builder appComponent(AppComponent appComponent) {
            this.appComponent = (AppComponent) Preconditions.checkNotNull(appComponent);
            return this;
        }

        public D4sHomeComponent build() {
            Preconditions.checkBuilderRequirement(this.d4sHomeModule, D4sHomeModule.class);
            Preconditions.checkBuilderRequirement(this.appComponent, AppComponent.class);
            return new DaggerD4sHomeComponent(this.d4sHomeModule, this.appComponent);
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

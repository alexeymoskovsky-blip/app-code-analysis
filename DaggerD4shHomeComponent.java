package com.petkit.android.activities.petkitBleDevice.d4sh.component;

import android.app.Application;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.gson.Gson;
import com.jess.arms.base.BaseActivity_MembersInjector;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.AppManager;
import com.jess.arms.integration.IRepositoryManager;
import com.petkit.android.activities.petkitBleDevice.d4sh.D4shHomeActivity;
import com.petkit.android.activities.petkitBleDevice.d4sh.contract.D4shHomeContract;
import com.petkit.android.activities.petkitBleDevice.d4sh.model.D4shHomeModel;
import com.petkit.android.activities.petkitBleDevice.d4sh.model.D4shHomeModel_Factory;
import com.petkit.android.activities.petkitBleDevice.d4sh.module.D4shHomeModule;
import com.petkit.android.activities.petkitBleDevice.d4sh.module.D4shHomeModule_ProvideD4shHomeActivityModelFactory;
import com.petkit.android.activities.petkitBleDevice.d4sh.module.D4shHomeModule_ProvideD4shHomeActivityViewFactory;
import com.petkit.android.activities.petkitBleDevice.d4sh.presenter.D4shHomePresenter;
import com.petkit.android.activities.petkitBleDevice.d4sh.presenter.D4shHomePresenter_Factory;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/* JADX INFO: loaded from: classes4.dex */
public final class DaggerD4shHomeComponent implements D4shHomeComponent {
    public Provider<AppManager> appManagerProvider;
    public Provider<Application> applicationProvider;
    public Provider<D4shHomeModel> d4shHomeModelProvider;
    public Provider<D4shHomePresenter> d4shHomePresenterProvider;
    public Provider<Gson> gsonProvider;
    public Provider<D4shHomeContract.Model> provideD4shHomeActivityModelProvider;
    public Provider<D4shHomeContract.View> provideD4shHomeActivityViewProvider;
    public Provider<IRepositoryManager> repositoryManagerProvider;
    public Provider<RxErrorHandler> rxErrorHandlerProvider;

    public DaggerD4shHomeComponent(D4shHomeModule d4shHomeModule, AppComponent appComponent) {
        initialize(d4shHomeModule, appComponent);
    }

    public static Builder builder() {
        return new Builder();
    }

    public final void initialize(D4shHomeModule d4shHomeModule, AppComponent appComponent) {
        this.repositoryManagerProvider = new com_jess_arms_di_component_AppComponent_repositoryManager(appComponent);
        this.gsonProvider = new com_jess_arms_di_component_AppComponent_gson(appComponent);
        com_jess_arms_di_component_AppComponent_application com_jess_arms_di_component_appcomponent_application = new com_jess_arms_di_component_AppComponent_application(appComponent);
        this.applicationProvider = com_jess_arms_di_component_appcomponent_application;
        Provider<D4shHomeModel> provider = DoubleCheck.provider(D4shHomeModel_Factory.create(this.repositoryManagerProvider, this.gsonProvider, com_jess_arms_di_component_appcomponent_application));
        this.d4shHomeModelProvider = provider;
        this.provideD4shHomeActivityModelProvider = DoubleCheck.provider(D4shHomeModule_ProvideD4shHomeActivityModelFactory.create(d4shHomeModule, provider));
        this.provideD4shHomeActivityViewProvider = DoubleCheck.provider(D4shHomeModule_ProvideD4shHomeActivityViewFactory.create(d4shHomeModule));
        this.rxErrorHandlerProvider = new com_jess_arms_di_component_AppComponent_rxErrorHandler(appComponent);
        com_jess_arms_di_component_AppComponent_appManager com_jess_arms_di_component_appcomponent_appmanager = new com_jess_arms_di_component_AppComponent_appManager(appComponent);
        this.appManagerProvider = com_jess_arms_di_component_appcomponent_appmanager;
        this.d4shHomePresenterProvider = DoubleCheck.provider(D4shHomePresenter_Factory.create(this.provideD4shHomeActivityModelProvider, this.provideD4shHomeActivityViewProvider, this.rxErrorHandlerProvider, this.applicationProvider, com_jess_arms_di_component_appcomponent_appmanager));
    }

    @Override // com.petkit.android.activities.petkitBleDevice.d4sh.component.D4shHomeComponent
    public void inject(D4shHomeActivity d4shHomeActivity) {
        injectD4shHomeActivity(d4shHomeActivity);
    }

    @CanIgnoreReturnValue
    public final D4shHomeActivity injectD4shHomeActivity(D4shHomeActivity d4shHomeActivity) {
        BaseActivity_MembersInjector.injectMPresenter(d4shHomeActivity, this.d4shHomePresenterProvider.get());
        return d4shHomeActivity;
    }

    public static final class Builder {
        public AppComponent appComponent;
        public D4shHomeModule d4shHomeModule;

        public Builder() {
        }

        public Builder d4shHomeModule(D4shHomeModule d4shHomeModule) {
            this.d4shHomeModule = (D4shHomeModule) Preconditions.checkNotNull(d4shHomeModule);
            return this;
        }

        public Builder appComponent(AppComponent appComponent) {
            this.appComponent = (AppComponent) Preconditions.checkNotNull(appComponent);
            return this;
        }

        public D4shHomeComponent build() {
            Preconditions.checkBuilderRequirement(this.d4shHomeModule, D4shHomeModule.class);
            Preconditions.checkBuilderRequirement(this.appComponent, AppComponent.class);
            return new DaggerD4shHomeComponent(this.d4shHomeModule, this.appComponent);
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

    public static class com_jess_arms_di_component_AppComponent_appManager implements Provider<AppManager> {
        public final AppComponent appComponent;

        public com_jess_arms_di_component_AppComponent_appManager(AppComponent appComponent) {
            this.appComponent = appComponent;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // javax.inject.Provider
        public AppManager get() {
            return (AppManager) Preconditions.checkNotNull(this.appComponent.appManager(), "Cannot return null from a non-@Nullable component method");
        }
    }
}

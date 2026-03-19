package com.petkit.android.activities.petkitBleDevice.d4sh.component;

import android.app.Application;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.gson.Gson;
import com.jess.arms.base.BaseActivity_MembersInjector;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.AppManager;
import com.jess.arms.integration.IRepositoryManager;
import com.petkit.android.activities.petkitBleDevice.d4sh.D4shVideoRecordActivity;
import com.petkit.android.activities.petkitBleDevice.d4sh.contract.D4shVideoRecordContract;
import com.petkit.android.activities.petkitBleDevice.d4sh.model.D4shVideoRecordModel;
import com.petkit.android.activities.petkitBleDevice.d4sh.model.D4shVideoRecordModel_Factory;
import com.petkit.android.activities.petkitBleDevice.d4sh.module.D4shVideoRecordModule;
import com.petkit.android.activities.petkitBleDevice.d4sh.module.D4shVideoRecordModule_ProvideD4shVideoRecordActivityModelFactory;
import com.petkit.android.activities.petkitBleDevice.d4sh.module.D4shVideoRecordModule_ProvideD4shVideoRecordActivityViewFactory;
import com.petkit.android.activities.petkitBleDevice.d4sh.presenter.D4shVideoRecordPresenter;
import com.petkit.android.activities.petkitBleDevice.d4sh.presenter.D4shVideoRecordPresenter_Factory;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/* JADX INFO: loaded from: classes4.dex */
public final class DaggerD4shVideoRecordComponent implements D4shVideoRecordComponent {
    public Provider<AppManager> appManagerProvider;
    public Provider<Application> applicationProvider;
    public Provider<D4shVideoRecordModel> d4shVideoRecordModelProvider;
    public Provider<D4shVideoRecordPresenter> d4shVideoRecordPresenterProvider;
    public Provider<Gson> gsonProvider;
    public Provider<D4shVideoRecordContract.Model> provideD4shVideoRecordActivityModelProvider;
    public Provider<D4shVideoRecordContract.View> provideD4shVideoRecordActivityViewProvider;
    public Provider<IRepositoryManager> repositoryManagerProvider;
    public Provider<RxErrorHandler> rxErrorHandlerProvider;

    public DaggerD4shVideoRecordComponent(D4shVideoRecordModule d4shVideoRecordModule, AppComponent appComponent) {
        initialize(d4shVideoRecordModule, appComponent);
    }

    public static Builder builder() {
        return new Builder();
    }

    public final void initialize(D4shVideoRecordModule d4shVideoRecordModule, AppComponent appComponent) {
        this.repositoryManagerProvider = new com_jess_arms_di_component_AppComponent_repositoryManager(appComponent);
        this.gsonProvider = new com_jess_arms_di_component_AppComponent_gson(appComponent);
        com_jess_arms_di_component_AppComponent_application com_jess_arms_di_component_appcomponent_application = new com_jess_arms_di_component_AppComponent_application(appComponent);
        this.applicationProvider = com_jess_arms_di_component_appcomponent_application;
        Provider<D4shVideoRecordModel> provider = DoubleCheck.provider(D4shVideoRecordModel_Factory.create(this.repositoryManagerProvider, this.gsonProvider, com_jess_arms_di_component_appcomponent_application));
        this.d4shVideoRecordModelProvider = provider;
        this.provideD4shVideoRecordActivityModelProvider = DoubleCheck.provider(D4shVideoRecordModule_ProvideD4shVideoRecordActivityModelFactory.create(d4shVideoRecordModule, provider));
        this.provideD4shVideoRecordActivityViewProvider = DoubleCheck.provider(D4shVideoRecordModule_ProvideD4shVideoRecordActivityViewFactory.create(d4shVideoRecordModule));
        this.rxErrorHandlerProvider = new com_jess_arms_di_component_AppComponent_rxErrorHandler(appComponent);
        com_jess_arms_di_component_AppComponent_appManager com_jess_arms_di_component_appcomponent_appmanager = new com_jess_arms_di_component_AppComponent_appManager(appComponent);
        this.appManagerProvider = com_jess_arms_di_component_appcomponent_appmanager;
        this.d4shVideoRecordPresenterProvider = DoubleCheck.provider(D4shVideoRecordPresenter_Factory.create(this.provideD4shVideoRecordActivityModelProvider, this.provideD4shVideoRecordActivityViewProvider, this.rxErrorHandlerProvider, this.applicationProvider, com_jess_arms_di_component_appcomponent_appmanager));
    }

    @Override // com.petkit.android.activities.petkitBleDevice.d4sh.component.D4shVideoRecordComponent
    public void inject(D4shVideoRecordActivity d4shVideoRecordActivity) {
        injectD4shVideoRecordActivity(d4shVideoRecordActivity);
    }

    @CanIgnoreReturnValue
    public final D4shVideoRecordActivity injectD4shVideoRecordActivity(D4shVideoRecordActivity d4shVideoRecordActivity) {
        BaseActivity_MembersInjector.injectMPresenter(d4shVideoRecordActivity, this.d4shVideoRecordPresenterProvider.get());
        return d4shVideoRecordActivity;
    }

    public static final class Builder {
        public AppComponent appComponent;
        public D4shVideoRecordModule d4shVideoRecordModule;

        public Builder() {
        }

        public Builder d4shVideoRecordModule(D4shVideoRecordModule d4shVideoRecordModule) {
            this.d4shVideoRecordModule = (D4shVideoRecordModule) Preconditions.checkNotNull(d4shVideoRecordModule);
            return this;
        }

        public Builder appComponent(AppComponent appComponent) {
            this.appComponent = (AppComponent) Preconditions.checkNotNull(appComponent);
            return this;
        }

        public D4shVideoRecordComponent build() {
            Preconditions.checkBuilderRequirement(this.d4shVideoRecordModule, D4shVideoRecordModule.class);
            Preconditions.checkBuilderRequirement(this.appComponent, AppComponent.class);
            return new DaggerD4shVideoRecordComponent(this.d4shVideoRecordModule, this.appComponent);
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

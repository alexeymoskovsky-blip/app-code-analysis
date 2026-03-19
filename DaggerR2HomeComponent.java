package com.petkit.android.activities.petkitBleDevice.r2.component;

import android.app.Application;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.gson.Gson;
import com.jess.arms.base.BaseActivity_MembersInjector;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.IRepositoryManager;
import com.petkit.android.activities.petkitBleDevice.r2.R2HomeActivity;
import com.petkit.android.activities.petkitBleDevice.r2.contract.R2HomeContract;
import com.petkit.android.activities.petkitBleDevice.r2.model.R2HomeModel;
import com.petkit.android.activities.petkitBleDevice.r2.model.R2HomeModel_Factory;
import com.petkit.android.activities.petkitBleDevice.r2.module.R2HomeModule;
import com.petkit.android.activities.petkitBleDevice.r2.module.R2HomeModule_ProvideR2HomeModelFactory;
import com.petkit.android.activities.petkitBleDevice.r2.module.R2HomeModule_ProvideR2HomeViewFactory;
import com.petkit.android.activities.petkitBleDevice.r2.presenter.R2HomePresenter;
import com.petkit.android.activities.petkitBleDevice.r2.presenter.R2HomePresenter_Factory;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/* JADX INFO: loaded from: classes5.dex */
public final class DaggerR2HomeComponent implements R2HomeComponent {
    public Provider<Application> applicationProvider;
    public Provider<Gson> gsonProvider;
    public Provider<R2HomeContract.Model> provideR2HomeModelProvider;
    public Provider<R2HomeContract.View> provideR2HomeViewProvider;
    public Provider<R2HomeModel> r2HomeModelProvider;
    public Provider<R2HomePresenter> r2HomePresenterProvider;
    public Provider<IRepositoryManager> repositoryManagerProvider;
    public Provider<RxErrorHandler> rxErrorHandlerProvider;

    public DaggerR2HomeComponent(R2HomeModule r2HomeModule, AppComponent appComponent) {
        initialize(r2HomeModule, appComponent);
    }

    public static Builder builder() {
        return new Builder();
    }

    public final void initialize(R2HomeModule r2HomeModule, AppComponent appComponent) {
        this.repositoryManagerProvider = new com_jess_arms_di_component_AppComponent_repositoryManager(appComponent);
        this.gsonProvider = new com_jess_arms_di_component_AppComponent_gson(appComponent);
        com_jess_arms_di_component_AppComponent_application com_jess_arms_di_component_appcomponent_application = new com_jess_arms_di_component_AppComponent_application(appComponent);
        this.applicationProvider = com_jess_arms_di_component_appcomponent_application;
        Provider<R2HomeModel> provider = DoubleCheck.provider(R2HomeModel_Factory.create(this.repositoryManagerProvider, this.gsonProvider, com_jess_arms_di_component_appcomponent_application));
        this.r2HomeModelProvider = provider;
        this.provideR2HomeModelProvider = DoubleCheck.provider(R2HomeModule_ProvideR2HomeModelFactory.create(r2HomeModule, provider));
        this.provideR2HomeViewProvider = DoubleCheck.provider(R2HomeModule_ProvideR2HomeViewFactory.create(r2HomeModule));
        com_jess_arms_di_component_AppComponent_rxErrorHandler com_jess_arms_di_component_appcomponent_rxerrorhandler = new com_jess_arms_di_component_AppComponent_rxErrorHandler(appComponent);
        this.rxErrorHandlerProvider = com_jess_arms_di_component_appcomponent_rxerrorhandler;
        this.r2HomePresenterProvider = DoubleCheck.provider(R2HomePresenter_Factory.create(this.provideR2HomeModelProvider, this.provideR2HomeViewProvider, com_jess_arms_di_component_appcomponent_rxerrorhandler, this.applicationProvider));
    }

    @Override // com.petkit.android.activities.petkitBleDevice.r2.component.R2HomeComponent
    public void inject(R2HomeActivity r2HomeActivity) {
        injectR2HomeActivity(r2HomeActivity);
    }

    @CanIgnoreReturnValue
    public final R2HomeActivity injectR2HomeActivity(R2HomeActivity r2HomeActivity) {
        BaseActivity_MembersInjector.injectMPresenter(r2HomeActivity, this.r2HomePresenterProvider.get());
        return r2HomeActivity;
    }

    public static final class Builder {
        public AppComponent appComponent;
        public R2HomeModule r2HomeModule;

        public Builder() {
        }

        public Builder r2HomeModule(R2HomeModule r2HomeModule) {
            this.r2HomeModule = (R2HomeModule) Preconditions.checkNotNull(r2HomeModule);
            return this;
        }

        public Builder appComponent(AppComponent appComponent) {
            this.appComponent = (AppComponent) Preconditions.checkNotNull(appComponent);
            return this;
        }

        public R2HomeComponent build() {
            Preconditions.checkBuilderRequirement(this.r2HomeModule, R2HomeModule.class);
            Preconditions.checkBuilderRequirement(this.appComponent, AppComponent.class);
            return new DaggerR2HomeComponent(this.r2HomeModule, this.appComponent);
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

package com.petkit.android.activities.petkitBleDevice.w5.component;

import android.app.Application;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.gson.Gson;
import com.jess.arms.base.BaseActivity_MembersInjector;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.IRepositoryManager;
import com.petkit.android.activities.petkitBleDevice.w5.W5HomeActivity;
import com.petkit.android.activities.petkitBleDevice.w5.contract.W5HomeContract;
import com.petkit.android.activities.petkitBleDevice.w5.model.W5HomeModel;
import com.petkit.android.activities.petkitBleDevice.w5.model.W5HomeModel_Factory;
import com.petkit.android.activities.petkitBleDevice.w5.module.W5HomeModule;
import com.petkit.android.activities.petkitBleDevice.w5.module.W5HomeModule_ProvideW5HomeModelFactory;
import com.petkit.android.activities.petkitBleDevice.w5.module.W5HomeModule_ProvideW5HomeViewFactory;
import com.petkit.android.activities.petkitBleDevice.w5.presenter.W5HomePresenter;
import com.petkit.android.activities.petkitBleDevice.w5.presenter.W5HomePresenter_Factory;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/* JADX INFO: loaded from: classes5.dex */
public final class DaggerW5HomeComponent implements W5HomeComponent {
    public Provider<Application> applicationProvider;
    public Provider<Gson> gsonProvider;
    public Provider<W5HomeContract.Model> provideW5HomeModelProvider;
    public Provider<W5HomeContract.View> provideW5HomeViewProvider;
    public Provider<IRepositoryManager> repositoryManagerProvider;
    public Provider<RxErrorHandler> rxErrorHandlerProvider;
    public Provider<W5HomeModel> w5HomeModelProvider;
    public Provider<W5HomePresenter> w5HomePresenterProvider;

    public DaggerW5HomeComponent(W5HomeModule w5HomeModule, AppComponent appComponent) {
        initialize(w5HomeModule, appComponent);
    }

    public static Builder builder() {
        return new Builder();
    }

    public final void initialize(W5HomeModule w5HomeModule, AppComponent appComponent) {
        this.repositoryManagerProvider = new com_jess_arms_di_component_AppComponent_repositoryManager(appComponent);
        this.gsonProvider = new com_jess_arms_di_component_AppComponent_gson(appComponent);
        com_jess_arms_di_component_AppComponent_application com_jess_arms_di_component_appcomponent_application = new com_jess_arms_di_component_AppComponent_application(appComponent);
        this.applicationProvider = com_jess_arms_di_component_appcomponent_application;
        Provider<W5HomeModel> provider = DoubleCheck.provider(W5HomeModel_Factory.create(this.repositoryManagerProvider, this.gsonProvider, com_jess_arms_di_component_appcomponent_application));
        this.w5HomeModelProvider = provider;
        this.provideW5HomeModelProvider = DoubleCheck.provider(W5HomeModule_ProvideW5HomeModelFactory.create(w5HomeModule, provider));
        this.provideW5HomeViewProvider = DoubleCheck.provider(W5HomeModule_ProvideW5HomeViewFactory.create(w5HomeModule));
        com_jess_arms_di_component_AppComponent_rxErrorHandler com_jess_arms_di_component_appcomponent_rxerrorhandler = new com_jess_arms_di_component_AppComponent_rxErrorHandler(appComponent);
        this.rxErrorHandlerProvider = com_jess_arms_di_component_appcomponent_rxerrorhandler;
        this.w5HomePresenterProvider = DoubleCheck.provider(W5HomePresenter_Factory.create(this.provideW5HomeModelProvider, this.provideW5HomeViewProvider, com_jess_arms_di_component_appcomponent_rxerrorhandler, this.applicationProvider));
    }

    @Override // com.petkit.android.activities.petkitBleDevice.w5.component.W5HomeComponent
    public void inject(W5HomeActivity w5HomeActivity) {
        injectW5HomeActivity(w5HomeActivity);
    }

    @CanIgnoreReturnValue
    public final W5HomeActivity injectW5HomeActivity(W5HomeActivity w5HomeActivity) {
        BaseActivity_MembersInjector.injectMPresenter(w5HomeActivity, this.w5HomePresenterProvider.get());
        return w5HomeActivity;
    }

    public static final class Builder {
        public AppComponent appComponent;
        public W5HomeModule w5HomeModule;

        public Builder() {
        }

        public Builder w5HomeModule(W5HomeModule w5HomeModule) {
            this.w5HomeModule = (W5HomeModule) Preconditions.checkNotNull(w5HomeModule);
            return this;
        }

        public Builder appComponent(AppComponent appComponent) {
            this.appComponent = (AppComponent) Preconditions.checkNotNull(appComponent);
            return this;
        }

        public W5HomeComponent build() {
            Preconditions.checkBuilderRequirement(this.w5HomeModule, W5HomeModule.class);
            Preconditions.checkBuilderRequirement(this.appComponent, AppComponent.class);
            return new DaggerW5HomeComponent(this.w5HomeModule, this.appComponent);
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

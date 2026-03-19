package com.petkit.android.activities.petkitBleDevice.p3.component;

import android.app.Application;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.gson.Gson;
import com.jess.arms.base.BaseActivity_MembersInjector;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.IRepositoryManager;
import com.petkit.android.activities.petkitBleDevice.p3.P3HomeActivity;
import com.petkit.android.activities.petkitBleDevice.p3.contract.P3HomeContract;
import com.petkit.android.activities.petkitBleDevice.p3.model.P3HomeModel;
import com.petkit.android.activities.petkitBleDevice.p3.model.P3HomeModel_Factory;
import com.petkit.android.activities.petkitBleDevice.p3.module.P3HomeModule;
import com.petkit.android.activities.petkitBleDevice.p3.module.P3HomeModule_ProvideP3HomeModelFactory;
import com.petkit.android.activities.petkitBleDevice.p3.module.P3HomeModule_ProvideP3HomeViewFactory;
import com.petkit.android.activities.petkitBleDevice.p3.presenter.P3HomePresenter;
import com.petkit.android.activities.petkitBleDevice.p3.presenter.P3HomePresenter_Factory;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/* JADX INFO: loaded from: classes4.dex */
public final class DaggerP3HomeComponent implements P3HomeComponent {
    public Provider<Application> applicationProvider;
    public Provider<Gson> gsonProvider;
    public Provider<P3HomeModel> p3HomeModelProvider;
    public Provider<P3HomePresenter> p3HomePresenterProvider;
    public Provider<P3HomeContract.Model> provideP3HomeModelProvider;
    public Provider<P3HomeContract.View> provideP3HomeViewProvider;
    public Provider<IRepositoryManager> repositoryManagerProvider;
    public Provider<RxErrorHandler> rxErrorHandlerProvider;

    public DaggerP3HomeComponent(P3HomeModule p3HomeModule, AppComponent appComponent) {
        initialize(p3HomeModule, appComponent);
    }

    public static Builder builder() {
        return new Builder();
    }

    public final void initialize(P3HomeModule p3HomeModule, AppComponent appComponent) {
        this.repositoryManagerProvider = new com_jess_arms_di_component_AppComponent_repositoryManager(appComponent);
        this.gsonProvider = new com_jess_arms_di_component_AppComponent_gson(appComponent);
        com_jess_arms_di_component_AppComponent_application com_jess_arms_di_component_appcomponent_application = new com_jess_arms_di_component_AppComponent_application(appComponent);
        this.applicationProvider = com_jess_arms_di_component_appcomponent_application;
        Provider<P3HomeModel> provider = DoubleCheck.provider(P3HomeModel_Factory.create(this.repositoryManagerProvider, this.gsonProvider, com_jess_arms_di_component_appcomponent_application));
        this.p3HomeModelProvider = provider;
        this.provideP3HomeModelProvider = DoubleCheck.provider(P3HomeModule_ProvideP3HomeModelFactory.create(p3HomeModule, provider));
        this.provideP3HomeViewProvider = DoubleCheck.provider(P3HomeModule_ProvideP3HomeViewFactory.create(p3HomeModule));
        com_jess_arms_di_component_AppComponent_rxErrorHandler com_jess_arms_di_component_appcomponent_rxerrorhandler = new com_jess_arms_di_component_AppComponent_rxErrorHandler(appComponent);
        this.rxErrorHandlerProvider = com_jess_arms_di_component_appcomponent_rxerrorhandler;
        this.p3HomePresenterProvider = DoubleCheck.provider(P3HomePresenter_Factory.create(this.provideP3HomeModelProvider, this.provideP3HomeViewProvider, com_jess_arms_di_component_appcomponent_rxerrorhandler, this.applicationProvider));
    }

    @Override // com.petkit.android.activities.petkitBleDevice.p3.component.P3HomeComponent
    public void inject(P3HomeActivity p3HomeActivity) {
        injectP3HomeActivity(p3HomeActivity);
    }

    @CanIgnoreReturnValue
    public final P3HomeActivity injectP3HomeActivity(P3HomeActivity p3HomeActivity) {
        BaseActivity_MembersInjector.injectMPresenter(p3HomeActivity, this.p3HomePresenterProvider.get());
        return p3HomeActivity;
    }

    public static final class Builder {
        public AppComponent appComponent;
        public P3HomeModule p3HomeModule;

        public Builder() {
        }

        public Builder p3HomeModule(P3HomeModule p3HomeModule) {
            this.p3HomeModule = (P3HomeModule) Preconditions.checkNotNull(p3HomeModule);
            return this;
        }

        public Builder appComponent(AppComponent appComponent) {
            this.appComponent = (AppComponent) Preconditions.checkNotNull(appComponent);
            return this;
        }

        public P3HomeComponent build() {
            Preconditions.checkBuilderRequirement(this.p3HomeModule, P3HomeModule.class);
            Preconditions.checkBuilderRequirement(this.appComponent, AppComponent.class);
            return new DaggerP3HomeComponent(this.p3HomeModule, this.appComponent);
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

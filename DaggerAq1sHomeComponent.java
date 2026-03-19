package com.petkit.android.activities.petkitBleDevice.aq1s.component;

import android.app.Application;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.gson.Gson;
import com.jess.arms.base.BaseActivity_MembersInjector;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.IRepositoryManager;
import com.petkit.android.activities.petkitBleDevice.aq1s.Aq1sHomeActivity;
import com.petkit.android.activities.petkitBleDevice.aq1s.contract.Aq1sHomeContract;
import com.petkit.android.activities.petkitBleDevice.aq1s.model.Aq1sHomeModel;
import com.petkit.android.activities.petkitBleDevice.aq1s.model.Aq1sHomeModel_Factory;
import com.petkit.android.activities.petkitBleDevice.aq1s.module.Aq1sHomeModule;
import com.petkit.android.activities.petkitBleDevice.aq1s.module.Aq1sHomeModule_ProvideAq1sHomeModelFactory;
import com.petkit.android.activities.petkitBleDevice.aq1s.module.Aq1sHomeModule_ProvideAq1sHomeViewFactory;
import com.petkit.android.activities.petkitBleDevice.aq1s.presenter.Aq1sHomePresenter;
import com.petkit.android.activities.petkitBleDevice.aq1s.presenter.Aq1sHomePresenter_Factory;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/* JADX INFO: loaded from: classes4.dex */
public final class DaggerAq1sHomeComponent implements Aq1sHomeComponent {
    public Provider<Application> applicationProvider;
    public Provider<Aq1sHomeModel> aq1sHomeModelProvider;
    public Provider<Aq1sHomePresenter> aq1sHomePresenterProvider;
    public Provider<Gson> gsonProvider;
    public Provider<Aq1sHomeContract.Model> provideAq1sHomeModelProvider;
    public Provider<Aq1sHomeContract.View> provideAq1sHomeViewProvider;
    public Provider<IRepositoryManager> repositoryManagerProvider;
    public Provider<RxErrorHandler> rxErrorHandlerProvider;

    public DaggerAq1sHomeComponent(Aq1sHomeModule aq1sHomeModule, AppComponent appComponent) {
        initialize(aq1sHomeModule, appComponent);
    }

    public static Builder builder() {
        return new Builder();
    }

    public final void initialize(Aq1sHomeModule aq1sHomeModule, AppComponent appComponent) {
        this.repositoryManagerProvider = new com_jess_arms_di_component_AppComponent_repositoryManager(appComponent);
        this.gsonProvider = new com_jess_arms_di_component_AppComponent_gson(appComponent);
        com_jess_arms_di_component_AppComponent_application com_jess_arms_di_component_appcomponent_application = new com_jess_arms_di_component_AppComponent_application(appComponent);
        this.applicationProvider = com_jess_arms_di_component_appcomponent_application;
        Provider<Aq1sHomeModel> provider = DoubleCheck.provider(Aq1sHomeModel_Factory.create(this.repositoryManagerProvider, this.gsonProvider, com_jess_arms_di_component_appcomponent_application));
        this.aq1sHomeModelProvider = provider;
        this.provideAq1sHomeModelProvider = DoubleCheck.provider(Aq1sHomeModule_ProvideAq1sHomeModelFactory.create(aq1sHomeModule, provider));
        this.provideAq1sHomeViewProvider = DoubleCheck.provider(Aq1sHomeModule_ProvideAq1sHomeViewFactory.create(aq1sHomeModule));
        com_jess_arms_di_component_AppComponent_rxErrorHandler com_jess_arms_di_component_appcomponent_rxerrorhandler = new com_jess_arms_di_component_AppComponent_rxErrorHandler(appComponent);
        this.rxErrorHandlerProvider = com_jess_arms_di_component_appcomponent_rxerrorhandler;
        this.aq1sHomePresenterProvider = DoubleCheck.provider(Aq1sHomePresenter_Factory.create(this.provideAq1sHomeModelProvider, this.provideAq1sHomeViewProvider, com_jess_arms_di_component_appcomponent_rxerrorhandler, this.applicationProvider));
    }

    @Override // com.petkit.android.activities.petkitBleDevice.aq1s.component.Aq1sHomeComponent
    public void inject(Aq1sHomeActivity aq1sHomeActivity) {
        injectAq1sHomeActivity(aq1sHomeActivity);
    }

    @CanIgnoreReturnValue
    public final Aq1sHomeActivity injectAq1sHomeActivity(Aq1sHomeActivity aq1sHomeActivity) {
        BaseActivity_MembersInjector.injectMPresenter(aq1sHomeActivity, this.aq1sHomePresenterProvider.get());
        return aq1sHomeActivity;
    }

    public static final class Builder {
        public AppComponent appComponent;
        public Aq1sHomeModule aq1sHomeModule;

        public Builder() {
        }

        public Builder aq1sHomeModule(Aq1sHomeModule aq1sHomeModule) {
            this.aq1sHomeModule = (Aq1sHomeModule) Preconditions.checkNotNull(aq1sHomeModule);
            return this;
        }

        public Builder appComponent(AppComponent appComponent) {
            this.appComponent = (AppComponent) Preconditions.checkNotNull(appComponent);
            return this;
        }

        public Aq1sHomeComponent build() {
            Preconditions.checkBuilderRequirement(this.aq1sHomeModule, Aq1sHomeModule.class);
            Preconditions.checkBuilderRequirement(this.appComponent, AppComponent.class);
            return new DaggerAq1sHomeComponent(this.aq1sHomeModule, this.appComponent);
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

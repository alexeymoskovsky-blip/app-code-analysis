package com.petkit.android.activities.petkitBleDevice.p3.component;

import android.app.Application;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.gson.Gson;
import com.jess.arms.base.BaseActivity_MembersInjector;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.IRepositoryManager;
import com.petkit.android.activities.petkitBleDevice.p3.P3MainActivity;
import com.petkit.android.activities.petkitBleDevice.p3.contract.P3MainContract;
import com.petkit.android.activities.petkitBleDevice.p3.model.P3MainModel;
import com.petkit.android.activities.petkitBleDevice.p3.model.P3MainModel_Factory;
import com.petkit.android.activities.petkitBleDevice.p3.module.P3MainModule;
import com.petkit.android.activities.petkitBleDevice.p3.module.P3MainModule_ProvideP3MainModelFactory;
import com.petkit.android.activities.petkitBleDevice.p3.module.P3MainModule_ProvideP3MainViewFactory;
import com.petkit.android.activities.petkitBleDevice.p3.presenter.P3MainPresenter;
import com.petkit.android.activities.petkitBleDevice.p3.presenter.P3MainPresenter_Factory;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/* JADX INFO: loaded from: classes4.dex */
public final class DaggerP3MainComponent implements P3MainComponent {
    public Provider<Application> applicationProvider;
    public Provider<Gson> gsonProvider;
    public Provider<P3MainModel> p3MainModelProvider;
    public Provider<P3MainPresenter> p3MainPresenterProvider;
    public Provider<P3MainContract.Model> provideP3MainModelProvider;
    public Provider<P3MainContract.View> provideP3MainViewProvider;
    public Provider<IRepositoryManager> repositoryManagerProvider;
    public Provider<RxErrorHandler> rxErrorHandlerProvider;

    public DaggerP3MainComponent(P3MainModule p3MainModule, AppComponent appComponent) {
        initialize(p3MainModule, appComponent);
    }

    public static Builder builder() {
        return new Builder();
    }

    public final void initialize(P3MainModule p3MainModule, AppComponent appComponent) {
        this.repositoryManagerProvider = new com_jess_arms_di_component_AppComponent_repositoryManager(appComponent);
        this.gsonProvider = new com_jess_arms_di_component_AppComponent_gson(appComponent);
        com_jess_arms_di_component_AppComponent_application com_jess_arms_di_component_appcomponent_application = new com_jess_arms_di_component_AppComponent_application(appComponent);
        this.applicationProvider = com_jess_arms_di_component_appcomponent_application;
        Provider<P3MainModel> provider = DoubleCheck.provider(P3MainModel_Factory.create(this.repositoryManagerProvider, this.gsonProvider, com_jess_arms_di_component_appcomponent_application));
        this.p3MainModelProvider = provider;
        this.provideP3MainModelProvider = DoubleCheck.provider(P3MainModule_ProvideP3MainModelFactory.create(p3MainModule, provider));
        this.provideP3MainViewProvider = DoubleCheck.provider(P3MainModule_ProvideP3MainViewFactory.create(p3MainModule));
        com_jess_arms_di_component_AppComponent_rxErrorHandler com_jess_arms_di_component_appcomponent_rxerrorhandler = new com_jess_arms_di_component_AppComponent_rxErrorHandler(appComponent);
        this.rxErrorHandlerProvider = com_jess_arms_di_component_appcomponent_rxerrorhandler;
        this.p3MainPresenterProvider = DoubleCheck.provider(P3MainPresenter_Factory.create(this.provideP3MainModelProvider, this.provideP3MainViewProvider, com_jess_arms_di_component_appcomponent_rxerrorhandler, this.applicationProvider));
    }

    @Override // com.petkit.android.activities.petkitBleDevice.p3.component.P3MainComponent
    public void inject(P3MainActivity p3MainActivity) {
        injectP3MainActivity(p3MainActivity);
    }

    @CanIgnoreReturnValue
    public final P3MainActivity injectP3MainActivity(P3MainActivity p3MainActivity) {
        BaseActivity_MembersInjector.injectMPresenter(p3MainActivity, this.p3MainPresenterProvider.get());
        return p3MainActivity;
    }

    public static final class Builder {
        public AppComponent appComponent;
        public P3MainModule p3MainModule;

        public Builder() {
        }

        public Builder p3MainModule(P3MainModule p3MainModule) {
            this.p3MainModule = (P3MainModule) Preconditions.checkNotNull(p3MainModule);
            return this;
        }

        public Builder appComponent(AppComponent appComponent) {
            this.appComponent = (AppComponent) Preconditions.checkNotNull(appComponent);
            return this;
        }

        public P3MainComponent build() {
            Preconditions.checkBuilderRequirement(this.p3MainModule, P3MainModule.class);
            Preconditions.checkBuilderRequirement(this.appComponent, AppComponent.class);
            return new DaggerP3MainComponent(this.p3MainModule, this.appComponent);
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

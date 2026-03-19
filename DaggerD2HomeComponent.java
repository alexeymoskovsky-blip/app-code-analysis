package com.petkit.android.activities.d2.component;

import android.app.Application;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.gson.Gson;
import com.jess.arms.base.BaseActivity_MembersInjector;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.IRepositoryManager;
import com.petkit.android.activities.d2.D2HomeActivity;
import com.petkit.android.activities.d2.contract.D2HomeContract;
import com.petkit.android.activities.d2.model.D2HomeModel;
import com.petkit.android.activities.d2.model.D2HomeModel_Factory;
import com.petkit.android.activities.d2.module.D2HomeModule;
import com.petkit.android.activities.d2.module.D2HomeModule_ProvideD2HomeModelFactory;
import com.petkit.android.activities.d2.module.D2HomeModule_ProvideD2HomeViewFactory;
import com.petkit.android.activities.d2.presenter.D2HomePresenter;
import com.petkit.android.activities.d2.presenter.D2HomePresenter_Factory;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/* JADX INFO: loaded from: classes3.dex */
public final class DaggerD2HomeComponent implements D2HomeComponent {
    public Provider<Application> applicationProvider;
    public Provider<D2HomeModel> d2HomeModelProvider;
    public Provider<D2HomePresenter> d2HomePresenterProvider;
    public Provider<Gson> gsonProvider;
    public Provider<D2HomeContract.Model> provideD2HomeModelProvider;
    public Provider<D2HomeContract.View> provideD2HomeViewProvider;
    public Provider<IRepositoryManager> repositoryManagerProvider;
    public Provider<RxErrorHandler> rxErrorHandlerProvider;

    public DaggerD2HomeComponent(D2HomeModule d2HomeModule, AppComponent appComponent) {
        initialize(d2HomeModule, appComponent);
    }

    public static Builder builder() {
        return new Builder();
    }

    public final void initialize(D2HomeModule d2HomeModule, AppComponent appComponent) {
        this.repositoryManagerProvider = new com_jess_arms_di_component_AppComponent_repositoryManager(appComponent);
        this.gsonProvider = new com_jess_arms_di_component_AppComponent_gson(appComponent);
        com_jess_arms_di_component_AppComponent_application com_jess_arms_di_component_appcomponent_application = new com_jess_arms_di_component_AppComponent_application(appComponent);
        this.applicationProvider = com_jess_arms_di_component_appcomponent_application;
        Provider<D2HomeModel> provider = DoubleCheck.provider(D2HomeModel_Factory.create(this.repositoryManagerProvider, this.gsonProvider, com_jess_arms_di_component_appcomponent_application));
        this.d2HomeModelProvider = provider;
        this.provideD2HomeModelProvider = DoubleCheck.provider(D2HomeModule_ProvideD2HomeModelFactory.create(d2HomeModule, provider));
        this.provideD2HomeViewProvider = DoubleCheck.provider(D2HomeModule_ProvideD2HomeViewFactory.create(d2HomeModule));
        com_jess_arms_di_component_AppComponent_rxErrorHandler com_jess_arms_di_component_appcomponent_rxerrorhandler = new com_jess_arms_di_component_AppComponent_rxErrorHandler(appComponent);
        this.rxErrorHandlerProvider = com_jess_arms_di_component_appcomponent_rxerrorhandler;
        this.d2HomePresenterProvider = DoubleCheck.provider(D2HomePresenter_Factory.create(this.provideD2HomeModelProvider, this.provideD2HomeViewProvider, com_jess_arms_di_component_appcomponent_rxerrorhandler, this.applicationProvider));
    }

    @Override // com.petkit.android.activities.d2.component.D2HomeComponent
    public void inject(D2HomeActivity d2HomeActivity) {
        injectD2HomeActivity(d2HomeActivity);
    }

    @CanIgnoreReturnValue
    public final D2HomeActivity injectD2HomeActivity(D2HomeActivity d2HomeActivity) {
        BaseActivity_MembersInjector.injectMPresenter(d2HomeActivity, this.d2HomePresenterProvider.get());
        return d2HomeActivity;
    }

    public static final class Builder {
        public AppComponent appComponent;
        public D2HomeModule d2HomeModule;

        public Builder() {
        }

        public Builder d2HomeModule(D2HomeModule d2HomeModule) {
            this.d2HomeModule = (D2HomeModule) Preconditions.checkNotNull(d2HomeModule);
            return this;
        }

        public Builder appComponent(AppComponent appComponent) {
            this.appComponent = (AppComponent) Preconditions.checkNotNull(appComponent);
            return this;
        }

        public D2HomeComponent build() {
            Preconditions.checkBuilderRequirement(this.d2HomeModule, D2HomeModule.class);
            Preconditions.checkBuilderRequirement(this.appComponent, AppComponent.class);
            return new DaggerD2HomeComponent(this.d2HomeModule, this.appComponent);
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

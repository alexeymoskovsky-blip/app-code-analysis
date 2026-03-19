package com.petkit.android.activities.petkitBleDevice.d3.component;

import android.app.Application;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.gson.Gson;
import com.jess.arms.base.BaseActivity_MembersInjector;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.IRepositoryManager;
import com.petkit.android.activities.petkitBleDevice.d3.D3HomeActivity;
import com.petkit.android.activities.petkitBleDevice.d3.component.D3HomeComponent;
import com.petkit.android.activities.petkitBleDevice.d3.contract.D3HomeContract;
import com.petkit.android.activities.petkitBleDevice.d3.model.D3HomeModel;
import com.petkit.android.activities.petkitBleDevice.d3.model.D3HomeModel_Factory;
import com.petkit.android.activities.petkitBleDevice.d3.presenter.D3HomePresenter;
import com.petkit.android.activities.petkitBleDevice.d3.presenter.D3HomePresenter_Factory;
import dagger.internal.DoubleCheck;
import dagger.internal.InstanceFactory;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/* JADX INFO: loaded from: classes4.dex */
public final class DaggerD3HomeComponent implements D3HomeComponent {
    public Provider<Application> applicationProvider;
    public Provider<D3HomeModel> d3HomeModelProvider;
    public Provider<D3HomePresenter> d3HomePresenterProvider;
    public Provider<Gson> gsonProvider;
    public Provider<IRepositoryManager> repositoryManagerProvider;
    public Provider<RxErrorHandler> rxErrorHandlerProvider;
    public Provider<D3HomeContract.View> viewProvider;

    public DaggerD3HomeComponent(AppComponent appComponent, D3HomeContract.View view) {
        initialize(appComponent, view);
    }

    public static D3HomeComponent.Builder builder() {
        return new Builder();
    }

    public final void initialize(AppComponent appComponent, D3HomeContract.View view) {
        this.repositoryManagerProvider = new com_jess_arms_di_component_AppComponent_repositoryManager(appComponent);
        this.gsonProvider = new com_jess_arms_di_component_AppComponent_gson(appComponent);
        com_jess_arms_di_component_AppComponent_application com_jess_arms_di_component_appcomponent_application = new com_jess_arms_di_component_AppComponent_application(appComponent);
        this.applicationProvider = com_jess_arms_di_component_appcomponent_application;
        this.d3HomeModelProvider = DoubleCheck.provider(D3HomeModel_Factory.create(this.repositoryManagerProvider, this.gsonProvider, com_jess_arms_di_component_appcomponent_application));
        this.viewProvider = InstanceFactory.create(view);
        com_jess_arms_di_component_AppComponent_rxErrorHandler com_jess_arms_di_component_appcomponent_rxerrorhandler = new com_jess_arms_di_component_AppComponent_rxErrorHandler(appComponent);
        this.rxErrorHandlerProvider = com_jess_arms_di_component_appcomponent_rxerrorhandler;
        this.d3HomePresenterProvider = DoubleCheck.provider(D3HomePresenter_Factory.create(this.d3HomeModelProvider, this.viewProvider, com_jess_arms_di_component_appcomponent_rxerrorhandler, this.applicationProvider));
    }

    @Override // com.petkit.android.activities.petkitBleDevice.d3.component.D3HomeComponent
    public void inject(D3HomeActivity d3HomeActivity) {
        injectD3HomeActivity(d3HomeActivity);
    }

    @CanIgnoreReturnValue
    public final D3HomeActivity injectD3HomeActivity(D3HomeActivity d3HomeActivity) {
        BaseActivity_MembersInjector.injectMPresenter(d3HomeActivity, this.d3HomePresenterProvider.get());
        return d3HomeActivity;
    }

    public static final class Builder implements D3HomeComponent.Builder {
        public AppComponent appComponent;
        public D3HomeContract.View view;

        public Builder() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.d3.component.D3HomeComponent.Builder
        public Builder view(D3HomeContract.View view) {
            this.view = (D3HomeContract.View) Preconditions.checkNotNull(view);
            return this;
        }

        @Override // com.petkit.android.activities.petkitBleDevice.d3.component.D3HomeComponent.Builder
        public Builder appComponent(AppComponent appComponent) {
            this.appComponent = (AppComponent) Preconditions.checkNotNull(appComponent);
            return this;
        }

        @Override // com.petkit.android.activities.petkitBleDevice.d3.component.D3HomeComponent.Builder
        public D3HomeComponent build() {
            Preconditions.checkBuilderRequirement(this.view, D3HomeContract.View.class);
            Preconditions.checkBuilderRequirement(this.appComponent, AppComponent.class);
            return new DaggerD3HomeComponent(this.appComponent, this.view);
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

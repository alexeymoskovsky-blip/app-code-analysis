package com.petkit.android.activities.petkitBleDevice.component;

import android.app.Application;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.gson.Gson;
import com.jess.arms.base.BaseActivity_MembersInjector;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.IRepositoryManager;
import com.petkit.android.activities.petkitBleDevice.T3HomeActivity;
import com.petkit.android.activities.petkitBleDevice.component.T3HomeComponent;
import com.petkit.android.activities.petkitBleDevice.contract.T3HomeContract;
import com.petkit.android.activities.petkitBleDevice.model.T3HomeModel;
import com.petkit.android.activities.petkitBleDevice.model.T3HomeModel_Factory;
import com.petkit.android.activities.petkitBleDevice.presenter.T3HomePresenter;
import com.petkit.android.activities.petkitBleDevice.presenter.T3HomePresenter_Factory;
import dagger.internal.DoubleCheck;
import dagger.internal.InstanceFactory;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/* JADX INFO: loaded from: classes4.dex */
public final class DaggerT3HomeComponent implements T3HomeComponent {
    public Provider<Application> applicationProvider;
    public Provider<Gson> gsonProvider;
    public Provider<IRepositoryManager> repositoryManagerProvider;
    public Provider<RxErrorHandler> rxErrorHandlerProvider;
    public Provider<T3HomeModel> t3HomeModelProvider;
    public Provider<T3HomePresenter> t3HomePresenterProvider;
    public Provider<T3HomeContract.View> viewProvider;

    public DaggerT3HomeComponent(AppComponent appComponent, T3HomeContract.View view) {
        initialize(appComponent, view);
    }

    public static T3HomeComponent.Builder builder() {
        return new Builder();
    }

    public final void initialize(AppComponent appComponent, T3HomeContract.View view) {
        this.repositoryManagerProvider = new com_jess_arms_di_component_AppComponent_repositoryManager(appComponent);
        this.gsonProvider = new com_jess_arms_di_component_AppComponent_gson(appComponent);
        com_jess_arms_di_component_AppComponent_application com_jess_arms_di_component_appcomponent_application = new com_jess_arms_di_component_AppComponent_application(appComponent);
        this.applicationProvider = com_jess_arms_di_component_appcomponent_application;
        this.t3HomeModelProvider = DoubleCheck.provider(T3HomeModel_Factory.create(this.repositoryManagerProvider, this.gsonProvider, com_jess_arms_di_component_appcomponent_application));
        this.viewProvider = InstanceFactory.create(view);
        com_jess_arms_di_component_AppComponent_rxErrorHandler com_jess_arms_di_component_appcomponent_rxerrorhandler = new com_jess_arms_di_component_AppComponent_rxErrorHandler(appComponent);
        this.rxErrorHandlerProvider = com_jess_arms_di_component_appcomponent_rxerrorhandler;
        this.t3HomePresenterProvider = DoubleCheck.provider(T3HomePresenter_Factory.create(this.t3HomeModelProvider, this.viewProvider, com_jess_arms_di_component_appcomponent_rxerrorhandler, this.applicationProvider));
    }

    @Override // com.petkit.android.activities.petkitBleDevice.component.T3HomeComponent
    public void inject(T3HomeActivity t3HomeActivity) {
        injectT3HomeActivity(t3HomeActivity);
    }

    @CanIgnoreReturnValue
    public final T3HomeActivity injectT3HomeActivity(T3HomeActivity t3HomeActivity) {
        BaseActivity_MembersInjector.injectMPresenter(t3HomeActivity, this.t3HomePresenterProvider.get());
        return t3HomeActivity;
    }

    public static final class Builder implements T3HomeComponent.Builder {
        public AppComponent appComponent;
        public T3HomeContract.View view;

        public Builder() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.component.T3HomeComponent.Builder
        public Builder view(T3HomeContract.View view) {
            this.view = (T3HomeContract.View) Preconditions.checkNotNull(view);
            return this;
        }

        @Override // com.petkit.android.activities.petkitBleDevice.component.T3HomeComponent.Builder
        public Builder appComponent(AppComponent appComponent) {
            this.appComponent = (AppComponent) Preconditions.checkNotNull(appComponent);
            return this;
        }

        @Override // com.petkit.android.activities.petkitBleDevice.component.T3HomeComponent.Builder
        public T3HomeComponent build() {
            Preconditions.checkBuilderRequirement(this.view, T3HomeContract.View.class);
            Preconditions.checkBuilderRequirement(this.appComponent, AppComponent.class);
            return new DaggerT3HomeComponent(this.appComponent, this.view);
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

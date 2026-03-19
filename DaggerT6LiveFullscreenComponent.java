package com.petkit.android.activities.petkitBleDevice.t6.component;

import android.app.Application;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.gson.Gson;
import com.jess.arms.base.BaseActivity_MembersInjector;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.AppManager;
import com.jess.arms.integration.IRepositoryManager;
import com.petkit.android.activities.petkitBleDevice.t6.T6LiveFullscreenActivity;
import com.petkit.android.activities.petkitBleDevice.t6.component.T6LiveFullscreenComponent;
import com.petkit.android.activities.petkitBleDevice.t6.contract.T6LiveFullscreenContract;
import com.petkit.android.activities.petkitBleDevice.t6.model.T6LiveFullscreenModel;
import com.petkit.android.activities.petkitBleDevice.t6.model.T6LiveFullscreenModel_Factory;
import com.petkit.android.activities.petkitBleDevice.t6.presenter.T6LiveFullscreenPresenter;
import com.petkit.android.activities.petkitBleDevice.t6.presenter.T6LiveFullscreenPresenter_Factory;
import dagger.internal.DoubleCheck;
import dagger.internal.InstanceFactory;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/* JADX INFO: loaded from: classes5.dex */
public final class DaggerT6LiveFullscreenComponent implements T6LiveFullscreenComponent {
    public Provider<AppManager> appManagerProvider;
    public Provider<Application> applicationProvider;
    public Provider<Gson> gsonProvider;
    public Provider<IRepositoryManager> repositoryManagerProvider;
    public Provider<RxErrorHandler> rxErrorHandlerProvider;
    public Provider<T6LiveFullscreenModel> t6LiveFullscreenModelProvider;
    public Provider<T6LiveFullscreenPresenter> t6LiveFullscreenPresenterProvider;
    public Provider<T6LiveFullscreenContract.View> viewProvider;

    public DaggerT6LiveFullscreenComponent(AppComponent appComponent, T6LiveFullscreenContract.View view) {
        initialize(appComponent, view);
    }

    public static T6LiveFullscreenComponent.Builder builder() {
        return new Builder();
    }

    public final void initialize(AppComponent appComponent, T6LiveFullscreenContract.View view) {
        this.repositoryManagerProvider = new com_jess_arms_di_component_AppComponent_repositoryManager(appComponent);
        this.gsonProvider = new com_jess_arms_di_component_AppComponent_gson(appComponent);
        com_jess_arms_di_component_AppComponent_application com_jess_arms_di_component_appcomponent_application = new com_jess_arms_di_component_AppComponent_application(appComponent);
        this.applicationProvider = com_jess_arms_di_component_appcomponent_application;
        this.t6LiveFullscreenModelProvider = DoubleCheck.provider(T6LiveFullscreenModel_Factory.create(this.repositoryManagerProvider, this.gsonProvider, com_jess_arms_di_component_appcomponent_application));
        this.viewProvider = InstanceFactory.create(view);
        this.rxErrorHandlerProvider = new com_jess_arms_di_component_AppComponent_rxErrorHandler(appComponent);
        com_jess_arms_di_component_AppComponent_appManager com_jess_arms_di_component_appcomponent_appmanager = new com_jess_arms_di_component_AppComponent_appManager(appComponent);
        this.appManagerProvider = com_jess_arms_di_component_appcomponent_appmanager;
        this.t6LiveFullscreenPresenterProvider = DoubleCheck.provider(T6LiveFullscreenPresenter_Factory.create(this.t6LiveFullscreenModelProvider, this.viewProvider, this.rxErrorHandlerProvider, this.applicationProvider, com_jess_arms_di_component_appcomponent_appmanager));
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.component.T6LiveFullscreenComponent
    public void inject(T6LiveFullscreenActivity t6LiveFullscreenActivity) {
        injectT6LiveFullscreenActivity(t6LiveFullscreenActivity);
    }

    @CanIgnoreReturnValue
    public final T6LiveFullscreenActivity injectT6LiveFullscreenActivity(T6LiveFullscreenActivity t6LiveFullscreenActivity) {
        BaseActivity_MembersInjector.injectMPresenter(t6LiveFullscreenActivity, this.t6LiveFullscreenPresenterProvider.get());
        return t6LiveFullscreenActivity;
    }

    public static final class Builder implements T6LiveFullscreenComponent.Builder {
        public AppComponent appComponent;
        public T6LiveFullscreenContract.View view;

        public Builder() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t6.component.T6LiveFullscreenComponent.Builder
        public Builder view(T6LiveFullscreenContract.View view) {
            this.view = (T6LiveFullscreenContract.View) Preconditions.checkNotNull(view);
            return this;
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t6.component.T6LiveFullscreenComponent.Builder
        public Builder appComponent(AppComponent appComponent) {
            this.appComponent = (AppComponent) Preconditions.checkNotNull(appComponent);
            return this;
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t6.component.T6LiveFullscreenComponent.Builder
        public T6LiveFullscreenComponent build() {
            Preconditions.checkBuilderRequirement(this.view, T6LiveFullscreenContract.View.class);
            Preconditions.checkBuilderRequirement(this.appComponent, AppComponent.class);
            return new DaggerT6LiveFullscreenComponent(this.appComponent, this.view);
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

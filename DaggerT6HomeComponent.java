package com.petkit.android.activities.petkitBleDevice.t6.component;

import android.app.Application;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.gson.Gson;
import com.jess.arms.base.BaseActivity_MembersInjector;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.AppManager;
import com.jess.arms.integration.IRepositoryManager;
import com.petkit.android.activities.petkitBleDevice.t6.T6HomeActivity;
import com.petkit.android.activities.petkitBleDevice.t6.component.T6HomeComponent;
import com.petkit.android.activities.petkitBleDevice.t6.contract.T6HomeContract;
import com.petkit.android.activities.petkitBleDevice.t6.model.T6HomeModel;
import com.petkit.android.activities.petkitBleDevice.t6.model.T6HomeModel_Factory;
import com.petkit.android.activities.petkitBleDevice.t6.presenter.T6HomePresenter;
import com.petkit.android.activities.petkitBleDevice.t6.presenter.T6HomePresenter_Factory;
import dagger.internal.DoubleCheck;
import dagger.internal.InstanceFactory;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/* JADX INFO: loaded from: classes5.dex */
public final class DaggerT6HomeComponent implements T6HomeComponent {
    public Provider<AppManager> appManagerProvider;
    public Provider<Application> applicationProvider;
    public Provider<Gson> gsonProvider;
    public Provider<IRepositoryManager> repositoryManagerProvider;
    public Provider<RxErrorHandler> rxErrorHandlerProvider;
    public Provider<T6HomeModel> t6HomeModelProvider;
    public Provider<T6HomePresenter> t6HomePresenterProvider;
    public Provider<T6HomeContract.View> viewProvider;

    public DaggerT6HomeComponent(AppComponent appComponent, T6HomeContract.View view) {
        initialize(appComponent, view);
    }

    public static T6HomeComponent.Builder builder() {
        return new Builder();
    }

    public final void initialize(AppComponent appComponent, T6HomeContract.View view) {
        this.repositoryManagerProvider = new com_jess_arms_di_component_AppComponent_repositoryManager(appComponent);
        this.gsonProvider = new com_jess_arms_di_component_AppComponent_gson(appComponent);
        com_jess_arms_di_component_AppComponent_application com_jess_arms_di_component_appcomponent_application = new com_jess_arms_di_component_AppComponent_application(appComponent);
        this.applicationProvider = com_jess_arms_di_component_appcomponent_application;
        this.t6HomeModelProvider = DoubleCheck.provider(T6HomeModel_Factory.create(this.repositoryManagerProvider, this.gsonProvider, com_jess_arms_di_component_appcomponent_application));
        this.viewProvider = InstanceFactory.create(view);
        this.rxErrorHandlerProvider = new com_jess_arms_di_component_AppComponent_rxErrorHandler(appComponent);
        com_jess_arms_di_component_AppComponent_appManager com_jess_arms_di_component_appcomponent_appmanager = new com_jess_arms_di_component_AppComponent_appManager(appComponent);
        this.appManagerProvider = com_jess_arms_di_component_appcomponent_appmanager;
        this.t6HomePresenterProvider = DoubleCheck.provider(T6HomePresenter_Factory.create(this.t6HomeModelProvider, this.viewProvider, this.rxErrorHandlerProvider, this.applicationProvider, com_jess_arms_di_component_appcomponent_appmanager));
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.component.T6HomeComponent
    public void inject(T6HomeActivity t6HomeActivity) {
        injectT6HomeActivity(t6HomeActivity);
    }

    @CanIgnoreReturnValue
    public final T6HomeActivity injectT6HomeActivity(T6HomeActivity t6HomeActivity) {
        BaseActivity_MembersInjector.injectMPresenter(t6HomeActivity, this.t6HomePresenterProvider.get());
        return t6HomeActivity;
    }

    public static final class Builder implements T6HomeComponent.Builder {
        public AppComponent appComponent;
        public T6HomeContract.View view;

        public Builder() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t6.component.T6HomeComponent.Builder
        public Builder view(T6HomeContract.View view) {
            this.view = (T6HomeContract.View) Preconditions.checkNotNull(view);
            return this;
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t6.component.T6HomeComponent.Builder
        public Builder appComponent(AppComponent appComponent) {
            this.appComponent = (AppComponent) Preconditions.checkNotNull(appComponent);
            return this;
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t6.component.T6HomeComponent.Builder
        public T6HomeComponent build() {
            Preconditions.checkBuilderRequirement(this.view, T6HomeContract.View.class);
            Preconditions.checkBuilderRequirement(this.appComponent, AppComponent.class);
            return new DaggerT6HomeComponent(this.appComponent, this.view);
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

package com.petkit.android.activities.petkitBleDevice.t6.component;

import android.app.Application;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.gson.Gson;
import com.jess.arms.base.BaseActivity_MembersInjector;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.AppManager;
import com.jess.arms.integration.IRepositoryManager;
import com.petkit.android.activities.petkitBleDevice.t6.T6WarnsListActivity;
import com.petkit.android.activities.petkitBleDevice.t6.component.T6WarnsListComponent;
import com.petkit.android.activities.petkitBleDevice.t6.contract.T6WarnsListContract;
import com.petkit.android.activities.petkitBleDevice.t6.model.T6WarnsListModel;
import com.petkit.android.activities.petkitBleDevice.t6.model.T6WarnsListModel_Factory;
import com.petkit.android.activities.petkitBleDevice.t6.presenter.T6WarnsListPresenter;
import com.petkit.android.activities.petkitBleDevice.t6.presenter.T6WarnsListPresenter_Factory;
import dagger.internal.DoubleCheck;
import dagger.internal.InstanceFactory;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/* JADX INFO: loaded from: classes5.dex */
public final class DaggerT6WarnsListComponent implements T6WarnsListComponent {
    public Provider<AppManager> appManagerProvider;
    public Provider<Application> applicationProvider;
    public Provider<Gson> gsonProvider;
    public Provider<IRepositoryManager> repositoryManagerProvider;
    public Provider<RxErrorHandler> rxErrorHandlerProvider;
    public Provider<T6WarnsListModel> t6WarnsListModelProvider;
    public Provider<T6WarnsListPresenter> t6WarnsListPresenterProvider;
    public Provider<T6WarnsListContract.View> viewProvider;

    public DaggerT6WarnsListComponent(AppComponent appComponent, T6WarnsListContract.View view) {
        initialize(appComponent, view);
    }

    public static T6WarnsListComponent.Builder builder() {
        return new Builder();
    }

    public final void initialize(AppComponent appComponent, T6WarnsListContract.View view) {
        this.repositoryManagerProvider = new com_jess_arms_di_component_AppComponent_repositoryManager(appComponent);
        this.gsonProvider = new com_jess_arms_di_component_AppComponent_gson(appComponent);
        com_jess_arms_di_component_AppComponent_application com_jess_arms_di_component_appcomponent_application = new com_jess_arms_di_component_AppComponent_application(appComponent);
        this.applicationProvider = com_jess_arms_di_component_appcomponent_application;
        this.t6WarnsListModelProvider = DoubleCheck.provider(T6WarnsListModel_Factory.create(this.repositoryManagerProvider, this.gsonProvider, com_jess_arms_di_component_appcomponent_application));
        this.viewProvider = InstanceFactory.create(view);
        this.rxErrorHandlerProvider = new com_jess_arms_di_component_AppComponent_rxErrorHandler(appComponent);
        com_jess_arms_di_component_AppComponent_appManager com_jess_arms_di_component_appcomponent_appmanager = new com_jess_arms_di_component_AppComponent_appManager(appComponent);
        this.appManagerProvider = com_jess_arms_di_component_appcomponent_appmanager;
        this.t6WarnsListPresenterProvider = DoubleCheck.provider(T6WarnsListPresenter_Factory.create(this.t6WarnsListModelProvider, this.viewProvider, this.rxErrorHandlerProvider, this.applicationProvider, com_jess_arms_di_component_appcomponent_appmanager));
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.component.T6WarnsListComponent
    public void inject(T6WarnsListActivity t6WarnsListActivity) {
        injectT6WarnsListActivity(t6WarnsListActivity);
    }

    @CanIgnoreReturnValue
    public final T6WarnsListActivity injectT6WarnsListActivity(T6WarnsListActivity t6WarnsListActivity) {
        BaseActivity_MembersInjector.injectMPresenter(t6WarnsListActivity, this.t6WarnsListPresenterProvider.get());
        return t6WarnsListActivity;
    }

    public static final class Builder implements T6WarnsListComponent.Builder {
        public AppComponent appComponent;
        public T6WarnsListContract.View view;

        public Builder() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t6.component.T6WarnsListComponent.Builder
        public Builder view(T6WarnsListContract.View view) {
            this.view = (T6WarnsListContract.View) Preconditions.checkNotNull(view);
            return this;
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t6.component.T6WarnsListComponent.Builder
        public Builder appComponent(AppComponent appComponent) {
            this.appComponent = (AppComponent) Preconditions.checkNotNull(appComponent);
            return this;
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t6.component.T6WarnsListComponent.Builder
        public T6WarnsListComponent build() {
            Preconditions.checkBuilderRequirement(this.view, T6WarnsListContract.View.class);
            Preconditions.checkBuilderRequirement(this.appComponent, AppComponent.class);
            return new DaggerT6WarnsListComponent(this.appComponent, this.view);
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

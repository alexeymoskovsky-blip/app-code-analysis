package com.petkit.android.activities.petkitBleDevice.t7.component;

import android.app.Application;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.gson.Gson;
import com.jess.arms.base.BaseFragment_MembersInjector;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.AppManager;
import com.jess.arms.integration.IRepositoryManager;
import com.petkit.android.activities.petkitBleDevice.t7.T7LiveVideoListFragment;
import com.petkit.android.activities.petkitBleDevice.t7.component.T7LiveVideoListComponent;
import com.petkit.android.activities.petkitBleDevice.t7.contract.T7LiveVideoListContract;
import com.petkit.android.activities.petkitBleDevice.t7.model.T7LiveVideoListModel;
import com.petkit.android.activities.petkitBleDevice.t7.model.T7LiveVideoListModel_Factory;
import com.petkit.android.activities.petkitBleDevice.t7.presenter.T7LiveVideoListPresenter;
import com.petkit.android.activities.petkitBleDevice.t7.presenter.T7LiveVideoListPresenter_Factory;
import dagger.internal.DoubleCheck;
import dagger.internal.InstanceFactory;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/* JADX INFO: loaded from: classes5.dex */
public final class DaggerT7LiveVideoListComponent implements T7LiveVideoListComponent {
    public Provider<AppManager> appManagerProvider;
    public Provider<Application> applicationProvider;
    public Provider<Gson> gsonProvider;
    public Provider<IRepositoryManager> repositoryManagerProvider;
    public Provider<RxErrorHandler> rxErrorHandlerProvider;
    public Provider<T7LiveVideoListModel> t7LiveVideoListModelProvider;
    public Provider<T7LiveVideoListPresenter> t7LiveVideoListPresenterProvider;
    public Provider<T7LiveVideoListContract.View> viewProvider;

    public DaggerT7LiveVideoListComponent(AppComponent appComponent, T7LiveVideoListContract.View view) {
        initialize(appComponent, view);
    }

    public static T7LiveVideoListComponent.Builder builder() {
        return new Builder();
    }

    public final void initialize(AppComponent appComponent, T7LiveVideoListContract.View view) {
        this.repositoryManagerProvider = new com_jess_arms_di_component_AppComponent_repositoryManager(appComponent);
        this.gsonProvider = new com_jess_arms_di_component_AppComponent_gson(appComponent);
        com_jess_arms_di_component_AppComponent_application com_jess_arms_di_component_appcomponent_application = new com_jess_arms_di_component_AppComponent_application(appComponent);
        this.applicationProvider = com_jess_arms_di_component_appcomponent_application;
        this.t7LiveVideoListModelProvider = DoubleCheck.provider(T7LiveVideoListModel_Factory.create(this.repositoryManagerProvider, this.gsonProvider, com_jess_arms_di_component_appcomponent_application));
        this.viewProvider = InstanceFactory.create(view);
        this.rxErrorHandlerProvider = new com_jess_arms_di_component_AppComponent_rxErrorHandler(appComponent);
        com_jess_arms_di_component_AppComponent_appManager com_jess_arms_di_component_appcomponent_appmanager = new com_jess_arms_di_component_AppComponent_appManager(appComponent);
        this.appManagerProvider = com_jess_arms_di_component_appcomponent_appmanager;
        this.t7LiveVideoListPresenterProvider = DoubleCheck.provider(T7LiveVideoListPresenter_Factory.create(this.t7LiveVideoListModelProvider, this.viewProvider, this.rxErrorHandlerProvider, this.applicationProvider, com_jess_arms_di_component_appcomponent_appmanager));
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t7.component.T7LiveVideoListComponent
    public void inject(T7LiveVideoListFragment t7LiveVideoListFragment) {
        injectT7LiveVideoListFragment(t7LiveVideoListFragment);
    }

    @CanIgnoreReturnValue
    public final T7LiveVideoListFragment injectT7LiveVideoListFragment(T7LiveVideoListFragment t7LiveVideoListFragment) {
        BaseFragment_MembersInjector.injectMPresenter(t7LiveVideoListFragment, this.t7LiveVideoListPresenterProvider.get());
        return t7LiveVideoListFragment;
    }

    public static final class Builder implements T7LiveVideoListComponent.Builder {
        public AppComponent appComponent;
        public T7LiveVideoListContract.View view;

        public Builder() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t7.component.T7LiveVideoListComponent.Builder
        public Builder view(T7LiveVideoListContract.View view) {
            this.view = (T7LiveVideoListContract.View) Preconditions.checkNotNull(view);
            return this;
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t7.component.T7LiveVideoListComponent.Builder
        public Builder appComponent(AppComponent appComponent) {
            this.appComponent = (AppComponent) Preconditions.checkNotNull(appComponent);
            return this;
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t7.component.T7LiveVideoListComponent.Builder
        public T7LiveVideoListComponent build() {
            Preconditions.checkBuilderRequirement(this.view, T7LiveVideoListContract.View.class);
            Preconditions.checkBuilderRequirement(this.appComponent, AppComponent.class);
            return new DaggerT7LiveVideoListComponent(this.appComponent, this.view);
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

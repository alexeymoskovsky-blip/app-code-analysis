package com.petkit.android.activities.petkitBleDevice.t6.component;

import android.app.Application;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.gson.Gson;
import com.jess.arms.base.BaseFragment_MembersInjector;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.AppManager;
import com.jess.arms.integration.IRepositoryManager;
import com.petkit.android.activities.petkitBleDevice.t6.T6VideoPlayerFragment;
import com.petkit.android.activities.petkitBleDevice.t6.component.T6VideoPlayerComponent;
import com.petkit.android.activities.petkitBleDevice.t6.contract.T6VideoPlayerContract;
import com.petkit.android.activities.petkitBleDevice.t6.model.T6VideoPlayerModel;
import com.petkit.android.activities.petkitBleDevice.t6.model.T6VideoPlayerModel_Factory;
import com.petkit.android.activities.petkitBleDevice.t6.presenter.T6VideoPlayerPresenter;
import com.petkit.android.activities.petkitBleDevice.t6.presenter.T6VideoPlayerPresenter_Factory;
import dagger.internal.DoubleCheck;
import dagger.internal.InstanceFactory;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/* JADX INFO: loaded from: classes5.dex */
public final class DaggerT6VideoPlayerComponent implements T6VideoPlayerComponent {
    public Provider<AppManager> appManagerProvider;
    public Provider<Application> applicationProvider;
    public Provider<Gson> gsonProvider;
    public Provider<IRepositoryManager> repositoryManagerProvider;
    public Provider<RxErrorHandler> rxErrorHandlerProvider;
    public Provider<T6VideoPlayerModel> t6VideoPlayerModelProvider;
    public Provider<T6VideoPlayerPresenter> t6VideoPlayerPresenterProvider;
    public Provider<T6VideoPlayerContract.View> viewProvider;

    public DaggerT6VideoPlayerComponent(AppComponent appComponent, T6VideoPlayerContract.View view) {
        initialize(appComponent, view);
    }

    public static T6VideoPlayerComponent.Builder builder() {
        return new Builder();
    }

    public final void initialize(AppComponent appComponent, T6VideoPlayerContract.View view) {
        this.repositoryManagerProvider = new com_jess_arms_di_component_AppComponent_repositoryManager(appComponent);
        this.gsonProvider = new com_jess_arms_di_component_AppComponent_gson(appComponent);
        com_jess_arms_di_component_AppComponent_application com_jess_arms_di_component_appcomponent_application = new com_jess_arms_di_component_AppComponent_application(appComponent);
        this.applicationProvider = com_jess_arms_di_component_appcomponent_application;
        this.t6VideoPlayerModelProvider = DoubleCheck.provider(T6VideoPlayerModel_Factory.create(this.repositoryManagerProvider, this.gsonProvider, com_jess_arms_di_component_appcomponent_application));
        this.viewProvider = InstanceFactory.create(view);
        this.rxErrorHandlerProvider = new com_jess_arms_di_component_AppComponent_rxErrorHandler(appComponent);
        com_jess_arms_di_component_AppComponent_appManager com_jess_arms_di_component_appcomponent_appmanager = new com_jess_arms_di_component_AppComponent_appManager(appComponent);
        this.appManagerProvider = com_jess_arms_di_component_appcomponent_appmanager;
        this.t6VideoPlayerPresenterProvider = DoubleCheck.provider(T6VideoPlayerPresenter_Factory.create(this.t6VideoPlayerModelProvider, this.viewProvider, this.rxErrorHandlerProvider, this.applicationProvider, com_jess_arms_di_component_appcomponent_appmanager));
    }

    @Override // com.petkit.android.activities.petkitBleDevice.t6.component.T6VideoPlayerComponent
    public void inject(T6VideoPlayerFragment t6VideoPlayerFragment) {
        injectT6VideoPlayerFragment(t6VideoPlayerFragment);
    }

    @CanIgnoreReturnValue
    public final T6VideoPlayerFragment injectT6VideoPlayerFragment(T6VideoPlayerFragment t6VideoPlayerFragment) {
        BaseFragment_MembersInjector.injectMPresenter(t6VideoPlayerFragment, this.t6VideoPlayerPresenterProvider.get());
        return t6VideoPlayerFragment;
    }

    public static final class Builder implements T6VideoPlayerComponent.Builder {
        public AppComponent appComponent;
        public T6VideoPlayerContract.View view;

        public Builder() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t6.component.T6VideoPlayerComponent.Builder
        public Builder view(T6VideoPlayerContract.View view) {
            this.view = (T6VideoPlayerContract.View) Preconditions.checkNotNull(view);
            return this;
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t6.component.T6VideoPlayerComponent.Builder
        public Builder appComponent(AppComponent appComponent) {
            this.appComponent = (AppComponent) Preconditions.checkNotNull(appComponent);
            return this;
        }

        @Override // com.petkit.android.activities.petkitBleDevice.t6.component.T6VideoPlayerComponent.Builder
        public T6VideoPlayerComponent build() {
            Preconditions.checkBuilderRequirement(this.view, T6VideoPlayerContract.View.class);
            Preconditions.checkBuilderRequirement(this.appComponent, AppComponent.class);
            return new DaggerT6VideoPlayerComponent(this.appComponent, this.view);
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

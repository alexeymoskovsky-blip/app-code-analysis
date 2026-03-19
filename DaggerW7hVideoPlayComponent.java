package com.petkit.android.activities.petkitBleDevice.w7h.component;

import android.app.Application;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.gson.Gson;
import com.jess.arms.base.BaseFragment_MembersInjector;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.AppManager;
import com.jess.arms.integration.IRepositoryManager;
import com.petkit.android.activities.petkitBleDevice.w7h.W7hVideoPlayFragment;
import com.petkit.android.activities.petkitBleDevice.w7h.component.W7hVideoPlayComponent;
import com.petkit.android.activities.petkitBleDevice.w7h.contract.W7hVideoPlayContract;
import com.petkit.android.activities.petkitBleDevice.w7h.model.W7hVideoPlayModel;
import com.petkit.android.activities.petkitBleDevice.w7h.model.W7hVideoPlayModel_Factory;
import com.petkit.android.activities.petkitBleDevice.w7h.presenter.W7hVideoPlayPresenter;
import com.petkit.android.activities.petkitBleDevice.w7h.presenter.W7hVideoPlayPresenter_Factory;
import dagger.internal.DoubleCheck;
import dagger.internal.InstanceFactory;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/* JADX INFO: loaded from: classes5.dex */
public final class DaggerW7hVideoPlayComponent implements W7hVideoPlayComponent {
    public Provider<AppManager> appManagerProvider;
    public Provider<Application> applicationProvider;
    public Provider<Gson> gsonProvider;
    public Provider<IRepositoryManager> repositoryManagerProvider;
    public Provider<RxErrorHandler> rxErrorHandlerProvider;
    public Provider<W7hVideoPlayContract.View> viewProvider;
    public Provider<W7hVideoPlayModel> w7hVideoPlayModelProvider;
    public Provider<W7hVideoPlayPresenter> w7hVideoPlayPresenterProvider;

    public DaggerW7hVideoPlayComponent(AppComponent appComponent, W7hVideoPlayContract.View view) {
        initialize(appComponent, view);
    }

    public static W7hVideoPlayComponent.Builder builder() {
        return new Builder();
    }

    public final void initialize(AppComponent appComponent, W7hVideoPlayContract.View view) {
        this.repositoryManagerProvider = new com_jess_arms_di_component_AppComponent_repositoryManager(appComponent);
        this.gsonProvider = new com_jess_arms_di_component_AppComponent_gson(appComponent);
        com_jess_arms_di_component_AppComponent_application com_jess_arms_di_component_appcomponent_application = new com_jess_arms_di_component_AppComponent_application(appComponent);
        this.applicationProvider = com_jess_arms_di_component_appcomponent_application;
        this.w7hVideoPlayModelProvider = DoubleCheck.provider(W7hVideoPlayModel_Factory.create(this.repositoryManagerProvider, this.gsonProvider, com_jess_arms_di_component_appcomponent_application));
        this.viewProvider = InstanceFactory.create(view);
        this.rxErrorHandlerProvider = new com_jess_arms_di_component_AppComponent_rxErrorHandler(appComponent);
        com_jess_arms_di_component_AppComponent_appManager com_jess_arms_di_component_appcomponent_appmanager = new com_jess_arms_di_component_AppComponent_appManager(appComponent);
        this.appManagerProvider = com_jess_arms_di_component_appcomponent_appmanager;
        this.w7hVideoPlayPresenterProvider = DoubleCheck.provider(W7hVideoPlayPresenter_Factory.create(this.w7hVideoPlayModelProvider, this.viewProvider, this.rxErrorHandlerProvider, this.applicationProvider, com_jess_arms_di_component_appcomponent_appmanager));
    }

    @Override // com.petkit.android.activities.petkitBleDevice.w7h.component.W7hVideoPlayComponent
    public void inject(W7hVideoPlayFragment w7hVideoPlayFragment) {
        injectW7hVideoPlayFragment(w7hVideoPlayFragment);
    }

    @CanIgnoreReturnValue
    public final W7hVideoPlayFragment injectW7hVideoPlayFragment(W7hVideoPlayFragment w7hVideoPlayFragment) {
        BaseFragment_MembersInjector.injectMPresenter(w7hVideoPlayFragment, this.w7hVideoPlayPresenterProvider.get());
        return w7hVideoPlayFragment;
    }

    public static final class Builder implements W7hVideoPlayComponent.Builder {
        public AppComponent appComponent;
        public W7hVideoPlayContract.View view;

        public Builder() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.w7h.component.W7hVideoPlayComponent.Builder
        public Builder view(W7hVideoPlayContract.View view) {
            this.view = (W7hVideoPlayContract.View) Preconditions.checkNotNull(view);
            return this;
        }

        @Override // com.petkit.android.activities.petkitBleDevice.w7h.component.W7hVideoPlayComponent.Builder
        public Builder appComponent(AppComponent appComponent) {
            this.appComponent = (AppComponent) Preconditions.checkNotNull(appComponent);
            return this;
        }

        @Override // com.petkit.android.activities.petkitBleDevice.w7h.component.W7hVideoPlayComponent.Builder
        public W7hVideoPlayComponent build() {
            Preconditions.checkBuilderRequirement(this.view, W7hVideoPlayContract.View.class);
            Preconditions.checkBuilderRequirement(this.appComponent, AppComponent.class);
            return new DaggerW7hVideoPlayComponent(this.appComponent, this.view);
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

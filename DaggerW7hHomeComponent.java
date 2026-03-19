package com.petkit.android.activities.petkitBleDevice.w7h.component;

import android.app.Application;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.gson.Gson;
import com.jess.arms.base.BaseActivity_MembersInjector;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.AppManager;
import com.jess.arms.integration.IRepositoryManager;
import com.petkit.android.activities.petkitBleDevice.w7h.W7hHomeActivity;
import com.petkit.android.activities.petkitBleDevice.w7h.component.W7hHomeComponent;
import com.petkit.android.activities.petkitBleDevice.w7h.contract.W7hHomeContract;
import com.petkit.android.activities.petkitBleDevice.w7h.model.W7hHomeModel;
import com.petkit.android.activities.petkitBleDevice.w7h.model.W7hHomeModel_Factory;
import com.petkit.android.activities.petkitBleDevice.w7h.presenter.W7hHomePresenter;
import com.petkit.android.activities.petkitBleDevice.w7h.presenter.W7hHomePresenter_Factory;
import dagger.internal.DoubleCheck;
import dagger.internal.InstanceFactory;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/* JADX INFO: loaded from: classes5.dex */
public final class DaggerW7hHomeComponent implements W7hHomeComponent {
    public Provider<AppManager> appManagerProvider;
    public Provider<Application> applicationProvider;
    public Provider<Gson> gsonProvider;
    public Provider<IRepositoryManager> repositoryManagerProvider;
    public Provider<RxErrorHandler> rxErrorHandlerProvider;
    public Provider<W7hHomeContract.View> viewProvider;
    public Provider<W7hHomeModel> w7hHomeModelProvider;
    public Provider<W7hHomePresenter> w7hHomePresenterProvider;

    public DaggerW7hHomeComponent(AppComponent appComponent, W7hHomeContract.View view) {
        initialize(appComponent, view);
    }

    public static W7hHomeComponent.Builder builder() {
        return new Builder();
    }

    public final void initialize(AppComponent appComponent, W7hHomeContract.View view) {
        this.repositoryManagerProvider = new com_jess_arms_di_component_AppComponent_repositoryManager(appComponent);
        this.gsonProvider = new com_jess_arms_di_component_AppComponent_gson(appComponent);
        com_jess_arms_di_component_AppComponent_application com_jess_arms_di_component_appcomponent_application = new com_jess_arms_di_component_AppComponent_application(appComponent);
        this.applicationProvider = com_jess_arms_di_component_appcomponent_application;
        this.w7hHomeModelProvider = DoubleCheck.provider(W7hHomeModel_Factory.create(this.repositoryManagerProvider, this.gsonProvider, com_jess_arms_di_component_appcomponent_application));
        this.viewProvider = InstanceFactory.create(view);
        this.rxErrorHandlerProvider = new com_jess_arms_di_component_AppComponent_rxErrorHandler(appComponent);
        com_jess_arms_di_component_AppComponent_appManager com_jess_arms_di_component_appcomponent_appmanager = new com_jess_arms_di_component_AppComponent_appManager(appComponent);
        this.appManagerProvider = com_jess_arms_di_component_appcomponent_appmanager;
        this.w7hHomePresenterProvider = DoubleCheck.provider(W7hHomePresenter_Factory.create(this.w7hHomeModelProvider, this.viewProvider, this.rxErrorHandlerProvider, this.applicationProvider, com_jess_arms_di_component_appcomponent_appmanager));
    }

    @Override // com.petkit.android.activities.petkitBleDevice.w7h.component.W7hHomeComponent
    public void inject(W7hHomeActivity w7hHomeActivity) {
        injectW7hHomeActivity(w7hHomeActivity);
    }

    @CanIgnoreReturnValue
    public final W7hHomeActivity injectW7hHomeActivity(W7hHomeActivity w7hHomeActivity) {
        BaseActivity_MembersInjector.injectMPresenter(w7hHomeActivity, this.w7hHomePresenterProvider.get());
        return w7hHomeActivity;
    }

    public static final class Builder implements W7hHomeComponent.Builder {
        public AppComponent appComponent;
        public W7hHomeContract.View view;

        public Builder() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.w7h.component.W7hHomeComponent.Builder
        public Builder view(W7hHomeContract.View view) {
            this.view = (W7hHomeContract.View) Preconditions.checkNotNull(view);
            return this;
        }

        @Override // com.petkit.android.activities.petkitBleDevice.w7h.component.W7hHomeComponent.Builder
        public Builder appComponent(AppComponent appComponent) {
            this.appComponent = (AppComponent) Preconditions.checkNotNull(appComponent);
            return this;
        }

        @Override // com.petkit.android.activities.petkitBleDevice.w7h.component.W7hHomeComponent.Builder
        public W7hHomeComponent build() {
            Preconditions.checkBuilderRequirement(this.view, W7hHomeContract.View.class);
            Preconditions.checkBuilderRequirement(this.appComponent, AppComponent.class);
            return new DaggerW7hHomeComponent(this.appComponent, this.view);
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

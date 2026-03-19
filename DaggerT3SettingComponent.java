package com.petkit.android.activities.petkitBleDevice.component;

import android.app.Application;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.gson.Gson;
import com.jess.arms.base.BaseActivity_MembersInjector;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.IRepositoryManager;
import com.petkit.android.activities.petkitBleDevice.T3SettingActivity;
import com.petkit.android.activities.petkitBleDevice.component.T3SettingComponent;
import com.petkit.android.activities.petkitBleDevice.contract.T3SettingContract;
import com.petkit.android.activities.petkitBleDevice.model.T3SettingModel;
import com.petkit.android.activities.petkitBleDevice.model.T3SettingModel_Factory;
import com.petkit.android.activities.petkitBleDevice.presenter.T3SettingPresenter;
import com.petkit.android.activities.petkitBleDevice.presenter.T3SettingPresenter_Factory;
import dagger.internal.DoubleCheck;
import dagger.internal.InstanceFactory;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/* JADX INFO: loaded from: classes4.dex */
public final class DaggerT3SettingComponent implements T3SettingComponent {
    public Provider<Application> applicationProvider;
    public Provider<Gson> gsonProvider;
    public Provider<IRepositoryManager> repositoryManagerProvider;
    public Provider<RxErrorHandler> rxErrorHandlerProvider;
    public Provider<T3SettingModel> t3SettingModelProvider;
    public Provider<T3SettingPresenter> t3SettingPresenterProvider;
    public Provider<T3SettingContract.View> viewProvider;

    public DaggerT3SettingComponent(AppComponent appComponent, T3SettingContract.View view) {
        initialize(appComponent, view);
    }

    public static T3SettingComponent.Builder builder() {
        return new Builder();
    }

    public final void initialize(AppComponent appComponent, T3SettingContract.View view) {
        this.repositoryManagerProvider = new com_jess_arms_di_component_AppComponent_repositoryManager(appComponent);
        this.gsonProvider = new com_jess_arms_di_component_AppComponent_gson(appComponent);
        com_jess_arms_di_component_AppComponent_application com_jess_arms_di_component_appcomponent_application = new com_jess_arms_di_component_AppComponent_application(appComponent);
        this.applicationProvider = com_jess_arms_di_component_appcomponent_application;
        this.t3SettingModelProvider = DoubleCheck.provider(T3SettingModel_Factory.create(this.repositoryManagerProvider, this.gsonProvider, com_jess_arms_di_component_appcomponent_application));
        this.viewProvider = InstanceFactory.create(view);
        com_jess_arms_di_component_AppComponent_rxErrorHandler com_jess_arms_di_component_appcomponent_rxerrorhandler = new com_jess_arms_di_component_AppComponent_rxErrorHandler(appComponent);
        this.rxErrorHandlerProvider = com_jess_arms_di_component_appcomponent_rxerrorhandler;
        this.t3SettingPresenterProvider = DoubleCheck.provider(T3SettingPresenter_Factory.create(this.t3SettingModelProvider, this.viewProvider, com_jess_arms_di_component_appcomponent_rxerrorhandler, this.applicationProvider));
    }

    @Override // com.petkit.android.activities.petkitBleDevice.component.T3SettingComponent
    public void inject(T3SettingActivity t3SettingActivity) {
        injectT3SettingActivity(t3SettingActivity);
    }

    @CanIgnoreReturnValue
    public final T3SettingActivity injectT3SettingActivity(T3SettingActivity t3SettingActivity) {
        BaseActivity_MembersInjector.injectMPresenter(t3SettingActivity, this.t3SettingPresenterProvider.get());
        return t3SettingActivity;
    }

    public static final class Builder implements T3SettingComponent.Builder {
        public AppComponent appComponent;
        public T3SettingContract.View view;

        public Builder() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.component.T3SettingComponent.Builder
        public Builder view(T3SettingContract.View view) {
            this.view = (T3SettingContract.View) Preconditions.checkNotNull(view);
            return this;
        }

        @Override // com.petkit.android.activities.petkitBleDevice.component.T3SettingComponent.Builder
        public Builder appComponent(AppComponent appComponent) {
            this.appComponent = (AppComponent) Preconditions.checkNotNull(appComponent);
            return this;
        }

        @Override // com.petkit.android.activities.petkitBleDevice.component.T3SettingComponent.Builder
        public T3SettingComponent build() {
            Preconditions.checkBuilderRequirement(this.view, T3SettingContract.View.class);
            Preconditions.checkBuilderRequirement(this.appComponent, AppComponent.class);
            return new DaggerT3SettingComponent(this.appComponent, this.view);
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

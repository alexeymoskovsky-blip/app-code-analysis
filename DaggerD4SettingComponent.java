package com.petkit.android.activities.petkitBleDevice.d4.component;

import android.app.Application;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.gson.Gson;
import com.jess.arms.base.BaseActivity_MembersInjector;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.IRepositoryManager;
import com.petkit.android.activities.petkitBleDevice.d4.D4SettingActivity;
import com.petkit.android.activities.petkitBleDevice.d4.component.D4SettingComponent;
import com.petkit.android.activities.petkitBleDevice.d4.contract.D4SettingContract;
import com.petkit.android.activities.petkitBleDevice.d4.model.D4SettingModel;
import com.petkit.android.activities.petkitBleDevice.d4.model.D4SettingModel_Factory;
import com.petkit.android.activities.petkitBleDevice.d4.presenter.D4SettingPresenter;
import com.petkit.android.activities.petkitBleDevice.d4.presenter.D4SettingPresenter_Factory;
import dagger.internal.DoubleCheck;
import dagger.internal.InstanceFactory;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/* JADX INFO: loaded from: classes4.dex */
public final class DaggerD4SettingComponent implements D4SettingComponent {
    public Provider<Application> applicationProvider;
    public Provider<D4SettingModel> d4SettingModelProvider;
    public Provider<D4SettingPresenter> d4SettingPresenterProvider;
    public Provider<Gson> gsonProvider;
    public Provider<IRepositoryManager> repositoryManagerProvider;
    public Provider<RxErrorHandler> rxErrorHandlerProvider;
    public Provider<D4SettingContract.View> viewProvider;

    public DaggerD4SettingComponent(AppComponent appComponent, D4SettingContract.View view) {
        initialize(appComponent, view);
    }

    public static D4SettingComponent.Builder builder() {
        return new Builder();
    }

    public final void initialize(AppComponent appComponent, D4SettingContract.View view) {
        this.repositoryManagerProvider = new com_jess_arms_di_component_AppComponent_repositoryManager(appComponent);
        this.gsonProvider = new com_jess_arms_di_component_AppComponent_gson(appComponent);
        com_jess_arms_di_component_AppComponent_application com_jess_arms_di_component_appcomponent_application = new com_jess_arms_di_component_AppComponent_application(appComponent);
        this.applicationProvider = com_jess_arms_di_component_appcomponent_application;
        this.d4SettingModelProvider = DoubleCheck.provider(D4SettingModel_Factory.create(this.repositoryManagerProvider, this.gsonProvider, com_jess_arms_di_component_appcomponent_application));
        this.viewProvider = InstanceFactory.create(view);
        com_jess_arms_di_component_AppComponent_rxErrorHandler com_jess_arms_di_component_appcomponent_rxerrorhandler = new com_jess_arms_di_component_AppComponent_rxErrorHandler(appComponent);
        this.rxErrorHandlerProvider = com_jess_arms_di_component_appcomponent_rxerrorhandler;
        this.d4SettingPresenterProvider = DoubleCheck.provider(D4SettingPresenter_Factory.create(this.d4SettingModelProvider, this.viewProvider, com_jess_arms_di_component_appcomponent_rxerrorhandler, this.applicationProvider));
    }

    @Override // com.petkit.android.activities.petkitBleDevice.d4.component.D4SettingComponent
    public void inject(D4SettingActivity d4SettingActivity) {
        injectD4SettingActivity(d4SettingActivity);
    }

    @CanIgnoreReturnValue
    public final D4SettingActivity injectD4SettingActivity(D4SettingActivity d4SettingActivity) {
        BaseActivity_MembersInjector.injectMPresenter(d4SettingActivity, this.d4SettingPresenterProvider.get());
        return d4SettingActivity;
    }

    public static final class Builder implements D4SettingComponent.Builder {
        public AppComponent appComponent;
        public D4SettingContract.View view;

        public Builder() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.d4.component.D4SettingComponent.Builder
        public Builder view(D4SettingContract.View view) {
            this.view = (D4SettingContract.View) Preconditions.checkNotNull(view);
            return this;
        }

        @Override // com.petkit.android.activities.petkitBleDevice.d4.component.D4SettingComponent.Builder
        public Builder appComponent(AppComponent appComponent) {
            this.appComponent = (AppComponent) Preconditions.checkNotNull(appComponent);
            return this;
        }

        @Override // com.petkit.android.activities.petkitBleDevice.d4.component.D4SettingComponent.Builder
        public D4SettingComponent build() {
            Preconditions.checkBuilderRequirement(this.view, D4SettingContract.View.class);
            Preconditions.checkBuilderRequirement(this.appComponent, AppComponent.class);
            return new DaggerD4SettingComponent(this.appComponent, this.view);
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

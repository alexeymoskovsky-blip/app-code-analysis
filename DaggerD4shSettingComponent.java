package com.petkit.android.activities.petkitBleDevice.d4sh.component;

import android.app.Application;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.gson.Gson;
import com.jess.arms.base.BaseActivity_MembersInjector;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.AppManager;
import com.jess.arms.integration.IRepositoryManager;
import com.petkit.android.activities.petkitBleDevice.d4sh.D4shSettingActivity;
import com.petkit.android.activities.petkitBleDevice.d4sh.component.D4shSettingComponent;
import com.petkit.android.activities.petkitBleDevice.d4sh.contract.D4shSettingContract;
import com.petkit.android.activities.petkitBleDevice.d4sh.model.D4shSettingModel;
import com.petkit.android.activities.petkitBleDevice.d4sh.model.D4shSettingModel_Factory;
import com.petkit.android.activities.petkitBleDevice.d4sh.presenter.D4shSettingPresenter;
import com.petkit.android.activities.petkitBleDevice.d4sh.presenter.D4shSettingPresenter_Factory;
import dagger.internal.DoubleCheck;
import dagger.internal.InstanceFactory;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/* JADX INFO: loaded from: classes4.dex */
public final class DaggerD4shSettingComponent implements D4shSettingComponent {
    public Provider<AppManager> appManagerProvider;
    public Provider<Application> applicationProvider;
    public Provider<D4shSettingModel> d4shSettingModelProvider;
    public Provider<D4shSettingPresenter> d4shSettingPresenterProvider;
    public Provider<Gson> gsonProvider;
    public Provider<IRepositoryManager> repositoryManagerProvider;
    public Provider<RxErrorHandler> rxErrorHandlerProvider;
    public Provider<D4shSettingContract.View> viewProvider;

    public DaggerD4shSettingComponent(AppComponent appComponent, D4shSettingContract.View view) {
        initialize(appComponent, view);
    }

    public static D4shSettingComponent.Builder builder() {
        return new Builder();
    }

    public final void initialize(AppComponent appComponent, D4shSettingContract.View view) {
        this.repositoryManagerProvider = new com_jess_arms_di_component_AppComponent_repositoryManager(appComponent);
        this.gsonProvider = new com_jess_arms_di_component_AppComponent_gson(appComponent);
        com_jess_arms_di_component_AppComponent_application com_jess_arms_di_component_appcomponent_application = new com_jess_arms_di_component_AppComponent_application(appComponent);
        this.applicationProvider = com_jess_arms_di_component_appcomponent_application;
        this.d4shSettingModelProvider = DoubleCheck.provider(D4shSettingModel_Factory.create(this.repositoryManagerProvider, this.gsonProvider, com_jess_arms_di_component_appcomponent_application));
        this.viewProvider = InstanceFactory.create(view);
        this.rxErrorHandlerProvider = new com_jess_arms_di_component_AppComponent_rxErrorHandler(appComponent);
        com_jess_arms_di_component_AppComponent_appManager com_jess_arms_di_component_appcomponent_appmanager = new com_jess_arms_di_component_AppComponent_appManager(appComponent);
        this.appManagerProvider = com_jess_arms_di_component_appcomponent_appmanager;
        this.d4shSettingPresenterProvider = DoubleCheck.provider(D4shSettingPresenter_Factory.create(this.d4shSettingModelProvider, this.viewProvider, this.rxErrorHandlerProvider, this.applicationProvider, com_jess_arms_di_component_appcomponent_appmanager));
    }

    @Override // com.petkit.android.activities.petkitBleDevice.d4sh.component.D4shSettingComponent
    public void inject(D4shSettingActivity d4shSettingActivity) {
        injectD4shSettingActivity(d4shSettingActivity);
    }

    @CanIgnoreReturnValue
    public final D4shSettingActivity injectD4shSettingActivity(D4shSettingActivity d4shSettingActivity) {
        BaseActivity_MembersInjector.injectMPresenter(d4shSettingActivity, this.d4shSettingPresenterProvider.get());
        return d4shSettingActivity;
    }

    public static final class Builder implements D4shSettingComponent.Builder {
        public AppComponent appComponent;
        public D4shSettingContract.View view;

        public Builder() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.d4sh.component.D4shSettingComponent.Builder
        public Builder view(D4shSettingContract.View view) {
            this.view = (D4shSettingContract.View) Preconditions.checkNotNull(view);
            return this;
        }

        @Override // com.petkit.android.activities.petkitBleDevice.d4sh.component.D4shSettingComponent.Builder
        public Builder appComponent(AppComponent appComponent) {
            this.appComponent = (AppComponent) Preconditions.checkNotNull(appComponent);
            return this;
        }

        @Override // com.petkit.android.activities.petkitBleDevice.d4sh.component.D4shSettingComponent.Builder
        public D4shSettingComponent build() {
            Preconditions.checkBuilderRequirement(this.view, D4shSettingContract.View.class);
            Preconditions.checkBuilderRequirement(this.appComponent, AppComponent.class);
            return new DaggerD4shSettingComponent(this.appComponent, this.view);
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

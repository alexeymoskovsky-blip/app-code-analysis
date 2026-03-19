package com.petkit.android.activities.petkitBleDevice.component;

import android.app.Application;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.gson.Gson;
import com.jess.arms.base.BaseActivity_MembersInjector;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.IRepositoryManager;
import com.petkit.android.activities.petkitBleDevice.BleDeviceBindProgressActivity;
import com.petkit.android.activities.petkitBleDevice.component.BleDeviceBindProgressComponent;
import com.petkit.android.activities.petkitBleDevice.contract.BleDeviceBindProgressContract;
import com.petkit.android.activities.petkitBleDevice.model.BleDeviceBindProgressModel;
import com.petkit.android.activities.petkitBleDevice.model.BleDeviceBindProgressModel_Factory;
import com.petkit.android.activities.petkitBleDevice.presenter.BleDeviceBindProgressPresenter;
import com.petkit.android.activities.petkitBleDevice.presenter.BleDeviceBindProgressPresenter_Factory;
import dagger.internal.DoubleCheck;
import dagger.internal.InstanceFactory;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/* JADX INFO: loaded from: classes4.dex */
public final class DaggerBleDeviceBindProgressComponent implements BleDeviceBindProgressComponent {
    public Provider<Application> applicationProvider;
    public Provider<BleDeviceBindProgressModel> bleDeviceBindProgressModelProvider;
    public Provider<BleDeviceBindProgressPresenter> bleDeviceBindProgressPresenterProvider;
    public Provider<Gson> gsonProvider;
    public Provider<IRepositoryManager> repositoryManagerProvider;
    public Provider<RxErrorHandler> rxErrorHandlerProvider;
    public Provider<BleDeviceBindProgressContract.View> viewProvider;

    public DaggerBleDeviceBindProgressComponent(AppComponent appComponent, BleDeviceBindProgressContract.View view) {
        initialize(appComponent, view);
    }

    public static BleDeviceBindProgressComponent.Builder builder() {
        return new Builder();
    }

    public final void initialize(AppComponent appComponent, BleDeviceBindProgressContract.View view) {
        this.repositoryManagerProvider = new com_jess_arms_di_component_AppComponent_repositoryManager(appComponent);
        this.gsonProvider = new com_jess_arms_di_component_AppComponent_gson(appComponent);
        com_jess_arms_di_component_AppComponent_application com_jess_arms_di_component_appcomponent_application = new com_jess_arms_di_component_AppComponent_application(appComponent);
        this.applicationProvider = com_jess_arms_di_component_appcomponent_application;
        this.bleDeviceBindProgressModelProvider = DoubleCheck.provider(BleDeviceBindProgressModel_Factory.create(this.repositoryManagerProvider, this.gsonProvider, com_jess_arms_di_component_appcomponent_application));
        this.viewProvider = InstanceFactory.create(view);
        com_jess_arms_di_component_AppComponent_rxErrorHandler com_jess_arms_di_component_appcomponent_rxerrorhandler = new com_jess_arms_di_component_AppComponent_rxErrorHandler(appComponent);
        this.rxErrorHandlerProvider = com_jess_arms_di_component_appcomponent_rxerrorhandler;
        this.bleDeviceBindProgressPresenterProvider = DoubleCheck.provider(BleDeviceBindProgressPresenter_Factory.create(this.bleDeviceBindProgressModelProvider, this.viewProvider, com_jess_arms_di_component_appcomponent_rxerrorhandler, this.applicationProvider));
    }

    @Override // com.petkit.android.activities.petkitBleDevice.component.BleDeviceBindProgressComponent
    public void inject(BleDeviceBindProgressActivity bleDeviceBindProgressActivity) {
        injectBleDeviceBindProgressActivity(bleDeviceBindProgressActivity);
    }

    @CanIgnoreReturnValue
    public final BleDeviceBindProgressActivity injectBleDeviceBindProgressActivity(BleDeviceBindProgressActivity bleDeviceBindProgressActivity) {
        BaseActivity_MembersInjector.injectMPresenter(bleDeviceBindProgressActivity, this.bleDeviceBindProgressPresenterProvider.get());
        return bleDeviceBindProgressActivity;
    }

    public static final class Builder implements BleDeviceBindProgressComponent.Builder {
        public AppComponent appComponent;
        public BleDeviceBindProgressContract.View view;

        public Builder() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.component.BleDeviceBindProgressComponent.Builder
        public Builder view(BleDeviceBindProgressContract.View view) {
            this.view = (BleDeviceBindProgressContract.View) Preconditions.checkNotNull(view);
            return this;
        }

        @Override // com.petkit.android.activities.petkitBleDevice.component.BleDeviceBindProgressComponent.Builder
        public Builder appComponent(AppComponent appComponent) {
            this.appComponent = (AppComponent) Preconditions.checkNotNull(appComponent);
            return this;
        }

        @Override // com.petkit.android.activities.petkitBleDevice.component.BleDeviceBindProgressComponent.Builder
        public BleDeviceBindProgressComponent build() {
            Preconditions.checkBuilderRequirement(this.view, BleDeviceBindProgressContract.View.class);
            Preconditions.checkBuilderRequirement(this.appComponent, AppComponent.class);
            return new DaggerBleDeviceBindProgressComponent(this.appComponent, this.view);
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

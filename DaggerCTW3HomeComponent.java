package com.petkit.android.activities.petkitBleDevice.ctw3.component;

import android.app.Application;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.gson.Gson;
import com.jess.arms.base.BaseActivity_MembersInjector;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.AppManager;
import com.jess.arms.integration.IRepositoryManager;
import com.petkit.android.activities.petkitBleDevice.ctw3.CTW3HomeActivity;
import com.petkit.android.activities.petkitBleDevice.ctw3.component.CTW3HomeComponent;
import com.petkit.android.activities.petkitBleDevice.ctw3.contract.CTW3HomeContract;
import com.petkit.android.activities.petkitBleDevice.ctw3.model.CTW3HomeModel;
import com.petkit.android.activities.petkitBleDevice.ctw3.model.CTW3HomeModel_Factory;
import com.petkit.android.activities.petkitBleDevice.ctw3.presenter.CTW3HomePresenter;
import com.petkit.android.activities.petkitBleDevice.ctw3.presenter.CTW3HomePresenter_Factory;
import dagger.internal.DoubleCheck;
import dagger.internal.InstanceFactory;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/* JADX INFO: loaded from: classes4.dex */
public final class DaggerCTW3HomeComponent implements CTW3HomeComponent {
    public Provider<AppManager> appManagerProvider;
    public Provider<Application> applicationProvider;
    public Provider<CTW3HomeModel> cTW3HomeModelProvider;
    public Provider<CTW3HomePresenter> cTW3HomePresenterProvider;
    public Provider<Gson> gsonProvider;
    public Provider<IRepositoryManager> repositoryManagerProvider;
    public Provider<RxErrorHandler> rxErrorHandlerProvider;
    public Provider<CTW3HomeContract.View> viewProvider;

    public DaggerCTW3HomeComponent(AppComponent appComponent, CTW3HomeContract.View view) {
        initialize(appComponent, view);
    }

    public static CTW3HomeComponent.Builder builder() {
        return new Builder();
    }

    public final void initialize(AppComponent appComponent, CTW3HomeContract.View view) {
        this.repositoryManagerProvider = new com_jess_arms_di_component_AppComponent_repositoryManager(appComponent);
        this.gsonProvider = new com_jess_arms_di_component_AppComponent_gson(appComponent);
        com_jess_arms_di_component_AppComponent_application com_jess_arms_di_component_appcomponent_application = new com_jess_arms_di_component_AppComponent_application(appComponent);
        this.applicationProvider = com_jess_arms_di_component_appcomponent_application;
        this.cTW3HomeModelProvider = DoubleCheck.provider(CTW3HomeModel_Factory.create(this.repositoryManagerProvider, this.gsonProvider, com_jess_arms_di_component_appcomponent_application));
        this.viewProvider = InstanceFactory.create(view);
        this.rxErrorHandlerProvider = new com_jess_arms_di_component_AppComponent_rxErrorHandler(appComponent);
        com_jess_arms_di_component_AppComponent_appManager com_jess_arms_di_component_appcomponent_appmanager = new com_jess_arms_di_component_AppComponent_appManager(appComponent);
        this.appManagerProvider = com_jess_arms_di_component_appcomponent_appmanager;
        this.cTW3HomePresenterProvider = DoubleCheck.provider(CTW3HomePresenter_Factory.create(this.cTW3HomeModelProvider, this.viewProvider, this.rxErrorHandlerProvider, this.applicationProvider, com_jess_arms_di_component_appcomponent_appmanager));
    }

    @Override // com.petkit.android.activities.petkitBleDevice.ctw3.component.CTW3HomeComponent
    public void inject(CTW3HomeActivity cTW3HomeActivity) {
        injectCTW3HomeActivity(cTW3HomeActivity);
    }

    @CanIgnoreReturnValue
    public final CTW3HomeActivity injectCTW3HomeActivity(CTW3HomeActivity cTW3HomeActivity) {
        BaseActivity_MembersInjector.injectMPresenter(cTW3HomeActivity, this.cTW3HomePresenterProvider.get());
        return cTW3HomeActivity;
    }

    public static final class Builder implements CTW3HomeComponent.Builder {
        public AppComponent appComponent;
        public CTW3HomeContract.View view;

        public Builder() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.ctw3.component.CTW3HomeComponent.Builder
        public Builder view(CTW3HomeContract.View view) {
            this.view = (CTW3HomeContract.View) Preconditions.checkNotNull(view);
            return this;
        }

        @Override // com.petkit.android.activities.petkitBleDevice.ctw3.component.CTW3HomeComponent.Builder
        public Builder appComponent(AppComponent appComponent) {
            this.appComponent = (AppComponent) Preconditions.checkNotNull(appComponent);
            return this;
        }

        @Override // com.petkit.android.activities.petkitBleDevice.ctw3.component.CTW3HomeComponent.Builder
        public CTW3HomeComponent build() {
            Preconditions.checkBuilderRequirement(this.view, CTW3HomeContract.View.class);
            Preconditions.checkBuilderRequirement(this.appComponent, AppComponent.class);
            return new DaggerCTW3HomeComponent(this.appComponent, this.view);
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

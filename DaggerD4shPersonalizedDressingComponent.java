package com.petkit.android.activities.petkitBleDevice.d4sh.component;

import android.app.Application;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.gson.Gson;
import com.jess.arms.base.BaseActivity_MembersInjector;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.AppManager;
import com.jess.arms.integration.IRepositoryManager;
import com.petkit.android.activities.petkitBleDevice.d4sh.D4shPersonalizedDressingActivity;
import com.petkit.android.activities.petkitBleDevice.d4sh.component.D4shPersonalizedDressingComponent;
import com.petkit.android.activities.petkitBleDevice.d4sh.contract.D4shPersonalizedDressingContract;
import com.petkit.android.activities.petkitBleDevice.d4sh.model.D4shPersonalizedDressingModel;
import com.petkit.android.activities.petkitBleDevice.d4sh.model.D4shPersonalizedDressingModel_Factory;
import com.petkit.android.activities.petkitBleDevice.d4sh.presenter.D4shPersonalizedDressingPresenter;
import com.petkit.android.activities.petkitBleDevice.d4sh.presenter.D4shPersonalizedDressingPresenter_Factory;
import dagger.internal.DoubleCheck;
import dagger.internal.InstanceFactory;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/* JADX INFO: loaded from: classes4.dex */
public final class DaggerD4shPersonalizedDressingComponent implements D4shPersonalizedDressingComponent {
    public Provider<AppManager> appManagerProvider;
    public Provider<Application> applicationProvider;
    public Provider<D4shPersonalizedDressingModel> d4shPersonalizedDressingModelProvider;
    public Provider<D4shPersonalizedDressingPresenter> d4shPersonalizedDressingPresenterProvider;
    public Provider<Gson> gsonProvider;
    public Provider<IRepositoryManager> repositoryManagerProvider;
    public Provider<RxErrorHandler> rxErrorHandlerProvider;
    public Provider<D4shPersonalizedDressingContract.View> viewProvider;

    public DaggerD4shPersonalizedDressingComponent(AppComponent appComponent, D4shPersonalizedDressingContract.View view) {
        initialize(appComponent, view);
    }

    public static D4shPersonalizedDressingComponent.Builder builder() {
        return new Builder();
    }

    public final void initialize(AppComponent appComponent, D4shPersonalizedDressingContract.View view) {
        this.repositoryManagerProvider = new com_jess_arms_di_component_AppComponent_repositoryManager(appComponent);
        this.gsonProvider = new com_jess_arms_di_component_AppComponent_gson(appComponent);
        com_jess_arms_di_component_AppComponent_application com_jess_arms_di_component_appcomponent_application = new com_jess_arms_di_component_AppComponent_application(appComponent);
        this.applicationProvider = com_jess_arms_di_component_appcomponent_application;
        this.d4shPersonalizedDressingModelProvider = DoubleCheck.provider(D4shPersonalizedDressingModel_Factory.create(this.repositoryManagerProvider, this.gsonProvider, com_jess_arms_di_component_appcomponent_application));
        this.viewProvider = InstanceFactory.create(view);
        this.rxErrorHandlerProvider = new com_jess_arms_di_component_AppComponent_rxErrorHandler(appComponent);
        com_jess_arms_di_component_AppComponent_appManager com_jess_arms_di_component_appcomponent_appmanager = new com_jess_arms_di_component_AppComponent_appManager(appComponent);
        this.appManagerProvider = com_jess_arms_di_component_appcomponent_appmanager;
        this.d4shPersonalizedDressingPresenterProvider = DoubleCheck.provider(D4shPersonalizedDressingPresenter_Factory.create(this.d4shPersonalizedDressingModelProvider, this.viewProvider, this.rxErrorHandlerProvider, this.applicationProvider, com_jess_arms_di_component_appcomponent_appmanager));
    }

    @Override // com.petkit.android.activities.petkitBleDevice.d4sh.component.D4shPersonalizedDressingComponent
    public void inject(D4shPersonalizedDressingActivity d4shPersonalizedDressingActivity) {
        injectD4shPersonalizedDressingActivity(d4shPersonalizedDressingActivity);
    }

    @CanIgnoreReturnValue
    public final D4shPersonalizedDressingActivity injectD4shPersonalizedDressingActivity(D4shPersonalizedDressingActivity d4shPersonalizedDressingActivity) {
        BaseActivity_MembersInjector.injectMPresenter(d4shPersonalizedDressingActivity, this.d4shPersonalizedDressingPresenterProvider.get());
        return d4shPersonalizedDressingActivity;
    }

    public static final class Builder implements D4shPersonalizedDressingComponent.Builder {
        public AppComponent appComponent;
        public D4shPersonalizedDressingContract.View view;

        public Builder() {
        }

        @Override // com.petkit.android.activities.petkitBleDevice.d4sh.component.D4shPersonalizedDressingComponent.Builder
        public Builder view(D4shPersonalizedDressingContract.View view) {
            this.view = (D4shPersonalizedDressingContract.View) Preconditions.checkNotNull(view);
            return this;
        }

        @Override // com.petkit.android.activities.petkitBleDevice.d4sh.component.D4shPersonalizedDressingComponent.Builder
        public Builder appComponent(AppComponent appComponent) {
            this.appComponent = (AppComponent) Preconditions.checkNotNull(appComponent);
            return this;
        }

        @Override // com.petkit.android.activities.petkitBleDevice.d4sh.component.D4shPersonalizedDressingComponent.Builder
        public D4shPersonalizedDressingComponent build() {
            Preconditions.checkBuilderRequirement(this.view, D4shPersonalizedDressingContract.View.class);
            Preconditions.checkBuilderRequirement(this.appComponent, AppComponent.class);
            return new DaggerD4shPersonalizedDressingComponent(this.appComponent, this.view);
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
